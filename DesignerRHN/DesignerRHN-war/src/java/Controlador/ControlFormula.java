package Controlador;

import Entidades.Formulas;
import Entidades.TiposFormulas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarFormulaInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlFormula implements Serializable {

    @EJB
    AdministrarFormulaInterface administrarFormula;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Parametros que llegan
    private TiposFormulas tiposFormulas;
    private List<Formulas> listaFormulas;
    private List<Formulas> filtradoListaFormulas;
    private List<Formulas> listaFormulasLOV;
    private List<Formulas> filtradoListaFormulasLOV;
    private Formulas formulaSeleccionadaLOV;
    private Formulas formulaSeleccionada;
    private boolean verSeleccionFormula;
    private boolean verMostrarTodos;
    private boolean mostrarTodos;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column columnaNombreLargo, columnaNombreCorto, columnaEstado, columnaNota, columnaTipo, columnaPeriodicidad;
    //Otros
    private boolean aceptar;
    private String altoTabla;
    private boolean propiedadesFormula;
    //modificar
    private List<Formulas> listaFormulasModificar;
    private boolean guardado;
    //crear VC
    public Formulas nuevaFormula;
    private List<Formulas> listaFormulasCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Formulas> listaFormulasBorrar;
    //editar celda
    private Formulas editarFormula;
    private int cualCelda, tipoLista;
    //duplicar
    private Formulas duplicarFormula;
    //RASTRO
    //CLONAR Formula
    private Formulas formulaOriginal;
    private Formulas formulaClon;
    private int cambioFormula;
    private boolean activoDetalleFormula, activoBuscarTodos;
    //0 - Detalle Concepto / 1 - Nomina
    private int llamadoPrevioPagina;
    private String paginaAnterior;
    //
    private String infoRegistro;
    private String infoRegistroFormula;
    //
    private DataTable tabla;
    private boolean unaVez;
    private int regSolucion;
    private String nombreLargoMientras;

    public ControlFormula() {
        llamadoPrevioPagina = 1;
        activoBuscarTodos = false;
        activoDetalleFormula = true;
        listaFormulas = null;
        filtradoListaFormulas = null;
        //Otros
        aceptar = true;
        listaFormulasBorrar = new ArrayList<Formulas>();
        listaFormulasCrear = new ArrayList<Formulas>();
        k = 0;
        listaFormulasModificar = new ArrayList<Formulas>();
        editarFormula = new Formulas();
        mostrarTodos = true;
        cualCelda = -1;
        guardado = true;
        //Crear 
        nuevaFormula = new Formulas();
        duplicarFormula = new Formulas();
        //CLON
        formulaClon = new Formulas();
        formulaOriginal = new Formulas();
        permitirIndex = true;
        altoTabla = "204";
        tiposFormulas = null;

        formulaSeleccionada = null;
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        tipoLista = 0;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarFormula.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
        listaFormulas = null;
        getListaFormulas();
        contarRegistros();
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public boolean activarSelec() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        regSolucion = -1;
        nombreLargoMientras = "0";
        return false;
    }

    //OBTENER FORMULA POR SECUENCIA
    public void obtenerFormulaSecuencia(BigInteger secuencia) {
        if (secuencia != null) {
            Formulas actual = administrarFormula.buscarFormulaSecuencia(secuencia);
            listaFormulas = new ArrayList<Formulas>();
            listaFormulas.add(actual);
            activoBuscarTodos = true;
            if (listaFormulas != null || !listaFormulas.isEmpty()) {
                if (listaFormulas.get(0).getTipo().equals("FINAL") && listaFormulas.get(0).getEstado().equals("ACTIVO")) {
                    propiedadesFormula = false;
                } else {
                    propiedadesFormula = true;
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:novedadFormula");
                context.update("form:procesoFormula");
                context.update("form:conceptoFormula");
                context.update("form:legislacionFormula");
                context.update("form:buscarFormula");
                context.update("form:mostrarTodos");
            }
        } else {
            getListaFormulas();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:buscarFormula");
            context.update("form:mostrarTodos");
            activoBuscarTodos = false;
        }
        llamadoPrevioPagina = 0;
    }

    public void recibirDatosTiposFormulas(BigInteger secuenciaTiposFormulas, TiposFormulas tiposFormulasRegistro) {
        tiposFormulas = tiposFormulasRegistro;
        listaFormulas = null;
        getListaFormulas();
        contarRegistros();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");
    }

    //SELECCIONAR NATURALEZA
    public void seleccionarItem(Formulas formula) {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        regSolucion = -1;
        nombreLargoMientras = "0";
        formulaSeleccionada = formula;
        if (!listaFormulasCrear.contains(formulaSeleccionada)) {
            if (listaFormulasModificar.isEmpty()) {
                listaFormulasModificar.add(formulaSeleccionada);
            } else if (!listaFormulasModificar.contains(formulaSeleccionada)) {
                listaFormulasModificar.add(formulaSeleccionada);
            }
        }

        if (guardado) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaSeleccionada.getTipo().equals("FINAL") && formulaSeleccionada.getEstado().equals("ACTIVO")) {
            propiedadesFormula = false;
        } else {
            propiedadesFormula = true;
        }
        context.update("form:novedadFormula");
        context.update("form:procesoFormula");
        context.update("form:conceptoFormula");
        context.update("form:legislacionFormula");
        //context.update("form:datosFormulas");
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaSeleccionada != null) {
            editarFormula = formulaSeleccionada;

            if (cualCelda == 0) {
                context.update("formularioDialogos:editorNombreLargo");
                context.execute("editorNombreLargo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editorNombreCorto");
                context.execute("editorNombreCorto.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editorNota");
                context.execute("editorNota.show()");
                cualCelda = -1;
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
        activoDetalleFormula = true;
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
    }

    public void modificarFormula(Formulas formula) {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        formulaSeleccionada = formula;

        if (!listaFormulasCrear.contains(formulaSeleccionada)) {
            if (listaFormulasModificar.isEmpty()) {
                listaFormulasModificar.add(formulaSeleccionada);
            } else if (!listaFormulasModificar.contains(formulaSeleccionada)) {
                listaFormulasModificar.add(formulaSeleccionada);
            }
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
        if (formulaSeleccionada.isPeriodicidadFormula()) {
            formulaSeleccionada.setPeriodicidadindependiente("S");
        } else if (formulaSeleccionada.isPeriodicidadFormula() == false) {
            formulaSeleccionada.setPeriodicidadindependiente("N");
        }
        context.update("form:datosFormulas");
    }

    //Ubicacion Celda.
    public void cambiarIndice(Formulas formula, int celda) {
        if (permitirIndex && unaVez) {
            RequestContext context = RequestContext.getCurrentInstance();
            formulaSeleccionada = formula;
            cualCelda = celda;
            if (formulaSeleccionada.getTipo().equals("FINAL") && formulaSeleccionada.getEstado().equals("ACTIVO")) {
                propiedadesFormula = false;
            } else {
                propiedadesFormula = true;
            }
            activoDetalleFormula = false;
            unaVez = false;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:novedadFormula");
            context.update("form:procesoFormula");
            context.update("form:conceptoFormula");
            context.update("form:legislacionFormula");
        }
    }

    public void lovFomula(int quien) {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (quien == 0) {
            if (guardado) {
                activoDetalleFormula = true;
                context.update("form:detalleFormula");
                context.update("form:operandoFormula");
                listaFormulasLOV = null;
                getListaFormulasLOV();
                modificarInfoRegistroFormulasLOV(listaFormulasLOV.size());
                context.update("formularioDialogos:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
                verSeleccionFormula = false;
                cambioFormula = 0;
            } else {
                verSeleccionFormula = true;
                context.execute("confirmarGuardar.show()");
            }
        } else {
            listaFormulasLOV = null;
            getListaFormulasLOV();
            modificarInfoRegistroFormulasLOV(listaFormulasLOV.size());
            context.update("formularioDialogos:FormulasDialogo");
            context.execute("FormulasDialogo.show()");
            cambioFormula = 1;
        }
        cualCelda = -1;
    }

    public void mostrarTodasFormulas() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado) {
            if (bandera == 1) {
                cargarTablaDefault();
            }
            mostrarTodos = true;
            verMostrarTodos = false;
            activoDetalleFormula = true;

            listaFormulas.clear();
            for (int i = 0; i < listaFormulasLOV.size(); i++) {
                listaFormulas.add(listaFormulasLOV.get(i));
            }

            contarRegistros();
            formulaSeleccionada = null;
            context.update("form:informacionRegistro");
            context.update("form:mostrarTodos");
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:datosFormulas");
        } else {
            verMostrarTodos = true;
            context.execute("confirmarGuardar.show()");
        }
        cualCelda = -1;
    }

    public void seleccionFormula() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambioFormula == 0) {
            if (bandera == 1) {
                cargarTablaDefault();
            }
            listaFormulas.clear();
            listaFormulas.add(formulaSeleccionadaLOV);
            formulaSeleccionada = formulaSeleccionadaLOV;
            mostrarTodos = false;
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:datosFormulas");
            context.update("form:mostrarTodos");
            modificarInfoRegistro(listaFormulas.size());
            context.update("form:informacionRegistro");
        } else {
            formulaOriginal = formulaSeleccionadaLOV;
            context.update("form:descripcionClon");
        }
        filtradoListaFormulasLOV = null;
        formulaSeleccionadaLOV = null;
        aceptar = true;

        context.reset("formularioDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void cancelarSeleccionFormula() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        filtradoListaFormulasLOV = null;
        formulaSeleccionadaLOV = null;
        aceptar = true;
        formulaOriginal.setNombresFormula(null);
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void borrarFormula() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaSeleccionada != null) {

            if (!listaFormulasModificar.isEmpty() && listaFormulasModificar.contains(formulaSeleccionada)) {
                int modIndex = listaFormulasModificar.indexOf(formulaSeleccionada);
                listaFormulasModificar.remove(modIndex);
                listaFormulasBorrar.add(formulaSeleccionada);
            } else if (!listaFormulasCrear.isEmpty() && listaFormulasCrear.contains(formulaSeleccionada)) {
                int crearIndex = listaFormulasCrear.indexOf(formulaSeleccionada);
                listaFormulasCrear.remove(crearIndex);
            } else {
                listaFormulasBorrar.add(formulaSeleccionada);
            }
            listaFormulas.remove(formulaSeleccionada);
            if (tipoLista == 1) {
                filtradoListaFormulas.remove(formulaSeleccionada);
            }

            activoDetalleFormula = true;
            modificarInfoRegistro(listaFormulas.size());
            context.update("form:informacionRegistro");
            context.update("form:datosFormulas");
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void guardarSalir() {
        guardarCambios();
        salir();
    }

    public void cancelarSalir() {
        refrescar();
        salir();
    }

    //GUARDAR
    public void guardarCambios() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listaFormulasBorrar.isEmpty()) {
                    for (int i = 0; i < listaFormulasBorrar.size(); i++) {
                        if (listaFormulasBorrar.get(i).isPeriodicidadFormula()) {
                            listaFormulasBorrar.get(i).setPeriodicidadindependiente("S");
                        } else if (listaFormulasBorrar.get(i).isPeriodicidadFormula() == false) {
                            listaFormulasBorrar.get(i).setPeriodicidadindependiente("N");
                        }
                        administrarFormula.borrar(listaFormulasBorrar.get(i));
                    }
                    listaFormulasBorrar.clear();
                }
                if (!listaFormulasCrear.isEmpty()) {
                    for (int i = 0; i < listaFormulasCrear.size(); i++) {
                        if (listaFormulasCrear.get(i).isPeriodicidadFormula()) {
                            listaFormulasCrear.get(i).setPeriodicidadindependiente("S");
                        } else if (listaFormulasCrear.get(i).isPeriodicidadFormula() == false) {
                            listaFormulasCrear.get(i).setPeriodicidadindependiente("N");
                        }
                        administrarFormula.crear(listaFormulasCrear.get(i));
                    }
                    listaFormulasCrear.clear();
                }
                if (!listaFormulasModificar.isEmpty()) {
                    administrarFormula.modificar(listaFormulasModificar);
                    listaFormulasModificar.clear();
                }
                listaFormulas = null;
                getListaFormulas();
                contarRegistros();
                context.update("form:informacionRegistro");
                context.update("form:datosFormulas");
                guardado = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                permitirIndex = true;
                k = 0;
                activoDetalleFormula = true;
                context.update("form:detalleFormula");
                context.update("form:operandoFormula");
                if (verSeleccionFormula) {
                    lovFomula(0);
                }
                if (verMostrarTodos) {
                    mostrarTodasFormulas();
                }
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambios : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Se presento un error en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext c = FacesContext.getCurrentInstance();
        formulaSeleccionada = null;
        if (bandera == 0) {
            altoTabla = "182";
            columnaNombreLargo = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNombreLargo");
            columnaNombreLargo.setFilterStyle("width: 94%;");
            columnaNombreCorto = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNombreCorto");
            columnaNombreCorto.setFilterStyle("width: 94%;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaEstado");
            columnaEstado.setFilterStyle("width: 94%;");
            columnaNota = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNota");
            columnaNota.setFilterStyle("width: 94%;");
            columnaTipo = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaTipo");
            columnaTipo.setFilterStyle("width: 94%;");
            columnaPeriodicidad = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaPeriodicidad");
            columnaPeriodicidad.setFilterStyle("width: 94%;");
            context.update("form:datosFormulas");
            bandera = 1;

        } else if (bandera == 1) {
            cargarTablaDefault();
        }
        activoDetalleFormula = true;
        context.update("form:datosFormulas");
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
    }

    public void dialogoIngresoNuevoRegistro() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("NuevaFormulaDialogo.show()");
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
    }

    public void agregarNuevaFormula() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
        if (nuevaFormula.getNombrelargo() == null) {
            mensajeValidacion = " * Nombre Largo\n";
            pasa++;
        }
        if (nuevaFormula.getNombrecorto() == null) {
            mensajeValidacion = mensajeValidacion + " *Nombre Corto\n";
            pasa++;
        }
        if (nuevaFormula.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " *Tipo\n";
            pasa++;
        }
        if (nuevaFormula.getEstado() == null) {
            mensajeValidacion = mensajeValidacion + " *Estado\n";
            pasa++;
        }
        if (pasa == 0) {
            if (bandera == 1) {
                cargarTablaDefault();
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevaFormula.setSecuencia(l);
            if (nuevaFormula.isPeriodicidadFormula()) {
                nuevaFormula.setPeriodicidadindependiente("S");
            } else if (nuevaFormula.isPeriodicidadFormula() == false) {
                nuevaFormula.setPeriodicidadindependiente("N");
            }
            listaFormulasCrear.add(nuevaFormula);
            listaFormulas.add(nuevaFormula);
            nuevaFormula = new Formulas();
            modificarInfoRegistro(listaFormulas.size());
            context.update("form:informacionRegistro");
            context.update("form:datosFormulas");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevaFormulaDialogo.hide()");
            context.update("formularioDialogos:NuevaFormulaDialogo");
        } else {
            context.update("formularioDialogos:validacioNuevaFormula");
            context.execute("validacioNuevaFormula.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaFormula() {
        nuevaFormula = new Formulas();
    }

    public void duplicarRegistro() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaSeleccionada != null) {
            duplicarFormula = new Formulas();
            duplicarFormula.setNombrelargo(formulaSeleccionada.getNombrelargo());
            duplicarFormula.setNombrecorto(formulaSeleccionada.getNombrecorto());
            duplicarFormula.setEstado(formulaSeleccionada.getEstado());
            duplicarFormula.setObservaciones(formulaSeleccionada.getObservaciones());
            duplicarFormula.setTipo(formulaSeleccionada.getTipo());
            duplicarFormula.setPeriodicidadindependiente(formulaSeleccionada.getPeriodicidadindependiente());

            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("formularioDialogos:duplicarFormula");
            context.execute("DuplicarFormulaDialogo.show()");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
        if (duplicarFormula.getNombrelargo() == null) {
            mensajeValidacion = " * Nombre Largo\n";
            pasa++;
        }
        if (duplicarFormula.getNombrecorto() == null) {
            mensajeValidacion = mensajeValidacion + " *Nombre Corto\n";
            pasa++;
        }
        if (duplicarFormula.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " *Tipo\n";
            pasa++;
        }
        if (duplicarFormula.getEstado() == null) {
            mensajeValidacion = mensajeValidacion + " *Estado\n";
            pasa++;
        }
        if (pasa == 0) {
            if (duplicarFormula.isPeriodicidadFormula()) {
                duplicarFormula.setPeriodicidadindependiente("S");
            } else if (duplicarFormula.isPeriodicidadFormula() == false) {
                duplicarFormula.setPeriodicidadindependiente("N");
            }
            k++;
            l = BigInteger.valueOf(k);
            duplicarFormula.setSecuencia(l);
            listaFormulas.add(duplicarFormula);
            listaFormulasCrear.add(duplicarFormula);
            context.update("form:datosFormulas");
            context.execute("DuplicarFormulaDialogo.hide()");
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            modificarInfoRegistro(listaFormulas.size());
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                cargarTablaDefault();
            }
            context.update("form:datosFormulas");
            duplicarFormula = new Formulas();
        } else {
            context.update("formularioDialogos:validacioNuevaFormula");
            context.execute("validacioNuevaFormula.show()");
        }
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicar() {
        duplicarFormula = new Formulas();
    }

    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            cargarTablaDefault();
        }
        listaFormulasBorrar.clear();
        listaFormulasCrear.clear();
        listaFormulasModificar.clear();
        formulaSeleccionada = null;
        k = 0;
        listaFormulas = null;
        guardado = true;
        context.update("form:ACEPTAR");
        permitirIndex = true;
        mostrarTodos = true;
        formulaClon = new Formulas();
        formulaOriginal = new Formulas();
        activoDetalleFormula = true;
        if (verSeleccionFormula) {
            lovFomula(0);
        }
        if (verMostrarTodos) {
            mostrarTodasFormulas();
        }
        activoBuscarTodos = false;
        context.update("form:detalleFormula");
        context.update("form:mostrarTodos");
        context.update("form:datosFormulas");
        context.update("form:nombreLargoFormulaClon");
        context.update("form:nombreCortoFormulaClon");
        context.update("form:observacionFormulaClon");
        context.update("form:descripcionClon");
        context.update("form:operandoFormula");
        formulaSeleccionada = null;
    }

    public void refrescar() {
        unaVez = true;
        regSolucion = -1;
        tipoLista = 0;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            cargarTablaDefault();
        }
        listaFormulasBorrar.clear();
        listaFormulasCrear.clear();
        listaFormulasModificar.clear();
        formulaSeleccionada = null;
        k = 0;
        listaFormulas = null;
        getListaFormulas();
        contarRegistros();
        context.update("form:informacionRegistro");
        guardado = true;
        context.update("form:ACEPTAR");
        permitirIndex = true;
        mostrarTodos = true;
        formulaClon = new Formulas();
        formulaOriginal = new Formulas();
        activoDetalleFormula = true;
        if (verSeleccionFormula) {
            lovFomula(0);
        }
        if (verMostrarTodos) {
            mostrarTodasFormulas();
        }
        context.update("form:detalleFormula");
        context.update("form:mostrarTodos");
        context.update("form:datosFormulas");
        context.update("form:nombreLargoFormulaClon");
        context.update("form:nombreCortoFormulaClon");
        context.update("form:observacionFormulaClon");
        context.update("form:descripcionClon");
        context.update("form:operandoFormula");
    }

    public void cargarTablaDefault() {
        unaVez = true;
        regSolucion = -1;
        tipoLista = 0;
        nombreLargoMientras = "0";
        FacesContext c = FacesContext.getCurrentInstance();
        altoTabla = "204";
        columnaNombreLargo = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNombreLargo");
        columnaNombreLargo.setFilterStyle("display: none; visibility: hidden;");
        columnaNombreCorto = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNombreCorto");
        columnaNombreCorto.setFilterStyle("display: none; visibility: hidden;");
        columnaEstado = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaEstado");
        columnaEstado.setFilterStyle("display: none; visibility: hidden;");
        columnaNota = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaNota");
        columnaNota.setFilterStyle("display: none; visibility: hidden;");
        columnaTipo = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaTipo");
        columnaTipo.setFilterStyle("display: none; visibility: hidden;");
        columnaPeriodicidad = (Column) c.getViewRoot().findComponent("form:datosFormulas:columnaPeriodicidad");
        columnaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
        bandera = 0;
        filtradoListaFormulas = null;
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        activoDetalleFormula = true;
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
        if (formulaSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(formulaSeleccionada.getSecuencia(), "FORMULAS");
            if (resultado == 1) {
                context.execute("errorObjetosDB.show()");
            } else if (resultado == 2) {
                context.execute("confirmarRastro.show()");
            } else if (resultado == 3) {
                context.execute("errorRegistroRastro.show()");
            } else if (resultado == 4) {
                context.execute("errorTablaConRastro.show()");
            } else if (resultado == 5) {
                context.execute("errorTablaSinRastro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("FORMULAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        DataTable tablaE = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosFormulasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tablaE, "FormulasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        activoDetalleFormula = true;
        RequestContext contxt = RequestContext.getCurrentInstance();
        contxt.update("form:detalleFormula");
        contxt.update("form:operandoFormula");
    }

    public void exportXLS() throws IOException {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        DataTable tablaE = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosFormulasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tablaE, "FormulasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        activoDetalleFormula = true;
        RequestContext contxt = RequestContext.getCurrentInstance();
        contxt.update("form:detalleFormula");
        contxt.update("form:operandoFormula");
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        unaVez = true;
        regSolucion = -1;
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        nombreLargoMientras = "0";
        formulaSeleccionada = null;
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistro(filtradoListaFormulas.size());
        context.update("form:informacionRegistro");
    }

    public void eventoFiltrarFormulas() {
        modificarInfoRegistroFormulasLOV(filtradoListaFormulasLOV.size());
        RequestContext.getCurrentInstance().update("formularioDialogos:infoRegistroFormula");
    }

    public void modificarInfoRegistroFormulasLOV(int valor) {
        infoRegistroFormula = String.valueOf(valor);
    }

    public void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    public void contarRegistros() {
        if (listaFormulas != null) {
            modificarInfoRegistro(listaFormulas.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    public void verDetalle(Formulas formula) {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        formulaSeleccionada = formula;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "historiaFormula");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //CLONAR
    public void clonarFormula() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaClon.getNombrelargo() != null && formulaClon.getNombrecorto() != null && formulaOriginal.getSecuencia() != null) {
            administrarFormula.clonarFormula(formulaOriginal.getNombrecorto(), formulaClon.getNombrecorto(), formulaClon.getNombrelargo(), formulaClon.getObservaciones());
            formulaClon = new Formulas();
            formulaOriginal = new Formulas();
            listaFormulas = null;
            getListaFormulas();
            contarRegistros();
            context.update("form:informacionRegistro");
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:datosFormulas");
            context.update("form:nombreLargoFormulaClon");
            context.update("form:nombreCortoFormulaClon");
            context.update("form:observacionFormulaClon");
            context.update("form:descripcionClon");
            guardado = false;
            context.update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Formula clonada con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } else {
            context.update("formularioDialogos:validacioNuevoClon");
            context.execute("validacioNuevoClon.show()");
        }
    }

    //FORMULA OPERANDO
    public void formulaOperando(Formulas formula) {
        formulaSeleccionada = formula;
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("confirmarOperandoFormula.show();");
    }

    public void confirmarFormulaOperando() {
        administrarFormula.operandoFormula(formulaSeleccionada.getSecuencia());
    }

    public String paginaRetorno() {
        unaVez = true;
        regSolucion = -1;
        nombreLargoMientras = "0";
        String paginaRetorno = "";
        if (llamadoPrevioPagina == 1) {
            paginaRetorno = "nomina";
        }
        if (llamadoPrevioPagina == 0) {
            paginaRetorno = "retornoDetalleConcepto";
        }
        llamadoPrevioPagina = 1;
        return paginaRetorno;
    }

    public void recordarSeleccion() {
        if (formulaSeleccionada != null) {
            if (listaFormulas.contains(formulaSeleccionada)) {
                if (!listaFormulas.get(0).equals(formulaSeleccionada)) {

                    regSolucion = listaFormulas.indexOf(formulaSeleccionada);
                    nombreLargoMientras = listaFormulas.get(regSolucion - 1).getNombrelargo();
                    List<Formulas> listaAux = new ArrayList<Formulas>();

                    for (int i = 0; i < listaFormulas.size(); i++) {
                        if (!listaFormulas.get(i).equals(formulaSeleccionada)) {
                            listaAux.add(listaFormulas.get(i));
                        }
                    }
                    listaFormulas.set(0, formulaSeleccionada);
                    for (int n = 0; n < listaAux.size(); n++) {
                        listaFormulas.set((n + 1), listaAux.get(n));
                    }
                }
            }
            if ((unaVez == false) && (regSolucion != -1) && (!nombreLargoMientras.equals("0"))) {
                listaFormulas.get(regSolucion).setNombrelargo(nombreLargoMientras);
            }
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosFormulas");
            tabla.setSelection(formulaSeleccionada);
        } else {
            unaVez = true;
            regSolucion = -1;
            nombreLargoMientras = "0";
            formulaSeleccionada = null;
        }
    }

    //GETTER AND SETTER
    public List<Formulas> getListaFormulas() {
        if (listaFormulas == null && tiposFormulas == null) {

            listaFormulas = administrarFormula.formulas();
            if (listaFormulas != null || !listaFormulas.isEmpty()) {
                if (listaFormulas.get(0).getTipo().equals("FINAL") && listaFormulas.get(0).getEstado().equals("ACTIVO")) {
                    propiedadesFormula = false;
                } else {
                    propiedadesFormula = true;
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:novedadFormula");
                context.update("form:procesoFormula");
                context.update("form:conceptoFormula");
                context.update("form:legislacionFormula");
            }
        } else if (tiposFormulas != null) {
            listaFormulas = administrarFormula.formulas();
            listaFormulas.clear();
            listaFormulas.add(tiposFormulas.getFormula());
        }
        return listaFormulas;
    }

    public void setListaFormulas(List<Formulas> listaFormulas) {
        this.listaFormulas = listaFormulas;
    }

    public List<Formulas> getFiltradoListaFormulas() {
        return filtradoListaFormulas;
    }

    public void setFiltradoListaFormulas(List<Formulas> filtradoListaFormulas) {
        this.filtradoListaFormulas = filtradoListaFormulas;
    }

    public List<Formulas> getListaFormulasLOV() {
        if (listaFormulasLOV == null) {
            listaFormulasLOV = administrarFormula.formulas();
        }
        return listaFormulasLOV;
    }

    public void setListaFormulasLOV(List<Formulas> listaFormulasLOV) {
        this.listaFormulasLOV = listaFormulasLOV;
    }

    public List<Formulas> getFiltradoListaFormulasLOV() {
        return filtradoListaFormulasLOV;
    }

    public void setFiltradoListaFormulasLOV(List<Formulas> filtradoListaFormulasLOV) {
        this.filtradoListaFormulasLOV = filtradoListaFormulasLOV;
    }

    public Formulas getFormulaSeleccionadaLOV() {
        return formulaSeleccionadaLOV;
    }

    public void setFormulaSeleccionadaLOV(Formulas formulaSeleccionadaLov) {
        this.formulaSeleccionadaLOV = formulaSeleccionadaLov;
    }

    public boolean isMostrarTodos() {
        return mostrarTodos;
    }

    public void setMostrarTodos(boolean mostrarTodos) {
        this.mostrarTodos = mostrarTodos;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public Formulas getNuevaFormula() {
        return nuevaFormula;
    }

    public void setNuevaFormula(Formulas nuevaFormula) {
        this.nuevaFormula = nuevaFormula;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public Formulas getEditarFormula() {
        return editarFormula;
    }

    public void setEditarFormula(Formulas editarFormula) {
        this.editarFormula = editarFormula;
    }

    public Formulas getDuplicarFormula() {
        return duplicarFormula;
    }

    public void setDuplicarFormula(Formulas duplicarFormula) {
        this.duplicarFormula = duplicarFormula;
    }

    public Formulas getFormulaOriginal() {
        return formulaOriginal;
    }

    public void setFormulaOriginal(Formulas formulaOriginal) {
        this.formulaOriginal = formulaOriginal;
    }

    public Formulas getFormulaClon() {
        return formulaClon;
    }

    public void setFormulaClon(Formulas formulaClon) {
        this.formulaClon = formulaClon;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public boolean isPropiedadesFormula() {
        return propiedadesFormula;
    }

    public boolean isActivoDetalleFormula() {
        return activoDetalleFormula;
    }

    public void setActivoDetalleFormula(boolean activoDetalleFormula) {
        this.activoDetalleFormula = activoDetalleFormula;
    }

    public boolean isActivoBuscarTodos() {
        return activoBuscarTodos;
    }

    public void setActivoBuscarTodos(boolean activoBuscarTodos) {
        this.activoBuscarTodos = activoBuscarTodos;
    }

    public Formulas getFormulaSeleccionada() {
        getListaFormulas();
        if (listaFormulas != null) {
            if (listaFormulas.size() > 0) {
                if (formulaSeleccionada == null) {
                    formulaSeleccionada = listaFormulas.get(0);
                }
            }
        }
        return formulaSeleccionada;
    }

    public void setFormulaSeleccionada(Formulas formulaSeleccionada) {
        this.formulaSeleccionada = formulaSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroFormula() {
        return infoRegistroFormula;
    }

    public void setInfoRegistroFormula(String infoRegistroFormula) {
        this.infoRegistroFormula = infoRegistroFormula;
    }
}

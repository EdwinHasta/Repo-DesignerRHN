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
    private Formulas formulaSeleccionada;
    private Formulas formulaSeleccionadaPrueba;
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
    private int index;
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
    private BigInteger secRegistro;
    //CLONAR Formula
    private Formulas formulaOriginal;
    private Formulas formulaClon;
    private int cambioFormula;
    private boolean activoDetalleFormula, activoBuscarTodos;
    //0 - Detalle Concepto / 1 - Nomina
    private int llamadoPrevioPagina;
    private Formulas actualFormula;
    private String paginaAnterior;
    //
    private String infoRegistro;
    private String infoRegistroFormula;
    //
    private DataTable tabla;

    public ControlFormula() {
        actualFormula = new Formulas();
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
        tipoLista = 0;
        guardado = true;
        //Crear 
        nuevaFormula = new Formulas();
        duplicarFormula = new Formulas();
        //CLON
        formulaClon = new Formulas();
        formulaOriginal = new Formulas();
        permitirIndex = true;
        altoTabla = "194";
        tiposFormulas = null;
        
        formulaSeleccionadaPrueba = null;

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
        if (listaFormulas != null) {
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
    }

    public String redirigir() {
        return paginaAnterior;
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
        if (listaFormulas != null) {
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");
    }

    //SELECCIONAR NATURALEZA
    public void seleccionarItem(int indice) {
        if (tipoLista == 0) {
            if (!listaFormulasCrear.contains(listaFormulas.get(indice))) {
                if (listaFormulasModificar.isEmpty()) {
                    listaFormulasModificar.add(listaFormulas.get(indice));
                } else if (!listaFormulasModificar.contains(listaFormulas.get(indice))) {
                    listaFormulasModificar.add(listaFormulas.get(indice));
                }
            }
        } else {
            if (!listaFormulasCrear.contains(filtradoListaFormulas.get(indice))) {
                if (listaFormulasModificar.isEmpty()) {
                    listaFormulasModificar.add(filtradoListaFormulas.get(indice));
                } else if (!listaFormulasModificar.contains(filtradoListaFormulas.get(indice))) {
                    listaFormulasModificar.add(filtradoListaFormulas.get(indice));
                }
            }
        }
        if (guardado) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaFormulas.get(indice).getTipo().equals("FINAL") && listaFormulas.get(indice).getEstado().equals("ACTIVO")) {
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                editarFormula = listaFormulas.get(index);
            }
            if (tipoLista == 1) {
                editarFormula = filtradoListaFormulas.get(index);
            }
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
        secRegistro = null;
    }

    public void modificarFormula(int indice) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
            if (!listaFormulasCrear.contains(listaFormulas.get(indice))) {
                if (listaFormulasModificar.isEmpty()) {
                    listaFormulasModificar.add(listaFormulas.get(indice));
                } else if (!listaFormulasModificar.contains(listaFormulas.get(indice))) {
                    listaFormulasModificar.add(listaFormulas.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            if (listaFormulas.get(indice).isPeriodicidadFormula()) {
                listaFormulas.get(indice).setPeriodicidadindependiente("S");
            } else if (listaFormulas.get(indice).isPeriodicidadFormula() == false) {
                listaFormulas.get(indice).setPeriodicidadindependiente("N");
            }
            secRegistro = null;
        } else {
            if (!listaFormulasCrear.contains(filtradoListaFormulas.get(indice))) {

                if (listaFormulasModificar.isEmpty()) {
                    listaFormulasModificar.add(filtradoListaFormulas.get(indice));
                } else if (!listaFormulasModificar.contains(filtradoListaFormulas.get(indice))) {
                    listaFormulasModificar.add(filtradoListaFormulas.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            if (filtradoListaFormulas.get(indice).isPeriodicidadFormula()) {
                filtradoListaFormulas.get(indice).setPeriodicidadindependiente("S");
            } else if (filtradoListaFormulas.get(indice).isPeriodicidadFormula() == false) {
                filtradoListaFormulas.get(indice).setPeriodicidadindependiente("N");
            }
            secRegistro = null;
        }
        //context.update("form:datosFormulas");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex) {
            RequestContext context = RequestContext.getCurrentInstance();
            index = indice;
            cualCelda = celda;
            secRegistro = listaFormulas.get(index).getSecuencia();
            if (tipoLista == 0) {
                actualFormula = listaFormulas.get(index);
                if (listaFormulas.get(index).getTipo().equals("FINAL") && listaFormulas.get(indice).getEstado().equals("ACTIVO")) {
                    propiedadesFormula = false;
                } else {
                    propiedadesFormula = true;
                }
            }
            if (tipoLista == 1) {
                actualFormula = filtradoListaFormulas.get(index);
                if (filtradoListaFormulas.get(index).getTipo().equals("FINAL") && filtradoListaFormulas.get(indice).getEstado().equals("ACTIVO")) {
                    propiedadesFormula = false;
                } else {
                    propiedadesFormula = true;
                }
            }
            activoDetalleFormula = false;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:novedadFormula");
            context.update("form:procesoFormula");
            context.update("form:conceptoFormula");
            context.update("form:legislacionFormula");
        }
    }

    public void lovFomula(int quien) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (quien == 0) {
            if (guardado) {
                activoDetalleFormula = true;
                context.update("form:detalleFormula");
                context.update("form:operandoFormula");
                listaFormulasLOV = null;
                getListaFormulasLOV();
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
            context.update("formularioDialogos:FormulasDialogo");
            context.execute("FormulasDialogo.show()");
            cambioFormula = 1;
        }
        secRegistro = null;
        cualCelda = -1;
    }

    public void mostrarTodasFormulas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "194";
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
                RequestContext.getCurrentInstance().update("form:datosFormulas");
                bandera = 0;
                filtradoListaFormulas = null;
                tipoLista = 0;
            }
            listaFormulas = null;
            mostrarTodos = true;
            verMostrarTodos = false;
            activoDetalleFormula = true;
            getListaFormulas();
            if (listaFormulas != null) {
                infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            context.update("form:informacionRegistro");
            context.update("form:datosFormulas");
            context.update("form:mostrarTodos");
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
        } else {
            verMostrarTodos = true;
            context.execute("confirmarGuardar.show()");
        }
        secRegistro = null;
        cualCelda = -1;
    }

    public void seleccionFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambioFormula == 0) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "194";
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
                columnaPeriodicidad.
                        setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosFormulas");
                bandera = 0;
                filtradoListaFormulas = null;
                tipoLista = 0;
            }
            listaFormulas.clear();
            listaFormulas.add(formulaSeleccionada);
            mostrarTodos = false;
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("form:datosFormulas");
            context.update("form:mostrarTodos");
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            context.update("form:informacionRegistro");
        } else {
            formulaOriginal = formulaSeleccionada;
            context.update("form:descripcionClon");
        }
        filtradoListaFormulasLOV = null;
        formulaSeleccionada = null;
        aceptar = true;
        
        context.reset("formularioDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void cancelarSeleccionFormula() {
        filtradoListaFormulasLOV = null;
        formulaSeleccionada = null;
        aceptar = true;
        formulaOriginal.setNombresFormula(null);
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovFormulas:globalFilter");
        context.execute("lovFormulas.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void borrarFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaFormulasModificar.isEmpty() && listaFormulasModificar.contains(listaFormulas.get(index))) {
                    int modIndex = listaFormulasModificar.indexOf(listaFormulas.get(index));
                    listaFormulasModificar.remove(modIndex);
                    listaFormulasBorrar.add(listaFormulas.get(index));
                } else if (!listaFormulasCrear.isEmpty() && listaFormulasCrear.contains(listaFormulas.get(index))) {
                    int crearIndex = listaFormulasCrear.indexOf(listaFormulas.get(index));
                    listaFormulasCrear.remove(crearIndex);
                } else {
                    listaFormulasBorrar.add(listaFormulas.get(index));
                }
                listaFormulas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaFormulasModificar.isEmpty() && listaFormulasModificar.contains(filtradoListaFormulas.get(index))) {
                    int modIndex = listaFormulasModificar.indexOf(filtradoListaFormulas.get(index));
                    listaFormulasModificar.remove(modIndex);
                    listaFormulasBorrar.add(filtradoListaFormulas.get(index));
                } else if (!listaFormulasCrear.isEmpty() && listaFormulasCrear.contains(filtradoListaFormulas.get(index))) {
                    int crearIndex = listaFormulasCrear.indexOf(filtradoListaFormulas.get(index));
                    listaFormulasCrear.remove(crearIndex);
                } else {
                    listaFormulasBorrar.add(filtradoListaFormulas.get(index));
                }
                filtradoListaFormulas.remove(index);
            }
            activoDetalleFormula = true;
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            context.update("form:informacionRegistro");
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            context.update("form:informacionRegistro");
            context.update("form:datosFormulas");
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            secRegistro = null;
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
                if (listaFormulas != null) {
                    infoRegistro = "Cantidad de registros : " + listaFormulas.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                context.update("form:datosFormulas");
                guardado = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                permitirIndex = true;
                k = 0;
                secRegistro = null;
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
        index = -1;
        secRegistro = null;
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "172";
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
            RequestContext.getCurrentInstance().update("form:datosFormulas");
            bandera = 1;

        } else if (bandera == 1) {
            altoTabla = "194";
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
            RequestContext.getCurrentInstance().update("form:datosFormulas");
            bandera = 0;
            filtradoListaFormulas = null;
            tipoLista = 0;
        }
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
    }

    public void dialogoIngresoNuevoRegistro() {
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("NuevaFormulaDialogo.show()");
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
    }

    public void agregarNuevaFormula() {

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
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "194";
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
                RequestContext.getCurrentInstance().update("form:datosFormulas");
                bandera = 0;
                filtradoListaFormulas = null;
                tipoLista = 0;
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
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            context.update("form:informacionRegistro");
            context.update("form:datosFormulas");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevaFormulaDialogo.hide()");
            context.update("formularioDialogos:NuevaFormulaDialogo");
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacioNuevaFormula");
            context.execute("validacioNuevaFormula.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaFormula() {
        nuevaFormula = new Formulas();
        secRegistro = null;
    }

    public void duplicarRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            duplicarFormula = new Formulas();
            if (tipoLista == 0) {
                duplicarFormula.setNombrelargo(listaFormulas.get(index).getNombrelargo());
                duplicarFormula.setNombrecorto(listaFormulas.get(index).getNombrecorto());
                duplicarFormula.setEstado(listaFormulas.get(index).getEstado());
                duplicarFormula.setObservaciones(listaFormulas.get(index).getObservaciones());
                duplicarFormula.setTipo(listaFormulas.get(index).getTipo());
                duplicarFormula.setPeriodicidadindependiente(listaFormulas.get(index).getPeriodicidadindependiente());
            }
            if (tipoLista == 1) {
                duplicarFormula.setNombrelargo(filtradoListaFormulas.get(index).getNombrelargo());
                duplicarFormula.setNombrecorto(filtradoListaFormulas.get(index).getNombrecorto());
                duplicarFormula.setEstado(filtradoListaFormulas.get(index).getEstado());
                duplicarFormula.setObservaciones(filtradoListaFormulas.get(index).getObservaciones());
                duplicarFormula.setTipo(filtradoListaFormulas.get(index).getTipo());
                duplicarFormula.setPeriodicidadindependiente(filtradoListaFormulas.get(index).getPeriodicidadindependiente());
            }
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:operandoFormula");
            context.update("formularioDialogos:duplicarFormula");
            context.execute("DuplicarFormulaDialogo.show()");
            secRegistro = null;
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
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
            secRegistro = null;
            context.execute("DuplicarFormulaDialogo.hide()");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "194";
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
                RequestContext.getCurrentInstance().update("form:datosFormulas");
                bandera = 0;
                filtradoListaFormulas = null;
                tipoLista = 0;
            }
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
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "194";
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
            RequestContext.getCurrentInstance().update("form:datosFormulas");
            bandera = 0;
            filtradoListaFormulas = null;
            tipoLista = 0;
        }
        listaFormulasBorrar.clear();
        listaFormulasCrear.clear();
        listaFormulasModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaFormulas = null;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.update("form:mostrarTodos");
        context.update("form:datosFormulas");
        context.update("form:nombreLargoFormulaClon");
        context.update("form:nombreCortoFormulaClon");
        context.update("form:observacionFormulaClon");
        context.update("form:descripcionClon");
        context.update("form:operandoFormula");
        formulaSeleccionadaPrueba = null;
    }

    public void refrescar() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "194";
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
            RequestContext.getCurrentInstance().update("form:datosFormulas");
            bandera = 0;
            filtradoListaFormulas = null;
            tipoLista = 0;
        }
        listaFormulasBorrar.clear();
        listaFormulasCrear.clear();
        listaFormulasModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaFormulas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        getListaFormulas();
        if (listaFormulas != null) {
            infoRegistro = "Cantidad de registros : " + listaFormulas.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        context.update("form:informacionRegistro");
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        activoDetalleFormula = true;
        context.update("form:detalleFormula");
        context.update("form:operandoFormula");
        if (index >= 0) {
            int resultado = administrarRastros.obtenerTabla(secRegistro, "FORMULAS");
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosFormulasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "FormulasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        secRegistro = null;
        activoDetalleFormula = true;
        RequestContext contxt = RequestContext.getCurrentInstance();
        contxt.update("form:detalleFormula");
        contxt.update("form:operandoFormula");
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosFormulasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "FormulasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        secRegistro = null;
        activoDetalleFormula = true;
        RequestContext contxt = RequestContext.getCurrentInstance();
        contxt.update("form:detalleFormula");
        contxt.update("form:operandoFormula");
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        index = -1;
        secRegistro = null;
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros : " + filtradoListaFormulas.size();
        context.update("form:informacionRegistro");
    }

    public void eventosort() {
        index = -1;
        secRegistro = null;
    }
    
    public void verDetalle(int indice) {
        index = indice;
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "historiaFormula");
    }
    
    public void activarAceptar() {
        aceptar = false;
    }

    //CLONAR
    public void clonarFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (formulaClon.getNombrelargo() != null && formulaClon.getNombrecorto() != null && formulaOriginal.getSecuencia() != null) {
            administrarFormula.clonarFormula(formulaOriginal.getNombrecorto(), formulaClon.getNombrecorto(), formulaClon.getNombrelargo(), formulaClon.getObservaciones());
            formulaClon = new Formulas();
            formulaOriginal = new Formulas();
            listaFormulas = null;
            getListaFormulas();
            if (listaFormulas != null) {
                infoRegistro = "Cantidad de registros : " + listaFormulas.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
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
    public void formulaOperando() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarOperandoFormula.show();");
        }
    }

    public void confirmarFormulaOperando() {
        administrarFormula.operandoFormula(listaFormulas.get(index).getSecuencia());
    }

    public String paginaRetorno() {
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
        if (index >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosFormulas");
            formulaSeleccionadaPrueba = listaFormulas.get(index);
            tabla.setSelection(formulaSeleccionadaPrueba);
        } else {
            formulaSeleccionadaPrueba = null;
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
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaFormulasLOV == null || listaFormulasLOV.isEmpty()) {
                infoRegistroFormula = "Cantidad de registros: 0 ";
            } else {
                infoRegistroFormula = "Cantidad de registros: " + listaFormulasLOV.size();
            }
            context.update("form:infoRegistroFormula");
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

    public Formulas getFormulaSeleccionada() {
        return formulaSeleccionada;
    }

    public void setFormulaSeleccionada(Formulas formulaSeleccionada) {
        this.formulaSeleccionada = formulaSeleccionada;
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

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
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

    public Formulas getActualFormula() {
        return actualFormula;
    }

    public void setActualFormula(Formulas actualFormula) {
        this.actualFormula = actualFormula;
    }

    public Formulas getFormulaSeleccionadaPrueba() {
        getListaFormulas();
        if (listaFormulas != null) {
            if (listaFormulas.size() > 0) {
                if(formulaSeleccionadaPrueba == null){
                formulaSeleccionadaPrueba = listaFormulas.get(0);
                }
            }
        }
        return formulaSeleccionadaPrueba;
    }

    public void setFormulaSeleccionadaPrueba(Formulas formulaSeleccionadaPrueba) {
        this.formulaSeleccionadaPrueba = formulaSeleccionadaPrueba;
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
        getListaFormulasLOV();
        if (listaFormulasLOV != null) {
            infoRegistroFormula = "Cantidad de registros : " + listaFormulasLOV.size();
        } else {
            infoRegistroFormula = "Cantidad de registros : 0";
        }
        return infoRegistroFormula;
    }

    public void setInfoRegistroFormula(String infoRegistroFormula) {
        this.infoRegistroFormula = infoRegistroFormula;
    }
}

package Controlador;

import Entidades.Contratos;
import Entidades.TiposCotizantes;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarContratosInterface;
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
public class ControlLegislacion implements Serializable {

    @EJB
    AdministrarContratosInterface administrarContratos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Contratos> listaContratos;
    private List<Contratos> filtradolistaContratos;
    private List<Contratos> listaContratoLOV;
    private List<Contratos> filtradoListaContratosLOV;
    private Contratos contratoSeleccionado;
    private boolean verSeleccionContrato;
    private boolean verMostrarTodos;
    private boolean mostrarTodos;
    private List<TiposCotizantes> listaTiposCotizantes;
    private TiposCotizantes tipoCotizanteSeleccionado;
    private List<TiposCotizantes> filtradoListaTiposCotizantes;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla 
    private Column columnaCodigo, columnaDescripción, columnaTipoCotizante, columnaEstado;
    //Otros
    private boolean aceptar;
    private int index;
    private String altoTabla;
    //modificar
    private List<Contratos> listaContratosModificar;
    private boolean guardado, guardarOk;
    //crear
    public Contratos nuevoContrato;
    private List<Contratos> listaContratosEmpresaCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar
    private List<Contratos> listaContratosBorrar;
    //editar celda
    private Contratos editarContrato;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Contratos duplicarContrato;
    //AUTOCOMPLETAR
    private String tipoCotizante;
    //RASTRO
    private BigInteger secRegistro;
    //REPRODUCIR CONTRATO
    private Contratos contratoOriginal;
    private Contratos contratoClon;
    private int cambioContrato;
    private boolean activoDetalleFormula;
    ///
    private Contratos actualContrato;
    private String paginaAnterior;

    public ControlLegislacion() {
        actualContrato = new Contratos();
        activoDetalleFormula = true;
        listaContratos = null;
        listaContratoLOV = null;
        listaTiposCotizantes = new ArrayList<TiposCotizantes>();
        //Otros
        aceptar = true;
        listaContratosBorrar = new ArrayList<Contratos>();
        listaContratosEmpresaCrear = new ArrayList<Contratos>();
        k = 0;
        listaContratosModificar = new ArrayList<Contratos>();
        //editar
        editarContrato = new Contratos();
        cambioEditor = false;
        aceptarEditar = true;
        mostrarTodos = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        nuevoContrato = new Contratos();
        duplicarContrato = new Contratos();
        //CLON
        contratoClon = new Contratos();
        contratoOriginal = new Contratos();
        permitirIndex = true;
        altoTabla = "230";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarContratos.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void recibirPaginaEntrante(String pagina){
        paginaAnterior = pagina;
        }
    
    public String redirigir(){
        return paginaAnterior;
    }
    
    public void valoresBackupAutocompletar(int tipoNuevo) {
        if (tipoNuevo == 1) {
            tipoCotizante = nuevoContrato.getTipocotizante().getDescripcion();
        } else if (tipoNuevo == 2) {
            tipoCotizante = duplicarContrato.getTipocotizante().getDescripcion();
        }
    }

    public void autocompletarNuevoyDuplicado(String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoNuevo == 1) {
            nuevoContrato.getTipocotizante().setDescripcion(tipoCotizante);
        } else if (tipoNuevo == 2) {
            duplicarContrato.getTipocotizante().setDescripcion(tipoCotizante);
        }
        for (int i = 0; i < listaTiposCotizantes.size(); i++) {
            if (listaTiposCotizantes.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }
        if (coincidencias == 1) {
            if (tipoNuevo == 1) {
                nuevoContrato.setTipocotizante(listaTiposCotizantes.get(indiceUnicoElemento));
                context.update("formularioDialogos:nuevaDescripcionTipoCotizante");
            } else if (tipoNuevo == 2) {
                duplicarContrato.setTipocotizante(listaTiposCotizantes.get(indiceUnicoElemento));
                context.update("formularioDialogos:duplicarDescripcionTipoCotizante");
            }
            listaTiposCotizantes.clear();
            getListaTiposCotizantes();
        } else {
            context.update("formularioDialogos:tiposCotizantesDialogo");
            context.execute("tiposCotizantesDialogo.show()");
            tipoActualizacion = tipoNuevo;
            if (tipoNuevo == 1) {
                context.update("formularioDialogos:nuevaDescripcionTipoCotizante");
            } else if (tipoNuevo == 2) {
                context.update("formularioDialogos:duplicarDescripcionTipoCotizante");
            }
        }
    }

    public void llamarLOVNuevo_Duplicado(int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        context.update("formularioDialogos:tiposCotizantesDialogo");
        context.execute("tiposCotizantesDialogo.show()");

    }

    public void llamarDialogosLista(Integer indice) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        context.update("formularioDialogos:tiposCotizantesDialogo");
        context.execute("tiposCotizantesDialogo.show()");
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("formularioDialogos:tiposCotizantesDialogo");
                context.execute("tiposCotizantesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarContrato = listaContratos.get(index);
            }
            if (tipoLista == 1) {
                editarContrato = filtradolistaContratos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editorCodigo");
                context.execute("editorCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editorDescripcion");
                context.execute("editorDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editorTipoCotizante");
                context.execute("editorTipoCotizante.show()");
                cualCelda = -1;
            }
        }
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        index = -1;
        secRegistro = null;
    }

    public void modificarContrato(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaContratosEmpresaCrear.contains(listaContratos.get(indice))) {

                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(listaContratos.get(indice))) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaContratosEmpresaCrear.contains(filtradolistaContratos.get(indice))) {

                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(filtradolistaContratos.get(indice))) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosContratos");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOSCOTIZANTES")) {
            if (tipoLista == 0) {
                listaContratos.get(indice).getTipocotizante().setDescripcion(tipoCotizante);
            } else {
                filtradolistaContratos.get(indice).getTipocotizante().setDescripcion(tipoCotizante);
            }
            for (int i = 0; i < listaTiposCotizantes.size(); i++) {
                if (listaTiposCotizantes.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaContratos.get(indice).setTipocotizante(listaTiposCotizantes.get(indiceUnicoElemento));
                } else {
                    filtradolistaContratos.get(indice).setTipocotizante(listaTiposCotizantes.get(indiceUnicoElemento));
                }
                listaTiposCotizantes.clear();
                getListaTiposCotizantes();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposCotizantesDialogo");
                context.execute("tiposCotizantesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaContratosEmpresaCrear.contains(listaContratos.get(indice))) {

                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(listaContratos.get(indice))) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    }
                }
            } else {
                if (!listaContratosEmpresaCrear.contains(filtradolistaContratos.get(indice))) {

                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(filtradolistaContratos.get(indice))) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    }

                }
            }
            if (guardado == true) {
                guardado = false;
            }
            index = -1;
            secRegistro = null;
        }
        context.update("form:datosContratos");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            actualContrato = listaContratos.get(index);
            secRegistro = listaContratos.get(index).getSecuencia();
            if (cualCelda == 2) {
                tipoCotizante = listaContratos.get(index).getTipocotizante().getDescripcion();
            }

        }
        activoDetalleFormula = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
    }

    public void actualizarTipoCotizante() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaContratos.get(index).setTipocotizante(tipoCotizanteSeleccionado);
                if (!listaContratosEmpresaCrear.contains(listaContratos.get(index))) {
                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(listaContratos.get(index));
                    } else if (!listaContratosModificar.contains(listaContratos.get(index))) {
                        listaContratosModificar.add(listaContratos.get(index));
                    }
                }
            } else {
                filtradolistaContratos.get(index).setTipocotizante(tipoCotizanteSeleccionado);
                if (!listaContratosEmpresaCrear.contains(filtradolistaContratos.get(index))) {
                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(filtradolistaContratos.get(index));
                    } else if (!listaContratosModificar.contains(filtradolistaContratos.get(index))) {
                        listaContratosModificar.add(filtradolistaContratos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosContratos");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoContrato.setTipocotizante(tipoCotizanteSeleccionado);
            context.update("formularioDialogos:nuevaDescripcionTipoCotizante");
        } else if (tipoActualizacion == 2) {
            duplicarContrato.setTipocotizante(tipoCotizanteSeleccionado);
            context.update("formularioDialogos:duplicarDescripcionTipoCotizante");
        }
        filtradoListaTiposCotizantes = null;
        tipoCotizanteSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tiposCotizantesDialogo.hide()");
        context.reset("formularioDialogos:lovContratos:globalFilter");
        context.update("formularioDialogos:lovContratos");
    }

    public void cancelarTipoCotizante() {
        filtradoListaTiposCotizantes = null;
        tipoCotizanteSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void lovContratos(int quien) {
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        if (quien == 0) {
            if (guardado == true) {
                listaContratos = null;
                getListaContratos();
                context.update("formularioDialogos:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                verSeleccionContrato = false;
                cambioContrato = 0;
            } else {
                verSeleccionContrato = true;
                context.execute("confirmarGuardar.show()");
            }
        } else if (quien == 1) {
            listaContratoLOV = null;
            getListaContratoLOV();
            context.update("formularioDialogos:ContratosDialogo");
            context.execute("ContratosDialogo.show()");
            cambioContrato = 1;
        } else if (quien == 2) {
            listaContratoLOV = null;
            getListaContratoLOV();
            context.update("formularioDialogos:ContratosDialogo");
            context.execute("ContratosDialogo.show()");
            cambioContrato = 2;
        }
        index = -1;
        secRegistro = null;
        cualCelda = -1;
    }

    public void mostrarTodosContratos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            if (bandera == 1) {
                altoTabla = "230";
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosContratos");
                bandera = 0;
                filtradolistaContratos = null;
                tipoLista = 0;
            }
            listaContratos = null;
            mostrarTodos = true;
            verMostrarTodos = false;
            getListaContratos();
            activoDetalleFormula = false;
            context.update("form:detalleFormula");
            context.update("form:datosContratos");
            context.update("form:mostrarTodos");
        } else {
            verMostrarTodos = true;
            context.execute("confirmarGuardar.show()");
        }
        index = -1;
        secRegistro = null;
        cualCelda = -1;
    }

    public void seleccionContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambioContrato == 0) {
            if (bandera == 1) {
                altoTabla = "230";
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosContratos");
                bandera = 0;
                filtradolistaContratos = null;
                tipoLista = 0;
            }
            listaContratos.clear();
            listaContratos.add(contratoSeleccionado);
            mostrarTodos = false;
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:datosContratos");
            context.update("form:mostrarTodos");
        } else if (cambioContrato == 1) {
            contratoOriginal = contratoSeleccionado;
            context.update("form:descripcionContrato2");
        } else if (cambioContrato == 2) {
            contratoClon = contratoSeleccionado;
            context.update("form:descripcionContrato1");
        }
        filtradoListaContratosLOV = null;
        contratoSeleccionado = null;
        aceptar = true;
        context.reset("formularioDialogos:lovContratos:globalFilter");
        context.update("formularioDialogos:lovContratos");
        context.execute("ContratosDialogo.hide()");
    }

    public void cancelarSeleccionContrato() {
        filtradoListaContratosLOV = null;
        contratoSeleccionado = null;
        aceptar = true;
    }

    public void borrarContrato() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaContratosModificar.isEmpty() && listaContratosModificar.contains(listaContratos.get(index))) {
                    int modIndex = listaContratosModificar.indexOf(listaContratos.get(index));
                    listaContratosModificar.remove(modIndex);
                    listaContratosBorrar.add(listaContratos.get(index));
                } else if (!listaContratosEmpresaCrear.isEmpty() && listaContratosEmpresaCrear.contains(listaContratos.get(index))) {
                    int crearIndex = listaContratosEmpresaCrear.indexOf(listaContratos.get(index));
                    listaContratosEmpresaCrear.remove(crearIndex);
                } else {
                    listaContratosBorrar.add(listaContratos.get(index));
                }
                listaContratos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaContratosModificar.isEmpty() && listaContratosModificar.contains(filtradolistaContratos.get(index))) {
                    int modIndex = listaContratosModificar.indexOf(filtradolistaContratos.get(index));
                    listaContratosModificar.remove(modIndex);
                    listaContratosBorrar.add(filtradolistaContratos.get(index));
                } else if (!listaContratosEmpresaCrear.isEmpty() && listaContratosEmpresaCrear.contains(filtradolistaContratos.get(index))) {
                    int crearIndex = listaContratosEmpresaCrear.indexOf(filtradolistaContratos.get(index));
                    listaContratosEmpresaCrear.remove(crearIndex);
                } else {
                    listaContratosBorrar.add(filtradolistaContratos.get(index));
                }
                filtradolistaContratos.remove(index);
            }

            activoDetalleFormula = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    //GUARDAR
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            if (!listaContratosBorrar.isEmpty()) {
                administrarContratos.borrarConceptos(listaContratosBorrar);
                listaContratosBorrar.clear();
            }
            if (!listaContratosEmpresaCrear.isEmpty()) {
                administrarContratos.crearConceptos(listaContratosEmpresaCrear);
                listaContratosEmpresaCrear.clear();
            }
            if (!listaContratosModificar.isEmpty()) {
                administrarContratos.modificarConceptos(listaContratosModificar);
                listaContratosModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaContratos = null;
            activoDetalleFormula = true;
            context.update("form:detalleFormula");
            context.update("form:datosContratos");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        secRegistro = null;
        if (verSeleccionContrato == true) {
            lovContratos(0);
        }
        if (verMostrarTodos == true) {
            mostrarTodosContratos();
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            altoTabla = "205";
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("width: 20px;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("width: 250px;");
            columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaTipoCotizante");
            columnaTipoCotizante.setFilterStyle("width: 280px;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
            columnaEstado.setFilterStyle("width: 70px;");
            RequestContext.getCurrentInstance().update("form:datosContratos");
            bandera = 1;

        } else if (bandera == 1) {
            altoTabla = "230";
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaTipoCotizante");
            columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosContratos");
            bandera = 0;
            filtradolistaContratos = null;
            tipoLista = 0;
        }
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
    }

    public void dialogoIngresoNuevoRegistro() {
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("NuevoContratoDialogo.show()");
        context.update("form:detalleFormula");
    }

    public void agregarNuevoContrato() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoContrato.getCodigo() == null) {
            mensajeValidacion = " * Código\n";
            pasa++;
        }
        if (nuevoContrato.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción\n";
            pasa++;
        }
        if (nuevoContrato.getEstado() == null) {
            mensajeValidacion = mensajeValidacion + " *Estado\n";
            pasa++;
        }
        if (pasa == 0) {
            if (bandera == 1) {
                altoTabla = "230";
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosContratos");
                bandera = 0;
                filtradolistaContratos = null;
                tipoLista = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoContrato.setSecuencia(l);
            if (nuevoContrato.getTipocotizante().getSecuencia() == null) {
                nuevoContrato.setTipocotizante(null);
            }
            listaContratosEmpresaCrear.add(nuevoContrato);
            listaContratos.add(nuevoContrato);
            nuevoContrato = new Contratos();
            context.update("form:datosContratos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoContratoDialogo.hide()");
            context.update("formularioDialogos:NuevoContratoDialogo");
            index = -1;
            secRegistro = null;

        } else {
            context.update("formularioDialogos:validacioNuevoContrato");
            context.execute("validacioNuevoContrato.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevoContrato() {
        nuevoContrato = new Contratos();
        index = -1;
        secRegistro = null;
    }

    public void duplicarRegistro() {
        if (index >= 0) {
            duplicarContrato = new Contratos();
            k++;
            l = BigInteger.valueOf(k);
            duplicarContrato.setSecuencia(l);
            if (tipoLista == 0) {
                duplicarContrato.setCodigo(listaContratos.get(index).getCodigo());
                duplicarContrato.setDescripcion(listaContratos.get(index).getDescripcion());
                duplicarContrato.setTipocotizante(listaContratos.get(index).getTipocotizante());
                duplicarContrato.setEstado(listaContratos.get(index).getEstado());
            }
            if (tipoLista == 1) {
                duplicarContrato.setCodigo(filtradolistaContratos.get(index).getCodigo());
                duplicarContrato.setDescripcion(filtradolistaContratos.get(index).getDescripcion());
                duplicarContrato.setTipocotizante(filtradolistaContratos.get(index).getTipocotizante());
                duplicarContrato.setEstado(filtradolistaContratos.get(index).getEstado());
            }
            activoDetalleFormula = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            context.update("formularioDialogos:duplicarContrato");
            context.execute("DuplicarContratoDialogo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        listaContratos.add(duplicarContrato);
        listaContratosEmpresaCrear.add(duplicarContrato);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosContratos");
        index = -1;
        secRegistro = null;
        context.execute("DuplicarContratoDialogo.hide()");
        if (guardado == true) {
            guardado = false;
        }
        if (bandera == 1) {
            altoTabla = "230";
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
            columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosContratos");
            bandera = 0;
            filtradolistaContratos = null;
            tipoLista = 0;
        }
        duplicarContrato = new Contratos();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicar() {
        duplicarContrato = new Contratos();
    }

    public void refrescar() {
        if (bandera == 1) {
            altoTabla = "230";
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaTipoCotizante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
            columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosContratos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosContratos");
            bandera = 0;
            filtradolistaContratos = null;
            tipoLista = 0;
        }
        listaContratosBorrar.clear();
        listaContratosEmpresaCrear.clear();
        listaContratosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaContratos = null;
        guardado = true;
        permitirIndex = true;
        mostrarTodos = true;
        contratoClon = new Contratos();
        contratoOriginal = new Contratos();
        if (verSeleccionContrato == true) {
            lovContratos(0);
        }
        if (verMostrarTodos == true) {
            mostrarTodosContratos();
        }
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.update("form:mostrarTodos");
        context.update("form:datosContratos");
        context.update("form:descripcionContrato1");
        context.update("form:descripcionContrato2");
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        activoDetalleFormula = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        if (!listaContratos.isEmpty()) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CONTRATOS");
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
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("CONTRATOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosContratosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ContratosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
        activoDetalleFormula = true;
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.update("form:detalleFormula");
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosContratosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ContratosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
        activoDetalleFormula = true;
        RequestContext context2 = RequestContext.getCurrentInstance();
        context2.update("form:detalleFormula");
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //CLONAR
    public void reproducirContrato() {
        if (contratoClon.getSecuencia() != null && contratoOriginal.getSecuencia() != null) {
            administrarContratos.reproducirContrato(contratoOriginal.getCodigo(), contratoClon.getCodigo());
            contratoClon = new Contratos();
            contratoOriginal = new Contratos();
            listaContratos = null;
            getListaContratos();
            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Información", "Reproducción completada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosContratos");
            context.update("form:descripcionContrato1");
            context.update("form:descripcionContrato2");
        }
    }

    //GETTER AND SETTER
    public List<Contratos> getListaContratos() {
        if (listaContratos == null) {
            listaContratos = administrarContratos.consultarContratos();
        }
        return listaContratos;
    }

    public void setListaContratos(List<Contratos> listaContratos) {
        this.listaContratos = listaContratos;
    }

    public List<Contratos> getFiltradolistaContratos() {
        return filtradolistaContratos;
    }

    public void setFiltradolistaContratos(List<Contratos> filtradolistaContratos) {
        this.filtradolistaContratos = filtradolistaContratos;
    }

    public List<Contratos> getListaContratoLOV() {
        if (listaContratoLOV == null) {
            listaContratoLOV = administrarContratos.consultarContratos();
        }
        return listaContratoLOV;
    }

    public void setListaContratoLOV(List<Contratos> listaContratoLOV) {
        this.listaContratoLOV = listaContratoLOV;
    }

    public List<Contratos> getFiltradoListaContratosLOV() {
        return filtradoListaContratosLOV;
    }

    public void setFiltradoListaContratosLOV(List<Contratos> filtradoListaContratosLOV) {
        this.filtradoListaContratosLOV = filtradoListaContratosLOV;
    }

    public Contratos getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contratos contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public boolean isMostrarTodos() {
        return mostrarTodos;
    }

    public void setMostrarTodos(boolean mostrarTodos) {
        this.mostrarTodos = mostrarTodos;
    }

    public List<TiposCotizantes> getListaTiposCotizantes() {
        if (listaTiposCotizantes.isEmpty()) {
            listaTiposCotizantes = administrarContratos.consultaLOVTiposCotizantes();
        }
        return listaTiposCotizantes;
    }

    public void setListaTiposCotizantes(List<TiposCotizantes> listaTiposCotizantes) {
        this.listaTiposCotizantes = listaTiposCotizantes;
    }

    public TiposCotizantes getTipoCotizanteSeleccionado() {
        return tipoCotizanteSeleccionado;
    }

    public void setTipoCotizanteSeleccionado(TiposCotizantes tipoCotizanteSeleccionado) {
        this.tipoCotizanteSeleccionado = tipoCotizanteSeleccionado;
    }

    public List<TiposCotizantes> getFiltradoListaTiposCotizantes() {
        return filtradoListaTiposCotizantes;
    }

    public void setFiltradoListaTiposCotizantes(List<TiposCotizantes> filtradoListaTiposCotizantes) {
        this.filtradoListaTiposCotizantes = filtradoListaTiposCotizantes;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public Contratos getNuevoContrato() {
        return nuevoContrato;
    }

    public void setNuevoContrato(Contratos nuevoContrato) {
        this.nuevoContrato = nuevoContrato;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public Contratos getEditarContrato() {
        return editarContrato;
    }

    public void setEditarContrato(Contratos editarContrato) {
        this.editarContrato = editarContrato;
    }

    public Contratos getDuplicarContrato() {
        return duplicarContrato;
    }

    public void setDuplicarContrato(Contratos duplicarContrato) {
        this.duplicarContrato = duplicarContrato;
    }

    public Contratos getContratoOriginal() {
        return contratoOriginal;
    }

    public void setContratoOriginal(Contratos contratoOriginal) {
        this.contratoOriginal = contratoOriginal;
    }

    public Contratos getContratoClon() {
        return contratoClon;
    }

    public void setContratoClon(Contratos contratoClon) {
        this.contratoClon = contratoClon;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public boolean isActivoDetalleFormula() {
        return activoDetalleFormula;
    }

    public void setActivoDetalleFormula(boolean activoDetalleFormula) {
        this.activoDetalleFormula = activoDetalleFormula;
    }

    public Contratos getActualContrato() {
        return actualContrato;
    }

    public void setActualContrato(Contratos actualContrato) {
        this.actualContrato = actualContrato;
    }

}

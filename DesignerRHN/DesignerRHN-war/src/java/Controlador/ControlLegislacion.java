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
    private Contratos contratoTablaSeleccionado;

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
    private boolean guardado;
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
    //
    private String infoRegistro;
    private String infoRegistroTipo, infoRegistroContrato;

    public ControlLegislacion() {
        contratoTablaSeleccionado = new Contratos();
        actualContrato = new Contratos();
        activoDetalleFormula = true;
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
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
        listaContratos = null;
        getListaContratos();
        if (listaContratos != null) {
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
    }

    public void dispararDialogoConfirmarGuardar() {
        if (!listaContratosModificar.isEmpty()) {
            for (int i = 0; i < listaContratosModificar.size(); i++) {
                System.out.println("Secuencia : " + listaContratosModificar.get(i).getSecuencia());
                System.out.println("Codigo : " + listaContratosModificar.get(i).getCodigo());
                System.out.println("Estado : " + listaContratosModificar.get(i).getEstado());
                System.out.println(" -- -- -- -- ");
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:confirmarGuardar");
        context.execute("confirmarGuardar.show()");
    }

    public String redirigir() {
        System.out.println("Primero Redirigir");
        return paginaAnterior;
    }

    public String redirigirGuardar() {
        guardarCambios();
        return paginaAnterior;
    }

    public String redirigirSalir() {
        refrescar();
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (!valorConfirmar.isEmpty()) {
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
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
        } else {
            listaTiposCotizantes.clear();
            getListaTiposCotizantes();
            if (tipoNuevo == 1) {
                nuevoContrato.setTipocotizante(new TiposCotizantes());
                context.update("formularioDialogos:nuevaDescripcionTipoCotizante");
            } else if (tipoNuevo == 2) {
                duplicarContrato.setTipocotizante(new TiposCotizantes());
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
                System.out.println("modificarContrato Estado : " + listaContratos.get(indice).getEstado());
                if (!listaContratosEmpresaCrear.contains(listaContratos.get(indice))) {
                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(listaContratos.get(indice))) {
                        listaContratosModificar.add(listaContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                System.out.println("modificarContrato Estado : " + filtradolistaContratos.get(indice).getEstado());
                if (!listaContratosEmpresaCrear.contains(filtradolistaContratos.get(indice))) {
                    if (listaContratosModificar.isEmpty()) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    } else if (!listaContratosModificar.contains(filtradolistaContratos.get(indice))) {
                        listaContratosModificar.add(filtradolistaContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOSCOTIZANTES")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                listaTiposCotizantes.clear();
                getListaTiposCotizantes();
                if (tipoLista == 0) {
                    listaContratos.get(indice).setTipocotizante(new TiposCotizantes());
                } else {
                    filtradolistaContratos.get(indice).setTipocotizante(new TiposCotizantes());
                }
                coincidencias = 1;
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
            if (tipoLista == 0) {
                actualContrato = listaContratos.get(index);
                secRegistro = listaContratos.get(index).getSecuencia();
                tipoCotizante = listaContratos.get(index).getTipocotizante().getDescripcion();
            } else {
                actualContrato = filtradolistaContratos.get(index);
                secRegistro = filtradolistaContratos.get(index).getSecuencia();
                tipoCotizante = filtradolistaContratos.get(index).getTipocotizante().getDescripcion();
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
        /*context.update("formularioDialogos:tiposCotizantesDialogo");
        context.update("formularioDialogos:lovTiposCotizantes");
        context.update("formularioDialogos:aceptarTC");*/
        context.reset("formularioDialogos:lovTiposCotizantes:globalFilter");
        context.execute("lovTiposCotizantes.clearFilters()");
        context.execute("tiposCotizantesDialogo.hide()");
    }

    public void cancelarTipoCotizante() {
        filtradoListaTiposCotizantes = null;
        tipoCotizanteSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovTiposCotizantes:globalFilter");
        context.execute("lovTiposCotizantes.clearFilters()");
        context.execute("tiposCotizantesDialogo.hide()");
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
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "230";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
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
            if (listaContratos != null) {
                infoRegistro = "Cantidad de registros : " + listaContratos.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            context.update("form:informacionRegistro");
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
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "230";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosContratos");
                bandera = 0;
                filtradolistaContratos = null;
                tipoLista = 0;
            }
            listaContratos.clear();
            listaContratos.add(contratoSeleccionado);
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
            context.update("form:informacionRegistro");
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
        aceptar = true;/*
        context.update("formularioDialogos:ContratosDialogo");
        context.update("formularioDialogos:lovContratos");
        context.update("formularioDialogos:aceptarC");*/
        context.reset("formularioDialogos:lovContratos:globalFilter");
        context.execute("lovContratos.clearFilters()");
        context.execute("ContratosDialogo.hide()");
    }

    public void cancelarSeleccionContrato() {
        filtradoListaContratosLOV = null;
        contratoSeleccionado = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovContratos:globalFilter");
        context.execute("lovContratos.clearFilters()");
        context.execute("ContratosDialogo.hide()");
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
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
            context.update("form:informacionRegistro");
            context.update("form:detalleFormula");
            context.update("form:datosContratos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
    }

    //GUARDAR
    public void guardarSalir() {
        System.out.println("guardado : " + guardado);
        guardarCambios();
        //refrescar();
    }

    public void cancelarSalir() {
        refrescar();
    }

    public void guardarCambios() {
        System.out.println("Primero guardarCambios");
        System.out.println(" -- ! -- ! -- ! -- ");
        for (int i = 0; i < listaContratosModificar.size(); i++) {
            System.out.println("Secuencia : " + listaContratosModificar.get(i).getSecuencia());
            System.out.println("Codigo : " + listaContratosModificar.get(i).getCodigo());
            System.out.println("Estado : " + listaContratosModificar.get(i).getEstado());
            System.out.println(" -- ! -- ! -- ! -- ");
        }
        RequestContext context = RequestContext.getCurrentInstance();
        try {
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
                    for (int i = 0; i < listaContratosModificar.size(); i++) {
                        System.out.println("Secuencia : " + listaContratosModificar.get(i).getSecuencia());
                        System.out.println("Codigo : " + listaContratosModificar.get(i).getCodigo());
                        System.out.println("Estado : " + listaContratosModificar.get(i).getEstado());
                        System.out.println(" -- -- -- -- ");
                    }
                    administrarContratos.modificarConceptos(listaContratosModificar);
                    listaContratosModificar.clear();
                }
                listaContratos = null;
                getListaContratos();
                if (listaContratos != null) {
                    infoRegistro = "Cantidad de registros : " + listaContratos.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                activoDetalleFormula = true;
                context.update("form:detalleFormula");
                context.update("form:datosContratos");
                guardado = true;
                permitirIndex = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                k = 0;
                index = -1;
                secRegistro = null;
                if (verSeleccionContrato == true) {
                    lovContratos(0);
                }
                if (verMostrarTodos == true) {
                    mostrarTodosContratos();
                }
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambios : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "205";
            columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("width: 20px;");
            columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("width: 250px;");
            columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaTipoCotizante");
            columnaTipoCotizante.setFilterStyle("width: 280px;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
            columnaEstado.setFilterStyle("width: 70px;");
            RequestContext.getCurrentInstance().update("form:datosContratos");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "230";
            columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaTipoCotizante");
            columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
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
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "230";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
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
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
            context.update("form:informacionRegistro");
            context.update("form:datosContratos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
            context.update("formularioDialogos:duplicarContrato");
            context.execute("DuplicarContratoDialogo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarContrato.getCodigo() == null) {
            mensajeValidacion = " * Código\n";
            pasa++;
        }
        if (duplicarContrato.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción\n";
            pasa++;
        }
        if (duplicarContrato.getEstado() == null) {
            mensajeValidacion = mensajeValidacion + " *Estado\n";
            pasa++;
        }
        if (pasa == 0) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarContrato.setSecuencia(l);
            listaContratos.add(duplicarContrato);
            listaContratosEmpresaCrear.add(duplicarContrato);
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
            context.update("form:informacionRegistro");
            context.update("form:datosContratos");
            index = -1;
            secRegistro = null;
            context.execute("DuplicarContratoDialogo.hide()");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "230";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaNaturaleza");
                columnaTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosContratos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosContratos");
                bandera = 0;
                filtradolistaContratos = null;
                tipoLista = 0;
            }
            duplicarContrato = new Contratos();
        } else {
            context.update("formularioDialogos:validacioNuevoContrato");
            context.execute("validacioNuevoContrato.show()");
        }
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
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
        getListaContratos();
        if (listaContratos != null) {
            infoRegistro = "Cantidad de registros : " + listaContratos.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        context.update("form:informacionRegistro");
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        infoRegistro = "Cantidad de registros : " + filtradolistaContratos.size();
        context.update("form:informacionRegistro");
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
            if (listaContratos != null) {
                infoRegistro = "Cantidad de registros : " + listaContratos.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");
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
            if (listaContratos != null) {
                for (int i = 0; i < listaContratos.size(); i++) {
                    if (listaContratos.get(i).getTipocotizante() == null) {
                        listaContratos.get(i).setTipocotizante(new TiposCotizantes());
                    }
                }
            }
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
        listaContratoLOV = administrarContratos.consultarContratos();
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
        listaTiposCotizantes = administrarContratos.consultaLOVTiposCotizantes();
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

    public Contratos getContratoTablaSeleccionado() {
        getListaContratos();
        if (listaContratos != null) {
            int tam = listaContratos.size();
            if (tam > 0) {
                contratoTablaSeleccionado = listaContratos.get(0);
            }
        }
        return contratoTablaSeleccionado;
    }

    public void setContratoTablaSeleccionado(Contratos contratoTablaSeleccionado) {
        this.contratoTablaSeleccionado = contratoTablaSeleccionado;
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

    public String getInfoRegistroTipo() {
        getListaTiposCotizantes();
        if (listaTiposCotizantes != null) {
            infoRegistroTipo = "Cantidad de registros : " + listaTiposCotizantes.size();
        } else {
            infoRegistroTipo = "Cantidad de registros : 0";
        }
        return infoRegistroTipo;
    }

    public void setInfoRegistroTipo(String infoRegistroTipo) {
        this.infoRegistroTipo = infoRegistroTipo;
    }

    public String getInfoRegistroContrato() {
        getListaContratoLOV();
        if (listaContratoLOV != null) {
            infoRegistroContrato = "Cantidad de registros : " + listaContratoLOV.size();
        } else {
            infoRegistroContrato = "Cantidad de registros : 0";
        }
        return infoRegistroContrato;
    }

    public void setInfoRegistroContrato(String infoRegistroContrato) {
        this.infoRegistroContrato = infoRegistroContrato;
    }

}

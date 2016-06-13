/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCentroCostosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlBetaCentrosCostos implements Serializable {

    @EJB
    AdministrarCentroCostosInterface administrarCentroCostos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
//EMPRESA
    private List<Empresas> lovEmpresas;
    private List<Empresas> filtradoListaEmpresas;
    private Empresas empresaSeleccionada;
    private int banderaModificacionEmpresa;
    private int indiceEmpresaMostrada;
//LISTA CENTRO COSTO
    private List<CentrosCostos> listCentrosCostosPorEmpresa;
    private List<CentrosCostos> lovCentrosCostosPorEmpresa;
    private List<CentrosCostos> filtrarCentrosCostos;
    private List<CentrosCostos> crearCentrosCostos;
    private List<CentrosCostos> modificarCentrosCostos;
    private List<CentrosCostos> borrarCentrosCostos;
    private CentrosCostos nuevoCentroCosto;
    private CentrosCostos duplicarCentroCosto;
    private CentrosCostos editarCentroCosto;
    private CentrosCostos centroCostoSeleccionado;
    private Column codigoCC, nombreCentroCosto,
            tipoCentroCosto, manoDeObra, codigoAT,
            obsoleto, codigoCTT, dimensiones;
    //AUTOCOMPLETAR
    private String grupoTipoCentroCostoAutocompletar;
    private List<TiposCentrosCostos> lovTiposCentrosCostos;
    private List<TiposCentrosCostos> filtradoTiposCentrosCostos;
    private TiposCentrosCostos tipoCentroCostoSeleccionado;
    private List<CentrosCostos> filtrarCentrosCostosLOV;
    private String nuevoTipoCCAutoCompletar;
    private CentrosCostos centrosCostosLovSeleccionado;
    private boolean banderaSeleccionCentrosCostosPorEmpresa;
    private int altoTabla;
    // lovs
    private String infoRegistro;
    private String infoRegistroLOVS;
    private boolean activarLOV;
    // Otros //
    private String paginaAnterior;
    private boolean buscarCentrocosto;
    private boolean mostrartodos;
    private DataTable tabla2;
    private BigInteger contadorComprobantesContables;
    private BigInteger contadorDetallesCCConsolidador;
    private BigInteger contadorEmpresas;
    private BigInteger contadorEstructuras;
    private BigInteger contadorDetallesCCDetalle;
    private BigInteger contadorInterconCondor;
    private BigInteger contadorInterconDynamics;
    private BigInteger contadorInterconGeneral;
    private BigInteger contadorInterconHelisa;
    private BigInteger contadorInterconSapbo;
    private BigInteger contadorInterconSiigo;
    private BigInteger contadorInterconTotal;
    private BigInteger contadorNovedadesD;
    private BigInteger contadorNovedadesC;
    private BigInteger contadorProcesosProductivos;
    private BigInteger contadorProyecciones;
    private BigInteger contadorSolucionesNodosC;
    private BigInteger contadorSolucionesNodosD;
    private BigInteger contadorSoPanoramas;
    private BigInteger contadorTerceros;
    private BigInteger contadorUnidadesRegistradas;
    private BigInteger contadorVigenciasCuentasC;
    private BigInteger contadorVigenciasCuentasD;
    private BigInteger contadorVigenciasProrrateos;

    public ControlBetaCentrosCostos() {
        permitirIndex = true;
        lovEmpresas = null;
        empresaSeleccionada = null;
        indiceEmpresaMostrada = 0;
        listCentrosCostosPorEmpresa = null;
        lovCentrosCostosPorEmpresa = null;
        crearCentrosCostos = new ArrayList<CentrosCostos>();
        modificarCentrosCostos = new ArrayList<CentrosCostos>();
        borrarCentrosCostos = new ArrayList<CentrosCostos>();
        editarCentroCosto = new CentrosCostos();
        nuevoCentroCosto = new CentrosCostos();
        duplicarCentroCosto = new CentrosCostos();
        lovTiposCentrosCostos = null;
        aceptar = true;
        filtradoListaEmpresas = null;
        guardado = true;
        banderaSeleccionCentrosCostosPorEmpresa = false;
        altoTabla = 285;
        buscarCentrocosto = false;
        mostrartodos = true;
        activarLOV = true;
        cualCelda = -1;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarCentroCostos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPagina(String pagina) {
        paginaAnterior = pagina;
        getLovEmpresas();
        if (!lovEmpresas.isEmpty()) {
            empresaSeleccionada = lovEmpresas.get(0);
        }
        getListCentrosCostosPorEmpresa();
        contarRegistros();
        if (listCentrosCostosPorEmpresa != null) {
            if (!listCentrosCostosPorEmpresa.isEmpty()) {
                centroCostoSeleccionado = listCentrosCostosPorEmpresa.get(0);
            }
        }
    }

    public String redirigirPaginaAnterior() {
        return paginaAnterior;
    }

    public void cambiarIndice(CentrosCostos centroCosto, int celda) {
        System.out.println("cambiarIndice():");
        System.err.println("cualCeldaEntra = " + cualCelda);
        if (centroCostoSeleccionado != null) {
            System.err.println("centroCostoSeleccionado = " + centroCostoSeleccionado.getNombre());
        } else {
            System.err.println("centroCostoSeleccionado = NULL");
        }
        System.err.println("centroCosto quedo : " + centroCosto.getNombre());
        centroCostoSeleccionado = centroCosto;
        cualCelda = celda;
        System.err.println("cualCelda quedo : " + cualCelda + "\n");

        if (permitirIndex == true) {
            if (cualCelda == 2) {
                activarLOV = false;
                RequestContext.getCurrentInstance().update("form:listaValores");
                grupoTipoCentroCostoAutocompletar = centroCostoSeleccionado.getTipocentrocosto().getNombre();
            } else {
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
            }
        }
    }

    public void cambiarIndiceDefault() {
        System.out.println("cambiarIndiceDefault");
        System.err.println("cualCelda = " + cualCelda);
        cualCelda = -1;
        System.err.println("cualCelda quedo = " + cualCelda);
        System.err.println("centroCostoSeleccionado = " + centroCostoSeleccionado.getNombre());
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void modificandoCentroCosto(CentrosCostos centroCosto, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR CENTROCOSTO");
        centroCostoSeleccionado = centroCosto;
        banderaModificacionEmpresa = 1;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        boolean banderita = false;
        boolean banderita1 = false;
        int contador = 0;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {

            if (!crearCentrosCostos.contains(centroCostoSeleccionado)) {
                if (centroCostoSeleccionado.getCodigo().isEmpty()) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                } else if (centroCostoSeleccionado.getCodigo().equals(" ")) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                } else {
                    for (int j = 0; j < listCentrosCostosPorEmpresa.size(); j++) {
                        if (j != listCentrosCostosPorEmpresa.indexOf(centroCostoSeleccionado)) {
                            if (centroCostoSeleccionado.getCodigo().equals(listCentrosCostosPorEmpresa.get(j).getCodigo())) {
                                contador++;
                            }
                        }
                    }
                    if (contador > 0) {
                        mensajeValidacion = "CODIGOS REPETIDOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }
                }

                if (centroCostoSeleccionado.getNombre().isEmpty()) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita1 = false;
                } else if (centroCostoSeleccionado.getNombre().equals(" ")) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita1 = false;
                } else {
                    banderita1 = true;
                }

                if (banderita == true && banderita1 == true) {
                    if (modificarCentrosCostos.isEmpty()) {
                        modificarCentrosCostos.add(centroCostoSeleccionado);
                    } else if (!modificarCentrosCostos.contains(centroCostoSeleccionado)) {
                        modificarCentrosCostos.add(centroCostoSeleccionado);
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                    context.update("form:ACEPTAR");
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            }

            context.update("form:datosCentrosCostos");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOCENTROCOSTO")) {
            System.out.println("ENTRE A MODIFICAR TIPO CENTRO COSTO, CONFIRMAR CAMBIO ES TIPOCENTROCOSTO");
            System.out.println("grupoTipoCentroCostoAutocompletar " + grupoTipoCentroCostoAutocompletar);
            System.out.println("centroCostoSeleccionado.getTipocentrocosto().getNombre() " + centroCostoSeleccionado.getTipocentrocosto().getNombre());

            centroCostoSeleccionado.getTipocentrocosto().setNombre(grupoTipoCentroCostoAutocompletar);

            for (int i = 0; i < lovTiposCentrosCostos.size(); i++) {
                if (lovTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                centroCostoSeleccionado.setTipocentrocosto(lovTiposCentrosCostos.get(indiceUnicoElemento));
            } else {
                permitirIndex = false;
                context.update("form:tiposCentrosCostosDialogo");
                context.execute("tiposCentrosCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        context.update("form:datosCentrosCostos");
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void cancelarModificacion() {
        try {
            System.out.println("entre a CONTROLBETACENTROSCOSTOS.cancelarModificacion");
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                //0
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                //7 
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = 285;
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }

            borrarCentrosCostos.clear();
            crearCentrosCostos.clear();
            modificarCentrosCostos.clear();
            k = 0;
            listCentrosCostosPorEmpresa = null;
            guardado = true;
            permitirIndex = true;
            buscarCentrocosto = false;
            mostrartodos = true;
            RequestContext context = RequestContext.getCurrentInstance();
            banderaModificacionEmpresa = 0;
            if (banderaModificacionEmpresa == 0) {
                cambiarEmpresa();
            }
            centroCostoSeleccionado = null;
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            getListCentrosCostosPorEmpresa();
            contarRegistros();
            context.update("form:informacionRegistro");
            context.update("form:datosCentrosCostos");
            context.update("form:ACEPTAR");
            context.update("form:BUSCARCENTROCOSTO");
            context.update("form:MOSTRARTODOS");
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.ModificarModificacion ERROR====================" + E.getMessage());
        }
    }

    public void salir() {
        try {
            System.out.println("entre a CONTROLBETACENTROSCOSTOS.Salir");
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = 285;
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            borrarCentrosCostos.clear();
            crearCentrosCostos.clear();
            modificarCentrosCostos.clear();
            centroCostoSeleccionado = null;
            k = 0;
            listCentrosCostosPorEmpresa = null;
            guardado = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");
            context.update("form:ACEPTAR");
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.ModificarModificacion ERROR====================" + E.getMessage());
        }
    }

    public void mostrarInfo(CentrosCostos centroC, int celda) {
        centroCostoSeleccionado = centroC;
        if (permitirIndex == true) {
            RequestContext context = RequestContext.getCurrentInstance();

            banderaModificacionEmpresa = 1;
            cualCelda = celda;
            if (cualCelda == 3) {
                if (centroCostoSeleccionado.getVariableManoObra().equals("DIRECTA")) {
                    centroCostoSeleccionado.setManoobra("D");
                } else if (centroCostoSeleccionado.getVariableManoObra().equals("INDIRECTA")) {
                    centroCostoSeleccionado.setManoobra("I");
                } else if (centroCostoSeleccionado.getVariableManoObra().equals(" ")) {
                    centroCostoSeleccionado.setManoobra(null);
                }
            } else if (cualCelda == 5) {
                System.out.println("OBSOLETO " + centroCostoSeleccionado.getObsoleto());
                if (centroCostoSeleccionado.getVariableObsoleto().equals("SI")) {
                    centroCostoSeleccionado.setObsoleto("S");
                } else if (centroCostoSeleccionado.getVariableObsoleto().equals("NO")) {
                    centroCostoSeleccionado.setObsoleto("N");
                } else if (centroCostoSeleccionado.getVariableObsoleto().equals(" ")) {
                    centroCostoSeleccionado.setObsoleto(null);
                }
            } else if (cualCelda == 7) {
                System.out.println("DIMENSIONES  " + centroCostoSeleccionado.getDimensiones());
            }
            if (!crearCentrosCostos.contains(centroCostoSeleccionado)) {

                if (modificarCentrosCostos.isEmpty()) {
                    modificarCentrosCostos.add(centroCostoSeleccionado);
                } else if (!modificarCentrosCostos.contains(centroCostoSeleccionado)) {
                    modificarCentrosCostos.add(centroCostoSeleccionado);
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            context.update("form:datosCentrosCostos");
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        }
    }

    public void asignarIndex(CentrosCostos centroCosto, int LND, int dig) {
        centroCostoSeleccionado = centroCosto;
        try {
            tipoActualizacion = LND;

            if (dig == 2) {
                activarLOV = false;
                modificarInfoRegistroLOVS(lovTiposCentrosCostos.size());
                RequestContext.getCurrentInstance().update("form:listaValores");
                RequestContext.getCurrentInstance().update("form:infoRegistroTcc");
                RequestContext.getCurrentInstance().update("form:tiposCentrosCostosDialogo");
                RequestContext.getCurrentInstance().execute("tiposCentrosCostosDialogo.show()");
                dig = -1;
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarCentroCosto() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarCentroCosto \n");
        try {
            banderaModificacionEmpresa = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarCentroCosto TIPOACTUALIZACION====" + tipoActualizacion);
            if (tipoActualizacion == 0) {
                centroCostoSeleccionado.setTipocentrocosto(tipoCentroCostoSeleccionado);
                if (!crearCentrosCostos.contains(centroCostoSeleccionado)) {
                    if (modificarCentrosCostos.isEmpty()) {
                        modificarCentrosCostos.add(centroCostoSeleccionado);
                    } else if (!modificarCentrosCostos.contains(centroCostoSeleccionado)) {
                        modificarCentrosCostos.add(centroCostoSeleccionado);
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                context.update("form:datosCentrosCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            } else if (tipoActualizacion == 1) {
                context.reset("formularioDialogos:nuevaTipoCentroCostos");
                System.out.println("Entro actualizar centro costo nuevo rgistro");
                nuevoCentroCosto.setTipocentrocosto(tipoCentroCostoSeleccionado);
                System.out.println("Centro Costo Seleccionado: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
                context.update("formularioDialogos:nuevaTipoCentroCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            } else if (tipoActualizacion == 2) {
                duplicarCentroCosto.setTipocentrocosto(tipoCentroCostoSeleccionado);
                context.update("formularioDialogos:duplicarTipoCentroCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            }
            filtradoTiposCentrosCostos = null;
            tipoCentroCostoSeleccionado = null;
            aceptar = true;
            tipoActualizacion = -1;
            permitirIndex = true;

            context.reset("form:lovTipoCentrosCostos:globalFilter");
            context.execute("lovTipoCentrosCostos.clearFilters()");
            context.update("form:tiposCentrosCostosDialogo");
            context.update("form:lovTipoCentrosCostos");
            context.update("form:aceptarTCC");
            context.execute("tiposCentrosCostosDialogo.hide()");

        } catch (Exception e) {
            System.out.println("ERROR BETA .actualizarCentroCosto ERROR============" + e.getMessage());
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void cancelarCambioTiposCentroCosto() {
        try {
            filtradoTiposCentrosCostos = null;
            tipoCentroCostoSeleccionado = null;
            aceptar = true;
            tipoActualizacion = -1;
            RequestContext context = RequestContext.getCurrentInstance();

            context.reset("form:lovTipoCentrosCostos:globalFilter");
            context.execute("lovTipoCentrosCostos.clearFilters()");
            context.update("form:tiposCentrosCostosDialogo");
            context.update("form:lovTipoCentrosCostos");
            context.update("form:aceptarTCC");
            context.execute("tiposCentrosCostosDialogo.hide()");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarCambioCentroCosto ERROR=====" + e.getMessage());
        }
    }

    public void llamadoDialogoBuscarCentrosCostos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                banderaSeleccionCentrosCostosPorEmpresa = true;
                context.execute("confirmarGuardar.show()");
            } else {
                modificarInfoRegistroLOVS(lovCentrosCostosPorEmpresa.size());
                RequestContext.getCurrentInstance().update("form:infoRegistroCc");
                context.update("form:lovCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.show()");
            }
        } catch (Exception e) {
            System.err.println("ERROR LLAMADO DIALOGO BUSCAR CENTROS COSTOS " + e);
        }
    }

    public void seleccionCentrosCostosPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            if (guardado == true) {
                listCentrosCostosPorEmpresa.clear();
                System.err.println("seleccionCentrosCostosPorEmpresa " + centrosCostosLovSeleccionado.getNombre());
                listCentrosCostosPorEmpresa.add(centrosCostosLovSeleccionado);
                centroCostoSeleccionado = centrosCostosLovSeleccionado;
                System.err.println("listCentrosCostosPorEmpresa tamaño " + listCentrosCostosPorEmpresa.size());
                System.err.println("listCentrosCostosPorEmpresa nombre " + listCentrosCostosPorEmpresa.get(0).getNombre());
                centrosCostosLovSeleccionado = null;
                filtrarCentrosCostosLOV = null;
                aceptar = true;
                banderaModificacionEmpresa = 1;
                buscarCentrocosto = true;
                mostrartodos = false;
                contarRegistros();

                context.reset("form:lovCentrosCostos:globalFilter");
                context.execute("lovCentrosCostos.clearFilters()");
                context.update("form:buscarCentrosCostosDialogo");
                context.update("form:lovCentrosCostos");
                context.update("form:aceptarNCC");
                context.execute("buscarCentrosCostosDialogo.hide()");

                context.update("form:informacionRegistro");
                context.update("form:datosCentrosCostos");
                context.update("form:BUSCARCENTROCOSTO");
                context.update("form:MOSTRARTODOS");
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.seleccionaVigencia ERROR====" + e.getMessage());
        }
    }

    public void cancelarSeleccionCentroCostoPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            centrosCostosLovSeleccionado = null;
            filtrarCentrosCostosLOV = null;
            aceptar = true;
            tipoActualizacion = -1;
            context.update("form:aceptarNCC");
            context.reset("form:lovCentrosCostos:globalFilter");
            context.execute("lovCentrosCostos.clearFilters()");
            context.update("form:buscarCentrosCostosDialogo");
            context.update("form:lovCentrosCostos");
            context.update("form:aceptarNCC");
            context.execute("buscarCentrosCostosDialogo.hide()");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarSeleccionVigencia ERROR====" + e.getMessage());
        }
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        System.out.println("1...");
        if (Campo.equals("TIPOSCENTROSCOSTOS")) {
            if (tipoNuevo == 1) {
                nuevoTipoCCAutoCompletar = nuevoCentroCosto.getTipocentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoTipoCCAutoCompletar = duplicarCentroCosto.getTipocentrocosto().getNombre();
            }

        }
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSCENTROSCOSTOS")) {
            System.out.println(" nuevoTipoCentroCosto    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoCentroCosto.getTipocentrocosto().getNombre());

            if (!nuevoCentroCosto.getTipocentrocosto().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                nuevoCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                getLovTiposCentrosCostos();
                for (int i = 0; i < lovTiposCentrosCostos.size(); i++) {
                    if (lovTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoCentroCosto.setTipocentrocosto(lovTiposCentrosCostos.get(indiceUnicoElemento));
                    lovTiposCentrosCostos = null;
                    getLovTiposCentrosCostos();
                } else {
                    context.update("form:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
                nuevoCentroCosto.getTipocentrocosto().setNombre(" ");
                System.out.println("NUEVO Valor nombre tcc: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
            }
            context.update("form:nuevoGrupoTipoCC");
        }

    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSCENTROSCOSTOS")) {
            System.out.println("Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + duplicarCentroCosto.getTipocentrocosto().getNombre());

            if (!duplicarCentroCosto.getTipocentrocosto().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                duplicarCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                for (int i = 0; i < lovTiposCentrosCostos.size(); i++) {
                    if (lovTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarCentroCosto.setTipocentrocosto(lovTiposCentrosCostos.get(indiceUnicoElemento));
                    lovTiposCentrosCostos = null;
                    getLovTiposCentrosCostos();
                } else {
                    context.update("form:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                duplicarCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                duplicarCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
                duplicarCentroCosto.getTipocentrocosto().setNombre(" ");
                System.out.println("Valor nombre tcc: " + duplicarCentroCosto.getTipocentrocosto().getNombre());
            }
            context.update("form:duplicarTipoCentroCostos");
        }
    }

    public void asignarVariableTiposCC(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        modificarInfoRegistroLOVS(lovTiposCentrosCostos.size());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:infoRegistroTcc");
        context.update("form:tiposCentrosCostosDialogo");
        context.execute("tiposCentrosCostosDialogo.show()");
    }

    public void limpiarNuevoCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarNuevoCentroCostos \n");
        try {
            nuevoCentroCosto = new CentrosCostos();
            nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
        } catch (Exception e) {
            System.out.println("Error CONTROLBETACENTROSCOSTOS.LimpiarNuevoCentroCostos ERROR=============================" + e.getMessage());
        }
    }

    public void agregarNuevoCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.agregarNuevoCentroCostos \n");
        try {
            int contador = 0;
            mensajeValidacion = " ";
            int duplicados = 0;
            RequestContext context = RequestContext.getCurrentInstance();

            banderaModificacionEmpresa = 1;
            if (nuevoCentroCosto.getCodigo() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoCentroCosto.getCodigo().isEmpty()) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoCentroCosto.getCodigo().equals(" ")) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";

            } else {
                System.out.println("codigo en Motivo Cambio Cargo: " + nuevoCentroCosto.getCodigo());

                for (int x = 0; x < listCentrosCostosPorEmpresa.size(); x++) {
                    if (listCentrosCostosPorEmpresa.get(x).getCodigo().equals(nuevoCentroCosto.getCodigo())) {
                        duplicados++;
                    }
                }
                System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

                if (duplicados > 0) {
                    mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                    System.out.println("Mensaje validacion : " + mensajeValidacion);
                } else {
                    System.out.println("bandera");
                    contador++;
                }
            }
            if (nuevoCentroCosto.getNombre() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoCentroCosto.getNombre().isEmpty()) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoCentroCosto.getNombre().equals(" ")) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";

            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (nuevoCentroCosto.getTipocentrocosto().getSecuencia() == null) {
                mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (nuevoCentroCosto.getObsoleto() == null) {
                nuevoCentroCosto.setObsoleto("N");
            }
            if (contador == 3) {
                k++;
                l = BigInteger.valueOf(k);
                nuevoCentroCosto.setSecuencia(l);
                nuevoCentroCosto.setComodin("N");
                nuevoCentroCosto.setEmpresa(empresaSeleccionada);
                if (crearCentrosCostos.contains(nuevoCentroCosto)) {
                } else {
                    crearCentrosCostos.add(nuevoCentroCosto);
                }
                listCentrosCostosPorEmpresa.add(nuevoCentroCosto);
                centroCostoSeleccionado = listCentrosCostosPorEmpresa.get(listCentrosCostosPorEmpresa.indexOf(nuevoCentroCosto));
                modificarInfoRegistro(listCentrosCostosPorEmpresa.size());
                if (tipoLista == 1) {
                    altoTabla = 285;
                }
                context.update("form:datosCentrosCostos");
                nuevoCentroCosto = new CentrosCostos();
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                if (bandera == 1) {
                    FacesContext c = FacesContext.getCurrentInstance();
                    codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                    codigoCC.setFilterStyle("display: none; visibility: hidden;");
                    nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                    nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                    tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                    manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                    codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                    codigoAT.setFilterStyle("display: none; visibility: hidden;");
                    obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                    obsoleto.setFilterStyle("display: none; visibility: hidden;");
                    codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                    codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                    dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                    dimensiones.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = 285;
                    RequestContext.getCurrentInstance().update("form:datosCentrosCostos");

                    bandera = 0;
                    filtrarCentrosCostos = null;
                    tipoLista = 0;
                }
                mensajeValidacion = " ";
                context.update("form:datosTipoReemplazo");
                RequestContext.getCurrentInstance().execute("NuevoRegistroCentroCostos.hide()");
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
            } else {
                contador = 0;
                context.update("form:validacionDuplicarVigencia");
                context.execute("validacionDuplicarVigencia.show()");
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.agregarNuevoCentroCostos ERROR===========================" + e.getMessage());
        }
    }

    public void cargarTiposCentrosCostosNuevoRegistro(int tipoNuevo) {
        modificarInfoRegistroLOVS(lovTiposCentrosCostos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroTcc");
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposCentrosCostosDialogo");
            context.execute("tiposCentrosCostosDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposCentrosCostosDialogo");
            context.execute("tiposCentrosCostosDialogo.show()");
        }
    }

    public void mostrarDialogoNuevoTiposCentrosCostos() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoCentroCosto = new CentrosCostos();
        nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
        context.update("form:nuevoCentroCostos");
        context.execute("NuevoRegistroCentroCostos.show()");
    }

    public void mostrarDialogoListaEmpresas() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("buscarCentrosCostosDialogo.show()");
    }

    public void duplicandoCentroCostos() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            banderaModificacionEmpresa = 1;

            if (centroCostoSeleccionado != null) {
                System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.duplicarCentroCostos TIPOLISTA===" + tipoLista);

                duplicarCentroCosto = new CentrosCostos();
                k++;
                l = BigInteger.valueOf(k);
                duplicarCentroCosto.setSecuencia(l);
                duplicarCentroCosto.setEmpresa(centroCostoSeleccionado.getEmpresa());
                duplicarCentroCosto.setCodigo(centroCostoSeleccionado.getCodigo());
                duplicarCentroCosto.setNombre(centroCostoSeleccionado.getNombre());
                duplicarCentroCosto.setTipocentrocosto(centroCostoSeleccionado.getTipocentrocosto());
                duplicarCentroCosto.setManoobra(centroCostoSeleccionado.getManoobra());
                duplicarCentroCosto.setCodigoalternativo(centroCostoSeleccionado.getCodigoalternativo());
                duplicarCentroCosto.setObsoleto(centroCostoSeleccionado.getObsoleto());
                duplicarCentroCosto.setCodigoctt(centroCostoSeleccionado.getCodigoctt());
                duplicarCentroCosto.setDimensiones(centroCostoSeleccionado.getDimensiones());
                duplicarCentroCosto.setComodin(centroCostoSeleccionado.getComodin());

                context.update("formularioDialogos:duplicarCentroCostos");
                context.execute("DuplicarRegistroCentroCostos.show()");
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.DuplicarCentroCostos ERROR===============" + e.getMessage());
        }
    }

    public void limpiarDuplicarCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarDuplicarCentroCostos \n");
        try {
            duplicarCentroCosto = new CentrosCostos();
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.limpiarDuplicarCentroCostos ERROR========" + e.getMessage());
        }

    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLTIPOSCENTROSCOSTOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;

        if (duplicarCentroCosto.getCodigo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarCentroCosto.getCodigo().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un codigo \n";

        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + duplicarCentroCosto.getCodigo());

            for (int x = 0; x < listCentrosCostosPorEmpresa.size(); x++) {
                if (listCentrosCostosPorEmpresa.get(x).getCodigo().equals(duplicarCentroCosto.getCodigo())) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }

        }
        if (duplicarCentroCosto.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarCentroCosto.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarCentroCosto.getTipocentrocosto().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarCentroCosto.getManoobra() == null) {
            System.out.println("Al duplicar la mano de obra ya es nula");
        } else if (duplicarCentroCosto.getManoobra().isEmpty()) {
            duplicarCentroCosto.setManoobra(null);
        }

        if (contador == 3) {
            System.err.println("DUPLICAR MANO DE OBRA " + duplicarCentroCosto.getManoobra());

            System.err.println("DUPLICAR OBSOLETO " + duplicarCentroCosto.getObsoleto());
            System.err.println("DUPLICAR DIMENSIONES " + duplicarCentroCosto.getDimensiones());
            if (crearCentrosCostos.contains(duplicarCentroCosto)) {
                System.out.println("Ya lo contengo.");
            } else {
                crearCentrosCostos.add(duplicarCentroCosto);
                listCentrosCostosPorEmpresa.add(duplicarCentroCosto);
            }
            centroCostoSeleccionado = listCentrosCostosPorEmpresa.get(listCentrosCostosPorEmpresa.indexOf(duplicarCentroCosto));
            modificarInfoRegistro(listCentrosCostosPorEmpresa.size());
            if (tipoLista == 1) {
                altoTabla = 285;
            }
            context.update("form:datosCentrosCostos");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                //6
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = 285;
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }

            duplicarCentroCosto = new CentrosCostos();
            duplicarCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
            context.update("form:datosTipoReemplazo");
            context.update("form:informacionRegistro");
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            RequestContext.getCurrentInstance().execute("DuplicarRegistroCentroCostos.hide()");
            mensajeValidacion = " ";
            banderaModificacionEmpresa = 1;

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        System.out.println("TIPOLISTA = " + tipoLista);
        BigInteger pruebilla;
        try {
            contadorComprobantesContables = administrarCentroCostos.contadorComprobantesContables(centroCostoSeleccionado.getSecuencia());
            contadorDetallesCCConsolidador = administrarCentroCostos.contadorDetallesCCConsolidador(centroCostoSeleccionado.getSecuencia());;
            contadorEmpresas = administrarCentroCostos.contadorEmpresas(centroCostoSeleccionado.getSecuencia());
            contadorEstructuras = administrarCentroCostos.contadorEstructuras(centroCostoSeleccionado.getSecuencia());
            contadorDetallesCCDetalle = administrarCentroCostos.contadorDetalleContable(centroCostoSeleccionado.getSecuencia());
            contadorInterconCondor = administrarCentroCostos.contadorInterConCondor(centroCostoSeleccionado.getSecuencia());
            contadorInterconDynamics = administrarCentroCostos.contadorInterConDynamics(centroCostoSeleccionado.getSecuencia());
            contadorInterconGeneral = administrarCentroCostos.contadorInterConGeneral(centroCostoSeleccionado.getSecuencia());
            contadorInterconHelisa = administrarCentroCostos.contadorInterConHelisa(centroCostoSeleccionado.getSecuencia());
            contadorInterconSapbo = administrarCentroCostos.contadorInterConSapbo(centroCostoSeleccionado.getSecuencia());
            contadorInterconSiigo = administrarCentroCostos.contadorInterConSiigo(centroCostoSeleccionado.getSecuencia());
            contadorInterconTotal = administrarCentroCostos.contadorInterConTotal(centroCostoSeleccionado.getSecuencia());
            contadorNovedadesD = administrarCentroCostos.contadorNovedadesD(centroCostoSeleccionado.getSecuencia());
            contadorNovedadesC = administrarCentroCostos.contadorNovedadesC(centroCostoSeleccionado.getSecuencia());
            contadorProcesosProductivos = administrarCentroCostos.contadorProcesosProductivos(centroCostoSeleccionado.getSecuencia());
            contadorProyecciones = administrarCentroCostos.contadorProyecciones(centroCostoSeleccionado.getSecuencia());
            contadorSolucionesNodosC = administrarCentroCostos.contadorSolucionesNodosC(centroCostoSeleccionado.getSecuencia());
            contadorSolucionesNodosD = administrarCentroCostos.contadorSolucionesNodosD(centroCostoSeleccionado.getSecuencia());
            contadorSoPanoramas = administrarCentroCostos.contadorSoPanoramas(centroCostoSeleccionado.getSecuencia());
            contadorTerceros = administrarCentroCostos.contadorTerceros(centroCostoSeleccionado.getSecuencia());
            contadorUnidadesRegistradas = administrarCentroCostos.contadorUnidadesRegistradas(centroCostoSeleccionado.getSecuencia());
            contadorVigenciasCuentasC = administrarCentroCostos.contadorVigenciasCuentasC(centroCostoSeleccionado.getSecuencia());
            contadorVigenciasCuentasD = administrarCentroCostos.contadorVigenciasCuentasD(centroCostoSeleccionado.getSecuencia());
            contadorVigenciasProrrateos = administrarCentroCostos.contadorVigenciasProrrateos(centroCostoSeleccionado.getSecuencia());

            if (contadorDetallesCCConsolidador.equals(new BigInteger("0"))
                    && contadorEmpresas.equals(new BigInteger("0"))
                    && contadorEstructuras.equals(new BigInteger("0"))
                    && contadorDetallesCCDetalle.equals(new BigInteger("0"))
                    && contadorInterconCondor.equals(new BigInteger("0"))
                    && contadorInterconDynamics.equals(new BigInteger("0"))
                    && contadorInterconGeneral.equals(new BigInteger("0"))
                    && contadorInterconHelisa.equals(new BigInteger("0"))
                    && contadorInterconSapbo.equals(new BigInteger("0"))
                    && contadorInterconSiigo.equals(new BigInteger("0"))
                    && contadorInterconTotal.equals(new BigInteger("0"))
                    && contadorNovedadesD.equals(new BigInteger("0"))
                    && contadorNovedadesC.equals(new BigInteger("0"))
                    && contadorProcesosProductivos.equals(new BigInteger("0"))
                    && contadorProyecciones.equals(new BigInteger("0"))
                    && contadorSolucionesNodosC.equals(new BigInteger("0"))
                    && contadorSolucionesNodosD.equals(new BigInteger("0"))
                    && contadorSoPanoramas.equals(new BigInteger("0"))
                    && contadorTerceros.equals(new BigInteger("0"))
                    && contadorUnidadesRegistradas.equals(new BigInteger("0"))
                    && contadorVigenciasCuentasC.equals(new BigInteger("0"))
                    && contadorVigenciasCuentasD.equals(new BigInteger("0"))
                    && contadorVigenciasProrrateos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoCentroCosto();
            } else {

                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                centroCostoSeleccionado = null;
                contadorComprobantesContables = new BigInteger("-1");
                contadorDetallesCCConsolidador = new BigInteger("-1");
                contadorEmpresas = new BigInteger("-1");
                contadorEstructuras = new BigInteger("-1");
                contadorDetallesCCDetalle = new BigInteger("-1");
                contadorInterconCondor = new BigInteger("-1");
                contadorInterconDynamics = new BigInteger("-1");
                contadorInterconGeneral = new BigInteger("-1");
                contadorInterconHelisa = new BigInteger("-1");
                contadorInterconSapbo = new BigInteger("-1");
                contadorInterconSiigo = new BigInteger("-1");
                contadorInterconTotal = new BigInteger("-1");
                contadorNovedadesD = new BigInteger("-1");
                contadorNovedadesC = new BigInteger("-1");
                contadorProcesosProductivos = new BigInteger("-1");
                contadorProyecciones = new BigInteger("-1");
                contadorSolucionesNodosC = new BigInteger("-1");
                contadorSolucionesNodosD = new BigInteger("-1");
                contadorSoPanoramas = new BigInteger("-1");
                contadorTerceros = new BigInteger("-1");
                contadorUnidadesRegistradas = new BigInteger("-1");
                contadorVigenciasCuentasC = new BigInteger("-1");
                contadorVigenciasCuentasD = new BigInteger("-1");
                contadorVigenciasProrrateos = new BigInteger("-1");
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        } catch (Exception e) {
            System.err.println("ERROR CONTROL BETA CENTROS COSTOS verificarBorrado ERROR " + e);
        }
    }

    public void borrandoCentroCosto() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            banderaModificacionEmpresa = 1;
            if (centroCostoSeleccionado != null) {
                if (!modificarCentrosCostos.isEmpty() && modificarCentrosCostos.contains(centroCostoSeleccionado)) {
                    int modIndex = modificarCentrosCostos.indexOf(centroCostoSeleccionado);
                    modificarCentrosCostos.remove(modIndex);
                    borrarCentrosCostos.add(centroCostoSeleccionado);
                } else if (!crearCentrosCostos.isEmpty() && crearCentrosCostos.contains(centroCostoSeleccionado)) {
                    int crearIndex = crearCentrosCostos.indexOf(centroCostoSeleccionado);
                    crearCentrosCostos.remove(crearIndex);
                } else {
                    borrarCentrosCostos.add(centroCostoSeleccionado);
                }
                listCentrosCostosPorEmpresa.remove(centroCostoSeleccionado);
                modificarInfoRegistro(listCentrosCostosPorEmpresa.size());
                if (tipoLista == 1) {
                    filtrarCentrosCostos.remove(centroCostoSeleccionado);
                    modificarInfoRegistro(filtrarCentrosCostos.size());
                }
                centroCostoSeleccionado = null;
                System.err.println("verificar Borrado " + guardado);
                if (guardado == true) {
                    guardado = false;
                }
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                context.update("form:datosCentrosCostos");
            } else {
                context.execute("seleccionarRegistro.show()");
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.BorrarCentroCosto ERROR=====================" + e.getMessage());
        }
    }

    public void guardarCambiosCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!borrarCentrosCostos.isEmpty()) {
                administrarCentroCostos.borrarCentroCostos(borrarCentrosCostos);
                //mostrarBorrados
                registrosBorrados = borrarCentrosCostos.size();
                borrarCentrosCostos.clear();
            }
            if (!crearCentrosCostos.isEmpty()) {
                administrarCentroCostos.crearCentroCostos(crearCentrosCostos);
                crearCentrosCostos.clear();
            }
            if (!modificarCentrosCostos.isEmpty()) {
                administrarCentroCostos.modificarCentroCostos(modificarCentrosCostos);
                modificarCentrosCostos.clear();
            }
            context.update("form:datosTipoCentroCosto");
            k = 0;
            guardado = true;

            if (banderaModificacionEmpresa == 0) {
                cambiarEmpresa();
                banderaModificacionEmpresa = 1;
            }
            if (banderaSeleccionCentrosCostosPorEmpresa == true) {
                lovCentrosCostosPorEmpresa = null;
                getLovCentrosCostosPorEmpresa();
                context.update("form:lovCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.show()");
                banderaSeleccionCentrosCostosPorEmpresa = false;
            }
        }
        contarRegistros();
        FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        banderaModificacionEmpresa = 0;
    }

    public void cancelarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaModificacionEmpresa == 0) {
            context.update("form:lovEmpresas");
            banderaModificacionEmpresa = 1;
        }

    }

    public void activarCtrlF11() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.activarCtrlF11 \n");

        try {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 0) {
                altoTabla = 261;
                System.out.println("Activar");
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("width: 85%");
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("width: 85%");
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("width: 85%");
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("width: 85%");
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("width: 85%");
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("width: 85%");
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("width: 85%");
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("width: 85%");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 1;
            } else if (bandera == 1) {
                System.out.println("Desactivar");
                //0
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = 285;
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        } catch (Exception e) {

            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.activarCtrlF11 ERROR====================" + e.getMessage());
        }
    }

//--------------------------------------------------------------------------
    //METODOS MANIPULAR EMPRESA MOSTRADA
    //--------------------------------------------------------------------------
    public void cambiarEmpresaSeleccionada(int updown) {
        try {
            if (banderaModificacionEmpresa == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarCambioEmpresa.show()");
            } else if (banderaModificacionEmpresa == 0) {
                for (int i = 0; i < lovEmpresas.size(); i++) {
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresa: " + i + " nombre: " + lovEmpresas.get(i).getNombre());
                }
                System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Entra a cambiar la empresa seleccionada");
                int temp = indiceEmpresaMostrada;
                System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: temp = " + temp);
                if (updown == 1) {
                    temp--;
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Arriba_ temp = " + temp + " lista: " + lovEmpresas.size());
                    if (temp >= 0 && temp < lovEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getLovEmpresas().get(indiceEmpresaMostrada);
                        getLovCentrosCostosPorEmpresa();
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("form:buscarCentrosCostosDialogo");
                    }
                } else {
                    temp++;
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Abajo_ temp = " + temp + " lista: " + lovEmpresas.size());
                    if (temp >= 0 && temp < lovEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getLovEmpresas().get(indiceEmpresaMostrada);
                        getLovCentrosCostosPorEmpresa();
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("form:buscarCentrosCostosDialogo");
                    }

                }
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada ERROR======" + e.getMessage());
        }

    }

    public void editarCelda() {
        try {
            if (centroCostoSeleccionado != null) {
                editarCentroCosto = centroCostoSeleccionado;

                RequestContext context = RequestContext.getCurrentInstance();
                System.out.println("CONTROLBETACENTROSCOSTOS: Entro a editar... valor celda: " + cualCelda);
                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarCCC");
                    context.execute("editarCCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarNCC");
                    context.execute("editarNCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarTCC");
                    context.execute("editarTCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 3) {
                    context.update("formularioDialogos:editarMO");
                    context.execute("editarMO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 4) {
                    context.update("formularioDialogos:editarCAT");
                    context.execute("editarCAT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 5) {
                    context.update("formularioDialogos:editarO");
                    context.execute("editarO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 6) {
                    context.update("formularioDialogos:editarCCTT");
                    context.execute("editarCCTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 7) {
                    context.update("formularioDialogos:editarD");
                    context.execute("editarD.show()");
                    cualCelda = -1;
                }
            }
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.editarCelDa ERROR=====================" + E.getMessage());
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (centroCostoSeleccionado != null) {
                if (cualCelda == 2) {
                    modificarInfoRegistroLOVS(lovTiposCentrosCostos.size());
                    RequestContext.getCurrentInstance().update("form:infoRegistroTcc");
                    context.update("form:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } catch (Exception e) {
            System.out.println("\n ERROR CONTROLBETACENTROSCOSTOS.listaValoresBoton ERROR====================" + e.getMessage());

        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CentroCostos", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     *
     * @throws IOException
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CentroCostos", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (centroCostoSeleccionado != null) {
            int resultado = administrarRastros.obtenerTabla(centroCostoSeleccionado.getSecuencia(), "CENTROSCOSTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
            System.out.println("resultado: " + resultado);
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
            if (administrarRastros.verificarHistoricosTabla("CENTROSCOSTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    public void lovEmpresas() {
        cualCelda = -1;
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        modificarInfoRegistroLOVS(lovEmpresas.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroEmp");
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("Cambiar empresa  GUARDADO = " + guardado);
        System.err.println("Cambiar empresa  GUARDADO = " + empresaSeleccionada.getNombre());
        if (guardado == true) {
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            getListCentrosCostosPorEmpresa();
            getLovCentrosCostosPorEmpresa();
            filtradoListaEmpresas = null;
            listCentrosCostosPorEmpresa = null;
            aceptar = true;
            context.reset("form:lovEmpresas:globalFilter");
            context.execute("lovEmpresas.clearFilters()");
            context.update("form:EmpresasDialogo");
            context.update("form:lovEmpresas");
            context.update("form:aceptarE");
            context.execute("EmpresasDialogo.hide()");

            context.update("form:lovEmpresas");
            banderaModificacionEmpresa = 0;
            context.update("form:datosCentrosCostos");
        } else {
            banderaModificacionEmpresa = 0;
            context.execute("confirmarGuardar.show()");
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void cancelarCambioEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoListaEmpresas = null;
        banderaModificacionEmpresa = 0;
        context.reset("form:lovEmpresas:globalFilter");
        context.execute("lovEmpresas.clearFilters()");
        context.update("form:EmpresasDialogo");
        context.update("form:lovEmpresas");
        context.update("form:aceptarE");
        context.execute("EmpresasDialogo.hide()");
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void posicionOtro() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node 
        String type = map.get("t"); // type attribute of node 
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        centroCostoSeleccionado = listCentrosCostosPorEmpresa.get(indice);
        cambiarIndice(centroCostoSeleccionado, columna);
    }

    public void recordarSeleccion() {
        if (centroCostoSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla2 = (DataTable) c.getViewRoot().findComponent("form:datosCentrosCostos");
            tabla2.setSelection(centroCostoSeleccionado);
        }
    }

    // FILTRADOS // 
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        modificarInfoRegistro(filtrarCentrosCostos.size());
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    public void eventoFiltrarCC() {
        modificarInfoRegistroLOVS(filtrarCentrosCostosLOV.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroCc");
    }

    public void eventoFiltrarTCC() {
        modificarInfoRegistroLOVS(filtradoTiposCentrosCostos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroTcc");
    }

    public void eventoFiltrarEmp() {
        modificarInfoRegistroLOVS(filtradoListaEmpresas.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroEmp");
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    private void modificarInfoRegistroLOVS(int valor) {
        infoRegistroLOVS = String.valueOf(valor);
    }

    public void contarRegistros() {
        if (listCentrosCostosPorEmpresa != null) {
            modificarInfoRegistro(listCentrosCostosPorEmpresa.size());
        } else {
            modificarInfoRegistro(0);
        }
    }
//-----------------------------------------------------------------------------**

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public List<Empresas> getLovEmpresas() {
        try {
            if (lovEmpresas == null) {
                lovEmpresas = administrarCentroCostos.buscarEmpresas();
            }
            return lovEmpresas;
        } catch (Exception e) {
            System.out.println("ERRO LISTA EMPRESAS " + e);
            return null;
        }
    }

    public void setLovEmpresas(List<Empresas> listaEmpresas) {
        this.lovEmpresas = listaEmpresas;
    }

    public List<Empresas> getFiltradoListaEmpresas() {
        return filtradoListaEmpresas;
    }

    public void setFiltradoListaEmpresas(List<Empresas> filtradoListaEmpresas) {
        this.filtradoListaEmpresas = filtradoListaEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public List<CentrosCostos> getListCentrosCostosPorEmpresa() {
        try {
            if (listCentrosCostosPorEmpresa == null) {
                listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
            }
            return listCentrosCostosPorEmpresa;
        } catch (Exception e) {
            System.out.println(" BETA  BETA ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }

    public List<CentrosCostos> getLovCentrosCostosPorEmpresa() {
        try {
            if (lovCentrosCostosPorEmpresa == null) {
                if (listCentrosCostosPorEmpresa != null) {
                    lovCentrosCostosPorEmpresa = new ArrayList<CentrosCostos>();
                }
                for (int i = 0; i < listCentrosCostosPorEmpresa.size(); i++) {
                    lovCentrosCostosPorEmpresa.add(listCentrosCostosPorEmpresa.get(i));
                }
            }
            return lovCentrosCostosPorEmpresa;
        } catch (Exception e) {
            System.out.println("ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }

    public void setLovCentrosCostosPorEmpresa(List<CentrosCostos> listCentrosCostosPorEmpresaBoton) {
        this.lovCentrosCostosPorEmpresa = listCentrosCostosPorEmpresaBoton;
    }

    public void setListCentrosCostosPorEmpresa(List<CentrosCostos> listCentrosCostosPorEmpresa) {
        this.listCentrosCostosPorEmpresa = listCentrosCostosPorEmpresa;
    }

    public List<CentrosCostos> getFiltrarCentrosCostos() {
        return filtrarCentrosCostos;
    }

    public void setFiltrarCentrosCostos(List<CentrosCostos> filtrarCentrosCostos) {
        this.filtrarCentrosCostos = filtrarCentrosCostos;
    }

    public CentrosCostos getNuevoCentroCosto() {
        return nuevoCentroCosto;
    }

    public void setNuevoCentroCosto(CentrosCostos nuevoCentroCosto) {
        this.nuevoCentroCosto = nuevoCentroCosto;
    }

    public CentrosCostos getDuplicarCentroCosto() {
        return duplicarCentroCosto;
    }

    public void setDuplicarCentroCosto(CentrosCostos duplicarCentroCosto) {
        this.duplicarCentroCosto = duplicarCentroCosto;
    }

    public List<TiposCentrosCostos> getLovTiposCentrosCostos() {
        if (lovTiposCentrosCostos == null) {
            lovTiposCentrosCostos = administrarCentroCostos.lovTiposCentrosCostos();
        }
        return lovTiposCentrosCostos;
    }

    public void setLovTiposCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        this.lovTiposCentrosCostos = listaTiposCentrosCostos;
    }

    public List<TiposCentrosCostos> getFiltradoTiposCentrosCostos() {
        return filtradoTiposCentrosCostos;
    }

    public void setFiltradoTiposCentrosCostos(List<TiposCentrosCostos> filtradoTiposCentrosCostos) {
        this.filtradoTiposCentrosCostos = filtradoTiposCentrosCostos;
    }

    public TiposCentrosCostos getTipoCentroCostoSeleccionado() {
        return tipoCentroCostoSeleccionado;
    }

    public void setTipoCentroCostoSeleccionado(TiposCentrosCostos tipoCentroCostoSeleccionado) {
        this.tipoCentroCostoSeleccionado = tipoCentroCostoSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<CentrosCostos> getFiltrarCentrosCostosLOV() {
        return filtrarCentrosCostosLOV;
    }

    public void setFiltrarCentrosCostosLOV(List<CentrosCostos> filterCentrosCostosPorEmpresa) {
        this.filtrarCentrosCostosLOV = filterCentrosCostosPorEmpresa;
    }

    public CentrosCostos getCentrosCostosLovSeleccionado() {
        return centrosCostosLovSeleccionado;
    }

    public void setCentrosCostosLovSeleccionado(CentrosCostos CentrosCostosPorEmpresaSeleccionado) {
        this.centrosCostosLovSeleccionado = CentrosCostosPorEmpresaSeleccionado;
    }

    public CentrosCostos getEditarCentroCosto() {
        return editarCentroCosto;
    }

    public void setEditarCentroCosto(CentrosCostos editarCentroCosto) {
        this.editarCentroCosto = editarCentroCosto;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos centroCostoBetaSeleccionado) {
        this.centroCostoSeleccionado = centroCostoBetaSeleccionado;
    }

    public int getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(int tamano) {
        this.altoTabla = tamano;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getInfoRegistroLOVS() {
        return infoRegistroLOVS;
    }

    public boolean isBuscarCentrocosto() {
        return buscarCentrocosto;
    }

    public void setBuscarCentrocosto(boolean buscarCentrocosto) {
        this.buscarCentrocosto = buscarCentrocosto;
    }

    public boolean isMostrartodos() {
        return mostrartodos;
    }

    public void setMostrartodos(boolean mostrartodos) {
        this.mostrartodos = mostrartodos;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposCotizantes;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasDiasTT;
import Entidades.VigenciasTiposTrabajadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposCotizantesInterface;
import InterfaceAdministrar.AdministrarTiposTrabajadoresInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.export.Exporter;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlTiposTrabajadores implements Serializable {

    @EJB
    AdministrarTiposCotizantesInterface administrarTiposCotizantes;
    @EJB
    AdministrarTiposTrabajadoresInterface administrarTiposTrabajadores;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Lista Tipos Trabajadores
    private List<TiposTrabajadores> listaTiposTrabajadores;
    private List<TiposTrabajadores> filtrarTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    //Vigencias Dias TT
    private List<VigenciasDiasTT> listaVigenciasDiasTT;
    private List<VigenciasDiasTT> filtrarVigenciasDiasTT;
    private VigenciasDiasTT vigenciaDiaSeleccionado;
    //Listas valores
    private List<TiposCotizantes> lovTiposCotizantes;
    private List<TiposCotizantes> filtrarLovTiposCotizantes;
    private TiposCotizantes tipoCotizanteLovSeleccionado;
    private List<TiposTrabajadores> lovTiposTrabajadores;
    private List<TiposTrabajadores> filtrarLovTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorLovSeleccionado;
    //Columnas Tabla Tipos Trabajadores
    private Column tTCodigo, tTNombre, ttFactorD, ttTipoCot, ttDiasVacNoOrd, ttDiasVac,
            tTModalidad, tTTipo, tTNivel, tTBase, tTPor, tTMPS, tTPatronPS, tTPatronPP, tTPatronPR, tTCesCC,
            VDTdias, VDTFecha;
    //Variables de control
    private int tipoActualizacion;
    private int bandera;
    private boolean aceptar;
    private boolean guardado;
    private int cualCelda, tipoLista, tipoListaVD, cualCeldaVD;
    public String infoRegistroTT, infoRegistroTC, infoRegistroVD, infoRegistroLovTT;
    private String altoTabla, altoTablaVD;
    //modificar
    private List<TiposTrabajadores> listTTModificar;
    //crear
    private TiposTrabajadores nuevoTipoT;
    private List<TiposTrabajadores> listTTCrear;
    private BigInteger nuevaSecuencia;
    private int intNuevaSec;
    //borrar
    private List<TiposTrabajadores> listTTBorrar;
    //editar celda
    private TiposTrabajadores editarTT;
    //duplicar
    private TiposTrabajadores duplicarTT;
    //Vigencias Dias
    private List<VigenciasDiasTT> listVDModificar;
    private List<VigenciasDiasTT> listVDCrear;
    private List<VigenciasDiasTT> listVDBorrar;
    private VigenciasDiasTT nuevaVD;
    private VigenciasDiasTT duplicarVD;
    private VigenciasDiasTT editarVD;
    //Backs
    private TiposCotizantes tipoCotizanteBack;
    private String nombreTTBack;
    private Short codigoBack;
    private Date fechaVDBack;
    //pagina anterior
    private String paginaAnterior;
    //Activar boton lista Valor
    private boolean activarLOV;
    //Para Recordar Seleccion
    private DataTable tablaTT, tablaVD;
//    private Map<BigInteger, String> mapaModalidades;
    private boolean mostrartodos;
    //RASTROS
    private String nombreTablaRastro, msnConfirmarRastro, msnConfirmarRastroHistorico;
    //
    private String permitirCambioBotonLov;
    private int tablaActiva;
    //CLONAR
    private String nombreNuevoClonado;
    private Short codigoNuevoClonado;
    private TiposTrabajadores tipoTrabajadorAClonar;

    /**
     * Creates a new instance of ControlTiposTrabajadores
     */
    public ControlTiposTrabajadores() {
        //Lista Tipos Trabajadores
        listaTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        listaVigenciasDiasTT = null;
        //Lista Tipos Cotizantes
        lovTiposCotizantes = null;
        tipoCotizanteLovSeleccionado = null;
        //Variables de control
        tipoActualizacion = -1;
        aceptar = true;
        guardado = true;
        cualCelda = -1;
        tipoLista = 0;
        infoRegistroTT = "";
        infoRegistroTC = "";
        altoTabla = "180";
        altoTablaVD = "50";
        //modificar
        listTTModificar = new ArrayList<TiposTrabajadores>();
        //crear
        nuevoTipoT = new TiposTrabajadores();
        listTTCrear = new ArrayList<TiposTrabajadores>();
        intNuevaSec = 0;
        //borrar
        listTTBorrar = new ArrayList<TiposTrabajadores>();
        //editar celda
        editarTT = new TiposTrabajadores();
        //duplicar
        duplicarTT = new TiposTrabajadores();
        paginaAnterior = "";
        activarLOV = true;
        tipoCotizanteBack = new TiposCotizantes();
        fechaVDBack = new Date();
//      mapaModalidades = new LinkedHashMap<BigInteger, String>();
        mostrartodos = true;
        lovTiposTrabajadores = null;

        listVDModificar = new ArrayList<VigenciasDiasTT>();
        listVDCrear = new ArrayList<VigenciasDiasTT>();
        listVDBorrar = new ArrayList<VigenciasDiasTT>();
        nuevaVD = new VigenciasDiasTT();
        duplicarVD = new VigenciasDiasTT();
        editarVD = new VigenciasDiasTT();
        permitirCambioBotonLov = "SIapagarCelda";
        tablaActiva = 0;
        tipoTrabajadorAClonar = new TiposTrabajadores();
        nombreNuevoClonado = "";
        codigoNuevoClonado = new Short("0");
        nombreTTBack = "";
        codigoBack = new Short("0");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposTrabajadores.obtenerConexion(ses.getId());
            administrarTiposCotizantes.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
        listaTiposTrabajadores = null;
        listaVigenciasDiasTT = null;
        getListaTiposTrabajadores();
        if (listaTiposTrabajadores != null) {
            if (!listaTiposTrabajadores.isEmpty()) {
                tipoTrabajadorSeleccionado = listaTiposTrabajadores.get(0);
                listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
                System.out.println("cambiarIndice. listaVigenciasDiasTT : " + listaVigenciasDiasTT);
            }
        }
        contarRegistrosTT();
        contarRegistrosVD();
    }

    public String retornarPagina() {
        return paginaAnterior;
    }

    public void cambiarIndice(TiposTrabajadores tipoTrabajador, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoTrabajadorSeleccionado = tipoTrabajador;
        tipoActualizacion = 0;
        cualCelda = celda;
        listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
        if (cualCelda == 3) {
            permitirCambioBotonLov = "NOapagarCelda";
            tipoCotizanteBack = tipoTrabajadorSeleccionado.getTipocotizante();
            activarBotonLOV();
        } else {
            permitirCambioBotonLov = "SoloHacerNull";
            anularBotonLOV();
        }
        if (cualCelda == 0) {
            codigoBack = tipoTrabajadorSeleccionado.getCodigo();
        } else if (cualCelda == 1) {
            nombreTTBack = tipoTrabajadorSeleccionado.getNombre();
        }
        contarRegistrosVD();
        vigenciaDiaSeleccionado = null;
        context.update("form:datosVigenciasDTT");
        tablaActiva = 1;
    }

    public void cambiarIndiceDefault() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirCambioBotonLov.equals("SoloHacerNull")) {
            anularBotonLOV();
        } else if (permitirCambioBotonLov.equals("SIapagarCelda")) {
            anularBotonLOV();
            cualCelda = -1;
        } else if (permitirCambioBotonLov.equals("NOapagarCelda")) {
            activarBotonLOV();
        }
        listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
        contarRegistrosVD();
        permitirCambioBotonLov = "SIapagarCelda";
        tablaActiva = 1;
        vigenciaDiaSeleccionado = null;
        System.out.println("cambiarIndiceDefault() tablaActiva: " + tablaActiva);
        context.update("form:datosVigenciasDTT");
    }

    public void cambiarIndiceVD(VigenciasDiasTT vigenciaDia, int celda) {
        System.out.println("cambiarIndiceVD celda: " + celda);
        vigenciaDiaSeleccionado = vigenciaDia;
        tipoActualizacion = 0;
        cualCeldaVD = celda;

        if (cualCeldaVD == 1) {
            fechaVDBack = vigenciaDiaSeleccionado.getFechaVigencia();
        }
        anularBotonLOV();
        tablaActiva = 2;
    }

    public void cambiarIndiceVDDefault() {
        System.out.println("cambiarIndiceDefault cualCeldaVD : " + cualCeldaVD);
        cualCeldaVD = 0;
        System.out.println("cambiarIndiceDefault cualCeldaVD : " + cualCeldaVD);
        tipoActualizacion = 0;
        anularBotonLOV();
        tablaActiva = 2;
        System.out.println("cambiarIndiceVDDefault() tablaActiva: " + tablaActiva);
    }

    public void asignarIndex(TiposTrabajadores tiposTrabajador, int column) {
        tipoTrabajadorSeleccionado = tiposTrabajador;
        tipoActualizacion = 0;
        cualCelda = column;
        if (cualCelda == 3) {
            activarBotonLOV();
            contarRegistrosLovTC(0);
            RequestContext.getCurrentInstance().update("form:dialogTiposCot");
            RequestContext.getCurrentInstance().execute("dialogTiposCot.show()");
        } else {
            anularBotonLOV();
        }
        tablaActiva = 1;
    }

    public void lovTipoCotizanteNueyDup(int tipoAct) {
        tipoActualizacion = tipoAct;
        activarBotonLOV();
        contarRegistrosLovTC(0);
        RequestContext.getCurrentInstance().update("form:dialogTiposCot");
        RequestContext.getCurrentInstance().execute("dialogTiposCot.show()");
    }

    public void listaValoresBoton() {
        if (cualCelda == 3) {
            contarRegistrosLovTC(0);
            tipoActualizacion = 0;
            RequestContext.getCurrentInstance().update("form:dialogTiposCot");
            RequestContext.getCurrentInstance().execute("dialogTiposCot.show()");
        }
    }

    public void modificarTT(TiposTrabajadores tipoTrabajador, String column, String valor) {
        tipoTrabajadorSeleccionado = tipoTrabajador;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (column.equalsIgnoreCase("TC")) {
            tipoTrabajadorSeleccionado.setTipocotizante(tipoCotizanteBack);

            for (int i = 0; i < lovTiposCotizantes.size(); i++) {
                if (lovTiposCotizantes.get(i).getDescripcion().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoTrabajadorSeleccionado.setTipocotizante(lovTiposCotizantes.get(indiceUnicoElemento));
            } else {
                context.update("form:dialogTiposCot");
                context.execute("dialogTiposCot.show()");
                tipoActualizacion = 0;
            }
            if (coincidencias == 1) {
                modificarTT(tipoTrabajador);
            }
        }
        if (column.equalsIgnoreCase("COD")) {
            System.out.println("modificarTT COD valor : " + valor);
            tipoTrabajadorSeleccionado.setCodigo(codigoBack);
            Short cod = new Short(valor);
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getCodigo() == cod) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias > 0) {
                context.update("form:errorClonadoRepetido");
                context.execute("errorClonadoRepetido.show()");
                tipoActualizacion = 0;
            } else {
                tipoTrabajadorSeleccionado.setCodigo(cod);
                modificarTT(tipoTrabajador);
            }
        }
        if (column.equalsIgnoreCase("NOM")) {
            System.out.println("modificarTT NOM valor : " + valor);
            tipoTrabajadorSeleccionado.setNombre(nombreTTBack);
//            llenarLOVTT();
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias > 0) {
                context.update("form:errorClonadoRepetido");
                context.execute("errorClonadoRepetido.show()");
            } else {
                tipoTrabajadorSeleccionado.setNombre(valor);
//                if (coincidencias == 1) {
                modificarTT(tipoTrabajador);
//                }
            }
            tipoActualizacion = 0;
        }
        activarBotonLOV();
        context.update("form:datosTTrabajadores");
    }

    public void modificarTT(TiposTrabajadores tipoTrabajador) {
        System.out.println("Entro en el evento Change()");
        tipoTrabajadorSeleccionado = tipoTrabajador;
        if (!listTTCrear.contains(tipoTrabajadorSeleccionado)) {
            if (listTTModificar.isEmpty()) {
                listTTModificar.add(tipoTrabajadorSeleccionado);
            } else if (!listTTModificar.contains(tipoTrabajadorSeleccionado)) {
                listTTModificar.add(tipoTrabajadorSeleccionado);
            }
        }
        if (guardado) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }

    public void modificarFechaVD(VigenciasDiasTT vigenciaDia) {
        vigenciaDiaSeleccionado = vigenciaDia;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        vigenciaDiaSeleccionado.setFechaVigencia(fechaVDBack);

        for (int i = 0; i < listaVigenciasDiasTT.size(); i++) {
            if (listaVigenciasDiasTT.get(i).getFechaVigencia() == vigenciaDia.getFechaVigencia()) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }
        if (coincidencias == 1) {
            context.update("form:dialogErrorFechas");
            context.execute("dialogErrorFechas.show()");
            tipoActualizacion = 0;
        } else {
            modificarVD(vigenciaDia);
        }
        activarBotonLOV();
        context.update("form:datosVigenciasDTT");
    }

    public void modificarVD(VigenciasDiasTT vigenciaDia) {
        vigenciaDiaSeleccionado = vigenciaDia;
        if (!listVDCrear.contains(vigenciaDiaSeleccionado)) {
            if (listVDModificar.isEmpty()) {
                listVDModificar.add(vigenciaDiaSeleccionado);
            } else if (!listVDModificar.contains(vigenciaDiaSeleccionado)) {
                listVDModificar.add(vigenciaDiaSeleccionado);
            }
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String column, String valor, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (column.equalsIgnoreCase("TC")) {
            if (tipoNuevo == 1) {
                nuevoTipoT.setTipocotizante(tipoCotizanteBack);
            } else if (tipoNuevo == 2) {
                duplicarTT.setTipocotizante(tipoCotizanteBack);
            }
            for (int i = 0; i < lovTiposCotizantes.size(); i++) {
                if (lovTiposCotizantes.get(i).getDescripcion().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTipoT.setTipocotizante(lovTiposCotizantes.get(indiceUnicoElemento));
                    //Tok/ context.update("formularioDialogos:Campo en dialogo nuevo");
                } else if (tipoNuevo == 2) {
                    duplicarTT.setTipocotizante(lovTiposCotizantes.get(indiceUnicoElemento));
                    //Tok/ context.update("formularioDialogos:Campo en dialogo duplicar");
                }
            } else {
                context.update("form:dialogTiposCot");
                context.execute("dialogTiposCot.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    //Tok/ context.update("formularioDialogos:Campo en dialogo nuevo");
                } else if (tipoNuevo == 2) {
                    //Tok/ context.update("formularioDialogos:Campo en dialogo duplicar");
                }
            }
        }
    }

    //LOVS
    /**
     * Metodo que actualiza la tipo cotizacion seleccionada
     */
    public void actualizarTipoCotizacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            tipoTrabajadorSeleccionado.setTipocotizante(tipoCotizanteLovSeleccionado);
            if (!listTTCrear.contains(tipoTrabajadorSeleccionado)) {
                if (listTTModificar.isEmpty()) {
                    listTTModificar.add(tipoTrabajadorSeleccionado);
                } else if (!listTTModificar.contains(tipoTrabajadorSeleccionado)) {
                    listTTModificar.add(tipoTrabajadorSeleccionado);
                }
            }

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosTTrabajadores");
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevoTipoT.setTipocotizante(tipoCotizanteLovSeleccionado);
            //Tok/ context.update("formularioDialogos:Campo en dialogo nuevo");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarTT.setTipocotizante(tipoCotizanteLovSeleccionado);
            //Tok/ context.update("formularioDialogos:Campo en dialogo duplicar");
        }
        filtrarLovTiposCotizantes = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.reset("form:lovTiposCot:globalFilter");
        context.execute("lovTiposCot.clearFilters()");
        context.execute("dialogTiposCot.hide()");
        context.update("form:dialogTiposCot");
        context.update("form:lovTiposCot");
        context.update("form:aceptarTC");
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioReformaLaboral() {
        filtrarLovTiposCotizantes = null;
        tipoCotizanteLovSeleccionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();

        context.reset("form:lovTiposCot:globalFilter");
        context.execute("lovTiposCot.clearFilters()");
        context.execute("dialogTiposCot.hide()");
        context.update("form:dialogTiposCot");
        context.update("form:lovTiposCot");
        context.update("form:aceptarTC");
    }

    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "156";
            altoTablaVD = "26";
            tTCodigo = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTCodigo");
            tTCodigo.setFilterStyle("width: 85%");
            tTNombre = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTNombre");
            tTNombre.setFilterStyle("width: 85%");
            ttFactorD = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttFactorD");
            ttFactorD.setFilterStyle("width: 85%");
            ttTipoCot = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttTipoCot");
            ttTipoCot.setFilterStyle("width: 85%");
            ttDiasVacNoOrd = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttDiasVacNoOrd");
            ttDiasVacNoOrd.setFilterStyle("width: 85%");
            ttDiasVac = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttDiasVac");
            ttDiasVac.setFilterStyle("width: 85%");
            tTModalidad = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTModalidad");
            tTModalidad.setFilterStyle("width: 85%");
            tTTipo = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTTipo");
            tTTipo.setFilterStyle("width: 85%");
            tTNivel = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTNivel");
            tTNivel.setFilterStyle("width: 85%");
            tTBase = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTBase");
            tTBase.setFilterStyle("width: 85%");
            tTPor = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPor");
            tTPor.setFilterStyle("width: 85%");
            tTMPS = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTMPS");
            tTMPS.setFilterStyle("width: 85%");
            tTPatronPS = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPS");
            tTPatronPS.setFilterStyle("width: 85%");
            tTPatronPP = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPP");
            tTPatronPP.setFilterStyle("width: 85%");
            tTPatronPR = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPR");
            tTPatronPR.setFilterStyle("width: 85%");
            tTCesCC = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTCesCC");
            tTCesCC.setFilterStyle("width: 85%");
            //Vigencias Dias TT
            VDTdias = (Column) c.getViewRoot().findComponent("form:datosVigenciasDTT:VDTdias");
            VDTdias.setFilterStyle("width: 85%");
            VDTFecha = (Column) c.getViewRoot().findComponent("form:datosVigenciasDTT:VDTFecha");
            VDTFecha.setFilterStyle("width: 85%");

            RequestContext.getCurrentInstance().update("form:datosTTrabajadores");
            RequestContext.getCurrentInstance().update("form:datosVigenciasDTT");
            bandera = 1;

        } else if (bandera == 1) {
            restaurarTablaTT();
        }
    }

    public void restaurarTablaTT() {
        FacesContext c = FacesContext.getCurrentInstance();
        altoTabla = "180";
        altoTablaVD = "50";
        tTCodigo = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTCodigo");
        tTCodigo.setFilterStyle("display: none; visibility: hidden;");
        tTNombre = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTNombre");
        tTNombre.setFilterStyle("display: none; visibility: hidden;");
        ttFactorD = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttFactorD");
        ttFactorD.setFilterStyle("display: none; visibility: hidden;");
        ttTipoCot = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttTipoCot");
        ttTipoCot.setFilterStyle("display: none; visibility: hidden;");
        ttDiasVacNoOrd = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttDiasVacNoOrd");
        ttDiasVacNoOrd.setFilterStyle("display: none; visibility: hidden;");
        ttDiasVac = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:ttDiasVac");
        ttDiasVac.setFilterStyle("display: none; visibility: hidden;");
        tTModalidad = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTModalidad");
        tTModalidad.setFilterStyle("display: none; visibility: hidden;");
        tTTipo = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTTipo");
        tTTipo.setFilterStyle("display: none; visibility: hidden;");
        tTNivel = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTNivel");
        tTNivel.setFilterStyle("display: none; visibility: hidden;");
        tTBase = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTBase");
        tTBase.setFilterStyle("display: none; visibility: hidden;");
        tTPor = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPor");
        tTPor.setFilterStyle("display: none; visibility: hidden;");
        tTMPS = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTMPS");
        tTMPS.setFilterStyle("display: none; visibility: hidden;");
        tTPatronPS = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPS");
        tTPatronPS.setFilterStyle("display: none; visibility: hidden;");
        tTPatronPP = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPP");
        tTPatronPP.setFilterStyle("display: none; visibility: hidden;");
        tTPatronPR = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTPatronPR");
        tTPatronPR.setFilterStyle("display: none; visibility: hidden;");
        tTCesCC = (Column) c.getViewRoot().findComponent("form:datosTTrabajadores:tTCesCC");
        tTCesCC.setFilterStyle("display: none; visibility: hidden;");
        //Vigencias Dias TT
        VDTdias = (Column) c.getViewRoot().findComponent("form:datosVigenciasDTT:VDTdias");
        VDTdias.setFilterStyle("display: none; visibility: hidden;");
        VDTFecha = (Column) c.getViewRoot().findComponent("form:datosVigenciasDTT:VDTFecha");
        VDTFecha.setFilterStyle("display: none; visibility: hidden;");

        RequestContext.getCurrentInstance().update("form:datosTTrabajadores");
        RequestContext.getCurrentInstance().update("form:datosVigenciasDTT");
        bandera = 0;
        filtrarTiposTrabajadores = null;
        tipoLista = 0;
    }

    //CREAR TT
    /**
     * Metodo que se encarga de agregar un nuevo TT
     */
    public void agregarNuevaTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        Short cod = new Short("0");
        if ((nuevoTipoT.getCodigo() != cod) || (nuevoTipoT.getNombre() != null)) {

            for (int j = 0; j < listaTiposTrabajadores.size(); j++) {
                if (nuevoTipoT.getCodigo() == listaTiposTrabajadores.get(j).getCodigo()) {
                    cont++;
                }
            }
            for (int j = 0; j < listaTiposTrabajadores.size(); j++) {
                if (nuevoTipoT.getNombre().equals(listaTiposTrabajadores.get(j).getNombre())) {
                    cont++;
                }
            }
            if (cont > 0) {
                context.update("form:validacionNuevo");
                context.execute("validacionNuevo.show()");
            } else {
                if (bandera == 1) {
                    restaurarTablaTT();
                }
                intNuevaSec++;
                nuevaSecuencia = BigInteger.valueOf(intNuevaSec);
                nuevoTipoT.setSecuencia(nuevaSecuencia);
                listTTCrear.add(nuevoTipoT);
                listaTiposTrabajadores.add(nuevoTipoT);
                perderSeleccionTT();
                tipoTrabajadorSeleccionado = listaTiposTrabajadores.get(listaTiposTrabajadores.indexOf(nuevoTipoT));

                anularBotonLOV();
                contarRegistrosTT();
                listaVigenciasDiasTT = null;
                nuevoTipoT = new TiposTrabajadores();
                nuevoTipoT.setTipocotizante(new TiposCotizantes());

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.update("form:datosTTrabajadores");
                context.execute("NuevoRegistroTT.hide()");
            }
        } else {
            context.execute("errorNewRegNulos.show()");
        }
    }
//LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaTT() {
        nuevoTipoT = new TiposTrabajadores();
        nuevoTipoT.setTipocotizante(new TiposCotizantes());
    }
    //DUPLICAR TT

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTrabajadorSeleccionado == null && vigenciaDiaSeleccionado == null) {
            context.execute("seleccionarRegistro.show()");
        } else if (tablaActiva == 2 && vigenciaDiaSeleccionado != null) {
            duplicarVD = new VigenciasDiasTT();
            intNuevaSec++;
            nuevaSecuencia = BigInteger.valueOf(intNuevaSec);

            duplicarVD.setCodigo(vigenciaDiaSeleccionado.getCodigo());
            duplicarVD.setDias(vigenciaDiaSeleccionado.getDias());
            duplicarVD.setFechaVigencia(vigenciaDiaSeleccionado.getFechaVigencia());
            duplicarVD.setTipoTrabajador(vigenciaDiaSeleccionado.getTipoTrabajador());
            duplicarVD.setSecuencia(nuevaSecuencia);
            System.out.println("duplicarTT abrir duplicarRegistroVD");
            context.update("formularioDialogos:duplicarRegistroVD");
            context.execute("duplicarRegistroVD.show()");

        } else if (tablaActiva == 1 && tipoTrabajadorSeleccionado != null) {
            duplicarTT = new TiposTrabajadores();
            intNuevaSec++;
            nuevaSecuencia = BigInteger.valueOf(intNuevaSec);

            duplicarTT.setBaseendeudamiento(tipoTrabajadorSeleccionado.getBaseendeudamiento());
            duplicarTT.setCesantiasectorconstruccion(tipoTrabajadorSeleccionado.getCesantiasectorconstruccion());
            duplicarTT.setCodigo(tipoTrabajadorSeleccionado.getCodigo());
            duplicarTT.setDerechodiasvacaciones(tipoTrabajadorSeleccionado.getDerechodiasvacaciones());
            duplicarTT.setDiasvacacionesnoordinarios(tipoTrabajadorSeleccionado.getDiasvacacionesnoordinarios());
            duplicarTT.setFactordesalarizacion(tipoTrabajadorSeleccionado.getFactordesalarizacion());
            duplicarTT.setModalidad(tipoTrabajadorSeleccionado.getModalidad());
            duplicarTT.setModalidadpensionsectorsalud(tipoTrabajadorSeleccionado.getModalidadpensionsectorsalud());
            duplicarTT.setNivelendeudamiento(tipoTrabajadorSeleccionado.getNivelendeudamiento());
            duplicarTT.setNombre(tipoTrabajadorSeleccionado.getNombre());
            duplicarTT.setPatronpagapension(tipoTrabajadorSeleccionado.getPatronpagapension());
            duplicarTT.setPatronpagaretencion(tipoTrabajadorSeleccionado.getPatronpagaretencion());
            duplicarTT.setPatronpagasalud(tipoTrabajadorSeleccionado.getPatronpagasalud());
            duplicarTT.setPorcentajesml(tipoTrabajadorSeleccionado.getPorcentajesml());
            duplicarTT.setPromediabasicoacumulados(tipoTrabajadorSeleccionado.getPromediabasicoacumulados());
            duplicarTT.setTipo(tipoTrabajadorSeleccionado.getTipo());
            duplicarTT.setTipocotizante(tipoTrabajadorSeleccionado.getTipocotizante());
            duplicarTT.setSemestreespecial(tipoTrabajadorSeleccionado.getSemestreespecial());
            duplicarTT.setSecuencia(nuevaSecuencia);
            System.out.println("duplicarTT abrir duplicarRegistroTT");
            context.update("formularioDialogos:duplicarRegistroTT");
            context.execute("duplicarRegistroTT.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasReformasLaborales
     */
    public void confirmarDuplicarTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        Short cod = new Short("0");
        if ((duplicarTT.getCodigo() != cod) && duplicarTT.getNombre() != null) {

            for (int j = 0; j < listaTiposTrabajadores.size(); j++) {
                if (duplicarTT.getCodigo() == listaTiposTrabajadores.get(j).getCodigo()) {
                    cont++;
                }
            }
            for (int j = 0; j < listaTiposTrabajadores.size(); j++) {
                if (duplicarTT.getNombre().equals(listaTiposTrabajadores.get(j).getNombre())) {
                    cont++;
                }
            }
            if (cont > 0) {
                context.update("form:validacionNuevo");
                context.execute("validacionNuevo.show()");
            } else {
                intNuevaSec++;
                nuevaSecuencia = BigInteger.valueOf(intNuevaSec);
                duplicarTT.setSecuencia(nuevaSecuencia);
                listaTiposTrabajadores.add(duplicarTT);
                listTTCrear.add(duplicarTT);
                perderSeleccionTT();
                tipoTrabajadorSeleccionado = listaTiposTrabajadores.get(listaTiposTrabajadores.lastIndexOf(duplicarTT));
                contarRegistrosTT();
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                duplicarTT = new TiposTrabajadores();
                if (bandera == 1) {
                    restaurarTablaTT();
                }
                anularBotonLOV();
                context.execute("duplicarRegistroTT.hide()");
            }
        } else {
            context.execute("errorNewRegNulos.show()");
        }
    }
//LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarTT() {
        duplicarTT = new TiposTrabajadores();
        duplicarTT.setTipocotizante(new TiposCotizantes());
    }

    //BORRAR TT
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTrabajadorSeleccionado == null && vigenciaDiaSeleccionado == null) {
            context.execute("seleccionarRegistro.show()");
        } else {

            if (tablaActiva == 2 && vigenciaDiaSeleccionado != null) {

                if (!listVDModificar.isEmpty() && listVDModificar.contains(vigenciaDiaSeleccionado)) {
                    listVDModificar.remove(vigenciaDiaSeleccionado);
                    listVDBorrar.add(vigenciaDiaSeleccionado);
                } else if (!listVDCrear.isEmpty() && listVDCrear.contains(vigenciaDiaSeleccionado)) {
                    listVDCrear.remove(vigenciaDiaSeleccionado);
                } else {
                    listVDBorrar.add(vigenciaDiaSeleccionado);
                }
                listaVigenciasDiasTT.remove(vigenciaDiaSeleccionado);
                if (tipoListaVD == 1) {
                    filtrarVigenciasDiasTT.remove(vigenciaDiaSeleccionado);
                }

            } else if (tablaActiva == 1 && tipoTrabajadorSeleccionado != null) {

                if (!listTTModificar.isEmpty() && listTTModificar.contains(tipoTrabajadorSeleccionado)) {
                    listTTModificar.remove(tipoTrabajadorSeleccionado);
                    listTTBorrar.add(tipoTrabajadorSeleccionado);
                } else if (!listTTCrear.isEmpty() && listTTCrear.contains(tipoTrabajadorSeleccionado)) {
                    listTTCrear.remove(tipoTrabajadorSeleccionado);
                } else {
                    listTTBorrar.add(tipoTrabajadorSeleccionado);
                }
                listaTiposTrabajadores.remove(tipoTrabajadorSeleccionado);
                if (tipoLista == 1) {
                    filtrarTiposTrabajadores.remove(tipoTrabajadorSeleccionado);
                }
                contarRegistrosTT();
            }

            perderSeleccionTT();
            context.update("form:datosTTrabajadores");

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
        anularBotonLOV();
    }
    //CREAR VD

    /**
     * Metodo que se encarga de agregar un nuevo VD
     */
    public void agregarNuevaVD() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        if ((nuevaVD.getDias() != null) && (nuevaVD.getFechaVigencia() != null)) {

            for (int j = 0; j < listaVigenciasDiasTT.size(); j++) {
                if (nuevaVD.getFechaVigencia() == listaVigenciasDiasTT.get(j).getFechaVigencia()) {
                    cont++;
                }
            }
            if (cont > 0) {
                context.update("form:validacionFechas");
                context.execute("validacionFechas.show()");
            } else {
                if (bandera == 1) {
                    restaurarTablaTT();
                }
                intNuevaSec++;
                nuevaSecuencia = BigInteger.valueOf(intNuevaSec);
                nuevaVD.setSecuencia(nuevaSecuencia);
                listVDCrear.add(nuevaVD);
                listaVigenciasDiasTT.add(nuevaVD);
                vigenciaDiaSeleccionado = listaVigenciasDiasTT.get(listaVigenciasDiasTT.indexOf(nuevaVD));
                anularBotonLOV();
                contarRegistrosVD();
                nuevaVD = new VigenciasDiasTT();

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.update("form:datosVigenciasDTT");
                context.execute("NuevoRegistroVD.hide()");
            }
        } else {
            context.execute("errorNewRegNulosVD.show()");
        }
    }
//LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaVD() {
        nuevaVD = new VigenciasDiasTT();
    }
    //DUPLICAR VD

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVDias() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaDiaSeleccionado == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            duplicarTT = new TiposTrabajadores();
            intNuevaSec++;
            nuevaSecuencia = BigInteger.valueOf(intNuevaSec);

            duplicarVD.setCodigo(vigenciaDiaSeleccionado.getCodigo());
            duplicarVD.setFechaVigencia(vigenciaDiaSeleccionado.getFechaVigencia());
            duplicarVD.setDias(vigenciaDiaSeleccionado.getDias());
            duplicarVD.setSecuencia(nuevaSecuencia);
            duplicarVD.setTipoTrabajador(vigenciaDiaSeleccionado.getTipoTrabajador());

            context.update("formularioDialogos:duplicarRegistroVD");
            context.execute("duplicarRegistroVD.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     */
    public void confirmarDuplicarVD() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        if ((duplicarVD.getDias() != null) || (duplicarVD.getFechaVigencia() != null)) {

            for (int j = 0; j < listaVigenciasDiasTT.size(); j++) {
                if (duplicarVD.getFechaVigencia() == listaVigenciasDiasTT.get(j).getFechaVigencia()) {
                    cont++;
                }
            }
            if (cont > 0) {
                context.update("form:validacionFechas");
                context.execute("validacionFechas.show()");
            } else {
                intNuevaSec++;
                nuevaSecuencia = BigInteger.valueOf(intNuevaSec);
                duplicarVD.setSecuencia(nuevaSecuencia);
                listaVigenciasDiasTT.add(duplicarVD);
                listVDCrear.add(duplicarVD);
                vigenciaDiaSeleccionado = listaVigenciasDiasTT.get(listaVigenciasDiasTT.indexOf(duplicarVD));
                contarRegistrosVD();
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                duplicarVD = new VigenciasDiasTT();
                if (bandera == 1) {
                    restaurarTablaTT();
                }
                anularBotonLOV();
                context.update("form:datosVigenciasDTT");
                context.execute("duplicarRegistroVD.hide()");
            }
        } else {
            context.execute("errorNewRegNulosVD.show()");
        }
    }
//LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarVD() {
        duplicarVD = new VigenciasDiasTT();
    }

    //BORRAR VD
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVD() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaDiaSeleccionado == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (!listVDModificar.isEmpty() && listVDModificar.contains(vigenciaDiaSeleccionado)) {
                listVDModificar.remove(vigenciaDiaSeleccionado);
                listVDBorrar.add(vigenciaDiaSeleccionado);
            } else if (!listVDCrear.isEmpty() && listVDCrear.contains(vigenciaDiaSeleccionado)) {
                listVDCrear.remove(vigenciaDiaSeleccionado);
            } else {
                listVDBorrar.add(vigenciaDiaSeleccionado);
            }
            listaVigenciasDiasTT.remove(vigenciaDiaSeleccionado);
            if (tipoLista == 1) {
                filtrarVigenciasDiasTT.remove(vigenciaDiaSeleccionado);
            }
            contarRegistrosTT();
            perderSeleccionTT();
            context.update("form:datosVigenciasDTT");

            activarBotonLOV();
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    //Seleccion en Listas de Valores
    public void actualizarTipoCotizante() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoActualizacion == 0) {
                tipoTrabajadorSeleccionado.setTipocotizante(tipoCotizanteLovSeleccionado);
                if (!listTTCrear.contains(tipoTrabajadorSeleccionado)) {
                    if (listTTModificar.isEmpty()) {
                        listTTModificar.add(tipoTrabajadorSeleccionado);
                    } else if (!listTTModificar.contains(tipoTrabajadorSeleccionado)) {
                        listTTModificar.add(tipoTrabajadorSeleccionado);
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                context.update("form:datosTTrabajadores");
                context.execute("dialogTiposCot.hide()");
            } else if (tipoActualizacion == 1) {
                nuevoTipoT.setTipocotizante(tipoCotizanteLovSeleccionado);
                context.update("formularioDialogos:nuevatipoCotNTT");
                context.execute("dialogTiposCot.hide()");
            } else if (tipoActualizacion == 2) {
                duplicarTT.setTipocotizante(tipoCotizanteLovSeleccionado);
                context.update("formularioDialogos:duplicartipoCotNTT");
                context.execute("dialogTiposCot.hide()");
            }
            filtrarLovTiposCotizantes = null;
            tipoCotizanteLovSeleccionado = null;
            aceptar = true;
            tipoActualizacion = -1;

            context.reset("form:lovTiposCot:globalFilter");
            context.execute("lovTiposCot.clearFilters()");
            context.update("form:dialogTiposCot");
            context.update("form:lovTiposCot");
            context.update("form:aceptarTC");
            context.execute("dialogTiposCot.hide()");

        } catch (Exception e) {
            System.out.println("ERROR BETA .actualizarCentroCosto ERROR============" + e.getMessage());
        }
        activarBotonLOV();
    }

    public void cancelarSeleccionTipoCot() {
        try {
            filtrarLovTiposCotizantes = null;
            tipoCotizanteLovSeleccionado = null;
            aceptar = true;
            tipoActualizacion = -1;

            RequestContext context = RequestContext.getCurrentInstance();
            context.reset("form:lovTiposCot:globalFilter");
            context.execute("lovTiposCot.clearFilters()");
            context.update("form:dialogTiposCot");
            context.update("form:lovTiposCot");
            context.update("form:aceptarTC");
            context.execute("dialogTiposCot.hide()");
        } catch (Exception e) {
            System.out.println("ERROR cancelarSeleccionTipoCot :" + e.getMessage());
        }
    }

    public void llamadoDialogoTT(int tipoAct) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = tipoAct;
        tipoTrabajadorLovSeleccionado = null;
        try {
            if (guardado == false) {
                context.execute("confirmarGuardar.show()");
            } else {
                llenarLOVTT();
                context.update("form:lovTiposTra");
                context.execute("dialogTiposTra.show()");
                contarRegistrosLovTT(0);
            }
        } catch (Exception e) {
            System.err.println("ERROR LLAMADO DIALOGO BUSCAR TT " + e);
        }
    }

    public void llenarLOVTT() {
        if (lovTiposTrabajadores == null) {
            lovTiposTrabajadores = new ArrayList<TiposTrabajadores>();
            if (listaTiposTrabajadores != null) {
                for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                    lovTiposTrabajadores.add(listaTiposTrabajadores.get(i));
                }
            } else {
                lovTiposTrabajadores = administrarTiposTrabajadores.buscarTiposTrabajadoresTT();
            }
        }
    }

    public void seleccionarTipoTrabajador() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoActualizacion == 0) {
                listaTiposTrabajadores.clear();
                listaTiposTrabajadores.add(tipoTrabajadorLovSeleccionado);
                tipoTrabajadorSeleccionado = tipoTrabajadorLovSeleccionado;
                listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
                filtrarLovTiposTrabajadores = null;
                aceptar = true;
                mostrartodos = false;
                contarRegistrosTT();
                contarRegistrosVD();
                activarBotonLOV();

                context.update("form:datosTTrabajadores");
                context.update("form:datosVigenciasDTT");
                context.update("form:mostrarTodos");
            } else if (tipoActualizacion == 3) {
                tipoTrabajadorAClonar = new TiposTrabajadores();
                tipoTrabajadorAClonar.setCodigo(tipoTrabajadorLovSeleccionado.getCodigo());
                tipoTrabajadorAClonar.setNombre(tipoTrabajadorLovSeleccionado.getNombre());
                tipoTrabajadorAClonar.setSecuencia(tipoTrabajadorLovSeleccionado.getSecuencia());

                context.update("form:codigoTipoTClonarBase");
                context.update("form:nombreTipoTClonarBase");
            }
            context.reset("form:lovTiposTra:globalFilter");
            context.execute("lovTiposTra.clearFilters()");
            context.update("form:dialogTiposTra");
            context.update("form:lovTiposTra");
            context.update("form:aceptarTTra");
            context.execute("dialogTiposTra.hide()");

        } catch (Exception e) {
            System.out.println("ERROR seleccionarTipoTrabajador : " + e.getMessage());
        }
    }

    public void autocompletarClonado(String column) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (column.equalsIgnoreCase("COD")) {
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getCodigo() == tipoTrabajadorAClonar.getCodigo()) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoTrabajadorAClonar = new TiposTrabajadores();
                tipoTrabajadorAClonar.setCodigo(listaTiposTrabajadores.get(indiceUnicoElemento).getCodigo());
                tipoTrabajadorAClonar.setNombre(listaTiposTrabajadores.get(indiceUnicoElemento).getNombre());
                tipoTrabajadorAClonar.setSecuencia(listaTiposTrabajadores.get(indiceUnicoElemento).getSecuencia());
                context.update("form:codigoTipoTClonarBase");
                context.update("form:nombreTipoTClonarBase");
            }
        } else if (column.equalsIgnoreCase("Nom")) {
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getNombre().startsWith(tipoTrabajadorAClonar.getNombre().toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoTrabajadorAClonar = new TiposTrabajadores();
                tipoTrabajadorAClonar.setCodigo(listaTiposTrabajadores.get(indiceUnicoElemento).getCodigo());
                tipoTrabajadorAClonar.setNombre(listaTiposTrabajadores.get(indiceUnicoElemento).getNombre());
                tipoTrabajadorAClonar.setSecuencia(listaTiposTrabajadores.get(indiceUnicoElemento).getSecuencia());
                context.update("form:codigoTipoTClonarBase");
                context.update("form:nombreTipoTClonarBase");
            }
        }
        if (coincidencias != 1) {
            context.update("form:lovTiposTra");
            context.update("form:dialogTiposTra");
            context.execute("dialogTiposTra.show()");
        }
    }

    public void clonarTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Entro en Clonar()");
        if (nombreNuevoClonado != null && codigoNuevoClonado != null && tipoTrabajadorAClonar.getSecuencia() != null) {
            int error = 0;
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getNombre().equals(nombreNuevoClonado)) {
                    error++;
                }
                if (listaTiposTrabajadores.get(i).getCodigo() == codigoNuevoClonado) {
                    error++;
                }
            }
            if (error > 0) {
                context.update("form:errorClonadoRepetido");
                context.execute("errorClonadoRepetido.show()");
            } else {
                String retornoClonado = administrarTiposTrabajadores.clonarTT(nombreNuevoClonado, codigoNuevoClonado, tipoTrabajadorAClonar.getSecuencia());
                if (retornoClonado.equals("S")) {
                    listaTiposTrabajadores = null;
                    getListaTiposTrabajadores();
                    boolean banderita = false;
                    if (listaTiposTrabajadores != null) {
                        for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                            if (listaTiposTrabajadores.get(i).getNombre().equals(nombreNuevoClonado)) {
                                banderita = true;
                            }
                        }
                        if (banderita) {
                            FacesMessage msg = new FacesMessage("Información", "Tipo trabajador clonado correctamente");
                            FacesContext.getCurrentInstance().addMessage(null, msg);
                            context.update("form:growl");
                        }
                    }
                } else {
                    FacesMessage msg = new FacesMessage("Error", "ERROR clonando Tipo trabajador : " + retornoClonado);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    context.update("form:growl");
                }
            }
        } else {
            context.update("form:errorClonadoNulos");
            context.execute("errorClonadoNulos.show()");
        }
    }

    public void cancelarCambioTipoTrabajador() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            filtrarLovTiposTrabajadores = null;
            aceptar = true;
            tipoActualizacion = -1;
            context.reset("form:lovTiposTra:globalFilter");
            context.execute("lovTiposTra.clearFilters()");
            context.update("form:dialogTiposTra");
            context.update("form:lovTiposTra");
            context.update("form:aceptarTTra");
            context.execute("dialogTiposTra.hide()");
        } catch (Exception e) {
            System.out.println("ERROR cancelarCambioTipoTrabajador : " + e.getMessage());
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        listaTiposTrabajadores.clear();
        if (lovTiposTrabajadores != null) {
            for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                listaTiposTrabajadores.add(lovTiposTrabajadores.get(i));
            }
        } else {
            listaTiposTrabajadores = administrarTiposTrabajadores.buscarTiposTrabajadoresTT();
        }
        tipoTrabajadorSeleccionado = null;
        listaVigenciasDiasTT = null;
        aceptar = true;
        contarRegistrosTT();
        contarRegistrosVD();
        anularBotonLOV();
        mostrartodos = true;
        context.update("form:datosTTrabajadores");
        context.update("form:datosVigenciasDTT");
        context.update("form:mostrarTodos");
    }

    //GUARDAR
    /**
     * Metodo que guarda los cambios efectuados en la pagina
     */
    public void guardadoGeneral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            if (!listTTBorrar.isEmpty()) {
                for (int i = 0; i < listTTBorrar.size(); i++) {
                    administrarTiposTrabajadores.borrarTT(listTTBorrar.get(i));
                }
                listTTBorrar.clear();
            }
            if (!listTTCrear.isEmpty()) {
                for (int i = 0; i < listTTCrear.size(); i++) {
                    administrarTiposTrabajadores.crearTT(listTTCrear.get(i));
                }
                listTTCrear.clear();
            }
            if (!listTTModificar.isEmpty()) {
                for (int i = 0; i < listTTModificar.size(); i++) {
                    administrarTiposTrabajadores.editarTT(listTTModificar.get(i));
                }
                listTTModificar.clear();
            }

            if (!listVDBorrar.isEmpty()) {
                for (int i = 0; i < listVDBorrar.size(); i++) {
                    administrarTiposTrabajadores.borrarVD(listVDBorrar.get(i));
                }
                listVDBorrar.clear();
            }
            if (!listVDCrear.isEmpty()) {
                for (int i = 0; i < listVDCrear.size(); i++) {
                    administrarTiposTrabajadores.crearVD(listVDCrear.get(i));
                }
                listVDCrear.clear();
            }
            if (!listVDModificar.isEmpty()) {
                for (int i = 0; i < listVDModificar.size(); i++) {
                    administrarTiposTrabajadores.editarVD(listVDModificar.get(i));
                }
                listVDModificar.clear();
            }

            listaTiposTrabajadores = null;
            getListaTiposTrabajadores();
            perderSeleccionTT();
            contarRegistrosTT();
            contarRegistrosVD();
            activarBotonLOV();

            guardado = true;
            context.update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            intNuevaSec = 0;
        }
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificaciones() {
        if (bandera == 1) {
            restaurarTablaTT();
        }
        tablaActiva = 0;
        listTTBorrar.clear();
        listTTCrear.clear();
        listTTModificar.clear();
        listaTiposTrabajadores = null;
        getListaTiposTrabajadores();
        lovTiposTrabajadores = null;
        contarRegistrosTT();
        perderSeleccionTT();
        intNuevaSec = 0;
        guardado = true;
        mostrartodos = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTTrabajadores");
        context.update("form:ACEPTAR");
    }
    //SALIR

    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            restaurarTablaTT();
        }
        lovTiposTrabajadores = null;
        tablaActiva = 0;

        activarBotonLOV();
        listTTBorrar.clear();
        listTTCrear.clear();
        listTTModificar.clear();
        intNuevaSec = 0;
        listaTiposTrabajadores = null;
        perderSeleccionTT();
        guardado = true;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTrabajadorSeleccionado == null && vigenciaDiaSeleccionado == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (tablaActiva == 1 && tipoTrabajadorSeleccionado != null) {
                editarTT = tipoTrabajadorSeleccionado;

                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarCodigoTT");
                    context.execute("editarCodigoTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarDescripTT");
                    context.execute("editarDescripTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarFactorDesTT");
                    context.execute("editarFactorDesTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 3) {
                    context.update("formularioDialogos:editarTipoCotTT");
                    context.execute("editarTipoCotTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 4) {
                    context.update("formularioDialogos:editarDiasVacNOTT");
                    context.execute("editarDiasVacNOTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 5) {
                    context.update("formularioDialogos:editarDiasVacTT");
                    context.execute("editarDiasVacTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 8) {
                    context.update("formularioDialogos:editarNivelEndTT");
                    context.execute("editarNivelEndTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 10) {
                    context.update("formularioDialogos:editarPorcentTT");
                    context.execute("editarPorcentTT.show()");
                    cualCelda = -1;
                }
            }
            if (tablaActiva == 2 && vigenciaDiaSeleccionado != null) {
                editarVD = vigenciaDiaSeleccionado;

                if (cualCeldaVD == 0) {
                    context.update("formularioDialogos:editarDiasVD");
                    context.execute("editarDiasVD.show()");
                    cualCeldaVD = -1;
                } else if (cualCeldaVD == 1) {
                    context.update("formularioDialogos:editarFechaVD");
                    context.execute("editarFechaVD.show()");
                    cualCeldaVD = -1;
                }
            }
        }
    }

    public void verificarNuevoVigenciasDias() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTrabajadorSeleccionado != null) {
            context.update("formularioDialogos:NuevoRegistroVD");
            context.update("formularioDialogos:nuevoVD");
            context.execute("NuevoRegistroVD.show()");
        } else {
            context.update("formularioDialogos:validacionNuevoVigenciasDias");
            context.execute("validacionNuevoVigenciasDias.show()");
        }
    }

    //METODO RASTROS PARA LAS TABLAS
    public void verificarRastro() {
        if (tipoTrabajadorSeleccionado == null && vigenciaDiaSeleccionado == null) {
            RequestContext.getCurrentInstance().execute("verificarRastrosTablasH.show()");
        } else {
            if (tablaActiva == 2 && vigenciaDiaSeleccionado != null) {
                verificarRastroVigenciasDias();
            } else if (tablaActiva == 1 && tipoTrabajadorSeleccionado != null) {
                verificarRastroTipoTrabajador();
            }
        }
    }

    public void verificarRastroTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(tipoTrabajadorSeleccionado.getSecuencia(), "TIPOSTRABAJADORES");
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "TiposTrabajadores";
            msnConfirmarRastro = "La tabla TIPOSTRABAJADORES tiene rastros para el registro seleccionado, ¿desea continuar?";
            context.update("form:msnConfirmarRastro");
            context.execute("confirmarRastro.show()");
        } else if (resultado == 3) {
            context.execute("errorRegistroRastro.show()");
        } else if (resultado == 4) {
            context.execute("errorTablaConRastro.show()");
        } else if (resultado == 5) {
            context.execute("errorTablaSinRastro.show()");
        }
    }

    public void verificarRastroTipoTrabajadorH() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("TIPOSTRABAJADORES")) {
            nombreTablaRastro = "TiposTrabajadores";
            msnConfirmarRastroHistorico = "La tabla TIPOSTRABAJADORES tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciasDias() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaDiaSeleccionado.getSecuencia(), "VIGENCIASDIASTT");
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasDiasTT";
            msnConfirmarRastro = "La tabla VIGENCIASDIASTT tiene rastros para el registro seleccionado, ¿desea continuar?";
            context.update("form:msnConfirmarRastro");
            context.execute("confirmarRastro.show()");
        } else if (resultado == 3) {
            context.execute("errorRegistroRastro.show()");
        } else if (resultado == 4) {
            context.execute("errorTablaConRastro.show()");
        } else if (resultado == 5) {
            context.execute("errorTablaSinRastro.show()");
        }
    }

    public void verificarRastroVigenciasDiasH() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASDIASTT")) {
            nombreTablaRastro = "VigenciasDiasTT";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASDIASTT tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }

    public void anularBotonLOV() {
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void activarBotonLOV() {
        activarLOV = false;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    //Perder seleccion TT
    public void perderSeleccionTT() {
        tipoTrabajadorSeleccionado = null;
        anularBotonLOV();
        listaVigenciasDiasTT = null;
        vigenciaDiaSeleccionado = null;
        contarRegistrosVD();
        RequestContext.getCurrentInstance().update("form:datosTTrabajadores");
        RequestContext.getCurrentInstance().update("form:datosVigenciasDTT");
    }

    // Eventos filtrar //
    public void eventoFiltrarTT() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        perderSeleccionTT();
        contarRegistrosTT();
    }

    public void eventoFiltrarVD() {
        if (tipoListaVD == 0) {
            tipoListaVD = 1;
        }
        vigenciaDiaSeleccionado = null;
        anularBotonLOV();
        contarRegistrosVD();
    }

    public void eventoFiltrarLovTC() {
        contarRegistrosLovTC(1);
    }

    public void eventoFiltrarLovTT() {
        contarRegistrosLovTT(1);
    }

    public void contarRegistrosTT() {
        if (tipoLista == 1) {
            infoRegistroTT = String.valueOf(filtrarTiposTrabajadores.size());
        } else if (listaTiposTrabajadores != null) {
            infoRegistroTT = String.valueOf(listaTiposTrabajadores.size());
        } else {
            infoRegistroTT = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    public void contarRegistrosVD() {
        if (tipoListaVD == 1) {
            infoRegistroVD = String.valueOf(filtrarVigenciasDiasTT.size());
        } else if (listaVigenciasDiasTT != null) {
            infoRegistroVD = String.valueOf(listaVigenciasDiasTT.size());
        } else {
            infoRegistroVD = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistroDias");
    }

    public void contarRegistrosLovTT(int tipoListaLOV) {
        if (tipoListaLOV == 1) {
            infoRegistroLovTT = String.valueOf(filtrarLovTiposTrabajadores.size());
        } else if (lovTiposTrabajadores != null) {
            infoRegistroLovTT = String.valueOf(lovTiposTrabajadores.size());
        } else {
            infoRegistroLovTT = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistroTTra");
    }

    public void contarRegistrosLovTC(int tipoListaLOV) {
        if (tipoListaLOV == 1) {
            infoRegistroTC = String.valueOf(filtrarLovTiposCotizantes.size());
        } else if (lovTiposCotizantes != null) {
            infoRegistroTC = String.valueOf(lovTiposCotizantes.size());
        } else {
            infoRegistroTC = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistroTCot");
    }

    public void recordarSeleccionTT() {
        if (tipoTrabajadorSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaTT = (DataTable) c.getViewRoot().findComponent("form:datosTTrabajadores");
            tablaTT.setSelection(tipoTrabajadorSeleccionado);
        } else {
            RequestContext.getCurrentInstance().execute("datosTTrabajadores.unselectAllRows()");
        }
    }

    public void recordarSeleccionVD() {
        if (vigenciaDiaSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaVD = (DataTable) c.getViewRoot().findComponent("form:datosVigenciasDTT");
            tablaVD.setSelection(vigenciaDiaSeleccionado);
            System.out.println("vigenciaDiaSeleccionado: " + vigenciaDiaSeleccionado);
        }
    }

    public void exportPDF() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) context.getViewRoot().findComponent("formExportar:datosTTrabajadoresExportar");
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposTrabajadoresPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) context.getViewRoot().findComponent("formExportar:datosTTrabajadoresExportar");
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposTrabajadoresXLS", false, false, "UTF-8", null, null);
    }
//
//    public void selectOneMenus() {
//        System.out.println("Evento select aqui");
//    }
//
//    public void cambiarModalidad(BigInteger sec) {
//        for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
//            if (listaTiposTrabajadores.get(i).getSecuencia().equals(sec)) {
//                System.out.println("Mapa de Modalidades para posicion : " + listaTiposTrabajadores.indexOf(listaTiposTrabajadores.get(i)));
//                System.out.println("Mapa de Modalidades Nuevo valor : " + mapaModalidades.get(sec));
//                listaTiposTrabajadores.get(i).setModalidad(mapaModalidades.get(sec));
//            }
//        }
//        RequestContext.getCurrentInstance().update("form:datosTTrabajadores");
//    }
//
//    public String retornarModalidad(BigInteger sec) {
//        return mapaModalidades.get(sec);
//    }

    //GET'S y SET'S
    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getAltoTablaVD() {
        return altoTablaVD;
    }

    public void setAltoTablaVD(String altoTablaVD) {
        this.altoTablaVD = altoTablaVD;
    }

    public TiposTrabajadores getDuplicarTT() {
        return duplicarTT;
    }

    public void setDuplicarTT(TiposTrabajadores duplicarTT) {
        this.duplicarTT = duplicarTT;
    }

    public TiposTrabajadores getEditarTT() {
        return editarTT;
    }

    public void setEditarTT(TiposTrabajadores editarTT) {
        this.editarTT = editarTT;
    }

    public List<TiposCotizantes> getFiltrarLovTiposCotizantes() {
        return filtrarLovTiposCotizantes;
    }

    public void setFiltrarLovTiposCotizantes(List<TiposCotizantes> filtrarLovTiposCotizantes) {
        this.filtrarLovTiposCotizantes = filtrarLovTiposCotizantes;
    }

    public List<TiposTrabajadores> getFiltrarTiposTrabajadores() {
        return filtrarTiposTrabajadores;
    }

    public void setFiltrarTiposTrabajadores(List<TiposTrabajadores> filtrarTiposTrabajadores) {
        this.filtrarTiposTrabajadores = filtrarTiposTrabajadores;
    }

    public String getInfoRegistroTC() {
        return infoRegistroTC;
    }

    public void setInfoRegistroTC(String infoRegistroTC) {
        this.infoRegistroTC = infoRegistroTC;
    }

    public String getInfoRegistroTT() {
        return infoRegistroTT;
    }

    public void setInfoRegistroTT(String infoRegistroTT) {
        this.infoRegistroTT = infoRegistroTT;
    }

    public List<TiposTrabajadores> getListaTiposTrabajadores() {
        try {
            if (listaTiposTrabajadores == null) {
                listaTiposTrabajadores = administrarTiposTrabajadores.buscarTiposTrabajadoresTT();
            }
        } catch (Exception e) {
            System.out.println("ERROR getListaTiposTrabajadores : " + e);
            listaTiposTrabajadores = null;
        }
        return listaTiposTrabajadores;
    }

    public void setListaTiposTrabajadores(List<TiposTrabajadores> listaTiposTrabajadores) {
        this.listaTiposTrabajadores = listaTiposTrabajadores;
    }

    public List<TiposCotizantes> getLovTiposCotizantes() {
        if (lovTiposCotizantes == null) {
            lovTiposCotizantes = administrarTiposCotizantes.tiposCotizantes();
        }
        return lovTiposCotizantes;
    }

    public void setLovTiposCotizantes(List<TiposCotizantes> lovTiposCotizantes) {
        this.lovTiposCotizantes = lovTiposCotizantes;
    }

    public TiposCotizantes getTipoCotizanteLovSeleccionado() {
        return tipoCotizanteLovSeleccionado;
    }

    public void setTipoCotizanteLovSeleccionado(TiposCotizantes tipoCotizanteLovSeleccionado) {
        this.tipoCotizanteLovSeleccionado = tipoCotizanteLovSeleccionado;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
    }

    public List<VigenciasDiasTT> getListaVigenciasDiasTT() {
        return listaVigenciasDiasTT;
    }

    public void setListaVigenciasDiasTT(List<VigenciasDiasTT> listaVigenciasDiasTT) {
        this.listaVigenciasDiasTT = listaVigenciasDiasTT;
    }

    public VigenciasDiasTT getVigenciaDiaSeleccionado() {
        return vigenciaDiaSeleccionado;
    }

    public void setVigenciaDiaSeleccionado(VigenciasDiasTT vigenciaDiaSeleccionado) {
        this.vigenciaDiaSeleccionado = vigenciaDiaSeleccionado;
    }

    public List<VigenciasDiasTT> getFiltrarVigenciasDiasTT() {
        return filtrarVigenciasDiasTT;
    }

    public void setFiltrarVigenciasDiasTT(List<VigenciasDiasTT> filtrarVigenciasDiasTT) {
        this.filtrarVigenciasDiasTT = filtrarVigenciasDiasTT;
    }

    public String getInfoRegistroVD() {
        return infoRegistroVD;
    }

    public void setInfoRegistroVD(String infoRegistroVD) {
        this.infoRegistroVD = infoRegistroVD;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        return lovTiposTrabajadores;
    }

    public void setLovTiposTrabajadores(List<TiposTrabajadores> lovTiposTrabajadores) {
        this.lovTiposTrabajadores = lovTiposTrabajadores;
    }

    public String getInfoRegistroLovTT() {
        return infoRegistroLovTT;
    }

    public void setInfoRegistroLovTT(String infoRegistroLovTT) {
        this.infoRegistroLovTT = infoRegistroLovTT;
    }

    public TiposTrabajadores getNuevoTipoT() {
        return nuevoTipoT;
    }

    public List<TiposTrabajadores> getFiltrarLovTiposTrabajadores() {
        return filtrarLovTiposTrabajadores;
    }

    public void setFiltrarLovTiposTrabajadores(List<TiposTrabajadores> filtrarLovTiposTrabajadores) {
        this.filtrarLovTiposTrabajadores = filtrarLovTiposTrabajadores;
    }

    public TiposTrabajadores getTipoTrabajadorLovSeleccionado() {
        return tipoTrabajadorLovSeleccionado;
    }

    public void setTipoTrabajadorLovSeleccionado(TiposTrabajadores tipoTrabajadorLovSeleccionado) {
        this.tipoTrabajadorLovSeleccionado = tipoTrabajadorLovSeleccionado;
    }

    public void setNuevoTipoT(TiposTrabajadores nuevoTipoT) {
        this.nuevoTipoT = nuevoTipoT;
    }

    public boolean isMostrartodos() {
        return mostrartodos;
    }

    public void setMostrartodos(boolean mostrartodos) {
        this.mostrartodos = mostrartodos;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public VigenciasDiasTT getDuplicarVD() {
        return duplicarVD;
    }

    public void setDuplicarVD(VigenciasDiasTT duplicarVD) {
        this.duplicarVD = duplicarVD;
    }

    public VigenciasDiasTT getEditarVD() {
        return editarVD;
    }

    public void setEditarVD(VigenciasDiasTT editarVD) {
        this.editarVD = editarVD;
    }

    public VigenciasDiasTT getNuevaVD() {
        return nuevaVD;
    }

    public void setNuevaVD(VigenciasDiasTT nuevaVD) {
        this.nuevaVD = nuevaVD;
    }

    public Short getCodigoNuevoClonado() {
        return codigoNuevoClonado;
    }

    public void setCodigoNuevoClonado(Short codigoNuevoClonado) {
        this.codigoNuevoClonado = codigoNuevoClonado;
    }

    public String getNombreNuevoClonado() {
        return nombreNuevoClonado;
    }

    public void setNombreNuevoClonado(String nombreNuevoClonado) {
        this.nombreNuevoClonado = nombreNuevoClonado;
    }

    public TiposTrabajadores getTipoTrabajadorAClonar() {
        return tipoTrabajadorAClonar;
    }

    public void setTipoTrabajadorAClonar(TiposTrabajadores tipoTrabajadorAClonar) {
        this.tipoTrabajadorAClonar = tipoTrabajadorAClonar;
    }
}

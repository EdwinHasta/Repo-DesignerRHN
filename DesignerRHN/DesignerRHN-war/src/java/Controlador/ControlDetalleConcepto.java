package Controlador;

import Entidades.CentrosCostos;
import Entidades.Conceptos;
import Entidades.Cuentas;
import Entidades.Formulas;
import Entidades.FormulasConceptos;
import Entidades.GruposConceptos;
import Entidades.ReformasLaborales;
import Entidades.TiposCentrosCostos;
import Entidades.TiposContratos;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasConceptosRL;
import Entidades.VigenciasConceptosTC;
import Entidades.VigenciasConceptosTT;
import Entidades.VigenciasCuentas;
import Entidades.VigenciasGruposConceptos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarDetalleConceptoInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlDetalleConcepto implements Serializable {

    @EJB
    AdministrarDetalleConceptoInterface administrarDetalleConcepto;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //////////////Conceptos////////////////
    private Conceptos conceptoActual;
    ///////////VigenciasCuentas////////////
    private List<VigenciasCuentas> listVigenciasCuentasConcepto;
    private List<VigenciasCuentas> filtrarListVigenciasCuentasConcepto;
    private VigenciasCuentas vigenciaCuentaSeleccionada;
    private String altoTablaVigenciaCuenta;
    ///////////////////VigenciasGruposConceptos///////////////////////
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptos;
    private List<VigenciasGruposConceptos> filtrarListVigenciasGruposConceptos;
    private VigenciasGruposConceptos vigenciaGrupoCoSeleccionada;
    private String altoTablaVigenciaGrupoC;
    ///////////////////VigenciasConceptosTT////////////////////////////
    private List<VigenciasConceptosTT> listVigenciasConceptosTTConcepto;
    private List<VigenciasConceptosTT> filtrarListVigenciasConceptosTT;
    private VigenciasConceptosTT vigenciaConceptoTTSeleccionada;
    private String altoTablaVigenciaConceptoTT;
    ///////////////////VigenciasConceptosTC///////////////////////////////
    private List<VigenciasConceptosTC> listVigenciasConceptosTCConcepto;
    private List<VigenciasConceptosTC> filtrarListVigenciasConceptosTC;
    private VigenciasConceptosTC vigenciaConceptoTCSeleccionada;
    private String altoTablaVigenciaConceptoTC;
    ///////////////////VigenciasConceptosRL///////////////////////////////
    private List<VigenciasConceptosRL> listVigenciasConceptosRLConcepto;
    private List<VigenciasConceptosRL> filtrarListVigenciasConceptosRL;
    private VigenciasConceptosRL vigenciaConceptoRLSeleccionada;
    private String altoTablaVigenciaConceptoRL;
    ///////////////////FormulasConceptos///////////////////////////////
    private List<FormulasConceptos> listFormulasConceptos;
    private List<FormulasConceptos> filtrarListFormulasConceptosConcepto;
    private FormulasConceptos vigFormulaConceptoSeleccionada;
    private String altoTablaFormulaConcepto;
    ///////////Conteo de registros para las tablas principales
    private String infoRegistroCuenta, infoRegistroGrupoC, infoRegistroConceptoTT, infoRegistroConceptoTC, infoRegistroConceptoRL, infoRegistroFormulaConcepto;
    ///////////VigenciasCuentas////////////
    private int banderaVigenciaCuenta;
    private List<VigenciasCuentas> listVigenciasCuentasModificar;
    private VigenciasCuentas nuevaVigenciaCuenta;
    private List<VigenciasCuentas> listVigenciasCuentasCrear;
    private List<VigenciasCuentas> listVigenciasCuentasBorrar;
    private VigenciasCuentas editarVigenciaCuenta;
    private int cualCeldaVigenciaCuenta, tipoListaVigenciaCuenta;
    private VigenciasCuentas duplicarVigenciaCuenta;
    private boolean cambiosVigenciaCuenta;
    private String auxVC_TipoCC, auxVC_Debito, auxVC_DescDeb, auxVC_Credito, auxVC_DescCre, auxVC_ConsDeb, auxVC_ConsCre;
    private Date auxVC_FechaIni, auxVC_FechaFin;
    private Column vigenciaCuentaFechaInicial, vigenciaCuentaFechaFinal, vigenciaCuentaTipoCC, vigenciaCuentaDebitoCod, vigenciaCuentaDebitoDes, vigenciaCuentaCCConsolidadorD, vigenciaCuentaCreditoCod, vigenciaCuentaCreditoDes, vigenciaCuentaCCConsolidadorC;
    private boolean permitirIndexVigenciaCuenta;
    ///////////////////VigenciasGruposConceptos///////////////////////
    private int banderaVigenciaGrupoConcepto;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosModificar;
    private VigenciasGruposConceptos nuevaVigenciaGrupoConcepto;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosCrear;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosBorrar;
    private VigenciasGruposConceptos editarVigenciaGrupoConcepto;
    private int cualCeldaVigenciaGrupoConcepto, tipoListaVigenciaGrupoConcepto;
    private VigenciasGruposConceptos duplicarVigenciaGrupoConcepto;
    private boolean cambiosVigenciaGrupoConcepto;
    private String auxVGC_Descripcion, auxVGC_Codigo;
    private Date auxVGC_FechaIni, auxVGC_FechaFin;
    private Column vigenciaGCFechaInicial, vigenciaGCFechaFinal, vigenciaGCCodigo, vigenciaGCDescripcion;
    private boolean permitirIndexVigenciaGrupoConcepto;
    ///////////////////VigenciasConceptosTT///////////////////////
    private int banderaVigenciaConceptoTT;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTModificar;
    private VigenciasConceptosTT nuevaVigenciaConceptoTT;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTCrear;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTBorrar;
    private VigenciasConceptosTT editarVigenciaConceptoTT;
    private int cualCeldaVigenciaConceptoTT, tipoListaVigenciaConceptoTT;
    private VigenciasConceptosTT duplicarVigenciaConceptoTT;
    private boolean cambiosVigenciaConceptoTT;
    private String auxVCTT_Descripcion;
    private Date auxVCTT_FechaIni, auxVCTT_FechaFin;
    private Column vigenciaCTTFechaInicial, vigenciaCTTFechaFinal, vigenciaCTTDescripcion;
    private boolean permitirIndexVigenciaConceptoTT;
    ///////////////////VigenciasConceptosTC///////////////////////
    private int banderaVigenciaConceptoTC;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCModificar;
    private VigenciasConceptosTC nuevaVigenciaConceptoTC;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCCrear;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCBorrar;
    private VigenciasConceptosTC editarVigenciaConceptoTC;
    private int cualCeldaVigenciaConceptoTC, tipoListaVigenciaConceptoTC;
    private VigenciasConceptosTC duplicarVigenciaConceptoTC;
    private boolean cambiosVigenciaConceptoTC;
    private String auxVCTC_Descripcion;
    private Date auxVCTC_FechaIni, auxVCTC_FechaFin;
    private Column vigenciaCTCFechaInicial, vigenciaCTCFechaFinal, vigenciaCTCDescripcion;
    private boolean permitirIndexVigenciaConceptoTC;
    ///////////////////VigenciasConceptosRL///////////////////////
    private int banderaVigenciaConceptoRL;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLModificar;
    private VigenciasConceptosRL nuevaVigenciaConceptoRL;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLCrear;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLBorrar;
    private VigenciasConceptosRL editarVigenciaConceptoRL;
    private int cualCeldaVigenciaConceptoRL, tipoListaVigenciaConceptoRL;
    private VigenciasConceptosRL duplicarVigenciaConceptoRL;
    private boolean cambiosVigenciaConceptoRL;
    private String auxVCRL_Descripcion;
    private Date auxVCRL_FechaIni, auxVCRL_FechaFin;
    private Column vigenciaCRLFechaInicial, vigenciaCRLFechaFinal, vigenciaCRLDescripcion;
    private boolean permitirIndexVigenciaConceptoRL;
    ///////////////////FormulasConceptos///////////////////////
    private int banderaFormulasConceptos;
    private List<FormulasConceptos> listFormulasConceptosModificar;
    private FormulasConceptos nuevaFormulasConceptos;
    private List<FormulasConceptos> listFormulasConceptosCrear;
    private List<FormulasConceptos> listFormulasConceptosBorrar;
    private FormulasConceptos editarFormulasConceptos;
    private int cualCeldaFormulasConceptos, tipoListaFormulasConceptos;
    private FormulasConceptos duplicarFormulasConceptos;
    private boolean cambiosFormulasConceptos;
    private String auxFC_Descripcion, auxFC_Orden;
    private Date auxFC_FechaIni, auxFC_FechaFin;
    private Column formulaCFechaInicial, formulaCFechaFinal, formulaCNombre, formulaCOrden;
    private boolean permitirIndexFormulasConceptos;
    private FormulasConceptos actualFormulaConcepto;

    ////////////Listas Valores VigenciasCuentas/////////////
    private List<TiposCentrosCostos> lovTiposCentrosCostos;
    private List<TiposCentrosCostos> filtrarListTiposCentrosCostos;
    private TiposCentrosCostos tipoCentroCostoSeleccionadoLOV;

    private List<Cuentas> lovCuentas;
    private List<Cuentas> filtrarListCuentas;
    private Cuentas cuentaSeleccionadaLOV;

    private List<CentrosCostos> lovCentrosCostos;
    private List<CentrosCostos> filtrarListCentrosCostos;
    private CentrosCostos centroCostoSeleccionadoLOV;
    /////////////Lista Valores VigenciaGrupoConcepto///////////////////////
    private List<GruposConceptos> lovGruposConceptos;
    private List<GruposConceptos> filtrarListGruposConceptos;
    private GruposConceptos grupoConceptoSeleccionadoLOV;
    /////////////Lista Valores VigenciasConceptosTT///////////////////////
    private List<TiposTrabajadores> lovTiposTrabajadores;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionadoLOV;
    /////////////Lista Valores VigenciasConceptosTC///////////////////////
    private List<TiposContratos> lovTiposContratos;
    private List<TiposContratos> filtrarListTiposContratos;
    private TiposContratos tipoContratoSeleccionadoLOV;
    /////////////Lista Valores VigenciasConceptosRL///////////////////////
    private List<ReformasLaborales> lovReformasLaborales;
    private List<ReformasLaborales> filtrarListReformasLaborales;
    private ReformasLaborales reformaLaboralSeleccionadoLOV;
    /////////////Lista Valores FormulasConceptos///////////////////////
    private List<Formulas> lovFormulas;
    private List<Formulas> filtrarListFormulas;
    private Formulas formulaSeleccionadoLOV;

    private List<FormulasConceptos> lovFormulasConceptos;
    private List<FormulasConceptos> filtrarListFormulasConceptos;
    private FormulasConceptos formulaConceptoSeleccionadoLOV;
    //////////////Otros////////////////Otros////////////////////
    private boolean aceptar;
    private boolean guardado;
    private BigInteger l;
    private int k;
    private String nombreXML, nombreExportar;
    private String nombreTabla;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int tipoActualizacion;
    private Date fechaParametro;
    private String comportamientoConcepto;
    private boolean formulaSeleccionada;
    private String paginaRetorno;
    private String conceptoEliminar;
    ////////////////////////////////
    private String infoRegistroLovTipoCentroCosto, infoRegistroLovCuentaDebito, infoRegistroLovCuentaCredito, infoRegistroLovCentroCostoDebito, infoRegistroLovCentroCostoCredito,
            infoRegistroLovGrupoConcepto, infoRegistroLovTipoTrabajador, infoRegistroLovTipoContrato, infoRegistroLovReformaLaboral, infoRegistroLovFormula, infoRegistroLovFormulasConceptos;
    ////
    public boolean activarLOV;

    public ControlDetalleConcepto() {
        System.err.println("ControlDetalleConcepto() : 1");
        altoTablaVigenciaCuenta = "105";
        altoTablaVigenciaGrupoC = "120";
        altoTablaVigenciaConceptoTT = "105";
        altoTablaVigenciaConceptoTC = "105";
        altoTablaVigenciaConceptoRL = "105";
        altoTablaFormulaConcepto = "122";
        //
        paginaRetorno = "";
        formulaSeleccionada = true;
        conceptoActual = new Conceptos();

        listFormulasConceptos = null;
        listVigenciasConceptosRLConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasGruposConceptos = null;
        listVigenciasCuentasConcepto = null;

        comportamientoConcepto = "";
        fechaParametro = new Date(1, 1, 0);

        permitirIndexVigenciaCuenta = true;
        permitirIndexVigenciaGrupoConcepto = true;
        permitirIndexVigenciaConceptoTT = true;
        permitirIndexVigenciaConceptoTC = true;
        permitirIndexVigenciaConceptoRL = true;
        permitirIndexFormulasConceptos = true;

        lovTiposCentrosCostos = null;
        lovTiposContratos = null;
        lovCuentas = null;
        lovCentrosCostos = null;
        lovReformasLaborales = null;
        lovFormulas = null;
        lovFormulasConceptos = null;

        tipoCentroCostoSeleccionadoLOV = null;
        tipoTrabajadorSeleccionadoLOV = null;
        tipoContratoSeleccionadoLOV = null;
        cuentaSeleccionadaLOV = null;
        centroCostoSeleccionadoLOV = null;
        reformaLaboralSeleccionadoLOV = null;
        formulaSeleccionadoLOV = null;
        formulaConceptoSeleccionadoLOV = null;

        nombreExportar = "";
        nombreTablaRastro = "";
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        backUp = null;

        aceptar = true;
        k = 0;

        listVigenciasCuentasBorrar = new ArrayList<VigenciasCuentas>();
        listVigenciasCuentasCrear = new ArrayList<VigenciasCuentas>();
        listVigenciasCuentasModificar = new ArrayList<VigenciasCuentas>();

        listVigenciasGruposConceptosBorrar = new ArrayList<VigenciasGruposConceptos>();
        listVigenciasGruposConceptosCrear = new ArrayList<VigenciasGruposConceptos>();
        listVigenciasGruposConceptosModificar = new ArrayList<VigenciasGruposConceptos>();

        listVigenciasConceptosTTBorrar = new ArrayList<VigenciasConceptosTT>();
        listVigenciasConceptosTTCrear = new ArrayList<VigenciasConceptosTT>();
        listVigenciasConceptosTTModificar = new ArrayList<VigenciasConceptosTT>();

        listVigenciasConceptosTCBorrar = new ArrayList<VigenciasConceptosTC>();
        listVigenciasConceptosTCCrear = new ArrayList<VigenciasConceptosTC>();
        listVigenciasConceptosTCModificar = new ArrayList<VigenciasConceptosTC>();

        listVigenciasConceptosRLBorrar = new ArrayList<VigenciasConceptosRL>();
        listVigenciasConceptosRLCrear = new ArrayList<VigenciasConceptosRL>();
        listVigenciasConceptosRLModificar = new ArrayList<VigenciasConceptosRL>();

        listFormulasConceptosBorrar = new ArrayList<FormulasConceptos>();
        listFormulasConceptosCrear = new ArrayList<FormulasConceptos>();
        listFormulasConceptosModificar = new ArrayList<FormulasConceptos>();

        editarVigenciaCuenta = new VigenciasCuentas();
        editarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        editarVigenciaConceptoTT = new VigenciasConceptosTT();
        editarVigenciaConceptoTC = new VigenciasConceptosTC();
        editarVigenciaConceptoRL = new VigenciasConceptosRL();
        editarFormulasConceptos = new FormulasConceptos();

        cualCeldaVigenciaCuenta = -1;
        cualCeldaVigenciaGrupoConcepto = -1;
        cualCeldaVigenciaConceptoTT = -1;
        cualCeldaVigenciaConceptoTC = -1;
        cualCeldaVigenciaConceptoRL = -1;
        cualCeldaFormulasConceptos = -1;

        tipoListaVigenciaCuenta = 0;
        tipoListaVigenciaGrupoConcepto = 0;
        tipoListaVigenciaConceptoTT = 0;
        tipoListaVigenciaConceptoTC = 0;
        tipoListaVigenciaConceptoRL = 0;
        tipoListaFormulasConceptos = 0;

        guardado = true;

        nuevaVigenciaCuenta = new VigenciasCuentas();
        nuevaVigenciaCuenta.setConsolidadorc(new CentrosCostos());
        nuevaVigenciaCuenta.setConsolidadord(new CentrosCostos());
        nuevaVigenciaCuenta.setCuentac(new Cuentas());
        nuevaVigenciaCuenta.setCuentad(new Cuentas());
        nuevaVigenciaCuenta.setTipocc(new TiposCentrosCostos());
        nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        nuevaVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
        nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
        nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
        nuevaVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
        nuevaFormulasConceptos = new FormulasConceptos();
        nuevaFormulasConceptos.setFormula(new Formulas());

        banderaVigenciaCuenta = 0;
        banderaVigenciaGrupoConcepto = 0;
        banderaVigenciaConceptoTT = 0;
        banderaVigenciaConceptoTC = 0;
        banderaVigenciaConceptoRL = 0;
        banderaFormulasConceptos = 0;

        nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
        nombreXML = "ConceptosXML";

        duplicarVigenciaCuenta = new VigenciasCuentas();
        duplicarVigenciaCuenta.setConsolidadorc(new CentrosCostos());
        duplicarVigenciaCuenta.setConsolidadord(new CentrosCostos());
        duplicarVigenciaCuenta.setCuentac(new Cuentas());
        duplicarVigenciaCuenta.setCuentad(new Cuentas());
        duplicarVigenciaCuenta.setTipocc(new TiposCentrosCostos());

        duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
        duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
        duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
        duplicarFormulasConceptos = new FormulasConceptos();

        cambiosVigenciaCuenta = false;
        cambiosVigenciaGrupoConcepto = false;
        cambiosVigenciaConceptoTT = false;
        cambiosVigenciaConceptoTC = false;
        cambiosVigenciaConceptoRL = false;
        cambiosFormulasConceptos = false;

        vigenciaCuentaSeleccionada = null;
        vigenciaGrupoCoSeleccionada = null;
        vigenciaConceptoRLSeleccionada = null;
        vigenciaConceptoTCSeleccionada = null;
        vigenciaConceptoTTSeleccionada = null;
        vigFormulaConceptoSeleccionada = null;

        infoRegistroLovTipoCentroCosto = "0";
        infoRegistroLovCuentaDebito = "0";
        infoRegistroLovCuentaCredito = "0";
        infoRegistroLovCentroCostoDebito = "0";
        infoRegistroLovCentroCostoCredito = "0";
        infoRegistroLovGrupoConcepto = "0";
        infoRegistroLovTipoTrabajador = "0";
        infoRegistroLovTipoContrato = "0";
        infoRegistroLovReformaLaboral = "0";
        infoRegistroLovFormula = "0";
        infoRegistroLovFormulasConceptos = "0";

        infoRegistroCuenta = "0";
        infoRegistroGrupoC = "0";
        infoRegistroConceptoTT = "0";
        infoRegistroConceptoTC = "0";
        infoRegistroConceptoRL = "0";
        infoRegistroFormulaConcepto = "0";

        activarLOV = true;
        System.err.println("ControlDetalleConcepto() : 2");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        System.out.println("Entro en inicializarAdministrador()");
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarDetalleConcepto.obtenerConexion(ses.getId());
            System.out.println("FacesContext x : " + x.toString());
            administrarRastros.obtenerConexion(ses.getId());
            System.out.println("HttpSession ses : " + ses.toString());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void obtenerConcepto(BigInteger secuencia) {
        conceptoActual = administrarDetalleConcepto.consultarConceptoActual(secuencia);
        if (conceptoActual != null) {
            Long auto = administrarDetalleConcepto.contarFormulasConceptosConcepto(conceptoActual.getSecuencia());
            Long semi = administrarDetalleConcepto.contarFormulasNovedadesConcepto(conceptoActual.getSecuencia());
            System.out.println("obtenerConcepto() auto : " + auto);
            System.out.println("obtenerConcepto() semi : " + semi);
            if ((auto == 0 && semi == 0) || auto == null || semi == null) {
                comportamientoConcepto = conceptoActual.getInfoDetalleConcepto() + "MANUAL";
            } else {
                if (auto > 0 && semi > 0) {
                    comportamientoConcepto = conceptoActual.getInfoDetalleConcepto() + "AUTOMATICO";
                }
                if (auto > 0 && semi == 0) {
                    comportamientoConcepto = conceptoActual.getInfoDetalleConcepto() + "AUTOMATICO";
                }
                if (auto == 0 && semi > 0) {
                    comportamientoConcepto = conceptoActual.getInfoDetalleConcepto() + "SEMI-AUTOMATICO";
                }
            }
        }
        System.out.println("1");
        getListFormulasConceptos();
        getListVigenciasConceptosRLConcepto();
        getListVigenciasConceptosTTConcepto();
        getListVigenciasConceptosTCConcepto();
        getListVigenciasGruposConceptos();
        getListVigenciasCuentasConcepto();
        contarRegistrosConceptoRL();
        contarRegistrosConceptoTT();
        contarRegistrosConceptoTC();
        contarRegistrosCuentas();
        contarRegistrosFormulaConcepto();
        contarRegistrosGrupoC();
        System.out.println("2");
        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptos = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptos = null;
        formulaSeleccionada = true;
        try{
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCuenta");
        context.update("form:datosVigenciaGrupoConcepto");
        context.update("form:datosVigenciaConceptoTT");
        context.update("form:datosVigenciaConceptoTC");
        context.update("form:datosVigenciaConceptoRL");
        context.update("form:datosFormulaConcepto");
        System.out.println("3");
        }        
        catch(Exception e){
            System.out.println("obtenerConcepto() Entro al Catch, Error : " + e.toString()); 
        }
    }

    public void modificarVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaCuenta(0);
        if (retorno == true) {
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                context.update("form:datosVigenciaCuenta");
                cambiosVigenciaCuenta = true;
            } else {
                vigenciaCuentaSeleccionada.getTipocc().setNombre(auxVC_TipoCC);
                vigenciaCuentaSeleccionada.getCuentad().setDescripcion(auxVC_DescDeb);
                vigenciaCuentaSeleccionada.getCuentad().setCodigo(auxVC_Debito);
                vigenciaCuentaSeleccionada.getConsolidadord().setNombre(auxVC_ConsDeb);
                vigenciaCuentaSeleccionada.getCuentac().setDescripcion(auxVC_DescCre);
                vigenciaCuentaSeleccionada.getCuentac().setCodigo(auxVC_Credito);
                vigenciaCuentaSeleccionada.getConsolidadorc().setNombre(auxVC_ConsCre);

                context.update("form:datosVigenciaCuenta");
                context.execute("errorRegNuevo.show()");
            }
        }
    }

    public void modificarVigenciaCuenta(VigenciasCuentas cuenta, String columnCambio, String valor) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        vigenciaCuentaSeleccionada = cuenta;
        RequestContext context = RequestContext.getCurrentInstance();

        if (columnCambio.equalsIgnoreCase("TIPOCC")) {
            vigenciaCuentaSeleccionada.getTipocc().setNombre(auxVC_TipoCC);

            for (int i = 0; i < lovTiposCentrosCostos.size(); i++) {
                if (lovTiposCentrosCostos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setTipocc(lovTiposCentrosCostos.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovTipoCentroCosto(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("CODDEBITO")) {
            vigenciaCuentaSeleccionada.getCuentad().setCodigo(auxVC_Debito);

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setCuentad(lovCuentas.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCuentaDebito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("DESDEBITO")) {
            vigenciaCuentaSeleccionada.getCuentad().setDescripcion(auxVC_DescDeb);

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getDescripcion().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setCuentad(lovCuentas.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCuentaDebito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("CONSOLIDADORDEBITO")) {
            vigenciaCuentaSeleccionada.getConsolidadord().setNombre(auxVC_ConsDeb);

            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setConsolidadord(lovCentrosCostos.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCentroCostoDebito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }
        }

        if (columnCambio.equalsIgnoreCase("CODCREDITO")) {
            vigenciaCuentaSeleccionada.getCuentac().setCodigo(auxVC_Credito);

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setCuentac(lovCuentas.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCuentaCredito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("DESCREDITO")) {
            vigenciaCuentaSeleccionada.getCuentac().setDescripcion(auxVC_DescCre);

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getDescripcion().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setCuentac(lovCuentas.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCuentaCredito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("CONSOLIDADORCREDITO")) {
            vigenciaCuentaSeleccionada.getConsolidadorc().setNombre(auxVC_ConsCre);

            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCuentaSeleccionada.setConsolidadorc(lovCentrosCostos.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovCentroCostoCredito(0);
                permitirIndexVigenciaCuenta = false;
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            vigenciaCuentaSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            cambiosVigenciaCuenta = true;
        }
        context.update("form:datosVigenciaCuenta");
    }

    //////////////VigenciaGrupoConcepto////////////////
    public void modificarVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaGrupoConcepto(0);
        if (retorno == true) {
            if (!listVigenciasGruposConceptosCrear.contains(vigenciaGrupoCoSeleccionada)) {
                if (listVigenciasGruposConceptosModificar.isEmpty()) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                } else if (!listVigenciasGruposConceptosModificar.contains(vigenciaGrupoCoSeleccionada)) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }

            context.update("form:datosVigenciaGrupoConcepto");
            cambiosVigenciaGrupoConcepto = true;
        } else {
            vigenciaGrupoCoSeleccionada.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            vigenciaGrupoCoSeleccionada.getGrupoconcepto().setDescripcion(auxVGC_Descripcion);

            context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoConcepto");
            context.execute("errorRegNuevo.show()");
        }
    }

    public void modificarVigenciaGrupoConcepto(VigenciasGruposConceptos grupoC, String columnCambio, String valor) {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        vigenciaGrupoCoSeleccionada = grupoC;
        if (columnCambio.equalsIgnoreCase("CODIGO")) {
            vigenciaGrupoCoSeleccionada.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);

            for (int i = 0; i < lovGruposConceptos.size(); i++) {
                if (lovGruposConceptos.get(i).getStrCodigo().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaGrupoCoSeleccionada.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovGrupoConcepto(0);
                permitirIndexVigenciaGrupoConcepto = false;
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (columnCambio.equalsIgnoreCase("DESCRIPCION")) {
            vigenciaGrupoCoSeleccionada.getGrupoconcepto().setDescripcion(auxVGC_Descripcion);

            for (int i = 0; i < lovGruposConceptos.size(); i++) {
                if (lovGruposConceptos.get(i).getDescripcion().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaGrupoCoSeleccionada.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovGrupoConcepto(0);
                permitirIndexVigenciaGrupoConcepto = false;
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listVigenciasGruposConceptosCrear.contains(vigenciaGrupoCoSeleccionada)) {
                if (listVigenciasGruposConceptosModificar.isEmpty()) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                } else if (!listVigenciasGruposConceptosModificar.contains(vigenciaGrupoCoSeleccionada)) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambiosVigenciaGrupoConcepto = true;
        }
        context.update("form:datosVigenciaGrupoConcepto");
    }

    //////////////VigenciaConceptoTT////////////////
    public void modificarVigenciaConceptoTT() {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaConceptoTT(0);
        if (retorno == true) {
            if (!listVigenciasConceptosTTCrear.contains(vigenciaConceptoTTSeleccionada)) {
                if (listVigenciasConceptosTTModificar.isEmpty()) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                } else if (!listVigenciasConceptosTTModificar.contains(vigenciaConceptoTTSeleccionada)) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            context.update("form:datosVigenciaConceptoTT");
            cambiosVigenciaConceptoTT = true;
        } else {
            vigenciaConceptoTTSeleccionada.getTipotrabajador().setNombre(auxVCTT_Descripcion);

            context.update("form:datosVigenciaConceptoTT");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////VigenciaConceptoTT////////////////
    public void modificarVigenciaConceptoTT(VigenciasConceptosTT conceptoTT, String columnCambio, String valor) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        vigenciaConceptoTTSeleccionada = conceptoTT;
        RequestContext context = RequestContext.getCurrentInstance();
        if (columnCambio.equalsIgnoreCase("TRABAJADOR")) {
            vigenciaConceptoTTSeleccionada.getTipotrabajador().setNombre(auxVCTT_Descripcion);

            for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                if (lovTiposTrabajadores.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaConceptoTTSeleccionada.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
            } else {
                contarRegistrosLovTipoTrabajador(0);
                permitirIndexVigenciaConceptoTT = false;
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listVigenciasConceptosTTCrear.contains(vigenciaConceptoTTSeleccionada)) {
                if (listVigenciasConceptosTTModificar.isEmpty()) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                } else if (!listVigenciasConceptosTTModificar.contains(vigenciaConceptoTTSeleccionada)) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambiosVigenciaConceptoTT = true;
        }
        context.update("form:datosVigenciaConceptoTT");
    }

    //////////////VigenciaConceptoTC////////////////
    public void modificarVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaConceptoTC(0);
        if (retorno == true) {
            if (!listVigenciasConceptosTCCrear.contains(vigenciaConceptoTCSeleccionada)) {
                if (listVigenciasConceptosTCModificar.isEmpty()) {
                    listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                } else if (!listVigenciasConceptosTCModificar.contains(vigenciaConceptoTCSeleccionada)) {
                    listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            context.update("form:datosVigenciaConceptoTC");
            cambiosVigenciaConceptoTC = true;
        } else {
            vigenciaConceptoTCSeleccionada.getTipocontrato().setNombre(auxVCTC_Descripcion);
            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////VigenciaConceptoTC////////////////
    public void modificarVigenciaConceptoTC(VigenciasConceptosTC conceptoTC, String columnCambio, String valor) {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaConceptoTC(0);
        if (retorno == true) {
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            vigenciaConceptoTCSeleccionada = conceptoTC;
            if (columnCambio.equalsIgnoreCase("CONTRATO")) {
                vigenciaConceptoTCSeleccionada.getTipocontrato().setNombre(auxVCTC_Descripcion);

                for (int i = 0; i < lovTiposContratos.size(); i++) {
                    if (lovTiposContratos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigenciaConceptoTCSeleccionada.setTipocontrato(lovTiposContratos.get(indiceUnicoElemento));
                } else {
                    contarRegistrosLovTipoContrato(0);
                    permitirIndexVigenciaConceptoTC = false;
                    context.update("form:TipoContratosDialogo");
                    context.execute("TipoContratosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (!listVigenciasConceptosTCCrear.contains(vigenciaConceptoTCSeleccionada)) {
                    if (listVigenciasConceptosTCModificar.isEmpty()) {
                        listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                    } else if (!listVigenciasConceptosTCModificar.contains(vigenciaConceptoTCSeleccionada)) {
                        listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosVigenciaConceptoTC");
        } else {
            vigenciaConceptoTCSeleccionada.getTipocontrato().setNombre(auxVCTC_Descripcion);

            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////VigenciaConceptoRL////////////////
    public void modificarVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaConceptoRL(0);
        if (retorno == true) {
            if (!listVigenciasConceptosRLCrear.contains(vigenciaConceptoRLSeleccionada)) {
                if (listVigenciasConceptosRLModificar.isEmpty()) {
                    listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                } else if (!listVigenciasConceptosRLModificar.contains(vigenciaConceptoRLSeleccionada)) {
                    listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            context.update("form:datosVigenciaConceptoRL");
            cambiosVigenciaConceptoRL = true;
        } else {
            vigenciaConceptoRLSeleccionada.getTiposalario().setNombre(auxVCRL_Descripcion);
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////VigenciaConceptoRL////////////////
    public void modificarVigenciaConceptoRL(VigenciasConceptosRL conceptoRL, String columnCambio, String valor) {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosVigenciaConceptoRL(0);
        if (retorno == true) {
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            vigenciaConceptoRLSeleccionada = conceptoRL;
            if (columnCambio.equalsIgnoreCase("REFORMA")) {
                vigenciaConceptoRLSeleccionada.getTiposalario().setNombre(auxVCRL_Descripcion);

                for (int i = 0; i < lovReformasLaborales.size(); i++) {
                    if (lovReformasLaborales.get(i).getNombre().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigenciaConceptoRLSeleccionada.setTiposalario(lovReformasLaborales.get(indiceUnicoElemento));
                } else {
                    contarRegistrosLovReformaLaboral(0);
                    permitirIndexVigenciaConceptoRL = false;
                    context.update("form:ReformaLaboralDialogo");
                    context.execute("ReformaLaboralDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (!listVigenciasConceptosRLCrear.contains(vigenciaConceptoRLSeleccionada)) {
                    if (listVigenciasConceptosRLModificar.isEmpty()) {
                        listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                    } else if (!listVigenciasConceptosRLModificar.contains(vigenciaConceptoRLSeleccionada)) {
                        listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }

                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosVigenciaConceptoRL");
        } else {
            vigenciaConceptoRLSeleccionada.getTiposalario().setNombre(auxVCRL_Descripcion);
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////FormulasConceptos////////////////
    public void modificarFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosFormulasConceptos(0);
        if (retorno == true) {
            if (!listFormulasConceptosCrear.contains(vigFormulaConceptoSeleccionada)) {
                if (listFormulasConceptosModificar.isEmpty()) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                } else if (!listFormulasConceptosModificar.contains(vigFormulaConceptoSeleccionada)) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }

            context.update("form:datosFormulaConcepto");
            cambiosFormulasConceptos = true;
        } else {
            vigFormulaConceptoSeleccionada.getFormula().setNombrelargo(auxFC_Descripcion);
            vigFormulaConceptoSeleccionada.setStrOrden(auxFC_Orden);

            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
        }
    }

    //////////////FormulasConceptos////////////////
    public void modificarFormulasConceptos(FormulasConceptos formulaC, String columnCambio, String valor) {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarNuevosDatosFormulasConceptos(0);
        if (retorno == true) {
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            vigFormulaConceptoSeleccionada = formulaC;
            if (columnCambio.equalsIgnoreCase("FORMULA")) {
                vigFormulaConceptoSeleccionada.getFormula().setNombrelargo(auxFC_Descripcion);

                for (int i = 0; i < lovFormulas.size(); i++) {
                    if (lovFormulas.get(i).getNombrelargo().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigFormulaConceptoSeleccionada.setFormula(lovFormulas.get(indiceUnicoElemento));
                } else {
                    contarRegistrosLovFormula(0);
                    permitirIndexFormulasConceptos = false;
                    context.update("form:FormulasDialogo");
                    context.execute("FormulasDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (columnCambio.equalsIgnoreCase("ORDEN")) {
                vigFormulaConceptoSeleccionada.setStrOrden(auxFC_Orden);

                for (int i = 0; i < lovFormulasConceptos.size(); i++) {
                    if (lovFormulasConceptos.get(i).getStrOrden().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigFormulaConceptoSeleccionada.setStrOrden(lovFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                } else {
                    contarRegistrosLovFormCon(0);
                    permitirIndexFormulasConceptos = false;
                    context.update("form:FormulaConceptoDialogo");
                    context.execute("FormulaConceptoDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (!listFormulasConceptosCrear.contains(vigFormulaConceptoSeleccionada)) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                    } else if (!listFormulasConceptosModificar.contains(vigFormulaConceptoSeleccionada)) {
                        listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosFormulaConcepto");
        } else {
            vigFormulaConceptoSeleccionada.getFormula().setNombrelargo(auxFC_Descripcion);
            vigFormulaConceptoSeleccionada.setStrOrden(auxFC_Orden);
            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    public void valoresBackupAutocompletarGeneral(int tipoNuevo, String Campo, int tabla) {
        if (tabla == 0) {
            if (Campo.equals("TIPOCC")) {
                if (tipoNuevo == 1) {
                    auxVC_TipoCC = nuevaVigenciaCuenta.getTipocc().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVC_TipoCC = duplicarVigenciaCuenta.getTipocc().getNombre();
                }
            } else if (Campo.equals("CODDEBITO")) {
                if (tipoNuevo == 1) {
                    auxVC_Debito = nuevaVigenciaCuenta.getCuentad().getCodigo();
                } else if (tipoNuevo == 2) {
                    auxVC_Debito = duplicarVigenciaCuenta.getCuentad().getCodigo();
                }
            } else if (Campo.equals("DESDEBITO")) {
                if (tipoNuevo == 1) {
                    auxVC_DescDeb = nuevaVigenciaCuenta.getCuentad().getDescripcion();
                } else if (tipoNuevo == 2) {
                    auxVC_DescDeb = duplicarVigenciaCuenta.getCuentad().getDescripcion();
                }
            } else if (Campo.equals("CONSOLIDADORDEBITO")) {
                if (tipoNuevo == 1) {
                    auxVC_ConsDeb = nuevaVigenciaCuenta.getConsolidadord().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVC_ConsDeb = duplicarVigenciaCuenta.getConsolidadord().getNombre();
                }
            } else if (Campo.equals("CODCREDITO")) {
                if (tipoNuevo == 1) {
                    auxVC_Credito = nuevaVigenciaCuenta.getCuentac().getCodigo();
                } else if (tipoNuevo == 2) {
                    auxVC_Credito = duplicarVigenciaCuenta.getCuentac().getCodigo();
                }
            } else if (Campo.equals("DESCREDITO")) {
                if (tipoNuevo == 1) {
                    auxVC_DescCre = nuevaVigenciaCuenta.getCuentad().getDescripcion();
                } else if (tipoNuevo == 2) {
                    auxVC_DescCre = duplicarVigenciaCuenta.getCuentad().getDescripcion();
                }
            } else if (Campo.equals("CONSOLIDADORCREDITO")) {
                if (tipoNuevo == 1) {
                    auxVC_ConsCre = nuevaVigenciaCuenta.getConsolidadorc().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVC_ConsCre = duplicarVigenciaCuenta.getConsolidadorc().getNombre();
                }
            }
        }
        if (tabla == 1) {
            if (Campo.equals("CODIGO")) {
                if (tipoNuevo == 1) {
                    auxVGC_Codigo = nuevaVigenciaGrupoConcepto.getGrupoconcepto().getStrCodigo();
                } else if (tipoNuevo == 2) {
                    auxVGC_Codigo = duplicarVigenciaGrupoConcepto.getGrupoconcepto().getStrCodigo();
                }
            } else if (Campo.equals("DESCRIPCION")) {
                if (tipoNuevo == 1) {
                    auxVGC_Descripcion = nuevaVigenciaGrupoConcepto.getGrupoconcepto().getDescripcion();
                } else if (tipoNuevo == 2) {
                    auxVGC_Descripcion = duplicarVigenciaGrupoConcepto.getGrupoconcepto().getDescripcion();
                }
            }
        }
        if (tabla == 2) {
            if (Campo.equals("TRABAJADOR")) {
                if (tipoNuevo == 1) {
                    auxVCTT_Descripcion = nuevaVigenciaConceptoTT.getTipotrabajador().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVCTT_Descripcion = duplicarVigenciaConceptoTT.getTipotrabajador().getNombre();
                }
            }
        }
        if (tabla == 3) {
            if (Campo.equals("CONTRATO")) {
                if (tipoNuevo == 1) {
                    auxVCTC_Descripcion = nuevaVigenciaConceptoTC.getTipocontrato().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVCTC_Descripcion = duplicarVigenciaConceptoTC.getTipocontrato().getNombre();
                }
            }
        }
        if (tabla == 4) {
            if (Campo.equals("REFORMA")) {
                if (tipoNuevo == 1) {
                    auxVCRL_Descripcion = nuevaVigenciaConceptoRL.getTiposalario().getNombre();
                } else if (tipoNuevo == 2) {
                    auxVCRL_Descripcion = duplicarVigenciaConceptoRL.getTiposalario().getNombre();
                }
            }
        }
        if (tabla == 5) {
            if (Campo.equals("FORMULA")) {
                if (tipoNuevo == 1) {
                    auxFC_Descripcion = nuevaFormulasConceptos.getFormula().getNombrelargo();
                } else if (tipoNuevo == 2) {
                    auxFC_Descripcion = duplicarFormulasConceptos.getFormula().getNombrelargo();
                }
            }
            if (Campo.equals("ORDEN")) {
                if (tipoNuevo == 1) {
                    auxFC_Orden = nuevaFormulasConceptos.getStrOrden();
                } else if (tipoNuevo == 2) {
                    auxFC_Orden = duplicarFormulasConceptos.getStrOrden();
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaCuenta(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOCC")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getTipocc().setNombre(auxVC_TipoCC);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getTipocc().setNombre(auxVC_TipoCC);
            }
            for (int i = 0; i < lovTiposCentrosCostos.size(); i++) {
                if (lovTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setTipocc(lovTiposCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoCCVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setTipocc(lovTiposCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoCCVC");
                }
            } else {
                contarRegistrosLovTipoCentroCosto(0);
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTipoCCVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoCCVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CODDEBITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getCuentad().setCodigo(auxVC_Debito);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getCuentad().setCodigo(auxVC_Debito);
            }
            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentad(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentad(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
            } else {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("DESDEBITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getCuentad().setDescripcion(auxVC_DescDeb);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getCuentad().setDescripcion(auxVC_DescDeb);
            }
            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentad(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentad(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
            } else {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CONSOLIDADORDEBITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getConsolidadord().setNombre(auxVC_ConsDeb);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getConsolidadord().setNombre(auxVC_ConsDeb);
            }
            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setConsolidadord(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaConsoliDebVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setConsolidadord(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaConsoliDebVC");
                }
            } else {
                contarRegistrosLovCentroCostoDebito(0);
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaConsoliDebVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicaConsoliDebVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CODCREDITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getCuentac().setCodigo(auxVC_Credito);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getCuentac().setCodigo(auxVC_Credito);
            }
            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentac(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentac(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
            } else {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("DESCREDITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getCuentac().setDescripcion(auxVC_DescCre);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getCuentac().setDescripcion(auxVC_DescCre);
            }
            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentac(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentac(lovCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
            } else {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CONSOLIDADORCREDITO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getConsolidadorc().setNombre(auxVC_ConsCre);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getConsolidadorc().setNombre(auxVC_ConsCre);
            }
            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setConsolidadorc(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaConsoliCreVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setConsolidadorc(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaConsoliCreVC");
                }
            } else {
                contarRegistrosLovCentroCostoCredito(0);
                context.update("form:CentroCostoCDialogo");
                context.execute("CentroCostoCDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaConsoliCreVC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicaConsoliCreVC");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaGrupoConcepto(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaGrupoConcepto.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaGrupoConcepto.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            }
            for (int i = 0; i < lovGruposConceptos.size(); i++) {
                if (lovGruposConceptos.get(i).getStrCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaGrupoConcepto.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
            } else {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("DESCRIPCION")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaGrupoConcepto.getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaGrupoConcepto.getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            }
            for (int i = 0; i < lovGruposConceptos.size(); i++) {
                if (lovGruposConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaGrupoConcepto.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(lovGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
            } else {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaConceptoTT(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TRABAJADOR")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoTT.getTipotrabajador().setNombre(auxVCTT_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoTT.getTipotrabajador().setNombre(auxVCTT_Descripcion);
            }
            for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoTT.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTrabajadorVCTT");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoTT.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTrabajadorVCTT");
                }
            } else {
                contarRegistrosLovTipoTrabajador(0);
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTrabajadorVCTT");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTrabajadorVCTT");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaConceptoTC(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONTRATO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoTC.getTipocontrato().setNombre(auxVCTC_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoTC.getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            for (int i = 0; i < lovTiposContratos.size(); i++) {
                if (lovTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoTC.setTipocontrato(lovTiposContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaContratoVCTC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoTC.setTipocontrato(lovTiposContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarContratoVCTC");
                }
            } else {
                contarRegistrosLovTipoContrato(0);
                context.update("form:TipoContratosDialogo");
                context.execute("TipoContratosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaContratoVCTC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarContratoVCTC");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaConceptoRL(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMA")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoRL.getTiposalario().setNombre(auxVCRL_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoRL.getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            for (int i = 0; i < lovReformasLaborales.size(); i++) {
                if (lovReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoRL.setTiposalario(lovReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaReformaVCRL");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoRL.setTiposalario(lovReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarReformaVCRL");
                }
            } else {
                contarRegistrosLovReformaLaboral(0);
                context.update("form:ReformaLaboralDialogo");
                context.execute("ReformaLaboralDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaReformaVCRL");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarReformaVCRL");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoFormulasConceptos(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        cargarLOVs();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoNuevo == 1) {
                nuevaFormulasConceptos.getFormula().setNombrelargo(auxFC_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarFormulasConceptos.getFormula().setNombrelargo(auxFC_Descripcion);
            }
            for (int i = 0; i < lovFormulas.size(); i++) {
                if (lovFormulas.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulasConceptos.setFormula(lovFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaFormulaFC");
                } else if (tipoNuevo == 2) {
                    duplicarFormulasConceptos.setFormula(lovFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarFormulaFC");
                }
            } else {
                contarRegistrosLovFormula(0);
                context.update("form:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaFormulaFC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarFormulaFC");
                }
            }
        }

        if (confirmarCambio.equalsIgnoreCase("ORDEN")) {
            if (tipoNuevo == 1) {
                nuevaFormulasConceptos.setStrOrden(auxFC_Orden);
            } else if (tipoNuevo == 2) {
                duplicarFormulasConceptos.setStrOrden(auxFC_Orden);
            }
            for (int i = 0; i < lovFormulasConceptos.size(); i++) {
                if (lovFormulasConceptos.get(i).getStrOrden().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulasConceptos.setStrOrden(lovFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    context.update("formularioDialogos:nuevaOrdenFC");
                } else if (tipoNuevo == 2) {
                    duplicarFormulasConceptos.setStrOrden(lovFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    context.update("formularioDialogos:duplicarOrdenFC");
                }
            } else {
                contarRegistrosLovFormCon(0);
                context.update("form:FormulaConceptoDialogo");
                context.execute("FormulaConceptoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaOrdenFC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarOrdenFC");
                }
            }
        }
    }

    public void cambiarIndiceVigenciaCuenta(VigenciasCuentas cuenta, int celda) {
        if (permitirIndexVigenciaCuenta == true) {
            cualCeldaVigenciaCuenta = celda;
            if (celda > 1) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigenciaCuentaSeleccionada = cuenta;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVC_FechaIni = vigenciaCuentaSeleccionada.getFechainicial();
            auxVC_FechaFin = vigenciaCuentaSeleccionada.getFechafinal();
            auxVC_TipoCC = vigenciaCuentaSeleccionada.getTipocc().getNombre();
            auxVC_DescDeb = vigenciaCuentaSeleccionada.getCuentad().getDescripcion();
            auxVC_Debito = vigenciaCuentaSeleccionada.getCuentad().getCodigo();
            auxVC_ConsDeb = vigenciaCuentaSeleccionada.getConsolidadord().getNombre();
            auxVC_DescCre = vigenciaCuentaSeleccionada.getCuentac().getDescripcion();
            auxVC_Credito = vigenciaCuentaSeleccionada.getCuentac().getCodigo();
            auxVC_ConsCre = vigenciaCuentaSeleccionada.getConsolidadorc().getNombre();

            vigenciaConceptoTCSeleccionada = null;
            vigenciaConceptoTTSeleccionada = null;
            vigenciaGrupoCoSeleccionada = null;
            vigenciaConceptoRLSeleccionada = null;
            vigFormulaConceptoSeleccionada = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaGrupoConcepto");
//            context.update("form:datosVigenciaConceptoTT");
//            context.update("form:datosVigenciaConceptoTC");
//            context.update("form:datosVigenciaConceptoRL");
//            context.update("form:datosFormulaConcepto");
        }
    }

    public void cambiarIndiceVigenciaGrupoConcepto(VigenciasGruposConceptos grupoConcepto, int celda) {
        if (permitirIndexVigenciaGrupoConcepto == true) {
            cualCeldaVigenciaGrupoConcepto = celda;
            if (celda == 2 || celda == 3) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigenciaGrupoCoSeleccionada = grupoConcepto;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVGC_FechaIni = vigenciaGrupoCoSeleccionada.getFechainicial();
            auxVGC_FechaFin = vigenciaGrupoCoSeleccionada.getFechafinal();
            auxVGC_Codigo = vigenciaGrupoCoSeleccionada.getGrupoconcepto().getStrCodigo();
            auxVGC_Descripcion = vigenciaGrupoCoSeleccionada.getGrupoconcepto().getDescripcion();

            vigenciaConceptoTCSeleccionada = null;
            vigenciaConceptoTTSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            vigenciaConceptoRLSeleccionada = null;
            vigFormulaConceptoSeleccionada = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaCuenta");
//            context.update("form:datosVigenciaConceptoTT");
//            context.update("form:datosVigenciaConceptoTC");
//            context.update("form:datosVigenciaConceptoRL");
//            context.update("form:datosFormulaConcepto");
        }
    }

    public void cambiarIndiceVigenciaConceptoTT(VigenciasConceptosTT conceptoTT, int celda) {
        if (permitirIndexVigenciaConceptoTT == true) {
            cualCeldaVigenciaConceptoTT = celda;
            if (celda == 2) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigenciaConceptoTTSeleccionada = conceptoTT;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCTT_FechaIni = vigenciaConceptoTTSeleccionada.getFechainicial();
            auxVCTT_FechaFin = vigenciaConceptoTTSeleccionada.getFechafinal();
            auxVCTT_Descripcion = vigenciaConceptoTTSeleccionada.getTipotrabajador().getNombre();

            vigenciaConceptoTCSeleccionada = null;
            vigenciaGrupoCoSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            vigenciaConceptoRLSeleccionada = null;
            vigFormulaConceptoSeleccionada = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaCuenta");
//            context.update("form:datosVigenciaGrupoConcepto");
//            context.update("form:datosVigenciaConceptoTC");
//            context.update("form:datosVigenciaConceptoRL");
//            context.update("form:datosFormulaConcepto");
        }
    }

    public void cambiarIndiceVigenciaConceptoTC(VigenciasConceptosTC conceptoTC, int celda) {
        if (permitirIndexVigenciaConceptoTC == true) {
            cualCeldaVigenciaConceptoTC = celda;
            if (celda == 2) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigenciaConceptoTCSeleccionada = conceptoTC;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCTC_FechaIni = vigenciaConceptoTCSeleccionada.getFechainicial();
            auxVCTC_FechaFin = vigenciaConceptoTCSeleccionada.getFechafinal();
            auxVCTC_Descripcion = vigenciaConceptoTCSeleccionada.getTipocontrato().getNombre();

            vigenciaConceptoTTSeleccionada = null;
            vigenciaGrupoCoSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            vigenciaConceptoRLSeleccionada = null;
            vigFormulaConceptoSeleccionada = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaCuenta");
//            context.update("form:datosVigenciaGrupoConcepto");
//            context.update("form:datosVigenciaConceptoTT");
//            context.update("form:datosVigenciaConceptoRL");
//            context.update("form:datosFormulaConcepto");
        }
    }

    public void cambiarIndiceVigenciaConceptoRL(VigenciasConceptosRL conceptoRL, int celda) {
        if (permitirIndexVigenciaConceptoRL == true) {
            cualCeldaVigenciaConceptoRL = celda;
            if (celda == 2) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigenciaConceptoRLSeleccionada = conceptoRL;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCRL_FechaIni = vigenciaConceptoRLSeleccionada.getFechainicial();
            auxVCRL_FechaFin = vigenciaConceptoRLSeleccionada.getFechafinal();
            auxVCRL_Descripcion = vigenciaConceptoRLSeleccionada.getTiposalario().getNombre();

            vigenciaConceptoTTSeleccionada = null;
            vigenciaGrupoCoSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            vigenciaConceptoTCSeleccionada = null;
            vigFormulaConceptoSeleccionada = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaCuenta");
//            context.update("form:datosVigenciaGrupoConcepto");
//            context.update("form:datosVigenciaConceptoTT");
//            context.update("form:datosVigenciaConceptoTC");
//            context.update("form:datosFormulaConcepto");
        }
    }

    public void cambiarIndiceFormulasConceptos(FormulasConceptos formulaC, int celda) {
        if (permitirIndexFormulasConceptos == true) {
            cualCeldaFormulasConceptos = celda;
            if (celda == 2 || celda == 3) {
                activarBotonLOV();
            } else {
                anularBotonLOV();
            }
            vigFormulaConceptoSeleccionada = formulaC;
            actualFormulaConcepto = vigFormulaConceptoSeleccionada;
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxFC_FechaIni = vigFormulaConceptoSeleccionada.getFechainicial();
            auxFC_FechaFin = vigFormulaConceptoSeleccionada.getFechafinal();
            auxFC_Descripcion = vigFormulaConceptoSeleccionada.getFormula().getNombrelargo();
            auxFC_Orden = vigFormulaConceptoSeleccionada.getStrOrden();

            vigenciaConceptoTTSeleccionada = null;
            vigenciaConceptoRLSeleccionada = null;
            vigenciaGrupoCoSeleccionada = null;
            vigenciaCuentaSeleccionada = null;
            vigenciaConceptoTCSeleccionada = null;
            formulaSeleccionada = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
//            context.update("form:datosVigenciaCuenta");
//            context.update("form:datosVigenciaGrupoConcepto");
//            context.update("form:datosVigenciaConceptoTT");
//            context.update("form:datosVigenciaConceptoTC");
//            context.update("form:datosVigenciaConceptoRL");
        }
    }

    public boolean validarFechasRegistroVigenciaCuenta(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasCuentas auxiliar = vigenciaCuentaSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVigenciaCuenta.getFechainicial().after(fechaParametro) && (nuevaVigenciaCuenta.getFechainicial().before(nuevaVigenciaCuenta.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaCuenta.getFechainicial().after(fechaParametro) && (duplicarVigenciaCuenta.getFechainicial().before(duplicarVigenciaCuenta.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroVigenciaGrupoConcepto(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasGruposConceptos auxiliar = vigenciaGrupoCoSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVigenciaGrupoConcepto.getFechainicial().after(fechaParametro) && (nuevaVigenciaGrupoConcepto.getFechainicial().before(nuevaVigenciaGrupoConcepto.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaGrupoConcepto.getFechainicial().after(fechaParametro) && (duplicarVigenciaGrupoConcepto.getFechainicial().before(duplicarVigenciaGrupoConcepto.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroVigenciaConceptoTT(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTT auxiliar = vigenciaConceptoTTSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVigenciaConceptoTT.getFechainicial().after(fechaParametro) && (nuevaVigenciaConceptoTT.getFechainicial().before(nuevaVigenciaConceptoTT.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaConceptoTT.getFechainicial().after(fechaParametro) && (duplicarVigenciaConceptoTT.getFechainicial().before(duplicarVigenciaConceptoTT.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroVigenciaConceptoTC(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTC auxiliar = vigenciaConceptoTCSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVigenciaConceptoTC.getFechainicial().after(fechaParametro) && (nuevaVigenciaConceptoTC.getFechainicial().before(nuevaVigenciaConceptoTC.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaConceptoTC.getFechainicial().after(fechaParametro) && (duplicarVigenciaConceptoTC.getFechainicial().before(duplicarVigenciaConceptoTC.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroVigenciaConceptoRL(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosRL auxiliar = vigenciaConceptoRLSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaVigenciaConceptoRL.getFechainicial().after(fechaParametro) && (nuevaVigenciaConceptoRL.getFechainicial().before(nuevaVigenciaConceptoRL.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaConceptoRL.getFechainicial().after(fechaParametro) && (duplicarVigenciaConceptoRL.getFechainicial().before(duplicarVigenciaConceptoRL.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroFormulasConceptos(int i) {
        boolean retorno = false;
        if (i == 0) {
            FormulasConceptos auxiliar = vigFormulaConceptoSeleccionada;
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaFormulasConceptos.getFechainicial().after(fechaParametro) && (nuevaFormulasConceptos.getFechainicial().before(nuevaFormulasConceptos.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarFormulasConceptos.getFechainicial().after(fechaParametro) && (duplicarFormulasConceptos.getFechainicial().before(duplicarFormulasConceptos.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void modificacionesFechaVigenciaCuenta(VigenciasCuentas cuenta, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        VigenciasCuentas auxiliar = null;
        auxiliar = cuenta;
        vigenciaCuentaSeleccionada = cuenta;

        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(0);
            if (validacion == true) {
                cambiarIndiceVigenciaCuenta(cuenta, c);
                modificarVigenciaCuenta();
                context.update("form:datosVigenciaCuenta");
            } else {
                vigenciaCuentaSeleccionada.setFechainicial(auxVC_FechaIni);
                vigenciaCuentaSeleccionada.setFechafinal(auxVC_FechaFin);
                context.update("form:datosVigenciaCuenta");
                context.execute("errorFechasVC.show()");
//                vigenciaCuentaSeleccionada = null;
            }
        } else {
            vigenciaCuentaSeleccionada.setFechainicial(auxVC_FechaIni);
            vigenciaCuentaSeleccionada.setFechafinal(auxVC_FechaFin);
            context.update("form:datosVigenciaCuenta");
            context.execute("errorRegNuevo.show()");
//            vigenciaCuentaSeleccionada = null;
        }
    }

    public void modificacionesFechaVigenciaGrupoConcepto(VigenciasGruposConceptos grupoC, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        VigenciasGruposConceptos auxiliar = null;
        auxiliar = vigenciaGrupoCoSeleccionada;
        vigenciaGrupoCoSeleccionada = grupoC;
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(0);
            if (validacion == true) {
                cambiarIndiceVigenciaGrupoConcepto(grupoC, c);
                modificarVigenciaGrupoConcepto();
            } else {
                vigenciaGrupoCoSeleccionada.setFechainicial(auxVGC_FechaIni);
                vigenciaGrupoCoSeleccionada.setFechafinal(auxVGC_FechaFin);
                context.update("form:datosVigenciaGrupoConcepto");
                context.execute("errorFechasVC.show()");
//                vigenciaGrupoCoSeleccionada = null;
            }
        } else {
            vigenciaGrupoCoSeleccionada.setFechainicial(auxVGC_FechaIni);
            vigenciaGrupoCoSeleccionada.setFechafinal(auxVGC_FechaFin);

            context.update("form:datosVigenciaGrupoConcepto");
            context.execute("errorRegNuevo.show()");
//            vigenciaGrupoCoSeleccionada = null;
        }
    }

    public void modificacionesFechaVigenciaConceptoTT(VigenciasConceptosTT conceptoTT, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        VigenciasConceptosTT auxiliar = null;
        auxiliar = conceptoTT;
        vigenciaConceptoTTSeleccionada = conceptoTT;

        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoTT(conceptoTT, c);
                modificarVigenciaConceptoTT();
            } else {
                vigenciaConceptoTTSeleccionada.setFechainicial(auxVCTT_FechaIni);
                vigenciaConceptoTTSeleccionada.setFechafinal(auxVCTT_FechaFin);
                context.update("form:datosVigenciaConceptoTT");
                context.execute("errorFechasVC.show()");
//                vigenciaConceptoTTSeleccionada = null;
            }
        } else {
            vigenciaConceptoTTSeleccionada.setFechainicial(auxVCTT_FechaIni);
            vigenciaConceptoTTSeleccionada.setFechafinal(auxVCTT_FechaFin);
            context.update("form:datosVigenciaConceptoTT");
            context.execute("errorRegNuevo.show()");
//            vigenciaConceptoTTSeleccionada = null;
        }
    }

    public void modificacionesFechaVigenciaConceptoTC(VigenciasConceptosTC conceptoTC, int c) {
        VigenciasConceptosTC auxiliar = null;
        auxiliar = conceptoTC;
        vigenciaConceptoTCSeleccionada = conceptoTC;

        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            RequestContext context = RequestContext.getCurrentInstance();
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoTC(conceptoTC, c);
                modificarVigenciaConceptoTC();
            } else {
                vigenciaConceptoTCSeleccionada.setFechainicial(auxVCTC_FechaIni);
                vigenciaConceptoTCSeleccionada.setFechafinal(auxVCTC_FechaFin);
                context.update("form:datosVigenciaConceptoTC");
                context.execute("errorFechasVC.show()");
//                vigenciaConceptoTCSeleccionada = null;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            vigenciaConceptoTCSeleccionada.setFechainicial(auxVCTC_FechaIni);
            vigenciaConceptoTCSeleccionada.setFechafinal(auxVCTC_FechaFin);
            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
//            vigenciaConceptoTCSeleccionada = null;
        }
    }

    public void modificacionesFechaVigenciaConceptoRL(VigenciasConceptosRL conceptoRL, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        VigenciasConceptosRL auxiliar = null;
        auxiliar = conceptoRL;
        vigenciaConceptoRLSeleccionada = conceptoRL;

        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoRL(conceptoRL, c);
                modificarVigenciaConceptoRL();
            } else {
                vigenciaConceptoRLSeleccionada.setFechainicial(auxVCRL_FechaIni);
                vigenciaConceptoRLSeleccionada.setFechafinal(auxVCRL_FechaFin);
                context.update("form:datosVigenciaConceptoRL");
                context.execute("errorFechasVC.show()");
//                vigenciaConceptoRLSeleccionada = null;
            }
        } else {
            vigenciaConceptoRLSeleccionada.setFechainicial(auxVCRL_FechaIni);
            vigenciaConceptoRLSeleccionada.setFechafinal(auxVCRL_FechaFin);
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
//            vigenciaConceptoRLSeleccionada = null;
        }
    }

    public void modificacionesFechaFormulasConceptos(FormulasConceptos formulaC, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        FormulasConceptos auxiliar = null;
        auxiliar = formulaC;
        vigFormulaConceptoSeleccionada = formulaC;

        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroFormulasConceptos(0);
            if (validacion == true) {
                cambiarIndiceFormulasConceptos(formulaC, c);
                modificarFormulasConceptos();
            } else {
                vigFormulaConceptoSeleccionada.setFechainicial(auxFC_FechaIni);
                vigFormulaConceptoSeleccionada.setFechafinal(auxFC_FechaFin);
                context.update("form:datosFormulaConcepto");
                context.execute("errorFechasVC.show()");
//                formulaConceptoSeleccionada = null;
            }
        } else {
            vigFormulaConceptoSeleccionada.setFechainicial(auxFC_FechaIni);
            vigFormulaConceptoSeleccionada.setFechafinal(auxFC_FechaFin);
            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
//            formulaConceptoSeleccionada = null;
        }
    }

    //GUARDAR
    /**
     */
    public void guardadoGeneral() {
        if (cambiosVigenciaCuenta == true) {
            guardarCambiosVigenciaCuenta();
        }
        if (cambiosVigenciaGrupoConcepto == true) {
            guardarCambiosVigenciaGrupoConcepto();
        }
        if (cambiosVigenciaConceptoTT == true) {
            guardarCambiosVigenciaConceptoTT();
        }
        if (cambiosVigenciaConceptoTC == true) {
            guardarCambiosVigenciaConceptoTC();
        }
        if (cambiosVigenciaConceptoRL == true) {
            guardarCambiosVigenciaConceptoRL();
        }
        if (cambiosFormulasConceptos == true) {
            guardarCambiosFormulasConceptos();
        }
    }

    public void guardarCambiosVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listVigenciasCuentasBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarVigenciasCuentas(listVigenciasCuentasBorrar);
                listVigenciasCuentasBorrar.clear();
            }
            if (!listVigenciasCuentasCrear.isEmpty()) {
                administrarDetalleConcepto.crearVigenciasCuentas(listVigenciasCuentasCrear);
                listVigenciasCuentasCrear.clear();
            }
            if (!listVigenciasCuentasModificar.isEmpty()) {
                administrarDetalleConcepto.modificarVigenciasCuentas(listVigenciasCuentasModificar);
                listVigenciasCuentasModificar.clear();
            }
            listVigenciasCuentasConcepto = null;
            context.update("form:datosVigenciaCuenta");
            k = 0;
            vigenciaCuentaSeleccionada = null;
            cambiosVigenciaCuenta = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Cuentas y Tipos CC con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosVigenciaCuenta : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Cuentas y Tipos CC, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listVigenciasGruposConceptosBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarVigenciasGruposConceptos(listVigenciasGruposConceptosBorrar);
                listVigenciasGruposConceptosBorrar.clear();
            }
            if (!listVigenciasGruposConceptosCrear.isEmpty()) {
                administrarDetalleConcepto.crearVigenciasGruposConceptos(listVigenciasGruposConceptosCrear);
                listVigenciasGruposConceptosCrear.clear();
            }
            if (!listVigenciasGruposConceptosModificar.isEmpty()) {
                administrarDetalleConcepto.modificarVigenciasGruposConceptos(listVigenciasGruposConceptosModificar);
                listVigenciasGruposConceptosModificar.clear();
            }
            listVigenciasCuentasConcepto = null;
            context.update("form:datosVigenciaGrupoConcepto");
            k = 0;
            vigenciaGrupoCoSeleccionada = null;
            cambiosVigenciaGrupoConcepto = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Grupos C/N/G con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosVigenciaGrupoConcepto : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Grupos C/N/G, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listVigenciasConceptosTTBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarVigenciasConceptosTT(listVigenciasConceptosTTBorrar);
                listVigenciasConceptosTTBorrar.clear();
            }
            if (!listVigenciasConceptosTTCrear.isEmpty()) {
                administrarDetalleConcepto.crearVigenciasConceptosTT(listVigenciasConceptosTTCrear);
                listVigenciasConceptosTTCrear.clear();
            }
            if (!listVigenciasConceptosTTModificar.isEmpty()) {
                administrarDetalleConcepto.modificarVigenciasConceptosTT(listVigenciasConceptosTTModificar);
                listVigenciasConceptosTTModificar.clear();
            }
            listVigenciasConceptosTTConcepto = null;
            context.update("form:datosVigenciaConceptoTT");
            k = 0;
            vigenciaConceptoTTSeleccionada = null;
            cambiosVigenciaConceptoTT = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Tipo Trabajador con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosVigenciaConceptoTT : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Tipo Trabajador, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listVigenciasConceptosTCBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarVigenciasConceptosTC(listVigenciasConceptosTCBorrar);
                listVigenciasConceptosTCBorrar.clear();
            }
            if (!listVigenciasConceptosTCCrear.isEmpty()) {
                administrarDetalleConcepto.crearVigenciasConceptosTC(listVigenciasConceptosTCCrear);
                listVigenciasConceptosTCCrear.clear();
            }
            if (!listVigenciasConceptosTCModificar.isEmpty()) {
                administrarDetalleConcepto.modificarVigenciasConceptosTC(listVigenciasConceptosTCModificar);
                listVigenciasConceptosTCModificar.clear();
            }
            listVigenciasConceptosTCConcepto = null;
            context.update("form:datosVigenciaConceptoTC");
            k = 0;
            vigenciaConceptoTCSeleccionada = null;
            cambiosVigenciaConceptoTC = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Tipo Contrato con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosVigenciaConceptoTC : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Tipo Contrato, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listVigenciasConceptosRLBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarVigenciasConceptosRL(listVigenciasConceptosRLBorrar);
                listVigenciasConceptosRLBorrar.clear();
            }
            if (!listVigenciasConceptosRLCrear.isEmpty()) {
                administrarDetalleConcepto.crearVigenciasConceptosRL(listVigenciasConceptosRLCrear);
                listVigenciasConceptosRLCrear.clear();
            }
            if (!listVigenciasConceptosRLModificar.isEmpty()) {
                administrarDetalleConcepto.modificarVigenciasConceptosRL(listVigenciasConceptosRLModificar);
                listVigenciasConceptosRLModificar.clear();
            }
            listVigenciasConceptosRLConcepto = null;
            context.update("form:datosVigenciaConceptoRL");
            k = 0;
            vigenciaConceptoRLSeleccionada = null;
            cambiosVigenciaConceptoRL = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Tipo Salario con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosVigenciaConceptoRL : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Tipo Salario, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listFormulasConceptosBorrar.isEmpty()) {
                administrarDetalleConcepto.borrarFormulasConceptos(listFormulasConceptosBorrar);
                listFormulasConceptosBorrar.clear();
            }
            if (!listFormulasConceptosCrear.isEmpty()) {
                administrarDetalleConcepto.crearFormulasConceptos(listFormulasConceptosCrear);
                listFormulasConceptosCrear.clear();
            }
            if (!listFormulasConceptosModificar.isEmpty()) {
                administrarDetalleConcepto.modificarFormulasConceptos(listFormulasConceptosModificar);
                listFormulasConceptosModificar.clear();
            }
            listFormulasConceptos = null;
            context.update("form:datosFormulaConcepto");
            k = 0;
            vigFormulaConceptoSeleccionada = null;
            cambiosFormulasConceptos = false;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron de Formula con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            context.update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosFormulasConceptos : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Formula, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {

        if (banderaVigenciaCuenta == 1) {
            recargarVigenciaCuentaDefault();
        }
        if (banderaVigenciaGrupoConcepto == 1) {
            recargarVigenciaGrupoCDefault();
        }

        if (banderaVigenciaConceptoTT == 1) {
            recargarVigenciaConceptoTT();
        }
        if (banderaVigenciaConceptoTC == 1) {
            recargarVigenciaConceptoTC();
        }
        if (banderaVigenciaConceptoRL == 1) {
            recargarVigenciaConceptoRT();
        }
        if (banderaFormulasConceptos == 1) {
            recargarFormulaConcepto();
        }

        listVigenciasCuentasBorrar.clear();
        listVigenciasCuentasCrear.clear();
        listVigenciasCuentasModificar.clear();

        listVigenciasGruposConceptosBorrar.clear();
        listVigenciasGruposConceptosCrear.clear();
        listVigenciasGruposConceptosModificar.clear();

        listVigenciasConceptosTTBorrar.clear();
        listVigenciasConceptosTTCrear.clear();
        listVigenciasConceptosTTModificar.clear();

        listVigenciasConceptosTCBorrar.clear();
        listVigenciasConceptosTCCrear.clear();
        listVigenciasConceptosTCModificar.clear();

        listVigenciasConceptosRLBorrar.clear();
        listVigenciasConceptosRLCrear.clear();
        listVigenciasConceptosRLModificar.clear();

        listFormulasConceptosBorrar.clear();
        listFormulasConceptosCrear.clear();
        listFormulasConceptosModificar.clear();

        vigenciaCuentaSeleccionada = null;
        vigenciaConceptoTTSeleccionada = null;
        vigenciaConceptoRLSeleccionada = null;
        vigenciaConceptoTCSeleccionada = null;
        vigenciaGrupoCoSeleccionada = null;
        vigFormulaConceptoSeleccionada = null;
        k = 0;
        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptos = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptos = null;

        guardado = true;
        formulaSeleccionada = true;

        cambiosVigenciaCuenta = false;
        cambiosVigenciaConceptoTT = false;
        cambiosVigenciaConceptoRL = false;
        cambiosVigenciaConceptoTC = false;
        cambiosVigenciaGrupoConcepto = false;
        cambiosFormulasConceptos = false;

        permitirIndexVigenciaCuenta = true;
        permitirIndexVigenciaConceptoTT = true;
        permitirIndexVigenciaConceptoTC = true;
        permitirIndexVigenciaConceptoRL = true;
        permitirIndexVigenciaGrupoConcepto = true;
        permitirIndexFormulasConceptos = true;

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCuenta");
        context.update("form:datosVigenciaGrupoConcepto");
        context.update("form:datosVigenciaConceptoTT");
        context.update("form:datosVigenciaConceptoTC");
        context.update("form:datosVigenciaConceptoRL");
        context.update("form:datosFormulaConcepto");
        context.update("form:detalleFormula");
    }

    public void listaValoresBoton() {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaCuentaSeleccionada != null) {
            if (cualCeldaVigenciaCuenta == 2) {
                contarRegistrosLovTipoContrato(0);
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 3) {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 4) {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 5) {
                contarRegistrosLovCentroCostoDebito(0);
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 6) {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 7) {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 8) {
                contarRegistrosLovCentroCostoCredito(0);
                context.update("form:CentroCostoCDialogo");
                context.execute("CentroCostoCDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (vigenciaGrupoCoSeleccionada != null) {
            if (cualCeldaVigenciaGrupoConcepto == 2) {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaGrupoConcepto == 3) {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (vigenciaConceptoTTSeleccionada != null) {
            if (cualCeldaVigenciaConceptoTT == 2) {
                contarRegistrosLovTipoTrabajador(0);
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (vigenciaConceptoTCSeleccionada != null) {
            if (cualCeldaVigenciaConceptoTC == 2) {
                contarRegistrosLovTipoContrato(0);
                context.update("form:TipoContratosDialogo");
                context.execute("TipoContratosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (vigenciaConceptoRLSeleccionada != null) {
            if (cualCeldaVigenciaConceptoRL == 2) {
                contarRegistrosLovReformaLaboral(0);
                context.update("form:ReformaLaboralDialogo");
                context.execute("ReformaLaboralDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (vigFormulaConceptoSeleccionada != null) {
            if (cualCeldaFormulasConceptos == 2) {
                contarRegistrosLovFormula(0);
                context.update("form:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaFormulasConceptos == 3) {
                contarRegistrosLovFormCon(0);
                context.update("form:FormulaConceptoDialogo");
                context.execute("FormulaConceptoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(int campo, int tipoAct, int tabla) {
        cargarLOVs();
        RequestContext context = RequestContext.getCurrentInstance();
        if (tabla == 0) {
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovTipoContrato(0);
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
            } else if (campo == 1) {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
            } else if (campo == 2) {
                contarRegistrosLovCuentaDebito(0);
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
            } else if (campo == 3) {
                contarRegistrosLovCentroCostoDebito(0);
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
            } else if (campo == 4) {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
            } else if (campo == 5) {
                contarRegistrosLovCuentaCredito(0);
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
            } else if (campo == 6) {
                contarRegistrosLovCentroCostoCredito(0);
                context.update("form:CentroCostoCDialogo");
                context.execute("CentroCostoCDialogo.show()");
            }
        }
        if (tabla == 1) {
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
            } else if (campo == 1) {
                contarRegistrosLovGrupoConcepto(0);
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
            }
        }
        if (tabla == 2) {
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovTipoTrabajador(0);
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
            }
        }
        if (tabla == 3) {
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovTipoContrato(0);
                context.update("form:TipoContratosDialogo");
                context.execute("TipoContratosDialogo.show()");
            }
        }
        if (tabla == 4) {
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovReformaLaboral(0);
                context.update("form:ReformaLaboralDialogo");
                context.execute("ReformaLaboralDialogo.show()");
            }
        }
        if (tabla == 5) {
            formulaSeleccionada = true;
            context.update("form:detalleFormula");
            if (tipoAct == 0) {
                tipoActualizacion = 0;
            } else if (tipoAct == 1) {
                tipoActualizacion = 1;
            } else if (tipoAct == 2) {
                tipoActualizacion = 2;
            }
            if (campo == 0) {
                contarRegistrosLovFormula(0);
                context.update("form:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
            }
            if (campo == 1) {
                contarRegistrosLovFormCon(0);
                context.update("form:FormulaConceptoDialogo");
                context.execute("FormulaConceptoDialogo.show()");
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        ///////////VigenciaCuenta/////////////
        if (vigenciaCuentaSeleccionada != null || vigenciaConceptoRLSeleccionada != null || vigenciaConceptoTCSeleccionada != null
                || vigenciaConceptoTTSeleccionada != null || vigFormulaConceptoSeleccionada != null || vigenciaGrupoCoSeleccionada != null) {

            if (vigenciaCuentaSeleccionada != null) {
                editarVigenciaCuenta = vigenciaCuentaSeleccionada;

                if (cualCeldaVigenciaCuenta == 0) {
                    context.update("formularioDialogos:editarFechaInicialVCD");
                    context.execute("editarFechaInicialVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 1) {
                    context.update("formularioDialogos:editarFechaFinalVCD");
                    context.execute("editarFechaFinalVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 2) {
                    context.update("formularioDialogos:editarTipoCCVCD");
                    context.execute("editarTipoCCVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 3) {
                    context.update("formularioDialogos:editaCodDebitoVCD");
                    context.execute("editaCodDebitoVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 4) {
                    context.update("formularioDialogos:editaDesDebitoVCD");
                    context.execute("editaDesDebitoVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 5) {
                    context.update("formularioDialogos:editaCCConsolidadorDVCD");
                    context.execute("editaCCConsolidadorDVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 6) {
                    context.update("formularioDialogos:editaCodCreditoVCD");
                    context.execute("editaCodCreditoVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 7) {
                    context.update("formularioDialogos:editaDesCreditoVCD");
                    context.execute("editaDesCreditoVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                } else if (cualCeldaVigenciaCuenta == 8) {
                    context.update("formularioDialogos:editaCCConsolidadorCVCD");
                    context.execute("editaCCConsolidadorCVCD.show()");
                    cualCeldaVigenciaCuenta = -1;
                }
                vigenciaCuentaSeleccionada = null;
            }
            ///////////VigenciaCuenta/////////////
            ///////////VigenciaGrupoConcepto/////////////
            if (vigenciaGrupoCoSeleccionada != null) {
                editarVigenciaGrupoConcepto = vigenciaGrupoCoSeleccionada;

                if (cualCeldaVigenciaGrupoConcepto == 0) {
                    context.update("formularioDialogos:editarFechaInicialVGCD");
                    context.execute("editarFechaInicialVGCD.show()");
                    cualCeldaVigenciaGrupoConcepto = -1;
                } else if (cualCeldaVigenciaGrupoConcepto == 1) {
                    context.update("formularioDialogos:editarFechaFinalVGCD");
                    context.execute("editarFechaFinalVGCD.show()");
                    cualCeldaVigenciaGrupoConcepto = -1;
                } else if (cualCeldaVigenciaGrupoConcepto == 2) {
                    context.update("formularioDialogos:editarCodigoVGCD");
                    context.execute("editarCodigoVGCD.show()");
                    cualCeldaVigenciaGrupoConcepto = -1;
                } else if (cualCeldaVigenciaGrupoConcepto == 3) {
                    context.update("formularioDialogos:editaDescripcionVGCD");
                    context.execute("editaDescripcionVGCD.show()");
                    cualCeldaVigenciaGrupoConcepto = -1;
                }
                vigenciaGrupoCoSeleccionada = null;
            }
            ///////////VigenciaGrupoConcepto/////////////
            ///////////VigenciaConceptoTT/////////////
            if (vigenciaConceptoTTSeleccionada != null) {
                editarVigenciaConceptoTT = vigenciaConceptoTTSeleccionada;

                if (cualCeldaVigenciaConceptoTT == 0) {
                    context.update("formularioDialogos:editarFechaInicialVCTCD");
                    context.execute("editarFechaInicialVCTCD.show()");
                    cualCeldaVigenciaConceptoTT = -1;
                } else if (cualCeldaVigenciaConceptoTT == 1) {
                    context.update("formularioDialogos:editarFechaFinalVCTCD");
                    context.execute("editarFechaFinalVCTCD.show()");
                    cualCeldaVigenciaConceptoTT = -1;
                } else if (cualCeldaVigenciaConceptoTT == 2) {
                    context.update("formularioDialogos:editaDescripcionVCTCD");
                    context.execute("editaDescripcionVCTCD.show()");
                    cualCeldaVigenciaConceptoTT = -1;
                }
                vigenciaConceptoTTSeleccionada = null;
            }
            ///////////VigenciaConceptoTT/////////////
            ///////////VigenciaConceptoTT/////////////
            if (vigenciaConceptoTCSeleccionada != null) {
                editarVigenciaConceptoTC = vigenciaConceptoTCSeleccionada;

                if (cualCeldaVigenciaConceptoTC == 0) {
                    context.update("formularioDialogos:editarFechaInicialVCTTD");
                    context.execute("editarFechaInicialVCTTD.show()");
                    cualCeldaVigenciaConceptoTC = -1;
                } else if (cualCeldaVigenciaConceptoTC == 1) {
                    context.update("formularioDialogos:editarFechaFinalVCTTD");
                    context.execute("editarFechaFinalVCTTD.show()");
                    cualCeldaVigenciaConceptoTC = -1;
                } else if (cualCeldaVigenciaConceptoTC == 2) {
                    context.update("formularioDialogos:editaDescripcionVCTTD");
                    context.execute("editaDescripcionVCTTD.show()");
                    cualCeldaVigenciaConceptoTC = -1;
                }
                vigenciaConceptoTCSeleccionada = null;
                vigenciaConceptoTCSeleccionada = null;
            }
            ///////////VigenciaConceptoTC/////////////
            ///////////VigenciaConceptoRL/////////////
            if (vigenciaConceptoRLSeleccionada != null) {
                editarVigenciaConceptoRL = vigenciaConceptoRLSeleccionada;

                if (cualCeldaVigenciaConceptoRL == 0) {
                    context.update("formularioDialogos:editarFechaInicialVCRLD");
                    context.execute("editarFechaInicialVCRLD.show()");
                    cualCeldaVigenciaConceptoRL = -1;
                } else if (cualCeldaVigenciaConceptoRL == 1) {
                    context.update("formularioDialogos:editarFechaFinalVCRLD");
                    context.execute("editarFechaFinalVCRLD.show()");
                    cualCeldaVigenciaConceptoRL = -1;
                } else if (cualCeldaVigenciaConceptoRL == 2) {
                    context.update("formularioDialogos:editaDescripcionVCRLD");
                    context.execute("editaDescripcionVCRLD.show()");
                    cualCeldaVigenciaConceptoRL = -1;
                }
            }
            ///////////VigenciaConceptoRL/////////////
            ///////////FormulasConceptos/////////////
            if (vigFormulaConceptoSeleccionada != null) {
                editarFormulasConceptos = vigFormulaConceptoSeleccionada;

                if (cualCeldaFormulasConceptos == 0) {
                    context.update("formularioDialogos:editarFechaInicialFCD");
                    context.execute("editarFechaInicialFCD.show()");
                    cualCeldaFormulasConceptos = -1;
                } else if (cualCeldaFormulasConceptos == 1) {
                    context.update("formularioDialogos:editarFechaFinalFCD");
                    context.execute("editarFechaFinalFCD.show()");
                    cualCeldaFormulasConceptos = -1;
                } else if (cualCeldaFormulasConceptos == 2) {
                    context.update("formularioDialogos:editaFormulaFCD");
                    context.execute("editaFormulaFCD.show()");
                    cualCeldaFormulasConceptos = -1;
                } else if (cualCeldaFormulasConceptos == 3) {
                    context.update("formularioDialogos:editaOrdenFCD");
                    context.execute("editaOrdenFCD.show()");
                    cualCeldaFormulasConceptos = -1;
                }
                formulaSeleccionada = true;
                context.update("form:detalleFormula");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void ingresoNuevoRegistro() {
        formulaSeleccionada = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        context.execute("seleccionarTablaNewReg.show()");
    }

    public void validarIngresoNuevaVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoVigenciaCuenta();
        context.update("form:nuevaVC");
        context.update("formularioDialogos:NuevoRegistroVigenciaCuenta");
        context.execute("NuevoRegistroVigenciaCuenta.show()");

    }

    public void validarIngresoNuevaVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoVigenciaGrupoConcepto();
        context.update("form:nuevaVGC");
        context.update("formularioDialogos:NuevoRegistroGrupoConcepto");
        context.execute("NuevoRegistroGrupoConcepto.show()");

    }

    public void validarIngresoNuevaVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoVigenciaConceptoTT();
        context.update("form:nuevaVCTT");
        context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTT");
        context.execute("NuevoRegistroVigenciaConceptoTT.show()");

    }

    public void validarIngresoNuevaVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoVigenciaConceptoTC();
        context.update("form:nuevaVCTC");
        context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTC");
        context.execute("NuevoRegistroVigenciaConceptoTC.show()");

    }

    public void validarIngresoNuevaVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoVigenciaConceptoRL();
        context.update("form:nuevaVCRL");
        context.update("formularioDialogos:NuevoRegistroVigenciaConceptoRL");
        context.execute("NuevoRegistroVigenciaConceptoRL.show()");

    }

    public void validarIngresoNuevaFormulasConceptos() {
        formulaSeleccionada = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
        limpiarNuevoFormulasConceptos();
        context.update("form:nuevaFC");
        context.update("formularioDialogos:NuevoRegistroFormulaConcepto");
        context.execute("NuevoRegistroFormulaConcepto.show()");

    }

    public void validarDuplicadoRegistro() {
        ///////////////VigenciaCuenta///////////////
        if (vigenciaCuentaSeleccionada != null) {
            duplicarVigenciaCuentaM();
        } ///////////////VigenciaGrupoConcepto///////////////
        else if (vigenciaGrupoCoSeleccionada != null) {
            duplicarVigenciaGrupoConceptoM();
        } ///////////////VigenciaConceptoTT///////////////
        else if (vigenciaConceptoTTSeleccionada != null) {
            duplicarVigenciaConceptoTTM();
        } ////////////VigenciaConceptoTC /////////////////////
        else if (vigenciaConceptoTCSeleccionada != null) {
            duplicarVigenciaConceptoTCM();
        } ////////////VigenciaConceptoRL /////////////////////
        else if (vigenciaConceptoRLSeleccionada != null) {
            duplicarVigenciaConceptoRLM();
        } ////////////FormulasConceptos /////////////////////
        else if (vigFormulaConceptoSeleccionada != null) {
            duplicarFormulasConceptosM();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    //////////Validaciones de los campos de las tablas de la pagina///////////////
    public boolean validarNuevosDatosVigenciaCuenta(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasCuentas aux = null;
            aux = vigenciaCuentaSeleccionada;

            if ((aux.getFechafinal() != null) && (aux.getFechainicial() != null)
                    && (aux.getConsolidadorc().getSecuencia() != null)
                    && (aux.getConsolidadord().getSecuencia() != null)
                    && (aux.getCuentac().getSecuencia() != null)
                    && (aux.getCuentad().getSecuencia() != null)
                    && (aux.getTipocc().getSecuencia() != null)) {
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaVigenciaCuenta.getFechafinal() != null) && (nuevaVigenciaCuenta.getFechainicial() != null)
                    && (nuevaVigenciaCuenta.getConsolidadorc().getSecuencia() != null)
                    && (nuevaVigenciaCuenta.getConsolidadord().getSecuencia() != null)
                    && (nuevaVigenciaCuenta.getCuentac().getSecuencia() != null)
                    && (nuevaVigenciaCuenta.getCuentad().getSecuencia() != null)
                    && (nuevaVigenciaCuenta.getTipocc().getSecuencia() != null)) {
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVigenciaCuenta.getFechafinal() != null) && (duplicarVigenciaCuenta.getFechainicial() != null)
                    && (duplicarVigenciaCuenta.getConsolidadorc().getSecuencia() != null)
                    && (duplicarVigenciaCuenta.getConsolidadord().getSecuencia() != null)
                    && (duplicarVigenciaCuenta.getCuentac().getSecuencia() != null)
                    && (duplicarVigenciaCuenta.getCuentad().getSecuencia() != null)
                    && (duplicarVigenciaCuenta.getTipocc().getSecuencia() != null)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaGrupoConcepto(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasGruposConceptos aux = null;
            aux = vigenciaGrupoCoSeleccionada;

            if ((aux.getFechafinal() != null) && (aux.getFechainicial() != null)
                    && (aux.getGrupoconcepto().getSecuencia() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaVigenciaGrupoConcepto.getFechafinal() != null) && (nuevaVigenciaGrupoConcepto.getFechainicial() != null)
                    && (nuevaVigenciaGrupoConcepto.getGrupoconcepto().getSecuencia() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVigenciaGrupoConcepto.getFechafinal() != null) && (duplicarVigenciaGrupoConcepto.getFechainicial() != null)
                    && (duplicarVigenciaGrupoConcepto.getGrupoconcepto().getSecuencia() != null)) {
                /////
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoTT(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTT aux = null;
            aux = vigenciaConceptoTTSeleccionada;

            if ((aux.getFechafinal() != null) && (aux.getFechainicial() != null)
                    && (aux.getTipotrabajador().getSecuencia() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaVigenciaConceptoTT.getTipotrabajador().getSecuencia() != null)
                    && (nuevaVigenciaConceptoTT.getFechafinal() != null) && (nuevaVigenciaConceptoTT.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVigenciaConceptoTT.getTipotrabajador().getSecuencia() != null)
                    && (duplicarVigenciaConceptoTT.getFechafinal() != null) && (duplicarVigenciaConceptoTT.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoTC(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTC aux = null;
            aux = vigenciaConceptoTCSeleccionada;

            if ((aux.getTipocontrato().getSecuencia() != null)
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaVigenciaConceptoTC.getTipocontrato().getSecuencia() != null)
                    && (nuevaVigenciaConceptoTC.getFechafinal() != null) && (nuevaVigenciaConceptoTC.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVigenciaConceptoTC.getTipocontrato().getSecuencia() != null)
                    && (duplicarVigenciaConceptoTC.getFechafinal() != null) && (duplicarVigenciaConceptoTC.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoRL(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosRL aux = null;
            aux = vigenciaConceptoRLSeleccionada;

            if ((aux.getTiposalario().getSecuencia() != null)
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaVigenciaConceptoRL.getTiposalario().getSecuencia() != null)
                    && (nuevaVigenciaConceptoRL.getFechafinal() != null) && (nuevaVigenciaConceptoRL.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVigenciaConceptoRL.getTiposalario().getSecuencia() != null)
                    && (duplicarVigenciaConceptoRL.getFechafinal() != null) && (duplicarVigenciaConceptoRL.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosFormulasConceptos(int i) {
        boolean retorno = false;
        if (i == 0) {
            FormulasConceptos aux = null;
            aux = vigFormulaConceptoSeleccionada;

            if ((aux.getFormula().getSecuencia() != null)
                    && (!aux.getStrOrden().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 1) {
            if ((nuevaFormulasConceptos.getFormula().getSecuencia() != null)
                    && (!nuevaFormulasConceptos.getStrOrden().isEmpty())
                    && (nuevaFormulasConceptos.getFechafinal() != null) && (nuevaFormulasConceptos.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarFormulasConceptos.getFormula().getSecuencia() != null)
                    && (!duplicarFormulasConceptos.getStrOrden().isEmpty())
                    && (duplicarFormulasConceptos.getFechafinal() != null) && (duplicarFormulasConceptos.getFechainicial() != null)) {
                /////
                retorno = true;
            }
        }
        return retorno;
    }

    public void agregarNuevoVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaCuenta(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(1);
            if (validacion == true) {
                if (banderaVigenciaCuenta == 1) {
                    recargarVigenciaCuentaDefault();
                }
                k++;
                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaCuenta.setSecuencia(var);
                nuevaVigenciaCuenta.setConcepto(conceptoActual);
                listVigenciasCuentasCrear.add(nuevaVigenciaCuenta);
                listVigenciasCuentasConcepto.add(nuevaVigenciaCuenta);
                vigenciaCuentaSeleccionada = listVigenciasCuentasConcepto.get(listVigenciasCuentasConcepto.indexOf(nuevaVigenciaCuenta));
                contarRegistrosCuentas();
                ////------////
                nuevaVigenciaCuenta = new VigenciasCuentas();
                nuevaVigenciaCuenta.setConsolidadorc(new CentrosCostos());
                nuevaVigenciaCuenta.setConsolidadord(new CentrosCostos());
                nuevaVigenciaCuenta.setCuentac(new Cuentas());
                nuevaVigenciaCuenta.setCuentad(new Cuentas());
                nuevaVigenciaCuenta.setTipocc(new TiposCentrosCostos());
                ////-----////
                context.execute("NuevoRegistroVigenciaCuenta.hide()");
                context.update("form:datosVigenciaCuenta");
                context.update("formularioDialogos:NuevoRegistroVigenciaCuenta");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigenciaCuenta = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaCuenta() {
        nuevaVigenciaCuenta = new VigenciasCuentas();
        nuevaVigenciaCuenta.setConsolidadorc(new CentrosCostos());
        nuevaVigenciaCuenta.setConsolidadord(new CentrosCostos());
        nuevaVigenciaCuenta.setCuentac(new Cuentas());
        nuevaVigenciaCuenta.setCuentad(new Cuentas());
        nuevaVigenciaCuenta.setTipocc(new TiposCentrosCostos());
    }

    public void agregarNuevoVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaGrupoConcepto(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(1);
            if (validacion == true) {
                if (banderaVigenciaGrupoConcepto == 1) {
                    recargarVigenciaGrupoCDefault();
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaGrupoConcepto.setSecuencia(var);
                nuevaVigenciaGrupoConcepto.setConcepto(conceptoActual);
                listVigenciasGruposConceptosCrear.add(nuevaVigenciaGrupoConcepto);
                listVigenciasGruposConceptos.add(nuevaVigenciaGrupoConcepto);
                vigenciaGrupoCoSeleccionada = listVigenciasGruposConceptos.get(listVigenciasGruposConceptos.indexOf(nuevaVigenciaGrupoConcepto));
                contarRegistrosGrupoC();
                ////------////
                nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
                nuevaVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
                ////-----////
                context.execute("NuevoRegistroGrupoConcepto.hide()");
                context.update("form:datosVigenciaGrupoConcepto");
                context.update("formularioDialogos:NuevoRegistroGrupoConcepto");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigenciaGrupoConcepto = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaGrupoConcepto() {
        nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        nuevaVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
        vigenciaGrupoCoSeleccionada = null;
        vigenciaGrupoCoSeleccionada = null;
    }

    public void agregarNuevoVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoTT(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoTT == 1) {
                    recargarVigenciaConceptoTT();
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoTT.setSecuencia(var);
                nuevaVigenciaConceptoTT.setConcepto(conceptoActual);
                listVigenciasConceptosTTCrear.add(nuevaVigenciaConceptoTT);
                listVigenciasConceptosTTConcepto.add(nuevaVigenciaConceptoTT);
                vigenciaConceptoTTSeleccionada = listVigenciasConceptosTTConcepto.get(listVigenciasConceptosTTConcepto.indexOf(nuevaVigenciaConceptoTT));
                contarRegistrosConceptoTT();
                ////------////
                nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
                nuevaVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
                ////-----////
                context.execute("NuevoRegistroVigenciaConceptoTT.hide()");
                context.update("form:datosVigenciaConceptoTT");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTT");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigenciaConceptoTT = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoTT() {
        nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
        nuevaVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
        vigenciaConceptoTTSeleccionada = null;
        vigenciaConceptoTTSeleccionada = null;
    }

    public void agregarNuevoVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoTC(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoTC == 1) {
                    recargarVigenciaConceptoTC();
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoTC.setSecuencia(var);
                nuevaVigenciaConceptoTC.setConcepto(conceptoActual);
                listVigenciasConceptosTCCrear.add(nuevaVigenciaConceptoTC);
                listVigenciasConceptosTCConcepto.add(nuevaVigenciaConceptoTC);
                vigenciaConceptoTCSeleccionada = listVigenciasConceptosTCConcepto.get(listVigenciasConceptosTCConcepto.indexOf(nuevaVigenciaConceptoTC));
                contarRegistrosConceptoTC();
                ////------////
                nuevaVigenciaConceptoTC = new VigenciasConceptosTC();
                nuevaVigenciaConceptoTC.setTipocontrato(new TiposContratos());
                ////-----////
                context.execute("NuevoRegistroVigenciaConceptoTC.hide()");
                context.update("form:datosVigenciaConceptoTC");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTC");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigenciaConceptoTC = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoTC() {
        nuevaVigenciaConceptoTC = new VigenciasConceptosTC();
        nuevaVigenciaConceptoTC.setTipocontrato(new TiposContratos());
        vigenciaConceptoTCSeleccionada = null;
        vigenciaConceptoTCSeleccionada = null;
    }

    public void agregarNuevoVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoRL(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoRL == 1) {
                    recargarVigenciaConceptoRT();
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoRL.setSecuencia(var);
                nuevaVigenciaConceptoRL.setConcepto(conceptoActual);
                listVigenciasConceptosRLCrear.add(nuevaVigenciaConceptoRL);
                listVigenciasConceptosRLConcepto.add(nuevaVigenciaConceptoRL);
                vigenciaConceptoRLSeleccionada = listVigenciasConceptosRLConcepto.get(listVigenciasConceptosRLConcepto.indexOf(nuevaVigenciaConceptoRL));
                contarRegistrosConceptoRL();
                ////------////
                nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
                nuevaVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
                ////-----////
                context.execute("NuevoRegistroVigenciaConceptoRL.hide()");
                context.update("form:datosVigenciaConceptoRL");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoRL");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigenciaConceptoRL = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoRL() {
        nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
        nuevaVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
        vigenciaConceptoRLSeleccionada = null;
    }

    public void agregarNuevoFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosFormulasConceptos(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulasConceptos(1);
            if (validacion == true) {
                if (banderaFormulasConceptos == 1) {
                    recargarFormulaConcepto();
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaFormulasConceptos.setSecuencia(var);
                nuevaFormulasConceptos.setConcepto(conceptoActual);
                listFormulasConceptosCrear.add(nuevaFormulasConceptos);
                listFormulasConceptos.add(nuevaFormulasConceptos);
                vigFormulaConceptoSeleccionada = listFormulasConceptos.get(listFormulasConceptos.indexOf(nuevaFormulasConceptos));
                contarRegistrosFormulaConcepto();
                ////------////
                nuevaFormulasConceptos = new FormulasConceptos();
                nuevaFormulasConceptos.setFormula(new Formulas());
                ////-----////
                context.execute("NuevoRegistroFormulaConcepto.hide()");
                context.update("form:datosFormulaConcepto");
                context.update("formularioDialogos:NuevoRegistroFormulaConcepto");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosFormulasConceptos = true;
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoFormulasConceptos() {
        nuevaFormulasConceptos = new FormulasConceptos();
        nuevaFormulasConceptos.setFormula(new Formulas());
        vigFormulaConceptoSeleccionada = null;
        vigFormulaConceptoSeleccionada = null;
    }

    public void duplicarVigenciaCuentaM() {
        if (vigenciaCuentaSeleccionada != null) {
            duplicarVigenciaCuenta = new VigenciasCuentas();
            duplicarVigenciaCuenta.setConcepto(vigenciaCuentaSeleccionada.getConcepto());
            duplicarVigenciaCuenta.setConsolidadorc(vigenciaCuentaSeleccionada.getConsolidadorc());
            duplicarVigenciaCuenta.setConsolidadord(vigenciaCuentaSeleccionada.getConsolidadord());
            duplicarVigenciaCuenta.setCuentac(vigenciaCuentaSeleccionada.getCuentac());
            duplicarVigenciaCuenta.setCuentad(vigenciaCuentaSeleccionada.getCuentad());
            duplicarVigenciaCuenta.setFechafinal(vigenciaCuentaSeleccionada.getFechafinal());
            duplicarVigenciaCuenta.setFechainicial(vigenciaCuentaSeleccionada.getFechainicial());
            duplicarVigenciaCuenta.setTipocc(vigenciaCuentaSeleccionada.getTipocc());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaCuenta");
            context.execute("DuplicarRegistroVigenciaCuenta.show()");
        }
    }

    public void duplicarVigenciaGrupoConceptoM() {
        if (vigenciaGrupoCoSeleccionada != null) {
            duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
            duplicarVigenciaGrupoConcepto.setConcepto(vigenciaGrupoCoSeleccionada.getConcepto());
            duplicarVigenciaGrupoConcepto.setGrupoconcepto(vigenciaGrupoCoSeleccionada.getGrupoconcepto());
            duplicarVigenciaGrupoConcepto.setFechafinal(vigenciaGrupoCoSeleccionada.getFechafinal());
            duplicarVigenciaGrupoConcepto.setFechainicial(vigenciaGrupoCoSeleccionada.getFechainicial());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaGrupoConcepto");
            context.execute("DuplicarRegistroVigenciaGrupoConcepto.show()");
        }
    }

    public void duplicarVigenciaConceptoTTM() {
        if (vigenciaConceptoTTSeleccionada != null) {
            duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
            duplicarVigenciaConceptoTT.setConcepto(vigenciaConceptoTTSeleccionada.getConcepto());
            duplicarVigenciaConceptoTT.setTipotrabajador(vigenciaConceptoTTSeleccionada.getTipotrabajador());
            duplicarVigenciaConceptoTT.setFechafinal(vigenciaConceptoTTSeleccionada.getFechafinal());
            duplicarVigenciaConceptoTT.setFechainicial(vigenciaConceptoTTSeleccionada.getFechainicial());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoTT");
            context.execute("DuplicarRegistroVigenciaConceptoTT.show()");
        }
    }

    public void duplicarVigenciaConceptoTCM() {
        if (vigenciaConceptoTCSeleccionada != null) {
            duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
            duplicarVigenciaConceptoTC.setConcepto(vigenciaConceptoTCSeleccionada.getConcepto());
            duplicarVigenciaConceptoTC.setTipocontrato(vigenciaConceptoTCSeleccionada.getTipocontrato());
            duplicarVigenciaConceptoTC.setFechafinal(vigenciaConceptoTCSeleccionada.getFechafinal());
            duplicarVigenciaConceptoTC.setFechainicial(vigenciaConceptoTCSeleccionada.getFechainicial());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoTC");
            context.execute("DuplicarRegistroVigenciaConceptoTC.show()");
        }
    }

    public void duplicarVigenciaConceptoRLM() {
        if (vigenciaConceptoRLSeleccionada != null) {
            duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
            duplicarVigenciaConceptoRL.setConcepto(vigenciaConceptoRLSeleccionada.getConcepto());
            duplicarVigenciaConceptoRL.setTiposalario(vigenciaConceptoRLSeleccionada.getTiposalario());
            duplicarVigenciaConceptoRL.setFechafinal(vigenciaConceptoRLSeleccionada.getFechafinal());
            duplicarVigenciaConceptoRL.setFechainicial(vigenciaConceptoRLSeleccionada.getFechainicial());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoRL");
            context.execute("DuplicarRegistroVigenciaConceptoRL.show()");
        }
    }

    public void duplicarFormulasConceptosM() {
        if (vigFormulaConceptoSeleccionada != null) {
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            duplicarFormulasConceptos = new FormulasConceptos();
            duplicarFormulasConceptos.setConcepto(vigFormulaConceptoSeleccionada.getConcepto());
            duplicarFormulasConceptos.setFormula(vigFormulaConceptoSeleccionada.getFormula());
            duplicarFormulasConceptos.setFechafinal(vigFormulaConceptoSeleccionada.getFechafinal());
            duplicarFormulasConceptos.setFechainicial(vigFormulaConceptoSeleccionada.getFechainicial());
            duplicarFormulasConceptos.setStrOrden(vigFormulaConceptoSeleccionada.getStrOrden());

            context.update("formularioDialogos:DuplicarRegistroFormulaConcepto");
            context.execute("DuplicarRegistroFormulaConcepto.show()");
        }
    }

    public void confirmarDuplicarVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaCuenta(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(2);
            if (validacion == true) {
                if (vigenciaCuentaSeleccionada != null) {
                    if (banderaVigenciaCuenta == 1) {
                        recargarVigenciaCuentaDefault();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarVigenciaCuenta.setSecuencia(var);
                    duplicarVigenciaCuenta.setConcepto(conceptoActual);
                    listVigenciasCuentasCrear.add(duplicarVigenciaCuenta);
                    listVigenciasCuentasConcepto.add(duplicarVigenciaCuenta);
                    vigenciaCuentaSeleccionada = listVigenciasCuentasConcepto.get(listVigenciasCuentasConcepto.indexOf(duplicarVigenciaCuenta));
                    contarRegistrosCuentas();

                    duplicarVigenciaCuenta = new VigenciasCuentas();
                    duplicarVigenciaCuenta.setConsolidadorc(new CentrosCostos());
                    duplicarVigenciaCuenta.setConsolidadord(new CentrosCostos());
                    duplicarVigenciaCuenta.setCuentac(new Cuentas());
                    duplicarVigenciaCuenta.setCuentad(new Cuentas());
                    duplicarVigenciaCuenta.setTipocc(new TiposCentrosCostos());
                    context.update("form:datosVigenciaCuenta");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("DuplicarRegistroVigenciaCuenta.hide()");
                    cambiosVigenciaCuenta = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaCuenta() {
        duplicarVigenciaCuenta = new VigenciasCuentas();
        duplicarVigenciaCuenta.setConsolidadorc(new CentrosCostos());
        duplicarVigenciaCuenta.setConsolidadord(new CentrosCostos());
        duplicarVigenciaCuenta.setCuentac(new Cuentas());
        duplicarVigenciaCuenta.setCuentad(new Cuentas());
        duplicarVigenciaCuenta.setTipocc(new TiposCentrosCostos());
    }

    public void confirmarDuplicarVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaGrupoConcepto(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(2);
            if (validacion == true) {
                if (vigenciaGrupoCoSeleccionada != null) {
                    if (banderaVigenciaGrupoConcepto == 1) {
                        recargarVigenciaGrupoCDefault();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarVigenciaGrupoConcepto.setSecuencia(var);
                    duplicarVigenciaGrupoConcepto.setConcepto(conceptoActual);
                    listVigenciasGruposConceptosCrear.add(duplicarVigenciaGrupoConcepto);
                    listVigenciasGruposConceptos.add(duplicarVigenciaGrupoConcepto);
                    vigenciaGrupoCoSeleccionada = listVigenciasGruposConceptos.get(listVigenciasGruposConceptos.indexOf(duplicarVigenciaGrupoConcepto));
                    contarRegistrosGrupoC();

                    duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
                    context.update("form:datosVigenciaGrupoConcepto");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("DuplicarRegistroVigenciaGrupoConcepto.hide()");
                    cambiosVigenciaGrupoConcepto = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaGrupoConcepto() {
        duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        duplicarVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
    }

    public void confirmarDuplicarVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoTT(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(2);
            if (validacion == true) {
                if (vigenciaConceptoTTSeleccionada != null) {
                    if (banderaVigenciaConceptoTT == 1) {
                        recargarVigenciaConceptoTT();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarVigenciaConceptoTT.setSecuencia(var);
                    duplicarVigenciaConceptoTT.setConcepto(conceptoActual);
                    listVigenciasConceptosTTCrear.add(duplicarVigenciaConceptoTT);
                    listVigenciasConceptosTTConcepto.add(duplicarVigenciaConceptoTT);
                    vigenciaConceptoTTSeleccionada = listVigenciasConceptosTTConcepto.get(listVigenciasConceptosTTConcepto.indexOf(duplicarVigenciaConceptoTT));
                    contarRegistrosConceptoTT();

                    duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
                    duplicarVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
                    context.update("form:datosVigenciaConceptoTT");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoTT.hide()");
                    cambiosVigenciaConceptoTT = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoTT() {
        duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
        duplicarVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
    }

    public void confirmarDuplicarVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoTC(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(2);
            if (validacion == true) {
                if (vigenciaConceptoTCSeleccionada != null) {
                    if (banderaVigenciaConceptoTC == 1) {
                        recargarVigenciaConceptoTC();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarVigenciaConceptoTC.setSecuencia(var);
                    duplicarVigenciaConceptoTC.setConcepto(conceptoActual);
                    listVigenciasConceptosTCCrear.add(duplicarVigenciaConceptoTC);
                    listVigenciasConceptosTCConcepto.add(duplicarVigenciaConceptoTC);
                    vigenciaConceptoTCSeleccionada = listVigenciasConceptosTCConcepto.get(listVigenciasConceptosTCConcepto.indexOf(duplicarVigenciaConceptoTC));
                    contarRegistrosConceptoTC();

                    duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
                    duplicarVigenciaConceptoTC.setTipocontrato(new TiposContratos());
                    context.update("form:datosVigenciaConceptoTC");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoTC.hide()");
                    cambiosVigenciaConceptoTC = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoTC() {
        duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
        duplicarVigenciaConceptoTC.setTipocontrato(new TiposContratos());
    }

    public void confirmarDuplicarVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosVigenciaConceptoRL(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(2);
            if (validacion == true) {
                if (vigenciaConceptoRLSeleccionada != null) {
                    if (banderaVigenciaConceptoRL == 1) {
                        recargarVigenciaConceptoRT();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarVigenciaConceptoRL.setSecuencia(var);
                    duplicarVigenciaConceptoRL.setConcepto(conceptoActual);
                    listVigenciasConceptosRLCrear.add(duplicarVigenciaConceptoRL);
                    listVigenciasConceptosRLConcepto.add(duplicarVigenciaConceptoRL);
                    vigenciaConceptoRLSeleccionada = listVigenciasConceptosRLConcepto.get(listVigenciasConceptosRLConcepto.indexOf(duplicarVigenciaConceptoRL));
                    contarRegistrosConceptoRL();

                    duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
                    duplicarVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
                    context.update("form:datosVigenciaConceptoRL");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoRL.hide()");
                    cambiosVigenciaConceptoRL = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoRL() {
        duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
        duplicarVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
    }

    public void confirmarDuplicarFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean resp = validarNuevosDatosFormulasConceptos(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulasConceptos(2);
            if (validacion == true) {
                if (vigFormulaConceptoSeleccionada != null) {
                    if (banderaFormulasConceptos == 1) {
                        recargarFormulaConcepto();
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);
                    duplicarFormulasConceptos.setSecuencia(var);
                    duplicarFormulasConceptos.setConcepto(conceptoActual);
                    listFormulasConceptosCrear.add(duplicarFormulasConceptos);
                    listFormulasConceptos.add(duplicarFormulasConceptos);
                    vigFormulaConceptoSeleccionada = listFormulasConceptos.get(listFormulasConceptos.indexOf(duplicarFormulasConceptos));
                    contarRegistrosFormulaConcepto();

                    duplicarFormulasConceptos = new FormulasConceptos();
                    duplicarFormulasConceptos.setFormula(new Formulas());
                    context.update("form:datosFormulaConcepto");
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    formulaSeleccionada = true;
                    context.update("form:detalleFormula");
                    context.execute("DuplicarRegistroFormulaConcepto.hide()");
                    cambiosFormulasConceptos = true;
                }
            } else {
                context.execute("errorFechasVC.show()");
            }
        } else {
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarFormulasConceptos() {
        duplicarFormulasConceptos = new FormulasConceptos();
        duplicarFormulasConceptos.setFormula(new Formulas());
    }
    ///////////////////////////////////////////////////////////////

    public void validarBorradoRegistro() {
        if (vigenciaCuentaSeleccionada != null) {
            borrarVigenciaCuenta();
        } else if (vigenciaGrupoCoSeleccionada != null) {
            borrarVigenciaGrupoConcepto();
        } else if (vigenciaConceptoTTSeleccionada != null) {
            borrarVigenciaConceptoTT();
        } else if (vigenciaConceptoTCSeleccionada != null) {
            borrarVigenciaConceptoTC();
        } else if (vigenciaConceptoRLSeleccionada != null) {
            borrarVigenciaConceptoRL();
        } else if (vigFormulaConceptoSeleccionada != null) {
            borrarFormulasConceptos();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaCuentaSeleccionada != null) {
            if (!listVigenciasCuentasModificar.isEmpty() && listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                listVigenciasCuentasModificar.remove(vigenciaCuentaSeleccionada);
                listVigenciasCuentasBorrar.add(vigenciaCuentaSeleccionada);
            } else if (!listVigenciasCuentasCrear.isEmpty() && listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                listVigenciasCuentasCrear.remove(vigenciaCuentaSeleccionada);
            } else {
                listVigenciasCuentasBorrar.add(vigenciaCuentaSeleccionada);
            }
            listVigenciasCuentasConcepto.remove(vigenciaCuentaSeleccionada);
            if (tipoListaVigenciaCuenta == 1) {
                filtrarListVigenciasCuentasConcepto.remove(vigenciaCuentaSeleccionada);
            }
            contarRegistrosCuentas();
            vigenciaCuentaSeleccionada = null;
            cambiosVigenciaCuenta = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVigenciaCuenta");
        }
    }

    public void borrarVigenciaGrupoConcepto() {
        if (vigenciaGrupoCoSeleccionada != null) {
            if (!listVigenciasGruposConceptosModificar.isEmpty() && listVigenciasGruposConceptosModificar.contains(vigenciaGrupoCoSeleccionada)) {
                listVigenciasGruposConceptosModificar.remove(vigenciaGrupoCoSeleccionada);
                listVigenciasGruposConceptosBorrar.add(vigenciaGrupoCoSeleccionada);
            } else if (!listVigenciasGruposConceptosCrear.isEmpty() && listVigenciasGruposConceptosCrear.contains(vigenciaGrupoCoSeleccionada)) {
                listVigenciasGruposConceptosCrear.remove(vigenciaGrupoCoSeleccionada);
            } else {
                listVigenciasGruposConceptosBorrar.add(vigenciaGrupoCoSeleccionada);
            }
            listVigenciasGruposConceptos.remove(vigenciaGrupoCoSeleccionada);
            if (tipoListaVigenciaGrupoConcepto == 1) {
                filtrarListVigenciasGruposConceptos.remove(vigenciaGrupoCoSeleccionada);
            }
            contarRegistrosGrupoC();
            vigenciaGrupoCoSeleccionada = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosVigenciaGrupoConcepto = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVigenciaGrupoConcepto");
        }
    }

    public void borrarVigenciaConceptoTT() {
        if (vigenciaConceptoTTSeleccionada != null) {
            if (!listVigenciasConceptosTTModificar.isEmpty() && listVigenciasConceptosTTModificar.contains(vigenciaConceptoTTSeleccionada)) {
                listVigenciasConceptosTTModificar.remove(vigenciaConceptoTTSeleccionada);
                listVigenciasConceptosTTBorrar.add(vigenciaConceptoTTSeleccionada);
            } else if (!listVigenciasConceptosTTCrear.isEmpty() && listVigenciasConceptosTTCrear.contains(vigenciaConceptoTTSeleccionada)) {
                listVigenciasConceptosTTCrear.remove(vigenciaConceptoTTSeleccionada);
            } else {
                listVigenciasConceptosTTBorrar.add(vigenciaConceptoTTSeleccionada);
            }
            listVigenciasConceptosTTConcepto.remove(vigenciaConceptoTTSeleccionada);
            if (tipoListaVigenciaConceptoTT == 1) {
                filtrarListVigenciasConceptosTT.remove(vigenciaConceptoTTSeleccionada);
            }
            contarRegistrosConceptoTT();
            vigenciaConceptoTTSeleccionada = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosVigenciaConceptoTT = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVigenciaConceptoTT");
        }
    }

    public void borrarVigenciaConceptoTC() {
        if (vigenciaConceptoTCSeleccionada != null) {
            if (!listVigenciasConceptosTCModificar.isEmpty() && listVigenciasConceptosTCModificar.contains(vigenciaConceptoTCSeleccionada)) {
                listVigenciasConceptosTCModificar.remove(vigenciaConceptoTCSeleccionada);
                listVigenciasConceptosTCBorrar.add(vigenciaConceptoTCSeleccionada);
            } else if (!listVigenciasConceptosTCCrear.isEmpty() && listVigenciasConceptosTCCrear.contains(vigenciaConceptoTCSeleccionada)) {
                listVigenciasConceptosTCCrear.remove(vigenciaConceptoTCSeleccionada);
            } else {
                listVigenciasConceptosTCBorrar.add(vigenciaConceptoTCSeleccionada);
            }
            listVigenciasConceptosTCConcepto.remove(vigenciaConceptoTCSeleccionada);
            if (tipoListaVigenciaConceptoTC == 1) {
                filtrarListVigenciasConceptosTC.remove(vigenciaConceptoTCSeleccionada);
            }
            contarRegistrosConceptoTC();
            vigenciaConceptoTCSeleccionada = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosVigenciaConceptoTC = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVigenciaConceptoTC");
        }
    }

    public void borrarVigenciaConceptoRL() {
        if (vigenciaConceptoRLSeleccionada != null) {
            if (!listVigenciasConceptosRLModificar.isEmpty() && listVigenciasConceptosRLModificar.contains(vigenciaConceptoRLSeleccionada)) {
                listVigenciasConceptosRLModificar.remove(vigenciaConceptoRLSeleccionada);
                listVigenciasConceptosRLBorrar.add(vigenciaConceptoRLSeleccionada);
            } else if (!listVigenciasConceptosRLCrear.isEmpty() && listVigenciasConceptosRLCrear.contains(vigenciaConceptoRLSeleccionada)) {
                listVigenciasConceptosRLCrear.remove(vigenciaConceptoRLSeleccionada);
            } else {
                listVigenciasConceptosRLBorrar.add(vigenciaConceptoRLSeleccionada);
            }
            listVigenciasConceptosRLConcepto.remove(vigenciaConceptoRLSeleccionada);
            if (tipoListaVigenciaConceptoRL == 1) {
                filtrarListVigenciasConceptosRL.remove(vigenciaConceptoRLSeleccionada);
            }
            contarRegistrosConceptoRL();
            vigenciaConceptoRLSeleccionada = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosVigenciaConceptoRL = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVigenciaConceptoRL");
        }
    }

    public void borrarFormulasConceptos() {
        if (vigFormulaConceptoSeleccionada != null) {
            if (!listFormulasConceptosModificar.isEmpty() && listFormulasConceptosModificar.contains(vigFormulaConceptoSeleccionada)) {
                listFormulasConceptosModificar.remove(vigFormulaConceptoSeleccionada);
                listFormulasConceptosBorrar.add(vigFormulaConceptoSeleccionada);
            } else if (!listFormulasConceptosCrear.isEmpty() && listFormulasConceptosCrear.contains(vigFormulaConceptoSeleccionada)) {
                listFormulasConceptosCrear.remove(vigFormulaConceptoSeleccionada);
            } else {
                listFormulasConceptosBorrar.add(vigFormulaConceptoSeleccionada);
            }
            listFormulasConceptos.remove(vigFormulaConceptoSeleccionada);
            if (tipoListaFormulasConceptos == 1) {
                filtrarListFormulasConceptos.remove(vigFormulaConceptoSeleccionada);
            }
            contarRegistrosFormulaConcepto();
            vigFormulaConceptoSeleccionada = null;

            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            cambiosFormulasConceptos = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:detalleFormula");
            context.update("form:datosFormulaConcepto");
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        vigenciaConceptoRLSeleccionada = null;
        vigenciaConceptoTTSeleccionada = null;
        vigenciaConceptoTCSeleccionada = null;
        vigenciaCuentaSeleccionada = null;
        vigenciaGrupoCoSeleccionada = null;
        vigFormulaConceptoSeleccionada = null;
        System.out.println("Entro en activarCtrlF11()");
//        if (vigenciaCuentaSeleccionada != null) {
        filtradoVigenciaCuenta();
//        }
//        if (vigenciaGrupoCoSeleccionada != null) {
        filtradoVigenciaGrupoConcepto();
//        }
//        if (vigenciaConceptoTTSeleccionada != null) {
        filtradoVigenciaConceptoTT();
//        }
//        if (vigenciaConceptoTCSeleccionada != null) {
        filtradoVigenciaConceptoTC();
//        }
//        if (vigenciaConceptoRLSeleccionada != null) {
        filtradoVigenciaConceptoRL();
//        }
//        if (vigFormulaConceptoSeleccionada != null) {
        filtradoFormulasConceptos();
        formulaSeleccionada = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:detalleFormula");
//        }
    }

    public void filtradoVigenciaCuenta() {
        System.out.println("banderaVigenciaCuenta : " + banderaVigenciaCuenta);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVigenciaCuenta == 0) {
            altoTablaVigenciaCuenta = "83";
            vigenciaCuentaFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
            vigenciaCuentaFechaInicial.setFilterStyle("width: 90%");
            vigenciaCuentaFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
            vigenciaCuentaFechaFinal.setFilterStyle("width: 90%");
            vigenciaCuentaTipoCC = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
            vigenciaCuentaTipoCC.setFilterStyle("width: 90%");
            vigenciaCuentaDebitoCod = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
            vigenciaCuentaDebitoCod.setFilterStyle("width: 90%");
            vigenciaCuentaDebitoDes = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
            vigenciaCuentaDebitoDes.setFilterStyle("width: 90%");
            vigenciaCuentaCCConsolidadorD = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
            vigenciaCuentaCCConsolidadorD.setFilterStyle("width: 90%");
            vigenciaCuentaCreditoCod = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
            vigenciaCuentaCreditoCod.setFilterStyle("width: 90%");
            vigenciaCuentaCreditoDes = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
            vigenciaCuentaCreditoDes.setFilterStyle("width: 90%");
            vigenciaCuentaCCConsolidadorC = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
            vigenciaCuentaCCConsolidadorC.setFilterStyle("width: 90%");
            banderaVigenciaCuenta = 1;
        } else if (banderaVigenciaCuenta == 1) {
            recargarVigenciaCuentaDefault();
        }
        RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
    }

    public void filtradoVigenciaGrupoConcepto() {
        System.out.println("banderaVigenciaGrupoConcepto : " + banderaVigenciaGrupoConcepto);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVigenciaGrupoConcepto == 0) {
            altoTablaVigenciaGrupoC = "98";
            vigenciaGCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
            vigenciaGCFechaInicial.setFilterStyle("width: 90%");
            vigenciaGCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
            vigenciaGCFechaFinal.setFilterStyle("width: 90%");
            vigenciaGCCodigo = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
            vigenciaGCCodigo.setFilterStyle("width: 90%");
            vigenciaGCDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
            vigenciaGCDescripcion.setFilterStyle("width: 90%");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
            banderaVigenciaGrupoConcepto = 1;
        } else if (banderaVigenciaGrupoConcepto == 1) {
            recargarVigenciaGrupoCDefault();
        }
        RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
    }

    public void filtradoVigenciaConceptoTT() {
        System.out.println("banderaVigenciaConceptoTT : " + banderaVigenciaConceptoTT);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVigenciaConceptoTT == 0) {
            altoTablaVigenciaConceptoTT = "83";
            vigenciaCTTFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
            vigenciaCTTFechaFinal.setFilterStyle("width: 90%");
            vigenciaCTTFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
            vigenciaCTTFechaInicial.setFilterStyle("width: 90%");
            vigenciaCTTDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
            vigenciaCTTDescripcion.setFilterStyle("width: 90%");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
            banderaVigenciaConceptoTT = 1;
        } else if (banderaVigenciaConceptoTT == 1) {
            recargarVigenciaConceptoTT();
        }
        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
    }

    public void filtradoVigenciaConceptoTC() {
        System.out.println("banderaVigenciaConceptoTC : " + banderaVigenciaConceptoTC);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVigenciaConceptoTC == 0) {
            altoTablaVigenciaConceptoTC = "83";
            vigenciaCTCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
            vigenciaCTCFechaFinal.setFilterStyle("width: 90%");
            vigenciaCTCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
            vigenciaCTCFechaInicial.setFilterStyle("width: 90%");
            vigenciaCTCDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
            vigenciaCTCDescripcion.setFilterStyle("width: 90%");
            banderaVigenciaConceptoTC = 1;
        } else if (banderaVigenciaConceptoTC == 1) {
            recargarVigenciaConceptoTC();
        }
        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
    }

    public void filtradoVigenciaConceptoRL() {
        System.out.println("banderaVigenciaConceptoRL : " + banderaVigenciaConceptoRL);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVigenciaConceptoRL == 0) {
            altoTablaVigenciaConceptoRL = "83";
            vigenciaCRLFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
            vigenciaCRLFechaFinal.setFilterStyle("width: 90%");
            vigenciaCRLFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
            vigenciaCRLFechaInicial.setFilterStyle("width: 90%");
            vigenciaCRLDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
            vigenciaCRLDescripcion.setFilterStyle("width: 90%");
            banderaVigenciaConceptoRL = 1;
        } else if (banderaVigenciaConceptoRL == 1) {
            recargarVigenciaConceptoRT();
        }
        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
    }

    public void filtradoFormulasConceptos() {
        System.out.println("banderaFormulasConceptos : " + banderaFormulasConceptos);
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaFormulasConceptos == 0) {
            altoTablaFormulaConcepto = "100";
            formulaCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
            formulaCFechaInicial.setFilterStyle("width: 90%");
            formulaCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
            formulaCFechaFinal.setFilterStyle("width: 90%");
            formulaCNombre = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
            formulaCNombre.setFilterStyle("width: 90%");
            formulaCOrden = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
            formulaCOrden.setFilterStyle("width: 90%");
            banderaFormulasConceptos = 1;
        } else if (banderaFormulasConceptos == 1) {
            recargarFormulaConcepto();
        }
        RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
    }

    //Activar y desactivar el boton de listas de valores : 
    public void anularBotonLOV() {
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void activarBotonLOV() {
        activarLOV = false;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaVigenciaCuenta == 1) {
            recargarVigenciaCuentaDefault();
        }
        if (banderaVigenciaGrupoConcepto == 1) {
            recargarVigenciaGrupoCDefault();
        }
        if (banderaVigenciaConceptoTT == 1) {
            recargarVigenciaConceptoTT();
        }
        if (banderaVigenciaConceptoTC == 1) {
            recargarVigenciaConceptoTC();
        }
        if (banderaVigenciaConceptoRL == 1) {
            recargarVigenciaConceptoRT();
        }

        if (banderaFormulasConceptos == 1) {
            recargarFormulaConcepto();
        }

        listVigenciasCuentasBorrar.clear();
        listVigenciasCuentasCrear.clear();
        listVigenciasCuentasModificar.clear();

        listVigenciasGruposConceptosBorrar.clear();
        listVigenciasGruposConceptosCrear.clear();
        listVigenciasGruposConceptosModificar.clear();

        listVigenciasConceptosTTBorrar.clear();
        listVigenciasConceptosTTCrear.clear();
        listVigenciasConceptosTTModificar.clear();

        listVigenciasConceptosTCBorrar.clear();
        listVigenciasConceptosTCCrear.clear();
        listVigenciasConceptosTCModificar.clear();

        listVigenciasConceptosRLBorrar.clear();
        listVigenciasConceptosRLCrear.clear();
        listVigenciasConceptosRLModificar.clear();

        listFormulasConceptosBorrar.clear();
        listFormulasConceptosCrear.clear();
        listFormulasConceptosModificar.clear();

        vigenciaGrupoCoSeleccionada = null;
        vigenciaCuentaSeleccionada = null;
        vigenciaConceptoRLSeleccionada = null;
        vigenciaConceptoTTSeleccionada = null;
        vigenciaConceptoTCSeleccionada = null;
        vigFormulaConceptoSeleccionada = null;

        conceptoActual = null;
        k = 0;

        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptos = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptos = null;

        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

        cambiosVigenciaCuenta = false;
        cambiosVigenciaGrupoConcepto = false;
        cambiosVigenciaConceptoTT = false;
        cambiosVigenciaConceptoTC = false;
        cambiosVigenciaConceptoRL = false;
        cambiosFormulasConceptos = false;

        nuevaVigenciaCuenta = new VigenciasCuentas();
        nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
        nuevaVigenciaConceptoTC = new VigenciasConceptosTC();
        nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
        nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        nuevaFormulasConceptos = new FormulasConceptos();

        duplicarVigenciaCuenta = new VigenciasCuentas();
        duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
        duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
        duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
        duplicarFormulasConceptos = new FormulasConceptos();

        lovTiposContratos = null;
        lovTiposTrabajadores = null;
        lovGruposConceptos = null;
        lovCentrosCostos = null;
        lovCuentas = null;
        lovTiposCentrosCostos = null;
        lovReformasLaborales = null;
        lovFormulas = null;
        lovFormulasConceptos = null;

        formulaSeleccionada = true;
        paginaRetorno = "";
    }

    public void actualizarTipoCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaCuentaSeleccionada.setTipocc(tipoCentroCostoSeleccionadoLOV);
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;

            context.update("form:datosVigenciaCuenta");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setTipocc(tipoCentroCostoSeleccionadoLOV);
            context.update("formularioDialogos:nuevaTipoCCVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setTipocc(tipoCentroCostoSeleccionadoLOV);
            context.update("formularioDialogos:duplicarTipoCCVC");
        }
        tipoCentroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;

        context.update("form:TipoCCDialogo");
        context.update("form:lovTiposCC");
        context.update("form:TipoCCDialogo:aceptarTCC");
        context.reset("form:lovTiposCC:globalFilter");
        context.execute("lovTiposCC.clearFilters()");
        context.execute("TipoCCDialogo.hide()");
    }

    public void cancelarCambioTipoCentroCosto() {
        tipoCentroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovTiposCC");
        context.update("form:TipoCCDialogo");
        context.update("form:TipoCCDialogo:aceptarTCC");
        context.reset("form:lovTiposCC:globalFilter");
        context.execute("lovTiposCC.clearFilters()");
        context.execute("TipoCCDialogo.hide()");
    }

    public void actualizarCuentaDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaCuentaSeleccionada.setCuentad(cuentaSeleccionadaLOV);
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            context.update("form:datosVigenciaCuenta");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setCuentad(cuentaSeleccionadaLOV);
            context.update("formularioDialogos:nuevaDebitoVC");
            context.update("formularioDialogos:nuevaDesDebitoVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setCuentad(cuentaSeleccionadaLOV);
            context.update("formularioDialogos:duplicarDebitoVC");
            context.update("formularioDialogos:duplicaDesDebitoVC");
        }
        filtrarListCuentas = null;
        cuentaSeleccionadaLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovDebito");
        context.update("form:DebitoDialogo");
        context.update("form:DebitoDialogo:aceptarCD");
        context.reset("form:lovDebito:globalFilter");
        context.execute("lovDebito.clearFilters()");
        context.execute("DebitoDialogo.hide()");
    }

    public void cancelarCambioCuentaDebito() {
        filtrarListCuentas = null;
        cuentaSeleccionadaLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovDebito");
        context.update("form:DebitoDialogo");
        context.update("form:DebitoDialogo:aceptarCD");
        context.reset("form:lovDebito:globalFilter");
        context.execute("lovDebito.clearFilters()");
        context.execute("DebitoDialogo.hide()");
    }

    public void actualizarCuentaCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaCuentaSeleccionada.setCuentac(cuentaSeleccionadaLOV);
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            context.update("form:datosVigenciaCuenta");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setCuentac(cuentaSeleccionadaLOV);
            context.update("formularioDialogos:nuevaCreditoVC");
            context.update("formularioDialogos:nuevaDesCreditoVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setCuentac(cuentaSeleccionadaLOV);
            context.update("formularioDialogos:duplicaCreditoVC");
            context.update("formularioDialogos:duplicaDesCreditoVC");
        }
        filtrarListCuentas = null;
        cuentaSeleccionadaLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovCredito");
        context.update("form:CreditoDialogo");
        context.update("form:CreditoDialogo:aceptarCC");
        context.reset("form:lovCredito:globalFilter");
        context.execute("lovCredito.clearFilters()");
        context.execute("CreditoDialogo.hide()");
    }

    public void cancelarCambioCuentaCredito() {
        filtrarListCuentas = null;
        cuentaSeleccionadaLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovCredito");
        context.update("form:CreditoDialogo");
        context.update("form:CreditoDialogo:aceptarCC");
        context.reset("form:lovCredito:globalFilter");
        context.execute("lovCredito.clearFilters()");
        context.execute("CreditoDialogo.hide()");
    }

    public void actualizarCentroCostoDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaCuentaSeleccionada.setConsolidadord(centroCostoSeleccionadoLOV);
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            context.update("form:datosVigenciaCuenta");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setConsolidadord(centroCostoSeleccionadoLOV);
            context.update("formularioDialogos:nuevaConsoliDebVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setConsolidadord(centroCostoSeleccionadoLOV);
            context.update("formularioDialogos:duplicaConsoliDebVC");
        }
        filtrarListCentrosCostos = null;
        centroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovCentroCostoD");
        context.update("form:CentroCostoDDialogo");
        context.update("form:CentroCostoDDialogo:aceptarCCD");
        context.reset("form:lovCentroCostoD:globalFilter");
        context.execute("lovCentroCostoD.clearFilters()");
        context.execute("CentroCostoDDialogo.hide()");
    }

    public void cancelarCambioCentroCostoDebito() {
        filtrarListCentrosCostos = null;
        centroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovCentroCostoD");
        context.update("form:CentroCostoDDialogo");
        context.update("form:CentroCostoDDialogo:aceptarCCD");
        context.reset("form:lovCentroCostoD:globalFilter");
        context.execute("lovCentroCostoD.clearFilters()");
        context.execute("CentroCostoDDialogo.hide()");
    }

    public void actualizarCentroCostoCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaCuentaSeleccionada.setConsolidadorc(centroCostoSeleccionadoLOV);
            if (!listVigenciasCuentasCrear.contains(vigenciaCuentaSeleccionada)) {
                if (listVigenciasCuentasModificar.isEmpty()) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                } else if (!listVigenciasCuentasModificar.contains(vigenciaCuentaSeleccionada)) {
                    listVigenciasCuentasModificar.add(vigenciaCuentaSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;

            context.update("form:datosVigenciaCuenta");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setConsolidadorc(centroCostoSeleccionadoLOV);
            context.update("formularioDialogos:nuevaConsoliCreVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setConsolidadorc(centroCostoSeleccionadoLOV);
            context.update("formularioDialogos:duplicaConsoliCreVC");
        }
        filtrarListCentrosCostos = null;
        centroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovCentroCostoC");
        context.update("form:CentroCostoCDialogo");
        context.update("form:CentroCostoCDialogo:aceptarCCC");
        context.reset("form:lovCentroCostoC:globalFilter");
        context.execute("lovCentroCostoC.clearFilters()");
        context.execute("CentroCostoCDialogo.hide()");
    }

    public void cancelarCambioCentroCostoCredito() {
        filtrarListCentrosCostos = null;
        centroCostoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovCentroCostoC");
        context.update("form:CentroCostoCDialogo");
        context.update("form:CentroCostoCDialogo:aceptarCCC");
        context.reset("form:lovCentroCostoC:globalFilter");
        context.execute("lovCentroCostoC.clearFilters()");
        context.execute("CentroCostoCDialogo.hide()");
    }

    public void actualizarGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaGrupoCoSeleccionada.setGrupoconcepto(grupoConceptoSeleccionadoLOV);
            if (!listVigenciasGruposConceptosCrear.contains(vigenciaGrupoCoSeleccionada)) {
                if (listVigenciasGruposConceptosModificar.isEmpty()) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                } else if (!listVigenciasGruposConceptosModificar.contains(vigenciaGrupoCoSeleccionada)) {
                    listVigenciasGruposConceptosModificar.add(vigenciaGrupoCoSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaGrupoConcepto = true;
            cambiosVigenciaGrupoConcepto = true;
            context.update("form:datosVigenciaGrupoConcepto");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaGrupoConcepto.setGrupoconcepto(grupoConceptoSeleccionadoLOV);
            context.update("formularioDialogos:nuevaCodigoVGC");
            context.update("formularioDialogos:nuevaDescripcionVGC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaGrupoConcepto.setGrupoconcepto(grupoConceptoSeleccionadoLOV);
            context.update("formularioDialogos:duplicarCodigoVGC");
            context.update("formularioDialogos:duplicarDescripcionVGC");
        }
        filtrarListGruposConceptos = null;
        grupoConceptoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovGrupoConcepto");
        context.update("form:GruposConceptosDialogo");
        context.update("form:GruposConceptosDialogo:aceptarGC");
        context.reset("form:lovGrupoConcepto:globalFilter");
        context.execute("lovGrupoConcepto.clearFilters()");
        context.execute("GruposConceptosDialogo.hide()");
    }

    public void cancelarCambioGrupoConcepto() {
        filtrarListGruposConceptos = null;
        grupoConceptoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaGrupoConcepto = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovGrupoConcepto");
        context.update("form:GruposConceptosDialogo");
        context.update("form:GruposConceptosDialogo:aceptarGC");
        context.reset("form:lovGrupoConcepto:globalFilter");
        context.execute("lovGrupoConcepto.clearFilters()");
        context.execute("GruposConceptosDialogo.hide()");
    }

    public void actualizarTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaConceptoTTSeleccionada.setTipotrabajador(tipoTrabajadorSeleccionadoLOV);
            if (!listVigenciasConceptosTTCrear.contains(vigenciaConceptoTTSeleccionada)) {
                if (listVigenciasConceptosTTModificar.isEmpty()) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                } else if (!listVigenciasConceptosTTModificar.contains(vigenciaConceptoTTSeleccionada)) {
                    listVigenciasConceptosTTModificar.add(vigenciaConceptoTTSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaConceptoTT = true;
            cambiosVigenciaConceptoTT = true;
            context.update("form:datosVigenciaConceptoTT");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoTT.setTipotrabajador(tipoTrabajadorSeleccionadoLOV);
            context.update("formularioDialogos:nuevaTrabajadorVCTT");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoTT.setTipotrabajador(tipoTrabajadorSeleccionadoLOV);
            context.update("formularioDialogos:duplicarTrabajadorVCTT");
        }
        filtrarListTiposTrabajadores = null;
        tipoTrabajadorSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovTipoTrabajador");
        context.update("form:TipoTrabajadorDialogo");
        context.update("form:TipoTrabajadorDialogo:aceptarTTr");
        context.reset("form:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    public void cancelarCambioTipoTrabajador() {
        filtrarListTiposTrabajadores = null;
        tipoTrabajadorSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoTT = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovTipoTrabajador");
        context.update("form:TipoTrabajadorDialogo");
        context.update("form:TipoTrabajadorDialogo:aceptarTTr");
        context.reset("form:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    public void actualizarTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaConceptoTCSeleccionada.setTipocontrato(tipoContratoSeleccionadoLOV);
            if (!listVigenciasConceptosTCCrear.contains(vigenciaConceptoTCSeleccionada)) {
                if (listVigenciasConceptosTCModificar.isEmpty()) {
                    listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                } else if (!listVigenciasConceptosTCModificar.contains(vigenciaConceptoTCSeleccionada)) {
                    listVigenciasConceptosTCModificar.add(vigenciaConceptoTCSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaConceptoTC = true;
            cambiosVigenciaConceptoTC = true;

            context.update("form:datosVigenciaConceptoTC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoTC.setTipocontrato(tipoContratoSeleccionadoLOV);
            context.update("formularioDialogos:nuevaContratoVCTC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoTC.setTipocontrato(tipoContratoSeleccionadoLOV);
            context.update("formularioDialogos:duplicarContratoVCTC");
        }
        filtrarListTiposContratos = null;
        tipoContratoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovTipoContrato");
        context.update("form:TipoContratosDialogo");
        context.update("form:TipoContratosDialogo:aceptarTCt");
        context.reset("form:lovTipoContrato:globalFilter");
        context.execute("lovTipoContrato.clearFilters()");
        context.execute("TipoContratosDialogo.hide()");
    }

    public void cancelarCambioTipoContrato() {
        filtrarListTiposContratos = null;
        tipoContratoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoTC = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovTipoContrato");
        context.update("form:TipoContratosDialogo");
        context.update("form:TipoContratosDialogo:aceptarTCt");
        context.reset("form:lovTipoContrato:globalFilter");
        context.execute("lovTipoContrato.clearFilters()");
        context.execute("TipoContratosDialogo.hide()");
    }

    public void actualizarReformaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaConceptoRLSeleccionada.setTiposalario(reformaLaboralSeleccionadoLOV);
            if (!listVigenciasConceptosRLCrear.contains(vigenciaConceptoRLSeleccionada)) {
                if (listVigenciasConceptosRLModificar.isEmpty()) {
                    listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                } else if (!listVigenciasConceptosRLModificar.contains(vigenciaConceptoRLSeleccionada)) {
                    listVigenciasConceptosRLModificar.add(vigenciaConceptoRLSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVigenciaConceptoRL = true;
            cambiosVigenciaConceptoRL = true;
            context.update("form:datosVigenciaConceptoRL");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoRL.setTiposalario(reformaLaboralSeleccionadoLOV);
            context.update("formularioDialogos:nuevaReformaVCRL");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoRL.setTiposalario(reformaLaboralSeleccionadoLOV);
            context.update("formularioDialogos:duplicarReformaVCRL");
        }
        filtrarListReformasLaborales = null;
        reformaLaboralSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovReformaLaboral");
        context.update("form:ReformaLaboralDialogo");
        context.update("form:ReformaLaboralDialogo:aceptarRL");
        context.reset("form:lovReformaLaboral:globalFilter");
        context.execute("lovReformaLaboral.clearFilters()");
        context.execute("ReformaLaboralDialogo.hide()");
    }

    public void cancelarCambioReformaLaboral() {
        filtrarListReformasLaborales = null;
        reformaLaboralSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoRL = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovReformaLaboral");
        context.update("form:ReformaLaboralDialogo");
        context.update("form:ReformaLaboralDialogo:aceptarRL");
        context.reset("form:lovReformaLaboral:globalFilter");
        context.execute("lovReformaLaboral.clearFilters()");
        context.execute("ReformaLaboralDialogo.hide()");
    }

    public void actualizarFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigFormulaConceptoSeleccionada.setFormula(formulaSeleccionadoLOV);
            if (!listFormulasConceptosCrear.contains(vigFormulaConceptoSeleccionada)) {
                if (listFormulasConceptosModificar.isEmpty()) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                } else if (!listFormulasConceptosModificar.contains(vigFormulaConceptoSeleccionada)) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexFormulasConceptos = true;
            cambiosFormulasConceptos = true;
            context.update(":form:editarNombreFC");
        } else if (tipoActualizacion == 1) {
            nuevaFormulasConceptos.setFormula(formulaSeleccionadoLOV);
            context.update("formularioDialogos:nuevaFormulaFC");
        } else if (tipoActualizacion == 2) {
            duplicarFormulasConceptos.setFormula(formulaSeleccionadoLOV);
            context.update("formularioDialogos:duplicarFormulaFC");
        }
        filtrarListFormulas = null;
        formulaSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.update("form:lovFormula");
        context.update("form:FormulasDialogo");
        context.update("form:FormulasDialogo:aceptarF");
        context.reset("form:lovFormula:globalFilter");
        context.execute("lovFormula.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void cancelarCambioFormula() {
        filtrarListFormulas = null;
        formulaSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexFormulasConceptos = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:lovFormula");
        context.update("form:FormulasDialogo");
        context.update("form:FormulasDialogo:aceptarF");
        context.reset("form:lovFormula:globalFilter");
        context.execute("lovFormula.clearFilters()");
        context.execute("FormulasDialogo.hide()");
    }

    public void actualizarOrden() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaFormulasConceptos == 0) {
            vigFormulaConceptoSeleccionada.setStrOrden(formulaConceptoSeleccionadoLOV.getStrOrden());
            if (!listFormulasConceptosCrear.contains(vigFormulaConceptoSeleccionada)) {
                if (listFormulasConceptosModificar.isEmpty()) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                } else if (!listFormulasConceptosModificar.contains(vigFormulaConceptoSeleccionada)) {
                    listFormulasConceptosModificar.add(vigFormulaConceptoSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexFormulasConceptos = true;
            cambiosFormulasConceptos = true;

            context.update("form:datosFormulaConcepto");
        } else if (tipoActualizacion == 1) {
            nuevaFormulasConceptos.setStrOrden(formulaConceptoSeleccionadoLOV.getStrOrden());
            context.update("formularioDialogos:nuevaOrdenFC");
        } else if (tipoActualizacion == 2) {
            duplicarFormulasConceptos.setStrOrden(formulaConceptoSeleccionadoLOV.getStrOrden());
            context.update("formularioDialogos:duplicarOrdenFC");
        }
        filtrarListFormulasConceptos = null;
        formulaConceptoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.reset("form:lovFormulaC");
        context.reset("form:FormulaConceptoDialogo");
        context.reset("form:FormulaConceptoDialogo:aceptarFC");
        context.reset("form:lovFormulaC:globalFilter");
        context.execute("lovFormulaC.clearFilters()");
        context.execute("FormulaConceptoDialogo.hide()");
    }

    public void cancelarCambioOrden() {
        filtrarListFormulasConceptos = null;
        formulaConceptoSeleccionadoLOV = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexFormulasConceptos = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovFormulaC");
        context.reset("form:FormulaConceptoDialogo");
        context.reset("form:FormulaConceptoDialogo:aceptarFC");
        context.reset("form:lovFormulaC:globalFilter");
        context.execute("lovFormulaC.clearFilters()");
        context.execute("FormulaConceptoDialogo.hide()");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     * Selecciona la tabla a exportar XML con respecto al index activo
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (vigenciaCuentaSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreXML = "VigenciasCuentas_XML";
        }
        if (vigenciaGrupoCoSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreXML = "VigenciasGruposConceptos_XML";
        }
        if (vigenciaConceptoTTSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreXML = "VigenciasConceptosTT_XML";
        }
        if (vigenciaConceptoTCSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreXML = "VigenciasConceptosTC_XML";
        }
        if (vigenciaConceptoRLSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreXML = "VigenciasConceptosRL_XML";
        }
        if (vigFormulaConceptoSeleccionada != null) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreXML = "FormulasConceptos_XML";
            formulaSeleccionada = true;
            RequestContext.getCurrentInstance().update("form:detalleFormula");
        }

        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (vigenciaCuentaSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreExportar = "VigenciasCuentas_PDF";
            exportPDF_Tabla();
        }
        if (vigenciaGrupoCoSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreExportar = "VigenciasGruposConceptos_PDF";
            exportPDF_Tabla();
        }
        if (vigenciaConceptoTTSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreExportar = "VigenciasConceptosTT_PDF";
            exportPDF_Tabla();
        }
        if (vigenciaConceptoTCSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreExportar = "VigenciasConceptosTC_PDF";
            exportPDF_Tabla();
        }
        if (vigenciaConceptoRLSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreExportar = "VigenciasConceptosRL_PDF";
            exportPDF_Tabla();
        }
        if (vigFormulaConceptoSeleccionada != null) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreExportar = "FormulasConceptos_PDF";
            exportPDF_Tabla();
            formulaSeleccionada = true;
            RequestContext.getCurrentInstance().update("form:detalleFormula");
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_Tabla() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(nombreTabla);
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, nombreExportar, false, false, "UTF-8", null, null);
        context.responseComplete();

    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (vigenciaCuentaSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreExportar = "VigenciasCuentas_XLS";
            exportXLS_Tabla();
        }
        if (vigenciaGrupoCoSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreExportar = "VigenciasGruposConceptos_XLS";
            exportXLS_Tabla();
        }
        if (vigenciaConceptoTTSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreExportar = "VigenciasConceptosTT_XLS";
            exportXLS_Tabla();
        }
        if (vigenciaConceptoTCSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreExportar = "VigenciasConceptosTC_XLS";
            exportXLS_Tabla();
        }
        if (vigenciaConceptoRLSeleccionada != null) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreExportar = "VigenciasConceptosRL_XLS";
            exportXLS_Tabla();
        }
        if (vigFormulaConceptoSeleccionada != null) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreExportar = "FormulasConceptos_XLS";
            exportXLS_Tabla();
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_Tabla() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(nombreTabla);
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, nombreExportar, false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrarCuenta() {
        if (tipoListaVigenciaCuenta == 0) {
            tipoListaVigenciaCuenta = 1;
        }
        vigenciaCuentaSeleccionada = null;
        contarRegistrosCuentas();
    }

    public void eventoFiltrarGrupo() {
        if (tipoListaVigenciaGrupoConcepto == 0) {
            tipoListaVigenciaGrupoConcepto = 1;
        }
        vigenciaGrupoCoSeleccionada = null;
        contarRegistrosGrupoC();
    }

    public void eventoFiltrarConceptoTT() {
        if (tipoListaVigenciaConceptoTT == 0) {
            tipoListaVigenciaConceptoTT = 1;
        }
        vigenciaConceptoTTSeleccionada = null;
        contarRegistrosConceptoTT();
    }

    public void eventoFiltrarConceptoTC() {
        if (tipoListaVigenciaConceptoTC == 0) {
            tipoListaVigenciaConceptoTC = 1;
        }
        vigenciaConceptoTCSeleccionada = null;
        contarRegistrosConceptoTC();
    }

    public void eventoFiltrarConceptoRL() {
        if (tipoListaVigenciaConceptoRL == 0) {
            tipoListaVigenciaConceptoRL = 1;
        }
        vigenciaConceptoRLSeleccionada = null;
        contarRegistrosConceptoRL();
    }

    public void eventoFiltrarFormulas() {
        formulaSeleccionada = true;
        if (tipoListaFormulasConceptos == 0) {
            tipoListaFormulasConceptos = 1;
        }
        vigFormulaConceptoSeleccionada = null;
        RequestContext.getCurrentInstance().update("form:detalleFormula");
        contarRegistrosFormulaConcepto();
    }

    public void verificarRastroTabla() {
        if (vigenciaCuentaSeleccionada != null) {
            verificarRastroVigenciaCuenta();
        } else if (vigenciaGrupoCoSeleccionada != null) {
            verificarRastroVigenciaGrupoConcepto();
        } else if (vigenciaConceptoTTSeleccionada != null) {
            verificarRastroVigenciaConceptoTT();
        } else if (vigenciaConceptoTCSeleccionada != null) {
            verificarRastroVigenciaConceptoTC();
        } else if (vigenciaConceptoRLSeleccionada != null) {
            verificarRastroVigenciaConceptoRL();
        } else if (vigFormulaConceptoSeleccionada != null) {
            verificarRastroFormulasConceptos();
            formulaSeleccionada = true;
            RequestContext.getCurrentInstance().update("form:detalleFormula");
            //Si ninguno esta seleccionado pregunta por historicos
        } else {
            RequestContext.getCurrentInstance().execute("verificarRastrosTablas.show()");
        }
    }

    public void verificarRastroVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaCuentaSeleccionada.getSecuencia(), "VIGENCIASCUENTAS");
        backUp = vigenciaCuentaSeleccionada.getSecuencia();
        vigenciaCuentaSeleccionada = null;
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasCuentas";
            msnConfirmarRastro = "La tabla VIGENCIASCUENTAS tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaCuentaHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASCUENTAS")) {
            nombreTablaRastro = "VigenciasCuentas";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASCUENTAS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }

    }

    public void verificarRastroVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaGrupoCoSeleccionada.getSecuencia(), "VIGENCIASGRUPOSCONCEPTOS");
        backUp = vigenciaGrupoCoSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasGruposConceptos";
            msnConfirmarRastro = "La tabla VIGENCIASGRUPOSCONCEPTOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaGrupoConceptoHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASGRUPOSCONCEPTOS")) {
            nombreTablaRastro = "VigenciasGruposConceptos";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASGRUPOSCONCEPTOS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaConceptoTTSeleccionada.getSecuencia(), "VIGENCIASCONCEPTOSTT");
        backUp = vigenciaConceptoTTSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasConceptosTT";
            msnConfirmarRastro = "La tabla VIGENCIASCONCEPTOSTT tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaConceptoTTHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSTT")) {
            nombreTablaRastro = "VigenciasConceptosTT";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSTT tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaConceptoTCSeleccionada.getSecuencia(), "VIGENCIASCONCEPTOSTC");
        backUp = vigenciaConceptoTCSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasConceptosTC";
            msnConfirmarRastro = "La tabla VIGENCIASCONCEPTOSTC tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaConceptoTCHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSTC")) {
            nombreTablaRastro = "VigenciasConceptosTC";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSTC tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaConceptoRLSeleccionada.getSecuencia(), "VIGENCIASCONCEPTOSRL");
        backUp = vigenciaConceptoRLSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasConceptosRL";
            msnConfirmarRastro = "La tabla VIGENCIASCONCEPTOSRL tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaConceptoRLHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSRL")) {
            nombreTablaRastro = "VigenciasConceptosRL";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSRL tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigFormulaConceptoSeleccionada.getSecuencia(), "FORMULASCONCEPTOS");
        backUp = vigFormulaConceptoSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "FormulasConceptos";
            msnConfirmarRastro = "La tabla FORMULASCONCEPTOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroFormulasConceptosHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("FORMULASCONCEPTOS")) {
            nombreTablaRastro = "FormulasConceptos";
            msnConfirmarRastroHistorico = "La tabla FORMULASCONCEPTOS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void validarEliminacionConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = administrarDetalleConcepto.verificarSolucionesNodosConcepto(conceptoActual.getSecuencia());
        if (retorno == true) {
            context.execute("errorEliminacionConcepto.show()");
        } else {
            context.execute("paso1Eliminacion.show()");
        }
    }

    public void eliminarConcepto() {
        boolean rep = administrarDetalleConcepto.eliminarConceptoTotal(conceptoActual.getSecuencia());
        if (rep == true) {
            salir();
            paginaRetorno = "retornoConcepto";
        } else {
            FacesMessage msg = new FacesMessage("Información", "El reporte no pudo ser eliminado.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            paginaRetorno = "";
        }
    }

    public String conceptoAEliminar() {
        if (vigFormulaConceptoSeleccionada != null) {
            conceptoEliminar = "Va a eliminar el concepto el cual tiene el código : " + vigFormulaConceptoSeleccionada.getConcepto().getCodigo().toString() + ".¿Esta seguro?";
        }
        return conceptoEliminar;
    }

    public void recargarVigenciaCuentaDefault() {
        altoTablaVigenciaCuenta = "105";
        FacesContext c = FacesContext.getCurrentInstance();
        vigenciaCuentaFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
        vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
        vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaTipoCC = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
        vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaDebitoCod = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
        vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaDebitoDes = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
        vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaCCConsolidadorD = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
        vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaCreditoCod = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
        vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaCreditoDes = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
        vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCuentaCCConsolidadorC = (Column) c.getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
        vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");
        banderaVigenciaCuenta = 0;
        filtrarListVigenciasCuentasConcepto = null;
        tipoListaVigenciaCuenta = 0;
    }

    public void recargarVigenciaGrupoCDefault() {
        altoTablaVigenciaGrupoC = "120";
        FacesContext c = FacesContext.getCurrentInstance();
        vigenciaGCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
        vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vigenciaGCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
        vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vigenciaGCCodigo = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
        vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
        vigenciaGCDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
        vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
        banderaVigenciaGrupoConcepto = 0;
        filtrarListVigenciasGruposConceptos = null;
        tipoListaVigenciaGrupoConcepto = 0;
    }

    public void recargarVigenciaConceptoTT() {
        altoTablaVigenciaConceptoTT = "105";
        FacesContext c = FacesContext.getCurrentInstance();
        vigenciaCTTFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
        vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCTTFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
        vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCTTDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
        vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
        banderaVigenciaConceptoTT = 0;
        filtrarListVigenciasConceptosTT = null;
        tipoListaVigenciaConceptoTT = 0;
    }

    public void recargarVigenciaConceptoRT() {
        FacesContext c = FacesContext.getCurrentInstance();
        altoTablaVigenciaConceptoRL = "105";
        vigenciaCRLFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
        vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCRLFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
        vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
        vigenciaCRLDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
        vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
        banderaVigenciaConceptoRL = 0;
        filtrarListVigenciasConceptosRL = null;
        tipoListaVigenciaConceptoRL = 0;
    }

    public void recargarFormulaConcepto() {
        FacesContext c = FacesContext.getCurrentInstance();
        altoTablaFormulaConcepto = "122";
        formulaCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
        formulaCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        formulaCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
        formulaCFechaFinal.setFilterStyle("display: none; visibililty: hidden;");
        formulaCNombre = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
        formulaCNombre.setFilterStyle("display: none; visibility: hidden;");
        formulaCOrden = (Column) c.getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
        formulaCOrden.setFilterStyle("display: none; visibility: hidden;");
        banderaFormulasConceptos = 0;
        filtrarListFormulasConceptosConcepto = null;
        tipoListaFormulasConceptos = 0;
    }

    public void recargarVigenciaConceptoTC() {
        altoTablaVigenciaConceptoTC = "105";
        FacesContext c = FacesContext.getCurrentInstance();
        vigenciaCTCFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
        vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCTCFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
        vigenciaCTCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vigenciaCTCDescripcion = (Column) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
        vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
        banderaVigenciaConceptoTC = 0;
        filtrarListVigenciasConceptosTC = null;
        tipoListaVigenciaConceptoTC = 0;
    }

    public void recordarSeleccionesC() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosVigenciaCuenta");
        if (vigenciaCuentaSeleccionada != null) {
            tabla.setSelection(vigenciaCuentaSeleccionada);
        }
    }

    public void recordarSeleccionesGC() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosVigenciaGrupoConcepto");
        if (vigenciaGrupoCoSeleccionada != null) {
            tabla.setSelection(vigenciaGrupoCoSeleccionada);
        }
    }

    public void recordarSeleccionesRL() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosVigenciaConceptoRL");
        if (vigenciaConceptoRLSeleccionada != null) {
            tabla.setSelection(vigenciaConceptoRLSeleccionada);
        }
    }

    public void recordarSeleccionesTT() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosVigenciaConceptoTT");
        if (vigenciaConceptoTTSeleccionada != null) {
            tabla.setSelection(vigenciaConceptoTTSeleccionada);
        }
    }

    public void recordarSeleccionesTC() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosVigenciaConceptoTC");
        if (vigenciaConceptoTCSeleccionada != null) {
            tabla.setSelection(vigenciaConceptoTCSeleccionada);
        }
    }

    public void recordarSeleccionesFC() {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("form:datosFormulaConcepto");
        if (vigFormulaConceptoSeleccionada != null) {
            tabla.setSelection(vigFormulaConceptoSeleccionada);
        }
    }

    public void cargarLOVs() {
        ////////////Listas Valores VigenciasCuentas/////////////
        if (lovTiposCentrosCostos == null) {
            lovTiposCentrosCostos = administrarDetalleConcepto.consultarLOVTiposCentrosCostos();
        }
        if (lovCuentas == null) {
            lovCuentas = administrarDetalleConcepto.consultarLOVCuentas();
        }
        if (lovCentrosCostos == null) {
            lovCentrosCostos = administrarDetalleConcepto.consultarLOVCentrosCostos();
        }
        /////////////Lista Valores VigenciaGrupoConcepto///////////////////////
        if (lovGruposConceptos == null) {
            lovGruposConceptos = administrarDetalleConcepto.consultarLOVGruposConceptos();
        }
        /////////////Lista Valores VigenciasConceptosTT///////////////////////
        if (lovTiposTrabajadores == null) {
            lovTiposTrabajadores = administrarDetalleConcepto.consultarLOVTiposTrabajadores();
        }
        /////////////Lista Valores VigenciasConceptosTC///////////////////////
        if (lovTiposContratos == null) {
            lovTiposContratos = administrarDetalleConcepto.consultarLOVTiposContratos();
        }
        /////////////Lista Valores VigenciasConceptosRL///////////////////////
        if (lovReformasLaborales
                == null) {
            lovReformasLaborales = administrarDetalleConcepto.consultarLOVReformasLaborales();
        }
        /////////////Lista Valores FormulasConceptos///////////////////////
        if (lovFormulas == null) {
            lovFormulas = administrarDetalleConcepto.consultarLOVFormulas();
        }
        if (lovFormulasConceptos == null) {
            lovFormulasConceptos = administrarDetalleConcepto.consultarLOVFormulasConceptos();
        }
    }

    //Conteo de registros: 
    public void contarRegistrosCuentas() {
        if (tipoListaVigenciaCuenta == 1) {
            infoRegistroCuenta = String.valueOf(filtrarListVigenciasCuentasConcepto.size());
        } else if (listVigenciasCuentasConcepto != null) {
            infoRegistroCuenta = String.valueOf(listVigenciasCuentasConcepto.size());
        } else {
            infoRegistroCuenta = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroCuenta");
    }

    public void contarRegistrosGrupoC() {
        if (tipoListaVigenciaGrupoConcepto == 1) {
            infoRegistroGrupoC = String.valueOf(filtrarListVigenciasGruposConceptos.size());
        } else if (listVigenciasGruposConceptos != null) {
            infoRegistroGrupoC = String.valueOf(listVigenciasGruposConceptos.size());
        } else {
            infoRegistroGrupoC = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroGrupoC");
    }

    public void contarRegistrosConceptoTT() {
        if (tipoListaVigenciaConceptoTT == 1) {
            infoRegistroConceptoTT = String.valueOf(filtrarListVigenciasConceptosTT.size());
        } else if (listVigenciasConceptosTTConcepto != null) {
            infoRegistroConceptoTT = String.valueOf(listVigenciasConceptosTTConcepto.size());
        } else {
            infoRegistroConceptoTT = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroConceptoTT");
    }

    public void contarRegistrosConceptoTC() {
        if (tipoListaVigenciaConceptoTC == 1) {
            infoRegistroConceptoTC = String.valueOf(filtrarListVigenciasConceptosTC.size());
        } else if (listVigenciasConceptosTCConcepto != null) {
            infoRegistroConceptoTC = String.valueOf(listVigenciasConceptosTCConcepto.size());
        } else {
            infoRegistroConceptoTC = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroConceptoTC");
    }

    public void contarRegistrosConceptoRL() {
        if (tipoListaVigenciaConceptoRL == 1) {
            infoRegistroConceptoRL = String.valueOf(filtrarListVigenciasConceptosRL.size());
        } else if (listVigenciasConceptosRLConcepto != null) {
            infoRegistroConceptoRL = String.valueOf(listVigenciasConceptosRLConcepto.size());
        } else {
            infoRegistroConceptoRL = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroConceptoRL");
    }

    public void contarRegistrosFormulaConcepto() {
        if (tipoListaFormulasConceptos == 1) {
            infoRegistroFormulaConcepto = String.valueOf(filtrarListFormulasConceptos.size());
        } else if (listFormulasConceptos != null) {
            infoRegistroFormulaConcepto = String.valueOf(listFormulasConceptos.size());
        } else {
            infoRegistroFormulaConcepto = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroFormulaConcepto");
    }

    //Conteo de Registros Listas de Valor:
    public void contarRegistrosLovTipoCentroCosto(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovTipoCentroCosto = String.valueOf(filtrarListTiposCentrosCostos.size());
        } else if (lovTiposCentrosCostos != null) {
            infoRegistroLovTipoCentroCosto = String.valueOf(lovTiposCentrosCostos.size());
        } else {
            infoRegistroLovTipoCentroCosto = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovTipoCentroCosto");
    }

    public void contarRegistrosLovCuentaDebito(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovCuentaDebito = String.valueOf(filtrarListCuentas.size());
        } else if (lovCuentas != null) {
            infoRegistroLovCuentaDebito = String.valueOf(lovCuentas.size());
        } else {
            infoRegistroLovCuentaDebito = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovCuentaDebito");
    }

    public void contarRegistrosLovCuentaCredito(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovCuentaCredito = String.valueOf(filtrarListCuentas.size());
        } else if (lovCuentas != null) {
            infoRegistroLovCuentaCredito = String.valueOf(lovCuentas.size());
        } else {
            infoRegistroLovCuentaCredito = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovCuentaCredito");
    }

    public void contarRegistrosLovCentroCostoDebito(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovCentroCostoDebito = String.valueOf(filtrarListCentrosCostos.size());
        } else if (lovCentrosCostos != null) {
            infoRegistroLovCentroCostoDebito = String.valueOf(lovCentrosCostos.size());
        } else {
            infoRegistroLovCentroCostoDebito = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovCentroCostoDebito");
    }

    public void contarRegistrosLovCentroCostoCredito(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovCentroCostoCredito = String.valueOf(filtrarListCentrosCostos.size());
        } else if (lovCentrosCostos != null) {
            infoRegistroLovCentroCostoCredito = String.valueOf(lovCentrosCostos.size());
        } else {
            infoRegistroLovCentroCostoCredito = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovCentroCostoCredito");
    }

    public void contarRegistrosLovGrupoConcepto(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovGrupoConcepto = String.valueOf(filtrarListGruposConceptos.size());
        } else if (lovGruposConceptos != null) {
            infoRegistroLovGrupoConcepto = String.valueOf(lovGruposConceptos.size());
        } else {
            infoRegistroLovGrupoConcepto = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovGrupoConcepto");
    }

    public void contarRegistrosLovTipoTrabajador(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovTipoTrabajador = String.valueOf(filtrarListTiposTrabajadores.size());
        } else if (lovTiposTrabajadores != null) {
            infoRegistroLovTipoTrabajador = String.valueOf(lovTiposTrabajadores.size());
        } else {
            infoRegistroLovTipoTrabajador = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovTipoTrabajador");
    }

    public void contarRegistrosLovTipoContrato(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovTipoContrato = String.valueOf(filtrarListTiposContratos.size());
        } else if (lovTiposContratos != null) {
            infoRegistroLovTipoContrato = String.valueOf(lovTiposContratos.size());
        } else {
            infoRegistroLovTipoContrato = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovTipoContrato");
    }

    public void contarRegistrosLovReformaLaboral(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovReformaLaboral = String.valueOf(filtrarListReformasLaborales.size());
        } else if (lovReformasLaborales != null) {
            infoRegistroLovReformaLaboral = String.valueOf(lovReformasLaborales.size());
        } else {
            infoRegistroLovReformaLaboral = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovReformaLaboral");
    }

    public void contarRegistrosLovFormula(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovFormula = String.valueOf(filtrarListFormulas.size());
        } else if (lovFormulas != null) {
            infoRegistroLovFormula = String.valueOf(lovFormulas.size());
        } else {
            infoRegistroLovFormula = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovFormula");
    }

    public void contarRegistrosLovFormCon(int tipoListaLov) {
        if (tipoListaLov == 1) {
            infoRegistroLovFormulasConceptos = String.valueOf(filtrarListFormulasConceptos.size());
        } else if (lovFormulasConceptos != null) {
            infoRegistroLovFormulasConceptos = String.valueOf(lovFormulasConceptos.size());
        } else {
            infoRegistroLovFormulasConceptos = String.valueOf(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroLovFormulasConceptos");
    }

    //GET - SET 
    public boolean isFormulaSeleccionada() {
        return formulaSeleccionada;
    }

    public void setFormulaSeleccionada(boolean formulaSeleccionada) {
        this.formulaSeleccionada = formulaSeleccionada;
    }

    public VigenciasCuentas getVigenciaCuentaSeleccionada() {
        return vigenciaCuentaSeleccionada;
    }

    public void setVigenciaCuentaSeleccionada(VigenciasCuentas vigenciaCuentaTablaSeleccionada) {
        this.vigenciaCuentaSeleccionada = vigenciaCuentaTablaSeleccionada;
    }

    public VigenciasGruposConceptos getVigenciaGrupoCoSeleccionada() {
        return vigenciaGrupoCoSeleccionada;
    }

    public void setVigenciaGrupoCoSeleccionada(VigenciasGruposConceptos vigenciaGrupoConceptoTablaSeleccionada) {
        this.vigenciaGrupoCoSeleccionada = vigenciaGrupoConceptoTablaSeleccionada;
    }

    public VigenciasConceptosTT getVigenciaConceptoTTSeleccionada() {
        return vigenciaConceptoTTSeleccionada;
    }

    public void setVigenciaConceptoTTSeleccionada(VigenciasConceptosTT vigenciaConceptoTTSeleccionada) {
        this.vigenciaConceptoTTSeleccionada = vigenciaConceptoTTSeleccionada;
    }

    public VigenciasConceptosTC getVigenciaConceptoTCSeleccionada() {
        return vigenciaConceptoTCSeleccionada;
    }

    public void setVigenciaConceptoTCSeleccionada(VigenciasConceptosTC vigenciaConceptoTCSeleccionada) {
        this.vigenciaConceptoTCSeleccionada = vigenciaConceptoTCSeleccionada;
    }

    public VigenciasConceptosRL getVigenciaConceptoRLSeleccionada() {
        return vigenciaConceptoRLSeleccionada;
    }

    public void setVigenciaConceptoRLSeleccionada(VigenciasConceptosRL vigenciaConceptoRLTablaSeleccionada) {
        this.vigenciaConceptoRLSeleccionada = vigenciaConceptoRLTablaSeleccionada;
    }

    public FormulasConceptos getVigFormulaConceptoSeleccionada() {
        return vigFormulaConceptoSeleccionada;
    }

    public void setVigFormulaConceptoSeleccionada(FormulasConceptos formulaConceptoTablaSeleccionada) {
        this.vigFormulaConceptoSeleccionada = formulaConceptoTablaSeleccionada;
    }

    public FormulasConceptos getFormulaConceptoSeleccionadoLOV() {
        return formulaConceptoSeleccionadoLOV;
    }

    public void setFormulaConceptoSeleccionadoLOV(FormulasConceptos formulaConceptoSeleccionado) {
        this.formulaConceptoSeleccionadoLOV = formulaConceptoSeleccionado;
    }

    public Formulas getFormulaSeleccionadoLOV() {
        return formulaSeleccionadoLOV;
    }

    public void setFormulaSeleccionadoLOV(Formulas formulaSeleccionado) {
        this.formulaSeleccionadoLOV = formulaSeleccionado;
    }

    public ReformasLaborales getReformaLaboralSeleccionadoLOV() {
        return reformaLaboralSeleccionadoLOV;
    }

    public void setReformaLaboralSeleccionadoLOV(ReformasLaborales reformaLaboralSeleccionado) {
        this.reformaLaboralSeleccionadoLOV = reformaLaboralSeleccionado;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionadoLOV() {
        return tipoTrabajadorSeleccionadoLOV;
    }

    public void setTipoTrabajadorSeleccionadoLOV(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionadoLOV = tipoTrabajadorSeleccionado;
    }

    public TiposContratos getTipoContratoSeleccionadoLOV() {
        return tipoContratoSeleccionadoLOV;
    }

    public void setTipoContratoSeleccionadoLOV(TiposContratos tipoContratoSeleccionado) {
        this.tipoContratoSeleccionadoLOV = tipoContratoSeleccionado;
    }

    public GruposConceptos getGrupoConceptoSeleccionadoLOV() {
        return grupoConceptoSeleccionadoLOV;
    }

    public void setGrupoConceptoSeleccionadoLOV(GruposConceptos grupoConceptoSeleccionado) {
        this.grupoConceptoSeleccionadoLOV = grupoConceptoSeleccionado;
    }

    public CentrosCostos getCentroCostoSeleccionadoLOV() {
        return centroCostoSeleccionadoLOV;
    }

    public void setCentroCostoSeleccionadoLOV(CentrosCostos centroCostoSeleccionado) {
        this.centroCostoSeleccionadoLOV = centroCostoSeleccionado;
    }

    public TiposCentrosCostos getTipoCentroCostoSeleccionadoLOV() {
        return tipoCentroCostoSeleccionadoLOV;
    }

    public void setTipoCentroCostoSeleccionadoLOV(TiposCentrosCostos tipoCentroCostoSeleccionado) {
        this.tipoCentroCostoSeleccionadoLOV = tipoCentroCostoSeleccionado;
    }

    public Cuentas getCuentaSeleccionadaLOV() {
        return cuentaSeleccionadaLOV;
    }

    public void setCuentaSeleccionadaLOV(Cuentas cuentaSeleccionada) {
        this.cuentaSeleccionadaLOV = cuentaSeleccionada;
    }

    public List<VigenciasCuentas> getListVigenciasCuentasConcepto() {
        try {
            if (listVigenciasCuentasConcepto == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listVigenciasCuentasConcepto = administrarDetalleConcepto.consultarListaVigenciasCuentasConcepto(conceptoActual.getSecuencia());
                }
            }
            return listVigenciasCuentasConcepto;
        } catch (Exception e) {
            System.out.println("Error getListConceptosJuridicos " + e.toString());
            return null;
        }
    }

    public void setListVigenciasCuentasConcepto(List<VigenciasCuentas> t) {
        this.listVigenciasCuentasConcepto = t;
    }

    public List<VigenciasCuentas> getFiltrarListVigenciasCuentasConcepto() {
        return filtrarListVigenciasCuentasConcepto;
    }

    public void setFiltrarListVigenciasCuentasConcepto(List<VigenciasCuentas> t) {
        this.filtrarListVigenciasCuentasConcepto = t;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<VigenciasCuentas> getListVigenciasCuentasModificar() {
        return listVigenciasCuentasModificar;
    }

    public void setListVigenciasCuentasModificar(List<VigenciasCuentas> setListVigenciasCuentasModificar) {
        this.listVigenciasCuentasModificar = setListVigenciasCuentasModificar;
    }

    public VigenciasCuentas getNuevaVigenciaCuenta() {
        return nuevaVigenciaCuenta;
    }

    public void setNuevaVigenciaCuenta(VigenciasCuentas setNuevaVigenciaCuenta) {
        this.nuevaVigenciaCuenta = setNuevaVigenciaCuenta;
    }

    public List<VigenciasCuentas> getListConceptosJuridicosCrear() {
        return listVigenciasCuentasCrear;
    }

    public void setListConceptosJuridicosCrear(List<VigenciasCuentas> setListConceptosJuridicosCrear) {
        this.listVigenciasCuentasCrear = setListConceptosJuridicosCrear;
    }

    public List<VigenciasCuentas> getListVigenciasCuentasBorrar() {
        return listVigenciasCuentasBorrar;
    }

    public void setListVigenciasCuentasBorrar(List<VigenciasCuentas> setListVigenciasCuentasBorrar) {
        this.listVigenciasCuentasBorrar = setListVigenciasCuentasBorrar;
    }

    public VigenciasCuentas getEditarVigenciaCuenta() {
        return editarVigenciaCuenta;
    }

    public void setEditarVigenciaCuenta(VigenciasCuentas setEditarVigenciaCuenta) {
        this.editarVigenciaCuenta = setEditarVigenciaCuenta;
    }

    public VigenciasCuentas getDuplicarVigenciaCuenta() {
        return duplicarVigenciaCuenta;
    }

    public void setDuplicarVigenciaCuenta(VigenciasCuentas setDuplicarVigenciaCuenta) {
        this.duplicarVigenciaCuenta = setDuplicarVigenciaCuenta;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public Conceptos getConceptoActual() {
        return conceptoActual;
    }

    public void setConceptoActual(Conceptos setConceptoActual) {
        this.conceptoActual = setConceptoActual;
    }

    public List<VigenciasCuentas> getListVigenciasCuentasCrear() {
        return listVigenciasCuentasCrear;
    }

    public void setListVigenciasCuentasCrear(List<VigenciasCuentas> listVigenciasCuentasCrear) {
        this.listVigenciasCuentasCrear = listVigenciasCuentasCrear;
    }

    public List<TiposCentrosCostos> getFiltrarListTiposCentrosCostos() {
        return filtrarListTiposCentrosCostos;
    }

    public void setFiltrarListTiposCentrosCostos(List<TiposCentrosCostos> filtrarListTiposCentrosCostos) {
        this.filtrarListTiposCentrosCostos = filtrarListTiposCentrosCostos;
    }

    public List<Cuentas> getFiltrarListCuentas() {
        return filtrarListCuentas;
    }

    public void setFiltrarListCuentas(List<Cuentas> filtrarListCuentas) {
        this.filtrarListCuentas = filtrarListCuentas;
    }

    public List<CentrosCostos> getFiltrarListCentrosCostos() {
        return filtrarListCentrosCostos;
    }

    public void setFiltrarListCentrosCostos(List<CentrosCostos> filtrarListCentrosCostos) {
        this.filtrarListCentrosCostos = filtrarListCentrosCostos;
    }

    public String getComportamientoConcepto() {
        return comportamientoConcepto;
    }

    public void setComportamientoConcepto(String comportamientoConcepto) {
        this.comportamientoConcepto = comportamientoConcepto;
    }

    public List<VigenciasGruposConceptos> getListVigenciasGruposConceptos() {
        try {
            if (listVigenciasGruposConceptos == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listVigenciasGruposConceptos = administrarDetalleConcepto.consultarListaVigenciasGruposConceptosConcepto(conceptoActual.getSecuencia());
                }
            }
            return listVigenciasGruposConceptos;
        } catch (Exception e) {
            System.out.println("Error getListVigenciasGruposConceptosConcepto : " + e.toString());
            return null;
        }

    }

    public void setListVigenciasGruposConceptos(List<VigenciasGruposConceptos> listVigenciasGruposConceptosConcepto) {
        this.listVigenciasGruposConceptos = listVigenciasGruposConceptosConcepto;
    }

    public List<VigenciasGruposConceptos> getFiltrarListVigenciasGruposConceptos() {
        return filtrarListVigenciasGruposConceptos;
    }

    public void setFiltrarListVigenciasGruposConceptos(List<VigenciasGruposConceptos> filtrarVigenciasGruposConceptosConcepto) {
        this.filtrarListVigenciasGruposConceptos = filtrarVigenciasGruposConceptosConcepto;
    }

    public List<VigenciasGruposConceptos> getListVigenciasGruposConceptosModificar() {
        return listVigenciasGruposConceptosModificar;
    }

    public void setListVigenciasGruposConceptosModificar(List<VigenciasGruposConceptos> listVigenciasGruposConceptosModificar) {
        this.listVigenciasGruposConceptosModificar = listVigenciasGruposConceptosModificar;
    }

    public VigenciasGruposConceptos getNuevaVigenciaGrupoConcepto() {
        return nuevaVigenciaGrupoConcepto;
    }

    public void setNuevaVigenciaGrupoConcepto(VigenciasGruposConceptos nuevaVigenciaGrupoConcepto) {
        this.nuevaVigenciaGrupoConcepto = nuevaVigenciaGrupoConcepto;
    }

    public List<VigenciasGruposConceptos> getListVigenciasGruposConceptosCrear() {
        return listVigenciasGruposConceptosCrear;
    }

    public void setListVigenciasGruposConceptosCrear(List<VigenciasGruposConceptos> listVigenciasGruposConceptosCrear) {
        this.listVigenciasGruposConceptosCrear = listVigenciasGruposConceptosCrear;
    }

    public List<VigenciasGruposConceptos> getListVigenciasGruposConceptosBorrar() {
        return listVigenciasGruposConceptosBorrar;
    }

    public void setListVigenciasGruposConceptosBorrar(List<VigenciasGruposConceptos> listVigenciasGruposConceptosBorrar) {
        this.listVigenciasGruposConceptosBorrar = listVigenciasGruposConceptosBorrar;
    }

    public VigenciasGruposConceptos getEditarVigenciaGrupoConcepto() {
        return editarVigenciaGrupoConcepto;
    }

    public void setEditarVigenciaGrupoConcepto(VigenciasGruposConceptos editarVigenciaGrupoConcepto) {
        this.editarVigenciaGrupoConcepto = editarVigenciaGrupoConcepto;
    }

    public VigenciasGruposConceptos getDuplicarVigenciaGrupoConcepto() {
        return duplicarVigenciaGrupoConcepto;
    }

    public void setDuplicarVigenciaGrupoConcepto(VigenciasGruposConceptos duplicarVigenciaGrupoConcepto) {
        this.duplicarVigenciaGrupoConcepto = duplicarVigenciaGrupoConcepto;
    }

    public List<GruposConceptos> getFiltrarListGruposConceptos() {
        return filtrarListGruposConceptos;
    }

    public void setFiltrarListGruposConceptos(List<GruposConceptos> filtrarListGruposConceptos) {
        this.filtrarListGruposConceptos = filtrarListGruposConceptos;
    }

    public List<VigenciasConceptosTT> getListVigenciasConceptosTTConcepto() {
        try {
            if (listVigenciasConceptosTTConcepto == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listVigenciasConceptosTTConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosTTConcepto(conceptoActual.getSecuencia());
                }
            }
            return listVigenciasConceptosTTConcepto;
        } catch (Exception e) {
            System.out.println("Error getListVigenciasConceptosTTConcepto : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasConceptosTTConcepto(List<VigenciasConceptosTT> listVigenciasConceptosTTConcepto) {
        this.listVigenciasConceptosTTConcepto = listVigenciasConceptosTTConcepto;
    }

    public List<VigenciasConceptosTT> getFiltrarListVigenciasConceptosTT() {
        return filtrarListVigenciasConceptosTT;
    }

    public void setFiltrarListVigenciasConceptosTT(List<VigenciasConceptosTT> filtrarListVigenciasConceptosTT) {
        this.filtrarListVigenciasConceptosTT = filtrarListVigenciasConceptosTT;
    }

    public List<VigenciasConceptosTC> getListVigenciasConceptosTCConcepto() {
        try {
            if (listVigenciasConceptosTCConcepto == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listVigenciasConceptosTCConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosTCConcepto(conceptoActual.getSecuencia());
                }
            }
            return listVigenciasConceptosTCConcepto;
        } catch (Exception e) {
            System.out.println("Error getListVigenciasConceptosTCConcepto : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasConceptosTCConcepto(List<VigenciasConceptosTC> listVigenciasConceptosTCConcepto) {
        this.listVigenciasConceptosTCConcepto = listVigenciasConceptosTCConcepto;
    }

    public List<VigenciasConceptosTC> getFiltrarListVigenciasConceptosTC() {
        return filtrarListVigenciasConceptosTC;
    }

    public void setFiltrarListVigenciasConceptosTC(List<VigenciasConceptosTC> filtrarListVigenciasConceptosTC) {
        this.filtrarListVigenciasConceptosTC = filtrarListVigenciasConceptosTC;
    }

    public List<VigenciasConceptosRL> getListVigenciasConceptosRLConcepto() {
        try {

            if (listVigenciasConceptosRLConcepto == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listVigenciasConceptosRLConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosRLCConcepto(conceptoActual.getSecuencia());
                }
            }
            return listVigenciasConceptosRLConcepto;
        } catch (Exception e) {
            return null;
        }
    }

    public void setListVigenciasConceptosRLConcepto(List<VigenciasConceptosRL> listVigenciasConceptosRLConcepto) {
        this.listVigenciasConceptosRLConcepto = listVigenciasConceptosRLConcepto;
    }

    public List<VigenciasConceptosRL> getFiltrarListVigenciasConceptosRL() {
        return filtrarListVigenciasConceptosRL;
    }

    public void setFiltrarListVigenciasConceptosRL(List<VigenciasConceptosRL> filtrarListVigenciasConceptosRL) {
        this.filtrarListVigenciasConceptosRL = filtrarListVigenciasConceptosRL;
    }

    public List<FormulasConceptos> getListFormulasConceptos() {
        try {
            if (listFormulasConceptos == null) {
                if (conceptoActual.getSecuencia() != null) {
                    listFormulasConceptos = administrarDetalleConcepto.consultarListaFormulasConceptosConcepto(conceptoActual.getSecuencia());
                }
            }
            return listFormulasConceptos;
        } catch (Exception e) {
            return null;
        }
    }

    public void setListFormulasConceptos(List<FormulasConceptos> listFormulasConceptosConcepto) {
        this.listFormulasConceptos = listFormulasConceptosConcepto;
    }

    public List<FormulasConceptos> getFiltrarListFormulasConceptosConcepto() {
        return filtrarListFormulasConceptosConcepto;
    }

    public void setFiltrarListFormulasConceptosConcepto(List<FormulasConceptos> filtrarListFormulasConceptos) {
        this.filtrarListFormulasConceptosConcepto = filtrarListFormulasConceptos;
    }

    public List<VigenciasConceptosTT> getListVigenciasConceptosTTModificar() {
        return listVigenciasConceptosTTModificar;
    }

    public void setListVigenciasConceptosTTModificar(List<VigenciasConceptosTT> listVigenciasConceptosTTModificar) {
        this.listVigenciasConceptosTTModificar = listVigenciasConceptosTTModificar;
    }

    public VigenciasConceptosTT getNuevaVigenciaConceptoTT() {
        return nuevaVigenciaConceptoTT;
    }

    public void setNuevaVigenciaConceptoTT(VigenciasConceptosTT nuevaVigenciaConceptoTT) {
        this.nuevaVigenciaConceptoTT = nuevaVigenciaConceptoTT;
    }

    public List<VigenciasConceptosTT> getListVigenciasConceptosTTCrear() {
        return listVigenciasConceptosTTCrear;
    }

    public void setListVigenciasConceptosTTCrear(List<VigenciasConceptosTT> listVigenciasConceptosTTCrear) {
        this.listVigenciasConceptosTTCrear = listVigenciasConceptosTTCrear;
    }

    public List<VigenciasConceptosTT> getListVigenciasConceptosTTBorrar() {
        return listVigenciasConceptosTTBorrar;
    }

    public void setListVigenciasConceptosTTBorrar(List<VigenciasConceptosTT> listVigenciasConceptosTTBorrar) {
        this.listVigenciasConceptosTTBorrar = listVigenciasConceptosTTBorrar;
    }

    public VigenciasConceptosTT getEditarVigenciaConceptoTT() {
        return editarVigenciaConceptoTT;
    }

    public void setEditarVigenciaConceptoTT(VigenciasConceptosTT editarVigenciaConceptoTT) {
        this.editarVigenciaConceptoTT = editarVigenciaConceptoTT;
    }

    public VigenciasConceptosTT getDuplicarVigenciaConceptoTT() {
        return duplicarVigenciaConceptoTT;
    }

    public void setDuplicarVigenciaConceptoTT(VigenciasConceptosTT duplicarVigenciaConceptoTT) {
        this.duplicarVigenciaConceptoTT = duplicarVigenciaConceptoTT;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }

    public List<VigenciasConceptosTC> getListVigenciasConceptosTCModificar() {
        return listVigenciasConceptosTCModificar;
    }

    public void setListVigenciasConceptosTCModificar(List<VigenciasConceptosTC> listVigenciasConceptosTCModificar) {
        this.listVigenciasConceptosTCModificar = listVigenciasConceptosTCModificar;
    }

    public VigenciasConceptosTC getNuevaVigenciaConceptoTC() {
        return nuevaVigenciaConceptoTC;
    }

    public void setNuevaVigenciaConceptoTC(VigenciasConceptosTC nuevaVigenciaConceptoTC) {
        this.nuevaVigenciaConceptoTC = nuevaVigenciaConceptoTC;
    }

    public List<VigenciasConceptosTC> getListVigenciasConceptosTCCrear() {
        return listVigenciasConceptosTCCrear;
    }

    public void setListVigenciasConceptosTCCrear(List<VigenciasConceptosTC> listVigenciasConceptosTCCrear) {
        this.listVigenciasConceptosTCCrear = listVigenciasConceptosTCCrear;
    }

    public List<VigenciasConceptosTC> getListVigenciasConceptosTCBorrar() {
        return listVigenciasConceptosTCBorrar;
    }

    public void setListVigenciasConceptosTCBorrar(List<VigenciasConceptosTC> listVigenciasConceptosTCBorrar) {
        this.listVigenciasConceptosTCBorrar = listVigenciasConceptosTCBorrar;
    }

    public VigenciasConceptosTC getEditarVigenciaConceptoTC() {
        return editarVigenciaConceptoTC;
    }

    public void setEditarVigenciaConceptoTC(VigenciasConceptosTC editarVigenciaConceptoTC) {
        this.editarVigenciaConceptoTC = editarVigenciaConceptoTC;
    }

    public VigenciasConceptosTC getDuplicarVigenciaConceptoTC() {
        return duplicarVigenciaConceptoTC;
    }

    public void setDuplicarVigenciaConceptoTC(VigenciasConceptosTC duplicarVigenciaConceptoTC) {
        this.duplicarVigenciaConceptoTC = duplicarVigenciaConceptoTC;
    }

    public List<TiposContratos> getFiltrarListTiposContratos() {
        return filtrarListTiposContratos;
    }

    public void setFiltrarListTiposContratos(List<TiposContratos> filtrarListTiposContratos) {
        this.filtrarListTiposContratos = filtrarListTiposContratos;
    }

    public List<VigenciasConceptosRL> getListVigenciasConceptosRLModificar() {
        return listVigenciasConceptosRLModificar;
    }

    public void setListVigenciasConceptosRLModificar(List<VigenciasConceptosRL> listVigenciasConceptosRLModificar) {
        this.listVigenciasConceptosRLModificar = listVigenciasConceptosRLModificar;
    }

    public VigenciasConceptosRL getNuevaVigenciaConceptoRL() {
        return nuevaVigenciaConceptoRL;
    }

    public void setNuevaVigenciaConceptoRL(VigenciasConceptosRL nuevaVigenciaConceptoRL) {
        this.nuevaVigenciaConceptoRL = nuevaVigenciaConceptoRL;
    }

    public List<VigenciasConceptosRL> getListVigenciasConceptosRLCrear() {
        return listVigenciasConceptosRLCrear;
    }

    public void setListVigenciasConceptosRLCrear(List<VigenciasConceptosRL> listVigenciasConceptosRLCrear) {
        this.listVigenciasConceptosRLCrear = listVigenciasConceptosRLCrear;
    }

    public List<VigenciasConceptosRL> getListVigenciasConceptosRLBorrar() {
        return listVigenciasConceptosRLBorrar;
    }

    public void setListVigenciasConceptosRLBorrar(List<VigenciasConceptosRL> listVigenciasConceptosRLBorrar) {
        this.listVigenciasConceptosRLBorrar = listVigenciasConceptosRLBorrar;
    }

    public VigenciasConceptosRL getEditarVigenciaConceptoRL() {
        return editarVigenciaConceptoRL;
    }

    public void setEditarVigenciaConceptoRL(VigenciasConceptosRL editarVigenciaConceptoRL) {
        this.editarVigenciaConceptoRL = editarVigenciaConceptoRL;
    }

    public VigenciasConceptosRL getDuplicarVigenciaConceptoRL() {
        return duplicarVigenciaConceptoRL;
    }

    public void setDuplicarVigenciaConceptoRL(VigenciasConceptosRL duplicarVigenciaConceptoRL) {
        this.duplicarVigenciaConceptoRL = duplicarVigenciaConceptoRL;
    }

    public List<ReformasLaborales> getFiltrarListReformasLaborales() {
        return filtrarListReformasLaborales;
    }

    public void setFiltrarListReformasLaborales(List<ReformasLaborales> filtrarListReformasLaborales) {
        this.filtrarListReformasLaborales = filtrarListReformasLaborales;
    }

    public List<FormulasConceptos> getListFormulasConceptosModificar() {
        return listFormulasConceptosModificar;
    }

    public void setListFormulasConceptosModificar(List<FormulasConceptos> listFormulasConceptosModificar) {
        this.listFormulasConceptosModificar = listFormulasConceptosModificar;
    }

    public FormulasConceptos getNuevaFormulasConceptos() {
        return nuevaFormulasConceptos;
    }

    public void setNuevaFormulasConceptos(FormulasConceptos nuevaFormulasConceptos) {
        this.nuevaFormulasConceptos = nuevaFormulasConceptos;
    }

    public List<FormulasConceptos> getListFormulasConceptosCrear() {
        return listFormulasConceptosCrear;
    }

    public void setListFormulasConceptosCrear(List<FormulasConceptos> listFormulasConceptosCrear) {
        this.listFormulasConceptosCrear = listFormulasConceptosCrear;
    }

    public List<FormulasConceptos> getListFormulasConceptosBorrar() {
        return listFormulasConceptosBorrar;
    }

    public void setListFormulasConceptosBorrar(List<FormulasConceptos> listFormulasConceptosBorrar) {
        this.listFormulasConceptosBorrar = listFormulasConceptosBorrar;
    }

    public FormulasConceptos getEditarFormulasConceptos() {
        return editarFormulasConceptos;
    }

    public void setEditarFormulasConceptos(FormulasConceptos editarFormulasConceptos) {
        this.editarFormulasConceptos = editarFormulasConceptos;
    }

    public FormulasConceptos getDuplicarFormulasConceptos() {
        return duplicarFormulasConceptos;
    }

    public void setDuplicarFormulasConceptos(FormulasConceptos duplicarFormulasConceptos) {
        this.duplicarFormulasConceptos = duplicarFormulasConceptos;
    }

    public List<Formulas> getFiltrarListFormulas() {
        return filtrarListFormulas;
    }

    public void setFiltrarListFormulas(List<Formulas> filtrarListFormulas) {
        this.filtrarListFormulas = filtrarListFormulas;
    }

    public List<FormulasConceptos> getFiltrarListFormulasConceptos() {
        return filtrarListFormulasConceptos;
    }

    public void setFiltrarListFormulasConceptos(List<FormulasConceptos> filtrarListFormulasConceptos) {
        this.filtrarListFormulasConceptos = filtrarListFormulasConceptos;
    }

    public String getPaginaRetorno() {
        return paginaRetorno;
    }

    public void setPaginaRetorno(String paginaRetorno) {
        this.paginaRetorno = paginaRetorno;
    }

    public FormulasConceptos getActualFormulaConcepto() {
        return actualFormulaConcepto;
    }

    public void setActualFormulaConcepto(FormulasConceptos setActualFormulaConcepto) {
        this.actualFormulaConcepto = setActualFormulaConcepto;
    }

    public String getAltoTablaVigenciaCuenta() {
        return altoTablaVigenciaCuenta;
    }

    public void setAltoTablaVigenciaCuenta(String altoTablaVigenciaCuenta) {
        this.altoTablaVigenciaCuenta = altoTablaVigenciaCuenta;
    }

    public String getAltoTablaVigenciaGrupoC() {
        return altoTablaVigenciaGrupoC;
    }

    public void setAltoTablaVigenciaGrupoC(String altoTablaVigenciaGrupoC) {
        this.altoTablaVigenciaGrupoC = altoTablaVigenciaGrupoC;
    }

    public String getAltoTablaVigenciaConceptoTT() {
        return altoTablaVigenciaConceptoTT;
    }

    public void setAltoTablaVigenciaConceptoTT(String altoTablaVigenciaConceptoTT) {
        this.altoTablaVigenciaConceptoTT = altoTablaVigenciaConceptoTT;
    }

    public String getAltoTablaVigenciaConceptoTC() {
        return altoTablaVigenciaConceptoTC;
    }

    public void setAltoTablaVigenciaConceptoTC(String altoTablaVigenciaConceptoTC) {
        this.altoTablaVigenciaConceptoTC = altoTablaVigenciaConceptoTC;
    }

    public String getAltoTablaVigenciaConceptoRL() {
        return altoTablaVigenciaConceptoRL;
    }

    public void setAltoTablaVigenciaConceptoRL(String altoTablaVigenciaConceptoRL) {
        this.altoTablaVigenciaConceptoRL = altoTablaVigenciaConceptoRL;
    }

    public String getAltoTablaFormulaConcepto() {
        return altoTablaFormulaConcepto;
    }

    public void setAltoTablaFormulaConcepto(String altoTablaFormulaConcepto) {
        this.altoTablaFormulaConcepto = altoTablaFormulaConcepto;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<TiposCentrosCostos> getLovTiposCentrosCostos() {
        return lovTiposCentrosCostos;
    }

    public void setLovTiposCentrosCostos(List<TiposCentrosCostos> lovTiposCentrosCostos) {
        this.lovTiposCentrosCostos = lovTiposCentrosCostos;
    }

    public List<Cuentas> getLovCuentas() {
        return lovCuentas;
    }

    public void setLovCuentas(List<Cuentas> lovCuentas) {
        this.lovCuentas = lovCuentas;
    }

    public List<CentrosCostos> getLovCentrosCostos() {
        return lovCentrosCostos;
    }

    public void setLovCentrosCostos(List<CentrosCostos> lovCentrosCostos) {
        this.lovCentrosCostos = lovCentrosCostos;
    }

    public List<GruposConceptos> getLovGruposConceptos() {
        return lovGruposConceptos;
    }

    public void setLovGruposConceptos(List<GruposConceptos> lovGruposConceptos) {
        this.lovGruposConceptos = lovGruposConceptos;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        return lovTiposTrabajadores;
    }

    public void setLovTiposTrabajadores(List<TiposTrabajadores> lovTiposTrabajadores) {
        this.lovTiposTrabajadores = lovTiposTrabajadores;
    }

    public List<TiposContratos> getLovTiposContratos() {
        return lovTiposContratos;
    }

    public void setLovTiposContratos(List<TiposContratos> lovTiposContratos) {
        this.lovTiposContratos = lovTiposContratos;
    }

    public List<ReformasLaborales> getLovReformasLaborales() {
        return lovReformasLaborales;
    }

    public void setLovReformasLaborales(List<ReformasLaborales> lovReformasLaborales) {
        this.lovReformasLaborales = lovReformasLaborales;
    }

    public List<Formulas> getLovFormulas() {
        return lovFormulas;
    }

    public void setLovFormulas(List<Formulas> lovFormulas) {
        this.lovFormulas = lovFormulas;
    }

    public List<FormulasConceptos> getLovFormulasConceptos() {
        return lovFormulasConceptos;
    }

    public void setLovFormulasConceptos(List<FormulasConceptos> lovFormulasConceptos) {
        this.lovFormulasConceptos = lovFormulasConceptos;
    }

    public String getInfoRegistroLovTipoCentroCosto() {
        return infoRegistroLovTipoCentroCosto;
    }

    public void setInfoRegistroLovTipoCentroCosto(String infoRegistroLovTipoCentroCosto) {
        this.infoRegistroLovTipoCentroCosto = infoRegistroLovTipoCentroCosto;
    }

    public String getInfoRegistroLovCuentaDebito() {
        return infoRegistroLovCuentaDebito;
    }

    public void setInfoRegistroLovCuentaDebito(String infoRegistroLovCuentaDebito) {
        this.infoRegistroLovCuentaDebito = infoRegistroLovCuentaDebito;
    }

    public String getInfoRegistroLovCuentaCredito() {
        return infoRegistroLovCuentaCredito;
    }

    public void setInfoRegistroLovCuentaCredito(String infoRegistroLovCuentaCredito) {
        this.infoRegistroLovCuentaCredito = infoRegistroLovCuentaCredito;
    }

    public String getInfoRegistroLovCentroCostoDebito() {
        return infoRegistroLovCentroCostoDebito;
    }

    public void setInfoRegistroLovCentroCostoDebito(String infoRegistroLovCentroCostoDebito) {
        this.infoRegistroLovCentroCostoDebito = infoRegistroLovCentroCostoDebito;
    }

    public String getInfoRegistroLovCentroCostoCredito() {
        return infoRegistroLovCentroCostoCredito;
    }

    public void setInfoRegistroLovCentroCostoCredito(String infoRegistroLovCentroCostoCredito) {
        this.infoRegistroLovCentroCostoCredito = infoRegistroLovCentroCostoCredito;
    }

    public String getInfoRegistroLovGrupoConcepto() {
        return infoRegistroLovGrupoConcepto;
    }

    public void setInfoRegistroLovGrupoConcepto(String infoRegistroLovGrupoConcepto) {
        this.infoRegistroLovGrupoConcepto = infoRegistroLovGrupoConcepto;
    }

    public String getInfoRegistroLovTipoTrabajador() {
        return infoRegistroLovTipoTrabajador;
    }

    public void setInfoRegistroLovTipoTrabajador(String infoRegistroLovTipoTrabajador) {
        this.infoRegistroLovTipoTrabajador = infoRegistroLovTipoTrabajador;
    }

    public String getInfoRegistroLovTipoContrato() {
        return infoRegistroLovTipoContrato;
    }

    public void setInfoRegistroLovTipoContrato(String infoRegistroLovTipoContrato) {
        this.infoRegistroLovTipoContrato = infoRegistroLovTipoContrato;
    }

    public String getInfoRegistroLovReformaLaboral() {
        return infoRegistroLovReformaLaboral;
    }

    public void setInfoRegistroLovReformaLaboral(String infoRegistroLovReformaLaboral) {
        this.infoRegistroLovReformaLaboral = infoRegistroLovReformaLaboral;
    }

    public String getInfoRegistroLovFormula() {
        return infoRegistroLovFormula;
    }

    public void setInfoRegistroLovFormula(String infoRegistroLovFormula) {
        this.infoRegistroLovFormula = infoRegistroLovFormula;
    }

    public String getInfoRegistroLovFormulasConceptos() {
        return infoRegistroLovFormulasConceptos;
    }

    public void setInfoRegistroLovFormulasConceptos(String infoRegistroLovOrden) {
        this.infoRegistroLovFormulasConceptos = infoRegistroLovOrden;
    }

    public String getInfoRegistroCuenta() {
        return infoRegistroCuenta;
    }

    public void setInfoRegistroCuenta(String infoRegistroCuenta) {
        this.infoRegistroCuenta = infoRegistroCuenta;
    }

    public String getInfoRegistroGrupoC() {
        return infoRegistroGrupoC;
    }

    public void setInfoRegistroGrupoC(String infoRegistroGrupoC) {
        this.infoRegistroGrupoC = infoRegistroGrupoC;
    }

    public String getInfoRegistroConceptoTT() {
        return infoRegistroConceptoTT;
    }

    public void setInfoRegistroConceptoTT(String infoRegistroConceptoTT) {
        this.infoRegistroConceptoTT = infoRegistroConceptoTT;
    }

    public String getInfoRegistroConceptoTC() {
        return infoRegistroConceptoTC;
    }

    public void setInfoRegistroConceptoTC(String infoRegistroConceptoTC) {
        this.infoRegistroConceptoTC = infoRegistroConceptoTC;
    }

    public String getInfoRegistroConceptoRL() {
        return infoRegistroConceptoRL;
    }

    public void setInfoRegistroConceptoRL(String infoRegistroConceptoRL) {
        this.infoRegistroConceptoRL = infoRegistroConceptoRL;
    }

    public String getInfoRegistroFormulaConcepto() {
        return infoRegistroFormulaConcepto;
    }

    public void setInfoRegistroFormulaConcepto(String infoRegistroFormulaConcepto) {
        this.infoRegistroFormulaConcepto = infoRegistroFormulaConcepto;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }

}

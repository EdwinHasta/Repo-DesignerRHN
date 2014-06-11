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
    
    
    //////////////Conceptos//////////////////
    private Conceptos conceptoActual;
    ///////////VigenciasCuentas////////////
    private List<VigenciasCuentas> listVigenciasCuentasConcepto;
    private List<VigenciasCuentas> filtrarListVigenciasCuentasConcepto;
    ///////////VigenciasCuentas/////////////
    private int banderaVigenciaCuenta;
    private int indexVigenciaCuenta, indexAuxVigenciaCuenta;
    private List<VigenciasCuentas> listVigenciasCuentasModificar;
    private VigenciasCuentas nuevaVigenciaCuenta;
    private List<VigenciasCuentas> listVigenciasCuentasCrear;
    private List<VigenciasCuentas> listVigenciasCuentasBorrar;
    private VigenciasCuentas editarVigenciaCuenta;
    private int cualCeldaVigenciaCuenta, tipoListaVigenciaCuenta;
    private VigenciasCuentas duplicarVigenciaCuenta;
    private boolean cambiosVigenciaCuenta;
    private BigInteger secRegistroVigenciaCuenta;
    private BigInteger backUpSecRegistroVigenciaCuenta;
    private String auxVC_TipoCC, auxVC_Debito, auxVC_DescDeb, auxVC_Credito, auxVC_DescCre, auxVC_ConsDeb, auxVC_ConsCre;
    private Date auxVC_FechaIni, auxVC_FechaFin;
    private Column vigenciaCuentaFechaInicial, vigenciaCuentaFechaFinal, vigenciaCuentaTipoCC, vigenciaCuentaDebitoCod, vigenciaCuentaDebitoDes, vigenciaCuentaCCConsolidadorD, vigenciaCuentaCreditoCod, vigenciaCuentaCreditoDes, vigenciaCuentaCCConsolidadorC;
    private boolean permitirIndexVigenciaCuenta;
    ////////////Listas Valores VigenciasCuentas/////////////
    private List<TiposCentrosCostos> listTiposCentrosCostos;
    private List<TiposCentrosCostos> filtrarListTiposCentrosCostos;
    private TiposCentrosCostos tipoCentroCostoSeleccionado;
    private List<Cuentas> listCuentas;
    private List<Cuentas> filtrarListCuentas;
    private Cuentas cuentaSeleccionada;
    private List<CentrosCostos> listCentrosCostos;
    private List<CentrosCostos> filtrarListCentrosCostos;
    private CentrosCostos centroCostoSeleccionado;
    ///////////////////VigenciasGruposConceptos///////////////////////
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosConcepto;
    private List<VigenciasGruposConceptos> filtrarListVigenciasGruposConceptosConcepto;
    ///////////////////VigenciasGruposConceptos///////////////////////
    private int banderaVigenciaGrupoConcepto;
    private int indexVigenciaGrupoConcepto, indexAuxVigenciaGrupoConcepto;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosModificar;
    private VigenciasGruposConceptos nuevaVigenciaGrupoConcepto;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosCrear;
    private List<VigenciasGruposConceptos> listVigenciasGruposConceptosBorrar;
    private VigenciasGruposConceptos editarVigenciaGrupoConcepto;
    private int cualCeldaVigenciaGrupoConcepto, tipoListaVigenciaGrupoConcepto;
    private VigenciasGruposConceptos duplicarVigenciaGrupoConcepto;
    private boolean cambiosVigenciaGrupoConcepto;
    private BigInteger secRegistroVigenciaGrupoConcepto;
    private BigInteger backUpSecRegistroVigenciaGrupoConcepto;
    private String auxVGC_Descripcion, auxVGC_Codigo;
    private Date auxVGC_FechaIni, auxVGC_FechaFin;
    private Column vigenciaGCFechaInicial, vigenciaGCFechaFinal, vigenciaGCCodigo, vigenciaGCDescripcion;
    private boolean permitirIndexVigenciaGrupoConcepto;
    /////////////Lista Valores VigenciaGrupoConcepto///////////////////////
    private List<GruposConceptos> listGruposConceptos;
    private List<GruposConceptos> filtrarListGruposConceptos;
    private GruposConceptos grupoConceptoSeleccionado;
    ///////////////////VigenciasConceptosTT///////////////////////////////
    private List<VigenciasConceptosTT> listVigenciasConceptosTTConcepto;
    private List<VigenciasConceptosTT> filtrarListVigenciasConceptosTT;
    ///////////////////VigenciasConceptosTT///////////////////////
    private int banderaVigenciaConceptoTT;
    private int indexVigenciaConceptoTT, indexAuxVigenciaConceptoTT;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTModificar;
    private VigenciasConceptosTT nuevaVigenciaConceptoTT;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTCrear;
    private List<VigenciasConceptosTT> listVigenciasConceptosTTBorrar;
    private VigenciasConceptosTT editarVigenciaConceptoTT;
    private int cualCeldaVigenciaConceptoTT, tipoListaVigenciaConceptoTT;
    private VigenciasConceptosTT duplicarVigenciaConceptoTT;
    private boolean cambiosVigenciaConceptoTT;
    private BigInteger secRegistroVigenciaConceptoTT;
    private BigInteger backUpSecRegistroVigenciaConceptoTT;
    private String auxVCTT_Descripcion;
    private Date auxVCTT_FechaIni, auxVCTT_FechaFin;
    private Column vigenciaCTTFechaInicial, vigenciaCTTFechaFinal, vigenciaCTTDescripcion;
    private boolean permitirIndexVigenciaConceptoTT;
    /////////////Lista Valores VigenciasConceptosTT///////////////////////
    private List<TiposTrabajadores> listTiposTrabajadores;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    ///////////////////VigenciasConceptosTC///////////////////////////////
    private List<VigenciasConceptosTC> listVigenciasConceptosTCConcepto;
    private List<VigenciasConceptosTC> filtrarListVigenciasConceptosTC;
    ///////////////////VigenciasConceptosTC///////////////////////
    private int banderaVigenciaConceptoTC;
    private int indexVigenciaConceptoTC, indexAuxVigenciaConceptoTC;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCModificar;
    private VigenciasConceptosTC nuevaVigenciaConceptoTC;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCCrear;
    private List<VigenciasConceptosTC> listVigenciasConceptosTCBorrar;
    private VigenciasConceptosTC editarVigenciaConceptoTC;
    private int cualCeldaVigenciaConceptoTC, tipoListaVigenciaConceptoTC;
    private VigenciasConceptosTC duplicarVigenciaConceptoTC;
    private boolean cambiosVigenciaConceptoTC;
    private BigInteger secRegistroVigenciaConceptoTC;
    private BigInteger backUpSecRegistroVigenciaConceptoTC;
    private String auxVCTC_Descripcion;
    private Date auxVCTC_FechaIni, auxVCTC_FechaFin;
    private Column vigenciaCTCFechaInicial, vigenciaCTCFechaFinal, vigenciaCTCDescripcion;
    private boolean permitirIndexVigenciaConceptoTC;
    /////////////Lista Valores VigenciasConceptosTC///////////////////////
    private List<TiposContratos> listTiposContratos;
    private List<TiposContratos> filtrarListTiposContratos;
    private TiposContratos tipoContratoSeleccionado;
    ///////////////////VigenciasConceptosRL///////////////////////////////
    private List<VigenciasConceptosRL> listVigenciasConceptosRLConcepto;
    private List<VigenciasConceptosRL> filtrarListVigenciasConceptosRL;
    ///////////////////VigenciasConceptosRL///////////////////////
    private int banderaVigenciaConceptoRL;
    private int indexVigenciaConceptoRL, indexAuxVigenciaConceptoRL;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLModificar;
    private VigenciasConceptosRL nuevaVigenciaConceptoRL;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLCrear;
    private List<VigenciasConceptosRL> listVigenciasConceptosRLBorrar;
    private VigenciasConceptosRL editarVigenciaConceptoRL;
    private int cualCeldaVigenciaConceptoRL, tipoListaVigenciaConceptoRL;
    private VigenciasConceptosRL duplicarVigenciaConceptoRL;
    private boolean cambiosVigenciaConceptoRL;
    private BigInteger secRegistroVigenciaConceptoRL;
    private BigInteger backUpSecRegistroVigenciaConceptoRL;
    private String auxVCRL_Descripcion;
    private Date auxVCRL_FechaIni, auxVCRL_FechaFin;
    private Column vigenciaCRLFechaInicial, vigenciaCRLFechaFinal, vigenciaCRLDescripcion;
    private boolean permitirIndexVigenciaConceptoRL;
    /////////////Lista Valores VigenciasConceptosRL///////////////////////
    private List<ReformasLaborales> listReformasLaborales;
    private List<ReformasLaborales> filtrarListReformasLaborales;
    private ReformasLaborales reformaLaboralSeleccionado;
    ///////////////////FormulasConceptos///////////////////////////////
    private List<FormulasConceptos> listFormulasConceptosConcepto;
    private List<FormulasConceptos> filtrarListFormulasConceptosConcepto;
    ///////////////////FormulasConceptos///////////////////////
    private int banderaFormulasConceptos;
    private int indexFormulasConceptos, indexAuxFormulasConceptos;
    private List<FormulasConceptos> listFormulasConceptosModificar;
    private FormulasConceptos nuevaFormulasConceptos;
    private List<FormulasConceptos> listFormulasConceptosCrear;
    private List<FormulasConceptos> listFormulasConceptosBorrar;
    private FormulasConceptos editarFormulasConceptos;
    private int cualCeldaFormulasConceptos, tipoListaFormulasConceptos;
    private FormulasConceptos duplicarFormulasConceptos;
    private boolean cambiosFormulasConceptos;
    private BigInteger secRegistroFormulasConceptos;
    private BigInteger backUpSecRegistroFormulasConceptos;
    private String auxFC_Descripcion, auxFC_Orden;
    private Date auxFC_FechaIni, auxFC_FechaFin;
    private Column formulaCFechaInicial, formulaCFechaFinal, formulaCNombre, formulaCOrden;
    private boolean permitirIndexFormulasConceptos;
    private FormulasConceptos actualFormulaConcepto;
    /////////////Lista Valores FormulasConceptos///////////////////////
    private List<Formulas> listFormulas;
    private List<Formulas> filtrarListFormulas;
    private Formulas formulaSeleccionado;
    private List<FormulasConceptos> listFormulasConceptos;
    private List<FormulasConceptos> filtrarListFormulasConceptos;
    private FormulasConceptos formulaConceptoSeleccionado;
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

    public ControlDetalleConcepto() {
        paginaRetorno = "";
        formulaSeleccionada = true;
        conceptoActual = new Conceptos();

        listFormulasConceptosConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasGruposConceptosConcepto = null;
        listVigenciasCuentasConcepto = null;

        comportamientoConcepto = "";
        fechaParametro = new Date(1, 1, 0);

        permitirIndexVigenciaCuenta = true;
        permitirIndexVigenciaGrupoConcepto = true;
        permitirIndexVigenciaConceptoTT = true;
        permitirIndexVigenciaConceptoTC = true;
        permitirIndexVigenciaConceptoRL = true;
        permitirIndexFormulasConceptos = true;

        listTiposCentrosCostos = null;
        listTiposContratos = null;
        listCuentas = null;
        listCentrosCostos = null;
        listReformasLaborales = null;
        listFormulas = null;
        listFormulasConceptos = null;

        tipoCentroCostoSeleccionado = new TiposCentrosCostos();
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        tipoContratoSeleccionado = new TiposContratos();
        cuentaSeleccionada = new Cuentas();
        centroCostoSeleccionado = new CentrosCostos();
        reformaLaboralSeleccionado = new ReformasLaborales();
        formulaSeleccionado = new Formulas();
        formulaConceptoSeleccionado = new FormulasConceptos();

        nombreExportar = "";
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";

        secRegistroVigenciaCuenta = null;
        secRegistroVigenciaConceptoTT = null;
        secRegistroVigenciaGrupoConcepto = null;
        secRegistroVigenciaConceptoTC = null;
        secRegistroVigenciaConceptoRL = null;
        secRegistroFormulasConceptos = null;

        backUpSecRegistroVigenciaConceptoTT = null;
        backUpSecRegistroVigenciaCuenta = null;
        backUpSecRegistroVigenciaGrupoConcepto = null;
        backUpSecRegistroVigenciaConceptoTC = null;
        backUpSecRegistroVigenciaConceptoRL = null;
        backUpSecRegistroFormulasConceptos = null;

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

        indexVigenciaCuenta = -1;
        indexVigenciaGrupoConcepto = -1;
        indexVigenciaConceptoTT = -1;
        indexVigenciaConceptoTC = -1;
        indexVigenciaConceptoRL = -1;
        indexFormulasConceptos = -1;

        banderaVigenciaCuenta = 0;
        banderaVigenciaGrupoConcepto = 0;
        banderaVigenciaConceptoTT = 0;
        banderaVigenciaConceptoTC = 0;
        banderaVigenciaConceptoRL = 0;
        banderaFormulasConceptos = 0;

        nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
        nombreXML = "ConceptosXML";

        duplicarVigenciaCuenta = new VigenciasCuentas();
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

    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarDetalleConcepto.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void obtenerConcepto(BigInteger secuencia) {
        conceptoActual = administrarDetalleConcepto.consultarConceptoActual(secuencia);
        if (conceptoActual != null) {
            Long auto = administrarDetalleConcepto.contarFormulasConceptosConcepto(conceptoActual.getSecuencia());
            System.out.println("Automatico : " + auto);
            Long semi = administrarDetalleConcepto.contarFormulasNovedadesConcepto(conceptoActual.getSecuencia());
            System.out.println("Semi - Automatico : " + semi);
            if (auto == 0 && semi == 0) {
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
        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptosConcepto = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptosConcepto = null;
        formulaSeleccionada = true;
    }

    public void modificarVigenciaCuenta(int indice) {
        boolean retorno = validarNuevosDatosVigenciaCuenta(0);
        if (retorno == true) {
            if (tipoListaVigenciaCuenta == 0) {
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indice))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indice));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indice))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaCuenta = -1;
                secRegistroVigenciaCuenta = null;
            } else {
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indice))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indice));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indice))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaCuenta = -1;
                secRegistroVigenciaCuenta = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCuenta");
            cambiosVigenciaCuenta = true;

        } else {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getTipocc().setNombre(auxVC_TipoCC);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().setDescripcion(auxVC_DescDeb);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().setCodigo(auxVC_Debito);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadord().setNombre(auxVC_ConsDeb);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().setDescripcion(auxVC_DescCre);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().setCodigo(auxVC_Credito);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadorc().setNombre(auxVC_ConsCre);
            }
            if (tipoListaVigenciaCuenta == 1) {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getTipocc().setNombre(auxVC_TipoCC);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().setDescripcion(auxVC_DescDeb);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().setCodigo(auxVC_Debito);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadord().setNombre(auxVC_ConsDeb);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().setDescripcion(auxVC_DescCre);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().setCodigo(auxVC_Credito);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadorc().setNombre(auxVC_ConsCre);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCuenta");
            context.execute("errorRegNuevo.show()");
            indexVigenciaCuenta = -1;
        }
    }

    public void modificarVigenciaCuenta(int indice, String confirmarCambio, String valorConfirmar) {
        indexVigenciaCuenta = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOCC")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getTipocc().setNombre(auxVC_TipoCC);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getTipocc().setNombre(auxVC_TipoCC);
            }
            for (int i = 0; i < listTiposCentrosCostos.size(); i++) {
                if (listTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setTipocc(listTiposCentrosCostos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setTipocc(listTiposCentrosCostos.get(indiceUnicoElemento));
                }
                listTiposCentrosCostos.clear();
                getListTiposCentrosCostos();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CODDEBITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getCuentad().setCodigo(auxVC_Debito);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getCuentad().setCodigo(auxVC_Debito);
            }
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setCuentad(listCuentas.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setCuentad(listCuentas.get(indiceUnicoElemento));
                }
                listCuentas.clear();
                getListCuentas();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("DESDEBITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getCuentad().setDescripcion(auxVC_DescDeb);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getCuentad().setDescripcion(auxVC_DescDeb);
            }
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setCuentad(listCuentas.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setCuentad(listCuentas.get(indiceUnicoElemento));
                }
                listCuentas.clear();
                getListCuentas();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }

        }
        if (confirmarCambio.equalsIgnoreCase("CONSOLIDADORDEBITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getConsolidadord().setNombre(auxVC_ConsDeb);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getConsolidadord().setNombre(auxVC_ConsDeb);
            }
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setConsolidadord(listCentrosCostos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setConsolidadord(listCentrosCostos.get(indiceUnicoElemento));
                }
                listCentrosCostos.clear();
                getListCentrosCostos();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }

        }

        ////////////////
        if (confirmarCambio.equalsIgnoreCase("CODCREDITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getCuentac().setCodigo(auxVC_Credito);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getCuentac().setCodigo(auxVC_Credito);
            }
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setCuentac(listCuentas.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setCuentac(listCuentas.get(indiceUnicoElemento));
                }
                listCuentas.clear();
                getListCuentas();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("DESCREDITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getCuentac().setDescripcion(auxVC_DescCre);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getCuentac().setDescripcion(auxVC_DescCre);
            }
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setCuentac(listCuentas.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setCuentac(listCuentas.get(indiceUnicoElemento));
                }
                listCuentas.clear();
                getListCuentas();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }

        }
        if (confirmarCambio.equalsIgnoreCase("CONSOLIDADORCREDITO")) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indice).getConsolidadorc().setNombre(auxVC_ConsCre);
            } else {
                filtrarListVigenciasCuentasConcepto.get(indice).getConsolidadorc().setNombre(auxVC_ConsCre);
            }
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indice).setConsolidadorc(listCentrosCostos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasCuentasConcepto.get(indice).setConsolidadorc(listCentrosCostos.get(indiceUnicoElemento));
                }
                listCentrosCostos.clear();
                getListCentrosCostos();
            } else {
                permitirIndexVigenciaCuenta = false;
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }

        }
        if (coincidencias == 1) {
            if (tipoListaVigenciaCuenta == 0) {
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indice))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indice));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indice))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaCuenta = -1;
                secRegistroVigenciaCuenta = null;
            } else {
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indice))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indice));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indice))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaCuenta = -1;
                secRegistroVigenciaCuenta = null;
            }
            cambiosVigenciaCuenta = true;
        }
        context.update("form:datosVigenciaCuenta");
    }

    //////////////VigenciaGrupoConcepto////////////////
    public void modificarVigenciaGrupoConcepto(int indice) {
        boolean retorno = validarNuevosDatosVigenciaGrupoConcepto(0);
        if (retorno == true) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                if (!listVigenciasGruposConceptosCrear.contains(listVigenciasGruposConceptosConcepto.get(indice))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indice));
                    } else if (!listVigenciasGruposConceptosModificar.contains(listVigenciasGruposConceptosConcepto.get(indice))) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaGrupoConcepto = -1;
                secRegistroVigenciaGrupoConcepto = null;
            } else {
                if (!listVigenciasGruposConceptosCrear.contains(filtrarListVigenciasGruposConceptosConcepto.get(indice))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indice));
                    } else if (!listVigenciasGruposConceptosModificar.contains(filtrarListVigenciasGruposConceptosConcepto.get(indice))) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaGrupoConcepto = -1;
                secRegistroVigenciaGrupoConcepto = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoConcepto");
            cambiosVigenciaGrupoConcepto = true;
        } else {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
                listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            }
            if (tipoListaVigenciaGrupoConcepto == 1) {
                filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
                filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoConcepto");
            context.execute("errorRegNuevo.show()");
            indexVigenciaGrupoConcepto = -1;
        }
    }

    public void modificarVigenciaGrupoConcepto(int indice, String confirmarCambio, String valorConfirmar) {
        indexVigenciaGrupoConcepto = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                listVigenciasGruposConceptosConcepto.get(indice).getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            } else {
                filtrarListVigenciasGruposConceptosConcepto.get(indice).getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            }
            for (int i = 0; i < listGruposConceptos.size(); i++) {
                if (listGruposConceptos.get(i).getStrCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaGrupoConcepto == 0) {
                    listVigenciasGruposConceptosConcepto.get(indice).setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasGruposConceptosConcepto.get(indice).setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                }
                listGruposConceptos.clear();
                getListGruposConceptos();
            } else {
                permitirIndexVigenciaGrupoConcepto = false;
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("DESCRIPCION")) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                listVigenciasGruposConceptosConcepto.get(indice).getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            } else {
                filtrarListVigenciasGruposConceptosConcepto.get(indice).getGrupoconcepto().setDescripcion(auxVGC_Descripcion);
            }
            for (int i = 0; i < listGruposConceptos.size(); i++) {
                if (listGruposConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaGrupoConcepto == 0) {
                    listVigenciasGruposConceptosConcepto.get(indice).setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasGruposConceptosConcepto.get(indice).setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                }
                listGruposConceptos.clear();
                getListGruposConceptos();
            } else {
                permitirIndexVigenciaGrupoConcepto = false;
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                if (!listVigenciasGruposConceptosCrear.contains(listVigenciasGruposConceptosConcepto.get(indice))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indice));
                    } else if (!listVigenciasGruposConceptosModificar.contains(listVigenciasGruposConceptosConcepto.get(indice))) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaGrupoConcepto = -1;
                secRegistroVigenciaGrupoConcepto = null;
            } else {
                if (!listVigenciasGruposConceptosCrear.contains(filtrarListVigenciasGruposConceptosConcepto.get(indice))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indice));
                    } else if (!listVigenciasGruposConceptosModificar.contains(filtrarListVigenciasGruposConceptosConcepto.get(indice))) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaGrupoConcepto = -1;
                secRegistroVigenciaGrupoConcepto = null;
            }
            cambiosVigenciaGrupoConcepto = true;
        }
        context.update("form:datosVigenciaGrupoConcepto");
    }

    //////////////VigenciaConceptoTT////////////////
    public void modificarVigenciaConceptoTT(int indice) {
        boolean retorno = validarNuevosDatosVigenciaConceptoTT(0);
        if (retorno == true) {
            if (tipoListaVigenciaConceptoTT == 0) {
                if (!listVigenciasConceptosTTCrear.contains(listVigenciasConceptosTTConcepto.get(indice))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indice));
                    } else if (!listVigenciasConceptosTTModificar.contains(listVigenciasConceptosTTConcepto.get(indice))) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTT = -1;
                secRegistroVigenciaConceptoTT = null;
            } else {
                if (!listVigenciasConceptosTTCrear.contains(filtrarListVigenciasConceptosTT.get(indice))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indice));
                    } else if (!listVigenciasConceptosTTModificar.contains(filtrarListVigenciasConceptosTT.get(indice))) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTT = -1;
                secRegistroVigenciaConceptoTT = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTT");
            cambiosVigenciaConceptoTT = true;
        } else {
            if (tipoListaVigenciaConceptoTT == 0) {
                listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getTipotrabajador().setNombre(auxVCTT_Descripcion);
            }
            if (tipoListaVigenciaConceptoTT == 1) {
                filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).getTipotrabajador().setNombre(auxVCTT_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTT");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoTT = -1;
        }
    }

    //////////////VigenciaConceptoTT////////////////
    public void modificarVigenciaConceptoTT(int indice, String confirmarCambio, String valorConfirmar) {
        indexVigenciaConceptoTT = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TRABAJADOR")) {
            if (tipoListaVigenciaConceptoTT == 0) {
                listVigenciasConceptosTTConcepto.get(indice).getTipotrabajador().setNombre(auxVCTT_Descripcion);
            } else {
                filtrarListVigenciasConceptosTT.get(indice).getTipotrabajador().setNombre(auxVCTT_Descripcion);
            }
            for (int i = 0; i < listTiposTrabajadores.size(); i++) {
                if (listTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaConceptoTT == 0) {
                    listVigenciasConceptosTTConcepto.get(indice).setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasConceptosTT.get(indice).setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                }
                listTiposTrabajadores.clear();
                getListTiposTrabajadores();
            } else {
                permitirIndexVigenciaConceptoTT = false;
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVigenciaConceptoTT == 0) {
                if (!listVigenciasConceptosTTCrear.contains(listVigenciasConceptosTTConcepto.get(indice))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indice));
                    } else if (!listVigenciasConceptosTTModificar.contains(listVigenciasConceptosTTConcepto.get(indice))) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTT = -1;
                secRegistroVigenciaConceptoTT = null;
            } else {
                if (!listVigenciasConceptosTTCrear.contains(filtrarListVigenciasConceptosTT.get(indice))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indice));
                    } else if (!listVigenciasConceptosTTModificar.contains(filtrarListVigenciasConceptosTT.get(indice))) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTT = -1;
                secRegistroVigenciaConceptoTT = null;
            }
            cambiosVigenciaConceptoTT = true;
        }
        context.update("form:datosVigenciaConceptoTT");
    }

    //////////////VigenciaConceptoTC////////////////
    public void modificarVigenciaConceptoTC(int indice) {
        boolean retorno = validarNuevosDatosVigenciaConceptoTC(0);
        if (retorno == true) {
            if (tipoListaVigenciaConceptoTC == 0) {
                if (!listVigenciasConceptosTCCrear.contains(listVigenciasConceptosTCConcepto.get(indice))) {
                    if (listVigenciasConceptosTCModificar.isEmpty()) {
                        listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indice));
                    } else if (!listVigenciasConceptosTCModificar.contains(listVigenciasConceptosTCConcepto.get(indice))) {
                        listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTC = -1;
                secRegistroVigenciaConceptoTC = null;
            } else {
                if (!listVigenciasConceptosTCCrear.contains(filtrarListVigenciasConceptosTC.get(indice))) {
                    if (listVigenciasConceptosTCModificar.isEmpty()) {
                        listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indice));
                    } else if (!listVigenciasConceptosTCModificar.contains(filtrarListVigenciasConceptosTC.get(indice))) {
                        listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoTC = -1;
                secRegistroVigenciaConceptoTC = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTC");
            cambiosVigenciaConceptoTC = true;
        } else {
            if (tipoListaVigenciaConceptoTC == 0) {
                listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoTC = -1;
        }
    }

    //////////////VigenciaConceptoTC////////////////
    public void modificarVigenciaConceptoTC(int indice, String confirmarCambio, String valorConfirmar) {
        boolean retorno = validarNuevosDatosVigenciaConceptoTC(0);
        if (retorno == true) {
            indexVigenciaConceptoTC = indice;
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            if (confirmarCambio.equalsIgnoreCase("CONTRATO")) {
                if (tipoListaVigenciaConceptoTC == 0) {
                    listVigenciasConceptosTCConcepto.get(indice).getTipocontrato().setNombre(auxVCTC_Descripcion);
                } else {
                    filtrarListVigenciasConceptosTC.get(indice).getTipocontrato().setNombre(auxVCTC_Descripcion);
                }
                for (int i = 0; i < listTiposContratos.size(); i++) {
                    if (listTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVigenciaConceptoTC == 0) {
                        listVigenciasConceptosTCConcepto.get(indice).setTipocontrato(listTiposContratos.get(indiceUnicoElemento));
                    } else {
                        filtrarListVigenciasConceptosTC.get(indice).setTipocontrato(listTiposContratos.get(indiceUnicoElemento));
                    }
                    listTiposContratos.clear();
                    getListTiposContratos();
                } else {
                    permitirIndexVigenciaConceptoTC = false;
                    context.update("form:TipoContratosDialogo");
                    context.execute("TipoContratosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaConceptoTC == 0) {
                    if (!listVigenciasConceptosTCCrear.contains(listVigenciasConceptosTCConcepto.get(indice))) {
                        if (listVigenciasConceptosTCModificar.isEmpty()) {
                            listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indice));
                        } else if (!listVigenciasConceptosTCModificar.contains(listVigenciasConceptosTCConcepto.get(indice))) {
                            listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexVigenciaConceptoTC = -1;
                    secRegistroVigenciaConceptoTC = null;
                } else {
                    if (!listVigenciasConceptosTCCrear.contains(filtrarListVigenciasConceptosTC.get(indice))) {
                        if (listVigenciasConceptosTCModificar.isEmpty()) {
                            listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indice));
                        } else if (!listVigenciasConceptosTCModificar.contains(filtrarListVigenciasConceptosTC.get(indice))) {
                            listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexVigenciaConceptoTC = -1;
                    secRegistroVigenciaConceptoTC = null;
                }
                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosVigenciaConceptoTC");
        } else {
            if (tipoListaVigenciaConceptoTC == 0) {
                listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoTC = -1;
        }
    }

    //////////////VigenciaConceptoRL////////////////
    public void modificarVigenciaConceptoRL(int indice) {
        boolean retorno = validarNuevosDatosVigenciaConceptoRL(0);
        if (retorno == true) {
            if (tipoListaVigenciaConceptoRL == 0) {
                if (!listVigenciasConceptosRLCrear.contains(listVigenciasConceptosRLConcepto.get(indice))) {
                    if (listVigenciasConceptosRLModificar.isEmpty()) {
                        listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indice));
                    } else if (!listVigenciasConceptosRLModificar.contains(listVigenciasConceptosRLConcepto.get(indice))) {
                        listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoRL = -1;
                secRegistroVigenciaConceptoRL = null;
            } else {
                if (!listVigenciasConceptosRLCrear.contains(filtrarListVigenciasConceptosRL.get(indice))) {
                    if (listVigenciasConceptosRLModificar.isEmpty()) {
                        listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indice));
                    } else if (!listVigenciasConceptosRLModificar.contains(filtrarListVigenciasConceptosRL.get(indice))) {
                        listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexVigenciaConceptoRL = -1;
                secRegistroVigenciaConceptoRL = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoRL");
            cambiosVigenciaConceptoRL = true;
        } else {
            if (tipoListaVigenciaConceptoRL == 0) {
                listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoRL = -1;
        }
    }

    //////////////VigenciaConceptoRL////////////////
    public void modificarVigenciaConceptoRL(int indice, String confirmarCambio, String valorConfirmar) {
        boolean retorno = validarNuevosDatosVigenciaConceptoRL(0);
        if (retorno == true) {
            indexVigenciaConceptoRL = indice;
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            if (confirmarCambio.equalsIgnoreCase("REFORMA")) {
                if (tipoListaVigenciaConceptoRL == 0) {
                    listVigenciasConceptosRLConcepto.get(indice).getTiposalario().setNombre(auxVCRL_Descripcion);
                } else {
                    filtrarListVigenciasConceptosRL.get(indice).getTiposalario().setNombre(auxVCRL_Descripcion);
                }
                for (int i = 0; i < listReformasLaborales.size(); i++) {
                    if (listReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVigenciaConceptoTC == 0) {
                        listVigenciasConceptosRLConcepto.get(indice).setTiposalario(listReformasLaborales.get(indiceUnicoElemento));
                    } else {
                        filtrarListVigenciasConceptosRL.get(indice).setTiposalario(listReformasLaborales.get(indiceUnicoElemento));
                    }
                    listReformasLaborales.clear();
                    getListReformasLaborales();
                } else {
                    permitirIndexVigenciaConceptoRL = false;
                    context.update("form:ReformaLaboralDialogo");
                    context.execute("ReformaLaboralDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigenciaConceptoRL == 0) {
                    if (!listVigenciasConceptosRLCrear.contains(listVigenciasConceptosRLConcepto.get(indice))) {
                        if (listVigenciasConceptosRLModificar.isEmpty()) {
                            listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indice));
                        } else if (!listVigenciasConceptosRLModificar.contains(listVigenciasConceptosRLConcepto.get(indice))) {
                            listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexVigenciaConceptoRL = -1;
                    secRegistroVigenciaConceptoRL = null;
                } else {
                    if (!listVigenciasConceptosRLCrear.contains(filtrarListVigenciasConceptosRL.get(indice))) {
                        if (listVigenciasConceptosRLModificar.isEmpty()) {
                            listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indice));
                        } else if (!listVigenciasConceptosRLModificar.contains(filtrarListVigenciasConceptosRL.get(indice))) {
                            listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexVigenciaConceptoRL = -1;
                    secRegistroVigenciaConceptoRL = null;
                }
                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosVigenciaConceptoRL");
        } else {
            if (tipoListaVigenciaConceptoRL == 0) {
                listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoRL = -1;
        }
    }

    //////////////FormulasConceptos////////////////
    public void modificarFormulasConceptos(int indice) {
        boolean retorno = validarNuevosDatosFormulasConceptos(0);
        if (retorno == true) {
            if (tipoListaFormulasConceptos == 0) {
                if (!listFormulasConceptosCrear.contains(listFormulasConceptosConcepto.get(indice))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indice));
                    } else if (!listFormulasConceptosModificar.contains(listFormulasConceptosConcepto.get(indice))) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexFormulasConceptos = -1;
                secRegistroFormulasConceptos = null;
            } else {
                if (!listFormulasConceptosCrear.contains(filtrarListFormulasConceptosConcepto.get(indice))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indice));
                    } else if (!listFormulasConceptosModificar.contains(filtrarListFormulasConceptosConcepto.get(indice))) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexFormulasConceptos = -1;
                secRegistroFormulasConceptos = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaConcepto");
            cambiosFormulasConceptos = true;
        } else {
            if (tipoListaFormulasConceptos == 0) {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula().setNombrelargo(auxFC_Descripcion);
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(auxFC_Orden);
            }
            if (tipoListaFormulasConceptos == 1) {
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula().setNombrelargo(auxFC_Descripcion);
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(auxFC_Orden);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
            indexFormulasConceptos = -1;
        }
    }

    //////////////FormulasConceptos////////////////
    public void modificarFormulasConceptos(int indice, String confirmarCambio, String valorConfirmar) {
        boolean retorno = validarNuevosDatosFormulasConceptos(0);
        if (retorno == true) {
            indexFormulasConceptos = indice;
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
                if (tipoListaFormulasConceptos == 0) {
                    listFormulasConceptosConcepto.get(indice).getFormula().setNombrelargo(auxFC_Descripcion);
                } else {
                    filtrarListFormulasConceptosConcepto.get(indice).getFormula().setNombrelargo(auxFC_Descripcion);
                }
                for (int i = 0; i < listFormulas.size(); i++) {
                    if (listFormulas.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVigenciaConceptoTC == 0) {
                        listFormulasConceptosConcepto.get(indice).setFormula(listFormulas.get(indiceUnicoElemento));
                    } else {
                        filtrarListFormulasConceptosConcepto.get(indice).setFormula(listFormulas.get(indiceUnicoElemento));
                    }
                    listFormulas.clear();
                    getListFormulas();
                } else {
                    permitirIndexFormulasConceptos = false;
                    context.update("form:FormulasDialogo");
                    context.execute("FormulasDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (confirmarCambio.equalsIgnoreCase("ORDEN")) {
                if (tipoListaFormulasConceptos == 0) {
                    listFormulasConceptosConcepto.get(indice).setStrOrden(auxFC_Orden);
                } else {
                    filtrarListFormulasConceptosConcepto.get(indice).setStrOrden(auxFC_Orden);
                }
                for (int i = 0; i < listFormulasConceptos.size(); i++) {
                    if (listFormulasConceptos.get(i).getStrOrden().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVigenciaConceptoTC == 0) {
                        listFormulasConceptosConcepto.get(indice).setStrOrden(listFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    } else {
                        filtrarListFormulasConceptosConcepto.get(indice).setStrOrden(listFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    }
                    listFormulasConceptos.clear();
                    getListFormulasConceptos();
                } else {
                    permitirIndexFormulasConceptos = false;
                    context.update("form:FormulaConceptoDialogo");
                    context.execute("FormulaConceptoDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaFormulasConceptos == 0) {
                    if (!listFormulasConceptosCrear.contains(listFormulasConceptosConcepto.get(indice))) {
                        if (listFormulasConceptosModificar.isEmpty()) {
                            listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indice));
                        } else if (!listFormulasConceptosModificar.contains(listFormulasConceptosConcepto.get(indice))) {
                            listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexFormulasConceptos = -1;
                    secRegistroFormulasConceptos = null;
                } else {
                    if (!listFormulasConceptosCrear.contains(filtrarListFormulasConceptosConcepto.get(indice))) {
                        if (listFormulasConceptosModificar.isEmpty()) {
                            listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indice));
                        } else if (!listFormulasConceptosModificar.contains(filtrarListFormulasConceptosConcepto.get(indice))) {
                            listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    indexFormulasConceptos = -1;
                    secRegistroFormulasConceptos = null;
                }
                cambiosVigenciaConceptoTC = true;
            }
            context.update("form:datosFormulaConcepto");
        } else {
            if (tipoListaFormulasConceptos == 0) {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula().setNombrelargo(auxFC_Descripcion);
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(auxFC_Orden);
            }
            if (tipoListaFormulasConceptos == 1) {
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula().setNombrelargo(auxFC_Descripcion);
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(auxFC_Orden);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
            indexFormulasConceptos = -1;
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOCC")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaCuenta.getTipocc().setNombre(auxVC_TipoCC);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaCuenta.getTipocc().setNombre(auxVC_TipoCC);
            }
            for (int i = 0; i < listTiposCentrosCostos.size(); i++) {
                if (listTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setTipocc(listTiposCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoCCVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setTipocc(listTiposCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoCCVC");
                }
                listTiposCentrosCostos.clear();
                getListTiposCentrosCostos();
            } else {
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
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentad(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentad(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentad(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDebitoVC");
                    context.update("formularioDialogos:nuevaDesDebitoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentad(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDebitoVC");
                    context.update("formularioDialogos:duplicaDesDebitoVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setConsolidadord(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaConsoliDebVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setConsolidadord(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaConsoliDebVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentac(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentac(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
            for (int i = 0; i < listCuentas.size(); i++) {
                if (listCuentas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setCuentac(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCreditoVC");
                    context.update("formularioDialogos:nuevaDesCreditoVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setCuentac(listCuentas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaCreditoVC");
                    context.update("formularioDialogos:duplicaDesCreditoVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaCuenta.setConsolidadorc(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaConsoliCreVC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaCuenta.setConsolidadorc(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaConsoliCreVC");
                }
                listCuentas.clear();
                getListCuentas();
            } else {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaGrupoConcepto.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaGrupoConcepto.getGrupoconcepto().setStrCodigo(auxVGC_Codigo);
            }
            for (int i = 0; i < listGruposConceptos.size(); i++) {
                if (listGruposConceptos.get(i).getStrCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaGrupoConcepto.setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
                listGruposConceptos.clear();
                getListGruposConceptos();
            } else {
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
            for (int i = 0; i < listGruposConceptos.size(); i++) {
                if (listGruposConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaGrupoConcepto.setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoVGC");
                    context.update("formularioDialogos:nuevaDescripcionVGC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(listGruposConceptos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoVGC");
                    context.update("formularioDialogos:duplicarDescripcionVGC");
                }
                listGruposConceptos.clear();
                getListGruposConceptos();
            } else {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TRABAJADOR")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoTT.getTipotrabajador().setNombre(auxVCTT_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoTT.getTipotrabajador().setNombre(auxVCTT_Descripcion);
            }
            for (int i = 0; i < listTiposTrabajadores.size(); i++) {
                if (listTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoTT.setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTrabajadorVCTT");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoTT.setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTrabajadorVCTT");
                }
                listTiposTrabajadores.clear();
                getListTiposTrabajadores();
            } else {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONTRATO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoTC.getTipocontrato().setNombre(auxVCTC_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoTC.getTipocontrato().setNombre(auxVCTC_Descripcion);
            }
            for (int i = 0; i < listTiposContratos.size(); i++) {
                if (listTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoTC.setTipocontrato(listTiposContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaContratoVCTC");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoTC.setTipocontrato(listTiposContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarContratoVCTC");
                }
                listTiposContratos.clear();
                getListTiposContratos();
            } else {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMA")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaConceptoRL.getTiposalario().setNombre(auxVCRL_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaConceptoRL.getTiposalario().setNombre(auxVCRL_Descripcion);
            }
            for (int i = 0; i < listReformasLaborales.size(); i++) {
                if (listReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaConceptoRL.setTiposalario(listReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaReformaVCRL");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaConceptoRL.setTiposalario(listReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarReformaVCRL");
                }
                listReformasLaborales.clear();
                getListReformasLaborales();
            } else {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoNuevo == 1) {
                nuevaFormulasConceptos.getFormula().setNombrelargo(auxFC_Descripcion);
            } else if (tipoNuevo == 2) {
                duplicarFormulasConceptos.getFormula().setNombrelargo(auxFC_Descripcion);
            }
            for (int i = 0; i < listFormulas.size(); i++) {
                if (listFormulas.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulasConceptos.setFormula(listFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaFormulaFC");
                } else if (tipoNuevo == 2) {
                    duplicarFormulasConceptos.setFormula(listFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarFormulaFC");
                }
                listFormulas.clear();
                getListFormulas();
            } else {
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
            for (int i = 0; i < listFormulasConceptos.size(); i++) {
                if (listFormulasConceptos.get(i).getStrOrden().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulasConceptos.setStrOrden(listFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    context.update("formularioDialogos:nuevaOrdenFC");
                } else if (tipoNuevo == 2) {
                    duplicarFormulasConceptos.setStrOrden(listFormulasConceptos.get(indiceUnicoElemento).getStrOrden());
                    context.update("formularioDialogos:duplicarOrdenFC");
                }
                listFormulasConceptos.clear();
                getListFormulasConceptos();
            } else {
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

    public void cambiarIndiceVigenciaCuenta(int indice, int celda) {
        if (permitirIndexVigenciaCuenta == true) {
            cualCeldaVigenciaCuenta = celda;
            indexVigenciaCuenta = indice;
            secRegistroVigenciaCuenta = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVC_FechaIni = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechainicial();
            auxVC_FechaFin = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechafinal();
            auxVC_TipoCC = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getTipocc().getNombre();
            auxVC_DescDeb = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().getDescripcion();
            auxVC_Debito = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad().getCodigo();
            auxVC_ConsDeb = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadord().getNombre();
            auxVC_DescCre = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().getDescripcion();
            auxVC_Credito = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac().getCodigo();
            auxVC_ConsCre = listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadorc().getNombre();

            indexVigenciaConceptoTC = -1;
            indexVigenciaConceptoTT = -1;
            indexVigenciaGrupoConcepto = -1;
            indexVigenciaConceptoRL = -1;
            indexFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void cambiarIndiceVigenciaGrupoConcepto(int indice, int celda) {
        if (permitirIndexVigenciaGrupoConcepto == true) {
            cualCeldaVigenciaGrupoConcepto = celda;
            indexVigenciaGrupoConcepto = indice;
            secRegistroVigenciaGrupoConcepto = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVGC_FechaIni = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechainicial();
            auxVGC_FechaFin = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechafinal();
            auxVGC_Codigo = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().getStrCodigo();
            auxVGC_Descripcion = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto().getDescripcion();

            indexVigenciaConceptoTC = -1;
            indexVigenciaConceptoTT = -1;
            indexVigenciaCuenta = -1;
            indexVigenciaConceptoRL = -1;
            indexFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void cambiarIndiceVigenciaConceptoTT(int indice, int celda) {
        if (permitirIndexVigenciaConceptoTT == true) {
            cualCeldaVigenciaConceptoTT = celda;
            indexVigenciaConceptoTT = indice;
            secRegistroVigenciaConceptoTT = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCTT_FechaIni = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getFechainicial();
            auxVCTT_FechaFin = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getFechafinal();
            auxVCTT_Descripcion = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getTipotrabajador().getNombre();

            indexVigenciaConceptoTC = -1;
            indexVigenciaGrupoConcepto = -1;
            indexVigenciaCuenta = -1;
            indexVigenciaConceptoRL = -1;
            indexFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void cambiarIndiceVigenciaConceptoTC(int indice, int celda) {
        if (permitirIndexVigenciaConceptoTC == true) {
            cualCeldaVigenciaConceptoTC = celda;
            indexVigenciaConceptoTC = indice;
            secRegistroVigenciaConceptoTC = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCTC_FechaIni = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getFechainicial();
            auxVCTC_FechaFin = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getFechafinal();
            auxVCTC_Descripcion = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getTipocontrato().getNombre();

            indexVigenciaConceptoTT = -1;
            indexVigenciaGrupoConcepto = -1;
            indexVigenciaCuenta = -1;
            indexVigenciaConceptoRL = -1;
            indexFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void cambiarIndiceVigenciaConceptoRL(int indice, int celda) {
        if (permitirIndexVigenciaConceptoRL == true) {
            cualCeldaVigenciaConceptoRL = celda;
            indexVigenciaConceptoRL = indice;
            secRegistroVigenciaConceptoRL = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxVCRL_FechaIni = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getFechainicial();
            auxVCRL_FechaFin = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getFechafinal();
            auxVCRL_Descripcion = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getTiposalario().getNombre();

            indexVigenciaConceptoTT = -1;
            indexVigenciaGrupoConcepto = -1;
            indexVigenciaCuenta = -1;
            indexVigenciaConceptoTC = -1;
            indexFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void cambiarIndiceFormulasConceptos(int indice, int celda) {
        if (permitirIndexFormulasConceptos == true) {
            cualCeldaFormulasConceptos = celda;
            indexFormulasConceptos = indice;
            secRegistroFormulasConceptos = listFormulasConceptosConcepto.get(indexFormulasConceptos).getSecuencia();
            actualFormulaConcepto = listFormulasConceptosConcepto.get(indexFormulasConceptos);
            ///////// Captura Objetos Para Campos NotNull ///////////
            auxFC_FechaIni = listFormulasConceptosConcepto.get(indexFormulasConceptos).getFechainicial();
            auxFC_FechaFin = listFormulasConceptosConcepto.get(indexFormulasConceptos).getFechafinal();
            auxFC_Descripcion = listFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula().getNombrelargo();
            auxFC_Orden = listFormulasConceptosConcepto.get(indexFormulasConceptos).getStrOrden();

            indexVigenciaConceptoTT = -1;
            indexVigenciaConceptoRL = -1;
            indexVigenciaGrupoConcepto = -1;
            indexVigenciaCuenta = -1;
            indexVigenciaConceptoTC = -1;
            formulaSeleccionada = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public boolean validarFechasRegistroVigenciaCuenta(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasCuentas auxiliar = listVigenciasCuentasConcepto.get(indexVigenciaCuenta);
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
            VigenciasGruposConceptos auxiliar = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto);
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
            VigenciasConceptosTT auxiliar = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT);
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
            VigenciasConceptosTC auxiliar = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC);
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
            VigenciasConceptosRL auxiliar = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL);
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
            FormulasConceptos auxiliar = listFormulasConceptosConcepto.get(indexFormulasConceptos);
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

    public void modificacionesFechaVigenciaCuenta(int i, int c) {
        VigenciasCuentas auxiliar = null;
        if (tipoListaVigenciaCuenta == 0) {
            auxiliar = listVigenciasCuentasConcepto.get(i);
        }
        if (tipoListaVigenciaCuenta == 1) {
            auxiliar = filtrarListVigenciasCuentasConcepto.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(0);
            if (validacion == true) {
                cambiarIndiceVigenciaCuenta(i, c);
                modificarVigenciaCuenta(i);
                indexAuxVigenciaCuenta = i;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciaCuenta");
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaVigenciaCuenta == 0) {
                    listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechainicial(auxVC_FechaIni);
                    listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechafinal(auxVC_FechaFin);
                }
                if (tipoListaVigenciaCuenta == 1) {
                    filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechainicial(auxVC_FechaIni);
                    filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechafinal(auxVC_FechaFin);
                }
                context.update("form:datosVigenciaCuenta");
                context.execute("errorFechasVC.show()");
                indexVigenciaCuenta = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechainicial(auxVC_FechaIni);
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechafinal(auxVC_FechaFin);
            }
            if (tipoListaVigenciaCuenta == 1) {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechainicial(auxVC_FechaIni);
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setFechafinal(auxVC_FechaFin);
            }
            context.update("form:datosVigenciaCuenta");
            context.execute("errorRegNuevo.show()");
            indexVigenciaCuenta = -1;
        }
    }

    public void modificacionesFechaVigenciaGrupoConcepto(int i, int c) {
        VigenciasGruposConceptos auxiliar = null;
        if (tipoListaVigenciaGrupoConcepto == 0) {
            auxiliar = listVigenciasGruposConceptosConcepto.get(i);
        }
        if (tipoListaVigenciaGrupoConcepto == 1) {
            auxiliar = filtrarListVigenciasGruposConceptosConcepto.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(0);
            if (validacion == true) {
                cambiarIndiceVigenciaGrupoConcepto(i, c);
                modificarVigenciaGrupoConcepto(i);
                indexAuxVigenciaGrupoConcepto = i;
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaVigenciaGrupoConcepto == 0) {
                    listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechainicial(auxVGC_FechaIni);
                    listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechafinal(auxVGC_FechaFin);
                }
                if (tipoListaVigenciaGrupoConcepto == 1) {
                    filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechainicial(auxVGC_FechaIni);
                    filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechafinal(auxVGC_FechaFin);
                }
                context.update("form:datosVigenciaGrupoConcepto");
                context.execute("errorFechasVC.show()");
                indexVigenciaGrupoConcepto = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaVigenciaGrupoConcepto == 0) {
                listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechainicial(auxVGC_FechaIni);
                listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechafinal(auxVGC_FechaFin);
            }
            if (tipoListaVigenciaGrupoConcepto == 1) {
                filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechainicial(auxVGC_FechaIni);
                filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setFechafinal(auxVGC_FechaFin);
            }
            context.update("form:datosVigenciaGrupoConcepto");
            context.execute("errorRegNuevo.show()");
            indexVigenciaGrupoConcepto = -1;
        }
    }

    public void modificacionesFechaVigenciaConceptoTT(int i, int c) {
        VigenciasConceptosTT auxiliar = null;
        if (tipoListaVigenciaConceptoTT == 0) {
            auxiliar = listVigenciasConceptosTTConcepto.get(i);
        }
        if (tipoListaVigenciaConceptoTT == 1) {
            auxiliar = filtrarListVigenciasConceptosTT.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoTT(i, c);
                modificarVigenciaConceptoTT(i);
                indexAuxVigenciaConceptoTT = i;
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaVigenciaConceptoTT == 0) {
                    listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).setFechainicial(auxVCTT_FechaIni);
                    listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).setFechafinal(auxVCTT_FechaFin);
                }
                if (tipoListaVigenciaConceptoTT == 1) {
                    filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).setFechainicial(auxVCTT_FechaIni);
                    filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).setFechafinal(auxVCTT_FechaFin);
                }
                context.update("form:datosVigenciaConceptoTT");
                context.execute("errorFechasVC.show()");
                indexVigenciaConceptoTT = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaVigenciaConceptoTT == 0) {
                listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).setFechainicial(auxVCTT_FechaIni);
                listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).setFechafinal(auxVCTT_FechaFin);
            }
            if (tipoListaVigenciaConceptoTT == 1) {
                filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).setFechainicial(auxVCTT_FechaIni);
                filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).setFechafinal(auxVCTT_FechaFin);
            }
            context.update("form:datosVigenciaConceptoTT");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoTT = -1;
        }
    }

    public void modificacionesFechaVigenciaConceptoTC(int i, int c) {
        VigenciasConceptosTC auxiliar = null;
        if (tipoListaVigenciaConceptoTC == 0) {
            auxiliar = listVigenciasConceptosTCConcepto.get(i);
        }
        if (tipoListaVigenciaConceptoTC == 1) {
            auxiliar = filtrarListVigenciasConceptosTC.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoTC(i, c);
                modificarVigenciaConceptoTC(i);
                indexAuxVigenciaConceptoTC = i;
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaVigenciaConceptoTC == 0) {
                    listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).setFechainicial(auxVCTC_FechaIni);
                    listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).setFechafinal(auxVCTC_FechaFin);
                }
                if (tipoListaVigenciaConceptoTC == 1) {
                    filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).setFechainicial(auxVCTC_FechaIni);
                    filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).setFechafinal(auxVCTC_FechaFin);
                }
                context.update("form:datosVigenciaConceptoTC");
                context.execute("errorFechasVC.show()");
                indexVigenciaConceptoTC = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaVigenciaConceptoTC == 0) {
                listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).setFechainicial(auxVCTC_FechaIni);
                listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).setFechafinal(auxVCTC_FechaFin);
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).setFechainicial(auxVCTC_FechaIni);
                filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).setFechafinal(auxVCTC_FechaFin);
            }
            context.update("form:datosVigenciaConceptoTC");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoTC = -1;
        }
    }

    public void modificacionesFechaVigenciaConceptoRL(int i, int c) {
        VigenciasConceptosRL auxiliar = null;
        if (tipoListaVigenciaConceptoRL == 0) {
            auxiliar = listVigenciasConceptosRLConcepto.get(i);
        }
        if (tipoListaVigenciaConceptoRL == 1) {
            auxiliar = filtrarListVigenciasConceptosRL.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(0);
            if (validacion == true) {
                cambiarIndiceVigenciaConceptoRL(i, c);
                modificarVigenciaConceptoRL(i);
                indexAuxVigenciaConceptoRL = i;
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaVigenciaConceptoRL == 0) {
                    listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).setFechainicial(auxVCRL_FechaIni);
                    listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).setFechafinal(auxVCRL_FechaFin);
                }
                if (tipoListaVigenciaConceptoRL == 1) {
                    filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).setFechainicial(auxVCRL_FechaIni);
                    filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).setFechafinal(auxVCRL_FechaFin);
                }
                context.update("form:datosVigenciaConceptoRL");
                context.execute("errorFechasVC.show()");
                indexVigenciaConceptoRL = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaVigenciaConceptoRL == 0) {
                listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).setFechainicial(auxVCRL_FechaIni);
                listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).setFechafinal(auxVCRL_FechaFin);
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).setFechainicial(auxVCRL_FechaIni);
                filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).setFechafinal(auxVCRL_FechaFin);
            }
            context.update("form:datosVigenciaConceptoRL");
            context.execute("errorRegNuevo.show()");
            indexVigenciaConceptoRL = -1;
        }
    }

    public void modificacionesFechaFormulasConceptos(int i, int c) {
        FormulasConceptos auxiliar = null;
        if (tipoListaFormulasConceptos == 0) {
            auxiliar = listFormulasConceptosConcepto.get(i);
        }
        if (tipoListaFormulasConceptos == 1) {
            auxiliar = filtrarListFormulasConceptosConcepto.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroFormulasConceptos(0);
            if (validacion == true) {
                cambiarIndiceFormulasConceptos(i, c);
                modificarFormulasConceptos(i);
                indexAuxFormulasConceptos = i;
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaFormulasConceptos == 0) {
                    listFormulasConceptosConcepto.get(indexFormulasConceptos).setFechainicial(auxFC_FechaIni);
                    listFormulasConceptosConcepto.get(indexFormulasConceptos).setFechafinal(auxFC_FechaFin);
                }
                if (tipoListaFormulasConceptos == 1) {
                    filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setFechainicial(auxFC_FechaIni);
                    filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setFechafinal(auxFC_FechaFin);
                }
                context.update("form:datosFormulaConcepto");
                context.execute("errorFechasVC.show()");
                indexFormulasConceptos = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaFormulasConceptos == 0) {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setFechainicial(auxFC_FechaIni);
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setFechafinal(auxFC_FechaFin);
            }
            if (tipoListaFormulasConceptos == 1) {
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setFechainicial(auxFC_FechaIni);
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setFechafinal(auxFC_FechaFin);
            }
            context.update("form:datosFormulaConcepto");
            context.execute("errorRegNuevo.show()");
            indexFormulasConceptos = -1;
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
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    public void guardarCambiosVigenciaCuenta() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCuenta");
        context.update("form:growl");
        k = 0;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        cambiosVigenciaCuenta = false;
    }

    public void guardarCambiosVigenciaGrupoConcepto() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaGrupoConcepto");
        context.update("form:growl");
        k = 0;
        indexVigenciaGrupoConcepto = -1;
        secRegistroVigenciaGrupoConcepto = null;
        cambiosVigenciaGrupoConcepto = false;
    }

    public void guardarCambiosVigenciaConceptoTT() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaConceptoTT");
        context.update("form:growl");
        k = 0;
        indexVigenciaConceptoTT = -1;
        secRegistroVigenciaConceptoTT = null;
        cambiosVigenciaConceptoTT = false;
    }

    public void guardarCambiosVigenciaConceptoTC() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaConceptoTC");
        context.update("form:growl");
        k = 0;
        indexVigenciaConceptoTC = -1;
        secRegistroVigenciaConceptoTC = null;
        cambiosVigenciaConceptoTC = false;
    }

    public void guardarCambiosVigenciaConceptoRL() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaConceptoRL");
        context.update("form:growl");
        k = 0;
        indexVigenciaConceptoRL = -1;
        secRegistroVigenciaConceptoRL = null;
        cambiosVigenciaConceptoRL = false;
    }

    public void guardarCambiosFormulasConceptos() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
        listFormulasConceptosConcepto = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaConcepto");
        context.update("form:growl");
        k = 0;
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
        cambiosFormulasConceptos = false;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (banderaVigenciaCuenta == 1) {
            vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
            vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
            vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
            vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
            vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
            vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
            vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
            vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
            vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
            vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
            banderaVigenciaCuenta = 0;
            filtrarListVigenciasCuentasConcepto = null;
            tipoListaVigenciaCuenta = 0;
        }
        if (banderaVigenciaGrupoConcepto == 1) {
            vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
            vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
            vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
            vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
            vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
            banderaVigenciaGrupoConcepto = 0;
            filtrarListVigenciasGruposConceptosConcepto = null;
            tipoListaVigenciaGrupoConcepto = 0;
        }

        if (banderaVigenciaConceptoTT == 1) {
            vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
            vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
            vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
            vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
            banderaVigenciaConceptoTT = 0;
            filtrarListVigenciasConceptosTT = null;
            tipoListaVigenciaConceptoTT = 0;
        }
        if (banderaVigenciaConceptoTC == 1) {
            vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
            vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
            vigenciaCTCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
            vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
            banderaVigenciaConceptoTC = 0;
            filtrarListVigenciasConceptosTC = null;
            tipoListaVigenciaConceptoTC = 0;
        }
        if (banderaVigenciaConceptoRL == 1) {
            vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
            vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
            vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
            vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
            vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
            banderaVigenciaConceptoRL = 0;
            filtrarListVigenciasConceptosRL = null;
            tipoListaVigenciaConceptoRL = 0;
        }

        if (banderaFormulasConceptos == 1) {
            formulaCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
            formulaCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
            formulaCFechaFinal.setFilterStyle("display: none; visibililty: hidden;");
            formulaCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
            formulaCNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaCOrden = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
            formulaCOrden.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
            banderaFormulasConceptos = 0;
            filtrarListFormulasConceptosConcepto = null;
            tipoListaFormulasConceptos = 0;
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

        indexVigenciaCuenta = -1;
        indexVigenciaConceptoTT = -1;
        indexVigenciaConceptoRL = -1;
        indexVigenciaConceptoTC = -1;
        indexVigenciaGrupoConcepto = -1;
        indexFormulasConceptos = -1;

        indexAuxVigenciaCuenta = -1;
        indexAuxVigenciaConceptoTT = -1;
        indexAuxVigenciaConceptoRL = -1;
        indexAuxVigenciaConceptoTC = -1;
        indexAuxVigenciaGrupoConcepto = -1;
        indexAuxFormulasConceptos = -1;

        secRegistroVigenciaCuenta = null;
        secRegistroVigenciaConceptoTT = null;
        secRegistroVigenciaConceptoRL = null;
        secRegistroVigenciaConceptoTC = null;
        secRegistroVigenciaGrupoConcepto = null;
        secRegistroFormulasConceptos = null;
        k = 0;
        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptosConcepto = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptosConcepto = null;

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

        getListVigenciasCuentasConcepto();
        getListVigenciasConceptosTTConcepto();
        getListVigenciasConceptosTCConcepto();
        getListVigenciasConceptosRLConcepto();
        getListVigenciasGruposConceptosConcepto();
        getListFormulasConceptosConcepto();

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
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexVigenciaCuenta >= 0) {
            if (cualCeldaVigenciaCuenta == 2) {
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 3) {
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 4) {
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 5) {
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 6) {
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 7) {
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaCuenta == 8) {
                context.update("form:CentroCostoCDialogo");
                context.execute("CentroCostoCDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            if (cualCeldaVigenciaGrupoConcepto == 2) {
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVigenciaGrupoConcepto == 3) {
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVigenciaConceptoTT >= 0) {
            if (cualCeldaVigenciaConceptoTT == 2) {
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVigenciaConceptoTC >= 0) {
            if (cualCeldaVigenciaConceptoTC == 2) {
                context.update("form:TipoContratosDialogo");
                context.execute("TipoContratosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVigenciaConceptoRL >= 0) {
            if (cualCeldaVigenciaConceptoRL == 2) {
                context.update("form:ReformaLaboralDialogo");
                context.execute("ReformaLaboralDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexFormulasConceptos >= 0) {
            if (cualCeldaFormulasConceptos == 2) {
                context.update("form:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaFormulasConceptos == 3) {
                context.update("form:FormulaConceptoDialogo");
                context.execute("FormulaConceptoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND, int tt) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tt == 0) {
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TipoCCDialogo");
                context.execute("TipoCCDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
            } else if (dlg == 2) {
                context.update("form:DebitoDialogo");
                context.execute("DebitoDialogo.show()");
            } else if (dlg == 3) {
                context.update("form:CentroCostoDDialogo");
                context.execute("CentroCostoDDialogo.show()");
            } else if (dlg == 4) {
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
            } else if (dlg == 5) {
                context.update("form:CreditoDialogo");
                context.execute("CreditoDialogo.show()");
            } else if (dlg == 6) {
                context.update("form:CentroCostoCDialogo");
                context.execute("CentroCostoCDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
            }
        }
        if (tt == 2) {
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
            }
        }
        if (tt == 3) {
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TipoContratosDialogo");
                context.execute("TipoContratosDialogo.show()");
            }
        }
        if (tt == 4) {
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:ReformaLaboralDialogo");
                context.execute("ReformaLaboralDialogo.show()");
            }
        }
        if (tt == 5) {
            formulaSeleccionada = true;
            context.update("form:detalleFormula");
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:FormulasDialogo");
                context.execute("FormulasDialogo.show()");
            }
            if (dlg == 1) {
                context.update("form:FormulaConceptoDialogo");
                context.execute("FormulaConceptoDialogo.show()");
            }
        }
    }

    public void editarCelda() {
        ///////////VigenciaCuenta/////////////
        if (indexVigenciaCuenta >= 0) {
            if (tipoListaVigenciaCuenta == 0) {
                editarVigenciaCuenta = listVigenciasCuentasConcepto.get(indexVigenciaCuenta);
            }
            if (tipoListaVigenciaCuenta == 1) {
                editarVigenciaCuenta = filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexVigenciaCuenta = -1;
            secRegistroVigenciaCuenta = null;
        }
        ///////////VigenciaCuenta/////////////
        ///////////VigenciaGrupoConcepto/////////////
        if (indexVigenciaGrupoConcepto >= 0) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                editarVigenciaGrupoConcepto = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto);
            }
            if (tipoListaVigenciaGrupoConcepto == 1) {
                editarVigenciaGrupoConcepto = filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexVigenciaGrupoConcepto = -1;
            secRegistroVigenciaGrupoConcepto = null;
        }
        ///////////VigenciaGrupoConcepto/////////////
        ///////////VigenciaConceptoTT/////////////
        if (indexVigenciaConceptoTT >= 0) {
            if (tipoListaVigenciaConceptoTT == 0) {
                editarVigenciaConceptoTT = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT);
            }
            if (tipoListaVigenciaConceptoTT == 1) {
                editarVigenciaConceptoTT = filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexVigenciaConceptoTT = -1;
            secRegistroVigenciaConceptoTT = null;
        }
        ///////////VigenciaConceptoTT/////////////
        ///////////VigenciaConceptoTT/////////////
        if (indexVigenciaConceptoTC >= 0) {
            if (tipoListaVigenciaConceptoTC == 0) {
                editarVigenciaConceptoTC = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC);
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                editarVigenciaConceptoTC = filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexVigenciaConceptoTC = -1;
            secRegistroVigenciaConceptoTC = null;
        }
        ///////////VigenciaConceptoTC/////////////
        ///////////VigenciaConceptoRL/////////////
        if (indexVigenciaConceptoRL >= 0) {
            if (tipoListaVigenciaConceptoRL == 0) {
                editarVigenciaConceptoRL = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL);
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                editarVigenciaConceptoRL = filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexVigenciaConceptoRL = -1;
            secRegistroVigenciaConceptoRL = null;
        }
        ///////////VigenciaConceptoRL/////////////
        ///////////FormulasConceptos/////////////
        if (indexFormulasConceptos >= 0) {
            if (tipoListaFormulasConceptos == 0) {
                editarFormulasConceptos = listFormulasConceptosConcepto.get(indexFormulasConceptos);
            }
            if (tipoListaFormulasConceptos == 1) {
                editarFormulasConceptos = filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            indexFormulasConceptos = -1;
            secRegistroFormulasConceptos = null;
        }
        ///////////FormulasConceptos/////////////
    }

    public void ingresoNuevoRegistro() {
        ////////////////// VigenciasCuentas /////////////////////

        int tamVC = listVigenciasCuentasConcepto.size();
        int tamVGC = listVigenciasGruposConceptosConcepto.size();
        int tamVCTT = listVigenciasConceptosTTConcepto.size();
        int tamVCTC = listVigenciasConceptosTCConcepto.size();
        int tamVCRL = listVigenciasConceptosRLConcepto.size();
        int tamFC = listFormulasConceptosConcepto.size();

        if (tamVC == 0 || tamVGC == 0 || tamVCTT == 0 || tamVCTC == 0 || tamVCRL == 0 || tamFC == 0) {
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            context.execute("seleccionarTablaNewReg.show()");
        } else {
            if (indexVigenciaCuenta >= 0) {
                validarIngresoNuevaVigenciaCuenta();
            } /////////////////// VigenciasCuentas /////////////////////
            ////////////VigenciaGruposConceptos /////////////////////
            else if (indexVigenciaGrupoConcepto >= 0) {
                validarIngresoNuevaVigenciaGrupoConcepto();
            } ////////////VigenciaGruposConceptos /////////////////////
            ////////////VigenciaConceptosTT /////////////////////
            else if (indexVigenciaConceptoTT >= 0) {
                validarIngresoNuevaVigenciaConceptoTT();
            } ////////////VigenciaConceptosTT /////////////////////
            ////////////VigenciaConceptosTC /////////////////////
            else if (indexVigenciaConceptoTC >= 0) {
                validarIngresoNuevaVigenciaConceptoTC();
            } ////////////VigenciaConceptosTC /////////////////////
            ////////////VigenciaConceptosRL /////////////////////
            else if (indexVigenciaConceptoRL >= 0) {
                validarIngresoNuevaVigenciaConceptoRL();
            } ////////////VigenciaConceptosRL /////////////////////
            ////////////FormulasConceptos /////////////////////
            else if (indexFormulasConceptos >= 0) {
                validarIngresoNuevaFormulasConceptos();
            } ////////////FormulasConceptos /////////////////////
            else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("seleccionarRegistro.show()");
            }
        }
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
        if (indexVigenciaCuenta >= 0) {
            duplicarVigenciaCuentaM();
        } ///////////////VigenciaGrupoConcepto///////////////
        else if (indexVigenciaGrupoConcepto >= 0) {
            duplicarVigenciaGrupoConceptoM();
        } ///////////////VigenciaConceptoTT///////////////
        else if (indexVigenciaConceptoTT >= 0) {
            duplicarVigenciaConceptoTTM();
        } ////////////VigenciaConceptoTC /////////////////////
        else if (indexVigenciaConceptoTC >= 0) {
            duplicarVigenciaConceptoTCM();
        } ////////////VigenciaConceptoRL /////////////////////
        else if (indexVigenciaConceptoRL >= 0) {
            duplicarVigenciaConceptoRLM();
        } ////////////FormulasConceptos /////////////////////
        else if (indexFormulasConceptos >= 0) {
            duplicarFormulasConceptosM();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void validarBorradoRegistro() {
        if (indexVigenciaCuenta >= 0) {
            borrarVigenciaCuenta();
        } else if (indexVigenciaGrupoConcepto >= 0) {
            borrarVigenciaGrupoConcepto();
        } else if (indexVigenciaConceptoTT >= 0) {
            borrarVigenciaConceptoTT();
        } else if (indexVigenciaConceptoTC >= 0) {
            borrarVigenciaConceptoTC();
        } else if (indexVigenciaConceptoRL >= 0) {
            borrarVigenciaConceptoRL();
        } else if (indexFormulasConceptos >= 0) {
            borrarFormulasConceptos();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    //////////Validaciones de los campos de las tablas de la pagina///////////////
    public boolean validarNuevosDatosVigenciaCuenta(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasCuentas aux = listVigenciasCuentasConcepto.get(indexVigenciaCuenta);
            if ((!aux.getConsolidadorc().getNombre().isEmpty())
                    && (!aux.getCuentac().getDescripcion().isEmpty())
                    && (!aux.getCuentac().getCodigo().isEmpty())
                    && (!aux.getConsolidadord().getNombre().isEmpty())
                    && (!aux.getCuentad().getDescripcion().isEmpty())
                    && (!aux.getCuentad().getCodigo().isEmpty())
                    && (!aux.getTipocc().getNombre().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaVigenciaCuenta.getConsolidadorc().getNombre().isEmpty())
                    && (!nuevaVigenciaCuenta.getCuentac().getDescripcion().isEmpty())
                    && (!nuevaVigenciaCuenta.getCuentac().getCodigo().isEmpty())
                    && (!nuevaVigenciaCuenta.getConsolidadord().getNombre().isEmpty())
                    && (!nuevaVigenciaCuenta.getCuentad().getDescripcion().isEmpty())
                    && (!nuevaVigenciaCuenta.getCuentad().getCodigo().isEmpty())
                    && (!nuevaVigenciaCuenta.getTipocc().getNombre().isEmpty())
                    && (nuevaVigenciaCuenta.getFechafinal() != null) && (nuevaVigenciaCuenta.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarVigenciaCuenta.getConsolidadorc().getNombre().isEmpty())
                    && (!duplicarVigenciaCuenta.getCuentac().getDescripcion().isEmpty())
                    && (!duplicarVigenciaCuenta.getCuentac().getCodigo().isEmpty())
                    && (!duplicarVigenciaCuenta.getConsolidadord().getNombre().isEmpty())
                    && (!duplicarVigenciaCuenta.getCuentad().getDescripcion().isEmpty())
                    && (!duplicarVigenciaCuenta.getCuentad().getCodigo().isEmpty())
                    && (!duplicarVigenciaCuenta.getTipocc().getNombre().isEmpty())
                    && (duplicarVigenciaCuenta.getFechafinal() != null) && (duplicarVigenciaCuenta.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaGrupoConcepto(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasGruposConceptos aux = listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto);
            if ((!aux.getGrupoconcepto().getDescripcion().isEmpty())
                    && (!aux.getGrupoconcepto().getStrCodigo().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaVigenciaGrupoConcepto.getGrupoconcepto().getDescripcion().isEmpty())
                    && (!nuevaVigenciaGrupoConcepto.getGrupoconcepto().getStrCodigo().isEmpty())
                    && (nuevaVigenciaGrupoConcepto.getFechafinal() != null) && (nuevaVigenciaGrupoConcepto.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarVigenciaGrupoConcepto.getGrupoconcepto().getDescripcion().isEmpty())
                    && (!duplicarVigenciaGrupoConcepto.getGrupoconcepto().getStrCodigo().isEmpty())
                    && (duplicarVigenciaGrupoConcepto.getFechafinal() != null) && (duplicarVigenciaGrupoConcepto.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoTT(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTT aux = listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT);
            if ((!aux.getTipotrabajador().getNombre().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaVigenciaConceptoTT.getTipotrabajador().getNombre().isEmpty())
                    && (nuevaVigenciaConceptoTT.getFechafinal() != null) && (nuevaVigenciaConceptoTT.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarVigenciaConceptoTT.getTipotrabajador().getNombre().isEmpty())
                    && (duplicarVigenciaConceptoTT.getFechafinal() != null) && (duplicarVigenciaConceptoTT.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoTC(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosTC aux = listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC);
            if ((!aux.getTipocontrato().getNombre().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaVigenciaConceptoTC.getTipocontrato().getNombre().isEmpty())
                    && (nuevaVigenciaConceptoTC.getFechafinal() != null) && (nuevaVigenciaConceptoTC.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarVigenciaConceptoTC.getTipocontrato().getNombre().isEmpty())
                    && (duplicarVigenciaConceptoTC.getFechafinal() != null) && (duplicarVigenciaConceptoTC.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosVigenciaConceptoRL(int i) {
        boolean retorno = false;
        if (i == 0) {
            VigenciasConceptosRL aux = listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL);
            if ((!aux.getTiposalario().getNombre().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaVigenciaConceptoRL.getTiposalario().getNombre().isEmpty())
                    && (nuevaVigenciaConceptoRL.getFechafinal() != null) && (nuevaVigenciaConceptoRL.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarVigenciaConceptoRL.getTiposalario().getNombre().isEmpty())
                    && (duplicarVigenciaConceptoRL.getFechafinal() != null) && (duplicarVigenciaConceptoRL.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public boolean validarNuevosDatosFormulasConceptos(int i) {
        boolean retorno = false;
        if (i == 0) {
            FormulasConceptos aux = listFormulasConceptosConcepto.get(indexFormulasConceptos);
            if ((!aux.getFormula().getNombrelargo().isEmpty())
                    && (!aux.getStrOrden().isEmpty())
                    && (aux.getFechafinal() != null) && (aux.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 1) {
            if ((!nuevaFormulasConceptos.getFormula().getNombrelargo().isEmpty())
                    && (!nuevaFormulasConceptos.getStrOrden().isEmpty())
                    && (nuevaFormulasConceptos.getFechafinal() != null) && (nuevaFormulasConceptos.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        if (i == 2) {
            if ((!duplicarFormulasConceptos.getFormula().getNombrelargo().isEmpty())
                    && (!duplicarFormulasConceptos.getStrOrden().isEmpty())
                    && (duplicarFormulasConceptos.getFechafinal() != null) && (duplicarFormulasConceptos.getFechainicial() != null)) {
                /////
                return true;
            }
        }
        return retorno;
    }

    public void agregarNuevoVigenciaCuenta() {
        boolean resp = validarNuevosDatosVigenciaCuenta(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(1);
            if (validacion == true) {
                if (banderaVigenciaCuenta == 1) {
                    vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
                    vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
                    vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
                    vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
                    vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
                    vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
                    vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
                    vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
                    vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
                    vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
                    banderaVigenciaCuenta = 0;
                    filtrarListVigenciasCuentasConcepto = null;
                    tipoListaVigenciaCuenta = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaCuenta.setSecuencia(var);
                nuevaVigenciaCuenta.setConcepto(conceptoActual);
                listVigenciasCuentasCrear.add(nuevaVigenciaCuenta);
                listVigenciasCuentasConcepto.add(nuevaVigenciaCuenta);
                ////------////
                nuevaVigenciaCuenta = new VigenciasCuentas();
                nuevaVigenciaCuenta.setConsolidadorc(new CentrosCostos());
                nuevaVigenciaCuenta.setConsolidadord(new CentrosCostos());
                nuevaVigenciaCuenta.setCuentac(new Cuentas());
                nuevaVigenciaCuenta.setCuentad(new Cuentas());
                nuevaVigenciaCuenta.setTipocc(new TiposCentrosCostos());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroVigenciaCuenta.hide()");
                context.update("form:datosVigenciaCuenta");
                context.update("formularioDialogos:NuevoRegistroVigenciaCuenta");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosVigenciaCuenta = true;
                indexVigenciaCuenta = -1;
                secRegistroVigenciaCuenta = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
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
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
    }

    public void agregarNuevoVigenciaGrupoConcepto() {
        boolean resp = validarNuevosDatosVigenciaGrupoConcepto(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(1);
            if (validacion == true) {
                if (banderaVigenciaGrupoConcepto == 1) {
                    vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
                    vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
                    vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
                    vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
                    vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
                    banderaVigenciaGrupoConcepto = 0;
                    filtrarListVigenciasGruposConceptosConcepto = null;
                    tipoListaVigenciaGrupoConcepto = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaGrupoConcepto.setSecuencia(var);
                nuevaVigenciaGrupoConcepto.setConcepto(conceptoActual);
                listVigenciasGruposConceptosCrear.add(nuevaVigenciaGrupoConcepto);
                listVigenciasGruposConceptosConcepto.add(nuevaVigenciaGrupoConcepto);
                ////------////
                nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
                nuevaVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroGrupoConcepto.hide()");
                context.update("form:datosVigenciaGrupoConcepto");
                context.update("formularioDialogos:NuevoRegistroGrupoConcepto");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosVigenciaGrupoConcepto = true;
                indexVigenciaGrupoConcepto = -1;
                secRegistroVigenciaGrupoConcepto = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaGrupoConcepto() {
        nuevaVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        nuevaVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
        indexVigenciaGrupoConcepto = -1;
        secRegistroVigenciaGrupoConcepto = null;
    }

    public void agregarNuevoVigenciaConceptoTT() {
        boolean resp = validarNuevosDatosVigenciaConceptoTT(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoTT == 1) {
                    vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
                    vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
                    vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
                    vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
                    banderaVigenciaConceptoTT = 0;
                    filtrarListVigenciasConceptosTT = null;
                    tipoListaVigenciaConceptoTT = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoTT.setSecuencia(var);
                nuevaVigenciaConceptoTT.setConcepto(conceptoActual);
                listVigenciasConceptosTTCrear.add(nuevaVigenciaConceptoTT);
                listVigenciasConceptosTTConcepto.add(nuevaVigenciaConceptoTT);
                ////------////
                nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
                nuevaVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroVigenciaConceptoTT.hide()");
                context.update("form:datosVigenciaConceptoTT");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTT");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosVigenciaConceptoTT = true;
                indexVigenciaConceptoTT = -1;
                secRegistroVigenciaConceptoTT = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoTT() {
        nuevaVigenciaConceptoTT = new VigenciasConceptosTT();
        nuevaVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
        indexVigenciaConceptoTT = -1;
        secRegistroVigenciaConceptoTT = null;
    }

    public void agregarNuevoVigenciaConceptoTC() {
        boolean resp = validarNuevosDatosVigenciaConceptoTC(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoTC == 1) {
                    vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
                    vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
                    vigenciaCTCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
                    vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
                    banderaVigenciaConceptoTC = 0;
                    filtrarListVigenciasConceptosTC = null;
                    tipoListaVigenciaConceptoTC = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoTC.setSecuencia(var);
                nuevaVigenciaConceptoTC.setConcepto(conceptoActual);
                listVigenciasConceptosTCCrear.add(nuevaVigenciaConceptoTC);
                listVigenciasConceptosTCConcepto.add(nuevaVigenciaConceptoTC);
                ////------////
                nuevaVigenciaConceptoTC = new VigenciasConceptosTC();
                nuevaVigenciaConceptoTC.setTipocontrato(new TiposContratos());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroVigenciaConceptoTC.hide()");
                context.update("form:datosVigenciaConceptoTC");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoTC");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosVigenciaConceptoTC = true;
                indexVigenciaConceptoTC = -1;
                secRegistroVigenciaConceptoTC = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoTC() {
        nuevaVigenciaConceptoTC = new VigenciasConceptosTC();
        nuevaVigenciaConceptoTC.setTipocontrato(new TiposContratos());
        indexVigenciaConceptoTC = -1;
        secRegistroVigenciaConceptoTC = null;
    }

    public void agregarNuevoVigenciaConceptoRL() {
        boolean resp = validarNuevosDatosVigenciaConceptoRL(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(1);
            if (validacion == true) {
                if (banderaVigenciaConceptoRL == 1) {
                    vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
                    vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
                    vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
                    vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
                    vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
                    banderaVigenciaConceptoRL = 0;
                    filtrarListVigenciasConceptosRL = null;
                    tipoListaVigenciaConceptoRL = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaVigenciaConceptoRL.setSecuencia(var);
                nuevaVigenciaConceptoRL.setConcepto(conceptoActual);
                listVigenciasConceptosRLCrear.add(nuevaVigenciaConceptoRL);
                listVigenciasConceptosRLConcepto.add(nuevaVigenciaConceptoRL);
                ////------////
                nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
                nuevaVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroVigenciaConceptoRL.hide()");
                context.update("form:datosVigenciaConceptoRL");
                context.update("formularioDialogos:NuevoRegistroVigenciaConceptoRL");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosVigenciaConceptoRL = true;
                indexVigenciaConceptoRL = -1;
                secRegistroVigenciaConceptoRL = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoVigenciaConceptoRL() {
        nuevaVigenciaConceptoRL = new VigenciasConceptosRL();
        nuevaVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
        indexVigenciaConceptoRL = -1;
        secRegistroVigenciaConceptoRL = null;
    }

    public void agregarNuevoFormulasConceptos() {
        boolean resp = validarNuevosDatosFormulasConceptos(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulasConceptos(1);
            if (validacion == true) {
                if (banderaFormulasConceptos == 1) {
                    vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
                    vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
                    vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
                    vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
                    vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
                    banderaVigenciaConceptoRL = 0;
                    filtrarListVigenciasConceptosRL = null;
                    tipoListaVigenciaConceptoRL = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaFormulasConceptos.setSecuencia(var);
                nuevaFormulasConceptos.setConcepto(conceptoActual);
                listFormulasConceptosCrear.add(nuevaFormulasConceptos);
                listFormulasConceptosConcepto.add(nuevaFormulasConceptos);
                ////------////
                nuevaFormulasConceptos = new FormulasConceptos();
                nuevaFormulasConceptos.setFormula(new Formulas());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroFormulaConcepto.hide()");
                context.update("form:datosFormulaConcepto");
                context.update("formularioDialogos:NuevoRegistroFormulaConcepto");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosFormulasConceptos = true;
                indexFormulasConceptos = -1;
                secRegistroFormulasConceptos = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoFormulasConceptos() {
        nuevaFormulasConceptos = new FormulasConceptos();
        nuevaFormulasConceptos.setFormula(new Formulas());
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
    }

    public void duplicarVigenciaCuentaM() {

        if (indexVigenciaCuenta >= 0) {
            duplicarVigenciaCuenta = new VigenciasCuentas();
            if (tipoListaVigenciaCuenta == 0) {
                duplicarVigenciaCuenta.setConcepto(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConcepto());
                duplicarVigenciaCuenta.setConsolidadorc(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadorc());
                duplicarVigenciaCuenta.setConsolidadord(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadord());
                duplicarVigenciaCuenta.setCuentac(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac());
                duplicarVigenciaCuenta.setCuentad(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad());
                duplicarVigenciaCuenta.setFechafinal(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechafinal());
                duplicarVigenciaCuenta.setFechainicial(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechainicial());
                duplicarVigenciaCuenta.setTipocc(listVigenciasCuentasConcepto.get(indexVigenciaCuenta).getTipocc());
            }
            if (tipoListaVigenciaCuenta == 1) {
                duplicarVigenciaCuenta.setConcepto(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConcepto());
                duplicarVigenciaCuenta.setConsolidadorc(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadorc());
                duplicarVigenciaCuenta.setConsolidadord(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getConsolidadord());
                duplicarVigenciaCuenta.setCuentac(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentac());
                duplicarVigenciaCuenta.setCuentad(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getCuentad());
                duplicarVigenciaCuenta.setFechafinal(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechafinal());
                duplicarVigenciaCuenta.setFechainicial(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getFechainicial());
                duplicarVigenciaCuenta.setTipocc(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).getTipocc());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaCuenta");
            context.execute("DuplicarRegistroVigenciaCuenta.show()");

        }
    }

    public void duplicarVigenciaGrupoConceptoM() {
        if (indexVigenciaGrupoConcepto >= 0) {
            duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
            if (tipoListaVigenciaGrupoConcepto == 0) {
                duplicarVigenciaGrupoConcepto.setConcepto(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getConcepto());
                duplicarVigenciaGrupoConcepto.setGrupoconcepto(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto());
                duplicarVigenciaGrupoConcepto.setFechafinal(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechafinal());
                duplicarVigenciaGrupoConcepto.setFechainicial(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechainicial());
            }
            if (tipoListaVigenciaGrupoConcepto == 1) {
                duplicarVigenciaGrupoConcepto.setConcepto(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getConcepto());
                duplicarVigenciaGrupoConcepto.setGrupoconcepto(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getGrupoconcepto());
                duplicarVigenciaGrupoConcepto.setFechafinal(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechafinal());
                duplicarVigenciaGrupoConcepto.setFechainicial(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).getFechainicial());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaGrupoConcepto");
            context.execute("DuplicarRegistroVigenciaGrupoConcepto.show()");

        }
    }

    public void duplicarVigenciaConceptoTTM() {
        if (indexVigenciaConceptoTT >= 0) {
            duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
            if (tipoListaVigenciaConceptoTT == 0) {
                duplicarVigenciaConceptoTT.setConcepto(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getConcepto());
                duplicarVigenciaConceptoTT.setTipotrabajador(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getTipotrabajador());
                duplicarVigenciaConceptoTT.setFechafinal(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getFechafinal());
                duplicarVigenciaConceptoTT.setFechainicial(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).getFechainicial());
            }
            if (tipoListaVigenciaConceptoTT == 1) {
                duplicarVigenciaConceptoTT.setConcepto(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).getConcepto());
                duplicarVigenciaConceptoTT.setTipotrabajador(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).getTipotrabajador());
                duplicarVigenciaConceptoTT.setFechafinal(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).getFechafinal());
                duplicarVigenciaConceptoTT.setFechainicial(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).getFechainicial());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoTT");
            context.execute("DuplicarRegistroVigenciaConceptoTT.show()");

        }
    }

    public void duplicarVigenciaConceptoTCM() {
        if (indexVigenciaConceptoTC >= 0) {
            duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
            if (tipoListaVigenciaConceptoTC == 0) {
                duplicarVigenciaConceptoTC.setConcepto(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getConcepto());
                duplicarVigenciaConceptoTC.setTipocontrato(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getTipocontrato());
                duplicarVigenciaConceptoTC.setFechafinal(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getFechafinal());
                duplicarVigenciaConceptoTC.setFechainicial(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).getFechainicial());
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                duplicarVigenciaConceptoTC.setConcepto(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getConcepto());
                duplicarVigenciaConceptoTC.setTipocontrato(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getTipocontrato());
                duplicarVigenciaConceptoTC.setFechafinal(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getFechafinal());
                duplicarVigenciaConceptoTC.setFechainicial(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).getFechainicial());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoTC");
            context.execute("DuplicarRegistroVigenciaConceptoTC.show()");

        }
    }

    public void duplicarVigenciaConceptoRLM() {
        if (indexVigenciaConceptoRL >= 0) {
            duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
            if (tipoListaVigenciaConceptoRL == 0) {
                duplicarVigenciaConceptoRL.setConcepto(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getConcepto());
                duplicarVigenciaConceptoRL.setTiposalario(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getTiposalario());
                duplicarVigenciaConceptoRL.setFechafinal(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getFechafinal());
                duplicarVigenciaConceptoRL.setFechainicial(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).getFechainicial());
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                duplicarVigenciaConceptoRL.setConcepto(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getConcepto());
                duplicarVigenciaConceptoRL.setTiposalario(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getTiposalario());
                duplicarVigenciaConceptoRL.setFechafinal(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getFechafinal());
                duplicarVigenciaConceptoRL.setFechainicial(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).getFechainicial());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaConceptoRL");
            context.execute("DuplicarRegistroVigenciaConceptoRL.show()");

        }
    }

    public void duplicarFormulasConceptosM() {
        if (indexFormulasConceptos >= 0) {
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            duplicarFormulasConceptos = new FormulasConceptos();
            if (tipoListaFormulasConceptos == 0) {
                duplicarFormulasConceptos.setConcepto(listFormulasConceptosConcepto.get(indexFormulasConceptos).getConcepto());
                duplicarFormulasConceptos.setFormula(listFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula());
                duplicarFormulasConceptos.setFechafinal(listFormulasConceptosConcepto.get(indexFormulasConceptos).getFechafinal());
                duplicarFormulasConceptos.setFechainicial(listFormulasConceptosConcepto.get(indexFormulasConceptos).getFechainicial());
                duplicarFormulasConceptos.setStrOrden(listFormulasConceptosConcepto.get(indexFormulasConceptos).getStrOrden());
            }
            if (tipoListaFormulasConceptos == 1) {
                duplicarFormulasConceptos.setConcepto(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getConcepto());
                duplicarFormulasConceptos.setFormula(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFormula());
                duplicarFormulasConceptos.setFechafinal(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFechafinal());
                duplicarFormulasConceptos.setFechainicial(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFechainicial());
                duplicarFormulasConceptos.setFechainicial(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).getFechainicial());
            }

            context.update("formularioDialogos:DuplicarRegistroFormulaConcepto");
            context.execute("DuplicarRegistroFormulaConcepto.show()");

        }
    }

    public void confirmarDuplicarVigenciaCuenta() {
        boolean resp = validarNuevosDatosVigenciaCuenta(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaCuenta(2);
            if (validacion == true) {
                if (indexVigenciaCuenta >= 0) {
                    if (banderaVigenciaCuenta == 1) {
                        vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
                        vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
                        vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
                        vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
                        vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
                        vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
                        vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
                        vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
                        vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
                        vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
                        banderaVigenciaCuenta = 0;
                        filtrarListVigenciasCuentasConcepto = null;
                        tipoListaVigenciaCuenta = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarVigenciaCuenta.setSecuencia(var);
                    duplicarVigenciaCuenta.setConcepto(conceptoActual);
                    listVigenciasCuentasCrear.add(duplicarVigenciaCuenta);
                    listVigenciasCuentasConcepto.add(duplicarVigenciaCuenta);
                    duplicarVigenciaCuenta = new VigenciasCuentas();

                    duplicarVigenciaCuenta = new VigenciasCuentas();
                    duplicarVigenciaCuenta.setConsolidadorc(new CentrosCostos());
                    duplicarVigenciaCuenta.setConsolidadord(new CentrosCostos());
                    duplicarVigenciaCuenta.setCuentac(new Cuentas());
                    duplicarVigenciaCuenta.setCuentad(new Cuentas());
                    duplicarVigenciaCuenta.setTipocc(new TiposCentrosCostos());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosVigenciaGrupoConcepto");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroVigenciaCuenta.hide()");
                    cambiosVigenciaCuenta = true;
                    indexVigenciaCuenta = -1;
                    secRegistroVigenciaCuenta = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
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
        boolean resp = validarNuevosDatosVigenciaGrupoConcepto(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaGrupoConcepto(2);
            if (validacion == true) {
                if (indexVigenciaGrupoConcepto >= 0) {
                    if (banderaVigenciaGrupoConcepto == 1) {
                        vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
                        vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
                        vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
                        vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
                        vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
                        banderaVigenciaGrupoConcepto = 0;
                        filtrarListVigenciasGruposConceptosConcepto = null;
                        tipoListaVigenciaGrupoConcepto = 0;
                    }

                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarVigenciaGrupoConcepto.setSecuencia(var);
                    duplicarVigenciaGrupoConcepto.setConcepto(conceptoActual);
                    listVigenciasGruposConceptosCrear.add(duplicarVigenciaGrupoConcepto);
                    listVigenciasGruposConceptosConcepto.add(duplicarVigenciaGrupoConcepto);

                    duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
                    duplicarVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosVigenciaGrupoConcepto");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroVigenciaGrupoConcepto.hide()");
                    cambiosVigenciaGrupoConcepto = true;
                    indexVigenciaGrupoConcepto = -1;
                    secRegistroVigenciaGrupoConcepto = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaGrupoConcepto() {
        duplicarVigenciaGrupoConcepto = new VigenciasGruposConceptos();
        duplicarVigenciaGrupoConcepto.setGrupoconcepto(new GruposConceptos());
    }

    public void confirmarDuplicarVigenciaConceptoTT() {
        boolean resp = validarNuevosDatosVigenciaConceptoTT(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTT(2);
            if (validacion == true) {
                if (indexVigenciaConceptoTT >= 0) {
                    if (banderaVigenciaConceptoTT == 1) {
                        vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
                        vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
                        vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
                        vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
                        banderaVigenciaConceptoTT = 0;
                        filtrarListVigenciasConceptosTT = null;
                        tipoListaVigenciaConceptoTT = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarVigenciaConceptoTT.setSecuencia(var);
                    duplicarVigenciaConceptoTT.setConcepto(conceptoActual);
                    listVigenciasConceptosTTCrear.add(duplicarVigenciaConceptoTT);
                    listVigenciasConceptosTTConcepto.add(duplicarVigenciaConceptoTT);

                    duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
                    duplicarVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosVigenciaConceptoTT");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoTT.hide()");
                    cambiosVigenciaConceptoTT = true;
                    indexVigenciaConceptoTT = -1;
                    secRegistroVigenciaConceptoTT = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoTT() {
        duplicarVigenciaConceptoTT = new VigenciasConceptosTT();
        duplicarVigenciaConceptoTT.setTipotrabajador(new TiposTrabajadores());
    }

    public void confirmarDuplicarVigenciaConceptoTC() {
        boolean resp = validarNuevosDatosVigenciaConceptoTC(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoTC(2);
            if (validacion == true) {
                if (indexVigenciaConceptoTC >= 0) {
                    if (banderaVigenciaConceptoTC == 1) {
                        vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
                        vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
                        vigenciaCTCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
                        vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
                        banderaVigenciaConceptoTC = 0;
                        filtrarListVigenciasConceptosTC = null;
                        tipoListaVigenciaConceptoTC = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarVigenciaConceptoTC.setSecuencia(var);
                    duplicarVigenciaConceptoTC.setConcepto(conceptoActual);
                    listVigenciasConceptosTCCrear.add(duplicarVigenciaConceptoTC);
                    listVigenciasConceptosTCConcepto.add(duplicarVigenciaConceptoTC);

                    duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
                    duplicarVigenciaConceptoTC.setTipocontrato(new TiposContratos());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosVigenciaConceptoTC");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoTC.hide()");
                    cambiosVigenciaConceptoTC = true;
                    indexVigenciaConceptoTC = -1;
                    secRegistroVigenciaConceptoTC = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoTC() {
        duplicarVigenciaConceptoTC = new VigenciasConceptosTC();
        duplicarVigenciaConceptoTC.setTipocontrato(new TiposContratos());
    }

    public void confirmarDuplicarVigenciaConceptoRL() {
        boolean resp = validarNuevosDatosVigenciaConceptoRL(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroVigenciaConceptoRL(2);
            if (validacion == true) {
                if (indexVigenciaConceptoRL >= 0) {
                    if (banderaVigenciaConceptoRL == 1) {
                        vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
                        vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
                        vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
                        vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
                        vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
                        banderaVigenciaConceptoRL = 0;
                        filtrarListVigenciasConceptosRL = null;
                        tipoListaVigenciaConceptoRL = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarVigenciaConceptoRL.setSecuencia(var);
                    duplicarVigenciaConceptoRL.setConcepto(conceptoActual);
                    listVigenciasConceptosRLCrear.add(duplicarVigenciaConceptoRL);
                    listVigenciasConceptosRLConcepto.add(duplicarVigenciaConceptoRL);

                    duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
                    duplicarVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosVigenciaConceptoRL");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroVigenciaConceptoRL.hide()");
                    cambiosVigenciaConceptoRL = true;
                    indexVigenciaConceptoRL = -1;
                    secRegistroVigenciaConceptoRL = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarVigenciaConceptoRL() {
        duplicarVigenciaConceptoRL = new VigenciasConceptosRL();
        duplicarVigenciaConceptoRL.setTiposalario(new ReformasLaborales());
    }

    public void confirmarDuplicarFormulasConceptos() {
        boolean resp = validarNuevosDatosFormulasConceptos(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulasConceptos(2);
            if (validacion == true) {
                if (indexFormulasConceptos >= 0) {
                    if (banderaFormulasConceptos == 1) {
                        formulaCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
                        formulaCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        formulaCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
                        formulaCFechaFinal.setFilterStyle("display: none; visibililty: hidden;");
                        formulaCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
                        formulaCNombre.setFilterStyle("display: none; visibility: hidden;");
                        formulaCOrden = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
                        formulaCOrden.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
                        banderaFormulasConceptos = 0;
                        filtrarListFormulasConceptosConcepto = null;
                        tipoListaFormulasConceptos = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarFormulasConceptos.setSecuencia(var);
                    duplicarFormulasConceptos.setConcepto(conceptoActual);
                    listFormulasConceptosCrear.add(duplicarFormulasConceptos);
                    listFormulasConceptosConcepto.add(duplicarFormulasConceptos);

                    duplicarFormulasConceptos = new FormulasConceptos();
                    duplicarFormulasConceptos.setFormula(new Formulas());
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosFormulaConcepto");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    formulaSeleccionada = true;

                    context.update("form:detalleFormula");
                    context.execute("DuplicarRegistroFormulaConcepto.hide()");
                    cambiosFormulasConceptos = true;
                    indexFormulasConceptos = -1;
                    secRegistroFormulasConceptos = null;
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarFormulasConceptos() {
        duplicarFormulasConceptos = new FormulasConceptos();
        duplicarFormulasConceptos.setFormula(new Formulas());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarVigenciaCuenta() {

        if (indexVigenciaCuenta >= 0) {
            if (tipoListaVigenciaCuenta == 0) {
                if (!listVigenciasCuentasModificar.isEmpty() && listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    int modIndex = listVigenciasCuentasModificar.indexOf(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    listVigenciasCuentasModificar.remove(modIndex);
                    listVigenciasCuentasBorrar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                } else if (!listVigenciasCuentasCrear.isEmpty() && listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    int crearIndex = listVigenciasCuentasCrear.indexOf(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    listVigenciasCuentasCrear.remove(crearIndex);
                } else {
                    listVigenciasCuentasBorrar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                }
                listVigenciasCuentasConcepto.remove(indexVigenciaCuenta);
            }
            if (tipoListaVigenciaCuenta == 1) {
                if (!listVigenciasCuentasModificar.isEmpty() && listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    int modIndex = listVigenciasCuentasModificar.indexOf(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    listVigenciasCuentasModificar.remove(modIndex);
                    listVigenciasCuentasBorrar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                } else if (!listVigenciasCuentasCrear.isEmpty() && listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    int crearIndex = listVigenciasCuentasCrear.indexOf(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    listVigenciasCuentasCrear.remove(crearIndex);
                } else {
                    listVigenciasCuentasBorrar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                }
                int VLIndex = listVigenciasCuentasConcepto.indexOf(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                listVigenciasCuentasConcepto.remove(VLIndex);
                filtrarListVigenciasCuentasConcepto.remove(indexVigenciaCuenta);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCuenta");
            indexVigenciaCuenta = -1;
            secRegistroVigenciaCuenta = null;
            cambiosVigenciaCuenta = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    public void borrarVigenciaGrupoConcepto() {
        if (indexVigenciaGrupoConcepto >= 0) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                if (!listVigenciasGruposConceptosModificar.isEmpty() && listVigenciasGruposConceptosModificar.contains(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    int modIndex = listVigenciasGruposConceptosModificar.indexOf(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    listVigenciasGruposConceptosModificar.remove(modIndex);
                    listVigenciasGruposConceptosBorrar.add(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                } else if (!listVigenciasGruposConceptosCrear.isEmpty() && listVigenciasGruposConceptosCrear.contains(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    int crearIndex = listVigenciasGruposConceptosCrear.indexOf(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    listVigenciasGruposConceptosCrear.remove(crearIndex);
                } else {
                    listVigenciasGruposConceptosBorrar.add(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                }
                listVigenciasGruposConceptosConcepto.remove(indexVigenciaGrupoConcepto);
            }
            if (tipoListaVigenciaGrupoConcepto == 1) {
                if (!listVigenciasGruposConceptosModificar.isEmpty() && listVigenciasGruposConceptosModificar.contains(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    int modIndex = listVigenciasGruposConceptosModificar.indexOf(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    listVigenciasGruposConceptosModificar.remove(modIndex);
                    listVigenciasGruposConceptosBorrar.add(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                } else if (!listVigenciasGruposConceptosCrear.isEmpty() && listVigenciasGruposConceptosCrear.contains(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    int crearIndex = listVigenciasGruposConceptosCrear.indexOf(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    listVigenciasGruposConceptosCrear.remove(crearIndex);
                } else {
                    listVigenciasGruposConceptosBorrar.add(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                }
                int VLIndex = listVigenciasGruposConceptosModificar.indexOf(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                listVigenciasGruposConceptosModificar.remove(VLIndex);
                filtrarListVigenciasGruposConceptosConcepto.remove(indexVigenciaGrupoConcepto);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoConcepto");
            indexVigenciaGrupoConcepto = -1;
            secRegistroVigenciaGrupoConcepto = null;
            cambiosVigenciaGrupoConcepto = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    public void borrarVigenciaConceptoTT() {
        if (indexVigenciaConceptoTT >= 0) {
            if (tipoListaVigenciaConceptoTT == 0) {
                if (!listVigenciasConceptosTTModificar.isEmpty() && listVigenciasConceptosTTModificar.contains(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT))) {
                    int modIndex = listVigenciasConceptosTTModificar.indexOf(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                    listVigenciasConceptosTTModificar.remove(modIndex);
                    listVigenciasConceptosTTBorrar.add(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                } else if (!listVigenciasConceptosTTCrear.isEmpty() && listVigenciasConceptosTTCrear.contains(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT))) {
                    int crearIndex = listVigenciasConceptosTTCrear.indexOf(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                    listVigenciasConceptosTTCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosTTBorrar.add(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                }
                listVigenciasConceptosTTConcepto.remove(indexVigenciaConceptoTT);
            }
            if (tipoListaVigenciaConceptoTT == 1) {
                if (!listVigenciasConceptosTTModificar.isEmpty() && listVigenciasConceptosTTModificar.contains(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT))) {
                    int modIndex = listVigenciasConceptosTTModificar.indexOf(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                    listVigenciasConceptosTTModificar.remove(modIndex);
                    listVigenciasConceptosTTBorrar.add(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                } else if (!listVigenciasConceptosTTCrear.isEmpty() && listVigenciasConceptosTTCrear.contains(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT))) {
                    int crearIndex = listVigenciasConceptosTTCrear.indexOf(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                    listVigenciasConceptosTTCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosTTBorrar.add(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                }
                int VLIndex = listVigenciasConceptosTTConcepto.indexOf(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                listVigenciasConceptosTTConcepto.remove(VLIndex);
                filtrarListVigenciasConceptosTT.remove(indexVigenciaConceptoTT);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTT");
            indexVigenciaConceptoTT = -1;
            secRegistroVigenciaConceptoTT = null;
            cambiosVigenciaConceptoTT = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    public void borrarVigenciaConceptoTC() {
        if (indexVigenciaConceptoTC >= 0) {
            if (tipoListaVigenciaConceptoTC == 0) {
                if (!listVigenciasConceptosTCModificar.isEmpty() && listVigenciasConceptosTCModificar.contains(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC))) {
                    int modIndex = listVigenciasConceptosTCModificar.indexOf(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                    listVigenciasConceptosTCModificar.remove(modIndex);
                    listVigenciasConceptosTCBorrar.add(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                } else if (!listVigenciasConceptosTCCrear.isEmpty() && listVigenciasConceptosTCCrear.contains(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC))) {
                    int crearIndex = listVigenciasConceptosTCCrear.indexOf(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                    listVigenciasConceptosTCCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosTCBorrar.add(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                }
                listVigenciasConceptosTCConcepto.remove(indexVigenciaConceptoTC);
            }
            if (tipoListaVigenciaConceptoTC == 1) {
                if (!listVigenciasConceptosTCModificar.isEmpty() && listVigenciasConceptosTCModificar.contains(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC))) {
                    int modIndex = listVigenciasConceptosTCModificar.indexOf(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                    listVigenciasConceptosTCModificar.remove(modIndex);
                    listVigenciasConceptosTCBorrar.add(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                } else if (!listVigenciasConceptosTCCrear.isEmpty() && listVigenciasConceptosTCCrear.contains(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC))) {
                    int crearIndex = listVigenciasConceptosTCCrear.indexOf(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                    listVigenciasConceptosTCCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosTCBorrar.add(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                }
                int VLIndex = listVigenciasConceptosTCConcepto.indexOf(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                listVigenciasConceptosTCConcepto.remove(VLIndex);
                filtrarListVigenciasConceptosTC.remove(indexVigenciaConceptoTC);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoTC");
            indexVigenciaConceptoTC = -1;
            secRegistroVigenciaConceptoTC = null;
            cambiosVigenciaConceptoTC = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    public void borrarVigenciaConceptoRL() {
        if (indexVigenciaConceptoRL >= 0) {
            if (tipoListaVigenciaConceptoRL == 0) {
                if (!listVigenciasConceptosRLModificar.isEmpty() && listVigenciasConceptosRLModificar.contains(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL))) {
                    int modIndex = listVigenciasConceptosRLModificar.indexOf(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                    listVigenciasConceptosRLModificar.remove(modIndex);
                    listVigenciasConceptosRLBorrar.add(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                } else if (!listVigenciasConceptosRLCrear.isEmpty() && listVigenciasConceptosRLCrear.contains(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL))) {
                    int crearIndex = listVigenciasConceptosRLCrear.indexOf(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                    listVigenciasConceptosRLCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosRLBorrar.add(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                }
                listVigenciasConceptosRLConcepto.remove(indexVigenciaConceptoRL);
            }
            if (tipoListaVigenciaConceptoRL == 1) {
                if (!listVigenciasConceptosRLModificar.isEmpty() && listVigenciasConceptosRLModificar.contains(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL))) {
                    int modIndex = listVigenciasConceptosRLModificar.indexOf(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                    listVigenciasConceptosRLModificar.remove(modIndex);
                    listVigenciasConceptosRLBorrar.add(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                } else if (!listVigenciasConceptosRLCrear.isEmpty() && listVigenciasConceptosRLCrear.contains(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL))) {
                    int crearIndex = listVigenciasConceptosRLCrear.indexOf(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                    listVigenciasConceptosRLCrear.remove(crearIndex);
                } else {
                    listVigenciasConceptosRLBorrar.add(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                }
                int VLIndex = listVigenciasConceptosRLConcepto.indexOf(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                listVigenciasConceptosRLConcepto.remove(VLIndex);
                filtrarListVigenciasConceptosRL.remove(indexVigenciaConceptoRL);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaConceptoRL");
            indexVigenciaConceptoRL = -1;
            secRegistroVigenciaConceptoRL = null;
            cambiosVigenciaConceptoRL = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    public void borrarFormulasConceptos() {
        if (indexFormulasConceptos >= 0) {
            if (tipoListaFormulasConceptos == 0) {
                if (!listFormulasConceptosModificar.isEmpty() && listFormulasConceptosModificar.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    int modIndex = listFormulasConceptosModificar.indexOf(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    listFormulasConceptosModificar.remove(modIndex);
                    listFormulasConceptosBorrar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                } else if (!listFormulasConceptosCrear.isEmpty() && listFormulasConceptosCrear.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    int crearIndex = listFormulasConceptosCrear.indexOf(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    listFormulasConceptosCrear.remove(crearIndex);
                } else {
                    listFormulasConceptosBorrar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                }
                listFormulasConceptosConcepto.remove(indexFormulasConceptos);
            }
            if (tipoListaFormulasConceptos == 1) {
                if (!listFormulasConceptosModificar.isEmpty() && listFormulasConceptosModificar.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    int modIndex = listFormulasConceptosModificar.indexOf(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    listFormulasConceptosModificar.remove(modIndex);
                    listFormulasConceptosBorrar.add(filtrarListFormulasConceptosConcepto.get(indexVigenciaConceptoRL));
                } else if (!listFormulasConceptosCrear.isEmpty() && listFormulasConceptosCrear.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    int crearIndex = listFormulasConceptosCrear.indexOf(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    listFormulasConceptosCrear.remove(crearIndex);
                } else {
                    listFormulasConceptosBorrar.add(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                }
                int VLIndex = listFormulasConceptosConcepto.indexOf(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                listFormulasConceptosConcepto.remove(VLIndex);
                filtrarListFormulasConceptosConcepto.remove(indexFormulasConceptos);
            }
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaConcepto");
            context.update("form:detalleFormula");
            indexFormulasConceptos = -1;
            secRegistroFormulasConceptos = null;
            cambiosFormulasConceptos = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (indexVigenciaCuenta >= 0) {
            filtradoVigenciaCuenta();
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            filtradoVigenciaGrupoConcepto();
        }
        if (indexVigenciaConceptoTT >= 0) {
            filtradoVigenciaConceptoTT();
        }
        if (indexVigenciaConceptoTC >= 0) {
            filtradoVigenciaConceptoTC();
        }
        if (indexVigenciaConceptoRL >= 0) {
            filtradoVigenciaConceptoRL();
        }
        if (indexFormulasConceptos >= 0) {
            filtradoFormulasConceptos();
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    /**
     */
    public void filtradoVigenciaCuenta() {
        if (banderaVigenciaCuenta == 0) {

            vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
            vigenciaCuentaFechaInicial.setFilterStyle("width: 60px");
            vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
            vigenciaCuentaFechaFinal.setFilterStyle("width: 60px");
            vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
            vigenciaCuentaTipoCC.setFilterStyle("width: 80px");
            vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
            vigenciaCuentaDebitoCod.setFilterStyle("width: 80px");
            vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
            vigenciaCuentaDebitoDes.setFilterStyle("width: 80px");
            vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
            vigenciaCuentaCCConsolidadorD.setFilterStyle("width: 80px");
            vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
            vigenciaCuentaCreditoCod.setFilterStyle("width: 80px");
            vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
            vigenciaCuentaCreditoDes.setFilterStyle("width: 80px");
            vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
            vigenciaCuentaCCConsolidadorC.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
            banderaVigenciaCuenta = 1;
        } else if (banderaVigenciaCuenta == 1) {
            vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
            vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
            vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
            vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
            vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
            vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
            vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
            vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
            vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
            vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
            banderaVigenciaCuenta = 0;
            filtrarListVigenciasCuentasConcepto = null;
            tipoListaVigenciaCuenta = 0;
        }
    }

    public void filtradoVigenciaGrupoConcepto() {
        if (banderaVigenciaGrupoConcepto == 0) {
            vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
            vigenciaGCFechaInicial.setFilterStyle("width: 60px");
            vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
            vigenciaGCFechaFinal.setFilterStyle("width: 60px");
            vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
            vigenciaGCCodigo.setFilterStyle("width: 60px");
            vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
            vigenciaGCDescripcion.setFilterStyle("width: 250px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
            banderaVigenciaGrupoConcepto = 1;
        } else if (banderaVigenciaGrupoConcepto == 1) {
            vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
            vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
            vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
            vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
            vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
            banderaVigenciaGrupoConcepto = 0;
            filtrarListVigenciasGruposConceptosConcepto = null;
            tipoListaVigenciaGrupoConcepto = 0;
        }
    }

    public void filtradoVigenciaConceptoTT() {
        if (banderaVigenciaConceptoTT == 0) {
            vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
            vigenciaCTTFechaFinal.setFilterStyle("width: 60px");
            vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
            vigenciaCTTFechaInicial.setFilterStyle("width: 60px");
            vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
            vigenciaCTTDescripcion.setFilterStyle("width: 250px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
            banderaVigenciaConceptoTT = 1;
        } else if (banderaVigenciaConceptoTT == 1) {
            vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
            vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
            vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
            vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
            banderaVigenciaConceptoTT = 0;
            filtrarListVigenciasConceptosTT = null;
            tipoListaVigenciaConceptoTT = 0;
        }
    }

    public void filtradoVigenciaConceptoTC() {
        if (banderaVigenciaConceptoTC == 0) {
            vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
            vigenciaCTCFechaFinal.setFilterStyle("width: 60px");
            vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
            vigenciaCTCFechaInicial.setFilterStyle("width: 60px");
            vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
            vigenciaCTCDescripcion.setFilterStyle("width: 250px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
            banderaVigenciaConceptoTC = 1;
        } else if (banderaVigenciaConceptoTC == 1) {
            vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
            vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
            vigenciaCTCFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
            vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
            vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
            banderaVigenciaConceptoTC = 0;
            filtrarListVigenciasConceptosTC = null;
            tipoListaVigenciaConceptoTC = 0;
        }
    }

    public void filtradoVigenciaConceptoRL() {
        if (banderaVigenciaConceptoRL == 0) {
            vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
            vigenciaCRLFechaFinal.setFilterStyle("width: 60px");
            vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
            vigenciaCRLFechaInicial.setFilterStyle("width: 60px");
            vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
            vigenciaCRLDescripcion.setFilterStyle("width: 250px");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
            banderaVigenciaConceptoRL = 1;
        } else if (banderaVigenciaConceptoRL == 1) {
            vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
            vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
            vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
            vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
            vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
            banderaVigenciaConceptoRL = 0;
            filtrarListVigenciasConceptosRL = null;
            tipoListaVigenciaConceptoRL = 0;
        }
    }

    public void filtradoFormulasConceptos() {
        if (banderaFormulasConceptos == 0) {
            formulaCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
            formulaCFechaInicial.setFilterStyle("width: 60px");
            formulaCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
            formulaCFechaFinal.setFilterStyle("width: 60px");
            formulaCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
            formulaCNombre.setFilterStyle("width: 200px");
            formulaCOrden = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
            formulaCOrden.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
            banderaFormulasConceptos = 1;
        } else if (banderaFormulasConceptos == 1) {
            formulaCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
            formulaCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
            formulaCFechaFinal.setFilterStyle("display: none; visibililty: hidden;");
            formulaCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
            formulaCNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaCOrden = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
            formulaCOrden.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
            banderaFormulasConceptos = 0;
            filtrarListFormulasConceptosConcepto = null;
            tipoListaFormulasConceptos = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaVigenciaCuenta == 1) {
            vigenciaCuentaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaInicial");
            vigenciaCuentaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaFechaFinal");
            vigenciaCuentaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaTipoCC");
            vigenciaCuentaTipoCC.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoCod");
            vigenciaCuentaDebitoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaDebitoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaDebitoDes");
            vigenciaCuentaDebitoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorD");
            vigenciaCuentaCCConsolidadorD.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoCod = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoCod");
            vigenciaCuentaCreditoCod.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCreditoDes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCreditoDes");
            vigenciaCuentaCreditoDes.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCuentaCCConsolidadorC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCuenta:vigenciaCuentaCCConsolidadorC");
            vigenciaCuentaCCConsolidadorC.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosVigenciaCuenta");
            banderaVigenciaCuenta = 0;
            filtrarListVigenciasCuentasConcepto = null;
            tipoListaVigenciaCuenta = 0;
        }
        if (banderaVigenciaGrupoConcepto == 1) {
            vigenciaGCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaInicial");
            vigenciaGCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCFechaFinal");
            vigenciaGCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCCodigo");
            vigenciaGCCodigo.setFilterStyle("display: none; visibility: hidden;");
            vigenciaGCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoConcepto:vigenciaGCDescripcion");
            vigenciaGCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoConcepto");
            banderaVigenciaGrupoConcepto = 0;
            filtrarListVigenciasGruposConceptosConcepto = null;
            tipoListaVigenciaGrupoConcepto = 0;
        }
        if (banderaVigenciaConceptoTT == 1) {
            vigenciaCTTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaFinal");
            vigenciaCTTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTFechaInicial");
            vigenciaCTTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTTDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTT:vigenciaCTTDescripcion");
            vigenciaCTTDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTT");
            banderaVigenciaConceptoTT = 0;
            filtrarListVigenciasConceptosTT = null;
            tipoListaVigenciaConceptoTT = 0;
        }
        if (banderaVigenciaConceptoTC == 1) {
            vigenciaCTCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaFinal");
            vigenciaCTCFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCFechaInicial");
            vigenciaCTCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCTCDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoTC:vigenciaCTCDescripcion");
            vigenciaCTCDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoTC");
            banderaVigenciaConceptoTC = 0;
            filtrarListVigenciasConceptosTC = null;
            tipoListaVigenciaConceptoTC = 0;
        }
        if (banderaVigenciaConceptoRL == 1) {
            vigenciaCRLFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaFinal");
            vigenciaCRLFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vigenciaCRLFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLFechaInicial");
            vigenciaCRLFechaInicial.setFilterStyle("display: none; visibililty: hidden;");
            vigenciaCRLDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaConceptoRL:vigenciaCRLDescripcion");
            vigenciaCRLDescripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaConceptoRL");
            banderaVigenciaConceptoRL = 0;
            filtrarListVigenciasConceptosRL = null;
            tipoListaVigenciaConceptoRL = 0;
        }

        if (banderaFormulasConceptos == 1) {
            formulaCFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaInicial");
            formulaCFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaCFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCFechaFinal");
            formulaCFechaFinal.setFilterStyle("display: none; visibililty: hidden;");
            formulaCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCNombre");
            formulaCNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaCOrden = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaConcepto:formulaCOrden");
            formulaCOrden.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaConcepto");
            banderaFormulasConceptos = 0;
            filtrarListFormulasConceptosConcepto = null;
            tipoListaFormulasConceptos = 0;
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

        indexVigenciaCuenta = -1;
        indexVigenciaGrupoConcepto = -1;
        indexVigenciaConceptoTT = -1;
        indexVigenciaConceptoTC = -1;
        indexVigenciaConceptoRL = -1;
        indexFormulasConceptos = -1;

        secRegistroVigenciaCuenta = null;
        secRegistroVigenciaGrupoConcepto = null;
        secRegistroVigenciaConceptoTT = null;
        secRegistroVigenciaConceptoRL = null;
        secRegistroVigenciaConceptoTC = null;
        secRegistroFormulasConceptos = null;

        conceptoActual = null;
        k = 0;

        indexAuxVigenciaCuenta = -1;
        indexAuxVigenciaGrupoConcepto = -1;
        indexAuxVigenciaConceptoTT = -1;
        indexAuxVigenciaConceptoTC = -1;
        indexAuxVigenciaConceptoRL = -1;
        indexAuxFormulasConceptos = -1;

        listVigenciasCuentasConcepto = null;
        listVigenciasGruposConceptosConcepto = null;
        listVigenciasConceptosTTConcepto = null;
        listVigenciasConceptosTCConcepto = null;
        listVigenciasConceptosRLConcepto = null;
        listFormulasConceptosConcepto = null;

        guardado = true;

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

        listTiposContratos = null;
        listTiposTrabajadores = null;
        listGruposConceptos = null;
        listCentrosCostos = null;
        listCuentas = null;
        listTiposCentrosCostos = null;
        listReformasLaborales = null;
        listFormulas = null;
        listFormulasConceptos = null;

        formulaSeleccionada = true;
        paginaRetorno = "";
    }

    public void actualizarTipoCentroCosto() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setTipocc(tipoCentroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            } else {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setTipocc(tipoCentroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTipoCCVC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setTipocc(tipoCentroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTipoCCVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setTipocc(tipoCentroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTipoCCVC");
        }
        filtrarListTiposCentrosCostos = null;
        tipoCentroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioTipoCentroCosto() {
        filtrarListTiposCentrosCostos = null;
        tipoCentroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
    }

    public void actualizarCuentaDebito() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setCuentad(cuentaSeleccionada);
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            } else {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setCuentad(cuentaSeleccionada);
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCodigoDebVC");
            context.update(":form:editarDescripcionDebVC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setCuentad(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaDebitoVC");
            context.update("formularioDialogos:nuevaDesDebitoVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setCuentad(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarDebitoVC");
            context.update("formularioDialogos:duplicaDesDebitoVC");
        }
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCuentaDebito() {
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
    }

    public void actualizarCuentaCredito() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setCuentac(cuentaSeleccionada);
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            } else {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setCuentac(cuentaSeleccionada);
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCodigoCreVC");
            context.update(":form:editarDescripcionCreVC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setCuentac(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCreditoVC");
            context.update("formularioDialogos:nuevaDesCreditoVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setCuentac(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicaCreditoVC");
            context.update("formularioDialogos:duplicaDesCreditoVC");
        }
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCuentaCredito() {
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
    }

    public void actualizarCentroCostoDebito() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setConsolidadord(centroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            } else {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setConsolidadord(centroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCCConsolidadorDVC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setConsolidadord(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaConsoliDebVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setConsolidadord(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicaConsoliDebVC");
        }
        filtrarListCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCentroCostoDebito() {
        filtrarListCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
    }

    public void actualizarCentroCostoCredito() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaCuenta == 0) {
                listVigenciasCuentasConcepto.get(indexVigenciaCuenta).setConsolidadorc(centroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(listVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(listVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            } else {
                filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta).setConsolidadorc(centroCostoSeleccionado);
                if (!listVigenciasCuentasCrear.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                    if (listVigenciasCuentasModificar.isEmpty()) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    } else if (!listVigenciasCuentasModificar.contains(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta))) {
                        listVigenciasCuentasModificar.add(filtrarListVigenciasCuentasConcepto.get(indexVigenciaCuenta));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaCuenta = true;
            cambiosVigenciaCuenta = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCCConsolidadorCVC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaCuenta.setConsolidadorc(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaConsoliCreVC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaCuenta.setConsolidadorc(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicaConsoliCreVC");
        }
        filtrarListCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCentroCostoCredito() {
        filtrarListCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVigenciaCuenta = -1;
        secRegistroVigenciaCuenta = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaCuenta = true;
    }

    public void actualizarGrupoConcepto() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setGrupoconcepto(grupoConceptoSeleccionado);
                if (!listVigenciasGruposConceptosCrear.contains(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    } else if (!listVigenciasGruposConceptosModificar.contains(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                        listVigenciasGruposConceptosModificar.add(listVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    }
                }
            } else {
                filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto).setGrupoconcepto(grupoConceptoSeleccionado);
                if (!listVigenciasGruposConceptosCrear.contains(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                    if (listVigenciasGruposConceptosModificar.isEmpty()) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    } else if (!listVigenciasGruposConceptosModificar.contains(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto))) {
                        listVigenciasGruposConceptosModificar.add(filtrarListVigenciasGruposConceptosConcepto.get(indexVigenciaGrupoConcepto));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaGrupoConcepto = true;
            cambiosVigenciaGrupoConcepto = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCodigoVGC");
            context.update(":form:editarDescripcionVGC");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaGrupoConcepto.setGrupoconcepto(grupoConceptoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCodigoVGC");
            context.update("formularioDialogos:nuevaDescripcionVGC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaGrupoConcepto.setGrupoconcepto(grupoConceptoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCodigoVGC");
            context.update("formularioDialogos:duplicarDescripcionVGC");
        }
        filtrarListGruposConceptos = null;
        grupoConceptoSeleccionado = null;
        aceptar = true;
        indexVigenciaGrupoConcepto = -1;
        secRegistroVigenciaGrupoConcepto = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioGrupoConcepto() {
        filtrarListGruposConceptos = null;
        grupoConceptoSeleccionado = null;
        aceptar = true;
        indexVigenciaGrupoConcepto = -1;
        secRegistroVigenciaGrupoConcepto = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaGrupoConcepto = true;
    }

    public void actualizarTipoTrabajador() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaConceptoTT == 0) {
                listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listVigenciasConceptosTTCrear.contains(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                    } else if (!listVigenciasConceptosTTModificar.contains(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT))) {
                        listVigenciasConceptosTTModificar.add(listVigenciasConceptosTTConcepto.get(indexVigenciaConceptoTT));
                    }
                }
            } else {
                filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listVigenciasConceptosTTCrear.contains(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT))) {
                    if (listVigenciasConceptosTTModificar.isEmpty()) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                    } else if (!listVigenciasConceptosTTModificar.contains(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT))) {
                        listVigenciasConceptosTTModificar.add(filtrarListVigenciasConceptosTT.get(indexVigenciaConceptoTT));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaConceptoTT = true;
            cambiosVigenciaConceptoTT = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:vigenciaCTTDescripcion");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoTT.setTipotrabajador(tipoTrabajadorSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTrabajadorVCTT");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoTT.setTipotrabajador(tipoTrabajadorSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTrabajadorVCTT");
        }
        filtrarListTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoTT = -1;
        secRegistroVigenciaConceptoTT = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioTipoTrabajador() {
        filtrarListTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoTT = -1;
        secRegistroVigenciaConceptoTT = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoTT = true;
    }

    public void actualizarTipoContrato() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaConceptoTC == 0) {
                listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC).setTipocontrato(tipoContratoSeleccionado);
                if (!listVigenciasConceptosTCCrear.contains(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC))) {
                    if (listVigenciasConceptosTCModificar.isEmpty()) {
                        listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                    } else if (!listVigenciasConceptosTCModificar.contains(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC))) {
                        listVigenciasConceptosTCModificar.add(listVigenciasConceptosTCConcepto.get(indexVigenciaConceptoTC));
                    }
                }
            } else {
                filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC).setTipocontrato(tipoContratoSeleccionado);
                if (!listVigenciasConceptosTCCrear.contains(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC))) {
                    if (listVigenciasConceptosTCModificar.isEmpty()) {
                        listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                    } else if (!listVigenciasConceptosTCModificar.contains(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC))) {
                        listVigenciasConceptosTCModificar.add(filtrarListVigenciasConceptosTC.get(indexVigenciaConceptoTC));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaConceptoTC = true;
            cambiosVigenciaConceptoTC = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:vigenciaCTCDescripcion");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoTC.setTipocontrato(tipoContratoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaContratoVCTC");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoTC.setTipocontrato(tipoContratoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarContratoVCTC");
        }
        filtrarListTiposContratos = null;
        tipoContratoSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoTC = -1;
        secRegistroVigenciaConceptoTC = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioTipoContrato() {
        filtrarListTiposContratos = null;
        tipoContratoSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoTC = -1;
        secRegistroVigenciaConceptoTC = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoTC = true;
    }

    public void actualizarReformaLaboral() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigenciaConceptoRL == 0) {
                listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL).setTiposalario(reformaLaboralSeleccionado);
                if (!listVigenciasConceptosRLCrear.contains(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL))) {
                    if (listVigenciasConceptosRLModificar.isEmpty()) {
                        listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                    } else if (!listVigenciasConceptosRLModificar.contains(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL))) {
                        listVigenciasConceptosRLModificar.add(listVigenciasConceptosRLConcepto.get(indexVigenciaConceptoRL));
                    }
                }
            } else {
                filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL).setTiposalario(reformaLaboralSeleccionado);
                if (!listVigenciasConceptosRLCrear.contains(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL))) {
                    if (listVigenciasConceptosRLModificar.isEmpty()) {
                        listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                    } else if (!listVigenciasConceptosRLModificar.contains(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL))) {
                        listVigenciasConceptosRLModificar.add(filtrarListVigenciasConceptosRL.get(indexVigenciaConceptoRL));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVigenciaConceptoRL = true;
            cambiosVigenciaConceptoRL = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:vigenciaCRLDescripcion");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaConceptoRL.setTiposalario(reformaLaboralSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaReformaVCRL");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaConceptoRL.setTiposalario(reformaLaboralSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarReformaVCRL");
        }
        filtrarListReformasLaborales = null;
        reformaLaboralSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoRL = -1;
        secRegistroVigenciaConceptoRL = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioReformaLaboral() {
        filtrarListReformasLaborales = null;
        reformaLaboralSeleccionado = null;
        aceptar = true;
        indexVigenciaConceptoRL = -1;
        secRegistroVigenciaConceptoRL = null;
        tipoActualizacion = -1;
        permitirIndexVigenciaConceptoRL = true;
    }

    public void actualizarFormula() {
        if (tipoActualizacion == 0) {
            if (tipoListaFormulasConceptos == 0) {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setFormula(formulaSeleccionado);
                if (!listFormulasConceptosCrear.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    } else if (!listFormulasConceptosModificar.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    }
                }
            } else {
                filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos).setFormula(formulaSeleccionado);
                if (!listFormulasConceptosCrear.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    } else if (!listFormulasConceptosModificar.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexFormulasConceptos = true;
            cambiosFormulasConceptos = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarNombreFC");
        } else if (tipoActualizacion == 1) {
            nuevaFormulasConceptos.setFormula(formulaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaFormulaFC");
        } else if (tipoActualizacion == 2) {
            duplicarFormulasConceptos.setFormula(formulaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarFormulaFC");
        }
        filtrarListFormulas = null;
        formulaSeleccionado = null;
        aceptar = true;
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioFormula() {
        filtrarListFormulas = null;
        formulaSeleccionado = null;
        aceptar = true;
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
        tipoActualizacion = -1;
        permitirIndexFormulasConceptos = true;
    }

    public void actualizarOrden() {
        if (tipoActualizacion == 0) {
            if (tipoListaFormulasConceptos == 0) {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(formulaConceptoSeleccionado.getStrOrden());
                if (!listFormulasConceptosCrear.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    } else if (!listFormulasConceptosModificar.contains(listFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                        listFormulasConceptosModificar.add(listFormulasConceptosConcepto.get(indexFormulasConceptos));
                    }
                }
            } else {
                listFormulasConceptosConcepto.get(indexFormulasConceptos).setStrOrden(formulaConceptoSeleccionado.getStrOrden());
                if (!listFormulasConceptosCrear.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                    if (listFormulasConceptosModificar.isEmpty()) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    } else if (!listFormulasConceptosModificar.contains(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos))) {
                        listFormulasConceptosModificar.add(filtrarListFormulasConceptosConcepto.get(indexFormulasConceptos));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexFormulasConceptos = true;
            cambiosFormulasConceptos = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarOrdenFC");
        } else if (tipoActualizacion == 1) {
            nuevaFormulasConceptos.setStrOrden(formulaConceptoSeleccionado.getStrOrden());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaOrdenFC");
        } else if (tipoActualizacion == 2) {
            duplicarFormulasConceptos.setStrOrden(formulaConceptoSeleccionado.getStrOrden());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarOrdenFC");
        }
        filtrarListFormulasConceptos = null;
        formulaConceptoSeleccionado = null;
        aceptar = true;
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioOrden() {
        filtrarListFormulasConceptos = null;
        formulaConceptoSeleccionado = null;
        aceptar = true;
        indexFormulasConceptos = -1;
        secRegistroFormulasConceptos = null;
        tipoActualizacion = -1;
        permitirIndexFormulasConceptos = true;
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
        if (indexVigenciaCuenta >= 0) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreXML = "VigenciasCuentas_XML";
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreXML = "VigenciasGruposConceptos_XML";
        }
        if (indexVigenciaConceptoTT >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreXML = "VigenciasConceptosTT_XML";
        }
        if (indexVigenciaConceptoTC >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreXML = "VigenciasConceptosTC_XML";
        }
        if (indexVigenciaConceptoRL >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreXML = "VigenciasConceptosRL_XML";
        }
        if (indexFormulasConceptos >= 0) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreXML = "FormulasConceptos_XML";
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }

        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (indexVigenciaCuenta >= 0) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreExportar = "VigenciasCuentas_PDF";
            exportPDF_Tabla();
            indexVigenciaCuenta = -1;
            indexAuxVigenciaCuenta = -1;
            secRegistroVigenciaCuenta = null;
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreExportar = "VigenciasGruposConceptos_PDF";
            exportPDF_Tabla();
            indexVigenciaGrupoConcepto = -1;
            indexAuxVigenciaGrupoConcepto = -1;
            secRegistroVigenciaGrupoConcepto = null;
        }
        if (indexVigenciaConceptoTT >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreExportar = "VigenciasConceptosTT_PDF";
            exportPDF_Tabla();
            indexVigenciaConceptoTT = -1;
            indexAuxVigenciaConceptoTT = -1;
            secRegistroVigenciaConceptoTT = null;
        }
        if (indexVigenciaConceptoTC >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreExportar = "VigenciasConceptosTC_PDF";
            exportPDF_Tabla();
            indexVigenciaConceptoTC = -1;
            indexAuxVigenciaConceptoTC = -1;
            secRegistroVigenciaConceptoTC = null;
        }
        if (indexVigenciaConceptoRL >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreExportar = "VigenciasConceptosRL_PDF";
            exportPDF_Tabla();
            indexVigenciaConceptoRL = -1;
            indexAuxVigenciaConceptoRL = -1;
            secRegistroVigenciaConceptoRL = null;
        }
        if (indexFormulasConceptos >= 0) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreExportar = "FormulasConceptos_PDF";
            exportPDF_Tabla();
            indexFormulasConceptos = -1;
            indexAuxFormulasConceptos = -1;
            secRegistroFormulasConceptos = null;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
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
        if (indexVigenciaCuenta >= 0) {
            nombreTabla = ":formExportarVigenciasCuentas:datosVigenciaCuentasExportar";
            nombreExportar = "VigenciasCuentas_XLS";
            exportXLS_Tabla();
            indexVigenciaCuenta = -1;
            indexAuxVigenciaCuenta = -1;
            secRegistroVigenciaCuenta = null;
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            nombreTabla = ":formExportarVigenciasGruposConceptos:datosVigenciaGrupoConceptoExportar";
            nombreExportar = "VigenciasGruposConceptos_XLS";
            exportXLS_Tabla();
            indexVigenciaGrupoConcepto = -1;
            indexAuxVigenciaGrupoConcepto = -1;
            secRegistroVigenciaGrupoConcepto = null;
        }
        if (indexVigenciaConceptoTT >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTT:datosVigenciaConceptoTTExportar";
            nombreExportar = "VigenciasConceptosTT_XLS";
            exportXLS_Tabla();
            indexVigenciaConceptoTT = -1;
            indexAuxVigenciaConceptoTT = -1;
            secRegistroVigenciaConceptoTT = null;
        }
        if (indexVigenciaConceptoTC >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosTC:datosVigenciaConceptoTCExportar";
            nombreExportar = "VigenciasConceptosTC_XLS";
            exportXLS_Tabla();
            indexVigenciaConceptoTC = -1;
            indexAuxVigenciaConceptoTC = -1;
            secRegistroVigenciaConceptoTC = null;
        }
        if (indexVigenciaConceptoRL >= 0) {
            nombreTabla = ":formExportarVigenciasConceptosRL:datosVigenciaConceptoRLExportar";
            nombreExportar = "VigenciasConceptosRL_XLS";
            exportXLS_Tabla();
            indexVigenciaConceptoRL = -1;
            indexAuxVigenciaConceptoRL = -1;
            secRegistroVigenciaConceptoRL = null;
        }
        if (indexFormulasConceptos >= 0) {
            nombreTabla = ":formExportarFormulasConceptos:datosFormulasConceptosExportar";
            nombreExportar = "FormulasConceptos_XLS";
            exportXLS_Tabla();
            indexFormulasConceptos = -1;
            indexAuxFormulasConceptos = -1;
            secRegistroFormulasConceptos = null;
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
    public void eventoFiltrar() {
        if (indexVigenciaCuenta >= 0) {
            if (tipoListaVigenciaCuenta == 0) {
                tipoListaVigenciaCuenta = 1;
            }
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            if (tipoListaVigenciaGrupoConcepto == 0) {
                tipoListaVigenciaGrupoConcepto = 1;
            }
        }
        if (indexVigenciaConceptoTT >= 0) {
            if (tipoListaVigenciaConceptoTT == 0) {
                tipoListaVigenciaConceptoTT = 1;
            }
        }
        if (indexVigenciaConceptoTC >= 0) {
            if (tipoListaVigenciaConceptoTC == 0) {
                tipoListaVigenciaConceptoTC = 1;
            }
        }
        if (indexVigenciaConceptoRL >= 0) {
            if (tipoListaVigenciaConceptoRL == 0) {
                tipoListaVigenciaConceptoRL = 1;
            }
        }
        if (indexFormulasConceptos >= 0) {
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
            if (tipoListaFormulasConceptos == 0) {
                tipoListaFormulasConceptos = 1;
            }
        }
    }

    public void verificarRastroTabla() {
        if (indexVigenciaCuenta >= 0) {
            verificarRastroVigenciaCuenta();
            indexVigenciaCuenta = -1;
            indexAuxVigenciaCuenta = -1;
        }
        if (indexVigenciaGrupoConcepto >= 0) {
            verificarRastroVigenciaGrupoConcepto();
            indexVigenciaGrupoConcepto = -1;
            indexAuxVigenciaGrupoConcepto = -1;
        }
        if (indexVigenciaConceptoTT >= 0) {
            verificarRastroVigenciaConceptoTT();
            indexVigenciaConceptoTT = -1;
            indexAuxVigenciaConceptoTT = -1;
        }
        if (indexVigenciaConceptoTC >= 0) {
            verificarRastroVigenciaConceptoTC();
            indexVigenciaConceptoTC = -1;
            indexAuxVigenciaConceptoTC = -1;
        }
        if (indexVigenciaConceptoRL >= 0) {
            verificarRastroVigenciaConceptoRL();
            indexVigenciaConceptoRL = -1;
            indexAuxVigenciaConceptoRL = -1;
        }
        if (indexFormulasConceptos >= 0) {
            verificarRastroFormulasConceptos();
            indexFormulasConceptos = -1;
            indexAuxFormulasConceptos = -1;
            formulaSeleccionada = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:detalleFormula");
        }
    }

    public void verificarRastroVigenciaCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasCuentasConcepto != null) {
            if (secRegistroVigenciaCuenta != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigenciaCuenta, "VIGENCIASCUENTAS");
                backUpSecRegistroVigenciaCuenta = secRegistroVigenciaCuenta;
                backUp = secRegistroVigenciaCuenta;
                secRegistroVigenciaCuenta = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCUENTAS")) {
                nombreTablaRastro = "VigenciasCuentas";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCUENTAS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaCuenta = -1;
    }

    public void verificarRastroVigenciaGrupoConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasGruposConceptosConcepto != null) {
            if (secRegistroVigenciaGrupoConcepto != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigenciaGrupoConcepto, "VIGENCIASGRUPOSCONCEPTOS");
                backUpSecRegistroVigenciaGrupoConcepto = secRegistroVigenciaGrupoConcepto;
                backUp = secRegistroVigenciaGrupoConcepto;
                secRegistroVigenciaGrupoConcepto = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASGRUPOSCONCEPTOS")) {
                nombreTablaRastro = "VigenciasGruposConceptos";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASGRUPOSCONCEPTOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaGrupoConcepto = -1;
    }

    public void verificarRastroVigenciaConceptoTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasConceptosTTConcepto != null) {
            if (secRegistroVigenciaConceptoTT != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigenciaConceptoTT, "VIGENCIASCONCEPTOSTT");
                backUpSecRegistroVigenciaConceptoTT = secRegistroVigenciaConceptoTT;
                backUp = secRegistroVigenciaConceptoTT;
                secRegistroVigenciaConceptoTT = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSTT")) {
                nombreTablaRastro = "VigenciasConceptosTT";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSTT tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaConceptoTT = -1;
    }

    public void verificarRastroVigenciaConceptoTC() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasConceptosTCConcepto != null) {
            if (secRegistroVigenciaConceptoTC != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigenciaConceptoTC, "VIGENCIASCONCEPTOSTC");
                backUpSecRegistroVigenciaConceptoTC = secRegistroVigenciaConceptoTC;
                backUp = secRegistroVigenciaConceptoTC;
                secRegistroVigenciaConceptoTC = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSTC")) {
                nombreTablaRastro = "VigenciasConceptosTC";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSTC tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaConceptoTC = -1;
    }

    public void verificarRastroVigenciaConceptoRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasConceptosRLConcepto != null) {
            if (secRegistroVigenciaConceptoRL != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigenciaConceptoRL, "VIGENCIASCONCEPTOSRL");
                backUpSecRegistroVigenciaConceptoRL = secRegistroVigenciaConceptoRL;
                backUp = secRegistroVigenciaConceptoRL;
                secRegistroVigenciaConceptoRL = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCONCEPTOSRL")) {
                nombreTablaRastro = "VigenciasConceptosRL";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCONCEPTOSRL tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaConceptoRL = -1;
    }

    public void verificarRastroFormulasConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listFormulasConceptosConcepto != null) {
            if (secRegistroFormulasConceptos != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroFormulasConceptos, "FORMULASCONCEPTOS");
                backUpSecRegistroFormulasConceptos = secRegistroFormulasConceptos;
                backUp = secRegistroFormulasConceptos;
                secRegistroFormulasConceptos = null;
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("FORMULASCONCEPTOS")) {
                nombreTablaRastro = "FormulasConceptos";
                msnConfirmarRastroHistorico = "La tabla FORMULASCONCEPTOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigenciaConceptoRL = -1;
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
            System.out.println("No elimina");
            context.execute("errorEliminacionConcepto.show()");
        } else {
            System.out.println("Proceso de eliminacion del concepto");
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
        if(indexFormulasConceptos>=0){
            conceptoEliminar = "Va a eliminar el concepto el cual tiene el código : " + listFormulasConceptosConcepto.get(indexFormulasConceptos).getConcepto().getCodigo().toString() + ". ¿Esta seguro?";
        }
        return conceptoEliminar;
    }

    //GET - SET 
    public List<VigenciasCuentas> getListVigenciasCuentasConcepto() {
        try {
            if (listVigenciasCuentasConcepto == null) {
                listVigenciasCuentasConcepto = new ArrayList<VigenciasCuentas>();
                listVigenciasCuentasConcepto = administrarDetalleConcepto.consultarListaVigenciasCuentasConcepto(conceptoActual.getSecuencia());
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

    public void setSecRegistroConcepto(BigInteger secRegistro) {
        this.secRegistroVigenciaCuenta = secRegistro;
    }

    public BigInteger getBackUpSecRegistroConceptos() {
        return backUpSecRegistroVigenciaCuenta;
    }

    public void setBackUpSecRegistroConceptos(BigInteger b) {
        this.backUpSecRegistroVigenciaCuenta = b;
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

    public BigInteger getSecRegistroConcepto() {
        return secRegistroVigenciaCuenta;
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

    public List<TiposCentrosCostos> getListTiposCentrosCostos() {
        if (listTiposCentrosCostos == null) {
            listTiposCentrosCostos = administrarDetalleConcepto.consultarLOVTiposCentrosCostos();

        }
        return listTiposCentrosCostos;
    }

    public void setListTiposCentrosCostos(List<TiposCentrosCostos> listTiposCentrosCostos) {
        this.listTiposCentrosCostos = listTiposCentrosCostos;
    }

    public List<TiposCentrosCostos> getFiltrarListTiposCentrosCostos() {
        return filtrarListTiposCentrosCostos;
    }

    public void setFiltrarListTiposCentrosCostos(List<TiposCentrosCostos> filtrarListTiposCentrosCostos) {
        this.filtrarListTiposCentrosCostos = filtrarListTiposCentrosCostos;
    }

    public TiposCentrosCostos getTipoCentroCostoSeleccionado() {
        return tipoCentroCostoSeleccionado;
    }

    public void setTipoCentroCostoSeleccionado(TiposCentrosCostos tipoCentroCostoSeleccionado) {
        this.tipoCentroCostoSeleccionado = tipoCentroCostoSeleccionado;
    }

    public List<Cuentas> getListCuentas() {
        if (listCuentas == null) {
            listCuentas = administrarDetalleConcepto.consultarLOVCuentas();
        }
        return listCuentas;
    }

    public void setListCuentas(List<Cuentas> listCuentas) {
        this.listCuentas = listCuentas;
    }

    public List<Cuentas> getFiltrarListCuentas() {
        return filtrarListCuentas;
    }

    public void setFiltrarListCuentas(List<Cuentas> filtrarListCuentas) {
        this.filtrarListCuentas = filtrarListCuentas;
    }

    public Cuentas getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(Cuentas cuentaSeleccionada) {
        this.cuentaSeleccionada = cuentaSeleccionada;
    }

    public List<CentrosCostos> getListCentrosCostos() {
        if (listCentrosCostos == null) {
            listCentrosCostos = administrarDetalleConcepto.consultarLOVCentrosCostos();
        }
        return listCentrosCostos;
    }

    public void setListCentrosCostos(List<CentrosCostos> listCentrosCostos) {
        this.listCentrosCostos = listCentrosCostos;
    }

    public List<CentrosCostos> getFiltrarListCentrosCostos() {
        return filtrarListCentrosCostos;
    }

    public void setFiltrarListCentrosCostos(List<CentrosCostos> filtrarListCentrosCostos) {
        this.filtrarListCentrosCostos = filtrarListCentrosCostos;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos centroCostoSeleccionado) {
        this.centroCostoSeleccionado = centroCostoSeleccionado;
    }

    public String getComportamientoConcepto() {
        return comportamientoConcepto;
    }

    public void setComportamientoConcepto(String comportamientoConcepto) {
        this.comportamientoConcepto = comportamientoConcepto;
    }

    public BigInteger getSecRegistroVigenciaCuenta() {
        return secRegistroVigenciaCuenta;
    }

    public void setSecRegistroVigenciaCuenta(BigInteger secRegistroVigenciaCuenta) {
        this.secRegistroVigenciaCuenta = secRegistroVigenciaCuenta;
    }

    public BigInteger getBackUpSecRegistroVigenciaCuenta() {
        return backUpSecRegistroVigenciaCuenta;
    }

    public void setBackUpSecRegistroVigenciaCuenta(BigInteger backUpSecRegistroVigenciaCuenta) {
        this.backUpSecRegistroVigenciaCuenta = backUpSecRegistroVigenciaCuenta;
    }

    public List<VigenciasGruposConceptos> getListVigenciasGruposConceptosConcepto() {
        try {
            if (listVigenciasGruposConceptosConcepto == null) {
                listVigenciasGruposConceptosConcepto = new ArrayList<VigenciasGruposConceptos>();
                listVigenciasGruposConceptosConcepto = administrarDetalleConcepto.consultarListaVigenciasGruposConceptosConcepto(conceptoActual.getSecuencia());
            }
            return listVigenciasGruposConceptosConcepto;
        } catch (Exception e) {
            System.out.println("Error getListVigenciasGruposConceptosConcepto : " + e.toString());
            return null;
        }

    }

    public void setListVigenciasGruposConceptosConcepto(List<VigenciasGruposConceptos> listVigenciasGruposConceptosConcepto) {
        this.listVigenciasGruposConceptosConcepto = listVigenciasGruposConceptosConcepto;
    }

    public List<VigenciasGruposConceptos> getFiltrarListVigenciasGruposConceptosConcepto() {
        return filtrarListVigenciasGruposConceptosConcepto;
    }

    public void setFiltrarListVigenciasGruposConceptosConcepto(List<VigenciasGruposConceptos> filtrarVigenciasGruposConceptosConcepto) {
        this.filtrarListVigenciasGruposConceptosConcepto = filtrarVigenciasGruposConceptosConcepto;
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

    public BigInteger getSecRegistroVigenciaGrupoConcepto() {
        return secRegistroVigenciaGrupoConcepto;
    }

    public void setSecRegistroVigenciaGrupoConcepto(BigInteger secRegistroVigenciaGrupoConcepto) {
        this.secRegistroVigenciaGrupoConcepto = secRegistroVigenciaGrupoConcepto;
    }

    public BigInteger getBackUpSecRegistroVigenciaGrupoConcepto() {
        return backUpSecRegistroVigenciaGrupoConcepto;
    }

    public void setBackUpSecRegistroVigenciaGrupoConcepto(BigInteger backUpSecRegistroVigenciaGrupoConcepto) {
        this.backUpSecRegistroVigenciaGrupoConcepto = backUpSecRegistroVigenciaGrupoConcepto;
    }

    public List<GruposConceptos> getListGruposConceptos() {
        if (listGruposConceptos == null) {
            listGruposConceptos = administrarDetalleConcepto.consultarLOVGruposConceptos();

        }
        return listGruposConceptos;
    }

    public void setListGruposConceptos(List<GruposConceptos> listGruposConceptos) {
        this.listGruposConceptos = listGruposConceptos;
    }

    public List<GruposConceptos> getFiltrarListGruposConceptos() {
        return filtrarListGruposConceptos;
    }

    public void setFiltrarListGruposConceptos(List<GruposConceptos> filtrarListGruposConceptos) {
        this.filtrarListGruposConceptos = filtrarListGruposConceptos;
    }

    public GruposConceptos getGrupoConceptoSeleccionado() {
        return grupoConceptoSeleccionado;
    }

    public void setGrupoConceptoSeleccionado(GruposConceptos grupoConceptoSeleccionado) {
        this.grupoConceptoSeleccionado = grupoConceptoSeleccionado;
    }

    public List<VigenciasConceptosTT> getListVigenciasConceptosTTConcepto() {
        try {
            if (listVigenciasConceptosTTConcepto == null) {
                listVigenciasConceptosTTConcepto = new ArrayList<VigenciasConceptosTT>();
                listVigenciasConceptosTTConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosTTConcepto(conceptoActual.getSecuencia());
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
                listVigenciasConceptosTCConcepto = new ArrayList<VigenciasConceptosTC>();
                listVigenciasConceptosTCConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosTCConcepto(conceptoActual.getSecuencia());
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
                listVigenciasConceptosRLConcepto = new ArrayList<VigenciasConceptosRL>();
                listVigenciasConceptosRLConcepto = administrarDetalleConcepto.consultarListaVigenciasConceptosRLCConcepto(conceptoActual.getSecuencia());
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

    public List<FormulasConceptos> getListFormulasConceptosConcepto() {
        try {
            if (listFormulasConceptosConcepto == null) {
                listFormulasConceptosConcepto = new ArrayList<FormulasConceptos>();
                listFormulasConceptosConcepto = administrarDetalleConcepto.consultarListaFormulasConceptosConcepto(conceptoActual.getSecuencia());
            }
            return listFormulasConceptosConcepto;
        } catch (Exception e) {
            return null;
        }
    }

    public void setListFormulasConceptosConcepto(List<FormulasConceptos> listFormulasConceptosConcepto) {
        this.listFormulasConceptosConcepto = listFormulasConceptosConcepto;
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

    public BigInteger getSecRegistroVigenciaConceptoTT() {
        return secRegistroVigenciaConceptoTT;
    }

    public void setSecRegistroVigenciaConceptoTT(BigInteger secRegistroVigenciaConceptoTT) {
        this.secRegistroVigenciaConceptoTT = secRegistroVigenciaConceptoTT;
    }

    public BigInteger getBackUpSecRegistroVigenciaConceptoTT() {
        return backUpSecRegistroVigenciaConceptoTT;
    }

    public void setBackUpSecRegistroVigenciaConceptoTT(BigInteger backUpSecRegistroVigenciaConceptoTT) {
        this.backUpSecRegistroVigenciaConceptoTT = backUpSecRegistroVigenciaConceptoTT;
    }

    public List<TiposTrabajadores> getListTiposTrabajadores() {
        try {
            if (listTiposTrabajadores == null) {
                listTiposTrabajadores = administrarDetalleConcepto.consultarLOVTiposTrabajadores();
            }
            return listTiposTrabajadores;
        } catch (Exception e) {
            System.out.println("Error getListTiposTrabajadores : " + e.toString());
            return null;
        }
    }

    public void setListTiposTrabajadores(List<TiposTrabajadores> listTiposTrabajadores) {
        this.listTiposTrabajadores = listTiposTrabajadores;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
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

    public BigInteger getSecRegistroVigenciaConceptoTC() {
        return secRegistroVigenciaConceptoTC;
    }

    public void setSecRegistroVigenciaConceptoTC(BigInteger secRegistroVigenciaConceptoTC) {
        this.secRegistroVigenciaConceptoTC = secRegistroVigenciaConceptoTC;
    }

    public BigInteger getBackUpSecRegistroVigenciaConceptoTC() {
        return backUpSecRegistroVigenciaConceptoTC;
    }

    public void setBackUpSecRegistroVigenciaConceptoTC(BigInteger backUpSecRegistroVigenciaConceptoTC) {
        this.backUpSecRegistroVigenciaConceptoTC = backUpSecRegistroVigenciaConceptoTC;
    }

    public List<TiposContratos> getListTiposContratos() {
        if (listTiposContratos == null) {
            listTiposContratos = administrarDetalleConcepto.consultarLOVTiposContratos();
        }
        return listTiposContratos;
    }

    public void setListTiposContratos(List<TiposContratos> listTiposContratos) {
        this.listTiposContratos = listTiposContratos;
    }

    public List<TiposContratos> getFiltrarListTiposContratos() {
        return filtrarListTiposContratos;
    }

    public void setFiltrarListTiposContratos(List<TiposContratos> filtrarListTiposContratos) {
        this.filtrarListTiposContratos = filtrarListTiposContratos;
    }

    public TiposContratos getTipoContratoSeleccionado() {
        return tipoContratoSeleccionado;
    }

    public void setTipoContratoSeleccionado(TiposContratos tipoContratoSeleccionado) {
        this.tipoContratoSeleccionado = tipoContratoSeleccionado;
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

    public BigInteger getSecRegistroVigenciaConceptoRL() {
        return secRegistroVigenciaConceptoRL;
    }

    public void setSecRegistroVigenciaConceptoRL(BigInteger secRegistroVigenciaConceptoRL) {
        this.secRegistroVigenciaConceptoRL = secRegistroVigenciaConceptoRL;
    }

    public BigInteger getBackUpSecRegistroVigenciaConceptoRL() {
        return backUpSecRegistroVigenciaConceptoRL;
    }

    public void setBackUpSecRegistroVigenciaConceptoRL(BigInteger backUpSecRegistroVigenciaConceptoRL) {
        this.backUpSecRegistroVigenciaConceptoRL = backUpSecRegistroVigenciaConceptoRL;
    }

    public List<ReformasLaborales> getListReformasLaborales() {
        try {
            if (listReformasLaborales == null) {
                listReformasLaborales = administrarDetalleConcepto.consultarLOVReformasLaborales();
            }
            return listReformasLaborales;
        } catch (Exception e) {
            System.out.println("Error getListReformasLaborales : " + e.toString());
            return null;
        }
    }

    public void setListReformasLaborales(List<ReformasLaborales> listReformasLaborales) {
        this.listReformasLaborales = listReformasLaborales;
    }

    public List<ReformasLaborales> getFiltrarListReformasLaborales() {
        return filtrarListReformasLaborales;
    }

    public void setFiltrarListReformasLaborales(List<ReformasLaborales> filtrarListReformasLaborales) {
        this.filtrarListReformasLaborales = filtrarListReformasLaborales;
    }

    public ReformasLaborales getReformaLaboralSeleccionado() {
        return reformaLaboralSeleccionado;
    }

    public void setReformaLaboralSeleccionado(ReformasLaborales reformaLaboralSeleccionado) {
        this.reformaLaboralSeleccionado = reformaLaboralSeleccionado;
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

    public BigInteger getSecRegistroFormulasConceptos() {
        return secRegistroFormulasConceptos;
    }

    public void setSecRegistroFormulasConceptos(BigInteger secRegistroFormulasConceptos) {
        this.secRegistroFormulasConceptos = secRegistroFormulasConceptos;
    }

    public BigInteger getBackUpSecRegistroFormulasConceptos() {
        return backUpSecRegistroFormulasConceptos;
    }

    public void setBackUpSecRegistroFormulasConceptos(BigInteger backUpSecRegistroFormulasConceptos) {
        this.backUpSecRegistroFormulasConceptos = backUpSecRegistroFormulasConceptos;
    }

    public List<Formulas> getListFormulas() {
        try {
            if (listFormulas == null) {
                listFormulas = administrarDetalleConcepto.consultarLOVFormulas();
            }
            return listFormulas;
        } catch (Exception e) {
            System.out.println("Error getListFormulas : " + e.toString());
            return null;
        }
    }

    public void setListFormulas(List<Formulas> listFormulas) {
        this.listFormulas = listFormulas;
    }

    public List<Formulas> getFiltrarListFormulas() {
        return filtrarListFormulas;
    }

    public void setFiltrarListFormulas(List<Formulas> filtrarListFormulas) {
        this.filtrarListFormulas = filtrarListFormulas;
    }

    public Formulas getFormulaSeleccionado() {
        return formulaSeleccionado;
    }

    public void setFormulaSeleccionado(Formulas formulaSeleccionado) {
        this.formulaSeleccionado = formulaSeleccionado;
    }

    public List<FormulasConceptos> getListFormulasConceptos() {
        try {
            if (listFormulasConceptos == null) {
                listFormulasConceptos = administrarDetalleConcepto.consultarLOVFormulasConceptos();
            }
            return listFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error getListFormulasConceptos Admi : " + e.toString());
            return null;
        }
    }

    public void setListFormulasConceptos(List<FormulasConceptos> listFormulasConceptos) {
        this.listFormulasConceptos = listFormulasConceptos;
    }

    public List<FormulasConceptos> getFiltrarListFormulasConceptos() {
        return filtrarListFormulasConceptos;
    }

    public void setFiltrarListFormulasConceptos(List<FormulasConceptos> filtrarListFormulasConceptos) {
        this.filtrarListFormulasConceptos = filtrarListFormulasConceptos;
    }

    public FormulasConceptos getFormulaConceptoSeleccionado() {
        return formulaConceptoSeleccionado;
    }

    public void setFormulaConceptoSeleccionado(FormulasConceptos formulaConceptoSeleccionado) {
        this.formulaConceptoSeleccionado = formulaConceptoSeleccionado;
    }

    public boolean isFormulaSeleccionada() {
        return formulaSeleccionada;
    }

    public void setFormulaSeleccionada(boolean formulaSeleccionada) {
        this.formulaSeleccionada = formulaSeleccionada;
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
}

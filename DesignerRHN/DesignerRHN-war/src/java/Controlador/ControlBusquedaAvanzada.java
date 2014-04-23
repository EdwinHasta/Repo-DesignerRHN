package Controlador;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import ClasesAyuda.ParametrosQueryBusquedaAvanzada;
import Entidades.Cargos;
import Entidades.CentrosCostos;
import Entidades.Ciudades;
import Entidades.ColumnasEscenarios;
import Entidades.Contratos;
import Entidades.Empleados;
import Entidades.EstadosAfiliaciones;
import Entidades.Estructuras;
import Entidades.JornadasLaborales;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosCambiosSueldos;
import Entidades.MotivosContratos;
import Entidades.MotivosLocalizaciones;
import Entidades.MotivosRetiros;
import Entidades.Motivosmvrs;
import Entidades.Mvrs;
import Entidades.NormasLaborales;
import Entidades.Papeles;
import Entidades.Periodicidades;
import Entidades.QVWEmpleadosCorte;
import Entidades.ReformasLaborales;
import Entidades.ResultadoBusquedaAvanzada;
import Entidades.Sucursales;
import Entidades.TercerosSucursales;
import Entidades.TiposContratos;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasCargos;
import Entidades.VigenciasContratos;
import Entidades.VigenciasFormasPagos;
import Entidades.VigenciasJornadas;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasNormasEmpleados;
import Entidades.VigenciasReformasLaborales;
import Entidades.VigenciasSueldos;
import Entidades.VigenciasTiposContratos;
import Entidades.VigenciasTiposTrabajadores;
import Entidades.VigenciasUbicaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarBusquedaAvanzadaInterface;
import InterfaceAdministrar.AdministrarEmplMvrsInterface;
import InterfaceAdministrar.AdministrarEmplVigenciasFormasPagosInterface;
import InterfaceAdministrar.AdministrarVigenciaLocalizacionInterface;
import InterfaceAdministrar.AdministrarVigenciaNormaLaboralInterface;
import InterfaceAdministrar.AdministrarVigenciasAfiliaciones3Interface;
import InterfaceAdministrar.AdministrarVigenciasCargosBusquedaAvanzadaInterface;
import InterfaceAdministrar.AdministrarVigenciasContratosInterface;
import InterfaceAdministrar.AdministrarVigenciasJornadasInterface;
import InterfaceAdministrar.AdministrarVigenciasReformasLaboralesInterface;
import InterfaceAdministrar.AdministrarVigenciasSueldosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposContratosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposTrabajadoresInterface;
import InterfaceAdministrar.AdministrarVigenciasUbicacionesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlBusquedaAvanzada implements Serializable {

    //Inyeccion EJB Administrar de cada modulo
    @EJB
    AdministrarVigenciasCargosBusquedaAvanzadaInterface administrarVigenciaCargoBusquedaAvanzada;
    @EJB
    AdministrarVigenciaLocalizacionInterface administrarVigenciaLocalizacion;
    @EJB
    AdministrarVigenciasSueldosInterface administrarVigenciaSueldo;
    @EJB
    AdministrarVigenciasTiposContratosInterface administrarVigenciaTipoContrato;
    @EJB
    AdministrarVigenciasTiposTrabajadoresInterface administrarVigenciasTiposTrabajadores;
    @EJB
    AdministrarVigenciasReformasLaboralesInterface administrarVigenciaReformaLaboral;
    @EJB
    AdministrarVigenciaNormaLaboralInterface administrarVigenciaNormaEmpleado;
    @EJB
    AdministrarVigenciasContratosInterface administrarVigenciaContrato;
    @EJB
    AdministrarVigenciasUbicacionesInterface administrarVigenciasUbicaciones;
    @EJB
    AdministrarVigenciasAfiliaciones3Interface administrarVigenciaAfiliacion3;
    @EJB
    AdministrarEmplVigenciasFormasPagosInterface administrarEmplVigenciaFormaPago;
    @EJB
    AdministrarEmplMvrsInterface administrarEmplMvrs;
    @EJB
    AdministrarVigenciasJornadasInterface administrarVigenciaJornada;
    //
    @EJB
    AdministrarBusquedaAvanzadaInterface administrarBusquedaAvanzada;

    //Objetos para realizar el proceso de busqueda avanzada
    //Modulo Cargos
    private VigenciasCargos vigenciaCargoBA;
    private int tabActivaCargos;
    private int casillaVigenciaCargo;
    private boolean permitirIndexVigenciaCargo;
    private Date fechaInicialCargo, fechaFinalCargo;
    private Date auxFechaInicialCargo, auxFechaFinalCargo;
    private int tipoFechaCargo;
    //Modulo Centro de Costo
    private VigenciasLocalizaciones vigenciaLocalizacionBA;
    private int tabActivaCentroCosto;
    private int casillaVigenciaLocalizacion;
    private boolean permitirIndexVigenciaLocalizacion;
    private Date fechaInicialCentroCosto, fechaFinalCentroCosto;
    private Date auxFechaInicialCentroCosto, auxFechaFinalCentroCosto;
    private int tipoFechaCentroCosto;
    //Modulo Sueldos
    private VigenciasSueldos vigenciaSueldoBA;
    private int tabActivaSueldo;
    private int casillaVigenciaSueldo;
    private boolean permitirIndexVigenciaSueldo;
    private Date fechaInicialSueldo, fechaFinalSueldo;
    private Date auxFechaInicialSueldo, auxFechaFinalSueldo;
    private int tipoFechaSueldo;
    //Modulo Fecha Contrato
    private VigenciasTiposContratos vigenciaTipoContratoBA;
    private int tabActivaFechaContrato;
    private int casillaVigenciaTipoContrato;
    private boolean permitirIndexVigenciaTipoContrato;
    private Date fechaInicialFechaContrato, fechaFinalFechaContrato;
    private Date auxFechaInicialFechaContrato, auxFechaFinalFechaContrato;
    private int tipoFechaFechaContrato;
    //Modulo Tipo de Trabajador
    private VigenciasTiposTrabajadores vigenciaTipoTrabajadorBA;
    private int tabActivaTipoTrabajador;
    private int casillaVigenciaTipoTrabajador;
    private boolean permitirIndexVigenciaTipoTrabajador;
    private Date fechaInicialTipoTrabajador, fechaFinalTipoTrabajador;
    private Date auxFechaInicialTipoTrabajador, auxFechaFinalTipoTrabajador;
    private int tipoFechaTipoTrabajador;
    //Modulo Tipo de Salario
    private VigenciasReformasLaborales vigenciaReformaLaboralBA;
    private int tabActivaTipoSalario;
    private int casillaVigenciaReformaLaboral;
    private boolean permitirIndexVigenciaReformaLaboral;
    private Date fechaInicialTipoSalario, fechaFinalTipoSalario;
    private Date auxFechaInicialTipoSalario, auxFechaFinalTipoSalario;
    private int tipoFechaTipoSalario;
    //Modulo Norma Laboral
    private VigenciasNormasEmpleados vigenciaNormaEmpleadoBA;
    private int tabActivaNormaLaboral;
    private int casillaVigenciaNormaLaboral;
    private boolean permitirIndexVigenciaNormaEmpleado;
    private Date fechaInicialNormaLaboral, fechaFinalNormaLaboral;
    private Date auxFechaInicialNormaLaboral, auxFechaFinalNormaLaboral;
    private int tipoFechaNormaLaboral;
    //Modulo Legislacion Laboral
    private VigenciasContratos vigenciaContratoBA;
    private int tabActivaLegislacionLaboral;
    private int casillaVigenciaContrato;
    private boolean permitirIndexVigenciaContrato;
    private Date fechaMIInicialLegislacionLaboral, fechaMIFinalLegislacionLaboral;
    private Date fechaMFInicialLegislacionLaboral, fechaMFFinalLegislacionLaboral;
    private Date auxFechaMIInicialLegislacionLaboral, auxFechaMIFinalLegislacionLaboral;
    private Date auxFechaMFInicialLegislacionLaboral, auxFechaMFFinalLegislacionLaboral;
    private int tipoFechaLegislacionLaboral;
    //Modulo Ubicacion Geografica
    private VigenciasUbicaciones vigenciaUbicacionBA;
    private int tabActivaUbicacion;
    private int casillaVigenciaUbicacion;
    private boolean permitirIndexVigenciaUbicacion;
    private Date fechaInicialUbicacion, fechaFinalUbicacion;
    private Date auxFechaInicialUbicacion, auxFechaFinalUbicacion;
    private int tipoFechaUbicacion;
    //Modulo Afiliaciones
    private VigenciasAfiliaciones vigenciaAfiliacionBA;
    private int tabActivaAfiliacion;
    private int casillaVigenciaAfiliacion;
    private boolean permitirIndexVigenciaAfiliacion;
    private Date fechaInicialAfiliacion, fechaFinalAfiliacion;
    private Date auxFechaInicialAfiliacion, auxFechaFinalAfiliacion;
    private int tipoFechaAfiliacion;
    //Modulo Forma Pago
    private VigenciasFormasPagos vigenciaFormaPagoBA;
    private int tabActivaFormaPago;
    private int casillaVigenciaFormaPago;
    private boolean permitirIndexVigenciaFormaPago;
    private Date fechaInicialFormaPago, fechaFinalFormaPago;
    private Date auxFechaInicialFormaPago, auxFechaFinalFormaPago;
    private int tipoFechaFormaPago;
    //Modulo Menor Valor Retencion (MVR)
    private Mvrs mvrsBA;
    private int tabActivaMvrs;
    private int casillaMvrs;
    private boolean permitirIndexMvrs;
    private Date fechaInicialMvrs, fechaFinalMvrs;
    private Date auxFechaInicialMvrs, auxFechaFinalMvrs;
    private int tipoFechaMvrs;
    //Modulo Sets
    private int tabActivaSets;
    private int casillaSets;
    private Date fechaMIInicialSets, fechaMIFinalSets;
    private Date fechaMFInicialSets, fechaMFFinalSets;
    private Date auxFechaMFInicialSets, auxFechaMFFinalSets;
    private Date auxFechaMIInicialSets, auxFechaMIFinalSets;
    private int tipoFechaSets;
    //Modulo Vacaciones
    private int tabActivaVacacion;
    private int casillaVacacion;
    private Date fechaMIInicialVacacion, fechaMIFinalVacacion;
    private Date fechaMFInicialVacacion, fechaMFFinalVacacion;
    private Date auxFechaMFInicialVacacion, auxFechaMFFinalVacacion;
    private Date auxFechaMIInicialVacacion, auxFechaMIFinalVacacion;
    //Modulo Norma Laboral
    private VigenciasJornadas vigenciaJornadaBA;
    private int tabActivaJornadaLaboral;
    private int casillaVigenciaJornadaLaboral;
    private boolean permitirIndexVigenciaJornada;
    private Date fechaInicialJornadaLaboral, fechaFinalJornadaLaboral;
    private Date auxFechaInicialJornadaLaboral, auxFechaFinalJornadaLaboral;
    //Modulo Fecha Retiro
    private MotivosRetiros motivoRetiroBA;
    private int tabActivaFechaRetiro;
    private int casillaMotivoRetiro;
    private boolean permitirIndexMotivoRetiro;
    private Date fechaInicialFechaRetiro, fechaFinalFechaRetiro;
    private Date auxFechaInicialFechaRetiro, auxFechaFinalFechaRetiro;
    //LOVS 
    //Motivos Retiros
    private List<MotivosRetiros> lovMotivosRetiros;
    private List<MotivosRetiros> filtrarLovMotivosRetiros;
    private MotivosRetiros motivoRetiroSeleccionado;
    //Jornadas Laborales
    private List<JornadasLaborales> lovJornadasLaborales;
    private List<JornadasLaborales> filtrarLovJornadasLaborales;
    private JornadasLaborales jornadaLaboralSeleccionada;
    //Motivos Mvrs
    private List<Motivosmvrs> lovMotivosMvrs;
    private List<Motivosmvrs> filtrarLovMotivosMvrs;
    private Motivosmvrs motivoMvrsSeleccionado;
    //Formas Pagos
    private List<Periodicidades> lovPeriodicidades;
    private List<Periodicidades> filtrarLovPeriodicidades;
    private Periodicidades periodicidadSeleccionada;
    //Sucursales
    private List<Sucursales> lovSucursales;
    private List<Sucursales> filtrarLovSucursales;
    private Sucursales sucursalSeleccionada;
    //Terceros
    private List<TercerosSucursales> lovTercerosSucursales;
    private List<TercerosSucursales> filtrarLovTercerosSucursales;
    private TercerosSucursales terceroSucursalSeleccionado;
    //Tipos Entidades
    private List<TiposEntidades> lovTiposEntidades;
    private List<TiposEntidades> filtrarLovTiposEntidades;
    private TiposEntidades tipoEntidadSeleccionado;
    //Estados Afiliaciones
    private List<EstadosAfiliaciones> lovEstadosAfiliaciones;
    private List<EstadosAfiliaciones> filtrarLovEstadosAfiliaciones;
    private EstadosAfiliaciones estadoAfiliacionSeleccionado;
    //Ciudades
    private List<UbicacionesGeograficas> lovUbicacionesGeograficas;
    private List<UbicacionesGeograficas> filtrarLovUbicacionesGeograficas;
    private UbicacionesGeograficas ubicacionGeograficaSeleccionada;
    //Contratos
    private List<Contratos> lovContratos;
    private List<Contratos> filtrarLovContratos;
    private Contratos contratoSeleccionado;
    //Normas Laborales
    private List<NormasLaborales> lovNormasLaborales;
    private List<NormasLaborales> filtrarLovNormasLaborales;
    private NormasLaborales normaLaboralSeleccionada;
    //Reformas Laborales
    private List<ReformasLaborales> lovReformasLaborales;
    private List<ReformasLaborales> filtrarLovReformasLaborales;
    private ReformasLaborales reformaLaboralSeleccionada;
    //Tipos Contratos
    private List<TiposTrabajadores> lovTiposTrabajadores;
    private List<TiposTrabajadores> filtrarLovTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    //Tipos Contratos
    private List<TiposContratos> lovTiposContratos;
    private List<TiposContratos> filtrarLovTiposContratos;
    private TiposContratos tipoContratoSeleccionado;
    //Motivos Contratos
    private List<MotivosContratos> lovMotivosContratos;
    private List<MotivosContratos> filtrarLovMotivosContratos;
    private MotivosContratos motivoContratoSeleccionado;
    //Tipos Sueldos
    private List<TiposSueldos> lovTiposSueldos;
    private List<TiposSueldos> filtrarLovTiposSueldos;
    private TiposSueldos tipoSueldoSeleccionado;
    //Motivos Cambios Sueldos
    private List<MotivosCambiosSueldos> lovMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> filtrarLovMotivosCambiosSueldos;
    private MotivosCambiosSueldos motivoSueldoSeleccionado;
    //Motivos Localizaciones
    private List<MotivosLocalizaciones> lovMotivosLocalizaciones;
    private List<MotivosLocalizaciones> filtrarLovMotivosLocalizaciones;
    private MotivosLocalizaciones motivoLocalizacionSeleccionado;
    //Estructuras
    private List<Estructuras> lovEstructuras;
    private List<Estructuras> filtrarLovEstructuras;
    private Estructuras estructuraSeleccionada;
    //Cargos
    private List<Cargos> lovCargos;
    private List<Cargos> filtrarLovCargos;
    private Cargos cargoSeleccionado;
    //Papeles
    private List<Papeles> lovPapeles;
    private List<Papeles> filtrarLovPapeles;
    private Papeles papelSeleccionado;
    //MotivosCambiosCargos
    private List<MotivosCambiosCargos> lovMotivosCambiosCargos;
    private List<MotivosCambiosCargos> filtrarLovMotivosCambiosCargos;
    private MotivosCambiosCargos motivoCambioSeleccionado;
    //Empleado Jefe
    private List<Empleados> lovEmpleados;
    private List<Empleados> filtrarLovEmpleados;
    private Empleados empleadoSeleccionado;
    ////////
    private String auxEstructuraVigenciaCargo, auxCargoVigenciaCargo, auxPapelVigenciaCargo, auxMotivoCambioCargoVigenciaCargo, auxJefeVigenciaCargo;
    private String auxLocalizacionVigenciaLocalizacion, auxMotivoLocalizacionVigenciaLocalizacion;
    private String auxTipoSueldoVigenciaSueldo, auxMotivoCambioSueldoVigenciaSueldo;
    private BigDecimal auxSueldoMinimoSueldo, auxSueldoMaximoSueldo;
    private String auxTipoContratoVigenciaTipoContrato, auxMotivoContratoVigenciaTipoContrato;
    private String auxTipoTrabajadorVigenciaTipoTrabajador;
    private String auxReformaLaboralVigenciaReformaLaboral;
    private String auxNormaLaboralVigenciaNormaEmpleado;
    private String auxContratoVigenciaContrato;
    private String auxUbicacionVigenciaUbicacion;
    private String auxTerceroVigenciaAfiliacion3, auxTipoEntidadVigenciaAfiliacion3, auxEstadoVigenciaAfiliacion3;
    private String auxPeriodicidadVigenciaFormaPago, auxSucursalVigenciaFormaPago;
    private String auxMotivoMvrs;
    private BigDecimal auxSueldoMinimoMvrs, auxSueldoMaximoMvrs;
    private BigDecimal auxPromedioMinimoSets, auxPromedioMaximoSets;
    private String auxJornadaJornadaLaboral;
    private String auxMotivoMotivoRetiro;
    //Otros
    private boolean aceptar;
    private Date fechaParametro;
    private boolean activoFechasCargos, activoFechasCentroCosto, activoFechasSueldo, activoFechasFechaContrato, activoFechasTipoTrabajador;
    private boolean activoFechasTipoSalario, activoFechasNormaLaboral, activoFechasLegislacionLaboral, activoFechasUbicacion;
    private boolean activoFechasAfiliacion, activoFechasFormaPago, activoFechasMvrs, activoFechasSets;
    private BigDecimal sueldoMaxSueldo, sueldoMinSueldo;
    private BigDecimal sueldoMaxMvrs, sueldoMinMvrs;
    private BigDecimal promedioMinimoSets, promedioMaximoSets;
    private String tipoMetodoSets;
    //Resulatos de busqueda
    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("codigo", "primeroApellido", "segundoApellido", "nombre", "columna0", "columna1", "columna2", "columna3", "columna4", "columna5", "columna6", "columna7", "columna8", "columna9");
    //Nombres de los valores en la clase que tomaran lo valores correspondientes de las columnas que se desean agregar
    private String columnTemplate;
    //Valores que seran mostrados en los header de cada columna
    private Map<String, String> mapValoresColumnas;
    //Cargue de los valores static de los arreglos color y manufactura
    private List<ResultadoBusquedaAvanzada> filteredListaResultadoBusquedaAvanzada;
    //Lista de filtrado
    private List<ResultadoBusquedaAvanzada> listaResultadoBusquedaAvanzada;
    //Lista de carros que sera cargada por la tabla
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    //Lista de las columnas que seran agregadas a la tabla
    private String nuevaColumna;
    // 
    private List<ParametrosQueryBusquedaAvanzada> listaParametrosQueryModulos;
    //
    private List<Empleados> listaEmpleadosResultados;
    //
    private boolean auxTabActivoCentroCosto;
    //
    private List<ColumnasEscenarios> listaColumnasEscenarios;
    //
    private List<ColumnasBusquedaAvanzada> listaColumnasBusquedaAvanzada;
    private List<ColumnasBusquedaAvanzada> filtrarListaColumnasBusquedaAvanzada;
    //PRUEBA
    List<BigInteger> listaCodigosEmpleado;
    List<QVWEmpleadosCorte> registros;
    List<QVWEmpleadosCorte> registrosFiltrado;

    private int indice, cualCelda;
    private String cabeceraEditarCelda, infoVariableEditarCelda;

    public ControlBusquedaAvanzada() {
        cabeceraEditarCelda = "";
        infoVariableEditarCelda = "";
        indice = -1;
        cualCelda = -1;
        listaColumnasBusquedaAvanzada = new ArrayList<ColumnasBusquedaAvanzada>();
        registros = new ArrayList<QVWEmpleadosCorte>();
        listaColumnasEscenarios = null;
        auxTabActivoCentroCosto = false;
        //
        listaEmpleadosResultados = new ArrayList<Empleados>();
        listaParametrosQueryModulos = new ArrayList<ParametrosQueryBusquedaAvanzada>();
        //Fechas Modulos
        auxFechaFinalCargo = null;
        auxFechaInicialCargo = null;
        auxFechaFinalCentroCosto = null;
        auxFechaInicialCentroCosto = null;
        auxFechaFinalSueldo = null;
        auxFechaInicialSueldo = null;
        auxFechaFinalFechaContrato = null;
        auxFechaInicialFechaContrato = null;
        auxFechaInicialTipoTrabajador = null;
        auxFechaFinalTipoTrabajador = null;
        auxFechaFinalTipoSalario = null;
        auxFechaInicialTipoSalario = null;
        auxFechaFinalNormaLaboral = null;
        auxFechaInicialNormaLaboral = null;
        auxFechaMIInicialLegislacionLaboral = null;
        auxFechaMIFinalLegislacionLaboral = null;
        auxFechaMFInicialLegislacionLaboral = null;
        auxFechaMFFinalLegislacionLaboral = null;
        auxFechaFinalUbicacion = null;
        auxFechaInicialUbicacion = null;
        auxFechaInicialAfiliacion = null;
        auxFechaFinalAfiliacion = null;
        auxFechaFinalFormaPago = null;
        auxFechaInicialFormaPago = null;
        auxFechaInicialMvrs = null;
        auxFechaFinalMvrs = null;
        auxFechaMFFinalSets = null;
        auxFechaMFInicialSets = null;
        auxFechaMIFinalSets = null;
        auxFechaMIInicialSets = null;
        auxFechaMIFinalVacacion = null;
        auxFechaMIInicialVacacion = null;
        auxFechaMFFinalVacacion = null;
        auxFechaMFInicialVacacion = null;
        auxFechaInicialJornadaLaboral = null;
        auxFechaFinalJornadaLaboral = null;
        auxFechaFinalFechaRetiro = null;
        auxFechaInicialFechaRetiro = null;
        //Tipo Fecha
        tipoFechaCargo = 1;
        tipoFechaCentroCosto = 1;
        tipoFechaSueldo = 1;
        tipoFechaFechaContrato = 1;
        tipoFechaTipoTrabajador = 1;
        tipoFechaTipoSalario = 1;
        tipoFechaNormaLaboral = 1;
        tipoFechaLegislacionLaboral = 1;
        tipoFechaUbicacion = 1;
        tipoFechaAfiliacion = 1;
        tipoFechaFormaPago = 1;
        tipoFechaMvrs = 1;
        tipoFechaSets = 1;
        //Activo Fechas
        activoFechasCargos = true;
        activoFechasCentroCosto = true;
        activoFechasSueldo = true;
        activoFechasFechaContrato = true;
        activoFechasTipoTrabajador = true;
        activoFechasTipoSalario = true;
        activoFechasNormaLaboral = true;
        activoFechasLegislacionLaboral = true;
        activoFechasUbicacion = true;
        activoFechasAfiliacion = true;
        activoFechasFormaPago = true;
        activoFechasMvrs = true;
        activoFechasSets = true;
        //POSICION MODULOS
        casillaVigenciaSueldo = -1;
        casillaVigenciaCargo = -1;
        casillaVigenciaLocalizacion = -1;
        casillaVigenciaTipoContrato = -1;
        casillaVigenciaTipoTrabajador = -1;
        casillaVigenciaTipoContrato = -1;
        casillaVigenciaNormaLaboral = -1;
        casillaVigenciaContrato = -1;
        casillaVigenciaUbicacion = -1;
        casillaVigenciaAfiliacion = -1;
        casillaVigenciaFormaPago = -1;
        casillaMvrs = -1;
        casillaSets = -1;
        casillaVacacion = -1;
        casillaVigenciaJornadaLaboral = -1;
        casillaMotivoRetiro = -1;
        //
        auxTabActivoCentroCosto = false;
        tabActivaCentroCosto = 0;
        tabActivaCargos = 0;
        tabActivaSueldo = 0;
        tabActivaFechaContrato = 0;
        tabActivaTipoTrabajador = 0;
        tabActivaTipoSalario = 0;
        tabActivaNormaLaboral = 0;
        tabActivaLegislacionLaboral = 0;
        tabActivaUbicacion = 0;
        tabActivaAfiliacion = 0;
        tabActivaFormaPago = 0;
        tabActivaMvrs = 0;
        tabActivaSets = 0;
        tabActivaVacacion = 0;
        tabActivaJornadaLaboral = 0;
        tabActivaFechaRetiro = 0;
        //PERMITIR INDEX
        permitirIndexVigenciaCargo = true;
        permitirIndexVigenciaLocalizacion = true;
        permitirIndexVigenciaSueldo = true;
        permitirIndexVigenciaTipoContrato = true;
        permitirIndexVigenciaTipoTrabajador = true;
        permitirIndexVigenciaReformaLaboral = true;
        permitirIndexVigenciaNormaEmpleado = true;
        permitirIndexVigenciaContrato = true;
        permitirIndexVigenciaUbicacion = true;
        permitirIndexVigenciaAfiliacion = true;
        permitirIndexVigenciaFormaPago = true;
        permitirIndexMvrs = true;
        permitirIndexVigenciaJornada = true;
        permitirIndexMotivoRetiro = true;
        //MODULOS
        vigenciaCargoBA = new VigenciasCargos();
        vigenciaCargoBA.setEstructura(new Estructuras());
        vigenciaCargoBA.getEstructura().setCentrocosto(new CentrosCostos());
        vigenciaCargoBA.setMotivocambiocargo(new MotivosCambiosCargos());
        vigenciaCargoBA.setPapel(new Papeles());
        vigenciaCargoBA.setCargo(new Cargos());
        vigenciaCargoBA.setEmpleadojefe(new Empleados());
        vigenciaLocalizacionBA = new VigenciasLocalizaciones();
        vigenciaLocalizacionBA.setLocalizacion(new Estructuras());
        vigenciaLocalizacionBA.setMotivo(new MotivosLocalizaciones());
        vigenciaSueldoBA = new VigenciasSueldos();
        vigenciaSueldoBA.setTiposueldo(new TiposSueldos());
        vigenciaSueldoBA.setMotivocambiosueldo(new MotivosCambiosSueldos());
        vigenciaTipoContratoBA = new VigenciasTiposContratos();
        vigenciaTipoContratoBA.setTipocontrato(new TiposContratos());
        vigenciaTipoContratoBA.setMotivocontrato(new MotivosContratos());
        vigenciaTipoTrabajadorBA = new VigenciasTiposTrabajadores();
        vigenciaTipoTrabajadorBA.setTipotrabajador(new TiposTrabajadores());
        vigenciaReformaLaboralBA = new VigenciasReformasLaborales();
        vigenciaReformaLaboralBA.setReformalaboral(new ReformasLaborales());
        vigenciaNormaEmpleadoBA = new VigenciasNormasEmpleados();
        vigenciaNormaEmpleadoBA.setNormalaboral(new NormasLaborales());
        vigenciaContratoBA = new VigenciasContratos();
        vigenciaContratoBA.setContrato(new Contratos());
        vigenciaUbicacionBA = new VigenciasUbicaciones();
        vigenciaUbicacionBA.setUbicacion(new UbicacionesGeograficas());
        vigenciaUbicacionBA.getUbicacion().setCiudad(new Ciudades());
        vigenciaAfiliacionBA = new VigenciasAfiliaciones();
        vigenciaAfiliacionBA.setTercerosucursal(new TercerosSucursales());
        vigenciaAfiliacionBA.setTipoentidad(new TiposEntidades());
        vigenciaAfiliacionBA.setEstadoafiliacion(new EstadosAfiliaciones());
        vigenciaFormaPagoBA = new VigenciasFormasPagos();
        vigenciaFormaPagoBA.setFormapago(new Periodicidades());
        vigenciaFormaPagoBA.setSucursal(new Sucursales());
        mvrsBA = new Mvrs();
        mvrsBA.setMotivo(new Motivosmvrs());
        vigenciaJornadaBA = new VigenciasJornadas();
        vigenciaJornadaBA.setJornadatrabajo(new JornadasLaborales());
        motivoRetiroBA = new MotivosRetiros();
        //LOVS
        lovMotivosRetiros = null;
        motivoRetiroSeleccionado = new MotivosRetiros();
        lovJornadasLaborales = null;
        jornadaLaboralSeleccionada = new JornadasLaborales();
        lovMotivosMvrs = null;
        motivoMvrsSeleccionado = new Motivosmvrs();
        lovPeriodicidades = null;
        periodicidadSeleccionada = new Periodicidades();
        lovSucursales = null;
        sucursalSeleccionada = new Sucursales();
        lovEstadosAfiliaciones = null;
        estadoAfiliacionSeleccionado = new EstadosAfiliaciones();
        lovTercerosSucursales = null;
        terceroSucursalSeleccionado = new TercerosSucursales();
        lovTiposEntidades = null;
        tipoEntidadSeleccionado = new TiposEntidades();
        lovUbicacionesGeograficas = null;
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        lovContratos = null;
        contratoSeleccionado = new Contratos();
        lovNormasLaborales = null;
        normaLaboralSeleccionada = new NormasLaborales();
        lovReformasLaborales = null;
        reformaLaboralSeleccionada = new ReformasLaborales();
        lovMotivosContratos = null;
        motivoContratoSeleccionado = new MotivosContratos();
        lovTiposSueldos = null;
        tipoSueldoSeleccionado = new TiposSueldos();
        lovMotivosCambiosSueldos = null;
        motivoSueldoSeleccionado = new MotivosCambiosSueldos();
        lovTiposSueldos = null;
        tipoSueldoSeleccionado = new TiposSueldos();
        lovMotivosLocalizaciones = null;
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        lovEstructuras = null;
        estructuraSeleccionada = new Estructuras();
        lovCargos = null;
        cargoSeleccionado = new Cargos();
        lovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        lovPapeles = null;
        papelSeleccionado = new Papeles();
        lovMotivosCambiosCargos = null;
        motivoCambioSeleccionado = new MotivosCambiosCargos();
        lovTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        //OTROS
        aceptar = true;
        auxSueldoMinimoSueldo = null;
        auxSueldoMaximoSueldo = null;
        auxSueldoMaximoMvrs = null;
        auxSueldoMinimoMvrs = null;
        auxPromedioMaximoSets = null;
        auxPromedioMinimoSets = null;
        tipoMetodoSets = "";
        //Resulatos de busqueda
        nuevaColumna = "";
        columnTemplate = "Codigo Primer_Apellido Segundo_Apellido Nombre ";
        mapValoresColumnas = new HashMap<String, String>();
        listaResultadoBusquedaAvanzada = new ArrayList<ResultadoBusquedaAvanzada>();
        listaCodigosEmpleado = new ArrayList<BigInteger>();
        createStaticColumns();
    }

    public void obtenerListaColumnasEscenarios(List<ColumnasEscenarios> listaRetorno) {
        if (listaRetorno != null) {
            listaColumnasEscenarios = new ArrayList<ColumnasEscenarios>();
            listaColumnasEscenarios = listaRetorno;
            System.out.println("listaColumnasEscenarios : " + listaColumnasEscenarios.size());
        } else {
            System.out.println("listaColumnasEscenarios : 0");
        }

    }

    public void ejecutarQuery() {
        restaurar();
        System.out.println("Inicio el ejecutarQuery");
        listaParametrosQueryModulos = new ArrayList<ParametrosQueryBusquedaAvanzada>();
        cargueParametrosModuloCargo();
        cargueParametrosModuloCentroCosto();
        cargueParametrosModuloSueldo();
        cargueParametrosModuloFechaContrato();
        cargueParametrosModuloTipoTrabajador();
        cargueParametrosModuloTipoSalario();
        cargueParametrosModuloNormaLaboral();
        cargueParametrosModuloLegislacionLaboral();
        cargueParametrosModuloUbicacionGeografica();
        cargueParametrosModuloAfiliaciones();
        cargueParametrosModuloFormaPago();
        cargueParametrosModuloMVR();
        cargueParametrosModuloSET();
        cargueParametrosModuloVacaciones();
        cargueParametrosModuloFechaRetiro();
        cargueParametrosModuloJornadaLaboral();
        String query = administrarBusquedaAvanzada.armarQueryModulosBusquedaAvanzada(listaParametrosQueryModulos);
        System.out.println("Query hecho por Administrar : " + query);
        String queryEmpleado = "SELECT codigoempleado FROM EMPLEADOS EM ";
        if (!query.isEmpty()) {
            queryEmpleado = queryEmpleado + query;
        }
        System.out.println("Query Final Busqueda Avanzada : " + queryEmpleado);
        listaCodigosEmpleado = administrarBusquedaAvanzada.ejecutarQueryBusquedaAvanzadaPorModulosCodigo(queryEmpleado);
        for (int i = 0; i < listaCodigosEmpleado.size(); i++) {
            System.out.println("listaCodigosEmpleado Codigo : " + listaCodigosEmpleado.get(i));
        }
        System.out.println("Tamaño: " + listaCodigosEmpleado.size());
        updateColumns();
    }

    public void cargueParametrosModuloCargo() {
        if (tabActivaCargos == 1) {
            System.out.println("Inicio el cargueParametrosModuloCargo");
            if (tipoFechaCargo == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "BCARGO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaCargo == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "BCARGO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("CARGO", "CARGODESDE", fechaInicialCargo.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("CARGO", "CARGOHASTA", fechaFinalCargo.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaCargoBA.getCargo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "CARGO", vigenciaCargoBA.getCargo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaCargoBA.getEstructura().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "ESTRUCTURA", vigenciaCargoBA.getEstructura().getSecuencia().toString());
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("CARGO", "CENTROCOSTO", vigenciaCargoBA.getEstructura().getCentrocosto().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
            }
            if (vigenciaCargoBA.getEmpleadojefe().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "EMPLEADOJEFE", vigenciaCargoBA.getEmpleadojefe().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaCargoBA.getMotivocambiocargo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "MOTIVOCAMBIOCARGO", vigenciaCargoBA.getMotivocambiocargo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaCargoBA.getPapel().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CARGO", "PAPEL", vigenciaCargoBA.getPapel().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloCentroCosto() {
        System.out.println("tabActivaCentroCosto : " + tabActivaCentroCosto);
        System.out.println("auxTabActivoCentroCosto : " + auxTabActivoCentroCosto);
        if (auxTabActivoCentroCosto == true) {
            tabActivaCentroCosto = 1;
        }
        if (tabActivaCentroCosto == 1) {
            System.out.println("Inicio el cargueParametrosModuloCentroCosto");
            if (tipoFechaCentroCosto == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "BCENTROCOSTO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaCentroCosto == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "BCENTROCOSTO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "CENTROCOSTODESDE", fechaInicialCentroCosto.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "CENTROCOSTOHASTA", fechaFinalCentroCosto.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaLocalizacionBA.getLocalizacion().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "LOCALIZACION", vigenciaLocalizacionBA.getLocalizacion().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaLocalizacionBA.getMotivo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("CENTROCOSTO", "MOTIVOLOCALIZACION", vigenciaLocalizacionBA.getMotivo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloSueldo() {
        if (tabActivaSueldo == 1) {
            System.out.println("Inicio el cargueParametrosModuloSueldo");
            if (tipoFechaSueldo == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "BSUELDO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaSueldo == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "BSUELDO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("SUELDO", "SUELDODESDE", fechaInicialSueldo.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("SUELDO", "SUELDOHASTA", fechaFinalSueldo.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaSueldoBA.getTiposueldo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "TIPOSUELDO ", vigenciaSueldoBA.getTiposueldo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaSueldoBA.getMotivocambiosueldo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "MOTIVOCAMBIOSUELDO", vigenciaSueldoBA.getMotivocambiosueldo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (sueldoMinSueldo != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "SUELDOMINIMO", sueldoMinSueldo.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (sueldoMaxSueldo != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SUELDO", "SUELDOMAXIMO", sueldoMaxSueldo.toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloFechaContrato() {
        if (tabActivaFechaContrato == 1) {
            System.out.println("Inicio el cargueParametrosModuloFechaContrato");
            if (tipoFechaFechaContrato == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "BFECHACONTRATO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaFechaContrato == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "BFECHACONTRATO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "FECHACONTRATODESDE", fechaInicialFechaContrato.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "FECHACONTRATOHASTA", fechaFinalFechaContrato.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaTipoContratoBA.getTipocontrato().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "TIPOCONTRATO", vigenciaTipoContratoBA.getTipocontrato().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaTipoContratoBA.getMotivocontrato().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FECHACONTRATO", "MOTIVOCONTRATO", vigenciaTipoContratoBA.getMotivocontrato().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloTipoTrabajador() {
        if (tabActivaTipoTrabajador == 1) {
            System.out.println("Inicio el cargueParametrosModuloTipoTrabajador");
            if (tipoFechaTipoTrabajador == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOTRABAJADOR", "BTIPOTRABAJADOR", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaTipoTrabajador == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOTRABAJADOR", "BTIPOTRABAJADOR", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("TIPOTRABAJADOR", "TIPOTRABAJADORDESDE", fechaInicialTipoTrabajador.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("TIPOTRABAJADOR", "TIPOTRABAJADORHASTA", fechaFinalTipoTrabajador.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaTipoTrabajadorBA.getTipotrabajador().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOTRABAJADOR", "TIPOTRABAJADOR", vigenciaTipoTrabajadorBA.getTipotrabajador().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloTipoSalario() {
        if (tabActivaTipoSalario == 1) {
            System.out.println("Inicio el cargueParametrosModuloTipoSalario");
            if (tipoFechaTipoSalario == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOSALARIO", "BTIPOSALARIO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaTipoSalario == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOSALARIO", "BTIPOSALARIO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("TIPOSALARIO", "TIPOSALARIODESDE", fechaInicialTipoSalario.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("TIPOSALARIO", "TIPOSALARIOHASTA", fechaFinalTipoSalario.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaReformaLaboralBA.getReformalaboral().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("TIPOSALARIO", "REFORMA", vigenciaReformaLaboralBA.getReformalaboral().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloNormaLaboral() {
        if (tabActivaNormaLaboral == 1) {
            System.out.println("Inicio el cargueParametrosModuloNormaLaboral");
            if (tipoFechaNormaLaboral == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("NORMALABORAL", "BNORMALABORAL", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaNormaLaboral == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("NORMALABORAL", "BNORMALABORAL", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("NORMALABORAL", "NORMALABORALDESDE", fechaInicialNormaLaboral.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("NORMALABORAL", "NORMALABORALHASTA", fechaFinalNormaLaboral.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaNormaEmpleadoBA.getNormalaboral().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("NORMALABORAL", "NORMA", vigenciaNormaEmpleadoBA.getNormalaboral().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloLegislacionLaboral() {
        if (tabActivaLegislacionLaboral == 1) {
            System.out.println("Inicio el cargueParametrosModuloLegislacionLaboral");
            if (tipoFechaLegislacionLaboral == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "BLEGISLACIONLABORAL", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaLegislacionLaboral == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "BLEGISLACIONLABORAL", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "LEGISLACIONLABORALDESDE", fechaMIInicialLegislacionLaboral.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "LEGISLACIONLABORALHASTA", fechaMIFinalLegislacionLaboral.toString());
                ParametrosQueryBusquedaAvanzada parametro4 = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "LEGISLACIONLABORALDESDEF", fechaMFInicialLegislacionLaboral.toString());
                ParametrosQueryBusquedaAvanzada parametro5 = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "LEGISLACIONLABORALHASTAF", fechaMFFinalLegislacionLaboral.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
                listaParametrosQueryModulos.add(parametro4);
                listaParametrosQueryModulos.add(parametro5);
            }
            if (vigenciaContratoBA.getContrato().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("LEGISLACIONLABORAL", "CONTRATO", vigenciaContratoBA.getContrato().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloUbicacionGeografica() {
        if (tabActivaUbicacion == 1) {
            System.out.println("Inicio el cargueParametrosModuloUbicacionGeografica");
            if (tipoFechaUbicacion == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("UBICACION", "BUBICACION", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaUbicacion == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("UBICACION", "BUBICACION", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("UBICACION", "UBICACIONDESDE", fechaInicialUbicacion.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("UBICACION", "UBICACIONHASTA", fechaFinalUbicacion.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaUbicacionBA.getUbicacion().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("UBICACION", "UBICACION", vigenciaUbicacionBA.getUbicacion().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloAfiliaciones() {
        if (tabActivaAfiliacion == 1) {
            System.out.println("Inicio el cargueParametrosModuloAfiliaciones");
            if (tipoFechaAfiliacion == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "BAFILIACIONES", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaAfiliacion == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "BAFILIACIONES", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "AFILIACIONESDESDE", fechaInicialAfiliacion.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "AFILIACIONESHASTA", fechaFinalAfiliacion.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaAfiliacionBA.getTercerosucursal().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "TERCERO", vigenciaAfiliacionBA.getTercerosucursal().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaAfiliacionBA.getTipoentidad().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "TIPOENTIDAD", vigenciaAfiliacionBA.getTipoentidad().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaAfiliacionBA.getEstadoafiliacion().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("AFILIACIONES", "ESTADO", vigenciaAfiliacionBA.getEstadoafiliacion().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloFormaPago() {
        if (tabActivaFormaPago == 1) {
            System.out.println("Inicio el cargueParametrosModuloFormaPago");
            if (tipoFechaFormaPago == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "BFORMAPAGO", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaFormaPago == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "BFORMAPAGO", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "FORMAPAGODESDE", fechaInicialFormaPago.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "FORMAPAGOHASTA", fechaFinalFormaPago.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaFormaPagoBA.getFormapago().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "FORMAPAGO", vigenciaFormaPagoBA.getFormapago().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (vigenciaFormaPagoBA.getSucursal().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FORMAPAGO", "SUCURSAL", vigenciaFormaPagoBA.getSucursal().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }

        }
    }

    public void cargueParametrosModuloMVR() {
        if (tabActivaMvrs == 1) {
            System.out.println("Inicio el cargueParametrosModuloMVR");
            if (tipoFechaMvrs == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("MVRS", "BMVRS", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaMvrs == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("MVRS", "BMVRS", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("MVRS", "MVRSDESDE", fechaInicialMvrs.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("MVRS", "MVRSHASTA", fechaFinalMvrs.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
            }
            if (mvrsBA.getMotivo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("MVRS", "MOTIVO", mvrsBA.getMotivo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (sueldoMinMvrs != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("MVRS", "SUELDOMINIMO", sueldoMinMvrs.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (sueldoMaxMvrs != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("MVRS", "SUELDOMAXIMO", sueldoMaxMvrs.toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloSET() {
        if (tabActivaSets == 1) {
            System.out.println("Inicio el cargueParametrosModuloSET");
            if (tipoFechaSets == 1) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SETS", "BSETS", "A");
                listaParametrosQueryModulos.add(parametro);
            }
            if (tipoFechaMvrs == 2) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SETS", "BSETS", "H");
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("SETS", "SETSDESDE", fechaMIInicialSets.toString());
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("SETS", "SETSHASTA", fechaMIFinalSets.toString());
                ParametrosQueryBusquedaAvanzada parametro4 = new ParametrosQueryBusquedaAvanzada("SETS", "SETSDESDEF", fechaMFInicialSets.toString());
                ParametrosQueryBusquedaAvanzada parametro5 = new ParametrosQueryBusquedaAvanzada("SETS", "SETSHASTAF", fechaMFFinalSets.toString());
                listaParametrosQueryModulos.add(parametro);
                listaParametrosQueryModulos.add(parametro2);
                listaParametrosQueryModulos.add(parametro3);
                listaParametrosQueryModulos.add(parametro4);
                listaParametrosQueryModulos.add(parametro5);
            }
            if (tipoMetodoSets != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SETS", "METODO", tipoMetodoSets);
                listaParametrosQueryModulos.add(parametro);
            }
            if (promedioMinimoSets != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SETS", "PROMEDIOMINIMO", promedioMinimoSets.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (promedioMaximoSets != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("SETS", "PROMEDIOMAXIMO", promedioMaximoSets.toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloVacaciones() {
        if (tabActivaVacacion == 1) {
            System.out.println("Inicio el cargueParametrosModuloVacaciones");
            ParametrosQueryBusquedaAvanzada parametroInicial = new ParametrosQueryBusquedaAvanzada("VACACIONES", "NN", "NN");
            listaParametrosQueryModulos.add(parametroInicial);
            if (fechaMIInicialVacacion != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("VACACIONES", "FECHASALIDADESDE", fechaMIInicialVacacion.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (fechaMIFinalVacacion != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("VACACIONES", "FECHASALIDAHASTA", fechaMIFinalVacacion.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (fechaMFInicialVacacion != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("VACACIONES", "FECHAREGRESODESDE", fechaMFInicialVacacion.toString());
                listaParametrosQueryModulos.add(parametro);
            }
            if (fechaMFFinalVacacion != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("VACACIONES", "FECHAREGRESOHASTA", fechaMFFinalVacacion.toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloFechaRetiro() {
        if (tabActivaFechaRetiro == 1) {
            System.out.println("Inicio el cargueParametrosModuloFechaRetiro");
            ParametrosQueryBusquedaAvanzada parametroInicial = new ParametrosQueryBusquedaAvanzada("FECHARETIRO", "NN", "NN");
            listaParametrosQueryModulos.add(parametroInicial);
            if (fechaInicialFechaRetiro != null) {
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("FECHARETIRO", "FECHARETIRODESDE", fechaInicialFechaRetiro.toString());
                listaParametrosQueryModulos.add(parametro2);
            }
            if (fechaFinalFechaRetiro != null) {
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("FECHARETIRO", "FECHARETIROHASTA", fechaFinalFechaRetiro.toString());
                listaParametrosQueryModulos.add(parametro3);
            }
            if (motivoRetiroBA.getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("FECHARETIRO", "MOTIVO", motivoRetiroBA.getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    public void cargueParametrosModuloJornadaLaboral() {
        if (tabActivaJornadaLaboral == 1) {
            System.out.println("Inicio el cargueParametrosModuloJornadaLaboral");
            ParametrosQueryBusquedaAvanzada parametroInicial = new ParametrosQueryBusquedaAvanzada("JORNADALABORAL", "NN", "NN");
            listaParametrosQueryModulos.add(parametroInicial);
            if (fechaInicialJornadaLaboral != null) {
                ParametrosQueryBusquedaAvanzada parametro2 = new ParametrosQueryBusquedaAvanzada("JORNADALABORAL", "JORNADALABORALDESDE", fechaInicialJornadaLaboral.toString());
                listaParametrosQueryModulos.add(parametro2);
            }
            if (fechaFinalJornadaLaboral != null) {
                ParametrosQueryBusquedaAvanzada parametro3 = new ParametrosQueryBusquedaAvanzada("JORNADALABORAL", "JORNADALABORALHASTA", fechaFinalJornadaLaboral.toString());
                listaParametrosQueryModulos.add(parametro3);
            }
            if (vigenciaJornadaBA.getJornadatrabajo().getSecuencia() != null) {
                ParametrosQueryBusquedaAvanzada parametro = new ParametrosQueryBusquedaAvanzada("JORNADALABORAL", "JORNADA", vigenciaJornadaBA.getJornadatrabajo().getSecuencia().toString());
                listaParametrosQueryModulos.add(parametro);
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void cancelarModificaciones() {
        //
        indice = -1;
        cualCelda = -1;
        //
        vigenciaCargoBA = new VigenciasCargos();
        vigenciaCargoBA.setEstructura(new Estructuras());
        vigenciaCargoBA.getEstructura().setCentrocosto(new CentrosCostos());
        vigenciaCargoBA.setMotivocambiocargo(new MotivosCambiosCargos());
        vigenciaCargoBA.setPapel(new Papeles());
        vigenciaCargoBA.setCargo(new Cargos());
        vigenciaCargoBA.setEmpleadojefe(new Empleados());
        vigenciaLocalizacionBA = new VigenciasLocalizaciones();
        vigenciaLocalizacionBA.setLocalizacion(new Estructuras());
        vigenciaLocalizacionBA.setMotivo(new MotivosLocalizaciones());
        vigenciaSueldoBA = new VigenciasSueldos();
        vigenciaSueldoBA.setTiposueldo(new TiposSueldos());
        vigenciaSueldoBA.setMotivocambiosueldo(new MotivosCambiosSueldos());
        vigenciaTipoContratoBA = new VigenciasTiposContratos();
        vigenciaTipoContratoBA.setTipocontrato(new TiposContratos());
        vigenciaTipoContratoBA.setMotivocontrato(new MotivosContratos());
        vigenciaTipoTrabajadorBA = new VigenciasTiposTrabajadores();
        vigenciaTipoTrabajadorBA.setTipotrabajador(new TiposTrabajadores());
        vigenciaReformaLaboralBA = new VigenciasReformasLaborales();
        vigenciaReformaLaboralBA.setReformalaboral(new ReformasLaborales());
        vigenciaNormaEmpleadoBA = new VigenciasNormasEmpleados();
        vigenciaNormaEmpleadoBA.setNormalaboral(new NormasLaborales());
        vigenciaContratoBA = new VigenciasContratos();
        vigenciaContratoBA.setContrato(new Contratos());
        vigenciaUbicacionBA = new VigenciasUbicaciones();
        vigenciaUbicacionBA.setUbicacion(new UbicacionesGeograficas());
        vigenciaUbicacionBA.getUbicacion().setCiudad(new Ciudades());
        vigenciaAfiliacionBA = new VigenciasAfiliaciones();
        vigenciaAfiliacionBA.setTercerosucursal(new TercerosSucursales());
        vigenciaAfiliacionBA.setTipoentidad(new TiposEntidades());
        vigenciaAfiliacionBA.setEstadoafiliacion(new EstadosAfiliaciones());
        vigenciaFormaPagoBA = new VigenciasFormasPagos();
        vigenciaFormaPagoBA.setFormapago(new Periodicidades());
        vigenciaFormaPagoBA.setSucursal(new Sucursales());
        mvrsBA = new Mvrs();
        mvrsBA.setMotivo(new Motivosmvrs());
        vigenciaJornadaBA = new VigenciasJornadas();
        vigenciaJornadaBA.setJornadatrabajo(new JornadasLaborales());
        motivoRetiroBA = new MotivosRetiros();
        //
        auxTabActivoCentroCosto = false;
        tabActivaFechaContrato = 0;
        tabActivaCargos = 0;
        tabActivaCentroCosto = 0;
        tabActivaSueldo = 0;
        tabActivaTipoTrabajador = 0;
        tabActivaTipoSalario = 0;
        tabActivaNormaLaboral = 0;
        tabActivaLegislacionLaboral = 0;
        tabActivaUbicacion = 0;
        tabActivaAfiliacion = 0;
        tabActivaFormaPago = 0;
        tabActivaMvrs = 0;
        tabActivaSets = 0;
        tabActivaVacacion = 0;
        tabActivaJornadaLaboral = 0;
        tabActivaFechaRetiro = 0;
        //
        auxFechaFinalFechaContrato = null;
        fechaFinalFechaContrato = null;
        auxFechaInicialFechaContrato = null;
        fechaInicialFechaContrato = null;
        auxFechaFinalCargo = null;
        fechaFinalCargo = null;
        auxFechaInicialCargo = null;
        fechaInicialCargo = null;
        auxFechaFinalCentroCosto = null;
        fechaFinalCentroCosto = null;
        auxFechaInicialCentroCosto = null;
        fechaInicialCentroCosto = null;
        auxFechaFinalSueldo = null;
        fechaFinalSueldo = null;
        auxFechaInicialSueldo = null;
        fechaInicialSueldo = null;
        auxFechaInicialTipoTrabajador = null;
        fechaInicialTipoTrabajador = null;
        auxFechaFinalTipoTrabajador = null;
        fechaFinalTipoTrabajador = null;
        auxFechaFinalTipoSalario = null;
        fechaFinalTipoSalario = null;
        auxFechaInicialTipoSalario = null;
        fechaInicialTipoSalario = null;
        auxFechaFinalNormaLaboral = null;
        fechaFinalNormaLaboral = null;
        auxFechaInicialNormaLaboral = null;
        fechaInicialNormaLaboral = null;
        auxFechaMIInicialLegislacionLaboral = null;
        auxFechaMIFinalLegislacionLaboral = null;
        auxFechaMFInicialLegislacionLaboral = null;
        auxFechaMFFinalLegislacionLaboral = null;
        fechaMFFinalLegislacionLaboral = null;
        fechaMFInicialLegislacionLaboral = null;
        fechaMIFinalLegislacionLaboral = null;
        fechaMIInicialLegislacionLaboral = null;
        auxFechaFinalUbicacion = null;
        fechaFinalUbicacion = null;
        auxFechaInicialUbicacion = null;
        fechaInicialUbicacion = null;
        auxFechaInicialAfiliacion = null;
        fechaInicialAfiliacion = null;
        auxFechaFinalAfiliacion = null;
        fechaFinalAfiliacion = null;
        auxFechaFinalFormaPago = null;
        fechaFinalFormaPago = null;
        auxFechaInicialFormaPago = null;
        fechaInicialFormaPago = null;
        auxFechaInicialMvrs = null;
        fechaInicialMvrs = null;
        auxFechaFinalMvrs = null;
        fechaFinalMvrs = null;
        auxFechaMFInicialSets = null;
        fechaMFInicialSets = null;
        auxFechaMFFinalSets = null;
        fechaMFFinalSets = null;
        auxFechaMIFinalSets = null;
        fechaMIFinalSets = null;
        auxFechaMIInicialSets = null;
        fechaMIInicialSets = null;
        auxFechaMFInicialVacacion = null;
        fechaMFInicialVacacion = null;
        auxFechaMFFinalVacacion = null;
        fechaMFFinalVacacion = null;
        auxFechaMIFinalVacacion = null;
        fechaMIFinalVacacion = null;
        auxFechaMIInicialVacacion = null;
        fechaMIInicialVacacion = null;
        auxFechaFinalJornadaLaboral = null;
        fechaFinalJornadaLaboral = null;
        auxFechaInicialJornadaLaboral = null;
        fechaInicialJornadaLaboral = null;
        auxFechaFinalFechaRetiro = null;
        fechaFinalFechaRetiro = null;
        auxFechaInicialFechaRetiro = null;
        fechaInicialFechaRetiro = null;
        //
        sueldoMaxSueldo = null;
        sueldoMinSueldo = null;
        auxSueldoMaximoSueldo = null;
        auxSueldoMinimoSueldo = null;
        sueldoMaxMvrs = null;
        sueldoMinMvrs = null;
        auxSueldoMaximoMvrs = null;
        auxSueldoMinimoMvrs = null;
        auxPromedioMaximoSets = null;
        promedioMaximoSets = null;
        auxPromedioMinimoSets = null;
        promedioMinimoSets = null;
        //
        tipoMetodoSets = "";
        //
        tipoFechaCargo = 1;
        activarCasillasFechasCargo();
        tipoFechaCentroCosto = 1;
        activarCasillasFechasCentroCosto();
        tipoFechaSueldo = 1;
        activarCasillasFechasSueldo();
        tipoFechaFechaContrato = 1;
        activarCasillasFechasFechaContrato();
        tipoFechaTipoTrabajador = 1;
        activarCasillasFechasTipoTrabajador();
        tipoFechaTipoSalario = 1;
        activarCasillasFechasTipoSalario();
        tipoFechaNormaLaboral = 1;
        activarCasillasFechasNormaLaboral();
        tipoFechaLegislacionLaboral = 1;
        activarCasillasFechasLegislacionLaboral();
        tipoFechaUbicacion = 1;
        activarCasillasFechasUbicacion();
        tipoFechaAfiliacion = 1;
        activarCasillasFechasAfiliacion();
        tipoFechaFormaPago = 1;
        activarCasillasFechasFormaPago();
        tipoFechaMvrs = 1;
        activarCasillasFechasMvrs();
        tipoFechaSets = 1;
        activarCasillasFechasSets();
        //
        restaurar();
        //
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto");
        context.update("form:tabViewCentroCosto");
        context.update("form:tabViewSueldo");
        context.update("form:tabViewFechaContrato");
        context.update("form:tabViewTipoTrabajador");
        context.update("form:tabViewTipoSalario");
        context.update("form:tabViewNormaLaboral");
        context.update("form:tabViewLegislacionLaboral");
        context.update("form:tabViewUbicacion");
        context.update("form:tabViewAfiliacion");
        context.update("form:tabViewFormaPago");
        context.update("form:tabViewMvrs");
        context.update("form:tabViewSets");
        context.update("form:tabViewVacacion");
        context.update("form:tabViewJornadaLaboral");
        context.update("form:tabViewFechaRetiro");
        //
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indice >= 0) {
            String[] columnas = columnTemplate.split(" ");
            cabeceraEditarCelda = columnas[cualCelda];
            if (cualCelda == 0) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getCodigo();
            }
            if (cualCelda == 1) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getPrimeroApellido();
            }
            if (cualCelda == 2) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getSegundoApellido();
            }
            if (cualCelda == 3) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getNombre();
            }
            if (cualCelda == 4) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna0();
            }
            if (cualCelda == 5) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna1();
            }
            if (cualCelda == 6) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna2();
            }
            if (cualCelda == 7) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna3();
            }
            if (cualCelda == 8) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna4();
            }
            if (cualCelda == 8) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna5();
            }
            if (cualCelda == 10) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna6();
            }
            if (cualCelda == 11) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna7();
            }
            if (cualCelda == 12) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna8();
            }
            if (cualCelda == 13) {
                infoVariableEditarCelda = listaColumnasBusquedaAvanzada.get(indice).getColumna9();
            }
            context.update("formularioDialogos:editarTablaBusquedaAvanzada");
                    context.execute("editarTablaBusquedaAvanzada.show()");
        } else {
            if (casillaVigenciaCargo >= 0) {
                if (casillaVigenciaCargo == 0) {
                    context.update("formularioDialogos:editarCargoModCargo");
                    context.execute("editarCargoModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 1) {
                    context.update("formularioDialogos:editarEstructuraModCargo");
                    context.execute("editarEstructuraModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 2) {
                    context.update("formularioDialogos:editarJefeModCargo");
                    context.execute("editarJefeModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 3) {
                    context.update("formularioDialogos:editarMotivoModCargo");
                    context.execute("editarMotivoModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 4) {
                    context.update("formularioDialogos:editarCentroCostoModCargo");
                    context.execute("editarCentroCostoModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 5) {
                    context.update("formularioDialogos:editarPapelModCargo");
                    context.execute("editarPapelModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 6) {
                    context.update("formularioDialogos:editarFechaInicialModCargo");
                    context.execute("editarFechaInicialModCargo.show()");
                    casillaVigenciaCargo = -1;
                } else if (casillaVigenciaCargo == 7) {
                    context.update("formularioDialogos:editarFechaFinalModCargo");
                    context.execute("editarFechaFinalModCargo.show()");
                    casillaVigenciaCargo = -1;
                }
            }
            if (casillaVigenciaLocalizacion >= 0) {
                if (casillaVigenciaLocalizacion == 0) {
                    context.update("formularioDialogos:editarLocalizacionModCentroCosto");
                    context.execute("editarLocalizacionModCentroCosto.show()");
                    casillaVigenciaLocalizacion = -1;
                } else if (casillaVigenciaLocalizacion == 1) {
                    context.update("formularioDialogos:editarMotivoModCentroCosto");
                    context.execute("editarMotivoModCentroCosto.show()");
                    casillaVigenciaLocalizacion = -1;
                } else if (casillaVigenciaLocalizacion == 2) {
                    context.update("formularioDialogos:editarFechaInicialModCentroCosto");
                    context.execute("editarFechaInicialModCentroCosto.show()");
                    casillaVigenciaLocalizacion = -1;
                } else if (casillaVigenciaLocalizacion == 3) {
                    context.update("formularioDialogos:editarFechaFinalModCentroCosto");
                    context.execute("editarFechaFinalModCentroCosto.show()");
                    casillaVigenciaLocalizacion = -1;
                }
            }
            if (casillaVigenciaSueldo >= 0) {
                if (casillaVigenciaSueldo == 0) {
                    context.update("formularioDialogos:editarTipoSueldoModSueldo");
                    context.execute("editarTipoSueldoModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                } else if (casillaVigenciaSueldo == 1) {
                    context.update("formularioDialogos:editarMotivoModSueldo");
                    context.execute("editarMotivoModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                } else if (casillaVigenciaSueldo == 2) {
                    context.update("formularioDialogos:editarSueldoMinimoModSueldo");
                    context.execute("editarSueldoMinimoModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                } else if (casillaVigenciaSueldo == 3) {
                    context.update("formularioDialogos:editarSueldoMaximoModSueldo");
                    context.execute("editarSueldoMaximoModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                } else if (casillaVigenciaSueldo == 4) {
                    context.update("formularioDialogos:editarFechaInicialModSueldo");
                    context.execute("editarFechaInicialModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                } else if (casillaVigenciaSueldo == 5) {
                    context.update("formularioDialogos:editarFechaFinalModSueldo");
                    context.execute("editarFechaFinalModSueldo.show()");
                    casillaVigenciaSueldo = -1;
                }
            }
            if (casillaVigenciaTipoContrato >= 0) {
                if (casillaVigenciaTipoContrato == 0) {
                    context.update("formularioDialogos:editarTipoContratoModFechaContrato");
                    context.execute("editarTipoContratoModFechaContrato.show()");
                    casillaVigenciaTipoContrato = -1;
                } else if (casillaVigenciaTipoContrato == 1) {
                    context.update("formularioDialogos:editarMotivoModFechaContrato");
                    context.execute("editarMotivoModFechaContrato.show()");
                    casillaVigenciaTipoContrato = -1;
                } else if (casillaVigenciaTipoContrato == 2) {
                    context.update("formularioDialogos:editarFechaInicialModFechaContrato");
                    context.execute("editarFechaInicialModFechaContrato.show()");
                    casillaVigenciaTipoContrato = -1;
                } else if (casillaVigenciaTipoContrato == 3) {
                    context.update("formularioDialogos:editarFechaFinalModFechaContrato");
                    context.execute("editarFechaFinalModFechaContrato.show()");
                    casillaVigenciaTipoContrato = -1;
                }
            }
            if (casillaVigenciaTipoTrabajador >= 0) {
                if (casillaVigenciaTipoTrabajador == 0) {
                    context.update("formularioDialogos:editarTipoTrabajadorModTipoTrabajador");
                    context.execute("editarTipoTrabajadorModTipoTrabajador.show()");
                    casillaVigenciaTipoTrabajador = -1;
                } else if (casillaVigenciaTipoTrabajador == 1) {
                    context.update("formularioDialogos:editarFechaInicialModTipoTrabajador");
                    context.execute("editarFechaInicialModTipoTrabajador.show()");
                    casillaVigenciaTipoTrabajador = -1;
                } else if (casillaVigenciaTipoTrabajador == 2) {
                    context.update("formularioDialogos:editarFechaFinalModTipoTrabajador");
                    context.execute("editarFechaFinalModTipoTrabajador.show()");
                    casillaVigenciaTipoTrabajador = -1;
                }
            }
            if (casillaVigenciaReformaLaboral >= 0) {
                if (casillaVigenciaReformaLaboral == 0) {
                    context.update("formularioDialogos:editarReformaLaboralModTipoSalario");
                    context.execute("editarReformaLaboralModTipoSalario.show()");
                    casillaVigenciaReformaLaboral = -1;
                } else if (casillaVigenciaReformaLaboral == 1) {
                    context.update("formularioDialogos:editarFechaInicialModTipoSalario");
                    context.execute("editarFechaInicialModTipoSalario.show()");
                    casillaVigenciaReformaLaboral = -1;
                } else if (casillaVigenciaReformaLaboral == 2) {
                    context.update("formularioDialogos:editarFechaFinalModTipoSalario");
                    context.execute("editarFechaFinalModTipoSalario.show()");
                    casillaVigenciaReformaLaboral = -1;
                }
            }
            if (casillaVigenciaNormaLaboral >= 0) {
                if (casillaVigenciaNormaLaboral == 0) {
                    context.update("formularioDialogos:editarNormaLaboralModNormaLaboral");
                    context.execute("editarNormaLaboralModNormaLaboral.show()");
                    casillaVigenciaNormaLaboral = -1;
                } else if (casillaVigenciaNormaLaboral == 1) {
                    context.update("formularioDialogos:editarFechaInicialModNormaLaboral");
                    context.execute("editarFechaInicialModNormaLaboral.show()");
                    casillaVigenciaNormaLaboral = -1;
                } else if (casillaVigenciaNormaLaboral == 2) {
                    context.update("formularioDialogos:editarFechaFinalModNormaLaboral");
                    context.execute("editarFechaFinalModNormaLaboral.show()");
                    casillaVigenciaNormaLaboral = -1;
                }
            }
            if (casillaVigenciaContrato >= 0) {
                if (casillaVigenciaContrato == 0) {
                    context.update("formularioDialogos:editarLegislacionModLegislacionLaboral");
                    context.execute("editarLegislacionModLegislacionLaboral.show()");
                    casillaVigenciaContrato = -1;
                } else if (casillaVigenciaContrato == 1) {
                    context.update("formularioDialogos:editarFechaMIInicialModLegislacionLaboral");
                    context.execute("editarFechaMIInicialModLegislacionLaboral.show()");
                    casillaVigenciaContrato = -1;
                } else if (casillaVigenciaContrato == 2) {
                    context.update("formularioDialogos:editarFechaMIFinalModLegislacionLaboral");
                    context.execute("editarFechaMIFinalModLegislacionLaboral.show()");
                    casillaVigenciaContrato = -1;
                } else if (casillaVigenciaContrato == 3) {
                    context.update("formularioDialogos:editarFechaMFInicialModLegislacionLaboral");
                    context.execute("editarFechaMFInicialModLegislacionLaboral.show()");
                    casillaVigenciaContrato = -1;
                } else if (casillaVigenciaContrato == 4) {
                    context.update("formularioDialogos:editarFechaMFFinalModLegislacionLaboral");
                    context.execute("editarFechaMFFinalModLegislacionLaboral.show()");
                    casillaVigenciaContrato = -1;
                }
            }
            if (casillaVigenciaUbicacion >= 0) {
                if (casillaVigenciaUbicacion == 0) {
                    context.update("formularioDialogos:editarUbicacionModUbicacion");
                    context.execute("editarUbicacionModUbicacion.show()");
                    casillaVigenciaUbicacion = -1;
                } else if (casillaVigenciaUbicacion == 1) {
                    context.update("formularioDialogos:editarCiudadModUbicacion");
                    context.execute("editarCiudadModUbicacion.show()");
                    casillaVigenciaUbicacion = -1;
                } else if (casillaVigenciaUbicacion == 2) {
                    context.update("formularioDialogos:editarFechaInicialModUbicacion");
                    context.execute("editarFechaInicialModUbicacion.show()");
                    casillaVigenciaUbicacion = -1;
                } else if (casillaVigenciaUbicacion == 3) {
                    context.update("formularioDialogos:editarFechaFinalModUbicacion");
                    context.execute("editarFechaFinalModUbicacion.show()");
                    casillaVigenciaUbicacion = -1;
                }
            }
            if (casillaVigenciaAfiliacion >= 0) {
                if (casillaVigenciaAfiliacion == 0) {
                    context.update("formularioDialogos:editarTerceroModAfiliacion");
                    context.execute("editarTerceroModAfiliacion.show()");
                    casillaVigenciaAfiliacion = -1;
                } else if (casillaVigenciaAfiliacion == 1) {
                    context.update("formularioDialogos:editarTipoEntidadModAfiliacion");
                    context.execute("editarTipoEntidadModAfiliacion.show()");
                    casillaVigenciaAfiliacion = -1;
                } else if (casillaVigenciaAfiliacion == 2) {
                    context.update("formularioDialogos:editarEstadoModAfiliacion");
                    context.execute("editarEstadoModAfiliacion.show()");
                    casillaVigenciaAfiliacion = -1;
                } else if (casillaVigenciaAfiliacion == 3) {
                    context.update("formularioDialogos:editarFechaInicialModAfiliacion");
                    context.execute("editarFechaInicialModAfiliacion.show()");
                    casillaVigenciaAfiliacion = -1;
                } else if (casillaVigenciaAfiliacion == 4) {
                    context.update("formularioDialogos:editarFechaFinalModAfiliacion");
                    context.execute("editarFechaFinalModAfiliacion.show()");
                    casillaVigenciaAfiliacion = -1;
                }
            }
            if (casillaVigenciaFormaPago >= 0) {
                if (casillaVigenciaFormaPago == 0) {
                    context.update("formularioDialogos:editarFormaPagoModFormaPago");
                    context.execute("editarFormaPagoModFormaPago.show()");
                    casillaVigenciaFormaPago = -1;
                } else if (casillaVigenciaFormaPago == 1) {
                    context.update("formularioDialogos:editarSucursalModFormaPago");
                    context.execute("editarSucursalModFormaPago.show()");
                    casillaVigenciaFormaPago = -1;
                } else if (casillaVigenciaFormaPago == 2) {
                    context.update("formularioDialogos:editarFechaInicialModFormaPago");
                    context.execute("editarFechaInicialModFormaPago.show()");
                    casillaVigenciaFormaPago = -1;
                } else if (casillaVigenciaFormaPago == 3) {
                    context.update("formularioDialogos:editarFechaFinalModFormaPago");
                    context.execute("editarFechaFinalModFormaPago.show()");
                    casillaVigenciaFormaPago = -1;
                }
            }
            if (casillaMvrs >= 0) {
                if (casillaMvrs == 0) {
                    context.update("formularioDialogos:editarMotivoModMvrs");
                    context.execute("editarMotivoModMvrs.show()");
                    casillaMvrs = -1;
                } else if (casillaMvrs == 1) {
                    context.update("formularioDialogos:editarSueldoMinModMvrs");
                    context.execute("editarSueldoMinModMvrs.show()");
                    casillaMvrs = -1;
                } else if (casillaMvrs == 2) {
                    context.update("formularioDialogos:editarSueldoMaxModMvrs");
                    context.execute("editarSueldoMaxModMvrs.show()");
                    casillaMvrs = -1;
                } else if (casillaMvrs == 3) {
                    context.update("formularioDialogos:editarFechaInicialModMvrs");
                    context.execute("editarFechaInicialModMvrs.show()");
                    casillaMvrs = -1;
                } else if (casillaMvrs == 4) {
                    context.update("formularioDialogos:editarFechaFinalModMvrs");
                    context.execute("editarFechaFinalModMvrs.show()");
                    casillaMvrs = -1;
                }
            }
            if (casillaSets >= 0) {
                if (casillaSets == 0) {
                    context.update("formularioDialogos:editarPromedioMinModSets");
                    context.execute("editarPromedioMinModSets.show()");
                    casillaSets = -1;
                } else if (casillaSets == 1) {
                    context.update("formularioDialogos:editarPromedioMaxModSets");
                    context.execute("editarPromedioMaxModSets.show()");
                    casillaSets = -1;
                } else if (casillaSets == 2) {
                    context.update("formularioDialogos:editarFechaMIInicialModSets");
                    context.execute("editarFechaMIInicialModSets.show()");
                    casillaSets = -1;
                } else if (casillaSets == 3) {
                    context.update("formularioDialogos:editarFechaMIFinalModSets");
                    context.execute("editarFechaMIFinalModSets.show()");
                    casillaSets = -1;
                } else if (casillaSets == 4) {
                    context.update("formularioDialogos:editarFechaMFInicialModSets");
                    context.execute("editarFechaMFInicialModSets.show()");
                    casillaSets = -1;
                } else if (casillaSets == 5) {
                    context.update("formularioDialogos:editarFechaMFFinalModSets");
                    context.execute("editarFechaMFFinalModSets.show()");
                    casillaSets = -1;
                }
            }
            if (casillaVacacion >= 0) {
                if (casillaVacacion == 0) {
                    context.update("formularioDialogos:editarFechaMIInicialModVacacion");
                    context.execute("editarFechaMIInicialModVacacion.show()");
                    casillaVacacion = -1;
                } else if (casillaVacacion == 1) {
                    context.update("formularioDialogos:editarFechaMIFinalModVacacion");
                    context.execute("editarFechaMIFinalModVacacion.show()");
                    casillaVacacion = -1;
                } else if (casillaVacacion == 2) {
                    context.update("formularioDialogos:editarFechaMFInicialModVacacion");
                    context.execute("editarFechaMFInicialModVacacion.show()");
                    casillaVacacion = -1;
                } else if (casillaVacacion == 3) {
                    context.update("formularioDialogos:editarFechaMFFinalModVacacion");
                    context.execute("editarFechaMFFinalModVacacion.show()");
                    casillaVacacion = -1;
                }
            }
            if (casillaVigenciaJornadaLaboral >= 0) {
                if (casillaVigenciaJornadaLaboral == 0) {
                    context.update("formularioDialogos:editarJornadaModJornadaLaboral");
                    context.execute("editarJornadaModJornadaLaboral.show()");
                    casillaVigenciaJornadaLaboral = -1;
                } else if (casillaVigenciaJornadaLaboral == 1) {
                    context.update("formularioDialogos:editarFechaInicialModJornadaLaboral");
                    context.execute("editarFechaInicialModJornadaLaboral.show()");
                    casillaVigenciaJornadaLaboral = -1;
                } else if (casillaVigenciaJornadaLaboral == 2) {
                    context.update("formularioDialogos:editarFechaFinalModJornadaLaboral");
                    context.execute("editarFechaFinalModJornadaLaboral.show()");
                    casillaVigenciaJornadaLaboral = -1;
                }
            }
            if (casillaMotivoRetiro >= 0) {
                if (casillaMotivoRetiro == 0) {
                    context.update("formularioDialogos:editarMotivoModFechaRetiro");
                    context.execute("editarMotivoModFechaRetiro.show()");
                    casillaMotivoRetiro = -1;
                } else if (casillaMotivoRetiro == 1) {
                    context.update("formularioDialogos:editarFechaInicialModFechaRetiro");
                    context.execute("editarFechaInicialModFechaRetiro.show()");
                    casillaMotivoRetiro = -1;
                } else if (casillaMotivoRetiro == 2) {
                    context.update("formularioDialogos:editarFechaFinalModFechaRetiro");
                    context.execute("editarFechaFinalModFechaRetiro.show()");
                    casillaMotivoRetiro = -1;
                }
            }
        }
    }

    public void botonListaValores() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casillaVigenciaCargo >= 0) {
            if (casillaVigenciaCargo == 0) {
                context.update("form:CargoCargoDialogo");
                context.execute("CargoCargoDialogo.show()");
                casillaVigenciaCargo = -1;
            }
            if (casillaVigenciaCargo == 1) {
                context.update("form:EstructuraCargoDialogo");
                context.execute("EstructuraCargoDialogo.show()");
                casillaVigenciaCargo = -1;
            }
            if (casillaVigenciaCargo == 2) {
                context.update("form:JefeCargoDialogo");
                context.execute("JefeCargoDialogo.show()");
                casillaVigenciaCargo = -1;
            }
            if (casillaVigenciaCargo == 3) {
                context.update("form:MotivoCargoDialogo");
                context.execute("MotivoCargoDialogo.show()");
                casillaVigenciaCargo = -1;
            }
            if (casillaVigenciaCargo == 5) {
                context.update("form:PapelCargoDialogo");
                context.execute("PapelCargoDialogo.show()");
                casillaVigenciaCargo = -1;
            }
        }
        if (casillaVigenciaLocalizacion >= 0) {
            if (casillaVigenciaLocalizacion == 0) {
                context.update("form:LocalizacionCentroCostoDialogo");
                context.execute("LocalizacionCentroCostoDialogo.show()");
                casillaVigenciaLocalizacion = -1;
            }
            if (casillaVigenciaLocalizacion == 1) {
                context.update("form:MotivoCentroCostoDialogo");
                context.execute("MotivoCentroCostoDialogo.show()");
                casillaVigenciaLocalizacion = -1;
            }
        }
        if (casillaVigenciaSueldo >= 0) {
            if (casillaVigenciaSueldo == 0) {
                context.update("form:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
                casillaVigenciaSueldo = -1;
            }
            if (casillaVigenciaSueldo == 1) {
                context.update("form:MotivoSueldoDialogo");
                context.execute("MotivoSueldoDialogo.show()");
                casillaVigenciaSueldo = -1;
            }
        }
        if (casillaVigenciaTipoContrato >= 0) {
            if (casillaVigenciaTipoContrato == 0) {
                context.update("form:TipoContratoFechaContratoDialogo");
                context.execute("TipoContratoFechaContratoDialogo.show()");
                casillaVigenciaTipoContrato = -1;
            }
            if (casillaVigenciaTipoContrato == 1) {
                context.update("form:MotivoFechaContratoDialogo");
                context.execute("MotivoFechaContratoDialogo.show()");
                casillaVigenciaTipoContrato = -1;
            }
        }
        if (casillaVigenciaTipoTrabajador >= 0) {
            if (casillaVigenciaTipoTrabajador == 0) {
                context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                casillaVigenciaTipoTrabajador = -1;
            }
        }
        if (casillaVigenciaReformaLaboral >= 0) {
            if (casillaVigenciaReformaLaboral == 0) {
                context.update("form:ReformaLaboralTipoSalarioDialogo");
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                casillaVigenciaReformaLaboral = -1;
            }
        }
        if (casillaVigenciaNormaLaboral >= 0) {
            if (casillaVigenciaNormaLaboral == 0) {
                context.update("form:NormaLaboralNormaLaboralDialogo");
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                casillaVigenciaNormaLaboral = -1;
            }
        }
        if (casillaVigenciaContrato >= 0) {
            if (casillaVigenciaContrato == 0) {
                context.update("form:LegislacionLegislacionLaboralDialogo");
                context.execute("LegislacionLegislacionLaboralDialogo.show()");
                casillaVigenciaContrato = -1;
            }
        }
        if (casillaVigenciaUbicacion >= 0) {
            if (casillaVigenciaUbicacion == 0) {
                context.update("form:UbicacionUbicacionDialogo");
                context.execute("UbicacionUbicacionDialogo.show()");
                casillaVigenciaUbicacion = -1;
            }
        }
        if (casillaVigenciaAfiliacion >= 0) {
            if (casillaVigenciaAfiliacion == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                casillaVigenciaAfiliacion = -1;
            }
            if (casillaVigenciaAfiliacion == 1) {
                context.update("form:TipoEntidadAfiliacionDialogo");
                context.execute("TipoEntidadAfiliacionDialogo.show()");
                casillaVigenciaAfiliacion = -1;
            }
            if (casillaVigenciaAfiliacion == 2) {
                context.update("form:EstadoAfiliacionDialogo");
                context.execute("EstadoAfiliacionDialogo.show()");
                casillaVigenciaAfiliacion = -1;
            }
        }
        if (casillaVigenciaFormaPago >= 0) {
            if (casillaVigenciaFormaPago == 0) {
                context.update("form:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                casillaVigenciaFormaPago = -1;
            }
            if (casillaVigenciaFormaPago == 1) {
                context.update("form:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
                casillaVigenciaFormaPago = -1;
            }
        }
        if (casillaMvrs >= 0) {
            if (casillaMvrs == 0) {
                context.update("form:MotivoMvrsDialogo");
                context.execute("MotivoMvrsDialogo.show()");
                casillaMvrs = -1;
            }
        }
        if (casillaVigenciaJornadaLaboral >= 0) {
            if (casillaVigenciaJornadaLaboral == 0) {
                context.update("form:JornadaJornadaLaboralDialogo");
                context.execute("JornadaJornadaLaboralDialogo.show()");
                casillaVigenciaJornadaLaboral = -1;
            }
        }
        if (casillaMotivoRetiro >= 0) {
            if (casillaMotivoRetiro == 0) {
                context.update("form:MotivoFechaRetiroDialogo");
                context.execute("MotivoFechaRetiroDialogo.show()");
                casillaMotivoRetiro = -1;
            }
        }
    }

    public void posicionModuloCargo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        int indice = Integer.parseInt(name);
        cambiarIndiceCargo(indice);
    }

    public void posicionModuloUbicacion() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        int indice = Integer.parseInt(name);
        cambiarIndiceUbicacion(indice);
    }

    public void cambiarIndiceCargo(int i) {
        if (permitirIndexVigenciaCargo == true) {
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaMotivoRetiro = -1;
            casillaVigenciaCargo = i;
            auxEstructuraVigenciaCargo = vigenciaCargoBA.getEstructura().getNombre();
            auxCargoVigenciaCargo = vigenciaCargoBA.getCargo().getNombre();
            auxJefeVigenciaCargo = vigenciaCargoBA.getEmpleadojefe().getPersona().getNombreCompleto();
            auxMotivoCambioCargoVigenciaCargo = vigenciaCargoBA.getMotivocambiocargo().getNombre();
            auxPapelVigenciaCargo = vigenciaCargoBA.getPapel().getDescripcion();
            auxFechaFinalCargo = fechaFinalCargo;
            auxFechaInicialCargo = fechaInicialCargo;
        }
    }

    public void cambiarIndiceCentroCosto(int i) {
        if (permitirIndexVigenciaLocalizacion == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaLocalizacion = i;
            auxLocalizacionVigenciaLocalizacion = vigenciaLocalizacionBA.getLocalizacion().getNombre();
            auxMotivoLocalizacionVigenciaLocalizacion = vigenciaLocalizacionBA.getMotivo().getDescripcion();
            auxFechaFinalCentroCosto = fechaFinalCentroCosto;
            auxFechaInicialCentroCosto = fechaInicialCentroCosto;
        }
    }

    public void cambiarIndiceSueldo(int i) {
        if (permitirIndexVigenciaSueldo == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaSueldo = i;
            auxTipoSueldoVigenciaSueldo = vigenciaSueldoBA.getTiposueldo().getDescripcion();
            auxMotivoCambioSueldoVigenciaSueldo = vigenciaSueldoBA.getMotivocambiosueldo().getNombre();
            auxFechaFinalSueldo = fechaFinalSueldo;
            auxFechaInicialSueldo = fechaInicialSueldo;
            auxSueldoMaximoSueldo = sueldoMaxSueldo;
            auxSueldoMinimoSueldo = sueldoMinSueldo;
        }
    }

    public void cambiarIndiceFechaContrato(int i) {
        if (permitirIndexVigenciaTipoContrato == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaTipoContrato = i;
            auxTipoContratoVigenciaTipoContrato = vigenciaTipoContratoBA.getTipocontrato().getNombre();
            auxMotivoContratoVigenciaTipoContrato = vigenciaTipoContratoBA.getMotivocontrato().getNombre();
            auxFechaFinalFechaContrato = fechaFinalFechaContrato;
            auxFechaInicialFechaContrato = fechaInicialFechaContrato;
        }
    }

    public void cambiarIndiceTipoTrabajador(int i) {
        if (permitirIndexVigenciaTipoTrabajador == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaTipoTrabajador = i;
            auxTipoTrabajadorVigenciaTipoTrabajador = vigenciaTipoTrabajadorBA.getTipotrabajador().getNombre();
            auxFechaFinalTipoTrabajador = fechaFinalTipoTrabajador;
            auxFechaInicialTipoTrabajador = fechaInicialTipoTrabajador;
        }
    }

    public void cambiarIndiceTipoSalario(int i) {
        if (permitirIndexVigenciaReformaLaboral == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaReformaLaboral = i;
            auxReformaLaboralVigenciaReformaLaboral = vigenciaReformaLaboralBA.getReformalaboral().getNombre();
            auxFechaFinalTipoSalario = fechaFinalTipoSalario;
            auxFechaInicialTipoSalario = fechaInicialTipoSalario;
        }
    }

    public void cambiarIndiceNormaLaboral(int i) {
        if (permitirIndexVigenciaNormaEmpleado == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaNormaLaboral = i;
            auxNormaLaboralVigenciaNormaEmpleado = vigenciaNormaEmpleadoBA.getNormalaboral().getNombre();
            auxFechaFinalNormaLaboral = fechaFinalNormaLaboral;
            auxFechaInicialNormaLaboral = fechaInicialNormaLaboral;
        }
    }

    public void cambiarIndiceLegislacionLaboral(int i) {
        if (permitirIndexVigenciaContrato == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaContrato = i;
            auxContratoVigenciaContrato = vigenciaContratoBA.getContrato().getDescripcion();
            auxFechaMFFinalLegislacionLaboral = fechaMFFinalLegislacionLaboral;
            auxFechaMFInicialLegislacionLaboral = fechaMFInicialLegislacionLaboral;
            auxFechaMIFinalLegislacionLaboral = fechaMIFinalLegislacionLaboral;
            auxFechaMIInicialLegislacionLaboral = fechaMIInicialLegislacionLaboral;
        }
    }

    public void cambiarIndiceUbicacion(int i) {
        if (permitirIndexVigenciaUbicacion == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaUbicacion = i;
            auxUbicacionVigenciaUbicacion = vigenciaUbicacionBA.getUbicacion().getDescripcion();
            auxFechaFinalUbicacion = fechaFinalUbicacion;
            auxFechaInicialUbicacion = fechaInicialUbicacion;
        }
    }

    public void cambiarIndiceAfiliacion(int i) {
        if (permitirIndexVigenciaAfiliacion == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaAfiliacion = i;
            auxTerceroVigenciaAfiliacion3 = vigenciaAfiliacionBA.getTercerosucursal().getDescripcion();
            auxTipoEntidadVigenciaAfiliacion3 = vigenciaAfiliacionBA.getTipoentidad().getNombre();
            auxEstadoVigenciaAfiliacion3 = vigenciaAfiliacionBA.getEstadoafiliacion().getNombre();
            auxFechaFinalAfiliacion = fechaFinalAfiliacion;
            auxFechaInicialAfiliacion = fechaInicialAfiliacion;
        }
    }

    public void cambiarIndiceFormaPago(int i) {
        if (permitirIndexVigenciaFormaPago == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaMvrs = -1;
            casillaSets = -1;
            casillaMotivoRetiro = -1;
            casillaVacacion = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaVigenciaFormaPago = i;
            auxSucursalVigenciaFormaPago = vigenciaFormaPagoBA.getSucursal().getNombre();
            auxPeriodicidadVigenciaFormaPago = vigenciaFormaPagoBA.getFormapago().getNombre();
            auxFechaFinalFormaPago = fechaFinalFormaPago;
            auxFechaInicialFormaPago = fechaInicialFormaPago;
        }
    }

    public void cambiarIndiceMvrs(int i) {
        if (permitirIndexMvrs == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaSets = -1;
            casillaVacacion = -1;
            casillaMotivoRetiro = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaMvrs = i;
            auxMotivoMvrs = mvrsBA.getMotivo().getNombre();
            auxSueldoMaximoMvrs = sueldoMaxMvrs;
            auxSueldoMinimoMvrs = sueldoMinMvrs;
            auxFechaFinalMvrs = fechaFinalMvrs;
            auxFechaInicialMvrs = fechaInicialMvrs;
        }
    }

    public void cambiarIndiceSets(int i) {
        casillaVigenciaCargo = -1;
        casillaVigenciaSueldo = -1;
        casillaVigenciaLocalizacion = -1;
        casillaVigenciaTipoContrato = -1;
        casillaVigenciaTipoTrabajador = -1;
        casillaVigenciaReformaLaboral = -1;
        casillaVigenciaNormaLaboral = -1;
        casillaVigenciaContrato = -1;
        casillaVigenciaUbicacion = -1;
        casillaVigenciaAfiliacion = -1;
        casillaVigenciaFormaPago = -1;
        casillaMvrs = -1;
        casillaVacacion = -1;
        casillaMotivoRetiro = -1;
        casillaVigenciaJornadaLaboral = -1;
        casillaSets = i;
        auxPromedioMaximoSets = promedioMaximoSets;
        auxPromedioMinimoSets = promedioMinimoSets;
        auxFechaMFFinalSets = fechaMFFinalSets;
        auxFechaMFInicialSets = fechaMFInicialSets;
        auxFechaMIFinalSets = fechaMIFinalSets;
        auxFechaMIInicialSets = fechaMIInicialSets;

    }

    public void cambiarIndiceVacacion(int i) {
        casillaVigenciaCargo = -1;
        casillaVigenciaSueldo = -1;
        casillaVigenciaLocalizacion = -1;
        casillaVigenciaTipoContrato = -1;
        casillaVigenciaTipoTrabajador = -1;
        casillaVigenciaReformaLaboral = -1;
        casillaVigenciaNormaLaboral = -1;
        casillaVigenciaContrato = -1;
        casillaVigenciaUbicacion = -1;
        casillaVigenciaAfiliacion = -1;
        casillaVigenciaFormaPago = -1;
        casillaMvrs = -1;
        casillaSets = -1;
        casillaMotivoRetiro = -1;
        casillaVigenciaJornadaLaboral = -1;
        casillaVacacion = i;
        auxFechaMFFinalVacacion = fechaMFFinalVacacion;
        auxFechaMFInicialVacacion = fechaMFInicialVacacion;
        auxFechaMIFinalVacacion = fechaMIFinalVacacion;
        auxFechaMIInicialVacacion = fechaMIInicialVacacion;
    }

    public void cambiarIndiceJornadaLaboral(int i) {
        if (permitirIndexVigenciaJornada == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaSets = -1;
            casillaVacacion = -1;
            casillaMvrs = -1;
            casillaMotivoRetiro = -1;
            casillaVigenciaJornadaLaboral = i;
            auxJornadaJornadaLaboral = vigenciaJornadaBA.getJornadatrabajo().getDescripcion();
            auxFechaFinalJornadaLaboral = fechaFinalJornadaLaboral;
            auxFechaInicialJornadaLaboral = fechaInicialJornadaLaboral;
        }
    }

    public void cambiarIndiceFechaRetiro(int i) {
        if (permitirIndexMotivoRetiro == true) {
            casillaVigenciaCargo = -1;
            casillaVigenciaSueldo = -1;
            casillaVigenciaLocalizacion = -1;
            casillaVigenciaTipoContrato = -1;
            casillaVigenciaTipoTrabajador = -1;
            casillaVigenciaReformaLaboral = -1;
            casillaVigenciaNormaLaboral = -1;
            casillaVigenciaContrato = -1;
            casillaVigenciaUbicacion = -1;
            casillaVigenciaAfiliacion = -1;
            casillaVigenciaFormaPago = -1;
            casillaSets = -1;
            casillaVacacion = -1;
            casillaMvrs = -1;
            casillaVigenciaJornadaLaboral = -1;
            casillaMotivoRetiro = i;
            auxMotivoMotivoRetiro = motivoRetiroBA.getNombre();
            auxFechaFinalFechaRetiro = fechaFinalFechaRetiro;
            auxFechaInicialFechaRetiro = fechaInicialFechaRetiro;
        }
    }

    public void modificarFechaFinalModuloCargo(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaCargo = i;
            cambiarIndiceCargo(i);
            retorno = validarFechasRegistroModuloCargo();
            if (retorno == false) {
                fechaFinalCargo = null;
                fechaInicialCargo = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewCosto");
                System.out.println("Error de fechas modulo de cargos");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalCargo = null;
            fechaInicialCargo = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewCosto");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloCargo Fechas Cargo : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloCentroCosto(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaLocalizacion = i;
            cambiarIndiceCentroCosto(i);
            retorno = validarFechasRegistroModuloCentroCosto();
            if (retorno == false) {
                fechaFinalCentroCosto = null;
                fechaInicialCentroCosto = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewCentroCosto");
                System.out.println("Error de fechas modulo de centro costo");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalCentroCosto = null;
            fechaInicialCentroCosto = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewCentroCosto");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloCentroCosto Fechas Centro Costo : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloSueldo(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaSueldo = i;
            cambiarIndiceSueldo(i);
            retorno = validarFechasRegistroModuloSueldo();
            if (retorno == false) {
                fechaFinalSueldo = null;
                fechaInicialSueldo = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewSueldo");
                System.out.println("Error de fechas modulo de sueldo");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalSueldo = null;
            fechaInicialSueldo = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewSueldo");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloSueldo Fechas Sueldo : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloFechaContrato(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaTipoContrato = i;
            cambiarIndiceFechaContrato(i);
            retorno = validarFechasRegistroModuloFechaContrato();
            if (retorno == false) {
                fechaFinalFechaContrato = null;
                fechaInicialFechaContrato = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewFechaContrato");
                System.out.println("Error de fechas modulo de fecha contrato");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalFechaContrato = null;
            fechaInicialFechaContrato = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewFechaContrato");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloFechaContrato Fechas Fecha Contrato : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloTipoTrabajador(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaTipoTrabajador = i;
            cambiarIndiceTipoTrabajador(i);
            retorno = validarFechasRegistroModuloTipoTrabajador();
            if (retorno == false) {
                fechaFinalTipoTrabajador = null;
                fechaInicialTipoTrabajador = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewTipoTrabajador");
                System.out.println("Error de fechas modulo de tipo trabajador");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalTipoTrabajador = null;
            fechaInicialTipoTrabajador = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewTipoTrabajador");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloTipoTrabajador Fechas Tipo Contrato : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloTipoSalario(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaReformaLaboral = i;
            cambiarIndiceTipoSalario(i);
            retorno = validarFechasRegistroModuloTipoSalario();
            if (retorno == false) {
                fechaFinalTipoSalario = null;
                fechaInicialTipoSalario = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewTipoSalario");
                System.out.println("Error de fechas modulo de tipo salario");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalTipoSalario = null;
            fechaInicialTipoSalario = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewTipoSalario");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloTipoSalario Fechas Tipo Salario : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloNormaLaboral(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaNormaLaboral = i;
            cambiarIndiceNormaLaboral(i);
            retorno = validarFechasRegistroModuloNormaLaboral();
            if (retorno == false) {
                fechaFinalNormaLaboral = null;
                fechaInicialNormaLaboral = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewNormaLaboral");
                System.out.println("Error de fechas modulo de norma laboral");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalNormaLaboral = null;
            fechaInicialNormaLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewNormaLaboral");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloNormaLaboral Fechas Norma Laboral : " + e.toString());
        }
    }

    public void modificarFechaMIFinalModuloLegislacionLaboral(int i) {
        try {
            if (fechaMIInicialLegislacionLaboral != null) {
                boolean retorno = false;
                casillaVigenciaContrato = i;
                cambiarIndiceLegislacionLaboral(i);
                retorno = validarFechasMIRegistroModuloLegislacionLaboral();
                if (retorno == false) {
                    fechaMIInicialLegislacionLaboral = null;
                    fechaMIFinalLegislacionLaboral = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewLegislacionLaboral");
                    System.out.println("Error de fechas MI modulo de legislacion laboral");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMIFinalLegislacionLaboral = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewLegislacionLaboral");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMIInicialLegislacionLaboral = null;
            fechaMIFinalLegislacionLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewLegislacionLaboral");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMIFinalModuloLegislacionLaboral Fechas Legislacion Laboral : " + e.toString());
        }
    }

    public void modificarFechaMFFinalModuloLegislacionLaboral(int i) {
        try {
            if (fechaMFInicialLegislacionLaboral != null) {
                boolean retorno = false;
                casillaVigenciaContrato = i;
                cambiarIndiceLegislacionLaboral(i);
                retorno = validarFechasMFRegistroModuloLegislacionLaboral();
                if (retorno == false) {
                    fechaMFInicialLegislacionLaboral = null;
                    fechaMFFinalLegislacionLaboral = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewLegislacionLaboral");
                    System.out.println("Error de fechas MI modulo de legislacion laboral");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMFFinalLegislacionLaboral = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewLegislacionLaboral");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMFInicialLegislacionLaboral = null;
            fechaMFFinalLegislacionLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewLegislacionLaboral");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMFFinalModuloLegislacionLaboral Fechas Legislacion Laboral : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloUbicacion(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaUbicacion = i;
            cambiarIndiceUbicacion(i);
            retorno = validarFechasRegistroModuloUbicacion();
            if (retorno == false) {
                fechaFinalUbicacion = null;
                fechaInicialUbicacion = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewUbicacion");
                System.out.println("Error de fechas modulo de ubicacion");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalUbicacion = null;
            fechaInicialUbicacion = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewUbicacion");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloUbicacion Fechas Ubicacion : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloAfiliacion(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaAfiliacion = i;
            cambiarIndiceAfiliacion(i);
            retorno = validarFechasRegistroModuloAfiliacion();
            if (retorno == false) {
                fechaFinalAfiliacion = null;
                fechaInicialAfiliacion = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewAfiliacion");
                System.out.println("Error de fechas modulo de afiliacion");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalAfiliacion = null;
            fechaInicialAfiliacion = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewAfiliacion");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloAfiliacion Fechas Afiliacion : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloFormaPago(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaFormaPago = i;
            cambiarIndiceFormaPago(i);
            retorno = validarFechasRegistroModuloFormaPago();
            if (retorno == false) {
                fechaFinalFormaPago = null;
                fechaInicialFormaPago = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewFormaPago");
                System.out.println("Error de fechas modulo de forma pago");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalFormaPago = null;
            fechaInicialFormaPago = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewFormaPago");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloFormaPago Fechas Forma Pago : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloMvrs(int i) {
        try {
            boolean retorno = false;
            casillaMvrs = i;
            cambiarIndiceMvrs(i);
            retorno = validarFechasRegistroModuloMvrs();
            if (retorno == false) {
                fechaFinalMvrs = null;
                fechaInicialMvrs = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewMvrs");
                System.out.println("Error de fechas modulo de mvrs");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalMvrs = null;
            fechaInicialMvrs = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewMvrs");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloMvrs Fechas Mvrs : " + e.toString());
        }
    }

    public void modificarFechaMIFinalModuloSets(int i) {
        try {
            if (fechaMIInicialSets != null) {
                boolean retorno = false;
                casillaSets = i;
                cambiarIndiceSets(i);
                retorno = validarFechasMIRegistroModuloSets();
                if (retorno == false) {
                    fechaMIInicialSets = null;
                    fechaMIFinalSets = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewSets");
                    System.out.println("Error de fechas MI modulo de Sets");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMIFinalSets = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewSets");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMIInicialSets = null;
            fechaMIFinalSets = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewSets");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMIFinalModuloSets Fechas Sets : " + e.toString());
        }
    }

    public void modificarFechaMFFinalModuloSets(int i) {
        try {
            if (fechaMFInicialSets != null) {
                boolean retorno = false;
                casillaSets = i;
                cambiarIndiceSets(i);
                retorno = validarFechasMFRegistroModuloSets();
                if (retorno == false) {
                    fechaMFInicialSets = null;
                    fechaMFFinalSets = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewSets");
                    System.out.println("Error de fechas MI modulo de sets");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMFFinalSets = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewSets");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMFInicialSets = null;
            fechaMFFinalSets = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewSets");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMFFinalModuloLegislacionLaboral Fechas Legislacion Laboral : " + e.toString());
        }
    }

    public void modificarFechaMIFinalModuloVacacion(int i) {
        try {
            if (fechaMIInicialVacacion != null) {
                boolean retorno = false;
                casillaVacacion = i;
                cambiarIndiceVacacion(i);
                retorno = validarFechasMIRegistroModuloVacacion();
                if (retorno == false) {
                    fechaMIInicialVacacion = null;
                    fechaMIFinalVacacion = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewVacacion");
                    System.out.println("Error de fechas MI modulo de Vacacion");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMIFinalVacacion = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewVacacion");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMIInicialVacacion = null;
            fechaMIFinalVacacion = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewVacacion");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMIFinalModuloVacacion Fechas Vacacion : " + e.toString());
        }
    }

    public void modificarFechaMFFinalModuloVacacion(int i) {
        try {
            if (fechaMFInicialVacacion != null) {
                boolean retorno = false;
                casillaSets = i;
                cambiarIndiceSets(i);
                retorno = validarFechasMFRegistroModuloSets();
                if (retorno == false) {
                    fechaMFInicialVacacion = null;
                    fechaMFFinalVacacion = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:tabViewVacacion");
                    System.out.println("Error de fechas MI modulo de sets");
                    context.execute("errorFechasIngresadas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMFFinalVacacion = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewVacacion");
                context.execute("errorFechasRequeridas.show()");
            }
        } catch (Exception e) {
            fechaMFInicialVacacion = null;
            fechaMFFinalVacacion = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewVacacion");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaMFFinalModuloVacacion Fechas Legislacion Laboral : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloJornadaLaboral(int i) {
        try {
            boolean retorno = false;
            casillaVigenciaJornadaLaboral = i;
            cambiarIndiceJornadaLaboral(i);
            retorno = validarFechasRegistroModuloJornadaLaboral();
            if (retorno == false) {
                fechaFinalJornadaLaboral = null;
                fechaInicialJornadaLaboral = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewJornadaLaboral");
                System.out.println("Error de fechas modulo de jornada laboral");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalJornadaLaboral = null;
            fechaInicialJornadaLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewJornadaLaboral");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloJornadaLaboral Fechas Jornada Laboral : " + e.toString());
        }
    }

    public void modificarFechaFinalModuloFechaRetiro(int i) {
        try {
            boolean retorno = false;
            casillaMotivoRetiro = i;
            cambiarIndiceFechaRetiro(i);
            retorno = validarFechasRegistroModuloFechaRetiro();
            if (retorno == false) {
                fechaFinalFechaRetiro = null;
                fechaInicialFechaRetiro = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tabViewFechaRetiro");
                System.out.println("Error de fechas modulo de fecha retiro");
                context.execute("errorFechasIngresadas.show()");
            }
        } catch (Exception e) {
            fechaFinalFechaRetiro = null;
            fechaInicialFechaRetiro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewFechaRetiro");
            context.execute("errorExceptionFechas.show()");
            System.out.println("Error modificarFechaFinalModuloFechaRetiro Fechas Fecha Retiro : " + e.toString());
        }
    }

    public boolean validarFechasRegistroModuloCargo() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialCargo != null && fechaFinalCargo != null) {
            if (fechaInicialCargo.after(fechaParametro) && fechaInicialCargo.before(fechaFinalCargo)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloCentroCosto() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialCentroCosto != null && fechaFinalCentroCosto != null) {
            if (fechaInicialCentroCosto.after(fechaParametro) && fechaInicialCentroCosto.before(fechaFinalCentroCosto)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloSueldo() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialSueldo != null && fechaFinalSueldo != null) {
            if (fechaInicialSueldo.after(fechaParametro) && fechaInicialSueldo.before(fechaFinalSueldo)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloFechaContrato() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialFechaContrato != null && fechaFinalFechaContrato != null) {
            if (fechaInicialFechaContrato.after(fechaParametro) && fechaInicialFechaContrato.before(fechaFinalFechaContrato)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloTipoTrabajador() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialTipoTrabajador != null && fechaFinalTipoTrabajador != null) {
            if (fechaInicialTipoTrabajador.after(fechaParametro) && fechaInicialTipoTrabajador.before(fechaFinalTipoTrabajador)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloTipoSalario() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialTipoSalario != null && fechaFinalTipoSalario != null) {
            if (fechaInicialTipoSalario.after(fechaParametro) && fechaInicialTipoSalario.before(fechaFinalTipoSalario)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloNormaLaboral() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialNormaLaboral != null && fechaFinalNormaLaboral != null) {
            if (fechaInicialNormaLaboral.after(fechaParametro) && fechaInicialNormaLaboral.before(fechaFinalNormaLaboral)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMFRegistroModuloLegislacionLaboral() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMFFinalLegislacionLaboral != null && fechaMFInicialLegislacionLaboral != null) {
            if (fechaMFInicialLegislacionLaboral.after(fechaParametro) && fechaMFInicialLegislacionLaboral.before(fechaMFFinalLegislacionLaboral)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMIRegistroModuloLegislacionLaboral() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMIFinalLegislacionLaboral != null && fechaMIInicialLegislacionLaboral != null) {
            if (fechaMIInicialLegislacionLaboral.after(fechaParametro) && fechaMIInicialLegislacionLaboral.before(fechaMIFinalLegislacionLaboral)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloUbicacion() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialUbicacion != null && fechaFinalUbicacion != null) {
            if (fechaInicialUbicacion.after(fechaParametro) && fechaInicialUbicacion.before(fechaFinalUbicacion)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloAfiliacion() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialAfiliacion != null && fechaFinalAfiliacion != null) {
            if (fechaInicialAfiliacion.after(fechaParametro) && fechaInicialAfiliacion.before(fechaFinalAfiliacion)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloFormaPago() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialFormaPago != null && fechaFinalFormaPago != null) {
            if (fechaInicialFormaPago.after(fechaParametro) && fechaInicialFormaPago.before(fechaFinalFormaPago)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloMvrs() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaInicialMvrs != null && fechaFinalMvrs != null) {
            if (fechaInicialMvrs.after(fechaParametro) && fechaInicialMvrs.before(fechaFinalMvrs)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMFRegistroModuloSets() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMFFinalSets != null && fechaMFInicialSets != null) {
            if (fechaMFInicialSets.after(fechaParametro) && fechaMFInicialSets.before(fechaMFFinalSets)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMIRegistroModuloSets() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMIFinalSets != null && fechaMIInicialSets != null) {
            if (fechaMIInicialSets.after(fechaParametro) && fechaMIInicialSets.before(fechaMIFinalSets)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMFRegistroModuloVacacion() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMFFinalVacacion != null && fechaMFInicialVacacion != null) {
            if (fechaMFInicialVacacion.after(fechaParametro) && fechaMFInicialVacacion.before(fechaMFFinalVacacion)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasMIRegistroModuloVacacion() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaMIFinalVacacion != null && fechaMIInicialVacacion != null) {
            if (fechaMIInicialVacacion.after(fechaParametro) && fechaMIInicialVacacion.before(fechaMIFinalVacacion)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloJornadaLaboral() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaFinalJornadaLaboral != null && fechaInicialJornadaLaboral != null) {
            if (fechaInicialJornadaLaboral.after(fechaParametro) && fechaInicialJornadaLaboral.before(fechaFinalJornadaLaboral)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechasRegistroModuloFechaRetiro() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (fechaFinalFechaRetiro != null && fechaInicialFechaRetiro != null) {
            if (fechaInicialFechaRetiro.after(fechaParametro) && fechaInicialFechaRetiro.before(fechaFinalFechaRetiro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarParametrosCargo(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("ESTRUCTURA")) {
            vigenciaCargoBA.getEstructura().setNombre(auxEstructuraVigenciaCargo);
            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCargoBA.setEstructura(lovEstructuras.get(indiceUnicoElemento));
                lovEstructuras = null;
                getLovEstructuras();
                context.update("form:tabViewCosto:parametroEstructuraModCargo");
                context.update("form:tabViewCosto:parametroCentroCostoModCargo");
            } else {
                permitirIndexVigenciaCargo = false;
                context.update("form:EstructuraCargoDialogo");
                context.execute("EstructuraCargoDialogo.show()");
            }
        }
        if (cualParametro.equals("CARGO")) {
            vigenciaCargoBA.getCargo().setNombre(auxCargoVigenciaCargo);
            for (int i = 0; i < lovCargos.size(); i++) {
                if (lovCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCargoBA.setCargo(lovCargos.get(indiceUnicoElemento));
                lovCargos = null;
                getLovCargos();
                context.update("form:tabViewCosto:parametroCargoModCargo");
            } else {
                permitirIndexVigenciaCargo = false;
                context.update("form:CargoCargoDialogo");
                context.execute("CargoCargoDialogo.show()");
            }
        }
        if (cualParametro.equals("PAPEL")) {
            vigenciaCargoBA.getPapel().setDescripcion(auxPapelVigenciaCargo);
            for (int i = 0; i < lovPapeles.size(); i++) {
                if (lovPapeles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCargoBA.setPapel(lovPapeles.get(indiceUnicoElemento));
                lovPapeles = null;
                getLovPapeles();
                context.update("form:tabViewCosto:parametroPapelModCargo");
            } else {
                permitirIndexVigenciaCargo = false;
                context.update("form:PapelCargoDialogo");
                context.execute("PapelCargoDialogo.show()");
            }
        }
        if (cualParametro.equals("MOTIVO")) {
            vigenciaCargoBA.getMotivocambiocargo().setNombre(auxMotivoCambioCargoVigenciaCargo);
            for (int i = 0; i < lovMotivosCambiosCargos.size(); i++) {
                if (lovMotivosCambiosCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCargoBA.setMotivocambiocargo(lovMotivosCambiosCargos.get(indiceUnicoElemento));
                lovMotivosCambiosCargos = null;
                getLovMotivosCambiosCargos();
                context.update("form:tabViewCosto:parametroMotivoModCargo");
            } else {
                permitirIndexVigenciaCargo = false;
                context.update("form:MotivoCargoDialogo");
                context.execute("MotivoCargoDialogo.show()");
            }
        }
        if (cualParametro.equals("JEFE")) {
            vigenciaCargoBA.getEmpleadojefe().getPersona().setNombreCompleto(auxJefeVigenciaCargo);
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaCargoBA.setEmpleadojefe(lovEmpleados.get(indiceUnicoElemento));
                lovEmpleados = null;
                getLovEmpleados();
                context.update("form:tabViewCosto:parametroJefeModCargo");
            } else {
                permitirIndexVigenciaCargo = false;
                context.update("form:JefeCargoDialogo");
                context.execute("JefeCargoDialogo.show()");
            }
        }
    }

    public void modificarParametrosCentroCosto(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("LOCALIZACION")) {
            vigenciaLocalizacionBA.getLocalizacion().setNombre(auxLocalizacionVigenciaLocalizacion);
            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaLocalizacionBA.setLocalizacion(lovEstructuras.get(indiceUnicoElemento));
                lovEstructuras = null;
                getLovEstructuras();
                context.update("form:tabViewCentroCosto:parametroLocalizacionModCentroCosto");
            } else {
                permitirIndexVigenciaLocalizacion = false;
                context.update("form:LocalizacionCentroCostoDialogo");
                context.execute("LocalizacionCentroCostoDialogo.show()");
            }
        }
        if (cualParametro.equals("MOTIVO")) {
            vigenciaLocalizacionBA.getMotivo().setDescripcion(auxMotivoLocalizacionVigenciaLocalizacion);
            for (int i = 0; i < lovMotivosLocalizaciones.size(); i++) {
                if (lovMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaLocalizacionBA.setMotivo(lovMotivosLocalizaciones.get(indiceUnicoElemento));
                lovMotivosLocalizaciones = null;
                getLovMotivosLocalizaciones();
                context.update("form:tabViewCentroCosto:parametroMotivoModCentroCosto");
            } else {
                permitirIndexVigenciaLocalizacion = false;
                context.update("form:MotivoCentroCostoDialogo");
                context.execute("MotivoCentroCostoDialogo.show()");
            }
        }
    }

    public void modificarParametrosSueldo(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("TIPOSUELDO")) {
            vigenciaSueldoBA.getTiposueldo().setDescripcion(auxTipoSueldoVigenciaSueldo);
            for (int i = 0; i < lovTiposSueldos.size(); i++) {
                if (lovTiposSueldos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSueldoBA.setTiposueldo(lovTiposSueldos.get(indiceUnicoElemento));
                lovTiposSueldos = null;
                getLovTiposSueldos();
                context.update("form:tabViewSueldo:parametroTipoSueldoModSueldo");
            } else {
                permitirIndexVigenciaSueldo = false;
                context.update("form:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
            }
        }
        if (cualParametro.equals("MOTIVO")) {
            vigenciaSueldoBA.getMotivocambiosueldo().setNombre(auxMotivoCambioSueldoVigenciaSueldo);
            for (int i = 0; i < lovMotivosCambiosSueldos.size(); i++) {
                if (lovMotivosCambiosSueldos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSueldoBA.setMotivocambiosueldo(lovMotivosCambiosSueldos.get(indiceUnicoElemento));
                lovMotivosCambiosSueldos = null;
                getLovMotivosCambiosSueldos();
                context.update("form:tabViewSueldo:parametroMotivoModSueldo");
            } else {
                permitirIndexVigenciaSueldo = false;
                context.update("form:MotivoSueldoDialogo");
                context.execute("MotivoSueldoDialogo.show()");
            }
        }
    }

    public void modificarParametrosFechaContrato(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("TIPOCONTRATO")) {
            vigenciaTipoContratoBA.getTipocontrato().setNombre(auxTipoContratoVigenciaTipoContrato);
            for (int i = 0; i < lovTiposContratos.size(); i++) {
                if (lovTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaTipoContratoBA.setTipocontrato(lovTiposContratos.get(indiceUnicoElemento));
                lovTiposContratos = null;
                getLovTiposContratos();
                context.update("form:tabViewFechaContrato:parametroTipoContratoModFechaContrato");
            } else {
                permitirIndexVigenciaTipoContrato = false;
                context.update("form:TipoContratoFechaContratoDialogo");
                context.execute("TipoContratoFechaContratoDialogo.show()");
            }
        }
        if (cualParametro.equals("MOTIVO")) {
            vigenciaTipoContratoBA.getMotivocontrato().setNombre(auxMotivoContratoVigenciaTipoContrato);
            for (int i = 0; i < lovMotivosContratos.size(); i++) {
                if (lovMotivosContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaTipoContratoBA.setMotivocontrato(lovMotivosContratos.get(indiceUnicoElemento));
                lovMotivosContratos = null;
                getLovMotivosContratos();
                context.update("form:tabViewFechaContrato:parametroMotivoModFechaContrato");
            } else {
                permitirIndexVigenciaTipoContrato = false;
                context.update("form:MotivoFechaContratoDialogo");
                context.execute("MotivoFechaContratoDialogo.show()");
            }
        }
    }

    public void modificarParametrosTipoTrabajador(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("TIPOTRABAJADOR")) {
            vigenciaTipoTrabajadorBA.getTipotrabajador().setNombre(auxTipoTrabajadorVigenciaTipoTrabajador);
            for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaTipoTrabajadorBA.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                lovTiposTrabajadores = null;
                getLovTiposTrabajadores();
                context.update("form:tabViewTipoTrabajador:parametroTipoTrabajadorModTipoContrato");
            } else {
                permitirIndexVigenciaTipoTrabajador = false;
                context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
            }
        }
    }

    public void modificarParametrosTipoSalario(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("REFORMA")) {
            vigenciaReformaLaboralBA.getReformalaboral().setNombre(auxReformaLaboralVigenciaReformaLaboral);
            for (int i = 0; i < lovReformasLaborales.size(); i++) {
                if (lovReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaReformaLaboralBA.setReformalaboral(lovReformasLaborales.get(indiceUnicoElemento));
                lovReformasLaborales = null;
                getLovReformasLaborales();
                context.update("form:tabViewTipoSalario:parametroReformaLaboralModTipoSalario");
            } else {
                permitirIndexVigenciaTipoTrabajador = false;
                context.update("form:ReformaLaboralTipoSalarioDialogo");
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
            }
        }
    }

    public void modificarParametrosNormaLaboral(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("NORMA")) {
            vigenciaNormaEmpleadoBA.getNormalaboral().setNombre(auxNormaLaboralVigenciaNormaEmpleado);
            for (int i = 0; i < lovNormasLaborales.size(); i++) {
                if (lovNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaNormaEmpleadoBA.setNormalaboral(lovNormasLaborales.get(indiceUnicoElemento));
                lovNormasLaborales = null;
                getLovNormasLaborales();
                context.update("form:tabViewNormaLaboral:parametroNormaLaboralModNormaLaboral");
            } else {
                permitirIndexVigenciaNormaEmpleado = false;
                context.update("form:NormaLaboralNormaLaboralDialogo");
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
            }
        }
    }

    public void modificarParametrosLegislacionLaboral(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("CONTRATO")) {
            vigenciaContratoBA.getContrato().setDescripcion(auxContratoVigenciaContrato);
            for (int i = 0; i < lovContratos.size(); i++) {
                if (lovContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaContratoBA.setContrato(lovContratos.get(indiceUnicoElemento));
                lovContratos = null;
                getLovContratos();
                context.update("form:tabViewLegislacionLaboral:parametroLegislacionModLegislacionLaboral");
            } else {
                permitirIndexVigenciaContrato = false;
                context.update("form:LegislacionLegislacionLaboralDialogo");
                context.execute("LegislacionLegislacionLaboralDialogo.show()");
            }
        }
    }

    public void modificarParametrosUbicacion(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("UBICACION")) {
            vigenciaUbicacionBA.getUbicacion().setDescripcion(auxUbicacionVigenciaUbicacion);
            for (int i = 0; i < lovUbicacionesGeograficas.size(); i++) {
                if (lovUbicacionesGeograficas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaUbicacionBA.setUbicacion(lovUbicacionesGeograficas.get(indiceUnicoElemento));
                lovUbicacionesGeograficas = null;
                getLovUbicacionesGeograficas();
                context.update("form:tabViewUbicacion:parametroUbicacionModUbicacion");
                context.update("form:tabViewUbicacion:parametroCiudadModUbicacion");
            } else {
                permitirIndexVigenciaUbicacion = false;
                context.update("form:UbicacionUbicacionDialogo");
                context.execute("UbicacionUbicacionDialogo.show()");
            }
        }
    }

    public void modificarParametrosAfiliacion(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("TERCERO")) {
            vigenciaAfiliacionBA.getTercerosucursal().setDescripcion(auxTerceroVigenciaAfiliacion3);
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaAfiliacionBA.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales = null;
                getLovTercerosSucursales();
                context.update("form:tabViewAfiliacion:parametroTerceroModAfiliacion");
            } else {
                permitirIndexVigenciaAfiliacion = false;
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        }
        if (cualParametro.equals("TIPOENTIDAD")) {
            vigenciaAfiliacionBA.getTipoentidad().setNombre(auxTipoEntidadVigenciaAfiliacion3);
            for (int i = 0; i < lovTiposEntidades.size(); i++) {
                if (lovTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaAfiliacionBA.setTipoentidad(lovTiposEntidades.get(indiceUnicoElemento));
                lovTiposEntidades = null;
                getLovTiposEntidades();
                context.update("form:tabViewAfiliacion:parametroTipoEntidadModAfiliacion");
            } else {
                permitirIndexVigenciaAfiliacion = false;
                context.update("form:TipoEntidadAfiliacionDialogo");
                context.execute("TipoEntidadAfiliacionDialogo.show()");
            }
        }
        if (cualParametro.equals("ESTADO")) {
            vigenciaAfiliacionBA.getEstadoafiliacion().setNombre(auxEstadoVigenciaAfiliacion3);
            for (int i = 0; i < lovEstadosAfiliaciones.size(); i++) {
                if (lovEstadosAfiliaciones.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaAfiliacionBA.setEstadoafiliacion(lovEstadosAfiliaciones.get(indiceUnicoElemento));
                lovEstadosAfiliaciones = null;
                getLovEstadosAfiliaciones();
                context.update("form:tabViewAfiliacion:parametroEstadoModAfiliacion");
            } else {
                permitirIndexVigenciaAfiliacion = false;
                context.update("form:EstadoAfiliacionDialogo");
                context.execute("EstadoAfiliacionDialogo.show()");
            }
        }
    }

    public void modificarParametrosFormaPago(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("PERIODICIDAD")) {
            vigenciaFormaPagoBA.getFormapago().setNombre(auxPeriodicidadVigenciaFormaPago);
            for (int i = 0; i < lovPeriodicidades.size(); i++) {
                if (lovPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaFormaPagoBA.setFormapago(lovPeriodicidades.get(indiceUnicoElemento));
                lovPeriodicidades = null;
                getLovPeriodicidades();
                context.update("form:tabViewFormaPago:parametroFormaPagoModFormaPago");
            } else {
                permitirIndexVigenciaFormaPago = false;
                context.update("form:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
            }
        }
        if (cualParametro.equals("SUCURSAL")) {
            vigenciaFormaPagoBA.getSucursal().setNombre(auxSucursalVigenciaFormaPago);
            for (int i = 0; i < lovSucursales.size(); i++) {
                if (lovSucursales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaFormaPagoBA.setSucursal(lovSucursales.get(indiceUnicoElemento));
                lovSucursales = null;
                getLovSucursales();
                context.update("form:tabViewFormaPago:parametroSucursalModFormaPago");
            } else {
                permitirIndexVigenciaFormaPago = false;
                context.update("form:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
            }
        }
    }

    public void modificarParametrosMvrs(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("MOTIVO")) {
            mvrsBA.getMotivo().setNombre(auxMotivoMvrs);
            for (int i = 0; i < lovMotivosMvrs.size(); i++) {
                if (lovMotivosMvrs.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                mvrsBA.setMotivo(lovMotivosMvrs.get(indiceUnicoElemento));
                lovMotivosMvrs = null;
                getLovMotivosMvrs();
                context.update("form:tabViewMvrs:parametroMotivoModMvrs");
            } else {
                permitirIndexMvrs = false;
                context.update("form:MotivoMvrsDialogo");
                context.execute("MotivoMvrsDialogo.show()");
            }
        }
    }

    public void modificarParametrosJornadaLaboral(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("JORNADA")) {
            vigenciaJornadaBA.getJornadatrabajo().setDescripcion(auxJornadaJornadaLaboral);
            for (int i = 0; i < lovJornadasLaborales.size(); i++) {
                if (lovJornadasLaborales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaJornadaBA.setJornadatrabajo(lovJornadasLaborales.get(indiceUnicoElemento));
                lovJornadasLaborales = null;
                getLovJornadasLaborales();
                context.update("form:tabViewJornadaLaboral:parametroJornadaModJornadaLaboral");
            } else {
                permitirIndexVigenciaJornada = false;
                context.update("form:JornadaJornadaLaboralDialogo");
                context.execute("JornadaJornadaLaboralDialogo.show()");
            }
        }
    }

    public void modificarParametrosFechaRetiro(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("MOTIVO")) {
            motivoRetiroBA.setNombre(auxMotivoMotivoRetiro);
            for (int i = 0; i < lovMotivosRetiros.size(); i++) {
                if (lovMotivosRetiros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                motivoRetiroBA = lovMotivosRetiros.get(indiceUnicoElemento);
                lovMotivosRetiros = null;
                getLovMotivosRetiros();
                context.update("form:tabViewFechaRetiro:parametroMotivoModFechaRetiro");
            } else {
                permitirIndexMotivoRetiro = false;
                context.update("form:MotivoFechaRetiroDialogo");
                context.execute("MotivoFechaRetiroDialogo.show()");
            }
        }
    }

    public void modificarSueldosModuloSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (sueldoMaxSueldo != null && sueldoMinSueldo != null) {
            if (sueldoMaxSueldo.intValue() < sueldoMinSueldo.intValue()) {
                sueldoMaxSueldo = auxSueldoMaximoSueldo;
                sueldoMinSueldo = auxSueldoMinimoSueldo;
                context.update("form:tabViewSueldo:parametroSueldoMinimoModSueldo");
                context.update("form:tabViewSueldo:parametroSueldoMaximoModSueldo");
                context.execute("errorSueldos.show()");
                System.out.println("Error modificarSueldosModuloSueldo");
            }
        }
    }

    public void modificarSueldosModuloMvrs() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (sueldoMaxMvrs != null && sueldoMinMvrs != null) {
            if (sueldoMaxMvrs.intValue() < sueldoMinMvrs.intValue()) {
                sueldoMaxMvrs = auxSueldoMaximoMvrs;
                sueldoMinMvrs = auxSueldoMinimoMvrs;
                context.update("form:tabViewMvrs:parametroSueldoMinimoModMvrs");
                context.update("form:tabViewMvrs:parametroSueldoMaximoModMvrs");
                context.execute("errorSueldos.show()");
                System.out.println("Error modificarSueldosModuloMvrs");
            }
        }
    }

    public void modificarPromediosModuloSets() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (promedioMaximoSets != null && promedioMinimoSets != null) {
            if (promedioMaximoSets.intValue() < promedioMinimoSets.intValue()) {
                promedioMaximoSets = auxPromedioMaximoSets;
                promedioMinimoSets = auxPromedioMinimoSets;
                context.update("form:tabViewSets:parametroPromedioMaxModSets");
                context.update("form:tabViewSets:parametroPromedioMinModSets");
                context.execute("errorPromedios.show()");
                System.out.println("Error modificarPromediosModuloSets");
            }
        }
    }

    public void actualizarParametroMotivoFechaRetiro() {
        motivoRetiroBA = motivoRetiroSeleccionado;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFechaRetiro:parametroMotivoModFechaRetiro");
        motivoRetiroSeleccionado = null;
        filtrarLovMotivosRetiros = null;
        casillaMotivoRetiro = -1;
        aceptar = true;
        permitirIndexMotivoRetiro = true;
        context.update("form:MotivoFechaRetiroDialogo");
        context.update("form:lovMotivoFechaRetiro");
        context.update("form:aceptarMRFR");
        context.execute("MotivoFechaRetiroDialogo.hide()");
    }

    public void cancelarParametroMotivoFechaRetiro() {
        motivoRetiroSeleccionado = null;
        filtrarLovMotivosRetiros = null;
        casillaMotivoRetiro = -1;
        aceptar = true;
        permitirIndexMotivoRetiro = true;
    }

    public void actualizarParametroJornadaJornadaLaboral() {
        vigenciaJornadaBA.setJornadatrabajo(jornadaLaboralSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewJornadaLaboral:parametroJornadaModJornadaLaboral");
        jornadaLaboralSeleccionada = null;
        filtrarLovJornadasLaborales = null;
        casillaVigenciaJornadaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaJornada = true;
        context.update("form:JornadaJornadaLaboralDialogo");
        context.update("form:lovJornadaJornadaLaboral");
        context.update("form:aceptarJLJL");
        context.execute("JornadaJornadaLaboralDialogo.hide()");
    }

    public void cancelarParametroJornadaJornadaLaboral() {
        jornadaLaboralSeleccionada = null;
        filtrarLovJornadasLaborales = null;
        casillaVigenciaJornadaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaJornada = true;
    }

    public void actualizarParametroMotivoMvrs() {
        mvrsBA.setMotivo(motivoMvrsSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewMvrs:parametroMotivoModMvrs");
        motivoMvrsSeleccionado = null;
        filtrarLovMotivosMvrs = null;
        casillaMvrs = -1;
        aceptar = true;
        permitirIndexMvrs = true;
        context.update("form:MotivoMvrsDialogo");
        context.update("form:lovMotivoMvrs");
        context.update("form:aceptarMMVR");
        context.execute("MotivoMvrsDialogo.hide()");
    }

    public void cancelarParametroMotivoMvrs() {
        motivoMvrsSeleccionado = null;
        filtrarLovMotivosMvrs = null;
        casillaMvrs = -1;
        aceptar = true;
        permitirIndexMvrs = true;
    }

    public void actualizarParametroPeriodicidadFormaPago() {
        vigenciaFormaPagoBA.setFormapago(periodicidadSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFormaPago:parametroFormaPagoModFormaPago");
        periodicidadSeleccionada = null;
        filtrarLovPeriodicidades = null;
        casillaVigenciaFormaPago = -1;
        aceptar = true;
        permitirIndexVigenciaFormaPago = true;
        context.update("form:PeriodicidadFormaPagoDialogo");
        context.update("form:lovPeriodicidadFormaPago");
        context.update("form:aceptarPFP");
        context.execute("PeriodicidadFormaPagoDialogo.hide()");
    }

    public void cancelarParametroPeriodicidadFormaPago() {
        periodicidadSeleccionada = null;
        filtrarLovPeriodicidades = null;
        casillaVigenciaFormaPago = -1;
        aceptar = true;
        permitirIndexVigenciaFormaPago = true;
    }

    public void actualizarParametroSucursalFormaPago() {
        vigenciaFormaPagoBA.setSucursal(sucursalSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFormaPago:parametroSucursalModFormaPago");
        sucursalSeleccionada = null;
        filtrarLovSucursales = null;
        casillaVigenciaFormaPago = -1;
        aceptar = true;
        permitirIndexVigenciaFormaPago = true;
        context.update("form:SucursalFormaPagoDialogo");
        context.update("form:lovSucursalFormaPago");
        context.update("form:aceptarSFP");
        context.execute("SucursalFormaPagoDialogo.hide()");
    }

    public void cancelarParametroSucursalFormaPago() {
        sucursalSeleccionada = null;
        filtrarLovSucursales = null;
        casillaVigenciaFormaPago = -1;
        aceptar = true;
        permitirIndexVigenciaFormaPago = true;
    }

    public void actualizarParametroEstadoAfiliacion() {
        vigenciaAfiliacionBA.setEstadoafiliacion(estadoAfiliacionSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewAfiliacion:parametroEstadoModAfiliacion");
        estadoAfiliacionSeleccionado = null;
        filtrarLovEstadosAfiliaciones = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
        context.update("form:EstadoAfiliacionDialogo");
        context.update("form:lovEstadoAfiliacion");
        context.update("form:aceptarEA");
        context.execute("EstadoAfiliacionDialogo.hide()");
    }

    public void cancelarParametroEstadoAfiliacion() {
        estadoAfiliacionSeleccionado = null;
        filtrarLovEstadosAfiliaciones = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
    }

    public void actualizarParametroTerceroAfiliacion() {
        vigenciaAfiliacionBA.setTercerosucursal(terceroSucursalSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewAfiliacion:parametroTerceroModAfiliacion");
        terceroSucursalSeleccionado = null;
        filtrarLovTercerosSucursales = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroTerceroAfiliacion() {
        terceroSucursalSeleccionado = null;
        filtrarLovTercerosSucursales = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
    }

    public void actualizarParametroTipoEntidadAfiliacion() {
        vigenciaAfiliacionBA.setTipoentidad(tipoEntidadSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewAfiliacion:parametroTipoEntidadModAfiliacion");
        tipoEntidadSeleccionado = null;
        filtrarLovTiposEntidades = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
        context.update("form:TipoEntidadAfiliacionDialogo");
        context.update("form:lovTipoEntidadAfiliacion");
        context.update("form:aceptarTEA");
        context.execute("TipoEntidadAfiliacionDialogo.hide()");
    }

    public void cancelarParametroTipoEntidadAfiliacion() {
        tipoEntidadSeleccionado = null;
        filtrarLovTiposEntidades = null;
        casillaVigenciaAfiliacion = -1;
        aceptar = true;
        permitirIndexVigenciaAfiliacion = true;
    }

    public void actualizarParametroUbicacionUbicacion() {
        vigenciaUbicacionBA.setUbicacion(ubicacionGeograficaSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewUbicacion:parametroUbicacionModUbicacion");
        context.update("form:tabViewUbicacion:parametroCiudadModUbicacion");
        ubicacionGeograficaSeleccionada = null;
        filtrarLovUbicacionesGeograficas = null;
        casillaVigenciaUbicacion = -1;
        aceptar = true;
        permitirIndexVigenciaUbicacion = true;
        context.update("form:UbicacionUbicacionDialogo");
        context.update("form:lovUbicacionUbicacion");
        context.update("form:aceptarUUG");
        context.execute("UbicacionUbicacionDialogo.hide()");
    }

    public void cancelarParametroUbicacionUbicacion() {
        ubicacionGeograficaSeleccionada = null;
        filtrarLovUbicacionesGeograficas = null;
        casillaVigenciaUbicacion = -1;
        aceptar = true;
        permitirIndexVigenciaUbicacion = true;
    }

    public void actualizarParametroContratoLegislacionLaboral() {
        vigenciaContratoBA.setContrato(contratoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewLegislacionLaboral:parametroLegislacionModLegislacionLaboral");
        contratoSeleccionado = null;
        filtrarLovContratos = null;
        casillaVigenciaContrato = -1;
        aceptar = true;
        permitirIndexVigenciaContrato = true;
        context.update("form:LegislacionLegislacionLaboralDialogo");
        context.update("form:lovLegislacionLegislacionLaboral");
        context.update("form:aceptarLLL");
        context.execute("LegislacionLegislacionLaboralDialogo.hide()");
    }

    public void cancelarParametroContratoLegislacionLaboral() {
        contratoSeleccionado = null;
        filtrarLovContratos = null;
        casillaVigenciaContrato = -1;
        aceptar = true;
        permitirIndexVigenciaContrato = true;
    }

    public void actualizarParametroNormaLaboralNormaLaboral() {
        vigenciaNormaEmpleadoBA.setNormalaboral(normaLaboralSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewNormaLaboral:parametroNormaLaboralModNormaLaboral");
        normaLaboralSeleccionada = null;
        filtrarLovNormasLaborales = null;
        casillaVigenciaNormaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaNormaEmpleado = true;
        context.update("form:NormaLaboralNormaLaboralDialogo");
        context.update("form:lovNormaLaboralNormaLaboral");
        context.update("form:aceptarNLNL");
        context.execute("NormaLaboralNormaLaboralDialogo.hide()");
    }

    public void cancelarParametroNormaLaboralNormaLaboral() {
        normaLaboralSeleccionada = null;
        filtrarLovNormasLaborales = null;
        casillaVigenciaNormaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaNormaEmpleado = true;
    }

    public void actualizarParametroReformaLaboralTipoSalario() {
        vigenciaReformaLaboralBA.setReformalaboral(reformaLaboralSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewTipoSalario:parametroReformaLaboralModTipoSalario");
        reformaLaboralSeleccionada = null;
        filtrarLovReformasLaborales = null;
        casillaVigenciaReformaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaReformaLaboral = true;
        context.update("form:ReformaLaboralTipoSalarioDialogo");
        context.update("form:lovReformaLaboralTipoSalario");
        context.update("form:aceptarRLTS");
        context.execute("ReformaLaboralTipoSalarioDialogo.hide()");
    }

    public void cancelarParametroReformaLaboralTipoSalario() {
        reformaLaboralSeleccionada = null;
        filtrarLovReformasLaborales = null;
        casillaVigenciaReformaLaboral = -1;
        aceptar = true;
        permitirIndexVigenciaReformaLaboral = true;
    }

    public void actualizarParametroTipoTrabajadorTipoTrabajador() {
        vigenciaTipoTrabajadorBA.setTipotrabajador(tipoTrabajadorSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewTipoTrabajador:parametroTipoTrabajadorModTipoTrabajador");
        tipoTrabajadorSeleccionado = null;
        filtrarLovTiposTrabajadores = null;
        casillaVigenciaTipoTrabajador = -1;
        aceptar = true;
        permitirIndexVigenciaTipoTrabajador = true;
        context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
        context.update("form:lovTipoTrabajadorTipoTrabajador");
        context.update("form:aceptarTTTT");
        context.execute("TipoTrabajadorTipoTrabajadorDialogo.hide()");
    }

    public void cancelarParametroTipoTrabajadorTipoTrabajador() {
        tipoTrabajadorSeleccionado = null;
        filtrarLovTiposTrabajadores = null;
        casillaVigenciaTipoTrabajador = -1;
        aceptar = true;
        permitirIndexVigenciaTipoTrabajador = true;
    }

    public void actualizarParametroTipoContratoFechaContrato() {
        vigenciaTipoContratoBA.setTipocontrato(tipoContratoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFechaContrato:parametroTipoContratoModFechaContrato");
        tipoContratoSeleccionado = null;
        filtrarLovTiposContratos = null;
        casillaVigenciaTipoContrato = -1;
        aceptar = true;
        permitirIndexVigenciaTipoContrato = true;
        context.update("form:TipoContratoFechaContratoDialogo");
        context.update("form:lovTipoContratoFechaContrato");
        context.update("form:aceptarTCFC");
        context.execute("TipoContratoFechaContratoDialogo.hide()");
    }

    public void cancelarParametroTipoContratoFechaContrato() {
        tipoContratoSeleccionado = null;
        filtrarLovTiposContratos = null;
        casillaVigenciaTipoContrato = -1;
        aceptar = true;
        permitirIndexVigenciaTipoContrato = true;
    }

    public void actualizarParametroMotivoFechaContrato() {
        vigenciaTipoContratoBA.setMotivocontrato(motivoContratoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFechaContrato:parametroMotivoModFechaContrato");
        motivoContratoSeleccionado = null;
        filtrarLovMotivosContratos = null;
        casillaVigenciaTipoContrato = -1;
        aceptar = true;
        permitirIndexVigenciaTipoContrato = true;
        context.update("form:MotivoFechaContratoDialogo");
        context.update("form:lovMotivoFechaContrato");
        context.update("form:aceptarMFC");
        context.execute("MotivoFechaContratoDialogo.hide()");
    }

    public void cancelarParametroMotivoFechaContrato() {
        motivoContratoSeleccionado = null;
        filtrarLovMotivosContratos = null;
        casillaVigenciaTipoContrato = -1;
        aceptar = true;
        permitirIndexVigenciaTipoContrato = true;
    }

    public void actualizarParametroTipoSueldoSueldo() {
        vigenciaSueldoBA.setTiposueldo(tipoSueldoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewSueldo:parametroTipoSueldoModSueldo");
        tipoSueldoSeleccionado = null;
        filtrarLovTiposSueldos = null;
        casillaVigenciaSueldo = -1;
        aceptar = true;
        permitirIndexVigenciaSueldo = true;
        context.update("form:TipoSueldoSueldoDialogo");
        context.update("form:lovTipoSueldoSueldo");
        context.update("form:aceptarTSS");
        context.execute("TipoSueldoSueldoDialogo.hide()");
    }

    public void cancelarParametroTipoSueldoSueldo() {
        tipoSueldoSeleccionado = null;
        filtrarLovTiposSueldos = null;
        casillaVigenciaSueldo = -1;
        aceptar = true;
        permitirIndexVigenciaSueldo = true;
    }

    public void actualizarParametroMotivoSueldo() {
        vigenciaSueldoBA.setMotivocambiosueldo(motivoSueldoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewSueldo:parametroMotivoModSueldo");
        motivoSueldoSeleccionado = null;
        filtrarLovMotivosCambiosSueldos = null;
        casillaVigenciaSueldo = -1;
        aceptar = true;
        permitirIndexVigenciaSueldo = true;
        context.update("form:MotivoSueldoDialogo");
        context.update("form:lovMotivoSueldo");
        context.update("form:aceptarMSS");
        context.execute("MotivoSueldoDialogo.hide()");
    }

    public void cancelarParametroMotivoSueldo() {
        motivoSueldoSeleccionado = null;
        filtrarLovMotivosCambiosSueldos = null;
        casillaVigenciaSueldo = -1;
        aceptar = true;
        permitirIndexVigenciaSueldo = true;
    }

    public void actualizarParametroLocalizacionCentroCosto() {
        vigenciaLocalizacionBA.setLocalizacion(estructuraSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCentroCosto:parametroLocalizacionModCentroCosto");
        estructuraSeleccionada = null;
        filtrarLovEstructuras = null;
        casillaVigenciaLocalizacion = -1;
        aceptar = true;
        permitirIndexVigenciaLocalizacion = true;
        context.update("form:LocalizacionCentroCostoDialogo");
        context.update("form:lovLocalizacionCentroCosto");
        context.update("form:aceptarECC");
        context.execute("LocalizacionCentroCostoDialogo.hide()");
        System.out.println("tabActivaCentroCosto : " + tabActivaCentroCosto);
    }

    public void cancelarParametroLocalizacionCentroCosto() {
        estructuraSeleccionada = null;
        filtrarLovEstructuras = null;
        casillaVigenciaLocalizacion = -1;
        aceptar = true;
        permitirIndexVigenciaLocalizacion = true;
    }

    public void actualizarParametroMotivoCentroCosto() {
        vigenciaLocalizacionBA.setMotivo(motivoLocalizacionSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCentroCosto:parametroMotivoModCentroCosto");
        motivoLocalizacionSeleccionado = null;
        filtrarLovMotivosLocalizaciones = null;
        casillaVigenciaLocalizacion = -1;
        aceptar = true;
        permitirIndexVigenciaLocalizacion = true;
        context.update("form:MotivoCentroCostoDialogo");
        context.update("form:lovMotivoCentroCosto");
        context.update("form:aceptarMCC");
        context.execute("MotivoCentroCostoDialogo.hide()");
        System.out.println("tabActivaCentroCosto : " + tabActivaCentroCosto);
    }

    public void cancelarParametroMotivoCentroCosto() {
        motivoLocalizacionSeleccionado = null;
        filtrarLovMotivosLocalizaciones = null;
        casillaVigenciaLocalizacion = -1;
        aceptar = true;
        permitirIndexVigenciaLocalizacion = true;
    }

    public void actualizarParametroEstructuraCargo() {
        vigenciaCargoBA.setEstructura(estructuraSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto:parametroEstructuraModCargo");
        context.update("form:tabViewCosto:parametroCentroCostoModCargo");
        estructuraSeleccionada = null;
        filtrarLovEstructuras = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
        context.update("form:EstructuraCargoDialogo");
        context.update("form:lovEstructuraCargo");
        context.update("form:aceptarEC");
        context.execute("EstructuraCargoDialogo.hide()");
    }

    public void cancelarParametroEstructuraCargo() {
        estructuraSeleccionada = null;
        filtrarLovEstructuras = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
    }

    public void actualizarParametroCargoCargo() {
        vigenciaCargoBA.setCargo(cargoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto:parametroCargoModCargo");
        cargoSeleccionado = null;
        filtrarLovCargos = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
        context.update("form:CargoCargoDialogo");
        context.update("form:lovCargoCargo");
        context.update("form:aceptarCC");
        context.execute("CargoCargoDialogo.hide()");
    }

    public void cancelarParametroCargoCargo() {
        cargoSeleccionado = null;
        filtrarLovCargos = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
    }

    public void actualizarParametroPapelCargo() {
        vigenciaCargoBA.setPapel(papelSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto:parametroPapelModCargo");
        papelSeleccionado = null;
        filtrarLovPapeles = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
        context.update("form:PapelCargoDialogo");
        context.update("form:lovPapelCargo");
        context.update("form:aceptarPC");
        context.execute("PapelCargoDialogo.hide()");
    }

    public void cancelarParametroPapelCargo() {
        papelSeleccionado = null;
        filtrarLovPapeles = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
    }

    public void actualizarParametroMotivoCargo() {
        vigenciaCargoBA.setMotivocambiocargo(motivoCambioSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto:parametroMotivoModCargo");
        motivoCambioSeleccionado = null;
        filtrarLovMotivosCambiosCargos = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
        context.update("form:MotivoCargoDialogo");
        context.update("form:lovMotivoCargo");
        context.update("form:aceptarMC");
        context.execute("MotivoCargoDialogo.hide()");
    }

    public void cancelarParametroMotivoCargo() {
        motivoCambioSeleccionado = null;
        filtrarLovMotivosCambiosCargos = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
    }

    public void actualizarParametroJefeCargo() {
        vigenciaCargoBA.setEmpleadojefe(empleadoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto:parametroJefeModCargo");
        empleadoSeleccionado = null;
        filtrarLovEmpleados = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
        context.update("form:JefeCargoDialogo");
        context.update("form:lovJefeCargo");
        context.update("form:aceptarJC");
        context.execute("JefeCargoDialogo.hide()");
    }

    public void cancelarParametroJefeCargo() {
        empleadoSeleccionado = null;
        filtrarLovEmpleados = null;
        casillaVigenciaCargo = -1;
        aceptar = true;
        permitirIndexVigenciaCargo = true;
    }

    public void changeTapCargo() {
        System.out.println("tabActivaCargos : " + tabActivaCargos);
    }

    public void changeTapCentroCosto() {
        System.out.println("tabActivaCentroCosto : " + tabActivaCentroCosto);
        if (tabActivaCentroCosto == 1) {
            auxTabActivoCentroCosto = true;
        } else {
            auxTabActivoCentroCosto = false;
        }
    }

    public void changeTapSueldo() {
        System.out.println("tabActivaSueldo : " + tabActivaSueldo);
    }

    public void changeTapFechaContrato() {
        System.out.println("tabActivaFechaContrato : " + tabActivaFechaContrato);
    }

    public void changeTapTipoTrabajador() {
        System.out.println("tabActivaTipoTrabajador : " + tabActivaTipoTrabajador);
    }

    public void changeTapTipoSalario() {
        System.out.println("tabActivaTipoSalario : " + tabActivaTipoSalario);
    }

    public void changeTapNormaLaboral() {
        System.out.println("tabActivaNormaLaboral : " + tabActivaNormaLaboral);
    }

    public void changeTapLegislacionLaboral() {
        System.out.println("tabActivaLegislacionLaboral : " + tabActivaLegislacionLaboral);
    }

    public void changeTapUbicacion() {
        System.out.println("tabActivaUbicacion : " + tabActivaUbicacion);
    }

    public void changeTapAfiliacion() {
        System.out.println("tabActivaAfiliacion : " + tabActivaAfiliacion);
    }

    public void changeTapFormaPago() {
        System.out.println("tabActivaFormaPago : " + tabActivaFormaPago);
    }

    public void changeTapMvrs() {
        System.out.println("tabActivaMvrs : " + tabActivaMvrs);
    }

    public void changeTapSets() {
        System.out.println("tabActivaSets : " + tabActivaSets);
    }

    public void changeTapVacacion() {
        System.out.println("tabActivaVacacion : " + tabActivaVacacion);
    }

    public void changeTapJornadaLaboral() {
        System.out.println("tabActivaJornadaLaboral : " + tabActivaJornadaLaboral);
    }

    public void changeTapFechaRetiro() {
        System.out.println("tabActivaFechaRetiro : " + tabActivaFechaRetiro);
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void activarCasillasFechasCargo() {
        if (tipoFechaCargo == 1) {
            fechaInicialCargo = null;
            fechaFinalCargo = null;
            auxFechaFinalCargo = null;
            auxFechaInicialCargo = null;
            activoFechasCargos = true;
        }
        if (tipoFechaCargo == 2) {
            activoFechasCargos = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCosto");
        context.update("form:tabViewCosto:tipoFechaCargo");
    }

    public void activarCasillasFechasCentroCosto() {
        if (tipoFechaCentroCosto == 1) {
            activoFechasCentroCosto = true;
            fechaInicialCentroCosto = null;
            fechaFinalCentroCosto = null;
            auxFechaFinalCentroCosto = null;
            auxFechaInicialCentroCosto = null;
        }
        if (tipoFechaCentroCosto == 2) {
            activoFechasCentroCosto = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewCentroCosto");
        context.update("form:tabViewCentroCosto:tipoFechaCentroCosto");
    }

    public void activarCasillasFechasSueldo() {
        if (tipoFechaSueldo == 1) {
            activoFechasSueldo = true;
            fechaInicialSueldo = null;
            fechaFinalSueldo = null;
            auxFechaFinalSueldo = null;
            auxFechaInicialSueldo = null;
        }
        if (tipoFechaSueldo == 2) {
            activoFechasSueldo = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewSueldo");
        context.update("form:tabViewSueldo:tipoFechaSueldo");
    }

    public void activarCasillasFechasFechaContrato() {
        if (tipoFechaFechaContrato == 1) {
            activoFechasFechaContrato = true;
            fechaInicialFechaContrato = null;
            fechaFinalFechaContrato = null;
            auxFechaFinalFechaContrato = null;
            auxFechaInicialFechaContrato = null;
        }
        if (tipoFechaFechaContrato == 2) {
            activoFechasFechaContrato = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFechaContrato");
        context.update("form:tabViewFechaContrato:tipoFechaFechaContrato");
    }

    public void activarCasillasFechasTipoTrabajador() {
        if (tipoFechaTipoTrabajador == 1) {
            activoFechasTipoTrabajador = true;
            fechaInicialTipoTrabajador = null;
            fechaFinalTipoTrabajador = null;
            auxFechaFinalTipoTrabajador = null;
            auxFechaInicialTipoTrabajador = null;
        }
        if (tipoFechaTipoTrabajador == 2) {
            activoFechasTipoTrabajador = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewTipoTrabajador");
        context.update("form:tabViewTipoTrabajador:tipoFechaTipoTrabajador");
    }

    public void activarCasillasFechasTipoSalario() {
        if (tipoFechaTipoSalario == 1) {
            activoFechasTipoSalario = true;
            fechaInicialTipoSalario = null;
            fechaFinalTipoSalario = null;
            auxFechaFinalTipoSalario = null;
            auxFechaInicialTipoSalario = null;
        }
        if (tipoFechaTipoSalario == 2) {
            activoFechasTipoSalario = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewTipoSalario");
        context.update("form:tabViewTipoSalario:tipoFechaTipoSalario");
    }

    public void activarCasillasFechasNormaLaboral() {
        if (tipoFechaNormaLaboral == 1) {
            activoFechasNormaLaboral = true;
            fechaInicialNormaLaboral = null;
            fechaFinalNormaLaboral = null;
            auxFechaFinalNormaLaboral = null;
            auxFechaInicialNormaLaboral = null;
        }
        if (tipoFechaNormaLaboral == 2) {
            activoFechasNormaLaboral = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewNormaLaboral");
        context.update("form:tabViewNormaLaboral:tipoFechaNormaLaboral");
    }

    public void activarCasillasFechasLegislacionLaboral() {
        if (tipoFechaLegislacionLaboral == 1) {
            activoFechasLegislacionLaboral = true;
            fechaMFFinalLegislacionLaboral = null;
            fechaMFInicialLegislacionLaboral = null;
            fechaMIFinalLegislacionLaboral = null;
            fechaMIInicialLegislacionLaboral = null;
            auxFechaMFFinalLegislacionLaboral = null;
            auxFechaMFInicialLegislacionLaboral = null;
            auxFechaMIFinalLegislacionLaboral = null;
            auxFechaMIInicialLegislacionLaboral = null;
        }
        if (tipoFechaLegislacionLaboral == 2) {
            activoFechasLegislacionLaboral = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewLegislacionLaboral");
        context.update("form:tabViewLegislacionLaboral:tipoFechaLegislacionLaboral");
    }

    public void activarCasillasFechasUbicacion() {
        if (tipoFechaUbicacion == 1) {
            activoFechasUbicacion = true;
            fechaInicialUbicacion = null;
            fechaFinalUbicacion = null;
            auxFechaFinalUbicacion = null;
            auxFechaInicialUbicacion = null;
        }
        if (tipoFechaUbicacion == 2) {
            activoFechasUbicacion = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewUbicacion");
        context.update("form:tabViewUbicacion:tipoFechaUbicacion");
    }

    public void activarCasillasFechasAfiliacion() {
        if (tipoFechaAfiliacion == 1) {
            activoFechasAfiliacion = true;
            fechaInicialAfiliacion = null;
            fechaFinalAfiliacion = null;
            auxFechaFinalAfiliacion = null;
            auxFechaInicialAfiliacion = null;
        }
        if (tipoFechaAfiliacion == 2) {
            activoFechasAfiliacion = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewAfiliacion");
        context.update("form:tabViewAfiliacion:tipoFechaAfiliacion");
    }

    public void activarCasillasFechasFormaPago() {
        if (tipoFechaFormaPago == 1) {
            activoFechasFormaPago = true;
            fechaInicialFormaPago = null;
            fechaFinalFormaPago = null;
            auxFechaFinalFormaPago = null;
            auxFechaInicialFormaPago = null;
        }
        if (tipoFechaFormaPago == 2) {
            activoFechasFormaPago = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewFormaPago");
        context.update("form:tabViewFormaPago:tipoFechaFormaPago");
    }

    public void activarCasillasFechasMvrs() {
        if (tipoFechaMvrs == 1) {
            activoFechasMvrs = true;
            fechaInicialMvrs = null;
            fechaFinalMvrs = null;
            auxFechaFinalMvrs = null;
            auxFechaInicialMvrs = null;
        }
        if (tipoFechaMvrs == 2) {
            activoFechasMvrs = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewMvrs");
        context.update("form:tabViewMvrs:tipoFechaMvrs");
    }

    public void activarCasillasFechasSets() {
        if (tipoFechaSets == 1) {
            activoFechasSets = true;
            fechaMFFinalSets = null;
            fechaMFInicialSets = null;
            fechaMIFinalSets = null;
            fechaMIInicialSets = null;
            auxFechaMFFinalSets = null;
            auxFechaMFInicialSets = null;
            auxFechaMIFinalSets = null;
            auxFechaMIInicialSets = null;
        }
        if (tipoFechaSets == 2) {
            activoFechasSets = false;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabViewSets");
        context.update("form:tabViewSets:tipoFechaLegislacionLaboral");
    }

    //Clase de columnas que sera cargadas en la tabla
    static public class ColumnModel implements Serializable {

        private String header;
        //Cabecera de la columna
        private String property;
        //Valor que sera mostrado en cada columna

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:resultadoBusquedaAvanzada");
        table.setValueExpression("sortBy", null);
        //update columns
        int tam = 0;
        listaResultadoBusquedaAvanzada = new ArrayList<ResultadoBusquedaAvanzada>();
        if (listaColumnasEscenarios != null) {
            tam = listaColumnasEscenarios.size();
        }
        String camposBusqueda = " secuencia, codigoempleado, primerapellido, segundoapellido, nombre";
        if (tam > 0) {
            for (int j = 0; j < tam; j++) {
                camposBusqueda = camposBusqueda + ", " + listaColumnasEscenarios.get(j).getNombrecolumna();
            }
        }
        listaColumnasBusquedaAvanzada = new ArrayList<ColumnasBusquedaAvanzada>();
        listaResultadoBusquedaAvanzada = administrarBusquedaAvanzada.obtenerQVWEmpleadosCorteParaEmpleadoCodigo(listaCodigosEmpleado, camposBusqueda);
        System.out.println("listaResultadoBusquedaAvanzada  : " + listaResultadoBusquedaAvanzada.size());
        int tamanoLista = 0;
        if (listaResultadoBusquedaAvanzada != null) {
            tamanoLista = listaResultadoBusquedaAvanzada.size();
        }
        for (int i = 0; i < tamanoLista; i++) {
            listaColumnasBusquedaAvanzada.add(new ColumnasBusquedaAvanzada("", "", "", "", "", "", "", "", "", "", "", "", "", ""));
        }
        for (int j = 0; j < tam; j++) {
            String[] palabras = listaColumnasEscenarios.get(j).getNombrecolumna().split(" ");
            nuevaColumna = palabras[0];
            for (int i = 1; i < palabras.length; i++) {
                nuevaColumna = nuevaColumna + "_" + palabras[i];
            }
            System.out.println("nuevaColumna : " + nuevaColumna);
            columnTemplate = columnTemplate + nuevaColumna + " ";
            nuevaColumna = "";
        }
        columns.clear();
        String[] valoresColumnasDeseadas = camposBusqueda.split(",");
        mapValoresColumnas.clear();
        if (tamanoLista > 0) {
            System.out.println("valoresColumnasDeseadas.length : " + valoresColumnasDeseadas.length);
            if (valoresColumnasDeseadas.length == 5) {
                createStaticColumns();
                cargarTablaColumnasEstaticas();
            }
            if (valoresColumnasDeseadas.length >= 6) {
                createStaticColumns();
                createDynamicColumns();
                cargarTablaColumnasEstaticas();
                cargarTablaColumnasDinamicas();
            }
        } else {
            createStaticColumns();
        }
        /*int tamEmpleado = listaEmpleadosResultados.size();
         for (int i = 0; i < tamEmpleado; i++) {
         listaColumnasBusquedaAvanzada.add(new ColumnasBusquedaAvanzada("", "", "", "", "", "", "", "", "", "", "", "", "", ""));
         }
         int tam = 0;
         if (listaColumnasEscenarios != null) {
         tam = listaColumnasEscenarios.size();
         }
         System.out.println("dsadsa listaColumnasEscenarios : " + tam);
         if (tam > 0) {
         for (int j = 0; j < listaColumnasEscenarios.size(); j++) {
         String[] palabras = listaColumnasEscenarios.get(j).getNombrecolumna().split(" ");
         nuevaColumna = palabras[0];
         for (int i = 1; i < palabras.length; i++) {
         nuevaColumna = nuevaColumna + "_" + palabras[i];
         }
         columnTemplate = columnTemplate + nuevaColumna + " ";
         nuevaColumna = "";
         }
         }
         String[] valoresColumnasDeseadas = columnTemplate.split(" ");
         columns.clear();
         mapValoresColumnas.clear();
         if (tamEmpleado > 0) {
         System.out.println("valoresColumnasDeseadas.length : " + valoresColumnasDeseadas.length);
         if (valoresColumnasDeseadas.length == 4) {
         createStaticColumns();
         cargarTablaColumnasEstaticas();
         }
         if (valoresColumnasDeseadas.length >= 5) {
         createStaticColumns();
         createDynamicColumns();
         cargarTablaColumnasEstaticas();
         cargarTablaColumnasDinamicas();
         }
         } else {
         listaColumnasBusquedaAvanzada = null;
         createStaticColumns();
         }*/
    }

    public void createStaticColumns() {
        String nameColumnaEstatica0 = "codigo";
        mapValoresColumnas.put(nameColumnaEstatica0, "Codigo");
        String nameColumnaEstatica1 = "primeroApellido";
        mapValoresColumnas.put(nameColumnaEstatica1, "Primer_Apellido");
        String nameColumnaEstatica2 = "segundoApellido";
        mapValoresColumnas.put(nameColumnaEstatica2, "Segundo_Apellido");
        String nameColumnaEstatica3 = "nombre";
        mapValoresColumnas.put(nameColumnaEstatica3, "Nombre");
        for (int i = 0; i < 4; i++) {
            columns.add(i, new ColumnModel("", ""));
        }
        columns.set(0, new ColumnModel("Codigo".toUpperCase(), "codigo"));
        columns.set(1, new ColumnModel("Primer_Apellido".toUpperCase(), "primeroApellido"));
        columns.set(2, new ColumnModel("Segundo_Apellido".toUpperCase(), "segundoApellido"));
        columns.set(3, new ColumnModel("Nombre".toUpperCase(), "nombre"));
    }

    public void createDynamicColumns() {
        System.out.println("columnTemplate : " + columnTemplate);
        String[] valoresColumnasDeseadas = columnTemplate.split(" ");
        int numColumna = 0;
        for (int i = 4; i < valoresColumnasDeseadas.length; i++) {
            String nameColumna = "columna" + String.valueOf(numColumna);
            System.out.println("createDynamicColumns  : " + nameColumna);
            mapValoresColumnas.put(nameColumna, valoresColumnasDeseadas[i]);
            numColumna = numColumna + 1;
        }
        int numeroColumnas = mapValoresColumnas.size();
        for (int i = 4; i < numeroColumnas; i++) {
            columns.add(i, new ColumnModel("", ""));
        }
        for (Map.Entry<String, String> entry : mapValoresColumnas.entrySet()) {
            String numero = entry.getKey().charAt(entry.getKey().length() - 1) + "";
            if (esNumero(numero) == true) {
                System.out.println("createDynamicColumns numero : " + numero);
                int numeroCol = Integer.parseInt(numero);
                if (VALID_COLUMN_KEYS.contains(entry.getKey())) {
                    columns.set(numeroCol + 4, new ColumnModel(entry.getValue().toUpperCase(), entry.getKey().toString()));
                }
            }
        }
    }

    public boolean esNumero(String valor) {
        try {
            int numeroCol = Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void cargarTablaColumnasEstaticas() {
        for (int i = 0; i < 4; i++) {
            int tamEmpleado = listaResultadoBusquedaAvanzada.size();
            System.out.println("tamEmpleado : " + tamEmpleado);
            if (columns.get(i).getProperty().equalsIgnoreCase("codigo")) {
                for (int j = 0; j < tamEmpleado; j++) {
                    System.out.println("listaResultadoBusquedaAvanzada.get(j).getCodigo() : " + listaResultadoBusquedaAvanzada.get(j).getCodigoEmpleado());
                    listaColumnasBusquedaAvanzada.get(j).setCodigo(listaResultadoBusquedaAvanzada.get(j).getCodigoEmpleado().toString().toUpperCase());
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("primeroApellido")) {
                for (int j = 0; j < tamEmpleado; j++) {
                    listaColumnasBusquedaAvanzada.get(j).setPrimeroApellido(listaResultadoBusquedaAvanzada.get(j).getPrimerApellido().toUpperCase());
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("segundoApellido")) {
                for (int j = 0; j < tamEmpleado; j++) {
                    listaColumnasBusquedaAvanzada.get(j).setSegundoApellido(listaResultadoBusquedaAvanzada.get(j).getSegundoApellido().toUpperCase());
                }
            }
            if (columns.get(i).getProperty().equalsIgnoreCase("nombre")) {
                for (int j = 0; j < tamEmpleado; j++) {
                    listaColumnasBusquedaAvanzada.get(j).setNombre(listaResultadoBusquedaAvanzada.get(j).getNombre().toUpperCase());
                }
            }
        }

    }

    public void cargarTablaColumnasDinamicas() {
        System.out.println("cargarTablaColumnasDinamicas");
        List<String> campos = new ArrayList<String>();
        for (int i = 4; i < columns.size(); i++) {
            System.out.println("columns.get(i).getHeader() : " + columns.get(i).getHeader());
            System.out.println("columns.get(i).getProperty() : " + columns.get(i).getProperty());
            campos.add(columns.get(i).getHeader());
        }
        int tam = columns.size();
        System.out.println("tam : " + tam);
        for (int i = 4; i < tam; i++) {
            for (int j = 0; j < listaColumnasBusquedaAvanzada.size(); j++) {
                if (columns.get(i).getProperty().equalsIgnoreCase("columna0")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(listaResultadoBusquedaAvanzada.get(j).getColumna0().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna1")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna1(listaResultadoBusquedaAvanzada.get(j).getColumna1().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna2")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna2(listaResultadoBusquedaAvanzada.get(j).getColumna2().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna3")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna3(listaResultadoBusquedaAvanzada.get(j).getColumna3().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna4")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna4(listaResultadoBusquedaAvanzada.get(j).getColumna4().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna5")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna5(listaResultadoBusquedaAvanzada.get(j).getColumna5().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna6")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna6(listaResultadoBusquedaAvanzada.get(j).getColumna6().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna7")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna7(listaResultadoBusquedaAvanzada.get(j).getColumna7().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna8")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna8(listaResultadoBusquedaAvanzada.get(j).getColumna8().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
                if (columns.get(i).getProperty().equalsIgnoreCase("columna9")) {
                    if (listaResultadoBusquedaAvanzada.get(j).getColumna0() != null) {
                        listaColumnasBusquedaAvanzada.get(j).setColumna9(listaResultadoBusquedaAvanzada.get(j).getColumna9().toUpperCase());
                    } else {
                        listaColumnasBusquedaAvanzada.get(j).setColumna0(" ");
                    }
                }
            }
        }
    }

    public void restaurar() {
        mapValoresColumnas.clear();
        columns.clear();
        System.out.println("columns : " + columns.size());
        System.out.println("mapValoresColumnas : " + mapValoresColumnas.size());
        columnTemplate = "";
        columnTemplate = "Codigo Primer_Apellido Segundo_Apellido Nombre ";
        listaResultadoBusquedaAvanzada = null;
        listaColumnasBusquedaAvanzada = new ArrayList<ColumnasBusquedaAvanzada>();
        createStaticColumns();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:resultadoBusquedaAvanzada");
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosBusquedaAvanzadaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ResultadosBusquedaAvanzada_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();

    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosBusquedaAvanzadaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ResultadosBusquedaAvanzada_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();

    }

    public void posicionTablaBusquedaAvanzada() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    public void cambiarIndice(int i, int c) {
        indice = i;
        cualCelda = c;
        System.out.println("Indice : " + i + " -- Celda : " + c);
    }

    ////
    public List<ResultadoBusquedaAvanzada> getListaResultadoBusquedaAvanzada() {
        return listaResultadoBusquedaAvanzada;
    }

    public void setListaResultadoBusquedaAvanzada(List<ResultadoBusquedaAvanzada> lista) {
        this.listaResultadoBusquedaAvanzada = lista;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public List<ResultadoBusquedaAvanzada> getFilteredListaResultadoBusquedaAvanzada() {
        return filteredListaResultadoBusquedaAvanzada;
    }

    public void setFilteredListaResultadoBusquedaAvanzada(List<ResultadoBusquedaAvanzada> filteredCars) {
        this.filteredListaResultadoBusquedaAvanzada = filteredCars;
    }

    public String getNuevaColumna() {
        return nuevaColumna;
    }

    public void setNuevaColumna(String nuevaColumna) {
        this.nuevaColumna = nuevaColumna;
    }

    //GET-SET
    public VigenciasLocalizaciones getVigenciaLocalizacionBA() {
        return vigenciaLocalizacionBA;
    }

    public void setVigenciaLocalizacionBA(VigenciasLocalizaciones vigenciaLocalizacionBA) {
        this.vigenciaLocalizacionBA = vigenciaLocalizacionBA;
    }

    public VigenciasCargos getVigenciaCargoBA() {
        return vigenciaCargoBA;
    }

    public void setVigenciaCargoBA(VigenciasCargos vigenciaCargoBA) {
        this.vigenciaCargoBA = vigenciaCargoBA;
    }

    public List<Estructuras> getLovEstructuras() {
        if (lovEstructuras == null) {
            lovEstructuras = administrarVigenciaCargoBusquedaAvanzada.lovEstructura();
        }
        return lovEstructuras;
    }

    public void setLovEstructuras(List<Estructuras> lovEstructurasVigenciasCargos) {
        this.lovEstructuras = lovEstructurasVigenciasCargos;
    }

    public List<Estructuras> getFiltrarLovEstructuras() {
        return filtrarLovEstructuras;
    }

    public void setFiltrarLovEstructuras(List<Estructuras> filtrarLovEstructurasVigenciasCargos) {
        this.filtrarLovEstructuras = filtrarLovEstructurasVigenciasCargos;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraVigenciaCargoSeleccionada) {
        this.estructuraSeleccionada = estructuraVigenciaCargoSeleccionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Cargos> getLovCargos() {
        if (lovCargos == null) {
            lovCargos = administrarVigenciaCargoBusquedaAvanzada.lovCargos();
        }
        return lovCargos;
    }

    public void setLovCargos(List<Cargos> lovCargosVigenciasCargos) {
        this.lovCargos = lovCargosVigenciasCargos;
    }

    public List<Cargos> getFiltrarLovCargos() {
        return filtrarLovCargos;
    }

    public void setFiltrarLovCargos(List<Cargos> filtrarLovCargosVigenciasCargos) {
        this.filtrarLovCargos = filtrarLovCargosVigenciasCargos;
    }

    public Cargos getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargos cargoVigenciaCargoSeleccionado) {
        this.cargoSeleccionado = cargoVigenciaCargoSeleccionado;
    }

    public List<Papeles> getLovPapeles() {
        if (lovPapeles == null) {
            lovPapeles = administrarVigenciaCargoBusquedaAvanzada.lovPapeles();
        }
        return lovPapeles;
    }

    public void setLovPapeles(List<Papeles> lovPapelesVigenciasCargos) {
        this.lovPapeles = lovPapelesVigenciasCargos;
    }

    public List<Papeles> getFiltrarLovPapeles() {
        return filtrarLovPapeles;
    }

    public void setFiltrarLovPapeles(List<Papeles> filtrarLovPapelesVigenciasCargos) {
        this.filtrarLovPapeles = filtrarLovPapelesVigenciasCargos;
    }

    public Papeles getPapelSeleccionado() {
        return papelSeleccionado;
    }

    public void setPapelSeleccionado(Papeles papelVigenciaCargoSeleccionado) {
        this.papelSeleccionado = papelVigenciaCargoSeleccionado;
    }

    public List<MotivosCambiosCargos> getLovMotivosCambiosCargos() {
        if (lovMotivosCambiosCargos == null) {
            lovMotivosCambiosCargos = administrarVigenciaCargoBusquedaAvanzada.lovMotivosCambiosCargos();
        }
        return lovMotivosCambiosCargos;
    }

    public void setLovMotivosCambiosCargos(List<MotivosCambiosCargos> lovMotivosCambiosCargosVigenciasCargos) {
        this.lovMotivosCambiosCargos = lovMotivosCambiosCargosVigenciasCargos;
    }

    public List<MotivosCambiosCargos> getFiltrarLovMotivosCambiosCargos() {
        return filtrarLovMotivosCambiosCargos;
    }

    public void setFiltrarLovMotivosCambiosCargos(List<MotivosCambiosCargos> filtrarLovMotivosCambiosCargosVigenciasCargos) {
        this.filtrarLovMotivosCambiosCargos = filtrarLovMotivosCambiosCargosVigenciasCargos;
    }

    public MotivosCambiosCargos getMotivoCambioSeleccionado() {
        return motivoCambioSeleccionado;
    }

    public void setMotivoCambioSeleccionado(MotivosCambiosCargos motivoCambioVigenciaCargoSeleccionado) {
        this.motivoCambioSeleccionado = motivoCambioVigenciaCargoSeleccionado;
    }

    public List<Empleados> getLovEmpleados() {
        if (lovEmpleados == null) {
            lovEmpleados = administrarVigenciaCargoBusquedaAvanzada.lovEmpleados();
        }
        return lovEmpleados;
    }

    public void setLovEmpleados(List<Empleados> lovEmpleadosVigenciasCargos) {
        this.lovEmpleados = lovEmpleadosVigenciasCargos;
    }

    public List<Empleados> getFiltrarLovEmpleados() {
        return filtrarLovEmpleados;
    }

    public void setFiltrarLovEmpleados(List<Empleados> filtrarLovEmpleadosVigenciasCargos) {
        this.filtrarLovEmpleados = filtrarLovEmpleadosVigenciasCargos;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoVigenciaCargoSeleccionado) {
        this.empleadoSeleccionado = empleadoVigenciaCargoSeleccionado;
    }

    public List<MotivosLocalizaciones> getLovMotivosLocalizaciones() {
        if (lovMotivosLocalizaciones == null) {
            lovMotivosLocalizaciones = administrarVigenciaLocalizacion.motivosLocalizaciones();
        }
        return lovMotivosLocalizaciones;
    }

    public void setLovMotivosLocalizaciones(List<MotivosLocalizaciones> lovMotivosLocalizaciones) {
        this.lovMotivosLocalizaciones = lovMotivosLocalizaciones;
    }

    public List<MotivosLocalizaciones> getFiltrarLovMotivosLocalizaciones() {
        return filtrarLovMotivosLocalizaciones;
    }

    public void setFiltrarLovMotivosLocalizaciones(List<MotivosLocalizaciones> filtarLovMotivosLocalizaciones) {
        this.filtrarLovMotivosLocalizaciones = filtarLovMotivosLocalizaciones;
    }

    public MotivosLocalizaciones getMotivoLocalizacionSeleccionado() {
        return motivoLocalizacionSeleccionado;
    }

    public void setMotivoLocalizacionSeleccionado(MotivosLocalizaciones motivoLocalizacionSeleccionado) {
        this.motivoLocalizacionSeleccionado = motivoLocalizacionSeleccionado;
    }

    public Date getFechaInicialCargo() {
        return fechaInicialCargo;
    }

    public void setFechaInicialCargo(Date fechaInicialCargo) {
        this.fechaInicialCargo = fechaInicialCargo;
    }

    public Date getFechaFinalCargo() {
        return fechaFinalCargo;
    }

    public void setFechaFinalCargo(Date fechaFinalCargo) {
        this.fechaFinalCargo = fechaFinalCargo;
    }

    public boolean isActivoFechasCentroCosto() {
        return activoFechasCentroCosto;
    }

    public void setActivoFechasCentroCosto(boolean activoFechasCentroCosto) {
        this.activoFechasCentroCosto = activoFechasCentroCosto;
    }

    public boolean isActivoFechasCargos() {
        return activoFechasCargos;
    }

    public void setActivoFechasCargos(boolean activoFechasCargos) {
        this.activoFechasCargos = activoFechasCargos;
    }

    public int getTabActivaCentroCosto() {
        return tabActivaCentroCosto;
    }

    public void setTabActivaCentroCosto(int tabActivaCentroCosto) {

        this.tabActivaCentroCosto = tabActivaCentroCosto;
    }

    public int getTipoFechaCargo() {
        return tipoFechaCargo;
    }

    public void setTipoFechaCargo(int tipoFechaCargo) {
        this.tipoFechaCargo = tipoFechaCargo;
    }

    public int getTabActivaCargos() {
        return tabActivaCargos;
    }

    public void setTabActivaCargos(int tabActivaCargos) {
        this.tabActivaCargos = tabActivaCargos;
    }

    public int getTipoFechaCentroCosto() {
        return tipoFechaCentroCosto;
    }

    public void setTipoFechaCentroCosto(int tipoFechaCentroCosto) {
        this.tipoFechaCentroCosto = tipoFechaCentroCosto;
    }

    public Date getFechaInicialCentroCosto() {
        return fechaInicialCentroCosto;
    }

    public void setFechaInicialCentroCosto(Date fechaInicialCentroCosto) {
        this.fechaInicialCentroCosto = fechaInicialCentroCosto;
    }

    public Date getFechaFinalCentroCosto() {
        return fechaFinalCentroCosto;
    }

    public void setFechaFinalCentroCosto(Date fechaFinalCentroCosto) {
        this.fechaFinalCentroCosto = fechaFinalCentroCosto;
    }

    public VigenciasSueldos getVigenciaSueldoBA() {
        return vigenciaSueldoBA;
    }

    public void setVigenciaSueldoBA(VigenciasSueldos vigenciaSueldoBA) {
        this.vigenciaSueldoBA = vigenciaSueldoBA;
    }

    public int getTabActivaSueldo() {
        return tabActivaSueldo;
    }

    public void setTabActivaSueldo(int tabActivaSueldo) {
        this.tabActivaSueldo = tabActivaSueldo;
    }

    public Date getFechaInicialSueldo() {
        return fechaInicialSueldo;
    }

    public void setFechaInicialSueldo(Date fechaInicialSueldo) {
        this.fechaInicialSueldo = fechaInicialSueldo;
    }

    public Date getFechaFinalSueldo() {
        return fechaFinalSueldo;
    }

    public void setFechaFinalSueldo(Date fechaFinalSueldo) {
        this.fechaFinalSueldo = fechaFinalSueldo;
    }

    public List<TiposSueldos> getLovTiposSueldos() {
        if (lovTiposSueldos == null) {
            lovTiposSueldos = administrarVigenciaSueldo.tiposSueldos();
        }
        return lovTiposSueldos;
    }

    public void setLovTiposSueldos(List<TiposSueldos> lovTiposSueldos) {
        this.lovTiposSueldos = lovTiposSueldos;
    }

    public List<TiposSueldos> getFiltrarLovTiposSueldos() {
        return filtrarLovTiposSueldos;
    }

    public void setFiltrarLovTiposSueldos(List<TiposSueldos> filtrarLovTiposSueldos) {
        this.filtrarLovTiposSueldos = filtrarLovTiposSueldos;
    }

    public TiposSueldos getTipoSueldoSeleccionado() {
        return tipoSueldoSeleccionado;
    }

    public void setTipoSueldoSeleccionado(TiposSueldos tipoSueldoSeleccionado) {
        this.tipoSueldoSeleccionado = tipoSueldoSeleccionado;
    }

    public List<MotivosCambiosSueldos> getLovMotivosCambiosSueldos() {
        if (lovMotivosCambiosSueldos == null) {
            lovMotivosCambiosSueldos = administrarVigenciaSueldo.motivosCambiosSueldos();
        }
        return lovMotivosCambiosSueldos;
    }

    public void setLovMotivosCambiosSueldos(List<MotivosCambiosSueldos> lovMotivosCambiosSueldos) {
        this.lovMotivosCambiosSueldos = lovMotivosCambiosSueldos;
    }

    public List<MotivosCambiosSueldos> getFiltrarLovMotivosCambiosSueldos() {
        return filtrarLovMotivosCambiosSueldos;
    }

    public void setFiltrarLovMotivosCambiosSueldos(List<MotivosCambiosSueldos> filtrarLovMotivosCambiosSueldos) {
        this.filtrarLovMotivosCambiosSueldos = filtrarLovMotivosCambiosSueldos;
    }

    public MotivosCambiosSueldos getMotivoSueldoSeleccionado() {
        return motivoSueldoSeleccionado;
    }

    public void setMotivoSueldoSeleccionado(MotivosCambiosSueldos motivoSueldoSeleccionado) {
        this.motivoSueldoSeleccionado = motivoSueldoSeleccionado;
    }

    public boolean isActivoFechasSueldo() {
        return activoFechasSueldo;
    }

    public void setActivoFechasSueldo(boolean activoFechasSueldo) {
        this.activoFechasSueldo = activoFechasSueldo;
    }

    public int getTipoFechaSueldo() {
        return tipoFechaSueldo;
    }

    public void setTipoFechaSueldo(int tipoFechaSueldo) {
        this.tipoFechaSueldo = tipoFechaSueldo;
    }

    public BigDecimal getSueldoMaxSueldo() {
        return sueldoMaxSueldo;
    }

    public void setSueldoMaxSueldo(BigDecimal sueldoMaxSueldo) {
        this.sueldoMaxSueldo = sueldoMaxSueldo;
    }

    public BigDecimal getSueldoMinSueldo() {
        return sueldoMinSueldo;
    }

    public void setSueldoMinSueldo(BigDecimal sueldoMinSueldoM) {
        this.sueldoMinSueldo = sueldoMinSueldoM;
    }

    public VigenciasTiposContratos getVigenciaTipoContratoBA() {
        return vigenciaTipoContratoBA;
    }

    public void setVigenciaTipoContratoBA(VigenciasTiposContratos vigenciaTipoContratoBA) {
        this.vigenciaTipoContratoBA = vigenciaTipoContratoBA;
    }

    public int getTabActivaFechaContrato() {
        return tabActivaFechaContrato;
    }

    public void setTabActivaFechaContrato(int tabActivaFechaContrato) {
        this.tabActivaFechaContrato = tabActivaFechaContrato;
    }

    public Date getFechaInicialFechaContrato() {
        return fechaInicialFechaContrato;
    }

    public void setFechaInicialFechaContrato(Date fechaInicialFechaContrato) {
        this.fechaInicialFechaContrato = fechaInicialFechaContrato;
    }

    public Date getFechaFinalFechaContrato() {
        return fechaFinalFechaContrato;
    }

    public void setFechaFinalFechaContrato(Date fechaFinalFechaContrato) {
        this.fechaFinalFechaContrato = fechaFinalFechaContrato;
    }

    public int getTipoFechaFechaContrato() {
        return tipoFechaFechaContrato;
    }

    public void setTipoFechaFechaContrato(int tipoFechaFechaContrato) {
        this.tipoFechaFechaContrato = tipoFechaFechaContrato;
    }

    public List<TiposContratos> getLovTiposContratos() {
        if (lovTiposContratos == null) {
            lovTiposContratos = administrarVigenciaTipoContrato.tiposContratos();
        }
        return lovTiposContratos;
    }

    public void setLovTiposContratos(List<TiposContratos> lovTiposContratos) {
        this.lovTiposContratos = lovTiposContratos;
    }

    public List<TiposContratos> getFiltrarLovTiposContratos() {
        return filtrarLovTiposContratos;
    }

    public void setFiltrarLovTiposContratos(List<TiposContratos> filtrarLovTiposContratos) {
        this.filtrarLovTiposContratos = filtrarLovTiposContratos;
    }

    public TiposContratos getTipoContratoSeleccionado() {
        return tipoContratoSeleccionado;
    }

    public void setTipoContratoSeleccionado(TiposContratos tipoContratoSeleccionado) {
        this.tipoContratoSeleccionado = tipoContratoSeleccionado;
    }

    public List<MotivosContratos> getLovMotivosContratos() {
        if (lovMotivosContratos == null) {
            lovMotivosContratos = administrarVigenciaTipoContrato.motivosContratos();
        }
        return lovMotivosContratos;
    }

    public void setLovMotivosContratos(List<MotivosContratos> lovMotivosContratos) {
        this.lovMotivosContratos = lovMotivosContratos;
    }

    public MotivosContratos getMotivoContratoSeleccionado() {
        return motivoContratoSeleccionado;
    }

    public void setMotivoContratoSeleccionado(MotivosContratos motivoContratoSeleccionado) {
        this.motivoContratoSeleccionado = motivoContratoSeleccionado;
    }

    public boolean isActivoFechasFechaContrato() {
        return activoFechasFechaContrato;
    }

    public void setActivoFechasFechaContrato(boolean activoFechasFechaContrato) {
        this.activoFechasFechaContrato = activoFechasFechaContrato;
    }

    public List<MotivosContratos> getFiltrarLovMotivosContratos() {
        return filtrarLovMotivosContratos;
    }

    public void setFiltrarLovMotivosContratos(List<MotivosContratos> filtrarLovMotivosContratos) {
        this.filtrarLovMotivosContratos = filtrarLovMotivosContratos;
    }

    public VigenciasTiposTrabajadores getVigenciaTipoTrabajadorBA() {
        return vigenciaTipoTrabajadorBA;
    }

    public void setVigenciaTipoTrabajadorBA(VigenciasTiposTrabajadores vigenciaTipoTrabajadorBA) {
        this.vigenciaTipoTrabajadorBA = vigenciaTipoTrabajadorBA;
    }

    public int getTabActivaTipoTrabajador() {
        return tabActivaTipoTrabajador;
    }

    public void setTabActivaTipoTrabajador(int tabActivaTipoTrabajador) {
        this.tabActivaTipoTrabajador = tabActivaTipoTrabajador;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
    }

    public boolean isActivoFechasTipoTrabajador() {
        return activoFechasTipoTrabajador;
    }

    public void setActivoFechasTipoTrabajador(boolean activoFechasTipoTrabajador) {
        this.activoFechasTipoTrabajador = activoFechasTipoTrabajador;
    }

    public Date getFechaInicialTipoTrabajador() {
        return fechaInicialTipoTrabajador;
    }

    public void setFechaInicialTipoTrabajador(Date fechaInicialTipoTrabajador) {
        this.fechaInicialTipoTrabajador = fechaInicialTipoTrabajador;
    }

    public Date getFechaFinalTipoTrabajador() {
        return fechaFinalTipoTrabajador;
    }

    public void setFechaFinalTipoTrabajador(Date fechaFinalTipoTrabajador) {
        this.fechaFinalTipoTrabajador = fechaFinalTipoTrabajador;
    }

    public int getTipoFechaTipoTrabajador() {
        return tipoFechaTipoTrabajador;
    }

    public void setTipoFechaTipoTrabajador(int tipoFechaTipoTrabajador) {
        this.tipoFechaTipoTrabajador = tipoFechaTipoTrabajador;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        if (lovTiposTrabajadores == null) {
            lovTiposTrabajadores = administrarVigenciasTiposTrabajadores.tiposTrabajadores();
        }
        return lovTiposTrabajadores;
    }

    public void setLovTiposTrabajadores(List<TiposTrabajadores> lovTiposTrabajadores) {
        this.lovTiposTrabajadores = lovTiposTrabajadores;
    }

    public List<TiposTrabajadores> getFiltrarLovTiposTrabajadores() {
        return filtrarLovTiposTrabajadores;
    }

    public void setFiltrarLovTiposTrabajadores(List<TiposTrabajadores> filtrarLovTiposTrabajadores) {
        this.filtrarLovTiposTrabajadores = filtrarLovTiposTrabajadores;
    }

    public VigenciasReformasLaborales getVigenciaReformaLaboralBA() {
        return vigenciaReformaLaboralBA;
    }

    public void setVigenciaReformaLaboralBA(VigenciasReformasLaborales vigenciaReformaLaboralBA) {
        this.vigenciaReformaLaboralBA = vigenciaReformaLaboralBA;
    }

    public int getTabActivaTipoSalario() {
        return tabActivaTipoSalario;
    }

    public void setTabActivaTipoSalario(int tabActivaTipoSalario) {
        this.tabActivaTipoSalario = tabActivaTipoSalario;
    }

    public Date getFechaInicialTipoSalario() {
        return fechaInicialTipoSalario;
    }

    public void setFechaInicialTipoSalario(Date fechaInicialTipoSalario) {
        this.fechaInicialTipoSalario = fechaInicialTipoSalario;
    }

    public Date getFechaFinalTipoSalario() {
        return fechaFinalTipoSalario;
    }

    public void setFechaFinalTipoSalario(Date fechaFinalTipoSalario) {
        this.fechaFinalTipoSalario = fechaFinalTipoSalario;
    }

    public int getTipoFechaTipoSalario() {
        return tipoFechaTipoSalario;
    }

    public void setTipoFechaTipoSalario(int tipoFechaTipoSalario) {
        this.tipoFechaTipoSalario = tipoFechaTipoSalario;
    }

    public List<ReformasLaborales> getLovReformasLaborales() {
        if (lovReformasLaborales == null) {
            lovReformasLaborales = administrarVigenciaReformaLaboral.reformasLaborales();
        }
        return lovReformasLaborales;
    }

    public void setLovReformasLaborales(List<ReformasLaborales> lovReformasLaborales) {
        this.lovReformasLaborales = lovReformasLaborales;
    }

    public List<ReformasLaborales> getFiltrarLovReformasLaborales() {
        return filtrarLovReformasLaborales;
    }

    public void setFiltrarLovReformasLaborales(List<ReformasLaborales> filtrarLovReformasLaborales) {
        this.filtrarLovReformasLaborales = filtrarLovReformasLaborales;
    }

    public ReformasLaborales getReformaLaboralSeleccionada() {
        return reformaLaboralSeleccionada;
    }

    public void setReformaLaboralSeleccionada(ReformasLaborales reformaLaboralSeleccionada) {
        this.reformaLaboralSeleccionada = reformaLaboralSeleccionada;
    }

    public boolean isActivoFechasTipoSalario() {
        return activoFechasTipoSalario;
    }

    public void setActivoFechasTipoSalario(boolean activoFechasTipoSalario) {
        this.activoFechasTipoSalario = activoFechasTipoSalario;
    }

    public VigenciasNormasEmpleados getVigenciaNormaEmpleadoBA() {
        return vigenciaNormaEmpleadoBA;
    }

    public void setVigenciaNormaEmpleadoBA(VigenciasNormasEmpleados vigenciaNormaEmpleadoBA) {
        this.vigenciaNormaEmpleadoBA = vigenciaNormaEmpleadoBA;
    }

    public int getTabActivaNormaLaboral() {
        return tabActivaNormaLaboral;
    }

    public void setTabActivaNormaLaboral(int tabActivaNormaLaboral) {
        this.tabActivaNormaLaboral = tabActivaNormaLaboral;
    }

    public Date getFechaInicialNormaLaboral() {
        return fechaInicialNormaLaboral;
    }

    public void setFechaInicialNormaLaboral(Date fechaInicialNormaLaboral) {
        this.fechaInicialNormaLaboral = fechaInicialNormaLaboral;
    }

    public Date getFechaFinalNormaLaboral() {
        return fechaFinalNormaLaboral;
    }

    public void setFechaFinalNormaLaboral(Date fechaFinalNormaLaboral) {
        this.fechaFinalNormaLaboral = fechaFinalNormaLaboral;
    }

    public int getTipoFechaNormaLaboral() {
        return tipoFechaNormaLaboral;
    }

    public void setTipoFechaNormaLaboral(int tipoFechaNormaLaboral) {
        this.tipoFechaNormaLaboral = tipoFechaNormaLaboral;
    }

    public List<NormasLaborales> getLovNormasLaborales() {
        if (lovNormasLaborales == null) {
            lovNormasLaborales = administrarVigenciaNormaEmpleado.lovNormasLaborales();
        }
        return lovNormasLaborales;
    }

    public void setLovNormasLaborales(List<NormasLaborales> lovNormasLaborales) {
        this.lovNormasLaborales = lovNormasLaborales;
    }

    public List<NormasLaborales> getFiltrarLovNormasLaborales() {
        return filtrarLovNormasLaborales;
    }

    public void setFiltrarLovNormasLaborales(List<NormasLaborales> filtrarLovNormasLaborales) {
        this.filtrarLovNormasLaborales = filtrarLovNormasLaborales;
    }

    public NormasLaborales getNormaLaboralSeleccionada() {
        return normaLaboralSeleccionada;
    }

    public void setNormaLaboralSeleccionada(NormasLaborales normaLaboralSeleccionada) {
        this.normaLaboralSeleccionada = normaLaboralSeleccionada;
    }

    public boolean isActivoFechasNormaLaboral() {
        return activoFechasNormaLaboral;
    }

    public void setActivoFechasNormaLaboral(boolean activoFechasNormaLaboral) {
        this.activoFechasNormaLaboral = activoFechasNormaLaboral;
    }

    public VigenciasContratos getVigenciaContratoBA() {
        return vigenciaContratoBA;
    }

    public void setVigenciaContratoBA(VigenciasContratos vigenciaContratoBA) {
        this.vigenciaContratoBA = vigenciaContratoBA;
    }

    public int getTabActivaLegislacionLaboral() {
        return tabActivaLegislacionLaboral;
    }

    public void setTabActivaLegislacionLaboral(int tabActivaLegislacionLaboral) {
        this.tabActivaLegislacionLaboral = tabActivaLegislacionLaboral;
    }

    public Date getFechaMIInicialLegislacionLaboral() {
        return fechaMIInicialLegislacionLaboral;
    }

    public void setFechaMIInicialLegislacionLaboral(Date fechaMIInicialLegislacionLaboral) {
        this.fechaMIInicialLegislacionLaboral = fechaMIInicialLegislacionLaboral;
    }

    public Date getFechaMIFinalLegislacionLaboral() {
        return fechaMIFinalLegislacionLaboral;
    }

    public void setFechaMIFinalLegislacionLaboral(Date fechaMIFinalLegislacionLaboral) {
        this.fechaMIFinalLegislacionLaboral = fechaMIFinalLegislacionLaboral;
    }

    public Date getFechaMFInicialLegislacionLaboral() {
        return fechaMFInicialLegislacionLaboral;
    }

    public void setFechaMFInicialLegislacionLaboral(Date fechaMFInicialLegislacionLaboral) {
        this.fechaMFInicialLegislacionLaboral = fechaMFInicialLegislacionLaboral;
    }

    public Date getFechaMFFinalLegislacionLaboral() {
        return fechaMFFinalLegislacionLaboral;
    }

    public void setFechaMFFinalLegislacionLaboral(Date fechaMFFinalLegislacionLaboral) {
        this.fechaMFFinalLegislacionLaboral = fechaMFFinalLegislacionLaboral;
    }

    public int getTipoFechaLegislacionLaboral() {
        return tipoFechaLegislacionLaboral;
    }

    public void setTipoFechaLegislacionLaboral(int tipoFechaLegislacionLaboral) {
        this.tipoFechaLegislacionLaboral = tipoFechaLegislacionLaboral;
    }

    public List<Contratos> getLovContratos() {
        if (lovContratos == null) {
            lovContratos = administrarVigenciaContrato.contratos();
        }
        return lovContratos;
    }

    public void setLovContratos(List<Contratos> lovContratos) {
        this.lovContratos = lovContratos;
    }

    public List<Contratos> getFiltrarLovContratos() {
        return filtrarLovContratos;
    }

    public void setFiltrarLovContratos(List<Contratos> filtrarLovContratos) {
        this.filtrarLovContratos = filtrarLovContratos;
    }

    public Contratos getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contratos contratoSeleccionado) {
        this.contratoSeleccionado = contratoSeleccionado;
    }

    public boolean isActivoFechasLegislacionLaboral() {
        return activoFechasLegislacionLaboral;
    }

    public void setActivoFechasLegislacionLaboral(boolean activoFechasLegislacionLaboral) {
        this.activoFechasLegislacionLaboral = activoFechasLegislacionLaboral;
    }

    public VigenciasUbicaciones getVigenciaUbicacionBA() {
        return vigenciaUbicacionBA;
    }

    public void setVigenciaUbicacionBA(VigenciasUbicaciones vigenciaUbicacionBA) {
        this.vigenciaUbicacionBA = vigenciaUbicacionBA;
    }

    public int getTabActivaUbicacion() {
        return tabActivaUbicacion;
    }

    public void setTabActivaUbicacion(int tabActivaUbicacion) {
        this.tabActivaUbicacion = tabActivaUbicacion;
    }

    public Date getFechaInicialUbicacion() {
        return fechaInicialUbicacion;
    }

    public void setFechaInicialUbicacion(Date fechaInicialUbicacion) {
        this.fechaInicialUbicacion = fechaInicialUbicacion;
    }

    public Date getFechaFinalUbicacion() {
        return fechaFinalUbicacion;
    }

    public void setFechaFinalUbicacion(Date fechaFinalUbicacion) {
        this.fechaFinalUbicacion = fechaFinalUbicacion;
    }

    public int getTipoFechaUbicacion() {
        return tipoFechaUbicacion;
    }

    public void setTipoFechaUbicacion(int tipoFechaUbicacion) {
        this.tipoFechaUbicacion = tipoFechaUbicacion;
    }

    public List<UbicacionesGeograficas> getLovUbicacionesGeograficas() {
        if (lovUbicacionesGeograficas == null) {
            lovUbicacionesGeograficas = administrarVigenciasUbicaciones.ubicacionesGeograficas();
        }
        return lovUbicacionesGeograficas;
    }

    public void setLovUbicacionesGeograficas(List<UbicacionesGeograficas> lovUbicacionesGeograficas) {
        this.lovUbicacionesGeograficas = lovUbicacionesGeograficas;
    }

    public List<UbicacionesGeograficas> getFiltrarLovUbicacionesGeograficas() {
        return filtrarLovUbicacionesGeograficas;
    }

    public void setFiltrarLovUbicacionesGeograficas(List<UbicacionesGeograficas> filtrarLovUbicacionesGeograficas) {
        this.filtrarLovUbicacionesGeograficas = filtrarLovUbicacionesGeograficas;
    }

    public UbicacionesGeograficas getUbicacionGeograficaSeleccionada() {
        return ubicacionGeograficaSeleccionada;
    }

    public void setUbicacionGeograficaSeleccionada(UbicacionesGeograficas ubicacionGeograficaSeleccionada) {
        this.ubicacionGeograficaSeleccionada = ubicacionGeograficaSeleccionada;
    }

    public boolean isActivoFechasUbicacion() {
        return activoFechasUbicacion;
    }

    public void setActivoFechasUbicacion(boolean activoFechasUbicacion) {
        this.activoFechasUbicacion = activoFechasUbicacion;
    }

    public VigenciasAfiliaciones getVigenciaAfiliacionBA() {
        return vigenciaAfiliacionBA;
    }

    public void setVigenciaAfiliacionBA(VigenciasAfiliaciones vigenciaAfiliacionBA) {
        this.vigenciaAfiliacionBA = vigenciaAfiliacionBA;
    }

    public int getTabActivaAfiliacion() {
        return tabActivaAfiliacion;
    }

    public void setTabActivaAfiliacion(int tabActivaAfiliacion) {
        this.tabActivaAfiliacion = tabActivaAfiliacion;
    }

    public Date getFechaInicialAfiliacion() {
        return fechaInicialAfiliacion;
    }

    public void setFechaInicialAfiliacion(Date fechaInicialAfiliacion) {
        this.fechaInicialAfiliacion = fechaInicialAfiliacion;
    }

    public Date getFechaFinalAfiliacion() {
        return fechaFinalAfiliacion;
    }

    public void setFechaFinalAfiliacion(Date fechaFinalAfiliacion) {
        this.fechaFinalAfiliacion = fechaFinalAfiliacion;
    }

    public int getTipoFechaAfiliacion() {
        return tipoFechaAfiliacion;
    }

    public void setTipoFechaAfiliacion(int tipoFechaAfiliacion) {
        this.tipoFechaAfiliacion = tipoFechaAfiliacion;
    }

    public List<TercerosSucursales> getLovTercerosSucursales() {
        if (lovTercerosSucursales == null) {
            lovTercerosSucursales = administrarVigenciaAfiliacion3.listTercerosSucursales();
        }
        return lovTercerosSucursales;
    }

    public void setLovTercerosSucursales(List<TercerosSucursales> lovTercerosSucursales) {
        this.lovTercerosSucursales = lovTercerosSucursales;
    }

    public List<TercerosSucursales> getFiltrarLovTercerosSucursales() {
        return filtrarLovTercerosSucursales;
    }

    public void setFiltrarLovTercerosSucursales(List<TercerosSucursales> filtrarLovTercerosSucursales) {
        this.filtrarLovTercerosSucursales = filtrarLovTercerosSucursales;
    }

    public TercerosSucursales getTerceroSucursalSeleccionado() {
        return terceroSucursalSeleccionado;
    }

    public void setTerceroSucursalSeleccionado(TercerosSucursales terceroSucursalSeleccionado) {
        this.terceroSucursalSeleccionado = terceroSucursalSeleccionado;
    }

    public List<TiposEntidades> getLovTiposEntidades() {
        if (lovTiposEntidades == null) {
            lovTiposEntidades = administrarVigenciaAfiliacion3.listTiposEntidades();
        }
        return lovTiposEntidades;
    }

    public void setLovTiposEntidades(List<TiposEntidades> lovTiposEntidades) {
        this.lovTiposEntidades = lovTiposEntidades;
    }

    public List<TiposEntidades> getFiltrarLovTiposEntidades() {
        return filtrarLovTiposEntidades;
    }

    public void setFiltrarLovTiposEntidades(List<TiposEntidades> filtrarLovTiposEntidades) {
        this.filtrarLovTiposEntidades = filtrarLovTiposEntidades;
    }

    public TiposEntidades getTipoEntidadSeleccionado() {
        return tipoEntidadSeleccionado;
    }

    public void setTipoEntidadSeleccionado(TiposEntidades tipoEntidadSeleccionado) {
        this.tipoEntidadSeleccionado = tipoEntidadSeleccionado;
    }

    public boolean isActivoFechasAfiliacion() {
        return activoFechasAfiliacion;
    }

    public void setActivoFechasAfiliacion(boolean activoFechasAfiliacion) {
        this.activoFechasAfiliacion = activoFechasAfiliacion;
    }

    public List<EstadosAfiliaciones> getLovEstadosAfiliaciones() {
        if (lovEstadosAfiliaciones == null) {
            lovEstadosAfiliaciones = administrarVigenciaAfiliacion3.listEstadosAfiliaciones();
        }
        return lovEstadosAfiliaciones;
    }

    public void setLovEstadosAfiliaciones(List<EstadosAfiliaciones> lovEstadosAfiliaciones) {
        this.lovEstadosAfiliaciones = lovEstadosAfiliaciones;
    }

    public List<EstadosAfiliaciones> getFiltrarLovEstadosAfiliaciones() {
        return filtrarLovEstadosAfiliaciones;
    }

    public void setFiltrarLovEstadosAfiliaciones(List<EstadosAfiliaciones> filtrarLovEstadosAfiliaciones) {
        this.filtrarLovEstadosAfiliaciones = filtrarLovEstadosAfiliaciones;
    }

    public EstadosAfiliaciones getEstadoAfiliacionSeleccionado() {
        return estadoAfiliacionSeleccionado;
    }

    public void setEstadoAfiliacionSeleccionado(EstadosAfiliaciones estadoAfiliacionSeleccionado) {
        this.estadoAfiliacionSeleccionado = estadoAfiliacionSeleccionado;
    }

    public VigenciasFormasPagos getVigenciaFormaPagoBA() {
        return vigenciaFormaPagoBA;
    }

    public void setVigenciaFormaPagoBA(VigenciasFormasPagos vigenciaFormaPagoBA) {
        this.vigenciaFormaPagoBA = vigenciaFormaPagoBA;
    }

    public int getTabActivaFormaPago() {
        return tabActivaFormaPago;
    }

    public void setTabActivaFormaPago(int tabActivaFormaPago) {
        this.tabActivaFormaPago = tabActivaFormaPago;
    }

    public Date getFechaInicialFormaPago() {
        return fechaInicialFormaPago;
    }

    public void setFechaInicialFormaPago(Date fechaInicialFormaPago) {
        this.fechaInicialFormaPago = fechaInicialFormaPago;
    }

    public Date getFechaFinalFormaPago() {
        return fechaFinalFormaPago;
    }

    public void setFechaFinalFormaPago(Date fechaFinalFormaPago) {
        this.fechaFinalFormaPago = fechaFinalFormaPago;
    }

    public int getTipoFechaFormaPago() {
        return tipoFechaFormaPago;
    }

    public void setTipoFechaFormaPago(int tipoFechaFormaPago) {
        this.tipoFechaFormaPago = tipoFechaFormaPago;
    }

    public List<Periodicidades> getLovPeriodicidades() {
        if (lovPeriodicidades == null) {
            lovPeriodicidades = administrarEmplVigenciaFormaPago.consultarLOVPerdiocidades();
        }
        return lovPeriodicidades;
    }

    public void setLovPeriodicidades(List<Periodicidades> lovPeriodicidades) {
        this.lovPeriodicidades = lovPeriodicidades;
    }

    public List<Periodicidades> getFiltrarLovPeriodicidades() {
        return filtrarLovPeriodicidades;
    }

    public void setFiltrarLovPeriodicidades(List<Periodicidades> filtrarLovPeriodicidades) {
        this.filtrarLovPeriodicidades = filtrarLovPeriodicidades;
    }

    public Periodicidades getPeriodicidadSeleccionada() {
        return periodicidadSeleccionada;
    }

    public void setPeriodicidadSeleccionada(Periodicidades periodicidadSeleccionada) {
        this.periodicidadSeleccionada = periodicidadSeleccionada;
    }

    public List<Sucursales> getLovSucursales() {
        if (lovSucursales == null) {
            lovSucursales = administrarEmplVigenciaFormaPago.consultarLOVSucursales();
        }
        return lovSucursales;
    }

    public void setLovSucursales(List<Sucursales> lovSucursales) {
        this.lovSucursales = lovSucursales;
    }

    public List<Sucursales> getFiltrarLovSucursales() {
        return filtrarLovSucursales;
    }

    public void setFiltrarLovSucursales(List<Sucursales> filtrarLovSucursales) {
        this.filtrarLovSucursales = filtrarLovSucursales;
    }

    public Sucursales getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(Sucursales sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public boolean isActivoFechasFormaPago() {
        return activoFechasFormaPago;
    }

    public void setActivoFechasFormaPago(boolean activoFechasFormaPago) {
        this.activoFechasFormaPago = activoFechasFormaPago;
    }

    public Mvrs getMvrsBA() {
        return mvrsBA;
    }

    public void setMvrsBA(Mvrs mvrsBA) {
        this.mvrsBA = mvrsBA;
    }

    public int getTabActivaMvrs() {
        return tabActivaMvrs;
    }

    public void setTabActivaMvrs(int tabActivaMvrs) {
        this.tabActivaMvrs = tabActivaMvrs;
    }

    public Date getFechaInicialMvrs() {
        return fechaInicialMvrs;
    }

    public void setFechaInicialMvrs(Date fechaInicialMvrs) {
        this.fechaInicialMvrs = fechaInicialMvrs;
    }

    public Date getFechaFinalMvrs() {
        return fechaFinalMvrs;
    }

    public void setFechaFinalMvrs(Date fechaFinalMvrs) {
        this.fechaFinalMvrs = fechaFinalMvrs;
    }

    public int getTipoFechaMvrs() {
        return tipoFechaMvrs;
    }

    public void setTipoFechaMvrs(int tipoFechaMvrs) {
        this.tipoFechaMvrs = tipoFechaMvrs;
    }

    public List<Motivosmvrs> getLovMotivosMvrs() {
        if (lovMotivosMvrs == null) {
            lovMotivosMvrs = administrarEmplMvrs.listMotivos();
        }
        return lovMotivosMvrs;
    }

    public void setLovMotivosMvrs(List<Motivosmvrs> lovMotivosMvrs) {
        this.lovMotivosMvrs = lovMotivosMvrs;
    }

    public List<Motivosmvrs> getFiltrarLovMotivosMvrs() {
        return filtrarLovMotivosMvrs;
    }

    public void setFiltrarLovMotivosMvrs(List<Motivosmvrs> filtrarLovMotivosMvrs) {
        this.filtrarLovMotivosMvrs = filtrarLovMotivosMvrs;
    }

    public Motivosmvrs getMotivoMvrsSeleccionado() {
        return motivoMvrsSeleccionado;
    }

    public void setMotivoMvrsSeleccionado(Motivosmvrs motivoMvrsSeleccionado) {
        this.motivoMvrsSeleccionado = motivoMvrsSeleccionado;
    }

    public boolean isActivoFechasMvrs() {
        return activoFechasMvrs;
    }

    public void setActivoFechasMvrs(boolean activoFechasMvrs) {
        this.activoFechasMvrs = activoFechasMvrs;
    }

    public BigDecimal getSueldoMaxMvrs() {
        return sueldoMaxMvrs;
    }

    public void setSueldoMaxMvrs(BigDecimal sueldoMaxMvrs) {
        this.sueldoMaxMvrs = sueldoMaxMvrs;
    }

    public BigDecimal getSueldoMinMvrs() {
        return sueldoMinMvrs;
    }

    public void setSueldoMinMvrs(BigDecimal sueldoMinMvrs) {
        this.sueldoMinMvrs = sueldoMinMvrs;
    }

    public int getTabActivaSets() {
        return tabActivaSets;
    }

    public void setTabActivaSets(int tabActivaSets) {
        this.tabActivaSets = tabActivaSets;
    }

    public Date getFechaMIInicialSets() {
        return fechaMIInicialSets;
    }

    public void setFechaMIInicialSets(Date fechaMIInicialSets) {
        this.fechaMIInicialSets = fechaMIInicialSets;
    }

    public Date getFechaMIFinalSets() {
        return fechaMIFinalSets;
    }

    public void setFechaMIFinalSets(Date fechaMIFinalSets) {
        this.fechaMIFinalSets = fechaMIFinalSets;
    }

    public Date getFechaMFInicialSets() {
        return fechaMFInicialSets;
    }

    public void setFechaMFInicialSets(Date fechaMFInicialSets) {
        this.fechaMFInicialSets = fechaMFInicialSets;
    }

    public Date getFechaMFFinalSets() {
        return fechaMFFinalSets;
    }

    public void setFechaMFFinalSets(Date fechaMFFinalSets) {
        this.fechaMFFinalSets = fechaMFFinalSets;
    }

    public int getTipoFechaSets() {
        return tipoFechaSets;
    }

    public void setTipoFechaSets(int tipoFechaSets) {
        this.tipoFechaSets = tipoFechaSets;
    }

    public boolean isActivoFechasSets() {
        return activoFechasSets;
    }

    public void setActivoFechasSets(boolean activoFechasSets) {
        this.activoFechasSets = activoFechasSets;
    }

    public BigDecimal getPromedioMinimoSets() {
        return promedioMinimoSets;
    }

    public void setPromedioMinimoSets(BigDecimal promedioMinimoSets) {
        this.promedioMinimoSets = promedioMinimoSets;
    }

    public BigDecimal getPromedioMaximoSets() {
        return promedioMaximoSets;
    }

    public void setPromedioMaximoSets(BigDecimal promedioMaximoSets) {
        this.promedioMaximoSets = promedioMaximoSets;
    }

    public String getTipoMetodoSets() {
        return tipoMetodoSets;
    }

    public void setTipoMetodoSets(String tipoMetodoSets) {
        this.tipoMetodoSets = tipoMetodoSets;
    }

    public int getTabActivaVacacion() {
        return tabActivaVacacion;
    }

    public void setTabActivaVacacion(int tabActivaVacacion) {
        this.tabActivaVacacion = tabActivaVacacion;
    }

    public Date getFechaMIInicialVacacion() {
        return fechaMIInicialVacacion;
    }

    public void setFechaMIInicialVacacion(Date fechaMIInicialVacacion) {
        this.fechaMIInicialVacacion = fechaMIInicialVacacion;
    }

    public Date getFechaMIFinalVacacion() {
        return fechaMIFinalVacacion;
    }

    public void setFechaMIFinalVacacion(Date fechaMIFinalVacacion) {
        this.fechaMIFinalVacacion = fechaMIFinalVacacion;
    }

    public Date getFechaMFInicialVacacion() {
        return fechaMFInicialVacacion;
    }

    public void setFechaMFInicialVacacion(Date fechaMFInicialVacacion) {
        this.fechaMFInicialVacacion = fechaMFInicialVacacion;
    }

    public Date getFechaMFFinalVacacion() {
        return fechaMFFinalVacacion;
    }

    public void setFechaMFFinalVacacion(Date fechaMFFinalVacacion) {
        this.fechaMFFinalVacacion = fechaMFFinalVacacion;
    }

    public VigenciasJornadas getVigenciaJornadaBA() {
        return vigenciaJornadaBA;
    }

    public void setVigenciaJornadaBA(VigenciasJornadas vigenciaJornadaBA) {
        this.vigenciaJornadaBA = vigenciaJornadaBA;
    }

    public int getTabActivaJornadaLaboral() {
        return tabActivaJornadaLaboral;
    }

    public void setTabActivaJornadaLaboral(int tabActivaJornadaLaboral) {
        this.tabActivaJornadaLaboral = tabActivaJornadaLaboral;
    }

    public Date getFechaInicialJornadaLaboral() {
        return fechaInicialJornadaLaboral;
    }

    public void setFechaInicialJornadaLaboral(Date fechaInicialJornadaLaboral) {
        this.fechaInicialJornadaLaboral = fechaInicialJornadaLaboral;
    }

    public Date getFechaFinalJornadaLaboral() {
        return fechaFinalJornadaLaboral;
    }

    public void setFechaFinalJornadaLaboral(Date fechaFinalJornadaLaboral) {
        this.fechaFinalJornadaLaboral = fechaFinalJornadaLaboral;
    }

    public List<JornadasLaborales> getLovJornadasLaborales() {
        if (lovJornadasLaborales == null) {
            lovJornadasLaborales = administrarVigenciaJornada.jornadasLaborales();
        }
        return lovJornadasLaborales;
    }

    public void setLovJornadasLaborales(List<JornadasLaborales> lovJornadasLaborales) {
        this.lovJornadasLaborales = lovJornadasLaborales;
    }

    public List<JornadasLaborales> getFiltrarLovJornadasLaborales() {
        return filtrarLovJornadasLaborales;
    }

    public void setFiltrarLovJornadasLaborales(List<JornadasLaborales> filtrarLovJornadasLaborales) {
        this.filtrarLovJornadasLaborales = filtrarLovJornadasLaborales;
    }

    public JornadasLaborales getJornadaLaboralSeleccionada() {
        return jornadaLaboralSeleccionada;
    }

    public void setJornadaLaboralSeleccionada(JornadasLaborales jornadaLaboralSeleccionada) {
        this.jornadaLaboralSeleccionada = jornadaLaboralSeleccionada;
    }

    public MotivosRetiros getMotivoRetiroBA() {
        return motivoRetiroBA;
    }

    public void setMotivoRetiroBA(MotivosRetiros motivoRetiroBA) {
        this.motivoRetiroBA = motivoRetiroBA;
    }

    public int getTabActivaFechaRetiro() {
        return tabActivaFechaRetiro;
    }

    public void setTabActivaFechaRetiro(int tabActivaFechaRetiro) {
        this.tabActivaFechaRetiro = tabActivaFechaRetiro;
    }

    public Date getFechaInicialFechaRetiro() {
        return fechaInicialFechaRetiro;
    }

    public void setFechaInicialFechaRetiro(Date fechaInicialFechaRetiro) {
        this.fechaInicialFechaRetiro = fechaInicialFechaRetiro;
    }

    public Date getFechaFinalFechaRetiro() {
        return fechaFinalFechaRetiro;
    }

    public void setFechaFinalFechaRetiro(Date fechaFinalFechaRetiro) {
        this.fechaFinalFechaRetiro = fechaFinalFechaRetiro;
    }

    public List<MotivosRetiros> getLovMotivosRetiros() {
        if (lovMotivosRetiros == null) {
            lovMotivosRetiros = administrarVigenciasTiposTrabajadores.motivosRetiros();
        }
        return lovMotivosRetiros;
    }

    public void setLovMotivosRetiros(List<MotivosRetiros> lovMotivosRetiros) {
        this.lovMotivosRetiros = lovMotivosRetiros;
    }

    public List<MotivosRetiros> getFiltrarLovMotivosRetiros() {
        return filtrarLovMotivosRetiros;
    }

    public void setFiltrarLovMotivosRetiros(List<MotivosRetiros> filtrarLovMotivosRetiros) {
        this.filtrarLovMotivosRetiros = filtrarLovMotivosRetiros;
    }

    public MotivosRetiros getMotivoRetiroSeleccionado() {
        return motivoRetiroSeleccionado;
    }

    public void setMotivoRetiroSeleccionado(MotivosRetiros motivoRetiroSeleccionado) {
        this.motivoRetiroSeleccionado = motivoRetiroSeleccionado;
    }

    public List<ColumnasEscenarios> getListaColumnasEscenarios() {
        return this.listaColumnasEscenarios;
    }

    public void setListaColumnasEscenarios(List<ColumnasEscenarios> setListColumnasEscenarios) {
        this.listaColumnasEscenarios = setListColumnasEscenarios;
    }

    public List<QVWEmpleadosCorte> getRegistros() {
        return this.registros;
    }

    public void setRegistros(List<QVWEmpleadosCorte> setListColumnasEscenarios) {
        this.registros = setListColumnasEscenarios;
    }

    public List<QVWEmpleadosCorte> getRegistrosFiltrado() {
        return this.registrosFiltrado;
    }

    public void setRegistrosFiltrado(List<QVWEmpleadosCorte> setListColumnasEscenarios) {
        this.registrosFiltrado = setListColumnasEscenarios;
    }

    public List<ColumnasBusquedaAvanzada> getFiltrarListaColumnasBusquedaAvanzada() {
        return filtrarListaColumnasBusquedaAvanzada;
    }

    public void setFiltrarListaColumnasBusquedaAvanzada(List<ColumnasBusquedaAvanzada> filtrarListaColumnasBusquedaAvanzada) {
        this.filtrarListaColumnasBusquedaAvanzada = filtrarListaColumnasBusquedaAvanzada;
    }

    public List<ColumnasBusquedaAvanzada> getListaColumnasBusquedaAvanzada() {
        return listaColumnasBusquedaAvanzada;
    }

    public void setListaColumnasBusquedaAvanzada(List<ColumnasBusquedaAvanzada> listaColumnasBusquedaAvanzada) {
        this.listaColumnasBusquedaAvanzada = listaColumnasBusquedaAvanzada;
    }

    public String getCabeceraEditarCelda() {
        return cabeceraEditarCelda;
    }

    public void setCabeceraEditarCelda(String cabeceraEditarCelda) {
        this.cabeceraEditarCelda = cabeceraEditarCelda;
    }

    public String getInfoVariableEditarCelda() {
        return infoVariableEditarCelda;
    }

    public void setInfoVariableEditarCelda(String infoVariableEditarCelda) {
        this.infoVariableEditarCelda = infoVariableEditarCelda;
    }

}
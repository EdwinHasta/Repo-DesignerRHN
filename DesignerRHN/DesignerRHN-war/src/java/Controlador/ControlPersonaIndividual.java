package Controlador;

import Entidades.Cargos;
import Entidades.CentrosCostos;
import Entidades.Ciudades;
import Entidades.Comprobantes;
import Entidades.Contratos;
import Entidades.CortesProcesos;
import Entidades.Direcciones;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.EstadosCiviles;
import Entidades.Estructuras;
import Entidades.JornadasLaborales;
import Entidades.MetodosPagos;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosCambiosSueldos;
import Entidades.MotivosContratos;
import Entidades.MotivosLocalizaciones;
import Entidades.NormasLaborales;
import Entidades.Papeles;
import Entidades.ParametrosEstructuras;
import Entidades.Periodicidades;
import Entidades.Personas;
import Entidades.Procesos;
import Entidades.ReformasLaborales;
import Entidades.Sets;
import Entidades.Sucursales;
import Entidades.Telefonos;
import Entidades.TercerosSucursales;
import Entidades.TiposContratos;
import Entidades.TiposDocumentos;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.Unidades;
import Entidades.VWValidaBancos;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasCargos;
import Entidades.VigenciasContratos;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasFormasPagos;
import Entidades.VigenciasJornadas;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasNormasEmpleados;
import Entidades.VigenciasReformasLaborales;
import Entidades.VigenciasSueldos;
import Entidades.VigenciasTiposContratos;
import Entidades.VigenciasTiposTrabajadores;
import Entidades.VigenciasUbicaciones;
import InterfaceAdministrar.AdministrarCarpetaPersonalInterface;
import InterfaceAdministrar.AdministrarPersonaIndividualInterface;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlPersonaIndividual implements Serializable {

    @EJB
    AdministrarPersonaIndividualInterface administrarPersonaIndividual;
    @EJB
    AdministrarCarpetaPersonalInterface administrarCarpetaPersonal;
    //Modulo Informacion Personal
    private Personas nuevaPersona;
    private Empleados nuevoEmpleado;
    private int indexInformacionPersonal;
    private boolean permitirIndexInformacionPersonal;
    private String auxInformacionPersonaEmpresal, auxInformacionPersonalCiudadNacimiento;
    private String auxInformacionPersonalCiudadDocumento, auxInformacionPersonalTipoDocumento;
    private Date fechaIngreso, fechaCorte;
    private Date auxFechaNacimiento, auxFechaIngreso, auxFechaCorte;
    //Modulo Cargo Desempeñado
    private VigenciasCargos nuevaVigenciaCargo;
    private int indexCargoDesempeñado;
    private boolean permitirIndexCargoDesempeñado;
    private String auxCargoDesempeñadoNombreCargo, auxCargoDesempeñadoMotivoCargo, auxCargoDesempeñadoNombreEstructura;
    private String auxCargoDesempeñadoPapel, auxCargoDesempeñadoEmpleadoJefe;
    //Modulo Centro de Costo - Localizacion
    private VigenciasLocalizaciones nuevaVigenciaLocalizacion;
    private int indexCentroCosto;
    private boolean permitirIndexCentroCosto;
    private String auxCentroCostoMotivo, auxCentroCostoEstructura;
    //Modulo Tipo Trabajador
    private VigenciasTiposTrabajadores nuevaVigenciaTipoTrabajador;
    private int indexTipoTrabajador;
    private boolean permitirIndexTipoTrabajador;
    private String auxTipoTrabajadorNombreTipoTrabajador;
    //Modulo Reforma Laboral
    private VigenciasReformasLaborales nuevaVigenciaReformaLaboral;
    private int indexTipoSalario;
    private boolean permitirIndexTipoSalario;
    private String auxTipoSalarioReformaLaboral;
    //Modulo Sueldo
    private VigenciasSueldos nuevaVigenciaSueldo;
    private int indexSueldo;
    private boolean permitirIndexSueldo;
    private String auxSueldoMotivoSueldo, auxSueldoTipoSueldo, auxSueldoUnidad;
    private BigDecimal valorSueldo;
    private BigDecimal auxSueldoValor;
    //Modulo Tipo Contrato
    private VigenciasTiposContratos nuevaVigenciaTipoContrato;
    private int indexTipoContrato;
    private boolean permitirIndexTipoContrato;
    private String auxTipoContratoMotivo, auxTipoContratoTipoContrato;
    private Date auxTipoContratoFecha;
    //Modulo Norma Laboral
    private VigenciasNormasEmpleados nuevaVigenciaNormaEmpleado;
    private int indexNormaLaboral;
    private boolean permitirIndexNormaLaboral;
    private String auxNormaLaboralNorma;
    //Modulo Legislacion Laboral
    private VigenciasContratos nuevaVigenciaContrato;
    private int indexLegislacionLaboral;
    private boolean permitirIndexLegislacionLaboral;
    private String auxLegislacionLaboralNombre;
    // Modulo Ubicacion Geografica
    private VigenciasUbicaciones nuevaVigenciaUbicacion;
    private int indexUbicacionGeografica;
    private boolean permitirIndexUbicacionGeografica;
    private String auxUbicacionGeograficaUbicacion;
    //Modulo Jornada Laboral
    private VigenciasJornadas nuevaVigenciaJornada;
    private int indexJornadaLaboral;
    private boolean permitirIndexJornadaLaboral;
    private String auxJornadaLaboralJornada;
    //Modulo Forma Pago
    private VigenciasFormasPagos nuevaVigenciaFormaPago;
    private int indexFormaPago;
    private boolean permitirIndexFormaPago;
    private String auxFormaPagoPeriodicidad, auxFormaPagoSucursal, auxFormaPagoMetodo;
    //Modulo Afiliaciones
    //EPS
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionEPS;
    private int indexAfiliacionEPS;
    private boolean permitirIndexAfiliacionEPS;
    private String auxAfiliacionEPS;
    //AFP
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionAFP;
    private int indexAfiliacionAFP;
    private boolean permitirIndexAfiliacionAFP;
    private String auxAfiliacionAFP;
    //Fondo Censantias
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionFondo;
    private int indexAfiliacionFondo;
    private boolean permitirIndexAfiliacionFondo;
    private String auxAfiliacionFondo;
    //ARP
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionARP;
    private int indexAfiliacionARP;
    private boolean permitirIndexAfiliacionARP;
    private String auxAfiliacionARP;
    //Caja
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionCaja;
    private int indexAfiliacionCaja;
    private boolean permitirIndexAfiliacionCaja;
    private String auxAfiliacionCaja;
    //Modulo Estado Civil
    private VigenciasEstadosCiviles nuevoEstadoCivil;
    private int indexEstadoCivil;
    private boolean permitirIndexEstadoCivil;
    private String auxEstadoCivilEstado;
    //Modulo Direcciones
    private Direcciones nuevaDireccion;
    private int indexDireccion;
    private boolean permitirIndexDireccion;
    private String auxDireccionCiudad;
    //Modulo Telefonos
    private Telefonos nuevoTelefono;
    private int indexTelefono;
    private boolean permitirIndexTelefono;
    private String auxTelefonoTipo, auxTelefonoCiudad;
    //LOVS
    private List<TiposTelefonos> lovTiposTelefonos;
    private List<TiposTelefonos> filtrarLovTiposTelefonos;
    private TiposTelefonos tipoTelefonoSeleccionado;
    private List<EstadosCiviles> lovEstadosCiviles;
    private List<EstadosCiviles> filtrarLovEstadosCiviles;
    private EstadosCiviles estadoCivilSeleccionado;
    private List<TercerosSucursales> lovTercerosSucursales;
    private List<TercerosSucursales> filtrarLovTercerosSucursales;
    private TercerosSucursales terceroSucursalSeleccionado;
    private List<JornadasLaborales> lovJornadasLaborales;
    private List<JornadasLaborales> filtrarLovJornadasLaborales;
    private JornadasLaborales jornadaLaboralSeleccionada;
    private List<Periodicidades> lovPeriodicidades;
    private List<Periodicidades> filtrarLovPeriodicidades;
    private Periodicidades periodicidadSeleccionada;
    private List<Sucursales> lovSucursales;
    private List<Sucursales> filtrarLovSucursales;
    private Sucursales sucursalSeleccionada;
    private List<MetodosPagos> lovMetodosPagos;
    private List<MetodosPagos> filtrarLovMetodosPagos;
    private MetodosPagos metodoPagoSeleccionado;
    private List<UbicacionesGeograficas> lovUbicacionesGeograficas;
    private List<UbicacionesGeograficas> filtrarLovUbicacionesGeograficas;
    private UbicacionesGeograficas ubicacionGeograficaSeleccionada;
    private List<Contratos> lovContratos;
    private List<Contratos> filtrarLovContratos;
    private Contratos contratoSeleccionado;
    private List<NormasLaborales> lovNormasLaborales;
    private List<NormasLaborales> filtrarLovNormasLaborales;
    private NormasLaborales normaLaboralSeleccionada;
    private List<MotivosContratos> lovMotivosContratos;
    private List<MotivosContratos> filtrarLovMotivosContratos;
    private MotivosContratos motivoContratoSeleccionado;
    private List<TiposContratos> lovTiposContratos;
    private List<TiposContratos> filtrarLovTiposContratos;
    private TiposContratos tipoContratoSeleccionado;
    private List<MotivosCambiosSueldos> lovMotivosCambiosSueldos;
    private List<MotivosCambiosSueldos> filtrarLovMotivosCambiosSueldos;
    private MotivosCambiosSueldos motivoCambioSueldoSeleccionado;
    private List<TiposSueldos> lovTiposSueldos;
    private List<TiposSueldos> filtrarLovTiposSueldos;
    private TiposSueldos tipoSueldoSeleccionado;
    private List<Unidades> lovUnidades;
    private List<Unidades> filtrarLovUnidades;
    private Unidades unidadSeleccionada;
    private List<ReformasLaborales> lovReformasLaborales;
    private List<ReformasLaborales> filtrarLovReformasLaborales;
    private ReformasLaborales reformaLaboralSeleccionada;
    private List<TiposTrabajadores> lovTiposTrabajadores;
    private List<TiposTrabajadores> filtrarLovTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    private List<Empresas> lovEmpresas;
    private List<Empresas> filtrarLovEmpresas;
    private Empresas empresaSeleccionada;
    private List<TiposDocumentos> lovTiposDocumentos;
    private List<TiposDocumentos> filtrarLovTiposDocumentos;
    private TiposDocumentos tipoDocumentoSeleccionado;
    private List<Ciudades> lovCiudades;
    private List<Ciudades> filtrarLovCiudades;
    private Ciudades ciudadSeleccionada;
    private List<Cargos> lovCargos;
    private List<Cargos> filtrarLovCargos;
    private Cargos cargoSeleccionado;
    private List<MotivosCambiosCargos> lovMotivosCambiosCargos;
    private List<MotivosCambiosCargos> filtrarLovMotivosCambiosCargos;
    private MotivosCambiosCargos motivoCambioCargoSeleccionado;
    private List<Estructuras> lovEstructuras;
    private List<Estructuras> filtrarLovEstructuras;
    private Estructuras estructuraSeleccionada;
    private List<Papeles> lovPapeles;
    private List<Papeles> filtrarLovPapeles;
    private Papeles papelSeleccionado;
    private List<Empleados> lovEmpleados;
    private List<Empleados> filtrarLovEmpleados;
    private Empleados empleadoSeleccionado;
    private List<MotivosLocalizaciones> lovMotivosLocalizaciones;
    private List<MotivosLocalizaciones> filtrarLovMotivosLocalizaciones;
    private MotivosLocalizaciones motivoLocalizacionSeleccionado;
    private List<Estructuras> lovEstructurasCentroCosto;
    private List<Estructuras> filtrarLovEstructurasCentroCosto;
    private Estructuras estructuraCentroCostoSeleccionada;
    //Otros
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Date fechaDesdeParametro, fechaHastaParametro;
    private ParametrosEstructuras parametrosEstructuras;
    private Date fechaParametro;
    private boolean disableNombreEstructuraCargo;
    private boolean disableDescripcionEstructura;
    private boolean disableUbicacionGeografica;
    private boolean disableAfiliaciones;
    private boolean disableCamposDependientesTipoTrabajador;
    private boolean aceptar;
    private BigInteger l;
    private int k;
    //Info Registros Lovs
    private String infoRegistroEmpresaInformacionPersonal;
    private String infoRegistroTipoDocumentoInformacionPersonal;
    private String infoRegistroCiudadDocumentoInformacionPersonal;
    private String infoRegistroCiudadNacimientoInformacionPersonal;
    private String infoRegistroCargoCargoDesempenado;
    private String infoRegistroMotivoCargoCargoDesempenado;
    private String infoRegistroEstructuraCargoDesempenado;
    private String infoRegistroPapelCargoDesempenado;
    private String infoRegistroJefeCargoDesempenado;
    private String infoRegistroMotivoCentroCosto;
    private String infoRegistroEstructuraCentroCosto;
    private String infoRegistroTipoTrabajadorTipoTrabajador;
    private String infoRegistroReformaTipoSalario;
    private String infoRegistroMotivoSueldo;
    private String infoRegistroTipoSueldoSueldo;
    private String infoRegistroUnidadSueldo;
    private String infoRegistroMotivoTipoContrato;
    private String infoRegistroTipoTipoContrato;
    private String infoRegistroNormaNormaLaboral;
    private String infoRegistroContratoLegislacionLaboral;
    private String infoRegistroUbicacionUbicacion;
    private String infoRegistroJornadaJornadaLaboral;
    private String infoRegistroFormaFormaPago;
    private String infoRegistroSucursalFormaPago;
    private String infoRegistroMetodoFormaPago;
    private String infoRegistroTerceroAfiliacion;
    private String infoRegistroEstadoCivilEstadoCivil;
    private String infoRegistroCiudadDireccion;
    private String infoRegistroCiudadTelefono;
    private String infoRegistroTipoTelefonoTelefono;
    //
    private String mensajeErrorFechasEmpleado;
    //
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public ControlPersonaIndividual() {
        nuevoEmpleado = new Empleados();
        nuevoEmpleado.setEmpresa(new Empresas());
        nuevaPersona = new Personas();
        nuevaPersona.setCiudaddocumento(new Ciudades());
        nuevaPersona.setCiudadnacimiento(new Ciudades());
        nuevaPersona.setTipodocumento(new TiposDocumentos());
        nuevaVigenciaCargo = new VigenciasCargos();
        nuevaVigenciaCargo.setPapel(new Papeles());
        nuevaVigenciaCargo.setCargo(new Cargos());
        nuevaVigenciaCargo.setEstructura(new Estructuras());
        nuevaVigenciaCargo.setMotivocambiocargo(new MotivosCambiosCargos());
        nuevaVigenciaCargo.setEmpleadojefe(new Empleados());
        nuevaVigenciaCargo.getEmpleadojefe().setPersona(new Personas());
        nuevaVigenciaLocalizacion = new VigenciasLocalizaciones();
        nuevaVigenciaLocalizacion.setLocalizacion(new Estructuras());
        nuevaVigenciaLocalizacion.getLocalizacion().setCentrocosto(new CentrosCostos());
        nuevaVigenciaLocalizacion.setMotivo(new MotivosLocalizaciones());
        nuevaVigenciaTipoTrabajador = new VigenciasTiposTrabajadores();
        nuevaVigenciaTipoTrabajador.setTipotrabajador(new TiposTrabajadores());
        nuevaVigenciaReformaLaboral = new VigenciasReformasLaborales();
        nuevaVigenciaReformaLaboral.setReformalaboral(new ReformasLaborales());
        nuevaVigenciaSueldo = new VigenciasSueldos();
        nuevaVigenciaSueldo.setUnidadpago(new Unidades());
        nuevaVigenciaSueldo.setTiposueldo(new TiposSueldos());
        nuevaVigenciaSueldo.setMotivocambiosueldo(new MotivosCambiosSueldos());
        nuevaVigenciaTipoContrato = new VigenciasTiposContratos();
        nuevaVigenciaTipoContrato.setTipocontrato(new TiposContratos());
        nuevaVigenciaTipoContrato.setMotivocontrato(new MotivosContratos());
        nuevaVigenciaNormaEmpleado = new VigenciasNormasEmpleados();
        nuevaVigenciaNormaEmpleado.setNormalaboral(new NormasLaborales());
        nuevaVigenciaContrato = new VigenciasContratos();
        nuevaVigenciaContrato.setContrato(new Contratos());
        nuevaVigenciaUbicacion = new VigenciasUbicaciones();
        nuevaVigenciaUbicacion.setUbicacion(new UbicacionesGeograficas());
        nuevaVigenciaFormaPago = new VigenciasFormasPagos();
        nuevaVigenciaFormaPago.setSucursal(new Sucursales());
        nuevaVigenciaFormaPago.setMetodopago(new MetodosPagos());
        nuevaVigenciaFormaPago.setFormapago(new Periodicidades());
        nuevaVigenciaJornada = new VigenciasJornadas();
        nuevaVigenciaJornada.setJornadatrabajo(new JornadasLaborales());
        nuevaVigenciaAfiliacionAFP = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionAFP.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionARP = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionARP.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionCaja = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionCaja.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionEPS = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionEPS.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionFondo = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionFondo.setTercerosucursal(new TercerosSucursales());
        nuevoEstadoCivil = new VigenciasEstadosCiviles();
        nuevoEstadoCivil.setEstadocivil(new EstadosCiviles());
        nuevaDireccion = new Direcciones();
        nuevaDireccion.setCiudad(new Ciudades());
        nuevoTelefono = new Telefonos();
        nuevoTelefono.setCiudad(new Ciudades());
        nuevoTelefono.setTipotelefono(new TiposTelefonos());
        //Lovs
        lovTiposTelefonos = null;
        tipoTelefonoSeleccionado = new TiposTelefonos();
        lovEstadosCiviles = null;
        estadoCivilSeleccionado = new EstadosCiviles();
        lovTercerosSucursales = null;
        terceroSucursalSeleccionado = new TercerosSucursales();
        lovJornadasLaborales = null;
        jornadaLaboralSeleccionada = new JornadasLaborales();
        lovPeriodicidades = null;
        periodicidadSeleccionada = new Periodicidades();
        lovSucursales = null;
        sucursalSeleccionada = new Sucursales();
        lovMetodosPagos = null;
        metodoPagoSeleccionado = new MetodosPagos();
        lovUbicacionesGeograficas = null;
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        lovContratos = null;
        contratoSeleccionado = new Contratos();
        lovNormasLaborales = null;
        normaLaboralSeleccionada = new NormasLaborales();
        lovMotivosContratos = null;
        motivoContratoSeleccionado = new MotivosContratos();
        lovTiposContratos = null;
        tipoContratoSeleccionado = new TiposContratos();
        lovMotivosCambiosSueldos = null;
        motivoCambioSueldoSeleccionado = new MotivosCambiosSueldos();
        lovTiposSueldos = null;
        tipoSueldoSeleccionado = new TiposSueldos();
        lovUnidades = null;
        unidadSeleccionada = new Unidades();
        lovReformasLaborales = null;
        reformaLaboralSeleccionada = new ReformasLaborales();
        lovTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        lovPapeles = null;
        papelSeleccionado = new Papeles();
        lovEstructuras = null;
        estructuraSeleccionada = new Estructuras();
        lovMotivosCambiosCargos = null;
        motivoCambioCargoSeleccionado = new MotivosCambiosCargos();
        lovCargos = null;
        cargoSeleccionado = new Cargos();
        lovEmpresas = null;
        empresaSeleccionada = new Empresas();
        lovTiposDocumentos = null;
        tipoDocumentoSeleccionado = new TiposDocumentos();
        lovCiudades = null;
        ciudadSeleccionada = new Ciudades();
        lovMotivosLocalizaciones = null;
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        lovEstructurasCentroCosto = null;
        estructuraCentroCostoSeleccionada = new Estructuras();
        lovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        //Index
        indexInformacionPersonal = -1;
        indexCargoDesempeñado = -1;
        indexCentroCosto = -1;
        indexTipoTrabajador = -1;
        indexTipoSalario = -1;
        indexSueldo = -1;
        indexTipoContrato = -1;
        indexNormaLaboral = -1;
        indexLegislacionLaboral = -1;
        indexUbicacionGeografica = -1;
        indexFormaPago = -1;
        indexJornadaLaboral = -1;
        indexAfiliacionAFP = -1;
        indexAfiliacionARP = -1;
        indexAfiliacionCaja = -1;
        indexAfiliacionEPS = -1;
        indexAfiliacionFondo = -1;
        indexEstadoCivil = -1;
        indexDireccion = -1;
        indexTelefono = -1;
        //Auxiliares
        auxFechaNacimiento = null;
        auxFechaIngreso = null;
        auxFechaCorte = null;
        auxTipoContratoFecha = null;
        valorSueldo = new BigDecimal("0");
        auxSueldoValor = new BigDecimal("0");
        //Otros
        k = 0;
        aceptar = true;
        disableNombreEstructuraCargo = true;
        disableDescripcionEstructura = true;
        disableUbicacionGeografica = true;
        disableAfiliaciones = true;
        disableCamposDependientesTipoTrabajador = true;
        mensajeErrorFechasEmpleado = "";
        //Permitir Index
        permitirIndexInformacionPersonal = true;
        permitirIndexCentroCosto = true;
        permitirIndexCargoDesempeñado = true;
        permitirIndexTipoTrabajador = true;
        permitirIndexTipoSalario = true;
        permitirIndexSueldo = true;
        permitirIndexTipoContrato = true;
        permitirIndexNormaLaboral = true;
        permitirIndexLegislacionLaboral = true;
        permitirIndexUbicacionGeografica = true;
        permitirIndexFormaPago = true;
        permitirIndexJornadaLaboral = true;
        permitirIndexAfiliacionAFP = true;
        permitirIndexAfiliacionARP = true;
        permitirIndexAfiliacionCaja = true;
        permitirIndexAfiliacionEPS = true;
        permitirIndexAfiliacionFondo = true;
        permitirIndexEstadoCivil = true;
        permitirIndexDireccion = true;
        permitirIndexTelefono = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarPersonaIndividual.obtenerConexion(ses.getId());
            administrarCarpetaPersonal.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void procesoDialogoEmpresa() {
        getInfoRegistroEmpresaInformacionPersonal();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpresaInformacionPersonalDialogo");
        context.execute("EmpresaInformacionPersonalDialogo.show()");
    }

    public void listaValoresInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexInformacionPersonal == 0) {
            getInfoRegistroEmpresaInformacionPersonal();
            context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
            context.execute("EmpresaInformacionPersonalDialogo.show()");
            indexInformacionPersonal = -1;
        }
        if (indexInformacionPersonal == 5) {
            context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
            context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
            indexInformacionPersonal = -1;
        }
        if (indexInformacionPersonal == 7) {
            context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
            context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
            indexInformacionPersonal = -1;
        }
        if (indexInformacionPersonal == 9) {
            context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
            context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
            indexInformacionPersonal = -1;
        }

    }

    public void listaValoresCargoDesempenado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCargoDesempeñado == 0) {
            context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
            context.execute("CargoCargoDesempeñadoDialogo.show()");
            indexCargoDesempeñado = -1;
        }
        if (indexCargoDesempeñado == 1) {
            context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
            context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
            indexCargoDesempeñado = -1;
        }
        if (indexCargoDesempeñado == 2) {
            context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
            context.execute("EstructuraCargoDesempeñadoDialogo.show()");
            indexCargoDesempeñado = -1;
        }
        if (indexCargoDesempeñado == 3) {
            context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
            context.execute("PapelCargoDesempeñadoDialogo.show()");
            indexCargoDesempeñado = -1;
        }
        if (indexCargoDesempeñado == 4) {
            context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
            context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
            indexCargoDesempeñado = -1;
        }

    }

    public void listaValoresCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCentroCosto == 0) {
            context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
            context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
            indexCentroCosto = -1;
        }
        if (indexCentroCosto == 1) {
            context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
            context.execute("EstructuraCentroCostoDialogo.show()");
            indexCentroCosto = -1;
        }

    }

    public void listaValoresTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexTipoTrabajador == 0) {
            context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
            context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
            indexTipoTrabajador = -1;
        }

    }

    public void listaValoresTipoSalario() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexTipoSalario == 0) {
            context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
            context.execute("ReformaLaboralTipoSalarioDialogo.show()");
            indexTipoSalario = -1;
        }
    }

    public void listaValoresSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexSueldo == 1) {
            context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
            context.execute("MotivoCambioSueldoSueldoDialogo.show()");
            indexSueldo = -1;
        }
        if (indexSueldo == 2) {
            context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
            context.execute("TipoSueldoSueldoDialogo.show()");
            indexSueldo = -1;
        }
        if (indexSueldo == 3) {
            context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
            context.execute("UnidadSueldoDialogo.show()");
            indexSueldo = -1;
        }
    }

    public void listaValoresTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexTipoContrato == 0) {
            context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
            context.execute("MotivoContratoTipoContratoDialogo.show()");
            indexTipoContrato = -1;
        }
        if (indexTipoContrato == 1) {
            context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
            context.execute("TipoContratoTipoContratoDialogo.show()");
            indexTipoContrato = -1;
        }
    }

    public void listaValoresNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexNormaLaboral == 0) {
            context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
            context.execute("NormaLaboralNormaLaboralDialogo.show()");
            indexNormaLaboral = -1;
        }
    }

    public void listaValoresLegislacionLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexLegislacionLaboral == 0) {
            context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
            context.execute("ContratoLegislacionLaboralDialogo.show()");
            indexLegislacionLaboral = -1;
        }
    }

    public void listaValoresUbicacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexUbicacionGeografica == 0) {
            context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
            context.execute("UbicacionUbicacionGeograficaDialogo.show()");
            indexUbicacionGeografica = -1;
        }
    }

    public void listaValoresJornadaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexJornadaLaboral == 0) {
            context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
            context.execute("JornadaJornadaLaboralDialogo.show()");
            indexJornadaLaboral = -1;
        }
    }

    public void listaValoresFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexFormaPago == 0) {
            context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
            context.execute("PeriodicidadFormaPagoDialogo.show()");
            indexFormaPago = -1;
        }
        if (indexFormaPago == 3) {
            context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
            context.execute("SucursalFormaPagoDialogo.show()");
            indexFormaPago = -1;
        }
        if (indexFormaPago == 4) {
            context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
            context.execute("MetodoPagoFormaPagoDialogo.show()");
            indexFormaPago = -1;
        }
    }

    public void listaValoresAfiliacionEPS() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAfiliacionEPS == 0) {
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionARP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAfiliacionARP == 0) {
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionAFP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAfiliacionAFP == 0) {
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionCaja() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAfiliacionCaja == 0) {
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionFondo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexAfiliacionFondo == 0) {
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexEstadoCivil == 0) {
            context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
            context.execute("EstadoCivilEstadoCivilDialogo.show()");
            indexEstadoCivil = -1;
        }
    }

    public void listaValoresDireccion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexDireccion == 1) {
            context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
            context.execute("CiudadDireccionDialogo.show()");
            indexDireccion = -1;
        }
    }

    public void listaValoresTelefono() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexTelefono == 0) {
            context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
            context.execute("TipoTelefonoTelefonoDialogo.show()");
            indexTelefono = -1;
        }
        if (indexTelefono == 1) {
            context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
            context.execute("CiudadTelefonoDialogo.show()");
            indexTelefono = -1;
        }
    }

    public void botonListaValores() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexInformacionPersonal >= 0) {
            if (indexInformacionPersonal == 0) {
                getInfoRegistroEmpresaInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
                context.execute("EmpresaInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 5) {
                getInfoRegistroTipoDocumentoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
                context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 7) {
                getInfoRegistroCiudadDocumentoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
                context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 9) {
                getInfoRegistroCiudadNacimientoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
                context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
        } else if (indexCargoDesempeñado >= 0) {
            if (indexCargoDesempeñado == 0) {
                getInfoRegistroCargoCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
                context.execute("CargoCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 1) {
                getInfoRegistroMotivoCargoCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
                context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 2) {
                getInfoRegistroEstructuraCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
                context.execute("EstructuraCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 3) {
                getInfoRegistroPapelCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
                context.execute("PapelCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 4) {
                getInfoRegistroJefeCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
                context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
        } else if (indexCentroCosto >= 0) {
            if (indexCentroCosto == 0) {
                getInfoRegistroMotivoCentroCosto();
                context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
                context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
                indexCentroCosto = -1;
            }
            if (indexCentroCosto == 1) {
                getInfoRegistroEstructuraCentroCosto();
                context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
                context.execute("EstructuraCentroCostoDialogo.show()");
                indexCentroCosto = -1;
            }
        } else if (indexTipoTrabajador >= 0) {
            if (indexTipoTrabajador == 0) {
                getInfoRegistroTipoTrabajadorTipoTrabajador();
                context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                indexTipoTrabajador = -1;
            }
        } else if (indexTipoSalario >= 0) {
            if (indexTipoSalario == 0) {
                getInfoRegistroReformaTipoSalario();
                context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                indexTipoSalario = -1;
            }
        } else if (indexSueldo >= 0) {
            if (indexSueldo == 1) {
                getInfoRegistroMotivoSueldo();
                context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
                context.execute("MotivoCambioSueldoSueldoDialogo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 2) {
                getInfoRegistroTipoSueldoSueldo();
                context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 3) {
                getInfoRegistroUnidadSueldo();
                context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
                context.execute("UnidadSueldoDialogo.show()");
                indexSueldo = -1;
            }
        } else if (indexTipoContrato >= 0) {
            if (indexTipoContrato == 0) {
                getInfoRegistroMotivoTipoContrato();
                context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
                context.execute("MotivoContratoTipoContratoDialogo.show()");
                indexTipoContrato = -1;
            }
            if (indexTipoContrato == 1) {
                getInfoRegistroTipoTipoContrato();
                context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
                context.execute("TipoContratoTipoContratoDialogo.show()");
                indexTipoContrato = -1;
            }
        } else if (indexNormaLaboral >= 0) {
            if (indexNormaLaboral == 0) {
                getInfoRegistroNormaNormaLaboral();
                context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                indexNormaLaboral = -1;
            }
        } else if (indexLegislacionLaboral >= 0) {
            if (indexLegislacionLaboral == 0) {
                getInfoRegistroContratoLegislacionLaboral();
                context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
                context.execute("ContratoLegislacionLaboralDialogo.show()");
                indexLegislacionLaboral = -1;
            }
        } else if (indexUbicacionGeografica >= 0) {
            if (indexUbicacionGeografica == 0) {
                getInfoRegistroUbicacionUbicacion();
                context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
                context.execute("UbicacionUbicacionGeograficaDialogo.show()");
                indexUbicacionGeografica = -1;
            }
        } else if (indexJornadaLaboral >= 0) {
            if (indexJornadaLaboral == 0) {
                getInfoRegistroJornadaJornadaLaboral();
                context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
                context.execute("JornadaJornadaLaboralDialogo.show()");
                indexJornadaLaboral = -1;
            }
        } else if (indexFormaPago >= 0) {
            if (indexFormaPago == 0) {
                getInfoRegistroFormaFormaPago();
                context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 3) {
                getInfoRegistroSucursalFormaPago();
                context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 4) {
                getInfoRegistroMetodoFormaPago();
                context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
        } else if (indexAfiliacionAFP >= 0) {
            if (indexAfiliacionAFP == 0) {
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (indexAfiliacionARP >= 0) {
            if (indexAfiliacionARP == 0) {
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (indexAfiliacionCaja >= 0) {
            if (indexAfiliacionCaja == 0) {
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (indexAfiliacionEPS >= 0) {
            if (indexAfiliacionEPS == 0) {
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (indexAfiliacionFondo >= 0) {
            if (indexAfiliacionFondo == 0) {
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (indexEstadoCivil >= 0) {
            if (indexEstadoCivil == 0) {
                getInfoRegistroEstadoCivilEstadoCivil();
                context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
                context.execute("EstadoCivilEstadoCivilDialogo.show()");
                indexEstadoCivil = -1;
            }
        } else if (indexDireccion >= 0) {
            if (indexDireccion == 1) {
                getInfoRegistroCiudadDireccion();
                context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
                context.execute("CiudadDireccionDialogo.show()");
                indexDireccion = -1;
            }
        } else if (indexTelefono >= 0) {
            if (indexTelefono == 0) {
                getInfoRegistroTipoTelefonoTelefono();
                context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
                context.execute("TipoTelefonoTelefonoDialogo.show()");
                indexTelefono = -1;
            }
            if (indexTelefono == 1) {
                getInfoRegistroCiudadTelefono();
                context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
                context.execute("CiudadTelefonoDialogo.show()");
                indexTelefono = -1;
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexInformacionPersonal >= 0) {
            if (indexInformacionPersonal == 0) {
                context.update("formDialogos:editarEmpresaInformacionPersonal");
                context.execute("editarEmpresaInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 1) {
                context.update("formDialogos:editarPrimerApellidoInformacionPersonal");
                context.execute("editarPrimerApellidoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 2) {
                context.update("formDialogos:editarSegundoApellidoInformacionPersonal");
                context.execute("editarSegundoApellidoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 3) {
                context.update("formDialogos:editarNombresInformacionPersonal");
                context.execute("editarNombresInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 5) {
                context.update("formDialogos:editarTipoDocumentoInformacionPersonal");
                context.execute("editarTipoDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 6) {
                context.update("formDialogos:editarNumeroDocumentoInformacionPersonal");
                context.execute("editarNumeroDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 7) {
                context.update("formDialogos:editarCiudadDocumentoInformacionPersonal");
                context.execute("editarCiudadDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 8) {
                context.update("formDialogos:editarFechaNacimientoInformacionPersonal");
                context.execute("editarFechaNacimientoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 9) {
                context.update("formDialogos:editarCiudadNacimientoInformacionPersonal");
                context.execute("editarCiudadNacimientoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 10) {
                context.update("formDialogos:editarFechaIngresoInformacionPersonal");
                context.execute("editarFechaIngresoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 11) {
                context.update("formDialogos:editarCodigoEmpleadoInformacionPersonal");
                context.execute("editarCodigoEmpleadoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 12) {
                context.update("formDialogos:editarEmailInformacionPersonal");
                context.execute("editarEmailInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 13) {
                context.update("formDialogos:editarFechaUltimoPagoInformacionPersonal");
                context.execute("editarFechaUltimoPagoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
        }
        if (indexCargoDesempeñado >= 0) {
            if (indexCargoDesempeñado == 0) {
                context.update("formDialogos:editarCargoCargoDesempeñado");
                context.execute("editarCargoCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 1) {
                context.update("formDialogos:editarMotivoCargoDesempeñado");
                context.execute("editarMotivoCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 2) {
                getLovEstructuras();
                context.update("formDialogos:editarEstructuraCargoDesempeñado");
                context.execute("editarEstructuraCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 3) {
                context.update("formDialogos:editarPapelCargoDesempeñado");
                context.execute("editarPapelCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 4) {
                context.update("formDialogos:editarEmpleadoJefeCargoDesempeñado");
                context.execute("editarEmpleadoJefeCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
        }
        if (indexCentroCosto >= 0) {
            if (indexCentroCosto == 0) {
                context.update("formDialogos:editarEstructuraCentroCosto");
                context.execute("editarEstructuraCentroCosto.show()");
                indexCentroCosto = -1;
            }
            if (indexCentroCosto == 1) {
                context.update("formDialogos:editarMotivoCentroCosto");
                context.execute("editarMotivoCentroCosto.show()");
                indexCentroCosto = -1;
            }
        }
        if (indexTipoTrabajador >= 0) {
            if (indexTipoTrabajador == 0) {
                context.update("formDialogos:editarTrabajadorTipoTrabajador");
                context.execute("editarTrabajadorTipoTrabajador.show()");
                indexTipoTrabajador = -1;
            }
        }
        if (indexTipoSalario >= 0) {
            if (indexTipoSalario == 0) {
                context.update("formDialogos:editarReformaTipoSalario");
                context.execute("editarReformaTipoSalario.show()");
                indexTipoSalario = -1;
            }
        }
        if (indexSueldo >= 0) {
            if (indexSueldo == 0) {
                context.update("formDialogos:editarValorSueldo");
                context.execute("editarValorSueldo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 1) {
                context.update("formDialogos:editarMotivoSueldo");
                context.execute("editarMotivoSueldo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 2) {
                context.update("formDialogos:editarTipoSueldoSueldo");
                context.execute("editarTipoSueldoSueldo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 3) {
                context.update("formDialogos:editarUnidadSueldo");
                context.execute("editarUnidadSueldo.show()");
                indexSueldo = -1;
            }
        }
        if (indexTipoContrato >= 0) {
            if (indexTipoContrato == 0) {
                context.update("formDialogos:editarMotivoTipoContrato");
                context.execute("editarMotivoTipoContrato.show()");
                indexTipoContrato = -1;
            }
            if (indexTipoContrato == 1) {
                context.update("formDialogos:editarTipoTipoContrato");
                context.execute("editarTipoTipoContrato.show()");
                indexTipoContrato = -1;
            }
            if (indexTipoContrato == 2) {
                context.update("formDialogos:editarFechaTipoContrato");
                context.execute("editarFechaTipoContrato.show()");
                indexTipoContrato = -1;
            }
        }
        if (indexNormaLaboral >= 0) {
            if (indexNormaLaboral == 0) {
                context.update("formDialogos:editarNormaNormaLaboral");
                context.execute("editarNormaNormaLaboral.show()");
                indexNormaLaboral = -1;
            }
        }
        if (indexLegislacionLaboral >= 0) {
            if (indexLegislacionLaboral == 0) {
                context.update("formDialogos:editarContratoLegislacionLaboral");
                context.execute("editarContratoLegislacionLaboral.show()");
                indexLegislacionLaboral = -1;
            }
        }
        if (indexUbicacionGeografica >= 0) {
            if (indexUbicacionGeografica == 0) {
                context.update("formDialogos:editarUbicacionUbicacionGeografica");
                context.execute("editarUbicacionUbicacionGeografica.show()");
                indexUbicacionGeografica = -1;
            }
        }
        if (indexJornadaLaboral >= 0) {
            if (indexJornadaLaboral == 0) {
                context.update("formDialogos:editarJornadaJornadaLaboral");
                context.execute("editarJornadaJornadaLaboral.show()");
                indexJornadaLaboral = -1;
            }
        }
        if (indexFormaPago >= 0) {
            if (indexFormaPago == 0) {
                context.update("formDialogos:editarPeriodicidadFormaPago");
                context.execute("editarPeriodicidadFormaPago.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 1) {
                context.update("formDialogos:editarCuentaFormaPago");
                context.execute("editarCuentaFormaPago.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 3) {
                context.update("formDialogos:editarSucursalFormaPago");
                context.execute("editarSucursalFormaPago.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 4) {
                context.update("formDialogos:editarMetodoFormaPago");
                context.execute("editarMetodoFormaPago.show()");
                indexFormaPago = -1;
            }
        }
        if (indexAfiliacionAFP >= 0) {
            if (indexAfiliacionAFP == 0) {
                context.update("formDialogos:editarAFPAfiliacion");
                context.execute("editarAFPAfiliacion.show()");
                indexAfiliacionAFP = -1;
            }
        }
        if (indexAfiliacionARP >= 0) {
            if (indexAfiliacionARP == 0) {
                context.update("formDialogos:editarARPAfiliacion");
                context.execute("editarARPAfiliacion.show()");
                indexAfiliacionARP = -1;
            }
        }
        if (indexAfiliacionCaja >= 0) {
            if (indexAfiliacionCaja == 0) {
                context.update("formDialogos:editarCajaAfiliacion");
                context.execute("editarCajaAfiliacion.show()");
                indexAfiliacionCaja = -1;
            }
        }
        if (indexAfiliacionEPS >= 0) {
            if (indexAfiliacionEPS == 0) {
                context.update("formDialogos:editarEPSAfiliacion");
                context.execute("editarEPSAfiliacion.show()");
                indexAfiliacionEPS = -1;
            }
        }
        if (indexAfiliacionFondo >= 0) {
            if (indexAfiliacionFondo == 0) {
                context.update("formDialogos:editarFondoAfiliacion");
                context.execute("editarFondoAfiliacion.show()");
                indexAfiliacionFondo = -1;
            }
        }
        if (indexEstadoCivil >= 0) {
            if (indexEstadoCivil == 0) {
                context.update("formDialogos:editarEstadoEstadoCivil");
                context.execute("editarEstadoEstadoCivil.show()");
                indexEstadoCivil = -1;
            }
        }
        if (indexDireccion >= 0) {
            if (indexDireccion == 0) {
                context.update("formDialogos:editarDireccionDireccion");
                context.execute("editarDireccionDireccion.show()");
                indexDireccion = -1;
            }
            if (indexDireccion == 1) {
                context.update("formDialogos:editarCiudadDireccion");
                context.execute("editarCiudadDireccion.show()");
                indexDireccion = -1;
            }
        }
        if (indexTelefono >= 0) {
            if (indexTelefono == 0) {
                context.update("formDialogos:editarTipoTelefonoTelefono");
                context.execute("editarTipoTelefonoTelefono.show()");
                indexTelefono = -1;
            }
            if (indexTelefono == 1) {
                context.update("formDialogos:editarCiudadTelefono");
                context.execute("editarCiudadTelefono.show()");
                indexTelefono = -1;
            }
            if (indexTelefono == 2) {
                context.update("formDialogos:editarNumeroTelTelefono");
                context.execute("editarNumeroTelTelefono.show()");
                indexTelefono = -1;
            }
        }
    }

    public void cancelarModificaciones() {
        nuevoEmpleado = new Empleados();
        nuevoEmpleado.setEmpresa(new Empresas());
        nuevaPersona = new Personas();
        nuevaPersona.setCiudaddocumento(new Ciudades());
        nuevaPersona.setCiudadnacimiento(new Ciudades());
        nuevaPersona.setTipodocumento(new TiposDocumentos());
        nuevaVigenciaCargo = new VigenciasCargos();
        nuevaVigenciaCargo.setPapel(new Papeles());
        nuevaVigenciaCargo.setCargo(new Cargos());
        nuevaVigenciaCargo.setEstructura(new Estructuras());
        nuevaVigenciaCargo.setMotivocambiocargo(new MotivosCambiosCargos());
        nuevaVigenciaCargo.setEmpleadojefe(new Empleados());
        nuevaVigenciaCargo.getEmpleadojefe().setPersona(new Personas());
        nuevaVigenciaLocalizacion = new VigenciasLocalizaciones();
        nuevaVigenciaLocalizacion.setLocalizacion(new Estructuras());
        nuevaVigenciaLocalizacion.getLocalizacion().setCentrocosto(new CentrosCostos());
        nuevaVigenciaLocalizacion.setMotivo(new MotivosLocalizaciones());
        nuevaVigenciaTipoTrabajador = new VigenciasTiposTrabajadores();
        nuevaVigenciaTipoTrabajador.setTipotrabajador(new TiposTrabajadores());
        nuevaVigenciaReformaLaboral = new VigenciasReformasLaborales();
        nuevaVigenciaReformaLaboral.setReformalaboral(new ReformasLaborales());
        nuevaVigenciaSueldo = new VigenciasSueldos();
        nuevaVigenciaSueldo.setUnidadpago(new Unidades());
        nuevaVigenciaSueldo.setTiposueldo(new TiposSueldos());
        nuevaVigenciaSueldo.setMotivocambiosueldo(new MotivosCambiosSueldos());
        nuevaVigenciaTipoContrato = new VigenciasTiposContratos();
        nuevaVigenciaTipoContrato.setTipocontrato(new TiposContratos());
        nuevaVigenciaTipoContrato.setMotivocontrato(new MotivosContratos());
        nuevaVigenciaNormaEmpleado = new VigenciasNormasEmpleados();
        nuevaVigenciaNormaEmpleado.setNormalaboral(new NormasLaborales());
        nuevaVigenciaContrato = new VigenciasContratos();
        nuevaVigenciaContrato.setContrato(new Contratos());
        nuevaVigenciaUbicacion = new VigenciasUbicaciones();
        nuevaVigenciaUbicacion.setUbicacion(new UbicacionesGeograficas());
        nuevaVigenciaFormaPago = new VigenciasFormasPagos();
        nuevaVigenciaFormaPago.setSucursal(new Sucursales());
        nuevaVigenciaFormaPago.setMetodopago(new MetodosPagos());
        nuevaVigenciaFormaPago.setFormapago(new Periodicidades());
        nuevaVigenciaJornada = new VigenciasJornadas();
        nuevaVigenciaJornada.setJornadatrabajo(new JornadasLaborales());
        nuevaVigenciaAfiliacionAFP = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionAFP.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionARP = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionARP.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionCaja = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionCaja.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionEPS = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionEPS.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaAfiliacionFondo = new VigenciasAfiliaciones();
        nuevaVigenciaAfiliacionFondo.setTercerosucursal(new TercerosSucursales());
        nuevoEstadoCivil = new VigenciasEstadosCiviles();
        nuevoEstadoCivil.setEstadocivil(new EstadosCiviles());
        nuevaDireccion = new Direcciones();
        nuevaDireccion.setCiudad(new Ciudades());
        nuevoTelefono = new Telefonos();
        nuevoTelefono.setCiudad(new Ciudades());
        nuevoTelefono.setTipotelefono(new TiposTelefonos());
        //Auxiliares
        valorSueldo = new BigDecimal("0");
        //Otros
        aceptar = true;
        disableNombreEstructuraCargo = true;
        disableDescripcionEstructura = true;
        disableUbicacionGeografica = true;
        disableAfiliaciones = true;
        disableCamposDependientesTipoTrabajador = true;
        //Index
        indexCargoDesempeñado = -1;
        indexCentroCosto = -1;
        indexTipoTrabajador = -1;
        indexTipoSalario = -1;
        indexSueldo = -1;
        indexTipoContrato = -1;
        indexNormaLaboral = -1;
        indexLegislacionLaboral = -1;
        indexUbicacionGeografica = -1;
        indexFormaPago = -1;
        indexJornadaLaboral = -1;
        indexAfiliacionAFP = -1;
        indexAfiliacionARP = -1;
        indexAfiliacionCaja = -1;
        indexAfiliacionEPS = -1;
        indexAfiliacionFondo = -1;
        indexEstadoCivil = -1;
        indexDireccion = -1;
        indexTelefono = -1;
        indexInformacionPersonal = -1;
        //Permitir Index
        permitirIndexAfiliacionAFP = true;
        permitirIndexAfiliacionARP = true;
        permitirIndexAfiliacionCaja = true;
        permitirIndexAfiliacionEPS = true;
        permitirIndexAfiliacionFondo = true;
        permitirIndexCargoDesempeñado = true;
        permitirIndexCentroCosto = true;
        permitirIndexDireccion = true;
        permitirIndexEstadoCivil = true;
        permitirIndexEstadoCivil = true;
        permitirIndexFormaPago = true;
        permitirIndexInformacionPersonal = true;
        permitirIndexJornadaLaboral = true;
        permitirIndexLegislacionLaboral = true;
        permitirIndexNormaLaboral = true;
        permitirIndexSueldo = true;
        permitirIndexTelefono = true;
        permitirIndexTipoContrato = true;
        permitirIndexTipoSalario = true;
        permitirIndexTipoTrabajador = true;
        permitirIndexUbicacionGeografica = true;
        fechaIngreso = null;
        fechaCorte = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:scrollPrincipal");
    }

    public boolean validarCamposObligatoriosEmpleado() {
        boolean retorno = true;
        int i = 1;
        if (nuevoEmpleado.getEmpresa().getSecuencia() == null) {
            System.out.println("1");
            retorno = false;
        }
        if (nuevaPersona.getPrimerapellido() == null) {
            System.out.println("2");
            retorno = false;
        } else {
            if (nuevaPersona.getPrimerapellido().isEmpty()) {
                System.out.println("3");
                retorno = false;
            }
        }
        if (nuevaPersona.getNombre() == null) {
            System.out.println("4");
            retorno = false;
        } else {
            if (nuevaPersona.getNombre().isEmpty()) {
                System.out.println("5");
                retorno = false;
            }
        }
        if (nuevaPersona.getTipodocumento().getSecuencia() == null) {
            System.out.println("6");
            retorno = false;
        }
        if (nuevaPersona.getSexo() == null) {
            System.out.println("7");
            retorno = false;
        }
        if (nuevaPersona.getNumerodocumento() == null) {
            System.out.println("8");
            retorno = false;
        }
        if (nuevaPersona.getCiudaddocumento().getSecuencia() == null) {
            System.out.println("9");
            retorno = false;
        }
        if (nuevaPersona.getCiudadnacimiento().getSecuencia() == null) {
            System.out.println("10");
            retorno = false;
        }
        if (nuevaPersona.getFechanacimiento() == null) {
            System.out.println("11");
            retorno = false;
        }
        if (fechaIngreso == null) {
            System.out.println("12");
            retorno = false;
        }
        if (nuevoEmpleado.getCodigoempleado() == null) {
            System.out.println("13");
            retorno = false;
        }
        //
        if (nuevaVigenciaCargo.getCargo().getSecuencia() == null) {
            System.out.println("14");
            retorno = false;
        }
        if (nuevaVigenciaCargo.getMotivocambiocargo().getSecuencia() == null) {
            System.out.println("15");
            retorno = false;
        }
        if (nuevaVigenciaCargo.getEstructura().getSecuencia() == null) {
            System.out.println("16");
            retorno = false;
        }
        //
        if (nuevaVigenciaLocalizacion.getMotivo().getSecuencia() == null) {
            System.out.println("17");
            retorno = false;
        }
        if (nuevaVigenciaLocalizacion.getLocalizacion().getSecuencia() == null) {
            System.out.println("18");
            retorno = false;
        }
        //
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() == null) {
            System.out.println("19");
            retorno = false;
        }
        //
        if (nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia() == null) {
            System.out.println("20");
            retorno = false;
        }
        //
        if (valorSueldo == null) {
            System.out.println("21");
            retorno = false;
        } else {
            if (valorSueldo.doubleValue() < 0) {
                System.out.println("22");
                retorno = false;
            }
        }
        if (nuevaVigenciaSueldo.getMotivocambiosueldo().getSecuencia() == null) {
            System.out.println("23");
            retorno = false;
        }
        if (nuevaVigenciaSueldo.getUnidadpago().getSecuencia() == null) {
            System.out.println("24");
            retorno = false;
        }
        if (nuevaVigenciaSueldo.getTiposueldo().getSecuencia() == null) {
            System.out.println("25");
            retorno = false;
        }
        //
        if (nuevaVigenciaTipoContrato.getMotivocontrato().getSecuencia() == null) {
            System.out.println("26");
            retorno = false;
        }
        if (nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia() == null) {
            System.out.println("27");
            retorno = false;
        }
        //
        if (nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia() == null) {
            System.out.println("28");
            retorno = false;
        }
        //
        if (nuevaVigenciaContrato.getContrato().getSecuencia() == null) {
            System.out.println("29");
            retorno = false;
        }
        //
        if (nuevaVigenciaUbicacion.getUbicacion().getSecuencia() == null) {
            System.out.println("30");
            retorno = false;
        }
        //
        if (nuevaVigenciaJornada.getJornadatrabajo().getSecuencia() == null) {
            System.out.println("31");
            retorno = false;
        }
        //
        if (nuevaVigenciaFormaPago.getFormapago().getSecuencia() == null) {
            System.out.println("32");
            retorno = false;
        }
        if (nuevaVigenciaFormaPago.getMetodopago().getSecuencia() == null) {
            System.out.println("33");
            retorno = false;
        }
        //
        if (nuevaVigenciaAfiliacionEPS.getTercerosucursal().getSecuencia() == null) {
            System.out.println("34");
            retorno = false;
        }
        //
        if (nuevaVigenciaAfiliacionARP.getTercerosucursal().getSecuencia() == null) {
            System.out.println("35");
            retorno = false;
        }

        return retorno;
    }

    public boolean validarFechasEmpleado() {
        mensajeErrorFechasEmpleado = "";
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        if (nuevaPersona.getFechanacimiento() != null) {
            if (nuevaPersona.getFechanacimiento().before(fechaParametro)) {
                retorno = false;
                mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado + " - Fecha Nacimiento";
            }
        }

        if (fechaIngreso != null) {
            if (fechaIngreso.before(fechaParametro)) {
                mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado + " - Fecha Ingreso";
                retorno = false;
            }
        }

        if (fechaCorte != null) {
            if (fechaCorte.before(fechaParametro)) {
                mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado + " - Fecha Corte";
                retorno = false;
            }
        }

        if (nuevaVigenciaTipoContrato.getFechavigencia() != null) {
            if (nuevaVigenciaTipoContrato.getFechavigencia().before(fechaParametro)) {
                mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado + " - Fecha Final Contrato";
                retorno = false;
            } else {
                if (nuevaVigenciaTipoContrato.getFechavigencia().before(fechaIngreso)) {
                    mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado + " - Fecha Final Contrato";
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public boolean validarCamposAlternativosEmpleado() {
        boolean retorno = true;
        if (nuevaPersona.getGruposanguineo().isEmpty() && !nuevaPersona.getFactorrh().isEmpty()) {
            System.out.println("1");
            retorno = false;
        }
        if (!nuevaPersona.getGruposanguineo().isEmpty() && nuevaPersona.getFactorrh().isEmpty()) {
            System.out.println("2");
            retorno = false;
        }
        if (nuevaDireccion.getDireccionalternativa() == null) {
            if (nuevaDireccion.getCiudad().getSecuencia() != null) {
                System.out.println("3");
                retorno = false;
            }
        } else {
            if (nuevaDireccion.getDireccionalternativa().isEmpty()) {
                if (nuevaDireccion.getCiudad().getSecuencia() != null) {
                    System.out.println("4");
                    retorno = false;
                }
            }
        }
        if (nuevaDireccion.getCiudad().getSecuencia() == null) {
            if ((nuevaDireccion.getDireccionalternativa() != null) && (!nuevaDireccion.getDireccionalternativa().isEmpty())) {
                System.out.println("5");
                retorno = false;
            }
        }
        if (nuevoTelefono.getNumerotelefono() > 0 && nuevoTelefono.getCiudad().getSecuencia() == null && nuevoTelefono.getTipotelefono().getSecuencia() == null) {
            System.out.println("6");
            retorno = false;
        }
        if (nuevoTelefono.getNumerotelefono() <= 0 && nuevoTelefono.getCiudad().getSecuencia() != null && nuevoTelefono.getTipotelefono().getSecuencia() == null) {
            System.out.println("7");
            retorno = false;
        }
        if (nuevoTelefono.getNumerotelefono() <= 0 && nuevoTelefono.getCiudad().getSecuencia() == null && nuevoTelefono.getTipotelefono().getSecuencia() != null) {
            System.out.println("8");
            retorno = false;
        }
        if (nuevoTelefono.getNumerotelefono() <= 0 && nuevoTelefono.getCiudad().getSecuencia() != null && nuevoTelefono.getTipotelefono().getSecuencia() != null) {
            System.out.println("9");
            retorno = false;
        }
        if (nuevoTelefono.getNumerotelefono() > 0 && nuevoTelefono.getCiudad().getSecuencia() != null && nuevoTelefono.getTipotelefono().getSecuencia() == null) {
            System.out.println("10");
            retorno = false;
        }
        if (nuevoTelefono.getNumerotelefono() > 0 && nuevoTelefono.getCiudad().getSecuencia() == null && nuevoTelefono.getTipotelefono().getSecuencia() != null) {
            System.out.println("11");
            retorno = false;
        }
        return retorno;
    }

    public void crearNuevoEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposObligatoriosEmpleado() == true) {
            if (validarFechasEmpleado() == true) {
                if (validarCamposAlternativosEmpleado() == true) {
                    k++;
                    l = BigInteger.valueOf(k);
                    String checkIntegral = administrarPersonaIndividual.obtenerCheckIntegralReformaLaboral(nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia());
                    nuevaPersona.setSecuencia(l);
                    administrarPersonaIndividual.crearNuevaPersona(nuevaPersona);
                    Personas personaAlmacenada = administrarPersonaIndividual.obtenerUltimoRegistroPersona(nuevaPersona.getNumerodocumento());
                    nuevoEmpleado.setPersona(personaAlmacenada);
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevoEmpleado.setSecuencia(l);
                    administrarPersonaIndividual.crearNuevoEmpleado(nuevoEmpleado);
                    Empleados empleadoAlmacenado = administrarPersonaIndividual.obtenerUltimoRegistroEmpleado(nuevoEmpleado.getEmpresa().getSecuencia(), nuevoEmpleado.getCodigoempleado());
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaCargo.setSecuencia(l);
                    nuevaVigenciaCargo.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaCargo.setFechavigencia(fechaIngreso);
                    if (nuevaVigenciaCargo.getPapel().getSecuencia() == null) {
                        nuevaVigenciaCargo.setPapel(null);
                    }
                    if (nuevaVigenciaCargo.getEmpleadojefe().getSecuencia() == null) {
                        nuevaVigenciaCargo.setEmpleadojefe(null);
                    }
                    administrarPersonaIndividual.crearVigenciaCargo(nuevaVigenciaCargo);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaLocalizacion.setSecuencia(l);
                    nuevaVigenciaLocalizacion.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaLocalizacion.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaLocalizacion(nuevaVigenciaLocalizacion);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaTipoTrabajador.setSecuencia(l);
                    nuevaVigenciaTipoTrabajador.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaTipoTrabajador.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaTipoTrabajador(nuevaVigenciaTipoTrabajador);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaReformaLaboral.setSecuencia(l);
                    nuevaVigenciaReformaLaboral.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaReformaLaboral.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaReformaLaboral(nuevaVigenciaReformaLaboral);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaSueldo.setSecuencia(l);
                    nuevaVigenciaSueldo.setEmpleado(empleadoAlmacenado); //
                    nuevaVigenciaSueldo.setFechavigencia(fechaIngreso); //
                    nuevaVigenciaSueldo.setValor(valorSueldo);//
                    nuevaVigenciaSueldo.setFechasistema(new Date());//
                    nuevaVigenciaSueldo.setFechavigenciaretroactivo(fechaIngreso);//
                    if (nuevaVigenciaSueldo.getUnidadpago().getSecuencia() == null) {
                        nuevaVigenciaSueldo.setUnidadpago(null);
                    }
                    if (nuevaVigenciaSueldo.getTiposueldo().getSecuencia() == null) {
                        nuevaVigenciaSueldo.setTiposueldo(null);
                    }
                    administrarPersonaIndividual.crearVigenciaSueldo(nuevaVigenciaSueldo);//
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaTipoContrato.setSecuencia(l);
                    nuevaVigenciaTipoContrato.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaTipoContrato.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaTipoContrato(nuevaVigenciaTipoContrato);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaNormaEmpleado.setSecuencia(l);
                    nuevaVigenciaNormaEmpleado.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaNormaEmpleado.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaNormaEmpleado(nuevaVigenciaNormaEmpleado);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaContrato.setSecuencia(l);
                    nuevaVigenciaContrato.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaContrato.setFechainicial(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaContrato(nuevaVigenciaContrato);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaUbicacion.setSecuencia(l);
                    nuevaVigenciaUbicacion.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaUbicacion.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaUbicacion(nuevaVigenciaUbicacion);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaJornada.setSecuencia(l);
                    nuevaVigenciaJornada.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaJornada.setFechavigencia(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaJornada(nuevaVigenciaJornada);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaFormaPago.setSecuencia(l);
                    nuevaVigenciaFormaPago.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaFormaPago.setFechavigencia(fechaIngreso);
                    if (nuevaVigenciaFormaPago.getSucursal().getSecuencia() == null) {
                        nuevaVigenciaFormaPago.setSucursal(null);
                    }
                    administrarPersonaIndividual.crearVigenciaFormaPago(nuevaVigenciaFormaPago);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaAfiliacionEPS.setSecuencia(l);
                    nuevaVigenciaAfiliacionEPS.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaAfiliacionEPS.setFechainicial(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaAfiliacion(nuevaVigenciaAfiliacionEPS);
                    //
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaAfiliacionARP.setSecuencia(l);
                    nuevaVigenciaAfiliacionARP.setEmpleado(empleadoAlmacenado);
                    nuevaVigenciaAfiliacionARP.setFechainicial(fechaIngreso);
                    administrarPersonaIndividual.crearVigenciaAfiliacion(nuevaVigenciaAfiliacionARP);
                    //
                    if (nuevaVigenciaAfiliacionAFP.getTercerosucursal().getSecuencia() != null) {
                        if (!nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre().contains("SENA")) {
                            k++;
                            l = BigInteger.valueOf(k);
                            nuevaVigenciaAfiliacionAFP.setSecuencia(l);
                            nuevaVigenciaAfiliacionAFP.setEmpleado(empleadoAlmacenado);
                            nuevaVigenciaAfiliacionAFP.setFechainicial(fechaIngreso);
                            administrarPersonaIndividual.crearVigenciaAfiliacion(nuevaVigenciaAfiliacionAFP);
                        }
                    }
                    //
                    if (nuevaVigenciaAfiliacionCaja.getTercerosucursal().getSecuencia() != null) {
                        if (!nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre().contains("SENA")) {
                            k++;
                            l = BigInteger.valueOf(k);
                            nuevaVigenciaAfiliacionCaja.setSecuencia(l);
                            nuevaVigenciaAfiliacionCaja.setEmpleado(empleadoAlmacenado);
                            nuevaVigenciaAfiliacionCaja.setFechainicial(fechaIngreso);
                            administrarPersonaIndividual.crearVigenciaAfiliacion(nuevaVigenciaAfiliacionCaja);
                        }
                    }
                    //

                    if (nuevaVigenciaAfiliacionFondo.getTercerosucursal().getSecuencia() != null) {
                        if (checkIntegral.equalsIgnoreCase("N") && !nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre().contains("SENA")) {
                            k++;
                            l = BigInteger.valueOf(k);
                            nuevaVigenciaAfiliacionFondo.setSecuencia(l);
                            nuevaVigenciaAfiliacionFondo.setEmpleado(empleadoAlmacenado);
                            nuevaVigenciaAfiliacionFondo.setFechainicial(fechaIngreso);
                            administrarPersonaIndividual.crearVigenciaAfiliacion(nuevaVigenciaAfiliacionFondo);
                        }
                    }

                    if (nuevoEstadoCivil.getSecuencia() != null) {
                        k++;
                        l = BigInteger.valueOf(k);
                        nuevoEstadoCivil.setSecuencia(l);
                        nuevoEstadoCivil.setPersona(empleadoAlmacenado.getPersona());
                        nuevoEstadoCivil.setFechavigencia(fechaIngreso);
                        administrarPersonaIndividual.crearEstadoCivil(nuevoEstadoCivil);
                    }

                    if ((nuevaDireccion.getDireccionalternativa() != null && !nuevaDireccion.getDireccionalternativa().isEmpty()) && nuevaDireccion.getCiudad().getSecuencia() != null) {
                        k++;
                        l = BigInteger.valueOf(k);
                        nuevaDireccion.setSecuencia(l);
                        nuevaDireccion.setPersona(empleadoAlmacenado.getPersona());
                        nuevaDireccion.setFechavigencia(fechaIngreso);
                        administrarPersonaIndividual.crearDireccion(nuevaDireccion);
                    }

                    if (nuevoTelefono.getCiudad().getSecuencia() != null && nuevoTelefono.getTipotelefono().getSecuencia() != null && nuevoTelefono.getNumerotelefono() > 0) {
                        k++;
                        l = BigInteger.valueOf(k);
                        nuevoTelefono.setSecuencia(l);
                        nuevoTelefono.setPersona(empleadoAlmacenado.getPersona());
                        nuevoTelefono.setFechavigencia(fechaIngreso);
                        administrarPersonaIndividual.crearTelefono(nuevoTelefono);
                    }

                    Sets nuevoSet = new Sets();
                    nuevoSet.setEmpleado(empleadoAlmacenado);
                    nuevoSet.setFechainicial(fechaIngreso);
                    nuevoSet.setPromedio(new BigDecimal("0.01"));
                    nuevoSet.setTiposet("1");
                    nuevoSet.setPorcentaje(new BigDecimal("0"));
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevoSet.setSecuencia(l);
                    administrarPersonaIndividual.crearSets(nuevoSet);

                    BigDecimal numeroComprobante = administrarPersonaIndividual.obtenerNumeroMaximoComprobante();

                    Comprobantes comprobante = new Comprobantes();
                    comprobante.setEmpleado(empleadoAlmacenado);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaIngreso); // Configuramos la fecha que se recibe
                    calendar.add(Calendar.DAY_OF_YEAR, -1);
                    Date fecha = calendar.getTime();

                    comprobante.setFecha(fecha);

                    if (numeroComprobante == null) {
                        numeroComprobante = new BigDecimal("0");
                    }
                    String valorComprobante = String.valueOf(numeroComprobante.intValue() + 1);
                    comprobante.setNumero(new BigInteger(valorComprobante));
                    comprobante.setValor(new BigDecimal("0.01"));

                    k++;
                    l = BigInteger.valueOf(k);
                    comprobante.setSecuencia(l);

                    administrarPersonaIndividual.crearComprobante(comprobante);

                    Comprobantes comprobanteEmpleado = administrarPersonaIndividual.buscarComprobanteParaPrimerRegistroEmpleado(empleadoAlmacenado.getSecuencia());

                    Procesos procesoCodigo1 = null;
                    short cod1 = 1;
                    procesoCodigo1 = administrarPersonaIndividual.buscarProcesoPorCodigo(cod1);
                    Procesos procesoCodigo80 = null;
                    short cod80 = 80;
                    procesoCodigo80 = administrarPersonaIndividual.buscarProcesoPorCodigo(cod80);

                    CortesProcesos corte = new CortesProcesos();
                    corte.setComprobante(comprobanteEmpleado);
                    if (fechaCorte == null) {
                        corte.setCorte(fecha);
                    } else {
                        corte.setCorte(fechaCorte);
                    }
                    corte.setEmpleado(empleadoAlmacenado);
                    corte.setProceso(procesoCodigo1);
                    k++;
                    l = BigInteger.valueOf(k);
                    corte.setSecuencia(l);
                    administrarPersonaIndividual.crearCortesProcesos(corte);

                    if (procesoCodigo80 != null) {
                        CortesProcesos corte2 = new CortesProcesos();
                        corte2.setComprobante(comprobanteEmpleado);
                        if (fechaCorte == null) {
                            corte2.setCorte(fecha);
                        } else {
                            corte2.setCorte(fechaCorte);
                        }
                        corte2.setEmpleado(empleadoAlmacenado);
                        corte2.setProceso(procesoCodigo80);
                        k++;
                        l = BigInteger.valueOf(k);
                        corte2.setSecuencia(l);
                        administrarPersonaIndividual.crearCortesProcesos(corte2);
                    }

                    short cod12 = 12;
                    TiposTrabajadores codigo12 = administrarPersonaIndividual.buscarTipoTrabajadorPorCodigo(cod12);
                    VigenciasTiposTrabajadores nuevaVigenciaTT = new VigenciasTiposTrabajadores();
                    nuevaVigenciaTT.setEmpleado(empleadoAlmacenado);
                    Date fechaNuevo = new Date(1, 1, 60);
                    nuevaVigenciaTT.setFechavigencia(fechaNuevo);
                    nuevaVigenciaTT.setTipotrabajador(codigo12);
                    k++;
                    l = BigInteger.valueOf(k);
                    nuevaVigenciaTT.setSecuencia(l);
                    administrarPersonaIndividual.crearVigenciaTipoTrabajador(nuevaVigenciaTT);
                    context.execute("procesoGuardadoOK.show()");
                    cancelarModificaciones();
                } else {
                    context.execute("errorCamposAlternativos.show()");
                }
            } else {
                mensajeErrorFechasEmpleado = "Existe un error en las siguientes fechas: " + mensajeErrorFechasEmpleado;
                context.update("formDialogos:errorFechasEmpleado");
                context.execute("errorFechasEmpleado.show()");
            }
        } else {
            context.execute("errorCamposObligatorios.show()");
        }
    }

    public void cambiarIndiceInformacionPersonal(int i) {
        System.out.println("cambiarIndiceInformacionPersonal : "+i);
        if (permitirIndexInformacionPersonal == true) {
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexInformacionPersonal = i;

            auxFechaCorte = fechaCorte;
            auxFechaIngreso = fechaIngreso;
            auxFechaNacimiento = nuevaPersona.getFechanacimiento();
            if (indexInformacionPersonal == 0) {
                if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
                    auxInformacionPersonaEmpresal = nuevoEmpleado.getEmpresa().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (indexInformacionPersonal == 5) {
                if (nuevaPersona.getTipodocumento().getSecuencia() != null) {
                    auxInformacionPersonalTipoDocumento = nuevaPersona.getTipodocumento().getNombrelargo();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (indexInformacionPersonal == 7) {
                if (nuevaPersona.getCiudaddocumento().getSecuencia() != null) {
                    auxInformacionPersonalCiudadDocumento = nuevaPersona.getCiudaddocumento().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (indexInformacionPersonal == 9) {
                if (nuevaPersona.getCiudadnacimiento().getSecuencia() != null) {
                    auxInformacionPersonalCiudadNacimiento = nuevaPersona.getCiudadnacimiento().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }

        }
    }

    public void cambiarIndiceCargoDesempeñado(int i) {
        if (permitirIndexCargoDesempeñado == true) {
            indexInformacionPersonal = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexCargoDesempeñado = i;
            auxCargoDesempeñadoPapel = nuevaVigenciaCargo.getPapel().getDescripcion();
            auxCargoDesempeñadoEmpleadoJefe = nuevaVigenciaCargo.getEmpleadojefe().getPersona().getNombreCompleto();
            if (indexCargoDesempeñado == 0) {
                if (nuevaVigenciaCargo.getCargo().getSecuencia() != null) {
                    auxCargoDesempeñadoNombreCargo = nuevaVigenciaCargo.getCargo().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }
            if (indexCargoDesempeñado == 1) {
                if (nuevaVigenciaCargo.getMotivocambiocargo().getSecuencia() != null) {
                    auxCargoDesempeñadoMotivoCargo = nuevaVigenciaCargo.getMotivocambiocargo().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }
            if (indexCargoDesempeñado == 2) {
                if (nuevaVigenciaCargo.getEstructura().getSecuencia() != null) {
                    auxCargoDesempeñadoNombreEstructura = nuevaVigenciaCargo.getEstructura().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }

        }
    }

    public void cambiarIndiceCentroCosto(int i) {
        if (permitirIndexCentroCosto == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexCentroCosto = i;
            if (indexCentroCosto == 0) {
                if (nuevaVigenciaLocalizacion.getMotivo().getSecuencia() != null) {
                    auxCentroCostoMotivo = nuevaVigenciaLocalizacion.getMotivo().getDescripcion();
                } else {
                    listaValoresCentroCosto();
                }
            }
            if (indexCentroCosto == 1) {
                if (nuevaVigenciaLocalizacion.getLocalizacion().getSecuencia() != null) {
                    auxCentroCostoEstructura = nuevaVigenciaLocalizacion.getLocalizacion().getCentrocosto().getNombre();
                } else {
                    listaValoresCentroCosto();
                }
            }
        }
    }

    public void cambiarIndiceTipoTrabajador(int i) {
        if (permitirIndexTipoTrabajador == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexTipoTrabajador = i;
            if (indexTipoTrabajador == 0) {
                if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
                    auxTipoTrabajadorNombreTipoTrabajador = nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre();
                } else {
                    listaValoresTipoTrabajador();
                }
            }
        }
    }

    public void cambiarIndiceReformaLaboral(int i) {
        if (permitirIndexTipoSalario == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexTipoSalario = i;
            if (indexTipoSalario == 0) {
                if (nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia() != null) {
                    auxTipoSalarioReformaLaboral = nuevaVigenciaReformaLaboral.getReformalaboral().getNombre();
                } else {
                    listaValoresTipoSalario();
                }
            }
        }
    }

    public void cambiarIndiceSueldo(int i) {
        if (permitirIndexSueldo == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexSueldo = i;
            auxSueldoValor = valorSueldo;
            if (indexSueldo == 1) {
                if (nuevaVigenciaSueldo.getMotivocambiosueldo().getSecuencia() != null) {
                    auxSueldoMotivoSueldo = nuevaVigenciaSueldo.getMotivocambiosueldo().getNombre();
                } else {
                    listaValoresSueldo();
                }
            }
            if (indexSueldo == 2) {
                if (nuevaVigenciaSueldo.getTiposueldo().getSecuencia() != null) {
                    auxSueldoTipoSueldo = nuevaVigenciaSueldo.getTiposueldo().getDescripcion();
                } else {
                    listaValoresSueldo();
                }
            }
            if (indexSueldo == 3) {
                if (nuevaVigenciaSueldo.getUnidadpago().getSecuencia() != null) {
                    auxSueldoUnidad = nuevaVigenciaSueldo.getUnidadpago().getNombre();
                } else {
                    listaValoresSueldo();
                }
            }
        }
    }

    public void cambiarIndiceTipoContrato(int i) {
        if (permitirIndexTipoContrato == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexTipoContrato = i;
            auxTipoContratoFecha = nuevaVigenciaTipoContrato.getFechavigencia();
            if (indexTipoContrato == 1) {
                if (nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia() != null) {
                    auxTipoContratoMotivo = nuevaVigenciaTipoContrato.getTipocontrato().getNombre();
                } else {
                    listaValoresTipoContrato();
                }
            }
            if (indexTipoContrato == 0) {
                if (nuevaVigenciaTipoContrato.getMotivocontrato().getSecuencia() != null) {
                    auxTipoContratoTipoContrato = nuevaVigenciaTipoContrato.getMotivocontrato().getNombre();
                } else {
                    listaValoresTipoContrato();
                }
            }
        }
    }

    public void cambiarIndiceNormaLaboral(int i) {
        if (permitirIndexNormaLaboral == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexNormaLaboral = i;
            if (indexNormaLaboral == 0) {
                if (nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia() != null) {
                    auxNormaLaboralNorma = nuevaVigenciaNormaEmpleado.getNormalaboral().getNombre();
                } else {
                    listaValoresNormaLaboral();
                }
            }
        }
    }

    public void cambiarIndiceLegislacionLaboral(int i) {
        if (permitirIndexLegislacionLaboral == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexLegislacionLaboral = i;
            if (indexLegislacionLaboral == 0) {
                if (nuevaVigenciaContrato.getContrato().getSecuencia() != null) {
                    auxLegislacionLaboralNombre = nuevaVigenciaContrato.getContrato().getDescripcion();
                } else {
                    listaValoresLegislacionLaboral();
                }
            }
        }
    }

    public void cambiarIndiceUbicacionGeografica(int i) {
        if (permitirIndexUbicacionGeografica == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexUbicacionGeografica = i;
            if (indexUbicacionGeografica == 0) {
                if (nuevaVigenciaUbicacion.getUbicacion().getSecuencia() != null) {
                    auxUbicacionGeograficaUbicacion = nuevaVigenciaUbicacion.getUbicacion().getDescripcion();
                } else {
                    listaValoresUbicacion();
                }
            }
        }
    }

    public void cambiarIndiceFormaPago(int i) {
        if (permitirIndexFormaPago == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexFormaPago = i;
            if (indexFormaPago == 0) {
                if (nuevaVigenciaFormaPago.getFormapago().getSecuencia() != null) {
                    auxFormaPagoPeriodicidad = nuevaVigenciaFormaPago.getFormapago().getNombre();
                } else {
                    listaValoresFormaPago();
                }
            }
            if (indexFormaPago == 3) {
                if (nuevaVigenciaFormaPago.getSucursal().getSecuencia() != null) {
                    auxFormaPagoSucursal = nuevaVigenciaFormaPago.getSucursal().getNombre();
                } else {
                    listaValoresFormaPago();
                }
            }
            if (indexFormaPago == 4) {
                if (nuevaVigenciaFormaPago.getMetodopago().getSecuencia() != null) {
                    auxFormaPagoMetodo = nuevaVigenciaFormaPago.getMetodopago().getDescripcion();
                } else {
                    listaValoresFormaPago();
                }
            }
        }
    }

    public void cambiarIndiceJornadaLaboral(int i) {
        if (permitirIndexJornadaLaboral == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexJornadaLaboral = i;
            if (indexJornadaLaboral == 0) {
                if (nuevaVigenciaJornada.getJornadatrabajo().getSecuencia() != null) {
                    auxJornadaLaboralJornada = nuevaVigenciaJornada.getJornadatrabajo().getDescripcion();
                } else {
                    listaValoresJornadaLaboral();
                }
            }
        }
    }

    public void cambiarIndiceAfiliacionFondo(int i) {
        if (permitirIndexAfiliacionFondo == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionEPS = -1;
            indexJornadaLaboral = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexAfiliacionFondo = i;

            auxAfiliacionFondo = nuevaVigenciaAfiliacionFondo.getTercerosucursal().getDescripcion();

        }
    }

    public void cambiarIndiceAfiliacionEPS(int i) {
        if (permitirIndexAfiliacionEPS == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionCaja = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexAfiliacionEPS = i;
            if (indexAfiliacionEPS == 0) {
                if (nuevaVigenciaAfiliacionEPS.getTercerosucursal().getSecuencia() != null) {
                    auxAfiliacionEPS = nuevaVigenciaAfiliacionEPS.getTercerosucursal().getDescripcion();
                } else {
                    listaValoresAfiliacionEPS();
                }
            }
        }
    }

    public void cambiarIndiceAfiliacionCaja(int i) {
        if (permitirIndexAfiliacionCaja == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexAfiliacionAFP = -1;
            indexAfiliacionARP = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexAfiliacionCaja = i;
            auxAfiliacionCaja = nuevaVigenciaAfiliacionCaja.getTercerosucursal().getDescripcion();
        }
    }

    public void cambiarIndiceAfiliacionARP(int i) {
        if (permitirIndexAfiliacionARP == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexAfiliacionAFP = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionCaja = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexAfiliacionARP = i;
            if (indexAfiliacionARP == 0) {
                if (nuevaVigenciaAfiliacionARP.getTercerosucursal().getSecuencia() != null) {
                    auxAfiliacionARP = nuevaVigenciaAfiliacionARP.getTercerosucursal().getDescripcion();
                } else {
                    listaValoresAfiliacionARP();
                }
            }
        }
    }

    public void cambiarIndiceAfiliacionAFP(int i) {
        if (permitirIndexAfiliacionAFP == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionARP = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexAfiliacionAFP = i;
            auxAfiliacionAFP = nuevaVigenciaAfiliacionAFP.getTercerosucursal().getDescripcion();
        }
    }

    public void cambiarIndiceEstadoCivil(int i) {
        if (permitirIndexEstadoCivil == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionAFP = -1;
            indexDireccion = -1;
            indexTelefono = -1;
            indexEstadoCivil = i;
            auxEstadoCivilEstado = nuevoEstadoCivil.getEstadocivil().getDescripcion();
        }
    }

    public void cambiarIndiceDireccion(int i) {
        if (permitirIndexDireccion == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionAFP = -1;
            indexEstadoCivil = -1;
            indexTelefono = -1;
            indexDireccion = i;
            auxDireccionCiudad = nuevaDireccion.getCiudad().getNombre();
        }
    }

    public void cambiarIndiceTelefono(int i) {
        if (permitirIndexTelefono == true) {
            indexInformacionPersonal = -1;
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
            indexTipoSalario = -1;
            indexSueldo = -1;
            indexTipoContrato = -1;
            indexNormaLaboral = -1;
            indexLegislacionLaboral = -1;
            indexUbicacionGeografica = -1;
            indexFormaPago = -1;
            indexJornadaLaboral = -1;
            indexAfiliacionFondo = -1;
            indexAfiliacionEPS = -1;
            indexAfiliacionCaja = -1;
            indexAfiliacionARP = -1;
            indexAfiliacionAFP = -1;
            indexEstadoCivil = -1;
            indexDireccion = -1;
            indexTelefono = i;
            auxTelefonoTipo = nuevoTelefono.getTipotelefono().getNombre();
            auxTelefonoCiudad = nuevoTelefono.getCiudad().getNombre();
        }
    }

    public void modificarCodigoEmpleado() {
        Empleados empleado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            empleado = administrarPersonaIndividual.buscarEmpleadoPorCodigoyEmpresa(nuevoEmpleado.getCodigoempleado(), nuevoEmpleado.getEmpresa().getSecuencia());
        } else {
            empleado = administrarPersonaIndividual.buscarEmpleadoPorCodigoyEmpresa(nuevoEmpleado.getCodigoempleado(), null);
        }
        if (empleado != null) {
            context.execute("errorEmpleadoDuplicado.show()");
        }
    }

    public void modificarNumeroDocumentoPersona() {
        RequestContext context = RequestContext.getCurrentInstance();
        Personas persona = administrarPersonaIndividual.buscarPersonaPorNumeroDocumento(nuevaPersona.getNumerodocumento());
        Empleados empleado = null;
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            empleado = administrarPersonaIndividual.buscarEmpleadoPorCodigoyEmpresa(nuevaPersona.getNumerodocumento(), nuevoEmpleado.getEmpresa().getSecuencia());
        } else {
            empleado = administrarPersonaIndividual.buscarEmpleadoPorCodigoyEmpresa(nuevaPersona.getNumerodocumento(), null);
        }
        if (persona != null && empleado != null) {
            nuevaPersona.setNumerodocumento(null);
            context.update("form:numeroDocumentoModPersonal");
            context.execute("errorEmpleadoRegistrado.show()");
        } else {
            if (persona != null && empleado == null) {
                nuevaPersona.setNumerodocumento(null);
                context.update("form:numeroDocumentoModPersonal");
                context.execute("errorPersonaRepetida.show()");
            }
            String contabilidad = administrarPersonaIndividual.obtenerPreValidadContabilidad();
            String bloqueAIngreso = administrarPersonaIndividual.obtenerPreValidaBloqueAIngreso();
            if (contabilidad != null) {
                if (contabilidad.equalsIgnoreCase("S")) {
                    VWValidaBancos valida = administrarPersonaIndividual.validarCodigoPrimarioVWValidaBancos(nuevaPersona.getNumerodocumento());
                    if (valida == null) {
                        //¡Esta ingresando un codigo de Tercero (nit de empleado) que no existe en el sistema contable. Por favor, antes de ingresar este registro en nómina, valide primero con contabilidad!.
                        context.execute("errorCodigoTerceroPersona.show()");
                    }
                    if (bloqueAIngreso.equalsIgnoreCase("S")) {
                        //¡¡¡No se puede ingresar el registro en Designer.RHN!!! Por favor salga de esta pantalla.
                        context.execute("errorIngresoTotalRegistro.show()");
                    }
                }
            }
        }
        nuevoEmpleado.setCodigoempleado(nuevaPersona.getNumerodocumento());
        context.update("form:codigoEmpleadoModPersonal");
    }

    public boolean validarFechasInformacionPersonal(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 8) {
            if (nuevaPersona.getFechanacimiento() != null) {
                if (nuevaPersona.getFechanacimiento().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 10) {
            if (fechaIngreso != null) {
                if (fechaIngreso.after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 13) {
            if (fechaCorte != null) {
                if (fechaCorte.after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public boolean validarFechasTipoContrato() {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (nuevaVigenciaTipoContrato.getFechavigencia() != null) {
            if (nuevaVigenciaTipoContrato.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }

        return retorno;
    }

    public void modificarFechaNacimientoInformacionPersonal(int i) {
        boolean retorno = validarFechasInformacionPersonal(8);
        if (retorno == true) {
            cambiarIndiceInformacionPersonal(i);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    public void modificarFechaIngresoInformacionPersonal(int i) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarFechasInformacionPersonal(10);
        if (retorno == true) {
            getFechaDesdeParametro();
            getFechaHastaParametro();
            if (fechaIngreso != null) {
                if (fechaIngreso.after(fechaDesdeParametro) && fechaIngreso.before(fechaHastaParametro)) {
                    cambiarIndiceInformacionPersonal(i);
                } else {
                    context.execute("advertenciaFechaIngreso.show()");
                    cambiarIndiceInformacionPersonal(i);
                }
            }
            modificacionesEmpresaFechaIngresoInformacionPersonal();
        } else {
            context.execute("errorFechas.show()");
            modificacionesEmpresaFechaIngresoInformacionPersonal();
        }
    }

    public void modificarFechaUltimoPagoInformacionPersonal(int i) {
        boolean retorno = validarFechasInformacionPersonal(13);
        if (retorno == true) {
            cambiarIndiceInformacionPersonal(i);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }
    
    public void posicionFechas() {
        System.out.println("posicionFechas");
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        int posicion = Integer.parseInt(name);
        cambiarIndiceInformacionPersonal(posicion);
    }

    public void modificarFechaFechaVigenciaTipoContrato(int i) {
        boolean retorno = validarFechasTipoContrato();
        if (retorno == true) {
            if (fechaIngreso != null && nuevaVigenciaTipoContrato.getFechavigencia() != null) {
                if (nuevaVigenciaTipoContrato.getFechavigencia().before(fechaIngreso)) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorFechaContratoFechaIngreso.show()");
                } else {
                    cambiarIndiceTipoContrato(i);
                }
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    public void modificarInformacionPersonal(int indice, String confirmarCambio, String valorConfirmar) {
        indexInformacionPersonal = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EMPRESA")) {
            nuevoEmpleado.getEmpresa().setNombre(auxInformacionPersonaEmpresal);
            if (lovEmpresas != null) {
                for (int i = 0; i < lovEmpresas.size(); i++) {
                    if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevoEmpleado.setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                lovEmpresas.clear();
                getLovEmpresas();
                context.update("form:empresaModPersonal");
                calcularControlEmpleadosEmpresa();
                modificacionesEmpresaFechaIngresoInformacionPersonal();
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroEmpresaInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
                context.execute("EmpresaInformacionPersonalDialogo.show()");
                context.update("form:empresaModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPODOCUMENTO")) {
            nuevaPersona.getTipodocumento().setNombrelargo(auxInformacionPersonalTipoDocumento);
            if (lovTiposDocumentos != null) {
                for (int i = 0; i < lovTiposDocumentos.size(); i++) {
                    if (lovTiposDocumentos.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setTipodocumento(lovTiposDocumentos.get(indiceUnicoElemento));
                lovTiposDocumentos.clear();
                getLovTiposDocumentos();
                context.update("form:tipoDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroTipoDocumentoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
                context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
                context.update("form:tipoDocumentoModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("NACIMIENTO")) {
            nuevaPersona.getCiudadnacimiento().setNombre(auxInformacionPersonalCiudadNacimiento);
            if (lovCiudades != null) {
                for (int i = 0; i < lovCiudades.size(); i++) {
                    if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setCiudadnacimiento(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadNacimientoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroCiudadNacimientoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
                context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
                context.update("form:ciudadNacimientoModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("DOCUMENTO")) {
            nuevaPersona.getCiudaddocumento().setNombre(auxInformacionPersonalCiudadDocumento);
            if (lovCiudades != null) {
                for (int i = 0; i < lovCiudades.size(); i++) {
                    if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setCiudaddocumento(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroCiudadDocumentoInformacionPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
                context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
                context.update("form:ciudadDocumentoModPersonal");
            }
        }
    }

    public void modificarCargoDesempeñado(int indice, String confirmarCambio, String valorConfirmar) {
        indexCargoDesempeñado = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CARGO")) {
            nuevaVigenciaCargo.getCargo().setNombre(auxCargoDesempeñadoNombreCargo);
            if (lovCargos != null) {
                for (int i = 0; i < lovCargos.size(); i++) {
                    if (lovCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setCargo(lovCargos.get(indiceUnicoElemento));
                lovCargos.clear();
                getLovCargos();
                context.update("form:cargoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroCargoCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
                context.execute("CargoCargoDesempeñadoDialogo.show()");
                context.update("form:cargoModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaCargo.getMotivocambiocargo().setNombre(auxCargoDesempeñadoMotivoCargo);
            if (lovMotivosCambiosCargos != null) {
                for (int i = 0; i < lovMotivosCambiosCargos.size(); i++) {
                    if (lovMotivosCambiosCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setMotivocambiocargo(lovMotivosCambiosCargos.get(indiceUnicoElemento));
                lovMotivosCambiosCargos.clear();
                getLovMotivosCambiosCargos();
                context.update("form:motivoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroMotivoCargoCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
                context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
                context.update("form:motivoModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            nuevaVigenciaCargo.getEstructura().setNombre(auxCargoDesempeñadoNombreEstructura);
            if (lovEstructuras != null) {
                for (int i = 0; i < lovEstructuras.size(); i++) {
                    if (lovEstructuras.get(i).getCentrocosto().getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setEstructura(lovEstructuras.get(indiceUnicoElemento));
                lovEstructuras.clear();
                getLovEstructuras();
                context.update("form:estructuraModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroEstructuraCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
                context.execute("EstructuraCargoDesempeñadoDialogo.show()");
                context.update("form:estructuraModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PAPEL")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaCargo.getPapel().setDescripcion(auxCargoDesempeñadoPapel);
                if (lovPapeles != null) {
                    for (int i = 0; i < lovPapeles.size(); i++) {
                        if (lovPapeles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaCargo.setPapel(lovPapeles.get(indiceUnicoElemento));
                    lovPapeles.clear();
                    getLovPapeles();
                    context.update("form:papelModCargoDesempeñado");
                } else {
                    permitirIndexCargoDesempeñado = false;
                    getInfoRegistroPapelCargoDesempenado();
                    context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
                    context.execute("PapelCargoDesempeñadoDialogo.show()");
                    context.update("form:papelModCargoDesempeñado");
                }
            } else {
                lovPapeles.clear();
                getLovPapeles();
                nuevaVigenciaCargo.setPapel(new Papeles());
                context.update("form:papelModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("JEFE")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaCargo.getEmpleadojefe().getPersona().setNombreCompleto(auxCargoDesempeñadoEmpleadoJefe);
                if (lovEmpleados != null) {
                    for (int i = 0; i < lovEmpleados.size(); i++) {
                        if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaCargo.setEmpleadojefe(lovEmpleados.get(indiceUnicoElemento));
                    lovEmpleados.clear();
                    getLovEmpleados();
                    context.update("form:empleadoJefeModCargoDesempeñado");
                } else {
                    permitirIndexCargoDesempeñado = false;
                    getInfoRegistroJefeCargoDesempenado();
                    context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
                    context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                    context.update("form:empleadoJefeModCargoDesempeñado");
                }
            } else {
                lovEmpleados.clear();
                getLovEmpleados();
                nuevaVigenciaCargo.setEmpleadojefe(new Empleados());
                nuevaVigenciaCargo.getEmpleadojefe().setPersona(new Personas());
                context.update("form:papelModCargoDesempeñado");
            }
        }
    }

    public void modificarCentroCosto(int indice, String confirmarCambio, String valorConfirmar) {
        indexCentroCosto = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            nuevaVigenciaLocalizacion.getLocalizacion().getCentrocosto().setNombre(auxCentroCostoEstructura);
            if (lovEstructurasCentroCosto != null) {
                for (int i = 0; i < lovEstructurasCentroCosto.size(); i++) {
                    if (lovEstructurasCentroCosto.get(i).getCentrocosto().getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaLocalizacion.setLocalizacion(lovEstructurasCentroCosto.get(indiceUnicoElemento));
                lovEstructurasCentroCosto.clear();
                getLovEstructurasCentroCosto();
                context.update("form:estructuraModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                getInfoRegistroEstructuraCentroCosto();
                context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
                context.execute("EstructuraCentroCostoDialogo.show()");
                context.update("form:estructuraModCentroCosto");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaLocalizacion.getMotivo().setDescripcion(auxCentroCostoMotivo);
            if (lovMotivosLocalizaciones != null) {
                for (int i = 0; i < lovMotivosLocalizaciones.size(); i++) {
                    if (lovMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaLocalizacion.setMotivo(lovMotivosLocalizaciones.get(indiceUnicoElemento));
                lovMotivosLocalizaciones.clear();
                getLovMotivosLocalizaciones();
                context.update("form:motivoModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                getInfoRegistroMotivoCentroCosto();
                context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
                context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
                context.update("form:motivoModCentroCosto");
            }
        }
    }

    public void modificarTipoTrabajador(int indice, String confirmarCambio, String valorConfirmar) {
        indexTipoTrabajador = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOTRABAJADOR")) {
            nuevaVigenciaTipoTrabajador.getTipotrabajador().setNombre(auxTipoTrabajadorNombreTipoTrabajador);
            if (lovTiposTrabajadores != null) {
                for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                    if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaTipoTrabajador.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                lovTiposTrabajadores.clear();
                getLovTiposTrabajadores();
                context.update("form:tipoTrabajadorModTipoTrabajador");
                validarDisableTipoTrabajador();
            } else {
                permitirIndexTipoTrabajador = false;
                getInfoRegistroTipoTrabajadorTipoTrabajador();
                context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                context.update("form:tipoTrabajadorModTipoTrabajador");
            }
        }
    }

    public void modificarTipoSalario(int indice, String confirmarCambio, String valorConfirmar) {
        indexTipoSalario = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMA")) {
            nuevaVigenciaReformaLaboral.getReformalaboral().setNombre(auxTipoSalarioReformaLaboral);
            if (lovReformasLaborales != null) {
                for (int i = 0; i < lovReformasLaborales.size(); i++) {
                    if (lovReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaReformaLaboral.setReformalaboral(lovReformasLaborales.get(indiceUnicoElemento));
                lovReformasLaborales.clear();
                getLovReformasLaborales();
                context.update("form:tipoSalarioModTipoSalario");
                validarTipoTrabajadorReformaLaboral();
            } else {
                permitirIndexTipoSalario = false;
                getInfoRegistroReformaTipoSalario();
                context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                context.update("form:tipoSalarioModTipoSalario");
            }
        }
    }

    public void modificarSueldo(int indice, String confirmarCambio, String valorConfirmar) {
        indexTipoSalario = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaSueldo.getMotivocambiosueldo().setNombre(auxSueldoMotivoSueldo);
            if (lovMotivosCambiosSueldos != null) {
                for (int i = 0; i < lovMotivosCambiosSueldos.size(); i++) {
                    if (lovMotivosCambiosSueldos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaSueldo.setMotivocambiosueldo(lovMotivosCambiosSueldos.get(indiceUnicoElemento));
                lovMotivosCambiosSueldos.clear();
                getLovMotivosCambiosSueldos();
                context.update("form:motivoSueldoModSueldo");
            } else {
                permitirIndexSueldo = false;
                getInfoRegistroMotivoSueldo();
                context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
                context.execute("MotivoCambioSueldoSueldoDialogo.show()");
                context.update("form:motivoSueldoModSueldo");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPOSUELDO")) {
            nuevaVigenciaSueldo.getTiposueldo().setDescripcion(auxSueldoTipoSueldo);
            if (lovTiposSueldos != null) {
                for (int i = 0; i < lovTiposSueldos.size(); i++) {
                    if (lovTiposSueldos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaSueldo.setTiposueldo(lovTiposSueldos.get(indiceUnicoElemento));
                lovTiposSueldos.clear();
                getLovTiposSueldos();
                context.update("form:motivoSueldoModSueldo");
                validarTipoTrabajadorTipoSueldo();

            } else {
                permitirIndexSueldo = false;
                getInfoRegistroTipoSueldoSueldo();
                context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
                context.update("form:tipoSueldoModSueldo");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("UNIDAD")) {
            nuevaVigenciaSueldo.getUnidadpago().setNombre(auxSueldoUnidad);
            if (lovUnidades != null) {
                for (int i = 0; i < lovUnidades.size(); i++) {
                    if (lovUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaSueldo.setUnidadpago(lovUnidades.get(indiceUnicoElemento));
                lovUnidades.clear();
                getLovUnidades();
                context.update("form:unidadPagoModSueldo");
            } else {
                permitirIndexSueldo = false;
                getInfoRegistroUnidadSueldo();
                context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
                context.execute("UnidadSueldoDialogo.show()");
                context.update("form:unidadPagoModSueldo");
            }
        }
    }

    public void modificarTipoContrato(int indice, String confirmarCambio, String valorConfirmar) {
        indexTipoContrato = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaTipoContrato.getMotivocontrato().setNombre(auxTipoContratoMotivo);
            if (lovMotivosContratos != null) {
                for (int i = 0; i < lovMotivosContratos.size(); i++) {
                    if (lovMotivosContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }

            if (coincidencias == 1) {
                nuevaVigenciaTipoContrato.setMotivocontrato(lovMotivosContratos.get(indiceUnicoElemento));
                lovMotivosContratos.clear();
                getLovMotivosContratos();
                context.update("form:motivoContratoModTipoContrato");
            } else {
                permitirIndexTipoContrato = false;
                getInfoRegistroMotivoTipoContrato();
                context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
                context.execute("MotivoContratoTipoContratoDialogo.show()");
                context.update("form:motivoContratoModTipoContrato");
            }
        }

        if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            nuevaVigenciaTipoContrato.getTipocontrato().setNombre(auxTipoContratoTipoContrato);
            if (lovTiposContratos != null) {
                for (int i = 0; i < lovTiposContratos.size(); i++) {
                    if (lovTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaTipoContrato.setTipocontrato(lovTiposContratos.get(indiceUnicoElemento));
                lovTiposContratos.clear();
                getLovTiposContratos();
                context.update("form:tipoContratoModTipoContrato");
                validarTipoTrabajadorTipoContrato();
            } else {
                permitirIndexTipoContrato = false;
                getInfoRegistroTipoTipoContrato();
                context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
                context.execute("TipoContratoTipoContratoDialogo.show()");
                context.update("form:tipoContratoModTipoContrato");
            }
        }
    }

    public void modificarNormaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        indexNormaLaboral = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMA")) {
            nuevaVigenciaNormaEmpleado.getNormalaboral().setNombre(auxNormaLaboralNorma);
            if (lovNormasLaborales != null) {
                for (int i = 0; i < lovNormasLaborales.size(); i++) {
                    if (lovNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaNormaEmpleado.setNormalaboral(lovNormasLaborales.get(indiceUnicoElemento));
                lovNormasLaborales.clear();
                getLovNormasLaborales();
                context.update("form:normaLaboralModNormaLaboral");
                validarTipoTrabajadorNormaLaboral();
            } else {
                permitirIndexNormaLaboral = false;
                getInfoRegistroNormaNormaLaboral();
                context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                context.update("form:normaLaboralModNormaLaboral");
            }
        }
    }

    public void modificarLegislacionLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        indexLegislacionLaboral = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONTRATO")) {
            nuevaVigenciaContrato.getContrato().setDescripcion(auxLegislacionLaboralNombre);
            if (lovContratos != null) {
                for (int i = 0; i < lovContratos.size(); i++) {
                    if (lovContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaContrato.setContrato(lovContratos.get(indiceUnicoElemento));
                lovContratos.clear();
                getLovContratos();
                context.update("form:legislacionLaboralModLegislacionLaboral");
                validarTipoTrabajadorContrato();
            } else {
                permitirIndexLegislacionLaboral = false;
                getInfoRegistroContratoLegislacionLaboral();
                context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
                context.execute("ContratoLegislacionLaboralDialogo.show()");
                context.update("form:legislacionLaboralModLegislacionLaboral");
            }
        }
    }

    public void modificarUbicacionGeografica(int indice, String confirmarCambio, String valorConfirmar) {
        indexUbicacionGeografica = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("UBICACION")) {
            nuevaVigenciaUbicacion.getUbicacion().setDescripcion(auxUbicacionGeograficaUbicacion);
            if (lovUbicacionesGeograficas != null) {
                for (int i = 0; i < lovUbicacionesGeograficas.size(); i++) {
                    if (lovUbicacionesGeograficas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaUbicacion.setUbicacion(lovUbicacionesGeograficas.get(indiceUnicoElemento));
                lovUbicacionesGeograficas.clear();
                getLovUbicacionesGeograficas();
                context.update("form:ubicacionGeograficaModUbicacionGeografica");
            } else {
                permitirIndexUbicacionGeografica = false;
                getInfoRegistroUbicacionUbicacion();
                context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
                context.execute("UbicacionUbicacionGeograficaDialogo.show()");
                context.update("form:ubicacionGeograficaModUbicacionGeografica");
            }
        }
    }

    public void modificarJornadaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        indexJornadaLaboral = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("JORNADA")) {
            nuevaVigenciaJornada.getJornadatrabajo().setDescripcion(auxJornadaLaboralJornada);
            if (lovJornadasLaborales != null) {
                for (int i = 0; i < lovJornadasLaborales.size(); i++) {
                    if (lovJornadasLaborales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaJornada.setJornadatrabajo(lovJornadasLaborales.get(indiceUnicoElemento));
                lovJornadasLaborales.clear();
                getLovJornadasLaborales();
                context.update("form:jornadaLaboralModJornadaLaboral");
            } else {
                permitirIndexJornadaLaboral = false;
                getInfoRegistroJornadaJornadaLaboral();
                context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
                context.execute("JornadaJornadaLaboralDialogo.show()");
                context.update("form:jornadaLaboralModJornadaLaboral");
            }
        }
    }

    public void modificarFormaPago(int indice, String confirmarCambio, String valorConfirmar) {
        indexFormaPago = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMA")) {
            nuevaVigenciaFormaPago.getFormapago().setNombre(auxFormaPagoPeriodicidad);
            if (lovPeriodicidades != null) {
                for (int i = 0; i < lovPeriodicidades.size(); i++) {
                    if (lovPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaFormaPago.setFormapago(lovPeriodicidades.get(indiceUnicoElemento));
                lovPeriodicidades.clear();
                getLovPeriodicidades();
                context.update("form:formaPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                getInfoRegistroFormaFormaPago();
                context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                context.update("form:formaPagoModFormaPago");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("SUCURSAL")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaFormaPago.getSucursal().setNombre(auxFormaPagoSucursal);
                if (lovSucursales != null) {
                    for (int i = 0; i < lovSucursales.size(); i++) {
                        if (lovSucursales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaFormaPago.setSucursal(lovSucursales.get(indiceUnicoElemento));
                    lovSucursales.clear();
                    getLovSucursales();
                    context.update("form:sucursalPagoModFormaPago");
                } else {
                    permitirIndexFormaPago = false;
                    getInfoRegistroSucursalFormaPago();
                    context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
                    context.execute("SucursalFormaPagoDialogo.show()");
                    context.update("form:sucursalPagoModFormaPago");
                }
            } else {
                lovSucursales.clear();
                getLovSucursales();
                nuevaVigenciaFormaPago.setSucursal(new Sucursales());
                context.update("form:sucursalPagoModFormaPago");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("METODO")) {
            nuevaVigenciaFormaPago.getMetodopago().setDescripcion(auxFormaPagoMetodo);
            if (lovMetodosPagos != null) {
                for (int i = 0; i < lovMetodosPagos.size(); i++) {
                    if (lovMetodosPagos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaFormaPago.setMetodopago(lovMetodosPagos.get(indiceUnicoElemento));
                lovMetodosPagos.clear();
                getLovMetodosPagos();
                context.update("form:metodoPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                getInfoRegistroMetodoFormaPago();
                context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                context.update("form:metodoPagoModFormaPago");
            }
        }
    }

    public void modificarAfiliacionFondo(int indice, String confirmarCambio, String valorConfirmar) {
        indexAfiliacionFondo = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FONDO")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaAfiliacionFondo.getTercerosucursal().setDescripcion(auxAfiliacionFondo);
                if (lovTercerosSucursales != null) {
                    for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                        if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaAfiliacionFondo.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                    lovTercerosSucursales.clear();
                    getLovTercerosSucursales();
                    TiposEntidades fondo = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("12"));
                    nuevaVigenciaAfiliacionFondo.setTipoentidad(fondo);
                    context.update("form:fondoCensantiasModAfiliaciones");
                } else {
                    permitirIndexAfiliacionFondo = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:fondoCensantiasModAfiliaciones");
                }
            } else {
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                nuevaVigenciaAfiliacionFondo.setTercerosucursal(new TercerosSucursales());
                context.update("form:fondoCensantiasModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionEPS(int indice, String confirmarCambio, String valorConfirmar) {
        indexAfiliacionEPS = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EPS")) {
            nuevaVigenciaAfiliacionEPS.getTercerosucursal().setDescripcion(auxAfiliacionEPS);
            if (lovTercerosSucursales != null) {
                for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                    if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionEPS.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                context.update("form:epsModAfiliaciones");
                TiposEntidades eps = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("1"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(eps);
                consultarCodigoSS();
            } else {
                permitirIndexAfiliacionEPS = false;
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                context.update("form:epsModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionCaja(int indice, String confirmarCambio, String valorConfirmar) {
        indexAfiliacionCaja = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CAJA")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaAfiliacionCaja.getTercerosucursal().setDescripcion(auxAfiliacionCaja);
                if (lovTercerosSucursales != null) {
                    for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                        if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaAfiliacionCaja.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                    lovTercerosSucursales.clear();
                    getLovTercerosSucursales();
                    TiposEntidades caja = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("14"));
                    nuevaVigenciaAfiliacionCaja.setTipoentidad(caja);
                    context.update("form:cajaCompensacionModAfiliaciones");
                } else {
                    permitirIndexAfiliacionCaja = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:cajaCompensacionModAfiliaciones");
                }
            } else {
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                nuevaVigenciaAfiliacionCaja.setTercerosucursal(new TercerosSucursales());
                context.update("form:cajaCompensacionModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionARP(int indice, String confirmarCambio, String valorConfirmar) {
        indexAfiliacionARP = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ARP")) {
            nuevaVigenciaAfiliacionARP.getTercerosucursal().setDescripcion(auxAfiliacionARP);
            if (lovTercerosSucursales != null) {
                for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                    if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionARP.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                TiposEntidades arp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("2"));
                nuevaVigenciaAfiliacionARP.setTipoentidad(arp);
                context.update("form:arpModAfiliaciones");
                consultarCodigoSC();
            } else {
                permitirIndexAfiliacionARP = false;
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                context.update("form:arpModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionAFP(int indice, String confirmarCambio, String valorConfirmar) {
        indexAfiliacionAFP = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("AFP")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaVigenciaAfiliacionAFP.getTercerosucursal().setDescripcion(auxAfiliacionAFP);
                if (lovTercerosSucursales != null) {
                    for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                        if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaVigenciaAfiliacionAFP.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                    lovTercerosSucursales.clear();
                    getLovTercerosSucursales();
                    TiposEntidades afp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("3"));
                    nuevaVigenciaAfiliacionAFP.setTipoentidad(afp);
                    context.update("form:afpModAfiliaciones");
                    consultarCodigoSP();
                } else {
                    permitirIndexAfiliacionAFP = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:afpModAfiliaciones");
                }
            } else {
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                nuevaVigenciaAfiliacionAFP.setTercerosucursal(new TercerosSucursales());
                context.update("form:afpModAfiliaciones");
            }
        }
    }

    public void modificarEstadoCivil(int indice, String confirmarCambio, String valorConfirmar) {
        indexEstadoCivil = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ESTADO")) {
            if (!valorConfirmar.isEmpty()) {
                nuevoEstadoCivil.getEstadocivil().setDescripcion(auxEstadoCivilEstado);
                if (lovEstadosCiviles != null) {
                    for (int i = 0; i < lovEstadosCiviles.size(); i++) {
                        if (lovEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevoEstadoCivil.setEstadocivil(lovEstadosCiviles.get(indiceUnicoElemento));
                    lovEstadosCiviles.clear();
                    getLovEstadosCiviles();
                    context.update("form:estadoCivilModEstadoCivil");
                } else {
                    permitirIndexEstadoCivil = false;
                    getInfoRegistroEstadoCivilEstadoCivil();
                    context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
                    context.execute("EstadoCivilEstadoCivilDialogo.show()");
                    context.update("form:estadoCivilModEstadoCivil");
                }
            } else {
                lovEstadosCiviles.clear();
                getLovEstadosCiviles();
                nuevoEstadoCivil.setEstadocivil(new EstadosCiviles());
                context.update("form:estadoCivilModEstadoCivil");
            }
        }
    }

    public void modificarDireccion(int indice, String confirmarCambio, String valorConfirmar) {
        indexDireccion = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
                nuevaDireccion.getCiudad().setNombre(auxDireccionCiudad);
                if (lovCiudades != null) {
                    for (int i = 0; i < lovCiudades.size(); i++) {
                        if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevaDireccion.setCiudad(lovCiudades.get(indiceUnicoElemento));
                    lovCiudades.clear();
                    getLovCiudades();
                    context.update("form:ciudadModDireccion");
                } else {
                    permitirIndexDireccion = false;
                    getInfoRegistroCiudadDireccion();
                    context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
                    context.execute("CiudadDireccionDialogo.show()");
                    context.update("form:ciudadModDireccion");
                }
            } else {
                nuevaDireccion.setCiudad(new Ciudades());
                context.update("form:ciudadModDireccion");
            }
        }
    }

    public void modificarTelefono(int indice, String confirmarCambio, String valorConfirmar) {
        indexTelefono = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
                nuevoTelefono.getCiudad().setNombre(auxTelefonoCiudad);
                if (lovCiudades != null) {
                    for (int i = 0; i < lovCiudades.size(); i++) {
                        if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevoTelefono.setCiudad(lovCiudades.get(indiceUnicoElemento));
                    lovCiudades.clear();
                    getLovCiudades();
                    context.update("form:ciudadModTelefono");
                } else {
                    permitirIndexTelefono = false;
                    getInfoRegistroCiudadTelefono();
                    context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
                    context.execute("CiudadTelefonoDialogo.show()");
                    context.update("form:ciudadModTelefono");
                }
            } else {
                lovCiudades.clear();
                getLovCiudades();
                nuevoTelefono.setCiudad(new Ciudades());
                context.update("form:ciudadModTelefono");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            if (!valorConfirmar.isEmpty()) {
                nuevoTelefono.getTipotelefono().setNombre(auxTelefonoTipo);
                if (lovTiposTelefonos != null) {
                    for (int i = 0; i < lovTiposTelefonos.size(); i++) {
                        if (lovTiposTelefonos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                }
                if (coincidencias == 1) {
                    nuevoTelefono.setTipotelefono(lovTiposTelefonos.get(indiceUnicoElemento));
                    lovTiposTelefonos.clear();
                    getLovTiposTelefonos();
                    context.update("form:tipoTelefonoModTelefono");
                } else {
                    permitirIndexTelefono = false;
                    getInfoRegistroTipoTelefonoTelefono();
                    context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
                    context.execute("TipoTelefonoTelefonoDialogo.show()");
                    context.update("form:tipoTelefonoModTelefono");
                }
            } else {
                lovTiposTelefonos.clear();
                getLovTiposTelefonos();
                nuevoTelefono.setTipotelefono(new TiposTelefonos());
                context.update("form:tipoTelefonoModTelefono");
            }
        }
    }

    public void actualizarParametroTerceroAfiliacion() {
        if (indexAfiliacionAFP >= 0) {
            actualizarParametroAFPAfiliacion();
        }
        if (indexAfiliacionARP >= 0) {
            actualizarParametroARPAfiliacion();
        }
        if (indexAfiliacionCaja >= 0) {
            actualizarParametroCajaAfiliacion();
        }
        if (indexAfiliacionEPS >= 0) {
            actualizarParametroEPSAfiliacion();
        }
        if (indexAfiliacionFondo >= 0) {
            actualizarParametroFondoAfiliacion();
        }
    }

    public void cancelarParametroTerceroAfiliacion() {
        if (indexAfiliacionAFP >= 0) {
            cancelarParametroAFPAfiliacion();
        }
        if (indexAfiliacionARP >= 0) {
            cancelarParametroARPAfiliacion();
        }
        if (indexAfiliacionCaja >= 0) {
            cancelarParametroCajaAfiliacion();
        }
        if (indexAfiliacionEPS >= 0) {
            cancelarParametroEPSAfiliacion();
        }
        if (indexAfiliacionFondo >= 0) {
            cancelarParametroFondoAfiliacion();
        }
    }

    public void actualizarParametroCiudadTelefono() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoTelefono.setCiudad(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexTelefono = true;
        context.update("form:ciudadModTelefono");
        context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
        context.update("formLovs:formDTelefono:lovCiudadTelefono");
        context.update("formLovs:formDTelefono:aceptarCT");
        context.reset("formLovs:formDTelefono:lovCiudadTelefono:globalFilter");
        context.execute("CiudadTelefonoDialogo.hide()");
    }

    public void cancelarParametroCiudadTelefono() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexTelefono = true;
    }

    public void actualizarParametroTipoTelefonoTelefono() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoTelefono.setTipotelefono(tipoTelefonoSeleccionado);
        tipoTelefonoSeleccionado = new TiposTelefonos();
        filtrarLovTiposTelefonos = null;
        aceptar = true;
        permitirIndexTelefono = true;
        context.update("form:tipoTelefonoModTelefono");
        context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
        context.update("formLovs:formDTelefono:lovTipoTelefonoTelefono");
        context.update("formLovs:formDTelefono:aceptarTTT");
        context.reset("formLovs:formDTelefono:lovTipoTelefonoTelefono:globalFilter");
        context.execute("TipoTelefonoTelefonoDialogo.hide()");
    }

    public void cancelarParametroTipoTelefonoTelefono() {
        tipoTelefonoSeleccionado = new TiposTelefonos();
        filtrarLovTiposTelefonos = null;
        aceptar = true;
        permitirIndexTelefono = true;
    }

    public void actualizarParametroCiudadDireccion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaDireccion.setCiudad(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexDireccion = true;
        context.update("form:ciudadModDireccion");
        context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
        context.update("formLovs:formDDireccion:lovCiudadDireccion");
        context.update("formLovs:formDDireccion:aceptarCD");
        context.reset("formLovs:formDDireccion:lovCiudadDireccion:globalFilter");
        context.execute("CiudadDireccionDialogo.hide()");
    }

    public void cancelarParametroCiudadDireccion() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexDireccion = true;
    }

    public void actualizarParametroEstadoEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEstadoCivil.setEstadocivil(estadoCivilSeleccionado);
        estadoCivilSeleccionado = new EstadosCiviles();
        filtrarLovEstadosCiviles = null;
        aceptar = true;
        permitirIndexEstadoCivil = true;
        context.update("form:estadoCivilModEstadoCivil");
        context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
        context.update("formLovs:formDEstadoCivil:lovEstadoCivilEstadoCivil");
        context.update("formLovs:formDEstadoCivil:aceptarECEC");
        context.reset("formLovs:formDEstadoCivil:lovEstadoCivilEstadoCivil:globalFilter");
        context.execute("EstadoCivilEstadoCivilDialogo.hide()");
    }

    public void cancelarParametroEstadoEstadoCivil() {
        estadoCivilSeleccionado = new EstadosCiviles();
        filtrarLovEstadosCiviles = null;
        aceptar = true;
        permitirIndexEstadoCivil = true;
    }

    public void actualizarParametroEPSAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaAfiliacionEPS.setTercerosucursal(terceroSucursalSeleccionado);
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        TiposEntidades eps = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("1"));
        nuevaVigenciaAfiliacionEPS.setTipoentidad(eps);
        permitirIndexAfiliacionEPS = true;
        context.update("form:epsModAfiliaciones");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");
        context.execute("TerceroAfiliacionDialogo.hide()");
        consultarCodigoSS();
    }

    public void cancelarParametroEPSAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionEPS = true;
    }

    public void actualizarParametroCajaAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaAfiliacionCaja.setTercerosucursal(terceroSucursalSeleccionado);
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        TiposEntidades caja = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("14"));
        nuevaVigenciaAfiliacionCaja.setTipoentidad(caja);
        permitirIndexAfiliacionCaja = true;
        context.update("form:cajaCompensacionModAfiliaciones");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");
        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroCajaAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionCaja = true;
    }

    public void actualizarParametroARPAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaAfiliacionARP.setTercerosucursal(terceroSucursalSeleccionado);
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        TiposEntidades arp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("2"));
        nuevaVigenciaAfiliacionARP.setTipoentidad(arp);
        permitirIndexAfiliacionARP = true;
        context.update("form:arpModAfiliaciones");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");
        context.execute("TerceroAfiliacionDialogo.hide()");
        consultarCodigoSC();
    }

    public void cancelarParametroARPAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionARP = true;
    }

    public void actualizarParametroAFPAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaAfiliacionAFP.setTercerosucursal(terceroSucursalSeleccionado);
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        TiposEntidades afp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("3"));
        nuevaVigenciaAfiliacionAFP.setTipoentidad(afp);
        permitirIndexAfiliacionAFP = true;
        context.update("form:afpModAfiliaciones");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");
        context.update("formLovs:formDAfiliacion:aceptarTSA");
        context.execute("TerceroAfiliacionDialogo.hide()");
        consultarCodigoSP();
    }

    public void cancelarParametroAFPAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionAFP = true;
    }

    public void actualizarParametroFondoAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaAfiliacionFondo.setTercerosucursal(terceroSucursalSeleccionado);
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        TiposEntidades fondo = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("12"));
        nuevaVigenciaAfiliacionFondo.setTipoentidad(fondo);
        permitirIndexAfiliacionFondo = true;
        context.update("form:fondoCensantiasModAfiliaciones");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");
        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroFondoAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionFondo = true;
    }

    public void actualizarParametroJornadaJornadaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaJornada.setJornadatrabajo(jornadaLaboralSeleccionada);
        jornadaLaboralSeleccionada = new JornadasLaborales();
        filtrarLovJornadasLaborales = null;
        aceptar = true;
        permitirIndexJornadaLaboral = true;
        context.update("form:jornadaLaboralModJornadaLaboral");
        context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
        context.update("formLovs:formDJornadaLaboral:lovJornadaJornadaLaboral");
        context.update("formLovs:formDJornadaLaboral:aceptarJLJL");
        context.reset("formLovs:formDJornadaLaboral:lovJornadaJornadaLaboral:globalFilter");
        context.execute("JornadaJornadaLaboralDialogo.hide()");
    }

    public void cancelarParametroJornadaJornadaLaboral() {
        jornadaLaboralSeleccionada = new JornadasLaborales();
        filtrarLovJornadasLaborales = null;
        aceptar = true;
        permitirIndexJornadaLaboral = true;
    }

    public void actualizarParametroMetodoFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setMetodopago(metodoPagoSeleccionado);
        metodoPagoSeleccionado = new MetodosPagos();
        filtrarLovMetodosPagos = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:metodoPagoModFormaPago");
        context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovMetodoPagoFormaPago");
        context.update("formLovs:formDFormaPago:aceptarMPFP");
        context.reset("formLovs:formDFormaPago:lovMetodoPagoFormaPago:globalFilter");
        context.execute("MetodoPagoFormaPagoDialogo.hide()");
    }

    public void cancelarParametroMetodoFormaPago() {
        metodoPagoSeleccionado = new MetodosPagos();
        filtrarLovMetodosPagos = null;
        aceptar = true;
        permitirIndexFormaPago = true;
    }

    public void actualizarParametroSucursalFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setSucursal(sucursalSeleccionada);
        sucursalSeleccionada = new Sucursales();
        filtrarLovSucursales = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:sucursalPagoModFormaPago");
        context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovSucursalPagoFormaPago");
        context.update("formLovs:formDFormaPago:aceptarSFP");
        context.reset("formLovs:formDFormaPago:lovSucursalPagoFormaPago:globalFilter");
        context.execute("SucursalFormaPagoDialogo.hide()");
    }

    public void cancelarParametroSucursalFormaPago() {
        sucursalSeleccionada = new Sucursales();
        filtrarLovSucursales = null;
        aceptar = true;
        permitirIndexFormaPago = true;
    }

    public void actualizarParametroPeriodicidadFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setFormapago(periodicidadSeleccionada);
        periodicidadSeleccionada = new Periodicidades();
        filtrarLovPeriodicidades = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:formaPagoModFormaPago");
        context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovPeriodicidadFormaPago");
        context.update("formLovs:formDFormaPago:aceptarPFP");
        context.reset("formLovs:formDFormaPago:lovPeriodicidadFormaPago:globalFilter");
        context.execute("PeriodicidadFormaPagoDialogo.hide()");
    }

    public void cancelarParametroPeriodicidadFormaPago() {
        periodicidadSeleccionada = new Periodicidades();
        filtrarLovPeriodicidades = null;
        aceptar = true;
        permitirIndexFormaPago = true;
    }

    public void actualizarParametroUbicacionUbicacionGeografica() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaUbicacion.setUbicacion(ubicacionGeograficaSeleccionada);
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        filtrarLovUbicacionesGeograficas = null;
        aceptar = true;
        permitirIndexUbicacionGeografica = true;
        context.update("form:ubicacionGeograficaModUbicacionGeografica");
        context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
        context.update("formLovs:formDUbicacion:lovUbicacionUbicacionGeografica");
        context.update("formLovs:formDUbicacion:aceptarUGUG");
        context.reset("formLovs:formDUbicacion:lovUbicacionUbicacionGeografica:globalFilter");
        context.execute("UbicacionUbicacionGeograficaDialogo.hide()");
    }

    public void cancelarParametroUbicacionUbicacionGeografica() {
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        filtrarLovUbicacionesGeograficas = null;
        aceptar = true;
        permitirIndexUbicacionGeografica = true;
    }

    public void actualizarParametroContratoLegislacionLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaContrato.setContrato(contratoSeleccionado);
        contratoSeleccionado = new Contratos();
        filtrarLovContratos = null;
        aceptar = true;
        permitirIndexLegislacionLaboral = true;
        context.update("form:legislacionLaboralModLegislacionLaboral");
        context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
        context.update("formLovs:formDLegislacionLaboral:lovContratoLegislacionLaboral");
        context.update("formLovs:formDLegislacionLaboral:aceptarCLL");
        context.reset("formLovs:formDLegislacionLaboral:lovContratoLegislacionLaboral:globalFilter");
        context.execute("ContratoLegislacionLaboralDialogo.hide()");
        validarTipoTrabajadorContrato();
    }

    public void cancelarParametroContratoLegislacionLaboral() {
        contratoSeleccionado = new Contratos();
        filtrarLovContratos = null;
        aceptar = true;
        permitirIndexLegislacionLaboral = true;
    }

    public void actualizarParametroNormaNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaNormaEmpleado.setNormalaboral(normaLaboralSeleccionada);
        normaLaboralSeleccionada = new NormasLaborales();
        filtrarLovNormasLaborales = null;
        aceptar = true;
        permitirIndexNormaLaboral = true;
        context.update("form:normaLaboralModNormaLaboral");
        context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
        context.update("formLovs:formDNormaLaboral:lovNormaLaboralNormaLaboral");
        context.update("formLovs:formDNormaLaboral:aceptarNLNL");
        context.reset("formLovs:formDNormaLaboral:lovNormaLaboralNormaLaboral:globalFilter");
        context.execute("NormaLaboralNormaLaboralDialogo.hide()");
        validarTipoTrabajadorNormaLaboral();
    }

    public void cancelarParametroNormaNormaLaboral() {
        normaLaboralSeleccionada = new NormasLaborales();
        filtrarLovNormasLaborales = null;
        aceptar = true;
        permitirIndexNormaLaboral = true;
    }

    public void actualizarParametroMotivoContratoTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoContrato.setMotivocontrato(motivoContratoSeleccionado);
        motivoContratoSeleccionado = new MotivosContratos();
        filtrarLovMotivosContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        context.update("form:motivoContratoModTipoContrato");
        context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
        context.update("formLovs:formDTipoContrato:lovMotivoContratoTipoContrato");
        context.update("formLovs:formDTipoContrato:aceptarMCTC");
        context.reset("formLovs:formDTipoContrato:lovMotivoContratoTipoContrato:globalFilter");
        context.execute("MotivoContratoTipoContratoDialogo.hide()");
    }

    public void cancelarParametroMotivoContratoTipoContrato() {
        motivoContratoSeleccionado = new MotivosContratos();
        filtrarLovMotivosContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
    }

    public void actualizarParametroTipoContratoTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoContrato.setTipocontrato(tipoContratoSeleccionado);
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        context.update("form:tipoContratoModTipoContrato");
        context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
        context.update("formLovs:formDTipoContrato:lovTipoContratoTipoContrato");
        context.update("formLovs:formDTipoContrato:aceptarTCTC");
        context.reset("formLovs:formDTipoContrato:lovTipoContratoTipoContrato:globalFilter");
        context.execute("TipoContratoTipoContratoDialogo.hide()");
        validarTipoTrabajadorTipoContrato();
    }

    public void cancelarParametroTipoContratoTipoContrato() {
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
    }

    public void actualizarParametroMotivoCambioSueldoSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
        motivoCambioSueldoSeleccionado = new MotivosCambiosSueldos();
        filtrarLovMotivosCambiosSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:motivoSueldoModSueldo");
        context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
        context.update("formLovs:formDSueldo:lovMotivoCambioSueldoSueldo");
        context.update("formLovs:formDSueldo:aceptarMCSS");
        context.reset("formLovs:formDSueldo:lovMotivoCambioSueldoSueldo:globalFilter");
        context.execute("MotivoCambioSueldoSueldoDialogo.hide()");
    }

    public void cancelarParametroMotivoCambioSueldoSueldo() {
        motivoCambioSueldoSeleccionado = new MotivosCambiosSueldos();
        filtrarLovMotivosCambiosSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
    }

    public void actualizarParametroTipoSueldoSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setTiposueldo(tipoSueldoSeleccionado);
        tipoSueldoSeleccionado = new TiposSueldos();
        filtrarLovTiposSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:tipoSueldoModSueldo");
        context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
        context.update("formLovs:formDSueldo:lovTipoSueldoSueldo");
        context.update("formLovs:formDSueldo:aceptarTSS");
        context.reset("formLovs:formDSueldo:lovTipoSueldoSueldo:globalFilter");
        context.execute("TipoSueldoSueldoDialogo.hide()");
        validarTipoTrabajadorTipoSueldo();
    }

    public void cancelarParametroTipoSueldoSueldo() {
        tipoSueldoSeleccionado = new TiposSueldos();
        filtrarLovTiposSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
    }

    public void actualizarParametroUnidadSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setUnidadpago(unidadSeleccionada);
        unidadSeleccionada = new Unidades();
        filtrarLovUnidades = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:unidadPagoModSueldo");
        context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
        context.update("formLovs:formDSueldo:lovUnidadSueldo");
        context.update("formLovs:formDSueldo:aceptarUS");
        context.reset("formLovs:formDSueldo:lovUnidadSueldo:globalFilter");
        context.execute("UnidadSueldoDialogo.hide()");
    }

    public void cancelarParametroUnidadSueldo() {
        unidadSeleccionada = new Unidades();
        filtrarLovUnidades = null;
        aceptar = true;
        permitirIndexSueldo = true;
    }

    public void actualizarParametroReformaLaboralTipoSalario() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaReformaLaboral.setReformalaboral(reformaLaboralSeleccionada);
        reformaLaboralSeleccionada = new ReformasLaborales();
        filtrarLovReformasLaborales = null;
        aceptar = true;
        permitirIndexTipoSalario = true;
        context.update("form:tipoSalarioModTipoSalario");
        context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
        context.update("formLovs:formDTipoSalario:lovReformaLaboralTipoSalario");
        context.update("formLovs:formDTipoSalario:aceptarRLTS");
        context.reset("formLovs:formDTipoSalario:lovReformaLaboralTipoSalario:globalFilter");
        context.execute("ReformaLaboralTipoSalarioDialogo.hide()");
        validarTipoTrabajadorReformaLaboral();
    }

    public void cancelarParametroReformaLaboralTipoSalario() {
        reformaLaboralSeleccionada = new ReformasLaborales();
        filtrarLovReformasLaborales = null;
        aceptar = true;
        permitirIndexTipoSalario = true;
    }

    public void actualizarParametroTipoTrabajadorTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoTrabajador.setTipotrabajador(tipoTrabajadorSeleccionado);
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        filtrarLovTiposTrabajadores = null;
        aceptar = true;
        permitirIndexTipoTrabajador = true;
        context.update("form:tipoTrabajadorModTipoTrabajador");
        context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
        context.update("formLovs:formDTipoTrabajador:lovTipoTrabajadorTipoTrabajador");
        context.update("formLovs:formDTipoTrabajador:aceptarTTTT");
        context.reset("formLovs:formDTipoTrabajador:lovTipoTrabajadorTipoTrabajador:globalFilter");
        context.execute("TipoTrabajadorTipoTrabajadorDialogo.hide()");
        validarDisableTipoTrabajador();
    }

    public void cancelarParametroTipoTrabajadorTipoTrabajador() {
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        filtrarLovTiposTrabajadores = null;
        aceptar = true;
        permitirIndexTipoTrabajador = true;
    }

    public void actualizarParametroEstructuraCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaLocalizacion.setLocalizacion(estructuraCentroCostoSeleccionada);
        estructuraCentroCostoSeleccionada = new Estructuras();
        filtrarLovEstructurasCentroCosto = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        context.update("form:estructuraModCentroCosto");
        context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
        context.update("formLovs:formDCentroCosto:lovEstructuraCentroCosto");
        context.update("formLovs:formDCentroCosto:aceptarECC");
        context.reset("formLovs:formDCentroCosto:lovEstructuraCentroCosto:globalFilter");
        context.execute("EstructuraCentroCostoDialogo.hide()");
    }

    public void cancelarParametroEstructuraCentroCosto() {
        estructuraCentroCostoSeleccionada = new Estructuras();
        filtrarLovEstructurasCentroCosto = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
    }

    public void actualizarParametroMotivoCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaLocalizacion.setMotivo(motivoLocalizacionSeleccionado);
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        filtrarLovMotivosLocalizaciones = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        context.update("form:motivoModCentroCosto");
        context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
        context.update("formLovs:formDCentroCosto:lovMotivoLocalizacionCentroCosto");
        context.update("formLovs:formDCentroCosto:aceptarMLCC");
        context.reset("formLovs:formDCentroCosto:lovMotivoLocalizacionCentroCosto:globalFilter");
        context.execute("MotivoLocalizacionCentroCostoDialogo.hide()");
    }

    public void cancelarParametroMotivoCentroCosto() {
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        filtrarLovMotivosLocalizaciones = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
    }

    public void actualizarParametroCargoCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setCargo(cargoSeleccionado);
        cargoSeleccionado = new Cargos();
        filtrarLovCargos = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:cargoModCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovCargoCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarCCD");
        context.reset("formLovs:formDCargoDesempenado:lovCargoCargoDesempeñado:globalFilter");
        context.execute("CargoCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroCargoCargoDesempeñado() {
        cargoSeleccionado = new Cargos();
        filtrarLovCargos = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
    }

    public void actualizarParametroMotivoCambioCargoCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setMotivocambiocargo(motivoCambioCargoSeleccionado);
        motivoCambioCargoSeleccionado = new MotivosCambiosCargos();
        filtrarLovMotivosCambiosCargos = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:motivoModCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovMotivoCambioCargoCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarMCCCD");
        context.reset("formLovs:formDCargoDesempenado:lovMotivoCambioCargoCargoDesempeñado:globalFilter");
        context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroMotivoCambioCargoCargoDesempeñado() {
        motivoCambioCargoSeleccionado = new MotivosCambiosCargos();
        filtrarLovMotivosCambiosCargos = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
    }

    public void actualizarParametroEstructuraCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setEstructura(estructuraSeleccionada);
        estructuraSeleccionada = new Estructuras();
        filtrarLovEstructuras = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:estructuraModCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovEstructuraCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarECD");
        context.reset("formLovs:formDCargoDesempenado:lovEstructuraCargoDesempeñado:globalFilter");
        context.execute("EstructuraCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroEstructuraCargoDesempeñado() {
        estructuraSeleccionada = new Estructuras();
        filtrarLovEstructuras = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
    }

    public void actualizarParametroPapelCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setPapel(papelSeleccionado);
        papelSeleccionado = new Papeles();
        filtrarLovPapeles = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:papelModCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovPapelCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarPCD");
        context.reset("formLovs:formDCargoDesempenado:lovPapelCargoDesempeñado:globalFilter");
        context.execute("PapelCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroPapelCargoDesempeñado() {
        papelSeleccionado = new Papeles();
        filtrarLovPapeles = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
    }

    public void actualizarParametroJefeCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setEmpleadojefe(empleadoSeleccionado);
        empleadoSeleccionado = new Empleados();
        filtrarLovEmpleados = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:empleadoJefeModCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovEmpleadoJefeCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarEJCD");
        context.reset("formLovs:formDCargoDesempenado:lovEmpleadoJefeCargoDesempeñado:globalFilter");
        context.execute("EmpleadoJefeCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroJefeCargoDesempeñado() {
        empleadoSeleccionado = new Empleados();
        filtrarLovEmpleados = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
    }

    public void actualizarParametroEmpresaInformacionPersonalVisible() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEmpleado.setEmpresa(empresaSeleccionada);
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:empresaModPersonal");
        context.update("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible");
        context.update("formLovs:formDInformacionPersonal:aceptarEIPV");
        context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible:globalFilter");
        context.execute("EmpresaInformacionPersonalDialogoVisible.hide()");
        calcularControlEmpleadosEmpresa();
        modificacionesEmpresaFechaIngresoInformacionPersonal();
    }

    public void cancelarParametroEmpresaInformacionPersonalVisible() {
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
    }

    public void actualizarParametroEmpresaInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEmpleado.setEmpresa(empresaSeleccionada);
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:empresaModPersonal");
        context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonal");
        context.update("formLovs:formDInformacionPersonal:aceptarEIP");
        context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonal:globalFilter");
        context.execute("EmpresaInformacionPersonalDialogo.hide()");
        calcularControlEmpleadosEmpresa();
        modificacionesEmpresaFechaIngresoInformacionPersonal();
    }

    public void cancelarParametroEmpresaInformacionPersonal() {
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
    }

    public void actualizarParametroTipoDocumentoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setTipodocumento(tipoDocumentoSeleccionado);
        tipoDocumentoSeleccionado = new TiposDocumentos();
        filtrarLovTiposDocumentos = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:tipoDocumentoModPersonal");
        context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovTipoDocumentoInformacionPersonal");
        context.update("formLovs:formDInformacionPersonal:aceptarTDIP");
        context.reset("formLovs:formDInformacionPersonal:lovTipoDocumentoInformacionPersonal:globalFilter");
        context.execute("TipoDocumentoInformacionPersonalDialogo.hide()");
    }

    public void cancelarParametroTipoDocumentoInformacionPersonal() {
        tipoDocumentoSeleccionado = new TiposDocumentos();
        filtrarLovTiposDocumentos = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
    }

    public void actualizarParametroCiudadDocumentoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setCiudaddocumento(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:ciudadDocumentoModPersonal");
        context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovCiudadDocumentoInformacionPersonal");
        context.update("formLovs:formDInformacionPersonal:aceptarCDIP");
        context.reset("formLovs:formDInformacionPersonal:lovCiudadDocumentoInformacionPersonal:globalFilter");
        context.execute("CiudadDocumentoInformacionPersonalDialogo.hide()");
    }

    public void cancelarParametroCiudadDocumentoInformacionPersonal() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
    }

    public void actualizarParametroCiudadNacimientoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setCiudadnacimiento(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:ciudadNacimientoModPersonal");
        context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovCiudadNacimientoInformacionPersonal");
        context.update("formLovs:formDInformacionPersonal:aceptarCNIP");
        context.reset("formLovs:formDInformacionPersonal:lovCiudadNacimientoInformacionPersonal:globalFilter");
        context.execute("CiudadNacimientoInformacionPersonalDialogo.hide()");
    }

    public void cancelarParametroCiudadNacimientoInformacionPersonal() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
    }

    public void calcularControlEmpleadosEmpresa() {
        BigInteger empleadosActuales = administrarPersonaIndividual.calcularNumeroEmpleadosEmpresa(nuevoEmpleado.getEmpresa().getSecuencia());
        BigInteger maximoEmpleados = administrarPersonaIndividual.obtenerMaximoEmpleadosEmpresa(nuevoEmpleado.getEmpresa().getSecuencia());
        if (empleadosActuales != null && maximoEmpleados != null) {
            if (empleadosActuales.intValue() >= maximoEmpleados.intValue()) {
                nuevoEmpleado.setEmpresa(new Empresas());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:empresaModPersonal");
                context.execute("errorTopeEmpleadosEmpresa.show()");
            }
        }
    }

    public void modificacionesEmpresaFechaIngresoInformacionPersonal() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            disableDescripcionEstructura = false;
            disableUbicacionGeografica = false;
            disableAfiliaciones = false;
            if (fechaIngreso != null) {
                disableNombreEstructuraCargo = false;
            } else {
                disableNombreEstructuraCargo = true;
            }
        } else {
            disableNombreEstructuraCargo = true;
            disableUbicacionGeografica = true;
            disableAfiliaciones = true;
            disableNombreEstructuraCargo = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:epsModAfiliaciones");
        context.update("form:arpModAfiliaciones");
        context.update("form:fondoCensantiasModAfiliaciones");
        context.update("form:afpModAfiliaciones");
        context.update("form:cajaCompensacionModAfiliaciones");
        context.update("form:estructuraModCargoDesempeñado");
        context.update("form:estructuraModCentroCosto");
        context.update("form:ubicacionGeograficaModUbicacionGeografica");
    }

    public void validarDisableTipoTrabajador() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            disableCamposDependientesTipoTrabajador = false;
        } else {
            disableCamposDependientesTipoTrabajador = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:legislacionLaboralModLegislacionLaboral");
        context.update("form:normaLaboralModNormaLaboral");
        context.update("form:tipoContratoModTipoContrato");
        context.update("form:tipoSueldoModSueldo");
        context.update("form:tipoSalarioModTipoSalario");
    }

    public boolean isNumber(String numero) {
        try {
            double num = Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isNumberTelefono(String numero) {
        try {
            long num = Long.parseLong(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void modificacionValorSueldo(String sueldo) {
        if (sueldo != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (isNumber(sueldo) == true) {
                double numero = Double.parseDouble(sueldo);
                if (numero < 0) {
                    valorSueldo = auxSueldoValor;
                    context.update("form:valorSueldoModSueldo");
                    context.execute("errorValorSueldo.show()");
                }
            } else {
                valorSueldo = auxSueldoValor;
                context.update("form:valorSueldoModSueldo");
                context.execute("errorFormatoValorSueldo.show()");
            }
        }
    }

    public void consultarCodigoSS() {
        String codigo = administrarPersonaIndividual.buscarCodigoSSTercero(nuevaVigenciaAfiliacionEPS.getTercerosucursal().getTercero().getSecuencia());
        if (codigo == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("advertenciaEPS.show()");
        }
    }

    public void consultarCodigoSP() {
        String codigo = administrarPersonaIndividual.buscarCodigoSPTercero(nuevaVigenciaAfiliacionAFP.getTercerosucursal().getTercero().getSecuencia());
        if (codigo == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("advertenciaAFP.show()");
        }
    }

    public void consultarCodigoSC() {
        String codigo = administrarPersonaIndividual.buscarCodigoSCTercero(nuevaVigenciaAfiliacionARP.getTercerosucursal().getTercero().getSecuencia());
        if (codigo == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("advertenciaARP.show()");
        }
    }

    public void modificarNumeroTelefonico(String numero) {
        boolean number = isNumberTelefono(numero);
        RequestContext context = RequestContext.getCurrentInstance();
        if (number == true) {
            long telefono = Long.parseLong(numero);
            if (telefono < 0) {
                nuevoTelefono.setNumerotelefono(0);
                context.update("form:numeroTelefonoModTelefono");
                context.execute("errorNumeroTelefonico.show()");
            }
        } else {
            nuevoTelefono.setNumerotelefono(0);
            context.update("form:numeroTelefonoModTelefono");
            context.execute("errorNumeroTelefonico.show()");
        }
    }

    public void validarTipoTrabajadorReformaLaboral() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorReformaLaboral(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia());
            if (validar != null) {
                if (validar.equalsIgnoreCase("N")) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("aletarTiposTrabajadoresRL.show()");
                } 
            }
        }
    }

    public void validarTipoTrabajadorTipoSueldo() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorTipoSueldo(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaSueldo.getTiposueldo().getSecuencia());
            if (validar.equalsIgnoreCase("N")) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("aletarTiposTrabajadoresTS.show()");
            }
        }
    }

    public void validarTipoTrabajadorTipoContrato() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorTipoContrato(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia());
            if (validar.equalsIgnoreCase("N")) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("aletarTiposTrabajadoresTC.show()");
            }
        }
    }

    public void validarTipoTrabajadorNormaLaboral() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorNormaLaboral(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia());
            if (validar.equalsIgnoreCase("N")) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("aletarTiposTrabajadoresNL.show()");
            }
        }
    }

    public void validarTipoTrabajadorContrato() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorContrato(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaContrato.getContrato().getSecuencia());
            if (validar.equalsIgnoreCase("N")) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("aletarTiposTrabajadoresC.show()");
            }
        }
    }

    public void validarEmailPersona(String email) {
        boolean esEmail = isEmail(email);
        if (esEmail == false) {
            nuevaPersona.setEmail(null);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:correoModPersonal");
            context.execute("errorEmailPersona.show()");
        }
    }

    public boolean isEmail(String email) {
        // Compiles the given regular expression into a pattern.
        Pattern pattern = Pattern.compile(PATTERN_EMAIL);
        // Match the given input against this pattern
        Matcher matcher = pattern.matcher(email);
        boolean valor = matcher.matches();
        return valor;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public Personas getNuevaPersona() {
        return nuevaPersona;
    }

    public void setNuevaPersona(Personas nuevoEmpleado) {
        this.nuevaPersona = nuevoEmpleado;
    }

    public List<Empresas> getLovEmpresas() {
        lovEmpresas = administrarPersonaIndividual.lovEmpresas();
        return lovEmpresas;
    }

    public void setLovEmpresas(List<Empresas> lovEmpresas) {
        this.lovEmpresas = lovEmpresas;
    }

    public List<Empresas> getFiltrarLovEmpresas() {
        return filtrarLovEmpresas;
    }

    public void setFiltrarLovEmpresas(List<Empresas> filtrarLovEmpresas) {
        this.filtrarLovEmpresas = filtrarLovEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public List<TiposDocumentos> getLovTiposDocumentos() {
        lovTiposDocumentos = administrarPersonaIndividual.lovTiposDocumentos();
        return lovTiposDocumentos;
    }

    public void setLovTiposDocumentos(List<TiposDocumentos> lovTiposDocumentos) {
        this.lovTiposDocumentos = lovTiposDocumentos;
    }

    public List<TiposDocumentos> getFiltrarLovTiposDocumentos() {
        return filtrarLovTiposDocumentos;
    }

    public void setFiltrarLovTiposDocumentos(List<TiposDocumentos> filtrarLovTiposDocumentos) {
        this.filtrarLovTiposDocumentos = filtrarLovTiposDocumentos;
    }

    public TiposDocumentos getTipoDocumentoSeleccionado() {
        return tipoDocumentoSeleccionado;
    }

    public void setTipoDocumentoSeleccionado(TiposDocumentos tipoDocumentoSeleccionado) {
        this.tipoDocumentoSeleccionado = tipoDocumentoSeleccionado;
    }

    public List<Ciudades> getLovCiudades() {
        lovCiudades = administrarPersonaIndividual.lovCiudades();
        return lovCiudades;
    }

    public void setLovCiudades(List<Ciudades> lovCiudades) {
        this.lovCiudades = lovCiudades;
    }

    public List<Ciudades> getFiltrarLovCiudades() {
        return filtrarLovCiudades;
    }

    public void setFiltrarLovCiudades(List<Ciudades> filtrarLovCiudades) {
        this.filtrarLovCiudades = filtrarLovCiudades;
    }

    public Ciudades getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudades ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        this.fechaCorte = fechaCorte;
    }

    public ParametrosEstructuras getParametrosEstructuras() {
        if (parametrosEstructuras == null) {
            parametrosEstructuras = administrarCarpetaPersonal.consultarParametrosUsuario();
        }
        return parametrosEstructuras;
    }

    public void setParametrosEstructuras(ParametrosEstructuras parametrosEstructuras) {
        this.parametrosEstructuras = parametrosEstructuras;
    }

    public Date getFechaDesdeParametro() {
        getParametrosEstructuras();
        fechaDesdeParametro = parametrosEstructuras.getFechadesdecausado();
        return fechaDesdeParametro;
    }

    public void setFechaDesdeParametro(Date fechaDesdeParametro) {
        this.fechaDesdeParametro = fechaDesdeParametro;
    }

    public Date getFechaHastaParametro() {
        getParametrosEstructuras();
        fechaHastaParametro = parametrosEstructuras.getFechahastacausado();
        return fechaHastaParametro;
    }

    public void setFechaHastaParametro(Date fechaHastaParametro) {
        this.fechaHastaParametro = fechaHastaParametro;
    }

    public Empleados getNuevoEmpleado() {
        return nuevoEmpleado;
    }

    public void setNuevoEmpleado(Empleados nuevoEmpleado) {
        this.nuevoEmpleado = nuevoEmpleado;
    }

    public VigenciasCargos getNuevaVigenciaCargo() {
        return nuevaVigenciaCargo;
    }

    public void setNuevaVigenciaCargo(VigenciasCargos nuevaVigenciaCargo) {
        this.nuevaVigenciaCargo = nuevaVigenciaCargo;
    }

    public List<Cargos> getLovCargos() {
        lovCargos = administrarPersonaIndividual.lovCargos();
        return lovCargos;
    }

    public void setLovCargos(List<Cargos> lovCargos) {
        this.lovCargos = lovCargos;
    }

    public List<Cargos> getFiltrarLovCargos() {
        return filtrarLovCargos;
    }

    public void setFiltrarLovCargos(List<Cargos> filtrarLovCargos) {
        this.filtrarLovCargos = filtrarLovCargos;
    }

    public List<MotivosCambiosCargos> getLovMotivosCambiosCargos() {
        lovMotivosCambiosCargos = administrarPersonaIndividual.lovMotivosCambiosCargos();
        return lovMotivosCambiosCargos;
    }

    public void setLovMotivosCambiosCargos(List<MotivosCambiosCargos> lovMotivosCambiosCargos) {
        this.lovMotivosCambiosCargos = lovMotivosCambiosCargos;
    }

    public List<MotivosCambiosCargos> getFiltrarLovMotivosCambiosCargos() {
        return filtrarLovMotivosCambiosCargos;
    }

    public void setFiltrarLovMotivosCambiosCargos(List<MotivosCambiosCargos> filtrarLovMotivosCambiosCargos) {
        this.filtrarLovMotivosCambiosCargos = filtrarLovMotivosCambiosCargos;
    }

    public MotivosCambiosCargos getMotivoCambioCargoSeleccionado() {
        return motivoCambioCargoSeleccionado;
    }

    public void setMotivoCambioCargoSeleccionado(MotivosCambiosCargos motivoCambioCargoSeleccionado) {
        this.motivoCambioCargoSeleccionado = motivoCambioCargoSeleccionado;
    }

    public List<Estructuras> getLovEstructuras() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null && fechaIngreso != null) {
            lovEstructuras = administrarPersonaIndividual.lovEstructurasModCargos(nuevoEmpleado.getEmpresa().getSecuencia(), fechaIngreso);
        }
        return lovEstructuras;
    }

    public void setLovEstructuras(List<Estructuras> lovEstructuras) {
        this.lovEstructuras = lovEstructuras;
    }

    public List<Estructuras> getFiltrarLovEstructuras() {
        return filtrarLovEstructuras;
    }

    public void setFiltrarLovEstructuras(List<Estructuras> filtrarLovEstructuras) {
        this.filtrarLovEstructuras = filtrarLovEstructuras;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public List<Papeles> getLovPapeles() {
        lovPapeles = administrarPersonaIndividual.lovPapeles();
        return lovPapeles;
    }

    public void setLovPapeles(List<Papeles> lovPapeles) {
        this.lovPapeles = lovPapeles;
    }

    public List<Papeles> getFiltrarLovPapeles() {
        return filtrarLovPapeles;
    }

    public void setFiltrarLovPapeles(List<Papeles> filtrarLovPapeles) {
        this.filtrarLovPapeles = filtrarLovPapeles;
    }

    public Papeles getPapelSeleccionado() {
        return papelSeleccionado;
    }

    public void setPapelSeleccionado(Papeles papelSeleccionado) {
        this.papelSeleccionado = papelSeleccionado;
    }

    public Cargos getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargos cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }

    public boolean isDisableNombreEstructuraCargo() {
        return disableNombreEstructuraCargo;
    }

    public void setDisableNombreEstructuraCargo(boolean disableNombreEstructuraCargo) {
        this.disableNombreEstructuraCargo = disableNombreEstructuraCargo;
    }

    public VigenciasLocalizaciones getNuevaVigenciaLocalizacion() {
        return nuevaVigenciaLocalizacion;
    }

    public void setNuevaVigenciaLocalizacion(VigenciasLocalizaciones nuevaVigenciaLocalizacion) {
        this.nuevaVigenciaLocalizacion = nuevaVigenciaLocalizacion;
    }

    public List<Empleados> getLovEmpleados() {
        lovEmpleados = administrarPersonaIndividual.lovEmpleados();
        return lovEmpleados;
    }

    public void setLovEmpleados(List<Empleados> lovEmpleados) {
        this.lovEmpleados = lovEmpleados;
    }

    public List<Empleados> getFiltrarLovEmpleados() {
        return filtrarLovEmpleados;
    }

    public void setFiltrarLovEmpleados(List<Empleados> filtrarLovEmpleados) {
        this.filtrarLovEmpleados = filtrarLovEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<MotivosLocalizaciones> getLovMotivosLocalizaciones() {
        lovMotivosLocalizaciones = administrarPersonaIndividual.lovMotivosLocalizaciones();
        return lovMotivosLocalizaciones;
    }

    public void setLovMotivosLocalizaciones(List<MotivosLocalizaciones> lovMotivosLocalizaciones) {
        this.lovMotivosLocalizaciones = lovMotivosLocalizaciones;
    }

    public List<MotivosLocalizaciones> getFiltrarLovMotivosLocalizaciones() {
        return filtrarLovMotivosLocalizaciones;
    }

    public void setFiltrarLovMotivosLocalizaciones(List<MotivosLocalizaciones> filtrarLovMotivosLocalizaciones) {
        this.filtrarLovMotivosLocalizaciones = filtrarLovMotivosLocalizaciones;
    }

    public MotivosLocalizaciones getMotivoLocalizacionSeleccionado() {
        return motivoLocalizacionSeleccionado;
    }

    public void setMotivoLocalizacionSeleccionado(MotivosLocalizaciones motivoLocalizacionSeleccionado) {
        this.motivoLocalizacionSeleccionado = motivoLocalizacionSeleccionado;
    }

    public List<Estructuras> getLovEstructurasCentroCosto() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            lovEstructurasCentroCosto = administrarPersonaIndividual.lovEstructurasModCentroCosto(nuevoEmpleado.getEmpresa().getSecuencia());
        }
        return lovEstructurasCentroCosto;
    }

    public void setLovEstructurasCentroCosto(List<Estructuras> lovEstructurasCentroCosto) {
        this.lovEstructurasCentroCosto = lovEstructurasCentroCosto;
    }

    public List<Estructuras> getFiltrarLovEstructurasCentroCosto() {
        return filtrarLovEstructurasCentroCosto;
    }

    public void setFiltrarLovEstructurasCentroCosto(List<Estructuras> filtrarLovEstructurasCentroCosto) {
        this.filtrarLovEstructurasCentroCosto = filtrarLovEstructurasCentroCosto;
    }

    public Estructuras getEstructuraCentroCostoSeleccionada() {
        return estructuraCentroCostoSeleccionada;
    }

    public void setEstructuraCentroCostoSeleccionada(Estructuras estructuraCentroCostoSeleccionada) {
        this.estructuraCentroCostoSeleccionada = estructuraCentroCostoSeleccionada;
    }

    public boolean isDisableDescripcionEstructura() {
        return disableDescripcionEstructura;
    }

    public void setDisableDescripcionEstructura(boolean disableDescripcionEstructura) {
        this.disableDescripcionEstructura = disableDescripcionEstructura;
    }

    public VigenciasTiposTrabajadores getNuevaVigenciaTipoTrabajador() {
        return nuevaVigenciaTipoTrabajador;
    }

    public void setNuevaVigenciaTipoTrabajador(VigenciasTiposTrabajadores nuevaVigenciaTipoTrabajador) {
        this.nuevaVigenciaTipoTrabajador = nuevaVigenciaTipoTrabajador;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        lovTiposTrabajadores = administrarPersonaIndividual.lovTiposTrabajadores();
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

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
    }

    public VigenciasReformasLaborales getNuevaVigenciaReformaLaboral() {
        return nuevaVigenciaReformaLaboral;
    }

    public void setNuevaVigenciaReformaLaboral(VigenciasReformasLaborales nuevaVigenciaReformaLaboral) {
        this.nuevaVigenciaReformaLaboral = nuevaVigenciaReformaLaboral;
    }

    public List<ReformasLaborales> getLovReformasLaborales() {
        lovReformasLaborales = administrarPersonaIndividual.lovReformasLaborales();
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

    public VigenciasSueldos getNuevaVigenciaSueldo() {
        return nuevaVigenciaSueldo;
    }

    public void setNuevaVigenciaSueldo(VigenciasSueldos nuevaVigenciaSueldo) {
        this.nuevaVigenciaSueldo = nuevaVigenciaSueldo;
    }

    public BigDecimal getValorSueldo() {
        return valorSueldo;
    }

    public void setValorSueldo(BigDecimal valorSueldo) {
        this.valorSueldo = valorSueldo;
    }

    public List<MotivosCambiosSueldos> getLovMotivosCambiosSueldos() {
        lovMotivosCambiosSueldos = administrarPersonaIndividual.lovMotivosCambiosSueldos();
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

    public MotivosCambiosSueldos getMotivoCambioSueldoSeleccionado() {
        return motivoCambioSueldoSeleccionado;
    }

    public void setMotivoCambioSueldoSeleccionado(MotivosCambiosSueldos motivoCambioSueldoSeleccionado) {
        this.motivoCambioSueldoSeleccionado = motivoCambioSueldoSeleccionado;
    }

    public List<TiposSueldos> getLovTiposSueldos() {
        lovTiposSueldos = administrarPersonaIndividual.lovTiposSueldos();
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

    public List<Unidades> getLovUnidades() {
        lovUnidades = administrarPersonaIndividual.lovUnidades();
        return lovUnidades;
    }

    public void setLovUnidades(List<Unidades> lovUnidades) {
        this.lovUnidades = lovUnidades;
    }

    public List<Unidades> getFiltrarLovUnidades() {
        return filtrarLovUnidades;
    }

    public void setFiltrarLovUnidades(List<Unidades> filtrarLovUnidades) {
        this.filtrarLovUnidades = filtrarLovUnidades;
    }

    public Unidades getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidades unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public VigenciasTiposContratos getNuevaVigenciaTipoContrato() {
        return nuevaVigenciaTipoContrato;
    }

    public void setNuevaVigenciaTipoContrato(VigenciasTiposContratos nuevaVigenciaTipoContrato) {
        this.nuevaVigenciaTipoContrato = nuevaVigenciaTipoContrato;
    }

    public List<MotivosContratos> getLovMotivosContratos() {
        lovMotivosContratos = administrarPersonaIndividual.lovMotivosContratos();
        return lovMotivosContratos;
    }

    public void setLovMotivosContratos(List<MotivosContratos> lovMotivosContratos) {
        this.lovMotivosContratos = lovMotivosContratos;
    }

    public List<MotivosContratos> getFiltrarLovMotivosContratos() {
        return filtrarLovMotivosContratos;
    }

    public void setFiltrarLovMotivosContratos(List<MotivosContratos> filtrarLovMotivosContratos) {
        this.filtrarLovMotivosContratos = filtrarLovMotivosContratos;
    }

    public MotivosContratos getMotivoContratoSeleccionado() {
        return motivoContratoSeleccionado;
    }

    public void setMotivoContratoSeleccionado(MotivosContratos motivoContratoSeleccionado) {
        this.motivoContratoSeleccionado = motivoContratoSeleccionado;
    }

    public List<TiposContratos> getLovTiposContratos() {
        lovTiposContratos = administrarPersonaIndividual.lovTiposContratos();
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

    public VigenciasNormasEmpleados getNuevaVigenciaNormaEmpleado() {
        return nuevaVigenciaNormaEmpleado;
    }

    public void setNuevaVigenciaNormaEmpleado(VigenciasNormasEmpleados nuevaVigenciaNormaEmpleado) {
        this.nuevaVigenciaNormaEmpleado = nuevaVigenciaNormaEmpleado;
    }

    public List<NormasLaborales> getLovNormasLaborales() {
        lovNormasLaborales = administrarPersonaIndividual.lovNormasLaborales();
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

    public VigenciasContratos getNuevaVigenciaContrato() {
        return nuevaVigenciaContrato;
    }

    public void setNuevaVigenciaContrato(VigenciasContratos nuevaVigenciaContrato) {
        this.nuevaVigenciaContrato = nuevaVigenciaContrato;
    }

    public List<Contratos> getLovContratos() {
        lovContratos = administrarPersonaIndividual.lovContratos();
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

    public VigenciasUbicaciones getNuevaVigenciaUbicacion() {
        return nuevaVigenciaUbicacion;
    }

    public void setNuevaVigenciaUbicacion(VigenciasUbicaciones nuevaVigenciaUbicacion) {
        this.nuevaVigenciaUbicacion = nuevaVigenciaUbicacion;
    }

    public List<UbicacionesGeograficas> getLovUbicacionesGeograficas() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            lovUbicacionesGeograficas = administrarPersonaIndividual.lovUbicacionesGeograficas(nuevoEmpleado.getEmpresa().getSecuencia());
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

    public boolean isDisableUbicacionGeografica() {
        return disableUbicacionGeografica;
    }

    public void setDisableUbicacionGeografica(boolean disableUbicacionGeografica) {
        this.disableUbicacionGeografica = disableUbicacionGeografica;
    }

    public VigenciasFormasPagos getNuevaVigenciaFormaPago() {
        return nuevaVigenciaFormaPago;
    }

    public void setNuevaVigenciaFormaPago(VigenciasFormasPagos nuevaVigenciaFormaPago) {
        this.nuevaVigenciaFormaPago = nuevaVigenciaFormaPago;
    }

    public List<Periodicidades> getLovPeriodicidades() {
        lovPeriodicidades = administrarPersonaIndividual.lovPeriodicidades();
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

    public List<Sucursales> getLovSucursales() {
        lovSucursales = administrarPersonaIndividual.lovSucursales();
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

    public List<MetodosPagos> getLovMetodosPagos() {
        lovMetodosPagos = administrarPersonaIndividual.lovMetodosPagos();
        return lovMetodosPagos;
    }

    public void setLovMetodosPagos(List<MetodosPagos> lovMetodosPagos) {
        this.lovMetodosPagos = lovMetodosPagos;
    }

    public List<MetodosPagos> getFiltrarLovMetodosPagos() {
        return filtrarLovMetodosPagos;
    }

    public void setFiltrarLovMetodosPagos(List<MetodosPagos> filtrarLovMetodosPagos) {
        this.filtrarLovMetodosPagos = filtrarLovMetodosPagos;
    }

    public MetodosPagos getMetodoPagoSeleccionado() {
        return metodoPagoSeleccionado;
    }

    public void setMetodoPagoSeleccionado(MetodosPagos metodoPagoSeleccionado) {
        this.metodoPagoSeleccionado = metodoPagoSeleccionado;
    }

    public Periodicidades getPeriodicidadSeleccionada() {
        return periodicidadSeleccionada;
    }

    public void setPeriodicidadSeleccionada(Periodicidades periodicidadSeleccionada) {
        this.periodicidadSeleccionada = periodicidadSeleccionada;
    }

    public VigenciasJornadas getNuevaVigenciaJornada() {
        return nuevaVigenciaJornada;
    }

    public void setNuevaVigenciaJornada(VigenciasJornadas nuevaVigenciaJornada) {
        this.nuevaVigenciaJornada = nuevaVigenciaJornada;
    }

    public List<JornadasLaborales> getLovJornadasLaborales() {
        lovJornadasLaborales = administrarPersonaIndividual.lovJornadasLaborales();
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

    public VigenciasAfiliaciones getNuevaVigenciaAfiliacionEPS() {
        return nuevaVigenciaAfiliacionEPS;
    }

    public void setNuevaVigenciaAfiliacionEPS(VigenciasAfiliaciones nuevaVigenciaAfiliacionEPS) {
        this.nuevaVigenciaAfiliacionEPS = nuevaVigenciaAfiliacionEPS;
    }

    public VigenciasAfiliaciones getNuevaVigenciaAfiliacionAFP() {
        return nuevaVigenciaAfiliacionAFP;
    }

    public void setNuevaVigenciaAfiliacionAFP(VigenciasAfiliaciones nuevaVigenciaAfiliacionAFP) {
        this.nuevaVigenciaAfiliacionAFP = nuevaVigenciaAfiliacionAFP;
    }

    public VigenciasAfiliaciones getNuevaVigenciaAfiliacionFondo() {
        return nuevaVigenciaAfiliacionFondo;
    }

    public void setNuevaVigenciaAfiliacionFondo(VigenciasAfiliaciones nuevaVigenciaAfiliacionFondo) {
        this.nuevaVigenciaAfiliacionFondo = nuevaVigenciaAfiliacionFondo;
    }

    public VigenciasAfiliaciones getNuevaVigenciaAfiliacionARP() {
        return nuevaVigenciaAfiliacionARP;
    }

    public void setNuevaVigenciaAfiliacionARP(VigenciasAfiliaciones nuevaVigenciaAfiliacionARP) {
        this.nuevaVigenciaAfiliacionARP = nuevaVigenciaAfiliacionARP;
    }

    public VigenciasAfiliaciones getNuevaVigenciaAfiliacionCaja() {
        return nuevaVigenciaAfiliacionCaja;
    }

    public void setNuevaVigenciaAfiliacionCaja(VigenciasAfiliaciones nuevaVigenciaAfiliacionCaja) {
        this.nuevaVigenciaAfiliacionCaja = nuevaVigenciaAfiliacionCaja;
    }

    public List<TercerosSucursales> getLovTercerosSucursales() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            lovTercerosSucursales = administrarPersonaIndividual.lovTercerosSucursales(nuevoEmpleado.getEmpresa().getSecuencia());
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

    public boolean isDisableAfiliaciones() {
        return disableAfiliaciones;
    }

    public void setDisableAfiliaciones(boolean disableAfiliaciones) {
        this.disableAfiliaciones = disableAfiliaciones;
    }

    public VigenciasEstadosCiviles getNuevoEstadoCivil() {
        return nuevoEstadoCivil;
    }

    public void setNuevoEstadoCivil(VigenciasEstadosCiviles nuevoEstadoCivil) {
        this.nuevoEstadoCivil = nuevoEstadoCivil;
    }

    public List<EstadosCiviles> getLovEstadosCiviles() {
        lovEstadosCiviles = administrarPersonaIndividual.lovEstadosCiviles();
        return lovEstadosCiviles;
    }

    public void setLovEstadosCiviles(List<EstadosCiviles> lovEstadosCiviles) {
        this.lovEstadosCiviles = lovEstadosCiviles;
    }

    public List<EstadosCiviles> getFiltrarLovEstadosCiviles() {
        return filtrarLovEstadosCiviles;
    }

    public void setFiltrarLovEstadosCiviles(List<EstadosCiviles> filtrarLovEstadosCiviles) {
        this.filtrarLovEstadosCiviles = filtrarLovEstadosCiviles;
    }

    public EstadosCiviles getEstadoCivilSeleccionado() {
        return estadoCivilSeleccionado;
    }

    public void setEstadoCivilSeleccionado(EstadosCiviles estadoCivilSeleccionado) {
        this.estadoCivilSeleccionado = estadoCivilSeleccionado;
    }

    public Direcciones getNuevaDireccion() {
        return nuevaDireccion;
    }

    public void setNuevaDireccion(Direcciones nuevaDireccion) {
        this.nuevaDireccion = nuevaDireccion;
    }

    public Telefonos getNuevoTelefono() {
        return nuevoTelefono;
    }

    public void setNuevoTelefono(Telefonos nuevoTelefono) {
        this.nuevoTelefono = nuevoTelefono;
    }

    public List<TiposTelefonos> getLovTiposTelefonos() {
        lovTiposTelefonos = administrarPersonaIndividual.lovTiposTelefonos();
        return lovTiposTelefonos;
    }

    public void setLovTiposTelefonos(List<TiposTelefonos> lovTiposTelefonos) {
        this.lovTiposTelefonos = lovTiposTelefonos;
    }

    public List<TiposTelefonos> getFiltrarLovTiposTelefonos() {
        return filtrarLovTiposTelefonos;
    }

    public void setFiltrarLovTiposTelefonos(List<TiposTelefonos> filtrarLovTiposTelefonos) {
        this.filtrarLovTiposTelefonos = filtrarLovTiposTelefonos;
    }

    public TiposTelefonos getTipoTelefonoSeleccionado() {
        return tipoTelefonoSeleccionado;
    }

    public void setTipoTelefonoSeleccionado(TiposTelefonos tipoTelefonoSeleccionado) {
        this.tipoTelefonoSeleccionado = tipoTelefonoSeleccionado;
    }

    public String getInfoRegistroEmpresaInformacionPersonal() {
        getLovEmpresas();
        if (lovEmpresas != null) {
            infoRegistroEmpresaInformacionPersonal = "Cantidad de registros: " + lovEmpresas.size();
        } else {
            infoRegistroEmpresaInformacionPersonal = "Cantidad de registros: 0 ";
        }
        return infoRegistroEmpresaInformacionPersonal;
    }

    public void setInfoRegistroEmpresaInformacionPersonal(String infoRegistroEmpresaInformacionPersonal) {
        this.infoRegistroEmpresaInformacionPersonal = infoRegistroEmpresaInformacionPersonal;
    }

    public String getInfoRegistroTipoDocumentoInformacionPersonal() {
        getLovTiposDocumentos();
        if (lovTiposDocumentos != null) {
            infoRegistroTipoDocumentoInformacionPersonal = "Cantidad de registros: " + lovTiposDocumentos.size();
        } else {
            infoRegistroTipoDocumentoInformacionPersonal = "Cantidad de registros: 0 ";
        }
        return infoRegistroTipoDocumentoInformacionPersonal;
    }

    public void setInfoRegistroTipoDocumentoInformacionPersonal(String infoRegistroTipoDocumentoInformacionPersonal) {
        this.infoRegistroTipoDocumentoInformacionPersonal = infoRegistroTipoDocumentoInformacionPersonal;
    }

    public String getInfoRegistroCiudadDocumentoInformacionPersonal() {
        getLovCiudades();
        if (lovCiudades != null) {
            infoRegistroCiudadDocumentoInformacionPersonal = "Cantidad de registros: " + lovCiudades.size();
        } else {
            infoRegistroCiudadDocumentoInformacionPersonal = "Cantidad de registros: 0 ";
        }
        return infoRegistroCiudadDocumentoInformacionPersonal;
    }

    public void setInfoRegistroCiudadDocumentoInformacionPersonal(String infoRegistroCiudadDocumentoInformacionPersonal) {
        this.infoRegistroCiudadDocumentoInformacionPersonal = infoRegistroCiudadDocumentoInformacionPersonal;
    }

    public String getInfoRegistroCiudadNacimientoInformacionPersonal() {
        getLovCiudades();
        if (lovCiudades != null) {
            infoRegistroCiudadNacimientoInformacionPersonal = "Cantidad de registros: " + lovCiudades.size();
        } else {
            infoRegistroCiudadNacimientoInformacionPersonal = "Cantidad de registros: 0 ";
        }
        return infoRegistroCiudadNacimientoInformacionPersonal;
    }

    public void setInfoRegistroCiudadNacimientoInformacionPersonal(String infoRegistroCiudadNacimientoInformacionPersonal) {
        this.infoRegistroCiudadNacimientoInformacionPersonal = infoRegistroCiudadNacimientoInformacionPersonal;
    }

    public String getInfoRegistroCargoCargoDesempenado() {
        getLovCargos();
        if (lovCargos != null) {
            infoRegistroCargoCargoDesempenado = "Cantidad de registros: " + lovCargos.size();
        } else {
            infoRegistroCargoCargoDesempenado = "Cantidad de registros: 0 ";
        }
        return infoRegistroCargoCargoDesempenado;
    }

    public void setInfoRegistroCargoCargoDesempenado(String infoRegistroCargoCargoDesempenado) {
        this.infoRegistroCargoCargoDesempenado = infoRegistroCargoCargoDesempenado;
    }

    public String getInfoRegistroMotivoCargoCargoDesempenado() {
        getLovMotivosCambiosCargos();
        if (lovMotivosCambiosCargos != null) {
            infoRegistroMotivoCargoCargoDesempenado = "Cantidad de registros: " + lovMotivosCambiosCargos.size();
        } else {
            infoRegistroMotivoCargoCargoDesempenado = "Cantidad de registros: 0 ";
        }
        return infoRegistroMotivoCargoCargoDesempenado;
    }

    public void setInfoRegistroMotivoCargoCargoDesempenado(String infoRegistroMotivoCargoCargoDesempenado) {
        this.infoRegistroMotivoCargoCargoDesempenado = infoRegistroMotivoCargoCargoDesempenado;
    }

    public String getInfoRegistroEstructuraCargoDesempenado() {
        getLovEstructuras();
        if (lovEstructuras != null) {
            infoRegistroEstructuraCargoDesempenado = "Cantidad de registros: " + lovEstructuras.size();
        } else {
            infoRegistroEstructuraCargoDesempenado = "Cantidad de registros: 0 ";
        }
        return infoRegistroEstructuraCargoDesempenado;
    }

    public void setInfoRegistroEstructuraCargoDesempenado(String infoRegistroEstructuraCargoDesempenado) {
        this.infoRegistroEstructuraCargoDesempenado = infoRegistroEstructuraCargoDesempenado;
    }

    public String getInfoRegistroPapelCargoDesempenado() {
        getLovPapeles();
        if (lovPapeles != null) {
            infoRegistroPapelCargoDesempenado = "Cantidad de registros: " + lovPapeles.size();
        } else {
            infoRegistroPapelCargoDesempenado = "Cantidad de registros: 0 ";
        }
        return infoRegistroPapelCargoDesempenado;
    }

    public void setInfoRegistroPapelCargoDesempenado(String infoRegistroPapelCargoDesempenado) {
        this.infoRegistroPapelCargoDesempenado = infoRegistroPapelCargoDesempenado;
    }

    public String getInfoRegistroJefeCargoDesempenado() {
        getLovEmpleados();
        if (lovEmpleados != null) {
            infoRegistroJefeCargoDesempenado = "Cantidad de registros: " + lovEmpleados.size();
        } else {
            infoRegistroJefeCargoDesempenado = "Cantidad de registros: 0 ";
        }
        return infoRegistroJefeCargoDesempenado;
    }

    public void setInfoRegistroJefeCargoDesempenado(String infoRegistroJefeCargoDesempenado) {
        this.infoRegistroJefeCargoDesempenado = infoRegistroJefeCargoDesempenado;
    }

    public String getInfoRegistroMotivoCentroCosto() {
        getLovMotivosLocalizaciones();
        if (lovMotivosLocalizaciones != null) {
            infoRegistroMotivoCentroCosto = "Cantidad de registros: " + lovMotivosLocalizaciones.size();
        } else {
            infoRegistroMotivoCentroCosto = "Cantidad de registros: 0 ";
        }
        return infoRegistroMotivoCentroCosto;
    }

    public void setInfoRegistroMotivoCentroCosto(String infoRegistroMotivoCentroCosto) {
        this.infoRegistroMotivoCentroCosto = infoRegistroMotivoCentroCosto;
    }

    public String getInfoRegistroEstructuraCentroCosto() {
        getLovEstructurasCentroCosto();
        if (lovEstructurasCentroCosto != null) {
            infoRegistroEstructuraCentroCosto = "Cantidad de registros: " + lovEstructurasCentroCosto.size();
        } else {
            infoRegistroEstructuraCentroCosto = "Cantidad de registros: 0 ";
        }
        return infoRegistroEstructuraCentroCosto;
    }

    public void setInfoRegistroEstructuraCentroCosto(String infoRegistroEstructuraCentroCosto) {
        this.infoRegistroEstructuraCentroCosto = infoRegistroEstructuraCentroCosto;
    }

    public String getInfoRegistroTipoTrabajadorTipoTrabajador() {
        getLovTiposTrabajadores();
        if (lovTiposTrabajadores != null) {
            infoRegistroTipoTrabajadorTipoTrabajador = "Cantidad de registros: " + lovTiposTrabajadores.size();
        } else {
            infoRegistroTipoTrabajadorTipoTrabajador = "Cantidad de registros: 0 ";
        }
        return infoRegistroTipoTrabajadorTipoTrabajador;
    }

    public void setInfoRegistroTipoTrabajadorTipoTrabajador(String infoRegistroTipoTrabajadorTipoTrabajador) {
        this.infoRegistroTipoTrabajadorTipoTrabajador = infoRegistroTipoTrabajadorTipoTrabajador;
    }

    public String getInfoRegistroReformaTipoSalario() {
        getLovReformasLaborales();
        if (lovReformasLaborales != null) {
            infoRegistroReformaTipoSalario = "Cantidad de registros: " + lovReformasLaborales.size();
        } else {
            infoRegistroReformaTipoSalario = "Cantidad de registros: 0 ";
        }
        return infoRegistroReformaTipoSalario;
    }

    public void setInfoRegistroReformaTipoSalario(String infoRegistroReformaTipoSalario) {
        this.infoRegistroReformaTipoSalario = infoRegistroReformaTipoSalario;
    }

    public String getInfoRegistroMotivoSueldo() {
        getLovMotivosCambiosSueldos();
        if (lovMotivosCambiosSueldos != null) {
            infoRegistroMotivoSueldo = "Cantidad de registros: " + lovMotivosCambiosSueldos.size();
        } else {
            infoRegistroMotivoSueldo = "Cantidad de registros: 0 ";
        }
        return infoRegistroMotivoSueldo;
    }

    public void setInfoRegistroMotivoSueldo(String infoRegistroMotivoSueldo) {
        this.infoRegistroMotivoSueldo = infoRegistroMotivoSueldo;
    }

    public String getInfoRegistroTipoSueldoSueldo() {
        getLovTiposSueldos();
        if (lovTiposSueldos != null) {
            infoRegistroTipoSueldoSueldo = "Cantidad de registros: " + lovTiposSueldos.size();
        } else {
            infoRegistroTipoSueldoSueldo = "Cantidad de registros: 0 ";
        }
        return infoRegistroTipoSueldoSueldo;
    }

    public void setInfoRegistroTipoSueldoSueldo(String infoRegistroTipoSueldoSueldo) {
        this.infoRegistroTipoSueldoSueldo = infoRegistroTipoSueldoSueldo;
    }

    public String getInfoRegistroUnidadSueldo() {
        getLovUnidades();
        if (lovUnidades != null) {
            infoRegistroUnidadSueldo = "Cantidad de registros: " + lovUnidades.size();
        } else {
            infoRegistroUnidadSueldo = "Cantidad de registros: 0 ";
        }
        return infoRegistroUnidadSueldo;
    }

    public void setInfoRegistroUnidadSueldo(String infoRegistroUnidadSueldo) {
        this.infoRegistroUnidadSueldo = infoRegistroUnidadSueldo;
    }

    public String getInfoRegistroMotivoTipoContrato() {
        getLovMotivosContratos();
        if (lovMotivosContratos != null) {
            infoRegistroMotivoTipoContrato = "Cantidad de registros: " + lovMotivosContratos.size();
        } else {
            infoRegistroMotivoTipoContrato = "Cantidad de registros: 0 ";
        }
        return infoRegistroMotivoTipoContrato;
    }

    public void setInfoRegistroMotivoTipoContrato(String infoRegistroMotivoTipoContrato) {
        this.infoRegistroMotivoTipoContrato = infoRegistroMotivoTipoContrato;
    }

    public String getInfoRegistroTipoTipoContrato() {
        getLovTiposContratos();
        if (lovTiposContratos != null) {
            infoRegistroTipoTipoContrato = "Cantidad de registros: " + lovTiposContratos.size();
        } else {
            infoRegistroTipoTipoContrato = "Cantidad de registros: 0 ";
        }
        return infoRegistroTipoTipoContrato;
    }

    public void setInfoRegistroTipoTipoContrato(String infoRegistroTipoTipoContrato) {
        this.infoRegistroTipoTipoContrato = infoRegistroTipoTipoContrato;
    }

    public String getInfoRegistroNormaNormaLaboral() {
        getLovNormasLaborales();
        if (lovNormasLaborales != null) {
            infoRegistroNormaNormaLaboral = "Cantidad de registros: " + lovNormasLaborales.size();
        } else {
            infoRegistroNormaNormaLaboral = "Cantidad de registros: 0 ";
        }
        return infoRegistroNormaNormaLaboral;
    }

    public void setInfoRegistroNormaNormaLaboral(String infoRegistroNormaNormaLaboral) {
        this.infoRegistroNormaNormaLaboral = infoRegistroNormaNormaLaboral;
    }

    public String getInfoRegistroContratoLegislacionLaboral() {
        getLovContratos();
        if (lovContratos != null) {
            infoRegistroContratoLegislacionLaboral = "Cantidad de registros: " + lovContratos.size();
        } else {
            infoRegistroContratoLegislacionLaboral = "Cantidad de registros: 0 ";
        }
        return infoRegistroContratoLegislacionLaboral;
    }

    public void setInfoRegistroContratoLegislacionLaboral(String infoRegistroContratoLegislacionLaboral) {
        this.infoRegistroContratoLegislacionLaboral = infoRegistroContratoLegislacionLaboral;
    }

    public String getInfoRegistroUbicacionUbicacion() {
        getLovUbicacionesGeograficas();
        if (lovUbicacionesGeograficas != null) {
            infoRegistroUbicacionUbicacion = "Cantidad de registros: " + lovUbicacionesGeograficas.size();
        } else {
            infoRegistroUbicacionUbicacion = "Cantidad de registros: 0 ";
        }
        return infoRegistroUbicacionUbicacion;
    }

    public void setInfoRegistroUbicacionUbicacion(String infoRegistroUbicacionUbicacion) {
        this.infoRegistroUbicacionUbicacion = infoRegistroUbicacionUbicacion;
    }

    public String getInfoRegistroJornadaJornadaLaboral() {
        getLovJornadasLaborales();
        if (lovJornadasLaborales != null) {
            infoRegistroJornadaJornadaLaboral = "Cantidad de registros: " + lovJornadasLaborales.size();
        } else {
            infoRegistroJornadaJornadaLaboral = "Cantidad de registros: 0 ";
        }
        return infoRegistroJornadaJornadaLaboral;
    }

    public void setInfoRegistroJornadaJornadaLaboral(String infoRegistroJornadaJornadaLaboral) {
        this.infoRegistroJornadaJornadaLaboral = infoRegistroJornadaJornadaLaboral;
    }

    public String getInfoRegistroFormaFormaPago() {
        getLovPeriodicidades();
        if (lovPeriodicidades != null) {
            infoRegistroFormaFormaPago = "Cantidad de registros: " + lovPeriodicidades.size();
        } else {
            infoRegistroFormaFormaPago = "Cantidad de registros: 0 ";
        }
        return infoRegistroFormaFormaPago;
    }

    public void setInfoRegistroFormaFormaPago(String infoRegistroFormaFormaPago) {
        this.infoRegistroFormaFormaPago = infoRegistroFormaFormaPago;
    }

    public String getInfoRegistroSucursalFormaPago() {
        getLovSucursales();
        if (lovSucursales != null) {
            infoRegistroSucursalFormaPago = "Cantidad de registros: " + lovSucursales.size();
        } else {
            infoRegistroSucursalFormaPago = "Cantidad de registros: 0 ";
        }
        return infoRegistroSucursalFormaPago;
    }

    public void setInfoRegistroSucursalFormaPago(String infoRegistroSucursalFormaPago) {
        this.infoRegistroSucursalFormaPago = infoRegistroSucursalFormaPago;
    }

    public String getInfoRegistroMetodoFormaPago() {
        getLovMetodosPagos();
        if (lovMetodosPagos != null) {
            infoRegistroMetodoFormaPago = "Cantidad de registros: " + lovMetodosPagos.size();
        } else {
            infoRegistroMetodoFormaPago = "Cantidad de registros: 0 ";
        }
        return infoRegistroMetodoFormaPago;
    }

    public void setInfoRegistroMetodoFormaPago(String infoRegistroMetodoFormaPago) {
        this.infoRegistroMetodoFormaPago = infoRegistroMetodoFormaPago;
    }

    public String getInfoRegistroTerceroAfiliacion() {
        getLovTercerosSucursales();
        if (lovTercerosSucursales != null) {
            infoRegistroTerceroAfiliacion = "Cantidad de registros: " + lovTercerosSucursales.size();
        } else {
            infoRegistroTerceroAfiliacion = "Cantidad de registros: 0 ";
        }
        return infoRegistroTerceroAfiliacion;
    }

    public void setInfoRegistroTerceroAfiliacion(String infoRegistroTerceroAfiliacion) {
        this.infoRegistroTerceroAfiliacion = infoRegistroTerceroAfiliacion;
    }

    public String getInfoRegistroEstadoCivilEstadoCivil() {
        getLovEstadosCiviles();
        if (lovEstadosCiviles != null) {
            infoRegistroEstadoCivilEstadoCivil = "Cantidad de registros: " + lovEstadosCiviles.size();
        } else {
            infoRegistroEstadoCivilEstadoCivil = "Cantidad de registros: 0 ";
        }
        return infoRegistroEstadoCivilEstadoCivil;
    }

    public void setInfoRegistroEstadoCivilEstadoCivil(String infoRegistroEstadoCivilEstadoCivil) {
        this.infoRegistroEstadoCivilEstadoCivil = infoRegistroEstadoCivilEstadoCivil;
    }

    public String getInfoRegistroCiudadDireccion() {
        getLovCiudades();
        if (lovCiudades != null) {
            infoRegistroCiudadDireccion = "Cantidad de registros: " + lovCiudades.size();
        } else {
            infoRegistroCiudadDireccion = "Cantidad de registros: 0 ";
        }
        return infoRegistroCiudadDireccion;
    }

    public void setInfoRegistroCiudadDireccion(String infoRegistroCiudadDireccion) {
        this.infoRegistroCiudadDireccion = infoRegistroCiudadDireccion;
    }

    public String getInfoRegistroCiudadTelefono() {
        getLovCiudades();
        if (lovCiudades != null) {
            infoRegistroCiudadTelefono = "Cantidad de registros: " + lovCiudades.size();
        } else {
            infoRegistroCiudadTelefono = "Cantidad de registros: 0 ";
        }
        return infoRegistroCiudadTelefono;
    }

    public void setInfoRegistroCiudadTelefono(String infoRegistroCiudadTelefono) {
        this.infoRegistroCiudadTelefono = infoRegistroCiudadTelefono;
    }

    public String getInfoRegistroTipoTelefonoTelefono() {
        getLovTiposTelefonos();
        if (lovTiposTelefonos != null) {
            infoRegistroTipoTelefonoTelefono = "Cantidad de registros: " + lovTiposTelefonos.size();
        } else {
            infoRegistroTipoTelefonoTelefono = "Cantidad de registros: 0 ";
        }
        return infoRegistroTipoTelefonoTelefono;
    }

    public void setInfoRegistroTipoTelefonoTelefono(String infoRegistroTipoTelefonoTelefono) {
        this.infoRegistroTipoTelefonoTelefono = infoRegistroTipoTelefonoTelefono;
    }

    public boolean isDisableCamposDependientesTipoTrabajador() {
        return disableCamposDependientesTipoTrabajador;
    }

    public void setDisableCamposDependientesTipoTrabajador(boolean disableCamposDependientesTipoTrabajador) {
        this.disableCamposDependientesTipoTrabajador = disableCamposDependientesTipoTrabajador;
    }

    public String getMensajeErrorFechasEmpleado() {
        return mensajeErrorFechasEmpleado;
    }

    public void setMensajeErrorFechasEmpleado(String mensajeErrorFechasEmpleado) {
        this.mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado;
    }

}

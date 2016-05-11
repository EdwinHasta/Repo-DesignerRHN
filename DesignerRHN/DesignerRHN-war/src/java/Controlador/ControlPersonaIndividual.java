package Controlador;

import Entidades.*;
import InterfaceAdministrar.AdministrarCarpetaPersonalInterface;
import InterfaceAdministrar.AdministrarPersonaIndividualInterface;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private int idInformacionPersonal;
    private boolean permitirIndexInformacionPersonal;
    private String auxInformacionPersonaEmpresal, auxInformacionPersonalCiudadNacimiento;
    private String auxInformacionPersonalCiudadDocumento, auxInformacionPersonalTipoDocumento;
    private Date fechaIngreso, fechaCorte;
    private Date auxFechaNacimiento, auxFechaIngreso, auxFechaCorte;
    //Modulo Cargo Desempeñado
    private VigenciasCargos nuevaVigenciaCargo;
    private int idCargoDesempeñado;
    private boolean permitirIndexCargoDesempeñado;
    private String auxCargoDesempeñadoNombreCargo, auxCargoDesempeñadoMotivoCargo, auxCargoDesempeñadoNombreEstructura;
    private String auxCargoDesempeñadoPapel, auxCargoDesempeñadoEmpleadoJefe;
    //Modulo Centro de Costo - Localizacion
    private VigenciasLocalizaciones nuevaVigenciaLocalizacion;
    private int idCentroCosto;
    private boolean permitirIndexCentroCosto;
    private String auxCentroCostoMotivo, auxCentroCostoEstructura;
    //Modulo Tipo Trabajador
    private VigenciasTiposTrabajadores nuevaVigenciaTipoTrabajador;
    private int idTipoTrabajador;
    private boolean permitirIndexTipoTrabajador;
    private String auxTipoTrabajadorNombreTipoTrabajador;
    //Modulo Reforma Laboral
    private VigenciasReformasLaborales nuevaVigenciaReformaLaboral;
    private int idTipoSalario;
    private boolean permitirIndexTipoSalario;
    private String auxTipoSalarioReformaLaboral;
    //Modulo Sueldo
    private VigenciasSueldos nuevaVigenciaSueldo;
    private int idSueldo;
    private boolean permitirIndexSueldo;
    private String auxSueldoMotivoSueldo, auxSueldoTipoSueldo, auxSueldoUnidad;
    private BigDecimal valorSueldo;
    private BigDecimal auxSueldoValor;
    //Modulo Tipo Contrato
    private VigenciasTiposContratos nuevaVigenciaTipoContrato;
    private int idTipoContrato;
    private boolean permitirIndexTipoContrato;
    private String auxTipoContratoMotivo, auxTipoContratoTipoContrato;
    private Date auxTipoContratoFecha;
    //Modulo Norma Laboral
    private VigenciasNormasEmpleados nuevaVigenciaNormaEmpleado;
    private int idNormaLaboral;
    private boolean permitirIndexNormaLaboral;
    private String auxNormaLaboralNorma;
    //Modulo Legislacion Laboral
    private VigenciasContratos nuevaVigenciaContrato;
    private int idLegislacionLaboral;
    private boolean permitirIndexLegislacionLaboral;
    private String auxLegislacionLaboralNombre;
    // Modulo Ubicacion Geografica
    private VigenciasUbicaciones nuevaVigenciaUbicacion;
    private int idUbicacionGeografica;
    private boolean permitirIndexUbicacionGeografica;
    private String auxUbicacionGeograficaUbicacion;
    //Modulo Jornada Laboral
    private VigenciasJornadas nuevaVigenciaJornada;
    private int idJornadaLaboral;
    private boolean permitirIndexJornadaLaboral;
    private String auxJornadaLaboralJornada;
    //Modulo Forma Pago
    private VigenciasFormasPagos nuevaVigenciaFormaPago;
    private int idFormaPago;
    private boolean permitirIndexFormaPago;
    private String auxFormaPagoPeriodicidad, auxFormaPagoSucursal, auxFormaPagoMetodo;
    //Modulo Afiliaciones
    //EPS
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionEPS;
    private int idAfiliacionEPS;
    private boolean permitirIndexAfiliacionEPS;
    private String auxAfiliacionEPS;
    //AFP
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionAFP;
    private int idAfiliacionAFP;
    private boolean permitirIndexAfiliacionAFP;
    private String auxAfiliacionAFP;
    //Fondo Censantias
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionFondo;
    private int idAfiliacionFondo;
    private boolean permitirIndexAfiliacionFondo;
    private String auxAfiliacionFondo;
    //ARP
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionARP;
    private int idAfiliacionARP;
    private boolean permitirIndexAfiliacionARP;
    private String auxAfiliacionARP;
    //Caja
    private VigenciasAfiliaciones nuevaVigenciaAfiliacionCaja;
    private int idAfiliacionCaja;
    private boolean permitirIndexAfiliacionCaja;
    private String auxAfiliacionCaja;
    //Modulo Estado Civil
    private VigenciasEstadosCiviles nuevoEstadoCivil;
    private int idEstadoCivil;
    private boolean permitirIndexEstadoCivil;
    private String auxEstadoCivilEstado;
    //Modulo Direcciones
    private Direcciones nuevaDireccion;
    private int idDireccion;
    private boolean permitirIndexDireccion;
    private String auxDireccionCiudad;
    //Modulo Telefonos
    private Telefonos nuevoTelefono;
    private int idTelefono;
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
    private List<MotivosLocalizaciones> filtrarLovMotivosCC;
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
    private boolean disableCamposDependientesTipoT;
    private boolean aceptar;
    private BigInteger l;
    private int k;
    //Info Registros Lovs
    private String infoRegistroEmpresaInfoPersonal;
    private String infoRegistroTipoDocInfoPersonal;
    private String infoRegistroCiudadDocInfoPersonal;
    private String infoRegistroCiudadNacInfoPersonal;
    private String infoRegistroCargoDesempenado;
    private String infoRegistroMotivoCargoDesempenado;
    private String infoRegistroEstructuraCargoDesempenado;
    private String infoRegistroPapelCargoDesempenado;
    private String infoRegistroJefeCargoDesempenado;
    private String infoRegistroMotivoCentroCosto;
    private String infoRegistroEstructuraCentroCosto;
    private String infoRegistroTipoTrabajadorTT;
    private String infoRegistroReformaTipoSalario;
    private String infoRegistroMotivoSueldo;
    private String infoRegistroTipoSueldoS;
    private String infoRegistroUnidadSueldo;
    private String infoRegistroMotivoTipoContrato;
    private String infoRegistroTipoContrato;
    private String infoRegistroNormaLaboral;
    private String infoRegistroContratoLegislacionL;
    private String infoRegistroUbicacionUb;
    private String infoRegistroJornadaLaboral;
    private String infoRegistroFormaPago;
    private String infoRegistroSucursalFormaPago;
    private String infoRegistroMetodoFormaPago;
    private String infoRegistroTerceroAfiliacion;
    private String infoRegistroEstadoCivil;
    private String infoRegistroCiudadDireccion;
    private String infoRegistroCiudadTelefono;
    private String infoRegistroTipoTelTelefono;
    //
    private String mensajeErrorFechasEmpleado;
    //
    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //unidad PESOS
    private Unidades unidadPesos;

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
        empresaSeleccionada = null;
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
        idInformacionPersonal = -1;
        idCargoDesempeñado = -1;
        idCentroCosto = -1;
        idTipoTrabajador = -1;
        idTipoSalario = -1;
        idSueldo = -1;
        idTipoContrato = -1;
        idNormaLaboral = -1;
        idLegislacionLaboral = -1;
        idUbicacionGeografica = -1;
        idFormaPago = -1;
        idJornadaLaboral = -1;
        idAfiliacionAFP = -1;
        idAfiliacionARP = -1;
        idAfiliacionCaja = -1;
        idAfiliacionEPS = -1;
        idAfiliacionFondo = -1;
        idEstadoCivil = -1;
        idDireccion = -1;
        idTelefono = -1;
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
        System.out.println("disableNombreEstructuraCargo es true");
        disableDescripcionEstructura = true;
        disableUbicacionGeografica = true;
        disableAfiliaciones = true;
        disableCamposDependientesTipoT = true;
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
        //
        unidadPesos = new Unidades();
        unidadPesos.setNombre("");
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
        getLovEmpresas();
        modificarInfoR_EmpresaInfoP(lovEmpresas.size());
    }

    public void procesoDialogoEmpresa() {
        getLovEmpresas();
        modificarInfoR_EmpresaInfoP(lovEmpresas.size());
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroEmpresaInformacionPersonal");
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroEmpresaInformacionPersonalV");
        System.out.println("entro en la funcion procesoDialogoEmpresa()");
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpresaInformacionPersonalDialogo");
        context.execute("EmpresaInformacionPersonalDialogo.show()");
    }

    public void listaValoresInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idInformacionPersonal == 0) {
            getLovEmpresas();
            modificarInfoR_EmpresaInfoP(lovEmpresas.size());
            context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
            context.execute("EmpresaInformacionPersonalDialogo.show()");
            idInformacionPersonal = -1;
        }
        if (idInformacionPersonal == 5) {
            context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
            modificarInfoR_TipoDocInfoP(lovTiposDocumentos.size());
            context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
            idInformacionPersonal = -1;
        }
        if (idInformacionPersonal == 7) {
            context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
            modificarInfoR_CiudadDocInfoP(lovCiudades.size());
            context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
            idInformacionPersonal = -1;
        }
        if (idInformacionPersonal == 9) {
            context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
            modificarInfoR_CiudadNacInfoP(lovCiudades.size());
            context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
            idInformacionPersonal = -1;
        }

    }

    public void listaValoresCargoDesempenado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idCargoDesempeñado == 0) {
            getLovCargos();
            context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
            modificarInfoR_CargoD(lovCargos.size());
            RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroCargoCargoDesempenado");
            context.execute("CargoCargoDesempeñadoDialogo.show()");
            idCargoDesempeñado = -1;
        }
        if (idCargoDesempeñado == 1) {
            context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
            modificarInfoR_MotivoCargoD(lovMotivosCambiosCargos.size());
            context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
            idCargoDesempeñado = -1;
        }
        if (idCargoDesempeñado == 2) {
            context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
            modificarInfoR_EstrCargoD(lovEstructuras.size());
            context.execute("EstructuraCargoDesempeñadoDialogo.show()");
            idCargoDesempeñado = -1;
        }
        if (idCargoDesempeñado == 3) {
            context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
            modificarInfoR_PapelCargoD(lovPapeles.size());
            context.execute("PapelCargoDesempeñadoDialogo.show()");
            idCargoDesempeñado = -1;
        }
        if (idCargoDesempeñado == 4) {
            context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
            modificarInfoR_JefeCargoD(lovEmpleados.size());
            context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
            idCargoDesempeñado = -1;
        }
    }

    public void listaValoresCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idCentroCosto == 0) {
            context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
            modificarInfoR_MotivoCentroC(lovMotivosLocalizaciones.size());
            context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
            idCentroCosto = -1;
        }
        if (idCentroCosto == 1) {
            getLovEstructurasCentroCosto();
            context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
            modificarInfoR_EstrCentroC(lovEstructurasCentroCosto.size());
            context.execute("EstructuraCentroCostoDialogo.show()");
            idCentroCosto = -1;
        }

    }

    public void listaValoresTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idTipoTrabajador == 0) {
            getLovTiposTrabajadores();
            context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
            modificarInfoR_TipoTraTT(lovTiposTrabajadores.size());
            context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
            idTipoTrabajador = -1;
        }

    }

    public void listaValoresTipoSalario() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idTipoSalario == 0) {
            context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
            modificarInfoR_ReformaTipoSa(lovReformasLaborales.size());
            context.execute("ReformaLaboralTipoSalarioDialogo.show()");
            idTipoSalario = -1;
        }
    }

    public void listaValoresSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idSueldo == 1) {
            context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
            modificarInfoR_MotivoSu(lovMotivosCambiosSueldos.size());
            context.execute("MotivoCambioSueldoSueldoDialogo.show()");
            idSueldo = -1;
        }
        if (idSueldo == 2) {
            context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
            modificarInfoR_TipoSuSu(lovTiposSueldos.size());
            context.execute("TipoSueldoSueldoDialogo.show()");
            idSueldo = -1;
        }
        if (idSueldo == 3) {
            context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
            modificarInfoR_UnidadSu(lovUnidades.size());
            context.execute("UnidadSueldoDialogo.show()");
            idSueldo = -1;
        }
    }

    public void listaValoresTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idTipoContrato == 0) {
            context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
            modificarInfoR_MotivoTipoCo(lovMotivosContratos.size());
            context.execute("MotivoContratoTipoContratoDialogo.show()");
            idTipoContrato = -1;
        }
        if (idTipoContrato == 1) {
            context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
            modificarInfoR_TipoContrato(lovTiposContratos.size());
            context.execute("TipoContratoTipoContratoDialogo.show()");
            idTipoContrato = -1;
        }
    }

    public void listaValoresNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idNormaLaboral == 0) {
            context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
            modificarInfoR_ReformaTipoSa(lovNormasLaborales.size());
            context.execute("NormaLaboralNormaLaboralDialogo.show()");
            idNormaLaboral = -1;
        }
    }

    public void listaValoresLegislacionLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idLegislacionLaboral == 0) {
            context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
            modificarInfoR_ContratoLL(lovContratos.size());
            context.execute("ContratoLegislacionLaboralDialogo.show()");
            idLegislacionLaboral = -1;
        }
    }

    public void listaValoresUbicacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idUbicacionGeografica == 0) {
            getLovUbicacionesGeograficas();
            context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
            modificarInfoR_UbicacionUb(lovUbicacionesGeograficas.size());
            RequestContext.getCurrentInstance().update("formLovs:formDUbicacion:infoRegistroUbicacionUbicacion");
            context.execute("UbicacionUbicacionGeograficaDialogo.show()");
            idUbicacionGeografica = -1;
        }
    }

    public void listaValoresJornadaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idJornadaLaboral == 0) {
            context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
            modificarInfoR_JornadaL(lovJornadasLaborales.size());
            context.execute("JornadaJornadaLaboralDialogo.show()");
            idJornadaLaboral = -1;
        }
    }

    public void listaValoresFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idFormaPago == 0) {
            context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
            modificarInfoR_PeriodFormaPago(lovPeriodicidades.size());
            context.execute("PeriodicidadFormaPagoDialogo.show()");
            idFormaPago = -1;
        }
        if (idFormaPago == 3) {
            context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
            modificarInfoR_SucursalFormaP(lovSucursales.size());
            context.execute("SucursalFormaPagoDialogo.show()");
            idFormaPago = -1;
        }
        if (idFormaPago == 4) {
            context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
            modificarInfoR_EmpresaInfoP(lovMetodosPagos.size());
            context.execute("MetodoPagoFormaPagoDialogo.show()");
            idFormaPago = -1;
        }
    }

    public void listaValoresAfiliacionEPS() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idAfiliacionEPS == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionARP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idAfiliacionARP == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionAFP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idAfiliacionAFP == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionCaja() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idAfiliacionCaja == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresAfiliacionFondo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idAfiliacionFondo == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
            modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
            context.execute("TerceroAfiliacionDialogo.show()");
        }
    }

    public void listaValoresEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idEstadoCivil == 0) {
            getLovTercerosSucursales();
            context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
            modificarInfoR_EstadoCivil(lovEstadosCiviles.size());
            context.execute("EstadoCivilEstadoCivilDialogo.show()");
            idEstadoCivil = -1;
        }
    }

    public void listaValoresDireccion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idDireccion == 1) {
            getLovTercerosSucursales();
            context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
            modificarInfoR_CiudadDir(lovCiudades.size());
            context.execute("CiudadDireccionDialogo.show()");
            idDireccion = -1;
        }
    }

    public void listaValoresTelefono() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idTelefono == 0) {
            context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
            modificarInfoR_TipoTelT(lovTiposTelefonos.size());
            context.execute("TipoTelefonoTelefonoDialogo.show()");
            idTelefono = -1;
        }
        if (idTelefono == 1) {
            context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
            modificarInfoR_CiudadTel(lovCiudades.size());
            context.execute("CiudadTelefonoDialogo.show()");
            idTelefono = -1;
        }
    }

    public void botonListaValores() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idInformacionPersonal >= 0) {
            if (idInformacionPersonal == 0) {
                context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
                modificarInfoR_EmpresaInfoP(lovEmpresas.size());
                context.execute("EmpresaInformacionPersonalDialogo.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 5) {
                getLovTiposDocumentos();
                context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
                modificarInfoR_TipoDocInfoP(lovTiposDocumentos.size());
                context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 7) {
                getLovCiudades();
                context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
                modificarInfoR_CiudadDocInfoP(lovCiudades.size());
                context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 9) {
                getLovCiudades();
                context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
                modificarInfoR_CiudadNacInfoP(lovCiudades.size());
                context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
                idInformacionPersonal = -1;
            }
        } else if (idCargoDesempeñado >= 0) {
            if (idCargoDesempeñado == 0) {
                getLovCargos();
                context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
                modificarInfoR_CargoD(lovCargos.size());
                context.execute("CargoCargoDesempeñadoDialogo.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 1) {
                getLovMotivosCambiosCargos();
                context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
                modificarInfoR_MotivoCargoD(lovMotivosCambiosCargos.size());
                context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 2) {
                getLovEstructuras();
                context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
                modificarInfoR_EstrCargoD(lovEstructuras.size());
                context.execute("EstructuraCargoDesempeñadoDialogo.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 3) {
                getLovPapeles();
                context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
                modificarInfoR_PapelCargoD(lovPapeles.size());
                context.execute("PapelCargoDesempeñadoDialogo.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 4) {
                getLovEmpleados();
                context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
                modificarInfoR_JefeCargoD(lovEmpleados.size());
                context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                idCargoDesempeñado = -1;
            }
        } else if (idCentroCosto >= 0) {
            if (idCentroCosto == 0) {
                getLovMotivosLocalizaciones();
                context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
                modificarInfoR_MotivoCentroC(lovMotivosLocalizaciones.size());
                context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
                idCentroCosto = -1;
            }
            if (idCentroCosto == 1) {
                getLovEstructurasCentroCosto();
                context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
                modificarInfoR_EstrCentroC(lovEstructurasCentroCosto.size());
                context.execute("EstructuraCentroCostoDialogo.show()");
                idCentroCosto = -1;
            }
        } else if (idTipoTrabajador >= 0) {
            if (idTipoTrabajador == 0) {
                getLovTiposTrabajadores();
                context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
                modificarInfoR_TipoTraTT(lovTiposTrabajadores.size());
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                idTipoTrabajador = -1;
            }
        } else if (idTipoSalario >= 0) {
            if (idTipoSalario == 0) {
                getLovReformasLaborales();
                context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
                modificarInfoR_ReformaTipoSa(lovReformasLaborales.size());
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                idTipoSalario = -1;
            }
        } else if (idSueldo >= 0) {
            if (idSueldo == 1) {
                getLovMotivosCambiosSueldos();
                context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
                modificarInfoR_MotivoSu(lovMotivosCambiosSueldos.size());
                context.execute("MotivoCambioSueldoSueldoDialogo.show()");
                idSueldo = -1;
            }
            if (idSueldo == 2) {
                getLovTiposSueldos();
                context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
                modificarInfoR_TipoSuSu(lovTiposSueldos.size());
                context.execute("TipoSueldoSueldoDialogo.show()");
                idSueldo = -1;
            }
            if (idSueldo == 3) {
                getLovUnidades();
                context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
                modificarInfoR_UnidadSu(lovUnidades.size());
                context.execute("UnidadSueldoDialogo.show()");
                idSueldo = -1;
            }
        } else if (idTipoContrato >= 0) {
            if (idTipoContrato == 0) {
                getLovMotivosContratos();
                context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
                modificarInfoR_MotivoTipoCo(lovMotivosContratos.size());
                context.execute("MotivoContratoTipoContratoDialogo.show()");
                idTipoContrato = -1;
            }
            if (idTipoContrato == 1) {
                getLovTiposContratos();
                context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
                modificarInfoR_TipoContrato(lovTiposContratos.size());
                context.execute("TipoContratoTipoContratoDialogo.show()");
                idTipoContrato = -1;
            }
        } else if (idNormaLaboral >= 0) {
            if (idNormaLaboral == 0) {
                getLovNormasLaborales();
                context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
                modificarInfoR_NormaL(lovNormasLaborales.size());
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                idNormaLaboral = -1;
            }
        } else if (idLegislacionLaboral >= 0) {
            if (idLegislacionLaboral == 0) {
                getLovContratos();
                context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
                modificarInfoR_ContratoLL(lovContratos.size());
                context.execute("ContratoLegislacionLaboralDialogo.show()");
                idLegislacionLaboral = -1;
            }
        } else if (idUbicacionGeografica >= 0) {
            if (idUbicacionGeografica == 0) {
                getLovUbicacionesGeograficas();
                context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
                modificarInfoR_UbicacionUb(lovUbicacionesGeograficas.size());
                context.execute("UbicacionUbicacionGeograficaDialogo.show()");
                idUbicacionGeografica = -1;
            }
        } else if (idJornadaLaboral >= 0) {
            if (idJornadaLaboral == 0) {
                getLovJornadasLaborales();
                context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
                modificarInfoR_JornadaL(lovJornadasLaborales.size());
                context.execute("JornadaJornadaLaboralDialogo.show()");
                idJornadaLaboral = -1;
            }
        } else if (idFormaPago >= 0) {
            if (idFormaPago == 0) {
                getLovPeriodicidades();
                context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
                modificarInfoR_PeriodFormaPago(lovPeriodicidades.size());
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                idFormaPago = -1;
            }
            if (idFormaPago == 3) {
                getLovSucursales();
                context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
                modificarInfoR_SucursalFormaP(lovSucursales.size());
                context.execute("SucursalFormaPagoDialogo.show()");
                idFormaPago = -1;
            }
            if (idFormaPago == 4) {
                getLovMetodosPagos();
                context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
                modificarInfoR_MetodoFormaP(lovMetodosPagos.size());
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                idFormaPago = -1;
            }
        } else if (idAfiliacionAFP >= 0) {
            if (idAfiliacionAFP == 0) {
                getLovTercerosSucursales();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (idAfiliacionARP >= 0) {
            if (idAfiliacionARP == 0) {
                getLovTercerosSucursales();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (idAfiliacionCaja >= 0) {
            if (idAfiliacionCaja == 0) {
                getLovTercerosSucursales();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (idAfiliacionEPS >= 0) {
            if (idAfiliacionEPS == 0) {
                getLovTercerosSucursales();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (idAfiliacionFondo >= 0) {
            if (idAfiliacionFondo == 0) {
                getLovTercerosSucursales();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
            }
        } else if (idEstadoCivil >= 0) {
            if (idEstadoCivil == 0) {
                getLovEstadosCiviles();
                context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
                modificarInfoR_EstadoCivil(lovEstadosCiviles.size());
                context.execute("EstadoCivilEstadoCivilDialogo.show()");
                idEstadoCivil = -1;
            }
        } else if (idDireccion >= 0) {
            if (idDireccion == 1) {
                getLovCiudades();
                context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
                modificarInfoR_CiudadDir(lovCiudades.size());
                context.execute("CiudadDireccionDialogo.show()");
                idDireccion = -1;
            }
        } else if (idTelefono >= 0) {
            if (idTelefono == 0) {
                getLovTiposTelefonos();
                context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
                modificarInfoR_TipoTelT(lovTiposTelefonos.size());
                context.execute("TipoTelefonoTelefonoDialogo.show()");
                idTelefono = -1;
            }
            if (idTelefono == 1) {
                getLovCiudades();
                context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
                modificarInfoR_CiudadTel(lovCiudades.size());
                context.execute("CiudadTelefonoDialogo.show()");
                idTelefono = -1;
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (idInformacionPersonal >= 0) {
            if (idInformacionPersonal == 0) {
                context.update("formDialogos:editarEmpresaInformacionPersonal");
                context.execute("editarEmpresaInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 1) {
                context.update("formDialogos:editarPrimerApellidoInformacionPersonal");
                context.execute("editarPrimerApellidoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 2) {
                context.update("formDialogos:editarSegundoApellidoInformacionPersonal");
                context.execute("editarSegundoApellidoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 3) {
                context.update("formDialogos:editarNombresInformacionPersonal");
                context.execute("editarNombresInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 5) {
                context.update("formDialogos:editarTipoDocumentoInformacionPersonal");
                context.execute("editarTipoDocumentoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 6) {
                context.update("formDialogos:editarNumeroDocumentoInformacionPersonal");
                context.execute("editarNumeroDocumentoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 7) {
                context.update("formDialogos:editarCiudadDocumentoInformacionPersonal");
                context.execute("editarCiudadDocumentoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 8) {
                context.update("formDialogos:editarFechaNacimientoInformacionPersonal");
                context.execute("editarFechaNacimientoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 9) {
                context.update("formDialogos:editarCiudadNacimientoInformacionPersonal");
                context.execute("editarCiudadNacimientoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 10) {
                context.update("formDialogos:editarFechaIngresoInformacionPersonal");
                context.execute("editarFechaIngresoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 11) {
                context.update("formDialogos:editarCodigoEmpleadoInformacionPersonal");
                context.execute("editarCodigoEmpleadoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 12) {
                context.update("formDialogos:editarEmailInformacionPersonal");
                context.execute("editarEmailInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
            if (idInformacionPersonal == 13) {
                context.update("formDialogos:editarFechaUltimoPagoInformacionPersonal");
                context.execute("editarFechaUltimoPagoInformacionPersonal.show()");
                idInformacionPersonal = -1;
            }
        }
        if (idCargoDesempeñado >= 0) {
            if (idCargoDesempeñado == 0) {
                context.update("formDialogos:editarCargoCargoDesempeñado");
                context.execute("editarCargoCargoDesempeñado.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 1) {
                context.update("formDialogos:editarMotivoCargoDesempeñado");
                context.execute("editarMotivoCargoDesempeñado.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 2) {
                context.update("formDialogos:editarEstructuraCargoDesempeñado");
                context.execute("editarEstructuraCargoDesempeñado.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 3) {
                context.update("formDialogos:editarPapelCargoDesempeñado");
                context.execute("editarPapelCargoDesempeñado.show()");
                idCargoDesempeñado = -1;
            }
            if (idCargoDesempeñado == 4) {
                context.update("formDialogos:editarEmpleadoJefeCargoDesempeñado");
                context.execute("editarEmpleadoJefeCargoDesempeñado.show()");
                idCargoDesempeñado = -1;
            }
        }
        if (idCentroCosto >= 0) {
            if (idCentroCosto == 0) {
                context.update("formDialogos:editarEstructuraCentroCosto");
                context.execute("editarEstructuraCentroCosto.show()");
                idCentroCosto = -1;
            }
            if (idCentroCosto == 1) {
                context.update("formDialogos:editarMotivoCentroCosto");
                context.execute("editarMotivoCentroCosto.show()");
                idCentroCosto = -1;
            }
        }
        if (idTipoTrabajador >= 0) {
            if (idTipoTrabajador == 0) {
                context.update("formDialogos:editarTrabajadorTipoTrabajador");
                context.execute("editarTrabajadorTipoTrabajador.show()");
                idTipoTrabajador = -1;
            }
        }
        if (idTipoSalario >= 0) {
            if (idTipoSalario == 0) {
                context.update("formDialogos:editarReformaTipoSalario");
                context.execute("editarReformaTipoSalario.show()");
                idTipoSalario = -1;
            }
        }
        if (idSueldo >= 0) {
            if (idSueldo == 0) {
                context.update("formDialogos:editarValorSueldo");
                context.execute("editarValorSueldo.show()");
                idSueldo = -1;
            }
            if (idSueldo == 1) {
                context.update("formDialogos:editarMotivoSueldo");
                context.execute("editarMotivoSueldo.show()");
                idSueldo = -1;
            }
            if (idSueldo == 2) {
                context.update("formDialogos:editarTipoSueldoSueldo");
                context.execute("editarTipoSueldoSueldo.show()");
                idSueldo = -1;
            }
            if (idSueldo == 3) {
                context.update("formDialogos:editarUnidadSueldo");
                context.execute("editarUnidadSueldo.show()");
                idSueldo = -1;
            }
        }
        if (idTipoContrato >= 0) {
            if (idTipoContrato == 0) {
                context.update("formDialogos:editarMotivoTipoContrato");
                context.execute("editarMotivoTipoContrato.show()");
                idTipoContrato = -1;
            }
            if (idTipoContrato == 1) {
                context.update("formDialogos:editarTipoTipoContrato");
                context.execute("editarTipoTipoContrato.show()");
                idTipoContrato = -1;
            }
            if (idTipoContrato == 2) {
                context.update("formDialogos:editarFechaTipoContrato");
                context.execute("editarFechaTipoContrato.show()");
                idTipoContrato = -1;
            }
        }
        if (idNormaLaboral >= 0) {
            if (idNormaLaboral == 0) {
                context.update("formDialogos:editarNormaNormaLaboral");
                context.execute("editarNormaNormaLaboral.show()");
                idNormaLaboral = -1;
            }
        }
        if (idLegislacionLaboral >= 0) {
            if (idLegislacionLaboral == 0) {
                context.update("formDialogos:editarContratoLegislacionLaboral");
                context.execute("editarContratoLegislacionLaboral.show()");
                idLegislacionLaboral = -1;
            }
        }
        if (idUbicacionGeografica >= 0) {
            if (idUbicacionGeografica == 0) {
                context.update("formDialogos:editarUbicacionUbicacionGeografica");
                context.execute("editarUbicacionUbicacionGeografica.show()");
                idUbicacionGeografica = -1;
            }
        }
        if (idJornadaLaboral >= 0) {
            if (idJornadaLaboral == 0) {
                context.update("formDialogos:editarJornadaJornadaLaboral");
                context.execute("editarJornadaJornadaLaboral.show()");
                idJornadaLaboral = -1;
            }
        }
        if (idFormaPago >= 0) {
            if (idFormaPago == 0) {
                context.update("formDialogos:editarPeriodicidadFormaPago");
                context.execute("editarPeriodicidadFormaPago.show()");
                idFormaPago = -1;
            }
            if (idFormaPago == 1) {
                context.update("formDialogos:editarCuentaFormaPago");
                context.execute("editarCuentaFormaPago.show()");
                idFormaPago = -1;
            }
            if (idFormaPago == 3) {
                context.update("formDialogos:editarSucursalFormaPago");
                context.execute("editarSucursalFormaPago.show()");
                idFormaPago = -1;
            }
            if (idFormaPago == 4) {
                context.update("formDialogos:editarMetodoFormaPago");
                context.execute("editarMetodoFormaPago.show()");
                idFormaPago = -1;
            }
        }
        if (idAfiliacionAFP >= 0) {
            if (idAfiliacionAFP == 0) {
                context.update("formDialogos:editarAFPAfiliacion");
                context.execute("editarAFPAfiliacion.show()");
                idAfiliacionAFP = -1;
            }
        }
        if (idAfiliacionARP >= 0) {
            if (idAfiliacionARP == 0) {
                context.update("formDialogos:editarARPAfiliacion");
                context.execute("editarARPAfiliacion.show()");
                idAfiliacionARP = -1;
            }
        }
        if (idAfiliacionCaja >= 0) {
            if (idAfiliacionCaja == 0) {
                context.update("formDialogos:editarCajaAfiliacion");
                context.execute("editarCajaAfiliacion.show()");
                idAfiliacionCaja = -1;
            }
        }
        if (idAfiliacionEPS >= 0) {
            if (idAfiliacionEPS == 0) {
                context.update("formDialogos:editarEPSAfiliacion");
                context.execute("editarEPSAfiliacion.show()");
                idAfiliacionEPS = -1;
            }
        }
        if (idAfiliacionFondo >= 0) {
            if (idAfiliacionFondo == 0) {
                context.update("formDialogos:editarFondoAfiliacion");
                context.execute("editarFondoAfiliacion.show()");
                idAfiliacionFondo = -1;
            }
        }
        if (idEstadoCivil >= 0) {
            if (idEstadoCivil == 0) {
                context.update("formDialogos:editarEstadoEstadoCivil");
                context.execute("editarEstadoEstadoCivil.show()");
                idEstadoCivil = -1;
            }
        }
        if (idDireccion >= 0) {
            if (idDireccion == 0) {
                context.update("formDialogos:editarDireccionDireccion");
                context.execute("editarDireccionDireccion.show()");
                idDireccion = -1;
            }
            if (idDireccion == 1) {
                context.update("formDialogos:editarCiudadDireccion");
                context.execute("editarCiudadDireccion.show()");
                idDireccion = -1;
            }
        }
        if (idTelefono >= 0) {
            if (idTelefono == 0) {
                context.update("formDialogos:editarTipoTelefonoTelefono");
                context.execute("editarTipoTelefonoTelefono.show()");
                idTelefono = -1;
            }
            if (idTelefono == 1) {
                context.update("formDialogos:editarCiudadTelefono");
                context.execute("editarCiudadTelefono.show()");
                idTelefono = -1;
            }
            if (idTelefono == 2) {
                context.update("formDialogos:editarNumeroTelTelefono");
                context.execute("editarNumeroTelTelefono.show()");
                idTelefono = -1;
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
        System.out.println("disableNombreEstructuraCargo es true");
        disableDescripcionEstructura = true;
        disableUbicacionGeografica = true;
        disableAfiliaciones = true;
        disableCamposDependientesTipoT = true;
        //Index
        idCargoDesempeñado = -1;
        idCentroCosto = -1;
        idTipoTrabajador = -1;
        idTipoSalario = -1;
        idSueldo = -1;
        idTipoContrato = -1;
        idNormaLaboral = -1;
        idLegislacionLaboral = -1;
        idUbicacionGeografica = -1;
        idFormaPago = -1;
        idJornadaLaboral = -1;
        idAfiliacionAFP = -1;
        idAfiliacionARP = -1;
        idAfiliacionCaja = -1;
        idAfiliacionEPS = -1;
        idAfiliacionFondo = -1;
        idEstadoCivil = -1;
        idDireccion = -1;
        idTelefono = -1;
        idInformacionPersonal = -1;
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

        lovEmpresas = null;
        lovEmpresas = null;
        lovTiposDocumentos = null;
        lovCiudades = null;
        lovCargos = null;
        lovMotivosCambiosCargos = null;
        lovEstructuras = null;
        lovPapeles = null;
        lovEmpleados = null;
        lovMotivosLocalizaciones = null;
        lovEstructurasCentroCosto = null;
        lovTiposTrabajadores = null;
        lovReformasLaborales = null;
        lovMotivosCambiosSueldos = null;
        lovTiposSueldos = null;
        lovUnidades = null;
        lovMotivosContratos = null;
        lovTiposContratos = null;
        lovNormasLaborales = null;
        lovContratos = null;
        lovUbicacionesGeograficas = null;
        lovPeriodicidades = null;
        lovSucursales = null;
        lovMetodosPagos = null;
        lovJornadasLaborales = null;
        lovTercerosSucursales = null;
        lovEstadosCiviles = null;
        lovTiposTelefonos = null;

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
                    nuevaVigenciaSueldo.setUnidadpago(unidadPesos);
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
                        nuevaDireccion.setTipoppal(".");
                        nuevaDireccion.setTiposecundario(".");
                        nuevaDireccion.setPersona(empleadoAlmacenado.getPersona());
                        nuevaDireccion.setFechavigencia(fechaIngreso);
                        //nuevaDireccion.setPpal(".");
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

    public void cambiarItemInformacionPersonal(int i) {
        System.out.println("cambiarItemInformacionPersonal : " + i);
        if (permitirIndexInformacionPersonal == true) {
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idInformacionPersonal = i;

            auxFechaCorte = fechaCorte;
            auxFechaIngreso = fechaIngreso;
            auxFechaNacimiento = nuevaPersona.getFechanacimiento();
            if (idInformacionPersonal == 0) {
                if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
                    auxInformacionPersonaEmpresal = nuevoEmpleado.getEmpresa().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (idInformacionPersonal == 5) {
                if (nuevaPersona.getTipodocumento().getSecuencia() != null) {
                    auxInformacionPersonalTipoDocumento = nuevaPersona.getTipodocumento().getNombrelargo();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (idInformacionPersonal == 7) {
                if (nuevaPersona.getCiudaddocumento().getSecuencia() != null) {
                    auxInformacionPersonalCiudadDocumento = nuevaPersona.getCiudaddocumento().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }
            if (idInformacionPersonal == 9) {
                if (nuevaPersona.getCiudadnacimiento().getSecuencia() != null) {
                    auxInformacionPersonalCiudadNacimiento = nuevaPersona.getCiudadnacimiento().getNombre();
                } else {
                    listaValoresInformacionPersonal();
                }
            }

        }
    }

    public void cambiarItemCargoDesempeñado(int itemCargoD) {
        if (permitirIndexCargoDesempeñado == true) {
            idInformacionPersonal = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idCargoDesempeñado = itemCargoD;
            auxCargoDesempeñadoPapel = nuevaVigenciaCargo.getPapel().getDescripcion();
            auxCargoDesempeñadoEmpleadoJefe = nuevaVigenciaCargo.getEmpleadojefe().getPersona().getNombreCompleto();
            if (idCargoDesempeñado == 0) {
                if (nuevaVigenciaCargo.getCargo().getSecuencia() != null) {
                    auxCargoDesempeñadoNombreCargo = nuevaVigenciaCargo.getCargo().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }
            if (idCargoDesempeñado == 1) {
                if (nuevaVigenciaCargo.getMotivocambiocargo().getSecuencia() != null) {
                    auxCargoDesempeñadoMotivoCargo = nuevaVigenciaCargo.getMotivocambiocargo().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }
            if (idCargoDesempeñado == 2) {
                if (nuevaVigenciaCargo.getEstructura().getSecuencia() != null) {
                    auxCargoDesempeñadoNombreEstructura = nuevaVigenciaCargo.getEstructura().getNombre();
                } else {
                    listaValoresCargoDesempenado();
                }
            }

        }
    }

    public void cambiarItemCentroCosto(int i) {
        if (permitirIndexCentroCosto == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idCentroCosto = i;
            if (idCentroCosto == 0) {
                if (nuevaVigenciaLocalizacion.getMotivo().getSecuencia() != null) {
                    auxCentroCostoMotivo = nuevaVigenciaLocalizacion.getMotivo().getDescripcion();
                } else {
                    listaValoresCentroCosto();
                }
            }
            if (idCentroCosto == 1) {
                if (nuevaVigenciaLocalizacion.getLocalizacion().getSecuencia() != null) {
                    auxCentroCostoEstructura = nuevaVigenciaLocalizacion.getLocalizacion().getCentrocosto().getNombre();
                } else {
                    listaValoresCentroCosto();
                }
            }
        }
    }

    public void cambiarItemTipoTrabajador(int i) {
        if (permitirIndexTipoTrabajador == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idTipoTrabajador = i;
            if (idTipoTrabajador == 0) {
                if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
                    auxTipoTrabajadorNombreTipoTrabajador = nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre();
                } else {
                    listaValoresTipoTrabajador();
                }
            }
        }
    }

    public void cambiarItemReformaLaboral(int i) {
        if (permitirIndexTipoSalario == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idTipoSalario = i;
            if (idTipoSalario == 0) {
                if (nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia() != null) {
                    auxTipoSalarioReformaLaboral = nuevaVigenciaReformaLaboral.getReformalaboral().getNombre();
                } else {
                    listaValoresTipoSalario();
                }
            }
        }
    }

    public void cambiarItemSueldo(int i) {
        if (permitirIndexSueldo == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idSueldo = i;
            auxSueldoValor = valorSueldo;
            if (idSueldo == 1) {
                if (nuevaVigenciaSueldo.getMotivocambiosueldo().getSecuencia() != null) {
                    auxSueldoMotivoSueldo = nuevaVigenciaSueldo.getMotivocambiosueldo().getNombre();
                } else {
                    listaValoresSueldo();
                }
            }
            if (idSueldo == 2) {
                if (nuevaVigenciaSueldo.getTiposueldo().getSecuencia() != null) {
                    auxSueldoTipoSueldo = nuevaVigenciaSueldo.getTiposueldo().getDescripcion();
                } else {
                    listaValoresSueldo();
                }
            }
            if (idSueldo == 3) {
                if (nuevaVigenciaSueldo.getUnidadpago().getSecuencia() != null) {
                    auxSueldoUnidad = nuevaVigenciaSueldo.getUnidadpago().getNombre();
                } else {
                    listaValoresSueldo();
                }
            }
        }
    }

    public void cambiarItemTipoContrato(int i) {
        if (permitirIndexTipoContrato == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idTipoContrato = i;
            auxTipoContratoFecha = nuevaVigenciaTipoContrato.getFechavigencia();
            if (idTipoContrato == 1) {
                if (nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia() != null) {
                    auxTipoContratoMotivo = nuevaVigenciaTipoContrato.getTipocontrato().getNombre();
                } else {
                    listaValoresTipoContrato();
                }
            }
            if (idTipoContrato == 0) {
                if (nuevaVigenciaTipoContrato.getMotivocontrato().getSecuencia() != null) {
                    auxTipoContratoTipoContrato = nuevaVigenciaTipoContrato.getMotivocontrato().getNombre();
                } else {
                    listaValoresTipoContrato();
                }
            }
        }
    }

    public void cambiarItemNormaLaboral(int i) {
        if (permitirIndexNormaLaboral == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idNormaLaboral = i;
            if (idNormaLaboral == 0) {
                if (nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia() != null) {
                    auxNormaLaboralNorma = nuevaVigenciaNormaEmpleado.getNormalaboral().getNombre();
                } else {
                    listaValoresNormaLaboral();
                }
            }
        }
    }

    public void cambiarItemLegislacionLaboral(int i) {
        if (permitirIndexLegislacionLaboral == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idLegislacionLaboral = i;
            if (idLegislacionLaboral == 0) {
                if (nuevaVigenciaContrato.getContrato().getSecuencia() != null) {
                    auxLegislacionLaboralNombre = nuevaVigenciaContrato.getContrato().getDescripcion();
                } else {
                    listaValoresLegislacionLaboral();
                }
            }
        }
    }

    public void cambiarItemUbicacionGeografica(int i) {
        if (permitirIndexUbicacionGeografica == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idUbicacionGeografica = i;
            if (idUbicacionGeografica == 0) {
                if (nuevaVigenciaUbicacion.getUbicacion().getSecuencia() != null) {
                    auxUbicacionGeograficaUbicacion = nuevaVigenciaUbicacion.getUbicacion().getDescripcion();
                } else {
                    listaValoresUbicacion();
                }
            }
        }
    }

    public void cambiarItemFormaPago(int i) {
        if (permitirIndexFormaPago == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idJornadaLaboral = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idFormaPago = i;
            if (idFormaPago == 0) {
                if (nuevaVigenciaFormaPago.getFormapago().getSecuencia() != null) {
                    auxFormaPagoPeriodicidad = nuevaVigenciaFormaPago.getFormapago().getNombre();
                } else {
                    listaValoresFormaPago();
                }
            }
            if (idFormaPago == 3) {
                if (nuevaVigenciaFormaPago.getSucursal().getSecuencia() != null) {
                    auxFormaPagoSucursal = nuevaVigenciaFormaPago.getSucursal().getNombre();
                } else {
                    listaValoresFormaPago();
                }
            }
            if (idFormaPago == 4) {
                if (nuevaVigenciaFormaPago.getMetodopago().getSecuencia() != null) {
                    auxFormaPagoMetodo = nuevaVigenciaFormaPago.getMetodopago().getDescripcion();
                } else {
                    listaValoresFormaPago();
                }
            }
        }
    }

    public void cambiarItemJornadaLaboral(int i) {
        if (permitirIndexJornadaLaboral == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idJornadaLaboral = i;
            if (idJornadaLaboral == 0) {
                if (nuevaVigenciaJornada.getJornadatrabajo().getSecuencia() != null) {
                    auxJornadaLaboralJornada = nuevaVigenciaJornada.getJornadatrabajo().getDescripcion();
                } else {
                    listaValoresJornadaLaboral();
                }
            }
        }
    }

    public void cambiarItemAfiliacionFondo(int i) {
        if (permitirIndexAfiliacionFondo == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idAfiliacionEPS = -1;
            idJornadaLaboral = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idAfiliacionFondo = i;

            auxAfiliacionFondo = nuevaVigenciaAfiliacionFondo.getTercerosucursal().getDescripcion();

        }
    }

    public void cambiarItemAfiliacionEPS(int i) {
        if (permitirIndexAfiliacionEPS == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idAfiliacionCaja = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idAfiliacionEPS = i;
            if (idAfiliacionEPS == 0) {
                if (nuevaVigenciaAfiliacionEPS.getTercerosucursal().getSecuencia() != null) {
                    auxAfiliacionEPS = nuevaVigenciaAfiliacionEPS.getTercerosucursal().getDescripcion();
                } else {
                    listaValoresAfiliacionEPS();
                }
            }
        }
    }

    public void cambiarItemAfiliacionCaja(int i) {
        if (permitirIndexAfiliacionCaja == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idAfiliacionAFP = -1;
            idAfiliacionARP = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idAfiliacionCaja = i;
            auxAfiliacionCaja = nuevaVigenciaAfiliacionCaja.getTercerosucursal().getDescripcion();
        }
    }

    public void cambiarItemAfiliacionARP(int i) {
        if (permitirIndexAfiliacionARP == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idAfiliacionAFP = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idAfiliacionCaja = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idAfiliacionARP = i;
            if (idAfiliacionARP == 0) {
                if (nuevaVigenciaAfiliacionARP.getTercerosucursal().getSecuencia() != null) {
                    auxAfiliacionARP = nuevaVigenciaAfiliacionARP.getTercerosucursal().getDescripcion();
                } else {
                    listaValoresAfiliacionARP();
                }
            }
        }
    }

    public void cambiarItemAfiliacionAFP(int i) {
        if (permitirIndexAfiliacionAFP == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idAfiliacionCaja = -1;
            idAfiliacionARP = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = -1;
            idAfiliacionAFP = i;
            auxAfiliacionAFP = nuevaVigenciaAfiliacionAFP.getTercerosucursal().getDescripcion();
        }
    }

    public void cambiarItemEstadoCivil(int i) {
        if (permitirIndexEstadoCivil == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idAfiliacionCaja = -1;
            idAfiliacionARP = -1;
            idAfiliacionAFP = -1;
            idDireccion = -1;
            idTelefono = -1;
            idEstadoCivil = i;
            auxEstadoCivilEstado = nuevoEstadoCivil.getEstadocivil().getDescripcion();
        }
    }

    public void cambiarItemDireccion(int i) {
        if (permitirIndexDireccion == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idAfiliacionCaja = -1;
            idAfiliacionARP = -1;
            idAfiliacionAFP = -1;
            idEstadoCivil = -1;
            idTelefono = -1;
            idDireccion = i;
            auxDireccionCiudad = nuevaDireccion.getCiudad().getNombre();
        }
    }

    public void cambiarItemTelefono(int i) {
        if (permitirIndexTelefono == true) {
            idInformacionPersonal = -1;
            idCargoDesempeñado = -1;
            idCentroCosto = -1;
            idTipoTrabajador = -1;
            idTipoSalario = -1;
            idSueldo = -1;
            idTipoContrato = -1;
            idNormaLaboral = -1;
            idLegislacionLaboral = -1;
            idUbicacionGeografica = -1;
            idFormaPago = -1;
            idJornadaLaboral = -1;
            idAfiliacionFondo = -1;
            idAfiliacionEPS = -1;
            idAfiliacionCaja = -1;
            idAfiliacionARP = -1;
            idAfiliacionAFP = -1;
            idEstadoCivil = -1;
            idDireccion = -1;
            idTelefono = i;
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
        System.out.println("fecha Nacimiento : " + nuevaPersona.getFechanacimiento());
        boolean retorno = validarFechasInformacionPersonal(8);
        if (retorno == true) {
            cambiarItemInformacionPersonal(i);
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    public void arreglarFecha() {
        FacesContext c = FacesContext.getCurrentInstance();
        Calendar calend;
        Object obj;
        obj = c.getViewRoot().findComponent("form:fechaIngresoModPersonal");
        calend = (Calendar) obj;
        System.out.println("calend" + calend);
    }

    public void modificarFechaIngresoInformacionPersonal(int i) {
        System.out.println("fechaIngreso : " + fechaIngreso);
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = validarFechasInformacionPersonal(10);
        System.out.println("retorno : " + retorno);
        if (retorno == true) {
            //   getFechaDesdeParametro();
            //   getFechaHastaParametro();
            if (fechaIngreso != null) {
                cambiarItemInformacionPersonal(i);
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
            cambiarItemInformacionPersonal(i);
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
        cambiarItemInformacionPersonal(posicion);
    }

    public void modificarFechaFechaVigenciaTipoContrato(int i) {
        boolean retorno = validarFechasTipoContrato();
        if (retorno == true) {
            if (fechaIngreso != null && nuevaVigenciaTipoContrato.getFechavigencia() != null) {
                if (nuevaVigenciaTipoContrato.getFechavigencia().before(fechaIngreso)) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorFechaContratoFechaIngreso.show()");
                } else {
                    cambiarItemTipoContrato(i);
                }
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorFechas.show()");
        }
    }

    public void modificarInformacionPersonal(int indice, String confirmarCambio, String valorConfirmar) {
        idInformacionPersonal = indice;
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
                context.update("form:empresaModPersonal");
                calcularControlEmpleadosEmpresa();
                modificacionesEmpresaFechaIngresoInformacionPersonal();
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroEmpresaInfoPersonal();
                context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
                modificarInfoR_EmpresaInfoP(lovEmpresas.size());
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
                context.update("form:tipoDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroTipoDocInfoPersonal();
                context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
                modificarInfoR_TipoDocInfoP(lovTiposDocumentos.size());
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
                context.update("form:ciudadNacimientoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroCiudadNacInfoPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
                modificarInfoR_CiudadNacInfoP(lovCiudades.size());
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
                context.update("form:ciudadDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                getInfoRegistroCiudadDocInfoPersonal();
                context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
                modificarInfoR_CiudadDocInfoP(lovCiudades.size());
                context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
                context.update("form:ciudadDocumentoModPersonal");
            }
        }
    }

    public void modificarCargoDesempeñado(int itemCargoD, String confirmarCambio, String valorConfirmar) {
        idCargoDesempeñado = itemCargoD;
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
                context.update("form:cargoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
                modificarInfoR_CargoD(lovCargos.size());
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
                context.update("form:motivoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroMotivoCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
                modificarInfoR_MotivoCargoD(lovMotivosCambiosCargos.size());
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
                context.update("form:estructuraModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getInfoRegistroEstructuraCargoDesempenado();
                context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
                modificarInfoR_EstrCargoD(lovEstructuras.size());
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
                    context.update("form:papelModCargoDesempeñado");
                } else {
                    permitirIndexCargoDesempeñado = false;
                    getInfoRegistroPapelCargoDesempenado();
                    context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
                    modificarInfoR_PapelCargoD(lovPapeles.size());
                    context.execute("PapelCargoDesempeñadoDialogo.show()");
                    context.update("form:papelModCargoDesempeñado");
                }
            } else {
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
                    context.update("form:empleadoJefeModCargoDesempeñado");
                } else {
                    permitirIndexCargoDesempeñado = false;
                    getInfoRegistroJefeCargoDesempenado();
                    context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
                    modificarInfoR_JefeCargoD(lovEmpleados.size());
                    context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                    context.update("form:empleadoJefeModCargoDesempeñado");
                }
            } else {
                nuevaVigenciaCargo.setEmpleadojefe(new Empleados());
                nuevaVigenciaCargo.getEmpleadojefe().setPersona(new Personas());
                context.update("form:papelModCargoDesempeñado");
            }
        }
    }

    public void modificarCentroCosto(int indice, String confirmarCambio, String valorConfirmar) {
        idCentroCosto = indice;
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
                context.update("form:estructuraModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                getInfoRegistroEstructuraCentroCosto();
                context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
                modificarInfoR_EstrCentroC(lovEstructurasCentroCosto.size());
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
                context.update("form:motivoModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                getInfoRegistroMotivoCentroCosto();
                context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
                modificarInfoR_MotivoCentroC(lovMotivosLocalizaciones.size());
                context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
                context.update("form:motivoModCentroCosto");
            }
        }
    }

    public void modificarTipoTrabajador(int indice, String confirmarCambio, String valorConfirmar) {
        idTipoTrabajador = indice;
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
                context.update("form:tipoTrabajadorModTipoTrabajador");
                validarDisableTipoTrabajador();
            } else {
                permitirIndexTipoTrabajador = false;
                getInfoRegistroTipoTrabajadorTT();
                context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");
                modificarInfoR_TipoTraTT(lovTiposTrabajadores.size());
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                context.update("form:tipoTrabajadorModTipoTrabajador");
            }
        }
    }

    public void modificarTipoSalario(int indice, String confirmarCambio, String valorConfirmar) {
        idTipoSalario = indice;
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
                context.update("form:tipoSalarioModTipoSalario");
                validarTipoTrabajadorReformaLaboral();
            } else {
                permitirIndexTipoSalario = false;
                getInfoRegistroReformaTipoSalario();
                context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
                modificarInfoR_ReformaTipoSa(lovReformasLaborales.size());
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                context.update("form:tipoSalarioModTipoSalario");
            }
        }
    }

    public void modificarSueldo(int indice, String confirmarCambio, String valorConfirmar) {
        idTipoSalario = indice;
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
                context.update("form:motivoSueldoModSueldo");
            } else {
                permitirIndexSueldo = false;
                getInfoRegistroMotivoSueldo();
                context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
                modificarInfoR_MotivoSu(lovMotivosCambiosSueldos.size());
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
                context.update("form:motivoSueldoModSueldo");
                validarTipoTrabajadorTipoSueldo();

            } else {
                permitirIndexSueldo = false;
                getInfoRegistroTipoSueldoS();
                context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
                modificarInfoR_TipoSuSu(lovTiposSueldos.size());
                context.execute("TipoSueldoSueldoDialogo.show()");
                context.update("form:tipoSueldoModSueldo");
            }
        }
       /* if (confirmarCambio.equalsIgnoreCase("UNIDAD")) {
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
                context.update("form:unidadPagoModSueldo");
            } else {
                permitirIndexSueldo = false;
                getInfoRegistroUnidadSueldo();
                context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
                modificarInfoR_UnidadSu(lovUnidades.size());
                context.execute("UnidadSueldoDialogo.show()");
                context.update("form:unidadPagoModSueldo");
            }
        }*/
    }

    public void modificarTipoContrato(int indice, String confirmarCambio, String valorConfirmar) {
        idTipoContrato = indice;
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
                context.update("form:motivoContratoModTipoContrato");
            } else {
                permitirIndexTipoContrato = false;
                getInfoRegistroMotivoTipoContrato();
                context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
                modificarInfoR_MotivoTipoCo(lovMotivosContratos.size());
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
                context.update("form:tipoContratoModTipoContrato");
                validarTipoTrabajadorTipoContrato();
            } else {
                permitirIndexTipoContrato = false;
                getInfoRegistroTipoContrato();
                context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
                modificarInfoR_TipoContrato(lovTiposContratos.size());
                context.execute("TipoContratoTipoContratoDialogo.show()");
                context.update("form:tipoContratoModTipoContrato");
            }
        }
    }

    public void modificarNormaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        idNormaLaboral = indice;
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
                context.update("form:normaLaboralModNormaLaboral");
                validarTipoTrabajadorNormaLaboral();
            } else {
                permitirIndexNormaLaboral = false;
                getInfoRegistroNormaLaboral();
                context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
                modificarInfoR_NormaL(lovNormasLaborales.size());
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                context.update("form:normaLaboralModNormaLaboral");
            }
        }
    }

    public void modificarLegislacionLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        idLegislacionLaboral = indice;
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
                context.update("form:legislacionLaboralModLegislacionLaboral");
                validarTipoTrabajadorContrato();
            } else {
                permitirIndexLegislacionLaboral = false;
                getInfoRegistroContratoLegislacionL();
                context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
                modificarInfoR_ContratoLL(lovContratos.size());
                context.execute("ContratoLegislacionLaboralDialogo.show()");
                context.update("form:legislacionLaboralModLegislacionLaboral");
            }
        }
    }

    public void modificarUbicacionGeografica(int indice, String confirmarCambio, String valorConfirmar) {
        idUbicacionGeografica = indice;
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
                context.update("form:ubicacionGeograficaModUbicacionGeografica");
            } else {
                permitirIndexUbicacionGeografica = false;
                getInfoRegistroUbicacionUb();
                context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
                modificarInfoR_UbicacionUb(lovUbicacionesGeograficas.size());
                context.execute("UbicacionUbicacionGeograficaDialogo.show()");
                context.update("form:ubicacionGeograficaModUbicacionGeografica");
            }
        }
    }

    public void modificarJornadaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        idJornadaLaboral = indice;
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
                context.update("form:jornadaLaboralModJornadaLaboral");
            } else {
                permitirIndexJornadaLaboral = false;
                getInfoRegistroJornadaLaboral();
                context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
                modificarInfoR_JornadaL(lovJornadasLaborales.size());
                context.execute("JornadaJornadaLaboralDialogo.show()");
                context.update("form:jornadaLaboralModJornadaLaboral");
            }
        }
    }

    public void modificarFormaPago(int indice, String confirmarCambio, String valorConfirmar) {
        idFormaPago = indice;
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
                context.update("form:formaPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                getInfoRegistroFormaPago();
                context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
                modificarInfoR_PeriodFormaPago(lovPeriodicidades.size());
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
                    context.update("form:sucursalPagoModFormaPago");
                } else {
                    permitirIndexFormaPago = false;
                    getInfoRegistroSucursalFormaPago();
                    context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
                    modificarInfoR_SucursalFormaP(lovSucursales.size());
                    context.execute("SucursalFormaPagoDialogo.show()");
                    context.update("form:sucursalPagoModFormaPago");
                }
            } else {
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
                context.update("form:metodoPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                getInfoRegistroMetodoFormaPago();
                context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
                modificarInfoR_MetodoFormaP(lovMetodosPagos.size());
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                context.update("form:metodoPagoModFormaPago");
            }
        }
    }

    public void modificarAfiliacionFondo(int indice, String confirmarCambio, String valorConfirmar) {
        idAfiliacionFondo = indice;
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
                    TiposEntidades fondo = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("12"));
                    nuevaVigenciaAfiliacionFondo.setTipoentidad(fondo);
                    context.update("form:fondoCensantiasModAfiliaciones");
                } else {
                    permitirIndexAfiliacionFondo = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:fondoCensantiasModAfiliaciones");
                }
            } else {
                nuevaVigenciaAfiliacionFondo.setTercerosucursal(new TercerosSucursales());
                context.update("form:fondoCensantiasModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionEPS(int indice, String confirmarCambio, String valorConfirmar) {
        idAfiliacionEPS = indice;
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
                context.update("form:epsModAfiliaciones");
                TiposEntidades eps = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("1"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(eps);
                consultarCodigoSS();
            } else {
                permitirIndexAfiliacionEPS = false;
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
                context.update("form:epsModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionCaja(int indice, String confirmarCambio, String valorConfirmar) {
        idAfiliacionCaja = indice;
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
                    TiposEntidades caja = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("14"));
                    nuevaVigenciaAfiliacionCaja.setTipoentidad(caja);
                    context.update("form:cajaCompensacionModAfiliaciones");
                } else {
                    permitirIndexAfiliacionCaja = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:cajaCompensacionModAfiliaciones");
                }
            } else {
                nuevaVigenciaAfiliacionCaja.setTercerosucursal(new TercerosSucursales());
                context.update("form:cajaCompensacionModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionARP(int indice, String confirmarCambio, String valorConfirmar) {
        idAfiliacionARP = indice;
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
                TiposEntidades arp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("2"));
                nuevaVigenciaAfiliacionARP.setTipoentidad(arp);
                context.update("form:arpModAfiliaciones");
                consultarCodigoSC();
            } else {
                permitirIndexAfiliacionARP = false;
                getInfoRegistroTerceroAfiliacion();
                context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                context.execute("TerceroAfiliacionDialogo.show()");
                context.update("form:arpModAfiliaciones");
            }
        }
    }

    public void modificarAfiliacionAFP(int indice, String confirmarCambio, String valorConfirmar) {
        idAfiliacionAFP = indice;
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
                    TiposEntidades afp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("3"));
                    nuevaVigenciaAfiliacionAFP.setTipoentidad(afp);
                    context.update("form:afpModAfiliaciones");
                    consultarCodigoSP();
                } else {
                    permitirIndexAfiliacionAFP = false;
                    getInfoRegistroTerceroAfiliacion();
                    context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
                    modificarInfoR_TerceroAfSuc(lovTercerosSucursales.size());
                    context.execute("TerceroAfiliacionDialogo.show()");
                    context.update("form:afpModAfiliaciones");
                }
            } else {
                nuevaVigenciaAfiliacionAFP.setTercerosucursal(new TercerosSucursales());
                context.update("form:afpModAfiliaciones");
            }
        }
    }

    public void modificarEstadoCivil(int indice, String confirmarCambio, String valorConfirmar) {
        idEstadoCivil = indice;
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
                    context.update("form:estadoCivilModEstadoCivil");
                } else {
                    permitirIndexEstadoCivil = false;
                    getInfoRegistrolEstadoCivil();
                    context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
                    modificarInfoR_EstadoCivil(lovEstadosCiviles.size());
                    context.execute("EstadoCivilEstadoCivilDialogo.show()");
                    context.update("form:estadoCivilModEstadoCivil");
                }
            } else {
                nuevoEstadoCivil.setEstadocivil(new EstadosCiviles());
                context.update("form:estadoCivilModEstadoCivil");
            }
        }
    }

    public void modificarDireccion(int indice, String confirmarCambio, String valorConfirmar) {
        idDireccion = indice;
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
                    context.update("form:ciudadModDireccion");
                } else {
                    permitirIndexDireccion = false;
                    getInfoRegistroCiudadDireccion();
                    context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
                    modificarInfoR_CiudadDir(lovCiudades.size());
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
        idTelefono = indice;
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
                    context.update("form:ciudadModTelefono");
                } else {
                    permitirIndexTelefono = false;
                    getInfoRegistroCiudadTelefono();
                    context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
                    modificarInfoR_CiudadTel(lovCiudades.size());
                    context.execute("CiudadTelefonoDialogo.show()");
                    context.update("form:ciudadModTelefono");
                }
            } else {
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
                    context.update("form:tipoTelefonoModTelefono");
                } else {
                    permitirIndexTelefono = false;
                    getInfoRegistroTipoTelTelefono();
                    context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
                    modificarInfoR_TipoTelT(lovTiposTelefonos.size());
                    context.execute("TipoTelefonoTelefonoDialogo.show()");
                    context.update("form:tipoTelefonoModTelefono");
                }
            } else {
                nuevoTelefono.setTipotelefono(new TiposTelefonos());
                context.update("form:tipoTelefonoModTelefono");
            }
        }
    }

    public void actualizarParametroTerceroAfiliacion() {
        if (idAfiliacionAFP >= 0) {
            actualizarParametroAFPAfiliacion();
        }
        if (idAfiliacionARP >= 0) {
            actualizarParametroARPAfiliacion();
        }
        if (idAfiliacionCaja >= 0) {
            actualizarParametroCajaAfiliacion();
        }
        if (idAfiliacionEPS >= 0) {
            actualizarParametroEPSAfiliacion();
        }
        if (idAfiliacionFondo >= 0) {
            actualizarParametroFondoAfiliacion();
        }
    }

    public void cancelarParametroTerceroAfiliacion() {
        if (idAfiliacionAFP >= 0) {
            cancelarParametroAFPAfiliacion();
        }
        if (idAfiliacionARP >= 0) {
            cancelarParametroARPAfiliacion();
        }
        if (idAfiliacionCaja >= 0) {
            cancelarParametroCajaAfiliacion();
        }
        if (idAfiliacionEPS >= 0) {
            cancelarParametroEPSAfiliacion();
        }
        if (idAfiliacionFondo >= 0) {
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
        context.reset("formLovs:formDTelefono:lovCiudadTelefono:globalFilter");
        context.execute("lovCiudadTelefono.clearFilters()");
        context.execute("lovCiudadTelefono.unselectAllRows()");

        context.update("formLovs:formDTelefono:CiudadTelefonoDialogo");
        context.update("formLovs:formDTelefono:lovCiudadTelefono");
        context.update("formLovs:formDTelefono:aceptarCT");
        context.execute("CiudadTelefonoDialogo.hide()");
    }

    public void cancelarParametroCiudadTelefono() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexTelefono = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTelefono:lovCiudadTelefono:globalFilter");
        context.execute("lovCiudadTelefono.clearFilters()");
        context.execute("lovCiudadTelefono.unselectAllRows()");
        context.execute("CiudadTelefonoDialogo.hide()");
    }

    public void actualizarParametroTipoTelefonoTelefono() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoTelefono.setTipotelefono(tipoTelefonoSeleccionado);
        tipoTelefonoSeleccionado = new TiposTelefonos();
        filtrarLovTiposTelefonos = null;
        aceptar = true;
        permitirIndexTelefono = true;
        context.update("form:tipoTelefonoModTelefono");
        context.reset("formLovs:formDTelefono:lovTipoTelefonoTelefono:globalFilter");
        context.execute("lovTipoTelefonoTelefono.clearFilters()");
        context.execute("lovTipoTelefonoTelefono.unselectAllRows()");
        context.update("formLovs:formDTelefono:TipoTelefonoTelefonoDialogo");
        context.update("formLovs:formDTelefono:lovTipoTelefonoTelefono");
        context.update("formLovs:formDTelefono:aceptarTTT");
        context.execute("TipoTelefonoTelefonoDialogo.hide()");
    }

    public void cancelarParametroTipoTelefonoTelefono() {
        tipoTelefonoSeleccionado = new TiposTelefonos();
        filtrarLovTiposTelefonos = null;
        aceptar = true;
        permitirIndexTelefono = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTelefono:lovTipoTelefonoTelefono:globalFilter");

        context.execute("lovTipoTelefonoTelefono.clearFilters()");

        context.execute("lovTipoTelefonoTelefono.unselectAllRows()");

        context.execute("TipoTelefonoTelefonoDialogo.hide()");
    }

    public void actualizarParametroCiudadDireccion() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaDireccion.setCiudad(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexDireccion = true;
        context.update("form:ciudadModDireccion");
        context.reset("formLovs:formDDireccion:lovCiudadDireccion:globalFilter");

        context.execute("lovCiudadDireccion.clearFilters()");

        context.execute("lovCiudadDireccion.unselectAllRows()");

        context.update("formLovs:formDDireccion:CiudadDireccionDialogo");
        context.update("formLovs:formDDireccion:lovCiudadDireccion");
        context.update("formLovs:formDDireccion:aceptarCD");

        context.execute("CiudadDireccionDialogo.hide()");
    }

    public void cancelarParametroCiudadDireccion() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexDireccion = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDDireccion:lovCiudadDireccion:globalFilter");

        context.execute("lovCiudadDireccion.clearFilters()");

        context.execute("lovCiudadDireccion.unselectAllRows()");

        context.execute("CiudadDireccionDialogo.hide()");
    }

    public void actualizarParametroEstadoEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEstadoCivil.setEstadocivil(estadoCivilSeleccionado);
        estadoCivilSeleccionado = new EstadosCiviles();
        filtrarLovEstadosCiviles = null;
        aceptar = true;
        permitirIndexEstadoCivil = true;
        context.update("form:estadoCivilModEstadoCivil");
        context.reset("formLovs:formDEstadoCivil:lovEstadoCivilEstadoCivil:globalFilter");

        context.execute("lovEstadoCivilEstadoCivil.clearFilters()");

        context.execute("lovEstadoCivilEstadoCivil.unselectAllRows()");

        context.update("formLovs:formDEstadoCivil:EstadoCivilEstadoCivilDialogo");
        context.update("formLovs:formDEstadoCivil:lovEstadoCivilEstadoCivil");
        context.update("formLovs:formDEstadoCivil:aceptarECEC");

        context
                .execute("EstadoCivilEstadoCivilDialogo.hide()");
    }

    public void cancelarParametroEstadoEstadoCivil() {
        estadoCivilSeleccionado = new EstadosCiviles();
        filtrarLovEstadosCiviles = null;
        aceptar = true;
        permitirIndexEstadoCivil = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDEstadoCivil:lovEstadoCivilEstadoCivil:globalFilter");

        context.execute("lovEstadoCivilEstadoCivil.clearFilters()");

        context.execute("lovEstadoCivilEstadoCivil.unselectAllRows()");

        context
                .execute("EstadoCivilEstadoCivilDialogo.hide()");
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
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");

        context.execute("TerceroAfiliacionDialogo.hide()");
        consultarCodigoSS();
    }

    public void cancelarParametroEPSAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionEPS = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.execute("TerceroAfiliacionDialogo.hide()");
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
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");

        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroCajaAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionCaja = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.execute("TerceroAfiliacionDialogo.hide()");
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
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");

        consultarCodigoSC();

        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroARPAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionARP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.execute("TerceroAfiliacionDialogo.hide()");
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

        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");
        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("TerceroAfiliacionDialogo.hide()");
        consultarCodigoSP();
    }

    public void cancelarParametroAFPAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionAFP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.execute("TerceroAfiliacionDialogo.hide()");
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
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.update("formLovs:formDAfiliacion:TerceroAfiliacionDialogo");
        context.update("formLovs:formDAfiliacion:lovTerceroAfiliacion");
        context.update("formLovs:formDAfiliacion:aceptarTSA");

        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void cancelarParametroFondoAfiliacion() {
        terceroSucursalSeleccionado = new TercerosSucursales();
        filtrarLovTercerosSucursales = null;
        aceptar = true;
        permitirIndexAfiliacionFondo = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDAfiliacion:lovTerceroAfiliacion:globalFilter");

        context.execute("lovTerceroAfiliacion.clearFilters()");

        context.execute("lovTerceroAfiliacion.unselectAllRows()");

        context.execute("TerceroAfiliacionDialogo.hide()");
    }

    public void actualizarParametroJornadaJornadaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaJornada.setJornadatrabajo(jornadaLaboralSeleccionada);
        jornadaLaboralSeleccionada = new JornadasLaborales();
        filtrarLovJornadasLaborales = null;
        aceptar = true;
        permitirIndexJornadaLaboral = true;
        context.update("form:jornadaLaboralModJornadaLaboral");
        context.reset("formLovs:formDJornadaLaboral:lovJornadaJornadaLaboral:globalFilter");

        context
                .execute("lovJornadaJornadaLaboral.clearFilters()");

        context.execute("lovJornadaJornadaLaboral.unselectAllRows()");
        context.update("formLovs:formDJornadaLaboral:JornadaJornadaLaboralDialogo");
        context.update("formLovs:formDJornadaLaboral:lovJornadaJornadaLaboral");
        context.update("formLovs:formDJornadaLaboral:aceptarJLJL");

        context.execute("JornadaJornadaLaboralDialogo.hide()");
    }

    public void cancelarParametroJornadaJornadaLaboral() {
        jornadaLaboralSeleccionada = new JornadasLaborales();
        filtrarLovJornadasLaborales = null;
        aceptar = true;
        permitirIndexJornadaLaboral = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDJornadaLaboral:lovJornadaJornadaLaboral:globalFilter");

        context.execute("lovJornadaJornadaLaboral.clearFilters()");

        context.execute("lovJornadaJornadaLaboral.unselectAllRows()");

        context.execute("JornadaJornadaLaboralDialogo.hide()");
    }

    public void actualizarParametroMetodoFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setMetodopago(metodoPagoSeleccionado);
        metodoPagoSeleccionado = new MetodosPagos();
        filtrarLovMetodosPagos = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:metodoPagoModFormaPago");
        context.reset("formLovs:formDFormaPago:lovMetodoPagoFormaPago:globalFilter");

        context.execute("lovMetodoPagoFormaPago.clearFilters()");

        context.execute("lovMetodoPagoFormaPago.unselectAllRows()");
        context.update("formLovs:formDFormaPago:MetodoPagoFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovMetodoPagoFormaPago");
        context.update("formLovs:formDFormaPago:aceptarMPFP");

        context.execute("MetodoPagoFormaPagoDialogo.hide()");
    }

    public void cancelarParametroMetodoFormaPago() {
        metodoPagoSeleccionado = new MetodosPagos();
        filtrarLovMetodosPagos = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDFormaPago:lovMetodoPagoFormaPago:globalFilter");

        context.execute("lovMetodoPagoFormaPago.clearFilters()");

        context.execute("lovMetodoPagoFormaPago.unselectAllRows()");

        context.execute("MetodoPagoFormaPagoDialogo.hide()");
    }

    public void actualizarParametroSucursalFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setSucursal(sucursalSeleccionada);
        sucursalSeleccionada = new Sucursales();
        filtrarLovSucursales = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:sucursalPagoModFormaPago");

        context.reset("formLovs:formDFormaPago:lovSucursalPagoFormaPago:globalFilter");

        context.execute("lovSucursalPagoFormaPago.clearFilters()");

        context.execute("lovSucursalPagoFormaPago.unselectAllRows()");
        context.update("formLovs:formDFormaPago:SucursalFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovSucursalPagoFormaPago");
        context.update("formLovs:formDFormaPago:aceptarSFP");

        context.execute("SucursalFormaPagoDialogo.hide()");
    }

    public void cancelarParametroSucursalFormaPago() {
        sucursalSeleccionada = new Sucursales();
        filtrarLovSucursales = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDFormaPago:lovSucursalPagoFormaPago:globalFilter");

        context.execute("lovSucursalPagoFormaPago.clearFilters()");

        context.execute("lovSucursalPagoFormaPago.unselectAllRows()");

        context.execute("SucursalFormaPagoDialogo.hide()");
    }

    public void actualizarParametroPeriodicidadFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaFormaPago.setFormapago(periodicidadSeleccionada);
        periodicidadSeleccionada = new Periodicidades();
        filtrarLovPeriodicidades = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        context.update("form:formaPagoModFormaPago");

        context.reset("formLovs:formDFormaPago:lovPeriodicidadFormaPago:globalFilter");

        context.execute("lovPeriodicidadFormaPago.clearFilters()");

        context.execute("lovPeriodicidadFormaPago.unselectAllRows()");
        context.update("formLovs:formDFormaPago:PeriodicidadFormaPagoDialogo");
        context.update("formLovs:formDFormaPago:lovPeriodicidadFormaPago");
        context.update("formLovs:formDFormaPago:aceptarPFP");

        context.execute("PeriodicidadFormaPagoDialogo.hide()");
    }

    public void cancelarParametroPeriodicidadFormaPago() {
        periodicidadSeleccionada = new Periodicidades();
        filtrarLovPeriodicidades = null;
        aceptar = true;
        permitirIndexFormaPago = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDFormaPago:lovPeriodicidadFormaPago:globalFilter");

        context.execute("lovPeriodicidadFormaPago.clearFilters()");

        context.execute("lovPeriodicidadFormaPago.unselectAllRows()");

        context.execute("PeriodicidadFormaPagoDialogo.hide()");
    }

    public void actualizarParametroUbicacionUbicacionGeografica() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaUbicacion.setUbicacion(ubicacionGeograficaSeleccionada);
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        filtrarLovUbicacionesGeograficas = null;
        aceptar = true;
        permitirIndexUbicacionGeografica = true;
        context.update("form:ubicacionGeograficaModUbicacionGeografica");
        context.reset("formLovs:formDUbicacion:lovUbicacionUbicacionGeografica:globalFilter");

        context.execute("lovUbicacionUbicacionGeografica.clearFilters()");

        context.execute("lovUbicacionUbicacionGeografica.unselectAllRows()");
        context.update("formLovs:formDUbicacion:UbicacionUbicacionGeograficaDialogo");
        context.update("formLovs:formDUbicacion:lovUbicacionUbicacionGeografica");
        context.update("formLovs:formDUbicacion:aceptarUGUG");

        context.execute("UbicacionUbicacionGeograficaDialogo.hide()");
    }

    public void cancelarParametroUbicacionUbicacionGeografica() {
        ubicacionGeograficaSeleccionada = new UbicacionesGeograficas();
        filtrarLovUbicacionesGeograficas = null;
        aceptar = true;
        permitirIndexUbicacionGeografica = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDUbicacion:lovUbicacionUbicacionGeografica:globalFilter");

        context.execute("lovUbicacionUbicacionGeografica.clearFilters()");

        context.execute("lovUbicacionUbicacionGeografica.unselectAllRows()");

        context.execute("UbicacionUbicacionGeograficaDialogo.hide()");
    }

    public void actualizarParametroContratoLegislacionLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaContrato.setContrato(contratoSeleccionado);
        contratoSeleccionado = new Contratos();
        filtrarLovContratos = null;
        aceptar = true;
        permitirIndexLegislacionLaboral = true;
        context.update("form:legislacionLaboralModLegislacionLaboral");
        context.reset("formLovs:formDLegislacionLaboral:lovContratoLegislacionLaboral:globalFilter");

        context.execute("lovContratoLegislacionLaboral.clearFilters()");

        context.execute("lovContratoLegislacionLaboral.unselectAllRows()");
        context.update("formLovs:formDLegislacionLaboral:ContratoLegislacionLaboralDialogo");
        context.update("formLovs:formDLegislacionLaboral:lovContratoLegislacionLaboral");
        context.update("formLovs:formDLegislacionLaboral:aceptarCLL");

        context.execute("ContratoLegislacionLaboralDialogo.hide()");
        validarTipoTrabajadorContrato();
    }

    public void cancelarParametroContratoLegislacionLaboral() {
        contratoSeleccionado = new Contratos();
        filtrarLovContratos = null;
        aceptar = true;
        permitirIndexLegislacionLaboral = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDLegislacionLaboral:lovContratoLegislacionLaboral:globalFilter");

        context.execute("lovContratoLegislacionLaboral.clearFilters()");

        context.execute("lovContratoLegislacionLaboral.unselectAllRows()");

        context.execute("ContratoLegislacionLaboralDialogo.hide()");
    }

    public void actualizarParametroNormaNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaNormaEmpleado.setNormalaboral(normaLaboralSeleccionada);
        normaLaboralSeleccionada = new NormasLaborales();
        filtrarLovNormasLaborales = null;
        aceptar = true;
        permitirIndexNormaLaboral = true;
        context.update("form:normaLaboralModNormaLaboral");

        context.reset("formLovs:formDNormaLaboral:lovNormaLaboralNormaLaboral:globalFilter");

        context.execute("lovNormaLaboralNormaLaboral.clearFilters()");

        context.execute("lovNormaLaboralNormaLaboral.unselectAllRows()");
        context.update("formLovs:formDNormaLaboral:NormaLaboralNormaLaboralDialogo");
        context.update("formLovs:formDNormaLaboral:lovNormaLaboralNormaLaboral");
        context.update("formLovs:formDNormaLaboral:aceptarNLNL");

        context.execute("NormaLaboralNormaLaboralDialogo.hide()");
        validarTipoTrabajadorNormaLaboral();
    }

    public void cancelarParametroNormaNormaLaboral() {
        normaLaboralSeleccionada = new NormasLaborales();
        filtrarLovNormasLaborales = null;
        aceptar = true;
        permitirIndexNormaLaboral = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDNormaLaboral:lovNormaLaboralNormaLaboral:globalFilter");

        context.execute("lovNormaLaboralNormaLaboral.clearFilters()");

        context.execute("lovNormaLaboralNormaLaboral.unselectAllRows()");

        context.execute("NormaLaboralNormaLaboralDialogo.hide()");
    }

    public void actualizarParametroMotivoContratoTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoContrato.setMotivocontrato(motivoContratoSeleccionado);
        motivoContratoSeleccionado = new MotivosContratos();
        filtrarLovMotivosContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        context.update("form:motivoContratoModTipoContrato");

        context.reset("formLovs:formDTipoContrato:lovMotivoContratoTipoContrato:globalFilter");

        context.execute("lovMotivoContratoTipoContrato.clearFilters()");

        context.execute("lovMotivoContratoTipoContrato.unselectAllRows()");
        context.update("formLovs:formDTipoContrato:MotivoContratoTipoContratoDialogo");
        context.update("formLovs:formDTipoContrato:lovMotivoContratoTipoContrato");
        context.update("formLovs:formDTipoContrato:aceptarMCTC");

        context.execute("MotivoContratoTipoContratoDialogo.hide()");
    }

    public void cancelarParametroMotivoContratoTipoContrato() {
        motivoContratoSeleccionado = new MotivosContratos();
        filtrarLovMotivosContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTipoContrato:lovMotivoContratoTipoContrato:globalFilter");

        context.execute("lovMotivoContratoTipoContrato.clearFilters()");

        context.execute("lovMotivoContratoTipoContrato.unselectAllRows()");

        context.execute("MotivoContratoTipoContratoDialogo.hide()");
    }

    public void actualizarParametroTipoContratoTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoContrato.setTipocontrato(tipoContratoSeleccionado);
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        context.update("form:tipoContratoModTipoContrato");
        context.reset("formLovs:formDTipoContrato:lovTipoContratoTipoContrato:globalFilter");

        context.execute("lovTipoContratoTipoContrato.clearFilters()");

        context.execute("lovTipoContratoTipoContrato.unselectAllRows()");
        context.update("formLovs:formDTipoContrato:TipoContratoTipoContratoDialogo");
        context.update("formLovs:formDTipoContrato:lovTipoContratoTipoContrato");
        context.update("formLovs:formDTipoContrato:aceptarTCTC");

        context.execute("TipoContratoTipoContratoDialogo.hide()");
        validarTipoTrabajadorTipoContrato();
    }

    public void cancelarParametroTipoContratoTipoContrato() {
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
        aceptar = true;
        permitirIndexTipoContrato = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTipoContrato:lovTipoContratoTipoContrato:globalFilter");

        context.execute("lovTipoContratoTipoContrato.clearFilters()");

        context.execute("lovTipoContratoTipoContrato.unselectAllRows()");

        context.execute("TipoContratoTipoContratoDialogo.hide()");
    }

    public void actualizarParametroMotivoCambioSueldoSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
        motivoCambioSueldoSeleccionado = new MotivosCambiosSueldos();
        filtrarLovMotivosCambiosSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:motivoSueldoModSueldo");
        context.reset("formLovs:formDSueldo:lovMotivoCambioSueldoSueldo:globalFilter");

        context.execute("lovMotivoCambioSueldoSueldo.clearFilters()");

        context.execute("lovMotivoCambioSueldoSueldo.unselectAllRows()");
        context.update("formLovs:formDSueldo:MotivoCambioSueldoSueldoDialogo");
        context.update("formLovs:formDSueldo:lovMotivoCambioSueldoSueldo");
        context.update("formLovs:formDSueldo:aceptarMCSS");

        context.execute("MotivoCambioSueldoSueldoDialogo.hide()");
    }

    public void cancelarParametroMotivoCambioSueldoSueldo() {
        motivoCambioSueldoSeleccionado = new MotivosCambiosSueldos();
        filtrarLovMotivosCambiosSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDSueldo:lovMotivoCambioSueldoSueldo:globalFilter");

        context.execute("lovMotivoCambioSueldoSueldo.clearFilters()");

        context.execute("lovMotivoCambioSueldoSueldo.unselectAllRows()");

        context.execute("MotivoCambioSueldoSueldoDialogo.hide()");
    }

    public void actualizarParametroTipoSueldoSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setTiposueldo(tipoSueldoSeleccionado);
        tipoSueldoSeleccionado = new TiposSueldos();
        filtrarLovTiposSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:tipoSueldoModSueldo");
        context.reset("formLovs:formDSueldo:lovTipoSueldoSueldo:globalFilter");

        context.execute("lovTipoSueldoSueldo.clearFilters()");

        context.execute("lovTipoSueldoSueldo.unselectAllRows()");
        context.update("formLovs:formDSueldo:TipoSueldoSueldoDialogo");
        context.update("formLovs:formDSueldo:lovTipoSueldoSueldo");
        context.update("formLovs:formDSueldo:aceptarTSS");

        context.execute("TipoSueldoSueldoDialogo.hide()");
        validarTipoTrabajadorTipoSueldo();
    }

    public void cancelarParametroTipoSueldoSueldo() {
        tipoSueldoSeleccionado = new TiposSueldos();
        filtrarLovTiposSueldos = null;
        aceptar = true;
        permitirIndexSueldo = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDSueldo:lovTipoSueldoSueldo:globalFilter");

        context.execute("lovTipoSueldoSueldo.clearFilters()");

        context.execute("lovTipoSueldoSueldo.unselectAllRows()");

        context.execute("TipoSueldoSueldoDialogo.hide()");
    }

    public void actualizarParametroUnidadSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaSueldo.setUnidadpago(unidadSeleccionada);
        unidadSeleccionada = new Unidades();
        filtrarLovUnidades = null;
        aceptar = true;
        permitirIndexSueldo = true;
        context.update("form:unidadPagoModSueldo");
        context.reset("formLovs:formDSueldo:lovUnidadSueldo:globalFilter");

        context.execute("lovUnidadSueldo.clearFilters()");

        context.execute("lovUnidadSueldo.unselectAllRows()");
        context.update("formLovs:formDSueldo:UnidadSueldoDialogo");
        context.update("formLovs:formDSueldo:lovUnidadSueldo");
        context.update("formLovs:formDSueldo:aceptarUS");

        context.execute("UnidadSueldoDialogo.hide()");
    }

    public void cancelarParametroUnidadSueldo() {
        unidadSeleccionada = new Unidades();
        filtrarLovUnidades = null;
        aceptar = true;
        permitirIndexSueldo = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDSueldo:lovUnidadSueldo:globalFilter");

        context.execute("lovUnidadSueldo.clearFilters()");

        context.execute("lovUnidadSueldo.unselectAllRows()");

        context.execute("UnidadSueldoDialogo.hide()");
    }

    public void actualizarParametroReformaLaboralTipoSalario() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaReformaLaboral.setReformalaboral(reformaLaboralSeleccionada);
        reformaLaboralSeleccionada = new ReformasLaborales();
        filtrarLovReformasLaborales = null;
        aceptar = true;
        permitirIndexTipoSalario = true;
        context.update("form:tipoSalarioModTipoSalario");

        context.reset("formLovs:formDTipoSalario:lovReformaLaboralTipoSalario:globalFilter");

        context.execute("lovReformaLaboralTipoSalario.clearFilters()");

        context.execute("lovReformaLaboralTipoSalario.unselectAllRows()");
        context.update("formLovs:formDTipoSalario:ReformaLaboralTipoSalarioDialogo");
        context.update("formLovs:formDTipoSalario:lovReformaLaboralTipoSalario");
        context.update("formLovs:formDTipoSalario:aceptarRLTS");

        context.execute("ReformaLaboralTipoSalarioDialogo.hide()");
        validarTipoTrabajadorReformaLaboral();
    }

    public void cancelarParametroReformaLaboralTipoSalario() {
        reformaLaboralSeleccionada = new ReformasLaborales();
        filtrarLovReformasLaborales = null;
        aceptar = true;
        permitirIndexTipoSalario = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTipoSalario:lovReformaLaboralTipoSalario:globalFilter");

        context.execute("lovReformaLaboralTipoSalario.clearFilters()");

        context.execute("lovReformaLaboralTipoSalario.unselectAllRows()");

        context.execute("ReformaLaboralTipoSalarioDialogo.hide()");
    }

    public void actualizarParametroTipoTrabajadorTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaTipoTrabajador.setTipotrabajador(tipoTrabajadorSeleccionado);
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        filtrarLovTiposTrabajadores = null;
        aceptar = true;
        permitirIndexTipoTrabajador = true;
        context.update("form:tipoTrabajadorModTipoTrabajador");

        context.reset("formLovs:formDTipoTrabajador:lovTipoTrabajadorTipoTrabajador:globalFilter");
        context.update("formLovs:formDTipoTrabajador:TipoTrabajadorTipoTrabajadorDialogo");

        context.execute("lovTipoTrabajadorTipoTrabajador.clearFilters()");

        context.execute("lovTipoTrabajadorTipoTrabajador.unselectAllRows()");
        context.update("formLovs:formDTipoTrabajador:lovTipoTrabajadorTipoTrabajador");
        context.update("formLovs:formDTipoTrabajador:aceptarTTTT");

        context.execute("TipoTrabajadorTipoTrabajadorDialogo.hide()");
        validarDisableTipoTrabajador();
    }

    public void cancelarParametroTipoTrabajadorTipoTrabajador() {
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        filtrarLovTiposTrabajadores = null;
        aceptar = true;
        permitirIndexTipoTrabajador = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDTipoTrabajador:lovTipoTrabajadorTipoTrabajador:globalFilter");

        context.execute("lovTipoTrabajadorTipoTrabajador.clearFilters()");

        context.execute("lovTipoTrabajadorTipoTrabajador.unselectAllRows()");

        context.execute("TipoTrabajadorTipoTrabajadorDialogo.hide()");
    }

    public void actualizarParametroEstructuraCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaLocalizacion.setLocalizacion(estructuraCentroCostoSeleccionada);
        estructuraCentroCostoSeleccionada = new Estructuras();
        filtrarLovEstructurasCentroCosto = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        context.update("form:estructuraModCentroCosto");

        context.reset("formLovs:formDCentroCosto:lovEstructuraCentroCosto:globalFilter");

        context.execute("lovEstructuraCentroCosto.clearFilters()");

        context.execute("lovEstructuraCentroCosto.unselectAllRows()");
        context.update("formLovs:formDCentroCosto:EstructuraCentroCostoDialogo");
        context.update("formLovs:formDCentroCosto:lovEstructuraCentroCosto");
        context.update("formLovs:formDCentroCosto:aceptarECC");

        context.execute("EstructuraCentroCostoDialogo.hide()");
    }

    public void cancelarParametroEstructuraCentroCosto() {
        estructuraCentroCostoSeleccionada = new Estructuras();
        filtrarLovEstructurasCentroCosto = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCentroCosto:lovEstructuraCentroCosto:globalFilter");

        context.execute("lovEstructuraCentroCosto.clearFilters()");

        context.execute("lovEstructuraCentroCosto.unselectAllRows()");

        context.execute("EstructuraCentroCostoDialogo.hide()");
    }

    public void actualizarParametroMotivoCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaLocalizacion.setMotivo(motivoLocalizacionSeleccionado);
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        filtrarLovMotivosCC = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        context.update("form:motivoModCentroCosto");
        context.reset("formLovs:formDCentroCosto:lovMotivoLocalizacionCentroCosto:globalFilter");

        context.execute("lovMotivoLocalizacionCentroCosto.clearFilters()");

        context.execute("lovMotivoLocalizacionCentroCosto.unselectAllRows()");

        context.update("formLovs:formDCentroCosto:MotivoLocalizacionCentroCostoDialogo");
        context.update("formLovs:formDCentroCosto:lovMotivoLocalizacionCentroCosto");
        context.update("formLovs:formDCentroCosto:aceptarMLCC");

        context.execute("MotivoLocalizacionCentroCostoDialogo.hide()");
    }

    public void cancelarParametroMotivoCentroCosto() {
        motivoLocalizacionSeleccionado = new MotivosLocalizaciones();
        filtrarLovMotivosCC = null;
        aceptar = true;
        permitirIndexCentroCosto = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCentroCosto:lovMotivoLocalizacionCentroCosto:globalFilter");

        context.execute("lovMotivoLocalizacionCentroCosto.clearFilters()");

        context.execute("lovMotivoLocalizacionCentroCosto.unselectAllRows()");

        context.execute("MotivoLocalizacionCentroCostoDialogo.hide()");
    }

    public void actualizarParametroCargoCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setCargo(cargoSeleccionado);
        cargoSeleccionado = new Cargos();
        filtrarLovCargos = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:cargoModCargoDesempeñado");

        context.reset("formLovs:formDCargoDesempenado:lovCargoCargoDesempeñado:globalFilter");

        context.execute("lovCargoCargoDesempeñado.clearFilters()");

        context.execute("lovCargoCargoDesempeñado.unselectAllRows()");
        context.update("formLovs:formDCargoDesempenado:CargoCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovCargoCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarCCD");

        context.execute("CargoCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroCargoCargoDesempeñado() {
        cargoSeleccionado = new Cargos();
        filtrarLovCargos = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCargoDesempenado:lovCargoCargoDesempeñado:globalFilter");

        context.execute("lovCargoCargoDesempeñado.clearFilters()");

        context.execute("lovCargoCargoDesempeñado.unselectAllRows()");

        context.execute("CargoCargoDesempeñadoDialogo.hide()");
    }

    public void actualizarParametroMotivoCambioCargoCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setMotivocambiocargo(motivoCambioCargoSeleccionado);
        motivoCambioCargoSeleccionado = new MotivosCambiosCargos();
        filtrarLovMotivosCambiosCargos = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:motivoModCargoDesempeñado");

        context.reset("formLovs:formDCargoDesempenado:lovMotivoCambioCargoCargoDesempeñado:globalFilter");

        context.execute("lovMotivoCambioCargoCargoDesempeñado.clearFilters()");

        context.execute("lovMotivoCambioCargoCargoDesempeñado.unselectAllRows()");
        context.update("formLovs:formDCargoDesempenado:MotivoCambioCargoCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovMotivoCambioCargoCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarMCCCD");

        context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroMotivoCambioCargoCargoDesempeñado() {
        motivoCambioCargoSeleccionado = new MotivosCambiosCargos();
        filtrarLovMotivosCambiosCargos = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCargoDesempenado:lovMotivoCambioCargoCargoDesempeñado:globalFilter");

        context.execute("lovMotivoCambioCargoCargoDesempeñado.clearFilters()");

        context.execute("lovMotivoCambioCargoCargoDesempeñado.unselectAllRows()");

        context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.hide()");
    }

    public void actualizarParametroEstructuraCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setEstructura(estructuraSeleccionada);
        estructuraSeleccionada = new Estructuras();
        filtrarLovEstructuras = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:estructuraModCargoDesempeñado");
        context.reset("formLovs:formDCargoDesempenado:lovEstructuraCargoDesempeñado:globalFilter");

        context.execute("lovEstructuraCargoDesempeñado.clearFilters()");

        context.execute("lovEstructuraCargoDesempeñado.unselectAllRows()");
        context.update("formLovs:formDCargoDesempenado:EstructuraCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovEstructuraCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarECD");

        context.execute("EstructuraCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroEstructuraCargoDesempeñado() {
        estructuraSeleccionada = new Estructuras();
        filtrarLovEstructuras = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCargoDesempenado:lovEstructuraCargoDesempeñado:globalFilter");

        context.execute("lovEstructuraCargoDesempeñado.clearFilters()");

        context.execute("lovEstructuraCargoDesempeñado.unselectAllRows()");

        context.execute("EstructuraCargoDesempeñadoDialogo.hide()");
    }

    public void actualizarParametroPapelCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setPapel(papelSeleccionado);
        papelSeleccionado = new Papeles();
        filtrarLovPapeles = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:papelModCargoDesempeñado");
        context.reset("formLovs:formDCargoDesempenado:lovPapelCargoDesempeñado:globalFilter");

        context.execute("lovPapelCargoDesempeñado.clearFilters()");

        context.execute("lovPapelCargoDesempeñado.unselectAllRows()");
        context.update("formLovs:formDCargoDesempenado:PapelCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovPapelCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarPCD");

        context.execute("PapelCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroPapelCargoDesempeñado() {
        papelSeleccionado = new Papeles();
        filtrarLovPapeles = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCargoDesempenado:lovPapelCargoDesempeñado:globalFilter");

        context.execute("lovPapelCargoDesempeñado.clearFilters()");

        context.execute("lovPapelCargoDesempeñado.unselectAllRows()");

        context.execute("PapelCargoDesempeñadoDialogo.hide()");
    }

    public void actualizarParametroJefeCargoDesempeñado() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaVigenciaCargo.setEmpleadojefe(empleadoSeleccionado);
        empleadoSeleccionado = new Empleados();
        filtrarLovEmpleados = null;
        aceptar = true;
        permitirIndexCargoDesempeñado = true;
        context.update("form:empleadoJefeModCargoDesempeñado");
        context.reset("formLovs:formDCargoDesempenado:lovEmpleadoJefeCargoDesempeñado:globalFilter");

        context.execute("lovEmpleadoJefeCargoDesempeñado.clearFilters()");

        context.execute("lovEmpleadoJefeCargoDesempeñado.unselectAllRows()");
        context.update("formLovs:formDCargoDesempenado:EmpleadoJefeCargoDesempeñadoDialogo");
        context.update("formLovs:formDCargoDesempenado:lovEmpleadoJefeCargoDesempeñado");
        context.update("formLovs:formDCargoDesempenado:aceptarEJCD");

        context.execute("EmpleadoJefeCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroJefeCargoDesempeñado() {
        empleadoSeleccionado = new Empleados();
        filtrarLovEmpleados = null;
        permitirIndexCargoDesempeñado = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDCargoDesempenado:lovEmpleadoJefeCargoDesempeñado:globalFilter");

        context.execute("lovEmpleadoJefeCargoDesempeñado.clearFilters()");

        context.execute("lovEmpleadoJefeCargoDesempeñado.unselectAllRows()");

        context.execute("EmpleadoJefeCargoDesempeñadoDialogo.hide()");
    }

    public void actualizarParametroEmpresaInformacionPersonalVisible() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEmpleado.setEmpresa(empresaSeleccionada);

        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        lovCargos = administrarPersonaIndividual.lovCargosXEmpresa(nuevoEmpleado.getEmpresa().getSecuencia());
        context.update("form:empresaModPersonal");

        context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible:globalFilter");

        context.execute("lovEmpresaInformacionPersonalVisible.clearFilters()");

        context.execute("lovEmpresaInformacionPersonalVisible.unselectAllRows()");
        context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogoVisible");
        context.update("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible");
        context.update("formLovs:formDInformacionPersonal:aceptarEIPV");

        context.execute("EmpresaInformacionPersonalDialogoVisible.hide()");
        calcularControlEmpleadosEmpresa();
        modificacionesEmpresaFechaIngresoInformacionPersonal();
    }

    public void cancelarParametroEmpresaInformacionPersonalVisible() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (empresaSeleccionada == null) {
            filtrarLovEmpresas = null;
            aceptar = true;
            context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible:globalFilter");

            context.execute("lovEmpresaInformacionPersonalVisible.clearFilters()");

            context.execute("lovEmpresaInformacionPersonalVisible.unselectAllRows()");
            context.reset("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogoVisible");
        } else {
            filtrarLovEmpresas = null;
            permitirIndexInformacionPersonal = true;
            aceptar = true;
            context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonalVisible:globalFilter");

            context.execute("lovEmpresaInformacionPersonalVisible.clearFilters()");

            context.execute("lovEmpresaInformacionPersonalVisible.unselectAllRows()");

            context.execute("EmpresaInformacionPersonalDialogoVisible.hide()");
        }
    }

    public void actualizarParametroEmpresaInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoEmpleado.setEmpresa(empresaSeleccionada);
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        calcularControlEmpleadosEmpresa();
        modificacionesEmpresaFechaIngresoInformacionPersonal();
        context.update("form:empresaModPersonal");
        context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonal:globalFilter");

        context.execute("lovEmpresaInformacionPersonal.clearFilters()");

        context.execute("lovEmpresaInformacionPersonal.unselectAllRows()");
        context.update("formLovs:formDInformacionPersonal:EmpresaInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonal");

        context.execute("EmpresaInformacionPersonalDialogo.hide()");
    }

    public void cancelarParametroEmpresaInformacionPersonal() {
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDInformacionPersonal:lovEmpresaInformacionPersonal:globalFilter");
        context.execute("lovEmpresaInformacionPersonal.clearFilters()");
        context.execute("lovEmpresaInformacionPersonal.unselectAllRows()");
        context.execute("EmpresaInformacionPersonalDialogo.hide()");
    }

    public void actualizarParametroTipoDocumentoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setTipodocumento(tipoDocumentoSeleccionado);
        tipoDocumentoSeleccionado = new TiposDocumentos();
        filtrarLovTiposDocumentos = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:tipoDocumentoModPersonal");

        context.reset("formLovs:formDInformacionPersonal:lovTipoDocumentoInformacionPersonal:globalFilter");
        context.execute("lovTipoDocumentoInformacionPersonal.clearFilters()");
        context.execute("lovTipoDocumentoInformacionPersonal.unselectAllRows()");
        context.update("formLovs:formDInformacionPersonal:TipoDocumentoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovTipoDocumentoInformacionPersonal");
        context.execute("TipoDocumentoInformacionPersonalDialogo.hide()");
    }

    public void cancelarParametroTipoDocumentoInformacionPersonal() {
        tipoDocumentoSeleccionado = new TiposDocumentos();
        filtrarLovTiposDocumentos = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDInformacionPersonal:lovTipoDocumentoInformacionPersonal:globalFilter");
        context.execute("lovTipoDocumentoInformacionPersonal.clearFilters()");
        context.execute("lovTipoDocumentoInformacionPersonal.unselectAllRows()");
        context.execute("TipoDocumentoInformacionPersonalDialogo.hide()");
    }

    public void actualizarParametroCiudadDocumentoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setCiudaddocumento(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:ciudadDocumentoModPersonal");
        context.reset("formLovs:formDInformacionPersonal:lovCiudadDocumentoInformacionPersonal:globalFilter");
        context.execute("lovCiudadDocumentoInformacionPersonal.clearFilters()");
        context.execute("lovCiudadDocumentoInformacionPersonal.unselectAllRows()");
        context.update("formLovs:formDInformacionPersonal:CiudadDocumentoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovCiudadDocumentoInformacionPersonal");
        context.execute("CiudadDocumentoInformacionPersonalDialogo.hide()");
        context.update("formLovs:formDInformacionPersonal:aceptarCDIP");
    }

    public void cancelarParametroCiudadDocumentoInformacionPersonal() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDInformacionPersonal:lovCiudadDocumentoInformacionPersonal:globalFilter");

        context.execute("lovCiudadDocumentoInformacionPersonal.clearFilters()");

        context.execute("lovCiudadDocumentoInformacionPersonal.unselectAllRows()");

        context.execute("CiudadDocumentoInformacionPersonalDialogo.hide()");
    }

    public void actualizarParametroCiudadNacimientoInformacionPersonal() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevaPersona.setCiudadnacimiento(ciudadSeleccionada);
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        aceptar = true;
        permitirIndexInformacionPersonal = true;
        context.update("form:ciudadNacimientoModPersonal");

        context.reset("formLovs:formDInformacionPersonal:lovCiudadNacimientoInformacionPersonal:globalFilter");

        context.execute("lovCiudadNacimientoInformacionPersonal.clearFilters()");

        context.execute("lovCiudadNacimientoInformacionPersonal.unselectAllRows()");
        context.update("formLovs:formDInformacionPersonal:CiudadNacimientoInformacionPersonalDialogo");
        context.update("formLovs:formDInformacionPersonal:lovCiudadNacimientoInformacionPersonal");

        context.execute("CiudadNacimientoInformacionPersonalDialogo.hide()");
        context.update("formLovs:formDInformacionPersonal:aceptarCNIP");
    }

    public void cancelarParametroCiudadNacimientoInformacionPersonal() {
        ciudadSeleccionada = new Ciudades();
        filtrarLovCiudades = null;
        permitirIndexInformacionPersonal = true;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:formDInformacionPersonal:lovCiudadNacimientoInformacionPersonal:globalFilter");

        context.execute("lovCiudadNacimientoInformacionPersonal.clearFilters()");

        context.execute("lovCiudadNacimientoInformacionPersonal.unselectAllRows()");

        context.execute("CiudadNacimientoInformacionPersonalDialogo.hide()");
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
            System.out.println("paso el if()  nuevoEmpleado.getEmpresa(): " + nuevoEmpleado.getEmpresa().getSecuencia());
            disableDescripcionEstructura = false;
            disableUbicacionGeografica = false;
            disableAfiliaciones = false;
            System.out.println("fechaIngreso : " + fechaIngreso);
            if (fechaIngreso != null) {
                disableNombreEstructuraCargo = false;
                lovEstructuras = null;
                getLovEstructuras();
                System.out.println("disableNombreEstructuraCargo es false");
            } else {
                disableNombreEstructuraCargo = true;
                System.out.println("disableNombreEstructuraCargo es true");
            }
        } else {
            disableNombreEstructuraCargo = true;
            System.out.println("disableNombreEstructuraCargo es true");
            disableUbicacionGeografica = true;
            disableAfiliaciones = true;
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
        context.update("form:infoEstructuraCargoDesempeñado");
    }

    public void validarDisableTipoTrabajador() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            disableCamposDependientesTipoT = false;
        } else {
            disableCamposDependientesTipoT = true;
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
            } else {
                System.out.println("Error en la consulta a la BD");
            }
        }
    }

    public void validarTipoTrabajadorTipoSueldo() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorTipoSueldo(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaSueldo.getTiposueldo().getSecuencia());
            if (validar != null) {
                if (validar.equalsIgnoreCase("N")) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("aletarTiposTrabajadoresTS.show()");
                }
            } else {
                System.out.println("Error en la consulta a la BD");
            }
        }
    }

    public void validarTipoTrabajadorTipoContrato() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorTipoContrato(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia());
            if (validar != null) {
                if (validar.equalsIgnoreCase("N")) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("aletarTiposTrabajadoresTC.show()");
                }
            } else {
                System.out.println("Error en la consulta a la BD");
            }
        }
    }

    public void validarTipoTrabajadorNormaLaboral() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorNormaLaboral(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia());
            if (validar != null) {
                if (validar.equalsIgnoreCase("N")) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("aletarTiposTrabajadoresNL.show()");
                }
            } else {
                System.out.println("Error en la consulta a la BD");
            }
        }
    }

    public void validarTipoTrabajadorContrato() {
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() != null) {
            String validar = administrarPersonaIndividual.validarTipoTrabajadorContrato(nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia(), nuevaVigenciaContrato.getContrato().getSecuencia());
            if (validar != null) {
                if (validar.equalsIgnoreCase("N")) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    //context.execute("aletarTiposTrabajadoresC.show()");
                }
            } else {
                System.out.println("Error en la consulta a la BD");
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
        //if (empresaSeleccionada != null) {
        aceptar = false;
        //}
    }

    public Date mostrarFechaIngreso() {
        return fechaIngreso;
    }

    private void modificarInfoR_EmpresaInfoP(int valor) {
        infoRegistroEmpresaInfoPersonal = String.valueOf(valor);
    }

    public void eventoFiltrar_EmpresaInfoP() {
        if (filtrarLovEmpresas.size() == 1) {
            empresaSeleccionada = filtrarLovEmpresas.get(0);
            aceptar = false;
        } else {
            empresaSeleccionada = null;
            aceptar = true;
        }

        modificarInfoR_EmpresaInfoP(filtrarLovEmpresas.size());
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroEmpresaInformacionPersonal");
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroEmpresaInformacionPersonalV");
    }

    private void modificarInfoR_TipoDocInfoP(int valor) {
        infoRegistroTipoDocInfoPersonal = String.valueOf(valor);
    }

    public void eventoFiltrar_TipoDocInfoP() {
        if (filtrarLovTiposDocumentos.size() == 1) {
            tipoDocumentoSeleccionado = filtrarLovTiposDocumentos.get(0);
            aceptar = false;
        } else {
            tipoDocumentoSeleccionado = null;
            aceptar = true;
        }
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroTipoDocumentoInformacionPersonal");
    }

    private void modificarInfoR_CiudadDocInfoP(int valor) {
        infoRegistroCiudadDocInfoPersonal = String.valueOf(valor);
    }

    public void eventoFiltrar_CiudadDocInfoP() {
        if (filtrarLovCiudades.size() == 1) {
            ciudadSeleccionada = filtrarLovCiudades.get(0);
            aceptar = false;
        } else {
            ciudadSeleccionada = null;
            aceptar = true;
        }

        modificarInfoR_CiudadDocInfoP(filtrarLovCiudades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroCiudadDocumentoInformacionPersonal");
    }

    private void modificarInfoR_CiudadNacInfoP(int valor) {
        infoRegistroCiudadNacInfoPersonal = String.valueOf(valor);
    }

    public void eventoFiltrar_CiudadNacInfoP() {
        if (filtrarLovCiudades.size() == 1) {
            ciudadSeleccionada = filtrarLovCiudades.get(0);
            aceptar = false;
        } else {
            ciudadSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_CiudadNacInfoP(filtrarLovCiudades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDInformacionPersonal:infoRegistroCiudadNacimientoInformacionPersonal");
    }

    //Funciones para contar registros:
    private void modificarInfoR_CargoD(int valor) {
        infoRegistroCargoDesempenado = String.valueOf(valor);
    }

    public void eventoFiltrar_CargoD() {
        if (filtrarLovCargos.size() == 1) {
            cargoSeleccionado = filtrarLovCargos.get(0);
            aceptar = false;
        } else {
            cargoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_CargoD(filtrarLovCargos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroCargoCargoDesempenado");
    }

    private void modificarInfoR_JefeCargoD(int valor) {
        infoRegistroJefeCargoDesempenado = String.valueOf(valor);
    }

    public void eventoFiltrar_JefeCargoD() {
        if (filtrarLovEmpleados.size() == 1) {
            empleadoSeleccionado = filtrarLovEmpleados.get(0);
            aceptar = false;
        } else {
            empleadoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_JefeCargoD(filtrarLovEmpleados.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroJefeCargoDesempenado");
    }

    private void modificarInfoR_PapelCargoD(int valor) {
        infoRegistroPapelCargoDesempenado = String.valueOf(valor);
    }

    public void eventoFiltrar_PapelCargoD() {
        if (filtrarLovPapeles.size() == 1) {
            papelSeleccionado = filtrarLovPapeles.get(0);
            aceptar = false;
        } else {
            papelSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_PapelCargoD(filtrarLovPapeles.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroPapelCargoDesempenado");
    }

    private void modificarInfoR_MotivoCargoD(int valor) {
        infoRegistroMotivoCargoDesempenado = String.valueOf(valor);
    }

    public void eventoFiltrar_MotivoCargoD() {
        if (filtrarLovMotivosCambiosCargos.size() == 1) {
            motivoCambioCargoSeleccionado = filtrarLovMotivosCambiosCargos.get(0);
            aceptar = false;
        } else {
            motivoCambioCargoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_MotivoCargoD(filtrarLovMotivosCambiosCargos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroMotivoCargoCargoDesempenado");
    }

    private void modificarInfoR_EstrCargoD(int valor) {
        infoRegistroEstructuraCargoDesempenado = String.valueOf(valor);
    }

    public void eventoFiltrar_EstrCargoD() {
        if (filtrarLovEstructuras.size() == 1) {
            estructuraSeleccionada = filtrarLovEstructuras.get(0);
            aceptar = false;
        } else {
            estructuraSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_EstrCargoD(filtrarLovEstructuras.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCargoDesempenado:infoRegistroEstructuraCargoDesempenado");
    }

    private void modificarInfoR_EstrCentroC(int valor) {
        infoRegistroEstructuraCentroCosto = String.valueOf(valor);
    }

    public void eventoFiltrar_EstrCentroC() {
        if (filtrarLovEstructurasCentroCosto.size() == 1) {
            estructuraCentroCostoSeleccionada = filtrarLovEstructurasCentroCosto.get(0);
            aceptar = false;
        } else {
            estructuraCentroCostoSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_EstrCentroC(filtrarLovEstructurasCentroCosto.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCentroCosto:infoRegistroEstructuraCentroCosto");
    }

    private void modificarInfoR_MotivoCentroC(int valor) {
        infoRegistroMotivoCentroCosto = String.valueOf(valor);
    }

    public void eventoFiltrar_MotivoCentroC() {
        if (filtrarLovMotivosCC.size() == 1) {
            motivoLocalizacionSeleccionado = filtrarLovMotivosCC.get(0);
            aceptar = false;
        } else {
            motivoLocalizacionSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_MotivoCentroC(filtrarLovMotivosCC.size());
        RequestContext.getCurrentInstance().update("formLovs:formDCentroCosto:infoRegistroMotivoCentroCosto");
    }

    private void modificarInfoR_MetodoFormaP(int valor) {
        infoRegistroMetodoFormaPago = String.valueOf(valor);
    }

    public void eventoFiltrar_MetodoFormaP() {
        if (filtrarLovMetodosPagos.size() == 1) {
            metodoPagoSeleccionado = filtrarLovMetodosPagos.get(0);
            aceptar = false;
        } else {
            metodoPagoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_MetodoFormaP(filtrarLovMetodosPagos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDFormaPago:infoRegistroMetodoFormaPago");
    }

    private void modificarInfoR_SucursalFormaP(int valor) {
        infoRegistroSucursalFormaPago = String.valueOf(valor);
    }

    public void eventoFiltrar_SucursalFormaP() {
        if (filtrarLovSucursales.size() == 1) {
            sucursalSeleccionada = filtrarLovSucursales.get(0);
            aceptar = false;
        } else {
            sucursalSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_SucursalFormaP(filtrarLovSucursales.size());
        RequestContext.getCurrentInstance().update("formLovs:formDFormaPago:infoRegistroSucursalFormaPago");
    }

    private void modificarInfoR_PeriodFormaPago(int valor) {
        infoRegistroFormaPago = String.valueOf(valor);
    }

    public void eventoFiltrar_PeriodFormaPago() {
        if (filtrarLovPeriodicidades.size() == 1) {
            periodicidadSeleccionada = filtrarLovPeriodicidades.get(0);
            aceptar = false;
        } else {
            periodicidadSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_PeriodFormaPago(filtrarLovPeriodicidades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDFormaPago:infoRegistroFormaFormaPago");
    }

    private void modificarInfoR_MotivoSu(int valor) {
        infoRegistroMotivoSueldo = String.valueOf(valor);
    }

    public void eventoFiltrar_MotivoSu() {
        if (filtrarLovMotivosCambiosSueldos.size() == 1) {
            motivoCambioSueldoSeleccionado = filtrarLovMotivosCambiosSueldos.get(0);
            aceptar = false;
        } else {
            motivoCambioSueldoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_MotivoSu(filtrarLovMotivosCambiosSueldos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDSueldo:infoRegistroMotivoSueldo");
    }

    private void modificarInfoR_TipoSuSu(int valor) {
        infoRegistroTipoSueldoS = String.valueOf(valor);
    }

    public void eventoFiltrar_TipoSuSu() {
        if (filtrarLovTiposSueldos.size() == 1) {
            tipoSueldoSeleccionado = filtrarLovTiposSueldos.get(0);
            aceptar = false;
        } else {
            tipoSueldoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_TipoSuSu(filtrarLovTiposSueldos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDSueldo:infoRegistroTipoSueldoSueldo");
    }

    private void modificarInfoR_UnidadSu(int valor) {
        infoRegistroUnidadSueldo = String.valueOf(valor);
    }

    public void eventoFiltrar_UnidadSu() {
        if (filtrarLovUnidades.size() == 1) {
            unidadSeleccionada = filtrarLovUnidades.get(0);
            aceptar = false;
        } else {
            unidadSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_UnidadSu(filtrarLovUnidades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDSueldo:infoRegistroUnidadSueldo");
    }

    private void modificarInfoR_MotivoTipoCo(int valor) {
        infoRegistroMotivoTipoContrato = String.valueOf(valor);
    }

    public void eventoFiltrar_MotivoTipoCo() {
        if (filtrarLovMotivosContratos.size() == 1) {
            motivoContratoSeleccionado = filtrarLovMotivosContratos.get(0);
            aceptar = false;
        } else {
            motivoContratoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_MotivoTipoCo(filtrarLovMotivosContratos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTipoContrato:infoRegistroMotivoTipoContrato");
    }

    private void modificarInfoR_TipoContrato(int valor) {
        infoRegistroTipoContrato = String.valueOf(valor);
    }

    public void eventoFiltrar_TipoContrato() {
        if (filtrarLovTiposContratos.size() == 1) {
            tipoContratoSeleccionado = filtrarLovTiposContratos.get(0);
            aceptar = false;
        } else {
            tipoContratoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_TipoContrato(filtrarLovTiposContratos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTipoContrato:infoRegistroTipoTipoContrato");
    }

    private void modificarInfoR_TipoTelT(int valor) {
        infoRegistroTipoTelTelefono = String.valueOf(valor);
    }

    public void eventoFiltrar_TipoTelT() {
        if (filtrarLovTiposTelefonos.size() == 1) {
            tipoTelefonoSeleccionado = filtrarLovTiposTelefonos.get(0);
            aceptar = false;
        } else {
            tipoTelefonoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_TipoTelT(filtrarLovTiposTelefonos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTelefono:infoRegistroTipoTelefonoTelefono");
    }

    private void modificarInfoR_CiudadTel(int valor) {
        infoRegistroCiudadTelefono = String.valueOf(valor);
    }

    public void eventoFiltrar_CiudadTel() {
        if (filtrarLovCiudades.size() == 1) {
            ciudadSeleccionada = filtrarLovCiudades.get(0);
            aceptar = false;
        } else {
            ciudadSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_CiudadTel(filtrarLovCiudades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTelefono:infoRegistroCiudadTelefono");
    }

    private void modificarInfoR_NormaL(int valor) {
        infoRegistroNormaLaboral = String.valueOf(valor);
    }

    public void eventoFiltrar_NormaL() {
        if (filtrarLovNormasLaborales.size() == 1) {
            normaLaboralSeleccionada = filtrarLovNormasLaborales.get(0);
            aceptar = false;
        } else {
            normaLaboralSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_NormaL(filtrarLovNormasLaborales.size());
        RequestContext.getCurrentInstance().update("formLovs:formDNormaLaboral:infoRegistroNormaNormaLaboral");
    }

    private void modificarInfoR_ReformaTipoSa(int valor) {
        infoRegistroReformaTipoSalario = String.valueOf(valor);
    }

    public void eventoFiltrar_ReformaTipoSa() {
        if (filtrarLovReformasLaborales.size() == 1) {
            reformaLaboralSeleccionada = filtrarLovReformasLaborales.get(0);
            aceptar = false;
        } else {
            reformaLaboralSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_ReformaTipoSa(filtrarLovReformasLaborales.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTipoSalario:infoRegistroReformaTipoSalario");
    }

    private void modificarInfoR_TerceroAfSuc(int valor) {
        infoRegistroTerceroAfiliacion = String.valueOf(valor);
    }

    public void eventoFiltrar_TerceroAfSuc() {
        if (filtrarLovTercerosSucursales.size() == 1) {
            terceroSucursalSeleccionado = filtrarLovTercerosSucursales.get(0);
            aceptar = false;
        } else {
            terceroSucursalSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_TerceroAfSuc(filtrarLovTercerosSucursales.size());
        RequestContext.getCurrentInstance().update("formLovs:formDAfiliacion:infoRegistroTerceroAfiliacion");
    }

    private void modificarInfoR_CiudadDir(int valor) {
        infoRegistroCiudadDireccion = String.valueOf(valor);
    }

    public void eventoFiltrar_CiudadDir() {
        if (filtrarLovCiudades.size() == 1) {
            ciudadSeleccionada = filtrarLovCiudades.get(0);
            aceptar = false;
        } else {
            ciudadSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_CiudadDir(filtrarLovCiudades.size());
        RequestContext.getCurrentInstance().update("formLovs:formDDireccion:infoRegistroCiudadDireccion");
    }

    private void modificarInfoR_TipoTraTT(int valor) {
        infoRegistroTipoTrabajadorTT = String.valueOf(valor);
    }

    public void eventoFiltrar_TipoTraTT() {
        if (filtrarLovTiposTrabajadores.size() == 1) {
            tipoTrabajadorSeleccionado = filtrarLovTiposTrabajadores.get(0);
            aceptar = false;
        } else {
            tipoTrabajadorSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_TipoTraTT(filtrarLovTiposTrabajadores.size());
        RequestContext.getCurrentInstance().update("formLovs:formDTipoTrabajador:infoRegistroTipoTrabajadorTipoTrabajador");
    }

    private void modificarInfoR_UbicacionUb(int valor) {
        infoRegistroUbicacionUb = String.valueOf(valor);
    }

    public void eventoFiltrar_UbicacionUb() {
        if (filtrarLovUbicacionesGeograficas.size() == 1) {
            ubicacionGeograficaSeleccionada = filtrarLovUbicacionesGeograficas.get(0);
            aceptar = false;
        } else {
            ubicacionGeograficaSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_UbicacionUb(filtrarLovUbicacionesGeograficas.size());
        RequestContext.getCurrentInstance().update("formLovs:formDUbicacion:infoRegistroUbicacionUbicacion");
    }

    private void modificarInfoR_ContratoLL(int valor) {
        infoRegistroContratoLegislacionL = String.valueOf(valor);
    }

    public void eventoFiltrar_ContratoLL() {
        if (filtrarLovContratos.size() == 1) {
            contratoSeleccionado = filtrarLovContratos.get(0);
            aceptar = false;
        } else {
            contratoSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_ContratoLL(filtrarLovContratos.size());
        RequestContext.getCurrentInstance().update("formLovs:formDLegislacionLaboral:infoRegistroContratoLegislacionLaboral");
    }

    private void modificarInfoR_EstadoCivil(int valor) {
        infoRegistroEstadoCivil = String.valueOf(valor);
    }

    public void eventoFiltrar_EstadoCivil() {
        if (filtrarLovEstadosCiviles.size() == 1) {
            estadoCivilSeleccionado = filtrarLovEstadosCiviles.get(0);
            aceptar = false;
        } else {
            estadoCivilSeleccionado = null;
            aceptar = true;
        }
        modificarInfoR_EstadoCivil(filtrarLovEstadosCiviles.size());
        RequestContext.getCurrentInstance().update("formLovs:formDEstadoCivil:infoRegistroEstadoCivilEstadoCivil");
    }

    private void modificarInfoR_JornadaL(int valor) {
        infoRegistroJornadaLaboral = String.valueOf(valor);
    }

    public void eventoFiltrar_JornadaL() {
        if (filtrarLovJornadasLaborales.size() == 1) {
            jornadaLaboralSeleccionada = filtrarLovJornadasLaborales.get(0);
            aceptar = false;
        } else {
            jornadaLaboralSeleccionada = null;
            aceptar = true;
        }
        modificarInfoR_JornadaL(filtrarLovJornadasLaborales.size());
        RequestContext.getCurrentInstance().update("formLovs:formDJornadaLaboral:infoRegistroJornadaJornadaLaboral");
    }

    public Personas getNuevaPersona() {
        return nuevaPersona;
    }

    public void setNuevaPersona(Personas nuevoEmpleado) {
        this.nuevaPersona = nuevoEmpleado;

    }

    public List<Empresas> getLovEmpresas() {
        if (lovEmpresas == null) {
            lovEmpresas = administrarPersonaIndividual.lovEmpresas();
        }
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
        if (lovTiposDocumentos == null) {
            lovTiposDocumentos = administrarPersonaIndividual.lovTiposDocumentos();
        }
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
        if (lovCiudades == null && nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            System.out.println("getLovCiudades:   nuevoEmpleado.getEmpresa().getSecuencia(): " + nuevoEmpleado.getEmpresa().getSecuencia());
            lovCiudades = administrarPersonaIndividual.lovCiudades();
        }
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
        System.out.println("entro en setFechaIngreso con FechaIngreso: " + fechaIngreso);
        this.fechaIngreso = fechaIngreso;
    }

    public Date getFechaCorte() {
        return fechaCorte;
    }

    public void setFechaCorte(Date fechaCorte) {
        System.out.println("entro en setFechaCorte con fechaCorte: " + fechaCorte);
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
        if (lovCargos == null) {
            //lovCargos = administrarPersonaIndividual.lovCargos();
            lovCargos = new ArrayList<Cargos>();
        }
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
        if (lovMotivosCambiosCargos == null) {
            lovMotivosCambiosCargos = administrarPersonaIndividual.lovMotivosCambiosCargos();
        }
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
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null && (fechaIngreso != null) && lovEstructuras == null) {
            System.out.println("getLovEstructuras() : nuevoEmpleado.getEmpresa().getSecuencia(): " + nuevoEmpleado.getEmpresa().getSecuencia());
            System.out.println("getLovEstructuras() : fechaIngreso: " + fechaIngreso);
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
        if (lovPapeles == null) {
            lovPapeles = administrarPersonaIndividual.lovPapeles();
        }
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
        if (lovEmpleados == null) {
            lovEmpleados = administrarPersonaIndividual.lovEmpleados();
        }
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
        if (lovMotivosLocalizaciones == null) {
            lovMotivosLocalizaciones = administrarPersonaIndividual.lovMotivosLocalizaciones();
        }
        return lovMotivosLocalizaciones;
    }

    public void setLovMotivosLocalizaciones(List<MotivosLocalizaciones> lovMotivosLocalizaciones) {
        this.lovMotivosLocalizaciones = lovMotivosLocalizaciones;
    }

    public List<MotivosLocalizaciones> getFiltrarLovMotivosCC() {
        return filtrarLovMotivosCC;
    }

    public void setFiltrarLovMotivosCC(List<MotivosLocalizaciones> filtrarLovMotivosLocalizaciones) {
        this.filtrarLovMotivosCC = filtrarLovMotivosLocalizaciones;
    }

    public MotivosLocalizaciones getMotivoLocalizacionSeleccionado() {
        return motivoLocalizacionSeleccionado;
    }

    public void setMotivoLocalizacionSeleccionado(MotivosLocalizaciones motivoLocalizacionSeleccionado) {
        this.motivoLocalizacionSeleccionado = motivoLocalizacionSeleccionado;
    }

    public List<Estructuras> getLovEstructurasCentroCosto() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null && lovEstructurasCentroCosto == null) {
            System.out.println("getLovEstructurasCentroCosto() : nuevoEmpleado.getEmpresa().getSecuencia() : " + nuevoEmpleado.getEmpresa().getSecuencia());
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
        if (lovTiposTrabajadores == null && nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            System.out.println("getLovTiposTrabajadores : nuevoEmpleado.getEmpresa().getSecuencia() : " + nuevoEmpleado.getEmpresa().getSecuencia());
            lovTiposTrabajadores = administrarPersonaIndividual.lovTiposTrabajadores();
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
        if (lovReformasLaborales == null) {
            lovReformasLaborales = administrarPersonaIndividual.lovReformasLaborales();
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
        if (lovMotivosCambiosSueldos == null) {
            lovMotivosCambiosSueldos = administrarPersonaIndividual.lovMotivosCambiosSueldos();
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

    public MotivosCambiosSueldos getMotivoCambioSueldoSeleccionado() {
        return motivoCambioSueldoSeleccionado;
    }

    public void setMotivoCambioSueldoSeleccionado(MotivosCambiosSueldos motivoCambioSueldoSeleccionado) {
        this.motivoCambioSueldoSeleccionado = motivoCambioSueldoSeleccionado;
    }

    public List<TiposSueldos> getLovTiposSueldos() {
        if (lovTiposSueldos == null) {
            lovTiposSueldos = administrarPersonaIndividual.lovTiposSueldos();
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

    public List<Unidades> getLovUnidades() {
        if (lovUnidades == null) {
            lovUnidades = administrarPersonaIndividual.lovUnidades();
            if (lovUnidades != null) {
                if (!lovUnidades.isEmpty()) {
                    for (int i = 0; i < lovUnidades.size(); i++) {
                        if (lovUnidades.get(i).getNombre().equals("PESOS")) {
                            unidadPesos = lovUnidades.get(i);
                            System.out.println("unidadPesos = " + unidadPesos.getNombre());
                        }
                    }
                }
            }
        }
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
        if (lovMotivosContratos == null) {
            lovMotivosContratos = administrarPersonaIndividual.lovMotivosContratos();
        }
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
        if (lovTiposContratos == null) {
            lovTiposContratos = administrarPersonaIndividual.lovTiposContratos();
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

    public VigenciasNormasEmpleados getNuevaVigenciaNormaEmpleado() {
        return nuevaVigenciaNormaEmpleado;
    }

    public void setNuevaVigenciaNormaEmpleado(VigenciasNormasEmpleados nuevaVigenciaNormaEmpleado) {
        this.nuevaVigenciaNormaEmpleado = nuevaVigenciaNormaEmpleado;
    }

    public List<NormasLaborales> getLovNormasLaborales() {
        if (lovNormasLaborales == null) {
            lovNormasLaborales = administrarPersonaIndividual.lovNormasLaborales();
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

    public VigenciasContratos getNuevaVigenciaContrato() {
        return nuevaVigenciaContrato;
    }

    public void setNuevaVigenciaContrato(VigenciasContratos nuevaVigenciaContrato) {
        this.nuevaVigenciaContrato = nuevaVigenciaContrato;
    }

    public List<Contratos> getLovContratos() {
        if (lovContratos == null) {
            lovContratos = administrarPersonaIndividual.lovContratos();
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

    public VigenciasUbicaciones getNuevaVigenciaUbicacion() {
        return nuevaVigenciaUbicacion;
    }

    public void setNuevaVigenciaUbicacion(VigenciasUbicaciones nuevaVigenciaUbicacion) {
        this.nuevaVigenciaUbicacion = nuevaVigenciaUbicacion;
    }

    public List<UbicacionesGeograficas> getLovUbicacionesGeograficas() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null && lovUbicacionesGeograficas == null) {
            System.out.println("getLovUbicacionesGeograficas : nuevoEmpleado.getEmpresa().getSecuencia(): " + nuevoEmpleado.getEmpresa().getSecuencia());
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
        if (lovPeriodicidades == null) {
            lovPeriodicidades = administrarPersonaIndividual.lovPeriodicidades();
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

    public List<Sucursales> getLovSucursales() {
        if (lovSucursales == null) {
            lovSucursales = administrarPersonaIndividual.lovSucursales();
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

    public List<MetodosPagos> getLovMetodosPagos() {
        if (lovMetodosPagos == null) {
            lovMetodosPagos = administrarPersonaIndividual.lovMetodosPagos();
        }
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
        if (lovJornadasLaborales == null) {
            lovJornadasLaborales = administrarPersonaIndividual.lovJornadasLaborales();
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
        if (lovTercerosSucursales == null && nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            System.out.println("getLovTercerosSucursales : nuevoEmpleado.getEmpresa().getSecuencia() : " + nuevoEmpleado.getEmpresa().getSecuencia());
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
        if (lovEstadosCiviles == null) {
            lovEstadosCiviles = administrarPersonaIndividual.lovEstadosCiviles();
        }
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
        if (lovTiposTelefonos == null) {
            lovTiposTelefonos = administrarPersonaIndividual.lovTiposTelefonos();
        }
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

    public String getInfoRegistroEmpresaInfoPersonal() {
        return infoRegistroEmpresaInfoPersonal;
    }

    public String getInfoRegistroTipoDocInfoPersonal() {
        return infoRegistroTipoDocInfoPersonal;
    }

    public String getInfoRegistroCiudadDocInfoPersonal() {
        return infoRegistroCiudadDocInfoPersonal;
    }

    public String getInfoRegistroCiudadNacInfoPersonal() {
        return infoRegistroCiudadNacInfoPersonal;
    }

    public String getInfoRegistroCargoDesempenado() {
        return infoRegistroCargoDesempenado;
    }

    public String getInfoRegistroMotivoCargoDesempenado() {
        return infoRegistroMotivoCargoDesempenado;
    }

    public String getInfoRegistroEstructuraCargoDesempenado() {
        return infoRegistroEstructuraCargoDesempenado;
    }

    public String getInfoRegistroPapelCargoDesempenado() {
        return infoRegistroPapelCargoDesempenado;
    }

    public String getInfoRegistroJefeCargoDesempenado() {
        return infoRegistroJefeCargoDesempenado;
    }

    public String getInfoRegistroMotivoCentroCosto() {
        return infoRegistroMotivoCentroCosto;
    }

    public String getInfoRegistroEstructuraCentroCosto() {
        return infoRegistroEstructuraCentroCosto;
    }

    public String getInfoRegistroTipoTrabajadorTT() {
        return infoRegistroTipoTrabajadorTT;
    }

    public String getInfoRegistroReformaTipoSalario() {
        return infoRegistroReformaTipoSalario;
    }

    public String getInfoRegistroMotivoSueldo() {
        return infoRegistroMotivoSueldo;
    }

    public String getInfoRegistroTipoSueldoS() {
        return infoRegistroTipoSueldoS;
    }

    public String getInfoRegistroUnidadSueldo() {
        return infoRegistroUnidadSueldo;
    }

    public String getInfoRegistroMotivoTipoContrato() {
        return infoRegistroMotivoTipoContrato;
    }

    public String getInfoRegistroTipoContrato() {
        return infoRegistroTipoContrato;
    }

    public String getInfoRegistroNormaLaboral() {
        return infoRegistroNormaLaboral;
    }

    public String getInfoRegistroContratoLegislacionL() {
        return infoRegistroContratoLegislacionL;
    }

    public String getInfoRegistroUbicacionUb() {
        return infoRegistroUbicacionUb;
    }

    public String getInfoRegistroJornadaLaboral() {
        return infoRegistroJornadaLaboral;
    }

    public String getInfoRegistroFormaPago() {
        return infoRegistroFormaPago;
    }

    public String getInfoRegistroSucursalFormaPago() {
        return infoRegistroSucursalFormaPago;
    }

    public String getInfoRegistroMetodoFormaPago() {
        return infoRegistroMetodoFormaPago;
    }

    public String getInfoRegistroTerceroAfiliacion() {
        return infoRegistroTerceroAfiliacion;
    }

    public String getInfoRegistrolEstadoCivil() {
        return infoRegistroEstadoCivil;
    }

    public String getInfoRegistroCiudadDireccion() {
        return infoRegistroCiudadDireccion;
    }

    public String getInfoRegistroCiudadTelefono() {
        return infoRegistroCiudadTelefono;
    }

    public String getInfoRegistroTipoTelTelefono() {
        return infoRegistroTipoTelTelefono;
    }

    public boolean isDisableCamposDependientesTipoT() {
        return disableCamposDependientesTipoT;
    }

    public void setDisableCamposDependientesTipoT(boolean disableCamposDependientesTipoTrabajador) {
        this.disableCamposDependientesTipoT = disableCamposDependientesTipoTrabajador;
    }

    public String getMensajeErrorFechasEmpleado() {
        return mensajeErrorFechasEmpleado;
    }

    public void setMensajeErrorFechasEmpleado(String mensajeErrorFechasEmpleado) {
        this.mensajeErrorFechasEmpleado = mensajeErrorFechasEmpleado;
    }

    public Unidades getUnidadPesos() {
        return unidadPesos;
    }

    public void setUnidadPesos(Unidades unidadPesos) {
        this.unidadPesos = unidadPesos;
    }
    
}

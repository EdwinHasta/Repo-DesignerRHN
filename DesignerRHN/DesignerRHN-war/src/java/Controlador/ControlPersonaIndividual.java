package Controlador;

import Entidades.Cargos;
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
    private EstadosCiviles nuevoEstadoCivil;
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
    private boolean aceptar;
    private BigInteger l;
    private int k;

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
        nuevaVigenciaLocalizacion.setMotivo(new MotivosLocalizaciones());
        nuevaVigenciaLocalizacion.setLocalizacion(new Estructuras());
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
        nuevoEstadoCivil = new EstadosCiviles();
        nuevaDireccion = new Direcciones();
        nuevaDireccion.setDireccionalternativa(" ");
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

    public void botonListaValores() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexInformacionPersonal >= 0) {
            if (indexInformacionPersonal == 0) {
                context.update("form:EmpresaInformacionPersonalDialogo");
                context.execute("EmpresaInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 5) {
                context.update("form:TipoDocumentoInformacionPersonalDialogo");
                context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 7) {
                context.update("form:CiudadDocumentoInformacionPersonalDialogo");
                context.execute("CiudadDocumentoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 9) {
                context.update("form:CiudadNacimientoInformacionPersonalDialogo");
                context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
                indexInformacionPersonal = -1;
            }
        }
        if (indexCargoDesempeñado >= 0) {
            if (indexCargoDesempeñado == 0) {
                context.update("form:CargoCargoDesempeñadoDialogo");
                context.execute("CargoCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 1) {
                context.update("form:MotivoCambioCargoCargoDesempeñadoDialogo");
                context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 2) {
                context.update("form:EstructuraCargoDesempeñadoDialogo");
                context.execute("EstructuraCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 3) {
                context.update("form:PapelCargoDesempeñadoDialogo");
                context.execute("PapelCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 4) {
                context.update("form:EmpleadoJefeCargoDesempeñadoDialogo");
                context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                indexCargoDesempeñado = -1;
            }
        }
        if (indexCentroCosto >= 0) {
            if (indexCentroCosto == 0) {
                context.update("form:MotivoLocalizacionCentroCostoDialogo");
                context.execute("MotivoLocalizacionCentroCostoDialogo.show()");
                indexCentroCosto = -1;
            }
            if (indexCentroCosto == 1) {
                context.update("form:EstructuraCentroCostoDialogo");
                context.execute("EstructuraCentroCostoDialogo.show()");
                indexCentroCosto = -1;
            }
        }
        if (indexTipoTrabajador >= 0) {
            if (indexTipoTrabajador == 0) {
                context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
                context.execute("TipoTrabajadorTipoTrabajadorDialogo.show()");
                indexTipoTrabajador = -1;
            }
        }
        if (indexTipoSalario >= 0) {
            if (indexTipoSalario == 0) {
                context.update("form:ReformaLaboralTipoSalarioDialogo");
                context.execute("ReformaLaboralTipoSalarioDialogo.show()");
                indexTipoSalario = -1;
            }
        }
        if (indexSueldo >= 0) {
            if (indexSueldo == 1) {
                context.update("form:MotivoCambioSueldoSueldoDialogo");
                context.execute("MotivoCambioSueldoSueldoDialogo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 2) {
                context.update("form:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
                indexSueldo = -1;
            }
            if (indexSueldo == 3) {
                context.update("form:UnidadSueldoDialogo");
                context.execute("UnidadSueldoDialogo.show()");
                indexSueldo = -1;
            }
        }
        if (indexTipoContrato >= 0) {
            if (indexTipoContrato == 0) {
                context.update("form:MotivoContratoTipoContratoDialogo");
                context.execute("MotivoContratoTipoContratoDialogo.show()");
                indexTipoContrato = -1;
            }
            if (indexTipoContrato == 1) {
                context.update("form:TipoContratoTipoContratoDialogo");
                context.execute("TipoContratoTipoContratoDialogo.show()");
                indexTipoContrato = -1;
            }
        }
        if (indexNormaLaboral >= 0) {
            if (indexNormaLaboral == 0) {
                context.update("form:NormaLaboralNormaLaboralDialogo");
                context.execute("NormaLaboralNormaLaboralDialogo.show()");
                indexNormaLaboral = -1;
            }
        }
        if (indexLegislacionLaboral >= 0) {
            if (indexLegislacionLaboral == 0) {
                context.update("form:ContratoLegislacionLaboralDialogo");
                context.execute("ContratoLegislacionLaboralDialogo.show()");
                indexLegislacionLaboral = -1;
            }
        }
        if (indexUbicacionGeografica >= 0) {
            if (indexUbicacionGeografica == 0) {
                context.update("form:UbicacionUbicacionGeograficaDialogo");
                context.execute("UbicacionUbicacionGeograficaDialogo.show()");
                indexUbicacionGeografica = -1;
            }
        }
        if (indexJornadaLaboral >= 0) {
            if (indexJornadaLaboral == 0) {
                context.update("form:JornadaJornadaLaboralDialogo");
                context.execute("JornadaJornadaLaboralDialogo.show()");
                indexJornadaLaboral = -1;
            }
        }
        if (indexFormaPago >= 0) {
            if (indexFormaPago == 0) {
                context.update("form:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 3) {
                context.update("form:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 4) {
                context.update("form:MetodoPagoFormaPagoDialogo");
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
        }

        if (indexFormaPago >= 0) {
            if (indexFormaPago == 0) {
                context.update("form:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 3) {
                context.update("form:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
            if (indexFormaPago == 4) {
                context.update("form:MetodoPagoFormaPagoDialogo");
                context.execute("MetodoPagoFormaPagoDialogo.show()");
                indexFormaPago = -1;
            }
        }
        if (indexAfiliacionAFP >= 0) {
            if (indexAfiliacionAFP == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                indexAfiliacionAFP = -1;
            }
        }
        if (indexAfiliacionARP >= 0) {
            if (indexAfiliacionARP == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                indexAfiliacionARP = -1;
            }
        }
        if (indexAfiliacionCaja >= 0) {
            if (indexAfiliacionCaja == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                indexAfiliacionCaja = -1;
            }
        }
        if (indexAfiliacionEPS >= 0) {
            if (indexAfiliacionEPS == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                indexAfiliacionEPS = -1;
            }
        }
        if (indexAfiliacionFondo >= 0) {
            if (indexAfiliacionFondo == 0) {
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
                indexAfiliacionFondo = -1;
            }
        }
        if (indexEstadoCivil >= 0) {
            if (indexEstadoCivil == 0) {
                context.update("form:EstadoCivilEstadoCivilDialogo");
                context.execute("EstadoCivilEstadoCivilDialogo.show()");
                indexEstadoCivil = -1;
            }
        }
        if (indexDireccion >= 0) {
            if (indexDireccion == 1) {
                context.update("form:CiudadDireccionDialogo");
                context.execute("CiudadDireccionDialogo.show()");
                indexDireccion = -1;
            }
        }
        if (indexTelefono >= 0) {
            if (indexTelefono == 0) {
                context.update("form:TipoTelefonoTelefonoDialogo");
                context.execute("TipoTelefonoTelefonoDialogo.show()");
                indexTelefono = -1;
            }
            if (indexTelefono == 1) {
                context.update("form:CiudadTelefonoDialogo");
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
        nuevaVigenciaLocalizacion.setMotivo(new MotivosLocalizaciones());
        nuevaVigenciaLocalizacion.setLocalizacion(new Estructuras());
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
        nuevoEstadoCivil = new EstadosCiviles();
        nuevaDireccion = new Direcciones();
        nuevaDireccion.setDireccionalternativa(" ");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:scrollPrincipal");
    }

    public boolean validarCamposObligatoriosEmpleado() {
        boolean retorno = true;
        if (nuevoEmpleado.getEmpresa().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaPersona.getPrimerapellido() == null || nuevaPersona.getPrimerapellido().isEmpty()) {
            retorno = false;
        }
        if (nuevaPersona.getNombre() == null || nuevaPersona.getNombre().isEmpty()) {
            retorno = false;
        }
        if (nuevaPersona.getTipodocumento().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaPersona.getSexo() == null) {
            retorno = false;
        }
        if (nuevaPersona.getNumerodocumento() == null) {
            retorno = false;
        }
        if (nuevaPersona.getCiudaddocumento().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaPersona.getCiudadnacimiento().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaPersona.getFechanacimiento() == null) {
            retorno = false;
        }
        if (fechaIngreso == null) {
            retorno = false;
        }
        if (nuevoEmpleado.getCodigoempleado() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaCargo.getCargo().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaVigenciaCargo.getMotivocambiocargo().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaVigenciaCargo.getEstructura().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaLocalizacion.getMotivo().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaVigenciaLocalizacion.getLocalizacion().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaTipoTrabajador.getTipotrabajador().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaSueldo.getValor().doubleValue() <= 0 || nuevaVigenciaSueldo.getValor() == null) {
            retorno = false;
        }
        if (nuevaVigenciaSueldo.getMotivocambiosueldo().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaTipoContrato.getMotivocontrato().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaVigenciaTipoContrato.getTipocontrato().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaNormaEmpleado.getNormalaboral().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaContrato.getTipocontrato().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaUbicacion.getUbicacion().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaJornada.getJornadatrabajo().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaFormaPago.getFormapago().getSecuencia() == null) {
            retorno = false;
        }
        if (nuevaVigenciaFormaPago.getMetodopago().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaAfiliacionEPS.getTercerosucursal().getSecuencia() == null) {
            retorno = false;
        }
        //
        if (nuevaVigenciaAfiliacionARP.getTercerosucursal().getSecuencia() == null) {
            retorno = false;
        }
        return retorno;
    }

    public void crearNuevoEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposObligatoriosEmpleado() == true) {
            k++;
            l = BigInteger.valueOf(k);
            String checkIntegral = administrarPersonaIndividual.obtenerCheckIntegralReformaLaboral(nuevaVigenciaReformaLaboral.getReformalaboral().getSecuencia());
            nuevaPersona.setSecuencia(l);
            administrarPersonaIndividual.crearNuevaPersona(nuevaPersona);
            Personas personaAlmacenada = administrarPersonaIndividual.obtenerUltimoRegistroPersona();
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
            nuevaVigenciaSueldo.setSecuencia(l);
            nuevaVigenciaSueldo.setEmpleado(empleadoAlmacenado); //
            nuevaVigenciaSueldo.setFechavigencia(fechaIngreso); //
            nuevaVigenciaSueldo.setValor(valorSueldo);//
            nuevaVigenciaSueldo.setFechasistema(new Date());//
            nuevaVigenciaSueldo.setFechavigenciaretroactivo(fechaIngreso);//
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

            /*Opcionales*/
            if (nuevoEstadoCivil.getSecuencia() != null) {
                k++;
                l = BigInteger.valueOf(k);
                nuevoEstadoCivil.setSecuencia(l);
                administrarPersonaIndividual.crearEstadoCivil(nuevoEstadoCivil);
            }

            if ((nuevaDireccion.getDireccionalternativa() != null && !nuevaDireccion.getDireccionalternativa().isEmpty()) || nuevaDireccion.getCiudad().getSecuencia() != null) {
                k++;
                l = BigInteger.valueOf(k);
                nuevaDireccion.setSecuencia(l);
                administrarPersonaIndividual.crearDireccion(nuevaDireccion);
            }

            if (nuevoTelefono.getCiudad().getSecuencia() != null || nuevoTelefono.getTipotelefono().getSecuencia() != null || nuevoTelefono.getNumerotelefono() > 0) {
                k++;
                l = BigInteger.valueOf(k);
                nuevoTelefono.setSecuencia(l);
                administrarPersonaIndividual.crearTelefono(nuevoTelefono);
            }

            /*Opcionales*/
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

            Procesos procesoCodigo1 = null;
            short cod1 = 1;
            procesoCodigo1 = administrarPersonaIndividual.buscarProcesoPorCodigo(cod1);
            Procesos procesoCodigo80 = null;
            short cod80 = 80;
            procesoCodigo80 = administrarPersonaIndividual.buscarProcesoPorCodigo(cod80);

            BigInteger numeroComprobante = administrarPersonaIndividual.obtenerNumeroMaximoComprobante();

            Comprobantes comprobante = new Comprobantes();
            comprobante.setEmpleado(empleadoAlmacenado);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaIngreso); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            Date fecha = calendar.getTime();

            comprobante.setFecha(fecha);

            String valorComprobante = String.valueOf(numeroComprobante.intValue() + 1);

            comprobante.setNumero(new BigInteger(valorComprobante));
            comprobante.setValor(new BigDecimal("0.01"));

            k++;
            l = BigInteger.valueOf(k);
            comprobante.setSecuencia(l);

            administrarPersonaIndividual.crearComprobante(comprobante);

            Comprobantes comprobanteEmpleado = administrarPersonaIndividual.buscarComprobanteParaPrimerRegistroEmpleado(empleadoAlmacenado.getSecuencia());

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
                corte2.setProceso(procesoCodigo1);
                k++;
                l = BigInteger.valueOf(k);
                corte2.setSecuencia(l);
                administrarPersonaIndividual.crearCortesProcesos(corte2);
            }
            /*
            TiposEntidades codigo12 = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("12"));
            nuevaVigenciaAfiliacionEPS.setTipoentidad(codigo12);
            VigenciasTiposTrabajadores nuevaVigenciaTT = new VigenciasTiposTrabajadores();
            nuevaVigenciaTT.setEmpleado(empleadoAlmacenado);
            Date fechaNuevo = new Date(1, 1, 60);
            nuevaVigenciaTT.setFechavigencia(fechaNuevo);
            nuevaVigenciaTT.setTipotrabajador(codigo12);
            */
            /*
             DECLARE
             v_rfTT NUMBER;
             BEGIN
             SELECT secuencia INTO v_rfTT
             FROM tipostrabajadores WHERE codigo=12;
  	
             INSERT INTO VIGENCIASTIPOSTRABAJADORES
             (EMPLEADO, FECHAVIGENCIA, TIPOTRABAJADOR)
             VALUES (:EMPLEADOS.SECUENCIA, to_date('01011960','ddmmyyyy'), v_rfTT);
             EXCEPTION WHEN OTHERS THEN NULL;
             END;
             */
            context.execute("procesoGuardadoOK.show()");
        } else {
            context.execute("errorCamposObligatorios.show()");
        }
    }

    public void cambiarIndiceInformacionPersonal(int i) {
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
            auxInformacionPersonalCiudadDocumento = nuevaPersona.getCiudaddocumento().getNombre();
            auxInformacionPersonalCiudadNacimiento = nuevaPersona.getCiudadnacimiento().getNombre();
            auxInformacionPersonalTipoDocumento = nuevaPersona.getTipodocumento().getNombrelargo();
            auxInformacionPersonaEmpresal = nuevoEmpleado.getEmpresa().getNombre();
            auxFechaCorte = fechaCorte;
            auxFechaIngreso = fechaIngreso;
            auxFechaNacimiento = nuevaPersona.getFechanacimiento();
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
            auxCargoDesempeñadoNombreCargo = nuevaVigenciaCargo.getCargo().getNombre();
            auxCargoDesempeñadoMotivoCargo = nuevaVigenciaCargo.getMotivocambiocargo().getNombre();
            auxCargoDesempeñadoNombreEstructura = nuevaVigenciaCargo.getEstructura().getNombre();
            auxCargoDesempeñadoPapel = nuevaVigenciaCargo.getPapel().getDescripcion();
            auxCargoDesempeñadoEmpleadoJefe = nuevaVigenciaCargo.getEmpleadojefe().getPersona().getNombreCompleto();
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
            auxCentroCostoMotivo = nuevaVigenciaLocalizacion.getMotivo().getDescripcion();
            auxCentroCostoEstructura = nuevaVigenciaLocalizacion.getLocalizacion().getNombre();
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
            auxTipoTrabajadorNombreTipoTrabajador = nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre();
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
            auxTipoSalarioReformaLaboral = nuevaVigenciaReformaLaboral.getReformalaboral().getNombre();
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
            auxSueldoMotivoSueldo = nuevaVigenciaSueldo.getMotivocambiosueldo().getNombre();
            auxSueldoTipoSueldo = nuevaVigenciaSueldo.getTiposueldo().getDescripcion();
            auxSueldoUnidad = nuevaVigenciaSueldo.getUnidadpago().getNombre();
            auxSueldoValor = valorSueldo;
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
            auxTipoContratoMotivo = nuevaVigenciaTipoContrato.getTipocontrato().getNombre();
            auxTipoContratoTipoContrato = nuevaVigenciaTipoContrato.getMotivocontrato().getNombre();
            auxTipoContratoFecha = nuevaVigenciaTipoContrato.getFechavigencia();
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
            auxNormaLaboralNorma = nuevaVigenciaNormaEmpleado.getNormalaboral().getNombre();
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
            auxLegislacionLaboralNombre = nuevaVigenciaContrato.getContrato().getDescripcion();
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
            auxUbicacionGeograficaUbicacion = nuevaVigenciaUbicacion.getUbicacion().getDescripcion();
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
            auxFormaPagoMetodo = nuevaVigenciaFormaPago.getFormapago().getNombre();
            auxFormaPagoPeriodicidad = nuevaVigenciaFormaPago.getFormapago().getNombre();
            auxFormaPagoSucursal = nuevaVigenciaFormaPago.getSucursal().getNombre();
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
            auxJornadaLaboralJornada = nuevaVigenciaJornada.getJornadatrabajo().getDescripcion();
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
            auxAfiliacionEPS = nuevaVigenciaAfiliacionEPS.getTercerosucursal().getDescripcion();
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
            auxAfiliacionARP = nuevaVigenciaAfiliacionARP.getTercerosucursal().getDescripcion();
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
            auxEstadoCivilEstado = nuevoEstadoCivil.getDescripcion();
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
            auxEstadoCivilEstado = nuevoEstadoCivil.getDescripcion();
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
            context.execute("errorEmpleadoRegistrado.show()");
        }
        if (persona != null && empleado == null) {
            context.execute("errorPersonaRepetida.show()");
        }
        String contabilidad = administrarPersonaIndividual.obtenerPreValidadContabilidad();
        System.out.println("contabilidad : " + contabilidad);
        String bloqueAIngreso = administrarPersonaIndividual.obtenerPreValidaBloqueAIngreso();
        System.out.println("bloqueAIngreso : " + bloqueAIngreso);
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
            nuevaPersona.setFechanacimiento(auxFechaNacimiento);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:fechaNacimientoModPersonal");
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
            fechaIngreso = auxFechaIngreso;
            context.update("form:fechaIngresoModPersonal");
            context.execute("errorFechas.show()");
            modificacionesEmpresaFechaIngresoInformacionPersonal();
        }
    }

    public void modificarFechaUltimoPagoInformacionPersonal(int i) {
        boolean retorno = validarFechasInformacionPersonal(13);
        if (retorno == true) {
            cambiarIndiceInformacionPersonal(i);
        } else {
            fechaCorte = auxFechaCorte;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:fechaUltimoPagoModPersonal");
            context.execute("errorFechas.show()");
        }
    }

    public void modificarFechaFechaVigenciaTipoContrato(int i) {
        boolean retorno = validarFechasTipoContrato();
        if (retorno == true) {
            if (fechaIngreso != null && nuevaVigenciaTipoContrato.getFechavigencia() != null) {
                if (nuevaVigenciaTipoContrato.getFechavigencia().before(fechaIngreso)) {
                    nuevaVigenciaTipoContrato.setFechavigencia(auxTipoContratoFecha);
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:fechaFinalContratoModTipoContrato");
                    context.execute("errorFechaContratoFechaIngreso.show()");
                } else {
                    cambiarIndiceTipoContrato(i);
                }
            }
        } else {
            nuevaVigenciaTipoContrato.setFechavigencia(auxTipoContratoFecha);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:fechaFinalContratoModTipoContrato");
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
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:EmpresaInformacionPersonalDialogo");
                context.execute("EmpresaInformacionPersonalDialogo.show()");
                context.update("form:empresaModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPODOCUMENTO")) {
            nuevaPersona.getTipodocumento().setNombrelargo(auxInformacionPersonalTipoDocumento);
            for (int i = 0; i < lovTiposDocumentos.size(); i++) {
                if (lovTiposDocumentos.get(i).getNombrelargo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setTipodocumento(lovTiposDocumentos.get(indiceUnicoElemento));
                lovTiposDocumentos.clear();
                getLovTiposDocumentos();
                context.update("form:tipoDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                context.update("form:TipoDocumentoInformacionPersonalDialogo");
                context.execute("TipoDocumentoInformacionPersonalDialogo.show()");
                context.update("form:tipoDocumentoModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("NACIMIENTO")) {
            nuevaPersona.getCiudadnacimiento().setNombre(auxInformacionPersonalCiudadNacimiento);
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setCiudadnacimiento(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadNacimientoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                context.update("form:CiudadNacimientoInformacionPersonalDialogo");
                context.execute("CiudadNacimientoInformacionPersonalDialogo.show()");
                context.update("form:ciudadNacimientoModPersonal");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("DOCUMENTO")) {
            nuevaPersona.getCiudaddocumento().setNombre(auxInformacionPersonalCiudadDocumento);
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaPersona.setCiudaddocumento(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadDocumentoModPersonal");
            } else {
                permitirIndexInformacionPersonal = false;
                context.update("form:CiudadDocumentoInformacionPersonalDialogo");
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
            for (int i = 0; i < lovCargos.size(); i++) {
                if (lovCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setCargo(lovCargos.get(indiceUnicoElemento));
                lovCargos.clear();
                getLovCargos();
                context.update("form:cargoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                context.update("form:CargoCargoDesempeñadoDialogo");
                context.execute("CargoCargoDesempeñadoDialogo.show()");
                context.update("form:cargoModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaCargo.getMotivocambiocargo().setNombre(auxCargoDesempeñadoMotivoCargo);
            for (int i = 0; i < lovMotivosCambiosCargos.size(); i++) {
                if (lovMotivosCambiosCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setMotivocambiocargo(lovMotivosCambiosCargos.get(indiceUnicoElemento));
                lovMotivosCambiosCargos.clear();
                getLovMotivosCambiosCargos();
                context.update("form:motivoModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                context.update("form:MotivoCambioCargoCargoDesempeñadoDialogo");
                context.execute("MotivoCambioCargoCargoDesempeñadoDialogo.show()");
                context.update("form:motivoModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            nuevaVigenciaCargo.getEstructura().setNombre(auxCargoDesempeñadoNombreEstructura);
            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setEstructura(lovEstructuras.get(indiceUnicoElemento));
                lovEstructuras.clear();
                getLovEstructuras();
                context.update("form:estructuraModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                getLovEstructuras();
                context.update("form:EstructuraCargoDesempeñadoDialogo");
                context.execute("EstructuraCargoDesempeñadoDialogo.show()");
                context.update("form:estructuraModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PAPEL")) {
            nuevaVigenciaCargo.getPapel().setDescripcion(auxCargoDesempeñadoPapel);
            for (int i = 0; i < lovPapeles.size(); i++) {
                if (lovPapeles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setPapel(lovPapeles.get(indiceUnicoElemento));
                lovPapeles.clear();
                getLovPapeles();
                context.update("form:papelModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                context.update("form:PapelCargoDesempeñadoDialogo");
                context.execute("PapelCargoDesempeñadoDialogo.show()");
                context.update("form:papelModCargoDesempeñado");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("JEFE")) {
            nuevaVigenciaCargo.getEmpleado().getPersona().setNombreCompleto(auxCargoDesempeñadoEmpleadoJefe);
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaCargo.setEmpleadojefe(lovEmpleados.get(indiceUnicoElemento));
                lovEmpleados.clear();
                getLovEmpleados();
                context.update("form:empleadoJefeModCargoDesempeñado");
            } else {
                permitirIndexCargoDesempeñado = false;
                context.update("form:EmpleadoJefeCargoDesempeñadoDialogo");
                context.execute("EmpleadoJefeCargoDesempeñadoDialogo.show()");
                context.update("form:empleadoJefeModCargoDesempeñado");
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
            for (int i = 0; i < lovEstructurasCentroCosto.size(); i++) {
                if (lovEstructurasCentroCosto.get(i).getCentrocosto().getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaLocalizacion.setLocalizacion(lovEstructurasCentroCosto.get(indiceUnicoElemento));
                lovEstructurasCentroCosto.clear();
                getLovEstructurasCentroCosto();
                context.update("form:estructuraModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                context.update("form:EstructuraCentroCostoDialogo");
                context.execute("EstructuraCentroCostoDialogo.show()");
                context.update("form:estructuraModCentroCosto");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("MOTIVO")) {
            nuevaVigenciaLocalizacion.getMotivo().setDescripcion(auxCentroCostoMotivo);
            for (int i = 0; i < lovMotivosLocalizaciones.size(); i++) {
                if (lovMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaLocalizacion.setMotivo(lovMotivosLocalizaciones.get(indiceUnicoElemento));
                lovMotivosLocalizaciones.clear();
                getLovMotivosLocalizaciones();
                context.update("form:motivoModCentroCosto");
            } else {
                permitirIndexCentroCosto = false;
                context.update("form:MotivoLocalizacionCentroCostoDialogo");
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
            for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaTipoTrabajador.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                lovTiposTrabajadores.clear();
                getLovTiposTrabajadores();
                context.update("form:tipoTrabajadorModTipoTrabajador");
            } else {
                permitirIndexTipoTrabajador = false;
                context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
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
            for (int i = 0; i < lovReformasLaborales.size(); i++) {
                if (lovReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:ReformaLaboralTipoSalarioDialogo");
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
            for (int i = 0; i < lovMotivosCambiosSueldos.size(); i++) {
                if (lovMotivosCambiosSueldos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaSueldo.setMotivocambiosueldo(lovMotivosCambiosSueldos.get(indiceUnicoElemento));
                lovMotivosCambiosSueldos.clear();
                getLovMotivosCambiosSueldos();
                context.update("form:motivoSueldoModSueldo");
            } else {
                permitirIndexSueldo = false;
                context.update("form:MotivoCambioSueldoSueldoDialogo");
                context.execute("MotivoCambioSueldoSueldoDialogo.show()");
                context.update("form:motivoSueldoModSueldo");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPOSUELDO")) {
            nuevaVigenciaSueldo.getTiposueldo().setDescripcion(auxSueldoTipoSueldo);
            for (int i = 0; i < lovTiposSueldos.size(); i++) {
                if (lovTiposSueldos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:TipoSueldoSueldoDialogo");
                context.execute("TipoSueldoSueldoDialogo.show()");
                context.update("form:tipoSueldoModSueldo");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("UNIDAD")) {
            nuevaVigenciaSueldo.getUnidadpago().setNombre(auxSueldoUnidad);
            for (int i = 0; i < lovUnidades.size(); i++) {
                if (lovUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaSueldo.setUnidadpago(lovUnidades.get(indiceUnicoElemento));
                lovUnidades.clear();
                getLovUnidades();
                context.update("form:unidadPagoModSueldo");
            } else {
                permitirIndexSueldo = false;
                context.update("form:UnidadSueldoDialogo");
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
            for (int i = 0; i < lovMotivosContratos.size(); i++) {
                if (lovMotivosContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaTipoContrato.setMotivocontrato(lovMotivosContratos.get(indiceUnicoElemento));
                lovMotivosContratos.clear();
                getLovMotivosContratos();
                context.update("form:motivoContratoModTipoContrato");
            } else {
                permitirIndexTipoContrato = false;
                context.update("form:MotivoContratoTipoContratoDialogo");
                context.execute("MotivoContratoTipoContratoDialogo.show()");
                context.update("form:motivoContratoModTipoContrato");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            nuevaVigenciaTipoContrato.getTipocontrato().setNombre(auxTipoContratoTipoContrato);
            for (int i = 0; i < lovTiposContratos.size(); i++) {
                if (lovTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:TipoContratoTipoContratoDialogo");
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
            for (int i = 0; i < lovNormasLaborales.size(); i++) {
                if (lovNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:NormaLaboralNormaLaboralDialogo");
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
            for (int i = 0; i < lovContratos.size(); i++) {
                if (lovContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:ContratoLegislacionLaboralDialogo");
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
            for (int i = 0; i < lovUbicacionesGeograficas.size(); i++) {
                if (lovUbicacionesGeograficas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaUbicacion.setUbicacion(lovUbicacionesGeograficas.get(indiceUnicoElemento));
                lovUbicacionesGeograficas.clear();
                getLovUbicacionesGeograficas();
                context.update("form:ubicacionGeograficaModUbicacionGeografica");
            } else {
                permitirIndexUbicacionGeografica = false;
                context.update("form:UbicacionUbicacionGeograficaDialogo");
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
            for (int i = 0; i < lovJornadasLaborales.size(); i++) {
                if (lovJornadasLaborales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaJornada.setJornadatrabajo(lovJornadasLaborales.get(indiceUnicoElemento));
                lovJornadasLaborales.clear();
                getLovJornadasLaborales();
                context.update("form:jornadaLaboralModJornadaLaboral");
            } else {
                permitirIndexJornadaLaboral = false;
                context.update("form:JornadaJornadaLaboralDialogo");
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
            for (int i = 0; i < lovPeriodicidades.size(); i++) {
                if (lovPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaFormaPago.setFormapago(lovPeriodicidades.get(indiceUnicoElemento));
                lovPeriodicidades.clear();
                getLovPeriodicidades();
                context.update("form:formaPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                context.update("form:PeriodicidadFormaPagoDialogo");
                context.execute("PeriodicidadFormaPagoDialogo.show()");
                context.update("form:formaPagoModFormaPago");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("SUCURSAL")) {
            nuevaVigenciaFormaPago.getSucursal().setNombre(auxFormaPagoSucursal);
            for (int i = 0; i < lovSucursales.size(); i++) {
                if (lovSucursales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaFormaPago.setSucursal(lovSucursales.get(indiceUnicoElemento));
                lovSucursales.clear();
                getLovSucursales();
                context.update("form:sucursalPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                context.update("form:SucursalFormaPagoDialogo");
                context.execute("SucursalFormaPagoDialogo.show()");
                context.update("form:sucursalPagoModFormaPago");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("METODO")) {
            nuevaVigenciaFormaPago.getMetodopago().setDescripcion(auxFormaPagoMetodo);
            for (int i = 0; i < lovMetodosPagos.size(); i++) {
                if (lovMetodosPagos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaFormaPago.setMetodopago(lovMetodosPagos.get(indiceUnicoElemento));
                lovMetodosPagos.clear();
                getLovMetodosPagos();
                context.update("form:metodoPagoModFormaPago");
            } else {
                permitirIndexFormaPago = false;
                context.update("form:MetodoPagoFormaPagoDialogo");
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
            nuevaVigenciaAfiliacionFondo.getTercerosucursal().setDescripcion(auxAfiliacionFondo);
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionFondo.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                TiposEntidades fondo = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("12"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(fondo);
                context.update("form:fondoCensantiasModAfiliaciones");
            } else {
                permitirIndexAfiliacionFondo = false;
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
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
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
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
                context.update("form:TerceroAfiliacionDialogo");
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
            nuevaVigenciaAfiliacionCaja.getTercerosucursal().setDescripcion(auxAfiliacionCaja);
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionCaja.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                TiposEntidades caja = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("14"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(caja);
                context.update("form:cajaCompensacionModAfiliaciones");
            } else {
                permitirIndexAfiliacionCaja = false;
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
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
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionARP.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                TiposEntidades arp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("2"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(arp);
                context.update("form:arpModAfiliaciones");
                consultarCodigoSC();
            } else {
                permitirIndexAfiliacionARP = false;
                context.update("form:TerceroAfiliacionDialogo");
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
            nuevaVigenciaAfiliacionAFP.getTercerosucursal().setDescripcion(auxAfiliacionAFP);
            for (int i = 0; i < lovTercerosSucursales.size(); i++) {
                if (lovTercerosSucursales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaVigenciaAfiliacionAFP.setTercerosucursal(lovTercerosSucursales.get(indiceUnicoElemento));
                lovTercerosSucursales.clear();
                getLovTercerosSucursales();
                TiposEntidades afp = administrarPersonaIndividual.buscarTipoEntidadPorCodigo(new Short("3"));
                nuevaVigenciaAfiliacionEPS.setTipoentidad(afp);
                context.update("form:afpModAfiliaciones");
                consultarCodigoSP();
            } else {
                permitirIndexAfiliacionAFP = false;
                context.update("form:TerceroAfiliacionDialogo");
                context.execute("TerceroAfiliacionDialogo.show()");
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
            nuevoEstadoCivil.setDescripcion(auxEstadoCivilEstado);
            for (int i = 0; i < lovEstadosCiviles.size(); i++) {
                if (lovEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevoEstadoCivil = lovEstadosCiviles.get(indiceUnicoElemento);
                lovEstadosCiviles.clear();
                getLovEstadosCiviles();
                context.update("form:estadoCivilModEstadoCivil");
            } else {
                permitirIndexEstadoCivil = false;
                context.update("form:EstadoCivilEstadoCivilDialogo");
                context.execute("EstadoCivilEstadoCivilDialogo.show()");
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
            nuevaDireccion.getCiudad().setNombre(auxDireccionCiudad);
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevaDireccion.setCiudad(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadModDireccion");
            } else {
                permitirIndexDireccion = false;
                context.update("form:CiudadDireccionDialogo");
                context.execute("CiudadDireccionDialogo.show()");
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
            nuevoTelefono.getCiudad().setNombre(auxTelefonoCiudad);
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevoTelefono.setCiudad(lovCiudades.get(indiceUnicoElemento));
                lovCiudades.clear();
                getLovCiudades();
                context.update("form:ciudadModTelefono");
            } else {
                permitirIndexTelefono = false;
                context.update("form:CiudadTelefonoDialogo");
                context.execute("CiudadTelefonoDialogo.show()");
                context.update("form:ciudadModTelefono");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPO")) {
            nuevoTelefono.getTipotelefono().setNombre(auxTelefonoTipo);
            for (int i = 0; i < lovTiposTelefonos.size(); i++) {
                if (lovTiposTelefonos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevoTelefono.setTipotelefono(lovTiposTelefonos.get(indiceUnicoElemento));
                lovTiposTelefonos.clear();
                getLovTiposTelefonos();
                context.update("form:tipoTelefonoModTelefono");
            } else {
                permitirIndexTelefono = false;
                context.update("form:TipoTelefonoTelefonoDialogo");
                context.execute("TipoTelefonoTelefonoDialogo.show()");
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
        context.update("form:CiudadTelefonoDialogo");
        context.update("form:lovCiudadTelefono");
        context.update("form:aceptarCT");
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
        context.update("form:TipoTelefonoTelefonoDialogo");
        context.update("form:lovTipoTelefonoTelefono");
        context.update("form:aceptarTTT");
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
        context.update("form:CiudadDireccionDialogo");
        context.update("form:lovCiudadDireccion");
        context.update("form:aceptarCD");
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
        nuevoEstadoCivil = estadoCivilSeleccionado;
        estadoCivilSeleccionado = new EstadosCiviles();
        filtrarLovEstadosCiviles = null;
        aceptar = true;
        permitirIndexEstadoCivil = true;
        context.update("form:estadoCivilModEstadoCivil");
        context.update("form:EstadoCivilEstadoCivilDialogo");
        context.update("form:lovEstadoCivilEstadoCivil");
        context.update("form:aceptarECEC");
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
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
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
        nuevaVigenciaAfiliacionEPS.setTipoentidad(caja);
        permitirIndexAfiliacionCaja = true;
        context.update("form:epsModAfiliaciones");
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
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
        nuevaVigenciaAfiliacionEPS.setTipoentidad(arp);
        permitirIndexAfiliacionARP = true;
        context.update("form:epsModAfiliaciones");
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
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
        nuevaVigenciaAfiliacionEPS.setTipoentidad(afp);
        permitirIndexAfiliacionAFP = true;
        context.update("form:afpModAfiliaciones");
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
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
        nuevaVigenciaAfiliacionEPS.setTipoentidad(fondo);
        permitirIndexAfiliacionFondo = true;
        context.update("form:fondoCensantiasModAfiliaciones");
        context.update("form:TerceroAfiliacionDialogo");
        context.update("form:lovTerceroAfiliacion");
        context.update("form:aceptarTSA");
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
        context.update("form:JornadaJornadaLaboralDialogo");
        context.update("form:lovJornadaJornadaLaboral");
        context.update("form:aceptarJLJL");
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
        context.update("form:MetodoPagoFormaPagoDialogo");
        context.update("form:lovMetodoPagoFormaPago");
        context.update("form:aceptarMPFP");
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
        context.update("form:SucursalFormaPagoDialogo");
        context.update("form:lovSucursalFormaPago");
        context.update("form:aceptarSFP");
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
        context.update("form:PeriodicidadFormaPagoDialogo");
        context.update("form:lovPeriodicidadFormaPago");
        context.update("form:aceptarPFP");
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
        context.update("form:UbicacionUbicacionGeograficaDialogo");
        context.update("form:lovUbicacionUbicacionGeografica");
        context.update("form:aceptarUGUG");
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
        context.update("form:ContratoLegislacionLaboralDialogo");
        context.update("form:lovContratoLegislacionLaboral");
        context.update("form:aceptarCLL");
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
        context.update("form:NormaLaboralNormaLaboralDialogo");
        context.update("form:lovNormaLaboralNormaLaboral");
        context.update("form:aceptarNLNL");
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
        context.update("form:MotivoContratoTipoContratoDialogo");
        context.update("form:lovMotivoCambioSueldoSueldo");
        context.update("form:aceptarMCTC");
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
        context.update("form:TipoContratoTipoContratoDialogo");
        context.update("form:lovMotivoCambioSueldoSueldo");
        context.update("form:aceptarTCTC");
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
        context.update("form:MotivoCambioSueldoSueldoDialogo");
        context.update("form:lovMotivoCambioSueldoSueldo");
        context.update("form:aceptarMCSS");
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
        context.update("form:TipoSueldoSueldoDialogo");
        context.update("form:lovTipoSueldoSueldo");
        context.update("form:aceptarTSS");
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
        context.update("form:UnidadSueldoDialogo");
        context.update("form:lovUnidadSueldo");
        context.update("form:aceptarUS");
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
        context.update("form:ReformaLaboralTipoSalarioDialogo");
        context.update("form:lovReformaLaboralTipoSalario");
        context.update("form:aceptarRLTS");
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
        context.update("form:TipoTrabajadorTipoTrabajadorDialogo");
        context.update("form:lovTipoTrabajadorTipoTrabajador");
        context.update("form:aceptarTTTT");
        context.execute("TipoTrabajadorTipoTrabajadorDialogo.hide()");
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
        context.update("form:EstructuraCentroCostoDialogo");
        context.update("form:lovEstructuraCentroCosto");
        context.update("form:aceptarECC");
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
        context.update("form:MotivoLocalizacionCentroCostoDialogo");
        context.update("form:lovMotivoLocalizacionCentroCosto");
        context.update("form:aceptarMLCC");
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
        context.update("form:CargoCargoDesempeñadoDialogo");
        context.update("form:lovCargoCargoDesempeñado");
        context.update("form:aceptarCCD");
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
        context.update("form:MotivoCambioCargoCargoDesempeñadoDialogo");
        context.update("form:lovMotivoCambioCargoCargoDesempeñado");
        context.update("form:aceptarMCCCD");
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
        context.update("form:EstructuraCargoDesempeñadoDialogo");
        context.update("form:lovEstructuraCargoDesempeñado");
        context.update("form:aceptarECD");
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
        context.update("form:PapelCargoDesempeñadoDialogo");
        context.update("form:lovPapelCargoDesempeñado");
        context.update("form:aceptarPCD");
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
        context.update("form:EmpleadoJefeCargoDesempeñadoDialogo");
        context.update("form:lovEmpleadoJefeCargoDesempeñado");
        context.update("form:aceptarEJCD");
        context.execute("EmpleadoJefeCargoDesempeñadoDialogo.hide()");
    }

    public void cancelarParametroJefeCargoDesempeñado() {
        empleadoSeleccionado = new Empleados();
        filtrarLovEmpleados = null;
        permitirIndexCargoDesempeñado = true;
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
        context.update("form:EmpresaInformacionPersonalDialogo");
        context.update("form:lovEmpresaInformacionPersonal");
        context.update("form:aceptarEIP");
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
        context.update("form:TipoDocumentoInformacionPersonalDialogo");
        context.update("form:lovTipoDocumentoInformacionPersonal");
        context.update("form:aceptarTDIP");
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
        context.update("form:CiudadDocumentoInformacionPersonalDialogo");
        context.update("form:lovCiudadDocumentoInformacionPersonal");
        context.update("form:aceptarCDIP");
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
        context.update("form:CiudadNacimientoInformacionPersonalDialogo");
        context.update("form:lovCiudadNacimientoInformacionPersonal");
        context.update("form:aceptarCNIP");
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
                if (numero <= 0) {
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
        String codigo = administrarPersonaIndividual.buscarCodigoSSTercero(nuevaVigenciaAfiliacionEPS.getSecuencia());
        if (codigo == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("advertenciaEPS.show()");
        }
    }

    public void consultarCodigoSP() {
        String codigo = administrarPersonaIndividual.buscarCodigoSPTercero(nuevaVigenciaAfiliacionEPS.getSecuencia());
        if (codigo == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("advertenciaAFP.show()");
        }
    }

    public void consultarCodigoSC() {
        String codigo = administrarPersonaIndividual.buscarCodigoSCTercero(nuevaVigenciaAfiliacionEPS.getSecuencia());
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
                } else {

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
        if (nuevoEmpleado.getSecuencia() != null) {
            lovEstructurasCentroCosto = administrarPersonaIndividual.lovEstructurasModCentroCosto(nuevoEmpleado.getSecuencia());
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

    public EstadosCiviles getNuevoEstadoCivil() {
        return nuevoEstadoCivil;
    }

    public void setNuevoEstadoCivil(EstadosCiviles nuevoEstadoCivil) {
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

}

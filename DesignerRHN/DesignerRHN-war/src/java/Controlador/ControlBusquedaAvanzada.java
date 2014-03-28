package Controlador;

import Entidades.Cargos;
import Entidades.CentrosCostos;
import Entidades.Ciudades;
import Entidades.Contratos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosCambiosSueldos;
import Entidades.MotivosContratos;
import Entidades.MotivosLocalizaciones;
import Entidades.NormasLaborales;
import Entidades.Papeles;
import Entidades.ReformasLaborales;
import Entidades.TiposContratos;
import Entidades.TiposSueldos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.VigenciasCargos;
import Entidades.VigenciasContratos;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasNormasEmpleados;
import Entidades.VigenciasReformasLaborales;
import Entidades.VigenciasSueldos;
import Entidades.VigenciasTiposContratos;
import Entidades.VigenciasTiposTrabajadores;
import Entidades.VigenciasUbicaciones;
import InterfaceAdministrar.AdministrarVigenciaLocalizacionInterface;
import InterfaceAdministrar.AdministrarVigenciaNormaLaboralInterface;
import InterfaceAdministrar.AdministrarVigenciasCargosBusquedaAvanzadaInterface;
import InterfaceAdministrar.AdministrarVigenciasContratosInterface;
import InterfaceAdministrar.AdministrarVigenciasReformasLaboralesInterface;
import InterfaceAdministrar.AdministrarVigenciasSueldosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposContratosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposTrabajadoresInterface;
import InterfaceAdministrar.AdministrarVigenciasUbicacionesInterface;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    //LOVS 
    //Ciudades
    private List<UbicacionesGeograficas> lovUbicacionesGeograficas;
    private List<UbicacionesGeograficas> filtrarLovUbicacionesGeograficas;
    private UbicacionesGeograficas ubicacionGeograficaSeleccionada;
    //
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
    //Otros
    private boolean aceptar;
    private Date fechaParametro;
    private boolean activoFechasCargos, activoFechasCentroCosto, activoFechasSueldo, activoFechasFechaContrato, activoFechasTipoTrabajador;
    private boolean activoFechasTipoSalario, activoFechasNormaLaboral, activoFechasLegislacionLaboral, activoFechasUbicacion;
    private BigDecimal sueldoMaxSueldo, sueldoMinSueldo;

    public ControlBusquedaAvanzada() {
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
        //
        tabActivaCentroCosto = 0;
        tabActivaCargos = 0;
        tabActivaSueldo = 0;
        tabActivaFechaContrato = 0;
        tabActivaTipoTrabajador = 0;
        tabActivaTipoSalario = 0;
        tabActivaNormaLaboral = 0;
        tabActivaLegislacionLaboral = 0;
        tabActivaUbicacion = 0;
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
        //LOVS
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
    }

    public void cancelarModificaciones() {
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
        //
        tabActivaFechaContrato = 0;
        tabActivaCargos = 0;
        tabActivaCentroCosto = 0;
        tabActivaSueldo = 0;
        tabActivaTipoTrabajador = 0;
        tabActivaTipoSalario = 0;
        tabActivaNormaLaboral = 0;
        tabActivaLegislacionLaboral = 0;
        tabActivaUbicacion = 0;
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
        //
        sueldoMaxSueldo = null;
        sueldoMinSueldo = null;
        auxSueldoMaximoSueldo = null;
        auxSueldoMinimoSueldo = null;
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
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
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
            casillaVigenciaUbicacion = i;
            auxUbicacionVigenciaUbicacion = vigenciaUbicacionBA.getUbicacion().getDescripcion();
            auxFechaFinalUbicacion = fechaFinalUbicacion;
            auxFechaInicialUbicacion = fechaInicialUbicacion;
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalCargo = null;
            fechaInicialCargo = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewCosto");
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalCentroCosto = null;
            fechaInicialCentroCosto = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewCentroCosto");
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalSueldo = null;
            fechaInicialSueldo = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewSueldo");
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalFechaContrato = null;
            fechaInicialFechaContrato = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewFechaContrato");
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalTipoTrabajador = null;
            fechaInicialTipoTrabajador = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewTipoTrabajador");
            System.out.println("Error modificarFechaFinalModuloFechaContrato Fechas Tipo Contrato : " + e.toString());
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalTipoSalario = null;
            fechaInicialTipoSalario = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewTipoSalario");
            System.out.println("Error modificarFechaFinalModuloFechaContrato Fechas Tipo Salario : " + e.toString());
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalNormaLaboral = null;
            fechaInicialNormaLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewNormaLaboral");
            System.out.println("Error modificarFechaFinalModuloFechaContrato Fechas Norma Laboral : " + e.toString());
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
                    //context.execute("errorFechas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMIFinalLegislacionLaboral = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewLegislacionLaboral");
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaMIInicialLegislacionLaboral = null;
            fechaMIFinalLegislacionLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewLegislacionLaboral");
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
                    //context.execute("errorFechas.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                fechaMFFinalLegislacionLaboral = null;
                System.out.println("Ingrese la fecha inicial antes de la fecha final");
                context.update("form:tabViewLegislacionLaboral");
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaMFInicialLegislacionLaboral = null;
            fechaMFFinalLegislacionLaboral = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewLegislacionLaboral");
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
                //context.execute("errorFechas.show()");
            }
        } catch (Exception e) {
            fechaFinalUbicacion = null;
            fechaInicialUbicacion = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tabViewUbicacion");
            System.out.println("Error modificarFechaFinalModuloFechaContrato Fechas Ubicacion : " + e.toString());
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

    public void modificarParametroUbicacion(String cualParametro, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualParametro.equals("UBICACION ")) {
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

    public void modificarSueldosModuloSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (sueldoMaxSueldo != null && sueldoMinSueldo != null) {
            if (sueldoMaxSueldo.intValue() < sueldoMinSueldo.intValue()) {
                sueldoMaxSueldo = auxSueldoMaximoSueldo;
                sueldoMinSueldo = auxSueldoMinimoSueldo;
                context.update("form:tabViewSueldo:parametroSueldoMinimoModSueldo");
                context.update("form:tabViewSueldo:parametroSueldoMaximoModSueldo");
                System.out.println("Error modificarSueldosModuloSueldo");
            }
        }
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
        context.update("form:tabViewCentroCosto:parametroMotivoModSueldo");
        estructuraSeleccionada = null;
        filtrarLovEstructuras = null;
        casillaVigenciaLocalizacion = -1;
        aceptar = true;
        permitirIndexVigenciaLocalizacion = true;
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

}

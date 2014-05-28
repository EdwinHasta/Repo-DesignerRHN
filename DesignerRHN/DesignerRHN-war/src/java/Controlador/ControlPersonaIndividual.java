package Controlador;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Estructuras;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosLocalizaciones;
import Entidades.Papeles;
import Entidades.ParametrosEstructuras;
import Entidades.Personas;
import Entidades.TiposDocumentos;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasCargos;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasTiposTrabajadores;
import InterfaceAdministrar.AdministrarCarpetaPersonalInterface;
import InterfaceAdministrar.AdministrarPersonaIndividualInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
    //LOVS
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
    private boolean aceptar;

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
        //Lovs
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
        //Auxiliares
        auxFechaNacimiento = null;
        auxFechaIngreso = null;
        auxFechaCorte = null;
        //Otros
        aceptar = true;
        disableNombreEstructuraCargo = true;
        disableDescripcionEstructura = true;
        //Permitir Index
        permitirIndexInformacionPersonal = true;
        permitirIndexCentroCosto = true;
        permitirIndexCargoDesempeñado = true;
        permitirIndexTipoTrabajador = true;
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
        System.out.println("indexCentroCosto : " + indexCentroCosto);
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
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexInformacionPersonal >= 0) {
            if (indexInformacionPersonal == 0) {
                context.update("form:editarEmpresaInformacionPersonal");
                context.execute("editarEmpresaInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 1) {
                context.update("form:editarPrimerApellidoInformacionPersonal");
                context.execute("editarPrimerApellidoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 2) {
                context.update("form:editarSegundoApellidoInformacionPersonal");
                context.execute("editarSegundoApellidoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 3) {
                context.update("form:editarNombresInformacionPersonal");
                context.execute("editarNombresInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 5) {
                context.update("form:editarTipoDocumentoInformacionPersonal");
                context.execute("editarTipoDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 6) {
                context.update("form:editarNumeroDocumentoInformacionPersonal");
                context.execute("editarNumeroDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 7) {
                context.update("form:editarCiudadDocumentoInformacionPersonal");
                context.execute("editarCiudadDocumentoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 8) {
                context.update("form:editarFechaNacimientoInformacionPersonal");
                context.execute("editarFechaNacimientoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 9) {
                context.update("form:editarCiudadNacimientoInformacionPersonal");
                context.execute("editarCiudadNacimientoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 10) {
                context.update("form:editarFechaIngresoInformacionPersonal");
                context.execute("editarFechaIngresoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 11) {
                context.update("form:editarCodigoEmpleadoInformacionPersonal");
                context.execute("editarCodigoEmpleadoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 12) {
                context.update("form:editarEmailInformacionPersonal");
                context.execute("editarEmailInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
            if (indexInformacionPersonal == 13) {
                context.update("form:editarFechaUltimoPagoInformacionPersonal");
                context.execute("editarFechaUltimoPagoInformacionPersonal.show()");
                indexInformacionPersonal = -1;
            }
        }
        if (indexCargoDesempeñado >= 0) {
            if (indexCargoDesempeñado == 0) {
                context.update("form:editarCargoCargoDesempeñado");
                context.execute("editarCargoCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 1) {
                context.update("form:editarMotivoCargoDesempeñado");
                context.execute("editarMotivoCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 2) {
                getLovEstructuras();
                context.update("form:editarEstructuraCargoDesempeñado");
                context.execute("editarEstructuraCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 3) {
                context.update("form:editarPapelCargoDesempeñado");
                context.execute("editarPapelCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
            if (indexCargoDesempeñado == 4) {
                context.update("form:editarEmpleadoJefeCargoDesempeñado");
                context.execute("editarEmpleadoJefeCargoDesempeñado.show()");
                indexCargoDesempeñado = -1;
            }
        }
        if (indexCentroCosto >= 0) {
            if (indexCentroCosto == 0) {
                context.update("form:editarEstructuraCentroCosto");
                context.execute("editarEstructuraCentroCosto.show()");
                indexCentroCosto = -1;
            }
            if (indexCentroCosto == 1) {
                context.update("form:editarMotivoCentroCosto");
                context.execute("editarMotivoCentroCosto.show()");
                indexCentroCosto = -1;
            }
        }
        if (indexTipoTrabajador >= 0) {
            if (indexTipoTrabajador == 0) {
                context.update("form:editarTrabajadorTipoTrabajador");
                context.execute("editarTrabajadorTipoTrabajador.show()");
                indexTipoTrabajador = -1;
            }
        }
    }

    public void cambiarIndiceInformacionPersonal(int i) {
        if (permitirIndexInformacionPersonal == true) {
            indexCargoDesempeñado = -1;
            indexCentroCosto = -1;
            indexTipoTrabajador = -1;
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
            indexTipoTrabajador = i;
            auxTipoTrabajadorNombreTipoTrabajador = nuevaVigenciaTipoTrabajador.getTipotrabajador().getNombre();
        }
    }

    public void modificarNumeroDocumentoPersona() {
        nuevoEmpleado.setCodigoempleado(nuevaPersona.getNumerodocumento());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:codigoEmpleadoModPersonal");
    }

    public boolean validarFechasInformacionPersonal(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
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

    public void modificacionesEmpresaFechaIngresoInformacionPersonal() {
        if (nuevoEmpleado.getEmpresa().getSecuencia() != null) {
            disableDescripcionEstructura = false;
            if (fechaIngreso != null) {
                disableNombreEstructuraCargo = false;
            }
        } else {
            disableNombreEstructuraCargo = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:estructuraModCargoDesempeñado");
        context.update("form:estructuraModCentroCosto");
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

    public Date getFechaDesde() {
        getParametrosEstructuras();
        fechaDesdeParametro = parametrosEstructuras.getFechadesdecausado();
        return fechaDesdeParametro;
    }

    public void setFechaDesdeParametro(Date fechaDesdeParametro) {
        this.fechaDesdeParametro = fechaDesdeParametro;
    }

    public Date getFechaHasta() {
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

}

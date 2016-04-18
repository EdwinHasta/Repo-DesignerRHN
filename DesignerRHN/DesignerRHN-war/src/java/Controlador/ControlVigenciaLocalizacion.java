package Controlador;

import Entidades.CentrosCostos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosLocalizaciones;
import Entidades.Proyectos;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasProrrateos;
import Entidades.VigenciasProrrateosProyectos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciaLocalizacionInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlVigenciaLocalizacion implements Serializable {

    @EJB
    AdministrarVigenciaLocalizacionInterface administrarVigenciaLocalizacion;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Localizaciones
    private List<VigenciasLocalizaciones> vigenciaLocalizaciones;
    private List<VigenciasLocalizaciones> filtradoVL;
    private VigenciasLocalizaciones vigenciaLocalizacionSeleccionada;
    //Vigencias Prorrateos
    private List<VigenciasProrrateos> vigenciasProrrateosCentroC;
    private List<VigenciasProrrateos> filtradoVP;
    private VigenciasProrrateos vigenciaProrrateoSeleccionada;
    // Vigencia Prorrateos Proyectos
    private List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectos;
    private List<VigenciasProrrateosProyectos> filtradoPP;
    private VigenciasProrrateosProyectos vigenciaProrrateoProyectoSeleccionada;
    //Centros Costos
    private List<CentrosCostos> listCentrosCostos;
    private CentrosCostos centroCostoSeleccionado;
    private List<CentrosCostos> filtradoCentroCostos;
    //Motivos Localizaciones
    private List<MotivosLocalizaciones> listMotivosLocalizaciones;
    private MotivosLocalizaciones motivoLocalizacionSelecionado;
    private List<MotivosLocalizaciones> filtradoMotivosLocalizaciones;
    //Estructuras-Centro Costo 
    private List<Estructuras> listEstructurasCC;
    private Estructuras estructuraSelecionada;
    private List<Estructuras> filtradoEstructuraCC;
    //Proyectos
    private List<Proyectos> listProyectos;
    private Proyectos proyectoSelecionado;
    private List<Proyectos> filtradoProyectos;
    //Empleado
    private Empleados empleado;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Activo/Desactivo VP Crtl + F11
    private int banderaVP;
    //Activo/Desactivo VPP Crtl + F11
    private int banderaVPP;
    //Columnas Tabla VL
    private Column vlFechaVigencia, vlCentroCosto, vlLocalizacion, vlMotivo, vlProyecto;
    //Columnas Tabla VP
    private Column vPCentroCosto, vPPorcentaje, vPFechaInicial, vPFechaFinal, vPProyecto, vPSubPorcentaje;
    //Columnas Tabla VPP
    private Column vPPProyecto, vPPPorcentaje, vPPFechaInicial, vPPFechaFinal;
    //Otros
    private boolean aceptar;
    //private int index;
    //modificar
    private List<VigenciasLocalizaciones> listVLModificar;
    private List<VigenciasProrrateos> listVPModificar;
    private List<VigenciasProrrateosProyectos> listVPPModificar;
    private boolean guardado;
    //crear VL
    public VigenciasLocalizaciones nuevaVigencia;
    //crear VP
    public VigenciasProrrateos nuevaVigenciaP;
    //crear VPP
    public VigenciasProrrateosProyectos nuevaVigenciaPP;
    private List<VigenciasLocalizaciones> listVLCrear;
    private BigInteger nuevaVLSecuencia;
    private int paraNuevaV;
    //borrar VL
    private List<VigenciasLocalizaciones> listVLBorrar;
    private List<VigenciasProrrateos> listVPBorrar;
    private List<VigenciasProrrateosProyectos> listVPPBorrar;
    //editar celda
    private VigenciasLocalizaciones editarVL;
    private VigenciasProrrateos editarVP;
    private VigenciasProrrateosProyectos editarVPP;
    private int cualCelda, tipoLista;
    //duplicar
    private VigenciasLocalizaciones duplicarVL;
    //Autocompletar
    private boolean permitirIndex, permitirIndexVP, permitirIndexVPP;
    //Variables Autompletar
    private String centroCosto, motivoLocalizacion, proyecto, centroCostosVP, proyectoVP, proyectoVPP;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaVP, cualCeldaVPP, tipoListaVP, tipoListaVPP;
    //Index Auxiliar Para Nuevos Registros
    private int indexAuxVL;
    //Duplicar Vigencia Prorrateo
    private VigenciasProrrateos duplicarVP;
    //Duplicar Vigencia Prorrateo Proyecto
    private VigenciasProrrateosProyectos duplicarVPP;
    //Lista Vigencia Prorrateo Crear
    private List<VigenciasProrrateos> listVPCrear;
    //Lista Vigencia Prorrateo Proyecto Crear
    private List<VigenciasProrrateosProyectos> listVPPCrear;
    private String nombreXML;
    private String nombreTabla;
    //Banderas Boolean de operaciones sobre vigencias prorrateos y vigencias prorrateos proyectos
    private boolean cambioVigenciaP, cambioVigenciaPP, cambiosVigencia;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private Date fechaParametro;
    private Date fechaVigencia, fechaIniVP, fechaFinVP, fechaIniVPP, fechaFinVPP;
    private String altoTabla1, altoTabla2, altoTabla3;
    //
    private String infoRegistroEstLocalizacion;
    private String infoRegistroMotivoLocalizacion;
    private String infoRegistroProyecto;
    private String infoRegistroCentroCosto;
    private String infoRegistroVL;
    private String infoRegistroVP;
    private String infoRegistroVPP;
    //Validaciones
    private String mensajeValidacion;
    private DataTable tabla;
    //para control
    private int cualTabla;

    public ControlVigenciaLocalizacion() {

        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        backUp = null;
        nombreTablaRastro = "";

        listCentrosCostos = null;

        vigenciaLocalizaciones = null;
        listEstructurasCC = null;
        listMotivosLocalizaciones = null;
        empleado = new Empleados();
        listProyectos = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listVLBorrar = new ArrayList<VigenciasLocalizaciones>();
        listVPBorrar = new ArrayList<VigenciasProrrateos>();
        listVPPBorrar = new ArrayList<VigenciasProrrateosProyectos>();
        //crear aficiones
        listVPCrear = new ArrayList<VigenciasProrrateos>();
        listVPPCrear = new ArrayList<VigenciasProrrateosProyectos>();
        listVLCrear = new ArrayList<VigenciasLocalizaciones>();
        //modificar aficiones
        listVLModificar = new ArrayList<VigenciasLocalizaciones>();
        listVPModificar = new ArrayList<VigenciasProrrateos>();
        listVPPModificar = new ArrayList<VigenciasProrrateosProyectos>();
        paraNuevaV = 0;
        //editar
        editarVL = new VigenciasLocalizaciones();
        editarVP = new VigenciasProrrateos();
        editarVPP = new VigenciasProrrateosProyectos();
        cualCelda = -1;
        tipoLista = 0;
        tipoListaVP = 0;
        tipoListaVPP = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasLocalizaciones();
        nuevaVigencia.setLocalizacion(new Estructuras());
        nuevaVigencia.getLocalizacion().setCentrocosto(new CentrosCostos());
        nuevaVigencia.setMotivo(new MotivosLocalizaciones());
        nuevaVigencia.setProyecto(new Proyectos());

        nuevaVigenciaP = new VigenciasProrrateos();
        nuevaVigenciaP.setCentrocosto(new CentrosCostos());
        nuevaVigenciaP.setProyecto(new Proyectos());
        nuevaVigenciaP.setPorcentaje(new BigDecimal(0));

        nuevaVigenciaPP = new VigenciasProrrateosProyectos();
        nuevaVigenciaPP.setProyecto(new Proyectos());
        nuevaVigenciaPP.setPorcentaje(0);

        bandera = 0;
        banderaVP = 0;
        banderaVPP = 0;
        permitirIndex = true;
        permitirIndexVP = true;
        permitirIndexVPP = true;
        cualCeldaVP = -1;
        cualCeldaVPP = -1;

        nombreTabla = ":formExportarVL:datosVLEmpleadoExportar";
        nombreXML = "VigenciasLocalizacionesXML";

        cambioVigenciaP = false;
        cambioVigenciaPP = false;
        cambiosVigencia = false;
        altoTabla1 = "115";
        altoTabla2 = "115";
        altoTabla3 = "115";

        mensajeValidacion = "";
        tipoActualizacion = 0;
        cualTabla = 0;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciaLocalizacion.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(Empleados empl) {
        empleado = empl;
        getVigenciaLocalizaciones();
        getVigenciasProrrateosCentroC();
        getVigenciasProrrateosProyectos();
        contarRegistrosVL();
        contarRegistrosVP();
        contarRegistrosVPP();
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVL() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {

            if (listVLModificar.isEmpty()) {
                listVLModificar.add(vigenciaLocalizacionSeleccionada);
            } else if (!listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                listVLModificar.add(vigenciaLocalizacionSeleccionada);
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
        cambiosVigencia = true;
    }

    public boolean validarFechasRegistro(int tipoAct) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (tipoAct == 0) {
            if (vigenciaLocalizacionSeleccionada.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (tipoAct == 1) {
            if (nuevaVigencia.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (tipoAct == 2) {
            if (duplicarVL.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechas(VigenciasLocalizaciones vLocalizacion, int c) {
        vigenciaLocalizacionSeleccionada = vLocalizacion;
        if (vigenciaLocalizacionSeleccionada.getFechavigencia() != null) {
            boolean retorno = false;
            retorno = validarFechasRegistro(0);
            if (retorno) {
                cambiarIndice(vigenciaLocalizacionSeleccionada, c);
                modificarVL();
            } else {
                vigenciaLocalizacionSeleccionada.setFechavigencia(fechaVigencia);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVLEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            vigenciaLocalizacionSeleccionada.setFechavigencia(fechaVigencia);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasLocalizaciones de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVL(VigenciasLocalizaciones vLocalizacion, String confirmarCambio, String valorConfirmar) {

        vigenciaLocalizacionSeleccionada = vLocalizacion;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            vigenciaLocalizacionSeleccionada.getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);

            for (int i = 0; i < listEstructurasCC.size(); i++) {
                if (listEstructurasCC.get(i).getCentrocosto().getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaLocalizacionSeleccionada.setLocalizacion(listEstructurasCC.get(indiceUnicoElemento));
            } else {
                permitirIndex = false;
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOLOCALIZACION")) {
            vigenciaLocalizacionSeleccionada.getMotivo().setDescripcion(motivoLocalizacion);

            for (int i = 0; i < listMotivosLocalizaciones.size(); i++) {
                if (listMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaLocalizacionSeleccionada.setMotivo(listMotivosLocalizaciones.get(indiceUnicoElemento));
            } else {
                permitirIndex = false;
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                vigenciaLocalizacionSeleccionada.getProyecto().setNombreproyecto(proyecto);

                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigenciaLocalizacionSeleccionada.setProyecto(listProyectos.get(indiceUnicoElemento));
                } else {
                    permitirIndex = false;
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                vigenciaLocalizacionSeleccionada.setProyecto(new Proyectos());

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigencia = true;
            }
        }
        if (coincidencias == 1) {
            if (!listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {

                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                } else if (!listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambiosVigencia = true;

        }
        context.update("form:datosVLEmpleado");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVPPorcentaje(String valor, int indice, int nTabla, int subPorcent) {
        tipoActualizacion = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext c = FacesContext.getCurrentInstance();
        try {
            if (nTabla == 1) {
                BigDecimal porcentajePrueba = new BigDecimal(valor);
                if (subPorcent == 1) {
                    vigenciaProrrateoSeleccionada.setSubporcentaje(porcentajePrueba.toBigInteger());
                } else {
                    vigenciaProrrateoSeleccionada.setPorcentaje(porcentajePrueba);
                }
                modificarVP();
                if (porcentajePrueba.intValueExact() < 0 || porcentajePrueba.intValueExact() > 100) {
                    context.update("form:validarPorcentajes");
                    context.execute("validarPorcentajes.show()");
                }
            }
            if (nTabla == 2) {
                int porcentajePrueba = Integer.parseInt(valor);
                vigenciaProrrateoProyectoSeleccionada.setPorcentaje(porcentajePrueba);
                modificarVP();
                if (porcentajePrueba < 0 || porcentajePrueba > 100) {
                    context.update("form:validarPorcentajes");
                    context.execute("validarPorcentajes.show()");
                }
            }
            if (nTabla == 1) {
                vPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                context.update("form:datosVPVigencia");
            } else if (nTabla == 2) {
                vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
                vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                context.update("form:datosVPPVigencia");
            }
        } catch (NumberFormatException e) {
            context.update("form:validarPorcentajes");
            context.execute("validarPorcentajes.show()");
        }

    }

    public void modificarVPPorcentajes(int tipoAct, int nTabla, String valor, int subPorcent) {

        tipoActualizacion = tipoAct;
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            System.out.println("entro al try");
            if (nTabla == 1) {
                BigDecimal porcentajePrueba = new BigDecimal(valor);
                if (tipoActualizacion == 1) {
                    if (subPorcent == 1) {
                        nuevaVigenciaP.setSubporcentaje(porcentajePrueba.toBigInteger());
                    } else {
                        nuevaVigenciaP.setPorcentaje(porcentajePrueba);
                    }
                } else if (tipoActualizacion == 2) {
                    if (subPorcent == 1) {
                        duplicarVP.setPorcentaje(porcentajePrueba);
                    } else {
                        duplicarVP.setPorcentaje(porcentajePrueba);
                    }
                }
                if (porcentajePrueba.intValueExact() > 100 || porcentajePrueba.intValueExact() < 0) {
                    context.update("form:validarPorcentajes");
                    context.execute("validarPorcentajes.show()");
                }
            }
            if (nTabla == 2) {
                int porcentajePrueba = Integer.parseInt(valor);
                if (tipoActualizacion == 1) {
                    nuevaVigenciaPP.setPorcentaje(porcentajePrueba);
                } else if (tipoActualizacion == 2) {
                }
                duplicarVPP.setPorcentaje(porcentajePrueba);
                if (porcentajePrueba > 100 || porcentajePrueba < 0) {
                    context.update("form:validarPorcentajes");
                    context.execute("validarPorcentajes.show()");
                }
            }
            if (nTabla == 1) {
                if (tipoActualizacion == 1) {
                    context.update("formularioDialogos:nuevaCentroCostoVP");
                } else if (tipoActualizacion == 2) {
                    context.update("formularioDialogos:duplicadoCentroCostoVP");
                }
            } else if (nTabla == 2) {
                if (tipoActualizacion == 1) {
                    context.update("formularioDialogos:nuevaProyectoVP");
                } else if (tipoActualizacion == 2) {
                    context.update("formularioDialogos:duplicadoProyectoVP");
                }
            }
        } catch (NumberFormatException e) {
            context.update("form:validarPorcentajes");
            context.execute("validarPorcentajes.show()");
        }

    }

    public void modificarVP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVPCrear.contains(vigenciaProrrateoSeleccionada)) {

            if (listVPModificar.isEmpty()) {
                listVPModificar.add(vigenciaProrrateoSeleccionada);
            } else if (!listVPModificar.contains(vigenciaProrrateoSeleccionada)) {
                listVPModificar.add(vigenciaProrrateoSeleccionada);
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
        cambioVigenciaP = true;
    }

    public boolean validarFechasRegistroVigenciaProrrateo(int tipoAct) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (tipoAct == 0) {

            if (vigenciaProrrateoSeleccionada.getFechafinal() != null) {
                if (vigenciaProrrateoSeleccionada.getFechainicial().after(fechaParametro) && vigenciaProrrateoSeleccionada.getFechainicial().before(vigenciaProrrateoSeleccionada.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (vigenciaProrrateoSeleccionada.getFechafinal() == null) {
                if (vigenciaProrrateoSeleccionada.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (tipoAct == 1) {
            if (nuevaVigenciaP.getFechafinal() != null) {
                if (nuevaVigenciaP.getFechainicial().after(fechaParametro) && nuevaVigenciaP.getFechainicial().before(nuevaVigenciaP.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaVigenciaP.getFechafinal() == null) {
                if (nuevaVigenciaP.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (tipoAct == 2) {
            if (duplicarVP.getFechafinal() != null) {
                if (duplicarVP.getFechainicial().after(fechaParametro) && duplicarVP.getFechainicial().before(duplicarVP.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarVP.getFechafinal() == null) {
                if (duplicarVP.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechasVigenciasProrrateos(VigenciasProrrateos vProrrateo, int c) {

        vigenciaProrrateoSeleccionada = vProrrateo;

        if (vigenciaProrrateoSeleccionada.getFechainicial() != null) {
            boolean retorno = false;
            retorno = validarFechasRegistroVigenciaProrrateo(0);
            if (retorno) {
                cambiarIndiceVP(vigenciaProrrateoSeleccionada, c);
                modificarVP();
            } else {
                vigenciaProrrateoSeleccionada.setFechafinal(fechaFinVP);
                vigenciaProrrateoSeleccionada.setFechainicial(fechaIniVP);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            vigenciaProrrateoSeleccionada.setFechainicial(fechaIniVP);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPVigencia");
            context.execute("errorRegNew.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciaProrrateo
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVP(VigenciasProrrateos vProrrateos, String confirmarCambio, String valorConfirmar) {
        vigenciaProrrateoSeleccionada = vProrrateos;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            vigenciaProrrateoSeleccionada.getCentrocosto().setNombre(centroCostosVP);

            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                cambioVigenciaP = true;
                vigenciaProrrateoSeleccionada.setCentrocosto(listCentrosCostos.get(indiceUnicoElemento));
            } else {
                permitirIndexVP = false;
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                vigenciaProrrateoSeleccionada.getProyecto().setNombreproyecto(proyectoVP);

                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    cambioVigenciaP = true;
                    vigenciaProrrateoSeleccionada.setProyecto(listProyectos.get(indiceUnicoElemento));
                } else {
                    permitirIndexVP = false;
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                vigenciaProrrateoSeleccionada.setProyecto(new Proyectos());

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambioVigenciaP = true;
            }
        }
        if (coincidencias == 1) {
            if (!listVPCrear.contains(vigenciaProrrateoSeleccionada)) {

                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(vigenciaProrrateoSeleccionada);
                } else if (!listVPModificar.contains(vigenciaProrrateoSeleccionada)) {
                    listVPModificar.add(vigenciaProrrateoSeleccionada);
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaP = true;
            vigenciaProrrateoSeleccionada = null;
        }
        context.update("form:datosVPVigencia");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateoProyectos que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVPP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVPPCrear.contains(vigenciaProrrateoProyectoSeleccionada)) {

            if (listVPPModificar.isEmpty()) {
                listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
            } else if (!listVPPModificar.contains(vigenciaProrrateoProyectoSeleccionada)) {
                listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
        cambioVigenciaPP = true;
    }

    public boolean validarFechasRegistroVigenciaProrrateoProyecto(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {

            if (vigenciaProrrateoProyectoSeleccionada.getFechafinal() != null) {
                if (vigenciaProrrateoProyectoSeleccionada.getFechainicial().after(fechaParametro) && vigenciaProrrateoProyectoSeleccionada.getFechainicial().before(vigenciaProrrateoProyectoSeleccionada.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (vigenciaProrrateoProyectoSeleccionada.getFechafinal() == null) {
                if (vigenciaProrrateoProyectoSeleccionada.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevaVigenciaPP.getFechafinal() != null) {
                if (nuevaVigenciaPP.getFechainicial().after(fechaParametro) && nuevaVigenciaPP.getFechainicial().before(nuevaVigenciaPP.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaVigenciaPP.getFechafinal() == null) {
                if (nuevaVigenciaPP.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVPP.getFechafinal() != null) {
                if (duplicarVPP.getFechainicial().after(fechaParametro) && duplicarVPP.getFechainicial().before(duplicarVPP.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarVPP.getFechafinal() == null) {
                if (duplicarVPP.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechasVigenciasProrrateosProyecto(VigenciasProrrateosProyectos vProrrateosProyectos, int c) {
        vigenciaProrrateoProyectoSeleccionada = vProrrateosProyectos;

        if (vigenciaProrrateoProyectoSeleccionada.getFechainicial() != null) {
            boolean retorno = false;
            retorno = validarFechasRegistroVigenciaProrrateoProyecto(0);
            if (retorno) {
                cambiarIndiceVPP(vigenciaProrrateoProyectoSeleccionada, c);
                modificarVPP();
            } else {
                vigenciaProrrateoProyectoSeleccionada.setFechafinal(fechaFinVPP);
                vigenciaProrrateoProyectoSeleccionada.setFechainicial(fechaIniVPP);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            vigenciaProrrateoProyectoSeleccionada.setFechainicial(fechaIniVPP);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            context.execute("errorRegNew.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciaProrrateoProyectos de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVPP(VigenciasProrrateosProyectos vProrrateosProyectos, String confirmarCambio, String valorConfirmar) {
        vigenciaProrrateoProyectoSeleccionada = vProrrateosProyectos;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            vigenciaProrrateoProyectoSeleccionada.getProyecto().setNombreproyecto(proyectoVPP);

            for (int i = 0; i < listProyectos.size(); i++) {
                if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                cambioVigenciaPP = true;
                vigenciaProrrateoProyectoSeleccionada.setProyecto(listProyectos.get(indiceUnicoElemento));
            } else {
                permitirIndexVPP = false;
                context.update("form:ProyectosDialogo");
                context.execute("ProyectosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listVPPCrear.contains(vigenciaProrrateoProyectoSeleccionada)) {

                if (listVPPModificar.isEmpty()) {
                    listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
                } else if (!listVPPModificar.contains(vigenciaProrrateoProyectoSeleccionada)) {
                    listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaPP = true;
            vigenciaProrrateoProyectoSeleccionada = null;
        }
        context.update("form:datosVPPVigencia");
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaLocalizacion)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                centroCosto = nuevaVigencia.getLocalizacion().getCentrocosto().getCodigoNombre();
            } else if (tipoNuevo == 2) {
                centroCosto = duplicarVL.getLocalizacion().getCentrocosto().getCodigoNombre();
            }
        } else if (Campo.equals("MOTIVOLOCALIZACION")) {
            if (tipoNuevo == 1) {
                motivoLocalizacion = nuevaVigencia.getMotivo().getDescripcion();
            } else if (tipoNuevo == 2) {
                motivoLocalizacion = duplicarVL.getMotivo().getDescripcion();
            }
        } else if (Campo.equals("PROYECTO")) {
            if (tipoNuevo == 1) {
                proyecto = nuevaVigencia.getProyecto().getNombreproyecto();
            } else if (tipoNuevo == 2) {
                proyecto = duplicarVL.getProyecto().getNombreproyecto();
            }
        }
    }

    /**
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciasLocalizaciones
     *
     * @param confirmarCambio Tipo de elemento a modificar: CENTROCOSTO -
     * MOTIVOLOCALIZACION - PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            } else if (tipoNuevo == 2) {
                duplicarVL.getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            }
            for (int i = 0; i < listEstructurasCC.size(); i++) {
                if (listEstructurasCC.get(i).getCentrocosto().getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setLocalizacion(listEstructurasCC.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCentroCosto");
                } else if (tipoNuevo == 2) {
                    duplicarVL.setLocalizacion(listEstructurasCC.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCentroCosto");
                }
            } else {
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCentroCosto");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCentroCosto");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOLOCALIZACION")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getMotivo().setDescripcion(motivoLocalizacion);
            } else if (tipoNuevo == 2) {
                duplicarVL.getMotivo().setDescripcion(motivoLocalizacion);
            }
            for (int i = 0; i < listMotivosLocalizaciones.size(); i++) {
                if (listMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setMotivo(listMotivosLocalizaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivo");
                } else if (tipoNuevo == 2) {
                    duplicarVL.setMotivo(listMotivosLocalizaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivo");
                }
            } else {
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMotivo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivo");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.getProyecto().setNombreproyecto(proyecto);
                } else if (tipoNuevo == 2) {
                    duplicarVL.getProyecto().setNombreproyecto(proyecto);
                }
                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setProyecto(listProyectos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaProyecto");
                    } else if (tipoNuevo == 2) {
                        duplicarVL.setProyecto(listProyectos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarProyecto");
                    }
                } else {
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaProyecto");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarProyecto");
                    }
                }
            }
        } else {
            if (tipoNuevo == 1) {
                nuevaVigencia.setProyecto(new Proyectos());
                context.update("formularioDialogos:nuevaProyecto");
            } else if (tipoNuevo == 2) {
                duplicarVL.setProyecto(new Proyectos());
                context.update("formularioDialogos:duplicarProyecto");
            }
        }
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaProrrateo)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletarVP(int tipoNuevo, String Campo) {
        if (Campo.equals("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                centroCostosVP = nuevaVigenciaP.getCentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                centroCostosVP = duplicarVP.getCentrocosto().getNombre();
            }
        } else if (Campo.equals("PROYECTO")) {
            if (tipoNuevo == 1) {
                proyectoVP = nuevaVigenciaP.getProyecto().getNombreproyecto();
            } else if (tipoNuevo == 2) {
                proyectoVP = duplicarVP.getProyecto().getNombreproyecto();
            }
        }
    }

    /**
     *
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciasProrrateos
     *
     * @param confirmarCambio Tipo de elemento a modificar: CENTROCOSTO -
     * PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicadoVP(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaP.getCentrocosto().setNombre(centroCostosVP);
            } else if (tipoNuevo == 2) {
                duplicarVP.getCentrocosto().setNombre(centroCostosVP);
            }
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaP.setCentrocosto(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCentroCostoVP");
                } else if (tipoNuevo == 2) {
                    duplicarVP.setCentrocosto(listCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicadoCentroCostoVP");
                }
            } else {
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCentroCostoVP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicadoCentroCostoVP");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaP.getProyecto().setNombreproyecto(proyecto);
                } else if (tipoNuevo == 2) {
                    duplicarVP.getProyecto().setNombreproyecto(proyecto);
                }
                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigenciaP.setProyecto(listProyectos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaProyectoVP");
                    } else if (tipoNuevo == 2) {
                        duplicarVP.setProyecto(listProyectos.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicadoProyectoVP");
                    }
                } else {
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaProyectoVP");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicadoProyectoVP");
                    }
                }
            }
        } else {
            if (tipoNuevo == 1) {
                nuevaVigenciaP.setProyecto(new Proyectos());
                context.update("formularioDialogos:nuevaProyectoVP");
            } else if (tipoNuevo == 2) {
                duplicarVP.setProyecto(new Proyectos());
                context.update("formularioDialogos:duplicadoProyectoVP");
            }
        }
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaProrrateoProyecto)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletarVPP(int tipoNuevo, String Campo) {
        if (Campo.equals("PROYECTO")) {
            if (tipoNuevo == 1) {
                proyectoVPP = nuevaVigenciaPP.getProyecto().getNombreproyecto();
            } else if (tipoNuevo == 2) {
                proyectoVPP = duplicarVPP.getProyecto().getNombreproyecto();
            }
        }
    }

    /**
     *
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciaProrrateoProyecto
     *
     * @param confirmarCambio Tipo de elemento a modificar: PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicadoVPP(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaPP.getProyecto().setNombreproyecto(proyecto);
            } else if (tipoNuevo == 2) {
                duplicarVPP.getProyecto().setNombreproyecto(proyecto);
            }
            for (int i = 0; i < listProyectos.size(); i++) {
                if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaPP.setProyecto(listProyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProyectoVPP");
                } else if (tipoNuevo == 2) {
                    duplicarVPP.setProyecto(listProyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarProyectoVPP");
                }
            } else {
                context.update("form:ProyectosDialogo");
                context.execute("ProyectosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaProyectoVPP");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarProyectoVPP");
                }
            }
        }
    }

    public void posicionLocalizacion() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(vigenciaLocalizacionSeleccionada, columna);
    }

    public void cambiarIndice(VigenciasLocalizaciones vLocalizacion, int celda) {
        FacesContext c = FacesContext.getCurrentInstance();
        vigenciaLocalizacionSeleccionada = vLocalizacion;

        if (permitirIndex) {
            if ((cambioVigenciaP == false) && (cambioVigenciaPP == false)) {
                cualCelda = celda;

                if (cualCelda == 1) {
                    centroCosto = vigenciaLocalizacionSeleccionada.getLocalizacion().getCentrocosto().getCodigoNombre();
                } else if (cualCelda == 3) {
                    motivoLocalizacion = vigenciaLocalizacionSeleccionada.getMotivo().getDescripcion();
                } else if (cualCelda == 4) {
                    proyecto = vigenciaLocalizacionSeleccionada.getProyecto().getNombreproyecto();
                }

                vigenciasProrrateosCentroC = null;
                getVigenciasProrrateosCentroC();
                vigenciasProrrateosProyectos = null;
                getVigenciasProrrateosProyectos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarGuardarSinSalir.show()");
            }
        }

        if (banderaVP == 1) {
            restablecerTablaVP();
        }
        if (banderaVPP == 1) {
            restablecerTablaVPP();
        }
        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
        RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVP(VigenciasProrrateos vProrrateos, int celda) {
        vigenciaProrrateoSeleccionada = vProrrateos;
        FacesContext c = FacesContext.getCurrentInstance();
        if (permitirIndexVP) {
            cualCeldaVP = celda;

            if (cambioVigenciaPP == false) {
                if (cualCeldaVP == 0) {
                    centroCostosVP = vigenciaProrrateoSeleccionada.getCentrocosto().getNombre();
                } else if (cualCelda == 4) {
                    proyectoVP = vigenciaProrrateoSeleccionada.getProyecto().getNombreproyecto();
                }
            }
        }
        if (bandera == 1) {
            restablecerTablaVL();
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
        }
        if (banderaVPP == 1) {
            restablecerTablaVPP();
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
        }
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasProrrateosProyectos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVPP(VigenciasProrrateosProyectos vProrrateosProyectos, int celda) {
        vigenciaProrrateoProyectoSeleccionada = vProrrateosProyectos;
        FacesContext c = FacesContext.getCurrentInstance();
        if (permitirIndexVPP) {
            cualCeldaVPP = celda;
            if (cualCeldaVP == 0) {
                proyectoVPP = vigenciaProrrateoProyectoSeleccionada.getProyecto().getNombreproyecto();
            }

        }
        if (bandera == 1) {
            restablecerTablaVL();
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
        }
        if (banderaVP == 1) {
            restablecerTablaVP();
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
        }
    }
    //GUARDAR

    public void guardarYSalir() {
        guardadoGeneral();
        salir();
    }

    public void cancelarYSalir() {
        cancelarModificacion();
        salir();
    }

    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (cambiosVigencia) {
            guardarCambiosVL();
        }
        if (cambioVigenciaP) {
            guardarCambiosVP();
        }
        if (cambioVigenciaPP) {
            guardarCambiosVPP();
        }
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosVL() {
        if (cambiosVigencia) {
            cambiosVigencia = false;
            if (!listVLBorrar.isEmpty()) {
                for (int i = 0; i < listVLBorrar.size(); i++) {
                    if (listVLBorrar.get(i).getProyecto().getSecuencia() == null) {
                        listVLBorrar.get(i).setProyecto(null);
                    }
                    if (listVLBorrar.get(i).getLocalizacion().getSecuencia() == null) {
                        listVLBorrar.get(i).setLocalizacion(null);
                    }
                    if (listVLBorrar.get(i).getMotivo().getSecuencia() == null) {
                        listVLBorrar.get(i).setLocalizacion(null);
                    }
                    administrarVigenciaLocalizacion.borrarVL(listVLBorrar.get(i));
                }
                listVLBorrar.clear();
            }
            if (!listVLCrear.isEmpty()) {
                for (int i = 0; i < listVLCrear.size(); i++) {
                    if (listVLCrear.get(i).getProyecto().getSecuencia() == null) {
                        listVLCrear.get(i).setProyecto(null);
                    }
                    if (listVLCrear.get(i).getLocalizacion().getSecuencia() == null) {
                        listVLCrear.get(i).setLocalizacion(null);
                    }
                    if (listVLCrear.get(i).getMotivo().getSecuencia() == null) {
                        listVLCrear.get(i).setLocalizacion(null);
                    }
                    administrarVigenciaLocalizacion.crearVL(listVLCrear.get(i));
                }
                listVLCrear.clear();
            }
            if (!listVLModificar.isEmpty()) {
                administrarVigenciaLocalizacion.modificarVL(listVLModificar);
                listVLModificar.clear();
            }
            vigenciaLocalizaciones = null;

            contarRegistrosVL();

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Localizacion con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            paraNuevaV = 0;
        }
        vigenciaLocalizacionSeleccionada = null;
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVP() {
        if (cambioVigenciaP) {
            if (!listVPBorrar.isEmpty()) {
                for (int i = 0; i < listVPBorrar.size(); i++) {
                    if (listVPBorrar.get(i).getProyecto().getSecuencia() == null) {
                        listVPBorrar.get(i).setProyecto(null);
                    }
                    if (listVPBorrar.get(i).getCentrocosto().getSecuencia() == null) {
                        listVPBorrar.get(i).setCentrocosto(null);
                    }
                    if (listVPBorrar.get(i).getViglocalizacion().getProyecto().getSecuencia() == null) {
                        listVPBorrar.get(i).getViglocalizacion().setProyecto(null);
                    }
                    administrarVigenciaLocalizacion.borrarVP(listVPBorrar.get(i));
                }
                listVPBorrar.clear();
            }
            if (!listVPCrear.isEmpty()) {
                for (int i = 0; i < listVPCrear.size(); i++) {
                    if (listVPCrear.get(i).getProyecto().getSecuencia() == null) {
                        listVPCrear.get(i).setProyecto(null);
                    }
                    if (listVPCrear.get(i).getCentrocosto().getSecuencia() == null) {
                        listVPCrear.get(i).setCentrocosto(null);
                    }
                    if (listVPCrear.get(i).getViglocalizacion().getProyecto().getSecuencia() == null) {
                        listVPCrear.get(i).getViglocalizacion().setProyecto(null);
                    }
                    administrarVigenciaLocalizacion.crearVP(listVPCrear.get(i));
                }
                listVPCrear.clear();
            }
            if (!listVPModificar.isEmpty()) {
                administrarVigenciaLocalizacion.modificarVP(listVPModificar);
                listVPModificar.clear();
            }
            vigenciasProrrateosCentroC = null;
            contarRegistrosVP();
            paraNuevaV = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            cambioVigenciaP = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Prorrateo Centro Costo con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        vigenciaProrrateoSeleccionada = null;
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasProrrateosProyectos
     */
    public void guardarCambiosVPP() {
        if (cambioVigenciaPP) {
            if (!listVPPBorrar.isEmpty()) {
                for (int i = 0; i < listVPPBorrar.size(); i++) {
                    if (listVPPBorrar.get(i).getProyecto().getSecuencia() == null) {
                        listVPPBorrar.get(i).setProyecto(null);
                    }
                    if (listVPPBorrar.get(i).getVigencialocalizacion().getProyecto().getSecuencia() == null) {
                        listVPPBorrar.get(i).getVigencialocalizacion().setProyecto(null);
                    }
                    administrarVigenciaLocalizacion.borrarVPP(listVPPBorrar.get(i));
                }
                listVPPBorrar.clear();
            }
            if (!listVPPCrear.isEmpty()) {
                for (int i = 0; i < listVPPCrear.size(); i++) {
                    if (listVPPCrear.get(i).getProyecto().getSecuencia() == null) {
                        listVPPCrear.get(i).setProyecto(null);
                    }
                    if (listVPPCrear.get(i).getVigencialocalizacion().getProyecto().getSecuencia() == null) {
                        listVPPCrear.get(i).getVigencialocalizacion().setProyecto(null);
                    }

                    administrarVigenciaLocalizacion.crearVPP(listVPPCrear.get(i));
                }
                listVPPCrear.clear();
            }
            if (!listVPPModificar.isEmpty()) {
                administrarVigenciaLocalizacion.modificarVPP(listVPPModificar);
                listVPPModificar.clear();
            }
            vigenciasProrrateosProyectos = null;
            contarRegistrosVPP();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            paraNuevaV = 0;
            cambioVigenciaPP = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Prorrateo Proyecto con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        vigenciaProrrateoProyectoSeleccionada = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            restablecerTablaVL();
            //CERRAR FILTRADO
            context.update("form:datosVLEmpleado");
        }
        if (banderaVP == 1) {
            restablecerTablaVP();
            context.update("form:datosVPVigencia");
        }
        if (banderaVPP == 1) {
            restablecerTablaVPP();
            context.update("form:datosVPPVigencia");
        }
        listVLBorrar.clear();
        listVPBorrar.clear();
        listVPPBorrar.clear();
        listVLCrear.clear();
        listVPCrear.clear();
        listVPPCrear.clear();
        listVLModificar.clear();
        listVPModificar.clear();
        listVPPModificar.clear();
        vigenciaLocalizacionSeleccionada = null;
        vigenciaProrrateoProyectoSeleccionada = null;
        vigenciaProrrateoSeleccionada = null;

        paraNuevaV = 0;
        vigenciaLocalizaciones = null;
        vigenciasProrrateosCentroC = null;
        vigenciasProrrateosProyectos = null;
        guardado = true;
        cambiosVigencia = false;
        cambioVigenciaP = false;
        cambioVigenciaPP = false;

        getVigenciaLocalizaciones();
        getVigenciasProrrateosCentroC();
        getVigenciasProrrateosProyectos();
        contarRegistrosVL();
        contarRegistrosVP();
        contarRegistrosVPP();

        context.update("form:ACEPTAR");
        context.update("form:datosVLEmpleado");
        context.update("form:datosVPVigencia");
        context.update("form:datosVPPVigencia");
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVP() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVP == 1) {
            restablecerTablaVP();
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
        }
        listVPBorrar.clear();
        listVPCrear.clear();
        listVPModificar.clear();
        vigenciaProrrateoSeleccionada = null;
        paraNuevaV = 0;
        vigenciasProrrateosCentroC = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVPVigencia");
        cambioVigenciaP = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos proyectos
     */
    public void cancelarModificacionVPP() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVPP == 1) {
            restablecerTablaVPP();
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
        }
        listVPPBorrar.clear();
        listVPPCrear.clear();
        listVPPModificar.clear();
        vigenciaProrrateoProyectoSeleccionada = null;
        paraNuevaV = 0;
        vigenciasProrrateosProyectos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVPPVigencia");
        cambioVigenciaPP = false;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (vigenciaLocalizacionSeleccionada == null && vigenciaProrrateoSeleccionada == null && vigenciaProrrateoProyectoSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (vigenciaLocalizacionSeleccionada != null) {
                editarVL = vigenciaLocalizacionSeleccionada;

                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarFechaVigencia");
                    context.execute("editarFechaVigencia.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarCentroCosto");
                    context.execute("editarCentroCosto.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarLocalizacion");
                    context.execute("editarLocalizacion.show()");
                    cualCelda = -1;
                } else if (cualCelda == 3) {
                    context.update("formularioDialogos:editarMotivoD");
                    context.execute("editarMotivoD.show()");
                    cualCelda = -1;
                } else if (cualCelda == 4) {
                    context.update("formularioDialogos:editarProyecto");
                    context.execute("editarProyecto.show()");
                    cualCelda = -1;
                }
            }
            if (vigenciaProrrateoSeleccionada != null) {
                editarVP = vigenciaProrrateoSeleccionada;

                if (cualCeldaVP == 0) {
                    context.update("formularioDialogos:editarCentroCostoVP");
                    context.execute("editarCentroCostoVP.show()");
                    cualCeldaVP = -1;
                } else if (cualCeldaVP == 1) {
                    context.update("formularioDialogos:sali");
                    context.execute("editarPorcentajeVP.show()");
                    cualCeldaVP = -1;
                } else if (cualCeldaVP == 2) {
                    context.update("formularioDialogos:editarFechaInicialVP");
                    context.execute("editarFechaInicialVP.show()");
                    cualCeldaVP = -1;
                } else if (cualCeldaVP == 3) {
                    context.update("formularioDialogos:editarFechaFinalVP");
                    context.execute("editarFechaFinalVP.show()");
                    cualCeldaVP = -1;
                } else if (cualCeldaVP == 4) {
                    context.update("formularioDialogos:editarProyectoVP");
                    context.execute("editarProyectoVP.show()");
                    cualCeldaVP = -1;
                } else if (cualCeldaVP == 5) {
                    context.update("formularioDialogos:editarSubPorcentajeVP");
                    context.execute("editarSubPorcentajeVP.show()");
                    cualCeldaVP = -1;
                }
            }
            if (vigenciaProrrateoProyectoSeleccionada != null) {
                editarVPP = vigenciaProrrateoProyectoSeleccionada;

                if (cualCeldaVPP == 0) {
                    context.update("formularioDialogos:editarProyectoVPP");
                    context.execute("editarProyectoVPP.show()");
                    cualCeldaVPP = -1;
                } else if (cualCeldaVPP == 1) {
                    context.update("formularioDialogos:editarPorcentajeVPP");
                    context.execute("editarPorcentajeVPP.show()");
                    cualCeldaVPP = -1;
                } else if (cualCeldaVPP == 2) {
                    context.update("formularioDialogos:editarFechaInicialVPP");
                    context.execute("editarFechaInicialVPP.show()");
                    cualCeldaVPP = -1;
                } else if (cualCeldaVPP == 3) {
                    context.update("formularioDialogos:editarFechaFinalVPP");
                    context.execute("editarFechaFinalVPP.show()");
                    cualCeldaVPP = -1;
                }
            }
        }
    }
    //CREAR VL

    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevaVL() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigencia.getFechavigencia() != null && nuevaVigencia.getLocalizacion().getSecuencia() != null && nuevaVigencia.getMotivo().getSecuencia() != null) {
            int cont = 0;
            mensajeValidacion = "";
            for (int j = 0; j < vigenciaLocalizaciones.size(); j++) {
                if (nuevaVigencia.getFechavigencia().equals(vigenciaLocalizaciones.get(j).getFechavigencia())) {
                    cont++;
                }
            }
            if (cont > 0) {
                mensajeValidacion = "FECHAS NO REPETIDAS";
                context.update("form:validarNuevoFechas");
                context.execute("validarNuevoFechas.show()");
            } else {
                FacesContext c = FacesContext.getCurrentInstance();
                if (validarFechasRegistro(1)) {
                    cambiosVigencia = true;
                    if (bandera == 1) {
                        restablecerTablaVL();
                        //CERRAR FILTRADO
                        RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                    }
                    //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    nuevaVigencia.setSecuencia(nuevaVLSecuencia);
                    nuevaVigencia.setEmpleado(empleado);
                    listVLCrear.add(nuevaVigencia);
                    vigenciaLocalizaciones.add(nuevaVigencia);
                    modificarInfoRegistroVL(vigenciaLocalizaciones.size());
                    nuevaVigencia = new VigenciasLocalizaciones();
                    nuevaVigencia.setLocalizacion(new Estructuras());
                    nuevaVigencia.getLocalizacion().setCentrocosto(new CentrosCostos());
                    nuevaVigencia.setMotivo(new MotivosLocalizaciones());
                    nuevaVigencia.setProyecto(new Proyectos());
                    context.update("form:datosVLEmpleado");
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.update("form:NuevoRegistroVL");
                    context.execute("NuevoRegistroVL.hide()");
                    vigenciaLocalizacionSeleccionada = null;
                } else {
                    context.update("errorFechaVL");
                    context.execute("errorFechaVL.show()");
                }
            }
        } else {
            context.update("formularioDialogos:negacionNuevaVL");
            context.execute("negacionNuevaVL.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVL() {
        nuevaVigencia = new VigenciasLocalizaciones();
        nuevaVigencia.setLocalizacion(new Estructuras());
        nuevaVigencia.getLocalizacion().setCentrocosto(new CentrosCostos());
        nuevaVigencia.setMotivo(new MotivosLocalizaciones());
        nuevaVigencia.setProyecto(new Proyectos());
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigenciaP.getCentrocosto().getSecuencia() != null && nuevaVigenciaP.getPorcentaje() != null && nuevaVigenciaP.getFechainicial() != null) {
            int cont = 0;
            mensajeValidacion = "";
            for (int j = 0; j < vigenciasProrrateosCentroC.size(); j++) {
                if (nuevaVigenciaP.getFechainicial().equals(vigenciasProrrateosCentroC.get(j).getFechainicial())) {
                    cont++;
                }
            }
            if (cont > 0) {
                mensajeValidacion = "FECHAS INICIALES NO REPETIDAS";
                context.update("form:validarNuevoFechas");
                context.execute("validarNuevoFechas.show()");
            } else {
                if (validarFechasRegistroVigenciaProrrateo(1)) {
                    cambioVigenciaP = true;
                    //CERRAR FILTRADO 
                    if (banderaVP == 1) {
                        restablecerTablaVP();
                        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                    } //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                    paraNuevaV++;
                    nuevaVLSecuencia =
                            BigInteger.valueOf(paraNuevaV);
                    nuevaVigenciaP.setSecuencia(nuevaVLSecuencia);
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        nuevaVigenciaP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                    }
                    if (tipoLista == 1) {// Si tiene Filtro
                        nuevaVigenciaP.setViglocalizacion(filtradoVL.get(indexAuxVL));
                    }
                    listVPCrear.add(nuevaVigenciaP);
                    if (vigenciasProrrateosCentroC == null) {
                        vigenciasProrrateosCentroC = new ArrayList<VigenciasProrrateos>();
                    }
                    vigenciasProrrateosCentroC.add(nuevaVigenciaP);
                    modificarInfoRegistroVP(vigenciasProrrateosCentroC.size());
                    nuevaVigenciaP = new VigenciasProrrateos();
                    nuevaVigenciaP.setCentrocosto(new CentrosCostos());
                    nuevaVigenciaP.setProyecto(new Proyectos());
                    context.update("form:datosVPVigencia");
                    context.execute("NuevoRegistroVP.hide()");
                    cualTabla = 0;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    tipoActualizacion = -1;
                    vigenciaProrrateoSeleccionada = null;
                } else {
                    context.update("form:errorFechas");
                    context.execute("errorFechas.show()");
                }
            }
        } else {
            context.update("formularioDialogos:negacionNuevaVLP");
            context.execute("negacionNuevaVLP.show()");
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaVP() {
        nuevaVigenciaP = new VigenciasProrrateos();
        nuevaVigenciaP.setCentrocosto(new CentrosCostos());
        nuevaVigenciaP.setProyecto(new Proyectos());
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva vigencia prorrateo proyecto
     */
    public void agregarNuevaVPP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigenciaPP.getFechainicial() != null && nuevaVigenciaPP.getPorcentaje() >= 0 && nuevaVigenciaPP.getProyecto().getSecuencia() != null) {
            int cont = 0;
            mensajeValidacion = "";
            for (int j = 0; j < vigenciasProrrateosProyectos.size(); j++) {
                if (nuevaVigenciaPP.getFechainicial().equals(vigenciasProrrateosProyectos.get(j).getFechainicial())) {
                    cont++;
                }
            }
            if (cont > 0) {
                mensajeValidacion = "FECHAS INICIALES NO REPETIDAS";
                context.update("form:validarNuevoFechas");
                context.execute("validarNuevoFechas.show()");
            } else {
                if (validarFechasRegistroVigenciaProrrateoProyecto(1)) {
                    cambioVigenciaPP = true;
                    //CERRAR FILTRADO
                    if (banderaVPP == 1) {
                        restablecerTablaVPP();
                        RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                    }
                    //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    nuevaVigenciaPP.setSecuencia(nuevaVLSecuencia);
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        nuevaVigenciaPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                    }
                    if (tipoLista == 1) {// Si tiene Filtro
                        nuevaVigenciaPP.setVigencialocalizacion(filtradoVL.get(indexAuxVL));
                    }
                    listVPPCrear.add(nuevaVigenciaPP);
                    vigenciasProrrateosProyectos.add(nuevaVigenciaPP);
                    modificarInfoRegistroVPP(vigenciasProrrateosProyectos.size());
                    nuevaVigenciaPP = new VigenciasProrrateosProyectos();
                    nuevaVigenciaPP.setProyecto(new Proyectos());
                    context.update("form:datosVPPVigencia");
                    context.execute("NuevoRegistroVPP.hide()");
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    vigenciaProrrateoProyectoSeleccionada = null;
                } else {
                    context.update("form:errorFechas");
                    context.execute("errorFechas.show()");
                }
            }
        } else {
            context.update("formularioDialogos:negacionNuevaVLPP");
            context.execute("negacionNuevaVLPP.show()");
        }
    }

    /**
     * Elimina los datos de una nueva vigencia prorrateo proyecto
     */
    public void limpiarNuevaVPP() {
        nuevaVigenciaPP = new VigenciasProrrateosProyectos();
        nuevaVigenciaPP.setProyecto(new Proyectos());
    }
    //DUPLICAR VL

    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (vigenciaLocalizacionSeleccionada == null && vigenciaProrrateoSeleccionada == null && vigenciaProrrateoProyectoSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (guardado) {
                tipoActualizacion = 2;
                if (vigenciaLocalizacionSeleccionada != null) {
                    duplicarVigenciaL();
                }
                if (vigenciaProrrateoSeleccionada != null) {
                    duplicarVigenciaP();
                }
                if (vigenciaProrrateoProyectoSeleccionada != null) {
                    duplicarVigenciaPP();
                }
            } else {
                context.execute("confirmarGuardarSinSalir.show()");
            }
        }
    }

    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarVigenciaL() {
        if (vigenciaLocalizacionSeleccionada != null) {
            duplicarVL = new VigenciasLocalizaciones();
            paraNuevaV++;
            nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);

            duplicarVL.setEmpleado(vigenciaLocalizacionSeleccionada.getEmpleado());
            duplicarVL.setFechavigencia(vigenciaLocalizacionSeleccionada.getFechavigencia());
            duplicarVL.setLocalizacion(vigenciaLocalizacionSeleccionada.getLocalizacion());
            duplicarVL.setMotivo(vigenciaLocalizacionSeleccionada.getMotivo());
            duplicarVL.setProyecto(vigenciaLocalizacionSeleccionada.getProyecto());

            if (duplicarVL.getMotivo() == null) {
                duplicarVL.setMotivo(new MotivosLocalizaciones());
            }
            if (duplicarVL.getProyecto() == null) {
                duplicarVL.setProyecto(new Proyectos());
            }
            if (duplicarVL.getLocalizacion() == null) {
                duplicarVL.setLocalizacion(new Estructuras());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVL");
            context.execute("DuplicarRegistroVL.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        mensajeValidacion = "";
        for (int j = 0; j < vigenciaLocalizaciones.size(); j++) {
            if (duplicarVL.getFechavigencia().equals(vigenciaLocalizaciones.get(j).getFechavigencia())) {
                cont++;
            }
        }
        if (cont > 0) {
            mensajeValidacion = "FECHAS NO REPETIDAS";
            context.update("form:validarNuevoFechas");
            context.execute("validarNuevoFechas.show()");
        } else {
            if (duplicarVL.getFechavigencia() != null && duplicarVL.getLocalizacion().getSecuencia() != null && duplicarVL.getMotivo().getSecuencia() != null) {
                if (validarFechasRegistro(2)) {
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    duplicarVL.setSecuencia(nuevaVLSecuencia);
                    cambiosVigencia = true;
                    vigenciaLocalizaciones.add(duplicarVL);
                    listVLCrear.add(duplicarVL);
                    modificarInfoRegistroVL(vigenciaLocalizaciones.size());
                    vigenciaLocalizacionSeleccionada = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (bandera == 1) {
                        restablecerTablaVL();
                        //CERRAR FILTRADO
                    }
                    context.update("form:datosVLEmpleado");
                    context.execute("DuplicarRegistroVL.hide();");
                    duplicarVL = new VigenciasLocalizaciones();
                } else {
                    context.execute("errorFechaVL.show()");
                }
            } else {
                context.execute("negacionNuevaVL.show()");
            }
        }
    }
//LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia Localizacion
     */
    public void limpiarduplicarVL() {
        duplicarVL = new VigenciasLocalizaciones();
        duplicarVL.setLocalizacion(new Estructuras());
        duplicarVL.getLocalizacion().setCentrocosto(new CentrosCostos());
        duplicarVL.setMotivo(new MotivosLocalizaciones());
        duplicarVL.setProyecto(new Proyectos());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarVigenciaP() {
        if (vigenciaProrrateoSeleccionada != null) {
            duplicarVP = new VigenciasProrrateos();

            duplicarVP.setCentrocosto(vigenciaProrrateoSeleccionada.getCentrocosto());
            duplicarVP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
            duplicarVP.setFechafinal(vigenciaProrrateoSeleccionada.getFechafinal());
            duplicarVP.setFechainicial(vigenciaProrrateoSeleccionada.getFechainicial());
            duplicarVP.setPorcentaje(vigenciaProrrateoSeleccionada.getPorcentaje());
            duplicarVP.setSubporcentaje(vigenciaProrrateoSeleccionada.getSubporcentaje());
            duplicarVP.setProyecto(vigenciaProrrateoSeleccionada.getProyecto());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoVP");
            context.execute("DuplicadoRegistroVP.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVP() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        mensajeValidacion = "";
        for (int j = 0; j < vigenciasProrrateosCentroC.size(); j++) {
            if (duplicarVP.getFechainicial().equals(vigenciasProrrateosCentroC.get(j).getFechainicial())) {
                cont++;
            }
        }
        if (cont > 0) {
            mensajeValidacion = "FECHAS INICIALES NO REPETIDAS";
            context.update("form:validarNuevoFechas");
            context.execute("validarNuevoFechas.show()");
        } else {
            if (duplicarVP.getCentrocosto().getSecuencia() != null && duplicarVP.getPorcentaje() == null && duplicarVP.getFechainicial() != null) {
                if (validarFechasRegistroVigenciaProrrateo(2)) {
                    cambioVigenciaP = true;
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    duplicarVP.setSecuencia(nuevaVLSecuencia);
                    vigenciasProrrateosCentroC.add(duplicarVP);
                    listVPCrear.add(duplicarVP);
                    modificarInfoRegistroVP(vigenciasProrrateosCentroC.size());
                    context.update("form:datosVPVigencia");
                    context.execute("DuplicadoRegistroVP.hide();");
                    vigenciaProrrateoSeleccionada = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (banderaVP == 1) {
                        //CERRAR FILTRADO
                        restablecerTablaVP();
                        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                    }
                    duplicarVP = new VigenciasProrrateos();
                } else {
                    context.execute("errorFechas.show();");
                }
            } else {
                context.execute("errorRegnew.show();");
            }

        }
    }

    /**
     * Limpia los elementos del duplicar registro Vigencia Prorrateo
     */
    public void limpiarduplicarVP() {
        duplicarVP = new VigenciasProrrateos();
        duplicarVP.setCentrocosto(new CentrosCostos());
        duplicarVP.setProyecto(new Proyectos());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica un registo Vigencia Prorrateo Proyecto
     */
    public void duplicarVigenciaPP() {
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            duplicarVPP = new VigenciasProrrateosProyectos();

            duplicarVPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
            duplicarVPP.setFechafinal(vigenciaProrrateoProyectoSeleccionada.getFechafinal());
            duplicarVPP.setFechainicial(vigenciaProrrateoProyectoSeleccionada.getFechainicial());
            duplicarVPP.setPorcentaje(vigenciaProrrateoProyectoSeleccionada.getPorcentaje());
            duplicarVPP.setProyecto(vigenciaProrrateoProyectoSeleccionada.getProyecto());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVPP");
            context.execute("DuplicarRegistroVPP.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * Vigencia Prorrateo Proyecto
     */
    public void confirmarDuplicarVPP() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        mensajeValidacion = "";
        for (int j = 0; j < vigenciasProrrateosProyectos.size(); j++) {
            if (duplicarVPP.getFechainicial().equals(vigenciasProrrateosProyectos.get(j).getFechainicial())) {
                cont++;
            }
        }
        if (cont > 0) {
            mensajeValidacion = "FECHAS INICIALES NO REPETIDAS";
            context.update("form:validarNuevoFechas");
            context.execute("validarNuevoFechas.show()");
        } else {

            if (duplicarVPP.getFechainicial() != null && duplicarVPP.getPorcentaje() >= 0 && duplicarVPP.getProyecto().getSecuencia() != null) {
                if (validarFechasRegistroVigenciaProrrateoProyecto(2)) {
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    duplicarVPP.setSecuencia(nuevaVLSecuencia);
                    vigenciasProrrateosProyectos.add(duplicarVPP);
                    listVPPCrear.add(duplicarVPP);
                    modificarInfoRegistroVPP(vigenciasProrrateosProyectos.size());
                    context.update("form:datosVPPVigencia");
                    context.execute("DuplicarRegistroVPP.show()");
                    vigenciaProrrateoProyectoSeleccionada = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (banderaVPP == 1) {
                        restablecerTablaVPP();
                        RequestContext.getCurrentInstance().update("form:datosVPPVigencia");

                    }
                    duplicarVPP = new VigenciasProrrateosProyectos();
                    cambioVigenciaPP = true;
                } else {
                    context.execute("errorFechas.show();");
                }
            } else {
                context.execute("errorRegnew.show();");
            }
        }
    }

    /**
     * Limpia el registro de duplicar Vigencia Prorrateo Proyecto
     */
    public void limpiarduplicarVPP() {
        duplicarVPP = new VigenciasProrrateosProyectos();
        duplicarVPP.setProyecto(new Proyectos());
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoVigencia() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (vigenciaLocalizacionSeleccionada == null && vigenciaProrrateoSeleccionada == null && vigenciaProrrateoProyectoSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (vigenciaLocalizacionSeleccionada != null) {
                int tam = 0;
                if (vigenciasProrrateosProyectos != null) {
                    tam = vigenciasProrrateosProyectos.size();
                }
                int tam2 = 0;
                if (vigenciasProrrateosCentroC != null) {
                    tam2 = vigenciasProrrateosCentroC.size();
                }
                if (tam == 0 && tam2 == 0) {
                    borrarVL();
                } else {
                    context.update("form:negacionBorradoVL");
                    context.execute("negacionBorradoVL.show()");
                }
            }
            if (vigenciaProrrateoSeleccionada != null) {
                borrarVP();
            }
            if (vigenciaProrrateoProyectoSeleccionada != null) {
                borrarVPP();
            }
        }
    }
    //BORRAR VL

    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarVL() {
        if (vigenciaLocalizacionSeleccionada != null) {
            cambiosVigencia = true;
            if (!listVLModificar.isEmpty() && listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                int modIndex = listVLModificar.indexOf(vigenciaLocalizacionSeleccionada);
                listVLModificar.remove(modIndex);
                listVLBorrar.add(vigenciaLocalizacionSeleccionada);
            } else if (!listVLCrear.isEmpty() && listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {
                int crearIndex = listVLCrear.indexOf(vigenciaLocalizacionSeleccionada);
                listVLCrear.remove(crearIndex);
            } else {
                listVLBorrar.add(vigenciaLocalizacionSeleccionada);
            }
            vigenciaLocalizaciones.remove(vigenciaLocalizacionSeleccionada);
            if (tipoLista == 1) {
                filtradoVL.remove(vigenciaLocalizacionSeleccionada);
            }
            modificarInfoRegistroVL(vigenciaLocalizaciones.size());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            vigenciaLocalizacionSeleccionada = null;
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarVP() {
        cambioVigenciaP = true;
        if (vigenciaProrrateoSeleccionada != null) {
            if (!listVPModificar.isEmpty() && listVPModificar.contains(vigenciaProrrateoSeleccionada)) {
                int modIndex = listVPModificar.indexOf(vigenciaProrrateoSeleccionada);
                listVPModificar.remove(modIndex);
                listVPBorrar.add(vigenciaProrrateoSeleccionada);
            } else if (!listVPCrear.isEmpty() && listVPCrear.contains(vigenciaProrrateoSeleccionada)) {
                int crearIndex = listVPCrear.indexOf(vigenciaProrrateoSeleccionada);
                listVPCrear.remove(crearIndex);
            } else {
                listVPBorrar.add(vigenciaProrrateoSeleccionada);
            }
            vigenciasProrrateosCentroC.remove(vigenciaProrrateoSeleccionada);
            if (tipoLista == 1) {
                filtradoVP.remove(vigenciaProrrateoSeleccionada);
            }
            modificarInfoRegistroVP(vigenciasProrrateosCentroC.size());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPVigencia");
            vigenciaProrrateoSeleccionada = null;
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo proyecto
     */
    public void borrarVPP() {
        cambioVigenciaPP = true;
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            if (!listVPPModificar.isEmpty() && listVPPModificar.contains(vigenciaProrrateoProyectoSeleccionada)) {
                int modIndex = listVPPModificar.indexOf(vigenciaProrrateoProyectoSeleccionada);
                listVPPModificar.remove(modIndex);
                listVPPBorrar.add(vigenciaProrrateoProyectoSeleccionada);
            } else if (!listVPPCrear.isEmpty() && listVPPCrear.contains(vigenciaProrrateoProyectoSeleccionada)) {
                int crearIndex = listVPPCrear.indexOf(vigenciaProrrateoProyectoSeleccionada);
                listVPPCrear.remove(crearIndex);
            } else {
                listVPPBorrar.add(vigenciaProrrateoProyectoSeleccionada);
            }
            vigenciasProrrateosProyectos.remove(vigenciaProrrateoProyectoSeleccionada);
            if (tipoLista == 1) {
                filtradoPP.remove(vigenciaProrrateoProyectoSeleccionada);
            }
            modificarInfoRegistroVPP(vigenciasProrrateosProyectos.size());

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            vigenciaProrrateoProyectoSeleccionada = null;
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (vigenciaLocalizacionSeleccionada != null) {
            filtradoVigenciaLocalizacion();
            vigenciaLocalizacionSeleccionada = null;
        }
        if (vigenciaProrrateoSeleccionada != null) {
            filtradoVigenciaProrrateo();
            vigenciaProrrateoSeleccionada = null;
        }
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            filtradoVigenciaProrrateoProyecto();
            vigenciaProrrateoProyectoSeleccionada = null;
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoVigenciaLocalizacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            vlFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("width: 80%");
            vlCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("width: 80%");
            vlLocalizacion = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("width: 80%");
            vlMotivo = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("width: 80%");
            vlProyecto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("width: 80%");
            altoTabla1 = "91";
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            restablecerTablaVL();
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaProrrateo() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVP == 0) {
            //Columnas Tabla VPP
            vPCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
            vPCentroCosto.setFilterStyle("width: 80%");
            vPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("width: 80%");
            vPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("width: 80%");
            vPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("width: 80%");
            vPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("width: 80%");
            vPSubPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("width: 80%");
            altoTabla2 = "91";
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 1;
        } else if (banderaVP == 1) {
            restablecerTablaVP();
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo proyecto
     */
    public void filtradoVigenciaProrrateoProyecto() {
        FacesContext c = FacesContext.getCurrentInstance();
        //Columnas Tabla VPP
        if (banderaVPP == 0) {
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("width: 80%");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("width: 80%");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("width: 80%");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("width: 80%");
            altoTabla3 = "91";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 1;
        } else if (banderaVPP == 1) {
            restablecerTablaVPP();
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
        }

    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            restablecerTablaVL();
            context.update("form:datosVLEmpleado");
        }

        listVLBorrar.clear();
        listVLCrear.clear();
        listVLModificar.clear();
        vigenciaLocalizacionSeleccionada = null;
        vigenciaProrrateoSeleccionada = null;
        vigenciaProrrateoProyectoSeleccionada = null;
        paraNuevaV = 0;
        vigenciaLocalizaciones = null;
        listCentrosCostos = null;
        listProyectos = null;
        listMotivosLocalizaciones = null;
        listEstructurasCC = null;
        guardado = true;
        vigenciasProrrateosCentroC = null;
        vigenciasProrrateosProyectos = null;
        context.update("form:ACEPTAR");
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     */
    public void asignarIndex(VigenciasLocalizaciones vLocalizacion, int dlg, int tipoAct) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = tipoAct;
        if (tipoAct == 0) {
            vigenciaLocalizacionSeleccionada = vLocalizacion;
        }

        if (dlg == 0) {
            //Estructuras
            contarRegistrosEstrLoca();
            context.update("form:LocalizacionDialogo");
            context.execute("LocalizacionDialogo.show()");
        } else if (dlg == 1) {
            //MotivosLocalizaciones
            contarRegistrosMotiLoc();
            context.update("form:MotivoDialogo");
            context.execute("MotivoDialogo.show()");
        } else if (dlg == 2) {
            //Proyectos
            contarRegistrosProyecto();
            context.update("form:ProyectosDialogo");
            context.execute("ProyectosDialogo.show()");
        }
    }

    public void asignarIndexVP(VigenciasProrrateos vProrrateo, int dlg, int tipoAct) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = tipoAct;
        if (tipoAct == 0) {
            vigenciaProrrateoSeleccionada = vProrrateo;
        }

        if (dlg == 0) {
            //Centro Costos
            contarRegistrosCentroC();
            context.update("form:CentroCostosDialogo");
            context.execute("CentroCostosDialogo.show()");
        } else if (dlg == 1) {
            //Proyectos
            contarRegistrosProyecto();
            context.update("form:ProyectosDialogo");
            context.execute("ProyectosDialogo.show()");
        }
    }

    public void asignarIndexVPP(VigenciasProrrateosProyectos vProrrateosProyectos, int dlg, int tipoAct) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = tipoAct;
        if (tipoAct == 0) {
            vigenciaProrrateoProyectoSeleccionada = vProrrateosProyectos;
        }
        if (dlg == 0) {
            //Proyectos
            contarRegistrosProyecto();
            context.update("form:ProyectosDialogo");
            context.execute("ProyectosDialogo.show()");
        }
    }

    //LOVS
    //Estructuras
    /**
     * Metodo que actualiza la estructura seleccionada (vigencia localizacion)
     */
    public void actualizarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {//// Si se trabaja sobre la tabla y no sobre un dialogo
            vigenciaLocalizacionSeleccionada.setLocalizacion(estructuraSelecionada);
            if (!listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {
                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                } else if (!listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosVigencia = true;
            permitirIndex = true;
            context.update("form:datosVLEmpleado");
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigencia.setLocalizacion(estructuraSelecionada);
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVL.setLocalizacion(estructuraSelecionada);
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoEstructuraCC = null;
        estructuraSelecionada = new Estructuras();
        aceptar = true;
        tipoActualizacion = -1;

        context.reset("form:lovLocalizacion:globalFilter");
        context.execute("lovLocalizacion.clearFilters()");
        context.execute("LocalizacionDialogo.hide()");
    }

    /**
     * Metodo que cancela los cambios sobre estructura (vigencia localizacion)
     */
    public void cancelarCambioEstructura() {
        filtradoEstructuraCC = null;
        estructuraSelecionada = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovLocalizacion:globalFilter");
        context.execute("lovLocalizacion.clearFilters()");
        context.execute("LocalizacionDialogo.hide()");
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarMotivoLocalizacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            vigenciaLocalizacionSeleccionada.setMotivo(motivoLocalizacionSelecionado);
            if (!listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {
                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                } else if (!listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                    listVLModificar.add(vigenciaLocalizacionSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosVigencia = true;
            permitirIndex = true;
            context.update("form:datosVLEmpleado");
        } else if (tipoActualizacion == 1) {// Para cerar registro
            nuevaVigencia.setMotivo(motivoLocalizacionSelecionado);
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVL.setMotivo(motivoLocalizacionSelecionado);
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoMotivosLocalizaciones = null;
        motivoLocalizacionSelecionado = new MotivosLocalizaciones();
        aceptar = true;
        tipoActualizacion = -1;

        context.reset("form:lovMotivo:globalFilter");
        context.execute("lovMotivo.clearFilters()");
        context.execute("MotivoDialogo.hide()");
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioMotivoLocalizacion() {
        filtradoMotivosLocalizaciones = null;
        motivoLocalizacionSelecionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovMotivo:globalFilter");
        context.execute("lovMotivo.clearFilters()");
        context.execute("MotivoDialogo.hide()");
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarProyecto() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (tipoActualizacion == 1) {// Para crear registro

            if (cualTabla == 1) {
                nuevaVigencia.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:nuevaVL");
            } else if (cualTabla == 2) {
                nuevaVigenciaP.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:nuevaVP");
            } else if (cualTabla == 3) {
                nuevaVigenciaPP.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:nuevaVPP");
            }

        } else if (vigenciaProrrateoSeleccionada != null) {
            if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
                vigenciaProrrateoSeleccionada.setProyecto(proyectoSelecionado);
                if (!listVPCrear.contains(vigenciaProrrateoSeleccionada)) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciaProrrateoSeleccionada);
                    } else if (!listVPModificar.contains(vigenciaProrrateoSeleccionada)) {
                        listVPModificar.add(vigenciaProrrateoSeleccionada);
                    }
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambioVigenciaP = true;
                permitirIndexVP = true;
                context.update("form:datosVPVigencia");
            } else if (tipoActualizacion == 2) {// Para duplicar registro
                duplicarVP.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:duplicarVP");
            }
            filtradoProyectos = null;
            proyectoSelecionado = new Proyectos();
            aceptar = true;
            tipoActualizacion = -1;

            context.reset("form:lovProyectos:globalFilter");
            context.execute("lovProyectos.clearFilters()");
            context.execute("ProyectosDialogo.hide()");

            //////////////////////////////////////////////
        } else if (vigenciaProrrateoProyectoSeleccionada != null) {
            if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
                vigenciaProrrateoProyectoSeleccionada.setProyecto(proyectoSelecionado);
                if (!listVPPCrear.contains(vigenciaProrrateoProyectoSeleccionada)) {
                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
                    } else if (!listVPPModificar.contains(vigenciaProrrateoProyectoSeleccionada)) {
                        listVPPModificar.add(vigenciaProrrateoProyectoSeleccionada);
                    }
                }

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                permitirIndexVPP = true;
                cambioVigenciaPP = true;
                context.update("form:datosVPPVigencia");
            } else if (tipoActualizacion == 2) {// Para duplicar registro
                duplicarVPP.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:duplicarVPP");
            }
            filtradoProyectos = null;
            proyectoSelecionado = new Proyectos();
            aceptar = true;
            tipoActualizacion = -1;

            context.reset("form:lovProyectos:globalFilter");
            context.execute("lovProyectos.clearFilters()");
            context.execute("ProyectosDialogo.hide()");

            ///////////////////////////////////////////////////////////////    
        } else if (vigenciaLocalizacionSeleccionada != null) {
            if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
                vigenciaLocalizacionSeleccionada.setProyecto(proyectoSelecionado);
                if (!listVLCrear.contains(vigenciaLocalizacionSeleccionada)) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizacionSeleccionada);
                    } else if (!listVLModificar.contains(vigenciaLocalizacionSeleccionada)) {
                        listVLModificar.add(vigenciaLocalizacionSeleccionada);
                    }
                }

                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigencia = true;
                permitirIndex = true;
                context.update("form:datosVLEmpleado");
            } else if (tipoActualizacion == 2) {// Para duplicar registro
                duplicarVL.setProyecto(proyectoSelecionado);
                context.update("formularioDialogos:duplicarVL");
            }
            filtradoProyectos = null;
            proyectoSelecionado = new Proyectos();
            aceptar = true;
            tipoActualizacion = -1;
            context.reset("form:lovProyectos:globalFilter");
            context.execute("lovProyectos.clearFilters()");
            context.execute("ProyectosDialogo.hide()");
        }
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioProyecto() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProyectos:globalFilter");
        context.execute("lovProyectos.clearFilters()");
        context.execute("ProyectosDialogo.hide()");
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Metodo que actualiza el centro costo seleccionado (vigencia prorrateo)
     */
    public void actualizarCentroCostoVP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            vigenciaProrrateoSeleccionada.setCentrocosto(centroCostoSeleccionado);
            if (!listVPCrear.contains(vigenciaProrrateoSeleccionada)) {
                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(vigenciaProrrateoSeleccionada);
                } else if (!listVPModificar.contains(vigenciaProrrateoSeleccionada)) {
                    listVPModificar.add(vigenciaProrrateoSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaP = true;
            context.update("form:datosVPVigencia");
            permitirIndexVP = true;
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigenciaP.setCentrocosto(centroCostoSeleccionado);
            context.update("formularioDialogos:nuevaVP");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVP.setCentrocosto(centroCostoSeleccionado);
            context.update("formularioDialogos:duplicarVP");
        }
        filtradoCentroCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        aceptar = true;
        tipoActualizacion = -1;

        context.reset("form:lovCentroCostos:globalFilter");
        context.execute("lovCentroCostos.clearFilters()");
        context.execute("CentroCostosDialogo.hide()");
    }

    /**
     * Cancela la seleccion del centro costo (vigencia prorrateo)
     */
    public void cancelarCambioCentroCostoVP() {
        filtradoCentroCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovCentroCostos:globalFilter");
        context.execute("lovCentroCostos.clearFilters()");
        context.execute("CentroCostosDialogo.hide()");
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Actualiza el proyeecto seleccionado (vigencia prorrateo)
     */
//    public void actualizarProyectoVP() {
//        RequestContext context = RequestContext.getCurrentInstance();
//        
//    }
    /**
     * Cancela el proyecto seleccionado (vigencia prorrateo)
     */
    public void cancelarCambioProyectoVP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProyectos:globalFilter");
        context.execute("lovProyectos.clearFilters()");
        context.execute("ProyectosDialogo.hide()");
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Actualiza el proyecto seleccionado (vigencia prorrateo proyecto)
     */
//    public void actualizarProyectoVPP() {
//        RequestContext context = RequestContext.getCurrentInstance();
//        
//    }
    /**
     * Cancela la seleccion del proyecto (vigencioa prorrateo proyecto)
     */
    public void cancelarCambioProyectoVPP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexVPP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProyectos:globalFilter");
        context.execute("lovProyectos.clearFilters()");
        context.execute("ProyectosDialogo.hide()");
    }
    //LISTA DE VALORES DINAMICA

    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (vigenciaLocalizacionSeleccionada == null && vigenciaProrrateoSeleccionada == null && vigenciaProrrateoProyectoSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            tipoActualizacion = 0;

            if (vigenciaProrrateoSeleccionada != null) {
                if (cualCeldaVP == 0) {
                    contarRegistrosCentroC();
                    context.update("form:CentroCostosDialogo");
                    context.execute("CentroCostosDialogo.show()");
                }
                if (cualCeldaVP == 4) {
                    contarRegistrosProyecto();
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                }
            } else if (vigenciaProrrateoProyectoSeleccionada != null) {
                if (cualCeldaVPP == 0) {
                    contarRegistrosProyecto();
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                }
            } else if (vigenciaLocalizacionSeleccionada != null) {
                if (cualCelda == 1) {
                    //Estructura
                    contarRegistrosEstrLoca();
                    context.update("form:LocalizacionDialogo");
                    context.execute("LocalizacionDialogo.show()");
                }
                if (cualCelda == 3) {
                    //Motivos
                    contarRegistrosMotiLoc();
                    context.update("form:MotivoDialogo");
                    context.execute("MotivoDialogo.show()");
                }
                if (cualCelda == 4) {
                    //Proyecto
                    contarRegistrosProyecto();
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                }
            }
        }
    }

    /**
     * Valida un proceso de nuevo registro dentro de la pagina con respecto a la
     * posicion en la pagina
     */
    public void validarNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            context.execute("confirmarGuardarSinSalir.show()");
        } else {
            //Dialogo de nuevo registro multiple
            context.update("form:NuevoRegistroPagina");
            context.execute("NuevoRegistroPagina.show()");
        }
    }

    public void validarCualTabla(int tabla) {
        cualTabla = tabla;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tabla == 1) {
            //Dialogo de nuevo registro vigencias localizaciones
            context.update("form:NuevoRegistroVL");
            context.execute("NuevoRegistroVL.show()");
        }
        if (tabla == 2) {
            if (vigenciaLocalizacionSeleccionada != null) {
                nuevaVigenciaP = new VigenciasProrrateos();
                nuevaVigenciaP.setCentrocosto(new CentrosCostos());
                nuevaVigenciaP.setProyecto(new Proyectos());
                //Dialogo de nuevo registro vigencia prorrateo
                context.update("form:NuevoRegistroVP");
                context.execute("NuevoRegistroVP.show()");
                tipoActualizacion = 1;
            } else {
                context.execute("seleccionarRegistroLE.show()");
            }
        }
        if (tabla == 3) {
            if (vigenciaLocalizacionSeleccionada != null) {
                nuevaVigenciaPP = new VigenciasProrrateosProyectos();
                nuevaVigenciaPP.setProyecto(new Proyectos());
                //Dialogo de nuevo registro vigencia prorrateo proyecto
                context.update("form:NuevoRegistroVPP");
                context.execute("NuevoRegistroVPP.show()");
                tipoActualizacion = 1;
            } else {
                context.execute("seleccionarRegistroLE.show()");
            }
        }
    }

    /**
     * Valida un registro VigenciaProrrateoProyecto con respecto a la existencia
     * de VigenciaProrrateo en el registro
     */
    public void validarRegistroVPP() {
        int tam = 0;
        if (vigenciasProrrateosCentroC != null) {
            tam = vigenciasProrrateosCentroC.size();
        }
        if (tam > 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:NuevoRegistroVPP");
            context.execute("NuevoRegistroVPP.show()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:negacionNuevoRegistroVPP");
            context.execute("negacionNuevoRegistroVPP.show()");
        }
    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Selecciona la tabla a exportar XML con respecto al index activo
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (vigenciaLocalizacionSeleccionada != null) {
            nombreTabla = ":formExportarVL:datosVLEmpleadoExportar";
            nombreXML = "VigenciasLocalizacionesXML";
        }
        if (vigenciaProrrateoSeleccionada != null) {
            nombreTabla = ":formExportarVP:datosVPVigenciaExportar";
            nombreXML = "VigenciasProrrateoXML";
        }
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            nombreTabla = ":formExportarVPP:datosVPPVigenciaExportar";
            nombreXML = "VigenciasProrrateosProyectosXML";
        }
        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (vigenciaLocalizacionSeleccionada != null) {
            exportPDFVL();
        }
        if (vigenciaProrrateoSeleccionada != null) {
            exportPDFVP();
        }
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            exportPDFVPP();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVL() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVL:datosVLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tablaEx, "VigenciasLocalizacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVP() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tablaEx, "VigenciasProrrateosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVPP() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVPP:datosVPPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tablaEx, "VigenciasProrrateosProyectosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (vigenciaLocalizacionSeleccionada != null) {
            exportXLSVL();
        }
        if (vigenciaProrrateoSeleccionada != null) {
            exportXLSVP();
        }
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            exportXLSVPP();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVL() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVL:datosVLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tablaEx, "VigenciasLocalizacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVP() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tablaEx, "VigenciasProrrateosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVPP() throws IOException {
        DataTable tablaEx = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVPP:datosVPPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tablaEx, "VigenciasProrrateosProyectosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void verificarRastroTabla() {
        //Si no hay registros eleccionado
        if (vigenciaLocalizacionSeleccionada == null && vigenciaProrrateoSeleccionada == null && vigenciaProrrateoProyectoSeleccionada == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        } else if (vigenciaProrrateoSeleccionada != null) {
            //Metodo Rastro Vigencias Afiliaciones
            verificarRastroVigenciaProrrateoCC();
        } else if (vigenciaProrrateoProyectoSeleccionada != null) {
            //Metodo Rastro Vigencias Afiliaciones
            verificarRastroVigenciaProrrateoProyecto();
        } else if (vigenciaLocalizacionSeleccionada != null) {
            verificarRastroVigenciaLocalizacion();
        }
    }

    public void verificarRastroVigenciaLocalizacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaLocalizacionSeleccionada.getSecuencia(), "VIGENCIASLOCALIZACIONES");
        backUp = vigenciaLocalizacionSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasLocalizaciones";
            msnConfirmarRastro = "La tabla VIGENCIASLOCALIZACIONES tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaLocalizacionHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASLOCALIZACIONES")) {
            nombreTablaRastro = "VigenciasLocalizaciones";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASLOCALIZACIONES tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciaProrrateoCC() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaProrrateoSeleccionada.getSecuencia(), "VIGENCIASPRORRATEOS");
        backUp = vigenciaProrrateoSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasProrrateos";
            msnConfirmarRastro = "La tabla VIGENCIASPRORRATEOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaProrrateoCCHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASPRORRATEOS")) {
            nombreTablaRastro = "VigenciasProrrateos";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASPRORRATEOS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroVigenciaProrrateoProyecto() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(vigenciaProrrateoProyectoSeleccionada.getSecuencia(), "VIGENCIASPRORRATEOSPROYECTOS");
        backUp = vigenciaProrrateoProyectoSeleccionada.getSecuencia();
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "VigenciasProrrateosProyectos";
            msnConfirmarRastro = "La tabla VIGENCIASPRORRATEOSPROYECTOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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

    public void verificarRastroVigenciaProrrateoProyectoHist() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("VIGENCIASPRORRATEOSPROYECTOS")) {
            nombreTablaRastro = "VigenciasProrrateosProyectos";
            msnConfirmarRastroHistorico = "La tabla VIGENCIASPRORRATEOSPROYECTOS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrarVL() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        vigenciaLocalizacionSeleccionada = null;
        modificarInfoRegistroVL(filtradoVL.size());
        RequestContext.getCurrentInstance().update("form:informacionRegistroVL");
    }

    public void eventoFiltrarVP() {
        if (tipoListaVP == 0) {
            tipoListaVP = 1;
        }
        vigenciaProrrateoSeleccionada = null;
        modificarInfoRegistroVP(filtradoVP.size());
        RequestContext.getCurrentInstance().update("form:informacionRegistroVP");
    }

    public void eventoFiltrarVPP() {
        if (tipoListaVPP == 0) {
            tipoListaVPP = 1;
        }
        vigenciaProrrateoProyectoSeleccionada = null;
        modificarInfoRegistroVPP(filtradoPP.size());
        RequestContext.getCurrentInstance().update("form:informacionRegistroVPP");
    }

    public void eventoFiltrarCentroC() {
        modificarInfoRegistroCentroC(filtradoCentroCostos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroCentroCosto");
    }

    public void eventoFiltrarEstrLoca() {
        modificarInfoRegistroEstrLoca(filtradoEstructuraCC.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroLocalizacion");
    }

    public void eventoFiltrarMotiLoc() {
        modificarInfoRegistroMotiLoc(filtradoMotivosLocalizaciones.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroMotivoLocalizacion");
    }

    public void eventoFiltrarProyecto() {
        modificarInfoRegistroProyecto(filtradoProyectos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroProyecto");
    }

    private void modificarInfoRegistroVL(int valor) {
        infoRegistroVL = String.valueOf(valor);
    }

    private void modificarInfoRegistroVP(int valor) {
        infoRegistroVP = String.valueOf(valor);
    }

    private void modificarInfoRegistroVPP(int valor) {
        infoRegistroVPP = String.valueOf(valor);
    }

    private void modificarInfoRegistroCentroC(int valor) {
        infoRegistroCentroCosto = String.valueOf(valor);
    }

    private void modificarInfoRegistroEstrLoca(int valor) {
        infoRegistroEstLocalizacion = String.valueOf(valor);
    }

    private void modificarInfoRegistroMotiLoc(int valor) {
        infoRegistroMotivoLocalizacion = String.valueOf(valor);
    }

    private void modificarInfoRegistroProyecto(int valor) {
        infoRegistroProyecto = String.valueOf(valor);
    }

    public void contarRegistrosVL() {
        if (vigenciaLocalizaciones != null) {
            modificarInfoRegistroVL(vigenciaLocalizaciones.size());
        } else {
            modificarInfoRegistroVL(0);
        }
    }

    public void contarRegistrosVP() {
        if (vigenciasProrrateosCentroC != null) {
            modificarInfoRegistroVP(vigenciasProrrateosCentroC.size());
        } else {
            modificarInfoRegistroVP(0);
        }
    }

    public void contarRegistrosVPP() {
        if (vigenciasProrrateosProyectos != null) {
            modificarInfoRegistroVPP(vigenciasProrrateosProyectos.size());
        } else {
            modificarInfoRegistroVPP(0);
        }
    }

    public void contarRegistrosCentroC() {
        if (listCentrosCostos != null) {
            modificarInfoRegistroCentroC(listCentrosCostos.size());
        } else {
            modificarInfoRegistroCentroC(0);
        }
    }

    public void contarRegistrosEstrLoca() {
        if (listEstructurasCC != null) {
            modificarInfoRegistroEstrLoca(listEstructurasCC.size());
        } else {
            modificarInfoRegistroEstrLoca(0);
        }
    }

    public void contarRegistrosMotiLoc() {
        if (listMotivosLocalizaciones != null) {
            modificarInfoRegistroMotiLoc(listMotivosLocalizaciones.size());
        } else {
            modificarInfoRegistroMotiLoc(0);
        }
    }

    public void contarRegistrosProyecto() {
        if (listProyectos != null) {
            modificarInfoRegistroProyecto(listProyectos.size());
        } else {
            modificarInfoRegistroProyecto(0);
        }
    }

    public void recordarSeleccionVL() {
        if (vigenciaLocalizacionSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVLEmpleado");
            tabla.setSelection(vigenciaLocalizacionSeleccionada);
        }
    }

    public void recordarSeleccionVP() {
        if (vigenciaProrrateoSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVPVigencia");
            tabla.setSelection(vigenciaProrrateoSeleccionada);
        }
    }

    public void recordarSeleccionVPP() {
        if (vigenciaProrrateoProyectoSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVPPVigencia");
            tabla.setSelection(vigenciaProrrateoProyectoSeleccionada);
        }
    }

    public void restablecerTablaVL() {
        FacesContext c = FacesContext.getCurrentInstance();
        vlFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
        vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
        vlCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
        vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
        vlLocalizacion = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
        vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
        vlMotivo = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
        vlMotivo.setFilterStyle("display: none; visibility: hidden;");
        vlProyecto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
        vlProyecto.setFilterStyle("display: none; visibility: hidden;");
        altoTabla1 = "115";
        bandera = 0;
        filtradoVL = null;
        tipoLista = 0;
    }

    public void restablecerTablaVP() {
        FacesContext c = FacesContext.getCurrentInstance();
        vPCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
        vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
        vPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
        vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
        vPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
        vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
        vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        vPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
        vPProyecto.setFilterStyle("display: none; visibility: hidden;");
        vPSubPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
        vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
        altoTabla2 = "115";
        banderaVP = 0;
        filtradoVP = null;
        tipoListaVP = 0;
    }

    public void restablecerTablaVPP() {
        FacesContext c = FacesContext.getCurrentInstance();
        vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
        vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
        vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
        vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
        vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
        vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
        vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
        vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
        altoTabla3 = "115";
        banderaVPP = 0;
        filtradoPP = null;
        tipoListaVPP = 0;
    }

    public List<VigenciasLocalizaciones> getVigenciaLocalizaciones() {
        if (vigenciaLocalizaciones == null) {
            if (empleado != null) {
                vigenciaLocalizaciones = new ArrayList<VigenciasLocalizaciones>();
                vigenciaLocalizaciones = administrarVigenciaLocalizacion.VigenciasLocalizacionesEmpleado(empleado.getSecuencia());
                if (vigenciaLocalizaciones != null) {
                    if (!vigenciaLocalizaciones.isEmpty()) {
                        for (int i = 0; i < vigenciaLocalizaciones.size(); i++) {
                            if (vigenciaLocalizaciones.get(i).getProyecto() == null) {
                                vigenciaLocalizaciones.get(i).setProyecto(new Proyectos());
                            }
                        }
                        /*
                         * if (vigenciaLocalizacionSeleccionada == null) {
                         * vigenciaLocalizacionSeleccionada =
                         * vigenciaLocalizaciones.get(0); }
                         */
                    }
                }
            }
        }
        return vigenciaLocalizaciones;
    }

    public void setVigenciaLocalizaciones(List<VigenciasLocalizaciones> vigenciaLocalizaciones) {
        this.vigenciaLocalizaciones = vigenciaLocalizaciones;
    }

    public List<VigenciasLocalizaciones> getFiltradoVL() {
        try {
            return filtradoVL;
        } catch (Exception e) {
            System.out.println("Error getFiltrarVL : " + e.toString());
            return null;
        }
    }

    public void setFiltradoVL(List<VigenciasLocalizaciones> filtrarVL) {
        try {
            this.filtradoVL = filtrarVL;
        } catch (Exception e) {
            System.out.println("Se estallo: " + e);
        }
    }

    public List<Estructuras> getListEstructurasCC() {
        if (listEstructurasCC == null) {
            listEstructurasCC = administrarVigenciaLocalizacion.estructuras();
        }
        return listEstructurasCC;
    }

    public void setListEstructurasCC(List<Estructuras> estructuras) {
        this.listEstructurasCC = estructuras;
    }

    public Estructuras getEstructuraSelecionada() {
        return estructuraSelecionada;
    }

    public void setEstructuraSelecionada(Estructuras estructuraSelecionada) {
        this.estructuraSelecionada = estructuraSelecionada;
    }

    public List<Estructuras> getFiltradoEstructura() {
        return filtradoEstructuraCC;
    }

    public void setFiltradoEstructura(List<Estructuras> filtradoEstructura) {
        this.filtradoEstructuraCC = filtradoEstructura;
    }

    public List<MotivosLocalizaciones> getListMotivosLocalizaciones() {
        if (listMotivosLocalizaciones == null) {
            listMotivosLocalizaciones = administrarVigenciaLocalizacion.motivosLocalizaciones();
        }
        return listMotivosLocalizaciones;
    }

    public void setListMotivosLocalizaciones(List<MotivosLocalizaciones> motivosLocalizaciones) {
        this.listMotivosLocalizaciones = motivosLocalizaciones;
    }

    public MotivosLocalizaciones getMotivoLocalizacionSelecionado() {
        return motivoLocalizacionSelecionado;
    }

    public void setMotivoLocalizacionSelecionado(MotivosLocalizaciones motivoLocalizacionSelecionado) {
        this.motivoLocalizacionSelecionado = motivoLocalizacionSelecionado;
    }

    public List<MotivosLocalizaciones> getFiltradoMotivosLocalizaciones() {
        return filtradoMotivosLocalizaciones;
    }

    public void setFiltradoMotivosLocalizaciones(List<MotivosLocalizaciones> filtradoMotivosLocalizaciones) {
        this.filtradoMotivosLocalizaciones = filtradoMotivosLocalizaciones;
    }

    public List<Proyectos> getListProyectos() {
        if (listProyectos == null) {
            listProyectos = administrarVigenciaLocalizacion.proyectos();
        }
        return listProyectos;
    }

    public void setListProyectos(List<Proyectos> listProyectos) {
        this.listProyectos = listProyectos;
    }

    public Proyectos getProyectoSelecionado() {
        return proyectoSelecionado;
    }

    public void setProyectoSelecionado(Proyectos proyectoSelecionado) {
        this.proyectoSelecionado = proyectoSelecionado;
    }

    public List<Proyectos> getFiltradoProyectos() {
        return filtradoProyectos;
    }

    public void setFiltradoProyectos(List<Proyectos> filtradoProyectos) {
        this.filtradoProyectos = filtradoProyectos;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<VigenciasLocalizaciones> getListVLModificar() {
        return listVLModificar;
    }

    public void setListVLModificar(List<VigenciasLocalizaciones> listVLModificar) {
        this.listVLModificar = listVLModificar;
    }

    public VigenciasLocalizaciones getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasLocalizaciones nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public List<VigenciasLocalizaciones> getListVLCrear() {
        return listVLCrear;
    }

    public void setListVLCrear(List<VigenciasLocalizaciones> listVLCrear) {
        this.listVLCrear = listVLCrear;
    }

    public List<VigenciasLocalizaciones> getListVLBorrar() {
        return listVLBorrar;
    }

    public void setListVLBorrar(List<VigenciasLocalizaciones> listVLBorrar) {
        this.listVLBorrar = listVLBorrar;
    }

    public VigenciasLocalizaciones getEditarVL() {
        return editarVL;
    }

    public void setEditarVL(VigenciasLocalizaciones editarVL) {
        this.editarVL = editarVL;
    }

    public VigenciasLocalizaciones getDuplicarVL() {
        return duplicarVL;
    }

    public void setDuplicarVL(VigenciasLocalizaciones duplicarVL) {
        this.duplicarVL = duplicarVL;
    }

    public List<VigenciasProrrateos> getVigenciasProrrateosCentroC() {
        try {
            if (vigenciaLocalizacionSeleccionada != null) {
                vigenciasProrrateosCentroC = administrarVigenciaLocalizacion.VigenciasProrrateosVigencia(vigenciaLocalizacionSeleccionada.getSecuencia());
                if (vigenciasProrrateosCentroC != null) {
                    for (int i = 0; i < vigenciasProrrateosCentroC.size(); i++) {
                        if (vigenciasProrrateosCentroC.get(i).getProyecto() == null) {
                            vigenciasProrrateosCentroC.get(i).setProyecto(new Proyectos());
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Error cargarVigenciasProrrateos : " + e.toString());
            return null;
        }
        return vigenciasProrrateosCentroC;
    }

    public void setVigenciasProrrateosVigencia(List<VigenciasProrrateos> vigenciasProrrateosVigencia) {
        this.vigenciasProrrateosCentroC = vigenciasProrrateosVigencia;
    }

    public List<VigenciasProrrateos> getFiltradoVP() {
        return filtradoVP;
    }

    public void setFiltradoVP(List<VigenciasProrrateos> filtradoVigenciasProrrateosVigencia) {
        this.filtradoVP = filtradoVigenciasProrrateosVigencia;
    }

    public List<VigenciasProrrateosProyectos> getVigenciasProrrateosProyectos() {
        try {
            if (vigenciaLocalizacionSeleccionada != null) {
                if (vigenciasProrrateosProyectos == null) {
                    vigenciasProrrateosProyectos = administrarVigenciaLocalizacion.VigenciasProrrateosProyectosVigencia(vigenciaLocalizacionSeleccionada.getSecuencia());
                }
            }
        } catch (Exception e) {
            System.out.println("Error vigenciasProrrateosProyectosVigencia");
            return null;
        }
        return vigenciasProrrateosProyectos;
    }

    public void setVigenciasProrrateosProyectos(List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectosVigencia) {
        this.vigenciasProrrateosProyectos = vigenciasProrrateosProyectosVigencia;
    }

    public List<VigenciasProrrateosProyectos> getFiltradoPP() {
        return filtradoPP;
    }

    public void setFiltradoPP(List<VigenciasProrrateosProyectos> filtradoVigenciasProrrateosProyectosVigencia) {
        this.filtradoPP = filtradoVigenciasProrrateosProyectosVigencia;
    }

    public VigenciasProrrateos getEditarVP() {
        return editarVP;
    }

    public void setEditarVP(VigenciasProrrateos editarVP) {
        this.editarVP = editarVP;
    }

    public VigenciasProrrateosProyectos getEditarVPP() {
        return editarVPP;
    }

    public void setEditarVPP(VigenciasProrrateosProyectos editarVPP) {
        this.editarVPP = editarVPP;
    }

    public List<CentrosCostos> getListCentrosCostos() {
        if (listCentrosCostos == null) {
            listCentrosCostos = administrarVigenciaLocalizacion.centrosCostos();
        }
        return listCentrosCostos;
    }

    public void setListCentrosCostos(List<CentrosCostos> centrosCostos) {
        this.listCentrosCostos = centrosCostos;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos centroCostoSeleccionado) {
        this.centroCostoSeleccionado = centroCostoSeleccionado;
    }

    public List<CentrosCostos> getFiltradoCentroCostos() {
        return filtradoCentroCostos;
    }

    public void setFiltradoCentroCostos(List<CentrosCostos> filtradoCentroCostos) {
        this.filtradoCentroCostos = filtradoCentroCostos;
    }

    public VigenciasProrrateos getNuevaVigenciaP() {
        return nuevaVigenciaP;
    }

    public void setNuevaVigenciaP(VigenciasProrrateos nuevaVigenciaP) {
        this.nuevaVigenciaP = nuevaVigenciaP;
    }

    public VigenciasProrrateosProyectos getNuevaVigenciaPP() {
        return nuevaVigenciaPP;
    }

    public void setNuevaVigenciaPP(VigenciasProrrateosProyectos nuevaVigenciaPP) {
        this.nuevaVigenciaPP = nuevaVigenciaPP;
    }

    public VigenciasProrrateos getDuplicarVP() {
        return duplicarVP;
    }

    public void setDuplicarVP(VigenciasProrrateos duplicarVP) {
        this.duplicarVP = duplicarVP;
    }

    public VigenciasProrrateosProyectos getDuplicarVPP() {
        return duplicarVPP;
    }

    public void setDuplicarVPP(VigenciasProrrateosProyectos duplicarVPP) {
        this.duplicarVPP = duplicarVPP;
    }

    public List<VigenciasProrrateos> getListVPCrear() {
        return listVPCrear;
    }

    public void setListVPCrear(List<VigenciasProrrateos> listVPCrear) {
        this.listVPCrear = listVPCrear;
    }

    public List<VigenciasProrrateosProyectos> getListVPPCrear() {
        return listVPPCrear;
    }

    public void setListVPPCrear(List<VigenciasProrrateosProyectos> listVPPCrear) {
        this.listVPPCrear = listVPPCrear;
    }

    public List<VigenciasProrrateos> getListVPModificar() {
        return listVPModificar;
    }

    public void setListVPModificar(List<VigenciasProrrateos> listVPModificar) {
        this.listVPModificar = listVPModificar;
    }

    public List<VigenciasProrrateosProyectos> getListVPPModificar() {
        return listVPPModificar;
    }

    public void setListVPPModificar(List<VigenciasProrrateosProyectos> listVPPModificar) {
        this.listVPPModificar = listVPPModificar;
    }

    public List<VigenciasProrrateos> getListVPBorrar() {
        return listVPBorrar;
    }

    public void setListVPBorrar(List<VigenciasProrrateos> listVPBorrar) {
        this.listVPBorrar = listVPBorrar;
    }

    public List<VigenciasProrrateosProyectos> getListVPPBorrar() {
        return listVPPBorrar;
    }

    public void setListVPPBorrar(List<VigenciasProrrateosProyectos> listVPPBorrar) {
        this.listVPPBorrar = listVPPBorrar;
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

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getAltoTabla1() {
        return altoTabla1;
    }

    public String getAltoTabla2() {
        return altoTabla2;
    }

    public String getAltoTabla3() {
        return altoTabla3;
    }

    public VigenciasLocalizaciones getVigenciaLocalizacionSeleccionada() {
        return vigenciaLocalizacionSeleccionada;
    }

    public void setVigenciaLocalizacionSeleccionada(VigenciasLocalizaciones vigenciaLocalizacionSeleccionada) {
        this.vigenciaLocalizacionSeleccionada = vigenciaLocalizacionSeleccionada;
    }

    public VigenciasProrrateos getVigenciaProrrateoSeleccionada() {
        return vigenciaProrrateoSeleccionada;
    }

    public void setVigenciaProrrateoSeleccionada(VigenciasProrrateos vigenciaProrrateoSeleccionada) {
        this.vigenciaProrrateoSeleccionada = vigenciaProrrateoSeleccionada;
    }

    public VigenciasProrrateosProyectos getVigenciaProrrateoProyectoSeleccionada() {
        return vigenciaProrrateoProyectoSeleccionada;
    }

    public void setVigenciaProrrateoProyectoSeleccionada(VigenciasProrrateosProyectos vigenciaProrrateoProyectoSeleccionada) {
        this.vigenciaProrrateoProyectoSeleccionada = vigenciaProrrateoProyectoSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getInfoRegistroEstLocalizacion() {
        return infoRegistroEstLocalizacion;
    }

    public String getInfoRegistroMotivoLocalizacion() {
        return infoRegistroMotivoLocalizacion;
    }

    public String getInfoRegistroProyecto() {
        return infoRegistroProyecto;
    }

    public String getInfoRegistroCentroCosto() {
        return infoRegistroCentroCosto;
    }

    public String getInfoRegistroVP() {
        return infoRegistroVP;
    }

    public String getInfoRegistroVPP() {
        return infoRegistroVPP;
    }

    public String getInfoRegistroVL() {
        return infoRegistroVL;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
}

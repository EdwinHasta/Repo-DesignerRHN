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
    private List<VigenciasLocalizaciones> filtrarVL;
    private VigenciasLocalizaciones vigenciaLocalizacionSeleccionada;
    //Vigencias Prorrateos
    private List<VigenciasProrrateos> vigenciasProrrateosCentroC;
    private List<VigenciasProrrateos> filtradoVigenciasProrrateosCentroC;
    private VigenciasProrrateos vigenciaProrrateoSeleccionada;
    // Vigencia Prorrateos Proyectos
    private List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectos;
    private List<VigenciasProrrateosProyectos> filtradoVigenciasProrrateosProyectos;
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
    private int index;
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
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexVP, indexVPP;
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
    private BigInteger secRegistroVL;
    private BigInteger backUpSecRegistroVL;
    private BigInteger secRegistroVP;
    private BigInteger backUpSecRegistroVP;
    private BigInteger secRegistroVPP;
    private BigInteger backUpSecRegistroVPP;
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
    private String infoRegistroProyectoVP;
    private String infoRegistroProyectoVPP;
    //Validaciones
    private String mensajeValidacion;
    private DataTable tabla;

    public ControlVigenciaLocalizacion() {

        secRegistroVPP = null;
        backUpSecRegistroVPP = null;
        backUpSecRegistroVL = null;
        secRegistroVP = null;
        backUpSecRegistroVP = null;
        secRegistroVL = null;
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
        listVLCrear = new ArrayList<VigenciasLocalizaciones>();
        paraNuevaV = 0;
        //modificar aficiones
        listVLModificar = new ArrayList<VigenciasLocalizaciones>();
        listVPModificar = new ArrayList<VigenciasProrrateos>();
        listVPPModificar = new ArrayList<VigenciasProrrateosProyectos>();
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
        index = -1;
        indexVP = -1;
        indexVPP = -1;
        cualCeldaVP = -1;
        cualCeldaVPP = -1;


        listVPCrear = new ArrayList<VigenciasProrrateos>();
        listVPPCrear = new ArrayList<VigenciasProrrateosProyectos>();

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
        vigenciaLocalizaciones = null;
        empleado = empl;
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVL(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {// Si NO tiene Filtro
            if (!listVLCrear.contains(vigenciaLocalizaciones.get(indice))) {

                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(vigenciaLocalizaciones.get(indice));
                } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(indice))) {
                    listVLModificar.add(vigenciaLocalizaciones.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambiosVigencia = true;
            //index = -1;
            //secRegistroVL = null;   
        } else {// Si tiene Filtro
            if (!listVLCrear.contains(filtrarVL.get(indice))) {

                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(filtrarVL.get(indice));
                } else if (!listVLModificar.contains(filtrarVL.get(indice))) {
                    listVLModificar.add(filtrarVL.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambiosVigencia = true;
            //index = -1;
            //secRegistroVL = null;
        }
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasLocalizaciones auxiliar = null;
            if (tipoLista == 0) {// Si NO tiene Filtro
                auxiliar = vigenciaLocalizaciones.get(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                auxiliar = filtrarVL.get(index);
            }
            if (auxiliar.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaVigencia.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarVL.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        VigenciasLocalizaciones auxiliar = null;
        if (tipoLista == 0) {// Si NO tiene Filtro
            auxiliar = vigenciaLocalizaciones.get(index);
        }
        if (tipoLista == 1) {// Si tiene Filtro
            auxiliar = filtrarVL.get(index);
        }
        if (auxiliar.getFechavigencia() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistro(0);
            if (retorno) {
                cambiarIndice(i, c);
                modificarVL(i);
            } else {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaLocalizaciones.get(i).setFechavigencia(fechaVigencia);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    filtrarVL.get(i).setFechavigencia(fechaVigencia);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVLEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(i).setFechavigencia(fechaVigencia);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                filtrarVL.get(i).setFechavigencia(fechaVigencia);
            }
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
    public void modificarVL(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(indice).getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            } else {// Si tiene Filtro
                filtrarVL.get(indice).getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            }
            for (int i = 0; i < listEstructurasCC.size(); i++) {
                if (listEstructurasCC.get(i).getCentrocosto().getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaLocalizaciones.get(indice).setLocalizacion(listEstructurasCC.get(indiceUnicoElemento));
                } else {// Si tiene Filtro
                    filtrarVL.get(indice).setLocalizacion(listEstructurasCC.get(indiceUnicoElemento));
                }
                listEstructurasCC.clear();
                getListEstructurasCC();
            } else {
                permitirIndex = false;
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOLOCALIZACION")) {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(indice).getMotivo().setDescripcion(motivoLocalizacion);
            } else {// Si tiene Filtro
                filtrarVL.get(indice).getMotivo().setDescripcion(motivoLocalizacion);
            }
            for (int i = 0; i < listMotivosLocalizaciones.size(); i++) {
                if (listMotivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaLocalizaciones.get(indice).setMotivo(listMotivosLocalizaciones.get(indiceUnicoElemento));
                } else {// Si tiene Filtro
                    filtrarVL.get(indice).setMotivo(listMotivosLocalizaciones.get(indiceUnicoElemento));
                }
                listMotivosLocalizaciones.clear();
                getListMotivosLocalizaciones();
            } else {
                permitirIndex = false;
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaLocalizaciones.get(indice).getProyecto().setNombreproyecto(proyecto);
                } else {// Si tiene Filtro
                    filtrarVL.get(indice).getProyecto().setNombreproyecto(proyecto);
                }
                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        vigenciaLocalizaciones.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                    } else {// Si tiene Filtro
                        filtrarVL.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                    }
                    listProyectos.clear();
                    getListProyectos();
                } else {
                    permitirIndex = false;
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                listProyectos.clear();
                getListProyectos();
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaLocalizaciones.get(indice).setProyecto(new Proyectos());
                } else {// Si tiene Filtro
                    filtrarVL.get(indice).setProyecto(new Proyectos());
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosVigencia = true;
                //index = -1;
                //secRegistroVL = null;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {// Si NO tiene Filtro
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(indice))) {

                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(indice));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(indice))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambiosVigencia = true;
                //index = -1;
                //secRegistroVL = null;
            } else {// Si tiene Filtro
                if (!listVLCrear.contains(filtrarVL.get(indice))) {

                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(indice));
                    } else if (!listVLModificar.contains(filtrarVL.get(indice))) {
                        listVLModificar.add(filtrarVL.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambiosVigencia = true;
                //index = -1;
                //secRegistroVL = null;
            }
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
                    vigenciasProrrateosCentroC.get(indice).setSubporcentaje(porcentajePrueba.toBigInteger());
                } else {
                    vigenciasProrrateosCentroC.get(indice).setPorcentaje(porcentajePrueba);
                }
                modificarVP(indice);
                if (porcentajePrueba.intValueExact() < 0 || porcentajePrueba.intValueExact() > 100) {
                    context.update("form:validarPorcentajes");
                    context.execute("validarPorcentajes.show()");
                }
            }
            if (nTabla == 2) {
                int porcentajePrueba = Integer.parseInt(valor);
                vigenciasProrrateosProyectos.get(indice).setPorcentaje(porcentajePrueba);
                modificarVP(indice);
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

    public void modificarVP(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaVP == 0) {// Si NO tiene Filtro
            if (!listVPCrear.contains(vigenciasProrrateosCentroC.get(indice))) {

                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(vigenciasProrrateosCentroC.get(indice));
                } else if (!listVPModificar.contains(vigenciasProrrateosCentroC.get(indice))) {
                    listVPModificar.add(vigenciasProrrateosCentroC.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaP = true;
            //indexVP = -1;
            //secRegistroVP = null;
        } else {// Si tiene Filtro
            if (!listVPCrear.contains(filtradoVigenciasProrrateosCentroC.get(indice))) {

                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indice));
                } else if (!listVPModificar.contains(filtradoVigenciasProrrateosCentroC.get(indice))) {
                    listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaP = true;
            //indexVP = -1;
            //secRegistroVP = null;
        }
    }

    public boolean validarFechasRegistroVigenciaProrrateo(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasProrrateos auxiliar = null;
            if (tipoLista == 0) {// Si NO tiene Filtro
                auxiliar = vigenciasProrrateosCentroC.get(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                auxiliar = filtradoVigenciasProrrateosCentroC.get(index);
            }
            if (auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
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
        if (i == 2) {
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

    public void modificarFechasVigenciasProrrateos(int i, int c) {
        VigenciasProrrateos auxiliar = null;
        if (tipoLista == 0) {// Si NO tiene Filtro
            auxiliar = vigenciasProrrateosCentroC.get(i);
        }
        if (tipoLista == 1) {// Si tiene Filtro
            auxiliar = filtradoVigenciasProrrateosCentroC.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaProrrateo(0);
            if (retorno) {
                cambiarIndiceVP(i, c);
                modificarVP(i);
            } else {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasProrrateosCentroC.get(i).setFechafinal(fechaFinVP);
                    vigenciasProrrateosCentroC.get(i).setFechainicial(fechaIniVP);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    filtradoVigenciasProrrateosCentroC.get(i).setFechafinal(fechaFinVP);
                    filtradoVigenciasProrrateosCentroC.get(i).setFechainicial(fechaIniVP);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasProrrateosCentroC.get(i).setFechainicial(fechaIniVP);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                filtradoVigenciasProrrateosCentroC.get(i).setFechainicial(fechaIniVP);
            }
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
    public void modificarVP(int indice, String confirmarCambio, String valorConfirmar) {
        indexVP = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                vigenciasProrrateosCentroC.get(indice).getCentrocosto().setNombre(centroCostosVP);
            } else {// Si tiene Filtro
                filtradoVigenciasProrrateosCentroC.get(indice).getCentrocosto().setNombre(centroCostosVP);
            }
            for (int i = 0; i < listCentrosCostos.size(); i++) {
                if (listCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVP == 0) {// Si NO tiene Filtro
                    cambioVigenciaP = true;
                    vigenciasProrrateosCentroC.get(indice).setCentrocosto(listCentrosCostos.get(indiceUnicoElemento));
                } else {// Si tiene Filtro
                    cambioVigenciaP = true;
                    filtradoVigenciasProrrateosCentroC.get(indice).setCentrocosto(listCentrosCostos.get(indiceUnicoElemento));
                }
                listCentrosCostos.clear();
                getListCentrosCostos();
            } else {
                permitirIndexVP = false;
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoListaVP == 0) {// Si NO tiene Filtro
                    vigenciasProrrateosCentroC.get(indice).getProyecto().setNombreproyecto(proyectoVP);
                } else {// Si tiene Filtro
                    filtradoVigenciasProrrateosCentroC.get(indice).getProyecto().setNombreproyecto(proyectoVP);
                }
                for (int i = 0; i < listProyectos.size(); i++) {
                    if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVP == 0) {// Si NO tiene Filtro
                        cambioVigenciaP = true;
                        vigenciasProrrateosCentroC.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                    } else {// Si tiene Filtro
                        cambioVigenciaP = true;
                        filtradoVigenciasProrrateosCentroC.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                    }
                    listProyectos.clear();
                    getListProyectos();
                } else {
                    permitirIndexVP = false;
                    context.update("form:ProyectosDialogoVP");
                    context.execute("ProyectosDialogoVP.show()");
                    tipoActualizacion = 0;
                }
            } else {
                listProyectos.clear();
                getListProyectos();
                if (tipoListaVP == 0) {// Si NO tiene Filtro
                    vigenciasProrrateosCentroC.get(indice).setProyecto(new Proyectos());
                } else {// Si tiene Filtro
                    filtradoVigenciasProrrateosCentroC.get(indice).setProyecto(new Proyectos());
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambioVigenciaP = true;
                //indexVP = -1;
                //secRegistroVP = null;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                if (!listVPCrear.contains(vigenciasProrrateosCentroC.get(indice))) {

                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indice));
                    } else if (!listVPModificar.contains(vigenciasProrrateosCentroC.get(indice))) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaP = true;
                //indexVP = -1;
                //secRegistroVP = null;
            } else {// Si tiene Filtro
                if (!listVPCrear.contains(filtradoVigenciasProrrateosCentroC.get(indice))) {

                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indice));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosCentroC.get(indice))) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaP = true;
                //indexVP = -1;
                //secRegistroVP = null;
            }
        }

        context.update(
                "form:datosVPVigencia");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateoProyectos que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVPP(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaVPP == 0) {// Si NO tiene Filtro
            if (!listVPPCrear.contains(vigenciasProrrateosProyectos.get(indice))) {

                if (listVPPModificar.isEmpty()) {
                    listVPPModificar.add(vigenciasProrrateosProyectos.get(indice));
                } else if (!listVPPModificar.contains(vigenciasProrrateosProyectos.get(indice))) {
                    listVPPModificar.add(vigenciasProrrateosProyectos.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaPP = true;
            //indexVPP = -1;
            //secRegistroVPP = null;
        } else {// Si tiene Filtro
            if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectos.get(indice))) {

                if (listVPPModificar.isEmpty()) {
                    listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indice));
                } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectos.get(indice))) {
                    listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indice));
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaPP = true;
            //indexVPP = -1;
            //secRegistroVPP = null;
        }
    }

    public boolean validarFechasRegistroVigenciaProrrateoProyecto(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasProrrateosProyectos auxiliar = null;
            if (tipoLista == 0) {// Si NO tiene Filtro
                auxiliar = vigenciasProrrateosProyectos.get(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                auxiliar = filtradoVigenciasProrrateosProyectos.get(index);
            }
            if (auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
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

    public void modificarFechasVigenciasProrrateosProyecto(int i, int c) {
        VigenciasProrrateosProyectos auxiliar = null;
        if (tipoLista == 0) {// Si NO tiene Filtro
            auxiliar = vigenciasProrrateosProyectos.get(i);
        }
        if (tipoLista == 1) {// Si tiene Filtro
            auxiliar = filtradoVigenciasProrrateosProyectos.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaProrrateoProyecto(0);
            if (retorno) {
                cambiarIndiceVPP(i, c);
                modificarVPP(i);
            } else {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciasProrrateosProyectos.get(i).setFechafinal(fechaFinVPP);
                    vigenciasProrrateosProyectos.get(i).setFechainicial(fechaIniVPP);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    filtradoVigenciasProrrateosProyectos.get(i).setFechafinal(fechaFinVPP);
                    filtradoVigenciasProrrateosProyectos.get(i).setFechainicial(fechaIniVPP);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciasProrrateosProyectos.get(i).setFechainicial(fechaIniVPP);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                filtradoVigenciasProrrateosProyectos.get(i).setFechainicial(fechaIniVPP);
            }
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
    public void modificarVPP(int indice, String confirmarCambio, String valorConfirmar) {
        indexVPP = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                vigenciasProrrateosProyectos.get(indice).getProyecto().setNombreproyecto(proyectoVPP);
            } else {// Si tiene Filtro
                filtradoVigenciasProrrateosProyectos.get(indice).getProyecto().setNombreproyecto(proyectoVPP);
            }
            for (int i = 0; i < listProyectos.size(); i++) {
                if (listProyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVPP == 0) {// Si NO tiene Filtro
                    cambioVigenciaPP = true;
                    vigenciasProrrateosProyectos.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                } else {// Si tiene Filtro
                    cambioVigenciaPP = true;
                    filtradoVigenciasProrrateosProyectos.get(indice).setProyecto(listProyectos.get(indiceUnicoElemento));
                }
                listProyectos.clear();
                getListProyectos();
            } else {
                permitirIndexVPP = false;
                context.update("form:ProyectosDialogoVPP");
                context.execute("ProyectosDialogoVPP.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                if (!listVPPCrear.contains(vigenciasProrrateosProyectos.get(indice))) {

                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(vigenciasProrrateosProyectos.get(indice));
                    } else if (!listVPPModificar.contains(vigenciasProrrateosProyectos.get(indice))) {
                        listVPPModificar.add(vigenciasProrrateosProyectos.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaPP = true;
                indexVPP = -1;
                secRegistroVPP = null;
            } else {// Si tiene Filtro
                if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectos.get(indice))) {

                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indice));
                    } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectos.get(indice))) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indice));
                    }
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaPP = true;
                indexVPP = -1;
                secRegistroVPP = null;
            }
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
                listEstructurasCC.clear();
                getListEstructurasCC();
            } else {
                getInfoRegistroEstLocalizacion();
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
                listMotivosLocalizaciones.clear();
                getListMotivosLocalizaciones();
            } else {
                getInfoRegistroMotivoLocalizacion();
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
                    listProyectos.clear();
                    getListProyectos();
                } else {
                    getInfoRegistroProyecto();
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
            listProyectos.clear();
            getListProyectos();
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
                listCentrosCostos.clear();
                getListCentrosCostos();
            } else {
                getInfoRegistroCentroCosto();
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
                    listProyectos.clear();
                    getListProyectos();
                } else {
                    getInfoRegistroProyectoVP();
                    context.update("form:ProyectosDialogoVP");
                    context.execute("ProyectosDialogoVP.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaProyectoVP");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicadoProyectoVP");
                    }
                }
            }
        } else {
            listProyectos.clear();
            getListProyectos();
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
                listProyectos.clear();
                getListProyectos();
            } else {
                getInfoRegistroProyectoVPP();
                context.update("form:ProyectosDialogoVPP");
                context.execute("ProyectosDialogoVPP.show()");
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
        cambiarIndice(indice, columna);
    }

    public void cambiarIndice(int indice, int celda) {
        FacesContext c = FacesContext.getCurrentInstance();
        if (permitirIndex) {
            if ((cambioVigenciaP == false) && (cambioVigenciaPP == false)) {
                cualCelda = celda;
                index = indice;
                indexAuxVL = indice;
                if (tipoLista == 0) {// Si NO tiene Filtro
                    secRegistroVL = vigenciaLocalizaciones.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        centroCosto = vigenciaLocalizaciones.get(index).getLocalizacion().getCentrocosto().getCodigoNombre();
                    } else if (cualCelda == 3) {
                        motivoLocalizacion = vigenciaLocalizaciones.get(index).getMotivo().getDescripcion();
                    } else if (cualCelda == 4) {
                        proyecto = vigenciaLocalizaciones.get(index).getProyecto().getNombreproyecto();
                    }
                } else {// Si tiene Filtro
                    secRegistroVL = filtrarVL.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        centroCosto = filtrarVL.get(index).getLocalizacion().getCentrocosto().getCodigoNombre();
                    } else if (cualCelda == 3) {
                        motivoLocalizacion = filtrarVL.get(index).getMotivo().getDescripcion();
                    } else if (cualCelda == 4) {
                        proyecto = filtrarVL.get(index).getProyecto().getNombreproyecto();
                    }
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
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosCentroC = null;
            tipoListaVP = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla3 = "115";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectos = null;
            tipoListaVPP = 0;
        }
        indexVP = -1;
        secRegistroVP = null;
        indexVPP = -1;
        secRegistroVPP = null;
        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
        RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVP(int indice, int celda) {
        FacesContext c = FacesContext.getCurrentInstance();
        if (permitirIndexVP) {
            indexVP = indice;
            cualCeldaVP = celda;
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                secRegistroVP = vigenciasProrrateosCentroC.get(indexVP).getSecuencia();
                if (cambioVigenciaPP == false) {
                    if (cualCeldaVP == 0) {
                        centroCostosVP = vigenciasProrrateosCentroC.get(indexVP).getCentrocosto().getNombre();
                    } else if (cualCelda == 4) {
                        proyectoVP = vigenciasProrrateosCentroC.get(indexVP).getProyecto().getNombreproyecto();
                    }
                }
            } else {// Si tiene Filtro
                secRegistroVP = filtradoVigenciasProrrateosCentroC.get(indexVP).getSecuencia();
                if (cambioVigenciaPP == false) {
                    if (cualCeldaVP == 0) {
                        centroCostosVP = filtradoVigenciasProrrateosCentroC.get(indexVP).getCentrocosto().getNombre();
                    } else if (cualCelda == 4) {
                        proyectoVP = filtradoVigenciasProrrateosCentroC.get(indexVP).getProyecto().getNombreproyecto();
                    }
                }
            }
        }
        if (bandera == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla3 = "115";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectos = null;
            tipoListaVPP = 0;
        }
        index = -1;
        secRegistroVL = null;
        indexVPP = -1;
        secRegistroVPP = null;
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasProrrateosProyectos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVPP(int indice, int celda) {
        FacesContext c = FacesContext.getCurrentInstance();
        if (permitirIndexVPP) {
            indexVPP = indice;
            cualCeldaVPP = celda;
            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                secRegistroVPP = vigenciasProrrateosProyectos.get(indexVPP).getSecuencia();
                if (cualCeldaVP == 0) {
                    proyectoVPP = vigenciasProrrateosProyectos.get(indexVPP).getProyecto().getNombreproyecto();
                }
            } else {// Si tiene Filtro
                secRegistroVPP = filtradoVigenciasProrrateosProyectos.get(indexVPP).getSecuencia();
                if (cualCeldaVP == 0) {
                    proyectoVPP = filtradoVigenciasProrrateosProyectos.get(indexVPP).getProyecto().getNombreproyecto();
                }
            }
        }
        if (bandera == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVP == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosCentroC = null;
            tipoListaVP = 0;
        }
        index = -1;
        secRegistroVL = null;
        indexVP = -1;
        secRegistroVP = null;
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
            getVigenciaLocalizacionSeleccionada();
            getVigenciaLocalizaciones();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Localizacion con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            paraNuevaV = 0;
        }
        index = -1;
        secRegistroVL = null;

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
            getVigenciaProrrateoSeleccionada();
            paraNuevaV = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            cambioVigenciaP = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Prorrateo Centro Costo con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        indexVP = -1;
        secRegistroVP = null;

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
            getVigenciaProrrateoProyectoSeleccionada();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            paraNuevaV = 0;
            cambioVigenciaPP = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Prorrateo Proyecto con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }

        indexVPP = -1;
        secRegistroVPP = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
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
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVP == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosCentroC = null;
            tipoListaVP = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla3 = "115";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectos = null;
            tipoListaVPP = 0;
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
        index = -1;
        secRegistroVL = null;
        indexVP = -1;
        secRegistroVP = null;
        indexVPP = -1;
        secRegistroVPP = null;
        paraNuevaV = 0;
        vigenciaLocalizaciones = null;
        vigenciasProrrateosCentroC = null;
        vigenciasProrrateosProyectos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVLEmpleado");
        context.update("form:datosVPVigencia");
        context.update("form:datosVPPVigencia");
        cambiosVigencia = false;
        cambioVigenciaP = false;
        cambioVigenciaPP = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVP() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVP == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosCentroC = null;
            tipoListaVP = 0;
        }
        listVPBorrar.clear();
        listVPCrear.clear();
        listVPModificar.clear();
        indexVP = -1;
        secRegistroVP = null;
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
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla3 = "115";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectos = null;
            tipoListaVPP = 0;
        }
        listVPPBorrar.clear();
        listVPPCrear.clear();
        listVPPModificar.clear();
        indexVPP = -1;
        secRegistroVPP = null;
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
        if (index < 0 && indexVP < 0 && indexVPP < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (tipoLista == 0) {// Si NO tiene Filtro
                    editarVL = vigenciaLocalizaciones.get(index);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    editarVL = filtrarVL.get(index);
                }
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
            if (indexVP >= 0) {
                if (tipoListaVP == 0) {// Si NO tiene Filtro
                    editarVP = vigenciasProrrateosCentroC.get(indexVP);
                }
                if (tipoListaVP == 1) {// Si tiene Filtro
                    editarVP = filtradoVigenciasProrrateosCentroC.get(indexVP);
                }
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
            if (indexVPP >= 0) {
                if (tipoListaVPP == 0) {// Si NO tiene Filtro
                    editarVPP = vigenciasProrrateosProyectos.get(indexVPP);
                }
                if (tipoListaVPP == 1) {// Si tiene Filtro
                    editarVPP = filtradoVigenciasProrrateosProyectos.get(tipoListaVPP);
                }
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
            //index = -1;
            //secRegistroVL = null;
            //indexVP = -1;
            //secRegistroVP = null;
            //indexVPP = -1;
            //secRegistroVPP = null;
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
                        //CERRAR FILTRADO
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
                        RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                        bandera = 0;
                        filtrarVL = null;
                        tipoLista = 0;
                    }
                    //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    nuevaVigencia.setSecuencia(nuevaVLSecuencia);
                    nuevaVigencia.setEmpleado(empleado);
                    listVLCrear.add(nuevaVigencia);

                    vigenciaLocalizaciones.add(nuevaVigencia);
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
                    index = -1;
                    secRegistroVL = null;
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
        //index = -1;
        secRegistroVL = null;
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
                        FacesContext c =
                                FacesContext.getCurrentInstance();
                        vPCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
                        vPCentroCosto.setFilterStyle("display: none; visibility:hidden;");
                        vPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                        vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                        vPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
                        vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        vPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
                        vPFechaFinal.setFilterStyle("display: none; visibility:hidden;");
                        vPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
                        vPProyecto.setFilterStyle("display: none; visibility: hidden;");
                        vPSubPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
                        vPSubPorcentaje.setFilterStyle("display: none; visibility:hidden;");
                        altoTabla2 = "115";
                        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                        banderaVP = 0;
                        filtradoVigenciasProrrateosCentroC = null;
                        tipoListaVP = 0;
                    } //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                    paraNuevaV++;
                    nuevaVLSecuencia =
                            BigInteger.valueOf(paraNuevaV);
                    nuevaVigenciaP.setSecuencia(nuevaVLSecuencia);
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        nuevaVigenciaP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                    }
                    if (tipoLista == 1) {// Si tiene Filtro
                        nuevaVigenciaP.setViglocalizacion(filtrarVL.get(indexAuxVL));
                    }
                    listVPCrear.add(nuevaVigenciaP);
                    if (vigenciasProrrateosCentroC
                            == null) {
                        vigenciasProrrateosCentroC = new ArrayList<VigenciasProrrateos>();
                    }
                    vigenciasProrrateosCentroC.add(nuevaVigenciaP);
                    nuevaVigenciaP = new VigenciasProrrateos();
                    nuevaVigenciaP.setCentrocosto(new CentrosCostos());
                    nuevaVigenciaP.setProyecto(new Proyectos());
                    index = indexAuxVL;
                    context.update("form:datosVPVigencia");
                    context.execute("NuevoRegistroVP.hide()");
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    tipoActualizacion = -1;
                    indexVP = -1;
                    secRegistroVP = null;
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
        //indexVP = -1;
        secRegistroVP = null;
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
                        RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                        banderaVPP = 0;
                        filtradoVigenciasProrrateosProyectos = null;
                        tipoListaVPP = 0;
                    }
                    //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                    paraNuevaV++;
                    nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);
                    nuevaVigenciaPP.setSecuencia(nuevaVLSecuencia);
                    if (tipoLista == 0) {// Si NO tiene Filtro
                        nuevaVigenciaPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                    }
                    if (tipoLista == 1) {// Si tiene Filtro
                        nuevaVigenciaPP.setVigencialocalizacion(filtrarVL.get(indexAuxVL));
                    }
                    listVPPCrear.add(nuevaVigenciaPP);
                    if (vigenciasProrrateosProyectos == null) {
                        vigenciasProrrateosProyectos = new ArrayList<VigenciasProrrateosProyectos>();
                    }
                    vigenciasProrrateosProyectos.add(nuevaVigenciaPP);
                    nuevaVigenciaPP = new VigenciasProrrateosProyectos();
                    nuevaVigenciaPP.setProyecto(new Proyectos());
                    index = indexAuxVL;
                    context.update("form:datosVPPVigencia");
                    context.execute("NuevoRegistroVPP.hide()");
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    indexVPP = -1;
                    secRegistroVPP = null;
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
        //indexVPP = -1;
        //secRegistroVPP = null;
    }
    //DUPLICAR VL

    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0 && indexVP < 0 && indexVPP < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (guardado) {
                tipoActualizacion = 2;
                if (index >= 0) {
                    duplicarVigenciaL();
                }
                if (indexVP >= 0) {
                    duplicarVigenciaP();
                }
                if (indexVPP >= 0) {
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
        if (index >= 0) {
            duplicarVL = new VigenciasLocalizaciones();
            paraNuevaV++;
            nuevaVLSecuencia = BigInteger.valueOf(paraNuevaV);

            if (tipoLista == 0) {// Si NO tiene Filtro

                duplicarVL.setEmpleado(vigenciaLocalizaciones.get(index).getEmpleado());
                duplicarVL.setFechavigencia(vigenciaLocalizaciones.get(index).getFechavigencia());
                duplicarVL.setLocalizacion(vigenciaLocalizaciones.get(index).getLocalizacion());
                duplicarVL.setMotivo(vigenciaLocalizaciones.get(index).getMotivo());
                duplicarVL.setProyecto(vigenciaLocalizaciones.get(index).getProyecto());
            }
            if (tipoLista == 1) {// Si tiene Filtro

                duplicarVL.setEmpleado(filtrarVL.get(index).getEmpleado());
                duplicarVL.setFechavigencia(filtrarVL.get(index).getFechavigencia());
                duplicarVL.setLocalizacion(filtrarVL.get(index).getLocalizacion());
                duplicarVL.setMotivo(filtrarVL.get(index).getMotivo());
                duplicarVL.setProyecto(filtrarVL.get(index).getProyecto());
            }
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
            //index = -1;
            //secRegistroVL = null;
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
                    context.update("form:datosVLEmpleado");
                    context.execute("DuplicarRegistroVL.hide();");
                    index = -1;
                    secRegistroVL = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (bandera == 1) {
                        //CERRAR FILTRADO
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
                        filtrarVL = null;
                        tipoLista = 0;
                    }
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
        if (indexVP >= 0) {
            duplicarVP = new VigenciasProrrateos();

            if (tipoListaVP == 0) {// Si NO tiene Filtro

                duplicarVP.setCentrocosto(vigenciasProrrateosCentroC.get(indexVP).getCentrocosto());
                duplicarVP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVP.setFechafinal(vigenciasProrrateosCentroC.get(indexVP).getFechafinal());
                duplicarVP.setFechainicial(vigenciasProrrateosCentroC.get(indexVP).getFechainicial());
                duplicarVP.setPorcentaje(vigenciasProrrateosCentroC.get(indexVP).getPorcentaje());
                duplicarVP.setSubporcentaje(vigenciasProrrateosCentroC.get(indexVP).getSubporcentaje());
                duplicarVP.setProyecto(vigenciasProrrateosCentroC.get(indexVP).getProyecto());
            }
            if (tipoListaVP == 1) {// Si tiene Filtro

                duplicarVP.setCentrocosto(filtradoVigenciasProrrateosCentroC.get(indexVP).getCentrocosto());
                duplicarVP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVP.setFechafinal(filtradoVigenciasProrrateosCentroC.get(indexVP).getFechafinal());
                duplicarVP.setFechainicial(filtradoVigenciasProrrateosCentroC.get(indexVP).getFechainicial());
                duplicarVP.setPorcentaje(filtradoVigenciasProrrateosCentroC.get(indexVP).getPorcentaje());
                duplicarVP.setSubporcentaje(filtradoVigenciasProrrateosCentroC.get(indexVP).getSubporcentaje());
                duplicarVP.setProyecto(filtradoVigenciasProrrateosCentroC.get(indexVP).getProyecto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoVP");
            context.execute("DuplicadoRegistroVP.show()");
            //indexVP = -1;
            secRegistroVP = null;
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
                    context.update("form:datosVPVigencia");
                    context.execute("DuplicadoRegistroVP.hide();");
                    indexVP = -1;
                    secRegistroVP = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (banderaVP == 1) {
                        //CERRAR FILTRADO
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
                        RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                        banderaVP = 0;
                        filtradoVigenciasProrrateosCentroC = null;
                        tipoListaVP = 0;
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
        if (indexVPP >= 0) {
            duplicarVPP = new VigenciasProrrateosProyectos();

            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                duplicarVPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVPP.setFechafinal(vigenciasProrrateosProyectos.get(indexVPP).getFechafinal());
                duplicarVPP.setFechainicial(vigenciasProrrateosProyectos.get(indexVPP).getFechainicial());
                duplicarVPP.setPorcentaje(vigenciasProrrateosProyectos.get(indexVPP).getPorcentaje());
                duplicarVPP.setProyecto(vigenciasProrrateosProyectos.get(indexVPP).getProyecto());
            }
            if (tipoListaVPP == 1) {// Si tiene Filtro
                duplicarVPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVPP.setFechafinal(filtradoVigenciasProrrateosProyectos.get(indexVPP).getFechafinal());
                duplicarVPP.setFechainicial(filtradoVigenciasProrrateosProyectos.get(indexVPP).getFechainicial());
                duplicarVPP.setPorcentaje(filtradoVigenciasProrrateosProyectos.get(indexVPP).getPorcentaje());
                duplicarVPP.setProyecto(filtradoVigenciasProrrateosProyectos.get(indexVPP).getProyecto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVPP");
            context.execute("DuplicarRegistroVPP.show()");
            //indexVPP = -1;
            //secRegistroVPP = null;
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
                    context.update("form:datosVPPVigencia");
                    context.execute("DuplicarRegistroVPP.show()");
                    indexVPP = -1;
                    secRegistroVPP = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (banderaVPP == 1) {
                        //CERRAR FILTRADO
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
                        filtradoVigenciasProrrateosProyectos = null;
                        tipoListaVPP = 0;
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
        if (index < 0 && indexVP < 0 && indexVPP < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
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
            if (indexVP >= 0) {
                borrarVP();
            }
            if (indexVPP >= 0) {
                borrarVPP();
            }
        }
    }
    //BORRAR VL

    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarVL() {
        if (index >= 0) {
            cambiosVigencia = true;
            if (tipoLista == 0) {// Si NO tiene Filtro
                if (!listVLModificar.isEmpty() && listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                    int modIndex = listVLModificar.indexOf(vigenciaLocalizaciones.get(index));
                    listVLModificar.remove(modIndex);
                    listVLBorrar.add(vigenciaLocalizaciones.get(index));
                } else if (!listVLCrear.isEmpty() && listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    int crearIndex = listVLCrear.indexOf(vigenciaLocalizaciones.get(index));
                    listVLCrear.remove(crearIndex);
                } else {
                    listVLBorrar.add(vigenciaLocalizaciones.get(index));
                }
                vigenciaLocalizaciones.remove(index);
            }
            if (tipoLista == 1) {// Si tiene Filtro
                if (!listVLModificar.isEmpty() && listVLModificar.contains(filtrarVL.get(index))) {
                    int modIndex = listVLModificar.indexOf(filtrarVL.get(index));
                    listVLModificar.remove(modIndex);
                    listVLBorrar.add(filtrarVL.get(index));
                } else if (!listVLCrear.isEmpty() && listVLCrear.contains(filtrarVL.get(index))) {
                    int crearIndex = listVLCrear.indexOf(filtrarVL.get(index));
                    listVLCrear.remove(crearIndex);
                } else {
                    listVLBorrar.add(filtrarVL.get(index));
                }
                int VLIndex = vigenciaLocalizaciones.indexOf(filtrarVL.get(index));
                vigenciaLocalizaciones.remove(VLIndex);
                filtrarVL.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            index = -1;
            secRegistroVL = null;
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
        if (indexVP >= 0) {
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                if (!listVPModificar.isEmpty() && listVPModificar.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                    int modIndex = listVPModificar.indexOf(vigenciasProrrateosCentroC.get(indexVP));
                    listVPModificar.remove(modIndex);
                    listVPBorrar.add(vigenciasProrrateosCentroC.get(indexVP));
                } else if (!listVPCrear.isEmpty() && listVPCrear.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                    int crearIndex = listVPCrear.indexOf(vigenciasProrrateosCentroC.get(indexVP));
                    listVPCrear.remove(crearIndex);
                } else {
                    listVPBorrar.add(vigenciasProrrateosCentroC.get(indexVP));
                }
                vigenciasProrrateosCentroC.remove(indexVP);
            }
            if (tipoListaVP == 1) {// Si tiene Filtro
                if (!listVPModificar.isEmpty() && listVPModificar.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                    int modIndex = listVPModificar.indexOf(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    listVPModificar.remove(modIndex);
                    listVPBorrar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                } else if (!listVPCrear.isEmpty() && listVPCrear.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                    int crearIndex = listVPCrear.indexOf(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    listVPCrear.remove(crearIndex);
                } else {
                    listVPBorrar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                }
                int VPIndex = vigenciasProrrateosCentroC.indexOf(filtradoVigenciasProrrateosCentroC.get(indexVP));
                vigenciasProrrateosCentroC.remove(VPIndex);
                filtradoVigenciasProrrateosCentroC.remove(indexVP);
            }
            index = indexAuxVL;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPVigencia");
            indexVP = -1;
            secRegistroVP = null;
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
        if (indexVPP >= 0) {
            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                if (!listVPPModificar.isEmpty() && listVPPModificar.contains(vigenciasProrrateosProyectos.get(indexVPP))) {
                    int modIndex = listVPPModificar.indexOf(vigenciasProrrateosProyectos.get(indexVPP));
                    listVPPModificar.remove(modIndex);
                    listVPPBorrar.add(vigenciasProrrateosProyectos.get(indexVPP));
                } else if (!listVPPCrear.isEmpty() && listVPPCrear.contains(vigenciasProrrateosProyectos.get(indexVPP))) {
                    int crearIndex = listVPPCrear.indexOf(vigenciasProrrateosProyectos.get(indexVPP));
                    listVPPCrear.remove(crearIndex);
                } else {
                    listVPPBorrar.add(vigenciasProrrateosProyectos.get(indexVPP));
                }
                vigenciasProrrateosProyectos.remove(indexVPP);
            }
            if (tipoListaVPP == 1) {// Si tiene Filtro
                if (!listVPPModificar.isEmpty() && listVPPModificar.contains(filtradoVigenciasProrrateosProyectos.get(indexVPP))) {
                    int modIndex = listVPPModificar.indexOf(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                    listVPPModificar.remove(modIndex);
                    listVPPBorrar.add(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                } else if (!listVPPCrear.isEmpty() && listVPPCrear.contains(filtradoVigenciasProrrateosProyectos.get(indexVPP))) {
                    int crearIndex = listVPPCrear.indexOf(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                    listVPPCrear.remove(crearIndex);
                } else {
                    listVPPBorrar.add(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                }
                int VPPIndex = vigenciasProrrateosProyectos.indexOf(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                vigenciasProrrateosProyectos.remove(VPPIndex);
                filtradoVigenciasProrrateosProyectos.remove(indexVPP);
            }
            index = indexAuxVL;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            indexVPP = -1;
            secRegistroVPP = null;
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
        if (index >= 0) {
            filtradoVigenciaLocalizacion();
            index = -1;
            secRegistroVL = null;
        }
        if (indexVP >= 0) {
            filtradoVigenciaProrrateo();
            indexVP = -1;
            secRegistroVP = null;
        }
        if (indexVPP >= 0) {
            filtradoVigenciaProrrateoProyecto();
            indexVPP = -1;
            secRegistroVPP = null;
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoVigenciaLocalizacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            vlFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("width: 60px");
            vlCentroCosto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("width: 100px");
            vlLocalizacion = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("width: 100px");
            vlMotivo = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("width: 100px");
            vlProyecto = (Column) c.getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("width: 100px");
            altoTabla1 = "91";
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
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
            vPCentroCosto.setFilterStyle("width: 60px");
            vPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("width: 25px");
            vPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("width: 50px");
            vPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("width: 50px");
            vPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("width: 50px");
            vPSubPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("width: 50px");
            altoTabla2 = "91";
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 1;
        } else if (banderaVP == 1) {
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
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosCentroC = null;
            tipoListaVP = 0;
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
            vPPProyecto.setFilterStyle("width: 60px");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("width: 25px");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("width: 50px");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("width: 50px");
            altoTabla3 = "91";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 1;
        } else if (banderaVPP == 1) {
            vPPProyecto = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla3 = "115";
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectos = null;
            tipoListaVPP = 0;
        }

    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
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
            context.update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }

        listVLBorrar.clear();
        listVLCrear.clear();
        listVLModificar.clear();
        index = -1;
        secRegistroVL = null;
        indexVP = -1;
        secRegistroVP = null;
        indexVPP = -1;
        secRegistroVPP = null;
        paraNuevaV = 0;
        vigenciaLocalizaciones = null;
        listCentrosCostos = null;
        listProyectos = null;
        listMotivosLocalizaciones = null;
        listEstructurasCC = null;
        guardado = true;
        context.update("form:ACEPTAR");
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     * @param tt Tipo Tabla : VigenciaLocalizacion / VigenciaProrrateo /
     * VigenciaProrrateoProyecto
     */
    public void asignarIndex(Integer indice, int dlg, int LND, int tt) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tt == 0) {
            if (LND == 0) {
                index = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                //Estructuras
                getInfoRegistroEstLocalizacion();
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
            } else if (dlg == 1) {
                //MotivosLocalizaciones
                getInfoRegistroMotivoLocalizacion();
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
            } else if (dlg == 2) {
                //Proyectos
                getInfoRegistroProyecto();
                context.update("form:ProyectosDialogo");
                context.execute("ProyectosDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                indexVP = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                //Centro Costos
                getInfoRegistroCentroCosto();
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
            } else if (dlg == 1) {
                //Proyectos
                getInfoRegistroProyectoVP();
                context.update("form:ProyectosDialogoVP");
                context.execute("ProyectosDialogoVP.show()");
            }
        }
        if (tt == 2) {
            if (LND == 0) {
                indexVPP = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                //Proyectos
                getInfoRegistroProyectoVPP();
                context.update("form:ProyectosDialogoVPP");
                context.execute("ProyectosDialogoVPP.show()");
            }
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
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(index).setLocalizacion(estructuraSelecionada);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {// Si tiene Filtro
                filtrarVL.get(index).setLocalizacion(estructuraSelecionada);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
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
        //index = -1;
        //secRegistroVL = null;
        tipoActualizacion = -1;/*
         * context.update("form:LocalizacionDialogo");
         * context.update("form:lovLocalizacion");
         * context.update("form:aceptarL");
         */

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
        //index = -1;
        //secRegistroVL = null;
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
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(index).setMotivo(motivoLocalizacionSelecionado);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {// Si tiene Filtro
                filtrarVL.get(index).setMotivo(motivoLocalizacionSelecionado);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
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
        //index = -1;
        //secRegistroVL = null;
        tipoActualizacion = -1;/*
         * context.update("form:lovMotivo"); context.update("form:aceptarM");
         */

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
        //index = -1;
        //secRegistroVL = null;
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
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            if (tipoLista == 0) {// Si NO tiene Filtro
                vigenciaLocalizaciones.get(index).setProyecto(proyectoSelecionado);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {// Si tiene Filtro
                filtrarVL.get(index).setProyecto(proyectoSelecionado);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
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
            nuevaVigencia.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVL.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoProyectos = null;
        proyectoSelecionado = new Proyectos();
        aceptar = true;
        //index = -1;
        //secRegistroVL = null;
        tipoActualizacion = -1;/*
         * context.update("form:ProyectosDialogo");
         * context.update("form:lovProyectos"); context.update("form:aceptarP");
         */

        context.reset("form:lovProyectos:globalFilter");
        context.execute("lovProyectos.clearFilters()");
        context.execute("ProyectosDialogo.hide()");
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioProyecto() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        //index = -1;
        //secRegistroVL = null;
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
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                vigenciasProrrateosCentroC.get(indexVP).setCentrocosto(centroCostoSeleccionado);
                if (!listVPCrear.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indexVP));
                    } else if (!listVPModificar.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indexVP));
                    }
                }
            } else {// Si tiene Filtro
                filtradoVigenciasProrrateosCentroC.get(indexVP).setCentrocosto(centroCostoSeleccionado);
                if (!listVPCrear.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    }
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
        //indexVP = -1;
        //secRegistroVP = null;
        tipoActualizacion = -1;/*
         * context.update("form:CentroCostosDialogo");
         * context.update("form:lovCentroCostos");
         * context.update("form:aceptarCC");
         */

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
        //indexVP = -1;
        //secRegistroVP = null;
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
    public void actualizarProyectoVP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            if (tipoListaVP == 0) {// Si NO tiene Filtro
                vigenciasProrrateosCentroC.get(indexVP).setProyecto(proyectoSelecionado);
                if (!listVPCrear.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indexVP));
                    } else if (!listVPModificar.contains(vigenciasProrrateosCentroC.get(indexVP))) {
                        listVPModificar.add(vigenciasProrrateosCentroC.get(indexVP));
                    }
                }
            } else {// Si tiene Filtro
                filtradoVigenciasProrrateosCentroC.get(indexVP).setProyecto(proyectoSelecionado);
                if (!listVPCrear.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosCentroC.get(indexVP))) {
                        listVPModificar.add(filtradoVigenciasProrrateosCentroC.get(indexVP));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaP = true;
            permitirIndexVP = true;
            context.update("form:datosVPVigencia");
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigenciaP.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:nuevaVP");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVP.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:duplicarVP");
        }
        filtradoProyectos = null;
        proyectoSelecionado = new Proyectos();
        aceptar = true;
        //indexVP = -1;
        //secRegistroVP = null;
        tipoActualizacion = -1;/*
         * context.update("form:ProyectosDialogoVP");
         * context.update("form:lovProyectosVP");
         * context.update("form:aceptarPVP");
         */

        context.reset("form:lovProyectosVP:globalFilter");
        context.execute("lovProyectosVP.clearFilters()");
        context.execute("ProyectosDialogoVP.hide()");
    }

    /**
     * Cancela el proyecto seleccionado (vigencia prorrateo)
     */
    public void cancelarCambioProyectoVP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        //indexVP = -1;
        //secRegistroVP = null;
        tipoActualizacion = -1;
        permitirIndexVP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProyectosVP:globalFilter");
        context.execute("lovProyectosVP.clearFilters()");
        context.execute("ProyectosDialogoVP.hide()");
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Actualiza el proyecto seleccionado (vigencia prorrateo proyecto)
     */
    public void actualizarProyectoVPP() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            if (tipoListaVPP == 0) {// Si NO tiene Filtro
                vigenciasProrrateosProyectos.get(indexVPP).setProyecto(proyectoSelecionado);
                if (!listVPPCrear.contains(vigenciasProrrateosProyectos.get(indexVPP))) {
                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(vigenciasProrrateosProyectos.get(indexVPP));
                    } else if (!listVPPModificar.contains(vigenciasProrrateosProyectos.get(indexVPP))) {
                        listVPPModificar.add(vigenciasProrrateosProyectos.get(indexVPP));
                    }
                }
            } else {// Si tiene Filtro
                filtradoVigenciasProrrateosProyectos.get(indexVPP).setProyecto(proyectoSelecionado);
                if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectos.get(indexVPP))) {
                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                    } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectos.get(indexVPP))) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectos.get(indexVPP));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndexVPP = true;
            cambioVigenciaPP = true;
            context.update("form:datosVPPVigencia");
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigenciaPP.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:nuevaVPP");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVPP.setProyecto(proyectoSelecionado);
            context.update("formularioDialogos:duplicarVPP");
        }
        filtradoProyectos = null;
        proyectoSelecionado = new Proyectos();
        aceptar = true;
        //indexVPP = -1;
        //secRegistroVPP = null;
        tipoActualizacion = -1;/*
         * context.update("form:ProyectosDialogoVPP");
         * context.update("form:lovProyectosVPP");
         * context.update("form:aceptarPVPP");
         */

        context.reset("form:lovProyectosVPP:globalFilter");
        context.execute("lovProyectosVPP.clearFilters()");
        context.execute("ProyectosDialogoVPP.hide()");
    }

    /**
     * Cancela la seleccion del proyecto (vigencioa prorrateo proyecto)
     */
    public void cancelarCambioProyectoVPP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        //indexVPP = -1;
        //secRegistroVPP = null;
        tipoActualizacion = -1;
        permitirIndexVPP = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProyectosVPP:globalFilter");
        context.execute("lovProyectosVPP.clearFilters()");
        context.execute("ProyectosDialogoVPP.hide()");
    }
    //LISTA DE VALORES DINAMICA

    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        if (index < 0 && indexVP < 0 && indexVPP < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (cualCelda == 1) {
                    //Estructura
                    getInfoRegistroEstLocalizacion();
                    context.update("form:LocalizacionDialogo");
                    context.execute("LocalizacionDialogo.show()");
                    tipoActualizacion = 0;
                }
                if (cualCelda == 3) {
                    //Motivos
                    getInfoRegistroMotivoLocalizacion();
                    context.update("form:MotivoDialogo");
                    context.execute("MotivoDialogo.show()");
                    tipoActualizacion = 0;
                }
                if (cualCelda == 4) {
                    //Proyecto
                    getInfoRegistroProyecto();
                    context.update("form:ProyectosDialogo");
                    context.execute("ProyectosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (indexVP >= 0) {
                if (cualCeldaVP == 0) {
                    getInfoRegistroCentroCosto();
                    context.update("form:CentroCostosDialogo");
                    context.execute("CentroCostosDialogo.show()");
                    tipoActualizacion = 0;
                }
                if (cualCeldaVP == 4) {
                    getInfoRegistroProyectoVP();
                    context.update("form:ProyectosDialogoVP");
                    context.execute("ProyectosDialogoVP.show()");
                    tipoActualizacion = 0;
                }
            }
            if (indexVPP >= 0) {
                if (cualCeldaVPP == 0) {
                    getInfoRegistroProyectoVPP();
                    context.update("form:ProyectosDialogoVPP");
                    context.execute("ProyectosDialogoVPP.show()");
                    tipoActualizacion = 0;
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (tabla == 1) {
            //Dialogo de nuevo registro vigencias localizaciones
            context.update("form:NuevoRegistroVL");
            context.execute("NuevoRegistroVL.show()");
        }
        if (tabla == 2) {
            if (index >= 0) {
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
            if (index >= 0) {
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
        //index = -1;
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
        if (index >= 0) {
            nombreTabla = ":formExportarVL:datosVLEmpleadoExportar";
            nombreXML = "VigenciasLocalizacionesXML";
        }
        if (indexVP >= 0) {
            nombreTabla = ":formExportarVP:datosVPVigenciaExportar";
            nombreXML = "VigenciasProrrateoXML";
        }
        if (indexVPP >= 0) {
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
        if (index >= 0) {
            exportPDFVL();
        }
        if (indexVP >= 0) {
            exportPDFVP();
        }
        if (indexVPP >= 0) {
            exportPDFVPP();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVL() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVL:datosVLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasLocalizacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        //index = -1;
        //secRegistroVL = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasProrrateosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        //indexVP = -1;
        //secRegistroVP = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVPP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVPP:datosVPPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasProrrateosProyectosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        //indexVPP = -1;
        //secRegistroVPP = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLSVL();
        }
        if (indexVP >= 0) {
            exportXLSVP();
        }
        if (indexVPP >= 0) {
            exportXLSVPP();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVL() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVL:datosVLEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasLocalizacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        //index = -1;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasProrrateosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        //indexVP = -1;
        //secRegistroVP = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVPP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVPP:datosVPPVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasProrrateosProyectosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        //indexVPP = -1;
        //secRegistroVPP = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
                index = -1;
                secRegistroVL = null;
            }
        }
        if (indexVP >= 0) {
            if (tipoListaVP == 0) {
                tipoListaVP = 1;
                indexVP = -1;
                secRegistroVP = null;
            }
        }
        if (indexVPP >= 0) {
            if (tipoListaVPP == 0) {
                tipoListaVPP = 1;
                indexVPP = -1;
                secRegistroVPP = null;
            }
        }
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void verificarRastroTabla() {
        //Si no hay registros eleccionado
        if (index < 0 && indexVP < 0 && indexVPP < 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        } else if (indexVP >= 0) {
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroVigenciaProrrateoCC();
                //indexVP = -1;
            } else if (indexVPP >= 0) {
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroVigenciaProrrateoProyecto();
                //indexVPP = -1;
            } else if (index >= 0) {
                verificarRastroVigenciaLocalizacion();
                //index = -1;
            }
    }

    public void verificarRastroVigenciaLocalizacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(secRegistroVL, "VIGENCIASLOCALIZACIONES");
        backUpSecRegistroVL = secRegistroVL;
        backUp = secRegistroVL;
        secRegistroVL = null;
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
        int resultado = administrarRastros.obtenerTabla(secRegistroVP, "VIGENCIASPRORRATEOS");
        backUpSecRegistroVP = secRegistroVP;
        backUp = secRegistroVP;
        secRegistroVP = null;
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
        int resultado = administrarRastros.obtenerTabla(secRegistroVPP, "VIGENCIASPRORRATEOSPROYECTOS");
        backUpSecRegistroVPP = secRegistroVPP;
        backUp = secRegistroVPP;
        secRegistroVPP = null;
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

    public void recordarSeleccionVL() {
        if (index >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVLEmpleado");
            vigenciaLocalizacionSeleccionada = vigenciaLocalizaciones.get(index);
            tabla.setSelection(vigenciaLocalizacionSeleccionada);
        } else {
            vigenciaLocalizacionSeleccionada = null;
        }
    }

    public void recordarSeleccionVP() {
        if (indexVP >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVPVigencia");
            vigenciaProrrateoSeleccionada = vigenciasProrrateosCentroC.get(indexVP);
            tabla.setSelection(vigenciaProrrateoSeleccionada);
        } else {
            vigenciaProrrateoSeleccionada = null;
        }
    }

    public void recordarSeleccionVPP() {
        if (indexVPP >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosVPPVigencia");
            vigenciaProrrateoProyectoSeleccionada = vigenciasProrrateosProyectos.get(indexVPP);
            tabla.setSelection(vigenciaProrrateoProyectoSeleccionada);
        } else {
            vigenciaProrrateoProyectoSeleccionada = null;
        }
    }

    public List<VigenciasLocalizaciones> getVigenciaLocalizaciones() {
        try {
            if (vigenciaLocalizaciones == null) {
                if (empleado != null) {
                    vigenciaLocalizaciones = new ArrayList<VigenciasLocalizaciones>();
                    vigenciaLocalizaciones = administrarVigenciaLocalizacion.VigenciasLocalizacionesEmpleado(empleado.getSecuencia());
                    if (vigenciaLocalizaciones != null) {
                        for (int i = 0; i < vigenciaLocalizaciones.size(); i++) {
                            if (vigenciaLocalizaciones.get(i).getProyecto() == null) {
                                vigenciaLocalizaciones.get(i).setProyecto(new Proyectos());
                            }
                        }
                    }
                }
            }
            return vigenciaLocalizaciones;

        } catch (Exception e) {
            System.out.println("Error getVigenciaLocalizaciones " + e.toString());
            return null;
        }
    }

    public void setVigenciaLocalizaciones(List<VigenciasLocalizaciones> vigenciaLocalizaciones) {
        this.vigenciaLocalizaciones = vigenciaLocalizaciones;
    }

    public List<VigenciasLocalizaciones> getFiltrarVL() {
        try {
            return filtrarVL;
        } catch (Exception e) {
            System.out.println("Error getFiltrarVL : " + e.toString());
            return null;
        }
    }

    public void setFiltrarVL(List<VigenciasLocalizaciones> filtrarVL) {
        try {
            this.filtrarVL = filtrarVL;
        } catch (Exception e) {
            System.out.println("Se estallo: " + e);
        }

    }

    public List<Estructuras> getListEstructurasCC() {
        if (listEstructurasCC == null) {
            listEstructurasCC = administrarVigenciaLocalizacion.estructuras();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listEstructurasCC == null || listEstructurasCC.isEmpty()) {
                infoRegistroEstLocalizacion = "Cantidad de registros: 0 ";
            } else {
                infoRegistroEstLocalizacion = "Cantidad de registros: " + listEstructurasCC.size();
            }
            context.update("form:infoRegistroTipoSueldo");
        }
        return listEstructurasCC;
        /*
         * try { if (listEstructurasCC == null || listEstructurasCC.isEmpty()) {
         * listEstructurasCC = administrarVigenciaLocalizacion.estructuras(); }
         * return listEstructurasCC; } catch (Exception e) {
         * e.printStackTrace(); return null; }
         */
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
            RequestContext context = RequestContext.getCurrentInstance();
            if (listMotivosLocalizaciones == null || listMotivosLocalizaciones.isEmpty()) {
                infoRegistroMotivoLocalizacion = "Cantidad de registros: 0 ";
            } else {
                infoRegistroMotivoLocalizacion = "Cantidad de registros: " + listMotivosLocalizaciones.size();
            }
            context.update("form:infoRegistroTipoSueldo");
        }
        return listMotivosLocalizaciones;
        /*
         * try { if (listMotivosLocalizaciones == null ||
         * listMotivosLocalizaciones.isEmpty()) { listMotivosLocalizaciones =
         * administrarVigenciaLocalizacion.motivosLocalizaciones(); } return
         * listMotivosLocalizaciones; } catch (Exception e) {
         * e.printStackTrace(); return null; }
         */
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
            RequestContext context = RequestContext.getCurrentInstance();
            if (listProyectos == null || listProyectos.isEmpty()) {
                infoRegistroProyecto = "Cantidad de registros: 0 ";
            } else {
                infoRegistroProyecto = "Cantidad de registros: " + listProyectos.size();
            }
            context.update("form:infoRegistroTipoSueldo");
        }
        return listProyectos;
        /*
         * try { if (listProyectos == null || listProyectos.isEmpty()) {
         * listProyectos = administrarVigenciaLocalizacion.proyectos(); } return
         * listProyectos; } catch (Exception e) { System.out.println("Error
         * getProyectos " + e.toString()); return null; }
         */
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
            if (index >= 0) {
                VigenciasLocalizaciones vigenciaTemporal = null;
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaTemporal = vigenciaLocalizaciones.get(index);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    vigenciaTemporal = filtrarVL.get(index);
                }
                // if (vigenciasProrrateosCentroC == null) {
                vigenciasProrrateosCentroC = administrarVigenciaLocalizacion.VigenciasProrrateosVigencia(vigenciaTemporal.getSecuencia());
                if (vigenciasProrrateosCentroC != null) {
                    for (int i = 0; i < vigenciasProrrateosCentroC.size(); i++) {
                        if (vigenciasProrrateosCentroC.get(i).getProyecto() == null) {
                            vigenciasProrrateosCentroC.get(i).setProyecto(new Proyectos());
                        }
                    }
                    //}
                }
                /*
                 * if (vigenciasProrrateosVigencia == null) {
                 * vigenciasProrrateosVigencia = null; } else { for (int i = 0;
                 * i < vigenciasProrrateosVigencia.size(); i++) { //////////////
                 * if (!listVPModificar.isEmpty() &&
                 * listVPModificar.contains(vigenciasProrrateosVigencia.get(i)))
                 * { int modIndex =
                 * listVPModificar.indexOf(vigenciasProrrateosVigencia.get(i));
                 * vigenciasProrrateosVigencia.get(i).setCentrocosto(listVPModificar.get(modIndex).getCentrocosto());
                 * vigenciasProrrateosVigencia.get(i).setFechafinal(listVPModificar.get(modIndex).getFechafinal());
                 * vigenciasProrrateosVigencia.get(i).setFechainicial(listVPModificar.get(modIndex).getFechainicial());
                 * vigenciasProrrateosVigencia.get(i).setPorcentaje(listVPModificar.get(modIndex).getPorcentaje());
                 * vigenciasProrrateosVigencia.get(i).setSubporcentaje(listVPModificar.get(modIndex).getSubporcentaje());
                 * vigenciasProrrateosVigencia.get(i).setProyecto(listVPModificar.get(modIndex).getProyecto());
                 * } else if (!listVPBorrar.isEmpty() &&
                 * listVPBorrar.contains(vigenciasProrrateosVigencia.get(i))) {
                 * vigenciasProrrateosVigencia.remove(i); } } } if (listVPCrear
                 * != null) { for (int i = 0; i < listVPCrear.size(); i++) {
                 * vigenciasProrrateosVigencia.add(listVPCrear.get(i)); } }
                 */
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

    public List<VigenciasProrrateos> getFiltradoVigenciasProrrateosCentroC() {
        return filtradoVigenciasProrrateosCentroC;
    }

    public void setFiltradoVigenciasProrrateosCentroC(List<VigenciasProrrateos> filtradoVigenciasProrrateosVigencia) {
        this.filtradoVigenciasProrrateosCentroC = filtradoVigenciasProrrateosVigencia;
    }

    public List<VigenciasProrrateosProyectos> getVigenciasProrrateosProyectos() {
        try {
            if (index >= 0) {
                VigenciasLocalizaciones vigenciaTemporal = null;
                if (tipoLista == 0) {// Si NO tiene Filtro
                    vigenciaTemporal = vigenciaLocalizaciones.get(index);
                }
                if (tipoLista == 1) {// Si tiene Filtro
                    vigenciaTemporal = filtrarVL.get(index);
                }
                if (vigenciasProrrateosProyectos == null) {
                    vigenciasProrrateosProyectos = administrarVigenciaLocalizacion.VigenciasProrrateosProyectosVigencia(vigenciaTemporal.getSecuencia());
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

    public List<VigenciasProrrateosProyectos> getFiltradoVigenciasProrrateosProyectos() {
        return filtradoVigenciasProrrateosProyectos;
    }

    public void setFiltradoVigenciasProrrateosProyectos(List<VigenciasProrrateosProyectos> filtradoVigenciasProrrateosProyectosVigencia) {
        this.filtradoVigenciasProrrateosProyectos = filtradoVigenciasProrrateosProyectosVigencia;
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
            RequestContext context = RequestContext.getCurrentInstance();
            if (listCentrosCostos == null || listCentrosCostos.isEmpty()) {
                infoRegistroCentroCosto = "Cantidad de registros: 0 ";
            } else {
                infoRegistroCentroCosto = "Cantidad de registros: " + listCentrosCostos.size();
            }
            context.update("form:infoRegistroTipoSueldo");
        }
        return listCentrosCostos;
        /*
         * try { if (listCentrosCostos == null || listCentrosCostos.isEmpty()) {
         * listCentrosCostos = administrarVigenciaLocalizacion.centrosCostos();
         * } return listCentrosCostos; } catch (Exception e) {
         * System.out.println("Error getCentrosCostos " + e.toString()); return
         * null; }
         */
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

    public BigInteger getSecRegistroVL() {
        return secRegistroVL;
    }

    public void setSecRegistroVL(BigInteger secRegistroVL) {
        this.secRegistroVL = secRegistroVL;
    }

    public BigInteger getBackUpSecRegistroVL() {
        return backUpSecRegistroVL;
    }

    public void setBackUpSecRegistroVL(BigInteger backUpSecRegistroVL) {
        this.backUpSecRegistroVL = backUpSecRegistroVL;
    }

    public BigInteger getSecRegistroVP() {
        return secRegistroVP;
    }

    public void setSecRegistroVP(BigInteger secRegistroVP) {
        this.secRegistroVP = secRegistroVP;
    }

    public BigInteger getBackUpSecRegistroVP() {
        return backUpSecRegistroVP;
    }

    public void setBackUpSecRegistroVP(BigInteger backUpSecRegistroVP) {
        this.backUpSecRegistroVP = backUpSecRegistroVP;
    }

    public BigInteger getSecRegistroVPP() {
        return secRegistroVPP;
    }

    public void setSecRegistroVPP(BigInteger secRegistroVPP) {
        this.secRegistroVPP = secRegistroVPP;
    }

    public BigInteger getBackUpSecRegistroVPP() {
        return backUpSecRegistroVPP;
    }

    public void setBackUpSecRegistroVPP(BigInteger backUpSecRegistroVPP) {
        this.backUpSecRegistroVPP = backUpSecRegistroVPP;
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
        getVigenciaLocalizaciones();
        if (vigenciaLocalizaciones != null) {
            int tam = vigenciaLocalizaciones.size();
            if (tam > 0) {
                vigenciaLocalizacionSeleccionada = vigenciaLocalizaciones.get(0);
            }
        }
        return vigenciaLocalizacionSeleccionada;
    }

    public void setVigenciaLocalizacionSeleccionada(VigenciasLocalizaciones vigenciaLocalizacionSeleccionada) {
        this.vigenciaLocalizacionSeleccionada = vigenciaLocalizacionSeleccionada;
    }

    public VigenciasProrrateos getVigenciaProrrateoSeleccionada() {
        getVigenciasProrrateosCentroC();
        if (vigenciasProrrateosCentroC != null) {
            int tam = vigenciasProrrateosCentroC.size();
            if (tam > 0) {
                vigenciaProrrateoSeleccionada = vigenciasProrrateosCentroC.get(0);
            }
        }
        return vigenciaProrrateoSeleccionada;
    }

    public void setVigenciaProrrateoSeleccionada(VigenciasProrrateos vigenciaProrrateoSeleccionada) {
        this.vigenciaProrrateoSeleccionada = vigenciaProrrateoSeleccionada;
    }

    public VigenciasProrrateosProyectos getVigenciaProrrateoProyectoSeleccionada() {
        getVigenciasProrrateosProyectos();
        if (vigenciasProrrateosProyectos != null) {
            int tam = vigenciasProrrateosProyectos.size();
            if (tam > 0) {
                vigenciaProrrateoProyectoSeleccionada = vigenciasProrrateosProyectos.get(0);
            }
        }
        return vigenciaProrrateoProyectoSeleccionada;
    }

    public void setVigenciaProrrateoProyectoSeleccionada(VigenciasProrrateosProyectos vigenciaProrrateoProyectoSeleccionada) {
        this.vigenciaProrrateoProyectoSeleccionada = vigenciaProrrateoProyectoSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getInfoRegistroEstLocalizacion() {
        getListEstructurasCC();
        if (listEstructurasCC != null) {
            infoRegistroEstLocalizacion = "Cantidad de registros: " + listEstructurasCC.size();
        } else {
            infoRegistroEstLocalizacion = "Cantidad de registros: 0";
        }
        return infoRegistroEstLocalizacion;
    }

    public void setInfoRegistroEstLocalizacion(String infoRegistroLocalizacion) {
        this.infoRegistroEstLocalizacion = infoRegistroLocalizacion;
    }

    public String getInfoRegistroMotivoLocalizacion() {
        getListMotivosLocalizaciones();
        if (listMotivosLocalizaciones != null) {
            infoRegistroMotivoLocalizacion = "Cantidad de registros: " + listMotivosLocalizaciones.size();
        } else {
            infoRegistroMotivoLocalizacion = "Cantidad de registros: 0";
        }
        return infoRegistroMotivoLocalizacion;
    }

    public void setInfoRegistroMotivoLocalizacion(String infoRegistroMotivoLocalizacion) {
        this.infoRegistroMotivoLocalizacion = infoRegistroMotivoLocalizacion;
    }

    public String getInfoRegistroProyecto() {
        getListProyectos();
        if (listProyectos != null) {
            infoRegistroProyecto = "Cantidad de registros: " + listProyectos.size();
        } else {
            infoRegistroProyecto = "Cantidad de registros: 0";
        }
        return infoRegistroProyecto;
    }

    public void setInfoRegistroProyecto(String infoRegistroProyecto) {
        this.infoRegistroProyecto = infoRegistroProyecto;
    }

    public String getInfoRegistroCentroCosto() {
        getListCentrosCostos();
        if (listCentrosCostos != null) {
            infoRegistroCentroCosto = "Cantidad de registros: " + listCentrosCostos.size();
        } else {
            infoRegistroCentroCosto = "Cantidad de registros: 0";
        }
        return infoRegistroCentroCosto;
    }

    public void setInfoRegistroCentroCosto(String infoRegistroCentroCosto) {
        this.infoRegistroCentroCosto = infoRegistroCentroCosto;
    }

    public String getInfoRegistroProyectoVP() {
        getListProyectos();
        if (listProyectos != null) {
            infoRegistroProyectoVP = "Cantidad de registros: " + listProyectos.size();
        } else {
            infoRegistroProyectoVP = "Cantidad de registros: 0";
        }
        return infoRegistroProyectoVP;
    }

    public void setInfoRegistroProyectoVP(String infoRegistroProyectoVP) {
        this.infoRegistroProyectoVP = infoRegistroProyectoVP;
    }

    public String getInfoRegistroProyectoVPP() {
        getListProyectos();
        if (listProyectos != null) {
            infoRegistroProyectoVPP = "Cantidad de registros: " + listProyectos.size();
        } else {
            infoRegistroProyectoVPP = "Cantidad de registros: 0";
        }
        return infoRegistroProyectoVPP;
    }

    public void setInfoRegistroProyectoVPP(String infoRegistroProyectoVPP) {
        this.infoRegistroProyectoVPP = infoRegistroProyectoVPP;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
}

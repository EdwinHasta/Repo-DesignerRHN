package Controlador;

import Entidades.Empleados;
import Entidades.MotivosCambiosSueldos;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasSueldos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasSueldosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlVigenciaSueldo implements Serializable {

    @EJB
    AdministrarVigenciasSueldosInterface administrarVigenciasSueldos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Sueldos
    private List<VigenciasSueldos> listVigenciasSueldos;
    private List<VigenciasSueldos> filtrarVigenciasSueldos;
    private VigenciasSueldos vigenciaSueldoSeleccionada;
    //Vigencias Afiliaciones
    private List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    private List<VigenciasAfiliaciones> filtrarVigenciasAfiliaciones;
    private VigenciasAfiliaciones vigenciaAfiliacioneSeleccionada;
    //Tipo Sueldo
    private List<TiposSueldos> listTiposSueldos;
    private TiposSueldos tipoSueldoSeleccionado;
    private List<TiposSueldos> filtrarTiposSueldos;
    //Motivo Cambio Sueldo
    private List<MotivosCambiosSueldos> listMotivosCambiosSueldos;
    private MotivosCambiosSueldos motivoCambioSueldoSeleccionado;
    private List<MotivosCambiosSueldos> filtrarMotivosCambiosSueldos;
    //Tipos Entidades
    private List<TiposEntidades> listTiposEntidades;
    private TiposEntidades tipoEntidadSeleccionado;
    private List<TiposEntidades> filtrarTiposEntidades;
    //Terceros
    private List<Terceros> listTerceros;
    private Terceros terceroSeleccionado;
    private List<Terceros> filtrarTerceros;
    //Empleado
    private Empleados empleado;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Activo/Desactivo VP Crtl + F11
    private int banderaVA;
    //Columnas Tabla VL
    private Column vSFechaVigencia, vSMotivoCambioSueldo, vSTipoSueldo, vSVigenciaRetroactivo, vSValor, vSObservaciones, vSRetroactivo;
    //Columnas Tabla VP
    private Column vAFechaInicial, vAFechaFinal, vATercero, vATipoEntidad, vAValor;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasSueldos> listVSModificar;
    private List<VigenciasAfiliaciones> listVAModificar;
    private boolean guardado, guardarOk;
    //crear VL
    public VigenciasSueldos nuevaVigencia;
    //crear VP
    public VigenciasAfiliaciones nuevaVigenciaA;
    private List<VigenciasSueldos> listVSCrear;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<VigenciasSueldos> listVSBorrar;
    private List<VigenciasAfiliaciones> listVABorrar;
    //editar celda
    private VigenciasSueldos editarVS;
    private VigenciasAfiliaciones editarVA;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasSueldos duplicarVS;
    //Autocompletar
    private boolean permitirIndex, permitirIndexVA;
    //Variables Autompletar
    private String motivoCambioSueldo, tiposEntidades, tiposSueldos, terceros;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexVA;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaVA, tipoListaVA;
    //Index Auxiliar Para Nuevos Registros
    private int indexAuxVS;
    //Duplicar Vigencia Prorrateo
    private VigenciasAfiliaciones duplicarVA;
    private List<VigenciasAfiliaciones> listVACrear;
    private String nombreXML;
    private String nombreTabla;
    //Banderas Boolean de operaciones sobre vigencias prorrateos y vigencias prorrateos proyectos
    private boolean cambioVigenciaA;
    //Retroactivo o no para prestaciones
    private boolean retroactivo;
    //Mostrar todos o actual
    private boolean mostrarActual;
    //valor total consultar actual
    private int valorTotal;
    //bandera cambios vigencias sueldos
    private boolean cambiosVS;
    private BigInteger secRegistroVS;
    private BigInteger backUpSecRegistroVS;
    private BigInteger secRegistroVA;
    private BigInteger backUpSecRegistroVA;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private Date fechaParametro, fechaVig, fechaRetro, fechaIni, fechaFin;
    private String altoTabla1, altoTabla2;

    public ControlVigenciaSueldo() {

        nombreTablaRastro = "";
        backUp = null;
        listVigenciasAfiliaciones = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroVS = null;
        backUpSecRegistroVS = null;
        secRegistroVA = null;
        backUpSecRegistroVA = null;
        listTiposSueldos = null;
        listVigenciasSueldos = null;
        listMotivosCambiosSueldos = new ArrayList<MotivosCambiosSueldos>();
        listTiposEntidades = new ArrayList<TiposEntidades>();
        empleado = new Empleados();
        listTerceros = new ArrayList<Terceros>();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVSBorrar = new ArrayList<VigenciasSueldos>();
        listVABorrar = new ArrayList<VigenciasAfiliaciones>();
        //crear aficiones
        listVSCrear = new ArrayList<VigenciasSueldos>();
        k = 0;
        //modificar aficiones
        listVSModificar = new ArrayList<VigenciasSueldos>();
        listVAModificar = new ArrayList<VigenciasAfiliaciones>();
        //editar
        editarVS = new VigenciasSueldos();
        editarVA = new VigenciasAfiliaciones();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        tipoListaVA = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasSueldos();
        nuevaVigencia.setMotivocambiosueldo(new MotivosCambiosSueldos());
        nuevaVigencia.setTiposueldo(new TiposSueldos());
        index = -1;
        bandera = 0;
        banderaVA = 0;
        permitirIndex = true;
        permitirIndexVA = true;
        indexVA = -1;
        cualCeldaVA = -1;

        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        listVACrear = new ArrayList<VigenciasAfiliaciones>();

        nombreTabla = ":formExportarVS:datosVSEmpleadoExportar";
        nombreXML = "VigenciasSueldosXML";

        cambioVigenciaA = false;

        retroactivo = true;
        duplicarVS = new VigenciasSueldos();
        mostrarActual = false;
        cambiosVS = false;
        altoTabla1 = "115";
        altoTabla2 = "115";

    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasSueldos.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
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
        listVigenciasSueldos = null;
        empleado = empl;
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVS(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
            if (!listVSCrear.contains(listVigenciasSueldos.get(indice))) {
                if (listVSModificar.isEmpty()) {
                    listVSModificar.add(listVigenciasSueldos.get(indice));
                } else if (!listVSModificar.contains(listVigenciasSueldos.get(indice))) {
                    listVSModificar.add(listVigenciasSueldos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistroVS = null;
        } else {
            if (!listVSCrear.contains(filtrarVigenciasSueldos.get(indice))) {
                if (listVSModificar.isEmpty()) {
                    listVSModificar.add(filtrarVigenciasSueldos.get(indice));
                } else if (!listVSModificar.contains(filtrarVigenciasSueldos.get(indice))) {
                    listVSModificar.add(filtrarVigenciasSueldos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistroVS = null;
        }
        cambiosVS = true;
        getValorTotal();
        context.update("form:totalConsultarActual");
        context.update("form:valorConsultarActual");

    }

    public boolean validarFechasRegistroVigenciaSueldo(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasSueldos auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasSueldos.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarVigenciasSueldos.get(index);
            }
            if (auxiliar.getFechavigencia().after(fechaParametro) && auxiliar.getFechavigenciaretroactivo().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaVigencia.getFechavigencia().after(fechaParametro) && nuevaVigencia.getFechavigenciaretroactivo().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarVS.getFechavigencia().after(fechaParametro) && duplicarVS.getFechavigenciaretroactivo().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechasVigenciaSueldo(int i, int c) {
        VigenciasSueldos auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasSueldos.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarVigenciasSueldos.get(i);
        }
        if (auxiliar.getFechavigencia() != null && auxiliar.getFechavigenciaretroactivo() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaSueldo(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarVS(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasSueldos.get(i).setFechavigencia(fechaVig);
                    listVigenciasSueldos.get(i).setFechavigenciaretroactivo(fechaRetro);
                }
                if (tipoLista == 1) {
                    filtrarVigenciasSueldos.get(i).setFechavigencia(fechaVig);
                    filtrarVigenciasSueldos.get(i).setFechavigenciaretroactivo(fechaRetro);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVSEmpleado");
                context.execute("errorFechasVS.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasSueldos.get(i).setFechavigencia(fechaVig);
                listVigenciasSueldos.get(i).setFechavigenciaretroactivo(fechaRetro);
            }
            if (tipoLista == 1) {
                filtrarVigenciasSueldos.get(i).setFechavigencia(fechaVig);
                filtrarVigenciasSueldos.get(i).setFechavigenciaretroactivo(fechaRetro);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSEmpleado");
            context.execute("negacionNuevaS.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasLocalizaciones de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     * @param confirmarCambio
     * @param valorConfirmar
     */
    public void modificarVS(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MOTIVOCAMBIOSUELDO")) {
            if (tipoLista == 0) {
                listVigenciasSueldos.get(indice).getMotivocambiosueldo().setNombre(motivoCambioSueldo);
            } else {
                filtrarVigenciasSueldos.get(indice).getMotivocambiosueldo().setNombre(motivoCambioSueldo);
            }
            for (int i = 0; i < listMotivosCambiosSueldos.size(); i++) {
                if (listMotivosCambiosSueldos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasSueldos.get(indice).setMotivocambiosueldo(listMotivosCambiosSueldos.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasSueldos.get(indice).setMotivocambiosueldo(listMotivosCambiosSueldos.get(indiceUnicoElemento));
                }
                listMotivosCambiosSueldos.clear();
                getListMotivosCambiosSueldos();
            } else {
                permitirIndex = false;
                context.update("form:MotivoCambioSueldoDialogo");
                context.execute("MotivoCambioSueldoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TIPOSUELDO")) {
            if (tipoLista == 0) {
                listVigenciasSueldos.get(indice).getTiposueldo().setDescripcion(tiposSueldos);
            } else {
                filtrarVigenciasSueldos.get(indice).getTiposueldo().setDescripcion(tiposSueldos);
            }
            for (int i = 0; i < listTiposSueldos.size(); i++) {
                if (listTiposSueldos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasSueldos.get(indice).setTiposueldo(listTiposSueldos.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasSueldos.get(indice).setTiposueldo(listTiposSueldos.get(indiceUnicoElemento));
                }
                listTiposSueldos.clear();
                getListTiposSueldos();
            } else {
                permitirIndex = false;
                context.update("form:TipoSueldoDialogo");
                context.execute("TipoSueldoDialogo.show()");
                tipoActualizacion = 0;
            }

        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVSCrear.contains(listVigenciasSueldos.get(indice))) {

                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(listVigenciasSueldos.get(indice));
                    } else if (!listVSModificar.contains(listVigenciasSueldos.get(indice))) {
                        listVSModificar.add(listVigenciasSueldos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistroVS = null;
            } else {
                if (!listVSCrear.contains(filtrarVigenciasSueldos.get(indice))) {

                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(indice));
                    } else if (!listVSModificar.contains(filtrarVigenciasSueldos.get(indice))) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistroVS = null;
            }
            cambiosVS = true;
        }
        context.update("form:datosVSEmpleado");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVA(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaVA == 0) {
            if (!listVACrear.contains(listVigenciasAfiliaciones.get(indice))) {
                if (listVAModificar.isEmpty()) {
                    listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indice))) {
                    listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaA = true;
            indexVA = -1;
            secRegistroVA = null;
        } else {
            if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {

                if (listVAModificar.isEmpty()) {
                    listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                    listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            cambioVigenciaA = true;
            indexVA = -1;
            secRegistroVA = null;
        }

    }

    public boolean validarFechasRegistroVigenciaAfiliaciones(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasAfiliaciones auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasAfiliaciones.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarVigenciasAfiliaciones.get(index);
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
            if (nuevaVigenciaA.getFechafinal() != null) {
                if (nuevaVigenciaA.getFechainicial().after(fechaParametro) && nuevaVigenciaA.getFechainicial().before(nuevaVigenciaA.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaVigenciaA.getFechafinal() == null) {
                if (nuevaVigenciaA.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVA.getFechafinal() != null) {
                if (duplicarVA.getFechainicial().after(fechaParametro) && duplicarVA.getFechainicial().before(duplicarVA.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarVA.getFechafinal() == null) {
                if (duplicarVA.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechasVigenciaAfiliaciones(int i, int c) {
        VigenciasAfiliaciones auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasAfiliaciones.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarVigenciasAfiliaciones.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaAfiliaciones(i);
            if (retorno == true) {
                cambiarIndiceVA(i, c);
                modificarVA(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVAVigencia");
                context.execute("errorFechasVA.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            context.execute("negacionNuevaVA.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciaProrrateo
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVA(int indice, String confirmarCambio, String valorConfirmar) {
        indexVA = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOENTIDAD")) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
            } else {
                filtrarVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
            }
            for (int i = 0; i < listTiposEntidades.size(); i++) {
                if (listTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVA == 0) {
                    cambioVigenciaA = true;
                    listVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaA = true;
                    filtrarVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                }
                listTiposEntidades.clear();
                getListTiposEntidades();
            } else {
                permitirIndexVA = false;
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCEROS")) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNombre(terceros);
            } else {
                filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNombre(terceros);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVA == 0) {
                    cambioVigenciaA = true;
                    listVigenciasAfiliaciones.get(indice).getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaA = true;
                    filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                }
                listTerceros.clear();
                getListTerceros();
            } else {
                permitirIndexVA = false;
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVA == 0) {
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indice))) {

                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistroVA = null;
            } else {
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {

                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistroVA = null;
            }
            cambioVigenciaA = true;
        }
        context.update("form:datosVAVigencia");
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaLocalizacion)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("MOTIVOCAMBIOSUELDO")) {
            if (tipoNuevo == 1) {
                motivoCambioSueldo = nuevaVigencia.getMotivocambiosueldo().getNombre();
            } else if (tipoNuevo == 2) {
                motivoCambioSueldo = duplicarVS.getMotivocambiosueldo().getNombre();
            }
        } else if (Campo.equals("TIPOSUELDO")) {
            if (tipoNuevo == 1) {
                tiposSueldos = nuevaVigencia.getTiposueldo().getDescripcion();
            } else if (tipoNuevo == 2) {
                tiposSueldos = duplicarVS.getTiposueldo().getDescripcion();
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
        if (confirmarCambio.equalsIgnoreCase("MOTIVOCAMBIOSUELDO")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getMotivocambiosueldo().setNombre(motivoCambioSueldo);
            } else if (tipoNuevo == 2) {
                duplicarVS.getMotivocambiosueldo().setNombre(motivoCambioSueldo);
            }
            for (int i = 0; i < listMotivosCambiosSueldos.size(); i++) {
                if (listMotivosCambiosSueldos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setMotivocambiosueldo(listMotivosCambiosSueldos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivoCambioSueldo");
                } else if (tipoNuevo == 2) {
                    duplicarVS.setMotivocambiosueldo(listMotivosCambiosSueldos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivoCambioSueldo");
                }
                listMotivosCambiosSueldos.clear();
                getListMotivosCambiosSueldos();
            } else {
                context.update("form:MotivoCambioSueldoDialogo");
                context.execute("MotivoCambioSueldoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaMotivoCambioSueldo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivoCambioSueldo");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPOSUELDO")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getTiposueldo().setDescripcion(tiposSueldos);
            } else if (tipoNuevo == 2) {
                duplicarVS.getTiposueldo().setDescripcion(tiposSueldos);
            }
            for (int i = 0; i < listTiposSueldos.size(); i++) {
                if (listTiposSueldos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setTiposueldo(listTiposSueldos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoSueldo");
                } else if (tipoNuevo == 2) {
                    duplicarVS.setTiposueldo(listTiposSueldos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoSueldo");
                }
                listTiposSueldos.clear();
                getListTiposSueldos();
            } else {
                context.update("form:TipoSueldoDialogo");
                context.execute("TipoSueldoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTipoSueldo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoSueldo");
                }
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
    public void valoresBackupAutocompletarVA(int tipoNuevo, String Campo) {

        if (Campo.equals("TIPOENTIDAD")) {
            if (tipoNuevo == 1) {
                tiposEntidades = nuevaVigenciaA.getTipoentidad().getNombre();
            } else if (tipoNuevo == 2) {
                tiposEntidades = duplicarVA.getTipoentidad().getNombre();
            }
        } else if (Campo.equals("TERCERO")) {
            if (tipoNuevo == 1) {
                terceros = nuevaVigenciaA.getTercerosucursal().getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                terceros = duplicarVA.getTercerosucursal().getTercero().getNombre();
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
    public void autocompletarNuevoyDuplicadoVA(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOENTIDAD")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaA.getTipoentidad().setNombre(tiposEntidades);
            } else if (tipoNuevo == 2) {
                duplicarVA.getTipoentidad().setNombre(tiposEntidades);
            }
            for (int i = 0; i < listTiposEntidades.size(); i++) {
                if (listTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaA.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoEntidadVA");
                } else if (tipoNuevo == 2) {
                    duplicarVA.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoEntidadVA");
                }
                listTiposEntidades.clear();
                getListTiposEntidades();
            } else {
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTipoEntidadVA");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoEntidadVA");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaA.getTercerosucursal().getTercero().setNombre(terceros);
            } else if (tipoNuevo == 2) {
                duplicarVA.getTercerosucursal().getTercero().setNombre(terceros);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaA.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTerceroVA");
                } else if (tipoNuevo == 2) {
                    duplicarVA.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTerceroVA");
                }
                listTerceros.clear();
                getListTerceros();
            } else {
                context.update("form:TerceroDialogoVA");
                context.execute("TerceroDialogoVA.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTerceroVA");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicadoTerceroVA");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            if (cambioVigenciaA == false) {
                cualCelda = celda;
                index = indice;
                indexAuxVS = indice;
                if (tipoLista == 0) {
                    fechaRetro = listVigenciasSueldos.get(index).getFechavigenciaretroactivo();
                    fechaVig = listVigenciasSueldos.get(index).getFechavigencia();
                    secRegistroVS = listVigenciasSueldos.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        motivoCambioSueldo = listVigenciasSueldos.get(index).getMotivocambiosueldo().getNombre();
                    } else if (cualCelda == 2) {
                        tiposSueldos = listVigenciasSueldos.get(index).getTiposueldo().getDescripcion();
                    }
                }
                if (tipoLista == 1) {
                    fechaRetro = filtrarVigenciasSueldos.get(index).getFechavigenciaretroactivo();
                    fechaVig = filtrarVigenciasSueldos.get(index).getFechavigencia();
                    secRegistroVS = filtrarVigenciasSueldos.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        motivoCambioSueldo = filtrarVigenciasSueldos.get(index).getMotivocambiosueldo().getNombre();
                    } else if (cualCelda == 2) {
                        tiposSueldos = filtrarVigenciasSueldos.get(index).getTiposueldo().getDescripcion();
                    }
                }
                getListVigenciasAfiliaciones();

                if (cambioVigenciaA == true) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("formularioDialogos:guardarCambiosVigenciasAfiliaciones");
                    context.execute("guardarCambiosVigenciasAfiliaciones.show()");

                }
            }

            if (banderaVA == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                vATercero.setFilterStyle("display: none; visibility: hidden;");
                vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
                vAValor.setFilterStyle("display: none; visibility: hidden;");
                altoTabla2 = "115";
                RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                banderaVA = 0;
                filtrarVigenciasAfiliaciones = null;
                tipoListaVA = 0;
            }
            indexVA = -1;
            secRegistroVA = null;
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
        }
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVA(int indice, int celda) {
        if (permitirIndexVA == true) {
            indexVA = indice;
            cualCeldaVA = celda;
            if (tipoListaVA == 0) {
                fechaFin = listVigenciasAfiliaciones.get(indexVA).getFechafinal();
                fechaIni = listVigenciasAfiliaciones.get(indexVA).getFechainicial();
                secRegistroVA = listVigenciasAfiliaciones.get(indexVA).getSecuencia();
                if (cualCeldaVA == 2) {
                    terceros = listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNombre();
                }
                if (cualCeldaVA == 3) {
                    tiposEntidades = listVigenciasAfiliaciones.get(indexVA).getTipoentidad().getNombre();
                }
                if (tipoListaVA == 1) {
                    fechaFin = filtrarVigenciasAfiliaciones.get(indexVA).getFechafinal();
                    fechaIni = filtrarVigenciasAfiliaciones.get(indexVA).getFechainicial();
                    secRegistroVA = filtrarVigenciasAfiliaciones.get(indexVA).getSecuencia();
                    if (cualCeldaVA == 2) {
                        terceros = filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNombre();
                    }
                    if (cualCeldaVA == 3) {
                        tiposEntidades = filtrarVigenciasAfiliaciones.get(indexVA).getTipoentidad().getNombre();
                    }
                }
            }
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
                vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
                vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
                vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
                vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
                vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
                vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
                vSValor.setFilterStyle("display: none; visibility: hidden;");
                vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
                vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
                vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
                vSRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                altoTabla1 = "115";
                RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
                bandera = 0;
                filtrarVigenciasSueldos = null;
                tipoLista = 0;
            }
            index = -1;
            secRegistroVS = null;
        }
    }

    public void guardadoGeneral() {
        guardarCambiosVS();
        if (listVigenciasAfiliaciones != null) {
            guardarCambiosVA();
        }
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosVS() {
        if (guardado == false) {
            if (!listVSBorrar.isEmpty()) {
                for (int i = 0; i < listVSBorrar.size(); i++) {
                    if (listVSBorrar.get(i).getMotivocambiosueldo().getSecuencia() == null) {
                        listVSBorrar.get(i).setMotivocambiosueldo(null);
                    }
                    if (listVSBorrar.get(i).getTiposueldo().getSecuencia() == null) {
                        listVSBorrar.get(i).setTiposueldo(null);
                    }
                    administrarVigenciasSueldos.borrarVS(listVSBorrar.get(i));
                }
                listVSBorrar.clear();
            }
            if (!listVSCrear.isEmpty()) {
                for (int i = 0; i < listVSCrear.size(); i++) {
                    if (listVSCrear.get(i).getMotivocambiosueldo().getSecuencia() == null) {
                        listVSCrear.get(i).setMotivocambiosueldo(null);
                    }
                    if (listVSCrear.get(i).getTiposueldo().getSecuencia() == null) {
                        listVSCrear.get(i).setTiposueldo(null);
                    }
                    administrarVigenciasSueldos.crearVS(listVSCrear.get(i));
                }
                listVSCrear.clear();
            }
            if (!listVSModificar.isEmpty()) {
                administrarVigenciasSueldos.modificarVS(listVSModificar);
                listVSModificar.clear();
            }
            listVigenciasSueldos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSEmpleado");
            context.update("form:ACEPTAR");
            k = 0;

        }
        index = -1;
        secRegistroVS = null;

    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVA() {
        if (guardado == false) {
            if (!listVABorrar.isEmpty()) {
                for (int i = 0; i < listVABorrar.size(); i++) {
                    if (listVABorrar.get(i).getTipoentidad().getSecuencia() == null) {
                        listVABorrar.get(i).setTipoentidad(null);
                    }
                    if (listVABorrar.get(i).getTercerosucursal().getTercero().getSecuencia() == null) {
                        listVABorrar.get(i).getTercerosucursal().setTercero(null);
                    }
                    administrarVigenciasSueldos.borrarVA(listVABorrar.get(i));
                }
                listVABorrar.clear();
            }
            if (!listVACrear.isEmpty()) {
                for (int i = 0; i < listVACrear.size(); i++) {
                    if (listVACrear.get(i).getTipoentidad().getSecuencia() == null) {
                        listVACrear.get(i).setTipoentidad(null);
                    }
                    if (listVACrear.get(i).getTercerosucursal().getTercero().getSecuencia() == null) {
                        listVACrear.get(i).getTercerosucursal().setTercero(null);
                    }
                    administrarVigenciasSueldos.crearVA(listVACrear.get(i));
                }
                listVACrear.clear();
            }
            if (!listVAModificar.isEmpty()) {
                administrarVigenciasSueldos.modificarVA(listVAModificar);
                listVAModificar.clear();
            }
            listVigenciasAfiliaciones = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            context.update("form:ACEPTAR");
            k = 0;
        }
        cambioVigenciaA = false;
        indexVA = -1;
        secRegistroVA = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
            vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
            vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
            vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
            vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
            vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
            vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
            vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
            vSValor.setFilterStyle("display: none; visibility: hidden;");
            vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
            vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
            vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
            vSRetroactivo.setFilterStyle("display: none; visibility: hidden;");
            altoTabla1 = "115";
            RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
            bandera = 0;
            filtrarVigenciasSueldos = null;
            tipoLista = 0;
        }
        if (banderaVA == 1) {
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
            vAValor.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        listVSBorrar.clear();
        listVABorrar.clear();
        listVSCrear.clear();
        listVACrear.clear();
        listVSModificar.clear();
        listVAModificar.clear();
        index = -1;
        secRegistroVS = null;
        indexVA = -1;
        secRegistroVA = null;
        k = 0;
        listVigenciasSueldos = null;
        listVigenciasAfiliaciones = null;
        cambioVigenciaA = false;
        mostrarActual = false;
        guardado = true;
        cambiosVS = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVSEmpleado");
        context.update("form:totalConsultarActual");
        context.update("form:valorConsultarActual");
        context.update("form:datosVAVigencia");
        context.update("form:ACEPTAR");
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVA() {
        if (banderaVA == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
            vAValor.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        listVABorrar.clear();
        listVACrear.clear();
        listVAModificar.clear();
        indexVA = -1;
        secRegistroVA = null;
        k = 0;
        listVigenciasAfiliaciones = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVAVigencia");
        context.update("form:ACEPTAR");
        cambioVigenciaA = false;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVS = listVigenciasSueldos.get(index);
            }
            if (tipoLista == 1) {
                editarVS = filtrarVigenciasSueldos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaVigenciaD");
                context.execute("editarFechaVigenciaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarMotivoCambioSueldoD");
                context.execute("editarMotivoCambioSueldoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoSueldoD");
                context.execute("editarTipoSueldoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarVigenciaRetroactivo");
                context.execute("editarVigenciaRetroactivo.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarValorD");
                context.execute("editarValorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarObservacionesD");
                context.execute("editarObservacionesD.show()");
                cualCelda = -1;
            }
        }
        if (indexVA >= 0) {
            if (tipoListaVA == 0) {
                editarVA = listVigenciasAfiliaciones.get(indexVA);
            }
            if (tipoListaVA == 1) {
                editarVA = filtrarVigenciasAfiliaciones.get(indexVA);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaVA == 0) {
                context.update("formularioDialogos:editarFechaInicialVAD");
                context.execute("editarFechaInicialVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 1) {
                context.update("formularioDialogos:editarFechaFinalVAD");
                context.execute("editarFechaFinalVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 2) {
                context.update("formularioDialogos:editarTerceroVAD");
                context.execute("editarTerceroVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 3) {
                context.update("formularioDialogos:editarTipoEntidadVAD");
                context.execute("editarTipoEntidadVAD"
                        + ".show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 4) {
                context.update("formularioDialogos:editarValorVAD");
                context.execute("editarValorVAD.show()");
                cualCeldaVA = -1;
            }
        }
        index = -1;
        secRegistroVS = null;
        indexVA = -1;
        secRegistroVA = null;
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevaVS() {
        if ((nuevaVigencia.getFechavigencia() != null) && (nuevaVigencia.getValor() != null) && (nuevaVigencia.getFechavigenciaretroactivo() != null)
                && (nuevaVigencia.getTiposueldo().getSecuencia() != null) && (nuevaVigencia.getMotivocambiosueldo().getSecuencia() != null)) {
            if (validarFechasRegistroVigenciaSueldo(1) == true) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
                    vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
                    vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
                    vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
                    vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
                    vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
                    vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                    vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
                    vSValor.setFilterStyle("display: none; visibility: hidden;");
                    vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
                    vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
                    vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
                    vSRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla1 = "115";
                    RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
                    bandera = 0;
                    filtrarVigenciasSueldos = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                k++;
                BigInteger var = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(var);
                nuevaVigencia.setEmpleado(empleado);
                listVSCrear.add(nuevaVigencia);

                listVigenciasSueldos.add(nuevaVigencia);
                nuevaVigencia = new VigenciasSueldos();
                nuevaVigencia.setMotivocambiosueldo(new MotivosCambiosSueldos());
                nuevaVigencia.setTiposueldo(new TiposSueldos());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVSEmpleado");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.execute("NuevoRegistroVS.hide()");
                if (mostrarActual == true) {
                    getValorTotal();
                    context.update("form:totalConsultarActual");
                    context.update("form:valorConsultarActual");
                }
                cambiosVS = true;
                index = -1;
                secRegistroVS = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVS.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaS.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVS() {
        nuevaVigencia = new VigenciasSueldos();
        nuevaVigencia.setMotivocambiosueldo(new MotivosCambiosSueldos());
        nuevaVigencia.setTiposueldo(new TiposSueldos());
        index = -1;
        secRegistroVS = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVA() {
        if (nuevaVigenciaA.getFechainicial() != null && nuevaVigenciaA.getTercerosucursal().getTercero().getSecuencia() != null && nuevaVigenciaA.getTipoentidad().getSecuencia() != null) {
            if (validarFechasRegistroVigenciaAfiliaciones(1) == true) {
                cambioVigenciaA = true;
                //CERRAR FILTRADO
                if (banderaVA == 1) {
                    FacesContext c = FacesContext.getCurrentInstance();
                    vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                    vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                    vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                    vATercero.setFilterStyle("display: none; visibility: hidden;");
                    vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                    vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                    vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
                    vAValor.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla2 = "115";
                    RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                    banderaVA = 0;
                    filtrarVigenciasAfiliaciones = null;
                    tipoListaVA = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                k++;
                l = BigInteger.valueOf(k);
                BigDecimal var = BigDecimal.valueOf(k);
                nuevaVigenciaA.setSecuencia(l);
                nuevaVigenciaA.setEmpleado(empleado);
                nuevaVigenciaA.setVigenciasueldo(listVigenciasSueldos.get(indexAuxVS));
                listVACrear.add(nuevaVigenciaA);
                //
                nuevaVigenciaA = new VigenciasAfiliaciones();
                nuevaVigenciaA.setTipoentidad(new TiposEntidades());
                nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
                index = indexAuxVS;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVAVigencia");
                context.execute("NuevoRegistroVA.hide()");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexVA = -1;
                secRegistroVA = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVA.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaVA.show()");
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaVA() {
        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        indexVA = -1;
        secRegistroVA = null;
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        if (index >= 0) {
            duplicarVigenciaS();
        }
        if (indexVA >= 0) {
            duplicarVigenciaA();
        }
    }

    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarVigenciaS() {
        if (index >= 0) {
            duplicarVS = new VigenciasSueldos();
            k++;
            BigInteger var = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarVS.setSecuencia(var);
                duplicarVS.setEmpleado(listVigenciasSueldos.get(index).getEmpleado());
                duplicarVS.setFechavigencia(listVigenciasSueldos.get(index).getFechavigencia());
                duplicarVS.setMotivocambiosueldo(listVigenciasSueldos.get(index).getMotivocambiosueldo());
                duplicarVS.setTiposueldo(listVigenciasSueldos.get(index).getTiposueldo());
                duplicarVS.setRetroactivo(listVigenciasSueldos.get(index).getRetroactivo());
                if (listVigenciasSueldos.get(index).getRetroactivo() == null || listVigenciasSueldos.get(index).getRetroactivo().equals("N")) {
                    duplicarVS.setCheckRetroactivo(false);
                } else {
                    duplicarVS.setCheckRetroactivo(true);
                }
                duplicarVS.setFechavigenciaretroactivo(listVigenciasSueldos.get(index).getFechavigenciaretroactivo());
                duplicarVS.setValor(listVigenciasSueldos.get(index).getValor());
                duplicarVS.setObservaciones(listVigenciasSueldos.get(index).getObservaciones());
            }
            if (tipoLista == 1) {
                duplicarVS.setSecuencia(var);
                duplicarVS.setEmpleado(filtrarVigenciasSueldos.get(index).getEmpleado());
                duplicarVS.setFechavigencia(filtrarVigenciasSueldos.get(index).getFechavigencia());
                duplicarVS.setMotivocambiosueldo(filtrarVigenciasSueldos.get(index).getMotivocambiosueldo());
                duplicarVS.setTiposueldo(filtrarVigenciasSueldos.get(index).getTiposueldo());
                duplicarVS.setRetroactivo(filtrarVigenciasSueldos.get(index).getRetroactivo());
                if (filtrarVigenciasSueldos.get(index).getRetroactivo() == null || filtrarVigenciasSueldos.get(index).getRetroactivo().equals("N")) {
                    duplicarVS.setCheckRetroactivo(false);
                } else {
                    duplicarVS.setCheckRetroactivo(true);
                }
                duplicarVS.setFechavigenciaretroactivo(filtrarVigenciasSueldos.get(index).getFechavigenciaretroactivo());
                duplicarVS.setValor(filtrarVigenciasSueldos.get(index).getValor());
                duplicarVS.setObservaciones(filtrarVigenciasSueldos.get(index).getObservaciones());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVS");
            context.execute("DuplicarRegistroVS.show()");
            index = -1;
            secRegistroVS = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        if ((duplicarVS.getFechavigencia() != null) && (duplicarVS.getValor() != null) && (duplicarVS.getFechavigenciaretroactivo() != null)
                && (duplicarVS.getTiposueldo().getSecuencia() != null) && (duplicarVS.getMotivocambiosueldo().getSecuencia() != null)) {
            if (validarFechasRegistroVigenciaSueldo(2) == true) {
                listVigenciasSueldos.add(duplicarVS);
                listVSCrear.add(duplicarVS);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVSEmpleado");
                index = -1;
                secRegistroVS = null;
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
                    vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
                    vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
                    vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
                    vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
                    vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
                    vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                    vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
                    vSValor.setFilterStyle("display: none; visibility: hidden;");
                    vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
                    vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
                    vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
                    vSRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla1 = "115";
                    RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
                    bandera = 0;
                    filtrarVigenciasSueldos = null;
                    tipoLista = 0;
                }
                cambiosVS = true;
                context.execute("DuplicarRegistroVS.hide()");
                duplicarVS = new VigenciasSueldos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVS.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaS.show()");
        }
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia Localizacion
     */
    public void limpiarduplicarVS() {
        duplicarVS = new VigenciasSueldos();
        duplicarVS.setMotivocambiosueldo(new MotivosCambiosSueldos());
        duplicarVS.setTiposueldo(new TiposSueldos());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarVigenciaA() {

        if (indexVA >= 0) {
            duplicarVA = new VigenciasAfiliaciones();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);

            if (tipoListaVA == 0) {
                duplicarVA.setSecuencia(l);
                duplicarVA.setVigenciasueldo(listVigenciasSueldos.get(indexAuxVS));
                duplicarVA.setFechafinal(listVigenciasAfiliaciones.get(indexVA).getFechainicial());
                duplicarVA.setFechainicial(listVigenciasAfiliaciones.get(indexVA).getFechafinal());
                duplicarVA.setTercerosucursal(listVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
                duplicarVA.setTipoentidad(listVigenciasAfiliaciones.get(indexVA).getTipoentidad());
                duplicarVA.setValor(listVigenciasAfiliaciones.get(indexVA).getValor());

            }
            if (tipoListaVA == 1) {
                duplicarVA.setSecuencia(l);
                duplicarVA.setVigenciasueldo(listVigenciasSueldos.get(indexAuxVS));
                duplicarVA.setFechafinal(filtrarVigenciasAfiliaciones.get(indexVA).getFechainicial());
                duplicarVA.setFechainicial(filtrarVigenciasAfiliaciones.get(indexVA).getFechafinal());
                duplicarVA.setTercerosucursal(filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
                duplicarVA.setTipoentidad(filtrarVigenciasAfiliaciones.get(indexVA).getTipoentidad());
                duplicarVA.setValor(filtrarVigenciasAfiliaciones.get(indexVA).getValor());
            }
            cambioVigenciaA = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoVA");
            context.execute("DuplicadoRegistroVA.show()");
            indexVA = -1;
            secRegistroVA = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVA() {
        if (duplicarVA.getFechainicial() != null && duplicarVA.getTercerosucursal().getTercero().getSecuencia() != null && duplicarVA.getTipoentidad().getSecuencia() != null) {
            if (validarFechasRegistroVigenciaAfiliaciones(2) == true) {
                cambioVigenciaA = true;
                listVigenciasAfiliaciones.add(duplicarVA);
                listVACrear.add(duplicarVA);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVAVigencia");
                context.execute("DuplicadoRegistroVA.hide()");
                indexVA = -1;
                secRegistroVA = null;
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (banderaVA == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                    vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                    vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                    vATercero.setFilterStyle("display: none; visibility: hidden;");
                    vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                    vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                    vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
                    vAValor.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla2 = "115";
                    RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                    banderaVA = 0;
                    filtrarVigenciasAfiliaciones = null;
                    tipoListaVA = 0;
                }
                duplicarVA = new VigenciasAfiliaciones();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVA.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaVA.show()");
        }
    }

    /**
     * Limpia los elementos del duplicar registro Vigencia Prorrateo
     */
    public void limpiarduplicarVA() {
        duplicarVA = new VigenciasAfiliaciones();
        duplicarVA.setTipoentidad(new TiposEntidades());
        duplicarVA.setTercerosucursal(new TercerosSucursales());

    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoVigencia() {
        if (index >= 0) {
            if (listVigenciasAfiliaciones == null) {
                borrarVS();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:negacionBorradoVS");
                context.execute("negacionBorradoVS.show()");
            }
        }
        if (indexVA >= 0) {
            borrarVA();
        }
    }

    //BORRAR VL
    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarVS() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVSModificar.isEmpty() && listVSModificar.contains(listVigenciasSueldos.get(index))) {
                    int modIndex = listVSModificar.indexOf(listVigenciasSueldos.get(index));
                    listVSModificar.remove(modIndex);
                    listVSBorrar.add(listVigenciasSueldos.get(index));
                } else if (!listVSCrear.isEmpty() && listVSCrear.contains(listVigenciasSueldos.get(index))) {
                    int crearIndex = listVSCrear.indexOf(listVigenciasSueldos.get(index));
                    listVSCrear.remove(crearIndex);
                } else {
                    listVSBorrar.add(listVigenciasSueldos.get(index));
                }
                listVigenciasSueldos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVSModificar.isEmpty() && listVSModificar.contains(filtrarVigenciasSueldos.get(index))) {
                    int modIndex = listVSModificar.indexOf(filtrarVigenciasSueldos.get(index));
                    listVSModificar.remove(modIndex);
                    listVSBorrar.add(filtrarVigenciasSueldos.get(index));
                } else if (!listVSCrear.isEmpty() && listVSCrear.contains(filtrarVigenciasSueldos.get(index))) {
                    int crearIndex = listVSCrear.indexOf(filtrarVigenciasSueldos.get(index));
                    listVSCrear.remove(crearIndex);
                } else {
                    listVSBorrar.add(filtrarVigenciasSueldos.get(index));
                }
                int VLIndex = listVigenciasSueldos.indexOf(filtrarVigenciasSueldos.get(index));
                listVigenciasSueldos.remove(VLIndex);
                filtrarVigenciasSueldos.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSEmpleado");
            if (mostrarActual == true) {
                getValorTotal();
                context.update("form:totalConsultarActual");
                context.update("form:valorConsultarActual");
            }
            index = -1;
            secRegistroVS = null;
            cambiosVS = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarVA() {
        cambioVigenciaA = true;
        if (indexVA >= 0) {
            if (tipoListaVA == 0) {
                if (!listVAModificar.isEmpty() && listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    int modIndex = listVAModificar.indexOf(listVigenciasAfiliaciones.get(indexVA));
                    listVAModificar.remove(modIndex);
                    listVABorrar.add(listVigenciasAfiliaciones.get(indexVA));
                } else if (!listVACrear.isEmpty() && listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    int crearIndex = listVACrear.indexOf(listVigenciasAfiliaciones.get(indexVA));
                    listVACrear.remove(crearIndex);
                } else {
                    listVABorrar.add(listVigenciasAfiliaciones.get(indexVA));
                }
                listVigenciasAfiliaciones.remove(indexVA);
            }
            if (tipoListaVA == 1) {
                if (!listVAModificar.isEmpty() && listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                    int modIndex = listVAModificar.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
                    listVAModificar.remove(modIndex);
                    listVABorrar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                } else if (!listVACrear.isEmpty() && listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                    int crearIndex = listVACrear.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
                    listVACrear.remove(crearIndex);
                } else {
                    listVABorrar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                }
                int VPIndex = listVigenciasAfiliaciones.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
                listVigenciasAfiliaciones.remove(VPIndex);
                filtrarVigenciasAfiliaciones.remove(indexVA);
            }
            index = indexAuxVS;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSVigencia");
            indexVA = -1;
            secRegistroVA = null;
            if (guardado == true) {
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
            filtradoVigenciaSueldo();
        }
        if (indexVA >= 0) {
            filtradoVigenciaAfiliacion();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoVigenciaSueldo() {
        if (index >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 0) {
                vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
                vSFechaVigencia.setFilterStyle("width: 60px");
                vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
                vSMotivoCambioSueldo.setFilterStyle("width: 60px");
                vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
                vSTipoSueldo.setFilterStyle("width: 60px");
                vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
                vSVigenciaRetroactivo.setFilterStyle("width: 60px");
                vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
                vSValor.setFilterStyle("width: 60px");
                vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
                vSObservaciones.setFilterStyle("width: 60px");
                vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
                vSRetroactivo.setFilterStyle("width: 10px");
                altoTabla1 = "91";
                RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
                bandera = 1;
            } else if (bandera == 1) {
                vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
                vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
                vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
                vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
                vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
                vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
                vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
                vSValor.setFilterStyle("display: none; visibility: hidden;");
                vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
                vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
                vSRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSRetroactivo");
                vSRetroactivo.setFilterStyle("display: none; visibility: hidden;");
                altoTabla1 = "115";
                RequestContext.getCurrentInstance().update("form:datosVSEmpleado");
                bandera = 0;
                filtrarVigenciasSueldos = null;
                tipoLista = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaAfiliacion() {
        if (indexVA >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (banderaVA == 0) {
                //Columnas Tabla VPP
                vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                vAFechaInicial.setFilterStyle("width: 60px");
                vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                vAFechaFinal.setFilterStyle("width: 60px");
                vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                vATercero.setFilterStyle("width: 60px");
                vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                vATipoEntidad.setFilterStyle("width: 60px");
                vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
                vAValor.setFilterStyle("width: 60px");
                altoTabla2 = "91";
                RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                banderaVA = 1;
            } else if (banderaVA == 1) {
                vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                vATercero.setFilterStyle("display: none; visibility: hidden;");
                vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
                vAValor.setFilterStyle("display: none; visibility: hidden;");
                altoTabla2 = "115";
                RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                banderaVA = 0;
                filtrarVigenciasAfiliaciones = null;
                tipoListaVA = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            vSFechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSFechaVigencia");
            vSFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vSMotivoCambioSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSMotivoCambioSueldo");
            vSMotivoCambioSueldo.setFilterStyle("display: none; visibility: hidden;");
            vSTipoSueldo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSTipoSueldo");
            vSTipoSueldo.setFilterStyle("display: none; visibility: hidden;");
            vSVigenciaRetroactivo = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSVigenciaRetroactivo");
            vSVigenciaRetroactivo.setFilterStyle("display: none; visibility: hidden;");
            vSValor = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSValor");
            vSValor.setFilterStyle("display: none; visibility: hidden;");
            vSObservaciones = (Column) c.getViewRoot().findComponent("form:datosVSEmpleado:vSObservaciones");
            vSObservaciones.setFilterStyle("display: none; visibility: hidden;");
            altoTabla1 = "115";
            context.update("form:datosVSEmpleado");
            bandera = 0;
            filtrarVigenciasSueldos = null;
            tipoLista = 0;
        }
        if (banderaVA == 1) {
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vAValor = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAValor");
            vAValor.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            context.update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }

        listVSBorrar.clear();
        listVSCrear.clear();
        listVSModificar.clear();
        index = -1;
        secRegistroVS = null;
        indexVA = -1;
        secRegistroVA = null;
        k = 0;
        listVigenciasSueldos = null;
        listVigenciasAfiliaciones = null;
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
                context.update("form:MotivoCambioSueldoDialogo");
                context.execute("MotivoCambioSueldoDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:TipoSueldoDialogo");
                context.execute("TipoSueldoDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                indexVA = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
            }
        }
    }

    //LOVS
    //Estructuras
    /**
     * Metodo que actualiza la estructura seleccionada (vigencia localizacion)
     */
    public void actualizarMotivoCambioSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasSueldos.get(index).setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
                if (!listVSCrear.contains(listVigenciasSueldos.get(index))) {
                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(listVigenciasSueldos.get(index));
                    } else if (!listVSModificar.contains(listVigenciasSueldos.get(index))) {
                        listVSModificar.add(listVigenciasSueldos.get(index));
                    }
                }
            } else {
                filtrarVigenciasSueldos.get(index).setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
                if (!listVSCrear.contains(filtrarVigenciasSueldos.get(index))) {
                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(index));
                    } else if (!listVSModificar.contains(filtrarVigenciasSueldos.get(index))) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            cambiosVS = true;
            context.update(":form:editarMotivoCambioSueldo");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
            context.update("formularioDialogos:nuevaVS");
        } else if (tipoActualizacion == 2) {
            duplicarVS.setMotivocambiosueldo(motivoCambioSueldoSeleccionado);
            context.update("formularioDialogos:duplicarVS");
        }
        filtrarMotivosCambiosSueldos = null;
        motivoCambioSueldoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVS = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre estructura (vigencia localizacion)
     */
    public void cancelarCambioMotivoCambioSueldo() {
        filtrarMotivosCambiosSueldos = null;
        motivoCambioSueldoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVS = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarTipoEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    }
                }
            } else {
                filtrarVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaA = true;
            permitirIndex = true;
            context.update(":form:editarTipoEntidadVA");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaA.setTipoentidad(tipoEntidadSeleccionado);
            context.update("formularioDialogos:nuevaVA");
        } else if (tipoActualizacion == 2) {
            duplicarVA.setTipoentidad(tipoEntidadSeleccionado);
            context.update("formularioDialogos:duplicadoVA");
        }
        filtrarTiposEntidades = null;
        tipoEntidadSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistroVA = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioTipoEntidad() {
        filtrarTiposEntidades = null;
        tipoEntidadSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistroVA = null;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().setTercero(terceroSeleccionado);
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    }
                }
            } else {
                filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal().setTercero(terceroSeleccionado);
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            context.update(":form:editarTerceroVA");
        } else if (tipoActualizacion == 1) {
            boolean banderaEncuentra = false;
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasSueldos.tercerosSucursales(terceroSeleccionado.getSecuencia());
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getSecuencia().compareTo(terceroSeleccionado.getSecuencia()) == 0) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if ((banderaEncuentra == true) && (posicion != -1)) {
                nuevaVigenciaA.setTercerosucursal(listTercerosSucursales.get(posicion));
            }
            context.update("formularioDialogos:nuevaVA");
        } else if (tipoActualizacion == 2) {
            boolean banderaEncuentra = false;
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasSueldos.tercerosSucursales(terceroSeleccionado.getSecuencia());
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getSecuencia() == terceroSeleccionado.getSecuencia()) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if ((banderaEncuentra == true) && (posicion != -1)) {
                duplicarVA.setTercerosucursal(listTercerosSucursales.get(posicion));
            }
            context.update("formularioDialogos:duplicadoVA");
        }
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistroVA = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioTerceros() {
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistroVA = null;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Metodo que actualiza el centro costo seleccionado (vigencia prorrateo)
     */
    public void actualizarTipoSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaVA == 0) {
                listVigenciasSueldos.get(index).setTiposueldo(tipoSueldoSeleccionado);
                if (!listVSCrear.contains(listVigenciasSueldos.get(index))) {
                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(listVigenciasSueldos.get(index));
                    } else if (!listVSModificar.contains(listVigenciasSueldos.get(index))) {
                        listVSModificar.add(listVigenciasSueldos.get(index));
                    }
                }
            } else {
                filtrarVigenciasSueldos.get(index).setTiposueldo(tipoSueldoSeleccionado);
                if (!listVSCrear.contains(filtrarVigenciasSueldos.get(index))) {
                    if (listVSModificar.isEmpty()) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(index));
                    } else if (!listVSModificar.contains(filtrarVigenciasSueldos.get(index))) {
                        listVSModificar.add(filtrarVigenciasSueldos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update(":form:editarTipoSueldo");
            permitirIndexVA = true;
            cambiosVS = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setTiposueldo(tipoSueldoSeleccionado);
            context.update("formularioDialogos:nuevaVS");
        } else if (tipoActualizacion == 2) {
            duplicarVS.setTiposueldo(tipoSueldoSeleccionado);
            context.update("formularioDialogos:duplicarVS");
        }
        filtrarTiposSueldos = null;
        tipoSueldoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVS = null;
        tipoActualizacion = -1;
    }

    /**
     * Cancela la seleccion del centro costo (vigencia prorrateo)
     */
    public void cancelarCambioTipoSueldo() {
        filtrarTiposSueldos = null;
        tipoSueldoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVS = null;
        tipoActualizacion = -1;
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 1) {
                context.update("form:MotivoCambioSueldoDialogo");
                context.execute("MotivoCambioSueldoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 2) {
                context.update("form:TipoSueldoDialogo");
                context.execute("TipoSueldoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVA >= 0) {
            if (cualCeldaVA == 2) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVA == 3) {
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
        }

    }

    /**
     * Valida un proceso de nuevo registro dentro de la pagina con respecto a la
     * posicion en la pagina
     */
    public void validarNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (listVigenciasAfiliaciones != null) {
                if (listVigenciasAfiliaciones.isEmpty()) {
                    context.update("form:NuevoRegistroPagina");
                    context.execute("NuevoRegistroPagina.show()");
                } else {
                    context.update("form:NuevoRegistroVS");
                    context.execute("NuevoRegistroVS.show()");
                }
            }
            if (listVigenciasAfiliaciones == null) {
                context.update("form:NuevoRegistroPagina");
                context.execute("NuevoRegistroPagina.show()");
            }
        }
        if (indexVA >= 0) {
            context.update("form:NuevoRegistroVA");
            context.execute("NuevoRegistroVA.show()");
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
            nombreTabla = ":formExportarVS:datosVSEmpleadoExportar";
            nombreXML = "VigenciasSueldosXML";
        }
        if (indexVA >= 0) {
            nombreTabla = ":formExportarVA:datosVAVigenciaExportar";
            nombreXML = "VigenciasAfiliacionesXML";
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
            exportPDFVS();
        }
        if (indexVA >= 0) {
            exportPDFVA();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVS() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportarVS:datosVSEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasSueldosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroVS = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVA() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportarVA:datosVAVigenciaExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasAfiliacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVA = -1;
        secRegistroVA = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLSVS();
        }
        if (indexVA >= 0) {
            exportXLSVA();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVS() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportarVS:datosVSEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasSueldosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroVS = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVA() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportarVA:datosVAVigenciaExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasAfiliacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVA = -1;
        secRegistroVA = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
        if (indexVA >= 0) {
            if (tipoListaVA == 0) {
                tipoListaVA = 1;
            }
        }
    }

    public void cambioConsultaActual() {
        if (cambiosVS == false) {
            mostrarActual = true;
            listVigenciasSueldos = new ArrayList<VigenciasSueldos>();
            listVigenciasSueldos = (List<VigenciasSueldos>) administrarVigenciasSueldos.VigenciasSueldosActualesEmpleado(empleado.getSecuencia());
            getValorTotal();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSEmpleado");
            context.update("form:datosVAVigencia");
            context.update("form:totalConsultarActual");
            context.update("form:valorConsultarActual");
        }
        if (cambiosVS == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambioConsultaGlobal() {
        if (cambiosVS == false) {
            mostrarActual = false;
            valorTotal = 0;
            listVigenciasSueldos = null;
            getListVigenciasSueldos();
            getValorTotal();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVSEmpleado");
            context.update("form:datosVAVigencia");
            context.update("form:totalConsultarActual");
            context.update("form:valorConsultarActual");
        }
        if (cambiosVS == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }
    }

    public String visibilidadTotal() {
        String retorno = null;
        if (mostrarActual == false) {
            retorno = "hidden";
        }
        if (mostrarActual == true) {
            retorno = "visible";
        }
        return retorno;
    }
    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS

    public void verificarRastroTabla() {
        if (listVigenciasAfiliaciones == null || listVigenciasSueldos == null) {
            System.out.println("Cero");
            //Dialogo para seleccionar el rato de la tabla deseada
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        }

        if ((listVigenciasAfiliaciones != null) && (listVigenciasSueldos != null)) {
            System.out.println("Uno");
            System.out.println("listVigenciasAfiliaciones : " + listVigenciasAfiliaciones);
            System.out.println("listVigenciasSueldos : " + listVigenciasSueldos);
            if (index >= 0) {
                System.out.println("Uno . Uno");
                verificarRastroVigenciaSueldo();
                index = -1;
            }
            if (indexVA >= 0) {
                System.out.println("Uno . Dos");
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroVigenciaAfiliacion();
                indexVA = -1;
            }
        }
    }
    //Verificar Rastro Vigencia Sueldos

    public void verificarRastroVigenciaSueldo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasSueldos != null) {
            if (secRegistroVS != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVS, "VIGENCIASSUELDOS");
                backUpSecRegistroVS = secRegistroVS;
                backUp = secRegistroVS;
                secRegistroVS = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasSueldos";
                    msnConfirmarRastro = "La tabla VIGENCIASSUELDOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASSUELDOS")) {
                nombreTablaRastro = "VigenciasSueldos";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASSUELDOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroVigenciaAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasAfiliaciones != null) {
            if (secRegistroVA != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVA, "VIGENCIASAFILIACIONES");
                backUpSecRegistroVA = secRegistroVA;
                backUp = backUpSecRegistroVA;
                secRegistroVA = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasAfiliaciones";
                    msnConfirmarRastro = "La tabla VIGENCIASAFILIACIONES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASAFILIACIONES")) {
                nombreTablaRastro = "VigenciasAfiliaciones";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASAFILIACIONES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVA = -1;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //GET - SET 
    public List<VigenciasSueldos> getListVigenciasSueldos() {
        try {
            if (listVigenciasSueldos == null) {
                listVigenciasSueldos = new ArrayList<VigenciasSueldos>();
                listVigenciasSueldos = (List<VigenciasSueldos>) administrarVigenciasSueldos.VigenciasSueldosEmpleado(empleado.getSecuencia());
            }
            return listVigenciasSueldos;
        } catch (Exception e) {
            System.out.println("Error getVigenciaLocalizaciones " + e.toString());
            return null;
        }
    }

    public void setListVigenciasSueldos(List<VigenciasSueldos> listVigenciasSueldos) {
        this.listVigenciasSueldos = listVigenciasSueldos;
    }

    public List<VigenciasSueldos> getFiltrarVigenciasSueldos() {
        return filtrarVigenciasSueldos;
    }

    public void setFiltrarVigenciasSueldos(List<VigenciasSueldos> filtrarVigenciasSueldos) {
        this.filtrarVigenciasSueldos = filtrarVigenciasSueldos;
    }

    public List<VigenciasAfiliaciones> getListVigenciasAfiliaciones() {
        if (index >= 0) {
            VigenciasSueldos vigenciaTemporal = (VigenciasSueldos) listVigenciasSueldos.get(index);
            listVigenciasAfiliaciones = null;
            listVigenciasAfiliaciones = administrarVigenciasSueldos.VigenciasAfiliacionesVigencia(vigenciaTemporal.getSecuencia());
            if (listVigenciasAfiliaciones == null) {
                listVigenciasAfiliaciones = null;
            }
            if (listVigenciasAfiliaciones.isEmpty()) {
                listVigenciasAfiliaciones = null;
            }
            if (listVigenciasAfiliaciones != null) {

                for (int i = 0; i < listVigenciasAfiliaciones.size(); i++) {
                    if (!listVAModificar.isEmpty() && listVAModificar.contains(listVigenciasAfiliaciones.get(i))) {
                        int modIndex = listVAModificar.indexOf(listVigenciasAfiliaciones.get(i));
                        listVigenciasAfiliaciones.get(i).setFechafinal(listVAModificar.get(modIndex).getFechainicial());
                        listVigenciasAfiliaciones.get(i).setFechainicial(listVAModificar.get(modIndex).getFechafinal());
                        listVigenciasAfiliaciones.get(i).setTercerosucursal(listVAModificar.get(modIndex).getTercerosucursal());
                        listVigenciasAfiliaciones.get(i).setTipoentidad(listVAModificar.get(modIndex).getTipoentidad());
                        listVigenciasAfiliaciones.get(i).setValor(listVAModificar.get(modIndex).getValor());
                    } else if (!listVABorrar.isEmpty() && listVABorrar.contains(listVigenciasAfiliaciones.get(i))) {
                        listVigenciasAfiliaciones.remove(i);
                    }
                }
            }
            if (listVACrear != null) {
                for (int i = 0; i < listVACrear.size(); i++) {
                    if (listVigenciasAfiliaciones == null) {
                        listVigenciasAfiliaciones = new ArrayList<VigenciasAfiliaciones>();
                    }
                    listVigenciasAfiliaciones.add(listVACrear.get(i));
                }
            }

        }

        return listVigenciasAfiliaciones;
    }

    public void setListVigenciasAfiliaciones(List<VigenciasAfiliaciones> listVigenciasAfiliaciones) {
        this.listVigenciasAfiliaciones = listVigenciasAfiliaciones;
    }

    public List<VigenciasAfiliaciones> getFiltrarVigenciasAfiliaciones() {
        return filtrarVigenciasAfiliaciones;
    }

    public void setFiltrarVigenciasAfiliaciones(List<VigenciasAfiliaciones> filtrarVigenciasAfiliaciones) {
        this.filtrarVigenciasAfiliaciones = filtrarVigenciasAfiliaciones;
    }

    public List<TiposSueldos> getListTiposSueldos() {
        try {

            listTiposSueldos = administrarVigenciasSueldos.tiposSueldos();
            return listTiposSueldos;
        } catch (Exception e) {
            System.out.println("Error getListTiposSueldos " + e.toString());
            return null;
        }
    }

    public void setListTiposSueldos(List<TiposSueldos> listTiposSueldos) {
        this.listTiposSueldos = listTiposSueldos;
    }

    public TiposSueldos getTipoSueldoSeleccionado() {
        return tipoSueldoSeleccionado;
    }

    public void setTipoSueldoSeleccionado(TiposSueldos tipoSueldoSeleccionado) {
        this.tipoSueldoSeleccionado = tipoSueldoSeleccionado;
    }

    public List<TiposSueldos> getFiltrarTiposSueldos() {
        return filtrarTiposSueldos;
    }

    public void setFiltrarTiposSueldos(List<TiposSueldos> filtrarTiposSueldos) {
        this.filtrarTiposSueldos = filtrarTiposSueldos;
    }

    public List<MotivosCambiosSueldos> getListMotivosCambiosSueldos() {
        try {
            listMotivosCambiosSueldos = administrarVigenciasSueldos.motivosCambiosSueldos();
            return listMotivosCambiosSueldos;
        } catch (Exception e) {
            System.out.println("Error getListMotivosCambiosSueldos " + e.toString());
            return null;
        }
    }

    public void setListMotivosCambiosSueldos(List<MotivosCambiosSueldos> listMotivosCambiosSueldos) {
        this.listMotivosCambiosSueldos = listMotivosCambiosSueldos;
    }

    public MotivosCambiosSueldos getMotivoCambioSueldoSeleccionado() {
        return motivoCambioSueldoSeleccionado;
    }

    public void setMotivoCambioSueldoSeleccionado(MotivosCambiosSueldos motivoCambioSueldoSeleccionado) {
        this.motivoCambioSueldoSeleccionado = motivoCambioSueldoSeleccionado;
    }

    public List<MotivosCambiosSueldos> getFiltrarMotivosCambiosSueldos() {
        return filtrarMotivosCambiosSueldos;
    }

    public void setFiltrarMotivosCambiosSueldos(List<MotivosCambiosSueldos> filtrarMotivosCambiosSueldos) {
        this.filtrarMotivosCambiosSueldos = filtrarMotivosCambiosSueldos;
    }

    public List<TiposEntidades> getListTiposEntidades() {
        try {
            if (listTiposEntidades.isEmpty()) {
                listTiposEntidades = administrarVigenciasSueldos.tiposEntidades();
            }
            return listTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error getListTiposEntidades " + e.toString());
            return null;
        }
    }

    public void setListTiposEntidades(List<TiposEntidades> listTiposEntidades) {
        this.listTiposEntidades = listTiposEntidades;
    }

    public TiposEntidades getTipoEntidadSeleccionado() {
        return tipoEntidadSeleccionado;
    }

    public void setTipoEntidadSeleccionado(TiposEntidades tipoEntidadSeleccionado) {
        this.tipoEntidadSeleccionado = tipoEntidadSeleccionado;
    }

    public List<TiposEntidades> getFiltrarTiposEntidades() {
        return filtrarTiposEntidades;
    }

    public void setFiltrarTiposEntidades(List<TiposEntidades> filtrarTiposEntidades) {
        this.filtrarTiposEntidades = filtrarTiposEntidades;
    }

    public List<Terceros> getListTerceros() {
        try {
            if (listTerceros.isEmpty()) {
                listTerceros = administrarVigenciasSueldos.terceros();
            }
            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error getListTerceros " + e.toString());
            return null;
        }
    }

    public void setListTerceros(List<Terceros> listTerceros) {
        this.listTerceros = listTerceros;
    }

    public Terceros getTerceroSeleccionado() {
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public List<Terceros> getFiltrarTerceros() {
        return filtrarTerceros;
    }

    public void setFiltrarTerceros(List<Terceros> filtrarTerceros) {
        this.filtrarTerceros = filtrarTerceros;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<VigenciasSueldos> getListVSModificar() {
        return listVSModificar;
    }

    public void setListVSModificar(List<VigenciasSueldos> listVSModificar) {
        this.listVSModificar = listVSModificar;
    }

    public List<VigenciasAfiliaciones> getListVAModificar() {
        return listVAModificar;
    }

    public void setListVAModificar(List<VigenciasAfiliaciones> listVAModificar) {
        this.listVAModificar = listVAModificar;
    }

    public VigenciasSueldos getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasSueldos nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public VigenciasAfiliaciones getNuevaVigenciaA() {
        return nuevaVigenciaA;
    }

    public void setNuevaVigenciaA(VigenciasAfiliaciones nuevaVigenciaA) {
        this.nuevaVigenciaA = nuevaVigenciaA;
    }

    public List<VigenciasSueldos> getListVSCrear() {
        return listVSCrear;
    }

    public void setListVSCrear(List<VigenciasSueldos> listVSCrear) {
        this.listVSCrear = listVSCrear;
    }

    public List<VigenciasSueldos> getListVSBorrar() {
        return listVSBorrar;
    }

    public void setListVSBorrar(List<VigenciasSueldos> listVSBorrar) {
        this.listVSBorrar = listVSBorrar;
    }

    public List<VigenciasAfiliaciones> getListVABorrar() {
        return listVABorrar;
    }

    public void setListVABorrar(List<VigenciasAfiliaciones> listVABorrar) {
        this.listVABorrar = listVABorrar;
    }

    public VigenciasSueldos getEditarVS() {
        return editarVS;
    }

    public void setEditarVS(VigenciasSueldos editarVS) {
        this.editarVS = editarVS;
    }

    public VigenciasAfiliaciones getEditarVA() {
        return editarVA;
    }

    public void setEditarVA(VigenciasAfiliaciones editarVA) {
        this.editarVA = editarVA;
    }

    public VigenciasSueldos getDuplicarVS() {
        return duplicarVS;
    }

    public void setDuplicarVS(VigenciasSueldos duplicarVS) {
        this.duplicarVS = duplicarVS;
    }

    public VigenciasAfiliaciones getDuplicarVA() {
        return duplicarVA;
    }

    public void setDuplicarVA(VigenciasAfiliaciones duplicarVA) {
        this.duplicarVA = duplicarVA;
    }

    public List<VigenciasAfiliaciones> getListVACrear() {
        return listVACrear;
    }

    public void setListVACrear(List<VigenciasAfiliaciones> listVACrear) {
        this.listVACrear = listVACrear;
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

    public boolean isRetroactivo() {
        return retroactivo;
    }

    public void setRetroactivo(boolean retroactivo) {
        this.retroactivo = retroactivo;
    }

    public boolean isMostrarActual() {

        return mostrarActual;
    }

    public void setMostrarActual(boolean mostrarActual) {
        this.mostrarActual = mostrarActual;
    }

    public int getValorTotal() {
        valorTotal = 0;
        if (listVigenciasSueldos != null) {
            for (int i = 0; i < listVigenciasSueldos.size(); i++) {
                valorTotal = valorTotal + listVigenciasSueldos.get(i).getValor().intValue();
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:totalConsultarActual");
        context.update("form:valorConsultarActual");
        return valorTotal;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigInteger getSecRegistroVS() {
        return secRegistroVS;
    }

    public void setSecRegistroVS(BigInteger secRegistroVS) {
        this.secRegistroVS = secRegistroVS;
    }

    public BigInteger getBackUpSecRegistroVS() {
        return backUpSecRegistroVS;
    }

    public void setBackUpSecRegistroVS(BigInteger backUpSecRegistroVS) {
        this.backUpSecRegistroVS = backUpSecRegistroVS;
    }

    public BigInteger getSecRegistroVA() {
        return secRegistroVA;
    }

    public void setSecRegistroVA(BigInteger secRegistroVA) {
        this.secRegistroVA = secRegistroVA;
    }

    public BigInteger getBackUpSecRegistroVA() {
        return backUpSecRegistroVA;
    }

    public void setBackUpSecRegistroVA(BigInteger backUpSecRegistroVA) {
        this.backUpSecRegistroVA = backUpSecRegistroVA;
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

    public boolean isGuardado() {
        return guardado;
    }

    public String getAltoTabla1() {
        return altoTabla1;
    }

    public String getAltoTabla2() {
        return altoTabla2;
    }

    public VigenciasSueldos getVigenciaSueldoSeleccionada() {
        return vigenciaSueldoSeleccionada;
    }

    public void setVigenciaSueldoSeleccionada(VigenciasSueldos vigenciaSueldoSeleccionada) {
        this.vigenciaSueldoSeleccionada = vigenciaSueldoSeleccionada;
    }

    public VigenciasAfiliaciones getVigenciaAfiliacioneSeleccionada() {
        return vigenciaAfiliacioneSeleccionada;
    }

    public void setVigenciaAfiliacioneSeleccionada(VigenciasAfiliaciones vigenciaAfiliacioneSeleccionada) {
        this.vigenciaAfiliacioneSeleccionada = vigenciaAfiliacioneSeleccionada;
    }

}

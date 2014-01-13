package Controlador;

import Entidades.CentrosCostos;
import Entidades.Empleados;
import Entidades.Estructuras;
import Entidades.MotivosLocalizaciones;
import Entidades.Proyectos;
import Entidades.Sets;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasProrrateos;
import Entidades.VigenciasProrrateosProyectos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciaLocalizacionInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    //Vigencias Prorrateos
    private List<VigenciasProrrateos> vigenciasProrrateosVigencia;
    private List<VigenciasProrrateos> filtradoVigenciasProrrateosVigencia;
    // Vigencia Prorrateos Proyectos
    private List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectosVigencia;
    private List<VigenciasProrrateosProyectos> filtradoVigenciasProrrateosProyectosVigencia;
    //Centros Costos
    private List<CentrosCostos> centrosCostos;
    private CentrosCostos centroCostoSeleccionado;
    private List<CentrosCostos> filtradoCentroCostos;
    //Estructuras
    private List<Estructuras> estructuras;
    private Estructuras estructuraSelecionada;
    private List<Estructuras> filtradoEstructura;
    //Motivos Localizaciones
    private List<MotivosLocalizaciones> motivosLocalizaciones;
    private MotivosLocalizaciones motivoLocalizacionSelecionado;
    private List<MotivosLocalizaciones> filtradoMotivosLocalizaciones;
    //Proyectos
    private List<Proyectos> proyectos;
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
    private boolean guardado, guardarOk;
    //crear VL
    public VigenciasLocalizaciones nuevaVigencia;
    //crear VP
    public VigenciasProrrateos nuevaVigenciaP;
    //crear VPP
    public VigenciasProrrateosProyectos nuevaVigenciaPP;
    private List<VigenciasLocalizaciones> listVLCrear;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<VigenciasLocalizaciones> listVLBorrar;
    private List<VigenciasProrrateos> listVPBorrar;
    private List<VigenciasProrrateosProyectos> listVPPBorrar;
    //editar celda
    private VigenciasLocalizaciones editarVL;
    private VigenciasProrrateos editarVP;
    private VigenciasProrrateosProyectos editarVPP;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
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
    private boolean cambioVigenciaP, cambioVigenciaPP;
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

        centrosCostos = null;

        vigenciaLocalizaciones = null;
        estructuras = new ArrayList<Estructuras>();
        motivosLocalizaciones = new ArrayList<MotivosLocalizaciones>();
        empleado = new Empleados();
        proyectos = new ArrayList<Proyectos>();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVLBorrar = new ArrayList<VigenciasLocalizaciones>();
        listVPBorrar = new ArrayList<VigenciasProrrateos>();
        listVPPBorrar = new ArrayList<VigenciasProrrateosProyectos>();
        //crear aficiones
        listVLCrear = new ArrayList<VigenciasLocalizaciones>();
        k = 0;
        //modificar aficiones
        listVLModificar = new ArrayList<VigenciasLocalizaciones>();
        listVPModificar = new ArrayList<VigenciasProrrateos>();
        listVPPModificar = new ArrayList<VigenciasProrrateosProyectos>();
        //editar
        editarVL = new VigenciasLocalizaciones();
        editarVP = new VigenciasProrrateos();
        editarVPP = new VigenciasProrrateosProyectos();
        cambioEditor = false;
        aceptarEditar = true;
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

        bandera = 0;
        banderaVP = 0;
        banderaVPP = 0;
        permitirIndex = true;
        permitirIndexVP = true;
        permitirIndexVPP = true;
        indexVP = -1;
        indexVPP = -1;
        cualCeldaVP = -1;
        cualCeldaVPP = -1;

        nuevaVigenciaP = new VigenciasProrrateos();
        nuevaVigenciaP.setCentrocosto(new CentrosCostos());
        nuevaVigenciaP.setProyecto(new Proyectos());

        nuevaVigenciaPP = new VigenciasProrrateosProyectos();
        nuevaVigenciaPP.setProyecto(new Proyectos());

        listVPCrear = new ArrayList<VigenciasProrrateos>();
        listVPPCrear = new ArrayList<VigenciasProrrateosProyectos>();

        nombreTabla = ":formExportarVL:datosVLEmpleadoExportar";
        nombreXML = "VigenciasLocalizacionesXML";

        cambioVigenciaP = false;
        cambioVigenciaPP = false;
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
        System.out.println("Busco al empleado --> " + empleado);
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVL(int indice) {
        if (tipoLista == 0) {
            if (!listVLCrear.contains(vigenciaLocalizaciones.get(indice))) {

                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(vigenciaLocalizaciones.get(indice));
                } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(indice))) {
                    listVLModificar.add(vigenciaLocalizaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroVL = null;
        } else {
            if (!listVLCrear.contains(filtrarVL.get(indice))) {

                if (listVLModificar.isEmpty()) {
                    listVLModificar.add(filtrarVL.get(indice));
                } else if (!listVLModificar.contains(filtrarVL.get(indice))) {
                    listVLModificar.add(filtrarVL.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroVL = null;
        }
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasLocalizaciones auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = vigenciaLocalizaciones.get(index);
            }
            if (tipoLista == 1) {
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
        if (tipoLista == 0) {
            auxiliar = vigenciaLocalizaciones.get(index);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarVL.get(index);
        }
        if (auxiliar.getFechavigencia() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistro(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarVL(i);
            } else {
                if (tipoLista == 0) {
                    vigenciaLocalizaciones.get(i).setFechavigencia(fechaVigencia);
                }
                if (tipoLista == 1) {
                    filtrarVL.get(i).setFechavigencia(fechaVigencia);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVLEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(i).setFechavigencia(fechaVigencia);
            }
            if (tipoLista == 1) {
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
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(indice).getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            } else {
                filtrarVL.get(indice).getLocalizacion().getCentrocosto().setCodigoNombre(centroCosto);
            }
            for (int i = 0; i < estructuras.size(); i++) {
                if (estructuras.get(i).getCentrocosto().getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciaLocalizaciones.get(indice).setLocalizacion(estructuras.get(indiceUnicoElemento));
                } else {
                    filtrarVL.get(indice).setLocalizacion(estructuras.get(indiceUnicoElemento));
                }
                estructuras.clear();
                getEstructuras();
            } else {
                permitirIndex = false;
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOLOCALIZACION")) {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(indice).getMotivo().setDescripcion(motivoLocalizacion);
            } else {
                filtrarVL.get(indice).getMotivo().setDescripcion(motivoLocalizacion);
            }
            for (int i = 0; i < motivosLocalizaciones.size(); i++) {
                if (motivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciaLocalizaciones.get(indice).setMotivo(motivosLocalizaciones.get(indiceUnicoElemento));
                } else {
                    filtrarVL.get(indice).setMotivo(motivosLocalizaciones.get(indiceUnicoElemento));
                }
                motivosLocalizaciones.clear();
                getMotivosLocalizaciones();
            } else {
                permitirIndex = false;
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(indice).getProyecto().setNombreproyecto(proyecto);
            } else {
                filtrarVL.get(indice).getProyecto().setNombreproyecto(proyecto);
            }
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciaLocalizaciones.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                } else {
                    filtrarVL.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                }
                proyectos.clear();
                getProyectos();
            } else {
                permitirIndex = false;
                context.update("form:ProyectosDialogo");
                context.execute("ProyectosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(indice))) {

                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(indice));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(indice))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroVL = null;
            } else {
                if (!listVLCrear.contains(filtrarVL.get(indice))) {

                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(indice));
                    } else if (!listVLModificar.contains(filtrarVL.get(indice))) {
                        listVLModificar.add(filtrarVL.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroVL = null;
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
    public void modificarVP(int indice) {
        if (tipoListaVP == 0) {
            if (!listVPCrear.contains(vigenciasProrrateosVigencia.get(indice))) {

                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(vigenciasProrrateosVigencia.get(indice));
                } else if (!listVPModificar.contains(vigenciasProrrateosVigencia.get(indice))) {
                    listVPModificar.add(vigenciasProrrateosVigencia.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioVigenciaP = true;
            indexVP = -1;
            secRegistroVP = null;
        } else {
            if (!listVPCrear.contains(filtradoVigenciasProrrateosVigencia.get(indice))) {

                if (listVPModificar.isEmpty()) {
                    listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indice));
                } else if (!listVPModificar.contains(filtradoVigenciasProrrateosVigencia.get(indice))) {
                    listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioVigenciaP = true;
            indexVP = -1;
            secRegistroVP = null;
        }
    }

    public boolean validarFechasRegistroVigenciaProrrateo(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasProrrateos auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = vigenciasProrrateosVigencia.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtradoVigenciasProrrateosVigencia.get(index);
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
        if (tipoLista == 0) {
            auxiliar = vigenciasProrrateosVigencia.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtradoVigenciasProrrateosVigencia.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaProrrateo(0);
            if (retorno == true) {
                cambiarIndiceVP(i, c);
                modificarVP(i);
            } else {
                if (tipoLista == 0) {
                    vigenciasProrrateosVigencia.get(i).setFechafinal(fechaFinVP);
                    vigenciasProrrateosVigencia.get(i).setFechainicial(fechaIniVP);
                }
                if (tipoLista == 1) {
                    filtradoVigenciasProrrateosVigencia.get(i).setFechafinal(fechaFinVP);
                    filtradoVigenciasProrrateosVigencia.get(i).setFechainicial(fechaIniVP);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                vigenciasProrrateosVigencia.get(i).setFechainicial(fechaIniVP);
            }
            if (tipoLista == 1) {
                filtradoVigenciasProrrateosVigencia.get(i).setFechainicial(fechaIniVP);
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
            if (tipoListaVP == 0) {
                vigenciasProrrateosVigencia.get(indice).getCentrocosto().setNombre(centroCostosVP);
            } else {
                filtradoVigenciasProrrateosVigencia.get(indice).getCentrocosto().setNombre(centroCostosVP);
            }
            for (int i = 0; i < centrosCostos.size(); i++) {
                if (centrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVP == 0) {
                    cambioVigenciaP = true;
                    vigenciasProrrateosVigencia.get(indice).setCentrocosto(centrosCostos.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaP = true;
                    filtradoVigenciasProrrateosVigencia.get(indice).setCentrocosto(centrosCostos.get(indiceUnicoElemento));
                }
                centrosCostos.clear();
                getCentrosCostos();
            } else {
                permitirIndexVP = false;
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROYECTO")) {
            if (tipoListaVP == 0) {
                vigenciasProrrateosVigencia.get(indice).getProyecto().setNombreproyecto(proyectoVP);
            } else {
                filtradoVigenciasProrrateosVigencia.get(indice).getProyecto().setNombreproyecto(proyectoVP);
            }
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVP == 0) {
                    cambioVigenciaP = true;
                    vigenciasProrrateosVigencia.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaP = true;
                    filtradoVigenciasProrrateosVigencia.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                }
                proyectos.clear();
                getProyectos();
            } else {
                permitirIndexVP = false;
                context.update("form:ProyectosDialogoVP");
                context.execute("ProyectosDialogoVP.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVP == 0) {
                if (!listVPCrear.contains(vigenciasProrrateosVigencia.get(indice))) {

                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indice));
                    } else if (!listVPModificar.contains(vigenciasProrrateosVigencia.get(indice))) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioVigenciaP = true;
                indexVP = -1;
                secRegistroVP = null;
            } else {
                if (!listVPCrear.contains(filtradoVigenciasProrrateosVigencia.get(indice))) {

                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indice));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosVigencia.get(indice))) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioVigenciaP = true;
                indexVP = -1;
                secRegistroVP = null;
            }
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
    public void modificarVPP(int indice) {
        if (tipoListaVPP == 0) {
            if (!listVPPCrear.contains(vigenciasProrrateosProyectosVigencia.get(indice))) {

                if (listVPPModificar.isEmpty()) {
                    listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indice));
                } else if (!listVPPModificar.contains(vigenciasProrrateosProyectosVigencia.get(indice))) {
                    listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioVigenciaPP = true;
            indexVPP = -1;
            secRegistroVPP = null;
        } else {
            if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indice))) {

                if (listVPPModificar.isEmpty()) {
                    listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indice));
                } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indice))) {
                    listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambioVigenciaPP = true;
            indexVPP = -1;
            secRegistroVPP = null;
        }
    }

    public boolean validarFechasRegistroVigenciaProrrateoProyecto(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            VigenciasProrrateosProyectos auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = vigenciasProrrateosProyectosVigencia.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtradoVigenciasProrrateosProyectosVigencia.get(index);
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
        if (tipoLista == 0) {
            auxiliar = vigenciasProrrateosProyectosVigencia.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtradoVigenciasProrrateosProyectosVigencia.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVigenciaProrrateoProyecto(0);
            if (retorno == true) {
                cambiarIndiceVPP(i, c);
                modificarVPP(i);
            } else {
                if (tipoLista == 0) {
                    vigenciasProrrateosProyectosVigencia.get(i).setFechafinal(fechaFinVPP);
                    vigenciasProrrateosProyectosVigencia.get(i).setFechainicial(fechaIniVPP);
                }
                if (tipoLista == 1) {
                    filtradoVigenciasProrrateosProyectosVigencia.get(i).setFechafinal(fechaFinVPP);
                    filtradoVigenciasProrrateosProyectosVigencia.get(i).setFechainicial(fechaIniVPP);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPPVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                vigenciasProrrateosProyectosVigencia.get(i).setFechainicial(fechaIniVPP);
            }
            if (tipoLista == 1) {
                filtradoVigenciasProrrateosProyectosVigencia.get(i).setFechainicial(fechaIniVPP);
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
            if (tipoListaVPP == 0) {
                vigenciasProrrateosProyectosVigencia.get(indice).getProyecto().setNombreproyecto(proyectoVPP);
            } else {
                filtradoVigenciasProrrateosProyectosVigencia.get(indice).getProyecto().setNombreproyecto(proyectoVPP);
            }
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVPP == 0) {
                    cambioVigenciaPP = true;
                    vigenciasProrrateosProyectosVigencia.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaPP = true;
                    filtradoVigenciasProrrateosProyectosVigencia.get(indice).setProyecto(proyectos.get(indiceUnicoElemento));
                }
                proyectos.clear();
                getProyectos();
            } else {
                permitirIndexVPP = false;
                context.update("form:ProyectosDialogoVPP");
                context.execute("ProyectosDialogoVPP.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVPP == 0) {
                if (!listVPPCrear.contains(vigenciasProrrateosProyectosVigencia.get(indice))) {

                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indice));
                    } else if (!listVPPModificar.contains(vigenciasProrrateosProyectosVigencia.get(indice))) {
                        listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioVigenciaPP = true;
                indexVPP = -1;
                secRegistroVPP = null;
            } else {
                if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indice))) {

                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indice));
                    } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indice))) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
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
            for (int i = 0; i < estructuras.size(); i++) {
                if (estructuras.get(i).getCentrocosto().getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setLocalizacion(estructuras.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCentroCosto");
                } else if (tipoNuevo == 2) {
                    duplicarVL.setLocalizacion(estructuras.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCentroCosto");
                }
                estructuras.clear();
                getEstructuras();
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
            for (int i = 0; i < motivosLocalizaciones.size(); i++) {
                if (motivosLocalizaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setMotivo(motivosLocalizaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMotivo");
                } else if (tipoNuevo == 2) {
                    duplicarVL.setMotivo(motivosLocalizaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivo");
                }
                motivosLocalizaciones.clear();
                getMotivosLocalizaciones();
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
            if (tipoNuevo == 1) {
                nuevaVigencia.getProyecto().setNombreproyecto(proyecto);
            } else if (tipoNuevo == 2) {
                duplicarVL.getProyecto().setNombreproyecto(proyecto);
            }
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProyecto");
                } else if (tipoNuevo == 2) {
                    duplicarVL.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarProyecto");
                }
                proyectos.clear();
                getProyectos();
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
            for (int i = 0; i < centrosCostos.size(); i++) {
                if (centrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaP.setCentrocosto(centrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCentroCostoVP");
                } else if (tipoNuevo == 2) {
                    duplicarVP.setCentrocosto(centrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicadoCentroCostoVP");
                }
                centrosCostos.clear();
                getCentrosCostos();
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
            if (tipoNuevo == 1) {
                nuevaVigenciaP.getProyecto().setNombreproyecto(proyecto);
            } else if (tipoNuevo == 2) {
                duplicarVP.getProyecto().setNombreproyecto(proyecto);
            }
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaP.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProyectoVP");
                } else if (tipoNuevo == 2) {
                    duplicarVP.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicadoProyectoVP");
                }
                proyectos.clear();
                getProyectos();
            } else {
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
            for (int i = 0; i < proyectos.size(); i++) {
                if (proyectos.get(i).getNombreproyecto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaPP.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProyectoVPP");
                } else if (tipoNuevo == 2) {
                    duplicarVPP.setProyecto(proyectos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarProyectoVPP");
                }
                proyectos.clear();
                getProyectos();
            } else {
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

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            if ((cambioVigenciaP == false) && (cambioVigenciaPP == false)) {
                cualCelda = celda;
                index = indice;
                indexAuxVL = indice;
                secRegistroVL = vigenciaLocalizaciones.get(index).getSecuencia();
                if (cualCelda == 1) {
                    centroCosto = vigenciaLocalizaciones.get(index).getLocalizacion().getCentrocosto().getCodigoNombre();
                } else if (cualCelda == 3) {
                    motivoLocalizacion = vigenciaLocalizaciones.get(index).getMotivo().getDescripcion();
                } else if (cualCelda == 4) {
                    proyecto = vigenciaLocalizaciones.get(index).getProyecto().getNombreproyecto();
                }
                getVigenciasProrrateosVigencia();
                if (!vigenciasProrrateosVigencia.isEmpty()) {
                    getVigenciasProrrateosProyectosVigencia();
                } else {
                }
            }
            if (cambioVigenciaP == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaProrrateo");
                context.execute("guardarCambiosVigenciaProrrateo.show()");

            }
            if (cambioVigenciaPP == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaProrrateoProyecto");
                context.execute("guardarCambiosVigenciaProrrateoProyecto.show()");

            }
        }

        System.out.println("Indice: " + index + " Celda: " + cualCelda);
        if (banderaVP == 1) {
            vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
            vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosVigencia = null;
            tipoListaVP = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectosVigencia = null;
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
        if (permitirIndexVP == true) {
            indexVP = indice;
            cualCeldaVP = celda;
            secRegistroVP = vigenciasProrrateosVigencia.get(indexVP).getSecuencia();
            if (cambioVigenciaPP == false) {
                if (cualCeldaVP == 0) {
                    centroCostosVP = vigenciasProrrateosVigencia.get(indexVP).getCentrocosto().getNombre();
                } else if (cualCelda == 4) {
                    proyectoVP = vigenciasProrrateosVigencia.get(indexVP).getProyecto().getNombreproyecto();
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaProrrateoProyecto");
                context.execute("guardarCambiosVigenciaProrrateoProyecto.show()");
            }
        }
        if (bandera == 1) {
            vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
            vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("display: none; visibility: hidden;");
            vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectosVigencia = null;
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
        if (permitirIndexVPP == true) {
            indexVPP = indice;
            cualCeldaVPP = celda;
            secRegistroVPP = vigenciasProrrateosProyectosVigencia.get(indexVPP).getSecuencia();
            if (cambioVigenciaP == false) {
                if (cualCeldaVP == 0) {
                    centroCostosVP = vigenciasProrrateosProyectosVigencia.get(indexVPP).getProyecto().getNombreproyecto();
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaProrrateo");
                context.execute("guardarCambiosVigenciaProrrateo.show()");
            }
        }
        if (bandera == 1) {
            vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
            vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("display: none; visibility: hidden;");
            vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVP == 1) {
            vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
            vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosVigencia = null;
            tipoListaVP = 0;
        }
        index = -1;
        secRegistroVL = null;
        indexVP = -1;
        secRegistroVP = null;
    }
    //GUARDAR

    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosVL();
        if (vigenciasProrrateosVigencia != null) {
            guardarCambiosVP();
        }
        if (vigenciasProrrateosProyectosVigencia != null) {
            guardarCambiosVPP();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosVL() {
        if (guardado == false) {
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVLEmpleado");
            k = 0;
        }
        index = -1;
        secRegistroVL = null;

    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVP() {
        if (guardado == false) {
            if (!listVPBorrar.isEmpty()) {
                for (int i = 0; i < listVPBorrar.size(); i++) {
                    if (listVPBorrar.get(i).getProyecto().getSecuencia() == null) {
                        listVPBorrar.get(i).setProyecto(null);
                    }
                    if (listVPBorrar.get(i).getCentrocosto().getSecuencia() == null) {
                        listVPBorrar.get(i).setCentrocosto(null);
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
                    administrarVigenciaLocalizacion.crearVP(listVPCrear.get(i));
                }
                listVPCrear.clear();
            }
            if (!listVPModificar.isEmpty()) {
                administrarVigenciaLocalizacion.modificarVP(listVPModificar);
                listVPModificar.clear();
            }
            vigenciasProrrateosVigencia = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPVigencia");
            k = 0;
        }
        cambioVigenciaP = false;
        indexVP = -1;
        secRegistroVP = null;
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasProrrateosProyectos
     */
    public void guardarCambiosVPP() {
        if (guardado == false) {
            if (!listVPPBorrar.isEmpty()) {
                for (int i = 0; i < listVPPBorrar.size(); i++) {
                    if (listVPPBorrar.get(i).getProyecto().getSecuencia() == null) {
                        listVPPBorrar.get(i).setProyecto(null);
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

                    administrarVigenciaLocalizacion.crearVPP(listVPPCrear.get(i));
                }
                listVPPCrear.clear();
            }
            if (!listVPPModificar.isEmpty()) {
                administrarVigenciaLocalizacion.modificarVPP(listVPPModificar);
                listVPPModificar.clear();
            }
            vigenciasProrrateosProyectosVigencia = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            k = 0;
        }
        cambioVigenciaPP = false;
        indexVPP = -1;
        secRegistroVPP = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
            vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("display: none; visibility: hidden;");
            vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
            bandera = 0;
            filtrarVL = null;
            tipoLista = 0;
        }
        if (banderaVP == 1) {
            vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
            vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosVigencia = null;
            tipoListaVP = 0;
        }
        if (banderaVPP == 1) {
            vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectosVigencia = null;
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
        k = 0;
        vigenciaLocalizaciones = null;
        vigenciasProrrateosVigencia = null;
        vigenciasProrrateosProyectosVigencia = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVLEmpleado");
        context.update("form:datosVPVigencia");
        context.update("form:datosVPPVigencia");
        cambioVigenciaP = false;
        cambioVigenciaPP = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVP() {
        if (banderaVP == 1) {
            vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
            vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
            vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
            vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
            vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
            vPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
            vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPVigencia");
            banderaVP = 0;
            filtradoVigenciasProrrateosVigencia = null;
            tipoListaVP = 0;
        }
        listVPBorrar.clear();
        listVPCrear.clear();
        listVPModificar.clear();
        indexVP = -1;
        secRegistroVP = null;
        k = 0;
        vigenciasProrrateosVigencia = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVPVigencia");
        cambioVigenciaP = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos proyectos
     */
    public void cancelarModificacionVPP() {
        if (banderaVPP == 1) {
            vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
            vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
            vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
            vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
            vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
            vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
            banderaVPP = 0;
            filtradoVigenciasProrrateosProyectosVigencia = null;
            tipoListaVPP = 0;
        }
        listVPPBorrar.clear();
        listVPPCrear.clear();
        listVPPModificar.clear();
        indexVPP = -1;
        secRegistroVPP = null;
        k = 0;
        vigenciasProrrateosProyectosVigencia = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVPPVigencia");
        cambioVigenciaPP = false;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVL = vigenciaLocalizaciones.get(index);
            }
            if (tipoLista == 1) {
                editarVL = filtrarVL.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            if (tipoListaVP == 0) {
                editarVP = vigenciasProrrateosVigencia.get(indexVP);
            }
            if (tipoListaVP == 1) {
                editarVP = filtradoVigenciasProrrateosVigencia.get(indexVP);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
            if (tipoListaVPP == 0) {
                editarVPP = vigenciasProrrateosProyectosVigencia.get(indexVPP);
            }
            if (tipoListaVPP == 1) {
                editarVPP = filtradoVigenciasProrrateosProyectosVigencia.get(tipoListaVPP);
            }
            RequestContext context = RequestContext.getCurrentInstance();
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
        index = -1;
        secRegistroVL = null;
        indexVP = -1;
        secRegistroVP = null;
        indexVPP = -1;
        secRegistroVPP = null;
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevaVL() {
        if (nuevaVigencia.getFechavigencia() != null && nuevaVigencia.getLocalizacion().getSecuencia() != null && nuevaVigencia.getMotivo().getSecuencia() != null) {
            if (validarFechasRegistro(1) == true) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
                    vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
                    vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
                    vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
                    vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
                    vlMotivo.setFilterStyle("display: none; visibility: hidden;");
                    vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
                    vlProyecto.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                    bandera = 0;
                    filtrarVL = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(l);
                nuevaVigencia.setEmpleado(empleado);
                listVLCrear.add(nuevaVigencia);

                vigenciaLocalizaciones.add(nuevaVigencia);
                nuevaVigencia = new VigenciasLocalizaciones();
                nuevaVigencia.setLocalizacion(new Estructuras());
                nuevaVigencia.getLocalizacion().setCentrocosto(new CentrosCostos());
                nuevaVigencia.setMotivo(new MotivosLocalizaciones());
                nuevaVigencia.setProyecto(new Proyectos());
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVLEmpleado");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("NuevoRegistroVL.hide()");
                index = -1;
                secRegistroVL = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechaVL.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
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
        index = -1;
        secRegistroVL = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVP() {
        if (nuevaVigenciaP.getCentrocosto().getSecuencia() != null && nuevaVigenciaP.getPorcentaje() == null && nuevaVigenciaP.getFechainicial() != null) {
            if (validarFechasRegistroVigenciaProrrateo(1) == true) {
                cambioVigenciaP = true;
                //CERRAR FILTRADO
                if (banderaVP == 1) {
                    vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
                    vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                    vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
                    vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
                    vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
                    vPProyecto.setFilterStyle("display: none; visibility: hidden;");
                    vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
                    vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                    banderaVP = 0;
                    filtradoVigenciasProrrateosVigencia = null;
                    tipoListaVP = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigenciaP.setSecuencia(l);
                nuevaVigenciaP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                listVPCrear.add(nuevaVigenciaP);
                //  vigenciasProrrateosVigencia.add(nuevaVigenciaP);
                //  System.out.println("Almaceno a vigenciasProrrateosVigencia el dato nuevaVigenciaP");

                nuevaVigenciaP = new VigenciasProrrateos();
                nuevaVigenciaP.setCentrocosto(new CentrosCostos());
                nuevaVigenciaP.setProyecto(new Proyectos());
                index = indexAuxVL;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPVigencia");
                context.execute("NuevoRegistroVP.hide();");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                indexVP = -1;
                secRegistroVP = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show();");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegnew.show();");
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaVP() {
        nuevaVigenciaP = new VigenciasProrrateos();
        nuevaVigenciaP.setCentrocosto(new CentrosCostos());
        nuevaVigenciaP.setProyecto(new Proyectos());
        indexVP = -1;
        secRegistroVP = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva vigencia prorrateo proyecto
     */
    public void agregarNuevaVPP() {
        if (nuevaVigenciaPP.getFechainicial() != null && nuevaVigenciaPP.getPorcentaje() >= 0 && nuevaVigenciaPP.getProyecto().getSecuencia() != null) {
            if (validarFechasRegistroVigenciaProrrateoProyecto(1) == true) {
                cambioVigenciaPP = true;
                //CERRAR FILTRADO
                if (banderaVP == 1) {
                    vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
                    vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
                    vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
                    vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
                    vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
                    vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                    banderaVPP = 0;
                    filtradoVigenciasProrrateosProyectosVigencia = null;
                    tipoListaVPP = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigenciaPP.setSecuencia(l);
                nuevaVigenciaPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                listVPPCrear.add(nuevaVigenciaPP);
                //   vigenciasProrrateosProyectosVigencia.add(nuevaVigenciaPP);
                nuevaVigenciaP = new VigenciasProrrateos();
                nuevaVigenciaP.setCentrocosto(new CentrosCostos());
                nuevaVigenciaP.setProyecto(new Proyectos());
                index = indexAuxVL;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPPVigencia");
                context.execute("NuevoRegistroVPP.hide();");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                indexVPP = -1;
                secRegistroVPP = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show();");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegnew.show();");
        }
    }

    /**
     * Elimina los datos de una nueva vigencia prorrateo proyecto
     */
    public void limpiarNuevaVPP() {
        nuevaVigenciaPP = new VigenciasProrrateosProyectos();
        nuevaVigenciaPP.setProyecto(new Proyectos());
        indexVPP = -1;
        secRegistroVPP = null;
    }
    //DUPLICAR VL

    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        if (index >= 0) {
            duplicarVigenciaL();
        }
        if (indexVP >= 0) {
            duplicarVigenciaP();
        }
        if (indexVPP >= 0) {
            duplicarVigenciaPP();
        }
    }

    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarVigenciaL() {
        if (index >= 0) {
            duplicarVL = new VigenciasLocalizaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVL.setSecuencia(l);
                duplicarVL.setEmpleado(vigenciaLocalizaciones.get(index).getEmpleado());
                duplicarVL.setFechavigencia(vigenciaLocalizaciones.get(index).getFechavigencia());
                duplicarVL.setLocalizacion(vigenciaLocalizaciones.get(index).getLocalizacion());
                duplicarVL.setMotivo(vigenciaLocalizaciones.get(index).getMotivo());
                duplicarVL.setProyecto(vigenciaLocalizaciones.get(index).getProyecto());
            }
            if (tipoLista == 1) {
                duplicarVL.setSecuencia(l);
                duplicarVL.setEmpleado(filtrarVL.get(index).getEmpleado());
                duplicarVL.setFechavigencia(filtrarVL.get(index).getFechavigencia());
                duplicarVL.setLocalizacion(filtrarVL.get(index).getLocalizacion());
                duplicarVL.setMotivo(filtrarVL.get(index).getMotivo());
                duplicarVL.setProyecto(filtrarVL.get(index).getProyecto());
            }
            if (duplicarVL.getMotivo() == null) {
                duplicarVL.setMotivo(new MotivosLocalizaciones());
            }
            if (duplicarVL.getLocalizacion() == null) {
                duplicarVL.setLocalizacion(new Estructuras());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVL");
            context.execute("DuplicarRegistroVL.show()");
            index = -1;
            secRegistroVL = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        if (duplicarVL.getFechavigencia() != null && duplicarVL.getLocalizacion().getSecuencia() != null && duplicarVL.getMotivo().getSecuencia() != null) {
            if (validarFechasRegistro(2) == true) {
                vigenciaLocalizaciones.add(duplicarVL);
                listVLCrear.add(duplicarVL);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVLEmpleado");
                context.execute("DuplicarRegistroVL.hide();");
                index = -1;
                secRegistroVL = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
                    vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
                    vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
                    vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
                    vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
                    vlMotivo.setFilterStyle("display: none; visibility: hidden;");
                    vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
                    vlProyecto.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                    bandera = 0;
                    filtrarVL = null;
                    tipoLista = 0;
                }
                duplicarVL = new VigenciasLocalizaciones();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechaVL.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaVL.show()");
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
            k++;
            l = BigInteger.valueOf(k);

            if (tipoListaVP == 0) {
                duplicarVP.setSecuencia(l);
                duplicarVP.setCentrocosto(vigenciasProrrateosVigencia.get(indexVP).getCentrocosto());
                duplicarVP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVP.setFechafinal(vigenciasProrrateosVigencia.get(indexVP).getFechainicial());
                duplicarVP.setFechainicial(vigenciasProrrateosVigencia.get(indexVP).getFechafinal());
                duplicarVP.setPorcentaje(vigenciasProrrateosVigencia.get(indexVP).getPorcentaje());
                duplicarVP.setSubporcentaje(vigenciasProrrateosVigencia.get(indexVP).getSubporcentaje());
                duplicarVP.setProyecto(vigenciasProrrateosVigencia.get(indexVP).getProyecto());
            }
            if (tipoListaVP == 1) {
                duplicarVP.setSecuencia(l);
                duplicarVP.setCentrocosto(filtradoVigenciasProrrateosVigencia.get(indexVP).getCentrocosto());
                duplicarVP.setViglocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVP.setFechafinal(filtradoVigenciasProrrateosVigencia.get(indexVP).getFechainicial());
                duplicarVP.setFechainicial(filtradoVigenciasProrrateosVigencia.get(indexVP).getFechafinal());
                duplicarVP.setPorcentaje(filtradoVigenciasProrrateosVigencia.get(indexVP).getPorcentaje());
                duplicarVP.setSubporcentaje(filtradoVigenciasProrrateosVigencia.get(indexVP).getSubporcentaje());
                duplicarVP.setProyecto(filtradoVigenciasProrrateosVigencia.get(indexVP).getProyecto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoVP");
            context.execute("DuplicadoRegistroVP.show()");
            indexVP = -1;
            secRegistroVP = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVP() {
        if (duplicarVP.getCentrocosto().getSecuencia() != null && duplicarVP.getPorcentaje() == null && duplicarVP.getFechainicial() != null) {
            if (validarFechasRegistroVigenciaProrrateo(2) == true) {
                cambioVigenciaP = true;
                vigenciasProrrateosVigencia.add(duplicarVP);
                listVPCrear.add(duplicarVP);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPVigencia");
                context.execute("DuplicadoRegistroVP.hide();");
                indexVP = -1;
                secRegistroVP = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (banderaVP == 1) {
                    //CERRAR FILTRADO
                    vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
                    vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                    vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
                    vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
                    vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
                    vPProyecto.setFilterStyle("display: none; visibility: hidden;");
                    vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
                    vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                    banderaVP = 0;
                    filtradoVigenciasProrrateosVigencia = null;
                    tipoListaVP = 0;
                }
                duplicarVP = new VigenciasProrrateos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show();");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegnew.show();");
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
        cambioVigenciaPP = true;
        if (indexVPP >= 0) {
            duplicarVPP = new VigenciasProrrateosProyectos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoListaVPP == 0) {
                duplicarVPP.setSecuencia(l);
                duplicarVPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVPP.setFechafinal(vigenciasProrrateosProyectosVigencia.get(indexVPP).getFechafinal());
                duplicarVPP.setFechainicial(vigenciasProrrateosProyectosVigencia.get(indexVPP).getFechainicial());
                duplicarVPP.setPorcentaje(vigenciasProrrateosProyectosVigencia.get(indexVPP).getPorcentaje());
                duplicarVPP.setProyecto(vigenciasProrrateosProyectosVigencia.get(indexVPP).getProyecto());
            }
            if (tipoListaVPP == 1) {
                duplicarVPP.setSecuencia(l);
                duplicarVPP.setVigencialocalizacion(vigenciaLocalizaciones.get(indexAuxVL));
                duplicarVPP.setFechafinal(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP).getFechafinal());
                duplicarVPP.setFechainicial(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP).getFechainicial());
                duplicarVPP.setPorcentaje(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP).getPorcentaje());
                duplicarVPP.setProyecto(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP).getProyecto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVPP");
            context.execute("DuplicarRegistroVPP.show()");
            indexVPP = -1;
            secRegistroVPP = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * Vigencia Prorrateo Proyecto
     */
    public void confirmarDuplicarVPP() {
        if (duplicarVPP.getFechainicial() != null && duplicarVPP.getPorcentaje() >= 0 && duplicarVPP.getProyecto().getSecuencia() != null) {
            if (validarFechasRegistroVigenciaProrrateoProyecto(2) == true) {
                vigenciasProrrateosProyectosVigencia.add(duplicarVPP);
                listVPPCrear.add(duplicarVPP);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVPPVigencia");
                context.execute("DuplicarRegistroVPP.show()");
                indexVPP = -1;
                secRegistroVPP = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (banderaVPP == 1) {
                    //CERRAR FILTRADO
                    vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
                    vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
                    vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
                    vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
                    vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
                    vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                    banderaVPP = 0;
                    filtradoVigenciasProrrateosProyectosVigencia = null;
                    tipoListaVPP = 0;
                }
                duplicarVPP = new VigenciasProrrateosProyectos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show();");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegnew.show();");
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
        if (index >= 0) {
            if ((vigenciasProrrateosProyectosVigencia == null) && (vigenciasProrrateosVigencia == null)) {
                borrarVL();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
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

    //BORRAR VL
    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarVL() {
        if (index >= 0) {
            if (tipoLista == 0) {
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
            if (tipoLista == 1) {
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
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarVP() {
        cambioVigenciaP = true;
        if (indexVP >= 0) {
            if (tipoListaVP == 0) {
                if (!listVPModificar.isEmpty() && listVPModificar.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                    int modIndex = listVPModificar.indexOf(vigenciasProrrateosVigencia.get(indexVP));
                    listVPModificar.remove(modIndex);
                    listVPBorrar.add(vigenciasProrrateosVigencia.get(indexVP));
                } else if (!listVPCrear.isEmpty() && listVPCrear.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                    int crearIndex = listVPCrear.indexOf(vigenciasProrrateosVigencia.get(indexVP));
                    listVPCrear.remove(crearIndex);
                } else {
                    listVPBorrar.add(vigenciasProrrateosVigencia.get(indexVP));
                }
                vigenciasProrrateosVigencia.remove(indexVP);
            }
            if (tipoListaVP == 1) {
                if (!listVPModificar.isEmpty() && listVPModificar.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                    int modIndex = listVPModificar.indexOf(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    listVPModificar.remove(modIndex);
                    listVPBorrar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                } else if (!listVPCrear.isEmpty() && listVPCrear.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                    int crearIndex = listVPCrear.indexOf(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    listVPCrear.remove(crearIndex);
                } else {
                    listVPBorrar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                }
                int VPIndex = vigenciasProrrateosVigencia.indexOf(filtradoVigenciasProrrateosVigencia.get(indexVP));
                vigenciasProrrateosVigencia.remove(VPIndex);
                filtradoVigenciasProrrateosVigencia.remove(indexVP);
            }
            index = indexAuxVL;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPVigencia");
            indexVP = -1;
            secRegistroVP = null;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo proyecto
     */
    public void borrarVPP() {
        cambioVigenciaPP = true;
        if (indexVPP >= 0) {
            if (tipoListaVPP == 0) {
                if (!listVPPModificar.isEmpty() && listVPPModificar.contains(vigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    int modIndex = listVPPModificar.indexOf(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                    listVPPModificar.remove(modIndex);
                    listVPPBorrar.add(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                } else if (!listVPPCrear.isEmpty() && listVPPCrear.contains(vigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    int crearIndex = listVPPCrear.indexOf(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                    listVPPCrear.remove(crearIndex);
                } else {
                    listVPPBorrar.add(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                }
                vigenciasProrrateosProyectosVigencia.remove(indexVPP);
            }
            if (tipoListaVPP == 1) {
                if (!listVPPModificar.isEmpty() && listVPPModificar.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    int modIndex = listVPPModificar.indexOf(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                    listVPPModificar.remove(modIndex);
                    listVPPBorrar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                } else if (!listVPPCrear.isEmpty() && listVPPCrear.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    int crearIndex = listVPPCrear.indexOf(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                    listVPPCrear.remove(crearIndex);
                } else {
                    listVPPBorrar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                }
                int VPPIndex = vigenciasProrrateosProyectosVigencia.indexOf(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                vigenciasProrrateosProyectosVigencia.remove(VPPIndex);
                filtradoVigenciasProrrateosProyectosVigencia.remove(indexVPP);
            }
            index = indexAuxVL;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVPPVigencia");
            indexVPP = -1;
            secRegistroVPP = null;
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
        if (index >= 0) {
            filtradoVigenciaLocalizacion();
        }
        if (indexVP >= 0) {
            filtradoVigenciaProrrateo();
        }
        if (indexVPP >= 0) {
            filtradoVigenciaProrrateoProyecto();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoVigenciaLocalizacion() {
        if (index >= 0) {
            if (bandera == 0) {
                vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
                vlFechaVigencia.setFilterStyle("width: 60px");
                vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
                vlCentroCosto.setFilterStyle("width: 100px");
                vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
                vlLocalizacion.setFilterStyle("width: 100px");
                vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
                vlMotivo.setFilterStyle("width: 100px");
                vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
                vlProyecto.setFilterStyle("width: 100px");
                RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                bandera = 1;
            } else if (bandera == 1) {
                vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
                vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
                vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
                vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
                vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
                vlMotivo.setFilterStyle("display: none; visibility: hidden;");
                vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
                vlProyecto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
                bandera = 0;
                filtrarVL = null;
                tipoLista = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaProrrateo() {
        if (indexVP >= 0) {
            if (banderaVP == 0) {
                //Columnas Tabla VPP
                vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
                vPCentroCosto.setFilterStyle("width: 60px");
                vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                vPPorcentaje.setFilterStyle("width: 25px");
                vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
                vPFechaInicial.setFilterStyle("width: 50px");
                vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
                vPFechaFinal.setFilterStyle("width: 50px");
                vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
                vPProyecto.setFilterStyle("width: 50px");
                vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
                vPSubPorcentaje.setFilterStyle("width: 50px");
                RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                banderaVP = 1;
            } else if (banderaVP == 1) {
                vPCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPCentroCosto");
                vPCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                vPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPPorcentaje");
                vPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                vPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaInicial");
                vPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPFechaFinal");
                vPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPProyecto");
                vPProyecto.setFilterStyle("display: none; visibility: hidden;");
                vPSubPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPVigencia:vPSubPorcentaje");
                vPSubPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVPVigencia");
                banderaVP = 0;
                filtradoVigenciasProrrateosVigencia = null;
                tipoListaVP = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo proyecto
     */
    public void filtradoVigenciaProrrateoProyecto() {
        if (indexVPP >= 0) {
            //Columnas Tabla VPP
            if (banderaVPP == 0) {
                vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
                vPPProyecto.setFilterStyle("width: 60px");
                vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
                vPPPorcentaje.setFilterStyle("width: 25px");
                vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
                vPPFechaInicial.setFilterStyle("width: 50px");
                vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
                vPPFechaFinal.setFilterStyle("width: 50px");
                RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                banderaVPP = 1;
            } else if (banderaVPP == 1) {
                vPPProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPProyecto");
                vPPProyecto.setFilterStyle("display: none; visibility: hidden;");
                vPPPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPPorcentaje");
                vPPPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                vPPFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaInicial");
                vPPFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vPPFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVPPVigencia:vPPFechaFinal");
                vPPFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVPPVigencia");
                banderaVPP = 0;
                filtradoVigenciasProrrateosProyectosVigencia = null;
                tipoListaVPP = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            vlFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlFechaVigencia");
            vlFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vlCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlCentroCosto");
            vlCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            vlLocalizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlLocalizacion");
            vlLocalizacion.setFilterStyle("display: none; visibility: hidden;");
            vlMotivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlMotivo");
            vlMotivo.setFilterStyle("display: none; visibility: hidden;");
            vlProyecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVLEmpleado:vlProyecto");
            vlProyecto.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVLEmpleado");
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
        k = 0;
        vigenciaLocalizaciones = null;
        guardado = true;
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
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
            } else if (dlg == 1) {
                //MotivosLocalizaciones
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
            } else if (dlg == 2) {
                //Proyectos
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
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
            } else if (dlg == 1) {
                //Proyectos
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
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(index).setLocalizacion(estructuraSelecionada);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {
                filtrarVL.get(index).setLocalizacion(estructuraSelecionada);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCentroCosto");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setLocalizacion(estructuraSelecionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {
            duplicarVL.setLocalizacion(estructuraSelecionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoEstructura = null;
        estructuraSelecionada = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre estructura (vigencia localizacion)
     */
    public void cancelarCambioEstructura() {
        filtradoEstructura = null;
        estructuraSelecionada = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarMotivoLocalizacion() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(index).setMotivo(motivoLocalizacionSelecionado);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {
                filtrarVL.get(index).setMotivo(motivoLocalizacionSelecionado);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarMotivo");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setMotivo(motivoLocalizacionSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {
            duplicarVL.setMotivo(motivoLocalizacionSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoMotivosLocalizaciones = null;
        motivoLocalizacionSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioMotivoLocalizacion() {
        filtradoMotivosLocalizaciones = null;
        motivoLocalizacionSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarProyecto() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciaLocalizaciones.get(index).setProyecto(proyectoSelecionado);
                if (!listVLCrear.contains(vigenciaLocalizaciones.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    } else if (!listVLModificar.contains(vigenciaLocalizaciones.get(index))) {
                        listVLModificar.add(vigenciaLocalizaciones.get(index));
                    }
                }
            } else {
                filtrarVL.get(index).setProyecto(proyectoSelecionado);
                if (!listVLCrear.contains(filtrarVL.get(index))) {
                    if (listVLModificar.isEmpty()) {
                        listVLModificar.add(filtrarVL.get(index));
                    } else if (!listVLModificar.contains(filtrarVL.get(index))) {
                        listVLModificar.add(filtrarVL.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarProyecto");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVL");
        } else if (tipoActualizacion == 2) {
            duplicarVL.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVL");
        }
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioProyecto() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistroVL = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Metodo que actualiza el centro costo seleccionado (vigencia prorrateo)
     */
    public void actualizarCentroCostoVP() {
        if (tipoActualizacion == 0) {
            if (tipoListaVP == 0) {
                vigenciasProrrateosVigencia.get(indexVP).setCentrocosto(centroCostoSeleccionado);
                if (!listVPCrear.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indexVP));
                    } else if (!listVPModificar.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indexVP));
                    }
                }
            } else {
                filtradoVigenciasProrrateosVigencia.get(indexVP).setCentrocosto(centroCostoSeleccionado);
                if (!listVPCrear.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCentroCostoVP");
            permitirIndexVP = true;
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaP.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVP");
        } else if (tipoActualizacion == 2) {
            duplicarVP.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVP");
        }
        cambioVigenciaP = true;
        filtradoCentroCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVP = -1;
        secRegistroVP = null;
        tipoActualizacion = -1;
    }

    /**
     * Cancela la seleccion del centro costo (vigencia prorrateo)
     */
    public void cancelarCambioCentroCostoVP() {
        filtradoCentroCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        indexVP = -1;
        secRegistroVP = null;
        tipoActualizacion = -1;
        permitirIndexVP = true;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Actualiza el proyeecto seleccionado (vigencia prorrateo)
     */
    public void actualizarProyectoVP() {
        if (tipoActualizacion == 0) {
            if (tipoListaVP == 0) {
                vigenciasProrrateosVigencia.get(indexVP).setProyecto(proyectoSelecionado);
                if (!listVPCrear.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indexVP));
                    } else if (!listVPModificar.contains(vigenciasProrrateosVigencia.get(indexVP))) {
                        listVPModificar.add(vigenciasProrrateosVigencia.get(indexVP));
                    }
                }
            } else {
                filtradoVigenciasProrrateosVigencia.get(indexVP).setProyecto(proyectoSelecionado);
                if (!listVPCrear.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                    if (listVPModificar.isEmpty()) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    } else if (!listVPModificar.contains(filtradoVigenciasProrrateosVigencia.get(indexVP))) {
                        listVPModificar.add(filtradoVigenciasProrrateosVigencia.get(indexVP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVP = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarProyectoVP");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaP.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVP");
        } else if (tipoActualizacion == 2) {
            duplicarVP.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVP");
        }
        cambioVigenciaP = true;
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        indexVP = -1;
        secRegistroVP = null;
        tipoActualizacion = -1;
    }

    /**
     * Cancela el proyecto seleccionado (vigencia prorrateo)
     */
    public void cancelarCambioProyectoVP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        indexVP = -1;
        secRegistroVP = null;
        tipoActualizacion = -1;
        permitirIndexVP = true;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     * Actualiza el proyecto seleccionado (vigencia prorrateo proyecto)
     */
    public void actualizarProyectoVPP() {
        if (tipoActualizacion == 0) {
            if (tipoListaVPP == 0) {
                vigenciasProrrateosProyectosVigencia.get(indexVPP).setProyecto(proyectoSelecionado);
                if (!listVPPCrear.contains(vigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                    } else if (!listVPPModificar.contains(vigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                        listVPPModificar.add(vigenciasProrrateosProyectosVigencia.get(indexVPP));
                    }
                }
            } else {
                filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP).setProyecto(proyectoSelecionado);
                if (!listVPPCrear.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                    if (listVPPModificar.isEmpty()) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                    } else if (!listVPPModificar.contains(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP))) {
                        listVPPModificar.add(filtradoVigenciasProrrateosProyectosVigencia.get(indexVPP));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexVPP = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarProyectoVPP");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaPP.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVPP");
        } else if (tipoActualizacion == 2) {
            duplicarVPP.setProyecto(proyectoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVPP");
        }
        cambioVigenciaPP = true;
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        indexVPP = -1;
        secRegistroVPP = null;
        tipoActualizacion = -1;
    }

    /**
     * Cancela la seleccion del proyecto (vigencioa prorrateo proyecto)
     */
    public void cancelarCambioProyectoVPP() {
        filtradoProyectos = null;
        proyectoSelecionado = null;
        aceptar = true;
        indexVPP = -1;
        secRegistroVPP = null;
        tipoActualizacion = -1;
        permitirIndexVPP = true;
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
                //Estructura
                context.update("form:LocalizacionDialogo");
                context.execute("LocalizacionDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 3) {
                //Motivos
                context.update("form:MotivoDialogo");
                context.execute("MotivoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 4) {
                //Proyecto
                context.update("form:ProyectosDialogo");
                context.execute("ProyectosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVP >= 0) {
            if (cualCeldaVP == 0) {
                context.update("form:CentroCostosDialogo");
                context.execute("CentroCostosDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVP == 4) {
                context.update("form:ProyectosDialogoVP");
                context.execute("ProyectosDialogoVP.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVPP >= 0) {
            if (cualCeldaVPP == 0) {
                context.update("form:ProyectosDialogoVPP");
                context.execute("ProyectosDialogoVPP.show()");
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
            if ((vigenciasProrrateosProyectosVigencia.isEmpty())) {
                //Dialogo de nuevo registro multiple
                context.update("form:NuevoRegistroPagina");
                context.execute("NuevoRegistroPagina.show()");
            } else {
                //Dialogo de nuevo registro vigencias localizaciones
                context.update("form:NuevoRegistroVL");
                context.execute("NuevoRegistroVL.show()");
            }
        }
        if (indexVP >= 0) {
            //Dialogo de nuevo registro vigencia prorrateo
            context.update("form:NuevoRegistroVP");
            context.execute("NuevoRegistroVP.show()");
        }
        if (indexVPP >= 0) {
            //Dialogo de nuevo registro vigencia prorrateo proyecto
            context.update("form:NuevoRegistroVPP");
            context.execute("NuevoRegistroVPP.show()");
        }
    }

    /**
     * Valida un registro VigenciaProrrateoProyecto con respecto a la existencia
     * de VigenciaProrrateo en el registro
     */
    public void validarRegistroVPP() {
        if (!vigenciasProrrateosVigencia.isEmpty()) {
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
        index = -1;
        secRegistroVL = null;
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
        indexVP = -1;
        secRegistroVP = null;
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
        indexVPP = -1;
        secRegistroVPP = null;
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
        index = -1;
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
        indexVP = -1;
        secRegistroVP = null;
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
        indexVPP = -1;
        secRegistroVPP = null;
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
        if (indexVP >= 0) {
            if (tipoListaVP == 0) {
                tipoListaVP = 1;
            }
        }
        if (indexVPP >= 0) {
            if (tipoListaVPP == 0) {
                tipoListaVPP = 1;
            }
        }

    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void verificarRastroTabla() {
        if (vigenciaLocalizaciones == null || vigenciasProrrateosProyectosVigencia == null || vigenciasProrrateosVigencia == null) {
            //Dialogo para seleccionar el rato de la tabla deseada
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        }

        if ((vigenciaLocalizaciones != null) && (vigenciasProrrateosProyectosVigencia != null) && (vigenciasProrrateosVigencia != null)) {
            if (index >= 0) {
                verificarRastroVigenciaLocalizacion();
                index = -1;

            }
            if (indexVP >= 0) {
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroVigenciaProrrateo();
                indexVP = -1;
            }
            if (indexVPP >= 0) {
                //Metodo Rastro Vigencias Afiliaciones
                verificarRastroVigenciaProrrateoProyecto();
                indexVPP = -1;
            }
        }
    }

    public void verificarRastroVigenciaLocalizacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaLocalizaciones != null) {
            if (secRegistroVL != null) {
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASLOCALIZACIONES")) {
                nombreTablaRastro = "VigenciasLocalizaciones";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASLOCALIZACIONES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroVigenciaProrrateo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciasProrrateosVigencia != null) {
            if (secRegistroVP != null) {
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASPRORRATEOS")) {
                nombreTablaRastro = "VigenciasProrrateos";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASPRORRATEOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVP = -1;
    }

    public void verificarRastroVigenciaProrrateoProyecto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciasProrrateosProyectosVigencia != null) {
            if (secRegistroVPP != null) {
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
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASPRORRATEOSPROYECTOS")) {
                nombreTablaRastro = "VigenciasProrrateosProyectos";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASPRORRATEOSPROYECTOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVPP = -1;
    }

    public List<VigenciasLocalizaciones> getVigenciaLocalizaciones() {
        try {
            if (vigenciaLocalizaciones == null) {
                vigenciaLocalizaciones = new ArrayList<VigenciasLocalizaciones>();
                vigenciaLocalizaciones = administrarVigenciaLocalizacion.VigenciasLocalizacionesEmpleado(empleado.getSecuencia());
                return vigenciaLocalizaciones;
            } else {
                return vigenciaLocalizaciones;
            }
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
            System.out.println("Error getFiltrarVL jejej");
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

    public List<Estructuras> getEstructuras() {
        if (estructuras.isEmpty()) {
            estructuras = administrarVigenciaLocalizacion.estructuras();
        }
        return estructuras;
    }

    public void setEstructuras(List<Estructuras> estructuras) {
        this.estructuras = estructuras;
    }

    public Estructuras getEstructuraSelecionada() {
        return estructuraSelecionada;
    }

    public void setEstructuraSelecionada(Estructuras estructuraSelecionada) {
        this.estructuraSelecionada = estructuraSelecionada;
    }

    public List<Estructuras> getFiltradoEstructura() {
        return filtradoEstructura;
    }

    public void setFiltradoEstructura(List<Estructuras> filtradoEstructura) {
        this.filtradoEstructura = filtradoEstructura;
    }

    public List<MotivosLocalizaciones> getMotivosLocalizaciones() {
        if (motivosLocalizaciones.isEmpty()) {
            motivosLocalizaciones = administrarVigenciaLocalizacion.motivosLocalizaciones();
        }
        return motivosLocalizaciones;
    }

    public void setMotivosLocalizaciones(List<MotivosLocalizaciones> motivosLocalizaciones) {
        this.motivosLocalizaciones = motivosLocalizaciones;
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

    public List<Proyectos> getProyectos() {
        try {
            if (proyectos.isEmpty()) {
                proyectos = administrarVigenciaLocalizacion.proyectos();
            }
            return proyectos;
        } catch (Exception e) {
            System.out.println("Error getProyectos " + e.toString());
            return null;
        }
    }

    public void setProyectos(List<Proyectos> proyectos) {
        this.proyectos = proyectos;
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

    public List<VigenciasProrrateos> getVigenciasProrrateosVigencia() {
        //   try {
        if (index >= 0) {
            VigenciasLocalizaciones vigenciaTemporal = vigenciaLocalizaciones.get(index);
            vigenciasProrrateosVigencia = administrarVigenciaLocalizacion.VigenciasProrrateosVigencia(vigenciaTemporal.getSecuencia());
            if (vigenciasProrrateosVigencia == null) {
                vigenciasProrrateosVigencia = null;
            } else {
                for (int i = 0; i < vigenciasProrrateosVigencia.size(); i++) {
                    //////////////
                    if (!listVPModificar.isEmpty() && listVPModificar.contains(vigenciasProrrateosVigencia.get(i))) {
                        int modIndex = listVPModificar.indexOf(vigenciasProrrateosVigencia.get(i));
                        vigenciasProrrateosVigencia.get(i).setCentrocosto(listVPModificar.get(modIndex).getCentrocosto());
                        vigenciasProrrateosVigencia.get(i).setFechafinal(listVPModificar.get(modIndex).getFechafinal());
                        vigenciasProrrateosVigencia.get(i).setFechainicial(listVPModificar.get(modIndex).getFechainicial());
                        vigenciasProrrateosVigencia.get(i).setPorcentaje(listVPModificar.get(modIndex).getPorcentaje());
                        vigenciasProrrateosVigencia.get(i).setSubporcentaje(listVPModificar.get(modIndex).getSubporcentaje());
                        vigenciasProrrateosVigencia.get(i).setProyecto(listVPModificar.get(modIndex).getProyecto());
                    } else if (!listVPBorrar.isEmpty() && listVPBorrar.contains(vigenciasProrrateosVigencia.get(i))) {
                        vigenciasProrrateosVigencia.remove(i);
                    }
                }
            }
            if (listVPCrear != null) {
                for (int i = 0; i < listVPCrear.size(); i++) {
                    vigenciasProrrateosVigencia.add(listVPCrear.get(i));
                }
            }

        }
        //    } catch (Exception e) {
        //        System.out.println("Error cargarVigenciasProrrateos");
        //         return null;
        //     }
        return vigenciasProrrateosVigencia;
    }

    public void setVigenciasProrrateosVigencia(List<VigenciasProrrateos> vigenciasProrrateosVigencia) {
        this.vigenciasProrrateosVigencia = vigenciasProrrateosVigencia;
    }

    public List<VigenciasProrrateos> getFiltradoVigenciasProrrateosVigencia() {
        return filtradoVigenciasProrrateosVigencia;
    }

    public void setFiltradoVigenciasProrrateosVigencia(List<VigenciasProrrateos> filtradoVigenciasProrrateosVigencia) {
        this.filtradoVigenciasProrrateosVigencia = filtradoVigenciasProrrateosVigencia;
    }

    public List<VigenciasProrrateosProyectos> getVigenciasProrrateosProyectosVigencia() {
        //  try {
        if (index >= 0) {
            VigenciasLocalizaciones vigenciaTemporal = vigenciaLocalizaciones.get(index);
            vigenciasProrrateosProyectosVigencia = administrarVigenciaLocalizacion.VigenciasProrrateosProyectosVigencia(vigenciaTemporal.getSecuencia());
            if (vigenciasProrrateosProyectosVigencia == null) {
                vigenciasProrrateosProyectosVigencia = null;
            } else {
                for (int i = 0; i < vigenciasProrrateosProyectosVigencia.size(); i++) {
                    //////////////
                    if (!listVPPModificar.isEmpty() && listVPPModificar.contains(vigenciasProrrateosProyectosVigencia.get(i))) {
                        int modIndex = listVPPModificar.indexOf(vigenciasProrrateosProyectosVigencia.get(i));
                        vigenciasProrrateosProyectosVigencia.get(i).setFechafinal(listVPPModificar.get(modIndex).getFechafinal());
                        vigenciasProrrateosProyectosVigencia.get(i).setFechainicial(listVPPModificar.get(modIndex).getFechainicial());
                        vigenciasProrrateosProyectosVigencia.get(i).setPorcentaje(listVPPModificar.get(modIndex).getPorcentaje());
                        vigenciasProrrateosProyectosVigencia.get(i).setProyecto(listVPPModificar.get(modIndex).getProyecto());
                    } else if (!listVPPBorrar.isEmpty() && listVPPBorrar.contains(vigenciasProrrateosProyectosVigencia.get(i))) {
                        vigenciasProrrateosProyectosVigencia.remove(i);
                    }
                }
            }
            if (listVPPCrear != null) {
                for (int i = 0; i < listVPPCrear.size(); i++) {
                    vigenciasProrrateosProyectosVigencia.add(listVPPCrear.get(i));
                }
            }
        }
        //      } catch (Exception e) {
        //        System.out.println("Error vigenciasProrrateosProyectosVigencia");
        //      return null;
        //  }
        return vigenciasProrrateosProyectosVigencia;
    }

    public void setVigenciasProrrateosProyectosVigencia(List<VigenciasProrrateosProyectos> vigenciasProrrateosProyectosVigencia) {
        this.vigenciasProrrateosProyectosVigencia = vigenciasProrrateosProyectosVigencia;
    }

    public List<VigenciasProrrateosProyectos> getFiltradoVigenciasProrrateosProyectosVigencia() {
        return filtradoVigenciasProrrateosProyectosVigencia;
    }

    public void setFiltradoVigenciasProrrateosProyectosVigencia(List<VigenciasProrrateosProyectos> filtradoVigenciasProrrateosProyectosVigencia) {
        this.filtradoVigenciasProrrateosProyectosVigencia = filtradoVigenciasProrrateosProyectosVigencia;
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

    public List<CentrosCostos> getCentrosCostos() {
        try {
            if (centrosCostos == null) {
                centrosCostos = administrarVigenciaLocalizacion.centrosCostos();
            }
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error getCentrosCostos " + e.toString());
            return null;
        }
    }

    public void setCentrosCostos(List<CentrosCostos> centrosCostos) {
        this.centrosCostos = centrosCostos;
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
}

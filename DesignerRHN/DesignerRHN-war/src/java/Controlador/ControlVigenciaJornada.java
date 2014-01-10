package Controlador;

import Entidades.Empleados;
import Entidades.IbcsAutoliquidaciones;
import Entidades.JornadasLaborales;
import Entidades.TiposDescansos;
import Entidades.VigenciasCompensaciones;
import Entidades.VigenciasJornadas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasJornadasInterface;
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
public class ControlVigenciaJornada implements Serializable {

    @EJB
    AdministrarVigenciasJornadasInterface administrarVigenciasJornadas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Localizaciones
    private List<VigenciasJornadas> listVigenciasJornadas;
    private List<VigenciasJornadas> filtrarVigenciasJornadas;
    //VigenciasCompensaciones Tiempo
    private List<VigenciasCompensaciones> listVigenciasCompensacionesTiempo;
    private List<VigenciasCompensaciones> filtrarVigenciasCompensacionesTiempo;
    // VigenciasCompensaciones Dinero
    private List<VigenciasCompensaciones> listVigenciasCompensacionesDinero;
    private List<VigenciasCompensaciones> filtrarVigenciasCompensacionesDinero;
    //Tipos Descansos
    private List<TiposDescansos> listTiposDescansos;
    private TiposDescansos tipoDescansoSeleccionado;
    private List<TiposDescansos> filtrarTiposDescansos;
    //Jornadas Laborales
    private List<JornadasLaborales> listJornadasLaborales;
    private JornadasLaborales jornadaLaboralSeleccionada;
    private List<JornadasLaborales> filtrarJornadasLaborales;
    //Empleado
    private Empleados empleado;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Activo/Desactivo VP Crtl + F11
    private int banderaVCT;
    //Activo/Desactivo VPP Crtl + F11
    private int banderaVCD;
    //Columnas Tabla VL
    private Column vJFechaVigencia, vJNombreJornada, vJTipoDescanso;
    //Columnas Tabla VP
    private Column vCTFechaInicial, vCTFechaFinal, vCTComentario;
    //Columnas Tabla VPP
    private Column vCDComentario, vCDFechaInicial, vCDFechaFinal;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasJornadas> listVJModificar;
    private List<VigenciasCompensaciones> listVCTModificar;
    private List<VigenciasCompensaciones> listVCDModificar;
    private boolean guardado, guardarOk;
    //crear VL
    public VigenciasJornadas nuevaVigencia;
    //crear VP
    public VigenciasCompensaciones nuevaVigenciaCT;
    //crear VPP
    public VigenciasCompensaciones nuevaVigenciaCD;
    private List<VigenciasJornadas> listVJCrear;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<VigenciasJornadas> listVJBorrar;
    private List<VigenciasCompensaciones> listVCTBorrar;
    private List<VigenciasCompensaciones> listVCDBorrar;
    //editar celda
    private VigenciasJornadas editarVJ;
    private VigenciasCompensaciones editarVCT;
    private VigenciasCompensaciones editarVCD;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasJornadas duplicarVJ;
    //Autocompletar
    private boolean permitirIndex, permitirIndexVCT, permitirIndexVCD;
    //Variables Autompletar
    private String nombreJornada, tipoDescanso;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexVCT, indexVCD;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaVCT, cualCeldaVCD, tipoListaVCT, tipoListaVCD;
    //Index Auxiliar Para Nuevos Registros
    private int indexAuxVJ;
    //Duplicar Vigencia Prorrateo
    private VigenciasCompensaciones duplicarVCT;
    //Duplicar Vigencia Prorrateo Proyecto
    private VigenciasCompensaciones duplicarVCD;
    //Lista Vigencia Prorrateo Crear
    private List<VigenciasCompensaciones> listVCTCrear;
    //Lista Vigencia Prorrateo Proyecto Crear
    private List<VigenciasCompensaciones> listVCDCrear;
    private String nombreXML;
    private String nombreTabla;
    //Banderas Boolean de operaciones sobre vigencias prorrateos y vigencias prorrateos proyectos
    private boolean cambioVigenciaCT, cambioVigenciaCD;
    //Bandera de validacion de existencia de datos en las tablas de compensaciones
    private boolean existeVCT, existeVCD;
    private BigInteger secRegistroVJ;
    private BigInteger backUpSecRegistroVJ;
    private BigInteger secRegistroVCT;
    private BigInteger backUpSecRegistroVCT;
    private BigInteger secRegistroVCD;
    private BigInteger backUpSecRegistroVCD;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private Date fechaParametro;
    private Date fechaVigenciaVJ;

    public ControlVigenciaJornada() {

        secRegistroVJ = null;
        backUpSecRegistroVJ = null;
        secRegistroVCT = null;
        backUpSecRegistroVCT = null;
        secRegistroVCD = null;
        backUpSecRegistroVCD = null;
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";

        listTiposDescansos = null;

        listVigenciasJornadas = null;
        listVigenciasCompensacionesTiempo = null;
        listVigenciasCompensacionesDinero = null;
        listJornadasLaborales = new ArrayList<JornadasLaborales>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVJBorrar = new ArrayList<VigenciasJornadas>();
        listVCTBorrar = new ArrayList<VigenciasCompensaciones>();
        listVCDBorrar = new ArrayList<VigenciasCompensaciones>();
        //crear aficiones
        listVJCrear = new ArrayList<VigenciasJornadas>();
        k = 0;
        //modificar aficiones
        listVJModificar = new ArrayList<VigenciasJornadas>();
        listVCTModificar = new ArrayList<VigenciasCompensaciones>();
        listVCDModificar = new ArrayList<VigenciasCompensaciones>();
        //editar
        editarVJ = new VigenciasJornadas();
        editarVCT = new VigenciasCompensaciones();
        editarVCD = new VigenciasCompensaciones();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        tipoListaVCT = 0;
        tipoListaVCD = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasJornadas();
        nuevaVigencia.setJornadatrabajo(new JornadasLaborales());
        nuevaVigencia.setTipodescanso(new TiposDescansos());
        bandera = 0;
        banderaVCT = 0;
        banderaVCD = 0;
        permitirIndex = true;
        permitirIndexVCT = true;
        permitirIndexVCD = true;
        indexVCT = -1;
        indexVCD = -1;
        cualCeldaVCT = -1;
        cualCeldaVCD = -1;

        nuevaVigenciaCT = new VigenciasCompensaciones();
        nuevaVigenciaCD = new VigenciasCompensaciones();
        listVCTCrear = new ArrayList<VigenciasCompensaciones>();
        listVCDCrear = new ArrayList<VigenciasCompensaciones>();

        nombreTabla = ":formExportarVJ:datosVJEmpleadoExportar";
        nombreXML = "VigenciasJornadasXML";

        cambioVigenciaCT = false;
        cambioVigenciaCD = false;

        existeVCT = false;
        existeVCD = false;
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(Empleados empl) {
        listVigenciasJornadas = null;
        empleado = empl;
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVJ(int indice) {
        if (tipoLista == 0) {
            if (!listVJCrear.contains(listVigenciasJornadas.get(indice))) {
                if (listVJModificar.isEmpty()) {
                    listVJModificar.add(listVigenciasJornadas.get(indice));
                } else if (!listVJModificar.contains(listVigenciasJornadas.get(indice))) {
                    listVJModificar.add(listVigenciasJornadas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroVJ = null;
        } else {
            if (!listVJCrear.contains(filtrarVigenciasJornadas.get(indice))) {

                if (listVJModificar.isEmpty()) {
                    listVJModificar.add(filtrarVigenciasJornadas.get(indice));
                } else if (!listVJModificar.contains(filtrarVigenciasJornadas.get(indice))) {
                    listVJModificar.add(filtrarVigenciasJornadas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroVJ = null;
        }
    }

    public boolean validarFechasRegistroVJ(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasJornadas auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasJornadas.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarVigenciasJornadas.get(index);
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

            if (duplicarVJ.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechasVJ(int i, int c) {
        VigenciasJornadas auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasJornadas.get(index);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarVigenciasJornadas.get(index);
        }
        if (auxiliar.getFechavigencia() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVJ(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarVJ(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasJornadas.get(i).setFechavigencia(fechaVigenciaVJ);
                }
                if (tipoLista == 1) {
                    filtrarVigenciasJornadas.get(i).setFechavigencia(fechaVigenciaVJ);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVJEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasJornadas.get(i).setFechavigencia(fechaVigenciaVJ);
            }
            if (tipoLista == 1) {
                filtrarVigenciasJornadas.get(i).setFechavigencia(fechaVigenciaVJ);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVJEmpleado");
            context.execute("negacionNuevaVJ.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciasLocalizaciones de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVJ(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NOMBREJORNADA")) {
            if (tipoLista == 0) {
                listVigenciasJornadas.get(indice).getJornadatrabajo().setDescripcion(nombreJornada);
            } else {
                filtrarVigenciasJornadas.get(indice).getJornadatrabajo().setDescripcion(nombreJornada);
            }
            for (int i = 0; i < listJornadasLaborales.size(); i++) {
                if (listJornadasLaborales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasJornadas.get(indice).setJornadatrabajo(listJornadasLaborales.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasJornadas.get(indice).setJornadatrabajo(listJornadasLaborales.get(indiceUnicoElemento));
                }
                listJornadasLaborales.clear();
                getListJornadasLaborales();
            } else {
                permitirIndex = false;
                context.update("form:JornadaLaboralDialogo");
                context.execute("JornadaLaboralDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPODESCANSO")) {
            if (tipoLista == 0) {
                listVigenciasJornadas.get(indice).getTipodescanso().setDescripcion(tipoDescanso);
            } else {
                filtrarVigenciasJornadas.get(indice).getTipodescanso().setDescripcion(tipoDescanso);
            }
            for (int i = 0; i < listTiposDescansos.size(); i++) {
                if (listTiposDescansos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasJornadas.get(indice).setTipodescanso(listTiposDescansos.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasJornadas.get(indice).setTipodescanso(listTiposDescansos.get(indiceUnicoElemento));
                }
                listTiposDescansos.clear();
                getListTiposDescansos();
            } else {
                permitirIndex = false;
                context.update("form:TiposDescansosDialogo");
                context.execute("TiposDescansosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVJCrear.contains(listVigenciasJornadas.get(indice))) {

                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(listVigenciasJornadas.get(indice));
                    } else if (!listVJModificar.contains(listVigenciasJornadas.get(indice))) {
                        listVJModificar.add(listVigenciasJornadas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroVJ = null;
            } else {
                if (!listVJCrear.contains(filtrarVigenciasJornadas.get(indice))) {

                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(indice));
                    } else if (!listVJModificar.contains(filtrarVigenciasJornadas.get(indice))) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroVJ = null;
            }
        }
        context.update("form:datosVJEmpleado");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVCT(int indice) {
        if (tipoListaVCT == 0) {
            if (!listVCTCrear.contains(listVigenciasCompensacionesTiempo.get(indice))) {

                if (listVCTModificar.isEmpty()) {
                    listVCTModificar.add(listVigenciasCompensacionesTiempo.get(indice));
                } else if (!listVCTModificar.contains(listVigenciasCompensacionesTiempo.get(indice))) {
                    listVCTModificar.add(listVigenciasCompensacionesTiempo.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            String auxiliar = listVigenciasCompensacionesTiempo.get(indice).getComentario();
            listVigenciasCompensacionesTiempo.get(indice).setComentario(auxiliar.toUpperCase());
            cambioVigenciaCT = true;
            indexVCT = -1;
            secRegistroVCT = null;
        } else {
            if (!listVCTCrear.contains(filtrarVigenciasCompensacionesTiempo.get(indice))) {

                if (listVCTModificar.isEmpty()) {
                    listVCTModificar.add(filtrarVigenciasCompensacionesTiempo.get(indice));
                } else if (!listVCTModificar.contains(filtrarVigenciasCompensacionesTiempo.get(indice))) {
                    listVCTModificar.add(filtrarVigenciasCompensacionesTiempo.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            String auxiliar = filtrarVigenciasCompensacionesTiempo.get(indice).getComentario();
            filtrarVigenciasCompensacionesTiempo.get(indice).setComentario(auxiliar.toUpperCase());
            cambioVigenciaCT = true;
            indexVCT = -1;
            secRegistroVCT = null;
        }
    }

    public void modificarFechasVCT(int i, int c) {
        cambiarIndiceVCT(i, c);
        modificarVCT(i);
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateoProyectos que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVCD(int indice) {
        if (tipoListaVCD == 0) {
            if (!listVCDCrear.contains(listVigenciasCompensacionesDinero.get(indice))) {

                if (listVCDModificar.isEmpty()) {
                    listVCDModificar.add(listVigenciasCompensacionesDinero.get(indice));
                } else if (!listVCDModificar.contains(listVigenciasCompensacionesDinero.get(indice))) {
                    listVCDModificar.add(listVigenciasCompensacionesDinero.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            String auxiliar = listVigenciasCompensacionesDinero.get(indice).getComentario();
            listVigenciasCompensacionesDinero.get(indice).setComentario(auxiliar.toUpperCase());
            cambioVigenciaCD = true;
            indexVCD = -1;
            secRegistroVCD = null;
        } else {
            if (!listVCDCrear.contains(filtrarVigenciasCompensacionesDinero.get(indice))) {

                if (listVCDModificar.isEmpty()) {
                    listVCDModificar.add(filtrarVigenciasCompensacionesDinero.get(indice));
                } else if (!listVCDModificar.contains(filtrarVigenciasCompensacionesDinero.get(indice))) {
                    listVCDModificar.add(filtrarVigenciasCompensacionesDinero.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            String auxiliar = filtrarVigenciasCompensacionesDinero.get(indice).getComentario();
            filtrarVigenciasCompensacionesDinero.get(indice).setComentario(auxiliar.toUpperCase());
            cambioVigenciaCD = true;
            indexVCD = -1;
            secRegistroVCD = null;
        }
    }

    public void modificarFechasVCD(int i, int c) {
        cambiarIndiceVCD(i, c);
        modificarVCD(i);
    }
    
    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaLocalizacion)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("NOMBREJORNADA")) {
            if (tipoNuevo == 1) {
                nombreJornada = nuevaVigencia.getJornadatrabajo().getDescripcion();
            } else if (tipoNuevo == 2) {
                nombreJornada = duplicarVJ.getJornadatrabajo().getDescripcion();
            }
        } else if (Campo.equals("TIPODESCANSO")) {
            if (tipoNuevo == 1) {
                tipoDescanso = nuevaVigencia.getTipodescanso().getDescripcion();
            } else if (tipoNuevo == 2) {
                tipoDescanso = duplicarVJ.getTipodescanso().getDescripcion();
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
        if (confirmarCambio.equalsIgnoreCase("NOMBREJORNADA")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getJornadatrabajo().setDescripcion(nombreJornada);
            } else if (tipoNuevo == 2) {
                duplicarVJ.getJornadatrabajo().setDescripcion(nombreJornada);
            }
            for (int i = 0; i < listJornadasLaborales.size(); i++) {
                if (listJornadasLaborales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setJornadatrabajo(listJornadasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaJornadaLaboral");
                } else if (tipoNuevo == 2) {
                    duplicarVJ.setJornadatrabajo(listJornadasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarJornadaLaboral");
                }
                listJornadasLaborales.clear();
                getListJornadasLaborales();
            } else {
                context.update("form:JornadaLaboralDialogo");
                context.execute("JornadaLaboralDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaJornadaLaboral");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarJornadaLaboral");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPODESCANSO")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getTipodescanso().setDescripcion(tipoDescanso);
            } else if (tipoNuevo == 2) {
                duplicarVJ.getTipodescanso().setDescripcion(tipoDescanso);
            }
            for (int i = 0; i < listTiposDescansos.size(); i++) {
                if (listTiposDescansos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setTipodescanso(listTiposDescansos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoDescanso");
                } else if (tipoNuevo == 2) {
                    duplicarVJ.setTipodescanso(listTiposDescansos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoDescanso");
                }
                listTiposDescansos.clear();
                getListTiposDescansos();
            } else {
                context.update("form:TiposDescansosDialogo");
                context.execute("TiposDescansosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTipoDescanso");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoDescanso");
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
            if ((cambioVigenciaCT == false) && (cambioVigenciaCD == false)) {
                cualCelda = celda;
                index = indice;
                indexAuxVJ = indice;
                if (tipoLista == 0) {
                    fechaVigenciaVJ = listVigenciasJornadas.get(index).getFechavigencia();
                    secRegistroVJ = listVigenciasJornadas.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        nombreJornada = listVigenciasJornadas.get(index).getJornadatrabajo().getDescripcion();
                    } else if (cualCelda == 2) {
                        tipoDescanso = listVigenciasJornadas.get(index).getTipodescanso().getDescripcion();
                    }
                }
                if (tipoLista == 1) {
                    fechaVigenciaVJ = filtrarVigenciasJornadas.get(index).getFechavigencia();
                    secRegistroVJ = filtrarVigenciasJornadas.get(index).getSecuencia();
                    if (cualCelda == 1) {
                        nombreJornada = filtrarVigenciasJornadas.get(index).getJornadatrabajo().getDescripcion();
                    } else if (cualCelda == 2) {
                        tipoDescanso = filtrarVigenciasJornadas.get(index).getTipodescanso().getDescripcion();
                    }
                }
                listVigenciasCompensacionesTiempo = null;
                listVigenciasCompensacionesTiempo = getListVigenciasCompensacionesTiempo();
                listVigenciasCompensacionesDinero = null;
                listVigenciasCompensacionesDinero = getListVigenciasCompensacionesDinero();
            }
            if (cambioVigenciaCT == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaCompensacionTiempo");
                context.execute("guardarCambiosVigenciaCompensacionTiempo.show()");

            }
            if (cambioVigenciaCD == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaCompensacionDinero");
                context.execute("guardarCambiosVigenciaCompensacionDinero.show()");

            }
        }

        if (banderaVCT == 1) {
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        if (banderaVCD == 1) {
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        indexVCT = -1;
        secRegistroVCT = null;
        indexVCD = -1;
        secRegistroVCD = null;
        RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
        RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVCT(int indice, int celda) {
        if (permitirIndexVCT == true) {
            if (cambioVigenciaCD == false) {
                indexVCT = indice;
                cualCeldaVCT = celda;
                if(tipoListaVCT==0){
                secRegistroVCT = listVigenciasCompensacionesTiempo.get(indexVCT).getSecuencia();
                }
                if(tipoListaVCT==1){
                secRegistroVCT = filtrarVigenciasCompensacionesTiempo.get(indexVCT).getSecuencia();
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaCompensacionDinero");
                context.execute("guardarCambiosVigenciaCompensacionDinero.show()");
            }
        }
        if (bandera == 1) {
            vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
            vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
            vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
            vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
            vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
            bandera = 0;
            filtrarVigenciasJornadas = null;
            tipoLista = 0;
        }
        if (banderaVCD == 1) {
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        index = -1;
        secRegistroVJ = null;
        indexVCD = -1;
        secRegistroVCD = null;

    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasProrrateosProyectos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVCD(int indice, int celda) {
        if (permitirIndexVCD == true) {
            if (cambioVigenciaCT == false) {
                indexVCD = indice;
                cualCeldaVCD = celda;
                secRegistroVCD = listVigenciasCompensacionesDinero.get(indexVCD).getSecuencia();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:guardarCambiosVigenciaCompensacionTiempo");
                context.execute("guardarCambiosVigenciaCompensacionTiempo.show()");
            }
        }
        if (bandera == 1) {
            vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
            vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
            vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
            vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
            vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
            bandera = 0;
            filtrarVigenciasJornadas = null;
            tipoLista = 0;
        }
        if (banderaVCT == 1) {
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        index = -1;
        secRegistroVJ = null;
        indexVCT = -1;
        secRegistroVCT = null;
    }
    //GUARDAR

    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosVJ();
        if (listVigenciasCompensacionesTiempo != null) {
            guardarCambiosVCT();
        }
        if (listVigenciasCompensacionesDinero != null) {
            guardarCambiosVCD();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosVJ() {
        if (guardado == false) {
            if (!listVJBorrar.isEmpty()) {
                for (int i = 0; i < listVJBorrar.size(); i++) {
                    if (listVJBorrar.get(i).getJornadatrabajo().getSecuencia() == null) {
                        listVJBorrar.get(i).setJornadatrabajo(null);
                    }
                    if (listVJBorrar.get(i).getTipodescanso().getSecuencia() == null) {
                        listVJBorrar.get(i).setTipodescanso(null);
                    }
                    administrarVigenciasJornadas.borrarVJ(listVJBorrar.get(i));
                }
                listVJBorrar.clear();
            }
            if (!listVJCrear.isEmpty()) {
                for (int i = 0; i < listVJCrear.size(); i++) {
                    if (listVJCrear.get(i).getJornadatrabajo().getSecuencia() == null) {
                        listVJCrear.get(i).setJornadatrabajo(null);
                    }
                    if (listVJCrear.get(i).getTipodescanso().getSecuencia() == null) {
                        listVJCrear.get(i).setTipodescanso(null);
                    }
                    administrarVigenciasJornadas.crearVJ(listVJCrear.get(i));
                }
                listVJCrear.clear();
            }
            if (!listVJModificar.isEmpty()) {
                administrarVigenciasJornadas.modificarVJ(listVJModificar);
                listVJModificar.clear();
            }
            listVigenciasJornadas = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVJEmpleado");
            k = 0;
        }
        index = -1;
        secRegistroVJ = null;
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVCT() {
        if (guardado == false) {
            if (!listVCTBorrar.isEmpty()) {
                for (int i = 0; i < listVCTBorrar.size(); i++) {
                    administrarVigenciasJornadas.borrarVC(listVCTBorrar.get(i));
                }
                listVCTBorrar.clear();
            }
            if (!listVCTCrear.isEmpty()) {
                for (int i = 0; i < listVCTCrear.size(); i++) {
                    administrarVigenciasJornadas.crearVC(listVCTCrear.get(i));
                }
                listVCTCrear.clear();
            }
            if (!listVCTModificar.isEmpty()) {
                administrarVigenciasJornadas.modificarVC(listVCTModificar);
                listVCTModificar.clear();
            }
            listVigenciasCompensacionesTiempo = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCT");
            k = 0;
        }
        cambioVigenciaCT = false;
        indexVCT = -1;
        secRegistroVCT = null;
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasProrrateosProyectos
     */
    public void guardarCambiosVCD() {
        if (guardado == false) {
            if (!listVCDBorrar.isEmpty()) {
                for (int i = 0; i < listVCDBorrar.size(); i++) {
                    administrarVigenciasJornadas.borrarVC(listVCDBorrar.get(i));
                }
                listVCDBorrar.clear();
            }
            if (!listVCDCrear.isEmpty()) {
                for (int i = 0; i < listVCDCrear.size(); i++) {
                    administrarVigenciasJornadas.crearVC(listVCDCrear.get(i));
                }
                listVCDCrear.clear();
            }
            if (!listVCDModificar.isEmpty()) {
                administrarVigenciasJornadas.modificarVC(listVCDModificar);
                listVCDModificar.clear();
            }
            listVigenciasCompensacionesDinero = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCD");
            k = 0;
        }
        cambioVigenciaCD = false;
        indexVCD = -1;
        secRegistroVCD = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
            vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
            vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
            vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
            vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
            bandera = 0;
            filtrarVigenciasJornadas = null;
            tipoLista = 0;
        }
        if (banderaVCT == 1) {
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        if (banderaVCD == 1) {
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        listVJBorrar.clear();
        listVCTBorrar.clear();
        listVCDBorrar.clear();
        listVJCrear.clear();
        listVCTCrear.clear();
        listVCDCrear.clear();
        listVJModificar.clear();
        listVCTModificar.clear();
        listVCDModificar.clear();
        index = -1;
        secRegistroVJ = null;
        indexVCT = -1;
        secRegistroVCT = null;
        indexVCD = -1;
        secRegistroVCD = null;
        k = 0;
        listVigenciasJornadas = null;
        listVigenciasCompensacionesTiempo = null;
        listVigenciasCompensacionesDinero = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVJEmpleado");
        context.update("form:datosVigenciaCT");
        context.update("form:datosVigenciaCD");
        cambioVigenciaCT = false;
        cambioVigenciaCD = false;
        existeVCD = false;
        existeVCT = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVCT() {
        if (banderaVCT == 1) {
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        listVCTBorrar.clear();
        listVCTCrear.clear();
        listVCTModificar.clear();
        indexVCT = -1;
        secRegistroVCT = null;
        k = 0;
        listVigenciasCompensacionesTiempo = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCT");
        cambioVigenciaCT = false;
        existeVCT = false;
    }

    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos proyectos
     */
    public void cancelarModificacionVCD() {
        if (banderaVCD == 1) {
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        listVCDBorrar.clear();
        listVCDCrear.clear();
        listVCDModificar.clear();
        indexVCD = -1;
        secRegistroVCD = null;
        k = 0;
        listVigenciasCompensacionesDinero = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCD");
        cambioVigenciaCD = false;
        existeVCD = false;
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVJ = listVigenciasJornadas.get(index);
            }
            if (tipoLista == 1) {
                editarVJ = filtrarVigenciasJornadas.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaVigencia");
                context.execute("editarFechaVigencia.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombreJornada");
                context.execute("editarNombreJornada.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoDescanso");
                context.execute("editarTipoDescanso.show()");
                cualCelda = -1;
            }
        }
        if (indexVCT >= 0) {
            if (tipoListaVCT == 0) {
                editarVCT = listVigenciasCompensacionesTiempo.get(indexVCT);
            }
            if (tipoListaVCT == 1) {
                editarVCT = filtrarVigenciasCompensacionesTiempo.get(indexVCT);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaVCT == 0) {
                context.update("formularioDialogos:editarFechaInicialVCT");
                context.execute("editarFechaInicialVCT.show()");
                cualCeldaVCT = -1;
            } else if (cualCeldaVCT == 1) {
                context.update("formularioDialogos:editarFechaFinalVCT");
                context.execute("editarFechaFinalVCT.show()");
                cualCeldaVCT = -1;
            } else if (cualCeldaVCT == 2) {
                context.update("formularioDialogos:editarComentarioVCT");
                context.execute("editarComentarioVCT.show()");
                cualCeldaVCT = -1;
            }
        }
        if (indexVCD >= 0) {
            if (tipoListaVCD == 0) {
                editarVCD = listVigenciasCompensacionesDinero.get(indexVCD);
            }
            if (tipoListaVCD == 1) {
                editarVCD = filtrarVigenciasCompensacionesDinero.get(tipoListaVCD);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaVCD == 0) {
                context.update("formularioDialogos:editarFechaInicialVCD");
                context.execute("editarFechaInicialVCD.show()");
                cualCeldaVCD = -1;
            } else if (cualCeldaVCD == 1) {
                context.update("formularioDialogos:editarFechaFinalVCD");
                context.execute("editarFechaFinalVCD.show()");
                cualCeldaVCD = -1;
            } else if (cualCeldaVCD == 2) {
                context.update("formularioDialogos:editarComentarioVCD");
                context.execute("editarComentarioVCD.show()");
                cualCeldaVCD = -1;
            }
        }
        index = -1;
        secRegistroVJ = null;
        indexVCT = -1;
        secRegistroVCT = null;
        indexVCD = -1;
        secRegistroVCD = null;
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevaVJ() {
        if (nuevaVigencia.getFechavigencia() != null && nuevaVigencia.getJornadatrabajo().getSecuencia() != null) {
            if (validarFechasRegistroVJ(1) == true) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
                    vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
                    vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
                    vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
                    vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
                    bandera = 0;
                    filtrarVigenciasJornadas = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(l);
                nuevaVigencia.setEmpleado(empleado);
                listVJCrear.add(nuevaVigencia);
                listVigenciasJornadas.add(nuevaVigencia);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVJEmpleado");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("NuevoRegistroVJ.hide()");
                index = -1;
                secRegistroVJ = null;
                nuevaVigencia = new VigenciasJornadas();
                nuevaVigencia.setJornadatrabajo(new JornadasLaborales());
                nuevaVigencia.setTipodescanso(new TiposDescansos());
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaVJ.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVJ() {
        nuevaVigencia = new VigenciasJornadas();
        nuevaVigencia.setJornadatrabajo(new JornadasLaborales());
        nuevaVigencia.setTipodescanso(new TiposDescansos());
        index = -1;
        secRegistroVJ = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVCT() {
        cambioVigenciaCT = true;
        //CERRAR FILTRADO
        if (banderaVCT == 1) {
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigenciaCT.setSecuencia(l);
        nuevaVigenciaCT.setVigenciajornada(listVigenciasJornadas.get(indexAuxVJ));
        nuevaVigenciaCT.setTipocompensacion("DESCANSO");
        nuevaVigenciaCT.setNovedadturnorotativo(null);
        String auxiliar = nuevaVigenciaCT.getComentario();
        nuevaVigenciaCT.setComentario(auxiliar.toUpperCase());
        listVCTCrear.add(nuevaVigenciaCT);
        nuevaVigenciaCT = new VigenciasCompensaciones();
        index = indexAuxVJ;
        listVigenciasCompensacionesTiempo = null;
        listVigenciasCompensacionesTiempo = getFiltrarVigenciasCompensacionesTiempo();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCT");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        indexVCT = -1;
        secRegistroVCT = null;
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaVCT() {
        nuevaVigenciaCT = new VigenciasCompensaciones();
        indexVCT = -1;
        secRegistroVCT = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva vigencia prorrateo proyecto
     */
    public void agregarNuevaVCD() {
        cambioVigenciaCD = true;
        //CERRAR FILTRADO
        if (banderaVCT == 1) {
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigenciaCD.setSecuencia(l);
        nuevaVigenciaCD.setVigenciajornada(listVigenciasJornadas.get(indexAuxVJ));
        nuevaVigenciaCD.setTipocompensacion("DINERO");
        nuevaVigenciaCD.setNovedadturnorotativo(null);
        String auxiliar = nuevaVigenciaCD.getComentario();
        nuevaVigenciaCD.setComentario(auxiliar.toUpperCase());
        listVCDCrear.add(nuevaVigenciaCD);
        nuevaVigenciaCT = new VigenciasCompensaciones();
        index = indexAuxVJ;
        listVigenciasCompensacionesDinero = null;
        listVigenciasCompensacionesDinero = getFiltrarVigenciasCompensacionesDinero();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCD");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        indexVCD = -1;
        secRegistroVCD = null;
    }

    /**
     * Elimina los datos de una nueva vigencia prorrateo proyecto
     */
    public void limpiarNuevaVCD() {
        nuevaVigenciaCD = new VigenciasCompensaciones();
        indexVCD = -1;
        secRegistroVCD = null;
    }
    //DUPLICAR VL

    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        if (index >= 0) {
            duplicarVigenciaJ();
        }
        if (indexVCT >= 0) {
            duplicarVigenciaCT();
        }
        if (indexVCD >= 0) {
            duplicarVigenciaCD();
        }
    }

    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarVigenciaJ() {
        if (index >= 0) {
            duplicarVJ = new VigenciasJornadas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVJ.setSecuencia(l);
                duplicarVJ.setEmpleado(listVigenciasJornadas.get(index).getEmpleado());
                duplicarVJ.setFechavigencia(listVigenciasJornadas.get(index).getFechavigencia());
                duplicarVJ.setJornadatrabajo(listVigenciasJornadas.get(index).getJornadatrabajo());
                duplicarVJ.setTipodescanso(listVigenciasJornadas.get(index).getTipodescanso());
            }
            if (tipoLista == 1) {
                duplicarVJ.setSecuencia(l);
                duplicarVJ.setEmpleado(filtrarVigenciasJornadas.get(index).getEmpleado());
                duplicarVJ.setFechavigencia(filtrarVigenciasJornadas.get(index).getFechavigencia());
                duplicarVJ.setJornadatrabajo(filtrarVigenciasJornadas.get(index).getJornadatrabajo());
                duplicarVJ.setTipodescanso(filtrarVigenciasJornadas.get(index).getTipodescanso());
            }
            if (duplicarVJ.getJornadatrabajo() == null) {
                duplicarVJ.setJornadatrabajo(new JornadasLaborales());
            }
            if (duplicarVJ.getTipodescanso() == null) {
                duplicarVJ.setTipodescanso(new TiposDescansos());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVJ");
            context.execute("DuplicarRegistroVJ.show()");
            index = -1;
            secRegistroVJ = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        if (duplicarVJ.getFechavigencia() != null && duplicarVJ.getJornadatrabajo().getSecuencia() != null) {
            if (validarFechasRegistroVJ(2) == true) {
                listVigenciasJornadas.add(duplicarVJ);
                listVJCrear.add(duplicarVJ);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVJEmpleado");
                context.execute("DuplicarRegistroVJ.hide()");
                index = -1;
                secRegistroVJ = null;
                if (guardado == true) {
                    guardado = false;
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
                    vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                    vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
                    vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
                    vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
                    vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
                    bandera = 0;
                    filtrarVigenciasJornadas = null;
                    tipoLista = 0;
                }
                duplicarVJ = new VigenciasJornadas();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("negacionNuevaVJ.show()");
        }

    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia Localizacion
     */
    public void limpiarduplicarVJ() {
        duplicarVJ = new VigenciasJornadas();
        duplicarVJ.setJornadatrabajo(new JornadasLaborales());
        duplicarVJ.setTipodescanso(new TiposDescansos());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarVigenciaCT() {
        if (indexVCT >= 0) {
            duplicarVCT = new VigenciasCompensaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoListaVCT == 0) {
                duplicarVCT.setSecuencia(l);
                duplicarVCT.setFechafinal(listVigenciasCompensacionesTiempo.get(indexVCT).getFechainicial());
                duplicarVCT.setFechainicial(listVigenciasCompensacionesTiempo.get(indexVCT).getFechafinal());
                duplicarVCT.setComentario(listVigenciasCompensacionesTiempo.get(indexVCT).getComentario());
            }
            if (tipoListaVCT == 1) {
                duplicarVCT.setSecuencia(l);
                duplicarVCT.setFechafinal(filtrarVigenciasCompensacionesTiempo.get(indexVCT).getFechainicial());
                duplicarVCT.setFechainicial(filtrarVigenciasCompensacionesTiempo.get(indexVCT).getFechafinal());
                duplicarVCT.setComentario(filtrarVigenciasCompensacionesTiempo.get(indexVCT).getComentario());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoVCT");
            context.execute("DuplicadoRegistroVCT.show()");
            indexVCT = -1;
            secRegistroVCT = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVCT() {
        cambioVigenciaCT = true;
        listVigenciasCompensacionesTiempo.add(duplicarVCT);
        listVCTCrear.add(duplicarVCT);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCT");
        indexVCT = -1;
        secRegistroVCT = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (banderaVCT == 1) {
            //CERRAR FILTRADO
            vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
            vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
            vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
            vCTComentario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
            banderaVCT = 0;
            filtrarVigenciasCompensacionesTiempo = null;
            tipoListaVCT = 0;
        }
        duplicarVCT = new VigenciasCompensaciones();
    }

    /**
     * Limpia los elementos del duplicar registro Vigencia Prorrateo
     */
    public void limpiarduplicarVCT() {
        duplicarVCT = new VigenciasCompensaciones();
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica un registo Vigencia Prorrateo Proyecto
     */
    public void duplicarVigenciaCD() {
        cambioVigenciaCD = true;
        if (indexVCD >= 0) {
            duplicarVCD = new VigenciasCompensaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoListaVCD == 0) {
                duplicarVCD.setSecuencia(l);
                duplicarVCD.setFechafinal(listVigenciasCompensacionesDinero.get(indexVCD).getFechafinal());
                duplicarVCD.setFechainicial(listVigenciasCompensacionesDinero.get(indexVCD).getFechainicial());
                duplicarVCD.setComentario(listVigenciasCompensacionesDinero.get(indexVCD).getComentario());
            }
            if (tipoListaVCD == 1) {
                duplicarVCD.setSecuencia(l);
                duplicarVCD.setFechafinal(filtrarVigenciasCompensacionesDinero.get(indexVCD).getFechafinal());
                duplicarVCD.setFechainicial(filtrarVigenciasCompensacionesDinero.get(indexVCD).getFechainicial());
                duplicarVCD.setComentario(filtrarVigenciasCompensacionesDinero.get(indexVCD).getComentario());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVCD");
            context.execute("DuplicarRegistroVCD.show()");
            indexVCD = -1;
            secRegistroVCD = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * Vigencia Prorrateo Proyecto
     */
    public void confirmarDuplicarVCD() {
        listVigenciasCompensacionesDinero.add(duplicarVCD);
        listVCDCrear.add(duplicarVCD);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaCD");
        indexVCD = -1;
        secRegistroVCD = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (banderaVCD == 1) {
            //CERRAR FILTRADO
            vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
            vCDComentario.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
            vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
            vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
            banderaVCD = 0;
            filtrarVigenciasCompensacionesDinero = null;
            tipoListaVCD = 0;
        }
        duplicarVCD = new VigenciasCompensaciones();
    }

    /**
     * Limpia el registro de duplicar Vigencia Prorrateo Proyecto
     */
    public void limpiarduplicarVCD() {
        duplicarVCD = new VigenciasCompensaciones();
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoVigencia() {
        if (index >= 0) {
            if (listVigenciasCompensacionesDinero != null) {
                if (listVigenciasCompensacionesDinero.isEmpty()) {
                    listVigenciasCompensacionesDinero = null;
                }
            }
            if (listVigenciasCompensacionesTiempo != null) {
                if (listVigenciasCompensacionesTiempo.isEmpty()) {
                    listVigenciasCompensacionesTiempo = null;
                }
            }
            if ((listVigenciasCompensacionesTiempo == null) && (listVigenciasCompensacionesDinero == null)) {
                borrarVJ();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:negacionBorradoVJ");
                context.execute("negacionBorradoVJ.show()");
            }
        }
        if (indexVCT >= 0) {
            borrarVCT();
        }
        if (indexVCD >= 0) {
            borrarVCD();
        }
    }

    //BORRAR VL
    /**
     * Metodo que borra una vigencia localizacion
     */
    public void borrarVJ() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVJModificar.isEmpty() && listVJModificar.contains(listVigenciasJornadas.get(index))) {
                    int modIndex = listVJModificar.indexOf(listVigenciasJornadas.get(index));
                    listVJModificar.remove(modIndex);
                    listVJBorrar.add(listVigenciasJornadas.get(index));
                } else if (!listVJCrear.isEmpty() && listVJCrear.contains(listVigenciasJornadas.get(index))) {
                    int crearIndex = listVJCrear.indexOf(listVigenciasJornadas.get(index));
                    listVJCrear.remove(crearIndex);
                } else {
                    listVJBorrar.add(listVigenciasJornadas.get(index));
                }
                listVigenciasJornadas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVJModificar.isEmpty() && listVJModificar.contains(filtrarVigenciasJornadas.get(index))) {
                    int modIndex = listVJModificar.indexOf(filtrarVigenciasJornadas.get(index));
                    listVJModificar.remove(modIndex);
                    listVJBorrar.add(filtrarVigenciasJornadas.get(index));
                } else if (!listVJCrear.isEmpty() && listVJCrear.contains(filtrarVigenciasJornadas.get(index))) {
                    int crearIndex = listVJCrear.indexOf(filtrarVigenciasJornadas.get(index));
                    listVJCrear.remove(crearIndex);
                } else {
                    listVJBorrar.add(filtrarVigenciasJornadas.get(index));
                }
                int VLIndex = listVigenciasJornadas.indexOf(filtrarVigenciasJornadas.get(index));
                listVigenciasJornadas.remove(VLIndex);
                filtrarVigenciasJornadas.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVJEmpleado");
            index = -1;
            secRegistroVJ = null;

            if (guardado == true) {
                guardado = false;
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarVCT() {
        cambioVigenciaCT = true;
        if (indexVCT >= 0) {
            if (tipoListaVCT == 0) {
                if (!listVCTModificar.isEmpty() && listVCTModificar.contains(listVigenciasCompensacionesTiempo.get(indexVCT))) {
                    int modIndex = listVCTModificar.indexOf(listVigenciasCompensacionesTiempo.get(indexVCT));
                    listVCTModificar.remove(modIndex);
                    listVCTBorrar.add(listVigenciasCompensacionesTiempo.get(indexVCT));
                } else if (!listVCTCrear.isEmpty() && listVCTCrear.contains(listVigenciasCompensacionesTiempo.get(indexVCT))) {
                    int crearIndex = listVCTCrear.indexOf(listVigenciasCompensacionesTiempo.get(indexVCT));
                    listVCTCrear.remove(crearIndex);
                } else {
                    listVCTBorrar.add(listVigenciasCompensacionesTiempo.get(indexVCT));
                }
                listVigenciasCompensacionesTiempo.remove(indexVCT);
            }
            if (tipoListaVCT == 1) {
                if (!listVCTModificar.isEmpty() && listVCTModificar.contains(filtrarVigenciasCompensacionesTiempo.get(indexVCT))) {
                    int modIndex = listVCTModificar.indexOf(filtrarVigenciasCompensacionesTiempo.get(indexVCT));
                    listVCTModificar.remove(modIndex);
                    listVCTBorrar.add(filtrarVigenciasCompensacionesTiempo.get(indexVCT));
                } else if (!listVCTCrear.isEmpty() && listVCTCrear.contains(filtrarVigenciasCompensacionesTiempo.get(indexVCT))) {
                    int crearIndex = listVCTCrear.indexOf(filtrarVigenciasCompensacionesTiempo.get(indexVCT));
                    listVCTCrear.remove(crearIndex);
                } else {
                    listVCTBorrar.add(filtrarVigenciasCompensacionesTiempo.get(indexVCT));
                }
                int VPIndex = listVigenciasCompensacionesTiempo.indexOf(filtrarVigenciasCompensacionesTiempo.get(indexVCT));
                listVigenciasCompensacionesTiempo.remove(VPIndex);
                filtrarVigenciasCompensacionesTiempo.remove(indexVCT);
            }
            index = indexAuxVJ;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCT");
            indexVCT = -1;
            secRegistroVCT = null;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo proyecto
     */
    public void borrarVCD() {
        cambioVigenciaCD = true;
        if (indexVCD >= 0) {
            if (tipoListaVCD == 0) {
                if (!listVCDModificar.isEmpty() && listVCDModificar.contains(listVigenciasCompensacionesDinero.get(indexVCD))) {
                    int modIndex = listVCDModificar.indexOf(listVigenciasCompensacionesDinero.get(indexVCD));
                    listVCDModificar.remove(modIndex);
                    listVCDBorrar.add(listVigenciasCompensacionesDinero.get(indexVCD));
                } else if (!listVCDCrear.isEmpty() && listVCDCrear.contains(listVigenciasCompensacionesDinero.get(indexVCD))) {
                    int crearIndex = listVCDCrear.indexOf(listVigenciasCompensacionesDinero.get(indexVCD));
                    listVCDCrear.remove(crearIndex);
                } else {
                    listVCDBorrar.add(listVigenciasCompensacionesDinero.get(indexVCD));
                }
                listVigenciasCompensacionesDinero.remove(indexVCD);
            }
            if (tipoListaVCD == 1) {
                if (!listVCDModificar.isEmpty() && listVCDModificar.contains(filtrarVigenciasCompensacionesDinero.get(indexVCD))) {
                    int modIndex = listVCDModificar.indexOf(filtrarVigenciasCompensacionesDinero.get(indexVCD));
                    listVCDModificar.remove(modIndex);
                    listVCDBorrar.add(filtrarVigenciasCompensacionesDinero.get(indexVCD));
                } else if (!listVCDCrear.isEmpty() && listVCDCrear.contains(filtrarVigenciasCompensacionesDinero.get(indexVCD))) {
                    int crearIndex = listVCDCrear.indexOf(filtrarVigenciasCompensacionesDinero.get(indexVCD));
                    listVCDCrear.remove(crearIndex);
                } else {
                    listVCDBorrar.add(filtrarVigenciasCompensacionesDinero.get(indexVCD));
                }
                int VPPIndex = listVigenciasCompensacionesDinero.indexOf(filtrarVigenciasCompensacionesDinero.get(indexVCD));
                listVigenciasCompensacionesDinero.remove(VPPIndex);
                filtrarVigenciasCompensacionesDinero.remove(indexVCD);
            }
            index = indexAuxVJ;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaCD");
            indexVCD = -1;
            secRegistroVCD = null;
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
            filtradoVigenciaJornada();
        }
        if (indexVCT >= 0) {
            filtradoVigenciaCompensacionTiempo();
        }
        if (indexVCD >= 0) {
            filtradoVigenciaCompensacionDinero();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoVigenciaJornada() {
        if (index >= 0) {
            if (bandera == 0) {
                vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
                vJFechaVigencia.setFilterStyle("width: 60px");
                vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
                vJNombreJornada.setFilterStyle("width: 100px");
                vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
                vJTipoDescanso.setFilterStyle("width: 100px");
                RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
                bandera = 1;
            } else if (bandera == 1) {
                vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
                vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
                vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
                vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
                vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
                bandera = 0;
                filtrarVigenciasJornadas = null;
                tipoLista = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaCompensacionTiempo() {
        if (indexVCT >= 0) {
            if (banderaVCT == 0) {
                //Columnas Tabla VPP
                vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
                vCTFechaInicial.setFilterStyle("width: 50px");
                vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
                vCTFechaFinal.setFilterStyle("width: 50px");
                vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
                vCTComentario.setFilterStyle("width: 50px");
                RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
                banderaVCT = 1;
            } else if (banderaVCT == 1) {
                vCTFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaInicial");
                vCTFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vCTFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTFechaFinal");
                vCTFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vCTComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCT:vCTComentario");
                vCTComentario.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaCT");
                banderaVCT = 0;
                filtrarVigenciasCompensacionesTiempo = null;
                tipoListaVCT = 0;
            }
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo proyecto
     */
    public void filtradoVigenciaCompensacionDinero() {
        if (indexVCD >= 0) {
            //Columnas Tabla VPP
            if (banderaVCD == 0) {
                vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
                vCDComentario.setFilterStyle("width: 25px");
                vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
                vCDFechaInicial.setFilterStyle("width: 50px");
                vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
                vCDFechaFinal.setFilterStyle("width: 50px");
                RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
                banderaVCD = 1;
            } else if (banderaVCD == 1) {
                vCDComentario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDComentario");
                vCDComentario.setFilterStyle("display: none; visibility: hidden;");
                vCDFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaInicial");
                vCDFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vCDFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaCD:vCDFechaFinal");
                vCDFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaCD");
                banderaVCD = 0;
                filtrarVigenciasCompensacionesDinero = null;
                tipoListaVCD = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            vJFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJFechaVigencia");
            vJFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            vJNombreJornada = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJNombreJornada");
            vJNombreJornada.setFilterStyle("display: none; visibility: hidden;");
            vJTipoDescanso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVJEmpleado:vJTipoDescanso");
            vJTipoDescanso.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVJEmpleado");
            bandera = 0;
            filtrarVigenciasJornadas = null;
            tipoLista = 0;
        }

        listVJBorrar.clear();
        listVJCrear.clear();
        listVJModificar.clear();
        index = -1;
        secRegistroVJ = null;
        indexVCT = -1;
        secRegistroVCT = null;
        indexVCD = -1;
        secRegistroVCD = null;
        k = 0;
        listVigenciasJornadas = null;
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
                context.update("form:JornadaLaboralDialogo");
                context.execute("JornadaLaboralDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:TiposDescansosDialogo");
                context.execute("TiposDescansosDialogo.show()");
            }
        }
    }

    //LOVS
    //Estructuras
    /**
     * Metodo que actualiza la estructura seleccionada (vigencia localizacion)
     */
    public void actualizarJornadaLaboral() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasJornadas.get(index).setJornadatrabajo(jornadaLaboralSeleccionada);
                if (!listVJCrear.contains(listVigenciasJornadas.get(index))) {
                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(listVigenciasJornadas.get(index));
                    } else if (!listVJModificar.contains(listVigenciasJornadas.get(index))) {
                        listVJModificar.add(listVigenciasJornadas.get(index));
                    }
                }
            } else {
                filtrarVigenciasJornadas.get(index).setJornadatrabajo(jornadaLaboralSeleccionada);
                if (!listVJCrear.contains(filtrarVigenciasJornadas.get(index))) {
                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(index));
                    } else if (!listVJModificar.contains(filtrarVigenciasJornadas.get(index))) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(index));
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
            nuevaVigencia.setJornadatrabajo(jornadaLaboralSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVJ");
        } else if (tipoActualizacion == 2) {
            duplicarVJ.setJornadatrabajo(jornadaLaboralSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVJ");
        }
        filtrarJornadasLaborales = null;
        jornadaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroVJ = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre estructura (vigencia localizacion)
     */
    public void cancelarCambioJornadaLaboral() {
        filtrarJornadasLaborales = null;
        jornadaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroVJ = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarTipoDescanso() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasJornadas.get(index).setTipodescanso(tipoDescansoSeleccionado);
                if (!listVJCrear.contains(listVigenciasJornadas.get(index))) {
                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(listVigenciasJornadas.get(index));
                    } else if (!listVJModificar.contains(listVigenciasJornadas.get(index))) {
                        listVJModificar.add(listVigenciasJornadas.get(index));
                    }
                }
            } else {
                filtrarVigenciasJornadas.get(index).setTipodescanso(tipoDescansoSeleccionado);
                if (!listVJCrear.contains(filtrarVigenciasJornadas.get(index))) {
                    if (listVJModificar.isEmpty()) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(index));
                    } else if (!listVJModificar.contains(filtrarVigenciasJornadas.get(index))) {
                        listVJModificar.add(filtrarVigenciasJornadas.get(index));
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
            nuevaVigencia.setTipodescanso(tipoDescansoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVJ");
        } else if (tipoActualizacion == 2) {
            duplicarVJ.setTipodescanso(tipoDescansoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVJ");
        }
        filtrarTiposDescansos = null;
        tipoDescansoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVJ = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioTipoDescanso() {
        filtrarTiposDescansos = null;
        tipoDescansoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroVJ = null;
        tipoActualizacion = -1;
        permitirIndex = true;
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
                context.update("form:JornadaLaboralDialogo");
                context.execute("JornadaLaboralDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 2) {
                context.update("form:TiposDescansosDialogo");
                context.execute("TiposDescansosDialogo.show()");
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
            boolean existeVJ = false;
            VigenciasJornadas vigJornada = listVigenciasJornadas.get(index);
            List<VigenciasJornadas> listaVJ = administrarVigenciasJornadas.VigenciasJornadasEmpleado(empleado.getSecuencia());
            for (int i = 0; i < listaVJ.size(); i++) {
                if (listaVJ.get(i).getSecuencia().equals(vigJornada.getSecuencia())) {
                    existeVJ = true;
                }
            }
            if (existeVJ == true) {
                if ((existeVCD == false) || (existeVCT == false)) {
                    context.update("form:NuevoRegistroPagina");
                    context.execute("NuevoRegistroPagina.show()");
                } else {
                    nuevaVigencia = new VigenciasJornadas();
                    nuevaVigencia.setJornadatrabajo(new JornadasLaborales());
                    nuevaVigencia.setTipodescanso(new TiposDescansos());
                    context.update("form:NuevoRegistroVJ");
                    context.execute("NuevoRegistroVJ.show()");
                }
            } else {
                context.update("form:confirmarGuardarVJ");
                context.execute("confirmarGuardarVJ.show()");
            }
        }
        if (indexVCT >= 0) {
            nuevaVigenciaCT = new VigenciasCompensaciones();
            context.update("form:NuevoRegistroVCT");
            context.execute("NuevoRegistroVCT.show()");
        }
        if (indexVCD >= 0) {
            nuevaVigenciaCD = new VigenciasCompensaciones();
            context.update("form:NuevoRegistroVCD");
            context.execute("NuevoRegistroVCD.show()");
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
            nombreTabla = ":formExportarVJ:datosVJEmpleadoExportar";
            nombreXML = "VigenciasJornadasXML";
        }
        if (indexVCT >= 0) {
            nombreTabla = ":formExportarVCT:datosVCTVigenciaExportar";
            nombreXML = "VigenciasCompensacionesTiempoXML";
        }
        if (indexVCD >= 0) {
            nombreTabla = ":formExportarVCD:datosVCDVigenciaExportar";
            nombreXML = "VigenciasCompensacionesDineroXML";
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
            exportPDVJ();
        }
        if (indexVCT >= 0) {
            exportPDVCT();
        }
        if (indexVCD >= 0) {
            exportPDFVCD();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDVJ() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVJ:datosVJEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasJornadasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroVJ = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDVCT() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVCT:datosVCTVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasCompensacionesTiempoPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVCT = -1;
        secRegistroVCT = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVCD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVCD:datosVCDVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasCompensacionesDineroPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVCD = -1;
        secRegistroVCD = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLSVJ();
        }
        if (indexVCT >= 0) {
            exportXLSVCT();
        }
        if (indexVCD >= 0) {
            exportXLSVCD();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVJ() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVJ:datosVJEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasJornadasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroVJ = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVCT() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVCT:datosVCTVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasCompensacionesTiempoXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVCT = -1;
        secRegistroVCT = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Prorrateo Proyecto
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVCD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVCD:datosVCDVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasCompensacionesDineroXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVCD = -1;
        secRegistroVCD = null;
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
        if (indexVCT >= 0) {
            if (tipoListaVCT == 0) {
                tipoListaVCT = 1;
            }
        }
        if (indexVCD >= 0) {
            if (tipoListaVCD == 0) {
                tipoListaVCD = 1;
            }
        }

    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void verificarRastroTabla() {
        if (listVigenciasJornadas == null || listVigenciasCompensacionesTiempo == null || listVigenciasCompensacionesDinero == null) {
            //Dialogo para seleccionar el rato de la tabla deseada
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        }

        if ((listVigenciasJornadas != null) && (listVigenciasCompensacionesTiempo != null) && (listVigenciasCompensacionesDinero != null)) {
            if (index >= 0) {
                verificarRastroVigenciaJornada();
                index = -1;

            }
            if (indexVCD >= 0) {
                verificarRastroVigenciaCompensacionDinero();
                indexVCD = -1;
            }
            if (indexVCT >= 0) {
                verificarRastroVigenciaCompensacionTiempo();
                indexVCT = -1;
            }
        }
    }

    public void verificarRastroVigenciaJornada() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasJornadas != null) {
            if (secRegistroVJ != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVJ, "VIGENCIASJORNADAS");
                backUpSecRegistroVJ = secRegistroVJ;
                backUp = secRegistroVJ;
                secRegistroVJ = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasJornadas";
                    msnConfirmarRastro = "La tabla VIGENCIASJORNADAS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASJORNADAS")) {
                nombreTablaRastro = "VigenciasJornadas";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASJORNADAS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroVigenciaCompensacionTiempo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasCompensacionesTiempo != null) {
            if (secRegistroVCT != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVCT, "VIGENCIASCOMPENSACIONES");
                backUpSecRegistroVCT = secRegistroVCT;
                backUp = secRegistroVCT;
                secRegistroVCT = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasCompensaciones";
                    msnConfirmarRastro = "La tabla VIGENCIASCOMPENSACIONES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCOMPENSACIONES")) {
                nombreTablaRastro = "VigenciasCompensaciones";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCOMPENSACIONES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVCT = -1;
    }

    public void verificarRastroVigenciaCompensacionDinero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasCompensacionesDinero != null) {
            if (secRegistroVCD != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVCD, "VIGENCIASCOMPENSACIONES");
                backUpSecRegistroVCD = secRegistroVCD;
                backUp = secRegistroVCD;
                secRegistroVCD = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasCompensaciones";
                    msnConfirmarRastro = "La tabla VIGENCIASCOMPENSACIONES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCOMPENSACIONES")) {
                nombreTablaRastro = "VigenciasCompensaciones";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASCOMPENSACIONES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVCD = -1;
    }

    public List<VigenciasJornadas> getListVigenciasJornadas() {
        try {
            if (listVigenciasJornadas == null) {
                listVigenciasJornadas = new ArrayList<VigenciasJornadas>();
                listVigenciasJornadas = administrarVigenciasJornadas.VigenciasJornadasEmpleado(empleado.getSecuencia());
                return listVigenciasJornadas;
            } else {
                return listVigenciasJornadas;
            }
        } catch (Exception e) {
            System.out.println("Error getVigenciaLocalizaciones " + e.toString());
            return null;
        }
    }

    public void setListVigenciasJornadas(List<VigenciasJornadas> vigenciasJornadas) {
        this.listVigenciasJornadas = vigenciasJornadas;
    }

    public List<VigenciasJornadas> getFiltrarVigenciasJornadas() {
        try {
            return filtrarVigenciasJornadas;
        } catch (Exception e) {
            System.out.println("Error getFiltrarVL");
            return null;
        }
    }

    public void setFiltrarVigenciasJornadas(List<VigenciasJornadas> filtrarVJ) {
        try {
            this.filtrarVigenciasJornadas = filtrarVJ;
        } catch (Exception e) {
            System.out.println("Se estallo: " + e);
        }

    }

    public List<JornadasLaborales> getListJornadasLaborales() {
        if (listJornadasLaborales.isEmpty()) {
            listJornadasLaborales = administrarVigenciasJornadas.jornadasLaborales();
        }
        return listJornadasLaborales;
    }

    public void setListJornadasLaborales(List<JornadasLaborales> jornadasLaborales) {
        this.listJornadasLaborales = jornadasLaborales;
    }

    public JornadasLaborales getJornadaLaboralSeleccionada() {
        return jornadaLaboralSeleccionada;
    }

    public void setJornadaLaboralSeleccionada(JornadasLaborales jornadasLaborales) {
        this.jornadaLaboralSeleccionada = jornadasLaborales;
    }

    public List<JornadasLaborales> getFiltrarJornadasLaborales() {
        return filtrarJornadasLaborales;
    }

    public void setFiltrarJornadasLaborales(List<JornadasLaborales> jornadasLaborales) {
        this.filtrarJornadasLaborales = jornadasLaborales;
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

    public List<VigenciasJornadas> getListVJModificar() {
        return listVJModificar;
    }

    public void setListVJModificar(List<VigenciasJornadas> listVJModificar) {
        this.listVJModificar = listVJModificar;
    }

    public VigenciasJornadas getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasJornadas nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public List<VigenciasJornadas> getListVJCrear() {
        return listVJCrear;
    }

    public void setListVJCrear(List<VigenciasJornadas> listVJCrear) {
        this.listVJCrear = listVJCrear;
    }

    public List<VigenciasJornadas> getListVJBorrar() {
        return listVJBorrar;
    }

    public void setListVJBorrar(List<VigenciasJornadas> listVJBorrar) {
        this.listVJBorrar = listVJBorrar;
    }

    public VigenciasJornadas getEditarVJ() {
        return editarVJ;
    }

    public void setEditarVJ(VigenciasJornadas editarVJ) {
        this.editarVJ = editarVJ;
    }

    public VigenciasJornadas getDuplicarVJ() {
        return duplicarVJ;
    }

    public void setDuplicarVJ(VigenciasJornadas duplicarVJ) {
        this.duplicarVJ = duplicarVJ;
    }

    public List<VigenciasCompensaciones> getListVigenciasCompensacionesTiempo() {
        if (index >= 0 && (listVigenciasJornadas.get(index).getSecuencia() != null)) {
            if (listVigenciasCompensacionesTiempo != null) {
                if (listVigenciasCompensacionesTiempo.isEmpty()) {
                    listVigenciasCompensacionesTiempo = null;
                }
            }
            if (listVigenciasCompensacionesTiempo == null) {
                String variable = "DESCANSO";
                List<VigenciasCompensaciones> listVigencia;
                listVigencia = administrarVigenciasJornadas.VigenciasCompensacionesSecVigencia(listVigenciasJornadas.get(index).getSecuencia());
                if (listVigencia != null) {
                    if (listVigencia.isEmpty()) {
                        listVigencia = null;
                    }
                }
                if (listVigencia == null) {
                    listVigenciasCompensacionesTiempo = null;
                } else {
                    listVigenciasCompensacionesTiempo = new ArrayList<VigenciasCompensaciones>();
                    for (int i = 0; i < listVigencia.size(); i++) {
                        if (listVigencia.get(i).getTipocompensacion().equalsIgnoreCase(variable)) {
                            listVigenciasCompensacionesTiempo.add(listVigencia.get(i));
                        }
                    }
                }
                if (listVigenciasCompensacionesTiempo != null) {
                    if (!listVCTModificar.isEmpty() || !listVCTBorrar.isEmpty()) {
                        for (int i = 0; i < listVigenciasCompensacionesTiempo.size(); i++) {
                            if (!listVCTModificar.isEmpty() && listVCTModificar.contains(listVigenciasCompensacionesTiempo.get(i))) {
                                int modIndex = listVCTModificar.indexOf(listVigenciasCompensacionesTiempo.get(i));

                                listVigenciasCompensacionesTiempo.get(i).setFechafinal(listVCTModificar.get(modIndex).getFechafinal());
                                listVigenciasCompensacionesTiempo.get(i).setFechainicial(listVCTModificar.get(modIndex).getFechainicial());
                                listVigenciasCompensacionesTiempo.get(i).setComentario(listVCTModificar.get(modIndex).getComentario());

                            } else if (!listVCTBorrar.isEmpty() && listVCTBorrar.contains(listVigenciasCompensacionesTiempo.get(i))) {
                                listVigenciasCompensacionesTiempo.remove(i);
                            }
                        }
                    }
                }
                if (!listVCTCrear.isEmpty()) {
                    if (listVigenciasCompensacionesTiempo == null) {
                        listVigenciasCompensacionesTiempo = new ArrayList<VigenciasCompensaciones>();
                    }
                    for (int i = 0; i < listVCTCrear.size(); i++) {
                        try {
                            listVigenciasCompensacionesTiempo.add(listVCTCrear.get(i));
                        } catch (Exception e) {
                            System.out.println("Error en cargueVigenciasCompensacionesTiempo " + e.toString());
                        }
                    }
                }
            }
        }
        if (listVigenciasCompensacionesTiempo != null) {
            if (listVigenciasCompensacionesTiempo.isEmpty()) {
                listVigenciasCompensacionesTiempo = null;
                existeVCT = false;
            } else {
                existeVCT = true;
            }
        } else {
            existeVCT = false;
        }
        return listVigenciasCompensacionesTiempo;
    }

    public void setListVigenciasCompensacionesTiempo(List<VigenciasCompensaciones> vigenciasCompensaciones) {
        this.listVigenciasCompensacionesTiempo = vigenciasCompensaciones;
    }

    public List<VigenciasCompensaciones> getFiltrarVigenciasCompensacionesTiempo() {
        return filtrarVigenciasCompensacionesTiempo;
    }

    public void setFiltrarVigenciasCompensacionesTiempo(List<VigenciasCompensaciones> vigenciasCompensaciones) {
        this.filtrarVigenciasCompensacionesTiempo = vigenciasCompensaciones;
    }

    public List<VigenciasCompensaciones> getListVigenciasCompensacionesDinero() {
        if (index >= 0 && (listVigenciasJornadas.get(index).getSecuencia() != null)) {
            if (listVigenciasCompensacionesDinero != null) {
                if (listVigenciasCompensacionesDinero.isEmpty()) {
                    listVigenciasCompensacionesDinero = null;
                }
            }
            if (listVigenciasCompensacionesDinero == null) {
                String variable = "DINERO";
                List<VigenciasCompensaciones> listVigencias;
                listVigencias = administrarVigenciasJornadas.VigenciasCompensacionesSecVigencia(listVigenciasJornadas.get(index).getSecuencia());
                if (listVigencias != null) {
                    if (listVigencias.isEmpty()) {
                        listVigencias = null;
                    }
                }
                if (listVigencias == null) {
                    listVigenciasCompensacionesDinero = null;
                } else {
                    listVigenciasCompensacionesDinero = new ArrayList<VigenciasCompensaciones>();
                    for (int i = 0; i < listVigencias.size(); i++) {
                        if (listVigencias.get(i).getTipocompensacion().equalsIgnoreCase(variable)) {
                            listVigenciasCompensacionesDinero.add(listVigencias.get(i));
                        }
                    }
                }
                if (listVigenciasCompensacionesDinero != null) {
                    for (int i = 0; i < listVigenciasCompensacionesDinero.size(); i++) {
                        //////////////
                        if (!listVCDModificar.isEmpty() && listVCDModificar.contains(listVigenciasCompensacionesDinero.get(i))) {
                            int modIndex = listVCDModificar.indexOf(listVigenciasCompensacionesDinero.get(i));

                            listVigenciasCompensacionesDinero.get(i).setFechafinal(listVCDModificar.get(modIndex).getFechafinal());
                            listVigenciasCompensacionesDinero.get(i).setFechainicial(listVCDModificar.get(modIndex).getFechainicial());
                            listVigenciasCompensacionesDinero.get(i).setComentario(listVCDModificar.get(modIndex).getComentario());

                        } else if (!listVCDBorrar.isEmpty() && listVCDBorrar.contains(listVigenciasCompensacionesDinero.get(i))) {
                            listVigenciasCompensacionesDinero.remove(i);
                        }
                    }
                }
                if (!listVCDCrear.isEmpty()) {
                    if (listVigenciasCompensacionesDinero == null) {
                        listVigenciasCompensacionesDinero = new ArrayList<VigenciasCompensaciones>();
                    }
                    for (int i = 0; i < listVCDCrear.size(); i++) {
                        listVigenciasCompensacionesDinero.add(listVCDCrear.get(i));
                    }
                }
            }
        }
        if (listVigenciasCompensacionesDinero != null) {
            if (listVigenciasCompensacionesDinero.isEmpty()) {
                listVigenciasCompensacionesDinero = null;
                existeVCD = false;
            } else {
                existeVCD = true;
            }
        } else {
            existeVCD = false;
        }
        return listVigenciasCompensacionesDinero;
    }

    public void setListVigenciasCompensacionesDinero(List<VigenciasCompensaciones> vigenciasCompensacioneses) {
        this.listVigenciasCompensacionesDinero = vigenciasCompensacioneses;
    }

    public List<VigenciasCompensaciones> getFiltrarVigenciasCompensacionesDinero() {
        return filtrarVigenciasCompensacionesDinero;
    }

    public void setFiltrarVigenciasCompensacionesDinero(List<VigenciasCompensaciones> vigenciasCompensaciones) {
        this.filtrarVigenciasCompensacionesDinero = vigenciasCompensaciones;
    }

    public VigenciasCompensaciones getEditarVCT() {
        return editarVCT;
    }

    public void setEditarVCT(VigenciasCompensaciones editarVCT) {
        this.editarVCT = editarVCT;
    }

    public VigenciasCompensaciones getEditarVCD() {
        return editarVCD;
    }

    public void setEditarVCD(VigenciasCompensaciones editarVCD) {
        this.editarVCD = editarVCD;
    }

    public List<TiposDescansos> getListTiposDescansos() {
        try {
            if (listTiposDescansos == null) {
                listTiposDescansos = administrarVigenciasJornadas.tiposDescansos();
            }
            return listTiposDescansos;
        } catch (Exception e) {
            System.out.println("Error getListTiposDescansos " + e.toString());
            return null;
        }
    }

    public void setListTiposDescansos(List<TiposDescansos> tiposDescansos) {
        this.listTiposDescansos = tiposDescansos;
    }

    public TiposDescansos getTipoDescansoSeleccionado() {
        return tipoDescansoSeleccionado;
    }

    public void setTipoDescansoSeleccionado(TiposDescansos tiposDescansos) {
        this.tipoDescansoSeleccionado = tiposDescansos;
    }

    public List<TiposDescansos> getFiltradoTiposDescansos() {
        return filtrarTiposDescansos;
    }

    public void setFiltradoTiposDescansos(List<TiposDescansos> tiposDescansos) {
        this.filtrarTiposDescansos = tiposDescansos;
    }

    public VigenciasCompensaciones getNuevaVigenciaCT() {
        return nuevaVigenciaCT;
    }

    public void setNuevaVigenciaCT(VigenciasCompensaciones nuevaVigenciaCT) {
        this.nuevaVigenciaCT = nuevaVigenciaCT;
    }

    public VigenciasCompensaciones getNuevaVigenciaCD() {
        return nuevaVigenciaCD;
    }

    public void setNuevaVigenciaCD(VigenciasCompensaciones nuevaVigenciaCD) {
        this.nuevaVigenciaCD = nuevaVigenciaCD;
    }

    public VigenciasCompensaciones getDuplicarVCT() {
        return duplicarVCT;
    }

    public void setDuplicarVCT(VigenciasCompensaciones duplicarVCT) {
        this.duplicarVCT = duplicarVCT;
    }

    public VigenciasCompensaciones getDuplicarVCD() {
        return duplicarVCD;
    }

    public void setDuplicarVCD(VigenciasCompensaciones duplicarVCD) {
        this.duplicarVCD = duplicarVCD;
    }

    public List<VigenciasCompensaciones> getListVCTCrear() {
        return listVCTCrear;
    }

    public void setListVCTCrear(List<VigenciasCompensaciones> listVCTCrear) {
        this.listVCTCrear = listVCTCrear;
    }

    public List<VigenciasCompensaciones> getListVCDCrear() {
        return listVCDCrear;
    }

    public void setListVCDCrear(List<VigenciasCompensaciones> listVCDCrear) {
        this.listVCDCrear = listVCDCrear;
    }

    public List<VigenciasCompensaciones> getListVCTModificar() {
        return listVCTModificar;
    }

    public void setListVCTModificar(List<VigenciasCompensaciones> listCTModificar) {
        this.listVCTModificar = listCTModificar;
    }

    public List<VigenciasCompensaciones> getListVCDModificar() {
        return listVCDModificar;
    }

    public void setListVCDModificar(List<VigenciasCompensaciones> listVCDModificar) {
        this.listVCDModificar = listVCDModificar;
    }

    public List<VigenciasCompensaciones> getListVCTBorrar() {
        return listVCTBorrar;
    }

    public void setListVCTBorrar(List<VigenciasCompensaciones> listVCTBorrar) {
        this.listVCTBorrar = listVCTBorrar;
    }

    public List<VigenciasCompensaciones> getListVCDBorrar() {
        return listVCDBorrar;
    }

    public void setListVCDBorrar(List<VigenciasCompensaciones> listVCDBorrar) {
        this.listVCDBorrar = listVCDBorrar;
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

    public BigInteger getSecRegistroVJ() {
        return secRegistroVJ;
    }

    public void setSecRegistroVJ(BigInteger secRegistroVJ) {
        this.secRegistroVJ = secRegistroVJ;
    }

    public BigInteger getBackUpSecRegistroVJ() {
        return backUpSecRegistroVJ;
    }

    public void setBackUpSecRegistroVJ(BigInteger backUpSecRegistroVJ) {
        this.backUpSecRegistroVJ = backUpSecRegistroVJ;
    }

    public BigInteger getSecRegistroVCT() {
        return secRegistroVCT;
    }

    public void setSecRegistroVCT(BigInteger secRegistroVCT) {
        this.secRegistroVCT = secRegistroVCT;
    }

    public BigInteger getBackUpSecRegistroVCT() {
        return backUpSecRegistroVCT;
    }

    public void setBackUpSecRegistroVCT(BigInteger backUpSecRegistroVCT) {
        this.backUpSecRegistroVCT = backUpSecRegistroVCT;
    }

    public BigInteger getSecRegistroVCD() {
        return secRegistroVCD;
    }

    public void setSecRegistroVCD(BigInteger secRegistroVCD) {
        this.secRegistroVCD = secRegistroVCD;
    }

    public BigInteger getBackUpSecRegistroVCD() {
        return backUpSecRegistroVCD;
    }

    public void setBackUpSecRegistroVCD(BigInteger backUpSecRegistroVCD) {
        this.backUpSecRegistroVCD = backUpSecRegistroVCD;
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

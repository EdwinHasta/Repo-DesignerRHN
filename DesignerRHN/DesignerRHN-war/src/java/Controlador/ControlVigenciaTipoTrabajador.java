package Controlador;

import Entidades.ClasesPensiones;
import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.Pensionados;
import Entidades.Personas;
import Entidades.Retirados;
import Entidades.TiposPensionados;
import Entidades.TiposTrabajadores;
import Entidades.TiposCotizantes;
import Entidades.VigenciasTiposTrabajadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasTiposTrabajadoresInterface;
import java.io.IOException;
import java.io.Serializable;
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
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlVigenciaTipoTrabajador implements Serializable {

    @EJB
    AdministrarVigenciasTiposTrabajadoresInterface administrarVigenciasTiposTrabajadores;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Tipos Trabajadores
    private List<VigenciasTiposTrabajadores> vigenciasTiposTrabajadores;
    private List<VigenciasTiposTrabajadores> filtrarVTT;
    private VigenciasTiposTrabajadores vigenciaSeleccionada;
    //Tipos Trabajadores
    private List<TiposTrabajadores> listaTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    private List<TiposTrabajadores> filtradoTiposTrabajadores;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vttFecha, vttTipoTrabajador, vttTipoCotizante;
    //Paneles Retirados - Pensionados
    private Panel panelRetiradosMensaje, panelRetiradosInput, panelPensionadosMensaje, panelPensionadosInput;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasTiposTrabajadores> listVTTModificar;
    private boolean guardado;
    //crear VC
    public VigenciasTiposTrabajadores nuevaVigencia;
    private List<VigenciasTiposTrabajadores> listVTTCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasTiposTrabajadores> listVTTBorrar;
    //editar celda
    private VigenciasTiposTrabajadores editarVTT;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasTiposTrabajadores duplicarVTT;
    //elementos retirados
    private Retirados retiroVigencia;
    private List<MotivosRetiros> motivosRetiros;
    private List<MotivosRetiros> filtradoMotivosRetiros;
    private MotivosRetiros motivoRetiroSeleccionado;
    private int indexRetiro;
    private int indexPension;
    //Boolean cambio registro retirados
    private boolean cambioRetiros;
    //Editar o Crear Retiro
    private boolean operacionRetiro;
    //Bandera almacenar extrabajador
    private boolean almacenarRetirado;
    private Retirados retiroCopia;
    private boolean banderaLimpiarRetiro;
    private boolean banderaEliminarRetiro;
    //elementos pensionados
    private List<Pensionados> listaPensionados;
    private Pensionados pensionVigencia;
    private Pensionados pensionadoSeleccionado;
    private List<Personas> listaPersonas;
    private Personas personaSeleccionada;
    private List<ClasesPensiones> clasesPensiones;
    private ClasesPensiones clasesPensionesSeleccionada;
    private List<TiposPensionados> tiposPensionados;
    private TiposPensionados tiposPensionadosSeleccionada;
    private Pensionados pensionCopia;
    //bandera Editar o crear pension
    private boolean operacionPension;
    //bandera encontro o no el registro pension en la base de datos
    private boolean almacenarPensionado;
    //bandera que encuentra si el registro pension se limpio
    private boolean banderaLimpiarPension;
    private boolean banderaEliminarPension;
    private boolean cambioPension;
    //filtrados listas pensionados
    private List<ClasesPensiones> clasesPensionesFiltrado;
    private List<TiposPensionados> tiposPensionadosFiltrado;
    private List<Pensionados> pensionadosFiltrado;
    private List<Personas> personasFiltrado;
    private boolean permitirIndex;
    private String tipoTrabajador;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaVigenciaVTT;
    private Date fechaParametro;
    private String altoTabla;
    //
    private String infoRegistroTipoTrabajador;
    private String infoRegistroMotivoRetiros;
    private String infoRegistroClasePension;
    private String infoRegistroTipoPension;
    private String infoRegistroEmpleado;
    private String infoRegistroPersona;
    public String paginaAnterior;
    //
    private boolean cambiosPagina;
    //
    private String mensajeValidacion;

    /**
     * Constructo del Controlador
     */
    public ControlVigenciaTipoTrabajador() {

        backUpSecRegistro = null;
        listaPensionados = new ArrayList<Pensionados>();
        pensionVigencia = new Pensionados();
        pensionVigencia.setClase(new ClasesPensiones());
        pensionVigencia.setCausabiente(new Empleados());
        pensionVigencia.setTipopensionado(new TiposPensionados());
        pensionVigencia.setTutor(new Personas());
        listaPersonas = new ArrayList<Personas>();
        personaSeleccionada = new Personas();
        clasesPensiones = new ArrayList<ClasesPensiones>();
        clasesPensionesSeleccionada = new ClasesPensiones();
        tiposPensionados = new ArrayList<TiposPensionados>();
        tiposPensionadosSeleccionada = new TiposPensionados();
        pensionadoSeleccionado = new Pensionados();
        operacionPension = false;
        almacenarPensionado = false;
        banderaLimpiarPension = false;
        cambioPension = false;
        banderaEliminarPension = false;

        banderaEliminarRetiro = false;
        banderaLimpiarRetiro = false;
        operacionRetiro = false;
        almacenarRetirado = false;
        retiroVigencia = new Retirados();
        retiroVigencia.setMotivoretiro(new MotivosRetiros());
        motivosRetiros = new ArrayList<MotivosRetiros>();
        motivoRetiroSeleccionado = new MotivosRetiros();

        vigenciasTiposTrabajadores = null;
        listaTiposTrabajadores = new ArrayList<TiposTrabajadores>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVTTBorrar = new ArrayList<VigenciasTiposTrabajadores>();
        //crear aficiones
        listVTTCrear = new ArrayList<VigenciasTiposTrabajadores>();
        k = 0;
        //modificar aficiones
        listVTTModificar = new ArrayList<VigenciasTiposTrabajadores>();
        //editar
        editarVTT = new VigenciasTiposTrabajadores();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasTiposTrabajadores();
        nuevaVigencia.setTipotrabajador(new TiposTrabajadores());
        nuevaVigencia.getTipotrabajador().setTipocotizante(new TiposCotizantes());
        index = -1;
        indexPension = -1;
        indexRetiro = -1;

        cambioRetiros = false;
        retiroCopia = new Retirados();
        pensionCopia = new Pensionados();

        permitirIndex = true;
        secRegistro = null;
        altoTabla = "116";

        cambiosPagina = true;

        mensajeValidacion = " ";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasTiposTrabajadores.obtenerConexion(ses.getId());
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
        vigenciasTiposTrabajadores = null;
        empleado = empl;
    }

    public void modificarVTT(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
            if (!listVTTCrear.contains(vigenciasTiposTrabajadores.get(indice))) {
                if (listVTTModificar.isEmpty()) {
                    listVTTModificar.add(vigenciasTiposTrabajadores.get(indice));
                } else if (!listVTTModificar.contains(vigenciasTiposTrabajadores.get(indice))) {
                    listVTTModificar.add(vigenciasTiposTrabajadores.get(indice));
                }
                if (cambiosPagina) {
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }
            }
            guardado = false;
            index = -1;
            secRegistro = null;
        } else {
            if (!listVTTCrear.contains(filtrarVTT.get(indice))) {

                if (listVTTModificar.isEmpty()) {
                    listVTTModificar.add(filtrarVTT.get(indice));
                } else if (!listVTTModificar.contains(filtrarVTT.get(indice))) {
                    listVTTModificar.add(filtrarVTT.get(indice));
                }
                if (cambiosPagina) {
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }
            }
            guardado = false;
            index = -1;
            secRegistro = null;
        }
        context.update("form:datosVTTEmpleado");
    }

    public boolean validarFechasRegistroVTT(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasTiposTrabajadores auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = vigenciasTiposTrabajadores.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarVTT.get(index);
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
            if (duplicarVTT.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechasVTT(int i, int c) {
        VigenciasTiposTrabajadores auxiliar = null;
        index = i;
        if (tipoLista == 0) {
            auxiliar = vigenciasTiposTrabajadores.get(index);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarVTT.get(index);
        }
        if (auxiliar.getFechavigencia() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistroVTT(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarVTT(i);
            } else {
                if (tipoLista == 0) {
                    vigenciasTiposTrabajadores.get(i).setFechavigencia(fechaVigenciaVTT);
                }
                if (tipoLista == 1) {
                    filtrarVTT.get(i).setFechavigencia(fechaVigenciaVTT);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVTTEmpleado");
                context.execute("errorFechaVTT.show()");
            }
        } else {
            if (tipoLista == 0) {
                vigenciasTiposTrabajadores.get(i).setFechavigencia(fechaVigenciaVTT);
            }
            if (tipoLista == 1) {
                filtrarVTT.get(i).setFechavigencia(fechaVigenciaVTT);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVTTEmpleado");
            context.execute("errorRegNewVTT.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla
     * VigenciaTiposTrabajador de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVTT(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOTRABAJADOR")) {
            if (tipoLista == 0) {
                vigenciasTiposTrabajadores.get(indice).getTipotrabajador().setNombre(tipoTrabajador);
            } else {
                filtrarVTT.get(indice).getTipotrabajador().setNombre(tipoTrabajador);
            }
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasTiposTrabajadores.get(indice).setTipotrabajador(listaTiposTrabajadores.get(indiceUnicoElemento));
                } else {
                    filtrarVTT.get(indice).setTipotrabajador(listaTiposTrabajadores.get(indiceUnicoElemento));
                }
                listaTiposTrabajadores.clear();
                getListaTiposTrabajadores();
            } else {
                permitirIndex = false;
                getInfoRegistroTipoTrabajador();
                context.update("formLovs:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVTTCrear.contains(vigenciasTiposTrabajadores.get(indice))) {

                    if (listVTTModificar.isEmpty()) {
                        listVTTModificar.add(vigenciasTiposTrabajadores.get(indice));
                    } else if (!listVTTModificar.contains(vigenciasTiposTrabajadores.get(indice))) {
                        listVTTModificar.add(vigenciasTiposTrabajadores.get(indice));
                    }
                    if (cambiosPagina) {
                        cambiosPagina = false;
                        context.update("form:ACEPTAR");
                    }
                }
                guardado = false;
                index = -1;
                secRegistro = null;
            } else {
                if (!listVTTCrear.contains(filtrarVTT.get(indice))) {

                    if (listVTTModificar.isEmpty()) {
                        listVTTModificar.add(filtrarVTT.get(indice));
                    } else if (!listVTTModificar.contains(filtrarVTT.get(indice))) {
                        listVTTModificar.add(filtrarVTT.get(indice));
                    }
                    if (cambiosPagina) {
                        cambiosPagina = false;
                        context.update("form:ACEPTAR");
                    }
                }
                guardado = false;
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVTTEmpleado");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("TIPOTRABAJADOR")) {
            if (tipoNuevo == 1) {
                tipoTrabajador = nuevaVigencia.getTipotrabajador().getNombre();
            } else if (tipoNuevo == 2) {
                tipoTrabajador = duplicarVTT.getTipotrabajador().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOTRABAJADOR")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getTipotrabajador().setNombre(tipoTrabajador);
            } else if (tipoNuevo == 2) {
                duplicarVTT.getTipotrabajador().setNombre(tipoTrabajador);
            }
            for (int i = 0; i < listaTiposTrabajadores.size(); i++) {
                if (listaTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setTipotrabajador(listaTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipoTrabajador");
                } else if (tipoNuevo == 2) {
                    duplicarVTT.setTipotrabajador(listaTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoTrabajador");
                }
                listaTiposTrabajadores.clear();
                getListaTiposTrabajadores();
            } else {
                context.update("formLovs:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipoTrabajador");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoTrabajador");
                }
            }
        }
    }

    public void cambiosPension() {
        if (cambioPension == false) {
            cambioPension = true;
        }
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void posicionTabla() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasTiposTrabajador y lanza los cambios en los paneles Retirados y
     * Pensionados en caso de encontrar concordancia del registro con alguno de
     * ellos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex) {
            if (cambioRetiros == false && cambioPension == false) {
                index = indice;
                cualCelda = celda;
                if (tipoLista == 0) {
                    secRegistro = vigenciasTiposTrabajadores.get(index).getSecuencia();
                    fechaVigenciaVTT = vigenciasTiposTrabajadores.get(index).getFechavigencia();
                }
                if (tipoLista == 1) {
                    secRegistro = filtrarVTT.get(index).getSecuencia();
                    fechaVigenciaVTT = filtrarVTT.get(index).getFechavigencia();
                }
                cambioVisibleRetiradosInput();
                cambioVisibleRetiradosMensaje();
                cambioVisiblePensionadoInput();
                cambioVisiblePensionadoMensaje();
            } else {
                if (cambioRetiros == true) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("formularioDialogos:guardarCambiosRetiros");
                    context.execute("guardarCambiosRetiros.show()");
                }
                if (cambioPension == true) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("formularioDialogos:guardarCambiosPensionados");
                    context.execute("guardarCambiosPensionados.show()");
                }
            }
        }
    }
    //GUARDAR

    /**
     * Guarda los cambios efectuador en la VigenciaTiposTrabajador y actualiza
     * la pagina en su totalidad
     */
    public void guardarCambiosVTT() {
        if (guardado == false) {
            if (!listVTTBorrar.isEmpty()) {
                for (int i = 0; i < listVTTBorrar.size(); i++) {
                    administrarVigenciasTiposTrabajadores.borrarVTT(listVTTBorrar.get(i));
                }
                listVTTBorrar.clear();
            }
            if (!listVTTCrear.isEmpty()) {
                for (int i = 0; i < listVTTCrear.size(); i++) {
                    administrarVigenciasTiposTrabajadores.crearVTT(listVTTCrear.get(i));

                }
                listVTTCrear.clear();
            }
            if (!listVTTModificar.isEmpty()) {
                administrarVigenciasTiposTrabajadores.modificarVTT(listVTTModificar);
                listVTTModificar.clear();
            }

            vigenciasTiposTrabajadores = null;
            retiroVigencia = new Retirados();
            retiroVigencia.setMotivoretiro(new MotivosRetiros());
            pensionVigencia = new Pensionados();
            pensionVigencia.setClase(new ClasesPensiones());
            pensionVigencia.setCausabiente(new Empleados());
            pensionVigencia.setTipopensionado(new TiposPensionados());
            pensionVigencia.setTutor(new Personas());
            RequestContext context = RequestContext.getCurrentInstance();
            FacesContext c = FacesContext.getCurrentInstance();
            System.out.println("panelRetiradosInput");
            panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
            panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

            panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
            panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

            panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
            panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");
            System.out.println("panelRetiradosMensaje");
            panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
            panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

            context.update("form:panelRetiradosInput");
            context.update("form:panelRetiradosMensaje");
            context.update("form:panelPensionadosInput");
            context.update("form:panelPensionadosMensaje");
            context.update("form:datosVTTEmpleado");
            guardado = true;
            k = 0;

            cambiosPagina = true;
            context.update("form:ACEPTAR");

            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        }
        index = -1;
        secRegistro = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones efectuadas en la pagina
     */
    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            cerrarFiltrado();
        }

        listVTTBorrar.clear();
        listVTTCrear.clear();
        listVTTModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasTiposTrabajadores = null;
        cambiosPagina = true;
        guardado = true;
        almacenarRetirado = false;
        almacenarPensionado = false;
        banderaEliminarRetiro = false;

        panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
        panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
        panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
        panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
        panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelRetiradosInput");
        context.update("form:panelRetiradosMensaje");
        context.update("form:panelPensionadosInput");
        context.update("form:panelPensionadosMensaje");

        context.update("form:datosVTTEmpleado");
        context.reset("form:motivoRetiro");
        context.reset("form:fechaRetiro");
        context.reset("form:observacion");

        context.reset("form:fechaPensionInicio");
        context.reset("form:fechaPensionFinal");
        context.reset("form:porcentajePension");
        context.reset("form:clasePension");
        context.reset("form:tipoPensionado");
        context.reset("form:resolucionPension");
        context.reset("form:causaBiente");
        context.reset("form:tutorPension");
        context.update("form:ACEPTAR");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (tipoLista == 0) {
                    editarVTT = vigenciasTiposTrabajadores.get(index);
                }
                if (tipoLista == 1) {
                    editarVTT = filtrarVTT.get(index);
                }

                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarFechaDialogo");
                    context.execute("editarFechaDialogo.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarTipoTrabajadorDialogo");
                    context.execute("editarTipoTrabajadorDialogo.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarTipoCotizanterDialogo");
                    context.execute("editarTipoCotizanterDialogo.show()");
                    cualCelda = -1;
                }
            }
            index = -1;
            secRegistro = null;
        }
    }
    //CREAR VU

    /**
     * Metodo que se encarga de agregar una nueva VigenciasTiposTrabajador
     */
    public void agregarNuevaVTT() {
        System.out.println("ControlVigenciaTipoTrabajador");
        System.out.println("Secuencia: " + nuevaVigencia.getSecuencia());
        if (nuevaVigencia.getFechavigencia() != null && nuevaVigencia.getTipotrabajador().getSecuencia() != null) {//Evalua que los datos no sean nulos ni esten vacios
            if (validarFechasRegistroVTT(1) == true) {
                FacesContext c = FacesContext.getCurrentInstance();
                if (bandera == 1) {
                    cerrarFiltrado();
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(l);
                nuevaVigencia.setEmpleado(empleado);
                listVTTCrear.add(nuevaVigencia);
                System.out.println("Secuencia VTT: " + nuevaVigencia.getSecuencia());
                vigenciasTiposTrabajadores.add(nuevaVigencia);
                nuevaVigencia = new VigenciasTiposTrabajadores();
                nuevaVigencia.setTipotrabajador(new TiposTrabajadores());
                nuevaVigencia.getTipotrabajador().setTipocotizante(new TiposCotizantes());

                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:panelRetiradosInput");
                context.update("form:panelRetiradosMensaje");
                context.update("form:panelPensionadosInput");
                context.update("form:panelPensionadosMensaje");

                context.update("form:datosVTTEmpleado");
                context.execute("NuevoRegistroVTT.hide();");
                if (cambiosPagina) {
                    cambiosPagina = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                guardado = false;
                index = -1;
                secRegistro = null;
                banderaEliminarRetiro = false;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechaVTT.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNewVTT.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Limpia las casillas del nuevo registro
     */
    public void limpiarNuevaVTT() {
        nuevaVigencia = new VigenciasTiposTrabajadores();
        nuevaVigencia.setTipotrabajador(new TiposTrabajadores());
        nuevaVigencia.getTipotrabajador().setTipocotizante(new TiposCotizantes());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Duplica una VigenciasTiposTrabajador
     */
    public void duplicarVigenciaTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                duplicarVTT = new VigenciasTiposTrabajadores();
                k++;
                l = BigInteger.valueOf(k);

                if (tipoLista == 0) {
                    duplicarVTT.setEmpleado(vigenciasTiposTrabajadores.get(index).getEmpleado());
                    duplicarVTT.setFechavigencia(vigenciasTiposTrabajadores.get(index).getFechavigencia());
                    duplicarVTT.setTipotrabajador(vigenciasTiposTrabajadores.get(index).getTipotrabajador());
                }
                if (tipoLista == 1) {
                    duplicarVTT.setEmpleado(filtrarVTT.get(index).getEmpleado());
                    duplicarVTT.setFechavigencia(filtrarVTT.get(index).getFechavigencia());
                    duplicarVTT.setTipotrabajador(vigenciasTiposTrabajadores.get(index).getTipotrabajador());
                }
                FacesContext c = FacesContext.getCurrentInstance();
                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                context.update("form:panelRetiradosInput");
                context.update("form:panelRetiradosMensaje");
                context.update("form:panelPensionadosInput");
                context.update("form:panelPensionadosMensaje");

                context.update("formularioDialogos:duplicarVTT");
                context.execute("DuplicarRegistroVTT.show()");
                index = -1;
                secRegistro = null;
                secRegistro = null;
            }
        }
    }

    /**
     * Guarda los datos del duplicado de la vigencia
     */
    public void confirmarDuplicar() {

        int contador = 0;
        mensajeValidacion = " ";
        int pasa = 0;
        int fechas = 0;

        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarVTT.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
        } else {

            for (int j = 0; j < vigenciasTiposTrabajadores.size(); j++) {
                if (duplicarVTT.getFechavigencia().equals(vigenciasTiposTrabajadores.get(j).getFechavigencia())) {
                    fechas++;
                }
            }
            if (fechas > 0) {
                context.update("form:validacionFechas");
                context.execute("validacionFechas.show()");
                pasa++;

            } else {
                if (duplicarVTT.getFechavigencia() != null && duplicarVTT.getTipotrabajador().getSecuencia() != null) {
                    if (validarFechasRegistroVTT(2) == true) {
                        k++;
                        l = BigInteger.valueOf(k);
                        duplicarVTT.setSecuencia(l);
                        vigenciasTiposTrabajadores.add(duplicarVTT);
                        listVTTCrear.add(duplicarVTT);
                        FacesContext c = FacesContext.getCurrentInstance();
                        panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                        panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                        panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                        panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                        panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                        panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                        panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                        panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                        context.update("form:panelRetiradosInput");
                        context.update("form:panelRetiradosMensaje");
                        context.update("form:panelPensionadosInput");
                        context.update("form:panelPensionadosMensaje");
                        context.update("form:datosVTTEmpleado");
                        index = -1;
                        secRegistro = null;
                        if (cambiosPagina) {
                            cambiosPagina = false;
                            context.update("form:ACEPTAR");
                        }
                        guardado = false;
                        if (bandera == 1) {
                            cerrarFiltrado();
                        }
                        duplicarVTT = new VigenciasTiposTrabajadores();
                        context.execute("DuplicarRegistroVTT.hide();");
                    } else {
                        context.execute("errorFechaVTT.show()");
                    }
                } else {
                    context.execute("errorFechaVTT.show()");
                }
            }
        }
    }
    //LIMPIAR DUPLICAR

    /**
     * Limpia las casillas de un duplicar registro
     */
    public void limpiarduplicarVTT() {
        duplicarVTT = new VigenciasTiposTrabajadores();
        duplicarVTT.setTipotrabajador(new TiposTrabajadores());
        duplicarVTT.getTipotrabajador().setTipocotizante(new TiposCotizantes());
    }

    //BORRAR VTT
    /**
     * Borra una VigenciaTiposTrabajador, en caso de que sea pensionado o
     * extrabajador realiza el borrado de una manera independiente. En caso de
     * que alguna casilla este llena muestra un mensaje de advertencia en los
     * dos casos
     */
    public void borrarVTT() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if ((index != indexRetiro) && (index != indexPension)) {
                    if (tipoLista == 0) {
                        if (!listVTTModificar.isEmpty() && listVTTModificar.contains(vigenciasTiposTrabajadores.get(index))) {
                            int modIndex = listVTTModificar.indexOf(vigenciasTiposTrabajadores.get(index));
                            listVTTModificar.remove(modIndex);
                            listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                        } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(vigenciasTiposTrabajadores.get(index))) {
                            int crearIndex = listVTTCrear.indexOf(vigenciasTiposTrabajadores.get(index));
                            listVTTCrear.remove(crearIndex);
                        } else {
                            listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                        }
                        vigenciasTiposTrabajadores.remove(index);
                    }
                    if (tipoLista == 1) {
                        if (!listVTTModificar.isEmpty() && listVTTModificar.contains(filtrarVTT.get(index))) {
                            int modIndex = listVTTModificar.indexOf(filtrarVTT.get(index));
                            listVTTModificar.remove(modIndex);
                            listVTTBorrar.add(filtrarVTT.get(index));
                        } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(filtrarVTT.get(index))) {
                            int crearIndex = listVTTCrear.indexOf(filtrarVTT.get(index));
                            listVTTCrear.remove(crearIndex);
                        } else {
                            listVTTBorrar.add(filtrarVTT.get(index));
                        }
                        int VCIndex = vigenciasTiposTrabajadores.indexOf(filtrarVTT.get(index));
                        vigenciasTiposTrabajadores.remove(VCIndex);
                        filtrarVTT.remove(index);
                    }

                    context.update("form:datosVTTEmpleado");

                    if (cambiosPagina) {
                        cambiosPagina = false;
                        context.update("form:ACEPTAR");
                    }
                    guardado = false;
                } else {
                    if (index == indexRetiro) {
                        banderaEliminarRetiro = true;
                        if ((retiroVigencia.getFecharetiro() == null) && (retiroVigencia.getMotivoretiro().getSecuencia() == null)
                                && (retiroVigencia.getDescripcion() == null)) {
                            if (retiroVigencia.getMotivoretiro().getSecuencia() == null) {
                                retiroVigencia.setMotivoretiro(null);
                            }
                            administrarVigenciasTiposTrabajadores.borrarRetirado(retiroVigencia);
                            if (tipoLista == 0) {
                                if (!listVTTModificar.isEmpty() && listVTTModificar.contains(vigenciasTiposTrabajadores.get(index))) {
                                    int modIndex = listVTTModificar.indexOf(vigenciasTiposTrabajadores.get(index));
                                    listVTTModificar.remove(modIndex);
                                    listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                                } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(vigenciasTiposTrabajadores.get(index))) {
                                    int crearIndex = listVTTCrear.indexOf(vigenciasTiposTrabajadores.get(index));
                                    listVTTCrear.remove(crearIndex);
                                } else {
                                    listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                                }
                                vigenciasTiposTrabajadores.remove(index);
                            }
                            if (tipoLista == 1) {
                                if (!listVTTModificar.isEmpty() && listVTTModificar.contains(filtrarVTT.get(index))) {
                                    int modIndex = listVTTModificar.indexOf(filtrarVTT.get(index));
                                    listVTTModificar.remove(modIndex);
                                    listVTTBorrar.add(filtrarVTT.get(index));
                                } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(filtrarVTT.get(index))) {
                                    int crearIndex = listVTTCrear.indexOf(filtrarVTT.get(index));
                                    listVTTCrear.remove(crearIndex);
                                } else {
                                    listVTTBorrar.add(filtrarVTT.get(index));
                                }
                                int VCIndex = vigenciasTiposTrabajadores.indexOf(filtrarVTT.get(index));
                                vigenciasTiposTrabajadores.remove(VCIndex);
                                filtrarVTT.remove(index);
                            }

                            context.update("form:datosVTTEmpleado");

                            if (cambiosPagina) {
                                cambiosPagina = false;
                                context.update("form:ACEPTAR");
                            }
                            guardado = false;
                        } else {
                            context.execute("informacionEliminarExtrabajador.show()");
                        }
                        banderaLimpiarRetiro = false;
                    }
                    if (index == indexPension) {
                        banderaEliminarPension = true;
                        if ((pensionVigencia.getFechainiciopension() == null) && (pensionVigencia.getFechafinalpension() == null)
                                && (pensionVigencia.getPorcentaje() == null) && (pensionVigencia.getClase().getSecuencia() == null)
                                && (pensionVigencia.getTipopensionado().getSecuencia() == null) && (pensionVigencia.getResolucionpension() == null)
                                && (pensionVigencia.getTutor().getSecuencia() == null) && (pensionVigencia.getCausabiente().getSecuencia() == null)) {
                            if (pensionVigencia.getCausabiente().getSecuencia() == null) {
                                pensionVigencia.setCausabiente(null);
                            }
                            if (pensionVigencia.getClase().getSecuencia() == null) {
                                pensionVigencia.setClase(null);
                            }
                            if (pensionVigencia.getTipopensionado().getSecuencia() == null) {
                                pensionVigencia.setTipopensionado(null);
                            }
                            if (pensionVigencia.getTutor().getSecuencia() == null) {
                                pensionVigencia.setTutor(null);
                            }
                            administrarVigenciasTiposTrabajadores.borrarPensionado(pensionVigencia);
                            if (tipoLista == 0) {
                                if (!listVTTModificar.isEmpty() && listVTTModificar.contains(vigenciasTiposTrabajadores.get(index))) {
                                    int modIndex = listVTTModificar.indexOf(vigenciasTiposTrabajadores.get(index));
                                    listVTTModificar.remove(modIndex);
                                    listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                                } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(vigenciasTiposTrabajadores.get(index))) {
                                    int crearIndex = listVTTCrear.indexOf(vigenciasTiposTrabajadores.get(index));
                                    listVTTCrear.remove(crearIndex);
                                } else {

                                    listVTTBorrar.add(vigenciasTiposTrabajadores.get(index));
                                }
                                vigenciasTiposTrabajadores.remove(index);
                            }
                            if (tipoLista == 1) {
                                if (!listVTTModificar.isEmpty() && listVTTModificar.contains(filtrarVTT.get(index))) {
                                    int modIndex = listVTTModificar.indexOf(filtrarVTT.get(index));
                                    listVTTModificar.remove(modIndex);
                                    listVTTBorrar.add(filtrarVTT.get(index));
                                } else if (!listVTTCrear.isEmpty() && listVTTCrear.contains(filtrarVTT.get(index))) {
                                    int crearIndex = listVTTCrear.indexOf(filtrarVTT.get(index));
                                    listVTTCrear.remove(crearIndex);
                                } else {
                                    listVTTBorrar.add(filtrarVTT.get(index));
                                }
                                int VCIndex = vigenciasTiposTrabajadores.indexOf(filtrarVTT.get(index));
                                vigenciasTiposTrabajadores.remove(VCIndex);
                                filtrarVTT.remove(index);
                            }

                            context.update("form:datosVTTEmpleado");

                            if (cambiosPagina) {
                                cambiosPagina = false;
                                context.update("form:ACEPTAR");
                            }
                            guardado = false;
                        } else {
                            //Dialogo de aviso de limpiar registro adicional pensionados
                            context.execute("informacionEliminarPensionado.show()");
                        }
                        banderaLimpiarPension = false;
                    }
                }
                FacesContext c = FacesContext.getCurrentInstance();
                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                context.update("form:panelRetiradosInput");
                context.update("form:panelRetiradosMensaje");
                context.update("form:panelPensionadosInput");
                context.update("form:panelPensionadosMensaje");
                context.update("form:datosVTTEmpleado");
            }
            index = -1;
            secRegistro = null;
            indexPension = -1;
            indexRetiro = -1;
            banderaLimpiarRetiro = false;
            banderaLimpiarPension = false;
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado de la tabla VigenciasTiposTrabajadores por
     * medio del boton filtrado o la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            vttFecha = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttFecha");
            vttFecha.setFilterStyle("width: 60px");
            vttTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttTipoTrabajador");
            vttTipoTrabajador.setFilterStyle("width: 100px");
            vttTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttTipoCotizante");
            vttTipoCotizante.setFilterStyle("width: 100px");
            altoTabla = "92";
            RequestContext.getCurrentInstance().update("form:datosVTTEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        cerrarFiltrado();
        listVTTBorrar.clear();
        listVTTCrear.clear();
        listVTTModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasTiposTrabajadores = null;
        cambiosPagina = true;
        guardado = false;
        cambioPension = false;
        cambioRetiros = false;
        //tiposPensionados = null;        
        clasesPensiones = null;
        listaPersonas = null;
        listaPensionados = null;
        motivosRetiros = null;
        listaTiposTrabajadores = null;
        motivosRetiros = null;
        //context.update("form:ACEPTAR");
    }

    public void cerrarFiltrado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vttFecha = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttFecha");
            vttFecha.setFilterStyle("display: none; visibility: hidden;");
            vttTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttTipoTrabajador");
            vttTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
            vttTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosVTTEmpleado:vttTipoCotizante");
            vttTipoCotizante.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "116";
            context.update("form:datosVTTEmpleado");
            bandera = 0;
            filtrarVTT = null;
            tipoLista = 0;
        }
    }

    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
    /**
     * Metodo que muestra los dialogos de las listas dentro de la pagina
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     * @param tt Tipo Trabajador : Pensionado / Retirado /
     * VigenciaTipoTrabajador
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
                //TiposTrabajadoresDialogo
                getInfoRegistroTipoTrabajador();
                context.update("formLovs:TipoTrabajadorDialogo");
                context.execute("formLovs:TipoTrabajadorDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                indexPension = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                //ClasesPensionDialogo
                getInfoRegistroClasePension();
                context.update("formLovs:clasePensionDialogo");
                context.execute("clasePensionDialogo.show()");
            } else if (dlg == 1) {
                //tipoPensionadoDialogo
                getInfoRegistroTipoPension();
                context.update("formLovs:tipoPensionadoDialogo");
                context.execute("tipoPensionadoDialogo.show()");
            } else if (dlg == 2) {
                //causaBientesDialogo
                getInfoRegistroEmpleado();
                context.update("formLovs:causaBientesDialogo");
                context.execute("causaBientesDialogo.show()");
            } else if (dlg == 3) {
                //tipoPensionadoDialogo
                getInfoRegistroPersona();
                context.update("formLovs:tutorDialogo");
                context.execute("tutorDialogo.show()");
            }
        }
        if (tt == 2) {
            if (LND == 0) {
                indexRetiro = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                //RetirosDialogo
                getInfoRegistroMotivoRetiros();
                context.update("formLovs:RetirosDialogo");
                context.execute("RetirosDialogo.show()");
            }
        }
    }

    //LOVS
    //CIUDAD
    /**
     * Actualiza la informacion de l tipo de trabajador con respecto a la tabla
     * - nuevo registro o duplicar registro
     */
    public void actualizarTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasTiposTrabajadores.get(index).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listVTTCrear.contains(vigenciasTiposTrabajadores.get(index))) {
                    if (listVTTModificar.isEmpty()) {
                        listVTTModificar.add(vigenciasTiposTrabajadores.get(index));
                    } else if (!listVTTModificar.contains(vigenciasTiposTrabajadores.get(index))) {
                        listVTTModificar.add(vigenciasTiposTrabajadores.get(index));
                    }
                }
            } else {
                filtrarVTT.get(index).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listVTTCrear.contains(filtrarVTT.get(index))) {
                    if (listVTTModificar.isEmpty()) {
                        listVTTModificar.add(filtrarVTT.get(index));
                    } else if (!listVTTModificar.contains(filtrarVTT.get(index))) {
                        listVTTModificar.add(filtrarVTT.get(index));
                    }
                }
            }
            if (cambiosPagina) {
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            }
            guardado = false;
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setTipotrabajador(tipoTrabajadorSeleccionado);
            context.update("formularioDialogos:nuevaVTT");
        } else if (tipoActualizacion == 2) {
            duplicarVTT.setTipotrabajador(tipoTrabajadorSeleccionado);
            context.update("formularioDialogos:duplicarVTT");
        }
        filtradoTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        /*
         * context.update("form:TipoTrabajadorDialogo");
         * context.update("form:lovTipoTrabajador");
         * context.update("form:aceptarTT");
         */
        context.reset("formLovs:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    /**
     * Cancela los cambios en el dialogo TipoTrabajador
     */
    public void cancelarCambioTipoTrabajador() {
        filtradoTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        //Si no hay registro selecciionado
        //if (index < 0 && indexPension < 0 && indexRetiro < 0) {
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (cualCelda == 1) {
                    //TiposTrabajadoresDialogo
                    getInfoRegistroTipoTrabajador();
                    context.update("formLovs:TipoTrabajadorDialogo");
                    context.execute("TipoTrabajadorDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
        }
    }

        /**
         * Activa el boton aceptar en la pantalla inicial y en los dialogos
         */
    

    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVTTEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasTiposTrabajadoresPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVTTEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasTiposTrabajadoresXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //VISIBILIDAD PANELES RETIRADOS - PENSIONADOS
    /**
     * Cambia la visibilidad del panelRetiradosMensaje con respecto a que el
     * registro sea Extrabajador
     */
    public void cambioVisibleRetiradosMensaje() {
        TiposTrabajadores tipoTrabajadorRetirado;
        if (index >= 0) {
            short n1 = 1;
            tipoTrabajadorRetirado = administrarVigenciasTiposTrabajadores.tipoTrabajadorCodigo(n1);
            VigenciasTiposTrabajadores vigenciaTemporal = vigenciasTiposTrabajadores.get(index);
            panelRetiradosMensaje = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:panelRetiradosMensaje");
            if (tipoTrabajadorRetirado.getCodigo() == vigenciaTemporal.getTipotrabajador().getCodigo()) {
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; display: none; visibility: hidden;");
            } else {
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left;visibility: visible;");
            }
            RequestContext.getCurrentInstance().update("form:panelRetiradosMensaje");
        }
    }

    /**
     * Cambia la visibilidad del panelRetiradosInput con respecto a que el
     * registro sea Extrabajador, en caso verdadero carga los datos del retirado
     */
    public void cambioVisibleRetiradosInput() {
        TiposTrabajadores tipoTrabajadorRetirado;
        if (index >= 0) {
            short n1 = 1;
            tipoTrabajadorRetirado = administrarVigenciasTiposTrabajadores.tipoTrabajadorCodigo(n1);
            VigenciasTiposTrabajadores vigenciaTemporal = vigenciasTiposTrabajadores.get(index);
            panelRetiradosInput = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:panelRetiradosInput");
            if (tipoTrabajadorRetirado.getCodigo() == vigenciaTemporal.getTipotrabajador().getCodigo()) {
                indexRetiro = index;
                cargarRetiro();
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible;");
            } else {
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");
            }
            RequestContext.getCurrentInstance().update("form:panelRetiradosInput");
            RequestContext context = RequestContext.getCurrentInstance();
            context.reset("form:motivoRetiro");
            context.reset("form:fechaRetiro");
            context.reset("form:observacion");
        }
    }

    /**
     * Cambia la visibilidad del panelPensionadosMensaje con respecto a que el
     * registro sea Extrabajador
     */
    public void cambioVisiblePensionadoMensaje() {
        TiposTrabajadores tipoTrabajadorPensionado;
        if (index >= 0) {
            short n2 = 2;
            tipoTrabajadorPensionado = administrarVigenciasTiposTrabajadores.tipoTrabajadorCodigo(n2);
            VigenciasTiposTrabajadores vigenciaTemporal = vigenciasTiposTrabajadores.get(index);
            panelPensionadosMensaje = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:panelPensionadosMensaje");
            if (tipoTrabajadorPensionado.getCodigo() == vigenciaTemporal.getTipotrabajador().getCodigo()) {
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; display: none; visibility: hidden;");
            } else {
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible;");
            }
            RequestContext.getCurrentInstance().update("form:panelPensionadosMensaje");
        }
    }

    /**
     * Cambia la visibilidad del panelPensionadosMensaje con respecto a que el
     * registro sea Extrabajador, en caso verdadero carga los datos del
     * pensionado
     */
    public void cambioVisiblePensionadoInput() {
        TiposTrabajadores tipoTrabajadorPensionado;
        if (index >= 0) {
            short n2 = 2;
            tipoTrabajadorPensionado = administrarVigenciasTiposTrabajadores.tipoTrabajadorCodigo(n2);
            VigenciasTiposTrabajadores vigenciaTemporal = vigenciasTiposTrabajadores.get(index);
            panelPensionadosInput = (Panel) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:panelPensionadosInput");
            if (tipoTrabajadorPensionado.getCodigo() == vigenciaTemporal.getTipotrabajador().getCodigo()) {
                indexPension = index;
                cargarPension();
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left;  visibility: visible;");
            } else {
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");
            }
            RequestContext.getCurrentInstance().update("form:panelPensionadosInput");
        }
    }

    //CARGAR RETIRO CON INDEX DEFINIDO
    /**
     * Carga los datos del retirado con respecto a la secuencia de la vigencia
     * seleccionada
     */
    public void cargarRetiro() {
        k++;
        l = BigInteger.valueOf(k);
        retiroVigencia = administrarVigenciasTiposTrabajadores.retiroPorSecuenciaVigencia(vigenciasTiposTrabajadores.get(indexRetiro).getSecuencia());
        if (retiroVigencia.getSecuencia() == null) {
            operacionRetiro = true;
            retiroVigencia = new Retirados();
            retiroVigencia.setSecuencia(l);
            retiroVigencia.setMotivoretiro(new MotivosRetiros());
        }

    }

    //CARGAR PENSION CON INDEX DEFINIDO
    /**
     * Carga los datos del pensionado con respecto a la secuencia de la vigencia
     * seleccionada
     */
    public void cargarPension() {
        k++;
        l = BigInteger.valueOf(k);
        pensionVigencia = administrarVigenciasTiposTrabajadores.pensionPorSecuenciaVigencia(vigenciasTiposTrabajadores.get(indexPension).getSecuencia());
        if (pensionVigencia.getSecuencia() == null) {
            operacionPension = true;
            pensionVigencia = new Pensionados();
            pensionVigencia.setClase(new ClasesPensiones());
            pensionVigencia.setCausabiente(new Empleados());
            pensionVigencia.setTipopensionado(new TiposPensionados());
            pensionVigencia.setTutor(new Personas());
            pensionVigencia.setSecuencia(l);

        }
    }

    //GUARDADO RETIROS
    /**
     * Guarda los datos efectuados en el panel retirados
     */
    public void guardarDatosRetiros() {
        if (retiroVigencia.getFecharetiro() != null) {
            if (tipoLista == 0) {
                retiroVigencia.setVigenciatipotrabajador(vigenciasTiposTrabajadores.get(indexRetiro));
            }
            if (tipoLista == 1) {
                retiroVigencia.setVigenciatipotrabajador(filtrarVTT.get(indexRetiro));
            }
            k++;
            k = retiroVigencia.getSecuencia().intValue();
            l = BigInteger.valueOf(k);
            retiroVigencia.setSecuencia(l);
            if (operacionRetiro == false) {
                if (banderaLimpiarRetiro == true) {
                    administrarVigenciasTiposTrabajadores.borrarRetirado(retiroCopia);
                } else {
                    administrarVigenciasTiposTrabajadores.editarRetirado(retiroVigencia);
                }
            } else {
                if (banderaLimpiarRetiro == false) {
                    if (retiroVigencia.getMotivoretiro().getSecuencia() == null) {
                        retiroVigencia.setMotivoretiro(null);
                    }
                    administrarVigenciasTiposTrabajadores.crearRetirado(retiroVigencia);
                }
            }
            FacesContext c = FacesContext.getCurrentInstance();
            if (banderaLimpiarRetiro == true) {

                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 10px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 10px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible;");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 10px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 10px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");
            } else {
                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

            }
            cambioRetiros = false;
            index = -1;
            secRegistro = null;

            retiroVigencia = new Retirados();
            retiroVigencia.setMotivoretiro(new MotivosRetiros());
            banderaLimpiarRetiro = false;

            operacionRetiro = false;

            //  cargarRetiro();
            RequestContext context = RequestContext.getCurrentInstance();

            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos de Retirados con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");

            context.update("form:panelRetiradosInput");
            context.update("form:panelRetiradosMensaje");
            context.update("form:panelPensionadosInput");
            context.update("form:panelPensionadosMensaje");

            cambiosPagina = true;
            context.update("form:ACEPTAR");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegRetiro.show()");

        }
    }

    /**
     * Cancela los cambios efectuados en el panel de retirados
     */
    public void cancelarDatosRetiros() {
        cambioRetiros = false;
        index = -1;
        secRegistro = null;
        indexRetiro = -1;
        // limpiarRetiro();
        retiroVigencia = new Retirados();
        retiroVigencia.setMotivoretiro(new MotivosRetiros());
        FacesContext c = FacesContext.getCurrentInstance();
        panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
        panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
        panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
        panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
        panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelRetiradosInput");
        context.update("form:panelRetiradosMensaje");
        context.update("form:panelPensionadosInput");
        context.update("form:panelPensionadosMensaje");
    }

    /**
     * Efectua el guardado de los datos cambiados en el panel pensiones
     */
    public void guardarDatosPensiones() {
        System.out.println("ENTRO guardarDatosPensiones");
        if (pensionVigencia.getFechainiciopension() != null && pensionVigencia.getClase().getSecuencia() != null) {
            System.out.println("ControlVigenciaTipoTrabajador.guardarDatosPensiones:");
            System.out.println("Secuencia: " + pensionVigencia.getSecuencia());
            if (tipoLista == 0) {
                pensionVigencia.setVigenciatipotrabajador(vigenciasTiposTrabajadores.get(indexPension));

            }
            if (tipoLista == 1) {
                pensionVigencia.setVigenciatipotrabajador(filtrarVTT.get(indexPension));
            }
            k++;
            k = pensionVigencia.getSecuencia().intValue();
            System.out.println("K: " + k);
            l = BigInteger.valueOf(k);
            System.out.println("L: " + l);
            pensionVigencia.setSecuencia(l);
            System.out.println("pensionVigencia.Secuencia: " + pensionVigencia.getSecuencia());
            if (operacionPension == false) {
                if (banderaLimpiarPension == true) {
                    administrarVigenciasTiposTrabajadores.borrarPensionado(pensionCopia);
                } else {
                    System.out.println("editar");
                    System.out.println("Secuencia: " + pensionVigencia.getSecuencia());
                    administrarVigenciasTiposTrabajadores.editarPensionado(pensionVigencia);
                }
            } else {
                if (banderaLimpiarPension == false) {
                    if (pensionVigencia.getCausabiente().getSecuencia() == null) {
                        pensionVigencia.setCausabiente(null);
                    }
                    if (pensionVigencia.getTipopensionado().getSecuencia() == null) {
                        pensionVigencia.setTipopensionado(null);
                    }
                    if (pensionVigencia.getTutor().getSecuencia() == null) {
                        pensionVigencia.setTutor(null);
                    }
                    if (pensionVigencia.getClase().getSecuencia() == null) {
                        pensionVigencia.setClase(null);
                    }
                    administrarVigenciasTiposTrabajadores.crearPensionado(pensionVigencia);
                }
            }
            FacesContext c = FacesContext.getCurrentInstance();
            if (banderaLimpiarPension == true) {

                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 10px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 10px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible;");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 10px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 10px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");
            } else {
                panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
                panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
                panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

                panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
                panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

                panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
                panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");
            }
            cambioPension = false;
            index = -1;
            secRegistro = null;

            pensionVigencia = new Pensionados();
            pensionVigencia.setClase(new ClasesPensiones());
            pensionVigencia.setCausabiente(new Empleados());
            pensionVigencia.setTipopensionado(new TiposPensionados());
            pensionVigencia.setTutor(new Personas());
            banderaLimpiarPension = false;

            operacionPension = false;

            RequestContext context = RequestContext.getCurrentInstance();

            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos de Pensionados con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");

            context.update("form:panelRetiradosInput");
            context.update("form:panelRetiradosMensaje");
            context.update("form:panelPensionadosInput");
            context.update("form:panelPensionadosMensaje");

            cambiosPagina = true;
            context.update("form:ACEPTAR");
            System.out.println("SALIO");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegPensionado.show()");
            System.out.println("SALIO CON ERROR");
        }
    }

    /**
     * Cancela los datos efectuados en el panel pension
     */
    public void cancelarDatosPension() {
        cambioPension = false;
        index = -1;
        secRegistro = null;
        indexPension = -1;
        //LimpiarPension();
        pensionVigencia = new Pensionados();
        pensionVigencia.setClase(new ClasesPensiones());
        pensionVigencia.setCausabiente(new Empleados());
        pensionVigencia.setTipopensionado(new TiposPensionados());
        pensionVigencia.setTutor(new Personas());
        FacesContext c = FacesContext.getCurrentInstance();
        panelRetiradosInput = (Panel) c.getViewRoot().findComponent("form:panelRetiradosInput");
        panelRetiradosInput.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelPensionadosMensaje = (Panel) c.getViewRoot().findComponent("form:panelPensionadosMensaje");
        panelPensionadosMensaje.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        panelPensionadosInput = (Panel) c.getViewRoot().findComponent("form:panelPensionadosInput");
        panelPensionadosInput.setStyle("position: absolute; left: 12px; top: 280px; font-size: 12px; width: 410px; height: 195px; border-radius: 10px; text-align: left; visibility: hidden; display: none;");

        panelRetiradosMensaje = (Panel) c.getViewRoot().findComponent("form:panelRetiradosMensaje");
        panelRetiradosMensaje.setStyle("position: absolute; left: 440px; top: 280px; font-size: 12px; width: 415px; height: 195px; border-radius: 10px; text-align: left; visibility: visible");

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelRetiradosInput");
        context.update("form:panelRetiradosMensaje");
        context.update("form:panelPensionadosInput");
        context.update("form:panelPensionadosMensaje");
    }

    /**
     * Dispara el dialogo de MotivosRetiros
     */
    public void dialogoRetiros() {
        RequestContext context = RequestContext.getCurrentInstance();
        getInfoRegistroMotivoRetiros();
        context.reset("formLovs:motivoRetiro");
        context.execute("RetirosDialogo.show()");

    }

    /**
     * Actualiza la selecciion del motivo retiro
     */
    public void actualizarMotivoRetiro() {
        banderaCambiosRetirados();
        retiroVigencia.setMotivoretiro(motivoRetiroSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelRetiradosInput");
        aceptar = true;
        index = -1;
        secRegistro = null;
        motivoRetiroSeleccionado = null;
        filtradoMotivosRetiros = null;
        getMotivosRetiros();
        /*
         * context.update("form:RetirosDialogo");
         * context.update("form:lovMotivosRetiros");
         * context.update("form:aceptarMR");
         */
        context.reset("formLovs:lovMotivosRetiros:globalFilter");
        context.execute("lovMotivosRetiros.clearFilters()");
        context.execute("RetirosDialogo.hide()");
    }

    /**
     * Cancela la seleccion del motivo retiro
     */
    public void cancelarMotivoRetiro() {
        motivoRetiroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovMotivosRetiros:globalFilter");
        context.execute("lovMotivosRetiros.clearFilters()");
        context.execute("RetirosDialogo.hide()");
    }

    //////////////////////////////////////
    //Pendiente
    /**
     * Dispara el dialogo de ClasePension
     */
    public void dialogoClasePension() {
        RequestContext context = RequestContext.getCurrentInstance();
        getInfoRegistroClasePension();
        context.reset("formLovs:clasePension");
        context.execute("clasePensionDialogo.show()");
    }

    /**
     * Actualiza la selecciion del Clase Pension
     */
    public void actualizarClasePension() {
        banderaCambiosPensionados();
        pensionVigencia.setClase(clasesPensionesSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelPensionadosInput");
        aceptar = true;
        index = -1;
        secRegistro = null;
        clasesPensionesFiltrado = null;
        clasesPensionesSeleccionada = null;
        getClasesPensiones();
        /*
         * context.update("form:clasePensionDialogo");
         * context.update("form:lovClasePension");
         * context.update("form:aceptarCP");
         */
        context.reset("formLovs:lovClasePension:globalFilter");
        context.execute("lovClasePension.clearFilters()");
        context.execute("clasePensionDialogo.hide()");
    }

    /**
     * Cancela la seleccion del Clase Pension
     */
    public void cancelarClasePension() {
        clasesPensionesSeleccionada = null;
        clasesPensionesFiltrado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovClasePension:globalFilter");
        context.execute("lovClasePension.clearFilters()");
        context.execute("clasePensionDialogo.hide()");
    }
    //////////////////////////////////////

    //////////////////////////////////////
    //Pendiente
    /**
     * Dispara el dialogo de TipoPensionado
     */
    public void dialogoTipoPensionado() {
        RequestContext context = RequestContext.getCurrentInstance();
        getInfoRegistroTipoPension();
        context.reset("formLovs:tipoPensionado");
        context.execute("tipoPensionadoDialogo.show()");
    }

    /**
     * Actualiza la selecciion del TipoPensionado
     */
    public void actualizarTipoPensionado() {
        banderaCambiosPensionados();
        pensionVigencia.setTipopensionado(tiposPensionadosSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelPensionadosInput");
        aceptar = true;
        index = -1;
        secRegistro = null;
        tiposPensionadosSeleccionada = null;
        tiposPensionadosFiltrado = null;
        getTiposPensionados();
        /*
         * context.update("form:tipoPensionadoDialogo");
         * context.update("form:lovTipoPensionado");
         * context.update("form:aceptarTP");
         */
        context.reset("formLovs:lovTipoPensionado:globalFilter");
        context.execute("lovTipoPensionado.clearFilters()");
        context.execute("tipoPensionadoDialogo.hide()");
    }

    /**
     * Cancela la seleccion del TipoPensionado
     */
    public void cancelarTipoPensionado() {
        tiposPensionadosSeleccionada = null;
        tiposPensionadosFiltrado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovTipoPensionado:globalFilter");
        context.execute("lovTipoPensionado.clearFilters()");
        context.execute("tipoPensionadoDialogo.hide()");
    }
    //////////////////////////////////////
    //////////////////////////////////////
    //Pendiente

    /**
     * Dispara el dialogo de CausaBiente
     */
    public void dialogoCausaBiente() {
        RequestContext context = RequestContext.getCurrentInstance();
        getInfoRegistroEmpleado();
        context.reset("formLovs:causaBiente");
        context.execute("causaBientesDialogo.show()");
    }

    /**
     * Actualiza la selecciion del CausaBiente
     */
    public void actualizarCausaBiente() {
        banderaCambiosPensionados();
        pensionVigencia.setCausabiente(pensionadoSeleccionado.getCausabiente());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelPensionadosInput");
        aceptar = true;
        index = -1;
        secRegistro = null;
        pensionadoSeleccionado = null;
        pensionadosFiltrado = null;
        getListaPensionados();
        /*
         * context.update("form:causaBientesDialogo");
         * context.update("form:lovCausaBientes");
         * context.update("form:aceptarCB");
         */
        context.reset("formLovs:lovCausaBientes:globalFilter");
        context.execute("lovCausaBientes.clearFilters()");
        context.execute("causaBientesDialogo.hide()");
    }

    /**
     * Cancela la seleccion del CausaBiente
     */
    public void cancelarCausaBiente() {
        pensionadoSeleccionado = null;
        pensionadosFiltrado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovCausaBientes:globalFilter");
        context.execute("lovCausaBientes.clearFilters()");
        context.execute("causaBientesDialogo.hide()");
    }
    //////////////////////////////////////
    //////////////////////////////////////
    //Pendiente

    /**
     * Dispara el dialogo de Tutor
     */
    public void dialogoTutor() {
        RequestContext context = RequestContext.getCurrentInstance();
        getInfoRegistroPersona();
        context.reset("formLovs:tutorPension");
        context.execute("tutorDialogo.show()");
    }

    /**
     * Actualiza la selecciion del Tutor
     */
    public void actualizarTutor() {
        banderaCambiosPensionados();
        pensionVigencia.setTutor(personaSeleccionada);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelPensionadosInput");
        aceptar = true;
        index = -1;
        secRegistro = null;
        personasFiltrado = null;
        personaSeleccionada = null;
        getListaPersonas();
        /*
         * context.update("form:tutorDialogo"); context.update("form:lovTutor");
         * context.update("form:aceptarT");
         */
        context.reset("formLovs:lovTutor:globalFilter");
        context.execute("lovTutor.clearFilters()");
        context.execute("tutorDialogo.hide()");
    }

    /**
     * Cancela la seleccion del Tutor
     */
    public void cancelarTutor() {
        personaSeleccionada = null;
        personasFiltrado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formLovs:lovTutor:globalFilter");
        context.execute("lovTutor.clearFilters()");
        context.execute("tutorDialogo.hide()");
    }
    //////////////////////////////////////

    /**
     * Limpia la informacion del panel retirado
     */
    public void limpiarRetiro() {
        banderaLimpiarRetiro = true;
        //se efecto un cambio en --> se adiciono retiroVigencia.getMotivoretiro() !=null, retiroVigencia.getDescripcion() !=null
        // en caso de fallas eliminar nuevo cambio
        if (retiroVigencia.getFecharetiro() != null || retiroVigencia.getMotivoretiro() != null || retiroVigencia.getDescripcion() != null) {
            banderaCambiosRetirados();
        }
        retiroCopia = retiroVigencia;
        retiroVigencia = new Retirados();
        retiroVigencia.setMotivoretiro(new MotivosRetiros());
        retiroVigencia.setSecuencia(retiroCopia.getSecuencia());
        index = -1;
        secRegistro = null;
        retiroVigencia.setVigenciatipotrabajador(vigenciasTiposTrabajadores.get(indexRetiro));
        if (operacionRetiro == false) {
            if (banderaLimpiarRetiro == true) {
                administrarVigenciasTiposTrabajadores.borrarRetirado(retiroCopia);
            } else {
                administrarVigenciasTiposTrabajadores.editarRetirado(retiroVigencia);
            }
        }
        cargarRetiro();
        banderaLimpiarRetiro = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:observacion");
        context.update("form:motivoRetiro");
        context.update("form:fechaRetiro");
    }

    /**
     * Limpia la informacion del panel pensionado
     */
    public void limpiarPension() {
        banderaLimpiarPension = true;
        if (pensionVigencia.getFechainiciopension() != null || pensionVigencia.getFechafinalpension() != null
                || pensionVigencia.getPorcentaje() != null || pensionVigencia.getClase() != null
                || pensionVigencia.getTipopensionado() != null || pensionVigencia.getResolucionpension() != null
                || pensionVigencia.getTutor() != null || pensionVigencia.getCausabiente() != null) {
            banderaCambiosPensionados();
        }
        pensionCopia = pensionVigencia;
        pensionVigencia = new Pensionados();
        pensionVigencia.setCausabiente(new Empleados());
        pensionVigencia.setClase(new ClasesPensiones());
        pensionVigencia.setTipopensionado(new TiposPensionados());
        pensionVigencia.setTutor(new Personas());
        pensionVigencia.setSecuencia(pensionCopia.getSecuencia());
        index = -1;
        secRegistro = null;
        pensionVigencia.setVigenciatipotrabajador(vigenciasTiposTrabajadores.get(indexPension));
        if (operacionPension == false) {
            if (banderaLimpiarPension == true) {
                administrarVigenciasTiposTrabajadores.borrarPensionado(pensionCopia);
            } else {
                administrarVigenciasTiposTrabajadores.editarPensionado(pensionVigencia);
            }
        } else {
            if (banderaLimpiarPension == false) {
                administrarVigenciasTiposTrabajadores.crearPensionado(pensionVigencia);
            }
        }
        cargarPension();
        banderaLimpiarPension = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelPensionadosInput");
    }

    /**
     * Metodo que activa una bandera que se activa si se realiza un cambio en el
     * panel retiros
     */
    public void banderaCambiosRetirados() {
        if (cambioRetiros == false) {
            cambioRetiros = true;
            banderaEliminarRetiro = false;
        }
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");

    }

    /**
     * Metodo que activa una bandera que se activa si se realiza un cambio en el
     * panel pensionado
     */
    public void banderaCambiosPensionados() {
        if (cambioPension == false) {
            System.out.println("ControlVigenciaTipoTrabajador.banderaCambiosPensionados()");
            System.out.println("Secuencia: " + pensionVigencia.getSecuencia());
            cambioPension = true;
            banderaEliminarPension = false;
        }
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");

    }

    /**
     * Metodo que valida que el registro extrabajador se encuentra almacenado en
     * la base de datos, en caso contrario muestra un dialogo para que lo
     * almacene o no
     */
    public void validarRegistroSeleccionadoRetirados() {
        RequestContext context = RequestContext.getCurrentInstance();

        context.reset("form:motivoRetiro");
        context.reset("form:fechaRetiro");
        context.reset("form:observacion");
        boolean banderaValidacion = true;
        List<VigenciasTiposTrabajadores> listaVigenciaTiposTrabajadoresReal;
        listaVigenciaTiposTrabajadoresReal = administrarVigenciasTiposTrabajadores.vigenciasTiposTrabajadoresEmpleado(empleado.getSecuencia());
        VigenciasTiposTrabajadores vigenciaSeleccionadaRetirados = vigenciasTiposTrabajadores.get(indexRetiro);
        int tamanoTabla = listaVigenciaTiposTrabajadoresReal.size();
        int i = 0;
        while (banderaValidacion && (i < tamanoTabla)) {
            if (listaVigenciaTiposTrabajadoresReal.get(i).getSecuencia() == vigenciaSeleccionadaRetirados.getSecuencia()) {
                if (listaVigenciaTiposTrabajadoresReal.get(i).getTipotrabajador().getNombre().equalsIgnoreCase(
                        vigenciaSeleccionadaRetirados.getTipotrabajador().getNombre())) {
                    banderaValidacion = false;
                    almacenarRetirado = true;
                }
            }
            i++;
        }
        if (banderaValidacion == true && guardado == false) {
            //Dialogo de almacenar el nuevo registro de retiro antes de realizar operaciones
            context.update("formularioDialogos:guardarNuevoRegistroRetiro");
            context.execute("guardarNuevoRegistroRetiro.show()");
        }
    }

    /**
     * Metodo que valida que el registro pensionado se encuentra almacenado en
     * la base de datos, en caso contrario muestra un dialogo para que lo
     * almacene o no
     */
    public void validarRegistroSeleccionadoPensionado() {
        System.out.println("ENTRO validarRegistroSeleccionadoPensionado");
        System.out.println("Secuencia: " + pensionVigencia.getSecuencia());
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:fechaPensionInicio");
        context.reset("form:fechaPensionFinal");
        context.reset("form:porcentajePension");
        context.reset("form:clasePension");
        context.reset("form:tipoPensionado");
        context.reset("form:resolucionPension");
        context.reset("form:causaBiente");
        context.reset("form:tutorPension");
        boolean banderaValidacion = true;
        List<VigenciasTiposTrabajadores> listaVigenciaTiposTrabajadoresReal;
        listaVigenciaTiposTrabajadoresReal = administrarVigenciasTiposTrabajadores.vigenciasTiposTrabajadoresEmpleado(empleado.getSecuencia());
        VigenciasTiposTrabajadores vigenciaSeleccionadaPension = vigenciasTiposTrabajadores.get(indexPension);
        int tamanoTabla = listaVigenciaTiposTrabajadoresReal.size();
        int i = 0;
        while (banderaValidacion && (i < tamanoTabla)) {
            if (listaVigenciaTiposTrabajadoresReal.get(i).getSecuencia() == vigenciaSeleccionadaPension.getSecuencia()) {
                if (listaVigenciaTiposTrabajadoresReal.get(i).getTipotrabajador().getNombre().equalsIgnoreCase(vigenciaSeleccionadaPension.getTipotrabajador().getNombre())) {
                    banderaValidacion = false;
                    almacenarPensionado = true;
                }
            }
            i++;
        }
        if (banderaValidacion == true && guardado == false) {
            //Dialogo de almacenar el nuevo registro de retiro antes de realizar operaciones
          /*
             * context.update("formularioDialogos:guardarNuevoRegistroPensionado");
             * context.execute("guardarNuevoRegistroPensionado.show()");
             */
        }
        System.out.println("SALIO validarRegistroSeleccionadoPensionado");
    }

    /**
     * Guardar el nuevo registro de extrabajador
     */
    public void guardarNuevoRegistroRetiro() {
        guardarCambiosVTT();
        almacenarRetirado = true;
    }

    /**
     * Guardar el nuevo registro de pensionado
     */
    public void guardarNuevoRegistroPension() {
        System.out.println("ENTRO guardarNuevoRegistroPension()");
        guardarCambiosVTT();
        almacenarPensionado = true;
    }

    /**
     * Guardado general de la pagina
     */
    public void guardarGeneral() {
        System.out.println("ENTRO GUARDADO GENERAL");
        if (cambiosPagina == false) {
            if ((almacenarRetirado == true) && (banderaEliminarRetiro == false)) {
                guardarDatosRetiros();

            }
            if ((almacenarPensionado == true) && (banderaEliminarPension == false)) {
                System.out.println("ENTRO IF ALMACENAR PENSIONADO");
                guardarDatosPensiones();

            }
            if (guardado == false) {
                guardarCambiosVTT();

            }
        }
        System.out.println("SALIO");
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {
            int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASTIPOSTRABAJADORES");
            backUpSecRegistro = secRegistro;
            secRegistro = null;
            if (resultado == 1) {
                context.execute("errorObjetosDB.show()");
            } else if (resultado == 2) {
                context.execute("confirmarRastro.show()");
            } else if (resultado == 3) {
                context.execute("errorRegistroRastro.show()");
            } else if (resultado == 4) {
                context.execute("errorTablaConRastro.show()");
            } else if (resultado == 5) {
                context.execute("errorTablaSinRastro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASTIPOSTRABAJADORES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public List<VigenciasTiposTrabajadores> getVigenciasTiposTrabajadoresEmpleado() {
        try {
            if (vigenciasTiposTrabajadores == null) {
                if (empleado != null) {
                    return vigenciasTiposTrabajadores = administrarVigenciasTiposTrabajadores.vigenciasTiposTrabajadoresEmpleado(empleado.getSecuencia());
                }
            }
            return vigenciasTiposTrabajadores;
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasTiposTrabajadoresEmpleado ");
            return null;
        }
    }

    public void setVigenciasTiposTrabajadores(List<VigenciasTiposTrabajadores> vigenciasTiposTrabajadores) {
        this.vigenciasTiposTrabajadores = vigenciasTiposTrabajadores;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public List<VigenciasTiposTrabajadores> getFiltrarVTT() {
        return filtrarVTT;
    }

    public void setFiltrarVTT(List<VigenciasTiposTrabajadores> filtrarVTT) {
        this.filtrarVTT = filtrarVTT;
    }

    public VigenciasTiposTrabajadores getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasTiposTrabajadores nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<TiposTrabajadores> getListaTiposTrabajadores() {
        try {
            if (listaTiposTrabajadores == null || listaTiposTrabajadores.isEmpty()) {
                listaTiposTrabajadores = administrarVigenciasTiposTrabajadores.tiposTrabajadores();
            }
            return listaTiposTrabajadores;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setListaTiposTrabajadores(List<TiposTrabajadores> listaTiposTrabajadores) {
        this.listaTiposTrabajadores = listaTiposTrabajadores;
    }

    public List<TiposTrabajadores> getFiltradoTiposTrabajadores() {
        return filtradoTiposTrabajadores;
    }

    public void setFiltradoTiposTrabajadores(List<TiposTrabajadores> filtradoTiposTrabajadores) {
        this.filtradoTiposTrabajadores = filtradoTiposTrabajadores;
    }

    public VigenciasTiposTrabajadores getEditarVTT() {
        return editarVTT;
    }

    public void setEditarVTT(VigenciasTiposTrabajadores editarVTT) {
        this.editarVTT = editarVTT;
    }

    public VigenciasTiposTrabajadores getDuplicarVTT() {
        return duplicarVTT;
    }

    public void setDuplicarVTT(VigenciasTiposTrabajadores duplicarVTT) {
        this.duplicarVTT = duplicarVTT;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSelecionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSelecionado;
    }

    public Retirados getRetiroVigencia() {
        return retiroVigencia;
    }

    public void setRetiroVigencia(Retirados retiroVigencia) {
        this.retiroVigencia = retiroVigencia;
    }

    public List<MotivosRetiros> getMotivosRetiros() {
        try {
            if (motivosRetiros == null || motivosRetiros.isEmpty()) {
                motivosRetiros = administrarVigenciasTiposTrabajadores.motivosRetiros();
            }
            return motivosRetiros;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setMotivosRetiros(List<MotivosRetiros> motivosRetiros) {
        this.motivosRetiros = motivosRetiros;
    }

    public MotivosRetiros getMotivoRetiroSeleccionado() {
        return motivoRetiroSeleccionado;
    }

    public void setMotivoRetiroSeleccionado(MotivosRetiros motivoRetiroSeleccionado) {
        this.motivoRetiroSeleccionado = motivoRetiroSeleccionado;
    }

    public List<Pensionados> getListaPensionados() {
        try {
            if (listaPensionados == null || listaPensionados.isEmpty()) {
                listaPensionados = administrarVigenciasTiposTrabajadores.listaPensionados();
            }
            return listaPensionados;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setListaPensionados(List<Pensionados> listaPensionados) {
        this.listaPensionados = listaPensionados;
    }

    public Pensionados getPensionVigencia() {
        return pensionVigencia;
    }

    public void setPensionVigencia(Pensionados pensionVigencia) {
        this.pensionVigencia = pensionVigencia;
    }

    public List<Personas> getListaPersonas() {
        try {
            if (listaPersonas == null || listaPersonas.isEmpty()) {
                listaPersonas = administrarVigenciasTiposTrabajadores.listaPersonas();
            }
            return listaPersonas;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setListaPersonas(List<Personas> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public Personas getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Personas personaSeleccionada) {
        this.personaSeleccionada = personaSeleccionada;
    }

    public List<ClasesPensiones> getClasesPensiones() {
        try {
            if (clasesPensiones == null || clasesPensiones.isEmpty()) {
                clasesPensiones = administrarVigenciasTiposTrabajadores.clasesPensiones();
            }
            return clasesPensiones;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setClasesPensiones(List<ClasesPensiones> clasesPensiones) {
        this.clasesPensiones = clasesPensiones;
    }

    public ClasesPensiones getClasesPensionesSeleccionada() {
        return clasesPensionesSeleccionada;
    }

    public void setClasesPensionesSeleccionada(ClasesPensiones clasesPensionesSeleccionada) {
        this.clasesPensionesSeleccionada = clasesPensionesSeleccionada;
    }

    public List<TiposPensionados> getTiposPensionados() {
        try {
            if (tiposPensionados == null || tiposPensionados.isEmpty()) {
                tiposPensionados = administrarVigenciasTiposTrabajadores.tiposPensionados();
            }
            return tiposPensionados;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setTiposPensionados(List<TiposPensionados> tiposPensionados) {
        this.tiposPensionados = tiposPensionados;
    }

    public TiposPensionados getTiposPensionadosSeleccionada() {
        return tiposPensionadosSeleccionada;
    }

    public void setTiposPensionadosSeleccionada(TiposPensionados tiposPensionadosSeleccionada) {
        this.tiposPensionadosSeleccionada = tiposPensionadosSeleccionada;
    }

    public Pensionados getPensionadoSeleccionado() {
        return pensionadoSeleccionado;
    }

    public void setPensionadoSeleccionado(Pensionados pensionadoSeleccionado) {
        this.pensionadoSeleccionado = pensionadoSeleccionado;
    }

    public List<ClasesPensiones> getClasesPensionesFiltrado() {
        return clasesPensionesFiltrado;
    }

    public void setClasesPensionesFiltrado(List<ClasesPensiones> clasesPensionesFiltrado) {
        this.clasesPensionesFiltrado = clasesPensionesFiltrado;
    }

    public List<TiposPensionados> getTiposPensionadosFiltrado() {
        return tiposPensionadosFiltrado;
    }

    public void setTiposPensionadosFiltrado(List<TiposPensionados> tiposPensionesFiltrado) {
        this.tiposPensionadosFiltrado = tiposPensionesFiltrado;
    }

    public List<Pensionados> getPensionadosFiltrado() {
        return pensionadosFiltrado;
    }

    public void setPensionadosFiltrado(List<Pensionados> pensionadosFiltrado) {
        this.pensionadosFiltrado = pensionadosFiltrado;
    }

    public List<Personas> getPersonasFiltrado() {
        return personasFiltrado;
    }

    public void setPersonasFiltrado(List<Personas> personasFiltrado) {
        this.personasFiltrado = personasFiltrado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public VigenciasTiposTrabajadores getVigenciaSeleccionada() {
        getVigenciasTiposTrabajadoresEmpleado();
        if (vigenciasTiposTrabajadores != null) {
            int tam = vigenciasTiposTrabajadores.size();
            if (tam > 0) {
                vigenciaSeleccionada = vigenciasTiposTrabajadores.get(0);
            }
        }
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasTiposTrabajadores vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public List<MotivosRetiros> getFiltradoMotivosRetiros() {
        return filtradoMotivosRetiros;
    }

    public void setFiltradoMotivosRetiros(List<MotivosRetiros> filtradoMotivosRetiros) {
        this.filtradoMotivosRetiros = filtradoMotivosRetiros;
    }

    public String getInfoRegistroTipoTrabajador() {
        getListaTiposTrabajadores();
        if (listaTiposTrabajadores != null) {
            infoRegistroTipoTrabajador = "Cantidad de registros: " + listaTiposTrabajadores.size();
        } else {
            infoRegistroTipoTrabajador = "Cantidad de registros: 0";
        }
        return infoRegistroTipoTrabajador;
    }

    public void setInfoRegistroTipoTrabajador(String infoRegistroTipoTrabajador) {
        this.infoRegistroTipoTrabajador = infoRegistroTipoTrabajador;
    }

    public String getInfoRegistroMotivoRetiros() {
        getMotivosRetiros();
        if (motivosRetiros != null) {
            infoRegistroMotivoRetiros = "Cantidad de registro : " + motivosRetiros.size();
        } else {
            infoRegistroMotivoRetiros = "Cantidad de registro : 0";
        }
        return infoRegistroMotivoRetiros;
    }

    public void setInfoRegistroMotivoRetiros(String infoRegistroMotivoRegistro) {
        this.infoRegistroMotivoRetiros = infoRegistroMotivoRegistro;
    }

    public String getInfoRegistroClasePension() {
        getClasesPensiones();
        if (clasesPensiones != null) {
            infoRegistroClasePension = "Cantidad de registros : " + clasesPensiones.size();
        } else {
            infoRegistroClasePension = "Cantidad de registros : 0";
        }
        return infoRegistroClasePension;
    }

    public void setInfoRegistroClasePension(String infoRegistroClasePension) {
        this.infoRegistroClasePension = infoRegistroClasePension;
    }

    public String getInfoRegistroTipoPension() {
        getListaPensionados();
        if (tiposPensionados != null) {
            infoRegistroTipoPension = "Cantidad de registros : " + tiposPensionados.size();
        } else {
            infoRegistroTipoPension = "Cantidad de registros: 0";
        }
        return infoRegistroTipoPension;
    }

    public void setInfoRegistroTipoPension(String infoRegistroTipoPension) {
        this.infoRegistroTipoPension = infoRegistroTipoPension;
    }

    public String getInfoRegistroEmpleado() {
        getListaPensionados();
        if (listaPensionados != null) {
            infoRegistroEmpleado = "Cantidad de registros : " + listaPensionados.size();
        } else {
            infoRegistroEmpleado = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleado;
    }

    public void setInfoRegistroEmpleado(String infoRegistroEmpleado) {
        this.infoRegistroEmpleado = infoRegistroEmpleado;
    }

    public String getInfoRegistroPersona() {
        getListaPersonas();
        if (listaPersonas != null) {
            infoRegistroPersona = "Cantidad de registros : " + listaPersonas.size();
        } else {
            infoRegistroPersona = "Cantidad de registros : 0";
        }
        return infoRegistroPersona;
    }

    public void setInfoRegistroPersona(String infoRegistroPersona) {
        this.infoRegistroPersona = infoRegistroPersona;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
}

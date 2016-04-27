package Controlador;

import Entidades.ActualUsuario;
import Entidades.Empleados;
import Entidades.ReformasLaborales;
import Entidades.VigenciasReformasLaborales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasReformasLaboralesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import org.apache.log4j.Logger;
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
public class ControlVigenciasReformasLaborales implements Serializable {

    @EJB
    AdministrarVigenciasReformasLaboralesInterface administrarVigenciasReformasLaborales;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Cargos
    private List<VigenciasReformasLaborales> vigenciasReformasLaborales;
    private List<VigenciasReformasLaborales> filtrarVRL;
    private VigenciasReformasLaborales vigenciaSeleccionada;
    private List<ReformasLaborales> listaReformasLaborales;
    private ReformasLaborales reformaLaboralSelecionada;
    private List<ReformasLaborales> filtradoReformasLaborales;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vrlFecha, vrlNombre;
    //Otros
    private boolean aceptar;
    //modificar
    private List<VigenciasReformasLaborales> listVRLModificar;
    private boolean guardado;
    //crear VC
    public VigenciasReformasLaborales nuevaVigencia;
    private List<VigenciasReformasLaborales> listVRLCrear;
    private BigInteger nuevaRFSecuencia;
    private int paraNuevaRF;
    //borrar VC
    private List<VigenciasReformasLaborales> listVRLBorrar;
    //editar celda
    private VigenciasReformasLaborales editarVRL;
    private int cualCelda, tipoLista;
    //duplicar
    private VigenciasReformasLaborales duplicarVRL;
    private String reformaLaboral;
    private boolean permitirIndex;
    private Date fechaParametro;
    private Date fechaIni;
    private String altoTabla;
    public String infoRegistro;
    //
    private String infoRegistroReformaLaboral;
    private final static Logger logger = Logger.getLogger("connectionSout");
    private final SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    private final Date fechaDia = new Date();
    //VALIDACION
    private String mensajeValidacion;
    private ActualUsuario actualUsuario;
    //
    private DataTable tablaC;

    /**
     * Constructor de la clases Controlador
     */
    public ControlVigenciasReformasLaborales() {

        vigenciasReformasLaborales = null;
        listaReformasLaborales = new ArrayList<ReformasLaborales>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVRLBorrar = new ArrayList<VigenciasReformasLaborales>();
        //crear aficiones
        listVRLCrear = new ArrayList<VigenciasReformasLaborales>();
        paraNuevaRF = 0;
        //modificar aficiones
        listVRLModificar = new ArrayList<VigenciasReformasLaborales>();
        //editar
        editarVRL = new VigenciasReformasLaborales();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasReformasLaborales();
        nuevaVigencia.setReformalaboral(new ReformasLaborales());
        permitirIndex = true;
        altoTabla = "270";
        mensajeValidacion = "";
        vigenciaSeleccionada = null;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasReformasLaborales.obtenerConexion(ses.getId());
            actualUsuario = administrarVigenciasReformasLaborales.obtenerActualUsuario();
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            logger.error("Metodo: inicializarAdministrador - ControlVigenciasReformasLaborales - Fecha : " + format.format(fechaDia) + " - Usuario : " + actualUsuario.getAlias() + " - Error : " + e.getCause());
        }
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param empl Secuencia del Empleado
     */
    public void recibirEmpleado(Empleados empl) {
        RequestContext context = RequestContext.getCurrentInstance();
        //PropertyConfigurator.configure("log4j.properties");
        vigenciasReformasLaborales = null;
        listaReformasLaborales = null;
        empleado = empl;
        getVigenciasReformasLaboralesEmpleado();
        if (vigenciasReformasLaborales != null) {
            modificarInfoRegistro(vigenciasReformasLaborales.size());
            if (!vigenciasReformasLaborales.isEmpty()){
                vigenciaSeleccionada = vigenciasReformasLaborales.get(0);
            }
        } else {
            modificarInfoRegistro(0);
        }
        context.update("form:informacionRegistro");
    }

    public void modificarVRL(VigenciasReformasLaborales vrl) {

        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVRLCrear.contains(vigenciaSeleccionada)) {
            vigenciaSeleccionada = vrl;
            if (listVRLModificar.isEmpty()) {
                listVRLModificar.add(vigenciaSeleccionada);
            } else if (!listVRLModificar.contains(vigenciaSeleccionada)) {
                listVRLModificar.add(vigenciaSeleccionada);
            }
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    public boolean validarFechasRegistro(int campo) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (campo == 0) {
            VigenciasReformasLaborales auxiliar = null;
            auxiliar = vigenciaSeleccionada;

            if (auxiliar.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (campo == 1) {
            if (nuevaVigencia.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (campo == 2) {
            if (duplicarVRL.getFechavigencia().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechas(VigenciasReformasLaborales vrl, int campo) {
        vigenciaSeleccionada = vrl;

        if (vigenciaSeleccionada.getFechavigencia() != null) {
            boolean retorno = false;
            retorno = validarFechasRegistro(0);
            if (retorno) {
                cambiarIndice(vigenciaSeleccionada, campo);
                modificarVRL(vigenciaSeleccionada);
            } else {
                vigenciaSeleccionada.setFechavigencia(fechaIni);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVRLEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            vigenciaSeleccionada.setFechavigencia(fechaIni);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVRLEmpleado");
            context.execute("errorRegNew.show()");
        }
    }

    public void modificarVRL(VigenciasReformasLaborales vrl, String confirmarCambio, String valorConfirmar) {
        vigenciaSeleccionada = vrl;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMALABORAL")) {
            vigenciaSeleccionada.getReformalaboral().setNombre(reformaLaboral);

            for (int i = 0; i < listaReformasLaborales.size(); i++) {
                if (listaReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSeleccionada.setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));

                listaReformasLaborales.clear();
                getListaReformasLaborales();
            } else {
                permitirIndex = false;
                getInfoRegistroReformaLaboral();
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listVRLCrear.contains(vigenciaSeleccionada)) {

                if (listVRLModificar.isEmpty()) {
                    listVRLModificar.add(vigenciaSeleccionada);
                } else if (!listVRLModificar.contains(vigenciaSeleccionada)) {
                    listVRLModificar.add(vigenciaSeleccionada);
                }
                if (guardado) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }

        }
        context.update("form:datosVRLEmpleado");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("REFORMALABORAL")) {
            if (tipoNuevo == 1) {
                reformaLaboral = nuevaVigencia.getReformalaboral().getNombre();
            } else if (tipoNuevo == 2) {
                reformaLaboral = duplicarVRL.getReformalaboral().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("REFORMALABORAL")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getReformalaboral().setNombre(reformaLaboral);
            } else if (tipoNuevo == 2) {
                duplicarVRL.getReformalaboral().setNombre(reformaLaboral);
            }
            for (int i = 0; i < listaReformasLaborales.size(); i++) {
                if (listaReformasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaReformaLaboral");
                } else if (tipoNuevo == 2) {
                    duplicarVRL.setReformalaboral(listaReformasLaborales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarReformaLaboral");
                }
                listaReformasLaborales.clear();
                getListaReformasLaborales();
            } else {
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaReformaLaboral");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarReformaLaboral");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla
     * VigenciasReformasLaborales
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(VigenciasReformasLaborales vrl, int celda) {
        vigenciaSeleccionada = vrl;
        if (permitirIndex) {
            cualCelda = celda;
            fechaIni = vigenciaSeleccionada.getFechavigencia();
            if (cualCelda == 1) {
                reformaLaboral = vigenciaSeleccionada.getReformalaboral().getNombre();
            }
        }
    }
    //GUARDAR

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasReformasLaborales
     */
    public void guardarCambiosVRL() {
        if (guardado == false) {
            if (!listVRLBorrar.isEmpty()) {
                for (int i = 0; i < listVRLBorrar.size(); i++) {
                    administrarVigenciasReformasLaborales.borrarVRL(listVRLBorrar.get(i));
                }
                listVRLBorrar.clear();
            }
            if (!listVRLCrear.isEmpty()) {
                for (int i = 0; i < listVRLCrear.size(); i++) {
                    administrarVigenciasReformasLaborales.crearVRL(listVRLCrear.get(i));
                }
                listVRLCrear.clear();
            }
            if (!listVRLModificar.isEmpty()) {
                administrarVigenciasReformasLaborales.modificarVRL(listVRLModificar);
                listVRLModificar.clear();
            }
            vigenciasReformasLaborales = null;
            getVigenciasReformasLaboralesEmpleado();
            if (vigenciasReformasLaborales != null) {
                modificarInfoRegistro(vigenciasReformasLaborales.size());
            } else {
                modificarInfoRegistro(0);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");
            context.update("form:datosVRLEmpleado");
            guardado = true;
            context.update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            paraNuevaRF = 0;
        }
        vigenciaSeleccionada = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }

        listVRLBorrar.clear();
        listVRLCrear.clear();
        listVRLModificar.clear();
        vigenciaSeleccionada = null;
        paraNuevaRF = 0;
        vigenciasReformasLaborales = null;
        getVigenciasReformasLaboralesEmpleado();
        if (vigenciasReformasLaborales != null) {
            modificarInfoRegistro(vigenciasReformasLaborales.size());
        } else {
            modificarInfoRegistro(0);
        }
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVRLEmpleado");
        context.update("form:ACEPTAR");
        context.update("form:informacionRegistro");

    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            editarVRL = vigenciaSeleccionada;

            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarReformaLaboral");
                context.execute("editarReformaLaboral.show()");
                cualCelda = -1;
            }
        }
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciaReformaLaboral
     */
    public void agregarNuevaVRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        mensajeValidacion = "";
        for (int j = 0; j < vigenciasReformasLaborales.size(); j++) {
            if (nuevaVigencia.getFechavigencia().equals(vigenciasReformasLaborales.get(j).getFechavigencia())) {
                cont++;
            }
        }
        if (cont > 0) {
            mensajeValidacion = "FECHAS NO REPETIDAS";
            context.update("form:validacionNuevoF");
            context.execute("validacionNuevoF.show()");
        } else {
            if (nuevaVigencia.getFechavigencia() != null && nuevaVigencia.getReformalaboral().getSecuencia() != null) {
                if (validarFechasRegistro(1)) {
                    if (bandera == 1) {
                        //CERRAR FILTRADO
                        FacesContext c = FacesContext.getCurrentInstance();
                        vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
                        vrlFecha.setFilterStyle("display: none; visibility: hidden;");
                        vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
                        vrlNombre.setFilterStyle("display: none; visibility: hidden;");
                        altoTabla = "270";
                        RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
                        bandera = 0;
                        filtrarVRL = null;
                        tipoLista = 0;
                    }
                    //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                    paraNuevaRF++;
                    nuevaRFSecuencia = BigInteger.valueOf(paraNuevaRF);
                    nuevaVigencia.setSecuencia(nuevaRFSecuencia);
                    nuevaVigencia.setEmpleado(empleado);
                    listVRLCrear.add(nuevaVigencia);

                    vigenciasReformasLaborales.add(nuevaVigencia);
                    nuevaVigencia = new VigenciasReformasLaborales();
                    nuevaVigencia.setReformalaboral(new ReformasLaborales());
                    modificarInfoRegistro(vigenciasReformasLaborales.size());
                    context.update("form:informacionRegistro");

                    context.update("form:datosVRLEmpleado");
                    context.execute("NuevoRegistroVRL.hide()");
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    vigenciaSeleccionada = null;
                } else {
                    context.execute("errorFechas.show()");
                }
            } else {
                context.execute("errorRegNew.show()");
            }
        }
    }
//LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaVRL() {
        nuevaVigencia = new VigenciasReformasLaborales();
        nuevaVigencia.setReformalaboral(new ReformasLaborales());
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            duplicarVRL = new VigenciasReformasLaborales();
            paraNuevaRF++;
            nuevaRFSecuencia = BigInteger.valueOf(paraNuevaRF);

            duplicarVRL.setEmpleado(vigenciaSeleccionada.getEmpleado());
            duplicarVRL.setFechavigencia(vigenciaSeleccionada.getFechavigencia());
            duplicarVRL.setReformalaboral(vigenciaSeleccionada.getReformalaboral());

            context.update("formularioDialogos:duplicarVRL");
            context.execute("DuplicarRegistroVRL.show()");
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasReformasLaborales
     */
    public void confirmarDuplicar() {
        RequestContext context = RequestContext.getCurrentInstance();
        int cont = 0;
        mensajeValidacion = "";
        for (int j = 0; j < vigenciasReformasLaborales.size(); j++) {
            if (duplicarVRL.getFechavigencia().equals(vigenciasReformasLaborales.get(j).getFechavigencia())) {
                cont++;
            }
        }
        if (cont > 0) {
            mensajeValidacion = "FECHAS NO REPETIDAS";
            context.update("form:validacionDuplicarF");
            context.execute("validacionDuplicarF.show()");
        } else {
            if (duplicarVRL.getFechavigencia() != null && duplicarVRL.getReformalaboral().getSecuencia() != null) {
                if (validarFechasRegistro(2)) {
                    paraNuevaRF++;
                    nuevaRFSecuencia = BigInteger.valueOf(paraNuevaRF);
                    duplicarVRL.setSecuencia(nuevaRFSecuencia);
                    vigenciasReformasLaborales.add(duplicarVRL);
                    listVRLCrear.add(duplicarVRL);
                    modificarInfoRegistro(vigenciasReformasLaborales.size());
                    context.update("form:informacionRegistro");
                    context.update("form:datosVRLEmpleado");
                    context.execute("DuplicarRegistroVRL.hide()");
                    vigenciaSeleccionada = null;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (bandera == 1) {
                        //CERRAR FILTRADO
                        FacesContext c = FacesContext.getCurrentInstance();
                        vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
                        vrlFecha.setFilterStyle("display: none; visibility: hidden;");
                        vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
                        vrlNombre.setFilterStyle("display: none; visibility: hidden;");
                        altoTabla = "270";
                        RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
                        bandera = 0;
                        filtrarVRL = null;
                        tipoLista = 0;
                    }
                    duplicarVRL = new VigenciasReformasLaborales();
                } else {
                    context.execute("errorFechas.show()");
                }
            } else {
                context.execute("errorRegNew.show()");
            }
        }
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarVRL() {
        duplicarVRL = new VigenciasReformasLaborales();
        duplicarVRL.setReformalaboral(new ReformasLaborales());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (!listVRLModificar.isEmpty() && listVRLModificar.contains(vigenciaSeleccionada)) {
                int modIndex = listVRLModificar.indexOf(vigenciaSeleccionada);
                listVRLModificar.remove(modIndex);
                listVRLBorrar.add(vigenciaSeleccionada);
            } else if (!listVRLCrear.isEmpty() && listVRLCrear.contains(vigenciaSeleccionada)) {
                int crearIndex = listVRLCrear.indexOf(vigenciaSeleccionada);
                listVRLCrear.remove(crearIndex);
            } else {
                listVRLBorrar.add(vigenciaSeleccionada);
            }
            vigenciasReformasLaborales.remove(vigenciaSeleccionada);
            if (tipoLista == 1) {
                filtrarVRL.remove(vigenciaSeleccionada);
            }
            modificarInfoRegistro(vigenciasReformasLaborales.size());

            context.update("form:datosVRLEmpleado");
            context.update("form:informacionRegistro");
            vigenciaSeleccionada = null;

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("width: 85%");
            vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("width: 85%");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
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
            vrlFecha = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlFecha");
            vrlFecha.setFilterStyle("display: none; visibility: hidden;");
            vrlNombre = (Column) c.getViewRoot().findComponent("form:datosVRLEmpleado:vrlNombre");
            vrlNombre.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            context.update("form:datosVRLEmpleado");
            bandera = 0;
            filtrarVRL = null;
            tipoLista = 0;
        }
        listVRLBorrar.clear();
        listVRLCrear.clear();
        listVRLModificar.clear();
        vigenciaSeleccionada = null;
        paraNuevaRF = 0;
        vigenciasReformasLaborales = null;
        guardado = true;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

    /**
     * Metodo que ejecuta el dialogo de reforma laboral
     *
     * @param indice Fila de la tabla
     * @param list Lista filtrada - Lista real
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
    public void asignarIndexT(VigenciasReformasLaborales vrl) {
        vigenciaSeleccionada = vrl;
        tipoActualizacion = 0;

        if (listaReformasLaborales != null) {
            modificarInfoRegistroRefLab(listaReformasLaborales.size());
        } else {
            modificarInfoRegistroRefLab(0);
        }
        RequestContext.getCurrentInstance().update("form:ReformasLaboralesDialogo");
        RequestContext.getCurrentInstance().execute("ReformasLaboralesDialogo.show()");
    }

    public void asignarIndex(int LND) {
        if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (listaReformasLaborales != null) {
            modificarInfoRegistroRefLab(listaReformasLaborales.size());
        } else {
            modificarInfoRegistroRefLab(0);
        }
        RequestContext.getCurrentInstance().update("form:ReformasLaboralesDialogo");
        RequestContext.getCurrentInstance().execute("ReformasLaboralesDialogo.show()");
    }

    //LOVS
    //CIUDAD
    /**
     * Metodo que actualiza la reforma laboral seleccionada
     */
    public void actualizarReformaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {// Si se trabaja sobre la tabla y no sobre un dialogo
            vigenciaSeleccionada.setReformalaboral(reformaLaboralSelecionada);
            if (!listVRLCrear.contains(vigenciaSeleccionada)) {
                if (listVRLModificar.isEmpty()) {
                    listVRLModificar.add(vigenciaSeleccionada);
                } else if (!listVRLModificar.contains(vigenciaSeleccionada)) {
                    listVRLModificar.add(vigenciaSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVRLEmpleado");
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {// Para crear registro
            nuevaVigencia.setReformalaboral(reformaLaboralSelecionada);
            context.update("formularioDialogos:nuevaReformaLaboral");
        } else if (tipoActualizacion == 2) {// Para duplicar registro
            duplicarVRL.setReformalaboral(reformaLaboralSelecionada);
            context.update("formularioDialogos:duplicarReformaLaboral");
        }
        filtradoReformasLaborales = null;
        aceptar = true;
        tipoActualizacion = -1;
        context.reset("form:lovReformasLaborales:globalFilter");
        context.execute("lovReformasLaborales.clearFilters()");
        context.execute("ReformasLaboralesDialogo.hide()");
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioReformaLaboral() {
        filtradoReformasLaborales = null;
        reformaLaboralSelecionada = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        //context.update("form:ReformasLaboralesDialogo");
        //context.update("form:lovReformasLaborales");
        //context.update("form:aceptarRL");
        context.reset("form:lovReformasLaborales:globalFilter");
        context.execute("lovReformasLaborales.clearFilters()");
        context.execute("ReformasLaboralesDialogo.hide()");
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a las
     * reformas laborales
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (cualCelda == 1) {

                if (listaReformasLaborales != null) {
                    modificarInfoRegistroRefLab(listaReformasLaborales.size());
                } else {
                    modificarInfoRegistroRefLab(0);
                }
                context.update("form:ReformasLaboralesDialogo");
                context.execute("ReformasLaboralesDialogo.show()");
                tipoActualizacion = 0;
            }
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
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosVRLEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasReformasLaboralesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosVRLEmpleadoExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasReformasLaboralesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistro(filtrarVRL.size());
        context.update("form:informacionRegistro");
    }

    public void eventoFiltrarRL() {
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistroRefLab(filtradoReformasLaborales.size());
        context.update("form:infoRegistroReformaLaboral");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(vigenciaSeleccionada.getSecuencia(), "VIGENCIASREFORMASLABORALES");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASREFORMASLABORALES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    private void modificarInfoRegistroRefLab(int valor) {
        infoRegistroReformaLaboral = String.valueOf(valor);
    }

    public void recordarSeleccion() {
        if (vigenciaSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosVRLEmpleado");
            tablaC.setSelection(vigenciaSeleccionada);
            System.out.println("vigenciaSeleccionada: " + vigenciaSeleccionada);
        }
    }
    //GETTERS AND SETTERS

    /**
     * Metodo que obtiene las VigenciasReformasLaborales de un empleado, en caso
     * de ser null por medio del administrar hace el llamado para almacenarlo
     *
     * @return listVC Lista VigenciasReformasLaborales
     */
    public List<VigenciasReformasLaborales> getVigenciasReformasLaboralesEmpleado() {
        try {
            if (vigenciasReformasLaborales == null) {
                vigenciasReformasLaborales = administrarVigenciasReformasLaborales.vigenciasReformasLaboralesEmpleado(empleado.getSecuencia());
            }
            return vigenciasReformasLaborales;
        } catch (Exception e) {
            logger.error("Metodo: getVigenciasReformasLaboralesEmpleado - ControlVigenciasReformasLaborales - Fecha : " + format.format(fechaDia) + " - Usuario : " + actualUsuario.getAlias() + " - Error : " + e.toString());
            return null;
        }
    }

    public void setVigenciasReformasLaborales(List<VigenciasReformasLaborales> vigenciasReformasLaborales) {
        this.vigenciasReformasLaborales = vigenciasReformasLaborales;
    }

    /**
     * Metodo que obtiene el empleado usando en el momento, en caso de ser null
     * por medio del administrar obtiene el valor
     *
     * @return empl Empleado que esta siendo usando en el momento
     */
    public Empleados getEmpleado() {
        return empleado;
    }

    public List<VigenciasReformasLaborales> getFiltrarVRL() {
        return filtrarVRL;
    }

    public void setFiltrarVRL(List<VigenciasReformasLaborales> filtrarVRL) {
        this.filtrarVRL = filtrarVRL;
    }

    public VigenciasReformasLaborales getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasReformasLaborales nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    /**
     * Metodo que obtiene la lista de reformas laborales, en caso de ser null
     * por medio del administrar los obtiene
     *
     * @return listTC Lista Reformas Laborales
     */
    public List<ReformasLaborales> getListaReformasLaborales() {
        if (listaReformasLaborales == null) {
            listaReformasLaborales = administrarVigenciasReformasLaborales.reformasLaborales();
        }
        return listaReformasLaborales;
    }

    public void setListaReformasLaborales(List<ReformasLaborales> listaLaboraleses) {
        this.listaReformasLaborales = listaLaboraleses;
    }

    public List<ReformasLaborales> getFiltradoReformasLaborales() {
        return filtradoReformasLaborales;
    }

    public void setFiltradoReformasLaborales(List<ReformasLaborales> filtradoReformasLaborales) {
        this.filtradoReformasLaborales = filtradoReformasLaborales;
    }

    public VigenciasReformasLaborales getEditarVRL() {
        return editarVRL;
    }

    public void setEditarVRL(VigenciasReformasLaborales editarVRL) {
        this.editarVRL = editarVRL;
    }

    public VigenciasReformasLaborales getDuplicarVRL() {
        return duplicarVRL;
    }

    public void setDuplicarVRL(VigenciasReformasLaborales duplicarVRL) {
        this.duplicarVRL = duplicarVRL;
    }

    public ReformasLaborales getReformaLaboralSelecionada() {
        return reformaLaboralSelecionada;
    }

    public void setReformaLaboralSelecionada(ReformasLaborales reformaLaboralSelecionada) {
        this.reformaLaboralSelecionada = reformaLaboralSelecionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public VigenciasReformasLaborales getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasReformasLaborales vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getInfoRegistroReformaLaboral() {
        return infoRegistroReformaLaboral;
    }

    public void setInfoRegistroReformaLaboral(String infoRegistroReformaLaboral) {
        this.infoRegistroReformaLaboral = infoRegistroReformaLaboral;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
}

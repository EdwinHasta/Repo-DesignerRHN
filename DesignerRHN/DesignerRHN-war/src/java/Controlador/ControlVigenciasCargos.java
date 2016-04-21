package Controlador;

import Convertidores.MotivosCambiosCargosConverter;
import Entidades.*;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEstructurasInterface;
import InterfaceAdministrar.AdministrarMotivosCambiosCargosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasCargosInterface;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
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
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@ManagedBean
@SessionScoped
public class ControlVigenciasCargos implements Serializable {

    //------------------------------------------------------------------------------------------
    //EJB
    //------------------------------------------------------------------------------------------
    @EJB
    AdministrarVigenciasCargosInterface administrarVigenciasCargos;
    @EJB
    AdministrarMotivosCambiosCargosInterface administrarMotivosCambiosCargos;
    @EJB
    AdministrarEstructurasInterface administrarEstructuras;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //------------------------------------------------------------------------------------------
    //ATRIBUTOS
    //------------------------------------------------------------------------------------------
    //Vigencias Cargos
    private List<VigenciasCargos> vigenciasCargosEmpleado;
    private List<VigenciasCargos> filterVC;
    private VigenciasCargos vigenciaSeleccionada;
    private DataTable tablaC;
    //private List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresesLista;
    private List<VwTiposEmpleados> actualesTiposTrabajadores;
    private List<VwTiposEmpleados> filtradoActualesTiposTrabajadores;
    private VwTiposEmpleados tiposTrabajadorJefeSeleccionado;
    private Date fechaVigencia;
//    private BigInteger secuencia;
    private Empleados empleado;
    private int tipoActualizacion;//Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vcFecha, vcEstructura, vcMotivo, vcNombreCargo, vcCentrosC, vcNombreJefe;
    //Estructuras
    private List<Estructuras> dlgEstructuras;
    private List<Estructuras> filterEstructuras;
    private Estructuras estructuraSeleccionada;
    //Motivos
    private List<MotivosCambiosCargos> motivosCambiosCargos;
    private List<MotivosCambiosCargos> filterMotivos;
    private MotivosCambiosCargos motivoSeleccionado;
    private MotivosCambiosCargosConverter motivoConverter;
    //Cargos
    private List<Cargos> cargos;
    private List<Cargos> filterCargos;
    private Cargos cargoSeleccionado;
    //Otros
    private boolean aceptar;
    private SimpleDateFormat formatoFecha;
    //Pruebas para modificar
    private List<VigenciasCargos> listVCModificar;
    private boolean guardado;
    //crear VC
    public VigenciasCargos nuevaVigencia;
    private List<VigenciasCargos> listVCCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<VigenciasCargos> listVCBorrar;
    //editar celda
    private VigenciasCargos editarVC;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private VigenciasCargos duplicarVC;
    //AUTOCOMPLETAR
    private String nombreEstructura, motivoCambioC, nombreCargo, nombreCompleto;
    private Date fechaVigenciaBck;
    private boolean permitirIndex;
    //ACTIVAR  - DESACTIVAR BOTONES ULTIMO Y PRIMER REGISTRO
    private boolean botonPrimero;
    private boolean botonAnterior;
    private boolean botonSiguiente;
    private boolean botonUltimo;
    //REGISTRO QUE TENDRA EL FOCO
    private String registroFoco;
    //INFORMACION DEL REGISTRO QUE TIENE EL FOCO
    private String infoRegistro;
    private String altoTabla;
    private String infoRegistroJefe;
    private String infoRegistroEstructuras;
    private String infoRegistroMotivos;
    private String infoRegistroCargos;
    //
    private boolean activarLOV;

    //------------------------------------------------------------------------------------------
    //CONSTRUCTOR(ES)
    //------------------------------------------------------------------------------------------
    public ControlVigenciasCargos() {
        System.out.println("ControlVigenciasCargos");

        actualesTiposTrabajadores = null;
        motivosCambiosCargos = null;
        dlgEstructuras = null;
        cargos = null;

        empleado = new Empleados();
        bandera = 0;
        //Vigencias Cargos
        vigenciasCargosEmpleado = null;
        fechaVigencia = new Date();
        //Estructuras
        //Motivos
        motivoSeleccionado = null;
        motivoConverter = new MotivosCambiosCargosConverter();
        //Cargos
        //Otros
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        aceptar = true;
        //borrar aficiones
        listVCBorrar = new ArrayList();
        //crear aficiones
        listVCCrear = new ArrayList();
        k = 0;
        //modificar aficiones
        listVCModificar = new ArrayList();
        //editar
        editarVC = new VigenciasCargos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigencia = new VigenciasCargos();
        nuevaVigencia.setEstructura(new Estructuras());
        nuevaVigencia.setMotivocambiocargo(new MotivosCambiosCargos());
        nuevaVigencia.setCargo(new Cargos());
        //lista empleados jefe
        //AUTOCOMPLETAR
        permitirIndex = true;
        //RASTRO
        //INICIALIZAR FOCO PARA EL PRIMER REGISTRO
        registroFoco = "form:datosVCEmpleado:editFecha";
        altoTabla = "292";
        vigenciaSeleccionada = null;

        activarLOV = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasCargos.obtenerConexion(ses.getId());
            administrarMotivosCambiosCargos.obtenerConexion(ses.getId());
            administrarEstructuras.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    //------------------------------------------------------------------------------------------
    //METODOS DE MANEJO DE INFORMACION
    //------------------------------------------------------------------------------------------
    //VIGENCIASCARGOS----------------------------------------------------
    public void recibirEmpleado(Empleados emp) {
        empleado = emp;
        getVigenciasCargosEmpleado();
        if (vigenciasCargosEmpleado != null) {
            vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
            modificarInfoRegistro(vigenciasCargosEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    /*
     * Metodo encargado de actualizar una VigenciaCargo en la base de datos
     */
    public void actualizarVigencias() {
        for (int i = 0; i < vigenciasCargosEmpleado.size(); i++) {
            administrarVigenciasCargos.editarVigenciaCargo(vigenciasCargosEmpleado.get(i));
        }
        vigenciasCargosEmpleado = null;
    }
    //ESTRUCTURAS--------------------------------------------------------
    /*
     * Metodo encargado de cargar las estructuras que van a componer el dialogo
     */

    public void cargarEstructuras(VigenciasCargos vCargo) {
        activarLOV = false;
        String forFecha = formatoFecha.format(vCargo.getFechavigencia());
        System.out.println("forFecha : " + forFecha);
        dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
        vigenciaSeleccionada = vCargo;
        if (dlgEstructuras != null) {
            modificarInfoRegistroEstructuras(dlgEstructuras.size());
        } else {
            modificarInfoRegistroEstructuras(0);
        }
        RequestContext.getCurrentInstance().update("form:dlgEstructuras");
        RequestContext.getCurrentInstance().update("form:listaValores");
        RequestContext.getCurrentInstance().execute("dlgEstructuras.show()");
        tipoActualizacion = 0;
    }

    public void cargarEstructurasNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            if (nuevaVigencia.getFechavigencia() == null) {
                context.execute("necesitaFecha.show()");
            } else {
                String forFecha = formatoFecha.format(nuevaVigencia.getFechavigencia());
                System.out.println("forFecha : " + forFecha);
                dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                if (dlgEstructuras != null) {
                    modificarInfoRegistroEstructuras(dlgEstructuras.size());
                } else {
                    modificarInfoRegistroEstructuras(0);
                }
                context.update("form:dlgEstructuras");
                context.execute("dlgEstructuras.show()");
            }
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            if (duplicarVC.getFechavigencia() == null) {
                context.execute("necesitaFecha.show()");
            } else {
                String forFecha = formatoFecha.format(duplicarVC.getFechavigencia());
                System.out.println("forFecha : " + forFecha);
                dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                if (dlgEstructuras != null) {
                    modificarInfoRegistroEstructuras(dlgEstructuras.size());
                } else {
                    modificarInfoRegistroEstructuras(0);
                }
                context.update("form:dlgEstructuras");
                context.execute("dlgEstructuras.show()");
            }
        }
    }
    /*
     * Metodo encargado de actualizar la Estructura de una VigenciaCargo
     */

    public void actualizarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaSeleccionada.setEstructura(estructuraSeleccionada);

            if (!listVCCrear.contains(vigenciaSeleccionada)) {
                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciaSeleccionada);
                } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                    listVCModificar.add(vigenciaSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setEstructura(estructuraSeleccionada);
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setEstructura(estructuraSeleccionada);
            context.update("formularioDialogos:duplicarVC");
        }
        filterEstructuras = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
        estructuraSeleccionada = null;
    }

    public void cancelarCambioEstructura() {
        //filterEstructuras = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
    //MOTIVOS----------------------------------------------------------
    /*
     * Metodo encargado de llenar la lista utilizada por el autocompletar
     */

    public List<MotivosCambiosCargos> autocompletarMotivo(String in) {
        List<MotivosCambiosCargos> mot = getMotivosCambiosCargos();
        List<MotivosCambiosCargos> rta = new ArrayList<MotivosCambiosCargos>();
        for (MotivosCambiosCargos m : mot) {
            if (m.getNombre().startsWith(in.toUpperCase())) {
                rta.add(m);
            }
        }
        //System.out.println("...");
        return rta;
    }
    /*
     * Metodo encargado de actualizar el MotivoCambioCargo de una VigenciaCargo
     */

    public void actualizarMotivo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaSeleccionada.setMotivocambiocargo(motivoSeleccionado);
            if (!listVCCrear.contains(vigenciaSeleccionada)) {
                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciaSeleccionada);
                } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                    listVCModificar.add(vigenciaSeleccionada);
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setMotivocambiocargo(motivoSeleccionado);
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setMotivocambiocargo(motivoSeleccionado);
            context.update("formularioDialogos:duplicarVC");
        }
        filterMotivos = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void cancelarCambioMotivo() {
        filterMotivos = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //CARGOS-----------------------------------------------------------
    /*
     * Metodo encargado de actualizar el MotivoCambioCargo de una VigenciaCargo
     */
    public void actualizarCargo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaSeleccionada.setCargo(cargoSeleccionado);
            if (!listVCCrear.contains(vigenciaSeleccionada)) {
                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciaSeleccionada);
                } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                    listVCModificar.add(vigenciaSeleccionada);
                }

            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setCargo(cargoSeleccionado);
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setCargo(cargoSeleccionado);
            context.update("formularioDialogos:duplicarVC");
        }
        filterCargos = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void cancelarCambioCargo() {
        filterCargos = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarEmpleadoJefe() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaSeleccionada.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(tiposTrabajadorJefeSeleccionado.getRfEmpleado()));
            if (!listVCCrear.contains(vigenciaSeleccionada)) {
                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciaSeleccionada);
                } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                    listVCModificar.add(vigenciaSeleccionada);
                }
            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(tiposTrabajadorJefeSeleccionado.getRfEmpleado()));
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(tiposTrabajadorJefeSeleccionado.getRfEmpleado()));
            context.update("formularioDialogos:duplicarVC");
        }
        //filtradoActualesTiposTrabajadores = null;
        aceptar = true;
        tipoActualizacion = -1;
        cualCelda = -1;

    }

    public void cancelarEmpleadoJefe() {
        //filtradoActualesTiposTrabajadores = null;
        //seleccionVWActualesTiposTrabajadoreses = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
    //FECHAVIGENCIA-------------------------------------------------------------
    /*
     * Metodo encargado de actualizar la fecha de la Vigencia seleccionada
     */

    public void actualizarFecha(Date fecha) {
        System.out.println(fecha);
        fechaVigencia = fecha;
    }
    /*
     * Metodo encargado de cambiar el formato de la fecha de una vigencia cargo
     */

    public String cambioFormatoFecha(VigenciasCargos vigencia) {
        Date fecha = vigencia.getFechavigencia();
        String forFecha = formatoFecha.format(fecha);
        return forFecha;
    }
    //OTROS---------------------------------------------------------------------
    /*
     * Metodo encargado de cambiar el valor booleano para habilitar un boton
     */

    public void activarAceptar() {
        aceptar = false;
    }
    /*
     * Metodo encargado de cambiar el valor booleano para deshabilitar un boton
     */

    public void desactivarAceptar() {
        aceptar = true;
    }

    public void asignarIndex(VigenciasCargos vCargos, int dlg) {
        vigenciaSeleccionada = vCargos;
        RequestContext context = RequestContext.getCurrentInstance();
        if (dlg == 0) {
            activarLOV = false;
            modificarInfoRegistroMotivos(motivosCambiosCargos.size());
            motivoSeleccionado = null;
            context.update("form:dlgMotivos");
            context.execute("dlgMotivos.show()");
        } else if (dlg == 1) {
            activarLOV = false;
            cargoSeleccionado = null;
            modificarInfoRegistroCargos(cargos.size());
            context.update("form:dlgCargos");
            context.execute("dlgCargos.show()");
        } else if (dlg == 2) {
            activarLOV = false;
            tiposTrabajadorJefeSeleccionado = null;
            modificarInfoRegistroJefe(actualesTiposTrabajadores.size());
            context.update("form:dialogoEmpleadoJefe");
            context.execute("dialogoEmpleadoJefe.show()");
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
        tipoActualizacion = 0;
    }

    public void asignarVariableMotivoCargoNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        motivoSeleccionado = null;
        modificarInfoRegistroMotivos(motivosCambiosCargos.size());
        RequestContext.getCurrentInstance().update("form:dlgMotivos");
        RequestContext.getCurrentInstance().execute("dlgMotivos.show()");
    }

    public void asignarVariableCargoNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        cargoSeleccionado = null;
        modificarInfoRegistroCargos(cargos.size());
        RequestContext.getCurrentInstance().update("form:dlgCargos");
        RequestContext.getCurrentInstance().execute("dlgCargos.show()");
    }

    public void asignarVariableEmpleadoJefeNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        tiposTrabajadorJefeSeleccionado = null;
        modificarInfoRegistroJefe(actualesTiposTrabajadores.size());
        RequestContext.getCurrentInstance().update("form:dialogoEmpleadoJefe");
        RequestContext.getCurrentInstance().execute("dialogoEmpleadoJefe.show()");
    }

    public void cancelarModificacion() {
        cerrarFiltrado();
        activarLOV = true;
        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        k = 0;
        vigenciasCargosEmpleado = null;
        vigenciaSeleccionada = null;
        guardado = true;
        permitirIndex = true;
        getVigenciasCargosEmpleado();
        if (vigenciasCargosEmpleado != null) {
            modificarInfoRegistro(vigenciasCargosEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVCEmpleado");
        context.update("form:ACEPTAR");
        context.update("form:informacionRegistro");
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void salir() {
        cerrarFiltrado();
        activarLOV = true;
        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        vigenciaSeleccionada = null;
        k = 0;
        vigenciasCargosEmpleado = null;
        guardado = true;
    }

    private void cerrarFiltrado() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
            vcFecha.setFilterStyle("display: none; visibility: hidden;");
            vcEstructura = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcEstructura");
            vcEstructura.setFilterStyle("display: none; visibility: hidden;");
            vcMotivo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcMotivo");
            vcMotivo.setFilterStyle("display: none; visibility: hidden;");
            vcNombreCargo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreCargo");
            vcNombreCargo.setFilterStyle("display: none; visibility: hidden;");
            vcCentrosC = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcCentrosC");
            vcCentrosC.setFilterStyle("display: none; visibility: hidden;");
            vcNombreJefe = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreJefe");
            vcNombreJefe.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "292";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filterVC = null;
            tipoLista = 0;
        }
    }

    /*
     * Metodo encargado de accionar un dialogo especifico
     */
    public void infoDialog() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("datosDialog.show()");
    }
    /*
     * metodo encargado de cambiar de pagina xhtml
     */

    /*
     * public String navega() { //Pruebas //vigenciasCargos //tablaPrueba
     * //pruebaRichFaces //Integracion //Gerencial return "Pruebas.xhtml"; }
     */
    public void fechaSeleccionada(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date nuevaFecha = (Date) event.getObject();
    }

    public void estadoLista() {
        //System.out.println("Lista Mostrada: ");
        //System.out.println("___________________________________________________________________________________________________________");
        for (int i = 0; i < vigenciasCargosEmpleado.size(); i++) {
            Date fecha = vigenciasCargosEmpleado.get(i).getFechavigencia();
            String forFecha = formatoFecha.format(fecha);
            System.out.println(forFecha + " | "
                    + vigenciasCargosEmpleado.get(i).getEstructura().getNombre() + " | "
                    + vigenciasCargosEmpleado.get(i).getMotivocambiocargo().getNombre() + " | "
                    + vigenciasCargosEmpleado.get(i).getCargo().getNombre());
            System.out.println("--------------------------------------------------------------------------------------------------------");
        }
    }

    /*
     * public void unclick() { System.out.println("Un solo Click"); }
     *
     * public void dobleclick() { System.out.println("Doble Click"); }
     */
    // METODOS DEL TOOLBAR
    public void modificarVC(VigenciasCargos vCargos, String confirmarCambio, String valor) {
        vigenciaSeleccionada = vCargos;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            activarLOV = true;
            int control = 0;
            for (int i = 0; i < vigenciasCargosEmpleado.size(); i++) {
                if ((vigenciasCargosEmpleado.get(i).getFechavigencia().compareTo(vigenciaSeleccionada.getFechavigencia()) == 0)
                        && (!vigenciasCargosEmpleado.get(i).getSecuencia().equals(vigenciaSeleccionada.getSecuencia()))) {
                    control++;
                    vigenciaSeleccionada.setFechavigencia(fechaVigenciaBck);
                }
            }

            if (control == 0) {
                if (!listVCCrear.contains(vigenciaSeleccionada)) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciaSeleccionada);
                    } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                        listVCModificar.add(vigenciaSeleccionada);
                    }
                }

                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            } else {
                context.execute("validacionFechaDuplicada.show();");
                context.execute("form:datosVCEmpleado");
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            activarLOV = false;
            vigenciaSeleccionada.getEstructura().setNombre(nombreEstructura);


            for (int i = 0; i < dlgEstructuras.size(); i++) {
                if (dlgEstructuras.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSeleccionada.setEstructura(dlgEstructuras.get(indiceUnicoElemento));

                dlgEstructuras.clear();
                getDlgEstructuras();
            } else {
                permitirIndex = false;
                context.update("form:dlgEstructuras");
                context.execute("dlgEstructuras.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOC")) {
            activarLOV = false;
            vigenciaSeleccionada.getMotivocambiocargo().setNombre(motivoCambioC);

            for (int i = 0; i < motivosCambiosCargos.size(); i++) {
                if (motivosCambiosCargos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSeleccionada.setMotivocambiocargo(motivosCambiosCargos.get(indiceUnicoElemento));

                motivosCambiosCargos.clear();
                getMotivosCambiosCargos();
            } else {
                permitirIndex = false;
                context.update("form:dlgMotivos");
                context.execute("dlgMotivos.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("NOMBREC")) {
            activarLOV = false;
            vigenciaSeleccionada.getCargo().setNombre(nombreCargo);

            for (int i = 0; i < cargos.size(); i++) {
                if (cargos.get(i).getNombre().startsWith(valor.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                vigenciaSeleccionada.setCargo(cargos.get(indiceUnicoElemento));

                cargos.clear();
                getCargos();
            } else {
                permitirIndex = false;
                context.update("form:dlgCargos");
                context.execute("dlgCargos.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("EMPLEADOJ")) {
            activarLOV = false;
            if (!valor.isEmpty()) {
                vigenciaSeleccionada.getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);

                for (int i = 0; i < actualesTiposTrabajadores.size(); i++) {
                    if (actualesTiposTrabajadores.get(i).getNombreCompleto().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    vigenciaSeleccionada.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(actualesTiposTrabajadores.get(indiceUnicoElemento).getRfEmpleado()));

                    actualesTiposTrabajadores.clear();
                    getActualesTiposTrabajadores();
                } else {
                    permitirIndex = false;
                    context.update("form:dialogoEmpleadoJefe");
                    context.execute("dialogoEmpleadoJefe.show()");
                    context.update("form:datosVCEmpleado");
                    tipoActualizacion = 0;
                }
            } else {
                vigenciaSeleccionada.getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                vigenciaSeleccionada.setEmpleadojefe(new Empleados());

                coincidencias = 1;
            }
        }
        if (coincidencias == 1) {
            if (!listVCCrear.contains(vigenciaSeleccionada)) {

                if (listVCModificar.isEmpty()) {
                    listVCModificar.add(vigenciaSeleccionada);
                } else if (!listVCModificar.contains(vigenciaSeleccionada)) {
                    listVCModificar.add(vigenciaSeleccionada);
                }
            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVCEmpleado");
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("ESTRUCTURA")) {
            if (tipoNuevo == 1) {
                nombreEstructura = nuevaVigencia.getEstructura().getNombre();
            } else if (tipoNuevo == 2) {
                nombreEstructura = duplicarVC.getEstructura().getNombre();
            }
        } else if (Campo.equals("MOTIVOC")) {
            if (tipoNuevo == 1) {
                motivoCambioC = nuevaVigencia.getMotivocambiocargo().getNombre();
            } else if (tipoNuevo == 2) {
                motivoCambioC = duplicarVC.getMotivocambiocargo().getNombre();
            }
        } else if (Campo.equals("CARGO")) {
            if (tipoNuevo == 1) {
                nombreCargo = nuevaVigencia.getCargo().getNombre();
            } else if (tipoNuevo == 2) {
                nombreCargo = duplicarVC.getCargo().getNombre();
            }
        } else if (Campo.equals("EMPLEADOJ")) {
            if (tipoNuevo == 1) {
                nombreCompleto = nuevaVigencia.getEmpleadojefe().getPersona().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                nombreCompleto = duplicarVC.getEmpleadojefe().getPersona().getNombreCompleto();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            boolean fechaValida = false;
            String forFecha;
            if (tipoNuevo == 1) {
                nuevaVigencia.getEstructura().setNombre(nombreEstructura);
                if (nuevaVigencia.getFechavigencia() == null) {
                    fechaValida = false;
                } else {
                    forFecha = formatoFecha.format(nuevaVigencia.getFechavigencia());
                    dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                    fechaValida = true;
                }
            } else if (tipoNuevo == 2) {
                duplicarVC.getEstructura().setNombre(nombreEstructura);
                if (duplicarVC.getFechavigencia() == null) {
                    fechaValida = false;
                } else {
                    forFecha = formatoFecha.format(duplicarVC.getFechavigencia());
                    dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                    fechaValida = true;
                }
            }
            if (fechaValida == true) {
                for (int i = 0; i < dlgEstructuras.size(); i++) {
                    if (dlgEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setEstructura(dlgEstructuras.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaEstructura");
                        context.update("formularioDialogos:nuevoCentroC");
                    } else if (tipoNuevo == 2) {
                        duplicarVC.setEstructura(dlgEstructuras.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarEstructura");
                        context.update("formularioDialogos:duplicarCentroC");
                    }
                    dlgEstructuras.clear();
                    getDlgEstructuras();
                } else {
                    context.update("form:dlgEstructuras");
                    context.execute("dlgEstructuras.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaEstructura");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarEstructura");
                    }
                }
            } else {
                context.execute("necesitaFecha.show()");
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaEstructura");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEstructura");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOC")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getMotivocambiocargo().setNombre(motivoCambioC);
            } else if (tipoNuevo == 2) {
                duplicarVC.getMotivocambiocargo().setNombre(motivoCambioC);
            }
            for (int i = 0; i < motivosCambiosCargos.size(); i++) {
                if (motivosCambiosCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setMotivocambiocargo(motivosCambiosCargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoMotivo");
                } else if (tipoNuevo == 2) {
                    duplicarVC.setMotivocambiocargo(motivosCambiosCargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMotivo");
                }
                motivosCambiosCargos.clear();
                getMotivosCambiosCargos();
            } else {
                context.update("form:dlgMotivos");
                context.execute("dlgMotivos.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoMotivo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarMotivo");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("NOMBREC")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getCargo().setNombre(nombreCargo);
            } else if (tipoNuevo == 2) {
                duplicarVC.getCargo().setNombre(nombreCargo);
            }
            for (int i = 0; i < cargos.size(); i++) {
                if (cargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setCargo(cargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoNombreCargo");
                } else if (tipoNuevo == 2) {
                    duplicarVC.setCargo(cargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarNombreCargo");
                }
                cargos.clear();
                getCargos();
            } else {
                context.update("form:dlgCargos");
                context.execute("dlgCargos.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoNombreCargo");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarNombreCargo");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("EMPLEADOJ")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                } else if (tipoNuevo == 2) {
                    duplicarVC.getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                }
                for (int i = 0; i < actualesTiposTrabajadores.size(); i++) {
                    if (actualesTiposTrabajadores.get(indiceUnicoElemento).getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(actualesTiposTrabajadores.get(indiceUnicoElemento).getRfEmpleado()));
                        context.update("formularioDialogos:nuevoJefe");
                    } else if (tipoNuevo == 2) {
                        duplicarVC.setEmpleadojefe(administrarVigenciasCargos.buscarEmpleado(actualesTiposTrabajadores.get(indiceUnicoElemento).getRfEmpleado()));
                        context.update("formularioDialogos:duplicarJefe");
                    }
                    actualesTiposTrabajadores.clear();
                    getActualesTiposTrabajadores();
                } else {
                    context.update("form:dialogoEmpleadoJefe");
                    context.execute("dialogoEmpleadoJefe.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevoJefe");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarJefe");
                    }
                }
            } else {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setEmpleadojefe(new Empleados());
                    context.update("formularioDialogos:nuevoJefe");
                } else if (tipoNuevo == 2) {
                    duplicarVC.setEmpleadojefe(new Empleados());
                    context.update("formularioDialogos:duplicarJefe");
                }
            }
        }
    }

    public void guardarCambiosVC() {
        if (guardado == false) {
            //System.out.println("Realizando Operaciones");
            if (!listVCBorrar.isEmpty()) {
                for (int i = 0; i < listVCBorrar.size(); i++) {
                    //System.out.println("Borrando...");
                    if (listVCBorrar.get(i).getEmpleadojefe() != null && listVCBorrar.get(i).getEmpleadojefe().getSecuencia() == null) {
                        //System.out.println("Entro y lo volvio nulo.");
                        listVCBorrar.get(i).setEmpleadojefe(null);
                        administrarVigenciasCargos.borrarVC(listVCBorrar.get(i));
                    } else {
                        administrarVigenciasCargos.borrarVC(listVCBorrar.get(i));
                    }
                }
                listVCBorrar.clear();
            }
            if (!listVCCrear.isEmpty()) {
                for (int i = 0; i < listVCCrear.size(); i++) {
                    //System.out.println("Creando...");
                    if (listVCCrear.get(i).getEmpleadojefe() != null && listVCCrear.get(i).getEmpleadojefe().getSecuencia() == null) {
                        listVCCrear.get(i).setEmpleadojefe(null);
                        administrarVigenciasCargos.crearVC(listVCCrear.get(i));
                    } else {
                        administrarVigenciasCargos.crearVC(listVCCrear.get(i));
                    }
                }
                listVCCrear.clear();
            }
            if (!listVCModificar.isEmpty()) {
                administrarVigenciasCargos.modificarVC(listVCModificar);
                listVCModificar.clear();
            }
            //System.out.println("Se guardaron los datos con exito");
            vigenciasCargosEmpleado = null;
            activarLOV = true;
            getVigenciasCargosEmpleado();
            if (vigenciasCargosEmpleado != null) {
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
                modificarInfoRegistro(vigenciasCargosEmpleado.size());
            } else {
                modificarInfoRegistro(0);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            RequestContext.getCurrentInstance().update("form:listaValores");
            guardado = true;
            context.update("form:ACEPTAR");
            context.update("form:informacionRegistro");
            k = 0;
            permitirIndex = true;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void cambiarIndice(VigenciasCargos vCargos, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            vigenciaSeleccionada = vCargos;
            cualCelda = celda;
            if (cualCelda == 0) {
                activarLOV = true;
                fechaVigenciaBck = vigenciaSeleccionada.getFechavigencia();

            } else if (cualCelda == 1) {
                activarLOV = false;
                nombreEstructura = vigenciaSeleccionada.getEstructura().getNombre();

            } else if (cualCelda == 2) {
                activarLOV = false;
                motivoCambioC = vigenciaSeleccionada.getMotivocambiocargo().getNombre();

            } else if (cualCelda == 3) {
                activarLOV = false;
                nombreCargo = vigenciaSeleccionada.getCargo().getNombre();

            } else if (cualCelda == 4) {
                activarLOV = true;

            } else if (cualCelda == 5) {
                activarLOV = false;
                nombreCompleto = vigenciaSeleccionada.getEmpleadojefe().getPersona().getNombreCompleto();
            }
        } else {
            context.execute("datosVCEmpleado.selectRow(" + vCargos + ", false); datosVCEmpleado.unselectAllRows()");
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    //CREAR VC
    public void agregarNuevaVC() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();

        vigenciaSeleccionada = null;
        if (nuevaVigencia.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha \n";
            pasa++;
        }
        if (nuevaVigencia.getEstructura().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Estructura \n";
            pasa++;
        }
        if (nuevaVigencia.getMotivocambiocargo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Motivo del cambio del cargo \n";
            pasa++;
        }
        if (nuevaVigencia.getCargo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " *Cargo";
            pasa++;
        }
        if (pasa == 0) {
            int control = 0;

            for (VigenciasCargos curVigenciasCargosEmpleado : vigenciasCargosEmpleado) {
                if (curVigenciasCargosEmpleado.getFechavigencia().compareTo(nuevaVigencia.getFechavigencia()) == 0) {
                    control++;
                }
            }

            if (control == 0) {
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
                    vcFecha.setFilterStyle("display: none; visibility: hidden;");
                    vcEstructura = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcEstructura");
                    vcEstructura.setFilterStyle("display: none; visibility: hidden;");
                    vcMotivo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcMotivo");
                    vcMotivo.setFilterStyle("display: none; visibility: hidden;");
                    vcNombreCargo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreCargo");
                    vcNombreCargo.setFilterStyle("display: none; visibility: hidden;");
                    vcCentrosC = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcCentrosC");
                    vcCentrosC.setFilterStyle("display: none; visibility: hidden;");
                    vcNombreJefe = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreJefe");
                    vcNombreJefe.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "292";
                    RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
                    bandera = 0;
                    filterVC = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(l);
                nuevaVigencia.setEmpleado(empleado);
                if (nuevaVigencia.getEmpleadojefe() != null && nuevaVigencia.getEmpleadojefe().getSecuencia() == null) {
                    nuevaVigencia.setEmpleadojefe(null);
                }
                listVCCrear.add(nuevaVigencia);
                vigenciasCargosEmpleado.add(nuevaVigencia);
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(vigenciasCargosEmpleado.indexOf(nuevaVigencia));
                activarLOV = true;
                nuevaVigencia = new VigenciasCargos();
                nuevaVigencia.setEstructura(new Estructuras());
                nuevaVigencia.setMotivocambiocargo(new MotivosCambiosCargos());
                nuevaVigencia.setCargo(new Cargos());
                modificarInfoRegistro(vigenciasCargosEmpleado.size());
                RequestContext.getCurrentInstance().update("form:informacionRegistro");
                RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
                RequestContext.getCurrentInstance().update("form:listaValores");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                RequestContext.getCurrentInstance().execute("NuevoRegistroVC.hide()");
            } else {
                RequestContext.getCurrentInstance().execute("validacionFechaDuplicada.show();");
            }
        } else {
            RequestContext.getCurrentInstance().update("form:validacioNuevaVigencia");
            RequestContext.getCurrentInstance().execute("validacioNuevaVigencia.show()");
        }
    }
    //EDITAR DIALOGOS

    public void cambioEditable() {
        cambioEditor = true;
        //System.out.println("Estado del cambio : " + cambioEditor);
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            editarVC = vigenciaSeleccionada;

            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarEstructura");
                context.execute("editarEstructura.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarMotivo");
                context.execute("editarMotivo.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarNombreCargo");
                context.execute("editarNombreCargo.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarCentroCosto");
                context.execute("editarCentroCosto.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarEmpleadoJefe");
                context.execute("editarEmpleadoJefe.show()");
                cualCelda = -1;
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void reiniciarEditar() {
        editarVC = new VigenciasCargos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
    }

    //BORRAR VC
    public void borrarVC() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            if (!listVCModificar.isEmpty() && listVCModificar.contains(vigenciaSeleccionada)) {
                int modIndex = listVCModificar.indexOf(vigenciaSeleccionada);
                listVCModificar.remove(modIndex);
                listVCBorrar.add(vigenciaSeleccionada);
            } else if (!listVCCrear.isEmpty() && listVCCrear.contains(vigenciaSeleccionada)) {
                int crearIndex = listVCCrear.indexOf(vigenciaSeleccionada);
                listVCCrear.remove(crearIndex);
            } else {
                listVCBorrar.add(vigenciaSeleccionada);
            }
            vigenciasCargosEmpleado.remove(vigenciaSeleccionada);
            if (tipoLista == 1) {
                filterVC.remove(vigenciaSeleccionada);
            }

            modificarInfoRegistro(vigenciasCargosEmpleado.size());
            vigenciaSeleccionada = null;
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");

            context.update("form:datosVCEmpleado");
            context.update("form:informacionRegistro");

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }
//DUPLICAR VC

    public void duplicarVigenciaC() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            duplicarVC = new VigenciasCargos();
            k++;
            l = BigInteger.valueOf(k);

            duplicarVC.setSecuencia(l);
            duplicarVC.setFechavigencia(vigenciaSeleccionada.getFechavigencia());
            duplicarVC.setEstructura(vigenciaSeleccionada.getEstructura());
            duplicarVC.setMotivocambiocargo(vigenciaSeleccionada.getMotivocambiocargo());
            duplicarVC.setCargo(vigenciaSeleccionada.getCargo());
            duplicarVC.setEmpleadojefe(vigenciaSeleccionada.getEmpleadojefe());
            duplicarVC.setCalificacion(vigenciaSeleccionada.getCalificacion());
            duplicarVC.setEmpleado(vigenciaSeleccionada.getEmpleado());
            duplicarVC.setEscalafon(vigenciaSeleccionada.getEscalafon());
            duplicarVC.setLiquidahe(vigenciaSeleccionada.getLiquidahe());
            duplicarVC.setTurnorotativo(vigenciaSeleccionada.getTurnorotativo());

            context.update("formularioDialogos:duplicarVC");
            context.execute("duplicarRegistroVC.show()");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();

        if (duplicarVC.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha \n";
            pasa++;
        }
        if (duplicarVC.getEstructura().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Estructura \n";
            pasa++;
        }
        if (duplicarVC.getMotivocambiocargo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Motivo del cambio del cargo \n";
            pasa++;
        }
        if (duplicarVC.getCargo().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " *Cargo";
            pasa++;
        }
        if (pasa == 0) {
            int control = 0;
            for (VigenciasCargos curVigenciasCargosEmpleado : vigenciasCargosEmpleado) {
                if (curVigenciasCargosEmpleado.getFechavigencia().compareTo(duplicarVC.getFechavigencia()) == 0) {
                    control++;
                }
            }
            if (control == 0) {
                vigenciasCargosEmpleado.add(duplicarVC);
                listVCCrear.add(duplicarVC);
                modificarInfoRegistro(vigenciasCargosEmpleado.size());
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(vigenciasCargosEmpleado.indexOf(duplicarVC));

                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (bandera == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
                    vcFecha.setFilterStyle("display: none; visibility: hidden;");
                    vcEstructura = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcEstructura");
                    vcEstructura.setFilterStyle("display: none; visibility: hidden;");
                    vcMotivo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcMotivo");
                    vcMotivo.setFilterStyle("display: none; visibility: hidden;");
                    vcNombreCargo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreCargo");
                    vcNombreCargo.setFilterStyle("display: none; visibility: hidden;");
                    vcCentrosC = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcCentrosC");
                    vcCentrosC.setFilterStyle("display: none; visibility: hidden;");
                    vcNombreJefe = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreJefe");
                    vcNombreJefe.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "292";
                    bandera = 0;
                    filterVC = null;
                    tipoLista = 0;
                }
                System.out.println("vigenciaSeleccionada : " + vigenciaSeleccionada);
                duplicarVC = new VigenciasCargos();
                duplicarVC.setEstructura(new Estructuras());
                duplicarVC.setMotivocambiocargo(new MotivosCambiosCargos());
                duplicarVC.setCargo(new Cargos());
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                context.execute("duplicarRegistroVC.hide()");
                context.update("form:datosVCEmpleado");
                System.out.println("Ya paso por la actualizacion de tabla");
                context.update("form:informacionRegistro");
            } else {
                context.execute("validacionFechaDuplicada.show();");
            }
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }

    public void anularLOV() {
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void limpiarduplicarVC() {
        duplicarVC = new VigenciasCargos();
        duplicarVC.setEstructura(new Estructuras());
        duplicarVC.setMotivocambiocargo(new MotivosCambiosCargos());
        duplicarVC.setCargo(new Cargos());
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            if (cualCelda == 1) {
                String forFecha = formatoFecha.format(vigenciaSeleccionada.getFechavigencia());
                dlgEstructuras = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                estructuraSeleccionada = null;
                modificarInfoRegistroEstructuras(dlgEstructuras.size());
                context.update("form:dlgEstructuras");
                context.execute("dlgEstructuras.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                tipoActualizacion = 0;
                motivoSeleccionado = null;
                modificarInfoRegistroMotivos(motivosCambiosCargos.size());
                context.update("form:dlgMotivos");
                context.execute("dlgMotivos.show()");
            } else if (cualCelda == 3) {
                tipoActualizacion = 0;
                cargoSeleccionado = null;
                modificarInfoRegistroCargos(cargos.size());
                context.update("form:dlgCargos");
                context.execute("dlgCargos.show()");
            } else if (cualCelda == 5) {
                tipoActualizacion = 0;
                tiposTrabajadorJefeSeleccionado = null;
                modificarInfoRegistroJefe(actualesTiposTrabajadores.size());
                context.update("form:dialogoEmpleadoJefe");
                context.execute("dialogoEmpleadoJefe.show()");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }
    //EXPORTAR PDF

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.addAuthor("Designer Software Ltda");
        pdf.setPageSize(PageSize.LETTER);
    }

    public void bien() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:editarCentroCosto");
        context.execute("editarCentroCosto.show()");
    }

    public void exportPDF() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) context.getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasCargosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) context.getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasCargosXLS", false, false, "UTF-8", null, null);
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevaVC() {
        nuevaVigencia = new VigenciasCargos();
        nuevaVigencia.setEstructura(new Estructuras());
        nuevaVigencia.setMotivocambiocargo(new MotivosCambiosCargos());
        nuevaVigencia.setCargo(new Cargos());
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            System.out.println("Activar");
            vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
            vcFecha.setFilterStyle("width: 85%");
            vcEstructura = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcEstructura");
            vcEstructura.setFilterStyle("");
            vcMotivo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcMotivo");
            vcMotivo.setFilterStyle("");
            vcNombreCargo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreCargo");
            vcNombreCargo.setFilterStyle("");
            vcCentrosC = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcCentrosC");
            vcCentrosC.setFilterStyle("");
            vcNombreJefe = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreJefe");
            vcNombreJefe.setFilterStyle("");
            altoTabla = "268";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
            vcFecha.setFilterStyle("display: none; visibility: hidden;");
            vcEstructura = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcEstructura");
            vcEstructura.setFilterStyle("display: none; visibility: hidden;");
            vcMotivo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcMotivo");
            vcMotivo.setFilterStyle("display: none; visibility: hidden;");
            vcNombreCargo = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreCargo");
            vcNombreCargo.setFilterStyle("display: none; visibility: hidden;");
            vcCentrosC = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcCentrosC");
            vcCentrosC.setFilterStyle("display: none; visibility: hidden;");
            vcNombreJefe = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcNombreJefe");
            vcNombreJefe.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "292";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filterVC = null;
            tipoLista = 0;
        }
        cualCelda = -1;
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(vigenciaSeleccionada.getSecuencia(), "VIGENCIASCARGOS");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCARGOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        activarLOV = true;
        vigenciaSeleccionada = null;
        modificarInfoRegistro(filterVC.size());
        RequestContext.getCurrentInstance().update("form:listaValores");
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    public void eventoFiltrarCargos() {
        modificarInfoRegistroCargos(filterCargos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroCargos");
    }

    public void eventoFiltrarEstructuras() {
        modificarInfoRegistroEstructuras(filterEstructuras.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroEstructuras");
    }

    public void eventoFiltrarJefe() {
        modificarInfoRegistroJefe(filtradoActualesTiposTrabajadores.size());
        RequestContext.getCurrentInstance().update("form:informacionLOVEJ");
    }

    public void eventoFiltrarMotios() {
        modificarInfoRegistroMotivos(filterMotivos.size());
        RequestContext.getCurrentInstance().update("form:infoRegistroMotivos");
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    private void modificarInfoRegistroCargos(int valor) {
        infoRegistroCargos = String.valueOf(valor);
    }

    private void modificarInfoRegistroEstructuras(int valor) {
        infoRegistroEstructuras = String.valueOf(valor);
    }

    private void modificarInfoRegistroJefe(int valor) {
        infoRegistroJefe = String.valueOf(valor);
    }

    private void modificarInfoRegistroMotivos(int valor) {
        infoRegistroMotivos = String.valueOf(valor);
    }

    public void recordarSeleccion() {
        if (vigenciaSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosVCEmpleado");
            tablaC.setSelection(vigenciaSeleccionada);
        }
    }

    //------------------------------------------------------------------------------------------
    //METODOS GETTER'S AND SETTER'S
    //------------------------------------------------------------------------------------------
    //VIGENCIAS CARGOS
    //VigenciasCargosEmpleado---------------------------
    public List<VigenciasCargos> getVigenciasCargosEmpleado() {
        try {
            if (vigenciasCargosEmpleado == null) {
                vigenciasCargosEmpleado = administrarVigenciasCargos.vigenciasEmpleado(empleado.getSecuencia());
            }
            return vigenciasCargosEmpleado;
        } catch (Exception e) {
            //System.out.println("Error....................!!!!!!!!!!!! getVigenciasCargosEmpleado ");
            return null;
        }
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public List<VigenciasCargos> getFilterVC() {
        return filterVC;
    }

    public void setFilterVC(List<VigenciasCargos> filterVC) {
        this.filterVC = filterVC;
    }

    public void setVigenciasCargosEmpleado(List<VigenciasCargos> vigenciasCargosEmpleado) {
        this.vigenciasCargosEmpleado = vigenciasCargosEmpleado;
    }
    //FechaVigencia--------------------------------------

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    //VigenciaSeleccionada--------------------------------

    public VigenciasCargos getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasCargos vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    //dlgEstructuras-----------------------------------------
    public List<Estructuras> getDlgEstructuras() {
        if (dlgEstructuras == null) {
            dlgEstructuras = administrarEstructuras.consultarTodoEstructuras();
        }
        return dlgEstructuras;
    }

    public void setDlgEstructuras(List<Estructuras> dlgEstructuras) {
        this.dlgEstructuras = dlgEstructuras;
    }
    //Estructurasfilter--------------------------------------

    public List<Estructuras> getFilterEstructuras() {
        return filterEstructuras;
    }

    public void setFilterEstructuras(List<Estructuras> filterEstructuras) {
        this.filterEstructuras = filterEstructuras;
    }

    //EstructuraSeleccionada---------------------------------
    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }
    //MOTIVOS
    //MotivosCambiosCargos---------------------------------

    public List<MotivosCambiosCargos> getMotivosCambiosCargos() {
        if (motivosCambiosCargos == null) {
            motivosCambiosCargos = administrarMotivosCambiosCargos.consultarMotivosCambiosCargos();
        }
        return motivosCambiosCargos;
    }

    public void setMotivosCambiosCargos(List<MotivosCambiosCargos> motivosCambiosCargos) {
        this.motivosCambiosCargos = motivosCambiosCargos;
    }

    public String getInfoRegistroMotivos() {
        return infoRegistroMotivos;
    }

    //FilterMotivos---------------------------------------
    public List<MotivosCambiosCargos> getFilterMotivos() {
        return filterMotivos;
    }

    public void setFilterMotivos(List<MotivosCambiosCargos> filterMotivos) {
        this.filterMotivos = filterMotivos;
    }
    //MotivoSeleccionado-----------------------------------

    public MotivosCambiosCargos getMotivoSeleccionado() {
        return motivoSeleccionado;
    }

    public void setMotivoSeleccionado(MotivosCambiosCargos motivoSeleccionado) {
        this.motivoSeleccionado = motivoSeleccionado;
    }
    //MotivoConverter--------------------------------------

    public MotivosCambiosCargosConverter getMotivoConverter() {
        motivoConverter.setMo(getMotivosCambiosCargos());
        return motivoConverter;
    }

    public void setMotivoConverter(MotivosCambiosCargosConverter motivoConverter) {
        this.motivoConverter = motivoConverter;
    }
    //CARGOS
    //Cargos------------------------------------------------

    public List<Cargos> getCargos() {
        if (cargos == null) {
            cargos = administrarEstructuras.consultarTodoCargos();
        }
        return cargos;
    }

    public void setCargos(List<Cargos> cargos) {
        this.cargos = cargos;
    }

    public String getInfoRegistroCargos() {
        return infoRegistroCargos;
    }

    //CargosFilter------------------------------------------
    public List<Cargos> getFilterCargos() {
        return filterCargos;
    }

    public void setFilterCargos(List<Cargos> cargosFilter) {
        this.filterCargos = cargosFilter;
    }
    //CargoSeleccionado-------------------------------------

    public Cargos getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargos cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }
    //OTROS
    //Aceptar---------------------------------------------

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    //Nueva Vigencia Cargo
    public VigenciasCargos getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasCargos nuevaVigencia) {
        this.nuevaVigencia = nuevaVigencia;
    }

    //editar VC celda
    public VigenciasCargos getEditarVC() {
        return editarVC;
    }

    public void setEditarVC(VigenciasCargos editarVC) {
        this.editarVC = editarVC;
    }

    // guardado VC
    public boolean isGuardado() {
        return guardado;
    }

    //DUPLICAR
    public VigenciasCargos getDuplicarVC() {
        return duplicarVC;
    }

    public void setDuplicarVC(VigenciasCargos duplicarVC) {
        this.duplicarVC = duplicarVC;
    }

    public List<VwTiposEmpleados> getActualesTiposTrabajadores() {
        if (actualesTiposTrabajadores == null) {
            actualesTiposTrabajadores = administrarVigenciasCargos.FiltrarTipoTrabajador();
        }
        return actualesTiposTrabajadores;
    }

    public List<VwTiposEmpleados> getFiltradoActualesTiposTrabajadores() {
        return filtradoActualesTiposTrabajadores;
    }

    public void setFiltradoActualesTiposTrabajadores(List<VwTiposEmpleados> filtradoVWActualesTiposTrabajadoresesLista) {
        this.filtradoActualesTiposTrabajadores = filtradoVWActualesTiposTrabajadoresesLista;
    }

    public VwTiposEmpleados getTiposTrabajadorJefeSeleccionado() {
        return tiposTrabajadorJefeSeleccionado;
    }

    public void setTiposTrabajadorJefeSeleccionado(VwTiposEmpleados tiposTrabajadorJefeSeleccionado) {
        this.tiposTrabajadorJefeSeleccionado = tiposTrabajadorJefeSeleccionado;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public boolean isBotonPrimero() {
        return botonPrimero;
    }

    public boolean isBotonAnterior() {
        return botonAnterior;
    }

    public boolean isBotonSiguiente() {
        return botonSiguiente;
    }

    public boolean isBotonUltimo() {
        return botonUltimo;
    }

    public String getRegistroFoco() {
        return registroFoco;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public String getInfoRegistroJefe() {
        return infoRegistroJefe;
    }

    public String getInfoRegistroEstructuras() {
        return infoRegistroEstructuras;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }
}

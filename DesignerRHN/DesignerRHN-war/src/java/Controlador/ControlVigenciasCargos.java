package Controlador;

import Exportar.ExportarXLS;
import Exportar.ExportarPDF;
import Convertidores.MotivosCambiosCargosConverter;
import Entidades.*;
import InterfaceAdministrar.*;
import com.lowagie.text.*;
import java.io.IOException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
    private List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresesLista;
    private List<VWActualesTiposTrabajadores> filtradoVWActualesTiposTrabajadoresesLista;
    private VWActualesTiposTrabajadores seleccionVWActualesTiposTrabajadoreses;
    private Date fechaVigencia;
    private BigInteger secuencia;
    private Empleados empleado;
    private int tipoActualizacion;//Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vcFecha, vcEstructura, vcMotivo, vcNombreCargo, vcCentrosC, vcNombreJefe;
    //Estructuras
    private List<Estructuras> estructuras;
    private List<Estructuras> estructurasLOV;
    private List<Estructuras> estructurasfilter;
    private Estructuras estructuraSeleccionada;
    //Motivos
    private List<MotivosCambiosCargos> motivosCambiosCargos;
    private List<MotivosCambiosCargos> filterMotivos;
    private MotivosCambiosCargos motivoSeleccionado;
    private MotivosCambiosCargosConverter motivoConverter;
    //Cargos
    private List<Cargos> cargos;
    private List<Cargos> cargosFilter;
    private Cargos cargoSeleccionado;
    //Otros
    private boolean aceptar;
    private int index;
    private SimpleDateFormat formatoFecha;
    //Pruebas para modificar
    private List<VigenciasCargos> listVCModificar;
    private VigenciasCargos vC;
    private String a;
    private boolean guardado;
    //crear VC
    public VigenciasCargos nuevaVigencia;
    private Integer max;
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
    //RASTRO
    private BigInteger secRegistro;
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
    private String cantidadRegistrosLOV;
    //------------------------------------------------------------------------------------------
    //CONSTRUCTOR(ES)
    //------------------------------------------------------------------------------------------

    public ControlVigenciasCargos() {
        System.out.println("Se creo un nuevo BakingBean YUPI!");

        empleado = new Empleados();
        bandera = 0;
        //Vigencias Cargos
        vigenciasCargosEmpleado = null;
        fechaVigencia = new Date();
        //Estructuras
        estructurasLOV = new ArrayList<Estructuras>();
        //Motivos
        motivosCambiosCargos = new ArrayList<MotivosCambiosCargos>();
        motivoSeleccionado = null;
        motivoConverter = new MotivosCambiosCargosConverter();
        //Cargos
        cargos = new ArrayList<Cargos>();
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
        vwActualesTiposTrabajadoresesLista = new ArrayList<VWActualesTiposTrabajadores>();
        //AUTOCOMPLETAR
        permitirIndex = true;
        //RASTRO
        secRegistro = null;
        //INICIALIZAR FOCO PARA EL PRIMER REGISTRO
        registroFoco = "form:datosVCEmpleado:editFecha";
        altoTabla = "270";

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasCargos.obtenerConexion(ses.getId());
            administrarMotivosCambiosCargos.obtenerConexion(ses.getId());
            administrarEstructuras.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
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
                vigenciasCargosEmpleado = administrarVigenciasCargos.vigenciasEmpleado(secuencia);
            }
            return vigenciasCargosEmpleado;
        } catch (Exception e) {
            System.out.println("Error....................!!!!!!!!!!!! getVigenciasCargosEmpleado ");
            return null;
        }

    }

    public Empleados getEmpleado() {
        try {
            empleado = administrarVigenciasCargos.buscarEmpleado(secuencia);
            //empleado = administrarVigenciasCargos.buscarEmpleado(BigInteger.valueOf(10661039));
        } catch (Exception e) {
            System.out.println("Falló ControlVigenciasCargos.getEmpleado.");
        }
        return empleado;
    }

    public BigInteger getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigInteger secuencia) {
        this.secuencia = secuencia;
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
    //ESTRUCTURAS
    //Estructuras--------------------------------------------

    public List<Estructuras> getEstructuras() {
        if (estructuras == null) {
            return estructuras = administrarEstructuras.consultarTodoEstructuras();
        } else {
            return estructuras;
        }
    }

    public void setEstructuras(List<Estructuras> estructuras) {
        this.estructuras = estructuras;
    }
    //EstructurasLOV-----------------------------------------

    public List<Estructuras> getEstructurasLOV() {
        return estructurasLOV = administrarEstructuras.consultarTodoEstructuras();

    }

    public void setEstructurasLOV(List<Estructuras> estructurasLOV) {
        this.estructurasLOV = estructurasLOV;
    }
    //Estructurasfilter--------------------------------------

    public List<Estructuras> getEstructurasfilter() {
        return estructurasfilter;
    }

    public void setEstructurasfilter(List<Estructuras> estructurasfilter) {
        this.estructurasfilter = estructurasfilter;
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
        return motivosCambiosCargos = administrarMotivosCambiosCargos.consultarMotivosCambiosCargos();
    }

    public void setMotivosCambiosCargos(List<MotivosCambiosCargos> motivosCambiosCargos) {
        this.motivosCambiosCargos = motivosCambiosCargos;
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
        return cargos = administrarEstructuras.consultarTodoCargos();
    }

    public void setCargos(List<Cargos> cargos) {
        this.cargos = cargos;
    }
    //CargosFilter------------------------------------------

    public List<Cargos> getCargosFilter() {
        return cargosFilter;
    }

    public void setCargosFilter(List<Cargos> cargosFilter) {
        this.cargosFilter = cargosFilter;
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

    public List<VWActualesTiposTrabajadores> getVwActualesTiposTrabajadoresesLista() {
        vwActualesTiposTrabajadoresesLista = administrarVigenciasCargos.FiltrarTipoTrabajador();
        return vwActualesTiposTrabajadoresesLista;
    }

    public List<VWActualesTiposTrabajadores> getFiltradoVWActualesTiposTrabajadoresesLista() {
        return filtradoVWActualesTiposTrabajadoresesLista;
    }

    public void setFiltradoVWActualesTiposTrabajadoresesLista(List<VWActualesTiposTrabajadores> filtradoVWActualesTiposTrabajadoresesLista) {
        this.filtradoVWActualesTiposTrabajadoresesLista = filtradoVWActualesTiposTrabajadoresesLista;
    }

    public VWActualesTiposTrabajadores getSeleccionVWActualesTiposTrabajadoreses() {
        return seleccionVWActualesTiposTrabajadoreses;
    }

    public void setSeleccionVWActualesTiposTrabajadoreses(VWActualesTiposTrabajadores seleccionVWActualesTiposTrabajadoreses) {
        this.seleccionVWActualesTiposTrabajadoreses = seleccionVWActualesTiposTrabajadoreses;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
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

    //------------------------------------------------------------------------------------------
    //METODOS DE MANEJO DE INFORMACION
    //------------------------------------------------------------------------------------------
    //VIGENCIASCARGOS----------------------------------------------------
    public void recibirEmpleado(BigInteger sec) {
        vigenciasCargosEmpleado = null;
        secuencia = sec;
        System.out.println(sec);
        getVigenciasCargosEmpleado();
        //INICIALIZAR BOTONES NAVEGACION
        if (vigenciasCargosEmpleado != null && !vigenciasCargosEmpleado.isEmpty()) {
            if (vigenciasCargosEmpleado.size() == 1) {
                /*botonPrimero = true;
                 botonAnterior = true;
                 botonSiguiente = true;
                 botonUltimo = true;*/
                //INFORMACION REGISTRO
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (vigenciasCargosEmpleado.size() > 1) {
                /*botonPrimero = true;
                 botonAnterior = true;
                 botonSiguiente = false;
                 botonUltimo = false;*/
                //INFORMACION REGISTRO
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
            }
        } else {
            /*botonPrimero = true;
             botonAnterior = true;
             botonSiguiente = true;
             botonUltimo = true;*/
            //infoRegistro = "No hay registros";
            infoRegistro = "Cantidad de registros: 0";
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

    public void cargarEstructuras(Date fechaVigencia, Integer indice) {
        String forFecha = formatoFecha.format(fechaVigencia);
        estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
        index = indice;
        System.out.println(indice);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:estructurasDialog");
        context.execute("estructurasDialog.show()");
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
                estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                context.update("form:estructurasDialog");
                context.execute("estructurasDialog.show()");
            }
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            if (duplicarVC.getFechavigencia() == null) {
                context.execute("necesitaFecha.show()");
            } else {
                String forFecha = formatoFecha.format(duplicarVC.getFechavigencia());
                estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                context.update("form:estructurasDialog");
                context.execute("estructurasDialog.show()");
            }
        }
    }
    /*
     * Metodo encargado de actualizar la Estructura de una VigenciaCargo
     */

    public void actualizarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(index).setEstructura(estructuraSeleccionada);

                if (!listVCCrear.contains(vigenciasCargosEmpleado.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(index))) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    }
                }
            } else {
                filterVC.get(index).setEstructura(estructuraSeleccionada);

                if (!listVCCrear.contains(filterVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filterVC.get(index));
                    } else if (!listVCModificar.contains(filterVC.get(index))) {
                        listVCModificar.add(filterVC.get(index));
                    }
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
        estructurasfilter = null;
        estructuraSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("estructurasDialog.hide()");
        context.reset("form:lOVEstructuras:globalFilter");
        context.update("form:lOVEstructuras");
    }

    public void cancelarCambioEstructura() {
        estructurasfilter = null;
        estructuraSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
    //MOTIVOS----------------------------------------------------------
    /*
     * Metodo encargado de llenar la lista utilizada por el autocompletar
     */

    public List<MotivosCambiosCargos> autocomlpetarMotivo(String in) {
        List<MotivosCambiosCargos> mot = getMotivosCambiosCargos();
        List<MotivosCambiosCargos> rta = new ArrayList<MotivosCambiosCargos>();
        for (MotivosCambiosCargos m : mot) {
            if (m.getNombre().startsWith(in.toUpperCase())) {
                rta.add(m);
            }
        }
        System.out.println("...");
        return rta;
    }
    /*
     * Metodo encargado de actualizar el MotivoCambioCargo de una VigenciaCargo
     */

    public void actualizarMotivo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(index).setMotivocambiocargo(motivoSeleccionado);
                if (!listVCCrear.contains(vigenciasCargosEmpleado.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(index))) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    }
                }
            } else {
                filterVC.get(index).setMotivocambiocargo(motivoSeleccionado);
                if (!listVCCrear.contains(filterVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filterVC.get(index));
                    } else if (!listVCModificar.contains(filterVC.get(index))) {
                        listVCModificar.add(filterVC.get(index));
                    }
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
        motivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("motivosDialog.hide()");
        context.reset("form:motivosCambCargo:globalFilter");
        context.update("form:motivosCambCargo");
    }

    public void cancelarCambioMotivo() {
        filterMotivos = null;
        motivoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
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
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(index).setCargo(cargoSeleccionado);
                if (!listVCCrear.contains(vigenciasCargosEmpleado.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(index))) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    }

                }
            } else {
                filterVC.get(index).setCargo(cargoSeleccionado);
                if (!listVCCrear.contains(filterVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filterVC.get(index));
                    } else if (!listVCModificar.contains(filterVC.get(index))) {
                        listVCModificar.add(filterVC.get(index));
                    }
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
        cargosFilter = null;
        cargoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("cargosDialog.hide()");
        context.reset("form:lOVCargos:globalFilter");
        context.update("form:lOVCargos");
    }

    public void cancelarCambioCargo() {
        cargosFilter = null;
        cargoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarEmpleadoJefe() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(index).setEmpleadojefe(seleccionVWActualesTiposTrabajadoreses.getEmpleado());
                if (!listVCCrear.contains(vigenciasCargosEmpleado.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(index))) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(index));
                    }
                }
            } else {
                filterVC.get(index).setEmpleadojefe(seleccionVWActualesTiposTrabajadoreses.getEmpleado());
                if (!listVCCrear.contains(filterVC.get(index))) {
                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filterVC.get(index));
                    } else if (!listVCModificar.contains(filterVC.get(index))) {
                        listVCModificar.add(filterVC.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVCEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setEmpleadojefe(seleccionVWActualesTiposTrabajadoreses.getEmpleado());
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarVC.setEmpleadojefe(seleccionVWActualesTiposTrabajadoreses.getEmpleado());
            context.update("formularioDialogos:duplicarVC");
        }
        filtradoVWActualesTiposTrabajadoresesLista = null;
        seleccionVWActualesTiposTrabajadoreses = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("dialogoEmpleadoJefe.hide()");
        context.reset("form:lvEmpleadoJefe:globalFilter");
        context.update("form:lvEmpleadoJefe");
    }

    public void cancelarEmpleadoJefe() {
        filtradoVWActualesTiposTrabajadoresesLista = null;
        seleccionVWActualesTiposTrabajadoreses = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
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
        //vigenciasCargosEmpleado.get(index).setFechavigencia(fecha);        
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
     * Metodo encargado de cambiar el valor booleano para habilitar
     * un boton
     */

    public void activarAceptar() {
        aceptar = false;
    }
    /*
     * Metodo encargado de cambiar el valor booleano para deshabilitar
     * un boton
     */

    public void desactivarAceptar() {
        aceptar = true;
    }

    public void asignarIndex(Integer indice, int dlg) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (dlg == 0) {
            tipoActualizacion = 0;
            context.update("form:motivosDialog");
            cantidadRegistrosLOV = "Cantidad de registros: " + motivosCambiosCargos.size();
            context.update("form:informacionLOVM");
            context.execute("motivosDialog.show()");
        } else if (dlg == 1) {
            tipoActualizacion = 0;
            context.update("form:cargosDialog");
            cantidadRegistrosLOV = "Cantidad de registros: " + cargos.size();
            context.update("form:informacionLOVC");
            context.execute("cargosDialog.show()");
        } else if (dlg == 2) {
            tipoActualizacion = 0;
            context.update("form:dialogoEmpleadoJefe");
            cantidadRegistrosLOV = "Cantidad de registros: " + vwActualesTiposTrabajadoresesLista.size();
            context.update("form:informacionLOVEJ");
            context.execute("dialogoEmpleadoJefe.show()");
        }
    }

    public void asignarVariableMotivoCargoNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:motivosDialog");
        context.execute("motivosDialog.show()");
    }

    public void asignarVariableCargoNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:cargosDialog");
        context.execute("cargosDialog.show()");
    }

    public void asignarVariableEmpleadoJefeNuevo(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:dialogoEmpleadoJefe");
        context.execute("dialogoEmpleadoJefe.show()");
    }

    public void cancelarModificacion() {
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
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filterVC = null;
            tipoLista = 0;
        }

        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasCargosEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getVigenciasCargosEmpleado();
        if (vigenciasCargosEmpleado != null && !vigenciasCargosEmpleado.isEmpty()) {
            vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
            infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVCEmpleado");
        context.update("form:ACEPTAR");
        context.update("form:informacionRegistro");
    }

    public void salir() {
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
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filterVC = null;
            tipoLista = 0;
        }

        listVCBorrar.clear();
        listVCCrear.clear();
        listVCModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        vigenciasCargosEmpleado = null;
        guardado = true;

    }

    public void picachu(String a) {
        System.out.println(a);
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

    public String navega() {
        //Pruebas
        //vigenciasCargos
        //tablaPrueba
        //pruebaRichFaces
        //Integracion
        //Gerencial
        return "Pruebas.xhtml";
    }

    public void fechaSeleccionada(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Nueva Fecha: " + format.format(event.getObject()));
        Date nuevaFecha = (Date) event.getObject();
        //vigenciasCargosEmpleado.get(indice).setFechavigencia(nuevaFecha);
    }

    public void estadoLista() {
        System.out.println("Lista Mostrada: ");
        System.out.println("___________________________________________________________________________________________________________");

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

    public void unclick() {
        System.out.println("Un solo Click");
    }

    public void dobleclick() {
        System.out.println("Doble Click");
    }

    // METODOS DEL TOOLBAR
    public void modificarVC(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            int control = 0;
            if (tipoLista == 0) {
                for (int i = 0; i < vigenciasCargosEmpleado.size(); i++) {
                    if (i == indice) {
                        i++;
                        if (i >= vigenciasCargosEmpleado.size()) {
                            break;
                        }
                    }
                    if (vigenciasCargosEmpleado.get(i).getFechavigencia().compareTo(vigenciasCargosEmpleado.get(indice).getFechavigencia()) == 0) {
                        control++;
                        vigenciasCargosEmpleado.get(indice).setFechavigencia(fechaVigenciaBck);
                    }
                }
            } else {
                for (int i = 0; i < filterVC.size(); i++) {
                    if (i == indice) {
                        i++;
                        if (i >= filterVC.size()) {
                            break;
                        }
                    }
                    if (filterVC.get(i).getFechavigencia().compareTo(filterVC.get(indice).getFechavigencia()) == 0) {
                        control++;
                        filterVC.get(indice).setFechavigencia(fechaVigenciaBck);
                    }
                }
            }
            if (control == 0) {
                if (tipoLista == 0) {
                    if (!listVCCrear.contains(vigenciasCargosEmpleado.get(indice))) {

                        if (listVCModificar.isEmpty()) {
                            listVCModificar.add(vigenciasCargosEmpleado.get(indice));
                        } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(indice))) {
                            listVCModificar.add(vigenciasCargosEmpleado.get(indice));
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!listVCCrear.contains(filterVC.get(indice))) {

                        if (listVCModificar.isEmpty()) {
                            listVCModificar.add(filterVC.get(indice));
                        } else if (!listVCModificar.contains(filterVC.get(indice))) {
                            listVCModificar.add(filterVC.get(indice));
                        }
                    }
                    index = -1;
                    secRegistro = null;
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
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(indice).getEstructura().setNombre(nombreEstructura);
            } else {
                filterVC.get(indice).getEstructura().setNombre(nombreEstructura);
            }

            for (int i = 0; i < estructurasLOV.size(); i++) {
                if (estructurasLOV.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasCargosEmpleado.get(indice).setEstructura(estructurasLOV.get(indiceUnicoElemento));
                } else {
                    filterVC.get(indice).setEstructura(estructurasLOV.get(indiceUnicoElemento));
                }
                estructurasLOV.clear();
                getEstructurasLOV();
            } else {
                permitirIndex = false;
                context.update("form:estructurasDialog");
                context.execute("estructurasDialog.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("MOTIVOC")) {
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(indice).getMotivocambiocargo().setNombre(motivoCambioC);
            } else {
                filterVC.get(indice).getMotivocambiocargo().setNombre(motivoCambioC);
            }
            for (int i = 0; i < motivosCambiosCargos.size(); i++) {
                if (motivosCambiosCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasCargosEmpleado.get(indice).setMotivocambiocargo(motivosCambiosCargos.get(indiceUnicoElemento));
                } else {
                    filterVC.get(indice).setMotivocambiocargo(motivosCambiosCargos.get(indiceUnicoElemento));
                }
                motivosCambiosCargos.clear();
                getMotivosCambiosCargos();
            } else {
                permitirIndex = false;
                context.update("form:motivosDialog");
                context.execute("motivosDialog.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("NOMBREC")) {
            if (tipoLista == 0) {
                vigenciasCargosEmpleado.get(indice).getCargo().setNombre(nombreCargo);
            } else {
                filterVC.get(indice).getCargo().setNombre(nombreCargo);
            }
            for (int i = 0; i < cargos.size(); i++) {
                if (cargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    vigenciasCargosEmpleado.get(indice).setCargo(cargos.get(indiceUnicoElemento));
                } else {
                    filterVC.get(indice).setCargo(cargos.get(indiceUnicoElemento));
                }
                cargos.clear();
                getCargos();
            } else {
                permitirIndex = false;
                context.update("form:cargosDialog");
                context.execute("cargosDialog.show()");
                context.update("form:datosVCEmpleado");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("EMPLEADOJ")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoLista == 0) {
                    vigenciasCargosEmpleado.get(indice).getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                } else {
                    filterVC.get(indice).getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                }
                for (int i = 0; i < vwActualesTiposTrabajadoresesLista.size(); i++) {
                    if (vwActualesTiposTrabajadoresesLista.get(i).getEmpleado().getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        vigenciasCargosEmpleado.get(indice).setEmpleadojefe(vwActualesTiposTrabajadoresesLista.get(indiceUnicoElemento).getEmpleado());
                    } else {
                        filterVC.get(indice).setEmpleadojefe(vwActualesTiposTrabajadoresesLista.get(indiceUnicoElemento).getEmpleado());
                    }
                    vwActualesTiposTrabajadoresesLista.clear();
                    getVwActualesTiposTrabajadoresesLista();
                } else {
                    permitirIndex = false;
                    context.update("form:dialogoEmpleadoJefe");
                    context.execute("dialogoEmpleadoJefe.show()");
                    context.update("form:datosVCEmpleado");
                    tipoActualizacion = 0;
                }
            } else {
                if (tipoLista == 0) {
                    vigenciasCargosEmpleado.get(indice).getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                    vigenciasCargosEmpleado.get(indice).setEmpleadojefe(new Empleados());
                } else {
                    filterVC.get(indice).getEmpleadojefe().getPersona().setNombreCompleto(nombreCompleto);
                    filterVC.get(indice).setEmpleadojefe(new Empleados());
                }
                coincidencias = 1;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVCCrear.contains(vigenciasCargosEmpleado.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(indice));
                    } else if (!listVCModificar.contains(vigenciasCargosEmpleado.get(indice))) {
                        listVCModificar.add(vigenciasCargosEmpleado.get(indice));
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVCCrear.contains(filterVC.get(indice))) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(filterVC.get(indice));
                    } else if (!listVCModificar.contains(filterVC.get(indice))) {
                        listVCModificar.add(filterVC.get(indice));
                    }
                }
                index = -1;
                secRegistro = null;
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:datosVCEmpleado");
        }
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
                    estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                    fechaValida = true;
                }
            } else if (tipoNuevo == 2) {
                duplicarVC.getEstructura().setNombre(nombreEstructura);
                if (duplicarVC.getFechavigencia() == null) {
                    fechaValida = false;
                } else {
                    forFecha = formatoFecha.format(duplicarVC.getFechavigencia());
                    estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                    fechaValida = true;
                }
            }
            if (fechaValida == true) {
                for (int i = 0; i < estructurasLOV.size(); i++) {
                    if (estructurasLOV.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setEstructura(estructurasLOV.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaEstructura");
                        context.update("formularioDialogos:nuevoCentroC");
                    } else if (tipoNuevo == 2) {
                        duplicarVC.setEstructura(estructurasLOV.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarEstructura");
                        context.update("formularioDialogos:duplicarCentroC");
                    }
                    estructurasLOV.clear();
                    getEstructurasLOV();
                } else {
                    context.update("form:estructurasDialog");
                    context.execute("estructurasDialog.show()");
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
                context.update("form:motivosDialog");
                context.execute("motivosDialog.show()");
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
                context.update("form:cargosDialog");
                context.execute("cargosDialog.show()");
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
                for (int i = 0; i < vwActualesTiposTrabajadoresesLista.size(); i++) {
                    if (vwActualesTiposTrabajadoresesLista.get(i).getEmpleado().getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setEmpleadojefe(vwActualesTiposTrabajadoresesLista.get(indiceUnicoElemento).getEmpleado());
                        context.update("formularioDialogos:nuevoJefe");
                    } else if (tipoNuevo == 2) {
                        duplicarVC.setEmpleadojefe(vwActualesTiposTrabajadoresesLista.get(indiceUnicoElemento).getEmpleado());
                        context.update("formularioDialogos:duplicarJefe");
                    }
                    vwActualesTiposTrabajadoresesLista.clear();
                    getVwActualesTiposTrabajadoresesLista();
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
            System.out.println("Realizando Operaciones");
            if (!listVCBorrar.isEmpty()) {
                for (int i = 0; i < listVCBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listVCBorrar.get(i).getEmpleadojefe() != null && listVCBorrar.get(i).getEmpleadojefe().getSecuencia() == null) {
                        System.out.println("Entro y lo volvio nulo.");
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
                    System.out.println("Creando...");
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
            System.out.println("Se guardaron los datos con exito");
            vigenciasCargosEmpleado = null;
            getVigenciasCargosEmpleado();
            if (vigenciasCargosEmpleado != null && !vigenciasCargosEmpleado.isEmpty()) {
                vigenciaSeleccionada = vigenciasCargosEmpleado.get(0);
                infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            guardado = true;
            context.update("form:ACEPTAR");
            context.update("form:informacionRegistro");
            k = 0;
            permitirIndex = true;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        secRegistro = null;
    }

    public void cambiarIndice(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = vigenciasCargosEmpleado.get(index).getSecuencia();
            vigenciaSeleccionada = vigenciasCargosEmpleado.get(index);
            //infoRegistro = "Registro " + (index + 1) + " de " + vigenciasCargosEmpleado.size();
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    fechaVigenciaBck = vigenciasCargosEmpleado.get(index).getFechavigencia();
                } else {
                    fechaVigenciaBck = filterVC.get(index).getFechavigencia();
                }
            } else if (cualCelda == 1) {
                if (tipoLista == 0) {
                    nombreEstructura = vigenciasCargosEmpleado.get(index).getEstructura().getNombre();
                } else {
                    nombreEstructura = filterVC.get(index).getEstructura().getNombre();
                }
            } else if (cualCelda == 2) {
                if (tipoLista == 0) {
                    motivoCambioC = vigenciasCargosEmpleado.get(index).getMotivocambiocargo().getNombre();
                } else {
                    motivoCambioC = filterVC.get(index).getMotivocambiocargo().getNombre();
                }
            } else if (cualCelda == 3) {
                if (tipoLista == 0) {
                    nombreCargo = vigenciasCargosEmpleado.get(index).getCargo().getNombre();
                } else {
                    nombreCargo = filterVC.get(index).getCargo().getNombre();
                }
            } else if (cualCelda == 5) {
                if (tipoLista == 0) {
                    nombreCompleto = vigenciasCargosEmpleado.get(index).getEmpleadojefe().getPersona().getNombreCompleto();
                } else {
                    nombreCompleto = filterVC.get(index).getEmpleadojefe().getPersona().getNombreCompleto();
                }
            }
        } else {
            context.execute("datosVCEmpleado.selectRow(" + index + ", false); datosVCEmpleado.unselectAllRows()");
        }
    }

    //PRIMER REGISTRO
    /*public void primerRegistro() {
     if (index >= 0) {
     RequestContext context = RequestContext.getCurrentInstance();
     botonPrimero = true;
     botonAnterior = true;
     registroFoco = "form:datosVCEmpleado:0:editFecha";
     infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
     context.update("form:btnPrimerRegistro");
     context.update("form:btnAnteriorRegistro");
     context.update("form:focoRegistro");
     context.update("form:informacionRegistro");
     }
     }

     public void anteriorRegistro() {
     if (index >= 0) {
     RequestContext context = RequestContext.getCurrentInstance();
     if (index == 1) {
     botonPrimero = true;
     botonAnterior = true;

     }
     int registro;
     registro = index - 1;
     registroFoco = "form:datosVCEmpleado:" + registro + ":editFecha";
     infoRegistro = "Registro " + index + " de " + vigenciasCargosEmpleado.size();
     context.update("form:focoRegistro");
     context.update("form:btnPrimerRegistro");
     context.update("form:btnAnteriorRegistro");
     context.update("form:informacionRegistro");
     }
     }

     public void siguienteRegistro() {
     if (index >= 0) {
     RequestContext context = RequestContext.getCurrentInstance();
     if (index == (vigenciasCargosEmpleado.size() - 2)) {
     botonUltimo = true;
     botonSiguiente = true;

     }
     int registro;
     registro = index + 1;
     registroFoco = "form:datosVCEmpleado:" + registro + ":editFecha";
     int numeroRegistro;
     numeroRegistro = registro + 1;
     infoRegistro = "Registro " + numeroRegistro + " de " + vigenciasCargosEmpleado.size();
     context.update("form:focoRegistro");
     context.update("form:btnSiguienteRegistro");
     context.update("form:btnUltimoRegistro");
     context.update("form:informacionRegistro");
     }
     }

     public void ultimoRegistro() {
     if (index >= 0) {
     RequestContext context = RequestContext.getCurrentInstance();
     //botonUltimo = true;
     ///botonSiguiente = true;
     int registro;
     registro = vigenciasCargosEmpleado.size() - 1;
     registroFoco = "form:datosVCEmpleado:" + registro + ":editFecha";
     infoRegistro = "Registro " + vigenciasCargosEmpleado.size() + " de " + vigenciasCargosEmpleado.size();
     context.update("form:btnSiguienteRegistro");
     context.update("form:btnUltimoRegistro");
     context.update("form:focoRegistro");
     context.update("form:informacionRegistro");
     //context.execute("('" + registroFoco + "').trigger('click'); return false;");
     context.execute("$('form:ATRAS').trigger('click');");

     //context.execute("{PrimeFaces.focus('" + registroFoco + "');}");
     }
     }*/
    //CREAR VC
    public void agregarNuevaVC() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();

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
                    altoTabla = "270";
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
                nuevaVigencia = new VigenciasCargos();
                nuevaVigencia.setEstructura(new Estructuras());
                nuevaVigencia.setMotivocambiocargo(new MotivosCambiosCargos());
                nuevaVigencia.setCargo(new Cargos());
                infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
                context.update("form:informacionRegistro");
                context.update("form:datosVCEmpleado");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.execute("NuevoRegistroVC.hide()");
                index = -1;
                secRegistro = null;
            } else {
                context.execute("validacionFechaDuplicada.show();");
            }
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }
    //EDITAR DIALOGOS

    public void cambioEditable() {
        cambioEditor = true;
        System.out.println("Estado del cambio : " + cambioEditor);
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVC = vigenciasCargosEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVC = filterVC.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
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

        }
        index = -1;
        secRegistro = null;
    }

    public void aplicarCambioCelda() {
        if (index >= 0) {
            if (cambioEditor == true) {
                if (!listVCCrear.contains(editarVC)) {

                    if (listVCModificar.isEmpty()) {
                        listVCModificar.add(editarVC);
                        vigenciasCargosEmpleado.set(index, editarVC);
                    } else if (listVCModificar.contains(editarVC)) {
                        int editarIndex = listVCModificar.indexOf(editarVC);
                        listVCModificar.set(editarIndex, editarVC);
                        vigenciasCargosEmpleado.set(index, editarVC);

                    } else if (!listVCModificar.contains(editarVC)) {
                        listVCModificar.add(editarVC);
                        vigenciasCargosEmpleado.set(index, editarVC);
                    }
                } else if (listVCCrear.contains(editarVC)) {
                    int editarIndex = listVCCrear.indexOf(editarVC);
                    listVCCrear.set(editarIndex, editarVC);
                    vigenciasCargosEmpleado.set(index, editarVC);
                }
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVCEmpleado");
            editarVC = new VigenciasCargos();
            cambioEditor = false;
            index = -1;
            secRegistro = null;
            aceptarEditar = true;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
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

        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoLista == 0) {
                if (!listVCModificar.isEmpty() && listVCModificar.contains(vigenciasCargosEmpleado.get(index))) {
                    int modIndex = listVCModificar.indexOf(vigenciasCargosEmpleado.get(index));
                    listVCModificar.remove(modIndex);
                    listVCBorrar.add(vigenciasCargosEmpleado.get(index));
                } else if (!listVCCrear.isEmpty() && listVCCrear.contains(vigenciasCargosEmpleado.get(index))) {
                    int crearIndex = listVCCrear.indexOf(vigenciasCargosEmpleado.get(index));
                    listVCCrear.remove(crearIndex);
                } else {
                    listVCBorrar.add(vigenciasCargosEmpleado.get(index));
                }
                vigenciasCargosEmpleado.remove(index);
                infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
            }
            if (tipoLista == 1) {
                if (!listVCModificar.isEmpty() && listVCModificar.contains(filterVC.get(index))) {
                    int modIndex = listVCModificar.indexOf(filterVC.get(index));
                    listVCModificar.remove(modIndex);
                    listVCBorrar.add(filterVC.get(index));
                } else if (!listVCCrear.isEmpty() && listVCCrear.contains(filterVC.get(index))) {
                    int crearIndex = listVCCrear.indexOf(filterVC.get(index));
                    listVCCrear.remove(crearIndex);
                } else {
                    listVCBorrar.add(filterVC.get(index));
                }
                int VCIndex = vigenciasCargosEmpleado.indexOf(filterVC.get(index));
                vigenciasCargosEmpleado.remove(VCIndex);
                filterVC.remove(index);
                infoRegistro = "Cantidad de registros: " + filterVC.size();

            }
            context.update("form:datosVCEmpleado");
            context.update("form:informacionRegistro");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }

    }

    //DUPLICAR VC
    public void duplicarVigenciaC() {
        if (index >= 0) {
            duplicarVC = new VigenciasCargos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVC.setSecuencia(l);
                duplicarVC.setFechavigencia(vigenciasCargosEmpleado.get(index).getFechavigencia());
                duplicarVC.setEstructura(vigenciasCargosEmpleado.get(index).getEstructura());
                duplicarVC.setMotivocambiocargo(vigenciasCargosEmpleado.get(index).getMotivocambiocargo());
                duplicarVC.setCargo(vigenciasCargosEmpleado.get(index).getCargo());
                duplicarVC.setEmpleadojefe(vigenciasCargosEmpleado.get(index).getEmpleadojefe());
                duplicarVC.setCalificacion(vigenciasCargosEmpleado.get(index).getCalificacion());
                duplicarVC.setEmpleado(vigenciasCargosEmpleado.get(index).getEmpleado());
                duplicarVC.setEscalafon(vigenciasCargosEmpleado.get(index).getEscalafon());
                duplicarVC.setLiquidahe(vigenciasCargosEmpleado.get(index).getLiquidahe());
                duplicarVC.setTurnorotativo(vigenciasCargosEmpleado.get(index).getTurnorotativo());
            }
            if (tipoLista == 1) {
                duplicarVC.setSecuencia(l);
                duplicarVC.setFechavigencia(filterVC.get(index).getFechavigencia());
                duplicarVC.setEstructura(filterVC.get(index).getEstructura());
                duplicarVC.setMotivocambiocargo(filterVC.get(index).getMotivocambiocargo());
                duplicarVC.setCargo(filterVC.get(index).getCargo());
                duplicarVC.setEmpleadojefe(filterVC.get(index).getEmpleadojefe());
                duplicarVC.setCalificacion(filterVC.get(index).getCalificacion());
                duplicarVC.setEmpleado(filterVC.get(index).getEmpleado());
                duplicarVC.setEscalafon(filterVC.get(index).getEscalafon());
                duplicarVC.setLiquidahe(filterVC.get(index).getLiquidahe());
                duplicarVC.setTurnorotativo(filterVC.get(index).getTurnorotativo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVC");
            context.execute("duplicarRegistroVC.show()");
            index = -1;
            secRegistro = null;
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
                infoRegistro = "Cantidad de registros: " + vigenciasCargosEmpleado.size();
                listVCCrear.add(duplicarVC);
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.update("form:informacionRegistro");
                context.update("form:datosVCEmpleado");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                    altoTabla = "270";
                    context.update("form:datosVCEmpleado");
                    bandera = 0;
                    filterVC = null;
                    tipoLista = 0;
                }
                duplicarVC = new VigenciasCargos();
                duplicarVC.setEstructura(new Estructuras());
                duplicarVC.setMotivocambiocargo(new MotivosCambiosCargos());
                duplicarVC.setCargo(new Cargos());
                context.execute("duplicarRegistroVC.hide()");
            } else {
                context.execute("validacionFechaDuplicada.show();");
            }
        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }

    public void limpiarduplicarVC() {
        duplicarVC = new VigenciasCargos();
        duplicarVC.setEstructura(new Estructuras());
        duplicarVC.setMotivocambiocargo(new MotivosCambiosCargos());
        duplicarVC.setCargo(new Cargos());
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                String forFecha = formatoFecha.format(vigenciasCargosEmpleado.get(index).getFechavigencia());
                estructurasLOV = administrarEstructuras.consultarNativeQueryEstructuras(forFecha);
                context.update("form:estructurasDialog");
                context.execute("estructurasDialog.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                tipoActualizacion = 0;
                context.update("form:motivosDialog");
                context.execute("motivosDialog.show()");
            } else if (cualCelda == 3) {
                tipoActualizacion = 0;
                context.update("form:cargosDialog");
                context.execute("cargosDialog.show()");
            } else if (cualCelda == 5) {
                tipoActualizacion = 0;
                context.update("form:dialogoEmpleadoJefe");
                context.execute("dialogoEmpleadoJefe.show()");
            }
        }
    }
    //EXPORTAR PDF

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.addAuthor("Designer Software");
        pdf.setPageSize(PageSize.LETTER);
        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "prime_logo.png";

        //pdf.add(Image.getInstance(logo));
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
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) context.getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        //DataTable tabla = (DataTable) context.getViewRoot().findComponent("form:aficiones");
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasCargosXLS", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevaVC() {
        nuevaVigencia = new VigenciasCargos();
        nuevaVigencia.setEstructura(new Estructuras());
        nuevaVigencia.setMotivocambiocargo(new MotivosCambiosCargos());
        nuevaVigencia.setCargo(new Cargos());
        index = -1;
        secRegistro = null;
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            System.out.println("Activar");
            vcFecha = (Column) c.getViewRoot().findComponent("form:datosVCEmpleado:vcFecha");
            vcFecha.setFilterStyle("width: 60px");
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
            altoTabla = "246";
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
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVCEmpleado");
            bandera = 0;
            filterVC = null;
            tipoLista = 0;
        }
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros: " + filterVC.size();
        context.update("form:informacionRegistro");
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!vigenciasCargosEmpleado.isEmpty()) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASCARGOS");
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
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASCARGOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    public String getCantidadRegistrosLOV() {
        return cantidadRegistrosLOV;
    }

    public void setCantidadRegistrosLOV(String cantidadRegistrosLOV) {
        this.cantidadRegistrosLOV = cantidadRegistrosLOV;
    }

}

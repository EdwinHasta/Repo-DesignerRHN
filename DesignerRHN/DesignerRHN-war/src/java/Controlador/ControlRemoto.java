package Controlador;

import Administrar.AdministrarCarpetaPersonal;
import Entidades.*;
import InterfaceAdministrar.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.util.JRClassLoader;
import org.primefaces.component.column.Column;
import org.primefaces.component.datagrid.DataGrid;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class ControlRemoto implements Serializable {

    @EJB
    AdministrarCarpetaPersonalInterface administrarCarpetaPersonal;
    private Empleados empleado;
    private Personas persona;
    private DetallesEmpresas detallesEmpresas;
    private VWActualesCargos vwActualesCargos;
    private Usuarios usuarios;
    private ParametrosEstructuras parametrosEstructuras;
    private VWActualesTiposContratos vwActualesTiposContratos;
    private VWActualesNormasEmpleados vwActualesNormasEmpleados;
    private VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud;
    private VWActualesAfiliacionesPension vwActualesAfiliacionesPension;
    private VWActualesLocalizaciones vwActualesLocalizaciones;
    private VWActualesTiposTrabajadores vwActualesTiposTrabajadores;
    private VWActualesTiposTrabajadores trabajador;
    private VWActualesContratos vwActualesContratos;
    private VWActualesJornadas vwActualesJornadas;
    private VWActualesReformasLaborales vwActualesReformasLaborales;
    private VWActualesUbicaciones vwActualesUbicaciones;
    private VWActualesFormasPagos vwActualesFormasPagos;
    private VWActualesVigenciasViajeros vwActualesVigenciasViajeros;
    private String estadoVacaciones, actualMVR, actualIBC, actualSet, actualComprobante;
    private List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresesLista;
    private List<VWActualesTiposTrabajadores> backup;
    private List<VWActualesTiposTrabajadores> busquedaRapida;
    private List<VWActualesTiposTrabajadores> filterBusquedaRapida;
    private List<VWActualesTiposTrabajadores> filterBuscarEmpleado;
    private List<VWActualesTiposTrabajadores> buscarEmplTipo;
    private List<Empleados> empl;
    private List<VigenciasCargos> vigenciasCargosEmpleados;
    private VigenciasCargos vigenciaSeleccionada;
    private String fechaActualesTiposContratos;
    private String FechaDesde, FechaHasta, FechaSistema;
    private String Sueldo;
    private String tipo, tipoBk;
    private SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    private Locale locale = new Locale("es", "CO");
    private NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    private String Mensaje;
    private String Imagen;
    private String fotoEmpleado;
    private BigInteger secuencia, identificacion;
    private boolean acumulado, novedad, evaluacion, activo, pensionado, aspirante, hv1, hv2, bandera;
    private VWActualesTiposTrabajadores emplSeleccionado;
    private VWActualesTiposTrabajadores emplSeleccionadoBE;
    private SelectItem[] tipoEmpleado;
    private boolean buscar, buscarEmp, mostrarT;
    private String destino = "C:\\Prueba\\Juguetito\\FirstFormWhitBD\\web\\ArchivosCargados\\";
    private UploadedFile file;
    private String nombreTabla;
    private Integer estadoEmpleado;
    //datos tablas ctrl+f11
    private Column tablasNombre, tablasDescripcion, moduloCodigo, moduloNombre, moduloObs;
    private boolean filtrosActivos;
    //DataGrid
    private DataGrid dg;
    @EJB
    AdministrarCarpetaDesignerInterface administrarCarpetaDesigner;
    private List<Modulos> listModulos;
    private List<Modulos> filtradolistModulos;
    private List<Tablas> listTablas;
    private List<Tablas> filterListTablas;
    private Modulos selectModulo;
    private Tablas selectTabla;
    private Pantallas pantalla;
    private String tablaExportar, nombreArchivo;
    private BigInteger secuenciaMod;
    //PESTAÑA ACTUAL
    private int numPestaña;
    //SELECT ONE RADIO
    private String mensajePagos, tituloPago;
    private String pago;
    //ALTO TABLAS (PESTAÑA DESIGNER)
    private String altoModulos, altoTablas;
    //Buscar Tablas LOV
    private List<Tablas> listTablasLOV;
    private List<Tablas> filtradoListTablasLOV;
    private Tablas seleccionTablaLOV;
    private boolean buscarTablasLOV, mostrarTodasTablas;
    //Visualizar seleccion de tipos trabajadores (StyleClass)
    private String styleActivos, stylePensionados, styleRetirados, styleAspirantes;
    private String actualCargo;
    private String redirigir;

    public ControlRemoto() {
        vwActualesCargos = new VWActualesCargos();
        vwActualesTiposContratos = new VWActualesTiposContratos();
        vwActualesNormasEmpleados = new VWActualesNormasEmpleados();
        vwActualesAfiliacionesSalud = new VWActualesAfiliacionesSalud();
        vwActualesAfiliacionesPension = new VWActualesAfiliacionesPension();
        vwActualesLocalizaciones = new VWActualesLocalizaciones();
        vwActualesTiposTrabajadores = new VWActualesTiposTrabajadores();
        vwActualesContratos = new VWActualesContratos();
        vwActualesJornadas = new VWActualesJornadas();
        vwActualesReformasLaborales = new VWActualesReformasLaborales();
        vwActualesUbicaciones = new VWActualesUbicaciones();
        vwActualesFormasPagos = new VWActualesFormasPagos();
        vwActualesVigenciasViajeros = new VWActualesVigenciasViajeros();
        vwActualesTiposTrabajadoresesLista = new ArrayList<VWActualesTiposTrabajadores>();
        administrarCarpetaPersonal = new AdministrarCarpetaPersonal();
        busquedaRapida = null;
        Imagen = "personal1.gif";
        styleActivos = "ui-state-highlight";
        acumulado = false;
        novedad = false;
        evaluacion = false;
        activo = false;
        pensionado = true;
        aspirante = false;
        hv1 = true;
        hv2 = false;
        buscar = true;
        buscarEmp = false;
        mostrarT = true;
        bandera = false;
        //Carpeta Designer //
        listModulos = null;
        selectModulo = null;
        tablaExportar = "data1";
        nombreArchivo = "Modulos";
        System.out.println("Se creo un nuevo BakingBean de NominaF");
        //Inicializar pestaña en 0
        numPestaña = 0;
        pago = "AUTOMATICO";
        tituloPago = "PAGOS AUTOMATICOS";
        mensajePagos = "Realice liquidaciones automáticas quincenales, mensuales, entre otras, por estructuras o por tipo de empleado. Primero ingrese los parametros a liquidar, después genere la liquidación para luego poder observar los comprobantes de pago. Usted puede deshacer todas las liquidaciones que desee siempre y cuando no se hayan cerrado. Al cerrar una liquidación se generan acumulados, por eso es importante estar seguro que la liquidación es correcta antes de cerrarla.";
        altoModulos = "93";
        altoTablas = "202";
        buscarTablasLOV = true;
        mostrarTodasTablas = true;
        filtrosActivos = false;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarCarpetaPersonal.obtenerConexion(ses.getId());
            administrarCarpetaDesigner.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void datosIniciales(int pestaña) {
        numPestaña = pestaña;
    }

    public void cancelarModificacion() {
        //vigenciasCargosEmpleado = null;
        //RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Backing bean ControlVigenciasCargos.cancelarModificacion");
        //context.update("form:datosVCEmpleado");
    }

    public void valorImputText(int indice) throws ParseException {
        estadoEmpleado = indice;
        int name = indice;
        trabajador = vwActualesTiposTrabajadoresesLista.get(name);
        if (trabajador.getEmpleado() != null) {
            secuencia = trabajador.getEmpleado().getSecuencia();
            identificacion = trabajador.getEmpleado().getPersona().getNumerodocumento();
        }

        try {
            vwActualesCargos = administrarCarpetaPersonal.consultarActualCargoEmpleado(secuencia);
            Date actualFechaHasta = administrarCarpetaPersonal.consultarActualesFechas();
            String actualARP = administrarCarpetaPersonal.consultarActualARP(vwActualesCargos.getEstructura().getSecuencia(), vwActualesCargos.getCargo().getSecuencia(), actualFechaHasta);
            actualCargo = "%ARP: " + actualARP + " > " + vwActualesCargos.getCargo().getNombre() + " - " + vwActualesCargos.getEstructura().getOrganigrama().getEmpresa().getNombre();
            //actualCargo = "%ARP: " + " > " + vwActualesCargos.getCargo().getNombre() + " - " + vwActualesCargos.getEstructura().getNombre();
        } catch (Exception e) {
            actualCargo = null;
        }

        try {
            vwActualesTiposContratos = administrarCarpetaPersonal.consultarActualTipoContratoEmpleado(secuencia);
            fechaActualesTiposContratos = formato.format(vwActualesTiposContratos.getFechaVigencia());
        } catch (Exception e) {
            vwActualesTiposContratos = null;
            fechaActualesTiposContratos = null;
        }

        try {
            vwActualesNormasEmpleados = administrarCarpetaPersonal.consultarActualNormaLaboralEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesNormasEmpleados = null;
        }

        try {
            vwActualesAfiliacionesSalud = administrarCarpetaPersonal.consultarActualAfiliacionSaludEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesAfiliacionesSalud = null;
        }

        try {
            vwActualesAfiliacionesPension = administrarCarpetaPersonal.consultarActualAfiliacionPensionEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesAfiliacionesPension = null;
        }

        try {
            vwActualesLocalizaciones = administrarCarpetaPersonal.consultarActualLocalizacionEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesLocalizaciones = null;
        }

        try {
            vwActualesTiposTrabajadores = administrarCarpetaPersonal.consultarActualTipoTrabajadorEmpleado(secuencia);
        } catch (Exception e) {

            vwActualesTiposTrabajadores = null;
        }

        try {
            vwActualesContratos = administrarCarpetaPersonal.consultarActualContratoEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesContratos = null;
        }

        try {
            vwActualesJornadas = administrarCarpetaPersonal.consultarActualJornadaEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesJornadas = null;
        }

        try {
            Sueldo = "TOTAL: " + nf.format(administrarCarpetaPersonal.consultarActualSueldoEmpleado(secuencia));
        } catch (Exception e) {
            Sueldo = null;
        }

        try {
            vwActualesReformasLaborales = administrarCarpetaPersonal.consultarActualReformaLaboralEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesReformasLaborales = null;
        }

        try {
            vwActualesUbicaciones = administrarCarpetaPersonal.consultarActualUbicacionEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesUbicaciones = null;
        }

        try {
            vwActualesFormasPagos = administrarCarpetaPersonal.consultarActualFormaPagoEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesFormasPagos = null;
        }

        try {
            vwActualesVigenciasViajeros = administrarCarpetaPersonal.consultarActualTipoViajeroEmpleado(secuencia);
        } catch (Exception e) {
            vwActualesVigenciasViajeros = null;
        }

        try {
            estadoVacaciones = administrarCarpetaPersonal.consultarActualEstadoVacaciones(secuencia);
        } catch (Exception e) {
            estadoVacaciones = null;
        }
        try {
            actualMVR = administrarCarpetaPersonal.consultarActualMVR(secuencia);
        } catch (Exception e) {
            actualMVR = null;
        }

        try {
            actualIBC = administrarCarpetaPersonal.actualIBC(secuencia, trabajador.getEmpleado().getEmpresa().getRetencionysegsocxpersona());
        } catch (Exception e) {
            actualIBC = null;
        }

        try {
            actualSet = administrarCarpetaPersonal.consultarActualSet(secuencia);
        } catch (Exception e) {
            actualSet = null;
        }

        try {
            actualComprobante = administrarCarpetaPersonal.consultarActualComprobante(secuencia);
        } catch (Exception e) {
            actualComprobante = null;
        }

        //RequestContext.getCurrentInstance().update("formulario:info:VCargoDesempeñado");
        //FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formulario:VCargoDesempeñado");
    }

    public void activos() {
        backup = vwActualesTiposTrabajadoresesLista;
        tipoBk = tipo;
        tipo = "ACTIVO";
        vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
        RequestContext context = RequestContext.getCurrentInstance();
        if (vwActualesTiposTrabajadoresesLista.isEmpty()) {
            vwActualesTiposTrabajadoresesLista = backup;
            Mensaje = "Activo";
            context.execute("alerta.show()");
            tipo = tipoBk;
        } else {
            backup = null;
            Imagen = "personal1.gif";
            styleActivos = "ui-state-highlight";
            stylePensionados = "";
            styleRetirados = "";
            styleAspirantes = "";
            buscarEmp = false;
            tipoBk = null;
            acumulado = false;
            novedad = false;
            evaluacion = false;
            activo = false;
            pensionado = true;
            aspirante = false;
            hv1 = true;
            hv2 = false;
            mostrarT = true;
            dg = (DataGrid) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data");
            dg.setFirst(0);
            context.update("form:tabMenu:data");
            context.update("form:tabMenu:Activos");
            context.update("form:tabMenu:Pensionados");
            context.update("form:tabMenu:Retirados");
            context.update("form:tabMenu:Aspirantes");
        }
    }

    public void pensionados() {

        backup = vwActualesTiposTrabajadoresesLista;
        tipoBk = tipo;
        tipo = "PENSIONADO";
        vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
        RequestContext context = RequestContext.getCurrentInstance();
        if (vwActualesTiposTrabajadoresesLista.isEmpty()) {
            vwActualesTiposTrabajadoresesLista = backup;
            Mensaje = "Pensionado";
            context.execute("alerta.show()");
            tipo = tipoBk;
        } else {
            backup = null;
            Imagen = "personal2.gif";
            stylePensionados = "ui-state-highlight";
            styleActivos = "";
            styleRetirados = "";
            styleAspirantes = "";
            buscarEmp = false;
            tipoBk = null;
            acumulado = false;
            novedad = false;
            evaluacion = false;
            activo = true;
            pensionado = false;
            aspirante = true;
            hv1 = true;
            hv2 = true;
            mostrarT = true;
            dg = (DataGrid) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data");
            dg.setFirst(0);
            context.update("form:tabMenu:data");
            context.update("form:tabMenu:Activos");
            context.update("form:tabMenu:Pensionados");
            context.update("form:tabMenu:Retirados");
            context.update("form:tabMenu:Aspirantes");
        }
    }

    public void retirados() {
        backup = vwActualesTiposTrabajadoresesLista;
        tipoBk = tipo;
        tipo = "RETIRADO";
        vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
        RequestContext context = RequestContext.getCurrentInstance();
        if (vwActualesTiposTrabajadoresesLista.isEmpty()) {
            vwActualesTiposTrabajadoresesLista = backup;
            Mensaje = "Retirado";
            context.execute("alerta.show()");
            tipo = tipoBk;
        } else {
            backup = null;
            Imagen = "personal3.gif";
            styleRetirados = "ui-state-highlight";
            stylePensionados = "";
            styleActivos = "";
            styleAspirantes = "";
            buscarEmp = false;
            tipoBk = null;
            acumulado = false;
            novedad = false;
            evaluacion = true;
            activo = true;
            pensionado = true;
            aspirante = true;
            hv1 = true;
            hv2 = true;
            mostrarT = true;
            dg = (DataGrid) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data");
            dg.setFirst(0);
            context.update("form:tabMenu:data");
            context.update("form:tabMenu:Activos");
            context.update("form:tabMenu:Pensionados");
            context.update("form:tabMenu:Retirados");
            context.update("form:tabMenu:Aspirantes");
        }
    }

    public void aspirantes() {
        backup = vwActualesTiposTrabajadoresesLista;
        tipoBk = tipo;
        tipo = "DISPONIBLE";
        vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
        RequestContext context = RequestContext.getCurrentInstance();
        if (vwActualesTiposTrabajadoresesLista.isEmpty()) {
            vwActualesTiposTrabajadoresesLista = backup;
            Mensaje = "Aspirante";
            context.execute("alerta.show()");
            tipo = tipoBk;
        } else {
            backup = null;
            Imagen = "personal4.gif";
            styleAspirantes = "ui-state-highlight";
            stylePensionados = "";
            styleActivos = "";
            styleRetirados = "";
            buscarEmp = false;
            tipoBk = null;
            acumulado = true;
            novedad = true;
            evaluacion = false;
            activo = true;
            pensionado = true;
            aspirante = false;
            hv1 = false;
            hv2 = true;
            mostrarT = true;
            dg = (DataGrid) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data");
            dg.setFirst(0);
            context.update("form:tabMenu:data");
            context.update("form:tabMenu:Activos");
            context.update("form:tabMenu:Pensionados");
            context.update("form:tabMenu:Retirados");
            context.update("form:tabMenu:Aspirantes");
        }
    }

    /* private SelectItem[] createFilterOptions() {

     SelectItem[] options = new SelectItem[4];
     options[0] = new SelectItem("", "Select");

     return options;
     }

     public SelectItem[] getTipoEmpleado() {
     return tipoEmpleado;
     }*/
    public void busquedaRapida() {
        filterBusquedaRapida = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (emplSeleccionado.getTipoTrabajador().getTipo().equalsIgnoreCase("Activo")) {
            Imagen = "personal1.gif";
            styleActivos = "ui-state-highlight";
            stylePensionados = "";
            styleRetirados = "";
            styleAspirantes = "";
            acumulado = false;
            novedad = false;
            evaluacion = false;
            activo = false;
            pensionado = true;
            aspirante = false;
            hv1 = true;
            hv2 = false;
            tipo = "ACTIVO";
        }
        if (emplSeleccionado.getTipoTrabajador().getTipo().equalsIgnoreCase("Pensionado")) {
            Imagen = "personal2.gif";
            stylePensionados = "ui-state-highlight";
            styleActivos = "";
            styleRetirados = "";
            styleAspirantes = "";
            acumulado = false;
            novedad = false;
            evaluacion = false;
            activo = true;
            pensionado = false;
            aspirante = true;
            hv1 = true;
            hv2 = true;
            tipo = "PENSIONADO";
        }
        if (emplSeleccionado.getTipoTrabajador().getTipo().equalsIgnoreCase("Retirado")) {
            Imagen = "personal3.gif";
            styleRetirados = "ui-state-highlight";
            stylePensionados = "";
            styleActivos = "";
            styleAspirantes = "";
            acumulado = false;
            novedad = false;
            evaluacion = true;
            activo = true;
            pensionado = true;
            aspirante = true;
            hv1 = true;
            hv2 = true;
            tipo = "RETIRADO";
        }
        if (emplSeleccionado.getTipoTrabajador().getTipo().equalsIgnoreCase("Disponible")) {
            Imagen = "personal4.gif";
            styleAspirantes = "ui-state-highlight";
            styleRetirados = "";
            stylePensionados = "";
            styleActivos = "";
            acumulado = true;
            novedad = true;
            evaluacion = false;
            activo = true;
            pensionado = true;
            aspirante = false;
            hv1 = false;
            hv2 = true;
            tipo = "DISPONIBLE";
        }
        context.update("form:tabMenu:Activos");
        context.update("form:tabMenu:Pensionados");
        context.update("form:tabMenu:Retirados");
        context.update("form:tabMenu:Aspirantes");
        vwActualesTiposTrabajadoresesLista.clear();
        vwActualesTiposTrabajadoresesLista.add(emplSeleccionado);
        emplSeleccionado = null;
        buscar = true;
        buscarEmp = true;
        mostrarT = false;
        //DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:lvBusquedaRapida");
        //dataTable.reset();
    }

    public void busquedaRapidaAtras() {
        buscar = true;
        filterBusquedaRapida = null;
        emplSeleccionado = null;
    }

    public void activarBuscar() {
        buscar = false;
    }

    public void buscarEmpleado() {
        filterBuscarEmpleado = null;
        tipo = emplSeleccionadoBE.getTipoTrabajador().getTipo();
        System.out.println(tipo);

        vwActualesTiposTrabajadoresesLista = new ArrayList<VWActualesTiposTrabajadores>();
        vwActualesTiposTrabajadoresesLista.add(emplSeleccionadoBE);
        emplSeleccionadoBE = null;
        buscar = true;
        mostrarT = false;
    }

    public void busquedaEmpleadoAtras() {
        buscar = true;
        filterBuscarEmpleado = null;
        emplSeleccionadoBE = null;
    }

    public void mostrarTodos() {
        vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);

        mostrarT = true;
        buscarEmp = false;
    }

    public void transformarArchivo(long size, InputStream in) {
        try {
            //extencion = fileName.split("[.]")[1];
            //System.out.println(extencion);

            OutputStream out = new FileOutputStream(new File(destino + identificacion + ".jpg"));
            int reader = 0;
            byte[] bytes = new byte[(int) size];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
            administrarCarpetaPersonal.actualizarFotoPersona(identificacion);
            //RequestContext.getCurrentInstance().update("formEncrip:foto");
        } catch (Exception e) {
            System.out.println("Pailander");
        }
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        System.out.println(event.getFile().getFileName());
        transformarArchivo(event.getFile().getSize(), event.getFile().getInputstream());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("Cargar.hide()");
        context.execute("Exito.show()");
    }

    public void probar(String nombreTab) {
        System.out.println(nombreTabla);
        System.out.println(nombreTab);
        //nombreTabla = nombreTab;
        setNombreTabla(nombreTab);
        System.out.println(nombreTabla);
    }

    public void lab() {
        System.out.println("HotKey <3");
        RequestContext context = RequestContext.getCurrentInstance();
        if (tablaExportar.equals("Tablas")) {
            tablasNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasNombre");
            tablasNombre.setFilterStyle("");
            tablasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasDescripcion");
            tablasDescripcion.setFilterStyle("");
            altoTablas = "176";
            context.update("form:tabMenu:Tablas");
            filtrosActivos = true;
        } else if (tablaExportar.equals("data1")) {
            moduloCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloCodigo");
            moduloCodigo.setFilterStyle("width: 40px");
            moduloNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloNombre");
            moduloNombre.setFilterStyle("");
            moduloObs = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloObs");
            moduloObs.setFilterStyle("");
            altoModulos = "70";
            context.update("form:tabMenu:data1");
            filtrosActivos = true;
        }
    }

    public void lab2() {
        tablasNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasNombre");
        tablasNombre.setFilterStyle("display: none; visibility: hidden;");
        tablasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasDescripcion");
        tablasDescripcion.setFilterStyle("display: none; visibility: hidden;");
        altoTablas = "202";
        filtrosActivos = false;
        RequestContext.getCurrentInstance().update("form:tabMenu:Tablas");
    }
/////////////////////////  
    private Integer progress;

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + 1;
            RequestContext.getCurrentInstance().update("formu:progreso");
            if (progress > 100) {
                progress = 100;
            }
        }
        return progress;
    }

    public void cancel() {
        progress = null;
    }

// Carpeta Designer //
    public List<Modulos> getListModulos() {
        if (listModulos == null) {
            listModulos = administrarCarpetaDesigner.consultarModulos();
            return listModulos;
        } else {
            return listModulos;
        }
    }

    public void cambiarTablas() {
        secuenciaMod = selectModulo.getSecuencia();
        listTablas = administrarCarpetaDesigner.consultarTablas(secuenciaMod);
        if (listTablas != null && !listTablas.isEmpty()) {
            buscarTablasLOV = false;
        } else {
            buscarTablasLOV = true;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (tablaExportar.equals("Tablas")) {
            tablasNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasNombre");
            tablasNombre.setFilterStyle("display: none; visibility: hidden;");
            tablasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasDescripcion");
            tablasDescripcion.setFilterStyle("display: none; visibility: hidden;");
            context.execute("tabl.clearFilters()");
            altoTablas = "202";
            context.update("form:tabMenu:Tablas");
            filtrosActivos = false;
        }
        mostrarTodasTablas = true;
        context.update("form:tabMenu:mostrarTodasTablas");
        context.update("form:tabMenu:buscarTablas");
        tablaExportar = "data1";
        nombreArchivo = "Modulos";
        filterListTablas = null;
    }

    public void exportarTabla() {
        if (tablaExportar.equals("data1")) {
            moduloCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloCodigo");
            moduloCodigo.setFilterStyle("display: none; visibility: hidden;");
            moduloNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloNombre");
            moduloNombre.setFilterStyle("display: none; visibility: hidden;");
            moduloObs = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:data1:moduloObs");
            moduloObs.setFilterStyle("display: none; visibility: hidden;");
            filtradolistModulos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("data1.clearFilters()");
            altoModulos = "93";
            context.update("form:tabMenu:data1");
            filtrosActivos = false;
        }
        tablaExportar = "Tablas";
        nombreArchivo = "Tablas";
    }

    
      
    public String redireccion(Integer indice){
         if(indice >= 0){
            if(listTablas.get(indice).getNombre().equalsIgnoreCase("USUARIOS")){
                redirigir = "usuario";
                
            } // Aca vienen un huevo de Else if para el resto de las pantallas
        }
        return redirigir;
    }
    
    public void infoTablas(Tablas tab) {
        
        selectTabla = tab;
        System.out.println(selectTabla.getSecuencia());
        BigInteger secuenciaTab = selectTabla.getSecuencia();
        pantalla = administrarCarpetaDesigner.consultarPantalla(secuenciaTab);
        System.out.println(pantalla.getNombre());
        RequestContext context = RequestContext.getCurrentInstance();
        tablaExportar = "Tablas";
        System.out.println("Va a imprmir -->" + tablaExportar);
        context.execute("VentanaTab.show()");
    }

    public List<Tablas> getListTablas() {
        return listTablas;
    }

    public Modulos getSelectModulo() {
        if (selectModulo == null) {
            if (listModulos == null) {
                getListModulos();
            }
            if (listModulos != null && !listModulos.isEmpty()) {
                selectModulo = listModulos.get(0);
                secuenciaMod = selectModulo.getSecuencia();
                listTablas = administrarCarpetaDesigner.consultarTablas(secuenciaMod);
                if (listTablas != null && !listTablas.isEmpty()) {
                    buscarTablasLOV = false;
                }
            } else {
                listTablas = null;
            }
            return selectModulo;
        } else {
            return selectModulo;
        }
    }

    public void setSelectModulo(Modulos selectModulo) {
        this.selectModulo = selectModulo;
    }

    public Tablas getSelectTabla() {
        return selectTabla;
    }

    public void setSelectTabla(Tablas selectTabla) {
        this.selectTabla = selectTabla;
    }

    public Pantallas getPantalla() {
        return pantalla;
    }

    public void setPantalla(Pantallas pantalla) {
        this.pantalla = pantalla;
    }

    public String getTablaExportar() {
        return tablaExportar;
    }

    public List<Tablas> getFilterListTablas() {
        return filterListTablas;
    }

    public void setFilterListTablas(List<Tablas> filterListTablas) {
        this.filterListTablas = filterListTablas;
    }

    public List<Modulos> getFiltradolistModulos() {
        return filtradolistModulos;
    }

    public void setFiltradolistModulos(List<Modulos> filtradolistModulos) {
        this.filtradolistModulos = filtradolistModulos;
    }

    // Getter and Setter //
    public Empleados getEmpleado() {
        return empleado;
    }

    public VWActualesCargos getVwActualesCargos() {
        return vwActualesCargos;
    }

    public VWActualesTiposContratos getVwActualesTiposContratos() {
        return vwActualesTiposContratos;
    }

    public VWActualesNormasEmpleados getVwActualesNormasEmpleados() {
        return vwActualesNormasEmpleados;
    }

    public String getFechaActualesTiposContratos() {
        return fechaActualesTiposContratos;
    }

    public VWActualesAfiliacionesSalud getVwActualesAfiliacionesSalud() {
        return vwActualesAfiliacionesSalud;
    }

    public VWActualesAfiliacionesPension getVwActualesAfiliacionesPension() {
        return vwActualesAfiliacionesPension;
    }

    public VWActualesLocalizaciones getVwActualesLocalizaciones() {
        return vwActualesLocalizaciones;
    }

    public VWActualesTiposTrabajadores getVwActualesTiposTrabajadores() {
        return vwActualesTiposTrabajadores;
    }

    public VWActualesContratos getVwActualesContratos() {
        return vwActualesContratos;
    }

    public VWActualesJornadas getVwActualesJornadas() {
        return vwActualesJornadas;
    }

    public String getSueldo() {
        return Sueldo;
    }

    public VWActualesReformasLaborales getVwActualesReformasLaborales() {
        return vwActualesReformasLaborales;
    }

    public VWActualesUbicaciones getVwActualesUbicaciones() {
        return vwActualesUbicaciones;
    }

    public VWActualesFormasPagos getVwActualesFormasPagos() {
        return vwActualesFormasPagos;
    }

    public VWActualesVigenciasViajeros getVwActualesVigenciasViajeros() {
        return vwActualesVigenciasViajeros;
    }

    public String getNombreTabla() {
        nombreTabla = "data1";
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public List<VWActualesTiposTrabajadores> getVwActualesTiposTrabajadoresesLista() {
        if (!vwActualesTiposTrabajadoresesLista.isEmpty()) {
            return vwActualesTiposTrabajadoresesLista;
        } else {
            tipo = "ACTIVO";
            vwActualesTiposTrabajadoresesLista = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
            if (vwActualesTiposTrabajadoresesLista != null && vwActualesTiposTrabajadoresesLista.isEmpty()) {
                vwActualesTiposTrabajadoresesLista.add(new VWActualesTiposTrabajadores());
                acumulado = true;
                novedad = true;
                evaluacion = true;
                bandera = true;
                hv1 = true;
                hv2 = true;
            }else{
                acumulado = false;
                novedad = false;
                evaluacion = false;
                bandera = false;
                hv1 = false;
                hv2 = false;
            }
            return vwActualesTiposTrabajadoresesLista;
        }
    }

    public String getMensaje() {
        return Mensaje;
    }

    public String getImagen() {
        return Imagen;
    }

    public DetallesEmpresas getDetallesEmpresas() {
        detallesEmpresas = administrarCarpetaPersonal.consultarDetalleEmpresaUsuario();
        return detallesEmpresas;
    }

    public Usuarios getUsuarios() {
        String alias = administrarCarpetaPersonal.consultarAliasActualUsuario();
        usuarios = administrarCarpetaPersonal.consultarUsuario(alias);
        return usuarios;
    }

    public ParametrosEstructuras getParametrosEstructuras() {
        return parametrosEstructuras;
    }

    public String getFechaDesde() {
        parametrosEstructuras = administrarCarpetaPersonal.consultarParametrosUsuario();
        FechaDesde = formato.format(parametrosEstructuras.getFechadesdecausado());
        return FechaDesde;
    }

    public String getFechaHasta() {
        FechaHasta = formato.format(parametrosEstructuras.getFechahastacausado());
        return FechaHasta;
    }

    public String getFechaSistema() {
        FechaSistema = formato.format(parametrosEstructuras.getFechasistema());
        return FechaSistema;
    }

    public List<VigenciasCargos> getVigenciasCargosEmpleados() {
        //BigInteger s = BigInteger.valueOf(10661039);
        vigenciasCargosEmpleados = administrarCarpetaPersonal.consultarVigenciasCargosEmpleado(secuencia);
        return vigenciasCargosEmpleados;
    }

    public VigenciasCargos getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasCargos vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public VWActualesTiposTrabajadores getTrabajador() {
        return trabajador;
    }

    public boolean isAcumulado() {
        return acumulado;
    }

    public boolean isNovedad() {
        return novedad;
    }

    public boolean isEvaluacion() {
        return evaluacion;
    }

    public boolean isActivo() {
        return activo;
    }

    public boolean isPensionado() {
        return pensionado;
    }

    public boolean isAspirante() {
        return aspirante;
    }

    public boolean isHv1() {
        return hv1;
    }

    public boolean isHv2() {
        return hv2;
    }

    public VWActualesTiposTrabajadores getEmplSeleccionado() {
        return emplSeleccionado;
    }

    public void setEmplSeleccionado(VWActualesTiposTrabajadores emplSeleccionado) {
        this.emplSeleccionado = emplSeleccionado;
    }

    public String getMensajePagos() {
        return mensajePagos;
    }

    public String getTituloPago() {
        return tituloPago;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public List<VWActualesTiposTrabajadores> getBusquedaRapida() {
        try {
            if (busquedaRapida == null) {
                busquedaRapida = administrarCarpetaPersonal.consultarRapidaEmpleados();
                return busquedaRapida;
            }
            return busquedaRapida;

        } catch (Exception e) {
            System.out.println("Paila Bbe");
            return busquedaRapida;
        }

    }

    public List<VWActualesTiposTrabajadores> getFilterBusquedaRapida() {
        return filterBusquedaRapida;
    }

    public void setFilterBusquedaRapida(List<VWActualesTiposTrabajadores> filterBusquedaRapida) {
        this.filterBusquedaRapida = filterBusquedaRapida;
    }

    public List<VWActualesTiposTrabajadores> getFilterBuscarEmpleado() {
        return filterBuscarEmpleado;
    }

    public void setFilterBuscarEmpleado(List<VWActualesTiposTrabajadores> filterBuscarEmpleado) {
        this.filterBuscarEmpleado = filterBuscarEmpleado;
    }

    public VWActualesTiposTrabajadores getEmplSeleccionadoBE() {
        return emplSeleccionadoBE;
    }

    public void setEmplSeleccionadoBE(VWActualesTiposTrabajadores emplSeleccionadoBE) {
        this.emplSeleccionadoBE = emplSeleccionadoBE;
    }

    public boolean isBuscar() {
        return buscar;
    }

    public boolean isBuscarEmp() {
        return buscarEmp;
    }

    public boolean isMostrarT() {
        return mostrarT;
    }

    public List<VWActualesTiposTrabajadores> getBuscarEmplTipo() {
        buscarEmplTipo = administrarCarpetaPersonal.consultarEmpleadosTipoTrabajador(tipo);
        return buscarEmplTipo;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public BigInteger getIdentificacion() {
        return identificacion;
    }

    public Integer getEstadoEmpleado() {
        return estadoEmpleado;
    }

    public int getNumPestaña() {
        return numPestaña;
    }

    public void setNumPestaña(int numPestaña) {
        this.numPestaña = numPestaña;
    }

    public String getAltoModulos() {
        return altoModulos;
    }

    public String getAltoTablas() {
        return altoTablas;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public List<Tablas> getListTablasLOV() {
        return listTablasLOV;
    }

    public void setListTablasLOV(List<Tablas> listTablasLOV) {
        this.listTablasLOV = listTablasLOV;
    }

    public List<Tablas> getFiltradoListTablasLOV() {
        return filtradoListTablasLOV;
    }

    public void setFiltradoListTablasLOV(List<Tablas> filtradoListTablasLOV) {
        this.filtradoListTablasLOV = filtradoListTablasLOV;
    }

    public Tablas getSeleccionTablaLOV() {
        return seleccionTablaLOV;
    }

    public void setSeleccionTablaLOV(Tablas seleccionTablaLOV) {
        this.seleccionTablaLOV = seleccionTablaLOV;
    }

    public String getEstadoVacaciones() {
        return estadoVacaciones;
    }

    public String getActualMVR() {
        return actualMVR;
    }

    public void setEstadoVacaciones(String estadoVacaciones) {
        this.estadoVacaciones = estadoVacaciones;
    }

    public void setActualMVR(String actualMVR) {
        this.actualMVR = actualMVR;
    }

    public String getActualIBC() {
        return actualIBC;
    }

    public void setActualIBC(String actualIBC) {
        this.actualIBC = actualIBC;
    }

    public String getActualSet() {
        return actualSet;
    }

    public void setActualSet(String actualSet) {
        this.actualSet = actualSet;
    }

    public String getActualComprobante() {
        return actualComprobante;
    }

    public void setActualComprobante(String actualComprobante) {
        this.actualComprobante = actualComprobante;
    }

    public String getStyleActivos() {
        return styleActivos;
    }

    public String getStylePensionados() {
        return stylePensionados;
    }

    public String getStyleRetirados() {
        return styleRetirados;
    }

    public String getStyleAspirantes() {
        return styleAspirantes;
    }

    public String getActualCargo() {
        return actualCargo;
    }

    public String getFotoEmpleado() {
        persona = administrarCarpetaPersonal.consultarFotoPersona(identificacion);
        if (persona.getPathfoto() == null || persona.getPathfoto().equalsIgnoreCase("N")) {
            fotoEmpleado = "default.jpg";
            return fotoEmpleado;
        } else {
            fotoEmpleado = identificacion.toString() + ".jpg";
            return fotoEmpleado;
        }
    }

    public void pestañaActual(TabChangeEvent event) {
        Tab pestaña = event.getTab();
        if (pestaña.getId().equals("Personal")) {
            numPestaña = 0;
        } else if (pestaña.getId().equals("Nomina")) {
            numPestaña = 1;
        } else if (pestaña.getId().equals("Integracion")) {
            numPestaña = 2;
        } else if (pestaña.getId().equals("Gerencial")) {
            numPestaña = 3;
        } else if (pestaña.getId().equals("Designer")) {
            numPestaña = 4;
        }
    }

    public void recargar() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorDerecha");
        context.update("form:tablaInferiorIzquierda");
    }

    public void activarFiltro() {
        Column columna;
        Column columna2;
        Column columna3;
        DataTable tabla;
        DataTable tabla2;
        tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tablaSuperiorDerecha");
        tabla2 = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tablaSuperiorIzquierda");
        columna = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tablaSuperiorDerecha:moduloNombre2");
        columna2 = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tablaSuperiorDerecha:moduloObs2");
        columna3 = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tablaSuperiorIzquierda:moduloCodigo1");
        tabla.setStyle("font-size: 11px; width: 600px; position: relative;");
        tabla2.setStyle("font-size: 11px; width: 300px; position: relative; top: -6px;");
        columna.setFilterStyle("");
        columna2.setFilterStyle("");
        columna3.setFilterStyle("");
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaSuperiorDerecha");
        context.update("form:tablaSuperiorIzquierda");
    }

    public void cambiarFormaPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (pago.equalsIgnoreCase("AUTOMATICO")) {
            tituloPago = "PAGOS AUTOMATICOS";
            mensajePagos = "Realice liquidaciones automáticas quincenales, mensuales, entre otras, por estructuras o por tipo de empleado. Primero ingrese los parametros a liquidar, después genere la liquidación para luego poder observar los comprobantes de pago. Usted puede deshacer todas las liquidaciones que desee siempre y cuando no se hayan cerrado. Al cerrar una liquidación se generan acumulados, por eso es importante estar seguro que la liquidación es correcta antes de cerrarla.";
            context.update("form:tabMenu:tipoPago");
            context.update("form:tabMenu:mensajePago");
        } else if (pago.equalsIgnoreCase("NO AUTOMATICO")) {
            tituloPago = "PAGOS POR FUERA DE NÓMINA";
            mensajePagos = "Genere pagos por fuera de nómina cuando necesite liquidar vacaciones por anticipado, viaticos, entre otros. esta liquidaciones se pueden efectuar por estructura o por empleado. Primero ingrese los parametros a liquidar, después genere la liquidación para luego poder observar los comprobantes de pago. Usted puede deshacer todas las liquidaciones que desee siempre y cuando no se hayan cerrado. Al cerrar una liquidación se generan acumulados, por eso es importante estar seguro que la liquidación es correcta antes de cerrarla.";
            context.update("form:tabMenu:tipoPago");
            context.update("form:tabMenu:mensajePago");
        }
    }

    public void buscarTablas() {
        if (selectModulo != null) {
            filtradoListTablasLOV = null;
            listTablasLOV = administrarCarpetaDesigner.consultarTablas(selectModulo.getSecuencia());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:lovTablas");
            context.execute("buscarTablasDialogo.show()");
        }
    }

    public void seleccionTabla() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (filtrosActivos == true) {
            tablasNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasNombre");
            tablasNombre.setFilterStyle("display: none; visibility: hidden;");
            tablasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tabMenu:Tablas:tablasDescripcion");
            tablasDescripcion.setFilterStyle("display: none; visibility: hidden;");
            altoTablas = "200";
            filtrosActivos = false;
            context.execute("tabl.clearFilters()");
        }
        tablaExportar = "data1";
        nombreArchivo = "Modulos";
        listTablas.clear();
        listTablas.add(seleccionTablaLOV);
        mostrarTodasTablas = false;
        filtradoListTablasLOV = null;
        seleccionTablaLOV = null;
        buscar = true;
        context.reset("form:lovTablas:globalFilter");
        context.update("form:lovTablas");
        context.update("form:mostrarTodasTablas");
        context.update("form:tabMenu:Tablas");
        context.update("form:tabMenu:mostrarTodasTablas");
        context.execute("buscarTablasDialogo.hide()");
    }

    public void cancelarSeleccionTabla() {
        filtradoListTablasLOV = null;
        seleccionTablaLOV = null;
        buscar = true;
    }

    public void mostrarTodo_Tablas() {
        listTablas.clear();
        listTablas = administrarCarpetaDesigner.consultarTablas(selectModulo.getSecuencia());
        filterListTablas = null;
        System.out.println("Tamaño: " + listTablas.size());
        mostrarTodasTablas = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tabMenu:Tablas");
        context.update("form:tabMenu:mostrarTodasTablas");
    }

    public void validarBorradoLiquidacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (pago.equals("AUTOMATICO")) {
            context.execute("confirmarBorrarLiquidacion.show()");
        } else if (pago.equals("NO AUTOMATICO")) {
            context.execute("confirmarBorrarLiquidacionPorFuera.show()");
        }
    }

    public void borrarLiquidacion() {
        administrarCarpetaPersonal.borrarLiquidacionAutomatico();
        mensajeResultadoBorrarLiquidacion();
    }

    public void borrarLiquidacionPorFuera() {
        administrarCarpetaPersonal.borrarLiquidacionNoAutomatico();
        mensajeResultadoBorrarLiquidacion();
    }

    public void mensajeResultadoBorrarLiquidacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage("Información", "Liquidación borrada con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
    }

    public boolean isBuscarTablasLOV() {
        return buscarTablasLOV;
    }

    public boolean isMostrarTodasTablas() {
        return mostrarTodasTablas;
    }

    public boolean isBandera() {
        return bandera;
    }
}

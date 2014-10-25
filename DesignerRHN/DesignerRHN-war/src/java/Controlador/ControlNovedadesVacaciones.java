/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.NovedadesSistema;
import Entidades.Vacaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNovedadesSistemaInterface;
import InterfaceAdministrar.AdministrarNovedadesVacacionesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesVacaciones implements Serializable {

    @EJB
    AdministrarNovedadesVacacionesInterface administrarNovedadesVacaciones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    @EJB
    AdministrarNovedadesSistemaInterface administrarNovedadesSistema;

    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaEmpleado;
    //Lista Empleados Novedad Vacaciones
    private List<Empleados> listaEmpleadosNovedad;
    private List<Empleados> filtradosListaEmpleadosNovedad;
    private Empleados empleadoSeleccionado;
    //LISTA NOVEDADES
    private List<NovedadesSistema> listaNovedades;
    private List<NovedadesSistema> filtradosListaNovedades;
    private NovedadesSistema novedadSeleccionada;
    //Duplicar
    private NovedadesSistema duplicarNovedad;
    //editar celda
    private NovedadesSistema editarNovedades;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    private int editor;
    //Crear Novedades
    private List<NovedadesSistema> listaNovedadesCrear;
    public NovedadesSistema nuevaNovedad;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<NovedadesSistema> listaNovedadesModificar;
    //Borrar Novedades
    private List<NovedadesSistema> listaNovedadesBorrar;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //LOV EMPLEADOS
    private List<Empleados> listaEmpleados;
    private List<Empleados> filtradoslistaEmpleados;
    private Empleados seleccionEmpleados;
    //LOV PERIODOS
    private List<Vacaciones> listaPeriodos;
    private List<Vacaciones> filtradoslistaPeriodos;
    private Vacaciones seleccionPeriodo;
    //Autocompletar
    private Date FechaInicial, FechaSiguiente, AdelantoHasta, FechaPago;
    private String Periodo;
    private BigDecimal Dias;
    private Short DiasAplazados;
    private String Subtipo;
    //Columnas Tabla NOVEDADES
    private Column nEFechaInicialDisfrute, nEPeriodo, nEDias, nEFechaSiguiente,
            nESubTipo, nEAdelantoHasta, nEFechaPago, nEDiasAplazados;
    private Date suma364;
    private Date finalsuma364;
    private Boolean readonly;
    private BigInteger diasTotales;
    private short diasAplazadosTotal;
    private final String cero;
    //VALIDAR SI EL QUE SE VA A BORRAR ESTÁ EN SOLUCIONES FORMULAS
    private int resultado;
    //ALTO SCROLL TABLA
    private String altoTabla;

    public ControlNovedadesVacaciones() {
        cero = "0";
        listaEmpleadosNovedad = null;
        listaNovedadesBorrar = new ArrayList<NovedadesSistema>();
        listaNovedadesCrear = new ArrayList<NovedadesSistema>();
        listaNovedadesModificar = new ArrayList<NovedadesSistema>();
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaEmpleados = null;
        permitirIndex = true;
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setSubtipo("TIEMPO");
        nuevaNovedad.setTipo("VACACION");
        nuevaNovedad.setVacacion(new Vacaciones());
        nuevaNovedad.setVacadiasaplazados(Short.valueOf(cero));
        diasTotales = BigInteger.valueOf(0);
        diasAplazadosTotal = Short.parseShort(cero);
        altoTabla = "115";
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarNovedadesVacaciones.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarNovedades = listaNovedades.get(index);
            }
            if (tipoLista == 1) {
                editarNovedades = filtradosListaNovedades.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editFechaInicial");
                context.execute("editFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editPeriodo");
                context.execute("editPeriodo.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editDias");
                context.execute("editDias.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editFechaSiguiente");
                context.execute("editFechaSiguiente.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editSubtipo");
                context.execute("editSubtipo.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editAdelantoHasta");
                context.execute("editAdelantoHasta.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editFechaPago");
                context.execute("editFechaPago.show()");
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarDA");
                context.execute("editarDA.show()");
            }
        }
        index = -1;
        secRegistro = null;
    }

    //LIMPIAR NUEVO REGISTRO NOVEDAD
    public void limpiarNuevaNovedad() {
        String cero = "0";
        nuevaNovedad = new NovedadesSistema();
        nuevaNovedad.setSubtipo("TIEMPO");
        nuevaNovedad.setTipo("VACACION");
        nuevaNovedad.setVacadiasaplazados(Short.valueOf(cero));
        readonly = false;
        index = -1;
        secRegistro = null;
    }

    public void limpiarduplicarNovedades() {
        String cero = "0";
        duplicarNovedad = new NovedadesSistema();
        duplicarNovedad.setSubtipo("TIEMPO");
        duplicarNovedad.setTipo("VACACION");
        duplicarNovedad.setVacadiasaplazados(Short.valueOf(cero));
        readonly = false;
        index = -1;
        secRegistro = null;

    }

    //CREAR NOVEDADES
    public void agregarNuevaNovedadVacaciones() {
        int pasa = 0;
        Empleados emp = new Empleados();
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevaNovedad.getFechainicialdisfrute() == null) {
            System.out.println("Entro a Fecha Inicial Disfrute");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial Disfrute\n";
            pasa++;
        }

        if (nuevaNovedad.getDias() == null) {
            System.out.println("Entro a Dias");
            mensajeValidacion = mensajeValidacion + " * Dias\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadEmpleado");
            context.execute("validacionNuevaNovedadEmpleado.show()");
        }

        if (pasa == 0) {
            if (bandera == 1) {
                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                FacesContext c = FacesContext.getCurrentInstance();

                altoTabla = "115";
                nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
                nEFechaInicialDisfrute.setFilterStyle("display: none; visibility: hidden;");
                nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
                nEPeriodo.setFilterStyle("display: none; visibility: hidden;");
                nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
                nEDias.setFilterStyle("display: none; visibility: hidden;");
                nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
                nEFechaSiguiente.setFilterStyle("display: none; visibility: hidden;");
                nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
                nESubTipo.setFilterStyle("display: none; visibility: hidden;");
                nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
                nEAdelantoHasta.setFilterStyle("display: none; visibility: hidden;");
                nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
                nEFechaPago.setFilterStyle("display: none; visibility: hidden;");
                nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
                nEDiasAplazados.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
                bandera = 0;
                filtradosListaNovedades = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevaNovedad.setSecuencia(l);
            nuevaNovedad.setEmpleado(emp); //Envia empleado
            System.out.println("Empleado enviado: " + emp.getPersona().getNombreCompleto());
            listaNovedadesCrear.add(nuevaNovedad);
            System.out.println(listaNovedadesCrear.size());
            System.out.println(listaNovedadesCrear.get(0).getTipo());
            System.out.println(nuevaNovedad.getTipo());
            listaNovedades.add(nuevaNovedad);
            nuevaNovedad = new NovedadesSistema();
            nuevaNovedad.setSubtipo("TIEMPO");
            nuevaNovedad.setSubtipo("TIEMPO");
            nuevaNovedad.setTipo("VACACION");
            nuevaNovedad.setVacacion(new Vacaciones());
            nuevaNovedad.setVacadiasaplazados(Short.valueOf(cero));

            context.update("form:datosNovedadesEmpleado");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.execute("NuevaNovedadVacaciones.hide()");
            index = -1;
            secRegistro = null;
        } else {
        }
    }

    //DUPLICAR ENCARGATURA
    public void duplicarNV() {
        System.out.println("Index= " + index);
        if (index >= 0) {
            duplicarNovedad = new NovedadesSistema();
            k++;
            l = BigInteger.valueOf(k);
            Empleados emple = new Empleados();

            for (int i = 0; i < listaEmpleados.size(); i++) {
                if (empleadoSeleccionado.getSecuencia().compareTo(listaEmpleados.get(i).getSecuencia()) == 0) {
                    emple = listaEmpleados.get(i);
                }
            }
            if (tipoLista == 0) {

                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(emple);
                duplicarNovedad.setFechainicialdisfrute(listaNovedades.get(index).getFechainicialdisfrute());
                duplicarNovedad.getVacacion().setPeriodo(listaNovedades.get(index).getVacacion().getPeriodo());
                duplicarNovedad.setDias(listaNovedades.get(index).getDias());
                duplicarNovedad.setFechasiguientefinvaca(listaNovedades.get(index).getFechasiguientefinvaca());
                duplicarNovedad.setSubtipo(listaNovedades.get(index).getSubtipo());
                duplicarNovedad.setAdelantapagohasta(listaNovedades.get(index).getAdelantapagohasta());
                duplicarNovedad.setFechapago(listaNovedades.get(index).getFechapago());
                duplicarNovedad.setVacadiasaplazados(listaNovedades.get(index).getVacadiasaplazados());
            }
            if (tipoLista == 1) {
                duplicarNovedad.setSecuencia(l);
                duplicarNovedad.setEmpleado(emple);
                duplicarNovedad.setFechainicialdisfrute(filtradosListaNovedades.get(index).getFechainicialdisfrute());
                duplicarNovedad.getVacacion().setPeriodo(filtradosListaNovedades.get(index).getVacacion().getPeriodo());
                duplicarNovedad.setDias(filtradosListaNovedades.get(index).getDias());
                duplicarNovedad.setFechasiguientefinvaca(filtradosListaNovedades.get(index).getFechasiguientefinvaca());
                duplicarNovedad.setSubtipo(filtradosListaNovedades.get(index).getSubtipo());
                duplicarNovedad.setAdelantapagohasta(filtradosListaNovedades.get(index).getAdelantapagohasta());
                duplicarNovedad.setFechapago(filtradosListaNovedades.get(index).getFechapago());
                duplicarNovedad.setVacadiasaplazados(filtradosListaNovedades.get(index).getVacadiasaplazados());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarNovedad");
            context.execute("DuplicarRegistroNovedad.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {

        int pasa = 0;

        RequestContext context = RequestContext.getCurrentInstance();

        if (duplicarNovedad.getFechainicialdisfrute() == null) {
            System.out.println("Entro a Fecha Inicial");
            mensajeValidacion = mensajeValidacion + " * Fecha Inicial\n";
            pasa++;
        }

        if (duplicarNovedad.getEmpleado() == null) {
            System.out.println("Entro a Empleado");
            mensajeValidacion = mensajeValidacion + " * Empleado\n";
            pasa++;
        }

        if (duplicarNovedad.getDias() == null) {
            System.out.println("Entro a Dias");
            mensajeValidacion = mensajeValidacion + " * Formula\n";
            pasa++;
        }

        System.out.println("Valor Pasa: " + pasa);
        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevaNovedadEmpleado");
            context.execute("validacionNuevaNovedadEmpleado.show()");
        }

        if (pasa == 0) {
            listaNovedades.add(duplicarNovedad);
            listaNovedadesCrear.add(duplicarNovedad);

            context.update("form:datosNovedadesEmpleado");
            index = -1;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                altoTabla = "115";
                nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
                nEFechaInicialDisfrute.setFilterStyle("display: none; visibility: hidden;");
                nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
                nEPeriodo.setFilterStyle("display: none; visibility: hidden;");
                nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
                nEDias.setFilterStyle("display: none; visibility: hidden;");
                nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
                nEFechaSiguiente.setFilterStyle("display: none; visibility: hidden;");
                nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
                nESubTipo.setFilterStyle("display: none; visibility: hidden;");
                nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
                nEAdelantoHasta.setFilterStyle("display: none; visibility: hidden;");
                nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
                nEFechaPago.setFilterStyle("display: none; visibility: hidden;");
                nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
                nEDiasAplazados.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
                bandera = 0;
                filtradosListaNovedades = null;
                tipoLista = 0;
            }

            duplicarNovedad = new NovedadesSistema();
            context.update("formularioDialogos:DuplicarRegistroNovedad");
            context.execute("DuplicarRegistroNovedad.hide()");
        }
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NovedadVacacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNovedadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NovedadVacacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        FacesContext c = FacesContext.getCurrentInstance();

        if (bandera == 0) {
            altoTabla = "91";
            nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
            nEFechaInicialDisfrute.setFilterStyle("width: 60px");
            nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
            nEPeriodo.setFilterStyle("");
            nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
            nEDias.setFilterStyle("width: 15px");
            nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
            nEFechaSiguiente.setFilterStyle("width: 60px");
            nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
            nESubTipo.setFilterStyle("width: 60px");
            nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
            nEAdelantoHasta.setFilterStyle("width: 60px");
            nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
            nEFechaPago.setFilterStyle("width: 60px");
            nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
            nEDiasAplazados.setFilterStyle("width: 15px");
            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            altoTabla = "115";
            nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
            nEFechaInicialDisfrute.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
            nEPeriodo.setFilterStyle("display: none; visibility: hidden;");
            nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
            nEDias.setFilterStyle("display: none; visibility: hidden;");
            nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
            nEFechaSiguiente.setFilterStyle("display: none; visibility: hidden;");
            nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
            nESubTipo.setFilterStyle("display: none; visibility: hidden;");
            nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
            nEAdelantoHasta.setFilterStyle("display: none; visibility: hidden;");
            nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
            nEFechaPago.setFilterStyle("display: none; visibility: hidden;");
            nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
            nEDiasAplazados.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad = administrarNovedadesSistema.buscarEmpleados();
        } else {
            listaEmpleadosNovedad = administrarNovedadesSistema.buscarEmpleados();
        }
        if (!listaEmpleadosNovedad.isEmpty()) {
            empleadoSeleccionado = listaEmpleadosNovedad.get(0);
            listaEmpleadosNovedad = null;
            getListaEmpleadosNovedad();
        }
        empleadoSeleccionado = listaEmpleadosNovedad.get(0);
        listaNovedades = null;
        getListaNovedades();
        diasTotales = BigInteger.valueOf(0);
        diasAplazadosTotal = 0;
        if (!listaNovedades.isEmpty()) {
            for (int i = 0; i < listaNovedades.size(); i++) {
                diasTotales = diasTotales.add(listaNovedades.get(i).getDias());
                diasAplazadosTotal = (short) (diasAplazadosTotal + listaNovedades.get(i).getVacadiasaplazados());
            }
        } else {
            diasTotales = BigInteger.valueOf(0);
            diasAplazadosTotal = 0;
        }
        context.update("form:datosEmpleados");
        context.update("form:datosNovedadesEmpleado");
        getDiasTotales();
        getDiasAplazadosTotal();
        context.update("form:diasTotales");
        context.update("form:diasAplazados");

        filtradosListaEmpleadosNovedad = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    //Ubicacion Celda Indice Abajo. //Van los que no son NOT NULL.
    public void cambiarIndice(int indice, int celda) {
        System.out.println("Permitir Index: " + permitirIndex);
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            System.out.println("Index: " + index + " Celda: " + celda);
            if (tipoLista == 0) {
                secRegistro = listaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    FechaInicial = listaNovedades.get(index).getFechainicialdisfrute();
                } else if (cualCelda == 1) {
                    Periodo = listaNovedades.get(index).getVacacion().getPeriodo();
                } else if (cualCelda == 2) {
                    Dias = listaNovedades.get(index).getVacacion().getDiaspendientes();
                } else if (cualCelda == 3) {
                    FechaSiguiente = listaNovedades.get(index).getFechasiguientefinvaca();
                } else if (cualCelda == 4) {
                    Subtipo = listaNovedades.get(index).getSubtipo();
                } else if (cualCelda == 5) {
                    AdelantoHasta = listaNovedades.get(index).getAdelantapagohasta();
                } else if (cualCelda == 6) {
                    FechaPago = listaNovedades.get(index).getFechapago();
                } else if (cualCelda == 7) {
                    DiasAplazados = listaNovedades.get(index).getVacadiasaplazados();
                }
            } else {
                secRegistro = filtradosListaNovedades.get(index).getSecuencia();
                if (cualCelda == 0) {
                    FechaInicial = filtradosListaNovedades.get(index).getFechainicialdisfrute();
                } else if (cualCelda == 1) {
                    Periodo = filtradosListaNovedades.get(index).getVacacion().getPeriodo();
                } else if (cualCelda == 2) {
                    Dias = filtradosListaNovedades.get(index).getVacacion().getDiaspendientes();
                } else if (cualCelda == 3) {
                    FechaSiguiente = filtradosListaNovedades.get(index).getFechasiguientefinvaca();
                } else if (cualCelda == 5) {
                    AdelantoHasta = filtradosListaNovedades.get(index).getAdelantapagohasta();
                } else if (cualCelda == 6) {
                    FechaPago = filtradosListaNovedades.get(index).getFechapago();
                } else if (cualCelda == 7) {
                    DiasAplazados = filtradosListaNovedades.get(index).getVacadiasaplazados();
                }
            }
        }

    }

    //Ubicacion Celda Arriba 
    public void cambiarEmpleado() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        if (listaNovedadesCrear.isEmpty() && listaNovedadesBorrar.isEmpty() && listaNovedadesModificar.isEmpty()) {
            secuenciaEmpleado = empleadoSeleccionado.getSecuencia();
            listaNovedades = null;
            getListaNovedades();
            diasTotales = BigInteger.valueOf(0);
            diasAplazadosTotal = 0;
            if (!listaNovedades.isEmpty()) {
                for (int i = 0; i < listaNovedades.size(); i++) {
                    diasTotales = diasTotales.add(listaNovedades.get(i).getDias());
                    diasAplazadosTotal = (short) (diasAplazadosTotal + listaNovedades.get(i).getVacadiasaplazados());
                }
            } else {
                diasTotales = BigInteger.valueOf(0);
                diasAplazadosTotal = 0;
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesEmpleado");
            context.update("form:diasTotales");
            context.update("form:diasAplazados");

        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:cambiar");
            context.execute("cambiar.show()");
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {

        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        }

    }

    public void actualizarEmpleadosNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad.add(seleccionEmpleados);
            empleadoSeleccionado = listaEmpleadosNovedad.get(0);
        } else {
            listaEmpleadosNovedad.add(seleccionEmpleados);
        }
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        listaNovedades = null;
        getListaNovedades();
        diasTotales = BigInteger.valueOf(0);
        diasAplazadosTotal = 0;
        if (!listaNovedades.isEmpty()) {
            for (int i = 0; i < listaNovedades.size(); i++) {
                diasTotales = diasTotales.add(listaNovedades.get(i).getDias());
                diasAplazadosTotal = (short) (diasAplazadosTotal + listaNovedades.get(i).getVacadiasaplazados());
            }
        } else {
            diasTotales = BigInteger.valueOf(0);
            diasAplazadosTotal = 0;
        }
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
        //context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosNovedadesEmpleado");

        context.update("form:diasTotales");
        context.update("form:diasAplazados");
        filtradosListaEmpleadosNovedad = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarPeriodos() {

        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaNovedades.get(index).getVacacion().setPeriodo(seleccionPeriodo.getPeriodo());
                if (!listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(listaNovedades.get(index))) {
                        listaNovedadesModificar.add(listaNovedades.get(index));
                    }
                }
            } else {
                filtradosListaNovedades.get(index).getVacacion().setPeriodo(seleccionPeriodo.getPeriodo());
                if (!listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    if (listaNovedadesModificar.isEmpty()) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    } else if (!listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                        listaNovedadesModificar.add(filtradosListaNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosNovedadesEmpleado");
        } else if (tipoActualizacion == 1) {
            nuevaNovedad.getVacacion().setPeriodo(seleccionPeriodo.getPeriodo());
            nuevaNovedad.getVacacion().setDiaspendientes(seleccionPeriodo.getDiaspendientes());
            readonly = true;
            context.update("formularioDialogos:nuevaNovedad");
        } else if (tipoActualizacion == 2) {
            duplicarNovedad.getVacacion().setPeriodo(seleccionPeriodo.getPeriodo());
            duplicarNovedad.getVacacion().setDiaspendientes(seleccionPeriodo.getDiaspendientes());
            context.update("formularioDialogos:duplicarNovedad");
        }
        filtradoslistaPeriodos = null;
        seleccionPeriodo = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("formularioDialogos:LOVPeriodos:globalFilter");
        context.execute("LOVPeriodos.clearFilters()");
        context.execute("periodosDialogo.hide()");
        //context.update("formularioDialogos:LOVPeriodos");
    }

    //BORRAR NOVEDADES
    public void borrarNovedades() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaNovedadesModificar.isEmpty() && listaNovedadesModificar.contains(listaNovedades.get(index))) {
                    int modIndex = listaNovedadesModificar.indexOf(listaNovedades.get(index));
                    listaNovedadesModificar.remove(modIndex);
                    listaNovedadesBorrar.add(listaNovedades.get(index));
                } else if (!listaNovedadesCrear.isEmpty() && listaNovedadesCrear.contains(listaNovedades.get(index))) {
                    int crearIndex = listaNovedadesCrear.indexOf(listaNovedades.get(index));
                    listaNovedadesCrear.remove(crearIndex);
                } else {
                    listaNovedadesBorrar.add(listaNovedades.get(index));
                }
                listaNovedades.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaNovedadesModificar.isEmpty() && listaNovedadesModificar.contains(filtradosListaNovedades.get(index))) {
                    int modIndex = listaNovedadesModificar.indexOf(filtradosListaNovedades.get(index));
                    listaNovedadesModificar.remove(modIndex);
                    listaNovedadesBorrar.add(filtradosListaNovedades.get(index));
                } else if (!listaNovedadesCrear.isEmpty() && listaNovedadesCrear.contains(filtradosListaNovedades.get(index))) {
                    int crearIndex = listaNovedadesCrear.indexOf(filtradosListaNovedades.get(index));
                    listaNovedadesCrear.remove(crearIndex);
                } else {
                    listaNovedadesBorrar.add(filtradosListaNovedades.get(index));
                }
                int CIndex = listaNovedades.indexOf(filtradosListaNovedades.get(index));
                listaNovedades.remove(CIndex);
                filtradosListaNovedades.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesEmpleado");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    //GUARDAR
    public void guardarCambiosNovedades() {
        Empleados emp = new Empleados();
        if (guardado == false) {
            System.out.println("Realizando Operaciones Novedades");

            if (!listaNovedadesBorrar.isEmpty()) {
                for (int i = 0; i < listaNovedadesBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaNovedadesBorrar.size());

                    if (listaNovedadesBorrar.get(i).getVacacion().getSecuencia() == null) {
                        listaNovedadesBorrar.get(i).setVacacion(null);
                    }
                    if (listaNovedadesBorrar.get(i).getFechasiguientefinvaca() == null) {
                        listaNovedadesBorrar.get(i).setFechasiguientefinvaca(null);
                    }
                    if (listaNovedadesBorrar.get(i).getFechapago() == null) {
                        listaNovedadesBorrar.get(i).setFechapago(null);
                    }
                    if (listaNovedadesBorrar.get(i).getAdelantapagohasta() == null) {
                        listaNovedadesBorrar.get(i).setAdelantapagohasta(null);
                    }
                    if (listaNovedadesBorrar.get(i).getVacadiasaplazados() == null) {
                        listaNovedadesBorrar.get(i).setVacadiasaplazados(null);
                    }
                    administrarNovedadesSistema.borrarNovedades(listaNovedadesBorrar.get(i));
                }
                System.out.println("Entra");
                listaNovedadesBorrar.clear();
            }

            if (!listaNovedadesCrear.isEmpty()) {
                for (int i = 0; i < listaNovedadesCrear.size(); i++) {
                    System.out.println("Creando...");

                    if (listaNovedadesCrear.get(i).getVacacion().getSecuencia() == null) {
                        listaNovedadesCrear.get(i).setVacacion(null);
                    }
                    if (listaNovedadesCrear.get(i).getFechasiguientefinvaca() == null) {
                        listaNovedadesCrear.get(i).setFechasiguientefinvaca(null);
                    }

                    if (listaNovedadesCrear.get(i).getFechapago() == null) {
                        listaNovedadesCrear.get(i).setFechapago(null);
                    }
                    if (listaNovedadesCrear.get(i).getAdelantapagohasta() == null) {
                        listaNovedadesCrear.get(i).setAdelantapagohasta(null);
                    }
                    if (listaNovedadesCrear.get(i).getVacadiasaplazados() == null) {
                        listaNovedadesCrear.get(i).setVacadiasaplazados(null);
                    }
                    System.out.println(listaNovedadesCrear.get(i).getTipo());
                    administrarNovedadesSistema.crearNovedades(listaNovedadesCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaNovedadesCrear.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaNovedades = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNovedadesEmpleado");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            //  k = 0;
        }
        index = -1;
        secRegistro = null;
    }

    public void cancelarCambioPeriodos() {
        filtradoslistaPeriodos = null;
        seleccionPeriodo = null;
        readonly = false;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVPeriodos:globalFilter");
        context.execute("LOVPeriodos.clearFilters()");
        context.execute("periodosDialogo.hide()");
    }

    //Sumarle dias a una fecha determinada
    //@param fch La fecha para sumarle los dias
    //@param dias Numero de dias a agregar
    //@return La fecha agregando los dias
    public Date sumarFechasDias(Date creacionEmpleado, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(creacionEmpleado.getTime());
        cal.add(Calendar.DATE, dias);
        suma364 = cal.getTime();
        return suma364;
    }

    public Date sumarFechasDias2(Date inicioPeriodo, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(inicioPeriodo.getTime());
        cal.add(Calendar.DATE, dias);
        finalsuma364 = cal.getTime();
        return finalsuma364;
    }

    public void lovPeriodo(BigInteger secuenciaEmpleado, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();

        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        System.out.println(nuevaNovedad.getDias());
        if (nuevaNovedad.getDias() == null) {
            System.out.println("Entró");
            listaPeriodos = administrarNovedadesSistema.periodosEmpleado(empleadoSeleccionado.getSecuencia());
            if (listaPeriodos.isEmpty()) {
                context.update("formularioDialogos:dias");
                context.execute("dias.show()");
            } else {
                context.update("formularioDialogos:periodosDialogo");
                context.execute("periodosDialogo.show()");
            }
        }
    }

    public void diasMayor(BigInteger dias) {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Dias Mayor");
        BigInteger backup = dias;
        if (nuevaNovedad.getDias().compareTo(BigInteger.valueOf(15)) == 1) {
            context.update("formularioDialogos:diasMayor");
            nuevaNovedad.setDias(null);
            context.update("formularioDialogos:nuevaNovedad");
            context.execute("diasMayor.show()");
        }
    }

    public void validaciones(Integer lnd) {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Entra");
        if (lnd == 1) {
            if (nuevaNovedad.getFechainicialdisfrute().before(empleadoSeleccionado.getFechacreacion())) {
                context.update("formularioDialogos:validacion1");
                nuevaNovedad.setFechainicialdisfrute(null);
                context.update("formularioDialogos:nuevaNovedad");
                context.execute("validacion1.show()");
            }
        } else if (lnd == 2){
            if (duplicarNovedad.getFechainicialdisfrute().before(empleadoSeleccionado.getFechacreacion())) {
                context.update("formularioDialogos:validacion1");
                duplicarNovedad.setFechainicialdisfrute(null);
                context.update("formularioDialogos:duplicarNovedad");
                context.execute("validacion1.show()");
            }
        }

        // Esperar para la consulta a BD de si es o no festivo o día habil etc...
    }

    public void agregarPeriodo(BigInteger secuenciaEmpleado) {
        RequestContext context = RequestContext.getCurrentInstance();
        Vacaciones vaca = new Vacaciones();
        sumarFechasDias(empleadoSeleccionado.getFechacreacion(), 364);
        sumarFechasDias2(suma364, 364);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String per = formato.format(suma364) + " -> " + formato.format(finalsuma364);
        vaca.setSecuencia(BigDecimal.valueOf(0));
        vaca.setDiaspendientes(BigDecimal.valueOf(15));
        vaca.setInicialcausacion(suma364);
        vaca.setFinalcausacion(finalsuma364);
        vaca.setPeriodo(per);
        listaPeriodos.add(vaca);
        context.update("formularioDialogos:periodosDialogo");
        context.execute("periodosDialogo.show()");
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void cancelarCambioEmpleados() {
        filtradoslistaEmpleados = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
    }

    public void ubicacionCampo() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        Map<String, String> map = contexto.getExternalContext().getRequestParameterMap();
        String campo = map.get("CAMPO");
        editor = 0;
        
        if (campo.equals("FECHA INICIAL")) {
            cualCelda = 0;
        } else if (campo.equals("PERIODO")) {
            cualCelda = 1;
        } else if (campo.equals("DIAS")) {
            cualCelda = 2;
        } else if (campo.equals("FECHA SIGUIENTE")) {
            cualCelda = 3;
        } else if (campo.equals("SUBTIPO")) {
            cualCelda = 4;
        } else if (campo.equals("ADELANTA HASTA")) {
            cualCelda = 5;
        } else if (campo.equals("FECHA PAGO")) {
            cualCelda = 6;
        } else if (campo.equals("DA")) {
            cualCelda = 7;
        }
        System.out.println("cualCelda es: " + cualCelda);
    }
    
    
    public void indiceRegistro() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        Map<String, String> map = contexto.getExternalContext().getRequestParameterMap();
        String indi = map.get("INDICE");
        
        index = Integer.parseInt(indi);
        System.out.println("index" + index);
        
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println(index);
        if (!listaNovedades.isEmpty()) {
            if (secRegistro != null && index >= 0) {
                int result = administrarRastros.obtenerTabla(secRegistro, "NOVEDADESSISTEMA");
                if (result == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (result == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (result == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (result == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (result == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("NOVEDADESSISTEMA")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
        System.out.println(index);
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            altoTabla = "115";
            nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
            nEFechaInicialDisfrute.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
            nEPeriodo.setFilterStyle("display: none; visibility: hidden;");
            nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
            nEDias.setFilterStyle("display: none; visibility: hidden;");
            nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
            nEFechaSiguiente.setFilterStyle("display: none; visibility: hidden;");
            nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
            nESubTipo.setFilterStyle("display: none; visibility: hidden;");
            nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
            nEAdelantoHasta.setFilterStyle("display: none; visibility: hidden;");
            nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
            nEFechaPago.setFilterStyle("display: none; visibility: hidden;");
            nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
            nEDiasAplazados.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }

        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
//        k = 0;
        listaNovedades = null;
        guardado = true;
        permitirIndex = true;
        resultado = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosNovedadesEmpleado");

    }

    public void salir() {

        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            altoTabla = "115";
            nEFechaInicialDisfrute = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaInicialDisfrute");
            nEFechaInicialDisfrute.setFilterStyle("display: none; visibility: hidden;");
            nEPeriodo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEPeriodo");
            nEPeriodo.setFilterStyle("display: none; visibility: hidden;");
            nEDias = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDias");
            nEDias.setFilterStyle("display: none; visibility: hidden;");
            nEFechaSiguiente = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaSiguiente");
            nEFechaSiguiente.setFilterStyle("display: none; visibility: hidden;");
            nESubTipo = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nESubTipo");
            nESubTipo.setFilterStyle("display: none; visibility: hidden;");
            nEAdelantoHasta = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEAdelantoHasta");
            nEAdelantoHasta.setFilterStyle("display: none; visibility: hidden;");
            nEFechaPago = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEFechaPago");
            nEFechaPago.setFilterStyle("display: none; visibility: hidden;");
            nEDiasAplazados = (Column) c.getViewRoot().findComponent("form:datosNovedadesEmpleado:nEDiasAplazados");
            nEDiasAplazados.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;

            RequestContext.getCurrentInstance().update("form:datosNovedadesEmpleado");
            bandera = 0;
            filtradosListaNovedades = null;
            tipoLista = 0;
        }
        listaNovedadesBorrar.clear();
        listaNovedadesCrear.clear();
        listaNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
        listaNovedades = null;
        resultado = 0;
        guardado = true;
        permitirIndex = true;
    }

//GETTER & SETTER
    public List<Empleados> getListaEmpleadosNovedad() {
        if (listaEmpleadosNovedad == null) {
            RequestContext context = RequestContext.getCurrentInstance();
            listaEmpleadosNovedad = administrarNovedadesVacaciones.empleadosVacaciones();
            empleadoSeleccionado = listaEmpleadosNovedad.get(0);

            context.update("form:datosEmpleados");

        }
        return listaEmpleadosNovedad;
    }

    public void setListaEmpleadosNovedad(List<Empleados> listaEmpleadosNovedad) {
        this.listaEmpleadosNovedad = listaEmpleadosNovedad;
    }

    public List<Empleados> getFiltradosListaEmpleadosNovedad() {
        return filtradosListaEmpleadosNovedad;
    }

    public void setFiltradosListaEmpleadosNovedad(List<Empleados> filtradosListaEmpleadosNovedad) {
        this.filtradosListaEmpleadosNovedad = filtradosListaEmpleadosNovedad;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    //LOV EMPLEADOS
    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesVacaciones.empleadosVacaciones();

        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Empleados> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<Empleados> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public Empleados getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(Empleados seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<NovedadesSistema> getListaNovedades() {
        if (listaNovedades == null) {
            listaNovedades = administrarNovedadesSistema.vacacionesEmpleado(empleadoSeleccionado.getSecuencia());
        }

        return listaNovedades;
    }

    public void setListaNovedades(List<NovedadesSistema> listaNovedades) {
        this.listaNovedades = listaNovedades;
    }

    public List<NovedadesSistema> getListaNovedadesCrear() {
        return listaNovedadesCrear;
    }

    public void setListaNovedadesCrear(List<NovedadesSistema> listaNovedadesCrear) {
        this.listaNovedadesCrear = listaNovedadesCrear;
    }

    public List<NovedadesSistema> getListaNovedadesModificar() {
        return listaNovedadesModificar;
    }

    public void setListaNovedadesModificar(List<NovedadesSistema> listaNovedadesModificar) {
        this.listaNovedadesModificar = listaNovedadesModificar;
    }

    public List<NovedadesSistema> getListaNovedadesBorrar() {
        return listaNovedadesBorrar;
    }

    public void setListaNovedadesBorrar(List<NovedadesSistema> listaNovedadesBorrar) {
        this.listaNovedadesBorrar = listaNovedadesBorrar;
    }

    public NovedadesSistema getEditarNovedades() {
        return editarNovedades;
    }

    public void setEditarNovedades(NovedadesSistema editarNovedades) {
        this.editarNovedades = editarNovedades;
    }

    public List<NovedadesSistema> getFiltradosListaNovedades() {
        return filtradosListaNovedades;
    }

    public void setFiltradosListaNovedades(List<NovedadesSistema> filtradosListaNovedades) {
        this.filtradosListaNovedades = filtradosListaNovedades;
    }

    public NovedadesSistema getNuevaNovedad() {
        return nuevaNovedad;
    }

    public void setNuevaNovedad(NovedadesSistema nuevaNovedad) {
        this.nuevaNovedad = nuevaNovedad;
    }

    public List<Vacaciones> getListaPeriodos() {
        if (listaPeriodos == null) {
            listaPeriodos = administrarNovedadesSistema.periodosEmpleado(secuenciaEmpleado);
        }
        return listaPeriodos;
    }

    public void setListaPeriodos(List<Vacaciones> listaPeriodos) {
        this.listaPeriodos = listaPeriodos;
    }

    public List<Vacaciones> getFiltradoslistaPeriodos() {
        return filtradoslistaPeriodos;
    }

    public void setFiltradoslistaPeriodos(List<Vacaciones> filtradoslistaPeriodos) {
        this.filtradoslistaPeriodos = filtradoslistaPeriodos;
    }

    public Vacaciones getSeleccionPeriodo() {
        return seleccionPeriodo;
    }

    public void setSeleccionPeriodo(Vacaciones seleccionPeriodo) {
        this.seleccionPeriodo = seleccionPeriodo;
    }

    public NovedadesSistema getDuplicarNovedad() {
        return duplicarNovedad;
    }

    public void setDuplicarNovedad(NovedadesSistema duplicarNovedad) {
        this.duplicarNovedad = duplicarNovedad;
    }

    public Boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(Boolean readonly) {
        this.readonly = readonly;
    }

    public BigInteger getDiasTotales() {
        return diasTotales;
    }

    public void setDiasTotales(BigInteger diasTotales) {
        this.diasTotales = diasTotales;
    }

    public short getDiasAplazadosTotal() {
        return diasAplazadosTotal;
    }

    public void setDiasAplazadosTotal(short diasAplazadosTotal) {
        this.diasAplazadosTotal = diasAplazadosTotal;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public NovedadesSistema getNovedadSeleccionada() {
        return novedadSeleccionada;
    }

    public void setNovedadSeleccionada(NovedadesSistema novedadSeleccionada) {
        this.novedadSeleccionada = novedadSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

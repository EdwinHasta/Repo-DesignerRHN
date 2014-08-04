package Controlador;

import Entidades.DetallesFormulas;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlComprobantes implements Serializable {

    @EJB
    AdministrarComprobantesInterface administrarComprobantes;

    private List<Parametros> listaParametros;
    private Parametros parametroActual;
    private ParametrosEstructuras parametroEstructura;
    private List<Parametros> listaParametrosLOV;
    private List<Parametros> filtradoListaParametrosLOV;
    private Parametros parametroSeleccionado;
    //SOLUCIONES NODOS EMPLEADO
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    //SOLUCIONES NODOS EMPLEADOR
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;
    //REGISTRO ACTUAL
    private int registroActual, index, tablaActual;
    //OTROS
    private boolean aceptar, mostrarTodos;
    private Locale locale = new Locale("es", "CO");
    private NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    //SUBTOTALES
    private BigDecimal subtotalPago, subtotalDescuento, subtotalPasivo, subtotalGasto, neto;
    private String Pago, Descuento, Pasivo, Gasto, netoTotal;
    //FILTRADO
    private Column codigoSNE, descipcionSNE, unidadSNE, pagoSNE, descuentoSNE, terceroSNE, fechaHastaSNE, debitoSNE, centroCostoDSNE, creditoSNE, centroCostoCSNE, saldoSNE, fechaDesdeSNE, fechaPagoSNE, FechaModificacioSNE;
    private Column codigoSNER, descipcionSNER, unidadSNER, pasivoSNER, gastoSNER, terceroSNER, fechaHastaSNER, debitoSNER, centroCostoDSNER, creditoSNER, centroCostoCSNER, saldoSNER, fechaDesdeSNER, fechaPagoSNER, FechaModificacioSNER;
    private int bandera;
    private String altoScrollSolucionesNodosEmpleado, altoScrollSolucionesNodosEmpleador;
    //PARCIALES 
    private String parcialesSolucionNodos;
    //DETALLES FORMULAS
    private List<DetallesFormulas> listaDetallesFormulas;
    //FORMATO FECHAS
    private SimpleDateFormat formatoFecha;
    private boolean estadoBtnArriba, estadoBtnAbajo;
    //
    private String infoRegistroEmpleado;

    public ControlComprobantes() {
        registroActual = 0;
        aceptar = true;
        mostrarTodos = true;
        subtotalPago = new BigDecimal(0);
        subtotalDescuento = new BigDecimal(0);
        subtotalPasivo = new BigDecimal(0);
        subtotalGasto = new BigDecimal(0);
        bandera = 0;
        altoScrollSolucionesNodosEmpleado = "95";
        altoScrollSolucionesNodosEmpleador = "95";
        listaDetallesFormulas = null;
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        estadoBtnArriba = false;
        estadoBtnAbajo = false;
        parametroActual = null;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarComprobantes.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void anteriorEmpleado() {
        if (registroActual > 0) {
            registroActual--;
            parametroActual = listaParametros.get(registroActual);
            listaSolucionesNodosEmpleado = null;
            listaSolucionesNodosEmpleador = null;
            getListaSolucionesNodosEmpleado();
            getListaSolucionesNodosEmpleador();
            if (registroActual == 0) {
                estadoBtnArriba = true;
            }
            if (registroActual < (listaParametros.size() - 1)) {
                estadoBtnAbajo = false;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:panelInf");
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
            context.update("form:btnArriba");
            context.update("form:btnAbajo");
        }
    }

    public void siguienteEmpleado() {
        if (registroActual < (listaParametros.size() - 1)) {
            registroActual++;
            parametroActual = listaParametros.get(registroActual);
            listaSolucionesNodosEmpleado = null;
            listaSolucionesNodosEmpleador = null;
            getListaSolucionesNodosEmpleado();
            getListaSolucionesNodosEmpleador();
            if (registroActual > 0) {
                estadoBtnArriba = false;
            }
            if (registroActual == (listaParametros.size() - 1)) {
                estadoBtnAbajo = true;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:panelInf");
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
            context.update("form:btnArriba");
            context.update("form:btnAbajo");
        }
    }

    public void seleccionarEmpleado() {
        listaParametros.clear();
        listaParametros.add(parametroSeleccionado);
        parametroActual = parametroSeleccionado;
        registroActual = 0;
        filtradoListaParametrosLOV = null;
        parametroSeleccionado = null;
        aceptar = true;
        mostrarTodos = false;
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        getListaSolucionesNodosEmpleado();
        getListaSolucionesNodosEmpleador();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelInf");
        context.update("form:datosSolucionesNodosEmpleado");
        context.update("form:datosSolucionesNodosEmpleador");

        context.update("formularioDialogos:buscarEmpleadoDialogo");
        context.update("formularioDialogos:lovEmpleados");
        context.update("formularioDialogos:aceptarP");
        context.reset("formularioDialogos:lovEmpleados:globalFilter");
        context.execute("buscarEmpleadoDialogo.hide()");
    }

    public void cancelarSeleccionEmpleado() {
        filtradoListaParametrosLOV = null;
        parametroSeleccionado = null;
        aceptar = true;
    }

    public void mostarTodosEmpleados() {
        registroActual = 0;
        listaParametros = null;
        parametroActual = null;
        listaParametrosLOV = null;
        getParametroActual();
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        getListaSolucionesNodosEmpleado();
        getListaSolucionesNodosEmpleador();
        mostrarTodos = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelInf");
        context.update("form:datosSolucionesNodosEmpleado");
        context.update("form:datosSolucionesNodosEmpleador");
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            //SOLUCIONES NODOS EMPLEADO
            codigoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:codigoSNE");
            codigoSNE.setFilterStyle("width: 40px");
            descipcionSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:descipcionSNE");
            descipcionSNE.setFilterStyle("width: 170px");
            unidadSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:unidadSNE");
            unidadSNE.setFilterStyle("width: 30px");
            pagoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:pagoSNE");
            pagoSNE.setFilterStyle("");
            descuentoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:descuentoSNE");
            descuentoSNE.setFilterStyle("");
            terceroSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:terceroSNE");
            terceroSNE.setFilterStyle("width: 180px");
            fechaHastaSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaHastaSNE");
            fechaHastaSNE.setFilterStyle("width: 40px");
            debitoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:debitoSNE");
            debitoSNE.setFilterStyle("width: 50px");
            centroCostoDSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:centroCostoDSNE");
            centroCostoDSNE.setFilterStyle("width: 80px");
            creditoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:creditoSNE");
            creditoSNE.setFilterStyle("width: 50px");
            centroCostoCSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:centroCostoCSNE");
            centroCostoCSNE.setFilterStyle("width: 80px");
            saldoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:saldoSNE");
            saldoSNE.setFilterStyle("width: 80px");
            fechaDesdeSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaDesdeSNE");
            fechaDesdeSNE.setFilterStyle("width: 40px");
            fechaPagoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaPagoSNE");
            fechaPagoSNE.setFilterStyle("width: 40px");
            FechaModificacioSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:FechaModificacioSNE");
            FechaModificacioSNE.setFilterStyle("width: 70px");

            //SOLUCIONES NODOS EMPLEADOR
            codigoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:codigoSNER");
            codigoSNER.setFilterStyle("width: 40px");
            descipcionSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:descipcionSNER");
            descipcionSNER.setFilterStyle("width: 170px");
            unidadSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:unidadSNER");
            unidadSNER.setFilterStyle("width: 30px");
            pasivoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:pasivoSNER");
            pasivoSNER.setFilterStyle("");
            gastoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:gastoSNER");
            gastoSNER.setFilterStyle("");
            terceroSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:terceroSNER");
            terceroSNER.setFilterStyle("width: 180px");
            fechaHastaSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaHastaSNER");
            fechaHastaSNER.setFilterStyle("width: 40px");
            debitoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:debitoSNER");
            debitoSNER.setFilterStyle("width: 50px");
            centroCostoDSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:centroCostoDSNER");
            centroCostoDSNER.setFilterStyle("width: 80px");
            creditoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:creditoSNER");
            creditoSNER.setFilterStyle("width: 50px");
            centroCostoCSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:centroCostoCSNER");
            centroCostoCSNER.setFilterStyle("width: 80px");
            saldoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:saldoSNER");
            saldoSNER.setFilterStyle("width: 80px");
            fechaDesdeSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaDesdeSNER");
            fechaDesdeSNER.setFilterStyle("width: 40px");
            fechaPagoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaPagoSNER");
            fechaPagoSNER.setFilterStyle("width: 40px");
            FechaModificacioSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:FechaModificacioSNER");
            FechaModificacioSNER.setFilterStyle("width: 70px");

            altoScrollSolucionesNodosEmpleado = "72";
            altoScrollSolucionesNodosEmpleador = "72";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
            bandera = 1;

        } else if (bandera == 1) {
            //SOLUCIONES NODOS EMPLEADO
            codigoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:codigoSNE");
            codigoSNE.setFilterStyle("display: none; visibility: hidden;");
            descipcionSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:descipcionSNE");
            descipcionSNE.setFilterStyle("display: none; visibility: hidden;");
            unidadSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:unidadSNE");
            unidadSNE.setFilterStyle("display: none; visibility: hidden;");
            pagoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:pagoSNE");
            pagoSNE.setFilterStyle("display: none; visibility: hidden;");
            descuentoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:descuentoSNE");
            descuentoSNE.setFilterStyle("display: none; visibility: hidden;");
            terceroSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:terceroSNE");
            terceroSNE.setFilterStyle("display: none; visibility: hidden;");
            fechaHastaSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaHastaSNE");
            fechaHastaSNE.setFilterStyle("display: none; visibility: hidden;");
            debitoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:debitoSNE");
            debitoSNE.setFilterStyle("display: none; visibility: hidden;");
            centroCostoDSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:centroCostoDSNE");
            centroCostoDSNE.setFilterStyle("display: none; visibility: hidden;");
            creditoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:creditoSNE");
            creditoSNE.setFilterStyle("display: none; visibility: hidden;");
            centroCostoCSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:centroCostoCSNE");
            centroCostoCSNE.setFilterStyle("display: none; visibility: hidden;");
            saldoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:saldoSNE");
            saldoSNE.setFilterStyle("display: none; visibility: hidden;");
            fechaDesdeSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaDesdeSNE");
            fechaDesdeSNE.setFilterStyle("display: none; visibility: hidden;");
            fechaPagoSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:fechaPagoSNE");
            fechaPagoSNE.setFilterStyle("display: none; visibility: hidden;");
            FechaModificacioSNE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleado:FechaModificacioSNE");
            FechaModificacioSNE.setFilterStyle("display: none; visibility: hidden;");

            //SOLUCIONES NODOS EMPLEADOR
            codigoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:codigoSNER");
            codigoSNER.setFilterStyle("display: none; visibility: hidden;");
            descipcionSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:descipcionSNER");
            descipcionSNER.setFilterStyle("display: none; visibility: hidden;");
            unidadSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:unidadSNER");
            unidadSNER.setFilterStyle("display: none; visibility: hidden;");
            pasivoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:pasivoSNER");
            pasivoSNER.setFilterStyle("display: none; visibility: hidden;");
            gastoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:gastoSNER");
            gastoSNER.setFilterStyle("display: none; visibility: hidden;");
            terceroSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:terceroSNER");
            terceroSNER.setFilterStyle("display: none; visibility: hidden;");
            fechaHastaSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaHastaSNER");
            fechaHastaSNER.setFilterStyle("display: none; visibility: hidden;");
            debitoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:debitoSNER");
            debitoSNER.setFilterStyle("display: none; visibility: hidden;");
            centroCostoDSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:centroCostoDSNER");
            centroCostoDSNER.setFilterStyle("display: none; visibility: hidden;");
            creditoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:creditoSNER");
            creditoSNER.setFilterStyle("display: none; visibility: hidden;");
            centroCostoCSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:centroCostoCSNER");
            centroCostoCSNER.setFilterStyle("display: none; visibility: hidden;");
            saldoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:saldoSNER");
            saldoSNER.setFilterStyle("display: none; visibility: hidden;");
            fechaDesdeSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaDesdeSNER");
            fechaDesdeSNER.setFilterStyle("display: none; visibility: hidden;");
            fechaPagoSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:fechaPagoSNER");
            fechaPagoSNER.setFilterStyle("display: none; visibility: hidden;");
            FechaModificacioSNER = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionesNodosEmpleador:FechaModificacioSNER");
            FechaModificacioSNER.setFilterStyle("display: none; visibility: hidden;");

            altoScrollSolucionesNodosEmpleado = "95";
            altoScrollSolucionesNodosEmpleador = "95";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
            bandera = 0;
            filtradolistaSolucionesNodosEmpleado = null;
        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla;
        Exporter exporter = new ExportarPDFTablasAnchas();
        FacesContext context = FacesContext.getCurrentInstance();
        tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSolucionesNodosEmpleadoExportar");
        exporter.export(context, tabla, "SolucionesNodosEmpleadoPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSolucionesNodosEmpleadorExportar");
        exporter.export(context, tabla, "SolucionesNodosEmpleadorPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla;
        Exporter exporter = new ExportarXLS();
        FacesContext context = FacesContext.getCurrentInstance();
        tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSolucionesNodosEmpleadoExportar");
        exporter.export(context, tabla, "SolucionesNodosEmpleadoXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    //PARCIALES CONCEPTO
    public void parcialSolucionNodo(int indice, int tabla) {
        index = indice;
        tablaActual = tabla;
        if (tabla == 0) {
            parcialesSolucionNodos = listaSolucionesNodosEmpleado.get(indice).getParciales();
        } else if (tabla == 1) {
            parcialesSolucionNodos = listaSolucionesNodosEmpleador.get(indice).getParciales();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:parcialesConcepto");
        context.execute("parcialesConcepto.show();");
    }

    public void verDetallesFormula() {
        listaDetallesFormulas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:detallesFormulas");
        context.execute("detallesFormulas.show();");
    }

    public void salir() {
        parametroActual = null;
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        parametroEstructura = null;
        registroActual = 0;
        estadoBtnArriba = false;
        estadoBtnAbajo = false;
    }

    public void activarAceptar() {
        aceptar = false;
    }
    //GETTER AND SETTER

    public List<Parametros> getListaParametros() {
        listaParametros = administrarComprobantes.consultarParametrosComprobantesActualUsuario();
        if (listaParametros == null || listaParametros.isEmpty() || listaParametros.size() == 1) {
            estadoBtnArriba = true;
            estadoBtnAbajo = true;
        } else {
            estadoBtnArriba = false;
            estadoBtnAbajo = false;
        }
        return listaParametros;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public Parametros getParametroActual() {
        if (parametroActual == null) {
            getListaParametros();
            if (listaParametros != null && !listaParametros.isEmpty()) {
                parametroActual = listaParametros.get(registroActual);
            }
        }
        return parametroActual;
    }

    public void setParametroActual(Parametros parametroActual) {
        this.parametroActual = parametroActual;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarComprobantes.consultarParametroEstructuraActualUsuario();
        }
        return parametroEstructura;
    }

    public void setParametroEstructura(ParametrosEstructuras parametroEstructura) {
        this.parametroEstructura = parametroEstructura;
    }

    public List<Parametros> getListaParametrosLOV() {

        listaParametrosLOV = administrarComprobantes.consultarParametrosComprobantesActualUsuario();

        return listaParametrosLOV;
    }

    public void setListaParametrosLOV(List<Parametros> listaParametrosLOV) {
        this.listaParametrosLOV = listaParametrosLOV;
    }

    public Parametros getParametroSeleccionado() {
        return parametroSeleccionado;
    }

    public void setParametroSeleccionado(Parametros parametroSeleccionado) {
        this.parametroSeleccionado = parametroSeleccionado;
    }

    public List<Parametros> getFiltradoListaParametrosLOV() {
        return filtradoListaParametrosLOV;
    }

    public void setFiltradoListaParametrosLOV(List<Parametros> filtradoListaParametrosLOV) {
        this.filtradoListaParametrosLOV = filtradoListaParametrosLOV;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public boolean isMostrarTodos() {
        return mostrarTodos;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleado() {
        //if (listaSolucionesNodosEmpleado == null && parametroActual != null) {
        if (parametroActual != null) {
            listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(parametroActual.getEmpleado().getSecuencia());
            if (listaSolucionesNodosEmpleado != null) {
                subtotalPago = new BigDecimal(0);
                subtotalDescuento = new BigDecimal(0);
                for (int i = 0; i < listaSolucionesNodosEmpleado.size(); i++) {
                    if (listaSolucionesNodosEmpleado.get(i).getTipo().equals("PAGO")) {
                        subtotalPago = subtotalPago.add(listaSolucionesNodosEmpleado.get(i).getValor());
                    } else {
                        subtotalDescuento = subtotalDescuento.add(listaSolucionesNodosEmpleado.get(i).getValor());
                    }
                }
                neto = subtotalPago.subtract(subtotalDescuento);
                Pago = nf.format(subtotalPago);
                Descuento = nf.format(subtotalDescuento);
                netoTotal = nf.format(neto);
            }
        }
        return listaSolucionesNodosEmpleado;
    }

    public void setListaSolucionesNodosEmpleado(List<SolucionesNodos> listaSolucionesNodosEmpleado) {
        this.listaSolucionesNodosEmpleado = listaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleado() {
        return filtradolistaSolucionesNodosEmpleado;
    }

    public void setFiltradolistaSolucionesNodosEmpleado(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado) {
        this.filtradolistaSolucionesNodosEmpleado = filtradolistaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleador() {
        //if (listaSolucionesNodosEmpleador == null && parametroActual != null) {
        if (parametroActual != null) {
            if (parametroActual.getEmpleado().getSecuencia() != null) {
                listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(parametroActual.getEmpleado().getSecuencia());
                if (listaSolucionesNodosEmpleador != null) {
                    subtotalPasivo = new BigDecimal(0);
                    subtotalGasto = new BigDecimal(0);
                    for (int i = 0; i < listaSolucionesNodosEmpleador.size(); i++) {
                        if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("PASIVO")) {
                            subtotalPasivo = subtotalPasivo.add(listaSolucionesNodosEmpleador.get(i).getValor());
                        } else if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("GASTO")) {
                            subtotalGasto = subtotalGasto.add(listaSolucionesNodosEmpleador.get(i).getValor());
                        }
                    }
                    Pasivo = nf.format(subtotalPasivo);
                    Gasto = nf.format(subtotalGasto);
                }
            }
        }
        return listaSolucionesNodosEmpleador;
    }

    public void setListaSolucionesNodosEmpleador(List<SolucionesNodos> listaSolucionesNodosEmpleador) {
        this.listaSolucionesNodosEmpleador = listaSolucionesNodosEmpleador;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleador() {
        return filtradolistaSolucionesNodosEmpleador;
    }

    public void setFiltradolistaSolucionesNodosEmpleador(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador) {
        this.filtradolistaSolucionesNodosEmpleador = filtradolistaSolucionesNodosEmpleador;
    }

    public String getPago() {
        return Pago;
    }

    public String getDescuento() {
        return Descuento;
    }

    public String getPasivo() {
        return Pasivo;
    }

    public String getGasto() {
        return Gasto;
    }

    public String getNetoTotal() {
        return netoTotal;
    }

    public String getAltoScrollSolucionesNodosEmpleado() {
        return altoScrollSolucionesNodosEmpleado;
    }

    public String getAltoScrollSolucionesNodosEmpleador() {
        return altoScrollSolucionesNodosEmpleador;
    }

    public String getParcialesSolucionNodos() {
        return parcialesSolucionNodos;
    }

    public List<DetallesFormulas> getListaDetallesFormulas() {
        if (listaDetallesFormulas == null) {
            BigInteger secEmpleado = null, secProceso = null, secHistoriaFormula, secFormula = null;
            String fechaDesde = null, fechaHasta = null;
            if (tablaActual == 0) {
                if (listaSolucionesNodosEmpleado != null && !listaSolucionesNodosEmpleado.isEmpty()) {
                    secFormula = listaSolucionesNodosEmpleado.get(index).getFormula().getSecuencia();
                    fechaDesde = formatoFecha.format(listaSolucionesNodosEmpleado.get(index).getFechadesde());
                    fechaHasta = formatoFecha.format(listaSolucionesNodosEmpleado.get(index).getFechahasta());
                    secEmpleado = listaSolucionesNodosEmpleado.get(index).getEmpleado().getSecuencia();
                    secProceso = listaSolucionesNodosEmpleado.get(index).getProceso().getSecuencia();
                }
            } else if (tablaActual == 1) {
                if (listaSolucionesNodosEmpleador != null && !listaSolucionesNodosEmpleador.isEmpty()) {
                    secFormula = listaSolucionesNodosEmpleador.get(index).getFormula().getSecuencia();
                    fechaDesde = formatoFecha.format(listaSolucionesNodosEmpleador.get(index).getFechadesde());
                    fechaHasta = formatoFecha.format(listaSolucionesNodosEmpleador.get(index).getFechahasta());
                    secEmpleado = listaSolucionesNodosEmpleador.get(index).getEmpleado().getSecuencia();
                    secProceso = listaSolucionesNodosEmpleador.get(index).getProceso().getSecuencia();
                }
            }
            if (secFormula != null && fechaDesde != null) {
                secHistoriaFormula = administrarComprobantes.consultarHistoriaFormulaFormula(secFormula, fechaDesde);
                listaDetallesFormulas = administrarComprobantes.consultarDetallesFormulasEmpleado(secEmpleado, fechaDesde, fechaHasta, secProceso, secHistoriaFormula);
            }
        }
        return listaDetallesFormulas;
    }

    public void setListaDetallesFormulas(List<DetallesFormulas> listaDetallesFormulas) {
        this.listaDetallesFormulas = listaDetallesFormulas;
    }

    public boolean isEstadoBtnArriba() {
        return estadoBtnArriba;
    }

    public boolean isEstadoBtnAbajo() {
        return estadoBtnAbajo;
    }

    public String getInfoRegistroEmpleado() {
        getListaParametrosLOV();
        if (listaParametrosLOV != null) {
            infoRegistroEmpleado = "Cantidad de registros : " + listaParametrosLOV.size();
        } else {
            infoRegistroEmpleado = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleado;
    }

    public void setInfoRegistroEmpleado(String infoRegistroEmpleado) {
        this.infoRegistroEmpleado = infoRegistroEmpleado;
    }

}

package Controlador;

import Entidades.CentrosCostos;
import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Cuentas;
import Entidades.DetallesFormulas;
import Entidades.Empleados;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.Terceros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplComprobantesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlEmplComprobantes implements Serializable {

    @EJB
    AdministrarEmplComprobantesInterface administrarComprobantes;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private Empleados empleado;
    private BigInteger secuenciaEmpleado;
    //TABLA 1
    private List<Comprobantes> listaComprobantes;
    private List<Comprobantes> filtradolistaComprobantes;
    private Comprobantes seleccionComprobante;
    //TABLA 2
    private List<CortesProcesos> listaCortesProcesos;
    private List<CortesProcesos> filtradolistaCortesProcesos;
    private CortesProcesos seleccionCortesProcesos;
    //TABLA 3
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    private SolucionesNodos empleadoTablaSeleccionado;
    //TABLA 4
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;
    private SolucionesNodos empleadorTablaSeleccionado;
    //LOV PROCESOS
    private List<Procesos> listaProcesos;
    private Procesos procesoSelecionado;
    private List<Procesos> filtradoProcesos;
    //LOV TERCEROS
    private List<Terceros> listaTerceros;
    private Terceros TerceroSelecionado;
    private List<Terceros> filtradolistaTerceros;
    //LOV CUENTAS
    private List<Cuentas> lovCuentas;
    private List<Cuentas> filtrarLovCuentas;
    private Cuentas cuentaSeleccionada;
    //LOV CENTROSCOSTOS
    private List<CentrosCostos> lovCentrosCostos;
    private List<CentrosCostos> filtrarLovCentrosCostos;
    private CentrosCostos centroCostoSeleccionado;
    //
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int banderaComprobantes;
    private int banderaCortesProcesos;
    //Columnas Tablas
    DataTable tablaComprobantes, tablaCortesProcesos;
    private Column numeroComprobanteC, fechaC, fechaEntregaC;
    private Column fechaCorteCP, procesoCP;
    private String altoScrollComprobante, altoScrollCortesProcesos;
    //Otros
    private boolean aceptar;
    private int indexComprobante;
    private int indexCortesProcesos;
    private int indexSolucionesEmpleado;
    private int indexSolucionesEmpleador;
    private int index, tablaActual;
    private String tablaExportar, nombreArchivoExportar;
    //modificar
    private List<Comprobantes> listaComprobantesModificar;
    private List<CortesProcesos> listaCortesProcesosModificar;
    private List<SolucionesNodos> listaSolucionesNodosEmpleadoModificar;
    private List<SolucionesNodos> listaSolucionesNodosEmpleadorModificar;
    private boolean guardado;
    private boolean modificacionesComprobantes;
    private boolean modificacionesCortesProcesos;
    private boolean modificacionesSolucionesNodosEmpleado;
    private boolean modificacionesSolucionesNodosEmpleador;
    //crear
    private Comprobantes nuevoComprobante;
    private CortesProcesos nuevoCorteProceso;
    private List<Comprobantes> listaComprobantesCrear;
    private List<CortesProcesos> listaCortesProcesosCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Comprobantes> listaComprobantesBorrar;
    private List<CortesProcesos> listaCortesProcesosBorrar;
    //editar celda
    private Comprobantes editarComprobante;
    private CortesProcesos editarCorteProceso;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Comprobantes duplicarComprobante;
    private CortesProcesos duplicarCorteProceso;
    //AUTOCOMPLETAR
    private String Proceso, Tercero, CodigoCredito, CodigoDebito, CentroCostoDebito, CentroCostoCredito;
    //RASTRO*/
    private BigInteger secRegistro;
    private String nombreTabla;
    //INFORMATIVOS
    private int cualCelda, tipoListaComprobantes, tipoListaCortesProcesos, tipoListaSNEmpleado, tipoListaSNEmpleador, tipoTabla;
    //SUBTOTALES Y NETO
    private BigDecimal subtotalPago, subtotalDescuento, subtotalPasivo, subtotalGasto, neto;
    //PARCIALES 
    private String parcialesSolucionNodos;
    //DETALLES FORMULAS
    private List<DetallesFormulas> listaDetallesFormulas;
    //FORMATO FECHAS
    private SimpleDateFormat formatoFecha;
    //
    private String infoRegistroProceso, infoRegistroTercero, infoRegistroCuentaDebito, infoRegistroCuentaCredito, infoRegistroCentroCostoDebito, infoRegistroCentroCostoCredito;
    //
    private Date auxFechaEntregadoComprobante;

    public ControlEmplComprobantes() {
        permitirIndex = true;
        listaProcesos = new ArrayList<Procesos>();
        listaTerceros = new ArrayList<Terceros>();
        procesoSelecionado = new Procesos();
        seleccionComprobante = new Comprobantes();
        //Otros
        aceptar = true;
        //borrar 
        listaComprobantesBorrar = new ArrayList<Comprobantes>();
        listaCortesProcesosBorrar = new ArrayList<CortesProcesos>();
        //crear 
        listaComprobantesCrear = new ArrayList<Comprobantes>();
        listaCortesProcesosCrear = new ArrayList<CortesProcesos>();
        k = 0;
        //modificar 
        listaComprobantesModificar = new ArrayList<Comprobantes>();
        listaCortesProcesosModificar = new ArrayList<CortesProcesos>();
        listaSolucionesNodosEmpleadoModificar = new ArrayList<SolucionesNodos>();
        listaSolucionesNodosEmpleadorModificar = new ArrayList<SolucionesNodos>();
        modificacionesCortesProcesos = false;
        modificacionesComprobantes = false;
        //editar
        editarComprobante = new Comprobantes();
        editarCorteProceso = new CortesProcesos();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoListaComprobantes = 0;
        tipoListaCortesProcesos = 0;
        tipoListaSNEmpleado = 0;
        tipoListaSNEmpleador = 0;
        tipoTabla = 0;
        //guardar
        guardado = true;
        //Crear 
        nuevoComprobante = new Comprobantes();
        nuevoCorteProceso = new CortesProcesos();
        nuevoCorteProceso.setProceso(new Procesos());
        duplicarComprobante = new Comprobantes();
        secRegistro = null;
        tablaExportar = ":formExportar:datosComprobantesExportar";
        altoScrollComprobante = "61";
        altoScrollComprobante = "61";
        banderaComprobantes = 0;
        banderaCortesProcesos = 0;
        nombreTabla = "Comprobantes";
        subtotalPago = new BigDecimal(0);
        subtotalDescuento = new BigDecimal(0);
        subtotalPasivo = new BigDecimal(0);
        subtotalGasto = new BigDecimal(0);
        listaDetallesFormulas = null;
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarComprobantes.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        subtotalPago = new BigDecimal(0);
        subtotalDescuento = new BigDecimal(0);
        subtotalPasivo = new BigDecimal(0);
        subtotalGasto = new BigDecimal(0);
        secuenciaEmpleado = sec;
        empleado = administrarComprobantes.consultarEmpleado(secuenciaEmpleado);
        if (empleado != null) {
            listaComprobantes = administrarComprobantes.consultarComprobantesEmpleado(empleado.getSecuencia());
            if (listaComprobantes != null) {
                int tam = listaComprobantes.size();
                if (tam > 0) {
                    for (int i = 0; i < listaComprobantes.size(); i++) {
                        if (listaComprobantes.get(i).getFecha() == null) {
                            listaComprobantes.get(i).setReadOnlyFecha(false);
                        } else {
                            listaComprobantes.get(i).setReadOnlyFecha(true);
                        }
                        if (listaComprobantes.get(i).getFechaentregado() == null) {
                            listaComprobantes.get(i).setReadOnlyFechaEntregado(false);
                        } else {
                            listaComprobantes.get(i).setReadOnlyFechaEntregado(true);
                        }
                        if (listaComprobantes.get(i).getNumero() == null) {
                            listaComprobantes.get(i).setReadOnlyNumero(false);
                        } else {
                            listaComprobantes.get(i).setReadOnlyNumero(true);
                        }
                    }
                    seleccionComprobante = listaComprobantes.get(0);
                    listaCortesProcesos = administrarComprobantes.consultarCortesProcesosComprobante(seleccionComprobante.getSecuencia());
                    if (!listaCortesProcesos.isEmpty()) {
                        listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                        listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                        for (int i = 0; i < listaSolucionesNodosEmpleado.size(); i++) {
                            if (listaSolucionesNodosEmpleado.get(i).getTipo().equals("PAGO")) {
                                subtotalPago = subtotalPago.add(listaSolucionesNodosEmpleado.get(i).getValor());
                            } else {
                                subtotalDescuento = subtotalDescuento.add(listaSolucionesNodosEmpleado.get(i).getValor());
                            }
                        }
                        for (int i = 0; i < listaSolucionesNodosEmpleador.size(); i++) {
                            if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("PASIVO")) {
                                subtotalPasivo = subtotalPasivo.add(listaSolucionesNodosEmpleador.get(i).getValor());
                            } else if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("GASTO")) {
                                subtotalGasto = subtotalGasto.add(listaSolucionesNodosEmpleador.get(i).getValor());
                            }
                        }
                        neto = subtotalPago.subtract(subtotalDescuento);
                    }
                }
            }
        }
    }

    public void posicionComprobante() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndiceComprobantes(indice, columna);
    }

    public void cambiarIndiceComprobantes(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (modificacionesSolucionesNodosEmpleado == false && modificacionesSolucionesNodosEmpleador == false) {
            if (modificacionesCortesProcesos == false) {
                if (permitirIndex == true) {
                    subtotalPago = new BigDecimal(0);
                    subtotalDescuento = new BigDecimal(0);
                    subtotalPasivo = new BigDecimal(0);
                    subtotalGasto = new BigDecimal(0);
                    indexComprobante = indice;
                    cualCelda = celda;
                    tipoTabla = 0;
                    nombreTabla = "Comprobantes";
                    seleccionComprobante = listaComprobantes.get(indexComprobante);
                    if (tipoListaComprobantes == 0) {
                        secRegistro = listaComprobantes.get(indexComprobante).getSecuencia();
                        auxFechaEntregadoComprobante = listaComprobantes.get(indexComprobante).getFechaentregado();
                        listaCortesProcesos = administrarComprobantes.consultarCortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia());
                        if (!listaCortesProcesos.isEmpty()) {
                            listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                            listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                        } else {
                            listaSolucionesNodosEmpleado = null;
                            listaSolucionesNodosEmpleador = null;
                        }
                    } else {
                        secRegistro = filtradolistaComprobantes.get(indexComprobante).getSecuencia();
                        auxFechaEntregadoComprobante = filtradolistaComprobantes.get(indexComprobante).getFechaentregado();
                        listaCortesProcesos = administrarComprobantes.consultarCortesProcesosComprobante(filtradolistaComprobantes.get(indexComprobante).getSecuencia());
                        if (!listaCortesProcesos.isEmpty()) {
                            listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                            listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                        } else {
                            listaSolucionesNodosEmpleado = null;
                            listaSolucionesNodosEmpleador = null;
                        }
                    }
                    if (banderaCortesProcesos == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();

                        fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
                        fechaCorteCP.setFilterStyle("display: none; visibility: hidden;");
                        procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
                        procesoCP.setFilterStyle("display: none; visibility: hidden;");
                        altoScrollComprobante = "61";
                        RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
                        banderaCortesProcesos = 0;
                        filtradolistaCortesProcesos = null;
                        tipoListaCortesProcesos = 0;
                    }
                    if (!listaSolucionesNodosEmpleado.isEmpty()) {
                        for (int i = 0; i < listaSolucionesNodosEmpleado.size(); i++) {
                            if (listaSolucionesNodosEmpleado.get(i).getTipo().equals("PAGO")) {
                                subtotalPago = subtotalPago.add(listaSolucionesNodosEmpleado.get(i).getValor());
                            } else {
                                subtotalDescuento = subtotalDescuento.add(listaSolucionesNodosEmpleado.get(i).getValor());
                            }
                        }
                    }
                    if (!listaSolucionesNodosEmpleador.isEmpty()) {
                        for (int i = 0; i < listaSolucionesNodosEmpleador.size(); i++) {
                            if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("PASIVO")) {
                                subtotalPasivo = subtotalPasivo.add(listaSolucionesNodosEmpleador.get(i).getValor());
                            } else if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("GASTO")) {
                                subtotalGasto = subtotalGasto.add(listaSolucionesNodosEmpleador.get(i).getValor());
                            }
                        }
                    }
                    neto = subtotalPago.subtract(subtotalDescuento);
                    tablaExportar = ":formExportar:datosComprobantesExportar";
                    nombreArchivoExportar = "ComprobantesXML";
                    context.update("form:datosCortesProcesos");
                    context.update("form:tablaInferiorIzquierda");
                    context.update("form:tablaInferiorDerecha");
                    context.update("form:tablaInferiorIzquierdaEM");
                    context.update("form:tablaInferiorDerechaEM");
                    context.update("form:exportarXML");
                    context.update("form:subTotalPago");
                    context.update("form:subTotalDescuento");
                    context.update("form:subTotalPasivo");
                    context.update("form:subTotalGasto");
                    context.update("form:neto");
                }
            } else {
                context.execute("confirmarGuardar.show();");
            }
        } else {
            context.execute("confirmarGuardar.show();");
        }
    }

    public void cambiarIndiceCortesProcesos(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (modificacionesSolucionesNodosEmpleado == false && modificacionesSolucionesNodosEmpleador == false) {
            if (permitirIndex == true) {
                subtotalPago = new BigDecimal(0);
                subtotalDescuento = new BigDecimal(0);
                subtotalPasivo = new BigDecimal(0);
                subtotalGasto = new BigDecimal(0);
                indexCortesProcesos = indice;
                cualCelda = celda;
                tipoTabla = 1;
                nombreTabla = "CortesProcesos";
                if (tipoListaCortesProcesos == 0) {
                    secRegistro = listaCortesProcesos.get(indexCortesProcesos).getSecuencia();
                    if (cualCelda == 1) {
                        Proceso = listaCortesProcesos.get(indexCortesProcesos).getProceso().getDescripcion();
                    }
                    listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                    listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                } else {
                    secRegistro = filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia();
                    if (cualCelda == 1) {
                        Proceso = filtradolistaCortesProcesos.get(indexCortesProcesos).getProceso().getDescripcion();
                    }
                    listaSolucionesNodosEmpleado = administrarComprobantes.consultarSolucionesNodosEmpleado(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                    listaSolucionesNodosEmpleador = administrarComprobantes.consultarSolucionesNodosEmpleador(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                }
            }
            if (banderaComprobantes == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                context.update("form:datosComprobantes");
                banderaComprobantes = 0;
                filtradolistaComprobantes = null;
                tipoListaComprobantes = 0;
            }
            if (!listaSolucionesNodosEmpleado.isEmpty()) {
                for (int i = 0; i < listaSolucionesNodosEmpleado.size(); i++) {
                    if (listaSolucionesNodosEmpleado.get(i).getTipo().equals("PAGO")) {
                        subtotalPago = subtotalPago.add(listaSolucionesNodosEmpleado.get(i).getValor());
                    } else {
                        subtotalDescuento = subtotalDescuento.add(listaSolucionesNodosEmpleado.get(i).getValor());
                    }
                }
            }
            if (!listaSolucionesNodosEmpleador.isEmpty()) {
                for (int i = 0; i < listaSolucionesNodosEmpleador.size(); i++) {
                    if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("PASIVO")) {
                        subtotalPasivo = subtotalPasivo.add(listaSolucionesNodosEmpleador.get(i).getValor());
                    } else if (listaSolucionesNodosEmpleador.get(i).getTipo().equals("GASTO")) {
                        subtotalGasto = subtotalGasto.add(listaSolucionesNodosEmpleador.get(i).getValor());
                    }
                }
            }
            neto = subtotalPago.subtract(subtotalDescuento);
            tablaExportar = ":formExportar:datosCortesProcesosExportar";
            nombreArchivoExportar = "CortesProcesosXML";
            context.update("form:tablaInferiorIzquierda");
            context.update("form:tablaInferiorDerecha");
            context.update("form:tablaInferiorIzquierdaEM");
            context.update("form:tablaInferiorDerechaEM");
            context.update("form:exportarXML");
            context.update("form:subTotalPago");
            context.update("form:subTotalDescuento");
            context.update("form:subTotalPasivo");
            context.update("form:subTotalGasto");
            context.update("form:neto");
        } else {
            context.execute("confirmarGuardar.show();");
        }
    }

    public void cambiarIndiceSolucionesNodosEmpleado(int indice, int celda) {
        if (permitirIndex == true) {
            indexSolucionesEmpleado = indice;
            cualCelda = celda;
            tipoTabla = 2;
            nombreTabla = "SolucionesNodos";
            tablaExportar = ":formExportar:solucionesNodoEmpleadoExportar";
            if (tipoListaSNEmpleado == 0) {
                secRegistro = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getSecuencia();
                if (cualCelda == 7) {
                    Tercero = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getNit().getNombre();
                }
                if (cualCelda == 8) {
                    CodigoDebito = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCuentad().getCodigo();
                }
                if (cualCelda == 9) {
                    CentroCostoDebito = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCentrocostod().getNombre();
                }
                if (cualCelda == 10) {
                    CodigoCredito = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCuentac().getCodigo();
                }
                if (cualCelda == 11) {
                    CentroCostoCredito = listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCentrocostoc().getNombre();
                }
            } else {
                secRegistro = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getSecuencia();
                if (cualCelda == 7) {
                    Tercero = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getNit().getNombre();
                }
                if (cualCelda == 8) {
                    CodigoDebito = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCuentad().getCodigo();
                }
                if (cualCelda == 9) {
                    CentroCostoDebito = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCentrocostod().getNombre();
                }
                if (cualCelda == 10) {
                    CodigoCredito = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCuentac().getCodigo();
                }
                if (cualCelda == 11) {
                    CentroCostoCredito = filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).getCentrocostoc().getNombre();
                }
            }
        }
        /* if (banderaComprobantes == 1) {
         numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
         numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
         fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
         fechaC.setFilterStyle("display: none; visibility: hidden;");
         fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
         fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
         altoScrollComprobante = "61";
         RequestContext.getCurrentInstance().update("form:datosComprobantes");
         banderaComprobantes = 0;
         filtradolistaComprobantes = null;
         tipoListaComprobantes = 0;
         }*/
        //tablaExportar = ":formExportar:datosCortesProcesosExportar";
        nombreArchivoExportar = "SolucionesNodosEmpleadoXML";
    }

    public void cambiarIndiceSolucionesNodosEmpleador(int indice, int celda) {
        if (permitirIndex == true) {
            indexSolucionesEmpleador = indice;
            cualCelda = celda;
            tipoTabla = 3;
            nombreTabla = "SolucionesNodos";
            tablaExportar = ":formExportar:solucionesNodoEmpleadorExportar";
            if (tipoListaSNEmpleador == 0) {
                secRegistro = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getSecuencia();
                if (cualCelda == 7) {
                    Tercero = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getNit().getNombre();
                }
                if (cualCelda == 8) {
                    CodigoDebito = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCuentad().getCodigo();
                }
                if (cualCelda == 9) {
                    CentroCostoDebito = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCentrocostod().getNombre();
                }
                if (cualCelda == 10) {
                    CodigoCredito = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCuentac().getCodigo();
                }
                if (cualCelda == 11) {
                    CentroCostoCredito = listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCentrocostoc().getNombre();
                }
            } else {
                secRegistro = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getSecuencia();
                if (cualCelda == 7) {
                    Tercero = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getNit().getNombre();
                }
                if (cualCelda == 8) {
                    CodigoDebito = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCuentad().getCodigo();
                }
                if (cualCelda == 9) {
                    CentroCostoDebito = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCentrocostod().getNombre();
                }
                if (cualCelda == 10) {
                    CodigoCredito = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCuentac().getCodigo();
                }
                if (cualCelda == 11) {
                    CentroCostoCredito = filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).getCentrocostoc().getNombre();
                }
            }
        }
        /* if (banderaComprobantes == 1) {
         numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
         numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
         fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
         fechaC.setFilterStyle("display: none; visibility: hidden;");
         fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
         fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
         altoScrollComprobante = "61";
         RequestContext.getCurrentInstance().update("form:datosComprobantes");
         banderaComprobantes = 0;
         filtradolistaComprobantes = null;
         tipoListaComprobantes = 0;
         }*/
        //tablaExportar = ":formExportar:datosCortesProcesosExportar";
        nombreArchivoExportar = "SolucionesNodosEmpleadorXML";
    }

    public void modificarComprobantesFechaEntregado(int indice, int tipoEvento, int celda) {
        indexComprobante = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        Comprobantes aux = null;
        if (tipoListaComprobantes == 0) {
            aux = listaComprobantes.get(indice);
        } else {
            aux = filtradolistaComprobantes.get(indice);
        }
        if (aux.getFechaentregado() == null && aux.isCheckEntregado() == true) {
            if (tipoListaComprobantes == 0) {
                listaComprobantes.get(indice).setFechaentregado(auxFechaEntregadoComprobante);
            } else {
                filtradolistaComprobantes.get(indice).setFechaentregado(auxFechaEntregadoComprobante);
            }
            context.execute("errorFechaEntregaNull.show()");
        } else {
            if (tipoEvento == 1 && aux.getFechaentregado() == null) {
                cambiarIndiceComprobantes(indice, celda);
            } else {
                modificarComprobantes(indice);
            }
        }
    }

    public void modificarComprobantes(int indice) {

        if (tipoListaComprobantes == 0) {
            if (!listaComprobantesCrear.contains(listaComprobantes.get(indice))) {
                if (listaComprobantesModificar.isEmpty()) {
                    listaComprobantesModificar.add(listaComprobantes.get(indice));
                } else if (!listaComprobantesModificar.contains(listaComprobantes.get(indice))) {
                    listaComprobantesModificar.add(listaComprobantes.get(indice));
                }
            }

        } else {
            if (!listaComprobantesCrear.contains(filtradolistaComprobantes.get(indice))) {
                if (listaComprobantesModificar.isEmpty()) {
                    listaComprobantesModificar.add(filtradolistaComprobantes.get(indice));
                } else if (!listaComprobantesModificar.contains(filtradolistaComprobantes.get(indice))) {
                    listaComprobantesModificar.add(filtradolistaComprobantes.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        }
        indexComprobante = -1;
        secRegistro = null;
        tipoTabla = -1;
        modificacionesComprobantes = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosComprobantes");
    }

    public void modificarCortesProcesos(int indice, String confirmarCambio, String valorConfirmar) {
        indexCortesProcesos = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equals("N")) {
            if (tipoListaCortesProcesos == 0) {
                if (!listaCortesProcesosCrear.contains(listaCortesProcesos.get(indice))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indice));
                    } else if (!listaCortesProcesosModificar.contains(listaCortesProcesos.get(indice))) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indice));
                    }
                }
            } else {
                if (!listaCortesProcesosCrear.contains(filtradolistaCortesProcesos.get(indice))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indice));
                    } else if (!listaCortesProcesosModificar.contains(filtradolistaCortesProcesos.get(indice))) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indice));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");

            }
            indexCortesProcesos = -1;
            secRegistro = null;
            tipoTabla = -1;
            modificacionesCortesProcesos = true;
            context.update("form:datosCortesProcesos");
        } else if (confirmarCambio.equalsIgnoreCase("PROCESO")) {
            if (tipoListaCortesProcesos == 0) {
                listaCortesProcesos.get(indice).getProceso().setDescripcion(Proceso);
            } else {
                filtradolistaCortesProcesos.get(indice).getProceso().setDescripcion(Proceso);
            }
            for (int i = 0; i < listaProcesos.size(); i++) {
                if (listaProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaCortesProcesos == 0) {
                    listaCortesProcesos.get(indice).setProceso(listaProcesos.get(indiceUnicoElemento));
                } else {
                    filtradolistaCortesProcesos.get(indice).setProceso(listaProcesos.get(indiceUnicoElemento));
                }
                listaProcesos.clear();
                getListaProcesos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaCortesProcesos == 0) {
                if (!listaCortesProcesosCrear.contains(listaCortesProcesos.get(indice))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indice));
                    } else if (!listaCortesProcesosModificar.contains(listaCortesProcesos.get(indice))) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indice));
                    }
                }
            } else {
                if (!listaCortesProcesosCrear.contains(filtradolistaCortesProcesos.get(indice))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indice));
                    } else if (!listaCortesProcesosModificar.contains(filtradolistaCortesProcesos.get(indice))) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indice));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");

            }
            modificacionesCortesProcesos = true;
            indexCortesProcesos = -1;
            secRegistro = null;
        }

        context.update("form:datosCortesProcesos");
    }

    //AUTOCOMPLETAR NUEVO Y DUPLICADO
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("PROCESO")) {
            if (tipoNuevo == 1) {
                Proceso = nuevoCorteProceso.getProceso().getDescripcion();
            } else if (tipoNuevo == 2) {
                Proceso = duplicarCorteProceso.getProceso().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROCESO")) {
            if (tipoNuevo == 1) {
                nuevoCorteProceso.getProceso().setDescripcion(Proceso);
            } else if (tipoNuevo == 2) {
                duplicarCorteProceso.getProceso().setDescripcion(Proceso);
            }
            for (int i = 0; i < listaProcesos.size(); i++) {
                if (listaProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCorteProceso.setProceso(listaProcesos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoProceso");
                } else if (tipoNuevo == 2) {
                    duplicarCorteProceso.setProceso(listaProcesos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicadoProceso");
                }
                listaProcesos.clear();
                getListaProcesos();
            } else {
                context.update("formularioDialogos:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoProceso");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicadoProceso");
                }
            }
        }
    }

    public void modificarSolucionesNodos(String tipoDato, int indice, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoDato.equals("TERCERO")) {
            if (tipoTabla == 2) {
                indexSolucionesEmpleado = indice;
                if (tipoListaSNEmpleado == 0) {
                    listaSolucionesNodosEmpleado.get(indice).getNit().setNombre(Tercero);
                } else {
                    filtradolistaSolucionesNodosEmpleado.get(indice).getNit().setNombre(Tercero);
                }
            } else if (tipoTabla == 3) {
                indexSolucionesEmpleador = indice;
                if (tipoListaSNEmpleador == 0) {
                    listaSolucionesNodosEmpleador.get(indice).getNit().setNombre(Tercero);
                } else {
                    filtradolistaSolucionesNodosEmpleador.get(indice).getNit().setNombre(Tercero);
                }
            }

            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoTabla == 2) {
                    if (tipoListaSNEmpleado == 0) {
                        listaSolucionesNodosEmpleado.get(indice).setNit(listaTerceros.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleado.get(indice).setNit(listaTerceros.get(indiceUnicoElemento));
                    }
                } else if (tipoTabla == 3) {
                    if (tipoListaSNEmpleador == 0) {
                        listaSolucionesNodosEmpleador.get(indice).setNit(listaTerceros.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleador.get(indice).setNit(listaTerceros.get(indiceUnicoElemento));
                    }
                }
                listaTerceros.clear();
                getListaTerceros();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:TercerosDialogo");
                context.execute("TercerosDialogo.show()");
            }
        }
        if (tipoDato.equals("CREDITO")) {
            if (tipoTabla == 2) {
                indexSolucionesEmpleado = indice;
                if (tipoListaSNEmpleado == 0) {
                    listaSolucionesNodosEmpleado.get(indice).getCuentac().setCodigo(CodigoCredito);
                } else {
                    filtradolistaSolucionesNodosEmpleado.get(indice).getCuentac().setCodigo(CodigoCredito);
                }
            } else if (tipoTabla == 3) {
                indexSolucionesEmpleador = indice;
                if (tipoListaSNEmpleador == 0) {
                    listaSolucionesNodosEmpleador.get(indice).getCuentac().setCodigo(CodigoCredito);
                } else {
                    filtradolistaSolucionesNodosEmpleador.get(indice).getCuentac().setCodigo(CodigoCredito);
                }
            }

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoTabla == 2) {
                    if (tipoListaSNEmpleado == 0) {
                        listaSolucionesNodosEmpleado.get(indice).setCuentac(lovCuentas.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleado.get(indice).setCuentac(lovCuentas.get(indiceUnicoElemento));
                    }
                } else if (tipoTabla == 3) {
                    if (tipoListaSNEmpleador == 0) {
                        listaSolucionesNodosEmpleador.get(indice).setCuentac(lovCuentas.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleador.get(indice).setCuentac(lovCuentas.get(indiceUnicoElemento));
                    }
                }
                lovCuentas.clear();
                getLovCuentas();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:CuentaCreditoDialogo");
                context.execute("CuentaCreditoDialogo.show()");
            }
        }
        if (tipoDato.equals("DEBITO")) {
            if (tipoTabla == 2) {
                indexSolucionesEmpleado = indice;
                if (tipoListaSNEmpleado == 0) {
                    listaSolucionesNodosEmpleado.get(indice).getCuentad().setCodigo(CodigoDebito);
                } else {
                    filtradolistaSolucionesNodosEmpleado.get(indice).getCuentad().setCodigo(CodigoDebito);
                }
            } else if (tipoTabla == 3) {
                indexSolucionesEmpleador = indice;
                if (tipoListaSNEmpleador == 0) {
                    listaSolucionesNodosEmpleador.get(indice).getCuentad().setCodigo(CodigoDebito);
                } else {
                    filtradolistaSolucionesNodosEmpleador.get(indice).getCuentad().setCodigo(CodigoDebito);
                }
            }

            for (int i = 0; i < lovCuentas.size(); i++) {
                if (lovCuentas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoTabla == 2) {
                    if (tipoListaSNEmpleado == 0) {
                        listaSolucionesNodosEmpleado.get(indice).setCuentad(lovCuentas.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleado.get(indice).setCuentad(lovCuentas.get(indiceUnicoElemento));
                    }
                } else if (tipoTabla == 3) {
                    if (tipoListaSNEmpleador == 0) {
                        listaSolucionesNodosEmpleador.get(indice).setCuentad(lovCuentas.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleador.get(indice).setCuentad(lovCuentas.get(indiceUnicoElemento));
                    }
                }
                lovCuentas.clear();
                getLovCuentas();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:CuentaDebitoDialogo");
                context.execute("CuentaDebitoDialogo.show()");
            }
        }
        if (tipoDato.equals("CENTROCOSTODEBITO")) {
            if (tipoTabla == 2) {
                indexSolucionesEmpleado = indice;
                if (tipoListaSNEmpleado == 0) {
                    listaSolucionesNodosEmpleado.get(indice).getCentrocostod().setNombre(CentroCostoDebito);
                } else {
                    filtradolistaSolucionesNodosEmpleado.get(indice).getCentrocostod().setNombre(CentroCostoDebito);
                }
            } else if (tipoTabla == 3) {
                indexSolucionesEmpleador = indice;
                if (tipoListaSNEmpleador == 0) {
                    listaSolucionesNodosEmpleador.get(indice).getCentrocostod().setNombre(CentroCostoDebito);
                } else {
                    filtradolistaSolucionesNodosEmpleador.get(indice).getCentrocostod().setNombre(CentroCostoDebito);
                }
            }

            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoTabla == 2) {
                    if (tipoListaSNEmpleado == 0) {
                        listaSolucionesNodosEmpleado.get(indice).setCentrocostod(lovCentrosCostos.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleado.get(indice).setCentrocostod(lovCentrosCostos.get(indiceUnicoElemento));
                    }
                } else if (tipoTabla == 3) {
                    if (tipoListaSNEmpleador == 0) {
                        listaSolucionesNodosEmpleador.get(indice).setCentrocostod(lovCentrosCostos.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleador.get(indice).setCentrocostod(lovCentrosCostos.get(indiceUnicoElemento));
                    }
                }
                lovCentrosCostos.clear();
                getLovCentrosCostos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:CentroCostoDebitoDialogo");
                context.execute("CentroCostoDebitoDialogo.show()");
            }
        }
        if (tipoDato.equals("CENTROCOSTOCREDITO")) {
            if (tipoTabla == 2) {
                indexSolucionesEmpleado = indice;
                if (tipoListaSNEmpleado == 0) {
                    listaSolucionesNodosEmpleado.get(indice).getCentrocostoc().setNombre(CentroCostoCredito);
                } else {
                    filtradolistaSolucionesNodosEmpleado.get(indice).getCentrocostoc().setNombre(CentroCostoCredito);
                }
            } else if (tipoTabla == 3) {
                indexSolucionesEmpleador = indice;
                if (tipoListaSNEmpleador == 0) {
                    listaSolucionesNodosEmpleador.get(indice).getCentrocostoc().setNombre(CentroCostoCredito);
                } else {
                    filtradolistaSolucionesNodosEmpleador.get(indice).getCentrocostoc().setNombre(CentroCostoCredito);
                }
            }

            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoTabla == 2) {
                    if (tipoListaSNEmpleado == 0) {
                        listaSolucionesNodosEmpleado.get(indice).setCentrocostoc(lovCentrosCostos.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleado.get(indice).setCentrocostoc(lovCentrosCostos.get(indiceUnicoElemento));
                    }
                } else if (tipoTabla == 3) {
                    if (tipoListaSNEmpleador == 0) {
                        listaSolucionesNodosEmpleador.get(indice).setCentrocostoc(lovCentrosCostos.get(indiceUnicoElemento));
                    } else {
                        filtradolistaSolucionesNodosEmpleador.get(indice).setCentrocostoc(lovCentrosCostos.get(indiceUnicoElemento));
                    }
                }
                lovCentrosCostos.clear();
                getLovCentrosCostos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:CentroCostoCreditoDialogo");
                context.execute("CentroCostoCreditoDialogo.show()");
            }
        }
        if (coincidencias == 1) {
            if (tipoTabla == 2) {
                if (tipoListaSNEmpleado == 0) {
                    if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                        listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indice));
                    } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indice))) {
                        listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indice));
                    }
                } else {
                    if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                        listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indice));
                    } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indice))) {
                        listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indice));
                    }
                }
                modificacionesSolucionesNodosEmpleado = true;
                context.update("form:tablaInferiorDerecha");
            } else if (tipoTabla == 3) {
                if (tipoListaSNEmpleador == 0) {
                    if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                        listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indice));
                    } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indice))) {
                        listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indice));
                    }
                    indexCortesProcesos = -1;
                } else {
                    if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                        listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indice));
                    } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indice))) {
                        listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indice));
                    }
                    indexCortesProcesos = -1;
                }
                modificacionesSolucionesNodosEmpleador = true;
                context.update("form:tablaInferiorDerechaEM");
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            secRegistro = null;
        }
    }
    //LOV PROCESOS

    public void llamarLOVProcesos(int indice) {
        indexCortesProcesos = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        context.update("formularioDialogos:ProcesosDialogo");
        context.execute("ProcesosDialogo.show()");
    }

    public void llamarLOVProcesosNuevo_Duplicado(int tipoN) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoN == 1) {
            tipoActualizacion = 1;
        } else {
            tipoActualizacion = 2;
        }
        context.update("formularioDialogos:ProcesosDialogo");
        context.execute("ProcesosDialogo.show()");
    }

    //LOV TERCEROS
    public void llamarLOVTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:TercerosDialogo");
        context.execute("TercerosDialogo.show()");
    }

    public void llamarLOVCuentaDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CuentaDebitoDialogo");
        context.execute("CuentaDebitoDialogo.show()");
    }

    public void llamarLOVCuentaCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CuentaCreditoDialogo");
        context.execute("CuentaCreditoDialogo.show()");
    }

    public void llamarLOVCentroCostoDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CentroCostoDebitoDialogo");
        context.execute("CentroCostoDebitoDialogo.show()");
    }

    public void llamarLOVCentroCostoCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CentroCostoCreditoDialogo");
        context.execute("CentroCostoCreditoDialogo.show()");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //CAMBIAR PROCESO
    public void actualizarProceso() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaCortesProcesos == 0) {
                listaCortesProcesos.get(indexCortesProcesos).setProceso(procesoSelecionado);
                if (!listaCortesProcesosCrear.contains(listaCortesProcesos.get(indexCortesProcesos))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indexCortesProcesos));
                    } else if (!listaCortesProcesosModificar.contains(listaCortesProcesos.get(indexCortesProcesos))) {
                        listaCortesProcesosModificar.add(listaCortesProcesos.get(indexCortesProcesos));
                    }
                }
            } else {
                filtradolistaCortesProcesos.get(indexCortesProcesos).setProceso(procesoSelecionado);
                if (!listaCortesProcesosCrear.contains(filtradolistaCortesProcesos.get(indexCortesProcesos))) {
                    if (listaCortesProcesosModificar.isEmpty()) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indexCortesProcesos));
                    } else if (!listaCortesProcesosModificar.contains(filtradolistaCortesProcesos.get(indexCortesProcesos))) {
                        listaCortesProcesosModificar.add(filtradolistaCortesProcesos.get(indexCortesProcesos));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:datosCortesProcesos");
            modificacionesCortesProcesos = true;
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoCorteProceso.setProceso(procesoSelecionado);
            context.update("formularioDialogos:nuevoCorteProceso");
        } else if (tipoActualizacion == 2) {
            duplicarCorteProceso.setProceso(procesoSelecionado);
            context.update("formularioDialogos:duplicadoCorteProceso");
        }
        filtradoProcesos = null;
        procesoSelecionado = null;
        aceptar = true;
        indexCortesProcesos = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        tipoTabla = -1;
        /*
         context.update("formularioDialogos:ProcesosDialogo");
         context.update("formularioDialogos:lovProcesos");
         context.update("formularioDialogos:aceptarP");*/
        context.reset("formularioDialogos:lovProcesos:globalFilter");
        context.execute("lovProcesos.clearFilters()");
        context.execute("ProcesosDialogo.hide()");
    }

    public void cancelarProceso() {
        filtradoProcesos = null;
        procesoSelecionado = null;
        aceptar = true;
        indexCortesProcesos = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovProcesos:globalFilter");
        context.execute("lovProcesos.clearFilters()");
        context.execute("ProcesosDialogo.hide()");
    }

    //ACTUALIZAR TERCERO
    public void actualizarTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 2) {
            if (tipoListaSNEmpleado == 0) {
                listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setNit(TerceroSelecionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            } else {
                filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setNit(TerceroSelecionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerecha");
            indexSolucionesEmpleado = -1;
            modificacionesSolucionesNodosEmpleado = true;
            permitirIndex = true;
        } else if (tipoTabla == 3) {
            if (tipoListaSNEmpleador == 0) {
                listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setNit(TerceroSelecionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            } else {
                filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setNit(TerceroSelecionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerechaEM");
            indexSolucionesEmpleador = -1;
            modificacionesSolucionesNodosEmpleador = true;
            permitirIndex = true;
        }

        filtradolistaTerceros = null;
        TerceroSelecionado = null;
        aceptar = true;
        secRegistro = null;
        cualCelda = -1;
        tipoTabla = -1;
        /*context.update("formularioDialogos:TercerosDialogo");
         context.update("formularioDialogos:lovTerceros");
         context.update("formularioDialogos:aceptarT");*/
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.execute("lovTerceros.clearFilters()");
        context.execute("TercerosDialogo.hide()");
    }

    public void cancelarTercero() {
        filtradolistaTerceros = null;
        TerceroSelecionado = null;
        aceptar = true;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.execute("lovTerceros.clearFilters()");
        context.execute("TercerosDialogo.hide()");
    }

    public void actualizarCuentaDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 2) {
            if (tipoListaSNEmpleado == 0) {
                listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCuentad(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            } else {
                filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCuentad(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerecha");
            indexSolucionesEmpleado = -1;
            modificacionesSolucionesNodosEmpleado = true;
            permitirIndex = true;
        } else if (tipoTabla == 3) {
            if (tipoListaSNEmpleador == 0) {
                listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCuentad(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            } else {
                filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCuentad(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerechaEM");
            indexSolucionesEmpleador = -1;
            modificacionesSolucionesNodosEmpleador = true;
            permitirIndex = true;
        }

        filtrarLovCuentas = null;
        cuentaSeleccionada = new Cuentas();
        aceptar = true;
        secRegistro = null;
        cualCelda = -1;
        tipoTabla = -1;
        /*
         context.update("formularioDialogos:CuentaDebitoDialogo");
         context.update("formularioDialogos:lovCuentaDebito");
         context.update("formularioDialogos:aceptarCD");*/
        context.reset("formularioDialogos:lovCuentaDebito:globalFilter");
        context.execute("lovCuentaDebito.clearFilters()");
        context.execute("CuentaDebitoDialogo.hide()");
    }

    public void cancelarCuentaDebito() {
        filtrarLovCuentas = null;
        cuentaSeleccionada = new Cuentas();
        aceptar = true;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovCuentaDebito:globalFilter");
        context.execute("lovCuentaDebito.clearFilters()");
        context.execute("CuentaDebitoDialogo.hide()");
    }

    public void actualizarCuentaCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 2) {
            if (tipoListaSNEmpleado == 0) {
                listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCuentac(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            } else {
                filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCuentac(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerecha");
            indexSolucionesEmpleado = -1;
            modificacionesSolucionesNodosEmpleado = true;
            permitirIndex = true;
        } else if (tipoTabla == 3) {
            if (tipoListaSNEmpleador == 0) {
                listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCuentac(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            } else {
                filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCuentac(cuentaSeleccionada);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerechaEM");
            indexSolucionesEmpleador = -1;
            modificacionesSolucionesNodosEmpleador = true;
            permitirIndex = true;
        }

        filtrarLovCuentas = null;
        cuentaSeleccionada = new Cuentas();
        aceptar = true;
        secRegistro = null;
        cualCelda = -1;
        tipoTabla = -1;
        /*
         context.update("formularioDialogos:CuentaCreditoDialogo");
         context.update("formularioDialogos:lovCuentaCredito");
         context.update("formularioDialogos:aceptarCC");*/
        context.reset("formularioDialogos:lovCuentaCredito:globalFilter");
        context.execute("lovCuentaCredito.clearFilters()");
        context.execute("CuentaCreditoDialogo.hide()");
    }

    public void cancelarCuentaCredito() {
        filtrarLovCuentas = null;
        cuentaSeleccionada = new Cuentas();
        aceptar = true;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovCuentaCredito:globalFilter");
        context.execute("lovCuentaCredito.clearFilters()");
        context.execute("CuentaCreditoDialogo.hide()");
    }

    public void actualizarCentroCostoCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 2) {
            if (tipoListaSNEmpleado == 0) {
                listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCentrocostoc(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            } else {
                filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCentrocostoc(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerecha");
            indexSolucionesEmpleado = -1;
            modificacionesSolucionesNodosEmpleado = true;
            permitirIndex = true;
        } else if (tipoTabla == 3) {
            if (tipoListaSNEmpleador == 0) {
                listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCentrocostoc(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            } else {
                filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCentrocostoc(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerechaEM");
            indexSolucionesEmpleador = -1;
            modificacionesSolucionesNodosEmpleador = true;
            permitirIndex = true;
        }

        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        aceptar = true;
        secRegistro = null;
        cualCelda = -1;
        tipoTabla = -1;
        /*
         context.update("formularioDialogos:CentroCostoCreditoDialogo");
         context.update("formularioDialogos:lovCentroCostoCredito");
         context.update("formularioDialogos:aceptarCCC");*/
        context.reset("formularioDialogos:lovCentroCostoCredito:globalFilter");
        context.execute("lovCentroCostoCredito.clearFilters()");
        context.execute("CentroCostoCreditoDialogo.hide()");
    }

    public void cancelarCentroCostoCredito() {
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        aceptar = true;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovCentroCostoCredito:globalFilter");
        context.execute("lovCentroCostoCredito.clearFilters()");
        context.execute("CentroCostoCreditoDialogo.hide()");
    }

    public void actualizarCentroCostoDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 2) {
            if (tipoListaSNEmpleado == 0) {
                listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCentrocostod(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(listaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            } else {
                filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado).setCentrocostod(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                } else if (!listaSolucionesNodosEmpleadoModificar.contains(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado))) {
                    listaSolucionesNodosEmpleadoModificar.add(filtradolistaSolucionesNodosEmpleado.get(indexSolucionesEmpleado));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerecha");
            indexSolucionesEmpleado = -1;
            modificacionesSolucionesNodosEmpleado = true;
            permitirIndex = true;
        } else if (tipoTabla == 3) {
            if (tipoListaSNEmpleador == 0) {
                listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCentrocostod(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(listaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            } else {
                filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador).setCentrocostod(centroCostoSeleccionado);
                if (listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                } else if (!listaSolucionesNodosEmpleadorModificar.contains(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador))) {
                    listaSolucionesNodosEmpleadorModificar.add(filtradolistaSolucionesNodosEmpleador.get(indexSolucionesEmpleador));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("form:tablaInferiorDerechaEM");
            indexSolucionesEmpleador = -1;
            modificacionesSolucionesNodosEmpleador = true;
            permitirIndex = true;
        }

        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        aceptar = true;
        secRegistro = null;
        cualCelda = -1;
        tipoTabla = -1;
        /*
         context.update("formularioDialogos:CentroCostoDebitoDialogo");
         context.update("formularioDialogos:lovCentroCostoDebito");
         context.update("formularioDialogos:aceptarCCD");*/
        
        context.reset("formularioDialogos:lovCentroCostoDebito:globalFilter");
        context.execute("lovCentroCostoDebito.clearFilters()");
        context.execute("CentroCostoDebitoDialogo.hide()");
    }

    public void cancelarCentroCostoDebito() {
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        aceptar = true;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        permitirIndex = true;
        tipoTabla = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovCentroCostoDebito:globalFilter");
        context.execute("lovCentroCostoDebito.clearFilters()");
        context.execute("CentroCostoDebitoDialogo.hide()");
    }

    //BORRAR VC
    public void borrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                if (tipoListaComprobantes == 0) {
                    if (administrarComprobantes.consultarCortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia()).isEmpty()) {
                        if (!listaComprobantesModificar.isEmpty() && listaComprobantesModificar.contains(listaComprobantes.get(indexComprobante))) {
                            int modIndex = listaComprobantesModificar.indexOf(listaComprobantes.get(indexComprobante));
                            listaComprobantesModificar.remove(modIndex);
                            listaComprobantesBorrar.add(listaComprobantes.get(indexComprobante));
                        } else if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(listaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(listaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                        } else {
                            listaComprobantesBorrar.add(listaComprobantes.get(indexComprobante));
                        }
                        listaComprobantes.remove(indexComprobante);
                        modificacionesComprobantes = true;
                    } else {
                        if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(listaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(listaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                            listaComprobantes.remove(indexComprobante);
                            modificacionesComprobantes = true;
                        } else {
                            context.execute("errorBorrarComprobante.show();");
                        }
                    }
                }
                if (tipoListaComprobantes == 1) {
                    if (administrarComprobantes.consultarCortesProcesosComprobante(filtradolistaComprobantes.get(indexComprobante).getSecuencia()).isEmpty()) {
                        if (!listaComprobantesModificar.isEmpty() && listaComprobantesModificar.contains(filtradolistaComprobantes.get(indexComprobante))) {
                            int modIndex = listaComprobantesModificar.indexOf(filtradolistaComprobantes.get(indexComprobante));
                            listaComprobantesModificar.remove(modIndex);
                            listaComprobantesBorrar.add(filtradolistaComprobantes.get(indexComprobante));
                        } else if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(filtradolistaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(filtradolistaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                        } else {
                            listaComprobantesBorrar.add(filtradolistaComprobantes.get(indexComprobante));
                        }
                        filtradolistaComprobantes.remove(indexComprobante);
                        modificacionesComprobantes = true;
                    } else {
                        if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(filtradolistaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(filtradolistaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                            filtradolistaComprobantes.remove(indexComprobante);
                            modificacionesComprobantes = true;
                        } else {
                            context.execute("errorBorrarComprobante.show();");
                        }
                    }
                }
                context.update("form:datosComprobantes");
                indexComprobante = -1;
            }
        } else if (tipoTabla == 1) {
            if (indexCortesProcesos >= 0) {
                if (tipoListaCortesProcesos == 0) {
                    if (administrarComprobantes.consultarSolucionesNodosEmpleado(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado).isEmpty() && administrarComprobantes.consultarSolucionesNodosEmpleador(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado).isEmpty()) {
                        if (!listaCortesProcesosModificar.isEmpty() && listaCortesProcesosModificar.contains(listaCortesProcesos.get(indexCortesProcesos))) {
                            int modIndex = listaCortesProcesosModificar.indexOf(listaCortesProcesos.get(indexCortesProcesos));
                            listaCortesProcesosModificar.remove(modIndex);
                            listaCortesProcesosBorrar.add(listaCortesProcesos.get(indexCortesProcesos));
                        } else {
                            listaCortesProcesosBorrar.add(listaCortesProcesos.get(indexCortesProcesos));
                        }
                        listaCortesProcesos.remove(indexCortesProcesos);
                        modificacionesCortesProcesos = true;
                    } else {
                        if (!listaCortesProcesosCrear.isEmpty() && listaCortesProcesosCrear.contains(listaCortesProcesos.get(indexCortesProcesos))) {
                            int crearIndex = listaCortesProcesosCrear.indexOf(listaCortesProcesos.get(indexCortesProcesos));
                            listaCortesProcesosCrear.remove(crearIndex);
                            listaCortesProcesos.remove(indexCortesProcesos);
                            modificacionesCortesProcesos = true;
                        } else {
                            context.execute("errorBorrarCortesProcesos.show();");
                        }
                    }
                }
                if (tipoListaCortesProcesos == 1) {
                    if (administrarComprobantes.consultarSolucionesNodosEmpleado(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado) == null && administrarComprobantes.consultarSolucionesNodosEmpleador(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado) == null) {
                        if (!listaCortesProcesosModificar.isEmpty() && listaCortesProcesosModificar.contains(filtradolistaCortesProcesos.get(indexCortesProcesos))) {
                            int modIndex = listaCortesProcesosModificar.indexOf(filtradolistaCortesProcesos.get(indexCortesProcesos));
                            listaCortesProcesosModificar.remove(modIndex);
                            listaCortesProcesosBorrar.add(filtradolistaCortesProcesos.get(indexCortesProcesos));
                        } else {
                            listaCortesProcesosBorrar.add(filtradolistaCortesProcesos.get(indexCortesProcesos));
                        }
                        filtradolistaCortesProcesos.remove(indexCortesProcesos);
                        modificacionesCortesProcesos = true;
                    } else {
                        if (!listaCortesProcesosCrear.isEmpty() && listaCortesProcesosCrear.contains(filtradolistaCortesProcesos.get(indexCortesProcesos))) {
                            int crearIndex = listaCortesProcesosCrear.indexOf(filtradolistaCortesProcesos.get(indexCortesProcesos));
                            listaCortesProcesosCrear.remove(crearIndex);
                            filtradolistaCortesProcesos.remove(indexCortesProcesos);
                            modificacionesCortesProcesos = true;
                        } else {
                            context.execute("errorBorrarCortesProcesos.show();");
                        }
                    }
                }
                context.update("form:datosCortesProcesos");
                indexCortesProcesos = -1;
            }
        }
        secRegistro = null;
        tipoTabla = -1;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }
//CREAR COMPROBANTE Y CORTE PROCESO

    public void nuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaComprobantes.isEmpty()) {
            if (listaCortesProcesos.isEmpty()) {
                if (indexComprobante >= 0) {
                    context.execute("NuevoRegistroPagina.show()");
                }
            } else {
                if (tipoTabla == 0) {
                    BigInteger sugerenciaNumero = administrarComprobantes.consultarMaximoNumeroComprobante().add(new BigInteger("1"));
                    nuevoComprobante.setNumero(sugerenciaNumero);
                    context.update("formularioDialogos:nuevoComprobante");
                    context.execute("NuevoRegistroComprobantes.show()");
                } else if (tipoTabla == 1) {
                    context.update("formularioDialogos:nuevoCorteProceso");
                    context.execute("NuevoRegistroCortesProcesos.show()");
                }
            }
        } else {
            BigInteger sugerenciaNumero = administrarComprobantes.consultarMaximoNumeroComprobante().add(new BigInteger("1"));
            nuevoComprobante.setNumero(sugerenciaNumero);
            context.update("formularioDialogos:nuevoComprobante");
            context.execute("NuevoRegistroComprobantes.show()");
        }
    }

    public void agregarNuevoComprobante() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoComprobante.getNumero() == null) {
            mensajeValidacion = " * Numero \n";
            pasa++;
        }
        if (nuevoComprobante.getFecha() == null) {
            mensajeValidacion = " * Fecha Comprobante \n";
            pasa++;
        }
        if (nuevoComprobante.isCheckEntregado() == true) {
            if (nuevoComprobante.getFechaentregado() == null) {
                mensajeValidacion = mensajeValidacion + " * Fecha Entrega \n";
                pasa++;
            }
        }
        if (pasa == 0) {
            if (banderaComprobantes == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                banderaComprobantes = 0;
                filtradolistaComprobantes = null;
                tipoListaComprobantes = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoComprobante.setSecuencia(l);
            nuevoComprobante.setEmpleado(empleado);
            nuevoComprobante.setValor(new BigDecimal(0));
            listaComprobantesCrear.add(nuevoComprobante);
            listaComprobantes.add(nuevoComprobante);
            nuevoComprobante = new Comprobantes();
            context.update("form:datosComprobantes");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevoRegistroComprobantes.hide()");
            modificacionesComprobantes = true;
            indexComprobante = -1;
            tipoTabla = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacioNuevoComprobante");
            context.execute("validacioNuevoComprobante.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO COMPROBANTES
    public void limpiarNuevoComprobante() {
        nuevoComprobante = new Comprobantes();
        indexComprobante = -1;
        tipoTabla = -1;
        secRegistro = null;
    }

    public void agregarNuevoCorteProceso() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoCorteProceso.getCorte() == null) {
            mensajeValidacion = " * Fecha de corte \n";
            pasa++;
        }
        if (nuevoCorteProceso.getProceso().getSecuencia() == null) {
            mensajeValidacion = " * Proceso \n";
            pasa++;
        }
        if (pasa == 0) {
            if (banderaCortesProcesos == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
                fechaCorteCP.setFilterStyle("display: none; visibility: hidden;");
                procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
                procesoCP.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
                banderaCortesProcesos = 0;
                filtradolistaCortesProcesos = null;
                tipoListaCortesProcesos = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoCorteProceso.setSecuencia(l);
            nuevoCorteProceso.setEmpleado(empleado);
            nuevoCorteProceso.setComprobante(seleccionComprobante);
            listaCortesProcesosCrear.add(nuevoCorteProceso);
            listaCortesProcesos.add(nuevoCorteProceso);
            nuevoCorteProceso = new CortesProcesos();
            nuevoCorteProceso.setProceso(new Procesos());
            context.update("form:datosCortesProcesos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevoRegistroCortesProcesos.hide()");
            modificacionesCortesProcesos = true;
            indexCortesProcesos = -1;
            tipoTabla = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacioNuevoCorteProceso");
            context.execute("validacioNuevoCorteProceso.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO CORTE PROCESO
    public void limpiarNuevoCorteProceso() {
        nuevoCorteProceso = new CortesProcesos();
        nuevoCorteProceso.setProceso(new Procesos());
        indexCortesProcesos = -1;
        tipoTabla = -1;
        secRegistro = null;
    }

    //DUPLICADOS
    public void duplicarC() {
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                duplicarComprobante = new Comprobantes();
                if (tipoListaComprobantes == 0) {
                    duplicarComprobante.setNumero(listaComprobantes.get(indexComprobante).getNumero());
                    duplicarComprobante.setFecha(listaComprobantes.get(indexComprobante).getFecha());
                    duplicarComprobante.setFechaentregado(listaComprobantes.get(indexComprobante).getFechaentregado());
                    duplicarComprobante.setEmpleado(listaComprobantes.get(indexComprobante).getEmpleado());
                    duplicarComprobante.setValor(new BigDecimal(0));
                } else if (tipoListaComprobantes == 1) {
                    duplicarComprobante.setNumero(filtradolistaComprobantes.get(indexComprobante).getNumero());
                    duplicarComprobante.setFecha(filtradolistaComprobantes.get(indexComprobante).getFecha());
                    duplicarComprobante.setFechaentregado(filtradolistaComprobantes.get(indexComprobante).getFechaentregado());
                    duplicarComprobante.setEmpleado(filtradolistaComprobantes.get(indexComprobante).getEmpleado());
                    duplicarComprobante.setValor(new BigDecimal(0));
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicadoComprobante");
                context.execute("DuplicarRegistroComprobantes.show()");
                indexComprobante = -1;
                secRegistro = null;
            }
        } else if (tipoTabla == 1) {
            if (indexCortesProcesos >= 0) {
                duplicarCorteProceso = new CortesProcesos();
                if (tipoListaCortesProcesos == 0) {
                    duplicarCorteProceso.setCorte(listaCortesProcesos.get(indexCortesProcesos).getCorte());
                    duplicarCorteProceso.setProceso(listaCortesProcesos.get(indexCortesProcesos).getProceso());
                    duplicarCorteProceso.setEmpleado(listaCortesProcesos.get(indexCortesProcesos).getEmpleado());
                    duplicarCorteProceso.setComprobante(seleccionComprobante);
                } else if (tipoListaCortesProcesos == 1) {
                    duplicarCorteProceso.setCorte(filtradolistaCortesProcesos.get(indexCortesProcesos).getCorte());
                    duplicarCorteProceso.setProceso(filtradolistaCortesProcesos.get(indexCortesProcesos).getProceso());
                    duplicarCorteProceso.setEmpleado(filtradolistaCortesProcesos.get(indexCortesProcesos).getEmpleado());
                    duplicarCorteProceso.setComprobante(seleccionComprobante);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicadoCorteProceso");
                context.execute("DuplicarCortesProcesos.show()");
                indexCortesProcesos = -1;
                secRegistro = null;
            }
        }
    }

    public void confirmarDuplicarComprobantes() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarComprobante.getNumero() == null) {
            mensajeValidacion = " * Numero \n";
            pasa++;
        }
        if (duplicarComprobante.getFecha() == null) {
            mensajeValidacion = " * Fecha Comprobante \n";
            pasa++;
        }
        if (duplicarComprobante.isCheckEntregado() == true) {
            if (duplicarComprobante.getFechaentregado() == null) {
                mensajeValidacion = mensajeValidacion + " * Fecha Entrega \n";
                pasa++;
            }
        }
        if (pasa == 0) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarComprobante.setSecuencia(l);
            listaComprobantes.add(duplicarComprobante);
            listaComprobantesCrear.add(duplicarComprobante);
            context.update("form:datosComprobantes");
            context.execute("DuplicarRegistroComprobantes.hide()");
            indexComprobante = -1;
            tipoTabla = -1;
            modificacionesComprobantes = true;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (banderaComprobantes == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                banderaComprobantes = 0;
                filtradolistaComprobantes = null;
                tipoListaComprobantes = 0;
            }
        } else {
            context.update("formularioDialogos:validacioNuevoComprobante");
            context.execute("validacioNuevoComprobante.show()");
        }
    }

    //LIMPIAR DUPLICADO REGISTRO COMPROBANTES
    public void limpiarDuplicadoComprobante() {
        duplicarComprobante = new Comprobantes();
        indexComprobante = -1;
        secRegistro = null;
        tipoTabla = -1;
    }

    public void confirmarDuplicarCortesProcesos() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarCorteProceso.getCorte() == null) {
            mensajeValidacion = " * Fecha Corte \n";
            pasa++;
        }
        if (duplicarCorteProceso.getProceso().getSecuencia() == null) {
            mensajeValidacion = " * Proceso \n";
            pasa++;
        }
        if (pasa == 0) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarCorteProceso.setSecuencia(l);
            listaCortesProcesos.add(duplicarCorteProceso);
            listaCortesProcesosCrear.add(duplicarCorteProceso);
            context.update("form:datosCortesProcesos");
            context.execute("DuplicarCortesProcesos.hide()");
            indexCortesProcesos = -1;
            tipoTabla = -1;
            modificacionesCortesProcesos = true;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (banderaCortesProcesos == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
                fechaCorteCP.setFilterStyle("display: none; visibility: hidden;");
                procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
                procesoCP.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
                banderaCortesProcesos = 0;
                filtradolistaCortesProcesos = null;
                tipoListaCortesProcesos = 0;
            }
        } else {
            context.update("formularioDialogos:validacioNuevoCorteProceso");
            context.execute("validacioNuevoCorteProceso.show()");
        }
    }

    //LIMPIAR DUPLICADO REGISTRO CORTES PROCESOS
    public void limpiarDuplicadoCortesProcesos() {
        duplicarCorteProceso = new CortesProcesos();
        indexCortesProcesos = -1;
        tipoTabla = -1;
        secRegistro = null;
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (tipoTabla == 0) {
            if (banderaComprobantes == 0) {
                numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("width: 40px;");
                fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("width: 60px;");
                fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("width: 60px;");
                altoScrollComprobante = "37";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                banderaComprobantes = 1;

            } else if (banderaComprobantes == 1) {
                numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                banderaComprobantes = 0;
                filtradolistaComprobantes = null;
                tipoListaComprobantes = 0;
            }
        } else if (tipoTabla == 1) {
            if (banderaCortesProcesos == 0) {
                fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
                fechaCorteCP.setFilterStyle("width: 80px;");
                procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
                procesoCP.setFilterStyle("width: 150px;");
                altoScrollCortesProcesos = "37";
                RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
                banderaCortesProcesos = 1;

            } else if (banderaCortesProcesos == 1) {
                fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
                fechaCorteCP.setFilterStyle("display: none; visibility: hidden;");
                procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
                procesoCP.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "61";
                RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
                banderaCortesProcesos = 0;
                filtradolistaCortesProcesos = null;
                tipoListaCortesProcesos = 0;
            }
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                if (tipoListaComprobantes == 0) {
                    editarComprobante = listaComprobantes.get(indexComprobante);
                }
                if (tipoListaComprobantes == 1) {
                    editarComprobante = filtradolistaComprobantes.get(indexComprobante);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarNumeroComprobante");
                    context.execute("editarNumeroComprobante.show()");
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarFechaComprobante");
                    context.execute("editarFechaComprobante.show()");
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarFechaEntrega");
                    context.execute("editarFechaEntrega.show()");
                }
            }
            indexComprobante = -1;
        } else if (tipoTabla == 1) {
            if (tipoListaCortesProcesos == 0) {
                editarCorteProceso = listaCortesProcesos.get(indexCortesProcesos);
            }
            if (tipoListaCortesProcesos == 1) {
                editarCorteProceso = filtradolistaCortesProcesos.get(indexCortesProcesos);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaCorte");
                context.execute("editarFechaCorte.show()");
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarProceso");
                context.execute("editarProceso.show()");
            }
            indexCortesProcesos = -1;
        }
        secRegistro = null;
    }

    //LISTA DE VALORES BOTON
    public void listaValoresBoton() {
        if (tipoTabla == 1) {
            if (indexCortesProcesos >= 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                if (cualCelda == 1) {
                    context.update("formularioDialogos:ProcesosDialogo");
                    context.execute("ProcesosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = -1;
        if (nombreTabla != null) {
            if (nombreTabla.equals("Comprobantes")) {
                resultado = administrarRastros.obtenerTabla(secRegistro, nombreTabla.toUpperCase());
            } else if (nombreTabla.equals("CortesProcesos")) {
                resultado = administrarRastros.obtenerTabla(secRegistro, nombreTabla.toUpperCase());
            }
            if (resultado == 1) {
                context.update("formDialogos:errorObjetosDB");
                context.execute("errorObjetosDB.show()");
            } else if (resultado == 2) {
                context.update("formDialogos:confirmarRastro");
                context.execute("confirmarRastro.show()");
            } else if (resultado == 3) {
                context.update("formDialogos:errorRegistroRastro");
                context.execute("errorRegistroRastro.show()");
            } else if (resultado == 4) {
                context.update("formDialogos:errorTablaConRastro");
                context.execute("errorTablaConRastro.show()");
            } else if (resultado == 5) {
                context.update("formDialogos:errorTablaSinRastro");
                context.execute("errorTablaSinRastro.show()");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }
    //EXPORTAR

    public void exportPDF() throws IOException {
        DataTable tabla;
        Exporter exporter = new ExportarPDF();
        if (tipoTabla == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosComprobantesExportar");
            exporter.export(context, tabla, "ComprobantesPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexComprobante = -1;
        } else if (tipoTabla == 1) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCortesProcesosExportar");
            exporter.export(context, tabla, "CortesProcesosPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexCortesProcesos = -1;
        } else if (tipoTabla == 2) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:solucionesNodoEmpleadoExportar");
            exporter.export(context, tabla, "SolucionesNodosEmpleadoPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexSolucionesEmpleado = -1;
        } else if (tipoTabla == 3) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:solucionesNodoEmpleadorExportar");
            exporter.export(context, tabla, "SolucionesNodosEmpleadorPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexSolucionesEmpleador = -1;
        }
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla;
        Exporter exporter = new ExportarXLS();
        if (tipoTabla == 0) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosComprobantesExportar");
            exporter.export(context, tabla, "ComprobantesXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexComprobante = -1;
        } else if (tipoTabla == 1) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCortesProcesosExportar");
            exporter.export(context, tabla, "CortesProcesosXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexComprobante = -1;
        } else if (tipoTabla == 2) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:solucionesNodoEmpleadoExportar");
            exporter.export(context, tabla, "SolucionesNodosEmpleadoXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexSolucionesEmpleado = -1;
        } else if (tipoTabla == 3) {
            FacesContext context = FacesContext.getCurrentInstance();
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:solucionesNodoEmpleadorExportar");
            exporter.export(context, tabla, "SolucionesNodosEmpleadorXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexSolucionesEmpleador = -1;
        }
        secRegistro = null;
    }

    //SALIR Y REFRESCAR
    public void salir() {
        if (banderaCortesProcesos == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            fechaCorteCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:fechaCorteCP");
            fechaCorteCP.setFilterStyle("display: none; visibility: hidden;");
            procesoCP = (Column) c.getViewRoot().findComponent("form:datosCortesProcesos:procesoCP");
            procesoCP.setFilterStyle("display: none; visibility: hidden;");
            altoScrollComprobante = "61";
            RequestContext.getCurrentInstance().update("form:datosCortesProcesos");
            banderaCortesProcesos = 0;
            filtradolistaCortesProcesos = null;
            tipoListaCortesProcesos = 0;
        }
        if (banderaComprobantes == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            numeroComprobanteC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
            numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
            fechaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaC");
            fechaC.setFilterStyle("display: none; visibility: hidden;");
            fechaEntregaC = (Column) c.getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
            fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
            altoScrollComprobante = "61";
            RequestContext.getCurrentInstance().update("form:datosComprobantes");
            banderaComprobantes = 0;
            filtradolistaComprobantes = null;
            tipoListaComprobantes = 0;
        }
        listaComprobantesBorrar.clear();
        listaCortesProcesosBorrar.clear();
        listaComprobantesCrear.clear();
        listaCortesProcesosCrear.clear();
        listaComprobantesModificar.clear();
        listaCortesProcesosModificar.clear();
        listaSolucionesNodosEmpleadoModificar.clear();
        listaSolucionesNodosEmpleadorModificar.clear();
        modificacionesComprobantes = false;
        modificacionesCortesProcesos = false;
        modificacionesSolucionesNodosEmpleado = false;
        modificacionesSolucionesNodosEmpleador = false;
        tipoTabla = 0;
        cualCelda = -1;
        tablaExportar = ":formExportar:datosComprobantesExportar";
        nombreTabla = "Comprobantes";
        indexComprobante = -1;
        indexCortesProcesos = -1;
        indexSolucionesEmpleado = -1;
        indexSolucionesEmpleador = -1;
        secRegistro = null;
        k = 0;
        listaComprobantes = null;
        listaCortesProcesos = null;
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        guardado = true;
        permitirIndex = true;
        recibirEmpleado(secuenciaEmpleado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosComprobantes");
        context.update("form:datosCortesProcesos");
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
    }

    public void guardarCambiosComprobantes() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listaComprobantesBorrar.isEmpty()) {
                for (int i = 0; i < listaComprobantesBorrar.size(); i++) {
                    administrarComprobantes.borrarComprobantes(listaComprobantesBorrar.get(i));
                }
                listaComprobantesBorrar.clear();
            }
            if (!listaComprobantesCrear.isEmpty()) {
                for (int i = 0; i < listaComprobantesCrear.size(); i++) {
                    administrarComprobantes.crearComprobante(listaComprobantesCrear.get(i));
                }
                listaComprobantesCrear.clear();
            }
            if (!listaComprobantesModificar.isEmpty()) {
                administrarComprobantes.modificarComprobantes(listaComprobantesModificar);
                listaComprobantesModificar.clear();
            }
            listaComprobantes = null;
            context.update("form:datosComprobantes");
            guardado = true;
            modificacionesComprobantes = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            k = 0;
            indexComprobante = -1;
            FacesMessage msg = new FacesMessage("Información", "Los datos de Comprobante se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosComprobantes Controlador: " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Comprobante, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosCorteProceso() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listaCortesProcesosBorrar.isEmpty()) {
                for (int i = 0; i < listaCortesProcesosBorrar.size(); i++) {
                    if (listaCortesProcesosBorrar.get(i).getProceso().getSecuencia() == null) {
                        listaCortesProcesosBorrar.get(i).setProceso(null);
                        administrarComprobantes.borrarCortesProcesos(listaCortesProcesosBorrar.get(i));
                    } else {
                        administrarComprobantes.borrarCortesProcesos(listaCortesProcesosBorrar.get(i));
                    }
                }
                listaCortesProcesosBorrar.clear();
            }
            if (!listaCortesProcesosCrear.isEmpty()) {
                for (int i = 0; i < listaCortesProcesosCrear.size(); i++) {
                    if (listaCortesProcesosCrear.get(i).getProceso().getSecuencia() == null) {
                        listaCortesProcesosCrear.get(i).setProceso(null);
                        administrarComprobantes.crearCorteProceso(listaCortesProcesosCrear.get(i));
                    } else {
                        administrarComprobantes.crearCorteProceso(listaCortesProcesosCrear.get(i));
                    }
                }
                listaCortesProcesosCrear.clear();
            }
            if (!listaCortesProcesosModificar.isEmpty()) {
                administrarComprobantes.modificarCortesProcesos(listaCortesProcesosModificar);
                listaCortesProcesosModificar.clear();
            }
            listaCortesProcesos = null;
            context.update("form:datosCortesProcesos");
            guardado = true;
            modificacionesCortesProcesos = false;
            context.update("form:aceptar");
            k = 0;
            indexCortesProcesos = -1;
            FacesMessage msg = new FacesMessage("Información", "Los datos de Corte Proceso se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosCorteProceso Controlador: " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Corte Proceso, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listaSolucionesNodosEmpleadoModificar.isEmpty()) {
                administrarComprobantes.modificarSolucionesNodosEmpleado(listaSolucionesNodosEmpleadoModificar);
                listaSolucionesNodosEmpleadoModificar.clear();
            }
            listaSolucionesNodosEmpleado = null;
            context.update("form:tablaInferiorDerecha");
            guardado = true;
            modificacionesSolucionesNodosEmpleado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            indexSolucionesEmpleado = -1;
            FacesMessage msg = new FacesMessage("Información", "Los datos de Empleado se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosEmpleado Controlador: " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Empleado, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosEmpleador() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listaSolucionesNodosEmpleadorModificar.isEmpty()) {
                administrarComprobantes.modificarSolucionesNodosEmpleado(listaSolucionesNodosEmpleadorModificar);
                listaSolucionesNodosEmpleadorModificar.clear();
            }
            listaSolucionesNodosEmpleador = null;
            context.update("form:tablaInferiorDerechaEM");
            guardado = true;
            modificacionesSolucionesNodosEmpleador = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            indexSolucionesEmpleador = -1;
            FacesMessage msg = new FacesMessage("Información", "Los datos de Empleador se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosEmpleador Controlador: " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Empleador, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    //GUARDAR
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            if (modificacionesComprobantes == true) {
                guardarCambiosComprobantes();
            }
            if (modificacionesCortesProcesos == true) {
                guardarCambiosCorteProceso();
            }
            if (modificacionesSolucionesNodosEmpleado == true) {
                guardarCambiosEmpleado();
            }

            if (modificacionesSolucionesNodosEmpleador == true) {
                guardarCambiosEmpleador();
            }
            recibirEmpleado(secuenciaEmpleado);
            tablaExportar = ":formExportar:datosComprobantesExportar";
            context.update("form:datosComprobantes");
            context.update("form:datosCortesProcesos");
            context.update("form:tablaInferiorIzquierda");
            context.update("form:tablaInferiorDerecha");
            context.update("form:tablaInferiorIzquierdaEM");
            context.update("form:tablaInferiorDerechaEM");
            context.update("form:exportarXML");
            secRegistro = null;
        }
    }

    public void organizarTablasEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
    }

    public void organizarTablasEmpleador() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
    }

    //EVENTO FILTRAR
    public void eventoFiltrarComponentes() {
        if (tipoListaComprobantes == 0) {
            tipoListaComprobantes = 1;
        }
    }

    public void eventoFiltrarCortesProcesos() {
        if (tipoListaCortesProcesos == 0) {
            tipoListaCortesProcesos = 1;
        }
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
//GETTER AND SETTER

    public Empleados getEmpleado() {
        return empleado;
    }

    public List<Comprobantes> getListaComprobantes() {
        return listaComprobantes;
    }

    public List<CortesProcesos> getListaCortesProcesos() {
        return listaCortesProcesos;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleado() {
        return listaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleador() {
        return listaSolucionesNodosEmpleador;
    }

    public void setListaComprobantes(List<Comprobantes> listaComprobantes) {
        this.listaComprobantes = listaComprobantes;
    }

    public List<Comprobantes> getFiltradolistaComprobantes() {
        return filtradolistaComprobantes;
    }

    public void setFiltradolistaComprobantes(List<Comprobantes> filtradolistaComprobantes) {
        this.filtradolistaComprobantes = filtradolistaComprobantes;
    }

    public void setListaCortesProcesos(List<CortesProcesos> listaCortesProcesos) {
        this.listaCortesProcesos = listaCortesProcesos;
    }

    public List<CortesProcesos> getFiltradolistaCortesProcesos() {
        return filtradolistaCortesProcesos;
    }

    public void setFiltradolistaCortesProcesos(List<CortesProcesos> filtradolistaCortesProcesos) {
        this.filtradolistaCortesProcesos = filtradolistaCortesProcesos;
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

    public void setListaSolucionesNodosEmpleador(List<SolucionesNodos> listaSolucionesNodosEmpleador) {
        this.listaSolucionesNodosEmpleador = listaSolucionesNodosEmpleador;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleador() {
        return filtradolistaSolucionesNodosEmpleador;
    }

    public void setFiltradolistaSolucionesNodosEmpleador(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador) {
        this.filtradolistaSolucionesNodosEmpleador = filtradolistaSolucionesNodosEmpleador;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Comprobantes getNuevoComprobante() {
        return nuevoComprobante;
    }

    public void setNuevoComprobante(Comprobantes nuevoComprobante) {
        this.nuevoComprobante = nuevoComprobante;
    }

    public CortesProcesos getNuevoCorteProceso() {
        return nuevoCorteProceso;
    }

    public void setNuevoCorteProceso(CortesProcesos nuevoCorteProceso) {
        this.nuevoCorteProceso = nuevoCorteProceso;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public Comprobantes getDuplicarComprobante() {
        return duplicarComprobante;
    }

    public void setDuplicarComprobante(Comprobantes duplicarComprobante) {
        this.duplicarComprobante = duplicarComprobante;
    }

    public String getTablaExportar() {
        return tablaExportar;
    }

    public void setTablaExportar(String tablaExportar) {
        this.tablaExportar = tablaExportar;
    }

    public String getNombreArchivoExportar() {
        return nombreArchivoExportar;
    }

    public void setNombreArchivoExportar(String nombreArchivoExportar) {
        this.nombreArchivoExportar = nombreArchivoExportar;
    }

    public String getAltoScrollComprobante() {
        return altoScrollComprobante;
    }

    public void setAltoScrollComprobante(String altoScrollComprobante) {
        this.altoScrollComprobante = altoScrollComprobante;
    }

    public Comprobantes getEditarComprobante() {
        return editarComprobante;
    }

    public void setEditarComprobante(Comprobantes editarComprobante) {
        this.editarComprobante = editarComprobante;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public List<Procesos> getListaProcesos() {
        listaProcesos = administrarComprobantes.consultarLOVProcesos();
        return listaProcesos;
    }

    public void setListaProcesos(List<Procesos> listaProcesos) {
        this.listaProcesos = listaProcesos;
    }

    public Procesos getProcesoSelecionado() {
        return procesoSelecionado;
    }

    public void setProcesoSelecionado(Procesos procesoSelecionado) {
        this.procesoSelecionado = procesoSelecionado;
    }

    public List<Procesos> getFiltradoProcesos() {
        return filtradoProcesos;
    }

    public void setFiltradoProcesos(List<Procesos> filtradoProcesos) {
        this.filtradoProcesos = filtradoProcesos;
    }

    public CortesProcesos getEditarCorteProceso() {
        return editarCorteProceso;
    }

    public void setEditarCorteProceso(CortesProcesos editarCorteProceso) {
        this.editarCorteProceso = editarCorteProceso;
    }

    public CortesProcesos getDuplicarCorteProceso() {
        return duplicarCorteProceso;
    }

    public void setDuplicarCorteProceso(CortesProcesos duplicarCorteProceso) {
        this.duplicarCorteProceso = duplicarCorteProceso;
    }

    public Comprobantes getSeleccionComprobante() {
        return seleccionComprobante;
    }

    public void setSeleccionComprobante(Comprobantes seleccionComprobante) {
        this.seleccionComprobante = seleccionComprobante;
    }

    public String getAltoScrollCortesProcesos() {
        return altoScrollCortesProcesos;
    }

    public List<Terceros> getListaTerceros() {
        if (empleado.getEmpresa().getSecuencia() != null) {
            listaTerceros = administrarComprobantes.consultarLOVTerceros(empleado.getEmpresa().getSecuencia());
        //if (empleado.getEmpresa() != null) {
            //listaTerceros = administrarComprobantes.consultarLOVTerceros(empleado.getEmpresa());
        }
        return listaTerceros;
    }

    public void setListaTerceros(List<Terceros> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    public Terceros getTerceroSelecionado() {
        return TerceroSelecionado;
    }

    public void setTerceroSelecionado(Terceros TerceroSelecionado) {
        this.TerceroSelecionado = TerceroSelecionado;
    }

    public List<Terceros> getFiltradolistaTerceros() {
        return filtradolistaTerceros;
    }

    public void setFiltradolistaTerceros(List<Terceros> filtradolistaTerceros) {
        this.filtradolistaTerceros = filtradolistaTerceros;
    }

    public BigDecimal getSubtotalPago() {
        return subtotalPago;
    }

    public BigDecimal getSubtotalDescuento() {
        return subtotalDescuento;
    }

    public BigDecimal getSubtotalPasivo() {
        return subtotalPasivo;
    }

    public BigDecimal getSubtotalGasto() {
        return subtotalGasto;
    }

    public BigDecimal getNeto() {
        return neto;
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
                secHistoriaFormula = administrarComprobantes.consultarHistoriaFormula(secFormula, fechaDesde);
                listaDetallesFormulas = administrarComprobantes.consultarDetallesFormula(secEmpleado, fechaDesde, fechaHasta, secProceso, secHistoriaFormula);
            }
        }
        return listaDetallesFormulas;
    }

    public void setListaDetallesFormulas(List<DetallesFormulas> listaDetallesFormulas) {
        this.listaDetallesFormulas = listaDetallesFormulas;
    }

    public String getParcialesSolucionNodos() {
        return parcialesSolucionNodos;
    }

    public CortesProcesos getSeleccionCortesProcesos() {
        getListaCortesProcesos();
        if (listaCortesProcesos != null) {
            int tam = listaCortesProcesos.size();
            if (tam > 0) {
                seleccionCortesProcesos = listaCortesProcesos.get(0);
            }
        }
        return seleccionCortesProcesos;
    }

    public void setSeleccionCortesProcesos(CortesProcesos seleccionCortesProcesos) {
        this.seleccionCortesProcesos = seleccionCortesProcesos;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<Cuentas> getLovCuentas() {
        lovCuentas = administrarComprobantes.lovCuentas();
        return lovCuentas;
    }

    public void setLovCuentas(List<Cuentas> lovCuentas) {
        this.lovCuentas = lovCuentas;
    }

    public List<Cuentas> getFiltrarLovCuentas() {
        return filtrarLovCuentas;
    }

    public void setFiltrarLovCuentas(List<Cuentas> filtrarLovCuentas) {
        this.filtrarLovCuentas = filtrarLovCuentas;
    }

    public Cuentas getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(Cuentas cuentaSeleccionada) {
        this.cuentaSeleccionada = cuentaSeleccionada;
    }

    public List<CentrosCostos> getLovCentrosCostos() {
        lovCentrosCostos = administrarComprobantes.lovCentrosCostos();
        return lovCentrosCostos;
    }

    public void setLovCentrosCostos(List<CentrosCostos> lovCentrosCostos) {
        this.lovCentrosCostos = lovCentrosCostos;
    }

    public List<CentrosCostos> getFiltrarLovCentrosCostos() {
        return filtrarLovCentrosCostos;
    }

    public void setFiltrarLovCentrosCostos(List<CentrosCostos> filtrarLovCentrosCostos) {
        this.filtrarLovCentrosCostos = filtrarLovCentrosCostos;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos centroCostoSeleccionado) {
        this.centroCostoSeleccionado = centroCostoSeleccionado;
    }

    public String getInfoRegistroProceso() {
        getListaProcesos();
        if (listaProcesos != null) {
            infoRegistroProceso = "Cantidad de registros : " + listaProcesos.size();
        } else {
            infoRegistroProceso = "Cantidad de registros : 0";
        }
        return infoRegistroProceso;
    }

    public void setInfoRegistroProceso(String infoRegistroProceso) {
        this.infoRegistroProceso = infoRegistroProceso;
    }

    public String getInfoRegistroTercero() {
        getListaTerceros();
        if (listaTerceros != null) {
            infoRegistroTercero = "Cantidad de registros : " + listaTerceros.size();
        } else {
            infoRegistroTercero = "Cantidad de registros : 0";
        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public String getInfoRegistroCuentaDebito() {
        getLovCuentas();
        if (lovCuentas != null) {
            infoRegistroCuentaDebito = "Cantidad de registros : " + lovCuentas.size();
        } else {
            infoRegistroCuentaDebito = "Cantidad de registros : 0";
        }
        return infoRegistroCuentaDebito;
    }

    public void setInfoRegistroCuentaDebito(String infoRegistroCuentaDebito) {
        this.infoRegistroCuentaDebito = infoRegistroCuentaDebito;
    }

    public String getInfoRegistroCuentaCredito() {
        getLovCuentas();
        if (lovCuentas != null) {
            infoRegistroCuentaCredito = "Cantidad de registros : " + lovCuentas.size();
        } else {
            infoRegistroCuentaCredito = "Cantidad de registros : 0";
        }
        return infoRegistroCuentaCredito;
    }

    public void setInfoRegistroCuentaCredito(String infoRegistroCuentaCredito) {
        this.infoRegistroCuentaCredito = infoRegistroCuentaCredito;
    }

    public String getInfoRegistroCentroCostoDebito() {
        getLovCentrosCostos();
        if (lovCentrosCostos != null) {
            infoRegistroCentroCostoDebito = "Cantidad de registros : " + lovCentrosCostos.size();
        } else {
            infoRegistroCentroCostoDebito = "Cantidad de registros : 0";
        }
        return infoRegistroCentroCostoDebito;
    }

    public void setInfoRegistroCentroCostoDebito(String infoRegistroCentroCostoDebito) {
        this.infoRegistroCentroCostoDebito = infoRegistroCentroCostoDebito;
    }

    public String getInfoRegistroCentroCostoCredito() {
        getLovCentrosCostos();
        if (lovCentrosCostos != null) {
            infoRegistroCentroCostoCredito = "Cantidad de registros : " + lovCentrosCostos.size();
        } else {
            infoRegistroCentroCostoCredito = "Cantidad de registros : 0";
        }
        return infoRegistroCentroCostoCredito;
    }

    public void setInfoRegistroCentroCostoCredito(String infoRegistroCentroCostoCredito) {
        this.infoRegistroCentroCostoCredito = infoRegistroCentroCostoCredito;
    }

    public SolucionesNodos getEmpleadoTablaSeleccionado() {
        getListaSolucionesNodosEmpleado();
        if (listaSolucionesNodosEmpleado != null) {
            int tam = listaSolucionesNodosEmpleado.size();
            if (tam > 0) {
                empleadoTablaSeleccionado = listaSolucionesNodosEmpleado.get(0);
            }
        }
        return empleadoTablaSeleccionado;
    }

    public void setEmpleadoTablaSeleccionado(SolucionesNodos empleadoTablaSeleccionado) {
        this.empleadoTablaSeleccionado = empleadoTablaSeleccionado;
    }

    public SolucionesNodos getEmpleadorTablaSeleccionado() {
        getListaSolucionesNodosEmpleador();
        if (listaSolucionesNodosEmpleador != null) {
            int tam = listaSolucionesNodosEmpleador.size();
            if (tam > 0) {
                empleadorTablaSeleccionado = listaSolucionesNodosEmpleador.get(0);
            }
        }
        return empleadorTablaSeleccionado;
    }

    public void setEmpleadorTablaSeleccionado(SolucionesNodos empleadorTablaSeleccionado) {
        this.empleadorTablaSeleccionado = empleadorTablaSeleccionado;
    }

}

package Controlador;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlComprobantes implements Serializable {

    @EJB
    AdministrarComprobantesInterface administrarComprobantes;
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
    //TABLA 3
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    //TABLA 4
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;
    private List<Procesos> listaProcesos;
    private Procesos procesoSelecionado;
    private List<Procesos> filtradoProcesos;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla Comprobantes
    DataTable tablaComprobantes, tablaCortesProcesos;
    private Column numeroComprobanteC, fechaC, fechaEntregaC;
    private String altoScrollComprobante, altoScrollCortesProcesos;
    //Otros
    private boolean aceptar;
    private int indexComprobante;
    private int indexCortesProcesos;
    private int indexSolucionesEmpleado;
    private int indexSolucionesEmpleador;
    private String tablaExportar, nombreArchivoExportar;
    //modificar
    private List<Comprobantes> listaComprobantesModificar;
    private List<CortesProcesos> listaCortesProcesosModificar;
    private boolean guardado, guardarOk;
    private boolean modificacionesComprobantes;
    private boolean modificacionesCortesProcesos;
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
    private String Proceso;
    //RASTRO*/
    private BigInteger secRegistro;
    private String nombreTabla;
    //INFORMATIVOS
    private int cualCelda, tipoListaComprobantes, tipoListaCortesProcesos, tipoTabla;

    public ControlComprobantes() {
        secuenciaEmpleado = BigInteger.valueOf(10661474);
        permitirIndex = true;
        listaProcesos = new ArrayList<Procesos>();
        empleado = new Empleados();
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
        tipoTabla = 0;
        //guardar
        guardado = true;
        //Crear 
        nuevoComprobante = new Comprobantes();
        nuevoCorteProceso = new CortesProcesos();
        nuevoCorteProceso.setProceso(new Procesos());
        nuevoCorteProceso.getProceso().setDescripcion(" ");
        secRegistro = null;
        tablaExportar = ":formExportar:datosComprobantesExportar";
        altoScrollComprobante = "67";
        nombreTabla = "Comprobantes";
    }

    public void recibirEmpleado(BigInteger sec) {
        secuenciaEmpleado = sec;
        empleado = administrarComprobantes.buscarEmpleado(secuenciaEmpleado);
        if (empleado != null) {
            listaComprobantes = administrarComprobantes.comprobantesEmpleado(empleado.getSecuencia());
            if (!listaComprobantes.isEmpty()) {
                seleccionComprobante = listaComprobantes.get(0);
                listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(seleccionComprobante.getSecuencia());
                if (!listaCortesProcesos.isEmpty()) {
                    listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                    listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                }
            }
        }
    }
    //Ubicacion Celda y Tabla.

    public void cambiarIndiceComprobantes(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (modificacionesCortesProcesos == false) {
            if (permitirIndex == true) {
                indexComprobante = indice;
                cualCelda = celda;
                tipoTabla = 0;
                nombreTabla = "Comprobantes";
                seleccionComprobante = listaComprobantes.get(indexComprobante);
                if (tipoListaComprobantes == 0) {
                    secRegistro = listaComprobantes.get(indexComprobante).getSecuencia();
                    listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia());
                    if (!listaCortesProcesos.isEmpty()) {
                        listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                        listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                    } else {
                        listaSolucionesNodosEmpleado = null;
                        listaSolucionesNodosEmpleador = null;
                    }
                } else {
                    secRegistro = filtradolistaComprobantes.get(indexComprobante).getSecuencia();
                    listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(filtradolistaComprobantes.get(indexComprobante).getSecuencia());
                    if (!listaCortesProcesos.isEmpty()) {
                        listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                        listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                    } else {
                        listaSolucionesNodosEmpleado = null;
                        listaSolucionesNodosEmpleador = null;
                    }
                }
                tablaExportar = ":formExportar:datosComprobantesExportar";
                nombreArchivoExportar = "ComprobantesXML";
                context.update("form:datosCortesProcesos");
                context.update("form:tablaInferiorIzquierda");
                context.update("form:tablaInferiorDerecha");
                context.update("form:tablaInferiorIzquierdaEM");
                context.update("form:tablaInferiorDerechaEM");
                context.update("form:exportarXML");
            }
        } else {
            context.execute("confirmarGuardar.show();");
        }
    }

    public void cambiarIndiceCortesProcesos(int indice, int celda) {
        if (permitirIndex == true) {
            indexCortesProcesos = indice;
            cualCelda = celda;
            tipoTabla = 1;
            nombreTabla = "CortesProcesos";
            if (tipoListaCortesProcesos == 0) {
                secRegistro = listaCortesProcesos.get(indexCortesProcesos).getSecuencia();
                if (cualCelda == 1) {
                    Proceso = listaCortesProcesos.get(indexCortesProcesos).getProceso().getDescripcion();
                }
                listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
            } else {
                secRegistro = listaCortesProcesos.get(indexCortesProcesos).getSecuencia();
                if (cualCelda == 1) {
                    Proceso = filtradolistaCortesProcesos.get(indexCortesProcesos).getProceso().getDescripcion();
                }
                listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
                listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
            }
        }
        tablaExportar = ":formExportar:datosCortesProcesosExportar";
        nombreArchivoExportar = "CortesProcesosXML";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
        context.update("form:exportarXML");
    }

    public void modificarComprobantes(int indice) {
        indexComprobante = indice;
        RequestContext context = RequestContext.getCurrentInstance();
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
        }
        indexComprobante = -1;
        secRegistro = null;
        tipoTabla = -1;
        modificacionesComprobantes = true;
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
            }
            indexCortesProcesos = -1;
            secRegistro = null;
            tipoTabla = -1;
            modificacionesCortesProcesos = true;
            context.update("form:datosCortesProcesos");
        } else if (confirmarCambio.equalsIgnoreCase("PROCESO")) {
            System.out.println("Entro " + Proceso);
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
            }
            modificacionesCortesProcesos = true;
            indexCortesProcesos = -1;
            secRegistro = null;
        }

        context.update("form:datosCortesProcesos");
    }

    //LOV PROCESOS
    public void llamarLOVProcesos(int indice) {
        indexCortesProcesos = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        context.update("form:ProcesosDialogo");
        context.execute("ProcesosDialogo.show()");
    }

    public void llamarLOVProcesosNuevo_Duplicado(int tipoN) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoN == 1) {
            tipoActualizacion = 1;
        } else {
            tipoActualizacion = 2;
        }
        context.update("form:ProcesosDialogo");
        context.execute("ProcesosDialogo.show()");
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
        context.execute("ProcesosDialogo.hide()");
        context.reset("formularioDialogos:lovProcesos:globalFilter");
        context.update("formularioDialogos:lovProcesos");
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
    }

    //BORRAR VC
    public void borrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                if (tipoListaComprobantes == 0) {
                    if (administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia()).isEmpty()) {
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
                    if (administrarComprobantes.cortesProcesosComprobante(filtradolistaComprobantes.get(indexComprobante).getSecuencia()).isEmpty()) {
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
                    if (administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado).isEmpty() && administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado).isEmpty()) {
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
                    if (administrarComprobantes.solucionesNodosCorteProcesoEmpleado(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado) == null && administrarComprobantes.solucionesNodosCorteProcesoEmpleador(filtradolistaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado) == null) {
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
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
    }
//CREAR COMPROBANTE Y CORTE PROCESO

    public void nuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCortesProcesos.isEmpty()) {
            if (indexComprobante >= 0) {
                context.execute("NuevoRegistroPagina.show()");
            }
        } else {
            if (tipoTabla == 0) {
                BigInteger sugerenciaNumero = administrarComprobantes.maximoNumeroComprobante().add(new BigInteger("1"));
                nuevoComprobante.setNumero(sugerenciaNumero);
                context.update("formularioDialogos:nuevoComprobante");
                context.execute("NuevoRegistroComprobantes.show()");
            } else if (tipoTabla == 1) {
                context.update("formularioDialogos:nuevoCorteProceso");
                context.execute("NuevoRegistroCortesProcesos.show()");
            }
        }
    }

    public void agregarNuevoComprobante() {
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoComprobante.getNumero() == null) {
            mensajeValidacion = " * Numero \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevoComprobante.getFecha() == null) {
            mensajeValidacion = " * Fecha Comprobante \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevoComprobante.getFechaentregado() == null) {
            mensajeValidacion = mensajeValidacion + " * Fecha Entregado \n";
            pasa = false;
        } else {
            pasa = true;
        }

        if (pasa == true) {
            if (bandera == 1) {
                numeroComprobanteC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "67";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                bandera = 0;
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
                RequestContext.getCurrentInstance().update("form:aceptar");
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
        boolean pasa = false;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoCorteProceso.getCorte() == null) {
            mensajeValidacion = " * Fecha de corte \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (nuevoCorteProceso.getProceso().getSecuencia() == null) {
            mensajeValidacion = " * Proceso \n";
            pasa = false;
        } else {
            pasa = true;
        }
        if (pasa == true) {
            /* if (bandera == 1) {
             //CERRAR FILTRADO
             vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
             vtcFecha.setFilterStyle("display: none; visibility: hidden;");
             vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
             vtcContrato.setFilterStyle("display: none; visibility: hidden;");
             vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
             vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
             vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
             vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
             vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
             vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
             vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
             vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
             vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
             vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
             RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
             bandera = 0;
             filtrarVTC = null;
             tipoLista = 0;
             }*/
            k++;
            l = BigInteger.valueOf(k);
            nuevoCorteProceso.setSecuencia(l);
            nuevoCorteProceso.setEmpleado(empleado);
            nuevoCorteProceso.setComprobante(seleccionComprobante);
            listaCortesProcesosCrear.add(nuevoCorteProceso);
            listaCortesProcesos.add(nuevoCorteProceso);
            nuevoCorteProceso = new CortesProcesos();
            nuevoCorteProceso.setProceso(new Procesos());
            nuevoCorteProceso.getProceso().setDescripcion(" ");
            context.update("form:datosCortesProcesos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
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
        nuevoCorteProceso.getProceso().setDescripcion(" ");
        indexCortesProcesos = -1;
        tipoTabla = -1;
        secRegistro = null;
    }

    //DUPLICADOS
    public void duplicarC() {
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                duplicarComprobante = new Comprobantes();
                k++;
                l = BigInteger.valueOf(k);
                if (tipoListaComprobantes == 0) {
                    duplicarComprobante.setSecuencia(l);
                    duplicarComprobante.setNumero(listaComprobantes.get(indexComprobante).getNumero());
                    duplicarComprobante.setFecha(listaComprobantes.get(indexComprobante).getFecha());
                    duplicarComprobante.setFechaentregado(listaComprobantes.get(indexComprobante).getFechaentregado());
                    duplicarComprobante.setEmpleado(listaComprobantes.get(indexComprobante).getEmpleado());
                    duplicarComprobante.setValor(new BigDecimal(0));
                } else if (tipoListaComprobantes == 1) {
                    duplicarComprobante.setSecuencia(l);
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
                k++;
                l = BigInteger.valueOf(k);
                if (tipoListaCortesProcesos == 0) {
                    duplicarCorteProceso.setSecuencia(l);
                    duplicarCorteProceso.setCorte(listaCortesProcesos.get(indexCortesProcesos).getCorte());
                    duplicarCorteProceso.setProceso(listaCortesProcesos.get(indexCortesProcesos).getProceso());
                    duplicarCorteProceso.setEmpleado(listaCortesProcesos.get(indexCortesProcesos).getEmpleado());
                    duplicarCorteProceso.setComprobante(seleccionComprobante);
                } else if (tipoListaCortesProcesos == 1) {
                    duplicarCorteProceso.setSecuencia(l);
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
        listaComprobantes.add(duplicarComprobante);
        listaComprobantesCrear.add(duplicarComprobante);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosComprobantes");
        context.execute("DuplicarRegistroComprobantes.hide()");
        indexComprobante = -1;
        tipoTabla = -1;
        modificacionesComprobantes = true;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            numeroComprobanteC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
            numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
            fechaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaC");
            fechaC.setFilterStyle("display: none; visibility: hidden;");
            fechaEntregaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
            fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
            altoScrollComprobante = "67";
            RequestContext.getCurrentInstance().update("form:datosComprobantes");
            bandera = 0;
            filtradolistaComprobantes = null;
            tipoListaComprobantes = 0;
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
        listaCortesProcesos.add(duplicarCorteProceso);
        listaCortesProcesosCrear.add(duplicarCorteProceso);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCortesProcesos");
        context.execute("DuplicarCortesProcesos.hide()");
        indexCortesProcesos = -1;
        tipoTabla = -1;
        modificacionesCortesProcesos = true;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        /*if (bandera == 1) {
         numeroComprobanteC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
         numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
         fechaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaC");
         fechaC.setFilterStyle("display: none; visibility: hidden;");
         fechaEntregaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
         fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
         altoScrollComprobante = "67";
         RequestContext.getCurrentInstance().update("form:datosComprobantes");
         bandera = 0;
         filtradolistaComprobantes = null;
         tipoListaComprobantes = 0;
         }*/
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
        if (tipoTabla == 0) {
            tablaComprobantes = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosComprobantes");
            if (bandera == 0) {
                numeroComprobanteC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("width: 40px;");
                fechaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("width: 60px;");
                fechaEntregaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("width: 60px;");
                altoScrollComprobante = "45";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                bandera = 1;

            } else if (bandera == 1) {
                numeroComprobanteC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:numeroComprobanteC");
                numeroComprobanteC.setFilterStyle("display: none; visibility: hidden;");
                fechaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaC");
                fechaC.setFilterStyle("display: none; visibility: hidden;");
                fechaEntregaC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosComprobantes:fechaEntregaC");
                fechaEntregaC.setFilterStyle("display: none; visibility: hidden;");
                altoScrollComprobante = "67";
                RequestContext.getCurrentInstance().update("form:datosComprobantes");
                bandera = 0;
                filtradolistaComprobantes = null;
                tipoListaComprobantes = 0;
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
        }
        indexComprobante = -1;
        secRegistro = null;
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
            }/* else if (nombreTabla.equals("HVHojasDeVida")) {
             secRastro = empleado.getSecuencia();
             resultado = administrarRastros.obtenerTabla(secRastro, nombreTabla.toUpperCase());
             }*/
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
                //System.out.println("Valor: " + nombreTabla);
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
        }
        secRegistro = null;
    }

    //SALIR Y REFRESCAR
    public void salir() {
        /* if (bandera == 1) {
         //CERRAR FILTRADO
         vtcFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFecha");
         vtcFecha.setFilterStyle("display: none; visibility: hidden;");
         vtcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcContrato");
         vtcContrato.setFilterStyle("display: none; visibility: hidden;");
         vtcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcTipoContrato");
         vtcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
         vtcCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcCiudad");
         vtcCiudad.setFilterStyle("display: none; visibility: hidden;");
         vtcFechaSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcFechaSP");
         vtcFechaSP.setFilterStyle("display: none; visibility: hidden;");
         vtcInicioFlexibilizacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcInicioFlexibilizacion");
         vtcInicioFlexibilizacion.setFilterStyle("display: none; visibility: hidden;");
         vtcObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVTCEmpleado:vtcObservacion");
         vtcObservacion.setFilterStyle("display: none; visibility: hidden;");
         RequestContext.getCurrentInstance().update("form:datosVTCEmpleado");
         bandera = 0;
         filtrarVTC = null;
         tipoLista = 0;
         }*/

        listaComprobantesBorrar.clear();
        listaCortesProcesosBorrar.clear();
        listaComprobantesCrear.clear();
        listaCortesProcesosCrear.clear();
        listaComprobantesModificar.clear();
        listaCortesProcesosModificar.clear();
        modificacionesComprobantes = false;
        modificacionesCortesProcesos = false;
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
        context.update("form:datosComprobantes");
        context.update("form:datosCortesProcesos");
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
    }

    //GUARDAR
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        //if (guardado == false) {
        if (modificacionesComprobantes == true) {
            System.out.println("Realizando Operaciones - Comprobantes");
            if (!listaComprobantesBorrar.isEmpty()) {
                for (int i = 0; i < listaComprobantesBorrar.size(); i++) {
                    System.out.println("Borrando Comprobante...");
                    administrarComprobantes.borrarComprobantes(listaComprobantesBorrar.get(i));
                }
                listaComprobantesBorrar.clear();
            }
            if (!listaComprobantesCrear.isEmpty()) {
                for (int i = 0; i < listaComprobantesCrear.size(); i++) {
                    System.out.println("Creando Comprobante...");
                    administrarComprobantes.crearComprobante(listaComprobantesCrear.get(i));
                }
                listaComprobantesCrear.clear();
            }
            if (!listaComprobantesModificar.isEmpty()) {
                administrarComprobantes.modificarComprobantes(listaComprobantesModificar);
                listaComprobantesModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito - Comprobantes");
            listaComprobantes = null;
            context.update("form:datosComprobantes");
            guardado = true;
            modificacionesComprobantes = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
            indexComprobante = -1;
        }
        if (modificacionesCortesProcesos == true) {
            System.out.println("Realizando Operaciones -Cortes Procesos");
            if (!listaCortesProcesosBorrar.isEmpty()) {
                for (int i = 0; i < listaCortesProcesosBorrar.size(); i++) {
                    System.out.println("Borrando Corte proceso...");
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
                    System.out.println("Creando Corte proceso...");
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
            System.out.println("Se guardaron los datos con exito - Cortes procesos");
            listaCortesProcesos = null;
            context.update("form:datosCortesProcesos");
            guardado = true;
            modificacionesCortesProcesos = false;
            context.update("form:aceptar");
            k = 0;
            indexCortesProcesos = -1;
        }
        //}
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
        if (listaProcesos.isEmpty()) {
            listaProcesos = administrarComprobantes.lovProcesos();
        }
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
}

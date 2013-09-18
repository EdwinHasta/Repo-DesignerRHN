package Controlador;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.SolucionesNodos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import java.io.IOException;
import java.io.Serializable;
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
    private Empleados empleado;
    private BigInteger secuenciaEmpleado;
    //TABLA 1
    private List<Comprobantes> listaComprobantes;
    private List<Comprobantes> filtradolistaComprobantes;
    //TABLA 2
    private List<CortesProcesos> listaCortesProcesos;
    private List<CortesProcesos> filtradolistaCortesProcesos;
    //TABLA 3
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    //TABLA 4
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;

    /*private List<Ciudades> listaCiudades;
     private Ciudades ciudadSelecionada;
     private List<Ciudades> filtradoCiudades;
     private List<MotivosContratos> listaMotivosContratos;
     private MotivosContratos MotivoContratoSelecionado;
     private List<MotivosContratos> filtradoMotivoContrato;
     private List<TiposContratos> listaTiposContratos;
     private List<TiposContratos> filtradoTiposContrato;
     private TiposContratos TipoContratoSelecionado;*/
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla
    //private Column vtcFecha, vtcContrato, vtcTipoContrato, vtcCiudad, vtcFechaSP, vtcInicioFlexibilizacion, vtcObservacion;
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
     /*private VigenciasTiposContratos editarVTC;
     private boolean cambioEditor, aceptarEditar;*/
    //duplicar
    private Comprobantes duplicarComprobante;
    //AUTOCOMPLETAR
    private String Motivo, TipoContrato, Ciudad;
    //RASTRO*/
    private BigInteger secRegistro;
    //INFORMATIVOS
    private int cualCelda, tipoLista, tipoTabla;

    public ControlComprobantes() {
        secuenciaEmpleado = BigInteger.valueOf(10661474);
        permitirIndex = true;
        //listaCiudades = new ArrayList<Ciudades>();
        //empleado = new Empleados();
        //ciudadSelecionada = new Ciudades();
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
        //editar
         /*editarVTC = new VigenciasTiposContratos();
         cambioEditor = false;
         aceptarEditar = true;*/
        cualCelda = -1;
        tipoLista = 0;
        tipoTabla = 0;
        //guardar
        guardado = true;
        //Crear 
        nuevoComprobante = new Comprobantes();
        secRegistro = null;
        tablaExportar = ":formExportar:datosComprobantesExportar";
    }

    public void recibirEmpleado(BigInteger sec) {
        secuenciaEmpleado = sec;
        empleado = administrarComprobantes.buscarEmpleado(secuenciaEmpleado);
        if (empleado != null) {
            listaComprobantes = administrarComprobantes.comprobantesEmpleado(empleado.getSecuencia());
            if (listaComprobantes != null) {
                listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(2).getSecuencia());
                if (listaCortesProcesos != null) {
                    listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                    if (listaCortesProcesos != null) {
                        listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
                    }
                }
            }
        }
    }
    //Ubicacion Celda y Tabla.

    public void cambiarIndiceComprobantes(int indice, int celda) {
        if (permitirIndex == true) {
            indexComprobante = indice;
            cualCelda = celda;
            tipoTabla = 0;
            secRegistro = listaComprobantes.get(indexComprobante).getSecuencia();
            listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia());
            if (!listaCortesProcesos.isEmpty()) {
                listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
                listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), secuenciaEmpleado);
            } else {
                listaSolucionesNodosEmpleado = null;
                listaSolucionesNodosEmpleador = null;
            }
            tablaExportar = ":formExportar:datosComprobantesExportar";
            nombreArchivoExportar = "ComprobantesXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCortesProcesos");
            context.update("form:tablaInferiorIzquierda");
            context.update("form:tablaInferiorDerecha");
            context.update("form:tablaInferiorIzquierdaEM");
            context.update("form:tablaInferiorDerechaEM");
            context.update("form:exportarXML");
        }
    }

    public void cambiarIndiceCortesProcesos(int indice, int celda) {
        /*if (permitirIndex == true) {
         index = indice;
         cualCelda = celda;
         secRegistro = vigenciasTiposContratoEmpleado.get(index).getSecuencia();
         if (cualCelda == 1) {
         Motivo = vigenciasTiposContratoEmpleado.get(index).getMotivocontrato().getNombre();
         } else if (cualCelda == 2) {
         TipoContrato = vigenciasTiposContratoEmpleado.get(index).getTipocontrato().getNombre();
         } else if (cualCelda == 3) {
         Ciudad = null;
         if (vigenciasTiposContratoEmpleado.get(index).getCiudad() != null) {
         Ciudad = vigenciasTiposContratoEmpleado.get(index).getCiudad().getNombre();
         }
         }
         }*/
        tipoTabla = 1;
        indexCortesProcesos = indice;
        listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);
        listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(indexCortesProcesos).getSecuencia(), secuenciaEmpleado);

        // tablaExportar = "formExportar:datosComprobantesExportar";
        // nombreArchivoExportar = "ComprobantesXML";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
        //context.update("form:exportarXML");
    }

    public void modificarComprobantes(int indice) {
        indexComprobante = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
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
        context.update("form:datosComprobantes");

    }

    //BORRAR VC
    public void borrar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoTabla == 0) {
            if (indexComprobante >= 0) {
                if (tipoLista == 0) {
                    if (administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(indexComprobante).getSecuencia()) == null) {
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
                    } else {
                        if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(listaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(listaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                            listaComprobantes.remove(indexComprobante);
                        } else {
                            context.execute("errorBorrarComprobante.show();");
                        }
                    }
                }
                if (tipoLista == 1) {
                    if (administrarComprobantes.cortesProcesosComprobante(filtradolistaComprobantes.get(indexComprobante).getSecuencia()) == null) {
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
                        int comprobantesIndex = listaComprobantes.indexOf(filtradolistaComprobantes.get(indexComprobante));
                        listaComprobantes.remove(comprobantesIndex);
                        listaComprobantes.remove(indexComprobante);
                    } else {
                        if (!listaComprobantesCrear.isEmpty() && listaComprobantesCrear.contains(filtradolistaComprobantes.get(indexComprobante))) {
                            int crearIndex = listaComprobantesCrear.indexOf(filtradolistaComprobantes.get(indexComprobante));
                            listaComprobantesCrear.remove(crearIndex);
                            filtradolistaComprobantes.remove(indexComprobante);
                        } else {
                            context.execute("errorBorrarComprobante.show();");
                        }
                    }
                }
                context.update("form:datosComprobantes");
                indexComprobante = -1;
            }
        } else if (tipoTabla == 1) {
        }
        secRegistro = null;
        tipoTabla = -1;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
    }
//CREAR VTC

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
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoComprobante.setSecuencia(l);
            nuevoComprobante.setEmpleado(empleado);
            listaComprobantesCrear.add(nuevoComprobante);

            listaComprobantes.add(nuevoComprobante);
            nuevoComprobante = new Comprobantes();
            context.update("form:datosComprobantes");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroComprobantes.hide()");
            indexComprobante = -1;
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
        secRegistro = null;
    }

    //DUPLICADOS
    public void duplicarC() {
        if (indexComprobante >= 0) {
            duplicarComprobante = new Comprobantes();
            k++;
            l = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarComprobante.setSecuencia(l);
                duplicarComprobante.setNumero(listaComprobantes.get(indexComprobante).getNumero());
                duplicarComprobante.setFecha(listaComprobantes.get(indexComprobante).getFecha());
                duplicarComprobante.setFechaentregado(listaComprobantes.get(indexComprobante).getFechaentregado());
            } else if (tipoLista == 1) {
                duplicarComprobante.setSecuencia(l);
                duplicarComprobante.setNumero(filtradolistaComprobantes.get(indexComprobante).getNumero());
                duplicarComprobante.setFecha(filtradolistaComprobantes.get(indexComprobante).getFecha());
                duplicarComprobante.setFechaentregado(filtradolistaComprobantes.get(indexComprobante).getFechaentregado());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicadoComprobante");
            context.execute("DuplicarRegistroComprobantes.show()");
            indexComprobante = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {

        listaComprobantes.add(duplicarComprobante);
        listaComprobantesCrear.add(duplicarComprobante);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosComprobantes");
        context.execute("DuplicarRegistroComprobantes.hide()");
        indexComprobante = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        /*if (bandera == 1) {
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
    }

    //LIMPIAR DUPLICADO REGISTRO COMPROBANTES
    public void limpiarDuplicadoComprobante() {
        duplicarComprobante = new Comprobantes();
        indexComprobante = -1;
        secRegistro = null;
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla;
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        if (tipoTabla == 0) {
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosComprobantesExportar");
            exporter.export(context, tabla, "ComprobantePDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexComprobante = -1;
        }
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla;
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        if (tipoTabla == 0) {
            tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosComprobantesExportar");
            exporter.export(context, tabla, "ComprobanteXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexComprobante = -1;
        }
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
}

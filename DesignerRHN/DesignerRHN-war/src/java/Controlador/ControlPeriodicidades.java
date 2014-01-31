/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Periodicidades;
import Entidades.Unidades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPeriodicidadesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlPeriodicidades implements Serializable {

    @EJB
    AdministrarPeriodicidadesInterface administrarPeriodicidades;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

//LISTA CENTRO COSTO
    private List<Periodicidades> listPeriodicidades;
    private List<Periodicidades> listPeriodicidadesBoton;
    private List<Periodicidades> filtrarPeriodicidades;
    private List<Periodicidades> crearPeriodicidades;
    private List<Periodicidades> modificarPeriodicidades;
    private List<Periodicidades> borrarPeriodicidades;
    private Periodicidades nuevoUnidad;
    private Periodicidades duplicarUnidad;
    private Periodicidades editarUnidad;

    private Column codigoCC, nombreUnidad,
            tipoUnidad, manoDeObra, codigoAT,
            obsoleto, codigoCTT, dimensiones;

    //AUTOCOMPLETAR
    private String unidadCodigo, unidadNombre, unidadBaseCodigo, unidadBaseDescripcion;
    private List<Unidades> listaUnidades;
    private List<Unidades> filtradoUnidades;
    private Unidades unidadSeleccionada;
    private List<Periodicidades> filterPericiodidades;
    private String nuevoTipoCCAutoCompletar;

    private Periodicidades PericiodidadesSeleccionado;
    private boolean banderaSeleccionPericiodidades;

    public ControlPeriodicidades() {
        permitirIndex = true;
        listPeriodicidades = null;
        listPeriodicidadesBoton = null;
        crearPeriodicidades = new ArrayList<Periodicidades>();
        modificarPeriodicidades = new ArrayList<Periodicidades>();
        borrarPeriodicidades = new ArrayList<Periodicidades>();
        editarUnidad = new Periodicidades();
        nuevoUnidad = new Periodicidades();
        duplicarUnidad = new Periodicidades();
        listaUnidades = null;
        aceptar = true;
        guardado = true;
        banderaSeleccionPericiodidades = false;

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("BETA CENTRO COSTO TIPO LISTA = " + tipoLista);
        System.err.println("PERMITIR INDEX = " + permitirIndex);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            System.err.println("CAMBIAR INDICE CUALCELDA = " + cualCelda);
            secRegistro = listPeriodicidades.get(index).getSecuencia();
            System.err.println("Sec Registro = " + secRegistro);
            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    unidadCodigo = listPeriodicidades.get(index).getUnidad().getCodigo();
                    System.err.println("grupoTipoUnidadAutocompletar = " + unidadCodigo);
                } else {
                    unidadCodigo = filtrarPeriodicidades.get(index).getUnidad().getCodigo();
                }
            }
            if (cualCelda == 3) {
                if (tipoLista == 0) {
                    unidadNombre = listPeriodicidades.get(index).getUnidad().getNombre();
                    System.err.println("grupoTipoUnidadAutocompletar = " + unidadNombre);
                } else {
                    unidadNombre = filtrarPeriodicidades.get(index).getUnidad().getNombre();
                }
            }
            if (cualCelda == 4) {
                if (tipoLista == 0) {
                    unidadBaseCodigo = listPeriodicidades.get(index).getUnidadbase().getCodigo();
                    System.err.println("grupoTipoUnidadAutocompletar = " + unidadBaseCodigo);
                } else {
                    unidadBaseCodigo = filtrarPeriodicidades.get(index).getUnidadbase().getCodigo();
                }
            }
            if (cualCelda == 5) {
                if (tipoLista == 0) {
                    unidadBaseDescripcion = listPeriodicidades.get(index).getUnidadbase().getNombre();
                    System.err.println("grupoTipoUnidadAutocompletar = " + unidadBaseDescripcion);
                } else {
                    unidadBaseDescripcion = filtrarPeriodicidades.get(index).getUnidadbase().getNombre();
                }
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void modificandoUnidad(int indice, String confirmarCambio, String valorConfirmar) {

        System.err.println("ENTRE A MODIFICAR CENTROCOSTO");
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        boolean banderita = false;
        boolean banderita1 = false;
        int contador = 0;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR CENTROCOSTO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearPeriodicidades.contains(listPeriodicidades.get(indice))) {
                    if (listPeriodicidades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPeriodicidades.size(); j++) {
                            if (j != indice) {
                                if (listPeriodicidades.get(indice).getCodigo().equals(listPeriodicidades.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (listPeriodicidades.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else if (listPeriodicidades.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;

                        }
                        context.update("form:ACEPTAR");

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                    if (filtrarPeriodicidades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPeriodicidades.size(); j++) {
                            if (j != indice) {
                                if (filtrarPeriodicidades.get(indice).getCodigo().equals(listPeriodicidades.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarPeriodicidades.size(); j++) {
                            if (j != indice) {
                                if (filtrarPeriodicidades.get(indice).getCodigo().equals(filtrarPeriodicidades.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }
                    }

                    if (filtrarPeriodicidades.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    }
                    if (filtrarPeriodicidades.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosPeriodicidades");
        } else if (confirmarCambio.equalsIgnoreCase("UNIDADES")) {
            System.err.println("ENTRE A MODIFICAR, CONFIRMAR CAMBIO ES UNIDADES");
            System.err.println("UNIDAD NOMBRE =  " + unidadNombre);
            System.err.println("UNIDAD CODIGO =  " + unidadCodigo);
            System.err.println("LISTA PERIODICIDADES INDICE NOMBRE UNIDAD " + listPeriodicidades.get(indice).getUnidad().getNombre());
            System.err.println("LISTA PERIODICIDADES INDICE CODIGO UNIDAD " + listPeriodicidades.get(indice).getUnidad().getCodigo());
            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    System.err.println("COMPLETAR CODIGO UNIDAD " + unidadCodigo);
                    listPeriodicidades.get(indice).getUnidad().setCodigo(unidadCodigo);
                } else {
                    filtrarPeriodicidades.get(indice).getUnidad().setCodigo(unidadCodigo);
                }
                getListaUnidades();
                System.out.println("VALOR A CONFIRMAR :  " + valorConfirmar.toUpperCase());
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listPeriodicidades.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        }
                    } else {
                        filtrarPeriodicidades.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        }
                    }
                    listaUnidades.clear();
                    listaUnidades = null;
                    getListaUnidades();
                    guardado = false;
                    context.update("form:ACEPTAR");

                } else {
                    permitirIndex = false;
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (cualCelda == 3) {
                if (tipoLista == 0) {
                    System.err.println("COMPLETAR   UNIDAD NOMBRE " + unidadNombre);
                    listPeriodicidades.get(indice).getUnidad().setNombre(unidadNombre);
                } else {

                    filtrarPeriodicidades.get(indice).getUnidad().setNombre(unidadNombre);
                }
                getListaUnidades();
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listPeriodicidades.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        }
                    } else {
                        filtrarPeriodicidades.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        }
                    }
                    listaUnidades.clear();
                    listaUnidades = null;
                    getListaUnidades();
                    guardado = false;
                    context.update("form:ACEPTAR");

                } else {
                    permitirIndex = false;
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = 0;
                }
            }

        } else if (confirmarCambio.equalsIgnoreCase("UNIDADESBASE")) {
            System.err.println("ENTRE A MODIFICAR, CONFIRMAR CAMBIO ES UNIDADES");
            System.err.println("UNIDAD BASE CODIGO =  " + unidadBaseCodigo);
            System.err.println("UNIDAD BASE DESCRIPCION =  " + unidadBaseDescripcion);
            System.err.println("LISTA PERIODICIDADES INDICE BASEUNIDAD DESCRIPCION " + listPeriodicidades.get(indice).getUnidadbase().getNombre());
            System.err.println("LISTA PERIODICIDADES INDICE BASEUNIDAD CODIGO " + listPeriodicidades.get(indice).getUnidadbase().getCodigo());
            if (cualCelda == 4) {
                if (tipoLista == 0) {
                    System.err.println("COMPLETAR BASE CODIGO UNIDAD " + unidadBaseCodigo);
                    listPeriodicidades.get(indice).getUnidadbase().setCodigo(unidadBaseCodigo);
                } else {
                    filtrarPeriodicidades.get(indice).getUnidadbase().setCodigo(unidadBaseCodigo);
                }
                getListaUnidades();
                System.out.println("VALOR A CONFIRMAR :  " + valorConfirmar.toUpperCase());
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listPeriodicidades.get(indice).setUnidadbase(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        }
                    } else {
                        filtrarPeriodicidades.get(indice).setUnidadbase(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        }
                    }
                    listaUnidades.clear();
                    listaUnidades = null;
                    getListaUnidades();
                    guardado = false;
                    context.update("form:ACEPTAR");

                } else {
                    permitirIndex = false;
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
            if (cualCelda == 5) {
                if (tipoLista == 0) {
                    System.err.println("COMPLETAR BASE DESCRIPCION " + unidadBaseDescripcion);
                    listPeriodicidades.get(indice).getUnidadbase().setNombre(unidadBaseDescripcion);
                } else {

                    filtrarPeriodicidades.get(indice).getUnidadbase().setNombre(unidadBaseDescripcion);
                }
                getListaUnidades();
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listPeriodicidades.get(indice).setUnidadbase(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(listPeriodicidades.get(indice));
                        }
                    } else {
                        filtrarPeriodicidades.get(indice).setUnidadbase(listaUnidades.get(indiceUnicoElemento));
                        if (modificarPeriodicidades.isEmpty()) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        } else if (!modificarPeriodicidades.contains(filtrarPeriodicidades.get(indice))) {
                            modificarPeriodicidades.add(filtrarPeriodicidades.get(indice));
                        }
                    }
                    listaUnidades.clear();
                    listaUnidades = null;
                    getListaUnidades();
                    guardado = false;
                    context.update("form:ACEPTAR");

                } else {
                    permitirIndex = false;
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = 0;
                }
            }

        }
        context.update("form:datosPeriodicidades");

    }

    public void cancelarModificacion() {
        try {
            System.out.println("entre a CONTROLBETACENTROSCOSTOS.cancelarModificacion");
            if (bandera == 1) {
                //CERRAR FILTRADO
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:nombreUnidad");
                nombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:tipoUnidad");
                tipoUnidad.setFilterStyle("display: none; visibility: hidden;");
                //3 
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                //7 
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                bandera = 0;
                filtrarPeriodicidades = null;
                tipoLista = 0;
            }

            borrarPeriodicidades.clear();
            crearPeriodicidades.clear();
            modificarPeriodicidades.clear();
            index = -1;
            k = 0;
            listPeriodicidades = null;
            guardado = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();

            context.update("form:datosPeriodicidades");
            context.update("form:ACEPTAR");
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.ModificarModificacion ERROR====================" + E.getMessage());
        }
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.asignarIndex \n");
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();

            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 2) {
                context.update("form:tiposPeriodicidadesDialogo");
                context.execute("tiposPeriodicidadesDialogo.show()");
                dig = -1;
            }
            if (dig == 3) {
                context.update("form:tiposPeriodicidadesDialogo");
                context.execute("tiposPeriodicidadesDialogo.show()");
                dig = -1;
            }
            if (dig == 4) {
                context.update("form:unidadesBaseDialogo");
                context.execute("unidadesBaseDialogo.show()");
                dig = -1;
            }
            if (dig == 5) {
                context.update("form:unidadesBaseDialogo");
                context.execute("unidadesBaseDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarUnidad() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarUnidad \n");
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarUnidad TIPOACTUALIZACION====" + tipoActualizacion);
            if (tipoActualizacion == 0) {
                listPeriodicidades.get(index).setUnidad(unidadSeleccionada);
                if (!crearPeriodicidades.contains(listPeriodicidades.get(index))) {
                    if (modificarPeriodicidades.isEmpty()) {
                        modificarPeriodicidades.add(listPeriodicidades.get(index));
                    } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(index))) {
                        modificarPeriodicidades.add(listPeriodicidades.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }

                }
                context.update("form:datosPeriodicidades");
                context.execute("tiposPeriodicidadesDialogo.hide()");
            } else if (tipoActualizacion == 1) {
                // context.reset("formularioDialogos:nuevaTipoPeriodicidades");
                System.out.println("Entro actualizar centro costo nuevo rgistro");
                nuevoUnidad.setUnidad(unidadSeleccionada);
                System.out.println("Centro Costo Seleccionado: " + nuevoUnidad.getUnidad().getNombre());
                context.update("formularioDialogos:nuevaTipoPeriodicidades");
            } else if (tipoActualizacion == 2) {
                duplicarUnidad.setUnidad(unidadSeleccionada);
                context.update("formularioDialogos:duplicarTipoPeriodicidades");
            }
            filtradoUnidades = null;
            unidadSeleccionada = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            permitirIndex = true;
        } catch (Exception e) {
            System.out.println("ERROR BETA .actualizarUnidad ERROR============" + e.getMessage());
        }
    }

    public void actualizarUnidadBase() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarUnidad \n");
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarUnidad TIPOACTUALIZACION====" + tipoActualizacion);
            if (tipoActualizacion == 0) {
                listPeriodicidades.get(index).setUnidadbase(unidadSeleccionada);
                if (!crearPeriodicidades.contains(listPeriodicidades.get(index))) {
                    if (modificarPeriodicidades.isEmpty()) {
                        modificarPeriodicidades.add(listPeriodicidades.get(index));
                    } else if (!modificarPeriodicidades.contains(listPeriodicidades.get(index))) {
                        modificarPeriodicidades.add(listPeriodicidades.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }

                }
                context.update("form:datosPeriodicidades");
                context.execute("unidadesBaseDialogo.hide()");
            } else if (tipoActualizacion == 1) {
                // context.reset("formularioDialogos:nuevaTipoPeriodicidades");
                System.out.println("Entro actualizar centro costo nuevo rgistro");
                nuevoUnidad.setUnidadbase(unidadSeleccionada);
                System.out.println("Centro Costo Seleccionado: " + nuevoUnidad.getUnidadbase().getNombre());
                context.update("formularioDialogos:nuevaTipoPeriodicidades");
            } else if (tipoActualizacion == 2) {
                duplicarUnidad.setUnidadbase(unidadSeleccionada);
                context.update("formularioDialogos:duplicarTipoPeriodicidades");
            }
            filtradoUnidades = null;
            unidadSeleccionada = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            permitirIndex = true;
        } catch (Exception e) {
            System.out.println("ERROR BETA .actualizarUnidad ERROR============" + e.getMessage());
        }
    }

    public void cancelarCambioTiposUnidad() {
        try {
            filtradoUnidades = null;
            unidadSeleccionada = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            permitirIndex = true;
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarCambioUnidad ERROR=====" + e.getMessage());
        }
    }

    public void llamadoDialogoBuscarPeriodicidades() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                banderaSeleccionPericiodidades = true;
                context.execute("confirmarGuardar.show()");

            } else {
                listPeriodicidadesBoton = null;
                getListPericiodidadesBoton();
                index = -1;
                context.update("formularioDialogos:lovPeriodicidades");
                context.execute("buscarPeriodicidadesDialogo.show()");

            }
        } catch (Exception e) {
            System.err.println("ERROR LLAMADO DIALOGO BUSCAR CENTROS COSTOS " + e);
        }

    }

    public void seleccionPeriodicidades() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            if (guardado == true) {
                listPeriodicidades.clear();
                System.err.println("seleccionPericiodidades " + PericiodidadesSeleccionado.getNombre());
                listPeriodicidades.add(PericiodidadesSeleccionado);
                System.err.println("listPeriodicidades tamaño " + listPeriodicidades.size());
                System.err.println("listPeriodicidades nombre " + listPeriodicidades.get(0).getNombre());
                PericiodidadesSeleccionado = null;
                filterPericiodidades = null;
                aceptar = true;
                context.update("form:datosPeriodicidades");
                context.execute("buscarPeriodicidadesDialogo.hide()");
                context.reset("formularioDialogos:lovPeriodicidades:globalFilter");
            } /*else {
             System.err.println("listPeriodicidades tamaño " + listPeriodicidades.size());
             System.err.println("listPeriodicidades nombre " + listPeriodicidades.get(0).getNombre());
             banderaSeleccionPericiodidades = true;
             context.execute("confirmarGuardar.show()");
             PericiodidadesSeleccionado = null;
             listPeriodicidades.clear();
             System.err.println("seleccionPericiodidades " + PericiodidadesSeleccionado.getNombre());
             listPeriodicidades.add(PericiodidadesSeleccionado);
             filterPericiodidades = null;
             aceptar = true;
             banderaModificacionEmpresa = 0;
             context.execute("buscarPeriodicidadesDialogo.hide()");
             context.reset("formularioDialogos:lovPeriodicidades:globalFilter");
             }*/


        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.seleccionaVigencia ERROR====" + e.getMessage());
        }
    }

    public void cancelarSeleccionUnidadPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            PericiodidadesSeleccionado = null;
            filterPericiodidades = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            context.update("form:aceptarNCC");

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarSeleccionVigencia ERROR====" + e.getMessage());
        }
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        System.out.println("1...");
        if (Campo.equals("TIPOSCENTROSCOSTOS")) {
            if (tipoNuevo == 1) {
                nuevoTipoCCAutoCompletar = nuevoUnidad.getUnidad().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoTipoCCAutoCompletar = duplicarUnidad.getUnidad().getNombre();
            }

        }
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSCENTROSCOSTOS")) {
            System.out.println(" nuevoTipoUnidad    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoUnidad.getUnidad().getNombre());

            if (!nuevoUnidad.getUnidad().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                nuevoUnidad.getUnidad().setNombre(nuevoTipoCCAutoCompletar);
                getListaUnidades();
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoUnidad.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    listaUnidades = null;
                    getListaUnidades();
                } else {
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoUnidad.getUnidad().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoUnidad.setUnidad(new Unidades());
                nuevoUnidad.getUnidad().setNombre(" ");
                System.out.println("NUEVO Valor nombre tcc: " + nuevoUnidad.getUnidad().getNombre());
            }
            context.update("formularioDialogos:nuevoGrupoTipoCC");
        }

    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSCENTROSCOSTOS")) {
            System.out.println("Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + duplicarUnidad.getUnidad().getNombre());

            if (!duplicarUnidad.getUnidad().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                duplicarUnidad.getUnidad().setNombre(nuevoTipoCCAutoCompletar);
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarUnidad.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    listaUnidades = null;
                    getListaUnidades();
                } else {
                    context.update("form:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                duplicarUnidad.getUnidad().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                duplicarUnidad.setUnidad(new Unidades());
                duplicarUnidad.getUnidad().setNombre(" ");
                System.out.println("Valor nombre tcc: " + duplicarUnidad.getUnidad().getNombre());
            }
            context.update("formularioDialogos:duplicarTipoPeriodicidades");
        }
    }

    public void asignarVariableTiposCC(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tiposPeriodicidadesDialogo");
        context.execute("tiposPeriodicidadesDialogo.show()");
    }

    public void limpiarNuevoPeriodicidades() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarNuevoPeriodicidades \n");
        try {
            nuevoUnidad = new Periodicidades();
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETACENTROSCOSTOS.LimpiarNuevoPeriodicidades ERROR=============================" + e.getMessage());
        }
    }

    public void agregarNuevoPeriodicidades() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.agregarNuevoPeriodicidades \n");
        try {
            int contador = 0;
            mensajeValidacion = " ";
            int duplicados = 0;
            RequestContext context = RequestContext.getCurrentInstance();

            if (nuevoUnidad.getCodigo() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoUnidad.getCodigo() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("codigo en Motivo Cambio Cargo: " + nuevoUnidad.getCodigo());

                for (int x = 0; x < listPeriodicidades.size(); x++) {
                    if (listPeriodicidades.get(x).getCodigo().equals(nuevoUnidad.getCodigo())) {
                        duplicados++;
                    }
                }
                System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

                if (duplicados > 0) {
                    mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                    System.out.println("Mensaje validacion : " + mensajeValidacion);
                } else {
                    System.out.println("bandera");
                    contador++;
                }
            }
            if (nuevoUnidad.getNombre() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoUnidad.getNombre().isEmpty()) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else if (nuevoUnidad.getNombre().equals(" ")) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";

            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (nuevoUnidad.getUnidad().getSecuencia() == null) {
                mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);

            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (contador == 3) {
                if (crearPeriodicidades.contains(nuevoUnidad)) {
                    System.out.println("Ya lo contengo.");
                } else {
                    crearPeriodicidades.add(nuevoUnidad);

                }
                listPeriodicidades.add(nuevoUnidad);
                context.update("form:datosPeriodicidades");
                nuevoUnidad = new Periodicidades();
                // index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                if (bandera == 1) {
                    codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCC");
                    codigoCC.setFilterStyle("display: none; visibility: hidden;");
                    nombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:nombreUnidad");
                    nombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                    tipoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:tipoUnidad");
                    tipoUnidad.setFilterStyle("display: none; visibility: hidden;");
                    manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:manoDeObra");
                    manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                    codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoAT");
                    codigoAT.setFilterStyle("display: none; visibility: hidden;");
                    obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:obsoleto");
                    obsoleto.setFilterStyle("display: none; visibility: hidden;");
                    codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCTT");
                    codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                    dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:dimensiones");
                    dimensiones.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosPeriodicidades");

                    bandera = 0;
                    filtrarPeriodicidades = null;
                    tipoLista = 0;
                }
                mensajeValidacion = " ";
                RequestContext.getCurrentInstance().execute("NuevoRegistroPeriodicidades.hide()");

            } else {
                contador = 0;
                context.update("form:validacionDuplicarVigencia");
                context.execute("validacionDuplicarVigencia.show()");
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.agregarNuevoPeriodicidades ERROR===========================" + e.getMessage());
        }
    }

    public void cargarUnidadesNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposPeriodicidadesDialogo");
            context.execute("tiposPeriodicidadesDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposPeriodicidadesDialogo");
            context.execute("tiposPeriodicidadesDialogo.show()");
        }
    }

    public void mostrarDialogoPeriodicidades() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoUnidad = new Periodicidades();
        nuevoUnidad.setUnidad(new Unidades());
        index = -1;
        context.update("formularioDialogos:nuevoPeriodicidades");
        context.execute("NuevoRegistroPeriodicidades.show()");
    }

    public void mostrarDialogoListaEmpresas() {
        RequestContext context = RequestContext.getCurrentInstance();
        index = -1;
        context.execute("buscarPeriodicidadesDialogo.show()");
    }

    public void duplicandoPeriodicidades() {
        try {
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.duplicarPeriodicidades INDEX===" + index);

            if (index >= 0) {
                System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.duplicarPeriodicidades TIPOLISTA===" + tipoLista);

                duplicarUnidad = new Periodicidades();
                k++;
                l = BigInteger.valueOf(k);
                if (tipoLista == 0) {
                    duplicarUnidad.setSecuencia(l);
                    duplicarUnidad.setCodigo(listPeriodicidades.get(index).getCodigo());
                    duplicarUnidad.setNombre(listPeriodicidades.get(index).getNombre());
                    duplicarUnidad.setUnidad(listPeriodicidades.get(index).getUnidad());
                    duplicarUnidad.setUnidadbase(listPeriodicidades.get(index).getUnidadbase());

                }
                if (tipoLista == 1) {

                    duplicarUnidad.setSecuencia(l);
                    duplicarUnidad.setCodigo(filtrarPeriodicidades.get(index).getCodigo());
                    duplicarUnidad.setNombre(filtrarPeriodicidades.get(index).getNombre());
                    duplicarUnidad.setUnidad(filtrarPeriodicidades.get(index).getUnidad());
                    duplicarUnidad.setUnidadbase(filtrarPeriodicidades.get(index).getUnidadbase());

                }

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicarPeriodicidades");
                context.execute("DuplicarRegistroPeriodicidades.show()");
                index = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.DuplicarPeriodicidades ERROR===============" + e.getMessage());
        }
    }

    public void limpiarDuplicarPeriodicidades() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarDuplicarPeriodicidades \n");
        try {
            duplicarUnidad = new Periodicidades();
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.limpiarDuplicarPeriodicidades ERROR========" + e.getMessage());
        }

    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLTIPOSCENTROSCOSTOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;

        if (duplicarUnidad.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + duplicarUnidad.getCodigo());

            for (int x = 0; x < listPeriodicidades.size(); x++) {
                if (listPeriodicidades.get(x).getCodigo().equals(duplicarUnidad.getCodigo())) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }

        }
        if (duplicarUnidad.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarUnidad.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarUnidad.getUnidad().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 3) {
            if (crearPeriodicidades.contains(duplicarUnidad)) {
                System.out.println("Ya lo contengo.");
            } else {
                listPeriodicidades.add(duplicarUnidad);
            }
            crearPeriodicidades.add(duplicarUnidad);
            context.update("form:datosPeriodicidades");

            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:nombreUnidad");
                nombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:tipoUnidad");
                tipoUnidad.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPeriodicidades");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCTT");
                tipoUnidad.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosPeriodicidades");
                bandera = 0;
                filtrarPeriodicidades = null;
                tipoLista = 0;
            }
            duplicarUnidad = new Periodicidades();
            duplicarUnidad.setUnidad(new Unidades());
            RequestContext.getCurrentInstance().execute("DuplicarRegistroPeriodicidades.hide()");
            mensajeValidacion = " ";

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    private BigInteger contarCPCompromisosPeriodicidad;
    private BigInteger contarDetallesPeriodicidadesPeriodicidad;
    private BigInteger contarEersPrestamosDtosPeriodicidad;
    private BigInteger contarEmpresasPeriodicidad;
    private BigInteger contarFormulasAseguradasPeriodicidad;
    private BigInteger contarFormulasContratosPeriodicidad;
    private BigInteger contarGruposProvisionesPeriodicidad;
    private BigInteger contarNovedadPeriodicidad;
    private BigInteger contadorInterconHelisa;
    private BigInteger contadorInterconSapbo;

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        System.out.println("TIPOLISTA = " + tipoLista);
        BigInteger pruebilla;
        try {
            if (tipoLista == 0) {
                //  System.err.println("Control Secuencia de ControlTiposEmpresas secuencia centro costo" + listPeriodicidades.get(index).getSecuencia());
                contarCPCompromisosPeriodicidad = administrarPeriodicidades.contarCPCompromisosPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorComprobantesContables: " + contadorComprobantesContables);
                contarDetallesPeriodicidadesPeriodicidad = administrarPeriodicidades.contarDetallesPeriodicidadesPeriodicidad(listPeriodicidades.get(index).getSecuencia());;
                // System.out.println("SE TOTEA ControlBetaCC contadorDetallesCCConsolidador: " + contadorDetallesCCConsolidador);
                contarEersPrestamosDtosPeriodicidad = administrarPeriodicidades.contarEersPrestamosDtosPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC contadorEmpresas: " + contadorEmpresas);
                contarEmpresasPeriodicidad = administrarPeriodicidades.contarEmpresasPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorEstructuras " + contadorEstructuras);
                contarFormulasAseguradasPeriodicidad = administrarPeriodicidades.contarFormulasAseguradasPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorDetallesCCDetalle" + contadorDetallesCCDetalle);
                contarFormulasContratosPeriodicidad = administrarPeriodicidades.contarFormulasContratosPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconCondor" + contadorInterconCondor);
                contarGruposProvisionesPeriodicidad = administrarPeriodicidades.contarGruposProvisionesPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC:contadorInterconDynamics " + contadorInterconDynamics);
                contarNovedadPeriodicidad = administrarPeriodicidades.contarNovedadPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconGeneral" + contadorInterconGeneral);
                contadorInterconHelisa = administrarPeriodicidades.contarParametrosCambiosMasivosPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                //            System.out.println("ControlBetaCC: contadorInterconHelisa" + contadorInterconHelisa);
                contadorInterconSapbo = administrarPeriodicidades.contarVigenciasFormasPagosPeriodicidad(listPeriodicidades.get(index).getSecuencia());
                //            System.out.println("ControlBetaCC: contadorInterconSapbo" + contadorInterconSapbo);
            } else {
                //               System.err.println(" FILTRAR  FILTRAR  FILTRAR  FILTRAR Control Secuencia de ControlTiposEmpresas secuencia centro costo" + listPeriodicidades.get(index).getSecuencia());
                contarCPCompromisosPeriodicidad = administrarPeriodicidades.contarCPCompromisosPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorComprobantesContables: " + contadorComprobantesContables);
                contarDetallesPeriodicidadesPeriodicidad = administrarPeriodicidades.contarDetallesPeriodicidadesPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());;
                // System.out.println("SE TOTEA ControlBetaCC contadorDetallesCCConsolidador: " + contadorDetallesCCConsolidador);
                contarEersPrestamosDtosPeriodicidad = administrarPeriodicidades.contarEersPrestamosDtosPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC contadorEmpresas: " + contadorEmpresas);
                contarEmpresasPeriodicidad = administrarPeriodicidades.contarEmpresasPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorEstructuras " + contadorEstructuras);
                contarFormulasAseguradasPeriodicidad = administrarPeriodicidades.contarFormulasAseguradasPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorDetallesCCDetalle" + contadorDetallesCCDetalle);
                contarFormulasContratosPeriodicidad = administrarPeriodicidades.contarFormulasContratosPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconCondor" + contadorInterconCondor);
                contarGruposProvisionesPeriodicidad = administrarPeriodicidades.contarGruposProvisionesPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC:contadorInterconDynamics " + contadorInterconDynamics);
                contarNovedadPeriodicidad = administrarPeriodicidades.contarNovedadPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconGeneral" + contadorInterconGeneral);
                contadorInterconHelisa = administrarPeriodicidades.contarParametrosCambiosMasivosPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                //            System.out.println("ControlBetaCC: contadorInterconHelisa" + contadorInterconHelisa);
                contadorInterconSapbo = administrarPeriodicidades.contarVigenciasFormasPagosPeriodicidad(filtrarPeriodicidades.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconSapbo" + contadorInterconSapbo);
                //       System.out.println("ControlBetaCC: contadorVigenciasProrrateos" + contadorVigenciasProrrateos);
                //pruebilla = administrarPeriodicidades.sumaTotal(filtrarPeriodicidades.get(index).getSecuencia());
                //System.err.println("pruebilla :::::::::::::::::::::::::::::::: " + pruebilla);
            }
            if (contarDetallesPeriodicidadesPeriodicidad.equals(new BigInteger("0"))
                    && contarEersPrestamosDtosPeriodicidad.equals(new BigInteger("0"))
                    && contarEmpresasPeriodicidad.equals(new BigInteger("0"))
                    && contarFormulasAseguradasPeriodicidad.equals(new BigInteger("0"))
                    && contarFormulasContratosPeriodicidad.equals(new BigInteger("0"))
                    && contarGruposProvisionesPeriodicidad.equals(new BigInteger("0"))
                    && contarNovedadPeriodicidad.equals(new BigInteger("0"))
                    && contadorInterconHelisa.equals(new BigInteger("0"))
                    && contadorInterconSapbo.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoUnidad();
            } else {

                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarCPCompromisosPeriodicidad = new BigInteger("-1");
                contarDetallesPeriodicidadesPeriodicidad = new BigInteger("-1");
                contarEersPrestamosDtosPeriodicidad = new BigInteger("-1");
                contarEmpresasPeriodicidad = new BigInteger("-1");
                contarFormulasAseguradasPeriodicidad = new BigInteger("-1");
                contarFormulasContratosPeriodicidad = new BigInteger("-1");
                contarGruposProvisionesPeriodicidad = new BigInteger("-1");
                contarNovedadPeriodicidad = new BigInteger("-1");
                contadorInterconHelisa = new BigInteger("-1");
                contadorInterconSapbo = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROL BETA CENTROS COSTOS verificarBorrado ERROR " + e);
        }
    }

    public void borrandoUnidad() {
        try {
            if (index >= 0) {
                if (tipoLista == 0) {
                    if (!modificarPeriodicidades.isEmpty() && modificarPeriodicidades.contains(listPeriodicidades.get(index))) {
                        int modIndex = modificarPeriodicidades.indexOf(listPeriodicidades.get(index));
                        modificarPeriodicidades.remove(modIndex);
                        borrarPeriodicidades.add(listPeriodicidades.get(index));
                    } else if (!crearPeriodicidades.isEmpty() && crearPeriodicidades.contains(listPeriodicidades.get(index))) {
                        int crearIndex = crearPeriodicidades.indexOf(listPeriodicidades.get(index));
                        crearPeriodicidades.remove(crearIndex);
                    } else {

                        borrarPeriodicidades.add(listPeriodicidades.get(index));
                    }
                    listPeriodicidades.remove(index);
                }
                if (tipoLista == 1) {
                    if (!modificarPeriodicidades.isEmpty() && modificarPeriodicidades.contains(filtrarPeriodicidades.get(index))) {
                        System.out.println("\n 6 ENTRE A CONTROLBETACENTROSCOSTOS.borrarUnidad tipolista==1 try if if if filtrarPeriodicidades.get(index).getCodigo()" + filtrarPeriodicidades.get(index).getCodigo());

                        int modIndex = modificarPeriodicidades.indexOf(filtrarPeriodicidades.get(index));
                        modificarPeriodicidades.remove(modIndex);
                        borrarPeriodicidades.add(filtrarPeriodicidades.get(index));
                    } else if (!crearPeriodicidades.isEmpty() && crearPeriodicidades.contains(filtrarPeriodicidades.get(index))) {
                        System.out.println("\n 7 ENTRE A CONTROLBETACENTROSCOSTOS.borrarUnidad tipolista==1 try if if if filtrarPeriodicidades.get(index).getCodigo()" + filtrarPeriodicidades.get(index).getCodigo());
                        int crearIndex = crearPeriodicidades.indexOf(filtrarPeriodicidades.get(index));
                        crearPeriodicidades.remove(crearIndex);
                    } else {
                        System.out.println("\n 8 ENTRE A CONTROLBETACENTROSCOSTOS.borrarUnidad tipolista==1 try if if if filtrarPeriodicidades.get(index).getCodigo()" + filtrarPeriodicidades.get(index).getCodigo());
                        borrarPeriodicidades.add(filtrarPeriodicidades.get(index));
                    }
                    int VCIndex = listPeriodicidades.indexOf(filtrarPeriodicidades.get(index));
                    listPeriodicidades.remove(VCIndex);
                    filtrarPeriodicidades.remove(index);
                }

                RequestContext context = RequestContext.getCurrentInstance();
                index = -1;
                System.err.println("verificar Borrado " + guardado);
                if (guardado == true) {
                    guardado = false;
                }
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                context.update("form:datosPeriodicidades");
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.BorrarUnidad ERROR=====================" + e.getMessage());
        }
    }

    public void guardarCambiosPeriodicidad() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarPeriodicidades.isEmpty()) {
                administrarPeriodicidades.borrarPeriodicidades(borrarPeriodicidades);
                //mostrarBorrados
                registrosBorrados = borrarPeriodicidades.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarPeriodicidades.clear();
            }
            if (!crearPeriodicidades.isEmpty()) {
                administrarPeriodicidades.crearPeriodicidades(crearPeriodicidades);
                crearPeriodicidades.clear();
            }
            if (!modificarPeriodicidades.isEmpty()) {
                administrarPeriodicidades.modificarPeriodicidades(modificarPeriodicidades);
                modificarPeriodicidades.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listPeriodicidades = null;
            context.update("form:datosTipoUnidad");
            k = 0;
            guardado = true;

            if (banderaSeleccionPericiodidades == true) {
                listPeriodicidadesBoton = null;
                getListPericiodidadesBoton();
                index = -1;
                context.update("formularioDialogos:lovPeriodicidades");
                context.execute("buscarPeriodicidadesDialogo.show()");
                banderaSeleccionPericiodidades = false;
            }
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.activarCtrlF11 \n");

        try {

            if (bandera == 0) {
                System.out.println("Activar");
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCC");
                codigoCC.setFilterStyle("width: 80px");
                nombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:nombreUnidad");
                nombreUnidad.setFilterStyle("width: 105px");
                tipoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:tipoUnidad");
                tipoUnidad.setFilterStyle("width: 100px");
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:manoDeObra");
                manoDeObra.setFilterStyle("width: 90px");
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoAT");
                codigoAT.setFilterStyle("width: 60px");
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:obsoleto");
                obsoleto.setFilterStyle("width: 35px");
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCTT");
                codigoCTT.setFilterStyle("width: 90px");
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:dimensiones");
                dimensiones.setFilterStyle("width: 15px");
                RequestContext.getCurrentInstance().update("form:datosPeriodicidades");
                bandera = 1;
            } else if (bandera == 1) {
                System.out.println("Desactivar");
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                nombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:nombreUnidad");
                nombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                tipoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:tipoUnidad");
                tipoUnidad.setFilterStyle("display: none; visibility: hidden;");
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPeriodicidades:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosPeriodicidades");
                bandera = 0;
                filtrarPeriodicidades = null;
                tipoLista = 0;
            }
        } catch (Exception e) {

            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.activarCtrlF11 ERROR====================" + e.getMessage());
        }
    }

    public void editarCelda() {
        try {
            System.out.println("\n ENTRE A editarCelda INDEX  " + index);
            if (index >= 0) {
                System.out.println("\n ENTRE AeditarCelda TIPOLISTA " + tipoLista);
                if (tipoLista == 0) {
                    editarUnidad = listPeriodicidades.get(index);
                }
                if (tipoLista == 1) {
                    editarUnidad = filtrarPeriodicidades.get(index);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                System.out.println("CONTROLBETACENTROSCOSTOS: Entro a editar... valor celda: " + cualCelda);
                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarCCC");
                    context.execute("editarCCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarNCC");
                    context.execute("editarNCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarTCC");
                    context.execute("editarTCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 3) {
                    context.update("formularioDialogos:editarMO");
                    context.execute("editarMO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 4) {
                    context.update("formularioDialogos:editarCAT");
                    context.execute("editarCAT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 5) {
                    context.update("formularioDialogos:editarO");
                    context.execute("editarO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 6) {
                    context.update("formularioDialogos:editarCCTT");
                    context.execute("editarCCTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 7) {
                    context.update("formularioDialogos:editarD");
                    context.execute("editarD.show()");
                    cualCelda = -1;
                }
            }
            index = -1;
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.editarCelDa ERROR=====================" + E.getMessage());
        }
    }

    public void listaValoresBoton() {

        try {
            if (index >= 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                if (cualCelda == 2) {
                    System.out.println("\n ListaValoresBoton \n");
                    context.update("formularioDialogos:tiposPeriodicidadesDialogo");
                    context.execute("tiposPeriodicidadesDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
        } catch (Exception e) {
            System.out.println("\n ERROR CONTROLBETACENTROSCOSTOS.listaValoresBoton ERROR====================" + e.getMessage());

        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPeriodicidadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Periodicidades", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    /**
     *
     * @throws IOException
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPeriodicidadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Periodicidades", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listPeriodicidades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CENTROSCOSTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
                System.out.println("resultado: " + resultado);
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
            if (administrarRastros.verificarHistoricosTabla("CENTROSCOSTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//-----------------------------------------------------------------------------**
    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public List<Periodicidades> getListPeriodicidades() {
        if (listPeriodicidades == null) {
            listPeriodicidades = administrarPeriodicidades.consultarPeriodicidades();
        }
        return listPeriodicidades;
    }

    public void setListPeriodicidades(List<Periodicidades> listPeriodicidades) {
        this.listPeriodicidades = listPeriodicidades;
    }

    public List<Periodicidades> getListPericiodidadesBoton() {
        try {
            if (listPeriodicidadesBoton == null) {
                //listPeriodicidadesBoton = administrarPeriodicidades.consultarPericiodidades(empresaSeleccionada.getSecuencia());
                listPeriodicidadesBoton = listPeriodicidades;
            }
            return listPeriodicidadesBoton;
        } catch (Exception e) {
            System.out.println("ControlCentrosCosto: Error al recibir los Periodicidades de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }

    public void setListPericiodidadesBoton(List<Periodicidades> listPeriodicidadesBoton) {
        this.listPeriodicidadesBoton = listPeriodicidadesBoton;
    }

    public void setListPericiodidades(List<Periodicidades> listPeriodicidades) {
        this.listPeriodicidades = listPeriodicidades;
    }

    public List<Periodicidades> getFiltrarPeriodicidades() {
        return filtrarPeriodicidades;
    }

    public void setFiltrarPeriodicidades(List<Periodicidades> filtrarPeriodicidades) {
        this.filtrarPeriodicidades = filtrarPeriodicidades;
    }

    public Periodicidades getNuevoUnidad() {
        if (nuevoUnidad == null) {
            nuevoUnidad = new Periodicidades();
        }
        return nuevoUnidad;
    }

    public void setNuevoUnidad(Periodicidades nuevoUnidad) {
        this.nuevoUnidad = nuevoUnidad;
    }

    public Periodicidades getDuplicarUnidad() {
        return duplicarUnidad;
    }

    public void setDuplicarUnidad(Periodicidades duplicarUnidad) {
        this.duplicarUnidad = duplicarUnidad;
    }

    public List<Unidades> getListaUnidades() {
        if (listaUnidades == null) {
            listaUnidades = administrarPeriodicidades.consultarLOVUnidades();
        }
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidades> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public List<Unidades> getFiltradoUnidades() {
        return filtradoUnidades;
    }

    public void setFiltradoUnidades(List<Unidades> filtradoUnidades) {
        this.filtradoUnidades = filtradoUnidades;
    }

    public Unidades getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidades unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Periodicidades> getFilterPericiodidades() {
        return filterPericiodidades;
    }

    public void setFilterPericiodidades(List<Periodicidades> filterPericiodidades) {
        this.filterPericiodidades = filterPericiodidades;
    }

    public Periodicidades getPericiodidadesSeleccionado() {
        return PericiodidadesSeleccionado;
    }

    public void setPericiodidadesSeleccionado(Periodicidades PericiodidadesSeleccionado) {
        this.PericiodidadesSeleccionado = PericiodidadesSeleccionado;
    }

    public Periodicidades getEditarUnidad() {
        return editarUnidad;
    }

    public void setEditarUnidad(Periodicidades editarUnidad) {
        this.editarUnidad = editarUnidad;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

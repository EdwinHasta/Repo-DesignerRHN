/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCentroCostosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class ControlBetaCentrosCostos implements Serializable {
    
    @EJB
    AdministrarCentroCostosInterface administrarCentroCostos;
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
//EMPRESA
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtradoListaEmpresas;
    
    private Empresas empresaSeleccionada;
    private int banderaModificacionEmpresa;
    private int indiceEmpresaMostrada;
//LISTA CENTRO COSTO
    private List<CentrosCostos> listCentrosCostosPorEmpresa;
    private List<CentrosCostos> listCentrosCostosPorEmpresaBoton;
    private List<CentrosCostos> filtrarCentrosCostos;
    private List<CentrosCostos> crearCentrosCostos;
    private List<CentrosCostos> modificarCentrosCostos;
    private List<CentrosCostos> borrarCentrosCostos;
    private CentrosCostos nuevoCentroCosto;
    private CentrosCostos duplicarCentroCosto;
    private CentrosCostos editarCentroCosto;
    private CentrosCostos centroCostoBetaSeleccionado;
    
    private Column codigoCC, nombreCentroCosto,
            tipoCentroCosto, manoDeObra, codigoAT,
            obsoleto, codigoCTT, dimensiones;

    //AUTOCOMPLETAR
    private String grupoTipoCentroCostoAutocompletar;
    private List<TiposCentrosCostos> listaTiposCentrosCostos;
    private List<TiposCentrosCostos> filtradoTiposCentrosCostos;
    private TiposCentrosCostos tipoCentroCostoSeleccionado;
    private List<CentrosCostos> filterCentrosCostosPorEmpresa;
    private String nuevoTipoCCAutoCompletar;
    private Empresas backUpEmpresaActual;
    
    private CentrosCostos CentrosCostosPorEmpresaSeleccionado;
    private boolean banderaSeleccionCentrosCostosPorEmpresa;
    
    private int tamano;
    
    public ControlBetaCentrosCostos() {
        permitirIndex = true;
        listaEmpresas = null;
        empresaSeleccionada = null;
        indiceEmpresaMostrada = 0;
        listCentrosCostosPorEmpresa = null;
        listCentrosCostosPorEmpresaBoton = null;
        crearCentrosCostos = new ArrayList<CentrosCostos>();
        modificarCentrosCostos = new ArrayList<CentrosCostos>();
        borrarCentrosCostos = new ArrayList<CentrosCostos>();
        editarCentroCosto = new CentrosCostos();
        nuevoCentroCosto = new CentrosCostos();
        duplicarCentroCosto = new CentrosCostos();
        listaTiposCentrosCostos = null;
        aceptar = true;
        filtradoListaEmpresas = null;
        guardado = true;
        banderaSeleccionCentrosCostosPorEmpresa = false;
        tamano = 270;
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
            secRegistro = listCentrosCostosPorEmpresa.get(index).getSecuencia();
            System.err.println("Sec Registro = " + secRegistro);
            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    grupoTipoCentroCostoAutocompletar = listCentrosCostosPorEmpresa.get(index).getTipocentrocosto().getNombre();
                    System.err.println("grupoTipoCentroCostoAutocompletar = " + grupoTipoCentroCostoAutocompletar);
                    
                } else {
                    grupoTipoCentroCostoAutocompletar = filtrarCentrosCostos.get(index).getTipocentrocosto().getNombre();
                }
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }
    
    public void modificandoCentroCosto(int indice, String confirmarCambio, String valorConfirmar) {
        
        System.err.println("ENTRE A MODIFICAR CENTROCOSTO");
        index = indice;
        banderaModificacionEmpresa = 1;
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
                if (!crearCentrosCostos.contains(listCentrosCostosPorEmpresa.get(indice))) {
                    if (listCentrosCostosPorEmpresa.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listCentrosCostosPorEmpresa.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listCentrosCostosPorEmpresa.size(); j++) {
                            if (j != indice) {
                                if (listCentrosCostosPorEmpresa.get(indice).getCodigo().equals(listCentrosCostosPorEmpresa.get(j).getCodigo())) {
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
                    
                    if (listCentrosCostosPorEmpresa.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else if (listCentrosCostosPorEmpresa.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else {
                        banderita1 = true;
                    }
                    
                    if (banderita == true && banderita1 == true) {
                        if (modificarCentrosCostos.isEmpty()) {
                            modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(indice));
                        } else if (!modificarCentrosCostos.contains(listCentrosCostosPorEmpresa.get(indice))) {
                            modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(indice));
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
                
                if (!crearCentrosCostos.contains(filtrarCentrosCostos.get(indice))) {
                    if (filtrarCentrosCostos.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarCentrosCostos.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listCentrosCostosPorEmpresa.size(); j++) {
                            if (j != indice) {
                                if (filtrarCentrosCostos.get(indice).getCodigo().equals(listCentrosCostosPorEmpresa.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarCentrosCostos.size(); j++) {
                            if (j != indice) {
                                if (filtrarCentrosCostos.get(indice).getCodigo().equals(filtrarCentrosCostos.get(j).getCodigo())) {
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
                    
                    if (filtrarCentrosCostos.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    }
                    if (filtrarCentrosCostos.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                    } else {
                        banderita1 = true;
                    }
                    
                    if (banderita == true && banderita1 == true) {
                        if (modificarCentrosCostos.isEmpty()) {
                            modificarCentrosCostos.add(filtrarCentrosCostos.get(indice));
                        } else if (!modificarCentrosCostos.contains(filtrarCentrosCostos.get(indice))) {
                            modificarCentrosCostos.add(filtrarCentrosCostos.get(indice));
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
            context.update("form:datosCentrosCostos");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOCENTROCOSTO")) {
            System.err.println("ENTRE A MODIFICAR TIPO CENTRO COSTO, CONFIRMAR CAMBIO ES TIPOCENTROCOSTO");
            System.err.println("grupoTipoCentroCostoAutocompletar " + grupoTipoCentroCostoAutocompletar);
            System.err.println("listCentrosCostosPorEmpresa.get(indice).getTipocentrocosto().getNombre() " + listCentrosCostosPorEmpresa.get(indice).getTipocentrocosto().getNombre());
            if (tipoLista == 0) {
                System.err.println("COMPLETAR   grupoTipoCentroCostoAutocompletar " + grupoTipoCentroCostoAutocompletar);
                listCentrosCostosPorEmpresa.get(indice).getTipocentrocosto().setNombre(grupoTipoCentroCostoAutocompletar);
            } else {
                
                filtrarCentrosCostos.get(indice).getTipocentrocosto().setNombre(grupoTipoCentroCostoAutocompletar);
            }
            getListaTiposCentrosCostos();
            for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
                if (listaTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listCentrosCostosPorEmpresa.get(indice).setTipocentrocosto(listaTiposCentrosCostos.get(indiceUnicoElemento));
                } else {
                    filtrarCentrosCostos.get(indice).setTipocentrocosto(listaTiposCentrosCostos.get(indiceUnicoElemento));
                }
                listaTiposCentrosCostos.clear();
                listaTiposCentrosCostos = null;
                getListaTiposCentrosCostos();
            } else {
                permitirIndex = false;
                context.update("form:tiposCentrosCostosDialogo");
                context.execute("tiposCentrosCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        context.update("form:datosCentrosCostos");
        
    }
    
    public void cancelarModificacion() {
        try {
            System.out.println("entre a CONTROLBETACENTROSCOSTOS.cancelarModificacion");
           FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                //0
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                //7 
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                tamano = 270;
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            
            borrarCentrosCostos.clear();
            crearCentrosCostos.clear();
            modificarCentrosCostos.clear();
            index = -1;
            k = 0;
            listCentrosCostosPorEmpresa = null;
            guardado = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            banderaModificacionEmpresa = 0;
            if (banderaModificacionEmpresa == 0) {
                cambiarEmpresa();
            }
            context.update("form:datosCentrosCostos");
            context.update("form:ACEPTAR");
        } catch (Exception E) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.ModificarModificacion ERROR====================" + E.getMessage());
        }
    }
    
    public void mostrarInfo(int indice, int celda) {
        if (permitirIndex == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            
            banderaModificacionEmpresa = 1;
            index = indice;
            cualCelda = celda;
            secRegistro = listCentrosCostosPorEmpresa.get(index).getSecuencia();
            if (cualCelda == 3) {
                if (listCentrosCostosPorEmpresa.get(indice).getVariableManoObra().equals("DIRECTA")) {
                    listCentrosCostosPorEmpresa.get(indice).setManoobra("D");
                } else if (listCentrosCostosPorEmpresa.get(indice).getVariableManoObra().equals("INDIRECTA")) {
                    listCentrosCostosPorEmpresa.get(indice).setManoobra("I");
                } else if (listCentrosCostosPorEmpresa.get(indice).getVariableManoObra().equals(" ")) {
                    listCentrosCostosPorEmpresa.get(indice).setManoobra(null);
                }
            } else if (cualCelda == 5) {
                System.out.println("OBSOLETO " + listCentrosCostosPorEmpresa.get(indice).getObsoleto());
                if (listCentrosCostosPorEmpresa.get(indice).getVariableObsoleto().equals("SI")) {
                    listCentrosCostosPorEmpresa.get(indice).setObsoleto("S");
                } else if (listCentrosCostosPorEmpresa.get(indice).getVariableObsoleto().equals("NO")) {
                    listCentrosCostosPorEmpresa.get(indice).setObsoleto("N");
                } else if (listCentrosCostosPorEmpresa.get(indice).getVariableObsoleto().equals(" ")) {
                    listCentrosCostosPorEmpresa.get(indice).setObsoleto(null);
                }
            } else if (cualCelda == 7) {
                System.out.println("DIMENSIONES  " + listCentrosCostosPorEmpresa.get(indice).getDimensiones());
            }
            if (!crearCentrosCostos.contains(listCentrosCostosPorEmpresa.get(indice))) {
                
                if (modificarCentrosCostos.isEmpty()) {
                    modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(indice));
                } else if (!modificarCentrosCostos.contains(listCentrosCostosPorEmpresa.get(indice))) {
                    modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            context.update("form:datosCentrosCostos");
            
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
        
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
                context.update("form:tiposCentrosCostosDialogo");
                context.execute("tiposCentrosCostosDialogo.show()");
                dig = -1;
            }
            
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.asignarIndex ERROR======" + e.getMessage());
        }
    }
    
    public void activarAceptar() {
        aceptar = false;
    }
    
    public void actualizarCentroCosto() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarCentroCosto \n");
        try {
            banderaModificacionEmpresa = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.actualizarCentroCosto TIPOACTUALIZACION====" + tipoActualizacion);
            if (tipoActualizacion == 0) {
                listCentrosCostosPorEmpresa.get(index).setTipocentrocosto(tipoCentroCostoSeleccionado);
                if (!crearCentrosCostos.contains(listCentrosCostosPorEmpresa.get(index))) {
                    if (modificarCentrosCostos.isEmpty()) {
                        modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(index));
                    } else if (!modificarCentrosCostos.contains(listCentrosCostosPorEmpresa.get(index))) {
                        modificarCentrosCostos.add(listCentrosCostosPorEmpresa.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                    
                }
                context.update("form:datosCentrosCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            } else if (tipoActualizacion == 1) {
                // context.reset("formularioDialogos:nuevaTipoCentroCostos");
                System.out.println("Entro actualizar centro costo nuevo rgistro");
                nuevoCentroCosto.setTipocentrocosto(tipoCentroCostoSeleccionado);
                System.out.println("Centro Costo Seleccionado: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
                context.update("formularioDialogos:nuevaTipoCentroCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            } else if (tipoActualizacion == 2) {
                duplicarCentroCosto.setTipocentrocosto(tipoCentroCostoSeleccionado);
                context.update("formularioDialogos:duplicarTipoCentroCostos");
                context.execute("tiposCentrosCostosDialogo.hide()");
            }
            filtradoTiposCentrosCostos = null;
            tipoCentroCostoSeleccionado = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            permitirIndex = true;
        } catch (Exception e) {
            System.out.println("ERROR BETA .actualizarCentroCosto ERROR============" + e.getMessage());
        }
    }
    
    public void cancelarCambioTiposCentroCosto() {
        try {
            filtradoTiposCentrosCostos = null;
            tipoCentroCostoSeleccionado = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarCambioCentroCosto ERROR=====" + e.getMessage());
        }
    }
    
    public void llamadoDialogoBuscarCentrosCostos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                banderaSeleccionCentrosCostosPorEmpresa = true;
                context.execute("confirmarGuardar.show()");
                
            } else {
                listCentrosCostosPorEmpresaBoton = null;
                getListCentrosCostosPorEmpresaBoton();
                index = -1;
                context.update("formularioDialogos:lovCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.show()");
                
            }
        } catch (Exception e) {
            System.err.println("ERROR LLAMADO DIALOGO BUSCAR CENTROS COSTOS " + e);
        }
        
    }
    
    public void seleccionCentrosCostosPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            
            if (guardado == true) {
                listCentrosCostosPorEmpresa.clear();
                System.err.println("seleccionCentrosCostosPorEmpresa " + CentrosCostosPorEmpresaSeleccionado.getNombre());
                listCentrosCostosPorEmpresa.add(CentrosCostosPorEmpresaSeleccionado);
                System.err.println("listCentrosCostosPorEmpresa tamaño " + listCentrosCostosPorEmpresa.size());
                System.err.println("listCentrosCostosPorEmpresa nombre " + listCentrosCostosPorEmpresa.get(0).getNombre());
                CentrosCostosPorEmpresaSeleccionado = null;
                filterCentrosCostosPorEmpresa = null;
                aceptar = true;
                banderaModificacionEmpresa = 1;
                context.update("form:datosCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.hide()");
                context.reset("formularioDialogos:lovCentrosCostos:globalFilter");
            } /*else {
             System.err.println("listCentrosCostosPorEmpresa tamaño " + listCentrosCostosPorEmpresa.size());
             System.err.println("listCentrosCostosPorEmpresa nombre " + listCentrosCostosPorEmpresa.get(0).getNombre());
             banderaSeleccionCentrosCostosPorEmpresa = true;
             context.execute("confirmarGuardar.show()");
             CentrosCostosPorEmpresaSeleccionado = null;
             listCentrosCostosPorEmpresa.clear();
             System.err.println("seleccionCentrosCostosPorEmpresa " + CentrosCostosPorEmpresaSeleccionado.getNombre());
             listCentrosCostosPorEmpresa.add(CentrosCostosPorEmpresaSeleccionado);
             filterCentrosCostosPorEmpresa = null;
             aceptar = true;
             banderaModificacionEmpresa = 0;
             context.execute("buscarCentrosCostosDialogo.hide()");
             context.reset("formularioDialogos:lovCentrosCostos:globalFilter");
             }*/
            
            
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.seleccionaVigencia ERROR====" + e.getMessage());
        }
    }
    
    public void cancelarSeleccionCentroCostoPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            CentrosCostosPorEmpresaSeleccionado = null;
            filterCentrosCostosPorEmpresa = null;
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
                nuevoTipoCCAutoCompletar = nuevoCentroCosto.getTipocentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoTipoCCAutoCompletar = duplicarCentroCosto.getTipocentrocosto().getNombre();
            }
            
        }
    }
    
    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOSCENTROSCOSTOS")) {
            System.out.println(" nuevoTipoCentroCosto    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
            
            if (!nuevoCentroCosto.getTipocentrocosto().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                nuevoCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                getListaTiposCentrosCostos();
                for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
                    if (listaTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoCentroCosto.setTipocentrocosto(listaTiposCentrosCostos.get(indiceUnicoElemento));
                    listaTiposCentrosCostos = null;
                    getListaTiposCentrosCostos();
                } else {
                    context.update("form:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
                nuevoCentroCosto.getTipocentrocosto().setNombre(" ");
                System.out.println("NUEVO Valor nombre tcc: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
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
            System.out.println("NOMBRE CENTRO COSTO: " + duplicarCentroCosto.getTipocentrocosto().getNombre());
            
            if (!duplicarCentroCosto.getTipocentrocosto().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoTipoCCAutoCompletar: " + nuevoTipoCCAutoCompletar);
                duplicarCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                for (int i = 0; i < listaTiposCentrosCostos.size(); i++) {
                    if (listaTiposCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarCentroCosto.setTipocentrocosto(listaTiposCentrosCostos.get(indiceUnicoElemento));
                    listaTiposCentrosCostos = null;
                    getListaTiposCentrosCostos();
                } else {
                    context.update("form:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                duplicarCentroCosto.getTipocentrocosto().setNombre(nuevoTipoCCAutoCompletar);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                duplicarCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
                duplicarCentroCosto.getTipocentrocosto().setNombre(" ");
                System.out.println("Valor nombre tcc: " + duplicarCentroCosto.getTipocentrocosto().getNombre());
            }
            context.update("formularioDialogos:duplicarTipoCentroCostos");
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
        context.update("form:tiposCentrosCostosDialogo");
        context.execute("tiposCentrosCostosDialogo.show()");
    }
    
    public void limpiarNuevoCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarNuevoCentroCostos \n");
        try {
            nuevoCentroCosto = new CentrosCostos();
            nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETACENTROSCOSTOS.LimpiarNuevoCentroCostos ERROR=============================" + e.getMessage());
        }
    }
    
    public void agregarNuevoCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.agregarNuevoCentroCostos \n");
        try {
            int contador = 0;
            mensajeValidacion = " ";
            int duplicados = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            
            banderaModificacionEmpresa = 1;
            if (nuevoCentroCosto.getCodigo() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
                
            } else if (nuevoCentroCosto.getCodigo().isEmpty()) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
                
            } else if (nuevoCentroCosto.getCodigo().equals(" ")) {
                mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
                
            } else {
                System.out.println("codigo en Motivo Cambio Cargo: " + nuevoCentroCosto.getCodigo());
                
                for (int x = 0; x < listCentrosCostosPorEmpresa.size(); x++) {
                    if (listCentrosCostosPorEmpresa.get(x).getCodigo().equals(nuevoCentroCosto.getCodigo())) {
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
            if (nuevoCentroCosto.getNombre() == null) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
                
            } else if (nuevoCentroCosto.getNombre().isEmpty()) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
                
            } else if (nuevoCentroCosto.getNombre().equals(" ")) {
                mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
                
            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (nuevoCentroCosto.getTipocentrocosto().getSecuencia() == null) {
                mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
                
            } else {
                System.out.println("Bandera : ");
                contador++;
            }
            if (nuevoCentroCosto.getObsoleto() == null) {
                nuevoCentroCosto.setObsoleto("N");
            }
            if (contador == 3) {
                k++;
                l = BigInteger.valueOf(k);
                nuevoCentroCosto.setSecuencia(l);
                System.err.println("AGREGAR obsoleto " + nuevoCentroCosto.getObsoleto());
                System.err.println("AGREGAR mano de obra" + nuevoCentroCosto.getManoobra());
                System.err.println("AGREGAR Secuencia " + nuevoCentroCosto.getSecuencia());
                System.err.println("AGREGAR Tipo Centro Costo " + nuevoCentroCosto.getTipocentrocosto().getNombre());
                nuevoCentroCosto.setComodin("N");
                nuevoCentroCosto.setEmpresa(empresaSeleccionada);
                if (crearCentrosCostos.contains(nuevoCentroCosto)) {
                    System.out.println("Ya lo contengo.");
                } else {
                    crearCentrosCostos.add(nuevoCentroCosto);
                    
                }
                listCentrosCostosPorEmpresa.add(nuevoCentroCosto);
                context.update("form:datosCentrosCostos");
                nuevoCentroCosto = new CentrosCostos();
                // index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                if (bandera == 1) {
                     FacesContext c = FacesContext.getCurrentInstance();
                    codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                    codigoCC.setFilterStyle("display: none; visibility: hidden;");
                    nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                    nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                    tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                    manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                    codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                    codigoAT.setFilterStyle("display: none; visibility: hidden;");
                    obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                    obsoleto.setFilterStyle("display: none; visibility: hidden;");
                    codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                    codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                    dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                    dimensiones.setFilterStyle("display: none; visibility: hidden;");
                    tamano = 270;
                    RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                    
                    bandera = 0;
                    filtrarCentrosCostos = null;
                    tipoLista = 0;
                }
                mensajeValidacion = " ";
                RequestContext.getCurrentInstance().execute("NuevoRegistroCentroCostos.hide()");
                
            } else {
                contador = 0;
                context.update("form:validacionDuplicarVigencia");
                context.execute("validacionDuplicarVigencia.show()");
            }
            
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.agregarNuevoCentroCostos ERROR===========================" + e.getMessage());
        }
    }
    
    public void cargarTiposCentrosCostosNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposCentrosCostosDialogo");
            context.execute("tiposCentrosCostosDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tiposCentrosCostosDialogo");
            context.execute("tiposCentrosCostosDialogo.show()");
        }
    }
    
    public void mostrarDialogoNuevoTiposCentrosCostos() {
        RequestContext context = RequestContext.getCurrentInstance();
        nuevoCentroCosto = new CentrosCostos();
        nuevoCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
        index = -1;
        context.update("formularioDialogos:nuevoCentroCostos");
        context.execute("NuevoRegistroCentroCostos.show()");
    }
    
    public void mostrarDialogoListaEmpresas() {
        RequestContext context = RequestContext.getCurrentInstance();
        index = -1;
        context.execute("buscarCentrosCostosDialogo.show()");
    }
    
    public void duplicandoCentroCostos() {
        try {
            banderaModificacionEmpresa = 1;
            System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.duplicarCentroCostos INDEX===" + index);
            
            if (index >= 0) {
                System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.duplicarCentroCostos TIPOLISTA===" + tipoLista);
                
                duplicarCentroCosto = new CentrosCostos();
                k++;
                l = BigInteger.valueOf(k);
                if (tipoLista == 0) {
                    duplicarCentroCosto.setSecuencia(l);
                    duplicarCentroCosto.setEmpresa(listCentrosCostosPorEmpresa.get(index).getEmpresa());
                    duplicarCentroCosto.setCodigo(listCentrosCostosPorEmpresa.get(index).getCodigo());
                    duplicarCentroCosto.setNombre(listCentrosCostosPorEmpresa.get(index).getNombre());
                    duplicarCentroCosto.setTipocentrocosto(listCentrosCostosPorEmpresa.get(index).getTipocentrocosto());
                    duplicarCentroCosto.setManoobra(listCentrosCostosPorEmpresa.get(index).getManoobra());
                    duplicarCentroCosto.setCodigoalternativo(listCentrosCostosPorEmpresa.get(index).getCodigoalternativo());
                    duplicarCentroCosto.setObsoleto(listCentrosCostosPorEmpresa.get(index).getObsoleto());
                    duplicarCentroCosto.setCodigoctt(listCentrosCostosPorEmpresa.get(index).getCodigoctt());
                    duplicarCentroCosto.setDimensiones(listCentrosCostosPorEmpresa.get(index).getDimensiones());
                    duplicarCentroCosto.setComodin(listCentrosCostosPorEmpresa.get(index).getComodin());
                    
                }
                if (tipoLista == 1) {
                    
                    duplicarCentroCosto.setSecuencia(l);
                    duplicarCentroCosto.setEmpresa(filtrarCentrosCostos.get(index).getEmpresa());
                    duplicarCentroCosto.setCodigo(filtrarCentrosCostos.get(index).getCodigo());
                    duplicarCentroCosto.setNombre(filtrarCentrosCostos.get(index).getNombre());
                    duplicarCentroCosto.setTipocentrocosto(filtrarCentrosCostos.get(index).getTipocentrocosto());
                    duplicarCentroCosto.setManoobra(filtrarCentrosCostos.get(index).getManoobra());
                    duplicarCentroCosto.setCodigoalternativo(filtrarCentrosCostos.get(index).getCodigoalternativo());
                    duplicarCentroCosto.setObsoleto(filtrarCentrosCostos.get(index).getObsoleto());
                    duplicarCentroCosto.setCodigoctt(filtrarCentrosCostos.get(index).getCodigoctt());
                    duplicarCentroCosto.setDimensiones(filtrarCentrosCostos.get(index).getDimensiones());
                    
                }
                
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicarCentroCostos");
                context.execute("DuplicarRegistroCentroCostos.show()");
                index = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.DuplicarCentroCostos ERROR===============" + e.getMessage());
        }
    }
    
    public void limpiarDuplicarCentroCostos() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.limpiarDuplicarCentroCostos \n");
        try {
            duplicarCentroCosto = new CentrosCostos();
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.limpiarDuplicarCentroCostos ERROR========" + e.getMessage());
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
        
        if (duplicarCentroCosto.getCodigo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
            
        } else if (duplicarCentroCosto.getCodigo().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un codigo \n";
            
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + duplicarCentroCosto.getCodigo());
            
            for (int x = 0; x < listCentrosCostosPorEmpresa.size(); x++) {
                if (listCentrosCostosPorEmpresa.get(x).getCodigo().equals(duplicarCentroCosto.getCodigo())) {
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
        if (duplicarCentroCosto.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
            
        } else if (duplicarCentroCosto.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un nombre \n";
            
        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarCentroCosto.getTipocentrocosto().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Un tipo de centro costo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
            
        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarCentroCosto.getManoobra() == null) {
            System.out.println("Al duplicar la mano de obra ya es nula");
        } else if (duplicarCentroCosto.getManoobra().isEmpty()) {
            duplicarCentroCosto.setManoobra(null);
        }
        
        if (contador == 3) {
            System.err.println("DUPLICAR MANO DE OBRA " + duplicarCentroCosto.getManoobra());
            
            System.err.println("DUPLICAR OBSOLETO " + duplicarCentroCosto.getObsoleto());
            System.err.println("DUPLICAR DIMENSIONES " + duplicarCentroCosto.getDimensiones());
            if (crearCentrosCostos.contains(duplicarCentroCosto)) {
                System.out.println("Ya lo contengo.");
            } else {
                listCentrosCostosPorEmpresa.add(duplicarCentroCosto);
            }
            crearCentrosCostos.add(duplicarCentroCosto);
            context.update("form:datosCentrosCostos");
            
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                 FacesContext c = FacesContext.getCurrentInstance();
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                //6
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                tamano = 270;
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            duplicarCentroCosto = new CentrosCostos();
            duplicarCentroCosto.setTipocentrocosto(new TiposCentrosCostos());
            RequestContext.getCurrentInstance().execute("DuplicarRegistroCentroCostos.hide()");
            mensajeValidacion = " ";
            banderaModificacionEmpresa = 1;
            
        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }
    
    private BigInteger contadorComprobantesContables;
    private BigInteger contadorDetallesCCConsolidador;
    private BigInteger contadorEmpresas;
    private BigInteger contadorEstructuras;
    private BigInteger contadorDetallesCCDetalle;
    private BigInteger contadorInterconCondor;
    private BigInteger contadorInterconDynamics;
    private BigInteger contadorInterconGeneral;
    private BigInteger contadorInterconHelisa;
    private BigInteger contadorInterconSapbo;
    private BigInteger contadorInterconSiigo;
    private BigInteger contadorInterconTotal;
    private BigInteger contadorNovedadesD;
    private BigInteger contadorNovedadesC;
    private BigInteger contadorProcesosProductivos;
    private BigInteger contadorProyecciones;
    private BigInteger contadorSolucionesNodosC;
    private BigInteger contadorSolucionesNodosD;
    private BigInteger contadorSoPanoramas;
    private BigInteger contadorTerceros;
    private BigInteger contadorUnidadesRegistradas;
    private BigInteger contadorVigenciasCuentasC;
    private BigInteger contadorVigenciasCuentasD;
    private BigInteger contadorVigenciasProrrateos;
    
    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        System.out.println("TIPOLISTA = " + tipoLista);
        BigInteger pruebilla;
        try {
            if (tipoLista == 0) {
                //  System.err.println("Control Secuencia de ControlTiposEmpresas secuencia centro costo" + listCentrosCostosPorEmpresa.get(index).getSecuencia());
                contadorComprobantesContables = administrarCentroCostos.contadorComprobantesContables(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorComprobantesContables: " + contadorComprobantesContables);
                contadorDetallesCCConsolidador = administrarCentroCostos.contadorDetallesCCConsolidador(listCentrosCostosPorEmpresa.get(index).getSecuencia());;
                // System.out.println("SE TOTEA ControlBetaCC contadorDetallesCCConsolidador: " + contadorDetallesCCConsolidador);
                contadorEmpresas = administrarCentroCostos.contadorEmpresas(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                // System.out.println("ControlBetaCC contadorEmpresas: " + contadorEmpresas);
                contadorEstructuras = administrarCentroCostos.contadorEstructuras(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //  System.out.println("ControlBetaCC contadorEstructuras " + contadorEstructuras);
                contadorDetallesCCDetalle = administrarCentroCostos.contadorDetalleContable(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorDetallesCCDetalle" + contadorDetallesCCDetalle);
                contadorInterconCondor = administrarCentroCostos.contadorInterConCondor(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconCondor" + contadorInterconCondor);
                contadorInterconDynamics = administrarCentroCostos.contadorInterConDynamics(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                // System.out.println("ControlBetaCC:contadorInterconDynamics " + contadorInterconDynamics);
                contadorInterconGeneral = administrarCentroCostos.contadorInterConGeneral(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconGeneral" + contadorInterconGeneral);
                contadorInterconHelisa = administrarCentroCostos.contadorInterConHelisa(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //            System.out.println("ControlBetaCC: contadorInterconHelisa" + contadorInterconHelisa);
                contadorInterconSapbo = administrarCentroCostos.contadorInterConSapbo(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //            System.out.println("ControlBetaCC: contadorInterconSapbo" + contadorInterconSapbo);
                contadorInterconSiigo = administrarCentroCostos.contadorInterConSiigo(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //          System.out.println("ControlBetaCC:contadorInterconSiigo" + contadorInterconSiigo);
                contadorInterconTotal = administrarCentroCostos.contadorInterConTotal(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //          System.out.println("ControlBetaCC: contadorInterconTotal" + contadorInterconTotal);
                contadorNovedadesD = administrarCentroCostos.contadorNovedadesD(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                ///          System.out.println("ControlBetaCC: contadorNovedadesD " + contadorNovedadesD);
                contadorNovedadesC = administrarCentroCostos.contadorNovedadesC(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //         System.out.println("ControlBetaCC: contadorNovedadesC " + contadorNovedadesC);
                contadorProcesosProductivos = administrarCentroCostos.contadorProcesosProductivos(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //          System.out.println("ControlBetaCC: contadorProcesosProductivos" + contadorProcesosProductivos);
                contadorProyecciones = administrarCentroCostos.contadorProyecciones(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //        System.out.println("ControlBetaCC: contadorProyecciones" + contadorProyecciones);
                contadorSolucionesNodosC = administrarCentroCostos.contadorSolucionesNodosC(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //        System.out.println("ControlBetaCC: contadorSolucionesNodosC" + contadorSolucionesNodosC);
                contadorSolucionesNodosD = administrarCentroCostos.contadorSolucionesNodosD(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC:contadorSolucionesNodosD " + contadorSolucionesNodosD);
                contadorSoPanoramas = administrarCentroCostos.contadorSoPanoramas(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //      System.out.println("ControlBetaCC: contadorSoPanoramas" + contadorSoPanoramas);
                contadorTerceros = administrarCentroCostos.contadorTerceros(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorTerceros" + contadorTerceros);
                contadorUnidadesRegistradas = administrarCentroCostos.contadorUnidadesRegistradas(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorUnidadesRegistradas" + contadorUnidadesRegistradas);
                contadorVigenciasCuentasC = administrarCentroCostos.contadorVigenciasCuentasC(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //      System.out.println("ControlBetaCC: contadorVigenciasCuentasC" + contadorVigenciasCuentasC);
                contadorVigenciasCuentasD = administrarCentroCostos.contadorVigenciasCuentasD(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorVigenciasCuentasD" + contadorVigenciasCuentasD);
                contadorVigenciasProrrateos = administrarCentroCostos.contadorVigenciasProrrateos(listCentrosCostosPorEmpresa.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC: contadorVigenciasProrrateos" + contadorVigenciasProrrateos);
            } else {
                //               System.err.println(" FILTRAR  FILTRAR  FILTRAR  FILTRAR Control Secuencia de ControlTiposEmpresas secuencia centro costo" + listCentrosCostosPorEmpresa.get(index).getSecuencia());
                contadorComprobantesContables = administrarCentroCostos.contadorComprobantesContables(filtrarCentrosCostos.get(index).getSecuencia());
                //             System.out.println("ControlBetaCC contadorComprobantesContables: " + contadorComprobantesContables);
                contadorDetallesCCConsolidador = administrarCentroCostos.contadorDetallesCCConsolidador(filtrarCentrosCostos.get(index).getSecuencia());;
                //           System.out.println("SE TOTEA ControlBetaCC contadorDetallesCCConsolidador: " + contadorDetallesCCConsolidador);
                contadorEmpresas = administrarCentroCostos.contadorEmpresas(filtrarCentrosCostos.get(index).getSecuencia());
                //         System.out.println("ControlBetaCC contadorEmpresas: " + contadorEmpresas);
                contadorEstructuras = administrarCentroCostos.contadorEstructuras(filtrarCentrosCostos.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC contadorEstructuras " + contadorEstructuras);
                contadorDetallesCCDetalle = administrarCentroCostos.contadorDetalleContable(filtrarCentrosCostos.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC: contadorDetallesCCDetalle" + contadorDetallesCCDetalle);
                contadorInterconCondor = administrarCentroCostos.contadorInterConCondor(filtrarCentrosCostos.get(index).getSecuencia());
                //   System.out.println("ControlBetaCC: contadorInterconCondor" + contadorInterconCondor);
                contadorInterconDynamics = administrarCentroCostos.contadorInterConDynamics(filtrarCentrosCostos.get(index).getSecuencia());
                // System.out.println("ControlBetaCC:contadorInterconDynamics " + contadorInterconDynamics);
                contadorInterconGeneral = administrarCentroCostos.contadorInterConGeneral(filtrarCentrosCostos.get(index).getSecuencia());
                //System.out.println("ControlBetaCC: contadorInterconGeneral" + contadorInterconGeneral);
                contadorInterconHelisa = administrarCentroCostos.contadorInterConHelisa(filtrarCentrosCostos.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconHelisa" + contadorInterconHelisa);
                contadorInterconSapbo = administrarCentroCostos.contadorInterConSapbo(filtrarCentrosCostos.get(index).getSecuencia());
                // System.out.println("ControlBetaCC: contadorInterconSapbo" + contadorInterconSapbo);
                contadorInterconSiigo = administrarCentroCostos.contadorInterConSiigo(filtrarCentrosCostos.get(index).getSecuencia());
                //        System.out.println("ControlBetaCC:contadorInterconSiigo" + contadorInterconSiigo);
                contadorInterconTotal = administrarCentroCostos.contadorInterConTotal(filtrarCentrosCostos.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorInterconTotal" + contadorInterconTotal);
                contadorNovedadesD = administrarCentroCostos.contadorNovedadesD(filtrarCentrosCostos.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorNovedadesD " + contadorNovedadesD);
                contadorNovedadesC = administrarCentroCostos.contadorNovedadesC(filtrarCentrosCostos.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC: contadorNovedadesC " + contadorNovedadesC);
                contadorProcesosProductivos = administrarCentroCostos.contadorProcesosProductivos(filtrarCentrosCostos.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC: contadorProcesosProductivos" + contadorProcesosProductivos);
                contadorProyecciones = administrarCentroCostos.contadorProyecciones(filtrarCentrosCostos.get(index).getSecuencia());
                //      System.out.println("ControlBetaCC: contadorProyecciones" + contadorProyecciones);
                contadorSolucionesNodosC = administrarCentroCostos.contadorSolucionesNodosC(filtrarCentrosCostos.get(index).getSecuencia());
                //      System.out.println("ControlBetaCC: contadorSolucionesNodosC" + contadorSolucionesNodosC);
                contadorSolucionesNodosD = administrarCentroCostos.contadorSolucionesNodosD(filtrarCentrosCostos.get(index).getSecuencia());
                //    System.out.println("ControlBetaCC:contadorSolucionesNodosD " + contadorSolucionesNodosD);
                contadorSoPanoramas = administrarCentroCostos.contadorSoPanoramas(filtrarCentrosCostos.get(index).getSecuencia());
                //     System.out.println("ControlBetaCC: contadorSoPanoramas" + contadorSoPanoramas);
                contadorTerceros = administrarCentroCostos.contadorTerceros(filtrarCentrosCostos.get(index).getSecuencia());
                //      System.out.println("ControlBetaCC: contadorTerceros" + contadorTerceros);
                contadorUnidadesRegistradas = administrarCentroCostos.contadorUnidadesRegistradas(filtrarCentrosCostos.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorUnidadesRegistradas" + contadorUnidadesRegistradas);
                contadorVigenciasCuentasC = administrarCentroCostos.contadorVigenciasCuentasC(filtrarCentrosCostos.get(index).getSecuencia());
                //        System.out.println("ControlBetaCC: contadorVigenciasCuentasC" + contadorVigenciasCuentasC);
                contadorVigenciasCuentasD = administrarCentroCostos.contadorVigenciasCuentasD(filtrarCentrosCostos.get(index).getSecuencia());
                //        System.out.println("ControlBetaCC: contadorVigenciasCuentasD" + contadorVigenciasCuentasD);
                contadorVigenciasProrrateos = administrarCentroCostos.contadorVigenciasProrrateos(filtrarCentrosCostos.get(index).getSecuencia());
                //       System.out.println("ControlBetaCC: contadorVigenciasProrrateos" + contadorVigenciasProrrateos);
                //pruebilla = administrarCentroCostos.sumaTotal(filtrarCentrosCostos.get(index).getSecuencia());
                //System.err.println("pruebilla :::::::::::::::::::::::::::::::: " + pruebilla);
            }
            if (contadorDetallesCCConsolidador.equals(new BigInteger("0"))
                    && contadorEmpresas.equals(new BigInteger("0"))
                    && contadorEstructuras.equals(new BigInteger("0"))
                    && contadorDetallesCCDetalle.equals(new BigInteger("0"))
                    && contadorInterconCondor.equals(new BigInteger("0"))
                    && contadorInterconDynamics.equals(new BigInteger("0"))
                    && contadorInterconGeneral.equals(new BigInteger("0"))
                    && contadorInterconHelisa.equals(new BigInteger("0"))
                    && contadorInterconSapbo.equals(new BigInteger("0"))
                    && contadorInterconSiigo.equals(new BigInteger("0"))
                    && contadorInterconTotal.equals(new BigInteger("0"))
                    && contadorNovedadesD.equals(new BigInteger("0"))
                    && contadorNovedadesC.equals(new BigInteger("0"))
                    && contadorProcesosProductivos.equals(new BigInteger("0"))
                    && contadorProyecciones.equals(new BigInteger("0"))
                    && contadorSolucionesNodosC.equals(new BigInteger("0"))
                    && contadorSolucionesNodosD.equals(new BigInteger("0"))
                    && contadorSoPanoramas.equals(new BigInteger("0"))
                    && contadorTerceros.equals(new BigInteger("0"))
                    && contadorUnidadesRegistradas.equals(new BigInteger("0"))
                    && contadorVigenciasCuentasC.equals(new BigInteger("0"))
                    && contadorVigenciasCuentasD.equals(new BigInteger("0"))
                    && contadorVigenciasProrrateos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoCentroCosto();
            } else {
                
                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contadorComprobantesContables = new BigInteger("-1");
                contadorDetallesCCConsolidador = new BigInteger("-1");
                contadorEmpresas = new BigInteger("-1");
                contadorEstructuras = new BigInteger("-1");
                contadorDetallesCCDetalle = new BigInteger("-1");
                contadorInterconCondor = new BigInteger("-1");
                contadorInterconDynamics = new BigInteger("-1");
                contadorInterconGeneral = new BigInteger("-1");
                contadorInterconHelisa = new BigInteger("-1");
                contadorInterconSapbo = new BigInteger("-1");
                contadorInterconSiigo = new BigInteger("-1");
                contadorInterconTotal = new BigInteger("-1");
                contadorNovedadesD = new BigInteger("-1");
                contadorNovedadesC = new BigInteger("-1");
                contadorProcesosProductivos = new BigInteger("-1");
                contadorProyecciones = new BigInteger("-1");
                contadorSolucionesNodosC = new BigInteger("-1");
                contadorSolucionesNodosD = new BigInteger("-1");
                contadorSoPanoramas = new BigInteger("-1");
                contadorTerceros = new BigInteger("-1");
                contadorUnidadesRegistradas = new BigInteger("-1");
                contadorVigenciasCuentasC = new BigInteger("-1");
                contadorVigenciasCuentasD = new BigInteger("-1");
                contadorVigenciasProrrateos = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROL BETA CENTROS COSTOS verificarBorrado ERROR " + e);
        }
    }
    
    public void borrandoCentroCosto() {
        try {
            banderaModificacionEmpresa = 1;
            if (index >= 0) {
                if (tipoLista == 0) {
                    if (!modificarCentrosCostos.isEmpty() && modificarCentrosCostos.contains(listCentrosCostosPorEmpresa.get(index))) {
                        int modIndex = modificarCentrosCostos.indexOf(listCentrosCostosPorEmpresa.get(index));
                        modificarCentrosCostos.remove(modIndex);
                        borrarCentrosCostos.add(listCentrosCostosPorEmpresa.get(index));
                    } else if (!crearCentrosCostos.isEmpty() && crearCentrosCostos.contains(listCentrosCostosPorEmpresa.get(index))) {
                        int crearIndex = crearCentrosCostos.indexOf(listCentrosCostosPorEmpresa.get(index));
                        crearCentrosCostos.remove(crearIndex);
                    } else {
                        
                        borrarCentrosCostos.add(listCentrosCostosPorEmpresa.get(index));
                    }
                    listCentrosCostosPorEmpresa.remove(index);
                }
                if (tipoLista == 1) {
                    if (!modificarCentrosCostos.isEmpty() && modificarCentrosCostos.contains(filtrarCentrosCostos.get(index))) {
                        System.out.println("\n 6 ENTRE A CONTROLBETACENTROSCOSTOS.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());
                        
                        int modIndex = modificarCentrosCostos.indexOf(filtrarCentrosCostos.get(index));
                        modificarCentrosCostos.remove(modIndex);
                        borrarCentrosCostos.add(filtrarCentrosCostos.get(index));
                    } else if (!crearCentrosCostos.isEmpty() && crearCentrosCostos.contains(filtrarCentrosCostos.get(index))) {
                        System.out.println("\n 7 ENTRE A CONTROLBETACENTROSCOSTOS.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());
                        int crearIndex = crearCentrosCostos.indexOf(filtrarCentrosCostos.get(index));
                        crearCentrosCostos.remove(crearIndex);
                    } else {
                        System.out.println("\n 8 ENTRE A CONTROLBETACENTROSCOSTOS.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());
                        borrarCentrosCostos.add(filtrarCentrosCostos.get(index));
                    }
                    int VCIndex = listCentrosCostosPorEmpresa.indexOf(filtrarCentrosCostos.get(index));
                    listCentrosCostosPorEmpresa.remove(VCIndex);
                    filtrarCentrosCostos.remove(index);
                }
                
                RequestContext context = RequestContext.getCurrentInstance();
                index = -1;
                System.err.println("verificar Borrado " + guardado);
                if (guardado == true) {
                    guardado = false;
                }
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                context.update("form:datosCentrosCostos");
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.BorrarCentroCosto ERROR=====================" + e.getMessage());
        }
    }
    
    public void guardarCambiosCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();
        
        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarCentrosCostos.isEmpty()) {
                administrarCentroCostos.borrarCentroCostos(borrarCentrosCostos);
                //mostrarBorrados
                registrosBorrados = borrarCentrosCostos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarCentrosCostos.clear();
            }
            if (!crearCentrosCostos.isEmpty()) {
                administrarCentroCostos.crearCentroCostos(crearCentrosCostos);
                crearCentrosCostos.clear();
            }
            if (!modificarCentrosCostos.isEmpty()) {
                administrarCentroCostos.modificarCentroCostos(modificarCentrosCostos);
                modificarCentrosCostos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listCentrosCostosPorEmpresa = null;
            context.update("form:datosTipoCentroCosto");
            k = 0;
            guardado = true;
            
            if (banderaModificacionEmpresa == 0) {
                cambiarEmpresa();
                banderaModificacionEmpresa = 1;
                
            }
            if (banderaSeleccionCentrosCostosPorEmpresa == true) {
                listCentrosCostosPorEmpresaBoton = null;
                getListCentrosCostosPorEmpresaBoton();
                index = -1;
                context.update("formularioDialogos:lovCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.show()");
                banderaSeleccionCentrosCostosPorEmpresa = false;
            }
        }
        index = -1;
        FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        banderaModificacionEmpresa = 0;
    }
    
    public void cancelarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaModificacionEmpresa == 0) {
            empresaSeleccionada = backUpEmpresaActual;
            context.update("formularioDialogos:lovEmpresas");
            banderaModificacionEmpresa = 1;
        }
        
    }
    
    public void activarCtrlF11() {
        System.out.println("\n ENTRE A CONTROLBETACENTROSCOSTOS.activarCtrlF11 \n");
        
        try {
            
                 FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 0) {
                tamano = 246;
                System.out.println("Activar");
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("width: 40px");
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("width: 105px");
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("width: 100px");
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("width: 90px");
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("width: 60px");
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("width: 35px");
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("width: 90px");
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("width: 15px");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 1;
            } else if (bandera == 1) {
                System.out.println("Desactivar");
                //0
                codigoCC = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                nombreCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                tipoCentroCosto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                manoDeObra = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                codigoAT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                obsoleto = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                codigoCTT = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                dimensiones = (Column) c.getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                tamano = 270;
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
        } catch (Exception e) {
            
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.activarCtrlF11 ERROR====================" + e.getMessage());
        }
    }

//--------------------------------------------------------------------------
    //METODOS MANIPULAR EMPRESA MOSTRADA
    //--------------------------------------------------------------------------
    public void cambiarEmpresaSeleccionada(int updown) {
        try {
            System.err.println("CONTROL CAMBIO EMPRESA BETA");
            if (banderaModificacionEmpresa == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarCambioEmpresa.show()");
            } else if (banderaModificacionEmpresa == 0) {
                getListaEmpresas();
                for (int i = 0; i < listaEmpresas.size(); i++) {
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresa: " + i + " nombre: " + listaEmpresas.get(i).getNombre());
                }
                System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Entra a cambiar la empresa seleccionada");
                int temp = indiceEmpresaMostrada;
                System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: temp = " + temp);
                if (updown == 1) {
                    temp--;
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Arriba_ temp = " + temp + " lista: " + listaEmpresas.size());
                    if (temp >= 0 && temp < listaEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getListaEmpresas().get(indiceEmpresaMostrada);
                        getListCentrosCostosPorEmpresaBoton();
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("formularioDialogos:buscarCentrosCostosDialogo");
                    }
                } else {
                    temp++;
                    System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Abajo_ temp = " + temp + " lista: " + listaEmpresas.size());
                    if (temp >= 0 && temp < listaEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getListaEmpresas().get(indiceEmpresaMostrada);
                        getListCentrosCostosPorEmpresaBoton();
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("formularioDialogos:buscarCentrosCostosDialogo");
                    }
                    
                }
            }
            
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cambiarEmpresaSeleccionada ERROR======" + e.getMessage());
        }
        
    }
    
    public void editarCelda() {
        try {
            System.out.println("\n ENTRE A editarCelda INDEX  " + index);
            if (index >= 0) {
                System.out.println("\n ENTRE AeditarCelda TIPOLISTA " + tipoLista);
                if (tipoLista == 0) {
                    editarCentroCosto = listCentrosCostosPorEmpresa.get(index);
                }
                if (tipoLista == 1) {
                    editarCentroCosto = filtrarCentrosCostos.get(index);
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
                    context.update("formularioDialogos:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
        } catch (Exception e) {
            System.out.println("\n ERROR CONTROLBETACENTROSCOSTOS.listaValoresBoton ERROR====================" + e.getMessage());
            
        }
    }
    
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CentroCostos", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    /**
     *
     * @throws IOException
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CentroCostos", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }
    
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listCentrosCostosPorEmpresa.isEmpty()) {
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
    
    public void lovEmpresas() {
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }
    
    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("Cambiar empresa  GUARDADO = " + guardado);
        System.err.println("Cambiar empresa  GUARDADO = " + empresaSeleccionada.getNombre());
        if (guardado == true) {
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            getListCentrosCostosPorEmpresa();
            getListCentrosCostosPorEmpresaBoton();
            filtradoListaEmpresas = null;
            listCentrosCostosPorEmpresa = null;
            aceptar = true;
            context.execute("EmpresasDialogo.hide()");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("formularioDialogos:lovEmpresas");
            backUpEmpresaActual = empresaSeleccionada;
            banderaModificacionEmpresa = 0;
            context.update("form:datosCentrosCostos");
            context.update("formularioDialogos:lovCentrosCostos");
            
        } else {
            banderaModificacionEmpresa = 0;
            context.execute("confirmarGuardar.show()");
        }
    }
    
    public void cancelarCambioEmpresa() {
        filtradoListaEmpresas = null;
        banderaModificacionEmpresa = 0;
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
    
    public List<Empresas> getListaEmpresas() {
        try {
            if (listaEmpresas == null) {
                listaEmpresas = administrarCentroCostos.buscarEmpresas();
                if (!listaEmpresas.isEmpty()) {
                    empresaSeleccionada = listaEmpresas.get(0);
                    backUpEmpresaActual = empresaSeleccionada;
                }
            }
            return listaEmpresas;
        } catch (Exception e) {
            System.out.println("ERRO LISTA EMPRESAS " + e);
            return null;
        }
    }
    
    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }
    
    public List<Empresas> getFiltradoListaEmpresas() {
        return filtradoListaEmpresas;
    }
    
    public void setFiltradoListaEmpresas(List<Empresas> filtradoListaEmpresas) {
        this.filtradoListaEmpresas = filtradoListaEmpresas;
    }
    
    public Empresas getEmpresaSeleccionada() {
        try {
            if (empresaSeleccionada == null) {
                getListaEmpresas();
                return empresaSeleccionada;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.getEmpresaSeleccionada ERROR " + e);
        } finally {
            return empresaSeleccionada;
        }
    }
    
    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }
    
    public List<CentrosCostos> getListCentrosCostosPorEmpresa() {
        try {
            if (empresaSeleccionada == null) {
                getEmpresaSeleccionada();
                if (listCentrosCostosPorEmpresa == null) {
                    listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                } else {
                    System.out.println(".-.");
                }
            } else if (listCentrosCostosPorEmpresa == null) {
                listCentrosCostosPorEmpresa = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
            }
            return listCentrosCostosPorEmpresa;
        } catch (Exception e) {
            System.out.println(" BETA  BETA ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }
    
    public List<CentrosCostos> getListCentrosCostosPorEmpresaBoton() {
        try {
            if (listCentrosCostosPorEmpresaBoton == null) {
                //listCentrosCostosPorEmpresaBoton = administrarCentroCostos.consultarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                listCentrosCostosPorEmpresaBoton = listCentrosCostosPorEmpresa;
            }
            return listCentrosCostosPorEmpresaBoton;
        } catch (Exception e) {
            System.out.println("ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }
    
    public void setListCentrosCostosPorEmpresaBoton(List<CentrosCostos> listCentrosCostosPorEmpresaBoton) {
        this.listCentrosCostosPorEmpresaBoton = listCentrosCostosPorEmpresaBoton;
    }
    
    public void setListCentrosCostosPorEmpresa(List<CentrosCostos> listCentrosCostosPorEmpresa) {
        this.listCentrosCostosPorEmpresa = listCentrosCostosPorEmpresa;
    }
    
    public List<CentrosCostos> getFiltrarCentrosCostos() {
        return filtrarCentrosCostos;
    }
    
    public void setFiltrarCentrosCostos(List<CentrosCostos> filtrarCentrosCostos) {
        this.filtrarCentrosCostos = filtrarCentrosCostos;
    }
    
    public CentrosCostos getNuevoCentroCosto() {
        if (nuevoCentroCosto == null) {
            nuevoCentroCosto = new CentrosCostos();
        }
        return nuevoCentroCosto;
    }
    
    public void setNuevoCentroCosto(CentrosCostos nuevoCentroCosto) {
        this.nuevoCentroCosto = nuevoCentroCosto;
    }
    
    public CentrosCostos getDuplicarCentroCosto() {
        return duplicarCentroCosto;
    }
    
    public void setDuplicarCentroCosto(CentrosCostos duplicarCentroCosto) {
        this.duplicarCentroCosto = duplicarCentroCosto;
    }
    
    public List<TiposCentrosCostos> getListaTiposCentrosCostos() {
        if (listaTiposCentrosCostos == null) {
            listaTiposCentrosCostos = administrarCentroCostos.lovTiposCentrosCostos();
        }
        return listaTiposCentrosCostos;
    }
    
    public void setListaTiposCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        this.listaTiposCentrosCostos = listaTiposCentrosCostos;
    }
    
    public List<TiposCentrosCostos> getFiltradoTiposCentrosCostos() {
        return filtradoTiposCentrosCostos;
    }
    
    public void setFiltradoTiposCentrosCostos(List<TiposCentrosCostos> filtradoTiposCentrosCostos) {
        this.filtradoTiposCentrosCostos = filtradoTiposCentrosCostos;
    }
    
    public TiposCentrosCostos getTipoCentroCostoSeleccionado() {
        return tipoCentroCostoSeleccionado;
    }
    
    public void setTipoCentroCostoSeleccionado(TiposCentrosCostos tipoCentroCostoSeleccionado) {
        this.tipoCentroCostoSeleccionado = tipoCentroCostoSeleccionado;
    }
    
    public boolean isAceptar() {
        return aceptar;
    }
    
    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }
    
    public List<CentrosCostos> getFilterCentrosCostosPorEmpresa() {
        return filterCentrosCostosPorEmpresa;
    }
    
    public void setFilterCentrosCostosPorEmpresa(List<CentrosCostos> filterCentrosCostosPorEmpresa) {
        this.filterCentrosCostosPorEmpresa = filterCentrosCostosPorEmpresa;
    }
    
    public CentrosCostos getCentrosCostosPorEmpresaSeleccionado() {
        return CentrosCostosPorEmpresaSeleccionado;
    }
    
    public void setCentrosCostosPorEmpresaSeleccionado(CentrosCostos CentrosCostosPorEmpresaSeleccionado) {
        this.CentrosCostosPorEmpresaSeleccionado = CentrosCostosPorEmpresaSeleccionado;
    }
    
    public CentrosCostos getEditarCentroCosto() {
        return editarCentroCosto;
    }
    
    public void setEditarCentroCosto(CentrosCostos editarCentroCosto) {
        this.editarCentroCosto = editarCentroCosto;
    }
    
    public boolean isGuardado() {
        return guardado;
    }
    
    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }
    
    public CentrosCostos getCentroCostoBetaSeleccionado() {
        return centroCostoBetaSeleccionado;
    }
    
    public void setCentroCostoBetaSeleccionado(CentrosCostos centroCostoBetaSeleccionado) {
        this.centroCostoBetaSeleccionado = centroCostoBetaSeleccionado;
    }
    
    public int getTamano() {
        return tamano;
    }
    
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    
}

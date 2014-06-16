/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ProcesosProductivos;
import Entidades.CentrosCostos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarProcesosProductivosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlProcesosProductivos implements Serializable {

    @EJB
    AdministrarProcesosProductivosInterface administrarProcesosProductivos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<ProcesosProductivos> listProcesosProductivos;
    private List<ProcesosProductivos> filtrarProcesosProductivos;
    private List<ProcesosProductivos> crearProcesosProductivos;
    private List<ProcesosProductivos> modificarProcesosProductivos;
    private List<ProcesosProductivos> borrarProcesosProductivos;
    private ProcesosProductivos nuevoProcesosProductivos;
    private ProcesosProductivos duplicarProcesosProductivos;
    private ProcesosProductivos editarProcesosProductivos;
    private ProcesosProductivos procesoProductivoSeleccionado;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, personafir;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;
    private Integer backupCodigo;
    private String backupDescripcion;
    private String backupPais;
    //---------------------------------
    private String backupCiudad;
    private List<CentrosCostos> listaCentrosCostos;
    private List<CentrosCostos> filtradoCentrosCostos;
    private CentrosCostos centrocostoSeleccionado;
    private String nuevoYduplicarCompletarPersona;
    //--------------------------------------
    private String backupBanco;
    private String infoRegistro;
    private String infoRegistroCentroCostos;

    public ControlProcesosProductivos() {
        listProcesosProductivos = null;
        crearProcesosProductivos = new ArrayList<ProcesosProductivos>();
        modificarProcesosProductivos = new ArrayList<ProcesosProductivos>();
        borrarProcesosProductivos = new ArrayList<ProcesosProductivos>();
        permitirIndex = true;
        editarProcesosProductivos = new ProcesosProductivos();
        nuevoProcesosProductivos = new ProcesosProductivos();
        nuevoProcesosProductivos.setCentrocosto(new CentrosCostos());
        duplicarProcesosProductivos = new ProcesosProductivos();
        duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());
        listaCentrosCostos = null;
        filtradoCentrosCostos = null;
        guardado = true;
        tamano = 270;
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarProcesosProductivos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlProcesosProductivos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlProcesosProductivos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listProcesosProductivos.get(index).getSecuencia();
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backupCodigo = listProcesosProductivos.get(index).getCodigo();
                }
                if (cualCelda == 1) {
                    backupDescripcion = listProcesosProductivos.get(index).getDescripcion();
                    System.out.println("DESCRIPCION : " + backupDescripcion);
                }
                if (cualCelda == 2) {
                    backupBanco = listProcesosProductivos.get(index).getCentrocosto().getNombre();
                    System.out.println("BANCO : " + backupBanco);
                }

            } else if (tipoLista == 1) {
                if (cualCelda == 0) {
                    backupCodigo = filtrarProcesosProductivos.get(index).getCodigo();
                }
                if (cualCelda == 1) {
                    backupDescripcion = filtrarProcesosProductivos.get(index).getDescripcion();
                    System.out.println("DESCRIPCION : " + backupDescripcion);
                }
                if (cualCelda == 2) {
                    backupBanco = filtrarProcesosProductivos.get(index).getCentrocosto().getNombre();
                    System.out.println("BANCO : " + backupBanco);
                }

            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlProcesosProductivos.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 2) {
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlProcesosProductivos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {

            if (cualCelda == 2) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProcesosProductivos");
            bandera = 0;
            filtrarProcesosProductivos = null;
            tipoLista = 0;
        }

        borrarProcesosProductivos.clear();
        crearProcesosProductivos.clear();
        modificarProcesosProductivos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listProcesosProductivos = null;
        guardado = true;
        permitirIndex = true;
        getListProcesosProductivos();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listProcesosProductivos == null || listProcesosProductivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosProcesosProductivos");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProcesosProductivos");
            bandera = 0;
            filtrarProcesosProductivos = null;
            tipoLista = 0;
        }

        borrarProcesosProductivos.clear();
        crearProcesosProductivos.clear();
        modificarProcesosProductivos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listProcesosProductivos = null;
        guardado = true;
        permitirIndex = true;
        getListProcesosProductivos();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listProcesosProductivos == null || listProcesosProductivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosProcesosProductivos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();

        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
            codigo.setFilterStyle("width: 20px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
            descripcion.setFilterStyle("width: 130px");
            personafir = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
            personafir.setFilterStyle("width: 130px");
            RequestContext.getCurrentInstance().update("form:datosProcesosProductivos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProcesosProductivos");
            bandera = 0;
            filtrarProcesosProductivos = null;
            tipoLista = 0;
        }
    }

    public void actualizarCentrosCostos() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("centrocosto seleccionado : " + centrocostoSeleccionado.getNombre());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listProcesosProductivos.get(index).setCentrocosto(centrocostoSeleccionado);

                if (!crearProcesosProductivos.contains(listProcesosProductivos.get(index))) {
                    if (modificarProcesosProductivos.isEmpty()) {
                        modificarProcesosProductivos.add(listProcesosProductivos.get(index));
                    } else if (!modificarProcesosProductivos.contains(listProcesosProductivos.get(index))) {
                        modificarProcesosProductivos.add(listProcesosProductivos.get(index));
                    }
                }
            } else {
                filtrarProcesosProductivos.get(index).setCentrocosto(centrocostoSeleccionado);

                if (!crearProcesosProductivos.contains(filtrarProcesosProductivos.get(index))) {
                    if (modificarProcesosProductivos.isEmpty()) {
                        modificarProcesosProductivos.add(filtrarProcesosProductivos.get(index));
                    } else if (!modificarProcesosProductivos.contains(filtrarProcesosProductivos.get(index))) {
                        modificarProcesosProductivos.add(filtrarProcesosProductivos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR PAIS PAIS SELECCIONADO : " + centrocostoSeleccionado.getNombre());
            context.update("form:datosProcesosProductivos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR PAIS NUEVO DEPARTAMENTO: " + centrocostoSeleccionado.getNombre());
            nuevoProcesosProductivos.setCentrocosto(centrocostoSeleccionado);
            context.update("formularioDialogos:nuevoPersona");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR PAIS DUPLICAR DEPARTAMENO: " + centrocostoSeleccionado.getNombre());
            duplicarProcesosProductivos.setCentrocosto(centrocostoSeleccionado);
            context.update("formularioDialogos:duplicarPersona");
        }
        filtradoCentrosCostos = null;
        centrocostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("personasDialogo.hide()");
        context.reset("form:lovCentrosCostos:globalFilter");
        context.update("form:lovCentrosCostos");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioCentrosCostos() {
        listProcesosProductivos.get(index).getCentrocosto().setNombre(backupBanco);
        filtradoCentrosCostos = null;
        centrocostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarProcesosProductivos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;
        int coincidencias = 0;
        int contador = 0;
        boolean banderita = false;
        boolean banderita1 = false;
        boolean banderita2 = false;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearProcesosProductivos.contains(listProcesosProductivos.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listProcesosProductivos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listProcesosProductivos.get(indice).setCodigo(backupCodigo);

                    } else {
                        for (int j = 0; j < listProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (listProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listProcesosProductivos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listProcesosProductivos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listProcesosProductivos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listProcesosProductivos.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarProcesosProductivos.isEmpty()) {
                            modificarProcesosProductivos.add(listProcesosProductivos.get(indice));
                        } else if (!modificarProcesosProductivos.contains(listProcesosProductivos.get(indice))) {
                            modificarProcesosProductivos.add(listProcesosProductivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosProcesosProductivos");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listProcesosProductivos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listProcesosProductivos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (listProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listProcesosProductivos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listProcesosProductivos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listProcesosProductivos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listProcesosProductivos.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (guardado == true) {
                            guardado = false;
                        }
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosProcesosProductivos");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearProcesosProductivos.contains(filtrarProcesosProductivos.get(indice))) {
                    if (filtrarProcesosProductivos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarProcesosProductivos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (filtrarProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (filtrarProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarProcesosProductivos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarProcesosProductivos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarProcesosProductivos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarProcesosProductivos.isEmpty()) {
                            modificarProcesosProductivos.add(filtrarProcesosProductivos.get(indice));
                        } else if (!modificarProcesosProductivos.contains(filtrarProcesosProductivos.get(indice))) {
                            modificarProcesosProductivos.add(filtrarProcesosProductivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (filtrarProcesosProductivos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarProcesosProductivos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (filtrarProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listProcesosProductivos.size(); j++) {
                            if (j != indice) {
                                if (filtrarProcesosProductivos.get(indice).getCodigo() == listProcesosProductivos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarProcesosProductivos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarProcesosProductivos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarProcesosProductivos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarProcesosProductivos.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosProcesosProductivos");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("PERSONAS")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listProcesosProductivos.get(indice).getCentrocosto().getNombre());
            if (!listProcesosProductivos.get(indice).getCentrocosto().getNombre().equals("")) {
                if (tipoLista == 0) {
                    listProcesosProductivos.get(indice).getCentrocosto().setNombre(backupBanco);
                } else {
                    listProcesosProductivos.get(indice).getCentrocosto().setNombre(backupBanco);
                }

                for (int i = 0; i < listaCentrosCostos.size(); i++) {
                    if (listaCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listProcesosProductivos.get(indice).setCentrocosto(listaCentrosCostos.get(indiceUnicoElemento));
                    } else {
                        filtrarProcesosProductivos.get(indice).setCentrocosto(listaCentrosCostos.get(indiceUnicoElemento));
                    }
                    listaCentrosCostos.clear();
                    listaCentrosCostos = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (backupBanco != null) {
                    if (tipoLista == 0) {
                        listProcesosProductivos.get(index).getCentrocosto().setNombre(backupBanco);
                    } else {
                        filtrarProcesosProductivos.get(index).getCentrocosto().setNombre(backupBanco);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO PERSONA : " + backupBanco);
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearProcesosProductivos.contains(listProcesosProductivos.get(indice))) {

                        if (modificarProcesosProductivos.isEmpty()) {
                            modificarProcesosProductivos.add(listProcesosProductivos.get(indice));
                        } else if (!modificarProcesosProductivos.contains(listProcesosProductivos.get(indice))) {
                            modificarProcesosProductivos.add(listProcesosProductivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearProcesosProductivos.contains(filtrarProcesosProductivos.get(indice))) {

                        if (modificarProcesosProductivos.isEmpty()) {
                            modificarProcesosProductivos.add(filtrarProcesosProductivos.get(indice));
                        } else if (!modificarProcesosProductivos.contains(filtrarProcesosProductivos.get(indice))) {
                            modificarProcesosProductivos.add(filtrarProcesosProductivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosProcesosProductivos");
            context.update("form:ACEPTAR");

        }
    }

    public void verificarBorrado() {
        BigInteger contarUnidadesProducidasProcesoProductivo;
        BigInteger contarTarifasProductosProcesoProductivo;
        BigInteger contarCargosProcesoProductivo;

        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de ControlTiposFamiliares ");
            if (tipoLista == 0) {
                contarCargosProcesoProductivo = administrarProcesosProductivos.contarCargosProcesoProductivo(listProcesosProductivos.get(index).getSecuencia());
                contarTarifasProductosProcesoProductivo = administrarProcesosProductivos.contarTarifasProductosProcesoProductivo(listProcesosProductivos.get(index).getSecuencia());
                contarUnidadesProducidasProcesoProductivo = administrarProcesosProductivos.contarUnidadesProducidasProcesoProductivo(listProcesosProductivos.get(index).getSecuencia());
            } else {
                contarCargosProcesoProductivo = administrarProcesosProductivos.contarCargosProcesoProductivo(filtrarProcesosProductivos.get(index).getSecuencia());
                contarTarifasProductosProcesoProductivo = administrarProcesosProductivos.contarTarifasProductosProcesoProductivo(filtrarProcesosProductivos.get(index).getSecuencia());
                contarUnidadesProducidasProcesoProductivo = administrarProcesosProductivos.contarUnidadesProducidasProcesoProductivo(filtrarProcesosProductivos.get(index).getSecuencia());
            }
            if (contarCargosProcesoProductivo.equals(new BigInteger("0")) && contarTarifasProductosProcesoProductivo.equals(new BigInteger("0")) && contarUnidadesProducidasProcesoProductivo.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoProcesosProductivos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                contarCargosProcesoProductivo = new BigInteger("-1");
                contarTarifasProductosProcesoProductivo = new BigInteger("-1");
                contarUnidadesProducidasProcesoProductivo = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposFamiliares verificarBorrado ERROR " + e);
        }
    }

    public void borrandoProcesosProductivos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoProcesosProductivos");
                if (!modificarProcesosProductivos.isEmpty() && modificarProcesosProductivos.contains(listProcesosProductivos.get(index))) {
                    int modIndex = modificarProcesosProductivos.indexOf(listProcesosProductivos.get(index));
                    modificarProcesosProductivos.remove(modIndex);
                    borrarProcesosProductivos.add(listProcesosProductivos.get(index));
                } else if (!crearProcesosProductivos.isEmpty() && crearProcesosProductivos.contains(listProcesosProductivos.get(index))) {
                    int crearIndex = crearProcesosProductivos.indexOf(listProcesosProductivos.get(index));
                    crearProcesosProductivos.remove(crearIndex);
                } else {
                    borrarProcesosProductivos.add(listProcesosProductivos.get(index));
                }
                listProcesosProductivos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoProcesosProductivos ");
                if (!modificarProcesosProductivos.isEmpty() && modificarProcesosProductivos.contains(filtrarProcesosProductivos.get(index))) {
                    int modIndex = modificarProcesosProductivos.indexOf(filtrarProcesosProductivos.get(index));
                    modificarProcesosProductivos.remove(modIndex);
                    borrarProcesosProductivos.add(filtrarProcesosProductivos.get(index));
                } else if (!crearProcesosProductivos.isEmpty() && crearProcesosProductivos.contains(filtrarProcesosProductivos.get(index))) {
                    int crearIndex = crearProcesosProductivos.indexOf(filtrarProcesosProductivos.get(index));
                    crearProcesosProductivos.remove(crearIndex);
                } else {
                    borrarProcesosProductivos.add(filtrarProcesosProductivos.get(index));
                }
                int VCIndex = listProcesosProductivos.indexOf(filtrarProcesosProductivos.get(index));
                listProcesosProductivos.remove(VCIndex);
                filtrarProcesosProductivos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (listProcesosProductivos == null || listProcesosProductivos.isEmpty()) {
                infoRegistro = "Cantidad de registros: 0 ";
            } else {
                infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();
            }
            context.update("form:informacionRegistro");
            context.update("form:datosProcesosProductivos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void valoresBackupAutocompletar(int tipoNuevo, String valorCambio) {
        System.out.println("1...");
        if (valorCambio.equals("PERSONA")) {
            if (tipoNuevo == 1) {
                nuevoYduplicarCompletarPersona = nuevoProcesosProductivos.getCentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarPersona = duplicarProcesosProductivos.getCentrocosto().getNombre();
            }

            System.out.println("PERSONA : " + nuevoYduplicarCompletarPersona);
        }
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PERSONA")) {
            System.out.println(" nueva Ciudad    Entro al if 'Centro costo'");
            System.out.println("NUEVO PERSONA :-------> " + nuevoProcesosProductivos.getCentrocosto().getNombre());

            if (!nuevoProcesosProductivos.getCentrocosto().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarPersona);
                nuevoProcesosProductivos.getCentrocosto().setNombre(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaCentrosCostos.size(); i++) {
                    if (listaCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoProcesosProductivos.setCentrocosto(listaCentrosCostos.get(indiceUnicoElemento));
                    listaCentrosCostos = null;
                    System.err.println("PERSONA GUARDADA :-----> " + nuevoProcesosProductivos.getCentrocosto().getNombre());
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoProcesosProductivos.getCentrocosto().setNombre(nuevoYduplicarCompletarPersona);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoProcesosProductivos.setCentrocosto(new CentrosCostos());
                nuevoProcesosProductivos.getCentrocosto().setNombre(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoProcesosProductivos.getCentrocosto().getNombre());
            }
            context.update("formularioDialogos:nuevoPersona");
        }
    }

    public void asignarVariableCentrosCostos(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:personasDialogo");
        context.execute("personasDialogo.show()");
    }

    public void asignarVariableCiudades(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:cargosDialogo");
        context.execute("cargosDialogo.show()");
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PERSONA")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarPersona);

            if (!duplicarProcesosProductivos.getCentrocosto().getNombre().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarPersona);
                duplicarProcesosProductivos.getCentrocosto().setNombre(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaCentrosCostos.size(); i++) {
                    if (listaCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarProcesosProductivos.setCentrocosto(listaCentrosCostos.get(indiceUnicoElemento));
                    listaCentrosCostos = null;
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarProcesosProductivos.getEmpresa().setNombre(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());
                    duplicarProcesosProductivos.getCentrocosto().setNombre(" ");

                    System.out.println("DUPLICAR PERSONA  : " + duplicarProcesosProductivos.getCentrocosto().getNombre());
                    System.out.println("nuevoYduplicarCompletarPERSONA : " + nuevoYduplicarCompletarPersona);
                    if (tipoLista == 0) {
                        listProcesosProductivos.get(index).getCentrocosto().setNombre(nuevoYduplicarCompletarPersona);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listProcesosProductivos.get(index).getCentrocosto().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarProcesosProductivos.get(index).getCentrocosto().setNombre(nuevoYduplicarCompletarPersona);
                    }

                }

            }
            context.update("formularioDialogos:duplicarPersona");
        }
    }

    /*public void verificarBorrado() {
     System.out.println("Estoy en verificarBorrado");
     BigInteger contarBienProgramacionesDepartamento;
     BigInteger contarCapModulosDepartamento;
     BigInteger contarCiudadesDepartamento;
     BigInteger contarSoAccidentesMedicosDepartamento;

     try {
     System.err.println("Control Secuencia de ControlProcesosProductivos ");
     if (tipoLista == 0) {
     contarBienProgramacionesDepartamento = administrarProcesosProductivos.contarBienProgramacionesDepartamento(listProcesosProductivos.get(index).getSecuencia());
     contarCapModulosDepartamento = administrarProcesosProductivos.contarCapModulosDepartamento(listProcesosProductivos.get(index).getSecuencia());
     contarCiudadesDepartamento = administrarProcesosProductivos.contarCiudadesDepartamento(listProcesosProductivos.get(index).getSecuencia());
     contarSoAccidentesMedicosDepartamento = administrarProcesosProductivos.contarSoAccidentesMedicosDepartamento(listProcesosProductivos.get(index).getSecuencia());
     } else {
     contarBienProgramacionesDepartamento = administrarProcesosProductivos.contarBienProgramacionesDepartamento(filtrarProcesosProductivos.get(index).getSecuencia());
     contarCapModulosDepartamento = administrarProcesosProductivos.contarCapModulosDepartamento(filtrarProcesosProductivos.get(index).getSecuencia());
     contarCiudadesDepartamento = administrarProcesosProductivos.contarCiudadesDepartamento(filtrarProcesosProductivos.get(index).getSecuencia());
     contarSoAccidentesMedicosDepartamento = administrarProcesosProductivos.contarSoAccidentesMedicosDepartamento(filtrarProcesosProductivos.get(index).getSecuencia());
     }
     if (contarBienProgramacionesDepartamento.equals(new BigInteger("0"))
     && contarCapModulosDepartamento.equals(new BigInteger("0"))
     && contarCiudadesDepartamento.equals(new BigInteger("0"))
     && contarSoAccidentesMedicosDepartamento.equals(new BigInteger("0"))) {
     System.out.println("Borrado==0");
     borrandoProcesosProductivos();
     } else {
     System.out.println("Borrado>0");

     RequestContext context = RequestContext.getCurrentInstance();
     context.update("form:validacionBorrar");
     context.execute("validacionBorrar.show()");
     index = -1;
     contarBienProgramacionesDepartamento = new BigInteger("-1");
     contarCapModulosDepartamento = new BigInteger("-1");
     contarCiudadesDepartamento = new BigInteger("-1");
     contarSoAccidentesMedicosDepartamento = new BigInteger("-1");

     }
     } catch (Exception e) {
     System.err.println("ERROR ControlProcesosProductivos verificarBorrado ERROR " + e);
     }
     }
     */
    public void revisarDialogoGuardar() {

        if (!borrarProcesosProductivos.isEmpty() || !crearProcesosProductivos.isEmpty() || !modificarProcesosProductivos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarProcesosProductivos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarProcesosProductivos");
            if (!borrarProcesosProductivos.isEmpty()) {
                administrarProcesosProductivos.borrarProcesosProductivos(borrarProcesosProductivos);
                //mostrarBorrados
                registrosBorrados = borrarProcesosProductivos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarProcesosProductivos.clear();
            }
            if (!modificarProcesosProductivos.isEmpty()) {
                administrarProcesosProductivos.modificarProcesosProductivos(modificarProcesosProductivos);
                modificarProcesosProductivos.clear();
            }
            if (!crearProcesosProductivos.isEmpty()) {
                administrarProcesosProductivos.crearProcesosProductivos(crearProcesosProductivos);
                crearProcesosProductivos.clear();
            }
            listProcesosProductivos = null;
            k = 0;
            guardado = true;

            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarProcesosProductivos = listProcesosProductivos.get(index);
            }
            if (tipoLista == 1) {
                editarProcesosProductivos = filtrarProcesosProductivos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editPais");
                context.execute("editPais.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editSubtituloFirma");
                context.execute("editSubtituloFirma.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editCentrosCostos");
                context.execute("editCentrosCostos.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editCiudades");
                context.execute("editCiudades.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoProcesosProductivos() {
        System.out.println("agregarNuevoProcesosProductivos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoProcesosProductivos.getCodigo() == null) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoProcesosProductivos.getCodigo());

            for (int x = 0; x < listProcesosProductivos.size(); x++) {
                if (listProcesosProductivos.get(x).getCodigo().equals(nuevoProcesosProductivos.getCodigo())) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;//1
            }
        }
        if (nuevoProcesosProductivos.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//2

        }

        if (nuevoProcesosProductivos.getCentrocosto().getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Centro Costo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//4

        }

        System.out.println("contador " + contador);

        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();

                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                bandera = 0;
                filtrarProcesosProductivos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoProcesosProductivos.setSecuencia(l);

            crearProcesosProductivos.add(nuevoProcesosProductivos);

            listProcesosProductivos.add(nuevoProcesosProductivos);
            nuevoProcesosProductivos = new ProcesosProductivos();
            nuevoProcesosProductivos.setCentrocosto(new CentrosCostos());
            context.update("form:datosProcesosProductivos");

            infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();

            context.update("form:informacionRegistro");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroProcesosProductivos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoProcesosProductivos() {
        System.out.println("limpiarNuevoProcesosProductivos");
        nuevoProcesosProductivos = new ProcesosProductivos();
        nuevoProcesosProductivos.setCentrocosto(new CentrosCostos());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void cargarNuevoProcesosProductivos() {
        System.out.println("cargarNuevoProcesosProductivos");

        duplicarProcesosProductivos = new ProcesosProductivos();
        duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("nuevoRegistroProcesosProductivos.show()");

    }

    public void duplicandoProcesosProductivos() {
        System.out.println("duplicandoProcesosProductivos");
        if (index >= 0) {
            duplicarProcesosProductivos = new ProcesosProductivos();
            duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarProcesosProductivos.setSecuencia(l);
                duplicarProcesosProductivos.setCodigo(listProcesosProductivos.get(index).getCodigo());
                duplicarProcesosProductivos.setDescripcion(listProcesosProductivos.get(index).getDescripcion());
                duplicarProcesosProductivos.setCentrocosto(listProcesosProductivos.get(index).getCentrocosto());
            }
            if (tipoLista == 1) {
                duplicarProcesosProductivos.setSecuencia(l);
                duplicarProcesosProductivos.setCodigo(filtrarProcesosProductivos.get(index).getCodigo());
                duplicarProcesosProductivos.setDescripcion(filtrarProcesosProductivos.get(index).getDescripcion());
                duplicarProcesosProductivos.setCentrocosto(filtrarProcesosProductivos.get(index).getCentrocosto());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroProcesosProductivos.show()");
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS EMPRESAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarProcesosProductivos.getCodigo());

        if (duplicarProcesosProductivos.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listProcesosProductivos.size(); x++) {
                if (listProcesosProductivos.get(x).getCodigo().equals(duplicarProcesosProductivos.getCodigo())) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Existan Codigo Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
                duplicados = 0;
            }
        }

        if (duplicarProcesosProductivos.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (duplicarProcesosProductivos.getCentrocosto().getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   *Centro Costo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarProcesosProductivos.getSecuencia() + "  " + duplicarProcesosProductivos.getCodigo());
            if (crearProcesosProductivos.contains(duplicarProcesosProductivos)) {
                System.out.println("Ya lo contengo.");
            }
            listProcesosProductivos.add(duplicarProcesosProductivos);
            crearProcesosProductivos.add(duplicarProcesosProductivos);
            context.update("form:datosProcesosProductivos");
            index = -1;
            System.out.println("--------------DUPLICAR------------------------");
            System.out.println("CODIGO : " + duplicarProcesosProductivos.getCodigo());
            System.out.println("EMPRESA: " + duplicarProcesosProductivos.getDescripcion());
            System.out.println("CARGO : " + duplicarProcesosProductivos.getCentrocosto().getNombre());
            System.out.println("--------------DUPLICAR------------------------");

            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");

            infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) c.getViewRoot().findComponent("form:datosProcesosProductivos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosProcesosProductivos");
                bandera = 0;
                filtrarProcesosProductivos = null;
                tipoLista = 0;
            }
            duplicarProcesosProductivos = new ProcesosProductivos();
            duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());

            RequestContext.getCurrentInstance().execute("duplicarRegistroProcesosProductivos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarProcesosProductivos() {
        duplicarProcesosProductivos = new ProcesosProductivos();
        duplicarProcesosProductivos.setCentrocosto(new CentrosCostos());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosProcesosProductivosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PROCESOSPRODUCTIVOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosProcesosProductivosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PROCESOSPRODUCTIVOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listProcesosProductivos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PROCESOSPRODUCTIVOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("PROCESOSPRODUCTIVOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ProcesosProductivos> getListProcesosProductivos() {
        if (listProcesosProductivos == null) {
            listProcesosProductivos = administrarProcesosProductivos.consultarProcesosProductivos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listProcesosProductivos == null || listProcesosProductivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listProcesosProductivos.size();
        }
        context.update("form:informacionRegistro");
        return listProcesosProductivos;
    }

    public void setListProcesosProductivos(List<ProcesosProductivos> listProcesosProductivos) {
        this.listProcesosProductivos = listProcesosProductivos;
    }

    public List<ProcesosProductivos> getFiltrarProcesosProductivos() {
        return filtrarProcesosProductivos;
    }

    public void setFiltrarProcesosProductivos(List<ProcesosProductivos> filtrarProcesosProductivos) {
        this.filtrarProcesosProductivos = filtrarProcesosProductivos;
    }

    public ProcesosProductivos getNuevoProcesosProductivos() {
        return nuevoProcesosProductivos;
    }

    public void setNuevoProcesosProductivos(ProcesosProductivos nuevoProcesosProductivos) {
        this.nuevoProcesosProductivos = nuevoProcesosProductivos;
    }

    public ProcesosProductivos getDuplicarProcesosProductivos() {
        return duplicarProcesosProductivos;
    }

    public void setDuplicarProcesosProductivos(ProcesosProductivos duplicarProcesosProductivos) {
        this.duplicarProcesosProductivos = duplicarProcesosProductivos;
    }

    public ProcesosProductivos getEditarProcesosProductivos() {
        return editarProcesosProductivos;
    }

    public void setEditarProcesosProductivos(ProcesosProductivos editarProcesosProductivos) {
        this.editarProcesosProductivos = editarProcesosProductivos;
    }

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

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public List<CentrosCostos> getListaCentrosCostos() {
        if (listaCentrosCostos == null) {
            listaCentrosCostos = administrarProcesosProductivos.consultarLOVCentrosCostos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCentrosCostos == null || listaCentrosCostos.isEmpty()) {
            infoRegistroCentroCostos = "Cantidad de registros: 0 ";
        } else {
            infoRegistroCentroCostos = "Cantidad de registros: " + listaCentrosCostos.size();
        }
        context.update("form:infoRegistroCentroCostos");
        return listaCentrosCostos;
    }

    public void setListaCentrosCostos(List<CentrosCostos> listaCentrosCostos) {
        this.listaCentrosCostos = listaCentrosCostos;
    }

    public List<CentrosCostos> getFiltradoCentrosCostos() {
        return filtradoCentrosCostos;
    }

    public void setFiltradoCentrosCostos(List<CentrosCostos> filtradoCentrosCostos) {
        this.filtradoCentrosCostos = filtradoCentrosCostos;
    }

    public CentrosCostos getCentrocostoSeleccionado() {
        return centrocostoSeleccionado;
    }

    public void setCentrocostoSeleccionado(CentrosCostos centrocostoSeleccionado) {
        this.centrocostoSeleccionado = centrocostoSeleccionado;
    }

    public ProcesosProductivos getProcesoProductivoSeleccionado() {
        return procesoProductivoSeleccionado;
    }

    public void setProcesoProductivoSeleccionado(ProcesosProductivos procesoProductivoSeleccionado) {
        this.procesoProductivoSeleccionado = procesoProductivoSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroCentroCostos() {
        return infoRegistroCentroCostos;
    }

    public void setInfoRegistroCentroCostos(String infoRegistroCentroCostos) {
        this.infoRegistroCentroCostos = infoRegistroCentroCostos;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

}

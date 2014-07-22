/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ConceptosRetroactivos;
import Entidades.Conceptos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarConceptosRetroactivosInterface;
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
public class ControlConceptosRetroactivos implements Serializable {

    @EJB
    AdministrarConceptosRetroactivosInterface administrarConceptosRetroactivos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<ConceptosRetroactivos> listConceptosRetroactivos;
    private List<ConceptosRetroactivos> filtrarConceptosRetroactivos;
    private List<ConceptosRetroactivos> crearConceptosRetroactivos;
    private List<ConceptosRetroactivos> modificarConceptosRetroactivos;
    private List<ConceptosRetroactivos> borrarConceptosRetroactivos;
    private ConceptosRetroactivos nuevoConceptosRetroactivos;
    private ConceptosRetroactivos duplicarConceptosRetroactivos;
    private ConceptosRetroactivos editarConceptosRetroactivos;
    private ConceptosRetroactivos sucursalSeleccionada;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, personafir, cargo;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;

    //---------------------------------
    private String backupConcepto;
    private List<Conceptos> listaConceptos;
    private List<Conceptos> filtradoConceptos;
    private Conceptos conceptoSeleccionado;
    private String nuevoYduplicarCompletarConcepto;
    //--------------------------------------
    private String backupConceptoRetro;
    private List<Conceptos> listaConceptosRetro;
    private List<Conceptos> filtradoConceptosRetro;
    private Conceptos conceptoRetroSeleccionado;
    private String nuevoYduplicarCompletarConceptoRetro;

    public ControlConceptosRetroactivos() {
        listConceptosRetroactivos = null;
        crearConceptosRetroactivos = new ArrayList<ConceptosRetroactivos>();
        modificarConceptosRetroactivos = new ArrayList<ConceptosRetroactivos>();
        borrarConceptosRetroactivos = new ArrayList<ConceptosRetroactivos>();
        permitirIndex = true;
        editarConceptosRetroactivos = new ConceptosRetroactivos();
        nuevoConceptosRetroactivos = new ConceptosRetroactivos();
        nuevoConceptosRetroactivos.setConcepto(new Conceptos());
        nuevoConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
        duplicarConceptosRetroactivos = new ConceptosRetroactivos();
        duplicarConceptosRetroactivos.setConcepto(new Conceptos());
        duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
        listaConceptos = null;
        filtradoConceptos = null;
        listaConceptosRetro = null;
        filtradoConceptosRetro = null;
        guardado = true;
        tamano = 270;
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarConceptosRetroactivos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlConceptosRetroactivos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarConceptosRetroactivos.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlConceptosRetroactivos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listConceptosRetroactivos.get(index).getSecuencia();
            if (tipoLista == 0) {

                if (cualCelda == 0) {
                    backupConcepto = listConceptosRetroactivos.get(index).getConcepto().getDescripcion();
                    System.out.println("BANCO : " + backupConcepto);

                }
                if (cualCelda == 1) {
                    backupConceptoRetro = listConceptosRetroactivos.get(index).getConceptoRetroActivo().getDescripcion();
                    System.out.println("CIUDAD : " + backupConceptoRetro);

                }

            } else if (tipoLista == 1) {

                if (cualCelda ==0) {
                    backupConcepto = filtrarConceptosRetroactivos.get(index).getConcepto().getDescripcion();
                    System.out.println("BANCO : " + backupConcepto);

                }
                if (cualCelda == 1) {
                    backupConceptoRetro = filtrarConceptosRetroactivos.get(index).getConceptoRetroActivo().getDescripcion();
                    System.out.println("CIUDAD : " + backupConceptoRetro);

                }
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlConceptosRetroactivos.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 0) {
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
                dig = -1;
            }
            if (dig == 1) {
                context.update("form:conceptosRetroDialogo");
                context.execute("conceptosRetroDialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlConceptosRetroactivos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {

            if (cualCelda == 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
            }
            if (cualCelda == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:conceptosRetroDialogo");
                context.execute("conceptosRetroDialogo.show()");
            }
        }
    }
    private String infoRegistro;

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptosRetroactivos");
            bandera = 0;
            filtrarConceptosRetroactivos = null;
            tipoLista = 0;
        }

        borrarConceptosRetroactivos.clear();
        crearConceptosRetroactivos.clear();
        modificarConceptosRetroactivos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptosRetroactivos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptosRetroactivos == null || listConceptosRetroactivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosConceptosRetroactivos");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptosRetroactivos");
            bandera = 0;
            filtrarConceptosRetroactivos = null;
            tipoLista = 0;
        }

        borrarConceptosRetroactivos.clear();
        crearConceptosRetroactivos.clear();
        modificarConceptosRetroactivos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptosRetroactivos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptosRetroactivos == null || listConceptosRetroactivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosConceptosRetroactivos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
            codigo.setFilterStyle("width: 20px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
            descripcion.setFilterStyle("width: 130px");
            personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
            personafir.setFilterStyle("width: 130px");
            cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
            cargo.setFilterStyle("width: 130px");
            RequestContext.getCurrentInstance().update("form:datosConceptosRetroactivos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosConceptosRetroactivos");
            bandera = 0;
            filtrarConceptosRetroactivos = null;
            tipoLista = 0;
        }
    }

    public void actualizarConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("banco seleccionado : " + conceptoSeleccionado.getDescripcion());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);
        System.err.println("banco seleccionado : " + conceptoSeleccionado.getDescripcion());
        System.err.println("tipo Actualizacion : " + tipoActualizacion);
        System.err.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listConceptosRetroactivos.get(index).setConcepto(conceptoSeleccionado);

                if (!crearConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                    if (modificarConceptosRetroactivos.isEmpty()) {
                        modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                    } else if (!modificarConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                        modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                    }
                }
            } else {
                filtrarConceptosRetroactivos.get(index).setConcepto(conceptoSeleccionado);

                if (!crearConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                    if (modificarConceptosRetroactivos.isEmpty()) {
                        modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                    } else if (!modificarConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                        modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR PAIS PAIS SELECCIONADO : " + conceptoSeleccionado.getDescripcion());
            context.update("form:datosConceptosRetroactivos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR PAIS NUEVO DEPARTAMENTO: " + conceptoSeleccionado.getDescripcion());
            nuevoConceptosRetroactivos.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:nuevoPersona");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR PAIS DUPLICAR DEPARTAMENO: " + conceptoSeleccionado.getDescripcion());
            duplicarConceptosRetroactivos.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:duplicarPersona");
        }
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("conceptosDialogo.hide()");
        context.reset("form:lovConceptos:globalFilter");
        context.update("form:lovConceptos");
        //context.update("form:datosHvEntrevista");
    }

    public void actualizarConceptosRetro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("ciudad seleccionado : " + conceptoRetroSeleccionado.getDescripcion());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listConceptosRetroactivos.get(index).setConceptoRetroActivo(conceptoRetroSeleccionado);

                if (!crearConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                    if (modificarConceptosRetroactivos.isEmpty()) {
                        modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                    } else if (!modificarConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                        modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                    }
                }
            } else {
                filtrarConceptosRetroactivos.get(index).setConceptoRetroActivo(conceptoRetroSeleccionado);

                if (!crearConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                    if (modificarConceptosRetroactivos.isEmpty()) {
                        modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                    } else if (!modificarConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                        modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR PAIS CONCEPTOSRETRO SELECCIONADO : " + conceptoRetroSeleccionado.getDescripcion());
            context.update("form:datosConceptosRetroactivos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR PAIS NUEVO DEPARTAMENTO: " + conceptoRetroSeleccionado.getDescripcion());
            nuevoConceptosRetroactivos.setConceptoRetroActivo(conceptoRetroSeleccionado);
            context.update("formularioDialogos:nuevoCargo");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR PAIS DUPLICAR DEPARTAMENO: " + conceptoRetroSeleccionado.getDescripcion());
            duplicarConceptosRetroactivos.setConceptoRetroActivo(conceptoRetroSeleccionado);
            context.update("formularioDialogos:duplicarCargo");
        }
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("conceptosRetroDialogo.hide()");
        context.reset("form:lovConceptosRetro:globalFilter");
        context.update("form:lovConceptosRetro");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioConceptos() {
        listConceptosRetroactivos.get(index).getConcepto().setDescripcion(backupConcepto);
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void cancelarCambioConceptosRetro() {
        filtradoConceptosRetro = null;
        conceptoRetroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarConceptosRetroactivos(int indice, String confirmarCambio, String valorConfirmar) {
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
        if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listConceptosRetroactivos.get(indice).getConcepto().getDescripcion());
            if (!listConceptosRetroactivos.get(indice).getConcepto().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listConceptosRetroactivos.get(indice).getConcepto().setDescripcion(backupConcepto);
                } else {
                    listConceptosRetroactivos.get(indice).getConcepto().setDescripcion(backupConcepto);
                }

                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    } else {
                        filtrarConceptosRetroactivos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    }
                    listaConceptos.clear();
                    listaConceptos = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:conceptosDialogo");
                    context.execute("conceptosDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (backupConcepto != null) {
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(index).getConcepto().setDescripcion(backupConcepto);
                    } else {
                        filtrarConceptosRetroactivos.get(index).getConcepto().setDescripcion(backupConcepto);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO CONCEPTOS : " + backupConcepto);
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearConceptosRetroactivos.contains(listConceptosRetroactivos.get(indice))) {

                        if (modificarConceptosRetroactivos.isEmpty()) {
                            modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(indice));
                        } else if (!modificarConceptosRetroactivos.contains(listConceptosRetroactivos.get(indice))) {
                            modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(indice))) {

                        if (modificarConceptosRetroactivos.isEmpty()) {
                            modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(indice));
                        } else if (!modificarConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(indice))) {
                            modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosConceptosRetroactivos");
            context.update("form:ACEPTAR");

        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTOSRETRO")) {
            System.out.println("MODIFICANDO CONCEPTOSRETRO: " + listConceptosRetroactivos.get(indice).getConceptoRetroActivo().getDescripcion());
            if (!listConceptosRetroactivos.get(indice).getConcepto().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listConceptosRetroactivos.get(indice).getConceptoRetroActivo().setDescripcion(backupConceptoRetro);
                } else {
                    listConceptosRetroactivos.get(indice).getConceptoRetroActivo().setDescripcion(backupConceptoRetro);
                }

                for (int i = 0; i < listaConceptosRetro.size(); i++) {
                    if (listaConceptosRetro.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(indice).setConceptoRetroActivo(listaConceptosRetro.get(indiceUnicoElemento));
                    } else {
                        filtrarConceptosRetroactivos.get(indice).setConceptoRetroActivo(listaConceptosRetro.get(indiceUnicoElemento));
                    }
                    listaConceptosRetro.clear();
                    listaConceptosRetro = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:conceptosRetroDialogo");
                    context.execute("conceptosRetroDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (backupConceptoRetro != null) {
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(index).getConceptoRetroActivo().setDescripcion(backupConceptoRetro);
                    } else {
                        filtrarConceptosRetroactivos.get(index).getConceptoRetroActivo().setDescripcion(backupConceptoRetro);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO CONCEPTOSRETRO : " + backupConceptoRetro);
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearConceptosRetroactivos.contains(listConceptosRetroactivos.get(indice))) {

                        if (modificarConceptosRetroactivos.isEmpty()) {
                            modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(indice));
                        } else if (!modificarConceptosRetroactivos.contains(listConceptosRetroactivos.get(indice))) {
                            modificarConceptosRetroactivos.add(listConceptosRetroactivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(indice))) {

                        if (modificarConceptosRetroactivos.isEmpty()) {
                            modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(indice));
                        } else if (!modificarConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(indice))) {
                            modificarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosConceptosRetroactivos");
            context.update("form:ACEPTAR");

        }

    }

    public void borrandoConceptosRetroactivos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoConceptosRetroactivos");
                if (!modificarConceptosRetroactivos.isEmpty() && modificarConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                    int modIndex = modificarConceptosRetroactivos.indexOf(listConceptosRetroactivos.get(index));
                    modificarConceptosRetroactivos.remove(modIndex);
                    borrarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                } else if (!crearConceptosRetroactivos.isEmpty() && crearConceptosRetroactivos.contains(listConceptosRetroactivos.get(index))) {
                    int crearIndex = crearConceptosRetroactivos.indexOf(listConceptosRetroactivos.get(index));
                    crearConceptosRetroactivos.remove(crearIndex);
                } else {
                    borrarConceptosRetroactivos.add(listConceptosRetroactivos.get(index));
                }
                listConceptosRetroactivos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoConceptosRetroactivos ");
                if (!modificarConceptosRetroactivos.isEmpty() && modificarConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                    int modIndex = modificarConceptosRetroactivos.indexOf(filtrarConceptosRetroactivos.get(index));
                    modificarConceptosRetroactivos.remove(modIndex);
                    borrarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                } else if (!crearConceptosRetroactivos.isEmpty() && crearConceptosRetroactivos.contains(filtrarConceptosRetroactivos.get(index))) {
                    int crearIndex = crearConceptosRetroactivos.indexOf(filtrarConceptosRetroactivos.get(index));
                    crearConceptosRetroactivos.remove(crearIndex);
                } else {
                    borrarConceptosRetroactivos.add(filtrarConceptosRetroactivos.get(index));
                }
                int VCIndex = listConceptosRetroactivos.indexOf(filtrarConceptosRetroactivos.get(index));
                listConceptosRetroactivos.remove(VCIndex);

                filtrarConceptosRetroactivos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (listConceptosRetroactivos == null || listConceptosRetroactivos.isEmpty()) {
                infoRegistro = "Cantidad de registros: 0 ";
            } else {
                infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
            }
            context.update("form:informacionRegistro");
            context.update("form:datosConceptosRetroactivos");
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
        if (valorCambio.equals("CONCEPTOS")) {
            if (tipoNuevo == 1) {
                nuevoYduplicarCompletarConcepto = nuevoConceptosRetroactivos.getConcepto().getDescripcion();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarConcepto = duplicarConceptosRetroactivos.getConcepto().getDescripcion();
            }

            System.out.println("CONCEPTOS : " + nuevoYduplicarCompletarConcepto);
        } else if (valorCambio.equals("CONCEPTOSRETRO")) {
            if (tipoNuevo == 1) {
                nuevoYduplicarCompletarConceptoRetro = nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarConceptoRetro = duplicarConceptosRetroactivos.getConceptoRetroActivo().getDescripcion();
            }
            System.out.println("CONCEPTOSRETRO : " + nuevoYduplicarCompletarConceptoRetro);
        }

    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println(" nueva ConceptoRetro    Entro al if 'Centro costo'");
            System.out.println("NUEVO CONCEPTOS :-------> " + nuevoConceptosRetroactivos.getConcepto().getDescripcion());

            if (!nuevoConceptosRetroactivos.getConcepto().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarConcepto);
                nuevoConceptosRetroactivos.getConcepto().setDescripcion(nuevoYduplicarCompletarConcepto);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoConceptosRetroactivos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                    System.err.println("CONCEPTOS GUARDADA :-----> " + nuevoConceptosRetroactivos.getConcepto().getDescripcion());
                } else {
                    context.update("form:conceptosDialogo");
                    context.execute("conceptosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoConceptosRetroactivos.getConcepto().setDescripcion(nuevoYduplicarCompletarConcepto);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoConceptosRetroactivos.setConcepto(new Conceptos());
                nuevoConceptosRetroactivos.getConcepto().setDescripcion(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoConceptosRetroactivos.getConcepto().getDescripcion());
            }
            context.update("formularioDialogos:nuevoPersona");
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTOSRETRO")) {
            System.out.println(" nueva ConceptoRetro    Entro al if 'Centro costo'");
            System.out.println("NUEVO CONCEPTOS :-------> " + nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion());

            if (!nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarConceptoRetro);
                nuevoConceptosRetroactivos.getConceptoRetroActivo().setDescripcion(nuevoYduplicarCompletarConceptoRetro);
                for (int i = 0; i < listaConceptosRetro.size(); i++) {
                    if (listaConceptosRetro.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoConceptosRetroactivos.setConceptoRetroActivo(listaConceptosRetro.get(indiceUnicoElemento));
                    listaConceptosRetro = null;
                    System.err.println("CONCEPTOSRETRO GUARDADA :-----> " + nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion());
                } else {
                    context.update("form:conceptosRetroDialogo");
                    context.execute("conceptosRetroDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoConceptosRetroactivos.getConceptoRetroActivo().setDescripcion(nuevoYduplicarCompletarConceptoRetro);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
                nuevoConceptosRetroactivos.getConceptoRetroActivo().setDescripcion(" ");
                System.out.println("NUEVO CONCEPTOSRETRO " + nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion());
            }
            context.update("formularioDialogos:nuevoCargo");
        }

    }

    public void asignarVariableConceptos(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:conceptosDialogo");
        context.execute("conceptosDialogo.show()");
    }

    public void asignarVariableConceptosRetro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:conceptosRetroDialogo");
        context.execute("conceptosRetroDialogo.show()");
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarConcepto);

            if (!duplicarConceptosRetroactivos.getConcepto().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarConcepto);
                duplicarConceptosRetroactivos.getConcepto().setDescripcion(nuevoYduplicarCompletarConcepto);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarConceptosRetroactivos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                } else {
                    context.update("form:conceptosDialogo");
                    context.execute("conceptosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarConceptosRetroactivos.getEmpresa().setDescripcion(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarConceptosRetroactivos.setConcepto(new Conceptos());
                    duplicarConceptosRetroactivos.getConcepto().setDescripcion(" ");

                    System.out.println("DUPLICAR CONCEPTOS  : " + duplicarConceptosRetroactivos.getConcepto().getDescripcion());
                    System.out.println("nuevoYduplicarCompletarCONCEPTOS : " + nuevoYduplicarCompletarConcepto);
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarConcepto);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listConceptosRetroactivos.get(index).getConcepto().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarConceptosRetroactivos.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarConcepto);
                    }

                }

            }
            context.update("formularioDialogos:duplicarPersona");
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTOSRETRO")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarConceptoRetro);

            if (!duplicarConceptosRetroactivos.getConceptoRetroActivo().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarConceptoRetro);
                duplicarConceptosRetroactivos.getConceptoRetroActivo().setDescripcion(nuevoYduplicarCompletarConceptoRetro);
                for (int i = 0; i < listaConceptosRetro.size(); i++) {
                    if (listaConceptosRetro.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarConceptosRetroactivos.setConceptoRetroActivo(listaConceptosRetro.get(indiceUnicoElemento));
                    listaConceptosRetro = null;
                    getListaConceptosRetro();
                } else {
                    context.update("form:conceptosRetroDialogo");
                    context.execute("conceptosRetroDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarConceptosRetroactivos.getEmpresa().setDescripcion(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
                    duplicarConceptosRetroactivos.getConceptoRetroActivo().setDescripcion(" ");

                    System.out.println("DUPLICAR CONCEPTOSRETRO  : " + duplicarConceptosRetroactivos.getConceptoRetroActivo().getDescripcion());
                    System.out.println("nuevoYduplicarCompletarCONCEPTOSRETRO : " + nuevoYduplicarCompletarConceptoRetro);
                    if (tipoLista == 0) {
                        listConceptosRetroactivos.get(index).getConceptoRetroActivo().setDescripcion(nuevoYduplicarCompletarConceptoRetro);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listConceptosRetroactivos.get(index).getConceptoRetroActivo().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarConceptosRetroactivos.get(index).getConceptoRetroActivo().setDescripcion(nuevoYduplicarCompletarConceptoRetro);
                    }

                }

            }
            context.update("formularioDialogos:duplicarCargo");
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarConceptosRetroactivos.isEmpty() || !crearConceptosRetroactivos.isEmpty() || !modificarConceptosRetroactivos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarConceptosRetroactivos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarConceptosRetroactivos");
            if (!borrarConceptosRetroactivos.isEmpty()) {
                administrarConceptosRetroactivos.borrarConceptosRetroactivos(borrarConceptosRetroactivos);
                //mostrarBorrados
                registrosBorrados = borrarConceptosRetroactivos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarConceptosRetroactivos.clear();
            }
            if (!modificarConceptosRetroactivos.isEmpty()) {
                administrarConceptosRetroactivos.modificarConceptosRetroactivos(modificarConceptosRetroactivos);
                modificarConceptosRetroactivos.clear();
            }
            if (!crearConceptosRetroactivos.isEmpty()) {
                administrarConceptosRetroactivos.crearConceptosRetroactivos(crearConceptosRetroactivos);
                crearConceptosRetroactivos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listConceptosRetroactivos = null;
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
                editarConceptosRetroactivos = listConceptosRetroactivos.get(index);
            }
            if (tipoLista == 1) {
                editarConceptosRetroactivos = filtrarConceptosRetroactivos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editConceptos");
                context.execute("editConceptos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editConceptosRetro");
                context.execute("editConceptosRetro.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoConceptosRetroactivos() {
        System.out.println("agregarNuevoConceptosRetroactivos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoConceptosRetroactivos.getConcepto().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//3

        }

        if (nuevoConceptosRetroactivos.getConceptoRetroActivo().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Concepto Retro \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//4

        }

        System.out.println("contador " + contador);

        if (contador == 4) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                bandera = 0;
                filtrarConceptosRetroactivos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoConceptosRetroactivos.setSecuencia(l);

            crearConceptosRetroactivos.add(nuevoConceptosRetroactivos);

            listConceptosRetroactivos.add(nuevoConceptosRetroactivos);
            nuevoConceptosRetroactivos = new ConceptosRetroactivos();
            nuevoConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
            nuevoConceptosRetroactivos.setConcepto(new Conceptos());
            context.update("form:datosConceptosRetroactivos");
            infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
            context.update("form:informacionRegistro");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroConceptosRetroactivos.hide()");
            context.update("nuevoRegistroConceptosRetroactivos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoConceptosRetroactivos() {
        System.out.println("limpiarNuevoConceptosRetroactivos");
        nuevoConceptosRetroactivos = new ConceptosRetroactivos();
        nuevoConceptosRetroactivos.setConcepto(new Conceptos());
        nuevoConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void cargarNuevoConceptosRetroactivos() {
        System.out.println("cargarNuevoConceptosRetroactivos");

        duplicarConceptosRetroactivos = new ConceptosRetroactivos();
        duplicarConceptosRetroactivos.setConcepto(new Conceptos());
        duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("nuevoRegistroConceptosRetroactivos.show()");
        context.update("formularioDialogos:nuevaTipoempresa");

    }

    public void duplicandoConceptosRetroactivos() {
        System.out.println("duplicandoConceptosRetroactivos");
        if (index >= 0) {
            duplicarConceptosRetroactivos = new ConceptosRetroactivos();
            duplicarConceptosRetroactivos.setConcepto(new Conceptos());
            duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarConceptosRetroactivos.setSecuencia(l);
                duplicarConceptosRetroactivos.setConcepto(listConceptosRetroactivos.get(index).getConcepto());
                duplicarConceptosRetroactivos.setConceptoRetroActivo(listConceptosRetroactivos.get(index).getConceptoRetroActivo());
            }
            if (tipoLista == 1) {
                duplicarConceptosRetroactivos.setSecuencia(l);
                duplicarConceptosRetroactivos.setConcepto(filtrarConceptosRetroactivos.get(index).getConcepto());
                duplicarConceptosRetroactivos.setConceptoRetroActivo(filtrarConceptosRetroactivos.get(index).getConceptoRetroActivo());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroConceptosRetroactivos.show()");
            index = -1;
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

        if (duplicarConceptosRetroactivos.getConcepto().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   *Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarConceptosRetroactivos.getConceptoRetroActivo().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   *Conceptro Retro \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 4) {

            if (crearConceptosRetroactivos.contains(duplicarConceptosRetroactivos)) {
                System.out.println("Ya lo contengo.");
            }
            listConceptosRetroactivos.add(duplicarConceptosRetroactivos);
            crearConceptosRetroactivos.add(duplicarConceptosRetroactivos);
            context.update("form:datosConceptosRetroactivos");
            index = -1;
            System.out.println("--------------DUPLICAR------------------------");

            System.out.println("CONCEPTOS : " + duplicarConceptosRetroactivos.getConcepto().getDescripcion());
            System.out.println("CONCEPTOSRETRO : " + duplicarConceptosRetroactivos.getConceptoRetroActivo().getDescripcion());
            System.out.println("--------------DUPLICAR------------------------");

            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) c.getViewRoot().findComponent("form:datosConceptosRetroactivos:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptosRetroactivos");
                bandera = 0;
                filtrarConceptosRetroactivos = null;
                tipoLista = 0;
            }
            duplicarConceptosRetroactivos = new ConceptosRetroactivos();
            duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
            duplicarConceptosRetroactivos.setConcepto(new Conceptos());

            RequestContext.getCurrentInstance().execute("duplicarRegistroConceptosRetroactivos.hide()");
            RequestContext.getCurrentInstance().update("duplicarRegistroConceptosRetroactivos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarConceptosRetroactivos() {
        duplicarConceptosRetroactivos = new ConceptosRetroactivos();
        duplicarConceptosRetroactivos.setConcepto(new Conceptos());
        duplicarConceptosRetroactivos.setConceptoRetroActivo(new Conceptos());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosRetroactivosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CONCEPTOSRETROACTIVOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosRetroactivosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CONCEPTOSRETROACTIVOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listConceptosRetroactivos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CONCEPTOSRETROACTIVOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOSRETROACTIVOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ConceptosRetroactivos> getListConceptosRetroactivos() {
        if (listConceptosRetroactivos == null) {
            listConceptosRetroactivos = administrarConceptosRetroactivos.consultarConceptosRetroactivos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptosRetroactivos == null || listConceptosRetroactivos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptosRetroactivos.size();
        }
        context.update("form:informacionRegistro");
        return listConceptosRetroactivos;
    }

    public void setListConceptosRetroactivos(List<ConceptosRetroactivos> listConceptosRetroactivos) {
        this.listConceptosRetroactivos = listConceptosRetroactivos;
    }

    public List<ConceptosRetroactivos> getFiltrarConceptosRetroactivos() {
        return filtrarConceptosRetroactivos;
    }

    public void setFiltrarConceptosRetroactivos(List<ConceptosRetroactivos> filtrarConceptosRetroactivos) {
        this.filtrarConceptosRetroactivos = filtrarConceptosRetroactivos;
    }

    public ConceptosRetroactivos getNuevoConceptosRetroactivos() {
        return nuevoConceptosRetroactivos;
    }

    public void setNuevoConceptosRetroactivos(ConceptosRetroactivos nuevoConceptosRetroactivos) {
        this.nuevoConceptosRetroactivos = nuevoConceptosRetroactivos;
    }

    public ConceptosRetroactivos getDuplicarConceptosRetroactivos() {
        return duplicarConceptosRetroactivos;
    }

    public void setDuplicarConceptosRetroactivos(ConceptosRetroactivos duplicarConceptosRetroactivos) {
        this.duplicarConceptosRetroactivos = duplicarConceptosRetroactivos;
    }

    public ConceptosRetroactivos getEditarConceptosRetroactivos() {
        return editarConceptosRetroactivos;
    }

    public void setEditarConceptosRetroactivos(ConceptosRetroactivos editarConceptosRetroactivos) {
        this.editarConceptosRetroactivos = editarConceptosRetroactivos;
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
    private String infoRegistroConceptos;

    public List<Conceptos> getListaConceptos() {
        if (listaConceptos == null) {
            listaConceptos = administrarConceptosRetroactivos.consultarLOVConceptos();
        }

        RequestContext context = RequestContext.getCurrentInstance();
        if (listaConceptos == null || listaConceptos.isEmpty()) {
            infoRegistroConceptos = "Cantidad de registros: 0 ";
        } else {
            infoRegistroConceptos = "Cantidad de registros: " + listaConceptos.size();
        }
        context.update("form:infoRegistroConceptos");
        return listaConceptos;
    }

    public void setListaConceptos(List<Conceptos> listaConceptos) {
        this.listaConceptos = listaConceptos;
    }

    public List<Conceptos> getFiltradoConceptos() {
        return filtradoConceptos;
    }

    public void setFiltradoConceptos(List<Conceptos> filtradoConceptos) {
        this.filtradoConceptos = filtradoConceptos;
    }

    public Conceptos getConceptoSeleccionado() {
        return conceptoSeleccionado;
    }

    public void setConceptoSeleccionado(Conceptos bancoSeleccionado) {
        this.conceptoSeleccionado = bancoSeleccionado;
    }
    private String infoRegistroConceptosRetro;

    public List<Conceptos> getListaConceptosRetro() {
        if (listaConceptosRetro == null) {
            listaConceptosRetro = administrarConceptosRetroactivos.consultarLOVConceptosRetro();
        }

        RequestContext context = RequestContext.getCurrentInstance();
        if (listaConceptosRetro == null || listaConceptosRetro.isEmpty()) {
            infoRegistroConceptosRetro = "Cantidad de registros: 0 ";
        } else {
            infoRegistroConceptosRetro = "Cantidad de registros: " + listaConceptosRetro.size();
        }
        context.update("form:infoRegistroConceptosRetro");
        return listaConceptosRetro;
    }

    public void setListaConceptosRetro(List<Conceptos> listaConceptosRetro) {
        this.listaConceptosRetro = listaConceptosRetro;
    }

    public List<Conceptos> getFiltradoConceptosRetro() {
        return filtradoConceptosRetro;
    }

    public void setFiltradoConceptosRetro(List<Conceptos> filtradoConceptosRetro) {
        this.filtradoConceptosRetro = filtradoConceptosRetro;
    }

    public Conceptos getConceptoRetroActivoSeleccionado() {
        return conceptoRetroSeleccionado;
    }

    public void setConceptoRetroActivoSeleccionado(Conceptos cargoSeleccionado) {
        this.conceptoRetroSeleccionado = cargoSeleccionado;
    }

    public ConceptosRetroactivos getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(ConceptosRetroactivos sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroConceptos() {
        return infoRegistroConceptos;
    }

    public void setInfoRegistroConceptos(String infoRegistroConceptos) {
        this.infoRegistroConceptos = infoRegistroConceptos;
    }

    public String getInfoRegistroConceptosRetro() {
        return infoRegistroConceptosRetro;
    }

    public void setInfoRegistroConceptosRetro(String infoRegistroConceptosRetro) {
        this.infoRegistroConceptosRetro = infoRegistroConceptosRetro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ConceptosProyecciones;
import Entidades.Conceptos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarConceptosProyeccionesInterface;
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
public class ControlConceptosProyecciones implements Serializable {

    @EJB
    AdministrarConceptosProyeccionesInterface administrarConceptosProyecciones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<ConceptosProyecciones> listConceptosProyecciones;
    private List<ConceptosProyecciones> filtrarConceptosProyecciones;
    private List<ConceptosProyecciones> crearConceptosProyecciones;
    private List<ConceptosProyecciones> modificarConceptosProyecciones;
    private List<ConceptosProyecciones> borrarConceptosProyecciones;
    private List<ConceptosProyecciones> departamentoSeleccionado;
    private ConceptosProyecciones nuevoConceptosProyecciones;
    private ConceptosProyecciones duplicarConceptosProyecciones;
    private ConceptosProyecciones editarConceptosProyecciones;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;
    private Short backupPorcentajeProyeccion;
    //-------------------------------
    private String concepto;
    private List<Conceptos> listaConceptos;
    private List<Conceptos> filtradoConceptos;
    private Conceptos conceptoSeleccionado;
    private String nuevoYduplicarCompletarPais;
    private String infoRegistro;

    public ControlConceptosProyecciones() {
        listConceptosProyecciones = null;
        crearConceptosProyecciones = new ArrayList<ConceptosProyecciones>();
        modificarConceptosProyecciones = new ArrayList<ConceptosProyecciones>();
        borrarConceptosProyecciones = new ArrayList<ConceptosProyecciones>();
        permitirIndex = true;
        editarConceptosProyecciones = new ConceptosProyecciones();
        nuevoConceptosProyecciones = new ConceptosProyecciones();
        nuevoConceptosProyecciones.setConcepto(new Conceptos());
        duplicarConceptosProyecciones = new ConceptosProyecciones();
        duplicarConceptosProyecciones.setConcepto(new Conceptos());
        listaConceptos = null;
        filtradoConceptos = null;
        guardado = true;
        tamano = 270;
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarConceptosProyecciones.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlConceptosProyecciones.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarConceptosProyecciones.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlConceptosProyecciones eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listConceptosProyecciones.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupPorcentajeProyeccion = listConceptosProyecciones.get(index).getPorcentajeproyeccion();
            } else if (tipoLista == 1) {
                backupPorcentajeProyeccion = filtrarConceptosProyecciones.get(index).getPorcentajeproyeccion();
            }
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    concepto = listConceptosProyecciones.get(index).getConcepto().getDescripcion();
                } else {
                    concepto = filtrarConceptosProyecciones.get(index).getConcepto().getDescripcion();
                }
            }
            System.out.println("Concepto : " + concepto);
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlConceptosProyecciones.asignarIndex \n");
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
        } catch (Exception e) {
            System.out.println("ERROR ControlConceptosProyecciones.asignarIndex ERROR======" + e.getMessage());
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
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
            bandera = 0;
            filtrarConceptosProyecciones = null;
            tipoLista = 0;
        }

        borrarConceptosProyecciones.clear();
        crearConceptosProyecciones.clear();
        modificarConceptosProyecciones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptosProyecciones = null;
        guardado = true;
        permitirIndex = true;
        getListConceptosProyecciones();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptosProyecciones == null || listConceptosProyecciones.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptosProyecciones.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosConceptosProyecciones");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
            bandera = 0;
            filtrarConceptosProyecciones = null;
            tipoLista = 0;
        }

        borrarConceptosProyecciones.clear();
        crearConceptosProyecciones.clear();
        modificarConceptosProyecciones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptosProyecciones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosConceptosProyecciones");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
            codigo.setFilterStyle("width: 30px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
            descripcion.setFilterStyle("width: 340px");
            RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
            bandera = 0;
            filtrarConceptosProyecciones = null;
            tipoLista = 0;
        }
    }

    public void actualizarConcepto() {
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Concepto seleccionado : " + conceptoSeleccionado.getDescripcion());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            for (int i = 0; i < listConceptosProyecciones.size(); i++) {
                if (listConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(conceptoSeleccionado.getDescripcion())) {
                    duplicados++;
                }
            }
            if (duplicados == 0) {
                if (tipoLista == 0) {
                    listConceptosProyecciones.get(index).setConcepto(conceptoSeleccionado);
                    if (!crearConceptosProyecciones.contains(listConceptosProyecciones.get(index))) {
                        if (modificarConceptosProyecciones.isEmpty()) {
                            modificarConceptosProyecciones.add(listConceptosProyecciones.get(index));
                        } else if (!modificarConceptosProyecciones.contains(listConceptosProyecciones.get(index))) {
                            modificarConceptosProyecciones.add(listConceptosProyecciones.get(index));
                        }
                    }
                } else {
                    mensajeValidacion = "CONCEPTO YA ELEGIDO";
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                }

            } else {
                for (int i = 0; i < listConceptosProyecciones.size(); i++) {
                    if (listConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(conceptoSeleccionado.getDescripcion())) {
                        duplicados++;
                    }
                }
                if (duplicados == 0) {
                    filtrarConceptosProyecciones.get(index).setConcepto(conceptoSeleccionado);

                    if (!crearConceptosProyecciones.contains(filtrarConceptosProyecciones.get(index))) {
                        if (modificarConceptosProyecciones.isEmpty()) {
                            modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(index));
                        } else if (!modificarConceptosProyecciones.contains(filtrarConceptosProyecciones.get(index))) {
                            modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(index));
                        }
                    }
                } else {
                    mensajeValidacion = "CONCEPTO YA ELEGIDO";
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                }

            }
            if (duplicados == 0) {
                if (guardado == true) {
                    guardado = false;
                }
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR CONCEPTO CONCEPTO SELECCIONADO : " + conceptoSeleccionado.getDescripcion());
            context.update("form:datosConceptosProyecciones");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR CONCEPTO NUEVO DEPARTAMENTO: " + conceptoSeleccionado.getDescripcion());
            nuevoConceptosProyecciones.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:nuevoPais");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR CONCEPTO DUPLICAR DEPARTAMENO: " + conceptoSeleccionado.getDescripcion());
            duplicarConceptosProyecciones.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:duplicarPais");
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
    }

    public void cancelarCambioConcepto() {
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarConceptosProyecciones(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR CONCEPTO PROYECCIÓN");
        index = indice;
        int coincidencias = 0;
        int contador = 0, duplicados = 0;
        boolean banderita = false;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR CONCEPTOS PROYECCIONES, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearConceptosProyecciones.contains(listConceptosProyecciones.get(indice))) {

                    System.out.println("backupPorcentajeProyeccion : " + backupPorcentajeProyeccion);

                    if (listConceptosProyecciones.get(indice).getPorcentajeproyeccion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listConceptosProyecciones.get(indice).setPorcentajeproyeccion(backupPorcentajeProyeccion);

                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarConceptosProyecciones.isEmpty()) {
                            modificarConceptosProyecciones.add(listConceptosProyecciones.get(indice));
                        } else if (!modificarConceptosProyecciones.contains(listConceptosProyecciones.get(indice))) {
                            modificarConceptosProyecciones.add(listConceptosProyecciones.get(indice));
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
                    context.update("form:datosConceptosProyecciones");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupPorcentajeProyeccion : " + backupPorcentajeProyeccion);

                    if (listConceptosProyecciones.get(indice).getPorcentajeproyeccion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listConceptosProyecciones.get(indice).setPorcentajeproyeccion(backupPorcentajeProyeccion);
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (guardado == true) {
                            guardado = false;
                        }
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosConceptosProyecciones");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearConceptosProyecciones.contains(filtrarConceptosProyecciones.get(indice))) {
                    if (filtrarConceptosProyecciones.get(indice).getPorcentajeproyeccion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarConceptosProyecciones.get(indice).setPorcentajeproyeccion(backupPorcentajeProyeccion);
                    } else {
                        banderita = true;
                    }
                    if (banderita == true) {

                        if (modificarConceptosProyecciones.isEmpty()) {
                            modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(indice));
                        } else if (!modificarConceptosProyecciones.contains(filtrarConceptosProyecciones.get(indice))) {
                            modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(indice));
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
                    if (filtrarConceptosProyecciones.get(indice).getPorcentajeproyeccion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarConceptosProyecciones.get(indice).setPorcentajeproyeccion(backupPorcentajeProyeccion);
                    } else {
                        banderita = true;
                    }
                    if (banderita == true) {
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
            context.update("form:datosConceptosProyecciones");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listConceptosProyecciones.get(indice).getConcepto().getDescripcion());
            if (!listConceptosProyecciones.get(indice).getConcepto().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listConceptosProyecciones.get(indice).getConcepto().setDescripcion(concepto);
                } else {
                    listConceptosProyecciones.get(indice).getConcepto().setDescripcion(concepto);
                }

                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        for (int i = 0; i < listConceptosProyecciones.size(); i++) {
                            if (listConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(conceptoSeleccionado.getDescripcion())) {
                                duplicados++;
                            }
                        }
                        if (duplicados == 0) {
                            listConceptosProyecciones.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                        }
                    } else {
                        for (int i = 0; i < filtrarConceptosProyecciones.size(); i++) {
                            if (filtrarConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(conceptoSeleccionado.getDescripcion())) {
                                duplicados++;
                            }
                        }
                        if (duplicados == 0) {
                            filtrarConceptosProyecciones.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                        }
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
                if (concepto != null) {
                    if (tipoLista == 0) {
                        listConceptosProyecciones.get(index).getConcepto().setDescripcion(concepto);
                    } else {
                        filtrarConceptosProyecciones.get(index).getConcepto().setDescripcion(concepto);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO : " + concepto);
                context.update("form:conceptosDialogo");
                context.execute("conceptosDialogo.show()");
            }
            if (duplicados == 0) {
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        if (!crearConceptosProyecciones.contains(listConceptosProyecciones.get(indice))) {

                            if (modificarConceptosProyecciones.isEmpty()) {
                                modificarConceptosProyecciones.add(listConceptosProyecciones.get(indice));
                            } else if (!modificarConceptosProyecciones.contains(listConceptosProyecciones.get(indice))) {
                                modificarConceptosProyecciones.add(listConceptosProyecciones.get(indice));
                            }
                            if (guardado == true) {
                                guardado = false;
                            }
                        }
                        index = -1;
                        secRegistro = null;
                    } else {
                        if (!crearConceptosProyecciones.contains(filtrarConceptosProyecciones.get(indice))) {

                            if (modificarConceptosProyecciones.isEmpty()) {
                                modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(indice));
                            } else if (!modificarConceptosProyecciones.contains(filtrarConceptosProyecciones.get(indice))) {
                                modificarConceptosProyecciones.add(filtrarConceptosProyecciones.get(indice));
                            }
                            if (guardado == true) {
                                guardado = false;
                            }
                        }
                        index = -1;
                        secRegistro = null;
                    }
                }

                context.update("form:datosConceptosProyecciones");
                context.update("form:ACEPTAR");

            } else {
                mensajeValidacion = "CONCEPTO YA ELEGIDO";
                context.update("form:validacionModificar");
                context.execute("validacionModificar.show()");
            }
        }

    }

    public void borrandoConceptosProyecciones() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoConceptosProyecciones");
                if (!modificarConceptosProyecciones.isEmpty() && modificarConceptosProyecciones.contains(listConceptosProyecciones.get(index))) {
                    int modIndex = modificarConceptosProyecciones.indexOf(listConceptosProyecciones.get(index));
                    modificarConceptosProyecciones.remove(modIndex);
                    borrarConceptosProyecciones.add(listConceptosProyecciones.get(index));
                } else if (!crearConceptosProyecciones.isEmpty() && crearConceptosProyecciones.contains(listConceptosProyecciones.get(index))) {
                    int crearIndex = crearConceptosProyecciones.indexOf(listConceptosProyecciones.get(index));
                    crearConceptosProyecciones.remove(crearIndex);
                } else {
                    borrarConceptosProyecciones.add(listConceptosProyecciones.get(index));
                }
                listConceptosProyecciones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoConceptosProyecciones ");
                if (!modificarConceptosProyecciones.isEmpty() && modificarConceptosProyecciones.contains(filtrarConceptosProyecciones.get(index))) {
                    int modIndex = modificarConceptosProyecciones.indexOf(filtrarConceptosProyecciones.get(index));
                    modificarConceptosProyecciones.remove(modIndex);
                    borrarConceptosProyecciones.add(filtrarConceptosProyecciones.get(index));
                } else if (!crearConceptosProyecciones.isEmpty() && crearConceptosProyecciones.contains(filtrarConceptosProyecciones.get(index))) {
                    int crearIndex = crearConceptosProyecciones.indexOf(filtrarConceptosProyecciones.get(index));
                    crearConceptosProyecciones.remove(crearIndex);
                } else {
                    borrarConceptosProyecciones.add(filtrarConceptosProyecciones.get(index));
                }
                int VCIndex = listConceptosProyecciones.indexOf(filtrarConceptosProyecciones.get(index));
                listConceptosProyecciones.remove(VCIndex);
                filtrarConceptosProyecciones.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConceptosProyecciones");
            infoRegistro = "Cantidad de registros: " + listConceptosProyecciones.size();
            context.update("form:informacionRegistro");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void valoresBackupAutocompletar(int tipoNuevo) {
        System.out.println("1...");
        if (tipoNuevo == 1) {
            nuevoYduplicarCompletarPais = nuevoConceptosProyecciones.getConcepto().getDescripcion();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarPais = duplicarConceptosProyecciones.getConcepto().getDescripcion();
        }

    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println(" nueva Ciudad    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoConceptosProyecciones.getConcepto().getDescripcion());

            if (!nuevoConceptosProyecciones.getConcepto().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCiudadCompletar: " + nuevoYduplicarCompletarPais);
                nuevoConceptosProyecciones.getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                getListaConceptos();
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoConceptosProyecciones.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                    getListaConceptos();
                    System.err.println("NORMA LABORAL GUARDADA " + nuevoConceptosProyecciones.getConcepto().getDescripcion());
                } else {
                    context.update("form:conceptosDialogo");
                    context.execute("conceptosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoConceptosProyecciones.getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoConceptosProyecciones.setConcepto(new Conceptos());
                nuevoConceptosProyecciones.getConcepto().setDescripcion(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoConceptosProyecciones.getConcepto().getDescripcion());
            }
            context.update("formularioDialogos:nuevoPais");
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

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONCEPTOS")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarPais);

            if (!duplicarConceptosProyecciones.getConcepto().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarPais);
                duplicarConceptosProyecciones.getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarConceptosProyecciones.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                    getListaConceptos();
                } else {
                    context.update("form:conceptosDialogo");
                    context.execute("conceptosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarConceptosProyecciones.getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarConceptosProyecciones.setConcepto(new Conceptos());
                    duplicarConceptosProyecciones.getConcepto().setDescripcion(" ");

                    System.out.println("DUPLICAR Valor NORMA LABORAL : " + duplicarConceptosProyecciones.getConcepto().getDescripcion());
                    System.out.println("nuevoYduplicarCompletarPais : " + nuevoYduplicarCompletarPais);
                    if (tipoLista == 0) {
                        listConceptosProyecciones.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listConceptosProyecciones.get(index).getConcepto().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarConceptosProyecciones.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarPais);
                    }

                }

            }
            context.update("formularioDialogos:duplicarPais");
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarConceptosProyecciones.isEmpty() || !crearConceptosProyecciones.isEmpty() || !modificarConceptosProyecciones.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarConceptosProyecciones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarConceptosProyecciones");
            if (!borrarConceptosProyecciones.isEmpty()) {
                administrarConceptosProyecciones.borrarConceptosProyecciones(borrarConceptosProyecciones);
                //mostrarBorrados
                registrosBorrados = borrarConceptosProyecciones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarConceptosProyecciones.clear();
            }
            if (!modificarConceptosProyecciones.isEmpty()) {
                administrarConceptosProyecciones.modificarConceptosProyecciones(modificarConceptosProyecciones);
                modificarConceptosProyecciones.clear();
            }
            if (!crearConceptosProyecciones.isEmpty()) {
                administrarConceptosProyecciones.crearConceptosProyecciones(crearConceptosProyecciones);
                crearConceptosProyecciones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listConceptosProyecciones = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosConceptosProyecciones");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarConceptosProyecciones = listConceptosProyecciones.get(index);
            }
            if (tipoLista == 1) {
                editarConceptosProyecciones = filtrarConceptosProyecciones.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editPais");
                context.execute("editPais.show()");
                cualCelda = -1;

            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoConceptosProyecciones() {
        System.out.println("agregarNuevoConceptosProyecciones");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoConceptosProyecciones.getPorcentajeproyeccion() == a) {
            mensajeValidacion = " *Porcentaje \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;
        }
        if (nuevoConceptosProyecciones.getConcepto().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int i = 0; i < listConceptosProyecciones.size(); i++) {
                if (listConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(nuevoConceptosProyecciones.getConcepto().getDescripcion())) {
                    duplicados++;
                }
            }
            if (duplicados == 0) {
                System.out.println("bandera");
                contador++;
            } else {
                mensajeValidacion += "Concepto Elegido";
            }

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
                bandera = 0;
                filtrarConceptosProyecciones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoConceptosProyecciones.setSecuencia(l);

            crearConceptosProyecciones.add(nuevoConceptosProyecciones);

            listConceptosProyecciones.add(nuevoConceptosProyecciones);
            nuevoConceptosProyecciones = new ConceptosProyecciones();
            nuevoConceptosProyecciones.setConcepto(new Conceptos());
            context.update("form:datosConceptosProyecciones");
            infoRegistro = "Cantidad de registros: " + listConceptosProyecciones.size();
            context.update("form:informacionRegistro");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroConceptosProyecciones.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoConceptosProyecciones() {
        System.out.println("limpiarNuevoConceptosProyecciones");
        nuevoConceptosProyecciones = new ConceptosProyecciones();
        nuevoConceptosProyecciones.setConcepto(new Conceptos());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoConceptosProyecciones() {
        System.out.println("duplicandoConceptosProyecciones");
        if (index >= 0) {
            duplicarConceptosProyecciones = new ConceptosProyecciones();
            duplicarConceptosProyecciones.setConcepto(new Conceptos());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarConceptosProyecciones.setSecuencia(l);
                duplicarConceptosProyecciones.setPorcentajeproyeccion(listConceptosProyecciones.get(index).getPorcentajeproyeccion());
                duplicarConceptosProyecciones.getConcepto().setDescripcion(listConceptosProyecciones.get(index).getConcepto().getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarConceptosProyecciones.setSecuencia(l);
                duplicarConceptosProyecciones.setPorcentajeproyeccion(filtrarConceptosProyecciones.get(index).getPorcentajeproyeccion());
                duplicarConceptosProyecciones.getConcepto().setDescripcion(filtrarConceptosProyecciones.get(index).getConcepto().getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroConceptosProyecciones.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONCEPTOSPROYECCIONES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar PorcentajeProyeccion " + duplicarConceptosProyecciones.getPorcentajeproyeccion());

        if (duplicarConceptosProyecciones.getPorcentajeproyeccion() == a) {
            mensajeValidacion = mensajeValidacion + "   *Porcentaje \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;
        }

        if (duplicarConceptosProyecciones.getConcepto().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   *Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int i = 0; i < listConceptosProyecciones.size(); i++) {
                if (listConceptosProyecciones.get(i).getConcepto().getDescripcion().equals(duplicarConceptosProyecciones.getConcepto().getDescripcion())) {
                    duplicados++;
                }
            }
            if (duplicados == 0) {
                System.out.println("bandera");
                contador++;
            } else {
                mensajeValidacion += "Concepto Elegido";
            }

        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarConceptosProyecciones.getSecuencia() + "  " + duplicarConceptosProyecciones.getPorcentajeproyeccion());
            if (crearConceptosProyecciones.contains(duplicarConceptosProyecciones)) {
                System.out.println("Ya lo contengo.");
            }
            listConceptosProyecciones.add(duplicarConceptosProyecciones);
            crearConceptosProyecciones.add(duplicarConceptosProyecciones);
            context.update("form:datosConceptosProyecciones");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            infoRegistro = "Cantidad de registros: " + listConceptosProyecciones.size();
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosConceptosProyecciones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptosProyecciones");
                bandera = 0;
                filtrarConceptosProyecciones = null;
                tipoLista = 0;
            }
            duplicarConceptosProyecciones = new ConceptosProyecciones();
            duplicarConceptosProyecciones.setConcepto(new Conceptos());

            RequestContext.getCurrentInstance().execute("duplicarRegistroConceptosProyecciones.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarConceptosProyecciones() {
        duplicarConceptosProyecciones = new ConceptosProyecciones();
        duplicarConceptosProyecciones.setConcepto(new Conceptos());
    }

    public void exportPDF() throws IOException {
        System.out.println("MetodoExportar PDF");
        /*//   try {
            /*for (int i = 0; i < listConceptosProyecciones.size(); i++) {
         System.out.println("ExportPDF ");
         System.out.println("Concepto : " + listConceptosProyecciones.get(i).getConcepto().getDescripcion());
         }
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosProyeccionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CONCEPTOSPROYECCIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
        //} catch (IOException e) {
        //  System.out.println("" + e.getStackTrace());
        //}*/
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosProyeccionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CONCEPTOSPROYECCIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listConceptosProyecciones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CONCEPTOSPROYECCIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOSPROYECCIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ConceptosProyecciones> getListConceptosProyecciones() {
        try {
            if (listConceptosProyecciones == null) {
                listConceptosProyecciones = administrarConceptosProyecciones.consultarConceptostosProyecciones();
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (listConceptosProyecciones == null || listConceptosProyecciones.isEmpty()) {
                infoRegistro = "Cantidad de registros: 0 ";
            } else {
                infoRegistro = "Cantidad de registros: " + listConceptosProyecciones.size();
            }
            context.update("form:informacionRegistro");
            return listConceptosProyecciones;
        } catch (Exception e) {
            System.out.println("ControlConceptosProyecciones ERROR : " + e);
            return null;
        }
    }

    public void setListConceptosProyecciones(List<ConceptosProyecciones> listConceptosProyecciones) {
        this.listConceptosProyecciones = listConceptosProyecciones;
    }

    public List<ConceptosProyecciones> getFiltrarConceptosProyecciones() {
        return filtrarConceptosProyecciones;
    }

    public void setFiltrarConceptosProyecciones(List<ConceptosProyecciones> filtrarConceptosProyecciones) {
        this.filtrarConceptosProyecciones = filtrarConceptosProyecciones;
    }

    public ConceptosProyecciones getNuevoConceptosProyecciones() {
        return nuevoConceptosProyecciones;
    }

    public void setNuevoConceptosProyecciones(ConceptosProyecciones nuevoConceptosProyecciones) {
        this.nuevoConceptosProyecciones = nuevoConceptosProyecciones;
    }

    public ConceptosProyecciones getDuplicarConceptosProyecciones() {
        return duplicarConceptosProyecciones;
    }

    public void setDuplicarConceptosProyecciones(ConceptosProyecciones duplicarConceptosProyecciones) {
        this.duplicarConceptosProyecciones = duplicarConceptosProyecciones;
    }

    public ConceptosProyecciones getEditarConceptosProyecciones() {
        return editarConceptosProyecciones;
    }

    public void setEditarConceptosProyecciones(ConceptosProyecciones editarConceptosProyecciones) {
        this.editarConceptosProyecciones = editarConceptosProyecciones;
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
            listaConceptos = administrarConceptosProyecciones.consultarLOVConceptos();
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

    public void setConceptoSeleccionado(Conceptos paisSeleccionado) {
        this.conceptoSeleccionado = paisSeleccionado;
    }

    public List<ConceptosProyecciones> getDepartamentoSeleccionado() {
        return departamentoSeleccionado;
    }

    public void setDepartamentoSeleccionado(List<ConceptosProyecciones> departamentoSeleccionado) {
        this.departamentoSeleccionado = departamentoSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getInfoRegistroConceptos() {
        return infoRegistroConceptos;
    }

    public void setInfoRegistroConceptos(String infoRegistroConceptos) {
        this.infoRegistroConceptos = infoRegistroConceptos;
    }

}

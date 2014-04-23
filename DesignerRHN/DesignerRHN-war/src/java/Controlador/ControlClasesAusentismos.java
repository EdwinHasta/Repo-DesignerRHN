/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Clasesausentismos;
import Entidades.Tiposausentismos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarClasesAusentismosInterface;
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
public class ControlClasesAusentismos implements Serializable {

    @EJB
    AdministrarClasesAusentismosInterface administrarClasesAusentismos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Clasesausentismos> listClasesAusentismos;
    private List<Clasesausentismos> filtrarClasesAusentismos;
    private List<Clasesausentismos> crearClasesAusentismos;
    private List<Clasesausentismos> modificarClasesAusentismos;
    private List<Clasesausentismos> borrarClasesAusentismos;
    private Clasesausentismos nuevoClasesAusentismos;
    private Clasesausentismos duplicarClasesAusentismos;
    private Clasesausentismos editarClasesAusentismos;
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
    private List<Tiposausentismos> listaTiposausentismos;
    private List<Tiposausentismos> filtradoTiposausentismos;
    private Tiposausentismos centrocostoSeleccionado;
    private String nuevoYduplicarCompletarPersona;
    //--------------------------------------
    private String backupBanco;

    public ControlClasesAusentismos() {
        listClasesAusentismos = null;
        crearClasesAusentismos = new ArrayList<Clasesausentismos>();
        modificarClasesAusentismos = new ArrayList<Clasesausentismos>();
        borrarClasesAusentismos = new ArrayList<Clasesausentismos>();
        permitirIndex = true;
        editarClasesAusentismos = new Clasesausentismos();
        nuevoClasesAusentismos = new Clasesausentismos();
        nuevoClasesAusentismos.setTipo(new Tiposausentismos());
        duplicarClasesAusentismos = new Clasesausentismos();
        duplicarClasesAusentismos.setTipo(new Tiposausentismos());
        listaTiposausentismos = null;
        filtradoTiposausentismos = null;
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlClasesAusentismos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlClasesAusentismos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listClasesAusentismos.get(index).getSecuencia();
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backupCodigo = listClasesAusentismos.get(index).getCodigo();
                    System.out.println("CODIGO : " + backupCodigo);
                }
                if (cualCelda == 1) {
                    backupDescripcion = listClasesAusentismos.get(index).getDescripcion();
                    System.out.println("DESCRIPCION : " + backupDescripcion);
                }
                if (cualCelda == 2) {
                    backupBanco = listClasesAusentismos.get(index).getTipo().getDescripcion();
                    System.out.println("BANCO : " + backupBanco);
                }

            } else if (tipoLista == 1) {
                if (cualCelda == 0) {
                    backupCodigo = filtrarClasesAusentismos.get(index).getCodigo();
                }
                if (cualCelda == 1) {
                    backupDescripcion = filtrarClasesAusentismos.get(index).getDescripcion();
                    System.out.println("DESCRIPCION : " + backupDescripcion);
                }
                if (cualCelda == 2) {
                    backupBanco = filtrarClasesAusentismos.get(index).getTipo().getDescripcion();
                    System.out.println("BANCO : " + backupBanco);
                }

            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlClasesAusentismos.asignarIndex \n");
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
            System.out.println("ERROR ControlClasesAusentismos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesAusentismos");
            bandera = 0;
            filtrarClasesAusentismos = null;
            tipoLista = 0;
        }

        borrarClasesAusentismos.clear();
        crearClasesAusentismos.clear();
        modificarClasesAusentismos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listClasesAusentismos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosClasesAusentismos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:codigo");
            codigo.setFilterStyle("width: 20px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:descripcion");
            descripcion.setFilterStyle("width: 130px");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:personafir");
            personafir.setFilterStyle("width: 130px");
            RequestContext.getCurrentInstance().update("form:datosClasesAusentismos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesAusentismos");
            bandera = 0;
            filtrarClasesAusentismos = null;
            tipoLista = 0;
        }
    }

    public void actualizarTiposausentismos() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("centrocosto seleccionado : " + centrocostoSeleccionado.getDescripcion());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listClasesAusentismos.get(index).setTipo(centrocostoSeleccionado);

                if (!crearClasesAusentismos.contains(listClasesAusentismos.get(index))) {
                    if (modificarClasesAusentismos.isEmpty()) {
                        modificarClasesAusentismos.add(listClasesAusentismos.get(index));
                    } else if (!modificarClasesAusentismos.contains(listClasesAusentismos.get(index))) {
                        modificarClasesAusentismos.add(listClasesAusentismos.get(index));
                    }
                }
            } else {
                filtrarClasesAusentismos.get(index).setTipo(centrocostoSeleccionado);

                if (!crearClasesAusentismos.contains(filtrarClasesAusentismos.get(index))) {
                    if (modificarClasesAusentismos.isEmpty()) {
                        modificarClasesAusentismos.add(filtrarClasesAusentismos.get(index));
                    } else if (!modificarClasesAusentismos.contains(filtrarClasesAusentismos.get(index))) {
                        modificarClasesAusentismos.add(filtrarClasesAusentismos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR PAIS PAIS SELECCIONADO : " + centrocostoSeleccionado.getDescripcion());
            context.update("form:datosClasesAusentismos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR PAIS NUEVO DEPARTAMENTO: " + centrocostoSeleccionado.getDescripcion());
            nuevoClasesAusentismos.setTipo(centrocostoSeleccionado);
            context.update("formularioDialogos:nuevoPersona");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR PAIS DUPLICAR DEPARTAMENO: " + centrocostoSeleccionado.getDescripcion());
            duplicarClasesAusentismos.setTipo(centrocostoSeleccionado);
            context.update("formularioDialogos:duplicarPersona");
        }
        filtradoTiposausentismos = null;
        centrocostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("personasDialogo.hide()");
        context.reset("form:lovTiposausentismos:globalFilter");
        context.update("form:lovTiposausentismos");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioTiposausentismos() {
        listClasesAusentismos.get(index).getTipo().setDescripcion(backupBanco);
        filtradoTiposausentismos = null;
        centrocostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarClasesAusentismos(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearClasesAusentismos.contains(listClasesAusentismos.get(indice))) {

                    System.out.println("listClasesAusentismos.get(indice).getCodigo() : " + listClasesAusentismos.get(indice).getCodigo());
                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listClasesAusentismos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesAusentismos.get(indice).setCodigo(backupCodigo);

                    } else {
                        for (int j = 0; j < listClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (listClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listClasesAusentismos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listClasesAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listClasesAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listClasesAusentismos.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarClasesAusentismos.isEmpty()) {
                            modificarClasesAusentismos.add(listClasesAusentismos.get(indice));
                        } else if (!modificarClasesAusentismos.contains(listClasesAusentismos.get(indice))) {
                            modificarClasesAusentismos.add(listClasesAusentismos.get(indice));
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
                    context.update("form:datosClasesAusentismos");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listClasesAusentismos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesAusentismos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (listClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listClasesAusentismos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listClasesAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listClasesAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listClasesAusentismos.get(indice).setDescripcion(backupDescripcion);

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
                    context.update("form:datosClasesAusentismos");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearClasesAusentismos.contains(filtrarClasesAusentismos.get(indice))) {
                    if (filtrarClasesAusentismos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesAusentismos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarClasesAusentismos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarClasesAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarClasesAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarClasesAusentismos.isEmpty()) {
                            modificarClasesAusentismos.add(filtrarClasesAusentismos.get(indice));
                        } else if (!modificarClasesAusentismos.contains(filtrarClasesAusentismos.get(indice))) {
                            modificarClasesAusentismos.add(filtrarClasesAusentismos.get(indice));
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
                    if (filtrarClasesAusentismos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesAusentismos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listClasesAusentismos.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesAusentismos.get(indice).getCodigo() == listClasesAusentismos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarClasesAusentismos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarClasesAusentismos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarClasesAusentismos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarClasesAusentismos.get(indice).setDescripcion(backupDescripcion);
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
            context.update("form:datosClasesAusentismos");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("PERSONAS")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listClasesAusentismos.get(indice).getTipo().getDescripcion());
            if (!listClasesAusentismos.get(indice).getTipo().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listClasesAusentismos.get(indice).getTipo().setDescripcion(backupBanco);
                } else {
                    listClasesAusentismos.get(indice).getTipo().setDescripcion(backupBanco);
                }

                for (int i = 0; i < listaTiposausentismos.size(); i++) {
                    if (listaTiposausentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listClasesAusentismos.get(indice).setTipo(listaTiposausentismos.get(indiceUnicoElemento));
                    } else {
                        filtrarClasesAusentismos.get(indice).setTipo(listaTiposausentismos.get(indiceUnicoElemento));
                    }
                    listaTiposausentismos.clear();
                    listaTiposausentismos = null;
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
                        listClasesAusentismos.get(index).getTipo().setDescripcion(backupBanco);
                    } else {
                        filtrarClasesAusentismos.get(index).getTipo().setDescripcion(backupBanco);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO PERSONA : " + backupBanco);
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearClasesAusentismos.contains(listClasesAusentismos.get(indice))) {

                        if (modificarClasesAusentismos.isEmpty()) {
                            modificarClasesAusentismos.add(listClasesAusentismos.get(indice));
                        } else if (!modificarClasesAusentismos.contains(listClasesAusentismos.get(indice))) {
                            modificarClasesAusentismos.add(listClasesAusentismos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearClasesAusentismos.contains(filtrarClasesAusentismos.get(indice))) {

                        if (modificarClasesAusentismos.isEmpty()) {
                            modificarClasesAusentismos.add(filtrarClasesAusentismos.get(indice));
                        } else if (!modificarClasesAusentismos.contains(filtrarClasesAusentismos.get(indice))) {
                            modificarClasesAusentismos.add(filtrarClasesAusentismos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosClasesAusentismos");
            context.update("form:ACEPTAR");

        }
    }

    public void verificarBorrado() {
        BigInteger contarSoAusentismosClaseAusentismo;
        BigInteger contarCausasAusentismosClaseAusentismo;

        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de ControlTiposFamiliares ");
            if (tipoLista == 0) {
                contarCausasAusentismosClaseAusentismo = administrarClasesAusentismos.contarCausasAusentismosClaseAusentismo(listClasesAusentismos.get(index).getSecuencia());
                contarSoAusentismosClaseAusentismo = administrarClasesAusentismos.contarSoAusentismosClaseAusentismo(listClasesAusentismos.get(index).getSecuencia());
            } else {
                contarCausasAusentismosClaseAusentismo = administrarClasesAusentismos.contarCausasAusentismosClaseAusentismo(filtrarClasesAusentismos.get(index).getSecuencia());
                contarSoAusentismosClaseAusentismo = administrarClasesAusentismos.contarSoAusentismosClaseAusentismo(filtrarClasesAusentismos.get(index).getSecuencia());
            }
            if (contarCausasAusentismosClaseAusentismo.equals(new BigInteger("0")) && contarSoAusentismosClaseAusentismo.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoClasesAusentismos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                contarCausasAusentismosClaseAusentismo = new BigInteger("-1");
                contarSoAusentismosClaseAusentismo = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposFamiliares verificarBorrado ERROR " + e);
        }
    }

    public void borrandoClasesAusentismos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoClasesAusentismos");
                if (!modificarClasesAusentismos.isEmpty() && modificarClasesAusentismos.contains(listClasesAusentismos.get(index))) {
                    int modIndex = modificarClasesAusentismos.indexOf(listClasesAusentismos.get(index));
                    modificarClasesAusentismos.remove(modIndex);
                    borrarClasesAusentismos.add(listClasesAusentismos.get(index));
                } else if (!crearClasesAusentismos.isEmpty() && crearClasesAusentismos.contains(listClasesAusentismos.get(index))) {
                    int crearIndex = crearClasesAusentismos.indexOf(listClasesAusentismos.get(index));
                    crearClasesAusentismos.remove(crearIndex);
                } else {
                    borrarClasesAusentismos.add(listClasesAusentismos.get(index));
                }
                listClasesAusentismos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoClasesAusentismos ");
                if (!modificarClasesAusentismos.isEmpty() && modificarClasesAusentismos.contains(filtrarClasesAusentismos.get(index))) {
                    int modIndex = modificarClasesAusentismos.indexOf(filtrarClasesAusentismos.get(index));
                    modificarClasesAusentismos.remove(modIndex);
                    borrarClasesAusentismos.add(filtrarClasesAusentismos.get(index));
                } else if (!crearClasesAusentismos.isEmpty() && crearClasesAusentismos.contains(filtrarClasesAusentismos.get(index))) {
                    int crearIndex = crearClasesAusentismos.indexOf(filtrarClasesAusentismos.get(index));
                    crearClasesAusentismos.remove(crearIndex);
                } else {
                    borrarClasesAusentismos.add(filtrarClasesAusentismos.get(index));
                }
                int VCIndex = listClasesAusentismos.indexOf(filtrarClasesAusentismos.get(index));
                listClasesAusentismos.remove(VCIndex);
                filtrarClasesAusentismos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosClasesAusentismos");
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
                nuevoYduplicarCompletarPersona = nuevoClasesAusentismos.getTipo().getDescripcion();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarPersona = duplicarClasesAusentismos.getTipo().getDescripcion();
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
            System.out.println("NUEVO PERSONA :-------> " + nuevoClasesAusentismos.getTipo().getDescripcion());

            if (!nuevoClasesAusentismos.getTipo().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarPersona);
                nuevoClasesAusentismos.getTipo().setDescripcion(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaTiposausentismos.size(); i++) {
                    if (listaTiposausentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoClasesAusentismos.setTipo(listaTiposausentismos.get(indiceUnicoElemento));
                    listaTiposausentismos = null;
                    System.err.println("PERSONA GUARDADA :-----> " + nuevoClasesAusentismos.getTipo().getDescripcion());
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoClasesAusentismos.getTipo().setDescripcion(nuevoYduplicarCompletarPersona);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoClasesAusentismos.setTipo(new Tiposausentismos());
                nuevoClasesAusentismos.getTipo().setDescripcion(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoClasesAusentismos.getTipo().getDescripcion());
            }
            context.update("formularioDialogos:nuevoPersona");
        }
    }

    public void asignarVariableTiposausentismos(int tipoNuevo) {
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

            if (!duplicarClasesAusentismos.getTipo().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarPersona);
                duplicarClasesAusentismos.getTipo().setDescripcion(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaTiposausentismos.size(); i++) {
                    if (listaTiposausentismos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarClasesAusentismos.setTipo(listaTiposausentismos.get(indiceUnicoElemento));
                    listaTiposausentismos = null;
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarClasesAusentismos.getEmpresa().setDescripcion(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarClasesAusentismos.setTipo(new Tiposausentismos());
                    duplicarClasesAusentismos.getTipo().setDescripcion(" ");

                    System.out.println("DUPLICAR PERSONA  : " + duplicarClasesAusentismos.getTipo().getDescripcion());
                    System.out.println("nuevoYduplicarCompletarPERSONA : " + nuevoYduplicarCompletarPersona);
                    if (tipoLista == 0) {
                        listClasesAusentismos.get(index).getTipo().setDescripcion(nuevoYduplicarCompletarPersona);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listClasesAusentismos.get(index).getTipo().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarClasesAusentismos.get(index).getTipo().setDescripcion(nuevoYduplicarCompletarPersona);
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
     System.err.println("Control Secuencia de ControlClasesAusentismos ");
     if (tipoLista == 0) {
     contarBienProgramacionesDepartamento = administrarClasesAusentismos.contarBienProgramacionesDepartamento(listClasesAusentismos.get(index).getSecuencia());
     contarCapModulosDepartamento = administrarClasesAusentismos.contarCapModulosDepartamento(listClasesAusentismos.get(index).getSecuencia());
     contarCiudadesDepartamento = administrarClasesAusentismos.contarCiudadesDepartamento(listClasesAusentismos.get(index).getSecuencia());
     contarSoAccidentesMedicosDepartamento = administrarClasesAusentismos.contarSoAccidentesMedicosDepartamento(listClasesAusentismos.get(index).getSecuencia());
     } else {
     contarBienProgramacionesDepartamento = administrarClasesAusentismos.contarBienProgramacionesDepartamento(filtrarClasesAusentismos.get(index).getSecuencia());
     contarCapModulosDepartamento = administrarClasesAusentismos.contarCapModulosDepartamento(filtrarClasesAusentismos.get(index).getSecuencia());
     contarCiudadesDepartamento = administrarClasesAusentismos.contarCiudadesDepartamento(filtrarClasesAusentismos.get(index).getSecuencia());
     contarSoAccidentesMedicosDepartamento = administrarClasesAusentismos.contarSoAccidentesMedicosDepartamento(filtrarClasesAusentismos.get(index).getSecuencia());
     }
     if (contarBienProgramacionesDepartamento.equals(new BigInteger("0"))
     && contarCapModulosDepartamento.equals(new BigInteger("0"))
     && contarCiudadesDepartamento.equals(new BigInteger("0"))
     && contarSoAccidentesMedicosDepartamento.equals(new BigInteger("0"))) {
     System.out.println("Borrado==0");
     borrandoClasesAusentismos();
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
     System.err.println("ERROR ControlClasesAusentismos verificarBorrado ERROR " + e);
     }
     }
     */
    public void revisarDialogoGuardar() {

        if (!borrarClasesAusentismos.isEmpty() || !crearClasesAusentismos.isEmpty() || !modificarClasesAusentismos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarClasesAusentismos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarClasesAusentismos");
            if (!borrarClasesAusentismos.isEmpty()) {
                administrarClasesAusentismos.borrarClasesAusentismos(borrarClasesAusentismos);
                //mostrarBorrados
                registrosBorrados = borrarClasesAusentismos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarClasesAusentismos.clear();
            }
            if (!modificarClasesAusentismos.isEmpty()) {
                administrarClasesAusentismos.modificarClasesAusentismos(modificarClasesAusentismos);
                modificarClasesAusentismos.clear();
            }
            if (!crearClasesAusentismos.isEmpty()) {
                administrarClasesAusentismos.crearClasesAusentismos(crearClasesAusentismos);
                crearClasesAusentismos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listClasesAusentismos = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosClasesAusentismos");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarClasesAusentismos = listClasesAusentismos.get(index);
            }
            if (tipoLista == 1) {
                editarClasesAusentismos = filtrarClasesAusentismos.get(index);
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
                context.update("formularioDialogos:editTiposausentismos");
                context.execute("editTiposausentismos.show()");
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

    public void agregarNuevoClasesAusentismos() {
        System.out.println("agregarNuevoClasesAusentismos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoClasesAusentismos.getCodigo() == null) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoClasesAusentismos.getCodigo());

            for (int x = 0; x < listClasesAusentismos.size(); x++) {
                if (listClasesAusentismos.get(x).getCodigo().equals(nuevoClasesAusentismos.getCodigo())) {
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
        if (nuevoClasesAusentismos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//2

        }

        if (nuevoClasesAusentismos.getTipo().getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Tipo Ausentismo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//4

        }

        System.out.println("contador " + contador);

        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                bandera = 0;
                filtrarClasesAusentismos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoClasesAusentismos.setSecuencia(l);

            crearClasesAusentismos.add(nuevoClasesAusentismos);

            listClasesAusentismos.add(nuevoClasesAusentismos);
            nuevoClasesAusentismos = new Clasesausentismos();
            nuevoClasesAusentismos.setTipo(new Tiposausentismos());
            context.update("form:datosClasesAusentismos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroClasesAusentismos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoClasesAusentismos() {
        System.out.println("limpiarNuevoClasesAusentismos");
        nuevoClasesAusentismos = new Clasesausentismos();
        nuevoClasesAusentismos.setTipo(new Tiposausentismos());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void cargarNuevoClasesAusentismos() {
        System.out.println("cargarNuevoClasesAusentismos");

        duplicarClasesAusentismos = new Clasesausentismos();
        duplicarClasesAusentismos.setTipo(new Tiposausentismos());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("nuevoRegistroClasesAusentismos.show()");

    }

    public void duplicandoClasesAusentismos() {
        System.out.println("duplicandoClasesAusentismos");
        if (index >= 0) {
            duplicarClasesAusentismos = new Clasesausentismos();
            duplicarClasesAusentismos.setTipo(new Tiposausentismos());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarClasesAusentismos.setSecuencia(l);
                duplicarClasesAusentismos.setCodigo(listClasesAusentismos.get(index).getCodigo());
                duplicarClasesAusentismos.setDescripcion(listClasesAusentismos.get(index).getDescripcion());
                duplicarClasesAusentismos.setTipo(listClasesAusentismos.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarClasesAusentismos.setSecuencia(l);
                duplicarClasesAusentismos.setCodigo(filtrarClasesAusentismos.get(index).getCodigo());
                duplicarClasesAusentismos.setDescripcion(filtrarClasesAusentismos.get(index).getDescripcion());
                duplicarClasesAusentismos.setTipo(filtrarClasesAusentismos.get(index).getTipo());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroClasesAusentismos.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarClasesAusentismos.getCodigo());

        if (duplicarClasesAusentismos.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listClasesAusentismos.size(); x++) {
                if (listClasesAusentismos.get(x).getCodigo().equals(duplicarClasesAusentismos.getCodigo())) {
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

        if (duplicarClasesAusentismos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * un Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (duplicarClasesAusentismos.getTipo().getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Tipo Ausentismo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarClasesAusentismos.getSecuencia() + "  " + duplicarClasesAusentismos.getCodigo());
            if (crearClasesAusentismos.contains(duplicarClasesAusentismos)) {
                System.out.println("Ya lo contengo.");
            }
            listClasesAusentismos.add(duplicarClasesAusentismos);
            crearClasesAusentismos.add(duplicarClasesAusentismos);
            context.update("form:datosClasesAusentismos");
            index = -1;
            System.out.println("--------------DUPLICAR------------------------");
            System.out.println("CODIGO : " + duplicarClasesAusentismos.getCodigo());
            System.out.println("EMPRESA: " + duplicarClasesAusentismos.getDescripcion());
            System.out.println("CARGO : " + duplicarClasesAusentismos.getTipo().getDescripcion());
            System.out.println("--------------DUPLICAR------------------------");

            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesAusentismos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosClasesAusentismos");
                bandera = 0;
                filtrarClasesAusentismos = null;
                tipoLista = 0;
            }
            duplicarClasesAusentismos = new Clasesausentismos();
            duplicarClasesAusentismos.setTipo(new Tiposausentismos());

            RequestContext.getCurrentInstance().execute("duplicarRegistroClasesAusentismos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarClasesAusentismos() {
        duplicarClasesAusentismos = new Clasesausentismos();
        duplicarClasesAusentismos.setTipo(new Tiposausentismos());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CLASESAUSENTISMOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesAusentismosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CLASESAUSENTISMOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listClasesAusentismos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CLASESAUSENTISMOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("CLASESAUSENTISMOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Clasesausentismos> getListClasesAusentismos() {
        if (listClasesAusentismos == null) {
            listClasesAusentismos = administrarClasesAusentismos.consultarClasesAusentismos();
        }
        return listClasesAusentismos;
    }

    public void setListClasesAusentismos(List<Clasesausentismos> listClasesAusentismos) {
        this.listClasesAusentismos = listClasesAusentismos;
    }

    public List<Clasesausentismos> getFiltrarClasesAusentismos() {
        return filtrarClasesAusentismos;
    }

    public void setFiltrarClasesAusentismos(List<Clasesausentismos> filtrarClasesAusentismos) {
        this.filtrarClasesAusentismos = filtrarClasesAusentismos;
    }

    public Clasesausentismos getNuevoClasesAusentismos() {
        return nuevoClasesAusentismos;
    }

    public void setNuevoClasesAusentismos(Clasesausentismos nuevoClasesAusentismos) {
        this.nuevoClasesAusentismos = nuevoClasesAusentismos;
    }

    public Clasesausentismos getDuplicarClasesAusentismos() {
        return duplicarClasesAusentismos;
    }

    public void setDuplicarClasesAusentismos(Clasesausentismos duplicarClasesAusentismos) {
        this.duplicarClasesAusentismos = duplicarClasesAusentismos;
    }

    public Clasesausentismos getEditarClasesAusentismos() {
        return editarClasesAusentismos;
    }

    public void setEditarClasesAusentismos(Clasesausentismos editarClasesAusentismos) {
        this.editarClasesAusentismos = editarClasesAusentismos;
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

    public List<Tiposausentismos> getListaTiposausentismos() {
        if (listaTiposausentismos == null) {
            listaTiposausentismos = administrarClasesAusentismos.consultarLOVTiposAusentismos();
        }
        return listaTiposausentismos;
    }

    public void setListaTiposausentismos(List<Tiposausentismos> listaTiposausentismos) {
        this.listaTiposausentismos = listaTiposausentismos;
    }

    public List<Tiposausentismos> getFiltradoTiposausentismos() {
        return filtradoTiposausentismos;
    }

    public void setFiltradoTiposausentismos(List<Tiposausentismos> filtradoTiposausentismos) {
        this.filtradoTiposausentismos = filtradoTiposausentismos;
    }

    public Tiposausentismos getTipoSeleccionado() {
        return centrocostoSeleccionado;
    }

    public void setTipoSeleccionado(Tiposausentismos centrocostoSeleccionado) {
        this.centrocostoSeleccionado = centrocostoSeleccionado;
    }

}
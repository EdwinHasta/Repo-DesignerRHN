/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ValoresConceptos;
import Entidades.Conceptos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarValoresConceptosInterface;
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
public class ControlValoresConceptos implements Serializable {

    @EJB
    AdministrarValoresConceptosInterface administrarValoresConceptos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<ValoresConceptos> listValoresConceptos;
    private List<ValoresConceptos> filtrarValoresConceptos;
    private List<ValoresConceptos> crearValoresConceptos;
    private List<ValoresConceptos> modificarValoresConceptos;
    private List<ValoresConceptos> borrarValoresConceptos;
    private ValoresConceptos nuevoValoresConceptos;
    private ValoresConceptos duplicarValoresConceptos;
    private ValoresConceptos editarValoresConceptos;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, personafir, cargo;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;
    private BigInteger backupCodigo;
    private Integer backupValorVoluntario;
    private String backupPais;
    //---------------------------------
    private String backupConcepto;
    private List<Conceptos> listaConceptos;
    private List<Conceptos> filtradoConceptos;
    private Conceptos conceptoSeleccionado;
    private String nuevoYduplicarCompletarPersona;
    //--------------------------------------
    private String nuevoYduplicarCompletarCargo;

    //---------------------------------
    private List<ValoresConceptos> listValoresConceptosBoton;
    private List<ValoresConceptos> filterValoresConceptosBoton;
    private ValoresConceptos conceptoSoporteSeleccionado;

    public ControlValoresConceptos() {
        listValoresConceptosBoton = null;
        listValoresConceptos = null;
        crearValoresConceptos = new ArrayList<ValoresConceptos>();
        modificarValoresConceptos = new ArrayList<ValoresConceptos>();
        borrarValoresConceptos = new ArrayList<ValoresConceptos>();
        permitirIndex = true;
        editarValoresConceptos = new ValoresConceptos();
        nuevoValoresConceptos = new ValoresConceptos();
        nuevoValoresConceptos.setConcepto(new Conceptos());
        duplicarValoresConceptos = new ValoresConceptos();
        duplicarValoresConceptos.setConcepto(new Conceptos());
        listaConceptos = null;
        filtradoConceptos = null;
        guardado = true;
        tamano = 302;

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlValoresConceptos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlValoresConceptos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listValoresConceptos.get(index).getSecuencia();
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    //conceptocodigo
                    backupCodigo = listValoresConceptos.get(index).getConcepto().getCodigo();
                }
                if (cualCelda == 1) {
                    backupConcepto = listValoresConceptos.get(index).getConcepto().getDescripcion();
                    System.out.println("CONCEPTO : " + backupConcepto);

                }
                if (cualCelda == 2) {
                    backupValorVoluntario = listValoresConceptos.get(index).getValorunitario();
                    System.out.println("VALOR VOLUNTARIO : " + backupConcepto);

                }

            } else if (tipoLista == 1) {
                if (cualCelda == 0) {
                    backupCodigo = filtrarValoresConceptos.get(index).getConcepto().getCodigo();
                }
                if (cualCelda == 1) {
                    backupConcepto = filtrarValoresConceptos.get(index).getConcepto().getDescripcion();
                    System.out.println("CONCEPTO : " + backupConcepto);
                }
                if (cualCelda == 2) {
                    backupValorVoluntario = filtrarValoresConceptos.get(index).getValorunitario();
                    System.out.println("VALOR VOLUNTARIO : " + backupConcepto);

                }
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A ControlValoresConceptos.asignarIndex \n");
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
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
                dig = -1;
            }
            if (dig == 1) {
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlValoresConceptos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {

            if (cualCelda == 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }
            if (cualCelda == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosValoresConceptos");
            bandera = 0;
            filtrarValoresConceptos = null;
            tipoLista = 0;
        }

        borrarValoresConceptos.clear();
        crearValoresConceptos.clear();
        modificarValoresConceptos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listValoresConceptos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosValoresConceptos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:codigo");
            codigo.setFilterStyle("width: 130px");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:personafir");
            personafir.setFilterStyle("width: 270px");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:cargo");
            cargo.setFilterStyle("width: 130px");
            RequestContext.getCurrentInstance().update("form:datosValoresConceptos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:personafir");
            personafir.setFilterStyle("display: none; visibility: hidden;");
            cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:cargo");
            cargo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosValoresConceptos");
            bandera = 0;
            filtrarValoresConceptos = null;
            tipoLista = 0;
        }
    }

    public void actualizarConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("concepto seleccionado : " + conceptoSeleccionado.getDescripcion());
        System.out.println("tipo Actualizacion : " + tipoActualizacion);
        System.out.println("tipo Lista : " + tipoLista);

        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listValoresConceptos.get(index).setConcepto(conceptoSeleccionado);

                if (!crearValoresConceptos.contains(listValoresConceptos.get(index))) {
                    if (modificarValoresConceptos.isEmpty()) {
                        modificarValoresConceptos.add(listValoresConceptos.get(index));
                    } else if (!modificarValoresConceptos.contains(listValoresConceptos.get(index))) {
                        modificarValoresConceptos.add(listValoresConceptos.get(index));
                    }
                }
            } else {
                filtrarValoresConceptos.get(index).setConcepto(conceptoSeleccionado);

                if (!crearValoresConceptos.contains(filtrarValoresConceptos.get(index))) {
                    if (modificarValoresConceptos.isEmpty()) {
                        modificarValoresConceptos.add(filtrarValoresConceptos.get(index));
                    } else if (!modificarValoresConceptos.contains(filtrarValoresConceptos.get(index))) {
                        modificarValoresConceptos.add(filtrarValoresConceptos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            System.out.println("ACTUALIZAR CONCEPTO SELECCIONADO : " + conceptoSeleccionado.getDescripcion());
            context.update("form:datosValoresConceptos");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZAR CONCEPTO NUEVO DEPARTAMENTO: " + conceptoSeleccionado.getDescripcion());
            nuevoValoresConceptos.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:nuevoPersona");
            context.update("formularioDialogos:nuevoCodigo");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZAR CONCEPTO DUPLICAR DEPARTAMENO: " + conceptoSeleccionado.getDescripcion());
            duplicarValoresConceptos.setConcepto(conceptoSeleccionado);
            context.update("formularioDialogos:duplicarPersona");
            context.update("formularioDialogos:duplicarCodigo");
        }
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        cambioValoresConceptos = true;
        context.execute("personasDialogo.hide()");
        context.reset("form:lovConceptos:globalFilter");
        context.update("form:lovConceptos");
    }

    public void cancelarCambioConceptos() {
        filtradoConceptos = null;
        conceptoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void modificarValoresConceptos(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearValoresConceptos.contains(listValoresConceptos.get(indice))) {

                    System.out.println("backupCodigo : " + backupValorVoluntario);

                    if (listValoresConceptos.get(indice).getValorunitario() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);

                    } else {
                        /*for (int j = 0; j < listValoresConceptos.size(); j++) {
                         if (j != indice) {
                         if (listValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                         contador++;
                         }
                         }
                         }

                         if (contador > 0) {
                         mensajeValidacion = "CODIGOS REPETIDOS";
                         banderita = false;
                         listValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);
                         } else {*/
                        banderita = true;
                        //}

                    }

                    if (banderita == true) {
                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(listValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
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
                    context.update("form:datosValoresConceptos");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);

                    if (listValoresConceptos.get(indice).getValorunitario() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);
                    } else {
                        for (int j = 0; j < listValoresConceptos.size(); j++) {
                            if (j != indice) {
                                if (listValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);
                        } else {
                            banderita = true;
                        }

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
                    context.update("form:datosValoresConceptos");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {
                    if (filtrarValoresConceptos.get(indice).getValorunitario() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);
                    } else {
                        for (int j = 0; j < filtrarValoresConceptos.size(); j++) {
                            if (j != indice) {
                                if (filtrarValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listValoresConceptos.size(); j++) {
                            if (j != indice) {
                                if (filtrarValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);

                        } else {
                            banderita = true;
                        }

                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
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
                    if (filtrarValoresConceptos.get(indice).getValorunitario() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);
                    } else {
                        for (int j = 0; j < filtrarValoresConceptos.size(); j++) {
                            if (j != indice) {
                                if (filtrarValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listValoresConceptos.size(); j++) {
                            if (j != indice) {
                                if (filtrarValoresConceptos.get(indice).getValorunitario() == listValoresConceptos.get(j).getValorunitario()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarValoresConceptos.get(indice).setValorunitario(backupValorVoluntario);

                        } else {
                            banderita = true;
                        }

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
            context.update("form:datosValoresConceptos");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {
            System.out.println("MODIFICANDO NORMA LABORAL : " + listValoresConceptos.get(indice).getConcepto().getDescripcion());
            if (!listValoresConceptos.get(indice).getConcepto().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listValoresConceptos.get(indice).getConcepto().setDescripcion(backupConcepto);
                } else {
                    listValoresConceptos.get(indice).getConcepto().setDescripcion(backupConcepto);
                }

                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listValoresConceptos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    } else {
                        filtrarValoresConceptos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    }
                    listaConceptos.clear();
                    listaConceptos = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (backupConcepto != null) {
                    if (tipoLista == 0) {
                        listValoresConceptos.get(index).getConcepto().setDescripcion(backupConcepto);
                    } else {
                        filtrarValoresConceptos.get(index).getConcepto().setDescripcion(backupConcepto);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("PAIS ANTES DE MOSTRAR DIALOGO PERSONA : " + backupConcepto);
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearValoresConceptos.contains(listValoresConceptos.get(indice))) {

                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(listValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {

                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosValoresConceptos");
            context.update("form:ACEPTAR");

        }

    }

    public void modificarValoresConceptosCodigo(int indice, String confirmarCambio, BigInteger valorConfirmar) {
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

        if (confirmarCambio.equalsIgnoreCase("CODIGOCONCEPTO")) {
            System.out.println("MODIFICANDO CODIGO CONCEPTO: " + listValoresConceptos.get(indice).getConcepto().getCodigo());
            if (!listValoresConceptos.get(indice).getConcepto().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listValoresConceptos.get(indice).getConcepto().setCodigo(backupCodigo);
                } else {
                    filtrarValoresConceptos.get(indice).getConcepto().setCodigo(backupCodigo);
                }

                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getCodigo().equals(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listValoresConceptos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    } else {
                        filtrarValoresConceptos.get(indice).setConcepto(listaConceptos.get(indiceUnicoElemento));
                    }
                    listaConceptos.clear();
                    listaConceptos = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (backupConcepto != null) {
                    if (tipoLista == 0) {
                        listValoresConceptos.get(index).getConcepto().setCodigo(backupCodigo);
                    } else {
                        filtrarValoresConceptos.get(index).getConcepto().setCodigo(backupCodigo);
                    }
                }
                tipoActualizacion = 0;
                System.out.println("CODIGO DIALOGO CONCEPTO : " + backupCodigo);
                context.update("form:personasDialogo");
                context.execute("personasDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearValoresConceptos.contains(listValoresConceptos.get(indice))) {

                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(listValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(listValoresConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {

                        if (modificarValoresConceptos.isEmpty()) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
                        } else if (!modificarValoresConceptos.contains(filtrarValoresConceptos.get(indice))) {
                            modificarValoresConceptos.add(filtrarValoresConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }
            cambioValoresConceptos = true;
            context.update("form:datosValoresConceptos");
            context.update("form:ACEPTAR");

        }

    }

    public void borrandoValoresConceptos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoValoresConceptos");
                if (!modificarValoresConceptos.isEmpty() && modificarValoresConceptos.contains(listValoresConceptos.get(index))) {
                    int modIndex = modificarValoresConceptos.indexOf(listValoresConceptos.get(index));
                    modificarValoresConceptos.remove(modIndex);
                    borrarValoresConceptos.add(listValoresConceptos.get(index));
                } else if (!crearValoresConceptos.isEmpty() && crearValoresConceptos.contains(listValoresConceptos.get(index))) {
                    int crearIndex = crearValoresConceptos.indexOf(listValoresConceptos.get(index));
                    crearValoresConceptos.remove(crearIndex);
                } else {
                    borrarValoresConceptos.add(listValoresConceptos.get(index));
                }
                listValoresConceptos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoValoresConceptos ");
                if (!modificarValoresConceptos.isEmpty() && modificarValoresConceptos.contains(filtrarValoresConceptos.get(index))) {
                    int modIndex = modificarValoresConceptos.indexOf(filtrarValoresConceptos.get(index));
                    modificarValoresConceptos.remove(modIndex);
                    borrarValoresConceptos.add(filtrarValoresConceptos.get(index));
                } else if (!crearValoresConceptos.isEmpty() && crearValoresConceptos.contains(filtrarValoresConceptos.get(index))) {
                    int crearIndex = crearValoresConceptos.indexOf(filtrarValoresConceptos.get(index));
                    crearValoresConceptos.remove(crearIndex);
                } else {
                    borrarValoresConceptos.add(filtrarValoresConceptos.get(index));
                }
                int VCIndex = listValoresConceptos.indexOf(filtrarValoresConceptos.get(index));
                listValoresConceptos.remove(VCIndex);
                filtrarValoresConceptos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosValoresConceptos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            cambioValoresConceptos = true;
        }

    }
    private BigInteger nuevoYduplicarCompletarCodigoConcepto;

    public void valoresBackupAutocompletar(int tipoNuevo, String valorCambio) {
        System.out.println("1...");
        if (valorCambio.equals("CODIGOCONCEPTO")) {
            if (tipoNuevo == 1) {
                nuevoYduplicarCompletarCodigoConcepto = nuevoValoresConceptos.getConcepto().getCodigo();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarCodigoConcepto = duplicarValoresConceptos.getConcepto().getCodigo();
            }
            System.out.println("CARGO : " + nuevoYduplicarCompletarCodigoConcepto);
        } else if (valorCambio.equals("CONCEPTO")) {
            if (tipoNuevo == 1) {
                nuevoYduplicarCompletarPersona = nuevoValoresConceptos.getConcepto().getDescripcion();
            } else if (tipoNuevo == 2) {
                nuevoYduplicarCompletarPersona = duplicarValoresConceptos.getConcepto().getDescripcion();
            }

            System.out.println("CONCEPTO : " + nuevoYduplicarCompletarPersona);
        }

    }

    public void autocompletarNuevoCodigoBigInteger(String confirmarCambio, BigInteger valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        if (confirmarCambio.equalsIgnoreCase("CODIGOCONCEPTO")) {
            System.out.println(" nueva Operando    Entro al if 'Centro costo'");
            System.out.println("NUEVO PERSONA :-------> " + nuevoValoresConceptos.getConcepto().getCodigo());

            if (!nuevoValoresConceptos.getConcepto().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarCodigoConcepto);
                nuevoValoresConceptos.getConcepto().setCodigo(nuevoYduplicarCompletarCodigoConcepto);
                getListaConceptos();
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getCodigo().equals(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoValoresConceptos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                    System.err.println("PERSONA GUARDADA :-----> " + nuevoValoresConceptos.getConcepto().getDescripcion());
                } else {
                    //listValoresConceptos.get(index).getConcepto().setCodigo(backupCodigo);
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoValoresConceptos.getConcepto().setCodigo(nuevoYduplicarCompletarCodigoConcepto);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoValoresConceptos.setConcepto(new Conceptos());
            }
            context.update("formularioDialogos:nuevoPersona");
            context.update("formularioDialogos:nuevoCodigo");
        }

    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {
            System.out.println(" nueva Operando    Entro al if 'Centro costo'");
            System.out.println("NUEVO PERSONA :-------> " + nuevoValoresConceptos.getConcepto().getDescripcion());

            if (!nuevoValoresConceptos.getConcepto().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCompletarPersona: " + nuevoYduplicarCompletarPersona);
                nuevoValoresConceptos.getConcepto().setDescripcion(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoValoresConceptos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                    System.err.println("PERSONA GUARDADA :-----> " + nuevoValoresConceptos.getConcepto().getDescripcion());
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoValoresConceptos.getConcepto().setDescripcion(nuevoYduplicarCompletarPersona);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoValoresConceptos.setConcepto(new Conceptos());
                nuevoValoresConceptos.getConcepto().setDescripcion(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoValoresConceptos.getConcepto().getDescripcion());
            }
            context.update("formularioDialogos:nuevoPersona");
            context.update("formularioDialogos:nuevoCodigo");

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
        context.update("form:personasDialogo");
        context.execute("personasDialogo.show()");
    }

    public void asignarVariableOperandos(int tipoNuevo) {
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

    public void autocompletarDuplicadoCodigoBigInteger(String confirmarCambio, BigInteger valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGOCONCEPTO")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarCodigoConcepto);

            if (duplicarValoresConceptos.getConcepto().getCodigo() != null) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoYduplicarCompletarCodigoConcepto: " + nuevoYduplicarCompletarCodigoConcepto);
                duplicarValoresConceptos.getConcepto().setCodigo(nuevoYduplicarCompletarCodigoConcepto);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getCodigo().equals(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarValoresConceptos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarValoresConceptos.getEmpresa().setNombre(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarValoresConceptos.setConcepto(new Conceptos());

                    System.out.println("DUPLICAR PERSONA  : " + duplicarValoresConceptos.getConcepto().getCodigo());
                    System.out.println("nuevoYduplicarCompletarPERSONA : " + nuevoYduplicarCompletarCodigoConcepto);
                    if (tipoLista == 0) {
                        listValoresConceptos.get(index).getConcepto().setCodigo(nuevoYduplicarCompletarCodigoConcepto);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listValoresConceptos.get(index).getConcepto().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarValoresConceptos.get(index).getConcepto().setCodigo(nuevoYduplicarCompletarCodigoConcepto);
                    }

                }

            }
            context.update("formularioDialogos:duplicarPersona");
            context.update("formularioDialogos:duplicarCodigo");
        }
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONCEPTO")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarPersona);

            if (!duplicarValoresConceptos.getConcepto().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarPersona);
                duplicarValoresConceptos.getConcepto().setDescripcion(nuevoYduplicarCompletarPersona);
                for (int i = 0; i < listaConceptos.size(); i++) {
                    if (listaConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarValoresConceptos.setConcepto(listaConceptos.get(indiceUnicoElemento));
                    listaConceptos = null;
                } else {
                    context.update("form:personasDialogo");
                    context.execute("personasDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarValoresConceptos.getEmpresa().setNombre(nuevoYduplicarCompletarPais);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarValoresConceptos.setConcepto(new Conceptos());
                    duplicarValoresConceptos.getConcepto().setDescripcion(" ");

                    System.out.println("DUPLICAR PERSONA  : " + duplicarValoresConceptos.getConcepto().getDescripcion());
                    System.out.println("nuevoYduplicarCompletarPERSONA : " + nuevoYduplicarCompletarPersona);
                    if (tipoLista == 0) {
                        listValoresConceptos.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarPersona);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listValoresConceptos.get(index).getConcepto().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarValoresConceptos.get(index).getConcepto().setDescripcion(nuevoYduplicarCompletarPersona);
                    }

                }

            }

            context.update("formularioDialogos:duplicarCodigo");
            context.update("formularioDialogos:duplicarPersona");
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarValoresConceptos.isEmpty() || !crearValoresConceptos.isEmpty() || !modificarValoresConceptos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarValoresConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarValoresConceptos");
            if (!borrarValoresConceptos.isEmpty()) {
                administrarValoresConceptos.borrarValoresConceptos(borrarValoresConceptos);
                //mostrarBorrados
                registrosBorrados = borrarValoresConceptos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarValoresConceptos.clear();
            }
            if (!modificarValoresConceptos.isEmpty()) {
                administrarValoresConceptos.modificarValoresConceptos(modificarValoresConceptos);
                modificarValoresConceptos.clear();
            }
            if (!crearValoresConceptos.isEmpty()) {
                administrarValoresConceptos.crearValoresConceptos(crearValoresConceptos);
                crearValoresConceptos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listValoresConceptos = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosValoresConceptos");
            k = 0;
            guardado = true;

        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarValoresConceptos = listValoresConceptos.get(index);
            }
            if (tipoLista == 1) {
                editarValoresConceptos = filtrarValoresConceptos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editPais");
                context.execute("editPais.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editConceptos");
                context.execute("editConceptos.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editOperandos");
                context.execute("editOperandos.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoValoresConceptos() {
        System.out.println("agregarNuevoValoresConceptos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoValoresConceptos.getConcepto().getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *un Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;//3

        }

        if (nuevoValoresConceptos.getValorunitario() == null) {
            mensajeValidacion = " * Valor Voluntario \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;//1
        }

        System.out.println("contador " + contador);
        BigInteger contarValoresConceptos = administrarValoresConceptos.contarConceptoValorConcepto(nuevoValoresConceptos.getConcepto().getSecuencia());

        if (contador == 2 && contarValoresConceptos.equals(new BigInteger("0"))) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                bandera = 0;
                filtrarValoresConceptos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoValoresConceptos.setSecuencia(l);
            crearValoresConceptos.add(nuevoValoresConceptos);

            listValoresConceptos.add(nuevoValoresConceptos);
            nuevoValoresConceptos = new ValoresConceptos();
            nuevoValoresConceptos.setConcepto(new Conceptos());
            context.update("form:datosValoresConceptos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroValoresConceptos.hide()");
            index = -1;
            secRegistro = null;
            cambioValoresConceptos = true;

        } else {
            if (contarValoresConceptos.intValue() > 0) {
                mensajeValidacion = "El CONCEPTO elegido ya fue insertado";
            }
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoValoresConceptos() {
        System.out.println("limpiarNuevoValoresConceptos");
        nuevoValoresConceptos = new ValoresConceptos();
        nuevoValoresConceptos.setConcepto(new Conceptos());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void cargarNuevoValoresConceptos() {
        System.out.println("cargarNuevoValoresConceptos");

        duplicarValoresConceptos = new ValoresConceptos();
        duplicarValoresConceptos.setConcepto(new Conceptos());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("nuevoRegistroValoresConceptos.show()");

    }

    public void duplicandoValoresConceptos() {
        System.out.println("duplicandoValoresConceptos");
        if (index >= 0) {
            duplicarValoresConceptos = new ValoresConceptos();
            duplicarValoresConceptos.setConcepto(new Conceptos());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarValoresConceptos.setSecuencia(l);
                duplicarValoresConceptos.setConcepto(listValoresConceptos.get(index).getConcepto());
                duplicarValoresConceptos.setValorunitario(listValoresConceptos.get(index).getValorunitario());
            }
            if (tipoLista == 1) {
                duplicarValoresConceptos.setSecuencia(l);
                duplicarValoresConceptos.setConcepto(filtrarValoresConceptos.get(index).getConcepto());
                duplicarValoresConceptos.setValorunitario(filtrarValoresConceptos.get(index).getValorunitario());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroValoresConceptos.show()");
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

        if (duplicarValoresConceptos.getConcepto().getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Concepto \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarValoresConceptos.getValorunitario() == null) {
            mensajeValidacion = " *Debe tener un codigo voluntario \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;//1
        }

        BigInteger contarConceptosOperandos = administrarValoresConceptos.contarConceptoValorConcepto(duplicarValoresConceptos.getConcepto().getSecuencia());

        if (contador == 2 && contarConceptosOperandos.equals(new BigInteger("0"))) {
            if (crearValoresConceptos.contains(duplicarValoresConceptos)) {
                System.out.println("Ya lo contengo.");
            }
            listValoresConceptos.add(duplicarValoresConceptos);
            crearValoresConceptos.add(duplicarValoresConceptos);
            context.update("form:datosValoresConceptos");
            index = -1;
            System.out.println("--------------DUPLICAR------------------------");
            System.out.println("PERSONA : " + duplicarValoresConceptos.getConcepto().getDescripcion());
            System.out.println("--------------DUPLICAR------------------------");

            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                personafir = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:personafir");
                personafir.setFilterStyle("display: none; visibility: hidden;");
                cargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosValoresConceptos:cargo");
                cargo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosValoresConceptos");
                bandera = 0;
                filtrarValoresConceptos = null;
                tipoLista = 0;
            }
            duplicarValoresConceptos = new ValoresConceptos();
            duplicarValoresConceptos.setConcepto(new Conceptos());

            RequestContext.getCurrentInstance().execute("duplicarRegistroValoresConceptos.hide()");
            index = -1;
        } else {
            if (contarConceptosOperandos.intValue() > 0) {
                mensajeValidacion = "El CONCEPTO elegido ya fue insertado";
            }
            contador = 0;

            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarValoresConceptos() {
        duplicarValoresConceptos = new ValoresConceptos();
        duplicarValoresConceptos.setConcepto(new Conceptos());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosValoresConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VALORESCONCEPTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosValoresConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VALORESCONCEPTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listValoresConceptos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VALORESCONCEPTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VALORESCONCEPTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    private boolean cambioValoresConceptos = false;

    public void llamadoDialogoBuscarConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                // banderaSeleccionCentrosCostosPorEmpresa = true;
                context.execute("confirmarGuardar.show()");

            } else {
                listValoresConceptosBoton = null;
                getListValoresConceptosBoton();
                index = -1;
                context.update("formularioDialogos:lovCentrosCostos");
                context.execute("buscarCentrosCostosDialogo.show()");

            }
        } catch (Exception e) {
            System.err.println("ERROR LLAMADO DIALOGO BUSCAR CENTROS COSTOS " + e);
        }

    }

    public void seleccionConceptoSoporte() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            if (guardado == true) {
                listValoresConceptos.clear();
                System.err.println("seleccionCentrosCostosPorEmpresa " + conceptoSoporteSeleccionado.getConcepto().getDescripcion());
                listValoresConceptos.add(conceptoSoporteSeleccionado);
                System.err.println("listCentrosCostosPorEmpresa tamaño " + listValoresConceptos.size());
                System.err.println("listCentrosCostosPorEmpresa nombre " + listValoresConceptos.get(0).getConcepto().getDescripcion());
                conceptoSoporteSeleccionado = null;
                filterValoresConceptosBoton = null;
                aceptar = true;
                context.update("form:datosValoresConceptos");
                context.execute("buscarCentrosCostosDialogo.hide()");
                context.reset("formularioDialogos:lovCentrosCostos:globalFilter");
            } else {
                context.update("form:confirmarGuardarConceptos");
                context.execute("confirmarGuardarConceptos.show()");
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.seleccionaVigencia ERROR====" + e.getMessage());
        }
    }

    public void cancelarSeleccionConceptoSoporte() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            conceptoSoporteSeleccionado = null;
            filterValoresConceptosBoton = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            context.update("form:aceptarNCC");

        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETACENTROSCOSTOS.cancelarSeleccionVigencia ERROR====" + e.getMessage());
        }
    }

    public List<ValoresConceptos> getListValoresConceptosBoton() {
        if (listValoresConceptosBoton == null) {
            listValoresConceptosBoton = listValoresConceptos;
        }
        return listValoresConceptosBoton;
    }

    public void setListValoresConceptosBoton(List<ValoresConceptos> listValoresConceptosBoton) {
        this.listValoresConceptosBoton = listValoresConceptosBoton;
    }

    public List<ValoresConceptos> getFilterValoresConceptosBoton() {
        return filterValoresConceptosBoton;
    }

    public void setFilterValoresConceptosBoton(List<ValoresConceptos> filterValoresConceptosBoton) {
        this.filterValoresConceptosBoton = filterValoresConceptosBoton;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public ValoresConceptos getConceptoSoporteSeleccionado() {
        return conceptoSoporteSeleccionado;
    }

    public void setConceptoSoporteSeleccionado(ValoresConceptos conceptoSoporteSeleccionado) {
        this.conceptoSoporteSeleccionado = conceptoSoporteSeleccionado;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ValoresConceptos> getListValoresConceptos() {
        if (listValoresConceptos == null) {
            listValoresConceptos = administrarValoresConceptos.consultarValoresConceptos();
        }
        return listValoresConceptos;
    }

    public void setListValoresConceptos(List<ValoresConceptos> listValoresConceptos) {
        this.listValoresConceptos = listValoresConceptos;
    }

    public List<ValoresConceptos> getFiltrarValoresConceptos() {
        return filtrarValoresConceptos;
    }

    public void setFiltrarValoresConceptos(List<ValoresConceptos> filtrarValoresConceptos) {
        this.filtrarValoresConceptos = filtrarValoresConceptos;
    }

    public ValoresConceptos getNuevoValoresConceptos() {
        return nuevoValoresConceptos;
    }

    public void setNuevoValoresConceptos(ValoresConceptos nuevoValoresConceptos) {
        this.nuevoValoresConceptos = nuevoValoresConceptos;
    }

    public ValoresConceptos getDuplicarValoresConceptos() {
        return duplicarValoresConceptos;
    }

    public void setDuplicarValoresConceptos(ValoresConceptos duplicarValoresConceptos) {
        this.duplicarValoresConceptos = duplicarValoresConceptos;
    }

    public ValoresConceptos getEditarValoresConceptos() {
        return editarValoresConceptos;
    }

    public void setEditarValoresConceptos(ValoresConceptos editarValoresConceptos) {
        this.editarValoresConceptos = editarValoresConceptos;
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

    public List<Conceptos> getListaConceptos() {
        if (listaConceptos == null) {
            listaConceptos = administrarValoresConceptos.consultarLOVConceptos();
        }
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

    public void setConceptoSeleccionado(Conceptos conceptoSeleccionado) {
        this.conceptoSeleccionado = conceptoSeleccionado;
    }

}

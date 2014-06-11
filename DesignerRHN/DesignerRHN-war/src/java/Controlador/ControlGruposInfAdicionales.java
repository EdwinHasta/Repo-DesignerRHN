/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.GruposInfAdicionales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarGruposInfAdicionalesInterface;
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
public class ControlGruposInfAdicionales implements Serializable {

    @EJB
    AdministrarGruposInfAdicionalesInterface administrarGruposInfAdicionales;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<GruposInfAdicionales> listGruposInfAdicionales;
    private List<GruposInfAdicionales> filtrarGruposInfAdicionales;
    private List<GruposInfAdicionales> crearGruposInfAdicionales;
    private List<GruposInfAdicionales> modificarGruposInfAdicionales;
    private List<GruposInfAdicionales> borrarGruposInfAdicionales;
    private GruposInfAdicionales nuevoGruposInfAdicionales;
    private GruposInfAdicionales duplicarGruposInfAdicionales;
    private GruposInfAdicionales editarGruposInfAdicionales;
    private GruposInfAdicionales grupoFactorRiesgoSeleccionado;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, estado;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;
    private Integer backupCodigo;
    private String backupDescripcion;
    private String infoRegistro;

    public ControlGruposInfAdicionales() {
        listGruposInfAdicionales = null;
        crearGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        modificarGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        borrarGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        permitirIndex = true;
        editarGruposInfAdicionales = new GruposInfAdicionales();
        nuevoGruposInfAdicionales = new GruposInfAdicionales();
        duplicarGruposInfAdicionales = new GruposInfAdicionales();
        guardado = true;
        tamano = 270;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarGruposInfAdicionales.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlGruposInfAdicionales.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlGruposInfAdicionales eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    private String backUpEstado;

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);
        System.err.println("permitirIndex  " + permitirIndex);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listGruposInfAdicionales.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listGruposInfAdicionales.get(index).getCodigo();
                backupDescripcion = listGruposInfAdicionales.get(index).getDescripcion();
                backUpEstado = listGruposInfAdicionales.get(index).getEstado();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarGruposInfAdicionales.get(index).getCodigo();
                backupDescripcion = filtrarGruposInfAdicionales.get(index).getDescripcion();
                backUpEstado = filtrarGruposInfAdicionales.get(index).getEstado();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlGruposInfAdicionales.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlGruposInfAdicionales.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            bandera = 0;
            filtrarGruposInfAdicionales = null;
            tipoLista = 0;
        }

        borrarGruposInfAdicionales.clear();
        crearGruposInfAdicionales.clear();
        modificarGruposInfAdicionales.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listGruposInfAdicionales = null;
        guardado = true;
        permitirIndex = true;
        getListGruposInfAdicionales();
        RequestContext context = RequestContext.getCurrentInstance();

        if (listGruposInfAdicionales == null || listGruposInfAdicionales.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosGruposInfAdicionales");
        context.update("form:ACEPTAR");
    }
    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            bandera = 0;
            filtrarGruposInfAdicionales = null;
            tipoLista = 0;
        }

        borrarGruposInfAdicionales.clear();
        crearGruposInfAdicionales.clear();
        modificarGruposInfAdicionales.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listGruposInfAdicionales = null;
        guardado = true;
        permitirIndex = true;
        getListGruposInfAdicionales();
        RequestContext context = RequestContext.getCurrentInstance();

        if (listGruposInfAdicionales == null || listGruposInfAdicionales.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosGruposInfAdicionales");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("width: 400px");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            bandera = 0;
            filtrarGruposInfAdicionales = null;
            tipoLista = 0;
        }
    }

    public void modificarGruposInfAdicionales(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0, pass = 0;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearGruposInfAdicionales.contains(listGruposInfAdicionales.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listGruposInfAdicionales.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (listGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                        } else {
                            pass++;
                        }

                    }
                    if (listGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";

                        listGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else if (listGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";

                        listGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        pass++;
                    }
                    if (listGruposInfAdicionales.get(indice).getEstado() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setEstado(backUpEstado);
                    } else if (listGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setEstado(backUpEstado);

                    } else {
                        pass++;
                    }

                    if (pass == 3) {
                        if (modificarGruposInfAdicionales.isEmpty()) {
                            modificarGruposInfAdicionales.add(listGruposInfAdicionales.get(indice));
                        } else if (!modificarGruposInfAdicionales.contains(listGruposInfAdicionales.get(indice))) {
                            modificarGruposInfAdicionales.add(listGruposInfAdicionales.get(indice));
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
                    context.update("form:datosGruposInfAdicionales");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listGruposInfAdicionales.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (listGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                        } else {
                            pass++;
                        }

                    }
                    if (listGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else if (listGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        pass++;
                    }
                    if (listGruposInfAdicionales.get(indice).getEstado().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setEstado(backUpEstado);
                    } else if (listGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        listGruposInfAdicionales.get(indice).setEstado(backUpEstado);

                    } else {
                        pass++;
                    }
                    if (pass == 3) {
                        if (guardado == true) {
                            guardado = false;
                        }
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");

                    }
                    index = -1;
                    secRegistro = null;
                    context.update("form:datosGruposInfAdicionales");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(indice))) {
                    if (filtrarGruposInfAdicionales.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarGruposInfAdicionales.get(indice).setCodigo(backupCodigo);

                        } else {
                            pass++;
                        }

                    }

                    if (filtrarGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else if (filtrarGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else {
                        pass++;
                    }
                    if (filtrarGruposInfAdicionales.get(indice).getEstado().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setEstado(backUpEstado);
                    } else if (filtrarGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setEstado(backUpEstado);

                    } else {
                        pass++;
                    }
                    if (pass == 3) {
                        if (modificarGruposInfAdicionales.isEmpty()) {
                            modificarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(indice));
                        } else if (!modificarGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(indice))) {
                            modificarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(indice));
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
                    if (filtrarGruposInfAdicionales.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarGruposInfAdicionales.get(indice).setCodigo(backupCodigo);

                        } else {
                            pass++;
                        }

                    }

                    if (filtrarGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else if (filtrarGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setDescripcion(backupDescripcion);
                    } else {
                        pass++;
                    }
                    if (filtrarGruposInfAdicionales.get(indice).getEstado().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setEstado(backUpEstado);
                    } else if (filtrarGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarGruposInfAdicionales.get(indice).setEstado(backUpEstado);

                    } else {
                        pass++;
                    }
                    if (pass == 3) {
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
            context.update("form:datosGruposInfAdicionales");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoGruposInfAdicionales() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoGruposInfAdicionales");
                if (!modificarGruposInfAdicionales.isEmpty() && modificarGruposInfAdicionales.contains(listGruposInfAdicionales.get(index))) {
                    int modIndex = modificarGruposInfAdicionales.indexOf(listGruposInfAdicionales.get(index));
                    modificarGruposInfAdicionales.remove(modIndex);
                    borrarGruposInfAdicionales.add(listGruposInfAdicionales.get(index));
                } else if (!crearGruposInfAdicionales.isEmpty() && crearGruposInfAdicionales.contains(listGruposInfAdicionales.get(index))) {
                    int crearIndex = crearGruposInfAdicionales.indexOf(listGruposInfAdicionales.get(index));
                    crearGruposInfAdicionales.remove(crearIndex);
                } else {
                    borrarGruposInfAdicionales.add(listGruposInfAdicionales.get(index));
                }
                listGruposInfAdicionales.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoGruposInfAdicionales ");
                if (!modificarGruposInfAdicionales.isEmpty() && modificarGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(index))) {
                    int modIndex = modificarGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                    modificarGruposInfAdicionales.remove(modIndex);
                    borrarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(index));
                } else if (!crearGruposInfAdicionales.isEmpty() && crearGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(index))) {
                    int crearIndex = crearGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                    crearGruposInfAdicionales.remove(crearIndex);
                } else {
                    borrarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(index));
                }
                int VCIndex = listGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                listGruposInfAdicionales.remove(VCIndex);
                filtrarGruposInfAdicionales.remove(index);

            }
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");
            context.update("form:datosGruposInfAdicionales");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        BigInteger verificarInformacionesAdicionales;

        try {
            System.err.println("Control Secuencia de ControlGruposInfAdicionales ");
            if (tipoLista == 0) {
                verificarInformacionesAdicionales = administrarGruposInfAdicionales.verificarInformacionesAdicionales(listGruposInfAdicionales.get(index).getSecuencia());
            } else {
                verificarInformacionesAdicionales = administrarGruposInfAdicionales.verificarInformacionesAdicionales(filtrarGruposInfAdicionales.get(index).getSecuencia());
            }
            if (verificarInformacionesAdicionales.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoGruposInfAdicionales();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarInformacionesAdicionales = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlGruposInfAdicionales verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarGruposInfAdicionales.isEmpty() || !crearGruposInfAdicionales.isEmpty() || !modificarGruposInfAdicionales.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarGruposInfAdicionales() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarGruposInfAdicionales");
            if (!borrarGruposInfAdicionales.isEmpty()) {
                administrarGruposInfAdicionales.borrarGruposInfAdicionales(borrarGruposInfAdicionales);
                //mostrarBorrados
                registrosBorrados = borrarGruposInfAdicionales.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarGruposInfAdicionales.clear();
            }
            if (!modificarGruposInfAdicionales.isEmpty()) {
                administrarGruposInfAdicionales.modificarGruposInfAdicionales(modificarGruposInfAdicionales);
                modificarGruposInfAdicionales.clear();
            }
            if (!crearGruposInfAdicionales.isEmpty()) {
                administrarGruposInfAdicionales.crearGruposInfAdicionales(crearGruposInfAdicionales);
                crearGruposInfAdicionales.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listGruposInfAdicionales = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosGruposInfAdicionales");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarGruposInfAdicionales = listGruposInfAdicionales.get(index);
            }
            if (tipoLista == 1) {
                editarGruposInfAdicionales = filtrarGruposInfAdicionales.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editDescripcion");
                context.execute("editDescripcion.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoGruposInfAdicionales() {
        System.out.println("agregarNuevoGruposInfAdicionales");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoGruposInfAdicionales.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoGruposInfAdicionales.getCodigo());

            for (int x = 0; x < listGruposInfAdicionales.size(); x++) {
                if (listGruposInfAdicionales.get(x).getCodigo() == nuevoGruposInfAdicionales.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoGruposInfAdicionales.getDescripcion() == null || nuevoGruposInfAdicionales.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;
        }
        if (nuevoGruposInfAdicionales.getEstado() == null || nuevoGruposInfAdicionales.getEstado().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Estado \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;
        }

        System.out.println("contador " + contador);

        if (contador == 3) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
                estado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
                bandera = 0;
                filtrarGruposInfAdicionales = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoGruposInfAdicionales.setSecuencia(l);

            crearGruposInfAdicionales.add(nuevoGruposInfAdicionales);

            listGruposInfAdicionales.add(nuevoGruposInfAdicionales);
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
            context.update("form:informacionRegistro");
            nuevoGruposInfAdicionales = new GruposInfAdicionales();
            context.update("form:datosGruposInfAdicionales");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroGruposInfAdicionales.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoGruposInfAdicionales() {
        System.out.println("limpiarNuevoGruposInfAdicionales");
        nuevoGruposInfAdicionales = new GruposInfAdicionales();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoGruposInfAdicionales() {
        System.out.println("duplicandoGruposInfAdicionales");
        if (index >= 0) {
            duplicarGruposInfAdicionales = new GruposInfAdicionales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarGruposInfAdicionales.setSecuencia(l);
                duplicarGruposInfAdicionales.setCodigo(listGruposInfAdicionales.get(index).getCodigo());
                duplicarGruposInfAdicionales.setDescripcion(listGruposInfAdicionales.get(index).getDescripcion());
                duplicarGruposInfAdicionales.setEstado(listGruposInfAdicionales.get(index).getEstado());
            }
            if (tipoLista == 1) {
                duplicarGruposInfAdicionales.setSecuencia(l);
                duplicarGruposInfAdicionales.setCodigo(filtrarGruposInfAdicionales.get(index).getCodigo());
                duplicarGruposInfAdicionales.setDescripcion(filtrarGruposInfAdicionales.get(index).getDescripcion());
                duplicarGruposInfAdicionales.setEstado(filtrarGruposInfAdicionales.get(index).getEstado());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroGruposInfAdicionales.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarGruposInfAdicionales.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarGruposInfAdicionales.getDescripcion());

        if (duplicarGruposInfAdicionales.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listGruposInfAdicionales.size(); x++) {
                if (listGruposInfAdicionales.get(x).getCodigo() == duplicarGruposInfAdicionales.getCodigo()) {
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
        if (duplicarGruposInfAdicionales.getDescripcion() == null || duplicarGruposInfAdicionales.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarGruposInfAdicionales.getEstado() == null || duplicarGruposInfAdicionales.getEstado().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   *Estado \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarGruposInfAdicionales.getSecuencia() + "  " + duplicarGruposInfAdicionales.getCodigo());
            if (crearGruposInfAdicionales.contains(duplicarGruposInfAdicionales)) {
                System.out.println("Ya lo contengo.");
            }
            listGruposInfAdicionales.add(duplicarGruposInfAdicionales);
            crearGruposInfAdicionales.add(duplicarGruposInfAdicionales);
            context.update("form:datosGruposInfAdicionales");
            index = -1;
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
            context.update("form:informacionRegistro");
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estado = (Column) c.getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
                estado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
                bandera = 0;
                filtrarGruposInfAdicionales = null;
                tipoLista = 0;
            }
            duplicarGruposInfAdicionales = new GruposInfAdicionales();
            RequestContext.getCurrentInstance().execute("duplicarRegistroGruposInfAdicionales.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarGruposInfAdicionales() {
        duplicarGruposInfAdicionales = new GruposInfAdicionales();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGruposInfAdicionalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "GRUPOSINFADICIONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGruposInfAdicionalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "GRUPOSINFADICIONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listGruposInfAdicionales.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "GRUPOSINFADICIONALES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("GRUPOSINFADICIONALES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<GruposInfAdicionales> getListGruposInfAdicionales() {
        if (listGruposInfAdicionales == null) {
            listGruposInfAdicionales = administrarGruposInfAdicionales.consultarGruposInfAdicionales();
        }
        RequestContext context = RequestContext.getCurrentInstance();

        if (listGruposInfAdicionales == null || listGruposInfAdicionales.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listGruposInfAdicionales.size();
        }
        context.update("form:informacionRegistro");
        return listGruposInfAdicionales;
    }

    public void setListGruposInfAdicionales(List<GruposInfAdicionales> listGruposInfAdicionales) {
        this.listGruposInfAdicionales = listGruposInfAdicionales;
    }

    public List<GruposInfAdicionales> getFiltrarGruposInfAdicionales() {
        return filtrarGruposInfAdicionales;
    }

    public void setFiltrarGruposInfAdicionales(List<GruposInfAdicionales> filtrarGruposInfAdicionales) {
        this.filtrarGruposInfAdicionales = filtrarGruposInfAdicionales;
    }

    public GruposInfAdicionales getNuevoGruposInfAdicionales() {
        return nuevoGruposInfAdicionales;
    }

    public void setNuevoGruposInfAdicionales(GruposInfAdicionales nuevoGruposInfAdicionales) {
        this.nuevoGruposInfAdicionales = nuevoGruposInfAdicionales;
    }

    public GruposInfAdicionales getDuplicarGruposInfAdicionales() {
        return duplicarGruposInfAdicionales;
    }

    public void setDuplicarGruposInfAdicionales(GruposInfAdicionales duplicarGruposInfAdicionales) {
        this.duplicarGruposInfAdicionales = duplicarGruposInfAdicionales;
    }

    public GruposInfAdicionales getEditarGruposInfAdicionales() {
        return editarGruposInfAdicionales;
    }

    public void setEditarGruposInfAdicionales(GruposInfAdicionales editarGruposInfAdicionales) {
        this.editarGruposInfAdicionales = editarGruposInfAdicionales;
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

    public GruposInfAdicionales getGrupoFactorRiesgoSeleccionado() {
        return grupoFactorRiesgoSeleccionado;
    }

    public void setGrupoFactorRiesgoSeleccionado(GruposInfAdicionales grupoFactorRiesgoSeleccionado) {
        this.grupoFactorRiesgoSeleccionado = grupoFactorRiesgoSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

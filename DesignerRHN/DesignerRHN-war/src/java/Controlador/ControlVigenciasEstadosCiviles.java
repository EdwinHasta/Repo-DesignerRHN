/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VigenciasEstadosCiviles;
import Entidades.EstadosCiviles;
import Entidades.Personas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVigenciasEstadosCivilesInterface;
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
public class ControlVigenciasEstadosCiviles implements Serializable {

    @EJB
    AdministrarVigenciasEstadosCivilesInterface administrarVigenciaEstadosCiviles;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<VigenciasEstadosCiviles> listVigenciaEstadoCivilPorEmpleado;
    private List<VigenciasEstadosCiviles> filtrarVigenciaEstadoCivilPorEmplado;
    private List<VigenciasEstadosCiviles> crearVigenciaEstadoCivilPorEmplado;
    private List<VigenciasEstadosCiviles> modificarVigenciaEstadoCivilPorEmplado;
    private List<VigenciasEstadosCiviles> borrarVigenciaEstadoCivilPorEmplado;
    private VigenciasEstadosCiviles nuevoVigenciaEstadoCivil;
    private VigenciasEstadosCiviles duplicarVigenciaEstadoCivil;
    private VigenciasEstadosCiviles editarVigenciaEstadoCivil;
    private VigenciasEstadosCiviles vigenciaSeleccionada;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column fecha, parentesco;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaEmpleado;
//Empleado
    private Empleados empleadoSeleccionado;
    //autocompletar
    private String estadoCivil;
    private List<EstadosCiviles> listaEstadosCiviles;
    private List<EstadosCiviles> filtradoEstadosCiviles;
    private EstadosCiviles vigenciaEstadoCivilSeleccionada;
    private String nuevoYduplicarCompletarEstadoCivil;
    //ALTO TABLA
    private String altoTabla;

    public ControlVigenciasEstadosCiviles() {
        listVigenciaEstadoCivilPorEmpleado = null;
        crearVigenciaEstadoCivilPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        modificarVigenciaEstadoCivilPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        borrarVigenciaEstadoCivilPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        permitirIndex = true;
        editarVigenciaEstadoCivil = new VigenciasEstadosCiviles();
        nuevoVigenciaEstadoCivil = new VigenciasEstadosCiviles();
        nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
        duplicarVigenciaEstadoCivil = new VigenciasEstadosCiviles();
        empleadoSeleccionado = null;
        secuenciaEmpleado = null;
        listaEstadosCiviles = null;
        filtradoEstadosCiviles = null;
        guardado = true;
        altoTabla = "270";
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciaEstadosCiviles.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    private BigInteger secuenciaPersona;

    public void recibirEmpleado(BigInteger sec, BigInteger secPersona) {
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN CONTROLBETAEMPLVIGENCIANORMALABORAL");
        }
        secuenciaEmpleado = sec;
        secuenciaPersona = secPersona;
        empleadoSeleccionado = null;
        listVigenciaEstadoCivilPorEmpleado = null;
        getEmpleadoSeleccionado();
        getListVigenciaEstadoCivilPorEmpleado();
    }

    public void mostrarNuevo() {
        System.err.println("NUEVA FECHA : " + nuevoVigenciaEstadoCivil.getFechavigencia());
    }

    public void mostrarInfo(int indice, int celda) {
        int contador = 0;
        int fechas = 0;
        mensajeValidacion = " ";
        index = indice;
        cualCelda = celda;
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            if (tipoLista == 0) {
                secRegistro = listVigenciaEstadoCivilPorEmpleado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listVigenciaEstadoCivilPorEmpleado.get(indice).getFechavigencia());
                if (listVigenciaEstadoCivilPorEmpleado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < listVigenciaEstadoCivilPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (listVigenciaEstadoCivilPorEmpleado.get(indice).getFechavigencia().equals(listVigenciaEstadoCivilPorEmpleado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS REPETIDAS";
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                        context.update("form:datosHvEntrevista");

                    }
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            } else {

                secRegistro = filtrarVigenciaEstadoCivilPorEmplado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + filtrarVigenciaEstadoCivilPorEmplado.get(indice).getFechavigencia());
                if (filtrarVigenciaEstadoCivilPorEmplado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < filtrarVigenciaEstadoCivilPorEmplado.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciaEstadoCivilPorEmplado.get(indice).getFechavigencia().equals(filtrarVigenciaEstadoCivilPorEmplado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                    for (int j = 0; j < listVigenciaEstadoCivilPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciaEstadoCivilPorEmplado.get(indice).getFechavigencia().equals(listVigenciaEstadoCivilPorEmpleado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS REPETIDAS";
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                        context.update("form:datosHvEntrevista");

                    }
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();

                }

                index = -1;
                secRegistro = null;
            }
            System.out.println("Indice: " + index + " Celda: " + cualCelda);

        }

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLBETAEMPLVIGENCIANORMALABORAL.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarVigenciaEstadoCivilPorEmplado.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listVigenciaEstadoCivilPorEmpleado.get(index).getSecuencia();
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    estadoCivil = listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil().getDescripcion();
                } else {
                    estadoCivil = listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil().getDescripcion();
                }
            }
            System.out.println("ESTADO CIVIL : " + estadoCivil);
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("\n ENTRE A CONTROLBETAEMPLVIGENCIANORMALABORAL.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 1) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            if (cualCelda == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }
        }
    }

    private String infoRegistro;

    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarVigenciaEstadoCivilPorEmplado = null;
            tipoLista = 0;
        }

        borrarVigenciaEstadoCivilPorEmplado.clear();
        crearVigenciaEstadoCivilPorEmplado.clear();
        modificarVigenciaEstadoCivilPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciaEstadoCivilPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getListVigenciaEstadoCivilPorEmpleado();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciaEstadoCivilPorEmpleado == null || listVigenciaEstadoCivilPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarVigenciaEstadoCivilPorEmplado = null;
            tipoLista = 0;
        }

        borrarVigenciaEstadoCivilPorEmplado.clear();
        crearVigenciaEstadoCivilPorEmplado.clear();
        modificarVigenciaEstadoCivilPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciaEstadoCivilPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getListVigenciaEstadoCivilPorEmpleado();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciaEstadoCivilPorEmpleado == null || listVigenciaEstadoCivilPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("width: 60px");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("width: 600px");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarVigenciaEstadoCivilPorEmplado = null;
            tipoLista = 0;
        }
    }

    public void modificandoVigenciaEstadoCivil(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EMPL VIGENCIA ESTADO CIVIL");
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPLVIGENCIANORMALABORAL, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                    if (listVigenciaEstadoCivilPorEmpleado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listVigenciaEstadoCivilPorEmpleado.size(); j++) {
                            if (j != indice) {
                                if (listVigenciaEstadoCivilPorEmpleado.get(indice).getFechavigencia().equals(listVigenciaEstadoCivilPorEmpleado.get(j).getFechavigencia())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "FECHAS REPETIDAS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }
                    }

                    if (banderita == true) {
                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
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
            } else {

                if (!crearVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(indice))) {
                    if (filtrarVigenciaEstadoCivilPorEmplado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(indice));
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
            context.update("form:datosHvEntrevista");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {
            System.out.println("MODIFICANDO ESTADO CIVIL : " + listVigenciaEstadoCivilPorEmpleado.get(indice).getEstadocivil().getDescripcion());
            if (!listVigenciaEstadoCivilPorEmpleado.get(indice).getEstadocivil().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listVigenciaEstadoCivilPorEmpleado.get(indice).getEstadocivil().setDescripcion(estadoCivil);
                } else {
                    listVigenciaEstadoCivilPorEmpleado.get(indice).getEstadocivil().setDescripcion(estadoCivil);
                }

                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listVigenciaEstadoCivilPorEmpleado.get(indice).setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    } else {
                        filtrarVigenciaEstadoCivilPorEmplado.get(indice).setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    }
                    listaEstadosCiviles.clear();
                    listaEstadosCiviles = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (tipoLista == 0) {
                    listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil().setDescripcion(estadoCivil);
                } else {
                    filtrarVigenciaEstadoCivilPorEmplado.get(index).getEstadocivil().setDescripcion(estadoCivil);
                }
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {

                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(indice))) {

                        if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                            modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(indice));
                        } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(indice))) {
                            modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosHvEntrevista");
            context.update("form:ACEPTAR");

        }

    }

    public void actualizarEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciaEstadoCivilPorEmpleado.get(index).setEstadocivil(vigenciaEstadoCivilSeleccionada);

                if (!crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(index))) {
                    if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                        modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(index));
                    } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(index))) {
                        modificarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarVigenciaEstadoCivilPorEmplado.get(index).setEstadocivil(vigenciaEstadoCivilSeleccionada);

                if (!crearVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(index))) {
                    if (modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                        modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                    } else if (!modificarVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(index))) {
                        modificarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosHvEntrevista");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZARNORMA LABORAR NUEVA ESTADO CIVIL: " + vigenciaEstadoCivilSeleccionada.getDescripcion());
            nuevoVigenciaEstadoCivil.setEstadocivil(vigenciaEstadoCivilSeleccionada);
            context.update("formularioDialogos:nuevoNombreSucursal");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZARNORMA LABORAR DUPLICAR ESTADO CIVIL: " + vigenciaEstadoCivilSeleccionada.getDescripcion());
            duplicarVigenciaEstadoCivil.setEstadocivil(vigenciaEstadoCivilSeleccionada);
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
        filtradoEstadosCiviles = null;
        vigenciaEstadoCivilSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.execute("lovTiposFamiliares.clearFilters()");
        context.execute("sucursalesDialogo.hide()");
        //context.update("form:lovTiposFamiliares");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioEstadoCivil() {
        filtradoEstadosCiviles = null;
        vigenciaEstadoCivilSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.execute("lovTiposFamiliares.clearFilters()");
        context.execute("sucursalesDialogo.hide()");
    }

    public void valoresBackupAutocompletar(int tipoNuevo) {
        System.out.println("1...");
        if (tipoNuevo == 1) {
            nuevoYduplicarCompletarEstadoCivil = nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarEstadoCivil = duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion();
        }
        System.out.println(" valoresBackupAutocompletar nuevoYduplicarCompletarEstadoCivil " + nuevoYduplicarCompletarEstadoCivil);
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {
            System.out.println(" nueva Ciudad    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion());

            if (!nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCiudadCompletar: " + nuevoYduplicarCompletarEstadoCivil);
                nuevoVigenciaEstadoCivil.getEstadocivil().setDescripcion(nuevoYduplicarCompletarEstadoCivil);
                getListaEstadosCiviles();
                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoVigenciaEstadoCivil.setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    listaEstadosCiviles = null;
                    getListaEstadosCiviles();
                    System.err.println("ESTADO CIVIL GUARDADA " + nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion());
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoVigenciaEstadoCivil.getEstadocivil().setDescripcion(nuevoYduplicarCompletarEstadoCivil);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
                nuevoVigenciaEstadoCivil.getEstadocivil().setDescripcion(" ");
                System.out.println("NUEVA ESTADO CIVIL" + nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion());
            }
            context.update("formularioDialogos:nuevoNombreSucursal");
        }

    }

    public void asignarVariableEstadosCiviles(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:sucursalesDialogo");
        context.execute("sucursalesDialogo.show()");
    }

    public void limpiarNuevoEstadoCivil() {
        try {
            System.out.println("\n ENTRE A LIMPIAR NUEVO ESTADO CIVIL  \n");
            nuevoVigenciaEstadoCivil = new VigenciasEstadosCiviles();
            nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETAEMPLVIGENCIANORMALABORAL LIMPIAR NUEVO ESTADO CIVIL ERROR :" + e.getMessage());
        }
    }

    public void cargarEstadosCivilesNuevoYDuplicar(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:sucursalesDialogo");
            context.execute("sucursalesDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:sucursalesDialogo");
            context.execute("sucursalesDialogo.show()");
        }
    }

    public void autocompletarDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        System.out.println("DUPLICAR entrooooooooooooooooooooooooooooooooooooooooooooooooooooooo!!!");
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarEstadoCivil);

            if (!duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion().equals("") || !duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion().isEmpty()) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarEstadoCivil);
                duplicarVigenciaEstadoCivil.getEstadocivil().setDescripcion(nuevoYduplicarCompletarEstadoCivil);
                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarVigenciaEstadoCivil.setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    listaEstadosCiviles = null;
                    getListaEstadosCiviles();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarVigenciaEstadoCivil.getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
                    duplicarVigenciaEstadoCivil.getEstadocivil().setDescripcion(" ");

                    System.out.println("DUPLICAR Valor ESTADO CIVIL : " + duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion());
                    if (tipoLista == 0) {
                        listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil().setDescripcion(nuevoYduplicarCompletarEstadoCivil);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia EstadoCivil " + listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarVigenciaEstadoCivilPorEmplado.get(index).getEstadocivil().setDescripcion(nuevoYduplicarCompletarEstadoCivil);
                    }

                }

            }
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
    }

    public void borrandoHvEntrevistas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoEvalCompetencias");
                if (!modificarVigenciaEstadoCivilPorEmplado.isEmpty() && modificarVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(index))) {
                    int modIndex = modificarVigenciaEstadoCivilPorEmplado.indexOf(listVigenciaEstadoCivilPorEmpleado.get(index));
                    modificarVigenciaEstadoCivilPorEmplado.remove(modIndex);
                    borrarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(index));
                } else if (!crearVigenciaEstadoCivilPorEmplado.isEmpty() && crearVigenciaEstadoCivilPorEmplado.contains(listVigenciaEstadoCivilPorEmpleado.get(index))) {
                    int crearIndex = crearVigenciaEstadoCivilPorEmplado.indexOf(listVigenciaEstadoCivilPorEmpleado.get(index));
                    crearVigenciaEstadoCivilPorEmplado.remove(crearIndex);
                } else {
                    borrarVigenciaEstadoCivilPorEmplado.add(listVigenciaEstadoCivilPorEmpleado.get(index));
                }
                listVigenciaEstadoCivilPorEmpleado.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarVigenciaEstadoCivilPorEmplado.isEmpty() && modificarVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(index))) {
                    int modIndex = modificarVigenciaEstadoCivilPorEmplado.indexOf(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                    modificarVigenciaEstadoCivilPorEmplado.remove(modIndex);
                    borrarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                } else if (!crearVigenciaEstadoCivilPorEmplado.isEmpty() && crearVigenciaEstadoCivilPorEmplado.contains(filtrarVigenciaEstadoCivilPorEmplado.get(index))) {
                    int crearIndex = crearVigenciaEstadoCivilPorEmplado.indexOf(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                    crearVigenciaEstadoCivilPorEmplado.remove(crearIndex);
                } else {
                    borrarVigenciaEstadoCivilPorEmplado.add(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                }
                int VCIndex = listVigenciaEstadoCivilPorEmpleado.indexOf(filtrarVigenciaEstadoCivilPorEmplado.get(index));
                listVigenciaEstadoCivilPorEmpleado.remove(VCIndex);
                filtrarVigenciaEstadoCivilPorEmplado.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosHvEntrevista");
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
            context.update("form:informacionRegistro");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

        }

    }

    /* public void verificarBorrado() {
     System.out.println("Estoy en verificarBorrado");
     try {
     System.err.println("Control Secuencia de ControlHvEntrevistas ");
     competenciasCargos = administrarHvEntrevistas.verificarBorradoCompetenciasCargos(listHvEntrevistas.get(index).getSecuencia());

     if (competenciasCargos.intValueExact() == 0) {
     System.out.println("Borrado==0");
     borrandoHvEntrevistas();
     } else {
     System.out.println("Borrado>0");

     RequestContext context = RequestContext.getCurrentInstance();
     context.update("form:validacionBorrar");
     context.execute("validacionBorrar.show()");
     index = -1;

     competenciasCargos = new BigDecimal(-1);

     }
     } catch (Exception e) {
     System.err.println("ERROR ControlHvEntrevistas verificarBorrado ERROR " + e);
     }
     }*/
    public void revisarDialogoGuardar() {

        if (!borrarVigenciaEstadoCivilPorEmplado.isEmpty() || !crearVigenciaEstadoCivilPorEmplado.isEmpty() || !modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarVigenciaEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                for (int i = 0; i < borrarVigenciaEstadoCivilPorEmplado.size(); i++) {
                    System.out.println("Borrando...");
                }
                administrarVigenciaEstadosCiviles.borrarVigenciasEstadosCiviles(borrarVigenciaEstadoCivilPorEmplado);
                //mostrarBorrados
                registrosBorrados = borrarVigenciaEstadoCivilPorEmplado.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarVigenciaEstadoCivilPorEmplado.clear();
            }
            if (!crearVigenciaEstadoCivilPorEmplado.isEmpty()) {
                for (int i = 0; i < crearVigenciaEstadoCivilPorEmplado.size(); i++) {
                    System.out.println("Creando...");
                    System.out.println("-----------------------------------------------");
                    System.out.println("Secuencia : " + crearVigenciaEstadoCivilPorEmplado.get(i).getSecuencia());
                    System.out.println("Empleado : " + crearVigenciaEstadoCivilPorEmplado.get(i).getPersona().getNombre());
                    System.out.println("Fecha :" + crearVigenciaEstadoCivilPorEmplado.get(i).getFechavigencia());
                    System.out.println("Estado Civil : " + crearVigenciaEstadoCivilPorEmplado.get(i).getEstadocivil().getDescripcion());
                    System.out.println("-----------------------------------------------");

                }
                administrarVigenciaEstadosCiviles.crearVigenciasEstadosCiviles(crearVigenciaEstadoCivilPorEmplado);
                crearVigenciaEstadoCivilPorEmplado.clear();
            }
            if (!modificarVigenciaEstadoCivilPorEmplado.isEmpty()) {
                System.out.println("Modificando...");
                administrarVigenciaEstadosCiviles.modificarVigenciasEstadosCiviles(modificarVigenciaEstadoCivilPorEmplado);
                modificarVigenciaEstadoCivilPorEmplado.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listVigenciaEstadoCivilPorEmpleado = null;
            context.update("form:datosHvEntrevista");
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
                editarVigenciaEstadoCivil = listVigenciaEstadoCivilPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaEstadoCivil = filtrarVigenciaEstadoCivilPorEmplado.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editPuntaje");
                context.execute("editPuntaje.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoVigenciaEstadoCivil() {
        System.out.println("agregarNuevoVigenciaEstadoCivil");
        int contador = 0;
        //nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
        Short a = 0;
        a = null;
        int fechas = 0;
        mensajeValidacion = " ";
        nuevoVigenciaEstadoCivil.setPersona(empleadoSeleccionado.getPersona());
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Nueva Fecha : " + nuevoVigenciaEstadoCivil.getFechavigencia());
        if (nuevoVigenciaEstadoCivil.getFechavigencia() == null || nuevoVigenciaEstadoCivil.getFechavigencia().equals("")) {
            mensajeValidacion = " *Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int i = 0; i < listVigenciaEstadoCivilPorEmpleado.size(); i++) {
                if (nuevoVigenciaEstadoCivil.getFechavigencia().equals(listVigenciaEstadoCivilPorEmpleado.get(i).getFechavigencia())) {
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "Fechas repetidas ";
            } else {
                contador++;
            }
        }
        if (nuevoVigenciaEstadoCivil.getEstadocivil().getSecuencia() == null || nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   *Estado Civil\n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
            System.out.println("Estado Civil Guardado:::::::  : " + nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion());

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                System.out.println("Desactivar");
                fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarVigenciaEstadoCivilPorEmplado = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoVigenciaEstadoCivil.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("fecha " + nuevoVigenciaEstadoCivil.getFechavigencia());
            System.err.println("nombre " + nuevoVigenciaEstadoCivil.getEstadocivil().getDescripcion());
            System.err.println("-----------------------------------------------");

            crearVigenciaEstadoCivilPorEmplado.add(nuevoVigenciaEstadoCivil);
            listVigenciaEstadoCivilPorEmpleado.add(nuevoVigenciaEstadoCivil);
            nuevoVigenciaEstadoCivil = new VigenciasEstadosCiviles();
            nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
            context.update("form:datosHvEntrevista");
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
            context.update("form:informacionRegistro");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoVigenciaEstadoCivil() {
        System.out.println("limpiarNuevoVigenciaEstadoCivil");
        nuevoVigenciaEstadoCivil = new VigenciasEstadosCiviles();
        nuevoVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoVigenciaEstadoCivil() {
        if (index >= 0) {
            System.out.println("duplicandoVigenciaEstadoCivil");
            duplicarVigenciaEstadoCivil = new VigenciasEstadosCiviles();
            duplicarVigenciaEstadoCivil.setPersona(new Personas());
            duplicarVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVigenciaEstadoCivil.setSecuencia(l);
                duplicarVigenciaEstadoCivil.setPersona(listVigenciaEstadoCivilPorEmpleado.get(index).getPersona());
                duplicarVigenciaEstadoCivil.setFechavigencia(listVigenciaEstadoCivilPorEmpleado.get(index).getFechavigencia());
                duplicarVigenciaEstadoCivil.setEstadocivil(listVigenciaEstadoCivilPorEmpleado.get(index).getEstadocivil());
            }
            if (tipoLista == 1) {
                duplicarVigenciaEstadoCivil.setSecuencia(l);
                duplicarVigenciaEstadoCivil.setPersona(filtrarVigenciaEstadoCivilPorEmplado.get(index).getPersona());
                duplicarVigenciaEstadoCivil.setFechavigencia(filtrarVigenciaEstadoCivilPorEmplado.get(index).getFechavigencia());
                duplicarVigenciaEstadoCivil.setEstadocivil(filtrarVigenciaEstadoCivilPorEmplado.get(index).getEstadocivil());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvC");
            context.execute("duplicarRegistroEvalCompetencias.show()");
            //index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR HVENTREVISTAS");
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        int fechas = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarVigenciaEstadoCivil.getFechavigencia());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion());

        if (duplicarVigenciaEstadoCivil.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {

            for (int j = 0; j < listVigenciaEstadoCivilPorEmpleado.size(); j++) {
                if (duplicarVigenciaEstadoCivil.getFechavigencia().equals(listVigenciaEstadoCivilPorEmpleado.get(j).getFechavigencia())) {
                    System.out.println("Se repiten");
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "FECHAS REPETIDAS";
            } else {
                System.out.println("bandera");
                contador++;
            }

        }
        if (duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion() == null || duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion().isEmpty() || duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion().equals(" ") || duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Estado Civil \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;

        }
        if (duplicarVigenciaEstadoCivil.getPersona().getSecuencia() == null) {
            duplicarVigenciaEstadoCivil.setPersona(empleadoSeleccionado.getPersona());
        }
        if (contador == 2) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarVigenciaEstadoCivil.setSecuencia(l);
            System.out.println("Datos Duplicando: " + duplicarVigenciaEstadoCivil.getSecuencia() + "  " + duplicarVigenciaEstadoCivil.getFechavigencia());
            if (crearVigenciaEstadoCivilPorEmplado.contains(duplicarVigenciaEstadoCivil)) {
                System.out.println("Ya lo contengo.");
            }
            listVigenciaEstadoCivilPorEmpleado.add(duplicarVigenciaEstadoCivil);
            crearVigenciaEstadoCivilPorEmplado.add(duplicarVigenciaEstadoCivil);
            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("fecha " + duplicarVigenciaEstadoCivil.getFechavigencia());
            System.err.println("Estado Civil " + duplicarVigenciaEstadoCivil.getEstadocivil().getDescripcion());
            System.err.println("Nombre Empleado " + duplicarVigenciaEstadoCivil.getPersona().getNombre());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
            context.update("form:informacionRegistro");
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarVigenciaEstadoCivilPorEmplado = null;
                tipoLista = 0;
            }
            duplicarVigenciaEstadoCivil = new VigenciasEstadosCiviles();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            fechas = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void limpiarDuplicarVigenciaEstadoCivil() {
        duplicarVigenciaEstadoCivil = new VigenciasEstadosCiviles();
        duplicarVigenciaEstadoCivil.setPersona(new Personas());
        duplicarVigenciaEstadoCivil.setEstadocivil(new EstadosCiviles());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listVigenciaEstadoCivilPorEmpleado.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASESTADOSCIVILES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASESTADOSCIVILES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<VigenciasEstadosCiviles> getListVigenciaEstadoCivilPorEmpleado() {
        if (listVigenciaEstadoCivilPorEmpleado == null) {
            listVigenciaEstadoCivilPorEmpleado = administrarVigenciaEstadosCiviles.consultarVigenciasEstadosCivilesPorEmpleado(secuenciaPersona);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciaEstadoCivilPorEmpleado == null || listVigenciaEstadoCivilPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listVigenciaEstadoCivilPorEmpleado.size();
        }
        context.update("form:informacionRegistro");
        return listVigenciaEstadoCivilPorEmpleado;
    }

    public void setListVigenciaEstadoCivilPorEmpleado(List<VigenciasEstadosCiviles> listVigenciaEstadoCivilPorEmpleado) {
        this.listVigenciaEstadoCivilPorEmpleado = listVigenciaEstadoCivilPorEmpleado;
    }

    public List<VigenciasEstadosCiviles> getFiltrarVigenciaEstadoCivilPorEmplado() {
        return filtrarVigenciaEstadoCivilPorEmplado;
    }

    public void setFiltrarVigenciaEstadoCivilPorEmplado(List<VigenciasEstadosCiviles> filtrarVigenciaEstadoCivilPorEmplado) {
        this.filtrarVigenciaEstadoCivilPorEmplado = filtrarVigenciaEstadoCivilPorEmplado;
    }

    public VigenciasEstadosCiviles getNuevoVigenciaEstadoCivil() {
        return nuevoVigenciaEstadoCivil;
    }

    public void setNuevoVigenciaEstadoCivil(VigenciasEstadosCiviles nuevoVigenciaEstadoCivil) {
        this.nuevoVigenciaEstadoCivil = nuevoVigenciaEstadoCivil;
    }

    public VigenciasEstadosCiviles getDuplicarVigenciaEstadoCivil() {
        return duplicarVigenciaEstadoCivil;
    }

    public void setDuplicarVigenciaEstadoCivil(VigenciasEstadosCiviles duplicarVigenciaEstadoCivil) {
        this.duplicarVigenciaEstadoCivil = duplicarVigenciaEstadoCivil;
    }

    public VigenciasEstadosCiviles getEditarVigenciaEstadoCivil() {
        return editarVigenciaEstadoCivil;
    }

    public void setEditarVigenciaEstadoCivil(VigenciasEstadosCiviles editarVigenciaEstadoCivil) {
        this.editarVigenciaEstadoCivil = editarVigenciaEstadoCivil;
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

    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarVigenciaEstadosCiviles.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    private String infoRecursoEstadosCiviles;

    public List<EstadosCiviles> getListaEstadosCiviles() {
        if (listaEstadosCiviles == null) {
            listaEstadosCiviles = administrarVigenciaEstadosCiviles.lovEstadosCiviles();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaEstadosCiviles == null || listaEstadosCiviles.isEmpty()) {
            infoRecursoEstadosCiviles = "Cantidad de registros: 0 ";
        } else {
            infoRecursoEstadosCiviles = "Cantidad de registros: " + listaEstadosCiviles.size();
        }
        context.update("form:infoRecursoEstadosCiviles");
        return listaEstadosCiviles;
    }

    public void setListaEstadosCiviles(List<EstadosCiviles> listaEstadosCiviles) {
        this.listaEstadosCiviles = listaEstadosCiviles;
    }

    public List<EstadosCiviles> getFiltradoEstadosCiviles() {
        return filtradoEstadosCiviles;
    }

    public void setFiltradoEstadosCiviles(List<EstadosCiviles> filtradoEstadosCiviles) {
        this.filtradoEstadosCiviles = filtradoEstadosCiviles;
    }

    public EstadosCiviles getVigenciaEstadoCivilSeleccionada() {
        return vigenciaEstadoCivilSeleccionada;
    }

    public void setVigenciaEstadoCivilSeleccionada(EstadosCiviles vigenciaEstadoCivilSeleccionada) {
        this.vigenciaEstadoCivilSeleccionada = vigenciaEstadoCivilSeleccionada;
    }

    public VigenciasEstadosCiviles getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasEstadosCiviles vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
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

    public String getInfoRecursoEstadosCiviles() {
        return infoRecursoEstadosCiviles;
    }

    public void setInfoRecursoEstadosCiviles(String infoRecursoEstadosCiviles) {
        this.infoRecursoEstadosCiviles = infoRecursoEstadosCiviles;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VigenciasNormasEmpleados;
import Entidades.NormasLaborales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVigenciaNormaLaboralInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlBetaEmplVigenciaNormaLaboral implements Serializable {

    @EJB
    AdministrarVigenciaNormaLaboralInterface administrarVigenciaNormaLaboral;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<VigenciasNormasEmpleados> listEmplVigenciaNormaLaboralPorEmpleado;
    private List<VigenciasNormasEmpleados> filtrarEmplVigenciaNormaLaboralPorEmplado;
    private List<VigenciasNormasEmpleados> crearEmplVigenciaNormaLaboralPorEmplado;
    private List<VigenciasNormasEmpleados> modificarEmplVigenciaNormaLaboralPorEmplado;
    private List<VigenciasNormasEmpleados> borrarEmplVigenciaNormaLaboralPorEmplado;
    private VigenciasNormasEmpleados nuevoEmplVigenciaNormaLaboral;
    private VigenciasNormasEmpleados duplicarEmplVigenciaNormaLaboral;
    private VigenciasNormasEmpleados editarEmplVigenciaNormaLaboral;
    private VigenciasNormasEmpleados vigenciaSeleccionada;
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
    private String normaLaboral;
    private List<NormasLaborales> listaNormasLaborales;
    private List<NormasLaborales> filtradoNormasLaborales;
    private NormasLaborales normaLaboralSeleccionada;
    private String nuevoYduplicarCompletarNormaLaboral;
    //ALTO TABLA
    private String altoTabla;

    public ControlBetaEmplVigenciaNormaLaboral() {
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        crearEmplVigenciaNormaLaboralPorEmplado = new ArrayList<VigenciasNormasEmpleados>();
        modificarEmplVigenciaNormaLaboralPorEmplado = new ArrayList<VigenciasNormasEmpleados>();
        borrarEmplVigenciaNormaLaboralPorEmplado = new ArrayList<VigenciasNormasEmpleados>();
        permitirIndex = true;
        editarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
        duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        empleadoSeleccionado = null;
        secuenciaEmpleado = null;
        listaNormasLaborales = null;
        filtradoNormasLaborales = null;
        guardado = true;
        altoTabla = "270";
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciaNormaLaboral.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        if (sec == null) {
            System.out.println("ERROR EN RECIbIR LA SECUENCIA DEL EMPLEADO EN CONTROLBETAEMPLVIGENCIANORMALABORAL");
        }
        secuenciaEmpleado = sec;
        empleadoSeleccionado = null;
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        getEmpleadoSeleccionado();
        getListEmplVigenciaNormaLaboralPorEmpleado();
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
                secRegistro = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getSecuencia();
                if (listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    listEmplVigenciaNormaLaboralPorEmpleado.get(indice).setFechavigencia(backUpFecha);
                    contador++;
                } else {
                    for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getFechavigencia().equals(listEmplVigenciaNormaLaboralPorEmpleado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS REPETIDAS";
                    listEmplVigenciaNormaLaboralPorEmpleado.get(indice).setFechavigencia(backUpFecha);
                    contador++;
                }
                if (contador == 0) {
                    if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
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
                }
            } else {

                secRegistro = filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getSecuencia();
                if (filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).getFechavigencia() == null) {
                    filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).setFechavigencia(backUpFecha);
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {

                    for (int j = 0; j < filtrarEmplVigenciaNormaLaboralPorEmplado.size(); j++) {
                        if (j != indice) {
                            if (filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).getFechavigencia().equals(filtrarEmplVigenciaNormaLaboralPorEmplado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).setFechavigencia(backUpFecha);
                    mensajeValidacion = "FECHAS REPETIDAS";
                    contador++;
                }
                if (contador == 0) {
                    if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
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

                }

                index = -1;
                secRegistro = null;
            }

        }
        context.update("form:datosHvEntrevista");

    }

    public void eventoFiltrar() {
        try {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL eventoFiltrar ERROR===" + e.getMessage());
        }
    }
    private Date backUpFecha;

    public void cambiarIndice(int indice, int celda) {

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getSecuencia();
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    backUpFecha = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getFechavigencia();
                } else {
                    backUpFecha = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getFechavigencia();
                }
            }
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    normaLaboral = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getNormalaboral().getNombre();
                } else {
                    normaLaboral = listEmplVigenciaNormaLaboralPorEmpleado.get(index).getNormalaboral().getNombre();
                }
            }
        }
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
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
            filtrarEmplVigenciaNormaLaboralPorEmplado = null;
            tipoLista = 0;
        }

        borrarEmplVigenciaNormaLaboralPorEmplado.clear();
        crearEmplVigenciaNormaLaboralPorEmplado.clear();
        modificarEmplVigenciaNormaLaboralPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getListEmplVigenciaNormaLaboralPorEmpleado();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listEmplVigenciaNormaLaboralPorEmpleado == null || listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
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
            filtrarEmplVigenciaNormaLaboralPorEmplado = null;
            tipoLista = 0;
        }

        borrarEmplVigenciaNormaLaboralPorEmplado.clear();
        crearEmplVigenciaNormaLaboralPorEmplado.clear();
        modificarEmplVigenciaNormaLaboralPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getListEmplVigenciaNormaLaboralPorEmpleado();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listEmplVigenciaNormaLaboralPorEmpleado == null || listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
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
            bandera = 1;
        } else if (bandera == 1) {
            fecha = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarEmplVigenciaNormaLaboralPorEmplado = null;
            tipoLista = 0;
        }
    }

    public void modificandoEmplVigenciaNormaLaboral(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                    if (listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                            if (j != indice) {
                                if (listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getFechavigencia().equals(listEmplVigenciaNormaLaboralPorEmpleado.get(j).getFechavigencia())) {
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
                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
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

                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice))) {
                    if (filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice));
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
            if (!listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getNormalaboral().getNombre().equals("")) {
                if (tipoLista == 0) {
                    listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getNormalaboral().setNombre(normaLaboral);
                } else {
                    listEmplVigenciaNormaLaboralPorEmpleado.get(indice).getNormalaboral().setNombre(normaLaboral);
                }

                for (int i = 0; i < listaNormasLaborales.size(); i++) {
                    if (listaNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listEmplVigenciaNormaLaboralPorEmpleado.get(indice).setNormalaboral(listaNormasLaborales.get(indiceUnicoElemento));
                    } else {
                        filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice).setNormalaboral(listaNormasLaborales.get(indiceUnicoElemento));
                    }
                    listaNormasLaborales.clear();
                    listaNormasLaborales = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (tipoLista == 0) {
                    listEmplVigenciaNormaLaboralPorEmpleado.get(index).getNormalaboral().setNombre(normaLaboral);
                } else {
                    filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getNormalaboral().setNombre(normaLaboral);
                }
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {

                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice))) {

                        if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice));
                        } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice))) {
                            modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(indice));
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

    public void actualizarNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listEmplVigenciaNormaLaboralPorEmpleado.get(index).setNormalaboral(normaLaboralSeleccionada);

                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(index))) {
                    if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                    } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(index))) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).setNormalaboral(normaLaboralSeleccionada);

                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index))) {
                    if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                    } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index))) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
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
            nuevoEmplVigenciaNormaLaboral.setNormalaboral(normaLaboralSeleccionada);
            context.update("formularioDialogos:nuevoNombreSucursal");
        } else if (tipoActualizacion == 2) {
            duplicarEmplVigenciaNormaLaboral.setNormalaboral(normaLaboralSeleccionada);
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
        filtradoNormasLaborales = null;
        normaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.update("form:sucursalesDialogo");
        context.update("form:lovTiposFamiliares");
        context.update("form:aceptarS");
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.execute("sucursalesDialogo.hide()");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioNormaLaboral() {
        filtradoNormasLaborales = null;
        normaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void valoresBackupAutocompletar(int tipoNuevo) {
        if (tipoNuevo == 1) {
            nuevoYduplicarCompletarNormaLaboral = nuevoEmplVigenciaNormaLaboral.getNormalaboral().getNombre();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarNormaLaboral = duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre();
        }
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {

            if (!nuevoEmplVigenciaNormaLaboral.getNormalaboral().getNombre().equals("")) {
                nuevoEmplVigenciaNormaLaboral.getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                getListaNormasLaborales();
                for (int i = 0; i < listaNormasLaborales.size(); i++) {
                    if (listaNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    nuevoEmplVigenciaNormaLaboral.setNormalaboral(listaNormasLaborales.get(indiceUnicoElemento));
                    listaNormasLaborales = null;
                    getListaNormasLaborales();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoEmplVigenciaNormaLaboral.getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
                nuevoEmplVigenciaNormaLaboral.getNormalaboral().setNombre(" ");
            }
            context.update("formularioDialogos:nuevoNombreSucursal");
        }

    }

    public void asignarVariableNormasLaborales(int tipoNuevo) {
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

    public void limpiarNuevaNormaLaboral() {
        try {
            nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETAEMPLVIGENCIANORMALABORAL LIMPIAR NUEVO NORMA LABORAL ERROR :" + e.getMessage());
        }
    }

    public void cargarNormasLaboralesNuevoYDuplicar(int tipoNuevo) {
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
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {

            if (!duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().equals("") || !duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().isEmpty()) {
                duplicarEmplVigenciaNormaLaboral.getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                for (int i = 0; i < listaNormasLaborales.size(); i++) {
                    if (listaNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    duplicarEmplVigenciaNormaLaboral.setNormalaboral(listaNormasLaborales.get(indiceUnicoElemento));
                    listaNormasLaborales = null;
                    getListaNormasLaborales();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarEmplVigenciaNormaLaboral.getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                    duplicarEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
                    duplicarEmplVigenciaNormaLaboral.getNormalaboral().setNombre(" ");

                    if (tipoLista == 0) {
                        listEmplVigenciaNormaLaboralPorEmpleado.get(index).getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                    } else if (tipoLista == 1) {
                        filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);
                    }

                }

            }
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
    }

    public void borrandoHvEntrevistas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty() && modificarEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(index))) {
                    int modIndex = modificarEmplVigenciaNormaLaboralPorEmplado.indexOf(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                    modificarEmplVigenciaNormaLaboralPorEmplado.remove(modIndex);
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                } else if (!crearEmplVigenciaNormaLaboralPorEmplado.isEmpty() && crearEmplVigenciaNormaLaboralPorEmplado.contains(listEmplVigenciaNormaLaboralPorEmpleado.get(index))) {
                    int crearIndex = crearEmplVigenciaNormaLaboralPorEmplado.indexOf(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                    crearEmplVigenciaNormaLaboralPorEmplado.remove(crearIndex);
                } else {
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(listEmplVigenciaNormaLaboralPorEmpleado.get(index));
                }
                listEmplVigenciaNormaLaboralPorEmpleado.remove(index);
            }
            if (tipoLista == 1) {
                if (!modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty() && modificarEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index))) {
                    int modIndex = modificarEmplVigenciaNormaLaboralPorEmplado.indexOf(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                    modificarEmplVigenciaNormaLaboralPorEmplado.remove(modIndex);
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                } else if (!crearEmplVigenciaNormaLaboralPorEmplado.isEmpty() && crearEmplVigenciaNormaLaboralPorEmplado.contains(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index))) {
                    int crearIndex = crearEmplVigenciaNormaLaboralPorEmplado.indexOf(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                    crearEmplVigenciaNormaLaboralPorEmplado.remove(crearIndex);
                } else {
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                }
                int VCIndex = listEmplVigenciaNormaLaboralPorEmpleado.indexOf(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index));
                listEmplVigenciaNormaLaboralPorEmpleado.remove(VCIndex);
                filtrarEmplVigenciaNormaLaboralPorEmplado.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosHvEntrevista");
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
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

        if (!borrarEmplVigenciaNormaLaboralPorEmplado.isEmpty() || !crearEmplVigenciaNormaLaboralPorEmplado.isEmpty() || !modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEmplVigenciaNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!borrarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                administrarVigenciaNormaLaboral.borrarVigenciaNormaLaboral(borrarEmplVigenciaNormaLaboralPorEmplado);
                //mostrarBorrados
                registrosBorrados = borrarEmplVigenciaNormaLaboralPorEmplado.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEmplVigenciaNormaLaboralPorEmplado.clear();
            }
            if (!crearEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                administrarVigenciaNormaLaboral.crearVigenciaNormaLaboral(crearEmplVigenciaNormaLaboralPorEmplado);
                crearEmplVigenciaNormaLaboralPorEmplado.clear();
            }
            if (!modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                administrarVigenciaNormaLaboral.modificarVigenciaNormaLaboral(modificarEmplVigenciaNormaLaboralPorEmplado);
                modificarEmplVigenciaNormaLaboralPorEmplado.clear();
            }
            listEmplVigenciaNormaLaboralPorEmpleado = null;
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
                editarEmplVigenciaNormaLaboral = listEmplVigenciaNormaLaboralPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarEmplVigenciaNormaLaboral = filtrarEmplVigenciaNormaLaboralPorEmplado.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
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

    public void agregarNuevoEmplVigenciaNormaLaboral() {
        int contador = 0;
        //nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
        Short a = 0;
        a = null;
        int fechas = 0;
        mensajeValidacion = " ";
        nuevoEmplVigenciaNormaLaboral.setEmpleado(empleadoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEmplVigenciaNormaLaboral.getFechavigencia() == null || nuevoEmplVigenciaNormaLaboral.getFechavigencia().equals("")) {
            mensajeValidacion = " *Fecha \n";
        } else {
            for (int i = 0; i < listEmplVigenciaNormaLaboralPorEmpleado.size(); i++) {
                if (nuevoEmplVigenciaNormaLaboral.getFechavigencia().equals(listEmplVigenciaNormaLaboralPorEmpleado.get(i).getFechavigencia())) {
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "Fechas repetidas ";
            } else {
                contador++;
            }
        }
        if (nuevoEmplVigenciaNormaLaboral.getNormalaboral().getSecuencia() == null || nuevoEmplVigenciaNormaLaboral.getNormalaboral().getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   *Norma laboral\n";

        } else {
            contador++;
        }

        if (contador == 2) {
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
                filtrarEmplVigenciaNormaLaboralPorEmplado = null;
                tipoLista = 0;
            }

            k++;
            l = BigInteger.valueOf(k);
            nuevoEmplVigenciaNormaLaboral.setSecuencia(l);
            crearEmplVigenciaNormaLaboralPorEmplado.add(nuevoEmplVigenciaNormaLaboral);
            listEmplVigenciaNormaLaboralPorEmpleado.add(nuevoEmplVigenciaNormaLaboral);
            nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
            context.update("form:datosHvEntrevista");
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
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

    public void limpiarNuevoEmplVigenciaNormaLaboral() {
        nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoEmplVigenciaNormaLaboral() {
        if (index >= 0) {
            duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            duplicarEmplVigenciaNormaLaboral.setEmpleado(new Empleados());
            duplicarEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEmplVigenciaNormaLaboral.setSecuencia(l);
                duplicarEmplVigenciaNormaLaboral.setEmpleado(listEmplVigenciaNormaLaboralPorEmpleado.get(index).getEmpleado());
                duplicarEmplVigenciaNormaLaboral.setFechavigencia(listEmplVigenciaNormaLaboralPorEmpleado.get(index).getFechavigencia());
                duplicarEmplVigenciaNormaLaboral.setNormalaboral(listEmplVigenciaNormaLaboralPorEmpleado.get(index).getNormalaboral());
            }
            if (tipoLista == 1) {
                duplicarEmplVigenciaNormaLaboral.setSecuencia(l);
                duplicarEmplVigenciaNormaLaboral.setEmpleado(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getEmpleado());
                duplicarEmplVigenciaNormaLaboral.setFechavigencia(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getFechavigencia());
                duplicarEmplVigenciaNormaLaboral.setNormalaboral(filtrarEmplVigenciaNormaLaboralPorEmplado.get(index).getNormalaboral());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvC");
            context.execute("duplicarRegistroEvalCompetencias.show()");
            //index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        int fechas = 0;
        a = null;
        if (duplicarEmplVigenciaNormaLaboral.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
        } else {

            for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                if (duplicarEmplVigenciaNormaLaboral.getFechavigencia().equals(listEmplVigenciaNormaLaboralPorEmpleado.get(j).getFechavigencia())) {
                    fechas++;
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "FECHAS REPETIDAS";
            } else {
                contador++;
            }

        }
        if (duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre() == null || duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().isEmpty() || duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().equals(" ") || duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Norma Laboral \n";
        } else {
            contador++;

        }
        if (duplicarEmplVigenciaNormaLaboral.getEmpleado().getSecuencia() == null) {
            duplicarEmplVigenciaNormaLaboral.setEmpleado(empleadoSeleccionado);
        }
        if (contador == 2) {
            listEmplVigenciaNormaLaboralPorEmpleado.add(duplicarEmplVigenciaNormaLaboral);
            crearEmplVigenciaNormaLaboralPorEmplado.add(duplicarEmplVigenciaNormaLaboral);
            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
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
                filtrarEmplVigenciaNormaLaboralPorEmplado = null;
                tipoLista = 0;
            }
            duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            fechas = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void limpiarDuplicarEmplVigenciaNormaLaboral() {
        duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        duplicarEmplVigenciaNormaLaboral.setEmpleado(new Empleados());
        duplicarEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
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
        if (!listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASNORMASEMPLEADOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASNORMASEMPLEADOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<VigenciasNormasEmpleados> getListEmplVigenciaNormaLaboralPorEmpleado() {
        if (listEmplVigenciaNormaLaboralPorEmpleado == null) {
            listEmplVigenciaNormaLaboralPorEmpleado = administrarVigenciaNormaLaboral.consultarVigenciasNormasEmpleadosPorEmpleado(secuenciaEmpleado);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listEmplVigenciaNormaLaboralPorEmpleado == null || listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
        }
        context.update("form:informacionRegistro");
        return listEmplVigenciaNormaLaboralPorEmpleado;
    }

    public void setListEmplVigenciaNormaLaboralPorEmpleado(List<VigenciasNormasEmpleados> listEmplVigenciaNormaLaboralPorEmpleado) {
        this.listEmplVigenciaNormaLaboralPorEmpleado = listEmplVigenciaNormaLaboralPorEmpleado;
    }

    public List<VigenciasNormasEmpleados> getFiltrarEmplVigenciaNormaLaboralPorEmplado() {
        return filtrarEmplVigenciaNormaLaboralPorEmplado;
    }

    public void setFiltrarEmplVigenciaNormaLaboralPorEmplado(List<VigenciasNormasEmpleados> filtrarEmplVigenciaNormaLaboralPorEmplado) {
        this.filtrarEmplVigenciaNormaLaboralPorEmplado = filtrarEmplVigenciaNormaLaboralPorEmplado;
    }

    public VigenciasNormasEmpleados getNuevoEmplVigenciaNormaLaboral() {
        return nuevoEmplVigenciaNormaLaboral;
    }

    public void setNuevoEmplVigenciaNormaLaboral(VigenciasNormasEmpleados nuevoEmplVigenciaNormaLaboral) {
        this.nuevoEmplVigenciaNormaLaboral = nuevoEmplVigenciaNormaLaboral;
    }

    public VigenciasNormasEmpleados getDuplicarEmplVigenciaNormaLaboral() {
        return duplicarEmplVigenciaNormaLaboral;
    }

    public void setDuplicarEmplVigenciaNormaLaboral(VigenciasNormasEmpleados duplicarEmplVigenciaNormaLaboral) {
        this.duplicarEmplVigenciaNormaLaboral = duplicarEmplVigenciaNormaLaboral;
    }

    public VigenciasNormasEmpleados getEditarEmplVigenciaNormaLaboral() {
        return editarEmplVigenciaNormaLaboral;
    }

    public void setEditarEmplVigenciaNormaLaboral(VigenciasNormasEmpleados editarEmplVigenciaNormaLaboral) {
        this.editarEmplVigenciaNormaLaboral = editarEmplVigenciaNormaLaboral;
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
            empleadoSeleccionado = administrarVigenciaNormaLaboral.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    private String infoRecursoNormasLaborales;

    public List<NormasLaborales> getListaNormasLaborales() {
        if (listaNormasLaborales == null) {
            listaNormasLaborales = administrarVigenciaNormaLaboral.lovNormasLaborales();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaNormasLaborales == null || listaNormasLaborales.isEmpty()) {
            infoRecursoNormasLaborales = "Cantidad de registros: 0 ";
        } else {
            infoRecursoNormasLaborales = "Cantidad de registros: " + listaNormasLaborales.size();
        }
        context.update("form:infoRecursoNormasLaborales");
        return listaNormasLaborales;
    }

    public void setListaNormasLaborales(List<NormasLaborales> listaNormasLaborales) {
        this.listaNormasLaborales = listaNormasLaborales;
    }

    public List<NormasLaborales> getFiltradoNormasLaborales() {
        return filtradoNormasLaborales;
    }

    public void setFiltradoNormasLaborales(List<NormasLaborales> filtradoNormasLaborales) {
        this.filtradoNormasLaborales = filtradoNormasLaborales;
    }

    public NormasLaborales getNormaLaboralSeleccionada() {
        return normaLaboralSeleccionada;
    }

    public void setNormaLaboralSeleccionada(NormasLaborales normaLaboralSeleccionada) {
        this.normaLaboralSeleccionada = normaLaboralSeleccionada;
    }

    public VigenciasNormasEmpleados getVigenciaSeleccionada() {
        getListEmplVigenciaNormaLaboralPorEmpleado();
        if (listEmplVigenciaNormaLaboralPorEmpleado != null) {
            int tam = listEmplVigenciaNormaLaboralPorEmpleado.size();
            if (tam > 0) {
                vigenciaSeleccionada = listEmplVigenciaNormaLaboralPorEmpleado.get(0);
            }
        }
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasNormasEmpleados vigenciaSeleccionada) {
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

    public String getInfoRecursoNormasLaborales() {
        return infoRecursoNormasLaborales;
    }

    public void setInfoRecursoNormasLaborales(String infoRecursoNormasLaborales) {
        this.infoRecursoNormasLaborales = infoRecursoNormasLaborales;
    }

}

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
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
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
    private String infoRegistro;
    //
    private DataTable tablaC;
    //
    private boolean activarLOV;

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
        altoTabla = "292";
        aceptar = true;
        vigenciaSeleccionada = null;
        normaLaboralSeleccionada = null;
        activarLOV = true;
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
            System.out.println("ERROR EN RECIBIR LA SECUENCIA DEL EMPLEADO EN CONTROLBETAEMPLVIGENCIANORMALABORAL");
        }
        secuenciaEmpleado = sec;
        getEmpleadoSeleccionado();
        getListEmplVigenciaNormaLaboralPorEmpleado();
        contarRegistrosNorma();
        if (listEmplVigenciaNormaLaboralPorEmpleado != null) {
            vigenciaSeleccionada = listEmplVigenciaNormaLaboralPorEmpleado.get(0);
            modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    public void mostrarInfo(VigenciasNormasEmpleados vNorma, int celda) {
        int contador = 0;
        int fechas = 0;
        mensajeValidacion = " ";
        vigenciaSeleccionada = vNorma;
        cualCelda = celda;
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex) {
            if (vigenciaSeleccionada.getFechavigencia() == null) {
                mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                vigenciaSeleccionada.setFechavigencia(backUpFecha);
                contador++;
            } else {
                for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                    if (listEmplVigenciaNormaLaboralPorEmpleado.get(j) != vigenciaSeleccionada) {
                        if (vigenciaSeleccionada.getFechavigencia().equals(vigenciaSeleccionada.getFechavigencia())) {
                            fechas++;
                        }
                    }
                }
            }
            if (fechas > 0) {
                mensajeValidacion = "FECHAS REPETIDAS";
                vigenciaSeleccionada.setFechavigencia(backUpFecha);
                contador++;
            }
            if (contador == 0) {
                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                    if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    }
                    if (guardado) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                    context.update("form:datosVNL");

                }
            } else {
                context.update("form:validacionModificar");
                context.execute("validacionModificar.show()");
            }
        } else {
            if (vigenciaSeleccionada.getFechavigencia() == null) {
                vigenciaSeleccionada.setFechavigencia(backUpFecha);
                mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                contador++;
            }
            context.update("form:datosVNL");

        }
    }
    private Date backUpFecha;

    public void cambiarIndice(VigenciasNormasEmpleados vNorma, int celda) {
        if (permitirIndex) {
            vigenciaSeleccionada = vNorma;
            cualCelda = celda;
            if (cualCelda == 0) {
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                backUpFecha = vigenciaSeleccionada.getFechavigencia();
            }
            if (cualCelda == 1) {
                activarLOV = false;
                RequestContext.getCurrentInstance().update("form:listaValores");
                normaLaboral = vigenciaSeleccionada.getNormalaboral().getNombre();
            }
        }
    }

    public void asignarIndex(VigenciasNormasEmpleados vNorma, int LND, int dig) {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            vigenciaSeleccionada = vNorma;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 1) {
                activarLOV = false;
                RequestContext.getCurrentInstance().update("form:listaValores");
                modificarInfoRegistroNorma(listaNormasLaborales.size());
                normaLaboralSeleccionada = null;
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (!listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
                if (vigenciaSeleccionada.getSecuencia() != null) {
                    if (vigenciaSeleccionada != null) {
                        if (cualCelda == 1) {
                            normaLaboralSeleccionada = null;
                            modificarInfoRegistroNorma(listaNormasLaborales.size());
                            context.update("form:sucursalesDialogo");
                            context.execute("sucursalesDialogo.show()");
                        }
                    }
                } else {
                    context.execute("seleccionarRegistro.show()");
                }
            }
        }
    }

    public void cancelarModificacion() {
        cerrarFiltrado();
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        borrarEmplVigenciaNormaLaboralPorEmplado.clear();
        crearEmplVigenciaNormaLaboralPorEmplado.clear();
        modificarEmplVigenciaNormaLaboralPorEmplado.clear();
        k = 0;
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        getListEmplVigenciaNormaLaboralPorEmpleado();
        if (listEmplVigenciaNormaLaboralPorEmpleado != null) {
            modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
        vigenciaSeleccionada = null;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:datosVNL");
        context.update("form:ACEPTAR");
        context.update("form:informacionRegistro");
    }

    private void cerrarFiltrado() {
        FacesContext c = FacesContext.getCurrentInstance();
        //CERRAR FILTRADO
        fecha = (Column) c.getViewRoot().findComponent("form:datosVNL:fecha");
        fecha.setFilterStyle("display: none; visibility: hidden;");
        parentesco = (Column) c.getViewRoot().findComponent("form:datosVNL:parentesco");
        parentesco.setFilterStyle("display: none; visibility: hidden;");
        altoTabla = "292";
        RequestContext.getCurrentInstance().update("form:datosVNL");
        bandera = 0;
        filtrarEmplVigenciaNormaLaboralPorEmplado = null;
        tipoLista = 0;
    }

    public void salir() {
        cerrarFiltrado();
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        borrarEmplVigenciaNormaLaboralPorEmplado.clear();
        crearEmplVigenciaNormaLaboralPorEmplado.clear();
        modificarEmplVigenciaNormaLaboralPorEmplado.clear();
        vigenciaSeleccionada = null;
        k = 0;
        listEmplVigenciaNormaLaboralPorEmpleado = null;
        guardado = true;
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            fecha = (Column) c.getViewRoot().findComponent("form:datosVNL:fecha");
            fecha.setFilterStyle("width: 60px");
            parentesco = (Column) c.getViewRoot().findComponent("form:datosVNL:parentesco");
            parentesco.setFilterStyle("width: 600px");
            altoTabla = "268";
            RequestContext.getCurrentInstance().update("form:datosVNL");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    public void modificandoEmplVigenciaNormaLaboral(VigenciasNormasEmpleados vNorma, String confirmarCambio, String valorConfirmar) {
        vigenciaSeleccionada = vNorma;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                if (vigenciaSeleccionada.getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                } else {
                    for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                        if (listEmplVigenciaNormaLaboralPorEmpleado.get(j) != vNorma) {
                            if (vigenciaSeleccionada.getFechavigencia().equals(vigenciaSeleccionada.getFechavigencia())) {
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
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    }
                    if (guardado) {
                        guardado = false;
                    }

                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            }

            context.update("form:datosVNL");
            context.update("form:ACEPTAR");
        } else if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {
            activarLOV = false;
            RequestContext.getCurrentInstance().update("form:listaValores");
            if (!vigenciaSeleccionada.getNormalaboral().getNombre().equals("")) {
                vigenciaSeleccionada.getNormalaboral().setNombre(normaLaboral);


                for (int i = 0; i < listaNormasLaborales.size(); i++) {
                    if (listaNormasLaborales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    vigenciaSeleccionada.setNormalaboral(listaNormasLaborales.get(indiceUnicoElemento));
                    listaNormasLaborales.clear();
                    listaNormasLaborales = null;

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                vigenciaSeleccionada.getNormalaboral().setNombre(normaLaboral);
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                    if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                        modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                    }
                    if (guardado) {
                        guardado = false;
                    }
                }
            }
            context.update("form:datosVNL");
            context.update("form:ACEPTAR");
        }
    }

    public void actualizarNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            vigenciaSeleccionada.setNormalaboral(normaLaboralSeleccionada);

            if (!crearEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                if (modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty()) {
                    modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                } else if (!modificarEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                    modificarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                }
            }

            if (guardado) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVNL");
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
        tipoActualizacion = -1;
        cualCelda = -1;
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.execute("lovTiposFamiliares.clearFilters()");
        context.execute("sucursalesDialogo.hide()");
        //context.update("form:datosVNL");
        context.update("form:sucursalesDialogo");
    }

    public void cancelarCambioNormaLaboral() {
        filtradoNormasLaborales = null;
        normaLaboralSeleccionada = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.execute("lovTiposFamiliares.clearFilters()");
        context.execute("sucursalesDialogo.hide()");
        context.update("form:sucursalesDialogo");
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
        normaLaboralSeleccionada = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:sucursalesDialogo");
        context.execute("sucursalesDialogo.show()");
    }

    public void limpiarNuevaNormaLaboral() {
        try {
            nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
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
                    vigenciaSeleccionada.getNormalaboral().setNombre(nuevoYduplicarCompletarNormaLaboral);

                }

            }
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
    }

    public void borrarNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (vigenciaSeleccionada != null) {
                if (!modificarEmplVigenciaNormaLaboralPorEmplado.isEmpty() && modificarEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                    int modIndex = modificarEmplVigenciaNormaLaboralPorEmplado.indexOf(vigenciaSeleccionada);
                    modificarEmplVigenciaNormaLaboralPorEmplado.remove(modIndex);
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                } else if (!crearEmplVigenciaNormaLaboralPorEmplado.isEmpty() && crearEmplVigenciaNormaLaboralPorEmplado.contains(vigenciaSeleccionada)) {
                    int crearIndex = crearEmplVigenciaNormaLaboralPorEmplado.indexOf(vigenciaSeleccionada);
                    crearEmplVigenciaNormaLaboralPorEmplado.remove(crearIndex);
                } else {
                    borrarEmplVigenciaNormaLaboralPorEmplado.add(vigenciaSeleccionada);
                }
                listEmplVigenciaNormaLaboralPorEmpleado.remove(vigenciaSeleccionada);
                if (tipoLista == 1) {
                    filtrarEmplVigenciaNormaLaboralPorEmplado.remove(vigenciaSeleccionada);
                }

                if (guardado) {
                    guardado = false;
                }
                context.update("form:datosVNL");
                modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                context.update("form:informacionRegistro");
                context.update("form:ACEPTAR");
                vigenciaSeleccionada = null;

            }
        }
    }

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
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            getListEmplVigenciaNormaLaboralPorEmpleado();
            contarRegistrosNorma();
            context.update("form:datosVNL");
            k = 0;
            guardado = true;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        //vigenciaSeleccionada = null;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (vigenciaSeleccionada != null) {
                editarEmplVigenciaNormaLaboral = vigenciaSeleccionada;
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
        }
    }

    public void agregarNuevoEmplVigenciaNormaLaboral() {
        int contador = 0;
        int fechas = 0;
        mensajeValidacion = " ";
        nuevoEmplVigenciaNormaLaboral.setEmpleado(empleadoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEmplVigenciaNormaLaboral.getFechavigencia() == null || nuevoEmplVigenciaNormaLaboral.getFechavigencia().equals("")) {
            mensajeValidacion = " *Fecha \n";
        } else {
            for (int i = 0; i < listEmplVigenciaNormaLaboralPorEmpleado.size(); i++) {
                if (nuevoEmplVigenciaNormaLaboral.getFechavigencia().equals(vigenciaSeleccionada.getFechavigencia())) {
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
                cerrarFiltrado();
            }

            k++;
            l = BigInteger.valueOf(k);
            nuevoEmplVigenciaNormaLaboral.setSecuencia(l);
            crearEmplVigenciaNormaLaboralPorEmplado.add(nuevoEmplVigenciaNormaLaboral);
            listEmplVigenciaNormaLaboralPorEmpleado.add(nuevoEmplVigenciaNormaLaboral);
            modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
            vigenciaSeleccionada = listEmplVigenciaNormaLaboralPorEmpleado.get(listEmplVigenciaNormaLaboralPorEmpleado.indexOf(nuevoEmplVigenciaNormaLaboral));
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
            nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
            context.update("form:datosVNL");
            //infoRegistro = "Cantidad de registros: " + listEmplVigenciaNormaLaboralPorEmpleado.size();
            context.update("form:informacionRegistro");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");
        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEmplVigenciaNormaLaboral() {
        nuevoEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        nuevoEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
    }

    //------------------------------------------------------------------------------
    public void duplicandoEmplVigenciaNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada == null) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (vigenciaSeleccionada != null) {
                duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
                duplicarEmplVigenciaNormaLaboral.setEmpleado(new Empleados());
                duplicarEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
                k++;
                l = BigInteger.valueOf(k);
                duplicarEmplVigenciaNormaLaboral.setSecuencia(l);
                duplicarEmplVigenciaNormaLaboral.setEmpleado(vigenciaSeleccionada.getEmpleado());
                duplicarEmplVigenciaNormaLaboral.setFechavigencia(vigenciaSeleccionada.getFechavigencia());
                duplicarEmplVigenciaNormaLaboral.setNormalaboral(vigenciaSeleccionada.getNormalaboral());

                context.update("formularioDialogos:duplicarEvC");
                context.execute("duplicarRegistroEvalCompetencias.show()");
            }
        }
    }

    public void confirmarDuplicar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listEmplVigenciaNormaLaboralPorEmpleado.isEmpty()) {
            int contador = 0;
            mensajeValidacion = " ";
            //Short a = 0;
            int fechas = 0;
            //a = null;
            if (duplicarEmplVigenciaNormaLaboral.getFechavigencia() == null) {
                mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            } else {

                for (int j = 0; j < listEmplVigenciaNormaLaboralPorEmpleado.size(); j++) {
                    if (duplicarEmplVigenciaNormaLaboral.getFechavigencia().equals(vigenciaSeleccionada.getFechavigencia())) {
                        fechas++;
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "FECHAS NO REPETIDAS";
                } else {
                    contador++;
                }

            }
            if (duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre() == null || duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().equals(" ") || duplicarEmplVigenciaNormaLaboral.getNormalaboral().getNombre().isEmpty()) {
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
                modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
                vigenciaSeleccionada = listEmplVigenciaNormaLaboralPorEmpleado.get(listEmplVigenciaNormaLaboralPorEmpleado.indexOf(duplicarEmplVigenciaNormaLaboral));
                //vigenciaSeleccionada = null;
                if (guardado) {
                    guardado = false;
                }
                if (bandera == 1) {
                    cerrarFiltrado();
                }
                duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                context.update("form:datosVNL");
                context.update("form:informacionRegistro");
                RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

            } else {
                contador = 0;
                fechas = 0;
                context.update("form:validacionDuplicarVigencia");
                context.execute("validacionDuplicarVigencia.show()");
            }
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }

    public void anularLOV() {
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void limpiarDuplicarEmplVigenciaNormaLaboral() {
        duplicarEmplVigenciaNormaLaboral = new VigenciasNormasEmpleados();
        duplicarEmplVigenciaNormaLaboral.setEmpleado(new Empleados());
        duplicarEmplVigenciaNormaLaboral.setNormalaboral(new NormasLaborales());
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVNLExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVNLExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASNORMASLABORALES", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (vigenciaSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(vigenciaSeleccionada.getSecuencia(), "VIGENCIASNORMASEMPLEADOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASNORMASEMPLEADOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        modificarInfoRegistro(filtrarEmplVigenciaNormaLaboralPorEmplado.size());
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
    }

    public void eventoFiltrarNorma() {
        modificarInfoRegistroNorma(filtradoNormasLaborales.size());
        RequestContext.getCurrentInstance().update("form:infoRecursoNormasLaborales");
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        System.out.println("infoRegistro: " + infoRegistro);
    }

    private void modificarInfoRegistroNorma(int valor) {
        infoRecursoNormasLaborales = String.valueOf(valor);
    }

    public void contarRegistrosNorma() {
        if (listEmplVigenciaNormaLaboralPorEmpleado != null) {
            modificarInfoRegistro(listEmplVigenciaNormaLaboralPorEmpleado.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    public void recordarSeleccion() {
        if (vigenciaSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosVNL");
            tablaC.setSelection(vigenciaSeleccionada);
        }
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<VigenciasNormasEmpleados> getListEmplVigenciaNormaLaboralPorEmpleado() {

        if (listEmplVigenciaNormaLaboralPorEmpleado == null) {
            listEmplVigenciaNormaLaboralPorEmpleado = administrarVigenciaNormaLaboral.consultarVigenciasNormasEmpleadosPorEmpleado(secuenciaEmpleado);

        }
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

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }
}

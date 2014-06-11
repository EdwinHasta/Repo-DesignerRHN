/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Tiposviajeros;
import Entidades.VigenciasViajeros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasViajerosInterface;
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
public class ControlVigenciasViajeros implements Serializable {

    @EJB
    AdministrarVigenciasViajerosInterface administrarVigenciasViajeros;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<VigenciasViajeros> listVigenciasViajerosPorEmpleado;
    private List<VigenciasViajeros> filtrarVigenciasViajerosPorEmplado;
    private List<VigenciasViajeros> crearVigenciasViajerosPorEmplado;
    private List<VigenciasViajeros> modificarVigenciasViajerosPorEmplado;
    private List<VigenciasViajeros> borrarVigenciasViajerosPorEmplado;
    private VigenciasViajeros nuevoVigenciasViajeros;
    private VigenciasViajeros duplicarVigenciasViajeros;
    private VigenciasViajeros editarVigenciasViajeros;
    private VigenciasViajeros vigenciaSeleccionada;
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
    private List<Tiposviajeros> listaTiposviajeros;
    private List<Tiposviajeros> filtradoTiposviajeros;
    private Tiposviajeros normaLaboralSeleccionada;
    private String nuevoYduplicarCompletarNormaLaboral;
    private String altoTabla;
    public String infoRegistro;

    public ControlVigenciasViajeros() {
        listVigenciasViajerosPorEmpleado = null;
        crearVigenciasViajerosPorEmplado = new ArrayList<VigenciasViajeros>();
        modificarVigenciasViajerosPorEmplado = new ArrayList<VigenciasViajeros>();
        borrarVigenciasViajerosPorEmplado = new ArrayList<VigenciasViajeros>();
        permitirIndex = true;
        editarVigenciasViajeros = new VigenciasViajeros();
        nuevoVigenciasViajeros = new VigenciasViajeros();
        nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());
        duplicarVigenciasViajeros = new VigenciasViajeros();
        empleadoSeleccionado = null;
        secuenciaEmpleado = null;
        listaTiposviajeros = null;
        filtradoTiposviajeros = null;
        guardado = true;
        altoTabla = "270";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasViajeros.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN CONTROLBETAEMPLVIGENCIANORMALABORAL");
        }
        secuenciaEmpleado = sec;
        empleadoSeleccionado = null;
        listVigenciasViajerosPorEmpleado = null;
        getEmpleadoSeleccionado();
        getListVigenciasViajerosPorEmpleado();
        //INICIALIZAR BOTONES NAVEGACION
        if (listVigenciasViajerosPorEmpleado != null && !listVigenciasViajerosPorEmpleado.isEmpty()) {
            if (listVigenciasViajerosPorEmpleado.size() == 1) {
                //INFORMACION REGISTRO
                vigenciaSeleccionada = listVigenciasViajerosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (listVigenciasViajerosPorEmpleado.size() > 1) {
                //INFORMACION REGISTRO
                vigenciaSeleccionada = listVigenciasViajerosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
            }
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");

    }

    public void mostrarNuevo() {
        System.err.println("NUEVA FECHA : " + nuevoVigenciasViajeros.getFechavigencia());
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
                secRegistro = listVigenciasViajerosPorEmpleado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listVigenciasViajerosPorEmpleado.get(indice).getFechavigencia());
                if (listVigenciasViajerosPorEmpleado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < listVigenciasViajerosPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (listVigenciasViajerosPorEmpleado.get(indice).getFechavigencia().equals(listVigenciasViajerosPorEmpleado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    context.update("form:validacionFechas");
                    context.execute("validacionFechas.show()");
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");
                        }
                        context.update("form:datosHvEntrevista");

                    }
                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                    cancelarModificacion();
                }
            } else {

                secRegistro = filtrarVigenciasViajerosPorEmplado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + filtrarVigenciasViajerosPorEmplado.get(indice).getFechavigencia());
                if (filtrarVigenciasViajerosPorEmplado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < filtrarVigenciasViajerosPorEmplado.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciasViajerosPorEmplado.get(indice).getFechavigencia().equals(filtrarVigenciasViajerosPorEmplado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                    for (int j = 0; j < listVigenciasViajerosPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (filtrarVigenciasViajerosPorEmplado.get(indice).getFechavigencia().equals(listVigenciasViajerosPorEmpleado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                }
                if (fechas > 0) {
                    context.update("form:validacionFechas");
                    context.execute("validacionFechas.show()");
                    contador++;
                }
                if (contador == 0) {
                    if (!crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");
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
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de Registros: " + filtrarVigenciasViajerosPorEmplado.size();
        context.update("form:informacionRegistro");
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listVigenciasViajerosPorEmpleado.get(index).getSecuencia();
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    normaLaboral = listVigenciasViajerosPorEmpleado.get(index).getTipoViajero().getNombre();
                } else {
                    normaLaboral = listVigenciasViajerosPorEmpleado.get(index).getTipoViajero().getNombre();
                }
            }
            System.out.println("NORMA LABORAL : " + normaLaboral);
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

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarVigenciasViajerosPorEmplado = null;
            tipoLista = 0;
        }

        borrarVigenciasViajerosPorEmplado.clear();
        crearVigenciasViajerosPorEmplado.clear();
        modificarVigenciasViajerosPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasViajerosPorEmpleado = null;
        getListVigenciasViajerosPorEmpleado();
        if (listVigenciasViajerosPorEmpleado != null && !listVigenciasViajerosPorEmpleado.isEmpty()) {
            vigenciaSeleccionada = listVigenciasViajerosPorEmpleado.get(0);
            infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }

        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");

        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("width: 60px");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("width: 600px");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarVigenciasViajerosPorEmplado = null;
            tipoLista = 0;
        }
    }

    public void modificandoVigenciasViajeros(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EMPL VIGENCIA NORMA LABORAL");
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
                if (!crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                    if (listVigenciasViajerosPorEmpleado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listVigenciasViajerosPorEmpleado.size(); j++) {
                            if (j != indice) {
                                if (listVigenciasViajerosPorEmpleado.get(indice).getFechavigencia().equals(listVigenciasViajerosPorEmpleado.get(j).getFechavigencia())) {
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
                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");
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

                if (!crearVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(indice))) {
                    if (filtrarVigenciasViajerosPorEmplado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");
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
        } else if (confirmarCambio.equalsIgnoreCase("VIGENCIASVIAJEROS")) {
            System.out.println("MODIFICANDO VIGENCIAS VIAJEROS : " + listVigenciasViajerosPorEmpleado.get(indice).getTipoViajero().getNombre());
            if (!listVigenciasViajerosPorEmpleado.get(indice).getTipoViajero().getNombre().equals("")) {
                if (tipoLista == 0) {
                    listVigenciasViajerosPorEmpleado.get(indice).getTipoViajero().setNombre(normaLaboral);
                } else {
                    listVigenciasViajerosPorEmpleado.get(indice).getTipoViajero().setNombre(normaLaboral);
                }

                for (int i = 0; i < listaTiposviajeros.size(); i++) {
                    if (listaTiposviajeros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listVigenciasViajerosPorEmpleado.get(indice).setTipoViajero(listaTiposviajeros.get(indiceUnicoElemento));
                    } else {
                        filtrarVigenciasViajerosPorEmplado.get(indice).setTipoViajero(listaTiposviajeros.get(indiceUnicoElemento));
                    }
                    listaTiposviajeros.clear();
                    listaTiposviajeros = null;
                    //getListaTiposFamiliares();

                } else {
                    permitirIndex = false;
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                if (tipoLista == 0) {
                    listVigenciasViajerosPorEmpleado.get(index).getTipoViajero().setNombre(normaLaboral);
                } else {
                    filtrarVigenciasViajerosPorEmplado.get(index).getTipoViajero().setNombre(normaLaboral);
                }
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {

                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(indice));
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(indice))) {

                        if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                            modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(indice));
                        } else if (!modificarVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(indice))) {
                            modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(indice));
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                context.update("form:datosHvEntrevista");
            }
        }

    }

    public void actualizarNormaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasViajerosPorEmpleado.get(index).setTipoViajero(normaLaboralSeleccionada);

                if (!crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(index))) {
                    if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                        modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(index));
                    } else if (!modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(index))) {
                        modificarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarVigenciasViajerosPorEmplado.get(index).setTipoViajero(normaLaboralSeleccionada);

                if (!crearVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(index))) {
                    if (modificarVigenciasViajerosPorEmplado.isEmpty()) {
                        modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(index));
                    } else if (!modificarVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(index))) {
                        modificarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosHvEntrevista");
        } else if (tipoActualizacion == 1) {
            System.out.println("ACTUALIZARNORMA LABORAR NUEVA NORMA LABORAL: " + normaLaboralSeleccionada.getNombre());
            nuevoVigenciasViajeros.setTipoViajero(normaLaboralSeleccionada);
            context.update("formularioDialogos:nuevoNombreSucursal");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZARNORMA LABORAR DUPLICAR NORMA LABORAL: " + normaLaboralSeleccionada.getNombre());
            duplicarVigenciasViajeros.setTipoViajero(normaLaboralSeleccionada);
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
        filtradoTiposviajeros = null;
        normaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("sucursalesDialogo.hide()");
        context.reset("form:lovTiposFamiliares:globalFilter");
        context.update("form:lovTiposFamiliares");
        //context.update("form:datosHvEntrevista");
    }

    public void cancelarCambioNormaLaboral() {
        filtradoTiposviajeros = null;
        normaLaboralSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void valoresBackupAutocompletar(int tipoNuevo) {
        System.out.println("1...");
        if (tipoNuevo == 1) {
            nuevoYduplicarCompletarNormaLaboral = nuevoVigenciasViajeros.getTipoViajero().getNombre();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarNormaLaboral = duplicarVigenciasViajeros.getTipoViajero().getNombre();
        }
    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("VIGENCIASVIAJEROS")) {
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoVigenciasViajeros.getTipoViajero().getNombre());

            if (!nuevoVigenciasViajeros.getTipoViajero().getNombre().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCiudadCompletar: " + nuevoYduplicarCompletarNormaLaboral);
                nuevoVigenciasViajeros.getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
                getListaTiposviajeros();
                for (int i = 0; i < listaTiposviajeros.size(); i++) {
                    if (listaTiposviajeros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoVigenciasViajeros.setTipoViajero(listaTiposviajeros.get(indiceUnicoElemento));
                    listaTiposviajeros = null;
                    getListaTiposviajeros();
                    System.err.println("NORMA LABORAL GUARDADA " + nuevoVigenciasViajeros.getTipoViajero().getNombre());
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoVigenciasViajeros.getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());
                nuevoVigenciasViajeros.getTipoViajero().setNombre(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoVigenciasViajeros.getTipoViajero().getNombre());
            }
            context.update("formularioDialogos:nuevoNombreSucursal");
        }
    }

    public void asignarVariableTiposviajeros(int tipoNuevo) {
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
            System.out.println("\n ENTRE A LIMPIAR NUEVO NORMA LABORAL \n");
            nuevoVigenciasViajeros = new VigenciasViajeros();
            nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETAEMPLVIGENCIANORMALABORAL LIMPIAR NUEVO NORMA LABORAL ERROR :" + e.getMessage());
        }
    }

    public void cargarTiposviajerosNuevoYDuplicar(int tipoNuevo) {
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
        if (confirmarCambio.equalsIgnoreCase("VIGENCIASVIAJEROS")) {
            System.out.println("DUPLICAR valorConfirmar : " + valorConfirmar);
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarNormaLaboral);

            if (!duplicarVigenciasViajeros.getTipoViajero().getNombre().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarNormaLaboral);
                duplicarVigenciasViajeros.getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
                for (int i = 0; i < listaTiposviajeros.size(); i++) {
                    if (listaTiposviajeros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarVigenciasViajeros.setTipoViajero(listaTiposviajeros.get(indiceUnicoElemento));
                    listaTiposviajeros = null;
                    getListaTiposviajeros();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarVigenciasViajeros.getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarVigenciasViajeros.setTipoViajero(new Tiposviajeros());
                    duplicarVigenciasViajeros.getTipoViajero().setNombre(" ");

                    System.out.println("DUPLICAR Valor NORMA LABORAL : " + duplicarVigenciasViajeros.getTipoViajero().getNombre());
                    if (tipoLista == 0) {
                        listVigenciasViajerosPorEmpleado.get(index).getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listVigenciasViajerosPorEmpleado.get(index).getTipoViajero().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarVigenciasViajerosPorEmplado.get(index).getTipoViajero().setNombre(nuevoYduplicarCompletarNormaLaboral);
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
                if (!modificarVigenciasViajerosPorEmplado.isEmpty() && modificarVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(index))) {
                    int modIndex = modificarVigenciasViajerosPorEmplado.indexOf(listVigenciasViajerosPorEmpleado.get(index));
                    modificarVigenciasViajerosPorEmplado.remove(modIndex);
                    borrarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(index));
                } else if (!crearVigenciasViajerosPorEmplado.isEmpty() && crearVigenciasViajerosPorEmplado.contains(listVigenciasViajerosPorEmpleado.get(index))) {
                    int crearIndex = crearVigenciasViajerosPorEmplado.indexOf(listVigenciasViajerosPorEmpleado.get(index));
                    crearVigenciasViajerosPorEmplado.remove(crearIndex);
                } else {
                    borrarVigenciasViajerosPorEmplado.add(listVigenciasViajerosPorEmpleado.get(index));
                }
                listVigenciasViajerosPorEmpleado.remove(index);
                infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarVigenciasViajerosPorEmplado.isEmpty() && modificarVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(index))) {
                    int modIndex = modificarVigenciasViajerosPorEmplado.indexOf(filtrarVigenciasViajerosPorEmplado.get(index));
                    modificarVigenciasViajerosPorEmplado.remove(modIndex);
                    borrarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(index));
                } else if (!crearVigenciasViajerosPorEmplado.isEmpty() && crearVigenciasViajerosPorEmplado.contains(filtrarVigenciasViajerosPorEmplado.get(index))) {
                    int crearIndex = crearVigenciasViajerosPorEmplado.indexOf(filtrarVigenciasViajerosPorEmplado.get(index));
                    crearVigenciasViajerosPorEmplado.remove(crearIndex);
                } else {
                    borrarVigenciasViajerosPorEmplado.add(filtrarVigenciasViajerosPorEmplado.get(index));
                }
                int VCIndex = listVigenciasViajerosPorEmpleado.indexOf(filtrarVigenciasViajerosPorEmplado.get(index));
                listVigenciasViajerosPorEmpleado.remove(VCIndex);
                filtrarVigenciasViajerosPorEmplado.remove(index);
                infoRegistro = "Cantidad de registros: " + filtrarVigenciasViajerosPorEmplado.size();

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            context.update("form:informacionRegistro");
            context.update("form:datosHvEntrevista");
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

        if (!borrarVigenciasViajerosPorEmplado.isEmpty() || !crearVigenciasViajerosPorEmplado.isEmpty() || !modificarVigenciasViajerosPorEmplado.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarVigenciasViajeros() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarVigenciasViajerosPorEmplado.isEmpty()) {
                for (int i = 0; i < borrarVigenciasViajerosPorEmplado.size(); i++) {
                    System.out.println("Borrando...");
                    administrarVigenciasViajeros.borrarVigenciasViajeros(borrarVigenciasViajerosPorEmplado);
                }
                //mostrarBorrados
                registrosBorrados = borrarVigenciasViajerosPorEmplado.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarVigenciasViajerosPorEmplado.clear();
            }
            if (!crearVigenciasViajerosPorEmplado.isEmpty()) {
                for (int i = 0; i < crearVigenciasViajerosPorEmplado.size(); i++) {
                    System.out.println("Creando...");
                    administrarVigenciasViajeros.crearVigenciasViajeros(crearVigenciasViajerosPorEmplado);

                }
                crearVigenciasViajerosPorEmplado.clear();
            }
            if (!modificarVigenciasViajerosPorEmplado.isEmpty()) {
                System.out.println("Modificando...");
                administrarVigenciasViajeros.modificarVigenciasViajeros(modificarVigenciasViajerosPorEmplado);
                modificarVigenciasViajerosPorEmplado.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listVigenciasViajerosPorEmpleado = null;
            getListVigenciasViajerosPorEmpleado();
            if (listVigenciasViajerosPorEmpleado != null && !listVigenciasViajerosPorEmpleado.isEmpty()) {
                vigenciaSeleccionada = listVigenciasViajerosPorEmpleado.get(0);
                infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }
            context.update("form:datosHvEntrevista");
            context.update("form:informacionRegistro");
            k = 0;
            guardado = true;
            context.update("form:ACEPTAR");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciasViajeros = listVigenciasViajerosPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciasViajeros = filtrarVigenciasViajerosPorEmplado.get(index);
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

    public void agregarNuevoVigenciasViajeros() {
        int contador = 0;
        //nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());
        Short a = 0;
        a = null;
        int fechas = 0;
        int pasa = 0;

        mensajeValidacion = " ";
        nuevoVigenciasViajeros.setEmpleado(empleadoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoVigenciasViajeros.getFechavigencia() == null || nuevoVigenciasViajeros.getFechavigencia().equals("")) {
            mensajeValidacion = " *Fecha\n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            if (listVigenciasViajerosPorEmpleado != null) {
                for (int i = 0; i < listVigenciasViajerosPorEmpleado.size(); i++) {
                    if (nuevoVigenciasViajeros.getFechavigencia().equals(listVigenciasViajerosPorEmpleado.get(i).getFechavigencia())) {
                        fechas++;
                    }
                }
            }
            if (fechas > 0) {
                context.update("form:validacionFechas");
                context.execute("validacionFechas.show()");
                pasa++;

            } else {
                contador++;
            }
        }
        if (nuevoVigenciasViajeros.getTipoViajero().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Tipo Viajero\n";

        } else {
            contador++;
        }
        /*if (nuevoHvEntrevista.getTipo() == (null)) {
         mensajeValidacion = mensajeValidacion + " *Debe tener un tipo entrevista \n";
         System.out.println("Mensaje validacion : " + mensajeValidacion);
         } else {
         System.out.println("bandera");
         contador++;
         }*/
        System.out.println("contador " + contador);

        if (contador == 2 && pasa == 0) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarVigenciasViajerosPorEmplado = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoVigenciasViajeros.setSecuencia(l);
            nuevoVigenciasViajeros.setEmpleado(empleadoSeleccionado);

            crearVigenciasViajerosPorEmplado.add(nuevoVigenciasViajeros);
            if (listVigenciasViajerosPorEmpleado == null) {
                listVigenciasViajerosPorEmpleado = new ArrayList<VigenciasViajeros>();
                listVigenciasViajerosPorEmpleado.add(nuevoVigenciasViajeros);
            } else {
                listVigenciasViajerosPorEmpleado.add(nuevoVigenciasViajeros);
            }
            infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
            context.update("form:informacionRegistro");
            nuevoVigenciasViajeros = new VigenciasViajeros();
            nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");

            index = -1;
            secRegistro = null;

        } else if (pasa == 0 && contador != 2) {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
            pasa = 0;
        }
        context.update("form:datosHvEntrevista");
    }

    public void limpiarNuevoVigenciasViajeros() {
        System.out.println("limpiarNuevoVigenciasViajeros");
        nuevoVigenciasViajeros = new VigenciasViajeros();
        nuevoVigenciasViajeros.setTipoViajero(new Tiposviajeros());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoVigenciasViajeros() {
        if (index >= 0) {
            System.out.println("duplicandoVigenciasViajeros");
            duplicarVigenciasViajeros = new VigenciasViajeros();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVigenciasViajeros.setSecuencia(l);
                duplicarVigenciasViajeros.setEmpleado(listVigenciasViajerosPorEmpleado.get(index).getEmpleado());
                duplicarVigenciasViajeros.setFechavigencia(listVigenciasViajerosPorEmpleado.get(index).getFechavigencia());
                duplicarVigenciasViajeros.setTipoViajero(listVigenciasViajerosPorEmpleado.get(index).getTipoViajero());
            }
            if (tipoLista == 1) {
                duplicarVigenciasViajeros.setSecuencia(l);
                duplicarVigenciasViajeros.setEmpleado(filtrarVigenciasViajerosPorEmplado.get(index).getEmpleado());
                duplicarVigenciasViajeros.setFechavigencia(filtrarVigenciasViajerosPorEmplado.get(index).getFechavigencia());
                duplicarVigenciasViajeros.setTipoViajero(filtrarVigenciasViajerosPorEmplado.get(index).getTipoViajero());
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
        int pasa = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        int fechas = 0;
        a = null;

        if (duplicarVigenciasViajeros.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {

            for (int j = 0; j < listVigenciasViajerosPorEmpleado.size(); j++) {
                if (duplicarVigenciasViajeros.getFechavigencia().equals(listVigenciasViajerosPorEmpleado.get(j).getFechavigencia())) {
                    System.out.println("Se repiten");
                    fechas++;
                }
            }
            if (fechas > 0) {
                context.update("form:validacionFechas");
                context.execute("validacionFechas.show()");
                pasa++;

            } else {
                contador++;
            }
        }
        if (duplicarVigenciasViajeros.getTipoViajero().getNombre() == null || duplicarVigenciasViajeros.getTipoViajero().getNombre().isEmpty() || duplicarVigenciasViajeros.getTipoViajero().getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Tipo Viajero \n";
        } else {
            contador++;
        }
        if (duplicarVigenciasViajeros.getEmpleado().getSecuencia() == null) {
            duplicarVigenciasViajeros.setEmpleado(empleadoSeleccionado);
        }
        if (contador == 2 && pasa == 0) {

            System.out.println("Datos Duplicando: " + duplicarVigenciasViajeros.getSecuencia() + "  " + duplicarVigenciasViajeros.getFechavigencia());
            if (crearVigenciasViajerosPorEmplado.contains(duplicarVigenciasViajeros)) {
                System.out.println("Ya lo contengo.");
            }
            listVigenciasViajerosPorEmpleado.add(duplicarVigenciasViajeros);
            crearVigenciasViajerosPorEmplado.add(duplicarVigenciasViajeros);
            infoRegistro = "Cantidad de registros: " + listVigenciasViajerosPorEmpleado.size();
            context.update("form:informacionRegistro");

            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "270";
                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarVigenciasViajerosPorEmplado = null;
                tipoLista = 0;
            }
            duplicarVigenciasViajeros = new VigenciasViajeros();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else if (pasa == 0 && contador != 2) {
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
            contador = 0;
            pasa = 0;
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void limpiarDuplicarVigenciasViajeros() {
        duplicarVigenciasViajeros = new VigenciasViajeros();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASVIAJEROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASVIAJEROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listVigenciasViajerosPorEmpleado.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASVIAJEROS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASVIAJEROS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<VigenciasViajeros> getListVigenciasViajerosPorEmpleado() {
        if (listVigenciasViajerosPorEmpleado == null) {
            listVigenciasViajerosPorEmpleado = administrarVigenciasViajeros.consultarVigenciasViajerosPorEmpleado(secuenciaEmpleado);
        }
        return listVigenciasViajerosPorEmpleado;
    }

    public void setListVigenciasViajerosPorEmpleado(List<VigenciasViajeros> listVigenciasViajerosPorEmpleado) {
        this.listVigenciasViajerosPorEmpleado = listVigenciasViajerosPorEmpleado;
    }

    public List<VigenciasViajeros> getFiltrarVigenciasViajerosPorEmplado() {
        return filtrarVigenciasViajerosPorEmplado;
    }

    public void setFiltrarVigenciasViajerosPorEmplado(List<VigenciasViajeros> filtrarVigenciasViajerosPorEmplado) {
        this.filtrarVigenciasViajerosPorEmplado = filtrarVigenciasViajerosPorEmplado;
    }

    public VigenciasViajeros getNuevoVigenciasViajeros() {
        return nuevoVigenciasViajeros;
    }

    public void setNuevoVigenciasViajeros(VigenciasViajeros nuevoVigenciasViajeros) {
        this.nuevoVigenciasViajeros = nuevoVigenciasViajeros;
    }

    public VigenciasViajeros getDuplicarVigenciasViajeros() {
        return duplicarVigenciasViajeros;
    }

    public void setDuplicarVigenciasViajeros(VigenciasViajeros duplicarVigenciasViajeros) {
        this.duplicarVigenciasViajeros = duplicarVigenciasViajeros;
    }

    public VigenciasViajeros getEditarVigenciasViajeros() {
        return editarVigenciasViajeros;
    }

    public void setEditarVigenciasViajeros(VigenciasViajeros editarVigenciasViajeros) {
        this.editarVigenciasViajeros = editarVigenciasViajeros;
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
            empleadoSeleccionado = administrarVigenciasViajeros.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Tiposviajeros> getListaTiposviajeros() {
        listaTiposviajeros = administrarVigenciasViajeros.consultarLOVTiposViajeros();
        return listaTiposviajeros;
    }

    public void setListaTiposviajeros(List<Tiposviajeros> listaTiposviajeros) {
        this.listaTiposviajeros = listaTiposviajeros;
    }

    public List<Tiposviajeros> getFiltradoTiposviajeros() {
        return filtradoTiposviajeros;
    }

    public void setFiltradoTiposviajeros(List<Tiposviajeros> filtradoTiposviajeros) {
        this.filtradoTiposviajeros = filtradoTiposviajeros;
    }

    public Tiposviajeros getNormaLaboralSeleccionada() {
        return normaLaboralSeleccionada;
    }

    public void setNormaLaboralSeleccionada(Tiposviajeros normaLaboralSeleccionada) {
        this.normaLaboralSeleccionada = normaLaboralSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public VigenciasViajeros getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasViajeros vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

}

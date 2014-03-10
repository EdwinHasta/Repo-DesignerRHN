/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VigenciasEstadosCiviles;
import Entidades.EstadosCiviles;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVigenciasEstadosCivilesInterface;
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
public class ControlVigenciasEstadosCiviles implements Serializable {

    @EJB
    AdministrarVigenciasEstadosCivilesInterface administrarVigenciasEstadosCiviles;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<VigenciasEstadosCiviles> listEmplVigenciasEstadosCivilesPorEmpleado;
    private List<VigenciasEstadosCiviles> filtrarEmplVigenciasEstadosCivilesPorEmplado;
    private List<VigenciasEstadosCiviles> crearEmplVigenciasEstadosCivilesPorEmplado;
    private List<VigenciasEstadosCiviles> modificarEmplVigenciasEstadosCivilesPorEmplado;
    private List<VigenciasEstadosCiviles> borrarEmplVigenciasEstadosCivilesPorEmplado;
    private VigenciasEstadosCiviles nuevoEmplVigenciasEstadosCiviles;
    private VigenciasEstadosCiviles duplicarEmplVigenciasEstadosCiviles;
    private VigenciasEstadosCiviles editarEmplVigenciasEstadosCiviles;
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
    private List<EstadosCiviles> listaEstadosCiviles;
    private List<EstadosCiviles> filtradoEstadosCiviles;
    private EstadosCiviles normaLaboralSeleccionada;
    private String nuevoYduplicarCompletarNormaLaboral;

    public ControlVigenciasEstadosCiviles() {
        listEmplVigenciasEstadosCivilesPorEmpleado = null;
        crearEmplVigenciasEstadosCivilesPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        modificarEmplVigenciasEstadosCivilesPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        borrarEmplVigenciasEstadosCivilesPorEmplado = new ArrayList<VigenciasEstadosCiviles>();
        permitirIndex = true;
        editarEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
        nuevoEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
        nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
        duplicarEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
        empleadoSeleccionado = null;
        secuenciaEmpleado = BigInteger.valueOf(10664356);
        listaEstadosCiviles = null;
        filtradoEstadosCiviles = null;
        guardado = true;
    }

    public void recibirEmpleado(BigInteger sec) {
        if (sec == null) {
            System.out.println("ERROR EN RECIVIR LA SECUENCIA DEL EMPLEADO EN CONTROLBETAEMPLVIGENCIANORMALABORAL");
        }
        secuenciaEmpleado = sec;
        empleadoSeleccionado = null;
        listEmplVigenciasEstadosCivilesPorEmpleado = null;
        getEmpleadoSeleccionado();
        getListEmplVigenciasEstadosCivilesPorEmpleado();
    }

    public void mostrarNuevo() {
        System.err.println("NUEVA FECHA : " + nuevoEmplVigenciasEstadosCiviles.getFechavigencia());
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
                secRegistro = listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getFechavigencia());
                if (listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < listEmplVigenciasEstadosCivilesPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getFechavigencia().equals(listEmplVigenciasEstadosCivilesPorEmpleado.get(j).getFechavigencia())) {
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
                    if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
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

                secRegistro = filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getSecuencia();
                System.err.println("MODIFICAR FECHA \n Indice" + indice + "Fecha " + filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).getFechavigencia());
                if (filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).getFechavigencia() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    contador++;
                } else {
                    for (int j = 0; j < filtrarEmplVigenciasEstadosCivilesPorEmplado.size(); j++) {
                        if (j != indice) {
                            if (filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).getFechavigencia().equals(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(j).getFechavigencia())) {
                                fechas++;
                            }
                        }
                    }
                    for (int j = 0; j < listEmplVigenciasEstadosCivilesPorEmpleado.size(); j++) {
                        if (j != indice) {
                            if (filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).getFechavigencia().equals(listEmplVigenciasEstadosCivilesPorEmpleado.get(j).getFechavigencia())) {
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
                    if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
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
        } catch (Exception e) {
            System.out.println("ERROR CONTROLBETAEMPLVIGENCIANORMALABORAL eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getSecuencia();
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    normaLaboral = listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil().getDescripcion();
                } else {
                    normaLaboral = listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil().getDescripcion();
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
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarEmplVigenciasEstadosCivilesPorEmplado = null;
            tipoLista = 0;
        }

        borrarEmplVigenciasEstadosCivilesPorEmplado.clear();
        crearEmplVigenciasEstadosCivilesPorEmplado.clear();
        modificarEmplVigenciasEstadosCivilesPorEmplado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEmplVigenciasEstadosCivilesPorEmpleado = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHvEntrevista");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("width: 60px");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("width: 600px");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
            fecha.setFilterStyle("display: none; visibility: hidden;");
            parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
            parentesco.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
            bandera = 0;
            filtrarEmplVigenciasEstadosCivilesPorEmplado = null;
            tipoLista = 0;
        }
    }

    public void modificandoEmplVigenciasEstadosCiviles(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                    if (listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEmplVigenciasEstadosCivilesPorEmpleado.size(); j++) {
                            if (j != indice) {
                                if (listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getFechavigencia().equals(listEmplVigenciasEstadosCivilesPorEmpleado.get(j).getFechavigencia())) {
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
                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
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

                if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice))) {
                    if (filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).getFechavigencia() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (banderita == true) {
                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice));
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
            System.out.println("MODIFICANDO NORMA LABORAL : " + listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getEstadocivil().getDescripcion());
            if (!listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getEstadocivil().getDescripcion().equals("")) {
                if (tipoLista == 0) {
                    listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getEstadocivil().setDescripcion(normaLaboral);
                } else {
                    listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).getEstadocivil().setDescripcion(normaLaboral);
                }

                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listEmplVigenciasEstadosCivilesPorEmpleado.get(indice).setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    } else {
                        filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice).setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
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
                    listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil().setDescripcion(normaLaboral);
                } else {
                    filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getEstadocivil().setDescripcion(normaLaboral);
                }
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {

                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice))) {

                        if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice));
                        } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice))) {
                            modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(indice));
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
                listEmplVigenciasEstadosCivilesPorEmpleado.get(index).setEstadocivil(normaLaboralSeleccionada);

                if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(index))) {
                    if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                        modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                    } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(index))) {
                        modificarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).setEstadocivil(normaLaboralSeleccionada);

                if (!crearEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index))) {
                    if (modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                        modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                    } else if (!modificarEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index))) {
                        modificarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
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
            System.out.println("ACTUALIZARNORMA LABORAR NUEVA NORMA LABORAL: " + normaLaboralSeleccionada.getDescripcion());
            nuevoEmplVigenciasEstadosCiviles.setEstadocivil(normaLaboralSeleccionada);
            context.update("formularioDialogos:nuevoNombreSucursal");
        } else if (tipoActualizacion == 2) {
            System.out.println("ACTUALIZARNORMA LABORAR DUPLICAR NORMA LABORAL: " + normaLaboralSeleccionada.getDescripcion());
            duplicarEmplVigenciasEstadosCiviles.setEstadocivil(normaLaboralSeleccionada);
            context.update("formularioDialogos:duplicarTipoCentroCostos");
        }
        filtradoEstadosCiviles = null;
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
        filtradoEstadosCiviles = null;
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
            nuevoYduplicarCompletarNormaLaboral = nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion();
        } else if (tipoNuevo == 2) {
            nuevoYduplicarCompletarNormaLaboral = duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion();
        }

    }

    public void autocompletarNuevo(String confirmarCambio, String valorConfirmar, int tipoNuevo) {

        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("NORMASLABORALES")) {
            System.out.println(" nueva Ciudad    Entro al if 'Centro costo'");
            System.out.println("NOMBRE CENTRO COSTO: " + nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());

            if (!nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion().equals("")) {
                System.out.println("ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("valorConfirmar: " + valorConfirmar);
                System.out.println("nuevoYduplicarCiudadCompletar: " + nuevoYduplicarCompletarNormaLaboral);
                nuevoEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                getListaEstadosCiviles();
                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    nuevoEmplVigenciasEstadosCiviles.setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    listaEstadosCiviles = null;
                    getListaEstadosCiviles();
                    System.err.println("NORMA LABORAL GUARDADA " + nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                nuevoEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                System.out.println("valorConfirmar cuando es vacio: " + valorConfirmar);
                nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
                nuevoEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(" ");
                System.out.println("NUEVA NORMA LABORAL" + nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());
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

    public void limpiarNuevaNormaLaboral() {
        try {
            System.out.println("\n ENTRE A LIMPIAR NUEVO NORMA LABORAL \n");
            nuevoEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
            nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
            index = -1;
        } catch (Exception e) {
            System.out.println("Error CONTROLBETAEMPLVIGENCIANORMALABORAL LIMPIAR NUEVO NORMA LABORAL ERROR :" + e.getMessage());
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
            System.out.println("DUPLICAR CIUDAD bkp : " + nuevoYduplicarCompletarNormaLaboral);

            if (!duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion().equals("")) {
                System.out.println("DUPLICAR ENTRO DONDE NO TENIA QUE ENTRAR");
                System.out.println("DUPLICAR valorConfirmar: " + valorConfirmar);
                System.out.println("DUPLICAR nuevoTipoCCAutoCompletar: " + nuevoYduplicarCompletarNormaLaboral);
                duplicarEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                for (int i = 0; i < listaEstadosCiviles.size(); i++) {
                    if (listaEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                System.out.println("Coincidencias: " + coincidencias);
                if (coincidencias == 1) {
                    duplicarEmplVigenciasEstadosCiviles.setEstadocivil(listaEstadosCiviles.get(indiceUnicoElemento));
                    listaEstadosCiviles = null;
                    getListaEstadosCiviles();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                }
            } else {
                if (tipoNuevo == 2) {
                    //duplicarEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                    System.out.println("DUPLICAR valorConfirmar cuando es vacio: " + valorConfirmar);
                    System.out.println("DUPLICAR INDEX : " + index);
                    duplicarEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
                    duplicarEmplVigenciasEstadosCiviles.getEstadocivil().setDescripcion(" ");

                    System.out.println("DUPLICAR Valor NORMA LABORAL : " + duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());
                    if (tipoLista == 0) {
                        listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
                        System.err.println("tipo lista" + tipoLista);
                        System.err.println("Secuencia Parentesco " + listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil().getSecuencia());
                    } else if (tipoLista == 1) {
                        filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getEstadocivil().setDescripcion(nuevoYduplicarCompletarNormaLaboral);
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
                if (!modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty() && modificarEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(index))) {
                    int modIndex = modificarEmplVigenciasEstadosCivilesPorEmplado.indexOf(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                    modificarEmplVigenciasEstadosCivilesPorEmplado.remove(modIndex);
                    borrarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                } else if (!crearEmplVigenciasEstadosCivilesPorEmplado.isEmpty() && crearEmplVigenciasEstadosCivilesPorEmplado.contains(listEmplVigenciasEstadosCivilesPorEmpleado.get(index))) {
                    int crearIndex = crearEmplVigenciasEstadosCivilesPorEmplado.indexOf(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                    crearEmplVigenciasEstadosCivilesPorEmplado.remove(crearIndex);
                } else {
                    borrarEmplVigenciasEstadosCivilesPorEmplado.add(listEmplVigenciasEstadosCivilesPorEmpleado.get(index));
                }
                listEmplVigenciasEstadosCivilesPorEmpleado.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty() && modificarEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index))) {
                    int modIndex = modificarEmplVigenciasEstadosCivilesPorEmplado.indexOf(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                    modificarEmplVigenciasEstadosCivilesPorEmplado.remove(modIndex);
                    borrarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                } else if (!crearEmplVigenciasEstadosCivilesPorEmplado.isEmpty() && crearEmplVigenciasEstadosCivilesPorEmplado.contains(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index))) {
                    int crearIndex = crearEmplVigenciasEstadosCivilesPorEmplado.indexOf(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                    crearEmplVigenciasEstadosCivilesPorEmplado.remove(crearIndex);
                } else {
                    borrarEmplVigenciasEstadosCivilesPorEmplado.add(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                }
                int VCIndex = listEmplVigenciasEstadosCivilesPorEmpleado.indexOf(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index));
                listEmplVigenciasEstadosCivilesPorEmpleado.remove(VCIndex);
                filtrarEmplVigenciasEstadosCivilesPorEmplado.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosHvEntrevista");
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

        if (!borrarEmplVigenciasEstadosCivilesPorEmplado.isEmpty() || !crearEmplVigenciasEstadosCivilesPorEmplado.isEmpty() || !modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEmplVigenciasEstadosCiviles() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                for (int i = 0; i < borrarEmplVigenciasEstadosCivilesPorEmplado.size(); i++) {
                    System.out.println("Borrando...");
                    administrarVigenciasEstadosCiviles.borrarVigenciasEstadosCiviles(borrarEmplVigenciasEstadosCivilesPorEmplado);
                }
                //mostrarBorrados
                registrosBorrados = borrarEmplVigenciasEstadosCivilesPorEmplado.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");

                borrarEmplVigenciasEstadosCivilesPorEmplado.clear();
            }
            if (!modificarEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                System.out.println("Modificando...");
                administrarVigenciasEstadosCiviles.modificarVigenciasEstadosCiviles(modificarEmplVigenciasEstadosCivilesPorEmplado);
                modificarEmplVigenciasEstadosCivilesPorEmplado.clear();
            }
            if (!crearEmplVigenciasEstadosCivilesPorEmplado.isEmpty()) {
                for (int i = 0; i < crearEmplVigenciasEstadosCivilesPorEmplado.size(); i++) {
                    System.out.println("Creando...");
                    System.out.println("-----------------------------------------------");
                    System.out.println("Empleado : " + crearEmplVigenciasEstadosCivilesPorEmplado.get(i).getPersona().getNombre());
                    System.out.println("Fecha :" + crearEmplVigenciasEstadosCivilesPorEmplado.get(i).getFechavigencia());
                    System.out.println("Norma Laboral : " + crearEmplVigenciasEstadosCivilesPorEmplado.get(i).getEstadocivil().getDescripcion());
                    System.out.println("-----------------------------------------------");

                }
                administrarVigenciasEstadosCiviles.crearVigenciasEstadosCiviles(crearEmplVigenciasEstadosCivilesPorEmplado);
                crearEmplVigenciasEstadosCivilesPorEmplado.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEmplVigenciasEstadosCivilesPorEmpleado = null;
            context.update("form:datosHvEntrevista");
            k = 0;
            guardado = true;
            context.update("form:mostrarGuardar");
            context.execute("mostrarGuardar.show()");
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEmplVigenciasEstadosCiviles = listEmplVigenciasEstadosCivilesPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarEmplVigenciasEstadosCiviles = filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index);
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

    public void agregarNuevoEmplVigenciasEstadosCiviles() {
        System.out.println("agregarNuevoEmplVigenciasEstadosCiviles");
        int contador = 0;
        //nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
        Short a = 0;
        a = null;
        int fechas = 0;
        mensajeValidacion = " ";
        nuevoEmplVigenciasEstadosCiviles.setPersona(empleadoSeleccionado.getPersona());
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Nueva Fecha : " + nuevoEmplVigenciasEstadosCiviles.getFechavigencia());
        if (nuevoEmplVigenciasEstadosCiviles.getFechavigencia() == null) {
            mensajeValidacion = " *Debe tener una fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            if (listEmplVigenciasEstadosCivilesPorEmpleado != null) {
                for (int i = 0; i < listEmplVigenciasEstadosCivilesPorEmpleado.size(); i++) {
                    if (nuevoEmplVigenciasEstadosCiviles.getFechavigencia().equals(listEmplVigenciasEstadosCivilesPorEmpleado.get(i).getFechavigencia())) {
                        fechas++;
                    }
                }
                if (fechas > 0) {
                    mensajeValidacion = "Fechas repetidas ";
                } else {
                    contador++;
                }
            } else {
                contador++;
            }
        }
        if (nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   *Un Estado Civil\n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
            System.out.println("NORMA LABORAL  : " + nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());

        } else {
            System.out.println("Bandera : ");
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

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarEmplVigenciasEstadosCivilesPorEmplado = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoEmplVigenciasEstadosCiviles.setSecuencia(l);
            System.err.println("---------------AGREGAR REGISTRO----------------");
            System.err.println("fecha " + nuevoEmplVigenciasEstadosCiviles.getFechavigencia());
            System.err.println("nombre " + nuevoEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());
            System.err.println("-----------------------------------------------");

            crearEmplVigenciasEstadosCivilesPorEmplado.add(nuevoEmplVigenciasEstadosCiviles);
            if (listEmplVigenciasEstadosCivilesPorEmpleado == null) {
                listEmplVigenciasEstadosCivilesPorEmpleado = new ArrayList<VigenciasEstadosCiviles>();
            }
            listEmplVigenciasEstadosCivilesPorEmpleado.add(nuevoEmplVigenciasEstadosCiviles);
            nuevoEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
            nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
            context.update("form:datosHvEntrevista");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");
            context.update("form:datosHvEntrevista");

            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEmplVigenciasEstadosCiviles() {
        System.out.println("limpiarNuevoEmplVigenciasEstadosCiviles");
        nuevoEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
        nuevoEmplVigenciasEstadosCiviles.setEstadocivil(new EstadosCiviles());
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoEmplVigenciasEstadosCiviles() {
        if (index >= 0) {
            System.out.println("duplicandoEmplVigenciasEstadosCiviles");
            duplicarEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEmplVigenciasEstadosCiviles.setSecuencia(l);
                duplicarEmplVigenciasEstadosCiviles.setPersona(listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getPersona());
                duplicarEmplVigenciasEstadosCiviles.setFechavigencia(listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getFechavigencia());
                duplicarEmplVigenciasEstadosCiviles.setEstadocivil(listEmplVigenciasEstadosCivilesPorEmpleado.get(index).getEstadocivil());
            }
            if (tipoLista == 1) {
                duplicarEmplVigenciasEstadosCiviles.setSecuencia(l);
                duplicarEmplVigenciasEstadosCiviles.setPersona(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getPersona());
                duplicarEmplVigenciasEstadosCiviles.setFechavigencia(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getFechavigencia());
                duplicarEmplVigenciasEstadosCiviles.setEstadocivil(filtrarEmplVigenciasEstadosCivilesPorEmplado.get(index).getEstadocivil());
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarEmplVigenciasEstadosCiviles.getFechavigencia());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());

        if (duplicarEmplVigenciasEstadosCiviles.getFechavigencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Fecha \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {

            for (int j = 0; j < listEmplVigenciasEstadosCivilesPorEmpleado.size(); j++) {
                if (duplicarEmplVigenciasEstadosCiviles.getFechavigencia().equals(listEmplVigenciasEstadosCivilesPorEmpleado.get(j).getFechavigencia())) {
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
        if (duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion() == null || duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion().isEmpty() || duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Norma Laboral \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("bandera");
            contador++;

        }
        if (duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getSecuencia() == null) {
            duplicarEmplVigenciasEstadosCiviles.setPersona(empleadoSeleccionado.getPersona());
        }
        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarEmplVigenciasEstadosCiviles.getSecuencia() + "  " + duplicarEmplVigenciasEstadosCiviles.getFechavigencia());
            if (crearEmplVigenciasEstadosCivilesPorEmplado.contains(duplicarEmplVigenciasEstadosCiviles)) {
                System.out.println("Ya lo contengo.");
            }
            listEmplVigenciasEstadosCivilesPorEmpleado.add(duplicarEmplVigenciasEstadosCiviles);
            crearEmplVigenciasEstadosCivilesPorEmplado.add(duplicarEmplVigenciasEstadosCiviles);
            context.update("form:datosHvEntrevista");
            index = -1;
            secRegistro = null;

            System.err.println("---------------DUPLICAR REGISTRO----------------");
            System.err.println("fecha " + duplicarEmplVigenciasEstadosCiviles.getFechavigencia());
            System.err.println("NormaLaboral " + duplicarEmplVigenciasEstadosCiviles.getEstadocivil().getDescripcion());
            System.err.println("Nombre Empleado " + duplicarEmplVigenciasEstadosCiviles.getPersona().getNombre());
            System.err.println("-----------------------------------------------");
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                fecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:fecha");
                fecha.setFilterStyle("display: none; visibility: hidden;");
                parentesco = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHvEntrevista:parentesco");
                parentesco.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosHvEntrevista");
                bandera = 0;
                filtrarEmplVigenciasEstadosCivilesPorEmplado = null;
                tipoLista = 0;
            }
            duplicarEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            fechas = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void limpiarDuplicarEmplVigenciasEstadosCiviles() {
        duplicarEmplVigenciasEstadosCiviles = new VigenciasEstadosCiviles();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VIGENCIASESTADOSCIVILES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosHvEntrevistaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VIGENCIASESTADOSCIVILES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEmplVigenciasEstadosCivilesPorEmpleado.isEmpty()) {
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
    public List<VigenciasEstadosCiviles> getListEmplVigenciasEstadosCivilesPorEmpleado() {
        if (listEmplVigenciasEstadosCivilesPorEmpleado == null) {
            if (secuenciaEmpleado == null) {
                listEmplVigenciasEstadosCivilesPorEmpleado = administrarVigenciasEstadosCiviles.consultarVigenciasEstadosCivilesPorEmpleado();
            } else {
                listEmplVigenciasEstadosCivilesPorEmpleado = administrarVigenciasEstadosCiviles.consultarVigenciasEstadosCivilesPorEmpleado(empleadoSeleccionado.getPersona().getSecuencia());
                System.out.println("NO ES NULA");
            }
        }
        return listEmplVigenciasEstadosCivilesPorEmpleado;
    }

    public void setListEmplVigenciasEstadosCivilesPorEmpleado(List<VigenciasEstadosCiviles> listEmplVigenciasEstadosCivilesPorEmpleado) {
        this.listEmplVigenciasEstadosCivilesPorEmpleado = listEmplVigenciasEstadosCivilesPorEmpleado;
    }

    public List<VigenciasEstadosCiviles> getFiltrarEmplVigenciasEstadosCivilesPorEmplado() {
        return filtrarEmplVigenciasEstadosCivilesPorEmplado;
    }

    public void setFiltrarEmplVigenciasEstadosCivilesPorEmplado(List<VigenciasEstadosCiviles> filtrarEmplVigenciasEstadosCivilesPorEmplado) {
        this.filtrarEmplVigenciasEstadosCivilesPorEmplado = filtrarEmplVigenciasEstadosCivilesPorEmplado;
    }

    public VigenciasEstadosCiviles getNuevoEmplVigenciasEstadosCiviles() {
        return nuevoEmplVigenciasEstadosCiviles;
    }

    public void setNuevoEmplVigenciasEstadosCiviles(VigenciasEstadosCiviles nuevoEmplVigenciasEstadosCiviles) {
        this.nuevoEmplVigenciasEstadosCiviles = nuevoEmplVigenciasEstadosCiviles;
    }

    public VigenciasEstadosCiviles getDuplicarEmplVigenciasEstadosCiviles() {
        return duplicarEmplVigenciasEstadosCiviles;
    }

    public void setDuplicarEmplVigenciasEstadosCiviles(VigenciasEstadosCiviles duplicarEmplVigenciasEstadosCiviles) {
        this.duplicarEmplVigenciasEstadosCiviles = duplicarEmplVigenciasEstadosCiviles;
    }

    public VigenciasEstadosCiviles getEditarEmplVigenciasEstadosCiviles() {
        return editarEmplVigenciasEstadosCiviles;
    }

    public void setEditarEmplVigenciasEstadosCiviles(VigenciasEstadosCiviles editarEmplVigenciasEstadosCiviles) {
        this.editarEmplVigenciasEstadosCiviles = editarEmplVigenciasEstadosCiviles;
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
            empleadoSeleccionado = administrarVigenciasEstadosCiviles.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<EstadosCiviles> getListaEstadosCiviles() {
        if (listaEstadosCiviles == null) {
            listaEstadosCiviles = administrarVigenciasEstadosCiviles.lovEstadosCiviles();
        }
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

    public EstadosCiviles getNormaLaboralSeleccionada() {
        return normaLaboralSeleccionada;
    }

    public void setNormaLaboralSeleccionada(EstadosCiviles normaLaboralSeleccionada) {
        this.normaLaboralSeleccionada = normaLaboralSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

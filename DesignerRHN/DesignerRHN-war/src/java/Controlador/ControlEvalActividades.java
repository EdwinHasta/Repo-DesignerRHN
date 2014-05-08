/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.EvalActividades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEvalActividadesInterface;
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
public class ControlEvalActividades implements Serializable {

    @EJB
    AdministrarEvalActividadesInterface administrarEvalActividades;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    
    private List<EvalActividades> listEvalActividades;
    private List<EvalActividades> filtrarEvalActividades;
    private List<EvalActividades> crearEvalActividades;
    private List<EvalActividades> modificarEvalActividades;
    private List<EvalActividades> borrarEvalActividades;
    private EvalActividades nuevoEvalActividades;
    private EvalActividades duplicarEvalActividades;
    private EvalActividades editarEvalActividades;
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
    private Integer backupCodigo;
    private String backupDescripcion;

    public ControlEvalActividades() {
        listEvalActividades = null;
        crearEvalActividades = new ArrayList<EvalActividades>();
        modificarEvalActividades = new ArrayList<EvalActividades>();
        borrarEvalActividades = new ArrayList<EvalActividades>();
        permitirIndex = true;
        editarEvalActividades = new EvalActividades();
        nuevoEvalActividades = new EvalActividades();
        duplicarEvalActividades = new EvalActividades();
        guardado = true;
        tamano = 302;
    }
    
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEvalActividades.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlEvalActividades.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlEvalActividades eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEvalActividades.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listEvalActividades.get(index).getCodigo();
                backupDescripcion = listEvalActividades.get(index).getDescripcion();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarEvalActividades.get(index).getCodigo();
                backupDescripcion = filtrarEvalActividades.get(index).getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlEvalActividades.asignarIndex \n");
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
            System.out.println("ERROR ControlEvalActividades.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalActividades");
            bandera = 0;
            filtrarEvalActividades = null;
            tipoLista = 0;
        }

        borrarEvalActividades.clear();
        crearEvalActividades.clear();
        modificarEvalActividades.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEvalActividades = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEvalActividades");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosEvalActividades");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalActividades");
            bandera = 0;
            filtrarEvalActividades = null;
            tipoLista = 0;
        }
    }

    public void modificarEvalActividades(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        boolean banderita1 = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearEvalActividades.contains(listEvalActividades.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listEvalActividades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (listEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listEvalActividades.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listEvalActividades.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listEvalActividades.get(indice).setDescripcion(backupDescripcion);
                    } else if (listEvalActividades.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listEvalActividades.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarEvalActividades.isEmpty()) {
                            modificarEvalActividades.add(listEvalActividades.get(indice));
                        } else if (!modificarEvalActividades.contains(listEvalActividades.get(indice))) {
                            modificarEvalActividades.add(listEvalActividades.get(indice));
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
                    context.update("form:datosEvalActividades");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listEvalActividades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listEvalActividades.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (listEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listEvalActividades.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listEvalActividades.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listEvalActividades.get(indice).setDescripcion(backupDescripcion);
                    } else if (listEvalActividades.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listEvalActividades.get(indice).setDescripcion(backupDescripcion);

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
                    context.update("form:datosEvalActividades");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearEvalActividades.contains(filtrarEvalActividades.get(indice))) {
                    if (filtrarEvalActividades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEvalActividades.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarEvalActividades.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarEvalActividades.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarEvalActividades.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarEvalActividades.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarEvalActividades.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarEvalActividades.isEmpty()) {
                            modificarEvalActividades.add(filtrarEvalActividades.get(indice));
                        } else if (!modificarEvalActividades.contains(filtrarEvalActividades.get(indice))) {
                            modificarEvalActividades.add(filtrarEvalActividades.get(indice));
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
                } else {if (filtrarEvalActividades.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEvalActividades.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listEvalActividades.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalActividades.get(indice).getCodigo() == listEvalActividades.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarEvalActividades.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarEvalActividades.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarEvalActividades.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarEvalActividades.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarEvalActividades.get(indice).setDescripcion(backupDescripcion);
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
            context.update("form:datosEvalActividades");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoEvalActividades() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoEvalActividades");
                if (!modificarEvalActividades.isEmpty() && modificarEvalActividades.contains(listEvalActividades.get(index))) {
                    int modIndex = modificarEvalActividades.indexOf(listEvalActividades.get(index));
                    modificarEvalActividades.remove(modIndex);
                    borrarEvalActividades.add(listEvalActividades.get(index));
                } else if (!crearEvalActividades.isEmpty() && crearEvalActividades.contains(listEvalActividades.get(index))) {
                    int crearIndex = crearEvalActividades.indexOf(listEvalActividades.get(index));
                    crearEvalActividades.remove(crearIndex);
                } else {
                    borrarEvalActividades.add(listEvalActividades.get(index));
                }
                listEvalActividades.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalActividades ");
                if (!modificarEvalActividades.isEmpty() && modificarEvalActividades.contains(filtrarEvalActividades.get(index))) {
                    int modIndex = modificarEvalActividades.indexOf(filtrarEvalActividades.get(index));
                    modificarEvalActividades.remove(modIndex);
                    borrarEvalActividades.add(filtrarEvalActividades.get(index));
                } else if (!crearEvalActividades.isEmpty() && crearEvalActividades.contains(filtrarEvalActividades.get(index))) {
                    int crearIndex = crearEvalActividades.indexOf(filtrarEvalActividades.get(index));
                    crearEvalActividades.remove(crearIndex);
                } else {
                    borrarEvalActividades.add(filtrarEvalActividades.get(index));
                }
                int VCIndex = listEvalActividades.indexOf(filtrarEvalActividades.get(index));
                listEvalActividades.remove(VCIndex);
                filtrarEvalActividades.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEvalActividades");
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
        BigInteger contarCapBuzonesEvalActividad;
        BigInteger contarCapNecesidadesEvalActividad;
        BigInteger contarEvalPlanesDesarrollosEvalActividad;

        try {
            System.err.println("Control Secuencia de ControlEvalActividades ");
            if (tipoLista == 0) {
                contarCapBuzonesEvalActividad = administrarEvalActividades.contarCapBuzonesEvalActividad(listEvalActividades.get(index).getSecuencia());
                contarCapNecesidadesEvalActividad = administrarEvalActividades.contarCapNecesidadesEvalActividad(listEvalActividades.get(index).getSecuencia());
                contarEvalPlanesDesarrollosEvalActividad = administrarEvalActividades.contarEvalPlanesDesarrollosEvalActividad(listEvalActividades.get(index).getSecuencia());
            } else {
                contarCapBuzonesEvalActividad = administrarEvalActividades.contarCapBuzonesEvalActividad(filtrarEvalActividades.get(index).getSecuencia());
                contarCapNecesidadesEvalActividad = administrarEvalActividades.contarCapNecesidadesEvalActividad(filtrarEvalActividades.get(index).getSecuencia());
                contarEvalPlanesDesarrollosEvalActividad = administrarEvalActividades.contarEvalPlanesDesarrollosEvalActividad(filtrarEvalActividades.get(index).getSecuencia());
            }
            if (contarCapBuzonesEvalActividad.equals(new BigInteger("0")) && contarCapNecesidadesEvalActividad.equals(new BigInteger("0"))&& contarEvalPlanesDesarrollosEvalActividad.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoEvalActividades();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarCapBuzonesEvalActividad = new BigInteger("-1");
                contarCapNecesidadesEvalActividad = new BigInteger("-1");
                contarEvalPlanesDesarrollosEvalActividad = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEvalActividades verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarEvalActividades.isEmpty() || !crearEvalActividades.isEmpty() || !modificarEvalActividades.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEvalActividades() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalActividades");
            if (!borrarEvalActividades.isEmpty()) {
                administrarEvalActividades.borrarEvalActividades(borrarEvalActividades);
                //mostrarBorrados
                registrosBorrados = borrarEvalActividades.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEvalActividades.clear();
            }
            if (!modificarEvalActividades.isEmpty()) {
                administrarEvalActividades.modificarEvalActividades(modificarEvalActividades);
                modificarEvalActividades.clear();
            }
            if (!crearEvalActividades.isEmpty()) {
                administrarEvalActividades.crearEvalActividades(crearEvalActividades);
                crearEvalActividades.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEvalActividades = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosEvalActividades");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEvalActividades = listEvalActividades.get(index);
            }
            if (tipoLista == 1) {
                editarEvalActividades = filtrarEvalActividades.get(index);
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

    public void agregarNuevoEvalActividades() {
        System.out.println("agregarNuevoEvalActividades");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEvalActividades.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoEvalActividades.getCodigo());

            for (int x = 0; x < listEvalActividades.size(); x++) {
                if (listEvalActividades.get(x).getCodigo() == nuevoEvalActividades.getCodigo()) {
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
        if (nuevoEvalActividades.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalActividades");
                bandera = 0;
                filtrarEvalActividades = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoEvalActividades.setSecuencia(l);

            crearEvalActividades.add(nuevoEvalActividades);

            listEvalActividades.add(nuevoEvalActividades);
            nuevoEvalActividades = new EvalActividades();
            context.update("form:datosEvalActividades");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalActividades.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEvalActividades() {
        System.out.println("limpiarNuevoEvalActividades");
        nuevoEvalActividades = new EvalActividades();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoEvalActividades() {
        System.out.println("duplicandoEvalActividades");
        if (index >= 0) {
            duplicarEvalActividades = new EvalActividades();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEvalActividades.setSecuencia(l);
                duplicarEvalActividades.setCodigo(listEvalActividades.get(index).getCodigo());
                duplicarEvalActividades.setDescripcion(listEvalActividades.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarEvalActividades.setSecuencia(l);
                duplicarEvalActividades.setCodigo(filtrarEvalActividades.get(index).getCodigo());
                duplicarEvalActividades.setDescripcion(filtrarEvalActividades.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroEvalActividades.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarEvalActividades.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarEvalActividades.getDescripcion());

        if (duplicarEvalActividades.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listEvalActividades.size(); x++) {
                if (listEvalActividades.get(x).getCodigo() == duplicarEvalActividades.getCodigo()) {
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
        if (duplicarEvalActividades.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarEvalActividades.getSecuencia() + "  " + duplicarEvalActividades.getCodigo());
            if (crearEvalActividades.contains(duplicarEvalActividades)) {
                System.out.println("Ya lo contengo.");
            }
            listEvalActividades.add(duplicarEvalActividades);
            crearEvalActividades.add(duplicarEvalActividades);
            context.update("form:datosEvalActividades");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalActividades:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalActividades");
                bandera = 0;
                filtrarEvalActividades = null;
                tipoLista = 0;
            }
            duplicarEvalActividades = new EvalActividades();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalActividades.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarEvalActividades() {
        duplicarEvalActividades = new EvalActividades();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalActividadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EVALACTIVIDADES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalActividadesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EVALACTIVIDADES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEvalActividades.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EVALACTIVIDADES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("EVALACTIVIDADES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<EvalActividades> getListEvalActividades() {
        if (listEvalActividades == null) {
            listEvalActividades = administrarEvalActividades.consultarEvalActividades();
        }
        return listEvalActividades;
    }

    public void setListEvalActividades(List<EvalActividades> listEvalActividades) {
        this.listEvalActividades = listEvalActividades;
    }

    public List<EvalActividades> getFiltrarEvalActividades() {
        return filtrarEvalActividades;
    }

    public void setFiltrarEvalActividades(List<EvalActividades> filtrarEvalActividades) {
        this.filtrarEvalActividades = filtrarEvalActividades;
    }

    public EvalActividades getNuevoEvalActividades() {
        return nuevoEvalActividades;
    }

    public void setNuevoEvalActividades(EvalActividades nuevoEvalActividades) {
        this.nuevoEvalActividades = nuevoEvalActividades;
    }

    public EvalActividades getDuplicarEvalActividades() {
        return duplicarEvalActividades;
    }

    public void setDuplicarEvalActividades(EvalActividades duplicarEvalActividades) {
        this.duplicarEvalActividades = duplicarEvalActividades;
    }

    public EvalActividades getEditarEvalActividades() {
        return editarEvalActividades;
    }

    public void setEditarEvalActividades(EvalActividades editarEvalActividades) {
        this.editarEvalActividades = editarEvalActividades;
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

}

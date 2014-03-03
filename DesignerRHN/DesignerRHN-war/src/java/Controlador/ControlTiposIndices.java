/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposIndices;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposIndicesInterface;
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
public class ControlTiposIndices implements Serializable {

    @EJB
    AdministrarTiposIndicesInterface administrarTiposIndices;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposIndices> listTiposIndices;
    private List<TiposIndices> filtrarTiposIndices;
    private List<TiposIndices> crearTiposIndices;
    private List<TiposIndices> modificarTiposIndices;
    private List<TiposIndices> borrarTiposIndices;
    private TiposIndices nuevoTiposIndices;
    private TiposIndices duplicarTiposIndices;
    private TiposIndices editarTiposIndices;
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

    public ControlTiposIndices() {
        listTiposIndices = null;
        crearTiposIndices = new ArrayList<TiposIndices>();
        modificarTiposIndices = new ArrayList<TiposIndices>();
        borrarTiposIndices = new ArrayList<TiposIndices>();
        permitirIndex = true;
        editarTiposIndices = new TiposIndices();
        nuevoTiposIndices = new TiposIndices();
        duplicarTiposIndices = new TiposIndices();
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposIndices.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposIndices eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposIndices.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listTiposIndices.get(index).getCodigo();
                backupDescripcion = listTiposIndices.get(index).getDescripcion();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarTiposIndices.get(index).getCodigo();
                backupDescripcion = filtrarTiposIndices.get(index).getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposIndices.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposIndices.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposIndices");
            bandera = 0;
            filtrarTiposIndices = null;
            tipoLista = 0;
        }

        borrarTiposIndices.clear();
        crearTiposIndices.clear();
        modificarTiposIndices.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposIndices = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposIndices");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposIndices");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposIndices");
            bandera = 0;
            filtrarTiposIndices = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposIndices(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearTiposIndices.contains(listTiposIndices.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposIndices.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (listTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposIndices.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposIndices.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposIndices.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposIndices.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposIndices.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposIndices.isEmpty()) {
                            modificarTiposIndices.add(listTiposIndices.get(indice));
                        } else if (!modificarTiposIndices.contains(listTiposIndices.get(indice))) {
                            modificarTiposIndices.add(listTiposIndices.get(indice));
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
                    context.update("form:datosTiposIndices");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposIndices.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposIndices.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (listTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposIndices.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposIndices.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposIndices.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposIndices.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposIndices.get(indice).setDescripcion(backupDescripcion);

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
                    context.update("form:datosTiposIndices");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearTiposIndices.contains(filtrarTiposIndices.get(indice))) {
                    if (filtrarTiposIndices.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposIndices.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposIndices.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposIndices.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposIndices.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposIndices.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposIndices.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposIndices.isEmpty()) {
                            modificarTiposIndices.add(filtrarTiposIndices.get(indice));
                        } else if (!modificarTiposIndices.contains(filtrarTiposIndices.get(indice))) {
                            modificarTiposIndices.add(filtrarTiposIndices.get(indice));
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
                } else {if (filtrarTiposIndices.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposIndices.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposIndices.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposIndices.get(indice).getCodigo() == listTiposIndices.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposIndices.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposIndices.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposIndices.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposIndices.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposIndices.get(indice).setDescripcion(backupDescripcion);
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
            context.update("form:datosTiposIndices");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposIndices() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposIndices");
                if (!modificarTiposIndices.isEmpty() && modificarTiposIndices.contains(listTiposIndices.get(index))) {
                    int modIndex = modificarTiposIndices.indexOf(listTiposIndices.get(index));
                    modificarTiposIndices.remove(modIndex);
                    borrarTiposIndices.add(listTiposIndices.get(index));
                } else if (!crearTiposIndices.isEmpty() && crearTiposIndices.contains(listTiposIndices.get(index))) {
                    int crearIndex = crearTiposIndices.indexOf(listTiposIndices.get(index));
                    crearTiposIndices.remove(crearIndex);
                } else {
                    borrarTiposIndices.add(listTiposIndices.get(index));
                }
                listTiposIndices.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposIndices ");
                if (!modificarTiposIndices.isEmpty() && modificarTiposIndices.contains(filtrarTiposIndices.get(index))) {
                    int modIndex = modificarTiposIndices.indexOf(filtrarTiposIndices.get(index));
                    modificarTiposIndices.remove(modIndex);
                    borrarTiposIndices.add(filtrarTiposIndices.get(index));
                } else if (!crearTiposIndices.isEmpty() && crearTiposIndices.contains(filtrarTiposIndices.get(index))) {
                    int crearIndex = crearTiposIndices.indexOf(filtrarTiposIndices.get(index));
                    crearTiposIndices.remove(crearIndex);
                } else {
                    borrarTiposIndices.add(filtrarTiposIndices.get(index));
                }
                int VCIndex = listTiposIndices.indexOf(filtrarTiposIndices.get(index));
                listTiposIndices.remove(VCIndex);
                filtrarTiposIndices.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposIndices");
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
        BigInteger contarIndicesTipoIndice;

        try {
            System.err.println("Control Secuencia de ControlTiposIndices ");
            if (tipoLista == 0) {
                contarIndicesTipoIndice = administrarTiposIndices.contarIndicesTipoIndice(listTiposIndices.get(index).getSecuencia());
            } else {
                contarIndicesTipoIndice = administrarTiposIndices.contarIndicesTipoIndice(filtrarTiposIndices.get(index).getSecuencia());
            }
            if (contarIndicesTipoIndice.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposIndices();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarIndicesTipoIndice = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposIndices verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposIndices.isEmpty() || !crearTiposIndices.isEmpty() || !modificarTiposIndices.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposIndices() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposIndices");
            if (!borrarTiposIndices.isEmpty()) {
                administrarTiposIndices.borrarTiposIndices(borrarTiposIndices);
                //mostrarBorrados
                registrosBorrados = borrarTiposIndices.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposIndices.clear();
            }
            if (!modificarTiposIndices.isEmpty()) {
                administrarTiposIndices.modificarTiposIndices(modificarTiposIndices);
                modificarTiposIndices.clear();
            }
            if (!crearTiposIndices.isEmpty()) {
                administrarTiposIndices.crearTiposIndices(crearTiposIndices);
                crearTiposIndices.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposIndices = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosTiposIndices");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposIndices = listTiposIndices.get(index);
            }
            if (tipoLista == 1) {
                editarTiposIndices = filtrarTiposIndices.get(index);
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

    public void agregarNuevoTiposIndices() {
        System.out.println("agregarNuevoTiposIndices");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposIndices.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposIndices.getCodigo());

            for (int x = 0; x < listTiposIndices.size(); x++) {
                if (listTiposIndices.get(x).getCodigo() == nuevoTiposIndices.getCodigo()) {
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
        if (nuevoTiposIndices.getDescripcion().equals(" ")) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposIndices");
                bandera = 0;
                filtrarTiposIndices = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposIndices.setSecuencia(l);

            crearTiposIndices.add(nuevoTiposIndices);

            listTiposIndices.add(nuevoTiposIndices);
            nuevoTiposIndices = new TiposIndices();
            context.update("form:datosTiposIndices");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposIndices.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposIndices() {
        System.out.println("limpiarNuevoTiposIndices");
        nuevoTiposIndices = new TiposIndices();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposIndices() {
        System.out.println("duplicandoTiposIndices");
        if (index >= 0) {
            duplicarTiposIndices = new TiposIndices();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposIndices.setSecuencia(l);
                duplicarTiposIndices.setCodigo(listTiposIndices.get(index).getCodigo());
                duplicarTiposIndices.setDescripcion(listTiposIndices.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposIndices.setSecuencia(l);
                duplicarTiposIndices.setCodigo(filtrarTiposIndices.get(index).getCodigo());
                duplicarTiposIndices.setDescripcion(filtrarTiposIndices.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposIndices.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposIndices.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposIndices.getDescripcion());

        if (duplicarTiposIndices.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposIndices.size(); x++) {
                if (listTiposIndices.get(x).getCodigo() == duplicarTiposIndices.getCodigo()) {
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
        if (duplicarTiposIndices.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposIndices.getSecuencia() + "  " + duplicarTiposIndices.getCodigo());
            if (crearTiposIndices.contains(duplicarTiposIndices)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposIndices.add(duplicarTiposIndices);
            crearTiposIndices.add(duplicarTiposIndices);
            context.update("form:datosTiposIndices");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposIndices:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposIndices");
                bandera = 0;
                filtrarTiposIndices = null;
                tipoLista = 0;
            }
            duplicarTiposIndices = new TiposIndices();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposIndices.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposIndices() {
        duplicarTiposIndices = new TiposIndices();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposIndicesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSINDICES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposIndicesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSINDICES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposIndices.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSINDICES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSINDICES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposIndices> getListTiposIndices() {
        if (listTiposIndices == null) {
            listTiposIndices = administrarTiposIndices.consultarTiposIndices();
        }
        return listTiposIndices;
    }

    public void setListTiposIndices(List<TiposIndices> listTiposIndices) {
        this.listTiposIndices = listTiposIndices;
    }

    public List<TiposIndices> getFiltrarTiposIndices() {
        return filtrarTiposIndices;
    }

    public void setFiltrarTiposIndices(List<TiposIndices> filtrarTiposIndices) {
        this.filtrarTiposIndices = filtrarTiposIndices;
    }

    public TiposIndices getNuevoTiposIndices() {
        return nuevoTiposIndices;
    }

    public void setNuevoTiposIndices(TiposIndices nuevoTiposIndices) {
        this.nuevoTiposIndices = nuevoTiposIndices;
    }

    public TiposIndices getDuplicarTiposIndices() {
        return duplicarTiposIndices;
    }

    public void setDuplicarTiposIndices(TiposIndices duplicarTiposIndices) {
        this.duplicarTiposIndices = duplicarTiposIndices;
    }

    public TiposIndices getEditarTiposIndices() {
        return editarTiposIndices;
    }

    public void setEditarTiposIndices(TiposIndices editarTiposIndices) {
        this.editarTiposIndices = editarTiposIndices;
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposConclusiones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposConclusionesInterface;
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
public class ControlTiposConclusiones implements Serializable {

    @EJB
    AdministrarTiposConclusionesInterface administrarTiposConclusiones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposConclusiones> listTiposConclusiones;
    private List<TiposConclusiones> filtrarTiposConclusiones;
    private List<TiposConclusiones> crearTiposConclusiones;
    private List<TiposConclusiones> modificarTiposConclusiones;
    private List<TiposConclusiones> borrarTiposConclusiones;
    private TiposConclusiones nuevoTiposConclusiones;
    private TiposConclusiones duplicarTiposConclusiones;
    private TiposConclusiones editarTiposConclusiones;
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

    public ControlTiposConclusiones() {
        listTiposConclusiones = null;
        crearTiposConclusiones = new ArrayList<TiposConclusiones>();
        modificarTiposConclusiones = new ArrayList<TiposConclusiones>();
        borrarTiposConclusiones = new ArrayList<TiposConclusiones>();
        permitirIndex = true;
        editarTiposConclusiones = new TiposConclusiones();
        nuevoTiposConclusiones = new TiposConclusiones();
        duplicarTiposConclusiones = new TiposConclusiones();
        guardado = true;
        tamano = 302;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposConclusiones.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposConclusiones eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposConclusiones.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listTiposConclusiones.get(index).getCodigo();
                backupDescripcion = listTiposConclusiones.get(index).getDescripcion();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarTiposConclusiones.get(index).getCodigo();
                backupDescripcion = filtrarTiposConclusiones.get(index).getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposConclusiones.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposConclusiones.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposConclusiones");
            bandera = 0;
            filtrarTiposConclusiones = null;
            tipoLista = 0;
        }

        borrarTiposConclusiones.clear();
        crearTiposConclusiones.clear();
        modificarTiposConclusiones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposConclusiones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposConclusiones");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposConclusiones");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposConclusiones");
            bandera = 0;
            filtrarTiposConclusiones = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposConclusiones(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearTiposConclusiones.contains(listTiposConclusiones.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposConclusiones.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (listTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposConclusiones.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposConclusiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposConclusiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposConclusiones.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposConclusiones.isEmpty()) {
                            modificarTiposConclusiones.add(listTiposConclusiones.get(indice));
                        } else if (!modificarTiposConclusiones.contains(listTiposConclusiones.get(indice))) {
                            modificarTiposConclusiones.add(listTiposConclusiones.get(indice));
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
                    context.update("form:datosTiposConclusiones");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposConclusiones.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposConclusiones.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (listTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposConclusiones.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposConclusiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposConclusiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposConclusiones.get(indice).setDescripcion(backupDescripcion);

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
                    context.update("form:datosTiposConclusiones");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearTiposConclusiones.contains(filtrarTiposConclusiones.get(indice))) {
                    if (filtrarTiposConclusiones.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposConclusiones.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposConclusiones.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposConclusiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposConclusiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposConclusiones.isEmpty()) {
                            modificarTiposConclusiones.add(filtrarTiposConclusiones.get(indice));
                        } else if (!modificarTiposConclusiones.contains(filtrarTiposConclusiones.get(indice))) {
                            modificarTiposConclusiones.add(filtrarTiposConclusiones.get(indice));
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
                } else {if (filtrarTiposConclusiones.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposConclusiones.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposConclusiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposConclusiones.get(indice).getCodigo() == listTiposConclusiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposConclusiones.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposConclusiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposConclusiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposConclusiones.get(indice).setDescripcion(backupDescripcion);
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
            context.update("form:datosTiposConclusiones");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposConclusiones() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposConclusiones");
                if (!modificarTiposConclusiones.isEmpty() && modificarTiposConclusiones.contains(listTiposConclusiones.get(index))) {
                    int modIndex = modificarTiposConclusiones.indexOf(listTiposConclusiones.get(index));
                    modificarTiposConclusiones.remove(modIndex);
                    borrarTiposConclusiones.add(listTiposConclusiones.get(index));
                } else if (!crearTiposConclusiones.isEmpty() && crearTiposConclusiones.contains(listTiposConclusiones.get(index))) {
                    int crearIndex = crearTiposConclusiones.indexOf(listTiposConclusiones.get(index));
                    crearTiposConclusiones.remove(crearIndex);
                } else {
                    borrarTiposConclusiones.add(listTiposConclusiones.get(index));
                }
                listTiposConclusiones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposConclusiones ");
                if (!modificarTiposConclusiones.isEmpty() && modificarTiposConclusiones.contains(filtrarTiposConclusiones.get(index))) {
                    int modIndex = modificarTiposConclusiones.indexOf(filtrarTiposConclusiones.get(index));
                    modificarTiposConclusiones.remove(modIndex);
                    borrarTiposConclusiones.add(filtrarTiposConclusiones.get(index));
                } else if (!crearTiposConclusiones.isEmpty() && crearTiposConclusiones.contains(filtrarTiposConclusiones.get(index))) {
                    int crearIndex = crearTiposConclusiones.indexOf(filtrarTiposConclusiones.get(index));
                    crearTiposConclusiones.remove(crearIndex);
                } else {
                    borrarTiposConclusiones.add(filtrarTiposConclusiones.get(index));
                }
                int VCIndex = listTiposConclusiones.indexOf(filtrarTiposConclusiones.get(index));
                listTiposConclusiones.remove(VCIndex);
                filtrarTiposConclusiones.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposConclusiones");
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
        BigInteger contarTSgruposTiposEntidadesTipoEntidad;
        BigInteger contarProcesosTipoConclusion;

        try {
            System.err.println("Control Secuencia de ControlTiposConclusiones ");
            if (tipoLista == 0) {
                contarProcesosTipoConclusion = administrarTiposConclusiones.contarProcesosTipoConclusion(listTiposConclusiones.get(index).getSecuencia());
            } else {
                contarProcesosTipoConclusion = administrarTiposConclusiones.contarProcesosTipoConclusion(filtrarTiposConclusiones.get(index).getSecuencia());
            }
            if ( contarProcesosTipoConclusion.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposConclusiones();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarTSgruposTiposEntidadesTipoEntidad = new BigInteger("-1");
                contarProcesosTipoConclusion = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposConclusiones verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposConclusiones.isEmpty() || !crearTiposConclusiones.isEmpty() || !modificarTiposConclusiones.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposConclusiones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposConclusiones");
            if (!borrarTiposConclusiones.isEmpty()) {
                administrarTiposConclusiones.borrarTiposConclusiones(borrarTiposConclusiones);
                //mostrarBorrados
                registrosBorrados = borrarTiposConclusiones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposConclusiones.clear();
            }
            if (!modificarTiposConclusiones.isEmpty()) {
                administrarTiposConclusiones.modificarTiposConclusiones(modificarTiposConclusiones);
                modificarTiposConclusiones.clear();
            }
            if (!crearTiposConclusiones.isEmpty()) {
                administrarTiposConclusiones.crearTiposConclusiones(crearTiposConclusiones);
                crearTiposConclusiones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposConclusiones = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosTiposConclusiones");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposConclusiones = listTiposConclusiones.get(index);
            }
            if (tipoLista == 1) {
                editarTiposConclusiones = filtrarTiposConclusiones.get(index);
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

    public void agregarNuevoTiposConclusiones() {
        System.out.println("agregarNuevoTiposConclusiones");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposConclusiones.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposConclusiones.getCodigo());

            for (int x = 0; x < listTiposConclusiones.size(); x++) {
                if (listTiposConclusiones.get(x).getCodigo() == nuevoTiposConclusiones.getCodigo()) {
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
        if (nuevoTiposConclusiones.getDescripcion().equals(" ")) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposConclusiones");
                bandera = 0;
                filtrarTiposConclusiones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposConclusiones.setSecuencia(l);

            crearTiposConclusiones.add(nuevoTiposConclusiones);

            listTiposConclusiones.add(nuevoTiposConclusiones);
            nuevoTiposConclusiones = new TiposConclusiones();
            context.update("form:datosTiposConclusiones");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposConclusiones.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposConclusiones() {
        System.out.println("limpiarNuevoTiposConclusiones");
        nuevoTiposConclusiones = new TiposConclusiones();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposConclusiones() {
        System.out.println("duplicandoTiposConclusiones");
        if (index >= 0) {
            duplicarTiposConclusiones = new TiposConclusiones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposConclusiones.setSecuencia(l);
                duplicarTiposConclusiones.setCodigo(listTiposConclusiones.get(index).getCodigo());
                duplicarTiposConclusiones.setDescripcion(listTiposConclusiones.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposConclusiones.setSecuencia(l);
                duplicarTiposConclusiones.setCodigo(filtrarTiposConclusiones.get(index).getCodigo());
                duplicarTiposConclusiones.setDescripcion(filtrarTiposConclusiones.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposConclusiones.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposConclusiones.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposConclusiones.getDescripcion());

        if (duplicarTiposConclusiones.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposConclusiones.size(); x++) {
                if (listTiposConclusiones.get(x).getCodigo() == duplicarTiposConclusiones.getCodigo()) {
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
        if (duplicarTiposConclusiones.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposConclusiones.getSecuencia() + "  " + duplicarTiposConclusiones.getCodigo());
            if (crearTiposConclusiones.contains(duplicarTiposConclusiones)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposConclusiones.add(duplicarTiposConclusiones);
            crearTiposConclusiones.add(duplicarTiposConclusiones);
            context.update("form:datosTiposConclusiones");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposConclusiones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposConclusiones");
                bandera = 0;
                filtrarTiposConclusiones = null;
                tipoLista = 0;
            }
            duplicarTiposConclusiones = new TiposConclusiones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposConclusiones.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposConclusiones() {
        duplicarTiposConclusiones = new TiposConclusiones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposConclusionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSCONCLUSIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposConclusionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSCONCLUSIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposConclusiones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCONCLUSIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCONCLUSIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposConclusiones> getListTiposConclusiones() {
        if (listTiposConclusiones == null) {
            listTiposConclusiones = administrarTiposConclusiones.consultarTiposConclusiones();
        }
        return listTiposConclusiones;
    }

    public void setListTiposConclusiones(List<TiposConclusiones> listTiposConclusiones) {
        this.listTiposConclusiones = listTiposConclusiones;
    }

    public List<TiposConclusiones> getFiltrarTiposConclusiones() {
        return filtrarTiposConclusiones;
    }

    public void setFiltrarTiposConclusiones(List<TiposConclusiones> filtrarTiposConclusiones) {
        this.filtrarTiposConclusiones = filtrarTiposConclusiones;
    }

    public TiposConclusiones getNuevoTiposConclusiones() {
        return nuevoTiposConclusiones;
    }

    public void setNuevoTiposConclusiones(TiposConclusiones nuevoTiposConclusiones) {
        this.nuevoTiposConclusiones = nuevoTiposConclusiones;
    }

    public TiposConclusiones getDuplicarTiposConclusiones() {
        return duplicarTiposConclusiones;
    }

    public void setDuplicarTiposConclusiones(TiposConclusiones duplicarTiposConclusiones) {
        this.duplicarTiposConclusiones = duplicarTiposConclusiones;
    }

    public TiposConclusiones getEditarTiposConclusiones() {
        return editarTiposConclusiones;
    }

    public void setEditarTiposConclusiones(TiposConclusiones editarTiposConclusiones) {
        this.editarTiposConclusiones = editarTiposConclusiones;
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

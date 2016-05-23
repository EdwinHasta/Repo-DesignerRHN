/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Tiposviajeros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposViajerosInterface;
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
public class ControlTiposViajeros implements Serializable {

    @EJB
    AdministrarTiposViajerosInterface administrarTiposViajeros;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Tiposviajeros> listTiposViajeros;
    private List<Tiposviajeros> filtrarTiposViajeros;
    private List<Tiposviajeros> crearTiposViajeros;
    private List<Tiposviajeros> modificarTiposViajeros;
    private List<Tiposviajeros> borrarTiposViajeros;
    private Tiposviajeros nuevoTiposViajeros;
    private Tiposviajeros duplicarTiposViajeros;
    private Tiposviajeros editarTiposViajeros;
    private Tiposviajeros tiposViajeroSeleccionado;
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
    private Integer backUpCodigo;
    private String backUpDescripcion;

    public ControlTiposViajeros() {
        listTiposViajeros = null;
        crearTiposViajeros = new ArrayList<Tiposviajeros>();
        modificarTiposViajeros = new ArrayList<Tiposviajeros>();
        borrarTiposViajeros = new ArrayList<Tiposviajeros>();
        permitirIndex = true;
        editarTiposViajeros = new Tiposviajeros();
        nuevoTiposViajeros = new Tiposviajeros();
        duplicarTiposViajeros = new Tiposviajeros();
        guardado = true;
        tamano = 270;
        System.out.println("controlTiposViajeros Constructor");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            System.out.println("ControlTiposViajeros PostConstruct ");
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposViajeros.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void inicializarLista() {
        getListTiposViajeros();
        if (listTiposViajeros != null) {
            tiposViajeroSeleccionado = listTiposViajeros.get(0);
            modificarInfoRegistro(listTiposViajeros.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    public void eventoFiltrar() {
        System.out.println("\n ENTRE A ControlTiposViajeros.eventoFiltrar \n");
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        modificarInfoRegistro(filtrarTiposViajeros.size());
        context.update("form:informacionRegistro");

    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backUpCodigo = listTiposViajeros.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);
                } else if (cualCelda == 1) {
                    backUpDescripcion = listTiposViajeros.get(index).getNombre();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);
                }
                secRegistro = listTiposViajeros.get(index).getSecuencia();
            } else {
                if (cualCelda == 0) {
                    backUpCodigo = filtrarTiposViajeros.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);

                } else if (cualCelda == 1) {
                    backUpDescripcion = filtrarTiposViajeros.get(index).getNombre();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);

                }
                secRegistro = filtrarTiposViajeros.get(index).getSecuencia();
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposViajeros.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposViajeros.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }
    private String infoRegistro;

    public void cancelarModificacion() {
        index = -1;
        secRegistro = null;
        cerrarFiltrado();
        borrarTiposViajeros.clear();
        crearTiposViajeros.clear();
        modificarTiposViajeros.clear();
        listTiposViajeros = null;
        k = 0;
        guardado = true;
        permitirIndex = true;
        getListTiposViajeros();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposViajeros == null) {
            modificarInfoRegistro(0);
        } else {
            modificarInfoRegistro(listTiposViajeros.size());
        }
        context.update("form:informacionRegistro");
        context.update("form:datosTiposViajeros");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        cerrarFiltrado();
        borrarTiposViajeros.clear();
        crearTiposViajeros.clear();
        modificarTiposViajeros.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposViajeros = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposViajeros");
        context.update("form:ACEPTAR");
    }

    public void cerrarFiltrado() {
        FacesContext c = FacesContext.getCurrentInstance();
        codigo = (Column) c.getViewRoot().findComponent("form:datosTiposViajeros:codigo");
        codigo.setFilterStyle("display: none; visibility: hidden;");
        descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposViajeros:descripcion");
        descripcion.setFilterStyle("display: none; visibility: hidden;");
        RequestContext.getCurrentInstance().update("form:datosTiposViajeros");
        bandera = 0;
        filtrarTiposViajeros = null;
        tipoLista = 0;
        tamano = 270;
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposViajeros:codigo");
            codigo.setFilterStyle("width: 170px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposViajeros:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposViajeros");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    public void modificarTiposViajeros(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposViajeros.contains(listTiposViajeros.get(indice))) {
                    if (listTiposViajeros.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposViajeros.size(); j++) {
                            if (j != indice) {
                                if (listTiposViajeros.get(indice).getCodigo().equals(listTiposViajeros.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposViajeros.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposViajeros.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }
                    if (listTiposViajeros.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposViajeros.isEmpty()) {
                            modificarTiposViajeros.add(listTiposViajeros.get(indice));
                        } else if (!modificarTiposViajeros.contains(listTiposViajeros.get(indice))) {
                            modificarTiposViajeros.add(listTiposViajeros.get(indice));
                        }
                        if (guardado) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (listTiposViajeros.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposViajeros.size(); j++) {
                            if (j != indice) {
                                if (listTiposViajeros.get(indice).getCodigo().equals(listTiposViajeros.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposViajeros.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposViajeros.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }
                    if (listTiposViajeros.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }

                    if (banderita == true) {

                        if (guardado) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearTiposViajeros.contains(filtrarTiposViajeros.get(indice))) {
                    if (filtrarTiposViajeros.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposViajeros.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {

                        for (int j = 0; j < filtrarTiposViajeros.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposViajeros.get(indice).getCodigo().equals(filtrarTiposViajeros.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposViajeros.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposViajeros.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }
                    if (filtrarTiposViajeros.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposViajeros.isEmpty()) {
                            modificarTiposViajeros.add(filtrarTiposViajeros.get(indice));
                        } else if (!modificarTiposViajeros.contains(filtrarTiposViajeros.get(indice))) {
                            modificarTiposViajeros.add(filtrarTiposViajeros.get(indice));
                        }
                        if (guardado) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (filtrarTiposViajeros.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposViajeros.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarTiposViajeros.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposViajeros.get(indice).getCodigo().equals(filtrarTiposViajeros.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposViajeros.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposViajeros.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }
                    if (filtrarTiposViajeros.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposViajeros.get(indice).setNombre(backUpDescripcion);
                    }

                    if (banderita == true) {

                        if (guardado) {
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
            context.update("form:datosTiposViajeros");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposViajeros() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposViajeros");
                if (!modificarTiposViajeros.isEmpty() && modificarTiposViajeros.contains(listTiposViajeros.get(index))) {
                    int modIndex = modificarTiposViajeros.indexOf(listTiposViajeros.get(index));
                    modificarTiposViajeros.remove(modIndex);
                    borrarTiposViajeros.add(listTiposViajeros.get(index));
                } else if (!crearTiposViajeros.isEmpty() && crearTiposViajeros.contains(listTiposViajeros.get(index))) {
                    int crearIndex = crearTiposViajeros.indexOf(listTiposViajeros.get(index));
                    crearTiposViajeros.remove(crearIndex);
                } else {
                    borrarTiposViajeros.add(listTiposViajeros.get(index));
                }
                listTiposViajeros.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposViajeros ");
                if (!modificarTiposViajeros.isEmpty() && modificarTiposViajeros.contains(filtrarTiposViajeros.get(index))) {
                    int modIndex = modificarTiposViajeros.indexOf(filtrarTiposViajeros.get(index));
                    modificarTiposViajeros.remove(modIndex);
                    borrarTiposViajeros.add(filtrarTiposViajeros.get(index));
                } else if (!crearTiposViajeros.isEmpty() && crearTiposViajeros.contains(filtrarTiposViajeros.get(index))) {
                    int crearIndex = crearTiposViajeros.indexOf(filtrarTiposViajeros.get(index));
                    crearTiposViajeros.remove(crearIndex);
                } else {
                    borrarTiposViajeros.add(filtrarTiposViajeros.get(index));
                }
                int VCIndex = listTiposViajeros.indexOf(filtrarTiposViajeros.get(index));
                listTiposViajeros.remove(VCIndex);
                filtrarTiposViajeros.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposViajeros");
            modificarInfoRegistro(listTiposViajeros.size());
            context.update("form:informacionRegistro");

            index = -1;
            secRegistro = null;

            if (guardado) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        BigInteger contarTiposLegalizaciones;
        BigInteger contarVigenciasViajeros;

        try {
            System.err.println("Control Secuencia de ControlTiposViajeros ");
            if (tipoLista == 0) {
                contarTiposLegalizaciones = administrarTiposViajeros.contarTiposLegalizaciones(listTiposViajeros.get(index).getSecuencia());
                contarVigenciasViajeros = administrarTiposViajeros.contarVigenciasViajeros(listTiposViajeros.get(index).getSecuencia());
            } else {
                contarTiposLegalizaciones = administrarTiposViajeros.contarTiposLegalizaciones(filtrarTiposViajeros.get(index).getSecuencia());
                contarVigenciasViajeros = administrarTiposViajeros.contarVigenciasViajeros(filtrarTiposViajeros.get(index).getSecuencia());
            }
            if (contarTiposLegalizaciones.equals(new BigInteger("0"))
                    && contarVigenciasViajeros.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposViajeros();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarTiposLegalizaciones = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposViajeros verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposViajeros.isEmpty() || !crearTiposViajeros.isEmpty() || !modificarTiposViajeros.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposViajeros() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposViajeros");
            if (!borrarTiposViajeros.isEmpty()) {
                administrarTiposViajeros.borrarTiposViajeros(borrarTiposViajeros);
                //mostrarBorrados
                registrosBorrados = borrarTiposViajeros.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposViajeros.clear();
            }
            if (!modificarTiposViajeros.isEmpty()) {
                administrarTiposViajeros.modificarTiposViajeros(modificarTiposViajeros);
                modificarTiposViajeros.clear();
            }
            if (!crearTiposViajeros.isEmpty()) {
                administrarTiposViajeros.crearTiposViajeros(crearTiposViajeros);
                crearTiposViajeros.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposViajeros = null;
            context.update("form:datosTiposViajeros");
            k = 0;
            guardado = true;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposViajeros = listTiposViajeros.get(index);
            }
            if (tipoLista == 1) {
                editarTiposViajeros = filtrarTiposViajeros.get(index);
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

    public void agregarNuevoTiposViajeros() {
        System.out.println("agregarNuevoTiposViajeros");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposViajeros.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposViajeros.getCodigo());

            for (int x = 0; x < listTiposViajeros.size(); x++) {
                if (listTiposViajeros.get(x).getCodigo().equals(nuevoTiposViajeros.getCodigo())) {
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
        if (nuevoTiposViajeros.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoTiposViajeros.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                cerrarFiltrado();
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposViajeros.setSecuencia(l);

            crearTiposViajeros.add(nuevoTiposViajeros);
            listTiposViajeros.add(nuevoTiposViajeros);
            tiposViajeroSeleccionado = listTiposViajeros.get(listTiposViajeros.indexOf(nuevoTiposViajeros));
            nuevoTiposViajeros = new Tiposviajeros();
            context.update("form:datosTiposViajeros");
            modificarInfoRegistro(listTiposViajeros.size());
            context.update("form:informacionRegistro");

            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposViajeros.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposViajeros() {
        System.out.println("limpiarNuevoTiposViajeros");
        nuevoTiposViajeros = new Tiposviajeros();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposViajeros() {
        System.out.println("duplicandoTiposViajeros");
        if (index >= 0) {
            duplicarTiposViajeros = new Tiposviajeros();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposViajeros.setSecuencia(l);
                duplicarTiposViajeros.setCodigo(listTiposViajeros.get(index).getCodigo());
                duplicarTiposViajeros.setNombre(listTiposViajeros.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarTiposViajeros.setSecuencia(l);
                duplicarTiposViajeros.setCodigo(filtrarTiposViajeros.get(index).getCodigo());
                duplicarTiposViajeros.setNombre(filtrarTiposViajeros.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposViajeros.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposViajeros.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposViajeros.getNombre());

        if (duplicarTiposViajeros.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposViajeros.size(); x++) {
                if (listTiposViajeros.get(x).getCodigo().equals(duplicarTiposViajeros.getCodigo())) {
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
        if (duplicarTiposViajeros.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTiposViajeros.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposViajeros.getSecuencia() + "  " + duplicarTiposViajeros.getCodigo());
            if (crearTiposViajeros.contains(duplicarTiposViajeros)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposViajeros.add(duplicarTiposViajeros);
            crearTiposViajeros.add(duplicarTiposViajeros);
            tiposViajeroSeleccionado = listTiposViajeros.get(listTiposViajeros.indexOf(duplicarTiposViajeros));
            context.update("form:datosTiposViajeros");
            index = -1;
            secRegistro = null;
            if (guardado) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            modificarInfoRegistro(listTiposViajeros.size());
            context.update("form:informacionRegistro");

            if (bandera == 1) {
                cerrarFiltrado();
            }
            duplicarTiposViajeros = new Tiposviajeros();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposViajeros.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposViajeros() {
        duplicarTiposViajeros = new Tiposviajeros();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposViajerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSVIAJEROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposViajerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSVIAJEROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (secRegistro != null) {
            int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSVIAJEROS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSVIAJEROS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Tiposviajeros> getListTiposViajeros() {
        if (listTiposViajeros == null) {
            listTiposViajeros = administrarTiposViajeros.consultarTiposViajeros();
        }
        return listTiposViajeros;
    }

    public void setListTiposViajeros(List<Tiposviajeros> listTiposViajeros) {
        this.listTiposViajeros = listTiposViajeros;
    }

    public List<Tiposviajeros> getFiltrarTiposViajeros() {
        return filtrarTiposViajeros;
    }

    public void setFiltrarTiposViajeros(List<Tiposviajeros> filtrarTiposViajeros) {
        this.filtrarTiposViajeros = filtrarTiposViajeros;
    }

    public Tiposviajeros getNuevoTiposViajeros() {
        return nuevoTiposViajeros;
    }

    public void setNuevoTiposViajeros(Tiposviajeros nuevoTiposViajeros) {
        this.nuevoTiposViajeros = nuevoTiposViajeros;
    }

    public Tiposviajeros getDuplicarTiposViajeros() {
        return duplicarTiposViajeros;
    }

    public void setDuplicarTiposViajeros(Tiposviajeros duplicarTiposViajeros) {
        this.duplicarTiposViajeros = duplicarTiposViajeros;
    }

    public Tiposviajeros getEditarTiposViajeros() {
        return editarTiposViajeros;
    }

    public void setEditarTiposViajeros(Tiposviajeros editarTiposViajeros) {
        this.editarTiposViajeros = editarTiposViajeros;
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

    public Tiposviajeros getTiposViajerosSeleccionado() {
        return tiposViajeroSeleccionado;
    }

    public void setTiposViajerosSeleccionado(Tiposviajeros tiposViajeroSeleccionado) {
        this.tiposViajeroSeleccionado = tiposViajeroSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }
}

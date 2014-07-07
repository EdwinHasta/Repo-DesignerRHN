/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposFamiliares;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposFamiliaresInterface;
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
public class ControlTiposFamiliares implements Serializable {

    @EJB
    AdministrarTiposFamiliaresInterface administrarTiposFamiliares;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<TiposFamiliares> listTiposFamiliares;
    private List<TiposFamiliares> filtrarTiposFamiliares;
    private List<TiposFamiliares> crearTiposFamiliares;
    private List<TiposFamiliares> modificarTiposFamiliares;
    private List<TiposFamiliares> borrarTiposFamiliares;
    private TiposFamiliares nuevoTiposFamiliares;
    private TiposFamiliares duplicarTiposFamiliares;
    private TiposFamiliares editarTiposFamiliares;
    private TiposFamiliares tiposFamiliaresSeleccionado;
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

    public ControlTiposFamiliares() {
        listTiposFamiliares = null;
        crearTiposFamiliares = new ArrayList<TiposFamiliares>();
        modificarTiposFamiliares = new ArrayList<TiposFamiliares>();
        borrarTiposFamiliares = new ArrayList<TiposFamiliares>();
        permitirIndex = true;
        editarTiposFamiliares = new TiposFamiliares();
        nuevoTiposFamiliares = new TiposFamiliares();
        duplicarTiposFamiliares = new TiposFamiliares();
        guardado = true;
        tamano = 270;
        System.out.println("controlTiposFamiliares Constructor");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            System.out.println("ControlTiposFamiliares PostConstruct ");
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposFamiliares.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposFamiliares.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarTiposFamiliares.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposFamiliares eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backUpCodigo = listTiposFamiliares.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);
                } else if (cualCelda == 1) {
                    backUpDescripcion = listTiposFamiliares.get(index).getTipo();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);
                }
                secRegistro = listTiposFamiliares.get(index).getSecuencia();
            } else {
                if (cualCelda == 0) {
                    backUpCodigo = filtrarTiposFamiliares.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);

                } else if (cualCelda == 1) {
                    backUpDescripcion = filtrarTiposFamiliares.get(index).getTipo();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);

                }
                secRegistro = filtrarTiposFamiliares.get(index).getSecuencia();
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposFamiliares.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposFamiliares.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }
    private String infoRegistro;

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
            bandera = 0;
            filtrarTiposFamiliares = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarTiposFamiliares.clear();
        crearTiposFamiliares.clear();
        modificarTiposFamiliares.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposFamiliares = null;
        guardado = true;
        permitirIndex = true;
        getListTiposFamiliares();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposFamiliares == null || listTiposFamiliares.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listTiposFamiliares.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosTiposFamiliares");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
            bandera = 0;
            filtrarTiposFamiliares = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarTiposFamiliares.clear();
        crearTiposFamiliares.clear();
        modificarTiposFamiliares.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposFamiliares = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposFamiliares");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
            codigo.setFilterStyle("width: 170px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
            bandera = 0;
            filtrarTiposFamiliares = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposFamiliares(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearTiposFamiliares.contains(listTiposFamiliares.get(indice))) {
                    if (listTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (listTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }
                    if (listTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposFamiliares.isEmpty()) {
                            modificarTiposFamiliares.add(listTiposFamiliares.get(indice));
                        } else if (!modificarTiposFamiliares.contains(listTiposFamiliares.get(indice))) {
                            modificarTiposFamiliares.add(listTiposFamiliares.get(indice));
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
                    if (listTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (listTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }
                    if (listTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }

                    if (banderita == true) {

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
            } else {

                if (!crearTiposFamiliares.contains(filtrarTiposFamiliares.get(indice))) {
                    if (filtrarTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {

                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }
                    if (filtrarTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposFamiliares.isEmpty()) {
                            modificarTiposFamiliares.add(filtrarTiposFamiliares.get(indice));
                        } else if (!modificarTiposFamiliares.contains(filtrarTiposFamiliares.get(indice))) {
                            modificarTiposFamiliares.add(filtrarTiposFamiliares.get(indice));
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
                    if (filtrarTiposFamiliares.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {

                        for (int j = 0; j < listTiposFamiliares.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposFamiliares.get(indice).getCodigo() == listTiposFamiliares.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposFamiliares.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposFamiliares.get(indice).getTipo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }
                    if (filtrarTiposFamiliares.get(indice).getTipo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposFamiliares.get(indice).setTipo(backUpDescripcion);
                    }

                    if (banderita == true) {

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
            context.update("form:datosTiposFamiliares");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposFamiliares() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposFamiliares");
                if (!modificarTiposFamiliares.isEmpty() && modificarTiposFamiliares.contains(listTiposFamiliares.get(index))) {
                    int modIndex = modificarTiposFamiliares.indexOf(listTiposFamiliares.get(index));
                    modificarTiposFamiliares.remove(modIndex);
                    borrarTiposFamiliares.add(listTiposFamiliares.get(index));
                } else if (!crearTiposFamiliares.isEmpty() && crearTiposFamiliares.contains(listTiposFamiliares.get(index))) {
                    int crearIndex = crearTiposFamiliares.indexOf(listTiposFamiliares.get(index));
                    crearTiposFamiliares.remove(crearIndex);
                } else {
                    borrarTiposFamiliares.add(listTiposFamiliares.get(index));
                }
                listTiposFamiliares.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposFamiliares ");
                if (!modificarTiposFamiliares.isEmpty() && modificarTiposFamiliares.contains(filtrarTiposFamiliares.get(index))) {
                    int modIndex = modificarTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                    modificarTiposFamiliares.remove(modIndex);
                    borrarTiposFamiliares.add(filtrarTiposFamiliares.get(index));
                } else if (!crearTiposFamiliares.isEmpty() && crearTiposFamiliares.contains(filtrarTiposFamiliares.get(index))) {
                    int crearIndex = crearTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                    crearTiposFamiliares.remove(crearIndex);
                } else {
                    borrarTiposFamiliares.add(filtrarTiposFamiliares.get(index));
                }
                int VCIndex = listTiposFamiliares.indexOf(filtrarTiposFamiliares.get(index));
                listTiposFamiliares.remove(VCIndex);
                filtrarTiposFamiliares.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposFamiliares");
            infoRegistro = "Cantidad de registros: " + listTiposFamiliares.size();
            context.update("form:informacionRegistro");

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
        BigInteger contarHvReferenciasTipoFamiliar;

        try {
            System.err.println("Control Secuencia de ControlTiposFamiliares ");
            if (tipoLista == 0) {
                contarHvReferenciasTipoFamiliar = administrarTiposFamiliares.contarHvReferenciasTipoFamiliar(listTiposFamiliares.get(index).getSecuencia());
            } else {
                contarHvReferenciasTipoFamiliar = administrarTiposFamiliares.contarHvReferenciasTipoFamiliar(filtrarTiposFamiliares.get(index).getSecuencia());
            }
            if (contarHvReferenciasTipoFamiliar.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposFamiliares();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarHvReferenciasTipoFamiliar = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposFamiliares verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposFamiliares.isEmpty() || !crearTiposFamiliares.isEmpty() || !modificarTiposFamiliares.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposFamiliares() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposFamiliares");
            if (!borrarTiposFamiliares.isEmpty()) {
                administrarTiposFamiliares.borrarTiposFamiliares(borrarTiposFamiliares);
                //mostrarBorrados
                registrosBorrados = borrarTiposFamiliares.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposFamiliares.clear();
            }
            if (!modificarTiposFamiliares.isEmpty()) {
                administrarTiposFamiliares.modificarTiposFamiliares(modificarTiposFamiliares);
                modificarTiposFamiliares.clear();
            }
            if (!crearTiposFamiliares.isEmpty()) {
                administrarTiposFamiliares.crearTiposFamiliares(crearTiposFamiliares);
                crearTiposFamiliares.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposFamiliares = null;
            context.update("form:datosTiposFamiliares");
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
                editarTiposFamiliares = listTiposFamiliares.get(index);
            }
            if (tipoLista == 1) {
                editarTiposFamiliares = filtrarTiposFamiliares.get(index);
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

    public void agregarNuevoTiposFamiliares() {
        System.out.println("agregarNuevoTiposFamiliares");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposFamiliares.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposFamiliares.getCodigo());

            for (int x = 0; x < listTiposFamiliares.size(); x++) {
                if (listTiposFamiliares.get(x).getCodigo() == nuevoTiposFamiliares.getCodigo()) {
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
        if (nuevoTiposFamiliares.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " *Parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoTiposFamiliares.getTipo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
                bandera = 0;
                filtrarTiposFamiliares = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposFamiliares.setSecuencia(l);

            crearTiposFamiliares.add(nuevoTiposFamiliares);

            listTiposFamiliares.add(nuevoTiposFamiliares);
            nuevoTiposFamiliares = new TiposFamiliares();
            context.update("form:datosTiposFamiliares");
            infoRegistro = "Cantidad de registros: " + listTiposFamiliares.size();
            context.update("form:informacionRegistro");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposFamiliares.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposFamiliares() {
        System.out.println("limpiarNuevoTiposFamiliares");
        nuevoTiposFamiliares = new TiposFamiliares();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposFamiliares() {
        System.out.println("duplicandoTiposFamiliares");
        if (index >= 0) {
            duplicarTiposFamiliares = new TiposFamiliares();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposFamiliares.setSecuencia(l);
                duplicarTiposFamiliares.setCodigo(listTiposFamiliares.get(index).getCodigo());
                duplicarTiposFamiliares.setTipo(listTiposFamiliares.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarTiposFamiliares.setSecuencia(l);
                duplicarTiposFamiliares.setCodigo(filtrarTiposFamiliares.get(index).getCodigo());
                duplicarTiposFamiliares.setTipo(filtrarTiposFamiliares.get(index).getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposFamiliares.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposFamiliares.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposFamiliares.getTipo());

        if (duplicarTiposFamiliares.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposFamiliares.size(); x++) {
                if (listTiposFamiliares.get(x).getCodigo() == duplicarTiposFamiliares.getCodigo()) {
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
        if (duplicarTiposFamiliares.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " *Parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTiposFamiliares.getTipo().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Parentesco \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposFamiliares.getSecuencia() + "  " + duplicarTiposFamiliares.getCodigo());
            if (crearTiposFamiliares.contains(duplicarTiposFamiliares)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposFamiliares.add(duplicarTiposFamiliares);
            crearTiposFamiliares.add(duplicarTiposFamiliares);
            context.update("form:datosTiposFamiliares");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            infoRegistro = "Cantidad de registros: " + listTiposFamiliares.size();
            context.update("form:informacionRegistro");

            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposFamiliares:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposFamiliares");
                bandera = 0;
                filtrarTiposFamiliares = null;
                tipoLista = 0;
            }
            duplicarTiposFamiliares = new TiposFamiliares();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposFamiliares.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposFamiliares() {
        duplicarTiposFamiliares = new TiposFamiliares();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposFamiliaresExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposFamiliaresExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSFAMILIARES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposFamiliares.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSFAMILIARES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSFAMILIARES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposFamiliares> getListTiposFamiliares() {
        if (listTiposFamiliares == null) {
            System.out.println("ControlTiposFamiliares getListTiposFamiliares");
            listTiposFamiliares = administrarTiposFamiliares.consultarTiposFamiliares();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposFamiliares == null || listTiposFamiliares.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listTiposFamiliares.size();
        }
        return listTiposFamiliares;
    }

    public void setListTiposFamiliares(List<TiposFamiliares> listTiposFamiliares) {
        this.listTiposFamiliares = listTiposFamiliares;
    }

    public List<TiposFamiliares> getFiltrarTiposFamiliares() {
        return filtrarTiposFamiliares;
    }

    public void setFiltrarTiposFamiliares(List<TiposFamiliares> filtrarTiposFamiliares) {
        this.filtrarTiposFamiliares = filtrarTiposFamiliares;
    }

    public TiposFamiliares getNuevoTiposFamiliares() {
        return nuevoTiposFamiliares;
    }

    public void setNuevoTiposFamiliares(TiposFamiliares nuevoTiposFamiliares) {
        this.nuevoTiposFamiliares = nuevoTiposFamiliares;
    }

    public TiposFamiliares getDuplicarTiposFamiliares() {
        return duplicarTiposFamiliares;
    }

    public void setDuplicarTiposFamiliares(TiposFamiliares duplicarTiposFamiliares) {
        this.duplicarTiposFamiliares = duplicarTiposFamiliares;
    }

    public TiposFamiliares getEditarTiposFamiliares() {
        return editarTiposFamiliares;
    }

    public void setEditarTiposFamiliares(TiposFamiliares editarTiposFamiliares) {
        this.editarTiposFamiliares = editarTiposFamiliares;
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

    public TiposFamiliares getTiposFamiliaresSeleccionado() {
        return tiposFamiliaresSeleccionado;
    }

    public void setTiposFamiliaresSeleccionado(TiposFamiliares clasesPensionesSeleccionado) {
        this.tiposFamiliaresSeleccionado = clasesPensionesSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

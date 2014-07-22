/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposChequeos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposChequeosInterface;
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
public class ControlTiposChequeos implements Serializable {

    @EJB
    AdministrarTiposChequeosInterface administrarTiposChequeos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<TiposChequeos> listTiposChequeos;
    private List<TiposChequeos> filtrarTiposChequeos;
    private List<TiposChequeos> crearTiposChequeos;
    private List<TiposChequeos> modificarTiposChequeos;
    private List<TiposChequeos> borrarTiposChequeos;
    private TiposChequeos nuevoTiposChequeos;
    private TiposChequeos duplicarTiposChequeos;
    private TiposChequeos editarTiposChequeos;
    private TiposChequeos tiposChequeosSeleccionado;
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

    public ControlTiposChequeos() {
        listTiposChequeos = null;
        crearTiposChequeos = new ArrayList<TiposChequeos>();
        modificarTiposChequeos = new ArrayList<TiposChequeos>();
        borrarTiposChequeos = new ArrayList<TiposChequeos>();
        permitirIndex = true;
        editarTiposChequeos = new TiposChequeos();
        nuevoTiposChequeos = new TiposChequeos();
        duplicarTiposChequeos = new TiposChequeos();
        guardado = true;
        tamano = 270;
        System.out.println("controlTiposChequeos Constructor");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            System.out.println("ControlTiposChequeos PostConstruct ");
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposChequeos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    private String paginaAnterior;

    public void recibirPagina(String pagina) {
        paginaAnterior = pagina;
    }

    public String redirigirPaginaAnterior() {
        return paginaAnterior;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposChequeos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarTiposChequeos.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposChequeos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                if (cualCelda == 0) {
                    backUpCodigo = listTiposChequeos.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);
                } else if (cualCelda == 1) {
                    backUpDescripcion = listTiposChequeos.get(index).getDescripcion();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);
                }
                secRegistro = listTiposChequeos.get(index).getSecuencia();
            } else {
                if (cualCelda == 0) {
                    backUpCodigo = filtrarTiposChequeos.get(index).getCodigo();
                    System.out.println(" backUpCodigo : " + backUpCodigo);

                } else if (cualCelda == 1) {
                    backUpDescripcion = filtrarTiposChequeos.get(index).getDescripcion();
                    System.out.println(" backUpDescripcion : " + backUpDescripcion);

                }
                secRegistro = filtrarTiposChequeos.get(index).getSecuencia();
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposChequeos.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposChequeos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
            bandera = 0;
            filtrarTiposChequeos = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarTiposChequeos.clear();
        crearTiposChequeos.clear();
        modificarTiposChequeos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposChequeos = null;
        guardado = true;
        permitirIndex = true;
        getListTiposChequeos();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposChequeos == null || listTiposChequeos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listTiposChequeos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosTiposChequeos");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
            bandera = 0;
            filtrarTiposChequeos = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarTiposChequeos.clear();
        crearTiposChequeos.clear();
        modificarTiposChequeos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposChequeos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposChequeos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
            codigo.setFilterStyle("width: 170px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
            bandera = 0;
            filtrarTiposChequeos = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposChequeos(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearTiposChequeos.contains(listTiposChequeos.get(indice))) {
                    if (listTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (listTiposChequeos.get(indice).getCodigo().equals(listTiposChequeos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposChequeos.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (listTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposChequeos.isEmpty()) {
                            modificarTiposChequeos.add(listTiposChequeos.get(indice));
                        } else if (!modificarTiposChequeos.contains(listTiposChequeos.get(indice))) {
                            modificarTiposChequeos.add(listTiposChequeos.get(indice));
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
                    if (listTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (listTiposChequeos.get(indice).getCodigo().equals(listTiposChequeos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listTiposChequeos.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (listTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
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

                if (!crearTiposChequeos.contains(filtrarTiposChequeos.get(indice))) {
                    if (filtrarTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposChequeos.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {

                        for (int j = 0; j < filtrarTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposChequeos.get(indice).getCodigo().equals(filtrarTiposChequeos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposChequeos.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (filtrarTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarTiposChequeos.isEmpty()) {
                            modificarTiposChequeos.add(filtrarTiposChequeos.get(indice));
                        } else if (!modificarTiposChequeos.contains(filtrarTiposChequeos.get(indice))) {
                            modificarTiposChequeos.add(filtrarTiposChequeos.get(indice));
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
                    if (filtrarTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        filtrarTiposChequeos.get(indice).setCodigo(backUpCodigo);
                        banderita = false;
                    } else {

                        for (int j = 0; j < filtrarTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposChequeos.get(indice).getCodigo().equals(filtrarTiposChequeos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarTiposChequeos.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (filtrarTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposChequeos.get(indice).setDescripcion(backUpDescripcion);
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
            context.update("form:datosTiposChequeos");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposChequeos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposChequeos");
                if (!modificarTiposChequeos.isEmpty() && modificarTiposChequeos.contains(listTiposChequeos.get(index))) {
                    int modIndex = modificarTiposChequeos.indexOf(listTiposChequeos.get(index));
                    modificarTiposChequeos.remove(modIndex);
                    borrarTiposChequeos.add(listTiposChequeos.get(index));
                } else if (!crearTiposChequeos.isEmpty() && crearTiposChequeos.contains(listTiposChequeos.get(index))) {
                    int crearIndex = crearTiposChequeos.indexOf(listTiposChequeos.get(index));
                    crearTiposChequeos.remove(crearIndex);
                } else {
                    borrarTiposChequeos.add(listTiposChequeos.get(index));
                }
                listTiposChequeos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposChequeos ");
                if (!modificarTiposChequeos.isEmpty() && modificarTiposChequeos.contains(filtrarTiposChequeos.get(index))) {
                    int modIndex = modificarTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                    modificarTiposChequeos.remove(modIndex);
                    borrarTiposChequeos.add(filtrarTiposChequeos.get(index));
                } else if (!crearTiposChequeos.isEmpty() && crearTiposChequeos.contains(filtrarTiposChequeos.get(index))) {
                    int crearIndex = crearTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                    crearTiposChequeos.remove(crearIndex);
                } else {
                    borrarTiposChequeos.add(filtrarTiposChequeos.get(index));
                }
                int VCIndex = listTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                listTiposChequeos.remove(VCIndex);
                filtrarTiposChequeos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposChequeos");
            infoRegistro = "Cantidad de registros: " + listTiposChequeos.size();
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
        BigInteger contarChequeosMedicosTipoChequeo;
        BigInteger contarTiposExamenesCargosTipoChequeo;

        try {
            System.err.println("Control Secuencia de ControlTiposChequeos ");
            if (tipoLista == 0) {
                contarChequeosMedicosTipoChequeo = administrarTiposChequeos.contarChequeosMedicosTipoChequeo(listTiposChequeos.get(index).getSecuencia());
                contarTiposExamenesCargosTipoChequeo = administrarTiposChequeos.contarTiposExamenesCargosTipoChequeo(listTiposChequeos.get(index).getSecuencia());
            } else {
                contarChequeosMedicosTipoChequeo = administrarTiposChequeos.contarChequeosMedicosTipoChequeo(filtrarTiposChequeos.get(index).getSecuencia());
                contarTiposExamenesCargosTipoChequeo = administrarTiposChequeos.contarTiposExamenesCargosTipoChequeo(filtrarTiposChequeos.get(index).getSecuencia());
            }
            if (contarChequeosMedicosTipoChequeo.equals(new BigInteger("0"))
                    && contarTiposExamenesCargosTipoChequeo.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposChequeos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposChequeos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposChequeos.isEmpty() || !crearTiposChequeos.isEmpty() || !modificarTiposChequeos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposChequeos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposChequeos");
            if (!borrarTiposChequeos.isEmpty()) {
                administrarTiposChequeos.borrarTiposChequeos(borrarTiposChequeos);
                //mostrarBorrados
                registrosBorrados = borrarTiposChequeos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposChequeos.clear();
            }
            if (!modificarTiposChequeos.isEmpty()) {
                administrarTiposChequeos.modificarTiposChequeos(modificarTiposChequeos);
                modificarTiposChequeos.clear();
            }
            if (!crearTiposChequeos.isEmpty()) {
                administrarTiposChequeos.crearTiposChequeos(crearTiposChequeos);
                crearTiposChequeos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposChequeos = null;
            context.update("form:datosTiposChequeos");
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
                editarTiposChequeos = listTiposChequeos.get(index);
            }
            if (tipoLista == 1) {
                editarTiposChequeos = filtrarTiposChequeos.get(index);
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

    public void agregarNuevoTiposChequeos() {
        System.out.println("agregarNuevoTiposChequeos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposChequeos.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposChequeos.getCodigo());

            for (int x = 0; x < listTiposChequeos.size(); x++) {
                if (listTiposChequeos.get(x).getCodigo().equals(nuevoTiposChequeos.getCodigo())) {
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
        if (nuevoTiposChequeos.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoTiposChequeos.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
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
                codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
                bandera = 0;
                filtrarTiposChequeos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposChequeos.setSecuencia(l);

            crearTiposChequeos.add(nuevoTiposChequeos);

            listTiposChequeos.add(nuevoTiposChequeos);
            nuevoTiposChequeos = new TiposChequeos();
            context.update("form:datosTiposChequeos");
            infoRegistro = "Cantidad de registros: " + listTiposChequeos.size();
            context.update("form:informacionRegistro");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposChequeos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposChequeos() {
        System.out.println("limpiarNuevoTiposChequeos");
        nuevoTiposChequeos = new TiposChequeos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposChequeos() {
        System.out.println("duplicandoTiposChequeos");
        if (index >= 0) {
            duplicarTiposChequeos = new TiposChequeos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposChequeos.setSecuencia(l);
                duplicarTiposChequeos.setCodigo(listTiposChequeos.get(index).getCodigo());
                duplicarTiposChequeos.setDescripcion(listTiposChequeos.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposChequeos.setSecuencia(l);
                duplicarTiposChequeos.setCodigo(filtrarTiposChequeos.get(index).getCodigo());
                duplicarTiposChequeos.setDescripcion(filtrarTiposChequeos.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposChequeos.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposChequeos.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposChequeos.getDescripcion());

        if (duplicarTiposChequeos.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposChequeos.size(); x++) {
                if (listTiposChequeos.get(x).getCodigo().equals(duplicarTiposChequeos.getCodigo())) {
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
        if (duplicarTiposChequeos.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTiposChequeos.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposChequeos.getSecuencia() + "  " + duplicarTiposChequeos.getCodigo());
            if (crearTiposChequeos.contains(duplicarTiposChequeos)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposChequeos.add(duplicarTiposChequeos);
            crearTiposChequeos.add(duplicarTiposChequeos);
            context.update("form:datosTiposChequeos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            infoRegistro = "Cantidad de registros: " + listTiposChequeos.size();
            context.update("form:informacionRegistro");

            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTiposChequeos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposChequeos");
                bandera = 0;
                filtrarTiposChequeos = null;
                tipoLista = 0;
            }
            duplicarTiposChequeos = new TiposChequeos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposChequeos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposChequeos() {
        duplicarTiposChequeos = new TiposChequeos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposChequeosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSCHEQUEOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposChequeosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSCHEQUEOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposChequeos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCHEQUEOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCHEQUEOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposChequeos> getListTiposChequeos() {
        if (listTiposChequeos == null) {
            System.out.println("ControlTiposChequeos getListTiposChequeos");
            listTiposChequeos = administrarTiposChequeos.consultarTiposChequeos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTiposChequeos == null || listTiposChequeos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listTiposChequeos.size();
        }
        return listTiposChequeos;
    }

    public void setListTiposChequeos(List<TiposChequeos> listTiposChequeos) {
        this.listTiposChequeos = listTiposChequeos;
    }

    public List<TiposChequeos> getFiltrarTiposChequeos() {
        return filtrarTiposChequeos;
    }

    public void setFiltrarTiposChequeos(List<TiposChequeos> filtrarTiposChequeos) {
        this.filtrarTiposChequeos = filtrarTiposChequeos;
    }

    public TiposChequeos getNuevoTiposChequeos() {
        return nuevoTiposChequeos;
    }

    public void setNuevoTiposChequeos(TiposChequeos nuevoTiposChequeos) {
        this.nuevoTiposChequeos = nuevoTiposChequeos;
    }

    public TiposChequeos getDuplicarTiposChequeos() {
        return duplicarTiposChequeos;
    }

    public void setDuplicarTiposChequeos(TiposChequeos duplicarTiposChequeos) {
        this.duplicarTiposChequeos = duplicarTiposChequeos;
    }

    public TiposChequeos getEditarTiposChequeos() {
        return editarTiposChequeos;
    }

    public void setEditarTiposChequeos(TiposChequeos editarTiposChequeos) {
        this.editarTiposChequeos = editarTiposChequeos;
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

    public TiposChequeos getTiposChequeosSeleccionado() {
        return tiposChequeosSeleccionado;
    }

    public void setTiposChequeosSeleccionado(TiposChequeos clasesPensionesSeleccionado) {
        this.tiposChequeosSeleccionado = clasesPensionesSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

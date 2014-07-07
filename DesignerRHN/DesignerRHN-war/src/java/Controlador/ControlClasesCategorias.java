/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ClasesCategorias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarClasesCategoriasInterface;
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
public class ControlClasesCategorias implements Serializable {

    @EJB
    AdministrarClasesCategoriasInterface administrarClasesCategorias;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<ClasesCategorias> listClasesCategorias;
    private List<ClasesCategorias> filtrarClasesCategorias;
    private List<ClasesCategorias> crearClasesCategorias;
    private List<ClasesCategorias> modificarClasesCategorias;
    private List<ClasesCategorias> borrarClasesCategorias;
    private ClasesCategorias nuevoClasesCategorias;
    private ClasesCategorias duplicarClasesCategorias;
    private ClasesCategorias editarClasesCategorias;
    private ClasesCategorias claseCategoriaSeleccionado;
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

    public ControlClasesCategorias() {
        listClasesCategorias = null;
        crearClasesCategorias = new ArrayList<ClasesCategorias>();
        modificarClasesCategorias = new ArrayList<ClasesCategorias>();
        borrarClasesCategorias = new ArrayList<ClasesCategorias>();
        permitirIndex = true;
        editarClasesCategorias = new ClasesCategorias();
        nuevoClasesCategorias = new ClasesCategorias();
        duplicarClasesCategorias = new ClasesCategorias();
        guardado = true;
        tamano = 270;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarClasesCategorias.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    private String paginaAnterior;

    public void recibirAtras(String atras) {
        paginaAnterior = atras;
        System.out.println("ControlClasesCategorias pagina anterior : " + paginaAnterior);
    }

    public String redireccionarAtras() {
        return paginaAnterior;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlClasesCategorias.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarClasesCategorias.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlClasesCategorias eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    private String backDescripcion;
    private Integer backCodigo;

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listClasesCategorias.get(index).getSecuencia();
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    backCodigo = listClasesCategorias.get(index).getCodigo();
                } else {
                    backCodigo = filtrarClasesCategorias.get(index).getCodigo();
                }
            }
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    backDescripcion = listClasesCategorias.get(index).getDescripcion();
                } else {
                    backDescripcion = filtrarClasesCategorias.get(index).getDescripcion();
                }
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlClasesCategorias.asignarIndex \n");
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
            System.out.println("ERROR ControlClasesCategorias.asignarIndex ERROR======" + e.getMessage());
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
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
            bandera = 0;
            filtrarClasesCategorias = null;
            tipoLista = 0;
        }

        borrarClasesCategorias.clear();
        crearClasesCategorias.clear();
        modificarClasesCategorias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listClasesCategorias = null;
        guardado = true;
        permitirIndex = true;
        getListClasesCategorias();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listClasesCategorias == null || listClasesCategorias.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listClasesCategorias.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosClasesCategorias");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
            bandera = 0;
            filtrarClasesCategorias = null;
            tipoLista = 0;
        }

        borrarClasesCategorias.clear();
        crearClasesCategorias.clear();
        modificarClasesCategorias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listClasesCategorias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosClasesCategorias");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
            bandera = 0;
            filtrarClasesCategorias = null;
            tipoLista = 0;
        }
    }

    public void modificarClasesCategorias(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearClasesCategorias.contains(listClasesCategorias.get(indice))) {
                    if (listClasesCategorias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setCodigo(backCodigo);
                    } else {
                        for (int j = 0; j < listClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (listClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listClasesCategorias.get(indice).setCodigo(backCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listClasesCategorias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }
                    if (listClasesCategorias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarClasesCategorias.isEmpty()) {
                            modificarClasesCategorias.add(listClasesCategorias.get(indice));
                        } else if (!modificarClasesCategorias.contains(listClasesCategorias.get(indice))) {
                            modificarClasesCategorias.add(listClasesCategorias.get(indice));
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
                    if (listClasesCategorias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setCodigo(backCodigo);
                    } else {
                        for (int j = 0; j < listClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (listClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            listClasesCategorias.get(indice).setCodigo(backCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listClasesCategorias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }
                    if (listClasesCategorias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listClasesCategorias.get(indice).setDescripcion(backDescripcion);
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

                if (!crearClasesCategorias.contains(filtrarClasesCategorias.get(indice))) {
                    if (filtrarClasesCategorias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setCodigo(backCodigo);
                    } else {
                        for (int j = 0; j < filtrarClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarClasesCategorias.get(indice).setCodigo(backCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarClasesCategorias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }
                    if (filtrarClasesCategorias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }

                    if (banderita == true) {
                        if (modificarClasesCategorias.isEmpty()) {
                            modificarClasesCategorias.add(filtrarClasesCategorias.get(indice));
                        } else if (!modificarClasesCategorias.contains(filtrarClasesCategorias.get(indice))) {
                            modificarClasesCategorias.add(filtrarClasesCategorias.get(indice));
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
                    if (filtrarClasesCategorias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setCodigo(backCodigo);
                    } else {
                        for (int j = 0; j < filtrarClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listClasesCategorias.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesCategorias.get(indice).getCodigo() == listClasesCategorias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarClasesCategorias.get(indice).setCodigo(backCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarClasesCategorias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setDescripcion(backDescripcion);
                    }
                    if (filtrarClasesCategorias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarClasesCategorias.get(indice).setDescripcion(backDescripcion);
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
            context.update("form:datosClasesCategorias");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoClasesCategorias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoClasesCategorias");
                if (!modificarClasesCategorias.isEmpty() && modificarClasesCategorias.contains(listClasesCategorias.get(index))) {
                    int modIndex = modificarClasesCategorias.indexOf(listClasesCategorias.get(index));
                    modificarClasesCategorias.remove(modIndex);
                    borrarClasesCategorias.add(listClasesCategorias.get(index));
                } else if (!crearClasesCategorias.isEmpty() && crearClasesCategorias.contains(listClasesCategorias.get(index))) {
                    int crearIndex = crearClasesCategorias.indexOf(listClasesCategorias.get(index));
                    crearClasesCategorias.remove(crearIndex);
                } else {
                    borrarClasesCategorias.add(listClasesCategorias.get(index));
                }
                listClasesCategorias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoClasesCategorias ");
                if (!modificarClasesCategorias.isEmpty() && modificarClasesCategorias.contains(filtrarClasesCategorias.get(index))) {
                    int modIndex = modificarClasesCategorias.indexOf(filtrarClasesCategorias.get(index));
                    modificarClasesCategorias.remove(modIndex);
                    borrarClasesCategorias.add(filtrarClasesCategorias.get(index));
                } else if (!crearClasesCategorias.isEmpty() && crearClasesCategorias.contains(filtrarClasesCategorias.get(index))) {
                    int crearIndex = crearClasesCategorias.indexOf(filtrarClasesCategorias.get(index));
                    crearClasesCategorias.remove(crearIndex);
                } else {
                    borrarClasesCategorias.add(filtrarClasesCategorias.get(index));
                }
                int VCIndex = listClasesCategorias.indexOf(filtrarClasesCategorias.get(index));
                listClasesCategorias.remove(VCIndex);
                filtrarClasesCategorias.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosClasesCategorias");
            infoRegistro = "Cantidad de registros: " + listClasesCategorias.size();
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
        BigInteger contarCategoriaClaseCategoria;

        try {
            System.err.println("Control Secuencia de ControlClasesCategorias ");
            if (tipoLista == 0) {
                contarCategoriaClaseCategoria = administrarClasesCategorias.contarCategoriaClaseCategoria(listClasesCategorias.get(index).getSecuencia());
            } else {
                contarCategoriaClaseCategoria = administrarClasesCategorias.contarCategoriaClaseCategoria(filtrarClasesCategorias.get(index).getSecuencia());
            }
            if (contarCategoriaClaseCategoria.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoClasesCategorias();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarCategoriaClaseCategoria = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlClasesCategorias verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarClasesCategorias.isEmpty() || !crearClasesCategorias.isEmpty() || !modificarClasesCategorias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarClasesCategorias() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarClasesCategorias");
            if (!borrarClasesCategorias.isEmpty()) {
                administrarClasesCategorias.borrarClasesCategorias(borrarClasesCategorias);
                //mostrarBorrados
                registrosBorrados = borrarClasesCategorias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarClasesCategorias.clear();
            }
            if (!modificarClasesCategorias.isEmpty()) {
                administrarClasesCategorias.modificarClasesCategorias(modificarClasesCategorias);
                modificarClasesCategorias.clear();
            }
            if (!crearClasesCategorias.isEmpty()) {
                administrarClasesCategorias.crearClasesCategorias(crearClasesCategorias);
                crearClasesCategorias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listClasesCategorias = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosClasesCategorias");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarClasesCategorias = listClasesCategorias.get(index);
            }
            if (tipoLista == 1) {
                editarClasesCategorias = filtrarClasesCategorias.get(index);
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

    public void agregarNuevoClasesCategorias() {
        System.out.println("agregarNuevoClasesCategorias");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoClasesCategorias.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoClasesCategorias.getCodigo());

            for (int x = 0; x < listClasesCategorias.size(); x++) {
                if (listClasesCategorias.get(x).getCodigo() == nuevoClasesCategorias.getCodigo()) {
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
        if (nuevoClasesCategorias.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoClasesCategorias.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
                bandera = 0;
                filtrarClasesCategorias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoClasesCategorias.setSecuencia(l);

            crearClasesCategorias.add(nuevoClasesCategorias);

            listClasesCategorias.add(nuevoClasesCategorias);
            nuevoClasesCategorias = new ClasesCategorias();
            context.update("form:datosClasesCategorias");
            infoRegistro = "Cantidad de registros: " + listClasesCategorias.size();
            context.update("form:informacionRegistro");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroClasesCategorias.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoClasesCategorias() {
        System.out.println("limpiarNuevoClasesCategorias");
        nuevoClasesCategorias = new ClasesCategorias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoClasesCategorias() {
        System.out.println("duplicandoClasesCategorias");
        if (index >= 0) {
            duplicarClasesCategorias = new ClasesCategorias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarClasesCategorias.setSecuencia(l);
                duplicarClasesCategorias.setCodigo(listClasesCategorias.get(index).getCodigo());
                duplicarClasesCategorias.setDescripcion(listClasesCategorias.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarClasesCategorias.setSecuencia(l);
                duplicarClasesCategorias.setCodigo(filtrarClasesCategorias.get(index).getCodigo());
                duplicarClasesCategorias.setDescripcion(filtrarClasesCategorias.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroClasesCategorias.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarClasesCategorias.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarClasesCategorias.getDescripcion());

        if (duplicarClasesCategorias.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listClasesCategorias.size(); x++) {
                if (listClasesCategorias.get(x).getCodigo() == duplicarClasesCategorias.getCodigo()) {
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
        if (duplicarClasesCategorias.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarClasesCategorias.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarClasesCategorias.getSecuencia() + "  " + duplicarClasesCategorias.getCodigo());
            if (crearClasesCategorias.contains(duplicarClasesCategorias)) {
                System.out.println("Ya lo contengo.");
            }
            listClasesCategorias.add(duplicarClasesCategorias);
            crearClasesCategorias.add(duplicarClasesCategorias);
            context.update("form:datosClasesCategorias");
            infoRegistro = "Cantidad de registros: " + listClasesCategorias.size();
            context.update("form:informacionRegistro");

            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosClasesCategorias:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosClasesCategorias");
                bandera = 0;
                filtrarClasesCategorias = null;
                tipoLista = 0;
            }
            duplicarClasesCategorias = new ClasesCategorias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroClasesCategorias.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarClasesCategorias() {
        duplicarClasesCategorias = new ClasesCategorias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesCategoriasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CLASESCATEGORIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesCategoriasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CLASESCATEGORIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listClasesCategorias.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CLASESCATEGORIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("CLASESCATEGORIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ClasesCategorias> getListClasesCategorias() {
        if (listClasesCategorias == null) {
            listClasesCategorias = administrarClasesCategorias.consultarClasesCategorias();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listClasesCategorias == null || listClasesCategorias.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listClasesCategorias.size();
        }
        context.update("form:informacionRegistro");
        return listClasesCategorias;
    }

    public void setListClasesCategorias(List<ClasesCategorias> listClasesCategorias) {
        this.listClasesCategorias = listClasesCategorias;
    }

    public List<ClasesCategorias> getFiltrarClasesCategorias() {
        return filtrarClasesCategorias;
    }

    public void setFiltrarClasesCategorias(List<ClasesCategorias> filtrarClasesCategorias) {
        this.filtrarClasesCategorias = filtrarClasesCategorias;
    }

    public ClasesCategorias getNuevoClasesCategorias() {
        return nuevoClasesCategorias;
    }

    public void setNuevoClasesCategorias(ClasesCategorias nuevoClasesCategorias) {
        this.nuevoClasesCategorias = nuevoClasesCategorias;
    }

    public ClasesCategorias getDuplicarClasesCategorias() {
        return duplicarClasesCategorias;
    }

    public void setDuplicarClasesCategorias(ClasesCategorias duplicarClasesCategorias) {
        this.duplicarClasesCategorias = duplicarClasesCategorias;
    }

    public ClasesCategorias getEditarClasesCategorias() {
        return editarClasesCategorias;
    }

    public void setEditarClasesCategorias(ClasesCategorias editarClasesCategorias) {
        this.editarClasesCategorias = editarClasesCategorias;
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

    public ClasesCategorias getClaseCategoriaSeleccionado() {
        return claseCategoriaSeleccionado;
    }

    public void setClaseCategoriaSeleccionado(ClasesCategorias claseCategoriaSeleccionado) {
        this.claseCategoriaSeleccionado = claseCategoriaSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

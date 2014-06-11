/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Idiomas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarIdiomasInterface;
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
public class ControlIdiomas implements Serializable {

    @EJB
    AdministrarIdiomasInterface administrarIdiomas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Idiomas> listIdiomas;
    private List<Idiomas> filtrarIdiomas;
    private List<Idiomas> crearIdiomas;
    private List<Idiomas> modificarIdiomas;
    private List<Idiomas> borrarIdiomas;
    private Idiomas nuevoIdiomas;
    private Idiomas duplicarIdiomas;
    private Idiomas editarIdiomas;
    private Idiomas idiomaSeleccionado;
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
    private String infoRegistro;

    public ControlIdiomas() {
        listIdiomas = null;
        crearIdiomas = new ArrayList<Idiomas>();
        modificarIdiomas = new ArrayList<Idiomas>();
        borrarIdiomas = new ArrayList<Idiomas>();
        permitirIndex = true;
        editarIdiomas = new Idiomas();
        nuevoIdiomas = new Idiomas();
        duplicarIdiomas = new Idiomas();
        guardado = true;
        tamano = 270;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarIdiomas.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlIdiomas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlIdiomas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);
        System.err.println("permitirIndex  " + permitirIndex);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listIdiomas.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listIdiomas.get(index).getCodigo();
                backupDescripcion = listIdiomas.get(index).getNombre();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarIdiomas.get(index).getCodigo();
                backupDescripcion = filtrarIdiomas.get(index).getNombre();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlIdiomas.asignarIndex \n");
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
            System.out.println("ERROR ControlIdiomas.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) c.getViewRoot().findComponent("form:datosIdiomas:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 0;
            filtrarIdiomas = null;
            tipoLista = 0;
        }

        borrarIdiomas.clear();
        crearIdiomas.clear();
        modificarIdiomas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listIdiomas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        getListIdiomas();
        if (listIdiomas == null || listIdiomas.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listIdiomas.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosIdiomas");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosIdiomas:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosIdiomas:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIdiomas");
            bandera = 0;
            filtrarIdiomas = null;
            tipoLista = 0;
        }
    }

    public void modificarIdiomas(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearIdiomas.contains(listIdiomas.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listIdiomas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listIdiomas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listIdiomas.size(); j++) {
                            if (j != indice) {
                                if (listIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listIdiomas.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listIdiomas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listIdiomas.get(indice).setNombre(backupDescripcion);
                    } else if (listIdiomas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listIdiomas.get(indice).setNombre(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarIdiomas.isEmpty()) {
                            modificarIdiomas.add(listIdiomas.get(indice));
                        } else if (!modificarIdiomas.contains(listIdiomas.get(indice))) {
                            modificarIdiomas.add(listIdiomas.get(indice));
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
                    context.update("form:datosIdiomas");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listIdiomas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listIdiomas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listIdiomas.size(); j++) {
                            if (j != indice) {
                                if (listIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listIdiomas.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listIdiomas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listIdiomas.get(indice).setNombre(backupDescripcion);
                    } else if (listIdiomas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listIdiomas.get(indice).setNombre(backupDescripcion);

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
                    context.update("form:datosIdiomas");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearIdiomas.contains(filtrarIdiomas.get(indice))) {
                    if (filtrarIdiomas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarIdiomas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarIdiomas.size(); j++) {
                            if (j != indice) {
                                if (filtrarIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listIdiomas.size(); j++) {
                            if (j != indice) {
                                if (filtrarIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarIdiomas.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarIdiomas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarIdiomas.get(indice).setNombre(backupDescripcion);
                    }
                    if (filtrarIdiomas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarIdiomas.get(indice).setNombre(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarIdiomas.isEmpty()) {
                            modificarIdiomas.add(filtrarIdiomas.get(indice));
                        } else if (!modificarIdiomas.contains(filtrarIdiomas.get(indice))) {
                            modificarIdiomas.add(filtrarIdiomas.get(indice));
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
                    if (filtrarIdiomas.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarIdiomas.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarIdiomas.size(); j++) {
                            if (j != indice) {
                                if (filtrarIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listIdiomas.size(); j++) {
                            if (j != indice) {
                                if (filtrarIdiomas.get(indice).getCodigo() == listIdiomas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarIdiomas.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarIdiomas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarIdiomas.get(indice).setNombre(backupDescripcion);
                    }
                    if (filtrarIdiomas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarIdiomas.get(indice).setNombre(backupDescripcion);
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
            context.update("form:datosIdiomas");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoIdiomas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoIdiomas");
                if (!modificarIdiomas.isEmpty() && modificarIdiomas.contains(listIdiomas.get(index))) {
                    int modIndex = modificarIdiomas.indexOf(listIdiomas.get(index));
                    modificarIdiomas.remove(modIndex);
                    borrarIdiomas.add(listIdiomas.get(index));
                } else if (!crearIdiomas.isEmpty() && crearIdiomas.contains(listIdiomas.get(index))) {
                    int crearIndex = crearIdiomas.indexOf(listIdiomas.get(index));
                    crearIdiomas.remove(crearIndex);
                } else {
                    borrarIdiomas.add(listIdiomas.get(index));
                }
                listIdiomas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoIdiomas ");
                if (!modificarIdiomas.isEmpty() && modificarIdiomas.contains(filtrarIdiomas.get(index))) {
                    int modIndex = modificarIdiomas.indexOf(filtrarIdiomas.get(index));
                    modificarIdiomas.remove(modIndex);
                    borrarIdiomas.add(filtrarIdiomas.get(index));
                } else if (!crearIdiomas.isEmpty() && crearIdiomas.contains(filtrarIdiomas.get(index))) {
                    int crearIndex = crearIdiomas.indexOf(filtrarIdiomas.get(index));
                    crearIdiomas.remove(crearIndex);
                } else {
                    borrarIdiomas.add(filtrarIdiomas.get(index));
                }
                int VCIndex = listIdiomas.indexOf(filtrarIdiomas.get(index));
                listIdiomas.remove(VCIndex);
                filtrarIdiomas.remove(index);

            }
            infoRegistro = "Cantidad de registros: " + listIdiomas.size();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");
            context.update("form:datosIdiomas");
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
        BigInteger verificarBorradoIdiomasPersonas;

        try {
            System.err.println("Control Secuencia de ControlIdiomas ");
            if (tipoLista == 0) {
                verificarBorradoIdiomasPersonas = administrarIdiomas.verificarBorradoIdiomasPersonas(listIdiomas.get(index).getSecuencia());
            } else {
                verificarBorradoIdiomasPersonas = administrarIdiomas.verificarBorradoIdiomasPersonas(filtrarIdiomas.get(index).getSecuencia());
            }
            if (verificarBorradoIdiomasPersonas.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoIdiomas();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarBorradoIdiomasPersonas = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlIdiomas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarIdiomas.isEmpty() || !crearIdiomas.isEmpty() || !modificarIdiomas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarIdiomas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarIdiomas");
            if (!borrarIdiomas.isEmpty()) {
                administrarIdiomas.borrarIdiomas(borrarIdiomas);
                //mostrarBorrados
                registrosBorrados = borrarIdiomas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarIdiomas.clear();
            }
            if (!modificarIdiomas.isEmpty()) {
                administrarIdiomas.modificarIdiomas(modificarIdiomas);
                modificarIdiomas.clear();
            }
            if (!crearIdiomas.isEmpty()) {
                administrarIdiomas.crearIdiomas(crearIdiomas);
                crearIdiomas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listIdiomas = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosIdiomas");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarIdiomas = listIdiomas.get(index);
            }
            if (tipoLista == 1) {
                editarIdiomas = filtrarIdiomas.get(index);
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

    public void agregarNuevoIdiomas() {
        System.out.println("agregarNuevoIdiomas");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoIdiomas.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoIdiomas.getCodigo());

            for (int x = 0; x < listIdiomas.size(); x++) {
                if (listIdiomas.get(x).getCodigo() == nuevoIdiomas.getCodigo()) {
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
        if (nuevoIdiomas.getNombre() == null || nuevoIdiomas.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Idioma \n";
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
                codigo = (Column) c.getViewRoot().findComponent("form:datosIdiomas:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosIdiomas");
                bandera = 0;
                filtrarIdiomas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoIdiomas.setSecuencia(l);

            crearIdiomas.add(nuevoIdiomas);

            listIdiomas.add(nuevoIdiomas);
            infoRegistro = "Cantidad de registros: " + listIdiomas.size();
            context.update("form:informacionRegistro");
            nuevoIdiomas = new Idiomas();
            context.update("form:datosIdiomas");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroIdiomas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoIdiomas() {
        System.out.println("limpiarNuevoIdiomas");
        nuevoIdiomas = new Idiomas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoIdiomas() {
        System.out.println("duplicandoIdiomas");
        if (index >= 0) {
            duplicarIdiomas = new Idiomas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarIdiomas.setSecuencia(l);
                duplicarIdiomas.setCodigo(listIdiomas.get(index).getCodigo());
                duplicarIdiomas.setNombre(listIdiomas.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarIdiomas.setSecuencia(l);
                duplicarIdiomas.setCodigo(filtrarIdiomas.get(index).getCodigo());
                duplicarIdiomas.setNombre(filtrarIdiomas.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroIdiomas.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarIdiomas.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarIdiomas.getNombre());

        if (duplicarIdiomas.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listIdiomas.size(); x++) {
                if (listIdiomas.get(x).getCodigo() == duplicarIdiomas.getCodigo()) {
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
        if (duplicarIdiomas.getNombre() == null || duplicarIdiomas.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   *Idioma \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarIdiomas.getSecuencia() + "  " + duplicarIdiomas.getCodigo());
            if (crearIdiomas.contains(duplicarIdiomas)) {
                System.out.println("Ya lo contengo.");
            }
            listIdiomas.add(duplicarIdiomas);
            crearIdiomas.add(duplicarIdiomas);
            context.update("form:datosIdiomas");
            index = -1;
            infoRegistro = "Cantidad de registros: " + listIdiomas.size();
            context.update("form:informacionRegistro");
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosIdiomas:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosIdiomas:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosIdiomas");
                bandera = 0;
                filtrarIdiomas = null;
                tipoLista = 0;
            }
            duplicarIdiomas = new Idiomas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroIdiomas.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarIdiomas() {
        duplicarIdiomas = new Idiomas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIdiomasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "IDIOMAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIdiomasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "IDIOMAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listIdiomas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "IDIOMAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("IDIOMAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Idiomas> getListIdiomas() {
        if (listIdiomas == null) {
            listIdiomas = administrarIdiomas.mostrarIdiomas();
        }
        RequestContext context = RequestContext.getCurrentInstance();

        if (listIdiomas == null || listIdiomas.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listIdiomas.size();
        }
        context.update("form:informacionRegistro");
        return listIdiomas;
    }

    public void setListIdiomas(List<Idiomas> listIdiomas) {
        this.listIdiomas = listIdiomas;
    }

    public List<Idiomas> getFiltrarIdiomas() {
        return filtrarIdiomas;
    }

    public void setFiltrarIdiomas(List<Idiomas> filtrarIdiomas) {
        this.filtrarIdiomas = filtrarIdiomas;
    }

    public Idiomas getNuevoIdiomas() {
        return nuevoIdiomas;
    }

    public void setNuevoIdiomas(Idiomas nuevoIdiomas) {
        this.nuevoIdiomas = nuevoIdiomas;
    }

    public Idiomas getDuplicarIdiomas() {
        return duplicarIdiomas;
    }

    public void setDuplicarIdiomas(Idiomas duplicarIdiomas) {
        this.duplicarIdiomas = duplicarIdiomas;
    }

    public Idiomas getEditarIdiomas() {
        return editarIdiomas;
    }

    public void setEditarIdiomas(Idiomas editarIdiomas) {
        this.editarIdiomas = editarIdiomas;
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

    public Idiomas getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(Idiomas idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.PryPlataformas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPryPlataformasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ControlPryPlataformas implements Serializable {

    @EJB
    AdministrarPryPlataformasInterface administrarPryPlataformas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<PryPlataformas> listPryPlataformas;
    private List<PryPlataformas> filtrarPryPlataformas;
    private List<PryPlataformas> crearPryPlataformas;
    private List<PryPlataformas> modificarPryPlataformas;
    private List<PryPlataformas> borrarPryPlataformas;
    private PryPlataformas nuevoPryPlataforma;
    private PryPlataformas duplicarPryPlataforma;
    private PryPlataformas editarPryPlataforma;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, observacion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

    public ControlPryPlataformas() {
        listPryPlataformas = null;
        crearPryPlataformas = new ArrayList<PryPlataformas>();
        modificarPryPlataformas = new ArrayList<PryPlataformas>();
        borrarPryPlataformas = new ArrayList<PryPlataformas>();
        permitirIndex = true;
        editarPryPlataforma = new PryPlataformas();
        nuevoPryPlataforma = new PryPlataformas();
        duplicarPryPlataforma = new PryPlataformas();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarPryPlataformas.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlPryPlataformas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlPryPlataformas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listPryPlataformas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlPryPlataformas.asignarIndex \n");
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
            System.out.println("ERROR ControlPryPlataformas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            observacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:observacion");
            observacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPrtPlataforma");
            bandera = 0;
            filtrarPryPlataformas = null;
            tipoLista = 0;
        }

        borrarPryPlataformas.clear();
        crearPryPlataformas.clear();
        modificarPryPlataformas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listPryPlataformas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosPrtPlataforma");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:codigo");
            codigo.setFilterStyle("width: 200px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:descripcion");
            descripcion.setFilterStyle("width: 270px");
            observacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:observacion");
            observacion.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosPrtPlataforma");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            observacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:observacion");
            observacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPrtPlataforma");
            bandera = 0;
            filtrarPryPlataformas = null;
            tipoLista = 0;
        }
    }

    public void modificarEvalPlataforma(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR PRY PLATAFORMAS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR PRYPLATAFORMAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearPryPlataformas.contains(listPryPlataformas.get(indice))) {
                    if (listPryPlataformas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPryPlataformas.size(); j++) {
                            if (j != indice) {
                                if (listPryPlataformas.get(indice).getCodigo() == listPryPlataformas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listPryPlataformas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listPryPlataformas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarPryPlataformas.isEmpty()) {
                            modificarPryPlataformas.add(listPryPlataformas.get(indice));
                        } else if (!modificarPryPlataformas.contains(listPryPlataformas.get(indice))) {
                            modificarPryPlataformas.add(listPryPlataformas.get(indice));
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

                if (!crearPryPlataformas.contains(filtrarPryPlataformas.get(indice))) {
                    if (filtrarPryPlataformas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPryPlataformas.size(); j++) {
                            if (j != indice) {
                                if (listPryPlataformas.get(indice).getCodigo() == listPryPlataformas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarPryPlataformas.size(); j++) {
                            if (j != indice) {
                                if (filtrarPryPlataformas.get(indice).getCodigo() == filtrarPryPlataformas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarPryPlataformas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarPryPlataformas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarPryPlataformas.isEmpty()) {
                            modificarPryPlataformas.add(filtrarPryPlataformas.get(indice));
                        } else if (!modificarPryPlataformas.contains(filtrarPryPlataformas.get(indice))) {
                            modificarPryPlataformas.add(filtrarPryPlataformas.get(indice));
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
            context.update("form:datosPrtPlataforma");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoPryPlataformas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoPryPlataformas");
                if (!modificarPryPlataformas.isEmpty() && modificarPryPlataformas.contains(listPryPlataformas.get(index))) {
                    int modIndex = modificarPryPlataformas.indexOf(listPryPlataformas.get(index));
                    modificarPryPlataformas.remove(modIndex);
                    borrarPryPlataformas.add(listPryPlataformas.get(index));
                } else if (!crearPryPlataformas.isEmpty() && crearPryPlataformas.contains(listPryPlataformas.get(index))) {
                    int crearIndex = crearPryPlataformas.indexOf(listPryPlataformas.get(index));
                    crearPryPlataformas.remove(crearIndex);
                } else {
                    borrarPryPlataformas.add(listPryPlataformas.get(index));
                }
                listPryPlataformas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoPryPlataformas");
                if (!modificarPryPlataformas.isEmpty() && modificarPryPlataformas.contains(filtrarPryPlataformas.get(index))) {
                    int modIndex = modificarPryPlataformas.indexOf(filtrarPryPlataformas.get(index));
                    modificarPryPlataformas.remove(modIndex);
                    borrarPryPlataformas.add(filtrarPryPlataformas.get(index));
                } else if (!crearPryPlataformas.isEmpty() && crearPryPlataformas.contains(filtrarPryPlataformas.get(index))) {
                    int crearIndex = crearPryPlataformas.indexOf(filtrarPryPlataformas.get(index));
                    crearPryPlataformas.remove(crearIndex);
                } else {
                    borrarPryPlataformas.add(filtrarPryPlataformas.get(index));
                }
                int VCIndex = listPryPlataformas.indexOf(filtrarPryPlataformas.get(index));
                listPryPlataformas.remove(VCIndex);
                filtrarPryPlataformas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosPrtPlataforma");
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

        BigInteger proyectos;
        try {
            System.err.println("Control Secuencia de ControlEvalCompetencias ");
            if (tipoLista == 0) {
                proyectos = administrarPryPlataformas.contarProyectosPryPlataformas(listPryPlataformas.get(index).getSecuencia());
            } else {
                proyectos = administrarPryPlataformas.contarProyectosPryPlataformas(filtrarPryPlataformas.get(index).getSecuencia());
            }
            if (proyectos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoPryPlataformas();
            } else {
                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                proyectos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlPryPlataformas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarPryPlataformas.isEmpty() || !crearPryPlataformas.isEmpty() || !modificarPryPlataformas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarPryPlataformas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarPryPlataformas");
            if (!borrarPryPlataformas.isEmpty()) {
              administrarPryPlataformas.borrarPryPlataformas(borrarPryPlataformas);
                
                //mostrarBorrados
                registrosBorrados = borrarPryPlataformas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarPryPlataformas.clear();
            }
            if (!crearPryPlataformas.isEmpty()) {
                administrarPryPlataformas.crearPryPlataformas(crearPryPlataformas);              
                crearPryPlataformas.clear();
            }
            if (!modificarPryPlataformas.isEmpty()) {
                administrarPryPlataformas.modificarPryPlataformas(modificarPryPlataformas);
                modificarPryPlataformas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listPryPlataformas = null;
            context.update("form:datosPrtPlataforma");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarPryPlataforma = listPryPlataformas.get(index);
            }
            if (tipoLista == 1) {
                editarPryPlataforma = filtrarPryPlataformas.get(index);
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

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editObservacion");
                context.execute("editObservacion.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoPryPlataformas() {
        System.out.println("agregarNuevoPryPlataformas");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoPryPlataforma.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoPryPlataforma.getCodigo());

            for (int x = 0; x < listPryPlataformas.size(); x++) {
                if (listPryPlataformas.get(x).getCodigo() == nuevoPryPlataforma.getCodigo()) {
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
        if (nuevoPryPlataforma.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripción \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                observacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:observacion");
                observacion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPrtPlataforma");
                bandera = 0;
                filtrarPryPlataformas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoPryPlataforma.setSecuencia(l);

            crearPryPlataformas.add(nuevoPryPlataforma);

            listPryPlataformas.add(nuevoPryPlataforma);
            nuevoPryPlataforma = new PryPlataformas();
            context.update("form:datosPrtPlataforma");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroPryPlataforma.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoPryPlataformas() {
        System.out.println("limpiarNuevoPryPlataformas");
        nuevoPryPlataforma = new PryPlataformas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoPryPlataformas() {
        System.out.println("duplicandoPryPlataformas");
        if (index >= 0) {
            duplicarPryPlataforma = new PryPlataformas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarPryPlataforma.setSecuencia(l);
                duplicarPryPlataforma.setCodigo(listPryPlataformas.get(index).getCodigo());
                duplicarPryPlataforma.setDescripcion(listPryPlataformas.get(index).getDescripcion());
                duplicarPryPlataforma.setObservacion(listPryPlataformas.get(index).getObservacion());
            }
            if (tipoLista == 1) {
                duplicarPryPlataforma.setSecuencia(l);
                duplicarPryPlataforma.setCodigo(filtrarPryPlataformas.get(index).getCodigo());
                duplicarPryPlataforma.setDescripcion(filtrarPryPlataformas.get(index).getDescripcion());
                duplicarPryPlataforma.setObservacion(filtrarPryPlataformas.get(index).getObservacion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarPPL");
            context.execute("duplicarRegistroPryPlataforma.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR PRYPLATAFORMAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarPryPlataforma.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarPryPlataforma.getDescripcion());

        if (duplicarPryPlataforma.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listPryPlataformas.size(); x++) {
                if (listPryPlataformas.get(x).getCodigo() == duplicarPryPlataforma.getCodigo()) {
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
        if (duplicarPryPlataforma.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarPryPlataforma.getSecuencia() + "  " + duplicarPryPlataforma.getCodigo());
            if (crearPryPlataformas.contains(duplicarPryPlataforma)) {
                System.out.println("Ya lo contengo.");
            }
            listPryPlataformas.add(duplicarPryPlataforma);
            crearPryPlataformas.add(duplicarPryPlataforma);
            context.update("form:datosPrtPlataforma");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                observacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPrtPlataforma:observacion");
                observacion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPrtPlataforma");
                bandera = 0;
                filtrarPryPlataformas = null;
                tipoLista = 0;
            }
            duplicarPryPlataforma = new PryPlataformas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroPryPlataforma.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarPryPlataformas() {
        duplicarPryPlataforma = new PryPlataformas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPrtPlataformaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PRY_PLATAFORMAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPrtPlataformaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PRY_PLATAFORMAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listPryPlataformas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PRY_PLATAFORMAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("PRY_PLATAFORMAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<PryPlataformas> getListPryPlataformas() {
        if (listPryPlataformas == null) {
            listPryPlataformas = administrarPryPlataformas.mostrarPryPlataformas();
        }
        return listPryPlataformas;
    }

    public void setListPryPlataformas(List<PryPlataformas> listPryPlataformas) {
        this.listPryPlataformas = listPryPlataformas;
    }

    public List<PryPlataformas> getFiltrarPryPlataformas() {
        return filtrarPryPlataformas;
    }

    public void setFiltrarPryPlataformas(List<PryPlataformas> filtrarPryPlataformas) {
        this.filtrarPryPlataformas = filtrarPryPlataformas;
    }

    public PryPlataformas getNuevoPryPlataforma() {
        return nuevoPryPlataforma;
    }

    public void setNuevoPryPlataforma(PryPlataformas nuevoPryPlataforma) {
        this.nuevoPryPlataforma = nuevoPryPlataforma;
    }

    public PryPlataformas getDuplicarPryPlataforma() {
        return duplicarPryPlataforma;
    }

    public void setDuplicarPryPlataforma(PryPlataformas duplicarPryPlataforma) {
        this.duplicarPryPlataforma = duplicarPryPlataforma;
    }

    public PryPlataformas getEditarPryPlataforma() {
        return editarPryPlataforma;
    }

    public void setEditarPryPlataforma(PryPlataformas editarPryPlataforma) {
        this.editarPryPlataforma = editarPryPlataforma;
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

}

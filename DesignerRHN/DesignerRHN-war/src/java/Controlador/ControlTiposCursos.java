/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposCursos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposCursosInterface;
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
public class ControlTiposCursos implements Serializable {

    @EJB
    AdministrarTiposCursosInterface administrarTiposCursos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposCursos> listTiposCursos;
    private List<TiposCursos> filtrarTiposCursos;
    private List<TiposCursos> crearTiposCursos;
    private List<TiposCursos> modificarTiposCursos;
    private List<TiposCursos> borrarTiposCursos;
    private TiposCursos nuevoTiposCursos;
    private TiposCursos duplicarTiposCursos;
    private TiposCursos editarTiposCursos;
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

    public ControlTiposCursos() {
        listTiposCursos = null;
        crearTiposCursos = new ArrayList<TiposCursos>();
        modificarTiposCursos = new ArrayList<TiposCursos>();
        borrarTiposCursos = new ArrayList<TiposCursos>();
        permitirIndex = true;
        editarTiposCursos = new TiposCursos();
        nuevoTiposCursos = new TiposCursos();
        duplicarTiposCursos = new TiposCursos();
        guardado = true;
        tamano = 302;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposCursos.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposCursos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposCursos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposCursos.get(index).getSecuencia();
            if (tipoLista == 0) {
                backupCodigo = listTiposCursos.get(index).getCodigo();
                backupDescripcion = listTiposCursos.get(index).getDescripcion();
            } else if (tipoLista == 1) {
                backupCodigo = filtrarTiposCursos.get(index).getCodigo();
                backupDescripcion = filtrarTiposCursos.get(index).getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposCursos.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposCursos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposCursos");
            bandera = 0;
            filtrarTiposCursos = null;
            tipoLista = 0;
        }

        borrarTiposCursos.clear();
        crearTiposCursos.clear();
        modificarTiposCursos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposCursos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposCursos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposCursos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 302;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposCursos");
            bandera = 0;
            filtrarTiposCursos = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposCursos(int indice, String confirmarCambio, String valorConfirmar) {
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
                if (!crearTiposCursos.contains(listTiposCursos.get(indice))) {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposCursos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposCursos.get(indice).setCodigo(backupCodigo);

                    } else {
                        for (int j = 0; j < listTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (listTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposCursos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposCursos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposCursos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposCursos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposCursos.get(indice).setDescripcion(backupDescripcion);

                    } else {
                        banderita1 = true;
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposCursos.isEmpty()) {
                            modificarTiposCursos.add(listTiposCursos.get(indice));
                        } else if (!modificarTiposCursos.contains(listTiposCursos.get(indice))) {
                            modificarTiposCursos.add(listTiposCursos.get(indice));
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
                    context.update("form:datosTiposCursos");
                    context.update("form:ACEPTAR");
                } else {

                    System.out.println("backupCodigo : " + backupCodigo);
                    System.out.println("backupDescripcion : " + backupDescripcion);

                    if (listTiposCursos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listTiposCursos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < listTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (listTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            listTiposCursos.get(indice).setCodigo(backupCodigo);
                        } else {
                            banderita = true;
                        }

                    }
                    if (listTiposCursos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposCursos.get(indice).setDescripcion(backupDescripcion);
                    } else if (listTiposCursos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        listTiposCursos.get(indice).setDescripcion(backupDescripcion);

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
                    context.update("form:datosTiposCursos");
                    context.update("form:ACEPTAR");

                }
            } else {

                if (!crearTiposCursos.contains(filtrarTiposCursos.get(indice))) {
                    if (filtrarTiposCursos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposCursos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposCursos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposCursos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposCursos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposCursos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposCursos.get(indice).setDescripcion(backupDescripcion);
                    }

                    if (banderita == true && banderita1 == true) {
                        if (modificarTiposCursos.isEmpty()) {
                            modificarTiposCursos.add(filtrarTiposCursos.get(indice));
                        } else if (!modificarTiposCursos.contains(filtrarTiposCursos.get(indice))) {
                            modificarTiposCursos.add(filtrarTiposCursos.get(indice));
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
                    if (filtrarTiposCursos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarTiposCursos.get(indice).setCodigo(backupCodigo);
                    } else {
                        for (int j = 0; j < filtrarTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposCursos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCursos.get(indice).getCodigo() == listTiposCursos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                            filtrarTiposCursos.get(indice).setCodigo(backupCodigo);

                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarTiposCursos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposCursos.get(indice).setDescripcion(backupDescripcion);
                    }
                    if (filtrarTiposCursos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita1 = false;
                        filtrarTiposCursos.get(indice).setDescripcion(backupDescripcion);
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
            context.update("form:datosTiposCursos");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposCursos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposCursos");
                if (!modificarTiposCursos.isEmpty() && modificarTiposCursos.contains(listTiposCursos.get(index))) {
                    int modIndex = modificarTiposCursos.indexOf(listTiposCursos.get(index));
                    modificarTiposCursos.remove(modIndex);
                    borrarTiposCursos.add(listTiposCursos.get(index));
                } else if (!crearTiposCursos.isEmpty() && crearTiposCursos.contains(listTiposCursos.get(index))) {
                    int crearIndex = crearTiposCursos.indexOf(listTiposCursos.get(index));
                    crearTiposCursos.remove(crearIndex);
                } else {
                    borrarTiposCursos.add(listTiposCursos.get(index));
                }
                listTiposCursos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposCursos ");
                if (!modificarTiposCursos.isEmpty() && modificarTiposCursos.contains(filtrarTiposCursos.get(index))) {
                    int modIndex = modificarTiposCursos.indexOf(filtrarTiposCursos.get(index));
                    modificarTiposCursos.remove(modIndex);
                    borrarTiposCursos.add(filtrarTiposCursos.get(index));
                } else if (!crearTiposCursos.isEmpty() && crearTiposCursos.contains(filtrarTiposCursos.get(index))) {
                    int crearIndex = crearTiposCursos.indexOf(filtrarTiposCursos.get(index));
                    crearTiposCursos.remove(crearIndex);
                } else {
                    borrarTiposCursos.add(filtrarTiposCursos.get(index));
                }
                int VCIndex = listTiposCursos.indexOf(filtrarTiposCursos.get(index));
                listTiposCursos.remove(VCIndex);
                filtrarTiposCursos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposCursos");
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
            System.err.println("Control Secuencia de ControlTiposCursos ");
            if (tipoLista == 0) {
                contarIndicesTipoIndice = administrarTiposCursos.contarCursosTipoCurso(listTiposCursos.get(index).getSecuencia());
            } else {
                contarIndicesTipoIndice = administrarTiposCursos.contarCursosTipoCurso(filtrarTiposCursos.get(index).getSecuencia());
            }
            if (contarIndicesTipoIndice.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposCursos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarIndicesTipoIndice = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCursos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposCursos.isEmpty() || !crearTiposCursos.isEmpty() || !modificarTiposCursos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposCursos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposCursos");
            if (!borrarTiposCursos.isEmpty()) {
                administrarTiposCursos.borrarTiposCursos(borrarTiposCursos);
                //mostrarBorrados
                registrosBorrados = borrarTiposCursos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposCursos.clear();
            }
            if (!modificarTiposCursos.isEmpty()) {
                administrarTiposCursos.modificarTiposCursos(modificarTiposCursos);
                modificarTiposCursos.clear();
            }
            if (!crearTiposCursos.isEmpty()) {
                administrarTiposCursos.crearTiposCursos(crearTiposCursos);
                crearTiposCursos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposCursos = null;
            context.execute("mostrarGuardar.show()");
            context.update("form:datosTiposCursos");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposCursos = listTiposCursos.get(index);
            }
            if (tipoLista == 1) {
                editarTiposCursos = filtrarTiposCursos.get(index);
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

    public void agregarNuevoTiposCursos() {
        System.out.println("agregarNuevoTiposCursos");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposCursos.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTiposCursos.getCodigo());

            for (int x = 0; x < listTiposCursos.size(); x++) {
                if (listTiposCursos.get(x).getCodigo() == nuevoTiposCursos.getCodigo()) {
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
        if (nuevoTiposCursos.getDescripcion().equals(" ")) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposCursos");
                bandera = 0;
                filtrarTiposCursos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposCursos.setSecuencia(l);

            crearTiposCursos.add(nuevoTiposCursos);

            listTiposCursos.add(nuevoTiposCursos);
            nuevoTiposCursos = new TiposCursos();
            context.update("form:datosTiposCursos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposCursos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposCursos() {
        System.out.println("limpiarNuevoTiposCursos");
        nuevoTiposCursos = new TiposCursos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposCursos() {
        System.out.println("duplicandoTiposCursos");
        if (index >= 0) {
            duplicarTiposCursos = new TiposCursos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposCursos.setSecuencia(l);
                duplicarTiposCursos.setCodigo(listTiposCursos.get(index).getCodigo());
                duplicarTiposCursos.setDescripcion(listTiposCursos.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposCursos.setSecuencia(l);
                duplicarTiposCursos.setCodigo(filtrarTiposCursos.get(index).getCodigo());
                duplicarTiposCursos.setDescripcion(filtrarTiposCursos.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposCursos.show()");
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
        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposCursos.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposCursos.getDescripcion());

        if (duplicarTiposCursos.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposCursos.size(); x++) {
                if (listTiposCursos.get(x).getCodigo() == duplicarTiposCursos.getCodigo()) {
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
        if (duplicarTiposCursos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTiposCursos.getSecuencia() + "  " + duplicarTiposCursos.getCodigo());
            if (crearTiposCursos.contains(duplicarTiposCursos)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposCursos.add(duplicarTiposCursos);
            crearTiposCursos.add(duplicarTiposCursos);
            context.update("form:datosTiposCursos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposCursos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposCursos");
                bandera = 0;
                filtrarTiposCursos = null;
                tipoLista = 0;
            }
            duplicarTiposCursos = new TiposCursos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposCursos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposCursos() {
        duplicarTiposCursos = new TiposCursos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposCursosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSCURSOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposCursosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSCURSOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposCursos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCURSOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCURSOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposCursos> getListTiposCursos() {
        if (listTiposCursos == null) {
            listTiposCursos = administrarTiposCursos.consultarTiposCursos();
        }
        return listTiposCursos;
    }

    public void setListTiposCursos(List<TiposCursos> listTiposCursos) {
        this.listTiposCursos = listTiposCursos;
    }

    public List<TiposCursos> getFiltrarTiposCursos() {
        return filtrarTiposCursos;
    }

    public void setFiltrarTiposCursos(List<TiposCursos> filtrarTiposCursos) {
        this.filtrarTiposCursos = filtrarTiposCursos;
    }

    public TiposCursos getNuevoTiposCursos() {
        return nuevoTiposCursos;
    }

    public void setNuevoTiposCursos(TiposCursos nuevoTiposCursos) {
        this.nuevoTiposCursos = nuevoTiposCursos;
    }

    public TiposCursos getDuplicarTiposCursos() {
        return duplicarTiposCursos;
    }

    public void setDuplicarTiposCursos(TiposCursos duplicarTiposCursos) {
        this.duplicarTiposCursos = duplicarTiposCursos;
    }

    public TiposCursos getEditarTiposCursos() {
        return editarTiposCursos;
    }

    public void setEditarTiposCursos(TiposCursos editarTiposCursos) {
        this.editarTiposCursos = editarTiposCursos;
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

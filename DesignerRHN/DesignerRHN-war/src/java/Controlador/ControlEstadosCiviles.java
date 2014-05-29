/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.EstadosCiviles;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEstadosCivilesInterface;
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
public class ControlEstadosCiviles implements Serializable {

    @EJB
    AdministrarEstadosCivilesInterface administrarEstadosCiviles;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<EstadosCiviles> listEstadosCiviles;
    private List<EstadosCiviles> filtrarEstadosCiviles;
    private List<EstadosCiviles> crearEstadosCiviles;
    private List<EstadosCiviles> modificarEstadosCiviles;
    private List<EstadosCiviles> borrarEstadosCiviles;
    private EstadosCiviles nuevoEstadoCivil;
    private EstadosCiviles duplicarEstadoCivil;
    private EstadosCiviles editarEstadoCivil;
    private EstadosCiviles estadoCivilSeleccionado;
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
    private BigInteger vigenciasEstadosAficilaciones;
    private Integer a;
    private int tamano;
    private Integer backUpCodigo;
    private String backUpDescripcion;

    public ControlEstadosCiviles() {
        listEstadosCiviles = null;
        crearEstadosCiviles = new ArrayList<EstadosCiviles>();
        modificarEstadosCiviles = new ArrayList<EstadosCiviles>();
        borrarEstadosCiviles = new ArrayList<EstadosCiviles>();
        permitirIndex = true;
        editarEstadoCivil = new EstadosCiviles();
        nuevoEstadoCivil = new EstadosCiviles();
        duplicarEstadoCivil = new EstadosCiviles();
        a = null;
        guardado = true;
        tamano =270;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEstadosCiviles.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlEstadosCiviles.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlEstadosCiviles eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    backUpCodigo = listEstadosCiviles.get(indice).getCodigo();
                } else {
                    backUpCodigo = filtrarEstadosCiviles.get(indice).getCodigo();
                }
            }
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    backUpDescripcion = listEstadosCiviles.get(indice).getDescripcion();
                } else {
                    backUpDescripcion = filtrarEstadosCiviles.get(indice).getDescripcion();
                }
            }
            secRegistro = listEstadosCiviles.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlEstadosCiviles.asignarIndex \n");
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
            System.out.println("ERROR ControlEstadosCiviles.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEstadosCiviles");
            bandera = 0;
            filtrarEstadosCiviles = null;
            tipoLista = 0;
        }

        borrarEstadosCiviles.clear();
        crearEstadosCiviles.clear();
        modificarEstadosCiviles.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEstadosCiviles = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEstadosCiviles");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosEstadosCiviles");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            tamano = 270;
            System.out.println("Desactivar");
            codigo = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEstadosCiviles");
            bandera = 0;
            filtrarEstadosCiviles = null;
            tipoLista = 0;
        }
    }

    public void modificarEstadoCivil(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EstadosCiviles");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR Enfermedades, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearEstadosCiviles.contains(listEstadosCiviles.get(indice))) {
                    if (listEstadosCiviles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listEstadosCiviles.size(); j++) {
                            if (j != indice) {
                                if (listEstadosCiviles.get(indice).getCodigo() == listEstadosCiviles.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            banderita = false;
                            listEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listEstadosCiviles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (listEstadosCiviles.get(indice).getDescripcion().equals(" ")) {
                        banderita = false;
                        listEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEstadosCiviles.isEmpty()) {
                            modificarEstadosCiviles.add(listEstadosCiviles.get(indice));
                        } else if (!modificarEstadosCiviles.contains(listEstadosCiviles.get(indice))) {
                            modificarEstadosCiviles.add(listEstadosCiviles.get(indice));
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
                    if (listEstadosCiviles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listEstadosCiviles.size(); j++) {
                            if (j != indice) {
                                if (listEstadosCiviles.get(indice).getCodigo() == listEstadosCiviles.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            banderita = false;
                            listEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listEstadosCiviles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        listEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (listEstadosCiviles.get(indice).getDescripcion().equals(" ")) {
                        banderita = false;
                        listEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
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

                if (!crearEstadosCiviles.contains(filtrarEstadosCiviles.get(indice))) {
                    if (filtrarEstadosCiviles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listEstadosCiviles.size(); j++) {
                            if (j != indice) {
                                if (listEstadosCiviles.get(indice).getCodigo() == listEstadosCiviles.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarEstadosCiviles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (filtrarEstadosCiviles.get(indice).getDescripcion().equals(" ")) {
                        filtrarEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEstadosCiviles.isEmpty()) {
                            modificarEstadosCiviles.add(filtrarEstadosCiviles.get(indice));
                        } else if (!modificarEstadosCiviles.contains(filtrarEstadosCiviles.get(indice))) {
                            modificarEstadosCiviles.add(filtrarEstadosCiviles.get(indice));
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
                    if (filtrarEstadosCiviles.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                    } else {
                        for (int j = 0; j < listEstadosCiviles.size(); j++) {
                            if (j != indice) {
                                if (listEstadosCiviles.get(indice).getCodigo() == listEstadosCiviles.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            filtrarEstadosCiviles.get(indice).setCodigo(backUpCodigo);
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarEstadosCiviles.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        filtrarEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                    }
                    if (filtrarEstadosCiviles.get(indice).getDescripcion().equals(" ")) {
                        filtrarEstadosCiviles.get(indice).setDescripcion(backUpDescripcion);
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
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
            context.update("form:datosEstadosCiviles");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoEstadoCivil() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarEstadosCiviles");
                if (!modificarEstadosCiviles.isEmpty() && modificarEstadosCiviles.contains(listEstadosCiviles.get(index))) {
                    int modIndex = modificarEstadosCiviles.indexOf(listEstadosCiviles.get(index));
                    modificarEstadosCiviles.remove(modIndex);
                    borrarEstadosCiviles.add(listEstadosCiviles.get(index));
                } else if (!crearEstadosCiviles.isEmpty() && crearEstadosCiviles.contains(listEstadosCiviles.get(index))) {
                    int crearIndex = crearEstadosCiviles.indexOf(listEstadosCiviles.get(index));
                    crearEstadosCiviles.remove(crearIndex);
                } else {
                    borrarEstadosCiviles.add(listEstadosCiviles.get(index));
                }
                listEstadosCiviles.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarEstadosCiviles ");
                if (!modificarEstadosCiviles.isEmpty() && modificarEstadosCiviles.contains(filtrarEstadosCiviles.get(index))) {
                    int modIndex = modificarEstadosCiviles.indexOf(filtrarEstadosCiviles.get(index));
                    modificarEstadosCiviles.remove(modIndex);
                    borrarEstadosCiviles.add(filtrarEstadosCiviles.get(index));
                } else if (!crearEstadosCiviles.isEmpty() && crearEstadosCiviles.contains(filtrarEstadosCiviles.get(index))) {
                    int crearIndex = crearEstadosCiviles.indexOf(filtrarEstadosCiviles.get(index));
                    crearEstadosCiviles.remove(crearIndex);
                } else {
                    borrarEstadosCiviles.add(filtrarEstadosCiviles.get(index));
                }
                int VCIndex = listEstadosCiviles.indexOf(filtrarEstadosCiviles.get(index));
                listEstadosCiviles.remove(VCIndex);
                filtrarEstadosCiviles.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEstadosCiviles");
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
        try {
            System.err.println("Control Secuencia de EstadosCiviles a borrar");
            vigenciasEstadosAficilaciones = administrarEstadosCiviles.verificarVigenciasEstadosCiviles(listEstadosCiviles.get(index).getSecuencia());

            if (!vigenciasEstadosAficilaciones.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                vigenciasEstadosAficilaciones = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrandoEstadoCivil();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEstadosCiviles verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarEstadosCiviles.isEmpty() || !crearEstadosCiviles.isEmpty() || !modificarEstadosCiviles.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEstadoCivil() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando EstadosCiviles");
            if (!borrarEstadosCiviles.isEmpty()) {
                administrarEstadosCiviles.borrarEstadosCiviles(borrarEstadosCiviles);
                //mostrarBorrados
                registrosBorrados = borrarEstadosCiviles.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEstadosCiviles.clear();
            }
            if (!crearEstadosCiviles.isEmpty()) {
                administrarEstadosCiviles.crearEstadosCiviles(crearEstadosCiviles);
                crearEstadosCiviles.clear();
            }
            if (!modificarEstadosCiviles.isEmpty()) {
                administrarEstadosCiviles.modificarEstadosCiviles(modificarEstadosCiviles);
                modificarEstadosCiviles.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEstadosCiviles = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosEstadosCiviles");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEstadoCivil = listEstadosCiviles.get(index);
            }
            if (tipoLista == 1) {
                editarEstadoCivil = filtrarEstadosCiviles.get(index);
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

    public void agregarNuevoEstadoCivil() {
        System.out.println("Agregar EstadosCiviles");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEstadoCivil.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoEstadoCivil.getCodigo());

            for (int x = 0; x < listEstadosCiviles.size(); x++) {
                if (listEstadosCiviles.get(x).getCodigo().equals(nuevoEstadoCivil.getCodigo())) {
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
        if (nuevoEstadoCivil.getDescripcion() == (null) || nuevoEstadoCivil.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Descripcion \n";
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
                codigo = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEstadosCiviles");
                bandera = 0;
                filtrarEstadosCiviles = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoEstadoCivil.setSecuencia(l);

            crearEstadosCiviles.add(nuevoEstadoCivil);

            listEstadosCiviles.add(nuevoEstadoCivil);
            nuevoEstadoCivil = new EstadosCiviles();

            context.update("form:datosEstadosCiviles");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEstadoCivil.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEstadoCivil() {
        System.out.println("limpiarNuevoEstadosCiviles");
        nuevoEstadoCivil = new EstadosCiviles();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarEstadosCiviles() {
        System.out.println("duplicarEstadosCiviles");
        if (index >= 0) {
            duplicarEstadoCivil = new EstadosCiviles();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEstadoCivil.setSecuencia(l);
                duplicarEstadoCivil.setCodigo(listEstadosCiviles.get(index).getCodigo());
                duplicarEstadoCivil.setDescripcion(listEstadosCiviles.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarEstadoCivil.setSecuencia(l);
                duplicarEstadoCivil.setCodigo(filtrarEstadosCiviles.get(index).getCodigo());
                duplicarEstadoCivil.setDescripcion(filtrarEstadosCiviles.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEC");
            context.execute("duplicarRegistroEstadoCivil.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR EstadosCiviles");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        System.err.println("ConfirmarDuplicar codigo " + duplicarEstadoCivil.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarEstadoCivil.getDescripcion());

        if (duplicarEstadoCivil.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listEstadosCiviles.size(); x++) {
                if (listEstadosCiviles.get(x).getCodigo().equals(duplicarEstadoCivil.getCodigo())) {
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
        if (duplicarEstadoCivil.getDescripcion() == null || duplicarEstadoCivil.getDescripcion().equals(" ") || duplicarEstadoCivil.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarEstadoCivil.getSecuencia() + "  " + duplicarEstadoCivil.getCodigo());
            if (crearEstadosCiviles.contains(duplicarEstadoCivil)) {
                System.out.println("Ya lo contengo.");
            }
            listEstadosCiviles.add(duplicarEstadoCivil);
            crearEstadosCiviles.add(duplicarEstadoCivil);
            context.update("form:datosEstadosCiviles");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                codigo = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosEstadosCiviles:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEstadosCiviles");
                bandera = 0;
                filtrarEstadosCiviles = null;
                tipoLista = 0;
            }
            duplicarEstadoCivil = new EstadosCiviles();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEstadoCivil.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarEstadosCiviles() {
        duplicarEstadoCivil = new EstadosCiviles();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEstadosCivilesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ESTADOSCIVILES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEstadosCivilesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ESTADOSCIVILES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEstadosCiviles.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "ESTADOSCIVILES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("ESTADOSCIVILES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    public List<EstadosCiviles> getListEstadosCiviles() {
        if (listEstadosCiviles == null) {
            listEstadosCiviles = administrarEstadosCiviles.consultarEstadosCiviles();
        }
        return listEstadosCiviles;
    }

    public void setListEstadosCiviles(List<EstadosCiviles> listEstadosCiviles) {
        this.listEstadosCiviles = listEstadosCiviles;
    }

    public List<EstadosCiviles> getFiltrarEstadosCiviles() {
        return filtrarEstadosCiviles;
    }

    public void setFiltrarEstadosCiviles(List<EstadosCiviles> filtrarEstadosCiviles) {
        this.filtrarEstadosCiviles = filtrarEstadosCiviles;
    }

    public List<EstadosCiviles> getModificarEstadosCiviles() {
        return modificarEstadosCiviles;
    }

    public void setModificarEstadosCiviles(List<EstadosCiviles> modificarEstadosCiviles) {
        this.modificarEstadosCiviles = modificarEstadosCiviles;
    }

    public EstadosCiviles getNuevoEstadoCivil() {
        return nuevoEstadoCivil;
    }

    public void setNuevoEstadoCivil(EstadosCiviles nuevoEstadoCivil) {
        this.nuevoEstadoCivil = nuevoEstadoCivil;
    }

    public EstadosCiviles getDuplicarEstadoCivil() {
        return duplicarEstadoCivil;
    }

    public void setDuplicarEstadoCivil(EstadosCiviles duplicarEstadoCivil) {
        this.duplicarEstadoCivil = duplicarEstadoCivil;
    }

    public EstadosCiviles getEditarEstadoCivil() {
        return editarEstadoCivil;
    }

    public void setEditarEstadoCivil(EstadosCiviles editarEstadoCivil) {
        this.editarEstadoCivil = editarEstadoCivil;
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

    public EstadosCiviles getEstadoCivilSeleccionado() {
        return estadoCivilSeleccionado;
    }

    public void setEstadoCivilSeleccionado(EstadosCiviles estadoCivilSeleccionado) {
        this.estadoCivilSeleccionado = estadoCivilSeleccionado;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

}

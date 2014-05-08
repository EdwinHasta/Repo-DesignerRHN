/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.GruposViaticos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarGruposViaticosInterface;
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
public class ControlGruposViaticos implements Serializable {

    @EJB
    AdministrarGruposViaticosInterface administrarGruposViaticos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<GruposViaticos> listGruposViaticos;
    private List<GruposViaticos> filtrarGruposViaticos;
    private List<GruposViaticos> crearGruposViaticos;
    private List<GruposViaticos> modificarGruposViaticos;
    private List<GruposViaticos> borrarGruposViaticos;
    private GruposViaticos nuevoGrupoViatico;
    private GruposViaticos duplicarGrupoViatico;
    private GruposViaticos editarGrupoViatico;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, porcentaje;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private Integer a;
    private BigInteger cargos;
    private BigInteger plantas;
    private BigInteger tablasViaticos;
    private BigInteger ersViaticos;

    public ControlGruposViaticos() {
        listGruposViaticos = null;
        crearGruposViaticos = new ArrayList<GruposViaticos>();
        modificarGruposViaticos = new ArrayList<GruposViaticos>();
        borrarGruposViaticos = new ArrayList<GruposViaticos>();
        permitirIndex = true;
        editarGrupoViatico = new GruposViaticos();
        nuevoGrupoViatico = new GruposViaticos();
        duplicarGrupoViatico = new GruposViaticos();
        a = null;
        guardado = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarGruposViaticos.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlGruposViaticos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlGruposViaticos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listGruposViaticos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlGruposViaticos.asignarIndex \n");
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
            System.out.println("ERROR ControlGruposViaticos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            porcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:porcentaje");
            porcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGrupoViatico");
            bandera = 0;
            filtrarGruposViaticos = null;
            tipoLista = 0;
        }

        borrarGruposViaticos.clear();
        crearGruposViaticos.clear();
        modificarGruposViaticos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listGruposViaticos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosGrupoViatico");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:codigo");
            codigo.setFilterStyle("width: 200px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:descripcion");
            descripcion.setFilterStyle("width: 270px");
            porcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:porcentaje");
            porcentaje.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosGrupoViatico");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            porcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:porcentaje");
            porcentaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGrupoViatico");
            bandera = 0;
            filtrarGruposViaticos = null;
            tipoLista = 0;
        }
    }

    public void modificarGrupoViatico(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR GRUPOS VIATIVOS");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR GRUPOS VIATIVOS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearGruposViaticos.contains(listGruposViaticos.get(indice))) {
                    if (listGruposViaticos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listGruposViaticos.size(); j++) {
                            if (j != indice) {
                                if (listGruposViaticos.get(indice).getCodigo() == listGruposViaticos.get(j).getCodigo()) {
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
                    if (listGruposViaticos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listGruposViaticos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listGruposViaticos.get(indice).getPorcentajelastday() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarGruposViaticos.isEmpty()) {
                            modificarGruposViaticos.add(listGruposViaticos.get(indice));
                        } else if (!modificarGruposViaticos.contains(listGruposViaticos.get(indice))) {
                            modificarGruposViaticos.add(listGruposViaticos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:ACEPTAR");
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearGruposViaticos.contains(filtrarGruposViaticos.get(indice))) {
                    if (filtrarGruposViaticos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listGruposViaticos.size(); j++) {
                            if (j != indice) {
                                if (listGruposViaticos.get(indice).getCodigo() == listGruposViaticos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarGruposViaticos.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposViaticos.get(indice).getCodigo() == filtrarGruposViaticos.get(j).getCodigo()) {
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

                    if (filtrarGruposViaticos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarGruposViaticos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarGruposViaticos.get(indice).getPorcentajelastday() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarGruposViaticos.isEmpty()) {
                            modificarGruposViaticos.add(filtrarGruposViaticos.get(indice));
                        } else if (!modificarGruposViaticos.contains(filtrarGruposViaticos.get(indice))) {
                            modificarGruposViaticos.add(filtrarGruposViaticos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:ACEPTAR");
                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosGrupoViatico");
        }

    }

    public void borrandoGruposViaticos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoGruposViaticos");
                if (!modificarGruposViaticos.isEmpty() && modificarGruposViaticos.contains(listGruposViaticos.get(index))) {
                    int modIndex = modificarGruposViaticos.indexOf(listGruposViaticos.get(index));
                    modificarGruposViaticos.remove(modIndex);
                    borrarGruposViaticos.add(listGruposViaticos.get(index));
                } else if (!crearGruposViaticos.isEmpty() && crearGruposViaticos.contains(listGruposViaticos.get(index))) {
                    int crearIndex = crearGruposViaticos.indexOf(listGruposViaticos.get(index));
                    crearGruposViaticos.remove(crearIndex);
                } else {
                    borrarGruposViaticos.add(listGruposViaticos.get(index));
                }
                listGruposViaticos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoGruposViaticos ");
                if (!modificarGruposViaticos.isEmpty() && modificarGruposViaticos.contains(filtrarGruposViaticos.get(index))) {
                    int modIndex = modificarGruposViaticos.indexOf(filtrarGruposViaticos.get(index));
                    modificarGruposViaticos.remove(modIndex);
                    borrarGruposViaticos.add(filtrarGruposViaticos.get(index));
                } else if (!crearGruposViaticos.isEmpty() && crearGruposViaticos.contains(filtrarGruposViaticos.get(index))) {
                    int crearIndex = crearGruposViaticos.indexOf(filtrarGruposViaticos.get(index));
                    crearGruposViaticos.remove(crearIndex);
                } else {
                    borrarGruposViaticos.add(filtrarGruposViaticos.get(index));
                }
                int VCIndex = listGruposViaticos.indexOf(filtrarGruposViaticos.get(index));
                listGruposViaticos.remove(VCIndex);
                filtrarGruposViaticos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosGrupoViatico");
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
            System.err.println("Control Secuencia de ControlGruposViaticos ");
            if (tipoLista == 0) {
                cargos = administrarGruposViaticos.verificarCargos(listGruposViaticos.get(index).getSecuencia());
                plantas = administrarGruposViaticos.verificarPlantas(listGruposViaticos.get(index).getSecuencia());
                tablasViaticos = administrarGruposViaticos.verificarTablasViaticos(listGruposViaticos.get(index).getSecuencia());
                ersViaticos = administrarGruposViaticos.verificarEersViaticos(listGruposViaticos.get(index).getSecuencia());
            } else {
                cargos = administrarGruposViaticos.verificarCargos(filtrarGruposViaticos.get(index).getSecuencia());
                plantas = administrarGruposViaticos.verificarPlantas(filtrarGruposViaticos.get(index).getSecuencia());
                tablasViaticos = administrarGruposViaticos.verificarTablasViaticos(filtrarGruposViaticos.get(index).getSecuencia());
                ersViaticos = administrarGruposViaticos.verificarEersViaticos(filtrarGruposViaticos.get(index).getSecuencia());
            }
            if (cargos.equals(new BigInteger("0")) || plantas.equals(new BigInteger("0")) || tablasViaticos.equals(new BigInteger("0")) || ersViaticos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoGruposViaticos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                cargos = new BigInteger("-1");
                plantas = new BigInteger("-1");
                tablasViaticos = new BigInteger("-1");
                ersViaticos = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlGruposViaticos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarGruposViaticos.isEmpty() || !crearGruposViaticos.isEmpty() || !modificarGruposViaticos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarGruposViaticos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarGruposViaticos");
            if (!borrarGruposViaticos.isEmpty()) {
                administrarGruposViaticos.borrarGruposViaticos(borrarGruposViaticos);
                //mostrarBorrados
                registrosBorrados = borrarGruposViaticos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarGruposViaticos.clear();
            }
            if (!crearGruposViaticos.isEmpty()) {
                administrarGruposViaticos.crearGruposViaticos(crearGruposViaticos);

                crearGruposViaticos.clear();
            }
            if (!modificarGruposViaticos.isEmpty()) {
                administrarGruposViaticos.modificarGruposViaticos(modificarGruposViaticos);
                modificarGruposViaticos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listGruposViaticos = null;
            context.update("form:datosGrupoViatico");
            k = 0;
        }
        guardado = true;
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarGrupoViatico = listGruposViaticos.get(index);
            }
            if (tipoLista == 1) {
                editarGrupoViatico = filtrarGruposViaticos.get(index);
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
                context.update("formularioDialogos:editporcentaje");
                context.execute("editporcentaje.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoGruposViaticos() {
        System.out.println("agregarNuevoGruposViaticos");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoGrupoViatico.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Grupos Viaticos: " + nuevoGrupoViatico.getCodigo());

            for (int x = 0; x < listGruposViaticos.size(); x++) {
                if (listGruposViaticos.get(x).getCodigo() == nuevoGrupoViatico.getCodigo()) {
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
        if (nuevoGrupoViatico.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoGrupoViatico.getPorcentajelastday() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Un Porcentaje \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                porcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:porcentaje");
                porcentaje.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGrupoViatico");
                bandera = 0;
                filtrarGruposViaticos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoGrupoViatico.setSecuencia(l);

            crearGruposViaticos.add(nuevoGrupoViatico);

            listGruposViaticos.add(nuevoGrupoViatico);
            nuevoGrupoViatico = new GruposViaticos();
            context.update("form:datosGrupoViatico");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroGruposViaticos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoGruposViaticos() {
        System.out.println("limpiarNuevoGruposViaticos");
        nuevoGrupoViatico = new GruposViaticos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoGruposViaticos() {
        System.out.println("duplicandoGruposViaticos");
        if (index >= 0) {
            duplicarGrupoViatico = new GruposViaticos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarGrupoViatico.setSecuencia(l);
                duplicarGrupoViatico.setCodigo(listGruposViaticos.get(index).getCodigo());
                duplicarGrupoViatico.setDescripcion(listGruposViaticos.get(index).getDescripcion());
                duplicarGrupoViatico.setPorcentajelastday(listGruposViaticos.get(index).getPorcentajelastday());
            }
            if (tipoLista == 1) {
                duplicarGrupoViatico.setSecuencia(l);
                duplicarGrupoViatico.setCodigo(filtrarGruposViaticos.get(index).getCodigo());
                duplicarGrupoViatico.setDescripcion(filtrarGruposViaticos.get(index).getDescripcion());
                duplicarGrupoViatico.setPorcentajelastday(filtrarGruposViaticos.get(index).getPorcentajelastday());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarGV");
            context.execute("duplicarRegistroGruposViaticos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR GRUPOSVIATIVOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        System.err.println("ConfirmarDuplicar codigo " + duplicarGrupoViatico.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarGrupoViatico.getDescripcion());

        if (duplicarGrupoViatico.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listGruposViaticos.size(); x++) {
                if (listGruposViaticos.get(x).getCodigo() == duplicarGrupoViatico.getCodigo()) {
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
        if (duplicarGrupoViatico.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarGrupoViatico.getPorcentajelastday() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Porcentaje \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarGrupoViatico.getSecuencia() + "  " + duplicarGrupoViatico.getCodigo());
            if (crearGruposViaticos.contains(duplicarGrupoViatico)) {
                System.out.println("Ya lo contengo.");
            }
            listGruposViaticos.add(duplicarGrupoViatico);
            crearGruposViaticos.add(duplicarGrupoViatico);
            context.update("form:datosGrupoViatico");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                porcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoViatico:porcentaje");
                porcentaje.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGrupoViatico");
                bandera = 0;
                filtrarGruposViaticos = null;
                tipoLista = 0;
            }
            duplicarGrupoViatico = new GruposViaticos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroGruposViaticos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarGruposViaticos() {
        duplicarGrupoViatico = new GruposViaticos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGrupoViaticoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "GRUPOSVIATICOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGrupoViaticoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "GRUPOSVIATICOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listGruposViaticos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "GRUPOSVIATICOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("GRUPOSVIATICOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*
    public List<GruposViaticos> getListGruposViaticos() {
        if (listGruposViaticos == null) {
            listGruposViaticos = administrarGruposViaticos.consultarGruposViaticos();
        }
        return listGruposViaticos;
    }

    public void setListGruposViaticos(List<GruposViaticos> listGruposViaticos) {
        this.listGruposViaticos = listGruposViaticos;
    }

    public List<GruposViaticos> getFiltrarGruposViaticos() {
        return filtrarGruposViaticos;
    }

    public void setFiltrarGruposViaticos(List<GruposViaticos> filtrarGruposViaticos) {
        this.filtrarGruposViaticos = filtrarGruposViaticos;
    }

    public GruposViaticos getNuevoGrupoViatico() {
        return nuevoGrupoViatico;
    }

    public void setNuevoGrupoViatico(GruposViaticos nuevoGrupoViatico) {
        this.nuevoGrupoViatico = nuevoGrupoViatico;
    }

    public GruposViaticos getDuplicarGrupoViatico() {
        return duplicarGrupoViatico;
    }

    public void setDuplicarGrupoViatico(GruposViaticos duplicarGrupoViatico) {
        this.duplicarGrupoViatico = duplicarGrupoViatico;
    }

    public GruposViaticos getEditarGrupoViatico() {
        return editarGrupoViatico;
    }

    public void setEditarGrupoViatico(GruposViaticos editarGrupoViatico) {
        this.editarGrupoViatico = editarGrupoViatico;
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

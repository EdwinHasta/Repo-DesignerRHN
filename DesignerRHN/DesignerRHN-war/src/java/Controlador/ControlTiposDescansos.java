/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposDescansos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposDescansosInterface;
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

@ManagedBean
@SessionScoped
public class ControlTiposDescansos implements Serializable {

    @EJB
    AdministrarTiposDescansosInterface administrarTiposDescansos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposDescansos> listTiposDescansos;
    private List<TiposDescansos> filtrarTiposDescansos;
    private List<TiposDescansos> crearTiposDescansos;
    private List<TiposDescansos> modificarTiposDescansos;
    private List<TiposDescansos> borrarTiposDescansos;
    private TiposDescansos nuevoTiposDescansos;
    private TiposDescansos duplicarTiposDescansos;
    private TiposDescansos editarTiposDescansos;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, diasTrabajados, diasDescansados;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //filtrado table
    private int tamano;

    public ControlTiposDescansos() {
        listTiposDescansos = null;
        crearTiposDescansos = new ArrayList<TiposDescansos>();
        modificarTiposDescansos = new ArrayList<TiposDescansos>();
        borrarTiposDescansos = new ArrayList<TiposDescansos>();
        permitirIndex = true;
        editarTiposDescansos = new TiposDescansos();
        nuevoTiposDescansos = new TiposDescansos();
        duplicarTiposDescansos = new TiposDescansos();
        guardado = true;
        tamano = 307;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposDescansos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposDescansos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposDescansos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposDescansos.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposDescansos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            diasTrabajados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasTrabajados");
            diasTrabajados.setFilterStyle("display: none; visibility: hidden;");
            diasDescansados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasDescansados");
            diasDescansados.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposDescansos");
            bandera = 0;
            filtrarTiposDescansos = null;
            tipoLista = 0;
        }

        borrarTiposDescansos.clear();
        crearTiposDescansos.clear();
        modificarTiposDescansos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposDescansos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTiposDescansos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 285;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:codigo");
            codigo.setFilterStyle("width: 50px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:descripcion");
            descripcion.setFilterStyle("width: 200px");
            diasTrabajados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasTrabajados");
            diasTrabajados.setFilterStyle("width: 200px");
            diasDescansados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasDescansados");
            diasDescansados.setFilterStyle("width: 200px");
            RequestContext.getCurrentInstance().update("form:datosTiposDescansos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 307;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            diasTrabajados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasTrabajados");
            diasTrabajados.setFilterStyle("display: none; visibility: hidden;");
            diasDescansados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasDescansados");
            diasDescansados.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposDescansos");
            bandera = 0;
            filtrarTiposDescansos = null;
            tipoLista = 0;
        }
    }

    public void modificarTiposDescansos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPODESCANSO");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposDescansos.contains(listTiposDescansos.get(indice))) {
                    if (listTiposDescansos.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        if (listTiposDescansos.get(indice).getCodigo().length() > 3) {
                            mensajeValidacion = "EL NOMBRE CORTO MAXIMO DEBE TENER 3 CARACTERES";
                            banderita = false;
                        } else {
                            for (int j = 0; j < listTiposDescansos.size(); j++) {
                                if (j != indice) {
                                    if (listTiposDescansos.get(indice).getCodigo().equals(listTiposDescansos.get(j).getCodigo())) {
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
                    }
                    if (listTiposDescansos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposDescansos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposDescansos.isEmpty()) {
                            modificarTiposDescansos.add(listTiposDescansos.get(indice));
                        } else if (!modificarTiposDescansos.contains(listTiposDescansos.get(indice))) {
                            modificarTiposDescansos.add(listTiposDescansos.get(indice));
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

                if (!crearTiposDescansos.contains(filtrarTiposDescansos.get(indice))) {
                    if (filtrarTiposDescansos.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarTiposDescansos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposDescansos.get(indice).getCodigo().equals(listTiposDescansos.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listTiposDescansos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposDescansos.get(indice).getCodigo().equals(listTiposDescansos.get(j).getCodigo())) {
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

                    if (filtrarTiposDescansos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposDescansos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposDescansos.isEmpty()) {
                            modificarTiposDescansos.add(filtrarTiposDescansos.get(indice));
                        } else if (!modificarTiposDescansos.contains(filtrarTiposDescansos.get(indice))) {
                            modificarTiposDescansos.add(filtrarTiposDescansos.get(indice));
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
            context.update("form:datosTiposDescansos");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposDescansos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposDescansos");
                if (!modificarTiposDescansos.isEmpty() && modificarTiposDescansos.contains(listTiposDescansos.get(index))) {
                    int modIndex = modificarTiposDescansos.indexOf(listTiposDescansos.get(index));
                    modificarTiposDescansos.remove(modIndex);
                    borrarTiposDescansos.add(listTiposDescansos.get(index));
                } else if (!crearTiposDescansos.isEmpty() && crearTiposDescansos.contains(listTiposDescansos.get(index))) {
                    int crearIndex = crearTiposDescansos.indexOf(listTiposDescansos.get(index));
                    crearTiposDescansos.remove(crearIndex);
                } else {
                    borrarTiposDescansos.add(listTiposDescansos.get(index));
                }
                listTiposDescansos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposDescansos ");
                if (!modificarTiposDescansos.isEmpty() && modificarTiposDescansos.contains(filtrarTiposDescansos.get(index))) {
                    int modIndex = modificarTiposDescansos.indexOf(filtrarTiposDescansos.get(index));
                    modificarTiposDescansos.remove(modIndex);
                    borrarTiposDescansos.add(filtrarTiposDescansos.get(index));
                } else if (!crearTiposDescansos.isEmpty() && crearTiposDescansos.contains(filtrarTiposDescansos.get(index))) {
                    int crearIndex = crearTiposDescansos.indexOf(filtrarTiposDescansos.get(index));
                    crearTiposDescansos.remove(crearIndex);
                } else {
                    borrarTiposDescansos.add(filtrarTiposDescansos.get(index));
                }
                int VCIndex = listTiposDescansos.indexOf(filtrarTiposDescansos.get(index));
                listTiposDescansos.remove(VCIndex);
                filtrarTiposDescansos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTiposDescansos");
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
        BigInteger contarCodeudoresTipoDocumento;

        try {
            System.err.println("Control Secuencia de ControlTiposDescansos "+listTiposDescansos.get(index).getSecuencia());
            if (tipoLista == 0) {
                contarCodeudoresTipoDocumento = administrarTiposDescansos.contarVigenciasJornadasTipoDescanso(listTiposDescansos.get(index).getSecuencia());
            } else {
                contarCodeudoresTipoDocumento = administrarTiposDescansos.contarVigenciasJornadasTipoDescanso(filtrarTiposDescansos.get(index).getSecuencia());
            }
            if (contarCodeudoresTipoDocumento.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTiposDescansos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarCodeudoresTipoDocumento = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposDescansos verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposDescansos.isEmpty() || !crearTiposDescansos.isEmpty() || !modificarTiposDescansos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposDescansos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposDescansos");
            if (!borrarTiposDescansos.isEmpty()) {
                administrarTiposDescansos.borrarTiposDescansos(borrarTiposDescansos);
                //mostrarBorrados
                registrosBorrados = borrarTiposDescansos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposDescansos.clear();
            }
            if (!modificarTiposDescansos.isEmpty()) {
                administrarTiposDescansos.modificarTiposDescansos(modificarTiposDescansos);
                modificarTiposDescansos.clear();
            }
            if (!crearTiposDescansos.isEmpty()) {
                administrarTiposDescansos.crearTiposDescansos(crearTiposDescansos);
                crearTiposDescansos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposDescansos = null;
            context.update("form:datosTiposDescansos");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTiposDescansos = listTiposDescansos.get(index);
            }
            if (tipoLista == 1) {
                editarTiposDescansos = filtrarTiposDescansos.get(index);
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

    public void agregarNuevoTiposDescansos() {
        System.out.println("agregarNuevoTiposDescansos");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTiposDescansos.getCodigo() == null) {
            mensajeValidacion = " *debe tener un codigo\n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int i = 0; i < listTiposDescansos.size(); i++) {
                if (nuevoTiposDescansos.getCodigo().equals(listTiposDescansos.get(i).getCodigo())) {
                    duplicados++;
                }
            }
            if (duplicados == 0) {
                contador++;
            } else {
                mensajeValidacion = "codigo repetido";
            }

        }
        if (nuevoTiposDescansos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *debe tener una Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                diasTrabajados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasTrabajados");
                diasTrabajados.setFilterStyle("display: none; visibility: hidden;");
                diasDescansados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasDescansados");
                diasDescansados.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposDescansos");
                bandera = 0;
                filtrarTiposDescansos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTiposDescansos.setSecuencia(l);

            crearTiposDescansos.add(nuevoTiposDescansos);

            listTiposDescansos.add(nuevoTiposDescansos);
            nuevoTiposDescansos = new TiposDescansos();
            context.update("form:datosTiposDescansos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposDescansos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposDescansos() {
        System.out.println("limpiarNuevoTiposDescansos");
        nuevoTiposDescansos = new TiposDescansos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposDescansos() {
        System.out.println("duplicandoTiposDescansos");
        if (index >= 0) {
            duplicarTiposDescansos = new TiposDescansos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTiposDescansos.setSecuencia(l);
                duplicarTiposDescansos.setCodigo(listTiposDescansos.get(index).getCodigo());
                duplicarTiposDescansos.setDescripcion(listTiposDescansos.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTiposDescansos.setSecuencia(l);
                duplicarTiposDescansos.setCodigo(filtrarTiposDescansos.get(index).getCodigo());
                duplicarTiposDescansos.setDescripcion(filtrarTiposDescansos.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroTiposDescansos.show()");
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

        System.err.println("ConfirmarDuplicar codigo " + duplicarTiposDescansos.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTiposDescansos.getDescripcion());

        if (duplicarTiposDescansos.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + "   * codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int i = 0; i < listTiposDescansos.size(); i++) {
                if (duplicarTiposDescansos.getCodigo().equals(listTiposDescansos.get(i).getCodigo())) {
                    duplicados++;
                }
            }
            if (duplicados == 0) {
                contador++;
            } else {
                mensajeValidacion = "codigo repetido";
            }

        }
        if (duplicarTiposDescansos.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion  \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            if (crearTiposDescansos.contains(duplicarTiposDescansos)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposDescansos.add(duplicarTiposDescansos);
            crearTiposDescansos.add(duplicarTiposDescansos);
            context.update("form:datosTiposDescansos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                diasTrabajados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasTrabajados");
                diasTrabajados.setFilterStyle("display: none; visibility: hidden;");
                diasDescansados = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposDescansos:diasDescansados");
                diasDescansados.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposDescansos");
                bandera = 0;
                filtrarTiposDescansos = null;
                tipoLista = 0;
            }
            duplicarTiposDescansos = new TiposDescansos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposDescansos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposDescansos() {
        duplicarTiposDescansos = new TiposDescansos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposDescansosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSDESCANSOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposDescansosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSDESCANSOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposDescansos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSDESCANSOS"); //En ENCARGATURAS lo cambia por el Descripcion de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSDESCANSOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposDescansos> getListTiposDescansos() {
        if (listTiposDescansos == null) {
            listTiposDescansos = administrarTiposDescansos.consultarTiposDescansos();
        }
        return listTiposDescansos;
    }

    public void setListTiposDescansos(List<TiposDescansos> listTiposDescansos) {
        this.listTiposDescansos = listTiposDescansos;
    }

    public List<TiposDescansos> getFiltrarTiposDescansos() {
        return filtrarTiposDescansos;
    }

    public void setFiltrarTiposDescansos(List<TiposDescansos> filtrarTiposDescansos) {
        this.filtrarTiposDescansos = filtrarTiposDescansos;
    }

    public TiposDescansos getNuevoTiposDescansos() {
        return nuevoTiposDescansos;
    }

    public void setNuevoTiposDescansos(TiposDescansos nuevoTiposDescansos) {
        this.nuevoTiposDescansos = nuevoTiposDescansos;
    }

    public TiposDescansos getDuplicarTiposDescansos() {
        return duplicarTiposDescansos;
    }

    public void setDuplicarTiposDescansos(TiposDescansos duplicarTiposDescansos) {
        this.duplicarTiposDescansos = duplicarTiposDescansos;
    }

    public TiposDescansos getEditarTiposDescansos() {
        return editarTiposDescansos;
    }

    public void setEditarTiposDescansos(TiposDescansos editarTiposDescansos) {
        this.editarTiposDescansos = editarTiposDescansos;
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

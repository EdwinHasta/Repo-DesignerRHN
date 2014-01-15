/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.GruposInfAdicionales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarGruposInfAdicionalesInterface;
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

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlGruposInfAdicionales implements Serializable {

    @EJB
    AdministrarGruposInfAdicionalesInterface administrarGruposInfAdicionales;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<GruposInfAdicionales> listGruposInfAdicionales;
    private List<GruposInfAdicionales> filtrarGruposInfAdicionales;
    private List<GruposInfAdicionales> crearGruposInfAdicionales;
    private List<GruposInfAdicionales> modificarGruposInfAdicionales;
    private List<GruposInfAdicionales> borrarGruposInfAdicionales;
    private GruposInfAdicionales nuevoGrupoInfAdicional;
    private GruposInfAdicionales duplicarGrupoInfAdicional;
    private GruposInfAdicionales editarGrupoInfAdicional;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, estado;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger vigenciasEstadosAficilaciones;
    private Integer a;

    public ControlGruposInfAdicionales() {
        listGruposInfAdicionales = null;
        crearGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        modificarGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        borrarGruposInfAdicionales = new ArrayList<GruposInfAdicionales>();
        permitirIndex = true;
        editarGrupoInfAdicional = new GruposInfAdicionales();
        nuevoGrupoInfAdicional = new GruposInfAdicionales();
        duplicarGrupoInfAdicional = new GruposInfAdicionales();
        a = null;
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
            secRegistro = listGruposInfAdicionales.get(index).getSecuencia();

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
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            bandera = 0;
            filtrarGruposInfAdicionales = null;
            tipoLista = 0;
        }

        borrarGruposInfAdicionales.clear();
        crearGruposInfAdicionales.clear();
        modificarGruposInfAdicionales.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listGruposInfAdicionales = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosGruposInfAdicionales");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("width: 200px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("width: 275px");
            estado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("width: 275px");

            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            estado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
            estado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
            bandera = 0;
            filtrarGruposInfAdicionales = null;
            tipoLista = 0;
        }
    }

    public void modificarGrupoInfAdicional(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR GruposInfAdicionales");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR Enfermedades, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearGruposInfAdicionales.contains(listGruposInfAdicionales.get(indice))) {
                    if (listGruposInfAdicionales.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (listGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
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
                    if (listGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (listGruposInfAdicionales.get(indice).getEstado().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarGruposInfAdicionales.isEmpty()) {
                            modificarGruposInfAdicionales.add(listGruposInfAdicionales.get(indice));
                        } else if (!modificarGruposInfAdicionales.contains(listGruposInfAdicionales.get(indice))) {
                            modificarGruposInfAdicionales.add(listGruposInfAdicionales.get(indice));
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

                if (!crearGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(indice))) {
                    if (filtrarGruposInfAdicionales.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (listGruposInfAdicionales.get(indice).getCodigo() == listGruposInfAdicionales.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarGruposInfAdicionales.size(); j++) {
                            if (j != indice) {
                                if (filtrarGruposInfAdicionales.get(indice).getCodigo() == filtrarGruposInfAdicionales.get(j).getCodigo()) {
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

                    if (filtrarGruposInfAdicionales.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarGruposInfAdicionales.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarGruposInfAdicionales.get(indice).getEstado().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarGruposInfAdicionales.get(indice).getEstado().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarGruposInfAdicionales.isEmpty()) {
                            modificarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(indice));
                        } else if (!modificarGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(indice))) {
                            modificarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(indice));
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
            context.update("form:datosGruposInfAdicionales");
        }

    }

    public void borrarGrupoInfAdicional() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarGrupoInfAdicional");
                if (!modificarGruposInfAdicionales.isEmpty() && modificarGruposInfAdicionales.contains(listGruposInfAdicionales.get(index))) {
                    int modIndex = modificarGruposInfAdicionales.indexOf(listGruposInfAdicionales.get(index));
                    modificarGruposInfAdicionales.remove(modIndex);
                    borrarGruposInfAdicionales.add(listGruposInfAdicionales.get(index));
                } else if (!crearGruposInfAdicionales.isEmpty() && crearGruposInfAdicionales.contains(listGruposInfAdicionales.get(index))) {
                    int crearIndex = crearGruposInfAdicionales.indexOf(listGruposInfAdicionales.get(index));
                    crearGruposInfAdicionales.remove(crearIndex);
                } else {
                    borrarGruposInfAdicionales.add(listGruposInfAdicionales.get(index));
                }
                listGruposInfAdicionales.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarGrupoInfAdicional ");
                if (!modificarGruposInfAdicionales.isEmpty() && modificarGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(index))) {
                    int modIndex = modificarGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                    modificarGruposInfAdicionales.remove(modIndex);
                    borrarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(index));
                } else if (!crearGruposInfAdicionales.isEmpty() && crearGruposInfAdicionales.contains(filtrarGruposInfAdicionales.get(index))) {
                    int crearIndex = crearGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                    crearGruposInfAdicionales.remove(crearIndex);
                } else {
                    borrarGruposInfAdicionales.add(filtrarGruposInfAdicionales.get(index));
                }
                int VCIndex = listGruposInfAdicionales.indexOf(filtrarGruposInfAdicionales.get(index));
                listGruposInfAdicionales.remove(VCIndex);
                filtrarGruposInfAdicionales.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosGruposInfAdicionales");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de verificarBorrado  a borrar");
            if (tipoLista == 0) {
                vigenciasEstadosAficilaciones = administrarGruposInfAdicionales.verificarInformacionesAdicionales(listGruposInfAdicionales.get(index).getSecuencia());
            } else {
                vigenciasEstadosAficilaciones = administrarGruposInfAdicionales.verificarInformacionesAdicionales(filtrarGruposInfAdicionales.get(index).getSecuencia());
            }
            if (vigenciasEstadosAficilaciones.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarGrupoInfAdicional();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                vigenciasEstadosAficilaciones = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEstadosCiviles verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarGruposInfAdicionales.isEmpty() || !crearGruposInfAdicionales.isEmpty() || !modificarGruposInfAdicionales.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarGrupoInfAdicional() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando GruposInfAdicionales");
            if (!borrarGruposInfAdicionales.isEmpty()) {
             administrarGruposInfAdicionales.borrarGruposInfAdicionales(borrarGruposInfAdicionales);
                
                //mostrarBorrados
                registrosBorrados = borrarGruposInfAdicionales.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarGruposInfAdicionales.clear();
            }
            if (!crearGruposInfAdicionales.isEmpty()) {
               administrarGruposInfAdicionales.crearGruposInfAdicionales(crearGruposInfAdicionales);
                crearGruposInfAdicionales.clear();
            }
            if (!modificarGruposInfAdicionales.isEmpty()) {
                administrarGruposInfAdicionales.modificarGruposInfAdicionales(modificarGruposInfAdicionales);
                modificarGruposInfAdicionales.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listGruposInfAdicionales = null;
            context.update("form:datosGruposInfAdicionales");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarGrupoInfAdicional = listGruposInfAdicionales.get(index);
            }
            if (tipoLista == 1) {
                editarGrupoInfAdicional = filtrarGruposInfAdicionales.get(index);
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
                context.update("formularioDialogos:editEstado");
                context.execute("editEstado.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoGrupoInfAdicional() {
        System.out.println("Agregar GruposInfAdicionales");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoGrupoInfAdicional.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoGrupoInfAdicional.getCodigo());

            for (int x = 0; x < listGruposInfAdicionales.size(); x++) {
                if (listGruposInfAdicionales.get(x).getCodigo() == nuevoGrupoInfAdicional.getCodigo()) {
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
        if (nuevoGrupoInfAdicional.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoGrupoInfAdicional.getEstado() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Estado \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
                estado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
                bandera = 0;
                filtrarGruposInfAdicionales = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoGrupoInfAdicional.setSecuencia(l);

            crearGruposInfAdicionales.add(nuevoGrupoInfAdicional);

            listGruposInfAdicionales.add(nuevoGrupoInfAdicional);
            nuevoGrupoInfAdicional = new GruposInfAdicionales();

            context.update("form:datosGruposInfAdicionales");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }

            context.execute("nuevoRegistroGrupoInfAdicional.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoGruposInfAdicionales() {
        System.out.println("limpiarNuevoEstadosCiviles");
        nuevoGrupoInfAdicional = new GruposInfAdicionales();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarGruposInfAdicionales() {
        System.out.println("duplicarEstadosCiviles");
        if (index >= 0) {
            duplicarGrupoInfAdicional = new GruposInfAdicionales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarGrupoInfAdicional.setSecuencia(l);
                duplicarGrupoInfAdicional.setCodigo(listGruposInfAdicionales.get(index).getCodigo());
                duplicarGrupoInfAdicional.setDescripcion(listGruposInfAdicionales.get(index).getDescripcion());
                duplicarGrupoInfAdicional.setEstado(listGruposInfAdicionales.get(index).getEstado());
            }
            if (tipoLista == 1) {
                duplicarGrupoInfAdicional.setSecuencia(l);
                duplicarGrupoInfAdicional.setCodigo(filtrarGruposInfAdicionales.get(index).getCodigo());
                duplicarGrupoInfAdicional.setEstado(filtrarGruposInfAdicionales.get(index).getDescripcion());
                duplicarGrupoInfAdicional.setEstado(filtrarGruposInfAdicionales.get(index).getEstado());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarGIA");
            context.execute("duplicarRegistroGrupoInfAdicional.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR GruposInfAdicionales");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        ;
        System.err.println("ConfirmarDuplicar codigo " + duplicarGrupoInfAdicional.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarGrupoInfAdicional.getDescripcion());

        if (duplicarGrupoInfAdicional.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listGruposInfAdicionales.size(); x++) {
                if (listGruposInfAdicionales.get(x).getCodigo() == duplicarGrupoInfAdicional.getCodigo()) {
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
        if (duplicarGrupoInfAdicional.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarGrupoInfAdicional.getEstado() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Estado \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarGrupoInfAdicional.getSecuencia() + "  " + duplicarGrupoInfAdicional.getCodigo());
            if (crearGruposInfAdicionales.contains(duplicarGrupoInfAdicional)) {
                System.out.println("Ya lo contengo.");
            }
            listGruposInfAdicionales.add(duplicarGrupoInfAdicional);
            crearGruposInfAdicionales.add(duplicarGrupoInfAdicional);
            context.update("form:datosGruposInfAdicionales");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                estado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGruposInfAdicionales:estado");
                estado.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGruposInfAdicionales");
                bandera = 0;
                filtrarGruposInfAdicionales = null;
                tipoLista = 0;
            }
            duplicarGrupoInfAdicional = new GruposInfAdicionales();
            RequestContext.getCurrentInstance().execute("duplicarRegistroGrupoInfAdicional.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarGruposInfAdicionales() {
        duplicarGrupoInfAdicional = new GruposInfAdicionales();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGruposInfAdicionalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "GRUPOSINFADICIONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosGruposInfAdicionalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "GRUPOSINFADICIONALES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listGruposInfAdicionales.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "GRUPOSINFADICIONALES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("GRUPOSINFADICIONALES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    public List<GruposInfAdicionales> getListGruposInfAdicionales() {
        if (listGruposInfAdicionales == null) {
            listGruposInfAdicionales = administrarGruposInfAdicionales.consultarGruposInfAdicionales();
        }
        return listGruposInfAdicionales;
    }

    public void setListGruposInfAdicionales(List<GruposInfAdicionales> listGruposInfAdicionales) {
        this.listGruposInfAdicionales = listGruposInfAdicionales;
    }

    public List<GruposInfAdicionales> getFiltrarGruposInfAdicionales() {
        return filtrarGruposInfAdicionales;
    }

    public void setFiltrarGruposInfAdicionales(List<GruposInfAdicionales> filtrarGruposInfAdicionales) {
        this.filtrarGruposInfAdicionales = filtrarGruposInfAdicionales;
    }

    public List<GruposInfAdicionales> getModificarGruposInfAdicionales() {
        return modificarGruposInfAdicionales;
    }

    public void setModificarGruposInfAdicionales(List<GruposInfAdicionales> modificarGruposInfAdicionales) {
        this.modificarGruposInfAdicionales = modificarGruposInfAdicionales;
    }

    public GruposInfAdicionales getNuevoGrupoInfAdicional() {
        return nuevoGrupoInfAdicional;
    }

    public void setNuevoGrupoInfAdicional(GruposInfAdicionales nuevoGrupoInfAdicional) {
        this.nuevoGrupoInfAdicional = nuevoGrupoInfAdicional;
    }

    public GruposInfAdicionales getDuplicarGrupoInfAdicional() {
        return duplicarGrupoInfAdicional;
    }

    public void setDuplicarGrupoInfAdicional(GruposInfAdicionales duplicarGrupoInfAdicional) {
        this.duplicarGrupoInfAdicional = duplicarGrupoInfAdicional;
    }

    public GruposInfAdicionales getEditarGrupoInfAdicional() {
        return editarGrupoInfAdicional;
    }

    public void setEditarGrupoInfAdicional(GruposInfAdicionales editarGrupoInfAdicional) {
        this.editarGrupoInfAdicional = editarGrupoInfAdicional;
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposAuxilios;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposAuxiliosInterface;
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
public class ControlTiposAuxilios implements Serializable {

    @EJB
    AdministrarTiposAuxiliosInterface administrarTiposAuxilios;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposAuxilios> listTiposAuxilios;
    private List<TiposAuxilios> filtrarTiposAuxilios;
    private List<TiposAuxilios> crearTiposAuxilios;
    private List<TiposAuxilios> modificarTiposAuxilios;
    private List<TiposAuxilios> borrarTiposAuxilios;
    private TiposAuxilios nuevoTipoAuxilios;
    private TiposAuxilios duplicarTipoAuxilio;
    private TiposAuxilios editarTipoAuxilio;
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

    public ControlTiposAuxilios() {
        listTiposAuxilios = null;
        crearTiposAuxilios = new ArrayList<TiposAuxilios>();
        modificarTiposAuxilios = new ArrayList<TiposAuxilios>();
        borrarTiposAuxilios = new ArrayList<TiposAuxilios>();
        permitirIndex = true;
        editarTipoAuxilio = new TiposAuxilios();
        nuevoTipoAuxilios = new TiposAuxilios();
        duplicarTipoAuxilio = new TiposAuxilios();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSAUXILIOS EVENTOFILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROLTIPOSAUXILIOS EVENTOFILTRAR  ERROR =" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposAuxilios.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSAUXILIOS ASIGNAR INDEX \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("TIPO ACTUALIZACION : " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLTIPOSAUXILIOS ASIGNAR INDEX ERROR = " + e);
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposAuxilios = null;
            tipoLista = 0;
        }

        borrarTiposAuxilios.clear();
        crearTiposAuxilios.clear();
        modificarTiposAuxilios.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposAuxilios = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("width: 205px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("width: 550px");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposAuxilios = null;
            tipoLista = 0;
        }
    }

    public void modificandoTipoAuxilio(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPOAUXILIO");
        index = indice;

        int contador = 0;
        int contadorGuardar = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR TIPOAUXILIO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposAuxilios.contains(listTiposAuxilios.get(indice))) {
                    if (listTiposAuxilios.get(indice).getCodigo() == a || listTiposAuxilios.get(indice).getCodigo().equals(null)) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposAuxilios.size(); j++) {
                            if (j != indice) {
                                if (listTiposAuxilios.get(indice).getCodigo().equals(listTiposAuxilios.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }
                    if (listTiposAuxilios.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposAuxilios.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (listTiposAuxilios.get(indice).getDescripcion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposAuxilios.get(indice).getDescripcion().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposAuxilios.isEmpty()) {
                            modificarTiposAuxilios.add(listTiposAuxilios.get(indice));
                        } else if (!modificarTiposAuxilios.contains(listTiposAuxilios.get(indice))) {
                            modificarTiposAuxilios.add(listTiposAuxilios.get(indice));
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

                if (!crearTiposAuxilios.contains(filtrarTiposAuxilios.get(indice))) {
                    if (filtrarTiposAuxilios.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposAuxilios.size(); j++) {
                            if (filtrarTiposAuxilios.get(indice).getCodigo().equals(listTiposAuxilios.get(j).getCodigo())) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarTiposAuxilios.size(); j++) {
                            if (j == indice) {
                                if (filtrarTiposAuxilios.get(indice).getCodigo().equals(filtrarTiposAuxilios.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }

                    if (filtrarTiposAuxilios.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarTiposAuxilios.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (filtrarTiposAuxilios.get(indice).getDescripcion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarTiposAuxilios.get(indice).getDescripcion().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposAuxilios.isEmpty()) {
                            modificarTiposAuxilios.add(filtrarTiposAuxilios.get(indice));
                        } else if (!modificarTiposAuxilios.contains(filtrarTiposAuxilios.get(indice))) {
                            modificarTiposAuxilios.add(filtrarTiposAuxilios.get(indice));
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
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
        }

    }
    private BigInteger verificarTablasAuxilios;

    public void verificarBorrado() {
        try {
            System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
            System.out.println("secuencia borrado : " + listTiposAuxilios.get(index).getSecuencia());
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + listTiposAuxilios.get(index).getSecuencia());
                verificarTablasAuxilios = administrarTiposAuxilios.contarTablasAuxiliosTiposAuxilios(listTiposAuxilios.get(index).getSecuencia());
            } else {
                System.out.println("secuencia borrado : " + filtrarTiposAuxilios.get(index).getSecuencia());
                verificarTablasAuxilios = administrarTiposAuxilios.contarTablasAuxiliosTiposAuxilios(filtrarTiposAuxilios.get(index).getSecuencia());
            }
            if (!verificarTablasAuxilios.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarTablasAuxilios = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrandoTiposAuxilios();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void borrandoTiposAuxilios() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposAuxilios");
                if (!modificarTiposAuxilios.isEmpty() && modificarTiposAuxilios.contains(listTiposAuxilios.get(index))) {
                    int modIndex = modificarTiposAuxilios.indexOf(listTiposAuxilios.get(index));
                    modificarTiposAuxilios.remove(modIndex);
                    borrarTiposAuxilios.add(listTiposAuxilios.get(index));
                } else if (!crearTiposAuxilios.isEmpty() && crearTiposAuxilios.contains(listTiposAuxilios.get(index))) {
                    int crearIndex = crearTiposAuxilios.indexOf(listTiposAuxilios.get(index));
                    crearTiposAuxilios.remove(crearIndex);
                } else {
                    borrarTiposAuxilios.add(listTiposAuxilios.get(index));
                }
                listTiposAuxilios.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposAuxilios");
                if (!modificarTiposAuxilios.isEmpty() && modificarTiposAuxilios.contains(filtrarTiposAuxilios.get(index))) {
                    int modIndex = modificarTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                    modificarTiposAuxilios.remove(modIndex);
                    borrarTiposAuxilios.add(filtrarTiposAuxilios.get(index));
                } else if (!crearTiposAuxilios.isEmpty() && crearTiposAuxilios.contains(filtrarTiposAuxilios.get(index))) {
                    int crearIndex = crearTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                    crearTiposAuxilios.remove(crearIndex);
                } else {
                    borrarTiposAuxilios.add(filtrarTiposAuxilios.get(index));
                }
                int VCIndex = listTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                listTiposAuxilios.remove(VCIndex);
                filtrarTiposAuxilios.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposAuxilios.isEmpty() || !crearTiposAuxilios.isEmpty() || !modificarTiposAuxilios.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposAuxilio() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO TIPOAUXILIO");
            if (!borrarTiposAuxilios.isEmpty()) {
                administrarTiposAuxilios.borrarTiposAuxilios(borrarTiposAuxilios);
                //mostrarBorrados
                registrosBorrados = borrarTiposAuxilios.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposAuxilios.clear();
            }
            if (!crearTiposAuxilios.isEmpty()) {
                administrarTiposAuxilios.crearTiposAuxilios(crearTiposAuxilios);
                crearTiposAuxilios.clear();
            }
            if (!modificarTiposAuxilios.isEmpty()) {
                administrarTiposAuxilios.modificarTiposAuxilios(modificarTiposAuxilios);
                modificarTiposAuxilios.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposAuxilios = null;
            guardado = true;
            context.update("form:datosTipoReemplazo");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoAuxilio = listTiposAuxilios.get(index);
            }
            if (tipoLista == 1) {
                editarTipoAuxilio = filtrarTiposAuxilios.get(index);
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

    public void agregarNuevoTiposAuxilios() {
        System.out.println("agregarNuevoTiposAuxilios");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoAuxilios.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoAuxilios.getCodigo());

            for (int x = 0; x < listTiposAuxilios.size(); x++) {
                if (listTiposAuxilios.get(x).getCodigo() == nuevoTipoAuxilios.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoTipoAuxilios.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una descripción \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposAuxilios = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoAuxilios.setSecuencia(l);

            crearTiposAuxilios.add(nuevoTipoAuxilios);

            listTiposAuxilios.add(nuevoTipoAuxilios);
            nuevoTipoAuxilios = new TiposAuxilios();
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposReemplazos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposAuxilios() {
        System.out.println("limpiarNuevoTiposAuxilios");
        nuevoTipoAuxilios = new TiposAuxilios();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposAuxilios() {
        System.out.println("duplicandoTiposAuxilios");
        if (index >= 0) {
            duplicarTipoAuxilio = new TiposAuxilios();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoAuxilio.setSecuencia(l);
                duplicarTipoAuxilio.setCodigo(listTiposAuxilios.get(index).getCodigo());
                duplicarTipoAuxilio.setDescripcion(listTiposAuxilios.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoAuxilio.setSecuencia(l);
                duplicarTipoAuxilio.setCodigo(filtrarTiposAuxilios.get(index).getCodigo());
                duplicarTipoAuxilio.setDescripcion(filtrarTiposAuxilios.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOSAUXILIOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarTipoAuxilio.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposAuxilios.size(); x++) {
                if (listTiposAuxilios.get(x).getCodigo() == duplicarTipoAuxilio.getCodigo()) {
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
        if (duplicarTipoAuxilio.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoAuxilio.getSecuencia() + "  " + duplicarTipoAuxilio.getCodigo());
            if (crearTiposAuxilios.contains(duplicarTipoAuxilio)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposAuxilios.add(duplicarTipoAuxilio);
            crearTiposAuxilios.add(duplicarTipoAuxilio);
            context.update("form:datosTipoReemplazo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposAuxilios = null;
                tipoLista = 0;
            }
            duplicarTipoAuxilio = new TiposAuxilios();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposAuxilios() {
        duplicarTipoAuxilio = new TiposAuxilios();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSAUXILIOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSAUXILIOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposAuxilios.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSAUXILIOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSAUXILIOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposAuxilios> getListTiposAuxilios() {
        if (listTiposAuxilios == null) {
            listTiposAuxilios = administrarTiposAuxilios.consultarTiposAuxilios();
        }
        return listTiposAuxilios;
    }

    public void setListTiposAuxilios(List<TiposAuxilios> listTiposAuxilios) {
        this.listTiposAuxilios = listTiposAuxilios;
    }

    public List<TiposAuxilios> getFiltrarTiposAuxilios() {
        return filtrarTiposAuxilios;
    }

    public void setFiltrarTiposAuxilios(List<TiposAuxilios> filtrarTiposAuxilios) {
        this.filtrarTiposAuxilios = filtrarTiposAuxilios;
    }

    public TiposAuxilios getNuevoTipoAuxilios() {
        return nuevoTipoAuxilios;
    }

    public void setNuevoTipoAuxilios(TiposAuxilios nuevoTipoAuxilios) {
        this.nuevoTipoAuxilios = nuevoTipoAuxilios;
    }

    public TiposAuxilios getDuplicarTipoAuxilio() {
        return duplicarTipoAuxilio;
    }

    public void setDuplicarTipoAuxilio(TiposAuxilios duplicarTipoAuxilio) {
        this.duplicarTipoAuxilio = duplicarTipoAuxilio;
    }

    public TiposAuxilios getEditarTipoAuxilio() {
        return editarTipoAuxilio;
    }

    public void setEditarTipoAuxilio(TiposAuxilios editarTipoAuxilio) {
        this.editarTipoAuxilio = editarTipoAuxilio;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
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

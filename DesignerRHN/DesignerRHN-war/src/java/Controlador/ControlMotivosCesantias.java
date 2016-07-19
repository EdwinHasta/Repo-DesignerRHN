/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MotivosCesantias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosCesantiasInterface;
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
public class ControlMotivosCesantias implements Serializable {

    @EJB
    AdministrarMotivosCesantiasInterface administrarMotivosCesantias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<MotivosCesantias> listMotivosCesantias;
    private List<MotivosCesantias> filtrarMotivosCesantias;
    private List<MotivosCesantias> crearMotivosCesantias;
    private List<MotivosCesantias> modificarMotivosCesantias;
    private List<MotivosCesantias> borrarMotivosCesantias;
    private MotivosCesantias nuevoMotivoCesantia;
    private MotivosCesantias duplicarMotivoCesantia;
    private MotivosCesantias editarMotivoCesantia;
    private MotivosCesantias motivoCesantiaSeleccionado;
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
    private String mensajeValidacion,paginaAnterior;

    public ControlMotivosCesantias() {
        listMotivosCesantias = null;
        crearMotivosCesantias = new ArrayList<MotivosCesantias>();
        modificarMotivosCesantias = new ArrayList<MotivosCesantias>();
        borrarMotivosCesantias = new ArrayList<MotivosCesantias>();
        permitirIndex = true;
        editarMotivoCesantia = new MotivosCesantias();
        nuevoMotivoCesantia = new MotivosCesantias();
        duplicarMotivoCesantia = new MotivosCesantias();
        guardado = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarMotivosCesantias.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void recibirPag(String pag) {
        paginaAnterior = pag;
        //contarRegistrosNovedades();
        if (!listMotivosCesantias.isEmpty()) {
            motivoCesantiaSeleccionado = listMotivosCesantias.get(0);
        }
    }

    public String volverPagAnterior() {
        return paginaAnterior;
    }
    
    
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLMOTIVOSCESANTIAS EVENTOFILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROLMOTIVOSCESANTIAS EVENTOFILTRAR  ERROR =" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMotivosCesantias.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLMOTIVOSCESANTIAS ASIGNAR INDEX \n");
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
            System.out.println("ERROR CONTROLMOTIVOSCESANTIAS ASIGNAR INDEX ERROR = " + e);
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
            filtrarMotivosCesantias = null;
            tipoLista = 0;
        }

        borrarMotivosCesantias.clear();
        crearMotivosCesantias.clear();
        modificarMotivosCesantias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosCesantias = null;
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
            filtrarMotivosCesantias = null;
            tipoLista = 0;
        }
    }

    public void modificandoMotivoCensantia(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVOSCESANTIA");
        index = indice;

        int contador = 0;
        int contadorGuardar = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVOEMBARGOS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMotivosCesantias.contains(listMotivosCesantias.get(indice))) {
                    if (listMotivosCesantias.get(indice).getCodigo() == a || listMotivosCesantias.get(indice).getCodigo().equals(null)) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosCesantias.size(); j++) {
                            if (j != indice) {
                                if (listMotivosCesantias.get(indice).getCodigo().equals(listMotivosCesantias.get(j).getCodigo())) {
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
                    if (listMotivosCesantias.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listMotivosCesantias.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (listMotivosCesantias.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listMotivosCesantias.get(indice).getNombre().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarMotivosCesantias.isEmpty()) {
                            modificarMotivosCesantias.add(listMotivosCesantias.get(indice));
                        } else if (!modificarMotivosCesantias.contains(listMotivosCesantias.get(indice))) {
                            modificarMotivosCesantias.add(listMotivosCesantias.get(indice));
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

                if (!crearMotivosCesantias.contains(filtrarMotivosCesantias.get(indice))) {
                    if (filtrarMotivosCesantias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {

                        for (int j = 0; j < listMotivosCesantias.size(); j++) {
                            if (j != indice) {
                                if (filtrarMotivosCesantias.get(indice).getCodigo().equals(listMotivosCesantias.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }

                        for (int j = 0; j < filtrarMotivosCesantias.size(); j++) {
                            if (j != indice) {
                                if (filtrarMotivosCesantias.get(indice).getCodigo().equals(filtrarMotivosCesantias.get(j).getCodigo())) {
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

                    if (filtrarMotivosCesantias.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarMotivosCesantias.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (filtrarMotivosCesantias.get(indice).getNombre() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarMotivosCesantias.get(indice).getNombre().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarMotivosCesantias.isEmpty()) {
                            modificarMotivosCesantias.add(filtrarMotivosCesantias.get(indice));
                        } else if (!modificarMotivosCesantias.contains(filtrarMotivosCesantias.get(indice))) {
                            modificarMotivosCesantias.add(filtrarMotivosCesantias.get(indice));
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
    private BigInteger verificarEerPrestamos;

    public void verificarBorrado() {
        try {
            System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
            System.out.println("secuencia borrado : " + listMotivosCesantias.get(index).getSecuencia());
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + listMotivosCesantias.get(index).getSecuencia());
                verificarEerPrestamos = administrarMotivosCesantias.contarNovedadesSistemasMotivoCesantia(listMotivosCesantias.get(index).getSecuencia());
            } else {
                System.out.println("secuencia borrado : " + filtrarMotivosCesantias.get(index).getSecuencia());
                verificarEerPrestamos = administrarMotivosCesantias.contarNovedadesSistemasMotivoCesantia(filtrarMotivosCesantias.get(index).getSecuencia());
            }
            if (!verificarEerPrestamos.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarEerPrestamos = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrandoMotivosCesantias();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void borrandoMotivosCesantias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoMotivosCesantias");
                if (!modificarMotivosCesantias.isEmpty() && modificarMotivosCesantias.contains(listMotivosCesantias.get(index))) {
                    int modIndex = modificarMotivosCesantias.indexOf(listMotivosCesantias.get(index));
                    modificarMotivosCesantias.remove(modIndex);
                    borrarMotivosCesantias.add(listMotivosCesantias.get(index));
                } else if (!crearMotivosCesantias.isEmpty() && crearMotivosCesantias.contains(listMotivosCesantias.get(index))) {
                    int crearIndex = crearMotivosCesantias.indexOf(listMotivosCesantias.get(index));
                    crearMotivosCesantias.remove(crearIndex);
                } else {
                    borrarMotivosCesantias.add(listMotivosCesantias.get(index));
                }
                listMotivosCesantias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoMotivosCesantias");
                if (!modificarMotivosCesantias.isEmpty() && modificarMotivosCesantias.contains(filtrarMotivosCesantias.get(index))) {
                    int modIndex = modificarMotivosCesantias.indexOf(filtrarMotivosCesantias.get(index));
                    modificarMotivosCesantias.remove(modIndex);
                    borrarMotivosCesantias.add(filtrarMotivosCesantias.get(index));
                } else if (!crearMotivosCesantias.isEmpty() && crearMotivosCesantias.contains(filtrarMotivosCesantias.get(index))) {
                    int crearIndex = crearMotivosCesantias.indexOf(filtrarMotivosCesantias.get(index));
                    crearMotivosCesantias.remove(crearIndex);
                } else {
                    borrarMotivosCesantias.add(filtrarMotivosCesantias.get(index));
                }
                int VCIndex = listMotivosCesantias.indexOf(filtrarMotivosCesantias.get(index));
                listMotivosCesantias.remove(VCIndex);
                filtrarMotivosCesantias.remove(index);

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

        if (!borrarMotivosCesantias.isEmpty() || !crearMotivosCesantias.isEmpty() || !modificarMotivosCesantias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarMotivoCesantia() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO MOTIVOCESANTIA");
            if (!borrarMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.borrarMotivosCesantias(borrarMotivosCesantias);
                //mostrarBorrados
                registrosBorrados = borrarMotivosCesantias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivosCesantias.clear();
            }
            if (!crearMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.crearMotivosCesantias(crearMotivosCesantias);

                crearMotivosCesantias.clear();
            }
            if (!modificarMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.modificarMotivosCesantias(modificarMotivosCesantias);
                modificarMotivosCesantias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosCesantias = null;
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
                editarMotivoCesantia = listMotivosCesantias.get(index);
            }
            if (tipoLista == 1) {
                editarMotivoCesantia = filtrarMotivosCesantias.get(index);
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

    public void agregarNuevoMotivosCesantias() {
        System.out.println("agregarNuevoMotivosCesantias");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoCesantia.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoCesantia.getCodigo());

            for (int x = 0; x < listMotivosCesantias.size(); x++) {
                if (listMotivosCesantias.get(x).getCodigo() == nuevoMotivoCesantia.getCodigo()) {
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
        if (nuevoMotivoCesantia.getNombre() == (null)) {
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
                filtrarMotivosCesantias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoCesantia.setSecuencia(l);

            crearMotivosCesantias.add(nuevoMotivoCesantia);

            listMotivosCesantias.add(nuevoMotivoCesantia);
            nuevoMotivoCesantia = new MotivosCesantias();
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

    public void limpiarNuevoMotivosCesantias() {
        System.out.println("limpiarNuevoMotivosCesantias");
        nuevoMotivoCesantia = new MotivosCesantias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoMotivosCesantias() {
        System.out.println("duplicandoMotivosCesantias");
        if (index >= 0) {
            duplicarMotivoCesantia = new MotivosCesantias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoCesantia.setSecuencia(l);
                duplicarMotivoCesantia.setCodigo(listMotivosCesantias.get(index).getCodigo());
                duplicarMotivoCesantia.setNombre(listMotivosCesantias.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarMotivoCesantia.setSecuencia(l);
                duplicarMotivoCesantia.setCodigo(filtrarMotivosCesantias.get(index).getCodigo());
                duplicarMotivoCesantia.setNombre(filtrarMotivosCesantias.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR MOTIVOSCESANTIAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarMotivoCesantia.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosCesantias.size(); x++) {
                if (listMotivosCesantias.get(x).getCodigo() == duplicarMotivoCesantia.getCodigo()) {
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
        if (duplicarMotivoCesantia.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoCesantia.getSecuencia() + "  " + duplicarMotivoCesantia.getCodigo());
            if (crearMotivosCesantias.contains(duplicarMotivoCesantia)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosCesantias.add(duplicarMotivoCesantia);
            crearMotivosCesantias.add(duplicarMotivoCesantia);
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
                filtrarMotivosCesantias = null;
                tipoLista = 0;
            }
            duplicarMotivoCesantia = new MotivosCesantias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarMotivosCesantias() {
        duplicarMotivoCesantia = new MotivosCesantias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MOTIVOSCENSANTIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MOTIVOSCENSANTIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosCesantias.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSCENSANTIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSCENSANTIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<MotivosCesantias> getListMotivosCesantias() {
        if (listMotivosCesantias == null) {
            listMotivosCesantias = administrarMotivosCesantias.consultarMotivosCesantias();
        }
        return listMotivosCesantias;
    }

    public void setListMotivosCesantias(List<MotivosCesantias> listMotivosCesantias) {
        this.listMotivosCesantias = listMotivosCesantias;
    }

    public List<MotivosCesantias> getFiltrarMotivosCesantias() {
        return filtrarMotivosCesantias;
    }

    public void setFiltrarMotivosCesantias(List<MotivosCesantias> filtrarMotivosCesantias) {
        this.filtrarMotivosCesantias = filtrarMotivosCesantias;
    }

    public MotivosCesantias getNuevoMotivoCesantia() {
        return nuevoMotivoCesantia;
    }

    public void setNuevoMotivoCesantia(MotivosCesantias nuevoMotivoCesantia) {
        this.nuevoMotivoCesantia = nuevoMotivoCesantia;
    }

    public MotivosCesantias getDuplicarMotivoCesantia() {
        return duplicarMotivoCesantia;
    }

    public void setDuplicarMotivoCesantia(MotivosCesantias duplicarMotivoCesantia) {
        this.duplicarMotivoCesantia = duplicarMotivoCesantia;
    }

    public MotivosCesantias getEditarMotivoCesantia() {
        return editarMotivoCesantia;
    }

    public void setEditarMotivoCesantia(MotivosCesantias editarMotivoCesantia) {
        this.editarMotivoCesantia = editarMotivoCesantia;
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

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    
}

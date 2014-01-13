/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposAccidentes;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposAccidentesInterface;
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
public class ControlTiposAccidentes implements Serializable {

    @EJB
    AdministrarTiposAccidentesInterface administrarTiposAccidentes;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposAccidentes> listTiposAccidentes;
    private List<TiposAccidentes> filtrarTiposAccidentes;
    private List<TiposAccidentes> crearTiposAccidentes;
    private List<TiposAccidentes> modificarTiposAccidentes;
    private List<TiposAccidentes> borrarTiposAccidentes;
    private TiposAccidentes nuevaTipoAccidente;
    private TiposAccidentes duplicarTipoAccidente;
    private TiposAccidentes editarTipoAccidente;
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
    private BigInteger verificarBorradoAccidentes;
    private BigInteger verificarSoAccidentesMedicos;

    public ControlTiposAccidentes() {
        listTiposAccidentes = null;
        crearTiposAccidentes = new ArrayList<TiposAccidentes>();
        modificarTiposAccidentes = new ArrayList<TiposAccidentes>();
        borrarTiposAccidentes = new ArrayList<TiposAccidentes>();
        permitirIndex = true;
        editarTipoAccidente = new TiposAccidentes();
        nuevaTipoAccidente = new TiposAccidentes();
        duplicarTipoAccidente = new TiposAccidentes();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n EVENTO FILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR EVENTO FILTRAR ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposAccidentes.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLTIPOSACCIDENTES  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLTIPOSACCIDENTES asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposAccidentes");
            bandera = 0;
            filtrarTiposAccidentes = null;
            tipoLista = 0;
        }

        borrarTiposAccidentes.clear();
        crearTiposAccidentes.clear();
        modificarTiposAccidentes.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposAccidentes = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datosTiposAccidentes");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:codigo");
            codigo.setFilterStyle("width: 360px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTiposAccidentes");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTiposAccidentes");
            bandera = 0;
            filtrarTiposAccidentes = null;
            tipoLista = 0;
        }
    }

    public void modificandoTipoAccidente(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR ELEMENTOS PARTE CUERPO");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO TIPO ACCIDENTE CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearTiposAccidentes.contains(listTiposAccidentes.get(indice))) {
                    if (listTiposAccidentes.get(indice).getCodigo() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposAccidentes.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposAccidentes.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposAccidentes.size(); j++) {
                            if (j != indice) {
                                if (listTiposAccidentes.get(indice).getCodigo().equals(listTiposAccidentes.get(j).getCodigo())) {
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
                    if (listTiposAccidentes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposAccidentes.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposAccidentes.isEmpty()) {
                            modificarTiposAccidentes.add(listTiposAccidentes.get(indice));
                        } else if (!modificarTiposAccidentes.contains(listTiposAccidentes.get(indice))) {
                            modificarTiposAccidentes.add(listTiposAccidentes.get(indice));
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

                if (!crearTiposAccidentes.contains(filtrarTiposAccidentes.get(indice))) {
                    if (filtrarTiposAccidentes.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposAccidentes.get(indice).getCodigo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposAccidentes.size(); j++) {

                            System.err.println("indice lista  indice : " + listTiposAccidentes.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarTiposAccidentes.get(indice).getCodigo().equals(listTiposAccidentes.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }

                        for (int j = 0; j < filtrarTiposAccidentes.size(); j++) {
                            System.err.println("indice filtrar indice : " + filtrarTiposAccidentes.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarTiposAccidentes.get(indice).getCodigo().equals(filtrarTiposAccidentes.get(j).getCodigo())) {
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

                    if (filtrarTiposAccidentes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposAccidentes.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposAccidentes.isEmpty()) {
                            modificarTiposAccidentes.add(filtrarTiposAccidentes.get(indice));
                        } else if (!modificarTiposAccidentes.contains(filtrarTiposAccidentes.get(indice))) {
                            modificarTiposAccidentes.add(filtrarTiposAccidentes.get(indice));
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
            context.update("form:datosTiposAccidentes");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTipoAccidente() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrandoTipoAccidente");
                if (!modificarTiposAccidentes.isEmpty() && modificarTiposAccidentes.contains(listTiposAccidentes.get(index))) {
                    int modIndex = modificarTiposAccidentes.indexOf(listTiposAccidentes.get(index));
                    modificarTiposAccidentes.remove(modIndex);
                    borrarTiposAccidentes.add(listTiposAccidentes.get(index));
                } else if (!crearTiposAccidentes.isEmpty() && crearTiposAccidentes.contains(listTiposAccidentes.get(index))) {
                    int crearIndex = crearTiposAccidentes.indexOf(listTiposAccidentes.get(index));
                    crearTiposAccidentes.remove(crearIndex);
                } else {
                    borrarTiposAccidentes.add(listTiposAccidentes.get(index));
                }
                listTiposAccidentes.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTipoAccidente");
                if (!modificarTiposAccidentes.isEmpty() && modificarTiposAccidentes.contains(filtrarTiposAccidentes.get(index))) {
                    int modIndex = modificarTiposAccidentes.indexOf(filtrarTiposAccidentes.get(index));
                    modificarTiposAccidentes.remove(modIndex);
                    borrarTiposAccidentes.add(filtrarTiposAccidentes.get(index));
                } else if (!crearTiposAccidentes.isEmpty() && crearTiposAccidentes.contains(filtrarTiposAccidentes.get(index))) {
                    int crearIndex = crearTiposAccidentes.indexOf(filtrarTiposAccidentes.get(index));
                    crearTiposAccidentes.remove(crearIndex);
                } else {
                    borrarTiposAccidentes.add(filtrarTiposAccidentes.get(index));
                }
                int VCIndex = listTiposAccidentes.indexOf(filtrarTiposAccidentes.get(index));
                listTiposAccidentes.remove(VCIndex);
                filtrarTiposAccidentes.remove(index);

            }
            context.update("form:datosTiposAccidentes");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("verificarBorrado");
        try {
            if (tipoLista == 0) {
                verificarBorradoAccidentes = administrarTiposAccidentes.verificarBorradoAccidentes(listTiposAccidentes.get(index).getSecuencia());
                verificarSoAccidentesMedicos = administrarTiposAccidentes.verificarSoAccidentesMedicos(listTiposAccidentes.get(index).getSecuencia());
            } else {
                verificarBorradoAccidentes = administrarTiposAccidentes.verificarBorradoAccidentes(filtrarTiposAccidentes.get(index).getSecuencia());
                verificarSoAccidentesMedicos = administrarTiposAccidentes.verificarSoAccidentesMedicos(filtrarTiposAccidentes.get(index).getSecuencia());
            }
            if (verificarBorradoAccidentes.equals(new BigInteger("0")) && verificarSoAccidentesMedicos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoTipoAccidente();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarBorradoAccidentes = new BigInteger("-1");
                verificarSoAccidentesMedicos = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR TIPOS ACCIDENTES  verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposAccidentes.isEmpty() || !crearTiposAccidentes.isEmpty() || !modificarTiposAccidentes.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoTiposAccidentes() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO TIPOS ACCIDENTES");
            if (!borrarTiposAccidentes.isEmpty()) {
                for (int i = 0; i < borrarTiposAccidentes.size(); i++) {
                    System.out.println("Borrando...");
                    administrarTiposAccidentes.borrarTiposAccidentes(borrarTiposAccidentes.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarTiposAccidentes.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposAccidentes.clear();
            }
            if (!crearTiposAccidentes.isEmpty()) {
                for (int i = 0; i < crearTiposAccidentes.size(); i++) {

                    System.out.println("Creando...");
                    administrarTiposAccidentes.crearTiposAccidentes(crearTiposAccidentes.get(i));

                }
                crearTiposAccidentes.clear();
            }
            if (!modificarTiposAccidentes.isEmpty()) {
                administrarTiposAccidentes.modificarTiposAccidentes(modificarTiposAccidentes);
                modificarTiposAccidentes.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposAccidentes = null;
            context.update("form:datosTiposAccidentes");
            k = 0;
            if (guardado == false) {
                guardado = true;
            }
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoAccidente = listTiposAccidentes.get(index);
            }
            if (tipoLista == 1) {
                editarTipoAccidente = filtrarTiposAccidentes.get(index);
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

    public void agregarNuevoTipoAccidente() {
        System.out.println("agregarNuevoTipoAccidente");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaTipoAccidente.getCodigo() == null) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaTipoAccidente.getCodigo());

            for (int x = 0; x < listTiposAccidentes.size(); x++) {
                if (listTiposAccidentes.get(x).getCodigo().equals(nuevaTipoAccidente.getCodigo())) {
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
        if (nuevaTipoAccidente.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener un nombre \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposAccidentes");
                bandera = 0;
                filtrarTiposAccidentes = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevaTipoAccidente.setSecuencia(l);

            crearTiposAccidentes.add(nuevaTipoAccidente);

            listTiposAccidentes.add(nuevaTipoAccidente);
            nuevaTipoAccidente = new TiposAccidentes();
            context.update("form:datosTiposAccidentes");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposAccidentes.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTipoAccidente() {
        System.out.println("limpiarNuevoTipoAccidente");
        nuevaTipoAccidente = new TiposAccidentes();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTipoAccidente() {
        System.out.println("duplicandoTipoAccidente");
        if (index >= 0) {
            duplicarTipoAccidente = new TiposAccidentes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoAccidente.setSecuencia(l);
                duplicarTipoAccidente.setCodigo(listTiposAccidentes.get(index).getCodigo());
                duplicarTipoAccidente.setNombre(listTiposAccidentes.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarTipoAccidente.setSecuencia(l);
                duplicarTipoAccidente.setCodigo(filtrarTiposAccidentes.get(index).getCodigo());
                duplicarTipoAccidente.setNombre(filtrarTiposAccidentes.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTA");
            context.execute("duplicarRegistroTiposAccidentes.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR TIPO ACCIDENTE");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoAccidente.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTipoAccidente.getNombre());

        if (duplicarTipoAccidente.getCodigo() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            for (int x = 0; x < listTiposAccidentes.size(); x++) {
                if (listTiposAccidentes.get(x).getCodigo().equals(duplicarTipoAccidente.getCodigo())) {
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
        if (duplicarTipoAccidente.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoAccidente.getSecuencia() + "  " + duplicarTipoAccidente.getCodigo());
            if (crearTiposAccidentes.contains(duplicarTipoAccidente)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposAccidentes.add(duplicarTipoAccidente);
            crearTiposAccidentes.add(duplicarTipoAccidente);
            context.update("form:datosTiposAccidentes");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTiposAccidentes:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTiposAccidentes");
                bandera = 0;
                filtrarTiposAccidentes = null;
                tipoLista = 0;
            }
            duplicarTipoAccidente = new TiposAccidentes();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposAccidentes.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTipoAccidente() {
        duplicarTipoAccidente = new TiposAccidentes();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposAccidentesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSACCIDENTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTiposAccidentesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSACCIDENTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposAccidentes.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSACCIDENTES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSACCIDENTES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposAccidentes> getListTiposAccidentes() {
        if (listTiposAccidentes == null) {
            listTiposAccidentes = administrarTiposAccidentes.mostrarTiposAccidentes();
        }
        return listTiposAccidentes;
    }

    public void setListTiposAccidentes(List<TiposAccidentes> listTiposAccidentes) {
        this.listTiposAccidentes = listTiposAccidentes;
    }

    public List<TiposAccidentes> getFiltrarTiposAccidentes() {
        return filtrarTiposAccidentes;
    }

    public void setFiltrarTiposAccidentes(List<TiposAccidentes> filtrarTiposAccidentes) {
        this.filtrarTiposAccidentes = filtrarTiposAccidentes;
    }

    public TiposAccidentes getNuevaTipoAccidente() {
        return nuevaTipoAccidente;
    }

    public void setNuevaTipoAccidente(TiposAccidentes nuevaTipoAccidente) {
        this.nuevaTipoAccidente = nuevaTipoAccidente;
    }

    public TiposAccidentes getDuplicarTipoAccidente() {
        return duplicarTipoAccidente;
    }

    public void setDuplicarTipoAccidente(TiposAccidentes duplicarTipoAccidente) {
        this.duplicarTipoAccidente = duplicarTipoAccidente;
    }

    public TiposAccidentes getEditarTipoAccidente() {
        return editarTipoAccidente;
    }

    public void setEditarTipoAccidente(TiposAccidentes editarTipoAccidente) {
        this.editarTipoAccidente = editarTipoAccidente;
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

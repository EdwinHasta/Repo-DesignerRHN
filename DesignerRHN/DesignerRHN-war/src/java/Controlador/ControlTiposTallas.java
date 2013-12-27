/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposTallas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposTallasInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ControlTiposTallas implements Serializable {

    @EJB
    AdministrarTiposTallasInterface administrarTiposTallas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposTallas> listTiposTallas;
    private List<TiposTallas> filtrarTiposTallas;
    private List<TiposTallas> crearTiposTallas;
    private List<TiposTallas> modificarTiposTallas;
    private List<TiposTallas> borrarTiposTalas;
    private TiposTallas nuevoTipoTalla;
    private TiposTallas duplicarTipoTalla;
    private TiposTallas editarTipoTalla;
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
    private BigDecimal elementos;
    private BigDecimal vigenciasTallas;

    public ControlTiposTallas() {
        listTiposTallas = null;
        crearTiposTallas = new ArrayList<TiposTallas>();
        modificarTiposTallas = new ArrayList<TiposTallas>();
        borrarTiposTalas = new ArrayList<TiposTallas>();
        permitirIndex = true;
        editarTipoTalla = new TiposTallas();
        nuevoTipoTalla = new TiposTallas();
        duplicarTipoTalla = new TiposTallas();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposTallas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposTallas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposTallas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposTallas.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposTallas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoTalla");
            bandera = 0;
            filtrarTiposTallas = null;
            tipoLista = 0;
        }

        borrarTiposTalas.clear();
        crearTiposTallas.clear();
        modificarTiposTallas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposTallas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoTalla");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:codigo");
            codigo.setFilterStyle("width: 300px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTipoTalla");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoTalla");
            bandera = 0;
            filtrarTiposTallas = null;
            tipoLista = 0;
        }
    }

    public void modificarTipoTalla(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO TALLA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR TALLA, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposTallas.contains(listTiposTallas.get(indice))) {
                    if (listTiposTallas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposTallas.size(); j++) {
                            if (j != indice) {
                                if (listTiposTallas.get(indice).getCodigo() == listTiposTallas.get(j).getCodigo()) {
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
                    if (listTiposTallas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposTallas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposTallas.isEmpty()) {
                            modificarTiposTallas.add(listTiposTallas.get(indice));
                        } else if (!modificarTiposTallas.contains(listTiposTallas.get(indice))) {
                            modificarTiposTallas.add(listTiposTallas.get(indice));
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

                if (!crearTiposTallas.contains(filtrarTiposTallas.get(indice))) {
                    if (filtrarTiposTallas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposTallas.size(); j++) {
                            if (j == indice) {
                                if (listTiposTallas.get(indice).getCodigo() == listTiposTallas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarTiposTallas.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposTallas.get(indice).getCodigo().equals(filtrarTiposTallas.get(j).getCodigo())) {
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

                    if (filtrarTiposTallas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposTallas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposTallas.isEmpty()) {
                            modificarTiposTallas.add(filtrarTiposTallas.get(indice));
                        } else if (!modificarTiposTallas.contains(filtrarTiposTallas.get(indice))) {
                            modificarTiposTallas.add(filtrarTiposTallas.get(indice));
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
            context.update("form:datosTipoTalla");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposTallas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposTallas");
                if (!modificarTiposTallas.isEmpty() && modificarTiposTallas.contains(listTiposTallas.get(index))) {
                    int modIndex = modificarTiposTallas.indexOf(listTiposTallas.get(index));
                    modificarTiposTallas.remove(modIndex);
                    borrarTiposTalas.add(listTiposTallas.get(index));
                } else if (!crearTiposTallas.isEmpty() && crearTiposTallas.contains(listTiposTallas.get(index))) {
                    int crearIndex = crearTiposTallas.indexOf(listTiposTallas.get(index));
                    crearTiposTallas.remove(crearIndex);
                } else {
                    borrarTiposTalas.add(listTiposTallas.get(index));
                }
                listTiposTallas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposTallas ");
                if (!modificarTiposTallas.isEmpty() && modificarTiposTallas.contains(filtrarTiposTallas.get(index))) {
                    int modIndex = modificarTiposTallas.indexOf(filtrarTiposTallas.get(index));
                    modificarTiposTallas.remove(modIndex);
                    borrarTiposTalas.add(filtrarTiposTallas.get(index));
                } else if (!crearTiposTallas.isEmpty() && crearTiposTallas.contains(filtrarTiposTallas.get(index))) {
                    int crearIndex = crearTiposTallas.indexOf(filtrarTiposTallas.get(index));
                    crearTiposTallas.remove(crearIndex);
                } else {
                    borrarTiposTalas.add(filtrarTiposTallas.get(index));
                }
                int VCIndex = listTiposTallas.indexOf(filtrarTiposTallas.get(index));
                listTiposTallas.remove(VCIndex);
                filtrarTiposTallas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosTipoTalla");
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de ControlTiposTallas ");
            if (tipoLista == 0) {
                elementos = administrarTiposTallas.verificarBorradoElementos(listTiposTallas.get(index).getSecuencia());
                vigenciasTallas = administrarTiposTallas.verificarBorradoVigenciasTallas(listTiposTallas.get(index).getSecuencia());
            } else {
                elementos = administrarTiposTallas.verificarBorradoElementos(filtrarTiposTallas.get(index).getSecuencia());
                vigenciasTallas = administrarTiposTallas.verificarBorradoVigenciasTallas(filtrarTiposTallas.get(index).getSecuencia());
            }
            if (elementos.intValueExact() == 0 && vigenciasTallas.intValueExact() == 0) {
                System.out.println("Borrado==0");
                borrandoTiposTallas();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                elementos = new BigDecimal(-1);

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposTallas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposTalas.isEmpty() || !crearTiposTallas.isEmpty() || !modificarTiposTallas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposTallas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposTallas");
            if (!borrarTiposTalas.isEmpty()) {
                for (int i = 0; i < borrarTiposTalas.size(); i++) {
                    System.out.println("Borrando...");
                    administrarTiposTallas.borrarTiposTallas(borrarTiposTalas.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarTiposTalas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposTalas.clear();
            }
            if (!crearTiposTallas.isEmpty()) {
                for (int i = 0; i < crearTiposTallas.size(); i++) {

                    System.out.println("Creando...");
                    administrarTiposTallas.crearTiposTallas(crearTiposTallas.get(i));

                }
                crearTiposTallas.clear();
            }
            if (!modificarTiposTallas.isEmpty()) {
                administrarTiposTallas.modificarTiposTallas(modificarTiposTallas);
                modificarTiposTallas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposTallas = null;
            context.update("form:datosTipoTalla");
            k = 0;
            guardado=true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoTalla = listTiposTallas.get(index);
            }
            if (tipoLista == 1) {
                editarTipoTalla = filtrarTiposTallas.get(index);
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

    public void agregarNuevoTiposTallas() {
        System.out.println("agregarNuevoTiposTallas");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoTalla.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoTalla.getCodigo());

            for (int x = 0; x < listTiposTallas.size(); x++) {
                if (listTiposTallas.get(x).getCodigo() == nuevoTipoTalla.getCodigo()) {
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
        if (nuevoTipoTalla.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoTalla");
                bandera = 0;
                filtrarTiposTallas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoTalla.setSecuencia(l);

            crearTiposTallas.add(nuevoTipoTalla);

            listTiposTallas.add(nuevoTipoTalla);
            nuevoTipoTalla = new TiposTallas();
            context.update("form:datosTipoTalla");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposTallas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposTallas() {
        System.out.println("limpiarNuevoTiposTallas");
        nuevoTipoTalla = new TiposTallas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposTallas() {
        System.out.println("duplicandoTiposTallas");
        if (index >= 0) {
            duplicarTipoTalla = new TiposTallas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoTalla.setSecuencia(l);
                duplicarTipoTalla.setCodigo(listTiposTallas.get(index).getCodigo());
                duplicarTipoTalla.setDescripcion(listTiposTallas.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoTalla.setSecuencia(l);
                duplicarTipoTalla.setCodigo(filtrarTiposTallas.get(index).getCodigo());
                duplicarTipoTalla.setDescripcion(filtrarTiposTallas.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTT");
            context.execute("duplicarRegistroTiposTallas.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS TALLAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoTalla.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTipoTalla.getDescripcion());

        if (duplicarTipoTalla.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposTallas.size(); x++) {
                if (listTiposTallas.get(x).getCodigo() == duplicarTipoTalla.getCodigo()) {
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
        if (duplicarTipoTalla.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoTalla.getSecuencia() + "  " + duplicarTipoTalla.getCodigo());
            if (crearTiposTallas.contains(duplicarTipoTalla)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposTallas.add(duplicarTipoTalla);
            crearTiposTallas.add(duplicarTipoTalla);
            context.update("form:datosTipoTalla");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoTalla:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoTalla");
                bandera = 0;
                filtrarTiposTallas = null;
                tipoLista = 0;
            }
            duplicarTipoTalla = new TiposTallas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposTallas.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposTallas() {
        duplicarTipoTalla = new TiposTallas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoTallaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSTALLAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoTallaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSTALLAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposTallas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSTALLAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSTALLAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposTallas> getListTiposTallas() {
        if (listTiposTallas == null) {
            listTiposTallas = administrarTiposTallas.mostrarTiposTallas();
        }

        return listTiposTallas;
    }

    public void setListTiposTallas(List<TiposTallas> listTiposTallas) {
        this.listTiposTallas = listTiposTallas;
    }

    public List<TiposTallas> getFiltrarTiposTallas() {
        return filtrarTiposTallas;
    }

    public void setFiltrarTiposTallas(List<TiposTallas> filtrarTiposTallas) {
        this.filtrarTiposTallas = filtrarTiposTallas;
    }

    public TiposTallas getNuevoTipoTalla() {
        return nuevoTipoTalla;
    }

    public void setNuevoTipoTalla(TiposTallas nuevoTipoTalla) {
        this.nuevoTipoTalla = nuevoTipoTalla;
    }

    public TiposTallas getDuplicarTipoTalla() {
        return duplicarTipoTalla;
    }

    public void setDuplicarTipoTalla(TiposTallas duplicarTipoTalla) {
        this.duplicarTipoTalla = duplicarTipoTalla;
    }

    public TiposTallas getEditarTipoTalla() {
        return editarTipoTalla;
    }

    public void setEditarTipoTalla(TiposTallas editarTipoTalla) {
        this.editarTipoTalla = editarTipoTalla;
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

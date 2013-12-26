/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposChequeos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposChequeosInterface;
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
public class ControlTiposChequeos implements Serializable {

    @EJB
    AdministrarTiposChequeosInterface administrarTiposChequeos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposChequeos> listTiposChequeos;
    private List<TiposChequeos> filtrarTiposChequeos;
    private List<TiposChequeos> crearTiposChequeos;
    private List<TiposChequeos> modificarTiposChequeos;
    private List<TiposChequeos> borrarTiposChequeos;
    private TiposChequeos nuevoTipoChequeo;
    private TiposChequeos duplicarTipoChequeo;
    private TiposChequeos editarTipoChequeo;
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

    public ControlTiposChequeos() {
        listTiposChequeos = null;
        crearTiposChequeos = new ArrayList<TiposChequeos>();
        modificarTiposChequeos = new ArrayList<TiposChequeos>();
        borrarTiposChequeos = new ArrayList<TiposChequeos>();
        permitirIndex = true;
        editarTipoChequeo = new TiposChequeos();
        nuevoTipoChequeo = new TiposChequeos();
        duplicarTipoChequeo = new TiposChequeos();
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposChequeos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposChequeos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposChequeos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposChequeos.asignarIndex \n");
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
            System.out.println("ERROR ControlTiposChequeos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoChequeo");
            bandera = 0;
            filtrarTiposChequeos = null;
            tipoLista = 0;
        }

        borrarTiposChequeos.clear();
        crearTiposChequeos.clear();
        modificarTiposChequeos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposChequeos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoChequeo");
        context.update("form:ACEPTAR");

    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosTipoChequeo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoChequeo");
            bandera = 0;
            filtrarTiposChequeos = null;
            tipoLista = 0;
        }
    }

    public void modificarTipoChequeo(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO CHEQUEO");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR CHEQUEO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposChequeos.contains(listTiposChequeos.get(indice))) {
                    if (listTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (listTiposChequeos.get(indice).getCodigo() == listTiposChequeos.get(j).getCodigo()) {
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
                    if (listTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposChequeos.isEmpty()) {
                            modificarTiposChequeos.add(listTiposChequeos.get(indice));
                        } else if (!modificarTiposChequeos.contains(listTiposChequeos.get(indice))) {
                            modificarTiposChequeos.add(listTiposChequeos.get(indice));
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

                if (!crearTiposChequeos.contains(filtrarTiposChequeos.get(indice))) {
                    if (filtrarTiposChequeos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposChequeos.size(); j++) {
                            if (j != indice) {
                                if (listTiposChequeos.get(indice).getCodigo() == listTiposChequeos.get(j).getCodigo()) {
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

                    if (filtrarTiposChequeos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarTiposChequeos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarTiposChequeos.isEmpty()) {
                            modificarTiposChequeos.add(filtrarTiposChequeos.get(indice));
                        } else if (!modificarTiposChequeos.contains(filtrarTiposChequeos.get(indice))) {
                            modificarTiposChequeos.add(filtrarTiposChequeos.get(indice));
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
            context.update("form:datosTipoChequeo");
            context.update("form:ACEPTAR");

        }

    }

    public void borrarandoTiposChequeo() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarandoTiposChequeo");
                if (!modificarTiposChequeos.isEmpty() && modificarTiposChequeos.contains(listTiposChequeos.get(index))) {
                    int modIndex = modificarTiposChequeos.indexOf(listTiposChequeos.get(index));
                    modificarTiposChequeos.remove(modIndex);
                    borrarTiposChequeos.add(listTiposChequeos.get(index));
                } else if (!crearTiposChequeos.isEmpty() && crearTiposChequeos.contains(listTiposChequeos.get(index))) {
                    int crearIndex = crearTiposChequeos.indexOf(listTiposChequeos.get(index));
                    crearTiposChequeos.remove(crearIndex);
                } else {
                    borrarTiposChequeos.add(listTiposChequeos.get(index));
                }
                listTiposChequeos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarandoTiposChequeo ");
                if (!modificarTiposChequeos.isEmpty() && modificarTiposChequeos.contains(filtrarTiposChequeos.get(index))) {
                    int modIndex = modificarTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                    modificarTiposChequeos.remove(modIndex);
                    borrarTiposChequeos.add(filtrarTiposChequeos.get(index));
                } else if (!crearTiposChequeos.isEmpty() && crearTiposChequeos.contains(filtrarTiposChequeos.get(index))) {
                    int crearIndex = crearTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                    crearTiposChequeos.remove(crearIndex);
                } else {
                    borrarTiposChequeos.add(filtrarTiposChequeos.get(index));
                }
                int VCIndex = listTiposChequeos.indexOf(filtrarTiposChequeos.get(index));
                listTiposChequeos.remove(VCIndex);
                filtrarTiposChequeos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoChequeo");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
        }

    }

    private BigInteger verificarChequeosMedicos;
    private BigInteger verificarTiposExamenesCargos;

    public void verificarBorrado() {
        System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
        try {
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + listTiposChequeos.get(index).getSecuencia());
                verificarChequeosMedicos = administrarTiposChequeos.verificarChequeosMedicos(listTiposChequeos.get(index).getSecuencia());
                verificarTiposExamenesCargos = administrarTiposChequeos.verificarTiposExamenesCargos(listTiposChequeos.get(index).getSecuencia());
            } else {
                verificarChequeosMedicos = administrarTiposChequeos.verificarChequeosMedicos(filtrarTiposChequeos.get(index).getSecuencia());
                verificarTiposExamenesCargos = administrarTiposChequeos.verificarTiposExamenesCargos(filtrarTiposChequeos.get(index).getSecuencia());
            }
            if (!verificarChequeosMedicos.equals(new BigInteger("0")) || !verificarTiposExamenesCargos.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarChequeosMedicos = new BigInteger("-1");
                verificarTiposExamenesCargos = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrarandoTiposChequeo();
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROLTIPOSCHEQUEOS verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposChequeos.isEmpty() || !crearTiposChequeos.isEmpty() || !modificarTiposChequeos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposChequeos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarTiposchequeos");
            if (!borrarTiposChequeos.isEmpty()) {
                for (int i = 0; i < borrarTiposChequeos.size(); i++) {
                    System.out.println("Borrando...");
                    administrarTiposChequeos.borrarTiposChequeos(borrarTiposChequeos.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarTiposChequeos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposChequeos.clear();
            }
            if (!crearTiposChequeos.isEmpty()) {
                for (int i = 0; i < crearTiposChequeos.size(); i++) {

                    System.out.println("Creando...");
                    administrarTiposChequeos.crearTiposChequeos(crearTiposChequeos.get(i));

                }
                crearTiposChequeos.clear();
            }
            if (!modificarTiposChequeos.isEmpty()) {
                administrarTiposChequeos.modificarTiposChequeos(modificarTiposChequeos);
                modificarTiposChequeos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposChequeos = null;
            context.update("form:datosTipoChequeo");
            k = 0;
            guardado = true;
        }
        index = -1;
        context.update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoChequeo = listTiposChequeos.get(index);
            }
            if (tipoLista == 1) {
                editarTipoChequeo = filtrarTiposChequeos.get(index);
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

    public void agregarNuevoTiposChequeos() {
        System.out.println("agregarNuevoTiposChequeos");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoChequeo.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoChequeo.getCodigo());

            for (int x = 0; x < listTiposChequeos.size(); x++) {
                if (listTiposChequeos.get(x).getCodigo() == nuevoTipoChequeo.getCodigo()) {
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
        if (nuevoTipoChequeo.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoChequeo");
                bandera = 0;
                filtrarTiposChequeos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoChequeo.setSecuencia(l);

            crearTiposChequeos.add(nuevoTipoChequeo);

            listTiposChequeos.add(nuevoTipoChequeo);
            nuevoTipoChequeo = new TiposChequeos();
            context.update("form:datosTipoChequeo");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposChequeo.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposChequeos() {
        System.out.println("limpiarNuevoTiposChequeos");
        nuevoTipoChequeo = new TiposChequeos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposChequeos() {
        System.out.println("duplicandoTiposChequeo");
        if (index >= 0) {
            duplicarTipoChequeo = new TiposChequeos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoChequeo.setSecuencia(l);
                duplicarTipoChequeo.setCodigo(listTiposChequeos.get(index).getCodigo());
                duplicarTipoChequeo.setDescripcion(listTiposChequeos.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoChequeo.setSecuencia(l);
                duplicarTipoChequeo.setCodigo(filtrarTiposChequeos.get(index).getCodigo());
                duplicarTipoChequeo.setDescripcion(filtrarTiposChequeos.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTC");
            context.execute("duplicarRegistroTiposChequeo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS CHEQUEO");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoChequeo.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarTipoChequeo.getDescripcion());

        if (duplicarTipoChequeo.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposChequeos.size(); x++) {
                if (listTiposChequeos.get(x).getCodigo() == duplicarTipoChequeo.getCodigo()) {
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
        if (duplicarTipoChequeo.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoChequeo.getSecuencia() + "  " + duplicarTipoChequeo.getCodigo());
            if (crearTiposChequeos.contains(duplicarTipoChequeo)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposChequeos.add(duplicarTipoChequeo);
            crearTiposChequeos.add(duplicarTipoChequeo);
            context.update("form:datosTipoChequeo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoChequeo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoChequeo");
                bandera = 0;
                filtrarTiposChequeos = null;
                tipoLista = 0;
            }
            duplicarTipoChequeo = new TiposChequeos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposChequeo.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposChequeos() {
        duplicarTipoChequeo = new TiposChequeos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoChequeoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSCHEQUEOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoChequeoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSCHEQUEOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposChequeos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCHEQUEOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCHEQUEOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<TiposChequeos> getListTiposChequeos() {
        if (listTiposChequeos == null) {
            listTiposChequeos = administrarTiposChequeos.mostrarTiposChequeos();
        }
        return listTiposChequeos;
    }

    public void setListTiposChequeos(List<TiposChequeos> listTiposChequeos) {
        this.listTiposChequeos = listTiposChequeos;
    }

    public List<TiposChequeos> getFiltrarTiposChequeos() {
        return filtrarTiposChequeos;
    }

    public void setFiltrarTiposChequeos(List<TiposChequeos> filtrarTiposChequeos) {
        this.filtrarTiposChequeos = filtrarTiposChequeos;
    }

    public TiposChequeos getNuevoTipoChequeo() {
        return nuevoTipoChequeo;
    }

    public void setNuevoTipoChequeo(TiposChequeos nuevoTipoChequeo) {
        this.nuevoTipoChequeo = nuevoTipoChequeo;
    }

    public TiposChequeos getDuplicarTipoChequeo() {
        return duplicarTipoChequeo;
    }

    public void setDuplicarTipoChequeo(TiposChequeos duplicarTipoChequeo) {
        this.duplicarTipoChequeo = duplicarTipoChequeo;
    }

    public TiposChequeos getEditarTipoChequeo() {
        return editarTipoChequeo;
    }

    public void setEditarTipoChequeo(TiposChequeos editarTipoChequeo) {
        this.editarTipoChequeo = editarTipoChequeo;
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

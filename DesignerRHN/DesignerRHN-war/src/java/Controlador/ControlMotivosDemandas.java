/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MotivosDemandas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosDemandasInterface;
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
public class ControlMotivosDemandas implements Serializable {

    @EJB
    AdministrarMotivosDemandasInterface administrarMotivosDemandas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<MotivosDemandas> listMotivosDemandas;
    private List<MotivosDemandas> filtrarMotivosDemandas;
    private List<MotivosDemandas> crearMotivosDemandas;
    private List<MotivosDemandas> modificarMotivosDemandas;
    private List<MotivosDemandas> borrarMotivosDemandas;
    private MotivosDemandas nuevoMotivoDemanda;
    private MotivosDemandas duplicarMotivoDemanda;
    private MotivosDemandas editarMotivoDemanda;
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
    private BigInteger demandas;
    private Integer a;

    public ControlMotivosDemandas() {
        listMotivosDemandas = null;
        crearMotivosDemandas = new ArrayList<MotivosDemandas>();
        modificarMotivosDemandas = new ArrayList<MotivosDemandas>();
        borrarMotivosDemandas = new ArrayList<MotivosDemandas>();
        permitirIndex = true;
        editarMotivoDemanda = new MotivosDemandas();
        nuevoMotivoDemanda = new MotivosDemandas();
        duplicarMotivoDemanda = new MotivosDemandas();
        a = null;
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMotivosDemandas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMotivosDemandas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMotivosDemandas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMotivosDemandas.asignarIndex \n");
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
            System.out.println("ERROR ControlMotivosDemandas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoDemanda");
            bandera = 0;
            filtrarMotivosDemandas = null;
            tipoLista = 0;
        }

        borrarMotivosDemandas.clear();
        crearMotivosDemandas.clear();
        modificarMotivosDemandas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosDemandas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoDemanda");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosMotivoDemanda");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoDemanda");
            bandera = 0;
            filtrarMotivosDemandas = null;
            tipoLista = 0;
        }
    }

    public void modificarMotivoDemanda(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVODEMANDA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        ;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVODEMANDA, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMotivosDemandas.contains(listMotivosDemandas.get(indice))) {
                    if (listMotivosDemandas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosDemandas.size(); j++) {
                            if (j != indice) {
                                if (listMotivosDemandas.get(indice).getCodigo() == listMotivosDemandas.get(j).getCodigo()) {
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
                    if (listMotivosDemandas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMotivosDemandas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivosDemandas.isEmpty()) {
                            modificarMotivosDemandas.add(listMotivosDemandas.get(indice));
                        } else if (!modificarMotivosDemandas.contains(listMotivosDemandas.get(indice))) {
                            modificarMotivosDemandas.add(listMotivosDemandas.get(indice));
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

                if (!crearMotivosDemandas.contains(filtrarMotivosDemandas.get(indice))) {
                    if (filtrarMotivosDemandas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosDemandas.size(); j++) {
                            if (j != indice) {
                                if (listMotivosDemandas.get(indice).getCodigo() == listMotivosDemandas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarMotivosDemandas.size(); j++) {
                            if (j != indice) {
                                if (filtrarMotivosDemandas.get(indice).getCodigo() == filtrarMotivosDemandas.get(j).getCodigo()) {
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

                    if (filtrarMotivosDemandas.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMotivosDemandas.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivosDemandas.isEmpty()) {
                            modificarMotivosDemandas.add(filtrarMotivosDemandas.get(indice));
                        } else if (!modificarMotivosDemandas.contains(filtrarMotivosDemandas.get(indice))) {
                            modificarMotivosDemandas.add(filtrarMotivosDemandas.get(indice));
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
            context.update("form:datosMotivoDemanda");
            context.update("form:ACEPTAR");

        }

    }

    public void borrarMotivoDemanda() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarMotivoDemanda");
                if (!modificarMotivosDemandas.isEmpty() && modificarMotivosDemandas.contains(listMotivosDemandas.get(index))) {
                    int modIndex = modificarMotivosDemandas.indexOf(listMotivosDemandas.get(index));
                    modificarMotivosDemandas.remove(modIndex);
                    borrarMotivosDemandas.add(listMotivosDemandas.get(index));
                } else if (!crearMotivosDemandas.isEmpty() && crearMotivosDemandas.contains(listMotivosDemandas.get(index))) {
                    int crearIndex = crearMotivosDemandas.indexOf(listMotivosDemandas.get(index));
                    crearMotivosDemandas.remove(crearIndex);
                } else {
                    borrarMotivosDemandas.add(listMotivosDemandas.get(index));
                }
                listMotivosDemandas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMotivosDemanda ");
                if (!modificarMotivosDemandas.isEmpty() && modificarMotivosDemandas.contains(filtrarMotivosDemandas.get(index))) {
                    int modIndex = modificarMotivosDemandas.indexOf(filtrarMotivosDemandas.get(index));
                    modificarMotivosDemandas.remove(modIndex);
                    borrarMotivosDemandas.add(filtrarMotivosDemandas.get(index));
                } else if (!crearMotivosDemandas.isEmpty() && crearMotivosDemandas.contains(filtrarMotivosDemandas.get(index))) {
                    int crearIndex = crearMotivosDemandas.indexOf(filtrarMotivosDemandas.get(index));
                    crearMotivosDemandas.remove(crearIndex);
                } else {
                    borrarMotivosDemandas.add(filtrarMotivosDemandas.get(index));
                }
                int VCIndex = listMotivosDemandas.indexOf(filtrarMotivosDemandas.get(index));
                listMotivosDemandas.remove(VCIndex);
                filtrarMotivosDemandas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMotivoDemanda");
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
            System.err.println("Control Secuencia de MotivosDemandas a borrar");
            if (tipoLista == 0) {
                demandas = administrarMotivosDemandas.contarDemandasMotivoDemanda(listMotivosDemandas.get(index).getSecuencia());
            } else {
                demandas = administrarMotivosDemandas.contarDemandasMotivoDemanda(filtrarMotivosDemandas.get(index).getSecuencia());
            }
            if (demandas.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarMotivoDemanda();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                demandas = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlMotivosDemandas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarMotivosDemandas.isEmpty() || !crearMotivosDemandas.isEmpty() || !modificarMotivosDemandas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarMotivoDemanda() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando MotivoDemanda");
            if (!borrarMotivosDemandas.isEmpty()) {
 administrarMotivosDemandas.borrarMotivosDemandas(borrarMotivosDemandas);
                
                //mostrarBorrados
                registrosBorrados = borrarMotivosDemandas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivosDemandas.clear();
            }
            if (!crearMotivosDemandas.isEmpty()) {
 administrarMotivosDemandas.crearMotivosDemandas(crearMotivosDemandas);

                
                crearMotivosDemandas.clear();
            }
            if (!modificarMotivosDemandas.isEmpty()) {
                administrarMotivosDemandas.modificarMotivosDemandas(modificarMotivosDemandas);
                modificarMotivosDemandas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosDemandas = null;
            context.update("form:datosMotivoDemanda");
            k = 0;
            guardado = true;
        }
        index = -1;
        context.update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMotivoDemanda = listMotivosDemandas.get(index);
            }
            if (tipoLista == 1) {
                editarMotivoDemanda = filtrarMotivosDemandas.get(index);
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

    public void agregarNuevoMotivoDemanda() {
        System.out.println("Agregar MotivosDemandas");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoDemanda.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoDemanda.getCodigo());

            for (int x = 0; x < listMotivosDemandas.size(); x++) {
                if (listMotivosDemandas.get(x).getCodigo() == nuevoMotivoDemanda.getCodigo()) {
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
        if (nuevoMotivoDemanda.getDescripcion() == (null) || nuevoMotivoDemanda.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoDemanda");
                bandera = 0;
                filtrarMotivosDemandas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoDemanda.setSecuencia(l);

            crearMotivosDemandas.add(nuevoMotivoDemanda);

            listMotivosDemandas.add(nuevoMotivoDemanda);
            nuevoMotivoDemanda = new MotivosDemandas();

            context.update("form:datosMotivoDemanda");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroMotivosDemandas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivoDemanda() {
        System.out.println("limpiarNuevoMotivoDemanda");
        nuevoMotivoDemanda = new MotivosDemandas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarMotivosDemandas() {
        System.out.println("duplicarMotivosDemandas");
        if (index >= 0) {
            duplicarMotivoDemanda = new MotivosDemandas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoDemanda.setSecuencia(l);
                duplicarMotivoDemanda.setCodigo(listMotivosDemandas.get(index).getCodigo());
                duplicarMotivoDemanda.setDescripcion(listMotivosDemandas.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarMotivoDemanda.setSecuencia(l);
                duplicarMotivoDemanda.setCodigo(filtrarMotivosDemandas.get(index).getCodigo());
                duplicarMotivoDemanda.setDescripcion(filtrarMotivosDemandas.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMD");
            context.execute("duplicarRegistroMotivoDemanda.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR MotivosDemandas");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivoDemanda.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarMotivoDemanda.getDescripcion());

        if (duplicarMotivoDemanda.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosDemandas.size(); x++) {
                if (listMotivosDemandas.get(x).getCodigo() == duplicarMotivoDemanda.getCodigo()) {
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
        if (duplicarMotivoDemanda.getDescripcion() == null || duplicarMotivoDemanda.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoDemanda.getSecuencia() + "  " + duplicarMotivoDemanda.getCodigo());
            if (crearMotivosDemandas.contains(duplicarMotivoDemanda)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosDemandas.add(duplicarMotivoDemanda);
            crearMotivosDemandas.add(duplicarMotivoDemanda);
            context.update("form:datosMotivoDemanda");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoDemanda:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoDemanda");
                bandera = 0;
                filtrarMotivosDemandas = null;
                tipoLista = 0;
            }
            duplicarMotivoDemanda = new MotivosDemandas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivoDemanda.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarMotivosDemandas() {
        duplicarMotivoDemanda = new MotivosDemandas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoDemandaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MOTIVOSDEMANDAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoDemandaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MOTIVOSDEMANDAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosDemandas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSDEMANDAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSDEMANDAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
//--------///////////////////////---------------------*****//*/*/*/*/*/-****----

    public List<MotivosDemandas> getListMotivosDemandas() {
        if (listMotivosDemandas == null) {
            listMotivosDemandas = administrarMotivosDemandas.consultarMotivosDemandas();
        }
        return listMotivosDemandas;
    }

    public void setListMotivosDemandas(List<MotivosDemandas> listMotivosDemandas) {
        this.listMotivosDemandas = listMotivosDemandas;
    }

    public List<MotivosDemandas> getFiltrarMotivosDemandas() {
        return filtrarMotivosDemandas;
    }

    public void setFiltrarMotivosDemandas(List<MotivosDemandas> filtrarMotivosDemandas) {
        this.filtrarMotivosDemandas = filtrarMotivosDemandas;
    }

    public MotivosDemandas getNuevoMotivoDemanda() {
        return nuevoMotivoDemanda;
    }

    public void setNuevoMotivoDemanda(MotivosDemandas nuevoMotivoDemanda) {
        this.nuevoMotivoDemanda = nuevoMotivoDemanda;
    }

    public MotivosDemandas getDuplicarMotivoDemanda() {
        return duplicarMotivoDemanda;
    }

    public void setDuplicarMotivoDemanda(MotivosDemandas duplicarMotivoDemanda) {
        this.duplicarMotivoDemanda = duplicarMotivoDemanda;
    }

    public MotivosDemandas getEditarMotivoDemanda() {
        return editarMotivoDemanda;
    }

    public void setEditarMotivoDemanda(MotivosDemandas editarMotivoDemanda) {
        this.editarMotivoDemanda = editarMotivoDemanda;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

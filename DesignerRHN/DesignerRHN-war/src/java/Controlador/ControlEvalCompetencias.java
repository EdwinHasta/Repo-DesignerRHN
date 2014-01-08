/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.EvalCompetencias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarEvalCompetenciasInterface;
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
public class ControlEvalCompetencias implements Serializable {

    @EJB
    AdministrarEvalCompetenciasInterface administrarEvalCompetencias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<EvalCompetencias> listEvalCompetencias;
    private List<EvalCompetencias> filtrarEvalCompetencias;
    private List<EvalCompetencias> crearEvalCompetencias;
    private List<EvalCompetencias> modificarEvalCompetencias;
    private List<EvalCompetencias> borrarEvalCompetencias;
    private EvalCompetencias nuevoEvalCompetencia;
    private EvalCompetencias duplicarEvalCompetencia;
    private EvalCompetencias editarEvalCompetencia;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, descripcionCompetencia;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger competenciasCargos;
    private Integer a;

    public ControlEvalCompetencias() {
        listEvalCompetencias = null;
        crearEvalCompetencias = new ArrayList<EvalCompetencias>();
        modificarEvalCompetencias = new ArrayList<EvalCompetencias>();
        borrarEvalCompetencias = new ArrayList<EvalCompetencias>();
        permitirIndex = true;
        guardado = true;
        editarEvalCompetencia = new EvalCompetencias();
        nuevoEvalCompetencia = new EvalCompetencias();
        duplicarEvalCompetencia = new EvalCompetencias();
        a = null;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlEvalCompetencias.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlEvalCompetencias eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEvalCompetencias.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlEvalCompetencias.asignarIndex \n");
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
            System.out.println("ERROR ControlEvalCompetencias.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            descripcionCompetencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcionCompetencia");
            descripcionCompetencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalCompetencia");
            bandera = 0;
            filtrarEvalCompetencias = null;
            tipoLista = 0;
        }

        borrarEvalCompetencias.clear();
        crearEvalCompetencias.clear();
        modificarEvalCompetencias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEvalCompetencias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEvalCompetencia");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:codigo");
            codigo.setFilterStyle("width: 200px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcion");
            descripcion.setFilterStyle("width: 270px");
            descripcionCompetencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcionCompetencia");
            descripcionCompetencia.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosEvalCompetencia");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            descripcionCompetencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcionCompetencia");
            descripcionCompetencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalCompetencia");
            bandera = 0;
            filtrarEvalCompetencias = null;
            tipoLista = 0;
        }
    }

    public void modificarEvalCompetencia(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EVAL COMPETENCIAS");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EVALCOMPETENCIAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearEvalCompetencias.contains(listEvalCompetencias.get(indice))) {
                    if (listEvalCompetencias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEvalCompetencias.size(); j++) {
                            if (j != indice) {
                                if (listEvalCompetencias.get(indice).getCodigo() == listEvalCompetencias.get(j).getCodigo()) {
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
                    if (listEvalCompetencias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEvalCompetencias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEvalCompetencias.isEmpty()) {
                            modificarEvalCompetencias.add(listEvalCompetencias.get(indice));
                        } else if (!modificarEvalCompetencias.contains(listEvalCompetencias.get(indice))) {
                            modificarEvalCompetencias.add(listEvalCompetencias.get(indice));
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

                if (!crearEvalCompetencias.contains(filtrarEvalCompetencias.get(indice))) {
                    if (filtrarEvalCompetencias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEvalCompetencias.size(); j++) {
                            if (j != indice) {
                                if (listEvalCompetencias.get(indice).getCodigo() == listEvalCompetencias.get(j).getCodigo()) {
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
                        context.update("form:ACEPTAR");
                    }

                    if (filtrarEvalCompetencias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEvalCompetencias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEvalCompetencias.isEmpty()) {
                            modificarEvalCompetencias.add(filtrarEvalCompetencias.get(indice));
                        } else if (!modificarEvalCompetencias.contains(filtrarEvalCompetencias.get(indice))) {
                            modificarEvalCompetencias.add(filtrarEvalCompetencias.get(indice));
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
            context.update("form:datosEvalCompetencia");
        }

    }

    public void borrandoEvalCompetencias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoEvalCompetencias");
                if (!modificarEvalCompetencias.isEmpty() && modificarEvalCompetencias.contains(listEvalCompetencias.get(index))) {
                    int modIndex = modificarEvalCompetencias.indexOf(listEvalCompetencias.get(index));
                    modificarEvalCompetencias.remove(modIndex);
                    borrarEvalCompetencias.add(listEvalCompetencias.get(index));
                } else if (!crearEvalCompetencias.isEmpty() && crearEvalCompetencias.contains(listEvalCompetencias.get(index))) {
                    int crearIndex = crearEvalCompetencias.indexOf(listEvalCompetencias.get(index));
                    crearEvalCompetencias.remove(crearIndex);
                } else {
                    borrarEvalCompetencias.add(listEvalCompetencias.get(index));
                }
                listEvalCompetencias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoEvalCompetencias ");
                if (!modificarEvalCompetencias.isEmpty() && modificarEvalCompetencias.contains(filtrarEvalCompetencias.get(index))) {
                    int modIndex = modificarEvalCompetencias.indexOf(filtrarEvalCompetencias.get(index));
                    modificarEvalCompetencias.remove(modIndex);
                    borrarEvalCompetencias.add(filtrarEvalCompetencias.get(index));
                } else if (!crearEvalCompetencias.isEmpty() && crearEvalCompetencias.contains(filtrarEvalCompetencias.get(index))) {
                    int crearIndex = crearEvalCompetencias.indexOf(filtrarEvalCompetencias.get(index));
                    crearEvalCompetencias.remove(crearIndex);
                } else {
                    borrarEvalCompetencias.add(filtrarEvalCompetencias.get(index));
                }
                int VCIndex = listEvalCompetencias.indexOf(filtrarEvalCompetencias.get(index));
                listEvalCompetencias.remove(VCIndex);
                filtrarEvalCompetencias.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEvalCompetencia");
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
            System.err.println("Control Secuencia de ControlEvalCompetencias ");
            competenciasCargos = administrarEvalCompetencias.verificarBorradoCompetenciasCargos(listEvalCompetencias.get(index).getSecuencia());

            if (competenciasCargos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoEvalCompetencias();
            } else {
                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                competenciasCargos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEvalCompetencias verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarEvalCompetencias.isEmpty() || !crearEvalCompetencias.isEmpty() || !modificarEvalCompetencias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEvalCompetencias() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarEvalCompetencias");
            if (!borrarEvalCompetencias.isEmpty()) {
                for (int i = 0; i < borrarEvalCompetencias.size(); i++) {
                    System.out.println("Borrando...");
                    administrarEvalCompetencias.borrarEvalCompetencias(borrarEvalCompetencias.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarEvalCompetencias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEvalCompetencias.clear();
            }
            if (!crearEvalCompetencias.isEmpty()) {
                for (int i = 0; i < crearEvalCompetencias.size(); i++) {

                    System.out.println("Creando...");
                    administrarEvalCompetencias.crearEvalCompetencias(crearEvalCompetencias.get(i));

                }
                crearEvalCompetencias.clear();
            }
            if (!modificarEvalCompetencias.isEmpty()) {
                administrarEvalCompetencias.modificarEvalCompetencias(modificarEvalCompetencias);
                modificarEvalCompetencias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEvalCompetencias = null;
            context.update("form:datosEvalCompetencia");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEvalCompetencia = listEvalCompetencias.get(index);
            }
            if (tipoLista == 1) {
                editarEvalCompetencia = filtrarEvalCompetencias.get(index);
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
                context.update("formularioDialogos:editDescripcionCompetencia");
                context.execute("editDescripcionCompetencia.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoEvalCompetencias() {
        System.out.println("agregarNuevoEvalCompetencias");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEvalCompetencia.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoEvalCompetencia.getCodigo());

            for (int x = 0; x < listEvalCompetencias.size(); x++) {
                if (listEvalCompetencias.get(x).getCodigo() == nuevoEvalCompetencia.getCodigo()) {
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
        if (nuevoEvalCompetencia.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                descripcionCompetencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcionCompetencia");
                descripcionCompetencia.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalCompetencia");
                bandera = 0;
                filtrarEvalCompetencias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoEvalCompetencia.setSecuencia(l);

            crearEvalCompetencias.add(nuevoEvalCompetencia);

            listEvalCompetencias.add(nuevoEvalCompetencia);
            nuevoEvalCompetencia = new EvalCompetencias();
            context.update("form:datosEvalCompetencia");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvalEmpresas.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEvalCompetencias() {
        System.out.println("limpiarNuevoEvalCompetencias");
        nuevoEvalCompetencia = new EvalCompetencias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoEvalCompetencias() {
        System.out.println("duplicandoEvalCompetencias");
        if (index >= 0) {
            duplicarEvalCompetencia = new EvalCompetencias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEvalCompetencia.setSecuencia(l);
                duplicarEvalCompetencia.setCodigo(listEvalCompetencias.get(index).getCodigo());
                duplicarEvalCompetencia.setDescripcion(listEvalCompetencias.get(index).getDescripcion());
                duplicarEvalCompetencia.setDesCompetencia(listEvalCompetencias.get(index).getDesCompetencia());
            }
            if (tipoLista == 1) {
                duplicarEvalCompetencia.setSecuencia(l);
                duplicarEvalCompetencia.setCodigo(filtrarEvalCompetencias.get(index).getCodigo());
                duplicarEvalCompetencia.setDescripcion(filtrarEvalCompetencias.get(index).getDescripcion());
                duplicarEvalCompetencia.setDesCompetencia(filtrarEvalCompetencias.get(index).getDesCompetencia());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvC");
            context.execute("duplicarRegistroEvalCompetencias.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR EVALCOMPETENCIAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        System.err.println("ConfirmarDuplicar codigo " + duplicarEvalCompetencia.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarEvalCompetencia.getDescripcion());

        if (duplicarEvalCompetencia.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listEvalCompetencias.size(); x++) {
                if (listEvalCompetencias.get(x).getCodigo() == duplicarEvalCompetencia.getCodigo()) {
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
        if (duplicarEvalCompetencia.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarEvalCompetencia.getSecuencia() + "  " + duplicarEvalCompetencia.getCodigo());
            if (crearEvalCompetencias.contains(duplicarEvalCompetencia)) {
                System.out.println("Ya lo contengo.");
            }
            listEvalCompetencias.add(duplicarEvalCompetencia);
            crearEvalCompetencias.add(duplicarEvalCompetencia);
            context.update("form:datosEvalCompetencia");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                descripcionCompetencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalCompetencia:descripcionCompetencia");
                descripcionCompetencia.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalCompetencia");
                bandera = 0;
                filtrarEvalCompetencias = null;
                tipoLista = 0;
            }
            duplicarEvalCompetencia = new EvalCompetencias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalCompetencias.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarEvalCompetencias() {
        duplicarEvalCompetencia = new EvalCompetencias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalCompetenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EVALCOMPETENCIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalCompetenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EVALCOMPETENCIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEvalCompetencias.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EVALCOMPETENCIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("EVALCOMPETENCIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<EvalCompetencias> getListEvalCompetencias() {
        if (listEvalCompetencias == null) {
            listEvalCompetencias = administrarEvalCompetencias.mostrarEvalCompetencias();
        }
        return listEvalCompetencias;
    }

    public void setListEvalCompetencias(List<EvalCompetencias> listEvalCompetencias) {
        this.listEvalCompetencias = listEvalCompetencias;
    }

    public List<EvalCompetencias> getFiltrarEvalCompetencias() {
        return filtrarEvalCompetencias;
    }

    public void setFiltrarEvalCompetencias(List<EvalCompetencias> filtrarEvalCompetencias) {
        this.filtrarEvalCompetencias = filtrarEvalCompetencias;
    }

    public EvalCompetencias getNuevoEvalCompetencia() {
        return nuevoEvalCompetencia;
    }

    public void setNuevoEvalCompetencia(EvalCompetencias nuevoEvalCompetencia) {
        this.nuevoEvalCompetencia = nuevoEvalCompetencia;
    }

    public EvalCompetencias getDuplicarEvalCompetencia() {
        return duplicarEvalCompetencia;
    }

    public void setDuplicarEvalCompetencia(EvalCompetencias duplicarEvalCompetencia) {
        this.duplicarEvalCompetencia = duplicarEvalCompetencia;
    }

    public EvalCompetencias getEditarEvalCompetencia() {
        return editarEvalCompetencia;
    }

    public void setEditarEvalCompetencia(EvalCompetencias editarEvalCompetencia) {
        this.editarEvalCompetencia = editarEvalCompetencia;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.EvalDimensiones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEvalDimensionesInterface;
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
public class ControlEvalDimensiones implements Serializable {

    
    @EJB
    AdministrarEvalDimensionesInterface administrarEvalDimensiones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<EvalDimensiones> listEvalDimensiones;
    private List<EvalDimensiones> filtrarEvalDimensiones;
    private List<EvalDimensiones> crearEvalDimensiones;
    private List<EvalDimensiones> modificarEvalDimensiones;
    private List<EvalDimensiones> borrarEvalDimensiones;
    private EvalDimensiones nuevoEvalDimension;
    private EvalDimensiones duplicarEvalDimension;
    private EvalDimensiones editarEvalDimension;
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

    public ControlEvalDimensiones() {

        listEvalDimensiones = null;
        crearEvalDimensiones = new ArrayList<EvalDimensiones>();
        modificarEvalDimensiones = new ArrayList<EvalDimensiones>();
        borrarEvalDimensiones = new ArrayList<EvalDimensiones>();
        permitirIndex = true;
        guardado = true;
        editarEvalDimension = new EvalDimensiones();
        nuevoEvalDimension = new EvalDimensiones();
        duplicarEvalDimension = new EvalDimensiones();
    }
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEvalDimensiones.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMotivosLocalizaciones.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMotivosLocalizaciones eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEvalDimensiones.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlEvalDimensiones.asignarIndex \n");
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlEvalDimensiones.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalDimension");
            bandera = 0;
            filtrarEvalDimensiones = null;
            tipoLista = 0;
        }

        borrarEvalDimensiones.clear();
        crearEvalDimensiones.clear();
        modificarEvalDimensiones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEvalDimensiones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEvalDimension");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosEvalDimension");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvalDimension");
            bandera = 0;
            filtrarEvalDimensiones = null;
            tipoLista = 0;
        }
    }

    public void modificarEvalDimensiones(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR EVALDIMENSIONES");
        index = indice;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVOS LOCALIZACIONES, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearEvalDimensiones.contains(listEvalDimensiones.get(indice))) {
                    if (listEvalDimensiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        System.err.println("tamano listEvalDimensiones :" + listEvalDimensiones.size());
                        for (int j = 0; j < listEvalDimensiones.size(); j++) {
                            if (j != indice) {
                                System.err.println("J!=Indice");
                                if (listEvalDimensiones.get(indice).getCodigo().equals(listEvalDimensiones.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }

                        System.err.println("Contador == " + contador);
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }
                    if (listEvalDimensiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEvalDimensiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (banderita == true) {
                        if (modificarEvalDimensiones.isEmpty()) {
                            modificarEvalDimensiones.add(listEvalDimensiones.get(indice));
                        } else if (!modificarEvalDimensiones.contains(listEvalDimensiones.get(indice))) {
                            modificarEvalDimensiones.add(listEvalDimensiones.get(indice));
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

                if (!crearEvalDimensiones.contains(filtrarEvalDimensiones.get(indice))) {
                    if (filtrarEvalDimensiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEvalDimensiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalDimensiones.get(indice).getCodigo() == listEvalDimensiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarEvalDimensiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarEvalDimensiones.get(indice).getCodigo() == filtrarEvalDimensiones.get(j).getCodigo()) {
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
                    if (filtrarEvalDimensiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEvalDimensiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEvalDimensiones.isEmpty()) {
                            modificarEvalDimensiones.add(filtrarEvalDimensiones.get(indice));
                        } else if (!modificarEvalDimensiones.contains(filtrarEvalDimensiones.get(indice))) {
                            modificarEvalDimensiones.add(filtrarEvalDimensiones.get(indice));
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
            context.update("form:datosMotivoCambioCargo");
            context.update("form:ACEPTAR");
        }

    }

    private BigInteger verificarTablasAuxilios;

    public void verificarBorrado() {
        try {
            System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
            System.out.println("secuencia borrado : " + listEvalDimensiones.get(index).getSecuencia());
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + listEvalDimensiones.get(index).getSecuencia());
                verificarTablasAuxilios = administrarEvalDimensiones.verificarEvalPlanillas(listEvalDimensiones.get(index).getSecuencia());
            } else {
                System.out.println("secuencia borrado : " + filtrarEvalDimensiones.get(index).getSecuencia());
                verificarTablasAuxilios = administrarEvalDimensiones.verificarEvalPlanillas(filtrarEvalDimensiones.get(index).getSecuencia());
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
                borrandoEvalDimensiones();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void borrandoEvalDimensiones() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrardatosEvalDimensiones");
                if (!modificarEvalDimensiones.isEmpty() && modificarEvalDimensiones.contains(listEvalDimensiones.get(index))) {
                    int modIndex = modificarEvalDimensiones.indexOf(listEvalDimensiones.get(index));
                    modificarEvalDimensiones.remove(modIndex);
                    borrarEvalDimensiones.add(listEvalDimensiones.get(index));
                } else if (!crearEvalDimensiones.isEmpty() && crearEvalDimensiones.contains(listEvalDimensiones.get(index))) {
                    int crearIndex = crearEvalDimensiones.indexOf(listEvalDimensiones.get(index));
                    crearEvalDimensiones.remove(crearIndex);
                } else {
                    borrarEvalDimensiones.add(listEvalDimensiones.get(index));
                }
                listEvalDimensiones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrardatosEvalDimensiones ");
                if (!modificarEvalDimensiones.isEmpty() && modificarEvalDimensiones.contains(filtrarEvalDimensiones.get(index))) {
                    int modIndex = modificarEvalDimensiones.indexOf(filtrarEvalDimensiones.get(index));
                    modificarEvalDimensiones.remove(modIndex);
                    borrarEvalDimensiones.add(filtrarEvalDimensiones.get(index));
                } else if (!crearEvalDimensiones.isEmpty() && crearEvalDimensiones.contains(filtrarEvalDimensiones.get(index))) {
                    int crearIndex = crearEvalDimensiones.indexOf(filtrarEvalDimensiones.get(index));
                    crearEvalDimensiones.remove(crearIndex);
                } else {
                    borrarEvalDimensiones.add(filtrarEvalDimensiones.get(index));
                }
                int VCIndex = listEvalDimensiones.indexOf(filtrarEvalDimensiones.get(index));
                listEvalDimensiones.remove(VCIndex);
                filtrarEvalDimensiones.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEvalDimension");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    /*public void verificarBorrado() {
     System.out.println("Estoy en verificarBorrado");
     try {
     borradoVC = administrarMotivosCambiosCargos.verificarBorradoVC(listMotivosLocalizaciones.get(index).getSecuencia());
     if (borradoVC.intValue() == 0) {
     System.out.println("Borrado==0");
     borrarMotivosCambiosCargos();
     }
     if (borradoVC.intValue() != 0) {
     System.out.println("Borrado>0");

     RequestContext context = RequestContext.getCurrentInstance();
     context.update("form:validacionBorrar");
     context.execute("validacionBorrar.show()");
     index = -1;
     borradoVC = new Long(-1);
     }

     } catch (Exception e) {
     System.err.println("ERROR ControlTiposEntidades verificarBorrado ERROR " + e);
     }
     }*/
    public void guardarEvalDimensiones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones EvalDimensiones");
            if (!borrarEvalDimensiones.isEmpty()) {
                administrarEvalDimensiones.borrarEvalDimensiones(borrarEvalDimensiones);
                //mostrarBorrados
                registrosBorrados = borrarEvalDimensiones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEvalDimensiones.clear();
            }
            if (!crearEvalDimensiones.isEmpty()) {
                administrarEvalDimensiones.crearEvalDimensiones(crearEvalDimensiones);
                crearEvalDimensiones.clear();
            }
            if (!modificarEvalDimensiones.isEmpty()) {
                administrarEvalDimensiones.modificarEvalDimensiones(modificarEvalDimensiones);
                modificarEvalDimensiones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEvalDimensiones = null;
            context.update("form:datosEvalDimension");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEvalDimension = listEvalDimensiones.get(index);
            }
            if (tipoLista == 1) {
                editarEvalDimension = filtrarEvalDimensiones.get(index);
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

    public void agregarNuevoEvalDimension() {
        System.out.println("Agregar EvalDimensiones");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEvalDimension.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoEvalDimension.getCodigo());

            for (int x = 0; x < listEvalDimensiones.size(); x++) {
                if (listEvalDimensiones.get(x).getCodigo().equals(nuevoEvalDimension.getCodigo())) {
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
        if (nuevoEvalDimension.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Una  Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalDimension");
                bandera = 0;
                filtrarEvalDimensiones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoEvalDimension.setSecuencia(l);

            crearEvalDimensiones.add(nuevoEvalDimension);

            listEvalDimensiones.add(nuevoEvalDimension);
            nuevoEvalDimension = new EvalDimensiones();

            context.update("form:datosEvalDimension");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            System.out.println("Despues de la bandera guardado");

            context.execute("nuevoRegistroEvalDimensiones.hide()");
            index = -1;
            secRegistro = null;
            System.out.println("Despues de nuevoRegistroEvalDimensiones");

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEvalDimension() {
        System.out.println("limpiarnuevoEvalDimensiones");
        nuevoEvalDimension = new EvalDimensiones();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarEvalDimensiones() {
        System.out.println("duplicarEvalDimensiones");
        if (index >= 0) {
            duplicarEvalDimension = new EvalDimensiones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEvalDimension.setSecuencia(l);
                duplicarEvalDimension.setCodigo(listEvalDimensiones.get(index).getCodigo());
                duplicarEvalDimension.setDescripcion(listEvalDimensiones.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarEvalDimension.setSecuencia(l);
                duplicarEvalDimension.setCodigo(filtrarEvalDimensiones.get(index).getCodigo());
                duplicarEvalDimension.setDescripcion(filtrarEvalDimensiones.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEvalsDimensiones");
            context.execute("duplicarRegistroEvalDimensiones.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR EvalDimensiones");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarEvalDimension.getCodigo());
        System.err.println("ConfirmarDuplicar descripcion " + duplicarEvalDimension.getDescripcion());

        if (duplicarEvalDimension.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listEvalDimensiones.size(); x++) {
                if (listEvalDimensiones.get(x).getCodigo().equals(duplicarEvalDimension.getCodigo())) {
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
        if (duplicarEvalDimension.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarEvalDimension.getSecuencia() + "  " + duplicarEvalDimension.getCodigo());
            if (crearEvalDimensiones.contains(duplicarEvalDimension)) {
                System.out.println("Ya lo contengo.");
            }
            listEvalDimensiones.add(duplicarEvalDimension);
            crearEvalDimensiones.add(duplicarEvalDimension);
            context.update("form:datosEvalDimension");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvalDimension:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvalDimension");
                bandera = 0;
                filtrarEvalDimensiones = null;
                tipoLista = 0;
            }
            duplicarEvalDimension = new EvalDimensiones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvalDimensiones.hide()");

            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarEvalDimensiones() {
        duplicarEvalDimension = new EvalDimensiones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalDimensionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Dimensiones", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEvalDimensionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Dimensiones", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEvalDimensiones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EVALDIMENSIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("EVALDIMENSIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //------*************/////////////////*****************----------*/*/*/-*/-*

    public List<EvalDimensiones> getListEvalDimensiones() {
        if (listEvalDimensiones == null) {
            listEvalDimensiones = administrarEvalDimensiones.consultarEvalDimensiones();
        }
        return listEvalDimensiones;
    }

    public void setListEvalDimensiones(List<EvalDimensiones> listEvalDimensiones) {
        this.listEvalDimensiones = listEvalDimensiones;
    }

    public List<EvalDimensiones> getFiltrarEvalDimensiones() {
        return filtrarEvalDimensiones;
    }

    public void setFiltrarEvalDimensiones(List<EvalDimensiones> filtrarEvalDimensiones) {
        this.filtrarEvalDimensiones = filtrarEvalDimensiones;
    }

    public EvalDimensiones getNuevoEvalDimension() {
        return nuevoEvalDimension;
    }

    public void setNuevoEvalDimension(EvalDimensiones nuevoEvalDimension) {
        this.nuevoEvalDimension = nuevoEvalDimension;
    }

    public EvalDimensiones getDuplicarEvalDimension() {
        return duplicarEvalDimension;
    }

    public void setDuplicarEvalDimension(EvalDimensiones duplicarEvalDimension) {
        this.duplicarEvalDimension = duplicarEvalDimension;
    }

    public EvalDimensiones getEditarEvalDimension() {
        return editarEvalDimension;
    }

    public void setEditarEvalDimension(EvalDimensiones editarEvalDimension) {
        this.editarEvalDimension = editarEvalDimension;
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

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

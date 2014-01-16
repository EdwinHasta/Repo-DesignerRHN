/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MotivosLocalizaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosLocalizacionesInterface;
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
public class ControlMotivosLocalizaciones implements Serializable {

    @EJB
    AdministrarMotivosLocalizacionesInterface administrarMotivosLocalizacion;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<MotivosLocalizaciones> listMotivosLocalizaciones;
    private List<MotivosLocalizaciones> filtrarMotivosLocalizaciones;
    private List<MotivosLocalizaciones> crearMotivosLocalizaciones;
    private List<MotivosLocalizaciones> modificarMotivosLocalizaciones;
    private List<MotivosLocalizaciones> borrarMotivosLocalizaciones;
    private MotivosLocalizaciones nuevoMotivoLocalizacion;
    private MotivosLocalizaciones duplicarMotivoLocalizacion;
    private MotivosLocalizaciones editarMotivoLocalizacion;
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
    private Long borradoVC;

    public ControlMotivosLocalizaciones() {

        listMotivosLocalizaciones = null;
        crearMotivosLocalizaciones = new ArrayList<MotivosLocalizaciones>();
        modificarMotivosLocalizaciones = new ArrayList<MotivosLocalizaciones>();
        borrarMotivosLocalizaciones = new ArrayList<MotivosLocalizaciones>();
        permitirIndex = true;
        editarMotivoLocalizacion = new MotivosLocalizaciones();
        nuevoMotivoLocalizacion = new MotivosLocalizaciones();
        duplicarMotivoLocalizacion = new MotivosLocalizaciones();
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
            secRegistro = listMotivosLocalizaciones.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMotivosLocalizaciones.asignarIndex \n");
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
            System.out.println("ERROR ControlMotivosLocalizaciones.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoLocalizacion");
            bandera = 0;
            filtrarMotivosLocalizaciones = null;
            tipoLista = 0;
        }

        borrarMotivosLocalizaciones.clear();
        crearMotivosLocalizaciones.clear();
        modificarMotivosLocalizaciones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMotivosLocalizaciones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoLocalizacion");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosMotivoLocalizacion");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoLocalizacion");
            bandera = 0;
            filtrarMotivosLocalizaciones = null;
            tipoLista = 0;
        }
    }

    public void modificarMotivosLocalizaciones(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVOS LOCALIZACIONES");
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
                if (!crearMotivosLocalizaciones.contains(listMotivosLocalizaciones.get(indice))) {
                    if (listMotivosLocalizaciones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosLocalizaciones.size(); j++) {
                            if (j != indice) {
                                if (listMotivosLocalizaciones.get(indice).getCodigo() == listMotivosLocalizaciones.get(j).getCodigo()) {
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
                    if (listMotivosLocalizaciones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMotivosLocalizaciones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (banderita == true) {
                        if (modificarMotivosLocalizaciones.isEmpty()) {
                            modificarMotivosLocalizaciones.add(listMotivosLocalizaciones.get(indice));
                        } else if (!modificarMotivosLocalizaciones.contains(listMotivosLocalizaciones.get(indice))) {
                            modificarMotivosLocalizaciones.add(listMotivosLocalizaciones.get(indice));
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

                if (!crearMotivosLocalizaciones.contains(filtrarMotivosLocalizaciones.get(indice))) {
                    if (filtrarMotivosLocalizaciones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMotivosLocalizaciones.size(); j++) {
                            if (j != indice) {
                                if (listMotivosLocalizaciones.get(indice).getCodigo() == listMotivosLocalizaciones.get(j).getCodigo()) {
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
                    if (filtrarMotivosLocalizaciones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMotivosLocalizaciones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMotivosLocalizaciones.isEmpty()) {
                            modificarMotivosLocalizaciones.add(filtrarMotivosLocalizaciones.get(indice));
                        } else if (!modificarMotivosLocalizaciones.contains(filtrarMotivosLocalizaciones.get(indice))) {
                            modificarMotivosLocalizaciones.add(filtrarMotivosLocalizaciones.get(indice));
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
        }

    }

    public void borrarMotivosLocalizaciones() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrardatosMotivoLocalizacion");
                if (!modificarMotivosLocalizaciones.isEmpty() && modificarMotivosLocalizaciones.contains(listMotivosLocalizaciones.get(index))) {
                    int modIndex = modificarMotivosLocalizaciones.indexOf(listMotivosLocalizaciones.get(index));
                    modificarMotivosLocalizaciones.remove(modIndex);
                    borrarMotivosLocalizaciones.add(listMotivosLocalizaciones.get(index));
                } else if (!crearMotivosLocalizaciones.isEmpty() && crearMotivosLocalizaciones.contains(listMotivosLocalizaciones.get(index))) {
                    int crearIndex = crearMotivosLocalizaciones.indexOf(listMotivosLocalizaciones.get(index));
                    crearMotivosLocalizaciones.remove(crearIndex);
                } else {
                    borrarMotivosLocalizaciones.add(listMotivosLocalizaciones.get(index));
                }
                listMotivosLocalizaciones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrardatosMotivoLocalizacion ");
                if (!modificarMotivosLocalizaciones.isEmpty() && modificarMotivosLocalizaciones.contains(filtrarMotivosLocalizaciones.get(index))) {
                    int modIndex = modificarMotivosLocalizaciones.indexOf(filtrarMotivosLocalizaciones.get(index));
                    modificarMotivosLocalizaciones.remove(modIndex);
                    borrarMotivosLocalizaciones.add(filtrarMotivosLocalizaciones.get(index));
                } else if (!crearMotivosLocalizaciones.isEmpty() && crearMotivosLocalizaciones.contains(filtrarMotivosLocalizaciones.get(index))) {
                    int crearIndex = crearMotivosLocalizaciones.indexOf(filtrarMotivosLocalizaciones.get(index));
                    crearMotivosLocalizaciones.remove(crearIndex);
                } else {
                    borrarMotivosLocalizaciones.add(filtrarMotivosLocalizaciones.get(index));
                }
                int VCIndex = listMotivosLocalizaciones.indexOf(filtrarMotivosLocalizaciones.get(index));
                listMotivosLocalizaciones.remove(VCIndex);
                filtrarMotivosLocalizaciones.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMotivoLocalizacion");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
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
    public void guardarMotivosLocalizaciones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Motivos Localizacion");
            if (!borrarMotivosLocalizaciones.isEmpty()) {
                administrarMotivosLocalizacion.borrarMotivosLocalizaciones(borrarMotivosLocalizaciones);

                //mostrarBorrados
                registrosBorrados = borrarMotivosLocalizaciones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivosLocalizaciones.clear();
            }
            if (!crearMotivosLocalizaciones.isEmpty()) {
                administrarMotivosLocalizacion.crearMotivosLocalizaciones(crearMotivosLocalizaciones);

                crearMotivosLocalizaciones.clear();
            }
            if (!modificarMotivosLocalizaciones.isEmpty()) {
                administrarMotivosLocalizacion.modificarMotivosLocalizaciones(modificarMotivosLocalizaciones);
                modificarMotivosLocalizaciones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosLocalizaciones = null;
            context.update("form:datosMotivoLocalizacion");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:aceptar");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMotivoLocalizacion = listMotivosLocalizaciones.get(index);
            }
            if (tipoLista == 1) {
                editarMotivoLocalizacion = filtrarMotivosLocalizaciones.get(index);
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

    public void agregarNuevoMotivoLocalizacion() {
        System.out.println("Agregar Motivo Localizacion");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoLocalizacion.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoLocalizacion.getCodigo());

            for (int x = 0; x < listMotivosLocalizaciones.size(); x++) {
                if (listMotivosLocalizaciones.get(x).getCodigo() == nuevoMotivoLocalizacion.getCodigo()) {
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
        if (nuevoMotivoLocalizacion.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoLocalizacion");
                bandera = 0;
                filtrarMotivosLocalizaciones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoLocalizacion.setSecuencia(l);

            crearMotivosLocalizaciones.add(nuevoMotivoLocalizacion);

            listMotivosLocalizaciones.add(nuevoMotivoLocalizacion);
            nuevoMotivoLocalizacion = new MotivosLocalizaciones();

            context.update("form:datosMotivoLocalizacion");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            System.out.println("Despues de la bandera guardado");

            context.execute("nuevoRegistroMotivoLocalizaciones.hide()");
            index = -1;
            secRegistro = null;
            System.out.println("Despues de nuevoRegistroMotivoLocalizaciones");

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivoLocalizacion() {
        System.out.println("limpiarNuevoMotivoLocalizacion");
        nuevoMotivoLocalizacion = new MotivosLocalizaciones();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarMotivosLocalizaciones() {
        System.out.println("duplicarMotivosLocalizaciones");
        if (index >= 0) {
            duplicarMotivoLocalizacion = new MotivosLocalizaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoLocalizacion.setSecuencia(l);
                duplicarMotivoLocalizacion.setCodigo(listMotivosLocalizaciones.get(index).getCodigo());
                duplicarMotivoLocalizacion.setDescripcion(listMotivosLocalizaciones.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarMotivoLocalizacion.setSecuencia(l);
                duplicarMotivoLocalizacion.setCodigo(filtrarMotivosLocalizaciones.get(index).getCodigo());
                duplicarMotivoLocalizacion.setDescripcion(filtrarMotivosLocalizaciones.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMotivosLocalizaciones");
            context.execute("duplicarRegistroMotivosLocalizaciones.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR ControlMotivosLocalizaciones");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivoLocalizacion.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarMotivoLocalizacion.getDescripcion());

        if (duplicarMotivoLocalizacion.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosLocalizaciones.size(); x++) {
                if (listMotivosLocalizaciones.get(x).getCodigo() == duplicarMotivoLocalizacion.getCodigo()) {
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
        if (duplicarMotivoLocalizacion.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoLocalizacion.getSecuencia() + "  " + duplicarMotivoLocalizacion.getCodigo());
            if (crearMotivosLocalizaciones.contains(duplicarMotivoLocalizacion)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosLocalizaciones.add(duplicarMotivoLocalizacion);
            crearMotivosLocalizaciones.add(duplicarMotivoLocalizacion);
            context.update("form:datosMotivoLocalizacion");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMotivoLocalizacion:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoLocalizacion");
                bandera = 0;
                filtrarMotivosLocalizaciones = null;
                tipoLista = 0;
            }
            duplicarMotivoLocalizacion = new MotivosLocalizaciones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivosLocalizaciones.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarMotivosLocalizaciones() {
        duplicarMotivoLocalizacion = new MotivosLocalizaciones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoLocalizacionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MotivosLocalizacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoLocalizacionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MotivosLocalizacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMotivosLocalizaciones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSLOCALIZACIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSLOCALIZACIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//**----------------------------------------------------------------------------
    public List<MotivosLocalizaciones> getListMotivosLocalizaciones() {
        if (listMotivosLocalizaciones == null) {
            listMotivosLocalizaciones = administrarMotivosLocalizacion.mostrarMotivosCambiosCargos();
        }
        return listMotivosLocalizaciones;
    }

    public void setListMotivosLocalizaciones(List<MotivosLocalizaciones> listMotivosLocalizaciones) {
        this.listMotivosLocalizaciones = listMotivosLocalizaciones;
    }

    public List<MotivosLocalizaciones> getFiltrarMotivosLocalizaciones() {
        return filtrarMotivosLocalizaciones;
    }

    public void setFiltrarMotivosLocalizaciones(List<MotivosLocalizaciones> filtrarMotivosLocalizaciones) {
        this.filtrarMotivosLocalizaciones = filtrarMotivosLocalizaciones;
    }

    public MotivosLocalizaciones getEditarMotivoLocalizacion() {
        return editarMotivoLocalizacion;
    }

    public void setEditarMotivoLocalizacion(MotivosLocalizaciones editarMotivoLocalizacion) {
        this.editarMotivoLocalizacion = editarMotivoLocalizacion;
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

    public MotivosLocalizaciones getNuevoMotivoLocalizacion() {
        return nuevoMotivoLocalizacion;
    }

    public void setNuevoMotivoLocalizacion(MotivosLocalizaciones nuevoMotivoLocalizacion) {
        this.nuevoMotivoLocalizacion = nuevoMotivoLocalizacion;
    }

    public MotivosLocalizaciones getDuplicarMotivoLocalizacion() {
        return duplicarMotivoLocalizacion;
    }

    public void setDuplicarMotivoLocalizacion(MotivosLocalizaciones duplicarMotivoLocalizacion) {
        this.duplicarMotivoLocalizacion = duplicarMotivoLocalizacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

}

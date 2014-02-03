/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ClasesPensiones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarClasesPensionesInterface;
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
public class ControlClasesPensiones implements Serializable {

    @EJB
    AdministrarClasesPensionesInterface administrarClasesPensiones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<ClasesPensiones> listClasesPensiones;
    private List<ClasesPensiones> filtrarClasesPensiones;
    private List<ClasesPensiones> crearClasesPensiones;
    private List<ClasesPensiones> modificarClasesPensiones;
    private List<ClasesPensiones> borrarClasesPensiones;
    private ClasesPensiones nuevoClasesPensiones;
    private ClasesPensiones duplicarClasesPensiones;
    private ClasesPensiones editarClasesPensiones;
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
    //filtrado table
    private int tamano;

    public ControlClasesPensiones() {
        listClasesPensiones = null;
        crearClasesPensiones = new ArrayList<ClasesPensiones>();
        modificarClasesPensiones = new ArrayList<ClasesPensiones>();
        borrarClasesPensiones = new ArrayList<ClasesPensiones>();
        permitirIndex = true;
        editarClasesPensiones = new ClasesPensiones();
        nuevoClasesPensiones = new ClasesPensiones();
        duplicarClasesPensiones = new ClasesPensiones();
        guardado = true;
        tamano = 300;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlClasesPensiones.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlClasesPensiones eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listClasesPensiones.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlClasesPensiones.asignarIndex \n");
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
            System.out.println("ERROR ControlClasesPensiones.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesPensiones");
            bandera = 0;
            filtrarClasesPensiones = null;
            tipoLista = 0;
        }

        borrarClasesPensiones.clear();
        crearClasesPensiones.clear();
        modificarClasesPensiones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listClasesPensiones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosClasesPensiones");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            tamano = 280;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:codigo");
            codigo.setFilterStyle("width: 220px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosClasesPensiones");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 300;
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosClasesPensiones");
            bandera = 0;
            filtrarClasesPensiones = null;
            tipoLista = 0;
        }
    }

    public void modificarClasesPensiones(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR SUB CATEGORIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR EMPRESAS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearClasesPensiones.contains(listClasesPensiones.get(indice))) {
                    if (listClasesPensiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listClasesPensiones.size(); j++) {
                            if (j != indice) {
                                if (listClasesPensiones.get(indice).getCodigo() == listClasesPensiones.get(j).getCodigo()) {
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
                    if (listClasesPensiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listClasesPensiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarClasesPensiones.isEmpty()) {
                            modificarClasesPensiones.add(listClasesPensiones.get(indice));
                        } else if (!modificarClasesPensiones.contains(listClasesPensiones.get(indice))) {
                            modificarClasesPensiones.add(listClasesPensiones.get(indice));
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

                if (!crearClasesPensiones.contains(filtrarClasesPensiones.get(indice))) {
                    if (filtrarClasesPensiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < filtrarClasesPensiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesPensiones.get(indice).getCodigo() == listClasesPensiones.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < listClasesPensiones.size(); j++) {
                            if (j != indice) {
                                if (filtrarClasesPensiones.get(indice).getCodigo() == listClasesPensiones.get(j).getCodigo()) {
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

                    if (filtrarClasesPensiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarClasesPensiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarClasesPensiones.isEmpty()) {
                            modificarClasesPensiones.add(filtrarClasesPensiones.get(indice));
                        } else if (!modificarClasesPensiones.contains(filtrarClasesPensiones.get(indice))) {
                            modificarClasesPensiones.add(filtrarClasesPensiones.get(indice));
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
            context.update("form:datosClasesPensiones");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoClasesPensiones() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoClasesPensiones");
                if (!modificarClasesPensiones.isEmpty() && modificarClasesPensiones.contains(listClasesPensiones.get(index))) {
                    int modIndex = modificarClasesPensiones.indexOf(listClasesPensiones.get(index));
                    modificarClasesPensiones.remove(modIndex);
                    borrarClasesPensiones.add(listClasesPensiones.get(index));
                } else if (!crearClasesPensiones.isEmpty() && crearClasesPensiones.contains(listClasesPensiones.get(index))) {
                    int crearIndex = crearClasesPensiones.indexOf(listClasesPensiones.get(index));
                    crearClasesPensiones.remove(crearIndex);
                } else {
                    borrarClasesPensiones.add(listClasesPensiones.get(index));
                }
                listClasesPensiones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoClasesPensiones ");
                if (!modificarClasesPensiones.isEmpty() && modificarClasesPensiones.contains(filtrarClasesPensiones.get(index))) {
                    int modIndex = modificarClasesPensiones.indexOf(filtrarClasesPensiones.get(index));
                    modificarClasesPensiones.remove(modIndex);
                    borrarClasesPensiones.add(filtrarClasesPensiones.get(index));
                } else if (!crearClasesPensiones.isEmpty() && crearClasesPensiones.contains(filtrarClasesPensiones.get(index))) {
                    int crearIndex = crearClasesPensiones.indexOf(filtrarClasesPensiones.get(index));
                    crearClasesPensiones.remove(crearIndex);
                } else {
                    borrarClasesPensiones.add(filtrarClasesPensiones.get(index));
                }
                int VCIndex = listClasesPensiones.indexOf(filtrarClasesPensiones.get(index));
                listClasesPensiones.remove(VCIndex);
                filtrarClasesPensiones.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosClasesPensiones");
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
        BigInteger contarRetiradosClasePension;

        try {
            System.err.println("Control Secuencia de ControlClasesPensiones ");
            if (tipoLista == 0) {
                contarRetiradosClasePension = administrarClasesPensiones.contarRetiradosClasePension(listClasesPensiones.get(index).getSecuencia());
            } else {
                contarRetiradosClasePension = administrarClasesPensiones.contarRetiradosClasePension(filtrarClasesPensiones.get(index).getSecuencia());
            }
            if (contarRetiradosClasePension.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoClasesPensiones();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                contarRetiradosClasePension = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlClasesPensiones verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarClasesPensiones.isEmpty() || !crearClasesPensiones.isEmpty() || !modificarClasesPensiones.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarClasesPensiones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarClasesPensiones");
            if (!borrarClasesPensiones.isEmpty()) {
                administrarClasesPensiones.borrarClasesPensiones(borrarClasesPensiones);
                //mostrarBorrados
                registrosBorrados = borrarClasesPensiones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarClasesPensiones.clear();
            }
            if (!modificarClasesPensiones.isEmpty()) {
                administrarClasesPensiones.modificarClasesPensiones(modificarClasesPensiones);
                modificarClasesPensiones.clear();
            }
            if (!crearClasesPensiones.isEmpty()) {
                administrarClasesPensiones.crearClasesPensiones(crearClasesPensiones);
                crearClasesPensiones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listClasesPensiones = null;
                context.execute("mostrarGuardar.show()");
            context.update("form:datosClasesPensiones");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarClasesPensiones = listClasesPensiones.get(index);
            }
            if (tipoLista == 1) {
                editarClasesPensiones = filtrarClasesPensiones.get(index);
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

    public void agregarNuevoClasesPensiones() {
        System.out.println("agregarNuevoClasesPensiones");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoClasesPensiones.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoClasesPensiones.getCodigo());

            for (int x = 0; x < listClasesPensiones.size(); x++) {
                if (listClasesPensiones.get(x).getCodigo() == nuevoClasesPensiones.getCodigo()) {
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
        if (nuevoClasesPensiones.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosClasesPensiones");
                bandera = 0;
                filtrarClasesPensiones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoClasesPensiones.setSecuencia(l);

            crearClasesPensiones.add(nuevoClasesPensiones);

            listClasesPensiones.add(nuevoClasesPensiones);
            nuevoClasesPensiones = new ClasesPensiones();
            context.update("form:datosClasesPensiones");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroClasesPensiones.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoClasesPensiones() {
        System.out.println("limpiarNuevoClasesPensiones");
        nuevoClasesPensiones = new ClasesPensiones();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoClasesPensiones() {
        System.out.println("duplicandoClasesPensiones");
        if (index >= 0) {
            duplicarClasesPensiones = new ClasesPensiones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarClasesPensiones.setSecuencia(l);
                duplicarClasesPensiones.setCodigo(listClasesPensiones.get(index).getCodigo());
                duplicarClasesPensiones.setDescripcion(listClasesPensiones.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarClasesPensiones.setSecuencia(l);
                duplicarClasesPensiones.setCodigo(filtrarClasesPensiones.get(index).getCodigo());
                duplicarClasesPensiones.setDescripcion(filtrarClasesPensiones.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTE");
            context.execute("duplicarRegistroClasesPensiones.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR TIPOS EMPRESAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarClasesPensiones.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarClasesPensiones.getDescripcion());

        if (duplicarClasesPensiones.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listClasesPensiones.size(); x++) {
                if (listClasesPensiones.get(x).getCodigo() == duplicarClasesPensiones.getCodigo()) {
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
        if (duplicarClasesPensiones.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarClasesPensiones.getSecuencia() + "  " + duplicarClasesPensiones.getCodigo());
            if (crearClasesPensiones.contains(duplicarClasesPensiones)) {
                System.out.println("Ya lo contengo.");
            }
            listClasesPensiones.add(duplicarClasesPensiones);
            crearClasesPensiones.add(duplicarClasesPensiones);
            context.update("form:datosClasesPensiones");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosClasesPensiones:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosClasesPensiones");
                bandera = 0;
                filtrarClasesPensiones = null;
                tipoLista = 0;
            }
            duplicarClasesPensiones = new ClasesPensiones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroClasesPensiones.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarClasesPensiones() {
        duplicarClasesPensiones = new ClasesPensiones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesPensionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MOTIVOSRETIROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosClasesPensionesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MOTIVOSRETIROS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listClasesPensiones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MOTIVOSRETIROS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSRETIROS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<ClasesPensiones> getListClasesPensiones() {
        if (listClasesPensiones == null) {
            listClasesPensiones = administrarClasesPensiones.consultarClasesPensiones();
        }
        return listClasesPensiones;
    }

    public void setListClasesPensiones(List<ClasesPensiones> listClasesPensiones) {
        this.listClasesPensiones = listClasesPensiones;
    }

    public List<ClasesPensiones> getFiltrarClasesPensiones() {
        return filtrarClasesPensiones;
    }

    public void setFiltrarClasesPensiones(List<ClasesPensiones> filtrarClasesPensiones) {
        this.filtrarClasesPensiones = filtrarClasesPensiones;
    }

    public ClasesPensiones getNuevoClasesPensiones() {
        return nuevoClasesPensiones;
    }

    public void setNuevoClasesPensiones(ClasesPensiones nuevoClasesPensiones) {
        this.nuevoClasesPensiones = nuevoClasesPensiones;
    }

    public ClasesPensiones getDuplicarClasesPensiones() {
        return duplicarClasesPensiones;
    }

    public void setDuplicarClasesPensiones(ClasesPensiones duplicarClasesPensiones) {
        this.duplicarClasesPensiones = duplicarClasesPensiones;
    }

    public ClasesPensiones getEditarClasesPensiones() {
        return editarClasesPensiones;
    }

    public void setEditarClasesPensiones(ClasesPensiones editarClasesPensiones) {
        this.editarClasesPensiones = editarClasesPensiones;
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

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

}

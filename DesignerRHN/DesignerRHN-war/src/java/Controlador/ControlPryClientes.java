/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.PryClientes;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPryClientesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlPryClientes implements Serializable {

    @EJB
    AdministrarPryClientesInterface administrarPryClientes;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<PryClientes> listPryClientes;
    private List<PryClientes> filtrarPryClientes;
    private List<PryClientes> crearPryClientes;
    private List<PryClientes> modificarPryClientes;
    private List<PryClientes> borrarPryClientes;
    private PryClientes nuevoPryCliente;
    private PryClientes duplicarPryCliente;
    private PryClientes editarPryCliente;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column nombre, direccion, telefono, contacto;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;

    public ControlPryClientes() {
        listPryClientes = null;
        crearPryClientes = new ArrayList<PryClientes>();
        modificarPryClientes = new ArrayList<PryClientes>();
        borrarPryClientes = new ArrayList<PryClientes>();
        permitirIndex = true;
        editarPryCliente = new PryClientes();
        nuevoPryCliente = new PryClientes();
        duplicarPryCliente = new PryClientes();
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlPryClientes.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlPryClientes eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listPryClientes.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlPryClientes.asignarIndex \n");
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
            System.out.println("ERROR ControlPryClientes.asignarIndex ERROR======" + e.getMessage());
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
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            direccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:direccion");
            direccion.setFilterStyle("display: none; visibility: hidden;");
            telefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:telefono");
            telefono.setFilterStyle("display: none; visibility: hidden;");
            contacto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:contacto");
            contacto.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPryCliente");
            bandera = 0;
            filtrarPryClientes = null;
            tipoLista = 0;
        }

        borrarPryClientes.clear();
        crearPryClientes.clear();
        modificarPryClientes.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listPryClientes = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosPryCliente");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:nombre");
            nombre.setFilterStyle("width: 215px");
            direccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:direccion");
            direccion.setFilterStyle("width: 210px");
            telefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:telefono");
            telefono.setFilterStyle("width: 100px");
            contacto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:contacto");
            contacto.setFilterStyle("width: 170px");
            RequestContext.getCurrentInstance().update("form:datosPryCliente");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            direccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:direccion");
            direccion.setFilterStyle("display: none; visibility: hidden;");
            telefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:telefono");
            telefono.setFilterStyle("display: none; visibility: hidden;");
            contacto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:contacto");
            contacto.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosPryCliente");
            bandera = 0;
            filtrarPryClientes = null;
            tipoLista = 0;
        }
    }

    public void modificarPryCliente(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR PRY CLIENTE");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR PRY CLIENTE, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearPryClientes.contains(listPryClientes.get(indice))) {

                    if (listPryClientes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        System.err.println("nombre");
                    } else if (listPryClientes.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                        System.err.println("nombre");
                    } else {
                        banderita = true;
                    }

                    System.err.println("VALIDACION BANDERITA == " + banderita);
                    if (banderita == true) {
                        if (modificarPryClientes.isEmpty()) {
                            modificarPryClientes.add(listPryClientes.get(indice));
                        } else if (!modificarPryClientes.contains(listPryClientes.get(indice))) {
                            modificarPryClientes.add(listPryClientes.get(indice));
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

                if (!crearPryClientes.contains(filtrarPryClientes.get(indice))) {

                    if (filtrarPryClientes.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarPryClientes.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarPryClientes.isEmpty()) {
                            modificarPryClientes.add(filtrarPryClientes.get(indice));
                        } else if (!modificarPryClientes.contains(filtrarPryClientes.get(indice))) {
                            modificarPryClientes.add(filtrarPryClientes.get(indice));
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
            context.update("form:datosPryCliente");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoPryCliente() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoPryCliente");
                if (!modificarPryClientes.isEmpty() && modificarPryClientes.contains(listPryClientes.get(index))) {
                    int modIndex = modificarPryClientes.indexOf(listPryClientes.get(index));
                    modificarPryClientes.remove(modIndex);
                    borrarPryClientes.add(listPryClientes.get(index));
                } else if (!crearPryClientes.isEmpty() && crearPryClientes.contains(listPryClientes.get(index))) {
                    int crearIndex = crearPryClientes.indexOf(listPryClientes.get(index));
                    crearPryClientes.remove(crearIndex);
                } else {
                    borrarPryClientes.add(listPryClientes.get(index));
                }
                listPryClientes.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoPryCliente");
                if (!modificarPryClientes.isEmpty() && modificarPryClientes.contains(filtrarPryClientes.get(index))) {
                    int modIndex = modificarPryClientes.indexOf(filtrarPryClientes.get(index));
                    modificarPryClientes.remove(modIndex);
                    borrarPryClientes.add(filtrarPryClientes.get(index));
                } else if (!crearPryClientes.isEmpty() && crearPryClientes.contains(filtrarPryClientes.get(index))) {
                    int crearIndex = crearPryClientes.indexOf(filtrarPryClientes.get(index));
                    crearPryClientes.remove(crearIndex);
                } else {
                    borrarPryClientes.add(filtrarPryClientes.get(index));
                }
                int VCIndex = listPryClientes.indexOf(filtrarPryClientes.get(index));
                listPryClientes.remove(VCIndex);
                filtrarPryClientes.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosPryCliente");
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
            BigInteger proyectos = new BigInteger("-1");
            System.err.println("Control Secuencia de ControlPryClientes ");
            if (tipoLista == 0) {
                proyectos = administrarPryClientes.verificarBorradoProyecto(listPryClientes.get(index).getSecuencia());
            } else {
                proyectos = administrarPryClientes.verificarBorradoProyecto(filtrarPryClientes.get(index).getSecuencia());
            }
            if (proyectos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoPryCliente();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                proyectos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEvalCompetencias verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarPryClientes.isEmpty() || !crearPryClientes.isEmpty() || !modificarPryClientes.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarPryCliente() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarPryCliente");
            if (!borrarPryClientes.isEmpty()) {
                for (int i = 0; i < borrarPryClientes.size(); i++) {
                    System.out.println("Borrando...");
                    administrarPryClientes.borrarPryClientes(borrarPryClientes.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarPryClientes.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarPryClientes.clear();
            }
            if (!crearPryClientes.isEmpty()) {
                for (int i = 0; i < crearPryClientes.size(); i++) {

                    System.out.println("Creando...");
                    administrarPryClientes.crearPryClientes(crearPryClientes.get(i));

                }
                crearPryClientes.clear();
            }
            if (!modificarPryClientes.isEmpty()) {
                administrarPryClientes.modificarPryClientes(modificarPryClientes);
                modificarPryClientes.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listPryClientes = null;
            context.update("form:datosPryCliente");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarPryCliente = listPryClientes.get(index);
            }
            if (tipoLista == 1) {
                editarPryCliente = filtrarPryClientes.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editNombre");
                context.execute("editNombre.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editDireccion");
                context.execute("editDireccion.show()");
                cualCelda = -1;

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editTelefono");
                context.execute("editTelefono.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editContacto");
                context.execute("editContacto.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoPryClientes() {
        System.out.println("agregarNuevoPryClientes");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoPryCliente.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 1) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                direccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:direccion");
                direccion.setFilterStyle("display: none; visibility: hidden;");
                telefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:telefono");
                telefono.setFilterStyle("display: none; visibility: hidden;");
                contacto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:contacto");
                contacto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPryCliente");
                bandera = 0;
                filtrarPryClientes = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoPryCliente.setSecuencia(l);

            crearPryClientes.add(nuevoPryCliente);

            listPryClientes.add(nuevoPryCliente);
            nuevoPryCliente = new PryClientes();
            context.update("form:datosPryCliente");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroPryClientes.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoPryClientes() {
        System.out.println("limpiarNuevoPryClientes");
        nuevoPryCliente = new PryClientes();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoPryClientes() {
        System.out.println("duplicandoPryClientes");
        if (index >= 0) {
            duplicarPryCliente = new PryClientes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarPryCliente.setSecuencia(l);
                duplicarPryCliente.setNombre(listPryClientes.get(index).getNombre());
                duplicarPryCliente.setDireccion(listPryClientes.get(index).getDireccion());
                duplicarPryCliente.setTelefono(listPryClientes.get(index).getTelefono());
                duplicarPryCliente.setContacto(listPryClientes.get(index).getContacto());
            }
            if (tipoLista == 1) {
                duplicarPryCliente.setSecuencia(l);
                duplicarPryCliente.setNombre(filtrarPryClientes.get(index).getNombre());
                duplicarPryCliente.setDireccion(filtrarPryClientes.get(index).getDireccion());
                duplicarPryCliente.setTelefono(filtrarPryClientes.get(index).getTelefono());
                duplicarPryCliente.setContacto(filtrarPryClientes.get(index).getContacto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarPRYC");
            context.execute("duplicarRegistroPryClientes.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR PRYCLIENTES");
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar nombre " + duplicarPryCliente.getNombre());
        System.err.println("ConfirmarDuplicar direccion " + duplicarPryCliente.getDireccion());

        if (duplicarPryCliente.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (contador == 1) {

            System.out.println("Datos Duplicando: " + duplicarPryCliente.getSecuencia() + "  " + duplicarPryCliente.getNombre());
            if (crearPryClientes.contains(duplicarPryCliente)) {
                System.out.println("Ya lo contengo.");
            }
            listPryClientes.add(duplicarPryCliente);
            crearPryClientes.add(duplicarPryCliente);
            context.update("form:datosPryCliente");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                direccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:direccion");
                direccion.setFilterStyle("display: none; visibility: hidden;");
                telefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:telefono");
                telefono.setFilterStyle("display: none; visibility: hidden;");
                contacto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPryCliente:contacto");
                contacto.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosPryCliente");
                bandera = 0;
                filtrarPryClientes = null;
                tipoLista = 0;
            }
            duplicarPryCliente = new PryClientes();
            RequestContext.getCurrentInstance().execute("duplicarRegistroPryClientes.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarPryClientes() {
        duplicarPryCliente = new PryClientes();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPryClienteExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PRY_CLIENTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPryClienteExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PRY_CLIENTES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listPryClientes.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PRY_CLIENTES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("PRY_CLIENTES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<PryClientes> getListPryClientes() {
        if (listPryClientes == null) {
            listPryClientes = administrarPryClientes.mostrarPryClientes();
        }
        return listPryClientes;
    }

    public void setListPryClientes(List<PryClientes> listPryClientes) {
        this.listPryClientes = listPryClientes;
    }

    public List<PryClientes> getFiltrarPryClientes() {
        return filtrarPryClientes;
    }

    public void setFiltrarPryClientes(List<PryClientes> filtrarPryClientes) {
        this.filtrarPryClientes = filtrarPryClientes;
    }

    public PryClientes getNuevoPryCliente() {
        return nuevoPryCliente;
    }

    public void setNuevoPryCliente(PryClientes nuevoPryCliente) {
        this.nuevoPryCliente = nuevoPryCliente;
    }

    public PryClientes getDuplicarPryCliente() {
        return duplicarPryCliente;
    }

    public void setDuplicarPryCliente(PryClientes duplicarPryCliente) {
        this.duplicarPryCliente = duplicarPryCliente;
    }

    public PryClientes getEditarPryCliente() {
        return editarPryCliente;
    }

    public void setEditarPryCliente(PryClientes editarPryCliente) {
        this.editarPryCliente = editarPryCliente;
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

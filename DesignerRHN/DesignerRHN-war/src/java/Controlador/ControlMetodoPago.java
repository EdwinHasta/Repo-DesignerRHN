/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MetodosPagos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMetodosPagosInterface;
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
public class ControlMetodoPago implements Serializable {

    @EJB
    AdministrarMetodosPagosInterface administrarMetodosPagos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    List<MetodosPagos> listMetodosPagos;
    List<MetodosPagos> filtrarMetodosPagos;
    List<MetodosPagos> borrarMetodosPagos;
    List<MetodosPagos> crearMetodosPagos;
    List<MetodosPagos> modificarMetodosPagos;
    MetodosPagos editarMetodoPago;
    MetodosPagos nuevoMetodoPago;
    MetodosPagos duplicarMetodoPago;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, pago;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private Integer a;

    public ControlMetodoPago() {
        listMetodosPagos = null;
        crearMetodosPagos = new ArrayList<MetodosPagos>();
        modificarMetodosPagos = new ArrayList<MetodosPagos>();
        borrarMetodosPagos = new ArrayList<MetodosPagos>();
        permitirIndex = true;
        guardado = true;
        editarMetodoPago = new MetodosPagos();
        nuevoMetodoPago = new MetodosPagos();
        a = null;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMotiviosCambiosCargos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMotiviosCambiosCargos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMetodosPagos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMetodoPago.asignarIndex \n");
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
            System.out.println("ERROR ControlMotiviosCambiosCargos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void prueba(String campo) {
        System.err.println("PROBANDO EL SELECT ONE MENU CAMPO SELECCIONADO = " + campo);
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            pago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:pago");
            pago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMetodoPago");
            bandera = 0;
            filtrarMetodosPagos = null;
            tipoLista = 0;
        }

        borrarMetodosPagos.clear();
        crearMetodosPagos.clear();
        modificarMetodosPagos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMetodosPagos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMetodoPago");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:codigo");
            codigo.setFilterStyle("width: 280px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:descripcion");
            descripcion.setFilterStyle("width: 290px");
            pago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:pago");
            pago.setFilterStyle("width: 150px");
            RequestContext.getCurrentInstance().update("form:datosMetodoPago");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            pago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:pago");
            pago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMetodoPago");
            bandera = 0;
            filtrarMetodosPagos = null;
            tipoLista = 0;
        }
    }

    public void modificarMetodosPagos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR METODOS PAGOS");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR METODOS PAGOS, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMetodosPagos.contains(listMetodosPagos.get(indice))) {
                    if (listMetodosPagos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMetodosPagos.size(); j++) {
                            if (j != indice) {
                                if (listMetodosPagos.get(indice).getCodigo() == listMetodosPagos.get(j).getCodigo()) {
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
                    if (listMetodosPagos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMetodosPagos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMetodosPagos.isEmpty()) {
                            modificarMetodosPagos.add(listMetodosPagos.get(indice));
                        } else if (!modificarMetodosPagos.contains(listMetodosPagos.get(indice))) {
                            modificarMetodosPagos.add(listMetodosPagos.get(indice));
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

                if (!crearMetodosPagos.contains(filtrarMetodosPagos.get(indice))) {
                    if (filtrarMetodosPagos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMetodosPagos.size(); j++) {
                            if (j != indice) {
                                if (listMetodosPagos.get(indice).getCodigo() == listMetodosPagos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarMetodosPagos.size(); j++) {
                            if (j != indice) {
                                if (filtrarMetodosPagos.get(indice).getCodigo() == filtrarMetodosPagos.get(j).getCodigo()) {
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

                    if (filtrarMetodosPagos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMetodosPagos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMetodosPagos.isEmpty()) {
                            modificarMetodosPagos.add(filtrarMetodosPagos.get(indice));
                        } else if (!modificarMetodosPagos.contains(filtrarMetodosPagos.get(indice))) {
                            modificarMetodosPagos.add(filtrarMetodosPagos.get(indice));
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
            context.update("form:datosMetodoPago");
            context.update("form:ACEPTAR");
        }

    }
    private BigInteger verificarVigenciasFormasPagos;

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de ControlTiposTallas ");
            if (tipoLista == 0) {
                verificarVigenciasFormasPagos = administrarMetodosPagos.verificarVigenciasFormasPagos(listMetodosPagos.get(index).getSecuencia());
            } else {
                verificarVigenciasFormasPagos = administrarMetodosPagos.verificarVigenciasFormasPagos(filtrarMetodosPagos.get(index).getSecuencia());
            }
            if (verificarVigenciasFormasPagos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoMetodoPago();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarVigenciasFormasPagos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposTallas verificarBorrado ERROR " + e);
        }
    }

    public void borrandoMetodoPago() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarMetodoPago");
                if (!modificarMetodosPagos.isEmpty() && modificarMetodosPagos.contains(listMetodosPagos.get(index))) {
                    int modIndex = modificarMetodosPagos.indexOf(listMetodosPagos.get(index));
                    modificarMetodosPagos.remove(modIndex);
                    borrarMetodosPagos.add(listMetodosPagos.get(index));
                } else if (!crearMetodosPagos.isEmpty() && crearMetodosPagos.contains(listMetodosPagos.get(index))) {
                    int crearIndex = crearMetodosPagos.indexOf(listMetodosPagos.get(index));
                    crearMetodosPagos.remove(crearIndex);
                } else {
                    borrarMetodosPagos.add(listMetodosPagos.get(index));
                }
                listMetodosPagos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarMetodoPago ");
                if (!modificarMetodosPagos.isEmpty() && modificarMetodosPagos.contains(filtrarMetodosPagos.get(index))) {
                    int modIndex = modificarMetodosPagos.indexOf(filtrarMetodosPagos.get(index));
                    modificarMetodosPagos.remove(modIndex);
                    borrarMetodosPagos.add(filtrarMetodosPagos.get(index));
                } else if (!crearMetodosPagos.isEmpty() && crearMetodosPagos.contains(filtrarMetodosPagos.get(index))) {
                    int crearIndex = crearMetodosPagos.indexOf(filtrarMetodosPagos.get(index));
                    crearMetodosPagos.remove(crearIndex);
                } else {
                    borrarMetodosPagos.add(filtrarMetodosPagos.get(index));
                }
                int VCIndex = listMetodosPagos.indexOf(filtrarMetodosPagos.get(index));
                listMetodosPagos.remove(VCIndex);
                filtrarMetodosPagos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMetodoPago");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void guardarMetodosPagos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarMetodosPagos.isEmpty()) {
                for (int i = 0; i < borrarMetodosPagos.size(); i++) {
                    System.out.println("Borrando...");
                    administrarMetodosPagos.borrarMetodosPagos(borrarMetodosPagos.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarMetodosPagos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMetodosPagos.clear();
            }
            if (!crearMetodosPagos.isEmpty()) {
                for (int i = 0; i < crearMetodosPagos.size(); i++) {

                    System.out.println("Creando...");
                    administrarMetodosPagos.crearMetodosPagos(crearMetodosPagos.get(i));

                }
                crearMetodosPagos.clear();
            }
            if (!modificarMetodosPagos.isEmpty()) {
                administrarMetodosPagos.modificarMetodosPagos(modificarMetodosPagos);
                modificarMetodosPagos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMetodosPagos = null;
            context.update("form:datosMetodoPago");
            k = 0;
        }
        index = -1;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMetodoPago = listMetodosPagos.get(index);
            }
            if (tipoLista == 1) {
                editarMetodoPago = filtrarMetodosPagos.get(index);
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

    public void agregarNuevoMetodoPago() {
        System.out.println("Agregar Metodo Pago");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("1");
        if (nuevoMetodoPago.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo Metodo: " + nuevoMetodoPago.getCodigo());

            for (int x = 0; x < listMetodosPagos.size(); x++) {
                if (listMetodosPagos.get(x).getCodigo() == nuevoMetodoPago.getCodigo()) {
                    duplicados++;
                }
            }
            System.err.println("2");
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoMetodoPago.getDescripcion() == null) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                pago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:pago");
                pago.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMetodoPago");
                bandera = 0;
                filtrarMetodosPagos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            ///nuevoMetodoPago.setSecuencia(l);
            System.err.println("nuevometodopago secuencia " + nuevoMetodoPago.getSecuencia());

            crearMetodosPagos.add(nuevoMetodoPago);

            listMetodosPagos.add(nuevoMetodoPago);
            nuevoMetodoPago = new MetodosPagos();

            context.update("form:datosMetodoPago");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            System.out.println("Despues de la bandera guardado");

            context.execute("nuevoRegistroMetodosPagos.hide()");
            index = -1;
            secRegistro = null;
            System.out.println("Despues de nuevoRegistroMetodosPagos");

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMetodosPagos() {
        System.out.println("limpiarNuevoMetodosPagos");
        nuevoMetodoPago = new MetodosPagos();
        secRegistro = null;
        index = -1;

    }

    public void duplicarMetodosPagos() {
        System.out.println("duplicarMetodosPagos");
        if (index >= 0) {
            duplicarMetodoPago = new MetodosPagos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMetodoPago.setSecuencia(null);
                duplicarMetodoPago.setCodigo(listMetodosPagos.get(index).getCodigo());
                duplicarMetodoPago.setDescripcion(listMetodosPagos.get(index).getDescripcion());
                duplicarMetodoPago.setPago(listMetodosPagos.get(index).getPago());
            }
            if (tipoLista == 1) {
                duplicarMetodoPago.setSecuencia(l);
                duplicarMetodoPago.setCodigo(filtrarMetodosPagos.get(index).getCodigo());
                duplicarMetodoPago.setDescripcion(filtrarMetodosPagos.get(index).getDescripcion());
                duplicarMetodoPago.setPago(filtrarMetodosPagos.get(index).getPago());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMetodosPagos");
            context.execute("duplicarRegistroMetodosPagos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR ControlMetodosPagos");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("ConfirmarDuplicar codigo " + duplicarMetodoPago.getCodigo());
        System.err.println("ConfirmarDuplicar descripcion " + duplicarMetodoPago.getDescripcion());
        System.err.println("ConfirmarDuplicar pago " + duplicarMetodoPago.getPago());

        if (duplicarMetodoPago.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMetodosPagos.size(); x++) {
                if (listMetodosPagos.get(x).getCodigo() == duplicarMetodoPago.getCodigo()) {
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
        if (duplicarMetodoPago.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMetodoPago.getSecuencia() + "  " + duplicarMetodoPago.getCodigo());
            if (crearMetodosPagos.contains(duplicarMetodoPago)) {
                System.out.println("Ya lo contengo.");
            }
            listMetodosPagos.add(duplicarMetodoPago);
            crearMetodosPagos.add(duplicarMetodoPago);
            context.update("form:datosMetodoPago");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMetodoPago:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMetodoPago");
                bandera = 0;
                filtrarMetodosPagos = null;
                tipoLista = 0;
            }
            duplicarMetodoPago = new MetodosPagos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMetodosPagos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarMetodoPago() {
        duplicarMetodoPago = new MetodosPagos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMetodoPagoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MetodosPagos", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMetodoPagoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MetodosPagos", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMetodosPagos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "METODOSPAGOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("METODOSPAGOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
    public List<MetodosPagos> getListMetodosPagos() {
        if (listMetodosPagos == null) {
            listMetodosPagos = administrarMetodosPagos.mostrarMetodosPagos();
            for (int i = 0; i < listMetodosPagos.size(); i++) {
                System.out.println(listMetodosPagos.get(i).getSecuencia());
            }
        }
        return listMetodosPagos;
    }

    public void setListMetodosPagos(List<MetodosPagos> listMetodosPagos) {
        this.listMetodosPagos = listMetodosPagos;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public List<MetodosPagos> getFiltrarMetodosPagos() {
        return filtrarMetodosPagos;
    }

    public void setFiltrarMetodosPagos(List<MetodosPagos> filtrarMetodosPagos) {
        this.filtrarMetodosPagos = filtrarMetodosPagos;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public MetodosPagos getEditarMetodoPago() {
        return editarMetodoPago;
    }

    public void setEditarMetodoPago(MetodosPagos editarMetodoPago) {
        this.editarMetodoPago = editarMetodoPago;
    }

    public MetodosPagos getNuevoMetodoPago() {
        return nuevoMetodoPago;
    }

    public void setNuevoMetodoPago(MetodosPagos nuevoMetodoPago) {
        this.nuevoMetodoPago = nuevoMetodoPago;
    }

    public MetodosPagos getDuplicarMetodoPago() {
        return duplicarMetodoPago;
    }

    public void setDuplicarMetodoPago(MetodosPagos duplicarMetodoPago) {
        this.duplicarMetodoPago = duplicarMetodoPago;
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
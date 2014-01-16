/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Monedas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMonedasInterface;
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
public class ControlMonedas implements Serializable {

    @EJB
    AdministrarMonedasInterface administrarMonedas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Monedas> listMonedas;
    private List<Monedas> filtrarMonedas;
    private List<Monedas> crearMonedas;
    private List<Monedas> modificarMonedas;
    private List<Monedas> borrarMonedas;
    private Monedas nuevoMoneda;
    private Monedas duplicarMoneda;
    private Monedas editarMoneda;
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
    private BigInteger proyectos;
    private String a;

    public ControlMonedas() {
        listMonedas = null;
        crearMonedas = new ArrayList<Monedas>();
        modificarMonedas = new ArrayList<Monedas>();
        borrarMonedas = new ArrayList<Monedas>();
        permitirIndex = true;
        editarMoneda = new Monedas();
        nuevoMoneda = new Monedas();
        duplicarMoneda = new Monedas();
        a = null;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlMonedas.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlMonedas eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listMonedas.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlMonedas.asignarIndex \n");
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
            System.out.println("ERROR ControlMonedas.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMoneda");
            bandera = 0;
            filtrarMonedas = null;
            tipoLista = 0;
        }

        borrarMonedas.clear();
        crearMonedas.clear();
        modificarMonedas.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listMonedas = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMoneda");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:codigo");
            codigo.setFilterStyle("width: 370px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosMoneda");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMoneda");
            bandera = 0;
            filtrarMonedas = null;
            tipoLista = 0;
        }
    }

    public void modificarMoneda(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MONEDA");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR IDIOMA, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearMonedas.contains(listMonedas.get(indice))) {
                    if (listMonedas.get(indice).getCodigo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMonedas.size(); j++) {
                            if (j != indice) {
                                if (listMonedas.get(indice).getCodigo().equals(listMonedas.get(j).getCodigo())) {
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
                    if (listMonedas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listMonedas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMonedas.isEmpty()) {
                            modificarMonedas.add(listMonedas.get(indice));
                        } else if (!modificarMonedas.contains(listMonedas.get(indice))) {
                            modificarMonedas.add(listMonedas.get(indice));
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

                if (!crearMonedas.contains(filtrarMonedas.get(indice))) {
                    if (filtrarMonedas.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listMonedas.size(); j++) {
                            if (j != indice) {
                                if (listMonedas.get(indice).getCodigo() == listMonedas.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarMonedas.size(); j++) {
                            if (j != indice) {
                                if (filtrarMonedas.get(indice).getCodigo() == filtrarMonedas.get(j).getCodigo()) {
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

                    if (filtrarMonedas.get(indice).getNombre().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarMonedas.get(indice).getNombre().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarMonedas.isEmpty()) {
                            modificarMonedas.add(filtrarMonedas.get(indice));
                        } else if (!modificarMonedas.contains(filtrarMonedas.get(indice))) {
                            modificarMonedas.add(filtrarMonedas.get(indice));
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
            context.update("form:datosMoneda");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoMonedas() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarandoMonedas");
                if (!modificarMonedas.isEmpty() && modificarMonedas.contains(listMonedas.get(index))) {
                    int modIndex = modificarMonedas.indexOf(listMonedas.get(index));
                    modificarMonedas.remove(modIndex);
                    borrarMonedas.add(listMonedas.get(index));
                } else if (!crearMonedas.isEmpty() && crearMonedas.contains(listMonedas.get(index))) {
                    int crearIndex = crearMonedas.indexOf(listMonedas.get(index));
                    crearMonedas.remove(crearIndex);
                } else {
                    borrarMonedas.add(listMonedas.get(index));
                }
                listMonedas.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarandoMonedas ");
                if (!modificarMonedas.isEmpty() && modificarMonedas.contains(filtrarMonedas.get(index))) {
                    int modIndex = modificarMonedas.indexOf(filtrarMonedas.get(index));
                    modificarMonedas.remove(modIndex);
                    borrarMonedas.add(filtrarMonedas.get(index));
                } else if (!crearMonedas.isEmpty() && crearMonedas.contains(filtrarMonedas.get(index))) {
                    int crearIndex = crearMonedas.indexOf(filtrarMonedas.get(index));
                    crearMonedas.remove(crearIndex);
                } else {
                    borrarMonedas.add(filtrarMonedas.get(index));
                }
                int VCIndex = listMonedas.indexOf(filtrarMonedas.get(index));
                listMonedas.remove(VCIndex);
                filtrarMonedas.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMoneda");
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
            System.err.println("Control Secuencia de ControlMonedas ");
            if (tipoLista == 0) {
                proyectos = administrarMonedas.verificarMonedasProyecto(listMonedas.get(index).getSecuencia());
            } else {
                proyectos = administrarMonedas.verificarMonedasProyecto(filtrarMonedas.get(index).getSecuencia());
            }
            if (proyectos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoMonedas();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                proyectos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlIdiomas verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarMonedas.isEmpty() || !crearMonedas.isEmpty() || !modificarMonedas.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarMonedas() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarMonedas");
            if (!borrarMonedas.isEmpty()) {
                for (int i = 0; i < borrarMonedas.size(); i++) {
                    System.out.println("Borrando...");
                    administrarMonedas.borrarMonedas(borrarMonedas.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarMonedas.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMonedas.clear();
            }
            if (!crearMonedas.isEmpty()) {
                for (int i = 0; i < crearMonedas.size(); i++) {

                    System.out.println("Creando...");
                    administrarMonedas.crearMonedas(crearMonedas.get(i));

                }
                crearMonedas.clear();
            }
            if (!modificarMonedas.isEmpty()) {
                administrarMonedas.modificarMonedas(modificarMonedas);
                modificarMonedas.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMonedas = null;
            context.update("form:datosMoneda");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarMoneda = listMonedas.get(index);
            }
            if (tipoLista == 1) {
                editarMoneda = filtrarMonedas.get(index);
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

    public void agregarNuevoMonedas() {
        System.out.println("agregarNuevoMonedas");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMoneda.getCodigo() == (null)) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMoneda.getCodigo());

            for (int x = 0; x < listMonedas.size(); x++) {
                if (listMonedas.get(x).getCodigo().equals(nuevoMoneda.getCodigo())) {
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
        if (nuevoMoneda.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Moneda \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMoneda");
                bandera = 0;
                filtrarMonedas = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoMoneda.setSecuencia(l);

            crearMonedas.add(nuevoMoneda);

            listMonedas.add(nuevoMoneda);
            nuevoMoneda = new Monedas();
            context.update("form:datosMoneda");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroMoneda.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMonedas() {
        System.out.println("limpiarNuevoMonedas");
        nuevoMoneda = new Monedas();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoMonedas() {
        System.out.println("duplicandoMonedas");
        if (index >= 0) {
            duplicarMoneda = new Monedas();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMoneda.setSecuencia(l);
                duplicarMoneda.setCodigo(listMonedas.get(index).getCodigo());
                duplicarMoneda.setNombre(listMonedas.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarMoneda.setSecuencia(l);
                duplicarMoneda.setCodigo(filtrarMonedas.get(index).getCodigo());
                duplicarMoneda.setNombre(filtrarMonedas.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarMO");
            context.execute("duplicarRegistroMoneda.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR MONEDAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();

        System.err.println("ConfirmarDuplicar codigo " + duplicarMoneda.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarMoneda.getNombre());

        if (duplicarMoneda.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMonedas.size(); x++) {
                if (listMonedas.get(x).getCodigo() == duplicarMoneda.getCodigo()) {
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
        if (duplicarMoneda.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Moneda \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMoneda.getSecuencia() + "  " + duplicarMoneda.getCodigo());
            if (crearMonedas.contains(duplicarMoneda)) {
                System.out.println("Ya lo contengo.");
            }
            listMonedas.add(duplicarMoneda);
            crearMonedas.add(duplicarMoneda);
            context.update("form:datosMoneda");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosMoneda:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMoneda");
                bandera = 0;
                filtrarMonedas = null;
                tipoLista = 0;
            }
            duplicarMoneda = new Monedas();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMoneda.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarMonedas() {
        duplicarMoneda = new Monedas();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMonedaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MONEDAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMonedaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MONEDAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listMonedas.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "MONEDAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MONEDAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Monedas> getListMonedas() {
        if (listMonedas == null) {
            listMonedas = administrarMonedas.consultarMonedas();
        }
        return listMonedas;
    }

    public void setListMonedas(List<Monedas> listMonedas) {
        this.listMonedas = listMonedas;
    }

    public List<Monedas> getModificarMonedas() {
        return modificarMonedas;
    }

    public void setModificarMonedas(List<Monedas> modificarMonedas) {
        this.modificarMonedas = modificarMonedas;
    }

    public Monedas getNuevoMoneda() {
        return nuevoMoneda;
    }

    public void setNuevoMoneda(Monedas nuevoMoneda) {
        this.nuevoMoneda = nuevoMoneda;
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

    public List<Monedas> getFiltrarMonedas() {
        return filtrarMonedas;
    }

    public void setFiltrarMonedas(List<Monedas> filtrarMonedas) {
        this.filtrarMonedas = filtrarMonedas;
    }

    public Monedas getEditarMoneda() {
        return editarMoneda;
    }

    public void setEditarMoneda(Monedas editarMoneda) {
        this.editarMoneda = editarMoneda;
    }

    public Monedas getDuplicarMoneda() {
        return duplicarMoneda;
    }

    public void setDuplicarMoneda(Monedas duplicarMoneda) {
        this.duplicarMoneda = duplicarMoneda;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

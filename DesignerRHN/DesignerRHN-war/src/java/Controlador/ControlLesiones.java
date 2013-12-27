/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Lesiones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarLesionesInterface;
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
public class ControlLesiones implements Serializable {

    @EJB
    AdministrarLesionesInterface administrarLesiones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Lesiones> listLesiones;
    private List<Lesiones> filtrarLesiones;
    private List<Lesiones> crearLesiones;
    private List<Lesiones> modificarLesiones;
    private List<Lesiones> borrarLesiones;
    private Lesiones nuevaLesion;
    private Lesiones duplicarLesion;
    private Lesiones editarLesion;
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
    private BigDecimal detallesLicensias;
    private BigDecimal soAccidentesDomesticos;

    public ControlLesiones() {
        listLesiones = null;
        crearLesiones = new ArrayList<Lesiones>();
        modificarLesiones = new ArrayList<Lesiones>();
        borrarLesiones = new ArrayList<Lesiones>();
        permitirIndex = true;
        editarLesion = new Lesiones();
        nuevaLesion = new Lesiones();
        duplicarLesion = new Lesiones();
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n EVENTO FILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR EVENTO FILTRAR ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listLesiones.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLESIONES  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLLESIONES asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datoslesion");
            bandera = 0;
            filtrarLesiones = null;
            tipoLista = 0;
        }

        borrarLesiones.clear();
        crearLesiones.clear();
        modificarLesiones.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listLesiones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datoslesion");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:codigo");
            codigo.setFilterStyle("width: 360px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datoslesion");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datoslesion");
            bandera = 0;
            filtrarLesiones = null;
            tipoLista = 0;
        }
    }

    public void modificandoLesion(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR LESIONES");
        index = indice;

        int contador = 0;
        int contador1 = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO LESIONES  CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearLesiones.contains(listLesiones.get(indice))) {
                    if (listLesiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listLesiones.size(); j++) {
                            if (j != indice) {
                                if (listLesiones.get(indice).getCodigo() == listLesiones.get(j).getCodigo()) {
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
                    if (listLesiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listLesiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarLesiones.isEmpty()) {
                            modificarLesiones.add(listLesiones.get(indice));
                        } else if (!modificarLesiones.contains(listLesiones.get(indice))) {
                            modificarLesiones.add(listLesiones.get(indice));
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

                if (!crearLesiones.contains(filtrarLesiones.get(indice))) {
                    if (filtrarLesiones.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        System.err.println("indice filtrar indice : " + filtrarLesiones.get(indice).getCodigo());
                        for (int j = 0; j < listLesiones.size(); j++) {
                            System.err.println("indice lista  indice : " + listLesiones.get(j).getCodigo());
                            if (filtrarLesiones.get(indice).getCodigo() == listLesiones.get(j).getCodigo()) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarLesiones.size(); j++) {
                            System.err.println("indice filtrar indice : " + filtrarLesiones.get(j).getCodigo());
                            if (j == indice) {
                                if (filtrarLesiones.get(indice).getCodigo() == filtrarLesiones.get(j).getCodigo()) {
                                    contador1++;
                                }
                            }
                        }
                        System.err.println("contador es igual a " + contador);
                        System.err.println("contador1 es igual a " + contador1);

                        if (contador > 0 || contador1 > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (filtrarLesiones.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarLesiones.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarLesiones.isEmpty()) {
                            modificarLesiones.add(filtrarLesiones.get(indice));
                        } else if (!modificarLesiones.contains(filtrarLesiones.get(indice))) {
                            modificarLesiones.add(filtrarLesiones.get(indice));
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
            context.update("form:datoslesion");
        }

    }

    public void borrandoLesiones() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrarandoLesiones");
                if (!modificarLesiones.isEmpty() && modificarLesiones.contains(listLesiones.get(index))) {
                    int modIndex = modificarLesiones.indexOf(listLesiones.get(index));
                    modificarLesiones.remove(modIndex);
                    borrarLesiones.add(listLesiones.get(index));
                } else if (!crearLesiones.isEmpty() && crearLesiones.contains(listLesiones.get(index))) {
                    int crearIndex = crearLesiones.indexOf(listLesiones.get(index));
                    crearLesiones.remove(crearIndex);
                } else {
                    borrarLesiones.add(listLesiones.get(index));
                }
                listLesiones.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarandoLesiones");
                if (!modificarLesiones.isEmpty() && modificarLesiones.contains(filtrarLesiones.get(index))) {
                    int modIndex = modificarLesiones.indexOf(filtrarLesiones.get(index));
                    modificarLesiones.remove(modIndex);
                    borrarLesiones.add(filtrarLesiones.get(index));
                } else if (!crearLesiones.isEmpty() && crearLesiones.contains(filtrarLesiones.get(index))) {
                    int crearIndex = crearLesiones.indexOf(filtrarLesiones.get(index));
                    crearLesiones.remove(crearIndex);
                } else {
                    borrarLesiones.add(filtrarLesiones.get(index));
                }
                int VCIndex = listLesiones.indexOf(filtrarLesiones.get(index));
                listLesiones.remove(VCIndex);
                filtrarLesiones.remove(index);

            }
            context.update("form:datoslesion");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("verificarBorrado");
        try {
            detallesLicensias = administrarLesiones.verificarBorradoDetallesLicensias(listLesiones.get(index).getSecuencia());
            soAccidentesDomesticos = administrarLesiones.verificarBorradoSoAccidentesDomesticos(listLesiones.get(index).getSecuencia());
            if (detallesLicensias.intValueExact() == 0 && soAccidentesDomesticos.intValueExact() == 0) {
                System.out.println("Borrado==0");
                borrandoLesiones();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                detallesLicensias = new BigDecimal(-1);
                soAccidentesDomesticos = new BigDecimal(-1);
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarLesiones.isEmpty() || !crearLesiones.isEmpty() || !modificarLesiones.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoLesiones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando TipoCertificados");
            if (!borrarLesiones.isEmpty()) {
                for (int i = 0; i < borrarLesiones.size(); i++) {
                    System.out.println("Borrando...");
                    administrarLesiones.borrarLesiones(borrarLesiones.get(i));
                }
                //mostrarBorrados
                registrosBorrados = borrarLesiones.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarLesiones.clear();
            }
            if (!crearLesiones.isEmpty()) {
                for (int i = 0; i < crearLesiones.size(); i++) {

                    System.out.println("Creando...");
                    administrarLesiones.crearLesiones(crearLesiones.get(i));

                }
                crearLesiones.clear();
            }
            if (!modificarLesiones.isEmpty()) {
                administrarLesiones.modificarLesiones(modificarLesiones);
                modificarLesiones.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listLesiones = null;
            context.update("form:datoslesion");
            k = 0;
            if (guardado == false) {
                guardado = true;
            }
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarLesion = listLesiones.get(index);
            }
            if (tipoLista == 1) {
                editarLesion = filtrarLesiones.get(index);
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

    public void agregarNuevaLesion() {
        System.out.println("agregarNuevaLesion");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaLesion.getCodigo() == a) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaLesion.getCodigo());

            for (int x = 0; x < listLesiones.size(); x++) {
                if (listLesiones.get(x).getCodigo().equals(nuevaLesion.getCodigo())) {
                    System.out.println("tengo un duplicado");
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
        if (nuevaLesion.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una descripcion \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datoslesion");
                bandera = 0;
                filtrarLesiones = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevaLesion.setSecuencia(l);

            crearLesiones.add(nuevaLesion);

            listLesiones.add(nuevaLesion);
            nuevaLesion = new Lesiones();
            context.update("form:datoslesion");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroLesion.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoLesiones() {
        System.out.println("limpiarNuevoLesiones");
        nuevaLesion = new Lesiones();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoLesiones() {
        System.out.println("duplicandoTiposCertificados");
        if (index >= 0) {
            duplicarLesion = new Lesiones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarLesion.setSecuencia(l);
                duplicarLesion.setCodigo(listLesiones.get(index).getCodigo());
                duplicarLesion.setDescripcion(listLesiones.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarLesion.setSecuencia(l);
                duplicarLesion.setCodigo(filtrarLesiones.get(index).getCodigo());
                duplicarLesion.setDescripcion(filtrarLesiones.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarLl");
            context.execute("duplicarRegistroLesion.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR LESIONES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarLesion.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarLesion.getDescripcion());

        if (duplicarLesion.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listLesiones.size(); x++) {
                if (listLesiones.get(x).getCodigo().equals(duplicarLesion.getCodigo())) {
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
        if (duplicarLesion.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarLesion.getSecuencia() + "  " + duplicarLesion.getCodigo());
            if (crearLesiones.contains(duplicarLesion)) {
                System.out.println("Ya lo contengo.");
            }
            listLesiones.add(duplicarLesion);
            crearLesiones.add(duplicarLesion);
            context.update("form:datoslesion");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datoslesion:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datoslesion");
                bandera = 0;
                filtrarLesiones = null;
                tipoLista = 0;
            }
            duplicarLesion = new Lesiones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroLesion.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarLesiones() {
        duplicarLesion = new Lesiones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datoslesionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "LESIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datoslesionExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "LESIONES", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listLesiones.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "LESIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("LESIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<Lesiones> getListLesiones() {
        if (listLesiones == null) {
            listLesiones = administrarLesiones.mostrarLesiones();
        }
        return listLesiones;
    }

    public void setListLesiones(List<Lesiones> listLesiones) {
        this.listLesiones = listLesiones;
    }

    public List<Lesiones> getFiltrarLesiones() {
        return filtrarLesiones;
    }

    public void setFiltrarLesiones(List<Lesiones> filtrarLesiones) {
        this.filtrarLesiones = filtrarLesiones;
    }

    public Lesiones getNuevaLesion() {
        return nuevaLesion;
    }

    public void setNuevaLesion(Lesiones nuevaLesion) {
        this.nuevaLesion = nuevaLesion;
    }

    public Lesiones getDuplicarLesion() {
        return duplicarLesion;
    }

    public void setDuplicarLesion(Lesiones duplicarLesion) {
        this.duplicarLesion = duplicarLesion;
    }

    public Lesiones getEditarLesion() {
        return editarLesion;
    }

    public void setEditarLesion(Lesiones editarLesion) {
        this.editarLesion = editarLesion;
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

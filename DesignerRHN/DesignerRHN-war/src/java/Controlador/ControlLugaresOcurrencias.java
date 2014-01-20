/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.LugaresOcurrencias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarLugaresOcurrenciasInterface;
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
public class ControlLugaresOcurrencias implements Serializable {

    @EJB
    AdministrarLugaresOcurrenciasInterface administrarLugaresOcurrencias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<LugaresOcurrencias> listLugaresOcurrencias;
    private List<LugaresOcurrencias> filtrarLugaresOcurrencias;
    private List<LugaresOcurrencias> crearLugaresOcurrencias;
    private List<LugaresOcurrencias> modificarLugaresOcurrencias;
    private List<LugaresOcurrencias> borrarLugaresOcurrencias;
    private LugaresOcurrencias nuevoLugarOcurrencia;
    private LugaresOcurrencias duplicarLugareOcurrencia;
    private LugaresOcurrencias editarLugarOcurrencia;
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

    public ControlLugaresOcurrencias() {
        listLugaresOcurrencias = null;
        crearLugaresOcurrencias = new ArrayList<LugaresOcurrencias>();
        modificarLugaresOcurrencias = new ArrayList<LugaresOcurrencias>();
        borrarLugaresOcurrencias = new ArrayList<LugaresOcurrencias>();
        permitirIndex = true;
        editarLugarOcurrencia = new LugaresOcurrencias();
        nuevoLugarOcurrencia = new LugaresOcurrencias();
        duplicarLugareOcurrencia = new LugaresOcurrencias();
        guardado = true;
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
            secRegistro = listLugaresOcurrencias.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLLUGARESOCURRENCIAS  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLLUGARESOCURRENCIAS asignarIndex ERROR " + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSitioOcurrencia");
            bandera = 0;
            filtrarLugaresOcurrencias = null;
            tipoLista = 0;
        }

        borrarLugaresOcurrencias.clear();
        crearLugaresOcurrencias.clear();
        modificarLugaresOcurrencias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listLugaresOcurrencias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datosSitioOcurrencia");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:codigo");
            codigo.setFilterStyle("width: 360px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosSitioOcurrencia");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSitioOcurrencia");
            bandera = 0;
            filtrarLugaresOcurrencias = null;
            tipoLista = 0;
        }
    }

    public void modificandoLugarOcurrencia(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR LUGARES OCURRENCIA");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO LUGARES OCURRENCIAS  CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearLugaresOcurrencias.contains(listLugaresOcurrencias.get(indice))) {
                    if (listLugaresOcurrencias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listLugaresOcurrencias.size(); j++) {
                            if (j != indice) {
                                if (listLugaresOcurrencias.get(indice).getCodigo() == listLugaresOcurrencias.get(j).getCodigo()) {
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
                    if (listLugaresOcurrencias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listLugaresOcurrencias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarLugaresOcurrencias.isEmpty()) {
                            modificarLugaresOcurrencias.add(listLugaresOcurrencias.get(indice));
                        } else if (!modificarLugaresOcurrencias.contains(listLugaresOcurrencias.get(indice))) {
                            modificarLugaresOcurrencias.add(listLugaresOcurrencias.get(indice));
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

                if (!crearLugaresOcurrencias.contains(filtrarLugaresOcurrencias.get(indice))) {
                    if (filtrarLugaresOcurrencias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listLugaresOcurrencias.size(); j++) {
                            System.err.println("indice lista  indice : " + listLugaresOcurrencias.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarLugaresOcurrencias.get(indice).getCodigo() == listLugaresOcurrencias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }

                        for (int j = 0; j < filtrarLugaresOcurrencias.size(); j++) {
                            System.err.println("indice filtrar indice : " + filtrarLugaresOcurrencias.get(j).getCodigo());
                            if (j != indice) {
                                if (filtrarLugaresOcurrencias.get(indice).getCodigo() == filtrarLugaresOcurrencias.get(j).getCodigo()) {
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

                    if (filtrarLugaresOcurrencias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarLugaresOcurrencias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarLugaresOcurrencias.isEmpty()) {
                            modificarLugaresOcurrencias.add(filtrarLugaresOcurrencias.get(indice));
                        } else if (!modificarLugaresOcurrencias.contains(filtrarLugaresOcurrencias.get(indice))) {
                            modificarLugaresOcurrencias.add(filtrarLugaresOcurrencias.get(indice));
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
            context.update("form:datosSitioOcurrencia");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoLugaresOcurrencias() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrandoLugaresOcurrencias");
                if (!modificarLugaresOcurrencias.isEmpty() && modificarLugaresOcurrencias.contains(listLugaresOcurrencias.get(index))) {
                    int modIndex = modificarLugaresOcurrencias.indexOf(listLugaresOcurrencias.get(index));
                    modificarLugaresOcurrencias.remove(modIndex);
                    borrarLugaresOcurrencias.add(listLugaresOcurrencias.get(index));
                } else if (!crearLugaresOcurrencias.isEmpty() && crearLugaresOcurrencias.contains(listLugaresOcurrencias.get(index))) {
                    int crearIndex = crearLugaresOcurrencias.indexOf(listLugaresOcurrencias.get(index));
                    crearLugaresOcurrencias.remove(crearIndex);
                } else {
                    borrarLugaresOcurrencias.add(listLugaresOcurrencias.get(index));
                }
                listLugaresOcurrencias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoLugaresOcurrencias");
                if (!modificarLugaresOcurrencias.isEmpty() && modificarLugaresOcurrencias.contains(filtrarLugaresOcurrencias.get(index))) {
                    int modIndex = modificarLugaresOcurrencias.indexOf(filtrarLugaresOcurrencias.get(index));
                    modificarLugaresOcurrencias.remove(modIndex);
                    borrarLugaresOcurrencias.add(filtrarLugaresOcurrencias.get(index));
                } else if (!crearLugaresOcurrencias.isEmpty() && crearLugaresOcurrencias.contains(filtrarLugaresOcurrencias.get(index))) {
                    int crearIndex = crearLugaresOcurrencias.indexOf(filtrarLugaresOcurrencias.get(index));
                    crearLugaresOcurrencias.remove(crearIndex);
                } else {
                    borrarLugaresOcurrencias.add(filtrarLugaresOcurrencias.get(index));
                }
                int VCIndex = listLugaresOcurrencias.indexOf(filtrarLugaresOcurrencias.get(index));
                listLugaresOcurrencias.remove(VCIndex);
                filtrarLugaresOcurrencias.remove(index);

            }
            context.update("form:datosSitioOcurrencia");
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
        BigInteger soAccidentes;

        try {
            if (tipoLista == 1) {
                soAccidentes = administrarLugaresOcurrencias.verificarSoAccidentesLugarOcurrencia(listLugaresOcurrencias.get(index).getSecuencia());
            } else {
                soAccidentes = administrarLugaresOcurrencias.verificarSoAccidentesLugarOcurrencia(filtrarLugaresOcurrencias.get(index).getSecuencia());
            }
            if (soAccidentes.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrandoLugaresOcurrencias();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                soAccidentes = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarLugaresOcurrencias.isEmpty() || !crearLugaresOcurrencias.isEmpty() || !modificarLugaresOcurrencias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoLugaresOcurrencias() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando LUGARES OCURRENCIAS");
            if (!borrarLugaresOcurrencias.isEmpty()) {
                administrarLugaresOcurrencias.borrarLugarOcurrencia(borrarLugaresOcurrencias);

                //mostrarBorrados
                registrosBorrados = borrarLugaresOcurrencias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarLugaresOcurrencias.clear();
            }
            if (!crearLugaresOcurrencias.isEmpty()) {
                administrarLugaresOcurrencias.crearLugarOcurrencia(crearLugaresOcurrencias);

                crearLugaresOcurrencias.clear();
            }
            if (!modificarLugaresOcurrencias.isEmpty()) {
                administrarLugaresOcurrencias.modificarLesiones(modificarLugaresOcurrencias);
                modificarLugaresOcurrencias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listLugaresOcurrencias = null;
            context.update("form:datosSitioOcurrencia");
            k = 0;
            guardado = true;

        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarLugarOcurrencia = listLugaresOcurrencias.get(index);
            }
            if (tipoLista == 1) {
                editarLugarOcurrencia = filtrarLugaresOcurrencias.get(index);
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

    public void agregarNuevoLugarOcurrencia() {
        System.out.println("agregarNuevoLugarOcurrencia");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoLugarOcurrencia.getCodigo() == a) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoLugarOcurrencia.getCodigo());

            for (int x = 0; x < listLugaresOcurrencias.size(); x++) {
                if (listLugaresOcurrencias.get(x).getCodigo() == nuevoLugarOcurrencia.getCodigo()) {
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
        if (nuevoLugarOcurrencia.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSitioOcurrencia");
                bandera = 0;
                filtrarLugaresOcurrencias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoLugarOcurrencia.setSecuencia(l);

            crearLugaresOcurrencias.add(nuevoLugarOcurrencia);

            listLugaresOcurrencias.add(nuevoLugarOcurrencia);
            nuevoLugarOcurrencia = new LugaresOcurrencias();
            context.update("form:datosSitioOcurrencia");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroSitioOcurrencia.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoLugarOcurrencia() {
        System.out.println("limpiarNuevoLugarOcurrencia");
        nuevoLugarOcurrencia = new LugaresOcurrencias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoLugarOcurrencia() {
        System.out.println("duplicandoLugarOcurrencia");
        if (index >= 0) {
            duplicarLugareOcurrencia = new LugaresOcurrencias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarLugareOcurrencia.setSecuencia(l);
                duplicarLugareOcurrencia.setCodigo(listLugaresOcurrencias.get(index).getCodigo());
                duplicarLugareOcurrencia.setDescripcion(listLugaresOcurrencias.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarLugareOcurrencia.setSecuencia(l);
                duplicarLugareOcurrencia.setCodigo(filtrarLugaresOcurrencias.get(index).getCodigo());
                duplicarLugareOcurrencia.setDescripcion(filtrarLugaresOcurrencias.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarSO");
            context.execute("duplicarRegistroSitioOcurrencia.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR LUGARES OCURRENCIA");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarLugareOcurrencia.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarLugareOcurrencia.getDescripcion());

        if (duplicarLugareOcurrencia.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listLugaresOcurrencias.size(); x++) {
                if (listLugaresOcurrencias.get(x).getCodigo() == duplicarLugareOcurrencia.getCodigo()) {
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
        if (duplicarLugareOcurrencia.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarLugareOcurrencia.getSecuencia() + "  " + duplicarLugareOcurrencia.getCodigo());
            if (crearLugaresOcurrencias.contains(duplicarLugareOcurrencia)) {
                System.out.println("Ya lo contengo.");
            }
            listLugaresOcurrencias.add(duplicarLugareOcurrencia);
            crearLugaresOcurrencias.add(duplicarLugareOcurrencia);
            context.update("form:datosSitioOcurrencia");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSitioOcurrencia:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosSitioOcurrencia");
                bandera = 0;
                filtrarLugaresOcurrencias = null;
                tipoLista = 0;
            }
            duplicarLugareOcurrencia = new LugaresOcurrencias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroSitioOcurrencia.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarLugaresOcurrencia() {
        duplicarLugareOcurrencia = new LugaresOcurrencias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSitioOcurrenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "SITIOOCURENCIA", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSitioOcurrenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "SITIOOCURENCIA", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listLugaresOcurrencias.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "LUGARESOCURRENCIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("LUGARESOCURRENCIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<LugaresOcurrencias> getListLugaresOcurrencias() {
        if (listLugaresOcurrencias == null) {
            listLugaresOcurrencias = administrarLugaresOcurrencias.consultarLugaresOcurrencias();
        }
        return listLugaresOcurrencias;
    }

    public void setListLugaresOcurrencias(List<LugaresOcurrencias> listLugaresOcurrencias) {
        this.listLugaresOcurrencias = listLugaresOcurrencias;
    }

    public List<LugaresOcurrencias> getFiltrarLugaresOcurrencias() {
        return filtrarLugaresOcurrencias;
    }

    public void setFiltrarLugaresOcurrencias(List<LugaresOcurrencias> filtrarLugaresOcurrencias) {
        this.filtrarLugaresOcurrencias = filtrarLugaresOcurrencias;
    }

    public LugaresOcurrencias getNuevoLugarOcurrencia() {
        return nuevoLugarOcurrencia;
    }

    public void setNuevoLugarOcurrencia(LugaresOcurrencias nuevoLugarOcurrencia) {
        this.nuevoLugarOcurrencia = nuevoLugarOcurrencia;
    }

    public LugaresOcurrencias getDuplicarLugareOcurrencia() {
        return duplicarLugareOcurrencia;
    }

    public void setDuplicarLugareOcurrencia(LugaresOcurrencias duplicarLugareOcurrencia) {
        this.duplicarLugareOcurrencia = duplicarLugareOcurrencia;
    }

    public LugaresOcurrencias getEditarLugarOcurrencia() {
        return editarLugarOcurrencia;
    }

    public void setEditarLugarOcurrencia(LugaresOcurrencias editarLugarOcurrencia) {
        this.editarLugarOcurrencia = editarLugarOcurrencia;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
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

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.PartesCuerpo;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarPartesCuerpoInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
public class ControlPartesCuerpo implements Serializable {

    @EJB
    AdministrarPartesCuerpoInterface administrarPartesCuerpo;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<PartesCuerpo> listPartesCuerpo;
    private List<PartesCuerpo> filtrarPartesCuerpo;
    private List<PartesCuerpo> crearPartesCuerpo;
    private List<PartesCuerpo> modificarPartesCuerpo;
    private List<PartesCuerpo> borrarPartesCuerpo;
    private PartesCuerpo nuevaParteCuerpo;
    private PartesCuerpo duplicarParteCuerpo;
    private PartesCuerpo editarParteCuerpo;
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
    private BigInteger verificarBorradoDetallesExamenes;
    private BigInteger verificarBorradoSoDetallesRevisiones;
    private BigInteger verificarSoAccidentesMedicos;

    public ControlPartesCuerpo() {
        listPartesCuerpo = null;
        crearPartesCuerpo = new ArrayList<PartesCuerpo>();
        modificarPartesCuerpo = new ArrayList<PartesCuerpo>();
        borrarPartesCuerpo = new ArrayList<PartesCuerpo>();
        permitirIndex = true;
        editarParteCuerpo = new PartesCuerpo();
        nuevaParteCuerpo = new PartesCuerpo();
        duplicarParteCuerpo = new PartesCuerpo();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarPartesCuerpo.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
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
            secRegistro = listPartesCuerpo.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE CONTROLPARTESCUERPO  AsignarIndex \n");
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
            System.out.println("ERROR CONTROLPARTESCUERPO asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPartesCuerpo");
            bandera = 0;
            filtrarPartesCuerpo = null;
            tipoLista = 0;
        }

        borrarPartesCuerpo.clear();
        crearPartesCuerpo.clear();
        modificarPartesCuerpo.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listPartesCuerpo = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        context.update("form:datosPartesCuerpo");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:codigo");
            codigo.setFilterStyle("width: 360px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosPartesCuerpo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosPartesCuerpo");
            bandera = 0;
            filtrarPartesCuerpo = null;
            tipoLista = 0;
        }
    }

    public void modificandoParteCuerpo(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("MODIFICAR ELEMENTOS PARTE CUERPO");
        index = indice;

        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("MODIFICANDO PARTE CUERPO  CONFIRMAR CAMBIO = N");
            if (tipoLista == 0) {
                if (!crearPartesCuerpo.contains(listPartesCuerpo.get(indice))) {
                    if (listPartesCuerpo.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPartesCuerpo.size(); j++) {
                            if (j != indice) {
                                if (listPartesCuerpo.get(indice).getCodigo() == listPartesCuerpo.get(j).getCodigo()) {
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
                    if (listPartesCuerpo.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listPartesCuerpo.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarPartesCuerpo.isEmpty()) {
                            modificarPartesCuerpo.add(listPartesCuerpo.get(indice));
                        } else if (!modificarPartesCuerpo.contains(listPartesCuerpo.get(indice))) {
                            modificarPartesCuerpo.add(listPartesCuerpo.get(indice));
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

                if (!crearPartesCuerpo.contains(filtrarPartesCuerpo.get(indice))) {
                    if (filtrarPartesCuerpo.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listPartesCuerpo.size(); j++) {
                            if (filtrarPartesCuerpo.get(indice).getCodigo() == listPartesCuerpo.get(j).getCodigo()) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarPartesCuerpo.size(); j++) {
                            if (j != indice) {
                                if (filtrarPartesCuerpo.get(indice).getCodigo() == filtrarPartesCuerpo.get(j).getCodigo()) {
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

                    if (filtrarPartesCuerpo.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarPartesCuerpo.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarPartesCuerpo.isEmpty()) {
                            modificarPartesCuerpo.add(filtrarPartesCuerpo.get(indice));
                        } else if (!modificarPartesCuerpo.contains(filtrarPartesCuerpo.get(indice))) {
                            modificarPartesCuerpo.add(filtrarPartesCuerpo.get(indice));
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
            context.update("form:datosPartesCuerpo");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoParteCuerpo() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("borrandoParteCuerpo");
                if (!modificarPartesCuerpo.isEmpty() && modificarPartesCuerpo.contains(listPartesCuerpo.get(index))) {
                    int modIndex = modificarPartesCuerpo.indexOf(listPartesCuerpo.get(index));
                    modificarPartesCuerpo.remove(modIndex);
                    borrarPartesCuerpo.add(listPartesCuerpo.get(index));
                } else if (!crearPartesCuerpo.isEmpty() && crearPartesCuerpo.contains(listPartesCuerpo.get(index))) {
                    int crearIndex = crearPartesCuerpo.indexOf(listPartesCuerpo.get(index));
                    crearPartesCuerpo.remove(crearIndex);
                } else {
                    borrarPartesCuerpo.add(listPartesCuerpo.get(index));
                }
                listPartesCuerpo.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoParteCuerpo");
                if (!modificarPartesCuerpo.isEmpty() && modificarPartesCuerpo.contains(filtrarPartesCuerpo.get(index))) {
                    int modIndex = modificarPartesCuerpo.indexOf(filtrarPartesCuerpo.get(index));
                    modificarPartesCuerpo.remove(modIndex);
                    borrarPartesCuerpo.add(filtrarPartesCuerpo.get(index));
                } else if (!crearPartesCuerpo.isEmpty() && crearPartesCuerpo.contains(filtrarPartesCuerpo.get(index))) {
                    int crearIndex = crearPartesCuerpo.indexOf(filtrarPartesCuerpo.get(index));
                    crearPartesCuerpo.remove(crearIndex);
                } else {
                    borrarPartesCuerpo.add(filtrarPartesCuerpo.get(index));
                }
                int VCIndex = listPartesCuerpo.indexOf(filtrarPartesCuerpo.get(index));
                listPartesCuerpo.remove(VCIndex);
                filtrarPartesCuerpo.remove(index);

            }
            context.update("form:datosPartesCuerpo");
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
            verificarBorradoDetallesExamenes = administrarPartesCuerpo.contarDetallesExamenesParteCuerpo(listPartesCuerpo.get(index).getSecuencia());
            verificarBorradoSoDetallesRevisiones = administrarPartesCuerpo.contarSoDetallesRevisionesParteCuerpo(listPartesCuerpo.get(index).getSecuencia());
            verificarSoAccidentesMedicos = administrarPartesCuerpo.contarSoAccidentesMedicosParteCuerpo(listPartesCuerpo.get(index).getSecuencia());
            if (verificarBorradoDetallesExamenes.equals(0) && verificarBorradoSoDetallesRevisiones.equals(0) && verificarSoAccidentesMedicos.equals(0)) {
                System.out.println("Borrado==0");
                borrandoParteCuerpo();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarBorradoDetallesExamenes = new BigInteger("-1");
                verificarBorradoSoDetallesRevisiones = new BigInteger("-1");
                verificarSoAccidentesMedicos = new BigInteger("-1");
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarPartesCuerpo.isEmpty() || !crearPartesCuerpo.isEmpty() || !modificarPartesCuerpo.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoPartesCuerpo() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando PARTES CUERPO");
            if (!borrarPartesCuerpo.isEmpty()) {
                administrarPartesCuerpo.borrarPartesCuerpo(borrarPartesCuerpo);

                //mostrarBorrados
                registrosBorrados = borrarPartesCuerpo.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarPartesCuerpo.clear();
            }
            if (!crearPartesCuerpo.isEmpty()) {
                administrarPartesCuerpo.crearPartesCuerpo(crearPartesCuerpo);
                crearPartesCuerpo.clear();
            }
            if (!modificarPartesCuerpo.isEmpty()) {
                administrarPartesCuerpo.modificarPartesCuerpo(modificarPartesCuerpo);
                modificarPartesCuerpo.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listPartesCuerpo = null;
            context.update("form:datosPartesCuerpo");
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
                editarParteCuerpo = listPartesCuerpo.get(index);
            }
            if (tipoLista == 1) {
                editarParteCuerpo = filtrarPartesCuerpo.get(index);
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

    public void agregarNuevaParteCuerpo() {
        System.out.println("agregarNuevaParteCuerpo");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaParteCuerpo.getCodigo() == a) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevaParteCuerpo.getCodigo());

            for (int x = 0; x < listPartesCuerpo.size(); x++) {
                if (listPartesCuerpo.get(x).getCodigo() == nuevaParteCuerpo.getCodigo()) {
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
        if (nuevaParteCuerpo.getDescripcion() == (null)) {
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPartesCuerpo");
                bandera = 0;
                filtrarPartesCuerpo = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevaParteCuerpo.setSecuencia(l);

            crearPartesCuerpo.add(nuevaParteCuerpo);

            listPartesCuerpo.add(nuevaParteCuerpo);
            nuevaParteCuerpo = new PartesCuerpo();
            context.update("form:datosPartesCuerpo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroPartesCuerpo.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevaParteCuerpo() {
        System.out.println("limpiarNuevaParteCuerpo");
        nuevaParteCuerpo = new PartesCuerpo();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoParteCuerpo() {
        System.out.println("duplicandoParteCuerpo");
        if (index >= 0) {
            duplicarParteCuerpo = new PartesCuerpo();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarParteCuerpo.setSecuencia(l);
                duplicarParteCuerpo.setCodigo(listPartesCuerpo.get(index).getCodigo());
                duplicarParteCuerpo.setDescripcion(listPartesCuerpo.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarParteCuerpo.setSecuencia(l);
                duplicarParteCuerpo.setCodigo(filtrarPartesCuerpo.get(index).getCodigo());
                duplicarParteCuerpo.setDescripcion(filtrarPartesCuerpo.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarPC");
            context.execute("duplicarRegistroPartesCuerpo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("CONFIRMAR DUPLICAR PARTE CUERPO");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarParteCuerpo.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarParteCuerpo.getDescripcion());

        if (duplicarParteCuerpo.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listPartesCuerpo.size(); x++) {
                if (listPartesCuerpo.get(x).getCodigo() == duplicarParteCuerpo.getCodigo()) {
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
        if (duplicarParteCuerpo.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarParteCuerpo.getSecuencia() + "  " + duplicarParteCuerpo.getCodigo());
            if (crearPartesCuerpo.contains(duplicarParteCuerpo)) {
                System.out.println("Ya lo contengo.");
            }
            listPartesCuerpo.add(duplicarParteCuerpo);
            crearPartesCuerpo.add(duplicarParteCuerpo);
            context.update("form:datosPartesCuerpo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosPartesCuerpo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosPartesCuerpo");
                bandera = 0;
                filtrarPartesCuerpo = null;
                tipoLista = 0;
            }
            duplicarParteCuerpo = new PartesCuerpo();
            RequestContext.getCurrentInstance().execute("duplicarRegistroPartesCuerpo.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarPartesCuerpo() {
        duplicarParteCuerpo = new PartesCuerpo();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPartesCuerpoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "PARTESCUERPO", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosPartesCuerpoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "PARTESCUERPO", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listPartesCuerpo.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PARTESCUERPO"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("PARTESCUERPO")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<PartesCuerpo> getListPartesCuerpo() {
        if (listPartesCuerpo == null) {
            listPartesCuerpo = administrarPartesCuerpo.consultarPartesCuerpo();
        }
        return listPartesCuerpo;
    }

    public void setListPartesCuerpo(List<PartesCuerpo> listPartesCuerpo) {
        this.listPartesCuerpo = listPartesCuerpo;
    }

    public List<PartesCuerpo> getModificarPartesCuerpo() {
        return modificarPartesCuerpo;
    }

    public void setModificarPartesCuerpo(List<PartesCuerpo> modificarPartesCuerpo) {
        this.modificarPartesCuerpo = modificarPartesCuerpo;
    }

    public PartesCuerpo getNuevaParteCuerpo() {
        return nuevaParteCuerpo;
    }

    public void setNuevaParteCuerpo(PartesCuerpo nuevaParteCuerpo) {
        this.nuevaParteCuerpo = nuevaParteCuerpo;
    }

    public PartesCuerpo getDuplicarParteCuerpo() {
        return duplicarParteCuerpo;
    }

    public void setDuplicarParteCuerpo(PartesCuerpo duplicarParteCuerpo) {
        this.duplicarParteCuerpo = duplicarParteCuerpo;
    }

    public PartesCuerpo getEditarParteCuerpo() {
        return editarParteCuerpo;
    }

    public void setEditarParteCuerpo(PartesCuerpo editarParteCuerpo) {
        this.editarParteCuerpo = editarParteCuerpo;
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

    public List<PartesCuerpo> getFiltrarPartesCuerpo() {
        return filtrarPartesCuerpo;
    }

    public void setFiltrarPartesCuerpo(List<PartesCuerpo> filtrarPartesCuerpo) {
        this.filtrarPartesCuerpo = filtrarPartesCuerpo;
    }

}

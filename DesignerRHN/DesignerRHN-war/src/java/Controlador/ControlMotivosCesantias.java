/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.MotivosCesantias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarMotivosCesantiasInterface;
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
public class ControlMotivosCesantias implements Serializable {

    @EJB
    AdministrarMotivosCesantiasInterface administrarMotivosCesantias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<MotivosCesantias> listMotivosCesantias;
    private List<MotivosCesantias> filtrarMotivosCesantias;
    private List<MotivosCesantias> crearMotivosCesantias;
    private List<MotivosCesantias> modificarMotivosCesantias;
    private List<MotivosCesantias> borrarMotivosCesantias;
    private MotivosCesantias nuevoMotivoCesantia;
    private MotivosCesantias duplicarMotivoCesantia;
    private MotivosCesantias editarMotivoCesantia;
    private MotivosCesantias motivoCesantiaSeleccionado;
    //otros
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion, paginaAnterior, infoRegistro,altoTabla;
    private boolean activarLov;

    public ControlMotivosCesantias() {
        listMotivosCesantias = null;
        crearMotivosCesantias = new ArrayList<MotivosCesantias>();
        modificarMotivosCesantias = new ArrayList<MotivosCesantias>();
        borrarMotivosCesantias = new ArrayList<MotivosCesantias>();
        permitirIndex = true;
        editarMotivoCesantia = new MotivosCesantias();
        nuevoMotivoCesantia = new MotivosCesantias();
        duplicarMotivoCesantia = new MotivosCesantias();
        guardado = true;
        activarLov = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarMotivosCesantias.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPag(String pag) {
        paginaAnterior = pag;
        getListMotivosCesantias();
        contarRegistros();
        if (!listMotivosCesantias.isEmpty()) {
            motivoCesantiaSeleccionado = listMotivosCesantias.get(0);
        }
    }

    public String volverPagAnterior() {
        return paginaAnterior;
    }

    public void cambiarIndice(MotivosCesantias motivo, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            motivoCesantiaSeleccionado = motivo;
            cualCelda = celda;
            motivoCesantiaSeleccionado.getSecuencia();

        }
    }

    public void asignarIndex(MotivosCesantias motivo, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLMOTIVOSCESANTIAS ASIGNAR INDEX \n");
            motivoCesantiaSeleccionado = motivo;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("TIPO ACTUALIZACION : " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLMOTIVOSCESANTIAS ASIGNAR INDEX ERROR = " + e);
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarMotivosCesantias = null;
            tipoLista = 0;
        }

        borrarMotivosCesantias.clear();
        crearMotivosCesantias.clear();
        modificarMotivosCesantias.clear();
        motivoCesantiaSeleccionado = null;
        k = 0;
        listMotivosCesantias = null;
        getListMotivosCesantias();
        contarRegistros();
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("width: 85%");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            System.out.println("Activar");
            bandera = 1;
            altoTabla ="222";
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarMotivosCesantias = null;
            tipoLista = 0;
            altoTabla ="246";
        }
    }

    public void modificandoMotivoCensantia(MotivosCesantias motivo, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR MOTIVOSCESANTIA");
        motivoCesantiaSeleccionado = motivo;

        int contador = 0;
        int contadorGuardar = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR MOTIVOEMBARGOS, CONFIRMAR CAMBIO ES N");

            if (!crearMotivosCesantias.contains(motivoCesantiaSeleccionado)) {
                if (motivoCesantiaSeleccionado.getCodigo() == a) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                }
                if (motivoCesantiaSeleccionado.getNombre() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                } else if (motivoCesantiaSeleccionado.getNombre().equals("")) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                } else {
                    contadorGuardar++;
                }

                if (modificarMotivosCesantias.isEmpty()) {
                    modificarMotivosCesantias.add(motivoCesantiaSeleccionado);
                } else if (!modificarMotivosCesantias.contains(motivoCesantiaSeleccionado)) {
                    modificarMotivosCesantias.add(motivoCesantiaSeleccionado);
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
        }
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    private BigInteger verificarEerPrestamos;

    public void verificarBorrado() {
        try {
            System.out.println("ESTOY EN VERIFICAR BORRADO tipoLista " + tipoLista);
            System.out.println("secuencia borrado : " + motivoCesantiaSeleccionado.getSecuencia());
            if (tipoLista == 0) {
                System.out.println("secuencia borrado : " + motivoCesantiaSeleccionado.getSecuencia());
                verificarEerPrestamos = administrarMotivosCesantias.contarNovedadesSistemasMotivoCesantia(motivoCesantiaSeleccionado.getSecuencia());
            } else {
                System.out.println("secuencia borrado : " + motivoCesantiaSeleccionado.getSecuencia());
                verificarEerPrestamos = administrarMotivosCesantias.contarNovedadesSistemasMotivoCesantia(motivoCesantiaSeleccionado.getSecuencia());
            }
            if (!verificarEerPrestamos.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                motivoCesantiaSeleccionado = null;

                verificarEerPrestamos = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrandoMotivosCesantias();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void borrandoMotivosCesantias() {

        if (motivoCesantiaSeleccionado != null) {
            System.out.println("Entro a borrandoMotivosCesantias");
            if (!modificarMotivosCesantias.isEmpty() && modificarMotivosCesantias.contains(motivoCesantiaSeleccionado)) {
                int modIndex = modificarMotivosCesantias.indexOf(motivoCesantiaSeleccionado);
                modificarMotivosCesantias.remove(modIndex);
                borrarMotivosCesantias.add(motivoCesantiaSeleccionado);
            } else if (!crearMotivosCesantias.isEmpty() && crearMotivosCesantias.contains(motivoCesantiaSeleccionado)) {
                int crearIndex = crearMotivosCesantias.indexOf(motivoCesantiaSeleccionado);
                crearMotivosCesantias.remove(crearIndex);
            } else {
                borrarMotivosCesantias.add(motivoCesantiaSeleccionado);
            }
            listMotivosCesantias.remove(motivoCesantiaSeleccionado);
            if (tipoLista == 1) {
                filtrarMotivosCesantias.remove(motivoCesantiaSeleccionado);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
            motivoCesantiaSeleccionado = null;
            modificarInfoRegistro(listMotivosCesantias.size());

            if (guardado == true) {
                guardado = false;
            }
        } else {
            RequestContext.getCurrentInstance().execute("seleccionarRegistro.show()");
        }

    }

    public void revisarDialogoGuardar() {

        if (!borrarMotivosCesantias.isEmpty() || !crearMotivosCesantias.isEmpty() || !modificarMotivosCesantias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarMotivoCesantia() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("REALIZANDO MOTIVOCESANTIA");
            if (!borrarMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.borrarMotivosCesantias(borrarMotivosCesantias);
                //mostrarBorrados
                registrosBorrados = borrarMotivosCesantias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivosCesantias.clear();
            }
            if (!crearMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.crearMotivosCesantias(crearMotivosCesantias);

                crearMotivosCesantias.clear();
            }
            if (!modificarMotivosCesantias.isEmpty()) {
                administrarMotivosCesantias.modificarMotivosCesantias(modificarMotivosCesantias);
                modificarMotivosCesantias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosCesantias = null;
            guardado = true;
            context.update("form:datosTipoReemplazo");
            k = 0;
        }
        motivoCesantiaSeleccionado = null;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (motivoCesantiaSeleccionado != null) {
            if (tipoLista == 0) {
                editarMotivoCesantia = motivoCesantiaSeleccionado;
            }
            if (tipoLista == 1) {
                editarMotivoCesantia = motivoCesantiaSeleccionado;
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
        } else {
            RequestContext.getCurrentInstance().execute("seleccionarRegistro.show()");
        }
    }

    public void agregarNuevoMotivosCesantias() {
        System.out.println("agregarNuevoMotivosCesantias");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoMotivoCesantia.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoCesantia.getCodigo());

            for (int x = 0; x < listMotivosCesantias.size(); x++) {
                if (listMotivosCesantias.get(x).getCodigo() == nuevoMotivoCesantia.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoMotivoCesantia.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una descripción \n";
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
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarMotivosCesantias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoCesantia.setSecuencia(l);
            crearMotivosCesantias.add(nuevoMotivoCesantia);
            listMotivosCesantias.add(nuevoMotivoCesantia);
            motivoCesantiaSeleccionado = nuevoMotivoCesantia;
            modificarInfoRegistro(listMotivosCesantias.size());
            nuevoMotivoCesantia = new MotivosCesantias();
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposReemplazos.hide()");

        } else {
            context.update("form:validacionNuevoMotivo");
            context.execute("validacionNuevoMotivo.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoMotivosCesantias() {
        nuevoMotivoCesantia = new MotivosCesantias();

    }

    public void duplicandoMotivosCesantias() {
        System.out.println("duplicandoMotivosCesantias");
        if (motivoCesantiaSeleccionado != null) {
            duplicarMotivoCesantia = new MotivosCesantias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoCesantia.setSecuencia(l);
                duplicarMotivoCesantia.setCodigo(motivoCesantiaSeleccionado.getCodigo());
                duplicarMotivoCesantia.setNombre(motivoCesantiaSeleccionado.getNombre());
            }
            if (tipoLista == 1) {
                duplicarMotivoCesantia.setSecuencia(l);
                duplicarMotivoCesantia.setCodigo(motivoCesantiaSeleccionado.getCodigo());
                duplicarMotivoCesantia.setNombre(motivoCesantiaSeleccionado.getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
        } else {
            RequestContext.getCurrentInstance().execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR MOTIVOSCESANTIAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarMotivoCesantia.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosCesantias.size(); x++) {
                if (listMotivosCesantias.get(x).getCodigo() == duplicarMotivoCesantia.getCodigo()) {
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
        if (duplicarMotivoCesantia.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoCesantia.getSecuencia() + "  " + duplicarMotivoCesantia.getCodigo());
            if (crearMotivosCesantias.contains(duplicarMotivoCesantia)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosCesantias.add(duplicarMotivoCesantia);
            crearMotivosCesantias.add(duplicarMotivoCesantia);
            motivoCesantiaSeleccionado = duplicarMotivoCesantia;
            modificarInfoRegistro(listMotivosCesantias.size());
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarMotivosCesantias = null;
                tipoLista = 0;
                
            }
            duplicarMotivoCesantia = new MotivosCesantias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarMotivosCesantias() {
        duplicarMotivoCesantia = new MotivosCesantias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MOTIVOSCENSANTIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MOTIVOSCENSANTIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (motivoCesantiaSeleccionado != null) {
            System.out.println("lol 2");
            int resultado = administrarRastros.obtenerTabla(motivoCesantiaSeleccionado.getSecuencia(), "MOTIVOSCENSANTIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSCENSANTIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLMOTIVOSCESANTIAS EVENTOFILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            modificarInfoRegistro(filtrarMotivosCesantias.size());
        } catch (Exception e) {
            System.err.println("ERROR CONTROLMOTIVOSCESANTIAS EVENTOFILTRAR  ERROR =" + e.getMessage());
        }
    }

    public void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:infoRegistro");
    }

    public void contarRegistros() {
        if (listMotivosCesantias != null) {
            modificarInfoRegistro(listMotivosCesantias.size());
        } else {
            modificarInfoRegistro(0);
        }
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<MotivosCesantias> getListMotivosCesantias() {
        if (listMotivosCesantias == null) {
            listMotivosCesantias = administrarMotivosCesantias.consultarMotivosCesantias();
        }
        return listMotivosCesantias;
    }

    public void setListMotivosCesantias(List<MotivosCesantias> listMotivosCesantias) {
        this.listMotivosCesantias = listMotivosCesantias;
    }

    public List<MotivosCesantias> getFiltrarMotivosCesantias() {
        return filtrarMotivosCesantias;
    }

    public void setFiltrarMotivosCesantias(List<MotivosCesantias> filtrarMotivosCesantias) {
        this.filtrarMotivosCesantias = filtrarMotivosCesantias;
    }

    public MotivosCesantias getNuevoMotivoCesantia() {
        return nuevoMotivoCesantia;
    }

    public void setNuevoMotivoCesantia(MotivosCesantias nuevoMotivoCesantia) {
        this.nuevoMotivoCesantia = nuevoMotivoCesantia;
    }

    public MotivosCesantias getDuplicarMotivoCesantia() {
        return duplicarMotivoCesantia;
    }

    public void setDuplicarMotivoCesantia(MotivosCesantias duplicarMotivoCesantia) {
        this.duplicarMotivoCesantia = duplicarMotivoCesantia;
    }

    public MotivosCesantias getEditarMotivoCesantia() {
        return editarMotivoCesantia;
    }

    public void setEditarMotivoCesantia(MotivosCesantias editarMotivoCesantia) {
        this.editarMotivoCesantia = editarMotivoCesantia;
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

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public MotivosCesantias getMotivoCesantiaSeleccionado() {
        return motivoCesantiaSeleccionado;
    }

    public void setMotivoCesantiaSeleccionado(MotivosCesantias motivoCesantiaSeleccionado) {
        this.motivoCesantiaSeleccionado = motivoCesantiaSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public boolean isActivarLov() {
        return activarLov;
    }

    public void setActivarLov(boolean activarLov) {
        this.activarLov = activarLov;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    
}

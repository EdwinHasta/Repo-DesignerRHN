/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import InterfaceAdministrar.AdministrarMotivosLocalizacionesInterface;
import Entidades.MotivosLocalizaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class ControlMotivosLocalizaciones implements Serializable {

    @EJB
    AdministrarMotivosLocalizacionesInterface administrarMotivosLocalizaciones;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<MotivosLocalizaciones> listMotivosLocalizaciones;
    private List<MotivosLocalizaciones> filtrarMotivosLocalizaciones;
    private List<MotivosLocalizaciones> crearMotivosLocalizaciones;
    private List<MotivosLocalizaciones> modificarMotivoContrato;
    private List<MotivosLocalizaciones> borrarMotivoContrato;
    private MotivosLocalizaciones nuevoMotivoContrato;
    private MotivosLocalizaciones duplicarMotivoContrato;
    private MotivosLocalizaciones editarMotivoContrato;
    private MotivosLocalizaciones motivoLocalizacionSeleccionado;
    //otros
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera, resultado;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger contarVigenciasLocalizacionesMotivoLocalizacion;
    private int tamano;

    private Integer backUpCodigo;
    private String backUpDescripcion;
    private String infoRegistro, paginaanterior;
    private DataTable tablaC;

    public ControlMotivosLocalizaciones() {

        listMotivosLocalizaciones = null;
        crearMotivosLocalizaciones = new ArrayList<MotivosLocalizaciones>();
        modificarMotivoContrato = new ArrayList<MotivosLocalizaciones>();
        borrarMotivoContrato = new ArrayList<MotivosLocalizaciones>();
        permitirIndex = true;
        editarMotivoContrato = new MotivosLocalizaciones();
        nuevoMotivoContrato = new MotivosLocalizaciones();
        duplicarMotivoContrato = new MotivosLocalizaciones();
        guardado = true;
        tamano = 270;
        paginaanterior = "";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarMotivosLocalizaciones.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPag(String pagina) {
        paginaanterior = pagina;
        getListMotivosLocalizaciones();
        contarRegistros();
        if (listMotivosLocalizaciones != null) {
            if (!listMotivosLocalizaciones.isEmpty()) {
                motivoLocalizacionSeleccionado = listMotivosLocalizaciones.get(0);
            }
        }
    }

    public String retornarPagina() {
        return paginaanterior;
    }

    public void cambiarIndice(MotivosLocalizaciones motivoLocalizacion, int celda) {
        motivoLocalizacionSeleccionado = motivoLocalizacion;
        if (permitirIndex == true) {
            cualCelda = celda;
            motivoLocalizacionSeleccionado.getSecuencia();
            if (cualCelda == 0) {
                backUpCodigo = motivoLocalizacionSeleccionado.getCodigo();
            }
            if (cualCelda == 1) {
                backUpDescripcion = motivoLocalizacionSeleccionado.getDescripcion();
            }
        }
    }

    public void asignarIndex(MotivosLocalizaciones motivoLocalizacion, int LND, int dig) {
        try {
            motivoLocalizacionSeleccionado = motivoLocalizacion;
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

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();

            codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
            bandera = 0;
            filtrarMotivosLocalizaciones = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarMotivoContrato.clear();
        crearMotivosLocalizaciones.clear();
        modificarMotivoContrato.clear();
        k = 0;
        listMotivosLocalizaciones = null;
        motivoLocalizacionSeleccionado = null;
        getListMotivosLocalizaciones();
        contarRegistros();
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMotivoContrato");
        context.update("form:infoRegistro");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();

            codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
            bandera = 0;
            filtrarMotivosLocalizaciones = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarMotivoContrato.clear();
        crearMotivosLocalizaciones.clear();
        modificarMotivoContrato.clear();
        motivoLocalizacionSeleccionado = null;
        k = 0;
        listMotivosLocalizaciones = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        contarRegistros();
        context.update("form:datosMotivoContrato");
        context.update("form:infoRegistro");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
            codigo.setFilterStyle("width: 85%");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
            descripcion.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            tamano = 270;
            System.out.println("Desactivar");
            codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
            bandera = 0;
            filtrarMotivosLocalizaciones = null;
            tipoLista = 0;
        }
        RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
    }

    public void modificarMotivosContrato(MotivosLocalizaciones motivoLocalizacion, String confirmarCambio, String valorConfirmar) {
        motivoLocalizacionSeleccionado = motivoLocalizacion;
        int contador = 0;
        Short codigoVacio = new Short("0");
        boolean coincidencias = false;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {

            if (motivoLocalizacionSeleccionado.getCodigo() == null || motivoLocalizacionSeleccionado.getCodigo().equals(codigoVacio)) {
                mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                coincidencias = false;
                motivoLocalizacionSeleccionado.setCodigo(backUpCodigo);
            } else {

                for (int j = 0; j < listMotivosLocalizaciones.size(); j++) {
                    if (motivoLocalizacionSeleccionado.getSecuencia() != listMotivosLocalizaciones.get(j).getSecuencia()) {
                        if (motivoLocalizacionSeleccionado.getCodigo().equals(listMotivosLocalizaciones.get(j).getCodigo())) {
                            contador++;
                        }
                    }
                }
                if (contador > 0) {
                    mensajeValidacion = "CODIGOS REPETIDOS";
                    coincidencias = false;
                    motivoLocalizacionSeleccionado.setCodigo(backUpCodigo);
                } else {
                    coincidencias = true;
                }
            }
        }

        if (confirmarCambio.equalsIgnoreCase("M")) {

            if (motivoLocalizacionSeleccionado.getDescripcion() == null || motivoLocalizacionSeleccionado.getDescripcion().isEmpty()) {
                mensajeValidacion = "NO PUEDE HABER  NINGUN CAMPO VACIO";
                motivoLocalizacionSeleccionado.setDescripcion(backUpDescripcion);
                coincidencias = false;
            }
            for (int j = 0; j < listMotivosLocalizaciones.size(); j++) {
                if (motivoLocalizacionSeleccionado.getSecuencia() != listMotivosLocalizaciones.get(j).getSecuencia()) {
                    if (motivoLocalizacionSeleccionado.getDescripcion().equals(listMotivosLocalizaciones.get(j).getDescripcion())) {
                        contador++;
                    }
                }
            }
            if (contador > 0) {
                mensajeValidacion = "MOTIVO REPETIDOS";
                coincidencias = false;
                motivoLocalizacionSeleccionado.setCodigo(backUpCodigo);
            } else {
                coincidencias = true;
            }
        }

        if (coincidencias == true) {
            if (!crearMotivosLocalizaciones.contains(motivoLocalizacionSeleccionado)) {
                if (!modificarMotivoContrato.contains(motivoLocalizacionSeleccionado)) {
                    modificarMotivoContrato.add(motivoLocalizacionSeleccionado);
                }
            }

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        } else {
            context.update("form:validacionModificar");
            context.execute("validacionModificar.show()");
        }

        context.update("form:datosMotivoCambioCargo");

    }

    public void borrarMotivosLocalizaciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (motivoLocalizacionSeleccionado != null) {
            if (!modificarMotivoContrato.isEmpty() && modificarMotivoContrato.contains(motivoLocalizacionSeleccionado)) {
                modificarMotivoContrato.remove(modificarMotivoContrato.indexOf(motivoLocalizacionSeleccionado));
                borrarMotivoContrato.add(motivoLocalizacionSeleccionado);
            } else if (!crearMotivosLocalizaciones.isEmpty() && crearMotivosLocalizaciones.contains(motivoLocalizacionSeleccionado)) {
                crearMotivosLocalizaciones.remove(crearMotivosLocalizaciones.indexOf(motivoLocalizacionSeleccionado));
            } else {
                borrarMotivoContrato.add(motivoLocalizacionSeleccionado);
            }
            listMotivosLocalizaciones.remove(motivoLocalizacionSeleccionado);

            if (tipoLista == 1) {
                filtrarMotivosLocalizaciones.remove(motivoLocalizacionSeleccionado);

            }
            context.update("form:datosMotivoContrato");
            context.update("form:infoRegistro");
            motivoLocalizacionSeleccionado = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        } else {
            context.execute("seleccionarRegistro.show()");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            if (tipoLista == 0) {
                contarVigenciasLocalizacionesMotivoLocalizacion = administrarMotivosLocalizaciones.contarVigenciasLocalizacionesMotivoLocalizacion(motivoLocalizacionSeleccionado.getSecuencia());
            } else {
                contarVigenciasLocalizacionesMotivoLocalizacion = administrarMotivosLocalizaciones.contarVigenciasLocalizacionesMotivoLocalizacion(motivoLocalizacionSeleccionado.getSecuencia());
            }
            if (contarVigenciasLocalizacionesMotivoLocalizacion.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarMotivosLocalizaciones();
            } else {
                System.out.println("Borrado>0");
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                contarVigenciasLocalizacionesMotivoLocalizacion = new BigInteger("-1");
            }

        } catch (Exception e) {
            System.err.println("ERROR ControlMotivosLocalizaciones verificarBorrado ERROR " + e);
        }
    }

    public void guardarMotivosLocalizaciones() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!borrarMotivoContrato.isEmpty()) {
                administrarMotivosLocalizaciones.borrarMotivosLocalizaciones(borrarMotivoContrato);
                registrosBorrados = borrarMotivoContrato.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarMotivoContrato.clear();
            }
            if (!crearMotivosLocalizaciones.isEmpty()) {
                administrarMotivosLocalizaciones.crearMotivosLocalizaciones(crearMotivosLocalizaciones);
            }
            crearMotivosLocalizaciones.clear();

            if (!modificarMotivoContrato.isEmpty()) {
                administrarMotivosLocalizaciones.modificarMotivosLocalizaciones(modificarMotivoContrato);
                modificarMotivoContrato.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listMotivosLocalizaciones = null;
            contarRegistros();
            context.update("form:datosMotivoContrato");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            k = 0;
            guardado = true;
        }
        RequestContext.getCurrentInstance()
                .update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (motivoLocalizacionSeleccionado != null) {
            if (tipoLista == 0) {
                editarMotivoContrato = motivoLocalizacionSeleccionado;
            }
            if (tipoLista == 1) {
                editarMotivoContrato = motivoLocalizacionSeleccionado;
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
    }

    public void agregarNuevoMotivoContrato() {
        System.out.println("Agregar Motivo Contrato");
        int contador = 0;
        int duplicados = 0;
        Integer a = null;
        //a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoMotivoContrato.getCodigo() == a) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoMotivoContrato.getCodigo());

            for (int x = 0; x < listMotivosLocalizaciones.size(); x++) {
                if (listMotivosLocalizaciones.get(x).getCodigo() == nuevoMotivoContrato.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoMotivoContrato.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoMotivoContrato.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
                bandera = 0;
                filtrarMotivosLocalizaciones = null;
                tipoLista = 0;
                tamano = 270;
            }
            System.out.println("Despues de la bandera");

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoMotivoContrato.setSecuencia(l);
            crearMotivosLocalizaciones.add(nuevoMotivoContrato);
            listMotivosLocalizaciones.add(nuevoMotivoContrato);
            motivoLocalizacionSeleccionado = nuevoMotivoContrato;
            nuevoMotivoContrato = new MotivosLocalizaciones();
            modificarInfoRegistro(listMotivosLocalizaciones.size());
            context.update("form:infoRegistro");
            context.update("form:datosMotivoContrato");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("nuevoRegistroMotivosLocalizaciones.hide()");
        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");

            contador = 0;
        }
    }

    public void limpiarNuevoMotivosLocalizaciones() {
        System.out.println("limpiarnuevoMotivoContrato");
        nuevoMotivoContrato = new MotivosLocalizaciones();
        motivoLocalizacionSeleccionado = null;

    }

    //------------------------------------------------------------------------------
    public void duplicarMotivosLocalizaciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (motivoLocalizacionSeleccionado != null) {
            duplicarMotivoContrato = new MotivosLocalizaciones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarMotivoContrato.setSecuencia(l);
                duplicarMotivoContrato.setCodigo(motivoLocalizacionSeleccionado.getCodigo());
                duplicarMotivoContrato.setDescripcion(motivoLocalizacionSeleccionado.getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarMotivoContrato.setSecuencia(l);
                duplicarMotivoContrato.setCodigo(motivoLocalizacionSeleccionado.getCodigo());
                duplicarMotivoContrato.setDescripcion(motivoLocalizacionSeleccionado.getDescripcion());
            }
            context.update("formularioDialogos:duplicarMotivosCambiosCargos");
            context.execute("duplicarRegistroMotivosLocalizaciones.show()");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarMotivoContrato.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarMotivoContrato.getDescripcion());

        if (duplicarMotivoContrato.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listMotivosLocalizaciones.size(); x++) {
                if (listMotivosLocalizaciones.get(x).getCodigo() == duplicarMotivoContrato.getCodigo()) {
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
        if (duplicarMotivoContrato.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   *Descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarMotivoContrato.getSecuencia() + "  " + duplicarMotivoContrato.getCodigo());
            if (crearMotivosLocalizaciones.contains(duplicarMotivoContrato)) {
                System.out.println("Ya lo contengo.");
            }
            listMotivosLocalizaciones.add(duplicarMotivoContrato);
            crearMotivosLocalizaciones.add(duplicarMotivoContrato);
            modificarInfoRegistro(listMotivosLocalizaciones.size());
            context.update("form:datosMotivoContrato");
            context.update("form:infoRegistro");
            motivoLocalizacionSeleccionado = duplicarMotivoContrato;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                tamano = 270;
                codigo = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosMotivoContrato:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMotivoContrato");
                bandera = 0;
                filtrarMotivosLocalizaciones = null;
                tipoLista = 0;
            }
            duplicarMotivoContrato = new MotivosLocalizaciones();
            RequestContext.getCurrentInstance().execute("duplicarRegistroMotivosLocalizaciones.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarMotivoLocalizacion");
            context.execute("validacionDuplicarMotivoLocalizacion.show()");
        }
    }

    public void recordarSeleccionMotivoLocalizacion() {
        if (motivoLocalizacionSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosMotivoContrato");
            tablaC.setSelection(motivoLocalizacionSeleccionado);
        }
    }

    //////GET'S Y SET'S
    public void limpiarduplicarMotivosLocalizaciones() {
        duplicarMotivoContrato = new MotivosLocalizaciones();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoContratoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "MotivosLocalizacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        motivoLocalizacionSeleccionado = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMotivoContratoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "MotivosLocalizacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        motivoLocalizacionSeleccionado = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (motivoLocalizacionSeleccionado != null) {
            resultado = administrarRastros.obtenerTabla(motivoLocalizacionSeleccionado.getSecuencia(), "MOTIVOSLOCALIZACIONES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("MOTIVOSLOCALIZACIONES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    public void eventoFiltrar() {
        try {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            modificarInfoRegistro(filtrarMotivosLocalizaciones.size());
            context.update("form:infoRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlMotiviosCambiosCargos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }

    public void contarRegistros() {
        if (listMotivosLocalizaciones != null) {
            modificarInfoRegistro(listMotivosLocalizaciones.size());
        } else {
            modificarInfoRegistro(0);
        }
    }
//-----------------------//---------------//----------------------//------------

    public List<MotivosLocalizaciones> getListMotivosLocalizaciones() {
        if (listMotivosLocalizaciones == null) {
            listMotivosLocalizaciones = administrarMotivosLocalizaciones.mostrarMotivosCambiosCargos();
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

    public MotivosLocalizaciones getNuevoMotivoContrato() {
        return nuevoMotivoContrato;
    }

    public void setNuevoMotivoContrato(MotivosLocalizaciones nuevoMotivoContrato) {
        this.nuevoMotivoContrato = nuevoMotivoContrato;
    }

    public MotivosLocalizaciones getEditarMotivoContrato() {
        return editarMotivoContrato;
    }

    public void setEditarMotivoContrato(MotivosLocalizaciones editarMotivoContrato) {
        this.editarMotivoContrato = editarMotivoContrato;
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

    public MotivosLocalizaciones getDuplicarMotivoContrato() {
        return duplicarMotivoContrato;
    }

    public void setDuplicarMotivoContrato(MotivosLocalizaciones duplicarMotivoContrato) {
        this.duplicarMotivoContrato = duplicarMotivoContrato;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public MotivosLocalizaciones getMotivoLocalizacionSeleccionado() {
        return motivoLocalizacionSeleccionado;
    }

    public void setMotivoLocalizacionSeleccionado(MotivosLocalizaciones motivoLocalizacionSeleccionado) {
        this.motivoLocalizacionSeleccionado = motivoLocalizacionSeleccionado;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public int getResultado() {
        return resultado;
    }

    public void setResultado(int resultado) {
        this.resultado = resultado;
    }

}

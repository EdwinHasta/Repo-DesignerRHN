/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.LiquidacionesLogs;
import Entidades.Operandos;
import Entidades.Procesos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarLiquidacionesLogsInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class ControlLiquidacionesLogs implements Serializable {

    @EJB
    AdministrarLiquidacionesLogsInterface administrarLiquidacionesLogs;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<LiquidacionesLogs> listLiquidacionesLogs;
    private List<LiquidacionesLogs> filtrarLiquidacionesLogs;
    private List<LiquidacionesLogs> crearLiquidacionesLogs;
    private List<LiquidacionesLogs> modificarLiquidacionesLogs;
    private List<LiquidacionesLogs> borrarLiquidacionesLogs;
    private LiquidacionesLogs nuevoLiquidacionesLogs;
    private LiquidacionesLogs duplicarLiquidacionesLogs;
    private LiquidacionesLogs editarLiquidacionesLogs;
    private LiquidacionesLogs liquidacionesLogsSeleccionado;
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

    private List<Empleados> listaEmpleados;
    private List<Empleados> filtrarEmpleados;
    private Empleados empleadoSeleccionado;

    public ControlLiquidacionesLogs() {
        listLiquidacionesLogs = null;
        crearLiquidacionesLogs = new ArrayList<LiquidacionesLogs>();
        modificarLiquidacionesLogs = new ArrayList<LiquidacionesLogs>();
        borrarLiquidacionesLogs = new ArrayList<LiquidacionesLogs>();
        permitirIndex = true;
        editarLiquidacionesLogs = new LiquidacionesLogs();
        nuevoLiquidacionesLogs = new LiquidacionesLogs();
        duplicarLiquidacionesLogs = new LiquidacionesLogs();
        guardado = true;
        tamano = 270;
        System.out.println("controlLiquidacionesLogs Constructor");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            System.out.println("ControlLiquidacionesLogs PostConstruct ");
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarLiquidacionesLogs.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlLiquidacionesLogs.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlLiquidacionesLogs eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void posicion() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listLiquidacionesLogs.get(index).getSecuencia();
            System.out.println("Indice: " + index + " Celda: " + cualCelda);
        }
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlLiquidacionesLogs.asignarIndex \n");
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
            System.out.println("ERROR ControlLiquidacionesLogs.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    private String infoRegistro;

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosLiquidacionesLogs");
            bandera = 0;
            filtrarLiquidacionesLogs = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarLiquidacionesLogs.clear();
        crearLiquidacionesLogs.clear();
        modificarLiquidacionesLogs.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listLiquidacionesLogs = null;
        guardado = true;
        permitirIndex = true;
        getListLiquidacionesLogs();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listLiquidacionesLogs == null || listLiquidacionesLogs.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listLiquidacionesLogs.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosLiquidacionesLogs");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosLiquidacionesLogs");
            bandera = 0;
            filtrarLiquidacionesLogs = null;
            tipoLista = 0;
            tamano = 270;
        }

        borrarLiquidacionesLogs.clear();
        crearLiquidacionesLogs.clear();
        modificarLiquidacionesLogs.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listLiquidacionesLogs = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosLiquidacionesLogs");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:codigo");
            codigo.setFilterStyle("width: 170px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:descripcion");
            descripcion.setFilterStyle("width: 400px");
            RequestContext.getCurrentInstance().update("form:datosLiquidacionesLogs");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            tamano = 270;
            codigo = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosLiquidacionesLogs:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosLiquidacionesLogs");
            bandera = 0;
            filtrarLiquidacionesLogs = null;
            tipoLista = 0;
        }
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarLiquidacionesLogs = listLiquidacionesLogs.get(index);
            }
            if (tipoLista == 1) {
                editarLiquidacionesLogs = filtrarLiquidacionesLogs.get(index);
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

    public void limpiarNuevoLiquidacionesLogs() {
        nuevoLiquidacionesLogs = new LiquidacionesLogs();
        nuevoLiquidacionesLogs.setEmpleado(new Empleados());
        nuevoLiquidacionesLogs.setProceso(new Procesos());
        nuevoLiquidacionesLogs.setOperando(new Operandos());
    }

    public void revisarDialogoGuardar() {

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:confirmarGuardar");
        context.execute("confirmarGuardar.show()");
    }

    public void borrarLiquidacionesLogsEmpleado() {
    }

    public void seleccionarEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();

        System.out.println("Empleado Seleccionado : " + empleadoSeleccionado.getPersona().getNombreCompleto());
        listLiquidacionesLogs = null;
        getListLiquidacionesLogs();
        aceptar = true;
        context.update("form:datosLiquidacionesLogs");
        context.execute("tiposCentrosCostosDialogo.hide()");
    }

    public void cancelarCambioEmpleado() {
        filtrarEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        tipoActualizacion = -1;

    }

    public void llamarDialogoEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tiposCentrosCostosDialogo");
        context.execute("tiposCentrosCostosDialogo.show()");
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosLiquidacionesLogsExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "LIQUIDACIONESLOGS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosLiquidacionesLogsExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "LIQUIDACIONESLOGS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listLiquidacionesLogs.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "LIQUIDACIONESLOGS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("LIQUIDACIONESLOGS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<LiquidacionesLogs> getListLiquidacionesLogs() {
        if (empleadoSeleccionado == null) {
            if (listLiquidacionesLogs == null) {
                System.out.println("ControlLiquidacionesLogs getListLiquidacionesLogs");
                listLiquidacionesLogs = administrarLiquidacionesLogs.consultarLiquidacionesLogs();
            }
        } else {
            listLiquidacionesLogs = administrarLiquidacionesLogs.consultarLiquidacionesLogsPorEmpleado(empleadoSeleccionado.getSecuencia());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listLiquidacionesLogs == null || listLiquidacionesLogs.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listLiquidacionesLogs.size();
        }
        return listLiquidacionesLogs;
    }

    public void setListLiquidacionesLogs(List<LiquidacionesLogs> listLiquidacionesLogs) {
        this.listLiquidacionesLogs = listLiquidacionesLogs;
    }

    public List<LiquidacionesLogs> getFiltrarLiquidacionesLogs() {
        return filtrarLiquidacionesLogs;
    }

    public void setFiltrarLiquidacionesLogs(List<LiquidacionesLogs> filtrarLiquidacionesLogs) {
        this.filtrarLiquidacionesLogs = filtrarLiquidacionesLogs;
    }

    public LiquidacionesLogs getNuevoLiquidacionesLogs() {
        return nuevoLiquidacionesLogs;
    }

    public void setNuevoLiquidacionesLogs(LiquidacionesLogs nuevoLiquidacionesLogs) {
        this.nuevoLiquidacionesLogs = nuevoLiquidacionesLogs;
    }

    public LiquidacionesLogs getEditarLiquidacionesLogs() {
        return editarLiquidacionesLogs;
    }

    public void setEditarLiquidacionesLogs(LiquidacionesLogs editarLiquidacionesLogs) {
        this.editarLiquidacionesLogs = editarLiquidacionesLogs;
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

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public LiquidacionesLogs getLiquidacionesLogsSeleccionado() {
        return liquidacionesLogsSeleccionado;
    }

    public void setLiquidacionesLogsSeleccionado(LiquidacionesLogs clasesPensionesSeleccionado) {
        this.liquidacionesLogsSeleccionado = clasesPensionesSeleccionado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }
    private String infoRegistroEmpleados;

    public List<Empleados> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarLiquidacionesLogs.consultarLOVEmpleados();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaEmpleados == null || listaEmpleados.isEmpty()) {
            infoRegistroEmpleados = "Cantidad de registros: 0 ";
        } else {
            infoRegistroEmpleados = "Cantidad de registros: " + listaEmpleados.size();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<Empleados> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<Empleados> getFiltrarEmpleados() {
        return filtrarEmpleados;
    }

    public void setFiltrarEmpleados(List<Empleados> filtrarEmpleados) {
        this.filtrarEmpleados = filtrarEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public String getInfoRegistroEmpleados() {
        return infoRegistroEmpleados;
    }

    public void setInfoRegistroEmpleados(String infoRegistroEmpleados) {
        this.infoRegistroEmpleados = infoRegistroEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

}

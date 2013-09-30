/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import InterfaceAdministrar.AdministrarNReporteContabilidadInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteContabilidad implements Serializable {

    @EJB
    AdministrarNReporteContabilidadInterface administrarNReporteContabilidad;
    ///
    private ParametrosInformes parametroDeInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
    //
    private String reporteGenerar;
    private Inforeportes inforreporteSeleccionado;
    private int bandera;
    private boolean aceptar;
    private Column codigoIR, reporteIR, tipoIR;
    private int casilla;
    private ParametrosInformes parametroModificacion;
    private int tipoLista;
    private int posicionReporte;
    private String requisitosReporte;
    //
    private String proceso;
    private boolean permitirIndex;
    private Calendar fechaDesdeParametroL, fechaHastaParametroL;
    private InputText empleadoDesdeParametroL, empleadoHastaParametroL;
    //
    private List<Empleados> listEmpleados;
    private List<Empleados> filtrarListEmpleados;
    private Empleados empleadoSeleccionado;
    private List<Procesos> listProcesos;
    private Procesos procesoSeleccionado;
    private List<Procesos> filtrarListProcesos;

    public ControlNReporteContabilidad() {

        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        filtrarListInforeportesUsuario = null;
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        tipoLista = 0;
        reporteGenerar = "";
        requisitosReporte = "";
        posicionReporte = -1;
        permitirIndex = true;
        listEmpleados = null;
        empleadoSeleccionado = new Empleados();
        listProcesos = null;
        procesoSeleccionado = new Procesos();
    }

    public void guardarCambios() {
        try {
            if (parametroModificacion.getProceso().getSecuencia() == null) {
                parametroModificacion.setProceso(null);
            }
            administrarNReporteContabilidad.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void posicionCelda(int i) {
        if (permitirIndex) {
            casilla = i;
            if (casilla == 5) {
                proceso = parametroDeInforme.getProceso().getDescripcion();
            }
        }
        System.out.println("Casilla : " + casilla);
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 1) {
            context.update("formularioDialogos:editarFechaDesde");
            context.execute("editarFechaDesde.show()");
        }
        if (casilla == 2) {
            context.update("formularioDialogos:empleadoDesde");
            context.execute("empleadoDesde.show()");
        }

        if (casilla == 3) {
            context.update("formularioDialogos:editarFechaHasta");
            context.execute("editarFechaHasta.show()");
        }
        if (casilla == 4) {
            context.update("formularioDialogos:empleadoHasta");
            context.execute("empleadoHasta.show()");
        }
        if (casilla == 5) {
            context.update("formularioDialogos:proceso");
            context.execute("proceso.show()");
        }
        casilla = -1;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void generarReporte(int i) {
        if (tipoLista == 0) {
            reporteGenerar = listaIR.get(i).getNombre();
            posicionReporte = i;
        }
        if (tipoLista == 1) {
            if (listaIR.contains(filtrarListInforeportesUsuario.get(i))) {
                int posicion = listaIR.indexOf(filtrarListInforeportesUsuario.get(i));
                reporteGenerar = listaIR.get(posicion).getNombre();
                posicionReporte = posicion;
            }
        }
        mostrarDialogoGenerarReporte();
    }

    public void mostrarDialogoGenerarReporte() {
        defaultPropiedadesParametrosReporte();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formDialogos:reporteAGenerar");
        context.execute("reporteAGenerar.show()");
    }

    public void cancelarGenerarReporte() {
        reporteGenerar = "";
        posicionReporte = -1;
        if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            listaIR = administrarNReporteContabilidad.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ReportesDialogo");
            context.execute("ReportesDialogo.show()");
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }

    public void salir() {
        if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listEmpleados = null;
        listaIR = null;
        listProcesos = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
        procesoSeleccionado = null;
    }

    public void mostrarDialogosListas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 2) {
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (casilla == 4) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (casilla == 5) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
    }

    public void actualizarProceso() {
        permitirIndex = true;
        parametroDeInforme.setProceso(procesoSeleccionado);
        parametroModificacion = parametroDeInforme;
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:procesoParametroL");
    }

    public void cancelarCambioProceso() {
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;
        permitirIndex = true;
    }

    public void actualizarEmplDesde() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoDesdeParametroL");
    }

    public void cancelarCambioEmplDesde() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void actualizarEmplHasta() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadohasta(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoHastaParametroL");
    }

    public void cancelarCambioEmplHasta() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("PROCESO")) {
            parametroDeInforme.getProceso().setDescripcion(proceso);
            for (int i = 0; i < listProcesos.size(); i++) {
                if (listProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setProceso(listProcesos.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listProcesos.clear();
                getListProcesos();
            } else {
                permitirIndex = false;
                context.update("form:ProcesoDialogo");
                context.execute("ProcesoDialogo.show()");
            }
        }
    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;
        listEmpleados = null;
        listProcesos = null;
        listEmpleados = administrarNReporteContabilidad.listEmpleados();
        listProcesos = administrarNReporteContabilidad.listProcesos();
        parametroDeInforme = administrarNReporteContabilidad.parametrosDeReporte();
        if (parametroDeInforme.getProceso() == null) {
            parametroDeInforme.setProceso(new Procesos());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:fechaDesdeParametroL");
        context.update("form:empleadoDesdeParametroL");
        context.update("form:fechaHastaParametroL");
        context.update("form:empleadoHastaParametroL");
        context.update("form:estadoParametroL");
        context.update("form:procesoParametroL");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            defaultPropiedadesParametrosReporte();
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarNReporteContabilidad.listInforeportesUsuario();
            listaIR = listaIRRespaldo;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:reportesNomina");
        } catch (Exception e) {
            System.out.println("Error en reporteModificado : " + e.toString());
        }
    }

    public void reporteSeleccionado(int i) {
        System.out.println("Posicion del reporte : " + i);
    }

    public void defaultPropiedadesParametrosReporte() {


        fechaDesdeParametroL = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametroL");
        fechaDesdeParametroL.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaDesdeParametroL");

        fechaHastaParametroL = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametroL");
        fechaHastaParametroL.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaHastaParametroL");

        empleadoDesdeParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametroL");
        empleadoDesdeParametroL.setStyle("position: absolute; top: 40px; left: 260px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoDesdeParametroL");

        empleadoHastaParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametroL");
        empleadoHastaParametroL.setStyle("position: absolute; top: 40px; left: 400px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoHastaParametroL");

    }

    public void modificarParametroInforme() {
        parametroModificacion = parametroDeInforme;
    }

    public void resaltoParametrosParaReporte(int i) {
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        defaultPropiedadesParametrosReporte();
        if (reporteS.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
            fechaDesdeParametroL = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametroL");
            fechaDesdeParametroL.setStyleClass("ui-datepicker, myClass3");
            RequestContext.getCurrentInstance().update("form:fechaDesdeParametroL");

        }
        if (reporteS.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
            fechaHastaParametroL = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametroL");
            fechaHastaParametroL.setStyleClass("ui-datepicker, myClass3");
            RequestContext.getCurrentInstance().update("form:fechaHastaParametroL");

        }
        if (reporteS.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
            empleadoDesdeParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametroL");
            empleadoDesdeParametroL.setStyle("position: absolute; top: 40px; left: 260px;font-size: 11px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoDesdeParametroL");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
            empleadoHastaParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametroL");
            empleadoHastaParametroL.setStyle("position: absolute; top: 40px; left: 400px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoHastaParametroL");
        }
    }

    public void parametrosDeReporte(int i) {
        String cadena = "";
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        if (reporteS.getFecdesde().equals("SI")) {
            cadena = cadena + "- Fecha Desde -";
        }
        if (reporteS.getFechasta().equals("SI")) {
            cadena = cadena + "- Fecha Hasta -";
        }
        if (reporteS.getEmdesde().equals("SI")) {
            cadena = cadena + "- Empleado Desde -";
        }
        if (reporteS.getEmhasta().equals("SI")) {
            cadena = cadena + "- Empleado Hasta -";
        }
        setRequisitosReporte(cadena);
        if (!requisitosReporte.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formDialogos:requisitosReporte");
            context.execute("requisitosReporte.show()");
        }
    }

    public void cancelarRequisitosReporte() {
        requisitosReporte = "";
    }

    public ParametrosInformes getParametroDeInforme() {
        try {
            if (parametroDeInforme == null) {
                parametroDeInforme = new ParametrosInformes();
                parametroDeInforme = administrarNReporteContabilidad.parametrosDeReporte();
            }

            if (parametroDeInforme.getCargo() == null) {
                parametroDeInforme.setCargo(new Cargos());
            }
            return parametroDeInforme;
        } catch (Exception e) {
            System.out.println("Error getParametroDeInforme : " + e);
            return null;
        }
    }

    public void setParametroDeInforme(ParametrosInformes parametroDeInforme) {
        this.parametroDeInforme = parametroDeInforme;
    }

    public List<Inforeportes> getListaIR() {
        try {
            if (listaIR == null) {
                listaIR = new ArrayList<Inforeportes>();
                listaIR = administrarNReporteContabilidad.listInforeportesUsuario();
            }
            listaIRRespaldo = listaIR;
            return listaIR;
        } catch (Exception e) {
            System.out.println("Error getListInforeportesUsuario : " + e);
            return null;
        }
    }

    public void setListaIR(List<Inforeportes> listaIR) {
        this.listaIR = listaIR;
    }

    public List<Inforeportes> getFiltrarListInforeportesUsuario() {
        return filtrarListInforeportesUsuario;
    }

    public void setFiltrarListInforeportesUsuario(List<Inforeportes> filtrarListInforeportesUsuario) {
        this.filtrarListInforeportesUsuario = filtrarListInforeportesUsuario;
    }

    public Inforeportes getInforreporteSeleccionado() {
        return inforreporteSeleccionado;
    }

    public void setInforreporteSeleccionado(Inforeportes inforreporteSeleccionado) {
        this.inforreporteSeleccionado = inforreporteSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public ParametrosInformes getParametroModificacion() {
        return parametroModificacion;
    }

    public void setParametroModificacion(ParametrosInformes parametroModificacion) {
        this.parametroModificacion = parametroModificacion;
    }

    public List<Inforeportes> getListaIRRespaldo() {
        return listaIRRespaldo;
    }

    public void setListaIRRespaldo(List<Inforeportes> listaIRRespaldo) {
        this.listaIRRespaldo = listaIRRespaldo;
    }

    public String getReporteGenerar() {
        if (posicionReporte != -1) {
            reporteGenerar = listaIR.get(posicionReporte).getNombre();
        }
        return reporteGenerar;
    }

    public void setReporteGenerar(String reporteGenerar) {
        this.reporteGenerar = reporteGenerar;
    }

    public String getRequisitosReporte() {
        return requisitosReporte;
    }

    public void setRequisitosReporte(String requisitosReporte) {
        this.requisitosReporte = requisitosReporte;
    }

    public List<Empleados> getListEmpleados() {
        try {
            if (listEmpleados == null) {
                listEmpleados = new ArrayList<Empleados>();
                listEmpleados = administrarNReporteContabilidad.listEmpleados();
            }
        } catch (Exception e) {
            System.out.println("Error en getListEmpleados : " + e.toString());
            return null;
        }
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleados> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<Empleados> getFiltrarListEmpleados() {
        return filtrarListEmpleados;
    }

    public void setFiltrarListEmpleados(List<Empleados> filtrarListEmpleados) {
        this.filtrarListEmpleados = filtrarListEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Procesos> getListProcesos() {
        try {
            if (listProcesos == null) {
                listProcesos = new ArrayList<Procesos>();
                listProcesos = administrarNReporteContabilidad.listProcesos();
            }
            return listProcesos;
        } catch (Exception e) {
            System.out.println("Error getListProcesos : " + e.toString());
            return null;
        }
    }

    public void setListProcesos(List<Procesos> listProcesos) {
        this.listProcesos = listProcesos;
    }

    public Procesos getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public List<Procesos> getFiltrarListProcesos() {
        return filtrarListProcesos;
    }

    public void setFiltrarListProcesos(List<Procesos> filtrarListProcesos) {
        this.filtrarListProcesos = filtrarListProcesos;
    }
}

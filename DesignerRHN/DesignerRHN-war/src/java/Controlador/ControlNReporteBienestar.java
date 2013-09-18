/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Actividades;
import Entidades.Empleados;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import InterfaceAdministrar.AdministrarNReporteBienestarInterface;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlNReporteBienestar implements Serializable{

    @EJB
    AdministrarNReporteBienestarInterface administrarNReporteBienestar;
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
    private String actividad;
    private boolean permitirIndex;
    private Calendar fechaDesdeParametroL, fechaHastaParametroL;
    private InputText empleadoDesdeParametroL, empleadoHastaParametroL;
    //
    private List<Empleados> listEmpleados;
    private List<Empleados> filtrarListEmpleados;
    private Empleados empleadoSeleccionado;
    private List<Actividades> listActividades;
    private Actividades actividadSeleccionada;
    private List<Actividades> filtrarListActividades;
    //
    private String parametroModulo;
    private String tituloReporte;

    public ControlNReporteBienestar() {

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
        listActividades = null;
        actividadSeleccionada = new Actividades();
        parametroModulo = "";
    }
    
    public void recibirParametroModulo(String parametro){
        parametroModulo = parametro;
    }

    public void guardarCambios() {
        try {
            if (parametroModificacion.getActividadbienestar() == null) {
                parametroModificacion.setActividadbienestar(null);
            }
            administrarNReporteBienestar.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void posicionCelda(int i) {
        if (permitirIndex) {
            casilla = i;
            if (casilla == 5) {
                actividad = parametroDeInforme.getActividadbienestar().getDescripcion();
            }
        }
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
            context.update("formularioDialogos:actividad");
            context.execute("actividad.show()");
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
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesBienestar");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            listaIR = administrarNReporteBienestar.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ReportesDialogo");
            context.execute("ReportesDialogo.show()");
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }
    
    public void actualizarSeleccionInforeporte() {
        listaIR = new ArrayList<Inforeportes>();
        listaIR.add(inforreporteSeleccionado);
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesBienestar");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
    }

    public void salir() {
        if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesBienestar");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listEmpleados = null;
        listaIR = null;
        listActividades = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
        actividadSeleccionada = null;
    }

    public void mostrarDialogosListas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 5) {
            context.update("form:ActividadDialogo");
            context.execute("ActividadDialogo.show()");
        }
    }

    public void actualizarActividad() {
        permitirIndex = true;
        parametroDeInforme.setActividadbienestar(actividadSeleccionada);
        parametroModificacion = parametroDeInforme;
        actividadSeleccionada = null;
        aceptar = true;
        filtrarListActividades = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:actividadParametroL");
    }

    public void cancelarCambioActividad() {
        actividadSeleccionada = null;
        aceptar = true;
        filtrarListActividades = null;
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
        if (campoConfirmar.equalsIgnoreCase("ACTIVIDAD")) {
            parametroDeInforme.getActividadbienestar().setDescripcion(actividad);
            for (int i = 0; i < listActividades.size(); i++) {
                if (listActividades.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setActividadbienestar(listActividades.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listActividades.clear();
                getListActividades();
            } else {
                permitirIndex = false;
                context.update("form:ActividadDialogo");
                context.execute("ActividadDialogo.show()");
            }
        }
    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;
        listEmpleados = null;
        listActividades = null;
        listEmpleados = administrarNReporteBienestar.listEmpleados();
        listActividades = administrarNReporteBienestar.listActividades();
        parametroDeInforme = administrarNReporteBienestar.parametrosDeReporte();
        if (parametroDeInforme.getActividadbienestar()== null) {
            parametroDeInforme.setActividadbienestar(new Actividades());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:fechaDesdeParametroL");
        context.update("form:empleadoDesdeParametroL");
        context.update("form:fechaHastaParametroL");
        context.update("form:empleadoHastaParametroL");
        context.update("form:estadoParametroL");
        context.update("form:actividadParametroL");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:tipoIR");
            tipoIR.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:reportesBienestar");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            defaultPropiedadesParametrosReporte();
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesBienestar:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesBienestar");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarNReporteBienestar.listInforeportesUsuario();
            listaIR = listaIRRespaldo;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:reportesBienestar");
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
    
    public void mostrarTodos() {
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        getListaIR();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:reportesBienestar");
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
                parametroDeInforme = administrarNReporteBienestar.parametrosDeReporte();
            }

            if (parametroDeInforme.getActividadbienestar()== null) {
                parametroDeInforme.setActividadbienestar(new Actividades());
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
                listaIR = administrarNReporteBienestar.listInforeportesUsuario();
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
                listEmpleados = administrarNReporteBienestar.listEmpleados();
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

    public List<Actividades> getListActividades() {
        try {
            if (listActividades == null) {
                listActividades = new ArrayList<Actividades>();
                listActividades = administrarNReporteBienestar.listActividades();
            }
            return listActividades;
        } catch (Exception e) {
            System.out.println("Error getListActividades : " + e.toString());
            return null;
        }
    }

    public void setListActividades(List<Actividades> listActividades) {
        this.listActividades = listActividades;
    }

    public Actividades getActividadSeleccionada() {
        return actividadSeleccionada;
    }

    public void setActividadSeleccionada(Actividades actividadSeleccionada) {
        this.actividadSeleccionada = actividadSeleccionada;
    }

    public List<Actividades> getFiltrarListActividades() {
        return filtrarListActividades;
    }

    public void setFiltrarListActividades(List<Actividades> filtrarListActividades) {
        this.filtrarListActividades = filtrarListActividades;
    }

    public String getParametroModulo() {
        return parametroModulo;
    }

    public void setParametroModulo(String parametroModulo) {
        this.parametroModulo = parametroModulo;
    }

    public String getTituloReporte() {
        tituloReporte = "Reportes Predefinidos - "+parametroModulo.toUpperCase();
        return tituloReporte;
    }

    public void setTituloReporte(String tituloReporte) {
        this.tituloReporte = tituloReporte;
    }
  
    
    
}

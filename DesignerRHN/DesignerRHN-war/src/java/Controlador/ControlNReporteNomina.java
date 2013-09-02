/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Asociaciones;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Estructuras;
import Entidades.GruposConceptos;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.Terceros;
import Entidades.TiposTrabajadores;
import Entidades.TiposAsociaciones;
import Entidades.UbicacionesGeograficas;
import InterfaceAdministrar.AdministrarReportesInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteNomina implements Serializable {

    @EJB
    AdministrarReportesInterface administrarReportes;
    private ParametrosInformes parametroDeInforme;
    private ParametrosInformes nuevoParametroInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
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
    private Calendar fechaDesdeParametro, fechaHastaParametro;
    private InputText empleadoDesdeParametro, empleadoHastaParametro, empresaParametro, estructuraParametro, tipoTrabajadorParametro, terceroParametro, procesoParametro, notasParametro, asociacionParametro, grupoParametro, ubicacionGeograficaParametro, tipoAsociacionParametro;
    private SelectOneMenu estadoParametro;
    private List<Empleados> listEmpleados;
    private List<Empresas> listEmpresas;
    private List<GruposConceptos> listGruposConceptos;
    private List<UbicacionesGeograficas> listUbicacionesGeograficas;
    private List<TiposAsociaciones> listTiposAsociaciones;
    private List<Estructuras> listEstructuras;
    private List<TiposTrabajadores> listTiposTrabajadores;
    private List<Terceros> listTerceros;
    private List<Procesos> listProcesos;
    private List<Asociaciones> listAsociaciones;
    private Empleados empleadoSeleccionado;
    private Empresas empresaSeleccionada;
    private GruposConceptos grupoCSeleccionado;
    private UbicacionesGeograficas ubicacionesGSeleccionado;
    private TiposAsociaciones tiposASeleccionado;
    private Estructuras estructuraSeleccionada;
    private TiposTrabajadores tipoTSeleccionado;
    private Terceros terceroSeleccionado;
    private Procesos procesoSeleccionado;
    private Asociaciones asociacionSeleccionado;
    private List<Empleados> filtrarListEmpleados;
    private List<Empresas> filtrarListEmpresas;
    private List<GruposConceptos> filtrarListGruposConceptos;
    private List<UbicacionesGeograficas> filtrarListUbicacionesGeograficas;
    private List<TiposAsociaciones> filtrarListTiposAsociaciones;
    private List<Estructuras> filtrarListEstructuras;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private List<Terceros> filtrarListTerceros;
    private List<Procesos> filtrarListProcesos;
    private List<Asociaciones> filtrarListAsociaciones;
    private String emplDesde, emplHasta, grupo, ubiGeo, tipoAso, estructura, empresa, tipoTrabajador, tercero, proceso, asociacion;
    private boolean permitirIndex;

    public ControlNReporteNomina() {
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
        listAsociaciones = new ArrayList<Asociaciones>();
        listEmpleados = new ArrayList<Empleados>();
        listEmpresas = new ArrayList<Empresas>();
        listEstructuras = new ArrayList<Estructuras>();
        listGruposConceptos = new ArrayList<GruposConceptos>();
        listProcesos = new ArrayList<Procesos>();
        listTerceros = new ArrayList<Terceros>();
        listTiposAsociaciones = new ArrayList<TiposAsociaciones>();
        listTiposTrabajadores = new ArrayList<TiposTrabajadores>();
        listUbicacionesGeograficas = new ArrayList<UbicacionesGeograficas>();

        empleadoSeleccionado = new Empleados();
        empresaSeleccionada = new Empresas();
        grupoCSeleccionado = new GruposConceptos();
        ubicacionesGSeleccionado = new UbicacionesGeograficas();
        tiposASeleccionado = new TiposAsociaciones();
        estructuraSeleccionada = new Estructuras();
        tipoTSeleccionado = new TiposTrabajadores();
        terceroSeleccionado = new Terceros();
        procesoSeleccionado = new Procesos();
        asociacionSeleccionado = new Asociaciones();
        permitirIndex = true;
    }

    public void guardarCambios() {
        try {
            if (parametroModificacion.getGrupo().getSecuencia() == null) {
                parametroModificacion.setGrupo(null);
            }
            if (parametroModificacion.getUbicaciongeografica().getSecuencia() == null) {
                parametroModificacion.setUbicaciongeografica(null);
            }
            if (parametroModificacion.getTipoasociacion().getSecuencia() == null) {
                parametroModificacion.setTipoasociacion(null);
            }
            if (parametroModificacion.getLocalizacion().getSecuencia() == null) {
                parametroModificacion.setLocalizacion(null);
            }
            if (parametroModificacion.getTipotrabajador().getSecuencia() == null) {
                parametroModificacion.setTipotrabajador(null);
            }
            if (parametroModificacion.getTercero().getSecuencia() == null) {
                parametroModificacion.setTercero(null);
            }
            if (parametroModificacion.getProceso().getSecuencia() == null) {
                parametroModificacion.setProceso(null);
            }
            if (parametroModificacion.getAsociacion().getSecuencia() == null) {
                parametroModificacion.setAsociacion(null);
            }
            administrarReportes.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void modificarParametroInforme() {
        System.out.println("Se modifico el parametro de informe");
        parametroModificacion = parametroDeInforme;
    }

    public void posicionCelda(int i) {
        casilla = i;
        if (casilla == 2) {
            emplDesde = parametroDeInforme.getCodigoempleadodesde().toString();
        }
        if (casilla == 4) {
            grupo = parametroDeInforme.getGrupo().getDescripcion();
        }
        if (casilla == 5) {
            ubiGeo = parametroDeInforme.getUbicaciongeografica().getDescripcion();
        }
        if (casilla == 6) {
            tipoAso = parametroDeInforme.getTipoasociacion().getDescripcion();
        }
        if (casilla == 8) {
            emplHasta = parametroDeInforme.getCodigoempleadohasta().toString();
        }
        if (casilla == 10) {
            empresa = parametroDeInforme.getEmpresa().getNombre();
        }
        if (casilla == 11) {
            estructura = parametroDeInforme.getLocalizacion().getNombre();
        }
        if (casilla == 12) {
            tipoTrabajador = parametroDeInforme.getTipotrabajador().getNombre();
        }
        if (casilla == 13) {
            tercero = parametroDeInforme.getTercero().getNombre();
        }
        if (casilla == 14) {
            proceso = parametroDeInforme.getProceso().getDescripcion();
        }
        if (casilla == 15) {
            asociacion = parametroDeInforme.getAsociacion().getDescripcion();
        }
        System.out.println("Casilla : " + casilla);
    }

    public void editarCelda() {
        System.out.println("Casilla a editar : " + casilla);
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
            context.update("formularioDialogos:grupoDesde");
            context.execute("grupoDesde.show()");
        }
        if (casilla == 4) {
            context.update("formularioDialogos:ubicacionGeografica");
            context.execute("ubicacionGeografica.show()");
        }
        if (casilla == 5) {
            context.update("formularioDialogos:tipoAsociacion");
            context.execute("tipoAsociacion.show()");
        }
        if (casilla == 6) {
            context.update("formularioDialogos:editarFechaHasta");
            context.execute("editarFechaHasta.show()");
        }
        if (casilla == 7) {
            context.update("formularioDialogos:empleadoHasta");
            context.execute("empleadoHasta.show()");
        }
        if (casilla == 8) {
            context.update("formularioDialogos:empresa");
            context.execute("empresa.show()");
        }
        if (casilla == 9) {
            context.update("formularioDialogos:estructura");
            context.execute("estructura.show()");
        }
        if (casilla == 10) {
            context.update("formularioDialogos:tipoTrabajador");
            context.execute("tipoTrabajador.show()");
        }
        if (casilla == 11) {
            context.update("formularioDialogos:tercero");
            context.execute("tercero.show()");
        }
        if (casilla == 12) {
            context.update("formularioDialogos:proceso");
            context.execute("proceso.show()");
        }
        if (casilla == 13) {
            context.update("formularioDialogos:notas");
            context.execute("notas.show()");
        }
        if (casilla == 14) {
            context.update("formularioDialogos:asociacion");
            context.execute("asociacion.show()");
        }
        casilla = -1;

    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarEmplDesde() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
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
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
    }

    public void cancelarCambioEmplHasta() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }
    
    public void actualizarGrupo() {
        permitirIndex = true;
        parametroDeInforme.setGrupo(grupoCSeleccionado);
        grupoCSeleccionado = null;
        aceptar = true;
        filtrarListGruposConceptos = null;
    }

    public void cancelarCambioGrupo() {
        grupoCSeleccionado = null;
        aceptar = true;
        filtrarListGruposConceptos = null;
        permitirIndex = true;
    }
    
    public void actualizarUbicacionGeografica() {
        permitirIndex = true;
        parametroDeInforme.setUbicaciongeografica(ubicacionesGSeleccionado);
        ubicacionesGSeleccionado = null;
        aceptar = true;
        filtrarListUbicacionesGeograficas = null;
    }

    public void cancelarCambioUbicacionGeografica() {
        ubicacionesGSeleccionado = null;
        aceptar = true;
        filtrarListUbicacionesGeograficas = null;
        permitirIndex = true;
    }
    
     public void actualizarTipoAsociacion() {
        permitirIndex = true;
        parametroDeInforme.setTipoasociacion(tiposASeleccionado);
        tiposASeleccionado = null;
        aceptar = true;
        filtrarListTiposAsociaciones = null;
    }

    public void cancelarTipoAsociacion() {
        tiposASeleccionado = null;
        aceptar = true;
        filtrarListTiposAsociaciones = null;
        permitirIndex = true;
    }
    
     public void actualizarEmpresa() {
        permitirIndex = true;
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
    }

    public void cancelarEmpresa() {
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
        permitirIndex = true;
    }
    
    public void actualizarEstructura(){
        parametroDeInforme.setLocalizacion(estructuraSeleccionada);
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
    }

    public void cancelarEstructura() {
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;
    }
    
    public void actualizarTipoTrabajador(){
        parametroDeInforme.setTipotrabajador(tipoTSeleccionado);
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
    }

    public void cancelarTipoTrabajador() {
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;
    }
    
    public void actualizarTercero(){
        parametroDeInforme.setTercero(terceroSeleccionado);
        terceroSeleccionado = null;
        aceptar = true;
        filtrarListTerceros = null;
    }

    public void cancelarTercero() {
        terceroSeleccionado = null;
        aceptar = true;
        filtrarListTerceros = null;
        permitirIndex = true;
    }
    
    public void actualizarProceso(){
        parametroDeInforme.setProceso(procesoSeleccionado);
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;
    }

    public void cancelarProceso() {
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;
        permitirIndex = true;
    }
    
    public void actualizarAsociacion(){
        parametroDeInforme.setAsociacion(asociacionSeleccionado);
        asociacionSeleccionado = null;
        aceptar = true;
        filtrarListAsociaciones = null;
    }

    public void cancelarAsociacion() {
        asociacionSeleccionado = null;
        aceptar = true;
        filtrarListAsociaciones = null;
        permitirIndex = true;
    }
    
    

    public void generarReporte(int i) {
        defaultPropiedadesParametrosReporte();
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formDialogos:reporteAGenerar");
        context.execute("reporteAGenerar.show()");
    }

    public void cancelarGenerarReporte() {
        reporteGenerar = "";
        posicionReporte = -1;
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            listaIR = administrarReportes.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ReportesDialogo");
            context.execute("ReportesDialogo.show()");
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }

    public void salir() {
        if (bandera == 1) {
            System.out.println("Desactivar");
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
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        listAsociaciones = null;
        listEmpleados = null;
        listEmpresas = null;
        listEstructuras = null;
        listGruposConceptos = null;
        listProcesos = null;
        listTerceros = null;
        listTiposAsociaciones = null;
        listTiposTrabajadores = null;
        listUbicacionesGeograficas = null;
        casilla = -1;
    }

    public void actualizarSeleccionInforeporte() {
        if (bandera == 1) {
            System.out.println("Desactivar");
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
        defaultPropiedadesParametrosReporte();
        listaIR = new ArrayList<Inforeportes>();
        listaIR.add(inforreporteSeleccionado);
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesNomina");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
    }

    public void mostrarTodos() {
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        getListaIR();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesNomina");
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
            defaultPropiedadesParametrosReporte();
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarReportes.listInforeportesUsuario();
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

        fechaDesdeParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametro");
        fechaDesdeParametro.setStyleClass("ui-datepicker, myClass");
        RequestContext.getCurrentInstance().update("form:fechaDesdeParametro");

        fechaHastaParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
        fechaHastaParametro.setStyleClass("ui-datepicker, myClass");
        RequestContext.getCurrentInstance().update("form:fechaHastaParametro");

        empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametro");
        empleadoDesdeParametro.setStyle("position: absolute; top: 35px; left: 90px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");

        empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
        empleadoHastaParametro.setStyle("position: absolute; top: 35px; left: 270px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");

        grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:grupoParametro");
        grupoParametro.setStyle("position: absolute; top: 85px; left: 90px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:grupoParametro");

        estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estructuraParametro");
        estructuraParametro.setStyle("position: absolute; top: 10px; left: 480px;font-size: 11px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("form:estructuraParametro");

        tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
        tipoTrabajadorParametro.setStyle("position: absolute; top: 35px; left: 480px;font-size: 11px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");

        terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:terceroParametro");
        terceroParametro.setStyle("position: absolute; top: 58px; left: 480px;font-size: 11px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("form:terceroParametro");

        terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:terceroParametro");
        terceroParametro.setStyle("position: absolute; top: 58px; left: 480px;font-size: 11px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("form:terceroParametro");

        estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estadoParametro");
        estadoParametro.setStyleClass("");
        RequestContext.getCurrentInstance().update("form:estadoParametro");
    }

    public void resaltoParametrosParaReporte(int i) {
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        defaultPropiedadesParametrosReporte();
        if (reporteS.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
            fechaDesdeParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametro");
            fechaDesdeParametro.setStyleClass("ui-datepicker, myClass3");

        }
        if (reporteS.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
            fechaHastaParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
            fechaHastaParametro.setStyleClass("ui-datepicker, myClass3");

        }
        if (reporteS.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
            empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametro");
            empleadoDesdeParametro.setStyle("position: absolute; top: 35px; left: 90px;font-size: 11px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
            empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
            empleadoHastaParametro.setStyle("position: absolute; top: 35px; left: 270px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");
        }
        if (reporteS.getGrupo().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Grupo -";
            grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:grupoParametro");
            grupoParametro.setStyle("position: absolute; top: 85px; left: 90px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:grupoParametro");
        }
        if (reporteS.getLocalizacion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Ubicacion Geofrafica -";
            estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estructuraParametro");
            estructuraParametro.setStyle("position: absolute; top: 10px; left: 480px;font-size: 11px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:estructuraParametro");
        }
        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
            tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
            tipoTrabajadorParametro.setStyle("position: absolute; top: 35px; left: 480px;font-size: 11px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");
        }
        if (reporteS.getTercero().equals("SI")) {
            System.out.println("Entro tercero");
            requisitosReporte = requisitosReporte + "- Tercero -";
            terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:terceroParametro");
            terceroParametro.setStyle("position: absolute; top: 58px; left: 480px;font-size: 11px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:terceroParametro");
        }
        if (reporteS.getEstado().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estado -";
            estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estadoParametro");
            estadoParametro.setStyleClass("selectOneMenuNReporteN");
            RequestContext.getCurrentInstance().update("form:estadoParametro");
        }
    }

    public void parametrosDeReporte(int i) {
        requisitosReporte = "";
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        RequestContext context = RequestContext.getCurrentInstance();
        if (reporteS.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
        }
        if (reporteS.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
        }
        if (reporteS.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
        }
        if (reporteS.getGrupo().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Grupo -";
        }
        if (reporteS.getLocalizacion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estructura -";
        }
        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
        }
        if (reporteS.getTercero().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tercero -";
        }
        if (reporteS.getEstado().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estado -";
        }
        if (!requisitosReporte.isEmpty()) {
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
                parametroDeInforme = administrarReportes.parametrosDeReporte();
            }
            if (parametroDeInforme.getGrupo() == null) {
                parametroDeInforme.setGrupo(new GruposConceptos());
            }
            if (parametroDeInforme.getUbicaciongeografica() == null) {
                parametroDeInforme.setUbicaciongeografica(new UbicacionesGeograficas());
            }
            if (parametroDeInforme.getTipoasociacion() == null) {
                parametroDeInforme.setTipoasociacion(new TiposAsociaciones());
            }
            if (parametroDeInforme.getLocalizacion() == null) {
                parametroDeInforme.setLocalizacion(new Estructuras());
            }
            if (parametroDeInforme.getTipotrabajador() == null) {
                parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
            }
            if (parametroDeInforme.getTercero() == null) {
                parametroDeInforme.setTercero(new Terceros());
            }
            if (parametroDeInforme.getProceso() == null) {
                parametroDeInforme.setProceso(new Procesos());
            }
            if (parametroDeInforme.getAsociacion() == null) {
                parametroDeInforme.setAsociacion(new Asociaciones());
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
                listaIR = administrarReportes.listInforeportesUsuario();
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

    public ParametrosInformes getNuevoParametroInforme() {
        return nuevoParametroInforme;
    }

    public void setNuevoParametroInforme(ParametrosInformes nuevoParametroInforme) {
        this.nuevoParametroInforme = nuevoParametroInforme;
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
                listEmpleados = administrarReportes.listEmpleados();
            }
        } catch (Exception e) {
            listEmpleados = null;
            System.out.println("Error en getListEmpleados : " + e.toString());
        }
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleados> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<Empresas> getListEmpresas() {
        try {
            if (listEmpresas == null) {
                listEmpresas = administrarReportes.listEmpresas();
            }
        } catch (Exception e) {
            listEmpresas = null;
            System.out.println("Error en getListEmpresas : " + e.toString());
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<GruposConceptos> getListGruposConceptos() {
        try {
            if (listGruposConceptos == null) {
                listGruposConceptos = administrarReportes.listGruposConcetos();
            }
        } catch (Exception e) {
            listGruposConceptos = null;
            System.out.println("Error en getListGruposConceptos : " + e.toString());
        }
        return listGruposConceptos;
    }

    public void setListGruposConceptos(List<GruposConceptos> listGruposConceptos) {
        this.listGruposConceptos = listGruposConceptos;
    }

    public List<UbicacionesGeograficas> getListUbicacionesGeograficas() {
        try {
            if (listUbicacionesGeograficas == null) {
                listUbicacionesGeograficas = administrarReportes.listUbicacionesGeograficas();
            }
        } catch (Exception e) {
            listUbicacionesGeograficas = null;
            System.out.println("Error en getListUbicacionesGeograficas : " + e.toString());
        }
        return listUbicacionesGeograficas;
    }

    public void setListUbicacionesGeograficas(List<UbicacionesGeograficas> listUbicacionesGeograficas) {
        this.listUbicacionesGeograficas = listUbicacionesGeograficas;
    }

    public List<TiposAsociaciones> getListTiposAsociaciones() {
        try {
            if (listTiposAsociaciones == null) {
                listTiposAsociaciones = administrarReportes.listTiposAsociaciones();
            }
        } catch (Exception e) {
            listTiposAsociaciones = null;
            System.out.println("Error en getListTiposAsociaciones : " + e.toString());
        }
        return listTiposAsociaciones;
    }

    public void setListTiposAsociaciones(List<TiposAsociaciones> listTiposAsociaciones) {
        this.listTiposAsociaciones = listTiposAsociaciones;
    }

    public List<Estructuras> getListEstructuras() {
        try {
            if (listEstructuras == null) {
                listEstructuras = administrarReportes.listEstructuras();
            }
        } catch (Exception e) {
            listEstructuras = null;
            System.out.println("Error en getListEstructuras : " + e.toString());
        }
        return listEstructuras;
    }

    public void setListEstructuras(List<Estructuras> listEstructuras) {
        this.listEstructuras = listEstructuras;
    }

    public List<TiposTrabajadores> getListTiposTrabajadores() {
        try {
            if (listTiposTrabajadores == null) {
                listTiposTrabajadores = administrarReportes.listTiposTrabajadores();
            }
        } catch (Exception e) {
            listTiposTrabajadores = null;
            System.out.println("Error en getListTiposTrabajadores : " + e.toString());
        }
        return listTiposTrabajadores;
    }

    public void setListTiposTrabajadores(List<TiposTrabajadores> listTiposTrabajadores) {
        this.listTiposTrabajadores = listTiposTrabajadores;
    }

    public List<Terceros> getListTerceros() {
        try {
            if (listTerceros == null) {
                listTerceros = administrarReportes.listTerceros();
            }
        } catch (Exception e) {
            listTerceros = null;
            System.out.println("Error en getListTerceros : " + e.toString());
        }
        return listTerceros;
    }

    public void setListTerceros(List<Terceros> listTerceros) {
        this.listTerceros = listTerceros;
    }

    public List<Procesos> getListProcesos() {
        try {
            if (listProcesos == null) {
                listProcesos = administrarReportes.listProcesos();
            }
        } catch (Exception e) {
            listProcesos = null;
            System.out.println("Error en getListProcesos : " + e.toString());
        }
        return listProcesos;
    }

    public void setListProcesos(List<Procesos> listProcesos) {
        this.listProcesos = listProcesos;
    }

    public List<Asociaciones> getListAsociaciones() {
        try {
            if (listAsociaciones == null) {
                listAsociaciones = administrarReportes.listAsociaciones();
            }
        } catch (Exception e) {
            listAsociaciones = null;
            System.out.println("Error en getListAsociaciones : " + e.toString());
        }
        return listAsociaciones;
    }

    public void setListAsociaciones(List<Asociaciones> listAsociaciones) {
        this.listAsociaciones = listAsociaciones;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public GruposConceptos getGrupoCSeleccionado() {
        return grupoCSeleccionado;
    }

    public void setGrupoCSeleccionado(GruposConceptos grupoCSeleccionado) {
        this.grupoCSeleccionado = grupoCSeleccionado;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public TiposTrabajadores getTipoTSeleccionado() {
        return tipoTSeleccionado;
    }

    public void setTipoTSeleccionado(TiposTrabajadores tipoTSeleccionado) {
        this.tipoTSeleccionado = tipoTSeleccionado;
    }

    public Terceros getTerceroSeleccionado() {
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public Procesos getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public Asociaciones getAsociacionSeleccionado() {
        return asociacionSeleccionado;
    }

    public void setAsociacionSeleccionado(Asociaciones asociacionSeleccionado) {
        this.asociacionSeleccionado = asociacionSeleccionado;
    }

    public List<Empleados> getFiltrarListEmpleados() {
        return filtrarListEmpleados;
    }

    public void setFiltrarListEmpleados(List<Empleados> filtrarListEmpleados) {
        this.filtrarListEmpleados = filtrarListEmpleados;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public List<GruposConceptos> getFiltrarListGruposConceptos() {
        return filtrarListGruposConceptos;
    }

    public void setFiltrarListGruposConceptos(List<GruposConceptos> filtrarListGruposConceptos) {
        this.filtrarListGruposConceptos = filtrarListGruposConceptos;
    }

    public List<UbicacionesGeograficas> getFiltrarListUbicacionesGeograficas() {
        return filtrarListUbicacionesGeograficas;
    }

    public void setFiltrarListUbicacionesGeograficas(List<UbicacionesGeograficas> filtrarListUbicacionesGeograficas) {
        this.filtrarListUbicacionesGeograficas = filtrarListUbicacionesGeograficas;
    }

    public List<TiposAsociaciones> getFiltrarListTiposAsociaciones() {
        return filtrarListTiposAsociaciones;
    }

    public void setFiltrarListTiposAsociaciones(List<TiposAsociaciones> filtrarListTiposAsociaciones) {
        this.filtrarListTiposAsociaciones = filtrarListTiposAsociaciones;
    }

    public List<Estructuras> getFiltrarListEstructuras() {
        return filtrarListEstructuras;
    }

    public void setFiltrarListEstructuras(List<Estructuras> filtrarListEstructuras) {
        this.filtrarListEstructuras = filtrarListEstructuras;
    }

    public List<Terceros> getFiltrarListTerceros() {
        return filtrarListTerceros;
    }

    public void setFiltrarListTerceros(List<Terceros> filtrarListTerceros) {
        this.filtrarListTerceros = filtrarListTerceros;
    }

    public List<Procesos> getFiltrarListProcesos() {
        return filtrarListProcesos;
    }

    public void setFiltrarListProcesos(List<Procesos> filtrarListProcesos) {
        this.filtrarListProcesos = filtrarListProcesos;
    }

    public List<Asociaciones> getFiltrarListAsociaciones() {
        return filtrarListAsociaciones;
    }

    public void setFiltrarListAsociaciones(List<Asociaciones> filtrarListAsociaciones) {
        this.filtrarListAsociaciones = filtrarListAsociaciones;
    }

    public UbicacionesGeograficas getUbicacionesGSeleccionado() {
        return ubicacionesGSeleccionado;
    }

    public void setUbicacionesGSeleccionado(UbicacionesGeograficas ubicacionesGSeleccionado) {
        this.ubicacionesGSeleccionado = ubicacionesGSeleccionado;
    }

    public TiposAsociaciones getTiposASeleccionado() {
        return tiposASeleccionado;
    }

    public void setTiposASeleccionado(TiposAsociaciones tiposASeleccionado) {
        this.tiposASeleccionado = tiposASeleccionado;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }
}

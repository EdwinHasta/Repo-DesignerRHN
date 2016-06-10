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
import InterfaceAdministrar.AdministarReportesInterface;
import InterfaceAdministrar.AdministrarNReportesNominaInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
//import javax.ejb.Remove;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.AsynchronousFilllListener;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteNomina implements Serializable {

    @EJB
    AdministrarNReportesNominaInterface administrarNReportesNomina;
    @EJB
    AdministarReportesInterface administarReportes;

    private ParametrosInformes parametroDeInforme;
    private ParametrosInformes nuevoParametroInforme;
    private List<Inforeportes> listaIR;
    private Inforeportes reporteSeleccionado;
    private List<Inforeportes> filtrarListIRU;
    private String reporteGenerar;
    private int bandera;
    private boolean aceptar;
    private Column codigoIR, reporteIR;
    //tipoIR;
    private int casilla;
    private ParametrosInformes parametroModificacion;
    private int posicionReporte;
    private String requisitosReporte;
    private InputText empleadoDesdeParametro, empleadoHastaParametro, estructuraParametro, tipoTrabajadorParametro, terceroParametro, grupoParametro;
    //private InputText empresaParametro,  procesoParametro, notasParametro, asociacionParametro, ubicacionGeograficaParametro, tipoAsociacionParametro;
    private SelectOneMenu estadoParametro;
    //LOV´s
    private List<Inforeportes> listValInforeportes;
    private Inforeportes reporteSeleccionadoLOV;
    private List<Inforeportes> filtrarLovInforeportes;
    private List<Empleados> listValEmpleados;
    private Empleados empleadoSeleccionado;
    private List<Empleados> filtrarListEmpleados;
    private List<GruposConceptos> listValGruposConceptos;
    private GruposConceptos grupoCSeleccionado;
    private List<GruposConceptos> filtrarListGruposConceptos;
    private List<UbicacionesGeograficas> listValUbicacionesGeograficas;
    private UbicacionesGeograficas ubicacionesGSeleccionado;
    private List<UbicacionesGeograficas> filtrarListUbicacionesGeograficas;
    private List<TiposAsociaciones> listValTiposAsociaciones;
    private TiposAsociaciones tiposASeleccionado;
    private List<TiposAsociaciones> filtrarListTiposAsociaciones;
    private List<Empresas> listValEmpresas;
    private Empresas empresaSeleccionada;
    private List<Empresas> filtrarListEmpresas;
    private List<Asociaciones> listValAsociaciones;
    private Asociaciones asociacionSeleccionado;
    private List<Asociaciones> filtrarListAsociaciones;
    private List<Terceros> listValTerceros;
    private Terceros terceroSeleccionado;
    private List<Terceros> filtrarListTerceros;
    private List<Procesos> listValProcesos;
    private Procesos procesoSeleccionado;
    private List<Procesos> filtrarListProcesos;
    private List<TiposTrabajadores> listValTiposTrabajadores;
    private TiposTrabajadores tipoTSeleccionado;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private List<Estructuras> listValEstructuras;
    private Estructuras estructuraSeleccionada;
    private List<Estructuras> filtrarListEstructuras;
    ////
    private String grupo, ubiGeo, tipoAso, estructura, empresa, tipoTrabajador, tercero, proceso, asociacion, estado;
    private boolean permitirIndex, cambiosReporte;
    //ALTO SCROLL TABLA
    private String altoTabla;
    //private int indice;
    //EXPORTAR REPORTE
    private String pathReporteGenerado = null;
    private String nombreReporte, tipoReporte;
    //
    private List<Inforeportes> listaInfoReportesModificados;
    //
    private String color, decoracion;
    private String color2, decoracion2;
    //
    private int casillaInforReporte;
    //
    private Date fechaDesde, fechaHasta;
    private BigInteger emplDesde, emplHasta;
    //
    private boolean activoMostrarTodos, activoBuscarReporte;
    //VISUALIZAR REPORTE PDF
    private StreamedContent reporte;
    private String cabezeraVisor;
    //Listener reporte
    private AsynchronousFilllListener asistenteReporte;
    //BANDERAS
    private boolean estadoReporte;
    private String resultadoReporte;
    //FileInputStream prueba;
    //
    private String infoRegistroEmpleadoDesde, infoRegistroEmpleadoHasta, infoRegistroGrupoConcepto, infoRegistroUbicacion, infoRegistroTipoAsociacion, infoRegistroEmpresa, infoRegistroAsociacion, infoRegistroTercero, infoRegistroProceso, infoRegistroTipoTrabajador, infoRegistroEstructura;
    private String infoRegistroReportes;
    //para Recordar
    private DataTable tabla;

    public ControlNReporteNomina() {
        System.out.println(this.getClass().getName() + ".Constructor()");
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        color = "black";
        decoracion = "none";
        color2 = "black";
        decoracion2 = "none";
        casillaInforReporte = -1;
        reporteSeleccionadoLOV = null;
        reporteSeleccionado = null;
        cambiosReporte = true;
        listaInfoReportesModificados = new ArrayList<Inforeportes>();
        parametroDeInforme = null;
        listaIR = null;
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        reporteGenerar = "";
        requisitosReporte = "";
        posicionReporte = -1;
        listValInforeportes = null;
        listValAsociaciones = null;
        listValEmpleados = null;
        listValEmpresas = null;
        listValEstructuras = null;
        listValGruposConceptos = null;
        listValProcesos = null;
        listValTerceros = null;
        listValTiposAsociaciones = null;
        listValTiposTrabajadores = null;
        listValUbicacionesGeograficas = null;

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
        altoTabla = "170";
        //prueba = new FileInputStream(new File("C:\\Users\\Administrador\\Documents\\Guia JasperReport.pdf"));
        //reporte = new DefaultStreamedContent(prueba, "application/pdf");
        //reporte = new DefaultStreamedContent();
        cabezeraVisor = null;
        estadoReporte = false;
        System.out.println(this.getClass().getName() + " fin del Constructor()");
    }

    @PostConstruct
    public void iniciarAdministradores() {
        System.out.println(this.getClass().getName() + ".iniciarAdministradores()");
        try {
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) contexto.getExternalContext().getSession(false);
            administarReportes.obtenerConexion(ses.getId());
            administrarNReportesNomina.obtenerConexion(ses.getId());

            System.out.println(this.getClass().getName() + " fin de iniciarAdministradores()");
        } catch (Exception e) {
            System.out.println("Error postconstruct controlNReporteNomina" + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void iniciarPagina() {
        System.out.println(this.getClass().getName() + ".iniciarPagina()");
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        listaIR = null;
        getListaIR();
        listValEmpleados = new ArrayList<Empleados>();
    }

    public void requisitosParaReporte() {
        System.out.println(this.getClass().getName() + ".requisitosParaReporte()");
        RequestContext context = RequestContext.getCurrentInstance();
        requisitosReporte = "";
        if (reporteSeleccionado.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
        }
        if (reporteSeleccionado.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
        }
        if (reporteSeleccionado.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
        }
        if (reporteSeleccionado.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
        }
        if (reporteSeleccionado.getGrupo().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Grupo -";
        }
        if (reporteSeleccionado.getLocalizacion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estructura -";
        }
        if (reporteSeleccionado.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
        }
        if (reporteSeleccionado.getTercero().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tercero -";
        }
        if (reporteSeleccionado.getEstado().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estado -";
        }
        if (!requisitosReporte.isEmpty()) {
            context.update("formDialogos:requisitosReporte");
            context.execute("requisitosReporte.show()");
        }
    }

    public void seleccionRegistro(Inforeportes reporte) {
        System.out.println(this.getClass().getName() + ".seleccionRegistro()");
        RequestContext context = RequestContext.getCurrentInstance();
        reporteSeleccionado = reporte;

        // Resalto Parametros Para Reporte
        defaultPropiedadesParametrosReporte();
        if (reporteSeleccionado.getFecdesde().equals("SI")) {
            color = "red";
            //RequestContext.getCurrentInstance().update("formParametros");
        }
        if (reporteSeleccionado.getFechasta().equals("SI")) {
            color2 = "red";
            //RequestContext.getCurrentInstance().update("formParametros");
        }
        if (reporteSeleccionado.getEmdesde().equals("SI")) {
            empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoDesdeParametro");
            //empleadoDesdeParametro.setStyle("position: absolute; top: 41px; left: 150px; height: 10px; font-size: 11px; width: 90px; color: red;");
            if (!empleadoDesdeParametro.getStyle().contains(" color: red;")) {
                empleadoDesdeParametro.setStyle(empleadoDesdeParametro.getStyle() + " color: red;");
            }
        }else{
            if (empleadoDesdeParametro.getStyle().contains(" color: red;")) {
                
                System.out.println("reeemplazarr "+empleadoDesdeParametro.getStyle().replace(" color: red;", ""));
                empleadoDesdeParametro.setStyle( empleadoDesdeParametro.getStyle().replace(" color: red;", "") );
            }
        }
        RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametro");
        if (reporteSeleccionado.getEmhasta().equals("SI")) {
            empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoHastaParametro");
            //empleadoHastaParametro.setStyle("position: absolute; top: 41px; left: 330px; height: 10px; font-size: 11px; width: 90px; color: red;");
            empleadoHastaParametro.setStyle(empleadoHastaParametro.getStyle() + "color: red;");
            //RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametro");
        }
        if (reporteSeleccionado.getGrupo() != null && reporteSeleccionado.getGrupo().equals("SI")) {
            grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:grupoParametro");
            //grupoParametro.setStyle("position: absolute; top: 89px; left: 150px; height: 10px; font-size: 11px; width: 130px; color: red;");
            grupoParametro.setStyle(grupoParametro.getStyle() + "color: red;");
            //RequestContext.getCurrentInstance().update("formParametros:grupoParametro");
        }
        if (reporteSeleccionado.getLocalizacion().equals("SI")) {
            estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estructuraParametro");
            //estructuraParametro.setStyle("position: absolute; top: 20px; left: 625px;height: 10px; font-size: 11px;width: 180px; color: red;");
            estructuraParametro.setStyle(estructuraParametro.getStyle() + "color: red;");
            //RequestContext.getCurrentInstance().update("formParametros:estructuraParametro");
        }
        if (reporteSeleccionado.getTipotrabajador().equals("SI")) {
            tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:tipoTrabajadorParametro");
            //tipoTrabajadorParametro.setStyle("position: absolute; top: 43px; left: 625px;height: 10px; font-size: 11px; width: 180px; color: red;");
            tipoTrabajadorParametro.setStyle(tipoTrabajadorParametro.getStyle() + "color: red;");
            //RequestContext.getCurrentInstance().update("formParametros:tipoTrabajadorParametro");
        }
        if (reporteSeleccionado.getTercero().equals("SI")) {
            terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:terceroParametro");
            //terceroParametro.setStyle("position: absolute; top: 66px; left: 625px; height: 10px; font-size: 11px; width: 180px; color: red;");
            terceroParametro.setStyle(terceroParametro.getStyle() + "color: red;");
            //RequestContext.getCurrentInstance().update("formParametros:terceroParametro");
        }
        if (reporteSeleccionado.getEstado().equals("SI")) {
            estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estadoParametro");
            estadoParametro.setStyleClass("selectOneMenuNReporteN");
            //RequestContext.getCurrentInstance().update("formParametros:estadoParametro");
        }
        RequestContext.getCurrentInstance().update("formParametros");
        context.update("form:reportesLaboral");
    }

    public void dispararDialogoGuardarCambios() {
        System.out.println(this.getClass().getName() + ".dispararDialogoGuardarCambios()");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("confirmarGuardar.show()");

    }

    public void guardarYSalir() {
        System.out.println(this.getClass().getName() + ".guardarYSalir()");
        guardarCambios();
        salir();
    }

    public void guardarCambios() {
        System.out.println(this.getClass().getName() + ".guardarCambios()");
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (cambiosReporte == false) {
                if (parametroDeInforme.getUsuario() != null) {
                    if (parametroDeInforme.getGrupo().getSecuencia() == null) {
                        parametroDeInforme.setGrupo(null);
                    }
                    if (parametroDeInforme.getUbicaciongeografica().getSecuencia() == null) {
                        parametroDeInforme.setUbicaciongeografica(null);
                    }
                    if (parametroDeInforme.getLocalizacion().getSecuencia() == null) {
                        parametroDeInforme.setLocalizacion(null);
                    }
                    if (parametroDeInforme.getTipotrabajador().getSecuencia() == null) {
                        parametroDeInforme.setTipotrabajador(null);
                    }
                    if (parametroDeInforme.getTercero().getSecuencia() == null) {
                        parametroDeInforme.setTercero(null);
                    }
                    if (parametroDeInforme.getProceso().getSecuencia() == null) {
                        parametroDeInforme.setProceso(null);
                    }
                    if (parametroDeInforme.getAsociacion().getSecuencia() == null) {
                        parametroDeInforme.setAsociacion(null);
                    }
                    if (parametroDeInforme.getTipoasociacion().getSecuencia() == null) {
                        parametroDeInforme.setTipoasociacion(null);
                    }
                    administrarNReportesNomina.modificarParametrosInformes(parametroDeInforme);
                }
                if (!listaInfoReportesModificados.isEmpty()) {
                    administrarNReportesNomina.guardarCambiosInfoReportes(listaInfoReportesModificados);
                }
                cambiosReporte = true;
                FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("form:growl");
                context.update("form:ACEPTAR");
            }
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "ha ocurrido un error en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            context.update("form:ACEPTAR");
        }
    }

    public void modificarParametroInforme() {
        System.out.println(this.getClass().getName() + ".modificarParametroInforme()");
        if (parametroDeInforme.getCodigoempleadodesde() != null && parametroDeInforme.getCodigoempleadohasta() != null
                && parametroDeInforme.getFechadesde() != null && parametroDeInforme.getFechahasta() != null) {
            if (parametroDeInforme.getFechadesde().before(parametroDeInforme.getFechahasta())) {
                parametroModificacion = parametroDeInforme;
                cambiosReporte = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
            } else {
                parametroDeInforme.setFechadesde(fechaDesde);
                parametroDeInforme.setFechahasta(fechaHasta);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formParametros");
                context.execute("errorFechas.show()");
            }
        } else {
            parametroDeInforme.setCodigoempleadodesde(emplDesde);
            parametroDeInforme.setCodigoempleadohasta(emplHasta);
            parametroDeInforme.setFechadesde(fechaDesde);
            parametroDeInforme.setFechahasta(fechaHasta);
            parametroDeInforme.getGrupo().setDescripcion(grupo);
            parametroDeInforme.getLocalizacion().setNombre(estructura);
            parametroDeInforme.getTipotrabajador().setNombre(tipoTrabajador);
            parametroDeInforme.getTercero().setNombre(tercero);
            parametroDeInforme.setEstadosolucionnodo(estado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formParametros");
            context.execute("errorRegNew.show()");
        }
    }

    public void posicionCelda(int i) {
        System.out.println(this.getClass().getName() + ".posicionCelda()");
        casilla = i;
        if (permitirIndex == true) {
            casillaInforReporte = -1;
            emplDesde = parametroDeInforme.getCodigoempleadodesde();
            fechaDesde = parametroDeInforme.getFechadesde();
            emplHasta = parametroDeInforme.getCodigoempleadohasta();
            fechaHasta = parametroDeInforme.getFechahasta();
            ubiGeo = parametroDeInforme.getUbicaciongeografica().getDescripcion();
            tipoAso = parametroDeInforme.getTipoasociacion().getDescripcion();
            empresa = parametroDeInforme.getEmpresa().getNombre();
            proceso = parametroDeInforme.getProceso().getDescripcion();
            asociacion = parametroDeInforme.getAsociacion().getDescripcion();
            grupo = parametroDeInforme.getGrupo().getDescripcion();
            estructura = parametroDeInforme.getLocalizacion().getNombre();
            tipoTrabajador = parametroDeInforme.getTipotrabajador().getNombre();
            tercero = parametroDeInforme.getTercero().getNombre();
            estado = parametroDeInforme.getEstadosolucionnodo();
        }
    }

    public void editarCelda() {
        System.out.println(this.getClass().getName() + ".editarCelda()");
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla >= 1) {
            if (casilla == 1) {
                context.update("formDialogos:editarFechaDesde");
                context.execute("editarFechaDesde.show()");
            }
            if (casilla == 2) {
                context.update("formDialogos:empleadoDesde");
                context.execute("empleadoDesde.show()");
            }
            if (casilla == 4) {
                context.update("formDialogos:grupoDesde");
                context.execute("grupoDesde.show()");
            }
            if (casilla == 5) {
                context.update("formDialogos:ubicacionGeografica");
                context.execute("ubicacionGeografica.show()");
            }
            if (casilla == 6) {
                context.update("formDialogos:tipoAsociacion");
                context.execute("tipoAsociacion.show()");
            }
            if (casilla == 7) {
                context.update("formDialogos:editarFechaHasta");
                context.execute("editarFechaHasta.show()");
            }
            if (casilla == 8) {
                context.update("formDialogos:empleadoHasta");
                context.execute("empleadoHasta.show()");
            }
            if (casilla == 10) {
                context.update("formDialogos:empresa");
                context.execute("empresa.show()");
            }
            if (casilla == 11) {
                context.update("formDialogos:estructura");
                context.execute("estructura.show()");
            }
            if (casilla == 12) {
                context.update("formDialogos:tipoTrabajador");
                context.execute("tipoTrabajador.show()");
            }
            if (casilla == 13) {
                context.update("formDialogos:tercero");
                context.execute("tercero.show()");
            }
            if (casilla == 14) {
                context.update("formDialogos:proceso");
                context.execute("proceso.show()");
            }
            if (casilla == 15) {
                context.update("formDialogos:notas");
                context.execute("notas.show()");
            }
            if (casilla == 16) {
                context.update("formDialogos:asociacion");
                context.execute("asociacion.show()");
            }
            casilla = -1;
        }
        if (casillaInforReporte >= 1) {
            if (casillaInforReporte == 1) {
                context.update("formParametros:infoReporteCodigoD");
                context.execute("infoReporteCodigoD.show()");
            }
            if (casillaInforReporte == 2) {
                context.update("formParametros:infoReporteNombreD");
                context.execute("infoReporteNombreD.show()");
            }
            casillaInforReporte = -1;
        }
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        System.out.println(this.getClass().getName() + ".autocompletarGeneral()");
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("GRUPO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getGrupo().setDescripcion(grupo);
                for (int i = 0; i < listValGruposConceptos.size(); i++) {
                    if (listValGruposConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setGrupo(listValGruposConceptos.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValGruposConceptos.clear();
                    getListValGruposConceptos();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:GruposConceptosDialogo");
                    context.execute("GruposConceptosDialogo.show()");
                }
            } else {
                parametroDeInforme.setGrupo(new GruposConceptos());
                parametroModificacion = parametroDeInforme;
                listValGruposConceptos.clear();
                getListValGruposConceptos();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("UBIGEO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getUbicaciongeografica().setDescripcion(ubiGeo);
                for (int i = 0; i < listValUbicacionesGeograficas.size(); i++) {
                    if (listValUbicacionesGeograficas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setUbicaciongeografica(listValUbicacionesGeograficas.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValUbicacionesGeograficas.clear();
                    getListValUbicacionesGeograficas();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:UbiGeograficaDialogo");
                    context.execute("UbiGeograficaDialogo.show()");
                }
            } else {
                parametroDeInforme.setUbicaciongeografica(new UbicacionesGeograficas());
                parametroModificacion = parametroDeInforme;
                listValUbicacionesGeograficas.clear();
                getListValUbicacionesGeograficas();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("EMPRESA")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getEmpresa().setNombre(empresa);
                for (int i = 0; i < listValEmpresas.size(); i++) {
                    if (listValEmpresas.get(i).getNombre().startsWith(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setEmpresa(listValEmpresas.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValEmpresas.clear();
                    getListValEmpresas();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:EmpresaDialogo");
                    context.execute("EmpresaDialogo.show()");
                }
            } else {
                parametroDeInforme.setEmpresa(new Empresas());
                parametroModificacion = parametroDeInforme;
                listValEmpresas.clear();
                getListValEmpresas();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOASO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getTipoasociacion().setDescripcion(tipoAso);
                for (int i = 0; i < listValTiposAsociaciones.size(); i++) {
                    if (listValTiposAsociaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setTipoasociacion(listValTiposAsociaciones.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValTiposAsociaciones.clear();
                    getListValTiposAsociaciones();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:TipoAsociacionDialogo");
                    context.execute("TipoAsociacionDialogo.show()");
                }
            } else {
                parametroDeInforme.setTipoasociacion(new TiposAsociaciones());
                parametroModificacion = parametroDeInforme;
                listValTiposAsociaciones.clear();
                getListValTiposAsociaciones();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("ESTRUCTURA")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getLocalizacion().setNombre(estructura);
                for (int i = 0; i < listValEstructuras.size(); i++) {
                    if (listValEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setLocalizacion(listValEstructuras.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValEstructuras.clear();
                    getListValEstructuras();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:EstructuraDialogo");
                    context.execute("EstructuraDialogo.show()");
                }
            } else {
                parametroDeInforme.setLocalizacion(new Estructuras());
                parametroModificacion = parametroDeInforme;
                listValEstructuras.clear();
                getListValEstructuras();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOTRABAJADOR")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getTipotrabajador().setNombre(tipoTrabajador);
                for (int i = 0; i < listValTiposTrabajadores.size(); i++) {
                    if (listValTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setTipotrabajador(listValTiposTrabajadores.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValTiposTrabajadores.clear();
                    getListValTiposTrabajadores();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:TipoTrabajadorDialogo");
                    context.execute("TipoTrabajadorDialogo.show()");
                }
            } else {
                parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
                parametroModificacion = parametroDeInforme;
                listValTiposTrabajadores.clear();
                getListValTiposTrabajadores();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TERCERO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getTercero().setNombre(tercero);
                for (int i = 0; i < listValTerceros.size(); i++) {
                    if (listValTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setTercero(listValTerceros.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValTerceros.clear();
                    getListValTerceros();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:TerceroDialogo");
                    context.execute("TerceroDialogo.show()");
                }
            } else {
                parametroDeInforme.setTercero(new Terceros());
                parametroModificacion = parametroDeInforme;
                listValTerceros.clear();
                getListValTerceros();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("PROCESO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getProceso().setDescripcion(proceso);
                for (int i = 0; i < listValProcesos.size(); i++) {
                    if (listValProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setProceso(listValProcesos.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValProcesos.clear();
                    getListValProcesos();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:ProcesoDialogo");
                    context.execute("ProcesoDialogo.show()");
                }
            } else {
                parametroDeInforme.setProceso(new Procesos());
                parametroModificacion = parametroDeInforme;
                listValProcesos.clear();
                getListValProcesos();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("ASOCIACION")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getAsociacion().setDescripcion(asociacion);
                for (int i = 0; i < listValAsociaciones.size(); i++) {
                    if (listValAsociaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setAsociacion(listValAsociaciones.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listValAsociaciones.clear();
                    getListValAsociaciones();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:AsociacionDialogo");
                    context.execute("AsociacionDialogo.show()");
                }
            } else {
                parametroDeInforme.setAsociacion(new Asociaciones());
                parametroModificacion = parametroDeInforme;
                listValAsociaciones.clear();
                getListValAsociaciones();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("DESDE")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.setCodigoempleadodesde(emplDesde);
                for (int i = 0; i < listValEmpleados.size(); i++) {
                    if (listValEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setCodigoempleadodesde(listValEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                    parametroModificacion = parametroDeInforme;
                    listValEmpleados.clear();
                    getListValEmpleados();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    if ((listValEmpleados == null) || listValEmpleados.isEmpty()) {
                        listValEmpleados = null;
                    }
                    context.update("form:EmpleadoDesdeDialogo");
                    context.execute("EmpleadoDesdeDialogo.show()");
                }
            } else {
                parametroDeInforme.setCodigoempleadodesde(new BigInteger("0"));
                parametroModificacion = parametroDeInforme;
                listValEmpleados.clear();
                getListValEmpleados();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("HASTA")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.setCodigoempleadohasta(emplHasta);
                for (int i = 0; i < listValEmpleados.size(); i++) {
                    if (listValEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setCodigoempleadohasta(listValEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                    parametroModificacion = parametroDeInforme;
                    listValEmpleados.clear();
                    getListValEmpleados();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:EmpleadoHastaDialogo");
                    context.execute("EmpleadoHastaDialogo.show()");
                }
            } else {
                parametroDeInforme.setCodigoempleadohasta(new BigInteger("9999999999999999999999"));
                parametroModificacion = parametroDeInforme;
                listValEmpleados.clear();
                getListValEmpleados();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
    }

    public void listaValoresBoton() {
        System.out.println(this.getClass().getName() + ".listaValoresBoton()");
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 2) {
            if ((listValEmpleados == null) || listValEmpleados.isEmpty()) {
                listValEmpleados = null;
            }
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (casilla == 4) {
            context.update("form:GruposConceptosDialogo");
            context.execute("GruposConceptosDialogo.show()");
        }
        if (casilla == 5) {
            context.update("form:UbiGeograficaDialogo");
            context.execute("UbiGeograficaDialogo.show()");
        }
        if (casilla == 6) {
            context.update("form:TipoAsociacionDialogo");
            context.execute("TipoAsociacionDialogo.show()");
        }
        if (casilla == 8) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (casilla == 10) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
        if (casilla == 11) {
            context.update("form:EstructuraDialogo");
            context.execute("EstructuraDialogo.show()");
        }
        if (casilla == 12) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (casilla == 13) {
            context.update("form:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        }
        if (casilla == 14) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
        if (casilla == 16) {
            context.update("form:AsociacionDialogo");
            context.execute("AsociacionDialogo.show()");
        }
    }

    public void dialogosParametros(int pos) {
        System.out.println(this.getClass().getName() + ".dialogosParametros()");
        RequestContext context = RequestContext.getCurrentInstance();
        if (pos == 2) {
            if ((listValEmpleados == null) || listValEmpleados.isEmpty()) {
                listValEmpleados = null;
            }
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (pos == 4) {
            context.update("form:GruposConceptosDialogo");
            context.execute("GruposConceptosDialogo.show()");
        }
        if (pos == 5) {
            context.update("form:UbiGeograficaDialogo");
            context.execute("UbiGeograficaDialogo.show()");
        }
        if (pos == 6) {
            context.update("form:TipoAsociacionDialogo");
            context.execute("TipoAsociacionDialogo.show()");
        }
        if (pos == 8) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (pos == 10) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
        if (pos == 11) {
            context.update("form:EstructuraDialogo");
            context.execute("EstructuraDialogo.show()");
        }
        if (pos == 12) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (pos == 13) {
            context.update("form:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        }
        if (pos == 14) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
        if (pos == 16) {
            context.update("form:AsociacionDialogo");
            context.execute("AsociacionDialogo.show()");
        }

    }

    public void activarAceptar() {
        System.out.println(this.getClass().getName() + ".activarAceptar()");
        aceptar = false;
    }

    public void actualizarEmplDesde() {
        System.out.println(this.getClass().getName() + ".actualizarEmplDesde()");
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoDesde:globalFilter");
        context.execute("lovEmpleadoDesde.clearFilters()");
        context.execute("EmpleadoDesdeDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoDesdeParametro");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;

    }

    public void cancelarCambioEmplDesde() {
        System.out.println(this.getClass().getName() + ".cancelarCambioEmplDesde()");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoDesde:globalFilter");
        context.execute("lovEmpleadoDesde.clearFilters()");
        context.execute("EmpleadoDesdeDialogo.hide()");
    }

    public void actualizarEmplHasta() {
        System.out.println(this.getClass().getName() + ".actualizarEmplHasta()");
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadohasta(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoHasta:globalFilter");
        context.execute("lovEmpleadoHasta.clearFilters()");
        context.execute("EmpleadoHastaDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoHastaParametro");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
    }

    public void cancelarCambioEmplHasta() {
        System.out.println(this.getClass().getName() + ".cancelarCambioEmplHasta()");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoHasta:globalFilter");
        context.execute("lovEmpleadoHasta.clearFilters()");
        context.execute("EmpleadoHastaDialogo.hide()");
    }

    public void actualizarGrupo() {
        System.out.println(this.getClass().getName() + ".actualizarGrupo()");
        permitirIndex = true;
        parametroDeInforme.setGrupo(grupoCSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovGruposConceptos:globalFilter");
        context.execute("lovGruposConceptos.clearFilters()");
        context.execute("GruposConceptosDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:grupoParametro");
        grupoCSeleccionado = null;
        aceptar = true;
        filtrarListGruposConceptos = null;

    }

    public void cancelarCambioGrupo() {
        System.out.println(this.getClass().getName() + ".cancelarCambioGrupo()");
        grupoCSeleccionado = null;
        aceptar = true;
        filtrarListGruposConceptos = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovGruposConceptos:globalFilter");
        context.execute("lovGruposConceptos.clearFilters()");
        context.execute("GruposConceptosDialogo.hide()");
    }

    public void actualizarUbicacionGeografica() {
        System.out.println(this.getClass().getName() + ".actualizarUbicacionGeografica()");
        permitirIndex = true;
        parametroDeInforme.setUbicaciongeografica(ubicacionesGSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovUbicacionGeografica:globalFilter");
        context.execute("lovUbicacionGeografica.clearFilters()");
        context.execute("UbiGeograficaDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:ubicacionGeograficaParametro");
        ubicacionesGSeleccionado = null;
        aceptar = true;
        filtrarListUbicacionesGeograficas = null;

    }

    public void cancelarCambioUbicacionGeografica() {
        System.out.println(this.getClass().getName() + ".cancelarCambioUbicacionGeografica()");
        ubicacionesGSeleccionado = null;
        aceptar = true;
        filtrarListUbicacionesGeograficas = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovUbicacionGeografica:globalFilter");
        context.execute("lovUbicacionGeografica.clearFilters()");
        context.execute("UbiGeograficaDialogo.hide()");
    }

    public void actualizarTipoAsociacion() {
        System.out.println(this.getClass().getName() + ".actualizarTipoAsociacion()");
        permitirIndex = true;
        parametroDeInforme.setTipoasociacion(tiposASeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTipoAsociacion:globalFilter");
        context.execute("lovTipoAsociacion.clearFilters()");
        context.execute("TipoAsociacionDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:tipoAsociacionParametro");
        tiposASeleccionado = null;
        aceptar = true;
        filtrarListTiposAsociaciones = null;

    }

    public void cancelarTipoAsociacion() {
        System.out.println(this.getClass().getName() + ".cancelarTipoAsociacion()");
        tiposASeleccionado = null;
        aceptar = true;
        filtrarListTiposAsociaciones = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTipoAsociacion:globalFilter");
        context.execute("lovTipoAsociacion.clearFilters()");
        context.execute("TipoAsociacionDialogo.hide()");
    }

    public void actualizarEmpresa() {
        System.out.println(this.getClass().getName() + ".actualizarEmpresa()");
        permitirIndex = true;
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpresa:globalFilter");
        context.execute("lovEmpresa.clearFilters()");
        context.execute("EmpresaDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:empresaParametro");
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;

    }

    public void cancelarEmpresa() {
        System.out.println(this.getClass().getName() + ".cancelarEmpresa()");
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpresa:globalFilter");
        context.execute("lovEmpresa.clearFilters()");
        context.execute("EmpresaDialogo.hide()");
    }

    public void actualizarEstructura() {
        System.out.println(this.getClass().getName() + ".actualizarEstructura()");
        parametroDeInforme.setLocalizacion(estructuraSeleccionada);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEstructura:globalFilter");
        context.execute("lovEstructura.clearFilters()");
        context.execute("EstructuraDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:estructuraParametro");
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;

    }

    public void cancelarEstructura() {
        System.out.println(this.getClass().getName() + ".cancelarEstructura()");
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEstructura:globalFilter");
        context.execute("lovEstructura.clearFilters()");
        context.execute("EstructuraDialogo.hide()");
    }

    public void actualizarTipoTrabajador() {
        System.out.println(this.getClass().getName() + ".actualizarTipoTrabajador()");
        parametroDeInforme.setTipotrabajador(tipoTSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:tipoTrabajadorParametro");
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;

    }

    public void cancelarTipoTrabajador() {
        System.out.println(this.getClass().getName() + ".cancelarTipoTrabajador()");
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTipoTrabajador:globalFilter");
        context.execute("lovTipoTrabajador.clearFilters()");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    public void actualizarTercero() {
        System.out.println(this.getClass().getName() + ".actualizarTercero()");
        permitirIndex = true;
        parametroDeInforme.setTercero(terceroSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:terceroParametro");
        terceroSeleccionado = null;
        aceptar = true;
        filtrarListTerceros = null;

    }

    public void cancelarTercero() {
        System.out.println(this.getClass().getName() + ".cancelarTercero()");
        terceroSeleccionado = null;
        aceptar = true;
        filtrarListTerceros = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");
    }

    public void actualizarProceso() {
        System.out.println(this.getClass().getName() + ".actualizarProceso()");
        permitirIndex = true;
        parametroDeInforme.setProceso(procesoSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProceso:globalFilter");
        context.execute("lovProceso.clearFilters()");
        context.execute("ProcesoDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:procesoParametro");
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;

    }

    public void cancelarProceso() {
        System.out.println(this.getClass().getName() + ".cancelarProceso()");
        procesoSeleccionado = null;
        aceptar = true;
        filtrarListProcesos = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovProceso:globalFilter");
        context.execute("lovProceso.clearFilters()");
        context.execute("ProcesoDialogo.hide()");
    }

    public void actualizarAsociacion() {
        System.out.println(this.getClass().getName() + ".actualizarAsociacion()");
        permitirIndex = true;
        parametroDeInforme.setAsociacion(asociacionSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovAsociacion:globalFilter");
        context.execute("lovAsociacion.clearFilters()");
        context.execute("AsociacionDialogo.hide()");

        context.update("form:ACEPTAR");
        context.update("formParametros:asociacionParametro");
        asociacionSeleccionado = null;
        aceptar = true;
        filtrarListAsociaciones = null;

    }

    public void cancelarAsociacion() {
        System.out.println(this.getClass().getName() + ".cancelarAsociacion()");
        asociacionSeleccionado = null;
        aceptar = true;
        filtrarListAsociaciones = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovAsociacion:globalFilter");
        context.execute("lovAsociacion.clearFilters()");
        context.execute("AsociacionDialogo.hide()");
    }

    public void mostrarDialogoGenerarReporte() {
        System.out.println(this.getClass().getName() + ".mostrarDialogoGenerarReporte()");
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formDialogos:reporteAGenerar");
        context.execute("reporteAGenerar.show()");
    }

    public void cancelarGenerarReporte() {
        System.out.println(this.getClass().getName() + ".cancelarGenerarReporte()");
        reporteGenerar = "";
        posicionReporte = -1;
    }

    public void mostrarDialogoBuscarReporte() {
        System.out.println(this.getClass().getName() + ".mostrarDialogoBuscarReporte()");
        try {
            if (cambiosReporte == true) {
                //getListValInforeportes();
                if (listValInforeportes != null) {
                    infoRegistroReportes = "Cantidad de registros : " + listValInforeportes.size();
                } else {
                    infoRegistroReportes = "Cantidad de registros : 0";
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ReportesDialogo");
                context.execute("ReportesDialogo.show()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarGuardarSinSalida.show()");
            }
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }

    public void salir() {
        System.out.println(this.getClass().getName() + ".salir()");
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "170";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListIRU = null;
        }
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listValAsociaciones = null;
        listValEmpleados = null;
        listValEmpresas = null;
        listValEstructuras = null;
        listValGruposConceptos = null;
        listValProcesos = null;
        listValTerceros = null;
        listValTiposAsociaciones = null;
        listValTiposTrabajadores = null;
        listValUbicacionesGeograficas = null;
        casilla = -1;
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        reporteSeleccionado = null;
        reporteSeleccionadoLOV = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void actualizarSeleccionInforeporte() {
        System.out.println(this.getClass().getName() + ".actualizarSeleccionInforeporte()");
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "170";
            FacesContext c = FacesContext.getCurrentInstance();
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListIRU = null;
        }
        defaultPropiedadesParametrosReporte();
        listaIR.clear();
        listaIR.add(reporteSeleccionadoLOV);
        filtrarListIRU = null;
        filtrarLovInforeportes = null;
        aceptar = true;
        activoBuscarReporte = true;
        activoMostrarTodos = false;
        reporteSeleccionado = null;
        reporteSeleccionadoLOV = null;
        context.update("form:MOSTRARTODOS");
        context.update("form:BUSCARREPORTE");
        context.reset("form:lovReportesDialogo:globalFilter");
        context.execute("lovReportesDialogo.clearFilters()");
        context.execute("ReportesDialogo.hide()");
        context.update("form:reportesNomina");
    }

    public void cancelarSeleccionInforeporte() {
        System.out.println(this.getClass().getName() + ".cancelarSeleccionInforeporte()");
        filtrarListIRU = null;
        reporteSeleccionadoLOV = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovReportesDialogo:globalFilter");
        context.execute("lovReportesDialogo.clearFilters()");
        context.execute("ReportesDialogo.hide()");
    }

    public void mostrarTodos() {
        System.out.println(this.getClass().getName() + ".mostrarTodos()");
        if (cambiosReporte == true) {
            defaultPropiedadesParametrosReporte();
            listaIR.clear();
            for (int i = 0; i < listValInforeportes.size(); i++) {
                listaIR.add(listValInforeportes.get(i));
            }
            RequestContext context = RequestContext.getCurrentInstance();
            activoBuscarReporte = false;
            activoMostrarTodos = true;
            context.update("form:MOSTRARTODOS");
            context.update("form:BUSCARREPORTE");
            context.update("form:reportesNomina");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void cancelarYSalir() {
        System.out.println(this.getClass().getName() + ".cancelarYSalir()");
        cancelarModificaciones();
        salir();
    }

    public void cancelarModificaciones() {
        System.out.println(this.getClass().getName() + ".cancelarModificaciones()");
        if (bandera == 1) {
            altoTabla = "170";
            FacesContext c = FacesContext.getCurrentInstance();
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListIRU = null;
        }
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        casilla = -1;
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        getParametroDeInforme();
        getListaIR();
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        reporteSeleccionado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:MOSTRARTODOS");
        context.update("form:BUSCARREPORTE");
        context.update("form:ACEPTAR");
        context.update("form:reportesNomina");
        context.update("formParametros:fechaDesdeParametro");
        context.update("formParametros:empleadoDesdeParametro");
        context.update("formParametros:estadoParametro");
        context.update("formParametros:grupoParametro");
        context.update("formParametros:ubicacionGeograficaParametro");
        context.update("formParametros:tipoAsociacionParametro");
        context.update("formParametros:fechaHastaParametro");
        context.update("formParametros:empleadoHastaParametro");
        context.update("formParametros:tipoPersonalParametro");
        context.update("formParametros:empresaParametro");
        context.update("formParametros:estructuraParametro");
        context.update("formParametros:tipoTrabajadorParametro");
        context.update("formParametros:terceroParametro");
        context.update("formParametros:notasParametro");
        context.update("formParametros:asociacionParametro");
    }

    public void activarCtrlF11() {
        System.out.println(this.getClass().getName() + ".activarCtrlF11()");
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "148";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("width: 96%");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("width: 96%");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "170";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListIRU = null;
            defaultPropiedadesParametrosReporte();
        }
    }

    public void modificacionTipoReporte(Inforeportes reporte) {
        System.out.println(this.getClass().getName() + ".modificacionTipoReporte()");
        reporteSeleccionado = reporte;
        cambiosReporte = false;
        if (listaInfoReportesModificados.isEmpty()) {
            listaInfoReportesModificados.add(reporteSeleccionado);
        } else {
            if ((!listaInfoReportesModificados.isEmpty()) && (!listaInfoReportesModificados.contains(reporteSeleccionado))) {
                listaInfoReportesModificados.add(reporteSeleccionado);
            } else {
                int posicion = listaInfoReportesModificados.indexOf(reporteSeleccionado);
                listaInfoReportesModificados.set(posicion, reporteSeleccionado);
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    /*public void reporteSeleccionado(int i) {
     System.out.println(this.getClass().getName() + ".reporteSeleccionado()");
     System.out.println("Posicion del reporte : " + i);
     }*/
    public void defaultPropiedadesParametrosReporte() {
        System.out.println(this.getClass().getName() + ".defaultPropiedadesParametrosReporte()");
        color = "black";
        decoracion = "none";
        color2 = "black";
        decoracion2 = "none";
        /*
         RequestContext.getCurrentInstance().update("formParametros");

         empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoDesdeParametro");
         empleadoDesdeParametro.setStyle("position: absolute; top: 41px; left: 150px; height: 10px; font-size: 11px; width: 90px;");
         RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametro");

         empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoHastaParametro");
         empleadoHastaParametro.setStyle("position: absolute; top: 41px; left: 330px; height: 10px; font-size: 11px; width: 90px;");
         RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametro");

         grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:grupoParametro");
         grupoParametro.setStyle("position: absolute; top: 89px; left: 150px; height: 10px; font-size: 11px; width: 130px;");
         RequestContext.getCurrentInstance().update("formParametros:grupoParametro");
        
         estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estructuraParametro");
         estructuraParametro.setStyle("position: absolute; top: 20px; left: 625px;height: 10px; font-size: 11px;width: 180px;");
         RequestContext.getCurrentInstance().update("formParametros:estructuraParametro");

         tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:tipoTrabajadorParametro");
         tipoTrabajadorParametro.setStyle("position: absolute; top: 43px; left: 625px;height: 10px; font-size: 11px; width: 180px;");
         RequestContext.getCurrentInstance().update("formParametros:tipoTrabajadorParametro");

         terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:terceroParametro");
         terceroParametro.setStyle("position: absolute; top: 66px; left: 625px; height: 10px; font-size: 11px; width: 180px;");
         RequestContext.getCurrentInstance().update("formParametros:terceroParametro");

         estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estadoParametro");
         estadoParametro.setStyleClass("position: absolute; top: 63px; left: 150px; width: 100px; text-transform: uppercase;");
         RequestContext.getCurrentInstance().update("formParametros:estadoParametro");
         */
    }

    public void cancelarRequisitosReporte() {
        System.out.println(this.getClass().getName() + ".cancelarRequisitosReporte()");
        requisitosReporte = "";
    }

    public ParametrosInformes getParametroDeInforme() {
        System.out.println(this.getClass().getName() + ".getParametroDeInforme()");
        try {
            if (parametroDeInforme == null) {
                parametroDeInforme = new ParametrosInformes();
                parametroDeInforme = administrarNReportesNomina.parametrosDeReporte();
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
            if (parametroDeInforme.getEmpresa() == null) {
                parametroDeInforme.setEmpresa(new Empresas());
            }
            return parametroDeInforme;
        } catch (Exception e) {
            System.out.println("Error getParametroDeInforme : " + e);
            return null;
        }
    }

    public void generarReporte(Inforeportes reporte) {
        System.out.println(this.getClass().getName() + ".generarReporte()");
        seleccionRegistro(reporte);
        RequestContext context = RequestContext.getCurrentInstance();
        if (reporteSeleccionado != null) {
            nombreReporte = reporteSeleccionado.getNombrereporte();
            tipoReporte = reporteSeleccionado.getTipo();

            if (nombreReporte != null && tipoReporte != null) {
                pathReporteGenerado = administarReportes.generarReporte(nombreReporte, tipoReporte);
            }
            if (pathReporteGenerado != null) {
                context.execute("validarDescargaReporte();");
            }
        }
    }
    /*
     * public void generarReporte() { /*HttpServletRequest request =
     * (HttpServletRequest)
     * FacesContext.getCurrentInstance().getExternalContext().getRequest();
     * HttpServletResponse response = (HttpServletResponse)
     * FacesContext.getCurrentInstance().getExternalContext().getResponse();
     * FacesContext con = getFacesContext(request, response);
     */
    //System.out.println("Faces: " + con);
    //FacesContext f = FacesContext.getCurrentInstance();
    ///System.out.println("Estado respuesta: " + f.getResponseComplete());
      /*
     * RequestContext context = RequestContext.getCurrentInstance();
     * //System.out.println("Context: " + f); if (tipoLista == 0) {
     * nombreReporte = listaIR.get(indice).getNombrereporte(); tipoReporte =
     * listaIR.get(indice).getTipo(); } else { nombreReporte =
     * filtrarListInforeportesUsuario.get(indice).getNombrereporte();
     * tipoReporte = filtrarListInforeportesUsuario.get(indice).getTipo(); } if
     * (asistenteReporte == null) { asistenteReporte = listener();
     * System.out.println("Creo el listener. :D"); } /*if (nombreReporte != null
     * && tipoReporte != null) { pathReporteGenerado =
     * administarReportes.generarReporte(nombreReporte, tipoReporte,
     * asistenteReporte); }
     */
    /*
     * if (nombreReporte != null) {
     * administarReportes.iniciarLlenadoReporte(nombreReporte,
     * asistenteReporte); } /* if (pathReporteGenerado != null) {
     * //context.execute("exportarReporte();"); System.out.println("Pasooo"); /*
     * try { //exportarReporte(); } catch (IOException ex) {
     * Logger.getLogger(ControlNReporteNomina.class.getName()).log(Level.SEVERE,
     * null, ex); }
     */
    //}
    //context.execute("dlg.hide()");
    // context.execute("esperarReporte();");
    // }

    public AsynchronousFilllListener listener() {
        System.out.println(this.getClass().getName() + ".listener()");
        return new AsynchronousFilllListener() {
            //RequestContext context = c;

            @Override
            public void reportFinished(JasperPrint jp) {
                System.out.println(this.getClass().getName() + ".listener().reportFinished()");
                try {
                    estadoReporte = true;
                    resultadoReporte = "Exito";
                    /*
                     * final RequestContext currentInstance =
                     * RequestContext.getCurrentInstance();
                     * Renderer.instance().render(template);
                     * RequestContext.setCurrentInstance(currentInstance)
                     */
                    // context.execute("formDialogos:generandoReporte");
                    //generarArchivoReporte(jp);
                } catch (Exception e) {
                    System.out.println("ControlNReporteNomina reportFinished ERROR: " + e.toString());
                }
            }

            @Override
            public void reportCancelled() {
                System.out.println(this.getClass().getName() + ".listener().reportCancelled()");
                estadoReporte = true;
                resultadoReporte = "Cancelación";
            }

            @Override
            public void reportFillError(Throwable e) {
                System.out.println(this.getClass().getName() + ".listener().reportFillError()");
                if (e.getCause() != null) {
                    pathReporteGenerado = "ControlNReporteNomina reportFillError Error: " + e.toString() + "\n" + e.getCause().toString();
                } else {
                    pathReporteGenerado = "ControlNReporteNomina reportFillError Error: " + e.toString();
                }
                estadoReporte = true;
                resultadoReporte = "Se estallo";
            }
        };
    }

    public void generarArchivoReporte(JasperPrint print) {
        System.out.println(this.getClass().getName() + ".generarArchivoReporte()");
        if (print != null && tipoReporte != null) {
            pathReporteGenerado = administarReportes.crearArchivoReporte(print, tipoReporte);
            validarDescargaReporte();
        }
    }

    public void exportarReporte() throws IOException {
        System.out.println(this.getClass().getName() + ".exportarReporte()");
        if (pathReporteGenerado != null) {
            File reporteF = new File(pathReporteGenerado);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(reporteF);
            byte[] bytes = new byte[1024];
            int read;
            if (!ctx.getResponseComplete()) {
                String fileName = reporteF.getName();
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();

                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                ctx.responseComplete();
            }
        }
    }

    public void validarDescargaReporte() {
        System.out.println(this.getClass().getName() + ".validarDescargaReporte()");
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("generandoReporte.hide();");
        if (pathReporteGenerado != null && !pathReporteGenerado.startsWith("Error:")) {
            if (!tipoReporte.equals("PDF")) {
                context.execute("descargarReporte.show();");
            } else {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(new File(pathReporteGenerado));
                    reporte = new DefaultStreamedContent(fis, "application/pdf");
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getCause());
                    reporte = null;
                }
                if (reporte != null) {
                    if (reporteSeleccionado != null) {
                        cabezeraVisor = "Reporte - " + reporteSeleccionado.getNombre();
                    } else {
                        cabezeraVisor = "Reporte - ";
                    }
                    context.update("formDialogos:verReportePDF");
                    context.execute("verReportePDF.show();");
                }
                pathReporteGenerado = null;
            }
        } else {
            context.update("formDialogos:errorGenerandoReporte");
            context.execute("errorGenerandoReporte.show();");
        }
    }

    public void reiniciarStreamedContent() {
        System.out.println(this.getClass().getName() + ".reiniciarStreamedContent()");
        reporte = null;
    }

    public void cancelarReporte() {
        System.out.println(this.getClass().getName() + ".cancelarReporte()");
        administarReportes.cancelarReporte();
    }

    //GETTER AND SETTER
    public void setParametroDeInforme(ParametrosInformes parametroDeInforme) {
        System.out.println(this.getClass().getName() + ".setParametroDeInforme()");
        this.parametroDeInforme = parametroDeInforme;
    }

    public List<Inforeportes> getListaIR() {
        System.out.println(this.getClass().getName() + ".getListaIR()");
        try {
            if (listaIR == null) {
                System.out.println(this.getClass().getName() + ".getListaIR() consulta lista");
                listaIR = administrarNReportesNomina.listInforeportesUsuario();
            }
            return listaIR;
        } catch (Exception e) {
            System.out.println("Error getListInforeportesUsuario : " + e);
            return null;
        }
    }

    public void recordarSeleccion() {
        System.out.println(this.getClass().getName() + ".recordarSeleccion()");
        if (reporteSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:reportesNomina");
            tabla.setSelection(reporteSeleccionado);
        } else {
            reporteSeleccionado = null;
        }
    }

    public void setListaIR(List<Inforeportes> listaIR) {
        System.out.println(this.getClass().getName() + ".setListaIR()");
        this.listaIR = listaIR;
    }

    public List<Inforeportes> getFiltrarListInforeportesUsuario() {
        System.out.println(this.getClass().getName() + ".getFiltrarListInforeportesUsuario()");
        return filtrarListIRU;
    }

    public void setFiltrarListInforeportesUsuario(List<Inforeportes> filtrarListInforeportesUsuario) {
        System.out.println(this.getClass().getName() + ".setFiltrarListInforeportesUsuario()");
        this.filtrarListIRU = filtrarListInforeportesUsuario;
    }

    public Inforeportes getReporteSeleccionadoLOV() {
        System.out.println(this.getClass().getName() + ".getReporteSeleccionadoLOV()");
        return reporteSeleccionadoLOV;
    }

    public void setReporteSeleccionadoLOV(Inforeportes inforreporteSeleccionadoLov) {
        System.out.println(this.getClass().getName() + ".setReporteSeleccionadoLOV()");
        this.reporteSeleccionadoLOV = inforreporteSeleccionadoLov;
    }

    public boolean isAceptar() {
        System.out.println(this.getClass().getName() + ".isAceptar()");
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        System.out.println(this.getClass().getName() + ".setAceptar()");
        this.aceptar = aceptar;
    }

    public ParametrosInformes getNuevoParametroInforme() {
        System.out.println(this.getClass().getName() + ".getNuevoParametroInforme()");
        return nuevoParametroInforme;
    }

    public void setNuevoParametroInforme(ParametrosInformes nuevoParametroInforme) {
        System.out.println(this.getClass().getName() + ".setNuevoParametroInforme()");
        this.nuevoParametroInforme = nuevoParametroInforme;
    }

    public ParametrosInformes getParametroModificacion() {
        System.out.println(this.getClass().getName() + ".getParametroModificacion()");
        return parametroModificacion;
    }

    public void setParametroModificacion(ParametrosInformes parametroModificacion) {
        System.out.println(this.getClass().getName() + ".setParametroModificacion()");
        this.parametroModificacion = parametroModificacion;
    }

    public String getReporteGenerar() {
        System.out.println(this.getClass().getName() + ".getReporteGenerar()");
        if (posicionReporte != -1) {
            reporteGenerar = listaIR.get(posicionReporte).getNombre();
        }
        return reporteGenerar;
    }

    public void setReporteGenerar(String reporteGenerar) {
        System.out.println(this.getClass().getName() + ".setReporteGenerar()");
        this.reporteGenerar = reporteGenerar;
    }

    public String getRequisitosReporte() {
        System.out.println(this.getClass().getName() + ".getRequisitosReporte()");
        return requisitosReporte;
    }

    public void setRequisitosReporte(String requisitosReporte) {
        System.out.println(this.getClass().getName() + ".setRequisitosReporte()");
        this.requisitosReporte = requisitosReporte;
    }

    public List<Empleados> getListValEmpleados() {
        System.out.println(this.getClass().getName() + ".getListValEmpleados()");
        if (listValEmpleados == null) {
            System.out.println(this.getClass().getName() + " consulta la lista de empleados");
            listValEmpleados = administrarNReportesNomina.listEmpleados();
        }
        return listValEmpleados;
    }

    public void setListValEmpleados(List<Empleados> listEmpleados) {
        System.out.println(this.getClass().getName() + ".setListValEmpleados()");
        this.listValEmpleados = listEmpleados;
    }

    public List<Empresas> getListValEmpresas() {
        System.out.println(this.getClass().getName() + ".getListValEmpresas()");
        if (listValEmpresas == null) {
            listValEmpresas = administrarNReportesNomina.listEmpresas();
        }
        return listValEmpresas;
    }

    public void setListValEmpresas(List<Empresas> listEmpresas) {
        System.out.println(this.getClass().getName() + ".setListValEmpresas()");
        this.listValEmpresas = listEmpresas;
    }

    public List<GruposConceptos> getListValGruposConceptos() {
        System.out.println(this.getClass().getName() + ".getListValGruposConceptos()");
        if (listValGruposConceptos == null) {
            listValGruposConceptos = administrarNReportesNomina.listGruposConcetos();
        }
        return listValGruposConceptos;
    }

    public void setListValGruposConceptos(List<GruposConceptos> listGruposConceptos) {
        System.out.println(this.getClass().getName() + ".setListValGruposConceptos()");
        this.listValGruposConceptos = listGruposConceptos;
    }

    public List<UbicacionesGeograficas> getListValUbicacionesGeograficas() {
        System.out.println(this.getClass().getName() + ".getListValUbicacionesGeograficas()");
        if (listValUbicacionesGeograficas == null) {
            listValUbicacionesGeograficas = administrarNReportesNomina.listUbicacionesGeograficas();
        }
        return listValUbicacionesGeograficas;
    }

    public void setListValUbicacionesGeograficas(List<UbicacionesGeograficas> listUbicacionesGeograficas) {
        System.out.println(this.getClass().getName() + ".setListValUbicacionesGeograficas()");
        this.listValUbicacionesGeograficas = listUbicacionesGeograficas;
    }

    public List<TiposAsociaciones> getListValTiposAsociaciones() {
        System.out.println(this.getClass().getName() + ".getListValTiposAsociaciones()");
        if (listValTiposAsociaciones == null) {
            listValTiposAsociaciones = administrarNReportesNomina.listTiposAsociaciones();
        }
        return listValTiposAsociaciones;
    }

    public void setListValTiposAsociaciones(List<TiposAsociaciones> listTiposAsociaciones) {
        System.out.println(this.getClass().getName() + ".setListValTiposAsociaciones()");
        this.listValTiposAsociaciones = listTiposAsociaciones;
    }

    public List<Estructuras> getListValEstructuras() {
        System.out.println(this.getClass().getName() + ".getListValEstructuras()");
        if (listValEstructuras == null) {
            listValEstructuras = administrarNReportesNomina.listEstructuras();
        }
        return listValEstructuras;
    }

    public void setListValEstructuras(List<Estructuras> listEstructuras) {
        System.out.println(this.getClass().getName() + ".setListValEstructuras()");
        this.listValEstructuras = listEstructuras;
    }

    public List<TiposTrabajadores> getListValTiposTrabajadores() {
        System.out.println(this.getClass().getName() + ".getListValTiposTrabajadores()");
        if (listValTiposTrabajadores == null) {
            listValTiposTrabajadores = administrarNReportesNomina.listTiposTrabajadores();
        }
        return listValTiposTrabajadores;
    }

    public void setListValTiposTrabajadores(List<TiposTrabajadores> listTiposTrabajadores) {
        System.out.println(this.getClass().getName() + ".setListValTiposTrabajadores()");
        this.listValTiposTrabajadores = listTiposTrabajadores;
    }

    public List<Terceros> getListValTerceros() {
        System.out.println(this.getClass().getName() + ".getListValTerceros()");
        if (listValTerceros == null) {
            listValTerceros = administrarNReportesNomina.listTerceros();
        }
        return listValTerceros;
    }

    public void setListValTerceros(List<Terceros> listTerceros) {
        System.out.println(this.getClass().getName() + ".setListValTerceros()");
        this.listValTerceros = listTerceros;
    }

    public List<Procesos> getListValProcesos() {
        System.out.println(this.getClass().getName() + ".getListValProcesos()");
        if (listValProcesos == null) {
            listValProcesos = administrarNReportesNomina.listProcesos();
        }
        return listValProcesos;
    }

    public void setListValProcesos(List<Procesos> listProcesos) {
        System.out.println(this.getClass().getName() + ".setListValProcesos()");
        this.listValProcesos = listProcesos;
    }

    public List<Asociaciones> getListValAsociaciones() {
        System.out.println(this.getClass().getName() + ".getListValAsociaciones()");
        if (listValAsociaciones == null) {
            listValAsociaciones = administrarNReportesNomina.listAsociaciones();
        }
        return listValAsociaciones;
    }

    public void setListValAsociaciones(List<Asociaciones> listAsociaciones) {
        System.out.println(this.getClass().getName() + ".setListValAsociaciones()");
        this.listValAsociaciones = listAsociaciones;
    }

    public Empleados getEmpleadoSeleccionado() {
        System.out.println(this.getClass().getName() + ".getEmpleadoSeleccionado()");
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        System.out.println(this.getClass().getName() + ".setEmpleadoSeleccionado()");
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public Empresas getEmpresaSeleccionada() {
        System.out.println(this.getClass().getName() + ".getEmpresaSeleccionada()");
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        System.out.println(this.getClass().getName() + ".setEmpresaSeleccionada()");
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public GruposConceptos getGrupoCSeleccionado() {
        System.out.println(this.getClass().getName() + ".getGrupoCSeleccionado()");
        return grupoCSeleccionado;
    }

    public void setGrupoCSeleccionado(GruposConceptos grupoCSeleccionado) {
        System.out.println(this.getClass().getName() + ".setGrupoCSeleccionado()");
        this.grupoCSeleccionado = grupoCSeleccionado;
    }

    public Estructuras getEstructuraSeleccionada() {
        System.out.println(this.getClass().getName() + ".getEstructuraSeleccionada()");
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        System.out.println(this.getClass().getName() + ".setEstructuraSeleccionada()");
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public TiposTrabajadores getTipoTSeleccionado() {
        System.out.println(this.getClass().getName() + ".getTipoTSeleccionado()");
        return tipoTSeleccionado;
    }

    public void setTipoTSeleccionado(TiposTrabajadores tipoTSeleccionado) {
        System.out.println(this.getClass().getName() + ".setTipoTSeleccionado()");
        this.tipoTSeleccionado = tipoTSeleccionado;
    }

    public Terceros getTerceroSeleccionado() {
        System.out.println(this.getClass().getName() + ".getTerceroSeleccionado()");
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        System.out.println(this.getClass().getName() + ".setTerceroSeleccionado()");
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public Procesos getProcesoSeleccionado() {
        System.out.println(this.getClass().getName() + ".getProcesoSeleccionado()");
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        System.out.println(this.getClass().getName() + ".setProcesoSeleccionado()");
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public Asociaciones getAsociacionSeleccionado() {
        System.out.println(this.getClass().getName() + ".getAsociacionSeleccionado()");
        return asociacionSeleccionado;
    }

    public void setAsociacionSeleccionado(Asociaciones asociacionSeleccionado) {
        System.out.println(this.getClass().getName() + ".setAsociacionSeleccionado()");
        this.asociacionSeleccionado = asociacionSeleccionado;
    }

    public List<Empleados> getFiltrarListEmpleados() {
        System.out.println(this.getClass().getName() + ".getFiltrarListEmpleados()");
        return filtrarListEmpleados;
    }

    public void setFiltrarListEmpleados(List<Empleados> filtrarListEmpleados) {
        System.out.println(this.getClass().getName() + ".setFiltrarListEmpleados()");
        this.filtrarListEmpleados = filtrarListEmpleados;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        System.out.println(this.getClass().getName() + ".getFiltrarListEmpresas()");
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        System.out.println(this.getClass().getName() + ".setFiltrarListEmpresas()");
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public List<GruposConceptos> getFiltrarListGruposConceptos() {
        System.out.println(this.getClass().getName() + ".getFiltrarListGruposConceptos()");
        return filtrarListGruposConceptos;
    }

    public void setFiltrarListGruposConceptos(List<GruposConceptos> filtrarListGruposConceptos) {
        System.out.println(this.getClass().getName() + ".setFiltrarListGruposConceptos()");
        this.filtrarListGruposConceptos = filtrarListGruposConceptos;
    }

    public List<UbicacionesGeograficas> getFiltrarListUbicacionesGeograficas() {
        System.out.println(this.getClass().getName() + ".getFiltrarListUbicacionesGeograficas()");
        return filtrarListUbicacionesGeograficas;
    }

    public void setFiltrarListUbicacionesGeograficas(List<UbicacionesGeograficas> filtrarListUbicacionesGeograficas) {
        System.out.println(this.getClass().getName() + ".setFiltrarListUbicacionesGeograficas()");
        this.filtrarListUbicacionesGeograficas = filtrarListUbicacionesGeograficas;
    }

    public List<TiposAsociaciones> getFiltrarListTiposAsociaciones() {
        System.out.println(this.getClass().getName() + ".getFiltrarListTiposAsociaciones()");
        return filtrarListTiposAsociaciones;
    }

    public void setFiltrarListTiposAsociaciones(List<TiposAsociaciones> filtrarListTiposAsociaciones) {
        System.out.println(this.getClass().getName() + ".setFiltrarListTiposAsociaciones()");
        this.filtrarListTiposAsociaciones = filtrarListTiposAsociaciones;
    }

    public List<Estructuras> getFiltrarListEstructuras() {
        System.out.println(this.getClass().getName() + ".getFiltrarListEstructuras()");
        return filtrarListEstructuras;
    }

    public void setFiltrarListEstructuras(List<Estructuras> filtrarListEstructuras) {
        System.out.println(this.getClass().getName() + ".setFiltrarListEstructuras()");
        this.filtrarListEstructuras = filtrarListEstructuras;
    }

    public List<Terceros> getFiltrarListTerceros() {
        System.out.println(this.getClass().getName() + ".getFiltrarListTerceros()");
        return filtrarListTerceros;
    }

    public void setFiltrarListTerceros(List<Terceros> filtrarListTerceros) {
        System.out.println(this.getClass().getName() + ".setFiltrarListTerceros()");
        this.filtrarListTerceros = filtrarListTerceros;
    }

    public List<Procesos> getFiltrarListProcesos() {
        System.out.println(this.getClass().getName() + ".getFiltrarListProcesos()");
        return filtrarListProcesos;
    }

    public void setFiltrarListProcesos(List<Procesos> filtrarListProcesos) {
        System.out.println(this.getClass().getName() + ".setFiltrarListProcesos()");
        this.filtrarListProcesos = filtrarListProcesos;
    }

    public List<Asociaciones> getFiltrarListAsociaciones() {
        System.out.println(this.getClass().getName() + ".getFiltrarListAsociaciones()");
        return filtrarListAsociaciones;
    }

    public void setFiltrarListAsociaciones(List<Asociaciones> filtrarListAsociaciones) {
        System.out.println(this.getClass().getName() + ".setFiltrarListAsociaciones()");
        this.filtrarListAsociaciones = filtrarListAsociaciones;
    }

    public UbicacionesGeograficas getUbicacionesGSeleccionado() {
        System.out.println(this.getClass().getName() + ".getUbicacionesGSeleccionado()");
        return ubicacionesGSeleccionado;
    }

    public void setUbicacionesGSeleccionado(UbicacionesGeograficas ubicacionesGSeleccionado) {
        System.out.println(this.getClass().getName() + ".setUbicacionesGSeleccionado()");
        this.ubicacionesGSeleccionado = ubicacionesGSeleccionado;
    }

    public TiposAsociaciones getTiposASeleccionado() {
        System.out.println(this.getClass().getName() + ".getTiposASeleccionado()");
        return tiposASeleccionado;
    }

    public void setTiposASeleccionado(TiposAsociaciones tiposASeleccionado) {
        System.out.println(this.getClass().getName() + ".setTiposASeleccionado()");
        this.tiposASeleccionado = tiposASeleccionado;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        System.out.println(this.getClass().getName() + ".getFiltrarListTiposTrabajadores()");
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        System.out.println(this.getClass().getName() + ".setFiltrarListTiposTrabajadores()");
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }

    public String getAltoTabla() {
        System.out.println(this.getClass().getName() + ".getAltoTabla()");
        return altoTabla;
    }

    public List<Inforeportes> getListaInfoReportesModificados() {
        System.out.println(this.getClass().getName() + ".getListaInfoReportesModificados()");
        return listaInfoReportesModificados;
    }

    public void setListaInfoReportesModificados(List<Inforeportes> listaInfoReportesModificados) {
        System.out.println(this.getClass().getName() + ".setListaInfoReportesModificados()");
        this.listaInfoReportesModificados = listaInfoReportesModificados;
    }

    public boolean isCambiosReporte() {
        System.out.println(this.getClass().getName() + ".isCambiosReporte()");
        return cambiosReporte;
    }

    public void setCambiosReporte(boolean cambiosReporte) {
        System.out.println(this.getClass().getName() + ".setCambiosReporte()");
        this.cambiosReporte = cambiosReporte;
    }

    public String getColor() {
        System.out.println(this.getClass().getName() + ".getColor()");
        return color;
    }

    public void setColor(String color) {
        System.out.println(this.getClass().getName() + ".setColor()");
        this.color = color;
    }

    public String getDecoracion() {
        System.out.println(this.getClass().getName() + ".getDecoracion()");
        return decoracion;
    }

    public void setDecoracion(String decoracion) {
        System.out.println(this.getClass().getName() + ".setDecoracion()");
        this.decoracion = decoracion;
    }

    public String getColor2() {
        System.out.println(this.getClass().getName() + ".getColor2()");
        return color2;
    }

    public void setColor2(String color) {
        System.out.println(this.getClass().getName() + ".setColor2()");
        this.color2 = color;
    }

    public String getDecoracion2() {
        System.out.println(this.getClass().getName() + ".getDecoracion2()");
        return decoracion2;
    }

    public void setDecoracion2(String decoracion) {
        System.out.println(this.getClass().getName() + ".setDecoracion2()");
        this.decoracion2 = decoracion;
    }

    public boolean isActivoMostrarTodos() {
        System.out.println(this.getClass().getName() + ".isActivoMostrarTodos()");
        return activoMostrarTodos;
    }

    public void setActivoMostrarTodos(boolean activoMostrarTodos) {
        System.out.println(this.getClass().getName() + ".setActivoMostrarTodos()");
        this.activoMostrarTodos = activoMostrarTodos;
    }

    public boolean isActivoBuscarReporte() {
        System.out.println(this.getClass().getName() + ".isActivoBuscarReporte()");
        return activoBuscarReporte;
    }

    public void setActivoBuscarReporte(boolean activoBuscarReporte) {
        System.out.println(this.getClass().getName() + ".setActivoBuscarReporte()");
        this.activoBuscarReporte = activoBuscarReporte;
    }

    public StreamedContent getReporte() {
        System.out.println(this.getClass().getName() + ".getReporte()");
        return reporte;
    }

    public String getCabezeraVisor() {
        System.out.println(this.getClass().getName() + ".getCabezeraVisor()");
        return cabezeraVisor;
    }

    public String getPathReporteGenerado() {
        System.out.println(this.getClass().getName() + ".getPathReporteGenerado()");
        return pathReporteGenerado;
    }

    /*
     * public static FacesContext getFacesContext(HttpServletRequest request,
     * HttpServletResponse response) { // Get current FacesContext. FacesContext
     * facesContext;
     *
     * System.out.println("Entro a crear un FacesContext"); // Create new
     * Lifecycle. LifecycleFactory lifecycleFactory = (LifecycleFactory)
     * FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY); Lifecycle
     * lifecycle =
     * lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
     *
     * // Create new FacesContext. FacesContextFactory contextFactory =
     * (FacesContextFactory)
     * FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
     * facesContext = contextFactory.getFacesContext(
     * request.getSession().getServletContext(), request, response, lifecycle);
     *
     * // Create new View. UIViewRoot view =
     * facesContext.getApplication().getViewHandler().createView( facesContext,
     * ""); facesContext.setViewRoot(view);
     *
     * // Set current FacesContext.
     * FacesContextWrapper.setCurrentInstance(facesContext);
     *
     * return facesContext; }
     */
    private static abstract class FacesContextWrapper extends FacesContext {

        protected static void setCurrentInstance(FacesContext facesContext) {
            System.out.println("FacesContextWrapper" + ".setCurrentInstance()");
            FacesContext.setCurrentInstance(facesContext);
        }
    }

    public String getInfoRegistroEmpleadoDesde() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroEmpleadoDesde()");
        getListValEmpleados();
        if (listValEmpleados != null) {
            infoRegistroEmpleadoDesde = "Cantidad de registros : " + listValEmpleados.size();
        } else {
            infoRegistroEmpleadoDesde = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleadoDesde;
    }

    public void setInfoRegistroEmpleadoDesde(String infoRegistroEmpleadoDesde) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroEmpleadoDesde()");
        this.infoRegistroEmpleadoDesde = infoRegistroEmpleadoDesde;
    }

    public String getInfoRegistroEmpleadoHasta() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroEmpleadoHasta()");
        getListValEmpleados();
        if (listValEmpleados != null) {
            infoRegistroEmpleadoHasta = "Cantidad de registros : " + listValEmpleados.size();
        } else {
            infoRegistroEmpleadoHasta = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleadoHasta;
    }

    public void setInfoRegistroEmpleadoHasta(String infoRegistroEmpleadoHasta) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroEmpleadoHasta()");
        this.infoRegistroEmpleadoHasta = infoRegistroEmpleadoHasta;
    }

    public String getInfoRegistroGrupoConcepto() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroGrupoConcepto()");
        getListValGruposConceptos();
        if (listValGruposConceptos != null) {
            infoRegistroGrupoConcepto = "Cantidad de registros : " + listValGruposConceptos.size();
        } else {
            infoRegistroGrupoConcepto = "Cantidad de registro : 0";
        }
        return infoRegistroGrupoConcepto;
    }

    public void setInfoRegistroGrupoConcepto(String infoRegistroGrupoConcepto) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroGrupoConcepto()");
        this.infoRegistroGrupoConcepto = infoRegistroGrupoConcepto;
    }

    public String getInfoRegistroUbicacion() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroUbicacion()");
        getListValUbicacionesGeograficas();
        if (listValUbicacionesGeograficas != null) {
            infoRegistroUbicacion = "Cantidad de registros : " + listValUbicacionesGeograficas.size();
        } else {
            infoRegistroUbicacion = "Cantidad de registros : 0";
        }
        return infoRegistroUbicacion;
    }

    public void setInfoRegistroUbicacion(String infoRegistroUbicacion) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroUbicacion()");
        this.infoRegistroUbicacion = infoRegistroUbicacion;
    }

    public String getInfoRegistroTipoAsociacion() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroTipoAsociacion()");
        getListValTiposAsociaciones();
        if (listValTiposAsociaciones != null) {
            infoRegistroTipoAsociacion = "Cantidad de registros : " + listValTiposAsociaciones.size();
        } else {
            infoRegistroTipoAsociacion = "Cantidad de registros : 0";
        }
        return infoRegistroTipoAsociacion;
    }

    public void setInfoRegistroTipoAsociacion(String infoRegistroTipoAsociacion) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroTipoAsociacion()");
        this.infoRegistroTipoAsociacion = infoRegistroTipoAsociacion;
    }

    public String getInfoRegistroEmpresa() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroEmpresa()");
        getListValEmpresas();
        if (listValEmpresas != null) {
            infoRegistroEmpresa = "Cantidad de registros : " + listValEmpresas.size();
        } else {
            infoRegistroEmpresa = "Cantidad de registros : 0";
        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroEmpresa()");
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public String getInfoRegistroAsociacion() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroAsociacion()");
        getListValAsociaciones();
        if (listValAsociaciones != null) {
            infoRegistroAsociacion = "Cantidad de registros : " + listValAsociaciones.size();
        } else {
            infoRegistroAsociacion = "Cantidad de registros : 0";
        }
        return infoRegistroAsociacion;
    }

    public void setInfoRegistroAsociacion(String infoRegistroAsociacion) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroAsociacion()");
        this.infoRegistroAsociacion = infoRegistroAsociacion;
    }

    public String getInfoRegistroTercero() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroTercero()");
        getListValTerceros();
        if (listValTerceros != null) {
            infoRegistroTercero = "Cantidad de registros : " + listValTerceros.size();
        } else {
            infoRegistroTercero = "Cantidad de registros : 0";
        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroTercero()");
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public String getInfoRegistroProceso() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroProceso()");
        getListValProcesos();
        if (listValProcesos != null) {
            infoRegistroProceso = "Cantidad de registros : " + listValProcesos.size();
        } else {
            infoRegistroProceso = "Cantidad de registros : 0";
        }
        return infoRegistroProceso;
    }

    public void setInfoRegistroProceso(String infoRegistroProceso) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroProceso()");
        this.infoRegistroProceso = infoRegistroProceso;
    }

    public String getInfoRegistroTipoTrabajador() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroTipoTrabajador()");
        getListValTiposTrabajadores();
        if (listValTiposTrabajadores != null) {
            infoRegistroTipoTrabajador = "Cantidad de registros : " + listValTiposTrabajadores.size();
        } else {
            infoRegistroTipoTrabajador = "Cantidad de registros : 0";
        }
        return infoRegistroTipoTrabajador;
    }

    public void setInfoRegistroTipoTrabajador(String infoRegistroTipoTrabajador) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroTipoTrabajador()");
        this.infoRegistroTipoTrabajador = infoRegistroTipoTrabajador;
    }

    public String getInfoRegistroEstructura() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroEstructura()");
        getListValEstructuras();
        if (listValEstructuras != null) {
            infoRegistroEstructura = "Cantidad de registros : " + listValEstructuras.size();

        } else {
            infoRegistroEstructura = "Cantidad de registros : 0";
        }
        return infoRegistroEstructura;
    }

    public void setInfoRegistroEstructura(String infoRegistroEstructura) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroEstructura()");
        this.infoRegistroEstructura = infoRegistroEstructura;
    }

    public String getInfoRegistroReportes() {
        System.out.println(this.getClass().getName() + ".getInfoRegistroReportes()");
        return infoRegistroReportes;
    }

    public void setInfoRegistroReportes(String infoRegistroReportes) {
        System.out.println(this.getClass().getName() + ".setInfoRegistroReportes()");
        this.infoRegistroReportes = infoRegistroReportes;
    }

    public List<Inforeportes> getListValInforeportes() {
        System.out.println(this.getClass().getName() + ".getListValInforeportes()");
        if (listValInforeportes == null) {
            listValInforeportes = administrarNReportesNomina.listInforeportesUsuario();
        }
        return listValInforeportes;
    }

    public void setListValInforeportes(List<Inforeportes> lovInforeportes) {
        System.out.println(this.getClass().getName() + ".setListValInforeportes()");
        this.listValInforeportes = lovInforeportes;
    }

    public List<Inforeportes> getFiltrarLovInforeportes() {
        System.out.println(this.getClass().getName() + ".getFiltrarLovInforeportes()");
        return filtrarLovInforeportes;
    }

    public void setFiltrarLovInforeportes(List<Inforeportes> filtrarLovInforeportes) {
        System.out.println(this.getClass().getName() + ".setFiltrarLovInforeportes()");
        this.filtrarLovInforeportes = filtrarLovInforeportes;
    }

    public Inforeportes getReporteSeleccionado() {
        System.out.println(this.getClass().getName() + ".getReporteSeleccionado()");
        return reporteSeleccionado;
    }

    public void setReporteSeleccionado(Inforeportes inforreporteSeleccionado) {
        System.out.println(this.getClass().getName() + ".setReporteSeleccionado()");
        this.reporteSeleccionado = inforreporteSeleccionado;
    }
}

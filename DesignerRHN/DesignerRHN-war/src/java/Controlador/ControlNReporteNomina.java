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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FactoryFinder;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.AsynchronousFilllListener;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.DefaultRequestContext;
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
    private String grupo, ubiGeo, tipoAso, estructura, empresa, tipoTrabajador, tercero, proceso, asociacion, estado;
    private boolean permitirIndex, cambiosReporte;
    //ALTO SCROLL TABLA
    private String altoTabla;
    private int indice;
    //EXPORTAR REPORTE
    private String pathReporteGenerado = null;
    private String nombreReporte, tipoReporte;
    //
    private List<Inforeportes> listaInfoReportesModificados;
    //
    private Inforeportes actualInfoReporteTabla;
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
    FileInputStream prueba;

    public ControlNReporteNomina() throws FileNotFoundException {
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        color = "black";
        decoracion = "none";
        color2 = "black";
        decoracion2 = "none";
        casillaInforReporte = -1;
        actualInfoReporteTabla = new Inforeportes();
        cambiosReporte = true;
        listaInfoReportesModificados = new ArrayList<Inforeportes>();
        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        tipoLista = 0;
        reporteGenerar = "";
        requisitosReporte = "";
        posicionReporte = -1;
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
        altoTabla = "185";
        indice = -1;
        prueba = new FileInputStream(new File("C:\\Users\\Administrador\\Documents\\Guia JasperReport.pdf"));
        reporte = new DefaultStreamedContent(prueba, "application/pdf");
        //reporte = new DefaultStreamedContent();
        cabezeraVisor = null;
        estadoReporte = false;
    }

    public void iniciarPagina() {
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        listaIR = null;
        getListaIR();
        if (listaIR.size() > 0) {
            actualInfoReporteTabla = listaIR.get(0);
        }
    }

    @PostConstruct
    public void iniciarAdministradores() {
        try {
            FacesContext contexto = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) contexto.getExternalContext().getSession(false);
            administarReportes.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct controlNReporteNomina" + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void requisitosParaReporte() {
        int indiceSeleccion = 0;
        if (tipoLista == 0) {
            indiceSeleccion = listaIR.indexOf(actualInfoReporteTabla);
        }
        if (tipoLista == 1) {
            indiceSeleccion = filtrarListInforeportesUsuario.indexOf(actualInfoReporteTabla);
        }
        parametrosDeReporte(indiceSeleccion);
    }

    public void seleccionRegistro() {
        int indiceSeleccion = 0;
        if (tipoLista == 0) {
            indiceSeleccion = listaIR.indexOf(actualInfoReporteTabla);
        }
        if (tipoLista == 1) {
            indiceSeleccion = filtrarListInforeportesUsuario.indexOf(actualInfoReporteTabla);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:reportesLaboral");
        resaltoParametrosParaReporte(indiceSeleccion);
    }

    public void cambiarIndexInforeporte(int i, int c) {
        casillaInforReporte = c;
        casilla = -1;
        if (tipoLista == 0) {
            actualInfoReporteTabla = listaIR.get(i);
        }
        if (tipoLista == 1) {
            actualInfoReporteTabla = filtrarListInforeportesUsuario.get(i);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:reportesNomina");
        resaltoParametrosParaReporte(i);
    }

    public void dispararDialogoGuardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("confirmarGuardar.show()");

    }

    public void guardarYSalir() {
        guardarCambios();
        salir();
    }

    public void guardarCambios() {
        try {
            if (cambiosReporte == false) {
                if (parametroModificacion != null) {
                    if (parametroModificacion.getGrupo().getSecuencia() == null) {
                        parametroModificacion.setGrupo(null);
                    }
                    if (parametroModificacion.getUbicaciongeografica().getSecuencia() == null) {
                        parametroModificacion.setUbicaciongeografica(null);
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
                    if (parametroModificacion.getTipoasociacion().getSecuencia() == null) {
                        parametroModificacion.setTipoasociacion(null);
                    }
                    administrarNReportesNomina.modificarParametrosInformes(parametroModificacion);
                }
                if (!listaInfoReportesModificados.isEmpty()) {
                    System.out.println("Entro a guardarCambiosInfoReportes");
                    System.out.println("listaInfoReportesModificados : " + listaInfoReportesModificados.size());
                    for (int i = 0; i < listaInfoReportesModificados.size(); i++) {
                        System.out.println("Modificara InfoReporte : " + listaInfoReportesModificados.get(i).getSecuencia());
                    }
                    administrarNReportesNomina.guardarCambiosInfoReportes(listaInfoReportesModificados);
                }
                cambiosReporte = true;
                RequestContext context = RequestContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("form:growl");
                context.update("form:ACEPTAR");
            }
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void modificarParametroInforme() {
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
            System.out.println("actualinforeporte : " + actualInfoReporteTabla.getCodigo());
            System.out.println("actualinforeporte : " + actualInfoReporteTabla.getNombre());
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
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("GRUPO")) {
            parametroDeInforme.getGrupo().setDescripcion(grupo);
            for (int i = 0; i < listGruposConceptos.size(); i++) {
                if (listGruposConceptos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setGrupo(listGruposConceptos.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listGruposConceptos.clear();
                getListGruposConceptos();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:GruposConceptosDialogo");
                context.execute("GruposConceptosDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("UBIGEO")) {
            parametroDeInforme.getUbicaciongeografica().setDescripcion(ubiGeo);

            for (int i = 0; i < listUbicacionesGeograficas.size(); i++) {
                if (listUbicacionesGeograficas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setUbicaciongeografica(listUbicacionesGeograficas.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listUbicacionesGeograficas.clear();
                getListUbicacionesGeograficas();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:UbiGeograficaDialogo");
                context.execute("UbiGeograficaDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("EMPRESA")) {
            parametroDeInforme.getEmpresa().setNombre(empresa);

            for (int i = 0; i < listEmpresas.size(); i++) {
                if (listEmpresas.get(i).getNombre().startsWith(valorConfirmar)) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setEmpresa(listEmpresas.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEmpresas.clear();
                getListEmpresas();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOASO")) {
            parametroDeInforme.getTipoasociacion().setDescripcion(tipoAso);
            for (int i = 0; i < listTiposAsociaciones.size(); i++) {
                if (listTiposAsociaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setTipoasociacion(listTiposAsociaciones.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listTiposAsociaciones.clear();
                getListTiposAsociaciones();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:TipoAsociacionDialogo");
                context.execute("TipoAsociacionDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("ESTRUCTURA")) {
            parametroDeInforme.getLocalizacion().setNombre(estructura);
            for (int i = 0; i < listEstructuras.size(); i++) {
                if (listEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setLocalizacion(listEstructuras.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEstructuras.clear();
                getListEstructuras();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:EstructuraDialogo");
                context.execute("EstructuraDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOTRABAJADOR")) {
            parametroDeInforme.getTipotrabajador().setNombre(tipoTrabajador);
            for (int i = 0; i < listTiposTrabajadores.size(); i++) {
                if (listTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listTiposTrabajadores.clear();
                getListTiposTrabajadores();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TERCERO")) {
            parametroDeInforme.getTercero().setNombre(tercero);
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setTercero(listTerceros.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listTerceros.clear();
                getListTerceros();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
            }
        }
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
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:ProcesoDialogo");
                context.execute("ProcesoDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("ASOCIACION")) {
            parametroDeInforme.getAsociacion().setDescripcion(asociacion);
            for (int i = 0; i < listAsociaciones.size(); i++) {
                if (listAsociaciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setAsociacion(listAsociaciones.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listAsociaciones.clear();
                getListAsociaciones();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:AsociacionDialogo");
                context.execute("AsociacionDialogo.show()");
            }
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 2) {
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (pos == 2) {
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
        aceptar = false;
    }

    public void actualizarEmplDesde() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpleadoDesdeDialogo");
        context.update("form:lovEmpleadoDesde");
        context.update("form:aceptarED");
        context.execute("EmpleadoDesdeDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoDesdeParametro");
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
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpleadoHastaDialogo");
        context.update("form:lovEmpleadoHasta");
        context.update("form:aceptarEH");
        context.execute("EmpleadoHastaDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoHastaParametro");
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
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:GruposConceptosDialogo");
        context.update("form:lovGruposConceptos");
        context.update("form:aceptarGC");
        context.execute("GruposConceptosDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:grupoParametro");
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
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:UbiGeograficaDialogo");
        context.update("form:lovUbicacionGeografica");
        context.update("form:aceptarUG");
        context.execute("UbiGeograficaDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:ubicacionGeograficaParametro");
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
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TipoAsociacionDialogo");
        context.update("form:lovTipoAsociacion");
        context.update("form:aceptarTA");
        context.execute("TipoAsociacionDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:tipoAsociacionParametro");
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
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpresaDialogo");
        context.update("form:lovEmpresa");
        context.update("form:aceptarE");
        context.execute("EmpresaDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empresaParametro");
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

    public void actualizarEstructura() {
        parametroDeInforme.setLocalizacion(estructuraSeleccionada);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EstructuraDialogo");
        context.update("form:lovEstructura");
        context.update("form:aceptarEST");
        context.execute("EstructuraDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:estructuraParametro");
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;

    }

    public void cancelarEstructura() {
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;
    }

    public void actualizarTipoTrabajador() {
        parametroDeInforme.setTipotrabajador(tipoTSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TipoTrabajadorDialogo");
        context.update("form:lovTipoTrabajador");
        context.update("form:aceptarTT");
        context.execute("TipoTrabajadorDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:tipoTrabajadorParametro");
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;

    }

    public void cancelarTipoTrabajador() {
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;
    }

    public void actualizarTercero() {
        permitirIndex = true;
        parametroDeInforme.setTercero(terceroSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TerceroDialogo");
        context.update("form:lovTercero");
        context.update("form:aceptarT");
        context.execute("TerceroDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:terceroParametro");
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

    public void actualizarProceso() {
        permitirIndex = true;
        parametroDeInforme.setProceso(procesoSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ProcesoDialogo");
        context.update("form:lovProceso");
        context.update("form:aceptarP");
        context.execute("ProcesoDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:procesoParametro");
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

    public void actualizarAsociacion() {
        permitirIndex = true;
        parametroDeInforme.setAsociacion(asociacionSeleccionado);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:AsociacionDialogo");
        context.update("form:lovAsociacion");
        context.update("form:aceptarA");
        context.execute("AsociacionDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:asociacionParametro");
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
            if (cambiosReporte == true) {
                listaIR = administrarNReportesNomina.listInforeportesUsuario();
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
        if (bandera == 1) {
            altoTabla = "185";
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
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void actualizarSeleccionInforeporte() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "185";
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
        actualInfoReporteTabla = listaIR.get(0);
        activoBuscarReporte = true;
        activoMostrarTodos = false;
        context.update("form:MOSTRARTODOS");
        context.update("form:BUSCARREPORTE");
        context.update("form:ReportesDialogo");
        context.update("form:lovReportesDialogo");
        context.update("form:aceptarR");
        context.execute("ReportesDialogo.hide()");
        context.update(":form:reportesNomina");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
    }

    public void mostrarTodos() {
        if (cambiosReporte == true) {
            defaultPropiedadesParametrosReporte();
            listaIR = null;
            getListaIR();
            if (listaIR.size() > 0) {
                actualInfoReporteTabla = listaIR.get(0);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            activoBuscarReporte = false;
            activoMostrarTodos = true;
            context.update("form:MOSTRARTODOS");
            context.update("form:BUSCARREPORTE");
            context.update(":form:reportesNomina");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void cancelarYSalir() {
        cancelarModificaciones();
        salir();
    }

    public void cancelarModificaciones() {
        if (bandera == 1) {
            altoTabla = "185";
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
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        getParametroDeInforme();
        getListaIR();
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        if (listaIR.size() > 0) {
            actualInfoReporteTabla = listaIR.get(0);
        }
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
        if (bandera == 0) {
            altoTabla = "163";
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
            altoTabla = "185";
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

    public void modificacionTipoReporte(int i) {
        cambiosReporte = false;
        System.out.println("Modificacion Reporte A Generar");
        if (tipoLista == 0) {
            if (listaInfoReportesModificados.isEmpty()) {
                System.out.println("Op..1");
                listaInfoReportesModificados.add(listaIR.get(i));
            } else {
                if ((!listaInfoReportesModificados.isEmpty()) && (!listaInfoReportesModificados.contains(listaIR.get(i)))) {
                    listaInfoReportesModificados.add(listaIR.get(i));
                    System.out.println("Op..2");
                } else {
                    int posicion = listaInfoReportesModificados.indexOf(listaIR.get(i));
                    listaInfoReportesModificados.set(posicion, listaIR.get(i));
                    System.out.println("Op..3");
                }
            }
        }
        if (tipoLista == 1) {
            if (listaInfoReportesModificados.isEmpty()) {
                listaInfoReportesModificados.add(filtrarListInforeportesUsuario.get(i));
            } else {
                if ((!listaInfoReportesModificados.isEmpty()) && (!listaInfoReportesModificados.contains(filtrarListInforeportesUsuario.get(i)))) {
                    listaInfoReportesModificados.add(filtrarListInforeportesUsuario.get(i));
                } else {
                    int posicion = listaInfoReportesModificados.indexOf(filtrarListInforeportesUsuario.get(i));
                    listaInfoReportesModificados.set(posicion, filtrarListInforeportesUsuario.get(i));
                }
            }
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void reporteSeleccionado(int i) {
        System.out.println("Posicion del reporte : " + i);
    }

    public void defaultPropiedadesParametrosReporte() {

        color = "black";
        decoracion = "none";
        color2 = "black";
        decoracion2 = "none";
        RequestContext.getCurrentInstance().update("formParametros");

        empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoDesdeParametro");
        empleadoDesdeParametro.setStyle("position: absolute; top: 35px; left: 130px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametro");

        empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoHastaParametro");
        empleadoHastaParametro.setStyle("position: absolute; top: 35px; left: 350px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametro");

        grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:grupoParametro");
        grupoParametro.setStyle("position: absolute; top: 85px; left: 130px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("formParametros:grupoParametro");

        estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estructuraParametro");
        estructuraParametro.setStyle("position: absolute; top: 10px; left: 580px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("formParametros:estructuraParametro");

        tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:tipoTrabajadorParametro");
        tipoTrabajadorParametro.setStyle("position: absolute; top: 35px; left: 580px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("formParametros:tipoTrabajadorParametro");

        terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:terceroParametro");
        terceroParametro.setStyle("position: absolute; top: 58px; left: 580px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("formParametros:terceroParametro");

        estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estadoParametro");
        estadoParametro.setStyleClass("position: absolute; top: 65px; left: 30px;");
        RequestContext.getCurrentInstance().update("formParametros:estadoParametro");
    }

    public void resaltoParametrosParaReporte(int i) {
        indice = i;
        Inforeportes reporteS = new Inforeportes();
        if (tipoLista == 0) {
            reporteS = listaIR.get(i);
        }
        if (tipoLista == 1) {
            reporteS = filtrarListInforeportesUsuario.get(i);
        }
        defaultPropiedadesParametrosReporte();
        if (reporteS.getFecdesde().equals("SI")) {
            color = "red";
            decoracion = "underline";
            RequestContext.getCurrentInstance().update("formParametros");
        }
        if (reporteS.getFechasta().equals("SI")) {
            color2 = "red";
            decoracion2 = "underline";
            RequestContext.getCurrentInstance().update("formParametros");
        }
        if (reporteS.getEmdesde().equals("SI")) {
            empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoDesdeParametro");
            empleadoDesdeParametro.setStyle("position: absolute; top: 35px; left: 130px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametro");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:empleadoHastaParametro");
            empleadoHastaParametro.setStyle("position: absolute; top: 35px; left: 350px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametro");
        }
        if (reporteS.getGrupo().equals("SI")) {
            grupoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:grupoParametro");
            grupoParametro.setStyle("position: absolute; top: 85px; left: 130px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:grupoParametro");
        }
        if (reporteS.getLocalizacion().equals("SI")) {
            estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estructuraParametro");
            estructuraParametro.setStyle("position: absolute; top: 10px; left: 580px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:estructuraParametro");
        }
        if (reporteS.getTipotrabajador().equals("SI")) {
            tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:tipoTrabajadorParametro");
            tipoTrabajadorParametro.setStyle("position: absolute; top: 35px; left: 580px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:tipoTrabajadorParametro");
        }
        if (reporteS.getTercero().equals("SI")) {
            terceroParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:terceroParametro");
            terceroParametro.setStyle("position: absolute; top: 58px; left: 580px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:terceroParametro");
        }
        if (reporteS.getEstado().equals("SI")) {
            estadoParametro = (SelectOneMenu) FacesContext.getCurrentInstance().getViewRoot().findComponent("formParametros:estadoParametro");
            estadoParametro.setStyleClass("selectOneMenuNReporteN");
            RequestContext.getCurrentInstance().update("formParametros:estadoParametro");
        }
    }

    public void parametrosDeReporte(int i) {
        requisitosReporte = "";
        Inforeportes reporteS = new Inforeportes();
        if (tipoLista == 0) {
            reporteS = listaIR.get(i);
        }
        if (tipoLista == 1) {
            reporteS = filtrarListInforeportesUsuario.get(i);
        }
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

    public void generarReporte() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("cambiosReporte = " + cambiosReporte);
        if (cambiosReporte == true) {
            System.out.println("0");
            if (tipoLista == 0) {
                nombreReporte = listaIR.get(indice).getNombrereporte();
                tipoReporte = listaIR.get(indice).getTipo();
            } else {
                nombreReporte = filtrarListInforeportesUsuario.get(indice).getNombrereporte();
                tipoReporte = filtrarListInforeportesUsuario.get(indice).getTipo();
            }
            if (nombreReporte != null && tipoReporte != null) {
                System.out.println("1");
                pathReporteGenerado = administarReportes.generarReporte(nombreReporte, tipoReporte);
            }
            if (pathReporteGenerado != null) {
                //context.execute("exportarReporte();");
                System.out.println("2");
                context.execute("validarDescargaReporte();");
            }
        } else {
            System.out.println("Syso antes ");

            context.update("form:confirmarGuardarSinSalida");
            context.execute("confirmarGuardarSinSalida.show()");
        }
        //context.execute("dlg.hide()");
    }
    /*public void generarReporte() {
     /*HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
     HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
     FacesContext con = getFacesContext(request, response);*/
        //System.out.println("Faces: " + con);
    //FacesContext f = FacesContext.getCurrentInstance();
    ///System.out.println("Estado respuesta: " + f.getResponseComplete());
      /*  RequestContext context = RequestContext.getCurrentInstance();
     //System.out.println("Context: " + f);
     if (tipoLista == 0) {
     nombreReporte = listaIR.get(indice).getNombrereporte();
     tipoReporte = listaIR.get(indice).getTipo();
     } else {
     nombreReporte = filtrarListInforeportesUsuario.get(indice).getNombrereporte();
     tipoReporte = filtrarListInforeportesUsuario.get(indice).getTipo();
     }
     if (asistenteReporte == null) {
     asistenteReporte = listener();
     System.out.println("Creo el listener. :D");
     }
     /*if (nombreReporte != null && tipoReporte != null) {
     pathReporteGenerado = administarReportes.generarReporte(nombreReporte, tipoReporte, asistenteReporte);
     }*/
    /* if (nombreReporte != null) {
     administarReportes.iniciarLlenadoReporte(nombreReporte, asistenteReporte);
     }
     /* if (pathReporteGenerado != null) {
     //context.execute("exportarReporte();");
     System.out.println("Pasooo");
     /* try {
     //exportarReporte();
     } catch (IOException ex) {
     Logger.getLogger(ControlNReporteNomina.class.getName()).log(Level.SEVERE, null, ex);
     }*/
    //}
    //context.execute("dlg.hide()");
    // context.execute("esperarReporte();");
    // }

    public AsynchronousFilllListener listener() {
        return new AsynchronousFilllListener() {
            //RequestContext context = c;

            @Override
            public void reportFinished(JasperPrint jp) {
                System.out.println("Termino el llenado");
                // System.out.println("Context listener: " + context);
                try {
                    estadoReporte = true;
                    resultadoReporte = "Exito";
                    /* final RequestContext currentInstance = RequestContext.getCurrentInstance();
                     Renderer.instance().render(template);
                     RequestContext.setCurrentInstance(currentInstance)*/
// System.out.println("Estado respuesta listener: " + c.getResponseComplete());
                    // context.execute("formDialogos:generandoReporte");
                    //generarArchivoReporte(jp);
                } catch (Exception e) {
                    System.out.println("Errrrrroooooorrrr: " + e.toString());
                }
                //System.out.println("FINALIZO");
            }

            @Override
            public void reportCancelled() {
                System.out.println("Fue cancelado");
                estadoReporte = true;
                resultadoReporte = "Cancelación";
            }

            @Override
            public void reportFillError(Throwable e) {
                System.out.println("Error llenando el reporte");
                if (e.getCause() != null) {
                    pathReporteGenerado = "Error: " + e.toString() + "\n" + e.getCause().toString();
                } else {
                    pathReporteGenerado = "Error: " + e.toString();
                }
                estadoReporte = true;
                resultadoReporte = "Se estallo";
                /*RequestContext context = RequestContext.getCurrentInstance();
                 context.update("formDialogos:errorGenerandoReporte");
                 context.execute("errorGenerandoReporte.show();");*/
            }
        };
    }

    public void generarArchivoReporte(JasperPrint print) {
        System.out.println("Hola pase... um0");
        if (print != null && tipoReporte != null) {
            System.out.println("Hola pase... um1");
            pathReporteGenerado = administarReportes.crearArchivoReporte(print, tipoReporte);
            System.out.println("Hola pase... um2");
            validarDescargaReporte();
            System.out.println("Hola pase... um3");
        }
    }

    public void exportarReporte() throws IOException {
        System.out.println("PUM");
        if (pathReporteGenerado != null) {
            System.out.println("3");
            System.out.println("Path (exportarReporte): " + pathReporteGenerado);
            File reporte = new File(pathReporteGenerado);
            FacesContext ctx = FacesContext.getCurrentInstance();
            FileInputStream fis = new FileInputStream(reporte);
            byte[] bytes = new byte[1024];
            int read;
            if (!ctx.getResponseComplete()) {
                System.out.println("4");
                String fileName = reporte.getName();
                HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
                response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
                ServletOutputStream out = response.getOutputStream();

                while ((read = fis.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                out.flush();
                out.close();
                System.out.println("\nDescargado\n");
                ctx.responseComplete();
            }
        }
    }

    public void validarDescargaReporte() {
        System.out.println("5");
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("6");
        context.execute("generandoReporte.hide();");
        System.out.println("7");
        if (pathReporteGenerado != null && !pathReporteGenerado.startsWith("Error:")) {
            if (!tipoReporte.equals("PDF")) {
                System.out.println("8");
                context.execute("descargarReporte.show();");
            } else {
                FileInputStream fis;
                try {
                    System.out.println("Ruta: " + pathReporteGenerado);
                    fis = new FileInputStream(new File(pathReporteGenerado));
                    reporte = new DefaultStreamedContent(fis, "application/pdf");
                } catch (FileNotFoundException ex) {
                    System.out.println("Error leyendo archivo");
                    System.out.println(ex.getCause());
                    reporte = null;
                }
                if (reporte != null) {
                    cabezeraVisor = "Reporte - " + listaIR.get(indice).getNombre();
                    context.update("formDialogos:verReportePDF");
                    System.out.println("9");
                    context.execute("verReportePDF.show();");
                }
                pathReporteGenerado = null;
            }
        } else {
            context.update("formDialogos:errorGenerandoReporte");
            context.execute("errorGenerandoReporte.show();");
        }
    }

    /* public void esperarLLenado() {
     while (!estadoReporte) {
     System.out.println("Esperando que termine.....");
     }
     estadoReporte = false;
     System.out.println("Resultado: " + resultadoReporte);
     System.out.println("Fin de la historia");
     }*/
    public void prueba() {
        System.out.println("TERMINOOOOOOOOOO HPTA VIDA");
    }

    public void cancelarReporte() {
        administarReportes.cancelarReporte();
    }

    //GETTER AND SETTER
    public void setParametroDeInforme(ParametrosInformes parametroDeInforme) {
        this.parametroDeInforme = parametroDeInforme;
    }

    public List<Inforeportes> getListaIR() {
        try {
            if (listaIR == null) {
                listaIR = new ArrayList<Inforeportes>();
                listaIR = administrarNReportesNomina.listInforeportesUsuario();
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
                listEmpleados = administrarNReportesNomina.listEmpleados();
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
                listEmpresas = administrarNReportesNomina.listEmpresas();
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
                listGruposConceptos = administrarNReportesNomina.listGruposConcetos();
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
                listUbicacionesGeograficas = administrarNReportesNomina.listUbicacionesGeograficas();
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
                listTiposAsociaciones = administrarNReportesNomina.listTiposAsociaciones();
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
                listEstructuras = administrarNReportesNomina.listEstructuras();
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
                listTiposTrabajadores = administrarNReportesNomina.listTiposTrabajadores();
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
                listTerceros = administrarNReportesNomina.listTerceros();
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
                listProcesos = administrarNReportesNomina.listProcesos();
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
                listAsociaciones = administrarNReportesNomina.listAsociaciones();
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

    public String getAltoTabla() {
        return altoTabla;
    }

    public List<Inforeportes> getListaInfoReportesModificados() {
        return listaInfoReportesModificados;
    }

    public void setListaInfoReportesModificados(List<Inforeportes> listaInfoReportesModificados) {
        this.listaInfoReportesModificados = listaInfoReportesModificados;
    }

    public boolean isCambiosReporte() {
        return cambiosReporte;
    }

    public void setCambiosReporte(boolean cambiosReporte) {
        this.cambiosReporte = cambiosReporte;
    }

    public Inforeportes getActualInfoReporteTabla() {
        return actualInfoReporteTabla;
    }

    public void setActualInfoReporteTabla(Inforeportes actualInfoReporteTabla) {
        this.actualInfoReporteTabla = actualInfoReporteTabla;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDecoracion() {
        return decoracion;
    }

    public void setDecoracion(String decoracion) {
        this.decoracion = decoracion;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color) {
        this.color2 = color;
    }

    public String getDecoracion2() {
        return decoracion2;
    }

    public void setDecoracion2(String decoracion) {
        this.decoracion2 = decoracion;
    }

    public boolean isActivoMostrarTodos() {
        return activoMostrarTodos;
    }

    public void setActivoMostrarTodos(boolean activoMostrarTodos) {
        this.activoMostrarTodos = activoMostrarTodos;
    }

    public boolean isActivoBuscarReporte() {
        return activoBuscarReporte;
    }

    public void setActivoBuscarReporte(boolean activoBuscarReporte) {
        this.activoBuscarReporte = activoBuscarReporte;
    }

    public StreamedContent getReporte() {
        System.out.println("get: " + reporte);
        return reporte;
    }

    public String getCabezeraVisor() {
        return cabezeraVisor;
    }

    public String getPathReporteGenerado() {
        return pathReporteGenerado;
    }

    /*
    public static FacesContext getFacesContext(HttpServletRequest request, HttpServletResponse response) {
        // Get current FacesContext.
        FacesContext facesContext;

        System.out.println("Entro a crear un FacesContext");
        // Create new Lifecycle.
        LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);

        // Create new FacesContext.
        FacesContextFactory contextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
        facesContext = contextFactory.getFacesContext(
                request.getSession().getServletContext(), request, response, lifecycle);

        // Create new View.
        UIViewRoot view = facesContext.getApplication().getViewHandler().createView(
                facesContext, "");
        facesContext.setViewRoot(view);

        // Set current FacesContext.
        FacesContextWrapper.setCurrentInstance(facesContext);

        return facesContext;
    }*/

    private static abstract class FacesContextWrapper extends FacesContext {

        protected static void setCurrentInstance(FacesContext facesContext) {
            FacesContext.setCurrentInstance(facesContext);
        }
    }
}

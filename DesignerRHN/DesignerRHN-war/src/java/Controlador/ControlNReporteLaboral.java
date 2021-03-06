/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import InterfaceAdministrar.AdministarReportesInterface;
import InterfaceAdministrar.AdministrarNReporteLaboralInterface;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteLaboral implements Serializable {

    @EJB
    AdministrarNReporteLaboralInterface administrarNReporteLaboral;
    @EJB
    AdministarReportesInterface administarReportes;
    //////
    private ParametrosInformes parametroDeInforme;
    private ParametrosInformes nuevoParametroInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
    private List<Inforeportes> lovInforeportes;
    private List<Inforeportes> filtrarLovInforeportes;
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
    private List<Cargos> listCargos;
    private List<Cargos> filtrarListCargos;
    private Cargos cargoSeleccionado;
    private String cargoActual, empresa;
    private boolean permitirIndex, cambiosReporte;
    private InputText empleadoDesdeParametroL, empleadoHastaParametroL, cargoParametroL, empresaParametroL;
    private List<Empresas> listEmpresas;
    private List<Empleados> listEmpleados;
    private List<Empresas> filtrarListEmpresas;
    private List<Empleados> filtrarListEmpleados;
    private Empresas empresaSeleccionada;
    private Empleados empleadoSeleccionado;
    //ALTO SCROLL TABLA
    private String altoTabla;
    private int indice;
    //EXPORTAR REPORTE
    private StreamedContent file;
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
    private BigDecimal emplDesde, emplHasta;
    //
    private boolean activoMostrarTodos, activoBuscarReporte;
    private String infoRegistroReportes, infoRegistroCargo, infoRegistroEmpleadoDesde, infoRegistroEmpleadoHasta, infoRegistroEmpresa;
    private String infoRegistro;

    public ControlNReporteLaboral() {
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
        altoTabla = "185";
        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        filtrarListCargos = null;
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        tipoLista = 0;
        reporteGenerar = "";
        requisitosReporte = "";
        posicionReporte = -1;
        listCargos = null;
        permitirIndex = true;
        listEmpleados = null;
        listEmpresas = null;
        empresaSeleccionada = new Empresas();
        empleadoSeleccionado = new Empleados();
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarNReporteLaboral.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void iniciarPagina() {
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        listaIR = null;
        getListaIR();
        if (listaIR != null) {
            modificarInfoRegistro(listaIR.size());
        } else {
            modificarInfoRegistro(0);
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
        context.update("form:reportesLaboral");
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
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (cambiosReporte == false) {
                if (parametroDeInforme.getUsuario() != null) {
                    if (parametroDeInforme.getCargo().getSecuencia() == null) {
                        parametroDeInforme.setCargo(null);
                    }

                    if (parametroDeInforme.getEmpresa().getSecuencia() == null) {
                        parametroDeInforme.setEmpresa(null);
                    }
                    administrarNReporteLaboral.modificarParametrosInformes(parametroDeInforme);
                }
            }
            if (!listaInfoReportesModificados.isEmpty()) {
                administrarNReporteLaboral.guardarCambiosInfoReportes(listaInfoReportesModificados);
            }
            cambiosReporte = true;
            context.update("form:ACEPTAR");
            getListaIR();
            if (listaIR != null) {
                //infoRegistro = "Cantidad de registros : " + listaIR.size();
                modificarInfoRegistro(listaIR.size());
            } else {
                //infoRegistro = "Cantidad de registros : 0";
                modificarInfoRegistro(0);
            }
            context.update("form:informacionRegistro");
            context.update("form:reportesLaboral");
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        }
    }

    public void modificarParametroAutocompletar(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("EMPLDESDE")) {
            parametroDeInforme.getCargo().setNombre(cargoActual);
            for (int i = 0; i < listCargos.size(); i++) {
                if (listCargos.get(i).getNombre().equals(valorConfirmar)) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroModificacion = parametroDeInforme;
                parametroModificacion.setCargo(listCargos.get(indiceUnicoElemento));
                listCargos = null;
                getListCargos();
                cambiosReporte = true;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:EmpleadoDesdeDialogo");
                context.execute("EmpleadoDesdeDialogo.show()");
            }

        }
    }

    public void posicionCelda(int i) {
        if (permitirIndex) {
            casilla = i;
            casillaInforReporte = -1;
            emplDesde = parametroDeInforme.getCodigoempleadodesde();
            fechaDesde = parametroDeInforme.getFechadesde();
            emplHasta = parametroDeInforme.getCodigoempleadohasta();
            fechaHasta = parametroDeInforme.getFechahasta();
            if (casilla == 5) {
                cargoActual = parametroDeInforme.getCargo().getNombre();
            }
            if (casilla == 6) {
                empresa = parametroDeInforme.getEmpresa().getNombre();
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla >= 1) {
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
            if (casilla == 6) {
                context.update("formularioDialogos:empresa");
                context.execute("empresa.show()");
            }
            if (casilla == 5) {
                context.update("formularioDialogos:cargo");
                context.execute("cargo.show()");
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

    public void activarAceptar() {
        aceptar = false;
    }

    public void generarReporte(int i) {
        if (cambiosReporte == true) {
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
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardarSinSalida.show()");
        }
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
            FacesContext c = FacesContext.getCurrentInstance();

            altoTabla = "185";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesLaboral");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            if (cambiosReporte == true) {
                getLovInforeportes();
                if (lovInforeportes != null) {
                    // infoRegistroReportes = "Cantidad de registros : " + lovInforeportes.size();
                    modificarInfoRegistro(lovInforeportes.size());
                } else {
                    //infoRegistroReportes = "Cantidad de registros : 0";
                    modificarInfoRegistro(0);
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
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            altoTabla = "185";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesLaboral");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listCargos = null;
        listEmpleados = null;
        listEmpresas = null;
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
        cargoSeleccionado = null;
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void cancelarYSalir() {
        cancelarModificaciones();
        salir();
    }

    public void cancelarModificaciones() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            defaultPropiedadesParametrosReporte();
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "185";
            RequestContext.getCurrentInstance().update("form:reportesLaboral");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        listaIRRespaldo = null;
        casilla = -1;
        listaInfoReportesModificados.clear();
        cambiosReporte = true;
        getParametroDeInforme();
        getListaIR();
        refrescarParametros();
        activoMostrarTodos = true;
        activoBuscarReporte = false;
        if (listaIR.size() > 0) {
            actualInfoReporteTabla = listaIR.get(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:MOSTRARTODOS");
        context.update("form:BUSCARREPORTE");
        context.update("form:ACEPTAR");
        getListaIR();
        if (listaIR != null) {
            //infoRegistro = "Cantidad de registros : " + listaIR.size();
            modificarInfoRegistro(listaIR.size());
        } else {
            // infoRegistro = "Cantidad de registros : 0";
            modificarInfoRegistro(0);
        }
        context.update("form:informacionRegistro");
        context.update("form:reportesLaboral");
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
        } else {
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

    public void actualizarSeleccionInforeporte() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "185";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesPersonal:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesPersonal:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesPersonal:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesPersonal");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listaIR = new ArrayList<Inforeportes>();
        listaIR.add(inforreporteSeleccionado);
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        lovInforeportes.clear();
        getLovInforeportes();
        filtrarLovInforeportes = null;
        aceptar = true;
        actualInfoReporteTabla = listaIR.get(0);
        activoBuscarReporte = true;
        activoMostrarTodos = false;
        context.update("form:MOSTRARTODOS");
        context.update("form:BUSCARREPORTE");
        /*
         * context.update("form:ReportesDialogo");
         * context.update("form:lovReportesDialogo");
         * context.update("form:aceptarR");
         */
        context.reset("form:lovReportesDialogo:globalFilter");
        context.execute("lovReportesDialogo.clearFilters()");
        context.execute("ReportesDialogo.hide()");

        context.update("form:reportesLaboral");
        //infoRegistro = "Cantidad de registros : " + listaIR.size();
        modificarInfoRegistro(listaIR.size());
        context.update("form:informacionRegistro");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovReportesDialogo:globalFilter");
        context.execute("lovReportesDialogo.clearFilters()");
        context.execute("ReportesDialogo.hide()");
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
            context.update("form:CargoDialogo");
            context.execute("CargoDialogo.show()");
        }
        if (casilla == 6) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
    }

    public void mostrarDialogoCargos() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CargoDialogo");
        context.execute("CargoDialogo.show()");
    }

    public void actualizarSeleccionCargo() {
        parametroDeInforme.setCargo(cargoSeleccionado);
        parametroModificacion = parametroDeInforme;
        cargoSeleccionado = new Cargos();
        filtrarListCargos = null;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        /*
         * context.update("form:CargoDialogo");
         * context.update("form:lovCargoDialogo");
         * context.update("form:aceptarC");
         */
        context.reset("form:lovCargoDialogo:globalFilter");
        context.execute("lovCargoDialogo.clearFilters()");
        context.execute("CargoDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:cargoParametroL");
        permitirIndex = true;
        aceptar = true;

    }

    public void cancelarSeleccionCargo() {
        filtrarListCargos = null;
        cargoSeleccionado = new Cargos();
        aceptar = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovCargoDialogo:globalFilter");
        context.execute("lovCargoDialogo.clearFilters()");
        context.execute("CargoDialogo.hide()");
    }

    public void actualizarEmpresa() {
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        parametroModificacion = parametroDeInforme;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        /*
         * context.update("form:EmpresaDialogo");
         * context.update("form:lovEmpresa"); context.update("form:aceptarE");
         */
        context.reset("form:lovEmpresa:globalFilter");
        context.execute("lovEmpresa.clearFilters()");
        context.execute("EmpresaDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empresaParametroL");
        empresaSeleccionada = null;
        filtrarListEmpresas = null;
        permitirIndex = true;
        aceptar = true;
    }

    public void cancelarEmpresa() {
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpresa:globalFilter");
        context.execute("lovEmpresa.clearFilters()");
        context.execute("EmpresaDialogo.hide()");
    }

    public void actualizarEmpleadoDesde() {
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        empleadoSeleccionado = null;
        filtrarListEmpleados = null;
        aceptar = true;
        permitirIndex = true;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        /*
         * context.update("form:EmpleadoDesdeDialogo");
         * context.update("form:lovEmpleadoDesde");
         * context.update("form:aceptarED");
         */
        context.reset("form:lovEmpleadoDesde:globalFilter");
        context.execute("lovEmpleadoDesde.clearFilters()");
        context.execute("EmpleadoDesdeDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoDesdeParametroL");

    }

    public void cancelarEmpleadoDesde() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoDesde:globalFilter");
        context.execute("lovEmpleadoDesde.clearFilters()");
        context.execute("EmpleadoDesdeDialogo.hide()");
    }

    public void actualizarEmpleadoHasta() {
        parametroDeInforme.setCodigoempleadohasta(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        empleadoSeleccionado = null;
        filtrarListEmpleados = null;
        cambiosReporte = false;
        RequestContext context = RequestContext.getCurrentInstance();
        /*
         * context.update("form:EmpleadoHastaDialogo");
         * context.update("form:lovEmpleadoHasta");
         * context.update("form:aceptarEH");
         */
        context.reset("form:lovEmpleadoHasta:globalFilter");
        context.execute("lovEmpleadoHasta.clearFilters()");
        context.execute("EmpleadoHastaDialogo.hide()");
        context.update("form:ACEPTAR");
        context.update("formParametros:empleadoHastaParametroL");
        aceptar = true;
        permitirIndex = true;
    }

    public void cancelarEmpleadoHasta() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEmpleadoHasta:globalFilter");
        context.execute("lovEmpleadoHasta.clearFilters()");
        context.execute("EmpleadoHastaDialogo.hide()");
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("EMPRESA")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                parametroDeInforme.setEmpresa(new Empresas());
                parametroModificacion = parametroDeInforme;
                listEmpresas.clear();
                getListEmpresas();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("CARGO")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.getCargo().setNombre(cargoActual);
                for (int i = 0; i < listCargos.size(); i++) {
                    if (listCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setCargo(listCargos.get(indiceUnicoElemento));
                    parametroModificacion = parametroDeInforme;
                    listCargos.clear();
                    getListCargos();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:CargoDialogo");
                    context.execute("CargoDialogo.show()");
                }
            } else {
                parametroDeInforme.setCargo(new Cargos());
                parametroModificacion = parametroDeInforme;
                listCargos.clear();
                getListCargos();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("DESDE")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.setCodigoempleadodesde(emplDesde);
                for (int i = 0; i < listEmpleados.size(); i++) {
                    if (listEmpleados.get(i).getCodigoempleado().toString().startsWith(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setCodigoempleadodesde(listEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                    parametroModificacion = parametroDeInforme;
                    listEmpleados.clear();
                    getListEmpleados();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:EmpleadoDesdeDialogo");
                    context.execute("EmpleadoDesdeDialogo.show()");
                }
            } else {
                parametroDeInforme.setCodigoempleadodesde(new BigDecimal("0"));
                parametroModificacion = parametroDeInforme;
                listEmpleados.clear();
                getListEmpleados();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("HASTA")) {
            if (!valorConfirmar.isEmpty()) {
                parametroDeInforme.setCodigoempleadohasta(emplHasta);
                for (int i = 0; i < listEmpleados.size(); i++) {
                    if (listEmpleados.get(i).getCodigoempleado().toString().startsWith(valorConfirmar)) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroDeInforme.setCodigoempleadohasta(listEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                    parametroModificacion = parametroDeInforme;
                    listEmpleados.clear();
                    getListEmpleados();
                    cambiosReporte = false;
                    context.update("form:ACEPTAR");
                } else {
                    permitirIndex = false;
                    context.update("form:EmpleadoHastaDialogo");
                    context.execute("EmpleadoHastaDialogo.show()");
                }
            } else {
                listEmpleados.clear();
                getListEmpleados();
                cambiosReporte = false;
                context.update("form:ACEPTAR");
                parametroDeInforme.setCodigoempleadohasta(new BigDecimal("9999999999999999999999"));
                parametroModificacion = parametroDeInforme;
            }
        }
    }

    public void mostrarTodos() {
        if (cambiosReporte == true) {
            defaultPropiedadesParametrosReporte();
            listaIR = null;
            getListaIR();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaIR != null) {
                // infoRegistro = "Cantidad de registros : " + listaIR.size();
                modificarInfoRegistro(listaIR.size());
            } else {
                //  infoRegistro = "Cantidad de registros : 0";
                modificarInfoRegistro(0);
            }
            activoBuscarReporte = false;
            activoMostrarTodos = true;
            context.update("form:MOSTRARTODOS");
            context.update("form:BUSCARREPORTE");
            context.update("form:reportesLaboral");
            context.update("form:informacionRegistro");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;
        parametroModificacion = null;
        listCargos = null;
        listEmpleados = null;
        listEmpresas = null;
        listCargos = administrarNReporteLaboral.listCargos();
        listEmpleados = administrarNReporteLaboral.listEmpleados();
        listEmpresas = administrarNReporteLaboral.listEmpresas();
        parametroDeInforme = administrarNReporteLaboral.parametrosDeReporte();
        if (parametroDeInforme.getCargo() == null) {
            parametroDeInforme.setCargo(new Cargos());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formParametros:fechaDesdeParametroL");
        context.update("formParametros:empleadoDesdeParametroL");
        context.update("formParametros:fechaHastaParametroL");
        context.update("formParametros:empleadoHastaParametroL");
        context.update("formParametros:cargoParametroL");
        context.update("formParametros:tipoPersonalParametroL");
        context.update("formParametros:empresaParametroL");
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        //infoRegistro = "Cantidad de Registros: " + filtrarListInforeportesUsuario.size();
        modificarInfoRegistro(filtrarListInforeportesUsuario.size());
        context.update("form:informacionRegistro");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "161";
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:tipoIR");
            tipoIR.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:reportesLaboral");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "185";
            defaultPropiedadesParametrosReporte();
            codigoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) c.getViewRoot().findComponent("form:reportesLaboral:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesLaboral");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }

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
        FacesContext c = FacesContext.getCurrentInstance();

        empleadoDesdeParametroL = (InputText) c.getViewRoot().findComponent("formParametros:empleadoDesdeParametroL");
        empleadoDesdeParametroL.setStyle("position: absolute; top: 50px; left: 120px;height: 15px;width: 90px;");
        RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametroL");

        empleadoHastaParametroL = (InputText) c.getViewRoot().findComponent("formParametros:empleadoHastaParametroL");
        empleadoHastaParametroL.setStyle("position: absolute; top: 50px; left: 290px;height: 15px;width: 90px;");
        RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametroL");

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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formParametros");
            context.execute("errorRegNew.show()");
        }
    }

    public void resaltoParametrosParaReporte(int i) {
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
            FacesContext c = FacesContext.getCurrentInstance();

            empleadoDesdeParametroL = (InputText) c.getViewRoot().findComponent("formParametros:empleadoDesdeParametroL");
            empleadoDesdeParametroL.setStyle("position: absolute; top: 50px; left: 120px;height: 15px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:empleadoDesdeParametroL");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            FacesContext c = FacesContext.getCurrentInstance();

            empleadoHastaParametroL = (InputText) c.getViewRoot().findComponent("formParametros:empleadoHastaParametroL");
            empleadoHastaParametroL.setStyle("position: absolute; top: 50px; left: 290px;height: 15px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("formParametros:empleadoHastaParametroL");
        }
    }

    public void parametrosDeReporte(int i) {
        String cadena = "";
        Inforeportes reporteS = new Inforeportes();
        if (tipoLista == 0) {
            reporteS = listaIR.get(i);
        }
        if (tipoLista == 1) {
            reporteS = filtrarListInforeportesUsuario.get(i);
        }
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

    public void generarReporte() throws IOException {
        if (cambiosReporte == true) {
            String nombreReporte, tipoReporte;
            String pathReporteGenerado = null;
            if (tipoLista == 0) {
                nombreReporte = listaIR.get(indice).getNombrereporte();
                tipoReporte = listaIR.get(indice).getTipo();
            } else {
                nombreReporte = filtrarListInforeportesUsuario.get(indice).getNombrereporte();
                tipoReporte = filtrarListInforeportesUsuario.get(indice).getTipo();
            }
            if (nombreReporte != null && tipoReporte != null) {
                pathReporteGenerado = administarReportes.generarReporte(nombreReporte, tipoReporte);
            }
            if (pathReporteGenerado != null) {
                exportarReporte(pathReporteGenerado);
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void exportarReporte(String rutaReporteGenerado) throws IOException {
        File reporte = new File(rutaReporteGenerado);
        FacesContext ctx = FacesContext.getCurrentInstance();
        FileInputStream fis = new FileInputStream(reporte);
        byte[] bytes = new byte[1024];
        int read;
        if (!ctx.getResponseComplete()) {
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

    public void cancelarRequisitosReporte() {
        requisitosReporte = "";
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
        System.out.println("infoRegistro: " + infoRegistro);
    }

    //GET & SET
    public ParametrosInformes getParametroDeInforme() {
        try {
            if (parametroDeInforme == null) {
                parametroDeInforme = new ParametrosInformes();
                parametroDeInforme = administrarNReporteLaboral.parametrosDeReporte();
                if (parametroDeInforme != null) {
                    if (parametroDeInforme.getCargo() == null) {
                        parametroDeInforme.setCargo(new Cargos());
                    }
                }
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
//        if (listaIR == null) {
//            listaIR = administrarNReporteLaboral.listInforeportesUsuario();
//        }
//        if (listaIR != null) {
//            listaIRRespaldo = listaIR;
//        }
//        return listaIR;
//        
        if (listaIR == null) {
            listaIR = administrarNReporteLaboral.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaIR != null) {
                int tam = listaIR.size();
                if (tam > 0) {
                    actualInfoReporteTabla = listaIR.get(0);
                }
                //context.update("form:infoRegistroMotivos");
            }
        }
        return listaIR;
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

    public List<Cargos> getListCargos() {
//        try {
//            listCargos = administrarNReporteLaboral.listCargos();
//            return listCargos;
//        } catch (Exception e) {
//            System.out.println("Error en getListCargos : " + e.toString());
//            return null;
//        }
        if (listCargos == null) {
            listCargos = administrarNReporteLaboral.listCargos();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listCargos == null || listCargos.isEmpty()) {
                infoRegistroCargo = "0";
            } else {
                infoRegistroCargo = String.valueOf(listCargos.size());
            }
            context.update("form:infoRegistroCargo");
        }
        return listCargos;
    }

    public void setListCargos(List<Cargos> listCargos) {
        this.listCargos = listCargos;
    }

    public List<Cargos> getFiltrarListCargos() {
        return filtrarListCargos;
    }

    public void setFiltrarListCargos(List<Cargos> filtrarListCargos) {
        this.filtrarListCargos = filtrarListCargos;
    }

    public Cargos getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargos cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }

    public List<Empresas> getListEmpresas() {
//        try {
//            listEmpresas = administrarNReporteLaboral.listEmpresas();
//            return listEmpresas;
//        } catch (Exception e) {
//            System.out.println("Error en getListEmpresas : " + e.toString());
//            return null;
//        }
//        
        if (listEmpresas == null) {
            listEmpresas = administrarNReporteLaboral.listEmpresas();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listEmpresas == null || listEmpresas.isEmpty()) {
                infoRegistroEmpresa = "0";
            } else {
                infoRegistroEmpresa = String.valueOf(listEmpresas.size());
            }
            context.update("form:infoRegistroEmpresa");
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Empleados> getListEmpleados() {
        try {
            listEmpleados = administrarNReporteLaboral.listEmpleados();
            return listEmpleados;
        } catch (Exception e) {
            System.out.println("Error en getListEmpleados : " + e.toString());
            return null;
        }

    }

    public void setListEmpleados(List<Empleados> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public List<Empleados> getFiltrarListEmpleados() {
        return filtrarListEmpleados;
    }

    public void setFiltrarListEmpleados(List<Empleados> filtrarListEmpleados) {
        this.filtrarListEmpleados = filtrarListEmpleados;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public boolean isCambiosReporte() {
        return cambiosReporte;
    }

    public void setCambiosReporte(boolean cambiosReporte) {
        this.cambiosReporte = cambiosReporte;
    }

    public List<Inforeportes> getListaInfoReportesModificados() {
        return listaInfoReportesModificados;
    }

    public void setListaInfoReportesModificados(List<Inforeportes> listaInfoReportesModificados) {
        this.listaInfoReportesModificados = listaInfoReportesModificados;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void setFile(StreamedContent file) {
        this.file = file;
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

    public Inforeportes getActualInfoReporteTabla() {
//        getListaIR();
//        if (listaIR != null) {
//            int tam = listaIR.size();
//            if (tam > 0) {
//                actualInfoReporteTabla = listaIR.get(0);
//            }
//        }
        return actualInfoReporteTabla;
    }

    public void setActualInfoReporteTabla(Inforeportes actualInfoReporteTabla) {
        this.actualInfoReporteTabla = actualInfoReporteTabla;
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

    public String getInfoRegistroReportes() {
        return infoRegistroReportes;
    }

    public void setInfoRegistroReportes(String infoRegistroReportes) {
        this.infoRegistroReportes = infoRegistroReportes;
    }

    public String getInfoRegistroCargo() {
//        getListCargos();
//        if (listCargos != null) {
//            infoRegistroCargo = String.valueOf(listCargos.size());
//        } else {
//            infoRegistroCargo = "0";
//        };
        return infoRegistroCargo;
    }

    public void setInfoRegistroCargo(String infoRegistroCargo) {
        this.infoRegistroCargo = infoRegistroCargo;
    }

    public String getInfoRegistroEmpleadoDesde() {
        getListEmpleados();
        if (listEmpleados != null) {
            infoRegistroEmpleadoDesde = String.valueOf(listEmpleados.size());
        } else {
            infoRegistroEmpleadoDesde = "0";
        }
        return infoRegistroEmpleadoDesde;
    }

    public void setInfoRegistroEmpleadoDesde(String infoRegistroEmpleadoDesde) {
        this.infoRegistroEmpleadoDesde = infoRegistroEmpleadoDesde;
    }

    public String getInfoRegistroEmpleadoHasta() {
        getListEmpleados();
        if (listEmpleados != null) {
            infoRegistroEmpleadoHasta = String.valueOf(listEmpleados.size());
        } else {
            infoRegistroEmpleadoHasta = "0";
        }
        return infoRegistroEmpleadoHasta;
    }

    public void setInfoRegistroEmpleadoHasta(String infoRegistroEmpleadoHasta) {
        this.infoRegistroEmpleadoHasta = infoRegistroEmpleadoHasta;
    }

    public String getInfoRegistroEmpresa() {
//        getListEmpresas();
//        if (listEmpresas != null) {
//            infoRegistroEmpresa = String.valueOf(listEmpresas.size());
//        } else {
//            infoRegistroEmpresa = "0";
//        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public List<Inforeportes> getLovInforeportes() {
        lovInforeportes = administrarNReporteLaboral.listInforeportesUsuario();
        return lovInforeportes;
    }

    public void setLovInforeportes(List<Inforeportes> lovInforeportes) {
        this.lovInforeportes = lovInforeportes;
    }

    public List<Inforeportes> getFiltrarLovInforeportes() {
        return filtrarLovInforeportes;
    }

    public void setFiltrarLovInforeportes(List<Inforeportes> filtrarLovInforeportes) {
        this.filtrarLovInforeportes = filtrarLovInforeportes;
    }
}

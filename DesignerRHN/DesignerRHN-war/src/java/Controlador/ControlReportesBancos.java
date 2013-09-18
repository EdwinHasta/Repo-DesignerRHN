/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Bancos;
import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarReportesBancosInterface;
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
public class ControlReportesBancos implements Serializable {

    @EJB
    AdministrarReportesBancosInterface administrarReportesBancos;
    private ParametrosInformes parametroDeInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
    private String reporteGenerar;
    private Inforeportes inforreporteSeleccionado;
    private int bandera;
    private boolean aceptar;
    private Column codigoIR, reporteIR;
    private int casilla;
    private ParametrosInformes parametroModificacion;
    private int tipoLista;
    private int posicionReporte;
    private String requisitosReporte;
    private Calendar fechaDesdeParametro, fechaHastaParametro, fechaCorteParametro;
    private InputText empleadoDesdeParametro, empleadoHastaParametro, tipoTrabajadorParametro, ciudadParametro;
    ///
    private List<Empleados> listEmpleados;
    private List<Empresas> listEmpresas;
    private List<Bancos> listBancos;
    private List<Procesos> listProcesos;
    private List<TiposTrabajadores> listTiposTrabajadores;
    private List<Ciudades> listCiudades;
    //
    private Empleados empleadoSeleccionado;
    private Empresas empresaSeleccionada;
    private TiposTrabajadores tipoTSeleccionado;
    private Procesos procesoSeleccionado;
    private Bancos bancoSeleccionado;
    private Ciudades ciudadSeleccionada;
    //////////////////
    private List<Empleados> filtrarListEmpleados;
    private List<Empresas> filtrarListEmpresas;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private List<Procesos> filtrarListProcesos;
    private List<Bancos> filtrarListBancos;
    private List<Ciudades> filtrarListCiudades;
    //
    private String banco, empresa, tipoTrabajador, proceso, ciudad;
    private boolean permitirIndex;

    public ControlReportesBancos() {
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
        listEmpleados = null;
        listEmpresas = null;
        listProcesos = null;
        listTiposTrabajadores = null;
        listCiudades = null;

        empleadoSeleccionado = new Empleados();
        empresaSeleccionada = new Empresas();
        tipoTSeleccionado = new TiposTrabajadores();
        procesoSeleccionado = new Procesos();
        ciudadSeleccionada = new Ciudades();
        permitirIndex = true;
    }

    public void guardarCambios() {
        try {
            if (parametroModificacion.getCodigoempleadodesde() == null) {
                parametroModificacion.setCodigoempleadodesde(null);
            }
            if (parametroModificacion.getCodigoempleadohasta() == null) {
                parametroModificacion.setCodigoempleadohasta(null);
            }
            if (parametroModificacion.getTipotrabajador().getSecuencia() == null) {
                parametroModificacion.setTipotrabajador(null);
            }
            if (parametroModificacion.getProceso().getSecuencia() == null) {
                parametroModificacion.setProceso(null);
            }
            if (parametroModificacion.getEmpresa().getSecuencia() == null) {
                parametroModificacion.setEmpresa(null);
            }
            if (parametroModificacion.getCiudad().getSecuencia() == null) {
                parametroModificacion.setCiudad(null);
            }

            administrarReportesBancos.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void modificarParametroInforme() {
        parametroModificacion = parametroDeInforme;
    }

    public void posicionCelda(int i) {
        casilla = i;
        if (permitirIndex == true) {
            if (casilla == 3) {
                empresa = parametroDeInforme.getEmpresa().getNombre();
            }
            if (casilla == 4) {
                proceso = parametroDeInforme.getProceso().getDescripcion();
            }
            if (casilla == 8) {
                banco = parametroDeInforme.getBanco().getNombre();
            }
            if (casilla == 9) {
                tipoTrabajador = parametroDeInforme.getTipotrabajador().getNombre();
            }
            if (casilla == 11) {
                ciudad = parametroDeInforme.getCiudad().getNombre();
            }
        }
        System.out.println("Casilla ¨= " + casilla);
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;


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
            } else {
                permitirIndex = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
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
            } else {
                permitirIndex = false;
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
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
            } else {
                permitirIndex = false;
                context.update("form:ProcesoDialogo");
                context.execute("ProcesoDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("BANCO")) {
            parametroDeInforme.getBanco().setNombre(banco);
            for (int i = 0; i < listBancos.size(); i++) {
                if (listBancos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setBanco(listBancos.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listBancos.clear();
                getListBancos();
            } else {
                permitirIndex = false;
                context.update("form:BancoDialogo");
                context.execute("BancoDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("CIUDAD")) {
            parametroDeInforme.getCiudad().setNombre(ciudad);
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setCiudad(listCiudades.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listCiudades.clear();
                getListCiudades();
            } else {
                permitirIndex = false;
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
            }
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 2) {
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (casilla == 3) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
        if (casilla == 4) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
        if (casilla == 6) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (casilla == 8) {
            context.update("form:BancoDialogo");
            context.execute("BancoDialogo.show()");
        }
        if (casilla == 9) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (casilla == 11) {
            context.update("form:CiudadDialogo");
            context.execute("CiudadDialogo.show()");
        }

    }

    public void listasValores(int pos) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (pos == 2) {
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (pos == 3) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
        if (pos == 4) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
        if (pos == 6) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }

        if (pos == 8) {
            context.update("form:BancoDialogo");
            context.execute("BancoDialogo.show()");
        }
        if (pos == 9) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (pos == 11) {
            context.update("form:CiudadDialogo");
            context.execute("CiudadDialogo.show()");
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
            context.update("formularioDialogos:empresa");
            context.execute("empresa.show()");
        }
        if (casilla == 4) {
            context.update("formularioDialogos:proceso");
            context.execute("proceso.show()");
        }
        if (casilla == 5) {
            context.update("formularioDialogos:editarFechaHasta");
            context.execute("editarFechaHasta.show()");
        }
        if (casilla == 6) {
            context.update("formularioDialogos:empleadoHasta");
            context.execute("empleadoHasta.show()");
        }
        if (casilla == 7) {
            context.update("formularioDialogos:numeroCuenta");
            context.execute("numeroCuenta.show()");
        }
        if (casilla == 8) {
            context.update("formularioDialogos:banco");
            context.execute("banco.show()");
        }
        if (casilla == 9) {
            context.update("formularioDialogos:tipoTrabajador");
            context.execute("tipoTrabajador.show()");
        }
        if (casilla == 10) {
            context.update("formularioDialogos:editarFechaCorte");
            context.execute("editarFechaCorte.show()");
        }
        if (casilla == 11) {
            context.update("formularioDialogos:ciudad");
            context.execute("ciudad.show()");
        }
        casilla = -1;

    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;
        listBancos = null;
        listCiudades = null;
        listEmpleados = null;
        listEmpresas = null;
        listTiposTrabajadores = null;
        listProcesos = null;
        listProcesos = administrarReportesBancos.listProcesos();
        listTiposTrabajadores = administrarReportesBancos.listTiposTrabajadores();
        listBancos = administrarReportesBancos.listBancos();
        listCiudades = administrarReportesBancos.listCiudades();
        listEmpleados = administrarReportesBancos.listEmpleados();
        listEmpresas = administrarReportesBancos.listEmpresas();
        parametroDeInforme = administrarReportesBancos.parametrosDeReporte();

        if (parametroDeInforme == null) {
            parametroDeInforme = new ParametrosInformes();
            parametroDeInforme = administrarReportesBancos.parametrosDeReporte();
        }

        if (parametroDeInforme.getBanco() == null) {
            parametroDeInforme.setBanco(new Bancos());
        }

        if (parametroDeInforme.getCiudad() == null) {
            parametroDeInforme.setCiudad(new Ciudades());
        }

        if (parametroDeInforme.getTipotrabajador() == null) {
            parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
        }

        if (parametroDeInforme.getProceso() == null) {
            parametroDeInforme.setProceso(new Procesos());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:fechaDesdeParametro");
        context.update("form:empleadoDesdeParametro");
        context.update("form:empresaParametro");
        context.update("form:procesoParametro");
        context.update("form:tipoCuentaParametro");
        context.update("form:fechaHastaParametro");
        context.update("form:empleadoHastaParametro");
        context.update("form:numeroCuentaParametro");
        context.update("form:bancoParametro");
        context.update("form:tipoTrabajadorParametro");
        context.update("form:fechaCorteParametro");
        context.update("form:ciudadParametro");
        
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarEmplDesde() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoDesdeParametro");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoHastaParametro");
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

    public void actualizarEmpresa() {
        permitirIndex = true;
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empresaParametro");
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

    public void actualizarTipoTrabajador() {
        parametroDeInforme.setTipotrabajador(tipoTSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tipoTrabajadorParametro");
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

    public void actualizarProceso() {
        permitirIndex = true;
        parametroDeInforme.setProceso(procesoSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:procesoParametro");
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

    public void actualizarBanco() {
        permitirIndex = true;
        parametroDeInforme.setBanco(bancoSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:bancoParametro");
        bancoSeleccionado = null;
        aceptar = true;
        filtrarListBancos = null;

    }

    public void cancelarBanco() {
        bancoSeleccionado = null;
        aceptar = true;
        filtrarListBancos = null;
        permitirIndex = true;
    }

    public void actualizarCiudad() {
        permitirIndex = true;
        parametroDeInforme.setCiudad(ciudadSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ciudadParametro");
        ciudadSeleccionada = null;
        aceptar = true;
        filtrarListCiudades = null;

    }

    public void cancelarCiudad() {
        ciudadSeleccionada = null;
        aceptar = true;
        filtrarListCiudades = null;
        permitirIndex = true;
    }

    public void archivoPlanoReporte(int i) {
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
            listaIR = administrarReportesBancos.listInforeportesUsuario();
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
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        listEmpleados = null;
        listEmpresas = null;
        listProcesos = null;
        listTiposTrabajadores = null;
        listBancos = null;
        listCiudades = null;
        casilla = -1;
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
            defaultPropiedadesParametrosReporte();
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarReportesBancos.listInforeportesUsuario();
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
        fechaDesdeParametro.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaDesdeParametro");

        fechaHastaParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
        fechaHastaParametro.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaHastaParametro");

        fechaCorteParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
        fechaCorteParametro.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaCorteParametro");

        empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametro");
        empleadoDesdeParametro.setStyle("position: absolute; top: 40px; left: 120px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");

        empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
        empleadoHastaParametro.setStyle("position: absolute; top: 40px; left: 390px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");

        tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
        tipoTrabajadorParametro.setStyle("position: absolute; top: 115px; left: 390px;font-size: 11px;height: 10px;width: 180px;");
        RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");

        ciudadParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:ciudadParametro");
        ciudadParametro.setStyle("position: absolute; top: 45px; left: 690px;font-size: 11px;height: 10px;");
        RequestContext.getCurrentInstance().update("form:ciudadParametro");
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
            empleadoDesdeParametro.setStyle("position: absolute; top: 40px; left: 120px;font-size: 11px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
            empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
            empleadoHastaParametro.setStyle("position: absolute; top: 40px; left: 390px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");
        }

        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
            tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
            tipoTrabajadorParametro.setStyle("position: absolute; top: 115px; left: 390px;font-size: 11px;height: 10px;width: 180px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");
        }

        if (reporteS.getCiudad().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Ciudad -";
            ciudadParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:ciudadParametro");
            ciudadParametro.setStyle("position: absolute; top: 45px; left: 690px;font-size: 11px;height: 10px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");
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
        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
        }
        if (reporteS.getGrupo().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Ciudad -";
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
                parametroDeInforme = administrarReportesBancos.parametrosDeReporte();
            }

            if (parametroDeInforme.getBanco() == null) {
                parametroDeInforme.setBanco(new Bancos());
            }

            if (parametroDeInforme.getCiudad() == null) {
                parametroDeInforme.setCiudad(new Ciudades());
            }

            if (parametroDeInforme.getTipotrabajador() == null) {
                parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
            }

            if (parametroDeInforme.getProceso() == null) {
                parametroDeInforme.setProceso(new Procesos());
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
                listaIR = administrarReportesBancos.listInforeportesUsuario();
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
                listEmpleados = administrarReportesBancos.listEmpleados();
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
                listEmpresas = administrarReportesBancos.listEmpresas();
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

    public List<TiposTrabajadores> getListTiposTrabajadores() {
        try {
            if (listTiposTrabajadores == null) {
                listTiposTrabajadores = administrarReportesBancos.listTiposTrabajadores();
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

    public List<Procesos> getListProcesos() {
        try {
            if (listProcesos == null) {
                listProcesos = administrarReportesBancos.listProcesos();
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

    public TiposTrabajadores getTipoTSeleccionado() {
        return tipoTSeleccionado;
    }

    public void setTipoTSeleccionado(TiposTrabajadores tipoTSeleccionado) {
        this.tipoTSeleccionado = tipoTSeleccionado;
    }

    public Procesos getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
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

    public List<Procesos> getFiltrarListProcesos() {
        return filtrarListProcesos;
    }

    public void setFiltrarListProcesos(List<Procesos> filtrarListProcesos) {
        this.filtrarListProcesos = filtrarListProcesos;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }

    public List<Bancos> getListBancos() {
        try {
            if (listBancos == null) {
                listBancos = new ArrayList<Bancos>();
                listBancos = administrarReportesBancos.listBancos();
            }
            return listBancos;
        } catch (Exception e) {
            System.out.println("Error en getListBancos : " + e.toString());
            return null;
        }
    }

    public void setListBancos(List<Bancos> listBancos) {
        this.listBancos = listBancos;
    }

    public Bancos getBancoSeleccionado() {
        return bancoSeleccionado;
    }

    public void setBancoSeleccionado(Bancos bancoSeleccionado) {
        this.bancoSeleccionado = bancoSeleccionado;
    }

    public List<Bancos> getFiltrarListBancos() {
        return filtrarListBancos;
    }

    public void setFiltrarListBancos(List<Bancos> filtrarListBancos) {
        this.filtrarListBancos = filtrarListBancos;
    }

    public List<Ciudades> getListCiudades() {
        try {
            if (listCiudades == null) {
                listCiudades = new ArrayList<Ciudades>();
                listCiudades = administrarReportesBancos.listCiudades();
            }
            return listCiudades;
        } catch (Exception e) {
            System.out.println("Error en getListCiudades : " + e.toString());
            return null;
        }
    }

    public void setListCiudades(List<Ciudades> listCiudades) {
        this.listCiudades = listCiudades;
    }

    public Ciudades getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudades ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public List<Ciudades> getFiltrarListCiudades() {
        return filtrarListCiudades;
    }

    public void setFiltrarListCiudades(List<Ciudades> filtrarListCiudades) {
        this.filtrarListCiudades = filtrarListCiudades;
    }
}

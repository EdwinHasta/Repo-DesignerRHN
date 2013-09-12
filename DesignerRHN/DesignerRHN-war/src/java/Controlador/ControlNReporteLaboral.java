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
import InterfaceAdministrar.AdministrarNReporteLaboralInterface;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteLaboral implements Serializable {

    @EJB
    AdministrarNReporteLaboralInterface administrarNReporteLaboral;
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
    private List<Cargos> listCargos;
    private List<Cargos> filtrarListCargos;
    private Cargos cargoSeleccionado;
    private String cargoActual, emplDesde, emplHasta, empresa;
    private boolean permitirIndex;
    private Calendar fechaDesdeParametroL, fechaHastaParametroL;
    private InputText empleadoDesdeParametroL, empleadoHastaParametroL, cargoParametroL, empresaParametroL;
    private List<Empresas> listEmpresas;
    private List<Empleados> listEmpleados;
    private List<Empresas> filtrarListEmpresas;
    private List<Empleados> filtrarListEmpleados;
    private Empresas empresaSeleccionada;
    private Empleados empleadoSeleccionado;

    public ControlNReporteLaboral() {

        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        filtrarListInforeportesUsuario = null;
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

    public void guardarCambios() {
        System.out.println("Guardar cambios");
        try {
            if (parametroModificacion.getGrupo().getSecuencia() == null) {
                parametroModificacion.setGrupo(null);
            }

            if (parametroModificacion.getEmpresa().getSecuencia() == null) {
                parametroModificacion.setEmpresa(null);
            }
            System.out.println("tipo pèrsopnal "+parametroModificacion.getTipopersonal());
            administrarNReporteLaboral.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
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
            } else {
                permitirIndex = false;
                context.update("form:EmpleadoDesdeDialogo");
                context.execute("EmpleadoDesdeDialogo.show()");
            }
        }
    }

    public void actualizarCargo() {
        permitirIndex = true;
        parametroDeInforme.setCargo(cargoSeleccionado);
        parametroModificacion = parametroDeInforme;
        cargoSeleccionado = null;
        aceptar = true;
        filtrarListCargos = null;
    }

    public void cancelarCambioCargo() {
        cargoSeleccionado = null;
        aceptar = true;
        filtrarListCargos = null;
        permitirIndex = true;
    }

    public void posicionCelda(int i) {
        if (permitirIndex) {
            casilla = i;
            if (casilla == 2) {
                emplDesde = parametroDeInforme.getCodigoempleadodesde().toString();
            }
            if (casilla == 4) {
                emplHasta = parametroDeInforme.getCodigoempleadohasta().toString();
            }
            if (casilla == 5) {
                cargoActual = parametroDeInforme.getCargo().getNombre();
            }
            if (casilla == 6) {
                empresa = parametroDeInforme.getEmpresa().getNombre();
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
            listaIR = administrarNReporteLaboral.listInforeportesUsuario();
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
        listCargos = null;
        listEmpleados = null;
        listEmpresas = null;
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
        cargoSeleccionado = null;
    }

    public void actualizarSeleccionInforeporte() {
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
        cargoSeleccionado = new Cargos();
        filtrarListCargos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:cargoParametroL");
        defaultPropiedadesParametrosReporte();
        permitirIndex = true;

    }

    public void cancelarSeleccionCargo() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        permitirIndex = true;
    }

    public void actualizarEmpresa() {
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        empresaSeleccionada = null;
        filtrarListEmpresas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empresaParametroL");
        defaultPropiedadesParametrosReporte();
        permitirIndex = true;
    }

    public void cancelarEmpresa() {
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
        permitirIndex = true;
    }

    public void actualizarEmpleadoDesde() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        empleadoSeleccionado = null;
        filtrarListEmpleados = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoDesdeParametroL");
        aceptar = false;
    }

    public void cancelarEmpleadoDesde() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void actualizarEmpleadoHasta() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme.setCodigoempleadohasta(empleadoSeleccionado.getCodigoempleado());
        empleadoSeleccionado = null;
        filtrarListEmpleados = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoHastaParametroL");
    }

    public void cancelarEmpleadoHasta() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("EMPLDESDE")) {
            BigInteger codigo = new BigInteger(emplDesde);
            parametroDeInforme.setCodigoempleadodesde(codigo);
            for (int i = 0; i < listEmpleados.size(); i++) {
                if (listEmpleados.get(i).getCodigoempleado().equals(valorConfirmar)) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setCodigoempleadodesde(listEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                parametroModificacion = parametroDeInforme;
                listEmpleados.clear();
                getListEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:EmpleadoDesdeDialogo");
                context.execute("EmpleadoDesdeDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("EMPLHASTA")) {
            BigInteger codigo = new BigInteger(emplHasta);
            parametroDeInforme.setCodigoempleadohasta(codigo);

            for (int i = 0; i < listEmpleados.size(); i++) {
                if (listEmpleados.get(i).getCodigoempleado().equals(valorConfirmar)) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setCodigoempleadohasta(listEmpleados.get(indiceUnicoElemento).getCodigoempleado());
                parametroModificacion = parametroDeInforme;
                listEmpleados.clear();
                getListEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:EmpleadoHastaDialogo");
                context.execute("EmpleadoHastaDialogo.show()");
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
            } else {
                permitirIndex = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("CARGO")) {
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
            } else {
                permitirIndex = false;
                context.update("form:CargoDialogo");
                context.execute("CargoDialogo.show()");
            }
        }
    }

    public void autocompletarCargo(String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        parametroDeInforme.getCargo().setNombre(cargoActual);
        for (int i = 0; i < listCargos.size(); i++) {
            if (listCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                indiceUnicoElemento = i;
                coincidencias++;
            }
        }
        if (coincidencias == 1) {
            parametroDeInforme.setCargo(listCargos.get(indiceUnicoElemento));
            listCargos.clear();
            listCargos = getListCargos();
            context.update("form:cargoParametroL");
        } else {
            permitirIndex = false;
            context.update("form:CargoDialogo");
            context.execute("CargoDialogo.show()");
        }

    }

    public void mostrarTodos() {
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        getListaIR();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:reportesNomina");
    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;
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
        context.update("form:fechaDesdeParametroL");
        context.update("form:empleadoDesdeParametroL");
        context.update("form:fechaHastaParametroL");
        context.update("form:empleadoHastaParametroL");
        context.update("form:cargoParametroL");
        context.update("form:tipoPersonalParametroL");
        context.update("form:empresaParametroL");
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
            listaIRRespaldo = administrarNReporteLaboral.listInforeportesUsuario();
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
        fechaDesdeParametroL.setStyleClass("ui-datepicker, myClass");
        RequestContext.getCurrentInstance().update("form:fechaDesdeParametroL");

        fechaHastaParametroL = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametroL");
        fechaHastaParametroL.setStyleClass("ui-datepicker, myClass");
        RequestContext.getCurrentInstance().update("form:fechaHastaParametroL");

        empleadoDesdeParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametroL");
        empleadoDesdeParametroL.setStyle("position: absolute; top: 50px; left: 90px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoDesdeParametroL");

        empleadoHastaParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametroL");
        empleadoHastaParametroL.setStyle("position: absolute; top: 50px; left: 270px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoHastaParametroL");

    }

    public void modificarParametroInforme() {
        parametroModificacion = parametroDeInforme;
        System.out.println("tipo pèrsopnal "+parametroModificacion.getTipopersonal());
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
            empleadoDesdeParametroL.setStyle("position: absolute; top: 50px; left: 90px;font-size: 11px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoDesdeParametroL");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
            empleadoHastaParametroL = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametroL");
            empleadoHastaParametroL.setStyle("position: absolute; top: 50px; left: 270px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
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
        System.out.println("Cadena : "+cadena);
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
                parametroDeInforme = administrarNReporteLaboral.parametrosDeReporte();
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
                listaIR = administrarNReporteLaboral.listInforeportesUsuario();
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
        System.out.println("Requisito reporte : "+requisitosReporte);
        return requisitosReporte;
    }

    public void setRequisitosReporte(String requisitosReporte) {
        this.requisitosReporte = requisitosReporte;
    }

    public List<Cargos> getListCargos() {
        try {
            if (listCargos == null) {
                listCargos = new ArrayList<Cargos>();
                listCargos = administrarNReporteLaboral.listCargos();
            }
        } catch (Exception e) {
            System.out.println("Error en getListCargos : " + e.toString());
            return null;
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
        try {
            if (listEmpresas == null) {
                listEmpresas = new ArrayList<Empresas>();
                listEmpresas = administrarNReporteLaboral.listEmpresas();
            }
        } catch (Exception e) {
            System.out.println("Error en getListEmpresas : " + e.toString());
            return null;
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Empleados> getListEmpleados() {
        try {
            if (listEmpleados == null) {
                listEmpleados = new ArrayList<Empleados>();
                listEmpleados = administrarNReporteLaboral.listEmpleados();
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
}

package Controlador;

import Entidades.Empleados;
import Entidades.VWVacaPendientesEmpleados;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarVWVacaPendientesEmpleadosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlEmplVacaPendiente implements Serializable {

    @EJB
    AdministrarVWVacaPendientesEmpleadosInterface administrarVWVacaPendientesEmpleados;
    private List<VWVacaPendientesEmpleados> listVacaPendientes;
    private List<VWVacaPendientesEmpleados> filtrarListVacaPendientes;
    private List<VWVacaPendientesEmpleados> listVacaDisfrutadas;
    private List<VWVacaPendientesEmpleados> filtrarListVacaDisfrutadas;
    private int tipoTabla, filtrarListaPendientes, filtrarListaDisfrutadas;
    private int banderaPendientes, banderaDisfrutadas;
    private int casillaPendiente, casillaDisfrutada;
    private int indexVPendientes, indexVDisfrutadas;
    //////
    private Empleados empleado;
    //
    private List<VWVacaPendientesEmpleados> listModificacionesTablaPendientes;
    private List<VWVacaPendientesEmpleados> listModificacionesTablaDisfrutadas;
    //////
    private List<VWVacaPendientesEmpleados> listCrearTablaPendientes;
    private List<VWVacaPendientesEmpleados> listCrearTablaDisfrutadas;
    //////
    private List<VWVacaPendientesEmpleados> listBorrarTablaPendientes;
    private List<VWVacaPendientesEmpleados> listBorrarTablaDisfrutadas;
    //
    private VWVacaPendientesEmpleados editarVacacion;
    private VWVacaPendientesEmpleados nuevaVacacion;
    private VWVacaPendientesEmpleados duplicarVacacion;
    //
    private String nombreTabla, nombreXML;
    //
    private BigInteger totalDiasPendientes;
    private BigInteger secuencia;
    //
    private BigDecimal diasProvisionados;
    //
    private Column vacacionesDP, vacacionesFechaInicial, vacacionesFechaFinal, vacacionesDPD, vacacionesFechaInicialD, vacacionesFechaFinalD;

    public ControlEmplVacaPendiente() {
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        tipoTabla = 0;
        banderaPendientes = 0;
        banderaDisfrutadas = 0;
        filtrarListaPendientes = 0;
        filtrarListaDisfrutadas = 0;
        casillaPendiente = 0;
        casillaDisfrutada = 0;
        indexVPendientes = 0;
        indexVDisfrutadas = 0;
        editarVacacion = new VWVacaPendientesEmpleados();
        nuevaVacacion = new VWVacaPendientesEmpleados();
        duplicarVacacion = new VWVacaPendientesEmpleados();
        listModificacionesTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listModificacionesTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        listCrearTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listCrearTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        listBorrarTablaPendientes = new ArrayList<VWVacaPendientesEmpleados>();
        listBorrarTablaDisfrutadas = new ArrayList<VWVacaPendientesEmpleados>();
        nombreTabla = ":formExportarVS:datosVSEmpleadoExportar";
        nombreXML = "VacacionesPendientesXML";
        totalDiasPendientes = BigInteger.valueOf(0);
        diasProvisionados = BigDecimal.valueOf(0);
    }

    public void recibirEmpleado(BigInteger sec) {
        this.secuencia = sec;
        empleado = administrarVWVacaPendientesEmpleados.obtenerEmpleado(secuencia);
    }

    public void modificacionesTablas() {
        if (tipoTabla == 1) {
            if (filtrarListaPendientes == 0) {
                VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(indexVPendientes);
                listModificacionesTablaPendientes.add(vacaPendiente);
            } else {
                int posicion = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(posicion);
                listModificacionesTablaPendientes.add(vacaPendiente);
            }
            for (int i = 0; i < listVacaPendientes.size(); i++) {
                System.out.println("Dias pendientes : " + listVacaPendientes.get(i).getDiaspendientes());
            }
            totalDiasPendientes = BigInteger.valueOf(0);
            getTotalDiasPendientes();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVacacionesPEmpleado:totalDiasP");

        }
        if (tipoTabla == 2) {
            if (filtrarListaDisfrutadas == 0) {
                VWVacaPendientesEmpleados vacaPendiente = listVacaDisfrutadas.get(indexVDisfrutadas);
                listModificacionesTablaDisfrutadas.add(vacaPendiente);
            } else {
                int posicion = listVacaDisfrutadas.indexOf(filtrarListVacaDisfrutadas.get(indexVDisfrutadas));
                VWVacaPendientesEmpleados vacaPendiente = listVacaDisfrutadas.get(posicion);
                listModificacionesTablaDisfrutadas.add(vacaPendiente);
            }
        }
    }

    public void limpiarNuevoRegistro() {
        nuevaVacacion = new VWVacaPendientesEmpleados();
    }

    public void validarNuevoRegistroPendientes() {
        if (filtrarListaPendientes == 1) {
            vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        int h = 0;
        h = h + 1;
        BigInteger k;
        k = BigInteger.valueOf(h);
        nuevaVacacion.setRfvacacion(k);
        nuevaVacacion.setEmpleado(empleado.getSecuencia());
        listVacaPendientes.add(nuevaVacacion);
        listCrearTablaPendientes.add(nuevaVacacion);
        nuevaVacacion = new VWVacaPendientesEmpleados();
        indexVPendientes = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVacacionesPEmpleado");
    }

    public void duplicarRegistroTabla() {
        if (indexVPendientes >= 0) {
            duplicarVacacion = new VWVacaPendientesEmpleados();
            if (filtrarListaPendientes == 1) {
                int pos = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                duplicarVacacion.setEmpleado(empleado.getSecuencia());
                duplicarVacacion.setEstado(listVacaPendientes.get(pos).getEstado());
                duplicarVacacion.setDiaspendientes(listVacaPendientes.get(pos).getDiaspendientes());
                duplicarVacacion.setInicialcausacion(listVacaPendientes.get(pos).getInicialcausacion());
                duplicarVacacion.setFinalcausacion(listVacaPendientes.get(pos).getFinalcausacion());
            } else {
                duplicarVacacion.setEmpleado(empleado.getSecuencia());
                duplicarVacacion.setEstado(listVacaPendientes.get(indexVPendientes).getEstado());
                duplicarVacacion.setDiaspendientes(listVacaPendientes.get(indexVPendientes).getDiaspendientes());
                duplicarVacacion.setInicialcausacion(listVacaPendientes.get(indexVPendientes).getInicialcausacion());
                duplicarVacacion.setFinalcausacion(listVacaPendientes.get(indexVPendientes).getFinalcausacion());
            }
            //Dialogo Duplicar VacaPendiente
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVP");
            context.execute("DuplicarRegistroVP.show()");
            indexVPendientes = -1;
        }

    }

    public void validarDuplicadoVacaPendientes() {
        if (filtrarListaPendientes == 1) {
            vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        listCrearTablaPendientes.add(duplicarVacacion);
        listVacaPendientes.add(duplicarVacacion);
        duplicarVacacion = new VWVacaPendientesEmpleados();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVacacionesPEmpleado");
    }

    public void limpiarDuplicadoVacaPendiente(){
        duplicarVacacion = new VWVacaPendientesEmpleados();
    }

    public void eliminarRegistroTabla() {
        if (tipoTabla == 1) {
            if (filtrarListaPendientes == 0) {
                if (!listCrearTablaPendientes.isEmpty() && listCrearTablaPendientes.contains(listVacaPendientes.get(indexVPendientes))) {
                    int crearIndex = listCrearTablaPendientes.indexOf(listVacaPendientes.get(indexVPendientes));
                    listCrearTablaPendientes.remove(crearIndex);
                } else if (!listModificacionesTablaPendientes.isEmpty() && listModificacionesTablaPendientes.contains(listVacaPendientes.get(indexVPendientes))) {
                    int modIndex = listModificacionesTablaPendientes.indexOf(listVacaPendientes.get(indexVPendientes));
                    listModificacionesTablaPendientes.remove(modIndex);
                    listBorrarTablaPendientes.add(listVacaPendientes.get(indexVPendientes));
                } else {
                    VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(indexVPendientes);
                    listBorrarTablaPendientes.add(vacaPendiente);
                    listVacaPendientes.remove(indexVPendientes);
                }
            } else {
                if (!listCrearTablaPendientes.isEmpty() && listCrearTablaPendientes.contains(filtrarListVacaPendientes.get(indexVPendientes))) {
                    int crearIndex = listCrearTablaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    listCrearTablaPendientes.remove(crearIndex);
                } else if (!listModificacionesTablaPendientes.isEmpty() && listModificacionesTablaPendientes.contains(filtrarListVacaPendientes.get(indexVPendientes))) {
                    int modIndex = listModificacionesTablaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    listModificacionesTablaPendientes.remove(modIndex);
                    listBorrarTablaPendientes.add(filtrarListVacaPendientes.get(indexVPendientes));
                } else {
                    int posicion = listVacaPendientes.indexOf(filtrarListVacaPendientes.get(indexVPendientes));
                    VWVacaPendientesEmpleados vacaPendiente = listVacaPendientes.get(posicion);
                    listBorrarTablaPendientes.add(vacaPendiente);
                    listVacaPendientes.remove(posicion);
                    filtrarListVacaPendientes.remove(indexVPendientes);
                }
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVacacionesPEmpleado");
        }

    }

    public void cancelarModificaciones() {
        if (filtrarListaDisfrutadas == 1) {
            vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
        if (filtrarListaPendientes == 1) {
            vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        listBorrarTablaDisfrutadas.clear();
        listBorrarTablaPendientes.clear();
        listCrearTablaDisfrutadas.clear();
        listCrearTablaPendientes.clear();
        listModificacionesTablaDisfrutadas.clear();
        listModificacionesTablaPendientes.clear();
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        casillaDisfrutada = -1;
        casillaPendiente = -1;
        indexVDisfrutadas = -1;
        indexVPendientes = -1;
        tipoTabla = -1;
        totalDiasPendientes = BigInteger.valueOf(0);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVacacionesPEmpleado");
        context.update("form:datosVacacionesDEmpleado");
        context.update("form:datosVacacionesPEmpleado:totalDiasP");

    }

    public void activarCtrlF11() {
        if (indexVPendientes >= 0) {
            filtradoVacacionesPendientes();
        }
        if (indexVDisfrutadas >= 0) {
            filtradoVacacionesDisfrutadas();
        }
    }

    public void filtradoVacacionesPendientes() {
        if (banderaPendientes == 0) {
            vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("width: 100px");
            vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("width: 100px");
            vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 1;
        } else if (banderaPendientes == 1) {
            vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
            vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
            vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
            vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
    }

    public void filtradoVacacionesDisfrutadas() {
        if (banderaDisfrutadas == 0) {
            vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("width: 100px");
            vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("width: 100px");
            vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 1;
        } else if (banderaDisfrutadas == 1) {
            vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
    }

    public void posicionTabla(int tabla, int index, int casilla) {
        if (tabla == 1) {
            tipoTabla = 1;
            indexVPendientes = index;
            casillaPendiente = casilla;
            indexVDisfrutadas = -1;
            casillaDisfrutada = -1;
            if (banderaDisfrutadas == 1) {
                vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
                vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
                vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
                vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
                banderaDisfrutadas = 0;
                filtrarListVacaDisfrutadas = null;
                filtrarListaDisfrutadas = 0;
            }
        }
        if (tabla == 2) {
            tipoTabla = 2;
            indexVDisfrutadas = index;
            casillaDisfrutada = casilla;
            casillaPendiente = -1;
            indexVPendientes = -1;
            if (banderaPendientes == 1) {
                vacacionesDP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesDP");
                vacacionesDP.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaFinal");
                vacacionesFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vacacionesFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesPEmpleado:vacacionesFechaInicial");
                vacacionesFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVacacionesPEmpleado");
                banderaPendientes = 0;
                filtrarListVacaPendientes = null;
                filtrarListaPendientes = 0;
            }
        }
    }

    public void guardarGeneral() {
        if (banderaPendientes == 1) {
            vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaPendientes = 0;
            filtrarListVacaPendientes = null;
            filtrarListaPendientes = 0;
        }
        if (banderaDisfrutadas == 1) {
            vacacionesDPD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesDPD");
            vacacionesDPD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaInicialD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaInicialD");
            vacacionesFechaInicialD.setFilterStyle("display: none; visibility: hidden;");
            vacacionesFechaFinalD = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVacacionesDEmpleado:vacacionesFechaFinalD");
            vacacionesFechaFinalD.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVacacionesDEmpleado");
            banderaDisfrutadas = 0;
            filtrarListVacaDisfrutadas = null;
            filtrarListaDisfrutadas = 0;
        }
        guardarCambiosVPendientes();
        guardarCambiosVDisfrutadas();
    }

    public void guardarCambiosVPendientes() {
        if (!listBorrarTablaPendientes.isEmpty()) {
            for (int i = 0; i < listBorrarTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.borrarVacaPendiente(listBorrarTablaPendientes.get(i));
            }
        }
        if (!listCrearTablaPendientes.isEmpty()) {
            for (int i = 0; i < listCrearTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.crearVacaPendiente(listCrearTablaPendientes.get(i));
            }
        }
        if (!listModificacionesTablaPendientes.isEmpty()) {
            for (int i = 0; i < listModificacionesTablaPendientes.size(); i++) {
                administrarVWVacaPendientesEmpleados.editarVacaPendiente(listModificacionesTablaPendientes.get(i));
            }
        }
    }

    public void guardarCambiosVDisfrutadas() {
        if (!listBorrarTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listBorrarTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.borrarVacaPendiente(listBorrarTablaDisfrutadas.get(i));
            }
        }
        if (!listCrearTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listCrearTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.crearVacaPendiente(listCrearTablaDisfrutadas.get(i));
            }
        }
        if (!listModificacionesTablaDisfrutadas.isEmpty()) {
            for (int i = 0; i < listModificacionesTablaDisfrutadas.size(); i++) {
                administrarVWVacaPendientesEmpleados.editarVacaPendiente(listModificacionesTablaDisfrutadas.get(i));
            }
        }
    }

    public void salir() {
        cancelarModificaciones();
        listBorrarTablaDisfrutadas.clear();
        listBorrarTablaPendientes.clear();
        listCrearTablaDisfrutadas.clear();
        listCrearTablaPendientes.clear();
        listModificacionesTablaDisfrutadas.clear();
        listModificacionesTablaPendientes.clear();
        listVacaDisfrutadas = null;
        listVacaPendientes = null;
        casillaDisfrutada = -1;
        casillaPendiente = -1;
        indexVDisfrutadas = -1;
        indexVPendientes = -1;
        tipoTabla = -1;
        totalDiasPendientes = BigInteger.valueOf(0);
        diasProvisionados = BigDecimal.valueOf(0);
    }

    public void editarCelda() {
        if (tipoTabla == 1) {
            editarVacacion = listVacaPendientes.get(indexVPendientes);
            RequestContext context = RequestContext.getCurrentInstance();
            if (casillaPendiente == 1) {
                //Dialogo Dias Pendientes
                context.update("formularioDialogos:editarDiasPendientesP");
                context.execute("editarDiasPendientesP.show()");
            }
            if (casillaPendiente == 2) {
                //Dialogo Fecha Inicial
                context.update("formularioDialogos:editarFechaInicialP");
                context.execute("editarFechaInicialP.show()");
            }
            if (casillaPendiente == 3) {
                //Dialogo Fecha Final
                context.update("formularioDialogos:editarFechaFinalP");
                context.execute("editarFechaFinalP.show()");
            }
        }
        if (tipoTabla == 2) {
            editarVacacion = listVacaDisfrutadas.get(indexVDisfrutadas);
            RequestContext context = RequestContext.getCurrentInstance();
            if (casillaDisfrutada == 1) {
                //Dialogo Dias Pendientes
                context.update("formularioDialogos:editarDiasPendientesD");
                context.execute("editarDiasPendientesD.show()");
            }
            if (casillaDisfrutada == 2) {
                //Dialogo Fecha Inicial
                context.update("formularioDialogos:editarFechaInicialD");
                context.execute("editarFechaInicialD.show()");
            }
            if (casillaDisfrutada == 3) {
                //Dialogo Fecha Final
                context.update("formularioDialogos:editarFechaFinalD");
                context.execute("editarFechaFinalD.show()");
            }
        }
    }

    public String exportXML() {
        if (indexVPendientes >= 0) {
            nombreTabla = ":formExportarVP:datosVPEmpleadoExportar";
            nombreXML = "VacacionesPendientesXML";
        }
        if (indexVDisfrutadas >= 0) {
            nombreTabla = ":formExportarVD:datosVDEmpleadoExportar";
            nombreXML = "VacacionesDisfrutadasXML";
        }
        return nombreTabla;
    }

    public void validarExportPDF() throws IOException {
        if (indexVPendientes >= 0) {
            exportPDFVP();
        }
        if (indexVDisfrutadas >= 0) {
            exportPDFVD();
        }
    }

    public void exportPDFVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VacacionesPendientesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVPendientes = -1;
    }

    public void exportPDFVD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVD:datosVDEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VacacionesDisfrutadasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVDisfrutadas = -1;
    }

    public void verificarExportXLS() throws IOException {
        if (indexVDisfrutadas >= 0) {
            exportXLSVP();
        }
        if (indexVPendientes >= 0) {
            exportXLSVD();
        }
    }

    public void exportXLSVP() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVP:datosVPEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VacacionesPendientesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVPendientes = -1;
    }

    public void exportXLSVD() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVD:datosVDEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VacacionesDisfrutadasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVDisfrutadas = -1;
    }

    public void eventoFiltrar() {
        if (indexVDisfrutadas >= 0) {
            if (filtrarListaDisfrutadas == 0) {
                filtrarListaDisfrutadas = 1;
            }
        }
        if (indexVPendientes >= 0) {
            if (filtrarListaPendientes == 0) {
                filtrarListaPendientes = 1;
            }
        }
    }

    public List<VWVacaPendientesEmpleados> getFiltrarListVacaPendientes() {
        return filtrarListVacaPendientes;
    }

    public void setFiltrarListVacaPendientes(List<VWVacaPendientesEmpleados> filtrarListVacaPendientes) {
        this.filtrarListVacaPendientes = filtrarListVacaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getFiltrarListVacaDisfrutadas() {
        return filtrarListVacaDisfrutadas;
    }

    public void setFiltrarListVacaDisfrutadas(List<VWVacaPendientesEmpleados> filtrarListVacaDisfrutadas) {
        this.filtrarListVacaDisfrutadas = filtrarListVacaDisfrutadas;
    }

    public VWVacaPendientesEmpleados getEditarVacacion() {
        return editarVacacion;
    }

    public void setEditarVacacion(VWVacaPendientesEmpleados editarVacaPendiente) {
        this.editarVacacion = editarVacaPendiente;
    }

    public List<VWVacaPendientesEmpleados> getListModificacionesTablaPendientes() {
        return listModificacionesTablaPendientes;
    }

    public void setListModificacionesTablaPendientes(List<VWVacaPendientesEmpleados> listModificacionesTablaPendientes) {
        this.listModificacionesTablaPendientes = listModificacionesTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListModificacionesTablaDisfrutadas() {
        return listModificacionesTablaDisfrutadas;
    }

    public void setListModificacionesTablaDisfrutadas(List<VWVacaPendientesEmpleados> listModificacionesTablaDisfrutadas) {
        this.listModificacionesTablaDisfrutadas = listModificacionesTablaDisfrutadas;
    }

    public List<VWVacaPendientesEmpleados> getListCrearTablaPendientes() {
        return listCrearTablaPendientes;
    }

    public void setListCrearTablaPendientes(List<VWVacaPendientesEmpleados> listCrearTablaPendientes) {
        this.listCrearTablaPendientes = listCrearTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListCrearTablaDisfrutadas() {
        return listCrearTablaDisfrutadas;
    }

    public void setListCrearTablaDisfrutadas(List<VWVacaPendientesEmpleados> listCrearTablaDisfrutadas) {
        this.listCrearTablaDisfrutadas = listCrearTablaDisfrutadas;
    }

    public List<VWVacaPendientesEmpleados> getListBorrarTablaPendientes() {
        return listBorrarTablaPendientes;
    }

    public void setListBorrarTablaPendientes(List<VWVacaPendientesEmpleados> listBorrarTablaPendientes) {
        this.listBorrarTablaPendientes = listBorrarTablaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListBorrarTablaDisfrutadas() {
        return listBorrarTablaDisfrutadas;
    }

    public void setListBorrarTablaDisfrutadas(List<VWVacaPendientesEmpleados> listBorrarTablaDisfrutadas) {
        this.listBorrarTablaDisfrutadas = listBorrarTablaDisfrutadas;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public BigInteger getTotalDiasPendientes() {
        if (totalDiasPendientes.compareTo(BigInteger.valueOf(0)) == 0) {
            totalDiasPendientes = BigInteger.valueOf(0);
            if (!listVacaPendientes.isEmpty()) {
                for (int i = 0; i < listVacaPendientes.size(); i++) {
                    totalDiasPendientes = totalDiasPendientes.add(listVacaPendientes.get(i).getDiaspendientes());
                }
            }
        }
        return totalDiasPendientes;
    }

    public void setTotalDiasPendientes(BigInteger totalDiasPendientes) {
        this.totalDiasPendientes = totalDiasPendientes;
    }

    public Empleados getEmpleado() {
        empleado = administrarVWVacaPendientesEmpleados.obtenerEmpleado(secuencia);
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<VWVacaPendientesEmpleados> getListVacaPendientes() {
        try {
            if (listVacaPendientes == null) {
                listVacaPendientes = administrarVWVacaPendientesEmpleados.vacaPendientesPendientes(empleado);
            }
            return listVacaPendientes;
        } catch (Exception e) {
            System.out.println("Error getListVacaPendientes : " + e.toString());
            return null;
        }
    }

    public void setListVacaPendientes(List<VWVacaPendientesEmpleados> listVacaPendientes) {
        this.listVacaPendientes = listVacaPendientes;
    }

    public List<VWVacaPendientesEmpleados> getListVacaDisfrutadas() {
        try {
            if (listVacaDisfrutadas == null) {
                listVacaDisfrutadas = administrarVWVacaPendientesEmpleados.vacaPendientesDisfrutadas(empleado);
            }
            return listVacaDisfrutadas;
        } catch (Exception e) {
            System.out.println("Error getListVacaDisfrutadas : " + e.toString());
            return null;
        }
    }

    public void setListVacaDisfrutadas(List<VWVacaPendientesEmpleados> listVacaDisfrutadas) {
        this.listVacaDisfrutadas = listVacaDisfrutadas;
    }

    public VWVacaPendientesEmpleados getNuevaVacacion() {
        return nuevaVacacion;
    }

    public void setNuevaVacacion(VWVacaPendientesEmpleados nuevaVacacion) {
        this.nuevaVacacion = nuevaVacacion;
    }

    public VWVacaPendientesEmpleados getDuplicarVacacion() {
        return duplicarVacacion;
    }

    public void setDuplicarVacacion(VWVacaPendientesEmpleados duplicarVacacion) {
        this.duplicarVacacion = duplicarVacacion;
    }

    public BigDecimal getDiasProvisionados() {
        try {
            diasProvisionados = administrarVWVacaPendientesEmpleados.diasProvisionadosEmpleado(empleado);
            return diasProvisionados;
        } catch (Exception e) {
            System.out.println("Error getDiasProvisionados : " + e.toString());
            return null;
        }
    }

    public void setDiasProvisionados(BigDecimal diasProvisionados) {
        this.diasProvisionados = diasProvisionados;
    }
}

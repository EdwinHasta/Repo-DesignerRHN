/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VWAcumulados;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplAcumuladosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
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
public class ControlEmplAcumulados implements Serializable {

    @EJB
    AdministrarEmplAcumuladosInterface administrarEmplAcumulados;

    private BigInteger secuenciaEmpleado;
    private Empleados empleadoSeleccionado;
//valores tablas
    private List<VWAcumulados> listVWAcumuladosPorEmpleado;
    private List<VWAcumulados> filtrarVWAcumuladosPorEmpleado;
    private VWAcumulados nuevaEmplAcumulados;
    private VWAcumulados editarVWAcumuladosPorEmpleado;
    private VWAcumulados acumuladosPorEmpleadoSeleccionado;
//otros
    private int tipoLista, bandera, index, cualCelda;
    //revisarcambios
    private BigInteger revisarConceptoCodigo;
    //
    private Column conceptoCodigo, conceptoDescripcion, fechaDesde, fechaPago, unidades,
            valor, saldo, tipo, corteProceso, tercero, formula, debito, centroCostoDebito,
            credito, centroCostoCredito, ultimaModificacion, observaciones, motivoNovedad;
    private BigInteger secRegistro;
    private int tamano;
    public String altoTabla;
    public String infoRegistro;

    public ControlEmplAcumulados() {
        empleadoSeleccionado = null;
        secuenciaEmpleado = BigInteger.valueOf(10664356);
        listVWAcumuladosPorEmpleado = null;
        editarVWAcumuladosPorEmpleado = new VWAcumulados();
        secRegistro = null;
        altoTabla = "310";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplAcumulados.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        RequestContext context = RequestContext.getCurrentInstance();

        if (sec == null) {
            System.out.println("ControlVigenciasFormasPagos.recibirEmpleado");
            System.out.println("La secuencia pasada como parametro es null: " + sec.toString());
        }
        empleadoSeleccionado = null;
        secuenciaEmpleado = sec;
        getListVWAcumuladosPorEmpleado();
        if (listVWAcumuladosPorEmpleado != null && !listVWAcumuladosPorEmpleado.isEmpty()) {
            System.out.println("Entra al primer IF");
            if (listVWAcumuladosPorEmpleado.size() == 1) {
                //INFORMACION REGISTRO
                acumuladosPorEmpleadoSeleccionado = listVWAcumuladosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (listVWAcumuladosPorEmpleado.size() > 1) {
                System.out.println("Else If");
                //INFORMACION REGISTRO
                acumuladosPorEmpleadoSeleccionado = listVWAcumuladosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + listVWAcumuladosPorEmpleado.size();
            }

        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        context.update("form:informacionRegistro");
    }

    public void eventoFiltrar() {

        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de Registros: " + filtrarVWAcumuladosPorEmpleado.size();
        context.update("form:informacionRegistro");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "286";
            conceptoCodigo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoCodigo");
            conceptoCodigo.setFilterStyle("width: 90px");
            conceptoDescripcion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoDescripcion");
            conceptoDescripcion.setFilterStyle("width: 280px");
            fechaDesde = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaDesde");
            fechaDesde.setFilterStyle("width: 50px");
            fechaPago = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaPago");
            fechaPago.setFilterStyle("width: 50px");
            unidades = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:unidades");
            unidades.setFilterStyle("width: 40px");
            valor = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:valor");
            valor.setFilterStyle("width: 55px");
            saldo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:saldo");
            saldo.setFilterStyle("width: 50px");
            tipo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tipo");
            tipo.setFilterStyle("width: 50px");
            corteProceso = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:corteProceso");
            corteProceso.setFilterStyle("width: 130px");
            tercero = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tercero");
            tercero.setFilterStyle("width: 160px");
            formula = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:formula");
            formula.setFilterStyle("width: 150px");
            debito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:debito");
            debito.setFilterStyle("width: 55px");
            centroCostoDebito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoDebito");
            centroCostoDebito.setFilterStyle("width: 100px");
            credito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:credito");
            credito.setFilterStyle("width: 55px");
            centroCostoCredito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoCredito");
            centroCostoCredito.setFilterStyle("width: 100px");
            ultimaModificacion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:ultimaModificacion");
            ultimaModificacion.setFilterStyle("width: 80px");
            observaciones = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:observaciones");
            observaciones.setFilterStyle("width: 80px");
            motivoNovedad = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:motivoNovedad");
            motivoNovedad.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosEmplAcumulados");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "310";
            System.out.println("Desactivar");
            conceptoCodigo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoCodigo");
            conceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            conceptoDescripcion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoDescripcion");
            conceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            fechaDesde = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaDesde");
            fechaDesde.setFilterStyle("display: none; visibility: hidden;");
            fechaPago = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaPago");
            fechaPago.setFilterStyle("display: none; visibility: hidden;");
            unidades = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:unidades");
            unidades.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            saldo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:saldo");
            saldo.setFilterStyle("display: none; visibility: hidden;");
            tipo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tipo");
            tipo.setFilterStyle("display: none; visibility: hidden;");
            corteProceso = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:corteProceso");
            corteProceso.setFilterStyle("display: none; visibility: hidden;");
            tercero = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tercero");
            tercero.setFilterStyle("display: none; visibility: hidden;");
            formula = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:formula");
            formula.setFilterStyle("display: none; visibility: hidden;");
            debito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:debito");
            debito.setFilterStyle("display: none; visibility: hidden;");
            centroCostoDebito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoDebito");
            centroCostoDebito.setFilterStyle("display: none; visibility: hidden;");
            credito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:credito");
            credito.setFilterStyle("display: none; visibility: hidden;");
            centroCostoCredito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoCredito");
            centroCostoCredito.setFilterStyle("display: none; visibility: hidden;");
            ultimaModificacion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:ultimaModificacion");
            ultimaModificacion.setFilterStyle("display: none; visibility: hidden;");
            observaciones = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:observaciones");
            observaciones.setFilterStyle("display: none; visibility: hidden;");
            motivoNovedad = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:motivoNovedad");
            motivoNovedad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEmplAcumulados");

            bandera = 0;
            filtrarVWAcumuladosPorEmpleado = null;
            tipoLista = 0;
        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVWAEmpleadoExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EmplAcumuladosPDF", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVWAEmpleadoExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EmplAcumuladosXLS", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void limpiarNuevaVigenciaFormpasPagos() {
        System.out.println("LimpiarNuevaVWAcumulados");
        nuevaEmplAcumulados = new VWAcumulados();

        index = -1;
        secRegistro = null;
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVWAcumuladosPorEmpleado = listVWAcumuladosPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVWAcumuladosPorEmpleado = filtrarVWAcumuladosPorEmpleado.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                System.err.println("EditarCelda cualcelda = " + cualCelda);
                context.update("formularioDialogos:editConceptoCodigo");
                context.execute("editConceptoCodigo.show()");
                cualCelda = -1;
            }
            if (cualCelda == 1) {
                context.update("formularioDialogos:editConceptoDescripcion");
                context.execute("editConceptoDescripcion.show()");
                cualCelda = -1;
            }
            if (cualCelda == 2) {
                context.update("formularioDialogos:editFechaDesde");
                context.execute("editFechaDesde.show()");
                cualCelda = -1;
            }
            if (cualCelda == 3) {
                context.update("formularioDialogos:editFechaPago");
                context.execute("editFechaPago.show()");
                cualCelda = -1;
            }
            if (cualCelda == 4) {
                context.update("formularioDialogos:editUnidad");
                context.execute("editUnidad.show()");
                cualCelda = -1;
            }
            if (cualCelda == 5) {
                context.update("formularioDialogos:editValor");
                context.execute("editValor.show()");
                cualCelda = -1;
            }
            if (cualCelda == 6) {
                context.update("formularioDialogos:editSaldo");
                context.execute("editSaldo.show()");
                cualCelda = -1;
            }
            if (cualCelda == 7) {
                context.update("formularioDialogos:editTipo");
                context.execute("editTipo.show()");
                cualCelda = -1;
            }
            if (cualCelda == 8) {
                context.update("formularioDialogos:editProceso");
                context.execute("editProceso.show()");
                cualCelda = -1;
            }
            if (cualCelda == 9) {
                context.update("formularioDialogos:editNitNombre");
                context.execute("editNitNombre.show()");
                cualCelda = -1;
            }
            if (cualCelda == 10) {
                context.update("formularioDialogos:editFormula");
                context.execute("editFormula.show()");
                cualCelda = -1;
            }
            if (cualCelda == 11) {
                context.update("formularioDialogos:editDebito");
                context.execute("editDebito.show()");
                cualCelda = -1;
            }
            if (cualCelda == 12) {
                context.update("formularioDialogos:editCentroCostoDebito");
                context.execute("editCentroCostoDebito.show()");
                cualCelda = -1;
            }
            if (cualCelda == 13) {
                context.update("formularioDialogos:editCredito");
                context.execute("editCredito.show()");
                cualCelda = -1;
            }
            if (cualCelda == 13) {
                context.update("formularioDialogos:editCredito");
                context.execute("editCredito.show()");
                cualCelda = -1;
            }
            if (cualCelda == 14) {
                context.update("formularioDialogos:editCentroCostoCredito");
                context.execute("editCentroCostoCredito.show()");
                cualCelda = -1;
            }
            if (cualCelda == 15) {
                context.update("formularioDialogos:editFechaModificacion");
                context.execute("editFechaModificacion.show()");
                cualCelda = -1;
            }
            if (cualCelda == 16) {
                context.update("formularioDialogos:editObservaciones");
                context.execute("editObservaciones.show()");
                cualCelda = -1;
            }
            if (cualCelda == 17) {
                context.update("formularioDialogos:editMotivoNovedad");
                context.execute("editMotivoNovedad.show()");
                cualCelda = -1;
            }
            if (cualCelda == 18) {
                context.update("formularioDialogos:editDetallesConcepto");
                context.execute("editDetallesConcepto.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {

        index = indice;
        cualCelda = celda;
        secRegistro = listVWAcumuladosPorEmpleado.get(index).getSecuencia();
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    public void revisarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("repeticiones.show()");
        listVWAcumuladosPorEmpleado = null;
        context = RequestContext.getCurrentInstance();
        context.update("form:datosEmplAcumulados");
    }
//------------------------------------------------------------------------------

    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            //CERRAR FILTRADO
            System.out.println("CancelarModificacion");
            conceptoCodigo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoCodigo");
            conceptoCodigo.setFilterStyle("display: none; visibility: hidden;");
            conceptoDescripcion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:conceptoDescripcion");
            conceptoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            fechaDesde = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaDesde");
            fechaDesde.setFilterStyle("display: none; visibility: hidden;");
            fechaPago = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:fechaPago");
            fechaPago.setFilterStyle("display: none; visibility: hidden;");
            unidades = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:unidades");
            unidades.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            saldo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:saldo");
            saldo.setFilterStyle("display: none; visibility: hidden;");
            tipo = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tipo");
            tipo.setFilterStyle("display: none; visibility: hidden;");
            corteProceso = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:corteProceso");
            corteProceso.setFilterStyle("display: none; visibility: hidden;");
            tercero = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:tercero");
            tercero.setFilterStyle("display: none; visibility: hidden;");
            formula = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:formula");
            formula.setFilterStyle("display: none; visibility: hidden;");
            debito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:debito");
            debito.setFilterStyle("display: none; visibility: hidden;");
            centroCostoDebito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoDebito");
            centroCostoDebito.setFilterStyle("display: none; visibility: hidden;");
            credito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:credito");
            credito.setFilterStyle("display: none; visibility: hidden;");
            centroCostoCredito = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:centroCostoCredito");
            centroCostoCredito.setFilterStyle("display: none; visibility: hidden;");
            ultimaModificacion = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:ultimaModificacion");
            ultimaModificacion.setFilterStyle("display: none; visibility: hidden;");
            observaciones = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:observaciones");
            observaciones.setFilterStyle("display: none; visibility: hidden;");
            motivoNovedad = (Column) c.getViewRoot().findComponent("form:datosEmplAcumulados:motivoNovedad");
            motivoNovedad.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "310";
            RequestContext.getCurrentInstance().update("form:datosEmplAcumulados");
            bandera = 0;
            filtrarVWAcumuladosPorEmpleado = null;
            tipoLista = 0;
        }

        index = -1;
        secRegistro = null;
        listVWAcumuladosPorEmpleado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEmplAcumulados");
    }

    //-----------------------------------------------------------------------------
    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarEmplAcumulados.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<VWAcumulados> getListVWAcumuladosPorEmpleado() {
        if (listVWAcumuladosPorEmpleado == null) {
            listVWAcumuladosPorEmpleado = administrarEmplAcumulados.consultarVWAcumuladosEmpleado(secuenciaEmpleado);
            System.out.println("Lista: " + listVWAcumuladosPorEmpleado.size());

        }
        return listVWAcumuladosPorEmpleado;
    }

    public void setListVWAcumuladosPorEmpleado(List<VWAcumulados> listVWAcumuladosPorEmpleado) {
        this.listVWAcumuladosPorEmpleado = listVWAcumuladosPorEmpleado;
    }

    public List<VWAcumulados> getFiltrarVWAcumuladosPorEmpleado() {
        return filtrarVWAcumuladosPorEmpleado;
    }

    public void setFiltrarVWAcumuladosPorEmpleado(List<VWAcumulados> filtrarVWAcumuladosPorEmpleado) {
        this.filtrarVWAcumuladosPorEmpleado = filtrarVWAcumuladosPorEmpleado;
    }

    public VWAcumulados getEditarVWAcumuladosPorEmpleado() {
        return editarVWAcumuladosPorEmpleado;
    }

    public void setEditarVWAcumuladosPorEmpleado(VWAcumulados editarVWAcumuladosPorEmpleado) {
        this.editarVWAcumuladosPorEmpleado = editarVWAcumuladosPorEmpleado;
    }

    public VWAcumulados getAcumuladosPorEmpleadoSeleccionado() {
        return acumuladosPorEmpleadoSeleccionado;
    }

    public void setAcumuladosPorEmpleadoSeleccionado(VWAcumulados acumuladosPorEmpleadoSeleccionado) {
        this.acumuladosPorEmpleadoSeleccionado = acumuladosPorEmpleadoSeleccionado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

}

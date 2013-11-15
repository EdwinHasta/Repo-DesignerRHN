/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Ibcs;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarIBCSInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlEmplIbcs implements Serializable {

    @EJB
    AdministrarIBCSInterface administrarIBCS;
    private List<Ibcs> listIbcsPorEmpleado;
    private List<Ibcs> filtrarIbcsPorEmpleado;
    private Ibcs editarIbcsPorEmpleado;
    private Ibcs nuevaIbcsPorEmpleado;
    private Ibcs dialogoIbcsPorEmpleado;
    private BigInteger secuenciaEmpleado;
    private Empleados empleadoSeleccionado;
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    private BigInteger secRegistro;
    private Column fechaFinal, fechaInicial, valor;
//*****************************************************************
    //prueba
    private Ibcs selecionIbcs;

    public Ibcs getSelecionIbcs() {
        return selecionIbcs;
    }

    public void setSelecionIbcs(Ibcs selecionIbcs) {
        this.selecionIbcs = selecionIbcs;
    }

//*****************************************************************
    public ControlEmplIbcs() {
        listIbcsPorEmpleado = null;
        secuenciaEmpleado = BigInteger.valueOf(10664356);
        editarIbcsPorEmpleado = new Ibcs();
        empleadoSeleccionado = null;
        selecionIbcs = new Ibcs();

    }

    public void recibirEmpleado(BigInteger sec) {
        if (sec == null) {
            System.out.println("ControlVigenciasFormasPagos.recibirEmpleado");
            System.out.println("La secuencia pasada como parametro es null: " + sec.toString());
        }
        empleadoSeleccionado = null;
        secuenciaEmpleado = sec;
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            System.out.println("CancelarModificacion");
            fechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaInicial");
            fechaInicial.setFilterStyle("display: none; visibility: hidden;");
            fechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaFinal");
            fechaFinal.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIbcs");
            bandera = 0;
            filtrarIbcsPorEmpleado = null;
            tipoLista = 0;
        }



        index = -1;
        secRegistro = null;
        listIbcsPorEmpleado = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosIbcs");
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlVigenciasAfiliaciones.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlVigenciasAfiliaciones eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void revisarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("repeticiones.show()");
        listIbcsPorEmpleado = null;
        context = RequestContext.getCurrentInstance();
        context.update("form:datosEmplAcumulados");
    }

    public void cambiarIndice(int indice, int celda) {

        index = indice;
        cualCelda = celda;
//        secRegistro = listIbcsPorEmpleado.get(index).getSecuencia();
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    public void limpiarNuevaIbcs() {
        nuevaIbcsPorEmpleado = new Ibcs();

        index = -1;
        secRegistro = null;
    }

    public void activarCtrlF11() {
        if (bandera == 0) {


            fechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaInicial");
            fechaInicial.setFilterStyle("width: 50px");
            fechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaFinal");
            fechaFinal.setFilterStyle("width: 50px");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:valor");
            valor.setFilterStyle("width: 40px");
            RequestContext.getCurrentInstance().update("form:datosIbcs");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");

            fechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaInicial");
            fechaInicial.setFilterStyle("display: none; visibility: hidden;");
            fechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:fechaFinal");
            fechaFinal.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIbcs:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIbcs");
            bandera = 0;
            filtrarIbcsPorEmpleado = null;
            tipoLista = 0;
        }
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarIbcsPorEmpleado = listIbcsPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarIbcsPorEmpleado = listIbcsPorEmpleado.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                System.err.println("EditarCelda cualcelda = " + cualCelda);
                context.update("formularioDialogos:editFechaInicial");
                context.execute("editFechaInicial.show()");
                cualCelda = -1;
            }
            if (cualCelda == 1) {
                context.update("formularioDialogos:editFechaFinal");
                context.execute("editFechaFinal.show()");
                cualCelda = -1;
            }
            if (cualCelda == 2) {
                context.update("formularioDialogos:editValor");
                context.execute("editValor.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void dialogo() {

         if (index >= 0) {
          if (tipoLista == 0) {
            dialogoIbcsPorEmpleado = listIbcsPorEmpleado.get(index);
          }
         if (tipoLista == 1) {
            dialogoIbcsPorEmpleado = filtrarIbcsPorEmpleado.get(index);
          }

        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Entro a dialogo");
        //  System.err.println("EditarCelda cualcelda = " + cualCelda);
        context.update("formularioDialogos:dialogoIbcs");
        context.execute("dialogoIbcs.show()");


         }
         index = -1;
         secRegistro = null;
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIbcsExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EmplIbcsPDF", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosIbcsExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EmplIbcsXLS", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //datosIbcsExportar
    //--------------------------------------------------------------------------
    public List<Ibcs> getListIbcsPorEmpleado() {
        if (listIbcsPorEmpleado == null) {
            listIbcsPorEmpleado = administrarIBCS.ibcsPorEmplelado(secuenciaEmpleado);
        }
        return listIbcsPorEmpleado;
    }

    public void setListIbcsPorEmpleado(List<Ibcs> listIbcsPorEmpleado) {
        this.listIbcsPorEmpleado = listIbcsPorEmpleado;
    }

    public Ibcs getEditarIbcsPorEmpleado() {
        return editarIbcsPorEmpleado;
    }

    public void setEditarIbcsPorEmpleado(Ibcs editarIbcsPorEmpleado) {
        this.editarIbcsPorEmpleado = editarIbcsPorEmpleado;
    }

    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarIBCS.buscarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Ibcs> getFiltrarIbcsPorEmpleado() {
        return filtrarIbcsPorEmpleado;
    }

    public void setFiltrarIbcsPorEmpleado(List<Ibcs> filtrarIbcsPorEmpleado) {
        this.filtrarIbcsPorEmpleado = filtrarIbcsPorEmpleado;
    }

    public Ibcs getDialogoIbcsPorEmpleado() {
        return dialogoIbcsPorEmpleado;
    }

    public void setDialogoIbcsPorEmpleado(Ibcs dialogoIbcsPorEmpleado) {
        this.dialogoIbcsPorEmpleado = dialogoIbcsPorEmpleado;
    }
}
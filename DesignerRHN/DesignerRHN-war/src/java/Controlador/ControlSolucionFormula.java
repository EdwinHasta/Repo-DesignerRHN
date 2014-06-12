
package Controlador;

import Entidades.Empleados;
import Entidades.Novedades;
import Entidades.SolucionesFormulas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSolucionesFormulasInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
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
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlSolucionFormula implements Serializable {

    @EJB
    AdministrarSolucionesFormulasInterface administrarSolucionesFormulas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<SolucionesFormulas> listaSolucionesFormulas;
    private List<SolucionesFormulas> filtrarListaSolucionesFormulas;
    private Empleados empleado;
    private Novedades novedad;
    private int bandera;
    private Column fechaHasta, concepto, valor, saldo, fechaPago, proceso,formula;
    private boolean aceptar;
    private int index;
    private SolucionesFormulas editarSolucionFormula;
    private int cualCelda, tipoLista;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private String informacionEmpleadoNovedad;
    private String algoTabla;

    public ControlSolucionFormula() {
        algoTabla = "200";
        empleado = new Empleados();
        novedad = new Novedades();
        backUpSecRegistro = null;
        listaSolucionesFormulas = null;
        aceptar = true;
        editarSolucionFormula = new SolucionesFormulas();
        cualCelda = -1;
        tipoLista = 0;
        secRegistro = null;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarSolucionesFormulas.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void recibirParametros(BigInteger codEmpleado, BigInteger secNovedad) {
        empleado = administrarSolucionesFormulas.empleadoActual(codEmpleado);
        listaSolucionesFormulas = administrarSolucionesFormulas.listaSolucionesFormulaParaEmpleadoYNovedad(empleado.getSecuencia(), secNovedad);
        novedad = administrarSolucionesFormulas.novedadActual(secNovedad);
        if (novedad == null) {
            informacionEmpleadoNovedad = empleado.getPersona().getNombreCompleto().toUpperCase() + " NOVEDAD POR VALOR DE : $ 0";
        } else {
            informacionEmpleadoNovedad = empleado.getPersona().getNombreCompleto().toUpperCase() + " NOVEDAD POR VALOR DE : $ " + novedad.getValortotal().toString();
        }
        int tamanio = informacionEmpleadoNovedad.length();
        if(tamanio>60){
            String aux = informacionEmpleadoNovedad;
            informacionEmpleadoNovedad="";
            for(int i = 0;i<56;i++){
                informacionEmpleadoNovedad = informacionEmpleadoNovedad+aux.charAt(i)+"";
            }
            informacionEmpleadoNovedad = informacionEmpleadoNovedad + "...";
        }
        
    }

    public void posicionTabla() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String type = map.get("t"); // type attribute of node
        String cass = map.get("n"); // type attribute of node
        int ind = Integer.parseInt(type);
        int cassi = Integer.parseInt(cass);
        cambiarIndice(ind, cassi);
    }

    public void cambiarIndice(int indice, int celda) {
        index = indice;
        cualCelda = celda;
        if (tipoLista == 0) {
            secRegistro = listaSolucionesFormulas.get(index).getSecuencia();
        }
        if (tipoLista == 1) {
            secRegistro = filtrarListaSolucionesFormulas.get(index).getSecuencia();
        }
        System.out.println("Index : " + index + "// Celda: " + cualCelda);
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarSolucionFormula = listaSolucionesFormulas.get(index);
            }
            if (tipoLista == 1) {
                editarSolucionFormula = filtrarListaSolucionesFormulas.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaHastaD");
                context.execute("editarFechaHastaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarConceptoD");
                context.execute("editarConceptoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarValorD");
                context.execute("editarValorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarSaldoD");
                context.execute("editarSaldoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarFechaPagoD");
                context.execute("editarFechaPagoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarProcesoD");
                context.execute("editarProcesoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarFormulaD");
                context.execute("editarFormulaD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            algoTabla = "178";
            fechaHasta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaHasta");
            fechaHasta.setFilterStyle("width: 60px");
            concepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:concepto");
            concepto.setFilterStyle("width: 60px");
            fechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaPago");
            fechaPago.setFilterStyle("width: 60px");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:valor");
            valor.setFilterStyle("width: 60px");
            saldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:saldo");
            saldo.setFilterStyle("width: 60px");
            proceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:proceso");
            proceso.setFilterStyle("width: 60px");
            formula = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:formula");
            formula.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosSolucionFormula");
            bandera = 1;
        } else if (bandera == 1) {
            algoTabla = "200";
            fechaHasta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaHasta");
            fechaHasta.setFilterStyle("display: none; visibility: hidden;");
            concepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:concepto");
            concepto.setFilterStyle("display: none; visibility: hidden;");
            fechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaPago");
            fechaPago.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            saldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:saldo");
            saldo.setFilterStyle("display: none; visibility: hidden;");
            proceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:proceso");
            proceso.setFilterStyle("display: none; visibility: hidden;");
            formula = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:formula");
            formula.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSolucionFormula");
            bandera = 0;
            filtrarListaSolucionesFormulas = null;
            tipoLista = 0;
        }
        index = -1;
        secRegistro = null;
    }

    public void salir() {
        if (bandera == 1) {
            algoTabla = "200";
            fechaHasta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaHasta");
            fechaHasta.setFilterStyle("display: none; visibility: hidden;");
            concepto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:concepto");
            concepto.setFilterStyle("display: none; visibility: hidden;");
            fechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:fechaPago");
            fechaPago.setFilterStyle("display: none; visibility: hidden;");
            valor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:valor");
            valor.setFilterStyle("display: none; visibility: hidden;");
            saldo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:saldo");
            saldo.setFilterStyle("display: none; visibility: hidden;");
            proceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:proceso");
            proceso.setFilterStyle("display: none; visibility: hidden;");
            formula = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSolucionFormula:formula");
            formula.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSolucionFormula");
            bandera = 0;
            filtrarListaSolucionesFormulas = null;
            tipoLista = 0;
        }
        index = -1;
        secRegistro = null;
        listaSolucionesFormulas = null;
    }

    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSolucionFormulaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "SolucionesFormulas_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSolucionFormulaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "SolucionesFormulas_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaSolucionesFormulas != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "SOLUCIONESFORMULAS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("SOLUCIONESFORMULAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public List<SolucionesFormulas> getListaSolucionesFormulas() {
        try {
            if (listaSolucionesFormulas == null) {
                return listaSolucionesFormulas = administrarSolucionesFormulas.listaSolucionesFormulaParaEmpleadoYNovedad(empleado.getSecuencia(), novedad.getSecuencia());
            }
            return listaSolucionesFormulas;

        } catch (Exception e) {
            System.out.println("Error...!! getListaSolucionesFormulas : " + e.toString());
            return null;
        }
    }

    public void setListaSolucionesFormulas(List<SolucionesFormulas> setListaSolucionesFormulas) {
        this.listaSolucionesFormulas = setListaSolucionesFormulas;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public List<SolucionesFormulas> getFiltrarListaSolucionesFormulas() {
        return filtrarListaSolucionesFormulas;
    }

    public void setFiltrarListaSolucionesFormulas(List<SolucionesFormulas> setFiltrarListaSolucionesFormulas) {
        this.filtrarListaSolucionesFormulas = setFiltrarListaSolucionesFormulas;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public SolucionesFormulas getEditarSolucionFormula() {
        return editarSolucionFormula;
    }

    public void setEditarSolucionFormula(SolucionesFormulas setEditarSolucionFormula) {
        this.editarSolucionFormula = setEditarSolucionFormula;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger BackUpSecRegistro) {
        this.backUpSecRegistro = BackUpSecRegistro;
    }

    public Novedades getNovedad() {
        return novedad;
    }

    public void setNovedad(Novedades novedad) {
        this.novedad = novedad;
    }

    public String getInformacionEmpleadoNovedad() {
        return informacionEmpleadoNovedad;
    }

    public void setInformacionEmpleadoNovedad(String informacionEmpleadoNovedad) {
        this.informacionEmpleadoNovedad = informacionEmpleadoNovedad;
    }

    public String getAlgoTabla() {
        return algoTabla;
    }

    public void setAlgoTabla(String algoTabla) {
        this.algoTabla = algoTabla;
    }

}

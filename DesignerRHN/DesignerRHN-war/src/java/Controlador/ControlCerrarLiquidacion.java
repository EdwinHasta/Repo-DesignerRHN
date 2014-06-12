package Controlador;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarCerrarLiquidacionInterface;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author -Feliipe-
 */
@ManagedBean
@SessionScoped
public class ControlCerrarLiquidacion implements Serializable {

    @EJB
    AdministrarCerrarLiquidacionInterface administrarCerrarLiquidacion;
    
    private Integer totalEmpleadosParaLiquidar;
    private boolean permisoParaLiquidar;
    private String usuarioBD;
    private ParametrosEstructuras parametroEstructura;
    private List<Parametros> listaParametros;
    private List<Parametros> filtradoListaParametros;
    private String opcionLiquidacion;
    private SimpleDateFormat formatoFecha;

    public ControlCerrarLiquidacion() {
        totalEmpleadosParaLiquidar = 0;
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarCerrarLiquidacion.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void tipoLiquidacion(String tipoLiquidacion){
        opcionLiquidacion = tipoLiquidacion;
    }
    
    public void confirmarCierreLiquidacion() {
        Integer conteo = administrarCerrarLiquidacion.consultarConteoProcesoSN(parametroEstructura.getProceso().getSecuencia());
        if (conteo == totalEmpleadosParaLiquidar) {
            cerrarLiquidacion();
        } else {
            RequestContext.getCurrentInstance().execute("confirmarCerrarConteoFallo.show();");
        }
    }

    public void cerrarLiquidacion() {
        if (opcionLiquidacion.equals("AUTOMATICO")) {
            administrarCerrarLiquidacion.cerrarLiquidacionAutomatico();
        } else if (opcionLiquidacion.equals("NO AUTOMATICO")) {
            administrarCerrarLiquidacion.cerrarLiquidacionNoAutomatico();
        }
        FacesMessage msg = new FacesMessage("Información", "Liquidación cerrada con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().update("form:growl");
    }

    public void confirmarAbrirLiquidacion() {
        String fechaDesde = formatoFecha.format(parametroEstructura.getFechadesdecausado());
        String fechaHasta = formatoFecha.format(parametroEstructura.getFechahastacausado());
        Integer conteo = administrarCerrarLiquidacion.contarLiquidacionesCerradas(parametroEstructura.getProceso().getSecuencia(), fechaDesde, fechaHasta);
        RequestContext context = RequestContext.getCurrentInstance();
        if (conteo > 0) {
            context.execute("confirmarBorrarComprobante.show();");
        } else {
            context.execute("errorComprobante.show();");
        }
    }

    public void abrirLiquidacion() {
        String fechaDesde = formatoFecha.format(parametroEstructura.getFechadesdecausado());
        String fechaHasta = formatoFecha.format(parametroEstructura.getFechahastacausado());
        administrarCerrarLiquidacion.abrirLiquidacion(parametroEstructura.getProceso().getCodigo(), fechaDesde, fechaHasta);
        FacesMessage msg = new FacesMessage("Información", "Comprobante eliminado exitosamente.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().update("form:growl");
    }

    public void salir(){
        parametroEstructura = null;
        listaParametros = null;
    }
    //GETTER AND SETTER
    public Integer getTotalEmpleadosParaLiquidar() {
        totalEmpleadosParaLiquidar = administrarCerrarLiquidacion.contarEmpleadosParaLiquidar();
        return totalEmpleadosParaLiquidar;
    }

    public void setTotalEmpleadosParaLiquidar(Integer totalEmpleadosParaLiquidar) {
        this.totalEmpleadosParaLiquidar = totalEmpleadosParaLiquidar;
    }

    public boolean isPermisoParaLiquidar() {
        return permisoParaLiquidar;
    }

    public void setPermisoParaLiquidar(boolean permisoParaLiquidar) {
        this.permisoParaLiquidar = permisoParaLiquidar;
    }

    public String getUsuarioBD() {
        usuarioBD = administrarCerrarLiquidacion.consultarAliasUsuarioBD();
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarCerrarLiquidacion.consultarParametrosLiquidacion();
        }
        return parametroEstructura;
    }

    public void setParametroEstructura(ParametrosEstructuras parametroEstructura) {
        this.parametroEstructura = parametroEstructura;
    }

    public List<Parametros> getListaParametros() {
        if (listaParametros == null) {
            getUsuarioBD();
            listaParametros = administrarCerrarLiquidacion.consultarEmpleadosCerrarLiquidacion(usuarioBD);
        }
        return listaParametros;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public List<Parametros> getFiltradoListaParametros() {
        return filtradoListaParametros;
    }

    public void setFiltradoListaParametros(List<Parametros> filtradoListaParametros) {
        this.filtradoListaParametros = filtradoListaParametros;
    }
}

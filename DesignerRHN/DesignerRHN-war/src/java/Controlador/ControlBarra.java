package Controlador;

import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarBarraInterface;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlBarra implements Serializable {

    @EJB
    AdministrarBarraInterface administrarBarra;
    private Integer totalEmpleadosParaLiquidar;
    private Integer totalEmpleadosLiquidados;
    private boolean permisoParaLiquidar;
    private String usuarioBD;
    private Integer barra;
    private ParametrosEstructuras parametroEstructura;
    private boolean empezar;
    private boolean bandera;
    private String horaInicialLiquidacion, horaFinalLiquidacion;

    public ControlBarra() {
        totalEmpleadosParaLiquidar = 0;
        totalEmpleadosLiquidados = 0;
        barra = 0;
        empezar = false;
        bandera = true;
        horaInicialLiquidacion = "--:--:--";
        horaFinalLiquidacion = "--:--:--";
    }

    public void contarLiquidados() {
        totalEmpleadosLiquidados = administrarBarra.empleadosLiquidados();
        RequestContext.getCurrentInstance().update("form:empleadosLiquidados");
    }

    public void liquidar() {
        barra = null;
        empezar = true;
        usuarioBD = administrarBarra.usuarioBD();
        permisoParaLiquidar = administrarBarra.permisosLiquidar(usuarioBD);
        barra = null;
        if (permisoParaLiquidar == true) {
            administrarBarra.inicializarParametrosEstados();
            administrarBarra.liquidarNomina();
            Date horaInicio = new Date();
            System.out.println("Hora Inicio: " + horaInicio.getHours() + ":" + horaInicio.getMinutes() + ":" + horaInicio.getSeconds());
            /*while (!administrarBarra.estadoLiquidacion(usuarioBD).equals("FINALIZADO")) {
             System.out.println("Hola Pipelon");
             }*/
            horaInicialLiquidacion = horaInicio.getHours() + ":" + horaInicio.getMinutes() + ":" + horaInicio.getSeconds();
            RequestContext.getCurrentInstance().update("form:horaI");
        } else {
            System.out.println("Liquidar: " + permisoParaLiquidar);
        }
    }

    public void limpiarbarra() {
        barra = null;
        if (empezar == false) {
            empezar = true;
        } else if (empezar == true) {
            empezar = false;
        }
    }

    public void liquidacionCompleta() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mensaje", "Liquidación terminada con Éxito."));
        RequestContext.getCurrentInstance().update("form:barra");
        empezar = false;
        contarLiquidados();
        Date horaFinal = new Date();
        System.out.println("Hora Final: " + horaFinal.getHours() + ":" + horaFinal.getMinutes() + ":" + horaFinal.getSeconds());
        horaFinalLiquidacion = horaFinal.getHours() + ":" + horaFinal.getMinutes() + ":" + horaFinal.getSeconds();
        RequestContext.getCurrentInstance().update("form:horaF");
    }

    public void cancelarLiquidacion() {
        barra = null;
        bandera = true;
        empezar = false;
    }
    //GETTER AND SETTER

    public Integer getTotalEmpleadosParaLiquidar() {
        totalEmpleadosParaLiquidar = administrarBarra.empleadosParaLiquidar();
        return totalEmpleadosParaLiquidar;
    }

    public void setTotalEmpleadosParaLiquidar(Integer totalEmpleadosParaLiquidar) {
        this.totalEmpleadosParaLiquidar = totalEmpleadosParaLiquidar;
    }

    public Integer getTotalEmpleadosLiquidados() {
        return totalEmpleadosLiquidados;
    }

    public void setTotalEmpleadosLiquidados(Integer totalEmpleadosLiquidados) {
        this.totalEmpleadosLiquidados = totalEmpleadosLiquidados;
    }

    public Integer getBarra() {
        if (empezar == true) {
            if (barra == null) {
                barra = 0;
            } else {
                barra = administrarBarra.progresoLiquidacion(totalEmpleadosParaLiquidar);
                if (barra >= 100) {
                    barra = 100;
                    bandera = false;
                }
                if (bandera == true) {
                    RequestContext.getCurrentInstance().update("form:barra");
                    contarLiquidados();
                }
            }
        }
        return barra;
    }

    public void setBarra(Integer barra) {
        this.barra = barra;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarBarra.parametrosLiquidacion();
        }
        return parametroEstructura;
    }

    public String getHoraInicialLiquidacion() {
        return horaInicialLiquidacion;
    }

    public String getHoraFinalLiquidacion() {
        return horaFinalLiquidacion;
    }
}

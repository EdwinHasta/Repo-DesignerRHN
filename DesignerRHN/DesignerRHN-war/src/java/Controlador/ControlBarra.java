package Controlador;

import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarBarraInterface;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
    private int barra;
    private ParametrosEstructuras parametroEstructura;
    private boolean empezar;

    public ControlBarra() {
        totalEmpleadosParaLiquidar = 0;
        totalEmpleadosLiquidados = 0;
        barra = 0;
        empezar = false;
    }

    public void contarLiquidados() {
        totalEmpleadosLiquidados = administrarBarra.empleadosLiquidados();
        RequestContext.getCurrentInstance().update("form:empleadosLiquidados");
    }

    public void liquidar() {
        usuarioBD = administrarBarra.usuarioBD();
        permisoParaLiquidar = administrarBarra.permisosLiquidar(usuarioBD);
        barra = 0;
        if (permisoParaLiquidar == true) {
            System.out.println("Liquidar: " + permisoParaLiquidar);
            administrarBarra.liquidarNomina();
            long tiempoI = System.currentTimeMillis();
            /*while (!administrarBarra.estadoLiquidacion(usuarioBD).equals("FINALIZADO")) {
             System.out.println("Hola Pipelon");
             }*/
            long tiempoF = System.currentTimeMillis();
            long milisegundos = tiempoF - tiempoI;
            long hora = milisegundos / 3600000;
            long restohora = milisegundos % 3600000;
            long minuto = restohora / 60000;
            long restominuto = restohora % 60000;
            long segundo = restominuto / 1000;
            long restosegundo = restominuto % 1000;
            System.out.println(hora + ":" + minuto + ":" + segundo + "." + restosegundo);
            contarLiquidados();
        } else {
            System.out.println("Liquidar: " + permisoParaLiquidar);
        }
    }

    public void limpiarbarra() {
        barra = 0;
        if (empezar == false) {
            empezar = true;
        } else if (empezar == true) {
            empezar = false;
        }
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

    public int getBarra() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (empezar == false) {
            barra = 0;
        } else {
            if (barra == 100) {
                System.out.println("1215485");
                context.execute("pbAjax.cancel()");
            } else {
                System.out.println("jeje");
                barra = barra + 10;
            }
        }
        context.update("form:pbAjax");
        return barra;
    }

    public void setBarra(int barra) {
        this.barra = barra;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarBarra.parametrosLiquidacion();
        }
        return parametroEstructura;
    }
}

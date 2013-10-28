/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Entidades.ParametrosEstructuras;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author -Feliipe-
 */
@ManagedBean
@SessionScoped
public class ControlCerrarLiquidacion implements Serializable{

    private Integer totalEmpleadosParaLiquidar;
    private boolean permisoParaLiquidar;
    private String usuarioBD;
    private ParametrosEstructuras parametroEstructura;

    public ControlCerrarLiquidacion() {
    }

    public Integer getTotalEmpleadosParaLiquidar() {
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
        return usuarioBD;
    }

    public void setUsuarioBD(String usuarioBD) {
        this.usuarioBD = usuarioBD;
    }

    public ParametrosEstructuras getParametroEstructura() {
        return parametroEstructura;
    }

    public void setParametroEstructura(ParametrosEstructuras parametroEstructura) {
        this.parametroEstructura = parametroEstructura;
    }
}

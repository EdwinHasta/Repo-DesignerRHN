/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.ParametrosEstructuras;

/**
 *
 * @author Administrator
 */
public interface AdministrarBarraInterface {
    public Integer empleadosParaLiquidar();
    public Integer empleadosLiquidados();
    public boolean permisosLiquidar(String usuarioBD);
    public String usuarioBD();
    public void liquidarNomina();
    public String estadoLiquidacion(String usuarioBD);
    public ParametrosEstructuras parametrosLiquidacion();
    public void inicializarParametrosEstados();
    public Integer progresoLiquidacion(Integer totalEmpleadoALiquidar);
}

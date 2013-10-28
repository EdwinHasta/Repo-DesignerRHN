/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

/**
 *
 * @author Administrator
 */
public interface PersistenciaCandadosInterface {
    public boolean permisoLiquidar(String usuarioBD);
    public void liquidar();
    public String estadoLiquidacion(String usuarioBD);
    public Integer progresoLiquidacion(Integer totalEmpleadosALiquidar);
    public void cancelarLiquidacion(String usuarioBD);
    public void cerrarLiquidacionAutomatico();
    public void cerrarLiquidacionNoAutomatico();
}

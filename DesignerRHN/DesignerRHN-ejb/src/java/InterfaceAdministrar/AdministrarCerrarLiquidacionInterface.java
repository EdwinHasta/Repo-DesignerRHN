/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarCerrarLiquidacionInterface {
    public Integer empleadosParaLiquidar();
    public boolean permisosLiquidar(String usuarioBD);
    public String usuarioBD();
    public ParametrosEstructuras parametrosLiquidacion();
    public List<Parametros> empleadosCerrarLiquidacion(String usuarioBD);
    public void cerrarLiquidacionAutomatico();
    public void cerrarLiquidacionNoAutomatico();
    public Integer conteoProcesoSN(BigInteger secProceso);
    public Integer conteoLiquidacionesCerradas(BigInteger secProceso, String fechaDesde, String fechaHasta);
    public void abrirLiquidacion(Short codigoProceso, String fechaDesde, String fechaHasta);
}

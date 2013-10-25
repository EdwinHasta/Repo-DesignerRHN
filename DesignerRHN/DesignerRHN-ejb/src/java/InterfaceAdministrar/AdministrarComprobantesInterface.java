/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.DetallesFormulas;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarComprobantesInterface {

    public List<Parametros> parametrosComprobantes();
    public ParametrosEstructuras parametroEstructura();
    public List<SolucionesNodos> solucionesNodosEmpleado(BigInteger secEmpleado);
    public List<SolucionesNodos> solucionesNodosEmpleador(BigInteger secEmpleado);
    public List<DetallesFormulas> detallesFormula(BigInteger secEmpleado, String fechaDesde, String fechaHasta, BigInteger secProceso, BigInteger secHistoriaFormula);
    public BigInteger obtenerHistoriaFormula(BigInteger secFormula, String fechaDesde);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.DetallesFormulas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaDetallesFormulasInterface {
    public List<DetallesFormulas> detallesFormula(BigInteger secEmpleado, String fechaDesde, String fechaHasta, BigInteger secProceso, BigInteger secHistoriaFormula);
}

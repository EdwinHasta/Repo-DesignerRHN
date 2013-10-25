/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 *
 * @author Administrator
 */
public interface PersistenciaHistoriasformulasInterface {
    public BigInteger obtenerSecuenciaHistoriaFormula(BigInteger secFormula, String fechaDesde);
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'HistoriasFormulas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaHistoriasformulasInterface {
    /**
     * Método encargado de obtener la secuencia de la ultima HistoriaFormula a una fecha dada y que está asociada
     * con una formula especifica.
     * @param secFormula Secuencia de la formula de la que se quiere conocer la HistoriaFormula
     * @param fecha Fecha a la cual se quiere saber, la ultima HistoriaFormula hecha.
     * @return Retorna la secuencia de la ultima HistoriaFormula de una formula a una fecha específica.
     */
    public BigInteger obtenerSecuenciaHistoriaFormula(BigInteger secFormula, String fecha);
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'FormulasNovedades' 
 * de la base de datos. 
 * @author betelgeuse
 */
public interface PersistenciaFormulasNovedadesInterface {
    /**
     * Método encargado de verfificar si una fórmula existe en la tabla FormulasNovedades de la base de datos.
     * @param secFormula Secuencia de la fórmula a verificar. 
     * @return Retorna true si la formula existe en la tabla FormulasNovedades de la base de datos.
     */
    public boolean verificarExistenciaFormulasNovedades(BigInteger secFormula);
}

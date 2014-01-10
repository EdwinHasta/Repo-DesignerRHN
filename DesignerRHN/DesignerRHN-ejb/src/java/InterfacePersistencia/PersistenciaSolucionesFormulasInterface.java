/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'SolucionesFormulas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaSolucionesFormulasInterface {
    /**
     * Método encargado de validar si existe al menos una SolucionFormula para una Novedad específica.
     * En caso de existir la relación significa que la Novedad ya fue liquidada y no puede ser eliminada.
     * @param secNovedad Secuencia de la Novedad
     * @return Retorna 1 si existe la relación y 0 de lo contrario. 
     */
    public int validarNovedadesNoLiquidadas(BigInteger secNovedad);
}

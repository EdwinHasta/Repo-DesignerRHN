/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import java.math.BigInteger;

/**
 * 
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

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Monedas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Monedas' 
 * de la base de datos.
 * @author Viktor
 */
public interface PersistenciaMonedasInterface {
    /**
     * Método encargado de buscar la Moneda con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la Moneda que se quiere encontrar.
     * @return Retorna la Moneda identificada con la secuencia dada por parámetro.
     */
    public Monedas buscarMonedaSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar todas las Monedas existentes en la base de datos.
     * @return Retorna una lista de Contratos. 
     */
    public List<Monedas> listMonedas();
    
}

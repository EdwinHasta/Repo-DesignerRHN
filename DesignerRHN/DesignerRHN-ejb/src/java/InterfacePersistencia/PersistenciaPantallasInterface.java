/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Pantallas;
import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Pantallas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaPantallasInterface {
    /**
     * Método encargado de buscar una pantalla asociada a una tabla.
     * @param secuenciaTab Secuencia de la tabla.
     * @return Retorna una Pantalla.
     */
    public Pantallas buscarPantalla(BigInteger secuenciaTab);

}

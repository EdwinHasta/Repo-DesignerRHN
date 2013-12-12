/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.HvEntrevistas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'HvEntrevistas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaHvEntrevistasInterface {
    /**
     * Método encargado de recuperar las ultimas HvEntrevistas realizadas para una hoja de vida.
     * @param secuenciaHV Secuencia de la hoja de vida.
     * @return Retorna una lista de HvEntrevistas asociadas a una hoja de vida y realizadas el mismo día.
     */
    public List<HvEntrevistas> entrevistasPersona(BigInteger secuenciaHV);
}

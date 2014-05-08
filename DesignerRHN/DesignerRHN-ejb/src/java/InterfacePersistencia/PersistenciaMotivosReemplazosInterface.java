/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MotivosReemplazos;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosReemplazos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaMotivosReemplazosInterface {
    /**
     * Método encargado de buscar todos los MotivosReemplazos existentes en la base de datos, ordenados por código.
     * @return Retorna una lista de MotivosReemplazos ordenados por código.
     */
    public List<MotivosReemplazos> motivosReemplazos(EntityManager em);
    
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposReemplazos;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposReemplazos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTiposReemplazosInterface {
    /**
     * Método encargado de buscar todos los TiposReemplazos existentes en la base de datos, ordenados por código.
     * @return Retorna una lista de TiposReemplazos ordenados por código.
     */
    public List<TiposReemplazos> tiposReemplazos();
    
}

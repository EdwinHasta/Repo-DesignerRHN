/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Departamentos;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Departamentos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaDepartamentosInterface {
    /**
     * Método encargado de buscar todos los Departamentos existentes en la base de datos. 
     * @return Retorna una lista de Deparamentos
     */
    public List<Departamentos> departamentos();
}

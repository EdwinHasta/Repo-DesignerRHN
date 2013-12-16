/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.UbicacionesGeograficas;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'UbicacionesGeograficas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaUbicacionesGeograficasInterface {
    /**
     * Método encargado de buscar todas las UbicacionesGeograficas existentes en la base de datos.
     * @return Retorna una lista de UbicacionesGeograficas.
     */
    public List<UbicacionesGeograficas> buscarUbicacionesGeograficas();
}

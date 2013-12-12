/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.ObjetosDB;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'ObjetosDB' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaObjetosDBInterface {
    /**
     * Método encargado de buscar el ObjetoDB con el nombre que entra por parámetro.
     * @param nombreTabla Nombre del ObjetoDB
     * @return Retorna el ObjetoDB con nombre igual al dado por parámetro.
     */
    public ObjetosDB obtenerObjetoTabla(String nombreTabla);
}

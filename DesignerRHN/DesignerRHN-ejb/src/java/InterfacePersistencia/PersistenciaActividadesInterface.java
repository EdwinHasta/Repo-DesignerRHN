/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Actividades;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Actividades' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaActividadesInterface {
    /**
     * Método encargado de insertar una actividad en la base de datos.
     * @param actividades Actividad que se quiere crear.
     */
    public void crear(Actividades actividades);
    /**
     * Método encargado de modificar una actividad de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.    
     * @param actividades Actividad con los cambios que se van a realizar.
     */
    public void editar(Actividades actividades);
    /**
     * Método encargado de eliminar de la base de datos la actividad que entra por parámetro.
     * @param actividades Actividad que se quiere eliminar.
     */
    public void borrar(Actividades actividades);
    /**
     * Método encargado de buscar todas las actividades existentes en la base de datos.
     * @return Retorna una lista de actividades.
     */
    public List<Actividades> buscarActividades();    
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Usuarios;
import javax.persistence.EntityManager;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MotivosContratos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaUsuariosInterface {
    /**
     * Método encargado de buscar el Usuario con el alias dado por parámetro.
     * @param alias Alias del Usuario que se quiere encontrar.
     * @return Retorna el Usuario identificado con el alias dado por parámetro.
     */
    public Usuarios buscarUsuario(EntityManager em, String alias);
}

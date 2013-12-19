/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Tiposausentismos;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposAusentismos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTiposAusentismosInterface {
    /**
     * Método encargado de insertar un TipoAusentismo en la base de datos.
     * @param tiposAusentismos TipoAusentismo que se quiere crear.
     */
    public void crear(Tiposausentismos tiposAusentismos);
    /**
     * Método encargado de modificar un TipoAusentismo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposAusentismos TipoAusentismo con los cambios que se van a realizar.
     */
    public void editar(Tiposausentismos tiposAusentismos);
    /**
     * Método encargado de eliminar de la base de datos el TipoAusentismo que entra por parámetro.
     * @param tiposAusentismos TipoAusentismo que se quiere eliminar.
     */
    public void borrar(Tiposausentismos tiposAusentismos);
    /**
     * Método encargado de buscar todos los TiposAusentismos existentes en la base de datos, ordenados por código.
     * @return Retorna una lista de TiposAusentismos ordenados por código.
     */
    public List<Tiposausentismos> buscarTiposAusentismos ();
}

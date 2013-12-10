/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Idiomas;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Idiomas' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaIdiomasInterface {
    /**
     * Método encargado de insertar un Idioma en la base de datos.
     * @param idiomas Idioma que se quiere crear.
     */
    public void crear(Idiomas idiomas);
    /**
     * Método encargado de modificar un Idioma de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param idiomas Idioma con los cambios que se van a realizar.
     */
    public void editar(Idiomas idiomas);
    /**
     * Método encargado de eliminar de la base de datos el Idioma que entra por parámetro.
     * @param idiomas Idioma que se quiere eliminar.
     */
    public void borrar(Idiomas idiomas);
    /**
     * Método encargado de buscar todos los Idiomas existentes en la base de datos.
     * @return Retorna una lista de Idiomas.
     */
    public List<Idiomas> buscarIdiomas();
    
}

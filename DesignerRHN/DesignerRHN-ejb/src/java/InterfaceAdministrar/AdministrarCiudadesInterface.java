/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'Ciudades'.
 * @author betelgeuse
 */
public interface AdministrarCiudadesInterface {
    /**
     * Método encargado de crear Ciudades.
     * @param listaCiudades Lista de los Ciudades que se van a crear.
     */
    public void crearCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de editar Ciudades.
     * @param listaCiudades Lista de los Ciudades que se van a modificar.
     */
    public void modificarCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de borrar Ciudades.
     * @param listaCiudades Lista de los Ciudades que se van a eliminar.
     */
    public void borrarCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de recuperar todos los Ciudades.
     * @return Retorna una lista de Ciudades.
     */
    public List<Ciudades> Ciudades();
    /**
     * Método encargado de recuperar todos los Ciudades.
     * @return Retorna una lista de Ciudades.
     */
    public List<Ciudades>  lovCiudades();
}

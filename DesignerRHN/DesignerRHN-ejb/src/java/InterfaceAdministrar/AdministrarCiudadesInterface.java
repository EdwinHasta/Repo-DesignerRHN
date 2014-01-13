/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'ATExtraRecargo'.
 * @author betelgeuse
 */
public interface AdministrarCiudadesInterface {
    /**
     * Método encargado de crear ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a crear.
     */
    public void crearCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de editar ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a modificar.
     */
    public void modificarCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de borrar ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a eliminar.
     */
    public void borrarCiudades(List<Ciudades> listaCiudades);
    /**
     * Método encargado de recuperar todos los ExtrasRecargos.
     * @return Retorna una lista de ExtrasRecargos.
     */
    public List<Ciudades> Ciudades();
    /**
     * Método encargado de recuperar todos los ExtrasRecargos.
     * @return Retorna una lista de ExtrasRecargos.
     */
    public List<Ciudades>  lovCiudades();
}

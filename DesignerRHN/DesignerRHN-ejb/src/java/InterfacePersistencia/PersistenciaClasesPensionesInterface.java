/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.ClasesPensiones;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'ClasesPensiones' de la base de datos.
 *
 * @author Andrés Pineda
 */
public interface PersistenciaClasesPensionesInterface {

    /**
     * Método encargado de insertar una ClasePension en la base de datos.
     *
     * @param clasesPensiones ClasePension que se quiere crear.
     */
    public void crear(ClasesPensiones clasesPensiones);

    /**
     * Método encargado de modificar una ClasePension de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param clasesPensiones ClasePension con los cambios que se van a
     * realizar.
     */
    public void editar(ClasesPensiones clasesPensiones);

    /**
     * Método encargado de eliminar de la base de datos la ClasePension que
     * entra por parámetro.
     *
     * @param clasesPensiones ClasePension que se quiere eliminar.
     */
    public void borrar(ClasesPensiones clasesPensiones);

    /**
     * Método encargado de buscar todas las ClasesPensiones existentes en la
     * base de datos.
     *
     * @return Retorna una lista de ClasesPensiones.
     */
    public List<ClasesPensiones> consultarClasesPensiones();

    /**
     * Método encargado de buscar la ClasePension con la secClasesPensiones dada
     * por parámetro.
     *
     * @param secClasesPensiones Secuencia de la ClasePension que se quiere
     * encontrar.
     * @return Retorna la ClasePension identificada con la secClasesPensiones
     * dada por parámetro.
     */
    public ClasesPensiones consultarClasePension(BigInteger secClasesPensiones);

    /**
     * Metodo encargado de contar cuantas Retirados estan relacionadas con la
     * secClasesPensiones de ClasePension recivida
     *
     * @param secClasesPensiones Secuencia de la ClasePension
     * @return Cuantos Retirados tienen la secClasesPensiones recivida
     */
    public BigInteger contarRetiradosClasePension(BigInteger secClasesPensiones);
}

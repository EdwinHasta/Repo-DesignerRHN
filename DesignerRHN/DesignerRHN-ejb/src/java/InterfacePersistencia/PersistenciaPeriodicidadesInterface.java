/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Periodicidades;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Periodicidades' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaPeriodicidadesInterface {

    /**
     * Método encargado de insertar una Periodicidad en la base de datos.
     *
     * @param periodicidades Periodicidad que se quiere crear.
     */
    public void crear(Periodicidades periodicidades);

    /**
     * Método encargado de modificar una Periodicidad de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param periodicidades Periodicidad con los cambios que se van a realizar.
     */
    public void editar(Periodicidades periodicidades);

    /**
     * Método encargado de eliminar de la base de datos la Periodicidad que
     * entra por parámetro.
     *
     * @param periodicidades Periodicidad que se quiere eliminar.
     */
    public void borrar(Periodicidades periodicidades);

    /**
     * Método encargado de buscar la Periodicidad con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia de la Periodicidad que se quiere encontrar.
     * @return Retorna la Periodicidad identificada con la secuencia dada por
     * parámetro.
     */
    public Periodicidades buscarPeriodicidades(BigInteger secuencia);

    /**
     * Método encargado de buscar todas las Periodicidades existentes en la base
     * de datos.
     *
     * @return Retorna una lista de Periodicidades.
     */
    public List<Periodicidades> buscarPeriodicidades();

    /**
     * Método encargado de verificar si hay al menos una Periodicidad con el
     * código dado como parámetro.
     *
     * @param codigoPeriodicidad Código de la Periodicidad
     * @return Retorna true si existe alguna Periodicidad con el código dado por
     * parámetro.
     */
    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad);
}

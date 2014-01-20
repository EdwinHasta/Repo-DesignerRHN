/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MotivosCambiosCargos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'MotivosCambiosCargos' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaMotivosCambiosCargosInterface {

    /**
     * Método encargado de insertar un MotivoCambioCargo en la base de datos.
     *
     * @param motivoCambioCargo MotivoCambioCargo que se quiere crear.
     */
    public void crear(MotivosCambiosCargos motivoCambioCargo);

    /**
     * *
     * Método encargado de modificar un MotivoCambioCargo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param motivoCambioCargo MotivoCambioCargo con los cambios que se van a
     * realizar.
     */
    public void editar(MotivosCambiosCargos motivoCambioCargo);

    /**
     * Método encargado de eliminar de la base de datos el MotivoCambioCargo que
     * entra por parámetro.
     *
     * @param motivoCambioCargo MotivoCambioCargo que se quiere eliminar.
     */
    public void borrar(MotivosCambiosCargos motivoCambioCargo);

    /**
     * Método encargado de buscar el MotivoCambioCargo con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia del MotivoCambioCargo que se quiere encontrar.
     * @return Retorna el MotivoCambioCargo identificado con la secuencia dada
     * por parámetro.
     */
    public MotivosCambiosCargos buscarMotivoCambioCargo(BigInteger secuencia);

    /**
     * Método encargado de buscar todos los MotivosCambiosCargos existentes en
     * la base de datos.
     *
     * @return Retorna una lista de MotivosCambiosCargos.
     */
    public List<MotivosCambiosCargos> buscarMotivosCambiosCargos();

    /**
     * Método encargado de verificar si hay al menos una VigenciaCargo asociada
     * a un MotivoCambioCargo.
     *
     * @param secuencia Secuencia del MotivoCambioCargo
     * @return Retorna un valor mayor a cero si existe alguna VigenciaCargo
     * asociada a un MotivoCambioCargo.
     */
    public BigInteger verificarBorradoVigenciasCargos(BigInteger secuencia);
}

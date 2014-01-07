/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Enfermedades;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Enfermedades' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaEnfermedadesInterface {

    /**
     * Método encargado de insertar una Enfermedad en la base de datos.
     *
     * @param enfermedades Enfermedad que se quiere crear.
     */
    public void crear(Enfermedades enfermedades);

    /**
     * Método encargado de modificar una Enfermedad de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param enfermedades Enfermedad con los cambios que se van a realizar.
     */
    public void editar(Enfermedades enfermedades);

    /**
     * Método encargado de eliminar de la base de datos la Enfermedad que entra
     * por parámetro.
     *
     * @param enfermedades Enfermedad
     */
    public void borrar(Enfermedades enfermedades);

    /**
     * Método encargado de buscar la Enfermedad con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia de la Enfermedad que se quiere encontrar.
     * @return Retorna la Enfermedad identificada con la secuencia dada por
     * parámetro.
     */
    public Enfermedades buscarEnfermedad(BigInteger secuencia);

    /**
     * Método encargado de buscar todas las Enfermedades existentes en la base
     * de datos.
     *
     * @return Retorna una lista de Enfermedades.
     */
    public List<Enfermedades> buscarEnfermedades();

    public BigInteger contadorAusentimos(BigInteger secuencia);

    public BigInteger contadorDetallesLicencias(BigInteger secuencia);

    public BigInteger contadorEnfermedadesPadecidas(BigInteger secuencia);

    public BigInteger contadorSoausentismos(BigInteger secuencia);

    public BigInteger contadorSorevisionessSistemas(BigInteger secuencia);
}

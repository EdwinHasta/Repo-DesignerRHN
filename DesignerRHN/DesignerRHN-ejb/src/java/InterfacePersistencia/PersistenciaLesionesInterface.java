/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Lesiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Lesiones' de la base de datos.
 *
 * @author John Pineda.
 */
@Local
public interface PersistenciaLesionesInterface {

    /**
     * Método encargado de insertar una Lesion en la base de datos.
     *
     * @param lesiones Lesion que se quiere crear.
     */
    public void crear(Lesiones lesiones);

    /**
     * Método encargado de modificar una Lesion de la base de datos. Este método
     * recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param lesiones Lesion con los cambios que se van a realizar.
     */
    public void editar(Lesiones lesiones);

    /**
     * Método encargado de eliminar de la base de datos la Lesion que entra por
     * parámetro.
     *
     * @param lesiones Lesion que se quiere eliminar.
     */
    public void borrar(Lesiones lesiones);

    /**
     * Método encargado de buscar la Lesion con la secuencia dada por parámetro.
     *
     * @param secuencia Secuencia de la Lesion que se quiere encontrar.
     * @return Retorna la Lesion identificada con la secuencia dada por
     * parámetro.
     */
    public Lesiones buscarLesion(BigInteger secuencia);

    /**
     * Método encargado de buscar todas las Lesiones existentes en la base de
     * datos.
     *
     * @return Retorna una lista de Lesiones.
     */
    public List<Lesiones> buscarLesiones();

    /**
     * Método encargado de contar los DetallesLicensias que están asociados a
     * una Lesion específica.
     *
     * @param secuencia Secuencia de la Lesion.
     * @return Retorna la cantidad de DetallesLicensias cuya Lesion tiene como
     * secuencia el valor dado por parámetro.
     */
    public BigInteger contadorDetallesLicensias(BigInteger secuencia);

    /**
     * Método encargado de contar los SOAccidentesMedicos que están asociados a
     * una Lesion específica.
     *
     * @param secuencia Secuencia de la Lesion.
     * @return Retorna la cantidad de SOAccidentesMedicos cuya Lesion tiene como
     * secuencia el valor dado por parámetro.
     */
    public BigInteger contadorSoAccidentesDomesticos(BigInteger secuencia);
}

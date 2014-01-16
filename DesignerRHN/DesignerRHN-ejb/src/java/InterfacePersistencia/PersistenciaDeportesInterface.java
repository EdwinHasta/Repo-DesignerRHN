/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Deportes;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Deportes' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaDeportesInterface {

    /**
     * Método encargado de insertar un Deporte en la base de datos.
     *
     * @param deportes Deporte que se quiere crear.
     */
    public void crear(Deportes deportes);

    /**
     * Método encargado de modificar un Deporte de la base de datos. Este método
     * recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param deportes Deporte con los cambios que se van a realizar.
     */
    public void editar(Deportes deportes);

    /**
     * Método encargado de eliminar de la base de datos el Deporte que entra por
     * parámetro.
     *
     * @param deportes Deporte que se quiere eliminar.
     */
    public void borrar(Deportes deportes);

    /**
     * Método encargado de buscar el Deporte con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Deporte que se quiere encontrar.
     * @return Retorna el Deporte cuya secuencia coincide con el parámetro dado.
     */
    public Deportes buscarDeporte(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Deportes existentes en la base de
     * datos.
     *
     * @return Retorna una lista de Deportes.
     */
    public List<Deportes> buscarDeportes();

    /**
     * 
     * @param secuencia
     * @return
     */
    public BigInteger contadorParametrosInformes(BigInteger secuencia);

    /**
     * 
     * @param secuencia
     * @return
     */
    public BigInteger contadorDeportesPersonas(BigInteger secuencia);

    /**
     * 
     * @param secuencia
     * @return
     */
    public BigInteger verificarBorradoVigenciasDeportes(BigInteger secuencia);

}

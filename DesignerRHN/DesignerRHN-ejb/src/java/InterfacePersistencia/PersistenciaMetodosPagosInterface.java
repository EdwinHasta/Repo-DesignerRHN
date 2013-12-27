/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.MetodosPagos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'MetodosPagos' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaMetodosPagosInterface {
    /**
     * Método encargado de insertar un MetodoPago en la base de datos.
     * @param metodosPagos MetodoPago que se quiere crear.
     */
    public void crear(MetodosPagos metodosPagos);
    /**
     * Método encargado de modificar un MetodoPago de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param metodosPagos MetodoPago con los cambios que se van a realizar.
     */
    public void editar(MetodosPagos metodosPagos);
    /**
     * Método encargado de eliminar de la base de datos el MetodoPago que entra por parámetro.
     * @param metodosPagos MetodoPago que se quiere eliminar.
     */
    public void borrar(MetodosPagos metodosPagos);
    /**
     * Método encargado de buscar el MetodoPago con la secuencia dada por parámetro.
     * @param secuencia Secuencia del MetodoPago que se quiere encontrar.
     * @return Retorna el MetodoPago identificado con la secuencia dada por parámetro.
     */
    public MetodosPagos buscarMetodosPagosEmpleado(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los MetodosPagos existentes en la base de datos.
     * @return Retorna una lista de MetodosPagos.
     */
    public List<MetodosPagos> buscarMetodosPagos();
}

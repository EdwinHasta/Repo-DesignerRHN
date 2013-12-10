/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Aficiones;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Aficiones' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaAficionesInterface {
    /**
     * Método encargado de insertar una afición en la base de datos.
     * @param aficiones Afición que se quiere crear.
     */
    public void crear(Aficiones aficiones);
    /**
     * Método encargado de modificar una afición de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param aficiones Afición con los cambios que se van a realizar. 
     */
    public void editar(Aficiones aficiones);
    /**
     * Método encargado de eliminar de la base de datos la afición que entra por parámetro.
     * @param aficiones Afición que se quiere eliminar
     */
    public void borrar(Aficiones aficiones);
    /**
     * Método encargado de buscar la afición con el secuencia dado por parámetro.
     * @param secuencia Identificador único de la afición que se quiere encontrar.
     * @return Retorna la afición identificada con el secuencia dado por parámetro.
     */
    public Aficiones buscarAficion(BigInteger secuencia);
    /**
     * Método encargado de buscar todas las aficiones existentes en la base de datos.
     * @return Retorna una lista de aficiones.
     */
    public List<Aficiones> buscarAficiones();
    /**
     * Método encargado de retornar el código cuyo valor sea el más grande de la tabla 'Aficiones'
     * de la base de datos.
     * @return Retorna el valor del código con el máximo valor.
     */
    public short maximoCodigoAficiones();
    /**
     * Método encargado de buscar la afición con un código determinado.
     * @param cod Código de la afición que se quiere buscar.
     * @return Retorna la afición cuyo código es igual al que entra por parámetro.
     */
    public Aficiones buscarAficionCodigo(Short cod);
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.EstadosCiviles;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'EstadosCiviles' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaEstadosCivilesInterface {
    /**
     * Método encargado de insertar un EstadoCivil en la base de datos.
     * @param estadosCiviles EstadoCivil que se quiere crear.
     */
    public void crear(EstadosCiviles estadosCiviles);
    /**
     * Método encargado de modificar un EstadoCivil de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param estadosCiviles EstadoCivil con los cambios que se van a realizar.
     */
    public void editar(EstadosCiviles estadosCiviles);
    /**
     * Método encargado de eliminar de la base de datos el EstadoCivil que entra por parámetro.
     * @param estadosCiviles EstadoCivil que se quiere eliminar.
     */
    public void borrar(EstadosCiviles estadosCiviles);
    /**
     * Método encargado de buscar el EstadoCivil con la secuencia dada por parámetro.
     * @param secuencia Identificador único del EstadoCivil que se quiere encontrar.
     * @return Retorna el EstadoCivil identificado con la secuencia dada por parámetro. 
     */
    public EstadosCiviles buscarEstadoCivil(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los EstadosCiviles existentes en la base de datos.
     * @return Retorna una lista de EstadosCiviles
     */
    public List<EstadosCiviles> buscarEstadosCiviles();
    
}

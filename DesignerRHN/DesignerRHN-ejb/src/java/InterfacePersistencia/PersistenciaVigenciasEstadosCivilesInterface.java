/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.VigenciasEstadosCiviles;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'VigenciasEstadosCiviles' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaVigenciasEstadosCivilesInterface {
    /**
     * Método encargado de insertar una VigenciaEstadoCivil en la base de datos.
     * @param vigenciasEstadosCiviles VigenciaEstadoCivil que se quiere crear.
     */
    public void crear(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    /**
     * Método encargado de modificar una VigenciaEstadoCivil de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param vigenciasEstadosCiviles VigenciaEstadoCivil con los cambios que se van a realizar.
     */
    public void editar(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    /**
     * Método encargado de eliminar de la base de datos la VigenciaEstadoCivil que entra por parámetro.
     * @param vigenciasEstadosCiviles VigenciaEstadoCivil que se quiere eliminar.
     */
    public void borrar(VigenciasEstadosCiviles vigenciasEstadosCiviles);
    /**
     * Método encargado de buscar la VigenciaEstadoCivil con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la VigenciaEstadoCivil que se quiere encontrar.
     * @return Retorna la VigenciaEstadoCivil identificada con la secuencia dada por parámetro.
     */
    public VigenciasEstadosCiviles buscarVigenciaEstadoCivil(BigInteger secuencia);
    /**
     * Método encargado de buscar todas las VigenciasEstadosCiviles existentes en la base de datos.
     * @return Retorna una lista de VigenciasEstadosCiviles.
     */
    public List<VigenciasEstadosCiviles> buscarVigenciasEstadosCiviles();
    /**
     * Método encargado de buscar las últimas VigenciasEstadosCiviles registradas de una Persona específica.
     * @param secuencia Secuencia de la Persona.
     * @return Retorna una lista de las VigenciasEstadosCiviles asociadas a una Persona. 
     */
    public List<VigenciasEstadosCiviles> vigenciaEstadoCivilPersona(BigInteger secuencia);
}

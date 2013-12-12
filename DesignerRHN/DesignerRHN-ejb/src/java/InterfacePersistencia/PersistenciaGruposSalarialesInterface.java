/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.GruposSalariales;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'GruposSalariales' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaGruposSalarialesInterface {
    /**
     * Método encargado de insertar un GrupoSalarial en la base de datos.
     * @param gruposSalariales GrupoSalarial
     */
    public void crear(GruposSalariales gruposSalariales);
    /**
     * Método encargado de modificar un GrupoSalarial de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param gruposSalariales GrupoSalarial con los cambios que se van a realizar.
     */
    public void editar(GruposSalariales gruposSalariales);
    /**
     * Método encargado de eliminar de la base de datos el GrupoSalarial que entra por parámetro.
     * @param gruposSalariales GrupoSalarial que se quiere eliminar.
     */
    public void borrar(GruposSalariales gruposSalariales);
    /**
     * Método encargado de buscar todos los GruposSalariales existentes en la base de datos.
     * @return Retorna una lista de GruposSalariales.
     */
    public List<GruposSalariales> buscarGruposSalariales();
    /**
     * Método encargado de buscar el GrupoSalarial con la secuencia dada por parámetro.
     * @param secuencia Secuencia del GrupoSalarial que se quiere encontrar.
     * @return Retorna el GrupoSalarial identificado con la secuencia dada por parámetro.
     */
    public GruposSalariales buscarGrupoSalarialSecuencia(BigInteger secuencia);
}

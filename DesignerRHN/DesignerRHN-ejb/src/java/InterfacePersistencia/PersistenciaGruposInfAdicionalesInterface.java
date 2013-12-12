/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.GruposInfAdicionales;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'GruposInfAdicionales' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaGruposInfAdicionalesInterface {
    /**
     * Método encargado de insertar un GrupoInfAdicional en la base de datos.
     * @param gruposInfAdicionales GrupoInfAdicional que se quiere crear.
     */
    public void crear(GruposInfAdicionales gruposInfAdicionales);
    /**
     * Método encargado de modificar un GrupoInfAdicional de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param gruposInfAdicionales GrupoInfAdicional con los cambios que se van a realizar.
     */
    public void editar(GruposInfAdicionales gruposInfAdicionales);
    /**
     * Método encargado de eliminar de la base de datos el GrupoInfAdicional que entra por parámetro.
     * @param gruposInfAdicionales GrupoInfAdicional que se quiere eliminar.
     */
    public void borrar(GruposInfAdicionales gruposInfAdicionales);
    /**
     * Método encargado de buscar el GrupoInfAdicional con la secuencia dada por parámetro.
     * @param secuencia Secuencia del GrupoInfAdicional que se quiere encontrar.
     * @return Retorna el GrupoInfAdicional identificado con la secuencia dada por parámetro.
     */
    public GruposInfAdicionales buscarGrupoInfAdicional(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los GruposInfAdicionales existentes en la base de datos.
     * @return Retorna una lista de GruposInfAdicionales.
     */
    public List<GruposInfAdicionales> buscarGruposInfAdicionales();
}

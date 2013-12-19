/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposIndicadores;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposIndicadores' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaTiposIndicadoresInterface {
    /**
     * Método encargado de insertar un TipoIndicador en la base de datos.
     * @param tiposIndicadores TipoIndicador que se quiere crear.
     */
    public void crear(TiposIndicadores tiposIndicadores);
    /**
     * Método encargado de modificar un TipoIndicador de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposIndicadores TipoIndicador con los cambios que se van a realizar.
     */
    public void editar(TiposIndicadores tiposIndicadores);
    /**
     * Método encargado de eliminar de la base de datos el TipoIndicador que entra por parámetro.
     * @param tiposIndicadores TipoIndicador que se quiere eliminar.
     */
    public void borrar(TiposIndicadores tiposIndicadores);
    /**
     * Método encargado de buscar todos los TiposIndicadores existentes en la base de datos.
     * @return Retorna una lista de TiposIndicadores.
     */
    public List<TiposIndicadores> buscarTiposIndicadores();
    /**
     * Método encargado de buscar el TipoIndicador con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoIndicador que se quiere encontrar.
     * @return Retorna el TipoIndicador identificado con la secuencia dada por parámetro.
     */
    public TiposIndicadores buscarTiposIndicadoresSecuencia(BigInteger secuencia);
    
}

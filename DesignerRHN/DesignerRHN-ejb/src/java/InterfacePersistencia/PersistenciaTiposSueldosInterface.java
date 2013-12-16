/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposSueldos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposSueldos' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaTiposSueldosInterface {
    /**
     * Método encargado de insertar un TipoSueldo en la base de datos.
     * @param tiposSueldos TipoSueldo que se quiere crear.
     */
    public void crear(TiposSueldos tiposSueldos);
    /**
     * Método encargado de modificar un TipoSueldo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposSueldos TipoSueldo con los cambios que se van a realizar.
     */
    public void editar(TiposSueldos tiposSueldos);
    /**
     * Método encargado de eliminar de la base de datos el TipoSueldo que entra por parámetro.
     * @param tiposSueldos TipoSueldo que se quiere eliminar.
     */
    public void borrar(TiposSueldos tiposSueldos);
    /**
     * Método encargado de buscar todos los TiposSueldos existentes en la base de datos.
     * @return Retorna una lista de TiposSueldos.
     */
    public List<TiposSueldos> buscarTiposSueldos();
    /**
     * Método encargado de buscar el TipoSueldo con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoSueldo que se quiere encontrar.
     * @return Retorna el TipoSueldo identificado con la secuencia dada por parámetro.
     */
    public TiposSueldos buscarTipoSueldoSecuencia(BigInteger secuencia);
    
}

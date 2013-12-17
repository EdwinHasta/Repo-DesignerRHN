/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposTrabajadores;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposTrabajadores' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaTiposTrabajadoresInterface {
    /**
     * Método encargado de insertar un TipoTrabajador en la base de datos.
     * @param tiposTrabajadores TipoTrabajador que se quiere crear.
     */
    public void crear(TiposTrabajadores tiposTrabajadores);
    /**
     * Método encargado de modificar un TipoTrabajador de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposTrabajadores TipoTrabajador con los cambios que se van a realizar.
     */
    public void editar(TiposTrabajadores tiposTrabajadores);
    /**
     * Método encargado de eliminar de la base de datos el TipoTrabajador que entra por parámetro.
     * @param tiposTrabajadores TipoTrabajador que se quiere eliminar.
     */
    public void borrar(TiposTrabajadores tiposTrabajadores);
    /**
     * Método encargado de buscar todos los TiposTrabajadores existentes en la base de datos.
     * @return Retorna una lista de TiposTrabajadores.
     */
    public List<TiposTrabajadores> buscarTiposTrabajadores();
    /**
     * Método encargado de buscar el TipoTrabajador con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoTrabajador que se quiere encontrar.
     * @return Retorna el TipoTrabajador identificado con la secuencia dada por parámetro.
     */
    public TiposTrabajadores buscarTipoTrabajadorSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar el TipoTrabajador con el código dado por parámetro.
     * @param codigo Código del TipoTrabajador que se quiere encontrar.
     * @return Retorna el TipoTrabajador identificado con el código dado por parámetro.
     */
    public TiposTrabajadores buscarTipoTrabajadorCodigo (BigDecimal codigo);
    
}

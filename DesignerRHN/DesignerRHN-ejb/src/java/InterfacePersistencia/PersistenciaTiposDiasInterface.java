/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposDias;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposDias' 
 * de la base de datos.
 * @author Andres Pineda.
 */
public interface PersistenciaTiposDiasInterface {
    /**
     * Método encargado de insertar un TipoDia en la base de datos.
     * @param tiposDias TipoDia que se quiere crear.
     */
    public void crear(TiposDias tiposDias);
    /**
     * Método encargado de modificar un TipoDia de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposDias TipoDia con los cambios que se van a realizar.
     */
    public void editar(TiposDias tiposDias);
    /**
     * Método encargado de eliminar de la base de datos el TipoDia que entra por parámetro.
     * @param tiposDias TipoDia que se quiere eliminar.
     */
    public void borrar(TiposDias tiposDias);
    /**
     * Método encargado de buscar el TipoDia con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoDia que se quiere encontrar.
     * @return Retorna el TipoDia identificado con la secuencia dada por parámetro.
     */
    public TiposDias buscarTipoDia(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los TiposDias existentes en la base de datos, ordenados
     * descendentemente por código.
     * @return Retorna una lista de TipoDias.
     */
    public List<TiposDias> buscarTiposDias();
}

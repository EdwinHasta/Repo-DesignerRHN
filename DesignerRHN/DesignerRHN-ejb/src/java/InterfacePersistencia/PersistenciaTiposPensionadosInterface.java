/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposPensionados;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposPensionados' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaTiposPensionadosInterface {
    /**
     * Método encargado de insertar un TipoPensionado en la base de datos.
     * @param tiposPensionados TipoPensionado que se quiere crear.
     */
    public void crear(TiposPensionados tiposPensionados);
    /**
     * Método encargado de modificar un TipoPensionado de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposPensionados TipoPensionado con los cambios que se van a realizar.
     */
    public void editar(TiposPensionados tiposPensionados);
    /**
     * Método encargado de eliminar de la base de datos el TipoPensionado que entra por parámetro.
     * @param tiposPensionados TipoPensionado que se quiere eliminar.
     */
    public void borrar(TiposPensionados tiposPensionados);
    /**
     * Método encargado de buscar todos los TiposPensionados existentes en la base de datos.
     * @return Retorna una lista de TiposPensionados.
     */
    public List<TiposPensionados> buscarTiposPensionados();
    /**
     * Método encargado de buscar el TipoPensionado con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoPensionado que se quiere encontrar.
     * @return Retorna el TipoPensionado identificado con la secuencia dada por parámetro.
     */
    public TiposPensionados buscarTipoPensionSecuencia(BigInteger secuencia); 
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.TiposCentrosCostos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposCentrosCostos' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaTiposCentrosCostosInterface {
    /**
     * Método encargado de insertar un TipoCentroCosto en la base de datos.
     * @param tiposCentrosCostos TipoCentroCosto que se quiere crear.
     */
    public void crear(TiposCentrosCostos tiposCentrosCostos);
    /**
     * Método encargado de modificar un TipoCentroCosto de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param tiposCentrosCostos TipoCentroCosto con los cambios que se van a realizar.
     */
    public void editar(TiposCentrosCostos tiposCentrosCostos);
    /**
     * Método encargado de eliminar de la base de datos el TipoCentroCosto que entra por parámetro.
     * @param tiposCentrosCostos TipoCentroCosto que se quiere eliminar.
     */
    public void borrar(TiposCentrosCostos tiposCentrosCostos);
    /**
     * Método encargado de buscar el TipoCentroCosto con la secuencia dada por parámetro.
     * @param secuencia Secuencia del TipoCentroCosto que se quiere encontrar.
     * @return Retorna el TipoCentroCosto identificado con la secuencia dada por parámetro.
     */
    public TiposCentrosCostos buscarTipoCentrosCostos(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los TiposCentrosCostos existentes en la base de datos.
     * @return Retorna una lista de TiposCentrosCostos.
     */
    public List<TiposCentrosCostos> buscarTiposCentrosCostos();
    /**
     * Método encargado de contar los CentrosCostos asociados a el TipoCentroCosto cuya secuencia coincide con la del 
     * parámetro.
     * @param secuencia Secuencia del TipoCentroCosto
     * @return Retorna un Número con la cantidad de CentrosCostos asociados a un TipoCentroCosto.
     */
    public Long verificarBorradoCentrosCostos(BigInteger secuencia);
    /**
     * Método encargado de contar las VigenciasCuentas asociadas a el TipoCentroCosto cuya secuencia coincide con la del 
     * parámetro.
     * @param secuencia Secuencia del TipoCentroCosto
     * @return Retorna un Número con la cantidad de VigenciasCuentas asociados a un TipoCentroCosto.
     */
    public Long verificarBorradoVigenciasCuentas(BigInteger secuencia);
    /**
     * Método encargado de contar los RiesgosProfesionales asociados a el TipoCentroCosto cuya secuencia coincide con la del 
     * parámetro.
     * @param secuencia Secuencia del TipoCentroCosto
     * @return Retorna un Número con la cantidad de RiesgosProfesionales asociados a un TipoCentroCosto.
     */
    public Long verificarBorradoRiesgosProfesionales(BigInteger secuencia);
}

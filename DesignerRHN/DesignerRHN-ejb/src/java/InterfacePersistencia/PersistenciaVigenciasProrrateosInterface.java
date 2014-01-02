/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.VigenciasProrrateos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'VigenciasProrrateos' 
 * de la base de datos.
 * @author AndresPineda
 */
public interface PersistenciaVigenciasProrrateosInterface {
    /**
     * Método encargado de insertar una VigenciaProrrateo en la base de datos.
     * @param vigenciasProrrateos VigenciaProrrateo que se quiere crear.
     */
    public void crear(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Método encargado de modificar una VigenciaProrrateo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param vigenciasProrrateos VigenciaProrrateo con los cambios que se van a realizar.
     */
    public void editar(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Método encargado de eliminar de la base de datos la VigenciaProrrateo que entra por parámetro.
     * @param vigenciasProrrateos VigenciaProrrateo que se quiere eliminar.
     */
    public void borrar(VigenciasProrrateos vigenciasProrrateos);
    /**
     * Método encargado de buscar todas las VigenciasProrrateos existentes en la base de datos.
     * @return Retorna una lista de VigenciasProrrateos.
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateos();
    /**
     * Método encargado de buscar las VigenciasProrrateos de un Empleado específico.
     * @param secuencia Secuencia del Empleado.
     * @return Retorna las VigenciasProrrateos, odenadas descendentemente por la fechaInicial, del Empleado cuya secuencia coincide 
     * con la secuencia dada por parámetro.
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateosEmpleado(BigInteger secuencia);
    /**
     * Método encargado de buscar la VigenciaProrrateo con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la VigenciaProrrateo que se quiere encontrar.
     * @return Retorna la VigenciaProrrateo identificada con la secuencia dada por parámetro.
     */
    public VigenciasProrrateos buscarVigenciaProrrateoSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar las VigenciaProrrateo asociadas a una VigenciaLocalizacion específica.
     * @param secuencia Secuencia de la VigenciaLocalizacion.
     * @return Retorna la lista de VigenciasProrrateos que estan asociadas con la VigenciaLocalizacion
     * cuya secuencia coincide con la secuencia dada por parámetro. 
     */
    public List<VigenciasProrrateos> buscarVigenciasProrrateosVigenciaSecuencia(BigInteger secuencia);
    
}

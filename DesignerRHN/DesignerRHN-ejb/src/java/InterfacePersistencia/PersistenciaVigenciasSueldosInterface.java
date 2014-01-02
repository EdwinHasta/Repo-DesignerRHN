/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.VigenciasSueldos;
import java.math.BigInteger;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'VigenciasSueldos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaVigenciasSueldosInterface {
    /**
     * Método encargado de insertar una VigenciaSueldo en la base de datos.
     * @param vigenciasSueldos VigenciaSueldo que se quiere crear.
     */
    public void crear(VigenciasSueldos vigenciasSueldos);
    /**
     * Método encargado de modificar una VigenciaSueldo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param vigenciasSueldos VigenciaSueldo con los cambios que se van a realizar.
     */
    public void editar(VigenciasSueldos vigenciasSueldos);
    /**
     * Método encargado de eliminar de la base de datos la VigenciaSueldo que entra por parámetro.
     * @param vigenciasSueldos VigenciaSueldo que se quiere eliminar.
     */
    public void borrar(VigenciasSueldos vigenciasSueldos);
    /**
     * Método encargado de buscar todas las VigenciasSueldos existentes en la base de datos.
     * @return Retorna una lista de VigenciasSueldos.
     */
    public List<VigenciasSueldos> buscarVigenciasSeldos();
    /**
     * Método encargado de buscar las VigenciasSueldos de un Empleado específico.
     * @param secuencia Secuencia del Empleado.
     * @return Retorna las VigenciasSueldos, odenadas descendentemente por la fechaVigencia, del Empleado cuya secuencia coincide 
     * con la secuencia dada por parámetro.
     */
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleado(BigInteger secuencia);
    /**
     * Método encargado de buscar la VigenciaSueldo con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la VigenciaSueldo que se quiere encontrar.
     * @return Retorna la VigenciaSueldo identificada con la secuencia dada por parámetro.
     */
    public VigenciasSueldos buscarVigenciasSueldosSecuencia(BigInteger secuencia);

    /**
     * Método encargado de buscar las VigenciasSueldos Actuales de un Empleado específico.
     * @param secEmpleado Secuencia del Empleado
     * @return Lista de VigenciasSueldos Actuales de un Empleado
     */
    public List<VigenciasSueldos> buscarVigenciasSueldosEmpleadoRecientes(BigInteger secEmpleado);
}

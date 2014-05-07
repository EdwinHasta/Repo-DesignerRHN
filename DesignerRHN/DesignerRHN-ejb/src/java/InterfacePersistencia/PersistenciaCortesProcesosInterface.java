/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.CortesProcesos;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'CortesProcesos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaCortesProcesosInterface {
    /**
     * Método encargado de insertar un CorteProceso en la base de datos.
     * @param corteProceso CorteProceso que se quiere crear.
     */
    public void crear(CortesProcesos corteProceso);
    /**
     * Método encargado de modificar un CorteProceso de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param corteProceso CorteProceso con los cambios que se van a realizar.
     */
    public void editar(CortesProcesos corteProceso);
    /**
     * Método encargado de eliminar de la base de datos el CorteProceso que entra por parámetro.
     * @param corteProceso CorteProceso que se quiere eliminar.
     */
    public void borrar(CortesProcesos corteProceso);
    /**
     * Método encargado de buscar el CorteProceso con el secuencia dado por parámetro.
     * @param secuencia Identificador único del CorteProceso que se quiere encontrar.
     * @return Retorna el CorteProceso identificado con el secuencia dado por parámetro.
     */
    public CortesProcesos buscarCorteProcesoSecuencia(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los CortesProcesos existentes en la base de datos.
     * @return Retorna una lista de CorteProceso.
     */
    public List<CortesProcesos> buscarCortesProcesos();
    /**
     * Método encargado de de buscar los CortesProcesos según su comprobante.
     * @param secuenciaComprobante Secuencia del comprobante por el cual se va a filtrar la consulta.
     * @return Retorna una lista de los CortesProcesos que tienen el comprobante con secuencia igual a la dada por parámetro.
     */
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante);
    /**
     * Método encargado de realizar un conteo de las liquidaciones cerradas con 
     * determinado proceso y entre las fechas dadas.
     * @param secProceso Secuencia del proceso con el que se realiza la liquidación
     * @param fechaDesde Fecha inicial del rango
     * @param fechaHasta Fecha final del rango
     * @return Retorna el numero de liquidaciones que hay en la base de datos y cumplen con los parámetros de entrada
     */
    public Integer contarLiquidacionesCerradas(BigInteger secProceso, String fechaDesde, String fechaHasta);
    /**
     * Método encargado de eliminar un comprobante que esta asociado a un determinado proceso, entre las fechas dadas.
     * @param codigoProceso Código del proceso
     * @param fechaDesde Fecha inicial del rango
     * @param fechaHasta Fecha final del rango
     */
    public void eliminarComprobante(Short codigoProceso, String fechaDesde, String fechaHasta);
    /**
     * Método encargado de buscar el comprobante más reciente del empleado.
     * @param em EntityManager que tiene la conexión.
     * @param secuenciaEmpleado Secuencia del empleado.
     * @return Retorna el comprobante reciente.
     */
    public CortesProcesos buscarComprobante (EntityManager em, BigInteger secuenciaEmpleado);
}
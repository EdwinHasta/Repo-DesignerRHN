/**
 * Documentación a cargo de AndresPineda
 */
package InterfacePersistencia;

import Entidades.OperandosLogs;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'OperandosLogs' de la base de datos.
 *
 * @author Andres Pineda.
 */
public interface PersistenciaOperandosLogsInterface {

    public List<OperandosLogs> buscarOperandosLogs();

    /**
     * Método encargado de insertar un OperandoLog en la base de datos.
     *
     * @param operandos OperandoLog que se quiere crear.
     */
    public void crear(OperandosLogs operandos);

    /**
     * Método encargado de modificar un OperandoLog de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param operandos OperandoLog con los cambios que se van a realizar.
     */
    public void editar(OperandosLogs operandos);

    /**
     * Método encargado de eliminar de la base de datos el OperandoLog que entra
     * por parámetro.
     *
     * @param operandos OperandoLog que se quiere eliminar.
     */
    public void borrar(OperandosLogs operandos);

    /**
     * Método encargado de obtener los OperandosLogs para un Proceso
     * referenciado por parametro
     *
     * @param secProceso Secuencia del Proceso
     * @return Lista de OperandosLogs
     */
    public List<OperandosLogs> buscarOperandosLogsParaProcesoSecuencia(BigInteger secProceso);
}

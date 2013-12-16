/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Soausentismos;
import java.math.BigInteger;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'Soausentismos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaSoausentismosInterface {
    /**
     * Método encargado de insertar un Soausentismo en la base de datos.
     * @param soausentismos Soausentismo que se quiere crear.
     */
    public void crear(Soausentismos soausentismos);
    /**
     * Método encargado de modificar un Soausentismo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param soausentismos Soausentismo con los cambios que se van a realizar.
     */
    public void editar(Soausentismos soausentismos);
    /**
     * Método encargado de eliminar de la base de datos el Soausentismo que entra por parámetro.
     * @param soausentismos Soausentismo que se quiere eliminar.
     */
    public void borrar(Soausentismos soausentismos);
    /**
     * Método encargado de buscar los Soausentismos (ausentismos) asociados a un empleado específico.
     * @param secuenciaEmpleado Secuencia del empleado para el cual se quieren saber los Soausentismos.
     * @return Retorna una lista de Soausentismos.
     */
    public List<Soausentismos> ausentismosEmpleado (BigInteger secuenciaEmpleado);
    /**
     * Método encargado de generar un String en formato NUMERO_CERTIFICADO: FECHA_INICIO_AUSENTISMO -> FECHA_FIN_AUSENTISMO
     * con la información del ausentismo cuya secuencia coincide con la del parámetro.
     * @param secuenciaProrroga Secuencia del Ausentismo.
     * @return Retorna un String con el formato descrito y la información del Ausentismo especificado.
     */
    public String prorrogaMostrar(BigInteger secuenciaProrroga);
    /**
     * Método encargado de buscar los Soausentismos para una lista de valores en la columna prorroga
     * de la tabla Soausentismos. Los Soausentismos que conforman esta lista de valores dependen del
     * empleado, de la causa del ausentismo y del ausentismo asociado a la prorroga.
     * (Prorroga: ausentismo asociado a un ausentismo específico).
     * @param secuenciaEmpleado Secuencia del empleado
     * @param secuenciaCausa Secuencia de la causa del ausentismo.
     * @param secuenciaAusentismo Secuencia del ausentismo asociado a la prorroga.
     * @return Retorna una lista de los Soausentismos que cumplen con las condiciones anteriores.
     */
    public List<Soausentismos> prorrogas(BigInteger secuenciaEmpleado, BigInteger secuenciaCausa, BigInteger secuenciaAusentismo);
   
}

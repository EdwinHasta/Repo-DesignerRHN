/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import java.math.BigInteger;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'RelacionesIncapacidades' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaRelacionesIncapacidadesInterface {
    /**
     * Método encargado de recuperar el año y el mes (en formato AÑO.MES) de las RelacionesIncapacidades
     * según el ausentismo al que estan asociados.
     * @param secuenciaAusentismo Secuencia del ausentismo al que están relacionadas las RelacionesIncapacidades.
     * @return Retorna una lista de Strings con el año y mes (en formato AÑO.MES) de las RelacionesIncapacidades
     * que coinciden con el ausentismo indicado por la secuencia del parámetro.
     */
    public String relaciones(BigInteger secuenciaAusentismo);
}

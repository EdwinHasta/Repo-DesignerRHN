/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.EvalResultadosConv;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'EvalResultadosConv' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaEvalResultadosConvInterface {
    /**
     * Método encargado de recuperar las ultimas EvalResultadosConv (evaluaciones) realizadas a un empleado.
     * @param secuenciaEmpleado Secuencia del empleado del que se quiere obtener la información
     * @return Retorna una lista de EvalResultadosConv con las ultimas evaluaciones realizadas al empleado.
     */
    public List<EvalResultadosConv> pruebasAplicadasPersona(BigInteger secuenciaEmpleado);
}

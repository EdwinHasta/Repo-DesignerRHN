/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.EvalEvaluadores;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'EvalEvaluadores' 
 * de la base de datos.
 * @author betelgeuse
 */
@Local
public interface PersistenciaEvalEvaluadoresInterface {
    /**
     * Método encargado de insertar un EvalEvaluador en la base de datos.
     * @param evalEvaluadores EvalEvaluador que se quiere crear.
     */
    public void crear(EvalEvaluadores evalEvaluadores);
    /**
     * Método encargado de modificar un EvalEvaluador de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param evalEvaluadores EvalEvaluador con los cambios que se van a realizar.
     */
    public void editar(EvalEvaluadores evalEvaluadores);
    /**
     * Método encargado de eliminar de la base de datos el EvalEvaluador que entra por parámetro.
     * @param evalEvaluadores EvalEvaluador que se quiere eliminar.
     */
    public void borrar(EvalEvaluadores evalEvaluadores);
    /**
     * Método encargado de buscar el EvalEvaluador con la secuencia dada por parámetro.
     * @param secuencia Identificador único del EvalEvaluador que se quiere encontrar.
     * @return Retorna el EvalEvaluador identificada con el secuencia dada por parámetro.
     */
    public EvalEvaluadores buscarEvalEvaluador(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los EvalEvaluadores existentes, ordenados por código, en la base de datos.
     * @return Retorna una lista de EvalEvaluadores.
     */
    public List<EvalEvaluadores> buscarEvalEvaluadores();
    /**
     * Método encargado de contar las relaciones de un EvalEvaluador específico con una EvalPrueba.
     * @param secuencia Secuencia del EvalEvaluador
     * @return Retorna el número de EvalPruebas cuyo EvalEvaluador tiene como secuencia el valor dado por parámetro.
     */
    public Long verificarBorradoEvalPruebas(BigInteger secuencia);
}

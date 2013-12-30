/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EvalEvaluadores;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEvalEvaluadoresInterface {

    public void crear(EvalEvaluadores evalEvaluadores);

    public void editar(EvalEvaluadores evalEvaluadores);

    public void borrar(EvalEvaluadores evalEvaluadores);

    public EvalEvaluadores buscarEvalEvaluador(BigInteger secuenciaEvalEvaluadores);

    public List<EvalEvaluadores> buscarEvalEvaluadores();

    public Long verificarBorradoEvalPruebas(BigInteger secuencia);
}

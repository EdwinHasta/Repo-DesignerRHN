/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.EvalEvaluadores;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEvalEvaluadoresInterface {

    public void modificarEvalEvaluadores(List<EvalEvaluadores> listEvalEvaluadoresModificadas);

    public void borrarEvalEvaluadores(EvalEvaluadores evalEvaluadores);

    public void crearEvalEvaluadores(EvalEvaluadores evalEvaluadores);

    public List<EvalEvaluadores> mostrarEvalEvaluadores();

    public EvalEvaluadores mostrarEvalEvaluador(BigInteger secEvalEvaluadores);

    public Long verificarBorradoEP(BigInteger secuenciaMovitoCambioCargo);
}

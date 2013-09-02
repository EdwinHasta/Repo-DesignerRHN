/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EvalResultadosConv;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaEvalResultadosConvInterface {

    public List<EvalResultadosConv> pruebasAplicadasPersona(BigInteger secuenciaEmpleado);
}

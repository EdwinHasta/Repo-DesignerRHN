/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EvalDimensiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEvalDimensionesInterface {

    public void crear(EvalDimensiones evalDimensiones);

    public void editar(EvalDimensiones evalDimensiones);

    public void borrar(EvalDimensiones evalDimensiones);

    public EvalDimensiones buscarEvalDimension(BigInteger secuencia);

    public List<EvalDimensiones> buscarEvalDimensiones();

    public BigInteger contradorEvalPlanillas(BigInteger secuencia);
}

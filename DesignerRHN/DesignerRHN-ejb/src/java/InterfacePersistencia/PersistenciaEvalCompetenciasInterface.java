/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EvalCompetencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEvalCompetenciasInterface {

    public void crear(EvalCompetencias evalCompetencias);

    public void editar(EvalCompetencias evalCompetencias);

    public void borrar(EvalCompetencias evalCompetencias);

    public EvalCompetencias buscarEvalCompetencia(BigInteger secuenciaTE);

    public List<EvalCompetencias> buscarEvalCompetencias();

    public BigInteger contadorCompetenciasCargos(BigInteger secuencia);
}

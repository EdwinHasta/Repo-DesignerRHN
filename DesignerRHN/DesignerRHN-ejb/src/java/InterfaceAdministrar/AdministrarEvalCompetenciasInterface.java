/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.EvalCompetencias;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEvalCompetenciasInterface {

    public void modificarEvalCompetencias(List<EvalCompetencias> listTiposEmpresasModificadas);

    public void borrarEvalCompetencias(EvalCompetencias tipoEmpresa);

    public void crearEvalCompetencias(EvalCompetencias tipoEmpresa);

    public List<EvalCompetencias> mostrarEvalCompetencias();

    public EvalCompetencias mostrarEvalCompetencia(BigInteger secTipoEmpresa);

    public BigInteger verificarBorradoCompetenciasCargos(BigInteger secuenciaCompetenciasCargos);
}

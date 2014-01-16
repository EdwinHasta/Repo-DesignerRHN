/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEvalCompetenciasInterface;
import Entidades.EvalCompetencias;
import InterfacePersistencia.PersistenciaEvalCompetenciasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEvalCompetencias implements AdministrarEvalCompetenciasInterface {

    @EJB
    PersistenciaEvalCompetenciasInterface persistenciaEvalCompetencias;

    @Override
    public void modificarEvalCompetencias(List<EvalCompetencias> listEvalCompetencias) {
        for (int i = 0; i < listEvalCompetencias.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEvalCompetencias.editar(listEvalCompetencias.get(i));
        }
    }

    @Override
    public void borrarEvalCompetencias(List<EvalCompetencias> listEvalCompetencias) {
        for (int i = 0; i < listEvalCompetencias.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaEvalCompetencias.borrar(listEvalCompetencias.get(i));
        }
    }

    @Override
    public void crearEvalCompetencias(List<EvalCompetencias> listEvalCompetencias) {
        for (int i = 0; i < listEvalCompetencias.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaEvalCompetencias.crear(listEvalCompetencias.get(i));
        }
    }

    @Override
    public List<EvalCompetencias> consultarEvalCompetencias() {
        List<EvalCompetencias> listEvalCompetencias;
        listEvalCompetencias = persistenciaEvalCompetencias.buscarEvalCompetencias();
        return listEvalCompetencias;
    }

    @Override
    public EvalCompetencias consultarEvalCompetencia(BigInteger secTipoEmpresa) {
        EvalCompetencias evalCompetencias;
        evalCompetencias = persistenciaEvalCompetencias.buscarEvalCompetencia(secTipoEmpresa);
        return evalCompetencias;
    }

    @Override
    public BigInteger verificarCompetenciasCargos(BigInteger secuenciaCompetenciasCargos) {
        BigInteger verificadorCompetenciasCargos = null;
        try {
            System.err.println("Secuencia Borrado Competencias Cargos" + secuenciaCompetenciasCargos);
            verificadorCompetenciasCargos = persistenciaEvalCompetencias.contadorCompetenciasCargos(secuenciaCompetenciasCargos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEvalCompetencias verificarBorradoCompetenciasCargos ERROR :" + e);
        } finally {
            return verificadorCompetenciasCargos;
        }
    }

}

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
    private EvalCompetencias evalCompetenciasSeleccionada;
    private EvalCompetencias evalCompetencias;
    private List<EvalCompetencias> listEvalCompetencias;

    public void modificarEvalCompetencias(List<EvalCompetencias> listTiposEmpresasModificadas) {
        for (int i = 0; i < listTiposEmpresasModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            evalCompetenciasSeleccionada = listTiposEmpresasModificadas.get(i);
            persistenciaEvalCompetencias.editar(evalCompetenciasSeleccionada);
        }
    }

    public void borrarEvalCompetencias(EvalCompetencias tipoEmpresa) {
        persistenciaEvalCompetencias.borrar(tipoEmpresa);
    }

    public void crearEvalCompetencias(EvalCompetencias tipoEmpresa) {
        persistenciaEvalCompetencias.crear(tipoEmpresa);
    }

    public List<EvalCompetencias> mostrarEvalCompetencias() {
        listEvalCompetencias = persistenciaEvalCompetencias.buscarEvalCompetencias();
        return listEvalCompetencias;
    }

    public EvalCompetencias mostrarEvalCompetencia(BigInteger secTipoEmpresa) {
        evalCompetencias = persistenciaEvalCompetencias.buscarEvalCompetencia(secTipoEmpresa);
        return evalCompetencias;
    }

    public BigInteger verificarBorradoCompetenciasCargos(BigInteger secuenciaCompetenciasCargos) {
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

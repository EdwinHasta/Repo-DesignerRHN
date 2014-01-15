/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEvalEvaluadoresInterface;
import Entidades.EvalEvaluadores;
import InterfacePersistencia.PersistenciaEvalEvaluadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarEvalEvaluadores implements AdministrarEvalEvaluadoresInterface {

    @EJB
    PersistenciaEvalEvaluadoresInterface persistenciaEvalEvaluadores;
    private EvalEvaluadores valEvaluadoresSeleccionado;
    private EvalEvaluadores valEvaluadores;
    private List<EvalEvaluadores> listEvalEvaluadores;

    public void modificarEvalEvaluadores(List<EvalEvaluadores> listEvalEvaluadoresModificadas) {
        for (int i = 0; i < listEvalEvaluadoresModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            valEvaluadoresSeleccionado = listEvalEvaluadoresModificadas.get(i);
            persistenciaEvalEvaluadores.editar(valEvaluadoresSeleccionado);
        }
    }

    public void borrarEvalEvaluadores(EvalEvaluadores evalEvaluadores) {
        persistenciaEvalEvaluadores.borrar(evalEvaluadores);
    }

    public void crearEvalEvaluadores(EvalEvaluadores evalEvaluadores) {
        persistenciaEvalEvaluadores.crear(evalEvaluadores);
    }

    public List<EvalEvaluadores> mostrarEvalEvaluadores() {
        listEvalEvaluadores = persistenciaEvalEvaluadores.buscarEvalEvaluadores();
        return listEvalEvaluadores;
    }

    public EvalEvaluadores mostrarEvalEvaluador(BigInteger secEvalEvaluadores) {
        valEvaluadores = persistenciaEvalEvaluadores.buscarEvalEvaluador(secEvalEvaluadores);
        return valEvaluadores;
    }

    public BigInteger verificarBorradoEP(BigInteger secuenciaMovitoCambioCargo) {
        BigInteger verificadorVP = new BigInteger("-1");
        try {
            verificadorVP = persistenciaEvalEvaluadores.verificarBorradoEvalPruebas(secuenciaMovitoCambioCargo);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarEvalEvaluadores verificarBorradoVC ERROR :" + e);
        } finally {
            return verificadorVP;
        }
    }
}

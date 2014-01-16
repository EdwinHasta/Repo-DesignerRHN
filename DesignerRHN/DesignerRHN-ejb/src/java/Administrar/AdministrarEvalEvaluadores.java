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

    @Override
    public void modificarEvalEvaluadores(List<EvalEvaluadores> listEvalEvaluadores) {
        for (int i = 0; i < listEvalEvaluadores.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaEvalEvaluadores.editar(listEvalEvaluadores.get(i));
        }
    }

    @Override
    public void borrarEvalEvaluadores(List<EvalEvaluadores> listEvalEvaluadores) {
        for (int i = 0; i < listEvalEvaluadores.size(); i++) {
            System.out.println("Administrar Borrar...");
            persistenciaEvalEvaluadores.borrar(listEvalEvaluadores.get(i));
        }
    }

    @Override
    public void crearEvalEvaluadores(List<EvalEvaluadores> listEvalEvaluadores) {
        for (int i = 0; i < listEvalEvaluadores.size(); i++) {
            System.out.println("Administrar Crear...");
            persistenciaEvalEvaluadores.crear(listEvalEvaluadores.get(i));
        }
    }

    @Override
    public List<EvalEvaluadores> consultarEvalEvaluadores() {
        listEvalEvaluadores = persistenciaEvalEvaluadores.buscarEvalEvaluadores();
        return listEvalEvaluadores;
    }

    @Override
    public EvalEvaluadores consultarEvalEvaluador(BigInteger secEvalEvaluadores) {
        valEvaluadores = persistenciaEvalEvaluadores.buscarEvalEvaluador(secEvalEvaluadores);
        return valEvaluadores;
    }

    @Override
    public BigInteger verificarEvalPruebas(BigInteger secuenciaMovitoCambioCargo) {
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

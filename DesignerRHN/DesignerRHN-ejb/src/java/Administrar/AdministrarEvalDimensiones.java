/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarEvalDimensionesInterface;
import Entidades.EvalDimensiones;
import InterfacePersistencia.PersistenciaEvalDimensionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarEvalDimensiones implements AdministrarEvalDimensionesInterface {

    @EJB
    PersistenciaEvalDimensionesInterface persistenciaTiposCentrosCostos;

    @Override
    public void modificarEvalDimensiones(List<EvalDimensiones> listaEvalDimensiones) {
        for (int i = 0; i < listaEvalDimensiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCentrosCostos.editar(listaEvalDimensiones.get(i));
        }
    }

    @Override
    public void borrarEvalDimensiones(List<EvalDimensiones> listaEvalDimensiones) {
        for (int i = 0; i < listaEvalDimensiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCentrosCostos.borrar(listaEvalDimensiones.get(i));
        }
    }

    @Override
    public void crearEvalDimensiones(List<EvalDimensiones> listaEvalDimensiones) {
        for (int i = 0; i < listaEvalDimensiones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposCentrosCostos.crear(listaEvalDimensiones.get(i));
        }
    }

    @Override
    public EvalDimensiones consultarEvalDimension(BigInteger secTipoCentrosCostos) {
        EvalDimensiones evalDimensiones;
        evalDimensiones = persistenciaTiposCentrosCostos.buscarEvalDimension(secTipoCentrosCostos);
        return evalDimensiones;
    }

    @Override
    public List<EvalDimensiones> consultarEvalDimensiones() {
        List<EvalDimensiones> listEvalDimensiones;
        listEvalDimensiones = persistenciaTiposCentrosCostos.buscarEvalDimensiones();
        return listEvalDimensiones;
    }

    @Override
    public BigInteger verificarEvalPlanillas(BigInteger secuenciaTiposAuxilios) {
        BigInteger verificarEvalPlanillas = null;
        try {
            verificarEvalPlanillas = persistenciaTiposCentrosCostos.contradorEvalPlanillas(secuenciaTiposAuxilios);
        } catch (Exception e) {
            System.err.println("ERROR ADMINISTRAREVALPLANILLAS verificarEvalPlanillas ERROR :" + e);
        } finally {
            return verificarEvalPlanillas;
        }
    }
}

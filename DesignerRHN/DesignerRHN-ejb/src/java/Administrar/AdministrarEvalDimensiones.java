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
    private EvalDimensiones evalDimensionesSeleccionado;
    private EvalDimensiones evalDimensiones;
    private List<EvalDimensiones> listEvalDimensiones;

    public void modificarEvalDimensiones(List<EvalDimensiones> listTiposEntidadesModificadas) {
        for (int i = 0; i < listTiposEntidadesModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            evalDimensionesSeleccionado = listTiposEntidadesModificadas.get(i);
            persistenciaTiposCentrosCostos.editar(evalDimensionesSeleccionado);
        }
    }

    public void borrarEvalDimensiones(EvalDimensiones tipoCentroCosto) {
        persistenciaTiposCentrosCostos.borrar(tipoCentroCosto);
    }

    public void crearEvalDimensiones(EvalDimensiones tiposCentrosCostos) {
        persistenciaTiposCentrosCostos.crear(tiposCentrosCostos);
    }

    public EvalDimensiones buscarEvalDimension(BigInteger secTipoCentrosCostos) {
        evalDimensiones = persistenciaTiposCentrosCostos.buscarEvalDimension(secTipoCentrosCostos);
        return evalDimensiones;
    }

    public List<EvalDimensiones> mostrarEvalDimensiones() {
        listEvalDimensiones = persistenciaTiposCentrosCostos.buscarEvalDimensiones();
        return listEvalDimensiones;
    }

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

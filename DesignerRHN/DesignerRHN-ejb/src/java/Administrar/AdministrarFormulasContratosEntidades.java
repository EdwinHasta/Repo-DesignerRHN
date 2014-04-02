/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Formulascontratos;
import Entidades.TiposEntidades;
import Entidades.FormulasContratosEntidades;
import InterfaceAdministrar.AdministrarFormulasContratosEntidadesInterface;
import InterfacePersistencia.PersistenciaFormulasContratosEntidadesInterface;
import InterfacePersistencia.PersistenciaFormulasContratosInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarFormulasContratosEntidades implements AdministrarFormulasContratosEntidadesInterface {
//ATRIBUTOS
    //--------------------------------------------------------------------------    

    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaFormulasContratosEntidades'.
     */
    @EJB
    PersistenciaFormulasContratosEntidadesInterface persistenciaFormulasContratosEntidades;
    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaFormulasContratosInterface persistenciaFormulas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarFormulasContratosEntidades(List<FormulasContratosEntidades> listaFormulasContratosEntidades) {
        for (int i = 0; i < listaFormulasContratosEntidades.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaFormulasContratosEntidades.editar(listaFormulasContratosEntidades.get(i));
        }
    }

    public void borrarFormulasContratosEntidades(List<FormulasContratosEntidades> listaFormulasContratosEntidades) {
        for (int i = 0; i < listaFormulasContratosEntidades.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaFormulasContratosEntidades.borrar(listaFormulasContratosEntidades.get(i));
        }
    }

    public void crearFormulasContratosEntidades(List<FormulasContratosEntidades> listaFormulasContratosEntidades) {
        for (int i = 0; i < listaFormulasContratosEntidades.size(); i++) {
            persistenciaFormulasContratosEntidades.crear(listaFormulasContratosEntidades.get(i));
        }
    }

    @Override
    public List<FormulasContratosEntidades> consultarFormulasContratosEntidades() {
        List<FormulasContratosEntidades> LOVTiposEntidades;
        return LOVTiposEntidades = persistenciaFormulasContratosEntidades.consultarFormulasContratosEntidades();
    }

    @Override
    public List<FormulasContratosEntidades> consultarFormulasContratosEntidadesPorFormulaContrato(BigInteger secFormulaContrato) {
        List<FormulasContratosEntidades> LOVTiposEntidades;
        return LOVTiposEntidades = persistenciaFormulasContratosEntidades.consultarFormulasContratosEntidadesPorFormulaContrato(secFormulaContrato);
    }

    public List<TiposEntidades> consultarLOVTiposEntidades() {
        List<TiposEntidades> LOVTiposEntidades;
        return LOVTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
    }

    @Override
    public Formulascontratos consultarFormulaDeFormulasContratosEntidades(BigInteger secFormulaContrato) {
        Formulascontratos formulaConcepto = persistenciaFormulas.formulasContratosParaContratoFormulasContratosEntidades(secFormulaContrato);
        return formulaConcepto;
    }

}

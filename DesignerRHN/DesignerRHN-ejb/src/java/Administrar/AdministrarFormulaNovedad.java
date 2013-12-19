package Administrar;

import Entidades.Formulas;
import Entidades.FormulasNovedades;
import InterfaceAdministrar.AdministrarFormulaNovedadInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaFormulasNovedadesInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarFormulaNovedad implements AdministrarFormulaNovedadInterface {

    @EJB
    PersistenciaFormulasNovedadesInterface persistenciaFormulasNovedades;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;

    @Override
    public List<FormulasNovedades> listFormulasNovedadesParaFormula(BigInteger secuencia) {
        try {
            List<FormulasNovedades> lista = persistenciaFormulasNovedades.formulasNovedadesParaFormulaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulasNovedadesParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearFormulasNovedades(List<FormulasNovedades> listFN) {
        try {
            for (int i = 0; i < listFN.size(); i++) {
                persistenciaFormulasNovedades.crear(listFN.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasNovedades Admi : " + e.toString());
        }
    }

    @Override
    public void editarFormulasNovedades(List<FormulasNovedades> listFN) {
        try {
            for (int i = 0; i < listFN.size(); i++) {
                persistenciaFormulasNovedades.editar(listFN.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasNovedades Admi : " + e.toString());
        }
    }

    @Override
    public void borrarFormulasNovedades(List<FormulasNovedades> listFN) {
        try {
            for (int i = 0; i < listFN.size(); i++) {
                persistenciaFormulasNovedades.borrar(listFN.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarFormulasNovedades Admi : " + e.toString());
        }
    }

    @Override
    public List<Formulas> listFormulas(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            List<Formulas> lista = new ArrayList<Formulas>();
            lista.add(form);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Formulas formulaActual(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            return form;
        } catch (Exception e) {
            return null;
        }
    }

}

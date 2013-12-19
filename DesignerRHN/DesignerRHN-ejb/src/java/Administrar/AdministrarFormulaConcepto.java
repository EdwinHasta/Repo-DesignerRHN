package Administrar;

import Entidades.Conceptos;
import Entidades.Formulas;
import Entidades.FormulasConceptos;
import InterfaceAdministrar.AdministrarFormulaConceptoInterface;
import InterfacePersistencia.PersistenciaConceptosInterface;
import InterfacePersistencia.PersistenciaFormulasConceptosInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarFormulaConcepto implements AdministrarFormulaConceptoInterface{

    @EJB
    PersistenciaFormulasConceptosInterface persistenciaFormulasConceptos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    @EJB
    PersistenciaConceptosInterface persistenciaConceptos;

    @Override
    public List<FormulasConceptos> formulasConceptosParaFormula(BigInteger secuencia) {
        try {
            List<FormulasConceptos> lista = persistenciaFormulasConceptos.formulasConceptosParaFormulaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error formulasConceptosParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearFormulasConceptos(List<FormulasConceptos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaFormulasConceptos.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void editarFormulasConceptos(List<FormulasConceptos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaFormulasConceptos.editar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public void borrarFormulasConceptos(List<FormulasConceptos> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaFormulasConceptos.borrar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarFormulasConceptos Admi : " + e.toString());
        }
    }

    @Override
    public List<FormulasConceptos> listFormulasConceptos() {
        try {
            List<FormulasConceptos> lista = persistenciaFormulasConceptos.buscarFormulasConceptos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listFormulasConceptos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Conceptos> listConceptos() {
        try {
            List<Conceptos> lista = persistenciaConceptos.buscarConceptos();
            return lista;
        } catch (Exception e) {
            System.out.println("Error listConceptos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Formulas formulaActual(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            return form;
        } catch (Exception e) {
            System.out.println("Error formulaActual Admi : " + e.toString());
            return null;
        }
    }

}

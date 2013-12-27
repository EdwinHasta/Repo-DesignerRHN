package Administrar;

import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
import InterfaceAdministrar.AdministrarHistoriaFormulaInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import InterfacePersistencia.PersistenciaNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
/**
 *
 * @author PROYECTO01
 */
@Stateless
public class AdministrarHistoriaFormula implements AdministrarHistoriaFormulaInterface {

    @EJB
    PersistenciaHistoriasformulasInterface persistenciaHistoriasFormulas;
    @EJB
    PersistenciaNodosInterface persistenciaNodos;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;

    @Override
    public List<Historiasformulas> listHistoriasFormulasParaFormula(BigInteger secuencia) {
        try {
            List<Historiasformulas> lista = persistenciaHistoriasFormulas.historiasFormulasParaFormulaSecuencia(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listHistoriasFormulasParaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.crear(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error crearHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public void editarHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.editar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error editarHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public void borrarHistoriasFormulas(List<Historiasformulas> lista) {
        try {
            for (int i = 0; i < lista.size(); i++) {
                persistenciaHistoriasFormulas.borrar(lista.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarHistoriasFormulas Admi : " + e.toString());
        }
    }

    @Override
    public List<Nodos> listNodosDeHistoriaFormula(BigInteger secuencia) {
        try {
            List<Nodos> lista = persistenciaNodos.buscarNodosPorSecuenciaHistoriaFormula(secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error listNodosDeHistoriaFormula Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Formulas actualFormula(BigInteger secuencia) {
        try {
            Formulas form = persistenciaFormulas.buscarFormula(secuencia);
            return form;
        } catch (Exception e) {
            System.out.println("Error actualFormula Admi : " + e.toString());
            return null;
        }
    }
}

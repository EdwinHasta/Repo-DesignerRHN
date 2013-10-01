package Administrar;

import Entidades.Formulas;
import InterfaceAdministrar.AdministrarFormulaInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarFormula implements AdministrarFormulaInterface {

    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;

    @Override
    public List<Formulas> formulas() {
        return persistenciaFormulas.lovFormulas();
    }

    @Override
    public void modificar(List<Formulas> listFormulasModificadas) {
        for (int i = 0; i < listFormulasModificadas.size(); i++) {
            System.out.println("Modificando...");
            if (listFormulasModificadas.get(i).isPeriodicidadFormula() == true) {
                listFormulasModificadas.get(i).setPeriodicidadindependiente("S");
            } else if (listFormulasModificadas.get(i).isPeriodicidadFormula() == false) {
                listFormulasModificadas.get(i).setPeriodicidadindependiente("N");
            }
            persistenciaFormulas.editar(listFormulasModificadas.get(i));
        }
    }

    @Override
    public void borrar(Formulas formula) {
        persistenciaFormulas.borrar(formula);
    }

    @Override
    public void crear(Formulas formula) {
        persistenciaFormulas.crear(formula);
    }

    @Override
    public void clonarFormula(String nombreCortoOrigen, String nombreCortoClon, String nombreLargoClon, String observacionClon) {
        persistenciaFormulas.clonarFormulas(nombreCortoOrigen, nombreCortoClon, nombreLargoClon, observacionClon);
    }

    @Override
    public void operandoFormula(BigInteger secFormula) {
        persistenciaFormulas.oprandoFormulas(secFormula);
    }
}

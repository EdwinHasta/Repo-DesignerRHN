package InterfaceAdministrar;

import Entidades.Formulas;
import java.math.BigInteger;
import java.util.List;

public interface AdministrarFormulaInterface {

    public List<Formulas> formulas();

    public void modificar(List<Formulas> listFormulasModificadas);

    public void borrar(Formulas formula);

    public void crear(Formulas formula);

    public void clonarFormula(String nombreCortoOrigen, String nombreCortoClon, String nombreLargoClon, String observacionClon);

    public void operandoFormula(BigInteger secFormula);

    public Formulas buscarFormulaSecuencia(BigInteger secuencia);
}

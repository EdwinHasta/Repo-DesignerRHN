/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Formulas;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaFormulasInterface {
    public void crear(Formulas formulas);
    public void editar(Formulas formulas);
    public void borrar(Formulas formulas);
    public Formulas buscarFormula(Object id);
    public List<Formulas> buscarFormulas();
    public List<Formulas> buscarFormulasCarge();
    public Formulas buscarFormulaCargeInicial();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.Formulas;
import Entidades.FormulasConceptos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarFormulaConceptoInterface {

    public List<FormulasConceptos> formulasConceptosParaFormula(BigInteger secuencia);

    public void crearFormulasConceptos(List<FormulasConceptos> lista);

    public void editarFormulasConceptos(List<FormulasConceptos> lista);

    public void borrarFormulasConceptos(List<FormulasConceptos> lista);

    public List<FormulasConceptos> listFormulasConceptos();

    public List<Conceptos> listConceptos();

    public Formulas formulaActual(BigInteger secuencia);

}

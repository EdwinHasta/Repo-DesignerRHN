/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Formulas;
import Entidades.FormulasNovedades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarFormulaNovedadInterface {

    public List<FormulasNovedades> listFormulasNovedadesParaFormula(BigInteger secuencia);

    public void crearFormulasNovedades(List<FormulasNovedades> listFN);

    public void editarFormulasNovedades(List<FormulasNovedades> listFN);

    public void borrarFormulasNovedades(List<FormulasNovedades> listFN);

    public List<Formulas> listFormulas(BigInteger secuencia);

    public Formulas formulaActual(BigInteger secuencia);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarHistoriaFormulaInterface {

    public List<Historiasformulas> listHistoriasFormulasParaFormula(BigInteger secuencia);

    public void crearHistoriasFormulas(List<Historiasformulas> lista);

    public void editarHistoriasFormulas(List<Historiasformulas> lista);

    public void borrarHistoriasFormulas(List<Historiasformulas> lista);

    public List<Nodos> listNodosDeHistoriaFormula(BigInteger secuencia);
    
    public Formulas actualFormula(BigInteger secuencia);

}

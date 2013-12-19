/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.FormulasProcesos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaFormulasProcesosInterface {

    public void crear(FormulasProcesos formulasProcesos);

    public void editar(FormulasProcesos formulasProcesos);

    public void borrar(FormulasProcesos formulasProcesos);

    public List<FormulasProcesos> formulasProcesosParaFormulaSecuencia(BigInteger secuencia);
}

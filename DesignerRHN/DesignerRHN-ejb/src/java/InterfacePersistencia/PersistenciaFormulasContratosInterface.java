/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Formulascontratos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaFormulasContratosInterface {

    public void crear(Formulascontratos formulascontratos);

    public void editar(Formulascontratos formulascontratos);

    public void borrar(Formulascontratos formulascontratos);

    public List<Formulascontratos> formulasContratosParaFormulaSecuencia(BigInteger secuencia);

}

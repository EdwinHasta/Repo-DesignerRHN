/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Contratos;
import Entidades.Formulas;
import Entidades.Formulascontratos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface AdministrarDetalleLegislacionInterface {

    public List<Terceros> listTerceros();

    public List<Periodicidades> listPeriodicidades();

    public List<Formulas> listFormulas();

    public List<Formulascontratos> listFormulasContratosParaFormula(BigInteger secuencia);

    public void crearFormulaContrato(List<Formulascontratos> listaFC);

    public void borrarrFormulaContrato(List<Formulascontratos> listaFC);

    public void editarFormulaContrato(List<Formulascontratos> listaFC);

    public Contratos contratoActual(BigInteger secuencia);

}

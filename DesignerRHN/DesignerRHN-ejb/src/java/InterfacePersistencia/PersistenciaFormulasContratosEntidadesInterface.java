/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.FormulasContratosEntidades;
import Entidades.Formulascontratos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaFormulasContratosEntidadesInterface {

    public void crear(FormulasContratosEntidades formulasAseguradas);

    public void editar(FormulasContratosEntidades formulasAseguradas);

    public void borrar(FormulasContratosEntidades formulasAseguradas);

    public List<FormulasContratosEntidades> consultarFormulasContratosEntidades();

    public FormulasContratosEntidades consultarFormulaContratoEntidad(BigInteger secuencia);


    public List<FormulasContratosEntidades> consultarFormulasContratosEntidadesPorFormulaContrato(BigInteger secFormulaContrato);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.FormulasAseguradas;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaFormulasAseguradasInterface {

    public void crear(FormulasAseguradas formulasAseguradas);

    public void editar(FormulasAseguradas formulasAseguradas);

    public void borrar(FormulasAseguradas formulasAseguradas);

    public List<FormulasAseguradas> consultarFormulasAseguradas();

    public FormulasAseguradas consultarFormulaAsegurada(BigInteger secuencia);
}

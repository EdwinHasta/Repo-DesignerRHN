/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ValoresConceptos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaValoresConceptosInterface {

    public void crear(ValoresConceptos valoresConceptos);

    public void editar(ValoresConceptos valoresConceptos);

    public void borrar(ValoresConceptos valoresConceptos);

    public List<ValoresConceptos> consultarValoresConceptos();

    public ValoresConceptos consultarValorConcepto(BigInteger secuencia);
     public BigInteger consultarConceptoValorConcepto(BigInteger concepto);
}

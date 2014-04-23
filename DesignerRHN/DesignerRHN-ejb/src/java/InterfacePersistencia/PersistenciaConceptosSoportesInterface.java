/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ConceptosSoportes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaConceptosSoportesInterface {

    public void crear(ConceptosSoportes conceptosSoportes);

    public void editar(ConceptosSoportes conceptosSoportes);

    public void borrar(ConceptosSoportes conceptosSoportes);

    public List<ConceptosSoportes> consultarConceptosSoportes();

    public ConceptosSoportes consultarConceptoSoporte(BigInteger secuencia);

    public BigInteger consultarConceptoSoporteConceptoOperador(BigInteger concepto, BigInteger operador);
}
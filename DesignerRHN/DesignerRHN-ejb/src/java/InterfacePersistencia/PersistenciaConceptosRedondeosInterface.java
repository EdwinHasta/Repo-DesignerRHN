/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ConceptosRedondeos;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaConceptosRedondeosInterface {

    public List<ConceptosRedondeos> buscarConceptosRedondeos();

    public void crear(ConceptosRedondeos conceptosRedondeos);

    public void editar(ConceptosRedondeos conceptosRedondeos);

    public void borrar(ConceptosRedondeos conceptosRedondeos);
}

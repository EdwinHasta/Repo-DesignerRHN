/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.ConceptosRedondeos;
import Entidades.TiposRedondeos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarConceptosRedondeosInterface {

    public void borrarConceptosRedondeos(ConceptosRedondeos conceptosRedondeos);

    public void crearConceptosRedondeos(ConceptosRedondeos conceptosRedondeos);

    public void modificarConceptosRedondeos(List<ConceptosRedondeos> listaConceptosRedondeosModificar);

    public List<ConceptosRedondeos> consultarConceptosRedondeos();

    public List<Conceptos> lovConceptos();

    public List<TiposRedondeos> lovTiposRedondeos();

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ConceptosJuridicos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaConceptosJuridicosInterface {

    public void crear(ConceptosJuridicos conceptosJuridicos);

    public void editar(ConceptosJuridicos conceptosJuridicos);

    public void borrar(ConceptosJuridicos conceptosJuridicos);

    public ConceptosJuridicos buscarConceptoJuridico(Object id);

    public List<ConceptosJuridicos> buscarConceptosJuridicos();

    public ConceptosJuridicos buscarConceptosJuridicosSecuencia(BigInteger secuencia);

    public List<ConceptosJuridicos> buscarConceptosJuridicosEmpresa(BigInteger secuencia);

}

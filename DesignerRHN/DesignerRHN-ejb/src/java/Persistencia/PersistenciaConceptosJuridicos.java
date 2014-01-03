/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */

package Persistencia;

import Entidades.ConceptosJuridicos;
import InterfacePersistencia.PersistenciaConceptosJuridicosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ConceptosJuridicos' 
 * de la base de datos.
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaConceptosJuridicos implements PersistenciaConceptosJuridicosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ConceptosJuridicos conceptosJuridicos) {
        try {
            em.persist(conceptosJuridicos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaConceptosJuridicos : " + e.toString());
        }
    }

    @Override
    public void editar(ConceptosJuridicos conceptosJuridicos) {
        try {
            em.merge(conceptosJuridicos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaConceptosJuridicos : " + e.toString());
        }
    }

    @Override
    public void borrar(ConceptosJuridicos conceptosJuridicos) {
        try {
            em.remove(em.merge(conceptosJuridicos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaConceptosJuridicos : " + e.toString());
        }
    }

    @Override
    public List<ConceptosJuridicos> buscarConceptosJuridicos() {
        try {
            List<ConceptosJuridicos> conceptosJuridicos = (List<ConceptosJuridicos>) em.createNamedQuery("ConceptosJuridicos.findAll").getResultList();
            return conceptosJuridicos;
        } catch (Exception e) {
            System.out.println("Error buscarConceptosJuridicos PersistenciaConceptosJuridicos : " + e.toString());
            return null;
        }
    }

    @Override
    public ConceptosJuridicos buscarConceptosJuridicosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cj FROM ConceptosJuridicos cj WHERE cj.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ConceptosJuridicos conceptosJuridicos = (ConceptosJuridicos) query.getSingleResult();
            return conceptosJuridicos;
        } catch (Exception e) {
            System.out.println("Error buscarConceptosJuridicosSecuencia PersistenciaConceptosJuridicos : " + e.toString());
            ConceptosJuridicos conceptosJuridicos = null;
            return conceptosJuridicos;
        }
    }

    @Override
    public List<ConceptosJuridicos> buscarConceptosJuridicosEmpresa(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cj FROM ConceptosJuridicos cj WHERE cj.empresa.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            List<ConceptosJuridicos> conceptosJuridicos = (List<ConceptosJuridicos>) query.getResultList();
            return conceptosJuridicos;
        } catch (Exception e) {
            System.out.println("Error buscarConceptosJuridicosEmpresa PersistenciaConceptosJuridicos : " + e.toString());
            List<ConceptosJuridicos> conceptosJuridicos = null;
            return conceptosJuridicos;
        }
    }
}

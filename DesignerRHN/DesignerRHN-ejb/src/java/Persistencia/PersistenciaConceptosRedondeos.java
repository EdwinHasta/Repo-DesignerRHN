/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ConceptosRedondeos;
import InterfacePersistencia.PersistenciaConceptosRedondeosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'ConceptosRedondeos' de la base
 * de datos.
 *
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaConceptosRedondeos implements PersistenciaConceptosRedondeosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public List<ConceptosRedondeos> buscarConceptosRedondeos(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ConceptosRedondeos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarConceptosRedondeos PersistenciaConceptosRedondeos : " + e.toString());
            return null;
        }
    }

    @Override
    public void crear(EntityManager em,ConceptosRedondeos conceptosRedondeos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conceptosRedondeos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaConceptosRedondeos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em,ConceptosRedondeos conceptosRedondeos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(conceptosRedondeos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaConceptosRedondeos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em,ConceptosRedondeos conceptosRedondeos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(conceptosRedondeos));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaConceptosRedondeos.borrar: " + e);
            }
        }
    }

}

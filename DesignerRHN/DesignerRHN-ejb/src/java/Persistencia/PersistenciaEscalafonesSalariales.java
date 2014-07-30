/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.EscalafonesSalariales;
import InterfacePersistencia.PersistenciaEscalafonesSalarialesInterface;
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
 * Clase encargada de realizar operaciones sobre la tabla
 * 'EscalafonesSalariales' de la base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaEscalafonesSalariales implements PersistenciaEscalafonesSalarialesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,EscalafonesSalariales escalafonesSalariales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(escalafonesSalariales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEscalafonesSalariales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em,EscalafonesSalariales escalafonesSalariales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(escalafonesSalariales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEscalafonesSalariales.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em,EscalafonesSalariales escalafonesSalariales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(escalafonesSalariales);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error PersistenciaEscalafonesSalariales.borrar: " + e);
        }
    }

    @Override
    public List<EscalafonesSalariales> buscarEscalafones(EntityManager em) {
        em.clear();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(EscalafonesSalariales.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public EscalafonesSalariales buscarEscalafonSecuencia(EntityManager em,BigInteger secEscalafon) {
        try {
            em.clear();
            Query query = em.createNamedQuery("SELECT e FROM EscalafonesSalariales e WHERE e.secuencia=:secuencia");
            query.setParameter("secuencia", secEscalafon);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            EscalafonesSalariales escalafonesSalariales = (EscalafonesSalariales) query.getSingleResult();
            return escalafonesSalariales;
        } catch (Exception e) {
            System.err.println("Error buscarEscalafonSecuencia PersistenciaEscalafonesSalariales : "+e.toString());
            return null;
        }
    }
}

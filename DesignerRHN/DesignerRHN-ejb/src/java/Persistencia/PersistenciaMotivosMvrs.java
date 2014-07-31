/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Motivosmvrs;
import InterfacePersistencia.PersistenciaMotivosMvrsInterface;
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
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosMvrs' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosMvrs implements PersistenciaMotivosMvrsInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Motivosmvrs motivosMvrs) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosMvrs);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosMvrs.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Motivosmvrs motivosMvrs) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosMvrs);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosMvrs.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Motivosmvrs motivosMvrs) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(motivosMvrs));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosMvrs.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public Motivosmvrs buscarMotivosMvrs(EntityManager em, BigInteger secuenciaMM) {
        try {
            em.clear();
            return em.find(Motivosmvrs.class, secuenciaMM);
        } catch (Exception e) {
            return null;
        }
    }

    //@Override
    public List<Motivosmvrs> buscarMotivosMvrs(EntityManager em) {
        em.clear();
        Query query = em.createQuery("SELECT m FROM Motivosmvrs m");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Motivosmvrs> lista = query.getResultList();
        return lista;
    }
}

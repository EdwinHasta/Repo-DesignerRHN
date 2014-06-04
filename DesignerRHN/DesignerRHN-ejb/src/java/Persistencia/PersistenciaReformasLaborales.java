/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ReformasLaborales;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ReformasLaborales' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaReformasLaborales implements PersistenciaReformasLaboralesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, ReformasLaborales reformaLaboral) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(reformaLaboral);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaReformasLaborales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, ReformasLaborales reformaLaboral) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(reformaLaboral);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaReformasLaborales.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, ReformasLaborales reformaLaboral) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(reformaLaboral));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaReformasLaborales.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<ReformasLaborales> buscarReformasLaborales(EntityManager em) {
        Query query = em.createQuery("SELECT e FROM ReformasLaborales e ORDER BY e.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<ReformasLaborales> reformaLista = (List<ReformasLaborales>) query.getResultList();
        return reformaLista;
    }

    @Override
    public ReformasLaborales buscarReformaSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT e FROM ReformasLaborales e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ReformasLaborales reformaL = (ReformasLaborales) query.getSingleResult();
            return reformaL;
        } catch (Exception e) {
        }
        ReformasLaborales reformaL = null;
        return reformaL;
    }
}

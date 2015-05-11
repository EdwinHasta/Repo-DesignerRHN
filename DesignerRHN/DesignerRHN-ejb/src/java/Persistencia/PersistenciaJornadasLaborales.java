/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.JornadasLaborales;
import InterfacePersistencia.PersistenciaJornadasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'JornadasLaborales' de
 * la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaJornadasLaborales implements PersistenciaJornadasLaboralesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, JornadasLaborales jornadasLaborales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(jornadasLaborales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaJornadasLaborales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, JornadasLaborales jornadasLaborales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(jornadasLaborales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaJornadasLaborales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, JornadasLaborales jornadasLaborales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(jornadasLaborales));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaJornadasLaborales.borrar: " + e);
            }
        }
    }

    @Override
    public List<JornadasLaborales> buscarJornadasLaborales(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT j FROM JornadasLaborales j");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<JornadasLaborales> jornadasLaborales = (List<JornadasLaborales>) query.getResultList();
            return jornadasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarJornadasLaborales PersistenciaJornadasLaborales");
            return null;
        }
    }

    @Override
    public JornadasLaborales buscarJornadaLaboralSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT jl FROM JornadasLaborales jl WHERE jl.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            JornadasLaborales jornadasLaborales = (JornadasLaborales) query.getSingleResult();
            return jornadasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarJornadaLaboralSecuencia PersistenciaJornadasLaborales");
            JornadasLaborales jornadasLaborales = null;
            return jornadasLaborales;
        }

    }
}

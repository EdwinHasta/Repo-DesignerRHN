/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Clasesausentismos;
import InterfacePersistencia.PersistenciaClasesAusentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ClasesAusentismos' de
 * la base de datos
 *
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaClasesAusentismos implements PersistenciaClasesAusentismosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Clasesausentismos clasesAusentismos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(clasesAusentismos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaClasesAusentismos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Clasesausentismos clasesAusentismos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(clasesAusentismos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaClasesAusentismos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Clasesausentismos clasesAusentismos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(clasesAusentismos));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaClasesAusentismos.borrar: " + e);
            }
        }
    }

    @Override
    public List<Clasesausentismos> buscarClasesAusentismos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ca FROM Clasesausentismos ca ORDER BY ca.codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Clasesausentismos> todasClasesAusentismos = query.getResultList();
            return todasClasesAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }

    public BigInteger contadorSoAusentismosClaseAusentismo(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createNativeQuery("SELECT COUNT(*)FROM soausentismos WHERE clase = ?");
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaClasesAusentismos contadorSoAusentismosClaseAusentismo  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaClasesAusentismos  contadorSoAusentismosClaseAusentismo. " + e);
            return retorno;
        }
    }

    public BigInteger contadorCausasAusentismosClaseAusentismo(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            Query query = em.createNativeQuery("SELECT COUNT(*)FROM causasausentismos WHERE clase = ?");
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaClasesAusentismos contadorCausasAusentismosClaseAusentismo  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaClasesAusentismos  contadorCausasAusentismosClaseAusentismo. " + e);
            return retorno;
        }
    }
}

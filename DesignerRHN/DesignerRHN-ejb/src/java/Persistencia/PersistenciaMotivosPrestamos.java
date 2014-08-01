/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaMotivosPrestamosInterface;
import Entidades.MotivosPrestamos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosPrestamos' de
 * la base de datos.
 *
 * @author John Pineda.
 */
@Stateless
public class PersistenciaMotivosPrestamos implements PersistenciaMotivosPrestamosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, MotivosPrestamos motivosPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosPrestamos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosPrestamos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, MotivosPrestamos motivosPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(motivosPrestamos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosPrestamos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, MotivosPrestamos motivosPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(motivosPrestamos));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosPrestamos.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public MotivosPrestamos buscarMotivoPrestamo(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            return em.find(MotivosPrestamos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MotivosPrestamos> buscarMotivosPrestamos(EntityManager em) {
        em.clear();
        Query query = em.createQuery("SELECT m FROM MotivosPrestamos m ORDER BY m.codigo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<MotivosPrestamos> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    @Override
    public BigInteger contadorEersPrestamos(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            em.clear();
            String sqlQuery = "SELECT COUNT(*)FROM eersprestamos eer WHERE eer.motivoprestamo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAMOTIVOSPRESTAMOS CONTADOREERSPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}

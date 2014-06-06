/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Comprobantes;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Comprobantes' de la
 * base de datos
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaComprobantes implements PersistenciaComprobantesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Comprobantes comprobante) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(comprobante);
            tx.commit();
        } catch (Exception e) {
            System.out.println("El comprobante no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void editar(EntityManager em, Comprobantes comprobante) {

        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(comprobante);
            tx.commit();
        } catch (Exception e) {
            System.out.println("El comprobante no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Comprobantes comprobante) {

        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(comprobante));
            tx.commit();
        } catch (Exception e) {
            System.out.println("El comprobante no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public Comprobantes buscarComprobanteSecuencia(EntityManager em, BigInteger secuencia) {
        return em.find(Comprobantes.class, secuencia);
    }

    @Override
    public List<Comprobantes> buscarComprobantes(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Comprobantes.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Comprobantes> comprobantesEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT c FROM Comprobantes c WHERE c.empleado.secuencia = :secuenciaEmpleado ORDER BY c.numero DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Comprobantes> listComprobantes = query.getResultList();
            return listComprobantes;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaComprobantes.comprobantesEmpleado)" + e);
            return null;
        }
    }

    @Override
    public BigInteger numeroMaximoComprobante(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT MAX(c.numero) FROM Comprobantes c");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            BigInteger max = (BigInteger) query.getSingleResult();
            return max;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaComprobantes.numeroMaximoComprobante)" + e);
            return null;
        }
    }

    @Override
    public BigInteger buscarValorNumeroMaximo(EntityManager em) {
        try {
            String sql = "SELECT nvl(MAX(NUMERO),0) FROM COMPROBANTES";
            Query query = em.createNativeQuery(sql);
            BigInteger valor = (BigInteger) query.getSingleResult();
            return valor;
        } catch (Exception e) {
            System.out.println("Error buscarValorNumeroMaximo PersistenciaComprobantes : " + e.toString());
            return null;
        }
    }

    @Override
    public Comprobantes buscarComprobanteParaPrimerRegistroEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT c FROM Comprobantes c WHERE c.empleado.secuencia=:secEmpleado");
            query.setParameter("secEmpleado", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Comprobantes comprobante = (Comprobantes) query.getSingleResult();
            return comprobante;
        } catch (Exception e) {
            System.out.println("Error buscarComprobanteParaPrimerRegistroEmpleado PersistenciaComprobantes : " + e.toString());
            return null;
        }
    }
}

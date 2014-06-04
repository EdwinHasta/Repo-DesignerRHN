/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.OperandosLogs;
import InterfacePersistencia.PersistenciaOperandosLogsInterface;
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
 * Clase encargada de realizar operaciones sobre la tabla 'OperandosLogs' de la
 * base de datos.
 *
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaOperandosLogs implements PersistenciaOperandosLogsInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, OperandosLogs operandos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(operandos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaOperandosLogs.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, OperandosLogs operandos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(operandos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaOperandosLogs.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, OperandosLogs operandos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(operandos));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaOperandosLogs.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<OperandosLogs> buscarOperandosLogs(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OperandosLogs.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarOperandos PersistenciaOperandosLogs : " + e.toString());
            return null;
        }
    }

    @Override
    public List<OperandosLogs> buscarOperandosLogsParaProcesoSecuencia(EntityManager em, BigInteger secProceso) {
        try {
            Query query = em.createQuery("SELECT ol FROM OperandosLogs ol WHERE ol.proceso.secuencia=:secProceso");
            query.setParameter("secProceso", secProceso);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<OperandosLogs> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;
        } catch (Exception e) {
            System.out.println("Error buscarOperandosLogsParaProcesoSecuencia (PersistenciaOperandosLogs): " + e.toString());
            return null;
        }
    }
}

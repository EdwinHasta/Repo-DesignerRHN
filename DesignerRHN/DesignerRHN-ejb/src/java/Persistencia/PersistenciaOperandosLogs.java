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
    public void crear(EntityManager em, OperandosLogs operandos) {
        try {
            em.persist(operandos);
        } catch (Exception e) {
            System.out.println("El Operandos no exite o esta reservada por lo cual no puede ser modificada (PersistenciaOperandosLogs) : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, OperandosLogs operandos) {
        try {
            em.merge(operandos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el OperandoLog (PersistenciaOperandosLogs) : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, OperandosLogs operandos) {
        try {
            em.remove(em.merge(operandos));
        } catch (Exception e) {
            System.out.println("El OperandoLog no se ha podido eliminar (PersistenciaOperandosLogs) : " + e.toString());
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

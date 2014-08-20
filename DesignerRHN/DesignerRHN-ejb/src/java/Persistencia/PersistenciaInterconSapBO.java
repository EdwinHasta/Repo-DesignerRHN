
package Persistencia;

import Entidades.InterconSapBO;
import InterfacePersistencia.PersistenciaInterconSapBOInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
@Stateless

public class PersistenciaInterconSapBO implements PersistenciaInterconSapBOInterface{

   @Override
    public void crear(EntityManager em, InterconSapBO interconSapBO) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconSapBO);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconSapBO.crear: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, InterconSapBO interconSapBO) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconSapBO);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconSapBO.editar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, InterconSapBO interconSapBO) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(interconSapBO));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconSapBO.borrar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public InterconSapBO buscarInterconSAPBOSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT i FROM InterconSapBO i WHERE i.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            InterconSapBO interconTotal = (InterconSapBO) query.getSingleResult();
            return interconTotal;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconSapBO.buscarInterconSAPBOSecuencia: " + e.toString());
            return null;
        }
    }   
    
    @Override
    public List<InterconSapBO> buscarInterconSAPBOParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            String sql = "select * from INTERCON_SAPBO i where fechacontabilizacion between \n" +
            " ? and ? and FLAG = 'CONTABILIZADO' AND SALIDA <> 'NETO'\n" +
            " and exists (select @'x' from empleados e where e.secuencia=i.empleado)";
            Query query = em.createNativeQuery(sql, InterconSapBO.class);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<InterconSapBO> intercon = query.getResultList();
            return intercon;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconSapBO.buscarInterconSAPBOParametroContable: " + e.toString());
            return null;
        }
    }
}

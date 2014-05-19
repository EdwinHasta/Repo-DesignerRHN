/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import Entidades.VigenciasCargos;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasCargos' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasCargos implements PersistenciaVigenciasCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager emr;*/
    //private UserTransaction utx;
    @Override
    public void crear(EntityManager em, VigenciasCargos vigenciasCargos) {
        try {
            //UserTransaction ut = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            System.out.println("0");
            //ut.setTransactionTimeout(300);
            System.out.println("1");
            em.setFlushMode(FlushModeType.COMMIT);
            em.getTransaction().begin();
            System.out.println("2");
            //em.joinTransaction();
            System.out.println("3");
            System.out.println("Empleado: " + vigenciasCargos.getEmpleado().getSecuencia());
            //System.out.println("Empleado pro: " + vigenciasCargos.getEmpleadojefe().getSecuencia());
            // vigenciasCargos.setEmpleadojefe(null);
            System.out.println("4");
            em.merge(vigenciasCargos);
            em.flush();
            em.clear();
            System.out.println("5");
            //em.getTransaction().commit();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                //utx.rollback();
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
            //emr.getTransaction().rollback();
        } /*finally {
         em.close();
         }*/

    }

    @Override
    public void editar(EntityManager em, VigenciasCargos vigenciasCargos) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasCargos);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("ALERTA! Error xD");
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasCargos vigenciasCargos) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasCargos));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error Persistencia Borrar VC: " + e);
        }
    }

    @Override
    public VigenciasCargos buscarVigenciaCargo(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(VigenciasCargos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasCargos> buscarVigenciasCargos(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasCargos.class));
        return em.createQuery(cq).getResultList();
    }

    public List<VigenciasCargos> buscarVigenciasCargosEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createNamedQuery("Empleados.findBySecuencia");
            query.setParameter("secuencia", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            Query query2 = em.createNamedQuery("VigenciasCargos.findByEmpleado");
            query2.setParameter("empleado", empleado);
            query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasCargos> vigenciasCargos = (List<VigenciasCargos>) query2.getResultList();
            return vigenciasCargos;
        } catch (Exception e) {
            List<VigenciasCargos> vigenciasCargos = null;
            return vigenciasCargos;
        }
    }
}

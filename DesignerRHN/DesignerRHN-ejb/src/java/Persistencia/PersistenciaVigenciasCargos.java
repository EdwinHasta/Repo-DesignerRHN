/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Empleados;
import Entidades.VigenciasCargos;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

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
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            System.out.println("vigenciasCargos Persona Nombre: " + vigenciasCargos.getEmpleado().getPersona().getNombre());
            System.out.println("vigenciasCargos Persona Secuencia: " + vigenciasCargos.getEmpleado().getPersona().getSecuencia());
            System.out.println("vigenciasCargos Empleado Secuencia: " + vigenciasCargos.getEmpleado().getSecuencia());
            tx.begin();
            System.out.println("TX Begin");
            em.merge(vigenciasCargos);
            System.out.println("Persist");
            tx.commit();
            System.out.println("commitea");
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCargos.crear: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasCargos vigenciasCargos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasCargos);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Error PersistenciaVigenciasCargos.editar: " + e);
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasCargos vigenciasCargos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(vigenciasCargos));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaVigenciasCargos.borrar: " + e);
            }
        }
    }

    @Override
    public VigenciasCargos buscarVigenciaCargo(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            return em.find(VigenciasCargos.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasCargos> buscarVigenciasCargos(EntityManager em) {
        em.clear();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasCargos.class));
        return em.createQuery(cq).getResultList();
    }

    public List<VigenciasCargos> buscarVigenciasCargosEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            em.clear();
            //em.flush();
            //em.getTransaction().begin();
            Query query = em.createNamedQuery("Empleados.findBySecuencia");
            query.setParameter("secuencia", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Empleados empleado = (Empleados) query.getSingleResult();
            Query query2 = em.createNamedQuery("VigenciasCargos.findByEmpleado");
            query2.setParameter("empleado", empleado);
            query2.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasCargos> vigenciasCargos = (List<VigenciasCargos>) query2.getResultList();
            //em.getTransaction().commit();
            return vigenciasCargos;
        } catch (Exception e) {
            List<VigenciasCargos> vigenciasCargos = null;
            return vigenciasCargos;
        }
    }
}

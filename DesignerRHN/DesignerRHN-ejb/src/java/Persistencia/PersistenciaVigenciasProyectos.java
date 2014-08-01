/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasProyectos;
import InterfacePersistencia.PersistenciaVigenciasProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasProyectos'
 * de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasProyectos implements PersistenciaVigenciasProyectosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, VigenciasProyectos vigenciasProyectos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasProyectos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasProyectos vigenciasProyectos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasProyectos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasProyectos vigenciasProyectos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(vigenciasProyectos));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<VigenciasProyectos> buscarVigenciasProyectos(EntityManager em) {
        em.clear();
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasProyectos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasProyectos> proyectosEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT COUNT(vp) FROM VigenciasProyectos vp WHERE vp.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vp FROM VigenciasProyectos vp WHERE vp.empleado.secuencia = :secuenciaEmpleado and vp.fechainicial = (SELECT MAX(vpr.fechainicial) FROM VigenciasProyectos vpr WHERE vpr.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                List<VigenciasProyectos> listaVigenciasProyectos = queryFinal.getResultList();
                return listaVigenciasProyectos;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.proyectosPersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasProyectos> vigenciasProyectosEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT vp FROM VigenciasProyectos vp WHERE vp.empleado.secuencia= :secuenciaEmpleado ORDER BY vp.fechainicial DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasProyectos> listaVigenciasProyectos = query.getResultList();
            return listaVigenciasProyectos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.vigenciasProyectosEmpleado" + e);
            return null;
        }
    }
}

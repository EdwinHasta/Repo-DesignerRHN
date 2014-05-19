/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasAfiliaciones;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'VigenciasAfiliaciones' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasAfiliaciones implements PersistenciaVigenciasAfiliacionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.getTransaction().begin();
            em.persist(vigenciasAfiliaciones);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("El registro VigenciasAfiliaciones no exite o esta reservada por lo cual no puede ser modificada (VigenciasAfiliaciones) : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasAfiliaciones);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("No se pudo modificar el registro VigenciasAfiliaciones : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasAfiliaciones));
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("No se pudo borrar el registro VigenciasAfiliaciones : " + e.toString());
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliaciones(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VigenciasAfiliaciones.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaAfiliacion PersistenciaVigenciasAfiliaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.empleado.secuencia = :secuenciaEmpl ORDER BY va.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasAfiliacionesEmpleado PersistenciaVigenciasAfiliaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasAfiliaciones buscarVigenciasAfiliacionesSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasAfiliaciones.findBySecuencia").setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasAfiliaciones vigenciasAfiliaciones = (VigenciasAfiliaciones) query.getSingleResult();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesVigenciaSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.vigenciasueldo.secuencia = :secVigencia");
            query.setParameter("secVigencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }
}

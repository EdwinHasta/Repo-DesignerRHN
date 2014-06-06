/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasCompensaciones;
import InterfacePersistencia.PersistenciaVigenciasCompensacionesInterface;
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
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasCompensaciones'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasCompensaciones implements PersistenciaVigenciasCompensacionesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, VigenciasCompensaciones vigenciasCompensaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasCompensaciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCompensaciones.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasCompensaciones vigenciasCompensaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciasCompensaciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCompensaciones.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasCompensaciones vigenciasCompensaciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(vigenciasCompensaciones));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasCompensaciones.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensaciones(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VigenciasCompensaciones.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCompensaciones PersistenciaVigenciasCompensaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.empleado.secuencia = :secuenciaEmpl ORDER BY vc.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasCompensacionesEmpleado PersistenciaVigenciasCompensaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasCompensaciones buscarVigenciaCompensacionSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasCompensaciones.findBySecuencia").setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasCompensaciones vigenciasCompensaciones = (VigenciasCompensaciones) query.getSingleResult();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaCompensacionSecuencia PersistenciaVigenciasCompensaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciaSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.secuencia =:secVigencia ORDER BY vc.fechainicial DESC");
            query.setParameter("secVigencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCompensacionesVigenciaSecuencia PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
    
    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesTipoCompensacion (EntityManager em, String tipoC){
        try{
           Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.tipocompensacion =:tipoCompensacion ORDER BY  vc.fechainicial DESC");
           query.setParameter("tipoCompensacion", tipoC);
           query.setHint("javax.persistence.cache.storeMode", "REFRESH");
           List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
           return vigenciasCompensaciones;
        }catch(Exception e){
            System.out.println("Error buscarVigenciasCompensacionesTipoCompensacion PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
    
    @Override
     public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciayCompensacion(EntityManager em, String tipoC,BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.tipocompensacion =:tipoCompensacion AND vc.vigenciajornada.secuencia =:secVigencia ORDER BY  vc.fechainicial DESC");
            query.setParameter("tipoCompensacion", tipoC);
            query.setParameter("tipoCompensacion", tipoC);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCompensacionesVigenciayCompensacion PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
}

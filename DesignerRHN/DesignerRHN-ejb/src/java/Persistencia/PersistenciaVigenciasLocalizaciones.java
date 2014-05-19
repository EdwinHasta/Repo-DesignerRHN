/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasLocalizaciones;
import InterfacePersistencia.PersistenciaVigenciasLocalizacionesInterface;
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
 * 'VigenciasLocalizaciones' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasLocalizaciones implements PersistenciaVigenciasLocalizacionesInterface {

    @Override
    public void crear(EntityManager em, VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.getTransaction().begin();
            em.persist(vigenciasLocalizaciones);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (VigenciasLocalizaciones): " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasLocalizaciones);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo modificar la VigenciasLocalizaciones : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasLocalizaciones));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasLocalizaciones (PersistenciaVigenciasLocalizaciones): " + e.toString());
        }
    }

    @Override
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizaciones(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VigenciasLocalizaciones.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasLocalizaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizacionesEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vl FROM VigenciasLocalizaciones vl WHERE vl.empleado.secuencia = :secuenciaEmpl ORDER BY vl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasLocalizaciones> vigenciasLocalizaciones = query.getResultList();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VigenciasLocalizaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasLocalizaciones buscarVigenciasLocalizacionesSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasLocalizaciones.findBySecuencia").setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasLocalizaciones vigenciasLocalizaciones = (VigenciasLocalizaciones) query.getSingleResult();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasLocalizacionesSecuencia Persistencia VigenciasLocalizaciones");
            return null;
        }
    }
}

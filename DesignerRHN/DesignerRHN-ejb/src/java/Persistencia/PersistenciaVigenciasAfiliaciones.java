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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasAfiliaciones'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasAfiliaciones implements PersistenciaVigenciasAfiliacionesInterface { 
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.persist(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("El registro VigenciasAfiliaciones no exite o esta reservada por lo cual no puede ser modificada (VigenciasAfiliaciones)");
        }
    }

    @Override
    public void editar(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.merge(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasAfiliaciones");
        }
    }

    @Override
    public void borrar(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.remove(em.merge(vigenciasAfiliaciones));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasAfiliaciones");
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliaciones() {
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
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.empleado.secuencia = :secuenciaEmpl ORDER BY va.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasAfiliacionesEmpleado PersistenciaVigenciasAfiliaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasAfiliaciones buscarVigenciasAfiliacionesSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasAfiliaciones.findBySecuencia").setParameter("secuencia", secuencia);
            VigenciasAfiliaciones vigenciasAfiliaciones = (VigenciasAfiliaciones) query.getSingleResult();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesVigenciaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.vigenciasueldo.secuencia = :secVigencia");
            query.setParameter("secVigencia", secuencia);
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }
}

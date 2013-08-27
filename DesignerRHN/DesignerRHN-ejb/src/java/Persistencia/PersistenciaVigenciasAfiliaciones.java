package Persistencia;

import Entidades.VigenciasAfiliaciones;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasAfiliaciones implements PersistenciaVigenciasAfiliacionesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasProrrateos.
     */
    @Override
    public void crear(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.persist(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("El registro VigenciasAfiliaciones no exite o esta reservada por lo cual no puede ser modificada (VigenciasAfiliaciones)");
        }
    }

    /*
     *Editar VigenciasProrrateos. 
     */
    @Override
    public void editar(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.merge(vigenciasAfiliaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasAfiliaciones");
        }
    }

    /*
     *Borrar VigenciasProrrateos.
     */
    @Override
    public void borrar(VigenciasAfiliaciones vigenciasAfiliaciones) {
        try {
            em.remove(em.merge(vigenciasAfiliaciones));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasAfiliaciones");
        }
    }

    /*
     *Encontrar un VigenciaProrrateo. 
     */
    @Override
    public VigenciasAfiliaciones buscarVigenciaAfiliacion(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasAfiliaciones.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaAfiliacion PersistenciaVigenciasAfiliaciones");
            return null;
        }
    }

    /*
     *Encontrar todos los VigenciasProrrateos.
     */
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
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.empleado.secuencia = :secuenciaEmpl ORDER BY va.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasAfiliacionesEmpleado PersistenciaVigenciasAfiliaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasAfiliaciones buscarVigenciasAfiliacionesSecuencia(BigInteger secVA) {
        try {
            Query query = em.createNamedQuery("VigenciasAfiliaciones.findBySecuencia").setParameter("secuencia", secVA);
            VigenciasAfiliaciones vigenciasAfiliaciones = (VigenciasAfiliaciones) query.getSingleResult();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }

    @Override
    public List<VigenciasAfiliaciones> buscarVigenciasAfiliacionesVigenciaSecuencia(BigDecimal secVigencia) {
        try {
            Query query = em.createQuery("SELECT va FROM VigenciasAfiliaciones va WHERE va.vigenciasueldo.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            List<VigenciasAfiliaciones> vigenciasAfiliaciones = query.getResultList();
            return vigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasAfiliacionesSecuencia PersistenciaVigenciasAfiliaciones ");
            return null;
        }
    }
}

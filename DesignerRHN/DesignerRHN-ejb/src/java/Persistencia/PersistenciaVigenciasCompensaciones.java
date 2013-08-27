package Persistencia;

import Entidades.VigenciasCompensaciones;
import InterfacePersistencia.PersistenciaVigenciasCompensacionesInterface;
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
public class PersistenciaVigenciasCompensaciones implements PersistenciaVigenciasCompensacionesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasCompensaciones.
     */
    @Override
    public void crear(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.persist(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("El registro VigenciasCompensaciones no exite o esta reservada por lo cual no puede ser modificada (VigenciasProrrateos)");
        }
    }

    /*
     *Editar VigenciasCompensaciones. 
     */
    @Override
    public void editar(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.merge(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasCompensaciones");
        }
    }

    /*
     *Borrar VigenciasCompensaciones.
     */
    @Override
    public void borrar(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.remove(em.merge(vigenciasCompensaciones));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasCompensaciones");
        }
    }

    /*
     *Encontrar un VigenciasCompensaciones. 
     */
    @Override
    public VigenciasCompensaciones buscarVigenciaCompensacion(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasCompensaciones.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaCompensacion PersistenciaVigenciasCompensaciones");
            return null;
        }
    }

    /*
     *Encontrar todos los VigenciasCompensaciones.
     */
    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensaciones() {
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
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.empleado.secuencia = :secuenciaEmpl ORDER BY vc.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasCompensacionesEmpleado PersistenciaVigenciasCompensaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasCompensaciones buscarVigenciaCompensacionSecuencia(BigInteger secVC) {
        try {
            Query query = em.createNamedQuery("VigenciasCompensaciones.findBySecuencia").setParameter("secuencia", secVC);
            VigenciasCompensaciones vigenciasCompensaciones = (VigenciasCompensaciones) query.getSingleResult();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaCompensacionSecuencia PersistenciaVigenciasCompensaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciaSecuencia(BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.secuencia =:secVigencia ORDER BY vc.fechainicial DESC");
            query.setParameter("secVigencia", secVigencia);
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCompensacionesVigenciaSecuencia PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
    
    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesTipoCompensacion (String tipoC){
        try{
           Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.tipocompensacion =:tipoCompensacion ORDER BY  vc.fechainicial DESC");
           query.setParameter("tipoCompensacion", tipoC);
           List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
           return vigenciasCompensaciones;
        }catch(Exception e){
            System.out.println("Error buscarVigenciasCompensacionesTipoCompensacion PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
    
    @Override
     public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciayCompensacion(String tipoC,BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.tipocompensacion =:tipoCompensacion AND vc.vigenciajornada.secuencia =:secVigencia ORDER BY  vc.fechainicial DESC");
            query.setParameter("tipoCompensacion", tipoC);
            query.setParameter("tipoCompensacion", tipoC);
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasCompensacionesVigenciayCompensacion PersistenciaVigenciasCompensaciones");
            return null;
        }
    }
}

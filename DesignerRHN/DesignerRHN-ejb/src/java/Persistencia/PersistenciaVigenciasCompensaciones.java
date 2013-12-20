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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasCompensaciones'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasCompensaciones implements PersistenciaVigenciasCompensacionesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.persist(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("El registro VigenciasCompensaciones no exite o esta reservada por lo cual no puede ser modificada (VigenciasProrrateos)");
        }
    }

    @Override
    public void editar(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.merge(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasCompensaciones");
        }
    }

    @Override
    public void borrar(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            em.remove(em.merge(vigenciasCompensaciones));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasCompensaciones");
        }
    }

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
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.empleado.secuencia = :secuenciaEmpl ORDER BY vc.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            List<VigenciasCompensaciones> vigenciasCompensaciones = query.getResultList();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasCompensacionesEmpleado PersistenciaVigenciasCompensaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasCompensaciones buscarVigenciaCompensacionSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasCompensaciones.findBySecuencia").setParameter("secuencia", secuencia);
            VigenciasCompensaciones vigenciasCompensaciones = (VigenciasCompensaciones) query.getSingleResult();
            return vigenciasCompensaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaCompensacionSecuencia PersistenciaVigenciasCompensaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasCompensaciones vc WHERE vc.vigenciajornada.secuencia =:secVigencia ORDER BY vc.fechainicial DESC");
            query.setParameter("secVigencia", secuencia);
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
     public List<VigenciasCompensaciones> buscarVigenciasCompensacionesVigenciayCompensacion(String tipoC,BigInteger secuencia) {
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

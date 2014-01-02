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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasLocalizaciones'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasLocalizaciones implements PersistenciaVigenciasLocalizacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.persist(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (VigenciasLocalizaciones)");
        }
    }

    @Override
    public void editar(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.merge(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la VigenciasLocalizaciones");
        }
    }
    
    @Override
    public void borrar(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.remove(em.merge(vigenciasLocalizaciones));
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasLocalizaciones (PersistenciaVigenciasLocalizaciones)");
        }
    }
 
    @Override
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizaciones() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasLocalizaciones.class));
        return em.createQuery(cq).getResultList();
        }catch(Exception e){
            System.out.println("Error buscarVigenciasLocalizaciones");
            return null;
        }
    }

    @Override
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizacionesEmpleado(BigInteger secuencia) {
        System.out.println("Entro buscarVigenciasLocalizacionesEmpleado PersistenciaVL");
        try {
            System.out.println("Entro if buscarVigenciasLocalizacionesEmpleado PersistenciaVL");
            Query query = em.createQuery("SELECT vl FROM VigenciasLocalizaciones vl WHERE vl.empleado.secuencia = :secuenciaEmpl ORDER BY vl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            List<VigenciasLocalizaciones> vigenciasLocalizaciones = query.getResultList();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VigenciasLocalizaciones " + e);
            return null;
        }
    }

    @Override
    public VigenciasLocalizaciones buscarVigenciasLocalizacionesSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("VigenciasLocalizaciones.findBySecuencia").setParameter("secuencia", secuencia);
            VigenciasLocalizaciones vigenciasLocalizaciones = (VigenciasLocalizaciones) query.getSingleResult();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasLocalizacionesSecuencia Persistencia VigenciasLocalizaciones");
            return null;
        }
    }
}

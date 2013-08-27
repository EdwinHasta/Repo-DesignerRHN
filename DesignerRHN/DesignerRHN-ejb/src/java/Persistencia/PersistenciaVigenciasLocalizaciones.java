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
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasLocalizaciones implements PersistenciaVigenciasLocalizacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasLocalizaciones.
     */
    @Override
    public void crear(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.persist(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser creada (VigenciasLocalizaciones)");
        }
    }

    /*
     *Editar VigenciasLocalizaciones. 
     */
    @Override
    public void editar(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.merge(vigenciasLocalizaciones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la VigenciasLocalizaciones");
        }
    }

    /*
     *Borrar VigenciasLocalizaciones.
     */
    @Override
    public void borrar(VigenciasLocalizaciones vigenciasLocalizaciones) {
        try {
            em.remove(em.merge(vigenciasLocalizaciones));
        } catch (Exception e) {
            System.out.println("Error borrarVigenciasLocalizaciones (PersistenciaVigenciasLocalizaciones)");
        }
    }

    /*
     *Encontrar una VigenciasLocalizaciones. 
     */
    @Override
    public VigenciasLocalizaciones buscarVigenciasLocalizacion(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasLocalizaciones.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasLocalizacion PersistenciaVigenciasLocalizaciones");
            return null;
        }
    }

    /*
     *Encontrar todas las VigenciasLocalizaciones.
     */
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

    /*
     *Encontrar las VigenciasLocalizaciones de un Empleado.
     */
    @Override
    public List<VigenciasLocalizaciones> buscarVigenciasLocalizacionesEmpleado(BigInteger secEmpleado) {
        System.out.println("Entro buscarVigenciasLocalizacionesEmpleado PersistenciaVL");
        try {
            System.out.println("Entro if buscarVigenciasLocalizacionesEmpleado PersistenciaVL");
            Query query = em.createQuery("SELECT vl FROM VigenciasLocalizaciones vl WHERE vl.empleado.secuencia = :secuenciaEmpl ORDER BY vl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasLocalizaciones> vigenciasLocalizaciones = query.getResultList();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VigenciasLocalizaciones " + e);
            return null;
        }
    }

    /*
     *Encontrar la VigenciasLocalizaciones por Secuencia.
     */
    @Override
    public VigenciasLocalizaciones buscarVigenciasLocalizacionesSecuencia(BigInteger secVL) {
        try {
            Query query = em.createNamedQuery("VigenciasLocalizaciones.findBySecuencia").setParameter("secuencia", secVL);
            VigenciasLocalizaciones vigenciasLocalizaciones = (VigenciasLocalizaciones) query.getSingleResult();
            return vigenciasLocalizaciones;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasLocalizacionesSecuencia Persistencia VigenciasLocalizaciones");
            return null;
        }
    }
}

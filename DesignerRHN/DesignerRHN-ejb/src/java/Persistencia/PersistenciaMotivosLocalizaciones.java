package Persistencia;

import Entidades.MotivosLocalizaciones;
import InterfacePersistencia.PersistenciaMotivosLocalizacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaMotivosLocalizaciones implements PersistenciaMotivosLocalizacionesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear MotivosLocalizaciones.
     */
    @Override
    public void crear(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.persist(motivosLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error crearMotivoLoc");
        }
    }

    /*
     *Editar MotivosLocalizaciones. 
     */
    @Override
    public void editar(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.merge(motivosLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error editarMotivoLoc");
        }
    }

    /*
     *Borrar MotivosLocalizaciones.
     */
    @Override
    public void borrar(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.remove(em.merge(motivosLocalizaciones));
        } catch (Exception e) {
            System.out.println("Error borrarMotivoLoc");
        }
    }

    /*
     *Encontrar un MotivosLocalizaciones 
     */
    @Override
    public MotivosLocalizaciones buscarMotivoLocalizacion(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(MotivosLocalizaciones.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarMotivoLocalizacion");
            return null;
        }

    }

    /*
     *Encontrar todas los MotivosLocalizaciones 
     */
    @Override
    public List<MotivosLocalizaciones> buscarMotivosLocalizaciones() {
        try {
            List<MotivosLocalizaciones> motivosL = (List<MotivosLocalizaciones>) em.createNamedQuery("MotivosLocalizaciones.findAll").getResultList();
            return motivosL;
        } catch (Exception e) {
            System.out.println("Error buscarMotivosLocalizaciones PersistenciaMotivosLovalizaciones");
            return null;
        }
    }

    @Override
    public MotivosLocalizaciones buscarMotivoLocalizacionSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT m FROM MotivosLocalizaciones m WHERE m.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            MotivosLocalizaciones motivoL = (MotivosLocalizaciones) query.getSingleResult();
            return motivoL;
        } catch (Exception e) {
            System.out.println("Error buscarMotivoLocalizacionSecuencia PersistenciaMotivosLocalizaciones");
            MotivosLocalizaciones motivoL = null;
            return motivoL;
        }

    }
}

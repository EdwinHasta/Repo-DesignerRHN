/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
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
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'MotivosLocalizaciones' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaMotivosLocalizaciones implements PersistenciaMotivosLocalizacionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.persist(motivosLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error crearMotivoLoc");
        }
    }

    @Override
    public void editar(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.merge(motivosLocalizaciones);
        } catch (Exception e) {
            System.out.println("Error editarMotivoLoc");
        }
    }

    @Override
    public void borrar(MotivosLocalizaciones motivosLocalizaciones) {
        try {
            em.remove(em.merge(motivosLocalizaciones));
        } catch (Exception e) {
            System.out.println("Error borrarMotivoLoc");
        }
    }

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

    public BigInteger contarVigenciasLocalizacionesMotivoLocalizacion(BigInteger secMotivoLocalizacion) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciaslocalizaciones WHERE motivo =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secMotivoLocalizacion);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador PERSISTENCIAMOTIVOSLOCALIZACIONES contarVigenciasLocalizacionesMotivoLocalizacion  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIAMOTIVOSLOCALIZACIONES  contarVigenciasLocalizacionesMotivoLocalizacion. " + e);
            return retorno;
        }
    }

}

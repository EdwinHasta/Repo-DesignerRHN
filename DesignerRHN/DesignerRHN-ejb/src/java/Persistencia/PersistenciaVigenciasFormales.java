/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasFormales;
import InterfacePersistencia.PersistenciaVigenciasFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasFormales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasFormales implements PersistenciaVigenciasFormalesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
    
    @Override
     public void crear(EntityManager em, VigenciasFormales vigenciasFormales) {
        try {
            em.merge(vigenciasFormales);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaVigenciasFormales.crear");
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasFormales vigenciasFormales) {
        em.merge(vigenciasFormales);
    }

    @Override
    public void borrar(EntityManager em, VigenciasFormales vigenciasFormales) {
        em.remove(em.merge(vigenciasFormales));
    }

    @Override
    public List<VigenciasFormales> buscarVigenciasFormales(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasFormales.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasFormales> educacionPersona(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT COUNT(vf) FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vf FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona and vf.fechavigencia = (SELECT MAX(vfo.fechavigencia) FROM VigenciasFormales vfo WHERE vfo.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuencia);
                List<VigenciasFormales> listaVigenciasFormales = queryFinal.getResultList();
                return listaVigenciasFormales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasFormales.educacionPersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasFormales> vigenciasFormalesPersona(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vF FROM VigenciasFormales vF WHERE vF.persona.secuencia = :secuenciaPersona ORDER BY vF.fechavigencia DESC");
            query.setParameter("secuenciaPersona", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasFormales> listaVigenciasFormales = query.getResultList();
            return listaVigenciasFormales;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona" + e);
            return null;
        }
    }
}
    
    
    
    
    
    
    
    


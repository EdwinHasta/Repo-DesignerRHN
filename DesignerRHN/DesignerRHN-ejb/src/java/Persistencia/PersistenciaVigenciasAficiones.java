/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasAficiones;
import InterfacePersistencia.PersistenciaVigenciasAficionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasAficiones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasAficiones implements PersistenciaVigenciasAficionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
   
    @Override
    public void crear(EntityManager em, VigenciasAficiones vigenciasAficiones) {
        try {
            em.merge(vigenciasAficiones);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasAficiones)");
        }
    }
   
    @Override
    public void editar(EntityManager em, VigenciasAficiones vigenciasAficiones) {
        try {
            em.merge(vigenciasAficiones);
        } catch (Exception e) {
            System.out.println("ALERTA! Error Persistencia VigenciasAficiones : "+e.toString());
        }
    }
   
    @Override
    public void borrar(EntityManager em, VigenciasAficiones vigenciasAficiones) {
        try {
            em.remove(em.merge(vigenciasAficiones));
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VigenciasAficiones: " + e);
        }
    }
   
    @Override
    public VigenciasAficiones buscarvigenciaAficion(EntityManager em, BigInteger secuencia) {
        try {
            BigInteger in = (BigInteger) secuencia;
            return em.find(VigenciasAficiones.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarvigenciaAficion : "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<VigenciasAficiones> aficionesPersona(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT COUNT(va) FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT va FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona and va.fechainicial = (SELECT MAX(vaf.fechainicial) FROM VigenciasAficiones vaf WHERE vaf.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuencia);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<VigenciasAficiones> listVigenciasAficiones = queryFinal.getResultList();
                return listVigenciasAficiones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasAficiones.aficionesPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<VigenciasAficiones> aficionesTotalesSecuenciaPersona(EntityManager em, BigInteger secuencia){
        try{
        Query queryFinal = em.createQuery("SELECT va FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona;");
                queryFinal.setParameter("secuenciaPersona", secuencia);
                queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<VigenciasAficiones> listVigenciasAficiones = queryFinal.getResultList();
                return listVigenciasAficiones;
        }catch(Exception e){
            System.out.println("Error aficionesTotalesSecuenciaPersona PersistenciaVigenciasAficiones : "+e.toString());
            return null;
        }
    }
}

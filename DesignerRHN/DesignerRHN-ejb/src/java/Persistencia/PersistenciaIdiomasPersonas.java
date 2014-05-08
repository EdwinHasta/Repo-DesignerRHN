/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.IdiomasPersonas;
import InterfacePersistencia.PersistenciaIdiomasPersonasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'IdiomasPersonas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaIdiomasPersonas implements PersistenciaIdiomasPersonasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, IdiomasPersonas idiomasPersonas) {
        try {
            em.persist(idiomasPersonas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaIdiomasPersonas : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, IdiomasPersonas idiomasPersonas) {
        try {
            em.merge(idiomasPersonas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaIdiomasPersonas : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, IdiomasPersonas idiomasPersonas) {
        try {
            em.remove(em.merge(idiomasPersonas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaIdiomasPersonas : " + e.toString());
        }
    }
    
    @Override
    public List<IdiomasPersonas> idiomasPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(ip) FROM IdiomasPersonas ip WHERE ip.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ip FROM IdiomasPersonas ip WHERE ip.persona.secuencia = :secuenciaPersona");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<IdiomasPersonas> listaIdiomasPersonas = queryFinal.getResultList();
                return listaIdiomasPersonas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaIdiomasPersonas.idiomasPersona" + e);
            return null;
        }
    }

    @Override
    public List<IdiomasPersonas> totalIdiomasPersonas(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ip FROM IdiomasPersonas ip");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<IdiomasPersonas> resultado = (List<IdiomasPersonas>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaIdiomasPersonas.totalIdiomasPersonas" + e);
            return null;
        }
    }
}

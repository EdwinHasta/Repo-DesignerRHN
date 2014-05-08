/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasDeportes;
import InterfacePersistencia.PersistenciaVigenciasDeportesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasDeportes'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasDeportes implements PersistenciaVigenciasDeportesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, VigenciasDeportes vigenciasDeportes) {
        try {
            em.persist(vigenciasDeportes);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasDeportes)");
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasDeportes vigenciasDeportes) {
        try {
            em.merge(vigenciasDeportes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la VigenciasDeportes");
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasDeportes vigenciasDeportes) {
        try {
            em.remove(em.merge(vigenciasDeportes));
        } catch (Exception e) {
            System.out.println("la VigenciasDeportes no se ha podido eliminar");
        }
    }

    @Override
    public List<VigenciasDeportes> deportePersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vd) FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vd FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona and vd.fechainicial = (SELECT MAX(vde.fechainicial) FROM VigenciasDeportes vde WHERE vde.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<VigenciasDeportes> listaVigenciasDeportes = queryFinal.getResultList();
                return listaVigenciasDeportes;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasDeportes.deportePersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasDeportes> deportesTotalesSecuenciaPersona(EntityManager em, BigInteger secuenciaP) {
        try {
            Query queryFinal = em.createQuery("SELECT vd FROM VigenciasDeportes vd WHERE vd.persona.secuencia = :secuenciaPersona");
            queryFinal.setParameter("secuenciaPersona", secuenciaP);
            queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasDeportes> listaVigenciasDeportes = queryFinal.getResultList();
            return listaVigenciasDeportes;
        } catch (Exception e) {
            System.out.println("Error deportesTotalesSecuenciaPersona PersistenciaVigenciasDeportes : " + e.toString());
            return null;
        }
    }
}

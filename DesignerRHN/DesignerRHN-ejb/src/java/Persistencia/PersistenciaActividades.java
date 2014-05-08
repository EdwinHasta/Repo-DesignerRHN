/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Actividades;
import InterfacePersistencia.PersistenciaActividadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Actividades' de la
 * base de datos
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaActividades implements PersistenciaActividadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em, Actividades actividades) {
        try {
            em.persist(actividades);
        } catch (Exception e) {
            System.out.println("Error creando bancos PersistenciaActividades");
        }
    }

    @Override
    public void editar(EntityManager em, Actividades actividades) {
        try {
            em.merge(actividades);
        } catch (Exception e) {
            System.out.println("Error editando bancos PersistenciaActividades");
        }
    }

    @Override
    public void borrar(EntityManager em, Actividades actividades) {
        try {
            em.remove(em.merge(actividades));
        } catch (Exception e) {
            System.out.println("Error borrando bancos PersistenciaActividades");
        }
    }

    @Override
    public List<Actividades> buscarActividades(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividades.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarActividades PersistenciaActividades : " + e.toString());
            return null;
        }
    }

    public BigInteger contarBienNecesidadesActividad(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM biennecesidades WHERE actividad = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaActividades contarTiposLegalizaciones persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaActividades contarTiposLegalizaciones. " + e);
            return retorno;
        }
    }

    public BigInteger contarParametrosInformesActividad(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM parametrosinformes WHERE actividadbienestar = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaActividades contarTiposLegalizaciones persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaActividades contarTiposLegalizaciones. " + e);
            return retorno;
        }
    }
}

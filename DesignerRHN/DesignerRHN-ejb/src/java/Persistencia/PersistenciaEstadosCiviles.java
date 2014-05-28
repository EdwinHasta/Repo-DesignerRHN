/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.EstadosCiviles;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless Clase encargada de realizar operaciones sobre la tabla
 * 'EstadosCiviles' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEstadosCiviles implements PersistenciaEstadosCivilesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, EstadosCiviles estadosCiviles) {
        try {
            em.getTransaction().begin();
            em.persist(estadosCiviles);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error creando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

    @Override
    public void editar(EntityManager em, EstadosCiviles estadosCiviles) {
        try {
            em.getTransaction().begin();
            em.merge(estadosCiviles);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error editando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

    @Override
    public void borrar(EntityManager em, EstadosCiviles estadosCiviles) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(estadosCiviles));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrando EstadosCiviles PersistenciaEstadosCiviles");
        }
    }

    @Override
    public EstadosCiviles buscarEstadoCivil(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(EstadosCiviles.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarEstadoCivil PersistenciaEstadosCiviles : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadosCiviles> consultarEstadosCiviles(EntityManager em) {
        Query query = em.createQuery("SELECT e FROM EstadosCiviles e ORDER BY e.codigo ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<EstadosCiviles> listEstadosCiviles = query.getResultList();
        return listEstadosCiviles;

    }

    public BigInteger contadorVigenciasEstadosCiviles(EntityManager em, BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*) FROM vigenciasestadosciviles WHERE estadocivil = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAESTADOSCIVILES contadorVigenciasEstadosCiviles = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAESTADOSCIVILES contadorVigenciasEstadosCiviles  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}

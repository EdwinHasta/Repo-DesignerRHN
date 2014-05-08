/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasEstadosCiviles;
import InterfacePersistencia.PersistenciaVigenciasEstadosCivilesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'VigenciasEstadosCiviles' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasEstadosCiviles implements PersistenciaVigenciasEstadosCivilesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        try {
            em.merge(vigenciasEstadosCiviles);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaVigenciasEstadosCiviles.crear: " + e);
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        em.merge(vigenciasEstadosCiviles);
    }

    @Override
    public void borrar(EntityManager em, VigenciasEstadosCiviles vigenciasEstadosCiviles) {
        em.remove(em.merge(vigenciasEstadosCiviles));
    }

    @Override
    public VigenciasEstadosCiviles buscarVigenciaEstadoCivil(EntityManager em, BigInteger secuencia) {
        return em.find(VigenciasEstadosCiviles.class, secuencia);
    }

    @Override
    public List<VigenciasEstadosCiviles> consultarVigenciasEstadosCiviles(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasEstadosCiviles.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasEstadosCiviles> consultarVigenciasEstadosCivilesPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vec) FROM VigenciasEstadosCiviles vec WHERE vec.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vec FROM VigenciasEstadosCiviles vec WHERE vec.persona.secuencia = :secuenciaPersona and vec.fechavigencia = (SELECT MAX(veci.fechavigencia) FROM VigenciasEstadosCiviles veci WHERE veci.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasEstadosCiviles> listaVigenciasEstadosCiviles = queryFinal.getResultList();
                return listaVigenciasEstadosCiviles;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEstadosCiviles.estadoCivilPersona" + e);
            return null;
        }
    }
    @Override
    public List<VigenciasEstadosCiviles> consultarVigenciasEstadosCivilesPorPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT vec FROM VigenciasEstadosCiviles vec WHERE vec.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasEstadosCiviles> listaVigenciasEstadosCiviles = query.getResultList();
            return listaVigenciasEstadosCiviles;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEstadosCiviles.consultarVigenciasEstadosCivilesPorPersona : " + e);
            return null;
        }
    }
}

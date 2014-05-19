package Persistencia;

import Entidades.VigenciasProrrateos;
import InterfacePersistencia.PersistenciaVigenciasProrrateosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasProrrateos'
 * de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasProrrateos implements PersistenciaVigenciasProrrateosInterface {

 
    @Override
    public void crear(EntityManager em, VigenciasProrrateos vigenciasProrrateos) {
        try {
                     em.getTransaction().begin();
            em.merge(vigenciasProrrateos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("El registro VigenciasProrrateos no exite o esta reservada por lo cual no puede ser modificada (VigenciasProrrateos) : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasProrrateos vigenciasProrrateos) {
        try {
            em.getTransaction().begin();
            em.merge(vigenciasProrrateos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasProrrateos: " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasProrrateos vigenciasProrrateos) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(vigenciasProrrateos));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasProrrateos: " + e.toString());
        }
    }

    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateos(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VigenciasProrrateos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateos PersistenciaVigenciasProrrateos");
            return null;
        }
    }

    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateosEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vp FROM VigenciasProrrateos vp WHERE vp.viglocalizacion.empleado.secuencia = :secuenciaEmpl ORDER BY vp.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasProrrateos> vigenciasProrrateos = query.getResultList();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia VigenciasProrrateos " + e);
            return null;
        }
    }

    @Override
    public VigenciasProrrateos buscarVigenciaProrrateoSecuencia(EntityManager em, BigInteger secVP) {
        try {
            Query query = em.createNamedQuery("VigenciasProrrateos.findBySecuencia").setParameter("secuencia", secVP);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasProrrateos vigenciasProrrateos = (VigenciasProrrateos) query.getSingleResult();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaProrrateoSecuencia PersistenciaVigenciasProrrateos");
            return null;
        }
    }

    @Override
    public List<VigenciasProrrateos> buscarVigenciasProrrateosVigenciaSecuencia(EntityManager em, BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT vp FROM VigenciasProrrateos vp WHERE vp.viglocalizacion.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasProrrateos> vigenciasProrrateos = query.getResultList();
            return vigenciasProrrateos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateosVigenciaSecuencia PersistenciaVigenciasProrrateos");
            return null;
        }
    }
}

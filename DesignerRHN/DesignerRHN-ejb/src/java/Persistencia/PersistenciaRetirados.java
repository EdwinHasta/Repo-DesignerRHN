/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Retirados;
import InterfacePersistencia.PersistenciaRetiradosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Retirados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaRetirados implements PersistenciaRetiradosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Retirados retirados) {
        try {
            em.persist(retirados);
        } catch (Exception e) {
            System.out.println("El registro Retiro no exite o esta reservada por lo cual no puede ser modificada (Retirados)");
        }
    }

    @Override
    public void editar(EntityManager em, Retirados retirados) {
        try {
            em.merge(retirados);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro Retirados");
        }
    }

    @Override
    public void borrar(EntityManager em, Retirados retirados) {
        try{
            em.remove(em.merge(retirados));
        }catch(Exception e){
        }
    }

    @Override
    public List<Retirados> buscarRetirados(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Retirados.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<Retirados> buscarRetirosEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT r FROM Retirados r WHERE r.vigenciatipotrabajador.empleado.secuencia = :secuenciaEmpl ORDER BY r.fecharetiro DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Retirados> retiros = query.getResultList();
            return retiros;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Retirados " + e);
            return null;
        }
    }

    @Override
    public Retirados buscarRetiroSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createNamedQuery("Retirados.findBySecuencia").setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Retirados retiro = (Retirados) query.getSingleResult();
            return retiro;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Retirados buscarRetiroVigenciaSecuencia(EntityManager em, BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT r FROM Retirados r WHERE r.vigenciatipotrabajador.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Retirados retiroVigencia = (Retirados) query.getSingleResult();
            return retiroVigencia;
        } catch (Exception e) {
            return new Retirados();
        }
    }
}

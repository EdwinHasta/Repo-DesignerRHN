/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Direcciones;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Direcciones' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDirecciones implements PersistenciaDireccionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Direcciones direcciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(direcciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Direcciones direcciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(direcciones);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Direcciones direcciones) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(direcciones));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaDirecciones.borrar: " + e);
            }
        }
    }

    @Override
    public Direcciones buscarDireccion(EntityManager em, BigInteger secuencia) {
        return em.find(Direcciones.class, secuencia);
    }

    @Override
    public List<Direcciones> buscarDirecciones(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Direcciones.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Direcciones> direccionPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(d) FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT d FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona and d.fechavigencia = (SELECT MAX(di.fechavigencia) FROM Direcciones di WHERE di.persona.secuencia = :secuenciaPersona) ");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<Direcciones> listaDirecciones = queryFinal.getResultList();
                return listaDirecciones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.direccionPersona" + e);
            return null;
        }
    }

    @Override
    public List<Direcciones> direccionesPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(d) FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT d FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona ");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<Direcciones> listaDirecciones = queryFinal.getResultList();
                return listaDirecciones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.direccionPersona" + e);
            return null;
        }
    }
}

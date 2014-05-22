/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Personas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.math.BigInteger;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Personas' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPersonas implements PersistenciaPersonasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Personas personas) {
        em.persist(personas);
    }

    @Override
    public void editar(EntityManager em, Personas personas) {
        em.getTransaction().begin();
        em.merge(personas);
        em.getTransaction().commit();
    }

    @Override
    public void borrar(EntityManager em, Personas personas) {
        em.remove(em.merge(personas));
    }

    @Override
    public Personas buscarPersona(EntityManager em, BigInteger secuencia) {
        return em.find(Personas.class, secuencia);
    }

    @Override
    public List<Personas> consultarPersonas(EntityManager em) {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Personas.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public void actualizarFotoPersona(EntityManager em, BigInteger identificacion) {
        try {
            em.getTransaction().begin();
            Query query = em.createNativeQuery("update Personas p set p.pathfoto='S' where p.numerodocumento = ?");
            query.setParameter(1, identificacion);
            query.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No se pudo agregar estado de fotografia: " + e);
        }
    }

    @Override
    public Personas buscarFotoPersona(EntityManager em, BigInteger identificacion) {
        try {
            Query query = em.createQuery("SELECT p from Personas p where p.numerodocumento = :identificacion");
            query.setParameter("identificacion", identificacion);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Personas persona = (Personas) query.getSingleResult();
            return persona;
        } catch (Exception e) {
            Personas personas = null;
            return personas;
        }
    }

    @Override
    public Personas buscarPersonaSecuencia(EntityManager em, BigInteger secuencia) {
        Personas persona;
        try {
            Query query = em.createQuery("SELECT p FROM Personas p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            persona = (Personas) query.getSingleResult();
            return persona;
        } catch (Exception e) {
            persona = null;
            System.out.println("Error buscarPersonaSecuencia PersistenciaPersonas");
        }
        return persona;
    }
}

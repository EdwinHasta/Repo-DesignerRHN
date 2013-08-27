package Persistencia;

import Entidades.Personas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaPersonasInterface;
import java.math.BigInteger;
import javax.persistence.Query;

@Stateless
public class PersistenciaPersonas implements PersistenciaPersonasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear persona.
     */
    public void crear(Personas personas) {
        em.persist(personas);
    }

    /*
     *Editar persona. 
     */
    public void editar(Personas personas) {
        em.merge(personas);
    }

    /*
     *Borrar persona.
     */
    public void borrar(Personas personas) {
        em.remove(em.merge(personas));
    }

    /*
     *Encontrar una persona. 
     */
    public Personas buscarPersona(BigInteger id) {
        return em.find(Personas.class, id);
    }

    /*
     *Encontrar todas las personas. 
     */
    public List<Personas> buscarPersonas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Personas.class));
        return em.createQuery(cq).getResultList();
    }

    public void actualizarFotoPersona(BigInteger identificacion) {
        try {
            Query query = em.createQuery("update Personas p set pathfoto='S' where p.numerodocumento =:identificacion");
            query.setParameter("identificacion", identificacion);
            query.executeUpdate();

        } catch (Exception e) {
            System.out.println("No se pudo agregar estado de fotografia");
        }
    }

    public Personas buscarFotoPersona(BigInteger identificacion) {
        try {
            Query query = em.createQuery("SELECT p from Personas p where p.numerodocumento = :identificacion");
            query.setParameter("identificacion", identificacion);
            Personas persona = (Personas) query.getSingleResult();
            return persona;

        } catch (Exception e) {
            Personas personas = null;
            return personas;
        }
    }

    @Override
    public Personas buscarPersonaSecuencia(BigInteger secuencia) {
        Personas persona;
        try {
            Query query = em.createQuery("SELECT p FROM Personas p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            persona = (Personas) query.getSingleResult();
            return persona;
        } catch (Exception e) {
            persona = null;
            System.out.println("Error buscarPersonaSecuencia PersistenciaPersonas");
        }
        return persona;
    }
}

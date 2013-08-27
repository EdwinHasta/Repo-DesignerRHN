package Persistencia;

import Entidades.Telefonos;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaTelefonos implements PersistenciaTelefonosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Telefono.
     */
    @Override
    public void crear(Telefonos telefonos) {
        try {
            em.merge(telefonos);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaTelefonos.crear: " + e);
        }
    }
    /*
     *Editar Telefono. 
     */

    @Override
    public void editar(Telefonos telefonos) {
        em.merge(telefonos);
    }

    /*
     *Borrar Telefono.
     */
    @Override
    public void borrar(Telefonos telefonos) {
        em.remove(em.merge(telefonos));
    }

    /*
     *Encontrar un Telefono. 
     */
    @Override
    public Telefonos buscarTelefono(BigInteger id) {
        return em.find(Telefonos.class, id);
    }

    /*
     *Encontrar todos los Telefonos.
     */
    @Override
    public List<Telefonos> buscarTelefonos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Telefonos.class));
        return em.createQuery(cq).getResultList();
    }

    /*
     * Encontrar Telefono de una Persona.
     */
    @Override
    public List<Telefonos> telefonoPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(t) FROM Telefonos t WHERE t.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT t FROM Telefonos t WHERE t.persona.secuencia = :secuenciaPersona and t.fechavigencia = (SELECT MAX(tl.fechavigencia) FROM Telefonos tl WHERE tl.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<Telefonos> listaTelefonos = queryFinal.getResultList();
                return listaTelefonos;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona"+ e);
            return null;
        }
    }
}

/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.Escalafones;
import InterfacePersistencia.PersistenciaEscalafonesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Escalafones' de la
 * base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaEscalafones implements PersistenciaEscalafonesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
   /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,Escalafones escalafones) {
        try {
            em.persist(escalafones);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (Escalafones)");
        }
    }

    @Override
    public void editar(EntityManager em,Escalafones escalafones) {
        try {
            em.merge(escalafones);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Escalafon");
        }
    }

    @Override
    public void borrar(EntityManager em,Escalafones escalafon) {
        try {
            em.remove(em.merge(escalafon));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el Escalafon");
        }
    }

    @Override
    public List<Escalafones> buscarEscalafones(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Escalafones.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Escalafones buscarEscalafonSecuencia(EntityManager em,BigInteger secEscalafon) {
        try {
            Query query = em.createNamedQuery("SELECT e FROM Escalafones e WHERE e.secuencia=:secuencia");
            query.setParameter("secuencia", secEscalafon);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Escalafones escalafones = (Escalafones) query.getSingleResult();
            return escalafones;
        } catch (Exception e) {
            return null;
        }
    }
}

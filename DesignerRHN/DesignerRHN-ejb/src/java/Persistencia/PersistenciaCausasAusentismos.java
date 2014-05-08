/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Causasausentismos;
import InterfacePersistencia.PersistenciaCausasAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'CausasAusentismos' de la base de datos
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaCausasAusentismos implements PersistenciaCausasAusentismosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,Causasausentismos causasAusentismos) {
        try {
            em.merge(causasAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaCausasAusentismos.crear");
        }
    }

    @Override
    public void editar(EntityManager em,Causasausentismos causasAusentismos) {
        em.merge(causasAusentismos);
    }

    @Override
    public void borrar(EntityManager em,Causasausentismos causasAusentismos) {
        em.remove(em.merge(causasAusentismos));
    }

    @Override
    public List<Causasausentismos> buscarCausasAusentismos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ca FROM Causasausentismos ca ORDER BY ca.codigo");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Causasausentismos> todasCausasAusentismos = query.getResultList();
            return todasCausasAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasCausas)" + e);
            return null;
        }
    }

}

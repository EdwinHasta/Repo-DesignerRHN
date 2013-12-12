/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Clasesausentismos;
import InterfacePersistencia.PersistenciaClasesAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
/**
 * Clase Stateless Clase encargada de realizar operaciones sobre la tabla 'ClasesAusentismos'
 * de la base de datos
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaClasesAusentismos implements PersistenciaClasesAusentismosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Clasesausentismos clasesAusentismos) {
        try {
            em.merge(clasesAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaClasesAusentismos.crear");
        }
    }

    @Override
    public void editar(Clasesausentismos clasesAusentismos) {
        em.merge(clasesAusentismos);
    }

    @Override
    public void borrar(Clasesausentismos clasesAusentismos) {
        em.remove(em.merge(clasesAusentismos));
    }

    @Override
    public List<Clasesausentismos> buscarClasesAusentismos() {
        try {
            Query query = em.createQuery("SELECT ca FROM Clasesausentismos ca ORDER BY ca.codigo");
            List<Clasesausentismos> todasClasesAusentismos = query.getResultList();
            return todasClasesAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}

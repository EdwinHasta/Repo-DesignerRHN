/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Tiposausentismos;
import InterfacePersistencia.PersistenciaTiposAusentismosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposAusentismos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposAusentismos implements PersistenciaTiposAusentismosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Tiposausentismos tiposAusentismos) {
        try {
            em.merge(tiposAusentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaTiposAusentismos.crear");
        }
    }

    @Override
    public void editar(Tiposausentismos tiposAusentismos) {
        em.merge(tiposAusentismos);
    }

    @Override
    public void borrar(Tiposausentismos tiposAusentismos) {
        em.remove(em.merge(tiposAusentismos));
    }

    @Override
    public List<Tiposausentismos> buscarTiposAusentismos() {
        try {
            Query query = em.createQuery("SELECT ta FROM Tiposausentismos ta ORDER BY ta.codigo");
            List<Tiposausentismos> todosTiposAusentismos = query.getResultList();
            return todosTiposAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}

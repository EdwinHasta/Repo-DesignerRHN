/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Unidades;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Unidades'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaUnidades implements PersistenciaUnidadesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, Unidades unidad) {
        try {
            em.persist(unidad);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaUnidades.crear");
        }
    }

    @Override
    public void editar(EntityManager em, Unidades unidad) {
        try {
            em.merge(unidad);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaUnidades.editar");
        }
    }

    @Override
    public void borrar(EntityManager em, Unidades unidad) {
        try {
            em.remove(em.merge(unidad));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaUnidades.borrar");
        }
    }

    @Override
    public List<Unidades> consultarUnidades(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT u FROM Unidades u ORDER BY u.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Unidades> listaUnidades = query.getResultList();
            return listaUnidades;
        } catch (Exception e) {
            return null;
        }
    }
}

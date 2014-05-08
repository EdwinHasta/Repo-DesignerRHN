/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposCotizantes;
import InterfacePersistencia.PersistenciaTiposCotizantesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposCotizantes'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposCotizantes implements PersistenciaTiposCotizantesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    
    @Override
    public void crear(EntityManager em, TiposCotizantes tiposCotizantes) {
        try {
            em.persist(tiposCotizantes);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposCotizantes");
        }
    }

    @Override
    public void editar(EntityManager em, TiposCotizantes tiposCotizantes) {
        try {
            em.merge(tiposCotizantes);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposCotizantes");
        }
    }

    @Override
    public void borrar(EntityManager em, TiposCotizantes tiposCotizantes) {
        try {
            em.remove(em.merge(tiposCotizantes));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposCotizantes");
        }
    }
    
    @Override
    public List<TiposCotizantes> lovTiposCotizantes(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposCotizantes tc ORDER BY tc.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposCotizantes> listaTiposCotizantes = query.getResultList();
            return listaTiposCotizantes;
        } catch (Exception e) {
            return null;
        }
    }
}

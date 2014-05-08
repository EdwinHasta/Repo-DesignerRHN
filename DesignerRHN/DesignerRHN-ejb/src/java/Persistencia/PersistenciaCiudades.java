/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Ciudades;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Ciudades'
 * de la base de datos
 * @author Betelgeuse
 */
@Stateless
public class PersistenciaCiudades implements PersistenciaCiudadesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
     public void crear(EntityManager em,Ciudades ciudades) {
        try {
            em.merge(ciudades);
        } catch (PersistenceException ex) {
            Logger.getLogger(Ciudades.class.getName()).log(Level.SEVERE, null, ex);
            throw new EntityExistsException(ex);
        }
    }

    @Override
    public void editar(EntityManager em,Ciudades ciudades) {
        em.merge(ciudades);
    }

    @Override
    public void borrar(EntityManager em,Ciudades ciudades) {
        em.remove(em.merge(ciudades));
    }

    @Override
    public List<Ciudades> ciudades(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT c FROM Ciudades c ORDER BY c.nombre");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Ciudades> ciudades = query.getResultList();
            return ciudades;
        } catch (Exception e) {
            return null;
        }
    }
    
}

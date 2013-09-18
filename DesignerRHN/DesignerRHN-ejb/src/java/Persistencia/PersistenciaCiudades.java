package Persistencia;

import Entidades.Ciudades;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaCiudades implements PersistenciaCiudadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
     public void crear(Ciudades ciudades) {
        try {
            em.merge(ciudades);
        } catch (PersistenceException ex) {
            Logger.getLogger(Ciudades.class.getName()).log(Level.SEVERE, null, ex);
            throw new EntityExistsException(ex);
        }
    }
       
     
     /*
      *Editar ciudades. 
      */
    

    @Override
    public void editar(Ciudades ciudades) {
        em.merge(ciudades);
    }

    /*
     *Borrar Ciudades.
     */
    @Override
    public void borrar(Ciudades ciudades) {
        em.remove(em.merge(ciudades));
    }

    /*
     *Encontrar una ciudad. 
     */

    public List<Ciudades> buscarCiudades() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Ciudades.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Ciudades> ciudades() {
        try {
            Query query = em.createQuery("SELECT c FROM Ciudades c ORDER BY c.nombre");
            List<Ciudades> ciudades = query.getResultList();
            return ciudades;
        } catch (Exception e) {
            return null;
        }
    }
    
}

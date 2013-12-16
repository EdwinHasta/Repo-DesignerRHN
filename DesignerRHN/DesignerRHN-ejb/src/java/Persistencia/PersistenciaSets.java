/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Sets;
import InterfacePersistencia.PersistenciaSetsInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Sets'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaSets  implements PersistenciaSetsInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Sets sets) {
        em.persist(sets);
    }

    @Override
    public void editar(Sets sets) {
        em.merge(sets);
    }

    @Override
    public void borrar(Sets sets) {
        em.remove(em.merge(sets));
    }
    
    @Override
    public List<Sets> buscarSets() {
        List<Sets> setsLista = (List<Sets>) em.createNamedQuery("Sets.findAll")
                .getResultList();
        return setsLista;
    }

    @Override
    public Sets buscarSetSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT e FROM Sets e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Sets sets = (Sets) query.getSingleResult();
            return sets;
        } catch (Exception e) {
            Sets sets = null;
            return sets;
        }
    }
    
    @Override
    public List<Sets> buscarSetsEmpleado(BigInteger secEmpleado){
        try {
            Query query = em.createQuery("SELECT st FROM Sets st WHERE st.empleado.secuencia = :secuenciaEmpl ORDER BY st.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            
            List<Sets> setsE = query.getResultList();
            return setsE;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Sets " + e);
            return null;
        }
    }
}

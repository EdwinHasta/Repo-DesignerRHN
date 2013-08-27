
package Persistencia;


import Entidades.Sets;
import Entidades.VigenciasContratos;
import InterfacePersistencia.PersistenciaSetsInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 *
 * @author AndresPineda
 */
@Stateless

public class PersistenciaSets  implements PersistenciaSetsInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Sets.
     */

    @Override
    public void crear(Sets sets) {
        em.persist(sets);
    }

    /*
     *Editar Sets. 
     */
    @Override
    public void editar(Sets sets) {
        em.merge(sets);
    }

    /*
     *Borrar Sets.
     */

    @Override
    public void borrar(Sets sets) {
        em.remove(em.merge(sets));
    }

    /*
     *Encontrar un Sets. 
     */

    @Override
    public Sets buscarSets(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Sets.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todos los Sets. 
     */

    @Override
    public List<Sets> buscarSets() {

        //javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        /*CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         cq.select(cq.from(Empleados.class));
         return em.createQuery(cq).getResultList();
         */
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

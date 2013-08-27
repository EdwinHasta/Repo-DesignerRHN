
package Persistencia;
import Entidades.ReformasLaborales;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless

public class PersistenciaReformasLaborales implements PersistenciaReformasLaboralesInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Reforma Laboral.
     */
    @Override
    public void crear(ReformasLaborales reformaLaboral) {
        em.persist(reformaLaboral);
    }

    /*
     *Editar Reforma laboral. 
     */
    @Override
    public void editar(ReformasLaborales reformaLaboral) {
        em.merge(reformaLaboral);
    }

    /*
     *Borrar Reforma Laboral.
     */
    @Override
    public void borrar(ReformasLaborales reformaLaboral) {
        em.remove(em.merge(reformaLaboral));
    }

    /*
     *Encontrar una reforma laboral. 
     */
    @Override
    public ReformasLaborales buscarReformaLaboral(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(ReformasLaborales.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todas las reformas. 
     */
    @Override
    public List<ReformasLaborales> buscarReformasLaborales() {

        //javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        /*CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
         cq.select(cq.from(Empleados.class));
         return em.createQuery(cq).getResultList();
         */
        List<ReformasLaborales> reformaLista = (List<ReformasLaborales>) em.createNamedQuery("ReformasLaborales.findAll")
                .getResultList();
        return reformaLista;
    }

    @Override
    public ReformasLaborales buscarReformaSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT e FROM ReformasLaborales e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ReformasLaborales reformaL = (ReformasLaborales) query.getSingleResult();
            return reformaL;
        } catch (Exception e) {
        }
        ReformasLaborales reformaL = null;
        return reformaL;
    }



}

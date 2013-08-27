package Persistencia;

import Entidades.Retirados;
import InterfacePersistencia.PersistenciaRetiradosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaRetirados implements PersistenciaRetiradosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Retirados.
     */
    @Override
    public void crear(Retirados retirados) {
        try {
            em.persist(retirados);
        } catch (Exception e) {
            System.out.println("El registro Retiro no exite o esta reservada por lo cual no puede ser modificada (Retirados)");
        }
    }

    /*
     *Editar Retirados. 
     */
    @Override
    public void editar(Retirados retirados) {
        try {
            em.merge(retirados);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro Retirados");
        }
    }

    /*
     *Borrar Retirados.
     */
    @Override
    public void borrar(Retirados retirados) {
        try{
            em.remove(em.merge(retirados));
        }catch(Exception e){
        }
    }

    /*
     *Encontrar un Retirado. 
     */
    @Override
    public Retirados buscarRetirado(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(Retirados.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todos los Retirados.
     */
    @Override
    public List<Retirados> buscarRetirados() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Retirados.class));
        return em.createQuery(cq).getResultList();
    }
    

    @Override
    public List<Retirados> buscarRetirosEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT r FROM Retirados r WHERE r.vigenciatipotrabajador.empleado.secuencia = :secuenciaEmpl ORDER BY r.fecharetiro DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<Retirados> retiros = query.getResultList();
            return retiros;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Retirados " + e);
            return null;
        }
    }

    @Override
    public Retirados buscarRetiroSecuencia(BigInteger secR) {
        try {
            Query query = em.createNamedQuery("Retirados.findBySecuencia").setParameter("secuencia", secR);
            Retirados retiro = (Retirados) query.getSingleResult();
            return retiro;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Retirados buscarRetiroVigenciaSecuencia(BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT r FROM Retirados r WHERE r.vigenciatipotrabajador.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            Retirados retiroVigencia = (Retirados) query.getSingleResult();
            return retiroVigencia;
        } catch (Exception e) {
            return new Retirados();
        }
    }
}

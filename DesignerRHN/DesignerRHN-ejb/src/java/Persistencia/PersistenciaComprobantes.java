package Persistencia;

import Entidades.Comprobantes;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaComprobantes implements PersistenciaComprobantesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Comprobante.
     */
    @Override
    public void crear(Comprobantes comprobante) {
        em.persist(comprobante);
    }

    /*
     *Editar Comprobante. 
     */
    @Override
    public void editar(Comprobantes comprobante) {
        em.merge(comprobante);
    }

    /*
     *Borrar Comprobante.
     */
    @Override
    public void borrar(Comprobantes comprobante) {
        em.remove(em.merge(comprobante));
    }

    /*
     *Encontrar un Comprobante. 
     */
    @Override
    public Comprobantes buscarComprobante(Object id) {
        return em.find(Comprobantes.class, id);
    }

    /*
     *Encontrar todas los Comprobantes. 
     */
    @Override
    public List<Comprobantes> buscarComprobantes() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Comprobantes.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
   public List<Comprobantes> comprobantesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT c FROM Comprobantes c WHERE c.empleado.secuencia = :secuenciaEmpleado ORDER BY c.numero DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Comprobantes> listComprobantes = query.getResultList();
            return listComprobantes;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaComprobantes.comprobantesEmpleado)" + e);
            return null;
        }
    }
    
    @Override
    public BigInteger numeroMaximoComprobante() {
        try {
            Query query = em.createQuery("SELECT MAX(c.numero) FROM Comprobantes c");
            BigInteger max = (BigInteger) query.getSingleResult();
            return max;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaComprobantes.numeroMaximoComprobante)" + e);
            return null;
        }
    }
}

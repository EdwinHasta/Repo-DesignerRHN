/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Comprobantes;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Comprobantes'
 * de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaComprobantes implements PersistenciaComprobantesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Comprobantes comprobante) {
        em.persist(comprobante);
    }

    @Override
    public void editar(Comprobantes comprobante) {
        em.merge(comprobante);
    }

    @Override
    public void borrar(Comprobantes comprobante) {
        em.remove(em.merge(comprobante));
    }

    @Override
    public Comprobantes buscarComprobanteSecuencia(BigInteger secuencia) {
        return em.find(Comprobantes.class, secuencia);
    }

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

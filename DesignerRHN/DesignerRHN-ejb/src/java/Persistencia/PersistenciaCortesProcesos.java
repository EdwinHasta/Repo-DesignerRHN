package Persistencia;

import Entidades.CortesProcesos;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaCortesProcesos implements PersistenciaCortesProcesosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Corte Proceso.
     */
    @Override
    public void crear(CortesProcesos corteProceso) {
        em.persist(corteProceso);
    }

    /*
     *Editar Corte Proceso. 
     */
    @Override
    public void editar(CortesProcesos corteProceso) {
        em.merge(corteProceso);
    }

    /*
     *Borrar Corte Proceso.
     */
    @Override
    public void borrar(CortesProcesos corteProceso) {
        em.remove(em.merge(corteProceso));
    }

    /*
     *Encontrar un Corte Proceso. 
     */
    @Override
    public CortesProcesos buscarCorteProceso(Object id) {
        return em.find(CortesProcesos.class, id);
    }

    /*
     *Encontrar todas los Cortes Procesos. 
     */
    @Override
    public List<CortesProcesos> buscarCortesProcesos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(CortesProcesos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<CortesProcesos> cortesProcesosComprobante(BigInteger secuenciaComprobante) {
        try {
            Query query = em.createQuery("SELECT cp FROM CortesProcesos cp WHERE cp.comprobante.secuencia = :secuenciaComprobante");
            query.setParameter("secuenciaComprobante", secuenciaComprobante);
            List<CortesProcesos> listCortesProcesos = query.getResultList();
            return listCortesProcesos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaCortesProcesos.cortesProcesosComprobante)" + e);
            return null;
        }
    }

    public Integer contarLiquidacionesCerradas(BigInteger secProceso, String fechaDesde, String fechaHasta) {
        try {
            String sqlQuery = "SELECT nvl(COUNT(CP.SECUENCIA),0)\n"
                    + "       FROM CORTESPROCESOS CP, empleados e, comprobantes co\n"
                    + "       WHERE e.secuencia=cp.empleado\n"
                    + "       AND co.secuencia=cp.comprobante\n"
                    + "       AND co.fecha  between  To_date( ?, 'dd/mm/yyyy') and To_date( ?, 'dd/mm/yyyy')\n"
                    + "       AND CP.proceso = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, fechaDesde);
            query.setParameter(2, fechaHasta);
            query.setParameter(3, secProceso);
            BigDecimal conteo = (BigDecimal) query.getSingleResult();
            Integer conteoLiquidacionCerradas = conteo.intValueExact();
            return conteoLiquidacionCerradas;
        } catch (Exception e) {
            System.out.println("Error contarLiquidacionesCerradas. " + e);
            return null;
        }
    }
    
    public void eliminarComprobante(Short codigoProceso, String fechaDesde, String fechaHasta) {
        try {
            String sqlQuery = "call CORTESPROCESOS_PKG.ELIMINARCOMPROBANTE(?, To_date( ?, 'dd/mm/yyyy'), To_date( ?, 'dd/mm/yyyy'))";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, codigoProceso);
            query.setParameter(2, fechaDesde);
            query.setParameter(3, fechaHasta);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error cerrarLiquidacion. " + e);
        }
    }
}

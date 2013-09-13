package Persistencia;

import Entidades.SolucionesNodos;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaSolucionesNodos implements PersistenciaSolucionesNodosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Solucion Nodo.
     */
    @Override
    public void crear(SolucionesNodos solucionNodo) {
        em.persist(solucionNodo);
    }

    /*
     *Editar Solucion Nodo. 
     */
    @Override
    public void editar(SolucionesNodos solucionNodo) {
        em.merge(solucionNodo);
    }

    /*
     *Borrar Solucion Nodo.
     */
    @Override
    public void borrar(SolucionesNodos solucionNodo) {
        em.remove(em.merge(solucionNodo));
    }

    /*
     *Encontrar una Solucion Nodo. 
     */
    @Override
    public SolucionesNodos buscarSolucionNodo(Object id) {
        return em.find(SolucionesNodos.class, id);
    }

    /*
     *Encontrar todas las Soluciones Nodos. 
     */
    @Override
    public List<SolucionesNodos> buscarSolucionesNodos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SolucionesNodos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN ('PAGO','DESCUENTO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado)" + e);
            return null;
        }
    }
    
    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN  ('PASIVO','GASTO','NETO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador)" + e);
            return null;
        }
    }
}

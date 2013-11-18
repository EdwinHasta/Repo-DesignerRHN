/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaIBCSInterface;
import Entidades.Ibcs;
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
public class PersistenciaIBCS implements PersistenciaIBCSInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Ibcs vigenciasFormasPagos) {
        em.persist(vigenciasFormasPagos);
    }

    @Override
    public void editar(Ibcs vigenciasFormasPagos) {
        em.merge(vigenciasFormasPagos);
    }

    @Override
    public void borrar(Ibcs vigenciasFormasPagos) {
        em.remove(em.merge(vigenciasFormasPagos));
    }

    @Override
    public Ibcs buscarIbcs(BigInteger secuencia) {
        try {
            return em.find(Ibcs.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaIBCS formas pagos ERROR : " + e);
            return null;
        }
    }

    /**
     *
     */

    public List<Ibcs> buscarIbcsPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT ib FROM Ibcs ib WHERE ib.empleado.secuencia = :secuenciaEmpl ORDER BY ib.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<Ibcs> ibcs = query.getResultList();
            return ibcs;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaIBCS Por Empleados ERROR" + e);
            return null;
        }
    }

    @Override
    public List<Ibcs> buscarVigenciasFormasPagosEmpleado() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Ibcs.class));
        return em.createQuery(cq).getResultList();
    }
}
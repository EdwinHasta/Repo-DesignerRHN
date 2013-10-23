/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.VigenciasFormasPagos;
import InterfacePersistencia.PersistenciaVigenciasFormasPagosInterface;
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
public class PersistenciaVigenciasFormasPagos implements PersistenciaVigenciasFormasPagosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /*
     * Crear empleado.
     */

    @Override
    public void crear(VigenciasFormasPagos vigenciasFormasPagos) {
        em.persist(vigenciasFormasPagos);
    }

    /*
     *Editar empleado. 
     */
    @Override
    public void editar(VigenciasFormasPagos vigenciasFormasPagos) {
        em.merge(vigenciasFormasPagos);
    }

    /*
     *Borrar empleado.
     */
    @Override
    public void borrar(VigenciasFormasPagos vigenciasFormasPagos) {
        em.remove(em.merge(vigenciasFormasPagos));
    }

    /*
     *Encontrar una VigenciaFormasPagos. 
     */
    @Override
    public VigenciasFormasPagos buscarVigenciasNormasEmpleado(BigInteger secuencia) {
        try {
            //BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(VigenciasFormasPagos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR :");
            return null;
        }
    }

    /**
     * 
     */
    @Override
    public List<VigenciasFormasPagos> buscarVigenciasFormasPagosPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vfp FROM VigenciasFormasPagos vfp WHERE vfp.empleado.secuencia = :secuenciaEmpl ORDER BY vfp.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<VigenciasFormasPagos> vigenciasNormasEmpleados = query.getResultList();
            return vigenciasNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Formas Pagos Por Empleados " + e);
            return null;
        }
    }
       @Override
    public List<VigenciasFormasPagos> buscarVigenciasFormasPagosEmpleado() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasFormasPagos.class));
        return em.createQuery(cq).getResultList();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.VigenciasNormasEmpleados;
import InterfacePersistencia.PersistenciaVigenciasNormasEmpleadosInterface;
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
public class PersistenciaVigenciasNormasEmpleados implements PersistenciaVigenciasNormasEmpleadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /*
     * Crear empleado.
     */

    @Override
    public void crear(VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        em.persist(vigenciasNormasEmpleados);
    }

    /*
     *Editar empleado. 
     */
    @Override
    public void editar(VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        em.merge(vigenciasNormasEmpleados);
    }

    /*
     *Borrar empleado.
     */
    @Override
    public void borrar(VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        em.remove(em.merge(vigenciasNormasEmpleados));
    }

    /*
     *Encontrar una VigenciaNormasEmpleados. 
     */
    @Override
    public VigenciasNormasEmpleados buscarVigenciasNormasEmpleado(BigInteger secuencia) {
        try {
            //BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(VigenciasNormasEmpleados.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 
     */
    @Override
    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleadosEmpl(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vne FROM VigenciasNormasEmpleados vne WHERE vne.empleado.secuencia = :secuenciaEmpl ORDER BY vne.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<VigenciasNormasEmpleados> vigenciasNormasEmpleados = query.getResultList();
            return vigenciasNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Normas Empleados " + e);
            return null;
        }
    }
       @Override
    public List<VigenciasNormasEmpleados> buscarVigenciasNormasEmpleados() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasNormasEmpleados.class));
        return em.createQuery(cq).getResultList();
    }
}

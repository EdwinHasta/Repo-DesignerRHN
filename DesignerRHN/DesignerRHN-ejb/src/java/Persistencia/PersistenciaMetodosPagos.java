/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.MetodosPagos;
import InterfacePersistencia.PersistenciaMetodosPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaMetodosPagos implements PersistenciaMetodosPagosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /*
     * Crear empleado.
     */

    @Override
    public void crear(MetodosPagos metodosPagos) {
        em.persist(metodosPagos);
    }

    /*
     *Editar empleado. 
     */
    @Override
    public void editar(MetodosPagos metodosPagos) {
        em.merge(metodosPagos);
    }

    /*
     *Borrar empleado.
     */
    @Override
    public void borrar(MetodosPagos metodosPagos) {
        em.remove(em.merge(metodosPagos));
    }

    /*
     *Encontrar una VigenciaFormasPagos. 
     */
    @Override
    public MetodosPagos buscarMetodosPagosEmpleado(BigInteger secuencia) {
        try {
            //BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(MetodosPagos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : " + e);
            return null;
        }
    }

    /**
     *
     */
    @Override
    public List<MetodosPagos> buscarMetodosPagos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(MetodosPagos.class));
        return em.createQuery(cq).getResultList();
    }
}

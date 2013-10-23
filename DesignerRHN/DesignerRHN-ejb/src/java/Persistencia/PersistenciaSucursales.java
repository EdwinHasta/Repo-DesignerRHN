/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Sucursales;
import InterfacePersistencia.PersistenciaSucursalesInterface;
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
public class PersistenciaSucursales implements PersistenciaSucursalesInterface {

       @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /*
     * Crear empleado.
     */

    @Override
    public void crear(Sucursales sucursales) {
        em.persist(sucursales);
    }

    /*
     *Editar empleado. 
     */
    @Override
    public void editar(Sucursales sucursales) {
        em.merge(sucursales);
    }

    /*
     *Borrar empleado.
     */
    @Override
    public void borrar(Sucursales sucursales) {
        em.remove(em.merge(sucursales));
    }

    /*
     *Encontrar un empleado. 
     */
    @Override
    public Sucursales buscarSucursal(BigInteger secuenciaS) {
        try {
            //BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Sucursales.class, secuenciaS);
        } catch (Exception e) {
            System.out.println("Persistencia Sucursales "+e);
            return null;
        }
    }

    @Override
    public List<Sucursales> buscarSucursales() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Sucursales.class));
        return em.createQuery(cq).getResultList();
    }
}

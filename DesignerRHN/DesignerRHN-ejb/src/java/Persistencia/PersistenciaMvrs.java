/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Mvrs;
import InterfacePersistencia.PersistenciaMvrsInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaMvrs implements PersistenciaMvrsInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(Mvrs mvrs) {
        em.persist(mvrs);
    }

    /*
     */
    @Override
    public void editar(Mvrs mvrs) {
        em.merge(mvrs);
    }

    /*
     */
    @Override
    public void borrar(Mvrs mvrs) {
        em.remove(em.merge(mvrs));
    }

    /*
     */
    @Override
    public Mvrs buscarMvr(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(Mvrs.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     */
    @Override
    public List<Mvrs> buscarMvrs() {
        List<Mvrs> mvrs = (List<Mvrs>) em.createNamedQuery("Mvrs.findAll").getResultList();
        return mvrs;
    }

    @Override
    public Mvrs buscarMvrSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT mvrs FROM Mvrs mvrs WHERE mvrs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Mvrs mvrs = (Mvrs) query.getSingleResult();
            return mvrs;
        } catch (Exception e) {
            Mvrs mvrs = null;
            return mvrs;
        }
    }

    @Override
    public List<Mvrs> buscarMvrsEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT mvrs FROM Mvrs mvrs WHERE mvrs.empleado.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            List<Mvrs> mvrs = (List<Mvrs>) query.getResultList();
            return mvrs;
        } catch (Exception e) {
            List<Mvrs> mvrs = null;
            return mvrs;
        }
    }
}
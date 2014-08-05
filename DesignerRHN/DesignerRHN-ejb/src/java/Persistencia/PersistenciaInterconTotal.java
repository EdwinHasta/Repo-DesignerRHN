/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.InterconTotal;
import InterfacePersistencia.PersistenciaInterconTotalInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
@Stateless
public class PersistenciaInterconTotal implements PersistenciaInterconTotalInterface {

    @Override
    public void crear(EntityManager em, InterconTotal interconTotal) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconTotal);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.crear: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, InterconTotal interconTotal) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconTotal);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.editar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, InterconTotal interconTotal) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(interconTotal));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.borrar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public InterconTotal buscarInterconTotalSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT i FROM InterconTotal i WHERE i.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            InterconTotal interconTotal = (InterconTotal) query.getSingleResult();
            return interconTotal;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.buscarInterconTotalSecuencia: " + e.toString());
            return null;
        }
    }

    @Override
    public List<InterconTotal> buscarInterconTotalParaParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            em.clear();
            String sql = "select * from InterconTotal i where fechacontabilizacion between ? and ?\n"
                    + " and FLAG = 'CONTABILIZADO' AND SALIDA <> 'NETO'\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=i.empleado)";
            Query query = em.createNativeQuery(sql, InterconTotal.class);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<InterconTotal> interconTotal = query.getResultList();
            return interconTotal;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.buscarInterconTotalParaParametroContable: " + e.toString());
            return null;
        }
    }

}

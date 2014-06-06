/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.EersPrestamos;
import InterfacePersistencia.PersistenciaEersPrestamosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaEersPrestamos implements PersistenciaEersPrestamosInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,EersPrestamos eersPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(eersPrestamos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEersPrestamos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em,EersPrestamos eersPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(eersPrestamos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEersPrestamos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em,EersPrestamos eersPrestamos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(eersPrestamos));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaEersPrestamos.borrar: " + e);
            }
        }
    }

    @Override
    public List<EersPrestamos> eersPrestamosEmpleado(EntityManager em,BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("select e FROM EersPrestamos e where e.tipoeer ='EMBARGO'AND EXISTS (SELECT em FROM Empleados em WHERE em.secuencia = e.empleado.secuencia and e.empleado.secuencia = :secuenciaEmpleado)");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EersPrestamos> eersPrestamos = query.getResultList();
            List<EersPrestamos> eersPrestamosResult = new ArrayList<EersPrestamos>(eersPrestamos);
            return eersPrestamosResult;
        } catch (Exception e) {
            System.out.println("Error: (eersPrestamos)" + e);
            return null;
        }
    }
}

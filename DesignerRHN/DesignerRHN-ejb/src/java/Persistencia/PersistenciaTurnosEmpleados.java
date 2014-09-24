/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTurnosEmpleadosInterface;
import java.math.BigInteger;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Administrador
 */
@Stateless
public class PersistenciaTurnosEmpleados implements PersistenciaTurnosEmpleadosInterface {

    @Override
    public Date obtenerFechaInicialMinimaTurnosEmpleados(EntityManager em) {
        try {
            em.clear();
            String sql = "select  min(fechainicial) from turnosempleados where procesado='N'";
            Query query = em.createNativeQuery(sql);
            Date fecha = (Date) query.getSingleResult();
            return fecha;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTurnosEmpleados.obtenerFechaInicialMinimaTurnosEmpleados : " + e.toString());
            return null;
        }
    }

    @Override
    public Date obtenerFechaFinalMaximaTurnosEmpleados(EntityManager em) {
        try {
            em.clear();
            String sql = "select  max(fechafinal) from turnosempleados where procesado='N'";
            Query query = em.createNativeQuery(sql);
            Date fecha = (Date) query.getSingleResult();
            return fecha;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTurnosEmpleados.obtenerFechaFinalMaximaTurnosEmpleados : " + e.toString());
            return null;
        }
    }
    
    @Override
    public int ejecutarPKG_CONTARNOVEDADESLIQ(EntityManager em,Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "call TURNOSEMPLEADOS_PKG.CONTARNOVEDADESLIQ(?,?,?,?)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaDesde);
            query.setParameter(2, fechaHasta);
            query.setParameter(3, emplDesde);
            query.setParameter(4, emplHasta);
            int dato = query.executeUpdate();
            tx.commit();
            return dato;
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosTiempos.EjecutarPKG_CONTARNOVEDADESLIQ : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
            return -1;
        }
    }
    
    @Override
    public void ejecutarPKG_ELIMINARLIQUIDACION(EntityManager em,Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "call TURNOSEMPLEADOS_PKG.ELIMINARLIQUIDACION(?,?,?,?)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaDesde);
            query.setParameter(2, fechaHasta);
            query.setParameter(3, emplDesde);
            query.setParameter(4, emplHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametrosTiempos.EjecutarPKG_ELIMINARLIQUIDACION : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
}

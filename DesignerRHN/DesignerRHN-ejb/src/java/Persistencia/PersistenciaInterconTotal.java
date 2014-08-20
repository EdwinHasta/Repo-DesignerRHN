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
            String sql = "select * from intercon_total i where fechacontabilizacion between ? and ?\n"
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

    @Override
    public Date obtenerFechaContabilizacionMaxInterconTotal(EntityManager em) {
        try {
            String sql = "select max(i.fechacontabilizacion) from intercon_total i "
                    + " where flag = 'ENVIADO' and exists (select 'x' from  empleados e\n"
                    + " where e.secuencia=i.empleado )";
            Query query = em.createNativeQuery(sql);
            Date fecha = (Date) query.getSingleResult();
            return fecha;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.obtenerFechaContabilizacionMaxInterconTotal : " + e.toString());
            return null;
        }
    }

    @Override
    public void actualizarFlagInterconTotal(EntityManager em, Date fechaInicial, Date fechaFinal, Short empresa) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "UPDATE intercon_TOTAL SET FLAG = 'CONTABILIZADO' \n"
                    + " WHERE FECHACONTABILIZACION BETWEEN ? AND ? \n"
                    + " AND FLAG = 'ENVIADO' \n"
                    + " AND intercon_total.empresa_codigo=? \n"
                    + " AND EXISTS (SELECT 'X' FROM EMPLEADOS E WHERE E.SECUENCIA=INTERCON_TOTAL.EMPLEADO)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, empresa);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.actualizarFlagInterconTotal. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void actualizarFlagInterconTotalProcesoDeshacer(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "UPDATE CONTABILIZACIONES SET FLAG='GENERADO' WHERE FLAG='CONTABILIZADO'\n"
                    + " AND FECHAGENERACION BETWEEN ? AND ? \n"
                    + " and exists (select 'x' from vwfempleados e, solucionesnodos sn where sn.empleado=e.secuencia and sn.secuencia=contabilizaciones.solucionnodo\n"
                    + " and sn.proceso=nvl(?,sn.proceso))";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.actualizarFlagInterconTotalProcesoDeshacer. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void eliminarInterconTotal(EntityManager em, Date fechaInicial, Date fechaFinal, Short empresa, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "DELETE INTERCON_TOTAL\n"
                    + " WHERE FECHACONTABILIZACION BETWEEN ? AND ?\n"
                    + " AND FLAG='CONTABILIZADO'\n"
                    + " and intercon_total.empresa_codigo=?\n"
                    + " AND nvl(INTERCON_TOTAL.PROCESO,0) = NVL(?,nvl(INTERCON_TOTAL.PROCESO,0))\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=intercon_total.empleado)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, empresa);
            query.setParameter(4, proceso);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.eliminarInterconTotal. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void ejecutarPKGUbicarnuevointercon_total(EntityManager em, BigInteger secuencia, Date fechaInicial, Date fechaFinal, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "call INTERFASETOTAL$PKG.Ubicarnuevointercon_total(?,?,?,?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            query.setParameter(2, fechaInicial);
            query.setParameter(3, fechaFinal);           
            query.setParameter(4, proceso);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.ejecutarPKGUbicarnuevointercon_total. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public int contarProcesosContabilizadosInterconTotal(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            em.clear();
            String sql = "select COUNT(*) from intercon_total i where\n"
                    + " i.fechacontabilizacion between ? and ? \n"
                    + " and i.flag = 'CONTABILIZADO'";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            BigInteger contador = (BigInteger) query.getSingleResult();
            if (contador != null) {
                return contador.intValue();
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.contarProcesosContabilizadosInterconTotal. " + e.toString());
            return -1;
        }
    }

    public void ejecutarCierrePeriodoContableInterconTotal(EntityManager em, Date fechaInicial, Date fechaFinal, Short empresa, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "UPDATE INTERCON_TOTAL I SET I.FLAG='ENVIADO'\n"
                    + " WHERE  \n"
                    + " I.FECHACONTABILIZACION BETWEEN ? AND ?\n"
                    + " and nvl(i.proceso,0) = nvl(?,nvl(i.proceso,0))\n"
                    + " and i.empresa_codigo=?\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=i.empleado)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, empresa);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.ejecutarCierrePeriodoContableInterconTotal. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
    
    public void cerrarProcesoContabilizacion(EntityManager em, Date fechaInicial, Date fechaFinal, Short empresa, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "UPDATE INTERCON_TOTAL I SET I.FLAG='ENVIADO' WHERE  \n" +
                        "     I.FECHACONTABILIZACION BETWEEN :PARAMETROSCONTABLES.FECHAINICIALCONTABILIZACION AND :PARAMETROSCONTABLES.FECHAFINALCONTABILIZACION\n" +
                        "     and nvl(i.proceso,0) = nvl(:PARAMETROSCONTABLES.proceso,nvl(i.proceso,0))\n" +
                        "     and i.empresa_codigo=:PARAMETROSCONTABLES.empresa_codigo"
                        + "   and exists (select 'x' from empleados e where e.secuencia=i.empleado);";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, empresa);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconTotal.cerrarProcesoContabilizacion. " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

}

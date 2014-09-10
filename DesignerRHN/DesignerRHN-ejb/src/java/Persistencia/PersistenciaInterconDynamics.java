package Persistencia;

import Entidades.InterconDynamics;
import InterfacePersistencia.PersistenciaInterconDynamicsInterface;
import java.math.BigDecimal;
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
public class PersistenciaInterconDynamics implements PersistenciaInterconDynamicsInterface {

    @Override
    public void crear(EntityManager em, InterconDynamics interconDynamics) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconDynamics);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.crear: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, InterconDynamics interconDynamics) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(interconDynamics);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.editar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, InterconDynamics interconDynamics) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(interconDynamics));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.borrar: " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public InterconDynamics buscarInterconDynamicSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT i FROM InterconDynamics i WHERE i.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            InterconDynamics intercon = (InterconDynamics) query.getSingleResult();
            return intercon;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.buscarInterconDynamicSecuencia: " + e.toString());
            return null;
        }
    }

    @Override
    public List<InterconDynamics> buscarInterconDynamicParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            em.clear();
            String sql = "select * from intercon_dynamics i where fechacontabilizacion between \n"
                    + " ? and ?\n"
                    + " and FLAG = 'CONTABILIZADO' AND SALIDA <> 'NETO'\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=i.empleado)";
            Query query = em.createNativeQuery(sql, InterconDynamics.class);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<InterconDynamics> intercon = query.getResultList();
            return intercon;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.buscarInterconDynamicParametroContable: " + e.toString());
            return null;
        }
    }

    @Override
    public int contarProcesosContabilizadosInterconDynamics(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            em.clear();
            String sql = "select COUNT(*) from intercon_dynamics i where\n"
                    + " i.fechacontabilizacion between ? and ? \n"
                    + " and i.flag = 'CONTABILIZADO'";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            BigDecimal contador = (BigDecimal) query.getSingleResult();
            if (contador != null) {
                return contador.intValue();
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.contarProcesosContabilizadosInterconDynamics. " + e.toString());
            return -1;
        }
    }

    @Override
    public void cerrarProcesoContabilizacion(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger empleadoDesde, BigInteger empleadoHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "UPDATE INTERCON_DYNAMICS I SET I.FLAG='ENVIADO'\n"
                    + "     WHERE  \n"
                    + "     I.FECHACONTABILIZACION BETWEEN ? AND ?\n"
                    + "     and nvl(i.proceso,0) = nvl(?,nvl(i.proceso,0))\n"
                    + "     and exists (select 'x' from empleados e where e.secuencia=i.empleado\n"
                    + "                 and e.codigoempleado between nvl(?,0)\n"
                    + "                                          and nvl(?,99999999999999))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, empleadoDesde);
            query.setParameter(5, empleadoHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.cerrarProcesoContabilizacion : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void ejecutarPKGCrearArchivoPlano(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, String descripcionProceso, String nombreArchivo, BigInteger empleadoDesde, BigInteger empleadoHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "call INTERFASEDYNAMICS$PKG.enviar(?,?,?,?, ?,nvl(?,0), nvl(?,999999999999999))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, descripcionProceso);
            query.setParameter(5, nombreArchivo);
            query.setParameter(6, empleadoDesde);
            query.setParameter(7, empleadoHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.ejecutarPKGCrearArchivoPlano : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void ejecutarPKGRecontabilizar(EntityManager em, Date fechaInicial, Date fechaFinal) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "call INTERFASEDYNAMICS$PKG.Recontabilizacion(?,?)";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.ejecutarPKGRecontabilizar : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void actualizarFlagContabilizacionDeshacerDynamics(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "UPDATE CONTABILIZACIONES\n"
                    + "		 SET FLAG='GENERADO'\n"
                    + "		 WHERE FLAG='CONTABILIZADO'\n"
                    + "		 AND FECHAGENERACION BETWEEN ? AND ? \n"
                    + "		 AND \n"
                    + "         EXISTS \n"
                    + "         (SELECT 'X' \n"
                    + "         FROM INTERCON_DYNAMICS IT \n"
                    + "         WHERE IT.CONTABILIZACION = CONTABILIZACIONES.SECUENCIA \n"
                    + "		     and FECHACONTABILIZACION BETWEEN ? AND ?\n"
                    + "         AND FLAG = 'CONTABILIZADO'\n"
                    + "         AND nvl(IT.PROCESO,0) = NVL(?,nvl(IT.PROCESO,0))\n"
                    + "         and exists (select 'x' from empleados e where e.secuencia=it.empleado\n"
                    + "                     and e.codigoempleado between nvl(?,0)\n"
                    + "                                          and nvl(?,99999999999999)))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, fechaInicial);
            query.setParameter(5, fechaFinal);
            query.setParameter(6, emplDesde);
            query.setParameter(7, emplHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.actualizarFlagContabilizacionDeshacerDynamics : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void deleteInterconDynamics(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "DELETE INTERCON_DYNAMICS\n"
                    + "		    WHERE FECHACONTABILIZACION BETWEEN ? AND ?\n"
                    + "		    AND FLAG='CONTABILIZADO'\n"
                    + "        AND nvl(INTERCON_DYNAMICS.PROCESO,0) = NVL(?,nvl(INTERCON_DYNAMICS.PROCESO,0))\n"
                    + "        and exists (select 'x' from empleados e where e.secuencia=intercon_DYNAMICS.empleado\n"
                    + "                    and e.codigoempleado between nvl(?,0)\n"
                    + "                                          and nvl(?,99999999999999))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.setParameter(4, emplDesde);
            query.setParameter(5, emplHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.deleteInterconDynamics : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void ejecutarPKGUbicarnuevointercon_DYNAMICS(EntityManager em, BigInteger secuencia, Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "call INTERFASEDYNAMICS$PKG.Ubicarnuevointercon_DYNAMICS(?,?,?,?, nvl(?,0), nvl(?,999999999999999))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, secuencia);
            query.setParameter(2, fechaInicial);
            query.setParameter(3, fechaFinal);
            query.setParameter(4, proceso);
            query.setParameter(5, emplDesde);
            query.setParameter(6, emplHasta);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.ejecutarPKGUbicarnuevointercon_DYNAMICS : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void anularComprobantesCerrados(EntityManager em, Date fechaInicial, Date fechaFinal, BigInteger proceso) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            String sql = "UPDATE intercon_DYNAMICS IT SET FLAG = 'CONTABILIZADO'\n"
                    + "		  WHERE FECHACONTABILIZACION BETWEEN ? AND ? \n"
                    + "		  AND FLAG = 'ENVIADO'\n"
                    + "		  and exists (select 'x' from empleados e where e.secuencia=IT.empleado)\n"
                    + "         AND nvl(IT.PROCESO,0) = NVL(?,nvl(IT.PROCESO,0))";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            query.setParameter(3, proceso);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.anularComprobantesCerrados : " + e.toString());
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public Date obtenerFechaContabilizacionMaxInterconDynamics(EntityManager em) {
        try {
            em.clear();
            String sql = "select max(i.fechacontabilizacion)\n"
                    + "  from intercon_DYNAMICS i where flag = 'ENVIADO'";
            Query query = em.createNativeQuery(sql);
            Date fecha = (Date) query.getSingleResult();
            return fecha;
        } catch (Exception e) {
            System.out.println("Error PersistenciaInterconDynamics.obtenerFechaContabilizacionMaxInterconDynamics : " + e.toString());
            return null;
        }
    }

}

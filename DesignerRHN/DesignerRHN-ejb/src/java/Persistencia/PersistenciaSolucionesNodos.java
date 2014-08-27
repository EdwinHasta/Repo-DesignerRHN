/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.SolucionesNodos;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'SolucionesNodos' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaSolucionesNodos implements PersistenciaSolucionesNodosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, SolucionesNodos solucionNodo) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(solucionNodo);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSolucionesNodos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, SolucionesNodos solucionNodo) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(solucionNodo);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSolucionesNodos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, SolucionesNodos solucionNodo) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(solucionNodo));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaSolucionesNodos.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<SolucionesNodos> buscarSolucionesNodos(EntityManager em) {
        em.clear();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SolucionesNodos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleado(EntityManager em, BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN ('PAGO','DESCUENTO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado)" + e);
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleador(EntityManager em, BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN  ('PASIVO','GASTO','NETO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador)" + e);
            return null;
        }
    }

    @Override
    public BigDecimal diasProvisionados(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT SN1.unidades "
                    + "FROM SolucionesNodos SN1, Conceptos c "
                    + "WHERE SN1.empleado.secuencia =:secuenciaempleado "
                    + "AND c.secuencia=SN1.concepto.secuencia "
                    + "AND SN1.tipo='PASIVO' "
                    + "AND SN1.estado='CERRADO' "
                    + "AND SN1.concepto.secuencia=(SELECT GC.concepto.secuencia "
                    + "FROM  GruposProvisiones GC "
                    + "WHERE GC.nombre='VACACIONES' "
                    + "AND GC.empresa.secuencia=c.empresa.secuencia) "
                    + "AND SN1.fechahasta = (SELECT MAX(SN2.fechahasta) "
                    + "FROM SolucionesNodos SN2 "
                    + "WHERE SN2.fechadesde<=CURRENT_DATE "
                    + "AND SN1.empleado.secuencia=SN2.empleado.secuencia "
                    + "AND SN1.concepto.secuencia=SN2.concepto.secuencia "
                    + "AND SN2.tipo = 'PASIVO' "
                    + "AND SN2.estado='CERRADO')");
            query.setParameter("secuenciaempleado", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            BigDecimal llegada = (BigDecimal) query.getSingleResult();
            return llegada;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.diasProvisionados)" + e.toString());
            return null;
        }
    }

    @Override
    public Long validacionTercerosVigenciaAfiliacion(EntityManager em, BigInteger secuencia, Date fechaInicial, BigDecimal secuenciaTE, BigInteger secuenciaTer) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT count(v)  "
                    + "FROM SolucionesNodos v "
                    + "where v.fechapago > :fechaInicial "
                    + "AND v.empleado.secuencia = :secuencia "
                    + "AND v.estado ='CERRADO' "
                    + "AND v.nit.secuencia != :secuenciaTer "
                    + "AND exists (SELECT cs "
                    + "FROM ConceptosSoportes cs "
                    + "WHERE cs.concepto.secuencia = v.concepto.secuencia "
                    + "AND cs.tipoentidad.secuencia = :secuenciaT "
                    + "and cs.tipo='AUTOLIQUIDACION' "
                    + "AND cs.subgrupo='COTIZACION')");
            query.setParameter("secuencia", secuencia);
            query.setParameter("fechaInicial", fechaInicial);
            query.setParameter("secuenciaT", secuenciaTE);
            query.setParameter("secuenciaTer", secuenciaTer);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long r = (Long) query.getSingleResult();
            System.out.println("Resultado : " + r);
            return r;
        } catch (Exception e) {
            System.out.println("Error validacionTercerosVigenciaAfiliacion Persistencia : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoEmpleado(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT sn "
                    + "FROM SolucionesNodos sn "
                    + "WHERE sn.estado = 'LIQUIDADO' "
                    + "AND sn.tipo IN ('PAGO','DESCUENTO') "
                    + "AND sn.empleado.secuencia = :secuenciaEmpleado "
                    + "ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoEmpleado)" + e);
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoEmpleador(EntityManager em, BigInteger secuenciaEmpleado) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT sn "
                    + "FROM SolucionesNodos sn "
                    + "WHERE sn.estado = 'LIQUIDADO' "
                    + "AND sn.tipo IN  ('PASIVO','GASTO','NETO') "
                    + "AND sn.valor <> 0 AND sn.empleado.secuencia = :secuenciaEmpleado "
                    + "ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoEmpleador)" + e);
            return null;
        }
    }

    @Override
    public Integer ContarProcesosSN(EntityManager em, BigInteger secProceso) {
        try {
            em.clear();
            String sqlQuery = "SELECT COUNT(distinct empleado)\n"
                    + "  FROM solucionesnodos sn\n"
                    + "  WHERE estado='LIQUIDADO'\n"
                    + "  AND sn.proceso = ?\n"
                    + "  AND exists (select 'x' from parametros p, usuarios u\n"
                    + "              where u.secuencia = p.usuario\n"
                    + "              and p.proceso = sn.proceso\n"
                    + "              AND p.empleado = sn.empleado)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secProceso);
            BigDecimal conteo = (BigDecimal) query.getSingleResult();
            Integer conteoProcesosSN = conteo.intValueExact();
            return conteoProcesosSN;
        } catch (Exception e) {
            System.out.println("Error ContarProcesosSN. " + e);
            return null;
        }
    }

    @Override
    public boolean solucionesNodosParaConcepto(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT count(sn) FROM SolucionesNodos sn WHERE sn.concepto.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long valor = (Long) query.getSingleResult();
            return valor > 0;
        } catch (Exception e) {
            System.out.println("Error solucionesNodosParaConcepto PersistenciaSolucionesNodos : " + e.toString());
            return false;
        }
    }

    @Override
    public List<SolucionesNodos> buscarSolucionesNodosParaParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            em.clear();
            String sql = "select * from SolucionesNodos s WHERE EXISTS (SELECT 'X' FROM contabilizaciones C\n"
                    + " where C.flag='GENERADO' and C.fechageneracion between ? and ? \n"
                    + " and c.solucionnodo = s.secuencia)\n"
                    + " AND  EXISTS (SELECT 'X' FROM  cortesprocesos cp , procesos p WHERE  cp.secuencia = s.corteproceso\n"
                    + " AND p.secuencia = cp.proceso AND CONTABILIZACION = 'S')\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=s.empleado) and s.valor <> 0";
            Query query = em.createNativeQuery(sql, SolucionesNodos.class);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<SolucionesNodos> soluciones = query.getResultList();
            return soluciones;
        } catch (Exception e) {
            System.out.println("Error buscarSolucionesNodosParaParametroContable PersistenciaSolucionesNodos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> buscarSolucionesNodosParaParametroContable_SAP(EntityManager em, Date fechaInicial, Date fechaFinal) {
        try {
            System.out.println("Entre al metodo solucionesnodos");
            em.clear();
            String sql = "select * from SolucionesNodos s WHERE EXISTS (SELECT 'X' FROM contabilizaciones C\n"
                    + "              where C.flag='GENERADO' and C.fechageneracion \n"
                    + "               between ? and ?\n"
                    + "                   and c.solucionnodo = s.secuencia)\n"
                    + " AND  EXISTS (SELECT 'X' FROM  cortesprocesos cp , procesos p WHERE  cp.secuencia = s.corteproceso\n"
                    + " AND p.secuencia = cp.proceso AND CONTABILIZACION = 'S')\n"
                    + " and exists (select 'x' from empleados e where e.secuencia=s.empleado)";
            Query query = em.createNativeQuery(sql, SolucionesNodos.class);
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<SolucionesNodos> soluciones = query.getResultList();
            if (soluciones != null) {
                System.out.println("Lista Soluciones Nodos soluciones : "+soluciones.size());
            } else {
                System.out.println("Lista Nula Soluciones Nodos");
            }
            return soluciones;
        } catch (Exception e) {
            System.out.println("Error buscarSolucionesNodosParaParametroContable_SAP PersistenciaSolucionesNodos : " + e.toString());
            return null;
        }
    }
}

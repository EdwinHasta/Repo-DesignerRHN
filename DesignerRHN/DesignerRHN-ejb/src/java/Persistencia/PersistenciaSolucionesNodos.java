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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'SolucionesNodos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaSolucionesNodos implements PersistenciaSolucionesNodosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(SolucionesNodos solucionNodo) {
        em.persist(solucionNodo);
    }

    @Override
    public void editar(SolucionesNodos solucionNodo) {
        em.merge(solucionNodo);
    }

    @Override
    public void borrar(SolucionesNodos solucionNodo) {
        em.remove(em.merge(solucionNodo));
    }

    @Override
    public List<SolucionesNodos> buscarSolucionesNodos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SolucionesNodos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN ('PAGO','DESCUENTO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleado)" + e);
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn FROM SolucionesNodos sn WHERE sn.estado = 'CERRADO' AND sn.tipo IN  ('PASIVO','GASTO','NETO') AND sn.corteproceso.secuencia = :secuenciaCorteProceso AND sn.empleado.secuencia = :secuenciaEmpleado ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaCorteProceso", secuenciaCorteProceso);
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoCorteProcesoEmpleador)" + e);
            return null;
        }
    }

    @Override
    public BigDecimal diasProvisionados(BigInteger secuencia) {
        try {
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
            BigDecimal llegada = (BigDecimal) query.getSingleResult();
            return llegada;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.diasProvisionados)" + e.toString());
            return null;
        }
    }

    @Override
    public Long validacionTercerosVigenciaAfiliacion(BigInteger secuencia, Date fechaInicial, BigDecimal secuenciaTE, BigInteger secuenciaTer) {
        try {
            Query query = em.createQuery("SELECT count(v)  "
                                       + "FROM SolucionesNodos v "
                                       + "where v.fechapago > :fechaInicial "
                                       + "AND v.empleado.secuencia = :secuencia "
                                       + "AND v.estado ='CERRADO' "
                                       + "AND v.tercero.secuencia != :secuenciaTer "
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
            Long r = (Long) query.getSingleResult();
            System.out.println("Resultado : " + r);
            return r;
        } catch (Exception e) {
            System.out.println("Error validacionTercerosVigenciaAfiliacion Persistencia : " + e.toString());
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn "
                                       + "FROM SolucionesNodos sn "
                                       + "WHERE sn.estado = 'LIQUIDADO' "
                                       + "AND sn.tipo IN ('PAGO','DESCUENTO') "
                                       + "AND sn.empleado.secuencia = :secuenciaEmpleado "
                                       + "ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoEmpleado)" + e);
            return null;
        }
    }

    @Override
    public List<SolucionesNodos> solucionNodoEmpleador(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT sn "
                                       + "FROM SolucionesNodos sn "
                                       + "WHERE sn.estado = 'LIQUIDADO' "
                                       + "AND sn.tipo IN  ('PASIVO','GASTO','NETO') "
                                       + "AND sn.valor <> 0 AND sn.empleado.secuencia = :secuenciaEmpleado "
                                       + "ORDER BY sn.concepto.codigo ASC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<SolucionesNodos> listSolucionesNodos = query.getResultList();
            return listSolucionesNodos;
        } catch (Exception e) {
            System.out.println("Error: (PersistenciaSolucionesNodos.solucionNodoEmpleador)" + e);
            return null;
        }
    }

    @Override
    public Integer ContarProcesosSN(BigInteger secProceso) {
        try {
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
}

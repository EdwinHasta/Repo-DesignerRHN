/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.ConsultasLiquidaciones;
import InterfacePersistencia.PersistenciaConsultasLiquidacionesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class PersistenciaConsultasLiquidaciones implements PersistenciaConsultasLiquidacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<ConsultasLiquidaciones> liquidacionesCerradas(String fechaInicial, String fechaFinal) {
        try {
            System.out.println(fechaInicial);
            System.out.println(fechaFinal);
            String sqlQuery = "select --+ rule\n"
                    + " emp.secuencia ID, emp.codigo EMPRESACODIGO, co.fecha CORTE, p.descripcion PROCESO, p.codigo CODIGO, count(distinct cp.empleado) TOTAL\n"
                    + " from  empresas emp, procesos p, cortesprocesos cp, empleados e, comprobantes co\n"
                    + " where co.fecha between To_date(?, 'dd/mm/yyyy') AND To_date(?, 'dd/mm/yyyy')\n"
                    + " and cp.proceso=p.secuencia\n"
                    + " and e.secuencia = cp.empleado\n"
                    + " and co.secuencia=cp.comprobante\n"
                    + " and emp.secuencia=e.empresa\n"
                    + " and exists (select 'x' from solucionesnodos sn where sn.empleado=e.secuencia and sn.corteproceso=cp.secuencia)\n"
                    + " group by emp.secuencia, emp.codigo, p.descripcion, co.fecha, p.codigo"
                    + " order by p.codigo";
            Query query = em.createNativeQuery(sqlQuery, "ConsultasLiquidacionesEmpresa");
            query.setParameter(1, fechaInicial);
            query.setParameter(2, fechaFinal);
            List<ConsultasLiquidaciones> listaLiquidacionesCerradas = query.getResultList();
            return listaLiquidacionesCerradas;
        } catch (Exception e) {
            System.out.println("Error PersistenciaConsultasLiquidaciones.liquidacionesCerradas. " + e);
            return null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaRelacionesIncapacidadesInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaRelacionesIncapacidades implements PersistenciaRelacionesIncapacidadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    //Trae las relaciones en base al ausentismo seleccionado
    public String relaciones(BigInteger secuenciaAusentismo) {
        try {
            String sqlQuery = ("SELECT COUNT(ri.ano||'.'||ri.mes) FROM RelacionesIncapacidades ri, Soausentismos so WHERE ri.soausentismo = so.secuencia AND so.secuencia = ?");
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuenciaAusentismo);
            BigDecimal resultadoConteo = (BigDecimal) query.getSingleResult();
            int resultado = resultadoConteo.intValueExact();
            if (resultado > 0) {
                sqlQuery = ("SELECT ri.ano||'.'||ri.mes FROM RelacionesIncapacidades ri, Soausentismos so WHERE ri.soausentismo = so.secuencia AND so.secuencia = ?");
                query = em.createNativeQuery(sqlQuery);
                query.setParameter(1, secuenciaAusentismo);
                String relacion = (String) query.getSingleResult();
                return relacion;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error: ( Relaciones)" + e.toString());
            return null;
        }
    }
}

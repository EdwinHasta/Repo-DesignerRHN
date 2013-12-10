/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Historiasformulas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHistoriasformulas implements PersistenciaHistoriasformulasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public BigInteger obtenerSecuenciaHistoriaFormula(BigInteger secFormula, String fecha) {
        try {
            String sqlQuery = "SELECT hf.secuencia\n"
                    + "FROM historiasformulas hf\n"
                    + "WHERE hf.formula = ?\n"
                    + "AND hf.fechainicial = (select max(hfi.fechainicial)\n"
                    + "                    from historiasformulas hfi\n"
                    + "                    where hfi.formula=hf.formula\n"
                    + "                    and hfi.fechainicial <= to_date( ?, 'dd/mm/yyyy'))";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secFormula);
            query.setParameter(2, fecha);
            BigDecimal secuencia = (BigDecimal) query.getSingleResult();
            BigInteger secHistoriaFormula = secuencia.toBigIntegerExact();
            return secHistoriaFormula;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHistoriasformulas.obtenerSecuenciaHistoriaFormula" + e);
            return null;
        }
    }
}

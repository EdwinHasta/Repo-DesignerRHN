package Persistencia;

import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaHistoriasformulas implements PersistenciaHistoriasformulasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public BigInteger obtenerSecuenciaHistoriaFormula(BigInteger secFormula, String fechaDesde) {
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
            query.setParameter(2, fechaDesde);
            BigDecimal secuencia = (BigDecimal) query.getSingleResult();
            BigInteger secHistoriaFormula = secuencia.toBigIntegerExact();
            return secHistoriaFormula;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHistoriasformulas.obtenerSecuenciaHistoriaFormula" + e);
            return null;
        }
    }
}

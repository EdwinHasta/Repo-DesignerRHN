/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Historiasformulas;
import InterfacePersistencia.PersistenciaHistoriasformulasInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
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
    public void crear(Historiasformulas historiasformulas) {
        em.persist(historiasformulas);
    }

    @Override
    public void editar(Historiasformulas historiasformulas) {
        em.merge(historiasformulas);
    }

    @Override
    public void borrar(Historiasformulas historiasformulas) {
        em.remove(em.merge(historiasformulas));
    }

    @Override
    public Historiasformulas buscarHistoriaformula(BigInteger secuencia) {
        try {
            return em.find(Historiasformulas.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la PersistenciaHistoriasformulas  buscarHistoriaformula : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Historiasformulas> historiasFormulasParaFormulaSecuencia(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT hf FROM Historiasformulas hf WHERE hf.formula.secuencia=:secuencia");
            queryFinal.setParameter("secuencia", secuencia);
            List<Historiasformulas> historiasformulas = queryFinal.getResultList();
            return historiasformulas;
        } catch (Exception e) {
            System.out.println("Error historiasFormulasParaFormulaSecuencia.formulasContratosParaFormulaSecuencia : " + e.toString());
            return null;
        }
    }


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

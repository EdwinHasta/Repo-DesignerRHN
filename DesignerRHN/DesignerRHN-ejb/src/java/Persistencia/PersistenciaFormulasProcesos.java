/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.FormulasProcesos;
import InterfacePersistencia.PersistenciaFormulasProcesosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'FormulasProcesos' de la base de datos.
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaFormulasProcesos implements PersistenciaFormulasProcesosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(FormulasProcesos formulasProcesos) {
        try {
            em.persist(formulasProcesos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFormulasProcesos : " + e.toString());
        }
    }

    @Override
    public void editar(FormulasProcesos formulasProcesos) {
        try {
            em.merge(formulasProcesos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFormulasProcesos : " + e.toString());
        }
    }

    @Override
    public void borrar(FormulasProcesos formulasProcesos) {
        try {
            em.remove(em.merge(formulasProcesos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFormulasProcesos : " + e.toString());
        }
    }

    @Override
    public List<FormulasProcesos> formulasProcesosParaFormulaSecuencia(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT fp FROM FormulasProcesos fp WHERE fp.formula.secuencia=:secuencia");
            queryFinal.setParameter("secuencia", secuencia);
            List<FormulasProcesos> formulasProcesos = queryFinal.getResultList();
            return formulasProcesos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulasProcesos.formulasProcesosParaFormulaSecuencia : " + e.toString());
            return null;
        }
    }
    
    
    @Override 
    public List<FormulasProcesos> formulasProcesosParaProcesoSecuencia(BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT fp FROM FormulasProcesos fp WHERE fp.proceso.secuencia=:secuencia");
            queryFinal.setParameter("secuencia", secuencia);
            List<FormulasProcesos> formulasProcesos = queryFinal.getResultList();
            return formulasProcesos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulasProcesos.formulasProcesosParaProcesoSecuencia : " + e.toString());
            return null;
        }
    }
}

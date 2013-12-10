/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.FormulasConceptos;
import InterfacePersistencia.PersistenciaFormulasConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'FormulasConceptos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormulasConceptos implements PersistenciaFormulasConceptosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public boolean verificarExistenciaConceptoFormulasConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(fc) FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto");
            query.setParameter("secConcepto", secConcepto);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public List<FormulasConceptos> formulasConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT fc FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto");
            query.setParameter("secConcepto", secConcepto);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean verificarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula) {
        try {
            Query query = em.createQuery("SELECT COUNT(fc) FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto AND fc.formula.secuencia = :secFormula");
            query.setParameter("secConcepto", secConcepto);
            query.setParameter("secFormula", secFormula);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion verificarFormulaCargue_Concepto: " + e);
            return false;
        }
    }
}

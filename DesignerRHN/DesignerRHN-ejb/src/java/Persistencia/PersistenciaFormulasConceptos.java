package Persistencia;

import Entidades.FormulasConceptos;
import InterfacePersistencia.PersistenciaFormulasConceptosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaFormulasConceptos implements PersistenciaFormulasConceptosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public boolean verificarExistenciaConceptoFormulasConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(fc) FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto");
            query.setParameter("secConcepto", secConcepto);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    public List<FormulasConceptos> formulasConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT fc FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto");
            query.setParameter("secConcepto", secConcepto);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean verificarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula) {
        try {
            Query query = em.createQuery("SELECT COUNT(fc) FROM FormulasConceptos fc WHERE fc.concepto.secuencia = :secConcepto AND fc.formula.secuencia = :secFormula");
            query.setParameter("secConcepto", secConcepto);
            query.setParameter("secFormula", secFormula);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion verificarFormulaCargue_Concepto: " + e);
            return false;
        }
    }
}

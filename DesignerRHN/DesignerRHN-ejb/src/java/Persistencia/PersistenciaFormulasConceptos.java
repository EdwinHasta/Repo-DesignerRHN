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
import javax.persistence.criteria.CriteriaQuery;
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
    public void crear(FormulasConceptos conceptos) {
        try {
            em.persist(conceptos);
        } catch (Exception e) {
            System.out.println("Error crearFormulasConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public void editar(FormulasConceptos conceptos) {
        try {
            em.merge(conceptos);
        } catch (Exception e) {
            System.out.println("Error crearFormulasConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public void borrar(FormulasConceptos conceptos) {
        try {
            em.remove(em.merge(conceptos));
        } catch (Exception e) {
            System.out.println("Error crearFormulasConceptos Persistencia : " + e.toString());
        }
    }

    @Override
    public List<FormulasConceptos> buscarFormulasConceptos() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(FormulasConceptos.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarFormulasConceptos Persistencia : "+e.toString());
            return null;
        }
    }
    
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
    
      @Override
    public Long comportamientoConceptoAutomaticoSecuenciaConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(f.secuencia) FROM FormulasConceptos f WHERE f.concepto.secuencia=:secConcepto");
            query.setParameter("secConcepto", secConcepto);
            Long resultado = (Long) query.getSingleResult();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error Persistencia comportamientoConceptoAutomaticoSecuenciaConcepto : " + e.toString());
            return null;
        }
    }

    @Override
    public Long comportamientoConceptoSemiAutomaticoSecuenciaConcepto(BigInteger secConcepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(f.secuencia) FROM FormulasConceptos f, FormulasNovedades fn WHERE  fn.formula=f.formula AND f.concepto.secuencia=:secConcepto");
            query.setParameter("secConcepto", secConcepto);
            Long resultado = (Long) query.getSingleResult();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error Persistencia comportamientoConceptoAutomaticoSecuenciaConcepto : " + e.toString());
            return null;
        }
    }
}

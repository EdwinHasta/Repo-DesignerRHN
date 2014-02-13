/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.TSFormulasConceptos;
import InterfacePersistencia.PersistenciaTSFormulasConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TSFormulasConceptos'
 * de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTSFormulasConceptos implements PersistenciaTSFormulasConceptosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TSFormulasConceptos tSFormulasConceptos) {
        try {
            em.persist(tSFormulasConceptos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTSFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public void editar(TSFormulasConceptos tSFormulasConceptos) {
        try {
            em.merge(tSFormulasConceptos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTSFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public void borrar(TSFormulasConceptos tSFormulasConceptos) {
        try {
            em.remove(em.merge(tSFormulasConceptos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTSFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public List<TSFormulasConceptos> buscarTSFormulasConceptos() {
        try {
            Query query = em.createQuery("SELECT t FROM TSFormulasConceptos t ORDER BY t.secuencia ASC");
            List<TSFormulasConceptos> tSFormulasConceptos = (List<TSFormulasConceptos>) query.getResultList();
            return tSFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTSFormulasConceptos PersistenciaTSFormulasConceptos : " + e.toString());
            return null;
        }
    }

    @Override
    public TSFormulasConceptos buscarTSFormulaConceptoSecuencia(BigInteger secTSFormula) {
        try {
            Query query = em.createQuery("SELECT t FROM TSFormulasConceptos t WHERE t.secuencia = :secTSFormula");
            query.setParameter("secTSFormula", secTSFormula);
            TSFormulasConceptos tSFormulasConceptos = (TSFormulasConceptos) query.getSingleResult();
            return tSFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTSFormulaConceptoSecuencia PersistenciaTSFormulasConceptos : " + e.toString());
            TSFormulasConceptos tSFormulasConceptos = null;
            return tSFormulasConceptos;
        }
    }

    @Override
    public List<TSFormulasConceptos> buscarTSFormulasConceptosPorSecuenciaTipoSueldo(BigInteger secTipoSueldo) {
        try {
            Query query = em.createQuery("SELECT t FROM TSFormulasConceptos t WHERE t.tiposueldo.secuencia =:secTipoSueldo");
            query.setParameter("secTipoSueldo", secTipoSueldo);
            List<TSFormulasConceptos> tEFormulasConceptos = (List<TSFormulasConceptos>) query.getResultList();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTSFormulasConceptosPorSecuenciaTipoSueldo PersistenciaTSFormulasConceptos : " + e.toString());
            return null;
        }
    }
}

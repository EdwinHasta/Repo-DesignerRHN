/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.TEFormulasConceptos;
import InterfacePersistencia.PersistenciaTEFormulasConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'TEFormulasConceptos' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTEFormulasConceptos implements PersistenciaTEFormulasConceptosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TEFormulasConceptos tEFormulasConceptos) {
        try {
            em.persist(tEFormulasConceptos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTEFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public void editar(TEFormulasConceptos tEFormulasConceptos) {
        try {
            em.merge(tEFormulasConceptos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTEFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public void borrar(TEFormulasConceptos tEFormulasConceptos) {
        try {
            em.remove(em.merge(tEFormulasConceptos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTEFormulasConceptos : " + e.toString());
        }
    }

    @Override
    public List<TEFormulasConceptos> buscarTEFormulasConceptos() {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t ORDER BY t.secuencia ASC");
            List<TEFormulasConceptos> tEFormulasConceptos = (List<TEFormulasConceptos>) query.getResultList();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulasConceptos PersistenciaTEFormulasConceptos : " + e.toString());
            return null;
        }
    }

    @Override
    public TEFormulasConceptos buscarTEFormulaConceptoSecuencia(BigInteger secTEFormula) {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t WHERE t.secuencia = :secTEFormula");
            query.setParameter("secTEFormula", secTEFormula);
            TEFormulasConceptos tEFormulasConceptos = (TEFormulasConceptos) query.getSingleResult();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulaConceptoSecuencia PersistenciaTEFormulasConceptos : " + e.toString());
            TEFormulasConceptos tEFormulasConceptos = null;
            return tEFormulasConceptos;
        }
    }

    @Override
    public List<TEFormulasConceptos> buscarTEFormulasConceptosPorSecuenciaTSGrupoTipoEntidad(BigInteger secTSGrupo) {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t WHERE t.tsgrupotipoentidad.secuencia =:secTSGrupo");
            query.setParameter("secTSGrupo", secTSGrupo);
            List<TEFormulasConceptos> tEFormulasConceptos = (List<TEFormulasConceptos>) query.getResultList();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulasConceptosPorSecuenciaTSGrupoTipoEntidad PersistenciaTEFormulasConceptos : " + e.toString());
            return null;
        }
    }
}

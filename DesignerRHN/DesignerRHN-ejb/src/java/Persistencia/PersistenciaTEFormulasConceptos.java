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
import javax.persistence.EntityTransaction;
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
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, TEFormulasConceptos tEFormulasConceptos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tEFormulasConceptos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTEFormulasConceptos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TEFormulasConceptos tEFormulasConceptos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tEFormulasConceptos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTEFormulasConceptos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TEFormulasConceptos tEFormulasConceptos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(tEFormulasConceptos));
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTEFormulasConceptos.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public List<TEFormulasConceptos> buscarTEFormulasConceptos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t ORDER BY t.secuencia ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TEFormulasConceptos> tEFormulasConceptos = (List<TEFormulasConceptos>) query.getResultList();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulasConceptos PersistenciaTEFormulasConceptos : " + e.toString());
            return null;
        }
    }

    @Override
    public TEFormulasConceptos buscarTEFormulaConceptoSecuencia(EntityManager em, BigInteger secTEFormula) {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t WHERE t.secuencia = :secTEFormula");
            query.setParameter("secTEFormula", secTEFormula);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TEFormulasConceptos tEFormulasConceptos = (TEFormulasConceptos) query.getSingleResult();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulaConceptoSecuencia PersistenciaTEFormulasConceptos : " + e.toString());
            TEFormulasConceptos tEFormulasConceptos = null;
            return tEFormulasConceptos;
        }
    }

    @Override
    public List<TEFormulasConceptos> buscarTEFormulasConceptosPorSecuenciaTSGrupoTipoEntidad(EntityManager em, BigInteger secTSGrupo) {
        try {
            Query query = em.createQuery("SELECT t FROM TEFormulasConceptos t WHERE t.tsgrupotipoentidad.secuencia =:secTSGrupo");
            query.setParameter("secTSGrupo", secTSGrupo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TEFormulasConceptos> tEFormulasConceptos = (List<TEFormulasConceptos>) query.getResultList();
            return tEFormulasConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarTEFormulasConceptosPorSecuenciaTSGrupoTipoEntidad PersistenciaTEFormulasConceptos : " + e.toString());
            return null;
        }
    }
}

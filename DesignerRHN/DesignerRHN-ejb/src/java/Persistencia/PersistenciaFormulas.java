/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Formulas;
import InterfacePersistencia.PersistenciaFormulasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Formulas' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormulas implements PersistenciaFormulasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /* @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Formulas formulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(formulas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, Formulas formulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(formulas);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulas.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, Formulas formulas) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(formulas));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaFormulas.borrar: " + e);
            }
        }
    }

    @Override
    public Formulas buscarFormula(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            return em.find(Formulas.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Formulas> buscarFormulas(EntityManager em) {
        em.clear();
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Formulas.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Formulas> buscarFormulasCarge(EntityManager em) {
        try {
           em.clear();
            Query query = em.createQuery("SELECT f FROM Formulas f WHERE f.secuencia IN (SELECT fn.formula.secuencia FROM FormulasNovedades fn WHERE fn.cargue = 'S')");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Formulas> listaFormulas = query.getResultList();
            return listaFormulas;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Formulas buscarFormulaCargeInicial(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT f FROM Formulas f WHERE f.secuencia IN (SELECT fn.formula.secuencia FROM FormulasNovedades fn WHERE fn.cargue = 'S' AND fn.formula.nombrecorto = 'LIQNOV')");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Formulas formulaInicial = (Formulas) query.getSingleResult();
            return formulaInicial;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Formulas> lovFormulas(EntityManager em) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT f FROM Formulas f ORDER BY f.nombrelargo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Formulas> listaFormulas = query.getResultList();
            return listaFormulas;
        } catch (Exception e) {
            System.out.println("Error lovFormulas: " + e);
            return null;
        }
    }

    @Override
    public void clonarFormulas(EntityManager em, String nombreCortoOrigen, String nombreCortoClon, String nombreLargoClon, String observacionClon) {
        int i = 0;
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "call FORMULAS_PKG.CLONARFORMULA(?, ?, ?, ?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, nombreCortoOrigen);
            query.setParameter(2, nombreCortoClon);
            query.setParameter(3, nombreLargoClon);
            query.setParameter(4, observacionClon);
            i = query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error en clonarFormulas: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void operandoFormulas(EntityManager em, BigInteger secFormula) {
        int i = 0;
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            String sqlQuery = "call UTL_FORMS.INSERTAROPERANDOFORMULA(?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secFormula);
            i = query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error en oprandoFormulas: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }
}

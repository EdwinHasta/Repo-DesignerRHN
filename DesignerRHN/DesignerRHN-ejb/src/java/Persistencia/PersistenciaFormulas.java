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
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Formulas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormulas implements PersistenciaFormulasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Formulas formulas) {
        try {
            em.persist(formulas);
        } catch (Exception e) {
            System.out.println("No es posible crear la Formula");
        }
    }

    @Override
    public void editar(Formulas formulas) {
        try {
            em.merge(formulas);
        } catch (Exception e) {
            System.out.println("La formula no exite o esta reservada por lo cual no puede ser modificada");
        }
    }

    @Override
    public void borrar(Formulas formulas) {
        em.remove(em.merge(formulas));
    }

    @Override
    public Formulas buscarFormula(BigInteger secuencia) {
        try {
            return em.find(Formulas.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Formulas> buscarFormulas() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Formulas.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Formulas> buscarFormulasCarge() {
        try {
            Query query = em.createQuery("SELECT f FROM Formulas f WHERE f.secuencia IN (SELECT fn.formula.secuencia FROM FormulasNovedades fn WHERE fn.cargue = 'S')");
            List<Formulas> listaFormulas = query.getResultList();
            return listaFormulas;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Formulas buscarFormulaCargeInicial() {
        try {
            Query query = em.createQuery("SELECT f FROM Formulas f WHERE f.secuencia IN (SELECT fn.formula.secuencia FROM FormulasNovedades fn WHERE fn.cargue = 'S' AND fn.formula.nombrecorto = 'LIQNOV')");
            Formulas formulaInicial = (Formulas) query.getSingleResult();
            return formulaInicial;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Formulas> lovFormulas() {
        try {
            Query query = em.createQuery("SELECT f FROM Formulas f ORDER BY f.nombrelargo ASC");
            List<Formulas> listaFormulas = query.getResultList();
            return listaFormulas;
        } catch (Exception e) {
            System.out.println("Error lovFormulas: " + e);
            return null;
        }
    }

    @Override
    public void clonarFormulas(String nombreCortoOrigen, String nombreCortoClon, String nombreLargoClon, String observacionClon) {
        int i = 0;
        try {
            String sqlQuery = "call FORMULAS_PKG.CLONARFORMULA(?, ?, ?, ?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, nombreCortoOrigen);
            query.setParameter(2, nombreCortoClon);
            query.setParameter(3, nombreLargoClon);
            query.setParameter(4, observacionClon);
            i = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en clonarFormulas: " + e);
        }
    }

    @Override
    public void operandoFormulas(BigInteger secFormula) {
        int i = 0;
        try {
            String sqlQuery = "call UTL_FORMS.INSERTAROPERANDOFORMULA(?)";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secFormula);
            i = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en oprandoFormulas: " + e);
        }
    }
}

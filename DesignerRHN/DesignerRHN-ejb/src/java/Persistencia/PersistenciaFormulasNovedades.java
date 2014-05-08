/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.FormulasNovedades;
import InterfacePersistencia.PersistenciaFormulasNovedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'FormulasNovedades' de la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormulasNovedades implements PersistenciaFormulasNovedadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,FormulasNovedades formulasNovedades) {
        try {
            em.persist(formulasNovedades);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaFormulasNovedades : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em,FormulasNovedades formulasNovedades) {
        try {
            em.merge(formulasNovedades);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaFormulasNovedades : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em,FormulasNovedades formulasNovedades) {
        try {
            em.remove(em.merge(formulasNovedades));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaFormulasNovedades : " + e.toString());
        }
    }

    @Override
    public List<FormulasNovedades> formulasNovedadesParaFormulaSecuencia(EntityManager em,BigInteger secuencia) {
        try {
            Query queryFinal = em.createQuery("SELECT fn FROM FormulasNovedades fn WHERE fn.formula.secuencia=:secuencia");
            queryFinal.setParameter("secuencia", secuencia);
            queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
                    ;
            List<FormulasNovedades> formulasNovedades = queryFinal.getResultList();
            return formulasNovedades;
        } catch (Exception e) {
            System.out.println("Error PersistenciaFormulasNovedades.formulasNovedadesParaFormulaSecuencia : " + e.toString());
            return null;
        }
    }

    @Override
    public boolean verificarExistenciaFormulasNovedades(EntityManager em,BigInteger secFormula) {
        try {
            Query query = em.createQuery("SELECT COUNT(fn) FROM FormulasNovedades fn WHERE fn.formula.secuencia = :secFormula");
            query.setParameter("secFormula", secFormula);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }
}

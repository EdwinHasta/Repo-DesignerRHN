/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaFormulasNovedadesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'FormulasNovedades'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaFormulasNovedades implements PersistenciaFormulasNovedadesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public boolean verificarExistenciaFormulasNovedades(BigInteger secFormula) {
        try {
            Query query = em.createQuery("SELECT COUNT(fn) FROM FormulasNovedades fn WHERE fn.formula.secuencia = :secFormula");
            query.setParameter("secFormula", secFormula);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }
}

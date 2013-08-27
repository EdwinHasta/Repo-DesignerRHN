package Persistencia;

import InterfacePersistencia.PersistenciaFormulasNovedadesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaFormulasNovedades implements PersistenciaFormulasNovedadesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    public boolean verificarExistenciaFormulasNovedades(BigInteger secFormula) {
        try {
            Query query = em.createQuery("SELECT COUNT(fn) FROM FormulasNovedades fn WHERE fn.formula.secuencia = :secFormula");
            query.setParameter("secFormula", secFormula);
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
}

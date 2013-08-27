package Persistencia;

import Entidades.VWActualesSueldos;
import InterfacePersistencia.PersistenciaVWActualesSueldosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesSueldos implements PersistenciaVWActualesSueldosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public BigDecimal buscarSueldoActivo(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT SUM(vw.valor) FROM VWActualesSueldos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            BigDecimal vwActualesSueldosValor = (BigDecimal) query.getSingleResult();
            return vwActualesSueldosValor;
        } catch (Exception e) {
            return null;
        }
    }
    
}

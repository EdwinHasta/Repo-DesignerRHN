package Persistencia;

import Entidades.VWActualesPensiones;
import InterfacePersistencia.PersistenciaVWActualesPensionesInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesPensiones implements  PersistenciaVWActualesPensionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public BigDecimal buscarSueldoPensionado(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesPensiones vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            BigDecimal vWActualesPensiones = (BigDecimal) query.getSingleResult();
            return vWActualesPensiones;
        } catch (Exception e) {
            return null;
        }
    }
}

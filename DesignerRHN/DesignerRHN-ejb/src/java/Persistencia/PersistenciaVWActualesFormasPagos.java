package Persistencia;

import Entidades.VWActualesFormasPagos;
import InterfacePersistencia.PersistenciaVWActualesFormasPagosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesFormasPagos implements PersistenciaVWActualesFormasPagosInterface{

     @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesFormasPagos buscarFormaPago(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesFormasPagos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesFormasPagos actualesFormasPagos = (VWActualesFormasPagos) query.getSingleResult();
            return actualesFormasPagos;
        } catch (Exception e) {
            VWActualesFormasPagos actualesFormasPagos =  null;
            return actualesFormasPagos;
        }

    }
}

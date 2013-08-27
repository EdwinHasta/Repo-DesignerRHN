package Persistencia;

import Entidades.VWActualesCargos;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesCargos implements PersistenciaVWActualesCargosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesCargos buscarCargoEmpleado(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesCargos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesCargos vwActualesCargos = (VWActualesCargos) query.getSingleResult();
            return vwActualesCargos;
        } catch (Exception e) {
            VWActualesCargos vwActualesCargos= null;
            return vwActualesCargos;
        }


    }
}

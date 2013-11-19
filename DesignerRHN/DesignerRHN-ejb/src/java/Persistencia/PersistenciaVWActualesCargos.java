package Persistencia;

import Entidades.VWActualesCargos;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesCargos implements PersistenciaVWActualesCargosInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public VWActualesCargos buscarCargoEmpleado(EntityManager entity, BigInteger secuencia) {
        try {
            Query query = entity.createQuery("SELECT vw FROM VWActualesCargos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesCargos vwActualesCargos = (VWActualesCargos) query.getSingleResult();
            return vwActualesCargos;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaVWActualesCargos.buscarCargoEmpleado");
            VWActualesCargos vwActualesCargos= null;
            return vwActualesCargos;
        }
    }
}

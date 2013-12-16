/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesCargos;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesCargos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesCargos implements PersistenciaVWActualesCargosInterface {

    @Override
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

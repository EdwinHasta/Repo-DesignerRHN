/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesCargos;
import InterfacePersistencia.PersistenciaVWActualesCargosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesCargos' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesCargos implements PersistenciaVWActualesCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesCargos buscarCargoEmpleado(EntityManager entity, BigInteger secuencia) {
        try {
            Query query = entity.createQuery("SELECT vw FROM VWActualesCargos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VWActualesCargos vwActualesCargos = (VWActualesCargos) query.getSingleResult();
            return vwActualesCargos;
        } catch (Exception e) {
            System.out.println("Error: PersistenciaVWActualesCargos.buscarCargoEmpleado " + e);
            VWActualesCargos vwActualesCargos = null;
            return vwActualesCargos;
        }
    }

    @Override  
    public Long conteoCodigosEmpleados(BigInteger secEstructura) {
        try {
            Query query = em.createQuery("SELECT COUNT (em.codigoempleado) FROM VWActualesCargos vc, Empleados em WHERE vc.empleado.secuencia = em.secuencia AND vc.estructura = :secEstructura AND EXISTS (SELECT vt FROM VWActualesTiposTrabajadores vt, TiposTrabajadores tt WHERE vt.empleado.secuencia = em.secuencia AND vt.tipoTrabajador.secuencia  = tt.secuencia AND tt.tipo = 'ACTIVO')");
            query.setParameter("secEstructura", secEstructura);
            Long conteo = (Long) query.getSingleResult();
            return conteo;
        } catch (Exception e) {
            System.out.println("Error conteoCodigosEmpleados PersistenciaVWActualesCargos: " + e.toString() + "\n" +e.getCause());
            Long conteo = null;
            return conteo;
        }
    }

}

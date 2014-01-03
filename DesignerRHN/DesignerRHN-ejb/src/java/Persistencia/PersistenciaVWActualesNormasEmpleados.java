/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesNormasEmpleados;
import InterfacePersistencia.PersistenciaVWActualesNormasEmpleadosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesNormasEmpleados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesNormasEmpleados implements PersistenciaVWActualesNormasEmpleadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesNormasEmpleados buscarNormaLaboral(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesNormasEmpleados vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesNormasEmpleados vwActualesNormasEmpleados = (VWActualesNormasEmpleados) query.getSingleResult();
            return vwActualesNormasEmpleados;
        } catch (Exception e) {
            VWActualesNormasEmpleados vwActualesNormasEmpleados = null;
            return vwActualesNormasEmpleados;
        }
    }
}

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
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    public VWActualesNormasEmpleados buscarNormaLaboral(EntityManager em, BigInteger secuencia) {
        try {
            em.clear();
            Query query = em.createQuery("SELECT vw FROM VWActualesNormasEmpleados vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VWActualesNormasEmpleados vwActualesNormasEmpleados = (VWActualesNormasEmpleados) query.getSingleResult();
            return vwActualesNormasEmpleados;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVWActualesNormasEmpleados.buscarNormaLaboral: " + e);
            VWActualesNormasEmpleados vwActualesNormasEmpleados = null;
            return vwActualesNormasEmpleados;
        }
    }
}

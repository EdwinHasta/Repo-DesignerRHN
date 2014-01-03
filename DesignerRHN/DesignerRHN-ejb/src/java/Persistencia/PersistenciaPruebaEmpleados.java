/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PruebaEmpleados;
import InterfacePersistencia.PersistenciaPruebaEmpleadosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br> 
 * Clase encargada de realizar las operaciones sobre las tablas 'EMPLEADOS', 'VWACTUALESSUELDOS', 'PERSONAS'
 * para mostrar determinada información de un empleado que se encuentra en las tablas mencionadas.
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaPruebaEmpleados implements PersistenciaPruebaEmpleadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public PruebaEmpleados empleadosAsignacion(BigInteger secEmpleado) {
        try {
            PruebaEmpleados pruebaEmpleado = null;            
            Query queryValidacion = em.createQuery("SELECT COUNT(vwa) FROM VWActualesSueldos vwa WHERE vwa.empleado.secuencia = :secEmpleado");
            queryValidacion.setParameter("secEmpleado", secEmpleado);
            Long resultado = (Long) queryValidacion.getSingleResult();
            if (resultado > 0) {
                String sqlQuery = "SELECT E.secuencia ID, E.codigoempleado CODIGO, P.PRIMERAPELLIDO||' '||P.SEGUNDOAPELLIDO||' '||P.NOMBRE NOMBRE, SUM(VWA.valor) VALOR\n"
                        + "       FROM EMPLEADOS E, VWACTUALESSUELDOS VWA, PERSONAS P\n"
                        + "       WHERE E.persona = P.secuencia \n"
                        + "       AND VWA.empleado = E.secuencia\n"
                        + "       AND VWA.empleado = ?\n"
                        + "       GROUP BY E.secuencia, E.codigoempleado, P.PRIMERAPELLIDO||' '||P.SEGUNDOAPELLIDO||' '||P.NOMBRE";
                Query query = em.createNativeQuery(sqlQuery, "PruebaEmpleadosAsignacionBasica");
                query.setParameter(1, secEmpleado);
                pruebaEmpleado = (PruebaEmpleados) query.getSingleResult();
            } else {               
                Query queryValidacion2 = em.createQuery("SELECT COUNT(vwp) FROM VWActualesPensiones vwp WHERE vwp.empleado.secuencia = :secEmpleado");
                queryValidacion2.setParameter("secEmpleado", secEmpleado);
                Long resultado2 = (Long) queryValidacion2.getSingleResult();
                if(resultado2 > 0){
                String sqlQuery = "SELECT E.secuencia ID, E.codigoempleado CODIGO, P.nombre NOMBRE, SUM(VWP.valor) VALOR\n"
                        + "       FROM EMPLEADOS E, VWACTUALESPENSIONES VWP, PERSONAS P\n"
                        + "       WHERE E.persona = P.secuencia \n"
                        + "       AND VWP.empleado = E.secuencia\n"
                        + "       AND VWP.empleado = ?\n"
                        + "       GROUP BY E.secuencia, E.codigoempleado, P.nombre";
                Query query = em.createNativeQuery(sqlQuery, "PruebaEmpleadosAsignacionBasica");
                query.setParameter(1, secEmpleado);
                pruebaEmpleado = (PruebaEmpleados) query.getSingleResult();
                }
            }
            return pruebaEmpleado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaPruebaEmpleados.empleadosAsignacion. " + e);
            return null;
        }
    }  
}

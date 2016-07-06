/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PruebaEmpleados;
import InterfacePersistencia.PersistenciaPruebaEmpleadosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public PruebaEmpleados empleadosAsignacion(EntityManager em, BigInteger secEmpleado) {
        try {
            em.clear();
            PruebaEmpleados pruebaEmpleado = null;            
            Query queryValidacion = em.createQuery("SELECT COUNT(vwa) FROM VWActualesSueldos vwa WHERE vwa.empleado.secuencia = :secEmpleado");
            queryValidacion.setParameter("secEmpleado", secEmpleado);
            queryValidacion.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) queryValidacion.getSingleResult();
            if (resultado > 0) {
                String sqlQuery = "SELECT E.secuencia ID, E.codigoempleado CODIGO, P.PRIMERAPELLIDO||' '||P.SEGUNDOAPELLIDO||' '||P.NOMBRE NOMBRE, SUM(VWA.valor) VALOR, tt.tipo TIPO\n"
                        + "       FROM EMPLEADOS E, VWACTUALESSUELDOS VWA, PERSONAS P, VWActualesTiposTrabajadores vwtt, TiposTrabajadores tt\n"
                        + "       WHERE E.persona = P.secuencia \n"
                        + "       AND VWA.empleado = E.secuencia\n"
                        + "       AND vwtt.empleado = E.secuencia\n"
                        + "       AND tt.secuencia = vwtt.tipotrabajador\n"
                        + "       AND VWA.empleado = ?\n"
                        + "       GROUP BY E.secuencia, E.codigoempleado, P.PRIMERAPELLIDO||' '||P.SEGUNDOAPELLIDO||' '||P.NOMBRE, tt.tipo";
                Query query = em.createNativeQuery(sqlQuery, "PruebaEmpleadosAsignacionBasica");
                query.setParameter(1, secEmpleado);
                pruebaEmpleado = (PruebaEmpleados) query.getSingleResult();
            } else {
                Query queryValidacion2 = em.createQuery("SELECT COUNT(vwp) FROM VWActualesPensiones vwp WHERE vwp.empleado.secuencia = :secEmpleado");
                queryValidacion2.setParameter("secEmpleado", secEmpleado);
                queryValidacion2.setHint("javax.persistence.cache.storeMode", "REFRESH");
                Long resultado2 = (Long) queryValidacion2.getSingleResult();
                if(resultado2 > 0){
                String sqlQuery = "SELECT E.secuencia ID, E.codigoempleado CODIGO, P.nombre NOMBRE, SUM(VWP.valor) VALOR, tt.tipo TIPO\n"
                        + "       FROM EMPLEADOS E, VWACTUALESPENSIONES VWP, PERSONAS P, VWActualesTiposTrabajadores vwtt, TiposTrabajadores tt\n"
                        + "       WHERE E.persona = P.secuencia \n"
                        + "       AND VWP.empleado = E.secuencia\n"
                        + "       AND vwtt.empleado = E.secuencia\n"
                        + "       AND tt.secuencia = vwtt.tipotrabajador\n"
                        + "       AND VWP.empleado = ?\n"
                        + "       GROUP BY E.secuencia, E.codigoempleado, P.nombre, tt.tipo";
                Query query = em.createNativeQuery(sqlQuery, "PruebaEmpleadosAsignacionBasica");
                query.setParameter(1, secEmpleado);
                pruebaEmpleado = (PruebaEmpleados) query.getSingleResult();
                }
            }
//            //Ahora el tipo:
//            em.clear();
//            Query queryT = em.createQuery("SELECT vw.tipoTrabajador.tipo FROM VWActualesTiposTrabajadores vw WHERE vw.empleado.secuencia= :secuencia");
//            queryT.setParameter("secuencia", secEmpleado);
//            queryT.setHint("javax.persistence.cache.storeMode", "REFRESH");
//            String tipoEmpleado = (String) queryT.getSingleResult();
//            pruebaEmpleado.setTipo(tipoEmpleado);
            
            return pruebaEmpleado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaPruebaEmpleados.empleadosAsignacion. " + e);
            return null;
        }
    }
}

/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaCandadosInterface;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Candados' de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaCandados implements PersistenciaCandadosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public boolean permisoLiquidar(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM Candados c WHERE c.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: permisoLiquidar " + e);
            return false;
        }
    }

    @Override
    public void liquidar() {
        int i = -100;
        try {
            String sqlQuery = "call PRCUTL_FORMSLIQUIDAR()";
            Query query = em.createNativeQuery(sqlQuery);
            i = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error liquidar. " + e);
        }
    }

    @Override
    public String estadoLiquidacion(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT c.estado FROM Candados c WHERE c.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            String estadoLiquidacion = (String) query.getSingleResult();
            return estadoLiquidacion;
        } catch (Exception e) {
            System.out.println("Exepcion: estadoLiquidacion " + e);
            return null;
        }
    }

    @Override
    public Integer progresoLiquidacion(Integer totalEmpleadosALiquidar) {
        try {
            String sqlQuery = "select conteoliquidados(?) from dual";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, totalEmpleadosALiquidar);
            BigDecimal progreso = (BigDecimal) query.getSingleResult();
            Integer porcentajeProgreso = progreso.intValueExact();
            return porcentajeProgreso;
        } catch (Exception e) {
            System.out.println("Error progresoLiquidacion. " + e);
            return null;
        }
    }

    @Override
    public void cancelarLiquidacion(String usuarioBD) {
        try {
            Query query = em.createQuery("UPDATE Candados c SET c.estado='CANCELAR' WHERE c.usuario.alias = :usuarioBD");
            query.setParameter("usuarioBD", usuarioBD);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Exepcion: cancelarLiquidacion " + e);
        }
    }

    @Override
    public void cerrarLiquidacionAutomatico() {
        try {
            String sqlQuery = "call UTL_FORMS.CERRARLIQUIDACION()";
            Query query = em.createNativeQuery(sqlQuery);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error cerrarLiquidacion. " + e);
        }
    }
    
    @Override
    public void cerrarLiquidacionNoAutomatico() {
        try {
            String sqlQuery = "call UTL_FORMS.CERRARLIQPAGOPORFUERA()";
            Query query = em.createNativeQuery(sqlQuery);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error cerrarLiquidacion. " + e);
        }
    }
}

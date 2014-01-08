/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import InterfaceAdministrar.AdministarReportesInterface;
import InterfacePersistencia.EntityManagerGlobalInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import Reportes.IniciarReporte;
import Reportes.IniciarReporteInterface;
import java.math.BigInteger;
import java.sql.Connection;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para los reportes.
 * @author betelgeuse
 */
@Stateful
public class AdministarReportes implements AdministarReportesInterface {
    
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que está usando el aplicativo.
     */
    @EJB
    EntityManagerGlobalInterface entityManagerGlobal;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    /**
     * Atributo encargado de comunicarse con la interface 'IniciarReporteInterface' para realizar un reporte.
     */
    IniciarReporteInterface reporte = new IniciarReporte();
    /**
     * Atributo que representa la conexión actual al aplicativo.
     */
    private Connection conexion;
    
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void datosConexion() {
        if (entityManagerGlobal != null) {
            EntityManager em = entityManagerGlobal.getEmf().createEntityManager();
            em.getTransaction().begin();
            conexion = em.unwrap(java.sql.Connection.class);
            em.getTransaction().commit();
            em.close();
        }
    }
    
    @Override
    public void generarReporte(BigInteger codigoEmpleado) {
        datosConexion();
        reporte.ejecutarReporte(conexion, codigoEmpleado);
    }
    
    @Override
    public void generarReporteXLSX() {
        reporte.ejecutarReporteXLSX();
    }
    
    @Override
    public void generarReporteXML() {
        reporte.ejecutarReporteXML();
    }
}

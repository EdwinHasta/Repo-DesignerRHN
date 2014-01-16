/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Generales;
import InterfaceAdministrar.AdministarReportesInterface;
import InterfacePersistencia.EntityManagerGlobalInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import Reportes.IniciarReporteInterface;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para los reportes.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministarReportes implements AdministarReportesInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    EntityManagerGlobalInterface entityManagerGlobal;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaGenerales'.
     */
    @EJB
    PersistenciaGeneralesInterface persistenciaGenerales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Atributo encargado de comunicarse con la interface
     * 'IniciarReporteInterface' para realizar un reporte.
     */
    @EJB
    IniciarReporteInterface reporte;
    /**
     * Atributo que representa la conexión actual al aplicativo.
     */
    private Connection conexion;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void consultarDatosConexion() {
        Connection conexion;
        if (entityManagerGlobal != null) {
            EntityManager em = entityManagerGlobal.getEmf().createEntityManager();
            em.getTransaction().begin();
            conexion = em.unwrap(java.sql.Connection.class);
            em.getTransaction().commit();
            em.close();
        }
    }

    @Override
    public String generarReporte(String nombreReporte, String tipoReporte) {
        Generales general = persistenciaGenerales.obtenerRutas();
        String nombreUsuario = persistenciaActualUsuario.actualAliasBD();
        if (general != null && nombreUsuario != null) {
            SimpleDateFormat formato = new SimpleDateFormat("ddMMyyyyhhmmss");
            String fechaActual = formato.format(new Date());
            String nombreArchivo = "JR" + nombreUsuario + fechaActual;
            String rutaReporte = general.getPathreportes();
            String rutaGenerado = general.getUbicareportes();
            if (tipoReporte.equals("PDF")) {
                nombreArchivo = nombreArchivo + ".pdf";
            } else if (tipoReporte.equals("XLSX")) {
                nombreArchivo = nombreArchivo + ".xlsx";
            } else if (tipoReporte.equals("XLS")) {
                nombreArchivo = nombreArchivo + ".xls";
            } else if (tipoReporte.equals("CSV")) {
                nombreArchivo = nombreArchivo + ".csv";
            } else if (tipoReporte.equals("HTML")) {
                nombreArchivo = nombreArchivo + ".html";
            }
            String pathReporteGenerado = reporte.ejecutarReporte(nombreReporte, rutaReporte, rutaGenerado, nombreArchivo, tipoReporte);
            return pathReporteGenerado;
        }
        return null;
    }

    @Override
    public void generarReportePDF() {
        reporte.ejecutarReportePDF();
    }

    @Override
    public void generarReporteXLSX() {
        reporte.ejecutarReporteXLSX();
    }

    @Override
    public void generarReporteXLS() {
        reporte.ejecutarReporteXLS();
    }

    @Override
    public void generarReporteCSV() {
        reporte.ejecutarReporteCSV();
    }

    @Override
    public void generarReporteHTML() {
        reporte.ejecutarReporteHTML();
    }
}

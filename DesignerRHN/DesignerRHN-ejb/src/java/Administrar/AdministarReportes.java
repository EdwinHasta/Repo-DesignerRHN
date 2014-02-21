/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.Generales;
import InterfaceAdministrar.AdministarReportesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import Reportes.IniciarReporteInterface;
import java.sql.Connection;
import java.sql.SQLException;
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
    AdministrarSesionesInterface administrarSesiones;
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
    private EntityManager em;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public void consultarDatosConexion() {
        em.getTransaction().begin();
        conexion = em.unwrap(java.sql.Connection.class);
        em.getTransaction().commit();
        //em.close();
    }

    @Override
    public String generarReporte(String nombreReporte, String tipoReporte) {
        //try {
        Generales general = persistenciaGenerales.obtenerRutas();
        String nombreUsuario = persistenciaActualUsuario.actualAliasBD();
        String pathReporteGenerado = null;
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
            } else if (tipoReporte.equals("DOCX")) {
                nombreArchivo = nombreArchivo + ".rtf";
            }
                //datosConexion();
            // if (conexion != null && !conexion.isClosed()) {
            pathReporteGenerado = reporte.ejecutarReporte(nombreReporte, rutaReporte, rutaGenerado, nombreArchivo, tipoReporte, null);
                    //conexion.close();
            // return pathReporteGenerado;
            // }
            return pathReporteGenerado;
        }
        return pathReporteGenerado;
        /* } catch (SQLException ex) {
         System.out.println("PUM PUM xD");
         return null;
         }*/
    }
}

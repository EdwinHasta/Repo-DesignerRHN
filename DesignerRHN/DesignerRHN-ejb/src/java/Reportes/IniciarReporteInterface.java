package Reportes;

import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public interface IniciarReporteInterface {
   public String ejecutarReporte(String nombreReporte, String rutaReporte, String rutaGenerado, String nombreArchivo, String tipoReporte, Connection cxn);
    public void cerrarConexion();
    public void inicarC();
}

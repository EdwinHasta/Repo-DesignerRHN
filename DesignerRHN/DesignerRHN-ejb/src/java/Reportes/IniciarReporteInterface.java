package Reportes;

/**
 *
 * @author Administrator
 */
public interface IniciarReporteInterface {
    public void inicarConexion(String url, String driver, String user, String psw);
    //public void ejecutarReporte(Connection conection, BigInteger codigoEmpleado);
    public void cerrarConexion();
    public void ejecutarReporteXLSX();
    public void inicarC();
    public void ejecutarReporteXML();
    public void ejecutarReportePDF();
    public void ejecutarReporteXLS();
    public void ejecutarReporteCSV();
    public void ejecutarReporteHTML();
    public String ejecutarReporte(String nombreReporte, String rutaReporte, String rutaGenerado, String nombreArchivo, String tipoReporte);
}

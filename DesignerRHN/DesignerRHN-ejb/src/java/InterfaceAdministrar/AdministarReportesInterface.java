/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para realizar reportes. 
 * @author betelgeuse
 */
public interface AdministarReportesInterface {
    /**
     * Método encargado de recuperar los datos de conexión del EntityManagerGlobal el cual tiene
     * el usuario asociado a un perfil del aplicativo.
     */
    public void datosConexion();
    /**
     * Método encargado de generar un comprobante (PDF) para un Empleado específico.
     * @param codigoEmpleado Código del empleado.
     */
    public String generarReporte(String nombreReporte, String tipoReporte);
    /**
     * Método encargado de generar un reporte XLSX para un Empleado específico.
     */
    public void generarReporteXLSX();
    /**
     * Método encargado de generar un reporte XML para un Empleado específico.
     */
    //public void generarReporteXML();
    public void generarReportePDF();
    public void generarReporteXLS();
    public void generarReporteCSV();
    public void generarReporteHTML();
}

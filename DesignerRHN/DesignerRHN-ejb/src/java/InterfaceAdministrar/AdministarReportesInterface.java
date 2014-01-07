/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import java.math.BigInteger;

/**
 * Interface encargada de determinar las operaciones que se realizan para realizar reportes. 
 * de la base de datos.
 * @author betelgeuse
 */
public interface AdministarReportesInterface {
    /**
     * Método encargado de recuperar los datos de conexión del usuario ya seteado.<br>
     * El usuario esta seteado cuando esta asociado a un perfil del aplicativo.
     */
    public void datosConexion();
    /**
     * Método encargado de generar un comprobante (PDF) para un Empleado específico.
     * @param codigoEmpleado Código del empleado.
     */
    public void generarReporte(BigInteger codigoEmpleado);
    /**
     * Método encargado de generar un reporte XLSX para un Empleado específico.
     */
    public void generarReporteXLSX();
    /**
     * Método encargado de generar un reporte XML para un Empleado específico.
     */
    public void generarReporteXML();
}

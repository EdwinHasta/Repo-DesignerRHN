package Reportes;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class IniciarReporte implements IniciarReporteInterface, Serializable {

    Connection conexion = null;

    public void inicarConexion(String url, String driver, String user, String psw) {
        try {
            System.out.println("URL: " + url);
            System.out.println("DRIVER: " + driver);
            System.out.println("USER: " + user);
            System.out.println("PASSWORD: " + psw);

            Class.forName(driver);
            conexion = DriverManager.getConnection(url, user, psw);
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Conexión fallida\n");
            System.out.println(e);
        }
    }

    public void ejecutarReporte(Connection conection, BigInteger codigoEmpleado) {
        try {
            File archivo = new File("C:\\Reportes\\Reportes_Fuentes\\report1.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", codigoEmpleado.intValue());
            jasperParameter.put("empleadoHasta", codigoEmpleado.intValue());
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conection);
            String outFileName = "C:\\Reportes\\Reportes_PDF\\DesprendibleConTercero.pdf";

            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            JasperExportManager.exportReportToPdfFile(imprimir, outFileName);
            byte[] bytes = JasperExportManager.exportReportToPdf(imprimir);
            /*try {
                conection.close();
            } catch (SQLException ex) {
                System.out.println("Error cerrando conexion " + ex);
            }*/
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error cerrar: " + e);
        }
    }

}

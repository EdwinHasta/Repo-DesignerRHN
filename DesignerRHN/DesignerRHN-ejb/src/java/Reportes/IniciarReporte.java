package Reportes;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JExcelApiMetadataExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Stateless
public class IniciarReporte implements IniciarReporteInterface, Serializable {

    Connection conexion = null;

    public void inicarConexion(String url, String driver, String user, String psw) {
        try {
            /*System.out.println("URL: " + url);
             System.out.println("DRIVER: " + driver);
             System.out.println("USER: " + user);
             System.out.println("PASSWORD: " + psw);

             Class.forName(driver);
             conexion = DriverManager.getConnection(url, user, psw);*/
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Conexión fallida\n");
            System.out.println(e);
        }
    }

    public void inicarC() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@SOPORTE9:1521:SOPORTE9", "PRODUCCION", "PRODUCCION");
            System.out.println("Conexión exitosa");
        } catch (Exception e) {
            System.out.println("Conexión fallida\n");
            System.out.println(e);
        }
    }

    /* public void ejecutarReporte(Connection conection, BigInteger codigoEmpleado) {
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
     }
     } catch (JRException e) {
     System.out.println("Error ejecutarReporte " + e);
     }
     }*/

    /* public void ejecutarReporteXLSX() {
     try {
     inicarC();
     File archivo = new File("C:\\Reportes\\Reportes_Fuentes\\ReportePruebaXML.jasper");
     JasperReport masterReport = null;
     masterReport = (JasperReport) JRLoader.loadObject(archivo);
     HashMap jasperParameter = new HashMap();
     jasperParameter.put("empleadoDesde", null);
     jasperParameter.put("empleadoHasta", null);
     JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
     String outFileName = "C:\\Reportes\\Reportes_XLSX\\DesprendibleConTercero.xlsx";

     JRXlsxExporter exporter = new JRXlsxExporter();
     exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
     exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
     exporter.exportReport();
     //byte[] bytes = JasperExportManager.exportReportToPdf(imprimir);
     /*try {
     conection.close();
     } catch (SQLException ex) {
     System.out.println("Error cerrando conexion " + ex);
     }
     } catch (JRException e) {
     System.out.println("Error ejecutarReporte " + e);
     }
     }*/
    
    
    
    public void ejecutarReporteXML() {
        try {
            inicarC();
            /*File archivo = new File("C:\\Reportes\\Reportes_Fuentes\\ReportePruebaXML.jasper");
             JasperReport masterReport = null;
             masterReport = (JasperReport) JRLoader.loadObject(archivo);
             HashMap jasperParameter = new HashMap();
             jasperParameter.put("empleadoDesde", 39071431);
             jasperParameter.put("empleadoHasta", 39071431);
             JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
             String outFileName = "C:\\Reportes\\Reportes_XML\\DesprendibleConTercero.xml";

             JRXmlExporter exporter = new JRXml4SwfExporter();
             exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
             exporter.exportReport();*/
            /*File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes\\report1.jasper");
             JasperReport masterReport = null;
             masterReport = (JasperReport) JRLoader.loadObject(archivo);
             HashMap jasperParameter = new HashMap();
             jasperParameter.put("empleadoDesde", 39071431);
             jasperParameter.put("empleadoHasta", 39071431);
             JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
             String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_XLS_MetaData\\DesprendibleConTercero.xls";

             JExcelApiMetadataExporter exporter = new JExcelApiMetadataExporter();
             exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
             exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
             exporter.exportReport();*/
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes\\report1.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            //jasperParameter.put("empleadoDesde", 39071431);
            //jasperParameter.put("empleadoHasta", 39071431);
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_CSV\\DesprendibleConTercero.csv";

            JRCsvMetadataExporter exporter = new JRCsvMetadataExporter();
            //exporter.setParameter(JRCsvMetadataExporterParameter.COLUMN_NAMES, new String[]{"Nombre,CodigoEmpleado,Codigo,Descripcion,Unidades,Pago,Descuento,Saldo"});
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            exporter.exportReport();
            //byte[] bytes = JasperExportManager.exportReportToPdf(imprimir);
            /*try {
             conection.close();
             } catch (SQLException ex) {
             System.out.println("Error cerrando conexion " + ex);
             }*/
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void ejecutarReportePDF() {
        try {
            inicarC();
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes_Betelgeuse\\desprendibleConTercero.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_PDF\\DesprendibleConTercero.pdf";

            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            //exporter.exportReport();
            // byte[] bytes = JasperExportManager.exportReportToPdf(imprimir);
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void ejecutarReporteXLSX() {
        try {
            inicarC();
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes_Betelgeuse\\desprendibleConTercero.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_XLSX\\DesprendibleConTercero.xlsx";

            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            exporter.exportReport();
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void ejecutarReporteXLS() {
        try {
            inicarC();
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes_Betelgeuse\\desprendibleConTercero.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_XLS_MetaData\\DesprendibleConTercero.xls";

            JExcelApiMetadataExporter exporter = new JExcelApiMetadataExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            exporter.exportReport();
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void ejecutarReporteCSV() {
        try {
            inicarC();
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes_Betelgeuse\\desprendibleConTercero.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_CSV\\DesprendibleConTercero.csv";

            JRCsvMetadataExporter exporter = new JRCsvMetadataExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            exporter.exportReport();
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public void ejecutarReporteHTML() {
        try {
            inicarC();
            File archivo = new File("C:\\Proyecto Betelgeuse\\Reportes\\Reportes_Fuentes_Betelgeuse\\desprendibleConTercero.jasper");
            JasperReport masterReport = null;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            HashMap jasperParameter = new HashMap();
            jasperParameter.put("empleadoDesde", null);
            jasperParameter.put("empleadoHasta", null);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, jasperParameter, conexion);
            String outFileName = "C:\\Proyecto Betelgeuse\\Reportes\\Reportes_HTML\\DesprendibleConTercero.html";

            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
            exporter.exportReport();
        } catch (JRException e) {
            System.out.println("Error ejecutarReporte " + e);
        }
    }

    public String ejecutarReporte(String nombreReporte, String rutaReporte, String rutaGenerado, String nombreArchivo, String tipoReporte) {
        try {
            inicarC();
            File archivo = new File(rutaReporte + nombreReporte + ".jasper");
            JasperReport masterReport;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, null, conexion);
            String outFileName = rutaGenerado + nombreArchivo;
            JRExporter exporter = null;
            if (tipoReporte.equals("PDF")) {
                exporter = new JRPdfExporter();
            } else if (tipoReporte.equals("XLSX")) {
                exporter = new JRXlsxExporter();
            } else if (tipoReporte.equals("XLS")) {
                exporter = new JExcelApiMetadataExporter();
            } else if (tipoReporte.equals("CSV")) {
                exporter = new JRCsvMetadataExporter();
            } else if (tipoReporte.equals("HTML")) {
                exporter = new JRHtmlExporter();
            }
            if (exporter != null) {
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
                exporter.exportReport();
            }
            cerrarConexion();
            return outFileName;
        } catch (JRException e) {
            System.out.println("Error IniciarReporte.ejecutarReporte: " + e);
            return null;
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

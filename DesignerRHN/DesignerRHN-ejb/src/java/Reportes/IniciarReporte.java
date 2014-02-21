package Reportes;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JExcelApiMetadataExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@Stateless
public class IniciarReporte implements IniciarReporteInterface, Serializable {

    Connection conexion = null;

    @Override
    public void inicarC() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@SOPORTE9:1521:SOPORTE9", "PRODUCCION", "PRODUCCION");
            System.out.println("Conexión exitosa");
        } catch (ClassNotFoundException e) {
            System.out.println("Conexión fallidab ClassNotFoundException\n");
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println("Conexión fallida SQLException\n");
            System.out.println(e);
        }
    }

    @Override
    public String ejecutarReporte(String nombreReporte, String rutaReporte, String rutaGenerado, String nombreArchivo, String tipoReporte, Connection cxn) {
        try {
            inicarC();
            File archivo = new File(rutaReporte + nombreReporte + ".jasper");
            JasperReport masterReport;
            masterReport = (JasperReport) JRLoader.loadObject(archivo);
            //JasperPrint imprimir = JasperFillManager.fillReport(masterReport, null, cxn);
            JasperPrint imprimir = JasperFillManager.fillReport(masterReport, null, conexion);
            String outFileName = rutaGenerado + nombreArchivo;
            JRExporter exporter = null;
            if (tipoReporte.equals("PDF")) {
                exporter = new JRPdfExporter();
            } else if (tipoReporte.equals("XLSX")) {
                exporter = new JRXlsxExporter();
            } else if (tipoReporte.equals("XLS")) {
                exporter = new JExcelApiMetadataExporter();
                exporter.setParameter(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE);
                exporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BACKGROUND, Boolean.TRUE);
            } else if (tipoReporte.equals("CSV")) {
                exporter = new JRCsvMetadataExporter();
                exporter.setParameter(JRCsvMetadataExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
            } else if (tipoReporte.equals("HTML")) {
                exporter = new JRXhtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
            } else if (tipoReporte.equals("DOCX")) {
                //exporter = new JRDocxExporter();
                exporter = new JRRtfExporter();
                //exporter.setParameter(JRDocxExporterParameter., Boolean.FALSE);
            }
            if (exporter != null) {
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, imprimir);
                exporter.exportReport();
            }
            //cerrarConexion();
            return outFileName;
        } catch (JRException e) {
            System.out.println("Error IniciarReporte.ejecutarReporte: " + e);
            System.out.println(e.getStackTrace());
            System.out.println("************************************");
            System.out.println(e.getCause().toString());
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error cerrar: " + e);
        }
    }
}

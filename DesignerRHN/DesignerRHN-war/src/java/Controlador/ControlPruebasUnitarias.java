package Controlador;

import ClasesAyuda.ColumnasBusquedaAvanzada;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.scrollpanel.ScrollPanel;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    private final String server = "192.168.0.16";
    private final int port = 21;
    private final String user = "Administrador";
    private final String pass = "Soporte9";

    private FTPClient ftpClient;

    public ControlPruebasUnitarias() {
        ftpClient = new FTPClient();
    }

    public void conectarAlFTP() {
        try {
            ftpClient.connect(server);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (Exception e) {
            System.out.println("Error en conexion : " + e.toString());
        }
    }

    public void descargarArchivoFTP() throws IOException {
        try {
            conectarAlFTP();
            String remoteFile1 = "/DesignerRHN/SalidasUTL/DESARROLLO/Interfase_Total_TODOS.txt";
            File downloadFile1 = new File("C:/Interfase_Total_TODOS.txt");
            OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile1));
            boolean success = ftpClient.retrieveFile(remoteFile1, outputStream1);
            outputStream1.close();
            if (success) {
                System.out.println("File #1 has been downloaded successfully.");
            } else {
                System.out.println("Ni mierda !");
            }
            ftpClient.logout();
        } catch (Exception e) {
            System.out.println("Error descarga : " + e.toString());
        } finally {
            
        }
    }

}

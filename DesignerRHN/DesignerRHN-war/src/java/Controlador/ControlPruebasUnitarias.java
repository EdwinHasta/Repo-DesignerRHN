package Controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    private StreamedContent reporte;

    public StreamedContent getReporte() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File("C:\\DesignerRHN\\Reportes\\ArchivosPlanos\\JRPRODUCCION14022014033535.pdf"));
            reporte = new DefaultStreamedContent(fis, "application/pdf");
        } catch (FileNotFoundException ex) {
            System.out.println("Error leyendo archivo");
        }
        return reporte;
    }

    public void setReporte(StreamedContent reporte) {
        this.reporte = reporte;
    }
}

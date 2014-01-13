package Controlador;

import InterfaceAdministrar.AdministarReportesInterface;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    AdministarReportesInterface administarReportes;
    private Integer a;

    public ControlPruebasUnitarias() {
        a= null;
    }

    public int exeption(){
        try {
            return a.intValue();
        } catch (Exception e) {
            return -1;
        }
    }
    
    public void espichoelboton(){
        System.out.println("imprima: " + exeption());
    }
    public void crearReportePDF() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReportePDF();
    }
    public void crearReporteXLSX() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteXLSX();
    }
    public void crearReporteXLS() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteXLS();
    }
    public void crearReporteCSV() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteCSV();
    }
    public void crearReporteHTML() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteHTML();
    }
}

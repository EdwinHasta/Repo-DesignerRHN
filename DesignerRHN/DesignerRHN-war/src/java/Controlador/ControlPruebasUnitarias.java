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

    public ControlPruebasUnitarias() {
    }
    public void crearReporteXLS() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteXLS();
    }
    public void crearReporteCSV() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteCSV();
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

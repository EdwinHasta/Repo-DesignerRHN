package Controlador;

import InterfaceAdministrar.AdministarReportesInterface;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    AdministarReportesInterface administarReportes;

    Calendar fechaPrueba;

    public ControlPruebasUnitarias() {
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

    public void eventBtn() {
        fechaPrueba = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaPrueba");
        if (fechaPrueba == null) {
            System.out.println("Error jodete !");
        } else {
            fechaPrueba.setStyleClass("myClass3");
        }
        RequestContext.getCurrentInstance().update("form:fechaPrueba");
    }

    public Calendar getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Calendar fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

}

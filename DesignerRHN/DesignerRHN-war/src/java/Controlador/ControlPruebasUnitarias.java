package Controlador;

import InterfaceAdministrar.AdministarReportesInterface;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    AdministarReportesInterface administarReportes;

    Date fechaPrueba;
    String color, decoracion;
    String alto, ancho;

    public ControlPruebasUnitarias() {
        color = "black";
        decoracion = "none";
        alto = "99";
        ancho = "80";
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
        color = "red";
        decoracion = "underline";
        ancho = "99";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form");
    }

    public void restaurar() {
        color = "black";
        decoracion = "none";
        ancho = "80";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form");
    }

    public Date getFechaPrueba() {
        if(fechaPrueba == null){
            fechaPrueba = new Date();
        }
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDecoracion() {
        return decoracion;
    }

    public void setDecoracion(String decoracion) {
        this.decoracion = decoracion;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

}

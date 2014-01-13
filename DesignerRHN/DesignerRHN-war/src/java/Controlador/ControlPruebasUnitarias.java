<<<<<<< HEAD
package Controlador;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    AdministarReportesInterface administarReportes;
    private Integer a;

    public ControlPruebasUnitarias() {
<<<<<<< HEAD
        a= null;
=======
        fechaPrueba = null;
        auxFecha = new Date();
>>>>>>> origin/master
    }

<<<<<<< HEAD
    public int exeption(){
        try {
            return a.intValue();
        } catch (Exception e) {
            return -1;
        }
=======
package Controlador;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    Date fechaPrueba;
    Date auxFecha;

    public ControlPruebasUnitarias() {
        fechaPrueba = null;
        auxFecha = new Date();
>>>>>>> origin/master
    }
<<<<<<< HEAD
    
    public void espichoelboton(){
        System.out.println("imprima: " + exeption());
    }
    public void crearReportePDF() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReportePDF();
=======
    public void cambioRealizado() {
        System.out.println("Se realizo el cambio de la fecha");
>>>>>>> origin/master
    }

    public void cambioNoRealizado() {
        System.out.println("Se realizo No se NOOO el cambio de la fecha");
    }
    
    public void select(){
        System.out.println("Se selecciono !");
    }

    public Date getAuxFecha() {
        return auxFecha;
    }

    public void setAuxFecha(Date auxFecha) {
        this.auxFecha = auxFecha;
    }

    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

}
=======

    public void cambioRealizado() {
        System.out.println("Se realizo el cambio de la fecha");
    }

    public void cambioNoRealizado() {
        System.out.println("Se realizo No se NOOO el cambio de la fecha");
    }
    
    public void select(){
        System.out.println("Se selecciono !");
    }

    public Date getAuxFecha() {
        return auxFecha;
    }

    public void setAuxFecha(Date auxFecha) {
        this.auxFecha = auxFecha;
    }

    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

}
>>>>>>> origin/master

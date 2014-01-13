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
    }

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

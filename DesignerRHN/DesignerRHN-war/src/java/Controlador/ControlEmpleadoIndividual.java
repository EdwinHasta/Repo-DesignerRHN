package Controlador;

import Entidades.Empleados;
import Entidades.HVHojasDeVida;
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlEmpleadoIndividual implements Serializable {

    @EJB
    AdministrarEmpleadoIndividualInterface administrarEmpleadoIndividual;
    private Empleados empleado;
    private HVHojasDeVida hojaDeVidaPersona;

    public ControlEmpleadoIndividual() {
    }
    

    public void recibirEmpleado(Empleados empl) {
        empleado = empl;
        hojaDeVidaPersona = administrarEmpleadoIndividual.hvHojaDeVidaPersona(empleado.getPersona().getSecuencia());
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public HVHojasDeVida getHojaDeVidaPersona() {
        return hojaDeVidaPersona;
    }
}

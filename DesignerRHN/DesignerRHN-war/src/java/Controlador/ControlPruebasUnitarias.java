package Controlador;

import Entidades.HvExperienciasLaborales;
import InterfaceAdministrar.AdministrarPerExperienciaLaboralInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaConsultasLiquidacionesInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaConsultasLiquidacionesInterface persistenciaConsultasLiquidaciones;
    @EJB
    AdministrarPerExperienciaLaboralInterface administrarPerExperienciaLaboral;
    List<HvExperienciasLaborales> listExperienciaLaboralEmpl;
    Integer progress;
    boolean bandera;
    Date fechaPrueba;
    BigInteger secuenciaPersona;

    public ControlPruebasUnitarias() {
        bandera = true;
        fechaPrueba = new Date();
        secuenciaPersona = new BigInteger("10675984");
    }

    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + 1;
            if (progress > 100) {
                progress = 100;
                bandera = false;
                System.out.println("Hola :$");
            }
            System.out.println("Bandera: " + bandera);
            if (bandera == true) {
                System.out.println("Hola :$$$$");
                RequestContext.getCurrentInstance().update("form:barra");
            }
        }
        System.out.println("Lau");
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
        System.out.println("Lau !!");
        RequestContext.getCurrentInstance().update("form:barra");
    }

    public void cancel() {
        progress = null;
        bandera = true;
    }

    public void cometalo() throws ParseException {
        persistenciaConsultasLiquidaciones.liquidacionesCerradas("01/01/2013", "31/01/2013");
    }

    public Date getFechaPrueba() {
        return fechaPrueba;
    }

    public void setFechaPrueba(Date fechaPrueba) {
        this.fechaPrueba = fechaPrueba;
    }

    public List<HvExperienciasLaborales> getListExperienciaLaboralEmpl() {
        if (listExperienciaLaboralEmpl == null) {
            listExperienciaLaboralEmpl = administrarPerExperienciaLaboral.listExperienciasLaboralesSecuenciaEmpleado(secuenciaPersona);
        }
        listExperienciaLaboralEmpl.get(0).setFechadesde(null);
        return listExperienciaLaboralEmpl;
    }

    public void setListExperienciaLaboralEmpl(List<HvExperienciasLaborales> listExperienciaLaboralEmpl) {
        this.listExperienciaLaboralEmpl = listExperienciaLaboralEmpl;
    }

    public void eventoMouse() {
        System.out.println("Evento focus OK");
    }

    public void accion() {
        System.out.println("FUNCIONA! :$$");
    }

   
}

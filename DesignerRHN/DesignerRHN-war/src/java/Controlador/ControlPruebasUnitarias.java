package Controlador;

import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaConsultasLiquidacionesInterface;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlPruebasUnitarias implements Serializable {

    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaConsultasLiquidacionesInterface persistenciaConsultasLiquidaciones;
    Integer progress;
    boolean bandera;

    public ControlPruebasUnitarias() {
        bandera = true;
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
}

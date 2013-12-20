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

    public void crearReporte() {
        //administarReportes.generarReporteXLSX();
        administarReportes.generarReporteXML();
    }
}

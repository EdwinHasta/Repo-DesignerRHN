package Controlador;

import InterfaceAdministrar.AdministrarComprobantesInterface;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlConprobantes {

    @EJB
    AdministrarComprobantesInterface administrarComprobantes;
    
    public ControlConprobantes() {
    }
}

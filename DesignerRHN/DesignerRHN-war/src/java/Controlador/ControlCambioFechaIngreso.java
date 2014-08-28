/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VigenciasTiposContratos;
import InterfaceAdministrar.AdministrarCambiosFechasIngresosInterface;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlCambioFechaIngreso {

    @EJB
    AdministrarCambiosFechasIngresosInterface administrarVigenciasTiposContratos;
    
    private BigInteger secuenciaEmpleado;
    private Empleados empleado;
    private VigenciasTiposContratos vigenciaTipoContrato;
    private Date fechaNueva;
            
    public ControlCambioFechaIngreso() {
        empleado = new Empleados();
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasTiposContratos.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec, VigenciasTiposContratos vigencia) {
        System.out.println("Recibe Empleado");
        RequestContext context = RequestContext.getCurrentInstance();
        secuenciaEmpleado = sec;
        vigenciaTipoContrato = vigencia;
        //empleado = administrarVigenciasTiposContratos.buscarEmpleado(BigInteger.valueOf(10661039));
        empleado = administrarVigenciasTiposContratos.buscarEmpleado(secuenciaEmpleado);

    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public VigenciasTiposContratos getVigenciaTipoContrato() {
        return vigenciaTipoContrato;
    }

    public void setVigenciaTipoContrato(VigenciasTiposContratos vigenciaTipoContrato) {
        this.vigenciaTipoContrato = vigenciaTipoContrato;
    }

    public Date getFechaNueva() {
        return fechaNueva;
    }

    public void setFechaNueva(Date fechaNueva) {
        this.fechaNueva = fechaNueva;
    }
    
    

}

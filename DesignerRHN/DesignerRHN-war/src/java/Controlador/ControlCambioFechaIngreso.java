/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.VigenciasTiposContratos;
import InterfaceAdministrar.AdministrarCambiosFechasIngresosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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
public class ControlCambioFechaIngreso implements Serializable {

    @EJB
    AdministrarCambiosFechasIngresosInterface administrarVigenciasTiposContratos;

    private BigInteger secuenciaEmpleado;
    private Empleados empleado;
    private VigenciasTiposContratos vigenciaTipoContrato;
    private Date fechaNueva;
    private String mensajeValidacion;
    private SimpleDateFormat formato;
    private String fechaNew;
    private String fechaOld;

    public ControlCambioFechaIngreso() {
        empleado = new Empleados();
        formato = new SimpleDateFormat("dd/MM/yyyy");
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

    public void dialogoPaso2() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:paso2");
        context.execute("paso2.show()");
    }

    public void dialogoPaso3() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:paso3");
        context.execute("paso3.show()");
    }
    
    public void cambiarFechaAntigua() {
        int pasa = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        if (fechaNueva == null) {
            mensajeValidacion = mensajeValidacion + "* Fecha Nueva";
            pasa++;
        }

        if (pasa == 0) {
            fechaNew = formato.format(fechaNueva);
            fechaOld = formato.format(vigenciaTipoContrato.getFechavigencia());
            context.update("formularioDialogos:paso1");
            context.execute("paso1.show()");
            System.out.println("Fecha Nueva: " + fechaNew + " Fecha Vieja: " + fechaOld);
        } else {
            context.update("formularioDialogos:validacionCambio");
            context.execute("validacionCambio.show()");
        }

    }
    
    public void realizarCambioFecha(){
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            administrarVigenciasTiposContratos.cambiarFechaIngreso(empleado.getSecuencia(),vigenciaTipoContrato.getFechavigencia(), fechaNueva);
            context.update("formularioDialogos:exito");
            context.execute("exito.show()");
            fechaNueva = null;
            context.update("form:divFechaNueva");
            context.update("form:fechaNueva");
            context.update("form:PanelTotal");
        } catch (Exception e) {
            System.out.println("Error al realizar el cambio de fecha de ingreso al empleado");
            context.update("formularioDialogos:error");
            context.execute("error.show()");
        }
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

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getFechaNew() {
        return fechaNew;
    }

    public void setFechaNew(String fechaNew) {
        this.fechaNew = fechaNew;
    }

    public String getFechaOld() {
        return fechaOld;
    }

    public void setFechaOld(String fechaOld) {
        this.fechaOld = fechaOld;
    }

}

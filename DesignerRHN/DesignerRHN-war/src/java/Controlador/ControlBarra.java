package Controlador;

import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarBarraInterface;
import java.io.Serializable;
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
public class ControlBarra implements Serializable {

    @EJB
    AdministrarBarraInterface administrarBarra;
    private Integer totalEmpleadosParaLiquidar;
    private Integer totalEmpleadosLiquidados;
    private boolean permisoParaLiquidar;
    private String usuarioBD;
    private Integer barra, bkBarra;
    private ParametrosEstructuras parametroEstructura;
    private boolean empezar, botonLiquidar, botonCancelar, cambioImagen;
    private boolean bandera, preparandoDatos;
    private String horaInicialLiquidacion, horaFinalLiquidacion, mensajeBarra, mensajeEstado, imagenEstado;
    private SimpleDateFormat formato;

    public ControlBarra() {
        totalEmpleadosParaLiquidar = 0;
        totalEmpleadosLiquidados = 0;
        barra = 0;
        empezar = false;
        bandera = true;
        preparandoDatos = false;
        botonCancelar = true;
        botonLiquidar = false;
        horaInicialLiquidacion = "--:--:--";
        horaFinalLiquidacion = "--:--:--";
        formato = new SimpleDateFormat("hh:mm:ss a");
        mensajeEstado = "Oprima el boton liquidar para iniciar.";
        imagenEstado = "nom_parametros.gif";
        cambioImagen = true;
    }

    public void contarLiquidados() {
        totalEmpleadosLiquidados = administrarBarra.empleadosLiquidados();
        RequestContext.getCurrentInstance().update("form:empleadosLiquidados");
    }

    public void liquidar() {
        RequestContext context = RequestContext.getCurrentInstance();
        empezar = true;
        usuarioBD = administrarBarra.usuarioBD();
        permisoParaLiquidar = administrarBarra.permisosLiquidar(usuarioBD);
        if (permisoParaLiquidar == true) {
            administrarBarra.inicializarParametrosEstados();
            administrarBarra.liquidarNomina();
            mensajeBarra = "Preparando datos...";
            mensajeEstado = "Un momento mientras se analizan los parametros.";
            imagenEstado = "hand2.gif";
            preparandoDatos = true;
            botonLiquidar = true;
            botonCancelar = false;
            Date horaInicio = new Date();
            System.out.println("Hora Inicio: " + formato.format(horaInicio));
            horaInicialLiquidacion = formato.format(horaInicio);
            horaFinalLiquidacion = "--:--:--";
            context.execute("barra.start()");
            context.update("form:liquidar");
            context.update("form:cancelar");
            context.update("form:horaF");
            context.update("form:horaI");
            context.update("form:estadoLiquidacion");
            context.update("form:imagen");
        } else {
            System.out.println("Liquidar: " + permisoParaLiquidar);
        }
    }

    public void limpiarbarra() {
        barra = null;
        if (empezar == false) {
            empezar = true;
        } else if (empezar == true) {
            empezar = false;
        }
    }

    public void liquidacionCompleta() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Información", "Liquidación terminada con Éxito."));
        mensajeBarra = "Liquidación Completa (" + barra + "%)";
        mensajeEstado = "Liquidación terminada con exito.";
        imagenEstado = "hand3.gif";
        empezar = false;
        botonCancelar = true;
        botonLiquidar = false;
        contarLiquidados();
        Date horaFinal = new Date();
        System.out.println("Hora Final: " + formato.format(horaFinal));
        horaFinalLiquidacion = formato.format(horaFinal);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:horaF");
        context.update("form:liquidar");
        context.update("form:cancelar");
        context.update("form:barra");
        context.update("form:estadoLiquidacion");
        context.update("form:imagen");
    }

    public void cancelarLiquidacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("barra.cancel()");
        empezar = false;
        administrarBarra.cancelarLiquidacion(usuarioBD);
        Date horaFinal = new Date();
        horaFinalLiquidacion = formato.format(horaFinal);
        mensajeBarra = "Liquidacion Cancelada (" + barra + "%)";
        //bkBarra = barra;
        mensajeEstado = "El proceso de liquidacion fue cancelado.";
        imagenEstado = "cancelarLiquidacion.jpg";
        botonCancelar = true;
        botonLiquidar = false;
        context.update("form:horaF");
        context.update("form:liquidar");
        context.update("form:cancelar");
        context.update("form:estadoLiquidacion");
        context.update("form:imagen");
        // barra = bkBarra;
        System.out.println(barra);
        //System.out.println(bkBarra);
        context.execute("barra.setValue("+barra+")");
        //context.execute("barra.getValue()");
        //context.update("form:barra"); 
    }

    public void actualizar() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:barra");
    }
    //GETTER AND SETTER

    public Integer getTotalEmpleadosParaLiquidar() {
        totalEmpleadosParaLiquidar = administrarBarra.empleadosParaLiquidar();
        return totalEmpleadosParaLiquidar;
    }

    public void setTotalEmpleadosParaLiquidar(Integer totalEmpleadosParaLiquidar) {
        this.totalEmpleadosParaLiquidar = totalEmpleadosParaLiquidar;
    }

    public Integer getTotalEmpleadosLiquidados() {
        return totalEmpleadosLiquidados;
    }

    public void setTotalEmpleadosLiquidados(Integer totalEmpleadosLiquidados) {
        this.totalEmpleadosLiquidados = totalEmpleadosLiquidados;
    }

    public Integer getBarra() {
        if (empezar == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (preparandoDatos == true) {
                barra = 101;
                context.update("form:barra");
                String estado = administrarBarra.estadoLiquidacion(usuarioBD);
                if (!estado.equalsIgnoreCase("INICIADO") && !estado.equalsIgnoreCase("EN COLA")) {
                    preparandoDatos = false;
                    barra = null;
                    bandera = true;
                    mensajeEstado = "Liquidando personal.";
                    context.update("form:estadoLiquidacion");
                }
            } else {
                if (barra == null) {
                    barra = 0;
                } else {
                    barra = administrarBarra.progresoLiquidacion(totalEmpleadosParaLiquidar);
                    if (barra >= 100) {
                        barra = 100;
                        System.out.println(":/");
                        bandera = false;
                    }
                    mensajeBarra = "Liquidando... " + barra + "%";
                    if (bandera == true) {
                        if (cambioImagen == true) {
                            cambioImagen = false;
                            imagenEstado = "hand2.gif";
                        } else {
                            cambioImagen = true;
                            imagenEstado = "hand1.gif";
                        }
                        context.update("form:barra");
                        context.update("form:imagen");
                        contarLiquidados();
                    }
                }
            }
        }
        System.out.println(":)");
        return barra;
    }

    public void setBarra(Integer barra) {
        this.barra = barra;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarBarra.parametrosLiquidacion();
        }
        return parametroEstructura;
    }

    public String getHoraInicialLiquidacion() {
        return horaInicialLiquidacion;
    }

    public String getHoraFinalLiquidacion() {
        return horaFinalLiquidacion;
    }

    public String getMensajeBarra() {
        return mensajeBarra;
    }

    public boolean isBotonLiquidar() {
        return botonLiquidar;
    }

    public boolean isBotonCancelar() {
        return botonCancelar;
    }

    public String getMensajeEstado() {
        return mensajeEstado;
    }

    public String getImagenEstado() {
        return imagenEstado;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.Estructuras;
import InterfaceAdministrar.AdministrarReingresarEmpleadoInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class ControlReingresarEmpleado implements Serializable {

    @EJB
    AdministrarReingresarEmpleadoInterface administrarReingresarEmpleado;
    //LOV EMPLEADOS
    private List<Empleados> lovEmpleados;
    private List<Empleados> filtradoLovEmpleados;
    private Empleados empleadoSeleccionado;
    //LOV ESTRUCTURAS
    private List<Estructuras> lovEstructuras;
    private List<Estructuras> filtradoLovEstructuras;
    private Estructuras estructuraSeleccionada;
    //Campos del Formulario
    private Empleados empleado;
    private Estructuras estructura;
    private Date fechaReingreso;
    private Date fechaFinContrato;
    private boolean aceptar;
    public String infoRegistroEmpleados;
    public String infoRegistroEstructuras;
    private String mensajeValidacion;
    public Date fechaRetiro;
    public String mostrarFechaRetiro;
    private SimpleDateFormat formato;
    public String nombre;

    public ControlReingresarEmpleado() {
        empleado = new Empleados();
        formato = new SimpleDateFormat("dd/MM/yyyy");
        estructura = new Estructuras();
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarReingresarEmpleado.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void cancelarModificaciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        empleado = new Empleados();
        estructura = new Estructuras();
        fechaReingreso = null;
        fechaFinContrato = null;
        context.update("form:nombreEmpleado");
        context.update("form:estructura");
        context.update("form:PanelTotal");
        context.update("form:divFechaReingreso");
        context.update("form:divFechaFinContrato");
    }

    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        empleado = new Empleados();
        estructura = new Estructuras();
        fechaReingreso = null;
        fechaFinContrato = null;
        context.update("form:nombreEmpleado");
        context.update("form:estructura");
        context.update("form:fechaReingreso");
        context.update("form:fechaFinContrato");
    }

    //AUTOCOMPLETAR
    public void modificarReingreso(String confirmarCambio, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EMPLEADO")) {
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {

                setEmpleado(lovEmpleados.get(indiceUnicoElemento));

                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                context.update("formularioDialogos:empleadosDialogo");
                context.execute("empleadosDialogo.show()");
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                setEstructura(lovEstructuras.get(indiceUnicoElemento));
                lovEstructuras.clear();
                getLovEstructuras();
            } else {
                context.update("formularioDialogos:estructurasDialogo");
                context.execute("estructurasDialogo.show()");
            }
        }

    }

    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
    public void asignarIndex(int dlg) {

        RequestContext context = RequestContext.getCurrentInstance();
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:estructurasDialogo");
            context.execute("estructurasDialogo.show()");
        }

    }

    public void actualizarEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        empleado = empleadoSeleccionado;
        empleadoSeleccionado = null;
        aceptar = true;
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
        //context.update("formularioDialogos:LOVEmpleados");
        context.update("form:nombreEmpleado");
    }

    public void actualizarEstructuras() {
        RequestContext context = RequestContext.getCurrentInstance();
        estructura = estructuraSeleccionada;
        estructuraSeleccionada = null;
        aceptar = true;
        context.reset("formularioDialogos:LOVEstructuras:globalFilter");
        context.execute("LOVEstructuras.clearFilters()");
        context.execute("estructurasDialogo.hide()");
        //context.update("formularioDialogos:LOVEstructuras");
        context.update("form:estructura");
    }

    public void validarFechaNull() {
        if (fechaReingreso != null) {
            verificarFecha();
        } else {
            System.out.println("Nula Fecha ");
        }
    }

    public void verificarFecha() {
        RequestContext context = RequestContext.getCurrentInstance();
        formato = new SimpleDateFormat("dd/MM/yyyy");
        if (empleado.getPersona() == null) {
            context.update("formularioDialogos:seleccioneEmpleado");
            context.execute("seleccioneEmpleado.show()");
            fechaReingreso = null;
            context.update("form:divFechaReingreso");
            context.update("form:divFechaReingreso:fechaReingreso");
            context.update("form:fechaReingreso");
        } else {
            fechaRetiro = administrarReingresarEmpleado.obtenerFechaRetiro(empleado.getSecuencia());
            System.out.println("formato: " + formato);
            System.out.println("fechaRetiro: " + fechaRetiro);
            mostrarFechaRetiro = formato.format(fechaRetiro);

            System.out.println("mostrarFechaRetiro: " + mostrarFechaRetiro);
            if (fechaReingreso.before(fechaRetiro)) {
                context.update("formularioDialogos:errorFechas");
                context.execute("errorFechas.show()");
            }
        }
    }

    public void irPaso3() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:paso3");
        context.execute("paso3.show()");
    }

    public void reingresarEmpleado() {
        int pasa = 0;
        nombre = new String();
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        if (empleado.getPersona().getNombreCompleto() == null || empleado.getPersona().getNombreCompleto().equals(" ") || empleado.getPersona().getNombreCompleto().equals("")) {
            mensajeValidacion = mensajeValidacion + " * Empleado\n";
            pasa++;
        }

        if (estructura.getNombre() == null || estructura.getNombre().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " * Estructura\n";
            pasa++;
        }

        if (fechaFinContrato == null) {
            mensajeValidacion = mensajeValidacion + "* Fecha Fin Contrato";
            pasa++;
        }

        if (fechaReingreso == null) {
            mensajeValidacion = mensajeValidacion + "* Fecha Reingreso";
            pasa++;
        }

        if (pasa == 0) {
            System.out.println("Secuencia Empleado: " + empleado.getSecuencia());
            System.out.println("Centro Costo: " + estructura.getCentrocosto().getSecuencia());
            System.out.println("Fecha Reingreso: " + fechaReingreso);
            System.out.println("Empresa: " + estructura.getCentrocosto().getEmpresa().getSecuencia());
            System.out.println("Fecha Fin Contarto: " + fechaFinContrato);
            fechaRetiro = administrarReingresarEmpleado.obtenerFechaRetiro(empleado.getSecuencia());
            System.out.println("formato: " + formato);
            System.out.println("fechaRetiro: " + fechaRetiro);
            mostrarFechaRetiro = formato.format(fechaRetiro);

            System.out.println("mostrarFechaRetiro: " + mostrarFechaRetiro);
            if (fechaReingreso.before(fechaRetiro)) {
                context.update("formularioDialogos:errorFechas");
                context.execute("errorFechas.show()");
            } else {
                context.update("formularioDialogos:paso1");
                context.execute("paso1.show()");
                System.out.println("Empleado es: " + empleado.getPersona().getNombreCompleto());
                nombre = empleado.getPersona().getNombreCompleto();
            }
        } else {
            context.update("formularioDialogos:validacionReingreso");
            context.execute("validacionReingreso.show()");
        }

    }

    public void reingresoEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            administrarReingresarEmpleado.reintegrarEmpleado(empleado.getSecuencia(), estructura.getCentrocosto().getSecuencia(), fechaReingreso, estructura.getCentrocosto().getEmpresa().getSecuencia(), fechaFinContrato);

            context.update("formularioDialogos:exito");
            context.execute("exito.show()");
            empleado = new Empleados();
            estructura = new Estructuras();
            fechaReingreso = null;
            fechaFinContrato = null;
            context.update("form:nombreEmpleado");
            context.update("form:estructura");
            context.update("form:PanelTotal");
            context.update("form:divFechaReingreso");
            context.update("form:divFechaFinContrato");
        } catch (Exception e) {
            System.out.println("Error en borrar al empleado");
            context.update("formularioDialogos:error");
            context.execute("error.show()");
        }
    }

    public void cancelarCambioEmpleados() {
        empleadoSeleccionado = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.execute("LOVEmpleados.clearFilters()");
        context.execute("empleadosDialogo.hide()");
    }

    public void cancelarCambioEstructuras() {
        estructuraSeleccionada = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:LOVEstructuras:globalFilter");
        context.execute("LOVEstructuras.clearFilters()");
        context.execute("estructurasDialogo.hide()");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public List<Empleados> getLovEmpleados() {
        lovEmpleados = administrarReingresarEmpleado.listaEmpleados();

        RequestContext context = RequestContext.getCurrentInstance();

        if (lovEmpleados == null || lovEmpleados.isEmpty()) {
            infoRegistroEmpleados = "Cantidad de registros: 0 ";
        } else {
            infoRegistroEmpleados = "Cantidad de registros: " + lovEmpleados.size();
        }
        context.update("formularioDialogos:infoRegistroEmpleados");
        return lovEmpleados;
    }

    public void setLovEmpleados(List<Empleados> lovEmpleados) {
        this.lovEmpleados = lovEmpleados;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Estructuras getEstructura() {

        return estructura;
    }

    public void setEstructura(Estructuras estructura) {
        this.estructura = estructura;
    }

    public Date getFechaReingreso() {
        return fechaReingreso;
    }

    public void setFechaReingreso(Date fechaReingreso) {
        this.fechaReingreso = fechaReingreso;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public List<Empleados> getFiltradoLovEmpleados() {
        return filtradoLovEmpleados;
    }

    public void setFiltradoLovEmpleados(List<Empleados> filtradoLovEmpleados) {
        this.filtradoLovEmpleados = filtradoLovEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Estructuras> getLovEstructuras() {
        lovEstructuras = administrarReingresarEmpleado.listaEstructuras();

        RequestContext context = RequestContext.getCurrentInstance();

        if (lovEstructuras == null || lovEstructuras.isEmpty()) {
            infoRegistroEstructuras = "Cantidad de registros: 0 ";
        } else {
            infoRegistroEstructuras = "Cantidad de registros: " + lovEstructuras.size();
        }
        context.update("formularioDialogos:infoRegistroEstructuras");
        return lovEstructuras;
    }

    public void setLovEstructuras(List<Estructuras> lovEstructuras) {
        this.lovEstructuras = lovEstructuras;
    }

    public List<Estructuras> getFiltradoLovEstructuras() {
        return filtradoLovEstructuras;
    }

    public void setFiltradoLovEstructuras(List<Estructuras> filtradoLovEstructuras) {
        this.filtradoLovEstructuras = filtradoLovEstructuras;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getInfoRegistroEmpleados() {
        return infoRegistroEmpleados;
    }

    public void setInfoRegistroEmpleados(String infoRegistroEmpleados) {
        this.infoRegistroEmpleados = infoRegistroEmpleados;
    }

    public String getInfoRegistroEstructuras() {
        return infoRegistroEstructuras;
    }

    public void setInfoRegistroEstructuras(String infoRegistroEstructuras) {
        this.infoRegistroEstructuras = infoRegistroEstructuras;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getMostrarFechaRetiro() {
        return mostrarFechaRetiro;
    }

    public void setMostrarFechaRetiro(String mostrarFechaRetiro) {
        this.mostrarFechaRetiro = mostrarFechaRetiro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}

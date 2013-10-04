package Controlador;

import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlComprobantes implements Serializable {

    @EJB
    AdministrarComprobantesInterface administrarComprobantes;
    private List<Parametros> listaParametros;
    private Parametros parametroActual;
    private ParametrosEstructuras parametroEstructura;
    private List<Parametros> listaParametrosLOV;
    private List<Parametros> filtradoListaParametrosLOV;
    private Parametros parametroSeleccionado;
    //SOLUCIONES NODOS EMPLEADO
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    //SOLUCIONES NODOS EMPLEADOR
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;
    //REGISTRO ACTUAL
    private int registroActual;
    //OTROS
    private boolean aceptar, mostrarTodos;

    public ControlComprobantes() {
        registroActual = 0;
        aceptar = true;
        mostrarTodos = true;
    }

    public void anteriorEmpleado() {
        if (registroActual > 0) {
            registroActual--;
            parametroActual = listaParametros.get(registroActual);
            listaSolucionesNodosEmpleado = null;
            listaSolucionesNodosEmpleador = null;
            getListaSolucionesNodosEmpleado();
            getListaSolucionesNodosEmpleador();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:panelInf");
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
        }
    }

    public void siguienteEmpleado() {
        if (registroActual < (listaParametros.size() - 1)) {
            registroActual++;
            parametroActual = listaParametros.get(registroActual);
            listaSolucionesNodosEmpleado = null;
            listaSolucionesNodosEmpleador = null;
            getListaSolucionesNodosEmpleado();
            getListaSolucionesNodosEmpleador();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:panelInf");
            context.update("form:datosSolucionesNodosEmpleado");
            context.update("form:datosSolucionesNodosEmpleador");
        }
    }

    public void seleccionarEmpleado() {
        listaParametros.clear();
        listaParametros.add(parametroSeleccionado);
        parametroActual = parametroSeleccionado;
        registroActual = 0;
        filtradoListaParametrosLOV = null;
        parametroSeleccionado = null;
        aceptar = true;
        mostrarTodos = false;
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        getListaSolucionesNodosEmpleado();
        getListaSolucionesNodosEmpleador();
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovEmpleados:globalFilter");
        context.update("formularioDialogos:lovEmpleados");
        context.execute("buscarEmpleadoDialogo.hide()");
        context.update("form:panelInf");
        context.update("form:datosSolucionesNodosEmpleado");
        context.update("form:datosSolucionesNodosEmpleador");
    }

    public void cancelarSeleccionEmpleado() {
        filtradoListaParametrosLOV = null;
        parametroSeleccionado = null;
        aceptar = true;
    }

    public void mostarTodosEmpleados() {
        registroActual = 0;
        listaParametros = null;
        parametroActual = null;
        listaParametrosLOV = null;
        getParametroActual();
        listaSolucionesNodosEmpleado = null;
        listaSolucionesNodosEmpleador = null;
        getListaSolucionesNodosEmpleado();
        getListaSolucionesNodosEmpleador();
        mostrarTodos = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:panelInf");
        context.update("form:datosSolucionesNodosEmpleado");
        context.update("form:datosSolucionesNodosEmpleador");
    }

    public void activarAceptar() {
        aceptar = false;
    }
    //GETTER AND SETTER

    public List<Parametros> getListaParametros() {
        listaParametros = administrarComprobantes.parametrosComprobantes();
        return listaParametros;
    }

    public void setListaParametros(List<Parametros> listaParametros) {
        this.listaParametros = listaParametros;
    }

    public Parametros getParametroActual() {
        if (parametroActual == null) {
            getListaParametros();
            if (listaParametros != null) {
                parametroActual = listaParametros.get(registroActual);
            }
        } 
        return parametroActual;
    }

    public void setParametroActual(Parametros parametroActual) {
        this.parametroActual = parametroActual;
    }

    public ParametrosEstructuras getParametroEstructura() {
        if (parametroEstructura == null) {
            parametroEstructura = administrarComprobantes.parametroEstructura();
        }
        return parametroEstructura;
    }

    public void setParametroEstructura(ParametrosEstructuras parametroEstructura) {
        this.parametroEstructura = parametroEstructura;
    }

    public List<Parametros> getListaParametrosLOV() {
        if (listaParametrosLOV == null) {
            listaParametrosLOV = administrarComprobantes.parametrosComprobantes();
        }
        return listaParametrosLOV;
    }

    public void setListaParametrosLOV(List<Parametros> listaParametrosLOV) {
        this.listaParametrosLOV = listaParametrosLOV;
    }

    public Parametros getParametroSeleccionado() {
        return parametroSeleccionado;
    }

    public void setParametroSeleccionado(Parametros parametroSeleccionado) {
        this.parametroSeleccionado = parametroSeleccionado;
    }

    public List<Parametros> getFiltradoListaParametrosLOV() {
        return filtradoListaParametrosLOV;
    }

    public void setFiltradoListaParametrosLOV(List<Parametros> filtradoListaParametrosLOV) {
        this.filtradoListaParametrosLOV = filtradoListaParametrosLOV;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public boolean isMostrarTodos() {
        return mostrarTodos;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleado() {
        if (listaSolucionesNodosEmpleado == null) {
            listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosEmpleado(parametroActual.getEmpleado().getSecuencia());
        }
        return listaSolucionesNodosEmpleado;
    }

    public void setListaSolucionesNodosEmpleado(List<SolucionesNodos> listaSolucionesNodosEmpleado) {
        this.listaSolucionesNodosEmpleado = listaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleado() {
        return filtradolistaSolucionesNodosEmpleado;
    }

    public void setFiltradolistaSolucionesNodosEmpleado(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado) {
        this.filtradolistaSolucionesNodosEmpleado = filtradolistaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleador() {
        if (listaSolucionesNodosEmpleador == null) {
            listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosEmpleador(parametroActual.getEmpleado().getSecuencia());
        }
        return listaSolucionesNodosEmpleador;
    }

    public void setListaSolucionesNodosEmpleador(List<SolucionesNodos> listaSolucionesNodosEmpleador) {
        this.listaSolucionesNodosEmpleador = listaSolucionesNodosEmpleador;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleador() {
        return filtradolistaSolucionesNodosEmpleador;
    }

    public void setFiltradolistaSolucionesNodosEmpleador(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador) {
        this.filtradolistaSolucionesNodosEmpleador = filtradolistaSolucionesNodosEmpleador;
    }
}

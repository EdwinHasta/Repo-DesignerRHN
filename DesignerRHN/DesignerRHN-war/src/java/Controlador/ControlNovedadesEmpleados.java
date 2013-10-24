/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.PruebaEmpleados;
import Entidades.VWActualesTiposTrabajadores;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Viktor
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesEmpleados implements Serializable {

    @EJB
    AdministrarNovedadesEmpleadosInterface administrarNovedadesEmpleados;
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaEmpleado;
    //Seleccion Mostrar Todos
    private PruebaEmpleados seleccionMostrar;
    //LISTA QUE NO ES LISTA - 1 SOLO ELEMENTO
    private List<PruebaEmpleados> listaEmpleadosNovedad;
    private List<PruebaEmpleados> filtradosListaEmpleadosNovedad;
    //editar celda
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    //Modificar NOVEDADES
    //private List<Encargaturas> listaEncargaturasModificar;
    private boolean guardado, guardarOk;
    //LOV EMPLEADOS
    private List<VWActualesTiposTrabajadores> listaEmpleados;
    private List<VWActualesTiposTrabajadores> filtradoslistaEmpleados;
    private VWActualesTiposTrabajadores seleccionEmpleados;

    public ControlNovedadesEmpleados() {
        listaEmpleados = null;
        listaEmpleadosNovedad = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;

    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        
        for (int i = 0; i < listaEmpleados.size(); i++) {
            System.out.println("Lista Empleados: " + listaEmpleados.get(i).getEmpleado().getPersona().getNombreCompleto());
        }
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:empleadosDialogo");
            context.execute("empleadosDialogo.show()");
        } else if (dlg == 1) {//FALTAN MAS LOVS, OBVIAMENTE
        }

    }

    public void activarAceptar() {
        aceptar = false;
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void actualizarEmpleadosNovedad() {
        
        PruebaEmpleados pe = administrarNovedadesEmpleados.novedadEmpleado(seleccionEmpleados.getSecuencia());
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad.add(pe);
        } else {
            listaEmpleadosNovedad.add(pe);
        }
        secuenciaEmpleado = seleccionEmpleados.getSecuencia();
        // listaEncargaturas = null;
        context.execute("empleadosDialogo.hide()");
        context.reset("formularioDialogos:LOVEmpleados:globalFilter");
        context.update("formularioDialogos:LOVEmpleados");
        context.update("form:datosEmpleados");
        context.update("form:datosEncargaturasEmpleado");
        filtradosListaEmpleadosNovedad = null;
        seleccionEmpleados = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaEmpleadosNovedad.isEmpty()) {
            listaEmpleadosNovedad.clear();
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosAsignacion();
        } else {
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosAsignacion();
        }
        if (!listaEmpleadosNovedad.isEmpty()) {
            seleccionMostrar = listaEmpleadosNovedad.get(0);
            listaEmpleadosNovedad = null;
            getListaEmpleadosNovedad();
        }
        //listaEncargaturas = null;
        context.update("form:datosEmpleados");
        //context.update("form:datosEncargaturasEmpleado");
        filtradosListaEmpleadosNovedad = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public List<PruebaEmpleados> getListaEmpleadosNovedad() {
        if (listaEmpleadosNovedad == null) {
            listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosAsignacion();
        }

        return listaEmpleadosNovedad;
    }

    public void setListaEmpleadosNovedad(List<PruebaEmpleados> listaEmpleados) {
        this.listaEmpleadosNovedad = listaEmpleados;
    }

    public List<PruebaEmpleados> getFiltradosListaEmpleadosNovedad() {
        return filtradosListaEmpleadosNovedad;
    }

    public void setFiltradosListaEmpleadosNovedad(List<PruebaEmpleados> filtradosListaEmpleadosNovedad) {
        this.filtradosListaEmpleadosNovedad = filtradosListaEmpleadosNovedad;
    }

    public List<VWActualesTiposTrabajadores> getListaEmpleados() {
        if (listaEmpleados == null) {
            listaEmpleados = administrarNovedadesEmpleados.tiposTrabajadores();
        }
        return listaEmpleados;
    }

    public void setListaEmpleados(List<VWActualesTiposTrabajadores> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public List<VWActualesTiposTrabajadores> getFiltradoslistaEmpleados() {
        return filtradoslistaEmpleados;
    }

    public void setFiltradoslistaEmpleados(List<VWActualesTiposTrabajadores> filtradoslistaEmpleados) {
        this.filtradoslistaEmpleados = filtradoslistaEmpleados;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public BigInteger getSecuenciaEmpleado() {
        return secuenciaEmpleado;
    }

    public void setSecuenciaEmpleado(BigInteger secuenciaEmpleado) {
        this.secuenciaEmpleado = secuenciaEmpleado;
    }

    public VWActualesTiposTrabajadores getSeleccionEmpleados() {
        return seleccionEmpleados;
    }

    public void setSeleccionEmpleados(VWActualesTiposTrabajadores seleccionEmpleados) {
        this.seleccionEmpleados = seleccionEmpleados;
    }
}

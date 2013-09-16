package Controlador;

import Entidades.Comprobantes;
import Entidades.CortesProcesos;
import Entidades.Empleados;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarComprobantesInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlComprobantes implements Serializable{

    @EJB
    AdministrarComprobantesInterface administrarComprobantes;
    private Empleados empleado;
    private BigInteger secuenciaEmpleado;
    //TABLA 1
    private List<Comprobantes> listaComprobantes;
    private List<Comprobantes> filtradolistaComprobantes;
    //TABLA 2
    private List<CortesProcesos> listaCortesProcesos;
    private List<CortesProcesos> filtradolistaCortesProcesos;
    //TABLA 3
    private List<SolucionesNodos> listaSolucionesNodosEmpleado;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleado;
    //TABLA 4
    private List<SolucionesNodos> listaSolucionesNodosEmpleador;
    private List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador;

    public ControlComprobantes() {
        secuenciaEmpleado = BigInteger.valueOf(10661474);
    }

    public void recibirEmpleado(BigInteger sec) {
        secuenciaEmpleado = sec;
        empleado = null;
        getEmpleado();
    }

//GETTER AND SETTER
    public Empleados getEmpleado() {
        if (empleado == null) {
            empleado = administrarComprobantes.buscarEmpleado(secuenciaEmpleado);
        }
        return empleado;
    }

    public List<Comprobantes> getListaComprobantes() {
        getEmpleado();
        if (empleado != null) {
            if (listaComprobantes == null) {
                listaComprobantes = administrarComprobantes.comprobantesEmpleado(empleado.getSecuencia());
            }
        }
        return listaComprobantes;
    }

    public List<CortesProcesos> getListaCortesProcesos() {
        if (listaComprobantes != null) {
            if (listaCortesProcesos == null) {
                listaCortesProcesos = administrarComprobantes.cortesProcesosComprobante(listaComprobantes.get(2).getSecuencia());
            }
        }
        return listaCortesProcesos;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleado() {
        if (listaCortesProcesos != null) {
            if (listaSolucionesNodosEmpleado == null) {
                listaSolucionesNodosEmpleado = administrarComprobantes.solucionesNodosCorteProcesoEmpleado(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
            }
        }
        return listaSolucionesNodosEmpleado;
    }

    public List<SolucionesNodos> getListaSolucionesNodosEmpleador() {
        if (listaCortesProcesos != null) {
            if (listaSolucionesNodosEmpleador == null) {
                listaSolucionesNodosEmpleador = administrarComprobantes.solucionesNodosCorteProcesoEmpleador(listaCortesProcesos.get(0).getSecuencia(), empleado.getSecuencia());
            }
        }
        return listaSolucionesNodosEmpleador;
    }

    public void setListaComprobantes(List<Comprobantes> listaComprobantes) {
        this.listaComprobantes = listaComprobantes;
    }

    public List<Comprobantes> getFiltradolistaComprobantes() {
        return filtradolistaComprobantes;
    }

    public void setFiltradolistaComprobantes(List<Comprobantes> filtradolistaComprobantes) {
        this.filtradolistaComprobantes = filtradolistaComprobantes;
    }

    public void setListaCortesProcesos(List<CortesProcesos> listaCortesProcesos) {
        this.listaCortesProcesos = listaCortesProcesos;
    }

    public List<CortesProcesos> getFiltradolistaCortesProcesos() {
        return filtradolistaCortesProcesos;
    }

    public void setFiltradolistaCortesProcesos(List<CortesProcesos> filtradolistaCortesProcesos) {
        this.filtradolistaCortesProcesos = filtradolistaCortesProcesos;
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

    public void setListaSolucionesNodosEmpleador(List<SolucionesNodos> listaSolucionesNodosEmpleador) {
        this.listaSolucionesNodosEmpleador = listaSolucionesNodosEmpleador;
    }

    public List<SolucionesNodos> getFiltradolistaSolucionesNodosEmpleador() {
        return filtradolistaSolucionesNodosEmpleador;
    }

    public void setFiltradolistaSolucionesNodosEmpleador(List<SolucionesNodos> filtradolistaSolucionesNodosEmpleador) {
        this.filtradolistaSolucionesNodosEmpleador = filtradolistaSolucionesNodosEmpleador;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }
    
    public void actualiza(){
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosComprobantes");
    }
}

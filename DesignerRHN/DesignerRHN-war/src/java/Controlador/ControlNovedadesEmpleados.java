/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.PruebaEmpleados;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Viktor
 */
@ManagedBean
@SessionScoped
public class ControlNovedadesEmpleados implements Serializable{
    
    @EJB
    AdministrarNovedadesEmpleadosInterface administrarNovedadesEmpleados;
    

    //LISTA QUE NO ES LISTA - 1 SOLO ELEMENTO
    private List<PruebaEmpleados> listaEmpleadosNovedad;
    private List<PruebaEmpleados> filtradosListaEmpleados;
    //editar celda
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    
        public ControlNovedadesEmpleados() {
            listaEmpleadosNovedad = null;
    }
        
        //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public List<PruebaEmpleados> getListaEmpleados() {
        if(listaEmpleadosNovedad == null){
           listaEmpleadosNovedad = administrarNovedadesEmpleados.empleadosAsignacion();
        }
            
        return listaEmpleadosNovedad;
    }

    public void setListaEmpleados(List<PruebaEmpleados> listaEmpleados) {
        this.listaEmpleadosNovedad = listaEmpleados;
    }

    public List<PruebaEmpleados> getFiltradosListaEmpleados() {
        return filtradosListaEmpleados;
    }

    public void setFiltradosListaEmpleados(List<PruebaEmpleados> filtradosListaEmpleados) {
        this.filtradosListaEmpleados = filtradosListaEmpleados;
    }
        
        
        
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Ciudades;
import Entidades.Departamentos;
import InterfaceAdministrar.AdministrarCiudadesInterface;
import InterfaceAdministrar.AdministrarDepartamentosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlCiudades {

    @EJB
    AdministrarCiudadesInterface  administrarCiudades;
    @EJB
    AdministrarDepartamentosInterface administrarDepartamentos;
    //Listas
    private List<Departamentos> listaDepartamentos;
    private List<Departamentos> filtradoListaDepatartamentos;
    private Departamentos seleccionDepartamento;
    private List<Ciudades> listaCiudades;
    private List<Ciudades> filtradoListaCiudades;
   /*/Otros
    private boolean aceptar;
    private int index;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;*/
    
    public ControlCiudades() {
        
    }
    

    public List<Ciudades> getListaCiudades() {
        if (listaCiudades == null) {
//            listAficiones = administrarCarpetaDesigner.buscarAficiones();
            listaCiudades = administrarCiudades.Ciudades();
            return listaCiudades;
        } else {
            return listaCiudades;
        }
    }

    public void setListaCiudades(List<Ciudades> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<Ciudades> getFiltradoListaCiudades() {
                return filtradoListaCiudades;
    }

    public void setFiltradoListaCiudades(List<Ciudades> filtradoListaCiudades) {
        this.filtradoListaCiudades = filtradoListaCiudades;
    }

    public List<Departamentos> getListaDepartamentos() {
        if (listaDepartamentos == null) {
            listaDepartamentos = administrarDepartamentos.Departamentos();
            return listaDepartamentos;
        } else {
            return listaDepartamentos;
        }
    }

    public void setListaDepartamentos(List<Departamentos> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
    }

    public List<Departamentos> getFiltradoListaDepatartamentos() {
        return filtradoListaDepatartamentos;
    }

    public void setFiltradoListaDepatartamentos(List<Departamentos> filtradoListaDepatartamentos) {
        this.filtradoListaDepatartamentos = filtradoListaDepatartamentos;
    }

    public Departamentos getSeleccionDepartamento() {
        return seleccionDepartamento;
    }

    public void setSeleccionDepartamento(Departamentos seleccionDepartamento) {
        this.seleccionDepartamento = seleccionDepartamento;
    }
}

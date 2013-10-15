/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.Estructuras;
import Entidades.MotivosReemplazos;
import Entidades.TiposReemplazos;
import InterfaceAdministrar.AdministrarNovedadesReemplazosInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 *
 * @author user
 */
@Stateful
public class AdministrarNovedadesReemplazos implements AdministrarNovedadesReemplazosInterface{
    
    @EJB
    PersistenciaEncargaturasInterface persistenciaEncargaturas;
    @EJB
    PersistenciaMotivosReemplazosInterface persistenciaMotivosReemplazos;
    @EJB
    PersistenciaTiposReemplazosInterface persistenciaTiposReemplazos;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    
    private Encargaturas e;
    
    
    //Trae las encargaturas del empleado cuya secuencia se envía como parametro//
    @Override
    public List<Encargaturas> encargaturasEmpleado(BigInteger secEmpleado) {
        try {
            return persistenciaEncargaturas.encargaturasEmpleado(secEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesReemplazos.encargaturasEmpleado" + e);
            return null;
        }
    }

    @Override
    public Empleados encontrarEmpleado(BigInteger secEmpleado) {
        return persistenciaEmpleados.buscarEmpleado(secEmpleado);
    }

    //Listas de Tipos Reemplazos, Profesion, Instituciones, Adiestramiento
    @Override
    public List<Empleados> lovEmpleados(){
        return persistenciaEmpleados.buscarEmpleados();
    }
    @Override
    public List<TiposReemplazos> lovTiposReemplazos() {
        return persistenciaTiposReemplazos.tiposReemplazos();
    }

    @Override
    public List<MotivosReemplazos> lovMotivosReemplazos() {
        return persistenciaMotivosReemplazos.motivosReemplazos();
    }
    
    @Override
    public List<Estructuras> lovEstructuras(){
        return persistenciaEstructuras.estructuras();
    }

    
    /*Toca Arreglarlo con el Native Query
    @Override
    public List<Cargos> lovCargos() {
        return persistenciaCargos.cargos();
    }*/
       
    @Override
    public void modificarEncargatura(List<Encargaturas> listaEncargaturasModificar) {
        for (int i = 0; i < listaEncargaturasModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaEncargaturasModificar.get(i).getTiporeemplazo().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setTiporeemplazo(null);
                e = listaEncargaturasModificar.get(i);
            } else {
                e = listaEncargaturasModificar.get(i);
            }
            if (listaEncargaturasModificar.get(i).getMotivoreemplazo().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setMotivoreemplazo(null);
                e = listaEncargaturasModificar.get(i);
            } else {
                e = listaEncargaturasModificar.get(i);
            }
            if (listaEncargaturasModificar.get(i).getEmpleado().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setEmpleado(null);
                e = listaEncargaturasModificar.get(i);
            } else {
                e = listaEncargaturasModificar.get(i);
            }
            if (listaEncargaturasModificar.get(i).getEstructura().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setEstructura(null);
                e = listaEncargaturasModificar.get(i);
            } else {
                e = listaEncargaturasModificar.get(i);
            }
           
            //FALTA LO DE CARGOS
                       
            
            persistenciaEncargaturas.editar(e);
        }
    }

    @Override
    public void borrarEncargaturas(Encargaturas encargaturas) {
        persistenciaEncargaturas.borrar(encargaturas);
    }


    @Override
    public void crearEncargaturas(Encargaturas encargaturas) {
        persistenciaEncargaturas.crear(encargaturas);
    }
    
    
  
    

}

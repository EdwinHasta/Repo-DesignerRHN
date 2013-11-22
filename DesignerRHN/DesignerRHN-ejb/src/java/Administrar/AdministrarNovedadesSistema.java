/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.Motivosdefinitivas;
import Entidades.NovedadesSistema;
import InterfaceAdministrar.AdministrarNovedadesSistemaInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import InterfacePersistencia.PersistenciaNovedadesSistemaInterface;
import Persistencia.PersistenciaMotivosRetiros;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarNovedadesSistema implements AdministrarNovedadesSistemaInterface{
    
    @EJB
    PersistenciaNovedadesSistemaInterface persistenciaNovedades;
    @EJB
    PersistenciaMotivosDefinitivasInterface persistenciaMotivos;
    @EJB
    PersistenciaMotivosRetirosInterface persistenciaRetiros;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    
    //Trae las novedades del empleado cuya secuencia se envía como parametro//
    @Override
    public List<NovedadesSistema> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            return persistenciaNovedades.novedadesEmpleado(secuenciaEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesEmpleados.novedadesEmpleado" + e);
            return null;
        }
    }
    
    @Override
    public void borrarNovedades(NovedadesSistema novedades) {
        persistenciaNovedades.borrar(novedades);
    }

    @Override
    public void crearNovedades(NovedadesSistema novedades) {
        persistenciaNovedades.crear(novedades);
    }
    
    

    @Override
    public void modificarNovedades(List<NovedadesSistema> listaNovedadesModificar) {
        for (int i = 0; i < listaNovedadesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaNovedadesModificar.get(i).getTercero().getSecuencia() == null) {
                listaNovedadesModificar.get(i).setTercero(null);
            }
            
            persistenciaNovedades.editar(listaNovedadesModificar.get(i));
        }
    }
    
    public List<Empleados> buscarEmpleados(){
        return persistenciaEmpleados.buscarEmpleados();
    }
    
    public List<Empleados> lovEmpleados(){
        return persistenciaEmpleados.buscarEmpleados();
    }
    
    public List<Motivosdefinitivas> lovMotivos(){
        return persistenciaMotivos.buscarMotivosDefinitivas();
    }
    
    public List<MotivosRetiros> lovRetiros(){
        return persistenciaRetiros.buscarMotivosRetiros();
    }
    
}

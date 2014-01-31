/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.MotivosRetiros;
import Entidades.MotivosDefinitivas;
import Entidades.NovedadesSistema;
import Entidades.Vacaciones;
import InterfaceAdministrar.AdministrarNovedadesSistemaInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import InterfacePersistencia.PersistenciaNovedadesSistemaInterface;
import InterfacePersistencia.PersistenciaVacacionesInterface;
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
    @EJB
    PersistenciaVacacionesInterface persistenciaVacaciones;
    
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
    public void modificarNovedades(NovedadesSistema novedades) {
            persistenciaNovedades.editar(novedades);
        
    }
    
    public List<Empleados> buscarEmpleados(){
        return persistenciaEmpleados.todosEmpleados();
    }
    
    public List<Empleados> lovEmpleados(){
        return persistenciaEmpleados.todosEmpleados();
    }
    
    public List<MotivosDefinitivas> lovMotivos(){
        return persistenciaMotivos.buscarMotivosDefinitivas();
    }
    
    public List<MotivosRetiros> lovRetiros(){
        return persistenciaRetiros.consultarMotivosRetiros();
    }
    
    public List<NovedadesSistema> vacacionesEmpleado(BigInteger secuenciaEmpleado){
        return persistenciaNovedades.novedadesEmpleadoVacaciones(secuenciaEmpleado);
    }
        
    public List<Vacaciones> periodosEmpleado(BigInteger secuenciaEmpleado){
        return persistenciaVacaciones.periodoVacaciones(secuenciaEmpleado);
    }
}

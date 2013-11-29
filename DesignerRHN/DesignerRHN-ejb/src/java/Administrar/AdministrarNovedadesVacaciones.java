/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import InterfaceAdministrar.AdministrarNovedadesVacacionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarNovedadesVacaciones implements AdministrarNovedadesVacacionesInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    
    public List<Empleados> empleadosVacaciones() {
        return persistenciaEmpleados.empleadosVacaciones();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import InterfaceAdministrar.AdministrarNovedadesVacacionesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarNovedadesVacaciones implements AdministrarNovedadesVacacionesInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
        /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    public List<Empleados> empleadosVacaciones() {
        return persistenciaEmpleados.empleadosVacaciones(em);
    }
}

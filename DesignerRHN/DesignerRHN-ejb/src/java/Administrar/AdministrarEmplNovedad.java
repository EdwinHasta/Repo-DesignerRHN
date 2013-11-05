/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.Novedades;
import InterfaceAdministrar.AdministrarEmplNovedadInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplNovedad implements AdministrarEmplNovedadInterface{

    @EJB
    PersistenciaNovedadesInterface persistenciaNovedades;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    @Override
    public List<Novedades> listNovedadesEmpleado(BigInteger secuenciaE) {
        try {
            List<Novedades> listNovedades = persistenciaNovedades.novedadesEmpleadoTotales(secuenciaE);
            return listNovedades;
        } catch (Exception e) {
            System.out.println("Error listNovedadesEmpleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados actualEmpleado(BigInteger secuencia) {
        try {
            Empleados empl = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empl;
        } catch (Exception e) {
            System.out.println("Error actualEmpleado Admi : " + e.toString());
            return null;
        }
    }
}

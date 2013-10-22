/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.PruebaEmpleados;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaPruebaEmpleadosInterface;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarNovedadesEmpleados implements AdministrarNovedadesEmpleadosInterface {

    @EJB
    PersistenciaPruebaEmpleadosInterface persistenciaPruebaEmpleados;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;


    @Override
    public List<PruebaEmpleados> empleadosAsignacion() {
        List<Empleados> listaEmpleados = persistenciaEmpleados.empleadosNovedad();
        List<PruebaEmpleados> listaEmpleadosNovedad = new ArrayList<PruebaEmpleados>();
        for (int i = 0; i < listaEmpleados.size(); i++) {
            PruebaEmpleados p = persistenciaPruebaEmpleados.empleadosAsignacion(listaEmpleados.get(i).getSecuencia());
            listaEmpleadosNovedad.add(p);
        }
        
        return listaEmpleadosNovedad;
    }
}

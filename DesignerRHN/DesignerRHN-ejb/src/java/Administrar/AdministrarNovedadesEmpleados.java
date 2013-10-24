/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.PruebaEmpleados;
import Entidades.VWActualesTiposTrabajadores;
import InterfaceAdministrar.AdministrarNovedadesEmpleadosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaPruebaEmpleadosInterface;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import Persistencia.PersistenciaVWActualesTiposTrabajadores;
import java.math.BigInteger;
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
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;

    @Override
    public List<PruebaEmpleados> empleadosAsignacion() {
        List<Empleados> listaEmpleados = persistenciaEmpleados.empleadosNovedad();
        List<PruebaEmpleados> listaEmpleadosNovedad = new ArrayList<PruebaEmpleados>();
        for (int i = 0; i < listaEmpleados.size(); i++) {
            PruebaEmpleados p = persistenciaPruebaEmpleados.empleadosAsignacion(listaEmpleados.get(i).getSecuencia());

            if (p != null) {
                listaEmpleadosNovedad.add(p);
            } else {
                p = new PruebaEmpleados();
                p.setCodigo(listaEmpleados.get(i).getCodigoempleado());
                p.setId(listaEmpleados.get(i).getSecuencia());
                p.setNombre(listaEmpleados.get(i).getPersona().getNombreCompleto());
                p.setValor(null);
                listaEmpleadosNovedad.add(p);
            }

        }
        return listaEmpleadosNovedad;
    }

    public PruebaEmpleados novedadEmpleado(BigInteger secuenciaEmpleado) {
        return persistenciaPruebaEmpleados.empleadosAsignacion(secuenciaEmpleado);
    }

    //Listas de Tipos Reemplazos, Motivos Reemplazos, Estructuras, Cargos
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.empleadosNovedad();
    }
    // Que tipo de Trabajador es
    public List<VWActualesTiposTrabajadores> tiposTrabajadores(){
        return persistenciaVWActualesTiposTrabajadores.tipoTrabajadorEmpleado();
    }
    
}

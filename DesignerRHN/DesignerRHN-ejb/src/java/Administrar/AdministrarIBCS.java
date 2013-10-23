/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarIBCSInterface;
import Entidades.Empleados;
import Entidades.Ibcs;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaIBCSInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarIBCS implements AdministrarIBCSInterface {

    @EJB
    PersistenciaIBCSInterface persistenciaIBCS;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    Empleados empleado;
    List<Ibcs> listIbcsPorEmpleado;

    @Override
    public List<Ibcs> ibcsPorEmplelado(BigInteger secEmpleado) {
        try {
            listIbcsPorEmpleado = persistenciaIBCS.buscarIbcsPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Ibcs ERROR " + e);
            listIbcsPorEmpleado = null;
        }
        return listIbcsPorEmpleado;
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("AdministrarVigenciasFormasPagos buscarEmpleado error" + e);
            empleado = null;
            return empleado;
        }
    }
}

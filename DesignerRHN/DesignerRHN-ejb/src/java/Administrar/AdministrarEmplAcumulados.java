/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import InterfaceAdministrar.AdministrarEmplAcumuladosInterface;
import Entidades.VWAcumulados;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaVWAcumuladosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplAcumulados implements AdministrarEmplAcumuladosInterface {

    @EJB
    PersistenciaVWAcumuladosInterface persistenciaVWAcumulados;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    private Empleados empleado;
    private List<VWAcumulados> listVWAcumulados;

    @Override
    public List<VWAcumulados> mostrarVWAcumuladosPorEmpleado(BigInteger secEmpleado) {
        try {
            listVWAcumulados = persistenciaVWAcumulados.buscarAcumuladosPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.err.println("ERROR EN ADMINISTRAR EMPLACUMULADOS ERROR " + e);
            listVWAcumulados = null;
        } finally {
            return listVWAcumulados;
        }
    }
    @Override
    public Empleados mostrarEmpleado(BigInteger secEmplado) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secEmplado);
        } catch (Exception e) {
            empleado = null;
            System.err.println("ERROR Administrar emplAcumulados ERROR : " + e);
        } finally {
            return empleado;
        }
    }
}

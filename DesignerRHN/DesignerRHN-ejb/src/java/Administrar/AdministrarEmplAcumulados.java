/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'EmplAcumulados'.
 *
 * @author betelgeuse
 */
@Stateful
public class AdministrarEmplAcumulados implements AdministrarEmplAcumuladosInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaVWAcumulados'.
     */
    @EJB
    PersistenciaVWAcumuladosInterface persistenciaVWAcumulados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpleados'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public List<VWAcumulados> consultarVWAcumuladosEmpleado(BigInteger secEmpleado) {
        try {
            return persistenciaVWAcumulados.buscarAcumuladosPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.err.println("ERROR EN ADMINISTRAR EMPLACUMULADOS ERROR " + e);
            return null;
        }
    }
    
    @Override
    public Empleados consultarEmpleado(BigInteger secEmplado) {
        try {
            return persistenciaEmpleados.buscarEmpleadoSecuencia(secEmplado);
        } catch (Exception e) {
            System.err.println("ERROR Administrar emplAcumulados ERROR : " + e);
            return null;
        } 
    }
}

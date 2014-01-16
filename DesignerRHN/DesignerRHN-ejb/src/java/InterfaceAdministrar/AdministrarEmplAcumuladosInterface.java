/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.VWAcumulados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEmplAcumuladosInterface {

    /**
     * Metodo Encargado de traer las VWAcumulados de un Empleado Especifico.
     *
     * @param secEmpleado Secuencia del Empleado.
     * @return Lista de VWAcumulados.
     */
    public List<VWAcumulados> consultarVWAcumuladosPorEmpleado(BigInteger secEmpleado);

    /**
     * *
     * Metodo encargado de buscar un Empleado especifico
     *
     * @param secEmpleado Secuencia del Empleado
     * @return Empleado .
     */
    public Empleados consultarEmpleado(BigInteger secEmpleado);
}

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

    public List<VWAcumulados> mostrarVWAcumuladosPorEmpleado(BigInteger secEmpleado);

    public Empleados mostrarEmpleado(BigInteger secEmplado);
}

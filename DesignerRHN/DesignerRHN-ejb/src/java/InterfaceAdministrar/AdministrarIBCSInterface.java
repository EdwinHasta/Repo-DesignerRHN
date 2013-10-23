/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Ibcs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarIBCSInterface {

    public List<Ibcs> ibcsPorEmplelado(BigInteger secEmpleado);

    public Empleados buscarEmpleado(BigInteger secuencia);
}

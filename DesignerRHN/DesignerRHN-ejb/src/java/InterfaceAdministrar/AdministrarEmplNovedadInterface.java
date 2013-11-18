/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.Novedades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarEmplNovedadInterface {

    public List<Novedades> listNovedadesEmpleado(BigInteger secuenciaE);

    public Empleados actualEmpleado(BigInteger secuencia);
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.PruebaEmpleados;
import Entidades.VWActualesTiposTrabajadores;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Viktor
 */
public interface AdministrarNovedadesEmpleadosInterface {

   public List<PruebaEmpleados> empleadosAsignacion();
   public List<Empleados> lovEmpleados();
   public PruebaEmpleados novedadEmpleado(BigInteger secuenciaEmpleado);
   public List<VWActualesTiposTrabajadores> tiposTrabajadores();
}

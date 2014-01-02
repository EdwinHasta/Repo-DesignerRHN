/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.NormasLaborales;
import Entidades.VigenciasNormasEmpleados;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarVigenciaNormaLaboralInterface {

    public List<VigenciasNormasEmpleados> vigenciasNormasEmpleadosPorEmpleado(BigInteger secEmpleado);

    public void modificarVigenciaNormaLaboral(List<VigenciasNormasEmpleados> listVNEModificadas);

    public void borrarVigenciaNormaLaboral(VigenciasNormasEmpleados vigenciasNormasEMpleados);

    public void crearVigenciaNormaLaboral(VigenciasNormasEmpleados vigenciasNormasEmpleados);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<NormasLaborales> mostrarNormasLaborales();
}

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

    public List<VigenciasNormasEmpleados> vigenciasNormasEmpleadosEmpl(BigInteger secEmpleado);

    public void modificarVNE(List<VigenciasNormasEmpleados> listVNEModificadas);

    public void borrarVNE(VigenciasNormasEmpleados vigenciasNormasEmpleados);

    public void crearVNE(VigenciasNormasEmpleados vigenciasNormasEmpleados);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public List<NormasLaborales> normasLaborales();
}

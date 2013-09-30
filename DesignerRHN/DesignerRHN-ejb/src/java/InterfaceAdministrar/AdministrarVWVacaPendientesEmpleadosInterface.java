
package InterfaceAdministrar;

import Entidades.Empleados;
import Entidades.VWVacaPendientesEmpleados;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarVWVacaPendientesEmpleadosInterface {

    public void crearVacaPendiente(VWVacaPendientesEmpleados vaca);

    public void editarVacaPendiente(VWVacaPendientesEmpleados vaca);

    public void borrarVacaPendiente(VWVacaPendientesEmpleados vaca);

    public List<VWVacaPendientesEmpleados> vacaPendientesPendientes(Empleados empl);

    public List<VWVacaPendientesEmpleados> vacaPendientesDisfrutadas(Empleados empl);
    
    public Empleados obtenerEmpleado(BigInteger secuencia);
    
    public BigDecimal diasProvisionadosEmpleado(Empleados empl);
}

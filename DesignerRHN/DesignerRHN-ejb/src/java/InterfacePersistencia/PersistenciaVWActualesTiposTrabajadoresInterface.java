package InterfacePersistencia;

import Entidades.Empleados;
import Entidades.VWActualesTiposTrabajadores;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaVWActualesTiposTrabajadoresInterface {

    public VWActualesTiposTrabajadores buscarTipoTrabajador(BigInteger secuencia);
    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo);
    public List<VWActualesTiposTrabajadores> busquedaRapidaTrabajadores();
    public boolean verificarTipoTrabajador(Empleados empleado);
    public List<VWActualesTiposTrabajadores> tipoTrabajadorEmpleado();
}

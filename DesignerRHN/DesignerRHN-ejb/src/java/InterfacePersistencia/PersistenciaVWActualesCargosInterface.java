package InterfacePersistencia;

import Entidades.VWActualesCargos;
import java.math.BigInteger;

public interface PersistenciaVWActualesCargosInterface {
    
    public VWActualesCargos buscarCargoEmpleado (BigInteger secuencia);
    
}

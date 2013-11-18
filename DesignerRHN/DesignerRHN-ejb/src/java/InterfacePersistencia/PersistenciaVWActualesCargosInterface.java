package InterfacePersistencia;

import Entidades.VWActualesCargos;
import java.math.BigInteger;
import javax.persistence.EntityManager;

public interface PersistenciaVWActualesCargosInterface {
    
    public VWActualesCargos buscarCargoEmpleado(EntityManager entity, BigInteger secuencia);
    
}

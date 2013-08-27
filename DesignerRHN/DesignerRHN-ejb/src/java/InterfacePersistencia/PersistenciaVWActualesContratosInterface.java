package InterfacePersistencia;

import Entidades.VWActualesContratos;
import java.math.BigInteger;

public interface PersistenciaVWActualesContratosInterface {

    public VWActualesContratos buscarContrato(BigInteger secuencia);
}

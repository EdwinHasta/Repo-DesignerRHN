package InterfacePersistencia;

import Entidades.VWActualesNormasEmpleados;
import java.math.BigInteger;

public interface PersistenciaVWActualesNormasEmpleadosInterface {

    public VWActualesNormasEmpleados buscarNormaLaboral(BigInteger secuencia);
}

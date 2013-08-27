package InterfacePersistencia;

import Entidades.VWActualesPensiones;
import java.math.BigDecimal;
import java.math.BigInteger;

public interface PersistenciaVWActualesPensionesInterface {

    public BigDecimal buscarSueldoPensionado(BigInteger secuencia);
}

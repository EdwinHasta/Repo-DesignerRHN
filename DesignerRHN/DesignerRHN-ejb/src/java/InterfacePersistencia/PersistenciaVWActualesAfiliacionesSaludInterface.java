package InterfacePersistencia;

import Entidades.VWActualesAfiliacionesSalud;
import java.math.BigInteger;

public interface PersistenciaVWActualesAfiliacionesSaludInterface {
        public VWActualesAfiliacionesSalud buscarAfiliacionSalud(BigInteger secuencia);
}

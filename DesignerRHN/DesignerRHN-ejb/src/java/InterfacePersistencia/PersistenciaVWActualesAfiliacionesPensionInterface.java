package InterfacePersistencia;

import Entidades.VWActualesAfiliacionesPension;
import java.math.BigInteger;

public interface PersistenciaVWActualesAfiliacionesPensionInterface {
    
        public VWActualesAfiliacionesPension buscarAfiliacionPension(BigInteger secuencia);

}

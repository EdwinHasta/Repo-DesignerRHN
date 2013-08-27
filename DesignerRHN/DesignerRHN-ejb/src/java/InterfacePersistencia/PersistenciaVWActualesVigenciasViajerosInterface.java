package InterfacePersistencia;

import Entidades.VWActualesVigenciasViajeros;
import java.math.BigInteger;


public interface PersistenciaVWActualesVigenciasViajerosInterface {
    
        public VWActualesVigenciasViajeros buscarTipoViajero(BigInteger secuencia);
}

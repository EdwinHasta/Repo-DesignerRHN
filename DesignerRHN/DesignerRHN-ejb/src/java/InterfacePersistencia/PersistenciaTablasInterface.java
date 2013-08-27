package InterfacePersistencia;

import Entidades.Tablas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author -Felipphe-
 */
public interface PersistenciaTablasInterface {
    
        public List<Tablas> buscarTablas(BigInteger secuenciaMod);

}

package InterfacePersistencia;

import Entidades.EstructurasFormulas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaEstructurasFormulasInterface {

    public List<EstructurasFormulas> estructurasFormulasParaHistoriaFormula(BigInteger secuencia);

}

package InterfacePersistencia;

import Entidades.EstructurasFormulas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author PROYECTO01
 */
public interface PersistenciaEstructurasFormulasInterface {

    /**
     * Método encargado de buscar el Evento con la secuencia dada por parámetro.
     * @param secuencia Secuencia de la HistoriaFormula a la cual se desea obtener sus EstructurasFormulas
     * @return Lista de EstructurasFormulas referenciados para la HistoriaFormula
     */
    public List<EstructurasFormulas> estructurasFormulasParaHistoriaFormula(BigInteger secuencia);

}

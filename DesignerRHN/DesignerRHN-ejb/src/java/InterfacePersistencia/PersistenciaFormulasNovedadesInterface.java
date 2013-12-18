/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.FormulasNovedades;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'FormulasNovedades' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaFormulasNovedadesInterface {

    /**
     * Método encargado de verfificar si una fórmula existe en la tabla
     * FormulasNovedades de la base de datos.
     *
     * @param secFormula Secuencia de la fórmula a verificar.
     * @return Retorna true si la formula existe en la tabla FormulasNovedades
     * de la base de datos.
     */
    public boolean verificarExistenciaFormulasNovedades(BigInteger secFormula);

    public void crear(FormulasNovedades formulasNovedades);

    public void editar(FormulasNovedades formulasNovedades);

    public void borrar(FormulasNovedades formulasNovedades);

    public List<FormulasNovedades> formulasNovedadesParaFormulaSecuencia(BigInteger secuencia);
}

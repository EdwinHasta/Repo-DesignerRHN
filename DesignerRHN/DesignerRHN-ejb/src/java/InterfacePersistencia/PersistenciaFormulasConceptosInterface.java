/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.FormulasConceptos;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaFormulasConceptosInterface {
    /**
     * Método encargado de verificar si existe al menos una FormulaConcepto asociada a un concepto especifico.
     * @param secConcepto Secuencia del Concepto al que se le realiza la verificación.
     * @return Retorna true si hay una o más formulas asociadas al concepto cuya secuencia es igual a la que entra como parámetro.
     */
    public boolean verificarExistenciaConceptoFormulasConcepto(BigInteger secConcepto);
    /**
     * Método encargado de buscar todas las FormulasConceptos de un concepto especifico.
     * @param secConcepto Secuencia del concepto asociado a las FormulasConceptos.
     * @return Retorna una lista de todas las FormulasConceptos de un concepto.
     */
    public List<FormulasConceptos> formulasConcepto(BigInteger secConcepto); 
    /**
     * Método encargado de verificar que una FormulaConcepto esté asociada con un concepto al momento de hace el cargue de archivos.
     * @param secConcepto Secuencia del concepto 
     * @param secFormula Secuencia de la FormulaConcepto
     * @return Retorna true si existe una relación entre la fórmula y el concepto
     */
    public boolean verificarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula);
}

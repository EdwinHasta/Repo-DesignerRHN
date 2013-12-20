/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.FormulasConceptos;
import java.math.BigInteger;
import java.util.List;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'FormulasConceptos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaFormulasConceptosInterface {
    /**
     * Método encargado de insertar una FormulaConcepto en la base de datos.
     * @param conceptos FormulaConcepto que se quiere crear.
     */
    public void crear(FormulasConceptos conceptos);
    /**
     * Método encargado de modificar una FormulaConcepto de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param conceptos FormulaConcepto con los cambios que se van a realizar.
     */
    public void editar(FormulasConceptos conceptos);
    /**
     * Método encargado de eliminar de la base de datos la FormulaConcepto que entra por parámetro.
     * @param conceptos FormulaConcepto que se quiere eliminar.
     */
    public void borrar(FormulasConceptos conceptos);
    /**
     * Método encargado de buscar todas las FormulasConceptos existentes en la base de datos.
     * @return Retorna una lista de FormulasConceptos.
     */
    public List<FormulasConceptos> buscarFormulasConceptos();
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
    
    public Long comportamientoConceptoAutomaticoSecuenciaConcepto(BigInteger secConcepto);
    
    public Long comportamientoConceptoSemiAutomaticoSecuenciaConcepto(BigInteger secConcepto);
    
    public List<FormulasConceptos> formulasConceptosParaFormulaSecuencia(BigInteger secuenciaF);
}

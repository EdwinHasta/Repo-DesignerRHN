/**
 * Documentación a cargo de AndresPineda
 */
package InterfacePersistencia;

import Entidades.TSFormulasConceptos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'TSFormulasConceptos' de la base de datos.
 *
 * @author AndresPineda
 */
public interface PersistenciaTSFormulasConceptosInterface {

    /**
     * Método encargado de insertar un TSFormulaConcepto en la base de datos.
     *
     * @param tSFormulasConceptos TSFormulaConcepto que se quiere crear.
     */
    public void crear(TSFormulasConceptos tSFormulasConceptos);

    /**
     * Método encargado de modificar un TSFormulaConcepto de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con
     * la información de la base de datos.
     *
     * @param tSFormulasConceptos TSFormulaConcepto con los cambios que se van a
     * realizar.
     */
    public void editar(TSFormulasConceptos tSFormulasConceptos);

    /**
     * Método encargado de eliminar de la base de datos el TSFormulaConcepto que
     * entra por parámetro.
     *
     * @param tSFormulasConceptos TSFormulaConcepto que se quiere eliminar.
     */
    public void borrar(TSFormulasConceptos tSFormulasConceptos);

    /**
     * Método encargado de buscar todos los TSFormulasConceptos existentes en la
     * base de datos.
     *
     * @return Retorna una lista de TSFormulasConceptos.
     */
    public List<TSFormulasConceptos> buscarTSFormulasConceptos();

    /**
     * Método encargado de buscar el TSFormulaConcepto con la secuencia dada por
     * parámetro.
     *
     * @param secTSFormula Secuencia del TSFormulaConcepto que se quiere
     * encontrar.
     * @return Retorna el TSFormulaConcepto identificado con la secuencia dada
     * por parámetro.
     */
    public TSFormulasConceptos buscarTSFormulaConceptoSecuencia(BigInteger secTSFormula);

    /**
     * Método encargado de buscar todos los TSFormulasConceptos existentes en la
     * base de datos para un secTipoSueldo dado por parametro.
     *
     * @param secTipoSueldo Secuencia del secTipoSueldo.
     * @return Retorna una lista de TSFormulasConceptos para el
     * TSGrupoTipoEntidad dado por parametro.
     */
    public List<TSFormulasConceptos> buscarTSFormulasConceptosPorSecuenciaTipoSueldo(BigInteger secTipoSueldo);

}

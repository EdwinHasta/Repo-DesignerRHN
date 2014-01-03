/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Formulascontratos;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'FormulasContratos' 
 * de la base de datos.
 * @author Andres Pineda.
 */
public interface PersistenciaFormulasContratosInterface {
    /**
     * Método encargado de insertar una FormulaContrato en la base de datos.
     * @param formulascontratos FormulaContrato que se quiere crear.
     */
    public void crear(Formulascontratos formulascontratos);
    /**
     * Método encargado de modificar una FormulaContrato de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param formulascontratos FormulaContrato con los cambios que se van a realizar.
     */
    public void editar(Formulascontratos formulascontratos);
    /**
     * Método encargado de eliminar de la base de datos la FormulaContrato que entra por parámetro.
     * @param formulascontratos FormulaContrato que se quiere eliminar.
     */
    public void borrar(Formulascontratos formulascontratos);
    /**
     * Método encargado de buscar las FormulasContratos asociadas a una Fórmula específica.
     * @param secuencia Secuencia de la fórmula.
     * @return Retorna una lista de FormulasContratos cuya fórmula tiene como secuencia el valor dado por parámetro.
     */
    public List<Formulascontratos> formulasContratosParaFormulaSecuencia(BigInteger secuencia);

}

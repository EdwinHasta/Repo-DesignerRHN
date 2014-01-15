/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Contratos;
import Entidades.Formulas;
import Entidades.Formulascontratos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'DetalleLegislacion'.
 * @author Andres Pineda.
 */
public interface AdministrarDetalleLegislacionInterface {
    /**
     * Método encargado de recuperar los Terceros necesarios para la lista de valores.
     * @return Retorna una lista de Terceros.
     */
    public List<Terceros> lovTerceros();
    /**
     * Método encargado de recuperar los Periodicidades necesarios para la lista de valores.
     * @return Retorna una lista de Periodicidades.
     */
    public List<Periodicidades> lovPeriodicidades();
    /**
     * Método encargado de recuperar los Formulas necesarios para la lista de valores.
     * @return Retorna una lista de Formulas.
     */
    public List<Formulas> lovFormulas();
    /**
     * Metodo encargado de recuperar la FormulasContratos según el Contrato que tengan asociado.
     * @param secuencia Secuencia del contrato por el cual se quiere filtrar.
     * @return Retorna una lista de Formulascontratos.
     */
    public List<Formulascontratos> listaFormulasContratosParaContrato(BigInteger secuencia);
    /**
     * Método encargado de crear Formulascontratos.
     * @param listaFormulascontratos Lista de las Formulascontratos que se van a crear.
     */
    public void crearFormulaContrato(List<Formulascontratos> listaFormulascontratos);
    /**
     * Método encargado de editar Formulascontratos.
     * @param listaFormulascontratos Lista de las Formulascontratos que se van a modificar.
     */
    public void modificarFormulaContrato(List<Formulascontratos> listaFormulascontratos);
    /**
     * Método encargado de borrar Formulascontratos.
     * @param listaFormulascontratos Lista de las Formulascontratos que se van a eliminar.
     */
    public void borrarFormulaContrato(List<Formulascontratos> listaFormulascontratos);
    /**
     * Método encargado de recuperar un Contrato dada su secuencia.
     * @param secContrato Secuencia del Contrato.
     * @return Retorna el Contrato cuya secuencia coincida con el valor del parámetro. 
     */
    public Contratos mostrarContrato(BigInteger secContrato);
}

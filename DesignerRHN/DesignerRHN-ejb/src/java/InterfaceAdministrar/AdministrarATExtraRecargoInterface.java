/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.Contratos;
import Entidades.DetallesExtrasRecargos;
import Entidades.ExtrasRecargos;
import Entidades.TiposDias;
import Entidades.TiposJornadas;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'ATExtraRecargo'. 
 * @author Andres Pineda
 */
public interface AdministrarATExtraRecargoInterface {
    /**
     * Método encargado de crear ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a crear.
     */
    public void crearExtrasRecargos(List<ExtrasRecargos> listaER);
    /**
     * Método encargado de editar ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a modificar.
     */
    public void editarExtrasRecargos(List<ExtrasRecargos> listaER);
    /**
     * Método encargado de borrar ExtrasRecargos.
     * @param listaER Lista de los ExtrasRecargos que se van a eliminar.
     */
    public void borrarExtrasRecargos(List<ExtrasRecargos> listaER);
    /**
     * Método encargado de recuperar todos los ExtrasRecargos.
     * @return Retorna una lista de ExtrasRecargos.
     */
    public List<ExtrasRecargos> listExtrasRecargos();
    /**
     * Método encargado de crear DetallesExtrasRecargos.
     * @param listaDER Lista de los DetallesExtrasRecargos que se van a crear.
     */
    public void crearDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);
    /**
     * Método encargado de editar DetallesExtrasRecargos.
     * @param listaDER Lista de los DetallesExtrasRecargos que se van a modificar.
     */
    public void editarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);
    /**
     * Método encargado de borrar DetallesExtrasRecargos.
     * @param listaDER Lista de los DetallesExtrasRecargos que se van a eliminar.
     */
    public void borrarDetallesExtrasRecargos(List<DetallesExtrasRecargos> listaDER);
    /**
     * Método encargado de recuperar los DetallesExtrasRecargos para la tabla de 'Detalles'. 
     * @param secuencia Secuencia del ExtraCargo.
     * @return Retorna una lista de DetallesExtrasRecargos.
     */
    public List<DetallesExtrasRecargos> listDetallesExtrasRecargos(BigInteger secuencia);
    /**
     * Método encargado de recuperar los TiposDias para la lista de valores.
     * @return Retorna una lista de TiposDias.
     */
    public List<TiposDias> listTiposDias();
    /**
     * Método encargado de recuperar los TiposJornadas para la lista de valores.
     * @return Retorna una lista de TiposJornadas.
     */
    public List<TiposJornadas> listTiposJornadas();
    /**
     * Método encargado de recuperar los Contratos para la lista de valores.
     * @return Retorna una lista de Contratos.
     */
    public List<Contratos> listContratos();
    /**
     * Método encargado de recuperar los Conceptos para la lista de valores.
     * @return Retorna una lista de Conceptos.
     */
    public List<Conceptos> listConceptos();
}

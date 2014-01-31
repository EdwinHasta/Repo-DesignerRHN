/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Periodicidades;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'Periodicidades' de la base de datos.
 *
 * @author betelgeuse
 */
public interface PersistenciaPeriodicidadesInterface {

    /**
     * Método encargado de insertar una Periodicidad en la base de datos.
     *
     * @param periodicidades Periodicidad que se quiere crear.
     */
    public void crear(Periodicidades periodicidades);

    /**
     * Método encargado de modificar una Periodicidad de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param periodicidades Periodicidad con los cambios que se van a realizar.
     */
    public void editar(Periodicidades periodicidades);

    /**
     * Método encargado de eliminar de la base de datos la Periodicidad que
     * entra por parámetro.
     *
     * @param periodicidades Periodicidad que se quiere eliminar.
     */
    public void borrar(Periodicidades periodicidades);

    /**
     * Método encargado de buscar la Periodicidad con la secPeriodicidades dada
     * por parámetro.
     *
     * @param secPeriodicidades Secuencia de la Periodicidad que se quiere
     * encontrar.
     * @return Retorna la Periodicidad identificada con la secPeriodicidades
     * dada por parámetro.
     */
    public Periodicidades consultarPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Método encargado de buscar todas las Periodicidades existentes en la base
     * de datos.
     *
     * @return Retorna una lista de Periodicidades.
     */
    public List<Periodicidades> consultarPeriodicidades();

    /**
     * Método encargado de verificar si hay al menos una Periodicidad con el
     * código dado como parámetro.
     *
     * @param codigoPeriodicidad Código de la Periodicidad
     * @return Retorna true si existe alguna Periodicidad con el código dado por
     * parámetro.
     */
    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad);

    /**
     * Metodo encargado de contar cuantas CPCompromisos estan relacionadas con
     * la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas CPCompromisos tienen relacion con la secPeriodicidades
     * recibida
     */
    public BigInteger contarCPCompromisosPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas DetallesPeriodicidades estan
     * relacionadas con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas DetallesPeriodicidades tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarDetallesPeriodicidadesPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas EersPrestamosDtos estan relacionadas
     * con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas EersPrestamosDtos tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarEersPrestamosDtosPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas Empresas estan relacionadas con la
     * secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas Empresas tienen relacion con la secPeriodicidades
     * recibida
     */
    public BigInteger contarEmpresasPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas FormulasAseguradas estan relacionadas
     * con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas FormulasAseguradas tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarFormulasAseguradasPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas FormulasContratos estan relacionadas
     * con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas FormulasContratos tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarFormulasContratosPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas GruposProvisiones estan relacionadas
     * con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas GruposProvisiones tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarGruposProvisionesPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas Novedad estan relacionadas con la
     * secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas Novedad tienen relacion con la secPeriodicidades recibida
     */
    public BigInteger contarNovedadPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas ParametrosCambiosMasivos estan
     * relacionadas con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas ParametrosCambiosMasivos tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarParametrosCambiosMasivosPeriodicidad(BigInteger secPeriodicidades);

    /**
     * Metodo encargado de contar cuantas VigenciasFormasPagos estan
     * relacionadas con la secPeriodicidades de la Periodicidad
     *
     * @param secPeriodicidades Secuencia de la Periodicidad
     * @return Cuantas VigenciasFormasPagos tienen relacion con la
     * secPeriodicidades recibida
     */
    public BigInteger contarVigenciasFormasPagosPeriodicidad(BigInteger secPeriodicidades);
}

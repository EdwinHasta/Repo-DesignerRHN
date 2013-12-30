/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.CentrosCostos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la
 * tabla 'CentrosCostos' de la base de datos.
 *
 * @author Hugo David Sin Gutiérrez
 */
public interface PersistenciaCentrosCostosInterface {

    /**
     * Método encargado de insertar un CentroCosto en la base de datos.
     *
     * @param centrosCostos CentroCosto que se quiere crear.
     */
    public void crear(CentrosCostos centrosCostos);

    /**
     * Método encargado de modificar un CentroCosto de la base de datos. Este
     * método recibe la información del parámetro para hacer un 'merge' con la
     * información de la base de datos.
     *
     * @param centrosCostos CentroCosto con los cambios que se van a realizar.
     */
    public void editar(CentrosCostos centrosCostos);

    /**
     * Método encargado de eliminar de la base de datos el CentroCosto que entra
     * por parámetro.
     *
     * @param centrosCostos CentroCosto que se quiere eliminar.
     */
    public void borrar(CentrosCostos centrosCostos);

    /**
     * Método encargado de buscar todos los CentrosCostos existentes en la base
     * de datos.
     *
     * @return Retorna una lista de Centros Costos.
     */
    public List<CentrosCostos> buscarCentrosCostos();

    /**
     * Método encargado de buscar el CentroCosto con la secuencia dado por
     * parámetro.
     *
     * @param secuencia Secuencia del CentroCosto que se quiere encontrar.
     * @return Retorna el CentroCosto identificado con la secuencia dada por
     * parámetro.
     */
    public CentrosCostos buscarCentroCostoSecuencia(BigInteger secuencia);

    /**
     * Método encargado de buscar los CentroCosto de una empresa especifica.
     *
     * @param secEmpresa Identificador único de la empresa a la cual pertenecen
     * los centrosCostos.
     * @return Retorna una lista de CentrosCostos que pertenecen a la empresa
     * con secuencia igual a la pasada por parametro.
     */
    public List<CentrosCostos> buscarCentrosCostosEmpr(BigInteger secEmpresa);

    /**
     * Método encargado de
     *
     * @param secEmpresa
     * @return
     */
    public long contadorSecuenciaEmpresa(BigInteger secEmpresa);

    public BigDecimal contadorComprobantesContables(BigInteger secuencia);

    public BigDecimal contadorDetallesCCConsolidador(BigInteger secuencia);

    public BigDecimal contadorDetallesCCDetalle(BigInteger secuencia);

    public BigDecimal contadorEmpresas(BigInteger secuencia);

    public BigDecimal contadorEstructuras(BigInteger secuencia);

    public BigDecimal contadorInterconCondor(BigInteger secuencia);

    public BigDecimal contadorInterconDynamics(BigInteger secuencia);

    public BigDecimal contadorInterconGeneral(BigInteger secuencia);

    public BigDecimal contadorInterconHelisa(BigInteger secuencia);

    public BigDecimal contadorInterconSapbo(BigInteger secuencia);

    public BigDecimal contadorInterconSiigo(BigInteger secuencia);

    public BigDecimal contadorInterconTotal(BigInteger secuencia);

    public BigDecimal contadorNovedadesC(BigInteger secuencia);

    public BigDecimal contadorNovedadesD(BigInteger secuencia);

    public BigDecimal contadorProcesosProductivos(BigInteger secuencia);

    public BigDecimal contadorProyecciones(BigInteger secuencia);

    public BigDecimal contadorSolucionesNodosC(BigInteger secuencia);

    public BigDecimal contadorSolucionesNodosD(BigInteger secuencia);

    public BigDecimal contadorSoPanoramas(BigInteger secuencia);

    public BigDecimal contadorTerceros(BigInteger secuencia);

    public BigDecimal contadorUnidadesRegistradas(BigInteger secuencia);

    public BigDecimal contadorVigenciasCuentasC(BigInteger secuencia);

    public BigDecimal contadorVigenciasCuentasD(BigInteger secuencia);

    public BigDecimal contadorVigenciasProrrateos(BigInteger secuencia);
}

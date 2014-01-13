/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.CentrosCostos;
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
     * Método encargado de buscar el CentroCosto con la secuencia dada por
     * parámetro.
     *
     * @param secuencia Secuencia del CentroCosto que se quiere encontrar.
     * @return Retorna el CentroCosto identificado con la secuencia dada por
     * parámetro.
     */
    public CentrosCostos buscarCentroCostoSecuencia(BigInteger secuencia);

    /**
     * Método encargado de buscar los CentroCosto de una empresa específica.
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

    public BigInteger contadorComprobantesContables(BigInteger secuencia);

    public BigInteger contadorDetallesCCConsolidador(BigInteger secuencia);

    public BigInteger contadorDetallesCCDetalle(BigInteger secuencia);

    public BigInteger contadorEmpresas(BigInteger secuencia);

    public BigInteger contadorEstructuras(BigInteger secuencia);

    public BigInteger contadorInterconCondor(BigInteger secuencia);

    public BigInteger contadorInterconDynamics(BigInteger secuencia);

    public BigInteger contadorInterconGeneral(BigInteger secuencia);

    public BigInteger contadorInterconHelisa(BigInteger secuencia);

    public BigInteger contadorInterconSapbo(BigInteger secuencia);

    public BigInteger contadorInterconSiigo(BigInteger secuencia);

    public BigInteger contadorInterconTotal(BigInteger secuencia);

    public BigInteger contadorNovedadesC(BigInteger secuencia);

    public BigInteger contadorNovedadesD(BigInteger secuencia);

    public BigInteger contadorProcesosProductivos(BigInteger secuencia);

    public BigInteger contadorProyecciones(BigInteger secuencia);

    public BigInteger contadorSolucionesNodosC(BigInteger secuencia);

    public BigInteger contadorSolucionesNodosD(BigInteger secuencia);

    public BigInteger contadorSoPanoramas(BigInteger secuencia);

    public BigInteger contadorTerceros(BigInteger secuencia);

    public BigInteger contadorUnidadesRegistradas(BigInteger secuencia);

    public BigInteger contadorVigenciasCuentasC(BigInteger secuencia);

    public BigInteger contadorVigenciasCuentasD(BigInteger secuencia);

    public BigInteger contadorVigenciasProrrateos(BigInteger secuencia);
}

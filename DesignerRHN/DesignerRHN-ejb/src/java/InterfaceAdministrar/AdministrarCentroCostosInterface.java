/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarCentroCostosInterface {

    public List<Empresas> buscarEmpresas();
    //************************************CENTROS COSTROS**************************************

    public void modificarCentroCostos(CentrosCostos centrosCostos);

    /**
     *
     * @param centrosCostos
     */
    public void borrarCentroCostos(CentrosCostos centrosCostos);

    /**
     *
     * @param centrosCostos
     */
    public void crearCentroCostos(CentrosCostos centrosCostos);

    /**
     *
     * @param secEmpresa
     * @return
     */
    public List<CentrosCostos> buscarCentrosCostosPorEmpresa(BigInteger secEmpresa);

    /**
     * ************************************TiposCentrosCosto********************
     * /**
     *
     * @return
     */
    public List<TiposCentrosCostos> buscarTiposCentrosCostos();

    /**
     *
     * @param secEmpresa
     * @return
     */
    public Long contadorSecueniasEmpresas(BigInteger secEmpresa);

    public BigDecimal contadorComprobantesContables(BigInteger secCentroCosto);

    public BigDecimal contadorDetallesCCConsolidador(BigInteger secCentroCosto);

    public BigDecimal contadorDetalleContable(BigInteger secCentroCosto);

    public BigDecimal contadorEmpresas(BigInteger secCentroCosto);

    public BigDecimal contadorEstructuras(BigInteger secCentroCosto);

    public BigDecimal contadorInterconCondor(BigInteger secCentroCosto);

    public BigDecimal contadorInterconDynamics(BigInteger secCentroCosto);

    public BigDecimal contadorInterconGeneral(BigInteger secCentroCosto);

    public BigDecimal contadorInterconHelisa(BigInteger secCentroCosto);

    public BigDecimal contadorInterconSapbo(BigInteger secCentroCosto);

    public BigDecimal contadorInterconSiigo(BigInteger secCentroCosto);

    public BigDecimal contadorInterconTotal(BigInteger secCentroCosto);

    public BigDecimal contadorNovedadesD(BigInteger secCentroCosto);

    public BigDecimal contadorNovedadesC(BigInteger secCentroCosto);

    public BigDecimal contadorProcesosProductivos(BigInteger secCentroCosto);

    public BigDecimal contadorProyecciones(BigInteger secCentroCosto);

    public BigDecimal contadorSolucionesNodosC(BigInteger secCentroCosto);

    public BigDecimal contadorSolucionesNodosD(BigInteger secCentroCosto);

    public BigDecimal contadorSoPanoramas(BigInteger secCentroCosto);

    public BigDecimal contadorTerceros(BigInteger secCentroCosto);

    public BigDecimal contadorUnidadesRegistradas(BigInteger secCentroCosto);

    public BigDecimal contadorVigenciasCuentasC(BigInteger secCentroCosto);

    public BigDecimal contadorVigenciasCuentasD(BigInteger secCentroCosto);

    public BigDecimal contadorVigenciasProrrateos(BigInteger secCentroCosto);
}

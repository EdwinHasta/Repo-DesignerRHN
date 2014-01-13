/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
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

    public BigInteger contadorComprobantesContables(BigInteger secCentroCosto);

    public BigInteger contadorDetallesCCConsolidador(BigInteger secCentroCosto);

    public BigInteger contadorDetalleContable(BigInteger secCentroCosto);

    public BigInteger contadorEmpresas(BigInteger secCentroCosto);

    public BigInteger contadorEstructuras(BigInteger secCentroCosto);

    public BigInteger contadorInterconCondor(BigInteger secCentroCosto);

    public BigInteger contadorInterconDynamics(BigInteger secCentroCosto);

    public BigInteger contadorInterconGeneral(BigInteger secCentroCosto);

    public BigInteger contadorInterconHelisa(BigInteger secCentroCosto);

    public BigInteger contadorInterconSapbo(BigInteger secCentroCosto);

    public BigInteger contadorInterconSiigo(BigInteger secCentroCosto);

    public BigInteger contadorInterconTotal(BigInteger secCentroCosto);

    public BigInteger contadorNovedadesD(BigInteger secCentroCosto);

    public BigInteger contadorNovedadesC(BigInteger secCentroCosto);

    public BigInteger contadorProcesosProductivos(BigInteger secCentroCosto);

    public BigInteger contadorProyecciones(BigInteger secCentroCosto);

    public BigInteger contadorSolucionesNodosC(BigInteger secCentroCosto);

    public BigInteger contadorSolucionesNodosD(BigInteger secCentroCosto);

    public BigInteger contadorSoPanoramas(BigInteger secCentroCosto);

    public BigInteger contadorTerceros(BigInteger secCentroCosto);

    public BigInteger contadorUnidadesRegistradas(BigInteger secCentroCosto);

    public BigInteger contadorVigenciasCuentasC(BigInteger secCentroCosto);

    public BigInteger contadorVigenciasCuentasD(BigInteger secCentroCosto);

    public BigInteger contadorVigenciasProrrateos(BigInteger secCentroCosto);

}

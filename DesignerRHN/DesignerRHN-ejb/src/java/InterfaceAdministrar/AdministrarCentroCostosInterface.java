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
    public long contadorSecueniasEmpresas(BigInteger secEmpresa);
}

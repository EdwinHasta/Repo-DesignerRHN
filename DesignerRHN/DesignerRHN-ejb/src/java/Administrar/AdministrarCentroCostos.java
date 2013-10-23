/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
import InterfaceAdministrar.AdministrarCentroCostosInterface;
import InterfacePersistencia.PersistenciaCentrosCostosInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaTiposCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
@LocalBean
public class AdministrarCentroCostos implements AdministrarCentroCostosInterface {

    /**
     * CREACION EJB
     */
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    @EJB
    PersistenciaTiposCentrosCostosInterface persistenciaTiposCentrosCostos;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    /**
     *
     */
    private List<CentrosCostos> listaCentrosCostos;
    private CentrosCostos centrosCostos;
    private List<TiposCentrosCostos> listaTiposCentrosCostos;
    private TiposCentrosCostos tiposCentrosCostos;
    private List<Empresas> listaEmpresas;
    private Empresas empresas;
    private long contadorEmpresas;
    //************************************EMPRESAS**************************************

    /**
     * METODO QUE SE ENCARGA DE TRAER LAS EMPRESAS
     *
     * @return
     */
    @Override
    public List<Empresas> buscarEmpresas() {
        try {
            return listaEmpresas = persistenciaEmpresas.buscarEmpresas();
        } catch (Exception e) {
            System.out.println("AdministrarCentroCostos: Falló al buscar las empresas /n" + e.getMessage());
            return listaEmpresas = null;
        }
    }
    //************************************CENTROS COSTROS**************************************

    @Override
    public void modificarCentroCostos(CentrosCostos centrosCostos) {
        try {
            persistenciaCentrosCostos.editar(centrosCostos);
        } catch (Exception e) {
            System.out.println("AdministrarCentrosCostos: Falló al editar el CentroCosto /n" + e.getMessage());
        }
    }

    /**
     *
     * @param centrosCostos
     */
    @Override
    public void borrarCentroCostos(CentrosCostos centrosCostos) {
        System.out.println("ENTRE A AdministrarCentroCostos.borrarCentroCostos ");
        try {
            System.out.println("AdministrarCentroCostos.borrarCentroCostos(CentrosCostos centrosCostos=====" + centrosCostos.getNombre());
            persistenciaCentrosCostos.borrar(centrosCostos);
        } catch (Exception e) {
            System.out.println("ERROR AdministrarCentroCostos.borrarCentroCostos ERROR=====" + e.getMessage());
        }
    }

    /**
     *
     * @param centrosCostos
     */
    @Override
    public void crearCentroCostos(CentrosCostos centrosCostos) {
        System.out.println("ENTRE A AdministrarCentroCostos.crearCentroCostos ");
        try {
            System.out.println("ENTRE A AdministrarCentroCostos.crearCentroCostos EN EL TRY");
            persistenciaCentrosCostos.crear(centrosCostos);
        } catch (Exception e) {
            System.out.println("ERROR AdministrarCentroCostos.crearCentroCostos ERROR======" + e.getMessage());
        }
    }

    /**
     *
     * @param secEmpresa
     * @return
     */
    @Override
    public List<CentrosCostos> buscarCentrosCostosPorEmpresa(BigInteger secEmpresa) {
        try {
            System.out.println("ENTRE A AdministrarCentroCostos.buscarCentrosCostosPorEmpresa ");
            listaCentrosCostos = persistenciaCentrosCostos.buscarCentrosCostosEmpr(secEmpresa);
        } catch (Exception e) {
            System.out.println("Error en Administrar CentrosCostos (centrosCostosPorEmpresa)");
            listaCentrosCostos = null;
        } finally {
            return listaCentrosCostos;
        }
    }

    @Override
    public List<TiposCentrosCostos> buscarTiposCentrosCostos() {
        try {
            return listaTiposCentrosCostos = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
        } catch (Exception e) {
            System.out.println("\n AdministrarCentroCostos error en buscarTiposCentroCostos \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public long contadorSecueniasEmpresas(BigInteger secEmpresa) {

        try {
            contadorEmpresas = persistenciaCentrosCostos.contadorSecuenciaEmpresa(secEmpresa);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSecuenciasEmpresas ERROR===" + e.getMessage());
            contadorEmpresas = -1;
        } finally {
            return contadorEmpresas;
        }
    }
}

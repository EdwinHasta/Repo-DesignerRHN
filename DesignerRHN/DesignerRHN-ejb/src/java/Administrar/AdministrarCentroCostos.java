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
import java.math.BigDecimal;
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

    //-------------------------------------------------------------------------------------
    private BigDecimal contadorComprobantesContables;
    private BigDecimal contadorDetallesCCConsolidador;
    private BigDecimal contadorEmpresasV;
    private BigDecimal contadorEstructuras;
    private BigDecimal contadorDetallesCCDetalle;
    private BigDecimal contadorInterconCondor;
    private BigDecimal contadorInterconDynamics;
    private BigDecimal contadorInterconGeneral;
    private BigDecimal contadorInterconHelisa;
    private BigDecimal contadorInterconSapbo;
    private BigDecimal contadorInterconSiigo;
    private BigDecimal contadorInterconTotal;
    private BigDecimal contadorNovedadesD;
    private BigDecimal contadorNovedadesC;
    private BigDecimal contadorProcesosProductivos;
    private BigDecimal contadorProyecciones;
    private BigDecimal contadorSolucionesNodosC;
    private BigDecimal contadorSolucionesNodosD;
    private BigDecimal contadorSoPanoramas;
    private BigDecimal contadorTerceros;
    private BigDecimal contadorUnidadesRegistradas;
    private BigDecimal contadorVigenciasCuentasC;
    private BigDecimal contadorVigenciasCuentasD;
    private BigDecimal contadorVigenciasProrrateos;
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

    public BigDecimal contadorComprobantesContables(BigInteger secCentroCosto) {

        try {
            contadorComprobantesContables = persistenciaCentrosCostos.contadorComprobantesContables(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorComprobantesContables ERROR===" + e.getMessage());
        } finally {
            return contadorComprobantesContables;
        }
    }

    public BigDecimal contadorDetallesCCConsolidador(BigInteger secCentroCosto) {

        try {
            contadorDetallesCCConsolidador = persistenciaCentrosCostos.contadorDetallesCCConsolidador(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorDetallesCCConsolidador ERROR===" + e.getMessage());
        } finally {
            return contadorDetallesCCConsolidador;
        }
    }

    public BigDecimal contadorDetalleContable(BigInteger secCentroCosto) {

        try {
            contadorDetallesCCDetalle = persistenciaCentrosCostos.contadorDetallesCCDetalle(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorDetalleContable ERROR===" + e.getMessage());
        } finally {
            return contadorDetallesCCDetalle;
        }
    }

    public BigDecimal contadorEmpresas(BigInteger secCentroCosto) {

        try {
            contadorEmpresasV = persistenciaCentrosCostos.contadorEmpresas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorEmpresas ERROR===" + e.getMessage());
        } finally {
            return contadorEmpresasV;
        }
    }

    public BigDecimal contadorEstructuras(BigInteger secCentroCosto) {

        try {
            contadorEstructuras = persistenciaCentrosCostos.contadorEstructuras(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorEstructuras ERROR===" + e.getMessage());
        } finally {
            return contadorEstructuras;
        }
    }

    public BigDecimal contadorInterconCondor(BigInteger secCentroCosto) {

        try {
            contadorInterconCondor = persistenciaCentrosCostos.contadorInterconCondor(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconCondor ERROR===" + e.getMessage());
        } finally {
            return contadorInterconCondor;
        }
    }

    public BigDecimal contadorInterconDynamics(BigInteger secCentroCosto) {

        try {
            contadorInterconDynamics = persistenciaCentrosCostos.contadorInterconDynamics(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconDynamics ERROR===" + e.getMessage());
        } finally {
            return contadorInterconDynamics;
        }
    }

    public BigDecimal contadorInterconGeneral(BigInteger secCentroCosto) {

        try {
            contadorInterconGeneral = persistenciaCentrosCostos.contadorInterconGeneral(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconGeneral ERROR===" + e.getMessage());
        } finally {
            return contadorInterconGeneral;
        }
    }

    public BigDecimal contadorInterconHelisa(BigInteger secCentroCosto) {

        try {
            contadorInterconHelisa = persistenciaCentrosCostos.contadorInterconHelisa(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconHelisa ERROR===" + e.getMessage());
        } finally {
            return contadorInterconHelisa;
        }
    }

    public BigDecimal contadorInterconSapbo(BigInteger secCentroCosto) {

        try {
            contadorInterconSapbo = persistenciaCentrosCostos.contadorInterconSapbo(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconSapbo ERROR===" + e.getMessage());
        } finally {
            return contadorInterconSapbo;
        }
    }

    public BigDecimal contadorInterconSiigo(BigInteger secCentroCosto) {

        try {
            contadorInterconSiigo = persistenciaCentrosCostos.contadorInterconSiigo(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconSiigo ERROR===" + e.getMessage());
        } finally {
            return contadorInterconSiigo;
        }
    }

    public BigDecimal contadorInterconTotal(BigInteger secCentroCosto) {

        try {
            contadorInterconTotal = persistenciaCentrosCostos.contadorInterconTotal(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconTotal ERROR===" + e.getMessage());
        } finally {
            return contadorInterconTotal;
        }
    }

    public BigDecimal contadorNovedadesD(BigInteger secCentroCosto) {

        try {
            contadorNovedadesD = persistenciaCentrosCostos.contadorNovedadesD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorNovedadesD ERROR===" + e.getMessage());
        } finally {
            return contadorNovedadesD;
        }
    }

    public BigDecimal contadorNovedadesC(BigInteger secCentroCosto) {

        try {
            contadorNovedadesC = persistenciaCentrosCostos.contadorNovedadesC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorNovedadesC ERROR===" + e.getMessage());
        } finally {
            return contadorNovedadesC;
        }
    }

    public BigDecimal contadorProcesosProductivos(BigInteger secCentroCosto) {

        try {
            contadorProcesosProductivos = persistenciaCentrosCostos.contadorProcesosProductivos(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorProcesosProductivos ERROR===" + e.getMessage());
        } finally {
            return contadorProcesosProductivos;
        }
    }

    public BigDecimal contadorProyecciones(BigInteger secCentroCosto) {

        try {
            contadorProyecciones = persistenciaCentrosCostos.contadorProyecciones(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorProyecciones ERROR===" + e.getMessage());
        } finally {
            return contadorProyecciones;
        }
    }

    public BigDecimal contadorSolucionesNodosC(BigInteger secCentroCosto) {

        try {
            contadorSolucionesNodosC = persistenciaCentrosCostos.contadorSolucionesNodosC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSolucionesNodosC ERROR===" + e.getMessage());
        } finally {
            return contadorSolucionesNodosC;
        }
    }

    public BigDecimal contadorSolucionesNodosD(BigInteger secCentroCosto) {

        try {
            contadorSolucionesNodosD = persistenciaCentrosCostos.contadorSolucionesNodosD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSolucionesNodosD ERROR===" + e.getMessage());
        } finally {
            return contadorSolucionesNodosD;
        }
    }

    public BigDecimal contadorSoPanoramas(BigInteger secCentroCosto) {

        try {
            contadorSoPanoramas = persistenciaCentrosCostos.contadorSoPanoramas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSoPanoramas ERROR===" + e.getMessage());
        } finally {
            return contadorSoPanoramas;
        }
    }

    public BigDecimal contadorTerceros(BigInteger secCentroCosto) {

        try {
            contadorTerceros = persistenciaCentrosCostos.contadorTerceros(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorTerceros ERROR===" + e.getMessage());
        } finally {
            return contadorTerceros;
        }
    }

    public BigDecimal contadorUnidadesRegistradas(BigInteger secCentroCosto) {

        try {
            contadorUnidadesRegistradas = persistenciaCentrosCostos.contadorUnidadesRegistradas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorUnidadesRegistradas ERROR===" + e.getMessage());
        } finally {
            return contadorUnidadesRegistradas;
        }
    }

    public BigDecimal contadorVigenciasCuentasC(BigInteger secCentroCosto) {

        try {
            contadorVigenciasCuentasC = persistenciaCentrosCostos.contadorVigenciasCuentasC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasCuentasC ERROR===" + e.getMessage());
        } finally {
            return contadorVigenciasCuentasC;
        }
    }

    public BigDecimal contadorVigenciasCuentasD(BigInteger secCentroCosto) {

        try {
            contadorVigenciasCuentasD = persistenciaCentrosCostos.contadorVigenciasCuentasD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasCuentasD ERROR===" + e.getMessage());
        } finally {
            return contadorVigenciasCuentasD;
        }
    }

    public BigDecimal contadorVigenciasProrrateos(BigInteger secCentroCosto) {

        try {
            contadorVigenciasProrrateos = persistenciaCentrosCostos.contadorVigenciasProrrateos(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasProrrateos ERROR===" + e.getMessage());
        } finally {
            return contadorVigenciasProrrateos;
        }
    }

}

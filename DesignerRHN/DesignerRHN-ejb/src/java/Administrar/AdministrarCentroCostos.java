/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla
 * 'CentroCostos'.
 *
 * @author betelgeuse
 */
@Stateful
@LocalBean
public class AdministrarCentroCostos implements AdministrarCentroCostosInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaCentrosCostos'.
     */
    @EJB
    PersistenciaCentrosCostosInterface persistenciaCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaTiposCentrosCostos'.
     */
    @EJB
    PersistenciaTiposCentrosCostosInterface persistenciaTiposCentrosCostos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

    //-------------------------------------------------------------------------------------
    @Override
    public List<Empresas> buscarEmpresas() {
        try {
            List<Empresas> listaEmpresas = persistenciaEmpresas.buscarEmpresas();
            return listaEmpresas;
        } catch (Exception e) {
            System.out.println("AdministrarCentroCostos: Falló al buscar las empresas /n" + e.getMessage());
            return null;
        }
    }

    @Override
    public void modificarCentroCostos(List<CentrosCostos> listaCentrosCostos) {
        try {
            for (int i = 0; i < listaCentrosCostos.size(); i++) {
                System.out.println("Modificando...");
                persistenciaCentrosCostos.editar(listaCentrosCostos.get(i));
            }
        } catch (Exception e) {
            System.err.println("AdministrarCentrosCostos: Falló al editar el CentroCosto /n" + e.getMessage());
        }
    }

    @Override
    public void borrarCentroCostos(List<CentrosCostos> listaCentrosCostos) {
        System.out.println("ENTRE A AdministrarCentroCostos.borrarCentroCostos ");
        try {
            for (int i = 0; i < listaCentrosCostos.size(); i++) {
                System.out.println("Borrando...");
                persistenciaCentrosCostos.borrar(listaCentrosCostos.get(i));
            }
        } catch (Exception e) {
            System.err.println("ERROR AdministrarCentroCostos.borrarCentroCostos ERROR=====" + e.getMessage());
        }
    }

    @Override
    public void crearCentroCostos(List<CentrosCostos> listaCentrosCostos) {
        System.out.println("ENTRE A AdministrarCentroCostos.crearCentroCostos ");
        try {
            for (int i = 0; i < listaCentrosCostos.size(); i++) {
                System.out.println("Creando...");
                persistenciaCentrosCostos.crear(listaCentrosCostos.get(i));
            }
        } catch (Exception e) {
            System.err.println("ERROR AdministrarCentroCostos.crearCentroCostos ERROR======" + e.getMessage());
        }
    }

    @Override
    public List<CentrosCostos> buscarCentrosCostosPorEmpresa(BigInteger secEmpresa) {
        try {
            System.out.println("ENTRE A AdministrarCentroCostos.buscarCentrosCostosPorEmpresa ");
            List<CentrosCostos> listaCentrosCostos = persistenciaCentrosCostos.buscarCentrosCostosEmpr(secEmpresa);
            return listaCentrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Administrar CentrosCostos (centrosCostosPorEmpresa)");
            return null;
        }
    }

    @Override
    public List<TiposCentrosCostos> lovTiposCentrosCostos() {
        try {
            List<TiposCentrosCostos> listaTiposCentrosCostos = persistenciaTiposCentrosCostos.buscarTiposCentrosCostos();
            return listaTiposCentrosCostos;
        } catch (Exception e) {
            System.out.println("\n AdministrarCentroCostos error en buscarTiposCentroCostos \n" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorComprobantesContables(BigInteger secCentroCosto) {
        BigInteger contadorComprobantesContables;
        try {
            contadorComprobantesContables = persistenciaCentrosCostos.contadorComprobantesContables(secCentroCosto);
            return contadorComprobantesContables;
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorComprobantesContables ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorDetallesCCConsolidador(BigInteger secCentroCosto) {

        try {
            BigInteger contadorDetallesCCConsolidador = persistenciaCentrosCostos.contadorDetallesCCConsolidador(secCentroCosto);
            return contadorDetallesCCConsolidador;
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorDetallesCCConsolidador ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorDetalleContable(BigInteger secCentroCosto) {

        try {
            BigInteger contadorDetallesCCDetalle = persistenciaCentrosCostos.contadorDetallesCCDetalle(secCentroCosto);
            return contadorDetallesCCDetalle;

        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorDetalleContable ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorEmpresas(BigInteger secCentroCosto) {

        try {
            BigInteger contadorEmpresasV;
            return contadorEmpresasV = persistenciaCentrosCostos.contadorEmpresas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorEmpresas ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorEstructuras(BigInteger secCentroCosto) {

        try {
            BigInteger contadorEstructuras;
            return contadorEstructuras = persistenciaCentrosCostos.contadorEstructuras(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorEstructuras ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorInterConCondor(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconCondor;
            return contadorInterconCondor = persistenciaCentrosCostos.contadorInterconCondor(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconCondor ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorInterConDynamics(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconDynamics;
            return contadorInterconDynamics = persistenciaCentrosCostos.contadorInterconDynamics(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconDynamics ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorInterConGeneral(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconGeneral;

            return contadorInterconGeneral = persistenciaCentrosCostos.contadorInterconGeneral(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconGeneral ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorInterConHelisa(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconHelisa;
            return contadorInterconHelisa = persistenciaCentrosCostos.contadorInterconHelisa(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconHelisa ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorInterConSapbo(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconSapbo;
            return contadorInterconSapbo = persistenciaCentrosCostos.contadorInterconSapbo(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconSapbo ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorInterConSiigo(BigInteger secCentroCosto) {

        try {
            BigInteger contadorInterconSiigo;

            return contadorInterconSiigo = persistenciaCentrosCostos.contadorInterconSiigo(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconSiigo ERROR===" + e.getMessage());
            return null;
        }
    }


    @Override
    public BigInteger contadorInterConTotal(BigInteger secCentroCosto) {
        try {
            BigInteger contadorInterconTotal;

            return contadorInterconTotal = persistenciaCentrosCostos.contadorInterconTotal(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorInterconTotal ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorNovedadesD(BigInteger secCentroCosto) {

        try {
            BigInteger contadorNovedadesD;

            return contadorNovedadesD = persistenciaCentrosCostos.contadorNovedadesD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorNovedadesD ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorNovedadesC(BigInteger secCentroCosto) {

        try {
            BigInteger contadorNovedadesC;

            return contadorNovedadesC = persistenciaCentrosCostos.contadorNovedadesC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorNovedadesC ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorProcesosProductivos(BigInteger secCentroCosto) {

        try {
            BigInteger contadorProcesosProductivos;
            return contadorProcesosProductivos = persistenciaCentrosCostos.contadorProcesosProductivos(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorProcesosProductivos ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorProyecciones(BigInteger secCentroCosto) {

        try {
            BigInteger contadorProyecciones;
            return contadorProyecciones = persistenciaCentrosCostos.contadorProyecciones(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorProyecciones ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorSolucionesNodosC(BigInteger secCentroCosto) {

        try {
            BigInteger contadorSolucionesNodosC;
            return contadorSolucionesNodosC = persistenciaCentrosCostos.contadorSolucionesNodosC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSolucionesNodosC ERROR===" + e.getMessage());

            return null;
        }
    }

    @Override
    public BigInteger contadorSolucionesNodosD(BigInteger secCentroCosto) {

        try {
            BigInteger contadorSolucionesNodosD;
            return contadorSolucionesNodosD = persistenciaCentrosCostos.contadorSolucionesNodosD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSolucionesNodosD ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorSoPanoramas(BigInteger secCentroCosto) {

        try {
            BigInteger contadorSoPanoramas;
            return contadorSoPanoramas = persistenciaCentrosCostos.contadorSoPanoramas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorSoPanoramas ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorTerceros(BigInteger secCentroCosto) {

        try {
            BigInteger contadorTerceros;
            return contadorTerceros = persistenciaCentrosCostos.contadorTerceros(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorTerceros ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorUnidadesRegistradas(BigInteger secCentroCosto) {

        try {
            BigInteger contadorUnidadesRegistradas;
            return contadorUnidadesRegistradas = persistenciaCentrosCostos.contadorUnidadesRegistradas(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorUnidadesRegistradas ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorVigenciasCuentasC(BigInteger secCentroCosto) {

        try {
            BigInteger contadorVigenciasCuentasC;
            return contadorVigenciasCuentasC = persistenciaCentrosCostos.contadorVigenciasCuentasC(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasCuentasC ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorVigenciasCuentasD(BigInteger secCentroCosto) {

        try {
            BigInteger contadorVigenciasCuentasD;
            return contadorVigenciasCuentasD = persistenciaCentrosCostos.contadorVigenciasCuentasD(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasCuentasD ERROR===" + e.getMessage());
            return null;
        }
    }

    @Override
    public BigInteger contadorVigenciasProrrateos(BigInteger secCentroCosto) {

        try {
            BigInteger contadorVigenciasProrrateos;
            return contadorVigenciasProrrateos = persistenciaCentrosCostos.contadorVigenciasProrrateos(secCentroCosto);
        } catch (Exception e) {
            System.out.println("ERROR administrarCentrosCostos.contadorVigenciasProrrateos ERROR===" + e.getMessage());
            return null;
        }
    }

}

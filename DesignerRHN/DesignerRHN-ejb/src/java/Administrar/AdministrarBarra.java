/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.ConsultasLiquidaciones;
import Entidades.ParametrosEstructuras;
import InterfaceAdministrar.AdministrarBarraInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaCandadosInterface;
import InterfacePersistencia.PersistenciaConsultasLiquidacionesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pantalla 'Barra'.
 * @author betelgeuse
 */
@Stateful
public class AdministrarBarra implements AdministrarBarraInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstados'.
     */
    @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaCandados'.
     */
    @EJB
    PersistenciaCandadosInterface persistenciaCandados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstructuras'.
     */
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaConsultasLiquidaciones'.
     */
    @EJB
    PersistenciaConsultasLiquidacionesInterface persistenciaConsultasLiquidaciones;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    @Override
    public Integer empleadosParaLiquidar() {
        return persistenciaParametrosEstados.empleadosParaLiquidar(usuarioBD());
    }

    @Override
    public Integer empleadosLiquidados() {
        return persistenciaParametrosEstados.empleadosLiquidados(usuarioBD());
    }

    @Override
    public boolean permisosLiquidar(String usuarioBD) {
        return persistenciaCandados.permisoLiquidar(usuarioBD);
    }

    @Override
    public String usuarioBD() {
        return persistenciaActualUsuario.actualAliasBD();
    }

    @Override
    public void liquidarNomina() {
        persistenciaCandados.liquidar();
    }

    @Override
    public String estadoLiquidacion(String usuarioBD) {
        return persistenciaCandados.estadoLiquidacion(usuarioBD);
    }

    @Override
    public ParametrosEstructuras parametrosLiquidacion() {
        return persistenciaParametrosEstructuras.buscarParametro(usuarioBD());
    }

    @Override
    public void inicializarParametrosEstados() {
        persistenciaParametrosEstados.inicializarParametrosEstados();
    }

    @Override
    public Integer progresoLiquidacion(Integer totalEmpleadoALiquidar) {
        return persistenciaCandados.progresoLiquidacion(totalEmpleadoALiquidar);
    }

    @Override
    public void cancelarLiquidacion(String usuarioBD) {
        persistenciaCandados.cancelarLiquidacion(usuarioBD);
    }

    @Override
    public List<ConsultasLiquidaciones> liquidacionesCerradas(String fechaInicial, String fechaFinal) {
        return persistenciaConsultasLiquidaciones.liquidacionesCerradas(fechaInicial, fechaFinal);
    }

    @Override
    public List<ConsultasLiquidaciones> preNomina() {
        return persistenciaConsultasLiquidaciones.preNomina();
    }

    @Override
    public String estadoConsultaDatos(BigInteger secuenciaEmpresa) {
        return persistenciaEmpresas.estadoConsultaDatos(secuenciaEmpresa);
    }
}

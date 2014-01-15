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
    public Integer contarEmpleadosParaLiquidar() {
        return persistenciaParametrosEstados.empleadosParaLiquidar(consultarUsuarioBD());
    }

    @Override
    public Integer contarEmpleadosLiquidados() {
        return persistenciaParametrosEstados.empleadosLiquidados(consultarUsuarioBD());
    }

    @Override
    public boolean verificarPermisosLiquidar(String usuarioBD) {
        return persistenciaCandados.permisoLiquidar(usuarioBD);
    }

    @Override
    public String consultarUsuarioBD() {
        return persistenciaActualUsuario.actualAliasBD();
    }

    @Override
    public void liquidarNomina() {
        persistenciaCandados.liquidar();
    }

    @Override
    public String consultarEstadoLiquidacion(String usuarioBD) {
        return persistenciaCandados.estadoLiquidacion(usuarioBD);
    }

    @Override
    public ParametrosEstructuras consultarParametrosLiquidacion() {
        return persistenciaParametrosEstructuras.buscarParametro(consultarUsuarioBD());
    }

    @Override
    public void inicializarParametrosEstados() {
        persistenciaParametrosEstados.inicializarParametrosEstados();
    }

    @Override
    public Integer consultarProgresoLiquidacion(Integer totalEmpleadoALiquidar) {
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
    public List<ConsultasLiquidaciones> consultarPreNomina() {
        return persistenciaConsultasLiquidaciones.preNomina();
    }

    @Override
    public String consultarEstadoConsultaDatos(BigInteger secuenciaEmpresa) {
        return persistenciaEmpresas.estadoConsultaDatos(secuenciaEmpresa);
    }
}

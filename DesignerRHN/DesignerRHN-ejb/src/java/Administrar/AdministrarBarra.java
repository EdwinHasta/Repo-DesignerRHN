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
import javax.ejb.Stateless;

@Stateless
public class AdministrarBarra implements AdministrarBarraInterface {

    @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    @EJB
    PersistenciaCandadosInterface persistenciaCandados;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaConsultasLiquidacionesInterface persistenciaConsultasLiquidaciones;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;

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

    public ParametrosEstructuras parametrosLiquidacion() {
        return persistenciaParametrosEstructuras.estructurasComprobantes(usuarioBD());
    }

    public void inicializarParametrosEstados() {
        persistenciaParametrosEstados.inicializarParametrosEstados();
    }

    public Integer progresoLiquidacion(Integer totalEmpleadoALiquidar) {
        return persistenciaCandados.progresoLiquidacion(totalEmpleadoALiquidar);
    }

    public void cancelarLiquidacion(String usuarioBD) {
        persistenciaCandados.cancelarLiquidacion(usuarioBD);
    }

    public List<ConsultasLiquidaciones> liquidacionesCerradas(String fechaInicial, String fechaFinal) {
        return persistenciaConsultasLiquidaciones.liquidacionesCerradas(fechaInicial, fechaFinal);
    }

    public List<ConsultasLiquidaciones> preNomina() {
        return persistenciaConsultasLiquidaciones.preNomina();
    }

    public String estadoConsultaDatos(BigInteger secuenciaEmpresa) {
        return persistenciaEmpresas.estadoConsultaDatos(secuenciaEmpresa);
    }
}

package Administrar;

import Entidades.Estructuras;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import Entidades.Usuarios;
import InterfaceAdministrar.AdministrarParametrosInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaParametrosEstadosInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaParametrosInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaUsuariosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarParametros implements AdministrarParametrosInterface {

    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaParametrosInterface persistenciaParametros;
    @EJB
    PersistenciaParametrosEstadosInterface persistenciaParametrosEstados;
    @EJB
    PersistenciaUsuariosInterface persistenciaUsuarios;

    public Usuarios usuarioActual() {
        String usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaUsuarios.buscarUsuario(usuarioBD);
    }

    @Override
    public ParametrosEstructuras parametrosLiquidacion() {
        String usuarioBD = persistenciaActualUsuario.actualAliasBD();
        return persistenciaParametrosEstructuras.buscarParametro(usuarioBD);
    }

    @Override
    public List<Estructuras> lovEstructuras() {
        return persistenciaEstructuras.estructuras();
    }

    public List<TiposTrabajadores> lovTiposTrabajadores() {
        return persistenciaTiposTrabajadores.buscarTiposTrabajadores();
    }

    @Override
    public List<Procesos> lovProcesos() {
        return persistenciaProcesos.procesosParametros();
    }

    @Override
    public List<Parametros> empleadosParametros() {
        return persistenciaParametros.empleadosParametros();
    }

    @Override
    public String estadoParametro(BigInteger secuenciaParametro) {
        return persistenciaParametrosEstados.parametrosComprobantes(secuenciaParametro);
    }

    public void crearParametroEstructura(ParametrosEstructuras parametroEstructura) {
        persistenciaParametrosEstructuras.editar(parametroEstructura);
    }

    public void eliminarParametros(List<Parametros> listaParametros) {
        for (int i = 0; i < listaParametros.size(); i++) {
            persistenciaParametros.borrar(listaParametros.get(i));
        }
    }

    @Override
    public void adicionarEmpleados(BigInteger secParametroEstructura) {
        persistenciaParametrosEstructuras.adicionarEmpleados(secParametroEstructura);
    }

    public void borrarParametros(BigInteger secParametroEstructura) {
        persistenciaParametros.borrarParametros(secParametroEstructura);
    }

    public Integer empleadosParametrizados(BigInteger secProceso) {
        return persistenciaParametrosEstructuras.empleadosParametrizados(secProceso);
    }

    public Integer diferenciaDias(String fechaInicial, String fechaFinal) {
        return persistenciaParametrosEstructuras.diasDiferenciaFechas(fechaInicial, fechaFinal);
    }
}

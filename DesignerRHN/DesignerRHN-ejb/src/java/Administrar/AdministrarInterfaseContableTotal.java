package Administrar;

import Entidades.ActualUsuario;
import Entidades.Empresas;
import Entidades.InterconTotal;
import Entidades.ParametrosContables;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarInterfaseContableTotalInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaInterconTotalInterface;
import InterfacePersistencia.PersistenciaParametrosContablesInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
@Stateful
public class AdministrarInterfaseContableTotal implements AdministrarInterfaseContableTotalInterface {

    @EJB
    PersistenciaParametrosContablesInterface persistenciaParametrosContables;
    @EJB
    PersistenciaInterconTotalInterface persistenciaInterconTotal;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    @Override
    public List<ParametrosContables> obtenerParametrosContablesUsuarioBD(String usuarioBD) {
        try {
            List<ParametrosContables> parametro = persistenciaParametrosContables.buscarParametrosContablesUsuarioBD(em, usuarioBD);
            if (parametro != null) {
                for (int i = 0; i < parametro.size(); i++) {
                    Empresas empresa = persistenciaEmpresas.consultarEmpresaPorCodigo(em, parametro.get(i).getEmpresaCodigo());
                    if (empresa != null) {
                        parametro.get(i).setEmpresaRegistro(empresa);
                    }
                    if (parametro.get(i).getProceso() == null) {
                        parametro.get(i).setProceso(new Procesos());
                    }
                }
            }
            return parametro;
        } catch (Exception e) {
            System.out.println("Error obtenerParametroContableUsuarioBD Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void modificarParametroContable(ParametrosContables parametro) {
        try {
            if (parametro.getProceso().getSecuencia() == null) {
                parametro.setProceso(null);
            }
            persistenciaParametrosContables.editar(em, parametro);
        } catch (Exception e) {
            System.out.println("Error modificarParametroContable Admi : " + e.toString());

        }
    }

    @Override
    public void borrarParametroContable(List<ParametrosContables> listPC) {
        try {
            for (int i = 0; i < listPC.size(); i++) {
                if (listPC.get(i).getProceso().getSecuencia() == null) {
                    listPC.get(i).setProceso(null);
                }
                persistenciaParametrosContables.borrar(em, listPC.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error borrarParametroContable Admi : " + e.toString());
        }
    }

    @Override
    public void crearParametroContable(ParametrosContables parametro) {
        try {
            if (parametro.getProceso().getSecuencia() == null) {
                parametro.setProceso(null);
            }
            persistenciaParametrosContables.crear(em, parametro);
        } catch (Exception e) {
            System.out.println("Error modificarParametroContable Admi : " + e.toString());

        }
    }

    @Override
    public List<SolucionesNodos> obtenerSolucionesNodosParametroContable(Date fechaInicial, Date fechaFinal) {
        try {
            List<SolucionesNodos> lista = persistenciaSolucionesNodos.buscarSolucionesNodosParaParametroContable(em, fechaInicial, fechaFinal);
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerSolucionesNodosParametroContable Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<InterconTotal> obtenerInterconTotalParametroContable(Date fechaInicial, Date fechaFinal) {
        try {
            List<InterconTotal> lista = persistenciaInterconTotal.buscarInterconTotalParaParametroContable(em, fechaInicial, fechaFinal);
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerInterconTotalParametroContable Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Procesos> lovProcesos() {
        try {
            List<Procesos> lista = persistenciaProcesos.buscarProcesos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovProcesos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empresas> lovEmpresas() {
        try {
            List<Empresas> lista = persistenciaEmpresas.buscarEmpresas(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpresas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public ActualUsuario obtenerActualUsuario() {
        try {
            ActualUsuario user = persistenciaActualUsuario.actualUsuarioBD(em);
            return user;
        } catch (Exception e) {
            System.out.println("Error obtenerActualUsuario Admi : " + e.toString());
            return null;
        }
    }

}

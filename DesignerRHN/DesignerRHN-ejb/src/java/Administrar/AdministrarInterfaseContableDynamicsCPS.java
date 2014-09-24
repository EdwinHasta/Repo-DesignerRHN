package Administrar;

import Entidades.ActualUsuario;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.InterconDynamics;
import Entidades.ParametrosContables;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import InterfaceAdministrar.AdministrarInterfaseContableDynamicsCPSInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaContabilizacionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import InterfacePersistencia.PersistenciaInterconDynamicsInterface;
import InterfacePersistencia.PersistenciaParametrosContablesInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaUsuariosInterfasesInterface;
import InterfacePersistencia.PersistenciaVWActualesFechasInterface;
import java.math.BigInteger;
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
public class AdministrarInterfaseContableDynamicsCPS implements AdministrarInterfaseContableDynamicsCPSInterface{

    @EJB
    PersistenciaParametrosContablesInterface persistenciaParametrosContables;
    @EJB
    PersistenciaInterconDynamicsInterface persistenciaInterconDynamics;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaVWActualesFechasInterface persistenciaVWActualesFechas;
    @EJB
    PersistenciaGeneralesInterface persistenciaGenerales;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaContabilizacionesInterface persistenciaContabilizaciones;
    @EJB
    PersistenciaUsuariosInterfasesInterface persistenciaUsuariosInterfases;
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
            List<SolucionesNodos> lista = persistenciaSolucionesNodos.buscarSolucionesNodosParaParametroContable_Dynamics(em, fechaInicial, fechaFinal);
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerSolucionesNodosParametroContable Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<InterconDynamics> obtenerInterconDynamicsParametroContable(Date fechaInicial, Date fechaFinal) {
        try {
            List<InterconDynamics> lista = persistenciaInterconDynamics.buscarInterconDynamicParametroContable(em, fechaInicial, fechaFinal);
            return lista;
        } catch (Exception e) {
            System.out.println("Error obtenerInterconDynamicsParametroContable Admi : " + e.toString());
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

    @Override
    public ParametrosEstructuras parametrosLiquidacion() {
        String usuarioBD = persistenciaActualUsuario.actualAliasBD(em);
        return persistenciaParametrosEstructuras.buscarParametro(em, usuarioBD);
    }

    @Override
    public Date buscarFechaHastaVWActualesFechas() {
        try {
            Date objeto = persistenciaVWActualesFechas.actualFechaHasta(em);
            return objeto;
        } catch (Exception e) {
            System.out.println("Error buscarFechaHastaVWActualesFechas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Date buscarFechaDesdeVWActualesFechas() {
        try {
            Date objeto = persistenciaVWActualesFechas.actualFechaDesde(em);
            return objeto;
        } catch (Exception e) {
            System.out.println("Error buscarFechaDesdeVWActualesFechas Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String obtenerDescripcionProcesoArchivo(BigInteger proceso) {
        try {
            String valor = persistenciaProcesos.obtenerDescripcionProcesoPorSecuencia(em, proceso);
            return valor;
        } catch (Exception e) {
            System.out.println("Error obtenerDescripcionProcesoArchivo Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String obtenerPathServidorWeb() {
        try {
            String path = persistenciaGenerales.obtenerPathServidorWeb(em);
            return path;
        } catch (Exception e) {
            System.out.println("Error obtenerPathServidorWeb Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String obtenerPathProceso() {
        try {
            String path = persistenciaGenerales.obtenerPathProceso(em);
            return path;
        } catch (Exception e) {
            System.out.println("Error obtenerPathProceso Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public int contarProcesosContabilizadosInterconDynamics(Date fechaInicial, Date fechaFinal) {
        try {
            int contador = persistenciaInterconDynamics.contarProcesosContabilizadosInterconDynamics(em, fechaInicial, fechaFinal);
            return contador;
        } catch (Exception e) {
            System.out.println("Error contarProcesosContabilizadosInterconDynamics Admi : " + e.toString());
            return -1;
        }
    }

    @Override
    public void cerrarProcesoContable(Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.cerrarProcesoContabilizacion(em, fechaInicial, fechaFinal, proceso, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error cerrarProcesoContable Admi : " + e.toString());
        }
    }

    @Override
    public List<Empleados> buscarEmpleadosEmpresa() {
        try {
            List<Empleados> lista = persistenciaEmpleados.buscarEmpleados(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleadosEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void ejecutarPKGCrearArchivoPlano(Date fechaIni, Date fechaFin, BigInteger proceso, String descripcionProceso, String nombreArchivo, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.ejecutarPKGCrearArchivoPlano_CPS(em, fechaIni, fechaFin, proceso, descripcionProceso, nombreArchivo, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKGCrearArchivoPlano Admi : " + e.toString());
        }
    }

    @Override
    public Integer conteoContabilizacionesDynamics(Date fechaIni, Date fechaFin) {
        try {
            Integer conteo = persistenciaContabilizaciones.obtenerContadorFlagGeneradoFechasDynamics(em, fechaIni, fechaFin);
            return conteo;
        } catch (Exception e) {
            System.out.println("Error conteoContabilizacionesDynamics Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void ejecutarPKGRecontabilizar(Date fechaIni, Date fechaFin) {
        try {
            persistenciaInterconDynamics.ejecutarPKGRecontabilizar(em, fechaIni, fechaFin);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKGRecontabilizar Admi : " + e.toString());
        }
    }

    @Override
    public void actualizarFlagContabilizacionDeshacerDynamics(Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.actualizarFlagContabilizacionDeshacerDynamics(em, fechaIni, fechaFin, proceso, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error actualizarFlagContabilizacionDeshacerDynamics Admi : " + e.toString());
        }
    }

    @Override
    public void deleteInterconDynamics(Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.deleteInterconDynamics(em, fechaIni, fechaFin, proceso, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error deleteInterconDynamics Admi : " + e.toString());
        }
    }
    
    @Override 
    public void actualizarFlagContabilizacionDeshacerDynamics_NOT_EXITS(Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.actualizarFlagContabilizacionDeshacerDynamics_NOT_EXITS(em, fechaIni, fechaFin, proceso, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error actualizarFlagContabilizacionDeshacerDynamics Admi : " + e.toString());
        }
    }

    @Override
    public void ejecutarPKGUbicarnuevointercon_DYNAMICS(BigInteger secuencia, Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaInterconDynamics.ejecutarPKGUbicarnuevointercon_DYNAMICS(em, secuencia, fechaIni, fechaFin, proceso, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKGUbicarnuevointercon_DYNAMICS Admi : " + e.toString());
        }
    }

    @Override
    public void anularComprobantesCerrados(Date fechaIni, Date fechaFin, BigInteger proceso) {
        try {
            persistenciaInterconDynamics.anularComprobantesCerrados(em, fechaIni, fechaFin, proceso);
        } catch (Exception e) {
            System.out.println("Error anularComprobantesCerrados Admi : " + e.toString());

        }
    }

    @Override
    public Date obtenerFechaMaxContabilizaciones() {
        try {
            Date fecha = persistenciaContabilizaciones.obtenerFechaMaximaContabilizaciones(em);
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaMaxContabilizaciones Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Date obtenerFechaMaxInterconDynamics() {
        try {
            Date fecha = persistenciaInterconDynamics.obtenerFechaContabilizacionMaxInterconDynamics(em);
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaMaxInterconDynamics Admi : " + e.toString());
            return null;
        }
    }
}

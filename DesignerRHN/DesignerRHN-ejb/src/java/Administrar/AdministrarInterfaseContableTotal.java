package Administrar;

import Entidades.ActualUsuario;
import Entidades.Empresas;
import Entidades.InterconTotal;
import Entidades.ParametrosContables;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.Terceros;
import Entidades.VWActualesFechas;
import InterfaceAdministrar.AdministrarInterfaseContableTotalInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaContabilizacionesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaInterconTotalInterface;
import InterfacePersistencia.PersistenciaParametrosContablesInterface;
import InterfacePersistencia.PersistenciaParametrosEstructurasInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
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
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaContabilizacionesInterface persistenciaContabilizaciones;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaVWActualesFechasInterface persistenciaVWActualesFechas;

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
            if (lista != null) {
                for (int i = 0; i < lista.size(); i++) {
                    if (lista.get(i).getCodigotercero() != null && (!lista.get(i).getCodigotercero().isEmpty())) {
                        Long codigo = new Long(lista.get(i).getCodigotercero());
                        Terceros tercero = persistenciaTerceros.buscarTerceroPorCodigo(em, codigo);
                        if (tercero != null) {
                            lista.get(i).setTerceroRegistro(tercero);
                        } else {
                            lista.get(i).setTerceroRegistro(new Terceros());
                        }
                    } else {
                        lista.get(i).setTerceroRegistro(new Terceros());
                    }
                }
            }
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
    public Date obtenerFechaMaxInterconTotal() {
        try {
            Date fecha = persistenciaInterconTotal.obtenerFechaContabilizacionMaxInterconTotal(em);
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaMaxInterconTotal Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public ParametrosEstructuras parametrosLiquidacion() {
        String usuarioBD = persistenciaActualUsuario.actualAliasBD(em);
        return persistenciaParametrosEstructuras.buscarParametro(em, usuarioBD);
    }

    @Override
    public void actualizarFlagInterconTotal(Date fechaInicial, Date fechaFinal, Short empresa) {
        try {
            persistenciaInterconTotal.actualizarFlagInterconTotal(em, fechaInicial, fechaFinal, empresa);
        } catch (Exception e) {
            System.out.println("Error actualizarFlagInterconTotal Admi : " + e.toString());
        }
    }

    @Override
    public void actualizarFlagInterconTotalProcesoDeshacer(Date fechaInicial, Date fechaFinal, BigInteger proceso) {
        try {
            persistenciaInterconTotal.actualizarFlagInterconTotalProcesoDeshacer(em, fechaInicial, fechaFinal, proceso);
        } catch (Exception e) {
            System.out.println("Error actualizarFlagInterconTotalProcesoDeshacer Admi : " + e.toString());
        }
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

    @Override
    public void ejcutarPKGUbicarnuevointercon_total(BigInteger secuencia, Date fechaInicial, Date fechaFinal, BigInteger proceso) {
        try {
            persistenciaInterconTotal.ejecutarPKGUbicarnuevointercon_total(em, secuencia, fechaInicial, fechaFinal, proceso);
        } catch (Exception e) {
            System.out.println("Error ejcutarPKGUbicarnuevointercon_total Admi : " + e.toString());
        }
    }

    @Override
    public void eliminarInterconTotal(Date fechaInicial, Date fechaFinal, Short empresa, BigInteger proceso) {
        try {
            persistenciaInterconTotal.eliminarInterconTotal(em, fechaInicial, fechaFinal, empresa, proceso);
        } catch (Exception e) {
            System.out.println("Error eliminarInterconTotal Admi : " + e.toString());
        }
    }

    @Override
    public int contarProcesosContabilizadosInterconTotal(Date fechaInicial, Date fechaFinal) {
        try {
            int contador = persistenciaInterconTotal.contarProcesosContabilizadosInterconTotal(em, fechaInicial, fechaFinal);
            return contador;
        } catch (Exception e) {
            System.out.println("Error contarProcesosContabilizadosInterconTotal Admi : " + e.toString());
            return -1;
        }
    }
    
    @Override
    public void cerrarProcesoContabilizacion(Date fechaInicial, Date fechaFinal, Short empresa, BigInteger proceso) {
        try {
            persistenciaInterconTotal.cerrarProcesoContabilizacion(em, fechaInicial, fechaFinal,empresa,proceso);
        } catch (Exception e) {
            System.out.println("Error cerrarProcesoContabilizacion Admi : " + e.toString());
        }
    }
}

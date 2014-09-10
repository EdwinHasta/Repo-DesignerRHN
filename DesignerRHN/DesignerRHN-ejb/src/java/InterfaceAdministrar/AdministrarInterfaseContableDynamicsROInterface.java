/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.ActualUsuario;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.InterconDynamics;
import Entidades.ParametrosContables;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.UsuariosInterfases;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface AdministrarInterfaseContableDynamicsROInterface {

    public void obtenerConexion(String idSesion);

    public List<ParametrosContables> obtenerParametrosContablesUsuarioBD(String usuarioBD);

    public void modificarParametroContable(ParametrosContables parametro);

    public void borrarParametroContable(List<ParametrosContables> listPC);

    public void crearParametroContable(ParametrosContables parametro);

    public List<SolucionesNodos> obtenerSolucionesNodosParametroContable(Date fechaInicial, Date fechaFinal);

    public List<InterconDynamics> obtenerInterconDynamicsParametroContable(Date fechaInicial, Date fechaFinal);

    public List<Procesos> lovProcesos();

    public List<Empresas> lovEmpresas();

    public ActualUsuario obtenerActualUsuario();

    public ParametrosEstructuras parametrosLiquidacion();

    public Date buscarFechaHastaVWActualesFechas();

    public Date buscarFechaDesdeVWActualesFechas();

    public String obtenerDescripcionProcesoArchivo(BigInteger proceso);

    public String obtenerPathServidorWeb();

    public String obtenerPathProceso();

    public int contarProcesosContabilizadosInterconDynamics(Date fechaInicial, Date fechaFinal);

    public List<Empleados> buscarEmpleadosEmpresa();

    public void cerrarProcesoContable(Date fechaInicial, Date fechaFinal, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void ejecutarPKGCrearArchivoPlano(Date fechaIni, Date fechaFin, BigInteger proceso, String descripcionProceso, String nombreArchivo, BigInteger emplDesde, BigInteger emplHasta);

    public Integer conteoContabilizacionesDynamics(Date fechaIni, Date fechaFin);

    public void ejecutarPKGRecontabilizar(Date fechaIni, Date fechaFin);

    public void actualizarFlagContabilizacionDeshacerDynamics(Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void deleteInterconDynamics(Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void ejecutarPKGUbicarnuevointercon_DYNAMICS(BigInteger secuencia, Date fechaIni, Date fechaFin, BigInteger proceso, BigInteger emplDesde, BigInteger emplHasta);

    public void anularComprobantesCerrados(Date fechaIni, Date fechaFin, BigInteger proceso);

    public Date obtenerFechaMaxContabilizaciones();

    public Date obtenerFechaMaxInterconDynamics();
}

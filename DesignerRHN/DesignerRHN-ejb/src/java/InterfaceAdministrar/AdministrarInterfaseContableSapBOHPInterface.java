/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.ActualUsuario;
import Entidades.Empresas;
import Entidades.InterconSapBO;
import Entidades.ParametrosContables;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Entidades.UsuariosInterfases;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface AdministrarInterfaseContableSapBOHPInterface {

    public void obtenerConexion(String idSesion);

    public List<ParametrosContables> obtenerParametrosContablesUsuarioBD(String usuarioBD);

    public void modificarParametroContable(ParametrosContables parametro);

    public void borrarParametroContable(List<ParametrosContables> listPC);

    public void crearParametroContable(ParametrosContables parametro);

    public List<SolucionesNodos> obtenerSolucionesNodosParametroContable(Date fechaInicial, Date fechaFinal);

    public List<InterconSapBO> obtenerInterconSapBOHPParametroContable(Date fechaInicial, Date fechaFinal);

    public List<Procesos> lovProcesos();

    public List<Empresas> lovEmpresas();

    public ActualUsuario obtenerActualUsuario();

    public Date obtenerMaxFechaContabilizaciones();

    public Date obtenerMaxFechaIntercoSapBO();

    public ParametrosEstructuras parametrosLiquidacion();

    public void actualizarFlagProcesoAnularInterfaseContableSAPBOHP(Date fechaIni, Date fechaFin);

    public Date buscarFechaHastaVWActualesFechas();

    public Date buscarFechaDesdeVWActualesFechas();

    public void cerrarProcesoLiquidacion(Date fechaIni, Date fechaFin, BigInteger proceso);

    public void cambiarFlagInterconContableSAPBOHP(Date fechaIni, Date fechaFin, BigInteger proceso);

    public void ejecutarDeleteInterconSAP(Date fechaIni, Date fechaFin, BigInteger proceso);

    public void ejeuctarPKGUbicarnuevointercon_SAPBOVHP(BigInteger secuencia, Date fechaIni, Date fechaFin, BigInteger proceso);

    public int contarProcesosContabilizadosInterconSAPBO(Date fechaInicial, Date fechaFinal);

    public Integer obtenerContadorFlagGeneradoFechasSAP(Date fechaIni, Date fechaFin);

    public void ejecutarPKGRecontabilizacion(Date fechaIni, Date fechaFin);

    public void ejecutarPKGCrearArchivoPlano(Date fechaIni, Date fechaFin, BigInteger proceso, String descripcion, String nombreArchivo);

    public String obtenerPathProceso();

    public String obtenerPathServidorWeb();

    public String obtenerDescripcionProcesoArchivo(BigInteger proceso);

    public UsuariosInterfases obtenerUsuarioInterfaseContabilizacion();

}

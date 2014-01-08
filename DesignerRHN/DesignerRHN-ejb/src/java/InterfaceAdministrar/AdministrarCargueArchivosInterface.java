/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import ClasesAyuda.ResultadoBorrarTodoNovedades;
import Entidades.ActualUsuario;
import Entidades.Conceptos;
import Entidades.Empleados;
import Entidades.Formulas;
import Entidades.TempNovedades;
import Entidades.VWActualesReformasLaborales;
import Entidades.VWActualesTiposContratos;
import Entidades.VWActualesTiposTrabajadores;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pantalla 'CargueArchivos'. 
 * @author betelgeuse
 */
public interface AdministrarCargueArchivosInterface {
    /**
     * Método encargado de crear TempNovedades.
     * @param tnovedad Lista de los TempNovedades que se van a crear.
     */
    public void crearTempNovedades(TempNovedades tnovedad);
    /**
     * Método encargado de editar TempNovedades.
     * @param tnovedad Lista de los TempNovedades que se van a modificar.
     */
    public void modificarTempNovedades(TempNovedades tnovedad);
    /**
     * Método encargado de borrar todos los RegistrosTempNovedades.
     * @param usuarioBD Lista de los ExtrasRecargos que se van a eliminar.
     */
    public void borrarRegistrosTempNovedades(String usuarioBD);
    /**
     * Método encargado de recuperar el usuario que esta usando el aplicativo.
     * @return Retorna un ActualUsuario con la información del usuario.
     */
    public ActualUsuario actualUsuario();
    /**
     * Método encargado de recuperar la secuencia de la empresa asociada al ParametroEstructura del usuario
     * que está usando el aplicativo.
     * @param usuarioBD Alias del usuario que está usando el aplicativo.
     * @return Retorna la secuencia de la empresa.
     */
    public BigInteger empresaParametros(String usuarioBD);
    /**
     * 
     * @param usuarioBD
     * @return 
     */
    public List<TempNovedades> listaTempNovedades(String usuarioBD);
    
    public boolean existenciaEmpleado(BigInteger codEmpleado, BigInteger secEmpresa);
    
    public boolean existenciaConcepto(BigInteger codConcepto);
    
    public boolean existenciaPeriodicidad(BigInteger codPeriodicidad);
    
    public boolean existenciaTercero(BigInteger nitTercero);
    
    public boolean validarTipoEmpleadoActivo(BigInteger codEmpleado, BigInteger secEmpresa);
    
    public Empleados buscarEmpleadoCodigo(BigInteger codEmpleado, BigInteger secEmpresa);
    
    public VWActualesTiposTrabajadores buscarTipoTrabajador(BigInteger secuenciaEmpleado);
    
    public VWActualesReformasLaborales buscarActualReformaLaboral(BigInteger secuenciaEmpleado);
    
    public VWActualesTiposContratos buscarActualTipoContrato(BigInteger secuenciaEmpleado);
    
    public Conceptos validarConceptoEmpresa(BigInteger codigoConcepto, BigInteger codigoEmpresa);
    
    public String determinarTipoConcepto(BigInteger secConcepto);
    
    public boolean verificarZonaT(BigInteger secConcepto, BigInteger secTS, BigInteger secTC, BigInteger secTT);
    
    public List<Formulas> formulasCargue();
    
    public Formulas formulaCargueInicial();
    
    public boolean validarFormulaCargue_Concepto(BigInteger secConcepto, BigInteger secFormula);
    
    public boolean validarNecesidadTercero(BigInteger secConcepto);
    
    public boolean validarTerceroEmpresaEmpleado(BigInteger nit, BigInteger secEmpresa);
    
    public List<String> obtenerDocumentosSoporteCargados(String usuarioBD);
    
    public void cargarTempNovedades(Date fechaReporte, String nombreCorto, String usarFormula);
    
    public int reversar(ActualUsuario usuarioBD, String documentoSoporte);
    
    public ResultadoBorrarTodoNovedades BorrarTodo(ActualUsuario usuarioBD, List<String> documentosSoporte);
}

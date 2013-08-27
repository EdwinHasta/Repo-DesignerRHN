/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author Administrator
 */
public interface AdministrarCargueArchivosInterface {

    public void crearTempNovedades(TempNovedades tnovedad);

    public void modificarTempNovedades(TempNovedades tnovedad);

    public void borrarRegistrosTempNovedades(String usuarioBD);

    public ActualUsuario actualUsuario();

    public BigInteger empresaParametros(String usuarioBD);

    public List<TempNovedades> listaTempNovedades(String usuarioBD);

    public void revisarConcepto(int codConcepto);

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

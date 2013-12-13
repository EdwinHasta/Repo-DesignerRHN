/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.SolucionesNodos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'SolucionesNodos' 
 * de la base de datos.
 * @author betelgeuse
 */
public interface PersistenciaSolucionesNodosInterface {
   /**
     * Método encargado de insertar una SolucionNodo en la base de datos.
     * @param solucionNodo SolucionNodo que se quiere crear.
     */
   public void crear(SolucionesNodos solucionNodo);
   /**
     * Método encargado de modificar una SolucionNodo de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param solucionNodo SolucionNodo con los cambios que se van a realizar.
     */
   public void editar(SolucionesNodos solucionNodo);
   /**
     * Método encargado de eliminar de la base de datos la SolucionNodo que entra por parámetro.
     * @param solucionNodo SolucionNodo que se quiere eliminar.
     */
   public void borrar(SolucionesNodos solucionNodo);
   /**
     * Método encargado de buscar todas las SolucionesNodos existentes en la base de datos.
     * @return Retorna una lista de SolucionesNodos.
     */
   public List<SolucionesNodos> buscarSolucionesNodos();
   /**
    * Método encargado de buscar las SolucionesNodos de un empleado, con un CorteProceso específico y además 
    * tienen las siguientes características:
    * ESTADO: 'CERRADO'
    * TIPO: 'PAGO' O 'DESCUENTO'
    * el resultado esta organizado por el código del Concepto asociado a la SolicionNodo
    * @param secuenciaCorteProceso Secuencia del CorteProceso que se quiere buscar.
    * @param secuenciaEmpleado Secuencia del Empleado al que se le quiere averiguar la información.
    * @return Retorna una lista de SolucionesNodos con las características mencionadas en la descripción del método.
    */
   public List<SolucionesNodos> solucionNodoCorteProcesoEmpleado(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
   /**
    * Método encargado de buscar las SolucionesNodos de un empleador, con un CorteProceso específico y además 
    * tienen las siguientes características:
    * ESTADO: 'CERRADO'
    * TIPO: 'PASIVO' o 'GASTO' o 'NETO'
    * el resultado esta organizado por el código del Concepto asociado a la SolicionNodo.
    * @param secuenciaCorteProceso Secuencia del CorteProceso que se quiere buscar.
    * @param secuenciaEmpleado Secuencia del Empleador al que se le quiere averiguar la información.
    * @return Retorna una lista de SolucionesNodos con las características mencionadas en la descripción del método. 
    */
   public List<SolucionesNodos> solucionNodoCorteProcesoEmpleador(BigInteger secuenciaCorteProceso, BigInteger secuenciaEmpleado);
   /**
    * Método encargado de obtener los días Provisionados de un empleado a la fecha actual.
    * @param secuencia Secuencia del empleado.
    * @return Retorna un BigDecimal con la cantidad de los días Provisionados.
    */
   public BigDecimal diasProvisionados(BigInteger secuencia);
   /**
    * Método encargado de contar cuantas liquidaciones existen para un empleado con la afiliación asociada al Tercero y  el TipoEntidad dados,
    * cuya fechaPago es mayor a la fecha dada por parámetro.
    * @param secuencia Secuencia del Empleado.
    * @param fechaInicial Fecha Inicial a partir de la cual se van a tomar las SolicionesNodos.
    * @param secuenciaTE Secuencia del TipoEntidad.
    * @param secuenciaTer Secuencia del Tercero.
    * @return Retorna un Long con la cantidad de liquidaciones que cumplen con las condiciones.
    */
   public Long validacionTercerosVigenciaAfiliacion(BigInteger secuencia,Date fechaInicial,BigDecimal secuenciaTE, BigInteger secuenciaTer);
   /**
    * Método encargado de buscar las SolucionesNodos de un empleado específico y además 
    * con las siguientes características:
    * ESTADO: 'LIQUIDADO'
    * TIPO: 'PAGO' O 'DESCUENTO'
    * el resultado esta organizado por el código del Concepto asociado a la SolicionNodo.
    * @param secuenciaEmpleado Secuencia del Empleado.
    * @return Retorna una lista de SolucionesNodos con las características mencionadas en la descripción del método. 
    */
   public List<SolucionesNodos> solucionNodoEmpleado(BigInteger secuenciaEmpleado);
   /**
    * Método encargado de buscar las SolucionesNodos de un empleado específico y además 
    * con las siguientes características:
    * ESTADO: 'LIQUIDADO'
    * TIPO: 'PASIVO' o 'GASTO' o 'NETO'
    * @param secuenciaEmpleado Secuencia del Empleador.
    * @return Retorna una lista de SolucionesNodos con las características mencionadas en la descripción del método. 
    */
   public List<SolucionesNodos> solucionNodoEmpleador(BigInteger secuenciaEmpleado);
   /**
    * de los empleados que voy a liquidar, cuantos estan en estado 'LIQUIDADO'
    * @param secProceso
    * @return 
    */
   public Integer ContarProcesosSN(BigInteger secProceso);
}

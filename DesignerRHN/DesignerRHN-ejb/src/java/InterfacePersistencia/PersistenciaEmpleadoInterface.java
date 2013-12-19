/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfacePersistencia;

import Entidades.Empleados;
import java.math.BigInteger;
import java.util.List;

public interface PersistenciaEmpleadoInterface {
    /**
     * Método encargado de insertar un Empleado en la base de datos.
     * @param empleados Empleado que se quiere crear.
     */
    public void crear(Empleados empleados);
    /**
     * Método encargado de modificar un Empleado de la base de datos.
     * Este método recibe la información del parámetro para hacer un 'merge' con la 
     * información de la base de datos.
     * @param empleados Empleado con los cambios que se van a realizar.
     */
    public void editar(Empleados empleados);
    /**
     * Método encargado de eliminar de la base de datos el empleado que entra por parámetro.
     * @param empleados Empleado que se quiere eliminar.
     */
    public void borrar(Empleados empleados);
    /**
     * Método encargado de buscar el Empleado con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Empleado que se quiere encontrar.
     * @return Retorna el Empleado identificado con la secuencia dada por parámetro.
     */
    public Empleados buscarEmpleado(BigInteger secuencia);
    /**
     * Método encargado de buscar todos los Empleados existentes en la base de datos.
     * @return Retorna una lista de Contratos.
     */
    public List<Empleados> buscarEmpleados();
    /**
     * Método encargado de buscar el Empleado con la secuencia dada por parámetro.
     * @param secuencia Secuencia del Empleado que se quiere encontrar.
     * @return Retorna el Empleado identificado con la secuencia dada por parámetro.
     */
    public Empleados buscarEmpleadoSecuencia(BigInteger secuencia);
    /**
     * Método encargado de verificar si un empleado existe para una empresa especifica.
     * @param codigoEmpleado Códígo del empleado.
     * @param secEmpresa Secuencia de la empresa a la que se quiere verificar si el usuario pertenece.
     * @return Retorna True si el empleado existe para esa empresa
     */
    public boolean verificarCodigoEmpleado_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa);
    /**
     * Método encargado de buscar un empleado de una empresa especifica.
     * @param codigoEmpleado Códígo del empleado.
     * @param secEmpresa Secuencia de la empresa a la que el usuario pertenecería.
     * @return Retorna el empleado que cumple las características dadas por los parámetros.
     */
    public Empleados buscarEmpleadoCodigo_Empresa(BigInteger codigoEmpleado, BigInteger secEmpresa);
    /**
     * Método encargado de buscar el Empleado con la secuencia dada por parámetro.
     * @param codigoEmpleado Código del Empleado que se quiere encontrar.
     * @return Retorna el Empleado identificado con el código dado por parámetro.
     */
    public Empleados buscarEmpleadoCodigo(BigInteger codigoEmpleado);
    /**
     * Método encargado de buscar los empleados que fueron parametrizados y se quiere consultar el comprobante de pago.
     * @param usuarioBD Alias del Usuario en la base de datos.
     * @return Retorna una lista de empleados
     */
    public List<Empleados> empleadosComprobantes(String usuarioBD);
    /**
     * Método encargado de buscar un empleado por su código
     * @param codigoEmpleado
     * @return 
     */
    public Empleados buscarEmpleadoTipo(BigInteger codigoEmpleado);
    /**
     * Método encargado de buscar los empleado que sean 'ACTIVOS','RETIRADOS' O 'PENCIONADOS'
     * y que se encuentren en la vista vwactualestipostrabajadores de la base de datos.
     * @return Retorna una lista de empleados.
     */
    public List<Empleados> empleadosNovedad();
    /**
     * Método encargado de buscar todos los Empleados existentes en la base de datos, ordenados por código.
    * @return Retorna una lista de Empleados ordenados por código. 
     */
    public List<Empleados> todosEmpleados();
    /**
     * Método encargado de buscar los empleado que sean 'ACTIVOS'
     * y que se encuentren en la vista vwactualestipostrabajadores de la base de datos.
     * @return 
     */
    public List<Empleados> empleadosVacaciones();
    /**
     * Método encargado de buscar los empleados en estado 'ACTIVO' y 'PENSIONADO'
     * para que sean adicionados a los parametors de liquidación.
     * @return 
     */
    public List<Empleados> lovEmpleadosParametros();
}

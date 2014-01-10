/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package InterfaceAdministrar;

import Entidades.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Interface encargada de determinar las operaciones lógicas necesarias para la pestaña 'Personal'.
 * @author Betelgeuse
 */
public interface AdministrarCarpetaPersonalInterface {
    /**
     * Método encargado de recuperar el cargo actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesCargos con la información requerida.
     */
    public VWActualesCargos ConsultarCargo(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar el Tipo Contrato actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesTiposContratos con la información requerida.
     */
    public VWActualesTiposContratos ConsultarTipoContrato(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la Norma actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesNormasEmpleados con la información requerida.
     */
    public VWActualesNormasEmpleados ConsultarNormaLaboral(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la afiliación a salud actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesAfiliacionesSalud con la información requerida.
     */
    public VWActualesAfiliacionesSalud ConsultarAfiliacionSalud(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la afiliación a pensión actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesAfiliacionesPension con la información requerida.
     */
    public VWActualesAfiliacionesPension ConsultarAfiliacionPension(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la localización actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesLocalizaciones con la información requerida.
     */
    public VWActualesLocalizaciones ConsultarLocalizacion(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar el tipo de trabajador actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesTiposTrabajadores con la información requerida.
     */
    public VWActualesTiposTrabajadores ConsultarTipoTrabajador(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar el contrato actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesContratos con la información requerida.
     */
    public VWActualesContratos ConsultarContrato(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la jornada actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesJornadas con la información requerida.
     */
    public VWActualesJornadas ConsultarJornada(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar el sueldo actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un BigDecimal con la información requerida.
     */
    public BigDecimal ConsultarSueldo(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la reforma laboral actual aplicada a un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesReformasLaborales con la información requerida.
     */
    public VWActualesReformasLaborales ConsultarReformaLaboral(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la Ubicación actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesUbicaciones con la información requerida.
     */
    public VWActualesUbicaciones ConsultarUbicacion(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar la forma de pago actual para un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesFormasPagos con la información requerida.
     */
    public VWActualesFormasPagos ConsultarFormaPago(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar el tipo viajero actual de un empleado específico.
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesUbicaciones con la información requerida.
     */
    public VWActualesVigenciasViajeros ConsultarTipoViajero(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar 
     * @param tipo
     * @return 
     */
    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo);
    
    public DetallesEmpresas ConsultarEmpresa();
    
    public Usuarios ConsultarUsuario(String alias);
    
    public ParametrosEstructuras ConsultarParametros();
    
    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado);
    
    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleados();
    
    public Personas buscarFotoPersona(BigInteger identificacion);
    
    public void actualizarFotoPersona(BigInteger identificacion);
    
    public Empleados buscarEmpleado(BigInteger sec);
    
    public void editarVigenciasCargos(VigenciasCargos vC);
    
    public String actualUsuario();
}

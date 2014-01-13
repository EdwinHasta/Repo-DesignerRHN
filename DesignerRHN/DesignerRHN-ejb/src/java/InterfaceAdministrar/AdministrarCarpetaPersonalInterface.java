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
 * @author betelgeuse
 */
public interface AdministrarCarpetaPersonalInterface {
    /**
     * Método encargado de recuperar el cargo actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesCargos con la información requerida.
     */
    public VWActualesCargos ConsultarCargo(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar el Tipo Contrato actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesTiposContratos con la información requerida.
     */
    public VWActualesTiposContratos ConsultarTipoContrato(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la Norma actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesNormasEmpleados con la información requerida.
     */
    public VWActualesNormasEmpleados ConsultarNormaLaboral(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la afiliación a salud actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesAfiliacionesSalud con la información requerida.
     */
    public VWActualesAfiliacionesSalud ConsultarAfiliacionSalud(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la afiliación a pensión actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesAfiliacionesPension con la información requerida.
     */
    public VWActualesAfiliacionesPension ConsultarAfiliacionPension(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la localización actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesLocalizaciones con la información requerida.
     */
    public VWActualesLocalizaciones ConsultarLocalizacion(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar el tipo de trabajador actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesTiposTrabajadores con la información requerida.
     */
    public VWActualesTiposTrabajadores ConsultarTipoTrabajador(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar el contrato actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesContratos con la información requerida.
     */
    public VWActualesContratos ConsultarContrato(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la jornada actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesJornadas con la información requerida.
     */
    public VWActualesJornadas ConsultarJornada(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar el sueldo actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un BigDecimal con la información requerida.
     */
    public BigDecimal ConsultarSueldo(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la reforma laboral actual aplicada a un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesReformasLaborales con la información requerida.
     */
    public VWActualesReformasLaborales ConsultarReformaLaboral(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la Ubicación actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesUbicaciones con la información requerida.
     */
    public VWActualesUbicaciones ConsultarUbicacion(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar la forma de pago actual para un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesFormasPagos con la información requerida.
     */
    public VWActualesFormasPagos ConsultarFormaPago(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar el tipo viajero actual de un empleado específico.
     * @param secuenciaEmpleado Secuencia del Empleado.
     * @return Retorna un VWActualesUbicaciones con la información requerida.
     */
    public VWActualesVigenciasViajeros ConsultarTipoViajero(BigInteger secuenciaEmpleado);
    /**
     * Método encargado de recuperar los empleados asociados con un tipoTrabajador específico.
     * @param tipo Tipo del trabajador por el cual se va a filtrar.
     * @return Retorna una lista de VWActualesTiposTrabajadores con la informacion requerida.
     */
    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo);
    /**
     * Método encargado de recuperar la información de la empresa a la cual pertenece el Usuario conectado.
     * @return Retorna el DetalleEmpresa con la información de la empresa.
     */
    public DetallesEmpresas ConsultarEmpresa();
    /**
     * Método encargado de recuperar el Usuario cuyo alias coincida con el dado por parámetro.
     * @param alias Alias del Usuario que se quiere encontrar.
     * @return Retorna el Usuario identificado con el alias dado por parámetro.
     */
    public Usuarios ConsultarUsuario(String alias);
    /**
     * Método encargado de recuperar los Parametros Estructuras del Usuario conectado.
     * @return Retorna un ParametroEstructura, el cual esta asociado con el usuario conectado.
     */
    public ParametrosEstructuras ConsultarParametros();
    /**
     * Método encargado de recuperar las VigenciasCargos de un empleado específico. 
     * @param secEmpleado Secuencia del Empleado.
     * @return Retorna una lista de VigenciasCargos asociadas al empleado cuya secuencia coincide con el 
     * valor del parámetro.
     */
    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado);
    /**
     * Método encargado de recuperar los VWActualesTiposTrabajadores, para obtener todos los
     * empleados sin importar el tipo.
     * @return Retorna una lista con todos los VWActualesTiposTrabajadores de la vista.
     */
    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleados();
    /**
     * Método encargado de recuperar una Persona según su identificación.
     * @param identificacion Identificación de la Persona.
     * @return Retorna la persona cuya identificación coincide con el valor del parámetro con el fin de
     * recuperar la foto.
     */
    public Personas buscarFotoPersona(BigInteger identificacion);
    /**
     * Método encargado de actualizar la foto de una persona específica.
     * @param identificacion Identificación de la persona a la cual se le va a actualizar la foto.
     */
    public void actualizarFotoPersona(BigInteger identificacion);
    /**
     * Método encargado de recuperar el empleado con una secuencia dada.
     * @param secuencia Secuencia del Empleado que se quiere recuperar.
     * @return Retorna el empleado cuya secuencia coincida con el valor dado por parámetro.
     */
    public Empleados buscarEmpleado(BigInteger secuencia);
    /**
     * Método encargado de editar VigenciasCargos.
     * @param vigenciasCargos Lista de las VigenciasCargos que se van a modificar.
     */
    public void editarVigenciasCargos(List<VigenciasCargos> vigenciasCargos);
    /**
     * Método encargado de recuperar el usuario que está conectado en la sesión actual.
     * @return Retorna un String con el Alias del usuario que esta conectado en la sesion actual.
     */
    public String actualUsuario();
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarCarpetaPersonalInterface {

    public VWActualesCargos ConsultarCargo(BigInteger secuenciaEmpleado);

    public VWActualesTiposContratos ConsultarTipoContrato(BigInteger secuenciaEmpleado);

    public VWActualesNormasEmpleados ConsultarNormaLaboral(BigInteger secuenciaEmpleado);

    public VWActualesAfiliacionesSalud ConsultarAfiliacionSalud(BigInteger secuenciaEmpleado);

    public VWActualesAfiliacionesPension ConsultarAfiliacionPension(BigInteger secuenciaEmpleado);

    public VWActualesLocalizaciones ConsultarLocalizacion(BigInteger secuenciaEmpleado);

    public VWActualesTiposTrabajadores ConsultarTipoTrabajador(BigInteger secuenciaEmpleado);

    public VWActualesContratos ConsultarContrato(BigInteger secuenciaEmpleado);

    public VWActualesJornadas ConsultarJornada(BigInteger secuenciaEmpleado);

    public BigDecimal ConsultarSueldo(BigInteger secuenciaEmpleado);

    public VWActualesReformasLaborales ConsultarReformaLaboral(BigInteger secuenciaEmpleado);

    public VWActualesUbicaciones ConsultarUbicacion(BigInteger secuenciaEmpleado);

    public VWActualesFormasPagos ConsultarFormaPago(BigInteger secuenciaEmpleado);

    public VWActualesVigenciasViajeros ConsultarTipoViajero(BigInteger secuenciaEmpleado);

    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo);

    public DetallesEmpresas ConsultarEmpresa();

    public Usuarios ConsultarUsuario(String alias);

    public ParametrosEstructuras ConsultarParametros();

    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado);

    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleados();

    public Personas buscarFotoPersona(BigInteger identificacion);

    public void actualizarFotoPersona(BigInteger identificacion);

    public Empleados buscarEmpleado(BigInteger secuencia);

    public void editarVigenciasCargos(VigenciasCargos vC);

    public String actualUsuario();
}

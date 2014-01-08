/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Administrar;

import Entidades.*;
import InterfaceAdministrar.AdministrarCarpetaPersonalInterface;
import InterfacePersistencia.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 * Clase Stateful. <br>
 * Clase encargada de realizar las operaciones lógicas para la pestaña 'Personal'.
 * @author Administrator
 */
@Stateful
public class AdministrarCarpetaPersonal implements AdministrarCarpetaPersonalInterface {
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesCargos'.
     */
    @EJB
    PersistenciaVWActualesCargosInterface persistenciaVWActualesCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualesTiposContratos'.
     */
    @EJB
    PersistenciaVWActualesTiposContratosInterface persistenciaActualesTiposContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesNormasEmpleados'.
     */
    @EJB
    PersistenciaVWActualesNormasEmpleadosInterface persistenciaVWActualesNormasEmpleados;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesAfiliacionesSalud'.
     */
    @EJB
    PersistenciaVWActualesAfiliacionesSaludInterface persistenciaVWActualesAfiliacionesSalud;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesAfiliacionesPension'.
     */
    @EJB
    PersistenciaVWActualesAfiliacionesPensionInterface persistenciaVWActualesAfiliacionesPension;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesLocalizaciones'.
     */
    @EJB
    PersistenciaVWActualesLocalizacionesInterface persistenciaVWActualesLocalizaciones;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasTiposTrabajadores'.
     */
    @EJB
    PersistenciaVigenciasTiposTrabajadoresInterface persistenciaVigenciasTiposTrabajadores;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesTiposTrabajadores'.
     */
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesContratos'.
     */
    @EJB
    PersistenciaVWActualesContratosInterface persistenciaVWActualesContratos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesJornadas'.
     */
    @EJB
    PersistenciaVWActualesJornadasInterface persistenciaVWActualesJornadas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesSueldos'.
     */
    @EJB
    PersistenciaVWActualesSueldosInterface persistenciaVWActualesSueldos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesPensiones'.
     */
    @EJB
    PersistenciaVWActualesPensionesInterface persistenciaVWActualesPensiones;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesReformasLaborales'.
     */
    @EJB
    PersistenciaVWActualesReformasLaboralesInterface persistenciaVWActualesReformasLaborales;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesUbicaciones'.
     */
    @EJB
    PersistenciaVWActualesUbicacionesInterface persistenciaVWActualesUbicaciones;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesFormasPagos'.
     */
    @EJB
    PersistenciaVWActualesFormasPagosInterface persistenciaVWActualesFormasPagos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVWActualesVigenciasViajeros'.
     */
    @EJB
    PersistenciaVWActualesVigenciasViajerosInterface persistenciaVWActualesVigenciasViajeros;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaDetallesEmpresas'.
     */
    @EJB
    PersistenciaDetallesEmpresasInterface persistenciaDetallesEmpresas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaUsuarios'.
     */
    @EJB
    PersistenciaUsuariosInterface persistenciaUsuarios;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaParametrosEstructuras'.
     */
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaVigenciasCargos'.
     */
    @EJB
    PersistenciaVigenciasCargosInterface persistenciaVigenciasCargos;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaPersonas'.
     */
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpleado'.
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia 'persistenciaEmpresas'.
     */
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas; 
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que está usando el aplicativo.
     */
    @EJB
    EntityManagerGlobalInterface entityManagerGlobal;
    /**
     * Atributo que representa el cargo actual de un empleado.
     */
    public VWActualesCargos vwActualesCargos;
    /**
     * Atributo que representa el tipo de contrato actual de un empleado.
     */
    public VWActualesTiposContratos vwActualesTiposContratos;
    /**
     * Atributo que representa la norma actual de un empleado.
     */
    public VWActualesNormasEmpleados vwActualesNormasEmpleados;
    /**
     * Atributo que representa la afiliación a salud actual de un empleado.
     */
    public VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud;
    /**
     * Atributo que representa la afiliación a pensión actual de un empleado.
     */
    public VWActualesAfiliacionesPension vwActualesAfiliacionesPension;
    /**
     * Atributo que representa la localización actual de un empleado.
     */
    public VWActualesLocalizaciones vwActualesLocalizaciones;
    /**
     * Atributo que representa la lista de VWActualesTiposTrabajadores teniendo en cuenta
     * el Tipo de Personal del empleado.
     */
    public List<VWActualesTiposTrabajadores> tipoEmpleadoLista;
    /**
     * Atributo que representa la lista de VWActualesTiposTrabajadores.
     */
    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleado;
    /**
     * Atributo que representa el tipo de trabajador actual de un empleado.
     */
    public VWActualesTiposTrabajadores vwActualesTiposTrabajadores;
    /**
     * Atributo que representa el contrato actual de un empleado.
     */
    public VWActualesContratos vwActualesContratos;
    /**
     * Atributo que representa la jornada actual de un empleado.
     */
    public VWActualesJornadas vwActualesJornadas;
    /**
     * Atributo que representa el sueldo actual de un empleado activo.
     */
    public VWActualesSueldos vwActualesSueldos;
    /**
     * Atributo que representa la pension actual de un empleado pensionado.
     */
    public VWActualesPensiones vwActualesPensiones;
    /**
     * Atributo que representa la reforma laboral actual que afecta a un empleado específico.
     */
    public VWActualesReformasLaborales vwActualesReformasLaborales;
    /**
     * Atributo que representa la ubicación actual de un empleado.
     */
    public VWActualesUbicaciones vWActualesUbicaciones;
    /**
     * Atributo que representa la forma de pago actual para un empleado.
     */
    public VWActualesFormasPagos vwActualesFormasPagos;
    /**
     * Atributo que representa el tipo viajero actual de un empleado.
     */
    public VWActualesVigenciasViajeros vwActualesVigenciasViajeros;
    /**
     * Atributo que representa la informacion detallada de una empresa.
     */
    public DetallesEmpresas detallesEmpresas;
    /**
     * Atributo que representa un usuario.
     */
    public Usuarios usuarios;
    /**
     * Atributo que representa el ParametroEstructura para la pestaña 'PERSONAL'.
     */
    public ParametrosEstructuras parametrosEstructuras;
    /**
     * Atributo que representa las VigenciasCargos de un empleado.
     */
    public List<VigenciasCargos> vigenciasCargos;
    /**
     * Atributo que representa el empleado del cual se mostrará la información.
     */
    public Empleados empleado;
    /**
     * Atributo que representa la persona que esta asociada a un empleado específico.
     */
    public Personas persona;    

    @Override
    public VWActualesCargos ConsultarCargo(BigInteger secuenciaEmpleado) {
        try {
            vwActualesCargos = persistenciaVWActualesCargos.buscarCargoEmpleado(entityManagerGlobal.getEmf().createEntityManager(), secuenciaEmpleado);
            return vwActualesCargos;
        } catch (Exception e) {
            vwActualesCargos = null;
            return vwActualesCargos;
        }
    }

    @Override
    public VWActualesTiposContratos ConsultarTipoContrato(BigInteger secuenciaEmpleado) {
        try {
            vwActualesTiposContratos = persistenciaActualesTiposContratos.buscarTiposContratosEmpleado(secuenciaEmpleado);
            return vwActualesTiposContratos;
        } catch (Exception e) {
            vwActualesTiposContratos = null;
            return vwActualesTiposContratos;
        }
    }

    @Override
    public VWActualesNormasEmpleados ConsultarNormaLaboral(BigInteger secuenciaEmpleado) {
        try {
            vwActualesNormasEmpleados = persistenciaVWActualesNormasEmpleados.buscarNormaLaboral(secuenciaEmpleado);
            return vwActualesNormasEmpleados;
        } catch (Exception e) {
            vwActualesNormasEmpleados = null;
            return vwActualesNormasEmpleados;
        }
    }

    @Override
    public VWActualesAfiliacionesSalud ConsultarAfiliacionSalud(BigInteger secuenciaEmpleado) {
        try {
            vwActualesAfiliacionesSalud = persistenciaVWActualesAfiliacionesSalud.buscarAfiliacionSalud(secuenciaEmpleado);
            return vwActualesAfiliacionesSalud;
        } catch (Exception e) {
            vwActualesAfiliacionesSalud = null;
            return vwActualesAfiliacionesSalud;
        }
    }

    @Override
    public VWActualesAfiliacionesPension ConsultarAfiliacionPension(BigInteger secuenciaEmpleado) {
        try {
            vwActualesAfiliacionesPension = persistenciaVWActualesAfiliacionesPension.buscarAfiliacionPension(secuenciaEmpleado);
            return vwActualesAfiliacionesPension;
        } catch (Exception e) {
            vwActualesAfiliacionesPension = null;
            return vwActualesAfiliacionesPension;
        }
    }

    @Override
    public VWActualesLocalizaciones ConsultarLocalizacion(BigInteger secuenciaEmpleado) {
        try {
            vwActualesLocalizaciones = persistenciaVWActualesLocalizaciones.buscarLocalizacion(secuenciaEmpleado);
            return vwActualesLocalizaciones;
        } catch (Exception e) {
            vwActualesLocalizaciones = null;
            return vwActualesLocalizaciones;
        }
    }

    @Override
    public VWActualesTiposTrabajadores ConsultarTipoTrabajador(BigInteger secuenciaEmpleado) {
        try {
            vwActualesTiposTrabajadores = persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secuenciaEmpleado);
            return vwActualesTiposTrabajadores;
        } catch (Exception e) {
            vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }
    }

    @Override
    public VWActualesContratos ConsultarContrato(BigInteger secuenciaEmpleado) {
        try {
            vwActualesContratos = persistenciaVWActualesContratos.buscarContrato(secuenciaEmpleado);
            return vwActualesContratos;
        } catch (Exception e) {
            vwActualesContratos = null;
            return null;
        }
    }

    @Override
    public VWActualesJornadas ConsultarJornada(BigInteger secuenciaEmpleado) {
        try {
            vwActualesJornadas = persistenciaVWActualesJornadas.buscarJornada(secuenciaEmpleado);
            return vwActualesJornadas;
        } catch (Exception e) {
            vwActualesJornadas = null;
            return vwActualesJornadas;
        }
    }

    @Override
    public BigDecimal ConsultarSueldo(BigInteger secuenciaEmpleado) {
        BigDecimal valor = null;
        try {
            vwActualesTiposTrabajadores = persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secuenciaEmpleado);
            String tipo = vwActualesTiposTrabajadores.getTipoTrabajador().getTipo();

            if (tipo.equalsIgnoreCase("ACTIVO")) {
                valor = persistenciaVWActualesSueldos.buscarSueldoActivo(secuenciaEmpleado);
            } else if (tipo.equalsIgnoreCase("PENSIONADO")) {
                valor = persistenciaVWActualesPensiones.buscarSueldoPensionado(secuenciaEmpleado);
            }
        } catch (Exception e) {
            valor = null;
        }
        return valor;
    }

    @Override
    public VWActualesReformasLaborales ConsultarReformaLaboral(BigInteger secuenciaEmpleado) {
        try {
            vwActualesReformasLaborales = persistenciaVWActualesReformasLaborales.buscarReformaLaboral(secuenciaEmpleado);
            return vwActualesReformasLaborales;
        } catch (Exception e) {
            vwActualesReformasLaborales = null;
            return vwActualesReformasLaborales;
        }
    }

    @Override
    public VWActualesUbicaciones ConsultarUbicacion(BigInteger secuenciaEmpleado) {
        try {
            vWActualesUbicaciones = persistenciaVWActualesUbicaciones.buscarUbicacion(secuenciaEmpleado);
            return vWActualesUbicaciones;
        } catch (Exception e) {
            vWActualesUbicaciones = null;
            return vWActualesUbicaciones;
        }
    }

    @Override
    public VWActualesFormasPagos ConsultarFormaPago(BigInteger secuenciaEmpleado) {
        try {
            vwActualesFormasPagos = persistenciaVWActualesFormasPagos.buscarFormaPago(secuenciaEmpleado);
            return vwActualesFormasPagos;
        } catch (Exception e) {
            vwActualesFormasPagos = null;
            return vwActualesFormasPagos;
        }
    }

    @Override
    public VWActualesVigenciasViajeros ConsultarTipoViajero(BigInteger secuenciaEmpleado) {
        try {
            vwActualesVigenciasViajeros = persistenciaVWActualesVigenciasViajeros.buscarTipoViajero(secuenciaEmpleado);
            return vwActualesVigenciasViajeros;
        } catch (Exception e) {
            vwActualesVigenciasViajeros = null;
            return vwActualesVigenciasViajeros;
        }
    }

    @Override
    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo) {
        try {
            tipoEmpleadoLista = persistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador(tipo);
            return tipoEmpleadoLista;
        } catch (Exception e) {
            tipoEmpleadoLista = null;
            return tipoEmpleadoLista;
        }
    }

    @Override
    public DetallesEmpresas ConsultarEmpresa() {
        try {
            Short codigoEmpresa = persistenciaEmpresas.codigoEmpresa();
            detallesEmpresas = persistenciaDetallesEmpresas.buscarDetalleEmpresa(codigoEmpresa);
            return detallesEmpresas;
        } catch (Exception e) {
            detallesEmpresas = null;
            return detallesEmpresas;
        }
    }

    @Override
    public Usuarios ConsultarUsuario(String alias) {
        try {
            usuarios = persistenciaUsuarios.buscarUsuario(alias);
            return usuarios;
        } catch (Exception e) {
            usuarios = null;
            return usuarios;
        }
    }

    @Override
    public ParametrosEstructuras ConsultarParametros() {
        try {
            parametrosEstructuras = persistenciaParametrosEstructuras.buscarParametro(actualUsuario());
            return parametrosEstructuras;
        } catch (Exception e) {
            parametrosEstructuras = null;
            return parametrosEstructuras;
        }
    }

    @Override
    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasCargos = persistenciaVigenciasCargos.buscarVigenciasCargosEmpleado(secEmpleado);
            return vigenciasCargos;
        } catch (Exception e) {
            vigenciasCargos = null;
            return vigenciasCargos;
        }
    }

    @Override
    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleados() {
        try {
            busquedaRapidaEmpleado = persistenciaVWActualesTiposTrabajadores.busquedaRapidaTrabajadores();
            return busquedaRapidaEmpleado;
        } catch (Exception e) {
            busquedaRapidaEmpleado = null;
            return busquedaRapidaEmpleado;
        }
    }

    @Override
    public Personas buscarFotoPersona(BigInteger identificacion) {
        try {
            persona = persistenciaPersonas.buscarFotoPersona(identificacion);
            return persona;
        } catch (Exception e) {
            persona = null;
            return persona;
        }
    }

    @Override
    public void actualizarFotoPersona(BigInteger identificacion) {
        try {
            persistenciaPersonas.actualizarFotoPersona(identificacion);
        } catch (Exception e) {
            System.out.println("No se puede actalizar el estado de la Foto.");
        }
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    @Override
    public void editarVigenciasCargos(VigenciasCargos vC) {
        try {
            persistenciaVigenciasCargos.editar(vC);
        } catch (Exception e) {
            System.out.println("Excepcion Administrar - No Se Guardo Nada ¬¬");
        }
    }

    @Override
    public String actualUsuario() {
        return persistenciaActualUsuario.actualAliasBD();
    }
}

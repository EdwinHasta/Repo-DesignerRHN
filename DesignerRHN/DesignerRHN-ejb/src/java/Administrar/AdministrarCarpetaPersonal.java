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
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------    
    @Override
    public VWActualesCargos consultarActualCargoEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesCargos vwActualesCargos = persistenciaVWActualesCargos.buscarCargoEmpleado(entityManagerGlobal.getEmf().createEntityManager(), secEmpleado);
            return vwActualesCargos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesTiposContratos consultarActualTipoContratoEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesTiposContratos vwActualesTiposContratos = persistenciaActualesTiposContratos.buscarTiposContratosEmpleado(secEmpleado);
            return vwActualesTiposContratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesNormasEmpleados consultarActualNormaLaboralEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesNormasEmpleados vwActualesNormasEmpleados = persistenciaVWActualesNormasEmpleados.buscarNormaLaboral(secEmpleado);
            return vwActualesNormasEmpleados;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesAfiliacionesSalud consultarActualAfiliacionSaludEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud = persistenciaVWActualesAfiliacionesSalud.buscarAfiliacionSalud(secEmpleado);
            return vwActualesAfiliacionesSalud;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesAfiliacionesPension consultarActualAfiliacionPensionEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesAfiliacionesPension vwActualesAfiliacionesPension = persistenciaVWActualesAfiliacionesPension.buscarAfiliacionPension(secEmpleado);
            return vwActualesAfiliacionesPension;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesLocalizaciones consultarActualLocalizacionEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesLocalizaciones vwActualesLocalizaciones = persistenciaVWActualesLocalizaciones.buscarLocalizacion(secEmpleado);
            return vwActualesLocalizaciones;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesTiposTrabajadores consultarActualTipoTrabajadorEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secEmpleado);
            return vwActualesTiposTrabajadores;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesContratos consultarActualContratoEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesContratos vwActualesContratos = persistenciaVWActualesContratos.buscarContrato(secEmpleado);
            return vwActualesContratos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesJornadas consultarActualJornadaEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesJornadas vwActualesJornadas = persistenciaVWActualesJornadas.buscarJornada(secEmpleado);
            return vwActualesJornadas;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigDecimal consultarActualSueldoEmpleado(BigInteger secEmpleado) {
        BigDecimal valor = null;
        try {
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secEmpleado);
            String tipo = vwActualesTiposTrabajadores.getTipoTrabajador().getTipo();

            if (tipo.equalsIgnoreCase("ACTIVO")) {
                valor = persistenciaVWActualesSueldos.buscarSueldoActivo(secEmpleado);
            } else if (tipo.equalsIgnoreCase("PENSIONADO")) {
                valor = persistenciaVWActualesPensiones.buscarSueldoPensionado(secEmpleado);
            }
        } catch (Exception e) {
            valor = null;
        }
        return valor;
    }

    @Override
    public VWActualesReformasLaborales consultarActualReformaLaboralEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesReformasLaborales vwActualesReformasLaborales = persistenciaVWActualesReformasLaborales.buscarReformaLaboral(secEmpleado);
            return vwActualesReformasLaborales;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesUbicaciones consultarActualUbicacionEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesUbicaciones vWActualesUbicaciones = persistenciaVWActualesUbicaciones.buscarUbicacion(secEmpleado);
            return vWActualesUbicaciones;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesFormasPagos consultarActualFormaPagoEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesFormasPagos vwActualesFormasPagos = persistenciaVWActualesFormasPagos.buscarFormaPago(secEmpleado);
            return vwActualesFormasPagos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public VWActualesVigenciasViajeros consultarActualTipoViajeroEmpleado(BigInteger secEmpleado) {
        try {
            VWActualesVigenciasViajeros vwActualesVigenciasViajeros = persistenciaVWActualesVigenciasViajeros.buscarTipoViajero(secEmpleado);
            return vwActualesVigenciasViajeros;
        } catch (Exception e) {
            return null;
        }
    }

    public List<VWActualesTiposTrabajadores> consultarEmpleadosTipoTrabajador(String tipo) {
        try {
            List<VWActualesTiposTrabajadores> tipoEmpleadoLista = persistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador(tipo);
            return tipoEmpleadoLista;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public DetallesEmpresas consultarDetalleEmpresaUsuario() {
        try {
            Short codigoEmpresa = persistenciaEmpresas.codigoEmpresa();
            DetallesEmpresas detallesEmpresas = persistenciaDetallesEmpresas.buscarDetalleEmpresa(codigoEmpresa);
            return detallesEmpresas;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Usuarios consultarUsuario(String alias) {
        try {
            Usuarios usuarios = persistenciaUsuarios.buscarUsuario(alias);
            return usuarios;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParametrosEstructuras consultarParametrosUsuario() {
        try {
            ParametrosEstructuras parametrosEstructuras = persistenciaParametrosEstructuras.buscarParametro(consultarAliasActualUsuario());
            return parametrosEstructuras;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasCargos> consultarVigenciasCargosEmpleado(BigInteger secEmpleado) {
        try {
            List<VigenciasCargos> vigenciasCargos = persistenciaVigenciasCargos.buscarVigenciasCargosEmpleado(secEmpleado);
            return vigenciasCargos;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VWActualesTiposTrabajadores> consultarRapidaEmpleados() {
        try {
            List<VWActualesTiposTrabajadores> busquedaRapidaEmpleado = persistenciaVWActualesTiposTrabajadores.busquedaRapidaTrabajadores();
            return busquedaRapidaEmpleado;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Personas consultarFotoPersona(BigInteger identificacion) {
        try {
            Personas persona = persistenciaPersonas.buscarFotoPersona(identificacion);
            return persona;
        } catch (Exception e) {
            return null;
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
    public Empleados consultarEmpleado(BigInteger secuencia) {
        try {
            Empleados empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            Empleados empleado = null;
            return empleado;
        }
    }

    @Override
    public void editarVigenciasCargos(List<VigenciasCargos> vigenciasCargos) {
        try {
            for (int i = 0; i < vigenciasCargos.size(); i++) {
                persistenciaVigenciasCargos.editar(vigenciasCargos.get(i));
            }
        } catch (Exception e) {
            System.out.println("Excepcion Administrar - No Se Guardo Nada ¬¬");
        }
    }

    @Override
    public String consultarAliasActualUsuario() {
        return persistenciaActualUsuario.actualAliasBD();
    }
}

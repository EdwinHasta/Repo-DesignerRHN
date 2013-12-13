package Administrar;

import Entidades.*;
import InterfaceAdministrar.AdministrarCarpetaPersonalInterface;
import InterfacePersistencia.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrator
 */
@Stateful
public class AdministrarCarpetaPersonal implements AdministrarCarpetaPersonalInterface {

    @EJB
    PersistenciaVWActualesCargosInterface persistenciaVWActualesCargos;
    @EJB
    PersistenciaVWActualesTiposContratosInterface persistenciaActualesTiposContratos;
    @EJB
    PersistenciaVWActualesNormasEmpleadosInterface persistenciaVWActualesNormasEmpleados;
    @EJB
    PersistenciaVWActualesAfiliacionesSaludInterface persistenciaVWActualesAfiliacionesSalud;
    @EJB
    PersistenciaVWActualesAfiliacionesPensionInterface persistenciaVWActualesAfiliacionesPension;
    @EJB
    PersistenciaVWActualesLocalizacionesInterface persistenciaVWActualesLocalizaciones;
    @EJB
    PersistenciaVigenciasTiposTrabajadoresInterface persistenciaVigenciasTiposTrabajadores;
    @EJB
    PersistenciaVWActualesTiposTrabajadoresInterface persistenciaVWActualesTiposTrabajadores;
    @EJB
    PersistenciaVWActualesContratosInterface persistenciaVWActualesContratos;
    @EJB
    PersistenciaVWActualesJornadasInterface persistenciaVWActualesJornadas;
    @EJB
    PersistenciaVWActualesSueldosInterface persistenciaVWActualesSueldos;
    @EJB
    PersistenciaVWActualesPensionesInterface persistenciaVWActualesPensiones;
    @EJB
    PersistenciaVWActualesReformasLaboralesInterface persistenciaVWActualesReformasLaborales;
    @EJB
    PersistenciaVWActualesUbicacionesInterface persistenciaVWActualesUbicaciones;
    @EJB
    PersistenciaVWActualesFormasPagosInterface persistenciaVWActualesFormasPagos;
    @EJB
    PersistenciaVWActualesVigenciasViajerosInterface persistenciaVWActualesVigenciasViajeros;
    @EJB
    PersistenciaDetallesEmpresasInterface persistenciaDetallesEmpresas;
    @EJB
    PersistenciaUsuariosInterface persistenciaUsuarios;
    @EJB
    PersistenciaParametrosEstructurasInterface persistenciaParametrosEstructuras;
    @EJB
    PersistenciaVigenciasCargosInterface persistenciaVigenciasCargos;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB 
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    EntityManagerGlobalInterface entityManagerGlobal;
    
    public VWActualesCargos vwActualesCargos;
    public VWActualesTiposContratos vwActualesTiposContratos;
    public VWActualesNormasEmpleados vwActualesNormasEmpleados;
    public VWActualesAfiliacionesSalud vwActualesAfiliacionesSalud;
    public VWActualesAfiliacionesPension vwActualesAfiliacionesPension;
    public VWActualesLocalizaciones vwActualesLocalizaciones;
    public List<VWActualesTiposTrabajadores> tipoEmpleadoLista;
    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleado;
    public VWActualesTiposTrabajadores vwActualesTiposTrabajadores;
    public VWActualesContratos vwActualesContratos;
    public VWActualesJornadas vwActualesJornadas;
    public VWActualesSueldos vwActualesSueldos;
    public VWActualesPensiones vwActualesPensiones;
    public VWActualesReformasLaborales vwActualesReformasLaborales;
    public VWActualesUbicaciones vWActualesUbicaciones;
    public VWActualesFormasPagos vwActualesFormasPagos;
    public VWActualesVigenciasViajeros vwActualesVigenciasViajeros;
    public DetallesEmpresas detallesEmpresas;
    public Usuarios usuarios;
    public ParametrosEstructuras parametrosEstructuras;
    public List<VigenciasCargos> vigenciasCargos;
    public Empleados empleado;
    public Personas persona;
    private EntityManager em;

    
    public VWActualesCargos ConsultarCargo(BigInteger secuenciaEmpleado) {
        try {
            vwActualesCargos = persistenciaVWActualesCargos.buscarCargoEmpleado(entityManagerGlobal.getEmf().createEntityManager(), secuenciaEmpleado);
            return vwActualesCargos;
        } catch (Exception e) {
            vwActualesCargos = null;
            return vwActualesCargos;
        }
    }

    public VWActualesTiposContratos ConsultarTipoContrato(BigInteger secuenciaEmpleado) {
        try {
            vwActualesTiposContratos = persistenciaActualesTiposContratos.buscarTiposContratosEmpleado(secuenciaEmpleado);
            return vwActualesTiposContratos;
        } catch (Exception e) {
            vwActualesTiposContratos = null;
            return vwActualesTiposContratos;
        }

    }

    public VWActualesNormasEmpleados ConsultarNormaLaboral(BigInteger secuenciaEmpleado) {
        try {
            vwActualesNormasEmpleados = persistenciaVWActualesNormasEmpleados.buscarNormaLaboral(secuenciaEmpleado);
            return vwActualesNormasEmpleados;
        } catch (Exception e) {
            vwActualesNormasEmpleados = null;
            return vwActualesNormasEmpleados;
        }

    }

    public VWActualesAfiliacionesSalud ConsultarAfiliacionSalud(BigInteger secuenciaEmpleado) {
        try {
            vwActualesAfiliacionesSalud = persistenciaVWActualesAfiliacionesSalud.buscarAfiliacionSalud(secuenciaEmpleado);
            return vwActualesAfiliacionesSalud;
        } catch (Exception e) {
            vwActualesAfiliacionesSalud = null;
            return vwActualesAfiliacionesSalud;
        }

    }

    public VWActualesAfiliacionesPension ConsultarAfiliacionPension(BigInteger secuenciaEmpleado) {
        try {
            vwActualesAfiliacionesPension = persistenciaVWActualesAfiliacionesPension.buscarAfiliacionPension(secuenciaEmpleado);
            return vwActualesAfiliacionesPension;
        } catch (Exception e) {
            vwActualesAfiliacionesPension = null;
            return vwActualesAfiliacionesPension;
        }

    }

    public VWActualesLocalizaciones ConsultarLocalizacion(BigInteger secuenciaEmpleado) {
        try {
            vwActualesLocalizaciones = persistenciaVWActualesLocalizaciones.buscarLocalizacion(secuenciaEmpleado);
            return vwActualesLocalizaciones;
        } catch (Exception e) {
            vwActualesLocalizaciones = null;
            return vwActualesLocalizaciones;
        }

    }

    public VWActualesTiposTrabajadores ConsultarTipoTrabajador(BigInteger secuenciaEmpleado) {

        try {
            vwActualesTiposTrabajadores = persistenciaVWActualesTiposTrabajadores.buscarTipoTrabajador(secuenciaEmpleado);
            return vwActualesTiposTrabajadores;
        } catch (Exception e) {
            vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }

    }

    public VWActualesContratos ConsultarContrato(BigInteger secuenciaEmpleado) {

        try {
            vwActualesContratos = persistenciaVWActualesContratos.buscarContrato(secuenciaEmpleado);
            return vwActualesContratos;
        } catch (Exception e) {
            vwActualesContratos = null;
            return null;
        }

    }

    public VWActualesJornadas ConsultarJornada(BigInteger secuenciaEmpleado) {

        try {
            vwActualesJornadas = persistenciaVWActualesJornadas.buscarJornada(secuenciaEmpleado);
            return vwActualesJornadas;
        } catch (Exception e) {
            vwActualesJornadas = null;
            return vwActualesJornadas;
        }

    }

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

    public VWActualesReformasLaborales ConsultarReformaLaboral(BigInteger secuenciaEmpleado) {
        try {
            vwActualesReformasLaborales = persistenciaVWActualesReformasLaborales.buscarReformaLaboral(secuenciaEmpleado);
            return vwActualesReformasLaborales;
        } catch (Exception e) {
            vwActualesReformasLaborales = null;
            return vwActualesReformasLaborales;
        }
    }

    public VWActualesUbicaciones ConsultarUbicacion(BigInteger secuenciaEmpleado) {
        try {
            vWActualesUbicaciones = persistenciaVWActualesUbicaciones.buscarUbicacion(secuenciaEmpleado);
            return vWActualesUbicaciones;
        } catch (Exception e) {
            vWActualesUbicaciones = null;
            return vWActualesUbicaciones;
        }
    }

    public VWActualesFormasPagos ConsultarFormaPago(BigInteger secuenciaEmpleado) {
        try {
            vwActualesFormasPagos = persistenciaVWActualesFormasPagos.buscarFormaPago(secuenciaEmpleado);
            return vwActualesFormasPagos;
        } catch (Exception e) {
            vwActualesFormasPagos = null;
            return vwActualesFormasPagos;
        }
    }

    public VWActualesVigenciasViajeros ConsultarTipoViajero(BigInteger secuenciaEmpleado) {
        try {
            vwActualesVigenciasViajeros = persistenciaVWActualesVigenciasViajeros.buscarTipoViajero(secuenciaEmpleado);
            return vwActualesVigenciasViajeros;
        } catch (Exception e) {
            vwActualesVigenciasViajeros = null;
            return vwActualesVigenciasViajeros;
        }
    }

    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String tipo) {

        try {
            tipoEmpleadoLista = persistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador(tipo);
            return tipoEmpleadoLista;
        } catch (Exception e) {
            tipoEmpleadoLista = null;
            return tipoEmpleadoLista;
        }

    }

    public DetallesEmpresas ConsultarEmpresa() {
        try {
            detallesEmpresas = persistenciaDetallesEmpresas.buscarDetalleEmpresa(empleado.getEmpresa().getSecuencia());
            return detallesEmpresas;
        } catch (Exception e) {
            detallesEmpresas = null;
            return detallesEmpresas;
        }
    }

    public Usuarios ConsultarUsuario(String alias) {
        try {
            usuarios = persistenciaUsuarios.buscarUsuario(alias);
            return usuarios;
        } catch (Exception e) {
            usuarios = null;
            return usuarios;
        }
    }

    public ParametrosEstructuras ConsultarParametros() {
        try {
            parametrosEstructuras = persistenciaParametrosEstructuras.buscarParametro(actualUsuario());
            return parametrosEstructuras;
        } catch (Exception e) {
            parametrosEstructuras = null;
            return parametrosEstructuras;
        }
    }

    public List<VigenciasCargos> vigenciasEmpleado(BigInteger secEmpleado) {
        try {
            vigenciasCargos = persistenciaVigenciasCargos.buscarVigenciaCargoEmpleado(secEmpleado);
            return vigenciasCargos;
        } catch (Exception e) {
            vigenciasCargos = null;
            return vigenciasCargos;
        }


    }

    public List<VWActualesTiposTrabajadores> busquedaRapidaEmpleados() {
        try {
            busquedaRapidaEmpleado = persistenciaVWActualesTiposTrabajadores.busquedaRapidaTrabajadores();
            return busquedaRapidaEmpleado;
        } catch (Exception e) {
            busquedaRapidaEmpleado = null;
            return busquedaRapidaEmpleado;
        }
    }

    public Personas buscarFotoPersona(BigInteger identificacion) {
        try {
            persona = persistenciaPersonas.buscarFotoPersona(identificacion);
            return persona;
        } catch (Exception e) {
            persona = null;
            return persona;
        }
    }

    public void actualizarFotoPersona(BigInteger identificacion) {
        try {
            persistenciaPersonas.actualizarFotoPersona(identificacion);
        } catch (Exception e) {
            System.out.println("No se puede actalizar el estado de la Foto.");
        }
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    public void editarVigenciasCargos(VigenciasCargos vC) {
        try {
            persistenciaVigenciasCargos.editar(vC);
        } catch (Exception e) {
            System.out.println("Excepcion Administrar - No Se Guardo Nada ¬¬");
        }
    }
    
    public String actualUsuario(){
        return persistenciaActualUsuario.actualAliasBD();
    }
    
}

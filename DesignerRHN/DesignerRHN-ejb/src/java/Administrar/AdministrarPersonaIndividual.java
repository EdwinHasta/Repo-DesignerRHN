/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.Comprobantes;
import Entidades.Contratos;
import Entidades.CortesProcesos;
import Entidades.Direcciones;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.EstadosCiviles;
import Entidades.Estructuras;
import Entidades.JornadasLaborales;
import Entidades.MetodosPagos;
import Entidades.MotivosCambiosCargos;
import Entidades.MotivosCambiosSueldos;
import Entidades.MotivosContratos;
import Entidades.MotivosLocalizaciones;
import Entidades.NormasLaborales;
import Entidades.Papeles;
import Entidades.Periodicidades;
import Entidades.Personas;
import Entidades.Procesos;
import Entidades.ReformasLaborales;
import Entidades.Sets;
import Entidades.Sucursales;
import Entidades.Telefonos;
import Entidades.TercerosSucursales;
import Entidades.TiposContratos;
import Entidades.TiposDocumentos;
import Entidades.TiposEntidades;
import Entidades.TiposSueldos;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.Unidades;
import Entidades.VWValidaBancos;
import Entidades.VigenciasAfiliaciones;
import Entidades.VigenciasCargos;
import Entidades.VigenciasContratos;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasFormasPagos;
import Entidades.VigenciasJornadas;
import Entidades.VigenciasLocalizaciones;
import Entidades.VigenciasNormasEmpleados;
import Entidades.VigenciasReformasLaborales;
import Entidades.VigenciasSueldos;
import Entidades.VigenciasTiposContratos;
import Entidades.VigenciasTiposTrabajadores;
import Entidades.VigenciasUbicaciones;
import InterfaceAdministrar.AdministrarPersonaIndividualInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaComprobantesInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaCortesProcesosInterface;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import InterfacePersistencia.PersistenciaJornadasLaboralesInterface;
import InterfacePersistencia.PersistenciaMetodosPagosInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosCargosInterface;
import InterfacePersistencia.PersistenciaMotivosCambiosSueldosInterface;
import InterfacePersistencia.PersistenciaMotivosContratosInterface;
import InterfacePersistencia.PersistenciaMotivosLocalizacionesInterface;
import InterfacePersistencia.PersistenciaNormasLaboralesInterface;
import InterfacePersistencia.PersistenciaPapelesInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaProcesosInterface;
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaSetsInterface;
import InterfacePersistencia.PersistenciaSucursalesInterface;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaTiposSueldosInterface;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import InterfacePersistencia.PersistenciaUnidadesInterface;
import InterfacePersistencia.PersistenciaVWValidaBancosInterface;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasCargosInterface;
import InterfacePersistencia.PersistenciaVigenciasContratosInterface;
import InterfacePersistencia.PersistenciaVigenciasEstadosCivilesInterface;
import InterfacePersistencia.PersistenciaVigenciasFormasPagosInterface;
import InterfacePersistencia.PersistenciaVigenciasJornadasInterface;
import InterfacePersistencia.PersistenciaVigenciasLocalizacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasNormasEmpleadosInterface;
import InterfacePersistencia.PersistenciaVigenciasReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaVigenciasSueldosInterface;
import InterfacePersistencia.PersistenciaVigenciasTiposContratosInterface;
import InterfacePersistencia.PersistenciaVigenciasTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaVigenciasUbicacionesInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
@Stateful
public class AdministrarPersonaIndividual implements AdministrarPersonaIndividualInterface {

    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaTiposDocumentosInterface persistenciaTiposDocumentos;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaMotivosCambiosCargosInterface persistenciaMotivosCambiosCargos;
    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;
    @EJB
    PersistenciaMotivosLocalizacionesInterface persistenciaMotivosLocalizaciones;
    @EJB
    PersistenciaUbicacionesGeograficasInterface persistenciaUbicacionesGeograficas;
    @EJB
    PersistenciaJornadasLaboralesInterface persistenciaJornadasLaborales;
    @EJB
    PersistenciaMotivosContratosInterface persistenciaMotivosContratos;
    @EJB
    PersistenciaTiposContratosInterface persistenciaTiposContratos;
    @EJB
    PersistenciaTiposTrabajadoresInterface persistenciaTiposTrabajadores;
    @EJB
    PersistenciaReformasLaboralesInterface persistenciaReformasLaborales;
    @EJB
    PersistenciaNormasLaboralesInterface persistenciaNormasLaborales;
    @EJB
    PersistenciaContratosInterface persistenciaContratos;
    @EJB
    PersistenciaMotivosCambiosSueldosInterface persistenciaMotivosCambiosSueldos;
    @EJB
    PersistenciaTiposSueldosInterface persistenciaTiposSueldos;
    @EJB
    PersistenciaUnidadesInterface persistenciaUnidades;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    @EJB
    PersistenciaSucursalesInterface persistenciaSucursales;
    @EJB
    PersistenciaMetodosPagosInterface persistenciaMetodosPagos;
    @EJB
    PersistenciaTercerosSucursalesInterface persistenciaTercerosSucursales;
    @EJB
    PersistenciaEstadosCivilesInterface persistenciaEstadosCiviles;
    @EJB
    PersistenciaTiposTelefonosInterface persistenciaTiposTelefonos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaPapelesInterface persistenciaPapeles;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaGeneralesInterface persistenciaGenerales;
    @EJB
    PersistenciaVWValidaBancosInterface persistenciaVWValidaBancos;
    @EJB
    PersistenciaVigenciasCargosInterface persistenciaVigenciasCargos;
    @EJB
    PersistenciaVigenciasLocalizacionesInterface persistenciaVigenciasLocalizaciones;
    @EJB
    PersistenciaVigenciasTiposTrabajadoresInterface persistenciaVigenciasTiposTrabajadores;
    @EJB
    PersistenciaVigenciasReformasLaboralesInterface persistenciaVigenciasReformasLaborales;
    @EJB
    PersistenciaVigenciasSueldosInterface persistenciaVigenciasSueldos;
    @EJB
    PersistenciaVigenciasTiposContratosInterface persistenciaVigenciasTiposContratos;
    @EJB
    PersistenciaVigenciasNormasEmpleadosInterface persistenciaVigenciasNormasEmpleados;
    @EJB
    PersistenciaVigenciasContratosInterface persistenciaVigenciasContratos;
    @EJB
    PersistenciaVigenciasUbicacionesInterface persistenciaVigenciasUbicaciones;
    @EJB
    PersistenciaVigenciasJornadasInterface persistenciaVigenciasJornadas;
    @EJB
    PersistenciaVigenciasFormasPagosInterface persistenciaVigenciasFormasPagos;
    @EJB
    PersistenciaDireccionesInterface persistenciaDirecciones;
    @EJB
    PersistenciaTelefonosInterface persistenciaTelefonos;
    @EJB
    PersistenciaSetsInterface persistenciaSets;
    @EJB
    PersistenciaProcesosInterface persistenciaProcesos;
    @EJB
    PersistenciaComprobantesInterface persistenciaComprobantes;
    @EJB
    PersistenciaCortesProcesosInterface persistenciaCortesProcesos;
    @EJB
    PersistenciaVigenciasEstadosCivilesInterface persistenciaVigenciasEstadosCiviles;
    @EJB
    PersistenciaVigenciasAfiliacionesInterface persistenciaVigenciasAfiliaciones;

    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }

    public List<Empresas> lovEmpresas() {
        try {
            List<Empresas> lista = persistenciaEmpresas.buscarEmpresas(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpresas AdministrarPersonaIndividualPersona : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposDocumentos> lovTiposDocumentos() {
        try {
            List<TiposDocumentos> lista = persistenciaTiposDocumentos.consultarTiposDocumentos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposDocumentos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Ciudades> lovCiudades() {
        try {
            List<Ciudades> lista = persistenciaCiudades.ciudades(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCiudades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Cargos> lovCargos() {
        try {
            List<Cargos> lista = persistenciaCargos.consultarCargos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MotivosCambiosCargos> lovMotivosCambiosCargos() {
        try {
            List<MotivosCambiosCargos> lista = persistenciaMotivosCambiosCargos.buscarMotivosCambiosCargos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMotivosCambiosCargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> lovEstructurasModCargos(BigInteger secEmpresa, Date fechaIngreso) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorEmpresaFechaIngreso(em, secEmpresa, fechaIngreso);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructurasModCargos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Estructuras> lovEstructurasModCentroCosto(BigInteger secEmpresa) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorEmpresa(em, secEmpresa);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructurasModCentroCosto Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MotivosLocalizaciones> lovMotivosLocalizaciones() {
        try {
            List<MotivosLocalizaciones> lista = persistenciaMotivosLocalizaciones.buscarMotivosLocalizaciones(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMotivosLocalizaciones Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<UbicacionesGeograficas> lovUbicacionesGeograficas(BigInteger secuencia) {
        try {
            List<UbicacionesGeograficas> lista = persistenciaUbicacionesGeograficas.consultarUbicacionesGeograficasPorEmpresa(em, secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovUbicacionesGeograficas Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<JornadasLaborales> lovJornadasLaborales() {
        try {
            List<JornadasLaborales> lista = persistenciaJornadasLaborales.buscarJornadasLaborales(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovJornadasLaborales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MotivosContratos> lovMotivosContratos() {
        try {
            List<MotivosContratos> lista = persistenciaMotivosContratos.buscarMotivosContratos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMotivosContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposContratos> lovTiposContratos() {
        try {
            List<TiposContratos> lista = persistenciaTiposContratos.tiposContratos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposTrabajadores> lovTiposTrabajadores() {
        try {
            List<TiposTrabajadores> lista = persistenciaTiposTrabajadores.buscarTiposTrabajadores(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposTrabajadores Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ReformasLaborales> lovReformasLaborales() {
        try {
            List<ReformasLaborales> lista = persistenciaReformasLaborales.buscarReformasLaborales(em);
            return lista;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NormasLaborales> lovNormasLaborales() {
        try {
            List<NormasLaborales> lista = persistenciaNormasLaborales.consultarNormasLaborales(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovNormasLaborales Admi : " + e.toString());
            return null;
        }
    }

    ///@Override
    public List<Contratos> lovContratos() {
        try {
            List<Contratos> lista = persistenciaContratos.buscarContratosPorUsuario(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovContratos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MotivosCambiosSueldos> lovMotivosCambiosSueldos() {
        try {
            List<MotivosCambiosSueldos> lista = persistenciaMotivosCambiosSueldos.buscarMotivosCambiosSueldos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMotivosCambiosSueldos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposSueldos> lovTiposSueldos() {
        try {
            List<TiposSueldos> lista = persistenciaTiposSueldos.buscarTiposSueldosParaUsuarioConectado(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposSueldos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Unidades> lovUnidades() {
        try {
            List<Unidades> lista = persistenciaUnidades.consultarUnidades(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovUnidades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Periodicidades> lovPeriodicidades() {
        try {
            List<Periodicidades> lista = persistenciaPeriodicidades.consultarPeriodicidades(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovPeriodicidades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Sucursales> lovSucursales() {
        try {
            List<Sucursales> lista = persistenciaSucursales.consultarSucursales(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovSucursales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<MetodosPagos> lovMetodosPagos() {
        try {
            List<MetodosPagos> lista = persistenciaMetodosPagos.buscarMetodosPagos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovMetodosPagos Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public List<TercerosSucursales> lovTercerosSucursales(BigInteger secuencia) {
        try {
            List<TercerosSucursales> lista = persistenciaTercerosSucursales.buscarTercerosSucursalesPorEmpresa(em, secuencia);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTercerosSucursales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadosCiviles> lovEstadosCiviles() {
        try {
            List<EstadosCiviles> lista = persistenciaEstadosCiviles.consultarEstadosCiviles(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstadosCiviles Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposTelefonos> lovTiposTelefonos() {
        try {
            List<TiposTelefonos> lista = persistenciaTiposTelefonos.tiposTelefonos(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovTiposTelefonos Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Empleados> lovEmpleados() {
        try {
            List<Empleados> lista = persistenciaEmpleado.buscarEmpleados(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpleados Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void crearPersona(Personas persona) {
        try {
            persistenciaPersonas.crear(em, persona);
        } catch (Exception e) {
            System.out.println("Error crearPersona Admi : " + e.toString());
        }
    }

    @Override
    public List<Papeles> lovPapeles() {
        try {
            List<Papeles> lista = persistenciaPapeles.consultarPapeles(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovPapeles Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public TiposEntidades buscarTipoEntidadPorCodigo(Short codigo) {
        try {
            TiposEntidades entidad = persistenciaTiposEntidades.buscarTipoEntidadPorCodigo(em, codigo);
            return entidad;
        } catch (Exception e) {
            System.out.println("Error buscarTipoEntidadPorCodigo Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String buscarCodigoSSTercero(BigInteger secuencia) {
        try {
            String codigo = persistenciaTerceros.buscarCodigoSSPorSecuenciaTercero(em, secuencia);
            return codigo;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSSTercero Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String buscarCodigoSPTercero(BigInteger secuencia) {
        try {
            String codigo = persistenciaTerceros.buscarCodigoSPPorSecuenciaTercero(em, secuencia);
            return codigo;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSPTercero Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String buscarCodigoSCTercero(BigInteger secuencia) {
        try {
            String codigo = persistenciaTerceros.buscarCodigoSCPorSecuenciaTercero(em, secuencia);
            return codigo;
        } catch (Exception e) {
            System.out.println("Error buscarCodigoSCTercero Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public BigInteger calcularNumeroEmpleadosEmpresa(BigInteger secuencia) {
        try {
            BigInteger valor = persistenciaEmpresas.calcularControlEmpleadosEmpresa(em, secuencia);
            return valor;
        } catch (Exception e) {
            System.out.println("Error calcularNumeroEmpleadosEmpresa Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public BigInteger obtenerMaximoEmpleadosEmpresa(BigInteger secuencia) {
        try {
            BigInteger valor = persistenciaEmpresas.obtenerMaximoEmpleadosEmpresa(em, secuencia);
            return valor;
        } catch (Exception e) {
            System.out.println("Error obtenerMaximoEmpleadosEmpresa Admi : " + e.toString());
            return null;
        }
    }

    public Empleados buscarEmpleadoPorCodigoyEmpresa(BigInteger codigo, BigInteger empresa) {
        try {
            Empleados empl = persistenciaEmpleado.buscarEmpleadoPorCodigoyEmpresa(em, codigo, empresa);
            return empl;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleadoPorCodigoyEmpresa Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Personas buscarPersonaPorNumeroDocumento(BigInteger numeroDocumento) {
        try {
            Personas persona = persistenciaPersonas.buscarPersonaPorNumeroDocumento(em, numeroDocumento);
            return persona;
        } catch (Exception e) {
            System.out.println("Error buscarPersonaPorNumeroDocumento Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String obtenerPreValidadContabilidad() {
        try {
            String variable = persistenciaGenerales.obtenerPreValidadContabilidad(em);
            return variable;
        } catch (Exception e) {
            System.out.println("Error obtenerPreValidadContabilidad Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public String obtenerPreValidaBloqueAIngreso() {
        try {
            String variable = persistenciaGenerales.obtenerPreValidaBloqueAIngreso(em);
            return variable;
        } catch (Exception e) {
            System.out.println("Error obtenerPreValidaBloqueAIngreso Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public VWValidaBancos validarCodigoPrimarioVWValidaBancos(BigInteger documento) {
        try {
            VWValidaBancos valida = persistenciaVWValidaBancos.validarDocumentoVWValidaBancos(em, documento);
            return valida;
        } catch (Exception e) {
            System.out.println("Error validarCodigoPrimarioVWValidaBancos Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String validarTipoTrabajadorReformaLaboral(BigInteger tipoTrabajador, BigInteger reformaLaboral) {
        try {
            String validar = persistenciaTiposTrabajadores.plantillaValidarTipoTrabajadorReformaLaboral(em, tipoTrabajador, reformaLaboral);
            return validar;
        } catch (Exception e) {
            System.out.println("Error validarTipoTrabajadorReformaLaboral Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String validarTipoTrabajadorTipoSueldo(BigInteger tipoTrabajador, BigInteger tipoSueldo) {
        try {
            String validar = persistenciaTiposTrabajadores.plantillaValidarTipoTrabajadorTipoSueldo(em, tipoTrabajador, tipoSueldo);
            return validar;
        } catch (Exception e) {
            System.out.println("Error validarTipoTrabajadorTipoSueldo Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String validarTipoTrabajadorTipoContrato(BigInteger tipoTrabajador, BigInteger tipoContrato) {
        try {
            String validar = persistenciaTiposTrabajadores.plantillaValidarTipoTrabajadorTipoContrato(em, tipoTrabajador, tipoContrato);
            return validar;
        } catch (Exception e) {
            System.out.println("Error validarTipoTrabajadorTipoContrato Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String validarTipoTrabajadorNormaLaboral(BigInteger tipoTrabajador, BigInteger normaLaboral) {
        try {
            String validar = persistenciaTiposTrabajadores.plantillaValidarTipoTrabajadorTipoSueldo(em, tipoTrabajador, normaLaboral);
            return validar;
        } catch (Exception e) {
            System.out.println("Error validarTipoTrabajadorNormaLaboral Admi : " + e.toString());
            return null;
        }
    }

    //@Override
    public String validarTipoTrabajadorContrato(BigInteger tipoTrabajador, BigInteger contrato) {
        try {
            String validar = persistenciaTiposTrabajadores.plantillaValidarTipoTrabajadorTipoContrato(em, tipoTrabajador, contrato);
            return validar;
        } catch (Exception e) {
            System.out.println("Error validarTipoTrabajadorContrato Admi : " + e.toString());
            return null;
        }
    }

    //Override
    public String obtenerCheckIntegralReformaLaboral(BigInteger reformaLaboral) {
        try {
            String variable = persistenciaReformasLaborales.obtenerCheckIntegralReformaLaboral(em, reformaLaboral);
            return variable;
        } catch (Exception e) {
            System.out.println("Error obtenerCheckIntegralReformaLaboral Admi : " + e.toString());
            return "N";
        }

    }

    public void crearNuevaPersona(Personas persona) {
        try {
            persistenciaPersonas.crear(em, persona);
        } catch (Exception e) {
            System.out.println("Error crearNuevaPersona Admi : " + e.toString());
        }
    }

    public Personas obtenerUltimoRegistroPersona(BigInteger documento) {
        try {
            Personas persona = persistenciaPersonas.obtenerUltimaPersonaAlmacenada(em, documento);
            return persona;
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoRegistroPersona Admi : " + e.toString());
            return null;
        }
    }

    public void crearNuevoEmpleado(Empleados empleado) {
        try {
            persistenciaEmpleado.crear(em, empleado);
        } catch (Exception e) {
            System.out.println("Error crearNuevaPersona Admi : " + e.toString());
        }
    }

    public Empleados obtenerUltimoRegistroEmpleado(BigInteger empresa, BigInteger codigoEmpleado) {
        try {
            Empleados empleado = persistenciaEmpleado.obtenerUltimoEmpleadoAlmacenado(em, empresa, codigoEmpleado);
            return empleado;
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoRegistroEmpleado Admi : " + e.toString());
            return null;
        }
    }

    public void crearVigenciaCargo(VigenciasCargos vigencia) {
        try {
            persistenciaVigenciasCargos.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error obtenerUltimoRegistroEmpleado Admi : " + e.toString());
        }
    }

    public void crearVigenciaLocalizacion(VigenciasLocalizaciones vigencia) {
        try {
            persistenciaVigenciasLocalizaciones.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaLocalizacion Admi : " + e.toString());
        }
    }

    public void crearVigenciaTipoTrabajador(VigenciasTiposTrabajadores vigencia) {
        try {
            persistenciaVigenciasTiposTrabajadores.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaTipoTrabajador Admi : " + e.toString());
        }
    }

    public void crearVigenciaReformaLaboral(VigenciasReformasLaborales vigencia) {
        try {
            persistenciaVigenciasReformasLaborales.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaReformaLaboral Admi : " + e.toString());
        }
    }

    public void crearVigenciaSueldo(VigenciasSueldos vigencia) {
        try {
            persistenciaVigenciasSueldos.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaSueldo Admi : " + e.toString());
        }
    }

    public void crearVigenciaTipoContrato(VigenciasTiposContratos vigencia) {
        try {
            persistenciaVigenciasTiposContratos.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaTipoContrato Admi : " + e.toString());
        }
    }

    public void crearVigenciaNormaEmpleado(VigenciasNormasEmpleados vigencia) {
        try {
            persistenciaVigenciasNormasEmpleados.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaNormaEmpleado Admi : " + e.toString());
        }
    }

    public void crearVigenciaContrato(VigenciasContratos vigencia) {
        try {
            persistenciaVigenciasContratos.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaContrato Admi : " + e.toString());
        }
    }

    public void crearVigenciaUbicacion(VigenciasUbicaciones vigencia) {
        try {
            persistenciaVigenciasUbicaciones.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaUbicacion Admi : " + e.toString());
        }
    }

    public void crearVigenciaJornada(VigenciasJornadas vigencia) {
        try {
            persistenciaVigenciasJornadas.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaJornada Admi : " + e.toString());
        }
    }

    public void crearVigenciaFormaPago(VigenciasFormasPagos vigencia) {
        try {
            persistenciaVigenciasFormasPagos.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaFormaPago Admi : " + e.toString());
        }
    }

    public void crearVigenciaAfiliacion(VigenciasAfiliaciones vigencia) {
        try {
            System.out.println("Admi vigencia crear Tipo Entidad: "+vigencia.getTipoentidad().getNombre());
            System.out.println("Admi vigencia crear Secuencia: "+vigencia.getSecuencia());
            System.out.println("Admi vigencia crear Empleado: "+vigencia.getEmpleado().getSecuencia());
            System.out.println("Admi vigencia crear Fecha: "+vigencia.getFechainicial());
            persistenciaVigenciasAfiliaciones.crear(em, vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaAfiliacion Admi : " + e.toString());
        }
    }

    public void crearEstadoCivil(VigenciasEstadosCiviles estado) {
        try {
            persistenciaVigenciasEstadosCiviles.crear(em, estado);
        } catch (Exception e) {
            System.out.println("Error crearEstadoCivil Admi : " + e.toString());
        }
    }

    public void crearDireccion(Direcciones direccion) {
        try {
            persistenciaDirecciones.crear(em, direccion);
        } catch (Exception e) {
            System.out.println("Error crearDireccion Admi : " + e.toString());
        }
    }

    public void crearTelefono(Telefonos telefono) {
        try {
            persistenciaTelefonos.crear(em, telefono);
        } catch (Exception e) {
            System.out.println("Error crearTelefono Admi : " + e.toString());
        }
    }

    public void crearSets(Sets set) {
        try {
            persistenciaSets.crear(em, set);
        } catch (Exception e) {
            System.out.println("Error crearSets Admi : " + e.toString());
        }
    }

    public Procesos buscarProcesoPorCodigo(short codigo) {
        try {
            Procesos proceso = persistenciaProcesos.buscarProcesosPorCodigo(em, codigo);
            return proceso;
        } catch (Exception e) {
            System.out.println("Error buscarProcesoPorCodigo Admi : " + e.toString());
            return null;
        }
    }

    public BigDecimal obtenerNumeroMaximoComprobante() {
        try {
            BigDecimal valor = persistenciaComprobantes.buscarValorNumeroMaximo(em);
            return valor;
        } catch (Exception e) {
            System.out.println("Error obtenerNumeroMaximoComprobante Admi : " + e.toString());
            return null;
        }
    }

    public void crearComprobante(Comprobantes comprobante) {
        try {
            persistenciaComprobantes.crear(em, comprobante);
        } catch (Exception e) {
            System.out.println("Error crearComprobante Admi : " + e.toString());
        }
    }

    public Comprobantes buscarComprobanteParaPrimerRegistroEmpleado(BigInteger secEmpleado) {
        try {
            Comprobantes comprobante = persistenciaComprobantes.buscarComprobanteParaPrimerRegistroEmpleado(em, secEmpleado);
            return comprobante;
        } catch (Exception e) {
            System.out.println("Error buscarComprobanteParaPrimerRegistroEmpleado Admi : " + e.toString());
            return null;
        }
    }

    public void crearCortesProcesos(CortesProcesos corte) {
        try {
            persistenciaCortesProcesos.crear(em, corte);
        } catch (Exception e) {
            System.out.println("Error crearCortesProcesos Admi : " + e.toString());
        }
    }

    public TiposTrabajadores buscarTipoTrabajadorPorCodigo(short codigo) {
        try {
            TiposTrabajadores tipo = persistenciaTiposTrabajadores.buscarTipoTrabajadorCodigoTiposhort(em, codigo);
            return tipo;
        } catch (Exception e) {
            System.out.println("Error buscarTipoTrabajadorPorCodigo Admi : " + e.toString());
            return null;
        }
    }

}

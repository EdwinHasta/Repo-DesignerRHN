/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.Contratos;
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
import Entidades.ReformasLaborales;
import Entidades.Sucursales;
import Entidades.TercerosSucursales;
import Entidades.TiposContratos;
import Entidades.TiposDocumentos;
import Entidades.TiposSueldos;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import Entidades.UbicacionesGeograficas;
import Entidades.Unidades;
import InterfaceAdministrar.AdministrarPersonaIndividualInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaContratosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaEstadosCivilesInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
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
import InterfacePersistencia.PersistenciaReformasLaboralesInterface;
import InterfacePersistencia.PersistenciaSucursalesInterface;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
import InterfacePersistencia.PersistenciaTiposContratosInterface;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import InterfacePersistencia.PersistenciaTiposSueldosInterface;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import InterfacePersistencia.PersistenciaUnidadesInterface;
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
    //@EJB
    //Persistencia
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
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorEmpresaFechaIngreso(em,secEmpresa,fechaIngreso);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEstructurasModCargos Admi : " + e.toString());
            return null;
        }
    }
    @Override
    public List<Estructuras> lovEstructurasModCentroCosto(BigInteger secEmpresa) {
        try {
            List<Estructuras> lista = persistenciaEstructuras.buscarEstructurasPorEmpresa(em,secEmpresa);
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
    public List<UbicacionesGeograficas> lovUbicacionesGeograficas() {
        try {
            List<UbicacionesGeograficas> lista = persistenciaUbicacionesGeograficas.consultarUbicacionesGeograficas(em);
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

    @Override
    public List<Contratos> lovContratos() {
        try {
            List<Contratos> lista = persistenciaContratos.buscarContratos(em);
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
            List<TiposSueldos> lista = persistenciaTiposSueldos.buscarTiposSueldos(em);
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

    @Override
    public List<TercerosSucursales> lovTercerosSucursales() {
        try {
            List<TercerosSucursales> lista = persistenciaTercerosSucursales.buscarTercerosSucursales(em);
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

}

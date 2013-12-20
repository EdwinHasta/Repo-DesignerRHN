package Administrar;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.Demandas;
import Entidades.Direcciones;
import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.EvalResultadosConv;
import Entidades.Familiares;
import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import Entidades.HvExperienciasLaborales;
import Entidades.HvReferencias;
import Entidades.IdiomasPersonas;
import Entidades.InformacionesAdicionales;
import Entidades.Personas;
import Entidades.Telefonos;
import Entidades.TiposDocumentos;
import Entidades.VigenciasAficiones;
import Entidades.VigenciasDeportes;
import Entidades.VigenciasDomiciliarias;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasEventos;
import Entidades.VigenciasFormales;
import Entidades.VigenciasIndicadores;
import Entidades.VigenciasProyectos;
import InterfacePersistencia.PersistenciaVigenciasProyectosInterface;
import InterfaceAdministrar.AdministrarEmpleadoIndividualInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaDemandasInterface;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import InterfacePersistencia.PersistenciaEvalResultadosConvInterface;
import InterfacePersistencia.PersistenciaFamiliaresInterface;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import InterfacePersistencia.PersistenciaHvEntrevistasInterface;
import InterfacePersistencia.PersistenciaHvExperienciasLaboralesInterface;
import InterfacePersistencia.PersistenciaIdiomasPersonasInterface;
import InterfacePersistencia.PersistenciaInformacionesAdicionalesInterface;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import InterfacePersistencia.PersistenciaVigenciasAficionesInterface;
import InterfacePersistencia.PersistenciaVigenciasDeportesInterface;
import InterfacePersistencia.PersistenciaVigenciasDomiciliariasInterface;
import InterfacePersistencia.PersistenciaVigenciasEstadosCivilesInterface;
import InterfacePersistencia.PersistenciaVigenciasEventosInterface;
import InterfacePersistencia.PersistenciaVigenciasFormalesInterface;
import InterfacePersistencia.PersistenciaVigenciasIndicadoresInterface;
import InterfacePersistencia.PersistenciaHvReferenciasInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaTiposDocumentosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarEmpleadoIndividual implements AdministrarEmpleadoIndividualInterface {

    @EJB
    PersistenciaHVHojasDeVidaInterface persistenciaHVHojasDeVida;
    @EJB
    PersistenciaTelefonosInterface persistenciaTelefonos;
    @EJB
    PersistenciaDireccionesInterface persistenciaDirecciones;
    @EJB
    PersistenciaVigenciasEstadosCivilesInterface persistenciaVigenciasEstadosCiviles;
    @EJB
    PersistenciaInformacionesAdicionalesInterface persistenciaInformacionesAdicionales;
    @EJB
    PersistenciaEncargaturasInterface persistenciaEncargaturas;
    @EJB
    PersistenciaVigenciasFormalesInterface persistenciaVigenciasFormales;
    @EJB
    PersistenciaIdiomasPersonasInterface persistenciaIdiomasPersonas;
    @EJB
    PersistenciaVigenciasProyectosInterface persistenciaVigenciasProyectos;
    @EJB
    PersistenciaHvReferenciasInterface persistenciaHvReferencias;
    @EJB
    PersistenciaHvExperienciasLaboralesInterface persistenciaHvExperienciasLaborales;
    @EJB
    PersistenciaVigenciasEventosInterface persistenciaVigenciasEventos;
    @EJB
    PersistenciaVigenciasDeportesInterface persistenciaVigenciasDeportes;
    @EJB
    PersistenciaVigenciasAficionesInterface persistenciaVigenciasAficiones;
    @EJB
    PersistenciaFamiliaresInterface persistenciaFamiliares;
    @EJB
    PersistenciaHvEntrevistasInterface persistenciaHvEntrevistas;
    @EJB
    PersistenciaVigenciasIndicadoresInterface persistenciaVigenciasIndicadores;
    @EJB
    PersistenciaDemandasInterface persistenciaDemandas;
    @EJB
    PersistenciaVigenciasDomiciliariasInterface persistenciaVigenciasDomiciliarias;
    @EJB
    PersistenciaEvalResultadosConvInterface persistenciaEvalResultadosConv;
    @EJB
    PersistenciaTiposDocumentosInterface persistenciaTiposDocumentos;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;

    @Override
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secPersona) {
        return persistenciaHVHojasDeVida.hvHojaDeVidaPersona(secPersona);
    }

    @Override
    public Telefonos primerTelefonoPersona(BigInteger secPersona) {
        List<Telefonos> listaTelefonos;
        listaTelefonos = persistenciaTelefonos.telefonosPersona(secPersona);
        if (listaTelefonos != null) {
            return listaTelefonos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Direcciones primeraDireccionPersona(BigInteger secPersona) {
        List<Direcciones> listaDirecciones;
        listaDirecciones = persistenciaDirecciones.direccionPersona(secPersona);
        if (listaDirecciones != null) {
            return listaDirecciones.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasEstadosCiviles estadoCivilPersona(BigInteger secPersona) {
        List<VigenciasEstadosCiviles> listaVigenciasEstadosCiviles;
        listaVigenciasEstadosCiviles = persistenciaVigenciasEstadosCiviles.vigenciaEstadoCivilPersona(secPersona);
        if (listaVigenciasEstadosCiviles != null) {
            return listaVigenciasEstadosCiviles.get(0);
        } else {
            return null;
        }
    }

    @Override
    public InformacionesAdicionales informacionAdicionalPersona(BigInteger secEmpleado) {
        List<InformacionesAdicionales> listaInformacionesAdicionales;
        listaInformacionesAdicionales = persistenciaInformacionesAdicionales.informacionAdicionalPersona(secEmpleado);
        if (listaInformacionesAdicionales != null) {
            return listaInformacionesAdicionales.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Encargaturas reemplazoPersona(BigInteger secEmpleado) {
        List<Encargaturas> listaEncargaturas;
        listaEncargaturas = persistenciaEncargaturas.reemplazoPersona(secEmpleado);
        if (listaEncargaturas != null) {
            return listaEncargaturas.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasFormales educacionPersona(BigInteger secPersona) {
        List<VigenciasFormales> listaVigenciasFormales;
        listaVigenciasFormales = persistenciaVigenciasFormales.educacionPersona(secPersona);
        if (listaVigenciasFormales != null) {
            return listaVigenciasFormales.get(0);
        } else {
            return null;
        }
    }

    @Override
    public IdiomasPersonas idiomasPersona(BigInteger secPersona) {
        List<IdiomasPersonas> listaIdiomasPersonas;
        listaIdiomasPersonas = persistenciaIdiomasPersonas.idiomasPersona(secPersona);
        if (listaIdiomasPersonas != null) {
            return listaIdiomasPersonas.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasProyectos proyectosPersona(BigInteger secEmpleado) {
        List<VigenciasProyectos> listaVigenciasProyectos;
        listaVigenciasProyectos = persistenciaVigenciasProyectos.proyectosPersona(secEmpleado);
        if (listaVigenciasProyectos != null) {
            return listaVigenciasProyectos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HvReferencias referenciasPersonalesPersona(BigInteger secHv) {
        List<HvReferencias> listaReferenciasPersonales;
        listaReferenciasPersonales = persistenciaHvReferencias.referenciasPersonalesPersona(secHv);
        if (listaReferenciasPersonales != null) {
            return listaReferenciasPersonales.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HvReferencias referenciasFamiliaresPersona(BigInteger secHv) {
        List<HvReferencias> listaReferenciasPersonales;
        listaReferenciasPersonales = persistenciaHvReferencias.referenciasFamiliaresPersona(secHv);
        if (listaReferenciasPersonales != null) {
            return listaReferenciasPersonales.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HvExperienciasLaborales experienciaLaboralPersona(BigInteger secHv) {
        List<HvExperienciasLaborales> listaExperienciaLaboral;
        listaExperienciaLaboral = persistenciaHvExperienciasLaborales.experienciaLaboralPersona(secHv);
        if (listaExperienciaLaboral != null) {
            return listaExperienciaLaboral.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasEventos eventosPersona(BigInteger secEmpl) {
        List<VigenciasEventos> listaVigenciasEventos;
        listaVigenciasEventos = persistenciaVigenciasEventos.eventosEmpleado(secEmpl);
        if (listaVigenciasEventos != null) {
            return listaVigenciasEventos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasDeportes deportesPersona(BigInteger secPersona) {
        List<VigenciasDeportes> listaVigenciasDeportes;
        listaVigenciasDeportes = persistenciaVigenciasDeportes.deportePersona(secPersona);
        if (listaVigenciasDeportes != null) {
            return listaVigenciasDeportes.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasAficiones aficionesPersona(BigInteger secPersona) {
        List<VigenciasAficiones> listaVigenciasAficiones;
        listaVigenciasAficiones = persistenciaVigenciasAficiones.aficionesPersona(secPersona);
        if (listaVigenciasAficiones != null) {
            return listaVigenciasAficiones.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Familiares familiaresPersona(BigInteger secPersona) {
        List<Familiares> listaFamiliares;
        listaFamiliares = persistenciaFamiliares.familiaresPersona(secPersona);
        if (listaFamiliares != null) {
            return listaFamiliares.get(0);
        } else {
            return null;
        }
    }

    @Override
    public HvEntrevistas entrevistasPersona(BigInteger secHv) {
        List<HvEntrevistas> listaEntrevistas;
        listaEntrevistas = persistenciaHvEntrevistas.entrevistasPersona(secHv);
        if (listaEntrevistas != null) {
            return listaEntrevistas.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasIndicadores indicadoresPersona(BigInteger secEmpl) {
        List<VigenciasIndicadores> listaVigenciasIndicadores;
        listaVigenciasIndicadores = persistenciaVigenciasIndicadores.indicadoresPersona(secEmpl);
        if (listaVigenciasIndicadores != null) {
            return listaVigenciasIndicadores.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Demandas demandasPersona(BigInteger secEmpl) {
        List<Demandas> listaDemandas;
        listaDemandas = persistenciaDemandas.demandasPersona(secEmpl);
        if (listaDemandas != null) {
            return listaDemandas.get(0);
        } else {
            return null;
        }
    }

    @Override
    public VigenciasDomiciliarias visitasDomiciliariasPersona(BigInteger secPersona) {
        List<VigenciasDomiciliarias> listaVigenciasDomiciliarias;
        listaVigenciasDomiciliarias = persistenciaVigenciasDomiciliarias.visitasDomiciliariasPersona(secPersona);
        if (listaVigenciasDomiciliarias != null) {
            return listaVigenciasDomiciliarias.get(0);
        } else {
            return null;
        }
    }

    @Override
    public EvalResultadosConv pruebasAplicadasPersona(BigInteger secEmpleado) {
        List<EvalResultadosConv> listaPruebasAplicadas;
        listaPruebasAplicadas = persistenciaEvalResultadosConv.pruebasAplicadasPersona(secEmpleado);
        if (listaPruebasAplicadas != null) {
            return listaPruebasAplicadas.get(0);
        } else {
            return null;
        }
    }

    //LOVS
    @Override
    public List<TiposDocumentos> tiposDocumentos() {
        List<TiposDocumentos> listaTiposDocumentos;
        listaTiposDocumentos = persistenciaTiposDocumentos.tiposDocumentos();
        return listaTiposDocumentos;
    }

    public List<Ciudades> ciudades() {
        List<Ciudades> listaCiudades;
        listaCiudades = persistenciaCiudades.ciudades();
        return listaCiudades;
    }

    public List<Cargos> cargos() {
        List<Cargos> listaCargos;
        listaCargos = persistenciaCargos.buscarCargos();
        return listaCargos;
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        Empleados empleado;
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    public void modificarEmpleado(Empleados empleado) {
        try {
            persistenciaEmpleado.editar(empleado);
        } catch (Exception e) {
            System.out.println("Error modificando. AdministrarEmpleadoIndividual.modificarEmpleado");
        }
    }

    public void modificarHojaDeVida(HVHojasDeVida hojaVida) {
        try {
            persistenciaHVHojasDeVida.editar(hojaVida);
        } catch (Exception e) {
            System.out.println("Error modificando. AdministrarEmpleadoIndividual.modificarHojaDeVida");
        }
    }

    public void modificarPersona(Personas personas) {
        try {
            if (personas.getFactorrh().equals("")) {
                personas.setFactorrh(null);
            }
            if (personas.getGruposanguineo().equals("")) {
                personas.setGruposanguineo(null);
            }
            if (personas.getViviendapropia().equals("")) {
                personas.setViviendapropia(null);
            }
            persistenciaPersonas.editar(personas);
        } catch (Exception e) {
            System.out.println("Error modificando. AdministrarEmpleadoIndividual.modificarPersona");
        }
    }

    public void actualizarFotoPersona(BigInteger identificacion) {
        try {
            persistenciaPersonas.actualizarFotoPersona(identificacion);
        } catch (Exception e) {
            System.out.println("No se puede actalizar el estado de la Foto.");
        }
    }
}

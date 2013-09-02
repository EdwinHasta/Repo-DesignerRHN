package InterfacePersistencia;

import Entidades.Demandas;
import Entidades.Direcciones;
import Entidades.Encargaturas;
import Entidades.Familiares;
import Entidades.HVHojasDeVida;
import Entidades.HvEntrevistas;
import Entidades.HvExperienciasLaborales;
import Entidades.HvReferencias;
import Entidades.IdiomasPersonas;
import Entidades.InformacionesAdicionales;
import Entidades.Telefonos;
import Entidades.VigenciasAficiones;
import Entidades.VigenciasDeportes;
import Entidades.VigenciasDomiciliarias;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasEventos;
import Entidades.VigenciasFormales;
import Entidades.VigenciasIndicadores;
import Entidades.VigenciasProyectos;
import java.math.BigInteger;

public interface AdministrarEmpleadoIndividualInterface {
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secPersona);
    public Telefonos primerTelefonoPersona(BigInteger secEmpleado);
    public Direcciones primeraDireccionPersona(BigInteger secEmpleado);
    public VigenciasEstadosCiviles estadoCivilPersona(BigInteger secEmpleado);
    public InformacionesAdicionales informacionAdicionalPersona(BigInteger secEmpleado);
    public Encargaturas reemplazoPersona(BigInteger secEmpleado);
    public VigenciasFormales educacionPersona(BigInteger secPersona);
    public IdiomasPersonas idiomasPersona(BigInteger secPersona);
    public VigenciasProyectos proyectosPersona(BigInteger secEmpleado);
    public HvReferencias referenciasPersonalesPersona(BigInteger secHv);
    public HvReferencias referenciasFamiliaresPersona(BigInteger secHv);
    public HvExperienciasLaborales experienciaLaboralPersona(BigInteger secHv);
    public VigenciasEventos eventosPersona(BigInteger secEmpl);
    public VigenciasDeportes deportesPersona(BigInteger secPersona);
    public VigenciasAficiones aficionesPersona(BigInteger secPersona);
    public Familiares familiaresPersona(BigInteger secPersona);
    public HvEntrevistas entrevistasPersona(BigInteger secHv);
    public VigenciasIndicadores indicadoresPersona(BigInteger secEmpl);
    public Demandas demandasPersona(BigInteger secEmpl);
    public VigenciasDomiciliarias visitasDomiciliariasPersona(BigInteger secPersona);
}

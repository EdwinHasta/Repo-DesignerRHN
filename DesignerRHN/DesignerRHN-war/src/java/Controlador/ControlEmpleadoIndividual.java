package Controlador;

import Entidades.Demandas;
import Entidades.Direcciones;
import Entidades.Empleados;
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
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ControlEmpleadoIndividual implements Serializable {

    @EJB
    AdministrarEmpleadoIndividualInterface administrarEmpleadoIndividual;
    private Empleados empleado;
    private HVHojasDeVida hojaDeVidaPersona;
    private Telefonos telefono;
    private Direcciones direccion;
    private VigenciasEstadosCiviles estadoCivil;
    private InformacionesAdicionales informacionAdicional;
    private Encargaturas encargatura;
    private VigenciasFormales vigenciaFormal;
    private IdiomasPersonas idiomasPersona;
    private VigenciasProyectos vigenciaProyecto;
    private HvReferencias hvReferenciasPersonales;
    private HvReferencias hvReferenciasFamiliares;
    private HvExperienciasLaborales experienciaLaboral;
    private VigenciasEventos vigenciaEvento;
    private VigenciasDeportes vigenciaDeporte;
    private VigenciasAficiones vigenciaAficion;
    private Familiares familiares;
    private HvEntrevistas entrevistas;
    private VigenciasIndicadores vigenciaIndicador;
    private Demandas demandas;
    private VigenciasDomiciliarias vigenciaDomiciliaria;
    //CAMPOS A MOSTRAR EN LA PAGINA
    private String telefonoP, direccionP, estadoCivilP,
            informacionAdicionalP, reemplazoP, educacionP,
            idiomasP, proyectosP, referenciasPersonalesP,
            referenciasFamiliaresP, experienciaLaboralP,
            eventosP, deportesP, aficionesP, familiaresP,
            entrevistasP, indicadoresP, demandasP, 
            visitasDomiciliariasP, pruebasAplicadasP;
    //CONVERTIR FECHA
    private SimpleDateFormat formatoFecha;

    public ControlEmpleadoIndividual() {
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    }

    public void recibirEmpleado(Empleados empl) {
        empleado = empl;
        BigInteger secPersona = empleado.getPersona().getSecuencia();
        BigInteger secEmpleado = empleado.getSecuencia();
        hojaDeVidaPersona = administrarEmpleadoIndividual.hvHojaDeVidaPersona(secPersona);
        telefono = administrarEmpleadoIndividual.primerTelefonoPersona(secPersona);
        if (telefono != null) {
            telefonoP = telefono.getTipotelefono().getNombre() + " :  " + telefono.getNumerotelefono();
        } else {
            telefonoP = "SIN REGISTRAR";
        }
        direccion = administrarEmpleadoIndividual.primeraDireccionPersona(secPersona);
        if (direccion != null) {
            direccionP = direccion.getDireccionalternativa();
        } else {
            direccionP = "SIN REGISTRAR";
        }
        estadoCivil = administrarEmpleadoIndividual.estadoCivilPersona(secPersona);
        if (estadoCivil != null) {
            estadoCivilP = estadoCivil.getEstadocivil().getDescripcion() + "   " + formatoFecha.format(estadoCivil.getFechavigencia());
        } else {
            estadoCivilP = "SIN REGISTRAR";
        }
        informacionAdicional = administrarEmpleadoIndividual.informacionAdicionalPersona(secEmpleado);
        if (informacionAdicional != null) {
            informacionAdicionalP = informacionAdicional.getDescripcion() + "  " + formatoFecha.format(informacionAdicional.getFechainicial());
        } else {
            informacionAdicionalP = "SIN REGISTRAR";
        }
        encargatura = administrarEmpleadoIndividual.reemplazoPersona(secEmpleado);
        if (encargatura != null) {
            reemplazoP = encargatura.getTiporeemplazo().getNombre() + "  " + formatoFecha.format(encargatura.getFechainicial());
        } else {
            reemplazoP = "SIN REGISTRAR";
        }
        vigenciaFormal = administrarEmpleadoIndividual.educacionPersona(secPersona);
        if (vigenciaFormal != null) {
            educacionP = vigenciaFormal.getTipoeducacion().getNombre() + "  " + formatoFecha.format(vigenciaFormal.getFechavigencia());
        } else {
            educacionP = "SIN REGISTRAR";
        }
        idiomasPersona = administrarEmpleadoIndividual.idiomasPersona(secPersona);
        if (idiomasPersona != null) {
            idiomasP = idiomasPersona.getIdioma().getNombre();
        } else {
            idiomasP = "SIN REGISTRAR";
        }
        vigenciaProyecto = administrarEmpleadoIndividual.proyectosPersona(secEmpleado);
        if (vigenciaProyecto != null) {
            proyectosP = vigenciaProyecto.getProyecto().getNombreproyecto() + "  " + formatoFecha.format(vigenciaProyecto.getFechafinal());
        } else {
            proyectosP = "SIN REGISTRAR";
        }
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public void setHojaDeVidaPersona(HVHojasDeVida hojaDeVidaPersona) {
        this.hojaDeVidaPersona = hojaDeVidaPersona;
    }

    public void setTelefono(Telefonos telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(Direcciones direccion) {
        this.direccion = direccion;
    }

    public void setEstadoCivil(VigenciasEstadosCiviles estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public void setInformacionAdicional(InformacionesAdicionales informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public void setEncargatura(Encargaturas encargatura) {
        this.encargatura = encargatura;
    }

    public void setVigenciaFormal(VigenciasFormales vigenciaFormal) {
        this.vigenciaFormal = vigenciaFormal;
    }

    public void setIdiomasPersona(IdiomasPersonas idiomasPersona) {
        this.idiomasPersona = idiomasPersona;
    }

    public void setVigenciaProyecto(VigenciasProyectos vigenciaProyecto) {
        this.vigenciaProyecto = vigenciaProyecto;
    }

    public void setHvReferenciasPersonales(HvReferencias hvReferenciasPersonales) {
        this.hvReferenciasPersonales = hvReferenciasPersonales;
    }

    public void setHvReferenciasFamiliares(HvReferencias hvReferenciasFamiliares) {
        this.hvReferenciasFamiliares = hvReferenciasFamiliares;
    }

    public void setExperienciaLaboral(HvExperienciasLaborales experienciaLaboral) {
        this.experienciaLaboral = experienciaLaboral;
    }

    public void setVigenciaEvento(VigenciasEventos vigenciaEvento) {
        this.vigenciaEvento = vigenciaEvento;
    }

    public void setVigenciaDeporte(VigenciasDeportes vigenciaDeporte) {
        this.vigenciaDeporte = vigenciaDeporte;
    }

    public void setVigenciaAficion(VigenciasAficiones vigenciaAficion) {
        this.vigenciaAficion = vigenciaAficion;
    }

    public void setFamiliares(Familiares familiares) {
        this.familiares = familiares;
    }

    public void setEntrevistas(HvEntrevistas entrevistas) {
        this.entrevistas = entrevistas;
    }

    public void setVigenciaIndicador(VigenciasIndicadores vigenciaIndicador) {
        this.vigenciaIndicador = vigenciaIndicador;
    }

    public void setDemandas(Demandas demandas) {
        this.demandas = demandas;
    }

    public void setVigenciaDomiciliaria(VigenciasDomiciliarias vigenciaDomiciliaria) {
        this.vigenciaDomiciliaria = vigenciaDomiciliaria;
    }

    public HVHojasDeVida getHojaDeVidaPersona() {
        return hojaDeVidaPersona;
    }

    public String getTelefonoP() {
        return telefonoP;
    }

    public String getDireccionP() {
        return direccionP;
    }

    public String getEstadoCivilP() {
        return estadoCivilP;
    }

    public String getInformacionAdicionalP() {
        return informacionAdicionalP;
    }

    public String getReemplazoP() {
        return reemplazoP;
    }

    public String getEducacionP() {
        return educacionP;
    }

    public String getIdiomasP() {
        return idiomasP;
    }

    public String getProyectosP() {
        return proyectosP;
    }

    public String getReferenciasPersonalesP() {
        return referenciasPersonalesP;
    }

    public String getReferenciasFamiliaresP() {
        return referenciasFamiliaresP;
    }

    public String getExperienciaLaboralP() {
        return experienciaLaboralP;
    }

    public String getEventosP() {
        return eventosP;
    }

    public String getDeportesP() {
        return deportesP;
    }

    public String getAficionesP() {
        return aficionesP;
    }

    public String getFamiliaresP() {
        return familiaresP;
    }

    public String getEntrevistasP() {
        return entrevistasP;
    }

    public String getIndicadoresP() {
        return indicadoresP;
    }

    public String getDemandasP() {
        return demandasP;
    }

    public String getVisitasDomiciliariasP() {
        return visitasDomiciliariasP;
    }

    public String getPruebasAplicadasP() {
        return pruebasAplicadasP;
    }
}

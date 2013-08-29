package Controlador;

import Entidades.Direcciones;
import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.HVHojasDeVida;
import Entidades.InformacionesAdicionales;
import Entidades.Telefonos;
import Entidades.VigenciasEstadosCiviles;
import Entidades.VigenciasFormales;
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
    //CAMPOS A MOSTRAR EN LA PAGINA
    private String telefonoP, direccionP, estadoCivilP, informacionAdicionalP, reemplazoP, educacionP;
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
        }else{
            telefonoP = "SIN REGISTRAR";
        }
        direccion = administrarEmpleadoIndividual.primeraDireccionPersona(secPersona);
        if (direccion != null) {
            direccionP = direccion.getDireccionalternativa();
        }else{
            direccionP = "SIN REGISTRAR";
        }
        estadoCivil = administrarEmpleadoIndividual.estadoCivilPersona(secPersona);
        if (estadoCivil != null) {
            estadoCivilP = estadoCivil.getEstadocivil().getDescripcion() + "   " + formatoFecha.format(estadoCivil.getFechavigencia());
        }else{
            estadoCivilP = "SIN REGISTRAR";
        }
        informacionAdicional = administrarEmpleadoIndividual.informacionAdicionalPersona(secEmpleado);
        if (informacionAdicional != null) {
            informacionAdicionalP = informacionAdicional.getDescripcion() + "  " + formatoFecha.format(informacionAdicional.getFechainicial());
        }else{
            informacionAdicionalP = "SIN REGISTRAR";
        }
        encargatura = administrarEmpleadoIndividual.reemplazoPersona(secEmpleado);
        if(encargatura != null){
            reemplazoP = encargatura.getTiporeemplazo().getNombre() + "  " + formatoFecha.format(encargatura.getFechainicial());
        }else{
            reemplazoP = "SIN REGISTRAR";
        }
        vigenciaFormal = administrarEmpleadoIndividual.educacionPersona(secPersona);
        if(vigenciaFormal != null){
            educacionP = vigenciaFormal.getTipoeducacion().getNombre() + "  " + formatoFecha.format(vigenciaFormal.getFechavigencia());
        }else{
            educacionP = "SIN REGISTRAR";
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
}

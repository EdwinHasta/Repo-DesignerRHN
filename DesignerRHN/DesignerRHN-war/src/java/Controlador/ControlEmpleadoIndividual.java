package Controlador;

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
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;

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
    private EvalResultadosConv pruebasAplicadas;
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
    //LISTAS DE VALORES
    private List<TiposDocumentos> listaTiposDocumentos;
    private List<TiposDocumentos> filtradoListaTiposDocumentos;
    private TiposDocumentos seleccionTipoDocumento;
    private List<Ciudades> listaCiudades;
    private List<Ciudades> filtradoListaCiudades;
    private Ciudades seleccionCiudad;
    private List<Cargos> listaCargos;
    private List<Cargos> filtradoListaCargos;
    private Cargos seleccionCargo;
    private boolean aceptar;
    private int modificacionCiudad;
    private String cabezeraDialogoCiudad;

    public ControlEmpleadoIndividual() {
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        listaTiposDocumentos = null;
        filtradoListaTiposDocumentos = new ArrayList<TiposDocumentos>();
        seleccionTipoDocumento = new TiposDocumentos();
        aceptar = true;
    }

    public void recibirEmpleado(Empleados empl) {
        empleado = empl;
        BigInteger secPersona = empleado.getPersona().getSecuencia();
        BigInteger secEmpleado = empleado.getSecuencia();
        hojaDeVidaPersona = administrarEmpleadoIndividual.hvHojaDeVidaPersona(secPersona);
        BigInteger secHv = hojaDeVidaPersona.getSecuencia();
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
        hvReferenciasPersonales = administrarEmpleadoIndividual.referenciasPersonalesPersona(secHv);
        if (hvReferenciasPersonales != null) {
            referenciasPersonalesP = hvReferenciasPersonales.getNombrepersona();
        } else {
            referenciasPersonalesP = "SIN REGISTRAR";
        }

        hvReferenciasFamiliares = administrarEmpleadoIndividual.referenciasFamiliaresPersona(secHv);
        if (hvReferenciasFamiliares != null) {
            referenciasFamiliaresP = hvReferenciasFamiliares.getNombrepersona();
        } else {
            referenciasFamiliaresP = "SIN REGISTRAR";
        }
        experienciaLaboral = administrarEmpleadoIndividual.experienciaLaboralPersona(secHv);
        if (experienciaLaboral != null) {
            experienciaLaboralP = experienciaLaboral.getEmpresa() + "  " + formatoFecha.format(experienciaLaboral.getFechadesde());
        } else {
            experienciaLaboralP = "SIN REGISTRAR";
        }
        vigenciaEvento = administrarEmpleadoIndividual.eventosPersona(secEmpleado);
        if (vigenciaEvento != null) {
            eventosP = vigenciaEvento.getEvento().getDescripcion() + "  " + formatoFecha.format(vigenciaEvento.getFechainicial());
        } else {
            eventosP = "SIN REGISTRAR";
        }
        vigenciaDeporte = administrarEmpleadoIndividual.deportesPersona(secPersona);
        if (vigenciaDeporte != null) {
            deportesP = vigenciaDeporte.getDeporte().getNombre() + "  " + formatoFecha.format(vigenciaDeporte.getFechainicial());
        } else {
            deportesP = "SIN REGISTRAR";
        }
        vigenciaAficion = administrarEmpleadoIndividual.aficionesPersona(secPersona);
        if (vigenciaAficion != null) {
            aficionesP = vigenciaAficion.getAficion().getDescripcion() + "  " + formatoFecha.format(vigenciaAficion.getFechainicial());
        } else {
            aficionesP = "SIN REGISTRAR";
        }
        familiares = administrarEmpleadoIndividual.familiaresPersona(secPersona);
        if (familiares != null) {
            familiaresP = familiares.getTipofamiliar().getTipo() + "  " + familiares.getPersona().getPrimerapellido() + "  " + familiares.getPersona().getNombre();
        } else {
            familiaresP = "SIN REGISTRAR";
        }
        entrevistas = administrarEmpleadoIndividual.entrevistasPersona(secHv);
        if (entrevistas != null) {
            entrevistasP = entrevistas.getNombre() + "  " + formatoFecha.format(entrevistas.getFecha());
        } else {
            entrevistasP = "SIN REGISTRAR";
        }
        vigenciaIndicador = administrarEmpleadoIndividual.indicadoresPersona(secEmpleado);
        if (vigenciaIndicador != null) {
            indicadoresP = vigenciaIndicador.getIndicador().getDescripcion() + "  " + formatoFecha.format(vigenciaIndicador.getFechainicial());
        } else {
            indicadoresP = "SIN REGISTRAR";
        }
        demandas = administrarEmpleadoIndividual.demandasPersona(secEmpleado);
        if (demandas != null) {
            demandasP = demandas.getMotivo().getDescripcion();
        } else {
            demandasP = "SIN REGISTRAR";
        }
        vigenciaDomiciliaria = administrarEmpleadoIndividual.visitasDomiciliariasPersona(secPersona);
        if (vigenciaDomiciliaria != null) {
            visitasDomiciliariasP = "VISITADO EL:  " + formatoFecha.format(vigenciaDomiciliaria.getFecha());
        } else {
            visitasDomiciliariasP = "SIN REGISTRAR";
        }
        pruebasAplicadas = administrarEmpleadoIndividual.pruebasAplicadasPersona(secEmpleado);
        if (pruebasAplicadas != null) {
            pruebasAplicadasP = pruebasAplicadas.getNombreprueba() + " -> " + pruebasAplicadas.getPuntajeobtenido() + "%";
        } else {
            pruebasAplicadasP = "SIN REGISTRAR";
        }
        listaTiposDocumentos = null;
        listaCiudades = null;
        listaCargos = null;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //METODOS LOVS
    public void seleccionarTipoDocumento() {
        if (seleccionTipoDocumento != null && empleado != null) {
            empleado.getPersona().setTipodocumento(seleccionTipoDocumento);
            seleccionTipoDocumento = null;
            filtradoListaTiposDocumentos = null;
            aceptar = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:tipo");
            context.execute("TiposDocumentosDialogo.hide()");
            context.reset("formDialogos:lovTiposDocumentos:globalFilter");
            context.update("formDialogos:lovTiposDocumentos");
        }
    }

    public void cancelarSeleccionTipoDocumento() {
        filtradoListaTiposDocumentos = null;
        seleccionTipoDocumento = null;
        aceptar = true;
    }

    public void dialogoCiudad(int modificacion) {
        RequestContext context = RequestContext.getCurrentInstance();
        modificacionCiudad = modificacion;
        if (modificacionCiudad == 0) {
            cabezeraDialogoCiudad = "Ciudad documento";
        } else {
            cabezeraDialogoCiudad = "Ciudad nacimiento";
        }
        context.update("formDialogos:CiudadesDialogo");
        context.execute("CiudadesDialogo.show()");
    }

    public void seleccionarCiudad() {
        if (seleccionCiudad != null && empleado != null) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (modificacionCiudad == 0) {
                empleado.getPersona().setCiudaddocumento(seleccionCiudad);
                context.update("form:lugarExpedicion");
            } else {
                empleado.getPersona().setCiudadnacimiento(seleccionCiudad);
                context.update("form:lugarNacimiento");
            }
            seleccionCiudad = null;
            filtradoListaCiudades = null;
            aceptar = true;
            context.execute("CiudadesDialogo.hide()");
            context.reset("formDialogos:lovCiudades:globalFilter");
            context.update("formDialogos:lovCiudades");
            modificacionCiudad = -1;
        }
    }

    public void cancelarSeleccionCiudad() {
        seleccionCiudad = null;
        filtradoListaCiudades = null;
        aceptar = true;
        modificacionCiudad = -1;
    }

    public void seleccionarCargo() {
        if (seleccionCargo != null && hojaDeVidaPersona != null) {
            hojaDeVidaPersona.setCargo(seleccionCargo);
            seleccionCargo = null;
            filtradoListaCargos = null;
            aceptar = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:cargoPostulado");
            context.execute("CargosDialogo.hide()");
            context.reset("formDialogos:lovCargos:globalFilter");
            context.update("formDialogos:lovCargos");
        }
    }

    public void cancelarSeleccionCargo() {
        filtradoListaCargos = null;
        seleccionCargo = null;
        aceptar = true;
    }
    //GETTER AND SETTER

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

    public HvReferencias getHvReferenciasPersonales() {
        return hvReferenciasPersonales;
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

    public void setPruebasAplicadas(EvalResultadosConv pruebasAplicadas) {
        this.pruebasAplicadas = pruebasAplicadas;
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

    //LISTAS DE VALORES
    public List<TiposDocumentos> getListaTiposDocumentos() {
        if (listaTiposDocumentos == null) {
            listaTiposDocumentos = administrarEmpleadoIndividual.tiposDocumentos();
        }
        return listaTiposDocumentos;
    }

    public void setListaTiposDocumentos(List<TiposDocumentos> listaTiposDocumentos) {
        this.listaTiposDocumentos = listaTiposDocumentos;
    }

    public List<TiposDocumentos> getFiltradoListaTiposDocumentos() {
        return filtradoListaTiposDocumentos;
    }

    public void setFiltradoListaTiposDocumentos(List<TiposDocumentos> filtradoListaTiposDocumentos) {
        this.filtradoListaTiposDocumentos = filtradoListaTiposDocumentos;
    }

    public TiposDocumentos getSeleccionTipoDocumento() {
        return seleccionTipoDocumento;
    }

    public void setSeleccionTipoDocumento(TiposDocumentos seleccionTipoDocumento) {
        this.seleccionTipoDocumento = seleccionTipoDocumento;
    }

    public List<Ciudades> getListaCiudades() {
        if (listaCiudades == null) {
            listaCiudades = administrarEmpleadoIndividual.ciudades();
        }
        return listaCiudades;
    }

    public void setListaCiudades(List<Ciudades> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }

    public List<Ciudades> getFiltradoListaCiudades() {
        return filtradoListaCiudades;
    }

    public void setFiltradoListaCiudades(List<Ciudades> filtradoListaCiudades) {
        this.filtradoListaCiudades = filtradoListaCiudades;
    }

    public Ciudades getSeleccionCiudades() {
        return seleccionCiudad;
    }

    public void setSeleccionCiudades(Ciudades seleccionCiudades) {
        this.seleccionCiudad = seleccionCiudades;
    }

    public List<Cargos> getListaCargos() {
        if(listaCargos == null){
            listaCargos = administrarEmpleadoIndividual.cargos();
        }
        return listaCargos;
    }

    public void setListaCargos(List<Cargos> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public List<Cargos> getFiltradoListaCargos() {
        return filtradoListaCargos;
    }

    public void setFiltradoListaCargos(List<Cargos> filtradoListaCargos) {
        this.filtradoListaCargos = filtradoListaCargos;
    }

    public Cargos getSeleccionCargo() {
        return seleccionCargo;
    }

    public void setSeleccionCargo(Cargos seleccionCargo) {
        this.seleccionCargo = seleccionCargo;
    }

    public String getCabezeraDialogoCiudad() {
        return cabezeraDialogoCiudad;
    }

    public boolean isAceptar() {
        return aceptar;
    }
}

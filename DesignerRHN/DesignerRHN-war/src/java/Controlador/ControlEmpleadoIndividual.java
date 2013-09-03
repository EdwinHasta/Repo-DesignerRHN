package Controlador;

import ClasesAyuda.EmpleadoIndividualExportar;
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
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfacePersistencia.AdministrarEmpleadoIndividualInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlEmpleadoIndividual implements Serializable {

    @EJB
    AdministrarEmpleadoIndividualInterface administrarEmpleadoIndividual;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private Empleados empleado;
    private BigInteger secuencia;
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
    private int dialogo;
    //AUTOCOMPLETAR
    private String tipoDocumento, ciudad, cargoPostulado;
    //EXPORTAR DATOS 
    private List<EmpleadoIndividualExportar> empleadoIndividualExportar;
    //RASTRO
    private String nombreTabla;
    private BigInteger secRastro;
    //GUARDAR
    private boolean guardado;

    public ControlEmpleadoIndividual() {
        formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        aceptar = true;
        empleado = null;
        guardado = true;
    }

    public void recibirEmpleado(BigInteger sec) {
        secuencia = sec;
        getEmpleado();
        datosEmpleado();
        empleadoIndividualExportar = new ArrayList<EmpleadoIndividualExportar>();
        empleadoIndividualExportar.add(new EmpleadoIndividualExportar(empleado, hojaDeVidaPersona));
        listaTiposDocumentos = null;
        listaCiudades = null;
        listaCargos = null;
    }

    public void datosEmpleado() {
        BigInteger secPersona = empleado.getPersona().getSecuencia();
        BigInteger secEmpleado = empleado.getSecuencia();
        hojaDeVidaPersona = administrarEmpleadoIndividual.hvHojaDeVidaPersona(secPersona);
        if (hojaDeVidaPersona != null) {
            BigInteger secHv = hojaDeVidaPersona.getSecuencia();

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
            entrevistas = administrarEmpleadoIndividual.entrevistasPersona(secHv);
            if (entrevistas != null) {
                entrevistasP = entrevistas.getNombre() + "  " + formatoFecha.format(entrevistas.getFecha());
            } else {
                entrevistasP = "SIN REGISTRAR";
            }
        } else {
            referenciasPersonalesP = "SIN REGISTRAR";
            referenciasFamiliaresP = "SIN REGISTRAR";
            experienciaLaboralP = "SIN REGISTRAR";
            experienciaLaboralP = "SIN REGISTRAR";
        }
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
            dialogo = -1;
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
        dialogo = -1;
    }

    public void dialogoCiudad(int modificacion) {
        RequestContext context = RequestContext.getCurrentInstance();
        modificacionCiudad = modificacion;
        if (modificacionCiudad == 0) {
            cabezeraDialogoCiudad = "Ciudad documento";
        } else if (modificacionCiudad == 1) {
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
            } else if (modificacionCiudad == 1) {
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
            dialogo = -1;
        }
    }

    public void cancelarSeleccionCiudad() {
        seleccionCiudad = null;
        filtradoListaCiudades = null;
        aceptar = true;
        modificacionCiudad = -1;
        dialogo = -1;
    }

    public void seleccionarCargo() {
        if (seleccionCargo != null && hojaDeVidaPersona != null) {
            hojaDeVidaPersona.setCargo(seleccionCargo);
            seleccionCargo = null;
            filtradoListaCargos = null;
            aceptar = true;
            dialogo = -1;
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
        dialogo = -1;
    }

    //AUTOCOMPLETAR
    public void valoresBackupAutocompletar(String Campo) {
        if (Campo.equals("TIPODOCUMENTO")) {
            tipoDocumento = empleado.getPersona().getTipodocumento().getNombrecorto();
            dialogo = 0;
            nombreTabla = "Personas";
        } else if (Campo.equals("CIUDADDOCUMENTO")) {
            modificacionCiudad = 0;
            ciudad = empleado.getPersona().getCiudaddocumento().getNombre();
            dialogo = 1;
            nombreTabla = "Personas";
        } else if (Campo.equals("CIUDADNACIMIENTO")) {
            modificacionCiudad = 1;
            ciudad = empleado.getPersona().getCiudadnacimiento().getNombre();
            dialogo = 1;
            nombreTabla = "Personas";
        } else if (Campo.equals("CARGOPOSTULADO")) {
            cargoPostulado = hojaDeVidaPersona.getCargo().getNombre();
            dialogo = 2;
            nombreTabla = "HVHojasDeVida";
        }
    }

    public void autocompletar(String confirmarCambio, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPODOCUMENTO")) {
            empleado.getPersona().getTipodocumento().setNombrecorto(tipoDocumento);
            for (int i = 0; i < listaTiposDocumentos.size(); i++) {
                if (listaTiposDocumentos.get(i).getNombrecorto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                empleado.getPersona().setTipodocumento(listaTiposDocumentos.get(indiceUnicoElemento));
                context.update("form:tipo");
                listaTiposDocumentos = null;
                getListaTiposDocumentos();
            } else {
                context.update("formDialogos:TiposDocumentosDialogo");
                context.execute("TiposDocumentosDialogo.show()");
                context.update("form:tipo");
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (modificacionCiudad == 0) {
                empleado.getPersona().getCiudaddocumento().setNombre(ciudad);
            } else if (modificacionCiudad == 1) {
                empleado.getPersona().getCiudadnacimiento().setNombre(ciudad);
            }
            for (int i = 0; i < listaCiudades.size(); i++) {
                if (listaCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (modificacionCiudad == 0) {
                    empleado.getPersona().setCiudaddocumento(listaCiudades.get(indiceUnicoElemento));
                    context.update("form:lugarExpedicion");
                } else if (modificacionCiudad == 1) {
                    empleado.getPersona().setCiudadnacimiento(listaCiudades.get(indiceUnicoElemento));
                    context.update("form:lugarNacimiento");
                }
                listaCiudades = null;
                getListaCiudades();
            } else {
                if (modificacionCiudad == 0) {
                    cabezeraDialogoCiudad = "Ciudad documento";
                    context.update("form:lugarExpedicion");
                } else if (modificacionCiudad == 1) {
                    cabezeraDialogoCiudad = "Ciudad nacimiento";
                    context.update("formularioDialogos:lugarNacimiento");
                }
                context.update("formDialogos:CiudadesDialogo");
                context.execute("CiudadesDialogo.show()");
            }
        } else if (confirmarCambio.equalsIgnoreCase("CARGOPOSTULADO")) {
            hojaDeVidaPersona.getCargo().setNombre(cargoPostulado);
            for (int i = 0; i < listaCargos.size(); i++) {
                if (listaCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                hojaDeVidaPersona.setCargo(listaCargos.get(indiceUnicoElemento));
                context.update("form:cargoPostulado");
                listaCargos = null;
                getListaCargos();
            } else {
                context.update("formDialogos:CargosDialogo");
                context.execute("CargosDialogo.show()");
                context.update("form:cargoPostulado");
            }
        }
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (dialogo == 0) {
            context.execute("TiposDocumentosDialogo.show()");
        } else if (dialogo == 1) {
            if (modificacionCiudad == 0) {
                cabezeraDialogoCiudad = "Ciudad documento";
            } else if (modificacionCiudad == 1) {
                cabezeraDialogoCiudad = "Ciudad nacimiento";
            }
            context.update("formDialogos:CiudadesDialogo");
            context.execute("CiudadesDialogo.show()");
        } else if (dialogo == 2) {
            context.execute("CargosDialogo.show()");
        }
    }

    public void refrescar() {
        empleado = null;
        getEmpleado();
        datosEmpleado();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form");
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "HojaVidaEmpleadoPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "HojaVidaEmpleadoXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void cambiarTablaRastro(String tabla) {
        nombreTabla = tabla;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = -1;
        if (nombreTabla != null) {
            if (nombreTabla.equals("Personas")) {
                secRastro = empleado.getPersona().getSecuencia();
                resultado = administrarRastros.obtenerTabla(secRastro, nombreTabla.toUpperCase());
            } else if (nombreTabla.equals("Empleados")) {
                secRastro = empleado.getSecuencia();
                resultado = administrarRastros.obtenerTabla(secRastro, nombreTabla.toUpperCase());
            } else if (nombreTabla.equals("HVHojasDeVida")) {
                secRastro = empleado.getSecuencia();
                resultado = administrarRastros.obtenerTabla(secRastro, nombreTabla.toUpperCase());
            }
            if (resultado == 1) {
                context.update("formDialogos:errorObjetosDB");
                context.execute("errorObjetosDB.show()");
            } else if (resultado == 2) {
                context.update("formDialogos:confirmarRastro");
                context.execute("confirmarRastro.show()");
            } else if (resultado == 3) {
                context.update("formDialogos:errorRegistroRastro");
                context.execute("errorRegistroRastro.show()");
            } else if (resultado == 4) {
                context.update("formDialogos:errorTablaConRastro");
                context.execute("errorTablaConRastro.show()");
            } else if (resultado == 5) {
                context.update("formDialogos:errorTablaSinRastro");
                context.execute("errorTablaSinRastro.show()");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    //GUARDAR CAMBIOS
    public void guardarCambios() {
        ///if (guardado == false) {
        System.out.println("cambio: " + empleado.getPersona().getNombre());
            administrarEmpleadoIndividual.modificarEmpleado(empleado);
            administrarEmpleadoIndividual.modificarPersona(empleado.getPersona());
            administrarEmpleadoIndividual.modificarHojaDeVida(hojaDeVidaPersona);
            empleado = null;
            getEmpleado();
            datosEmpleado();
            guardado = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form");
            context.update("form:aceptar");
        //}
    }
//GETTER AND SETTER

    public Empleados getEmpleado() {
        if (empleado == null) {
            empleado = administrarEmpleadoIndividual.buscarEmpleado(secuencia);
        }
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
        if (listaCargos == null) {
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

    public List<EmpleadoIndividualExportar> getEmpleadoIndividualExportar() {
        return empleadoIndividualExportar;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setPruebasAplicadasP(String pruebasAplicadasP) {
        this.pruebasAplicadasP = pruebasAplicadasP;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public BigInteger getSecRastro() {
        return secRastro;
    }

    public void setSecRastro(BigInteger secRastro) {
        this.secRastro = secRastro;
    }
}

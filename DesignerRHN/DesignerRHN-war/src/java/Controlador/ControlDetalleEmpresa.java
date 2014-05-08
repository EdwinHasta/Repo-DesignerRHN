package Controlador;

import Entidades.Cargos;
import Entidades.Ciudades;
import Entidades.DetallesEmpresas;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Personas;
import Exportar.ExportarPDF;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarDetallesEmpresasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlDetalleEmpresa implements Serializable {

    
    @EJB
    AdministrarDetallesEmpresasInterface administrarDetalleEmpresa;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Detalles Empresas
    private List<DetallesEmpresas> listaDetallesEmpresas;
    private List<DetallesEmpresas> filtrarListaDetallesEmpresas;
    //LOV Empresas
    private List<Empresas> lovEmpresas;
    private Empresas empresaSeleccionada;
    private List<Empresas> filtrarLovEmpresas;
    //LOV Ciudades
    private List<Ciudades> lovCiudades;
    private Ciudades ciudadSeleccionada;
    private List<Ciudades> filtrarLovCiudades;
    //LOV Empleados
    private List<Empleados> lovEmpleados;
    private Empleados empleadoSeleccionado;
    private List<Empleados> filtrarLovEmpleados;
    //LOV Personas
    private List<Personas> lovPersonas;
    private Personas personaSeleccionada;
    private List<Personas> filtrarLovPersonas;
    //LOV Cargos
    private List<Cargos> lovCargos;
    private Cargos cargoSeleccionado;
    private List<Cargos> filtrarLovCargos;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int bandera;
    //Columnas Tabla VP
    private Column detalleEmpresa, detalleTipoDocumento, detalleTipo, detalleDireccion, detalleCiudad;
    private Column detalleTelefono, detalleFax, detalleNombreRepresentante, detalleDocumentoRepresentane;
    private Column detalleCiudadDocumento, detalleTipoNit, detalleDigitoVerificacion, detalleGerenteGeneral;
    private Column detallePersonaFirma, detalleCargoFirma, detalleEmail, detalleTipoZona, detalleCIIU, detalleActEconomica;
    private Column detalleSubGerente, detalleArquitecto, detalleCargoArquitecto, detalleRepresentante, detallePlanilla;
    private Column detalleTipoPersona, detalleNaturalezaJ, detalleClaseAportante, detalleFormaPresentacion, detalleTipoAportante;
    private Column detalleTipoAccion, detalleFechaComercio, detalleAnosParafiscal;
    private Column detalleReformaExonera, detallePilaMultinea, detalleSolidaridadFosyga, detalleExoneraLnsTarifa, detalleReportaLnsTarifa;
    //Otros
    private boolean aceptar;
    //modificar
    private List<DetallesEmpresas> listDetallesEmpresasModificar;
    private boolean guardado;
    //crear |
    private List<DetallesEmpresas> listDetallesEmpresasCrear;
    public DetallesEmpresas nuevaDetalleEmpresa;
    private BigInteger l;
    private int k;
    //borrar 
    private List<DetallesEmpresas> listDetallesEmpresasBorrar;
    //editar celda
    private DetallesEmpresas editarDetalleEmpresa;
    //duplicar
    private DetallesEmpresas duplicarDetalleEmpresa;
    //Autocompletar
    private boolean permitirIndex;
    //Variables Autompletar      
    private String ciudad, empresa, gerente, representante, cargo, persona, ciudadDocumento, subGerente;
    private int index;
    private int cualCelda, tipoLista;
    private boolean cambiosPagina;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private BigInteger secEmpresa;
    private Empresas actualEmpresa;
    //
    private Date fechaParametro;
    private Date auxFechaCamaraComercio;
    private String auxTipo, auxDireccion, auxTelefono, auxFax, auxNameRepre, auxDocRepre;
    private String altoTabla;
    private String paginaAnterior;

    /**
     * Creates a new instance of ControlDetalleEmpresa
     */
    public ControlDetalleEmpresa() {
        paginaAnterior = "";
        altoTabla = "260";
        actualEmpresa = null;
        secEmpresa = null;
        //Otros
        backUpSecRegistro = null;
        tipoLista = 0;
        k = 0;
        tipoLista = 0;
        guardado = true;
        bandera = 0;
        aceptar = true;
        cualCelda = -1;
        index = -1;
        secRegistro = null;
        cambiosPagina = true;
        //Lista BMC
        listDetallesEmpresasBorrar = new ArrayList<DetallesEmpresas>();
        listDetallesEmpresasModificar = new ArrayList<DetallesEmpresas>();
        listDetallesEmpresasCrear = new ArrayList<DetallesEmpresas>();
        //Editar
        editarDetalleEmpresa = new DetallesEmpresas();
        permitirIndex = true;
        //LOV
        lovEmpleados = null;
        lovCiudades = null;
        lovEmpresas = null;
        lovCargos = null;
        lovPersonas = null;
        //Nuevo
        nuevaDetalleEmpresa = new DetallesEmpresas();
        nuevaDetalleEmpresa.setCiudad(new Ciudades());
        nuevaDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        nuevaDetalleEmpresa.setEmpresa(new Empresas());
        nuevaDetalleEmpresa.setGerentegeneral(new Empleados());
        nuevaDetalleEmpresa.getGerentegeneral().setPersona(new Personas());
        nuevaDetalleEmpresa.setRepresentantecir(new Empleados());
        nuevaDetalleEmpresa.getRepresentantecir().setPersona(new Personas());
        nuevaDetalleEmpresa.setSubgerente(new Empleados());
        nuevaDetalleEmpresa.getSubgerente().setPersona(new Personas());
        nuevaDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        nuevaDetalleEmpresa.setPersonafirmaconstancia(new Personas());
        //Duplicar
        duplicarDetalleEmpresa = new DetallesEmpresas();
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarDetalleEmpresa.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public String regresarAPaginaAnterior(){
        return paginaAnterior;
    }
    
    
    public void inicializarPagina(BigInteger secuencia, String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
        secEmpresa = secuencia;
    }

    public boolean validarFechaCamaraComercio(int i) {
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        if (i == 0) {
            DetallesEmpresas auxiliar = new DetallesEmpresas();
            if (tipoLista == 0) {
                auxiliar = listaDetallesEmpresas.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListaDetallesEmpresas.get(index);
            }
            if (auxiliar.getFechacamaracomercio() != null) {
                if (auxiliar.getFechacamaracomercio().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }

        }
        if (i == 1) {
            if (nuevaDetalleEmpresa.getFechacamaracomercio() != null) {
                if (nuevaDetalleEmpresa.getFechacamaracomercio().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarDetalleEmpresa.getFechacamaracomercio() != null) {
                if (duplicarDetalleEmpresa.getFechacamaracomercio().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }

        return retorno;
    }

    public boolean validarDatosNullDetalleEmpresa(int i) {
        boolean retorno = true;
        if (i == 0) {
            DetallesEmpresas detalle = new DetallesEmpresas();
            if (tipoLista == 0) {
                detalle = listaDetallesEmpresas.get(index);
            }
            if (tipoLista == 1) {
                detalle = filtrarListaDetallesEmpresas.get(index);
            }
            if (detalle.getEmpresa().getSecuencia() == null || detalle.getTipo().isEmpty() || detalle.getDireccion().isEmpty()
                    || detalle.getCiudad().getSecuencia() == null || detalle.getTelefono().isEmpty() || detalle.getFax().isEmpty()
                    || detalle.getNombrerepresentante().isEmpty() || detalle.getDocumentorepresentante().isEmpty()) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaDetalleEmpresa.getEmpresa().getSecuencia() == null || nuevaDetalleEmpresa.getTipo().isEmpty() || nuevaDetalleEmpresa.getDireccion().isEmpty()
                    || nuevaDetalleEmpresa.getCiudad().getSecuencia() == null || nuevaDetalleEmpresa.getTelefono().isEmpty() || nuevaDetalleEmpresa.getFax().isEmpty()
                    || nuevaDetalleEmpresa.getNombrerepresentante().isEmpty() || nuevaDetalleEmpresa.getDocumentorepresentante().isEmpty()) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarDetalleEmpresa.getEmpresa().getSecuencia() == null || duplicarDetalleEmpresa.getTipo().isEmpty() || duplicarDetalleEmpresa.getDireccion().isEmpty()
                    || duplicarDetalleEmpresa.getCiudad().getSecuencia() == null || duplicarDetalleEmpresa.getTelefono().isEmpty() || duplicarDetalleEmpresa.getFax().isEmpty()
                    || duplicarDetalleEmpresa.getNombrerepresentante().isEmpty() || duplicarDetalleEmpresa.getDocumentorepresentante().isEmpty()) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificacionesFechaCamaraComercio(int i, int c) {
        index = i;
        if (validarFechaCamaraComercio(0) == true) {
            cambiarIndice(i, c);
            modificarDetalleEmpresa(i);
        } else {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setFechacamaracomercio(auxFechaCamaraComercio);
            }
            if (tipoLista == 1) {
                filtrarListaDetallesEmpresas.get(index).setFechacamaracomercio(auxFechaCamaraComercio);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalleEmpresa");
            context.execute("errorFechas.show()");
        }
    }

    public void modificarDetalleEmpresaSOneMenuCheckBox(int indice) {
        System.out.println("indice : " + indice);
        if (tipoLista == 0) {
            if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(indice))) {
                if (listDetallesEmpresasModificar.isEmpty()) {
                    listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(indice))) {
                    listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambiosPagina = false;
            index = -1;
            secRegistro = null;
        } else {
            if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(indice))) {
                if (listDetallesEmpresasModificar.isEmpty()) {
                    listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(indice))) {
                    listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambiosPagina = false;
            index = -1;
            secRegistro = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosDetalleEmpresa");
    }

    public void modificarDetalleEmpresa(int indice) {
        if (validarDatosNullDetalleEmpresa(0) == true) {
            if (tipoLista == 0) {
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(indice))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(indice))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambiosPagina = false;
                index = -1;
                secRegistro = null;
            } else {
                int ind = listaDetallesEmpresas.indexOf(filtrarListaDetallesEmpresas.get(indice));
                index = ind;
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(indice))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(indice))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambiosPagina = false;
                index = -1;
                secRegistro = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosDetalleEmpresa");
        } else {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).setTelefono(auxTelefono);
                listaDetallesEmpresas.get(indice).setDireccion(auxDireccion);
                listaDetallesEmpresas.get(indice).setTipo(auxTipo);
                listaDetallesEmpresas.get(indice).setFax(auxFax);
                listaDetallesEmpresas.get(indice).setNombrerepresentante(auxNameRepre);
                listaDetallesEmpresas.get(indice).setDocumentorepresentante(auxDocRepre);
            }
            if (tipoLista == 1) {
                filtrarListaDetallesEmpresas.get(indice).setTelefono(auxTelefono);
                filtrarListaDetallesEmpresas.get(indice).setDireccion(auxDireccion);
                filtrarListaDetallesEmpresas.get(indice).setTipo(auxTipo);
                filtrarListaDetallesEmpresas.get(indice).setFax(auxFax);
                filtrarListaDetallesEmpresas.get(indice).setNombrerepresentante(auxNameRepre);
                filtrarListaDetallesEmpresas.get(indice).setDocumentorepresentante(auxDocRepre);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalleEmpresa");
            context.execute("errorDatosNull.show()");
        }
    }

    public void modificarDetalleEmpresa(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Indice :: " + indice);
        System.out.println("valorConfirmar : " + valorConfirmar);
        System.out.println("empresa : " + empresa);
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getCiudad().setNombre(ciudad);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getEmpresa().setNombre(ciudad);
            }
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setCiudad(lovCiudades.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setCiudad(lovCiudades.get(indiceUnicoElemento));
                }
                lovCiudades = null;
                getLovCiudades();
                cambiosPagina = false;
            } else {
                permitirIndex = false;
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("EMPRESA")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getEmpresa().setNombre(empresa);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getEmpresa().setNombre(empresa);
            }
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                }
                lovEmpresas = null;
                getLovEmpresas();
                cambiosPagina = false;
            } else {
                permitirIndex = false;
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("GERENTE")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getGerentegeneral().getPersona().setNombreCompleto(gerente);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getGerentegeneral().getPersona().setNombreCompleto(gerente);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setGerentegeneral(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setGerentegeneral(lovEmpleados.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:GerenteDialogo");
                context.execute("GerenteDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("REPRESENTANTE")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getRepresentantecir().getPersona().setNombreCompleto(representante);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getRepresentantecir().getPersona().setNombreCompleto(representante);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setRepresentantecir(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setRepresentantecir(lovEmpleados.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:RepresentanteDialogo");
                context.execute("RepresentanteDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CARGO")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getCargofirmaconstancia().setNombre(cargo);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getCargofirmaconstancia().setNombre(cargo);
            }
            for (int i = 0; i < lovCargos.size(); i++) {
                if (lovCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setCargofirmaconstancia(lovCargos.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setCargofirmaconstancia(lovCargos.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovCargos = null;
                getLovCargos();
            } else {
                permitirIndex = false;
                context.update("form:CargoFirmaDialogo");
                context.execute("CargoFirmaDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("SUBGERENTE")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getSubgerente().getPersona().setNombreCompleto(subGerente);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getSubgerente().getPersona().setNombreCompleto(subGerente);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setSubgerente(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setSubgerente(lovEmpleados.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:SubGerenteDialogo");
                context.execute("SubGerenteDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PERSONA")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getPersonafirmaconstancia().setNombreCompleto(persona);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getPersonafirmaconstancia().setNombreCompleto(persona);
            }
            for (int i = 0; i < lovPersonas.size(); i++) {
                if (lovPersonas.get(i).getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setPersonafirmaconstancia(lovPersonas.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setPersonafirmaconstancia(lovPersonas.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovPersonas = null;
                getLovPersonas();
            } else {
                permitirIndex = false;
                context.update("form:PersonaFirmaDialogo");
                context.execute("PersonaFirmaDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDADDOCUMENTO")) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(indice).getCiudaddocumentorepresentante().setNombre(ciudadDocumento);
            } else {
                filtrarListaDetallesEmpresas.get(indice).getCiudaddocumentorepresentante().setNombre(ciudadDocumento);
            }
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDetallesEmpresas.get(indice).setCiudaddocumentorepresentante(lovCiudades.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesEmpresas.get(indice).setCiudaddocumentorepresentante(lovCiudades.get(indiceUnicoElemento));
                }
                cambiosPagina = false;
                lovCiudades = null;
                getLovCiudades();
            } else {
                permitirIndex = false;
                context.update("form:CiudadDocumentoDialogo");
                context.execute("CiudadDocumentoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(indice))) {

                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(indice))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambiosPagina = false;
                index = -1;
                secRegistro = null;
            } else {
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(indice))) {

                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(indice))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambiosPagina = false;
                index = -1;
                secRegistro = null;
            }
            cambiosPagina = false;
        }
        context.update("form:ACEPTAR");
        context.update("form:datosDetalleEmpresa");
    }

    public void valoresBackupAutocompletarDetalleEmpresa(int tipoNuevo, String Campo) {

        if (Campo.equals("CIUDAD")) {
            if (tipoNuevo == 1) {
                ciudad = nuevaDetalleEmpresa.getCiudad().getNombre();
            } else if (tipoNuevo == 2) {
                ciudad = duplicarDetalleEmpresa.getCiudad().getNombre();
            }
        } else if (Campo.equals("CIUDADDOCUMENTO")) {
            if (tipoNuevo == 1) {
                ciudadDocumento = nuevaDetalleEmpresa.getCiudaddocumentorepresentante().getNombre();
            } else if (tipoNuevo == 2) {
                ciudadDocumento = duplicarDetalleEmpresa.getCiudaddocumentorepresentante().getNombre();
            }
        } else if (Campo.equals("PERSONA")) {
            if (tipoNuevo == 1) {
                persona = nuevaDetalleEmpresa.getPersonafirmaconstancia().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                persona = duplicarDetalleEmpresa.getPersonafirmaconstancia().getNombreCompleto();
            }
        } else if (Campo.equals("CARGO")) {
            if (tipoNuevo == 1) {
                cargo = nuevaDetalleEmpresa.getCargofirmaconstancia().getNombre();
            } else if (tipoNuevo == 2) {
                cargo = duplicarDetalleEmpresa.getCargofirmaconstancia().getNombre();
            }
        } else if (Campo.equals("REPRESENTANTE")) {
            if (tipoNuevo == 1) {
                representante = nuevaDetalleEmpresa.getRepresentantecir().getPersona().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                representante = duplicarDetalleEmpresa.getRepresentantecir().getPersona().getNombreCompleto();
            }
        } else if (Campo.equals("GERENTE")) {
            if (tipoNuevo == 1) {
                gerente = nuevaDetalleEmpresa.getGerentegeneral().getPersona().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                gerente = duplicarDetalleEmpresa.getGerentegeneral().getPersona().getNombreCompleto();
            }
        } else if (Campo.equals("EMPRESA")) {
            if (tipoNuevo == 1) {
                empresa = nuevaDetalleEmpresa.getEmpresa().getNombre();
            } else if (tipoNuevo == 2) {
                empresa = duplicarDetalleEmpresa.getEmpresa().getNombre();
            }
        } else if (Campo.equals("SUBGERENTE")) {
            if (tipoNuevo == 1) {
                subGerente = nuevaDetalleEmpresa.getSubgerente().getPersona().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                subGerente = duplicarDetalleEmpresa.getSubgerente().getPersona().getNombreCompleto();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoDetalleEmpresa(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("EMPRESA")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getEmpresa().setNombre(empresa);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getEmpresa().setNombre(empresa);
            }
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaEmpresaDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarEmpresaDetalle");
                }
                lovEmpresas = null;
                getLovEmpresas();
            } else {
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaEmpresaDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarEmpresaDetalle");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("GERENTE")) {
            System.out.println("gerente: " + gerente);
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getGerentegeneral().getPersona().setNombreCompleto(gerente);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getGerentegeneral().getPersona().setNombreCompleto(gerente);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                System.out.println("Entra gerente");
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setGerentegeneral(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaGerenteDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setGerentegeneral(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarGerenteDetalle");
                }
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                context.update("form:GerenteDialogo");
                context.execute("GerenteDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaGerenteDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarGerenteDetalle");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("SUBGERENTE")) {
            System.out.println("subGerente: " + subGerente);
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getSubgerente().getPersona().setNombreCompleto(subGerente);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getSubgerente().getPersona().setNombreCompleto(subGerente);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                System.out.println("Entra subGerente");
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setSubgerente(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaSubGerenteDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setSubgerente(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarSubGerenteDetalle");
                }
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                System.out.println("Error false subGerente");
                context.update("form:SubGerenteDialogo");
                context.execute("SubGerenteDialogo.show()");
                tipoActualizacion = tipoNuevo;
                System.out.println("tipoNuevo : " + tipoNuevo);
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaSubGerenteDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarSubGerenteDetalle");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("REPRESENTANTE")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getRepresentantecir().getPersona().setNombreCompleto(representante);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getRepresentantecir().getPersona().setNombreCompleto(representante);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setRepresentantecir(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaRepresentanteDetalle");
                } else {
                    duplicarDetalleEmpresa.setRepresentantecir(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarRepresentanteDetalle");
                }
                lovEmpleados = null;
                getLovEmpleados();
            } else {
                context.update("form:RepresentanteDialogo");
                context.execute("RepresentanteDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaRepresentanteDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarRepresentanteDetalle");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CARGO")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getCargofirmaconstancia().setNombre(cargo);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getCargofirmaconstancia().setNombre(cargo);
            }
            for (int i = 0; i < lovCargos.size(); i++) {
                if (lovCargos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setCargofirmaconstancia(lovCargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCargoFirmaDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setCargofirmaconstancia(lovCargos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCargoFirmaDetalle");
                }
                lovCargos = null;
                getLovCargos();
            } else {
                context.update("form:CargoFirmaDialogo");
                context.execute("CargoFirmaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCargoFirmaDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCargoFirmaDetalle");
                }
            }

        } else if (confirmarCambio.equalsIgnoreCase("PERSONA")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getPersonafirmaconstancia().setNombreCompleto(persona);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getPersonafirmaconstancia().setNombreCompleto(persona);
            }
            for (int i = 0; i < lovPersonas.size(); i++) {
                if (lovPersonas.get(i).getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setPersonafirmaconstancia(lovPersonas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaPersonaFirmaDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setPersonafirmaconstancia(lovPersonas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarPersonaFirmaDetalle");
                }
                lovPersonas = null;
                getLovPersonas();
            } else {
                context.update("form:PersonaFirmaDialogo");
                context.execute("PersonaFirmaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaPersonaFirmaDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarPersonaFirmaDetalle");
                }
            }

        } else if (confirmarCambio.equalsIgnoreCase("CIUDADDOCUMENTO")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getCiudaddocumentorepresentante().setNombre(ciudadDocumento);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getCiudaddocumentorepresentante().setNombre(ciudadDocumento);
            }
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setCiudaddocumentorepresentante(lovCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDocumentoDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setCiudaddocumentorepresentante(lovCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCiudadDocumentoDetalle");
                }
                lovCiudades = null;
                getLovCiudades();
            } else {
                context.update("form:CiudadDocumentoDialogo");
                context.execute("CiudadDocumentoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDocumentoDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCiudadDocumentoDetalle");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoNuevo == 1) {
                nuevaDetalleEmpresa.getCiudad().setNombre(ciudad);
            } else if (tipoNuevo == 2) {
                duplicarDetalleEmpresa.getCiudad().setNombre(ciudad);
            }
            for (int i = 0; i < lovCiudades.size(); i++) {
                if (lovCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalleEmpresa.setCiudad(lovCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalleEmpresa.setCiudad(lovCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDetalleEmpresa::duplicarCiudadDetalle");
                }
                lovCiudades = null;
                getLovCiudades();
            } else {
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDetalleEmpresa::duplicarCiudadDetalle");
                }
            }
        }
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaDetallesEmpresas.get(index).getSecuencia();
                empresa = listaDetallesEmpresas.get(index).getEmpresa().getNombre();
                ciudad = listaDetallesEmpresas.get(index).getCiudad().getNombre();
                ciudadDocumento = listaDetallesEmpresas.get(index).getCiudaddocumentorepresentante().getNombre();
                gerente = listaDetallesEmpresas.get(index).getGerentegeneral().getPersona().getNombreCompleto();
                subGerente = listaDetallesEmpresas.get(index).getSubgerente().getPersona().getNombreCompleto();
                persona = listaDetallesEmpresas.get(index).getPersonafirmaconstancia().getNombreCompleto();
                cargo = listaDetallesEmpresas.get(index).getCargofirmaconstancia().getNombre();
                representante = listaDetallesEmpresas.get(index).getRepresentantecir().getPersona().getNombreCompleto();
                auxTipo = listaDetallesEmpresas.get(index).getTipo();
                auxDireccion = listaDetallesEmpresas.get(index).getDireccion();
                auxTelefono = listaDetallesEmpresas.get(index).getTelefono();
                auxFax = listaDetallesEmpresas.get(index).getFax();
                auxNameRepre = listaDetallesEmpresas.get(index).getNombrerepresentante();
                auxDocRepre = listaDetallesEmpresas.get(index).getDocumentorepresentante();
            }
            if (tipoLista == 1) {
                secRegistro = filtrarListaDetallesEmpresas.get(index).getSecuencia();
                empresa = filtrarListaDetallesEmpresas.get(index).getEmpresa().getNombre();
                ciudad = filtrarListaDetallesEmpresas.get(index).getCiudad().getNombre();
                ciudadDocumento = filtrarListaDetallesEmpresas.get(index).getCiudaddocumentorepresentante().getNombre();
                gerente = filtrarListaDetallesEmpresas.get(index).getGerentegeneral().getPersona().getNombreCompleto();
                subGerente = filtrarListaDetallesEmpresas.get(index).getSubgerente().getPersona().getNombreCompleto();
                persona = filtrarListaDetallesEmpresas.get(index).getPersonafirmaconstancia().getNombreCompleto();
                cargo = filtrarListaDetallesEmpresas.get(index).getCargofirmaconstancia().getNombre();
                representante = filtrarListaDetallesEmpresas.get(index).getRepresentantecir().getPersona().getNombreCompleto();
                auxTipo = filtrarListaDetallesEmpresas.get(index).getTipo();
                auxDireccion = filtrarListaDetallesEmpresas.get(index).getDireccion();
                auxTelefono = filtrarListaDetallesEmpresas.get(index).getTelefono();
                auxFax = filtrarListaDetallesEmpresas.get(index).getFax();
                auxNameRepre = filtrarListaDetallesEmpresas.get(index).getNombrerepresentante();
                auxDocRepre = filtrarListaDetallesEmpresas.get(index).getDocumentorepresentante();
            }
            System.out.println("CambiarIndice empresa : " + empresa);
        }
    }

    public void guardarSalir(){
        guardadoGeneral();
        salir();
    }
    public void cancelaralir(){
        cancelarModificacion();
        salir();
    }
    
    //GUARDAR
    public void guardadoGeneral() {
        guardarCambios();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    public void guardarCambios() {
        if (guardado == false) {
            if (!listDetallesEmpresasBorrar.isEmpty()) {
                administrarDetalleEmpresa.borrarDetalleEmpresa(listDetallesEmpresasBorrar);
                listDetallesEmpresasBorrar.clear();
            }
            if (!listDetallesEmpresasCrear.isEmpty()) {
                administrarDetalleEmpresa.crearDetalleEmpresa(listDetallesEmpresasCrear);
                listDetallesEmpresasCrear.clear();
            }
            if (!listDetallesEmpresasModificar.isEmpty()) {
                administrarDetalleEmpresa.editarDetalleEmpresa(listDetallesEmpresasModificar);
                listDetallesEmpresasModificar.clear();
            }
            listaDetallesEmpresas = null;
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalleEmpresa");
            k = 0;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = true;
        context.update("form:ACEPTAR");
        index = -1;
        secRegistro = null;
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            altoTabla = "260";
            detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
            detalleEmpresa.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
            detalleTipoDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
            detalleTipo.setFilterStyle("display: none; visibility: hidden;");
            detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
            detalleDireccion.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
            detalleCiudad.setFilterStyle("display: none; visibility: hidden;");
            detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
            detalleTelefono.setFilterStyle("display: none; visibility: hidden;");
            detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
            detalleFax.setFilterStyle("display: none; visibility: hidden;");
            detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
            detalleNombreRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
            detalleDocumentoRepresentane.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
            detalleCiudadDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
            detalleTipoNit.setFilterStyle("display: none; visibility: hidden;");
            detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
            detalleDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
            detalleGerenteGeneral.setFilterStyle("display: none; visibility: hidden;");
            detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
            detallePersonaFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
            detalleCargoFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
            detalleEmail.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
            detalleTipoZona.setFilterStyle("display: none; visibility: hidden;");
            detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
            detalleCIIU.setFilterStyle("display: none; visibility: hidden;");
            detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
            detalleActEconomica.setFilterStyle("display: none; visibility: hidden;");
            detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
            detalleSubGerente.setFilterStyle("display: none; visibility: hidden;");
            detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
            detalleArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
            detalleCargoArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
            detalleRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
            detallePlanilla.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
            detalleTipoPersona.setFilterStyle("display: none; visibility: hidden;");
            detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
            detalleNaturalezaJ.setFilterStyle("display: none; visibility: hidden;");
            detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
            detalleClaseAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
            detalleFormaPresentacion.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
            detalleTipoAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
            detalleTipoAccion.setFilterStyle("display: none; visibility: hidden;");
            detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
            detalleFechaComercio.setFilterStyle("display: none; visibility: hidden;");
            detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
            detalleAnosParafiscal.setFilterStyle("display: none; visibility: hidden;");
            detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
            detalleReformaExonera.setFilterStyle("display: none; visibility: hidden;");
            detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
            detallePilaMultinea.setFilterStyle("display: none; visibility: hidden;");
            detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
            detalleSolidaridadFosyga.setFilterStyle("display: none; visibility: hidden;");
            detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
            detalleExoneraLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
            detalleReportaLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
            bandera = 0;
            filtrarListaDetallesEmpresas = null;
            tipoLista = 0;
        }
        lovEmpleados = null;
        lovCiudades = null;
        lovEmpresas = null;
        lovPersonas = null;
        lovCargos = null;
        listDetallesEmpresasBorrar.clear();
        listDetallesEmpresasCrear.clear();
        listDetallesEmpresasModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaDetallesEmpresas = null;
        getListaDetallesEmpresas();
        guardado = true;
        nuevaDetalleEmpresa = new DetallesEmpresas();
        nuevaDetalleEmpresa.setCiudad(new Ciudades());
        nuevaDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        nuevaDetalleEmpresa.setEmpresa(new Empresas());
        nuevaDetalleEmpresa.setGerentegeneral(new Empleados());
        nuevaDetalleEmpresa.setRepresentantecir(new Empleados());
        nuevaDetalleEmpresa.setArquitecto(new Empleados());
        nuevaDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        nuevaDetalleEmpresa.setPersonafirmaconstancia(new Personas());
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = true;
        context.update("form:ACEPTAR");
        context.update("form:datosDetalleEmpresa");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarDetalleEmpresa = listaDetallesEmpresas.get(index);
            }
            if (tipoLista == 1) {
                editarDetalleEmpresa = filtrarListaDetallesEmpresas.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarEmpresaDE");
                context.execute("editarEmpresaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoEmpresaDE");
                context.execute("editarTipoEmpresaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarDireccionDE");
                context.execute("editarDireccionDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarCiudadDE");
                context.execute("editarCiudadDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarTelefonoDE");
                context.execute("editarTelefonoDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarFaxDE");
                context.execute("editarFaxDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarNombreRepresentanteDE");
                context.execute("editarNombreRepresentanteDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarDocumentoRepresentanteDE");
                context.execute("editarDocumentoRepresentanteDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarCiudadDocumentoDE");
                context.execute("editarCiudadDocumentoDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarTipoNitDE");
                context.execute("editarTipoNitDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:editarDigitoVerificacionDE");
                context.execute("editarDigitoVerificacionDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:editarGerenteDE");
                context.execute("editarGerenteDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:editarPersonaFirmaDE");
                context.execute("editarPersonaFirmaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 14) {
                context.update("formularioDialogos:editarCargoFirmaDE");
                context.execute("editarCargoFirmaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 15) {
                context.update("formularioDialogos:editarEmailDE");
                context.execute("editarEmailDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 17) {
                context.update("formularioDialogos:editarCIIUDE");
                context.execute("editarCIIUDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 18) {
                context.update("formularioDialogos:editarActEconomicaDE");
                context.execute("editarActEconomicaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 19) {
                context.update("formularioDialogos:editarSubGerenteDE");
                context.execute("editarSubGerenteDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 20) {
                context.update("formularioDialogos:editarArquitectoDE");
                context.execute("editarArquitectoDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 21) {
                context.update("formularioDialogos:editarCargoArquitectoDE");
                context.execute("editarCargoArquitectoDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 22) {
                context.update("formularioDialogos:editarRepresentanteDE");
                context.execute("editarRepresentanteDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 23) {
                context.update("formularioDialogos:editarNPlanillaDE");
                context.execute("editarNPlanillaDE.show()");
                cualCelda = -1;
            } else if (cualCelda == 30) {
                context.update("formularioDialogos:editarFechaCamaraComercioDE");
                context.execute("editarFechaCamaraComercioDE.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     */
    public void agregarNuevaDetalleEmpresa() {
        if (validarDatosNullDetalleEmpresa(1) == true) {
            if (validarFechaCamaraComercio(1) == true) {
                cambiosPagina = false;
                //CERRAR FILTRADO
                if (bandera == 1) {
                    altoTabla = "260";
                    detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
                    detalleEmpresa.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
                    detalleTipoDocumento.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
                    detalleTipo.setFilterStyle("display: none; visibility: hidden;");
                    detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
                    detalleDireccion.setFilterStyle("display: none; visibility: hidden;");
                    detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
                    detalleCiudad.setFilterStyle("display: none; visibility: hidden;");
                    detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
                    detalleTelefono.setFilterStyle("display: none; visibility: hidden;");
                    detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
                    detalleFax.setFilterStyle("display: none; visibility: hidden;");
                    detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
                    detalleNombreRepresentante.setFilterStyle("display: none; visibility: hidden;");
                    detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
                    detalleDocumentoRepresentane.setFilterStyle("display: none; visibility: hidden;");
                    detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
                    detalleCiudadDocumento.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
                    detalleTipoNit.setFilterStyle("display: none; visibility: hidden;");
                    detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
                    detalleDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                    detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
                    detalleGerenteGeneral.setFilterStyle("display: none; visibility: hidden;");
                    detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
                    detallePersonaFirma.setFilterStyle("display: none; visibility: hidden;");
                    detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
                    detalleCargoFirma.setFilterStyle("display: none; visibility: hidden;");
                    detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
                    detalleEmail.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
                    detalleTipoZona.setFilterStyle("display: none; visibility: hidden;");
                    detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
                    detalleCIIU.setFilterStyle("display: none; visibility: hidden;");
                    detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
                    detalleActEconomica.setFilterStyle("display: none; visibility: hidden;");
                    detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
                    detalleSubGerente.setFilterStyle("display: none; visibility: hidden;");
                    detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
                    detalleArquitecto.setFilterStyle("display: none; visibility: hidden;");
                    detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
                    detalleCargoArquitecto.setFilterStyle("display: none; visibility: hidden;");
                    detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
                    detalleRepresentante.setFilterStyle("display: none; visibility: hidden;");
                    detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
                    detallePlanilla.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
                    detalleTipoPersona.setFilterStyle("display: none; visibility: hidden;");
                    detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
                    detalleNaturalezaJ.setFilterStyle("display: none; visibility: hidden;");
                    detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
                    detalleClaseAportante.setFilterStyle("display: none; visibility: hidden;");
                    detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
                    detalleFormaPresentacion.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
                    detalleTipoAportante.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
                    detalleTipoAccion.setFilterStyle("display: none; visibility: hidden;");
                    detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
                    detalleFechaComercio.setFilterStyle("display: none; visibility: hidden;");
                    detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
                    detalleAnosParafiscal.setFilterStyle("display: none; visibility: hidden;");
                    detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
                    detalleReformaExonera.setFilterStyle("display: none; visibility: hidden;");
                    detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
                    detallePilaMultinea.setFilterStyle("display: none; visibility: hidden;");
                    detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
                    detalleSolidaridadFosyga.setFilterStyle("display: none; visibility: hidden;");
                    detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
                    detalleExoneraLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
                    detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
                    detalleReportaLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
                    bandera = 0;
                    filtrarListaDetallesEmpresas = null;
                    tipoLista = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevaDetalleEmpresa.setSecuencia(l);
                if (listaDetallesEmpresas == null) {
                    listaDetallesEmpresas = new ArrayList<DetallesEmpresas>();
                }
                listaDetallesEmpresas.add(nuevaDetalleEmpresa);
                listDetallesEmpresasCrear.add(nuevaDetalleEmpresa);
                //
                nuevaDetalleEmpresa = new DetallesEmpresas();
                nuevaDetalleEmpresa.setCiudad(new Ciudades());
                nuevaDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
                nuevaDetalleEmpresa.setEmpresa(new Empresas());
                nuevaDetalleEmpresa.setGerentegeneral(new Empleados());
                nuevaDetalleEmpresa.getGerentegeneral().setPersona(new Personas());
                nuevaDetalleEmpresa.setRepresentantecir(new Empleados());
                nuevaDetalleEmpresa.getRepresentantecir().setPersona(new Personas());
                nuevaDetalleEmpresa.setSubgerente(new Empleados());
                nuevaDetalleEmpresa.getSubgerente().setPersona(new Personas());
                nuevaDetalleEmpresa.setCargofirmaconstancia(new Cargos());
                nuevaDetalleEmpresa.setPersonafirmaconstancia(new Personas());
                //
                if (guardado == true) {
                    guardado = false;
                }
                index = -1;
                secRegistro = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosDetalleEmpresa");
                context.execute("NuevoRegistroDetalleEmpresa.hide()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNull.show()");
        }
    }

    public void limpiarNuevaDetalleEmpresa() {
        nuevaDetalleEmpresa = new DetallesEmpresas();
        nuevaDetalleEmpresa.setCiudad(new Ciudades());
        nuevaDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        nuevaDetalleEmpresa.setEmpresa(new Empresas());
        nuevaDetalleEmpresa.setGerentegeneral(new Empleados());
        nuevaDetalleEmpresa.getGerentegeneral().setPersona(new Personas());
        nuevaDetalleEmpresa.setRepresentantecir(new Empleados());
        nuevaDetalleEmpresa.getRepresentantecir().setPersona(new Personas());
        nuevaDetalleEmpresa.setSubgerente(new Empleados());
        nuevaDetalleEmpresa.getSubgerente().setPersona(new Personas());
        nuevaDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        nuevaDetalleEmpresa.setPersonafirmaconstancia(new Personas());
    }

    public void cancelarNuevaDetalleEmpresa() {
        nuevaDetalleEmpresa = new DetallesEmpresas();
        nuevaDetalleEmpresa.setCiudad(new Ciudades());
        nuevaDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        nuevaDetalleEmpresa.setEmpresa(new Empresas());
        nuevaDetalleEmpresa.setGerentegeneral(new Empleados());
        nuevaDetalleEmpresa.getGerentegeneral().setPersona(new Personas());
        nuevaDetalleEmpresa.setRepresentantecir(new Empleados());
        nuevaDetalleEmpresa.getRepresentantecir().setPersona(new Personas());
        nuevaDetalleEmpresa.setSubgerente(new Empleados());
        nuevaDetalleEmpresa.getSubgerente().setPersona(new Personas());
        nuevaDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        nuevaDetalleEmpresa.setPersonafirmaconstancia(new Personas());
        index = -1;
        secRegistro = null;
    }

    public void verificarDuplicarDetalleEmpresa() {
        if (index >= 0) {
            duplicarDetalleEmpresaD();
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     */
    public void duplicarDetalleEmpresaD() {
        if (index >= 0) {
            duplicarDetalleEmpresa = new DetallesEmpresas();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarDetalleEmpresa.setSecuencia(l);
                duplicarDetalleEmpresa.setEmpresa(listaDetallesEmpresas.get(index).getEmpresa());
                duplicarDetalleEmpresa.setTipodocumento(listaDetallesEmpresas.get(index).getTipodocumento());
                duplicarDetalleEmpresa.setTipo(listaDetallesEmpresas.get(index).getTipo());
                duplicarDetalleEmpresa.setDireccion(listaDetallesEmpresas.get(index).getDireccion());
                duplicarDetalleEmpresa.setCiudad(listaDetallesEmpresas.get(index).getCiudad());
                duplicarDetalleEmpresa.setTelefono(listaDetallesEmpresas.get(index).getTelefono());
                duplicarDetalleEmpresa.setFax(listaDetallesEmpresas.get(index).getFax());
                duplicarDetalleEmpresa.setNombrerepresentante(listaDetallesEmpresas.get(index).getNombrerepresentante());
                duplicarDetalleEmpresa.setDocumentorepresentante(listaDetallesEmpresas.get(index).getDocumentorepresentante());
                duplicarDetalleEmpresa.setCiudaddocumentorepresentante(listaDetallesEmpresas.get(index).getCiudaddocumentorepresentante());
                duplicarDetalleEmpresa.setTiponit(listaDetallesEmpresas.get(index).getTiponit());
                duplicarDetalleEmpresa.setDigitoverificacion(listaDetallesEmpresas.get(index).getDigitoverificacion());
                duplicarDetalleEmpresa.setGerentegeneral(listaDetallesEmpresas.get(index).getGerentegeneral());
                duplicarDetalleEmpresa.setPersonafirmaconstancia(listaDetallesEmpresas.get(index).getPersonafirmaconstancia());
                duplicarDetalleEmpresa.setCargofirmaconstancia(listaDetallesEmpresas.get(index).getCargofirmaconstancia());
                duplicarDetalleEmpresa.setEmail(listaDetallesEmpresas.get(index).getEmail());
                duplicarDetalleEmpresa.setZona(listaDetallesEmpresas.get(index).getZona());
                duplicarDetalleEmpresa.setCiiu(listaDetallesEmpresas.get(index).getCiiu());
                duplicarDetalleEmpresa.setActividadeconomica(listaDetallesEmpresas.get(index).getActividadeconomica());
                duplicarDetalleEmpresa.setSubgerente(listaDetallesEmpresas.get(index).getSubgerente());
                duplicarDetalleEmpresa.setArquitecto(listaDetallesEmpresas.get(index).getArquitecto());
                duplicarDetalleEmpresa.setCargoarquitecto(listaDetallesEmpresas.get(index).getCargoarquitecto());
                duplicarDetalleEmpresa.setRepresentantecir(listaDetallesEmpresas.get(index).getRepresentantecir());
                duplicarDetalleEmpresa.setPilaultimaplanilla(listaDetallesEmpresas.get(index).getPilaultimaplanilla());
                duplicarDetalleEmpresa.setTipopersona(listaDetallesEmpresas.get(index).getTipopersona());
                duplicarDetalleEmpresa.setNaturalezajuridica(listaDetallesEmpresas.get(index).getNaturalezajuridica());
                duplicarDetalleEmpresa.setClaseaportante(listaDetallesEmpresas.get(index).getClaseaportante());
                duplicarDetalleEmpresa.setFormapresentacion(listaDetallesEmpresas.get(index).getFormapresentacion());
                duplicarDetalleEmpresa.setTipoaportante(listaDetallesEmpresas.get(index).getTipoaportante());
                duplicarDetalleEmpresa.setTipoaccion(listaDetallesEmpresas.get(index).getTipoaccion());
                duplicarDetalleEmpresa.setFechacamaracomercio(listaDetallesEmpresas.get(index).getFechacamaracomercio());
                duplicarDetalleEmpresa.setAnosinicialesexentoprf(listaDetallesEmpresas.get(index).getAnosinicialesexentoprf());
                duplicarDetalleEmpresa.setCheckExoneraLnsTarifaAfpPatron(listaDetallesEmpresas.get(index).isCheckExoneraLnsTarifaAfpPatron());
                duplicarDetalleEmpresa.setCheckPilaSsaMultilineasln(listaDetallesEmpresas.get(index).isCheckPilaSsaMultilineasln());
                duplicarDetalleEmpresa.setCheckReformaExoneraIcbfSenaSalud(listaDetallesEmpresas.get(index).isCheckReformaExoneraIcbfSenaSalud());
                duplicarDetalleEmpresa.setCheckReportaLnsTarifaAfpEspecial(listaDetallesEmpresas.get(index).isCheckReportaLnsTarifaAfpEspecial());
                duplicarDetalleEmpresa.setCheckSolidaridadFosygaeExentoPrf(listaDetallesEmpresas.get(index).isCheckSolidaridadFosygaeExentoPrf());
            }
            if (tipoLista == 1) {
                duplicarDetalleEmpresa.setSecuencia(l);
                duplicarDetalleEmpresa.setEmpresa(filtrarListaDetallesEmpresas.get(index).getEmpresa());
                duplicarDetalleEmpresa.setTipodocumento(filtrarListaDetallesEmpresas.get(index).getTipodocumento());
                duplicarDetalleEmpresa.setTipo(filtrarListaDetallesEmpresas.get(index).getTipo());
                duplicarDetalleEmpresa.setDireccion(filtrarListaDetallesEmpresas.get(index).getDireccion());
                duplicarDetalleEmpresa.setCiudad(filtrarListaDetallesEmpresas.get(index).getCiudad());
                duplicarDetalleEmpresa.setTelefono(filtrarListaDetallesEmpresas.get(index).getTelefono());
                duplicarDetalleEmpresa.setFax(filtrarListaDetallesEmpresas.get(index).getFax());
                duplicarDetalleEmpresa.setNombrerepresentante(filtrarListaDetallesEmpresas.get(index).getNombrerepresentante());
                duplicarDetalleEmpresa.setDocumentorepresentante(filtrarListaDetallesEmpresas.get(index).getDocumentorepresentante());
                duplicarDetalleEmpresa.setCiudaddocumentorepresentante(filtrarListaDetallesEmpresas.get(index).getCiudaddocumentorepresentante());
                duplicarDetalleEmpresa.setTiponit(filtrarListaDetallesEmpresas.get(index).getTiponit());
                duplicarDetalleEmpresa.setDigitoverificacion(filtrarListaDetallesEmpresas.get(index).getDigitoverificacion());
                duplicarDetalleEmpresa.setGerentegeneral(filtrarListaDetallesEmpresas.get(index).getGerentegeneral());
                duplicarDetalleEmpresa.setPersonafirmaconstancia(filtrarListaDetallesEmpresas.get(index).getPersonafirmaconstancia());
                duplicarDetalleEmpresa.setCargofirmaconstancia(filtrarListaDetallesEmpresas.get(index).getCargofirmaconstancia());
                duplicarDetalleEmpresa.setEmail(filtrarListaDetallesEmpresas.get(index).getEmail());
                duplicarDetalleEmpresa.setZona(filtrarListaDetallesEmpresas.get(index).getZona());
                duplicarDetalleEmpresa.setCiiu(filtrarListaDetallesEmpresas.get(index).getCiiu());
                duplicarDetalleEmpresa.setActividadeconomica(filtrarListaDetallesEmpresas.get(index).getActividadeconomica());
                duplicarDetalleEmpresa.setSubgerente(filtrarListaDetallesEmpresas.get(index).getSubgerente());
                duplicarDetalleEmpresa.setArquitecto(filtrarListaDetallesEmpresas.get(index).getArquitecto());
                duplicarDetalleEmpresa.setCargoarquitecto(filtrarListaDetallesEmpresas.get(index).getCargoarquitecto());
                duplicarDetalleEmpresa.setRepresentantecir(filtrarListaDetallesEmpresas.get(index).getRepresentantecir());
                duplicarDetalleEmpresa.setPilaultimaplanilla(filtrarListaDetallesEmpresas.get(index).getPilaultimaplanilla());
                duplicarDetalleEmpresa.setTipopersona(filtrarListaDetallesEmpresas.get(index).getTipopersona());
                duplicarDetalleEmpresa.setNaturalezajuridica(filtrarListaDetallesEmpresas.get(index).getNaturalezajuridica());
                duplicarDetalleEmpresa.setClaseaportante(filtrarListaDetallesEmpresas.get(index).getClaseaportante());
                duplicarDetalleEmpresa.setFormapresentacion(filtrarListaDetallesEmpresas.get(index).getFormapresentacion());
                duplicarDetalleEmpresa.setTipoaportante(filtrarListaDetallesEmpresas.get(index).getTipoaportante());
                duplicarDetalleEmpresa.setTipoaccion(filtrarListaDetallesEmpresas.get(index).getTipoaccion());
                duplicarDetalleEmpresa.setFechacamaracomercio(filtrarListaDetallesEmpresas.get(index).getFechacamaracomercio());
                duplicarDetalleEmpresa.setAnosinicialesexentoprf(filtrarListaDetallesEmpresas.get(index).getAnosinicialesexentoprf());
                duplicarDetalleEmpresa.setCheckExoneraLnsTarifaAfpPatron(filtrarListaDetallesEmpresas.get(index).isCheckExoneraLnsTarifaAfpPatron());
                duplicarDetalleEmpresa.setCheckPilaSsaMultilineasln(filtrarListaDetallesEmpresas.get(index).isCheckPilaSsaMultilineasln());
                duplicarDetalleEmpresa.setCheckReformaExoneraIcbfSenaSalud(filtrarListaDetallesEmpresas.get(index).isCheckReformaExoneraIcbfSenaSalud());
                duplicarDetalleEmpresa.setCheckReportaLnsTarifaAfpEspecial(filtrarListaDetallesEmpresas.get(index).isCheckReportaLnsTarifaAfpEspecial());
                duplicarDetalleEmpresa.setCheckSolidaridadFosygaeExentoPrf(filtrarListaDetallesEmpresas.get(index).isCheckSolidaridadFosygaeExentoPrf());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarDetalleEmpresa");
            context.execute("DuplicarRegistroDetalleEmpresa.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicarDetalleEmpresa() {
        if (validarDatosNullDetalleEmpresa(2) == true) {
            if (validarFechaCamaraComercio(2) == true) {
                cambiosPagina = false;
                k++;
                l = BigInteger.valueOf(k);
                duplicarDetalleEmpresa.setSecuencia(l);
                if (listaDetallesEmpresas == null) {
                    listaDetallesEmpresas = new ArrayList<DetallesEmpresas>();
                }
                listaDetallesEmpresas.add(duplicarDetalleEmpresa);
                listDetallesEmpresasCrear.add(duplicarDetalleEmpresa);
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    altoTabla = "260";
                    detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
                    detalleEmpresa.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
                    detalleTipoDocumento.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
                    detalleTipo.setFilterStyle("display: none; visibility: hidden;");
                    detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
                    detalleDireccion.setFilterStyle("display: none; visibility: hidden;");
                    detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
                    detalleCiudad.setFilterStyle("display: none; visibility: hidden;");
                    detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
                    detalleTelefono.setFilterStyle("display: none; visibility: hidden;");
                    detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
                    detalleFax.setFilterStyle("display: none; visibility: hidden;");
                    detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
                    detalleNombreRepresentante.setFilterStyle("display: none; visibility: hidden;");
                    detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
                    detalleDocumentoRepresentane.setFilterStyle("display: none; visibility: hidden;");
                    detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
                    detalleCiudadDocumento.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
                    detalleTipoNit.setFilterStyle("display: none; visibility: hidden;");
                    detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
                    detalleDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                    detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
                    detalleGerenteGeneral.setFilterStyle("display: none; visibility: hidden;");
                    detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
                    detallePersonaFirma.setFilterStyle("display: none; visibility: hidden;");
                    detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
                    detalleCargoFirma.setFilterStyle("display: none; visibility: hidden;");
                    detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
                    detalleEmail.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
                    detalleTipoZona.setFilterStyle("display: none; visibility: hidden;");
                    detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
                    detalleCIIU.setFilterStyle("display: none; visibility: hidden;");
                    detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
                    detalleActEconomica.setFilterStyle("display: none; visibility: hidden;");
                    detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
                    detalleSubGerente.setFilterStyle("display: none; visibility: hidden;");
                    detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
                    detalleArquitecto.setFilterStyle("display: none; visibility: hidden;");
                    detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
                    detalleCargoArquitecto.setFilterStyle("display: none; visibility: hidden;");
                    detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
                    detalleRepresentante.setFilterStyle("display: none; visibility: hidden;");
                    detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
                    detallePlanilla.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
                    detalleTipoPersona.setFilterStyle("display: none; visibility: hidden;");
                    detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
                    detalleNaturalezaJ.setFilterStyle("display: none; visibility: hidden;");
                    detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
                    detalleClaseAportante.setFilterStyle("display: none; visibility: hidden;");
                    detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
                    detalleFormaPresentacion.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
                    detalleTipoAportante.setFilterStyle("display: none; visibility: hidden;");
                    detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
                    detalleTipoAccion.setFilterStyle("display: none; visibility: hidden;");
                    detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
                    detalleFechaComercio.setFilterStyle("display: none; visibility: hidden;");
                    detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
                    detalleAnosParafiscal.setFilterStyle("display: none; visibility: hidden;");
                    detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
                    detalleReformaExonera.setFilterStyle("display: none; visibility: hidden;");
                    detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
                    detallePilaMultinea.setFilterStyle("display: none; visibility: hidden;");
                    detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
                    detalleSolidaridadFosyga.setFilterStyle("display: none; visibility: hidden;");
                    detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
                    detalleExoneraLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
                    detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
                    detalleReportaLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
                    bandera = 0;
                    filtrarListaDetallesEmpresas = null;
                    tipoLista = 0;
                }
                duplicarDetalleEmpresa = new DetallesEmpresas();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosDetalleEmpresa");
                context.execute("DuplicarRegistroDetalleEmpresa.hide()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNull.show()");
        }
    }

    public void cancelarDuplicadoDetalleEmpresa() {
        duplicarDetalleEmpresa = new DetallesEmpresas();
        duplicarDetalleEmpresa.setCiudad(new Ciudades());
        duplicarDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        duplicarDetalleEmpresa.setEmpresa(new Empresas());
        duplicarDetalleEmpresa.setGerentegeneral(new Empleados());
        duplicarDetalleEmpresa.setRepresentantecir(new Empleados());
        duplicarDetalleEmpresa.setArquitecto(new Empleados());
        duplicarDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        duplicarDetalleEmpresa.setPersonafirmaconstancia(new Personas());
        index = -1;
        secRegistro = null;
    }

    public void limpiarDuplicadoDetalleEmpresa() {
        duplicarDetalleEmpresa = new DetallesEmpresas();
        duplicarDetalleEmpresa.setCiudad(new Ciudades());
        duplicarDetalleEmpresa.setCiudaddocumentorepresentante(new Ciudades());
        duplicarDetalleEmpresa.setEmpresa(new Empresas());
        duplicarDetalleEmpresa.setGerentegeneral(new Empleados());
        duplicarDetalleEmpresa.setRepresentantecir(new Empleados());
        duplicarDetalleEmpresa.setArquitecto(new Empleados());
        duplicarDetalleEmpresa.setCargofirmaconstancia(new Cargos());
        duplicarDetalleEmpresa.setPersonafirmaconstancia(new Personas());
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoDetalleEmpresa() {
        if (index >= 0) {
            borrarDetalleEmpresa();
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarDetalleEmpresa() {
        cambiosPagina = false;
        if (tipoLista == 0) {
            if (!listDetallesEmpresasModificar.isEmpty() && listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                int modIndex = listDetallesEmpresasModificar.indexOf(listaDetallesEmpresas.get(index));
                listDetallesEmpresasModificar.remove(modIndex);
                listDetallesEmpresasBorrar.add(listaDetallesEmpresas.get(index));
            } else if (!listDetallesEmpresasCrear.isEmpty() && listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                int crearIndex = listDetallesEmpresasCrear.indexOf(listaDetallesEmpresas.get(index));
                listDetallesEmpresasCrear.remove(crearIndex);
            } else {
                listDetallesEmpresasBorrar.add(listaDetallesEmpresas.get(index));
            }
            listaDetallesEmpresas.remove(index);
        }
        if (tipoLista == 1) {
            if (!listDetallesEmpresasModificar.isEmpty() && listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                int modIndex = listDetallesEmpresasModificar.indexOf(filtrarListaDetallesEmpresas.get(index));
                listDetallesEmpresasModificar.remove(modIndex);
                listDetallesEmpresasBorrar.add(filtrarListaDetallesEmpresas.get(index));
            } else if (!listDetallesEmpresasCrear.isEmpty() && listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                int crearIndex = listDetallesEmpresasCrear.indexOf(filtrarListaDetallesEmpresas.get(index));
                listDetallesEmpresasCrear.remove(crearIndex);
            } else {
                listDetallesEmpresasBorrar.add(filtrarListaDetallesEmpresas.get(index));
            }
            int VPIndex = listaDetallesEmpresas.indexOf(filtrarListaDetallesEmpresas.get(index));
            listaDetallesEmpresas.remove(VPIndex);
            filtrarListaDetallesEmpresas.remove(index);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosDetalleEmpresa");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
        }

    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        filtradoDetalleEmpresa();
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoDetalleEmpresa() {
        if (bandera == 0) {
            altoTabla = "238";
            detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
            detalleEmpresa.setFilterStyle("width: 90px");
            detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
            detalleTipoDocumento.setFilterStyle("width: 70px");
            detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
            detalleTipo.setFilterStyle("width: 70px");
            detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
            detalleDireccion.setFilterStyle("width: 90px");
            detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
            detalleCiudad.setFilterStyle("width: 90px");
            detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
            detalleTelefono.setFilterStyle("width: 70px");
            detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
            detalleFax.setFilterStyle("width: 70px");
            detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
            detalleNombreRepresentante.setFilterStyle("width: 90px");
            detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
            detalleDocumentoRepresentane.setFilterStyle("width: 90px");
            detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
            detalleCiudadDocumento.setFilterStyle("width: 90px");
            detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
            detalleTipoNit.setFilterStyle("width: 70px");
            detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
            detalleDigitoVerificacion.setFilterStyle("width: 90px");
            detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
            detalleGerenteGeneral.setFilterStyle("width: 90px");
            detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
            detallePersonaFirma.setFilterStyle("width: 90px");
            detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
            detalleCargoFirma.setFilterStyle("width: 90px");
            detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
            detalleEmail.setFilterStyle("width: 90px");
            detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
            detalleTipoZona.setFilterStyle("width: 70px");
            detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
            detalleCIIU.setFilterStyle("width: 90px");
            detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
            detalleActEconomica.setFilterStyle("width: 90px");
            detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
            detalleSubGerente.setFilterStyle("width: 90px");
            detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
            detalleArquitecto.setFilterStyle("width: 90px");
            detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
            detalleCargoArquitecto.setFilterStyle("width: 90px");
            detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
            detalleRepresentante.setFilterStyle("width: 90px");
            detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
            detallePlanilla.setFilterStyle("width: 90px");
            detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
            detalleTipoPersona.setFilterStyle("width: 90px");
            detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
            detalleNaturalezaJ.setFilterStyle("width: 90px");
            detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
            detalleClaseAportante.setFilterStyle("width: 90px");
            detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
            detalleFormaPresentacion.setFilterStyle("width: 90px");
            detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
            detalleTipoAportante.setFilterStyle("width: 90px");
            detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
            detalleTipoAccion.setFilterStyle("width: 90px");
            detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
            detalleFechaComercio.setFilterStyle("width: 90px");
            detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
            detalleAnosParafiscal.setFilterStyle("width: 90px");
            detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
            detalleReformaExonera.setFilterStyle("width: 15px");
            detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
            detallePilaMultinea.setFilterStyle("width: 15px");
            detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
            detalleSolidaridadFosyga.setFilterStyle("width: 15px");
            detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
            detalleExoneraLnsTarifa.setFilterStyle("width: 15px");
            detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
            detalleReportaLnsTarifa.setFilterStyle("width: 15px");
            RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "260";
            detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
            detalleEmpresa.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
            detalleTipoDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
            detalleTipo.setFilterStyle("display: none; visibility: hidden;");
            detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
            detalleDireccion.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
            detalleCiudad.setFilterStyle("display: none; visibility: hidden;");
            detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
            detalleTelefono.setFilterStyle("display: none; visibility: hidden;");
            detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
            detalleFax.setFilterStyle("display: none; visibility: hidden;");
            detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
            detalleNombreRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
            detalleDocumentoRepresentane.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
            detalleCiudadDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
            detalleTipoNit.setFilterStyle("display: none; visibility: hidden;");
            detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
            detalleDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
            detalleGerenteGeneral.setFilterStyle("display: none; visibility: hidden;");
            detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
            detallePersonaFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
            detalleCargoFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
            detalleEmail.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
            detalleTipoZona.setFilterStyle("display: none; visibility: hidden;");
            detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
            detalleCIIU.setFilterStyle("display: none; visibility: hidden;");
            detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
            detalleActEconomica.setFilterStyle("display: none; visibility: hidden;");
            detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
            detalleSubGerente.setFilterStyle("display: none; visibility: hidden;");
            detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
            detalleArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
            detalleCargoArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
            detalleRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
            detallePlanilla.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
            detalleTipoPersona.setFilterStyle("display: none; visibility: hidden;");
            detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
            detalleNaturalezaJ.setFilterStyle("display: none; visibility: hidden;");
            detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
            detalleClaseAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
            detalleFormaPresentacion.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
            detalleTipoAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
            detalleTipoAccion.setFilterStyle("display: none; visibility: hidden;");
            detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
            detalleFechaComercio.setFilterStyle("display: none; visibility: hidden;");
            detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
            detalleAnosParafiscal.setFilterStyle("display: none; visibility: hidden;");
            detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
            detalleReformaExonera.setFilterStyle("display: none; visibility: hidden;");
            detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
            detallePilaMultinea.setFilterStyle("display: none; visibility: hidden;");
            detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
            detalleSolidaridadFosyga.setFilterStyle("display: none; visibility: hidden;");
            detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
            detalleExoneraLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
            detalleReportaLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
            bandera = 0;
            filtrarListaDetallesEmpresas = null;
            tipoLista = 0;
        }

    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            altoTabla = "260";
            detalleEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmpresa");
            detalleEmpresa.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoDocumento");
            detalleTipoDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipo");
            detalleTipo.setFilterStyle("display: none; visibility: hidden;");
            detalleDireccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDireccion");
            detalleDireccion.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudad");
            detalleCiudad.setFilterStyle("display: none; visibility: hidden;");
            detalleTelefono = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTelefono");
            detalleTelefono.setFilterStyle("display: none; visibility: hidden;");
            detalleFax = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFax");
            detalleFax.setFilterStyle("display: none; visibility: hidden;");
            detalleNombreRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNombreRepresentante");
            detalleNombreRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detalleDocumentoRepresentane = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDocumentoRepresentane");
            detalleDocumentoRepresentane.setFilterStyle("display: none; visibility: hidden;");
            detalleCiudadDocumento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCiudadDocumento");
            detalleCiudadDocumento.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoNit");
            detalleTipoNit.setFilterStyle("display: none; visibility: hidden;");
            detalleDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleDigitoVerificacion");
            detalleDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            detalleGerenteGeneral = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleGerenteGeneral");
            detalleGerenteGeneral.setFilterStyle("display: none; visibility: hidden;");
            detallePersonaFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePersonaFirma");
            detallePersonaFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoFirma = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoFirma");
            detalleCargoFirma.setFilterStyle("display: none; visibility: hidden;");
            detalleEmail = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleEmail");
            detalleEmail.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoZona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoZona");
            detalleTipoZona.setFilterStyle("display: none; visibility: hidden;");
            detalleCIIU = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCIIU");
            detalleCIIU.setFilterStyle("display: none; visibility: hidden;");
            detalleActEconomica = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleActEconomica");
            detalleActEconomica.setFilterStyle("display: none; visibility: hidden;");
            detalleSubGerente = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSubGerente");
            detalleSubGerente.setFilterStyle("display: none; visibility: hidden;");
            detalleArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleArquitecto");
            detalleArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleCargoArquitecto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleCargoArquitecto");
            detalleCargoArquitecto.setFilterStyle("display: none; visibility: hidden;");
            detalleRepresentante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleRepresentante");
            detalleRepresentante.setFilterStyle("display: none; visibility: hidden;");
            detallePlanilla = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePlanilla");
            detallePlanilla.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoPersona = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoPersona");
            detalleTipoPersona.setFilterStyle("display: none; visibility: hidden;");
            detalleNaturalezaJ = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleNaturalezaJ");
            detalleNaturalezaJ.setFilterStyle("display: none; visibility: hidden;");
            detalleClaseAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleClaseAportante");
            detalleClaseAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleFormaPresentacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFormaPresentacion");
            detalleFormaPresentacion.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAportante = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAportante");
            detalleTipoAportante.setFilterStyle("display: none; visibility: hidden;");
            detalleTipoAccion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleTipoAccion");
            detalleTipoAccion.setFilterStyle("display: none; visibility: hidden;");
            detalleFechaComercio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleFechaComercio");
            detalleFechaComercio.setFilterStyle("display: none; visibility: hidden;");
            detalleAnosParafiscal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleAnosParafiscal");
            detalleAnosParafiscal.setFilterStyle("display: none; visibility: hidden;");
            detalleReformaExonera = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReformaExonera");
            detalleReformaExonera.setFilterStyle("display: none; visibility: hidden;");
            detallePilaMultinea = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detallePilaMultinea");
            detallePilaMultinea.setFilterStyle("display: none; visibility: hidden;");
            detalleSolidaridadFosyga = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleSolidaridadFosyga");
            detalleSolidaridadFosyga.setFilterStyle("display: none; visibility: hidden;");
            detalleExoneraLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleExoneraLnsTarifa");
            detalleExoneraLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            detalleReportaLnsTarifa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDetalleEmpresa:detalleReportaLnsTarifa");
            detalleReportaLnsTarifa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDetalleEmpresa");
            bandera = 0;
            filtrarListaDetallesEmpresas = null;
            tipoLista = 0;
        }
        listDetallesEmpresasBorrar.clear();
        listDetallesEmpresasCrear.clear();
        listDetallesEmpresasModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaDetallesEmpresas = null;
        guardado = true;
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        tipoActualizacion = -1;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) 

    /**
     *
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            index = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:EmpresasDialogo");
            context.execute("EmpresasDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:CiudadDialogo");
            context.execute("CiudadDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:CiudadDocumentoDialogo");
            context.execute("CiudadDocumentoDialogo.show()");
        } else if (dlg == 3) {
            context.update("form:GerenteDialogo");
            context.execute("GerenteDialogo.show()");
        } else if (dlg == 4) {
            context.update("form:PersonaFirmaDialogo");
            context.execute("PersonaFirmaDialogo.show()");
        } else if (dlg == 5) {
            context.update("form:CargoFirmaDialogo");
            context.execute("CargoFirmaDialogo.show()");
        } else if (dlg == 6) {
            context.update("form:SubGerenteDialogo");
            context.execute("SubGerenteDialogo.show()");
        } else if (dlg == 7) {
            context.update("form:RepresentanteDialogo");
            context.execute("RepresentanteDialogo.show()");
        }

    }

    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setEmpresa(empresaSeleccionada);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambiosPagina = false;
                permitirIndex = true;

            } else {
                filtrarListaDetallesEmpresas.get(index).setEmpresa(empresaSeleccionada);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                }
                cambiosPagina = false;
                permitirIndex = true;

            }
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setEmpresa(empresaSeleccionada);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaEmpresaDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setEmpresa(empresaSeleccionada);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarEmpresaDetalle");
        }
        filtrarLovEmpresas = null;
        empresaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:EmpresasDialogo");
        context.update("form:lovEmpresas");
        context.update("form:aceptarE");
        context.execute("EmpresasDialogo.hide()");
    }

    public void cancelarCambioEmpresa() {
        filtrarLovEmpresas = null;
        empresaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarCiudad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setCiudad(ciudadSeleccionada);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setCiudad(ciudadSeleccionada);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCiudadDetalle");
        }
        filtrarLovCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:CiudadDialogo");
        context.update("form:lovCiudad");
        context.update("form:aceptarC");
        context.execute("CiudadDialogo.hide()");
    }

    public void cancelarCambioCiudad() {
        filtrarLovCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarCiudadDocumento() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setCiudaddocumentorepresentante(ciudadSeleccionada);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setCiudaddocumentorepresentante(ciudadSeleccionada);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;

            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setCiudaddocumentorepresentante(ciudadSeleccionada);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCiudadDocumentoDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setCiudaddocumentorepresentante(ciudadSeleccionada);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCiudadDocumentoDetalle");
        }
        filtrarLovCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:CiudadDocumentoDialogo");
        context.update("form:lovCiudadDocumento");
        context.update("form:aceptarCD");
        context.execute("CiudadDocumentoDialogo.hide()");
    }

    public void cancelarCambioCiudadDocumento() {
        filtrarLovCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarGerente() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setGerentegeneral(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setGerentegeneral(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setGerentegeneral(empleadoSeleccionado);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaGerenteDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setGerentegeneral(empleadoSeleccionado);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarGerenteDetalle");
        }
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:GerenteDialogo");
        context.update("form:lovGerente");
        context.update("form:aceptarG");
        context.execute("GerenteDialogo.hide()");
    }

    public void cancelarCambioGerente() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarSubGerente() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setSubgerente(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setSubgerente(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setSubgerente(empleadoSeleccionado);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaSubGerenteDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setSubgerente(empleadoSeleccionado);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarSubGerenteDetalle");
        }
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:SubGerenteDialogo");
        context.update("form:lovSubGerente");
        context.update("form:aceptarSG");
        context.execute("SubGerenteDialogo.hide()");
    }

    public void cancelarCambioSubGerente() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarRepresentante() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setRepresentantecir(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setRepresentantecir(empleadoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setRepresentantecir(empleadoSeleccionado);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaRepresentanteDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setRepresentantecir(empleadoSeleccionado);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarRepresentanteDetalle");
        }
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:RepresentanteDialogo");
        context.update("form:lovRepresentante");
        context.update("form:aceptarR");
        context.execute("RepresentanteDialogo.hide()");
    }

    public void cancelarCambioRepresentante() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarPersona() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setPersonafirmaconstancia(personaSeleccionada);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setPersonafirmaconstancia(personaSeleccionada);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setPersonafirmaconstancia(personaSeleccionada);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaPersonaFirmaDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setPersonafirmaconstancia(personaSeleccionada);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarPersonaFirmaDetalle");
        }
        filtrarLovPersonas = null;
        personaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:PersonaFirmaDialogo");
        context.update("form:lovPersona");
        context.update("form:aceptarPF");
        context.execute("PersonaFirmaDialogo.hide()");
    }

    public void cancelarCambioPersona() {
        filtrarLovPersonas = null;
        personaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarCargo() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaDetallesEmpresas.get(index).setCargofirmaconstancia(cargoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(listaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(listaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(listaDetallesEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaDetallesEmpresas.get(index).setCargofirmaconstancia(cargoSeleccionado);
                if (!listDetallesEmpresasCrear.contains(filtrarListaDetallesEmpresas.get(index))) {
                    if (listDetallesEmpresasModificar.isEmpty()) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    } else if (!listDetallesEmpresasModificar.contains(filtrarListaDetallesEmpresas.get(index))) {
                        listDetallesEmpresasModificar.add(filtrarListaDetallesEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            permitirIndex = true;
            context.update("form:ACEPTAR");
            context.update(":form:datosDetalleEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevaDetalleEmpresa.setCargofirmaconstancia(cargoSeleccionado);
            context.update("formularioDialogos:nuevaDetalleEmpresa:nuevaCargoFirmaDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalleEmpresa.setCargofirmaconstancia(cargoSeleccionado);
            context.update("formularioDialogos:duplicarDetalleEmpresa:duplicarCargoFirmaDetalle");
        }
        filtrarLovCargos = null;
        cargoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:CargoFirmaDialogo");
        context.update("form:lovCargoFirma");
        context.update("form:aceptarCF");
        context.execute("CargoFirmaDialogo.hide()");
    }

    public void cancelarCambioCargo() {
        filtrarLovCargos = null;
        cargoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 0) {
                context.update("form:EmpresasDialogo");
                context.execute("EmpresasDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 4) {
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 9) {
                context.update("form:CiudadDocumentoDialogo");
                context.execute("CiudadDocumentoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 12) {
                context.update("form:GerenteDialogo");
                context.execute("GerenteDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 13) {
                context.update("form:PersonaFirmaDialogo");
                context.execute("PersonaFirmaDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 14) {
                context.update("form:CargoFirmaDialogo");
                context.execute("CargoFirmaDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 19) {
                context.update("form:SubGerenteDialogo");
                context.execute("SubGerenteDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 22) {
                context.update("form:RepresentanteDialogo");
                context.execute("RepresentanteDialogo.show()");
                tipoActualizacion = 0;
            }
        }

    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_DE();
    }

    /**
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_DE() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarDE:datosDetalleExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "DetallesEmpresas_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLS_EM();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_EM() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarDE:datosDetalleExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DetallesEmpresas_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaDetallesEmpresas != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DETALLESEMPRESAS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("DETALLESEMPRESAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GET - SET 
    public Empresas getActualEmpresa() {
        if (actualEmpresa == null) {
            actualEmpresa = new Empresas();
            actualEmpresa = administrarDetalleEmpresa.empresaActual(secEmpresa);
        }
        return actualEmpresa;
    }

    public void setActualEmpresa(Empresas actualEmpresa) {
        this.actualEmpresa = actualEmpresa;
    }

    public List<DetallesEmpresas> getListaDetallesEmpresas() {
        try {
            if (listaDetallesEmpresas == null) {
                listaDetallesEmpresas = new ArrayList<DetallesEmpresas>();
                listaDetallesEmpresas = administrarDetalleEmpresa.listaDetallesEmpresasPorSecuencia(secEmpresa);
                for (int i = 0; i < listaDetallesEmpresas.size(); i++) {
                    if (listaDetallesEmpresas.get(i).getCiudaddocumentorepresentante() == null) {
                        listaDetallesEmpresas.get(i).setCiudaddocumentorepresentante(new Ciudades());
                    }
                    if (listaDetallesEmpresas.get(i).getCiudad() == null) {
                        listaDetallesEmpresas.get(i).setCiudad(new Ciudades());
                    }
                    if (listaDetallesEmpresas.get(i).getGerentegeneral() == null) {
                        listaDetallesEmpresas.get(i).setGerentegeneral(new Empleados());
                        listaDetallesEmpresas.get(i).getGerentegeneral().setPersona(new Personas());
                    }
                    if (listaDetallesEmpresas.get(i).getPersonafirmaconstancia() == null) {
                        listaDetallesEmpresas.get(i).setPersonafirmaconstancia(new Personas());
                    }
                    if (listaDetallesEmpresas.get(i).getCargofirmaconstancia() == null) {
                        listaDetallesEmpresas.get(i).setCargofirmaconstancia(new Cargos());
                    }
                    if (listaDetallesEmpresas.get(i).getRepresentantecir() == null) {
                        listaDetallesEmpresas.get(i).setRepresentantecir(new Empleados());
                        listaDetallesEmpresas.get(i).getRepresentantecir().setPersona(new Personas());
                    }
                    if (listaDetallesEmpresas.get(i).getSubgerente() == null) {
                        listaDetallesEmpresas.get(i).setSubgerente(new Empleados());
                        listaDetallesEmpresas.get(i).getSubgerente().setPersona(new Personas());
                    }
                }
            }
            return listaDetallesEmpresas;
        } catch (Exception e) {
            System.out.println("Error en getListaDetallesEmpresas : " + e.toString());
            return null;
        }
    }

    public void setListaDetallesEmpresas(List<DetallesEmpresas> setListaDetallesEmpresas) {
        this.listaDetallesEmpresas = setListaDetallesEmpresas;
    }

    public List<DetallesEmpresas> getFiltrarListaDetallesEmpresas() {
        return filtrarListaDetallesEmpresas;
    }

    public void setFiltrarListaDetallesEmpresas(List<DetallesEmpresas> setFiltrarListaDetallesEmpresas) {
        this.filtrarListaDetallesEmpresas = setFiltrarListaDetallesEmpresas;
    }

    public List<Empresas> getLovEmpresas() {
        try {
            if (lovEmpresas == null) {
                lovEmpresas = new ArrayList<Empresas>();
                lovEmpresas = administrarDetalleEmpresa.lovEmpresas();
            }
            return lovEmpresas;
        } catch (Exception e) {
            System.out.println("Error getLovEmpresas " + e.toString());
            return null;
        }
    }

    public void setLovEmpresas(List<Empresas> setLovEmpresas) {
        this.lovEmpresas = setLovEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas setEmpresaSeleccionada) {
        this.empresaSeleccionada = setEmpresaSeleccionada;
    }

    public List<Empresas> getFiltrarLovEmpresas() {
        return filtrarLovEmpresas;
    }

    public void setFiltrarLovEmpresas(List<Empresas> setFiltrarLovEmpresas) {
        this.filtrarLovEmpresas = setFiltrarLovEmpresas;
    }

    public List<Ciudades> getLovCiudades() {
        try {
            if (lovCiudades == null) {
                lovCiudades = new ArrayList<Ciudades>();
                lovCiudades = administrarDetalleEmpresa.lovCiudades();
            }
            return lovCiudades;
        } catch (Exception e) {
            System.out.println("Error getLovCiudades " + e.toString());
            return null;
        }
    }

    public void setLovCiudades(List<Ciudades> setLovCiudades) {
        this.lovCiudades = setLovCiudades;
    }

    public Ciudades getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudades setCiudadSeleccionada) {
        this.ciudadSeleccionada = setCiudadSeleccionada;
    }

    public List<Ciudades> getFiltrarLovCiudades() {
        return filtrarLovCiudades;
    }

    public void setFiltrarLovCiudades(List<Ciudades> setFiltrarLovCiudades) {
        this.filtrarLovCiudades = setFiltrarLovCiudades;
    }

    public List<DetallesEmpresas> getListDetallesEmpresasModificar() {
        return listDetallesEmpresasModificar;
    }

    public void setListDetallesEmpresasModificar(List<DetallesEmpresas> setListDetallesEmpresasModificar) {
        this.listDetallesEmpresasModificar = setListDetallesEmpresasModificar;
    }

    public DetallesEmpresas getNuevaDetalleEmpresa() {
        return nuevaDetalleEmpresa;
    }

    public void setNuevaDetalleEmpresa(DetallesEmpresas setNuevaDetalleEmpresa) {
        this.nuevaDetalleEmpresa = setNuevaDetalleEmpresa;
    }

    public List<DetallesEmpresas> getListDetallesEmpresasBorrar() {
        return listDetallesEmpresasBorrar;
    }

    public void setListDetallesEmpresasBorrar(List<DetallesEmpresas> setListDetallesEmpresasBorrar) {
        this.listDetallesEmpresasBorrar = setListDetallesEmpresasBorrar;
    }

    public DetallesEmpresas getEditarDetalleEmpresa() {
        return editarDetalleEmpresa;
    }

    public void setEditarDetalleEmpresa(DetallesEmpresas setEditarDetalleEmpresa) {
        this.editarDetalleEmpresa = setEditarDetalleEmpresa;
    }

    public DetallesEmpresas getDuplicarDetalleEmpresa() {
        return duplicarDetalleEmpresa;
    }

    public void setDuplicarDetalleEmpresa(DetallesEmpresas setDuplicarDetalleEmpresa) {
        this.duplicarDetalleEmpresa = setDuplicarDetalleEmpresa;
    }

    public List<DetallesEmpresas> getListDetallesEmpresasCrear() {
        return listDetallesEmpresasCrear;
    }

    public void setListDetallesEmpresasCrear(List<DetallesEmpresas> setListDetallesEmpresasCrear) {
        this.listDetallesEmpresasCrear = setListDetallesEmpresasCrear;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<Empleados> getLovEmpleados() {
        try {
            if (lovEmpleados == null) {
                lovEmpleados = new ArrayList<Empleados>();
                lovEmpleados = administrarDetalleEmpresa.lovEmpleados();
            }
            return lovEmpleados;
        } catch (Exception e) {
            System.out.println("Error en getLovEmpleados ... " + e.toString());
            return null;
        }

    }

    public void setLovEmpleados(List<Empleados> setLovEmpleados) {
        this.lovEmpleados = setLovEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados setEmpleadoSeleccionado) {
        this.empleadoSeleccionado = setEmpleadoSeleccionado;
    }

    public List<Empleados> getFiltrarLovEmpleados() {
        return filtrarLovEmpleados;
    }

    public void setFiltrarLovEmpleados(List<Empleados> setFiltrarLovEmpleados) {
        this.filtrarLovEmpleados = setFiltrarLovEmpleados;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public List<Personas> getLovPersonas() {
        if (lovPersonas == null) {
            lovPersonas = new ArrayList<Personas>();
            lovPersonas = administrarDetalleEmpresa.lovPersonas();
        }
        return lovPersonas;
    }

    public void setLovPersonas(List<Personas> setLovPersonas) {
        this.lovPersonas = setLovPersonas;
    }

    public Personas getPersonaSeleccionada() {
        return personaSeleccionada;
    }

    public void setPersonaSeleccionada(Personas setPersonaSeleccionada) {
        this.personaSeleccionada = setPersonaSeleccionada;
    }

    public List<Personas> getFiltrarLovPersonas() {
        return filtrarLovPersonas;
    }

    public void setFiltrarLovPersonas(List<Personas> setFiltrarLovPersonas) {
        this.filtrarLovPersonas = setFiltrarLovPersonas;
    }

    public List<Cargos> getLovCargos() {
        if (lovCargos == null) {
            lovCargos = administrarDetalleEmpresa.lovCargos();
        }
        return lovCargos;
    }

    public void setLovCargos(List<Cargos> lovCargos) {
        this.lovCargos = lovCargos;
    }

    public Cargos getCargoSeleccionado() {
        return cargoSeleccionado;
    }

    public void setCargoSeleccionado(Cargos cargoSeleccionado) {
        this.cargoSeleccionado = cargoSeleccionado;
    }

    public List<Cargos> getFiltrarLovCargos() {
        return filtrarLovCargos;
    }

    public void setFiltrarLovCargos(List<Cargos> filtrarLovCargos) {
        this.filtrarLovCargos = filtrarLovCargos;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

}

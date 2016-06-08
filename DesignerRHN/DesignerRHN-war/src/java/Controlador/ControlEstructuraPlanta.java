package Controlador;

import Entidades.CentrosCostos;
import Entidades.Estructuras;
import Entidades.Organigramas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEstructurasPlantasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlEstructuraPlanta implements Serializable {

    @EJB
    AdministrarEstructurasPlantasInterface administrarEstructuraPlanta;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Organigramas> listaOrganigramas;
    private List<Organigramas> filtrarListaOrganigramas;
    private Organigramas organigramaSeleccionado;
    //
    private List<Estructuras> listaEstructuras;
    private List<Estructuras> filtrarListaEstructuras;
    private Estructuras estructuraSeleccionada;
    //
    //Activo/Desactivo Crtl + F11
    private int bandera, banderaEstructura;
    //Columnas Tabla 
    private Column organigramaFecha, organigramaCodigo, organigramaEmpresa, organigramaNIT, organigramaEstado;
    private Column estructuraCodigo, estructuraEstructura, estructuraCantidadControlar, estructuraCantidadActivo, estructuraCentroCosto, estructuraEstructuraPadre;
    //Otros
    private boolean aceptar;
    //modificar
    private List<Organigramas> listOrganigramasModificar;
    private List<Estructuras> listEstructurasModificar;
    private boolean guardado, guardadoEstructura;
    //crear 
    private Estructuras nuevoEstructura;
    private List<Estructuras> listEstructurasCrear;
    private BigInteger l;
    private int k;
    //borrar 
    private List<Estructuras> listEstructurasBorrar;
    //editar celda
    private Organigramas editarOrganigrama;
    private Estructuras editarEstructura;
    private int cualCelda, tipoLista, cualCeldaEstructura, tipoListaEstructura;
    //duplicar
    private Estructuras duplicarEstructura;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;
    private String centroCosto, estructuraPadre;
    ///////////LOV///////////
    private List<CentrosCostos> lovCentrosCostos;
    private List<CentrosCostos> filtrarLovCentrosCostos;
    private CentrosCostos centroCostoSeleccionado;
    private List<Estructuras> lovEstructurasPadres;
    private List<Estructuras> filtrarLovEstructurasPadres;
    private Estructuras estructuraPadreSeleccionado;
    private boolean permitirIndexEstructura;
    private int tipoActualizacion;
    private Long auxCodigoEstructura;
    private String auxNombreEstructura;
    //
    private boolean cambiosPagina;
    private String altoTablaOrganigrama, altoTablaEstructura;
    //
    private String paginaAnterior;
    //
    private boolean activoEstructura;
    //
    private Date fechaParametro;
    private Date fechaOrganigrama;
    //
    private String infoRegistroOr, infoRegistroEs, infoRegistroCentroCosto, infoRegistroEstructuraPa;
    //
    private DataTable tabla;
    private boolean activarLOV;

    public ControlEstructuraPlanta() {
        organigramaSeleccionado = null;
        estructuraSeleccionada = null;
        activoEstructura = true;
        paginaAnterior = "";
        //altos tablas
        altoTablaOrganigrama = "65";
        altoTablaEstructura = "210";
        //Permitir index
        permitirIndexEstructura = true;
        //lovs
        lovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        lovEstructurasPadres = new ArrayList<Estructuras>();
        estructuraPadreSeleccionado = new Estructuras();
        //listas de tablas
        listaOrganigramas = null;
        listaEstructuras = null;

        infoRegistroOr = "";
        infoRegistroEs = "";
        infoRegistroCentroCosto = "";
        infoRegistroEstructuraPa = "";
        //Otros|
        aceptar = true;
        cambiosPagina = true;
        tipoActualizacion = -1;
        k = 0;
        //borrar 
        listEstructurasBorrar = new ArrayList<Estructuras>();
        //crear 
        listEstructurasCrear = new ArrayList<Estructuras>();
        //modificar 
        listEstructurasModificar = new ArrayList<Estructuras>();
        listOrganigramasModificar = new ArrayList<Organigramas>();
        //editar
        editarOrganigrama = new Organigramas();
        editarEstructura = new Estructuras();
        //Cual Celda
        cualCelda = -1;
        cualCeldaEstructura = -1;
        //Tipo Lista
        tipoListaEstructura = 0;
        tipoLista = 0;
        //guardar
        guardado = true;
        guardadoEstructura = true;
        //Crear 
        nuevoEstructura = new Estructuras();
        nuevoEstructura.setCentrocosto(new CentrosCostos());
        nuevoEstructura.setEstructurapadre(new Estructuras());

        //Duplicar
        duplicarEstructura = new Estructuras();

        //Banderas
        bandera = 0;
        banderaEstructura = 0;

        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEstructuraPlanta.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public String valorPaginaAnterior() {
        return paginaAnterior;
    }

    public void inicializarPagina(String paginaLlamado) {
        paginaAnterior = paginaLlamado;
        getListaOrganigramas();
        contarRegistrosOr();
        RequestContext.getCurrentInstance().update("form:infoRegistroOrg");
        if (listaOrganigramas != null) {
            if (!listaOrganigramas.isEmpty()) {
                organigramaSeleccionado = listaOrganigramas.get(0);
                getListaEstructuras();
                contarRegistrosEs();
                RequestContext.getCurrentInstance().update("form:infoRegistroEst");
            }
        } else {
            System.out.println("El getListaOrganigramas() no trajo datos");
        }
        listaOrganigramas = null;
    }

    public boolean validarFechasRegistro() {
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);

        if (organigramaSeleccionado.getFecha() != null) {
            if (organigramaSeleccionado.getFecha().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        } else {
            retorno = false;
        }

        return retorno;
    }

    public void modificacionesFechas(Organigramas organigramas, int c) {
        boolean variable = validarFechasRegistro();
        if (variable == true) {
            cambiarIndice(organigramas, c);
            modificarOrganigrama();
        } else {
            organigramaSeleccionado.setFecha(fechaOrganigrama);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOrganigrama");
            context.execute("errorFecha.show()");
        }
    }

    public boolean validarCamposNulosEstructura(int i) {
        boolean retorno = true;
        if (i == 0) {
            Estructuras aux = new Estructuras();
            aux = estructuraSeleccionada;


            if (aux.getCodigo() == null) {
                retorno = false;
            }
            if (aux.getNombre() == null) {
                retorno = false;
            } else {
                if (aux.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevoEstructura.getCodigo() == null) {
                retorno = false;
            }
            if (nuevoEstructura.getNombre() == null) {
                retorno = false;
            } else {
                if (nuevoEstructura.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        if (i == 2) {

            if (duplicarEstructura.getCodigo() == null) {
                retorno = false;
            }
            if (duplicarEstructura.getNombre() == null) {
                retorno = false;
            } else {
                if (duplicarEstructura.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarOrganigrama() {
        listOrganigramasModificar.add(organigramaSeleccionado);

        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public boolean validarCodigoNoExistente(int i) {
        boolean retorno = true;
        int conteo = 0;
        if (i == 0) {
            Estructuras temporal = null;
            temporal = estructuraSeleccionada;

            for (int j = 0; j < listaEstructuras.size(); j++) {
                if (listaEstructuras.get(j).getCodigo() == temporal.getCodigo()) {
                    conteo++;
                }
            }
            if (conteo > 1) {
                retorno = false;
            }
        }
        if (i == 1) {
            for (int j = 0; j < listaEstructuras.size(); j++) {
                if (listaEstructuras.get(j).getCodigo() == nuevoEstructura.getCodigo()) {
                    conteo++;
                }
            }
            if (conteo > 1) {
                retorno = false;
            }
        }
        if (i == 2) {
            for (int j = 0; j < listaEstructuras.size(); j++) {
                if (listaEstructuras.get(j).getCodigo() == duplicarEstructura.getCodigo()) {
                    conteo++;
                }
            }
            if (conteo > 1) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void procesoModificacionEstructura(Estructuras estructura) {
        estructuraSeleccionada = estructura;
        boolean respuesta = validarCamposNulosEstructura(0);
        if (respuesta == true) {
            if (validarCodigoNoExistente(0) == true) {
                modificarEstructura();
            } else {
                estructuraSeleccionada.setCodigo(auxCodigoEstructura);

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosEstructura");
                context.execute("errorCodigo.show()");
            }
        } else {
            estructuraSeleccionada.setCodigo(auxCodigoEstructura);
            estructuraSeleccionada.setNombre(auxNombreEstructura);

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEstructura");
            context.execute("errorDatosNullEstructura.show()");
        }
    }

    public void modificarEstructura() {
        String aux = "";
        aux = estructuraSeleccionada.getNombre();

        if (aux.length() >= 1 && aux.length() <= 50) {
            if (!listEstructurasCrear.contains(estructuraSeleccionada)) {
                if (listEstructurasModificar.isEmpty()) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                } else if (!listEstructurasModificar.contains(estructuraSeleccionada)) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                }
                if (guardadoEstructura == true) {
                    guardadoEstructura = false;
                }
            }

            estructuraSeleccionada = null;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEstructura");
        } else {
            estructuraSeleccionada.setNombre(auxNombreEstructura);

            estructuraSeleccionada = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEstructura");
        }
    }

    public void modificarEstructura(Estructuras estructura, String confirmarCambio, String valorConfirmar) {
        estructuraSeleccionada = estructura;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PADRE")) {
            estructuraSeleccionada.getEstructurapadre().setNombre(estructuraPadre);

            for (int i = 0; i < lovEstructurasPadres.size(); i++) {
                if (lovEstructurasPadres.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                estructuraSeleccionada.setEstructurapadre(lovEstructurasPadres.get(indiceUnicoElemento));

                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexEstructura = false;
                context.update("form:EstructuraPadreDialogo");
                context.execute("EstructuraPadreDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            estructuraSeleccionada.getCentrocosto().setCodigoNombre(centroCosto);

            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getCodigoNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                estructuraSeleccionada.setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));

                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexEstructura = false;
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listEstructurasCrear.contains(estructuraSeleccionada)) {
                if (listEstructurasModificar.isEmpty()) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                } else if (!listEstructurasModificar.contains(estructuraSeleccionada)) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                }
                if (guardadoEstructura == true) {
                    guardadoEstructura = false;
                }
            }

        }
        context.update("form:datosEstructura");
    }

    public void posicionOrganigrama() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        Organigramas organigrama = listaOrganigramas.get(indice);
        cambiarIndice(organigrama, columna);
    }

    public void posicionEstructura() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        estructuraSeleccionada = listaEstructuras.get(indice);
        cambiarIndiceEstructura(estructuraSeleccionada, columna);
    }

    public void cambiarIndice(Organigramas organigrama, int celda) {
        if (guardadoEstructura == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            cualCelda = celda;
            cualCeldaEstructura = -1;
            organigramaSeleccionado = organigrama;
            estructuraSeleccionada = null;
            fechaOrganigrama = organigramaSeleccionado.getFecha();

            //lovCentrosCostos = null;
            //getLovCentrosCostos();
            listaEstructuras = null;
            getListaEstructuras();
            contarRegistrosEs();
            RequestContext.getCurrentInstance().update("form:infoRegistroEst");
            context.update("form:datosEstructura");
            if (banderaEstructura == 1) {
                altoTablaEstructura = "210";
                estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
                estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
                estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
                estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
                estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
                estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
                estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
                estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
                estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
                estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
                estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEstructura");
                banderaEstructura = 0;
                filtrarListaEstructuras = null;
                tipoListaEstructura = 0;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceEstructura(Estructuras estructuras, int celda) {
        if (permitirIndexEstructura == true) {
            estructuraSeleccionada = estructuras;
            cualCeldaEstructura = celda;
            estructuraPadre = estructuraSeleccionada.getEstructurapadre().getNombre();
            centroCosto = estructuraSeleccionada.getCentrocosto().getCodigoNombre();
            auxCodigoEstructura = estructuraSeleccionada.getCodigo();
            auxNombreEstructura = estructuraSeleccionada.getNombre();
        }
        if (cualCeldaEstructura == 4 || cualCeldaEstructura == 5) {
            activarLOV = false;
        } else {
            activarLOV = true;
        }
        RequestContext.getCurrentInstance().update("form:listaValores");
    }
    //GUARDAR

    public void guardarYSalir() {
        guardarGeneral();
        salir();
    }

    public void cancelarYSalir() {

        cancelarModificacionGeneral();
        salir();
    }

    public void guardarGeneral() {
        if (guardado == false || guardadoEstructura == false) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == false) {
                guardarCambiosOrganigrama();
            }
            if (guardadoEstructura == false) {
                guardarCambiosEstructura();
            }
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            cambiosPagina = true;
            context.update("form:ACEPTAR");
        }
    }

    public void guardarCambiosOrganigrama() {
        if (!listOrganigramasModificar.isEmpty()) {
            administrarEstructuraPlanta.modificarOrganigramas(listOrganigramasModificar);
            listOrganigramasModificar.clear();
        }
        listaOrganigramas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOrganigrama");
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        organigramaSeleccionado = null;
    }

    public void guardarCambiosEstructura() {
        if (!listEstructurasBorrar.isEmpty()) {
            administrarEstructuraPlanta.borrarEstructura(listEstructurasBorrar);
            listEstructurasBorrar.clear();
        }
        if (!listEstructurasCrear.isEmpty()) {
            administrarEstructuraPlanta.crearEstructura(listEstructurasCrear);
            listEstructurasCrear.clear();
        }
        if (!listEstructurasModificar.isEmpty()) {
            administrarEstructuraPlanta.editarEstructura(listEstructurasModificar);
            listEstructurasModificar.clear();
        }
        listaEstructuras = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEstructura");
        guardadoEstructura = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        estructuraSeleccionada = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacionGeneral() {
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        if (guardado == false) {
            cancelarModificacionOrganigrama();
            contarRegistrosOr();
            RequestContext.getCurrentInstance().update("form:infoRegistroOrg");
            context.update("form:datosOrganigrama");
        }
        if (guardadoEstructura == false) {
            cancelarModificacionEstructura();
            RequestContext.getCurrentInstance().update("form:infoRegistroEst");
            context.update("form:datosEstructura");
        }
        contarRegistrosEs();
        activarLOV = true;
        estructuraSeleccionada = null;
        organigramaSeleccionado = null;
        listaEstructuras = null;
        contarRegistrosEs();
        RequestContext.getCurrentInstance().update("form:listaValores");
    }

    public void cancelarModificacionOrganigrama() {
        if (bandera == 1) {
            altoTablaOrganigrama = "65";
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaNIT");
            organigramaNIT.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigrama");
            bandera = 0;
            filtrarListaOrganigramas = null;
            tipoLista = 0;
        }
        listOrganigramasModificar.clear();
        k = 0;
        listaOrganigramas = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosOrganigrama");
    }

    public void cancelarModificacionEstructura() {
        if (banderaEstructura == 1) {
            altoTablaEstructura = "210";
            estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
            estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
            estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
            estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
            estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
            estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
            estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
            estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEstructura");
            banderaEstructura = 0;
            filtrarListaEstructuras = null;
            tipoListaEstructura = 0;
        }
        listEstructurasBorrar.clear();
        listEstructurasCrear.clear();
        listEstructurasModificar.clear();
        k = 0;
        listaEstructuras = null;
        guardadoEstructura = true;
        permitirIndexEstructura = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEstructura");
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (estructuraSeleccionada != null) {
            editarEstructura = estructuraSeleccionada;

            if (cualCeldaEstructura == 0) {
                context.update("formularioDialogos:editarCodigoEstructuraD");
                context.execute("editarCodigoEstructuraD.show()");
                cualCeldaEstructura = -1;
            } else if (cualCeldaEstructura == 1) {
                context.update("formularioDialogos:editarEstructuraEstructuraD");
                context.execute("editarEstructuraEstructuraD.show()");
                cualCeldaEstructura = -1;
            } else if (cualCeldaEstructura == 2) {
                context.update("formularioDialogos:editarCantidadControlarEstructuraD");
                context.execute("editarCantidadControlarEstructuraD.show()");
                cualCeldaEstructura = -1;
            } else if (cualCeldaEstructura == 3) {
                context.update("formularioDialogos:editarCantidadActivosEstructuraD");
                context.execute("editarCantidadActivosEstructuraD.show()");
                cualCeldaEstructura = -1;
            } else if (cualCeldaEstructura == 4) {
                context.update("formularioDialogos:editarCentroCostoEstructuraD");
                context.execute("editarCentroCostoEstructuraD.show()");
                cualCeldaEstructura = -1;
            } else if (cualCeldaEstructura == 5) {
                context.update("formularioDialogos:editarEstructuraPadreEstructuraD");
                context.execute("editarEstructuraPadreEstructuraD.show()");
                cualCeldaEstructura = -1;
            }
        } else if (organigramaSeleccionado != null) {
            editarOrganigrama = organigramaSeleccionado;

            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaOrganigramaD");
                context.execute("editarFechaOrganigramaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarCodigoOrganigramaD");
                context.execute("editarCodigoOrganigramaD.show()");
                cualCelda = -1;

            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarEmpresaOrganigramaD");
                context.execute("editarEmpresaOrganigramaD.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarNITOrganigramaD");
                context.execute("editarNITOrganigramaD.show()");
                cualCelda = -1;

            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarEstadoOrganigramaD");
                context.execute("editarEstadoOrganigramaD.show()");
                cualCelda = -1;

            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void dialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        activoEstructura = false;
        codigoNuevoEstructura();
        lovEstructurasPadres = null;
        lovEstructurasPadres = administrarEstructuraPlanta.lovEstructuras();
        context.update("formularioDialogos:NuevoRegistroEstructura");
        context.execute("NuevoRegistroEstructura.show()");

    }

    public void codigoNuevoEstructura() {
        String code = "";
        int tam = listaEstructuras.size();
        if (tam > 0) {
            int newCode = listaEstructuras.get(tam - 1).getCodigo().intValue() + 1;
            code = String.valueOf(newCode);
        } else {
            code = "1";
        }
        nuevoEstructura.setCodigo(new Long(code));
    }

//CREAR 
    public void agregarNuevoEstructura() {
        boolean respueta = validarCamposNulosEstructura(1);
        if (respueta == true) {
            if (validarCodigoNoExistente(1) == true) {
                if (banderaEstructura == 1) {
                    altoTablaEstructura = "210";
                    estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
                    estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
                    estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
                    estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
                    estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
                    estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
                    estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosEstructura");
                    banderaEstructura = 0;
                    filtrarListaEstructuras = null;
                    tipoListaEstructura = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoEstructura.setSecuencia(l);
                nuevoEstructura.setOrganigrama(organigramaSeleccionado);

                if (listaEstructuras == null) {
                    listaEstructuras = new ArrayList<Estructuras>();
                }
                listEstructurasCrear.add(nuevoEstructura);
                listaEstructuras.add(nuevoEstructura);
                modificarInfoRegistroEs(listaEstructuras.size());
                RequestContext.getCurrentInstance().update("form:infoRegistroEst");
                estructuraSeleccionada = listaEstructuras.get(listaEstructuras.indexOf(nuevoEstructura));
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosEstructura");
                context.execute("NuevoRegistroEstructura.hide()");
                nuevoEstructura = new Estructuras();
                nuevoEstructura.setCentrocosto(new CentrosCostos());
                nuevoEstructura.setEstructurapadre(new Estructuras());
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                if (guardadoEstructura == true) {
                    guardadoEstructura = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorCodigo.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullEstructura.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevaEstructura() {
        nuevoEstructura = new Estructuras();
        nuevoEstructura.setCentrocosto(new CentrosCostos());
        nuevoEstructura.setEstructurapadre(new Estructuras());
    }

    //DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
    }

    public void duplicarEstructuraM() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (estructuraSeleccionada != null) {
            duplicarEstructuraM();
            duplicarEstructura = new Estructuras();
            duplicarEstructura.setCodigo(estructuraSeleccionada.getCodigo());
            duplicarEstructura.setNombre(estructuraSeleccionada.getNombre());
            duplicarEstructura.setCentrocosto(estructuraSeleccionada.getCentrocosto());
            duplicarEstructura.setEstructurapadre(estructuraSeleccionada.getEstructurapadre());
            duplicarEstructura.setCantidadCargosControlar(estructuraSeleccionada.getCantidadCargosControlar());
            duplicarEstructura.setCantidadCargosEmplActivos(estructuraSeleccionada.getCantidadCargosEmplActivos());

            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            context.update("formularioDialogos:DuplicarRegistroEstructura");
            context.execute("DuplicarRegistroEstructura.show()");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicarEstructura() {
        boolean respueta = validarCamposNulosEstructura(2);
        if (respueta == true) {
            if (validarCodigoNoExistente(2) == true) {
                if (banderaEstructura == 1) {
                    altoTablaEstructura = "210";
                    estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
                    estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
                    estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
                    estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
                    estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
                    estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
                    estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
                    estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosEstructura");
                    banderaEstructura = 0;
                    filtrarListaEstructuras = null;
                    tipoListaEstructura = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                duplicarEstructura.setSecuencia(l);
                duplicarEstructura.setOrganigrama(organigramaSeleccionado);

                listaEstructuras.add(duplicarEstructura);
                listEstructurasCrear.add(duplicarEstructura);
                estructuraSeleccionada = listaEstructuras.get(listaEstructuras.indexOf(nuevoEstructura));
                modificarInfoRegistroEs(listaEstructuras.size());
                RequestContext.getCurrentInstance().update("form:infoRegistroEst");
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                activarLOV = true;
                RequestContext.getCurrentInstance().update("form:listaValores");
                context.update("form:ACEPTAR");
                context.update("form:datosEstructura");
                context.execute("DuplicarRegistroEstructura.hide()");
                estructuraSeleccionada = null;
                if (guardadoEstructura == true) {
                    guardadoEstructura = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }

                duplicarEstructura = new Estructuras();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorCodigo.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullEstructura.show()");
        }
    }

    public void limpiarDuplicarEstructura() {
        duplicarEstructura = new Estructuras();
        duplicarEstructura.setEstructurapadre(new Estructuras());
        duplicarEstructura.setCentrocosto(new CentrosCostos());
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void borrarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (estructuraSeleccionada != null) {
            if (!listEstructurasModificar.isEmpty() && listEstructurasModificar.contains(estructuraSeleccionada)) {
                int modIndex = listEstructurasModificar.indexOf(estructuraSeleccionada);
                listEstructurasModificar.remove(modIndex);
                listEstructurasBorrar.add(estructuraSeleccionada);
            } else if (!listEstructurasCrear.isEmpty() && listEstructurasCrear.contains(estructuraSeleccionada)) {
                int crearIndex = listEstructurasCrear.indexOf(estructuraSeleccionada);
                listEstructurasCrear.remove(crearIndex);
            } else {
                listEstructurasBorrar.add(estructuraSeleccionada);
            }
            listaEstructuras.remove(estructuraSeleccionada);
            if (tipoListaEstructura == 1) {
                filtrarListaEstructuras.remove(estructuraSeleccionada);
            }
            modificarInfoRegistroEs(listaEstructuras.size());
            RequestContext.getCurrentInstance().update("form:infoRegistroEst");
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            context.update("form:datosEstructura");
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            estructuraSeleccionada = null;

            if (guardadoEstructura == true) {
                guardadoEstructura = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (bandera == 0) {
            altoTablaOrganigrama = "43";
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaFecha");
            organigramaFecha.setFilterStyle("width: 85%");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaCodigo");
            organigramaCodigo.setFilterStyle("width: 85%");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("width: 85%");
            organigramaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaNIT");
            organigramaNIT.setFilterStyle("width: 85%");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEstado");
            organigramaEstado.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosOrganigrama");
            bandera = 1;
        } else if (bandera == 1) {
            altoTablaOrganigrama = "65";
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaFecha");
            organigramaFecha.setFilterStyle("display: none; visibility: hidden;");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaCodigo");
            organigramaCodigo.setFilterStyle("display: none; visibility: hidden;");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("display: none; visibility: hidden;");
            organigramaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaNIT");
            organigramaNIT.setFilterStyle("display: none; visibility: hidden;");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEstado");
            organigramaEstado.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOrganigrama");
            bandera = 0;
            filtrarListaOrganigramas = null;
            tipoLista = 0;
        }
        if (banderaEstructura == 0) {
            altoTablaEstructura = "188";
            estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
            estructuraEstructura.setFilterStyle("width: 85%");
            estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
            estructuraCodigo.setFilterStyle("width: 85%");
            estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
            estructuraCantidadControlar.setFilterStyle("width: 85%");
            estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
            estructuraCantidadActivo.setFilterStyle("width: 85%");
            estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
            estructuraCentroCosto.setFilterStyle("width: 85%");
            estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
            estructuraEstructuraPadre.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosEstructura");
            banderaEstructura = 1;
        } else if (banderaEstructura == 1) {
            altoTablaEstructura = "210";
            estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
            estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
            estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
            estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
            estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
            estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
            estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
            estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEstructura");
            banderaEstructura = 0;
            filtrarListaEstructuras = null;
            tipoListaEstructura = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            altoTablaOrganigrama = "65";
            organigramaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaFecha");
            organigramaFecha.setFilterStyle("width: 85%");
            organigramaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaCodigo");
            organigramaCodigo.setFilterStyle("width: 85%");
            organigramaEmpresa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEmpresa");
            organigramaEmpresa.setFilterStyle("width: 85%");
            organigramaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaNIT");
            organigramaNIT.setFilterStyle("width: 85%");
            organigramaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOrganigrama:organigramaEstado");
            organigramaEstado.setFilterStyle("width: 85%");
            RequestContext.getCurrentInstance().update("form:datosOrganigrama");
            bandera = 0;
            filtrarListaOrganigramas = null;
            tipoLista = 0;
        }
        if (banderaEstructura == 1) {
            altoTablaEstructura = "210";
            estructuraEstructura = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructura");
            estructuraEstructura.setFilterStyle("display: none; visibility: hidden;");
            estructuraCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCodigo");
            estructuraCodigo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadControlar = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadControlar");
            estructuraCantidadControlar.setFilterStyle("display: none; visibility: hidden;");
            estructuraCantidadActivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCantidadActivo");
            estructuraCantidadActivo.setFilterStyle("display: none; visibility: hidden;");
            estructuraCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraCentroCosto");
            estructuraCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            estructuraEstructuraPadre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEstructura:estructuraEstructuraPadre");
            estructuraEstructuraPadre.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEstructura");
            banderaEstructura = 0;
            filtrarListaEstructuras = null;
            tipoListaEstructura = 0;
        }
        listOrganigramasModificar.clear();
        listEstructurasBorrar.clear();
        listEstructurasCrear.clear();
        listEstructurasModificar.clear();
        organigramaSeleccionado = null;
        estructuraSeleccionada = null;
        k = 0;
        listaOrganigramas = null;
        listaEstructuras = null;
        guardado = true;
        guardadoEstructura = true;
        cambiosPagina = true;
        lovCentrosCostos = null;
        lovEstructurasPadres = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        altoTablaOrganigrama = "65";
        altoTablaEstructura = "210";
        RequestContext.getCurrentInstance().update("form:datosEstructura");
        RequestContext.getCurrentInstance().update("form:datosOrganigrama");
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (estructuraSeleccionada != null) {
            if (cualCeldaEstructura == 2 || cualCeldaEstructura == 4) {
                modificarInfoRegistrosCCosto(lovCentrosCostos.size());
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaEstructura == 5) {
                cargarLovEstructurasPadres(organigramaSeleccionado.getSecuencia(), estructuraSeleccionada.getSecuencia());
                modificarInfoRegistroEsPa(lovEstructurasPadres.size());
                context.update("form:EstructuraPadreDialogo");
                context.execute("EstructuraPadreDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void dispararDialogo() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        estructuraSeleccionada = listaEstructuras.get(indice);
        cambiarIndiceEstructura(estructuraSeleccionada, columna);
        tipoActualizacion = 0;
        RequestContext contextt = RequestContext.getCurrentInstance();
        contextt.update("form:CentroCostoDialogo");
        contextt.execute("CentroCostoDialogo.show()");
    }

    public void asignarIndex(Estructuras estructura, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = LND;


        if (dlg == 0) {
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            modificarInfoRegistrosCCosto(lovCentrosCostos.size());
            context.update("form:CentroCostoDialogo");
            context.execute("CentroCostoDialogo.show()");
        }
        if (dlg == 1) {
            activarLOV = true;
            cargarLovEstructurasPadres(organigramaSeleccionado.getSecuencia(), estructuraSeleccionada.getSecuencia());
            RequestContext.getCurrentInstance().update("form:listaValores");
            modificarInfoRegistroEsPa(lovEstructurasPadres.size());
            context.update("form:EstructuraPadreDialogo");
            context.execute("EstructuraPadreDialogo.show()");
        }
    }

    public void asignarIndex(int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = LND;

        if (dlg == 0) {
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            modificarInfoRegistrosCCosto(lovCentrosCostos.size());
            context.update("form:CentroCostoDialogo");
            context.execute("CentroCostoDialogo.show()");
        }
        if (dlg == 1) {
            activarLOV = true;
            RequestContext.getCurrentInstance().update("form:listaValores");
            modificarInfoRegistroEsPa(lovEstructurasPadres.size());
            context.update("form:EstructuraPadreDialogo");
            context.execute("EstructuraPadreDialogo.show()");
        }
    }

    public void valoresBackupAutocompletarGeneral(int tipoNuevo, String Campo) {

        if (Campo.equals("PROCESOPRODUCTIVO")) {
            if (tipoNuevo == 1) {
                centroCosto = nuevoEstructura.getCentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                centroCosto = duplicarEstructura.getCentrocosto().getNombre();
            }
        }
        if (Campo.equals("PADRE")) {
            if (tipoNuevo == 1) {
                estructuraPadre = nuevoEstructura.getEstructurapadre().getNombre();
            } else if (tipoNuevo == 2) {
                estructuraPadre = duplicarEstructura.getEstructurapadre().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoEstructura(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                nuevoEstructura.getCentrocosto().setNombre(centroCosto);
            } else if (tipoNuevo == 2) {
                duplicarEstructura.getCentrocosto().setNombre(centroCosto);
            }
            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoEstructura.setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoEstructuraCentroCosto");
                } else if (tipoNuevo == 2) {
                    duplicarEstructura.setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEstructuraCentroCosto");
                }
            } else {
                context.update("form:ProcesoProductivoDialogo");
                context.execute("ProcesoProductivoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoEstructuraCentroCosto");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEstructuraCentroCosto");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PADRE")) {
            if (tipoNuevo == 1) {
                nuevoEstructura.getEstructurapadre().setNombre(estructuraPadre);
            } else if (tipoNuevo == 2) {
                duplicarEstructura.getEstructurapadre().setNombre(estructuraPadre);
            }
            for (int i = 0; i < lovEstructurasPadres.size(); i++) {
                if (lovEstructurasPadres.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoEstructura.setEstructurapadre(lovEstructurasPadres.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoEstructuraEstructuraPadre");
                } else if (tipoNuevo == 2) {
                    duplicarEstructura.setEstructurapadre(lovEstructurasPadres.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEstructuraEstructuraPadre");
                }
            } else {
                context.update("form:TipoEmpresaDialogo");
                context.execute("TipoEmpresaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoEstructuraEstructuraPadre");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEstructuraEstructuraPadre");
                }
            }
        }
    }

    public void actualizarCentroCosto() {
        System.out.println("tipoActualizacion : " + tipoActualizacion);
        System.out.println("centroCostoSeleccionado : " + centroCostoSeleccionado.getNombre());
        System.out.println("estructuraSeleccionado : " + estructuraSeleccionada);
        if (tipoActualizacion == 0) {
            estructuraSeleccionada.setCentrocosto(centroCostoSeleccionado);
            if (!listEstructurasCrear.contains(estructuraSeleccionada)) {
                if (listEstructurasModificar.isEmpty()) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                } else if (!listEstructurasModificar.contains(estructuraSeleccionada)) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                }
            }

            if (guardadoEstructura == true) {
                guardadoEstructura = false;
            }
            permitirIndexEstructura = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEstructura");
        } else if (tipoActualizacion == 1) {
            nuevoEstructura.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoEstructuraCentroCosto");
        } else if (tipoActualizacion == 2) {
            duplicarEstructura.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEstructuraCentroCosto");
        }
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();

        context.update("form:CentroCostoDialogo");
        context.update("form:lovCentroCosto");
        context.update("form:aceptarCC");

        context.reset("form:lovCentroCosto:globalFilter");
        context.execute("lovCentroCosto.clearFilters()");
        context.execute("CentroCostoDialogo.hide()");
    }

    public void cancelarCambioCentroCosto() {
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        permitirIndexEstructura = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovCentroCosto:globalFilter");
        context.execute("lovCentroCosto.clearFilters()");
        context.execute("CentroCostoDialogo.hide()");
    }

    public void actualizarEstructuraPadre() {
        if (tipoActualizacion == 0) {
            estructuraSeleccionada.setEstructurapadre(estructuraPadreSeleccionado);
            if (!listEstructurasCrear.contains(estructuraSeleccionada)) {
                if (listEstructurasModificar.isEmpty()) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                } else if (!listEstructurasModificar.contains(estructuraSeleccionada)) {
                    listEstructurasModificar.add(estructuraSeleccionada);
                }
            }

            if (guardadoEstructura == true) {
                guardadoEstructura = false;
            }
            permitirIndexEstructura = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEstructura");
        } else if (tipoActualizacion == 1) {
            nuevoEstructura.setEstructurapadre(estructuraPadreSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoEstructuraEstructuraPadre");
        } else if (tipoActualizacion == 2) {
            duplicarEstructura.setEstructurapadre(estructuraPadreSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEstructuraEstructuraPadre");
        }
        filtrarLovEstructurasPadres = null;
        estructuraPadreSeleccionado = null;
        aceptar = true;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();

        context.update("form:EstructuraPadreDialogo");
        context.update("form:lovEstructuraPadre");
        context.update("form:aceptarEP");

        context.reset("form:lovEstructuraPadre:globalFilter");
        context.execute("lovEstructuraPadre.clearFilters()");
        context.execute("EstructuraPadreDialogo.hide()");
    }

    public void cancelarCambioEstructuraPadre() {
        filtrarLovEstructurasPadres = null;
        estructuraPadreSeleccionado = null;
        aceptar = true;
        estructuraSeleccionada = null;
        tipoActualizacion = -1;
        permitirIndexEstructura = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEstructuraPadre:globalFilter");
        context.execute("lovEstructuraPadre.clearFilters()");
        context.execute("EstructuraPadreDialogo.hide()");
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public String exportXML() {
        if (organigramaSeleccionado != null) {
            nombreTabla = ":formExportarO:datosOrganigramaExportar";
            nombreXML = "Organigramas_XML";
        }
        if (estructuraSeleccionada != null) {
            nombreTabla = ":formExportarE:datosEstructuraExportar";
            nombreXML = "Estructuras_XML";
        }
        return nombreTabla;
    }

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (organigramaSeleccionado != null) {
            exportPDF_O();
        }
        if (estructuraSeleccionada != null) {
            exportPDF_E();
        }
    }

    public void exportPDF_O() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarO:datosOrganigramaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Organigramas_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportPDF_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarE:datosEstructuraExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Estructuras_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportXLS() throws IOException {
        if (organigramaSeleccionado != null) {
            exportXLS_O();
        }
        if (estructuraSeleccionada != null) {
            exportXLS_E();
        }
    }

    public void exportXLS_O() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarO:datosOrganigramaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Organigramas_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarE:datosEstructuraExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Estructuras_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void anularLOV() {
        activarLOV = true;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrarO() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        organigramaSeleccionado = null;
        modificarInfoRegistroOr(filtrarListaOrganigramas.size());
    }

    public void eventoFiltrarE() {
        if (tipoListaEstructura == 0) {
            tipoListaEstructura = 1;
        }
        activarLOV = true;
        RequestContext.getCurrentInstance().update("form:listaValores");
        estructuraSeleccionada = null;
        modificarInfoRegistroEs(filtrarListaEstructuras.size());
    }

    private void modificarInfoRegistroOr(int valor) {
        infoRegistroOr = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:infoRegistroOrg");
    }

    private void modificarInfoRegistroEs(int valor) {
        infoRegistroEs = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:infoRegistroEst");
    }

    public void contarRegistrosOr() {
        if (listaOrganigramas != null) {
            modificarInfoRegistroOr(listaOrganigramas.size());
        } else {
            modificarInfoRegistroOr(0);
        }
    }

    public void contarRegistrosEs() {
        if (listaEstructuras != null) {
            modificarInfoRegistroEs(listaEstructuras.size());
        } else {
            modificarInfoRegistroEs(0);
        }
        RequestContext.getCurrentInstance().update("form:infoRegistroEst");
    }

    private void modificarInfoRegistrosCCosto(int valor) {
        infoRegistroCentroCosto = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:infoRegistroCentroCosto");
    }

    private void modificarInfoRegistroEsPa(int valor) {
        infoRegistroEstructuraPa = String.valueOf(valor);
        RequestContext.getCurrentInstance().update("form:infoRegistroEstructura");
    }

    public void eventoFiltrarCCosto() {
        modificarInfoRegistrosCCosto(filtrarLovCentrosCostos.size());
    }

    public void eventoFiltrarEsPa() {
        modificarInfoRegistroEsPa(filtrarLovEstructurasPadres.size());
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        if (organigramaSeleccionado == null && estructuraSeleccionada == null) {
            RequestContext.getCurrentInstance().execute("verificarRastrosTablasH.show()");
        } else {
            if (estructuraSeleccionada != null) {
                verificarRastroEstructura();
            } else if (organigramaSeleccionado != null) {
                verificarRastroOrganigrama();
            }
        }
    }

    public void verificarRastroOrganigrama() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(organigramaSeleccionado.getSecuencia(), "ORGANIGRAMAS");
        organigramaSeleccionado = null;
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "Organigramas";
            msnConfirmarRastro = "La tabla ORGANIGRAMAS tiene rastros para el registro seleccionado, ¿desea continuar?";
            context.update("form:msnConfirmarRastro");
            context.execute("confirmarRastro.show()");
        } else if (resultado == 3) {
            context.execute("errorRegistroRastro.show()");
        } else if (resultado == 4) {
            context.execute("errorTablaConRastro.show()");
        } else if (resultado == 5) {
            context.execute("errorTablaSinRastro.show()");
        }
    }

    public void verificarRastroOrganigramaH() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("ORGANIGRAMAS")) {
            nombreTablaRastro = "Organigramas";
            msnConfirmarRastroHistorico = "La tabla ORGANIGRAMAS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void verificarRastroEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        int resultado = administrarRastros.obtenerTabla(estructuraSeleccionada.getSecuencia(), "ESTRUCTURAS");
        estructuraSeleccionada = null;
        if (resultado == 1) {
            context.execute("errorObjetosDB.show()");
        } else if (resultado == 2) {
            nombreTablaRastro = "Estructuras";
            msnConfirmarRastro = "La tabla ESTRUCTURAS tiene rastros para el registro seleccionado, ¿desea continuar?";
            context.update("form:msnConfirmarRastro");
            context.execute("confirmarRastro.show()");
        } else if (resultado == 3) {
            context.execute("errorRegistroRastro.show()");
        } else if (resultado == 4) {
            context.execute("errorTablaConRastro.show()");
        } else if (resultado == 5) {
            context.execute("errorTablaSinRastro.show()");
        }
    }

    public void verificarRastroEstructuraH() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (administrarRastros.verificarHistoricosTabla("ESTRUCTURAS")) {
            nombreTablaRastro = "Estructuras";
            msnConfirmarRastroHistorico = "La tabla ESTRUCTURAS tiene rastros historicos, ¿Desea continuar?";
            context.update("form:confirmarRastroHistorico");
            context.execute("confirmarRastroHistorico.show()");
        } else {
            context.execute("errorRastroHistorico.show()");
        }
    }

    public void recordarSeleccionOr() {
        if (organigramaSeleccionado != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosOrganigrama");
            tabla.setSelection(organigramaSeleccionado);
        }
    }

    public void recordarSeleccionEx() {
        if (estructuraSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tabla = (DataTable) c.getViewRoot().findComponent("form:datosEstructura");
            tabla.setSelection(estructuraSeleccionada);
        }
    }

    //GETTERS AND SETTERS
    public List<Organigramas> getListaOrganigramas() {
        try {
            if (listaOrganigramas == null) {
                listaOrganigramas = administrarEstructuraPlanta.listaOrganigramas();
            }
            return listaOrganigramas;

        } catch (Exception e) {
            System.out.println("Error...!! getListaOrganigramas " + e.toString());
            return null;
        }
    }

    public void setListaOrganigramas(List<Organigramas> setListaOrganigramas) {
        this.listaOrganigramas = setListaOrganigramas;
    }

    public List<Organigramas> getFiltrarListaOrganigramas() {
        return filtrarListaOrganigramas;
    }

    public void setFiltrarListaOrganigramas(List<Organigramas> setFiltrarListaOrganigramas) {
        this.filtrarListaOrganigramas = setFiltrarListaOrganigramas;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Organigramas getEditarOrganigrama() {
        return editarOrganigrama;
    }

    public void setEditarOrganigrama(Organigramas setEditarOrganigrama) {
        this.editarOrganigrama = setEditarOrganigrama;
    }

    public List<Estructuras> getListaEstructuras() {
        if (listaEstructuras == null) {
            if (organigramaSeleccionado != null) {
                listaEstructuras = administrarEstructuraPlanta.listaEstructurasPorSecuenciaOrganigrama(organigramaSeleccionado.getSecuencia());
            }

            if (listaEstructuras != null) {
                for (int i = 0; i < listaEstructuras.size(); i++) {
                    if (listaEstructuras.get(i).getCentrocosto() == null) {
                        listaEstructuras.get(i).setCentrocosto(new CentrosCostos());
                    }
                    if (listaEstructuras.get(i).getEstructurapadre() == null) {
                        listaEstructuras.get(i).setEstructurapadre(new Estructuras());
                    }
                }
            }
        }
        return listaEstructuras;
    }

    public void setListaEstructuras(List<Estructuras> setListaEstructuras) {
        this.listaEstructuras = setListaEstructuras;
    }

    public List<Estructuras> getFiltrarListaEstructuras() {
        return filtrarListaEstructuras;
    }

    public void setFiltrarListaEstructuras(List<Estructuras> setFiltrarListaEstructuras) {
        this.filtrarListaEstructuras = setFiltrarListaEstructuras;
    }

    public List<Organigramas> getListOrganigramasModificar() {
        return listOrganigramasModificar;
    }

    public void setListOrganigramasModificar(List<Organigramas> setListOrganigramasModificar) {
        this.listOrganigramasModificar = setListOrganigramasModificar;
    }

    public List<Estructuras> getListEstructurasModificar() {
        return listEstructurasModificar;
    }

    public void setListEstructurassModificar(List<Estructuras> setListEstructurassModificar) {
        this.listEstructurasModificar = setListEstructurassModificar;
    }

    public Estructuras getNuevoEstructura() {
        return nuevoEstructura;
    }

    public void setNuevoEstructura(Estructuras setNuevoEstructura) {
        this.nuevoEstructura = setNuevoEstructura;
    }

    public List<Estructuras> getListEstructurasCrear() {
        return listEstructurasCrear;
    }

    public void setListEstructurasCrear(List<Estructuras> setListEstructurasCrear) {
        this.listEstructurasCrear = setListEstructurasCrear;
    }

    public List<Estructuras> getListEstructurasBorrar() {
        return listEstructurasBorrar;
    }

    public void setListEstructurasBorrar(List<Estructuras> setListEstructurasBorrar) {
        this.listEstructurasBorrar = setListEstructurasBorrar;
    }

    public Estructuras getEditarEstructura() {
        return editarEstructura;
    }

    public void setEditarEstructura(Estructuras setEditarEstructura) {
        this.editarEstructura = setEditarEstructura;
    }

    public Estructuras getDuplicarEstructura() {
        return duplicarEstructura;
    }

    public void setDuplicarEstructura(Estructuras setDuplicarEstructura) {
        this.duplicarEstructura = setDuplicarEstructura;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String setNombreTabla) {
        this.nombreTabla = setNombreTabla;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public String getAltoTablaOrganigrama() {
        return altoTablaOrganigrama;
    }

    public void setAltoTablaOrganigrama(String setAltoTablaOrganigrama) {
        this.altoTablaOrganigrama = setAltoTablaOrganigrama;
    }

    public String getAltoTablaEstructura() {
        return altoTablaEstructura;
    }

    public void setAltoTablaEstructura(String setAltoTablaEstructura) {
        this.altoTablaEstructura = setAltoTablaEstructura;
    }

    public List<CentrosCostos> getLovCentrosCostos() {
        if (lovCentrosCostos == null) {
            if (organigramaSeleccionado != null) {
                System.out.println("organigramaSeleccionado : " + organigramaSeleccionado);
                System.out.println("organigramaSeleccionado.getEmpresa() : " + organigramaSeleccionado.getEmpresa());
                System.out.println("organigramaSeleccionado.getEmpresa().getSecuencia() : " + organigramaSeleccionado.getEmpresa().getSecuencia());
                lovCentrosCostos = administrarEstructuraPlanta.lovCentrosCostos(organigramaSeleccionado.getEmpresa().getSecuencia());
            } else {
                lovCentrosCostos = new ArrayList<CentrosCostos>();
            }
        }
        return lovCentrosCostos;
    }

    public void setLovCentrosCostos(List<CentrosCostos> setLovCentrosCostos) {
        this.lovCentrosCostos = setLovCentrosCostos;
    }

    public List<CentrosCostos> getFiltrarLovCentrosCostos() {
        return filtrarLovCentrosCostos;
    }

    public void setFiltrarLovCentrosCostos(List<CentrosCostos> setFiltrarLovCentrosCostos) {
        this.filtrarLovCentrosCostos = setFiltrarLovCentrosCostos;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos setCentroCostoSeleccionado) {
        this.centroCostoSeleccionado = setCentroCostoSeleccionado;
    }

    public void cargarLovEstructurasPadres(BigInteger secOrganigrama, BigInteger secEstructura) {
        lovEstructurasPadres = administrarEstructuraPlanta.lovEstructurasPadres(secOrganigrama, secEstructura);
    }

    public List<Estructuras> getLovEstructurasPadres() {
        return lovEstructurasPadres;
    }

    public void setLovEstructurasPadres(List<Estructuras> setLovEstructurasPadres) {
        this.lovEstructurasPadres = setLovEstructurasPadres;
    }

    public List<Estructuras> getFiltrarLovEstructurasPadres() {
        return filtrarLovEstructurasPadres;
    }

    public void setFiltrarLovEstructurasPadres(List<Estructuras> setFiltrarLovEstructurasPadres) {
        this.filtrarLovEstructurasPadres = setFiltrarLovEstructurasPadres;
    }

    public Estructuras getEstructuraPadreSeleccionado() {
        return estructuraPadreSeleccionado;
    }

    public void setEstructuraPadreSeleccionado(Estructuras setEstructuraPadreSeleccionado) {
        this.estructuraPadreSeleccionado = setEstructuraPadreSeleccionado;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public boolean isActivoEstructura() {
        return activoEstructura;
    }

    public void setActivoEstructura(boolean setActivoEstructura) {
        this.activoEstructura = setActivoEstructura;
    }

    public Organigramas getOrganigramaSeleccionado() {
        return organigramaSeleccionado;
    }

    public void setOrganigramaSeleccionado(Organigramas organigramaTablaSeleccionado) {
        this.organigramaSeleccionado = organigramaTablaSeleccionado;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraTablaSeleccionado) {
        this.estructuraSeleccionada = estructuraTablaSeleccionado;
    }

    public String getInfoRegistroCentroCosto() {
        return infoRegistroCentroCosto;
    }

    public void setInfoRegistroCentroCosto(String infoRegistroCentroCosto) {
        this.infoRegistroCentroCosto = infoRegistroCentroCosto;
    }

    public String getInfoRegistroEstructuraPa() {
        return infoRegistroEstructuraPa;
    }

    public void setInfoRegistroEstructuraPa(String infoRegistroEstructura) {
        this.infoRegistroEstructuraPa = infoRegistroEstructura;
    }

    public String getInfoRegistroEs() {
        return infoRegistroEs;
    }

    public void setInfoRegistroEs(String infoRegistroEs) {
        this.infoRegistroEs = infoRegistroEs;
    }

    public String getInfoRegistroOr() {
        return infoRegistroOr;
    }

    public void setInfoRegistroOr(String infoRegistroOr) {
        this.infoRegistroOr = infoRegistroOr;
    }

    public boolean isActivarLOV() {
        return activarLOV;
    }

    public void setActivarLOV(boolean activarLOV) {
        this.activarLOV = activarLOV;
    }
}

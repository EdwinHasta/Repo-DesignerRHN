package Controlador;

import Entidades.EersCabeceras;
import Entidades.Empleados;
import Entidades.Estructuras;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarATAprobacionHEInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
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
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlATAprobacionHE implements Serializable {

    @EJB
    AdministrarATAprobacionHEInterface administrarATAprobacionHE;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<Empleados> lovEmpleados;
    private List<Empleados> filtrarLovEmpleados;
    private Empleados empleadoSeleccionado;
    private Empleados empleadoActualProceso;
    private String infoRegistroEmpleado;

    private List<EersCabeceras> listaEersCabeceras;
    private List<EersCabeceras> filtrarListaEersCabeceras;
    private EersCabeceras cabeceraSeleccionada;
    private String auxCabeceraEstructura, auxCabeceraEmpleado, auxCabeceraDocumentoEmpl;
    private Date auxCabeceraFechaPago;

    private List<Estructuras> lovEstructuras;
    private List<Estructuras> filtrarLovEstructuras;
    private Estructuras estructuraSeleccionada;
    private String infoRegistroEstructura;

    private int indexCabecera, cualCeldaCabecera;
    private int banderaCabecera, tipoListaCabecera;
    private String altoTablaCabecera;

    private List<EersCabeceras> listEersCabecerasModificar;

    private EersCabeceras editarCabecera;

    private boolean guardado;
    private boolean permitirIndexCabecera;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaParametro;
    private boolean aceptar;
    private String paginaAnterior;
    private String nombreTablaXML, nombreArchivoXML;
    private boolean activarBuscar, activarMostrarTodos;

    //
    private int numeroScrollAporte;
    private int rowsAporteEntidad;
    //
    private String altoDivTablaInferiorIzquierda, altoDivTablaInferiorDerecha;
    private String topDivTablaInferiorIzquierda, topDivTablaInferiorDerecha;

    public ControlATAprobacionHE() {
        altoDivTablaInferiorIzquierda = "195px";
        topDivTablaInferiorIzquierda = "37px";

        altoDivTablaInferiorDerecha = "195px";
        topDivTablaInferiorDerecha = "37px";

        activarBuscar = false;
        activarMostrarTodos = true;

        listaEersCabeceras = null;
        cabeceraSeleccionada = new EersCabeceras();
        empleadoActualProceso = null;

        listEersCabecerasModificar = new ArrayList<EersCabeceras>();

        lovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        lovEstructuras = null;
        estructuraSeleccionada = new Estructuras();

        editarCabecera = new EersCabeceras();

        indexCabecera = -1;
        cualCeldaCabecera = -1;
        banderaCabecera = 0;
        tipoListaCabecera = 0;
        altoTablaCabecera = "80";

        guardado = true;
        permitirIndexCabecera = true;
        aceptar = true;

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarATAprobacionHE.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            numeroScrollAporte = 505;
            rowsAporteEntidad = 20;
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlATAprobacionHE: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPagina(String page) {
        paginaAnterior = page;
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public int obtenerNumeroScrollAporte() {
        return numeroScrollAporte;
    }

    public void pruebaRemota() {
        RequestContext context = RequestContext.getCurrentInstance();
        int tam = 0;
        if (tipoListaCabecera == 0) {
            tam = listaEersCabeceras.size();
        } else {
            tam = filtrarListaEersCabeceras.size();
        }
        if (rowsAporteEntidad < tam) {
            rowsAporteEntidad = rowsAporteEntidad + 20;
            numeroScrollAporte = numeroScrollAporte + 500;
            context.execute("operacionEnProceso.hide()");
            context.update("form:PanelTotal");
        }
    }

    public void modificarCabecera(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaCabecera == 0) {
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indice));
            } else if (!listEersCabecerasModificar.contains(listaEersCabeceras.get(indice))) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indice));
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            indexCabecera = -1;
            secRegistro = null;
        } else {
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indice));
            } else if (!listEersCabecerasModificar.contains(filtrarListaEersCabeceras.get(indice))) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indice));
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            indexCabecera = -1;
            secRegistro = null;
        }
        context.update("form:datosCabecera");
    }

    public void modificarCabecera(int indice, String confirmarCambio, String valorConfirmar) {
        indexCabecera = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            if (tipoListaCabecera == 0) {
                listaEersCabeceras.get(indice).getEstructuraaprueba().setNombre(auxCabeceraEstructura);
            } else {
                filtrarListaEersCabeceras.get(indice).getEstructuraaprueba().setNombre(auxCabeceraEstructura);
            }
            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaCabecera == 0) {
                    listaEersCabeceras.get(indice).setEstructuraaprueba(lovEstructuras.get(indiceUnicoElemento));
                } else {
                    filtrarListaEersCabeceras.get(indice).setEstructuraaprueba(lovEstructuras.get(indiceUnicoElemento));
                }
                lovEstructuras.clear();
                getLovEstructuras();
            } else {
                permitirIndexCabecera = false;
                context.update("formEstructura:EstructuraDialogo");
                context.execute("EstructuraDialogo.show()");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoListaCabecera == 0) {
                listaEersCabeceras.get(indice).getEmpleado().getPersona().setStrNumeroDocumento(auxCabeceraDocumentoEmpl);
            } else {
                filtrarListaEersCabeceras.get(indice).getEmpleado().getPersona().setStrNumeroDocumento(auxCabeceraDocumentoEmpl);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getStrNumeroDocumento().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaCabecera == 0) {
                    listaEersCabeceras.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaEersCabeceras.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                permitirIndexCabecera = false;
                context.update("formLovEmpleado:LovEmpleadoDialogo");
                context.execute("LovEmpleadoDialogo.show()");
            }
        }
        if (confirmarCambio.equalsIgnoreCase("NOMBRE")) {
            if (tipoListaCabecera == 0) {
                listaEersCabeceras.get(indice).getEmpleado().getPersona().setNombreCompleto(auxCabeceraEmpleado);
            } else {
                filtrarListaEersCabeceras.get(indice).getEmpleado().getPersona().setNombreCompleto(auxCabeceraEmpleado);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaCabecera == 0) {
                    listaEersCabeceras.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaEersCabeceras.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                permitirIndexCabecera = false;
                context.update("formLovEmpleado:LovEmpleadoDialogo");
                context.execute("LovEmpleadoDialogo.show()");
            }
        }
        if (coincidencias == 1) {
            if (tipoListaCabecera == 0) {
                if (listEersCabecerasModificar.isEmpty()) {
                    listEersCabecerasModificar.add(listaEersCabeceras.get(indice));
                } else if (!listEersCabecerasModificar.contains(listaEersCabeceras.get(indice))) {
                    listEersCabecerasModificar.add(listaEersCabeceras.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexCabecera = -1;
                secRegistro = null;
            } else {
                if (listEersCabecerasModificar.isEmpty()) {
                    listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indice));
                } else if (!listEersCabecerasModificar.contains(filtrarListaEersCabeceras.get(indice))) {
                    listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexCabecera = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosCabecera");
    }

    public boolean validarFechasRegistroCabecera(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            EersCabeceras auxiliar = null;
            if (tipoListaCabecera == 0) {
                auxiliar = listaEersCabeceras.get(indexCabecera);
            } else {
                auxiliar = filtrarListaEersCabeceras.get(indexCabecera);
            }
            if (auxiliar.getFechapago() != null) {
                if (auxiliar.getFechapago().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechaCabecera(int i, int c) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean retorno = false;
        indexCabecera = i;
        retorno = validarFechasRegistroCabecera(0);
        if (retorno == true) {
            cambiarIndiceCabecera(i, c);
            modificarCabecera(i);
        } else {
            if (tipoListaCabecera == 0) {
                listaEersCabeceras.get(i).setFechapago(auxCabeceraFechaPago);
            } else {
                filtrarListaEersCabeceras.get(i).setFechapago(auxCabeceraFechaPago);
            }
            context.update("form:datosCabecera");
            context.execute("errorFechaCabecera.show()");
        }
    }

    public void cambiarIndiceCabecera(int i, int celda) {
        if (permitirIndexCabecera == true) {
            indexCabecera = i;
            cualCeldaCabecera = celda;
            lovEstructuras = null;
            if (tipoListaCabecera == 0) {
                secRegistro = listaEersCabeceras.get(indexCabecera).getSecuencia();
                auxCabeceraEstructura = listaEersCabeceras.get(indexCabecera).getEstructuraaprueba().getNombre();
                auxCabeceraFechaPago = listaEersCabeceras.get(indexCabecera).getFechapago();
                auxCabeceraDocumentoEmpl = listaEersCabeceras.get(indexCabecera).getEmpleado().getPersona().getStrNumeroDocumento();
                auxCabeceraEmpleado = listaEersCabeceras.get(indexCabecera).getEmpleado().getPersona().getNombreCompleto();
                //
                lovEstructuras = administrarATAprobacionHE.lovEstructuras(listaEersCabeceras.get(indexCabecera).getEerestado().getSecuencia());
            } else {
                secRegistro = filtrarListaEersCabeceras.get(indexCabecera).getSecuencia();
                auxCabeceraEstructura = filtrarListaEersCabeceras.get(indexCabecera).getEstructuraaprueba().getNombre();
                auxCabeceraFechaPago = filtrarListaEersCabeceras.get(indexCabecera).getFechapago();
                auxCabeceraDocumentoEmpl = filtrarListaEersCabeceras.get(indexCabecera).getEmpleado().getPersona().getStrNumeroDocumento();
                auxCabeceraEmpleado = filtrarListaEersCabeceras.get(indexCabecera).getEmpleado().getPersona().getNombreCompleto();
                //
                lovEstructuras = administrarATAprobacionHE.lovEstructuras(filtrarListaEersCabeceras.get(indexCabecera).getEerestado().getSecuencia());
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCabecera >= 0) {
            if (cualCeldaCabecera == 0) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 1) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 2) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 3) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 4) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 5) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 6) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 7) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 8) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 9) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            if (cualCeldaCabecera == 10) {
                context.update("");
                context.execute("");
                cualCeldaCabecera = -1;
            }
            indexCabecera = -1;
            secRegistro = null;
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCabecera >= 0) {
            if (cualCeldaCabecera == 3) {
                context.update("formEstructura:EstructuraDialogo");
                context.execute("EstructuraDialogo.show()");
                cualCeldaCabecera = -1;
            }
            secRegistro = null;
        }
    }

    public void asignarIndex(int indice, int dialogo) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCabecera >= 0) {
            indexCabecera = indice;
            if (dialogo == 0) {
                context.update("formEstructura:EstructuraDialogo");
                context.execute("EstructuraDialogo.show()");
            }
        }
    }

    public void cancelarModificacion() {
        listaEersCabeceras = null;
        getListaEersCabeceras();

        indexCabecera = -1;
        cualCeldaCabecera = -1;
        banderaCabecera = 0;
        tipoListaCabecera = 0;
        altoTablaCabecera = "80";

        guardado = true;
        secRegistro = null;
        aceptar = true;

        activarBuscar = false;
        activarMostrarTodos = true;

        numeroScrollAporte = 505;
        rowsAporteEntidad = 20;

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:PanelTotal");
    }

    public void salir() {
        listaEersCabeceras = null;

        indexCabecera = -1;
        cualCeldaCabecera = -1;
        banderaCabecera = 0;
        tipoListaCabecera = 0;
        altoTablaCabecera = "80";

        guardado = true;
        secRegistro = null;
        aceptar = true;

        activarBuscar = false;
        activarMostrarTodos = true;

        numeroScrollAporte = 505;
        rowsAporteEntidad = 20;
    }

    public void guardadoGeneral() {
        if (guardado == false) {
            guardarCambiosCabecera();
        }
    }

    public void guardarCambiosCabecera() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listEersCabecerasModificar.isEmpty()) {
                administrarATAprobacionHE.editarEersCabeceras(listEersCabecerasModificar);
                listEersCabecerasModificar.clear();
            }
            listaEersCabeceras = null;
            if (activarBuscar == true) {
                listaEersCabeceras = administrarATAprobacionHE.obtenerEersCabecerasPorEmpleado(empleadoActualProceso.getSecuencia());
            } else {
                listaEersCabeceras = administrarATAprobacionHE.obtenerTotalesEersCabeceras();
            }
            context.update("form:datosHoraExtra");
            guardado = true;
            context.update("form:ACEPTAR");
            indexCabecera = -1;
            secRegistro = null;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Conceptos A Aprobar con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosCabecera Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en el guardado de Conceptos A Aprobar");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }

    }

    public void dispararDialogoBuscarEmpleados() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            context.update("formEmpleado:EmpleadoDialogo");
            context.execute("EmpleadoDialogo.show()");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    public void mostrarTodosEmpleados() {
        if (guardado == true) {
            cancelarModificacion();
            numeroScrollAporte = 505;
            rowsAporteEntidad = 20;
            cargarDatosNuevosCabecera(1);
        } else {
            RequestContext.getCurrentInstance().execute("confirmarGuardar.show()");
        }
    }

    public void actualizarEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();

        listaEersCabeceras = null;
        empleadoActualProceso = empleadoSeleccionado;
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;

        activarBuscar = true;
        activarMostrarTodos = false;

        numeroScrollAporte = 505;
        rowsAporteEntidad = 20;

        context.update("form:panelTotal");
        context.update("formEmpleado:EmpleadoDialogo");
        context.update("formEmpleado:lovEmpleado");
        context.update("formEmpleado:aceptarE");
        context.reset("formEmpleado:lovEmpleado:globalFilter");
        context.execute("EmpleadoDialogo.hide()");

        cargarDatosNuevosCabecera(2);
    }

    public void cargarDatosNuevosCabecera(int opcion) {
        try {
            if (opcion == 1) {
                listaEersCabeceras = administrarATAprobacionHE.obtenerTotalesEersCabeceras();
            }
            if (opcion == 2) {
                listaEersCabeceras = administrarATAprobacionHE.obtenerEersCabecerasPorEmpleado(empleadoActualProceso.getSecuencia());
            }
            if (listaEersCabeceras != null) {
                for (int i = 0; i < listaEersCabeceras.size(); i++) {
                    if (listaEersCabeceras.get(i).getEstructuraaprueba() == null) {
                        listaEersCabeceras.get(i).setEstructuraaprueba(new Estructuras());
                    }
                }
            }
            Thread.sleep(2000L);
            RequestContext.getCurrentInstance().update("form:PanelTotal");
            System.out.println("Ejecuto Time");
            RequestContext.getCurrentInstance().execute("operacionEnProceso.hide()");

        } catch (Exception e) {
            System.out.println("Error cargarDatosNuevos Controlador : " + e.toString());
        }
    }

    public void cancelarCambioEmpleado() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;
    }

    public void actualizarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaCabecera == 0) {
            listaEersCabeceras.get(indexCabecera).setEstructuraaprueba(estructuraSeleccionada);
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indexCabecera));
            } else if (!listEersCabecerasModificar.contains(listaEersCabeceras.get(indexCabecera))) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indexCabecera));
            }

        } else {
            filtrarListaEersCabeceras.get(indexCabecera).setEstructuraaprueba(estructuraSeleccionada);
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indexCabecera));
            } else if (!listEersCabecerasModificar.contains(filtrarListaEersCabeceras.get(indexCabecera))) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indexCabecera));
            }
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        context.update("form:datosCabecera");
        permitirIndexCabecera = true;
        filtrarLovEstructuras = null;
        estructuraSeleccionada = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;
        context.update("formEstructura:EstructuraDialogo");
        context.update("formEstructura:lovEstructura");
        context.update("formEstructura:aceptarEA");
        context.reset("formEstructura:lovEstructura:globalFilter");
        context.execute("EstructuraDialogo.hide()");
    }

    public void cancelarCambioEstructura() {
        filtrarLovEstructuras = null;
        estructuraSeleccionada = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;
        permitirIndexCabecera = true;
    }

    public void actualizarEmpleadoTabla() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaCabecera == 0) {
            listaEersCabeceras.get(indexCabecera).setEmpleado(empleadoSeleccionado);
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indexCabecera));
            } else if (!listEersCabecerasModificar.contains(listaEersCabeceras.get(indexCabecera))) {
                listEersCabecerasModificar.add(listaEersCabeceras.get(indexCabecera));
            }

        } else {
            filtrarListaEersCabeceras.get(indexCabecera).setEmpleado(empleadoSeleccionado);
            if (listEersCabecerasModificar.isEmpty()) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indexCabecera));
            } else if (!listEersCabecerasModificar.contains(filtrarListaEersCabeceras.get(indexCabecera))) {
                listEersCabecerasModificar.add(filtrarListaEersCabeceras.get(indexCabecera));
            }
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        context.update("form:datosCabecera");
        permitirIndexCabecera = true;
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;
        context.update("formLovEmpleado:LovEmpleadoDialogo");
        context.update("formLovEmpleado:lovLovEmpleado");
        context.update("formLovEmpleado:aceptarEL");
        context.reset("formLovEmpleado:lovLovEmpleado:globalFilter");
        context.execute("LovEmpleadoDialogo.hide()");
    }

    public void cancelarCambioEmpleadoTabla() {
        filtrarLovEstructuras = null;
        estructuraSeleccionada = null;
        aceptar = true;
        indexCabecera = -1;
        secRegistro = null;
        permitirIndexCabecera = true;
    }

    public void eventoFiltrar() {
        if (indexCabecera >= 0) {
            if (tipoListaCabecera == 0) {
                tipoListaCabecera = 1;
            }
        }
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (indexCabecera >= 0) {
            if (banderaCabecera == 0) {

                altoTablaCabecera = "58";
                RequestContext.getCurrentInstance().update("form:datosCabecera");
                banderaCabecera = 1;
            } else if (banderaCabecera == 1) {
                //CERRAR FILTRADO

                altoTablaCabecera = "80";
                RequestContext.getCurrentInstance().update("form:datosCabecera");
                banderaCabecera = 0;
                filtrarListaEersCabeceras = null;
                tipoListaCabecera = 0;
            }
        }
    }

    public void exportPDF() throws IOException {
        if (indexCabecera >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosCabeceraExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "HorasAAprobar_PDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexCabecera = -1;
            secRegistro = null;
        }
    }

    public void exportXLS() throws IOException {
        if (indexCabecera >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosEmpleadoExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "HorasAAprobar_XLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexCabecera = -1;
            secRegistro = null;
        }
    }

    public String obtenerTablaXML() {
        if (indexCabecera >= 0) {
            nombreTablaXML = ":formExportar:datosEmpleadoExportar";
        }
        return nombreTablaXML;
    }

    public String obtenerNombreArchivoXML() {
        if (indexCabecera >= 0) {
            nombreArchivoXML = "HorasAAproba_XML";
        }
        return nombreArchivoXML;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void validarRastro() {
        if (indexCabecera >= 0) {
            verificarRastroCabecera();
        }
    }

    public void verificarRastroCabecera() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaEersCabeceras != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EERSCABECERAS");
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
            if (administrarRastros.verificarHistoricosTabla("EERSCABECERAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexCabecera = -1;
    }

    public void guardarSalir() {
        guardadoGeneral();
        salir();
    }

    public void cancelarSalir() {
        cancelarModificacion();
        salir();
    }

    //GET - SET
    public List<Empleados> getLovEmpleados() {
        lovEmpleados = administrarATAprobacionHE.lovEmpleados();
        if (lovEmpleados != null) {
            infoRegistroEmpleado = "Cantidad de registros : " + lovEmpleados.size();
        } else {
            infoRegistroEmpleado = "Cantidad de registros : 0";
        }
        return lovEmpleados;
    }

    public void setLovEmpleados(List<Empleados> lovEmpleados) {
        this.lovEmpleados = lovEmpleados;
    }

    public List<Empleados> getFiltrarLovEmpleados() {
        return filtrarLovEmpleados;
    }

    public void setFiltrarLovEmpleados(List<Empleados> filtrarLovEmpleados) {
        this.filtrarLovEmpleados = filtrarLovEmpleados;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public Empleados getEmpleadoActualProceso() {
        if (empleadoActualProceso == null) {
            getLovEmpleados();
            if (lovEmpleados != null) {
                if (lovEmpleados.size() > 0) {
                    empleadoActualProceso = lovEmpleados.get(0);
                }
            }
        }
        return empleadoActualProceso;
    }

    public void setEmpleadoActualProceso(Empleados empleadoActualProceso) {
        this.empleadoActualProceso = empleadoActualProceso;
    }

    public List<EersCabeceras> getListaEersCabeceras() {
        if (listaEersCabeceras == null) {
            listaEersCabeceras = administrarATAprobacionHE.obtenerTotalesEersCabeceras();
            if (listaEersCabeceras != null) {
                for (int i = 0; i < listaEersCabeceras.size(); i++) {
                    if (listaEersCabeceras.get(i).getEstructuraaprueba() == null) {
                        listaEersCabeceras.get(i).setEstructuraaprueba(new Estructuras());
                    }
                }
            }
        }

        return listaEersCabeceras;
    }

    public void setListaEersCabeceras(List<EersCabeceras> listaEersCabeceras) {
        this.listaEersCabeceras = listaEersCabeceras;
    }

    public List<EersCabeceras> getFiltrarListaEersCabeceras() {
        return filtrarListaEersCabeceras;
    }

    public void setFiltrarListaEersCabeceras(List<EersCabeceras> filtrarListaEersCabeceras) {
        this.filtrarListaEersCabeceras = filtrarListaEersCabeceras;
    }

    public EersCabeceras getCabeceraSeleccionada() {
        return cabeceraSeleccionada;
    }

    public void setCabeceraSeleccionada(EersCabeceras cabeceraSeleccionada) {
        this.cabeceraSeleccionada = cabeceraSeleccionada;
    }

    public List<Estructuras> getLovEstructuras() {
        return lovEstructuras;
    }

    public void setLovEstructuras(List<Estructuras> lovEstructuras) {
        this.lovEstructuras = lovEstructuras;
    }

    public List<Estructuras> getFiltrarLovEstructuras() {
        return filtrarLovEstructuras;
    }

    public void setFiltrarLovEstructuras(List<Estructuras> filtrarLovEstructuras) {
        this.filtrarLovEstructuras = filtrarLovEstructuras;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public String getAltoTablaCabecera() {
        return altoTablaCabecera;
    }

    public void setAltoTablaCabecera(String altoTablaCabecera) {
        this.altoTablaCabecera = altoTablaCabecera;
    }

    public EersCabeceras getEditarCabecera() {
        return editarCabecera;
    }

    public void setEditarCabecera(EersCabeceras editarCabecera) {
        this.editarCabecera = editarCabecera;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
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

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public boolean isActivarBuscar() {
        return activarBuscar;
    }

    public void setActivarBuscar(boolean activarBuscar) {
        this.activarBuscar = activarBuscar;
    }

    public boolean isActivarMostrarTodos() {
        return activarMostrarTodos;
    }

    public void setActivarMostrarTodos(boolean activarMostrarTodos) {
        this.activarMostrarTodos = activarMostrarTodos;
    }

    public String getInfoRegistroEmpleado() {
        return infoRegistroEmpleado;
    }

    public void setInfoRegistroEmpleado(String infoRegistroEmpleado) {
        this.infoRegistroEmpleado = infoRegistroEmpleado;
    }

    public String getInfoRegistroEstructura() {
        getLovEstructuras();
        if (lovEstructuras != null) {
            infoRegistroEstructura = "Cantidad de registros : " + lovEstructuras.size();
        } else {
            infoRegistroEstructura = "Cantidad de registros : 0";
        }
        return infoRegistroEstructura;
    }

    public void setInfoRegistroEstructura(String infoRegistroEstructura) {
        this.infoRegistroEstructura = infoRegistroEstructura;
    }

    public int getNumeroScrollAporte() {
        return numeroScrollAporte;
    }

    public void setNumeroScrollAporte(int numeroPrueba) {
        this.numeroScrollAporte = numeroPrueba;
    }

    public int getRowsAporteEntidad() {
        return rowsAporteEntidad;
    }

    public void setRowsAporteEntidad(int rowsAporteEntidad) {
        this.rowsAporteEntidad = rowsAporteEntidad;
    }

    public String getAltoDivTablaInferiorIzquierda() {
        return altoDivTablaInferiorIzquierda;
    }

    public void setAltoDivTablaInferiorIzquierda(String altoDivTablaInferiorIzquierda) {
        this.altoDivTablaInferiorIzquierda = altoDivTablaInferiorIzquierda;
    }

    public String getAltoDivTablaInferiorDerecha() {
        return altoDivTablaInferiorDerecha;
    }

    public void setAltoDivTablaInferiorDerecha(String altoDivTablaInferiorDerecha) {
        this.altoDivTablaInferiorDerecha = altoDivTablaInferiorDerecha;
    }

    public String getTopDivTablaInferiorIzquierda() {
        return topDivTablaInferiorIzquierda;
    }

    public void setTopDivTablaInferiorIzquierda(String topDivTablaInferiorIzquierda) {
        this.topDivTablaInferiorIzquierda = topDivTablaInferiorIzquierda;
    }

    public String getTopDivTablaInferiorDerecha() {
        return topDivTablaInferiorDerecha;
    }

    public void setTopDivTablaInferiorDerecha(String topDivTablaInferiorDerecha) {
        this.topDivTablaInferiorDerecha = topDivTablaInferiorDerecha;
    }

}

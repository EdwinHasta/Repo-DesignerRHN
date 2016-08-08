package Controlador;

import Entidades.Cuadrillas;
import Entidades.DetallesTurnosRotativos;
import Entidades.Empleados;
import Entidades.Personas;
import Entidades.Turnosrotativos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarATCuadrillaInterface;
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
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlATCuadrilla implements Serializable {

    @EJB
    AdministrarATCuadrillaInterface administrarATCuadrilla;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Cuadrillas
    private List<Cuadrillas> listaCuadrillas;
    private List<Cuadrillas> filtrarListaCuadrillas;
    private Cuadrillas cuadrillaSeleccionada;
    private String auxCuadrillaDescripcion;
    private short auxCuadrillaCodigo;
    //Turnos Rotativos
    private List<Turnosrotativos> listaTurnosRotativos;
    private List<Turnosrotativos> filtrarListaTurnosRotativos;
    private Turnosrotativos turnoSeleccionado;
    private short auxTurnoCodigo;
    private String auxTurnoDescripcion;
    private Date auxTurnoFecha;
    private short auxTurnoHoraInicial, auxTurnoHoraFinal, auxTurnoMinInicial, auxTurnoMinFinal;
    //Detalles Turnos Rotativos
    private List<DetallesTurnosRotativos> listaDetallesTurnosRotativos;
    private List<DetallesTurnosRotativos> filtrarListaDetallesTurnosRotativos;
    private DetallesTurnosRotativos detalleSeleccionado;
    private short auxDetalleOrden;
    private BigDecimal auxDetalleCodigo;
    private String auxDetalleEmpleado;

    //
    private List<Empleados> lovEmpleados;
    private List<Empleados> filtrarLovEmpleados;
    private Empleados empleadoSeleccionado;
    private String infoRegistroEmpleado;
    //
    private List<Empleados> lovEmpleadosPorCuadrillas;
    private List<Empleados> filtrarLovEmpleadosPorCuadrillas;
    private Empleados empleadoCuadrillasSeleccionado;
    private String infoRegistroEmpleadoCuadrilla;
    //modificar - crear - borrar
    private List<Cuadrillas> listCuadrillasModificar;
    private List<Cuadrillas> listCuadrillasBorrar;
    private List<Cuadrillas> listCuadrillasCrear;
    private List<Turnosrotativos> listTurnosRotativosModificar;
    private List<Turnosrotativos> listTurnosRotativosBorrar;
    private List<Turnosrotativos> listTurnosRotativosCrear;
    private List<DetallesTurnosRotativos> listDetallesTurnosRotativosModificar;
    private List<DetallesTurnosRotativos> listDetallesTurnosRotativosBorrar;
    private List<DetallesTurnosRotativos> listDetallesTurnosRotativosCrear;
    //crear 
    public Cuadrillas nuevaCuadrilla;
    private Turnosrotativos nuevaTurnoRotativo;
    private DetallesTurnosRotativos nuevaDetalle;
    //duplicar
    private Cuadrillas duplicarCuadrilla;
    private Turnosrotativos duplicarTurnoRotativo;
    private DetallesTurnosRotativos duplicarDetalle;
    //editar celda
    private Cuadrillas editarCuadrilla;
    private Turnosrotativos editarTurnoRotativo;
    private DetallesTurnosRotativos editarDetalle;
    //Tabla 
    private int cualCeldaCuadrilla, tipoListaCuadrilla, cualCeldaTurno, tipoListaTurno, cualCeldaDetalle, tipoListaDetalle;
    private int indexCuadrilla, indexCuadrillaAux, indexTurno, indexTurnoAux, indexDetalle;
    private int banderaCuadrilla, banderaTurno, banderaDetalle;
    private String altoTablaCuadrilla, altoTablaTurno, altoTablaDetalle;
    private boolean permitirIndexDetalle;
    //Otro
    private boolean guardado;
    private boolean cambiosCuadrilla, cambiosTurno, cambiosDetalle;
    private int tipoActualizacion;
    private BigInteger l;
    private int k;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaParametro;
    private boolean aceptar;
    private String paginaAnterior;
    //Columnas Tabla Cuadrillas
    private Column cuadrillaCodigo, cuadrillaDescripcion, cuadrillaDias, cuadrillaModulo;
    private Column turnoCodigo, turnoDescripcion, turnoFecha, turnoHoraInicial, turnoHoraFinal, turnoMinInicial, turnoMinFinal;
    private Column detalleOrden, detalleCodigo, detalleEmpleado;
    //
    private String nombreTablaXML, nombreArchivoXML;
    //
    private boolean activarBtnMostrar, activarBtnBuscar;

    public ControlATCuadrilla() {
        activarBtnBuscar = false;
        activarBtnMostrar = true;

        listaCuadrillas = null;
        listaTurnosRotativos = null;
        listaDetallesTurnosRotativos = null;

        listCuadrillasBorrar = new ArrayList<Cuadrillas>();
        listCuadrillasCrear = new ArrayList<Cuadrillas>();
        listCuadrillasModificar = new ArrayList<Cuadrillas>();
        listTurnosRotativosModificar = new ArrayList<Turnosrotativos>();
        listTurnosRotativosBorrar = new ArrayList<Turnosrotativos>();
        listTurnosRotativosCrear = new ArrayList<Turnosrotativos>();
        listDetallesTurnosRotativosModificar = new ArrayList<DetallesTurnosRotativos>();
        listDetallesTurnosRotativosBorrar = new ArrayList<DetallesTurnosRotativos>();
        listDetallesTurnosRotativosCrear = new ArrayList<DetallesTurnosRotativos>();

        editarCuadrilla = new Cuadrillas();
        editarTurnoRotativo = new Turnosrotativos();
        editarDetalle = new DetallesTurnosRotativos();
        cualCeldaCuadrilla = -1;
        cualCeldaTurno = -1;
        cualCeldaDetalle = -1;
        tipoListaCuadrilla = 0;
        tipoListaTurno = 0;
        tipoListaDetalle = 0;

        tipoActualizacion = 0;

        nuevaCuadrilla = new Cuadrillas();
        nuevaTurnoRotativo = new Turnosrotativos();
        nuevaDetalle = new DetallesTurnosRotativos();
        nuevaDetalle.setEmpleado(new Empleados());
        nuevaDetalle.getEmpleado().setPersona(new Personas());

        altoTablaCuadrilla = "70";
        altoTablaTurno = "80";
        altoTablaDetalle = "80";

        secRegistro = null;
        backUpSecRegistro = null;

        k = 0;
        guardado = true;
        cambiosCuadrilla = false;
        cambiosTurno = false;
        cambiosDetalle = false;
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarATCuadrilla.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPagina(String page) {
        paginaAnterior = page;
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public void modificarCuadrilla(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaCuadrilla == 0) {
            if (!listCuadrillasCrear.contains(listaCuadrillas.get(indice))) {

                if (listCuadrillasModificar.isEmpty()) {
                    listCuadrillasModificar.add(listaCuadrillas.get(indice));
                } else if (!listCuadrillasModificar.contains(listaCuadrillas.get(indice))) {
                    listCuadrillasModificar.add(listaCuadrillas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    cambiosCuadrilla = true;
                    context.update("form:ACEPTAR");
                }
            }
            indexCuadrilla = -1;
            secRegistro = null;
        } else {
            if (!listCuadrillasCrear.contains(filtrarListaCuadrillas.get(indice))) {

                if (listCuadrillasModificar.isEmpty()) {
                    listCuadrillasModificar.add(filtrarListaCuadrillas.get(indice));
                } else if (!listCuadrillasModificar.contains(filtrarListaCuadrillas.get(indice))) {
                    listCuadrillasModificar.add(filtrarListaCuadrillas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    cambiosCuadrilla = true;
                    context.update("form:ACEPTAR");
                }
            }
            indexCuadrilla = -1;
            secRegistro = null;
        }
        context.update("form:datosCuadrilla");
    }

    public void modificarTurno(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validar = validarCamposNullTurnos(0);
        if (validar == true) {
            if (tipoListaTurno == 0) {
                if (!listTurnosRotativosCrear.contains(listaTurnosRotativos.get(indice))) {

                    if (listTurnosRotativosModificar.isEmpty()) {
                        listTurnosRotativosModificar.add(listaTurnosRotativos.get(indice));
                    } else if (!listTurnosRotativosModificar.contains(listaTurnosRotativos.get(indice))) {
                        listTurnosRotativosModificar.add(listaTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosTurno = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexTurno = -1;
                secRegistro = null;
            } else {
                if (!listTurnosRotativosCrear.contains(filtrarListaTurnosRotativos.get(indice))) {
                    if (listTurnosRotativosModificar.isEmpty()) {
                        listTurnosRotativosModificar.add(filtrarListaTurnosRotativos.get(indice));
                    } else if (!listTurnosRotativosModificar.contains(filtrarListaTurnosRotativos.get(indice))) {
                        listTurnosRotativosModificar.add(filtrarListaTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosTurno = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexTurno = -1;
                secRegistro = null;
            }
        } else {
            if (tipoListaTurno == 0) {
                listaTurnosRotativos.get(indice).setCodigo(auxTurnoCodigo);
                listaTurnosRotativos.get(indice).setDescripcion(auxTurnoDescripcion);
                listaTurnosRotativos.get(indice).setFechasemilla(auxTurnoFecha);
                listaTurnosRotativos.get(indice).setHorainicial(auxTurnoHoraInicial);
                listaTurnosRotativos.get(indice).setHorafinal(auxTurnoHoraFinal);
                listaTurnosRotativos.get(indice).setMinutoinicial(auxTurnoMinInicial);
                listaTurnosRotativos.get(indice).setMinutofinal(auxTurnoMinFinal);
            } else {
                filtrarListaTurnosRotativos.get(indice).setCodigo(auxTurnoCodigo);
                filtrarListaTurnosRotativos.get(indice).setDescripcion(auxTurnoDescripcion);
                filtrarListaTurnosRotativos.get(indice).setFechasemilla(auxTurnoFecha);
                filtrarListaTurnosRotativos.get(indice).setHorainicial(auxTurnoHoraInicial);
                filtrarListaTurnosRotativos.get(indice).setHorafinal(auxTurnoHoraFinal);
                filtrarListaTurnosRotativos.get(indice).setMinutoinicial(auxTurnoMinInicial);
                filtrarListaTurnosRotativos.get(indice).setMinutofinal(auxTurnoMinFinal);
            }
            context.execute("errorNullTurno.show()");
        }
        context.update("form:datosTurno");
    }

    public void modificarDetalle(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validar = validarCamposNullDetalles(0);
        if (validar == true) {
            if (tipoListaDetalle == 0) {
                if (!listDetallesTurnosRotativosCrear.contains(listaDetallesTurnosRotativos.get(indice))) {

                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indice));
                    } else if (!listDetallesTurnosRotativosModificar.contains(listaDetallesTurnosRotativos.get(indice))) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosDetalle = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexDetalle = -1;
                secRegistro = null;
            } else {
                if (!listDetallesTurnosRotativosCrear.contains(filtrarListaDetallesTurnosRotativos.get(indice))) {
                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indice));
                    } else if (!listDetallesTurnosRotativosModificar.contains(filtrarListaDetallesTurnosRotativos.get(indice))) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosDetalle = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexDetalle = -1;
                secRegistro = null;
            }
        } else {
            if (tipoListaDetalle == 0) {
                listaDetallesTurnosRotativos.get(indice).setOrden(auxDetalleOrden);
                listaDetallesTurnosRotativos.get(indice).getEmpleado().setCodigoempleado(auxDetalleCodigo);
                listaDetallesTurnosRotativos.get(indice).getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            } else {
                filtrarListaDetallesTurnosRotativos.get(indice).setOrden(auxDetalleOrden);
                filtrarListaDetallesTurnosRotativos.get(indice).getEmpleado().setCodigoempleado(auxDetalleCodigo);
                filtrarListaDetallesTurnosRotativos.get(indice).getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            }
            context.execute("errorNullDetalle.show()");
        }
        context.update("form:datosDetalle");
    }

    public void modificarDetalle(int indice, String confirmarCambio, String valorConfirmar) {
        indexDetalle = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoListaDetalle == 0) {
                listaDetallesTurnosRotativos.get(indice).getEmpleado().setCodigoempleado(auxDetalleCodigo);
            } else {
                filtrarListaDetallesTurnosRotativos.get(indice).getEmpleado().setCodigoempleado(auxDetalleCodigo);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaDetalle == 0) {
                    listaDetallesTurnosRotativos.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesTurnosRotativos.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                permitirIndexDetalle = false;
                context.update("formEmpleado:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("NOMBRE")) {
            if (tipoListaDetalle == 0) {
                listaDetallesTurnosRotativos.get(indice).getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            } else {
                filtrarListaDetallesTurnosRotativos.get(indice).getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaDetalle == 0) {
                    listaDetallesTurnosRotativos.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaDetallesTurnosRotativos.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                permitirIndexDetalle = false;
                context.update("formEmpleado:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaDetalle == 0) {
                if (!listDetallesTurnosRotativosCrear.contains(listaDetallesTurnosRotativos.get(indice))) {

                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indice));
                    } else if (!listDetallesTurnosRotativosModificar.contains(listaDetallesTurnosRotativos.get(indice))) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosDetalle = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexDetalle = -1;
                secRegistro = null;
            } else {
                if (!listDetallesTurnosRotativosCrear.contains(filtrarListaDetallesTurnosRotativos.get(indice))) {
                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indice));
                    } else if (!listDetallesTurnosRotativosModificar.contains(filtrarListaDetallesTurnosRotativos.get(indice))) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        cambiosDetalle = true;
                        context.update("form:ACEPTAR");
                    }
                }
                indexDetalle = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosDetalle");
    }

    public void modificarParametrosObligatoriosCuadrilla(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validar = validarCamposNullCuadrillas(0);
        if (validar == true) {
            modificarCuadrilla(indice);
        } else {
            if (tipoListaCuadrilla == 0) {
                listaCuadrillas.get(indice).setCodigo(auxCuadrillaCodigo);
                listaCuadrillas.get(indice).setDescripcion(auxCuadrillaDescripcion);
            } else {
                filtrarListaCuadrillas.get(indice).setCodigo(auxCuadrillaCodigo);
                filtrarListaCuadrillas.get(indice).setDescripcion(auxCuadrillaDescripcion);
            }
            indexCuadrilla = -1;
            secRegistro = null;
            context.execute("errorNullCuadrillas.show()");
        }
        context.update("form:datosCuadrilla");
    }

    public boolean validarFechasRegistroTurno(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            Turnosrotativos auxiliar = null;
            if (tipoListaTurno == 0) {
                auxiliar = listaTurnosRotativos.get(indexTurno);
            } else {
                auxiliar = filtrarListaTurnosRotativos.get(indexTurno);
            }
            if (auxiliar.getFechasemilla().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaTurnoRotativo.getFechasemilla().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarTurnoRotativo.getFechasemilla().after(fechaParametro)) {
                retorno = true;
            } else {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarFechaTurno(int i, int c) {
        Turnosrotativos auxiliar = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaTurno == 0) {
            auxiliar = listaTurnosRotativos.get(i);
        } else {
            auxiliar = filtrarListaTurnosRotativos.get(i);
        }
        if (auxiliar.getFechasemilla() != null) {
            boolean retorno = false;
            indexTurno = i;
            retorno = validarCamposNullTurnos(0);
            if (retorno == true) {
                cambiarIndiceTurno(i, c);
                modificarTurno(i);
            } else {
                if (tipoListaTurno == 0) {
                    listaTurnosRotativos.get(i).setFechasemilla(auxTurnoFecha);
                } else {
                    filtrarListaTurnosRotativos.get(i).setFechasemilla(auxTurnoFecha);

                }
                context.update("form:datosTurno");
                context.execute("errorFechaTurno.show()");
            }
        } else {
            if (tipoListaTurno == 0) {
                listaTurnosRotativos.get(i).setFechasemilla(auxTurnoFecha);
            } else {
                filtrarListaTurnosRotativos.get(i).setFechasemilla(auxTurnoFecha);

            }
            context.update("form:datosTurno");
            context.execute("errorNullTurno.show()");
        }
    }

    public void cambiarIndiceCuadrilla(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosDetalle == false && cambiosTurno == false) {
            indexCuadrilla = indice;

            indexCuadrillaAux = indice;
            cualCeldaCuadrilla = celda;

            indexTurno = -1;
            listaTurnosRotativos = null;
            indexDetalle = -1;
            listaDetallesTurnosRotativos = null;

            if (tipoListaCuadrilla == 0) {
                auxCuadrillaCodigo = listaCuadrillas.get(indexCuadrilla).getCodigo();
                auxCuadrillaDescripcion = listaCuadrillas.get(indexCuadrilla).getDescripcion();
                secRegistro = listaCuadrillas.get(indexCuadrilla).getSecuencia();
                listaTurnosRotativos = administrarATCuadrilla.obtenerTurnosRotativos(secRegistro);
            } else {
                auxCuadrillaCodigo = filtrarListaCuadrillas.get(indexCuadrilla).getCodigo();
                auxCuadrillaDescripcion = filtrarListaCuadrillas.get(indexCuadrilla).getDescripcion();
                secRegistro = filtrarListaCuadrillas.get(indexCuadrilla).getSecuencia();
                listaTurnosRotativos = administrarATCuadrilla.obtenerTurnosRotativos(secRegistro);
            }

            if (banderaTurno == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
                turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                turnoFecha.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
                altoTablaTurno = "80";
                RequestContext.getCurrentInstance().update("form:datosTurno");
                banderaTurno = 0;
                filtrarListaTurnosRotativos = null;
                tipoListaTurno = 0;
            }

            if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }

            context.update("form:datosTurno");
            context.update("form:datosDetalle");
        } else {
            context.execute("confirmarGuardar.show()");
        }

    }

    public void cambiarIndiceTurno(int indice, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosDetalle == false) {
            indexTurno = indice;
            cualCeldaTurno = celda;
            indexTurnoAux = indice;

            indexCuadrilla = -1;
            indexDetalle = -1;

            listaDetallesTurnosRotativos = null;

            if (tipoListaTurno == 0) {
                auxTurnoCodigo = listaTurnosRotativos.get(indexTurno).getCodigo();
                auxTurnoDescripcion = listaTurnosRotativos.get(indexTurno).getDescripcion();
                auxTurnoFecha = listaTurnosRotativos.get(indexTurno).getFechasemilla();
                auxTurnoHoraFinal = listaTurnosRotativos.get(indexTurno).getHorafinal();
                auxTurnoHoraInicial = listaTurnosRotativos.get(indexTurno).getHorainicial();
                auxTurnoMinFinal = listaTurnosRotativos.get(indexTurno).getMinutofinal();
                auxTurnoMinInicial = listaTurnosRotativos.get(indexTurno).getMinutoinicial();
                secRegistro = listaTurnosRotativos.get(indexTurno).getSecuencia();
                listaDetallesTurnosRotativos = administrarATCuadrilla.obtenerDetallesTurnosRotativos(secRegistro);
            } else {
                auxTurnoCodigo = filtrarListaTurnosRotativos.get(indexTurno).getCodigo();
                auxTurnoDescripcion = filtrarListaTurnosRotativos.get(indexTurno).getDescripcion();
                auxTurnoFecha = filtrarListaTurnosRotativos.get(indexTurno).getFechasemilla();
                auxTurnoHoraFinal = filtrarListaTurnosRotativos.get(indexTurno).getHorafinal();
                auxTurnoHoraInicial = filtrarListaTurnosRotativos.get(indexTurno).getHorainicial();
                auxTurnoMinFinal = filtrarListaTurnosRotativos.get(indexTurno).getMinutofinal();
                auxTurnoMinInicial = filtrarListaTurnosRotativos.get(indexTurno).getMinutoinicial();
                secRegistro = filtrarListaTurnosRotativos.get(indexTurno).getSecuencia();
                listaDetallesTurnosRotativos = administrarATCuadrilla.obtenerDetallesTurnosRotativos(secRegistro);
            }

            if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }

            context.update("form:datosDetalle");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceDetalle(int indice, int celda) {
        if (permitirIndexDetalle == true) {
            indexDetalle = indice;
            cualCeldaDetalle = celda;

            indexCuadrilla = -1;
            indexTurno = -1;

            if (tipoListaDetalle == 0) {
                auxDetalleOrden = listaDetallesTurnosRotativos.get(indexDetalle).getOrden();
                auxDetalleCodigo = listaDetallesTurnosRotativos.get(indexDetalle).getEmpleado().getCodigoempleado();
                auxDetalleEmpleado = listaDetallesTurnosRotativos.get(indexDetalle).getEmpleado().getPersona().getNombreCompleto();
                secRegistro = listaDetallesTurnosRotativos.get(indexDetalle).getSecuencia();
            } else {
                auxDetalleOrden = filtrarListaDetallesTurnosRotativos.get(indexDetalle).getOrden();
                auxDetalleCodigo = filtrarListaDetallesTurnosRotativos.get(indexDetalle).getEmpleado().getCodigoempleado();
                auxDetalleEmpleado = filtrarListaDetallesTurnosRotativos.get(indexDetalle).getEmpleado().getPersona().getNombreCompleto();
                secRegistro = filtrarListaDetallesTurnosRotativos.get(indexDetalle).getSecuencia();
            }
        }
    }

    public void guardadoGeneral() {
        if (guardado == false) {
            if (cambiosCuadrilla == true) {
                guardarCambiosCuadrillas();
            }
            if (cambiosTurno == true) {
                guardarCambiosTurnos();
            }
            if (cambiosDetalle == true) {
                System.out.println("Guardo");
                guardarCambiosDetalles();
            }
        }
    }

    public void guardarCambiosCuadrillas() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listCuadrillasBorrar.isEmpty()) {
                administrarATCuadrilla.borrarCuadrillas(listCuadrillasBorrar);
                listCuadrillasBorrar.clear();
            }
            if (!listCuadrillasCrear.isEmpty()) {
                administrarATCuadrilla.crearCuadrillas(listCuadrillasCrear);
                listCuadrillasCrear.clear();
            }
            if (!listCuadrillasModificar.isEmpty()) {
                administrarATCuadrilla.editarCuadrillas(listCuadrillasModificar);
                listCuadrillasModificar.clear();
            }
            listaCuadrillas = null;
            getListaCuadrillas();
            context.update("form:datosCuadrilla");
            guardado = true;
            context.update("form:ACEPTAR");
            k = 0;
            indexCuadrilla = -1;
            secRegistro = null;
            cambiosCuadrilla = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Cuadrillas con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosCuadrillas Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en el guardado de Cuadrillas");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosTurnos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listTurnosRotativosBorrar.isEmpty()) {
                administrarATCuadrilla.borrarTurnosRotativos(listTurnosRotativosBorrar);
                listTurnosRotativosBorrar.clear();
            }
            if (!listTurnosRotativosCrear.isEmpty()) {
                administrarATCuadrilla.crearTurnosRotativos(listTurnosRotativosCrear);
                listTurnosRotativosCrear.clear();
            }
            if (!listTurnosRotativosModificar.isEmpty()) {
                administrarATCuadrilla.editarTurnosRotativos(listTurnosRotativosModificar);
                listTurnosRotativosModificar.clear();
            }
            listaTurnosRotativos = null;
            if (tipoListaCuadrilla == 0) {
                listaTurnosRotativos = administrarATCuadrilla.obtenerTurnosRotativos(listaCuadrillas.get(indexCuadrillaAux).getSecuencia());
            } else {
                listaTurnosRotativos = administrarATCuadrilla.obtenerTurnosRotativos(filtrarListaCuadrillas.get(indexCuadrillaAux).getSecuencia());
            }
            context.update("form:datosTurno");
            guardado = true;
            context.update("form:ACEPTAR");
            k = 0;
            indexTurno = -1;
            secRegistro = null;
            cambiosTurno = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Turnos Rotativos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosTurnos Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en el guardado de Turnos Rotativos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosDetalles() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            System.out.println("guardarCambiosDetalles");
            if (!listDetallesTurnosRotativosBorrar.isEmpty()) {
                System.out.println("guardarCambiosDetalles listDetallesTurnosRotativosBorrar");
                administrarATCuadrilla.borrarDetallesTurnosRotativos(listDetallesTurnosRotativosBorrar);
                listDetallesTurnosRotativosBorrar.clear();
            }
            if (!listDetallesTurnosRotativosCrear.isEmpty()) {
                System.out.println("guardarCambiosDetalles listDetallesTurnosRotativosCrear");
                administrarATCuadrilla.crearDetallesTurnosRotativos(listDetallesTurnosRotativosCrear);
                listDetallesTurnosRotativosCrear.clear();
            }
            if (!listDetallesTurnosRotativosModificar.isEmpty()) {
                System.out.println("guardarCambiosDetalles listDetallesTurnosRotativosModificar");
                administrarATCuadrilla.editarDetallesTurnosRotativos(listDetallesTurnosRotativosModificar);
                listDetallesTurnosRotativosModificar.clear();
            }
            listaDetallesTurnosRotativos = null;
            if (tipoListaTurno == 0) {
                listaDetallesTurnosRotativos = administrarATCuadrilla.obtenerDetallesTurnosRotativos(listaTurnosRotativos.get(indexTurnoAux).getSecuencia());
            } else {
                listaDetallesTurnosRotativos = administrarATCuadrilla.obtenerDetallesTurnosRotativos(filtrarListaTurnosRotativos.get(indexTurnoAux).getSecuencia());
            }
            context.update("form:datosDetalle");
            guardado = true;
            context.update("form:ACEPTAR");
            k = 0;
            indexDetalle = -1;
            secRegistro = null;
            cambiosDetalle = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Detalles Turnos Rotativos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosDetalles Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en el guardado de Detalles Turnos Rotativos");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }
    //CANCELAR MODIFICACIONES

    public void cancelarModificacion() {
        if (banderaCuadrilla == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
            cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
            cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
            cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
            cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
            altoTablaCuadrilla = "70";
            RequestContext.getCurrentInstance().update("form:datosCuadrilla");
            banderaCuadrilla = 0;
            filtrarListaCuadrillas = null;
            tipoListaCuadrilla = 0;
        }
        if (banderaTurno == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
            turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
            turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
            turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
            turnoFecha.setFilterStyle("display: none; visibility: hidden;");
            turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
            turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
            turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
            turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
            turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
            turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
            turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
            turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTablaTurno = "80";
            RequestContext.getCurrentInstance().update("form:datosTurno");
            banderaTurno = 0;
            filtrarListaTurnosRotativos = null;
            tipoListaTurno = 0;
        }

        if (banderaDetalle == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
            detalleOrden.setFilterStyle("display: none; visibility: hidden;");
            detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
            detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
            detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
            detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
            altoTablaDetalle = "80";
            RequestContext.getCurrentInstance().update("form:datosDetalle");
            banderaDetalle = 0;
            filtrarListaDetallesTurnosRotativos = null;
            tipoListaDetalle = 0;
        }

        listCuadrillasBorrar.clear();
        listCuadrillasCrear.clear();
        listCuadrillasModificar.clear();

        listTurnosRotativosCrear.clear();
        listTurnosRotativosModificar.clear();
        listTurnosRotativosBorrar.clear();

        listDetallesTurnosRotativosCrear.clear();
        listDetallesTurnosRotativosModificar.clear();
        listDetallesTurnosRotativosBorrar.clear();

        indexCuadrilla = -1;
        indexCuadrillaAux = -1;
        indexTurno = -1;
        indexTurnoAux = -1;
        indexDetalle = -1;
        secRegistro = null;
        k = 0;
        listaCuadrillas = null;
        getListaCuadrillas();
        listaTurnosRotativos = null;
        listaDetallesTurnosRotativos = null;
        guardado = true;
        cambiosCuadrilla = false;
        cambiosTurno = false;
        cambiosDetalle = false;
        permitirIndexDetalle = true;
        activarBtnBuscar = false;
        activarBtnMostrar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCuadrilla");
        context.update("form:datosTurno");
        context.update("form:datosDetalle");
        context.update("form:ACEPTAR");
        context.update("form:BUSCAR");
        context.update("form:TODOS");

    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (indexCuadrilla >= 0) {
            if (tipoListaCuadrilla == 0) {
                editarCuadrilla = listaCuadrillas.get(indexCuadrilla);
            } else {
                editarCuadrilla = filtrarListaCuadrillas.get(indexCuadrilla);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaCuadrilla == 0) {
                context.update("formularioDialogos:editarCuadrillaCodigo");
                context.execute("editarCuadrillaCodigo.show()");
                cualCeldaCuadrilla = -1;
            } else if (cualCeldaCuadrilla == 1) {
                context.update("formularioDialogos:editarCuadrillaDescripcion");
                context.execute("editarCuadrillaDescripcion.show()");
                cualCeldaCuadrilla = -1;
            } else if (cualCeldaCuadrilla == 3) {
                context.update("formularioDialogos:editarCuadrillaModulo");
                context.execute("editarCuadrillaModulo.show()");
                cualCeldaCuadrilla = -1;
            } else if (cualCeldaCuadrilla == 5) {
                context.update("formularioDialogos:editarCuadrillaDias");
                context.execute("editarCuadrillaDias.show()");
                cualCeldaCuadrilla = -1;
            }
            indexCuadrilla = -1;
            secRegistro = null;
        }
        if (indexTurno >= 0) {
            if (tipoListaTurno == 0) {
                editarTurnoRotativo = listaTurnosRotativos.get(indexTurno);
            } else {
                editarTurnoRotativo = filtrarListaTurnosRotativos.get(indexTurno);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaTurno == 0) {
                context.update("formularioDialogos:editarTurnoCodigo");
                context.execute("editarTurnoCodigo.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 1) {
                context.update("formularioDialogos:editarTurnoDescripcion");
                context.execute("editarTurnoDescripcion.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 2) {
                context.update("formularioDialogos:editarTurnoFechaSemilla");
                context.execute("editarTurnoFechaSemilla.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 3) {
                context.update("formularioDialogos:editarTurnoHoraInicial");
                context.execute("editarTurnoHoraInicial.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 4) {
                context.update("formularioDialogos:editarTurnoHoraFinal");
                context.execute("editarTurnoHoraFinal.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 5) {
                context.update("formularioDialogos:editarTurnoMinInicial");
                context.execute("editarTurnoMinInicial.show()");
                cualCeldaTurno = -1;
            } else if (cualCeldaTurno == 6) {
                context.update("formularioDialogos:editarTurnoMinFinal");
                context.execute("editarTurnoMinFinal.show()");
                cualCeldaTurno = -1;
            }
            indexTurno = -1;
            secRegistro = null;
        }

        if (indexDetalle >= 0) {
            if (tipoListaDetalle == 0) {
                editarDetalle = listaDetallesTurnosRotativos.get(indexDetalle);
            } else {
                editarDetalle = filtrarListaDetallesTurnosRotativos.get(indexDetalle);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaDetalle == 0) {
                context.update("formularioDialogos:editarDetalleOrden");
                context.execute("editarDetalleOrden.show()");
                cualCeldaDetalle = -1;
            } else if (cualCeldaDetalle == 1) {
                context.update("formularioDialogos:editarDetalleCodigo");
                context.execute("editarDetalleCodigo.show()");
                cualCeldaDetalle = -1;
            } else if (cualCeldaDetalle == 2) {
                context.update("formularioDialogos:editarDetalleEmpleado");
                context.execute("editarDetalleEmpleado.show()");
                cualCeldaDetalle = -1;
            }
            indexDetalle = -1;
            secRegistro = null;
        }
    }

    public boolean validarCamposNullCuadrillas(int i) {
        boolean retorno = true;
        if (i == 0) {
            Cuadrillas aux = null;
            if (tipoListaCuadrilla == 0) {
                aux = listaCuadrillas.get(indexCuadrilla);
            } else {
                aux = filtrarListaCuadrillas.get(indexCuadrilla);
            }
            if (aux.getDescripcion() == null) {
                retorno = false;
            } else {
                if (aux.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (aux.getCodigo() <= 0) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaCuadrilla.getDescripcion() == null) {
                retorno = false;
            } else {
                if (nuevaCuadrilla.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (nuevaCuadrilla.getCodigo() <= 0) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarCuadrilla.getDescripcion() == null) {
                retorno = false;
            } else {
                if (duplicarCuadrilla.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (duplicarCuadrilla.getCodigo() <= 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposNullTurnos(int i) {
        boolean retorno = true;
        if (i == 0) {
            Turnosrotativos aux = null;
            if (tipoListaCuadrilla == 0) {
                aux = listaTurnosRotativos.get(indexTurno);
            } else {
                aux = filtrarListaTurnosRotativos.get(indexTurno);
            }
            if (aux.getDescripcion() == null) {
                retorno = false;
            } else {
                if (aux.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (aux.getCodigo() < 0 || aux.getCodigo() == 0 || aux.getHorafinal() < 0 || aux.getHorainicial() < 0
                    || aux.getMinutofinal() < 0 || aux.getMinutoinicial() < 0) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevaTurnoRotativo.getDescripcion() == null) {
                retorno = false;
            } else {
                if (nuevaTurnoRotativo.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (nuevaTurnoRotativo.getCodigo() <= 0 || nuevaTurnoRotativo.getHorafinal() < 0 || nuevaTurnoRotativo.getHorainicial() < 0
                    || nuevaTurnoRotativo.getMinutofinal() < 0 || nuevaTurnoRotativo.getMinutoinicial() < 0) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarTurnoRotativo.getDescripcion() == null) {
                retorno = false;
            } else {
                if (duplicarTurnoRotativo.getDescripcion().isEmpty()) {
                    retorno = false;
                }
            }
            if (duplicarTurnoRotativo.getCodigo() <= 0 || duplicarTurnoRotativo.getHorafinal() < 0 || duplicarTurnoRotativo.getHorainicial() < 0
                    || duplicarTurnoRotativo.getMinutofinal() < 0 || duplicarTurnoRotativo.getMinutoinicial() < 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposNullDetalles(int i) {
        boolean retorno = true;
        if (i == 0) {
            DetallesTurnosRotativos aux = null;
            if (tipoListaCuadrilla == 0) {
                aux = listaDetallesTurnosRotativos.get(indexDetalle);
            } else {
                aux = filtrarListaDetallesTurnosRotativos.get(indexDetalle);
            }
            if (aux.getOrden() <= 0) {
                retorno = false;
            }
            if (aux.getEmpleado().getCodigoempleado() == null) {
                retorno = false;
            }
            if (aux.getEmpleado().getPersona().getNombreCompleto() == null) {
                retorno = false;
            } else {
                if (aux.getEmpleado().getPersona().getNombreCompleto().isEmpty()) {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevaDetalle.getOrden() <= 0) {
                retorno = false;
            }
            if (nuevaDetalle.getEmpleado().getSecuencia() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarDetalle.getOrden() <= 0) {
                retorno = false;
            }
            if (duplicarDetalle.getEmpleado().getSecuencia() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void disparaDialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCuadrilla >= 0) {
            if (cambiosTurno == false) {
                int tam = 0;
                if (listaTurnosRotativos != null) {
                    tam = listaTurnosRotativos.size();
                }
                if (tam == 0) {
                    context.execute("NuevoRegistroPagina.show()");
                } else {
                    context.update("formularioDialogos:nuevaCuadrilla");
                    context.execute("NuevoRegistroCuadrilla.show()");
                }
            } else {
                context.execute("confirmarGuardar.show()");
            }
        }
        if (indexTurno >= 0) {
            if (cambiosDetalle == false) {
                int tam = 0;
                if (listaDetallesTurnosRotativos != null) {
                    tam = listaDetallesTurnosRotativos.size();
                }
                if (tam == 0) {
                    context.execute("NuevoRegistroPagina.show()");
                } else {
                    context.update("formularioDialogos:nuevaTurno");
                    context.execute("NuevoRegistroTurno.show()");
                }
            } else {
                context.execute("confirmarGuardar.show()");
            }
        }
        if (indexDetalle >= 0) {
            context.update("formularioDialogos:nuevaDetalle");
            context.execute("NuevoRegistroDetalle.show()");
        }
    }

    public void agregarNuevaCuadrilla() {
        boolean validarNull = validarCamposNullCuadrillas(1);
        if (validarNull == true) {
            if (banderaCuadrilla == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
                cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
                cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
                cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
                cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
                altoTablaCuadrilla = "70";
                RequestContext.getCurrentInstance().update("form:datosCuadrilla");
                banderaCuadrilla = 0;
                filtrarListaCuadrillas = null;
                tipoListaCuadrilla = 0;
            }

            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevaCuadrilla.setSecuencia(l);
            listCuadrillasCrear.add(nuevaCuadrilla);

            listaCuadrillas.add(nuevaCuadrilla);
            nuevaCuadrilla = new Cuadrillas();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuadrilla");
            context.execute("NuevoRegistroCuadrilla.hide()");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosCuadrilla = true;
            indexCuadrilla = -1;
            secRegistro = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorNullCuadrillas.show()");
        }
    }
//LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaCuadrilla() {
        nuevaCuadrilla = new Cuadrillas();
        indexCuadrilla = -1;
        secRegistro = null;
    }

    public void agregarNuevaTurno() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validarNull = validarCamposNullTurnos(1);
        if (validarNull == true) {
            if (validarFechasRegistroTurno(1) == true) {
                if (banderaTurno == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                    turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
                    turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                    turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                    turnoFecha.setFilterStyle("display: none; visibility: hidden;");
                    turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                    turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
                    turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                    turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                    turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                    turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
                    turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                    turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
                    altoTablaTurno = "80";
                    RequestContext.getCurrentInstance().update("form:datosTurno");
                    banderaTurno = 0;
                    filtrarListaTurnosRotativos = null;
                    tipoListaTurno = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevaTurnoRotativo.setSecuencia(l);
                if (tipoListaCuadrilla == 0) {
                    nuevaTurnoRotativo.setCuadrilla(listaCuadrillas.get(indexCuadrillaAux));
                } else {
                    nuevaTurnoRotativo.setCuadrilla(filtrarListaCuadrillas.get(indexCuadrillaAux));
                }
                listTurnosRotativosCrear.add(nuevaTurnoRotativo);
                listaTurnosRotativos.add(nuevaTurnoRotativo);
                nuevaTurnoRotativo = new Turnosrotativos();
                context.update("form:datosTurno");
                context.execute("NuevoRegistroTurno.hide()");
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                cambiosTurno = true;
                indexTurno = -1;
                secRegistro = null;
            } else {
                context.execute("errorFechaTurno.show()");
            }
        } else {
            context.execute("errorNullTurno.show()");
        }
    }
//LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaTurno() {
        nuevaTurnoRotativo = new Turnosrotativos();
        indexTurno = -1;
        secRegistro = null;
    }

    public void agregarNuevaDetalle() {
        boolean validarNull = validarCamposNullDetalles(1);
        if (validarNull == true) {
            if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevaDetalle.setSecuencia(l);
            if (tipoListaTurno == 0) {
                nuevaDetalle.setTurnorotativo(listaTurnosRotativos.get(indexTurnoAux));
            } else {
                nuevaDetalle.setTurnorotativo(filtrarListaTurnosRotativos.get(indexTurnoAux));
            }
            listDetallesTurnosRotativosCrear.add(nuevaDetalle);
            listaDetallesTurnosRotativos.add(nuevaDetalle);
            System.out.println("listaDetallesTurnosRotativos : " + listaDetallesTurnosRotativos.size());
            nuevaDetalle = new DetallesTurnosRotativos();
            nuevaDetalle.setEmpleado(new Empleados());
            nuevaDetalle.getEmpleado().setPersona(new Personas());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalle");
            context.execute("NuevoRegistroDetalle.hide()");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosDetalle = true;
            indexDetalle = -1;
            secRegistro = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorNullTurno.show()");
        }
    }
//LIMPIAR NUEVO REGISTRO

    public void limpiarNuevaDetalle() {
        nuevaDetalle = new DetallesTurnosRotativos();
        nuevaDetalle.setEmpleado(new Empleados());
        nuevaDetalle.getEmpleado().setPersona(new Personas());
        indexDetalle = -1;
        secRegistro = null;
    }

    public void disparaDialogoDuplicarRegistro() {
        if (indexCuadrilla >= 0) {
            duplicarCuadrillaM();
        }
        if (indexTurno >= 0) {
            duplicarTurnoM();
        }
        if (indexDetalle >= 0) {
            duplicarDetalleM();
        }
    }

    public void duplicarCuadrillaM() {
        duplicarCuadrilla = new Cuadrillas();
        if (tipoListaCuadrilla == 0) {
            duplicarCuadrilla.setCodigo(listaCuadrillas.get(indexCuadrilla).getCodigo());
            duplicarCuadrilla.setDescripcion(listaCuadrillas.get(indexCuadrilla).getDescripcion());
            duplicarCuadrilla.setEstado(listaCuadrillas.get(indexCuadrilla).getEstado());
            duplicarCuadrilla.setModulo(listaCuadrillas.get(indexCuadrilla).getModulo());
            duplicarCuadrilla.setMetodorotacion(listaCuadrillas.get(indexCuadrilla).getMetodorotacion());
            duplicarCuadrilla.setDiasciclo(listaCuadrillas.get(indexCuadrilla).getDiasciclo());
        } else {
            duplicarCuadrilla.setCodigo(filtrarListaCuadrillas.get(indexCuadrilla).getCodigo());
            duplicarCuadrilla.setDescripcion(filtrarListaCuadrillas.get(indexCuadrilla).getDescripcion());
            duplicarCuadrilla.setEstado(filtrarListaCuadrillas.get(indexCuadrilla).getEstado());
            duplicarCuadrilla.setModulo(filtrarListaCuadrillas.get(indexCuadrilla).getModulo());
            duplicarCuadrilla.setMetodorotacion(filtrarListaCuadrillas.get(indexCuadrilla).getMetodorotacion());
            duplicarCuadrilla.setDiasciclo(filtrarListaCuadrillas.get(indexCuadrilla).getDiasciclo());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicarCuadrilla");
        context.execute("DuplicarRegistroCuadrilla.show()");
        indexCuadrilla = -1;
        secRegistro = null;
    }

    public void duplicarTurnoM() {
        duplicarTurnoRotativo = new Turnosrotativos();
        if (tipoListaCuadrilla == 0) {
            duplicarTurnoRotativo.setCodigo(listaTurnosRotativos.get(indexTurno).getCodigo());
            duplicarTurnoRotativo.setDescripcion(listaTurnosRotativos.get(indexTurno).getDescripcion());
            duplicarTurnoRotativo.setFechasemilla(listaTurnosRotativos.get(indexTurno).getFechasemilla());
            duplicarTurnoRotativo.setHorafinal(listaTurnosRotativos.get(indexTurno).getHorafinal());
            duplicarTurnoRotativo.setHorainicial(listaTurnosRotativos.get(indexTurno).getHorainicial());
            duplicarTurnoRotativo.setMinutofinal(listaTurnosRotativos.get(indexTurno).getMinutofinal());
            duplicarTurnoRotativo.setMinutoinicial(listaTurnosRotativos.get(indexTurno).getMinutoinicial());
        } else {
            duplicarTurnoRotativo.setCodigo(filtrarListaTurnosRotativos.get(indexTurno).getCodigo());
            duplicarTurnoRotativo.setDescripcion(filtrarListaTurnosRotativos.get(indexTurno).getDescripcion());
            duplicarTurnoRotativo.setFechasemilla(filtrarListaTurnosRotativos.get(indexTurno).getFechasemilla());
            duplicarTurnoRotativo.setHorafinal(filtrarListaTurnosRotativos.get(indexTurno).getHorafinal());
            duplicarTurnoRotativo.setHorainicial(filtrarListaTurnosRotativos.get(indexTurno).getHorainicial());
            duplicarTurnoRotativo.setMinutofinal(filtrarListaTurnosRotativos.get(indexTurno).getMinutofinal());
            duplicarTurnoRotativo.setMinutoinicial(filtrarListaTurnosRotativos.get(indexTurno).getMinutoinicial());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicarTurno");
        context.execute("DuplicarRegistroTurno.show()");
        indexTurno = -1;
        secRegistro = null;
    }

    public void duplicarDetalleM() {
        if (tipoListaDetalle == 0) {
            duplicarDetalle.setOrden(listaDetallesTurnosRotativos.get(indexDetalle).getOrden());
            duplicarDetalle.setEmpleado(listaDetallesTurnosRotativos.get(indexDetalle).getEmpleado());
        } else {
            duplicarDetalle.setOrden(filtrarListaDetallesTurnosRotativos.get(indexDetalle).getOrden());
            duplicarDetalle.setEmpleado(filtrarListaDetallesTurnosRotativos.get(indexDetalle).getEmpleado());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicarDetalle");
        context.execute("DuplicarRegistroDetalle.show()");
        indexDetalle = -1;
        secRegistro = null;
    }

    public void confirmarDuplicarCuadrilla() {
        boolean validarNull = validarCamposNullCuadrillas(2);
        if (validarNull == true) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarCuadrilla.setSecuencia(l);
            listaCuadrillas.add(duplicarCuadrilla);
            listCuadrillasCrear.add(duplicarCuadrilla);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuadrilla");
            context.execute("DuplicarRegistroCuadrilla.hide()");
            indexCuadrilla = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (banderaCuadrilla == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
                cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
                cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
                cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
                cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
                altoTablaCuadrilla = "70";
                RequestContext.getCurrentInstance().update("form:datosCuadrilla");
                banderaCuadrilla = 0;
                filtrarListaCuadrillas = null;
                tipoListaCuadrilla = 0;
            }
            cambiosCuadrilla = true;
            duplicarCuadrilla = new Cuadrillas();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorNullCuadrillas.show()");
        }
    }
    //LIMPIAR DUPLICAR

    public void limpiarDuplicarCuadrilla() {
        duplicarCuadrilla = new Cuadrillas();
    }

    public void confirmarDuplicarTurno() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean validarNull = validarCamposNullTurnos(2);
        if (validarNull == true) {
            if (validarFechasRegistroTurno(2) == true) {
                k++;
                l = BigInteger.valueOf(k);
                duplicarTurnoRotativo.setSecuencia(l);
                if (tipoListaCuadrilla == 0) {
                    duplicarTurnoRotativo.setCuadrilla(listaCuadrillas.get(indexCuadrillaAux));
                } else {
                    duplicarTurnoRotativo.setCuadrilla(filtrarListaCuadrillas.get(indexCuadrillaAux));
                }
                listaTurnosRotativos.add(duplicarTurnoRotativo);
                listTurnosRotativosCrear.add(duplicarTurnoRotativo);
                context.update("form:datosTurno");
                context.execute("DuplicarRegistroTurno.hide()");
                indexTurno = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (banderaTurno == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                    turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
                    turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                    turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                    turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                    turnoFecha.setFilterStyle("display: none; visibility: hidden;");
                    turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                    turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
                    turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                    turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                    turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                    turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
                    turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                    turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
                    altoTablaTurno = "80";
                    RequestContext.getCurrentInstance().update("form:datosTurno");
                    banderaTurno = 0;
                    filtrarListaTurnosRotativos = null;
                    tipoListaTurno = 0;
                }
                cambiosTurno = true;
                duplicarTurnoRotativo = new Turnosrotativos();
            } else {
                context.execute("errorFechaTurno.show()");
            }
        } else {
            context.execute("errorNullTurno.show()");
        }
    }
    //LIMPIAR DUPLICAR

    public void limpiarDuplicarTurno() {
        duplicarTurnoRotativo = new Turnosrotativos();
    }

    public void confirmarDuplicarDetalle() {
        boolean validarNull = validarCamposNullDetalles(2);
        if (validarNull == true) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarDetalle.setSecuencia(l);
            if (tipoListaTurno == 0) {
                duplicarDetalle.setTurnorotativo(listaTurnosRotativos.get(indexTurnoAux));
            } else {
                duplicarDetalle.setTurnorotativo(filtrarListaTurnosRotativos.get(indexTurnoAux));
            }
            listaDetallesTurnosRotativos.add(duplicarDetalle);
            listDetallesTurnosRotativosCrear.add(duplicarDetalle);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalle");
            context.execute("DuplicarRegistroDetalle.hide()");
            indexDetalle = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }
            cambiosDetalle = true;
            duplicarDetalle = new DetallesTurnosRotativos();
            duplicarDetalle.setEmpleado(new Empleados());
            duplicarDetalle.getEmpleado().setPersona(new Personas());
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorNullDetalle.show()");
        }
    }
    //LIMPIAR DUPLICAR

    public void limpiarDuplicarDetalle() {
        duplicarDetalle = new DetallesTurnosRotativos();
        duplicarDetalle.setEmpleado(new Empleados());
        duplicarDetalle.getEmpleado().setPersona(new Personas());
    }

    public void valoresBackupAutocompletarDetalle(int tipoNuevo, String Campo) {
        if (Campo.equals("CODIGO")) {
            if (tipoNuevo == 1) {
                auxDetalleCodigo = nuevaDetalle.getEmpleado().getCodigoempleado();
            } else if (tipoNuevo == 2) {
                auxDetalleCodigo = duplicarDetalle.getEmpleado().getCodigoempleado();
            }
        }
        if (Campo.equals("NOMBRE")) {
            if (tipoNuevo == 1) {
                auxDetalleEmpleado = nuevaDetalle.getEmpleado().getPersona().getNombreCompleto();
            } else if (tipoNuevo == 2) {
                auxDetalleEmpleado = duplicarDetalle.getEmpleado().getPersona().getNombreCompleto();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoDetalle(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGO")) {
            if (tipoNuevo == 1) {
                nuevaDetalle.getEmpleado().setCodigoempleado(auxDetalleCodigo);
            } else if (tipoNuevo == 2) {
                duplicarDetalle.getEmpleado().setCodigoempleado(auxDetalleCodigo);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getCodigoempleadoSTR().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalle.setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoDetalle");
                    context.update("formularioDialogos:nuevaEmpleadoDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalle.setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoDetalle");
                    context.update("formularioDialogos:duplicarEmpleadoDetalle");
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                context.update("formEmpleado:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCodigoDetalle");
                    context.update("formularioDialogos:nuevaEmpleadoDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoDetalle");
                    context.update("formularioDialogos:duplicarEmpleadoDetalle");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("NOMBRE")) {
            if (tipoNuevo == 1) {
                nuevaDetalle.getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            } else if (tipoNuevo == 2) {
                duplicarDetalle.getEmpleado().getPersona().setNombreCompleto(auxDetalleEmpleado);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaDetalle.setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoDetalle");
                    context.update("formularioDialogos:nuevaEmpleadoDetalle");
                } else if (tipoNuevo == 2) {
                    duplicarDetalle.setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoDetalle");
                    context.update("formularioDialogos:duplicarEmpleadoDetalle");
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                context.update("formEmpleado:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCodigoDetalle");
                    context.update("formularioDialogos:nuevaEmpleadoDetalle");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoDetalle");
                    context.update("formularioDialogos:duplicarEmpleadoDetalle");
                }
            }
        }
    }

    public void validarBorrarRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexCuadrilla >= 0) {
            int tam = 0;
            if (listaTurnosRotativos != null) {
                tam = listaTurnosRotativos.size();
            }
            if (tam == 0) {
                borrarCuadrilla();
            } else {
                context.execute("errorBorrarCuadrilla.show()");
            }
        }
        if (indexTurno >= 0) {
            int tam = 0;
            if (listaDetallesTurnosRotativos != null) {
                tam = listaDetallesTurnosRotativos.size();
            }
            if (tam == 0) {
                borrarTurno();
            } else {
                context.execute("errorBorrarTurno.show()");
            }
        }
        if (indexDetalle >= 0) {
            borrarDetalle();
        }
    }

    public void borrarCuadrilla() {
        if (indexCuadrilla >= 0) {
            if (tipoListaCuadrilla == 0) {
                if (!listCuadrillasModificar.isEmpty() && listCuadrillasModificar.contains(listaCuadrillas.get(indexCuadrilla))) {
                    int modIndex = listCuadrillasModificar.indexOf(listaCuadrillas.get(indexCuadrilla));
                    listCuadrillasModificar.remove(modIndex);
                    listCuadrillasBorrar.add(listaCuadrillas.get(indexCuadrilla));
                } else if (!listCuadrillasCrear.isEmpty() && listCuadrillasCrear.contains(listaCuadrillas.get(indexCuadrilla))) {
                    int crearIndex = listCuadrillasCrear.indexOf(listaCuadrillas.get(indexCuadrilla));
                    listCuadrillasCrear.remove(crearIndex);
                } else {
                    listCuadrillasBorrar.add(listaCuadrillas.get(indexCuadrilla));
                }
                listaCuadrillas.remove(indexCuadrilla);
            } else {
                if (!listCuadrillasModificar.isEmpty() && listCuadrillasModificar.contains(filtrarListaCuadrillas.get(indexCuadrilla))) {
                    int modIndex = listCuadrillasModificar.indexOf(filtrarListaCuadrillas.get(indexCuadrilla));
                    listCuadrillasModificar.remove(modIndex);
                    listCuadrillasBorrar.add(filtrarListaCuadrillas.get(indexCuadrilla));
                } else if (!listCuadrillasCrear.isEmpty() && listCuadrillasCrear.contains(filtrarListaCuadrillas.get(indexCuadrilla))) {
                    int crearIndex = listCuadrillasCrear.indexOf(filtrarListaCuadrillas.get(indexCuadrilla));
                    listCuadrillasCrear.remove(crearIndex);
                } else {
                    listCuadrillasBorrar.add(filtrarListaCuadrillas.get(indexCuadrilla));
                }
                int VCIndex = listaCuadrillas.indexOf(filtrarListaCuadrillas.get(indexCuadrilla));
                listaCuadrillas.remove(VCIndex);
                filtrarListaCuadrillas.remove(indexCuadrilla);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuadrilla");

            indexCuadrilla = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosCuadrilla = true;
        }
    }

    public void borrarTurno() {
        if (indexTurno >= 0) {
            if (tipoListaTurno == 0) {
                if (!listTurnosRotativosModificar.isEmpty() && listTurnosRotativosModificar.contains(listaTurnosRotativos.get(indexTurno))) {
                    int modIndex = listTurnosRotativosModificar.indexOf(listaTurnosRotativos.get(indexTurno));
                    listTurnosRotativosModificar.remove(modIndex);
                    listTurnosRotativosBorrar.add(listaTurnosRotativos.get(indexTurno));
                } else if (!listTurnosRotativosCrear.isEmpty() && listTurnosRotativosCrear.contains(listaTurnosRotativos.get(indexTurno))) {
                    int crearIndex = listTurnosRotativosCrear.indexOf(listaTurnosRotativos.get(indexTurno));
                    listTurnosRotativosCrear.remove(crearIndex);
                } else {
                    listTurnosRotativosBorrar.add(listaTurnosRotativos.get(indexTurno));
                }
                listaTurnosRotativos.remove(indexTurno);
            } else {
                if (!listTurnosRotativosModificar.isEmpty() && listTurnosRotativosModificar.contains(filtrarListaTurnosRotativos.get(indexTurno))) {
                    int modIndex = listTurnosRotativosModificar.indexOf(filtrarListaTurnosRotativos.get(indexTurno));
                    listTurnosRotativosModificar.remove(modIndex);
                    listTurnosRotativosBorrar.add(filtrarListaTurnosRotativos.get(indexTurno));
                } else if (!listTurnosRotativosCrear.isEmpty() && listTurnosRotativosCrear.contains(filtrarListaTurnosRotativos.get(indexTurno))) {
                    int crearIndex = listTurnosRotativosCrear.indexOf(filtrarListaTurnosRotativos.get(indexTurno));
                    listTurnosRotativosCrear.remove(crearIndex);
                } else {
                    listTurnosRotativosBorrar.add(filtrarListaTurnosRotativos.get(indexTurno));
                }
                int VCIndex = listaTurnosRotativos.indexOf(filtrarListaTurnosRotativos.get(indexTurno));
                listaTurnosRotativos.remove(VCIndex);
                filtrarListaTurnosRotativos.remove(indexTurno);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTurno");

            indexTurno = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosTurno = true;
        }
    }

    public void borrarDetalle() {
        if (indexDetalle >= 0) {
            if (tipoListaDetalle == 0) {
                if (!listDetallesTurnosRotativosModificar.isEmpty() && listDetallesTurnosRotativosModificar.contains(listaDetallesTurnosRotativos.get(indexDetalle))) {
                    int modIndex = listDetallesTurnosRotativosModificar.indexOf(listaDetallesTurnosRotativos.get(indexDetalle));
                    listDetallesTurnosRotativosModificar.remove(modIndex);
                    listDetallesTurnosRotativosBorrar.add(listaDetallesTurnosRotativos.get(indexDetalle));
                } else if (!listDetallesTurnosRotativosCrear.isEmpty() && listDetallesTurnosRotativosCrear.contains(listaDetallesTurnosRotativos.get(indexDetalle))) {
                    int crearIndex = listDetallesTurnosRotativosCrear.indexOf(listaDetallesTurnosRotativos.get(indexDetalle));
                    listDetallesTurnosRotativosCrear.remove(crearIndex);
                } else {
                    listDetallesTurnosRotativosBorrar.add(listaDetallesTurnosRotativos.get(indexDetalle));
                }
                listaDetallesTurnosRotativos.remove(indexDetalle);
            } else {
                if (!listDetallesTurnosRotativosModificar.isEmpty() && listDetallesTurnosRotativosModificar.contains(filtrarListaDetallesTurnosRotativos.get(indexDetalle))) {
                    int modIndex = listDetallesTurnosRotativosModificar.indexOf(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                    listDetallesTurnosRotativosModificar.remove(modIndex);
                    listDetallesTurnosRotativosBorrar.add(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                } else if (!listDetallesTurnosRotativosCrear.isEmpty() && listDetallesTurnosRotativosCrear.contains(filtrarListaDetallesTurnosRotativos.get(indexDetalle))) {
                    int crearIndex = listDetallesTurnosRotativosCrear.indexOf(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                    listDetallesTurnosRotativosCrear.remove(crearIndex);
                } else {
                    listDetallesTurnosRotativosBorrar.add(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                }
                int VCIndex = listaDetallesTurnosRotativos.indexOf(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                listaDetallesTurnosRotativos.remove(VCIndex);
                filtrarListaDetallesTurnosRotativos.remove(indexDetalle);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalle");

            indexDetalle = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosDetalle = true;
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (indexCuadrilla >= 0) {
            if (banderaCuadrilla == 0) {
                cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
                cuadrillaCodigo.setFilterStyle("width: 60px");
                cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
                cuadrillaDescripcion.setFilterStyle("width: 60px");
                cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
                cuadrillaDias.setFilterStyle("width: 60px");
                cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
                cuadrillaModulo.setFilterStyle("width: 60px");
                altoTablaCuadrilla = "48";
                RequestContext.getCurrentInstance().update("form:datosCuadrilla");
                banderaCuadrilla = 1;
            } else if (banderaCuadrilla == 1) {
                //CERRAR FILTRADO
                cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
                cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
                cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
                cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
                cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
                altoTablaCuadrilla = "70";
                RequestContext.getCurrentInstance().update("form:datosCuadrilla");
                banderaCuadrilla = 0;
                filtrarListaCuadrillas = null;
                tipoListaCuadrilla = 0;
            }
        }
        if (indexTurno >= 0) {
            if (banderaTurno == 0) {
                turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                turnoCodigo.setFilterStyle("width: 60px");
                turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                turnoDescripcion.setFilterStyle("width: 60px");
                turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                turnoFecha.setFilterStyle("width: 60px");
                turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                turnoHoraInicial.setFilterStyle("width: 60px");
                turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                turnoHoraFinal.setFilterStyle("width: 60px");
                turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                turnoMinInicial.setFilterStyle("width: 60px");
                turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                turnoMinFinal.setFilterStyle("width: 60px");
                altoTablaTurno = "58";
                RequestContext.getCurrentInstance().update("form:datosTurno");
                banderaTurno = 1;
            } else if (banderaTurno == 1) {
                //CERRAR FILTRADO
                turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
                turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                turnoFecha.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
                altoTablaTurno = "80";
                RequestContext.getCurrentInstance().update("form:datosTurno");
                banderaTurno = 0;
                filtrarListaTurnosRotativos = null;
                tipoListaTurno = 0;
            }
        }
        if (indexDetalle >= 0) {
            if (banderaDetalle == 0) {
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("width: 60px");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("width: 60px");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("width: 60px");
                altoTablaDetalle = "58";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 1;
            } else if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }
        }
    }

    //SALIR
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaCuadrilla == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
            cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
            cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
            cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
            cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
            cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
            altoTablaCuadrilla = "70";
            RequestContext.getCurrentInstance().update("form:datosCuadrilla");
            banderaCuadrilla = 0;
            filtrarListaCuadrillas = null;
            tipoListaCuadrilla = 0;
        }
        if (banderaTurno == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
            turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
            turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
            turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
            turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
            turnoFecha.setFilterStyle("display: none; visibility: hidden;");
            turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
            turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
            turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
            turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
            turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
            turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
            turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
            turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTablaTurno = "80";
            RequestContext.getCurrentInstance().update("form:datosTurno");
            banderaTurno = 0;
            filtrarListaTurnosRotativos = null;
            tipoListaTurno = 0;
        }

        if (banderaDetalle == 1) {
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
            detalleOrden.setFilterStyle("display: none; visibility: hidden;");
            detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
            detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
            detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
            detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
            altoTablaDetalle = "80";
            RequestContext.getCurrentInstance().update("form:datosDetalle");
            banderaDetalle = 0;
            filtrarListaDetallesTurnosRotativos = null;
            tipoListaDetalle = 0;
        }

        listCuadrillasBorrar.clear();
        listCuadrillasCrear.clear();
        listCuadrillasModificar.clear();

        listTurnosRotativosBorrar.clear();
        listTurnosRotativosCrear.clear();
        listTurnosRotativosModificar.clear();

        listDetallesTurnosRotativosBorrar.clear();
        listDetallesTurnosRotativosCrear.clear();
        listDetallesTurnosRotativosModificar.clear();

        indexCuadrilla = -1;
        indexCuadrillaAux = -1;
        indexTurno = -1;
        indexTurnoAux = -1;
        indexDetalle = -1;
        secRegistro = null;
        k = 0;
        listaCuadrillas = null;
        listaTurnosRotativos = null;
        listaDetallesTurnosRotativos = null;
        guardado = true;
        context.update("form:ACEPTAR");
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

    public void asignarIndex(Integer indice, int LND) {
        indexCuadrilla = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        context.update("formEmpleado:EmpleadoDialogo");
        context.execute("EmpleadoDialogo.show()");
    }

    public void actualizarEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaDetalle == 0) {
                listaDetallesTurnosRotativos.get(indexDetalle).setEmpleado(empleadoSeleccionado);
                if (!listDetallesTurnosRotativosCrear.contains(listaDetallesTurnosRotativos.get(indexDetalle))) {
                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indexDetalle));
                    } else if (!listDetallesTurnosRotativosModificar.contains(listaDetallesTurnosRotativos.get(indexDetalle))) {
                        listDetallesTurnosRotativosModificar.add(listaDetallesTurnosRotativos.get(indexDetalle));
                    }
                }
            } else {
                filtrarListaDetallesTurnosRotativos.get(indexDetalle).setEmpleado(empleadoSeleccionado);
                if (!listDetallesTurnosRotativosCrear.contains(filtrarListaDetallesTurnosRotativos.get(indexDetalle))) {
                    if (listDetallesTurnosRotativosModificar.isEmpty()) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                    } else if (!listDetallesTurnosRotativosModificar.contains(filtrarListaDetallesTurnosRotativos.get(indexDetalle))) {
                        listDetallesTurnosRotativosModificar.add(filtrarListaDetallesTurnosRotativos.get(indexDetalle));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambiosDetalle = true;
            context.update("form:datosDetalle");
            permitirIndexDetalle = true;
        } else if (tipoActualizacion == 1) {
            nuevaDetalle.setEmpleado(empleadoSeleccionado);
            context.update("formularioDialogos:nuevaCodigoDetalle");
            context.update("formularioDialogos:nuevaEmpleadoDetalle");
        } else if (tipoActualizacion == 2) {
            duplicarDetalle.setEmpleado(empleadoSeleccionado);
            context.update("formularioDialogos:duplicarCodigoDetalle");
            context.update("formularioDialogos:duplicarEmpleadoDetalle");
        }
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        indexDetalle = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        /*
         context.update("formEmpleado:EmpleadoDialogo");
         context.update("formEmpleado:lovEmpleado");
         context.update("formEmpleado:aceptarE");*/
        context.reset("formEmpleado:lovEmpleado:globalFilter");
        context.execute("lovEmpleado.clearFilters()");
        context.execute("EmpleadoDialogo.hide()");
    }

    public void cancelarCambioEmpleado() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = null;
        aceptar = true;
        indexDetalle = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexDetalle = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formEmpleado:lovEmpleado:globalFilter");
        context.execute("lovEmpleado.clearFilters()");
        context.execute("EmpleadoDialogo.hide()");
    }

    public void listaValoresBoton() {
        if (indexCuadrilla >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaCuadrilla == 1) {
                context.update("formEmpleado:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void borrarProgramacionCompleta() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == true) {
                administrarATCuadrilla.borrarProgramacionCompleta();
                cancelarModificacion();
                FacesMessage msg = new FacesMessage("Información", "Se realizo con exito el borrado de la programacion");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            } else {
                RequestContext.getCurrentInstance().execute("confirmarGuardar.show()");
            }
        } catch (Exception e) {
            System.out.println("Error borrarProgramacionCompleta Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en el borrado de la programacion, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void mostrarTodos() {
        if (cambiosDetalle == false) {
            cancelarModificacion();
            activarBtnBuscar = false;
            activarBtnMostrar = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:BUSCAR");
            context.update("form:TODOS");
        } else {
            RequestContext.getCurrentInstance().execute("confirmarGuardar.show()");
        }
    }

    public void actionBtnBuscarEmpleados() {
        try {
            if (guardado == true) {
                filtrarLovEmpleadosPorCuadrillas = null;
                empleadoCuadrillasSeleccionado = null;
                RequestContext context = RequestContext.getCurrentInstance();
                lovEmpleadosPorCuadrillas = administrarATCuadrilla.consultarEmpleadosProcesoBuscarEmpleadosCuadrillas();
                getInfoRegistroEmpleadoCuadrilla();
                context.update("formEmpleado:EmpleadoCuadrillaDialogo");
                context.execute("EmpleadoCuadrillaDialogo.show()");
            } else {
                RequestContext.getCurrentInstance().execute("confirmarGuardar.show()");
            }
        } catch (Exception e) {
            System.out.println("Error actionBtnBuscarEmpleados Controlador : " + e.toString());
        }
    }

    public void actualizarEmpleadosCuadrillas() {
        try {
            if (banderaCuadrilla == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                cuadrillaCodigo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaCodigo");
                cuadrillaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDescripcion = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDescripcion");
                cuadrillaDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaDias = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaDias");
                cuadrillaDias.setFilterStyle("display: none; visibility: hidden;");
                cuadrillaModulo = (Column) c.getViewRoot().findComponent("form:datosCuadrilla:cuadrillaModulo");
                cuadrillaModulo.setFilterStyle("display: none; visibility: hidden;");
                altoTablaCuadrilla = "70";
                RequestContext.getCurrentInstance().update("form:datosCuadrilla");
                banderaCuadrilla = 0;
                filtrarListaCuadrillas = null;
                tipoListaCuadrilla = 0;
            }
            if (banderaTurno == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                turnoCodigo = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoCodigo");
                turnoCodigo.setFilterStyle("display: none; visibility: hidden;");
                turnoDescripcion = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoDescripcion");
                turnoDescripcion.setFilterStyle("display: none; visibility: hidden;");
                turnoFecha = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoFecha");
                turnoFecha.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraInicial");
                turnoHoraInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoHoraFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoHoraFinal");
                turnoHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                turnoMinInicial = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinInicial");
                turnoMinInicial.setFilterStyle("display: none; visibility: hidden;");
                turnoMinFinal = (Column) c.getViewRoot().findComponent("form:datosTurno:turnoMinFinal");
                turnoMinFinal.setFilterStyle("display: none; visibility: hidden;");
                altoTablaTurno = "80";
                RequestContext.getCurrentInstance().update("form:datosTurno");
                banderaTurno = 0;
                filtrarListaTurnosRotativos = null;
                tipoListaTurno = 0;
            }

            if (banderaDetalle == 1) {
                //CERRAR FILTRADO
                FacesContext c = FacesContext.getCurrentInstance();
                detalleOrden = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleOrden");
                detalleOrden.setFilterStyle("display: none; visibility: hidden;");
                detalleCodigo = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleCodigo");
                detalleCodigo.setFilterStyle("display: none; visibility: hidden;");
                detalleEmpleado = (Column) c.getViewRoot().findComponent("form:datosDetalle:detalleEmpleado");
                detalleEmpleado.setFilterStyle("display: none; visibility: hidden;");
                altoTablaDetalle = "80";
                RequestContext.getCurrentInstance().update("form:datosDetalle");
                banderaDetalle = 0;
                filtrarListaDetallesTurnosRotativos = null;
                tipoListaDetalle = 0;
            }

            listCuadrillasBorrar.clear();
            listCuadrillasCrear.clear();
            listCuadrillasModificar.clear();

            listTurnosRotativosCrear.clear();
            listTurnosRotativosModificar.clear();
            listTurnosRotativosBorrar.clear();

            listDetallesTurnosRotativosCrear.clear();
            listDetallesTurnosRotativosModificar.clear();
            listDetallesTurnosRotativosBorrar.clear();

            indexCuadrilla = -1;
            indexCuadrillaAux = -1;
            indexTurno = -1;
            indexTurnoAux = -1;
            indexDetalle = -1;
            secRegistro = null;
            k = 0;
            listaCuadrillas = null;
            listaCuadrillas = administrarATCuadrilla.obtenerInfoCuadrillasPorEmpleado(empleadoCuadrillasSeleccionado.getSecuencia());
            listaTurnosRotativos = null;
            listaDetallesTurnosRotativos = null;
            guardado = true;
            cambiosCuadrilla = false;
            cambiosTurno = false;
            cambiosDetalle = false;
            permitirIndexDetalle = true;

            empleadoCuadrillasSeleccionado = null;
            filtrarLovEmpleadosPorCuadrillas = null;

            RequestContext context = RequestContext.getCurrentInstance();
            /*
             context.update("formEmpleado:EmpleadoCuadrillaDialogo");
             context.update("formEmpleado:lovEmpleadoCuadrilla");
             context.update("formEmpleado:aceptarEC");*/
            context.reset("formEmpleado:lovEmpleadoCuadrilla:globalFilter");
            context.execute("lovEmpleadoCuadrilla.clearFilters()");
            context.execute("EmpleadoCuadrillaDialogo.hide()");

            context.update("form:datosCuadrilla");
            context.update("form:datosTurno");
            context.update("form:datosDetalle");
            context.update("form:ACEPTAR");

            activarBtnBuscar = true;
            activarBtnMostrar = false;
            context.update("form:BUSCAR");
            context.update("form:TODOS");
        } catch (Exception e) {
            System.out.println("Error actualizarEmpleadosCuadrillas Controlador : " + e.toString());
        }
    }

    public void cancelarActualizarEmpleadosCuadrillas() {
        empleadoCuadrillasSeleccionado = null;
        filtrarLovEmpleadosPorCuadrillas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formEmpleado:lovEmpleadoCuadrilla:globalFilter");
        context.execute("lovEmpleadoCuadrilla.clearFilters()");
        context.execute("EmpleadoCuadrillaDialogo.hide()");
    }

    //EXPORTAR
    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        if (indexCuadrilla >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosCuadrillaExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "Cuadrillas_PDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexCuadrilla = -1;
            secRegistro = null;
        }
        if (indexTurno >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosTurnoExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "TurnosRotativos_PDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexTurno = -1;
            secRegistro = null;
        }
        if (indexDetalle >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosDetalleExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "DetallesTurnosRotativos_PDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexDetalle = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        if (indexCuadrilla >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosCuadrillaExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "Cuadrillas_XLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexCuadrilla = -1;
            secRegistro = null;
        }
        if (indexTurno >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosTurnoExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "TurnosRotativos_XLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexTurno = -1;
            secRegistro = null;
        }
        if (indexDetalle >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosDetalleExportar");
            FacesContext context = c;
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "DetallesTurnosRotativos_XLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexDetalle = -1;
            secRegistro = null;
        }
    }

    public String obtenerTablaXML() {
        if (indexCuadrilla >= 0) {
            nombreTablaXML = ":formExportar:datosCuadrillaExportar";
        }
        if (indexTurno >= 0) {
            nombreTablaXML = ":formExportar:datosTurnoExportar";
        }
        if (indexDetalle >= 0) {
            nombreTablaXML = ":formExportar:datosDetalleExportar";
        }
        return nombreTablaXML;
    }

    public String obtenerNombreArchivoXML() {
        if (indexCuadrilla >= 0) {
            nombreArchivoXML = "Cuadrillas_XML";
        }
        if (indexCuadrilla >= 0) {
            nombreArchivoXML = "TurnosRotativos_XML";
        }
        if (indexDetalle >= 0) {
            nombreArchivoXML = "DetallesTurnosRotativos_XML";
        }
        return nombreArchivoXML;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (indexCuadrilla >= 0) {
            if (tipoListaCuadrilla == 0) {
                tipoListaCuadrilla = 1;
            }
        }
        if (indexTurno >= 0) {
            if (tipoListaTurno == 0) {
                tipoListaTurno = 1;
            }
        }
        if (indexDetalle >= 0) {
            if (tipoListaDetalle == 0) {
                tipoListaDetalle = 1;
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void validarRastro() {
        if (indexCuadrilla >= 0) {
            verificarRastroCuadrilla();
        }
        if (indexTurno >= 0) {
            verificarRastroTurno();
        }
        if (indexDetalle >= 0) {
            verificarRastroDetalle();
        }
    }

    public void verificarRastroCuadrilla() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCuadrillas != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CUADRILLAS");
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
            if (administrarRastros.verificarHistoricosTabla("CUADRILLAS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexCuadrilla = -1;
    }

    public void verificarRastroTurno() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaTurnosRotativos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TURNOSROTATIVOS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB_T.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastro_T.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro_T.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("TURNOSROTATIVOS")) {
                context.execute("confirmarRastroHistorico_T.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexTurno = -1;
    }

    public void verificarRastroDetalle() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaDetallesTurnosRotativos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "DETALLESTURNOSROTATIVOS");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB_D.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastro_D.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro_D.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("DETALLESTURNOSROTATIVOS")) {
                context.execute("confirmarRastroHistorico_D.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexDetalle = -1;
    }

    public void guardarSalir() {
        guardadoGeneral();
        salir();
    }

    public void cancelarSalir() {
        cancelarModificacion();
        salir();
    }
    //GETTERS AND SETTERS

    public List<Cuadrillas> getListaCuadrillas() {
        try {
            if (listaCuadrillas == null) {
                return listaCuadrillas = administrarATCuadrilla.obtenerCuadrillas();
            } else {
                return listaCuadrillas;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getVigenciasReformasLaboralesEmpleado ");
            return null;
        }
    }

    public void setListaCuadrillas(List<Cuadrillas> vigenciasReformasLaborales) {
        this.listaCuadrillas = vigenciasReformasLaborales;
    }

    public List<Cuadrillas> getFiltrarListaCuadrillas() {
        return filtrarListaCuadrillas;
    }

    public void setFiltrarListaCuadrillas(List<Cuadrillas> filtrarVRL) {
        this.filtrarListaCuadrillas = filtrarVRL;
    }

    public Cuadrillas getNuevaCuadrilla() {
        return nuevaCuadrilla;
    }

    public void setNuevaCuadrilla(Cuadrillas nuevaVigencia) {
        this.nuevaCuadrilla = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Cuadrillas getEditarCuadrilla() {
        return editarCuadrilla;
    }

    public void setEditarCuadrilla(Cuadrillas editarVRL) {
        this.editarCuadrilla = editarVRL;
    }

    public Cuadrillas getDuplicarCuadrilla() {
        return duplicarCuadrilla;
    }

    public void setDuplicarCuadrilla(Cuadrillas duplicarCuadrilla) {
        this.duplicarCuadrilla = duplicarCuadrilla;
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

    public boolean isGuardado() {
        return guardado;
    }

    public String getAltoTablaCuadrilla() {
        return altoTablaCuadrilla;
    }

    public Cuadrillas getCuadrillaSeleccionada() {
        return cuadrillaSeleccionada;
    }

    public void setCuadrillaSeleccionada(Cuadrillas vigenciaSeleccionada) {
        this.cuadrillaSeleccionada = vigenciaSeleccionada;
    }

    public String getNombreTablaXML() {
        return nombreTablaXML;
    }

    public void setNombreTablaXML(String nombreTablaXML) {
        this.nombreTablaXML = nombreTablaXML;
    }

    public String getNombreArchivoXML() {
        return nombreArchivoXML;
    }

    public void setNombreArchivoXML(String nombreArchivoXML) {
        this.nombreArchivoXML = nombreArchivoXML;
    }

    public List<Turnosrotativos> getListaTurnosRotativos() {
        return listaTurnosRotativos;
    }

    public void setListaTurnosRotativos(List<Turnosrotativos> listaTurnosRotativos) {
        this.listaTurnosRotativos = listaTurnosRotativos;
    }

    public List<Turnosrotativos> getFiltrarListaTurnosRotativos() {
        return filtrarListaTurnosRotativos;
    }

    public void setFiltrarListaTurnosRotativos(List<Turnosrotativos> filtrarListaTurnosRotativos) {
        this.filtrarListaTurnosRotativos = filtrarListaTurnosRotativos;
    }

    public Turnosrotativos getTurnoSeleccionado() {
        return turnoSeleccionado;
    }

    public void setTurnoSeleccionado(Turnosrotativos turnoSeleccionado) {
        this.turnoSeleccionado = turnoSeleccionado;
    }

    public Turnosrotativos getNuevaTurnoRotativo() {
        return nuevaTurnoRotativo;
    }

    public void setNuevaTurnoRotativo(Turnosrotativos nuevaTurnoRotativo) {
        this.nuevaTurnoRotativo = nuevaTurnoRotativo;
    }

    public Turnosrotativos getDuplicarTurnoRotativo() {
        return duplicarTurnoRotativo;
    }

    public void setDuplicarTurnoRotativo(Turnosrotativos duplicarTurnoRotativo) {
        this.duplicarTurnoRotativo = duplicarTurnoRotativo;
    }

    public Turnosrotativos getEditarTurnoRotativo() {
        return editarTurnoRotativo;
    }

    public void setEditarTurnoRotativo(Turnosrotativos editarTurnoRotativo) {
        this.editarTurnoRotativo = editarTurnoRotativo;
    }

    public String getAltoTablaTurno() {
        return altoTablaTurno;
    }

    public void setAltoTablaTurno(String altoTablaTurno) {
        this.altoTablaTurno = altoTablaTurno;
    }

    public List<DetallesTurnosRotativos> getListaDetallesTurnosRotativos() {
        return listaDetallesTurnosRotativos;
    }

    public void setListaDetallesTurnosRotativos(List<DetallesTurnosRotativos> listaDetallesTurnosRotativos) {
        this.listaDetallesTurnosRotativos = listaDetallesTurnosRotativos;
    }

    public List<DetallesTurnosRotativos> getFiltrarListaDetallesTurnosRotativos() {
        return filtrarListaDetallesTurnosRotativos;
    }

    public void setFiltrarListaDetallesTurnosRotativos(List<DetallesTurnosRotativos> filtrarListaDetallesTurnosRotativos) {
        this.filtrarListaDetallesTurnosRotativos = filtrarListaDetallesTurnosRotativos;
    }

    public DetallesTurnosRotativos getDetalleSeleccionado() {
        return detalleSeleccionado;
    }

    public void setDetalleSeleccionado(DetallesTurnosRotativos detalleSeleccionado) {
        this.detalleSeleccionado = detalleSeleccionado;
    }

    public List<Empleados> getLovEmpleados() {
        lovEmpleados = administrarATCuadrilla.lovEmpleados();
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

    public String getInfoRegistroEmpleado() {
        getLovEmpleados();
        if (lovEmpleados != null) {
            infoRegistroEmpleado = "Cantidad de registros : " + lovEmpleados.size();
        } else {
            infoRegistroEmpleado = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleado;
    }

    public void setInfoRegistroEmpleado(String infoRegistroEmpleado) {
        this.infoRegistroEmpleado = infoRegistroEmpleado;
    }

    public DetallesTurnosRotativos getNuevaDetalle() {
        return nuevaDetalle;
    }

    public void setNuevaDetalle(DetallesTurnosRotativos nuevaDetalle) {
        this.nuevaDetalle = nuevaDetalle;
    }

    public DetallesTurnosRotativos getDuplicarDetalle() {
        return duplicarDetalle;
    }

    public void setDuplicarDetalle(DetallesTurnosRotativos duplicarDetalle) {
        this.duplicarDetalle = duplicarDetalle;
    }

    public DetallesTurnosRotativos getEditarDetalle() {
        return editarDetalle;
    }

    public void setEditarDetalle(DetallesTurnosRotativos editarDetalle) {
        this.editarDetalle = editarDetalle;
    }

    public String getAltoTablaDetalle() {
        return altoTablaDetalle;
    }

    public void setAltoTablaDetalle(String altoTablaDetalle) {
        this.altoTablaDetalle = altoTablaDetalle;
    }

    public List<Empleados> getLovEmpleadosPorCuadrillas() {
        return lovEmpleadosPorCuadrillas;
    }

    public void setLovEmpleadosPorCuadrillas(List<Empleados> lovEmpleadosPorCuadrillas) {
        this.lovEmpleadosPorCuadrillas = lovEmpleadosPorCuadrillas;
    }

    public List<Empleados> getFiltrarLovEmpleadosPorCuadrillas() {
        return filtrarLovEmpleadosPorCuadrillas;
    }

    public void setFiltrarLovEmpleadosPorCuadrillas(List<Empleados> filtrarLovEmpleadosPorCuadrillas) {
        this.filtrarLovEmpleadosPorCuadrillas = filtrarLovEmpleadosPorCuadrillas;
    }

    public Empleados getEmpleadoCuadrillasSeleccionado() {
        return empleadoCuadrillasSeleccionado;
    }

    public void setEmpleadoCuadrillasSeleccionado(Empleados empleadoCuadrillasSeleccionado) {
        this.empleadoCuadrillasSeleccionado = empleadoCuadrillasSeleccionado;
    }

    public String getInfoRegistroEmpleadoCuadrilla() {
        getLovEmpleadosPorCuadrillas();
        if (lovEmpleadosPorCuadrillas != null) {
            infoRegistroEmpleadoCuadrilla = "Cantidad de registros : " + lovEmpleadosPorCuadrillas.size();
        } else {
            infoRegistroEmpleadoCuadrilla = "Cantidad de registros : 0";
        }
        return infoRegistroEmpleadoCuadrilla;
    }

    public void setInfoRegistroEmpleadoCuadrilla(String infoRegistroEmpleadoCuadrilla) {
        this.infoRegistroEmpleadoCuadrilla = infoRegistroEmpleadoCuadrilla;
    }

    public boolean isActivarBtnMostrar() {
        return activarBtnMostrar;
    }

    public void setActivarBtnMostrar(boolean activarBtnMostrar) {
        this.activarBtnMostrar = activarBtnMostrar;
    }

    public boolean isActivarBtnBuscar() {
        return activarBtnBuscar;
    }

    public void setActivarBtnBuscar(boolean activarBtnBuscar) {
        this.activarBtnBuscar = activarBtnBuscar;
    }

}

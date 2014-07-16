package Controlador;

import Entidades.ActualUsuario;
import Entidades.AportesEntidades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.ParametrosAutoliq;
import Entidades.ParametrosEstructuras;
import Entidades.ParametrosInformes;
import Entidades.Terceros;
import Entidades.TiposEntidades;
import Entidades.TiposTrabajadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarParametroAutoliqInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author Administrador
 */
@ManagedBean 
@SessionScoped 
public class ControlParametroAutoliq implements Serializable {

    @EJB
    AdministrarParametroAutoliqInterface administrarParametroAutoliq;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    // PARAMETROS AUTOLIQ //
    private List<ParametrosAutoliq> listaParametrosAutoliq;
    private List<ParametrosAutoliq> filtrarListaParametrosAutoliq;
    private ParametrosAutoliq parametroTablaSeleccionado;
    //
    private ParametrosAutoliq nuevoParametro;
    private ParametrosAutoliq editarParametro;
    private ParametrosAutoliq duplicarParametro;
    //
    private List<ParametrosAutoliq> listParametrosAutoliqCrear;
    private List<ParametrosAutoliq> listParametrosAutoliqModificar;
    private List<ParametrosAutoliq> listParametrosAutoliqBorrar;
    //
    private int index, cualCelda, indexAUX;
    private int bandera, tipoLista;
    private boolean permitirIndex, cambiosParametro;
    private BigInteger secRegistro, backUpSecRegistro;
    //
    //APORTES ENTIDADES//
    private List<AportesEntidades> listaAportesEntidades;
    private List<AportesEntidades> filtrarListaAportesEntidades;
    private AportesEntidades aporteTablaSeleccionado;
    //
    private AportesEntidades editarAporteEntidad;
    //
    private List<AportesEntidades> listAportesEntidadesModificar;
    private List<AportesEntidades> listAportesEntidadesBorrar;
    //
    private int indexAporte, cualCeldaAporte;
    private int banderaAporte, tipoListaAporte;
    private boolean permitirIndexAporte, cambiosAporte;
    private BigInteger secRegistroAporte, backUpSecRegistroAporte;

    //
    private int tipoActualizacion, k;
    private BigInteger l;
    private boolean aceptar, guardado;
    //LOVS//
    private List<TiposTrabajadores> lovTiposTrabajadores;
    private List<TiposTrabajadores> filtrarLovTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    private String infoRegistroTipoTrabajador;
    //--//
    private List<Empresas> lovEmpresas;
    private List<Empresas> filtrarLovEmpresas;
    private Empresas empresaSeleccionada;
    private String infoRegistroEmpresa;
    //--//
    private List<Empleados> lovEmpleados;
    private List<Empleados> filtrarLovEmpleados;
    private Empleados empleadoSeleccionado;
    private String infoRegistroEmpleado;
    //--//
    private List<Terceros> lovTerceros;
    private List<Terceros> filtrarLovTerceros;
    private Terceros terceroSeleccionado;
    private String infoRegistroTercero;
    //--//
    private List<TiposEntidades> lovTiposEntidades;
    private List<TiposEntidades> filtrarLovTiposEntidades;
    private TiposEntidades tipoEntidadSeleccionado;
    private String infoRegistroTipoEntidad;
    //
    private List<AportesEntidades> lovAportesEntidades;
    private List<AportesEntidades> filtrarLovAportesEntidades;
    private AportesEntidades aporteEntidadSeleccionado;
    private String infoRegistroAporteEntidad;
    //
    private String auxTipoTipoTrabajador, auxEmpresa;
    private String auxTipoEntidad, auxEmpleado;
    private long auxNitTercero;
    //
    private String altoTabla;
    private String altoTablaAporte;
    //
    private String paginaAnterior;
    //
    private Column parametroAno, parametroTipoTrabajador, parametroEmpresa;
    //
    private Column aporteCodigoEmpleado, aporteAno, aporteMes, aporteNombreEmpleado, aporteNIT, aporteTercero, aporteTipoEntidad;
    private Column aporteEmpleado, aporteEmpleador, aporteAjustePatronal, aporteSolidaridadl, aporteSubSistencia;
    private Column aporteSubsPensionados, aporteSalarioBasico, aporteIBC, aporteIBCReferencia, aporteDiasCotizados, aporteTipoAportante;
    private Column aporteING, aporteRET, aporteTDA, aporteTAA, aporteVSP, aporteVTE, aporteVST, aporteSLN, aporteIGE, aporteLMA, aporteVAC, aporteAVP, aporteVCT, aporteIRP, aporteSUS, aporteINTE;
    private Column aporteTarifaEPS, aporteTarifaAAFP, aporteTarifaACTT, aporteCodigoCTT, aporteAVPEValor, aporteAVPPValor, aporteRETCONTAValor, aporteCodigoNEPS, aporteCodigoNAFP;
    private Column aporteEGValor, aporteEGAutorizacion, aporteMaternidadValor, aporteMaternidadAuto, aporteUPCValor, aporteTipo, aporteTipoPensionado, aportePensionCompartida, aporteSubTipoCotizante;
    private Column aporteExtranjero, aporteFechaIngreso, aporteTarifaCaja, aporteTarifaSena, aporteTarifaICBF, aporteTarifaESAP, aporteTarifaMEN;
    //
    private boolean disabledBuscar;
    //
    private String infoRegistroParametro, infoRegistroAporte;
    //
    private ParametrosEstructuras parametroEstructura;
    private ParametrosInformes parametroInforme;
    private ActualUsuario usuario;
    //
    private boolean activoBtnsPaginas; 

    public ControlParametroAutoliq() {
        activoBtnsPaginas = true;
        disabledBuscar = true;
        altoTabla = "70";
        altoTablaAporte = "180";
        //
        parametroTablaSeleccionado = new ParametrosAutoliq();
        aporteTablaSeleccionado = new AportesEntidades();
        //
        nuevoParametro = new ParametrosAutoliq();
        nuevoParametro.setEmpresa(new Empresas());
        nuevoParametro.setTipotrabajador(new TiposTrabajadores());

        editarParametro = new ParametrosAutoliq();
        editarAporteEntidad = new AportesEntidades();

        duplicarParametro = new ParametrosAutoliq();
        duplicarParametro.setEmpresa(new Empresas());
        duplicarParametro.setTipotrabajador(new TiposTrabajadores());
        //
        listParametrosAutoliqCrear = new ArrayList<ParametrosAutoliq>();
        listParametrosAutoliqModificar = new ArrayList<ParametrosAutoliq>();
        listParametrosAutoliqBorrar = new ArrayList<ParametrosAutoliq>();

        listAportesEntidadesBorrar = new ArrayList<AportesEntidades>();
        listAportesEntidadesModificar = new ArrayList<AportesEntidades>();
        //
        index = -1;
        indexAporte = -1;
        cualCelda = -1;
        cualCeldaAporte = -1;
        bandera = 0;
        banderaAporte = 0;
        tipoLista = 0;
        tipoListaAporte = 0;
        permitirIndex = true;
        permitirIndexAporte = true;
        cambiosParametro = false;
        cambiosAporte = false;
        //
        secRegistro = null;
        secRegistroAporte = null;
        aceptar = true;
        guardado = true;
        //
        lovTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        lovEmpresas = null;
        empresaSeleccionada = new Empresas();
        lovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        lovTerceros = null;
        terceroSeleccionado = new Terceros();
        lovTiposEntidades = null;
        tipoEntidadSeleccionado = new TiposEntidades();
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarParametroAutoliq.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPagina(String pagina) {
        paginaAnterior = pagina;
        listaParametrosAutoliq = null;
        getListaParametrosAutoliq();
        if (listaParametrosAutoliq != null) {
            infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
        } else {
            infoRegistroParametro = "Cantidad de registros : 0";
        }
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public void modificarParametroAutoliq(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoLista == 0) {
            if (!listParametrosAutoliqCrear.contains(listaParametrosAutoliq.get(indice))) {

                if (listParametrosAutoliqModificar.isEmpty()) {
                    listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(indice));
                } else if (!listParametrosAutoliqModificar.contains(listaParametrosAutoliq.get(indice))) {
                    listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
            cambiosParametro = true;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
        } else {
            if (!listParametrosAutoliqCrear.contains(filtrarListaParametrosAutoliq.get(indice))) {

                if (listParametrosAutoliqModificar.isEmpty()) {
                    listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(indice));
                } else if (!listParametrosAutoliqModificar.contains(filtrarListaParametrosAutoliq.get(indice))) {
                    listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
            cambiosParametro = true;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
        }
        context.update("form:datosParametroAuto");
    }

    public void modificarAporteEntidad(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaAporte == 0) {
            if (listAportesEntidadesModificar.isEmpty()) {
                listAportesEntidadesModificar.add(listaAportesEntidades.get(indice));
            } else if (!listAportesEntidadesModificar.contains(listaAportesEntidades.get(indice))) {
                listAportesEntidadesModificar.add(listaAportesEntidades.get(indice));
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            indexAporte = -1;
            secRegistroAporte = null;
            cambiosAporte = true;
        } else {
            if (listAportesEntidadesModificar.isEmpty()) {
                listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indice));
            } else if (!listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indice))) {
                listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indice));
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            indexAporte = -1;
            secRegistroAporte = null;
            cambiosAporte = true;
        }
        context.update("form:datosAporteEntidad");
    }

    public void modificarParametroAutoliq(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOTRABAJADOR")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoLista == 0) {
                    listaParametrosAutoliq.get(indice).getTipotrabajador().setNombre(auxTipoTipoTrabajador);
                } else {
                    filtrarListaParametrosAutoliq.get(indice).getTipotrabajador().setNombre(auxTipoTipoTrabajador);
                }
                for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                    if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listaParametrosAutoliq.get(indice).setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                    } else {
                        filtrarListaParametrosAutoliq.get(indice).setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                    }
                    lovTiposTrabajadores.clear();
                    getLovTiposTrabajadores();
                } else {
                    permitirIndex = false;
                    context.update("formularioDialogos:TipoTrabajadorDialogo");
                    context.execute("TipoTrabajadorDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                coincidencias = 1;
                if (tipoLista == 0) {
                    listaParametrosAutoliq.get(indice).setTipotrabajador(new TiposTrabajadores());
                } else {
                    filtrarListaParametrosAutoliq.get(indice).setTipotrabajador(new TiposTrabajadores());
                }
                lovTiposTrabajadores.clear();
                getLovTiposTrabajadores();
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listParametrosAutoliqCrear.contains(listaParametrosAutoliq.get(indice))) {

                    if (listParametrosAutoliqModificar.isEmpty()) {
                        listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(indice));
                    } else if (!listParametrosAutoliqModificar.contains(listaParametrosAutoliq.get(indice))) {
                        listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
                cambiosParametro = true;
                activoBtnsPaginas = true;
                context.update("form:btn2");
                context.update("form:btn3");
                context.update("form:btn4");
                context.update("form:btn5");
                context.update("form:btn7");
            } else {
                if (!listParametrosAutoliqCrear.contains(filtrarListaParametrosAutoliq.get(indice))) {

                    if (listParametrosAutoliqModificar.isEmpty()) {
                        listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(indice));
                    } else if (!listParametrosAutoliqModificar.contains(filtrarListaParametrosAutoliq.get(indice))) {
                        listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
                cambiosParametro = true;
                activoBtnsPaginas = true;
                context.update("form:btn2");
                context.update("form:btn3");
                context.update("form:btn4");
                context.update("form:btn5");
                context.update("form:btn7");
            }
        }
        context.update("form:datosParametroAuto");
    }
    
    public void organizarTablasEmpleador() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierdaEM");
        context.update("form:tablaInferiorDerechaEM");
    }

    public void modificarAporteEntidad(int indice, String confirmarCambio, String valorConfirmar) {
        indexAporte = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOENTIDAD")) {
            if (tipoListaAporte == 0) {
                listaAportesEntidades.get(indice).getTipoentidad().setNombre(auxTipoEntidad);
            } else {
                filtrarListaAportesEntidades.get(indice).getTipoentidad().setNombre(auxTipoEntidad);
            }
            for (int i = 0; i < lovTiposEntidades.size(); i++) {
                if (lovTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaAporte == 0) {
                    listaAportesEntidades.get(indice).setTipoentidad(lovTiposEntidades.get(indiceUnicoElemento));
                } else {
                    filtrarListaAportesEntidades.get(indice).setTipoentidad(lovTiposEntidades.get(indiceUnicoElemento));
                }
                lovTiposEntidades.clear();
                getLovTiposEntidades();
            } else {
                permitirIndexAporte = false;
                context.update("formularioDialogos:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoListaAporte == 0) {
                    listaAportesEntidades.get(indice).getTerceroRegistro().setNit(auxNitTercero);
                } else {
                    filtrarListaAportesEntidades.get(indice).getTerceroRegistro().setNit(auxNitTercero);
                }
                for (int i = 0; i < lovTerceros.size(); i++) {
                    if (lovTerceros.get(i).getStrNit().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaAporte == 0) {
                        listaAportesEntidades.get(indice).setTerceroRegistro(lovTerceros.get(indiceUnicoElemento));
                    } else {
                        filtrarListaAportesEntidades.get(indice).setTerceroRegistro(lovTerceros.get(indiceUnicoElemento));
                    }
                    lovTerceros.clear();
                    getLovTerceros();
                } else {
                    permitirIndexAporte = false;
                    context.update("formularioDialogos:TerceroDialogo");
                    context.execute("TerceroDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                coincidencias = 1;
                if (tipoListaAporte == 0) {
                    listaAportesEntidades.get(indice).setTerceroRegistro(new Terceros());
                } else {
                    filtrarListaAportesEntidades.get(indice).setTerceroRegistro(new Terceros());
                }
                lovTerceros.clear();
                getLovTerceros();
            }
        }
        if (confirmarCambio.equalsIgnoreCase("EMPLEADO")) {
            if (tipoListaAporte == 0) {
                listaAportesEntidades.get(indice).getEmpleado().getPersona().setNombreCompleto(auxEmpleado);
            } else {
                filtrarListaAportesEntidades.get(indice).getEmpleado().getPersona().setNombreCompleto(auxEmpleado);
            }
            for (int i = 0; i < lovEmpleados.size(); i++) {
                if (lovEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaAporte == 0) {
                    listaAportesEntidades.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                } else {
                    filtrarListaAportesEntidades.get(indice).setEmpleado(lovEmpleados.get(indiceUnicoElemento));
                }
                lovEmpleados.clear();
                getLovEmpleados();
            } else {
                permitirIndexAporte = false;
                context.update("formularioDialogos:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaAporte == 0) {
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indice));
                } else if (!listAportesEntidadesModificar.contains(listaAportesEntidades.get(indice))) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexAporte = -1;
                secRegistroAporte = null;
                cambiosAporte = true;
            } else {
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indice));
                } else if (!listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indice))) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexAporte = -1;
                secRegistroAporte = null;
                cambiosAporte = true;
            }
        }
        context.update("form:datosAporteEntidad");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("TIPOTRABAJADOR")) {
            if (tipoNuevo == 1) {
                auxTipoTipoTrabajador = nuevoParametro.getTipotrabajador().getNombre();
            } else if (tipoNuevo == 2) {
                auxTipoTipoTrabajador = duplicarParametro.getTipotrabajador().getNombre();
            }
        }
        if (Campo.equals("EMPRESA")) {
            if (tipoNuevo == 1) {
                auxEmpresa = nuevoParametro.getEmpresa().getNombre();
            } else if (tipoNuevo == 2) {
                auxEmpresa = duplicarParametro.getEmpresa().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoParametroAutoliq(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOTRABAJADOR")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevoParametro.getTipotrabajador().setNombre(auxTipoTipoTrabajador);
                } else if (tipoNuevo == 2) {
                    duplicarParametro.getTipotrabajador().setNombre(auxTipoTipoTrabajador);
                }
                for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                    if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevoParametro.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaTipoTrabajadorParametro");
                    } else if (tipoNuevo == 2) {
                        duplicarParametro.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarTipoTrabajadorParametro");
                    }
                    lovTiposTrabajadores.clear();
                    getLovTiposTrabajadores();
                } else {
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaTipoTrabajadorParametro");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarTipoTrabajadorParametro");
                    }
                    context.update("formularioDialogos:TipoTrabajadorDialogo");
                    context.execute("TipoTrabajadorDialogo.show()");
                }
            } else {
                if (tipoNuevo == 1) {
                    nuevoParametro.setTipotrabajador(new TiposTrabajadores());
                    context.update("formularioDialogos:nuevaTipoTrabajadorParametro");
                } else if (tipoNuevo == 2) {
                    duplicarParametro.setTipotrabajador(new TiposTrabajadores());
                    context.update("formularioDialogos:duplicarTipoTrabajadorParametro");
                }
                lovTiposTrabajadores.clear();
                getLovTiposTrabajadores();
            }
        }
        if (confirmarCambio.equalsIgnoreCase("EMPRESA")) {
            if (tipoNuevo == 1) {
                nuevoParametro.getEmpresa().setNombre(auxEmpresa);
            } else if (tipoNuevo == 2) {
                duplicarParametro.getEmpresa().setNombre(auxEmpresa);
            }
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoParametro.setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaEmpresaParametro");
                } else if (tipoNuevo == 2) {
                    duplicarParametro.setEmpresa(lovEmpresas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEmpresaParametro");
                }
                lovEmpresas.clear();
                getLovEmpresas();
            } else {
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaEmpresaParametro");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEmpresaParametro");
                }
                context.update("formularioDialogos:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        }
    }

    public void posicionParametro() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndice(indice, columna);
    }

    public void posicionAporteEntidad() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndiceAporteEntidad(indice, columna);
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            indexAUX = indice;
            if (tipoLista == 0) {
                secRegistro = listaParametrosAutoliq.get(index).getSecuencia();
                auxTipoTipoTrabajador = listaParametrosAutoliq.get(index).getTipotrabajador().getNombre();
            } else {
                secRegistro = filtrarListaParametrosAutoliq.get(index).getSecuencia();
                auxTipoTipoTrabajador = filtrarListaParametrosAutoliq.get(index).getTipotrabajador().getNombre();
            }
            if (banderaAporte == 1) {
                desactivarFiltradoAporteEntidad();
                banderaAporte = 0;
                filtrarListaAportesEntidades = null;
                tipoListaAporte = 0;
            }
            listaAportesEntidades = null;
            getListaAportesEntidades();
            if (listaAportesEntidades != null) {
                infoRegistroAporte = "Cantidad de registros : " + listaAportesEntidades.size();
            } else {
                infoRegistroAporte = "Cantidad de registros : 0";
            }
            RequestContext context = RequestContext.getCurrentInstance();
            activoBtnsPaginas = false;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            context.update("form:infoRegistroAporte");
            context.update("form:datosAporteEntidad");
            context.update("form:superiorIzquierdoEM");
            context.update("form:inferiorIzquierdaEM");
            //context.update("form:datosAporteEntidad2");
            lovAportesEntidades = null;
            getLovAportesEntidades();
            if (lovAportesEntidades != null) {
                if (lovAportesEntidades.isEmpty()) {
                    disabledBuscar = true;
                } else {
                    disabledBuscar = false;
                }
            } else {
                disabledBuscar = true;
            }
            context.update("form:btn1");
        }
    }

    public void cambiarIndiceAporteEntidad(int indice, int celda) {
        if (permitirIndexAporte == true) {
            indexAporte = indice;
            cualCeldaAporte = celda;
            index = -1;
            activoBtnsPaginas = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            if (tipoListaAporte == 0) {
                secRegistroAporte = listaAportesEntidades.get(indexAporte).getSecuencia();
                auxEmpleado = listaAportesEntidades.get(indexAporte).getEmpleado().getPersona().getNombreCompleto();
                auxNitTercero = listaAportesEntidades.get(indexAporte).getTerceroRegistro().getNit();
                auxTipoEntidad = listaAportesEntidades.get(indexAporte).getTipoentidad().getNombre();
            } else {
                secRegistroAporte = filtrarListaAportesEntidades.get(indexAporte).getSecuencia();
                auxEmpleado = filtrarListaAportesEntidades.get(indexAporte).getEmpleado().getPersona().getNombreCompleto();
                auxNitTercero = filtrarListaAportesEntidades.get(indexAporte).getTerceroRegistro().getNit();
                auxTipoEntidad = filtrarListaAportesEntidades.get(indexAporte).getTipoentidad().getNombre();
            }
        }
    }

    public void guardarYSalir() {
        guardadoGeneral();
        salir();
    }

    public void cancelarYSalir() {
        cancelarModificacion();
        salir();
    }

    public void guardadoGeneral() {
        if (guardado == false) {
            if (cambiosParametro == true) {
                guardarCambiosParametro();
            }
            if (cambiosAporte == true) {
                guardarCambiosAportes();
            }
        }
    }

    public void guardarCambiosParametro() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listParametrosAutoliqBorrar.isEmpty()) {
                administrarParametroAutoliq.borrarParametrosAutoliq(listParametrosAutoliqBorrar);
                listParametrosAutoliqBorrar.clear();
            }
            if (!listParametrosAutoliqCrear.isEmpty()) {
                administrarParametroAutoliq.crearParametrosAutoliq(listParametrosAutoliqCrear);
                listParametrosAutoliqCrear.clear();
            }
            if (!listParametrosAutoliqModificar.isEmpty()) {
                administrarParametroAutoliq.editarParametrosAutoliq(listParametrosAutoliqModificar);
                listParametrosAutoliqModificar.clear();
            }
            listaParametrosAutoliq = null;
            getListaParametrosAutoliq();
            if (listaParametrosAutoliq != null) {
                infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
            } else {
                infoRegistroParametro = "Cantidad de registros : 0";
            }
            context.update("form:infoRegistroParametro");
            context.update("form:datosParametroAuto");
            k = 0;
            index = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            indexAUX = -1;

            secRegistro = null;
            cambiosParametro = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Parametro de Liquidacion con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosParametro  Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Parametro de Liquidacion, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosAportes() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!listAportesEntidadesBorrar.isEmpty()) {
                administrarParametroAutoliq.borrarAportesEntidades(listAportesEntidadesBorrar);
                listAportesEntidadesBorrar.clear();
            }
            if (!listAportesEntidadesModificar.isEmpty()) {
                administrarParametroAutoliq.editarAportesEntidades(listAportesEntidadesModificar);
                listAportesEntidadesModificar.clear();
            }
            listaAportesEntidades = null;
            getListaAportesEntidades();
            if (listaAportesEntidades != null) {
                infoRegistroAporte = "Cantidad de registros : " + listaAportesEntidades.size();
            } else {
                infoRegistroAporte = "Cantidad de registros : 0";
            }
            context.update("form:infoRegistroAporte");
            context.update("form:datosAporteEntidad");
            k = 0;
            indexAporte = -1;
            secRegistroAporte = null;
            cambiosAporte = false;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Aporte Entidad con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosAportes  Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Aporte Entidad, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            altoTabla = "70";
            parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
            parametroAno.setFilterStyle("display: none; visibility: hidden;");
            parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
            parametroTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
            parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
            parametroEmpresa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosParametroAuto");
            bandera = 0;
            filtrarListaParametrosAutoliq = null;
            tipoLista = 0;
        }
        if (banderaAporte == 1) {
            desactivarFiltradoAporteEntidad();
            banderaAporte = 0;
            filtrarListaAportesEntidades = null;
            tipoListaAporte = 0;
        }
        listParametrosAutoliqBorrar.clear();
        listParametrosAutoliqCrear.clear();
        listParametrosAutoliqModificar.clear();
        //
        listAportesEntidadesBorrar.clear();
        listAportesEntidadesModificar.clear();
        //
        index = -1;
        indexAUX = -1;
        indexAporte = -1;
        secRegistro = null;
        secRegistroAporte = null;
        k = 0;
        listaParametrosAutoliq = null;
        getListaParametrosAutoliq();
        if (listaParametrosAutoliq != null) {
            infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
        } else {
            infoRegistroParametro = "Cantidad de registros : 0";
        }
        listaAportesEntidades = null;
        getListaAportesEntidades();
        if (listaAportesEntidades != null) {
            infoRegistroAporte = "Cantidad de registros : " + listaAportesEntidades.size();
        } else {
            infoRegistroAporte = "Cantidad de registros : 0";
        }
        cambiosParametro = false;
        cambiosAporte = false;
        guardado = true;
        disabledBuscar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        context.update("form:ACEPTAR");
        context.update("form:btn1");
        context.update("form:infoRegistroAporte");
        context.update("form:infoRegistroParametro");
        context.update("form:datosParametroAuto");
        context.update("form:datosAporteEntidad");
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                editarParametro = listaParametrosAutoliq.get(index);
            } else {
                editarParametro = filtrarListaParametrosAutoliq.get(index);
            }
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarAnoD");
                context.execute("editarAnoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoTrabajadorD");
                context.execute("editarTipoTrabajadorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarEmpresaD");
                context.execute("editarEmpresaD.show()");
                cualCelda = -1;
            }
            index = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            secRegistro = null;
        }
        if (indexAporte >= 0) {
            if (tipoListaAporte == 0) {
                editarAporteEntidad = listaAportesEntidades.get(indexAporte);
            } else {
                editarAporteEntidad = filtrarListaAportesEntidades.get(indexAporte);
            }
            if (cualCeldaAporte == 0) {
                context.update("formularioDialogos:editarAnoD");
                context.execute("editarAnoD.show()");
                cualCeldaAporte = -1;
            } else if (cualCeldaAporte == 2) {
                context.update("formularioDialogos:editarTipoTrabajadorD");
                context.execute("editarTipoTrabajadorD.show()");
                cualCeldaAporte = -1;
            } else if (cualCeldaAporte == 3) {
                context.update("formularioDialogos:editarEmpresaD");
                context.execute("editarEmpresaD.show()");
                cualCeldaAporte = -1;
            }
            indexAporte = -1;
            secRegistroAporte = null;
        }
    }

    public void dispararDialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            context.update("formularioDialogos:nuevaParametro");
            context.execute("NuevoRegistroParametro.show()");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    public void agregarNuevaParametroAutoliq() {
        if (nuevoParametro.getAno() > 0 && nuevoParametro.getMes() > 0
                && nuevoParametro.getEmpresa().getSecuencia() != null) {
            if (bandera == 1) {
                altoTabla = "70";
                FacesContext c = FacesContext.getCurrentInstance();
                //CERRAR FILTRADO
                parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
                parametroAno.setFilterStyle("display: none; visibility: hidden;");
                parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
                parametroTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
                parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
                parametroEmpresa.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosParametroAuto");
                bandera = 0;
                filtrarListaParametrosAutoliq = null;
                tipoLista = 0;
            }
            if (banderaAporte == 1) {
                desactivarFiltradoAporteEntidad();
                banderaAporte = 0;
                filtrarListaAportesEntidades = null;
                tipoListaAporte = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoParametro.setSecuencia(l);
            listParametrosAutoliqCrear.add(nuevoParametro);
            if (listaParametrosAutoliq == null) {
                listaParametrosAutoliq = new ArrayList<ParametrosAutoliq>();
            }
            listaParametrosAutoliq.add(nuevoParametro);
            nuevoParametro = new ParametrosAutoliq();
            nuevoParametro.setTipotrabajador(new TiposTrabajadores());
            nuevoParametro.setEmpresa(new Empresas());
            RequestContext context = RequestContext.getCurrentInstance();

            infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();

            context.update("form:infoRegistroParametro");
            context.update("form:datosParametroAuto");
            context.execute("NuevoRegistroParametro.hide()");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            index = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            secRegistro = null;
            cambiosParametro = true;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNullParametro.show()");
        }
    }

    public void limpiarNuevaParametroAutoliq() {
        nuevoParametro = new ParametrosAutoliq();
        nuevoParametro.setTipotrabajador(new TiposTrabajadores());
        nuevoParametro.setEmpresa(new Empresas());
        index = -1;
        activoBtnsPaginas = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
    }

    public void duplicarParametroAutoliq() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (guardado == false) {
                duplicarParametro = new ParametrosAutoliq();
                if (tipoLista == 0) {
                    duplicarParametro.setAno(listaParametrosAutoliq.get(index).getAno());
                    duplicarParametro.setEmpresa(listaParametrosAutoliq.get(index).getEmpresa());
                    duplicarParametro.setMes(listaParametrosAutoliq.get(index).getMes());
                    duplicarParametro.setTipotrabajador(listaParametrosAutoliq.get(index).getTipotrabajador());
                }
                if (tipoLista == 1) {
                    duplicarParametro.setAno(filtrarListaParametrosAutoliq.get(index).getAno());
                    duplicarParametro.setEmpresa(filtrarListaParametrosAutoliq.get(index).getEmpresa());
                    duplicarParametro.setMes(filtrarListaParametrosAutoliq.get(index).getMes());
                    duplicarParametro.setTipotrabajador(filtrarListaParametrosAutoliq.get(index).getTipotrabajador());

                }
                context.update("formularioDialogos:duplicarParametro");
                context.execute("DuplicarRegistroParametro.show()");
                index = -1;
                activoBtnsPaginas = true;
                context.update("form:btn2");
                context.update("form:btn3");
                context.update("form:btn4");
                context.update("form:btn5");
                context.update("form:btn7");
                secRegistro = null;
            } else {
                context.execute("confirmarGuardar.show()");
            }
        }
    }

    public void confirmarDuplicarParametroAutoliq() {
        if (duplicarParametro.getAno() > 0 && duplicarParametro.getMes() > 0
                && duplicarParametro.getEmpresa().getSecuencia() != null) {
            if (bandera == 1) {
                altoTabla = "70";
                FacesContext c = FacesContext.getCurrentInstance();
                parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
                parametroAno.setFilterStyle("display: none; visibility: hidden;");
                parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
                parametroTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
                parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
                parametroEmpresa.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosParametroAuto");
                bandera = 0;
                filtrarListaParametrosAutoliq = null;
                tipoLista = 0;
            }
            if (banderaAporte == 1) {
                desactivarFiltradoAporteEntidad();
                banderaAporte = 0;
                filtrarListaAportesEntidades = null;
                tipoListaAporte = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            duplicarParametro.setSecuencia(l);
            listaParametrosAutoliq.add(duplicarParametro);
            listParametrosAutoliqCrear.add(duplicarParametro);
            RequestContext context = RequestContext.getCurrentInstance();

            infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();

            context.update("form:infoRegistroParametro");
            context.update("form:datosParametroAuto");
            context.execute("DuplicarRegistroParametro.hide()");
            index = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            duplicarParametro = new ParametrosAutoliq();
            cambiosParametro = true;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNullParametro.show()");
        }
    }

    public void limpiarDuplicarParametroAutoliq() {
        duplicarParametro = new ParametrosAutoliq();
        duplicarParametro.setEmpresa(new Empresas());
        duplicarParametro.setTipotrabajador(new TiposTrabajadores());
    }

    public void validarBorradoPagina() {
        if (index >= 0) {
            int tam = 0;
            if (listaAportesEntidades != null) {
                tam = listaAportesEntidades.size();
            }
            if (tam == 0) {
                borrarParametroAutoliq();
            } else {
                RequestContext.getCurrentInstance().execute("errorBorrarParametro.show()");
            }
        }
        if (indexAporte >= 0) {
            borrarAporteEntidad();
        }
    }

    public void borrarParametroAutoliq() {
        if (tipoLista == 0) {
            if (!listParametrosAutoliqModificar.isEmpty() && listParametrosAutoliqModificar.contains(listaParametrosAutoliq.get(index))) {
                int modIndex = listParametrosAutoliqModificar.indexOf(listaParametrosAutoliq.get(index));
                listParametrosAutoliqModificar.remove(modIndex);
                listParametrosAutoliqBorrar.add(listaParametrosAutoliq.get(index));
            } else if (!listParametrosAutoliqCrear.isEmpty() && listParametrosAutoliqCrear.contains(listaParametrosAutoliq.get(index))) {
                int crearIndex = listParametrosAutoliqCrear.indexOf(listaParametrosAutoliq.get(index));
                listParametrosAutoliqCrear.remove(crearIndex);
            } else {
                listParametrosAutoliqBorrar.add(listaParametrosAutoliq.get(index));
            }
            listaParametrosAutoliq.remove(index);
        }
        if (tipoLista == 1) {
            if (!listParametrosAutoliqModificar.isEmpty() && listParametrosAutoliqModificar.contains(filtrarListaParametrosAutoliq.get(index))) {
                int modIndex = listParametrosAutoliqModificar.indexOf(filtrarListaParametrosAutoliq.get(index));
                listParametrosAutoliqModificar.remove(modIndex);
                listParametrosAutoliqBorrar.add(filtrarListaParametrosAutoliq.get(index));
            } else if (!listParametrosAutoliqCrear.isEmpty() && listParametrosAutoliqCrear.contains(filtrarListaParametrosAutoliq.get(index))) {
                int crearIndex = listParametrosAutoliqCrear.indexOf(filtrarListaParametrosAutoliq.get(index));
                listParametrosAutoliqCrear.remove(crearIndex);
            } else {
                listParametrosAutoliqBorrar.add(filtrarListaParametrosAutoliq.get(index));
            }
            int VCIndex = listaParametrosAutoliq.indexOf(filtrarListaParametrosAutoliq.get(index));
            listaParametrosAutoliq.remove(VCIndex);
            filtrarListaParametrosAutoliq.remove(index);
        }

        RequestContext context = RequestContext.getCurrentInstance();

        infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();

        context.update("form:infoRegistroParametro");
        context.update("form:datosParametroAuto");
        index = -1;
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        cambiosParametro = true;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }

    }

    public void borrarAporteEntidad() {
        if (tipoLista == 0) {
            if (!listAportesEntidadesModificar.isEmpty() && listAportesEntidadesModificar.contains(listaAportesEntidades.get(indexAporte))) {
                int modIndex = listAportesEntidadesModificar.indexOf(listaAportesEntidades.get(indexAporte));
                listAportesEntidadesModificar.remove(modIndex);
                listAportesEntidadesBorrar.add(listaAportesEntidades.get(indexAporte));
            } else {
                listAportesEntidadesBorrar.add(listaAportesEntidades.get(indexAporte));
            }
            listaParametrosAutoliq.remove(indexAporte);
        }
        if (tipoLista == 1) {
            if (!listAportesEntidadesModificar.isEmpty() && listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indexAporte))) {
                int modIndex = listAportesEntidadesModificar.indexOf(filtrarListaAportesEntidades.get(indexAporte));
                listAportesEntidadesModificar.remove(modIndex);
                listAportesEntidadesBorrar.add(filtrarListaAportesEntidades.get(indexAporte));
            } else {
                listAportesEntidadesBorrar.add(filtrarListaAportesEntidades.get(indexAporte));
            }
            int VCIndex = listaParametrosAutoliq.indexOf(filtrarListaAportesEntidades.get(indexAporte));
            listaAportesEntidades.remove(VCIndex);
            filtrarListaAportesEntidades.remove(indexAporte);
        }

        RequestContext context = RequestContext.getCurrentInstance();

        infoRegistroAporte = "Cantidad de registros : " + listaAportesEntidades.size();

        context.update("form:infoRegistroAporte");
        context.update("form:datosAporteEntidad");
        indexAporte = -1;
        secRegistroAporte = null;
        cambiosAporte = true;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }

    public void borrarAporteEntidadProcesoAutomatico() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                guardadoGeneral();
            }
            if (tipoLista == 0) {
                administrarParametroAutoliq.borrarAportesEntidadesProcesoAutomatico(listaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia(), listaParametrosAutoliq.get(indexAUX).getMes(), listaParametrosAutoliq.get(indexAUX).getAno());
            } else {
                administrarParametroAutoliq.borrarAportesEntidadesProcesoAutomatico(filtrarListaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia(), filtrarListaParametrosAutoliq.get(indexAUX).getMes(), filtrarListaParametrosAutoliq.get(indexAUX).getAno());
            }
            listaParametrosAutoliq = null;
            getListaParametrosAutoliq();
            System.out.println("Paso el getListaParametros");
            if (listaParametrosAutoliq != null) {
                infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
            } else {
                infoRegistroParametro = "Cantidad de registros : 0";
            }
            listaAportesEntidades = null;
            infoRegistroAporte = "Cantidad de registros : 0";
            disabledBuscar = true;
            context.update("form:ACEPTAR");
            context.update("form:btn1");
            context.update("form:infoRegistroAporte");
            context.update("form:infoRegistroParametro");
            context.update("form:datosParametroAuto");
            context.update("form:datosAporteEntidad");
            indexAUX = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            FacesMessage msg = new FacesMessage("Información", "El borrado fue realizado con éxito. Recuerdo que los cambios manuales deben ser borrados manualmente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error borrarAporteEntidadProcesoAutomatico Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el proceso de borrado de Aportes Entidades.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void procesoLiquidacionAporteEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            getUsuario();
            if (usuario != null) {
                getParametroEstructura();
                getParametroInforme();
                if (parametroEstructura != null && parametroInforme != null) {
                    boolean fechasIgualesEstructura = true;
                    boolean fechasIgualesInforme = true;
                    short ano = 0;
                    short mes = 0;
                    if (tipoLista == 0) {
                        ano = listaParametrosAutoliq.get(indexAUX).getAno();
                        mes = listaParametrosAutoliq.get(indexAUX).getMes();
                    } else {
                        ano = filtrarListaParametrosAutoliq.get(indexAUX).getAno();
                        mes = filtrarListaParametrosAutoliq.get(indexAUX).getMes();
                    }

                    if ((parametroEstructura.getFechahastacausado().getMonth() + 1) != mes) {
                        fechasIgualesEstructura = false;
                    }
                    if ((parametroEstructura.getFechahastacausado().getYear() + 1900) != ano) {
                        fechasIgualesEstructura = false;
                    }
                    if ((parametroInforme.getFechahasta().getMonth() + 1) != mes) {
                        fechasIgualesInforme = false;
                    }
                    if ((parametroInforme.getFechahasta().getYear() + 1900) != ano) {
                        fechasIgualesInforme = false;
                    }
                    if (fechasIgualesEstructura == true && fechasIgualesInforme == true) {
                        context.execute("procesoLiquidacionOK.show()");
                    } else {
                        context.execute("errorProcesoLiquidacionFechas.show()");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error procesoLiquidacionAporteEntidad Controlador : " + e.toString());
        }
    }

    public void procesoLiquidacionOK() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                guardadoGeneral();
            }

            getParametroEstructura();
            getParametroInforme();
            String procesoInsertar = "";
            String procesoActualizar = "";
            if (tipoLista == 0) {
                procesoInsertar = administrarParametroAutoliq.ejecutarPKGInsertar(parametroEstructura.getFechadesdecausado(), parametroEstructura.getFechahastacausado(), listaParametrosAutoliq.get(indexAUX).getTipotrabajador().getSecuencia(), listaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
                procesoActualizar = administrarParametroAutoliq.ejecutarPKGActualizarNovedades(listaParametrosAutoliq.get(indexAUX).getAno(), listaParametrosAutoliq.get(indexAUX).getMes(), listaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
            } else {
                procesoInsertar = administrarParametroAutoliq.ejecutarPKGInsertar(parametroEstructura.getFechadesdecausado(), parametroEstructura.getFechahastacausado(), filtrarListaParametrosAutoliq.get(indexAUX).getTipotrabajador().getSecuencia(), filtrarListaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
                procesoActualizar = administrarParametroAutoliq.ejecutarPKGActualizarNovedades(filtrarListaParametrosAutoliq.get(indexAUX).getAno(), filtrarListaParametrosAutoliq.get(indexAUX).getMes(), filtrarListaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
            }
            if ((procesoInsertar.equals("PROCESO_EXITOSO")) && (procesoActualizar.equals("PROCESO_EXITOSO"))) {
                listaParametrosAutoliq = null;
                getListaParametrosAutoliq();
                if (listaParametrosAutoliq != null) {
                    infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
                } else {
                    infoRegistroParametro = "Cantidad de registros : 0";
                }
                listaAportesEntidades = null;
                infoRegistroAporte = "Cantidad de registros : 0";
                disabledBuscar = true;
                context.update("form:ACEPTAR");
                context.update("form:btn1");
                context.update("form:infoRegistroAporte");
                context.update("form:infoRegistroParametro");
                context.update("form:datosParametroAuto");
                context.update("form:datosAporteEntidad");
                indexAUX = -1;
                activoBtnsPaginas = true;
                context.update("form:btn2");
                context.update("form:btn3");
                context.update("form:btn4");
                context.update("form:btn5");
                context.update("form:btn7");
                FacesMessage msg = new FacesMessage("Información", "El proceso de Liquidacion fue realizado con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            } else if ((procesoInsertar.equals("ERROR_PERSISTENCIA")) || (procesoActualizar.equals("ERROR_PERSISTENCIA"))) {
                FacesMessage msg = new FacesMessage("Información", "Ocurrio un error en la ejecucion del proceso de liquidacion. Por favor, revisar los archivos de error de la carpeta SalidasUTL");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }

        } catch (Exception e) {
            System.out.println("Error procesoLiquidacionOK Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el proceso de Liquidacion.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void cambiarFechasParametros() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            short ano = 0;
            short mes = 0;
            if (tipoLista == 0) {
                ano = listaParametrosAutoliq.get(indexAUX).getAno();
                mes = listaParametrosAutoliq.get(indexAUX).getMes();
            } else {
                ano = filtrarListaParametrosAutoliq.get(indexAUX).getAno();
                mes = filtrarListaParametrosAutoliq.get(indexAUX).getMes();
            }
            Date fechaDesdeParametros;

            Calendar calFin = Calendar.getInstance();

            calFin.set(Integer.parseInt(String.valueOf(ano)), Integer.parseInt(String.valueOf(mes - 1)), 1);

            fechaDesdeParametros = calFin.getTime();

            parametroEstructura.setFechadesdecausado(fechaDesdeParametros);
            parametroInforme.setFechadesde(fechaDesdeParametros);

            Date fechaHastaParametros;
            calFin.set(Integer.parseInt(String.valueOf(ano)), Integer.parseInt(String.valueOf(mes - 1)), calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
            fechaHastaParametros = calFin.getTime();

            parametroEstructura.setFechahastacausado(fechaHastaParametros);
            parametroInforme.setFechahasta(fechaHastaParametros);

            administrarParametroAutoliq.modificarParametroEstructura(parametroEstructura);
            administrarParametroAutoliq.modificarParametroInforme(parametroInforme);

            parametroInforme = null;
            parametroEstructura = null;
            indexAUX = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            FacesMessage msg = new FacesMessage("Información", "Se realizo con éxito el cambio de fechas de ParametrosEstructuras y ParametrosReportes");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        } catch (Exception e) {
            System.out.println("Error cambiarFechasParametros Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en la modificacio de las fechas de ParametrosEstructuras y ParametrosReportes");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        }
    }

    public void procesoAcumularDiferenciaAporteEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            getUsuario();
            if (usuario != null) {
                getParametroEstructura();
                getParametroInforme();
                if (parametroEstructura != null && parametroInforme != null) {
                    boolean fechasIgualesEstructura = true;
                    boolean fechasIgualesInforme = true;
                    short ano = 0;
                    short mes = 0;
                    if (tipoLista == 0) {
                        ano = listaParametrosAutoliq.get(indexAUX).getAno();
                        mes = listaParametrosAutoliq.get(indexAUX).getMes();
                    } else {
                        ano = filtrarListaParametrosAutoliq.get(indexAUX).getAno();
                        mes = filtrarListaParametrosAutoliq.get(indexAUX).getMes();
                    }
                    if ((parametroEstructura.getFechahastacausado().getMonth() + 1) != mes) {
                        fechasIgualesEstructura = false;
                    }
                    if ((parametroEstructura.getFechahastacausado().getYear() + 1900) != ano) {
                        fechasIgualesEstructura = false;
                    }
                    if ((parametroInforme.getFechahasta().getMonth() + 1) != mes) {
                        fechasIgualesInforme = false;
                    }
                    if ((parametroInforme.getFechahasta().getYear() + 1900) != ano) {
                        fechasIgualesInforme = false;
                    }
                    if (fechasIgualesEstructura == true && fechasIgualesInforme == true) {
                        context.execute("acumularDiferenciaOK.show()");
                    } else {
                        context.execute("errorAcumularDiferencia.show()");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error procesoLiquidacionAporteEntidad Controlador : " + e.toString());
        }
    }

    public void acumularDiferenciaOK() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                guardadoGeneral();
            }
            if (tipoLista == 0) {
                administrarParametroAutoliq.ejecutarPKGActualizarNovedades(listaParametrosAutoliq.get(indexAUX).getAno(), listaParametrosAutoliq.get(indexAUX).getMes(), listaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
            } else {
                administrarParametroAutoliq.ejecutarPKGActualizarNovedades(filtrarListaParametrosAutoliq.get(indexAUX).getAno(), filtrarListaParametrosAutoliq.get(indexAUX).getMes(), filtrarListaParametrosAutoliq.get(indexAUX).getEmpresa().getSecuencia());
            }
            listaParametrosAutoliq = null;
            getListaParametrosAutoliq();
            if (listaParametrosAutoliq != null) {
                infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();
            } else {
                infoRegistroParametro = "Cantidad de registros : 0";
            }
            listaAportesEntidades = null;
            infoRegistroAporte = "Cantidad de registros : 0";
            disabledBuscar = true;
            context.update("form:ACEPTAR");
            context.update("form:btn1");
            context.update("form:infoRegistroAporte");
            context.update("form:infoRegistroParametro");
            context.update("form:datosParametroAuto");
            context.update("form:datosAporteEntidad");
            indexAUX = -1;
            activoBtnsPaginas = true;
            context.update("form:btn2");
            context.update("form:btn3");
            context.update("form:btn4");
            context.update("form:btn5");
            context.update("form:btn7");
            FacesMessage msg = new FacesMessage("Información", "El proceso de Acumular Diferencias de Aportes Entidades fue realizado con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        } catch (Exception e) {
            System.out.println("Error acumularDiferenciaOK Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el proceso de Acumular Diferencias.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void activarCtrlF11() {

        if (index >= 0) {
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 0) {
                altoTabla = "48";
                parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
                parametroAno.setFilterStyle("width: 20px");
                parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
                parametroTipoTrabajador.setFilterStyle("width: 60px");
                parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
                parametroEmpresa.setFilterStyle("width: 60px");
                RequestContext.getCurrentInstance().update("form:datosParametroAuto");
                bandera = 1;
            } else if (bandera == 1) {
                altoTabla = "70";
                parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
                parametroAno.setFilterStyle("display: none; visibility: hidden;");
                parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
                parametroTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
                parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
                parametroEmpresa.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosParametroAuto");
                bandera = 0;
                filtrarListaParametrosAutoliq = null;
                tipoLista = 0;
            }
        }
        if (indexAporte >= 0) {
            if (banderaAporte == 0) {
                activarFiltradoAporteEntidad();
                banderaAporte = 1;
            } else if (banderaAporte == 1) {
                desactivarFiltradoAporteEntidad();
                banderaAporte = 0;
                filtrarListaAportesEntidades = null;
                tipoListaAporte = 0;
            }
        }
    }

    public void desactivarFiltradoAporteEntidad() {
        FacesContext c = FacesContext.getCurrentInstance();
        altoTablaAporte = "180";
        aporteCodigoEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoEmpleado");
        aporteCodigoEmpleado.setFilterStyle("display: none; visibility: hidden;");

        aporteAno = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAno");
        aporteAno.setFilterStyle("display: none; visibility: hidden;");

        aporteMes = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMes");
        aporteMes.setFilterStyle("display: none; visibility: hidden;");

        aporteNombreEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteNombreEmpleado");
        aporteNombreEmpleado.setFilterStyle("display: none; visibility: hidden;");

        aporteNIT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteNIT");
        aporteNIT.setFilterStyle("display: none; visibility: hidden;");

        aporteTercero = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTercero");
        aporteTercero.setFilterStyle("display: none; visibility: hidden;");

        aporteTipoEntidad = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoEntidad");
        aporteTipoEntidad.setFilterStyle("display: none; visibility: hidden;");

        aporteEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEmpleado");
        aporteEmpleado.setFilterStyle("display: none; visibility: hidden;");

        aporteEmpleador = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEmpleador");
        aporteEmpleador.setFilterStyle("display: none; visibility: hidden;");

        aporteAjustePatronal = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAjustePatronal");
        aporteAjustePatronal.setFilterStyle("display: none; visibility: hidden;");

        aporteSolidaridadl = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSolidaridadl");
        aporteSolidaridadl.setFilterStyle("display: none; visibility: hidden;");

        aporteSubSistencia = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubSistencia");
        aporteSubSistencia.setFilterStyle("display: none; visibility: hidden;");

        aporteSubsPensionados = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubsPensionados");
        aporteSubsPensionados.setFilterStyle("display: none; visibility: hidden;");

        aporteSalarioBasico = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSalarioBasico");
        aporteSalarioBasico.setFilterStyle("display: none; visibility: hidden;");

        aporteIBC = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIBC");
        aporteIBC.setFilterStyle("display: none; visibility: hidden;");

        aporteIBCReferencia = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIBCReferencia");
        aporteIBCReferencia.setFilterStyle("display: none; visibility: hidden;");

        aporteDiasCotizados = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteDiasCotizados");
        aporteDiasCotizados.setFilterStyle("display: none; visibility: hidden;");

        aporteTipoAportante = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoAportante");
        aporteTipoAportante.setFilterStyle("display: none; visibility: hidden;");

        aporteING = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteING");
        aporteING.setFilterStyle("display: none; visibility: hidden;");

        aporteRET = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteRET");
        aporteRET.setFilterStyle("display: none; visibility: hidden;");

        aporteTDA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTDA");
        aporteTDA.setFilterStyle("display: none; visibility: hidden;");

        aporteTAA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTAA");
        aporteTAA.setFilterStyle("display: none; visibility: hidden;");

        aporteVSP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVSP");
        aporteVSP.setFilterStyle("display: none; visibility: hidden;");

        aporteVTE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVTE");
        aporteVTE.setFilterStyle("display: none; visibility: hidden;");

        aporteVST = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVST");
        aporteVST.setFilterStyle("display: none; visibility: hidden;");

        aporteSLN = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSLN");
        aporteSLN.setFilterStyle("display: none; visibility: hidden;");

        aporteIGE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIGE");
        aporteIGE.setFilterStyle("display: none; visibility: hidden;");

        aporteLMA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteLMA");
        aporteLMA.setFilterStyle("display: none; visibility: hidden;");

        aporteVAC = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVAC");
        aporteVAC.setFilterStyle("display: none; visibility: hidden;");

        aporteAVP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVP");
        aporteAVP.setFilterStyle("display: none; visibility: hidden;");

        aporteVCT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVCT");
        aporteVCT.setFilterStyle("display: none; visibility: hidden;");

        aporteIRP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIRP");
        aporteIRP.setFilterStyle("display: none; visibility: hidden;");

        aporteSUS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSUS");
        aporteSUS.setFilterStyle("display: none; visibility: hidden;");

        aporteINTE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteINTE");
        aporteINTE.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaEPS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaEPS");
        aporteTarifaEPS.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaAAFP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaAAFP");
        aporteTarifaAAFP.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaACTT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaACTT");
        aporteTarifaACTT.setFilterStyle("display: none; visibility: hidden;");

        aporteCodigoCTT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoCTT");
        aporteCodigoCTT.setFilterStyle("display: none; visibility: hidden;");

        aporteAVPEValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVPEValor");
        aporteAVPEValor.setFilterStyle("display: none; visibility: hidden;");

        aporteAVPPValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVPPValor");
        aporteAVPPValor.setFilterStyle("display: none; visibility: hidden;");

        aporteRETCONTAValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteRETCONTAValor");
        aporteRETCONTAValor.setFilterStyle("display: none; visibility: hidden;");

        aporteCodigoNEPS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoNEPS");
        aporteCodigoNEPS.setFilterStyle("display: none; visibility: hidden;");

        aporteCodigoNAFP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoNAFP");
        aporteCodigoNAFP.setFilterStyle("display: none; visibility: hidden;");

        aporteEGValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEGValor");
        aporteEGValor.setFilterStyle("display: none; visibility: hidden;");

        aporteEGAutorizacion = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEGAutorizacion");
        aporteEGAutorizacion.setFilterStyle("display: none; visibility: hidden;");

        aporteMaternidadValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMaternidadValor");
        aporteMaternidadValor.setFilterStyle("display: none; visibility: hidden;");

        aporteMaternidadAuto = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMaternidadAuto");
        aporteMaternidadAuto.setFilterStyle("display: none; visibility: hidden;");

        aporteUPCValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteUPCValor");
        aporteUPCValor.setFilterStyle("display: none; visibility: hidden;");

        aporteTipo = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipo");
        aporteTipo.setFilterStyle("display: none; visibility: hidden;");

        aporteTipoPensionado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoPensionado");
        aporteTipoPensionado.setFilterStyle("display: none; visibility: hidden;");

        aportePensionCompartida = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aportePensionCompartida");
        aportePensionCompartida.setFilterStyle("display: none; visibility: hidden;");

        aporteExtranjero = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteExtranjero");
        aporteExtranjero.setFilterStyle("display: none; visibility: hidden;");

        aporteFechaIngreso = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteFechaIngreso");
        aporteFechaIngreso.setFilterStyle("display: none; visibility: hidden;");

        aporteSubTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubTipoCotizante");
        aporteSubTipoCotizante.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaCaja = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaCaja");
        aporteTarifaCaja.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaSena = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaSena");
        aporteTarifaSena.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaICBF = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaICBF");
        aporteTarifaICBF.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaESAP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaESAP");
        aporteTarifaESAP.setFilterStyle("display: none; visibility: hidden;");

        aporteTarifaMEN = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaMEN");
        aporteTarifaMEN.setFilterStyle("display: none; visibility: hidden;");

        RequestContext.getCurrentInstance().update("form:datosAporteEntidad");
    }

    public void activarFiltradoAporteEntidad() {
        FacesContext c = FacesContext.getCurrentInstance();
        altoTablaAporte = "158";

        aporteCodigoEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoEmpleado");
        aporteCodigoEmpleado.setFilterStyle("width: 75px");

        aporteAno = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAno");
        aporteAno.setFilterStyle("width: 20px");

        aporteMes = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMes");
        aporteMes.setFilterStyle("width: 20px");

        aporteNombreEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteNombreEmpleado");
        aporteNombreEmpleado.setFilterStyle("width: 80px");

        aporteNIT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteNIT");
        aporteNIT.setFilterStyle("width: 50px");

        aporteTercero = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTercero");
        aporteTercero.setFilterStyle("width: 90px");

        aporteTipoEntidad = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoEntidad");
        aporteTipoEntidad.setFilterStyle("width: 80px");

        aporteEmpleado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEmpleado");
        aporteEmpleado.setFilterStyle("width: 50px");

        aporteEmpleador = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEmpleador");
        aporteEmpleador.setFilterStyle("width: 50px");

        aporteAjustePatronal = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAjustePatronal");
        aporteAjustePatronal.setFilterStyle("width: 50px");

        aporteSolidaridadl = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSolidaridadl");
        aporteSolidaridadl.setFilterStyle("width: 50px");

        aporteSubSistencia = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubSistencia");
        aporteSubSistencia.setFilterStyle("width: 50px");

        aporteSubsPensionados = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubsPensionados");
        aporteSubsPensionados.setFilterStyle("width: 90px");

        aporteSalarioBasico = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSalarioBasico");
        aporteSalarioBasico.setFilterStyle("width: 50px");

        aporteIBC = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIBC");
        aporteIBC.setFilterStyle("width: 50px");

        aporteIBCReferencia = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIBCReferencia");
        aporteIBCReferencia.setFilterStyle("width: 50px");

        aporteDiasCotizados = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteDiasCotizados");
        aporteDiasCotizados.setFilterStyle("width: 50px");

        aporteTipoAportante = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoAportante");
        aporteTipoAportante.setFilterStyle("width: 50px");

        aporteING = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteING");
        aporteING.setFilterStyle("width: 15px");

        aporteRET = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteRET");
        aporteRET.setFilterStyle("width: 15px");

        aporteTDA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTDA");
        aporteTDA.setFilterStyle("width: 15px");

        aporteTAA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTAA");
        aporteTAA.setFilterStyle("width: 15px");

        aporteVSP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVSP");
        aporteVSP.setFilterStyle("width: 15px");

        aporteVTE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVTE");
        aporteVTE.setFilterStyle("width: 15px");

        aporteVST = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVST");
        aporteVST.setFilterStyle("width: 15px");

        aporteSLN = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSLN");
        aporteSLN.setFilterStyle("width: 15px");

        aporteIGE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIGE");
        aporteIGE.setFilterStyle("width: 15px");

        aporteLMA = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteLMA");
        aporteLMA.setFilterStyle("width: 15px");

        aporteVAC = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVAC");
        aporteVAC.setFilterStyle("width: 15px");

        aporteAVP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVP");
        aporteAVP.setFilterStyle("width: 15px");

        aporteVCT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteVCT");
        aporteVCT.setFilterStyle("width: 15px");

        aporteIRP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteIRP");
        aporteIRP.setFilterStyle("width: 15px");

        aporteSUS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSUS");
        aporteSUS.setFilterStyle("width: 15px");

        aporteINTE = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteINTE");
        aporteINTE.setFilterStyle("width: 15px");

        aporteTarifaEPS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaEPS");
        aporteTarifaEPS.setFilterStyle("width: 40px");

        aporteTarifaAAFP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaAAFP");
        aporteTarifaAAFP.setFilterStyle("width: 40px");

        aporteTarifaACTT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaACTT");
        aporteTarifaACTT.setFilterStyle("width: 40px");

        aporteCodigoCTT = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoCTT");
        aporteCodigoCTT.setFilterStyle("width: 40px");

        aporteAVPEValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVPEValor");
        aporteAVPEValor.setFilterStyle("width: 50px");

        aporteAVPPValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteAVPPValor");
        aporteAVPPValor.setFilterStyle("width: 50px");

        aporteRETCONTAValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteRETCONTAValor");
        aporteRETCONTAValor.setFilterStyle("width: 50px");

        aporteCodigoNEPS = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoNEPS");
        aporteCodigoNEPS.setFilterStyle("width: 50px");

        aporteCodigoNAFP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteCodigoNAFP");
        aporteCodigoNAFP.setFilterStyle("width: 50px");

        aporteEGValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEGValor");
        aporteEGValor.setFilterStyle("width: 30px");

        aporteEGAutorizacion = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteEGAutorizacion");
        aporteEGAutorizacion.setFilterStyle("width: 80px");

        aporteMaternidadValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMaternidadValor");
        aporteMaternidadValor.setFilterStyle("width: 70px");

        aporteMaternidadAuto = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteMaternidadAuto");
        aporteMaternidadAuto.setFilterStyle("width: 110px");

        aporteUPCValor = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteUPCValor");
        aporteUPCValor.setFilterStyle("width: 40px");

        aporteTipo = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipo");
        aporteTipo.setFilterStyle("width: 20px");

        aporteTipoPensionado = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTipoPensionado");
        aporteTipoPensionado.setFilterStyle("width: 15px");

        aportePensionCompartida = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aportePensionCompartida");
        aportePensionCompartida.setFilterStyle("width: 15px");

        aporteExtranjero = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteExtranjero");
        aporteExtranjero.setFilterStyle("width: 15px");

        aporteFechaIngreso = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteFechaIngreso");
        aporteFechaIngreso.setFilterStyle("width: 50px");

        aporteSubTipoCotizante = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteSubTipoCotizante");
        aporteSubTipoCotizante.setFilterStyle("width: 110px");

        aporteTarifaCaja = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaCaja");
        aporteTarifaCaja.setFilterStyle("width: 50px");

        aporteTarifaSena = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaSena");
        aporteTarifaSena.setFilterStyle("width: 50px");

        aporteTarifaICBF = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaICBF");
        aporteTarifaICBF.setFilterStyle("width: 50px");

        aporteTarifaESAP = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaESAP");
        aporteTarifaESAP.setFilterStyle("width: 50px");

        aporteTarifaMEN = (Column) c.getViewRoot().findComponent("form:datosAporteEntidad:aporteTarifaMEN");
        aporteTarifaMEN.setFilterStyle("width: 50px");

        RequestContext.getCurrentInstance().update("form:datosAporteEntidad");

    }

    public void organizarTablas() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tablaInferiorIzquierda");
        context.update("form:tablaInferiorDerecha");
    }

    public void salir() {
        if (bandera == 1) {
            altoTabla = "70";
            FacesContext c = FacesContext.getCurrentInstance();
            parametroAno = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroAno");
            parametroAno.setFilterStyle("display: none; visibility: hidden;");
            parametroTipoTrabajador = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroTipoTrabajador");
            parametroTipoTrabajador.setFilterStyle("display: none; visibility: hidden;");
            parametroEmpresa = (Column) c.getViewRoot().findComponent("form:datosParametroAuto:parametroEmpresa");
            parametroEmpresa.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosParametroAuto");
            bandera = 0;
            filtrarListaParametrosAutoliq = null;
            tipoLista = 0;
        }
        if (banderaAporte == 1) {
            desactivarFiltradoAporteEntidad();
            banderaAporte = 0;
            filtrarListaAportesEntidades = null;
            tipoListaAporte = 0;
        }
        listParametrosAutoliqBorrar.clear();
        listParametrosAutoliqCrear.clear();
        listParametrosAutoliqModificar.clear();
        //
        listAportesEntidadesBorrar.clear();
        listAportesEntidadesModificar.clear();
        //
        index = -1;
        activoBtnsPaginas = true;
        indexAporte = -1;
        secRegistro = null;
        secRegistroAporte = null;
        k = 0;
        listaParametrosAutoliq = null;
        listaAportesEntidades = null;
        guardado = true;
        cambiosParametro = false;
        cambiosAporte = false;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    public void asignarIndex(Integer indice, int LND, int dialogo) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dialogo == 1) {
            context.update("formularioDialogos:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (dialogo == 2) {
            context.update("formularioDialogos:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
    }

    public void asignarIndexAporte(Integer indice, int LND, int dialogo) {
        indexAporte = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        }
        if (dialogo == 1) {
            context.update("formularioDialogos:EmpleadoDialogo");
            context.execute("EmpleadoDialogo.show()");
        }
        if (dialogo == 2) {
            context.update("formularioDialogos:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        }
        if (dialogo == 3) {
            context.update("formularioDialogos:TipoEntidadDialogo");
            context.execute("TipoEntidadDialogo.show()");
        }
    }

    public void actualizarTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaParametrosAutoliq.get(index).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listParametrosAutoliqCrear.contains(listaParametrosAutoliq.get(index))) {
                    if (listParametrosAutoliqModificar.isEmpty()) {
                        listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(index));
                    } else if (!listParametrosAutoliqModificar.contains(listaParametrosAutoliq.get(index))) {
                        listParametrosAutoliqModificar.add(listaParametrosAutoliq.get(index));
                    }
                }
            } else {
                filtrarListaParametrosAutoliq.get(index).setTipotrabajador(tipoTrabajadorSeleccionado);
                if (!listParametrosAutoliqCrear.contains(filtrarListaParametrosAutoliq.get(index))) {
                    if (listParametrosAutoliqModificar.isEmpty()) {
                        listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(index));
                    } else if (!listParametrosAutoliqModificar.contains(filtrarListaParametrosAutoliq.get(index))) {
                        listParametrosAutoliqModificar.add(filtrarListaParametrosAutoliq.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndex = true;
            cambiosParametro = true;
            context.update("form:datosParametroAuto");
        } else if (tipoActualizacion == 1) {
            nuevoParametro.setTipotrabajador(tipoTrabajadorSeleccionado);
            context.update("formularioDialogos:nuevaTipoTrabajadorParametro");
        } else if (tipoActualizacion == 2) {
            duplicarParametro.setTipotrabajador(tipoTrabajadorSeleccionado);
            context.update("formularioDialogos:duplicarTipoTrabajadorParametro");
        }
        filtrarLovTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        aceptar = true;
        index = -1;

        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("formularioDialogos:TipoTrabajadorDialogo");
        context.update("formularioDialogos:lovTipoTrabajador");
        context.update("formularioDialogos:aceptarTT");
        context.reset("formularioDialogos:lovTipoTrabajador:globalFilter");
        context.execute("TipoTrabajadorDialogo.hide()");
    }

    public void cancelarCambioTipoTrabajador() {
        filtrarLovTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = new TiposTrabajadores();
        aceptar = true;
        index = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 1) {
            nuevoParametro.setEmpresa(empresaSeleccionada);
            context.update("formularioDialogos:nuevaEmpresaParametro");
        } else if (tipoActualizacion == 2) {
            duplicarParametro.setEmpresa(empresaSeleccionada);
            context.update("formularioDialogos:duplicarEmpresaParametro");
        }
        filtrarLovEmpresas = null;
        empresaSeleccionada = new Empresas();
        aceptar = true;
        index = -1;
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("formularioDialogos:EmpresaDialogo");
        context.update("formularioDialogos:lovEmpresa");
        context.update("formularioDialogos:aceptarE");
        context.reset("formularioDialogos:lovEmpresa:globalFilter");
        context.execute("EmpresaDialogo.hide()");
    }

    public void cancelarCambioEmpresa() {
        filtrarLovEmpresas = null;
        empresaSeleccionada = new Empresas();
        aceptar = true;
        index = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarEmpleado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaAporte == 0) {
                listaAportesEntidades.get(indexAporte).setEmpleado(empleadoSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(listaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                }
            } else {
                filtrarListaAportesEntidades.get(indexAporte).setEmpleado(empleadoSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndexAporte = true;
            cambiosAporte = true;
            context.update("form:datosAporteEntidad");
        }
        filtrarLovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        aceptar = true;
        indexAporte = -1;
        secRegistroAporte = null;
        tipoActualizacion = -1;
        context.update("formularioDialogos:EmpleadoDialogo");
        context.update("formularioDialogos:lovEmpleado");
        context.update("formularioDialogos:aceptarEMPL");
        context.reset("formularioDialogos:lovEmpleado:globalFilter");
        context.execute("EmpleadoDialogo.hide()");
    }

    public void cancelarCambioEmpleado() {
        filtrarLovEmpleados = null;
        empleadoSeleccionado = new Empleados();
        aceptar = true;
        indexAporte = -1;

        secRegistroAporte = null;
        tipoActualizacion = -1;
        permitirIndexAporte = true;
    }

    public void actualizarTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaAporte == 0) {
                listaAportesEntidades.get(indexAporte).setTerceroRegistro(terceroSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(listaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                }
            } else {
                filtrarListaAportesEntidades.get(indexAporte).setTerceroRegistro(terceroSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndexAporte = true;
            cambiosAporte = true;
            context.update("form:datosAporteEntidad");
        }
        filtrarLovTerceros = null;
        terceroSeleccionado = new Terceros();
        aceptar = true;
        indexAporte = -1;
        secRegistroAporte = null;
        tipoActualizacion = -1;
        context.update("formularioDialogos:TerceroDialogo");
        context.update("formularioDialogos:lovTercero");
        context.update("formularioDialogos:aceptarT");
        context.reset("formularioDialogos:lovTercero:globalFilter");
        context.execute("TerceroDialogo.hide()");
    }

    public void cancelarCambioTercero() {
        filtrarLovTerceros = null;
        terceroSeleccionado = new Terceros();
        aceptar = true;
        indexAporte = -1;
        secRegistroAporte = null;
        tipoActualizacion = -1;
        permitirIndexAporte = true;
    }

    public void actualizarTipoEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaAporte == 0) {
                listaAportesEntidades.get(indexAporte).setTipoentidad(tipoEntidadSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(listaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(listaAportesEntidades.get(indexAporte));
                }
            } else {
                filtrarListaAportesEntidades.get(indexAporte).setTipoentidad(tipoEntidadSeleccionado);
                if (listAportesEntidadesModificar.isEmpty()) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                } else if (!listAportesEntidadesModificar.contains(filtrarListaAportesEntidades.get(indexAporte))) {
                    listAportesEntidadesModificar.add(filtrarListaAportesEntidades.get(indexAporte));
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndexAporte = true;
            cambiosAporte = true;
            context.update("form:datosAporteEntidad");
        }
        filtrarLovTiposEntidades = null;
        tipoEntidadSeleccionado = new TiposEntidades();
        aceptar = true;
        indexAporte = -1;
        secRegistroAporte = null;
        tipoActualizacion = -1;
        context.update("formularioDialogos:TipoEntidadDialogo");
        context.update("formularioDialogos:lovTipoEntidad");
        context.update("formularioDialogos:aceptarTE");
        context.reset("formularioDialogos:lovTipoEntidad:globalFilter");
        context.execute("TipoEntidadDialogo.hide()");
    }

    public void cancelarCambioTipoEntidad() {
        filtrarLovTiposEntidades = null;
        tipoEntidadSeleccionado = new TiposEntidades();
        aceptar = true;
        indexAporte = -1;
        secRegistroAporte = null;
        tipoActualizacion = -1;
        permitirIndexAporte = true;
    }

    public void dispararDialogoBuscar() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            context.update("formularioDialogos:BuscarAporteDialogo");
            context.execute("BuscarAporteDialogo.show()");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    public void actualizarAporteEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaAporte == 1) {
            desactivarFiltradoAporteEntidad();
            banderaAporte = 0;
            filtrarListaAportesEntidades = null;
            tipoListaAporte = 0;
        }
        listaAportesEntidades.clear();
        listaAportesEntidades.add(aporteEntidadSeleccionado);
        filtrarLovAportesEntidades = null;
        aporteEntidadSeleccionado = new AportesEntidades();
        aceptar = true;
        index = -1;
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;

        infoRegistroParametro = "Cantidad de registros : " + listaParametrosAutoliq.size();

        context.update("form:infoRegistroParametro");
        context.update("form:datosAporteEntidad");
        
        context.update("formularioDialogos:BuscarAporteDialogo");
        context.update("formularioDialogos:lovBuscarAporte");
        context.update("formularioDialogos:aceptarBA");
        context.reset("formularioDialogos:lovBuscarAporte:globalFilter");
        context.execute("BuscarAporteDialogo.hide()");
    }

    public void cancelarCambioAporteEntidad() {
        filtrarLovEmpresas = null;
        empresaSeleccionada = new Empresas();
        aceptar = true;
        index = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 2) {
                context.update("formularioDialogos:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexAporte >= 0) {
            if (cualCeldaAporte == 3) {
                context.update("formularioDialogos:EmpleadoDialogo");
                context.execute("EmpleadoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaAporte == 4) {
                context.update("formularioDialogos:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaAporte == 6) {
                context.update("formularioDialogos:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public String exportXMLTabla() {
        String tabla = "";
        if (index >= 0) {
            tabla = ":formExportar:datosParametroAutoExportar";
        }
        if (indexAporte >= 0) {
            tabla = ":formExportar:datosAporteEntidadExportar";
        }
        return tabla;
    }

    public String exportXMLNombreArchivo() {
        String nombre = "";
        if (index >= 0) {
            nombre = "ParametrosAutoliquidacion_XML";
        }
        if (indexAporte >= 0) {
            nombre = "AportesEntidades_XML";
        }
        return nombre;
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosParametroAutoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ParametrosAutoliquidacion_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        RequestContext context2 = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context2.update("form:btn2");
        context2.update("form:btn3");
        context2.update("form:btn4");
        context2.update("form:btn5");
        context2.update("form:btn7");
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosParametroAutoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ParametrosAutoliquidacion_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        RequestContext context2 = RequestContext.getCurrentInstance();
        activoBtnsPaginas = true;
        context2.update("form:btn2");
        context2.update("form:btn3");
        context2.update("form:btn4");
        context2.update("form:btn5");
        context2.update("form:btn7");
        secRegistro = null;
    }

    public void exportPDF_AE() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosAporteEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "AportesEntidades_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexAporte = -1;
        secRegistroAporte = null;
    }

    public void exportXLS_AE() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosAporteEntidadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "AportesEntidades_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexAporte = -1;
        secRegistroAporte = null;
    }

    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
        if (indexAporte >= 0) {
            if (tipoListaAporte == 0) {
                tipoListaAporte = 1;
            }
        }
    }

    public void verificarRastroTablas() {
        if (index >= 0) {
            verificarRastro();
        }
        if (indexAporte >= 0) {
            verificarRastroAporteEntidad();
        }
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaParametrosAutoliq != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "PARAMETROSAUTOLIQ");
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
            if (administrarRastros.verificarHistoricosTabla("PARAMETROSAUTOLIQ")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
        activoBtnsPaginas = true;
        context.update("form:btn2");
        context.update("form:btn3");
        context.update("form:btn4");
        context.update("form:btn5");
        context.update("form:btn7");
    }

    public void verificarRastroAporteEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaAportesEntidades != null) {
            if (secRegistroAporte != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroAporte, "APORTESENTIDADES");
                backUpSecRegistroAporte = secRegistroAporte;
                secRegistroAporte = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDBAporte.show()");
                } else if (resultado == 2) {
                    context.execute("confirmarRastroAporte.show()");
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
            if (administrarRastros.verificarHistoricosTabla("APORTESENTIDADES")) {
                context.execute("confirmarRastroHistoricoAporte.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexAporte = -1;
    }

    //GET - SET//
    public List<ParametrosAutoliq> getListaParametrosAutoliq() {
        try {
            if (listaParametrosAutoliq == null) {
                listaParametrosAutoliq = administrarParametroAutoliq.consultarParametrosAutoliq();
                if (listaParametrosAutoliq != null) {
                    for (int i = 0; i < listaParametrosAutoliq.size(); i++) {
                        if (listaParametrosAutoliq.get(i).getTipotrabajador() == null) {
                            listaParametrosAutoliq.get(i).setTipotrabajador(new TiposTrabajadores());
                        }
                    }
                }
            }
            return listaParametrosAutoliq;
        } catch (Exception e) {
            System.out.println("Error !!!!!!!!! getListaParametrosAutoliq : " + e.toString());
            return null;
        }
    }

    public void setListaParametrosAutoliq(List<ParametrosAutoliq> listaParametrosAutoliq) {
        this.listaParametrosAutoliq = listaParametrosAutoliq;
    }

    public List<ParametrosAutoliq> getFiltrarListaParametrosAutoliq() {
        return filtrarListaParametrosAutoliq;
    }

    public void setFiltrarListaParametrosAutoliq(List<ParametrosAutoliq> filtrarListaParametrosAutoliq) {
        this.filtrarListaParametrosAutoliq = filtrarListaParametrosAutoliq;
    }

    public ParametrosAutoliq getParametroTablaSeleccionado() {
        getListaParametrosAutoliq();
        if (listaParametrosAutoliq != null) {
            int tam = listaParametrosAutoliq.size();
            if (tam > 0) {
                parametroTablaSeleccionado = listaParametrosAutoliq.get(0);
            }
        }
        return parametroTablaSeleccionado;
    }

    public void setParametroTablaSeleccionado(ParametrosAutoliq parametroTablaSeleccionado) {
        this.parametroTablaSeleccionado = parametroTablaSeleccionado;
    }

    public ParametrosAutoliq getNuevoParametro() {
        return nuevoParametro;
    }

    public void setNuevoParametro(ParametrosAutoliq nuevoParametro) {
        this.nuevoParametro = nuevoParametro;
    }

    public ParametrosAutoliq getEditarParametro() {
        return editarParametro;
    }

    public void setEditarParametro(ParametrosAutoliq editarParametro) {
        this.editarParametro = editarParametro;
    }

    public ParametrosAutoliq getDuplicarParametro() {
        return duplicarParametro;
    }

    public void setDuplicarParametro(ParametrosAutoliq duplicarParametro) {
        this.duplicarParametro = duplicarParametro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        lovTiposTrabajadores = administrarParametroAutoliq.lovTiposTrabajadores();
        return lovTiposTrabajadores;
    }

    public void setLovTiposTrabajadores(List<TiposTrabajadores> lovTiposTrabajadores) {
        this.lovTiposTrabajadores = lovTiposTrabajadores;
    }

    public List<TiposTrabajadores> getFiltrarLovTiposTrabajadores() {
        return filtrarLovTiposTrabajadores;
    }

    public void setFiltrarLovTiposTrabajadores(List<TiposTrabajadores> filtrarLovTiposTrabajadores) {
        this.filtrarLovTiposTrabajadores = filtrarLovTiposTrabajadores;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
    }

    public String getInfoRegistroTipoTrabajador() {
        getLovTiposTrabajadores();
        if (lovTiposTrabajadores != null) {
            infoRegistroTipoTrabajador = "Cantidad de registros : " + lovTiposTrabajadores.size();
        } else {
            infoRegistroTipoTrabajador = "Cantidad de registros : 0";
        }
        return infoRegistroTipoTrabajador;
    }

    public void setInfoRegistroTipoTrabajador(String infoRegistroTipoTrabajador) {
        this.infoRegistroTipoTrabajador = infoRegistroTipoTrabajador;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public List<Empresas> getLovEmpresas() {
        lovEmpresas = administrarParametroAutoliq.lovEmpresas();
        return lovEmpresas;
    }

    public void setLovEmpresas(List<Empresas> lovEmpresas) {
        this.lovEmpresas = lovEmpresas;
    }

    public List<Empresas> getFiltrarLovEmpresas() {
        return filtrarLovEmpresas;
    }

    public void setFiltrarLovEmpresas(List<Empresas> filtrarLovEmpresas) {
        this.filtrarLovEmpresas = filtrarLovEmpresas;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public String getInfoRegistroEmpresa() {
        getLovEmpresas();
        if (lovEmpresas != null) {
            infoRegistroEmpresa = "Cantidad de regtistros : " + lovEmpresas.size();
        } else {
            infoRegistroEmpresa = "Cantidad de registros : 0";
        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public List<AportesEntidades> getListaAportesEntidades() {
        try {
            if (listaAportesEntidades == null) {
                if (index >= 0) {
                    ParametrosAutoliq aux = null;
                    if (tipoLista == 0) {
                        aux = listaParametrosAutoliq.get(index);
                    } else {
                        aux = filtrarListaParametrosAutoliq.get(index);
                    }
                    listaAportesEntidades = administrarParametroAutoliq.consultarAportesEntidadesPorParametroAutoliq(aux.getEmpresa().getSecuencia(), aux.getMes(), aux.getAno());
                    if (listaAportesEntidades != null) {
                        for (int i = 0; i < listaAportesEntidades.size(); i++) {
                            if (listaAportesEntidades.get(i).getTerceroRegistro() == null) {
                                listaAportesEntidades.get(i).setTerceroRegistro(null);
                            }
                        }
                    }
                }
            }
            return listaAportesEntidades;
        } catch (Exception e) {
            System.out.println("Error !!!!!!!!!!! getListaAportesEntidades : " + e.toString());
            return null;
        }
    }

    public void setListaAportesEntidades(List<AportesEntidades> listaAportesEntidades) {
        this.listaAportesEntidades = listaAportesEntidades;
    }

    public List<AportesEntidades> getFiltrarListaAportesEntidades() {
        return filtrarListaAportesEntidades;
    }

    public void setFiltrarListaAportesEntidades(List<AportesEntidades> filtrarListaAportesEntidades) {
        this.filtrarListaAportesEntidades = filtrarListaAportesEntidades;
    }

    public AportesEntidades getAporteTablaSeleccionado() {
        getListaAportesEntidades();
        if (listaAportesEntidades != null) {
            int tam = listaAportesEntidades.size();
            if (tam > 0) {
                aporteTablaSeleccionado = listaAportesEntidades.get(0);
            }
        }
        return aporteTablaSeleccionado;
    }

    public void setAporteTablaSeleccionado(AportesEntidades aporteTablaSeleccionado) {
        this.aporteTablaSeleccionado = aporteTablaSeleccionado;
    }

    public AportesEntidades getEditarAporteEntidad() {
        return editarAporteEntidad;
    }

    public void setEditarAporteEntidad(AportesEntidades editarAporteEntidad) {
        this.editarAporteEntidad = editarAporteEntidad;
    }

    public BigInteger getBackUpSecRegistroAporte() {
        return backUpSecRegistroAporte;
    }

    public void setBackUpSecRegistroAporte(BigInteger backUpSecRegistroAporte) {
        this.backUpSecRegistroAporte = backUpSecRegistroAporte;
    }

    public List<Empleados> getLovEmpleados() {
        lovEmpleados = administrarParametroAutoliq.lovEmpleados();
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

    public List<Terceros> getLovTerceros() {
        lovTerceros = administrarParametroAutoliq.lovTerceros();
        return lovTerceros;
    }

    public void setLovTerceros(List<Terceros> lovTerceros) {
        this.lovTerceros = lovTerceros;
    }

    public List<Terceros> getFiltrarLovTerceros() {
        return filtrarLovTerceros;
    }

    public void setFiltrarLovTerceros(List<Terceros> filtrarLovTerceros) {
        this.filtrarLovTerceros = filtrarLovTerceros;
    }

    public Terceros getTerceroSeleccionado() {
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public String getInfoRegistroTercero() {
        getLovTerceros();
        if (lovTerceros != null) {
            infoRegistroTercero = "Cantidad de registros : " + lovTerceros.size();
        } else {
            infoRegistroTercero = "Cantidad de registros : 0";
        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public List<TiposEntidades> getLovTiposEntidades() {
        lovTiposEntidades = administrarParametroAutoliq.lovTiposEntidades();
        return lovTiposEntidades;
    }

    public void setLovTiposEntidades(List<TiposEntidades> lovTiposEntidades) {
        this.lovTiposEntidades = lovTiposEntidades;
    }

    public List<TiposEntidades> getFiltrarLovTiposEntidades() {
        return filtrarLovTiposEntidades;
    }

    public void setFiltrarLovTiposEntidades(List<TiposEntidades> filtrarLovTiposEntidades) {
        this.filtrarLovTiposEntidades = filtrarLovTiposEntidades;
    }

    public TiposEntidades getTipoEntidadSeleccionado() {
        return tipoEntidadSeleccionado;
    }

    public void setTipoEntidadSeleccionado(TiposEntidades tipoEntidadSeleccionado) {
        this.tipoEntidadSeleccionado = tipoEntidadSeleccionado;
    }

    public String getInfoRegistroTipoEntidad() {
        getLovTiposEntidades();
        if (lovTiposEntidades != null) {
            infoRegistroTipoEntidad = "Cantidad de registros : " + lovTiposEntidades.size();
        } else {
            infoRegistroTipoEntidad = "Cantidad de registros : 0";
        }
        return infoRegistroTipoEntidad;
    }

    public void setInfoRegistroTipoEntidad(String infoRegistroTipoEntidad) {
        this.infoRegistroTipoEntidad = infoRegistroTipoEntidad;
    }

    public String getAltoTablaAporte() {
        return altoTablaAporte;
    }

    public void setAltoTablaAporte(String altoTablaAporte) {
        this.altoTablaAporte = altoTablaAporte;
    }

    public List<AportesEntidades> getLovAportesEntidades() {
        if (lovAportesEntidades == null) {
            if (index >= 0) {
                ParametrosAutoliq aux = null;
                if (tipoLista == 0) {
                    aux = listaParametrosAutoliq.get(index);
                } else {
                    aux = filtrarListaParametrosAutoliq.get(index);
                }
                lovAportesEntidades = administrarParametroAutoliq.consultarAportesEntidadesPorParametroAutoliq(aux.getEmpresa().getSecuencia(), aux.getMes(), aux.getAno());
                if (lovAportesEntidades != null) {
                    for (int i = 0; i < lovAportesEntidades.size(); i++) {
                        if (lovAportesEntidades.get(i).getTercero() == null) {
                            lovAportesEntidades.get(i).setTerceroRegistro(new Terceros());
                        }
                    }
                }
            }
        }
        return lovAportesEntidades;
    }

    public void setLovAportesEntidades(List<AportesEntidades> lovAportesEntidades) {
        this.lovAportesEntidades = lovAportesEntidades;
    }

    public List<AportesEntidades> getFiltrarLovAportesEntidades() {
        return filtrarLovAportesEntidades;
    }

    public void setFiltrarLovAportesEntidades(List<AportesEntidades> filtrarLovAportesEntidades) {
        this.filtrarLovAportesEntidades = filtrarLovAportesEntidades;
    }

    public AportesEntidades getAporteEntidadSeleccionado() {
        return aporteEntidadSeleccionado;
    }

    public void setAporteEntidadSeleccionado(AportesEntidades aporteEntidadSeleccionado) {
        this.aporteEntidadSeleccionado = aporteEntidadSeleccionado;
    }

    public String getInfoRegistroAporteEntidad() {
        getLovAportesEntidades();
        if (lovAportesEntidades != null) {
            infoRegistroAporteEntidad = "Cantidad de registros : " + lovAportesEntidades.size();
        } else {
            infoRegistroAporteEntidad = "Cantidad de registros : 0";
        }
        return infoRegistroAporteEntidad;
    }

    public void setInfoRegistroAporteEntidad(String infoRegistroAporteEntidad) {
        this.infoRegistroAporteEntidad = infoRegistroAporteEntidad;
    }

    public boolean isDisabledBuscar() {
        return disabledBuscar;
    }

    public void setDisabledBuscar(boolean disabledBuscar) {
        this.disabledBuscar = disabledBuscar;
    }

    public String getInfoRegistroParametro() {
        return infoRegistroParametro;
    }

    public void setInfoRegistroParametro(String infoRegistroParametro) {
        this.infoRegistroParametro = infoRegistroParametro;
    }

    public String getInfoRegistroAporte() {
        return infoRegistroAporte;
    }

    public void setInfoRegistroAporte(String infoRegistroAporte) {
        this.infoRegistroAporte = infoRegistroAporte;
    }

    public ParametrosEstructuras getParametroEstructura() {
        getUsuario();
        parametroEstructura = administrarParametroAutoliq.buscarParametroEstructura(usuario.getAlias());
        return parametroEstructura;
    }

    public void setParametroEstructura(ParametrosEstructuras parametroEstructura) {
        this.parametroEstructura = parametroEstructura;
    }

    public ParametrosInformes getParametroInforme() {
        getUsuario();
        parametroInforme = administrarParametroAutoliq.buscarParametroInforme(usuario.getAlias());
        return parametroInforme;
    }

    public void setParametroInforme(ParametrosInformes parametroInforme) {
        this.parametroInforme = parametroInforme;
    }

    public ActualUsuario getUsuario() {
        if (usuario == null) {
            usuario = administrarParametroAutoliq.obtenerActualUsuario();
        }
        return usuario;
    }

    public void setUsuario(ActualUsuario usuario) {
        this.usuario = usuario;
    }

    public boolean isActivoBtnsPaginas() {
        return activoBtnsPaginas;
    }

    public void setActivoBtnsPaginas(boolean activoBtnsPaginas) {
        this.activoBtnsPaginas = activoBtnsPaginas;
    }

}

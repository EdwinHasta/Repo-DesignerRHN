package Controlador;

import Entidades.ActualUsuario;
import Entidades.Empresas;
import Entidades.InterconSapBO;
import Entidades.InterconTotal;
import Entidades.ParametrosContables;
import Entidades.Procesos;
import Entidades.SolucionesNodos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarInterfaseContableSapBOInterface;
import InterfaceAdministrar.AdministrarInterfaseContableTotalInterface;
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
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlInterfaseContableSapBOV8 implements Serializable {

    @EJB
    AdministrarInterfaseContableSapBOInterface administrarInterfaseContableSapBO;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private ActualUsuario actualUsuarioBD;
    //
    private ParametrosContables parametroContableActual;
    private ParametrosContables nuevoParametroContable;
    private List<ParametrosContables> listaParametrosContables;
    private int indexParametroContable;
    private String auxParametroEmpresa, auxParametroProceso;
    private Date auxParametroFechaInicial, auxParametroFechaFinal;
    private boolean permitirIndexParametro;
    private boolean cambiosParametro;
    private List<ParametrosContables> listParametrosContablesBorrar;
    //
    private List<SolucionesNodos> listaGenerados;
    private List<SolucionesNodos> filtrarListaGenerados;
    private SolucionesNodos generadoTablaSeleccionado;
    private int indexGenerado, cualCeldaGenerado;
    private SolucionesNodos editarGenerado;
    private int tipoListaGenerada, banderaGenerado;
    //
    private List<InterconSapBO> listaInterconSapBO;
    private List<InterconSapBO> filtrarListaInterconSapBO;
    private InterconSapBO interconTablaSeleccionada;
    private int indexIntercon, cualCeldaIntercon;
    private InterconSapBO editarIntercon;
    private int tipoListaIntercon, banderaIntercon;

    private List<Empresas> lovEmpresas;
    private List<Empresas> filtrarLovEmpresas;
    private Empresas empresaSeleccionada;
    private String infoRegistroEmpresa;

    private List<Procesos> lovProcesos;
    private List<Procesos> filtrarLovProcesos;
    private Procesos procesoSeleccionado;
    private String infoRegistroProceso;
    //
    private String paginaAnterior;
    //
    private boolean guardado;
    private Date fechaDeParametro;
    private boolean aceptar;
    //
    private boolean estadoBtnArriba, estadoBtnAbajo;
    private int registroActual;
    //
    private boolean activarAgregar, activarOtros;
    private boolean activarEnviar, activarDeshacer;
    //
    private int tipoActualizacion;
    //
    private boolean modificacionParametro;
    //
    //
    private BigInteger secRegistro, backUpSecRegistro;
    //
    private int tipoPlano;
    //
    private String msnFechasActualizar;
    //
    private int totalCGenerado, totalDGenerado, totalDInter, totalCInter;
    //
    private String msnPaso1;

    private String altoTablaGenerada;
    private String altoTablaIntercon;

    public ControlInterfaseContableSapBOV8() {
        actualUsuarioBD = null;
        parametroContableActual = null;
        nuevoParametroContable = new ParametrosContables();
        listaParametrosContables = null;
        indexParametroContable = -1;
        permitirIndexParametro = true;
        cambiosParametro = false;
        listParametrosContablesBorrar = new ArrayList<ParametrosContables>();
        listaGenerados = null;
        generadoTablaSeleccionado = new SolucionesNodos();
        indexGenerado = -1;
        cualCeldaGenerado = -1;
        editarGenerado = new SolucionesNodos();
        tipoListaGenerada = 0;
        banderaGenerado = 0;
        listaInterconSapBO = null;
        interconTablaSeleccionada = new InterconSapBO();
        indexIntercon = -1;
        cualCeldaIntercon = -1;
        editarIntercon = new InterconSapBO();
        tipoListaIntercon = 0;
        banderaIntercon = 0;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarInterfaseContableSapBO.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlInterfaseContableSapBOV8: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void recibirPagina(String paginaAnt) {
        paginaAnterior = paginaAnt;
        actualUsuarioBD = null;
        getActualUsuarioBD();
        listaParametrosContables = null;
        getListaParametrosContables();
        parametroContableActual = null;
        getParametroContableActual();
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public void anteriorParametro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            if (registroActual > 0) {
                registroActual--;
                parametroContableActual = listaParametrosContables.get(registroActual);
                listaGenerados = null;
                listaInterconSapBO = null;
                activarEnviar = true;
                activarDeshacer = true;
                context.update("form:btnEnviar");
                context.update("form:btnDeshacer");
                context.update("form:PLANO");
                totalCGenerado = 0;
                totalDGenerado = 0;
                totalDInter = 0;
                totalCInter = 0;
                if (registroActual == 0) {
                    estadoBtnArriba = true;
                }
                if (registroActual < (listaParametrosContables.size() - 1)) {
                    estadoBtnAbajo = false;
                }
                activarEnviar = true;
                activarDeshacer = true;
                context.update("form:PanelTotal");
                context.update("form:btnArriba");
                context.update("form:btnAbajo");
            }
        } else {
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void siguienteParametro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            if (registroActual < (listaParametrosContables.size() - 1)) {
                registroActual++;
                parametroContableActual = listaParametrosContables.get(registroActual);
                listaGenerados = null;
                listaInterconSapBO = null;
                activarEnviar = true;
                activarDeshacer = true;
                context.update("form:btnEnviar");
                context.update("form:btnDeshacer");
                context.update("form:PLANO");
                totalCGenerado = 0;
                totalDGenerado = 0;
                totalDInter = 0;
                totalCInter = 0;
                if (registroActual > 0) {
                    estadoBtnArriba = false;
                }
                if (registroActual == (listaParametrosContables.size() - 1)) {
                    estadoBtnAbajo = true;
                }

                
                
                activarEnviar = true;
                activarDeshacer = true;
                context.update("form:PanelTotal");
                context.update("form:btnArriba");
                context.update("form:btnAbajo");
            }
        } else {
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }
    
    public void modificarParametroContable() {
        if (guardado == true) {
            guardado = false;
        }
        cambiosParametro = true;
        modificacionParametro = true;
        indexParametroContable = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }
    
    public void modificarParametroContable(String confirmarCambio, String valorConfirmar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (valorConfirmar.equals("EMPRESA")) {
            parametroContableActual.getEmpresaRegistro().setNombre(auxParametroEmpresa);
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(confirmarCambio.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroContableActual.setEmpresaRegistro(lovEmpresas.get(indiceUnicoElemento));
                parametroContableActual.setEmpresaCodigo(lovEmpresas.get(indiceUnicoElemento).getCodigo());
                lovEmpresas.clear();
                getLovEmpresas();
                if (guardado == true) {
                    guardado = false;
                }
                cambiosParametro = true;
                modificacionParametro = true;
                context.update("form:ACEPTAR");
                context.update("form:parametroEmpresa");
            } else {
                permitirIndexParametro = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        } else if (valorConfirmar.equals("PROCESO")) {
            if (!confirmarCambio.isEmpty()) {
                parametroContableActual.getProceso().setDescripcion(auxParametroProceso);
                for (int i = 0; i < lovProcesos.size(); i++) {
                    if (lovProcesos.get(i).getDescripcion().startsWith(confirmarCambio.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroContableActual.setProceso(lovProcesos.get(indiceUnicoElemento));
                    lovProcesos.clear();
                    getLovProcesos();
                    if (guardado == true) {
                        guardado = false;
                    }
                    cambiosParametro = true;
                    modificacionParametro = true;
                    context.update("form:ACEPTAR");
                    context.update("form:parametroProceso");
                } else {
                    permitirIndexParametro = false;
                    context.update("form:ProcesoDialogo");
                    context.execute("ProcesoDialogo.show()");
                }
            } else {
                parametroContableActual.setProceso(new Procesos());
                if (guardado == true) {
                    guardado = false;
                }
                cambiosParametro = true;
                modificacionParametro = true;
                context.update("form:ACEPTAR");
                context.update("form:parametroProceso");
            }
        }
    }

    public void modificarFechasParametro(int i) {
        if (parametroContableActual.getFechainicialcontabilizacion() != null && parametroContableActual.getFechafinalcontabilizacion() != null) {
            boolean validacion = validarFechaParametro(0);
            if (validacion == true) {
                cambiarIndiceParametro(i);
                modificarParametroContable();
            } else {
                parametroContableActual.setFechafinalcontabilizacion(auxParametroFechaFinal);
                parametroContableActual.setFechainicialcontabilizacion(auxParametroFechaInicial);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:panelParametro:parametroFechaFinal");
                context.update("form:panelParametro:parametroFechaInicial");
                context.execute("errorFechasParametro.show()");
            }
        } else {
            parametroContableActual.setFechafinalcontabilizacion(auxParametroFechaFinal);
            parametroContableActual.setFechainicialcontabilizacion(auxParametroFechaInicial);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:panelParametro:parametroFechaFinal");
            context.update("form:panelParametro:parametroFechaInicial");
            context.execute("errorFechasNull.show()");
        }
    }

    public boolean validarFechaParametro(int i) {
        fechaDeParametro = new Date();
        fechaDeParametro.setYear(0);
        fechaDeParametro.setMonth(1);
        fechaDeParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            if (parametroContableActual.getFechainicialcontabilizacion().after(fechaDeParametro) && parametroContableActual.getFechafinalcontabilizacion().after(fechaDeParametro)) {
                if (parametroContableActual.getFechafinalcontabilizacion().after(parametroContableActual.getFechainicialcontabilizacion())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoParametroContable.getFechainicialcontabilizacion().after(fechaDeParametro) && nuevoParametroContable.getFechafinalcontabilizacion().after(fechaDeParametro)) {
                if (nuevoParametroContable.getFechafinalcontabilizacion().after(nuevoParametroContable.getFechainicialcontabilizacion())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }
        return retorno;
    }
    
    public void posicionGenerado() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        indexGenerado = indice;
        cualCeldaGenerado = columna;
        indexParametroContable = -1;
        indexIntercon = -1;
    }

    public void posicionIntercon() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        indexIntercon = indice;
        cualCeldaIntercon = columna;
        indexParametroContable = -1;
        indexGenerado = -1;
        if (tipoListaIntercon == 0) {
            secRegistro = listaInterconSapBO.get(indexIntercon).getSecuencia();
        } else {
            secRegistro = filtrarListaInterconSapBO.get(indexIntercon).getSecuencia();
        }
    }

    public void cambiarIndiceParametro(int indice) {
        if (permitirIndexParametro == true) {
            indexParametroContable = indice;
            indexGenerado = -1;
            indexIntercon = -1;
            auxParametroEmpresa = parametroContableActual.getEmpresaRegistro().getNombre();
            auxParametroProceso = parametroContableActual.getProceso().getDescripcion();
            auxParametroFechaFinal = parametroContableActual.getFechafinalcontabilizacion();
            auxParametroFechaInicial = parametroContableActual.getFechainicialcontabilizacion();
        }
    }
    
    public void guardarSalir() {
        guardadoGeneral();
        salir();
    }

    public void cancelarSalir() {
        cancelarModificaciones();
        salir();
    }

    public void guardadoGeneral() {
        if (guardado == false) {
            if (cambiosParametro == true) {
                guardarCambiosParametro();
            }
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }

    public void guardarCambiosParametro() {
        try {
            if (modificacionParametro == true) {
                administrarInterfaseContableSapBO.modificarParametroContable(parametroContableActual);
                modificacionParametro = false;
            }
            if (!listParametrosContablesBorrar.isEmpty()) {
                for (int i = 0; i < listParametrosContablesBorrar.size(); i++) {
                    administrarInterfaseContableSapBO.borrarParametroContable(listParametrosContablesBorrar);
                }
                listParametrosContablesBorrar.clear();
            }
            listaParametrosContables = null;
            getListaParametrosContables();
            parametroContableActual = null;
            getParametroContableActual();
            cambiosParametro = false;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        } catch (Exception e) {
            System.out.println("Error guardarCambiosParametro Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Un error ha ocurrido en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        }
    }

    public void cancelarModificaciones() {        
        totalCGenerado = 0;
        totalDGenerado = 0;
        totalDInter = 0;
        totalCInter = 0;
        activarEnviar = true;
        activarDeshacer = true;
        modificacionParametro = false;
        aceptar = true;
        listParametrosContablesBorrar.clear();
        actualUsuarioBD = null;
        getActualUsuarioBD();
        listaParametrosContables = null;
        getListaParametrosContables();
        parametroContableActual = null;
        listaGenerados = null;
        listaInterconSapBO = null;
        getParametroContableActual();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:btnEnviar");
        context.update("form:btnDeshacer");
        context.update("form:PLANO");
        context.update("form:PanelTotal");
        cambiosParametro = false;
        guardado = true;
        indexParametroContable = -1;
        indexGenerado = -1;
        indexIntercon = -1;
        context.update("form:ACEPTAR");
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexParametroContable >= 0) {
            if (indexParametroContable == 0) {
                context.update("formularioDialogos:editarEmpresaParametro");
                context.execute("editarEmpresaParametro.show()");
                indexParametroContable = -1;
            } else if (indexParametroContable == 1) {
                context.update("formularioDialogos:editarDocContableParametro");
                context.execute("editarDocContableParametro.show()");
                indexParametroContable = -1;
            } else if (indexParametroContable == 2) {
                context.update("formularioDialogos:editarProcesoParametro");
                context.execute("editarProcesoParametro.show()");
                indexParametroContable = -1;
            } else if (indexParametroContable == 3) {
                context.update("formularioDialogos:editarFechaInicialParametro");
                context.execute("editarFechaInicialParametro.show()");
                indexParametroContable = -1;
            } else if (indexParametroContable == 4) {
                context.update("formularioDialogos:editarFechaFinalParametro");
                context.execute("editarFechaFinalParametro.show()");
                indexParametroContable = -1;
            }
        }
        if (indexGenerado >= 0) {
            if (tipoListaGenerada == 0) {
                editarGenerado = listaGenerados.get(indexGenerado);
            } else {
                editarGenerado = filtrarListaGenerados.get(indexGenerado);
            }
            if (cualCeldaGenerado == 0) {
                context.update("formularioDialogos:editarProcesoGenerado");
                context.execute("editarProcesoGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 1) {
                context.update("formularioDialogos:editarEmpleadoGenerado");
                context.execute("editarEmpleadoGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 2) {
                context.update("formularioDialogos:editarCreditoGenerado");
                context.execute("editarCreditoGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 3) {
                context.update("formularioDialogos:editarDebitoGenerado");
                context.execute("editarDebitoGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 4) {
                context.update("formularioDialogos:editarTerceroGenerado");
                context.execute("editarTerceroGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 5) {
                context.update("formularioDialogos:editarValorGenerado");
                context.execute("editarValorGenerado.show()");
                cualCeldaGenerado = -1;
            } else if (cualCeldaGenerado == 6) {
                context.update("formularioDialogos:editarConceptoGenerado");
                context.execute("editarConceptoGenerado.show()");
                cualCeldaGenerado = -1;
            }
            indexGenerado = -1;
        }
        if (indexIntercon >= 0) {
            if (tipoListaIntercon == 0) {
                editarIntercon = listaInterconSapBO.get(indexIntercon);
            } else {
                editarIntercon = filtrarListaInterconSapBO.get(indexIntercon);
            }
            if (cualCeldaIntercon == 0) {
                context.update("formularioDialogos:editarEmpleadoIntercon");
                context.execute("editarEmpleadoIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 1) {
                context.update("formularioDialogos:editarTerceroIntercon");
                context.execute("editarTerceroIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 2) {
                context.update("formularioDialogos:editarCuentaIntercon");
                context.execute("editarCuentaIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 3) {
                context.update("formularioDialogos:editarDebitoIntercon");
                context.execute("editarDebitoIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 4) {
                context.update("formularioDialogos:editarCreditoIntercon");
                context.execute("editarCreditoIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 5) {
                context.update("formularioDialogos:editarConceptoIntercon");
                context.execute("editarConceptoIntercon.show()");
                cualCeldaIntercon = -1;
            } else if (cualCeldaIntercon == 6) {
                context.update("formularioDialogos:editarCentroCostoIntercon");
                context.execute("editarCentroCostoIntercon.show()");
                cualCeldaIntercon = -1;
            }
            indexIntercon = -1;
        }
    }
    
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        listParametrosContablesBorrar.clear();
        modificacionParametro = false;
        listaParametrosContables = null;
        getListaParametrosContables();
        parametroContableActual = null;
        listaGenerados = null;
        listaInterconSapBO = null;
        actualUsuarioBD = null;
        cambiosParametro = false;
        guardado = true;
        indexParametroContable = -1;
        indexGenerado = -1;
        indexIntercon = -1;
        activarEnviar = true;
        activarDeshacer = true;
        totalCGenerado = 0;
        totalDGenerado = 0;
        totalDInter = 0;
        totalCInter = 0;
        context.update("form:ACEPTAR");
    }
    
    public void asignarIndex(Integer indice, int numeroDialogo, int tipoNuevo) {
        tipoActualizacion = tipoNuevo;
        indexParametroContable = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (numeroDialogo == 0) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        } else if (numeroDialogo == 1) {
            context.update("form:ProcesoDialogo");
            context.execute("ProcesoDialogo.show()");
        }
    }
    
    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            parametroContableActual.setEmpresaRegistro(empresaSeleccionada);
            parametroContableActual.setEmpresaCodigo(empresaSeleccionada.getCodigo());
            indexParametroContable = -1;
            if (guardado == true) {
                guardado = false;
            }
            modificacionParametro = true;
            cambiosParametro = true;
            context.update("form:ACEPTAR");
            context.update("form:parametroEmpresa");
        }
        if (tipoActualizacion == 1) {
            nuevoParametroContable.setEmpresaRegistro(empresaSeleccionada);
            nuevoParametroContable.setEmpresaCodigo(empresaSeleccionada.getCodigo());
            context.update("formularioDialogos:nuevaEmpresaParametro");
        }
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        aceptar = true;
        context.update("form:EmpresaDialogo");
        context.update("form:lovEmpresa");
        context.update("form:aceptarE");
        context.reset("form:lovEmpresa:globalFilter");
        context.execute("EmpresaDialogo.hide()");
    }

    public void cancelarEmpresa() {
        empresaSeleccionada = new Empresas();
        filtrarLovEmpresas = null;
        indexParametroContable = -1;
        permitirIndexParametro = true;
        aceptar = true;
        tipoActualizacion = -1;
    }

    public void actualizarProceso() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            parametroContableActual.setProceso(procesoSeleccionado);
            indexParametroContable = -1;
            if (guardado == true) {
                guardado = false;
            }
            modificacionParametro = true;
            cambiosParametro = true;
            context.update("form:ACEPTAR");
            context.update("form:parametroProceso");
        }
        if (tipoActualizacion == 1) {
            nuevoParametroContable.setProceso(procesoSeleccionado);
            context.update("formularioDialogos:nuevaProcesoParametro");
        }
        procesoSeleccionado = new Procesos();
        filtrarLovProcesos = null;
        aceptar = true;
        context.update("form:ProcesoDialogo");
        context.update("form:lovProceso");
        context.update("form:aceptarP");
        context.reset("form:lovProceso:globalFilter");
        context.execute("ProcesoDialogo.hide()");
    }

    public void cancelarProceso() {
        aceptar = true;
        procesoSeleccionado = new Procesos();
        filtrarLovProcesos = null;
        indexParametroContable = -1;
        permitirIndexParametro = true;
    }
    
    public void listaValoresBoton() {
        if (indexParametroContable >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (indexParametroContable == 0) {
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
            if (indexParametroContable == 2) {
                context.update("form:ProcesoDialogo");
                context.execute("ProcesoDialogo.show()");
            }
        }
    }
    
    public void valoresBackupAutocompletar() {
        auxParametroEmpresa = nuevoParametroContable.getEmpresaRegistro().getNombre();
        auxParametroProceso = nuevoParametroContable.getProceso().getDescripcion();
    }

    public void autocompletarNuevo(String procesoCambio, String confirmarCambio) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (procesoCambio.equals("EMPRESA")) {
            nuevoParametroContable.getEmpresaRegistro().setNombre(auxParametroEmpresa);
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(confirmarCambio.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                nuevoParametroContable.setEmpresaRegistro(lovEmpresas.get(indiceUnicoElemento));
                nuevoParametroContable.setEmpresaCodigo(lovEmpresas.get(indiceUnicoElemento).getCodigo());
                lovEmpresas.clear();
                getLovEmpresas();
                context.update("formularioDialogos:nuevaEmpresaParametro");
            } else {
                nuevoParametroContable.getEmpresaRegistro().setNombre(auxParametroEmpresa);
                context.update("formularioDialogos:nuevaEmpresaParametro");
                permitirIndexParametro = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        } else if (procesoCambio.equals("PROCESO")) {
            if (!confirmarCambio.isEmpty()) {
                nuevoParametroContable.getProceso().setDescripcion(auxParametroProceso);
                for (int i = 0; i < lovProcesos.size(); i++) {
                    if (lovProcesos.get(i).getDescripcion().startsWith(confirmarCambio.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    nuevoParametroContable.setProceso(lovProcesos.get(indiceUnicoElemento));
                    lovProcesos.clear();
                    getLovProcesos();
                    context.update("formularioDialogos:nuevaProcesoParametro");
                } else {
                    nuevoParametroContable.getEmpresaRegistro().setNombre(auxParametroEmpresa);
                    context.update("formularioDialogos:nuevaProcesoParametro");
                    permitirIndexParametro = false;
                    context.update("form:ProcesoDialogo");
                    context.execute("ProcesoDialogo.show()");
                }
            } else {
                nuevoParametroContable.setProceso(new Procesos());
                lovProcesos.clear();
                getLovProcesos();
                context.update("formularioDialogos:nuevaProcesoParametro");
            }
        }
    }
    
    public void borrarParametroContable() {
        if (modificacionParametro == true) {
            modificacionParametro = false;
        }
        listaParametrosContables.remove(parametroContableActual);
        listParametrosContablesBorrar.add(parametroContableActual);
        parametroContableActual = null;
        getParametroContableActual();
        if (listaParametrosContables != null) {
            int tam = listaParametrosContables.size();
            if (tam == 0 || tam == 1) {
                estadoBtnAbajo = true;
                estadoBtnArriba = true;
            }
            if (tam > 1) {
                estadoBtnAbajo = false;
                estadoBtnArriba = true;
            }
        }
        activarEnviar = true;
        activarDeshacer = true;
        totalCGenerado = 0;
        totalDGenerado = 0;
        totalDInter = 0;
        totalCInter = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:PanelTotal");
        context.update("form:btnEnviar");
        context.update("form:btnDeshacer");
        context.update("form:PLANO");
    }

    public void agregarNuevoParametro() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (nuevoParametroContable.getEmpresaRegistro().getSecuencia() != null && nuevoParametroContable.getFechafinalcontabilizacion() != null && nuevoParametroContable.getFechainicialcontabilizacion() != null) {
                boolean validar = validarFechaParametro(1);
                if (validar == true) {
                    context.execute("NuevoRegistroPC.hide()");
                    int k = 1;
                    BigInteger var = new BigInteger(String.valueOf(k));
                    nuevoParametroContable.setSecuencia(var);
                    if (nuevoParametroContable.getProceso().getSecuencia() == null) {
                        nuevoParametroContable.setProceso(new Procesos());
                    }
                    administrarInterfaseContableSapBO.crearParametroContable(nuevoParametroContable);
                    nuevoParametroContable = new ParametrosContables();
                    activarAgregar = true;
                    activarOtros = false;
                    listaParametrosContables = null;
                    getListaParametrosContables();
                    parametroContableActual = null;
                    getParametroContableActual();
                    listaGenerados = null;
                    listaInterconSapBO = null;
                    activarEnviar = true;
                    activarDeshacer = true;
                    totalCGenerado = 0;
                    totalDGenerado = 0;
                    totalDInter = 0;
                    totalCInter = 0;
                    context.update("form:btnEnviar");
                    context.update("form:btnDeshacer");
                    context.update("form:PLANO");
                    context.update("form:PanelTotal");
                    FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    RequestContext.getCurrentInstance().update("form:growl");
                } else {
                    context.execute("errorFechasParametro.show()");
                }
            } else {
                context.execute("errorNewRegNull.show()");
            }
        } catch (Exception e) {
            System.out.println("Error Controlador agregarNuevo : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
        }
    }

    public void limpiarNuevoParametro() {
        nuevoParametroContable = new ParametrosContables();
        nuevoParametroContable.setEmpresaRegistro(new Empresas());
        nuevoParametroContable.setProceso(new Procesos());
    }

    public void validarExportPDF() throws IOException {
        if (indexParametroContable >= 0) {
            exportPDF_PC();
        }
        if (indexGenerado >= 0) {
            exportPDF_G();
        }
        if (indexIntercon >= 0) {
            exportPDF_I();
        }
    }

    public void exportPDF_PC() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosParametroExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ParametrosContables_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexGenerado = -1;
    }

    public void exportPDF_G() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosGenerarExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Generados_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexGenerado = -1;
    }

    public void exportPDF_I() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosInterconExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "InterconTotal_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexIntercon = -1;
    }

    public void validarExportXLS() throws IOException {
        if (indexParametroContable >= 0) {
            exportXLS_PC();
        }
        if (indexGenerado >= 0) {
            exportXLS_G();
        }
        if (indexIntercon >= 0) {
            exportXLS_I();
        }
    }

    public void exportXLS_PC() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosParametroExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ParametrosContables_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexParametroContable = -1;
    }

    public void exportXLS_G() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosGenerarExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Generados_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexGenerado = -1;
    }

    public void exportXLS_I() throws IOException {
        FacesContext c = FacesContext.getCurrentInstance();
        DataTable tabla = (DataTable) c.getViewRoot().findComponent("formExportar:datosInterconExportar");
        FacesContext context = c;
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "InterconTotal_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexIntercon = -1;
    }

    public String validarExportXML() {
        String tabla = "";
        if (indexParametroContable >= 0) {
            tabla = ":formExportar:datosParametroExportar";
        }
        if (indexGenerado >= 0) {
            tabla = ":formExportar:datosGenerarExportar";
        }
        if (indexIntercon >= 0) {
            tabla = ":formExportar:datosInterconExportar";
        }
        return tabla;
    }

    public String validarNombreExportXML() {
        String nombre = "";
        if (indexParametroContable >= 0) {
            nombre = "ParametrosContables_XML";
        }
        if (indexGenerado >= 0) {
            nombre = "Generados_XML";
        }
        if (indexIntercon >= 0) {
            nombre = "InterconTotal_XML";
        }
        return nombre;
    }

    public void eventoFiltrar() {
        if (indexGenerado >= 0) {
            if (tipoListaGenerada == 0) {
                tipoListaGenerada = 1;
            }
        }
        if (indexIntercon >= 0) {
            if (tipoListaIntercon == 0) {
                tipoListaIntercon = 1;
            }
        }
    }
    
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
    }
    
    public void validarRastro() {
        if (indexIntercon >= 0) {
            System.out.println("validarRastro");
            verificarRastro();
        }
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaInterconSapBO != null) {
            System.out.println("listaInterconTotal");
            if (secRegistro != null) {
                System.out.println("secRegistro");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "INTERCON_TOTAL");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                System.out.println("resultado");
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
            System.out.println("verificarHistoricosTabla");
            if (administrarRastros.verificarHistoricosTabla("INTERCON_TOTAL")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexIntercon = -1;
    }

    public ActualUsuario getActualUsuarioBD() {
        if (actualUsuarioBD == null) {
            actualUsuarioBD = administrarInterfaseContableSapBO.obtenerActualUsuario();
        }
        return actualUsuarioBD;
    }

    public void setActualUsuarioBD(ActualUsuario actualUsuarioBD) {
        this.actualUsuarioBD = actualUsuarioBD;
    }

    public ParametrosContables getParametroContableActual() {
        getListaParametrosContables();
        if (listaParametrosContables != null) {
            if (listParametrosContablesBorrar.size() > 0) {
                parametroContableActual = listParametrosContablesBorrar.get(0);
            }
        }
        return parametroContableActual;
    }

    public void setParametroContableActual(ParametrosContables parametroContableActual) {
        this.parametroContableActual = parametroContableActual;
    }

    public ParametrosContables getNuevoParametroContable() {
        return nuevoParametroContable;
    }

    public void setNuevoParametroContable(ParametrosContables nuevoParametroContable) {
        this.nuevoParametroContable = nuevoParametroContable;
    }

    public List<ParametrosContables> getListaParametrosContables() {
        if (listaParametrosContables == null) {
            getActualUsuarioBD();
            if (actualUsuarioBD != null) {
                listaParametrosContables = administrarInterfaseContableSapBO.obtenerParametrosContablesUsuarioBD(actualUsuarioBD.getAlias());
            }
        }
        return listaParametrosContables;
    }

    public void setListaParametrosContables(List<ParametrosContables> listaParametrosContables) {
        this.listaParametrosContables = listaParametrosContables;
    }

    public List<ParametrosContables> getListParametrosContablesBorrar() {
        return listParametrosContablesBorrar;
    }

    public void setListParametrosContablesBorrar(List<ParametrosContables> listParametrosContablesBorrar) {
        this.listParametrosContablesBorrar = listParametrosContablesBorrar;
    }

    public List<SolucionesNodos> getListaGenerados() {
        return listaGenerados;
    }

    public void setListaGenerados(List<SolucionesNodos> listaGenerados) {
        this.listaGenerados = listaGenerados;
    }

    public List<SolucionesNodos> getFiltrarListaGenerados() {
        return filtrarListaGenerados;
    }

    public void setFiltrarListaGenerados(List<SolucionesNodos> filtrarListaGenerados) {
        this.filtrarListaGenerados = filtrarListaGenerados;
    }

    public SolucionesNodos getGeneradoTablaSeleccionado() {
        getListaGenerados();
        if (listaGenerados != null) {
            if (listaGenerados.size() > 0) {
                generadoTablaSeleccionado = listaGenerados.get(0);
            }
        }
        return generadoTablaSeleccionado;
    }

    public void setGeneradoTablaSeleccionado(SolucionesNodos generadoTablaSeleccionado) {
        this.generadoTablaSeleccionado = generadoTablaSeleccionado;
    }

    public SolucionesNodos getEditarGenerado() {
        return editarGenerado;
    }

    public void setEditarGenerado(SolucionesNodos editarGenerado) {
        this.editarGenerado = editarGenerado;
    }

    public List<InterconSapBO> getListaInterconSapBO() {
        return listaInterconSapBO;
    }

    public void setListaInterconSapBO(List<InterconSapBO> listaInterconSapBO) {
        this.listaInterconSapBO = listaInterconSapBO;
    }

    public List<InterconSapBO> getFiltrarListaInterconSapBO() {
        return filtrarListaInterconSapBO;
    }

    public void setFiltrarListaInterconSapBO(List<InterconSapBO> filtrarListaInterconSapBO) {
        this.filtrarListaInterconSapBO = filtrarListaInterconSapBO;
    }

    public InterconSapBO getInterconTablaSeleccionada() {
        getListaInterconSapBO();
        if (listaInterconSapBO != null) {
            if (listaInterconSapBO.size() > 0) {
                interconTablaSeleccionada = listaInterconSapBO.get(0);
            }
        }
        return interconTablaSeleccionada;
    }

    public void setInterconTablaSeleccionada(InterconSapBO interconTablaSeleccionada) {
        this.interconTablaSeleccionada = interconTablaSeleccionada;
    }

    public InterconSapBO getEditarIntercon() {
        return editarIntercon;
    }

    public void setEditarIntercon(InterconSapBO editarIntercon) {
        this.editarIntercon = editarIntercon;
    }

    public boolean isPermitirIndexParametro() {
        return permitirIndexParametro;
    }

    public void setPermitirIndexParametro(boolean permitirIndexParametro) {
        this.permitirIndexParametro = permitirIndexParametro;
    }

    public List<Empresas> getLovEmpresas() {
        lovEmpresas = administrarInterfaseContableSapBO.lovEmpresas();
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

    public List<Procesos> getLovProcesos() {
        lovProcesos = administrarInterfaseContableSapBO.lovProcesos();
        return lovProcesos;
    }

    public void setLovProcesos(List<Procesos> lovProcesos) {
        this.lovProcesos = lovProcesos;
    }

    public List<Procesos> getFiltrarLovProcesos() {
        return filtrarLovProcesos;
    }

    public void setFiltrarLovProcesos(List<Procesos> filtrarLovProcesos) {
        this.filtrarLovProcesos = filtrarLovProcesos;
    }

    public Procesos getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public boolean isEstadoBtnArriba() {
        return estadoBtnArriba;
    }

    public void setEstadoBtnArriba(boolean estadoBtnArriba) {
        this.estadoBtnArriba = estadoBtnArriba;
    }

    public boolean isEstadoBtnAbajo() {
        return estadoBtnAbajo;
    }

    public void setEstadoBtnAbajo(boolean estadoBtnAbajo) {
        this.estadoBtnAbajo = estadoBtnAbajo;
    }

    public boolean isActivarAgregar() {
        return activarAgregar;
    }

    public void setActivarAgregar(boolean activarAgregar) {
        this.activarAgregar = activarAgregar;
    }

    public boolean isActivarOtros() {
        return activarOtros;
    }

    public void setActivarOtros(boolean activarOtros) {
        this.activarOtros = activarOtros;
    }

    public boolean isActivarEnviar() {
        return activarEnviar;
    }

    public void setActivarEnviar(boolean activarEnviar) {
        this.activarEnviar = activarEnviar;
    }

    public boolean isActivarDeshacer() {
        return activarDeshacer;
    }

    public void setActivarDeshacer(boolean activarDeshacer) {
        this.activarDeshacer = activarDeshacer;
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

    public int getTipoPlano() {
        return tipoPlano;
    }

    public void setTipoPlano(int tipoPlano) {
        this.tipoPlano = tipoPlano;
    }

    public String getMsnFechasActualizar() {
        return msnFechasActualizar;
    }

    public void setMsnFechasActualizar(String msnFechasActualizar) {
        this.msnFechasActualizar = msnFechasActualizar;
    }

    public int getTotalCGenerado() {
        return totalCGenerado;
    }

    public void setTotalCGenerado(int totalCGenerado) {
        this.totalCGenerado = totalCGenerado;
    }

    public int getTotalDGenerado() {
        return totalDGenerado;
    }

    public void setTotalDGenerado(int totalDGenerado) {
        this.totalDGenerado = totalDGenerado;
    }

    public int getTotalDInter() {
        return totalDInter;
    }

    public void setTotalDInter(int totalDInter) {
        this.totalDInter = totalDInter;
    }

    public int getTotalCInter() {
        return totalCInter;
    }

    public void setTotalCInter(int totalCInter) {
        this.totalCInter = totalCInter;
    }

    public String getMsnPaso1() {
        return msnPaso1;
    }

    public void setMsnPaso1(String msnPaso1) {
        this.msnPaso1 = msnPaso1;
    }

    public String getAltoTablaGenerada() {
        return altoTablaGenerada;
    }

    public void setAltoTablaGenerada(String altoTablaGenerada) {
        this.altoTablaGenerada = altoTablaGenerada;
    }

    public String getAltoTablaIntercon() {
        return altoTablaIntercon;
    }

    public void setAltoTablaIntercon(String altoTablaIntercon) {
        this.altoTablaIntercon = altoTablaIntercon;
    }

    public String getInfoRegistroEmpresa() {
        getLovEmpresas();
        if (lovEmpresas != null) {
            infoRegistroEmpresa = "Cantidad de registros : " + lovEmpresas.size();
        } else {
            infoRegistroEmpresa = "Cantidad de registros : 0";
        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public String getInfoRegistroProceso() {
        getLovProcesos();
        if (lovProcesos != null) {
            infoRegistroProceso = "Cantidad de registros : " + lovProcesos.size();
        } else {
            infoRegistroProceso = "Cantidad de registros : 0";
        }
        return infoRegistroProceso;
    }

    public void setInfoRegistroProceso(String infoRegistroProceso) {
        this.infoRegistroProceso = infoRegistroProceso;
    }

}

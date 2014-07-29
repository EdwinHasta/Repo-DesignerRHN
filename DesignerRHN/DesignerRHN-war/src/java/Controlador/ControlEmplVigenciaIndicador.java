package Controlador;

import Entidades.Empleados;
import Entidades.Indicadores;
import Entidades.TiposIndicadores;
import Entidades.VigenciasIndicadores;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplVigenciaIndicadorInterface;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlEmplVigenciaIndicador implements Serializable {

    @EJB
    AdministrarEmplVigenciaIndicadorInterface administrarEmplVigenciaIndicador;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //VigenciasIndicadores
    private List<VigenciasIndicadores> listVigenciasIndicadores;
    private List<VigenciasIndicadores> filtrarListVigenciasIndicadores;
    private VigenciasIndicadores vigenciaTablaSeleccionada;
    //TiposIndicadores
    private List<TiposIndicadores> listTiposIndicadores;
    private TiposIndicadores tipoIndicadorSeleccionado;
    private List<TiposIndicadores> filtrarListTiposIndicadores;
    //Indicadores
    private List<Indicadores> listIndicadores;
    private Indicadores indicadorSeleccionado;
    private List<Indicadores> filtrarListIndicadores;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int banderaV;
    //Columnas Tabla VP
    private Column viTipoIndicador, viIndicador, viFechaInicial, viFechaFinal;
    //Otros
    private boolean aceptar;
    //modificar
    private List<VigenciasIndicadores> listVigenciaIndicadorModificar;
    private boolean guardado;
    //crear VP
    public VigenciasIndicadores nuevaVigencia;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<VigenciasIndicadores> listVigenciaIndicadorBorrar;
    //editar celda
    private VigenciasIndicadores editarVigenciaIndicador;
    //duplicar
    //Autocompletar
    private boolean permitirIndexV;
    //Variables Autompletar
    private String tipos, indicador;
    private int index;
    private int cualCelda, tipoLista;
    private VigenciasIndicadores duplicarVigenciaIndicador;
    private List<VigenciasIndicadores> listVigenciaIndicadorCrear;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaInic, fechaFin;
    private Empleados empleado;
    private Date fechaParametro;
    //
    private String infoRegistro;
    private String infoRegistroTipo, infoRegistroIndicador;
    private String altoTabla;

    public ControlEmplVigenciaIndicador() {
        altoTabla = "310";
        empleado = new Empleados();
        backUpSecRegistro = null;
        tipoLista = 0;
        //Otros
        aceptar = true;
        listVigenciaIndicadorBorrar = new ArrayList<VigenciasIndicadores>();
        k = 0;
        listVigenciaIndicadorModificar = new ArrayList<VigenciasIndicadores>();
        editarVigenciaIndicador = new VigenciasIndicadores();
        tipoLista = 0;
        guardado = true;

        banderaV = 0;
        permitirIndexV = true;
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        listIndicadores = null;
        listTiposIndicadores = null;
        nuevaVigencia = new VigenciasIndicadores();
        nuevaVigencia.setIndicador(new Indicadores());
        nuevaVigencia.setTipoindicador(new TiposIndicadores());
        listVigenciaIndicadorCrear = new ArrayList<VigenciasIndicadores>();
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplVigenciaIndicador.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger secuencia) {
        empleado = new Empleados();
        empleado = administrarEmplVigenciaIndicador.empleadoActual(secuencia);
        listVigenciasIndicadores = null;
        getListVigenciasIndicadores();
        if (listVigenciasIndicadores != null) {
            infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasIndicadores auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listVigenciasIndicadores.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListVigenciasIndicadores.get(index);
            }
            if (auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevaVigencia.getFechafinal() != null) {
                if (nuevaVigencia.getFechainicial().after(fechaParametro) && nuevaVigencia.getFechainicial().before(nuevaVigencia.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (nuevaVigencia.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVigenciaIndicador.getFechafinal() != null) {
                if (duplicarVigenciaIndicador.getFechainicial().after(fechaParametro) && duplicarVigenciaIndicador.getFechainicial().before(duplicarVigenciaIndicador.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                if (duplicarVigenciaIndicador.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        VigenciasIndicadores auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listVigenciasIndicadores.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarListVigenciasIndicadores.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            if (auxiliar.getFechafinal() == null) {
                retorno = true;
            }
            if (auxiliar.getFechafinal() != null) {
                index = i;
                retorno = validarFechasRegistro(0);
            }
            if (retorno == true) {
                cambiarIndiceV(i, c);
                modificarVigencia(i);
            } else {
                if (tipoLista == 0) {
                    listVigenciasIndicadores.get(i).setFechafinal(fechaFin);
                    listVigenciasIndicadores.get(i).setFechainicial(fechaInic);
                }
                if (tipoLista == 1) {
                    filtrarListVigenciasIndicadores.get(i).setFechafinal(fechaFin);
                    filtrarListVigenciasIndicadores.get(i).setFechainicial(fechaInic);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigencia");
                context.execute("form:errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listVigenciasIndicadores.get(i).setFechainicial(fechaInic);
            }
            if (tipoLista == 1) {
                filtrarListVigenciasIndicadores.get(i).setFechainicial(fechaInic);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigencia");
            context.execute("errorRegInfo.show()");
        }
    }

    public void modificarVigencia(int indice) {
        if (tipoLista == 0) {
            index = indice;
            if (!listVigenciaIndicadorCrear.contains(listVigenciasIndicadores.get(indice))) {
                if (listVigenciaIndicadorModificar.isEmpty()) {
                    listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(indice));
                } else if (!listVigenciaIndicadorModificar.contains(listVigenciasIndicadores.get(indice))) {
                    listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        } else {
            int ind = listVigenciasIndicadores.indexOf(filtrarListVigenciasIndicadores.get(indice));
            index = ind;

            if (!listVigenciaIndicadorCrear.contains(filtrarListVigenciasIndicadores.get(indice))) {
                if (listVigenciaIndicadorModificar.isEmpty()) {
                    listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(indice));
                } else if (!listVigenciaIndicadorModificar.contains(filtrarListVigenciasIndicadores.get(indice))) {
                    listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
            index = -1;
            secRegistro = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigencia");

    }

    public void modificarVigencia(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOS")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoLista == 0) {
                    listVigenciasIndicadores.get(indice).getTipoindicador().setDescripcion(tipos);
                } else {
                    filtrarListVigenciasIndicadores.get(indice).getTipoindicador().setDescripcion(tipos);
                }
                for (int i = 0; i < listTiposIndicadores.size(); i++) {
                    if (listTiposIndicadores.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listVigenciasIndicadores.get(indice).setTipoindicador(listTiposIndicadores.get(indiceUnicoElemento));
                    } else {
                        filtrarListVigenciasIndicadores.get(indice).setTipoindicador(listTiposIndicadores.get(indiceUnicoElemento));
                    }
                    listTiposIndicadores = null;
                    getListTiposIndicadores();
                } else {
                    permitirIndexV = false;
                    context.update("form:TiposDialogo");
                    context.execute("TiposDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                coincidencias = 1;
                listTiposIndicadores = null;
                getListTiposIndicadores();
                if (tipoLista == 0) {
                    listVigenciasIndicadores.get(indice).setTipoindicador(new TiposIndicadores());
                } else {
                    filtrarListVigenciasIndicadores.get(indice).setTipoindicador(new TiposIndicadores());
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("INDICADORES")) {
            if (tipoLista == 0) {
                listVigenciasIndicadores.get(indice).getIndicador().setDescripcion(indicador);
            } else {
                filtrarListVigenciasIndicadores.get(indice).getIndicador().setDescripcion(indicador);
            }
            for (int i = 0; i < listIndicadores.size(); i++) {
                if (listIndicadores.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasIndicadores.get(indice).setIndicador(listIndicadores.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasIndicadores.get(indice).setIndicador(listIndicadores.get(indiceUnicoElemento));
                }
                listIndicadores = null;
                getListIndicadores();
            } else {
                permitirIndexV = false;
                context.update("form:IndicadorDialogo");
                context.execute("IndicadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVigenciaIndicadorCrear.contains(listVigenciasIndicadores.get(indice))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(indice));
                    } else if (!listVigenciaIndicadorModificar.contains(listVigenciasIndicadores.get(indice))) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVigenciaIndicadorCrear.contains(filtrarListVigenciasIndicadores.get(indice))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(indice));
                    } else if (!listVigenciaIndicadorModificar.contains(filtrarListVigenciasIndicadores.get(indice))) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigencia");
    }

    public void valoresBackupAutocompletarVigencia(int tipoNuevo, String Campo) {

        if (Campo.equals("TIPOS")) {
            if (tipoNuevo == 1) {
                tipos = nuevaVigencia.getTipoindicador().getDescripcion();
            } else if (tipoNuevo == 2) {
                tipos = duplicarVigenciaIndicador.getTipoindicador().getDescripcion();
            }
        } else if (Campo.equals("INDICADORES")) {
            if (tipoNuevo == 1) {
                indicador = nuevaVigencia.getIndicador().getDescripcion();
            } else if (tipoNuevo == 2) {
                indicador = duplicarVigenciaIndicador.getIndicador().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigencia(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOS")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.getTipoindicador().setDescripcion(tipos);
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaIndicador.getTipoindicador().setDescripcion(tipos);
                }
                for (int i = 0; i < listTiposIndicadores.size(); i++) {
                    if (listTiposIndicadores.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigencia.setTipoindicador(listTiposIndicadores.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaTipoIndicadorV");
                    } else if (tipoNuevo == 2) {
                        duplicarVigenciaIndicador.setTipoindicador(listTiposIndicadores.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarTipoV");
                    }
                    listTiposIndicadores = null;
                    getListTiposIndicadores();
                } else {
                    context.update("form:TiposDialogo");
                    context.execute("TiposDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaTipoIndicadorV");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarTipoV");
                    }
                }
            } else {
                listTiposIndicadores = null;
                getListTiposIndicadores();
                if (tipoNuevo == 1) {
                    nuevaVigencia.setTipoindicador(new TiposIndicadores());
                    context.update("formularioDialogos:nuevaTipoIndicadorV");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaIndicador.setTipoindicador(new TiposIndicadores());
                    context.update("formularioDialogos:duplicarTipoV");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("INDICADORES")) {
            if (tipoNuevo == 1) {
                nuevaVigencia.getIndicador().setDescripcion(indicador);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaIndicador.getIndicador().setDescripcion(indicador);
            }
            for (int i = 0; i < listIndicadores.size(); i++) {
                if (listIndicadores.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }

            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigencia.setIndicador(listIndicadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaIndicadorV");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaIndicador.setIndicador(listIndicadores.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarIndicadorV");
                }
                listIndicadores = null;
                getListIndicadores();
            } else {
                context.update("form:IndicadorDialogo");
                context.execute("IndicadorDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaIndicadorV");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarIndicadorV");
                }
            }
        }
    }

    public void cambiarIndiceV(int indice, int celda) {
        if (permitirIndexV == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                fechaFin = listVigenciasIndicadores.get(index).getFechafinal();
                fechaInic = listVigenciasIndicadores.get(index).getFechainicial();
                secRegistro = listVigenciasIndicadores.get(index).getSecuencia();
                //tipos = listVigenciasIndicadores.get(index).getTipoindicador().getDescripcion();
                indicador = listVigenciasIndicadores.get(index).getIndicador().getDescripcion();
            }
            if (tipoLista == 1) {
                fechaFin = filtrarListVigenciasIndicadores.get(index).getFechafinal();
                fechaInic = filtrarListVigenciasIndicadores.get(index).getFechainicial();
                secRegistro = filtrarListVigenciasIndicadores.get(index).getSecuencia();
                tipos = filtrarListVigenciasIndicadores.get(index).getTipoindicador().getDescripcion();
                indicador = filtrarListVigenciasIndicadores.get(index).getIndicador().getDescripcion();
            }
        }
    }

    public void guardadoGeneral() {
        guardarCambiosV();
    }

    public void guardarCambiosV() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listVigenciaIndicadorBorrar.isEmpty()) {
                    administrarEmplVigenciaIndicador.borrarVigenciasIndicadores(listVigenciaIndicadorBorrar);
                    listVigenciaIndicadorBorrar.clear();
                }
                if (!listVigenciaIndicadorCrear.isEmpty()) {
                    administrarEmplVigenciaIndicador.crearVigenciasIndicadores(listVigenciaIndicadorCrear);
                    listVigenciaIndicadorCrear.clear();
                }
                if (!listVigenciaIndicadorModificar.isEmpty()) {
                    administrarEmplVigenciaIndicador.editarVigenciasIndicadores(listVigenciaIndicadorModificar);
                    listVigenciaIndicadorModificar.clear();
                }
                listVigenciasIndicadores = null;
                getListVigenciasIndicadores();
                if (listVigenciasIndicadores != null) {
                    infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                context.update("form:datosVigencia");
                k = 0;
                index = -1;
                secRegistro = null;
                guardado = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado, intente nuevamente");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void cancelarModificacionV() {
        if (banderaV == 1) {
            altoTabla = "310";
            FacesContext c = FacesContext.getCurrentInstance();
            viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
            viTipoIndicador.setFilterStyle("display: none; visibility: hidden;");

            viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
            viIndicador.setFilterStyle("display: none; visibility: hidden;");

            viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
            viFechaInicial.setFilterStyle("display: none; visibility: hidden;");

            viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
            viFechaFinal.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosVigencia");
            banderaV = 0;
            filtrarListVigenciasIndicadores = null;
            tipoLista = 0;
        }
        listIndicadores = null;
        listTiposIndicadores = null;
        listVigenciaIndicadorBorrar.clear();
        listVigenciaIndicadorCrear.clear();
        listVigenciaIndicadorModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasIndicadores = null;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        RequestContext context = RequestContext.getCurrentInstance();
        getListVigenciasIndicadores();
        if (listVigenciasIndicadores != null) {
            infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        context.update("form:informacionRegistro");
        context.update("form:datosVigencia");
        nuevaVigencia = new VigenciasIndicadores();
        nuevaVigencia.setIndicador(new Indicadores());
        nuevaVigencia.setTipoindicador(new TiposIndicadores());
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaIndicador = listVigenciasIndicadores.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaIndicador = filtrarListVigenciasIndicadores.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicialVD");
                context.execute("editarFechaInicialVD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinalVD");
                context.execute("editarFechaFinalVD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTipoVD");
                context.execute("editarTipoVD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarIndicadorVD");
                context.execute("editarIndicadorVD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevaV() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigencia.getFechainicial() != null && nuevaVigencia.getIndicador().getSecuencia() != null) {
            if (validarFechasRegistro(1) == true) {
                //CERRAR FILTRADO
                if (banderaV == 1) {
                    altoTabla = "310";
                    FacesContext c = FacesContext.getCurrentInstance();
                    viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
                    viTipoIndicador.setFilterStyle("display: none; visibility: hidden;");

                    viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
                    viIndicador.setFilterStyle("display: none; visibility: hidden;");

                    viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
                    viFechaInicial.setFilterStyle("display: none; visibility: hidden;");

                    viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
                    viFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigencia");
                    banderaV = 0;
                    filtrarListVigenciasIndicadores = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                k++;
                l = BigInteger.valueOf(k);
                nuevaVigencia.setSecuencia(l);
                nuevaVigencia.setEmpleado(empleado);
                listVigenciasIndicadores.add(nuevaVigencia);
                listVigenciaIndicadorCrear.add(nuevaVigencia);
                //
                nuevaVigencia = new VigenciasIndicadores();
                nuevaVigencia.setTipoindicador(new TiposIndicadores());
                nuevaVigencia.setIndicador(new Indicadores());
                limpiarNuevaV();
                //
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
                infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
                context.update("form:informacionRegistro");
                context.update("form:datosVigencia");
                context.execute("NuevoRegistroV.hide()");

            } else {
                context.execute("form:errorFechas.show()");
            }
        } else {
            context.execute("errorRegInfo.show()");
        }
    }

    public void limpiarNuevaV() {
        nuevaVigencia = new VigenciasIndicadores();
        nuevaVigencia.setTipoindicador(new TiposIndicadores());
        nuevaVigencia.setIndicador(new Indicadores());
        index = -1;
        secRegistro = null;

    }

    public void cancelarNuevaV() {
        nuevaVigencia = new VigenciasIndicadores();
        nuevaVigencia.setTipoindicador(new TiposIndicadores());
        nuevaVigencia.setIndicador(new Indicadores());
        index = -1;
        secRegistro = null;
    }

    public void verificarDuplicarVigencia() {
        if (index >= 0) {
            duplicarVigenciaM();
        }
    }

    public void duplicarVigenciaM() {
        duplicarVigenciaIndicador = new VigenciasIndicadores();
        if (tipoLista == 0) {
            duplicarVigenciaIndicador.setFechafinal(listVigenciasIndicadores.get(index).getFechafinal());
            duplicarVigenciaIndicador.setFechainicial(listVigenciasIndicadores.get(index).getFechainicial());
            duplicarVigenciaIndicador.setEmpleado(empleado);
            duplicarVigenciaIndicador.setIndicador(listVigenciasIndicadores.get(index).getIndicador());
            duplicarVigenciaIndicador.setTipoindicador(listVigenciasIndicadores.get(index).getTipoindicador());
        }
        if (tipoLista == 1) {
            duplicarVigenciaIndicador.setFechafinal(filtrarListVigenciasIndicadores.get(index).getFechafinal());
            duplicarVigenciaIndicador.setFechainicial(filtrarListVigenciasIndicadores.get(index).getFechainicial());
            duplicarVigenciaIndicador.setEmpleado(empleado);
            duplicarVigenciaIndicador.setIndicador(filtrarListVigenciasIndicadores.get(index).getIndicador());
            duplicarVigenciaIndicador.setTipoindicador(filtrarListVigenciasIndicadores.get(index).getTipoindicador());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicarV");
        context.execute("DuplicarRegistroV.show()");
        index = -1;
        secRegistro = null;
    }

    public void confirmarDuplicarV() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarVigenciaIndicador.getFechainicial() != null && duplicarVigenciaIndicador.getIndicador().getSecuencia() != null) {
            if (validarFechasRegistro(2) == true) {
                k++;
                l = BigInteger.valueOf(k);
                duplicarVigenciaIndicador.setEmpleado(empleado);
                duplicarVigenciaIndicador.setSecuencia(l);
                listVigenciasIndicadores.add(duplicarVigenciaIndicador);
                listVigenciaIndicadorCrear.add(duplicarVigenciaIndicador);
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                if (banderaV == 1) {
                    //CERRAR FILTRADO
                    altoTabla = "310";
                    FacesContext c = FacesContext.getCurrentInstance();
                    viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
                    viTipoIndicador.setFilterStyle("display: none; visibility: hidden;");

                    viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
                    viIndicador.setFilterStyle("display: none; visibility: hidden;");

                    viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
                    viFechaInicial.setFilterStyle("display: none; visibility: hidden;");

                    viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
                    viFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigencia");
                    banderaV = 0;
                    filtrarListVigenciasIndicadores = null;
                    tipoLista = 0;
                }
                duplicarVigenciaIndicador = new VigenciasIndicadores();
                limpiarduplicarV();
                getListVigenciasIndicadores();
                infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
                context.update("form:informacionRegistro");
                context.update("form:datosVigencia");
                context.execute("DuplicarRegistroV.hide()");
            } else {
                context.execute("errorFechas.show()");
            }
        } else {
            context.execute("errorRegInfo.show()");
        }
    }

    public void limpiarduplicarV() {
        duplicarVigenciaIndicador = new VigenciasIndicadores();
        duplicarVigenciaIndicador.setTipoindicador(new TiposIndicadores());
        duplicarVigenciaIndicador.setIndicador(new Indicadores());
    }

    public void cancelarDuplicadoV() {
        duplicarVigenciaIndicador = new VigenciasIndicadores();
        duplicarVigenciaIndicador.setTipoindicador(new TiposIndicadores());
        duplicarVigenciaIndicador.setIndicador(new Indicadores());
        index = -1;
        secRegistro = null;
    }

    public void validarBorradoVigencia() {
        if (index >= 0) {
            borrarV();
        }
    }

    public void borrarV() {
        if (tipoLista == 0) {
            if (!listVigenciaIndicadorModificar.isEmpty() && listVigenciaIndicadorModificar.contains(listVigenciasIndicadores.get(index))) {
                int modIndex = listVigenciaIndicadorModificar.indexOf(listVigenciasIndicadores.get(index));
                listVigenciaIndicadorModificar.remove(modIndex);
                listVigenciaIndicadorBorrar.add(listVigenciasIndicadores.get(index));
            } else if (!listVigenciaIndicadorCrear.isEmpty() && listVigenciaIndicadorCrear.contains(listVigenciasIndicadores.get(index))) {
                int crearIndex = listVigenciaIndicadorCrear.indexOf(listVigenciasIndicadores.get(index));
                listVigenciaIndicadorCrear.remove(crearIndex);
            } else {
                listVigenciaIndicadorBorrar.add(listVigenciasIndicadores.get(index));
            }
            listVigenciasIndicadores.remove(index);
        }
        if (tipoLista == 1) {
            if (!listVigenciaIndicadorModificar.isEmpty() && listVigenciaIndicadorModificar.contains(filtrarListVigenciasIndicadores.get(index))) {
                int modIndex = listVigenciaIndicadorModificar.indexOf(filtrarListVigenciasIndicadores.get(index));
                listVigenciaIndicadorModificar.remove(modIndex);
                listVigenciaIndicadorBorrar.add(filtrarListVigenciasIndicadores.get(index));
            } else if (!listVigenciaIndicadorCrear.isEmpty() && listVigenciaIndicadorCrear.contains(filtrarListVigenciasIndicadores.get(index))) {
                int crearIndex = listVigenciaIndicadorCrear.indexOf(filtrarListVigenciasIndicadores.get(index));
                listVigenciaIndicadorCrear.remove(crearIndex);
            } else {
                listVigenciaIndicadorBorrar.add(filtrarListVigenciasIndicadores.get(index));
            }
            int VPIndex = listVigenciasIndicadores.indexOf(filtrarListVigenciasIndicadores.get(index));
            listVigenciasIndicadores.remove(VPIndex);
            filtrarListVigenciasIndicadores.remove(index);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros : " + listVigenciasIndicadores.size();
        context.update("form:informacionRegistro");
        context.update("form:datosVigencia");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }

    }

    public void activarCtrlF11() {
        filtradoVigencia();
    }

    public void filtradoVigencia() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaV == 0) {
            altoTabla = "288";
            viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
            viFechaInicial.setFilterStyle("width: 50px");

            viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
            viFechaFinal.setFilterStyle("width: 50px");
            
            viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
            viTipoIndicador.setFilterStyle("width: 150px");

            viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
            viIndicador.setFilterStyle("width: 150px");

            ///
            RequestContext.getCurrentInstance().update("form:datosVigencia");
            tipoLista = 1;
            banderaV = 1;
        } else if (banderaV == 1) {
            altoTabla = "310";
            viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
            viTipoIndicador.setFilterStyle("display: none; visibility: hidden;");

            viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
            viIndicador.setFilterStyle("display: none; visibility: hidden;");

            viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
            viFechaInicial.setFilterStyle("display: none; visibility: hidden;");

            viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
            viFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigencia");
            banderaV = 0;
            filtrarListVigenciasIndicadores = null;
            tipoLista = 0;
        }

    }

    public void salir() {
        if (banderaV == 1) {
            altoTabla = "310";
            FacesContext c = FacesContext.getCurrentInstance();
            viTipoIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viTipoIndicador");
            viTipoIndicador.setFilterStyle("display: none; visibility: hidden;");

            viIndicador = (Column) c.getViewRoot().findComponent("form:datosVigencia:viIndicador");
            viIndicador.setFilterStyle("display: none; visibility: hidden;");

            viFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaInicial");
            viFechaInicial.setFilterStyle("display: none; visibility: hidden;");

            viFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVigencia:viFechaFinal");
            viFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigencia");
            banderaV = 0;
            filtrarListVigenciasIndicadores = null;
            tipoLista = 0;
        }
        listVigenciaIndicadorBorrar.clear();
        listVigenciaIndicadorCrear.clear();
        listVigenciaIndicadorModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasIndicadores = null;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        tipoActualizacion = -1;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

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
            context.update("form:TiposDialogo");
            context.execute("TiposDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:IndicadorDialogo");
            context.execute("IndicadorDialogo.show()");
        }

    }

    public void actualizarTipoIndicador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasIndicadores.get(index).setTipoindicador(tipoIndicadorSeleccionado);
                if (!listVigenciaIndicadorCrear.contains(listVigenciasIndicadores.get(index))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(index));
                    } else if (!listVigenciaIndicadorModificar.contains(listVigenciasIndicadores.get(index))) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                permitirIndexV = true;

            } else {
                filtrarListVigenciasIndicadores.get(index).setTipoindicador(tipoIndicadorSeleccionado);
                if (!listVigenciaIndicadorCrear.contains(filtrarListVigenciasIndicadores.get(index))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(index));
                    } else if (!listVigenciaIndicadorModificar.contains(filtrarListVigenciasIndicadores.get(index))) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(index));
                    }
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                permitirIndexV = true;
            }
            context.update("form:datosVigencia");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setTipoindicador(tipoIndicadorSeleccionado);
            context.update("formularioDialogos:nuevaTipoIndicadorV");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaIndicador.setTipoindicador(tipoIndicadorSeleccionado);
            context.update("formularioDialogos:duplicarTipoV");
        }
        filtrarListTiposIndicadores = null;
        tipoIndicadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:TiposDialogo");
        context.update("form:lovTipos");
        context.update("form:aceptarT");
        context.reset("form:lovTipos:globalFilter");
        context.execute("TiposDialogo.hide()");
    }

    public void cancelarCambioTipoIndicador() {
        filtrarListTiposIndicadores = null;
        tipoIndicadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexV = true;
    }

    public void actualizarIndicador() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasIndicadores.get(index).setIndicador(indicadorSeleccionado);
                if (!listVigenciaIndicadorCrear.contains(listVigenciasIndicadores.get(index))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(index));
                    } else if (!listVigenciaIndicadorModificar.contains(listVigenciasIndicadores.get(index))) {
                        listVigenciaIndicadorModificar.add(listVigenciasIndicadores.get(index));
                    }
                }
            } else {
                filtrarListVigenciasIndicadores.get(index).setIndicador(indicadorSeleccionado);
                if (!listVigenciaIndicadorCrear.contains(filtrarListVigenciasIndicadores.get(index))) {
                    if (listVigenciaIndicadorModificar.isEmpty()) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(index));
                    } else if (!listVigenciaIndicadorModificar.contains(filtrarListVigenciasIndicadores.get(index))) {
                        listVigenciaIndicadorModificar.add(filtrarListVigenciasIndicadores.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndexV = true;
            context.update("form:datosVigencia");
        } else if (tipoActualizacion == 1) {
            nuevaVigencia.setIndicador(indicadorSeleccionado);
            context.update("formularioDialogos:nuevaIndicadorV");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaIndicador.setIndicador(indicadorSeleccionado);
            context.update("formularioDialogos:duplicarIndicadorV");
        }
        filtrarListIndicadores = null;
        indicadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:IndicadorDialogo");
        context.update("form:lovIndicador");
        context.update("form:aceptarI");
        context.reset("form:lovIndicador:globalFilter");
        context.execute("IndicadorDialogo.hide()");
    }

    public void cancelarCambioIndicador() {
        filtrarListIndicadores = null;
        indicadorSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexV = true;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 2) {
                context.update("form:TiposDialogo");
                context.execute("TiposDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 3) {
                context.update("form:IndicadorDialogo");
                context.execute("IndicadorDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void validarExportPDF() throws IOException {
        exportPDF_V();
    }

    public void exportPDF_V() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarV:datosVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CensosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarExportXLS() throws IOException {
        exportXLS_V();
    }

    public void exportXLS_V() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarV:datosVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CensosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros : " + filtrarListVigenciasIndicadores.size();
            context.update("form:informacionRegistro");
        }
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasIndicadores != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASINDICADORES");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASINDICADORES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GET - SET 
    public List<VigenciasIndicadores> getListVigenciasIndicadores() {
        try {
            if (listVigenciasIndicadores == null) {
                if (empleado.getSecuencia() != null) {
                    listVigenciasIndicadores = administrarEmplVigenciaIndicador.listVigenciasIndicadoresEmpleado(empleado.getSecuencia());
                    if (listVigenciasIndicadores != null) {
                        for (int i = 0; i < listVigenciasIndicadores.size(); i++) {
                            if (listVigenciasIndicadores.get(i).getTipoindicador() == null) {
                                listVigenciasIndicadores.get(i).setTipoindicador(new TiposIndicadores());
                            }
                            if (listVigenciasIndicadores.get(i).getIndicador() == null) {
                                listVigenciasIndicadores.get(i).setIndicador(new Indicadores());
                            }
                        }
                    }
                }
            }
            return listVigenciasIndicadores;
        } catch (Exception e) {
            System.out.println("Error en getListVigenciasIndicadores : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasIndicadores(List<VigenciasIndicadores> setListVigenciasIndicadores) {
        this.listVigenciasIndicadores = setListVigenciasIndicadores;
    }

    public List<VigenciasIndicadores> getFiltrarListVigenciasIndicadores() {
        return filtrarListVigenciasIndicadores;
    }

    public void setFiltrarListVigenciasIndicadores(List<VigenciasIndicadores> setFiltrarListVigenciasIndicadores) {
        this.filtrarListVigenciasIndicadores = setFiltrarListVigenciasIndicadores;
    }

    public List<TiposIndicadores> getListTiposIndicadores() {
        try {
            listTiposIndicadores = administrarEmplVigenciaIndicador.listTiposIndicadores();
            return listTiposIndicadores;
        } catch (Exception e) {
            System.out.println("Error getListTiposIndicadores " + e.toString());
            return null;
        }
    }

    public void setListTiposIndicadores(List<TiposIndicadores> setListTiposIndicadores) {
        this.listTiposIndicadores = setListTiposIndicadores;
    }

    public TiposIndicadores getTipoIndicadorSeleccionado() {
        return tipoIndicadorSeleccionado;
    }

    public void setTipoIndicadorSeleccionado(TiposIndicadores setTipoIndicadorSeleccionado) {
        this.tipoIndicadorSeleccionado = setTipoIndicadorSeleccionado;
    }

    public List<TiposIndicadores> getFiltrarListTiposIndicadores() {
        return filtrarListTiposIndicadores;
    }

    public void setFiltrarListTiposIndicadores(List<TiposIndicadores> setFiltrarListTiposIndicadores) {
        this.filtrarListTiposIndicadores = setFiltrarListTiposIndicadores;
    }

    public List<Indicadores> getListIndicadores() {
        try {
            listIndicadores = administrarEmplVigenciaIndicador.listIndicadores();
            return listIndicadores;
        } catch (Exception e) {
            System.out.println("Error getListIndicadores " + e.toString());
            return null;
        }
    }

    public void setListIndicadores(List<Indicadores> setListIndicadores) {
        this.listIndicadores = setListIndicadores;
    }

    public Indicadores getIndicadorSeleccionado() {
        return indicadorSeleccionado;
    }

    public void setIndicadorSeleccionado(Indicadores setIndicadorSeleccionado) {
        this.indicadorSeleccionado = setIndicadorSeleccionado;
    }

    public List<Indicadores> getFiltrarListIndicadores() {
        return filtrarListIndicadores;
    }

    public void setFiltrarListIndicadores(List<Indicadores> setFiltrarListIndicadores) {
        this.filtrarListIndicadores = setFiltrarListIndicadores;
    }

    public List<VigenciasIndicadores> getListVigenciaIndicadorModificar() {
        return listVigenciaIndicadorModificar;
    }

    public void setListVigenciaIndicadorModificar(List<VigenciasIndicadores> setListVigenciaIndicadorModificar) {
        this.listVigenciaIndicadorModificar = setListVigenciaIndicadorModificar;
    }

    public VigenciasIndicadores getNuevaVigencia() {
        return nuevaVigencia;
    }

    public void setNuevaVigencia(VigenciasIndicadores setNuevaVigencia) {
        this.nuevaVigencia = setNuevaVigencia;
    }

    public List<VigenciasIndicadores> getListVigenciaIndicadorBorrar() {
        return listVigenciaIndicadorBorrar;
    }

    public void setListVigenciaIndicadorBorrar(List<VigenciasIndicadores> getListVigenciaIndicadorBorrar) {
        this.listVigenciaIndicadorBorrar = getListVigenciaIndicadorBorrar;
    }

    public VigenciasIndicadores getEditarVigenciaIndicador() {
        return editarVigenciaIndicador;
    }

    public void setEditarVigenciaIndicador(VigenciasIndicadores setEditarVigenciaIndicador) {
        this.editarVigenciaIndicador = setEditarVigenciaIndicador;
    }

    public VigenciasIndicadores getDuplicarVigenciaIndicador() {
        return duplicarVigenciaIndicador;
    }

    public void setDuplicarVigenciaIndicador(VigenciasIndicadores setDuplicarVigenciaIndicador) {
        this.duplicarVigenciaIndicador = setDuplicarVigenciaIndicador;
    }

    public List<VigenciasIndicadores> getListVigenciaIndicadorCrear() {
        return listVigenciaIndicadorCrear;
    }

    public void setListVigenciaIndicadorCrear(List<VigenciasIndicadores> setListVigenciaIndicadorCrear) {
        this.listVigenciaIndicadorCrear = setListVigenciaIndicadorCrear;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
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

    public Empleados getEmpleado() {
        return empleado;
    }

    public VigenciasIndicadores getVigenciaTablaSeleccionada() {
        getListVigenciasIndicadores();
        if (listVigenciasIndicadores != null) {
            int tam = listVigenciasIndicadores.size();
            if (tam > 0) {
                vigenciaTablaSeleccionada = listVigenciasIndicadores.get(0);
            }
        }
        return vigenciaTablaSeleccionada;
    }

    public void setVigenciaTablaSeleccionada(VigenciasIndicadores vigenciaTablaSeleccionada) {
        this.vigenciaTablaSeleccionada = vigenciaTablaSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroTipo() {
        getListTiposIndicadores();
        if (listTiposIndicadores != null) {
            infoRegistroTipo = "Cantidad de registros : " + listTiposIndicadores.size();
        } else {
            infoRegistroTipo = "Cantidad de registros : 0";
        }
        return infoRegistroTipo;
    }

    public void setInfoRegistroTipo(String infoRegistroTipo) {
        this.infoRegistroTipo = infoRegistroTipo;
    }

    public String getInfoRegistroIndicador() {
        getListIndicadores();
        if (listIndicadores != null) {
            infoRegistroIndicador = "Cantidad de registros : " + listIndicadores.size();
        } else {
            infoRegistroIndicador = "Cantidad de registros : 0";
        }
        return infoRegistroIndicador;
    }

    public void setInfoRegistroIndicador(String infoRegistroIndicador) {
        this.infoRegistroIndicador = infoRegistroIndicador;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}

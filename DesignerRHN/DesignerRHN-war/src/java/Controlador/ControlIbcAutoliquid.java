/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.IbcsAutoliquidaciones;
import Entidades.Mvrs;
import Entidades.Procesos;
import Entidades.TiposEntidades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarIbcAutoliquidInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
public class ControlIbcAutoliquid implements Serializable {

    @EJB
    AdministrarIbcAutoliquidInterface administrarIBCAuto;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<IbcsAutoliquidaciones> listIbcsAutoliquidaciones;
    private List<IbcsAutoliquidaciones> filtrarListIbcsAutoliquidaciones;
    //
    private List<TiposEntidades> listTiposEntidades;
    private TiposEntidades tipoEntidadActual;
    private int registroActual;
    //
    private List<Procesos> listProcesos;
    private List<Procesos> filtrarListProcesos;
    private Procesos procesoSeleccionado;
    //
    //
    private int tipoActualizacion;
    private int bandera;
    private TiposEntidades backUpTipoEntidadActual;
    //Columnas Tabla VL
    private Column ibcFechaInicial, ibcFechaFinal, ibcEstado, ibcProceso, ibcValor, ibcFechaPago;
    //Otros
    private boolean aceptar;
    private int index;
    private List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesModificar;
    private boolean guardado, guardarOk;
    public IbcsAutoliquidaciones nuevoIBC;
    private List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesCrear;
    private BigInteger l;
    private int k;
    private List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesBorrar;
    private IbcsAutoliquidaciones editarIBC;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    private IbcsAutoliquidaciones duplicarIBC;
    private boolean permitirIndex, permitirIndexTS;
    //Variables Autompletar
    //private String motivoCambioSueldo, tiposEntidades, tiposSueldos, terceros;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private String nombreXML;
    private String nombreTabla;
    private boolean cambiosIBC;
    private BigInteger secRegistroIBC;
    private BigInteger backUpSecRegistroIBC;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int indexAux;
    private String proceso;
    private Empleados empleado;
    private BigInteger secuenciaEmpleado;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    private BigDecimal auxValor;

    public ControlIbcAutoliquid() {
        secuenciaEmpleado = new BigInteger("10661474");
        empleado = new Empleados();
        registroActual = 0;
        indexAux = 0;
        listProcesos = null;
        backUpTipoEntidadActual = new TiposEntidades();
        tipoEntidadActual = new TiposEntidades();
        listTiposEntidades = null;
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroIBC = null;
        backUpSecRegistroIBC = null;
        listIbcsAutoliquidaciones = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listIbcsAutoliquidacionesBorrar = new ArrayList<IbcsAutoliquidaciones>();
        //crear aficiones
        listIbcsAutoliquidacionesCrear = new ArrayList<IbcsAutoliquidaciones>();
        k = 0;
        //modificar aficiones
        listIbcsAutoliquidacionesModificar = new ArrayList<IbcsAutoliquidaciones>();
        //editar
        editarIBC = new IbcsAutoliquidaciones();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoIBC = new IbcsAutoliquidaciones();
        nuevoIBC.setProceso(new Procesos());
        index = -1;
        bandera = 0;
        permitirIndex = true;
        permitirIndexTS = true;

        nombreTabla = ":formExportarIBCS:datosIBCSExportar";
        nombreXML = "IBCSAutoliquidacionXML";

        duplicarIBC = new IbcsAutoliquidaciones();
        cambiosIBC = false;
    }

    public void obtenerTipoEntidad(BigInteger empl) {
        listIbcsAutoliquidaciones = null;
        listTiposEntidades = null;
        empleado = administrarIBCAuto.empleadoActual(secuenciaEmpleado);
        tipoEntidadActual = getTipoEntidadActual();
        getListIbcsAutoliquidaciones();
    }

    public void anteriorTipoEntidad() {
        if (cambiosIBC == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
        if (cambiosIBC == false) {
            if (registroActual > 0) {
                registroActual--;
                tipoEntidadActual = listTiposEntidades.get(registroActual);
                listIbcsAutoliquidaciones = null;
                getListIbcsAutoliquidaciones();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tipoEntidadActual");
                context.update("form:datosIBCS");
            }
        }
    }

    public void siguienteTipoEntidad() {
        if (cambiosIBC == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
        if (cambiosIBC == false) {
            if (registroActual < (listTiposEntidades.size() - 1)) {
                registroActual++;
                tipoEntidadActual = listTiposEntidades.get(registroActual);
                listIbcsAutoliquidaciones = null;
                getListIbcsAutoliquidaciones();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:tipoEntidadActual");
                context.update("form:datosIBCS");
            }
        }
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarIBCS(int indice) {
        if (tipoLista == 0) {

            listIbcsAutoliquidaciones.get(indice).setValor(auxValor);
            if (!listIbcsAutoliquidacionesCrear.contains(listIbcsAutoliquidaciones.get(indice))) {
                if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                    listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(indice));
                } else if (!listIbcsAutoliquidacionesModificar.contains(listIbcsAutoliquidaciones.get(indice))) {
                    listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroIBC = null;
        } else {
            filtrarListIbcsAutoliquidaciones.get(indice).setValor(auxValor);
            if (!listIbcsAutoliquidacionesCrear.contains(filtrarListIbcsAutoliquidaciones.get(indice))) {
                if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                    listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(indice));
                } else if (!listIbcsAutoliquidacionesModificar.contains(filtrarListIbcsAutoliquidaciones.get(indice))) {
                    listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroIBC = null;
        }
        cambiosIBC = true;
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            IbcsAutoliquidaciones auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listIbcsAutoliquidaciones.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarListIbcsAutoliquidaciones.get(index);
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
            if (nuevoIBC.getFechafinal() != null) {
                if (nuevoIBC.getFechainicial().after(fechaParametro) && nuevoIBC.getFechainicial().before(nuevoIBC.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevoIBC.getFechafinal() == null) {
                if (nuevoIBC.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarIBC.getFechafinal() != null) {
                if (duplicarIBC.getFechainicial().after(fechaParametro) && duplicarIBC.getFechainicial().before(duplicarIBC.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarIBC.getFechafinal() == null) {
                if (duplicarIBC.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        IbcsAutoliquidaciones auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listIbcsAutoliquidaciones.get(index);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarListIbcsAutoliquidaciones.get(index);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistro(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarIBCS(i);
            } else {
                if (tipoLista == 0) {
                    listIbcsAutoliquidaciones.get(i).setFechafinal(fechaFin);
                    listIbcsAutoliquidaciones.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarListIbcsAutoliquidaciones.get(i).setFechafinal(fechaFin);
                    filtrarListIbcsAutoliquidaciones.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosIBCS");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listIbcsAutoliquidaciones.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarListIbcsAutoliquidaciones.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosIBCS");
            context.execute("errorRegNew.show()");
        }
    }

    public void modificarIBCSAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROCESO")) {
            if (tipoLista == 0) {
                listIbcsAutoliquidaciones.get(indice).getProceso().setDescripcion(proceso);
            } else {
                filtrarListIbcsAutoliquidaciones.get(indice).getProceso().setDescripcion(proceso);
            }
            for (int i = 0; i < listProcesos.size(); i++) {
                if (listProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listIbcsAutoliquidaciones.get(indice).setProceso(listProcesos.get(indiceUnicoElemento));
                } else {
                    filtrarListIbcsAutoliquidaciones.get(indice).setProceso(listProcesos.get(indiceUnicoElemento));
                }
                listProcesos.clear();
                getListProcesos();
            } else {
                permitirIndex = false;
                context.update("form:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listIbcsAutoliquidacionesCrear.contains(listIbcsAutoliquidaciones.get(indice))) {
                    if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                        listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(indice));
                    } else if (!listIbcsAutoliquidacionesModificar.contains(listIbcsAutoliquidaciones.get(indice))) {
                        listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroIBC = null;
            } else {
                if (!listIbcsAutoliquidacionesCrear.contains(filtrarListIbcsAutoliquidaciones.get(indice))) {
                    if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                        listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(indice));
                    } else if (!listIbcsAutoliquidacionesModificar.contains(filtrarListIbcsAutoliquidaciones.get(indice))) {
                        listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroIBC = null;
            }
            cambiosIBC = true;
        }
        context.update("form:datosIBCS");
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        cualCelda = celda;
        index = indice;
        indexAux = indice;
        if (tipoLista == 0) {
            secRegistroIBC = listIbcsAutoliquidaciones.get(index).getSecuencia();
            fechaFin = listIbcsAutoliquidaciones.get(index).getFechafinal();
            fechaIni = listIbcsAutoliquidaciones.get(index).getFechainicial();
            auxValor = listIbcsAutoliquidaciones.get(index).getValor();
            if (cualCelda == 3) {
                proceso = listIbcsAutoliquidaciones.get(index).getProceso().getDescripcion();
            }
        }
        if (tipoLista == 1) {
            secRegistroIBC = filtrarListIbcsAutoliquidaciones.get(index).getSecuencia();
            fechaFin = filtrarListIbcsAutoliquidaciones.get(index).getFechafinal();
            fechaIni = filtrarListIbcsAutoliquidaciones.get(index).getFechainicial();
            auxValor = filtrarListIbcsAutoliquidaciones.get(index).getValor();
            if (cualCelda == 3) {
                proceso = filtrarListIbcsAutoliquidaciones.get(index).getProceso().getDescripcion();
            }
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (cambiosIBC == true) {
            guardarCambiosIBCS();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosIBCS() {
        if (guardado == false) {
            if (!listIbcsAutoliquidacionesBorrar.isEmpty()) {
                administrarIBCAuto.borrarIbcsAutoliquidaciones(listIbcsAutoliquidacionesBorrar);
                listIbcsAutoliquidacionesBorrar.clear();
            }
            if (!listIbcsAutoliquidacionesCrear.isEmpty()) {
                administrarIBCAuto.crearIbcsAutoliquidaciones(listIbcsAutoliquidacionesCrear);
                listIbcsAutoliquidacionesCrear.clear();
            }
            if (!listIbcsAutoliquidacionesModificar.isEmpty()) {
                administrarIBCAuto.modificarIbcsAutoliquidaciones(listIbcsAutoliquidacionesModificar);
                listIbcsAutoliquidacionesModificar.clear();
            }
            listIbcsAutoliquidaciones = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosIBCS");
            k = 0;
        }
        index = -1;
        secRegistroIBC = null;
        cambiosIBC = false;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
            ibcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
            ibcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
            ibcEstado.setFilterStyle("display: none; visibility: hidden;");
            ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
            ibcProceso.setFilterStyle("display: none; visibility: hidden;");
            ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
            ibcValor.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
            ibcFechaPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIBCS");
            bandera = 0;
            filtrarListIbcsAutoliquidaciones = null;
            tipoLista = 0;
        }
        listIbcsAutoliquidacionesBorrar.clear();
        listIbcsAutoliquidacionesCrear.clear();
        listIbcsAutoliquidacionesModificar.clear();
        index = -1;
        indexAux = -1;
        secRegistroIBC = null;
        k = 0;
        listIbcsAutoliquidaciones = null;
        guardado = true;
        cambiosIBC = false;
        getListIbcsAutoliquidaciones();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosIBCS");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarIBC = listIbcsAutoliquidaciones.get(index);
            }
            if (tipoLista == 1) {
                editarIBC = filtrarListIbcsAutoliquidaciones.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicialD");
                context.execute("editarFechaInicialD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinalD");
                context.execute("editarFechaFinalD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarEstadoD");
                context.execute("editarEstadoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarProcesoD");
                context.execute("editarProcesoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarValorD");
                context.execute("editarValorD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarFechaPagoD");
                context.execute("editarFechaPagoD.show()");
                cualCelda = -1;
            }
        }

        index = -1;
        secRegistroIBC = null;
    }

    public void validarIngresoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:NuevoRegistroIBCS");
        context.execute("NuevoRegistroIBCS.show()");

    }

    public void validarDuplicadoRegistro() {
        if (index >= 0) {
            duplicarIBCS();
        }
    }

    public void validarBorradoRegistro() {
        if (index >= 0) {
            borrarIBCS();
        }
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevoIBCS() {
        if (nuevoIBC.getFechainicial() != null && nuevoIBC.getValor() != null) {
            if (validarFechasRegistro(1) == true) {
                if (bandera == 1) {
                    ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
                    ibcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
                    ibcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
                    ibcEstado.setFilterStyle("display: none; visibility: hidden;");
                    ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
                    ibcProceso.setFilterStyle("display: none; visibility: hidden;");
                    ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
                    ibcValor.setFilterStyle("display: none; visibility: hidden;");
                    ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
                    ibcFechaPago.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosIBCS");
                    bandera = 0;
                    filtrarListIbcsAutoliquidaciones = null;
                    tipoLista = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
                k++;
                BigInteger var = BigInteger.valueOf(k);
                nuevoIBC.setSecuencia(var);
                nuevoIBC.setTipoentidad(tipoEntidadActual);
                listIbcsAutoliquidacionesCrear.add(nuevoIBC);
                listIbcsAutoliquidaciones.add(nuevoIBC);
                ////------////
                nuevoIBC = new IbcsAutoliquidaciones();
                nuevoIBC.setProceso(new Procesos());

                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosIBCS");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("NuevoRegistroIBCS.hide()");
                cambiosIBC = true;
                index = -1;
                secRegistroIBC = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevoIBCS() {
        nuevoIBC = new IbcsAutoliquidaciones();
        nuevoIBC.setProceso(new Procesos());

        index = -1;
        secRegistroIBC = null;
    }

    ////////--- //// ---////
    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarIBCS() {
        if (index >= 0) {
            duplicarIBC = new IbcsAutoliquidaciones();

            if (tipoLista == 0) {
                duplicarIBC.setEmpleado(listIbcsAutoliquidaciones.get(index).getEmpleado());
                duplicarIBC.setEstado(listIbcsAutoliquidaciones.get(index).getEstado());
                duplicarIBC.setFechafinal(listIbcsAutoliquidaciones.get(index).getFechafinal());
                duplicarIBC.setFechainicial(listIbcsAutoliquidaciones.get(index).getFechainicial());
                duplicarIBC.setFechapago(listIbcsAutoliquidaciones.get(index).getFechapago());
                duplicarIBC.setFechasistema(listIbcsAutoliquidaciones.get(index).getFechasistema());
                duplicarIBC.setProceso(listIbcsAutoliquidaciones.get(index).getProceso());
                duplicarIBC.setValor(listIbcsAutoliquidaciones.get(index).getValor());
            }

            if (tipoLista == 1) {
                duplicarIBC.setEmpleado(filtrarListIbcsAutoliquidaciones.get(index).getEmpleado());
                duplicarIBC.setEstado(filtrarListIbcsAutoliquidaciones.get(index).getEstado());
                duplicarIBC.setFechafinal(filtrarListIbcsAutoliquidaciones.get(index).getFechafinal());
                duplicarIBC.setFechainicial(filtrarListIbcsAutoliquidaciones.get(index).getFechainicial());
                duplicarIBC.setFechapago(filtrarListIbcsAutoliquidaciones.get(index).getFechapago());
                duplicarIBC.setFechasistema(filtrarListIbcsAutoliquidaciones.get(index).getFechasistema());
                duplicarIBC.setProceso(filtrarListIbcsAutoliquidaciones.get(index).getProceso());
                duplicarIBC.setValor(filtrarListIbcsAutoliquidaciones.get(index).getValor());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroIBCS");
            context.execute("DuplicarRegistroIBCS.show()");
            index = -1;
            secRegistroIBC = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        if (duplicarIBC.getFechainicial() != null && duplicarIBC.getValor() != null) {
            if (validarFechasRegistro(2) == true) {
                if (bandera == 1) {
                    ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
                    ibcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
                    ibcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
                    ibcEstado.setFilterStyle("display: none; visibility: hidden;");
                    ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
                    ibcProceso.setFilterStyle("display: none; visibility: hidden;");
                    ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
                    ibcValor.setFilterStyle("display: none; visibility: hidden;");
                    ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
                    ibcFechaPago.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosIBCS");
                    bandera = 0;
                    filtrarListIbcsAutoliquidaciones = null;
                    tipoLista = 0;
                }
                k++;
                BigInteger var = BigInteger.valueOf(k);
                duplicarIBC.setSecuencia(var);
                duplicarIBC.setTipoentidad(tipoEntidadActual);
                listIbcsAutoliquidacionesCrear.add(duplicarIBC);
                listIbcsAutoliquidaciones.add(duplicarIBC);
                duplicarIBC = new IbcsAutoliquidaciones();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosIBCS");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("DuplicarRegistroIBCS.hide()");
                cambiosIBC = true;
                index = -1;
                secRegistroIBC = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
    }

    public void limpiarDuplicarIBCS() {
        duplicarIBC = new IbcsAutoliquidaciones();
        duplicarIBC.setProceso(new Procesos());
        index = -1;
        secRegistroIBC = null;
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarIBCS() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listIbcsAutoliquidacionesModificar.isEmpty() && listIbcsAutoliquidacionesModificar.contains(listIbcsAutoliquidaciones.get(index))) {
                    int modIndex = listIbcsAutoliquidacionesModificar.indexOf(listIbcsAutoliquidaciones.get(index));
                    listIbcsAutoliquidacionesModificar.remove(modIndex);
                    listIbcsAutoliquidacionesBorrar.add(listIbcsAutoliquidaciones.get(index));
                } else if (!listIbcsAutoliquidacionesCrear.isEmpty() && listIbcsAutoliquidacionesCrear.contains(listIbcsAutoliquidaciones.get(index))) {
                    int crearIndex = listIbcsAutoliquidacionesCrear.indexOf(listIbcsAutoliquidaciones.get(index));
                    listIbcsAutoliquidacionesCrear.remove(crearIndex);
                } else {
                    listIbcsAutoliquidacionesBorrar.add(listIbcsAutoliquidaciones.get(index));
                }
                listIbcsAutoliquidaciones.remove(index);
            }
            if (tipoLista == 1) {
                if (!listIbcsAutoliquidacionesModificar.isEmpty() && listIbcsAutoliquidacionesModificar.contains(filtrarListIbcsAutoliquidaciones.get(index))) {
                    int modIndex = listIbcsAutoliquidacionesModificar.indexOf(filtrarListIbcsAutoliquidaciones.get(index));
                    listIbcsAutoliquidacionesModificar.remove(modIndex);
                    listIbcsAutoliquidacionesBorrar.add(filtrarListIbcsAutoliquidaciones.get(index));
                } else if (!listIbcsAutoliquidacionesCrear.isEmpty() && listIbcsAutoliquidacionesCrear.contains(filtrarListIbcsAutoliquidaciones.get(index))) {
                    int crearIndex = listIbcsAutoliquidacionesCrear.indexOf(filtrarListIbcsAutoliquidaciones.get(index));
                    listIbcsAutoliquidacionesCrear.remove(crearIndex);
                } else {
                    listIbcsAutoliquidacionesBorrar.add(filtrarListIbcsAutoliquidaciones.get(index));
                }
                int VLIndex = listIbcsAutoliquidaciones.indexOf(filtrarListIbcsAutoliquidaciones.get(index));
                listIbcsAutoliquidaciones.remove(VLIndex);
                filtrarListIbcsAutoliquidaciones.remove(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosIBCS");
            index = -1;
            secRegistroIBC = null;
            cambiosIBC = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (index >= 0) {
            filtradoIBCS();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoIBCS() {
        if (bandera == 0) {
            ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
            ibcFechaInicial.setFilterStyle("width: 50px");
            ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
            ibcFechaFinal.setFilterStyle("width: 50px");
            ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
            ibcEstado.setFilterStyle("width: 80px");
            ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
            ibcProceso.setFilterStyle("width: 80px");
            ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
            ibcValor.setFilterStyle("width: 80px");
            ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
            ibcFechaPago.setFilterStyle("width: 50px");
            RequestContext.getCurrentInstance().update("form:datosIBCS");
            bandera = 1;
        } else if (bandera == 1) {
            ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
            ibcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
            ibcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
            ibcEstado.setFilterStyle("display: none; visibility: hidden;");
            ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
            ibcProceso.setFilterStyle("display: none; visibility: hidden;");
            ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
            ibcValor.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
            ibcFechaPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIBCS");
            bandera = 0;
            filtrarListIbcsAutoliquidaciones = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            ibcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaInicial");
            ibcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaFinal");
            ibcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            ibcEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcEstado");
            ibcEstado.setFilterStyle("display: none; visibility: hidden;");
            ibcProceso = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcProceso");
            ibcProceso.setFilterStyle("display: none; visibility: hidden;");
            ibcValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcValor");
            ibcValor.setFilterStyle("display: none; visibility: hidden;");
            ibcFechaPago = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosIBCS:ibcFechaPago");
            ibcFechaPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosIBCS");
            bandera = 0;
            filtrarListIbcsAutoliquidaciones = null;
            tipoLista = 0;
        }

        listIbcsAutoliquidacionesBorrar.clear();
        listIbcsAutoliquidacionesCrear.clear();
        listIbcsAutoliquidacionesModificar.clear();
        index = -1;
        secRegistroIBC = null;
        tipoEntidadActual = null;
        registroActual = 0;
        k = 0;
        listIbcsAutoliquidaciones = null;
        guardado = true;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     *
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
            context.update("form:ProcesosDialogo");
            context.execute("ProcesosDialogo.show()");
        }
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de todas las tablas con respecto al
     * index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 3) {
                context.update("form:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    /**
     * Valida un proceso de nuevo registro dentro de la pagina con respecto a la
     * posicion en la pagina
     */
    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("PROCESO")) {
            if (tipoNuevo == 1) {
                proceso = nuevoIBC.getProceso().getDescripcion();
            } else if (tipoNuevo == 2) {
                proceso = duplicarIBC.getProceso().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoIBCS(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("PROCESO")) {
            if (tipoNuevo == 1) {
                nuevoIBC.getProceso().setDescripcion(proceso);
            } else if (tipoNuevo == 2) {
                duplicarIBC.getProceso().setDescripcion(proceso);
            }
            for (int i = 0; i < listProcesos.size(); i++) {
                if (listProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoIBC.setProceso(listProcesos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProcesoIBCS");
                } else if (tipoNuevo == 2) {
                    duplicarIBC.setProceso(listProcesos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicaProcesoIBCS");
                }
                listProcesos.clear();
                getListProcesos();
            } else {
                context.update("form:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaProcesoIBCS");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicaProcesoIBCS");
                }
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarProceso() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listIbcsAutoliquidaciones.get(index).setProceso(procesoSeleccionado);
                if (!listIbcsAutoliquidacionesCrear.contains(listIbcsAutoliquidaciones.get(index))) {
                    if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                        listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(index));
                    } else if (!listIbcsAutoliquidacionesModificar.contains(listIbcsAutoliquidaciones.get(index))) {
                        listIbcsAutoliquidacionesModificar.add(listIbcsAutoliquidaciones.get(index));
                    }
                }
            } else {
                filtrarListIbcsAutoliquidaciones.get(index).setProceso(procesoSeleccionado);
                if (!listIbcsAutoliquidacionesCrear.contains(filtrarListIbcsAutoliquidaciones.get(index))) {
                    if (listIbcsAutoliquidacionesModificar.isEmpty()) {
                        listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(index));
                    } else if (!listIbcsAutoliquidacionesModificar.contains(filtrarListIbcsAutoliquidaciones.get(index))) {
                        listIbcsAutoliquidacionesModificar.add(filtrarListIbcsAutoliquidaciones.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosIBCS");
        } else if (tipoActualizacion == 1) {
            nuevoIBC.setProceso(procesoSeleccionado);
            context.update("formularioDialogos:nuevaProcesoIBCS");
        } else if (tipoActualizacion == 2) {
            duplicarIBC.setProceso(procesoSeleccionado);
            context.update("formularioDialogos:duplicaProcesoIBCS");
        }
        cambiosIBC = true;
        filtrarListProcesos = null;
        procesoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroIBC = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioProceso() {
        filtrarListProcesos = null;
        procesoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroIBC = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    /**
     * Selecciona la tabla a exportar XML con respecto al index activo
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (index >= 0) {
            nombreTabla = ":formExportarIBCS:datosIBCSExportar";
            nombreXML = "IBCSAutoliquidacionXML";
        }
        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_IBC();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_IBC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarIBCS:datosIBCSExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "IBCSAutoliquidacionPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroIBC = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLS_IBC();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_IBC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarIBCS:datosIBCSExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "IBCSAutoliquidacionXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroIBC = null;
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

    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS
    public void verificarRastroTabla() {
        verificarRastroIBCS();
        index = -1;
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroIBCS() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listIbcsAutoliquidaciones != null) {
            if (secRegistroIBC != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroIBC, "IBCSAUTOLIQUIDACIONES");
                backUpSecRegistroIBC = secRegistroIBC;
                backUp = secRegistroIBC;
                secRegistroIBC = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "IbcsAutoliquidaciones";
                    msnConfirmarRastro = "La tabla IBCSAUTOLIQUIDACIONES tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("IBCSAUTOLIQUIDACIONES")) {
                nombreTablaRastro = "IbcsAutoliquidaciones";
                msnConfirmarRastroHistorico = "La tabla IBCSAUTOLIQUIDACIONES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public List<IbcsAutoliquidaciones> getListIbcsAutoliquidaciones() {
        try {
            if (listIbcsAutoliquidaciones == null) {
                listIbcsAutoliquidaciones = administrarIBCAuto.listIBCSAutoliquidaciones(tipoEntidadActual.getSecuencia(), empleado.getSecuencia());
            }
            return listIbcsAutoliquidaciones;
        } catch (Exception e) {
            System.out.println("Error getListIbcsAutoliquidaciones : " + e.toString());
            return null;
        }

    }

    public void setListIbcsAutoliquidaciones(List<IbcsAutoliquidaciones> listIbcsAutoliquidaciones) {
        this.listIbcsAutoliquidaciones = listIbcsAutoliquidaciones;
    }

    public List<IbcsAutoliquidaciones> getFiltrarListIbcsAutoliquidaciones() {
        return filtrarListIbcsAutoliquidaciones;
    }

    public void setFiltrarListIbcsAutoliquidaciones(List<IbcsAutoliquidaciones> filtrarListIbcsAutoliquidaciones) {
        this.filtrarListIbcsAutoliquidaciones = filtrarListIbcsAutoliquidaciones;
    }

    public List<TiposEntidades> getListTiposEntidades() {
        try {
            if (listTiposEntidades == null) {
                listTiposEntidades = administrarIBCAuto.listTiposEntidadesIBCS();
            }
            return listTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error getListTiposEntidades : " + e.toString());
            return null;
        }

    }

    public void setListTiposEntidades(List<TiposEntidades> listTiposEntidades) {
        this.listTiposEntidades = listTiposEntidades;
    }

    public TiposEntidades getTipoEntidadActual() {
        try {
            getListTiposEntidades();
            int tam = listTiposEntidades.size();
            if (tam >= 1) {
                tipoEntidadActual = listTiposEntidades.get(registroActual);
            }
            return tipoEntidadActual;
        } catch (Exception e) {
            System.out.println("Error getTipoEntidadActual : " + e.toString());
            return null;
        }
    }

    public void setTipoEntidadActual(TiposEntidades tipoEntidadActual) {
        this.tipoEntidadActual = tipoEntidadActual;
    }

    public List<Procesos> getListProcesos() {
        try {
            if (listProcesos == null) {
                listProcesos = administrarIBCAuto.listProcesos();
            }
            return listProcesos;
        } catch (Exception e) {
            System.out.println("Error getListProcesos : " + e.toString());
            return null;
        }

    }

    public void setListProcesos(List<Procesos> listProcesos) {
        this.listProcesos = listProcesos;
    }

    public List<Procesos> getFiltrarListProcesos() {
        return filtrarListProcesos;
    }

    public void setFiltrarListProcesos(List<Procesos> filtrarListProcesos) {
        this.filtrarListProcesos = filtrarListProcesos;
    }

    public Procesos getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(Procesos procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public List<IbcsAutoliquidaciones> getListIbcsAutoliquidacionesModificar() {
        return listIbcsAutoliquidacionesModificar;
    }

    public void setListIbcsAutoliquidacionesModificar(List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesModificar) {
        this.listIbcsAutoliquidacionesModificar = listIbcsAutoliquidacionesModificar;
    }

    public IbcsAutoliquidaciones getNuevoIBC() {
        return nuevoIBC;
    }

    public void setNuevoIBC(IbcsAutoliquidaciones nuevoIBC) {
        this.nuevoIBC = nuevoIBC;
    }

    public List<IbcsAutoliquidaciones> getListIbcsAutoliquidacionesCrear() {
        return listIbcsAutoliquidacionesCrear;
    }

    public void setListIbcsAutoliquidacionesCrear(List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesCrear) {
        this.listIbcsAutoliquidacionesCrear = listIbcsAutoliquidacionesCrear;
    }

    public List<IbcsAutoliquidaciones> getListIbcsAutoliquidacionesBorrar() {
        return listIbcsAutoliquidacionesBorrar;
    }

    public void setListIbcsAutoliquidacionesBorrar(List<IbcsAutoliquidaciones> listIbcsAutoliquidacionesBorrar) {
        this.listIbcsAutoliquidacionesBorrar = listIbcsAutoliquidacionesBorrar;
    }

    public IbcsAutoliquidaciones getEditarIBC() {
        return editarIBC;
    }

    public void setEditarIBC(IbcsAutoliquidaciones editarIBC) {
        this.editarIBC = editarIBC;
    }

    public IbcsAutoliquidaciones getDuplicarIBC() {
        return duplicarIBC;
    }

    public void setDuplicarIBC(IbcsAutoliquidaciones duplicarIBC) {
        this.duplicarIBC = duplicarIBC;
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

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public BigInteger getBackUpSecRegistroIBC() {
        return backUpSecRegistroIBC;
    }

    public void setBackUpSecRegistroIBC(BigInteger backUpSecRegistroIBC) {
        this.backUpSecRegistroIBC = backUpSecRegistroIBC;
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

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }
}

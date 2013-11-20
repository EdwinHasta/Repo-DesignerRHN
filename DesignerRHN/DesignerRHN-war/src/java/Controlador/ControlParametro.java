package Controlador;

import Entidades.Estructuras;
import Entidades.Parametros;
import Entidades.ParametrosEstructuras;
import Entidades.Procesos;
import Entidades.TiposTrabajadores;
import Entidades.Usuarios;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarParametrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

@ManagedBean
@SessionScoped
public class ControlParametro implements Serializable {

    @EJB
    AdministrarParametrosInterface administrarParametros;
    private ParametrosEstructuras parametroLiquidacion;
    private List<Estructuras> lovEstructuras, filtradoLovEstructuras;
    private Estructuras seleccionEstructura;
    private List<TiposTrabajadores> lovTiposTrabajadores, filtradoLovTiposTrabajadores;
    private TiposTrabajadores seleccionTipoTrabajador;
    private List<Procesos> lovProcesos, filtradoLovProcesos;
    private Procesos seleccionProcesos;
    //OTROS
    private boolean aceptar, guardado, borrados;
    private BigInteger secRegistro;
    private int index, tipoLista, cualCelda, bandera, editor;
    private Integer empleadosParametrizados;
    //AUTOCOMPLETAR
    private String nombreEstructura, nombreTipoTrabajador, nombreProceso;
    //EMPLEADOS PARA LIQUIDAR
    private List<Parametros> empleadosParametros;
    private List<Parametros> filtradoEmpleadosParametros;
    private List<Parametros> empleadosParametrosEliminados;
    private Parametros editarEmpleadosParametros;
    //COLUMNAS
    private Column FechaDesde, FechaHasta, Codigo, pApellido, sApellido, nombre, estadoParametro;

    public ControlParametro() {
        aceptar = true;
        guardado = true;
        borrados = false;
        bandera = 0;
        editor = 0;
        index = -1;
        cualCelda = -1;
        tipoLista = 0;
        empleadosParametrosEliminados = new ArrayList<Parametros>();
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void llamarLov() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        Map<String, String> map = contexto.getExternalContext().getRequestParameterMap();
        String LOV = map.get("LOV");
        if (LOV.equals("ESTRUCTURA")) {
            context.update("formularioDialogos:estructurasDialogo");
            context.execute("estructurasDialogo.show()");
        } else if (LOV.equals("TIPO TRABAJADOR")) {
            context.update("formularioDialogos:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        } else if (LOV.equals("PROCESO")) {
            context.update("formularioDialogos:ProcesosDialogo");
            context.execute("ProcesosDialogo.show()");
        }
    }

    public void ubicacionCampo() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        Map<String, String> map = contexto.getExternalContext().getRequestParameterMap();
        String campo = map.get("CAMPO");
        editor = 0;
        if (campo.equals("FECHA DESDE")) {
            cualCelda = 0;
        } else if (campo.equals("FECHA HASTA")) {
            cualCelda = 1;
        } else if (campo.equals("FECHA CORTE")) {
            cualCelda = 2;
        } else if (campo.equals("CODIGO CC")) {
            cualCelda = 4;
        } else if (campo.equals("NOMBRE CC")) {
            cualCelda = 5;
        } else if (campo.equals("DESDE")) {
            cualCelda = 8;
        } else if (campo.equals("HASTA")) {
            cualCelda = 9;
        } else if (campo.equals("CODIGO")) {
            cualCelda = 10;
        } else if (campo.equals("PAPELLIDO")) {
            cualCelda = 11;
        } else if (campo.equals("SAPELLIDO")) {
            cualCelda = 12;
        } else if (campo.equals("NOMBRE")) {
            cualCelda = 13;
        } else if (campo.equals("ESTADO")) {
            cualCelda = 14;
        }
    }

    public void ubicacionCampoTabla() {
        FacesContext contexto = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        Map<String, String> map = contexto.getExternalContext().getRequestParameterMap();
        String campo = map.get("CAMPO");
        String indice = map.get("INDICE");
        index = Integer.parseInt(indice);
        editor = 1;
        if (campo.equals("DESDE")) {
            cualCelda = 8;
        } else if (campo.equals("HASTA")) {
            cualCelda = 9;
        } else if (campo.equals("CODIGO")) {
            cualCelda = 10;
        } else if (campo.equals("PAPELLIDO")) {
            cualCelda = 11;
        } else if (campo.equals("SAPELLIDO")) {
            cualCelda = 12;
        } else if (campo.equals("NOMBRE")) {
            cualCelda = 13;
        } else if (campo.equals("ESTADO")) {
            cualCelda = 14;
        }
    }

    public void actualizarEstructura() {
        RequestContext context = RequestContext.getCurrentInstance();
        parametroLiquidacion.setEstructura(seleccionEstructura);
        context.update("form:Estructura");
        context.update("form:codigoCC");
        context.update("form:nombreCC");
        filtradoLovEstructuras = null;
        seleccionEstructura = null;
        aceptar = true;
        guardado = false;
        context.execute("estructurasDialogo.hide()");
        context.reset("formularioDialogos:lOVEstructuras:globalFilter");
        context.update("formularioDialogos:lOVEstructuras");
    }

    public void cancelarCambioEstructura() {
        filtradoLovEstructuras = null;
        seleccionEstructura = null;
        aceptar = true;
    }

    public void actualizarTipoTrabajador() {
        RequestContext context = RequestContext.getCurrentInstance();
        parametroLiquidacion.setTipotrabajador(seleccionTipoTrabajador);
        context.update("form:tipoTrabajador");
        filtradoLovTiposTrabajadores = null;
        seleccionTipoTrabajador = null;
        aceptar = true;
        guardado = false;
        context.execute("TipoTrabajadorDialogo.hide()");
        context.reset("formularioDialogos:lovTipoTrabajador:globalFilter");
        context.update("formularioDialogos:lovTipoTrabajador");
    }

    public void cancelarCambioTipoTrabajador() {
        filtradoLovTiposTrabajadores = null;
        seleccionTipoTrabajador = null;
        aceptar = true;
    }

    public void actualizarProceso() {
        RequestContext context = RequestContext.getCurrentInstance();
        parametroLiquidacion.setProceso(seleccionProcesos);
        context.update("form:proceso");
        filtradoLovProcesos = null;
        seleccionProcesos = null;
        aceptar = true;
        guardado = false;
        context.execute("ProcesosDialogo.hide()");
        context.reset("formularioDialogos:lovProcesos:globalFilter");
        context.update("formularioDialogos:lovProcesos");
    }

    public void cancelarCambioProceso() {
        filtradoLovProcesos = null;
        seleccionProcesos = null;
        aceptar = true;
    }

    public void valoresBackupAutocompletar(String Campo) {
        editor = 0;
        if (Campo.equals("ESTRUCTURA")) {
            cualCelda = 3;
            nombreEstructura = parametroLiquidacion.getEstructura().getNombre();
        } else if (Campo.equals("TIPO TRABAJADOR")) {
            cualCelda = 6;
            nombreTipoTrabajador = parametroLiquidacion.getTipotrabajador().getNombre();
        } else if (Campo.equals("PROCESO")) {
            cualCelda = 7;
            nombreProceso = parametroLiquidacion.getProceso().getDescripcion();
        }
    }

    public void autocompletar(String confirmarCambio, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        if (confirmarCambio.equalsIgnoreCase("ESTRUCTURA")) {
            parametroLiquidacion.getEstructura().setNombre(nombreEstructura);

            for (int i = 0; i < lovEstructuras.size(); i++) {
                if (lovEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroLiquidacion.setEstructura(lovEstructuras.get(indiceUnicoElemento));
                context.update("form:Estructura");
                context.update("form:codigoCC");
                context.update("form:nombreCC");
                lovEstructuras.clear();
                getLovEstructuras();
                guardado = false;
            } else {
                context.update("formularioDialogos:estructurasDialogo");
                context.execute("estructurasDialogo.show()");
                context.update("form:Estructura");
            }
        } else if (confirmarCambio.equalsIgnoreCase("TIPO TRABAJADOR")) {
            if (!valorConfirmar.equals("")) {
                parametroLiquidacion.getTipotrabajador().setNombre(nombreTipoTrabajador);
                for (int i = 0; i < lovTiposTrabajadores.size(); i++) {
                    if (lovTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    parametroLiquidacion.setTipotrabajador(lovTiposTrabajadores.get(indiceUnicoElemento));
                    context.update("form:tipoTrabajador");
                    lovTiposTrabajadores.clear();
                    getLovTiposTrabajadores();
                    guardado = false;
                } else {
                    context.update("formularioDialogos:TipoTrabajadorDialogo");
                    context.execute("TipoTrabajadorDialogo.show()");
                    context.update("form:tipoTrabajador");
                }
            } else {
                guardado = false;
                parametroLiquidacion.setTipotrabajador(new TiposTrabajadores());
                context.update("form:tipoTrabajador");
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROCESO")) {

            parametroLiquidacion.getProceso().setDescripcion(nombreProceso);
            for (int i = 0; i < lovProcesos.size(); i++) {
                if (lovProcesos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroLiquidacion.setProceso(lovProcesos.get(indiceUnicoElemento));
                context.update("form:proceso");
                lovProcesos.clear();
                getLovProcesos();
                guardado = false;
            } else {
                context.update("formularioDialogos:ProcesosDialogo");
                context.execute("ProcesosDialogo.show()");
                context.update("form:proceso");
            }
        }
    }

    public void guardarCambios() {
        if (guardado == false) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (parametroLiquidacion.getTipotrabajador().getSecuencia() == null) {
                parametroLiquidacion.setTipotrabajador(null);
            }
            if (parametroLiquidacion.getEstructura().getSecuencia() == null) {
                parametroLiquidacion.setEstructura(null);
            }
            if (parametroLiquidacion.getFechadesdecausado() != null
                    && parametroLiquidacion.getFechahastacausado() != null
                    && parametroLiquidacion.getFechasistema() != null
                    && parametroLiquidacion.getProceso() != null) {
                administrarParametros.crearParametroEstructura(parametroLiquidacion);
                parametroLiquidacion = null;
                getParametroLiquidacion();
                context.update("form:panelParametro");
                guardado = true;
                context.update("form:aceptar");
            } else {
                //DIALOGO NO SE PUEDE GUARDAR
            }
            if (borrados == true) {
                administrarParametros.eliminarParametros(empleadosParametrosEliminados);
                empleadosParametrosEliminados.clear();
                borrados = false;
                guardado = true;
                empleadosParametros = null;
                context.update("form:empleadosParametros");
            }
        }
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (editor == 0) {
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaDesde");
                context.execute("editarFechaDesde.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaHasta");
                context.execute("editarFechaHasta.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarFechaCorte");
                context.execute("editarFechaCorte.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarEstructura");
                context.execute("editarEstructura.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarCodigoCC");
                context.execute("editarCodigoCC.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarNombreCentroCosto");
                context.execute("editarNombreCentroCosto.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarTipoTrabajador");
                context.execute("editarTipoTrabajador.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarProceso");
                context.execute("editarProceso.show()");
                cualCelda = -1;
            }
        } else {
            if (index >= 0) {
                if (tipoLista == 0) {
                    editarEmpleadosParametros = empleadosParametros.get(index);
                }
                if (tipoLista == 1) {
                    editarEmpleadosParametros = filtradoEmpleadosParametros.get(index);
                }

                if (cualCelda == 8) {
                    context.update("formularioDialogos:editarDesde");
                    context.execute("editarDesde.show()");
                    cualCelda = -1;
                } else if (cualCelda == 9) {
                    context.update("formularioDialogos:editarHasta");
                    context.execute("editarHasta.show()");
                    cualCelda = -1;
                } else if (cualCelda == 10) {
                    context.update("formularioDialogos:editarCodigo");
                    context.execute("editarCodigo.show()");
                    cualCelda = -1;
                } else if (cualCelda == 11) {
                    context.update("formularioDialogos:editarPApellido");
                    context.execute("editarPApellido.show()");
                    cualCelda = -1;
                } else if (cualCelda == 12) {
                    context.update("formularioDialogos:editarSApellido");
                    context.execute("editarSApellido.show()");
                    cualCelda = -1;
                } else if (cualCelda == 13) {
                    context.update("formularioDialogos:editarNombre");
                    context.execute("editarNombre.show()");
                    cualCelda = -1;
                } else if (cualCelda == 14) {
                    context.update("formularioDialogos:editarEstado");
                    context.execute("editarEstado.show()");
                    cualCelda = -1;
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cualCelda == 3) {
            context.update("formularioDialogos:estructurasDialogo");
            context.execute("estructurasDialogo.show()");
        } else if (cualCelda == 6) {
            context.update("formularioDialogos:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        } else if (cualCelda == 7) {
            context.update("formularioDialogos:ProcesosDialogo");
            context.execute("ProcesosDialogo.show()");
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            FechaDesde = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:FechaDesde");
            FechaDesde.setFilterStyle("width: 50px;");
            FechaHasta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:FechaHasta");
            FechaHasta.setFilterStyle("width: 50px;");
            Codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:Codigo");
            Codigo.setFilterStyle("width: 60px;");
            pApellido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:pApellido");
            pApellido.setFilterStyle("width: 80px;");
            sApellido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:sApellido");
            sApellido.setFilterStyle("width: 80px;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:nombre");
            nombre.setFilterStyle("");
            estadoParametro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:estadoParametro");
            estadoParametro.setFilterStyle("width: 60px;");
            RequestContext.getCurrentInstance().update("form:empleadosParametros");
            bandera = 1;
        } else if (bandera == 1) {
            FechaDesde = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:FechaDesde");
            FechaDesde.setFilterStyle("display: none; visibility: hidden;");
            FechaHasta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:FechaHasta");
            FechaHasta.setFilterStyle("display: none; visibility: hidden;");
            Codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:Codigo");
            Codigo.setFilterStyle("display: none; visibility: hidden;");
            pApellido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:pApellido");
            pApellido.setFilterStyle("display: none; visibility: hidden;");
            sApellido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:sApellido");
            sApellido.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            estadoParametro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadosParametros:estadoParametro");
            estadoParametro.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:empleadosParametros");
            bandera = 0;
            filtradoEmpleadosParametros = null;
            tipoLista = 0;
        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:empleadosParametrosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EmpleadosParametrosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:empleadosParametrosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EmpleadosParametrosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void cambiosCampos() {
        guardado = false;
    }

    public void eliminarParametros(int indice) {
        borrados = true;
        guardado = false;
        empleadosParametrosEliminados.add(empleadosParametros.get(indice));
        empleadosParametros.remove(indice);
        RequestContext.getCurrentInstance().update("form:empleadosParametros");
    }

    public void adicionarEmpleados() {
        guardarCambios();
        if (parametroLiquidacion != null) {
            administrarParametros.adicionarEmpleados(parametroLiquidacion.getSecuencia());
        }
        empleadosParametros = null;
        RequestContext.getCurrentInstance().update("form:empleadosParametros");
    }

    public void consultarEmpleadosParametrizados() {
        if (parametroLiquidacion != null) {
            empleadosParametrizados = administrarParametros.empleadosParametrizados(parametroLiquidacion.getProceso().getSecuencia());
            if (empleadosParametrizados == 0) {
                adicionarEmpleados();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:confirmarAdicionarEmpleados");
                context.execute("confirmarAdicionarEmpleados.show()");
            }
        }
    }

    public void salir() {
        parametroLiquidacion = null;
        empleadosParametros = null;
        empleadosParametrosEliminados.clear();
        borrados = false;
        lovEstructuras = null;
        lovProcesos = null;
        lovTiposTrabajadores = null;
    }

    public void borrarParametros() {
        empleadosParametros = null;
        empleadosParametrosEliminados.clear();
        borrados = false;
        administrarParametros.borrarParametros(parametroLiquidacion.getSecuencia());
        RequestContext.getCurrentInstance().update("form:empleadosParametros");
    }
    //GETTER AND SETTER

    public ParametrosEstructuras getParametroLiquidacion() {
        if (parametroLiquidacion == null) {
            parametroLiquidacion = administrarParametros.parametrosLiquidacion();
            if (parametroLiquidacion == null) {
                Usuarios au = administrarParametros.usuarioActual();
                parametroLiquidacion = new ParametrosEstructuras();
                parametroLiquidacion.setUsuario(au);
                parametroLiquidacion.setProceso(new Procesos());
                parametroLiquidacion.setSecuencia(BigInteger.valueOf(0));
            }
        }
        return parametroLiquidacion;
    }

    public void setParametroLiquidacion(ParametrosEstructuras parametroLiquidacion) {
        this.parametroLiquidacion = parametroLiquidacion;
    }

    public List<Estructuras> getLovEstructuras() {
        lovEstructuras = administrarParametros.lovEstructuras();
        return lovEstructuras;
    }

    public void setLovEstructuras(List<Estructuras> lovEstructuras) {
        this.lovEstructuras = lovEstructuras;
    }

    public List<Estructuras> getFiltradoLovEstructuras() {
        return filtradoLovEstructuras;
    }

    public void setFiltradoLovEstructuras(List<Estructuras> filtradoLovEstructuras) {
        this.filtradoLovEstructuras = filtradoLovEstructuras;
    }

    public Estructuras getSeleccionEstructura() {
        return seleccionEstructura;
    }

    public void setSeleccionEstructura(Estructuras seleccionEstructura) {
        this.seleccionEstructura = seleccionEstructura;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<TiposTrabajadores> getLovTiposTrabajadores() {
        lovTiposTrabajadores = administrarParametros.lovTiposTrabajadores();
        return lovTiposTrabajadores;
    }

    public void setLovTiposTrabajadores(List<TiposTrabajadores> lovTiposTrabajadores) {
        this.lovTiposTrabajadores = lovTiposTrabajadores;
    }

    public List<TiposTrabajadores> getFiltradoLovTiposTrabajadores() {
        return filtradoLovTiposTrabajadores;
    }

    public void setFiltradoLovTiposTrabajadores(List<TiposTrabajadores> filtradoLovTiposTrabajadores) {
        this.filtradoLovTiposTrabajadores = filtradoLovTiposTrabajadores;
    }

    public TiposTrabajadores getSeleccionTipoTrabajador() {
        return seleccionTipoTrabajador;
    }

    public void setSeleccionTipoTrabajador(TiposTrabajadores seleccionTipoTrabajador) {
        this.seleccionTipoTrabajador = seleccionTipoTrabajador;
    }

    public List<Procesos> getLovProcesos() {
        lovProcesos = administrarParametros.lovProcesos();
        return lovProcesos;
    }

    public void setLovProcesos(List<Procesos> lovProcesos) {
        this.lovProcesos = lovProcesos;
    }

    public List<Procesos> getFiltradoLovProcesos() {
        return filtradoLovProcesos;
    }

    public void setFiltradoLovProcesos(List<Procesos> filtradoLovProcesos) {
        this.filtradoLovProcesos = filtradoLovProcesos;
    }

    public Procesos getSeleccionProcesos() {
        return seleccionProcesos;
    }

    public void setSeleccionProcesos(Procesos seleccionProcesos) {
        this.seleccionProcesos = seleccionProcesos;
    }

    public List<Parametros> getEmpleadosParametros() {
        if (empleadosParametros == null) {
            empleadosParametros = administrarParametros.empleadosParametros();
            if (empleadosParametros != null && !empleadosParametros.isEmpty()) {
                for (int i = 0; i < empleadosParametros.size(); i++) {
                    empleadosParametros.get(i).setEstadoParametro(administrarParametros.estadoParametro(empleadosParametros.get(i).getSecuencia()));
                }
            }
        }
        return empleadosParametros;
    }

    public void setEmpleadosParametros(List<Parametros> empleadosParametros) {
        this.empleadosParametros = empleadosParametros;
    }

    public List<Parametros> getFiltradoEmpleadosParametros() {
        return filtradoEmpleadosParametros;
    }

    public void setFiltradoEmpleadosParametros(List<Parametros> filtradoEmpleadosParametros) {
        this.filtradoEmpleadosParametros = filtradoEmpleadosParametros;
    }

    public Parametros getEditarEmpleadosParametros() {
        return editarEmpleadosParametros;
    }

    public Integer getEmpleadosParametrizados() {
        return empleadosParametrizados;
    }
}

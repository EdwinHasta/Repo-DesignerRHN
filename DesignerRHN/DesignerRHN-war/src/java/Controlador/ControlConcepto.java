package Controlador;

import Entidades.*;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarConceptosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

@ManagedBean
@SessionScoped
public class ControlConcepto implements Serializable {

    @EJB
    AdministrarConceptosInterface administrarConceptos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Conceptos> listaConceptosEmpresa;
    private List<Conceptos> filtradoConceptosEmpresa;
    //Lista de valores de unidades
    private List<Unidades> listaUnidades;
    private Unidades unidadSeleccionada;
    private List<Unidades> filtradoUnidades;
    //Lista de valores de terceros
    private List<Terceros> listaTerceros;
    private Terceros terceroSeleccionado;
    private List<Terceros> filtradoTerceros;
    //Lista de empresas para cargar datos
    private List<Empresas> listaEmpresas;
    private Empresas empresaActual;
    private List<Empresas> filtradoListaEmpresas;
    //Lista completa de conceptos para clonar
    private List<Conceptos> listaConceptosEmpresa_Estado;
    private Conceptos conceptoSeleccionado;
    private List<Conceptos> filtradoConceptosEmpresa_Estado;
    private Conceptos seleccionConceptoEmpresa;
    private Empresas backUpEmpresaActual;
    private Map<String, String> conjuntoC;
    private String estadoConceptoEmpresa;
    private String backUpEstadoConceptoEmpresa;
    private boolean verCambioEmpresa;
    private boolean verCambioEstado;
    private boolean verSeleccionConcepto;
    private boolean verMostrarTodos;
    private boolean mostrarTodos;
    private int tipoActualizacion;
    private boolean permitirIndex;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column columnaCodigo, columnaDescripción, columnaNaturaleza, columnaCodigoUnidad, columnaNombreUnidad, columnaCodigoDesprendible, columnaDescripcionDesplendible,
            columnaIndependienteConcepto, columnaConjunto, columnaFechaAcumulado, columnaNombreTercero, columnaEstado, columnaEnvio, columnaCodigoAlternativo;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<Conceptos> listaConceptosEmpresaModificar;
    private boolean guardado;
    //crear VC
    public Conceptos nuevoConcepto;
    private List<Conceptos> listaConceptosEmpresaCrear;
    private BigInteger nuevoConceptoSecuencia;
    private int paraNuevoConcepto;
    private String mensajeValidacion;
    //borrar VC
    private List<Conceptos> listaConceptosBorrar;
    //editar celda
    private Conceptos editarConcepto;
    private int cualCelda, tipoLista;
    //duplicar
    private Conceptos duplicarConcepto;
    //AUTOCOMPLETAR
    private String codigoUnidad, nombreUnidad, tercero;
    //RASTRO
    private BigInteger secRegistro;
    //CLONAR CONCEPTO
    private Conceptos conceptoOriginal;
    private Conceptos conceptoClon;
    private int cambioConcepto;
    private Conceptos conceptoRegistro;
    private String paginaAnterior;
    //
    private String altoTabla;
    private String infoRegistro;
    private String infoRegistroUnidad, infoRegistroTercero, infoRegistroEmpresa, infoRegistroConcepto;
    //
    private boolean activoDetalle;
    private List<VWAcumulados> listVWAcumuladosPorEmpleado;
    //Advertencias
    private boolean continuarNuevoNat;

    public ControlConcepto() {
        activoDetalle = true;
        altoTabla = "205";
        conceptoRegistro = new Conceptos();
        listaConceptosEmpresa_Estado = null;
        conjuntoC = new LinkedHashMap<String, String>();
        listaUnidades = null;
        listaTerceros = null;
        listaEmpresas = null;
        empresaActual = new Empresas();
        backUpEmpresaActual = new Empresas();
        //Otros
        aceptar = true;
        //borrar aficiones
        listaConceptosBorrar = new ArrayList<Conceptos>();
        //crear aficiones
        listaConceptosEmpresaCrear = new ArrayList<Conceptos>();
        paraNuevoConcepto = 0;
        //modificar aficiones
        listaConceptosEmpresaModificar = new ArrayList<Conceptos>();
        //editar
        editarConcepto = new Conceptos();
        mostrarTodos = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoConcepto = new Conceptos();
        nuevoConcepto.setUnidad(new Unidades());
        nuevoConcepto.setTercero(new Terceros());
        nuevoConcepto.setConjunto(Short.parseShort("45"));
        duplicarConcepto = new Conceptos();
        //CLON
        conceptoClon = new Conceptos();
        conceptoOriginal = new Conceptos();
        permitirIndex = true;
        verCambioEmpresa = false;
        verCambioEstado = false;
        int i = 0;
        conjuntoC.put("45", "45");
        while (i <= 43) {
            i++;
            conjuntoC.put("" + i + "", "" + i + "");
        }

        listVWAcumuladosPorEmpleado = null;
        continuarNuevoNat = false;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarConceptos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            estadoConceptoEmpresa = "TODOS";
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void cambiarEstado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "205";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
                columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptos");
                bandera = 0;
                filtradoConceptosEmpresa = null;
                tipoLista = 0;
            }
            listaConceptosEmpresa = null;
            getListaConceptosEmpresa();
            backUpEstadoConceptoEmpresa = estadoConceptoEmpresa;
            context.update("form:datosConceptos");
            verCambioEstado = false;
        } else {
            verCambioEstado = true;
            context.execute("confirmarGuardar.show()");
        }
        index = -1;
        activoDetalle = true;
        context.update("form:DETALLES");
        secRegistro = null;
        cualCelda = -1;
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
        estadoConceptoEmpresa = "S";
        backUpEstadoConceptoEmpresa = "S";
        listaConceptosEmpresa = null;
        if (listaEmpresas == null) {
            getListaEmpresas();
        }
        getEmpresaActual();
        if (empresaActual == null) {
            empresaActual = listaEmpresas.get(0);
        }
        getListaConceptosEmpresa();
        if (listaConceptosEmpresa != null) {
            infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosConceptos");
    }

    public String redirigir() {
        return paginaAnterior;
    }
    //SELECCIONAR NATURALEZA

    public void seleccionarItem(String itemSeleccionado, int indice, int celda) {
        if (tipoLista == 0) {
            if (celda == 2) {
                if (itemSeleccionado.equals("NETO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("N");
                } else if (itemSeleccionado.equals("GASTO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("G");
                } else if (itemSeleccionado.equals("DESCUENTO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("D");
                } else if (itemSeleccionado.equals("PAGO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("P");
                } else if (itemSeleccionado.equals("PASIVO")) {
                    listaConceptosEmpresa.get(indice).setNaturaleza("L");
                }
            } else if (celda == 11) {
                if (itemSeleccionado.equals("ACTIVO")) {
                    listaConceptosEmpresa.get(indice).setActivo("S");
                } else if (itemSeleccionado.equals("INACTIVO")) {
                    listaConceptosEmpresa.get(indice).setActivo("N");
                }
            } else if (celda == 12) {
                if (itemSeleccionado.equals("SI")) {
                    listaConceptosEmpresa.get(indice).setEnviotesoreria("S");
                } else if (itemSeleccionado.equals("NO")) {
                    listaConceptosEmpresa.get(indice).setEnviotesoreria("N");
                }
            }
            if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {
                if (listaConceptosEmpresaModificar.isEmpty()) {
                    listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                    listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                }
            }
        } else {
            if (celda == 2) {
                if (itemSeleccionado.equals("NETO")) {
                    filtradoConceptosEmpresa.get(indice).setNaturaleza("N");
                } else if (itemSeleccionado.equals("GASTO")) {
                    filtradoConceptosEmpresa.get(indice).setNaturaleza("G");
                } else if (itemSeleccionado.equals("DESCUENTO")) {
                    filtradoConceptosEmpresa.get(indice).setNaturaleza("D");
                } else if (itemSeleccionado.equals("PAGO")) {
                    filtradoConceptosEmpresa.get(indice).setNaturaleza("P");
                } else if (itemSeleccionado.equals("PASIVO")) {
                    filtradoConceptosEmpresa.get(indice).setNaturaleza("L");
                }
            } else if (celda == 11) {
                if (itemSeleccionado.equals("ACTIVO")) {
                    filtradoConceptosEmpresa.get(indice).setActivo("S");
                } else if (itemSeleccionado.equals("INACTIVO")) {
                    filtradoConceptosEmpresa.get(indice).setActivo("N");
                }
            } else if (celda == 12) {
                if (itemSeleccionado.equals("SI")) {
                    filtradoConceptosEmpresa.get(indice).setEnviotesoreria("S");
                } else if (itemSeleccionado.equals("NO")) {
                    filtradoConceptosEmpresa.get(indice).setEnviotesoreria("N");
                }
            }

            if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(indice))) {
                if (listaConceptosEmpresaModificar.isEmpty()) {
                    listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                } else if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(indice))) {
                    listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                }
            }
        }
        if (guardado) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("CODIGOUNIDAD")) {
            if (tipoNuevo == 1) {
                codigoUnidad = nuevoConcepto.getUnidad().getCodigo();
            } else if (tipoNuevo == 2) {
                codigoUnidad = duplicarConcepto.getUnidad().getCodigo();
            }
        } else if (Campo.equals("NOMBREUNIDAD")) {
            if (tipoNuevo == 1) {
                nombreUnidad = nuevoConcepto.getUnidad().getNombre();
            } else if (tipoNuevo == 2) {
                nombreUnidad = duplicarConcepto.getUnidad().getNombre();
            }
        } else if (Campo.equals("TERCERO")) {
            if (tipoNuevo == 1) {
                tercero = nuevoConcepto.getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                tercero = duplicarConcepto.getTercero().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CODIGOUNIDAD")) {
            if (tipoNuevo == 1) {
                nuevoConcepto.getUnidad().setCodigo(codigoUnidad);
            } else if (tipoNuevo == 2) {
                duplicarConcepto.getUnidad().setCodigo(codigoUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoConcepto.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCodigoUnidad");
                    context.update("formularioDialogos:nuevoNombreUnidad");
                } else if (tipoNuevo == 2) {
                    duplicarConcepto.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoUnidad");
                    context.update("formularioDialogos:duplicarNombreUnidad");
                }
                listaUnidades.clear();
                getListaUnidades();
            } else {
                context.update("formularioDialogos:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCodigoUnidad");
                    context.update("formularioDialogos:nuevoNombreUnidad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoUnidad");
                    context.update("formularioDialogos:duplicarNombreUnidad");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("NOMBREUNIDAD")) {
            if (tipoNuevo == 1) {
                nuevoConcepto.getUnidad().setNombre(nombreUnidad);
            } else if (tipoNuevo == 2) {
                duplicarConcepto.getUnidad().setNombre(nombreUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoConcepto.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCodigoUnidad");
                    context.update("formularioDialogos:nuevoNombreUnidad");
                } else if (tipoNuevo == 2) {
                    duplicarConcepto.setUnidad(listaUnidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoUnidad");
                    context.update("formularioDialogos:duplicarNombreUnidad");
                }
                listaUnidades.clear();
                getListaUnidades();
            } else {
                context.update("formularioDialogos:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCodigoUnidad");
                    context.update("formularioDialogos:nuevoNombreUnidad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoUnidad");
                    context.update("formularioDialogos:duplicarNombreUnidad");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevoConcepto.getTercero().setNombre(tercero);
                } else if (tipoNuevo == 2) {
                    duplicarConcepto.getTercero().setNombre(tercero);
                }
                for (int i = 0; i < listaTerceros.size(); i++) {
                    if (listaTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevoConcepto.setTercero(listaTerceros.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevoTercero");
                    } else if (tipoNuevo == 2) {
                        duplicarConcepto.setTercero(listaTerceros.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarTercero");
                    }
                    listaTerceros.clear();
                    getListaTerceros();
                } else {
                    context.update("formularioDialogos:TercerosDialogo");
                    context.execute("TercerosDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevoTercero");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarTercero");
                    }
                }
            }
        } else {
            listaTerceros.clear();
            getListaTerceros();
            if (tipoNuevo == 1) {
                nuevoConcepto.setTercero(new Terceros());
            } else if (tipoNuevo == 2) {
                duplicarConcepto.setTercero(new Terceros());
            }
            if (tipoNuevo == 1) {
                context.update("formularioDialogos:nuevoTercero");
            } else if (tipoNuevo == 2) {
                context.update("formularioDialogos:duplicarTercero");
            }
        }
    }

    public void llamarLOVSNuevo_Duplicado(int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        activoDetalle = true;
        context.update("form:DETALLES");
        if (dlg == 0) {
            context.update("formularioDialogos:unidadesDialogo");
            context.execute("unidadesDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:TercerosDialogo");
            context.execute("TercerosDialogo.show()");
        }
    }

    public void llamarDialogosLista(Integer indice, int dlg) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        tipoActualizacion = 0;
        if (dlg == 0) {
            context.update("formularioDialogos:unidadesDialogo");
            context.execute("unidadesDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:TercerosDialogo");
            context.execute("TercerosDialogo.show()");
        }
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            //Si la columna es Unidades
            if (cualCelda == 3 || cualCelda == 4) {
                context.update("formularioDialogos:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = 0;
                //Si la columna es tercersos
            } else if (cualCelda == 10) {
                tipoActualizacion = 0;
                context.update("formularioDialogos:TercerosDialogo");
                context.execute("TercerosDialogo.show()");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                editarConcepto = listaConceptosEmpresa.get(index);
            }
            if (tipoLista == 1) {
                editarConcepto = filtradoConceptosEmpresa.get(index);
            }
            switch (cualCelda) {
                case 0: {
                    context.update("formularioDialogos:editorCodigo");
                    context.execute("editorCodigo.show()");
                }
                break;
                case 1: {
                    context.update("formularioDialogos:editorDescripcion");
                    context.execute("editorDescripcion.show()");
                }
                break;
                case 2: {
                    context.update("formularioDialogos:editorNaturaleza");
                    context.execute("editorNaturaleza.show()");
                }
                break;
                case 3: {
                    context.update("formularioDialogos:editorCodigoUnidad");
                    context.execute("editorCodigoUnidad.show()");
                }
                break;
                case 4: {
                    context.update("formularioDialogos:editorNombreUnidad");
                    context.execute("editorNombreUnidad.show()");
                }
                break;
                case 5: {
                    context.update("formularioDialogos:editorCodigoDesprendible");
                    context.execute("editorCodigoDesprendible.show()");
                }
                break;
                case 6: {
                    context.update("formularioDialogos:editorDescripcionDesprendible");
                    context.execute("editorDescripcionDesprendible.show()");
                }
                break;
                case 7: {
                    context.update("formularioDialogos:editorIndependienteTesoreria");
                    context.execute("editorIndependienteTesoreria.show()");
                }
                break;
                case 8: {
                    context.update("formularioDialogos:editorConjunto");
                    context.execute("editorConjunto.show()");
                }
                break;
                case 9: {
                    context.update("formularioDialogos:editorFechaAcumulacion");
                    context.execute("editorFechaAcumulacion.show()");
                }
                break;
                case 10: {
                    context.update("formularioDialogos:editorTercero");
                    context.execute("editorTercero.show()");
                }
                break;
                case 11: {
                    context.update("formularioDialogos:editorEstado");
                    context.execute("editorEstado.show()");
                }
                break;
                case 12: {
                    context.update("formularioDialogos:editorEnvioTesoreria");
                    context.execute("editorEnvioTesoreria.show()");
                }
                break;
                case 13: {
                    context.update("formularioDialogos:editorCodigoAleternativo");
                    context.execute("editorCodigoAleternativo.show()");
                }
                break;
            }
            cualCelda = -1;
            index = -1;
            secRegistro = null;
            activoDetalle = true;
            context.update("form:DETALLES");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void modificarConcepto(int indice, String confirmarCambio, String valor) {
        tipoActualizacion = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        //  Validación: que el concepto no sea usado para ningun registro de empleado
        boolean conceptoUtilizado;
        if (tipoLista == 0) {
            conceptoUtilizado = administrarConceptos.ValidarUpdateConceptoAcumulados(listaConceptosEmpresa.get(indice).getSecuencia());
        } else {
            conceptoUtilizado = administrarConceptos.ValidarUpdateConceptoAcumulados(filtradoConceptosEmpresa.get(indice).getSecuencia());
        }
        //Si se puede modificar
        if (conceptoUtilizado) {
            index = indice;
            int coincidencias = 0;
            int indiceUnicoElemento = 0;
            if (confirmarCambio.equalsIgnoreCase("COD")) {
                BigInteger cod = new BigInteger(valor);
                boolean error = validarCodigo(cod);
                if (error) {
                    if (tipoLista == 0) {
                        listaConceptosEmpresa.get(indice).setCodigo(cod);
                    } else {
                        filtradoConceptosEmpresa.get(indice).setCodigo(cod);
                    }
                    context.update("formularioDialogos:NuevoConceptoDialogo");
                    context.update("formularioDialogos:validacioNuevoCodigo");
                    context.execute("validacioNuevoCodigo.show()");
                    refrescar();
                }

            } else if (confirmarCambio.equalsIgnoreCase("N")) {
                if (tipoLista == 0) {
                    if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {

                        if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))
                                || listaConceptosEmpresaModificar.isEmpty()) {
                            listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                        }
                        if (guardado) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                    }
                    index = -1;
                    secRegistro = null;
                    activoDetalle = true;
                    context.update("form:DETALLES");
                } else {
                    if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(indice))) {

                        if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(indice))
                                || listaConceptosEmpresaModificar.isEmpty()) {
                            listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                        }
                        if (guardado) {
                            guardado = false;
                            RequestContext.getCurrentInstance().update("form:ACEPTAR");
                        }
                    }
                    index = -1;
                    secRegistro = null;
                    activoDetalle = true;
                    context.update("form:DETALLES");
                }
                context.update("form:datosConceptos");
            } else if (confirmarCambio.equalsIgnoreCase("UNIDADESCODIGO")) {
                if (tipoLista == 0) {
                    listaConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
                } else {
                    filtradoConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
                }
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getCodigo().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listaConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                    } else {
                        filtradoConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                    }
                    listaUnidades.clear();
                    getListaUnidades();
                } else {
                    permitirIndex = false;
                    context.update("formularioDialogos:unidadesDialogo");
                    context.execute("unidadesDialogo.show()");
                }
            } else if (confirmarCambio.equalsIgnoreCase("UNIDADESNOMBRE")) {
                if (tipoLista == 0) {
                    listaConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
                } else {
                    filtradoConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
                }
                for (int i = 0; i < listaUnidades.size(); i++) {
                    if (listaUnidades.get(i).getNombre().startsWith(valor.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listaConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                    } else {
                        filtradoConceptosEmpresa.get(indice).setUnidad(listaUnidades.get(indiceUnicoElemento));
                    }
                } else {
                    permitirIndex = false;
                    context.update("formularioDialogos:unidadesDialogo");
                    context.execute("unidadesDialogo.show()");
                }
            } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
                if (!valor.isEmpty()) {
                    if (tipoLista == 0) {
                        listaConceptosEmpresa.get(indice).getTercero().setNombre(tercero);
                    } else {
                        filtradoConceptosEmpresa.get(indice).getTercero().setNombre(tercero);
                    }
                    for (int i = 0; i < listaTerceros.size(); i++) {
                        if (listaTerceros.get(i).getNombre().startsWith(valor.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }
                    if (coincidencias == 1) {
                        if (tipoLista == 0) {
                            listaConceptosEmpresa.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                        } else {
                            filtradoConceptosEmpresa.get(indice).setTercero(listaTerceros.get(indiceUnicoElemento));
                        }
                        listaTerceros.clear();
                        getListaTerceros();
                    } else {
                        permitirIndex = false;
                        context.update("formularioDialogos:TercerosDialogo");
                        context.execute("TercerosDialogo.show()");
                    }
                } else {
                    listaTerceros.clear();
                    getListaTerceros();
                    if (tipoLista == 0) {
                        listaConceptosEmpresa.get(indice).setTercero(new Terceros());
                    } else {
                        filtradoConceptosEmpresa.get(indice).setTercero(new Terceros());
                    }
                    coincidencias = 1;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {

                        if (listaConceptosEmpresaModificar.isEmpty()) {
                            listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                        } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                            listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                        }
                    }
                } else {
                    if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(indice))) {

                        if (listaConceptosEmpresaModificar.isEmpty()) {
                            listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                        } else if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(indice))) {
                            listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                        }
                    }
                }
                if (guardado) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
                activoDetalle = true;
                context.update("form:DETALLES");
            }

        } else {
            if (tipoLista == 0) {
                refrescar();
                context.execute("errorNoModificar.show()");
            }
        }
        tipoActualizacion = -1;
        context.update("form:datosConceptos");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaConceptosEmpresa.get(index).getSecuencia();
                conceptoRegistro = listaConceptosEmpresa.get(index);
                codigoUnidad = listaConceptosEmpresa.get(index).getUnidad().getCodigo();
                nombreUnidad = listaConceptosEmpresa.get(index).getUnidad().getNombre();
                tercero = listaConceptosEmpresa.get(index).getTercero().getNombre();
            } else {
                secRegistro = filtradoConceptosEmpresa.get(index).getSecuencia();
                conceptoRegistro = filtradoConceptosEmpresa.get(index);
                codigoUnidad = filtradoConceptosEmpresa.get(index).getUnidad().getCodigo();
                nombreUnidad = filtradoConceptosEmpresa.get(index).getUnidad().getNombre();
                tercero = filtradoConceptosEmpresa.get(index).getTercero().getNombre();
            }
            RequestContext context = RequestContext.getCurrentInstance();
            activoDetalle = false;
            context.update("form:DETALLES");
        }
    }

    public void actualizarUnidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(index).setUnidad(unidadSeleccionada);
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    }
                }
            } else {
                filtradoConceptosEmpresa.get(index).setUnidad(unidadSeleccionada);
                if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(index));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:DETALLES");
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoConcepto.setUnidad(unidadSeleccionada);
            context.update("formularioDialogos:nuevoCodigoUnidad");
            context.update("formularioDialogos:nuevoNombreUnidad");
        } else if (tipoActualizacion == 2) {
            duplicarConcepto.setUnidad(unidadSeleccionada);
            context.update("formularioDialogos:duplicarCodigoUnidad");
            context.update("formularioDialogos:duplicarNombreUnidad");
        }
        filtradoUnidades = null;
        unidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        activoDetalle = true;

        context.update("form:DETALLES");
        context.reset("formularioDialogos:lovUnidades:globalFilter");
        context.execute("lovUnidades.clearFilters()");
        context.execute("unidadesDialogo.hide()");
        context.update("form:datosConceptos");
    }

    public void cancelarUnidades() {
        filtradoUnidades = null;
        unidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        activoDetalle = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.reset("formularioDialogos:lovUnidades:globalFilter");
        context.execute("lovUnidades.clearFilters()");
        context.execute("unidadesDialogo.hide()");
    }

    public void actualizarTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(index).setTercero(terceroSeleccionado);
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(index));
                    }
                }
            } else {
                filtradoConceptosEmpresa.get(index).setTercero(terceroSeleccionado);
                if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(index))) {
                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(index));
                    } else if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(index))) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(index));
                    }
                }
            }
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevoConcepto.setTercero(terceroSeleccionado);
            context.update("formularioDialogos:nuevoTercero");
        } else if (tipoActualizacion == 2) {
            duplicarConcepto.setTercero(terceroSeleccionado);
            context.update("formularioDialogos:duplicarTercero");
        }
        filtradoTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        activoDetalle = true;

        context.update("form:DETALLES");
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.execute("lovTerceros.clearFilters()");
        context.execute("TercerosDialogo.hide()");
        context.update("form:datosConceptos");
    }

    public void cancelarTercero() {
        filtradoTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        activoDetalle = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.execute("lovTerceros.clearFilters()");
        context.execute("TercerosDialogo.hide()");
    }

    public void lovEmpresas() {
        index = -1;
        secRegistro = null;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado) {
            listaConceptosEmpresa = null;
            getListaConceptosEmpresa();
            context.update("form:datosConceptos");
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            filtradoListaEmpresas = null;
            aceptar = true;

            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.execute("lovEmpresas.clearFilters()");
            context.execute("EmpresasDialogo.hide()");
            backUpEmpresaActual = empresaActual;
            verCambioEmpresa = false;
        } else {
            verCambioEmpresa = true;
            context.execute("confirmarGuardarSinSalida.show()");
        }
    }

    public void cancelarCambioEmpresa() {
        filtradoListaEmpresas = null;
        verCambioEmpresa = true;
        empresaActual = backUpEmpresaActual;
        index = -1;
        activoDetalle = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.reset("formularioDialogos:lovEmpresas:globalFilter");
        context.execute("lovEmpresas.clearFilters()");
        context.execute("EmpresasDialogo.hide()");
    }

    public void lovConcepto(int quien) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (quien == 0) {
            if (guardado) {
                listaConceptosEmpresa = null;
                getListaConceptosEmpresa();
                context.update("formularioDialogos:ConceptosDialogo");
                context.execute("ConceptosDialogo.show()");
                verSeleccionConcepto = false;
                cambioConcepto = 0;
            } else {
                verSeleccionConcepto = true;
                context.execute("confirmarGuardar.show()");
            }
        } else {
            listaConceptosEmpresa_Estado = administrarConceptos.consultarConceptosEmpresaSinPasivos(empresaActual.getSecuencia());
            context.update("formularioDialogos:ConceptosDialogo");
            context.execute("ConceptosDialogo.show()");
            cambioConcepto = 1;
        }
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    public void mostrarTodosConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "205";
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
                columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptos");
                bandera = 0;
                filtradoConceptosEmpresa = null;
                tipoLista = 0;
            }
            listaConceptosEmpresa = null;
            mostrarTodos = true;
            verMostrarTodos = false;
            getListaConceptosEmpresa();
            context.update("form:datosConceptos");
            context.update("form:mostrarTodos");
        } else {
            verMostrarTodos = true;
            context.execute("confirmarGuardar.show()");
        }
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    public void seleccionConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambioConcepto == 0) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTabla = "205";
                columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
                columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConceptos");
                bandera = 0;
                filtradoConceptosEmpresa = null;
                tipoLista = 0;
            }
            listaConceptosEmpresa.clear();
            listaConceptosEmpresa.add(conceptoSeleccionado);
            mostrarTodos = false;
            context.update("form:datosConceptos");
            context.update("form:mostrarTodos");
        } else {
            conceptoOriginal = conceptoSeleccionado;
            context.update("form:descripcionClon");
        }
        filtradoConceptosEmpresa_Estado = null;
        conceptoSeleccionado = null;
        aceptar = true;
        /*
         * context.update("formularioDialogos:ConceptosDialogo");
         * context.update("formularioDialogos:lovConceptos");
         * context.update("formularioDialogos:aceptarC");
         */
        context.reset("formularioDialogos:lovConceptos:globalFilter");
        context.execute("lovConceptos.clearFilters()");
        context.execute("ConceptosDialogo.hide()");
    }

    public void cancelarSeleccionConcepto() {
        filtradoConceptosEmpresa_Estado = null;
        conceptoSeleccionado = null;
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("formularioDialogos:lovConceptos:globalFilter");
        context.execute("lovConceptos.clearFilters()");
        context.execute("ConceptosDialogo.hide()");
    }

    public void borrarConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaConceptosEmpresaModificar.isEmpty() && listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(index))) {
                    int modIndex = listaConceptosEmpresaModificar.indexOf(listaConceptosEmpresa.get(index));
                    listaConceptosEmpresaModificar.remove(modIndex);
                    listaConceptosBorrar.add(listaConceptosEmpresa.get(index));
                } else if (!listaConceptosEmpresaCrear.isEmpty() && listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(index))) {
                    int crearIndex = listaConceptosEmpresaCrear.indexOf(listaConceptosEmpresa.get(index));
                    listaConceptosEmpresaCrear.remove(crearIndex);
                } else {
                    listaConceptosBorrar.add(listaConceptosEmpresa.get(index));
                }
                listaConceptosEmpresa.remove(index);
            }
            if (tipoLista == 1) {
                if (!listaConceptosEmpresaModificar.isEmpty() && listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(index))) {
                    int modIndex = listaConceptosEmpresaModificar.indexOf(filtradoConceptosEmpresa.get(index));
                    listaConceptosEmpresaModificar.remove(modIndex);
                    listaConceptosBorrar.add(filtradoConceptosEmpresa.get(index));
                } else if (!listaConceptosEmpresaCrear.isEmpty() && listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(index))) {
                    int crearIndex = listaConceptosEmpresaCrear.indexOf(filtradoConceptosEmpresa.get(index));
                    listaConceptosEmpresaCrear.remove(crearIndex);
                } else {
                    listaConceptosBorrar.add(filtradoConceptosEmpresa.get(index));
                }
                filtradoConceptosEmpresa.remove(index);
            }

            infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
            context.update("form:informacionRegistro");
            context.update("form:datosConceptos");
            index = -1;
            secRegistro = null;
            activoDetalle = true;
            RequestContext.getCurrentInstance().update("form:DETALLES");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void guardarSalir() {
        guardarCambios();
        refrescar();
    }

    public void cancelarSalir() {
        refrescar();
    }

    //GUARDAR
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listaConceptosBorrar.isEmpty()) {
                    administrarConceptos.borrarConceptos(listaConceptosBorrar);
                    listaConceptosBorrar.clear();
                }
                if (!listaConceptosEmpresaCrear.isEmpty()) {
                    administrarConceptos.crearConceptos(listaConceptosEmpresaCrear);
                    listaConceptosEmpresaCrear.clear();
                }
                if (!listaConceptosEmpresaModificar.isEmpty()) {
                    administrarConceptos.modificarConceptos(listaConceptosEmpresaModificar);
                    listaConceptosEmpresaModificar.clear();
                }
                listaConceptosEmpresa = null;
                getListaConceptosEmpresa();
                if (listaConceptosEmpresa != null) {
                    infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                context.update("form:informacionRegistro");
                context.update("form:datosConceptos");
                guardado = true;
                permitirIndex = true;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                paraNuevoConcepto = 0;
                index = -1;
                secRegistro = null;
                activoDetalle = true;
                RequestContext.getCurrentInstance().update("form:DETALLES");
                if (verCambioEmpresa) {
                    cambiarEmpresa();
                }
                if (verCambioEstado) {
                    cambiarEstado();
                }
                if (verSeleccionConcepto) {
                    lovConcepto(0);
                }
                if (verMostrarTodos) {
                    mostrarTodosConceptos();
                }
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambios : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Se presento un error en el guardado, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void cancelarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (verCambioEmpresa) {
            empresaActual = backUpEmpresaActual;
            context.update("formularioDialogos:lovEmpresas");
            verCambioEmpresa = false;
        }
        if (verCambioEstado) {
            estadoConceptoEmpresa = backUpEstadoConceptoEmpresa;
            context.update("form:opciones");
            verCambioEstado = false;
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "173";
            columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
            columnaIndependienteConcepto.setFilterStyle("width: 5px;");
            columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("width: 20px;");
            columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("width: 190px;");
            columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("width: 70px;");
            columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("width: 5px;");
            columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("width: 40px;");
            columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("width: 20px;");
            columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("width:110px;");
            columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("width: 20px;");
            columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("width: 75px;");
            columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("width: 140px;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("width: 55px;");
            columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("width: 20px;");
            columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("width: 130px;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 1;

        } else if (bandera == 1) {
            altoTabla = "205";
            columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
            columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
            columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 0;
            filtradoConceptosEmpresa = null;
            tipoLista = 0;
        }
    }

    public boolean validarCodigo(BigInteger cod) {
        int error = 0;
        for (int i = 0; i < listaConceptosEmpresa_Estado.size(); i++) {
            if (listaConceptosEmpresa_Estado.get(i).getCodigo().equals(cod)) {
                error++;
            }
        }
        if (tipoActualizacion > 0) {
            return (error > 0);
        } else {
            return (error > 1);
        }
    }

    public void agregarNuevoConcepto() {

        RequestContext context = RequestContext.getCurrentInstance();
        int pasa = 0;
        mensajeValidacion = "";
        if (nuevoConcepto.getCodigo() == null) {
            mensajeValidacion = " * Código concepto\n";
            pasa++;
        }
        if (nuevoConcepto.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción\n";
            pasa++;
        }
        if (nuevoConcepto.getNaturalezaConcepto() == null) {
            mensajeValidacion = mensajeValidacion + " *Naturaleza\n";
            pasa++;
        }
        if (nuevoConcepto.getUnidad().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " *Unidad\n";
            pasa++;
        }
        if (pasa == 0) {
            tipoActualizacion = 1;
            //Se valida que el codigo de concepto no exista
            boolean error = validarCodigo(nuevoConcepto.getCodigo());
            if (error == false) {
                if (continuarNuevoNat == false) {
                    if (nuevoConcepto.getNaturalezaConcepto().equals("NETO") && nuevoConcepto.getConjunto() != 45) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es NETO, Recuerde que usualmente para ella el conjunto de conceptos debe ser 45.";
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("GASTO") && (nuevoConcepto.getConjunto() > 37 || nuevoConcepto.getConjunto() < 31)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es GASTO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 31 y 37";
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("DESCUENTO") && (nuevoConcepto.getConjunto() > 30 || nuevoConcepto.getConjunto() < 21)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es DESCUENTO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 21 y 30";
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("PAGO") && nuevoConcepto.getConjunto() > 20) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es PAGO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 1 y 20";
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("PASIVO") && (nuevoConcepto.getConjunto() > 44 || nuevoConcepto.getConjunto() < 38)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es PASIVO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 38 y 44";
                    }
                }

                if (pasa == 0) {
                    if (bandera == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();
                        altoTabla = "205";
                        columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
                        columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                        columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                        columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                        columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                        columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                        columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                        columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                        columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                        columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                        columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                        columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                        columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                        columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                        columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                        columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                        columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                        columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                        columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                        columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                        columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                        columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                        columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                        columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                        context.update("form:datosConceptos");
                        bandera = 0;
                        filtradoConceptosEmpresa = null;
                        tipoLista = 0;
                    }
                    paraNuevoConcepto++;
                    nuevoConceptoSecuencia = BigInteger.valueOf(paraNuevoConcepto);
                    nuevoConcepto.setSecuencia(nuevoConceptoSecuencia);
                    nuevoConcepto.setEmpresa(empresaActual);
                    if (nuevoConcepto.getTercero().getSecuencia() == null) {
                        nuevoConcepto.setTercero(null);
                    }

                    if (nuevoConcepto.getNaturalezaConcepto().equals("NETO")) {
                        nuevoConcepto.setNaturaleza("N");
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("GASTO")) {
                        nuevoConcepto.setNaturaleza("G");
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("DESCUENTO")) {
                        nuevoConcepto.setNaturaleza("D");
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("PAGO")) {
                        nuevoConcepto.setNaturaleza("P");
                    } else if (nuevoConcepto.getNaturalezaConcepto().equals("PASIVO")) {
                        nuevoConcepto.setNaturaleza("L");
                    }

                    if (nuevoConcepto.getEstadoConcepto().equals("ACTIVO")) {
                        nuevoConcepto.setActivo("S");
                    } else if (nuevoConcepto.getEstadoConcepto().equals("INACTIVO")) {
                        nuevoConcepto.setActivo("N");
                    }

                    if (nuevoConcepto.getEnviarConcepto().equals("SI")) {
                        nuevoConcepto.setEnviotesoreria("S");
                    } else if (nuevoConcepto.getEnviarConcepto().equals("NO")) {
                        nuevoConcepto.setEnviotesoreria("N");
                    }

                    if (nuevoConcepto.isIndependienteConcepto()) {
                        nuevoConcepto.setIndependiente("S");
                    } else {
                        nuevoConcepto.setIndependiente("N");
                    }

                    listaConceptosEmpresaCrear.add(nuevoConcepto);

                    listaConceptosEmpresa.add(nuevoConcepto);
                    infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
                    context.update("form:informacionRegistro");
                    nuevoConcepto = new Conceptos();
                    nuevoConcepto.setUnidad(new Unidades());
                    nuevoConcepto.setTercero(new Terceros());
                    nuevoConcepto.setConjunto(Short.parseShort("45"));

                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    context.execute("NuevoConceptoDialogo.hide()");
                    context.update("formularioDialogos:NuevoConceptoDialogo");
                    index = -1;
                    secRegistro = null;
                    activoDetalle = true;
                    continuarNuevoNat = false;
                    tipoActualizacion = -1;
                    context.update("form:DETALLES");
                    context.update("form:datosConceptos");
                } else {
                    context.update("formularioDialogos:validacioNaturaleza");
                    context.execute("validacioNaturaleza.show()");
                }
            } else {
                context.update("formularioDialogos:validacioNuevoCodigo");
                context.execute("validacioNuevoCodigo.show()");
            }
        } else {
            context.update("formularioDialogos:validacioNuevoConcepto");
            context.execute("validacioNuevoConcepto.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    public void limpiarNuevoConcepto() {
        nuevoConcepto = new Conceptos();
        nuevoConcepto.setUnidad(new Unidades());
        nuevoConcepto.setTercero(new Terceros());
        index = -1;
        secRegistro = null;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    public void duplicarRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            duplicarConcepto = new Conceptos();
            // duplicarConcepto.setConjunto(Short.parseShort("45"));
            if (tipoLista == 0) {
                duplicarConcepto.setEmpresa(listaConceptosEmpresa.get(index).getEmpresa());
                duplicarConcepto.setCodigo(listaConceptosEmpresa.get(index).getCodigo());
                duplicarConcepto.setDescripcion(listaConceptosEmpresa.get(index).getDescripcion());
                duplicarConcepto.setNaturaleza(listaConceptosEmpresa.get(index).getNaturaleza());
                duplicarConcepto.setUnidad(listaConceptosEmpresa.get(index).getUnidad());
                duplicarConcepto.setCodigodesprendible(listaConceptosEmpresa.get(index).getCodigodesprendible());
                duplicarConcepto.setIndependiente(listaConceptosEmpresa.get(index).getIndependiente());
                duplicarConcepto.setConjunto(listaConceptosEmpresa.get(index).getConjunto());
                duplicarConcepto.setContenidofechahasta(listaConceptosEmpresa.get(index).getContenidofechahasta());
                duplicarConcepto.setTercero(listaConceptosEmpresa.get(index).getTercero());
                duplicarConcepto.setActivo(listaConceptosEmpresa.get(index).getActivo());
                duplicarConcepto.setEnviotesoreria(listaConceptosEmpresa.get(index).getEnviotesoreria());
                duplicarConcepto.setCodigoalternativo(listaConceptosEmpresa.get(index).getCodigoalternativo());
            }
            if (tipoLista == 1) {
                duplicarConcepto.setEmpresa(filtradoConceptosEmpresa.get(index).getEmpresa());
                duplicarConcepto.setCodigo(filtradoConceptosEmpresa.get(index).getCodigo());
                duplicarConcepto.setDescripcion(filtradoConceptosEmpresa.get(index).getDescripcion());
                duplicarConcepto.setNaturaleza(filtradoConceptosEmpresa.get(index).getNaturaleza());
                duplicarConcepto.setUnidad(filtradoConceptosEmpresa.get(index).getUnidad());
                duplicarConcepto.setCodigodesprendible(filtradoConceptosEmpresa.get(index).getCodigodesprendible());
                duplicarConcepto.setIndependiente(filtradoConceptosEmpresa.get(index).getIndependiente());
                duplicarConcepto.setConjunto(filtradoConceptosEmpresa.get(index).getConjunto());
                duplicarConcepto.setContenidofechahasta(filtradoConceptosEmpresa.get(index).getContenidofechahasta());
                duplicarConcepto.setTercero(filtradoConceptosEmpresa.get(index).getTercero());
                duplicarConcepto.setActivo(filtradoConceptosEmpresa.get(index).getActivo());
                duplicarConcepto.setEnviotesoreria(filtradoConceptosEmpresa.get(index).getEnviotesoreria());
                duplicarConcepto.setCodigoalternativo(filtradoConceptosEmpresa.get(index).getCodigoalternativo());
            }
            context.update("formularioDialogos:duplicarConcepto");
            context.execute("DuplicarConceptoDialogo.show()");
            index = -1;
            secRegistro = null;
            activoDetalle = true;
            context.update("form:DETALLES");
        } else {
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (duplicarConcepto.getCodigo() == null) {
            mensajeValidacion = " * Código concepto\n";
            pasa++;
        }
        if (duplicarConcepto.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción\n";
            pasa++;
        }
        if (duplicarConcepto.getUnidad().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " *Unidad\n";
            pasa++;
        }
        if (duplicarConcepto.getNaturalezaConcepto() == null) {
            mensajeValidacion = mensajeValidacion + " *Naturaleza\n";
            pasa++;
        }
        if (pasa == 0) {
            tipoActualizacion = 2;
            //Se valida que el codigo de concepto no exista
            boolean error = validarCodigo(duplicarConcepto.getCodigo());
            if (error == false) {
                if (continuarNuevoNat == false) {
                    if (duplicarConcepto.getNaturalezaConcepto().equals("NETO") && duplicarConcepto.getConjunto() != 45) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es NETO, Recuerde que usualmente para ella el conjunto de conceptos debe ser 45.";
                    } else if (duplicarConcepto.getNaturalezaConcepto().equals("GASTO") && (duplicarConcepto.getConjunto() > 37 || duplicarConcepto.getConjunto() < 31)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es GASTO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 31 y 37";
                    } else if (duplicarConcepto.getNaturalezaConcepto().equals("DESCUENTO") && (duplicarConcepto.getConjunto() > 30 || duplicarConcepto.getConjunto() < 21)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es DESCUENTO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 21 y 30";
                    } else if (duplicarConcepto.getNaturalezaConcepto().equals("PAGO") && duplicarConcepto.getConjunto() > 20) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es PAGO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 1 y 20";
                    } else if (duplicarConcepto.getNaturalezaConcepto().equals("PASIVO") && (duplicarConcepto.getConjunto() > 44 || duplicarConcepto.getConjunto() < 38)) {
                        pasa++;
                        mensajeValidacion = "La naturaleza del concepto es PASIVO, Recuerde que usualmente para ella el conjunto de conceptos debe estar entre 38 y 44";
                    }
                }
                if (pasa == 0) {
                    paraNuevoConcepto++;
                    nuevoConceptoSecuencia = BigInteger.valueOf(paraNuevoConcepto);
                    duplicarConcepto.setSecuencia(nuevoConceptoSecuencia);
                    listaConceptosEmpresa.add(duplicarConcepto);
                    listaConceptosEmpresaCrear.add(duplicarConcepto);
                    infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
                    index = -1;
                    secRegistro = null;
                    activoDetalle = true;
                    if (guardado) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    if (bandera == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();
                        altoTabla = "205";
                        columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
                        columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                        columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                        columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                        columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                        columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                        columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                        columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                        columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                        columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                        columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                        columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                        columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                        columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                        columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                        columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                        columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                        columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                        columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                        columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                        columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                        columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                        columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                        columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                        columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                        context.update("form:datosConceptos");
                        context.update("form:informacionRegistro");
                        context.update("form:datosConceptos");
                        context.update("form:DETALLES");
                        context.execute("DuplicarConceptoDialogo.hide()");
                        bandera = 0;
                        filtradoConceptosEmpresa = null;
                        tipoLista = 0;
                    }
                    tipoActualizacion = -1;
                    continuarNuevoNat = false;
                    duplicarConcepto = new Conceptos();
                    context.update("form:datosConceptos");
                } else {
                    context.update("formularioDialogos:validacioNaturaleza");
                    context.execute("validacioNaturaleza.show()");
                }
            } else {
                context.update("formularioDialogos:validacioNuevoCodigo");
                context.execute("validacioNuevoCodigo.show()");
            }
        } else {
            context.update("formularioDialogos:validacioNuevoConcepto");
            context.execute("validacioNuevoConcepto.show()");
        }
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicar() {
        duplicarConcepto = new Conceptos();
    }
    //Para continuar con tras validar naturaleza

    public void activarContinuar() {
        continuarNuevoNat = true;
        if (tipoActualizacion == 1) {
            agregarNuevoConcepto();
        } else if (tipoActualizacion == 2) {
            confirmarDuplicar();
        }
    }

    public void refrescar() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "205";
            columnaIndependienteConcepto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaIndependienteConcepto");
            columnaIndependienteConcepto.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaNaturaleza = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreUnidad = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoDesprendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripcionDesplendible = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
            columnaConjunto = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaAcumulado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreTercero = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            columnaEnvio = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoAlternativo = (Column) c.getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 0;
            filtradoConceptosEmpresa = null;
            tipoLista = 0;
        }
        listaConceptosBorrar.clear();
        listaConceptosEmpresaCrear.clear();
        listaConceptosEmpresaModificar.clear();
        index = -1;
        secRegistro = null;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
        paraNuevoConcepto = 0;
        listaConceptosEmpresa = null;
        getListaConceptosEmpresa();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        permitirIndex = true;
        mostrarTodos = true;
        conceptoClon = new Conceptos();
        conceptoOriginal = new Conceptos();
        if (verCambioEmpresa) {
            cambiarEmpresa();
        }
        if (verCambioEstado) {
            cambiarEstado();
        }

        if (verSeleccionConcepto) {
            lovConcepto(0);
        }
        if (verMostrarTodos) {
            mostrarTodosConceptos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        getListaConceptosEmpresa();
        if (listaConceptosEmpresa != null) {
            infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        context.update("form:informacionRegistro");
        context.update("form:mostrarTodos");
        context.update("form:datosConceptos");
        context.update("form:codigoConceptoClon");
        context.update("form:descripcioConceptoClon");
        context.update("form:descripcionClon");
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            int resultado = administrarRastros.obtenerTabla(secRegistro, "CONCEPTOS");
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
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "ConceptosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ConceptosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
        activoDetalle = true;
        RequestContext.getCurrentInstance().update("form:DETALLES");
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de registros : " + filtradoConceptosEmpresa.size();
        context.update("form:informacionRegistro");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //CLONAR
    public void clonarConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (conceptoClon.getCodigo() != null && conceptoClon.getDescripcion() != null && conceptoOriginal.getSecuencia() != null) {
            tipoActualizacion = 3;
            //Se valida que el codigo de concepto no exista
            boolean error = validarCodigo(conceptoClon.getCodigo());
            if (error == false) {
                administrarConceptos.clonarConcepto(conceptoOriginal.getSecuencia(), conceptoClon.getCodigo(), conceptoClon.getDescripcion());
                conceptoClon = new Conceptos();
                conceptoOriginal = new Conceptos();
                listaConceptosEmpresa = null;
                getListaConceptosEmpresa();
                if (listaConceptosEmpresa != null) {
                    infoRegistro = "Cantidad de registros : " + listaConceptosEmpresa.size();
                } else {
                    infoRegistro = "Cantidad de registros : 0";
                }
                guardado = false;
                context.update("form:ACEPTAR");
                FacesMessage msg = new FacesMessage("Información", "Concepto clonado con exito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
                context.update("form:codigoConceptoClon");
                context.update("form:descripcioConceptoClon");
                context.update("form:descripcionClon");
                context.update("form:datosConceptos");
            } else {
                context.update("formularioDialogos:validacioNuevoCodigo");
                context.execute("validacioNuevoCodigo.show()");
            }
        } else {
            context.update("formularioDialogos:validacioNuevoConcepto");
            context.execute("validacioNuevoClon.show()");
        }
        tipoActualizacion = -1;
    }
    //GETTER AND SETTER

    public List<Conceptos> getListaConceptosEmpresa() {

        if (listaConceptosEmpresa == null) {
            if (!estadoConceptoEmpresa.equals("TODOS")) {
                listaConceptosEmpresa = administrarConceptos.consultarConceptosEmpresaActivos_Inactivos(empresaActual.getSecuencia(), estadoConceptoEmpresa);
            } else {
                listaConceptosEmpresa = administrarConceptos.consultarConceptosEmpresa(empresaActual.getSecuencia());
            }
            listaConceptosEmpresa_Estado = listaConceptosEmpresa;
        }
        return listaConceptosEmpresa;
    }

    public void setListaConceptosEmpresa(List<Conceptos> listaConceptosEmpresa) {
        this.listaConceptosEmpresa = listaConceptosEmpresa;
    }

    public List<Conceptos> getFiltradoConceptosEmpresa() {
        return filtradoConceptosEmpresa;
    }

    public void setFiltradoConceptosEmpresa(List<Conceptos> filtradoConceptosEmpresa) {
        this.filtradoConceptosEmpresa = filtradoConceptosEmpresa;
    }

    public List<Empresas> getListaEmpresas() {
        if (listaEmpresas == null) {
            listaEmpresas = administrarConceptos.consultarEmpresas();
            if (listaEmpresas.isEmpty()) {
                infoRegistroEmpresa = "Cantidad de registros: 0 ";
            } else {
                infoRegistroEmpresa = "Cantidad de registros: " + listaEmpresas.size();
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:infoRegistroEmpresa");
        } else {
            empresaActual = listaEmpresas.get(0);
            backUpEmpresaActual = empresaActual;
        }
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public Empresas getEmpresaActual() {
        getListaEmpresas();
        return empresaActual;
    }

    public void setEmpresaActual(Empresas empresaActual) {
        this.empresaActual = empresaActual;
    }

    public Map<String, String> getConjuntoC() {
        return conjuntoC;
    }

    public void setConjuntoC(Map<String, String> conjuntoC) {
        this.conjuntoC = conjuntoC;
    }

    public List<Unidades> getListaUnidades() {
        if (listaUnidades == null) {
            listaUnidades = administrarConceptos.consultarLOVUnidades();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaUnidades == null || listaUnidades.isEmpty()) {
                infoRegistroUnidad = "Cantidad de registros: 0 ";
            } else {
                infoRegistroUnidad = "Cantidad de registros: " + listaUnidades.size();
            }
            context.update("form:infoRegistroTipoEntidad");
        }
        return listaUnidades;
    }

    public void setListaUnidades(List<Unidades> listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Unidades getUnidadSeleccionada() {
        return unidadSeleccionada;
    }

    public void setUnidadSeleccionada(Unidades unidadSeleccionada) {
        this.unidadSeleccionada = unidadSeleccionada;
    }

    public List<Unidades> getFiltradoUnidades() {
        return filtradoUnidades;
    }

    public void setFiltradoUnidades(List<Unidades> filtradoUnidades) {
        this.filtradoUnidades = filtradoUnidades;
    }

    public List<Terceros> getListaTerceros() {
        if (listaTerceros == null) {
            listaTerceros = administrarConceptos.consultarLOVTerceros(empresaActual.getSecuencia());
            RequestContext context = RequestContext.getCurrentInstance();
            if (listaTerceros.isEmpty()) {
                infoRegistroTercero = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTercero = "Cantidad de registros: " + listaTerceros.size();
            }
            context.update("form:infoRegistroTipoEntidad");
        }
        return listaTerceros;
    }

    public void setListaTerceros(List<Terceros> listaTerceros) {
        this.listaTerceros = listaTerceros;
    }

    public List<Terceros> getFiltradoTerceros() {
        return filtradoTerceros;
    }

    public void setFiltradoTerceros(List<Terceros> filtradoTerceros) {
        this.filtradoTerceros = filtradoTerceros;
    }

    public Terceros getTerceroSeleccionado() {
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Conceptos getEditarConcepto() {
        return editarConcepto;
    }

    public void setEditarConcepto(Conceptos editarConcepto) {
        this.editarConcepto = editarConcepto;
    }

    public Conceptos getDuplicarConcepto() {
        return duplicarConcepto;
    }

    public void setDuplicarConcepto(Conceptos duplicarConcepto) {
        this.duplicarConcepto = duplicarConcepto;
    }

    public Conceptos getNuevoConcepto() {
        return nuevoConcepto;
    }

    public void setNuevoConcepto(Conceptos nuevoConcepto) {
        this.nuevoConcepto = nuevoConcepto;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public List<Empresas> getFiltradoListaEmpresas() {
        return filtradoListaEmpresas;
    }

    public void setFiltradoListaEmpresas(List<Empresas> filtradoListaEmpresas) {
        this.filtradoListaEmpresas = filtradoListaEmpresas;
    }

    public String getEstadoConceptoEmpresa() {
        return estadoConceptoEmpresa;
    }

    public void setEstadoConceptoEmpresa(String estadoConceptoEmpresa) {
        this.estadoConceptoEmpresa = estadoConceptoEmpresa;
    }

    public List<Conceptos> getListaConceptosEmpresa_Estado() {
        return listaConceptosEmpresa_Estado;
    }

    public void setListaConceptosEmpresa_Estado(List<Conceptos> listaConceptosEmpresa_Estado) {
        this.listaConceptosEmpresa_Estado = listaConceptosEmpresa_Estado;
    }

    public List<Conceptos> getFiltradoConceptosEmpresa_Estado() {
        return filtradoConceptosEmpresa_Estado;
    }

    public void setFiltradoConceptosEmpresa_Estado(List<Conceptos> filtradoConceptosEmpresa_Estado) {
        this.filtradoConceptosEmpresa_Estado = filtradoConceptosEmpresa_Estado;
    }

    public Conceptos getConceptoSeleccionado() {
        return conceptoSeleccionado;
    }

    public void setConceptoSeleccionado(Conceptos conceptoSeleccionado) {
        this.conceptoSeleccionado = conceptoSeleccionado;
    }

    public boolean isMostrarTodos() {
        return mostrarTodos;
    }

    public Conceptos getConceptoOriginal() {
        return conceptoOriginal;
    }

    public void setConceptoOriginal(Conceptos conceptoOriginal) {
        this.conceptoOriginal = conceptoOriginal;
    }

    public Conceptos getConceptoClon() {
        return conceptoClon;
    }

    public void setConceptoClon(Conceptos conceptoClon) {
        this.conceptoClon = conceptoClon;
    }

    public Conceptos getConceptoRegistro() {
        return conceptoRegistro;
    }

    public void setConceptoRegistro(Conceptos conceptoRegistro) {
        this.conceptoRegistro = conceptoRegistro;
    }

    public Conceptos getSeleccionConceptoEmpresa() {
        getListaConceptosEmpresa();
        if (listaConceptosEmpresa != null) {
            int tam = listaConceptosEmpresa.size();
            if (tam > 0) {
                seleccionConceptoEmpresa = listaConceptosEmpresa.get(0);
            }
        }
        return seleccionConceptoEmpresa;
    }

    public void setSeleccionConceptoEmpresa(Conceptos seleccionConceptoEmpresa) {
        this.seleccionConceptoEmpresa = seleccionConceptoEmpresa;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroUnidad() {
        getListaUnidades();
        if (listaUnidades != null) {
            infoRegistroUnidad = "Cantidad de registros : " + listaUnidades.size();
        } else {
            infoRegistroUnidad = "Cantidad de registros : 0";
        }
        return infoRegistroUnidad;
    }

    public void setInfoRegistroUnidad(String infoRegistroUnidad) {
        this.infoRegistroUnidad = infoRegistroUnidad;
    }

    public String getInfoRegistroTercero() {
        getListaTerceros();
        if (listaTerceros != null) {
            infoRegistroTercero = "Cantidad de registros : " + listaTerceros.size();
        } else {
            infoRegistroTercero = "Cantidad de registros : 0";
        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public String getInfoRegistroEmpresa() {
        getListaEmpresas();
        if (listaEmpresas != null) {
            infoRegistroEmpresa = "Cantidad de registros : " + listaEmpresas.size();
        } else {
            infoRegistroEmpresa = "Cantidad de registros : 0";
        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public String getInfoRegistroConcepto() {
        getListaConceptosEmpresa_Estado();
        if (listaConceptosEmpresa_Estado != null) {
            infoRegistroConcepto = "Cantidad de registros : " + listaConceptosEmpresa_Estado.size();
        } else {
            infoRegistroConcepto = "Cantidad de registros : 0";
        }
        return infoRegistroConcepto;
    }

    public void setInfoRegistroConcepto(String infoRegistroConcepto) {
        this.infoRegistroConcepto = infoRegistroConcepto;
    }

    public boolean isActivoDetalle() {
        return activoDetalle;
    }

    public void setActivoDetalle(boolean activoDetalle) {
        this.activoDetalle = activoDetalle;
    }
}

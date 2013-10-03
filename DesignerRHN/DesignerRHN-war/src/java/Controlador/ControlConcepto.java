package Controlador;

import Entidades.Conceptos;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.Unidades;
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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    private List<Conceptos> listaConceptosEmpresa_Estado;
    private List<Conceptos> filtradoConceptosEmpresa_Estado;
    private Conceptos conceptoSeleccionado;
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtradoListaEmpresas;
    private Empresas empresaActual;
    private Empresas backUpEmpresaActual;
    private String estadoConceptoEmpresa;
    private String backUpEstadoConceptoEmpresa;
    private boolean verCambioEmpresa;
    private boolean verCambioEstado;
    private boolean verSeleccionConcepto;
    private boolean verMostrarTodos;
    private boolean mostrarTodos;
    private Map<String, String> conjuntoC;
    private List<Unidades> listaUnidades;
    private Unidades unidadSeleccionada;
    private List<Unidades> filtradoUnidades;
    private List<Terceros> listaTerceros;
    private Terceros terceroSeleccionado;
    private List<Terceros> filtradoTerceros;
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
    private boolean guardado, guardarOk;
    //crear VC
    public Conceptos nuevoConcepto;
    private List<Conceptos> listaConceptosEmpresaCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    //borrar VC
    private List<Conceptos> listaConceptosBorrar;
    //editar celda
    private Conceptos editarConcepto;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Conceptos duplicarConcepto;
    //AUTOCOMPLETAR
    private String codigoUnidad, nombreUnidad, Tercero;
    //RASTRO
    private BigInteger secRegistro;
    //CLONAR CONCEPTO
    private Conceptos conceptoOriginal;
    private Conceptos conceptoClon;
    private int cambioConcepto;

    public ControlConcepto() {
        listaConceptosEmpresa = null;
        listaConceptosEmpresa_Estado = null;
        conjuntoC = new LinkedHashMap<String, String>();
        listaUnidades = new ArrayList<Unidades>();
        listaTerceros = new ArrayList<Terceros>();
        empresaActual = new Empresas();
        backUpEmpresaActual = new Empresas();
        //Otros
        aceptar = true;
        //borrar aficiones
        listaConceptosBorrar = new ArrayList<Conceptos>();
        //crear aficiones
        listaConceptosEmpresaCrear = new ArrayList<Conceptos>();
        k = 0;
        //modificar aficiones
        listaConceptosEmpresaModificar = new ArrayList<Conceptos>();
        //editar
        editarConcepto = new Conceptos();
        cambioEditor = false;
        aceptarEditar = true;
        mostrarTodos = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoConcepto = new Conceptos();
        nuevoConcepto.setUnidad(new Unidades());
        nuevoConcepto.setTercero(new Terceros());
        duplicarConcepto = new Conceptos();
        //CLON
        conceptoClon = new Conceptos();
        conceptoOriginal = new Conceptos();
        permitirIndex = true;
        verCambioEmpresa = false;
        verCambioEstado = false;
        estadoConceptoEmpresa = "S";
        backUpEstadoConceptoEmpresa = "S";
        int i = 0;
        while (i <= 44) {
            i++;
            conjuntoC.put("" + i + "", "" + i + "");
        }
    }

    public void cambiarEstado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            if (bandera == 1) {
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
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
        secRegistro = null;
        cualCelda = -1;
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
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosConceptos");
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
                Tercero = nuevoConcepto.getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                Tercero = duplicarConcepto.getTercero().getNombre();
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
            if (tipoNuevo == 1) {
                nuevoConcepto.getTercero().setNombre(Tercero);
            } else if (tipoNuevo == 2) {
                duplicarConcepto.getTercero().setNombre(Tercero);
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
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 3 || cualCelda == 4) {
                context.update("formularioDialogos:unidadesDialogo");
                context.execute("unidadesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 10) {
                tipoActualizacion = 0;
                context.update("formularioDialogos:TercerosDialogo");
                context.execute("TercerosDialogo.show()");
            }
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarConcepto = listaConceptosEmpresa.get(index);
            }
            if (tipoLista == 1) {
                editarConcepto = filtradoConceptosEmpresa.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editorCodigo");
                context.execute("editorCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editorDescripcion");
                context.execute("editorDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editorNaturaleza");
                context.execute("editorNaturaleza.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editorCodigoUnidad");
                context.execute("editorCodigoUnidad.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editorNombreUnidad");
                context.execute("editorNombreUnidad.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editorCodigoDesprendible");
                context.execute("editorCodigoDesprendible.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editorDescripcionDesprendible");
                context.execute("editorDescripcionDesprendible.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editorIndependienteTesoreria");
                context.execute("editorIndependienteTesoreria.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editorConjunto");
                context.execute("editorConjunto.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editorFechaAcumulacion");
                context.execute("editorFechaAcumulacion.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editorTercero");
                context.execute("editorTercero.show()");
                cualCelda = -1;
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:editorEstado");
                context.execute("editorEstado.show()");
                cualCelda = -1;
            } else if (cualCelda == 12) {
                context.update("formularioDialogos:editorEnvioTesoreria");
                context.execute("editorEnvioTesoreria.show()");
                cualCelda = -1;
            } else if (cualCelda == 13) {
                context.update("formularioDialogos:editorCodigoAleternativo");
                context.execute("editorCodigoAleternativo.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    public void modificarConcepto(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaConceptosEmpresaCrear.contains(listaConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(listaConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(listaConceptosEmpresa.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaConceptosEmpresaCrear.contains(filtradoConceptosEmpresa.get(indice))) {

                    if (listaConceptosEmpresaModificar.isEmpty()) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                    } else if (!listaConceptosEmpresaModificar.contains(filtradoConceptosEmpresa.get(indice))) {
                        listaConceptosEmpresaModificar.add(filtradoConceptosEmpresa.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosConceptos");
        } else if (confirmarCambio.equalsIgnoreCase("UNIDADESCODIGO")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
            } else {
                filtradoConceptosEmpresa.get(indice).getUnidad().setCodigo(codigoUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
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
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("UNIDADESNOMBRE")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
            } else {
                filtradoConceptosEmpresa.get(indice).getUnidad().setNombre(nombreUnidad);
            }
            for (int i = 0; i < listaUnidades.size(); i++) {
                if (listaUnidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
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
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoLista == 0) {
                listaConceptosEmpresa.get(indice).getTercero().setNombre(Tercero);
            } else {
                filtradoConceptosEmpresa.get(indice).getTercero().setNombre(Tercero);
            }
            for (int i = 0; i < listaTerceros.size(); i++) {
                if (listaTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
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
                tipoActualizacion = 0;
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
            if (guardado == true) {
                guardado = false;
            }
            index = -1;
            secRegistro = null;
        }
        context.update("form:datosConceptos");
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listaConceptosEmpresa.get(index).getSecuencia();
            if (cualCelda == 3) {
                codigoUnidad = listaConceptosEmpresa.get(index).getUnidad().getCodigo();
            } else if (cualCelda == 4) {
                nombreUnidad = listaConceptosEmpresa.get(index).getUnidad().getNombre();
            } else if (cualCelda == 10) {
                Tercero = listaConceptosEmpresa.get(index).getTercero().getNombre();
            }
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
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosConceptos");
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
        context.execute("unidadesDialogo.hide()");
        context.reset("formularioDialogos:lovUnidades:globalFilter");
        context.update("formularioDialogos:lovUnidades");
    }

    public void cancelarUnidades() {
        filtradoUnidades = null;
        unidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
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
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosConceptos");
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
        context.execute("TercerosDialogo.hide()");
        context.reset("formularioDialogos:lovTerceros:globalFilter");
        context.update("formularioDialogos:lovTerceros");
    }

    public void cancelarTercero() {
        filtradoTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void lovEmpresas() {
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            listaConceptosEmpresa = null;
            getListaConceptosEmpresa();
            context.update("form:datosConceptos");
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            filtradoListaEmpresas = null;
            aceptar = true;
            context.execute("EmpresasDialogo.hide()");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("formularioDialogos:lovEmpresas");
            backUpEmpresaActual = empresaActual;
            verCambioEmpresa = false;
        } else {
            verCambioEmpresa = true;
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cancelarCambioEmpresa() {
        filtradoListaEmpresas = null;
        verCambioEmpresa = true;
        index = -1;
    }

    public void lovConcepto(int quien) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (quien == 0) {
            if (guardado == true) {
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
            listaConceptosEmpresa_Estado = administrarConceptos.conceptosEmpresaSinPasivos(empresaActual.getSecuencia());
            context.update("formularioDialogos:ConceptosDialogo");
            context.execute("ConceptosDialogo.show()");
            cambioConcepto = 1;
        }
        index = -1;
        secRegistro = null;
        cualCelda = -1;
    }

    public void mostrarTodosConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == true) {
            if (bandera == 1) {
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
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
    }

    public void seleccionConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambioConcepto == 0) {
            if (bandera == 1) {
                columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
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
        context.reset("formularioDialogos:lovConceptos:globalFilter");
        context.update("formularioDialogos:lovConceptos");
        context.execute("ConceptosDialogo.hide()");
    }

    public void cancelarSeleccionConcepto() {
        filtradoConceptosEmpresa_Estado = null;
        conceptoSeleccionado = null;
        aceptar = true;
    }

    public void borrarConcepto() {

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

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConceptos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //GUARDAR
    public void guardarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (guardado == false) {
            if (!listaConceptosBorrar.isEmpty()) {
                for (int i = 0; i < listaConceptosBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listaConceptosBorrar.get(i).isIndependienteConcepto() == true) {
                        listaConceptosBorrar.get(i).setIndependiente("S");
                    } else if (listaConceptosBorrar.get(i).isIndependienteConcepto() == false) {
                        listaConceptosBorrar.get(i).setIndependiente("N");
                    }

                    if (listaConceptosBorrar.get(i).getTercero().getSecuencia() == null) {
                        listaConceptosBorrar.get(i).setTercero(null);
                        administrarConceptos.borrar(listaConceptosBorrar.get(i));
                    } else {
                        administrarConceptos.borrar(listaConceptosBorrar.get(i));
                    }

                }
                listaConceptosBorrar.clear();
            }
            if (!listaConceptosEmpresaCrear.isEmpty()) {
                for (int i = 0; i < listaConceptosEmpresaCrear.size(); i++) {
                    System.out.println("Creando...");
                    if (listaConceptosEmpresaCrear.get(i).isIndependienteConcepto() == true) {
                        listaConceptosEmpresaCrear.get(i).setIndependiente("S");
                    } else if (listaConceptosEmpresaCrear.get(i).isIndependienteConcepto() == false) {
                        listaConceptosEmpresaCrear.get(i).setIndependiente("N");
                    }

                    if (listaConceptosEmpresaCrear.get(i).getTercero().getSecuencia() == null) {
                        listaConceptosEmpresaCrear.get(i).setTercero(null);
                        administrarConceptos.crear(listaConceptosEmpresaCrear.get(i));
                    } else {
                        administrarConceptos.crear(listaConceptosEmpresaCrear.get(i));
                    }
                }
                listaConceptosEmpresaCrear.clear();
            }
            if (!listaConceptosEmpresaModificar.isEmpty()) {
                administrarConceptos.modificar(listaConceptosEmpresaModificar);
                listaConceptosEmpresaModificar.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listaConceptosEmpresa = null;
            context.update("form:datosConceptos");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
        index = -1;
        secRegistro = null;
        if (verCambioEmpresa == true) {
            cambiarEmpresa();
        }
        if (verCambioEstado
                == true) {
            cambiarEstado();
        }
        if (verSeleccionConcepto
                == true) {
            lovConcepto(0);
        }
        if (verMostrarTodos
                == true) {
            mostrarTodosConceptos();
        }
    }

    public void cancelarCambios() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (verCambioEmpresa == true) {
            empresaActual = backUpEmpresaActual;
            context.update("formularioDialogos:lovEmpresas");
            verCambioEmpresa = false;
        }
        if (verCambioEstado == true) {
            estadoConceptoEmpresa = backUpEstadoConceptoEmpresa;
            context.update("form:opciones");
            verCambioEstado = false;
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("width: 20px;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("width: 190px;");
            columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("width: 70px;");
            columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("width: 10px;");
            columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("width: 40px;");
            columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("width: 20px;");
            columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("width:110px;");
            columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("width: 20px;");
            columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("width: 75px;");
            columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("width: 140px;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("width: 55px;");
            columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("width: 20px;");
            columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("width: 130px;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 1;

        } else if (bandera == 1) {
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
            columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 0;
            filtradoConceptosEmpresa = null;
            tipoLista = 0;
        }
    }

    public void agregarNuevoConcepto() {
        int pasa = 0;
        mensajeValidacion = "";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoConcepto.getCodigo() == null) {
            mensajeValidacion = " * Código concepto\n";
            pasa++;
        }
        if (nuevoConcepto.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripción\n";
            pasa++;
        }
        if (nuevoConcepto.getUnidad().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " *Unidad\n";
            pasa++;
        }
        if (pasa == 0) {
            if (nuevoConcepto.getNaturalezaConcepto().equals("NETO") && nuevoConcepto.getConjunto() != 45) {
                pasa++;
                mensajeValidacion = "La naturaleza del concepto es NETO, por lo tanto el conjunto de conceptos debe ser 45.";
            } else if (nuevoConcepto.getNaturalezaConcepto().equals("GASTO") && (nuevoConcepto.getConjunto() > 37 || nuevoConcepto.getConjunto() < 31)) {
                pasa++;
                mensajeValidacion = "La naturaleza del concepto es GASTO, por lo tanto el conjunto de conceptos debe estar entre 31 y 37";
            } else if (nuevoConcepto.getNaturalezaConcepto().equals("DESCUENTO") && (nuevoConcepto.getConjunto() > 30 || nuevoConcepto.getConjunto() < 21)) {
                pasa++;
                mensajeValidacion = "La naturaleza del concepto es DESCUENTO, por lo tanto el conjunto de conceptos debe estar entre 21 y 30";
            } else if (nuevoConcepto.getNaturalezaConcepto().equals("PAGO") && nuevoConcepto.getConjunto() > 20) {
                pasa++;
                mensajeValidacion = "La naturaleza del concepto es PAGO, por lo tanto el conjunto de conceptos debe estar entre 1 y 20";
            } else if (nuevoConcepto.getNaturalezaConcepto().equals("PASIVO") && (nuevoConcepto.getConjunto() > 44 || nuevoConcepto.getConjunto() < 38)) {
                pasa++;
                mensajeValidacion = "La naturaleza del concepto es PASIVO, por lo tanto el conjunto de conceptos debe estar entre 38 y 44";
            }
            if (pasa == 0) {
                if (bandera == 1) {
                    columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
                    columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
                    columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
                    columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
                    columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
                    columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
                    columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
                    columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
                    columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
                    columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
                    columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
                    columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
                    columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
                    columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
                    columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
                    columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
                    columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
                    columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
                    columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
                    columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
                    columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
                    columnaEstado.setFilterStyle("display: none; visibility: hidden;");
                    columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
                    columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
                    columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
                    columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosConceptos");
                    bandera = 0;
                    filtradoConceptosEmpresa = null;
                    tipoLista = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoConcepto.setSecuencia(l);
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

                if (nuevoConcepto.isIndependienteConcepto() == true) {
                    nuevoConcepto.setIndependiente("S");
                } else {
                    nuevoConcepto.setIndependiente("N");
                }

                listaConceptosEmpresaCrear.add(nuevoConcepto);

                listaConceptosEmpresa.add(nuevoConcepto);
                nuevoConcepto = new Conceptos();
                nuevoConcepto.setUnidad(new Unidades());
                nuevoConcepto.setTercero(new Terceros());
                context.update("form:datosConceptos");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("NuevoConceptoDialogo.hide()");
                context.update("formularioDialogos:NuevoConceptoDialogo");
                index = -1;
                secRegistro = null;
            } else {
                context.update("formularioDialogos:validacioNaturaleza");
                context.execute("validacioNaturaleza.show()");
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
    }

    public void duplicarRegistro() {
        if (index >= 0) {
            duplicarConcepto = new Conceptos();
            k++;
            l = BigInteger.valueOf(k);
            duplicarConcepto.setSecuencia(l);
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarConcepto");
            context.execute("DuplicarConceptoDialogo.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        listaConceptosEmpresa.add(duplicarConcepto);
        listaConceptosEmpresaCrear.add(duplicarConcepto);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosConceptos");
        index = -1;
        secRegistro = null;
        context.execute("DuplicarConceptoDialogo.hide()");
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
            columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
            columnaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConceptos");
            bandera = 0;
            filtradoConceptosEmpresa = null;
            tipoLista = 0;
        }
        duplicarConcepto = new Conceptos();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicar() {
        duplicarConcepto = new Conceptos();
    }

    public void refrescar() {
        if (bandera == 1) {
            columnaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigo");
            columnaCodigo.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripción = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripción");
            columnaDescripción.setFilterStyle("display: none; visibility: hidden;");
            columnaNaturaleza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNaturaleza");
            columnaNaturaleza.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoUnidad");
            columnaCodigoUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreUnidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreUnidad");
            columnaNombreUnidad.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoDesprendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoDesprendible");
            columnaCodigoDesprendible.setFilterStyle("display: none; visibility: hidden;");
            columnaDescripcionDesplendible = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaDescripcionDesplendible");
            columnaDescripcionDesplendible.setFilterStyle("display: none; visibility: hidden;");
            columnaConjunto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaConjunto");
            columnaConjunto.setFilterStyle("display: none; visibility: hidden;");
            columnaFechaAcumulado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaFechaAcumulado");
            columnaFechaAcumulado.setFilterStyle("display: none; visibility: hidden;");
            columnaNombreTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaNombreTercero");
            columnaNombreTercero.setFilterStyle("display: none; visibility: hidden;");
            columnaEstado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEstado");
            columnaEstado.setFilterStyle("display: none; visibility: hidden;");
            columnaEnvio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaEnvio");
            columnaEnvio.setFilterStyle("display: none; visibility: hidden;");
            columnaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConceptos:columnaCodigoAlternativo");
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
        k = 0;
        listaConceptosEmpresa = null;
        guardado = true;
        permitirIndex = true;
        mostrarTodos = true;
        conceptoClon = new Conceptos();
        conceptoOriginal = new Conceptos();
        if (verCambioEmpresa == true) {
            cambiarEmpresa();
        }
        if (verCambioEstado == true) {
            cambiarEstado();
        }

        if (verSeleccionConcepto == true) {
            lovConcepto(0);
        }
        if (verMostrarTodos == true) {
            mostrarTodosConceptos();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:mostrarTodos");
        context.update("form:datosConceptos");
        context.update("form:codigoConceptoClon");
        context.update("form:descripcioConceptoClon");
        context.update("form:descripcionClon");
    }

    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaConceptosEmpresa.isEmpty()) {
            if (secRegistro != null) {
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
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
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
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ConceptosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //CLONAR
    public void clonarConcepto() {
        if (conceptoClon.getCodigo() != null && conceptoClon.getDescripcion() != null && conceptoOriginal.getSecuencia() != null) {
            administrarConceptos.clonarConcepto(conceptoOriginal.getSecuencia(), conceptoClon.getCodigo(), conceptoClon.getDescripcion());
            conceptoClon = new Conceptos();
            conceptoOriginal = new Conceptos();
            listaConceptosEmpresa = null;
            getListaConceptosEmpresa();
            RequestContext context = RequestContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage("Información", "Concepto clonado con exito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            context.update("form:datosConceptos");
            context.update("form:codigoConceptoClon");
            context.update("form:descripcioConceptoClon");
            context.update("form:descripcionClon");
        }
    }
    //GETTER AND SETTER

    public List<Conceptos> getListaConceptosEmpresa() {
        if (listaConceptosEmpresa == null) {
            if (!estadoConceptoEmpresa.equals("TODOS")) {
                listaConceptosEmpresa = administrarConceptos.conceptosEmpresaAtivos_Inactivos(empresaActual.getSecuencia(), estadoConceptoEmpresa);
            } else {
                listaConceptosEmpresa = administrarConceptos.conceptosEmpresa(empresaActual.getSecuencia());
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
            listaEmpresas = administrarConceptos.listadoEmpresas();
            if (!listaEmpresas.isEmpty()) {
                empresaActual = listaEmpresas.get(0);
                backUpEmpresaActual = empresaActual;
            }
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
        if (listaUnidades.isEmpty()) {
            listaUnidades = administrarConceptos.lovUnidades();
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
        if (listaTerceros.isEmpty()) {
            listaTerceros = administrarConceptos.lovTerceros(empresaActual.getSecuencia());
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
}
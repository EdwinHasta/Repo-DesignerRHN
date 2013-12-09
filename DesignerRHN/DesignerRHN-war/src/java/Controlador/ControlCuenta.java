/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Cuentas;
import Entidades.Empresas;
import Entidades.Rubrospresupuestales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCuentasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class ControlCuenta implements Serializable {

    @EJB
    AdministrarCuentasInterface administrarCuentas;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Cuentas> listCuentas;
    private List<Cuentas> filtrarListCuentas;
    //
    private List<Empresas> listEmpresas;
    private List<Empresas> filtrarListEmpresas;
    private Empresas empresaActual;
    //
    private List<Rubrospresupuestales> listRubros;
    private List<Rubrospresupuestales> filtrarListRubros;
    private Rubrospresupuestales rubroSeleccionado;
    //
    private List<Cuentas> listCuentasTesoreria;
    private List<Cuentas> filtrarListCuentasTesoreria;
    private Cuentas cuentaSeleccionada;
    //
    private int tipoActualizacion;
    private int bandera;
    private Empresas backUpEmpresaActual;
    //Columnas Tabla VL
    private Column cuentaCodigo, cuentasDescripcion, cuentaContracuenta, cuentaRubro, cuentaManejaNit, cuentaManejaNitEmpleado, cuentaProrrateo, cuentaCodigoA, cuentaConsolidaNit, cuentaIncluyeShort, cuentaAsociadaSAP, cuentaSubCuenta;
    //Otros
    private boolean aceptar;
    private int index;
    private List<Cuentas> listCuentasModificar;
    private boolean guardado, guardarOk;
    public Cuentas nuevoCuentas;
    private List<Cuentas> listCuentasCrear;
    private BigInteger l;
    private int k;
    private List<Cuentas> listCuentasBorrar;
    private Cuentas editarCuentas;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    private Cuentas duplicarCuentas;
    private boolean permitirIndex, permitirIndexTS;
    //Variables Autompletar
    //private String motivoCambioSueldo, tiposEntidades, tiposSueldos, terceros;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private String nombreXML;
    private String nombreTabla;
    private boolean retroactivo;
    private int valorTotal;
    private boolean cambiosCuentas;
    private BigInteger secRegistroCuentas;
    private BigInteger backUpSecRegistroCuentas;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int indexAux;
    private String cuenta, rubroP;
    private Cuentas cuentaActual;

    public ControlCuenta() {

        cuentaActual = null;
        indexAux = 0;
        listRubros = null;
        listCuentasTesoreria = null;
        backUpEmpresaActual = new Empresas();
        empresaActual = new Empresas();
        listEmpresas = null;
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroCuentas = null;
        backUpSecRegistroCuentas = null;
        listCuentas = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listCuentasBorrar = new ArrayList<Cuentas>();
        //crear aficiones
        listCuentasCrear = new ArrayList<Cuentas>();
        k = 0;
        //modificar aficiones
        listCuentasModificar = new ArrayList<Cuentas>();
        //editar
        editarCuentas = new Cuentas();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoCuentas = new Cuentas();
        nuevoCuentas.setRubropresupuestal(new Rubrospresupuestales());
        nuevoCuentas.setContracuentatesoreria(new Cuentas());
        index = -1;
        bandera = 0;
        permitirIndex = true;
        permitirIndexTS = true;

        nombreTabla = ":formExportarCuentas:datosCuentasExportar";
        nombreXML = "CuentasXML";


        retroactivo = true;
        duplicarCuentas = new Cuentas();
        cambiosCuentas = false;
    }

    public void obtenerEmpresa() {
        index = -1;
        listCuentas = null;
        empresaActual = getEmpresaActual();
    }

   
    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarCuenta(int indice) {
        if (tipoLista == 0) {
            if (!listCuentasCrear.contains(listCuentas.get(indice))) {
                if (listCuentasModificar.isEmpty()) {
                    listCuentasModificar.add(listCuentas.get(indice));
                } else if (!listCuentasModificar.contains(listCuentas.get(indice))) {
                    listCuentasModificar.add(listCuentas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroCuentas = null;
        } else {
            if (!listCuentasCrear.contains(filtrarListCuentas.get(indice))) {
                if (listCuentasModificar.isEmpty()) {
                    listCuentasModificar.add(filtrarListCuentas.get(indice));
                } else if (!listCuentasModificar.contains(filtrarListCuentas.get(indice))) {
                    listCuentasModificar.add(filtrarListCuentas.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroCuentas = null;
        }
        cambiosCuentas = true;
    }

    public void modificarCuentaAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONTRACUENTA")) {
            if (tipoLista == 0) {
                listCuentas.get(indice).getContracuentatesoreria().setDescripcion(cuenta);
            } else {
                filtrarListCuentas.get(indice).getContracuentatesoreria().setDescripcion(cuenta);
            }
            for (int i = 0; i < listCuentasTesoreria.size(); i++) {
                if (listCuentasTesoreria.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listCuentas.get(indice).setContracuentatesoreria(listCuentasTesoreria.get(indiceUnicoElemento));
                } else {
                    filtrarListCuentas.get(indice).setContracuentatesoreria(listCuentasTesoreria.get(indiceUnicoElemento));
                }
                listCuentasTesoreria.clear();
                getListCuentasTesoreria();
            } else {
                permitirIndex = false;
                context.update("form:ContracuentaDialogo");
                context.execute("ContracuentaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("RUBRO")) {
            if (tipoLista == 0) {
                listCuentas.get(indice).getRubropresupuestal().setDescripcion(rubroP);
            } else {
                filtrarListCuentas.get(indice).getRubropresupuestal().setDescripcion(rubroP);
            }
            for (int i = 0; i < listRubros.size(); i++) {
                if (listRubros.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listCuentas.get(indice).setRubropresupuestal(listRubros.get(indiceUnicoElemento));
                } else {
                    filtrarListCuentas.get(indice).setRubropresupuestal(listRubros.get(indiceUnicoElemento));
                }
                listRubros.clear();
                getListRubros();
            } else {
                permitirIndex = false;
                context.update("form:RubrosDialogo");
                context.execute("RubrosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listCuentasCrear.contains(listCuentas.get(indice))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(listCuentas.get(indice));
                    } else if (!listCuentasModificar.contains(listCuentas.get(indice))) {
                        listCuentasModificar.add(listCuentas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroCuentas = null;
            } else {
                if (!listCuentasCrear.contains(filtrarListCuentas.get(indice))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(filtrarListCuentas.get(indice));
                    } else if (!listCuentasModificar.contains(filtrarListCuentas.get(indice))) {
                        listCuentasModificar.add(filtrarListCuentas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroCuentas = null;
            }
            cambiosCuentas = true;
        }
        context.update("form:datosCuenta");
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
        secRegistroCuentas = listCuentas.get(index).getSecuencia();
        cuentaActual = listCuentas.get(index);

        if (cualCelda == 5) {
            cuenta = listCuentas.get(index).getContracuentatesoreria().getDescripcion();
        }
        if (cualCelda == 3) {
            rubroP = listCuentas.get(index).getRubropresupuestal().getDescripcion();
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (cambiosCuentas == true) {
            guardarCambiosCuenta();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosCuenta() {
        if (guardado == false) {
            if (!listCuentasBorrar.isEmpty()) {
                administrarCuentas.borrarCuentas(listCuentasBorrar);
                listCuentasBorrar.clear();
            }
            if (!listCuentasCrear.isEmpty()) {
                administrarCuentas.crearCuentas(listCuentasCrear);
                listCuentasCrear.clear();
            }
            if (!listCuentasModificar.isEmpty()) {
                administrarCuentas.modificarCuentas(listCuentasModificar);
                listCuentasModificar.clear();
            }
            listCuentas = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuenta");
            k = 0;
        }
        index = -1;
        secRegistroCuentas = null;
        cambiosCuentas = false;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        System.out.println("Cancelo Modificacion");
        if (bandera == 1) {
            cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
            cuentaCodigo.setFilterStyle("display: none; visibility: hidden;");
            cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
            cuentasDescripcion.setFilterStyle("display: none; visibility: hidden;");
            cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
            cuentaContracuenta.setFilterStyle("display: none; visibility: hidden;");
            cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
            cuentaRubro.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
            cuentaManejaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
            cuentaManejaNitEmpleado.setFilterStyle("display: none; visibility: hidden;");
            cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
            cuentaProrrateo.setFilterStyle("display: none; visibility: hidden;");
            cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
            cuentaCodigoA.setFilterStyle("display: none; visibility: hidden;");
            cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
            cuentaConsolidaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
            cuentaIncluyeShort.setFilterStyle("display: none; visibility: hidden;");
            cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
            cuentaAsociadaSAP.setFilterStyle("display: none; visibility: hidden;");
            cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
            cuentaSubCuenta.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCuenta");
            bandera = 0;
            filtrarListCuentas = null;
            tipoLista = 0;
        }
        listCuentasBorrar.clear();
        listCuentasCrear.clear();
        listCuentasModificar.clear();
        index = -1;
        indexAux = -1;
        secRegistroCuentas = null;
        k = 0;
        listCuentas = null;
        listCuentasTesoreria = null;
        guardado = true;
        cambiosCuentas = false;
        cuentaActual = null;
        getListCuentas();
        getListCuentasTesoreria();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCuenta");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarCuentas = listCuentas.get(index);
            }
            if (tipoLista == 1) {
                editarCuentas = filtrarListCuentas.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoCuentaD");
                context.execute("editarCodigoCuentaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarDescripcionCuentaD");
                context.execute("editarDescripcionCuentaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarCuentaTCuentaD");
                context.execute("editarCuentaTCuentaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarRubroCuentaD");
                context.execute("editarRubroCuentaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 11) {
                context.update("formularioDialogos:editarCodigoACuentaD");
                context.execute("editarCodigoACuentaD.show()");
                cualCelda = -1;
            }
        }

        index = -1;
        secRegistroCuentas = null;
    }

    public void validarIngresoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:NuevoRegistroCuenta");
        context.execute("NuevoRegistroCuenta.show()");

    }

    public void validarDuplicadoRegistro() {
        System.out.println("Entro");
        if (index >= 0) {
            System.out.println("Entro If");
            duplicarCuenta();
        }
    }

    public void validarBorradoRegistro() {
        if (index >= 0) {
            borrarCuenta();
        }
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevoCuenta() {
        if (bandera == 1) {
            cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
            cuentaCodigo.setFilterStyle("display: none; visibility: hidden;");
            cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
            cuentasDescripcion.setFilterStyle("display: none; visibility: hidden;");
            cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
            cuentaContracuenta.setFilterStyle("display: none; visibility: hidden;");
            cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
            cuentaRubro.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
            cuentaManejaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
            cuentaManejaNitEmpleado.setFilterStyle("display: none; visibility: hidden;");
            cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
            cuentaProrrateo.setFilterStyle("display: none; visibility: hidden;");
            cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
            cuentaCodigoA.setFilterStyle("display: none; visibility: hidden;");
            cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
            cuentaConsolidaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
            cuentaIncluyeShort.setFilterStyle("display: none; visibility: hidden;");
            cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
            cuentaAsociadaSAP.setFilterStyle("display: none; visibility: hidden;");
            cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
            cuentaSubCuenta.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCuenta");
            bandera = 0;
            filtrarListCuentas = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
        k++;
        BigInteger var = BigInteger.valueOf(k);
        nuevoCuentas.setSecuencia(var);
        nuevoCuentas.setEmpresa(empresaActual);
        listCuentasCrear.add(nuevoCuentas);
        listCuentas.add(nuevoCuentas);
        ////------////
        nuevoCuentas = new Cuentas();
        nuevoCuentas.setRubropresupuestal(new Rubrospresupuestales());
        nuevoCuentas.setContracuentatesoreria(new Cuentas());
        ////-----////
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCuenta");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        context.execute("NuevoRegistroTercero.hide()");
        cambiosCuentas = true;
        index = -1;
        secRegistroCuentas = null;
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevoCuenta() {
        nuevoCuentas = new Cuentas();
        nuevoCuentas.setRubropresupuestal(new Rubrospresupuestales());
        nuevoCuentas.setContracuentatesoreria(new Cuentas());
        index = -1;
        secRegistroCuentas = null;
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
    public void duplicarCuenta() {
        System.out.println("Entro Metodo");
        if (index >= 0) {
            System.out.println("Entro If Metodo");
            duplicarCuentas = new Cuentas();
            if (tipoLista == 0) {
                duplicarCuentas.setCodigo(listCuentas.get(index).getCodigo());
                duplicarCuentas.setDescripcion(listCuentas.get(index).getDescripcion());
                duplicarCuentas.setNaturaleza(listCuentas.get(index).getNaturaleza());
                duplicarCuentas.setContracuentatesoreria(listCuentas.get(index).getContracuentatesoreria());
                duplicarCuentas.setTipo(listCuentas.get(index).getTipo());
                duplicarCuentas.setRubropresupuestal(listCuentas.get(index).getRubropresupuestal());
                duplicarCuentas.setManejanit(listCuentas.get(index).getManejanit());
                duplicarCuentas.setManejanitempleado(listCuentas.get(index).getManejanitempleado());
                duplicarCuentas.setProrrateo(listCuentas.get(index).getProrrateo());
                duplicarCuentas.setManejacentrocosto(listCuentas.get(index).getManejacentrocosto());
                duplicarCuentas.setIncluyecentrocostocodigocuenta(listCuentas.get(index).getIncluyecentrocostocodigocuenta());
                duplicarCuentas.setCodigoalternativo(listCuentas.get(index).getCodigoalternativo());
                duplicarCuentas.setConsolidanitempresa(listCuentas.get(index).getConsolidanitempresa());
                duplicarCuentas.setCuentaasociadasap(listCuentas.get(index).getCuentaasociadasap());
                duplicarCuentas.setManejasubcuenta(listCuentas.get(index).getManejasubcuenta());
            }
            if (tipoLista == 1) {

                duplicarCuentas.setCodigo(filtrarListCuentas.get(index).getCodigo());
                duplicarCuentas.setDescripcion(filtrarListCuentas.get(index).getDescripcion());
                duplicarCuentas.setNaturaleza(filtrarListCuentas.get(index).getNaturaleza());
                duplicarCuentas.setContracuentatesoreria(filtrarListCuentas.get(index).getContracuentatesoreria());
                duplicarCuentas.setTipo(filtrarListCuentas.get(index).getTipo());
                duplicarCuentas.setRubropresupuestal(filtrarListCuentas.get(index).getRubropresupuestal());
                duplicarCuentas.setManejanit(filtrarListCuentas.get(index).getManejanit());
                duplicarCuentas.setManejanitempleado(filtrarListCuentas.get(index).getManejanitempleado());
                duplicarCuentas.setProrrateo(filtrarListCuentas.get(index).getProrrateo());
                duplicarCuentas.setManejacentrocosto(filtrarListCuentas.get(index).getManejacentrocosto());
                duplicarCuentas.setIncluyecentrocostocodigocuenta(filtrarListCuentas.get(index).getIncluyecentrocostocodigocuenta());
                duplicarCuentas.setCodigoalternativo(filtrarListCuentas.get(index).getCodigoalternativo());
                duplicarCuentas.setConsolidanitempresa(filtrarListCuentas.get(index).getConsolidanitempresa());
                duplicarCuentas.setCuentaasociadasap(filtrarListCuentas.get(index).getCuentaasociadasap());
                duplicarCuentas.setManejasubcuenta(filtrarListCuentas.get(index).getManejasubcuenta());

            }
            if (duplicarCuentas.getRubropresupuestal().getSecuencia() == null) {
                duplicarCuentas.setRubropresupuestal(new Rubrospresupuestales());
            }
            if (duplicarCuentas.getContracuentatesoreria().getSecuencia() == null) {
                duplicarCuentas.setContracuentatesoreria(new Cuentas());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroCuenta");
            context.execute("DuplicarRegistroCuenta.show()");
            index = -1;
            secRegistroCuentas = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicar() {
        index = indexAux;
        if (index >= 0) {
            if (bandera == 1) {
                cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
                cuentaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
                cuentasDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
                cuentaContracuenta.setFilterStyle("display: none; visibility: hidden;");
                cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
                cuentaRubro.setFilterStyle("display: none; visibility: hidden;");
                cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
                cuentaManejaNit.setFilterStyle("display: none; visibility: hidden;");
                cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
                cuentaManejaNitEmpleado.setFilterStyle("display: none; visibility: hidden;");
                cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
                cuentaProrrateo.setFilterStyle("display: none; visibility: hidden;");
                cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
                cuentaCodigoA.setFilterStyle("display: none; visibility: hidden;");
                cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
                cuentaConsolidaNit.setFilterStyle("display: none; visibility: hidden;");
                cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
                cuentaIncluyeShort.setFilterStyle("display: none; visibility: hidden;");
                cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
                cuentaAsociadaSAP.setFilterStyle("display: none; visibility: hidden;");
                cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
                cuentaSubCuenta.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCuenta");
                bandera = 0;
                filtrarListCuentas = null;
                tipoLista = 0;
            }
            k++;
            BigInteger var = BigInteger.valueOf(k);
            duplicarCuentas.setSecuencia(var);
            duplicarCuentas.setEmpresa(empresaActual);
            listCuentasCrear.add(duplicarCuentas);
            listCuentas.add(duplicarCuentas);
            duplicarCuentas = new Cuentas();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuenta");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("DuplicarRegistroCuenta.hide()");
            cambiosCuentas = true;
            index = -1;
            secRegistroCuentas = null;
        }
    }

    public void limpiarDuplicarCuenta() {
        duplicarCuentas = new Cuentas();
        nuevoCuentas.setRubropresupuestal(new Rubrospresupuestales());
        nuevoCuentas.setContracuentatesoreria(new Cuentas());
        index = -1;
        secRegistroCuentas = null;
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarCuenta() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listCuentasModificar.isEmpty() && listCuentasModificar.contains(listCuentas.get(index))) {
                    int modIndex = listCuentasModificar.indexOf(listCuentas.get(index));
                    listCuentasModificar.remove(modIndex);
                    listCuentasBorrar.add(listCuentas.get(index));
                } else if (!listCuentasCrear.isEmpty() && listCuentasCrear.contains(listCuentas.get(index))) {
                    int crearIndex = listCuentasCrear.indexOf(listCuentas.get(index));
                    listCuentasCrear.remove(crearIndex);
                } else {
                    listCuentasBorrar.add(listCuentas.get(index));
                }
                listCuentas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listCuentasModificar.isEmpty() && listCuentasModificar.contains(filtrarListCuentas.get(index))) {
                    int modIndex = listCuentasModificar.indexOf(filtrarListCuentas.get(index));
                    listCuentasModificar.remove(modIndex);
                    listCuentasBorrar.add(filtrarListCuentas.get(index));
                } else if (!listCuentasCrear.isEmpty() && listCuentasCrear.contains(filtrarListCuentas.get(index))) {
                    int crearIndex = listCuentasCrear.indexOf(filtrarListCuentas.get(index));
                    listCuentasCrear.remove(crearIndex);
                } else {
                    listCuentasBorrar.add(filtrarListCuentas.get(index));
                }
                int VLIndex = listCuentas.indexOf(filtrarListCuentas.get(index));
                listCuentas.remove(VLIndex);
                filtrarListCuentas.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCuenta");

            index = -1;
            secRegistroCuentas = null;
            cambiosCuentas = true;
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
            filtradoCuenta();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoCuenta() {
        if (index >= 0) {
            if (bandera == 0) {
                cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
                cuentaCodigo.setFilterStyle("width: 80px");
                cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
                cuentasDescripcion.setFilterStyle("width: 80px");
                cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
                cuentaContracuenta.setFilterStyle("width: 80px");
                cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
                cuentaRubro.setFilterStyle("width: 80px");
                cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
                cuentaManejaNit.setFilterStyle("width: 40px");
                cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
                cuentaManejaNitEmpleado.setFilterStyle("width: 840px0px");
                cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
                cuentaProrrateo.setFilterStyle("width: 8040pxpx");
                cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
                cuentaCodigoA.setFilterStyle("width: 80px");
                cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
                cuentaConsolidaNit.setFilterStyle("width: 40px");
                cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
                cuentaIncluyeShort.setFilterStyle("width: 40px");
                cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
                cuentaAsociadaSAP.setFilterStyle("width: 40px");
                cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
                cuentaSubCuenta.setFilterStyle("width: 40px");
                RequestContext.getCurrentInstance().update("form:datosCuenta");
                bandera = 1;
            } else if (bandera == 1) {
                cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
                cuentaCodigo.setFilterStyle("display: none; visibility: hidden;");
                cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
                cuentasDescripcion.setFilterStyle("display: none; visibility: hidden;");
                cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
                cuentaContracuenta.setFilterStyle("display: none; visibility: hidden;");
                cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
                cuentaRubro.setFilterStyle("display: none; visibility: hidden;");
                cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
                cuentaManejaNit.setFilterStyle("display: none; visibility: hidden;");
                cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
                cuentaManejaNitEmpleado.setFilterStyle("display: none; visibility: hidden;");
                cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
                cuentaProrrateo.setFilterStyle("display: none; visibility: hidden;");
                cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
                cuentaCodigoA.setFilterStyle("display: none; visibility: hidden;");
                cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
                cuentaConsolidaNit.setFilterStyle("display: none; visibility: hidden;");
                cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
                cuentaIncluyeShort.setFilterStyle("display: none; visibility: hidden;");
                cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
                cuentaAsociadaSAP.setFilterStyle("display: none; visibility: hidden;");
                cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
                cuentaSubCuenta.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCuenta");
                bandera = 0;
                filtrarListCuentas = null;
                tipoLista = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            cuentaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigo");
            cuentaCodigo.setFilterStyle("display: none; visibility: hidden;");
            cuentasDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentasDescripcion");
            cuentasDescripcion.setFilterStyle("display: none; visibility: hidden;");
            cuentaContracuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaContracuenta");
            cuentaContracuenta.setFilterStyle("display: none; visibility: hidden;");
            cuentaRubro = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaRubro");
            cuentaRubro.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNit");
            cuentaManejaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaManejaNitEmpleado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaManejaNitEmpleado");
            cuentaManejaNitEmpleado.setFilterStyle("display: none; visibility: hidden;");
            cuentaProrrateo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaProrrateo");
            cuentaProrrateo.setFilterStyle("display: none; visibility: hidden;");
            cuentaCodigoA = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaCodigoA");
            cuentaCodigoA.setFilterStyle("display: none; visibility: hidden;");
            cuentaConsolidaNit = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaConsolidaNit");
            cuentaConsolidaNit.setFilterStyle("display: none; visibility: hidden;");
            cuentaIncluyeShort = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaIncluyeShort");
            cuentaIncluyeShort.setFilterStyle("display: none; visibility: hidden;");
            cuentaAsociadaSAP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaAsociadaSAP");
            cuentaAsociadaSAP.setFilterStyle("display: none; visibility: hidden;");
            cuentaSubCuenta = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCuenta:cuentaSubCuenta");
            cuentaSubCuenta.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCuenta");
            bandera = 0;
            filtrarListCuentas = null;
            tipoLista = 0;
        }

        listCuentasBorrar.clear();
        listCuentasCrear.clear();
        listCuentasModificar.clear();
        index = -1;
        secRegistroCuentas = null;
        listCuentas = null;
        listEmpresas = null;
        empresaActual = null;
        k = 0;
        listCuentas = null;
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
            context.update("form:ContracuentaDialogo");
            context.execute("ContracuentaDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:RubrosDialogo");
            context.execute("RubrosDialogo.show()");
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
                context.update("form:ContracuentaDialogo");
                context.execute("ContracuentaDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 5) {
                context.update("form:RubrosDialogo");
                context.execute("RubrosDialogo.show()");
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
        if (Campo.equals("CONTRACUENTA")) {
            if (tipoNuevo == 1) {
                cuenta = nuevoCuentas.getContracuentatesoreria().getDescripcion();
            } else if (tipoNuevo == 2) {
                cuenta = duplicarCuentas.getContracuentatesoreria().getDescripcion();
            }
        } else if (Campo.equals("RUBRO")) {
            if (tipoNuevo == 1) {
                rubroP = nuevoCuentas.getRubropresupuestal().getDescripcion();
            } else if (tipoNuevo == 2) {
                rubroP = duplicarCuentas.getRubropresupuestal().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoCuenta(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CONTRACUENTA")) {
            if (tipoNuevo == 1) {
                nuevoCuentas.getContracuentatesoreria().setDescripcion(cuenta);
            } else if (tipoNuevo == 2) {
                duplicarCuentas.getContracuentatesoreria().setDescripcion(cuenta);
            }
            for (int i = 0; i < listCuentasTesoreria.size(); i++) {
                if (listCuentasTesoreria.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCuentas.setContracuentatesoreria(listCuentasTesoreria.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTerceroCT");
                } else if (tipoNuevo == 2) {
                    duplicarCuentas.setContracuentatesoreria(listCuentasTesoreria.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTerceroCT");
                }
                listCuentasTesoreria.clear();
                getListCuentasTesoreria();
            } else {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTerceroCT");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTerceroCT");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("RUBROS")) {
            if (tipoNuevo == 1) {
                nuevoCuentas.getRubropresupuestal().setDescripcion(rubroP);
            } else if (tipoNuevo == 2) {
                duplicarCuentas.getRubropresupuestal().setDescripcion(rubroP);
            }
            for (int i = 0; i < listRubros.size(); i++) {
                if (listRubros.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoCuentas.setRubropresupuestal(listRubros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoAT");
                } else if (tipoNuevo == 2) {
                    duplicarCuentas.setRubropresupuestal(listRubros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoAT");
                }
                listRubros.clear();
                getListRubros();
            } else {
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCodigoAT");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCodigoAT");
                }
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarCuenta() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listCuentas.get(index).setContracuentatesoreria(cuentaSeleccionada);
                if (!listCuentasCrear.contains(listCuentas.get(index))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(listCuentas.get(index));
                    } else if (!listCuentasModificar.contains(listCuentas.get(index))) {
                        listCuentasModificar.add(listCuentas.get(index));
                    }
                }
            } else {
                filtrarListCuentas.get(index).setContracuentatesoreria(cuentaSeleccionada);
                if (!listCuentasCrear.contains(filtrarListCuentas.get(index))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(filtrarListCuentas.get(index));
                    } else if (!listCuentasModificar.contains(filtrarListCuentas.get(index))) {
                        listCuentasModificar.add(filtrarListCuentas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosCuentas = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCuentaContracuenta");
        } else if (tipoActualizacion == 1) {
            nuevoCuentas.setContracuentatesoreria(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTerceroCT");
        } else if (tipoActualizacion == 2) {
            duplicarCuentas.setContracuentatesoreria(cuentaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTerceroCT");
        }
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroCuentas = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCuenta() {
        filtrarListCuentas = null;
        cuentaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroCuentas = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarRubro() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listCuentas.get(index).setRubropresupuestal(rubroSeleccionado);
                if (!listCuentasCrear.contains(listCuentas.get(index))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(listCuentas.get(index));
                    } else if (!listCuentasModificar.contains(listCuentas.get(index))) {
                        listCuentasModificar.add(listCuentas.get(index));
                    }
                }
            } else {
                filtrarListCuentas.get(index).setRubropresupuestal(rubroSeleccionado);
                if (!listCuentasCrear.contains(filtrarListCuentas.get(index))) {
                    if (listCuentasModificar.isEmpty()) {
                        listCuentasModificar.add(filtrarListCuentas.get(index));
                    } else if (!listCuentasModificar.contains(filtrarListCuentas.get(index))) {
                        listCuentasModificar.add(filtrarListCuentas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosCuentas = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarCuentaRubro");
        } else if (tipoActualizacion == 1) {
            nuevoCuentas.setRubropresupuestal(rubroSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCiudadT");
        } else if (tipoActualizacion == 2) {
            duplicarCuentas.setRubropresupuestal(rubroSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTCiudadT");
        }
        filtrarListRubros = null;
        rubroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroCuentas = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioRubro() {
        filtrarListRubros = null;
        rubroSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroCuentas = null;
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
            nombreTabla = ":formExportarCuentas:datosCuentasExportar";
            nombreXML = "CuentasXML";
        }
        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (index >= 0) {
            exportPDFT();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFT() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarCuentas:datosCuentasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CuentasPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroCuentas = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLST();
        }

    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLST() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarCuentas:datosCuentasExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CuentasXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroCuentas = null;
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

    public void dialogoSeleccionarCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:BuscarCuentasDialogo");
        context.execute("BuscarCuentasDialogo.show()");
    }

    public void cancelarSeleccionCuenta() {
        cuentaSeleccionada = new Cuentas();
        filtrarListCuentasTesoreria = null;
    }

    public void validarSeleccionCuenta() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosCuentas == false) {
            listCuentas = null;
            listCuentas = new ArrayList<Cuentas>();
            listCuentas.add(cuentaSeleccionada);
            cuentaSeleccionada = new Cuentas();
            filtrarListCuentasTesoreria = null;
            context.update("form:datosCuenta");
        } else {
            cuentaSeleccionada = new Cuentas();
            filtrarListCuentasTesoreria = null;
            context.execute("confirmarGuardar.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosCuentas == false) {
            listCuentas = null;
            getListCuentas();
            context.update("form:datosCuenta");
            cuentaActual = null;
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS
    public void verificarRastroTabla() {
        verificarRastroTerceros();
        index = -1;
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listCuentas != null) {
            if (secRegistroCuentas != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroCuentas, "CUENTAS");
                backUpSecRegistroCuentas = secRegistroCuentas;
                backUp = secRegistroCuentas;
                secRegistroCuentas = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Cuentas";
                    msnConfirmarRastro = "La tabla CUENTAS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("CUENTAS")) {
                nombreTablaRastro = "Cuentas";
                msnConfirmarRastroHistorico = "La tabla CUENTAS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void lovEmpresas() {
        index = -1;
        secRegistroCuentas = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosCuentas == false) {
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            filtrarListEmpresas = null;
            aceptar = true;
            context.execute("EmpresasDialogo.hide()");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("formularioDialogos:lovEmpresas");
            context.update("formularioDialogos:aceptarE");
            backUpEmpresaActual = empresaActual;
            listCuentas = null;
            listCuentasTesoreria = null;
            cuentaActual = null;
            getListCuentas();
            getListCuentasTesoreria();
            context.update("form:datosCuenta");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cancelarCambioEmpresa() {
        index = -1;
        filtrarListEmpresas = null;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //GET - SET 
    public List<Cuentas> getListCuentas() {
        try {
            if (listCuentas == null) {
                listCuentas = null;
                listCuentas = administrarCuentas.listCuentasEmpresa(empresaActual.getSecuencia());
                if (listCuentas != null) {
                    for (int i = 0; i < listCuentas.size(); i++) {
                        if (listCuentas.get(i).getRubropresupuestal() == null) {
                            listCuentas.get(i).setRubropresupuestal(new Rubrospresupuestales());
                        }
                        if (listCuentas.get(i).getContracuentatesoreria() == null) {
                            listCuentas.get(i).setContracuentatesoreria(new Cuentas());
                        }
                    }
                }
            }
            return listCuentas;
        } catch (Exception e) {
            System.out.println("Error getListTerceros " + e.toString());
            return null;
        }
    }

    public void setListCuentas(List<Cuentas> t) {
        this.listCuentas = t;
    }

    public List<Cuentas> getFiltrarListCuentas() {
        return filtrarListCuentas;
    }

    public void setFiltrarListCuentas(List<Cuentas> t) {
        this.filtrarListCuentas = t;
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

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public boolean isRetroactivo() {
        return retroactivo;
    }

    public void setRetroactivo(boolean retroactivo) {
        this.retroactivo = retroactivo;
    }

    public BigInteger getSecRegistroCuentas() {
        return secRegistroCuentas;
    }

    public void setSecRegistroCuentas(BigInteger secRegistroCuentas) {
        this.secRegistroCuentas = secRegistroCuentas;
    }

    public BigInteger getBackUpSecRegistroCuentas() {
        return backUpSecRegistroCuentas;
    }

    public void setBackUpSecRegistroCuentas(BigInteger b) {
        this.backUpSecRegistroCuentas = b;
    }

    public List<Cuentas> getListCuentasModificar() {
        return listCuentasModificar;
    }

    public void setListCuentasModificar(List<Cuentas> listTerceroCuentas) {
        this.listCuentasModificar = listTerceroCuentas;
    }

    public Cuentas getNuevoCuentas() {
        return nuevoCuentas;
    }

    public void setNuevoCuentas(Cuentas nuevoCuentas) {
        this.nuevoCuentas = nuevoCuentas;
    }

    public List<Cuentas> getListCuentasCrear() {
        return listCuentasCrear;
    }

    public void setListCuentasCrear(List<Cuentas> listCuentasCrear) {
        this.listCuentasCrear = listCuentasCrear;
    }

    public List<Cuentas> getListCuentasBorrar() {
        return listCuentasBorrar;
    }

    public void setListCuentasBorrar(List<Cuentas> listCuentasBorrar) {
        this.listCuentasBorrar = listCuentasBorrar;
    }

    public Cuentas getEditarCuentas() {
        return editarCuentas;
    }

    public void setEditarCuentas(Cuentas editarCuentas) {
        this.editarCuentas = editarCuentas;
    }

    public Cuentas getDuplicarCuentas() {
        return duplicarCuentas;
    }

    public void setDuplicarCuentas(Cuentas duplicarCuentas) {
        this.duplicarCuentas = duplicarCuentas;
    }

    public BigInteger getSecRegistroCuentaso() {
        return secRegistroCuentas;
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

    public List<Empresas> getListEmpresas() {
        if (listEmpresas == null) {
            listEmpresas = administrarCuentas.listEmpresas();
            if (!listEmpresas.isEmpty()) {
                empresaActual = listEmpresas.get(0);
                backUpEmpresaActual = empresaActual;
            }
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public Empresas getEmpresaActual() {
        getListEmpresas();
        return empresaActual;
    }

    public void setEmpresaActual(Empresas empresaActual) {
        this.empresaActual = empresaActual;
    }

    public Empresas getBackUpEmpresaActual() {
        return backUpEmpresaActual;
    }

    public void setBackUpEmpresaActual(Empresas backUpEmpresaActual) {
        this.backUpEmpresaActual = backUpEmpresaActual;
    }

    public List<Rubrospresupuestales> getListRubros() {
        if (listRubros == null) {
            listRubros = administrarCuentas.listRubros();
        }
        return listRubros;
    }

    public void setListRubros(List<Rubrospresupuestales> listCiudades) {
        this.listRubros = listCiudades;
    }

    public List<Rubrospresupuestales> getFiltrarListRubros() {
        return filtrarListRubros;
    }

    public void setFiltrarListRubros(List<Rubrospresupuestales> filtrar) {
        this.filtrarListRubros = filtrar;
    }

    public Rubrospresupuestales getRubroSeleccionado() {
        return rubroSeleccionado;
    }

    public void setRubroSeleccionado(Rubrospresupuestales seleccionado) {
        this.rubroSeleccionado = seleccionado;
    }

    public List<Cuentas> getListCuentasTesoreria() {
        if (listCuentasTesoreria == null) {
            listCuentasTesoreria = administrarCuentas.listCuentasEmpresa(empresaActual.getSecuencia());
        }
        return listCuentasTesoreria;
    }

    public void setListCuentasTesoreria(List<Cuentas> CuentasTesoreria) {
        this.listCuentasTesoreria = CuentasTesoreria;
    }

    public List<Cuentas> getFiltrarListCuentasTesoreria() {
        return filtrarListCuentasTesoreria;
    }

    public void setFiltrarListCuentasTesoreria(List<Cuentas> filtrarList) {
        this.filtrarListCuentasTesoreria = filtrarList;
    }

    public Cuentas getCuentaSeleccionada() {
        return cuentaSeleccionada;
    }

    public void setCuentaSeleccionada(Cuentas seleccionado) {
        this.cuentaSeleccionada = seleccionado;
    }

    public Cuentas getCuentaActual() {
        return cuentaActual;
    }

    public void setCuentaActual(Cuentas cuentaActual) {
        this.cuentaActual = cuentaActual;
    }
}

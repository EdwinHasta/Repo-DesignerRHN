/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Ciudades;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTerceroInterface;
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
public class ControlTercero implements Serializable {

    @EJB
    AdministrarTerceroInterface administrarTercero;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Terceros> listTerceros;
    private List<Terceros> filtrarListTercero;
    //
    private List<TercerosSucursales> listTercerosSucursales;
    private List<TercerosSucursales> filtrarListTercerosSucursales;
    //
    private List<Empresas> listEmpresas;
    private List<Empresas> filtrarListEmpresas;
    private Empresas empresaActual;
    //
    private List<Ciudades> listCiudades;
    private List<Ciudades> filtrarListCiudades;
    private Ciudades ciudadSeleccionada;
    //
    private List<Terceros> listTerceroConsolidador;
    private List<Terceros> filtrarListTerceroConsolidador;
    private Terceros terceroCSeleccionado;
    //
    private Empleados empleado;
    private int tipoActualizacion;
    private int bandera;
    private int banderaTS;
    private Empresas backUpEmpresaActual;
    //Columnas Tabla VL
    private Column terceroNombre, terceroNIT, terceroDigitoVerificacion, terceroNITAlternativo, terceroCodigoSS, terceroCodigoSP, terceroCodigoSC, terceroTConsolidador, terceroNITConsolidado, terceroCiudad, terceroCodigoAlterno;
    //Columnas Tabla VP
    private Column terceroSucursalCodigo, terceroSucursalPatronal, terceroSucursalObservacion, terceroSucursalCiudad;
    //Otros
    private boolean aceptar;
    private int index;
    private List<Terceros> listTerceroModificar;
    private boolean guardado, guardarOk;
    public Terceros nuevoTercero;
    private List<Terceros> listTerceroCrear;
    private BigInteger l;
    private int k;
    private List<Terceros> listTerceroBorrar;
    private Terceros editarTercero;
    private TercerosSucursales editarTerceroSucursal;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    private Terceros duplicarTercero;
    private boolean permitirIndex, permitirIndexTS;
    //Variables Autompletar
    //private String motivoCambioSueldo, tiposEntidades, tiposSueldos, terceros;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexTS;
    private int cualCeldaTS, tipoListaTS;
    private String nombreXML;
    private String nombreTabla;
    private boolean retroactivo;
    private int valorTotal;
    private boolean cambiosTercero, cambiosTerceroSucursal;
    private BigInteger secRegistroTercero, secRegistroTerceroSucursal;
    private BigInteger backUpSecRegistroTercero, backUpSecRegistroTerceroSucursal;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int indexAux;
    private List<TercerosSucursales> listTerceroSucursalCrear, listTerceroSucursalModificar, listTerceroSucursalBorrar;
    private TercerosSucursales nuevoTerceroSucursal, duplicarTerceroSucursal;
    private String terceroConsolidador, ciudad, ciudadTS;
    private long nitConsolidado;

    /**
     * Creates a new instance of ControlTercero
     */
    public ControlTercero() {
        editarTerceroSucursal = new TercerosSucursales();
        indexAux = 0;
        listCiudades = null;
        listTerceroConsolidador = null;
        backUpEmpresaActual = new Empresas();
        empresaActual = new Empresas();
        listEmpresas = null;
        listTercerosSucursales = null;
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroTercero = null;
        secRegistroTerceroSucursal = null;
        backUpSecRegistroTercero = null;
        backUpSecRegistroTerceroSucursal = null;
        listTerceros = null;
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listTerceroBorrar = new ArrayList<Terceros>();
        //crear aficiones
        listTerceroCrear = new ArrayList<Terceros>();
        listTerceroSucursalBorrar = new ArrayList<TercerosSucursales>();
        listTerceroSucursalModificar = new ArrayList<TercerosSucursales>();
        listTerceroSucursalCrear = new ArrayList<TercerosSucursales>();
        k = 0;
        //modificar aficiones
        listTerceroModificar = new ArrayList<Terceros>();
        //editar
        editarTercero = new Terceros();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        tipoListaTS = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoTercero = new Terceros();
        nuevoTercero.setTerceroconsolidador(new Terceros());
        nuevoTercero.setCiudad(new Ciudades());
        index = -1;
        bandera = 0;
        banderaTS = 0;
        permitirIndex = true;
        permitirIndexTS = true;
        indexTS = -1;
        cualCeldaTS = -1;

        nombreTabla = ":formExportarTerceros:datosTercerosExportar";
        nombreXML = "TercerosXML";

        cambiosTerceroSucursal = false;

        retroactivo = true;
        duplicarTercero = new Terceros();
        cambiosTercero = false;
    }

    public void obtenerEmpresa() {
        index = -1;
        listTerceros = null;
        listTercerosSucursales = null;
        empresaActual = getEmpresaActual();
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarTercero(int indice) {
        if (tipoLista == 0) {
            if (!listTerceroCrear.contains(listTerceros.get(indice))) {
                if (listTerceroModificar.isEmpty()) {
                    listTerceroModificar.add(listTerceros.get(indice));
                } else if (!listTerceroModificar.contains(listTerceros.get(indice))) {
                    listTerceroModificar.add(listTerceros.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            listTerceros.get(indice).getTerceroconsolidador().setNit(nitConsolidado);
            index = -1;
            secRegistroTercero = null;
        } else {
            if (!listTerceroCrear.contains(filtrarListTercero.get(indice))) {
                if (listTerceroModificar.isEmpty()) {
                    listTerceroModificar.add(filtrarListTercero.get(indice));
                } else if (!listTerceroModificar.contains(filtrarListTercero.get(indice))) {
                    listTerceroModificar.add(filtrarListTercero.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistroTercero = null;
            filtrarListTercero.get(indice).getTerceroconsolidador().setNit(nitConsolidado);
        }
        cambiosTercero = true;
    }

    public void modificarTerceroAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TERCEROCONSOLIDADOR")) {
            if (tipoLista == 0) {
                listTerceros.get(indice).getTerceroconsolidador().setNombre(terceroConsolidador);
            } else {
                filtrarListTercero.get(indice).getTerceroconsolidador().setNombre(terceroConsolidador);
            }
            for (int i = 0; i < listTerceroConsolidador.size(); i++) {
                if (listTerceroConsolidador.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listTerceros.get(indice).setTerceroconsolidador(listTerceroConsolidador.get(indiceUnicoElemento));
                } else {
                    filtrarListTercero.get(indice).setTerceroconsolidador(listTerceroConsolidador.get(indiceUnicoElemento));
                }
                listTerceroConsolidador.clear();
                getListTerceroConsolidador();
            } else {
                permitirIndex = false;
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoLista == 0) {
                listTerceros.get(indice).getCiudad().setNombre(ciudad);
            } else {
                filtrarListTercero.get(indice).getCiudad().setNombre(ciudad);
            }
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listTerceros.get(indice).setCiudad(listCiudades.get(indiceUnicoElemento));
                } else {
                    filtrarListTercero.get(indice).setCiudad(listCiudades.get(indiceUnicoElemento));
                }
                listCiudades.clear();
                getListCiudades();
            } else {
                permitirIndex = false;
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listTerceroCrear.contains(listTerceros.get(indice))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(listTerceros.get(indice));
                    } else if (!listTerceroModificar.contains(listTerceros.get(indice))) {
                        listTerceroModificar.add(listTerceros.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroTercero = null;
            } else {
                if (!listTerceroCrear.contains(filtrarListTercero.get(indice))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(filtrarListTercero.get(indice));
                    } else if (!listTerceroModificar.contains(filtrarListTercero.get(indice))) {
                        listTerceroModificar.add(filtrarListTercero.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroTercero = null;
            }
            cambiosTercero = true;
        }
        context.update("form:datosTerceros");
    }

    public void modificarTerceroSucursal(int indice) {
        if (tipoListaTS == 0) {
            if (!listTerceroSucursalCrear.contains(listTercerosSucursales.get(indice))) {
                if (listTerceroSucursalModificar.isEmpty()) {
                    listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                } else if (!listTerceroSucursalModificar.contains(listTercerosSucursales.get(indice))) {
                    listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexTS = -1;
            secRegistroTerceroSucursal = null;
        } else {
            if (!listTerceroSucursalCrear.contains(filtrarListTercerosSucursales.get(indice))) {
                if (listTerceroSucursalModificar.isEmpty()) {
                    listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indice));
                } else if (!listTerceroSucursalModificar.contains(filtrarListTercerosSucursales.get(indice))) {
                    listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexTS = -1;
            secRegistroTerceroSucursal = null;
        }
        cambiosTerceroSucursal = true;
    }

    public void modificarTerceroSucursalAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoLista == 0) {
                listTercerosSucursales.get(indice).getCiudad().setNombre(ciudadTS);
            } else {
                filtrarListTercerosSucursales.get(indice).getCiudad().setNombre(ciudadTS);
            }
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listTercerosSucursales.get(indice).setCiudad(listCiudades.get(indiceUnicoElemento));
                } else {
                    filtrarListTercerosSucursales.get(indice).setCiudad(listCiudades.get(indiceUnicoElemento));
                }
                listCiudades.clear();
                getListCiudades();
            } else {
                permitirIndex = false;
                context.update("form:CiudadTSDialogo");
                context.execute("CiudadTSDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaTS == 0) {
                if (!listTerceroSucursalCrear.contains(listTercerosSucursales.get(indice))) {
                    if (listTerceroSucursalModificar.isEmpty()) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                    } else if (!listTerceroSucursalModificar.contains(listTercerosSucursales.get(indice))) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexTS = -1;
                secRegistroTerceroSucursal = null;
            } else {
                if (!listTerceroSucursalCrear.contains(filtrarListTercerosSucursales.get(indice))) {
                    if (listTerceroSucursalModificar.isEmpty()) {
                        listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indice));
                    } else if (!listTerceroSucursalModificar.contains(filtrarListTercerosSucursales.get(indice))) {
                        listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexTS = -1;
                secRegistroTerceroSucursal = null;
            }
            cambiosTerceroSucursal = true;
        }
        context.update("form:datosTercerosSucursales");
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
        if (cambiosTerceroSucursal == false) {
            if (banderaTS == 1) {
                terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 0;
                filtrarListTercerosSucursales = null;
                tipoListaTS = 0;
            }
            cualCelda = celda;
            index = indice;
            indexAux = indice;
            secRegistroTercero = listTerceros.get(index).getSecuencia();
            indexTS = -1;
            secRegistroTerceroSucursal = null;
            getListTercerosSucursales();
            RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");


            if (cualCelda == 7) {
                terceroConsolidador = listTerceros.get(index).getTerceroconsolidador().getNombre();
            }
            if (cualCelda == 8) {
                nitConsolidado = listTerceros.get(index).getTerceroconsolidador().getNit();
            }
            if (cualCelda == 9) {
                ciudad = listTerceros.get(index).getCiudad().getNombre();
            }
        }
        if (cambiosTerceroSucursal == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void cambiarIndiceTS(int indice, int celda) {
        if (permitirIndexTS == true) {
            cualCeldaTS = celda;
            indexTS = indice;
            index = -1;
            secRegistroTerceroSucursal = listTercerosSucursales.get(indice).getSecuencia();
            if (cualCeldaTS == 3) {
                ciudadTS = listTercerosSucursales.get(indexTS).getCiudad().getNombre();
            }
            if (bandera == 1) {
                terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }

        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (cambiosTercero == true) {
            guardarCambiosTercero();
        }
        if (cambiosTerceroSucursal == true) {
            guardarCambiosTerceroSucursales();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosTercero() {
        if (guardado == false) {
            if (!listTerceroBorrar.isEmpty()) {
                for (int i = 0; i < listTerceroBorrar.size(); i++) {
                    administrarTercero.borrarTercero(listTerceroBorrar.get(i));
                }
                listTerceroBorrar.clear();
            }
            if (!listTerceroCrear.isEmpty()) {
                for (int i = 0; i < listTerceroCrear.size(); i++) {
                    administrarTercero.crearTercero(listTerceroCrear.get(i));
                }
                listTerceroCrear.clear();
            }
            if (!listTerceroModificar.isEmpty()) {
                for (int i = 0; i < listTerceroModificar.size(); i++) {
                    administrarTercero.modificarTercero(listTerceroModificar.get(i));
                }
                listTerceroModificar.clear();
            }
            listTerceros = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTerceros");
            k = 0;
        }
        index = -1;
        secRegistroTercero = null;
        cambiosTercero = false;
    }

    public void guardarCambiosTerceroSucursales() {
        if (guardado == false) {
            if (!listTerceroSucursalBorrar.isEmpty()) {
                for (int i = 0; i < listTerceroSucursalBorrar.size(); i++) {
                    administrarTercero.borrarTerceroSucursales(listTerceroSucursalBorrar.get(i));
                }
                listTerceroSucursalBorrar.clear();
            }
            if (!listTerceroSucursalCrear.isEmpty()) {
                for (int i = 0; i < listTerceroSucursalCrear.size(); i++) {
                    administrarTercero.crearTerceroSucursales(listTerceroSucursalCrear.get(i));
                }
                listTerceroSucursalCrear.clear();
            }
            if (!listTerceroSucursalModificar.isEmpty()) {
                for (int i = 0; i < listTerceroSucursalModificar.size(); i++) {
                    administrarTercero.modificarTerceroSucursales(listTerceroSucursalModificar.get(i));
                }
                listTerceroSucursalModificar.clear();
            }
            listTercerosSucursales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTercerosSucursales");
            k = 0;
        }
        indexTS = -1;
        secRegistroTerceroSucursal = null;
        cambiosTerceroSucursal = false;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        System.out.println("Cancelo Modificacion");
        if (bandera == 1) {
            terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }

        if (banderaTS == 1) {
            terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
            terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
            banderaTS = 0;
            filtrarListTercerosSucursales = null;
            tipoListaTS = 0;
        }
        listTerceroBorrar.clear();
        listTerceroCrear.clear();
        listTerceroModificar.clear();
        listTerceroSucursalBorrar.clear();
        listTerceroSucursalCrear.clear();
        listTerceroSucursalModificar.clear();
        index = -1;
        indexTS = -1;
        indexAux = -1;
        secRegistroTercero = null;
        k = 0;
        listTerceros = null;
        listTercerosSucursales = null;
        cambiosTerceroSucursal = false;
        guardado = true;
        cambiosTercero = false;
        getListTerceros();
        getListTercerosSucursales();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTerceros");
        context.update("form:datosTercerosSucursales");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTercero = listTerceros.get(index);
            }
            if (tipoLista == 1) {
                editarTercero = filtrarListTercero.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarNombreTerceroD");
                context.execute("editarNombreTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNitTerceroD");
                context.execute("editarNitTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDigtoVTerceroD");
                context.execute("editarDigtoVTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarNITATerceroD");
                context.execute("editarNITATerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarCSSTerceroD");
                context.execute("editarCSSTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarCSPTerceroD");
                context.execute("editarCSPTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarCSCTerceroD");
                context.execute("editarCSCTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarTCTerceroD");
                context.execute("editarTCTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarNITTCTerceroD");
                context.execute("editarNITTCTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarCiudadTerceroD");
                context.execute("editarCiudadTerceroD.show()");
                cualCelda = -1;
            } else if (cualCelda == 10) {
                context.update("formularioDialogos:editarCodigoATerceroD");
                context.execute("editarCodigoATerceroD.show()");
                cualCelda = -1;
            }
        }
        if (indexTS >= 0) {
            if (tipoListaTS == 0) {
                editarTerceroSucursal = listTercerosSucursales.get(indexTS);
            }
            if (tipoListaTS == 1) {
                editarTerceroSucursal = filtrarListTercerosSucursales.get(indexTS);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaTS == 0) {
                context.update("formularioDialogos:editarSucursalTerceroSD");
                context.execute("editarSucursalTerceroSD.show()");
                cualCeldaTS = -1;
            } else if (cualCeldaTS == 1) {
                context.update("formularioDialogos:editarPatronalTerceroSD");
                context.execute("editarPatronalTerceroSD.show()");
                cualCeldaTS = -1;
            } else if (cualCeldaTS == 2) {
                context.update("formularioDialogos:editarObservacionesTerceroSD");
                context.execute("editarObservacionesTerceroSD.show()");
                cualCeldaTS = -1;
            } else if (cualCeldaTS == 3) {
                context.update("formularioDialogos:editarCiudadTerceroSD");
                context.execute("editarCiudadTerceroSD.show()");
                cualCeldaTS = -1;
            }
        }
        index = -1;
        secRegistroTercero = null;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
    }

    public void validarIngresoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cambiosTerceroSucursal == false) {
                if (listTercerosSucursales.isEmpty()) {
                    context.execute("NuevoRegistroPagina.show()");
                } else {
                    context.update("form:NuevoRegistroTercero");
                    context.execute("NuevoRegistroTercero.show()");
                }
            } else {
                context.execute("confirmarGuardar.show()");
            }
        }
        if (indexTS >= 0) {
            context.update("form:NuevoRegistroTerceroSucursal");
            context.execute("NuevoRegistroTerceroSucursal.show()");
        }
    }

    public void validarDuplicadoRegistro() {

        if (index >= 0) {
            duplicarTercero();
        }
        if (indexTS >= 0) {
            duplicarTerceroS();
        }
    }

    public void validarBorradoRegistro() {
        if (index >= 0) {
            if (listTercerosSucursales == null) {
                borrarTercero();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorBorradoTercero.show()");
            }
        }
        if (indexTS >= 0) {
            borrarTerceroSucursal();
        }
    }

    //CREAR VL
    /**
     * Metodo que se encarga de agregar un nueva VigenciasLocalizaciones
     */
    public void agregarNuevoTercero() {
        if (bandera == 1) {
            terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }
        if (banderaTS == 1) {
            terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
            terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
            banderaTS = 0;
            filtrarListTercerosSucursales = null;
            tipoListaTS = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
        k++;
        BigInteger var = BigInteger.valueOf(k);
        nuevoTercero.setSecuencia(var);
        nuevoTercero.setEmpresa(empresaActual);
        listTerceroCrear.add(nuevoTercero);
        listTerceros.add(nuevoTercero);
        ////------////
        nuevoTercero = new Terceros();
        nuevoTercero.setTerceroconsolidador(new Terceros());
        nuevoTercero.setCiudad(new Ciudades());
        ////-----////
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTerceros");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        context.execute("NuevoRegistroTercero.hide()");
        cambiosTercero = true;
        index = -1;
        secRegistroTercero = null;
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevoTercero() {
        nuevoTercero = new Terceros();
        nuevoTercero.setTerceroconsolidador(new Terceros());
        nuevoTercero.setCiudad(new Ciudades());
        index = -1;
        secRegistroTercero = null;
    }

    ////////--- //// ---////
    public void agregarNuevoTerceroSucursal() {
        if (bandera == 1) {
            terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }
        if (banderaTS == 1) {
            terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
            terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
            banderaTS = 0;
            filtrarListTercerosSucursales = null;
            tipoListaTS = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS 
        k++;
        BigInteger var = BigInteger.valueOf(k);
        nuevoTerceroSucursal.setSecuencia(var);
        nuevoTerceroSucursal.setTercero(listTerceros.get(indexAux));
        listTerceroSucursalCrear.add(nuevoTerceroSucursal);
        listTercerosSucursales.add(nuevoTerceroSucursal);
        ////------////
        nuevoTerceroSucursal = new TercerosSucursales();
        nuevoTerceroSucursal.setCiudad(new Ciudades());
        ////-----////
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTercerosSucursales");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        context.execute("DuplicarRegistroTerceroSucursal.hide()");
        cambiosTerceroSucursal = true;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevoTerceroSucursal() {
        nuevoTerceroSucursal = new TercerosSucursales();
        nuevoTerceroSucursal.setCiudad(new Ciudades());
        indexTS = -1;
        secRegistroTerceroSucursal = null;
    }
    //DUPLICAR VL

    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    /**
     * Duplica una nueva vigencia localizacion
     */
    public void duplicarTercero() {
        index = indexAux;
        if (index >= 0) {
            duplicarTercero = new Terceros();
            if (tipoLista == 0) {
                duplicarTercero.setNombre(listTerceros.get(index).getNombre());
                duplicarTercero.setNit(listTerceros.get(index).getNit());
                duplicarTercero.setDigitoverificacion(listTerceros.get(index).getDigitoverificacion());
                duplicarTercero.setNitalternativo(listTerceros.get(index).getNitalternativo());
                duplicarTercero.setCodigoss(listTerceros.get(index).getCodigoss());
                duplicarTercero.setCodigosp(listTerceros.get(index).getCodigosp());
                duplicarTercero.setCodigosc(listTerceros.get(index).getCodigosc());
                duplicarTercero.setTerceroconsolidador(listTerceros.get(index).getTerceroconsolidador());
                duplicarTercero.setCiudad(listTerceros.get(index).getCiudad());
                duplicarTercero.setCodigoalternativo(listTerceros.get(index).getCodigoalternativo());
                duplicarTercero.setTiponit(listTerceros.get(index).getTiponit());
                duplicarTercero.setCodigotercerosap(listTerceros.get(index).getCodigotercerosap());
            }
            if (tipoLista == 1) {
                duplicarTercero.setNombre(filtrarListTercero.get(index).getNombre());
                duplicarTercero.setNit(filtrarListTercero.get(index).getNit());
                duplicarTercero.setDigitoverificacion(filtrarListTercero.get(index).getDigitoverificacion());
                duplicarTercero.setNitalternativo(filtrarListTercero.get(index).getNitalternativo());
                duplicarTercero.setCodigoss(filtrarListTercero.get(index).getCodigoss());
                duplicarTercero.setCodigosp(filtrarListTercero.get(index).getCodigosp());
                duplicarTercero.setCodigosc(filtrarListTercero.get(index).getCodigosc());
                duplicarTercero.setTerceroconsolidador(filtrarListTercero.get(index).getTerceroconsolidador());
                duplicarTercero.setCiudad(filtrarListTercero.get(index).getCiudad());
                duplicarTercero.setCodigoalternativo(filtrarListTercero.get(index).getCodigoalternativo());
                duplicarTercero.setTiponit(filtrarListTercero.get(index).getTiponit());
                duplicarTercero.setCodigotercerosap(filtrarListTercero.get(index).getCodigotercerosap());
            }
            if (duplicarTercero.getTerceroconsolidador() == null) {
                duplicarTercero.setTerceroconsolidador(new Terceros());
            }
            if (duplicarTercero.getCiudad() == null) {
                duplicarTercero.setCiudad(new Ciudades());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroTercero");
            context.execute("DuplicarRegistroTercero.show()");
            index = -1;
            secRegistroTercero = null;
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
                terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 0;
                filtrarListTercerosSucursales = null;
                tipoListaTS = 0;
            }
            k++;
            BigInteger var = BigInteger.valueOf(k);
            duplicarTercero.setSecuencia(var);
            duplicarTercero.setEmpresa(empresaActual);
            listTerceroCrear.add(duplicarTercero);
            listTerceros.add(duplicarTercero);
            duplicarTercero = new Terceros();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTerceros");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("DuplicarRegistroTercero.hide()");
            cambiosTercero = true;
            index = -1;
            secRegistroTercero = null;
        }
    }

    public void limpiarDuplicarTercero() {
        duplicarTercero = new Terceros();
        duplicarTercero.setTerceroconsolidador(new Terceros());
        duplicarTercero.setCiudad(new Ciudades());
    }

    ////-- !! --- !! --///
    public void duplicarTerceroS() {
        if (indexTS >= 0) {
            duplicarTerceroSucursal = new TercerosSucursales();
            if (tipoListaTS == 0) {
                duplicarTerceroSucursal.setCodigosucursal(listTercerosSucursales.get(indexTS).getCodigosucursal());
                duplicarTerceroSucursal.setCodigopatronal(listTercerosSucursales.get(indexTS).getCodigopatronal());
                duplicarTerceroSucursal.setDescripcion(listTercerosSucursales.get(indexTS).getDescripcion());
                duplicarTerceroSucursal.setCiudad(listTercerosSucursales.get(indexTS).getCiudad());
                duplicarTerceroSucursal.setTercero(listTerceros.get(indexAux));

            }
            if (tipoListaTS == 1) {
                duplicarTerceroSucursal.setCodigosucursal(filtrarListTercerosSucursales.get(indexTS).getCodigosucursal());
                duplicarTerceroSucursal.setCodigopatronal(filtrarListTercerosSucursales.get(indexTS).getCodigopatronal());
                duplicarTerceroSucursal.setDescripcion(filtrarListTercerosSucursales.get(indexTS).getDescripcion());
                duplicarTerceroSucursal.setCiudad(filtrarListTercerosSucursales.get(indexTS).getCiudad());
                duplicarTerceroSucursal.setTercero(listTerceros.get(index));

            }
            if (duplicarTerceroSucursal.getCiudad() == null) {
                duplicarTerceroSucursal.setCiudad(new Ciudades());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroTerceroSucursal");
            context.execute("DuplicarRegistroTerceroSucursal.show()");
            indexTS = -1;
            secRegistroTercero = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasLocalizaciones
     */
    public void confirmarDuplicarTS() {
        if (indexTS >= 0) {
            if (bandera == 1) {
                terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 0;
                filtrarListTercerosSucursales = null;
                tipoListaTS = 0;
            }
            k++;
            BigInteger var = BigInteger.valueOf(k);
            duplicarTerceroSucursal.setSecuencia(var);
            duplicarTerceroSucursal.setTercero(listTerceros.get(indexAux));
            listTerceroSucursalCrear.add(duplicarTerceroSucursal);
            listTercerosSucursales.add(duplicarTerceroSucursal);
            duplicarTerceroSucursal = new TercerosSucursales();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTerceros");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("DuplicarRegistroTerceroSucursal.hide()");
            cambiosTerceroSucursal = true;
            indexTS = -1;
            secRegistroTerceroSucursal = null;
        }
    }

    public void limpiarDuplicarTerceroSucursal() {
        duplicarTerceroSucursal = new TercerosSucursales();
        duplicarTerceroSucursal.setCiudad(new Ciudades());
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarTercero() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listTerceroModificar.isEmpty() && listTerceroModificar.contains(listTerceros.get(index))) {
                    int modIndex = listTerceroModificar.indexOf(listTerceros.get(index));
                    listTerceroModificar.remove(modIndex);
                    listTerceroBorrar.add(listTerceros.get(index));
                } else if (!listTerceroCrear.isEmpty() && listTerceroCrear.contains(listTerceros.get(index))) {
                    int crearIndex = listTerceroCrear.indexOf(listTerceros.get(index));
                    listTerceroCrear.remove(crearIndex);
                } else {
                    listTerceroBorrar.add(listTerceros.get(index));
                }
                listTerceros.remove(index);
            }
            if (tipoLista == 1) {
                if (!listTerceroModificar.isEmpty() && listTerceroModificar.contains(filtrarListTercero.get(index))) {
                    int modIndex = listTerceroModificar.indexOf(filtrarListTercero.get(index));
                    listTerceroModificar.remove(modIndex);
                    listTerceroBorrar.add(filtrarListTercero.get(index));
                } else if (!listTerceroCrear.isEmpty() && listTerceroCrear.contains(filtrarListTercero.get(index))) {
                    int crearIndex = listTerceroCrear.indexOf(filtrarListTercero.get(index));
                    listTerceroCrear.remove(crearIndex);
                } else {
                    listTerceroBorrar.add(filtrarListTercero.get(index));
                }
                int VLIndex = listTerceros.indexOf(filtrarListTercero.get(index));
                listTerceros.remove(VLIndex);
                filtrarListTercero.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTerceros");

            index = -1;
            secRegistroTercero = null;
            cambiosTercero = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    ////--- !! --- !!! ///
    public void borrarTerceroSucursal() {
        if (indexTS >= 0) {
            if (tipoListaTS == 0) {
                if (!listTerceroSucursalModificar.isEmpty() && listTerceroSucursalModificar.contains(listTercerosSucursales.get(indexTS))) {
                    int modIndex = listTerceroSucursalModificar.indexOf(listTercerosSucursales.get(indexTS));
                    listTerceroSucursalModificar.remove(modIndex);
                    listTerceroSucursalBorrar.add(listTercerosSucursales.get(indexTS));
                } else if (!listTerceroSucursalCrear.isEmpty() && listTerceroSucursalCrear.contains(listTercerosSucursales.get(indexTS))) {
                    int crearIndex = listTerceroSucursalCrear.indexOf(listTercerosSucursales.get(indexTS));
                    listTerceroSucursalCrear.remove(crearIndex);
                } else {
                    listTerceroSucursalBorrar.add(listTercerosSucursales.get(indexTS));
                }
                listTercerosSucursales.remove(indexTS);
            }
            if (tipoListaTS == 1) {
                if (!listTerceroSucursalModificar.isEmpty() && listTerceroSucursalModificar.contains(filtrarListTercerosSucursales.get(indexTS))) {
                    int modIndex = listTerceroSucursalModificar.indexOf(filtrarListTercerosSucursales.get(indexTS));
                    listTerceroSucursalModificar.remove(modIndex);
                    listTerceroSucursalBorrar.add(filtrarListTercerosSucursales.get(indexTS));
                } else if (!listTerceroSucursalCrear.isEmpty() && listTerceroSucursalCrear.contains(filtrarListTercerosSucursales.get(indexTS))) {
                    int crearIndex = listTerceroSucursalCrear.indexOf(filtrarListTercerosSucursales.get(indexTS));
                    listTerceroSucursalCrear.remove(crearIndex);
                } else {
                    listTerceroSucursalBorrar.add(filtrarListTercerosSucursales.get(indexTS));
                }
                int VLIndex = listTerceros.indexOf(filtrarListTercerosSucursales.get(indexTS));
                listTerceros.remove(VLIndex);
                filtrarListTercerosSucursales.remove(indexTS);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTercerosSucursales");

            indexTS = -1;
            secRegistroTerceroSucursal = null;
            cambiosTerceroSucursal = true;
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
            filtradoTercero();
        }
        if (indexTS >= 0) {
            filtradoTerceroSucursal();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoTercero() {
        if (index >= 0) {
            if (bandera == 0) {
                terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("width: 80px");
                terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("width: 80px");
                terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("width: 80px");
                terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("width: 80px");
                terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("width: 80px");
                terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("width: 80px");
                terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("width: 80px");
                terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("width: 80px");
                terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("width: 80px");
                terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("width: 80px");
                terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 1;
            } else if (bandera == 1) {
                terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
        }
    }

    public void filtradoTerceroSucursal() {
        if (indexTS >= 0) {
            if (banderaTS == 0) {
                terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("width: 80px");
                terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("width: 80px");
                terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("width: 80px");
                terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 1;
            } else if (banderaTS == 1) {
                terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 0;
                filtrarListTercerosSucursales = null;
                tipoListaTS = 0;
            }
        }
    }
    //SALIR

    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            terceroNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }

        if (banderaTS == 1) {
            terceroSucursalCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
            terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
            banderaTS = 0;
            filtrarListTercerosSucursales = null;
            tipoListaTS = 0;
        }

        listTerceroBorrar.clear();
        listTerceroCrear.clear();
        listTerceroModificar.clear();
        index = -1;
        secRegistroTercero = null;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
        k = 0;
        listTerceros = null;
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
     * @param tt Tipo Tabla : VigenciaLocalizacion / VigenciaProrrateo /
     * VigenciaProrrateoProyecto
     */
    public void asignarIndex(Integer indice, int dlg, int LND, int tt) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tt == 0) {
            if (LND == 0) {
                index = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
            } else if (dlg == 1) {
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
            }
        }
        if (tt == 1) {
            if (LND == 0) {
                indexTS = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:CiudadTSDialogo");
                context.execute("CiudadTSDialogo.show()");
            }
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
            if (cualCelda == 7) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 9) {
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexTS >= 0) {
            if (cualCeldaTS == 3) {
                context.update("form:CiudadTSDialogo");
                context.execute("CiudadTSDialogo.show()");
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
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo, int tipoLista) {
        if (tipoLista == 0) {
            if (Campo.equals("TERCEROCONSOLIDADOR")) {
                if (tipoNuevo == 1) {
                    terceroConsolidador = nuevoTercero.getTerceroconsolidador().getNombre();
                } else if (tipoNuevo == 2) {
                    terceroConsolidador = duplicarTercero.getTerceroconsolidador().getNombre();
                }
            } else if (Campo.equals("CIUDAD")) {
                if (tipoNuevo == 1) {
                    ciudad = nuevoTercero.getCiudad().getNombre();
                } else if (tipoNuevo == 2) {
                    ciudad = duplicarTercero.getCiudad().getNombre();
                }
            }
        }
        if (tipoLista == 1) {
            if (Campo.equals("CIUDAD")) {
                if (tipoNuevo == 1) {
                    ciudadTS = nuevoTerceroSucursal.getCiudad().getNombre();
                } else if (tipoNuevo == 2) {
                    ciudadTS = duplicarTerceroSucursal.getCiudad().getNombre();
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoTercero(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TERCEROCONSOLIDADOR")) {
            if (tipoNuevo == 1) {
                nuevoTercero.getTerceroconsolidador().setNombre(terceroConsolidador);
            } else if (tipoNuevo == 2) {
                duplicarTercero.getTerceroconsolidador().setNombre(terceroConsolidador);
            }
            for (int i = 0; i < listTerceroConsolidador.size(); i++) {
                if (listTerceroConsolidador.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTercero.setTerceroconsolidador(listTerceroConsolidador.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTerceroCT");
                } else if (tipoNuevo == 2) {
                    duplicarTercero.setTerceroconsolidador(listTerceroConsolidador.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTerceroCT");
                }
                listTerceroConsolidador.clear();
                getListTerceroConsolidador();
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
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoNuevo == 1) {
                nuevoTercero.getCiudad().setNombre(ciudad);
            } else if (tipoNuevo == 2) {
                duplicarTercero.getCiudad().setNombre(ciudad);
            }
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTercero.setCiudad(listCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCodigoAT");
                } else if (tipoNuevo == 2) {
                    duplicarTercero.setCiudad(listCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCodigoAT");
                }
                listCiudades.clear();
                getListCiudades();
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

    public void autocompletarNuevoyDuplicadoTerceroSucursal(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (tipoNuevo == 1) {
                nuevoTerceroSucursal.getCiudad().setNombre(ciudadTS);
            } else if (tipoNuevo == 2) {
                duplicarTerceroSucursal.getCiudad().setNombre(ciudadTS);
            }
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTerceroSucursal.setCiudad(listCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaCiudadTS");
                } else if (tipoNuevo == 2) {
                    duplicarTerceroSucursal.setCiudad(listCiudades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCiudadTS");
                }
                listCiudades.clear();
                getListCiudades();
            } else {
                context.update("form:CiudadTSDialogo");
                context.execute("CiudadTSDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaCiudadTS");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCiudadTS");
                }
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarTerceros() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listTerceros.get(index).setTerceroconsolidador(terceroCSeleccionado);
                if (!listTerceroCrear.contains(listTerceros.get(index))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(listTerceros.get(index));
                    } else if (!listTerceroModificar.contains(listTerceros.get(index))) {
                        listTerceroModificar.add(listTerceros.get(index));
                    }
                }
            } else {
                filtrarListTercero.get(index).setTerceroconsolidador(terceroCSeleccionado);
                if (!listTerceroCrear.contains(filtrarListTercero.get(index))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(filtrarListTercero.get(index));
                    } else if (!listTerceroModificar.contains(filtrarListTercero.get(index))) {
                        listTerceroModificar.add(filtrarListTercero.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosTercero = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTConsolidador");
            context.update(":form:editarTerceroNITConsolidado");
        } else if (tipoActualizacion == 1) {
            nuevoTercero.setTerceroconsolidador(terceroCSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTerceroCT");
            context.update("formularioDialogos:nuevaTerceroCNITT");
        } else if (tipoActualizacion == 2) {
            duplicarTercero.setTerceroconsolidador(terceroCSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTerceroCT");
            context.update("formularioDialogos:duplicarTTerceroCNITT");
        }
        filtrarListTercero = null;
        terceroCSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioTerceros() {
        filtrarListTercero = null;
        terceroCSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarCiudad() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listTerceros.get(index).setCiudad(ciudadSeleccionada);
                if (!listTerceroCrear.contains(listTerceros.get(index))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(listTerceros.get(index));
                    } else if (!listTerceroModificar.contains(listTerceros.get(index))) {
                        listTerceroModificar.add(listTerceros.get(index));
                    }
                }
            } else {
                filtrarListTercero.get(index).setCiudad(ciudadSeleccionada);
                if (!listTerceroCrear.contains(filtrarListTercero.get(index))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(filtrarListTercero.get(index));
                    } else if (!listTerceroModificar.contains(filtrarListTercero.get(index))) {
                        listTerceroModificar.add(filtrarListTercero.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosTercero = true;
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTerceroCiudad");
        } else if (tipoActualizacion == 1) {
            nuevoTercero.setCiudad(ciudadSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCiudadT");
        } else if (tipoActualizacion == 2) {
            duplicarTercero.setCiudad(ciudadSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTCiudadT");
        }
        filtrarListCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCiudad() {
        filtrarListCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarCiudadTS() {
        if (tipoActualizacion == 0) {
            if (tipoListaTS == 0) {
                listTercerosSucursales.get(indexTS).setCiudad(ciudadSeleccionada);
                if (!listTerceroSucursalCrear.contains(listTercerosSucursales.get(indexTS))) {
                    if (listTerceroSucursalModificar.isEmpty()) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indexTS));
                    } else if (!listTerceroSucursalModificar.contains(listTercerosSucursales.get(indexTS))) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indexTS));
                    }
                }
            } else {
                filtrarListTercerosSucursales.get(indexTS).setCiudad(ciudadSeleccionada);
                if (!listTerceroSucursalCrear.contains(filtrarListTercerosSucursales.get(indexTS))) {
                    if (listTerceroSucursalModificar.isEmpty()) {
                        listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indexTS));
                    } else if (!listTerceroSucursalModificar.contains(filtrarListTercerosSucursales.get(indexTS))) {
                        listTerceroSucursalModificar.add(filtrarListTercerosSucursales.get(indexTS));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosTerceroSucursal = true;
            permitirIndexTS = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTerceroSucursalCiudad");
        } else if (tipoActualizacion == 1) {
            nuevoTerceroSucursal.setCiudad(ciudadSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaCiudadTS");
        } else if (tipoActualizacion == 2) {
            duplicarTerceroSucursal.setCiudad(ciudadSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarCiudadTS");
        }
        filtrarListCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioCiudadTS() {
        filtrarListCiudades = null;
        ciudadSeleccionada = null;
        aceptar = true;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
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
            nombreTabla = ":formExportarTerceros:datosTercerosExportar";
            nombreXML = "TercerosXML";
        }
        if (indexTS >= 0) {
            nombreTabla = ":formExportarTercerosSucursales:datosTercerosSucursalesExportar";
            nombreXML = "TercerosSucursalesXML";
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
        if (indexTS >= 0) {
            exportPDFTS();
        }
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFT() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTerceros:datosTercerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TercerosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroTercero = null;
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFTS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTercerosSucursales:datosTercerosSucursalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TercerosSucursalesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexTS = -1;
        secRegistroTerceroSucursal = null;
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
        if (indexTS >= 0) {
            exportXLSTS();
        }
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLST() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTerceros:datosTercerosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TercerosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistroTercero = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSTS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTercerosSucursales:datosTercerosSucursalesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TercerosSucursalesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexTS = -1;
        secRegistroTerceroSucursal = null;
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
        if (indexTS >= 0) {
            if (tipoListaTS == 0) {
                tipoListaTS = 1;
            }
        }
    }

    public void dialogoSeleccionarTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:BuscarTerceroDialogo");
        context.execute("BuscarTerceroDialogo.show()");
    }

    public void cancelarSeleccionTercero() {
        terceroCSeleccionado = new Terceros();
        filtrarListTerceroConsolidador = null;
    }

    public void validarSeleccionTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosTercero == false && cambiosTerceroSucursal == false) {
            listTerceros = null;
            listTerceros = new ArrayList<Terceros>();
            if (terceroCSeleccionado.getCiudad() == null) {
                terceroCSeleccionado.setCiudad(new Ciudades());
            }
            if (terceroCSeleccionado.getTerceroconsolidador() == null) {
                terceroCSeleccionado.setTerceroconsolidador(new Terceros());
            }
            listTerceros.add(terceroCSeleccionado);
            terceroCSeleccionado = new Terceros();
            filtrarListTerceroConsolidador = null;
            context.update("form:datosTerceros");
            getListTercerosSucursales();
            context.update("form:datosTercerosSucursales");
        } else {
            terceroCSeleccionado = new Terceros();
            filtrarListTerceroConsolidador = null;
            context.execute("confirmarGuardar.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosTercero == false && cambiosTerceroSucursal == false) {
            listTerceros = null;
            getListTerceros();
            context.update("form:datosTerceros");
            getListTercerosSucursales();
            context.update("form:datosTercerosSucursales");
        } else {
            context.execute("confirmarGuardar.show()");
        }
    }

    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS
    public void verificarRastroTabla() {
        if (listTercerosSucursales.isEmpty() || listTerceros.isEmpty()) {
            //Dialogo para seleccionar el rato de la tabla deseada
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        } else {
            if ((!listTercerosSucursales.isEmpty()) && (!listTerceros.isEmpty())) {
                if (index >= 0) {
                    verificarRastroTerceros();
                    index = -1;
                }
                if (indexTS >= 0) {
                    verificarRastroTercerosSucursales();
                    indexTS = -1;
                }
            }
        }
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listTerceros != null) {
            if (secRegistroTercero != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroTercero, "TERCEROS");
                backUpSecRegistroTercero = secRegistroTercero;
                backUp = secRegistroTercero;
                secRegistroTercero = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Terceros";
                    msnConfirmarRastro = "La tabla TERCEROS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("TERCEROS")) {
                nombreTablaRastro = "Terceros";
                msnConfirmarRastroHistorico = "La tabla TERCEROS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroTercerosSucursales() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listTercerosSucursales.isEmpty()) {
            if (secRegistroTerceroSucursal == null) {
                context.execute("seleccionarRegistro.show()");
            } else {
                int resultado = administrarRastros.obtenerTabla(secRegistroTerceroSucursal, "TERCEROSSUCURSALES");
                backUpSecRegistroTerceroSucursal = secRegistroTerceroSucursal;
                backUp = secRegistroTerceroSucursal;
                secRegistroTerceroSucursal = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "TercerosSucursales";
                    msnConfirmarRastro = "La tabla TERCEROSSUCURSALES tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
                    context.execute("confirmarRastro.show()");
                } else if (resultado == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (resultado == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (resultado == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("TERCEROSSUCURSALES")) {
                nombreTablaRastro = "TercerosSucursales";
                msnConfirmarRastroHistorico = "La tabla TERCEROSSUCURSALES tiene rastros historicos, ¿Desea continuar?";
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
        secRegistroTercero = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosTercero == false && cambiosTerceroSucursal == false) {
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            filtrarListEmpresas = null;
            aceptar = true;
            context.execute("EmpresasDialogo.hide()");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("formularioDialogos:lovEmpresas");
            context.update("formularioDialogos:aceptarE");
            backUpEmpresaActual = empresaActual;
            listTerceros = null;
            listTercerosSucursales = null;
            getListTerceros();
            getListTercerosSucursales();
            context.update("form:datosTerceros");
            context.update("form:datosTercerosSucursales");
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
    public List<Terceros> getListTerceros() {
        try {
            if (listTerceros == null) {
                listTerceros = null;
                listTerceros = administrarTercero.obtenerListTerceros(empresaActual.getSecuencia());
                if (listTerceros != null) {
                    for (int i = 0; i < listTerceros.size(); i++) {
                        if (listTerceros.get(i).getCiudad() == null) {
                            listTerceros.get(i).setCiudad(new Ciudades());
                        }
                        if (listTerceros.get(i).getTerceroconsolidador() == null) {
                            listTerceros.get(i).setTerceroconsolidador(new Terceros());
                        }
                    }
                }
            }
            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error getListTerceros " + e.toString());
            return null;
        }
    }

    public void setListTerceros(List<Terceros> t) {
        this.listTerceros = t;
    }

    public List<Terceros> getFiltrarListTercero() {
        return filtrarListTercero;
    }

    public void setFiltrarListTercero(List<Terceros> t) {
        this.filtrarListTercero = t;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
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

    public BigInteger getSecRegistroVS() {
        return secRegistroTercero;
    }

    public void setSecRegistroVS(BigInteger secRegistroVS) {
        this.secRegistroTercero = secRegistroVS;
    }

    public BigInteger getBackUpSecRegistroTercero() {
        return backUpSecRegistroTercero;
    }

    public void setBackUpSecRegistroTercero(BigInteger b) {
        this.backUpSecRegistroTercero = b;
    }

    public List<Terceros> getListTerceroModificar() {
        return listTerceroModificar;
    }

    public void setListTerceroModificar(List<Terceros> listTerceroModificar) {
        this.listTerceroModificar = listTerceroModificar;
    }

    public Terceros getNuevoTercero() {
        return nuevoTercero;
    }

    public void setNuevoTercero(Terceros nuevoTercero) {
        this.nuevoTercero = nuevoTercero;
    }

    public List<Terceros> getListTerceroCrear() {
        return listTerceroCrear;
    }

    public void setListTerceroCrear(List<Terceros> listTerceroCrear) {
        this.listTerceroCrear = listTerceroCrear;
    }

    public List<Terceros> getListTerceroBorrar() {
        return listTerceroBorrar;
    }

    public void setListTerceroBorrar(List<Terceros> listTerceroBorrar) {
        this.listTerceroBorrar = listTerceroBorrar;
    }

    public Terceros getEditarTercero() {
        return editarTercero;
    }

    public void setEditarTercero(Terceros editarTercero) {
        this.editarTercero = editarTercero;
    }

    public Terceros getDuplicarTecero() {
        return duplicarTercero;
    }

    public void setDuplicarTecero(Terceros duplicarTecero) {
        this.duplicarTercero = duplicarTecero;
    }

    public BigInteger getSecRegistroTercero() {
        return secRegistroTercero;
    }

    public void setSecRegistroTercero(BigInteger secRegistroTercero) {
        this.secRegistroTercero = secRegistroTercero;
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

    public List<TercerosSucursales> getListTercerosSucursales() {
        try {
            if (index >= 0) {
                listTercerosSucursales = null;
                listTercerosSucursales = administrarTercero.obtenerListTercerosSucursales(listTerceros.get(index).getSecuencia());
                if (!listTercerosSucursales.isEmpty()) {
                    for (int i = 0; i < listTercerosSucursales.size(); i++) {
                        if (listTercerosSucursales.get(i).getCiudad() == null) {
                            listTercerosSucursales.get(i).setCiudad(new Ciudades());
                        }
                    }
                }
            }
            return listTercerosSucursales;
        } catch (Exception e) {
            System.out.println("Error en getListTercerosSucursales : " + e.toString());
            return null;
        }
    }

    public void setListTercerosSucursales(List<TercerosSucursales> listTercerosSucursales) {
        this.listTercerosSucursales = listTercerosSucursales;
    }

    public List<TercerosSucursales> getFiltrarListTercerosSucursales() {
        return filtrarListTercerosSucursales;
    }

    public void setFiltrarListTercerosSucursales(List<TercerosSucursales> filtrarListTercerosSucursales) {
        this.filtrarListTercerosSucursales = filtrarListTercerosSucursales;
    }

    public List<Empresas> getListEmpresas() {
        if (listEmpresas == null) {
            listEmpresas = administrarTercero.listEmpresas();
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

    public List<Ciudades> getListCiudades() {
        if (listCiudades == null) {
            listCiudades = administrarTercero.listCiudades();
        }
        return listCiudades;
    }

    public void setListCiudades(List<Ciudades> listCiudades) {
        this.listCiudades = listCiudades;
    }

    public List<Ciudades> getFiltrarListCiudades() {
        return filtrarListCiudades;
    }

    public void setFiltrarListCiudades(List<Ciudades> filtrarListCiudades) {
        this.filtrarListCiudades = filtrarListCiudades;
    }

    public Ciudades getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudades ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public List<Terceros> getListTerceroConsolidador() {
        if (listTerceroConsolidador == null) {
            listTerceroConsolidador = administrarTercero.obtenerListTerceros(empresaActual.getSecuencia());
        }
        return listTerceroConsolidador;
    }

    public void setListTerceroConsolidador(List<Terceros> listTerceroConsolidador) {
        this.listTerceroConsolidador = listTerceroConsolidador;
    }

    public List<Terceros> getFiltrarListTerceroConsolidador() {
        return filtrarListTerceroConsolidador;
    }

    public void setFiltrarListTerceroConsolidador(List<Terceros> filtrarListTerceroConsolidador) {
        this.filtrarListTerceroConsolidador = filtrarListTerceroConsolidador;
    }

    public Terceros getTerceroCSeleccionado() {
        return terceroCSeleccionado;
    }

    public void setTerceroCSeleccionado(Terceros terceroCSeleccionado) {
        this.terceroCSeleccionado = terceroCSeleccionado;
    }

    public TercerosSucursales getEditarTerceroSucursal() {
        return editarTerceroSucursal;
    }

    public void setEditarTerceroSucursal(TercerosSucursales editarTerceroSucursal) {
        this.editarTerceroSucursal = editarTerceroSucursal;
    }

    public long getNitConsolidado() {
        return nitConsolidado;
    }

    public void setNitConsolidado(long nitConsolidado) {
        this.nitConsolidado = nitConsolidado;
    }

    public BigInteger getSecRegistroTerceroSucursal() {
        return secRegistroTerceroSucursal;
    }

    public void setSecRegistroTerceroSucursal(BigInteger secRegistroTerceroSucursal) {
        this.secRegistroTerceroSucursal = secRegistroTerceroSucursal;
    }

    public BigInteger getBackUpSecRegistroTerceroSucursal() {
        return backUpSecRegistroTerceroSucursal;
    }

    public void setBackUpSecRegistroTerceroSucursal(BigInteger backUpSecRegistroTerceroSucursal) {
        this.backUpSecRegistroTerceroSucursal = backUpSecRegistroTerceroSucursal;
    }

    public TercerosSucursales getNuevoTerceroSucursal() {
        return nuevoTerceroSucursal;
    }

    public void setNuevoTerceroSucursal(TercerosSucursales nuevoTerceroSucursal) {
        this.nuevoTerceroSucursal = nuevoTerceroSucursal;
    }

    public TercerosSucursales getDuplicarTerceroSucursal() {
        return duplicarTerceroSucursal;
    }

    public void setDuplicarTerceroSucursal(TercerosSucursales duplicarTerceroSucursal) {
        this.duplicarTerceroSucursal = duplicarTerceroSucursal;
    }
}

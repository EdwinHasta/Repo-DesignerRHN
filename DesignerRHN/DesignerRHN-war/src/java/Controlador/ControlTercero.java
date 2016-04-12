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
public class ControlTercero implements Serializable {

    @EJB
    AdministrarTerceroInterface administrarTercero;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Terceros> listTerceros;
    private List<Terceros> filtrarListTercero;
    private List<Terceros> listTercerosLOV;
    private List<Terceros> filtrarListTerceroLOV;
    private Terceros terceroTablaSeleccionado;
    private Terceros terceroLOVSeleccionado;
    //
    private List<TercerosSucursales> listTercerosSucursales;
    private List<TercerosSucursales> filtrarListTercerosSucursales;
    private TercerosSucursales terceroSucursalTablaSeleccionado;
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
    private boolean guardado;
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
    private String paginaAnterior;
    //
    private String infoRegistroEmpresa, infoRegistroCiudad2, infoRegistroCiudad1, infoRegistroTercero, infoRegistroTerceroConsolidador;
    //
    private String altoTablaTercero, altoTablaSucursal;
    //
    private String auxNombreTercero;
    private long auxNitTercero;
    //
    private BigInteger auxCodigoSucursal;

    /**
     * Creates a new instance of ControlTercero
     */
    public ControlTercero() {
        altoTablaTercero = "150";
        altoTablaSucursal = "90";
        terceroLOVSeleccionado = new Terceros();
        listTercerosLOV = null;
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
        nuevoTerceroSucursal = new TercerosSucursales();
        nuevoTerceroSucursal.setCiudad(new Ciudades());
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
        duplicarTercero = new Terceros();
        cambiosTercero = false;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTercero.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    /*
     * public void recibirPagina(String pagina) { paginaAnterior = pagina;
    }*/
        public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
        index = -1;
        empresaActual = getEmpresaActual();
        listTerceros = null;
        getListTerceros();
    }

    public String redirigirPaginaAnterior() {
        return paginaAnterior;
    }

    /**
     * Modifica los elementos de la tabla VigenciaLocalizacion que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarTercero(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTercero(0) == true) {
            if (tipoLista == 0) {
                if (!listTerceroCrear.contains(listTerceros.get(indice))) {
                    if (listTerceroModificar.isEmpty()) {
                        listTerceroModificar.add(listTerceros.get(indice));
                    } else if (!listTerceroModificar.contains(listTerceros.get(indice))) {
                        listTerceroModificar.add(listTerceros.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistroTercero = null;
                filtrarListTercero.get(indice).getTerceroconsolidador().setNit(nitConsolidado);
            }
            cambiosTercero = true;
        } else {
            if (tipoLista == 0) {
                listTerceros.get(indice).setNit(auxNitTercero);
                listTerceros.get(indice).setNombre(auxNombreTercero);
            } else {
                filtrarListTercero.get(indice).setNit(auxNitTercero);
                filtrarListTercero.get(indice).setNombre(auxNombreTercero);
            }
            context.execute("errorDatosNullTerceros.show()");
        }
        context.update("form:datosTerceros");
    }

    public void modificarTerceroAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TERCEROCONSOLIDADOR")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                coincidencias = 1;
                if (tipoLista == 0) {
                    listTerceros.get(indice).setTerceroconsolidador(new Terceros());
                } else {
                    filtrarListTercero.get(indice).setTerceroconsolidador(new Terceros());
                }
                listTerceroConsolidador.clear();
                getListTerceroConsolidador();
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                coincidencias = 1;
                if (tipoLista == 0) {
                    listTerceros.get(indice).setCiudad(new Ciudades());
                } else {
                    filtrarListTercero.get(indice).setCiudad(new Ciudades());
                }
                listCiudades.clear();
                getListCiudades();
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTerceroSucursal(0) == true) {
            if (tipoListaTS == 0) {
                if (!listTerceroSucursalCrear.contains(listTercerosSucursales.get(indice))) {
                    if (listTerceroSucursalModificar.isEmpty()) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                    } else if (!listTerceroSucursalModificar.contains(listTercerosSucursales.get(indice))) {
                        listTerceroSucursalModificar.add(listTercerosSucursales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                }
                indexTS = -1;
                secRegistroTerceroSucursal = null;
            }
            cambiosTerceroSucursal = true;
        } else {
            if (tipoListaTS == 0) {
                listTercerosSucursales.get(indice).setCodigosucursal(auxCodigoSucursal);
            } else {
                filtrarListTercerosSucursales.get(indice).setCodigosucursal(auxCodigoSucursal);
            }
            context.execute("errorDatosNullSucursal.show()");
        }
        context.update("form:datosTercerosSucursales");
    }

    public void modificarTerceroSucursalAutocompletar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                coincidencias = 1;
                if (tipoLista == 0) {
                    listTercerosSucursales.get(indice).setCiudad(new Ciudades());
                } else {
                    filtrarListTercerosSucursales.get(indice).setCiudad(new Ciudades());
                }
                listCiudades.clear();
                getListCiudades();
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
            if (permitirIndex == true) {
                if (banderaTS == 1) {
                    FacesContext c = FacesContext.getCurrentInstance();
                    altoTablaSucursal = "90";
                    terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                    terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                    terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                    terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                    terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                    terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                    terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                    terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                    banderaTS = 0;
                    filtrarListTercerosSucursales = null;
                    tipoListaTS = 0;
                }
                cualCelda = celda;
                index = indice;
                indexAux = indice;
                if (tipoLista == 0) {
                    secRegistroTercero = listTerceros.get(index).getSecuencia();
                    auxNitTercero = listTerceros.get(index).getNit();
                    auxNombreTercero = listTerceros.get(index).getNombre();
                    if (cualCelda == 7) {
                        terceroConsolidador = listTerceros.get(index).getTerceroconsolidador().getNombre();
                    }
                    if (cualCelda == 8) {
                        nitConsolidado = listTerceros.get(index).getTerceroconsolidador().getNit();
                    }
                    if (cualCelda == 9) {
                        ciudad = listTerceros.get(index).getCiudad().getNombre();
                    }
                } else {
                    secRegistroTercero = filtrarListTercero.get(index).getSecuencia();
                    auxNitTercero = filtrarListTercero.get(index).getNit();
                    auxNombreTercero = filtrarListTercero.get(index).getNombre();
                    if (cualCelda == 7) {
                        terceroConsolidador = filtrarListTercero.get(index).getTerceroconsolidador().getNombre();
                    }
                    if (cualCelda == 8) {
                        nitConsolidado = filtrarListTercero.get(index).getTerceroconsolidador().getNit();
                    }
                    if (cualCelda == 9) {
                        ciudad = filtrarListTercero.get(index).getCiudad().getNombre();
                    }
                }
                indexTS = -1;
                secRegistroTerceroSucursal = null;
                listTercerosSucursales = null;
                getListTercerosSucursales();
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
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
            if (tipoListaTS == 0) {
                auxCodigoSucursal = listTercerosSucursales.get(indice).getCodigosucursal();
                secRegistroTerceroSucursal = listTercerosSucursales.get(indice).getSecuencia();
                if (cualCeldaTS == 3) {
                    ciudadTS = listTercerosSucursales.get(indexTS).getCiudad().getNombre();
                }
            } else {
                secRegistroTerceroSucursal = filtrarListTercerosSucursales.get(indice).getSecuencia();
                auxCodigoSucursal = filtrarListTercerosSucursales.get(indice).getCodigosucursal();
                if (cualCeldaTS == 3) {
                    ciudadTS = filtrarListTercerosSucursales.get(indexTS).getCiudad().getNombre();
                }
            }
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (guardado == false) {
            if (cambiosTercero == true) {
                guardarCambiosTercero();
            }
            if (cambiosTerceroSucursal == true) {
                guardarCambiosTerceroSucursales();
            }
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
    }

    public void guardarCambiosTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listTerceroBorrar.isEmpty()) {
                    for (int i = 0; i < listTerceroBorrar.size(); i++) {
                        if (listTerceroBorrar.get(i).getTerceroconsolidador().getSecuencia() == null) {
                            listTerceroBorrar.get(i).setTerceroconsolidador(null);
                        }
                        if (listTerceroBorrar.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroBorrar.get(i).setCiudad(null);
                        }
                        administrarTercero.borrarTercero(listTerceroBorrar.get(i));
                    }
                    listTerceroBorrar.clear();
                }
                if (!listTerceroCrear.isEmpty()) {
                    for (int i = 0; i < listTerceroCrear.size(); i++) {
                        if (listTerceroCrear.get(i).getTerceroconsolidador().getSecuencia() == null) {
                            listTerceroCrear.get(i).setTerceroconsolidador(null);
                        }
                        if (listTerceroCrear.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroCrear.get(i).setCiudad(null);
                        }
                        administrarTercero.crearTercero(listTerceroCrear.get(i));
                    }
                    listTerceroCrear.clear();
                }
                if (!listTerceroModificar.isEmpty()) {
                    for (int i = 0; i < listTerceroModificar.size(); i++) {
                        if (listTerceroModificar.get(i).getTerceroconsolidador().getSecuencia() == null) {
                            listTerceroModificar.get(i).setTerceroconsolidador(null);
                        }
                        if (listTerceroModificar.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroModificar.get(i).setCiudad(null);
                        }
                        administrarTercero.modificarTercero(listTerceroModificar.get(i));
                    }
                    listTerceroModificar.clear();
                }
                listTerceros = null;

                context.update("form:datosTerceros");
                k = 0;
                index = -1;
                secRegistroTercero = null;
                cambiosTercero = false;
                FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos de Tercero con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambiosTercero Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Tercero, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void guardarCambiosTerceroSucursales() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!listTerceroSucursalBorrar.isEmpty()) {
                    for (int i = 0; i < listTerceroSucursalBorrar.size(); i++) {
                        if (listTerceroSucursalBorrar.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroSucursalBorrar.get(i).setCiudad(null);
                        }
                        administrarTercero.borrarTerceroSucursales(listTerceroSucursalBorrar.get(i));
                    }
                    listTerceroSucursalBorrar.clear();
                }
                if (!listTerceroSucursalCrear.isEmpty()) {
                    for (int i = 0; i < listTerceroSucursalCrear.size(); i++) {
                        if (listTerceroSucursalCrear.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroSucursalCrear.get(i).setCiudad(null);
                        }
                        administrarTercero.crearTerceroSucursales(listTerceroSucursalCrear.get(i));
                    }
                    listTerceroSucursalCrear.clear();
                }
                if (!listTerceroSucursalModificar.isEmpty()) {
                    for (int i = 0; i < listTerceroSucursalModificar.size(); i++) {
                        if (listTerceroSucursalModificar.get(i).getCiudad().getSecuencia() == null) {
                            listTerceroSucursalModificar.get(i).setCiudad(null);
                        }
                        administrarTercero.modificarTerceroSucursales(listTerceroSucursalModificar.get(i));
                    }
                    listTerceroSucursalModificar.clear();
                }
                listTercerosSucursales = null;
                getListTercerosSucursales();
                context.update("form:datosTercerosSucursales");
                k = 0;
                indexTS = -1;
                secRegistroTerceroSucursal = null;
                cambiosTerceroSucursal = false;
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos de Tercero Sucursal con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarCambiosTercero Controlador : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Ha ocurrido un error en el guardado de Tercero Sucursal, intente nuevamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaTercero = "150";
            terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }

        if (banderaTS == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaSucursal = "90";
            terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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

    public boolean validarCamposNulosTercero(int i) {
        boolean retorno = true;
        if (i == 0) {
            Terceros aux = null;
            if (tipoLista == 0) {
                aux = listTerceros.get(index);
            } else {
                aux = filtrarListTercero.get(index);
            }
            if (aux.getNit() < 0) {
                retorno = false;
            }
            if (aux.getNombre() == null) {
                retorno = false;
            } else {
                if (aux.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevoTercero.getNit() < 0) {
                retorno = false;
            }
            if (nuevoTercero.getNombre() == null) {
                retorno = false;
            } else {
                if (nuevoTercero.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarTercero.getNit() < 0) {
                retorno = false;
            }
            if (duplicarTercero.getNombre() == null) {
                retorno = false;
            } else {
                if (duplicarTercero.getNombre().isEmpty()) {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public boolean validarCamposNulosTerceroSucursal(int i) {
        boolean retorno = true;
        if (i == 0) {
            TercerosSucursales aux = null;
            if (tipoListaTS == 0) {
                aux = listTercerosSucursales.get(indexTS);
            } else {
                aux = filtrarListTercerosSucursales.get(indexTS);
            }
            if (aux.getCodigosucursal() == null) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoTerceroSucursal.getCodigosucursal() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarTerceroSucursal.getCodigosucursal() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void agregarNuevoTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTercero(1) == true) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaTercero = "150";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaSucursal = "90";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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
            if (listTerceros == null) {
                listTerceros = new ArrayList<Terceros>();
            }
            listTerceros.add(nuevoTercero);
            ////------////
            nuevoTercero = new Terceros();
            nuevoTercero.setTerceroconsolidador(new Terceros());
            nuevoTercero.setCiudad(new Ciudades());
            ////-----////

            context.update("form:datosTerceros");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevoRegistroTercero.hide()");
            cambiosTercero = true;
            index = -1;
            secRegistroTercero = null;
        } else {
            context.execute("errorDatosNullTerceros.show()");
        }
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTerceroSucursal(1) == true) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaTercero = "150";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaSucursal = "90";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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
            if (tipoLista == 0) {
                nuevoTerceroSucursal.setTercero(listTerceros.get(indexAux));
            } else {
                nuevoTerceroSucursal.setTercero(filtrarListTercero.get(indexAux));
            }
            listTerceroSucursalCrear.add(nuevoTerceroSucursal);
            if (listTercerosSucursales == null) {
                listTercerosSucursales = new ArrayList<TercerosSucursales>();
            }
            listTercerosSucursales.add(nuevoTerceroSucursal);
            ////------////
            nuevoTerceroSucursal = new TercerosSucursales();
            nuevoTerceroSucursal.setCiudad(new Ciudades());
            ////-----////

            context.update("form:datosTercerosSucursales");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("NuevoRegistroTerceroSucursal.hide()");
            cambiosTerceroSucursal = true;
            indexTS = -1;
            secRegistroTerceroSucursal = null;
        } else {
            context.execute("errorDatosNullSucursal.show()");
        }
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTercero(2) == true) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaTercero = "150";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaSucursal = "90";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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

            context.update("form:datosTerceros");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("DuplicarRegistroTercero.hide()");
            cambiosTercero = true;
            index = -1;
            secRegistroTercero = null;
        } else {
            context.execute("errorDatosNullTerceros.show()");
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

            }
            if (tipoListaTS == 1) {
                duplicarTerceroSucursal.setCodigosucursal(filtrarListTercerosSucursales.get(indexTS).getCodigosucursal());
                duplicarTerceroSucursal.setCodigopatronal(filtrarListTercerosSucursales.get(indexTS).getCodigopatronal());
                duplicarTerceroSucursal.setDescripcion(filtrarListTercerosSucursales.get(indexTS).getDescripcion());
                duplicarTerceroSucursal.setCiudad(filtrarListTercerosSucursales.get(indexTS).getCiudad());

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
        RequestContext context = RequestContext.getCurrentInstance();
        if (validarCamposNulosTerceroSucursal(2) == true) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaTercero = "150";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 0;
                filtrarListTercero = null;
                tipoLista = 0;
            }
            if (banderaTS == 1) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaSucursal = "90";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 0;
                filtrarListTercerosSucursales = null;
                tipoListaTS = 0;
            }
            k++;
            BigInteger var = BigInteger.valueOf(k);
            duplicarTerceroSucursal.setSecuencia(var);
            if (tipoLista == 0) {
                duplicarTerceroSucursal.setTercero(listTerceros.get(indexAux));
            } else {
                duplicarTerceroSucursal.setTercero(filtrarListTercero.get(indexAux));
            }
            listTerceroSucursalCrear.add(duplicarTerceroSucursal);
            listTercerosSucursales.add(duplicarTerceroSucursal);
            duplicarTerceroSucursal = new TercerosSucursales();
            context.update("form:datosTerceros");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("DuplicarRegistroTerceroSucursal.hide()");
            cambiosTerceroSucursal = true;
            indexTS = -1;
            secRegistroTerceroSucursal = null;
        } else {
            context.execute("errorDatosNullSucursal.show()");
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
            FacesContext c = FacesContext.getCurrentInstance();
            if (bandera == 0) {
                altoTablaTercero = "128";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("width: 80px");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("width: 80px");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("width: 80px");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("width: 80px");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("width: 80px");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("width: 80px");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("width: 80px");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("width: 80px");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("width: 80px");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("width: 80px");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCodigoAlterno.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosTerceros");
                bandera = 1;
            } else if (bandera == 1) {
                altoTablaTercero = "150";
                terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
                terceroNombre.setFilterStyle("display: none; visibility: hidden;");
                terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
                terceroNIT.setFilterStyle("display: none; visibility: hidden;");
                terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
                terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
                terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
                terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
                terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
                terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
                terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
                terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
                terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
                terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
                terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
                terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
                terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
                terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
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
            FacesContext c = FacesContext.getCurrentInstance();
            if (banderaTS == 0) {
                altoTablaSucursal = "68";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("width: 80px");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("width: 80px");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("width: 80px");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
                terceroSucursalCiudad.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosTercerosSucursales");
                banderaTS = 1;
            } else if (banderaTS == 1) {
                altoTablaSucursal = "90";
                terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
                terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
                terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
                terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
                terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaTercero = "150";
            terceroNombre = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNombre");
            terceroNombre.setFilterStyle("display: none; visibility: hidden;");
            terceroNIT = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNIT");
            terceroNIT.setFilterStyle("display: none; visibility: hidden;");
            terceroDigitoVerificacion = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroDigitoVerificacion");
            terceroDigitoVerificacion.setFilterStyle("display: none; visibility: hidden;");
            terceroNITAlternativo = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITAlternativo");
            terceroNITAlternativo.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSS = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSS");
            terceroCodigoSS.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSP = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSP");
            terceroCodigoSP.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoSC = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCodigoSC");
            terceroCodigoSC.setFilterStyle("display: none; visibility: hidden;");
            terceroTConsolidador = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroTConsolidador");
            terceroTConsolidador.setFilterStyle("display: none; visibility: hidden;");
            terceroNITConsolidado = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroNITConsolidado");
            terceroNITConsolidado.setFilterStyle("display: none; visibility: hidden;");
            terceroCiudad = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCiudad.setFilterStyle("display: none; visibility: hidden;");
            terceroCodigoAlterno = (Column) c.getViewRoot().findComponent("form:datosTerceros:terceroCiudad");
            terceroCodigoAlterno.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTerceros");
            bandera = 0;
            filtrarListTercero = null;
            tipoLista = 0;
        }

        if (banderaTS == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaSucursal = "90";
            terceroSucursalCodigo = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCodigo");
            terceroSucursalCodigo.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalPatronal = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalPatronal");
            terceroSucursalPatronal.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalObservacion = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalObservacion");
            terceroSucursalObservacion.setFilterStyle("display: none; visibility: hidden;");
            terceroSucursalCiudad = (Column) c.getViewRoot().findComponent("form:datosTercerosSucursales:terceroSucursalCiudad");
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
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
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
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                if (tipoNuevo == 1) {
                    nuevoTercero.setTerceroconsolidador(new Terceros());
                    context.update("formularioDialogos:nuevaTerceroCT");
                } else if (tipoNuevo == 2) {
                    duplicarTercero.setTerceroconsolidador(new Terceros());
                    context.update("formularioDialogos:duplicarTerceroCT");
                }
                listTerceroConsolidador.clear();
                getListTerceroConsolidador();
            }
        } else if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                if (tipoNuevo == 1) {
                    nuevoTercero.setCiudad(new Ciudades());
                    context.update("formularioDialogos:nuevaCodigoAT");
                } else if (tipoNuevo == 2) {
                    duplicarTercero.setCiudad(new Ciudades());
                    context.update("formularioDialogos:duplicarCodigoAT");
                }
                listCiudades.clear();
                getListCiudades();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoTerceroSucursal(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CIUDAD")) {
            if (!valorConfirmar.isEmpty()) {
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
            } else {
                if (tipoNuevo == 1) {
                    nuevoTerceroSucursal.setCiudad(new Ciudades());
                    context.update("formularioDialogos:nuevaCiudadTS");
                } else if (tipoNuevo == 2) {
                    duplicarTerceroSucursal.setCiudad(new Ciudades());
                    context.update("formularioDialogos:duplicarCiudadTS");
                }
                listCiudades.clear();
                getListCiudades();
            }
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            cambiosTercero = true;
            permitirIndex = true;

            context.update("form:datosTerceros");
        } else if (tipoActualizacion == 1) {
            nuevoTercero.setTerceroconsolidador(terceroCSeleccionado);
            context.update("formularioDialogos:nuevaTerceroCT");
            context.update("formularioDialogos:nuevaTerceroCNITT");
        } else if (tipoActualizacion == 2) {
            duplicarTercero.setTerceroconsolidador(terceroCSeleccionado);
            context.update("formularioDialogos:duplicarTTerceroCT");
            context.update("formularioDialogos:duplicarTTerceroCNITT");
        }
        filtrarListTerceroConsolidador = null;
        terceroCSeleccionado = new Terceros();
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        /*
         * context.update("form:TerceroDialogo");
         * context.update("form:lovTercero");
        context.update("form:aceptarT");
         */
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");

    }

    public void cancelarCambioTerceros() {
        filtrarListTerceroConsolidador = null;
        terceroCSeleccionado = new Terceros();
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");
    }

    public void actualizarCiudad() {
        RequestContext context = RequestContext.getCurrentInstance();
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            cambiosTercero = true;
            permitirIndex = true;

            context.update("form:datosTerceros");
        } else if (tipoActualizacion == 1) {
            nuevoTercero.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:nuevaCiudadT");
        } else if (tipoActualizacion == 2) {
            duplicarTercero.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:duplicarTCiudadT");
        }
        filtrarListCiudades = null;
        ciudadSeleccionada = new Ciudades();
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        /*
         * context.update("form:CiudadDialogo");
         * context.update("form:lovCiudad");
        context.update("form:aceptarC");
         */
        context.reset("form:lovCiudad:globalFilter");
        context.execute("lovCiudad.clearFilters()");
        context.execute("CiudadDialogo.hide()");
    }

    public void cancelarCambioCiudad() {
        filtrarListCiudades = null;
        ciudadSeleccionada = new Ciudades();
        aceptar = true;
        index = -1;
        secRegistroTercero = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovCiudad:globalFilter");
        context.execute("lovCiudad.clearFilters()");
        context.execute("CiudadDialogo.hide()");
    }

    public void actualizarCiudadTS() {
        RequestContext context = RequestContext.getCurrentInstance();
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
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            cambiosTerceroSucursal = true;
            permitirIndexTS = true;
            context.update("form:datosTercerosSucursales");
        } else if (tipoActualizacion == 1) {
            nuevoTerceroSucursal.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:nuevaCiudadTS");
        } else if (tipoActualizacion == 2) {
            duplicarTerceroSucursal.setCiudad(ciudadSeleccionada);
            context.update("formularioDialogos:duplicarCiudadTS");
        }
        filtrarListCiudades = null;
        ciudadSeleccionada = new Ciudades();
        aceptar = true;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
        tipoActualizacion = -1;
        /*
         * context.update("form:CiudadTSDialogo");
         * context.update("form:lovCiudadTS");
        context.update("form:aceptarCTS");
         */
        context.reset("form:lovCiudadTS:globalFilter");
        context.execute("lovCiudadTS.clearFilters()");
        context.execute("CiudadTSDialogo.hide()");
    }

    public void cancelarCambioCiudadTS() {
        filtrarListCiudades = null;
        ciudadSeleccionada = new Ciudades();
        aceptar = true;
        indexTS = -1;
        secRegistroTerceroSucursal = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovCiudadTS:globalFilter");
        context.execute("lovCiudadTS.clearFilters()");
        context.execute("CiudadTSDialogo.hide()");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovBuscarTercero:globalFilter");
        context.execute("lovBuscarTercero.clearFilters()");
        context.execute("BuscarTerceroDialogo.hide()");
    }

    public void validarSeleccionTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosTercero == false && cambiosTerceroSucursal == false) {
            listTerceros = null;
            listTerceros = new ArrayList<Terceros>();
            if (terceroLOVSeleccionado.getCiudad() == null) {
                terceroLOVSeleccionado.setCiudad(new Ciudades());
            }
            if (terceroLOVSeleccionado.getTerceroconsolidador() == null) {
                terceroLOVSeleccionado.setTerceroconsolidador(new Terceros());
            }
            listTerceros.add(terceroLOVSeleccionado);
            terceroLOVSeleccionado = new Terceros();
            filtrarListTerceroLOV = null;
            listTercerosLOV.clear();
            getListTercerosLOV();
            context.update("form:datosTerceros");
            getListTercerosSucursales();
            context.update("form:datosTercerosSucursales");
            /*
             * context.update("form:BuscarTerceroDialogo");
             context.update("form:aceptarBT");
             */
            context.reset("form:lovBuscarTercero:globalFilter");
            context.execute("lovBuscarTercero.clearFilters()");
            context.execute("BuscarTerceroDialogo.hide()");
        } else {
            terceroLOVSeleccionado = new Terceros();
            filtrarListTerceroLOV = null;
            listTercerosLOV.clear();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void mostrarTodos() {
        System.out.println("ControlTercero.mostrarTodos()");
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
        indexTS = -1;
        cualCeldaTS = -1;
        secRegistroTerceroSucursal = null;
        RequestContext.getCurrentInstance().update("formularioDialogos:EmpresasDialogo");
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void actualizarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (cambiosTercero == false && cambiosTerceroSucursal == false) {
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            filtrarListEmpresas = null;
            aceptar = true;
            context.update("formularioDialogos:EmpresasDialogo");
            context.update("formularioDialogos:lovEmpresas");
            context.update("formularioDialogos:aceptarE");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.execute("EmpresasDialogo.hide()");
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
        secRegistroTercero = null;
        cualCelda = -1;
        indexTS = -1;
        cualCeldaTS = -1;
        secRegistroTerceroSucursal = null;
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
                if (empresaActual.getSecuencia() != null) {
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
            if (listTercerosSucursales == null) {
                if (index >= 0) {
                    if (tipoLista == 0) {
                        listTercerosSucursales = administrarTercero.obtenerListTercerosSucursales(listTerceros.get(index).getSecuencia());
                    } else {
                        listTercerosSucursales = administrarTercero.obtenerListTercerosSucursales(filtrarListTercero.get(index).getSecuencia());
                    }
                    if (listTercerosSucursales != null) {
                        for (int i = 0; i < listTercerosSucursales.size(); i++) {
                            if (listTercerosSucursales.get(i).getCiudad() == null) {
                                listTercerosSucursales.get(i).setCiudad(new Ciudades());
                            }
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
        listEmpresas = administrarTercero.listEmpresas();
        if (listEmpresas != null) {
            if (listEmpresas.size() > 0) {
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
        listCiudades = administrarTercero.listCiudades();
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
        if (empresaActual.getSecuencia() != null) {
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

    public Terceros getTerceroTablaSeleccionado() {
        System.out.println("getTerceroSeleccionado");
        getListTerceros();
        if (listTerceros != null) {
            int tam = listTerceros.size();
            if (tam > 0) {
                terceroTablaSeleccionado = listTerceros.get(0);
            }
        }
        return terceroTablaSeleccionado;
    }

    public void setTerceroTablaSeleccionado(Terceros terceroTablaSeleccionado) {
        this.terceroTablaSeleccionado = terceroTablaSeleccionado;
    }

    public TercerosSucursales getTerceroSucursalTablaSeleccionado() {
        getListTercerosSucursales();
        if (listTercerosSucursales != null) {
            terceroSucursalTablaSeleccionado = new TercerosSucursales();
            int tam = listTercerosSucursales.size();
            if (tam > 0) {
                terceroSucursalTablaSeleccionado = listTercerosSucursales.get(0);
            }
        }
        return terceroSucursalTablaSeleccionado;
    }

    public void setTerceroSucursalTablaSeleccionado(TercerosSucursales terceroSucursalTablaSeleccionado) {
        this.terceroSucursalTablaSeleccionado = terceroSucursalTablaSeleccionado;
    }

    public String getInfoRegistroEmpresa() {
        getListEmpresas();
        if (listEmpresas != null) {
            infoRegistroEmpresa = "Cantidad de registros : " + listEmpresas.size();
        } else {
            infoRegistroEmpresa = "Cantidad de registros : 0";
        }
        return infoRegistroEmpresa;
    }

    public void setInfoRegistroEmpresa(String infoRegistroEmpresa) {
        this.infoRegistroEmpresa = infoRegistroEmpresa;
    }

    public String getInfoRegistroCiudad2() {
        getListCiudades();
        if (listCiudades != null) {
            infoRegistroCiudad2 = "Cantidad de registros : " + listCiudades.size();
        } else {
            infoRegistroCiudad2 = "Cantidad de registros : 0";
        }
        return infoRegistroCiudad2;
    }

    public void setInfoRegistroCiudad2(String infoRegistroCiudad2) {
        this.infoRegistroCiudad2 = infoRegistroCiudad2;
    }

    public String getInfoRegistroCiudad1() {
        getListCiudades();
        if (listCiudades != null) {
            infoRegistroCiudad1 = "Cantidad de registros : " + listCiudades.size();
        } else {
            infoRegistroCiudad1 = "Cantidad de registros : 0";
        }
        return infoRegistroCiudad1;
    }

    public void setInfoRegistroCiudad1(String infoRegistroCiudad1) {
        this.infoRegistroCiudad1 = infoRegistroCiudad1;
    }

    public String getInfoRegistroTercero() {
        System.out.println("getInfoRegistroTercero");
        getListTerceros();
        if (listTerceros != null) {
            infoRegistroTercero = "Cantidad de registros : " + listTerceros.size();
        } else {
            infoRegistroTercero = "Cantidad de registros : 0";
        }
        return infoRegistroTercero;
    }

    public void setInfoRegistroTercero(String infoRegistroTercero) {
        this.infoRegistroTercero = infoRegistroTercero;
    }

    public String getInfoRegistroTerceroConsolidador() {
        getListTerceroConsolidador();
        if (listTerceroConsolidador != null) {
            infoRegistroTerceroConsolidador = "Cantidad de registros : " + listTerceroConsolidador.size();
        } else {
            infoRegistroTerceroConsolidador = "Cantidad de registros : 0";
        }
        return infoRegistroTerceroConsolidador;
    }

    public void setInfoRegistroTerceroConsolidador(String infoRegistroTerceroConsolidador) {
        this.infoRegistroTerceroConsolidador = infoRegistroTerceroConsolidador;
    }

    public List<Terceros> getListTercerosLOV() {
        if (empresaActual.getSecuencia() != null) {
            listTercerosLOV = administrarTercero.obtenerListTerceros(empresaActual.getSecuencia());
            if (listTercerosLOV != null) {
                for (int i = 0; i < listTercerosLOV.size(); i++) {
                    if (listTercerosLOV.get(i).getCiudad() == null) {
                        listTercerosLOV.get(i).setCiudad(new Ciudades());
                    }
                    if (listTercerosLOV.get(i).getTerceroconsolidador() == null) {
                        listTercerosLOV.get(i).setTerceroconsolidador(new Terceros());
                    }
                }
            }
        }
        return listTercerosLOV;
    }

    public void setListTercerosLOV(List<Terceros> listTercerosLOV) {
        this.listTercerosLOV = listTercerosLOV;
    }

    public List<Terceros> getFiltrarListTerceroLOV() {
        return filtrarListTerceroLOV;
    }

    public void setFiltrarListTerceroLOV(List<Terceros> filtrarListTerceroLOV) {
        this.filtrarListTerceroLOV = filtrarListTerceroLOV;
    }

    public Terceros getTerceroLOVSeleccionado() {
        return terceroLOVSeleccionado;
    }

    public void setTerceroLOVSeleccionado(Terceros terceroLOVSeleccionado) {
        this.terceroLOVSeleccionado = terceroLOVSeleccionado;
    }

    public String getAltoTablaTercero() {
        return altoTablaTercero;
    }

    public void setAltoTablaTercero(String altoTablaTercero) {
        this.altoTablaTercero = altoTablaTercero;
    }

    public String getAltoTablaSucursal() {
        return altoTablaSucursal;
    }

    public void setAltoTablaSucursal(String altoTablaSucursal) {
        this.altoTablaSucursal = altoTablaSucursal;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }
}

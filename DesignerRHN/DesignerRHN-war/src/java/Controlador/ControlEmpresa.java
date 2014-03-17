package Controlador;

import Entidades.CentrosCostos;
import Entidades.Circulares;
import Entidades.Empresas;
import Entidades.Monedas;
import Entidades.VigenciasMonedasBases;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmpresasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlEmpresa implements Serializable {

    @EJB
    AdministrarEmpresasInterface administrarEmpresa;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtrarListaEmpresas;
    //
    private List<VigenciasMonedasBases> listaVigenciasMonedasBases;
    private List<VigenciasMonedasBases> filtrarListaVigenciasMonedasBases;
    //
    private List<Circulares> listaCirculares;
    private List<Circulares> filtrarListaCirculares;
    //Activo/Desactivo Crtl + F11
    private int bandera, banderaVigencia, banderaCircular;
    //Columnas Tabla 
    private Column empresaCodigo, empresaNIT, empresaNombre, empresaReglamento, empresaManual, empresaLogo, empresaCentroCosto, empresaCodigoAlternativo;
    private Column vigenciaFecha, vigenciaCodigo, vigenciaMoneda;
    private Column circularFecha, circularExpedido, circularContenido;
    //Otros
    private boolean aceptar;
    private int index, indexVigencia, indexAux, indexCircular;
    //modificar
    private List<Empresas> listEmpresasModificar;
    private List<VigenciasMonedasBases> listVigenciasMonedasBasesModificar;
    private List<Circulares> listCircularesModificar;
    private boolean guardado, guardadoVigencia, guardadoCircular;
    //crear 
    private Empresas nuevoEmpresa;
    private VigenciasMonedasBases nuevoVigenciaMonedaBase;
    private Circulares nuevoCircular;
    private List<Empresas> listEmpresasCrear;
    private List<VigenciasMonedasBases> listVigenciasMonedasBasesCrear;
    private List<Circulares> listCircularesCrear;
    private BigInteger l;
    private int k;
    //borrar 
    private List<Empresas> listEmpresasBorrar;
    private List<VigenciasMonedasBases> listVigenciasMonedasBasesBorrar;
    private List<Circulares> listCircularesBorrar;
    //editar celda
    private Empresas editarEmpresa;
    private VigenciasMonedasBases editarVigenciaMonedaBase;
    private Circulares editarCircular;
    private int cualCelda, tipoLista, cualCeldaVigencia, tipoListaVigencia, cualCeldaCircular, tipoListaCircular;
    //duplicar
    private Empresas duplicarEmpresa;
    private VigenciasMonedasBases duplicarVigenciaMonedaBase;
    private Circulares duplicarCircular;
    private BigInteger secRegistro, secRegistroVigencia, secRegistroCircular;
    private BigInteger backUpSecRegistro, backUpSecRegistroVigencia, backUpSecRegistroCircular;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;
    private String moneda, centroCosto, codigoMoneda;

    ///////////LOV///////////
    private List<CentrosCostos> lovCentrosCostos;
    private List<CentrosCostos> filtrarLovCentrosCostos;
    private CentrosCostos centroCostoSeleccionado;

    private List<Monedas> lovMonedas;
    private List<Monedas> filtrarLovMonedas;
    private Monedas monedaSeleccionado;

    private List<Empresas> lovEmpresas;
    private List<Empresas> filtrarLovEmpresas;
    private Empresas empresaSeleccionada;
    private Empresas empresaOrigenClonado, empresaDestinoClonado;

    private boolean permitirIndex, permitirIndexVigencia;
    private int tipoActualizacion;
    private short auxCodigoEmpresa;
    private String auxNombreEmpresa;
    private long auxNitEmpresa;
    private Date auxFechaVigencia;
    private Date auxFechaCircular;
    private Date fechaParametro;
    //
    private boolean cambiosPagina;
    private String altoTablaEmpresa, altoTablaVigencia, altoTablaCircular;
    //
    private String paginaAnterior;
    //
    private boolean activoVigencia, activoCircular;
    //
    private boolean activoCasillaClonado;
    //
    private String variableClonado;
    //
    private String auxClonadoNombreOrigen, auxClonadoNombreDestino;
    private short auxClonadoCodigoOrigen, auxClonadoCodigoDestino;
    //
    private int indexClonadoOrigen, indexClonadoDestino;
    //
    private boolean activoBtnesAdd;
    //

    public ControlEmpresa() {
        activoBtnesAdd = true;
        activoCasillaClonado = true;
        activoVigencia = true;
        activoCircular = true;
        paginaAnterior = "";
        //altos tablas
        altoTablaEmpresa = "112";
        altoTablaVigencia = "110";
        altoTablaCircular = "110";
        //Permitir index
        permitirIndex = true;
        permitirIndexVigencia = true;
        //lovs
        lovCentrosCostos = null;
        centroCostoSeleccionado = new CentrosCostos();
        lovMonedas = null;
        monedaSeleccionado = new Monedas();
        lovEmpresas = null;
        empresaSeleccionada = new Empresas();
        empresaOrigenClonado = new Empresas();
        empresaDestinoClonado = new Empresas();
        //index tablas
        indexVigencia = -1;
        indexCircular = -1;
        indexClonadoOrigen = -1;
        indexClonadoDestino = -1;
        //listas de tablas
        listaEmpresas = null;
        listaVigenciasMonedasBases = null;
        listaCirculares = null;
        //Otros
        aceptar = true;
        cambiosPagina = true;
        tipoActualizacion = -1;
        variableClonado = "origen";
        k = 0;
        //borrar 
        listEmpresasBorrar = new ArrayList<Empresas>();
        listVigenciasMonedasBasesBorrar = new ArrayList<VigenciasMonedasBases>();
        listCircularesBorrar = new ArrayList<Circulares>();
        //crear 
        listEmpresasCrear = new ArrayList<Empresas>();
        listVigenciasMonedasBasesCrear = new ArrayList<VigenciasMonedasBases>();
        listCircularesCrear = new ArrayList<Circulares>();
        //modificar 
        listEmpresasModificar = new ArrayList<Empresas>();
        listVigenciasMonedasBasesModificar = new ArrayList<VigenciasMonedasBases>();
        listCircularesModificar = new ArrayList<Circulares>();
        //editar
        editarEmpresa = new Empresas();
        editarVigenciaMonedaBase = new VigenciasMonedasBases();
        editarCircular = new Circulares();
        //Cual Celda
        cualCelda = -1;
        cualCeldaVigencia = -1;
        cualCeldaCircular = -1;
        //Tipo Lista
        tipoListaVigencia = 0;
        tipoLista = 0;
        tipoListaCircular = 0;
        //guardar
        guardado = true;
        guardadoVigencia = true;
        guardadoCircular = true;
        //Crear 
        nuevoEmpresa = new Empresas();
        nuevoEmpresa.setCentrocosto(new CentrosCostos());
        nuevoVigenciaMonedaBase = new VigenciasMonedasBases();
        nuevoVigenciaMonedaBase.setMoneda(new Monedas());
        nuevoCircular = new Circulares();

        //Duplicar
        duplicarEmpresa = new Empresas();
        duplicarVigenciaMonedaBase = new VigenciasMonedasBases();
        duplicarCircular = new Circulares();
        //Sec Registro
        secRegistro = null;
        backUpSecRegistro = null;
        secRegistroVigencia = null;
        backUpSecRegistroVigencia = null;
        secRegistroVigencia = null;
        backUpSecRegistroCircular = null;
        //Banderas
        bandera = 0;
        banderaVigencia = 0;
        banderaCircular = 0;
    }

    public String valorPaginaAnterior() {
        return paginaAnterior;
    }

    public void inicializarPagina(String paginaLlamado) {
        paginaAnterior = paginaLlamado;
        listaVigenciasMonedasBases = null;
        listaCirculares = null;
        listaEmpresas = null;
        getListaEmpresas();
        int tam = listaEmpresas.size();
        if (tam >= 1) {
            index = 0;
            getListaCirculares();
            getListaVigenciasMonedasBases();
        }
    }

    public boolean validarFechaVigenciaMonedaBase(int i) {
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        if (i == 0) {
            VigenciasMonedasBases auxiliar = new VigenciasMonedasBases();
            if (tipoListaVigencia == 0) {
                auxiliar = listaVigenciasMonedasBases.get(indexVigencia);
            }
            if (tipoListaVigencia == 1) {
                auxiliar = filtrarListaVigenciasMonedasBases.get(indexVigencia);
            }
            if (auxiliar.getFecha() != null) {
                if (auxiliar.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }

        }
        if (i == 1) {
            if (nuevoVigenciaMonedaBase.getFecha() != null) {
                if (nuevoVigenciaMonedaBase.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaMonedaBase.getFecha() != null) {
                if (duplicarVigenciaMonedaBase.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    public void modificacionesFechaVigenciaMonedaBase(int i, int c) {
        indexVigencia = i;
        if (validarFechaVigenciaMonedaBase(0) == true) {
            cambiarIndiceVigencia(i, c);
            modificarVigenciaMonedaBase(i);
        } else {
            if (tipoListaVigencia == 0) {
                listaVigenciasMonedasBases.get(indexVigencia).setFecha(auxFechaVigencia);
            }
            if (tipoListaVigencia == 1) {
                filtrarListaVigenciasMonedasBases.get(indexVigencia).setFecha(auxFechaVigencia);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDetalleEmpresa");
            context.execute("errorFechasVigencia.show()");
        }
    }

    public boolean validarFechaCircular(int i) {
        boolean retorno = true;
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        if (i == 0) {
            Circulares auxiliar = new Circulares();
            if (tipoListaCircular == 0) {
                auxiliar = listaCirculares.get(indexCircular);
            }
            if (tipoListaCircular == 1) {
                auxiliar = filtrarListaCirculares.get(indexCircular);
            }
            if (auxiliar.getFecha() != null) {
                if (auxiliar.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }

        }
        if (i == 1) {
            if (nuevoCircular.getFecha() != null) {
                if (nuevoCircular.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarCircular.getFecha() != null) {
                if (duplicarCircular.getFecha().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            } else {
                retorno = true;
            }
        }
        return retorno;
    }

    public void modificacionesFechaCircular(int i, int c) {
        indexCircular = i;
        if (validarFechaCircular(0) == true) {
            cambiarIndiceCircular(i, c);
            modificarCirculares(i);
        } else {
            if (tipoListaCircular == 0) {
                listaCirculares.get(indexCircular).setFecha(auxFechaCircular);
            }
            if (tipoListaCircular == 1) {
                filtrarListaCirculares.get(indexCircular).setFecha(auxFechaCircular);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCircular");
            context.execute("errorFechasCircular.show()");
        }
    }

    public boolean validarCamposNulosEmpresa(int i) {
        boolean retorno = true;
        if (i == 0) {
            Empresas aux = new Empresas();
            if (tipoLista == 0) {
                aux = listaEmpresas.get(index);
            }
            if (tipoLista == 1) {
                aux = filtrarListaEmpresas.get(index);
            }
            if (aux.getNombre().isEmpty() || aux.getNit() <= 0) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoEmpresa.getNombre().isEmpty() || nuevoEmpresa.getNit() <= 0) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarEmpresa.getNombre().isEmpty() || duplicarEmpresa.getNit() <= 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposNulosVigenciaMonedaBase(int i) {
        boolean retorno = true;
        if (i == 0) {
            VigenciasMonedasBases aux = new VigenciasMonedasBases();
            if (tipoLista == 0) {
                aux = listaVigenciasMonedasBases.get(indexVigencia);
            }
            if (tipoLista == 1) {
                aux = filtrarListaVigenciasMonedasBases.get(indexVigencia);
            }
            if (aux.getMoneda().getSecuencia() == null || aux.getFecha() == null) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoVigenciaMonedaBase.getMoneda().getSecuencia() == null || nuevoVigenciaMonedaBase.getFecha() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarVigenciaMonedaBase.getMoneda().getSecuencia() == null || duplicarVigenciaMonedaBase.getFecha() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void procesoModificacionEmpresa(int i) {
        index = i;
        boolean respuesta = validarCamposNulosEmpresa(0);
        if (respuesta == true) {
            modificarEmpresa(i);
        } else {
            if (tipoLista == 0) {
                listaEmpresas.get(index).setCodigo(auxCodigoEmpresa);
                listaEmpresas.get(index).setNombre(auxNombreEmpresa);
                listaEmpresas.get(index).setNit(auxNitEmpresa);
            }
            if (tipoLista == 1) {
                filtrarListaEmpresas.get(index).setCodigo(auxCodigoEmpresa);
                filtrarListaEmpresas.get(index).setNombre(auxNombreEmpresa);
                filtrarListaEmpresas.get(index).setNit(auxNitEmpresa);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEmpresa");
            context.execute("errorDatosNullEmpresa.show()");
        }
    }

    public void modificarEmpresa(int indice) {
        int tamDes = 0;
        if (tipoLista == 0) {
            tamDes = listaEmpresas.get(indice).getNombre().length();
        }
        if (tipoLista == 1) {
            tamDes = filtrarListaEmpresas.get(indice).getNombre().length();
        }
        if (tamDes >= 1 && tamDes <= 50) {
            if (tipoLista == 0) {
                String textM = listaEmpresas.get(indice).getNombre().toUpperCase();
                listaEmpresas.get(indice).setNombre(textM);
                String textM1 = listaEmpresas.get(indice).getReglamento().toUpperCase();
                listaEmpresas.get(indice).setReglamento(textM1);
                String textM2 = listaEmpresas.get(indice).getManualadministrativo().toUpperCase();
                listaEmpresas.get(indice).setManualadministrativo(textM2);
                if (!listEmpresasCrear.contains(listaEmpresas.get(indice))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(listaEmpresas.get(indice));
                    } else if (!listEmpresasModificar.contains(listaEmpresas.get(indice))) {
                        listEmpresasModificar.add(listaEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                String textM = filtrarListaEmpresas.get(indice).getNombre().toUpperCase();
                filtrarListaEmpresas.get(indice).setNombre(textM);
                String textM1 = filtrarListaEmpresas.get(indice).getReglamento().toUpperCase();
                listaEmpresas.get(indice).setReglamento(textM1);
                String textM2 = filtrarListaEmpresas.get(indice).getManualadministrativo().toUpperCase();
                listaEmpresas.get(indice).setManualadministrativo(textM2);
                if (!listEmpresasCrear.contains(filtrarListaEmpresas.get(indice))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(indice));
                    } else if (!listEmpresasModificar.contains(filtrarListaEmpresas.get(indice))) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
            index = -1;
            secRegistro = null;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEmpresa");
        } else {
            if (tipoLista == 0) {
                listaEmpresas.get(index).setNombre(auxNombreEmpresa);
            }
            if (tipoLista == 1) {
                filtrarListaEmpresas.get(index).setNombre(auxNombreEmpresa);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEmpresa");
            context.execute("errorNombreEmpresa.show()");
        }
    }

    public void modificarEmpresa(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoLista == 0) {
                listaEmpresas.get(indice).getCentrocosto().setNombre(centroCosto);
            } else {
                filtrarListaEmpresas.get(indice).getCentrocosto().setNombre(centroCosto);
            }
            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaEmpresas.get(indice).setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                } else {
                    filtrarListaEmpresas.get(indice).setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                }
                lovCentrosCostos.clear();
                getLovCentrosCostos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listEmpresasCrear.contains(listaEmpresas.get(indice))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(listaEmpresas.get(indice));
                    } else if (!listEmpresasModificar.contains(listaEmpresas.get(indice))) {
                        listEmpresasModificar.add(listaEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                if (!listEmpresasCrear.contains(filtrarListaEmpresas.get(indice))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(indice));
                    } else if (!listEmpresasModificar.contains(filtrarListaEmpresas.get(indice))) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosEmpresa");
    }

    public void modificarVigenciaMonedaBase(int indice) {
        if (tipoListaVigencia == 0) {
            if (!listVigenciasMonedasBasesCrear.contains(listaVigenciasMonedasBases.get(indice))) {
                if (listVigenciasMonedasBasesModificar.isEmpty()) {
                    listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indice));
                } else if (!listVigenciasMonedasBasesModificar.contains(listaVigenciasMonedasBases.get(indice))) {
                    listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indice));
                }
                if (guardadoVigencia == true) {
                    guardadoVigencia = false;
                }
            }
        }
        if (tipoListaVigencia == 1) {
            if (!listVigenciasMonedasBasesCrear.contains(filtrarListaVigenciasMonedasBases.get(indice))) {
                if (listVigenciasMonedasBasesModificar.isEmpty()) {
                    listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indice));
                } else if (!listVigenciasMonedasBasesModificar.contains(filtrarListaVigenciasMonedasBases.get(indice))) {
                    listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indice));
                }
                if (guardadoVigencia == true) {
                    guardadoVigencia = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexVigencia = -1;
        secRegistroVigencia = null;
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVigenciaMonedaBase");
    }

    public void modificarVigenciaMonedaBase(int indice, String confirmarCambio, String valorConfirmar) {
        indexVigencia = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MONEDA")) {
            if (tipoListaVigencia == 0) {
                listaVigenciasMonedasBases.get(indice).getMoneda().setNombre(moneda);
            } else {
                filtrarListaVigenciasMonedasBases.get(indice).getMoneda().setNombre(moneda);
            }
            for (int i = 0; i < lovMonedas.size(); i++) {
                if (lovMonedas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigencia == 0) {
                    listaVigenciasMonedasBases.get(indice).setMoneda(lovMonedas.get(indiceUnicoElemento));
                } else {
                    filtrarListaVigenciasMonedasBases.get(indice).setMoneda(lovMonedas.get(indiceUnicoElemento));
                }
                lovMonedas.clear();
                getLovMonedas();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexVigencia = false;
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("CODIGOMONEDA")) {
            if (tipoListaVigencia == 0) {
                listaVigenciasMonedasBases.get(indice).getMoneda().setCodigo(codigoMoneda);
            } else {
                filtrarListaVigenciasMonedasBases.get(indice).getMoneda().setCodigo(codigoMoneda);
            }
            for (int i = 0; i < lovMonedas.size(); i++) {
                if (lovMonedas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVigencia == 0) {
                    listaVigenciasMonedasBases.get(indice).setMoneda(lovMonedas.get(indiceUnicoElemento));
                } else {
                    filtrarListaVigenciasMonedasBases.get(indice).setMoneda(lovMonedas.get(indiceUnicoElemento));
                }
                lovMonedas.clear();
                getLovMonedas();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexVigencia = false;
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVigencia == 0) {
                if (!listVigenciasMonedasBasesCrear.contains(listaVigenciasMonedasBases.get(indice))) {
                    if (listVigenciasMonedasBasesModificar.isEmpty()) {
                        listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indice));
                    } else if (!listVigenciasMonedasBasesModificar.contains(listaVigenciasMonedasBases.get(indice))) {
                        listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indice));
                    }
                    if (guardadoVigencia == true) {
                        guardadoVigencia = false;
                    }
                }
            }
            if (tipoListaVigencia == 1) {
                if (!listVigenciasMonedasBasesCrear.contains(filtrarListaVigenciasMonedasBases.get(indice))) {
                    if (listVigenciasMonedasBasesModificar.isEmpty()) {
                        listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indice));
                    } else if (!listVigenciasMonedasBasesModificar.contains(filtrarListaVigenciasMonedasBases.get(indice))) {
                        listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indice));
                    }
                    if (guardadoVigencia == true) {
                        guardadoVigencia = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosVigenciaMonedaBase");
    }

    public boolean validarCamposNulosCircular(int i) {
        boolean retorno = true;
        if (i == 0) {
            Circulares aux = new Circulares();
            if (tipoLista == 0) {
                aux = listaCirculares.get(indexCircular);
            }
            if (tipoLista == 1) {
                aux = filtrarListaCirculares.get(indexCircular);
            }
            if (aux.getFecha() == null) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoCircular.getFecha() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarCircular.getFecha() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void modificarCirculares(int indice) {
        if (tipoListaCircular == 0) {
            String textM = listCircularesCrear.get(indice).getExpedidopor().toUpperCase();
            listCircularesCrear.get(indice).setExpedidopor(textM);
            String textM1 = listCircularesCrear.get(indice).getTexto().toUpperCase();
            listCircularesCrear.get(indice).setTexto(textM1);
            if (!listCircularesCrear.contains(listaCirculares.get(indice))) {
                if (listCircularesModificar.isEmpty()) {
                    listCircularesModificar.add(listaCirculares.get(indice));
                } else if (!listCircularesModificar.contains(listaCirculares.get(indice))) {
                    listCircularesModificar.add(listaCirculares.get(indice));
                }
                if (guardadoCircular == true) {
                    guardadoCircular = false;
                }
            }
        }
        if (tipoListaCircular == 1) {
            String textM = filtrarListaCirculares.get(indice).getExpedidopor().toUpperCase();
            filtrarListaCirculares.get(indice).setExpedidopor(textM);
            String textM1 = filtrarListaCirculares.get(indice).getTexto().toUpperCase();
            filtrarListaCirculares.get(indice).setTexto(textM1);
            if (!listCircularesCrear.contains(filtrarListaCirculares.get(indice))) {
                if (listCircularesModificar.isEmpty()) {
                    listCircularesModificar.add(filtrarListaCirculares.get(indice));
                } else if (!listCircularesModificar.contains(filtrarListaCirculares.get(indice))) {
                    listCircularesModificar.add(filtrarListaCirculares.get(indice));
                }
                if (guardadoCircular == true) {
                    guardadoCircular = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexCircular = -1;
        secRegistroCircular = null;
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosCircular");

    }

    public void cambiarIndice(int indice, int celda) {
        if (guardadoVigencia == true && guardadoCircular == true) {
            if (permitirIndex == true) {
                cualCelda = celda;
                indexAux = indice;
                index = indice;
                indexVigencia = -1;
                indexCircular = -1;
                if (tipoLista == 0) {
                    auxCodigoEmpresa = listaEmpresas.get(index).getCodigo();
                    secRegistro = listaEmpresas.get(index).getSecuencia();
                    auxNombreEmpresa = listaEmpresas.get(index).getNombre();
                    auxNitEmpresa = listaEmpresas.get(index).getNit();
                    centroCosto = listaEmpresas.get(index).getCentrocosto().getNombre();
                }
                if (tipoLista == 1) {
                    auxCodigoEmpresa = filtrarListaEmpresas.get(index).getCodigo();
                    secRegistro = filtrarListaEmpresas.get(index).getSecuencia();
                    auxNombreEmpresa = filtrarListaEmpresas.get(index).getNombre();
                    auxNitEmpresa = filtrarListaEmpresas.get(index).getNit();
                    centroCosto = filtrarListaEmpresas.get(index).getCentrocosto().getNombre();
                }
                activoBtnesAdd = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:DETALLES");
                context.update("form:EMPRESABANCO");
                context.update("form:DIRECCIONES");
                listaVigenciasMonedasBases = null;
                getListaVigenciasMonedasBases();
                context.update("form:datosVigenciaMonedaBase");
                listaCirculares = null;
                getListaCirculares();
                context.update("form:datosCircular");
                if (banderaVigencia == 1) {
                    altoTablaVigencia = "110";
                    vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
                    vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
                    vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
                    vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
                    banderaVigencia = 0;
                    filtrarListaVigenciasMonedasBases = null;
                    tipoListaVigencia = 0;
                }
                if (banderaCircular == 1) {
                    altoTablaCircular = "110";
                    circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
                    circularFecha.setFilterStyle("display: none; visibility: hidden;");
                    circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
                    circularExpedido.setFilterStyle("display: none; visibility: hidden;");
                    circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
                    circularContenido.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCircular");
                    banderaCircular = 0;
                    filtrarListaCirculares = null;
                    tipoListaCircular = 0;
                }
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceVigencia(int indice, int celda) {
        if (permitirIndexVigencia == true) {
            indexVigencia = indice;
            index = -1;
            indexCircular = -1;
            cualCeldaVigencia = celda;
            if (tipoListaVigencia == 0) {
                secRegistroVigencia = listaVigenciasMonedasBases.get(indexVigencia).getSecuencia();
                moneda = listaVigenciasMonedasBases.get(indexVigencia).getMoneda().getNombre();
                codigoMoneda = listaVigenciasMonedasBases.get(indexVigencia).getMoneda().getCodigo();
                auxFechaVigencia = listaVigenciasMonedasBases.get(indexVigencia).getFecha();
            }
            if (tipoListaVigencia == 1) {
                secRegistroVigencia = filtrarListaVigenciasMonedasBases.get(indexVigencia).getSecuencia();
                moneda = filtrarListaVigenciasMonedasBases.get(indexVigencia).getMoneda().getNombre();
                codigoMoneda = filtrarListaVigenciasMonedasBases.get(indexVigencia).getMoneda().getCodigo();
                auxFechaVigencia = filtrarListaVigenciasMonedasBases.get(indexVigencia).getFecha();
            }
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        }
    }

    public void cambiarIndiceCircular(int indice, int celda) {
        indexCircular = indice;
        index = -1;
        indexVigencia = -1;
        cualCeldaCircular = celda;
        if (tipoListaCircular == 0) {
            secRegistroCircular = listaCirculares.get(indexCircular).getSecuencia();
            auxFechaCircular = listaCirculares.get(indexCircular).getFecha();
        }
        if (tipoListaCircular == 1) {
            secRegistroCircular = filtrarListaCirculares.get(indexCircular).getSecuencia();
            auxFechaCircular = filtrarListaCirculares.get(indexCircular).getFecha();
        }
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
    }

    //GUARDAR
    public void guardarYSalir() {
        guardarGeneral();
        salir();
    }

    public void cancelarYSalir() {
        cancelarModificacionGeneral();
        salir();
    }

    public void guardarGeneral() {
        if (guardado == false || guardadoVigencia == false || guardadoCircular == false) {
            if (guardado == false) {
                guardarCambiosEmpresa();
            }
            if (guardadoVigencia == false) {
                guardarCambiosVigencia();
            }
            if (guardadoCircular == false) {
                guardarCambiosCircular();
            }
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            cambiosPagina = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
        }
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
    }

    public void guardarCambiosEmpresa() {

        if (!listEmpresasBorrar.isEmpty()) {
            for (int i = 0; i < listEmpresasBorrar.size(); i++) {
                administrarEmpresa.borrarEmpresas(listEmpresasBorrar);
            }
            listEmpresasBorrar.clear();
        }
        if (!listEmpresasCrear.isEmpty()) {
            for (int i = 0; i < listEmpresasCrear.size(); i++) {
                administrarEmpresa.crearEmpresas(listEmpresasCrear);
            }
            listEmpresasCrear.clear();
        }
        if (!listEmpresasModificar.isEmpty()) {
            administrarEmpresa.editarEmpresas(listEmpresasModificar);
            listEmpresasModificar.clear();
        }
        listaEmpresas = null;
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
        context.update("form:datosEmpresa");
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        index = -1;
        secRegistro = null;

    }

    public void guardarCambiosVigencia() {
        if (!listVigenciasMonedasBasesBorrar.isEmpty()) {
            administrarEmpresa.borrarVigenciasMonedasBases(listVigenciasMonedasBasesBorrar);
            listVigenciasMonedasBasesBorrar.clear();
        }
        if (!listVigenciasMonedasBasesCrear.isEmpty()) {
            administrarEmpresa.crearVigenciasMonedasBases(listVigenciasMonedasBasesCrear);
            listVigenciasMonedasBasesCrear.clear();
        }
        if (!listVigenciasMonedasBasesModificar.isEmpty()) {
            administrarEmpresa.editarVigenciasMonedasBases(listVigenciasMonedasBasesModificar);
            listVigenciasMonedasBasesModificar.clear();
        }
        listaVigenciasMonedasBases = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaMonedaBase");
        guardadoVigencia = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        indexVigencia = -1;
        secRegistroVigencia = null;
    }

    public void guardarCambiosCircular() {
        if (!listCircularesBorrar.isEmpty()) {
            administrarEmpresa.borrarCirculares(listCircularesBorrar);
            listCircularesBorrar.clear();
        }
        if (!listCircularesCrear.isEmpty()) {
            administrarEmpresa.crearCirculares(listCircularesCrear);
            listCircularesCrear.clear();
        }
        if (!listCircularesModificar.isEmpty()) {
            administrarEmpresa.editarCirculares(listCircularesModificar);
            listCircularesModificar.clear();
        }
        listaCirculares = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCircular");
        guardadoCircular = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;
        indexCircular = -1;
        secRegistroCircular = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacionGeneral() {
        if (guardado == false) {
            cancelarModificacionEmpresa();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEmpresa");
        }
        if (guardadoVigencia == false) {
            cancelarModificacionVigencia();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaMonedaBase");
        }
        if (guardadoCircular == false) {
            cancelarModificacionCircular();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCircular");
        }
        cancelarModificacionClonado();
        cambiosPagina = true;
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
        context.update("form:ACEPTAR");
    }

    public void cancelarModificacionEmpresa() {
        if (bandera == 1) {
            altoTablaEmpresa = "112";
            empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
            empresaCodigo.setFilterStyle("display: none; visibility: hidden;");
            empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
            empresaNIT.setFilterStyle("display: none; visibility: hidden;");
            empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
            empresaNombre.setFilterStyle("display: none; visibility: hidden;");
            empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
            empresaReglamento.setFilterStyle("display: none; visibility: hidden;");
            empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
            empresaManual.setFilterStyle("display: none; visibility: hidden;");
            empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
            empresaLogo.setFilterStyle("display: none; visibility: hidden;");
            empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
            empresaCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
            empresaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEmpresa");
            bandera = 0;
            filtrarListaEmpresas = null;
            tipoLista = 0;
        }
        listEmpresasBorrar.clear();
        listEmpresasCrear.clear();
        listEmpresasModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaEmpresas = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEmpresa");
    }

    public void cancelarModificacionVigencia() {
        if (banderaVigencia == 1) {
            altoTablaVigencia = "110";
            vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
            vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
            vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
            vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
            vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
            vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
            banderaVigencia = 0;
            filtrarListaVigenciasMonedasBases = null;
            tipoListaVigencia = 0;
        }
        listVigenciasMonedasBasesBorrar.clear();
        listVigenciasMonedasBasesCrear.clear();
        listVigenciasMonedasBasesModificar.clear();
        indexVigencia = -1;
        secRegistroVigencia = null;
        k = 0;
        listaVigenciasMonedasBases = null;
        guardadoVigencia = true;
        permitirIndexVigencia = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaMonedaBase");
    }

    public void cancelarModificacionCircular() {
        if (banderaCircular == 1) {
            altoTablaCircular = "110";
            circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
            circularFecha.setFilterStyle("display: none; visibility: hidden;");
            circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
            circularExpedido.setFilterStyle("display: none; visibility: hidden;");
            circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
            circularContenido.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCircular");
            banderaCircular = 0;
            filtrarListaCirculares = null;
            tipoListaCircular = 0;
        }
        listCircularesBorrar.clear();
        listCircularesCrear.clear();
        listCircularesModificar.clear();
        indexCircular = -1;
        secRegistroCircular = null;
        k = 0;
        listaCirculares = null;
        guardadoCircular = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosCircular");
    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEmpresa = listaEmpresas.get(index);
            }
            if (tipoLista == 1) {
                editarEmpresa = filtrarListaEmpresas.get(index);
            }
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoEmpresaD");
                context.execute("editarCodigoEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNITEmpresaD");
                context.execute("editarNITEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarNombreEmpresaD");
                context.execute("editarNombreEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarReglamentoEmpresaD");
                context.execute("editarReglamentoEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarManualEmpresaD");
                context.execute("editarManualEmpresaD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarLogoD");
                context.execute("editarLogoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarCentroCostoD");
                context.execute("editarCentroCostoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarCodigoAlternativoD");
                context.execute("editarCodigoAlternativoD.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
            activoBtnesAdd = true;
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        }
        if (indexVigencia >= 0) {
            if (tipoListaVigencia == 0) {
                editarVigenciaMonedaBase = listaVigenciasMonedasBases.get(indexVigencia);
            }
            if (tipoListaVigencia == 1) {
                editarVigenciaMonedaBase = listaVigenciasMonedasBases.get(indexVigencia);
            }
            if (cualCeldaVigencia == 0) {
                context.update("formularioDialogos:editarFechaVigenciaD");
                context.execute("editarFechaVigenciaD.show()");
                cualCeldaVigencia = -1;
            } else if (cualCeldaVigencia == 1) {
                context.update("formularioDialogos:editarCodigoVigenciaD");
                context.execute("editarCodigoVigenciaD.show()");
                cualCeldaVigencia = -1;
            } else if (cualCeldaVigencia == 2) {
                context.update("formularioDialogos:editarMonedaVigenciaD");
                context.execute("editarMonedaVigenciaD.show()");
                cualCeldaVigencia = -1;
            }
            indexVigencia = -1;
            secRegistroVigencia = null;
        }
        if (indexCircular >= 0) {
            if (tipoListaCircular == 0) {
                editarCircular = listaCirculares.get(indexCircular);
            }
            if (tipoListaCircular == 1) {
                editarCircular = listaCirculares.get(indexCircular);
            }
            if (cualCeldaCircular == 0) {
                context.update("formularioDialogos:editarFechaCircularD");
                context.execute("editarFechaCircularD.show()");
                cualCeldaCircular = -1;
            } else if (cualCeldaCircular == 1) {
                context.update("formularioDialogos:editarExpedidoCircularD");
                context.execute("editarExpedidoCircularD.show()");
                cualCeldaCircular = -1;
            } else if (cualCeldaCircular == 2) {
                context.update("formularioDialogos:editarContenidoCircularD");
                context.execute("editarContenidoCircularD.show()");
                cualCeldaCircular = -1;
            }
            indexCircular = -1;
            secRegistroCircular = null;
        }
        if (indexClonadoOrigen == 1) {
            context.update("formularioDialogos:editarOrigenClonadoD");
            context.execute("editarOrigenClonadoD.show()");
        }
        if (indexClonadoDestino == 1) {
            context.update("formularioDialogos:editarDestinoClonadoD");
            context.execute("editarDestinoClonadoD.show()");
        }
    }

    public void dialogoNuevoRegistro() {
        if (guardado == false || guardadoVigencia == false || guardadoCircular == false) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            int tam1 = listaEmpresas.size();
            int tam2 = listaVigenciasMonedasBases.size();
            int tam3 = listaCirculares.size();
            if (tam1 == 0 || tam2 == 0 || tam3 == 0) {
                activoVigencia = false;
                activoCircular = false;
                context.update("formularioDialogos:verificarNuevoRegistro");
                context.execute("verificarNuevoRegistro.show()");
            } else {
                if (index >= 0) {
                    context.update("formularioDialogos:NuevoRegistroEmpresa");
                    context.execute("NuevoRegistroEmpresa.show()");
                }
                if (indexVigencia >= 0) {
                    context.update("formularioDialogos:NuevoRegistroVigencia");
                    context.execute("NuevoRegistroVigencia.show()");
                }
                if (indexCircular >= 0) {
                    context.update("formularioDialogos:NuevoRegistroCircular");
                    context.execute("NuevoRegistroCircular.show()");
                }
            }
        }
    }

    //CREAR 
    public void agregarNuevoEmpresa() {
        boolean respueta = validarCamposNulosEmpresa(1);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoEmpresa.getNombre().length();
            if (tamDes >= 1 && tamDes <= 50) {
                if (bandera == 1) {
                    altoTablaEmpresa = "112";
                    empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
                    empresaCodigo.setFilterStyle("display: none; visibility: hidden;");
                    empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
                    empresaNIT.setFilterStyle("display: none; visibility: hidden;");
                    empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
                    empresaNombre.setFilterStyle("display: none; visibility: hidden;");
                    empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
                    empresaReglamento.setFilterStyle("display: none; visibility: hidden;");
                    empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
                    empresaManual.setFilterStyle("display: none; visibility: hidden;");
                    empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
                    empresaLogo.setFilterStyle("display: none; visibility: hidden;");
                    empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
                    empresaCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                    empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
                    empresaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosEmpresa");
                    bandera = 0;
                    filtrarListaEmpresas = null;
                    tipoLista = 0;
                }

                k++;
                l = BigInteger.valueOf(k);
                String text = nuevoEmpresa.getNombre().toUpperCase();
                nuevoEmpresa.setNombre(text);
                nuevoEmpresa.setSecuencia(l);
                if (listaEmpresas == null) {
                    listaEmpresas = new ArrayList<Empresas>();
                }
                listEmpresasCrear.add(nuevoEmpresa);
                listaEmpresas.add(nuevoEmpresa);
                nuevoEmpresa = new Empresas();
                cambiosPagina = false;
                activoBtnesAdd = true;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:DETALLES");
                context.update("form:EMPRESABANCO");
                context.update("form:DIRECCIONES");
                context.update("form:ACEPTAR");
                context.update("form:datosEmpresa");
                context.execute("NuevoRegistroEmpresa.hide()");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                index = -1;
                secRegistro = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorNombreEmpresa.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullEmpresa.show()");
        }
    }

    public void agregarNuevoVigenciaMonedaBase() {
        boolean respueta = validarCamposNulosVigenciaMonedaBase(1);
        if (respueta == true) {
            if (validarFechaVigenciaMonedaBase(1) == true) {
                if (banderaVigencia == 1) {
                    altoTablaVigencia = "110";
                    vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
                    vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
                    vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
                    vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
                    banderaVigencia = 0;
                    filtrarListaVigenciasMonedasBases = null;
                    tipoListaVigencia = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoVigenciaMonedaBase.setSecuencia(l);
                if (tipoLista == 0) {
                    nuevoVigenciaMonedaBase.setEmpresa(listaEmpresas.get(indexAux));
                }
                if (tipoLista == 1) {
                    nuevoVigenciaMonedaBase.setEmpresa(filtrarListaEmpresas.get(indexAux));
                }
                if (listaVigenciasMonedasBases.size() == 0) {
                    listaVigenciasMonedasBases = new ArrayList<VigenciasMonedasBases>();
                }
                listVigenciasMonedasBasesCrear.add(nuevoVigenciaMonedaBase);
                listaVigenciasMonedasBases.add(nuevoVigenciaMonedaBase);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                index = indexAux;
                context.update("form:datosVigenciaMonedaBase");
                context.execute("NuevoRegistroVigencia.hide()");
                nuevoVigenciaMonedaBase = new VigenciasMonedasBases();
                nuevoVigenciaMonedaBase.setMoneda(new Monedas());
                if (guardadoVigencia == true) {
                    guardadoVigencia = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                indexVigencia = -1;
                secRegistroVigencia = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVigencia.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullVigencia.show()");
        }
    }

    public void agregarNuevoCircular() {
        boolean pte = validarCamposNulosCircular(1);
        if (pte == true) {
            if (validarFechaCircular(1) == true) {
                if (banderaCircular == 1) {
                    altoTablaCircular = "110";
                    circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
                    circularFecha.setFilterStyle("display: none; visibility: hidden;");
                    circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
                    circularExpedido.setFilterStyle("display: none; visibility: hidden;");
                    circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
                    circularContenido.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCircular");
                    banderaCircular = 0;
                    filtrarListaCirculares = null;
                    tipoListaCircular = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoCircular.setSecuencia(l);
                if (tipoLista == 0) {
                    nuevoCircular.setEmpresa(listaEmpresas.get(indexAux));
                }
                if (tipoLista == 1) {
                    nuevoCircular.setEmpresa(filtrarListaEmpresas.get(indexAux));
                }
                if (listaCirculares.size() == 0) {
                    listaCirculares = new ArrayList<Circulares>();
                }
                listCircularesCrear.add(nuevoCircular);
                listaCirculares.add(nuevoCircular);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                index = indexAux;
                context.update("form:datosCircular");
                context.execute("NuevoRegistroCircular.hide()");
                nuevoCircular = new Circulares();
                if (guardadoCircular == true) {
                    guardadoCircular = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                indexCircular = -1;
                secRegistroCircular = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasCircular.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCircular.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     */
    public void limpiarNuevaEmpresa() {
        nuevoEmpresa = new Empresas();
        nuevoEmpresa.setCentrocosto(new CentrosCostos());
        index = -1;
        secRegistro = null;
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
    }

    public void limpiarNuevaVigenciaMonedaBase() {
        nuevoVigenciaMonedaBase = new VigenciasMonedasBases();
        nuevoVigenciaMonedaBase.setMoneda(new Monedas());
        indexVigencia = -1;
        secRegistroVigencia = null;
    }

    public void limpiarNuevaCircular() {
        nuevoCircular = new Circulares();
        indexCircular = -1;
        secRegistroCircular = null;
    }

    //DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
        if (index >= 0) {
            duplicarEmpresaM();
        }
        if (indexVigencia >= 0) {
            duplicarVigenciaMonedaBaseM();
        }
        if (indexCircular >= 0) {
            duplicarCircularM();
        }
    }

    public void duplicarEmpresaM() {
        duplicarEmpresa = new Empresas();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoLista == 0) {
            duplicarEmpresa.setSecuencia(l);
            duplicarEmpresa.setCodigo(listaEmpresas.get(index).getCodigo());
            duplicarEmpresa.setNit(listaEmpresas.get(index).getNit());
            duplicarEmpresa.setNombre(listaEmpresas.get(index).getNombre());
            duplicarEmpresa.setReglamento(listaEmpresas.get(index).getReglamento());
            duplicarEmpresa.setManualadministrativo(listaEmpresas.get(index).getManualadministrativo());
            duplicarEmpresa.setLogo(listaEmpresas.get(index).getLogo());
            duplicarEmpresa.setCodigoalternativo(listaEmpresas.get(index).getCodigoalternativo());
            duplicarEmpresa.setCentrocosto(listaEmpresas.get(index).getCentrocosto());
        }
        if (tipoLista == 1) {
            duplicarEmpresa.setSecuencia(l);
            duplicarEmpresa.setCodigo(filtrarListaEmpresas.get(index).getCodigo());
            duplicarEmpresa.setNit(filtrarListaEmpresas.get(index).getNit());
            duplicarEmpresa.setNombre(filtrarListaEmpresas.get(index).getNombre());
            duplicarEmpresa.setReglamento(filtrarListaEmpresas.get(index).getReglamento());
            duplicarEmpresa.setManualadministrativo(filtrarListaEmpresas.get(index).getManualadministrativo());
            duplicarEmpresa.setLogo(filtrarListaEmpresas.get(index).getLogo());
            duplicarEmpresa.setCodigoalternativo(filtrarListaEmpresas.get(index).getCodigoalternativo());
            duplicarEmpresa.setCentrocosto(filtrarListaEmpresas.get(index).getCentrocosto());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroEmpresa");
        context.execute("DuplicarRegistroEmpresa.show()");
        index = -1;
        secRegistro = null;
        activoBtnesAdd = true;
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");

    }

    public void duplicarVigenciaMonedaBaseM() {
        duplicarVigenciaMonedaBase = new VigenciasMonedasBases();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoListaVigencia == 0) {
            duplicarVigenciaMonedaBase.setSecuencia(l);
            duplicarVigenciaMonedaBase.setFecha(listaVigenciasMonedasBases.get(indexVigencia).getFecha());
            duplicarVigenciaMonedaBase.setMoneda(listaVigenciasMonedasBases.get(indexVigencia).getMoneda());
        }
        if (tipoListaVigencia == 1) {
            duplicarVigenciaMonedaBase.setSecuencia(l);
            duplicarVigenciaMonedaBase.setFecha(filtrarListaVigenciasMonedasBases.get(indexVigencia).getFecha());
            duplicarVigenciaMonedaBase.setMoneda(filtrarListaVigenciasMonedasBases.get(indexVigencia).getMoneda());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroVigenciaMonedaBase");
        context.execute("DuplicarRegistroVigenciaMonedaBase.show()");
        indexVigencia = -1;
        secRegistroVigencia = null;

    }

    public void duplicarCircularM() {
        duplicarCircular = new Circulares();
        k++;
        l = BigInteger.valueOf(k);
        if (tipoListaCircular == 0) {
            duplicarCircular.setSecuencia(l);
            duplicarCircular.setFecha(listaCirculares.get(indexCircular).getFecha());
            duplicarCircular.setExpedidopor(listaCirculares.get(indexCircular).getExpedidopor());
            duplicarCircular.setTexto(listaCirculares.get(indexCircular).getTexto());
        }
        if (tipoListaCircular == 1) {
            duplicarCircular.setFecha(filtrarListaCirculares.get(indexCircular).getFecha());
            duplicarCircular.setExpedidopor(filtrarListaCirculares.get(indexCircular).getExpedidopor());
            duplicarCircular.setTexto(filtrarListaCirculares.get(indexCircular).getTexto());
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:DuplicarRegistroCircular");
        context.execute("DuplicarRegistroCircular.show()");
        indexCircular = -1;
        secRegistroCircular = null;

    }

    /**
     */
    public void confirmarDuplicarEmpresa() {
        boolean respueta = validarCamposNulosEmpresa(2);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoEmpresa.getNombre().length();
            if (tamDes >= 1 && tamDes <= 50) {
                if (bandera == 1) {
                    if (bandera == 1) {
                        altoTablaEmpresa = "112";
                        empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
                        empresaCodigo.setFilterStyle("display: none; visibility: hidden;");
                        empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
                        empresaNIT.setFilterStyle("display: none; visibility: hidden;");
                        empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
                        empresaNombre.setFilterStyle("display: none; visibility: hidden;");
                        empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
                        empresaReglamento.setFilterStyle("display: none; visibility: hidden;");
                        empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
                        empresaManual.setFilterStyle("display: none; visibility: hidden;");
                        empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
                        empresaLogo.setFilterStyle("display: none; visibility: hidden;");
                        empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
                        empresaCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                        empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
                        empresaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosEmpresa");
                        bandera = 0;
                        filtrarListaEmpresas = null;
                        tipoLista = 0;
                    }

                    String text = duplicarEmpresa.getNombre().toUpperCase();
                    duplicarEmpresa.setNombre(text);
                    listaEmpresas.add(duplicarEmpresa);
                    listEmpresasCrear.add(duplicarEmpresa);
                    cambiosPagina = false;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:ACEPTAR");
                    context.update("form:datosEmpresa");
                    context.execute("DuplicarRegistroEmpresa.hide()");
                    index = -1;
                    secRegistro = null;
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    duplicarEmpresa = new Empresas();
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorNombreEmpresa.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorDatosNullEmpresa.show()");
            }
        }
    }

    public void confirmarDuplicarVigenciaMonedaBase() {
        boolean respueta = validarCamposNulosVigenciaMonedaBase(2);
        if (respueta == true) {
            if (validarFechaVigenciaMonedaBase(2) == true) {
                if (banderaVigencia == 1) {
                    altoTablaVigencia = "110";
                    vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
                    vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
                    vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
                    vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
                    vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
                    banderaVigencia = 0;
                    filtrarListaVigenciasMonedasBases = null;
                    tipoListaVigencia = 0;
                }

                if (tipoLista == 0) {
                    duplicarVigenciaMonedaBase.setEmpresa(listaEmpresas.get(indexAux));
                }
                if (tipoLista == 1) {
                    duplicarVigenciaMonedaBase.setEmpresa(filtrarListaEmpresas.get(indexAux));
                }
                listaVigenciasMonedasBases.add(duplicarVigenciaMonedaBase);
                listVigenciasMonedasBasesCrear.add(duplicarVigenciaMonedaBase);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosVigenciaMonedaBase");
                context.execute("DuplicarRegistroVigenciaMonedaBase.hide()");
                indexVigencia = -1;
                secRegistroVigencia = null;
                if (guardadoVigencia == true) {
                    guardadoVigencia = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }

                duplicarVigenciaMonedaBase = new VigenciasMonedasBases();
                duplicarVigenciaMonedaBase.setMoneda(new Monedas());
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasVigencia.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullVigencia.show()");
        }
    }

    public void confirmarDuplicarCircular() {
        boolean respueta = validarCamposNulosCircular(2);
        if (respueta == true) {
            if (validarFechaCircular(2) == true) {
                if (banderaCircular == 1) {
                    altoTablaCircular = "110";
                    circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
                    circularFecha.setFilterStyle("display: none; visibility: hidden;");
                    circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
                    circularExpedido.setFilterStyle("display: none; visibility: hidden;");
                    circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
                    circularContenido.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosCircular");
                    banderaCircular = 0;
                    filtrarListaCirculares = null;
                    tipoListaCircular = 0;
                }
                if (tipoLista == 0) {
                    duplicarCircular.setEmpresa(listaEmpresas.get(indexAux));
                }
                if (tipoLista == 1) {
                    duplicarCircular.setEmpresa(filtrarListaEmpresas.get(indexAux));
                }
                listaCirculares.add(duplicarCircular);
                listCircularesCrear.add(duplicarCircular);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosCircular");
                context.execute("DuplicarRegistroCircular.hide()");
                indexCircular = -1;
                secRegistroCircular = null;
                if (guardadoCircular == true) {
                    guardadoCircular = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                duplicarCircular = new Circulares();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasCircular.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullCircular.show()");
        }
    }

    //LIMPIAR DUPLICAR
    /**
     */
    public void limpiarDuplicarEmpresa() {
        duplicarEmpresa = new Empresas();
        duplicarEmpresa.setCentrocosto(new CentrosCostos());
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
    }

    public void limpiarDuplicarVigenciaMonedaBase() {
        duplicarVigenciaMonedaBase = new VigenciasMonedasBases();
        duplicarVigenciaMonedaBase.setMoneda(new Monedas());
    }

    public void limpiarDuplicarCircular() {
        duplicarCircular = new Circulares();
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //BORRAR VC
    /**
     */
    public void verificarRegistroBorrar() {
        if (index >= 0) {
            int tam = listaVigenciasMonedasBases.size();
            int tam2 = listaCirculares.size();
            if (tam == 0 && tam2 == 0) {
                borrarEmpresa();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorBorrarRegistro.show()");
            }
        }
        if (indexVigencia >= 0) {
            borrarVigencia();
        }
        if (indexCircular >= 0) {
            borrarCircular();
        }
    }

    public void borrarEmpresa() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listEmpresasModificar.isEmpty() && listEmpresasModificar.contains(listaEmpresas.get(index))) {
                    int modIndex = listEmpresasModificar.indexOf(listaEmpresas.get(index));
                    listEmpresasModificar.remove(modIndex);
                    listEmpresasBorrar.add(listaEmpresas.get(index));
                } else if (!listEmpresasCrear.isEmpty() && listEmpresasCrear.contains(listaEmpresas.get(index))) {
                    int crearIndex = listEmpresasCrear.indexOf(listaEmpresas.get(index));
                    listEmpresasCrear.remove(crearIndex);
                } else {
                    listEmpresasBorrar.add(listaEmpresas.get(index));
                }
                listaEmpresas.remove(index);
            }
            if (tipoLista == 1) {
                if (!listEmpresasModificar.isEmpty() && listEmpresasModificar.contains(filtrarListaEmpresas.get(index))) {
                    int modIndex = listEmpresasModificar.indexOf(filtrarListaEmpresas.get(index));
                    listEmpresasModificar.remove(modIndex);
                    listEmpresasBorrar.add(filtrarListaEmpresas.get(index));
                } else if (!listEmpresasCrear.isEmpty() && listEmpresasCrear.contains(filtrarListaEmpresas.get(index))) {
                    int crearIndex = listEmpresasCrear.indexOf(filtrarListaEmpresas.get(index));
                    listEmpresasCrear.remove(crearIndex);
                } else {
                    listEmpresasBorrar.add(filtrarListaEmpresas.get(index));
                }
                int VCIndex = listaEmpresas.indexOf(filtrarListaEmpresas.get(index));
                listaEmpresas.remove(VCIndex);
                filtrarListaEmpresas.remove(index);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEmpresa");
            index = -1;
            secRegistro = null;
            activoBtnesAdd = true;
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarVigencia() {
        if (indexVigencia >= 0) {
            if (tipoListaVigencia == 0) {
                if (!listVigenciasMonedasBasesModificar.isEmpty() && listVigenciasMonedasBasesModificar.contains(listaVigenciasMonedasBases.get(indexVigencia))) {
                    int modIndex = listVigenciasMonedasBasesModificar.indexOf(listaVigenciasMonedasBases.get(indexVigencia));
                    listVigenciasMonedasBasesModificar.remove(modIndex);
                    listVigenciasMonedasBasesBorrar.add(listaVigenciasMonedasBases.get(indexVigencia));
                } else if (!listVigenciasMonedasBasesCrear.isEmpty() && listVigenciasMonedasBasesCrear.contains(listaVigenciasMonedasBases.get(indexVigencia))) {
                    int crearIndex = listVigenciasMonedasBasesCrear.indexOf(listaVigenciasMonedasBases.get(indexVigencia));
                    listVigenciasMonedasBasesCrear.remove(crearIndex);
                } else {
                    listVigenciasMonedasBasesBorrar.add(listaVigenciasMonedasBases.get(indexVigencia));
                }
                listaVigenciasMonedasBases.remove(indexVigencia);
            }
            if (tipoListaVigencia == 1) {
                if (!listVigenciasMonedasBasesModificar.isEmpty() && listVigenciasMonedasBasesModificar.contains(filtrarListaVigenciasMonedasBases.get(indexVigencia))) {
                    int modIndex = listVigenciasMonedasBasesModificar.indexOf(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                    listVigenciasMonedasBasesModificar.remove(modIndex);
                    listVigenciasMonedasBasesBorrar.add(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                } else if (!listVigenciasMonedasBasesCrear.isEmpty() && listVigenciasMonedasBasesCrear.contains(filtrarListaVigenciasMonedasBases.get(indexVigencia))) {
                    int crearIndex = listVigenciasMonedasBasesCrear.indexOf(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                    listVigenciasMonedasBasesCrear.remove(crearIndex);
                } else {
                    listVigenciasMonedasBasesBorrar.add(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                }
                int VCIndex = listaVigenciasMonedasBases.indexOf(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                listaVigenciasMonedasBases.remove(VCIndex);
                filtrarListaVigenciasMonedasBases.remove(indexVigencia);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosVigenciaMonedaBase");
            indexVigencia = -1;
            secRegistroVigencia = null;

            if (guardadoVigencia == true) {
                guardadoVigencia = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarCircular() {
        if (indexCircular >= 0) {
            if (tipoListaCircular == 0) {
                if (!listCircularesModificar.isEmpty() && listCircularesModificar.contains(listaCirculares.get(indexCircular))) {
                    int modIndex = listCircularesModificar.indexOf(listaCirculares.get(indexCircular));
                    listCircularesModificar.remove(modIndex);
                    listCircularesBorrar.add(listaCirculares.get(indexCircular));
                } else if (!listCircularesCrear.isEmpty() && listCircularesCrear.contains(listaCirculares.get(indexCircular))) {
                    int crearIndex = listCircularesCrear.indexOf(listaCirculares.get(indexCircular));
                    listCircularesCrear.remove(crearIndex);
                } else {
                    listCircularesBorrar.add(listaCirculares.get(indexCircular));
                }
                listaCirculares.remove(indexCircular);
            }
            if (tipoListaCircular == 1) {
                if (!listCircularesModificar.isEmpty() && listCircularesModificar.contains(filtrarListaCirculares.get(indexCircular))) {
                    int modIndex = listCircularesModificar.indexOf(filtrarListaCirculares.get(indexCircular));
                    listCircularesModificar.remove(modIndex);
                    listCircularesBorrar.add(filtrarListaCirculares.get(indexCircular));
                } else if (!listCircularesCrear.isEmpty() && listCircularesCrear.contains(filtrarListaCirculares.get(indexCircular))) {
                    int crearIndex = listCircularesCrear.indexOf(filtrarListaCirculares.get(indexCircular));
                    listCircularesCrear.remove(crearIndex);
                } else {
                    listCircularesBorrar.add(filtrarListaCirculares.get(indexCircular));
                }
                int VCIndex = listaCirculares.indexOf(filtrarListaCirculares.get(indexCircular));
                listaCirculares.remove(VCIndex);
                filtrarListaCirculares.remove(indexCircular);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosCircular");
            indexCircular = -1;
            secRegistroCircular = null;

            if (guardadoCircular == true) {
                guardadoCircular = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (index >= 0) {
            if (bandera == 0) {
                altoTablaEmpresa = "90";
                empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
                empresaCodigo.setFilterStyle("width: 65px");
                empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
                empresaNIT.setFilterStyle("width: 65px");
                empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
                empresaNombre.setFilterStyle("width: 65px");
                empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
                empresaReglamento.setFilterStyle("width: 65px");
                empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
                empresaManual.setFilterStyle("width: 65px");
                empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
                empresaLogo.setFilterStyle("width: 65px");
                empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
                empresaCentroCosto.setFilterStyle("width: 65px");
                empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
                empresaCodigoAlternativo.setFilterStyle("width: 65px");
                RequestContext.getCurrentInstance().update("form:datosEmpresa");
                bandera = 1;
            } else if (bandera == 1) {
                altoTablaEmpresa = "112";
                empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
                empresaCodigo.setFilterStyle("display: none; visibility: hidden;");
                empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
                empresaNIT.setFilterStyle("display: none; visibility: hidden;");
                empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
                empresaNombre.setFilterStyle("display: none; visibility: hidden;");
                empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
                empresaReglamento.setFilterStyle("display: none; visibility: hidden;");
                empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
                empresaManual.setFilterStyle("display: none; visibility: hidden;");
                empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
                empresaLogo.setFilterStyle("display: none; visibility: hidden;");
                empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
                empresaCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
                empresaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEmpresa");
                bandera = 0;
                filtrarListaEmpresas = null;
                tipoLista = 0;
            }
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        }
        if (indexVigencia >= 0) {
            if (banderaVigencia == 0) {
                altoTablaVigencia = "88";
                vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
                vigenciaCodigo.setFilterStyle("width: 80px");
                vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
                vigenciaFecha.setFilterStyle("width: 70px");
                vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
                vigenciaMoneda.setFilterStyle("width: 70px");
                RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
                banderaVigencia = 1;
            } else if (banderaVigencia == 1) {
                altoTablaVigencia = "110";
                vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
                vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
                vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
                vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
                vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
                vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
                banderaVigencia = 0;
                filtrarListaVigenciasMonedasBases = null;
                tipoListaVigencia = 0;
            }
        }
        if (indexCircular >= 0) {
            if (banderaCircular == 0) {
                altoTablaCircular = "88";
                circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
                circularFecha.setFilterStyle("width: 100px");
                circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
                circularExpedido.setFilterStyle("width: 100px");
                circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
                circularContenido.setFilterStyle("width: 100px");
                RequestContext.getCurrentInstance().update("form:datosCircular");
                banderaCircular = 1;
            } else if (banderaCircular == 1) {
                altoTablaCircular = "110";
                circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
                circularFecha.setFilterStyle("display: none; visibility: hidden;");
                circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
                circularExpedido.setFilterStyle("display: none; visibility: hidden;");
                circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
                circularContenido.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCircular");
                banderaCircular = 0;
                filtrarListaCirculares = null;
                tipoListaCircular = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            altoTablaEmpresa = "112";
            empresaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigo");
            empresaCodigo.setFilterStyle("display: none; visibility: hidden;");
            empresaNIT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNIT");
            empresaNIT.setFilterStyle("display: none; visibility: hidden;");
            empresaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaNombre");
            empresaNombre.setFilterStyle("display: none; visibility: hidden;");
            empresaReglamento = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaReglamento");
            empresaReglamento.setFilterStyle("display: none; visibility: hidden;");
            empresaManual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaManual");
            empresaManual.setFilterStyle("display: none; visibility: hidden;");
            empresaLogo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaLogo");
            empresaLogo.setFilterStyle("display: none; visibility: hidden;");
            empresaCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCentroCosto");
            empresaCentroCosto.setFilterStyle("display: none; visibility: hidden;");
            empresaCodigoAlternativo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEmpresa:empresaCodigoAlternativo");
            empresaCodigoAlternativo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEmpresa");
            bandera = 0;
            filtrarListaEmpresas = null;
            tipoLista = 0;
        }
        if (banderaVigencia == 1) {
            altoTablaVigencia = "110";
            vigenciaCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaCodigo");
            vigenciaCodigo.setFilterStyle("display: none; visibility: hidden;");
            vigenciaFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaFecha");
            vigenciaFecha.setFilterStyle("display: none; visibility: hidden;");
            vigenciaMoneda = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaMonedaBase:vigenciaMoneda");
            vigenciaMoneda.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaMonedaBase");
            banderaVigencia = 0;
            filtrarListaVigenciasMonedasBases = null;
            tipoListaVigencia = 0;
        }
        if (banderaCircular == 1) {
            altoTablaCircular = "110";
            circularFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularFecha");
            circularFecha.setFilterStyle("display: none; visibility: hidden;");
            circularExpedido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCircular:circularExpedido");
            circularExpedido.setFilterStyle("display: none; visibility: hidden;");
            circularContenido = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:circularContenido:circularFecha");
            circularContenido.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosCircular");
            banderaCircular = 0;
            filtrarListaCirculares = null;
            tipoListaCircular = 0;
        }
        listEmpresasBorrar.clear();
        listEmpresasCrear.clear();
        listEmpresasModificar.clear();
        listVigenciasMonedasBasesBorrar.clear();
        listVigenciasMonedasBasesCrear.clear();
        listVigenciasMonedasBasesModificar.clear();
        listCircularesBorrar.clear();
        listCircularesCrear.clear();
        listCircularesModificar.clear();
        index = -1;
        indexAux = -1;
        indexVigencia = -1;
        indexCircular = -1;
        secRegistro = null;
        secRegistroVigencia = null;
        secRegistroCircular = null;
        k = 0;
        listaEmpresas = null;
        listaVigenciasMonedasBases = null;
        listaCirculares = null;
        guardado = true;
        guardadoVigencia = true;
        guardadoCircular = true;
        cambiosPagina = true;
        lovMonedas = null;
        lovCentrosCostos = null;
        activoBtnesAdd = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:DETALLES");
        context.update("form:EMPRESABANCO");
        context.update("form:DIRECCIONES");
        context.update("form:ACEPTAR");
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 6) {
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (indexVigencia >= 0) {
            if (cualCeldaVigencia == 1) {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCeldaVigencia == 2) {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND, int tabla) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tabla == 0) {
            if (LND == 0) {
                indexVigencia = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
            }
        }
        if (tabla == 1) {
            if (LND == 0) {
                indexCircular = indice;
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dlg == 0) {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
            }
            if (dlg == 0) {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
            }
        }
    }

    public void valoresBackupAutocompletarGeneral(int tipoNuevo, String Campo) {
        if (Campo.equals("MONEDA")) {
            if (tipoNuevo == 1) {
                moneda = nuevoVigenciaMonedaBase.getMoneda().getNombre();
            } else if (tipoNuevo == 2) {
                moneda = duplicarVigenciaMonedaBase.getMoneda().getNombre();
            }
        }
        if (Campo.equals("CODIGOMONEDA")) {
            if (tipoNuevo == 1) {
                codigoMoneda = nuevoVigenciaMonedaBase.getMoneda().getCodigo();
            } else if (tipoNuevo == 2) {
                codigoMoneda = duplicarVigenciaMonedaBase.getMoneda().getCodigo();
            }
        }
        if (Campo.equals("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                centroCosto = nuevoEmpresa.getCentrocosto().getNombre();
            } else if (tipoNuevo == 2) {
                centroCosto = duplicarEmpresa.getCentrocosto().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoVigenciaMonedaBase(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MONEDA")) {
            if (tipoNuevo == 1) {
                nuevoVigenciaMonedaBase.getMoneda().setNombre(moneda);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaMonedaBase.getMoneda().setNombre(moneda);
            }
            for (int i = 0; i < lovMonedas.size(); i++) {
                if (lovMonedas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoVigenciaMonedaBase.setMoneda(lovMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseMoneda");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaMonedaBase.setMoneda(lovMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseMoneda");
                }
                lovMonedas.clear();
                getLovMonedas();
            } else {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseMoneda");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseMoneda");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("CODIGOMONEDA")) {
            if (tipoNuevo == 1) {
                nuevoVigenciaMonedaBase.getMoneda().setCodigo(codigoMoneda);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaMonedaBase.getMoneda().setCodigo(codigoMoneda);
            }
            for (int i = 0; i < lovMonedas.size(); i++) {
                if (lovMonedas.get(i).getCodigo().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoVigenciaMonedaBase.setMoneda(lovMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseMoneda");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaMonedaBase.setMoneda(lovMonedas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseMoneda");
                }
                lovMonedas.clear();
                getLovMonedas();
            } else {
                context.update("form:MonedaDialogo");
                context.execute("MonedaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:nuevoVigenciaMonedaBaseMoneda");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseCodigo");
                    context.update("formularioDialogos:duplicarVigenciaMonedaBaseMoneda");
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicadoEmpresa(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CENTROCOSTO")) {
            if (tipoNuevo == 1) {
                nuevoEmpresa.getCentrocosto().setNombre(centroCosto);
            } else if (tipoNuevo == 2) {
                duplicarEmpresa.getCentrocosto().setNombre(centroCosto);
            }
            for (int i = 0; i < lovCentrosCostos.size(); i++) {
                if (lovCentrosCostos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoEmpresa.setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoEmpresaCentroCosto");
                } else if (tipoNuevo == 2) {
                    duplicarEmpresa.setCentrocosto(lovCentrosCostos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarEmpresaCentroCosto");
                }
                lovCentrosCostos.clear();
                getLovCentrosCostos();
            } else {
                context.update("form:CentroCostoDialogo");
                context.execute("CentroCostoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoEmpresaCentroCosto");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarEmpresaCentroCosto");
                }
            }
        }
    }

    public void actualizarMoneda() {
        if (tipoActualizacion == 0) {
            if (tipoListaVigencia == 0) {
                listaVigenciasMonedasBases.get(indexVigencia).setMoneda(monedaSeleccionado);
                if (!listVigenciasMonedasBasesCrear.contains(listaVigenciasMonedasBases.get(indexVigencia))) {
                    if (listVigenciasMonedasBasesModificar.isEmpty()) {
                        listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indexVigencia));
                    } else if (!listVigenciasMonedasBasesModificar.contains(listaVigenciasMonedasBases.get(indexVigencia))) {
                        listVigenciasMonedasBasesModificar.add(listaVigenciasMonedasBases.get(indexVigencia));
                    }
                }
            } else {
                filtrarListaVigenciasMonedasBases.get(indexVigencia).setMoneda(monedaSeleccionado);
                if (!listVigenciasMonedasBasesCrear.contains(filtrarListaVigenciasMonedasBases.get(indexVigencia))) {
                    if (listVigenciasMonedasBasesModificar.isEmpty()) {
                        listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                    } else if (!listVigenciasMonedasBasesModificar.contains(filtrarListaVigenciasMonedasBases.get(indexVigencia))) {
                        listVigenciasMonedasBasesModificar.add(filtrarListaVigenciasMonedasBases.get(indexVigencia));
                    }
                }
            }
            if (guardadoVigencia == true) {
                guardadoVigencia = false;
            }
            permitirIndexVigencia = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosVigenciaMonedaBase");
        } else if (tipoActualizacion == 1) {
            nuevoVigenciaMonedaBase.setMoneda(monedaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoVigenciaMonedaBaseCodigo");
            context.update("formularioDialogos:nuevoVigenciaMonedaBaseMoneda");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaMonedaBase.setMoneda(monedaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigenciaMonedaBaseCodigo");
            context.update("formularioDialogos:duplicarVigenciaMonedaBaseMoneda");
        }
        filtrarLovMonedas = null;
        monedaSeleccionado = null;
        aceptar = true;
        indexVigencia = -1;
        secRegistroVigencia = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:MonedaDialogo");
        context.update("form:lovMoneda");
        context.update("form:aceptarM");
        context.execute("MonedaDialogo.hide()");
    }

    public void cancelarCambioMoneda() {
        filtrarLovMonedas = null;
        monedaSeleccionado = null;
        aceptar = true;
        indexVigencia = -1;
        secRegistroVigencia = null;
        tipoActualizacion = -1;
        permitirIndexVigencia = true;
    }

    public void actualizarCentroCosto() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaEmpresas.get(index).setCentrocosto(centroCostoSeleccionado);
                if (!listEmpresasCrear.contains(listaEmpresas.get(index))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(listaEmpresas.get(index));
                    } else if (!listEmpresasModificar.contains(listaEmpresas.get(index))) {
                        listEmpresasModificar.add(listaEmpresas.get(index));
                    }
                }
            } else {
                filtrarListaEmpresas.get(index).setCentrocosto(centroCostoSeleccionado);
                if (!listEmpresasCrear.contains(filtrarListaEmpresas.get(index))) {
                    if (listEmpresasModificar.isEmpty()) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(index));
                    } else if (!listEmpresasModificar.contains(filtrarListaEmpresas.get(index))) {
                        listEmpresasModificar.add(filtrarListaEmpresas.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosEmpresa");
        } else if (tipoActualizacion == 1) {
            nuevoEmpresa.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoEmpresaCentroCosto");
        } else if (tipoActualizacion == 2) {
            duplicarEmpresa.setCentrocosto(centroCostoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEmpresaCentroCosto");
        }
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CentroCostoDialogo");
        context.update("form:lovCentroCosto");
        context.update("form:aceptarCC");
        context.execute("CentroCostoDialogo.hide()");
    }

    public void cancelarCambioCentroCosto() {
        filtrarLovCentrosCostos = null;
        centroCostoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public String exportXML() {
        if (index >= 0) {
            nombreTabla = ":formExportarEMP:datosEmpresaExportar";
            nombreXML = "Empresas_XML";
        }
        if (indexVigencia >= 0) {
            nombreTabla = ":formExportarVMB:datosVigenciaMonedaBaseExportar";
            nombreXML = "ConceptosJuridicos_XML";
        }
        if (indexCircular >= 0) {
            nombreTabla = ":formExportarC:datosCircularExportar";
            nombreXML = "Circulares_XML";
        }
        return nombreTabla;
    }

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (index >= 0) {
            exportPDF_E();
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        }
        if (indexVigencia >= 0) {
            exportPDF_VMB();
        }
        if (indexCircular >= 0) {
            exportPDF_C();
        }
    }

    public void exportPDF_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarEMP:datosEmpresaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Empresas_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;

    }

    public void exportPDF_VMB() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVMB:datosVigenciaMonedaBaseExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ConceptosJuridicos_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVigencia = -1;
        secRegistroVigencia = null;
    }

    public void exportPDF_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarC:datosCircularExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "Circulares_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexCircular = -1;
        secRegistroCircular = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLS_E();
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        }
        if (indexVigencia >= 0) {
            exportXLS_VMB();
        }
        if (indexCircular >= 0) {
            exportXLS_C();
        }
    }

    public void exportXLS_E() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarEMP:datosEmpresaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Empresas_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS_VMB() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVMB:datosVigenciaMonedaBaseExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ConceptosJuridicos_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVigencia = -1;
        secRegistroVigencia = null;
    }

    public void exportXLS_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarC:datosCircularExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "Circulares_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexCircular = -1;
        secRegistroCircular = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
        if (indexVigencia >= 0) {
            if (tipoListaVigencia == 0) {
                tipoListaVigencia = 1;
            }
        }
        if (indexCircular >= 0) {
            if (tipoListaCircular == 0) {
                tipoListaCircular = 1;
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        int tam = listaEmpresas.size();
        int tam1 = listaVigenciasMonedasBases.size();
        int tam2 = listaCirculares.size();
        if (tam == 0 || tam1 == 0 || tam2 == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("verificarRastrosTablas.show()");
        } else {
            if (index >= 0) {
                verificarRastroEmpresa();
                index = -1;
                activoBtnesAdd = true;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:DETALLES");
                context.update("form:EMPRESABANCO");
                context.update("form:DIRECCIONES");
            }
            if (indexVigencia >= 0) {
                verificarRastroVigencia();
                indexVigencia = -1;
            }
            if (indexCircular >= 0) {
                verificarRastroCircular();
                indexCircular = -1;
            }
        }
    }

    public void verificarRastroEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaEmpresas != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EMPRESAS");
                backUpSecRegistro = secRegistro;
                backUp = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Empresas";
                    msnConfirmarRastro = "La tabla EMPRESAS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("EMPRESAS")) {
                nombreTablaRastro = "Empresas";
                msnConfirmarRastroHistorico = "La tabla EMPRESAS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroVigencia() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaVigenciasMonedasBases != null) {
            if (secRegistroVigencia != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigencia, "VIGENCIASMONEDASBASES");
                backUpSecRegistroVigencia = secRegistroVigencia;
                backUp = secRegistroVigencia;
                secRegistroVigencia = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasMonedasBases";
                    msnConfirmarRastro = "La tabla VIGENCIASMONEDASBASES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASMONEDASBASES")) {
                nombreTablaRastro = "VigenciasMonedasBases";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASMONEDASBASES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexVigencia = -1;
    }

    public void verificarRastroCircular() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaCirculares != null) {
            if (secRegistroCircular != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroCircular, "CIRCULARES");
                backUpSecRegistroCircular = secRegistroCircular;
                backUp = backUpSecRegistroCircular;
                secRegistroCircular = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Circulares";
                    msnConfirmarRastro = "La tabla CIRCULARES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("CIRCULARES")) {
                nombreTablaRastro = "Circulares";
                msnConfirmarRastroHistorico = "La tabla CIRCULARES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexCircular = -1;
    }

    public void cancelarModificacionClonado() {
        RequestContext context = RequestContext.getCurrentInstance();
        empresaOrigenClonado = new Empresas();
        empresaDestinoClonado = new Empresas();
        activoCasillaClonado = true;
        context.update("form:CodigoNuevoClonado");
        context.update("form:DescripcionNuevoClonado");
        context.update("form:CodigoBaseClonado");
        context.update("form:DescripcionBaseClonado");

    }

    public void dispararDialogoEmpresaOrigenClonado() {
        variableClonado = "origen";
        lovEmpresas = null;
        getLovEmpresas();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpresaDialogo");
        context.execute("EmpresaDialogo.show()");

    }

    public void dispararDialogoEmpresaDestinoClonado() {
        variableClonado = "destino";
        lovEmpresas = null;
        getLovEmpresas();
        int indice = lovEmpresas.indexOf(empresaOrigenClonado);
        lovEmpresas.remove(indice);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:EmpresaDialogo");
        context.execute("EmpresaDialogo.show()");
    }

    public void actualizarEmpresasClonado() {
        if (variableClonado.equalsIgnoreCase("origen")) {
            System.out.println("Origen");
            actualizarEmpresaOrigenClonado();
        } else if (variableClonado.equalsIgnoreCase("destino")) {
            System.out.println("Destino");
            actualizarEmpresaDestinoClonado();
        }
        aceptar = true;
    }

    public void actualizarEmpresaOrigenClonado() {
        empresaOrigenClonado = empresaSeleccionada;
        filtrarLovEmpresas = null;
        empresaSeleccionada = null;
        activoCasillaClonado = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CodigoNuevoClonado");
        context.update("form:DescripcionNuevoClonado");
        context.update("form:CodigoBaseClonado");
        context.update("form:DescripcionBaseClonado");
        context.update("form:EmpresaDialogo");
        context.update("form:lovEmpresa");
        context.update("form:aceptarEmp");
        context.execute("EmpresaDialogo.hide()");
    }

    public void actualizarEmpresaDestinoClonado() {
        empresaDestinoClonado = empresaSeleccionada;
        filtrarLovEmpresas = null;
        empresaSeleccionada = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:CodigoBaseClonado");
        context.update("form:DescripcionBaseClonado");
        context.update("form:EmpresaDialogo");
        context.update("form:lovEmpresa");
        context.update("form:aceptarEmp");
        context.execute("EmpresaDialogo.hide()");
    }

    public void cancelarSeleccionEmpresa() {
        filtrarLovEmpresas = null;
        empresaSeleccionada = null;
    }

    public void validarProcesoClonado() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (empresaDestinoClonado.getSecuencia() != null && empresaOrigenClonado.getSecuencia() != null) {
            empresaOrigenClonado = new Empresas();
            empresaDestinoClonado = new Empresas();
            activoCasillaClonado = true;
            context.update("form:CodigoBaseClonado");
            context.update("form:DescripcionBaseClonado");
            System.out.println("Proceso Clonado en Proceso");
        } else {
            context.execute("errorProcesoClonado.show()");
        }
    }

    public void posicionProcesoClonadoOrigen() {
        if (guardado == true) {
            index = -1;
            indexCircular = -1;
            indexVigencia = -1;
            indexClonadoOrigen = 1;
            indexClonadoDestino = -1;
            auxClonadoCodigoOrigen = empresaOrigenClonado.getCodigo();
            auxClonadoNombreOrigen = empresaOrigenClonado.getNombre();
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void posicionProcesoClonadoDestino() {
        if (guardado == true) {
            index = -1;
            indexCircular = -1;
            indexVigencia = -1;
            indexClonadoOrigen = -1;
            indexClonadoDestino = 1;
            auxClonadoCodigoDestino = empresaDestinoClonado.getCodigo();
            auxClonadoNombreDestino = empresaDestinoClonado.getNombre();
            activoBtnesAdd = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:DETALLES");
            context.update("form:EMPRESABANCO");
            context.update("form:DIRECCIONES");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void autoCompletarEmpresaOrigenClonado(String valorConfirmar, int tipoAutoCompletar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoAutoCompletar == 0) {
            short num = Short.parseShort(valorConfirmar);
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getCodigo() == num) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                empresaOrigenClonado = lovEmpresas.get(indiceUnicoElemento);
                lovEmpresas.clear();
                getLovEmpresas();
            } else {
                empresaOrigenClonado.setCodigo(auxClonadoCodigoOrigen);
                empresaOrigenClonado.setNombre(auxClonadoNombreOrigen);
                context.update("form:CodigoNuevoClonado");
                context.update("form:DescripcionNuevoClonado");
                dispararDialogoEmpresaOrigenClonado();
            }
        }
        if (tipoAutoCompletar == 1) {
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                empresaOrigenClonado = lovEmpresas.get(indiceUnicoElemento);
                lovEmpresas.clear();
                getLovEmpresas();
            } else {
                empresaOrigenClonado.setCodigo(auxClonadoCodigoOrigen);
                empresaOrigenClonado.setNombre(auxClonadoNombreOrigen);
                context.update("form:CodigoNuevoClonado");
                context.update("form:DescripcionNuevoClonado");
                dispararDialogoEmpresaOrigenClonado();
            }
        }
    }

    public void autoCompletarEmpresaDestinoClonado(String valorConfirmar, int tipoAutoCompletar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoAutoCompletar == 0) {
            short num = Short.parseShort(valorConfirmar);
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getCodigo() == num) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                empresaOrigenClonado = lovEmpresas.get(indiceUnicoElemento);
                lovEmpresas.clear();
                getLovEmpresas();
            } else {
                empresaDestinoClonado.setCodigo(auxClonadoCodigoDestino);
                empresaDestinoClonado.setNombre(auxClonadoNombreDestino);
                context.update("form:CodigoNuevoClonado");
                context.update("form:DescripcionNuevoClonado");
                dispararDialogoEmpresaDestinoClonado();
            }
        }
        if (tipoAutoCompletar == 1) {
            for (int i = 0; i < lovEmpresas.size(); i++) {
                if (lovEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                empresaOrigenClonado = lovEmpresas.get(indiceUnicoElemento);
                lovEmpresas.clear();
                getLovEmpresas();
            } else {
                empresaDestinoClonado.setCodigo(auxClonadoCodigoDestino);
                empresaDestinoClonado.setNombre(auxClonadoNombreDestino);
                context.update("form:CodigoNuevoClonado");
                context.update("form:DescripcionNuevoClonado");
                dispararDialogoEmpresaDestinoClonado();
            }
        }
    }

    //GETTERS AND SETTERS
    public List<Empresas> getListaEmpresas() {
        try {
            if (listaEmpresas == null) {
                listaEmpresas = new ArrayList<Empresas>();
                listaEmpresas = administrarEmpresa.listaEmpresas();
                for (int i = 0; i < listaEmpresas.size(); i++) {
                    if (listaEmpresas.get(i).getCentrocosto() == null) {
                        listaEmpresas.get(i).setCentrocosto(new CentrosCostos());
                    }
                }
            }

            return listaEmpresas;

        } catch (Exception e) {
            System.out.println("Error...!! getListaEmpresas " + e.toString());
            return null;
        }
    }

    public void setListaEmpresas(List<Empresas> setListaEmpresas) {
        this.listaEmpresas = setListaEmpresas;
    }

    public List<Empresas> getFiltrarListaEmpresas() {
        return filtrarListaEmpresas;
    }

    public void setFiltrarListaEmpresas(List<Empresas> setFiltrarListaEmpresas) {
        this.filtrarListaEmpresas = setFiltrarListaEmpresas;
    }

    public Empresas getNuevoEmpresa() {
        return nuevoEmpresa;
    }

    public void setNuevoEmpresa(Empresas setNuevoEmpresa) {
        this.nuevoEmpresa = setNuevoEmpresa;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Empresas getEditarEmpresa() {
        return editarEmpresa;
    }

    public void setEditarEmpresa(Empresas setEditarEmpresa) {
        this.editarEmpresa = setEditarEmpresa;
    }

    public Empresas getDuplicarEmpresa() {
        return duplicarEmpresa;
    }

    public void setDuplicarEmpresa(Empresas setDuplicarEmpresa) {
        this.duplicarEmpresa = setDuplicarEmpresa;
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

    public void setBackUpSecRegistro(BigInteger BackUpSecRegistro) {
        this.backUpSecRegistro = BackUpSecRegistro;
    }

    public List<VigenciasMonedasBases> getListaVigenciasMonedasBases() {
        if (listaVigenciasMonedasBases == null) {
            listaVigenciasMonedasBases = new ArrayList<VigenciasMonedasBases>();
            if (index == -1) {
                index = indexAux;
            }
            if (tipoLista == 0) {
                listaVigenciasMonedasBases = administrarEmpresa.listaVigenciasMonedasBasesParaEmpresa(listaEmpresas.get(index).getSecuencia());
            }
            if (tipoLista == 1) {
                listaVigenciasMonedasBases = administrarEmpresa.listaVigenciasMonedasBasesParaEmpresa(filtrarListaEmpresas.get(index).getSecuencia());
            }
            for (int i = 0; i < listaVigenciasMonedasBases.size(); i++) {
                if (listaVigenciasMonedasBases.get(i).getMoneda() == null) {
                    listaVigenciasMonedasBases.get(i).setMoneda(new Monedas());
                }
            }
        }
        return listaVigenciasMonedasBases;
    }

    public void setListaVigenciasMonedasBases(List<VigenciasMonedasBases> setListaVigenciasMonedasBases) {
        this.listaVigenciasMonedasBases = setListaVigenciasMonedasBases;
    }

    public List<VigenciasMonedasBases> getFiltrarListaVigenciasMonedasBases() {
        return filtrarListaVigenciasMonedasBases;
    }

    public void setFiltrarListaVigenciasMonedasBases(List<VigenciasMonedasBases> setFiltrarListaVigenciasMonedasBases) {
        this.filtrarListaVigenciasMonedasBases = setFiltrarListaVigenciasMonedasBases;
    }

    public List<Empresas> getListEmpresasModificar() {
        return listEmpresasModificar;
    }

    public void setListEmpresasModificar(List<Empresas> setListEmpresasModificar) {
        this.listEmpresasModificar = setListEmpresasModificar;
    }

    public List<Empresas> getListEmpresasCrear() {
        return listEmpresasCrear;
    }

    public void setListEmpresasCrear(List<Empresas> setListEmpresasCrear) {
        this.listEmpresasCrear = setListEmpresasCrear;
    }

    public List<Empresas> getListEmpresasBorrar() {
        return listEmpresasBorrar;
    }

    public void setListEmpresasBorrar(List<Empresas> setListEmpresasBorrar) {
        this.listEmpresasBorrar = setListEmpresasBorrar;
    }

    public List<VigenciasMonedasBases> getListVigenciasMonedasBasesModificar() {
        return listVigenciasMonedasBasesModificar;
    }

    public void setListVigenciasMonedasBasesModificar(List<VigenciasMonedasBases> setListVigenciasMonedasBasesModificar) {
        this.listVigenciasMonedasBasesModificar = setListVigenciasMonedasBasesModificar;
    }

    public VigenciasMonedasBases getNuevoVigenciaMonedaBase() {
        return nuevoVigenciaMonedaBase;
    }

    public void setNuevoVigenciaMonedaBase(VigenciasMonedasBases setNuevoVigenciaMonedaBase) {
        this.nuevoVigenciaMonedaBase = setNuevoVigenciaMonedaBase;
    }

    public List<VigenciasMonedasBases> getListVigenciasMonedasBasesCrear() {
        return listVigenciasMonedasBasesCrear;
    }

    public void setListVigenciasMonedasBasesCrear(List<VigenciasMonedasBases> setListVigenciasMonedasBasesCrear) {
        this.listVigenciasMonedasBasesCrear = setListVigenciasMonedasBasesCrear;
    }

    public List<VigenciasMonedasBases> getLisVigenciasMonedasBasesBorrar() {
        return listVigenciasMonedasBasesBorrar;
    }

    public void setListVigenciasMonedasBasesBorrar(List<VigenciasMonedasBases> setListVigenciasMonedasBasesBorrar) {
        this.listVigenciasMonedasBasesBorrar = setListVigenciasMonedasBasesBorrar;
    }

    public VigenciasMonedasBases getEditarVigenciaMonedaBase() {
        return editarVigenciaMonedaBase;
    }

    public void setEditarVigenciaMonedaBase(VigenciasMonedasBases setEditarVigenciaMonedaBase) {
        this.editarVigenciaMonedaBase = setEditarVigenciaMonedaBase;
    }

    public VigenciasMonedasBases getDuplicarVigenciaMonedaBase() {
        return duplicarVigenciaMonedaBase;
    }

    public void setDuplicarVigenciaMonedaBase(VigenciasMonedasBases setDuplicarVigenciaMonedaBase) {
        this.duplicarVigenciaMonedaBase = setDuplicarVigenciaMonedaBase;
    }

    public BigInteger getSecRegistroVigencia() {
        return secRegistroVigencia;
    }

    public void setSecRegistroVigencia(BigInteger setSecRegistroVigencia) {
        this.secRegistroVigencia = setSecRegistroVigencia;
    }

    public BigInteger getBackUpSecRegistroVigencia() {
        return backUpSecRegistroVigencia;
    }

    public void setBackUpSecRegistroVigencia(BigInteger setBackUpSecRegistroVigencia) {
        this.backUpSecRegistroVigencia = setBackUpSecRegistroVigencia;
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

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String setNombreTabla) {
        this.nombreTabla = setNombreTabla;
    }

    public List<CentrosCostos> getLovCentrosCostos() {
        if (lovCentrosCostos == null) {
            lovCentrosCostos = administrarEmpresa.lovCentrosCostos();
        }
        return lovCentrosCostos;
    }

    public void setLovCentrosCostos(List<CentrosCostos> setLovCentrosCostos) {
        this.lovCentrosCostos = setLovCentrosCostos;
    }

    public List<CentrosCostos> getFiltrarLovCentrosCostos() {
        return filtrarLovCentrosCostos;
    }

    public void setFiltrarLovCentrosCostos(List<CentrosCostos> setFiltrarLovCentrosCostos) {
        this.filtrarLovCentrosCostos = setFiltrarLovCentrosCostos;
    }

    public CentrosCostos getCentroCostoSeleccionado() {
        return centroCostoSeleccionado;
    }

    public void setCentroCostoSeleccionado(CentrosCostos setCentroCostoSeleccionado) {
        this.centroCostoSeleccionado = setCentroCostoSeleccionado;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean setCambiosPagina) {
        this.cambiosPagina = setCambiosPagina;
    }

    public String getAltoTablaEmpresa() {
        return altoTablaEmpresa;
    }

    public void setAltoTablaEmpresa(String setAltoTablaEmpresa) {
        this.altoTablaEmpresa = setAltoTablaEmpresa;
    }

    public String getAltoTablaVigencia() {
        return altoTablaVigencia;
    }

    public void setAltoTablaVigencia(String setAltoTablaVigencia) {
        this.altoTablaVigencia = setAltoTablaVigencia;
    }

    public List<Monedas> getLovMonedas() {
        if (lovMonedas == null) {
            lovMonedas = administrarEmpresa.lovMonedas();
        }
        return lovMonedas;
    }

    public void setLovMonedas(List<Monedas> setLovMonedas) {
        this.lovMonedas = setLovMonedas;
    }

    public List<Monedas> getFiltrarLovMonedas() {
        return filtrarLovMonedas;
    }

    public void setFiltrarLovMonedas(List<Monedas> setFiltrarLovMonedas) {
        this.filtrarLovMonedas = setFiltrarLovMonedas;
    }

    public Monedas getMonedaSeleccionado() {
        return monedaSeleccionado;
    }

    public void setMonedaSeleccionado(Monedas setMonedaSeleccionado) {
        this.monedaSeleccionado = setMonedaSeleccionado;
    }

    public List<Circulares> getListaCirculares() {
        try {
            if (listaCirculares == null) {
                listaCirculares = new ArrayList<Circulares>();
                if (index == -1) {
                    index = indexAux;
                }
                if (tipoLista == 0) {
                    listaCirculares = administrarEmpresa.listaCircularesParaEmpresa(listaEmpresas.get(index).getSecuencia());
                }
                if (tipoLista == 1) {
                    listaCirculares = administrarEmpresa.listaCircularesParaEmpresa(filtrarListaEmpresas.get(index).getSecuencia());
                }

            }
            return listaCirculares;
        } catch (Exception e) {
            System.out.println("Error en getListaCirculares .... !!! : " + e.toString());
            return null;
        }
    }

    public void setListaCirculares(List<Circulares> setListaCirculares) {
        this.listaCirculares = setListaCirculares;
    }

    public List<Circulares> getFiltrarListaCirculares() {
        return filtrarListaCirculares;
    }

    public void setFiltrarListaCirculares(List<Circulares> setFiltrarListaCirculares) {
        this.filtrarListaCirculares = setFiltrarListaCirculares;
    }

    public List<Circulares> getListCircularesModificar() {
        return listCircularesModificar;
    }

    public void setListCircularesModificar(List<Circulares> setListCircularesModificar) {
        this.listCircularesModificar = setListCircularesModificar;
    }

    public Circulares getNuevoCircular() {
        return nuevoCircular;
    }

    public void setNuevoCircular(Circulares setNuevoCircular) {
        this.nuevoCircular = setNuevoCircular;
    }

    public List<Circulares> getListCircularesCrear() {
        return listCircularesCrear;
    }

    public void setListCircularesCrear(List<Circulares> setListCircularesCrear) {
        this.listCircularesCrear = setListCircularesCrear;
    }

    public List<Circulares> getListCircularesBorrar() {
        return listCircularesBorrar;
    }

    public void setListCircularesBorrar(List<Circulares> setListCircularesBorrar) {
        this.listCircularesBorrar = setListCircularesBorrar;
    }

    public Circulares getEditarCircular() {
        return editarCircular;
    }

    public void setEditarCircular(Circulares setEditarCircular) {
        this.editarCircular = setEditarCircular;
    }

    public Circulares getDuplicarCircular() {
        return duplicarCircular;
    }

    public void setDuplicarCircular(Circulares setDuplicarCircular) {
        this.duplicarCircular = setDuplicarCircular;
    }

    public BigInteger getSecRegistroCircular() {
        return secRegistroCircular;
    }

    public void setSecRegistroCircular(BigInteger setSecRegistroCircular) {
        this.secRegistroCircular = setSecRegistroCircular;
    }

    public BigInteger getBackUpSecRegistroCircular() {
        return backUpSecRegistroCircular;
    }

    public void setBackUpSecRegistroCircular(BigInteger setBackUpSecRegistroCircular) {
        this.backUpSecRegistroCircular = setBackUpSecRegistroCircular;
    }

    public String getAltoTablaCircular() {
        return altoTablaCircular;
    }

    public void setAltoTablaCircular(String setAltoTablaCircular) {
        this.altoTablaCircular = setAltoTablaCircular;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String setPaginaAnterior) {
        this.paginaAnterior = setPaginaAnterior;
    }

    public boolean isActivoVigencia() {
        return activoVigencia;
    }

    public void setActivoVigencia(boolean setActivoVigencia) {
        this.activoVigencia = setActivoVigencia;
    }

    public boolean isActivoCircular() {
        return activoCircular;
    }

    public void setActivoCircular(boolean setActivoCircular) {
        this.activoCircular = setActivoCircular;
    }

    public List<Empresas> getLovEmpresas() {
        if (lovEmpresas == null) {
            lovEmpresas = administrarEmpresa.listaEmpresas();
        }
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

    public Empresas getEmpresaOrigenClonado() {
        return empresaOrigenClonado;
    }

    public void setEmpresaOrigenClonado(Empresas empresaOrigenClonado) {
        this.empresaOrigenClonado = empresaOrigenClonado;
    }

    public Empresas getEmpresaDestinoClonado() {
        return empresaDestinoClonado;
    }

    public void setEmpresaDestinoClonado(Empresas empresaDestinoClonado) {
        this.empresaDestinoClonado = empresaDestinoClonado;
    }

    public boolean isActivoCasillaClonado() {
        return activoCasillaClonado;
    }

    public void setActivoCasillaClonado(boolean activoCasillaClonado) {
        this.activoCasillaClonado = activoCasillaClonado;
    }

    public boolean isActivoBtnesAdd() {
        return activoBtnesAdd;
    }

    public void setActivoBtnesAdd(boolean activoBtnesAdd) {
        this.activoBtnesAdd = activoBtnesAdd;
    }

}

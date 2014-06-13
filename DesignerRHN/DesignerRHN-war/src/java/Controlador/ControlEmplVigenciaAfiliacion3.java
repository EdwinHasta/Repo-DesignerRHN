/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.EstadosAfiliaciones;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Entidades.TiposEntidades;
import Entidades.VigenciasAfiliaciones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasAfiliaciones3Interface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlEmplVigenciaAfiliacion3 implements Serializable {

    @EJB
    AdministrarVigenciasAfiliaciones3Interface administrarVigenciasAfiliaciones3;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Afiliaciones
    private List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    private List<VigenciasAfiliaciones> filtrarVigenciasAfiliaciones;
    private VigenciasAfiliaciones vigenciaSeleccionada;
    //Tipos Entidades
    private List<TiposEntidades> listTiposEntidades;
    private TiposEntidades tipoEntidadSeleccionado;
    private List<TiposEntidades> filtrarTiposEntidades;
    //Terceros
    private List<Terceros> listTerceros;
    private Terceros terceroSeleccionado;
    private List<Terceros> filtrarTerceros;
    //EstadosAfiliaciones
    private List<EstadosAfiliaciones> listEstadosAfiliaciones;
    private EstadosAfiliaciones estadoSSeleccionado;
    private List<EstadosAfiliaciones> filtrarEstadosAfiliaciones;
    //Empleado
    private Empleados empleado;
    //Tipo Actualizacion
    private int tipoActualizacion;
    //Activo/Desactivo VP Crtl + F11
    private int banderaVA;
    //Columnas Tabla VP
    private Column vAFechaInicial, vAFechaFinal, vATercero, vATipoEntidad, vANITTercero, vACodigo, vAEstadoAfiliacion, vAObservaciones;
    //Otros
    private boolean aceptar;
    //modificar
    private List<VigenciasAfiliaciones> listVAModificar;
    private boolean guardado, guardarOk;
    //crear VP
    public VigenciasAfiliaciones nuevaVigenciaA;
    private BigInteger l;
    private int k;
    //borrar VL
    private List<VigenciasAfiliaciones> listVABorrar;
    //editar celda
    private VigenciasAfiliaciones editarVA;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    //Autocompletar
    private boolean permitirIndexVA;
    //Variables Autompletar
    private String tiposEntidades, terceros, estadosAfiliacion;
    private long nit;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexVA;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaVA, tipoListaVA;
    //Duplicar Vigencia Prorrateo
    private VigenciasAfiliaciones duplicarVA;
    private List<VigenciasAfiliaciones> listVACrear;
    private boolean cambioVigenciaA;
    private VigenciasAfiliaciones vigenciaValidaciones;
    private Date fechaContratacion;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    //ALTO TABLA
    private String altoTabla;
    private String infoRegistro;

    //
    private String infoRegistroTipoEntidad, infoRegistroTercero, infoRegistroEstado;

    public ControlEmplVigenciaAfiliacion3() {
        backUpSecRegistro = null;
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listVABorrar = new ArrayList<VigenciasAfiliaciones>();
        //crear aficiones
        k = 0;
        //modificar aficiones
        listVAModificar = new ArrayList<VigenciasAfiliaciones>();
        //editar
        editarVA = new VigenciasAfiliaciones();
        cambioEditor = false;
        aceptarEditar = true;
        tipoListaVA = 0;
        //guardar
        guardado = true;

        banderaVA = 0;
        permitirIndexVA = true;
        indexVA = -1;
        secRegistro = null;
        cualCeldaVA = -1;
        listEstadosAfiliaciones = null;
        estadoSSeleccionado = new EstadosAfiliaciones();
        listTerceros = null;
        terceroSeleccionado = new Terceros();
        listTiposEntidades = null;
        tipoEntidadSeleccionado = new TiposEntidades();
        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
        listVACrear = new ArrayList<VigenciasAfiliaciones>();
        fechaContratacion = new Date();
        cambioVigenciaA = false;
        altoTabla = "270";

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarVigenciasAfiliaciones3.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    //EMPLEADO DE LA VIGENCIA 
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(BigInteger empl) {
        listVigenciasAfiliaciones = null;
        empleado = administrarVigenciasAfiliaciones3.obtenerEmpleado(empl);
        fechaContratacion = administrarVigenciasAfiliaciones3.fechaContratacion(empleado);
        getListVigenciasAfiliaciones();
        int tam = 0;
        if (listVigenciasAfiliaciones != null) {
            tam = listVigenciasAfiliaciones.size();
            if (tam > 0) {
                vigenciaSeleccionada = listVigenciasAfiliaciones.get(0);
            }
            infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVA(int indice) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoListaVA == 0) {
            indexVA = indice;
            vigenciaValidaciones = listVigenciasAfiliaciones.get(indexVA);
            boolean validacion = true;
            if (cualCeldaVA != 0 && cualCeldaVA != 1) {
                validacion = validarModificacionRegistroTabla();
            }
            if (validacion == true) {
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indice))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                listVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNit(nit);
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistro = null;
            } else {
                VigenciasAfiliaciones cambio = administrarVigenciasAfiliaciones3.vigenciaAfiliacionSecuencia(listVigenciasAfiliaciones.get(indexVA).getSecuencia());
                listVigenciasAfiliaciones.set(indexVA, cambio);
            }
        } else {
            int ind = listVigenciasAfiliaciones.indexOf(filtrarVigenciasAfiliaciones.get(indice));
            indexVA = ind;
            vigenciaValidaciones = filtrarVigenciasAfiliaciones.get(indexVA);
            boolean validacion = true;
            if (cualCeldaVA != 0 && cualCeldaVA != 1) {
                validacion = validarModificacionRegistroTabla();
            }
            if (validacion == true) {
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNit(nit);
                listVigenciasAfiliaciones.get(ind).getTercerosucursal().getTercero().setNit(nit);
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistro = null;
            } else {
                VigenciasAfiliaciones cambio = administrarVigenciasAfiliaciones3.vigenciaAfiliacionSecuencia(listVigenciasAfiliaciones.get(indexVA).getSecuencia());
                listVigenciasAfiliaciones.set(indexVA, cambio);
            }
        }
        context.update("form:datosVAVigencia");
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        boolean retorno = true;
        if (i == 0) {
            VigenciasAfiliaciones auxiliar = null;
            if (tipoListaVA == 0) {
                auxiliar = listVigenciasAfiliaciones.get(indexVA);
            } else {
                auxiliar = filtrarVigenciasAfiliaciones.get(indexVA);
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
            if (nuevaVigenciaA.getFechafinal() != null) {
                if (nuevaVigenciaA.getFechainicial().after(fechaParametro) && nuevaVigenciaA.getFechainicial().before(nuevaVigenciaA.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevaVigenciaA.getFechafinal() == null) {
                if (nuevaVigenciaA.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarVA.getFechafinal() != null) {
                if (duplicarVA.getFechainicial().after(fechaParametro) && duplicarVA.getFechainicial().before(duplicarVA.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarVA.getFechafinal() == null) {
                if (duplicarVA.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {

        VigenciasAfiliaciones auxiliar = null;
        if (tipoListaVA == 0) {
            auxiliar = listVigenciasAfiliaciones.get(i);
        } else {
            auxiliar = filtrarVigenciasAfiliaciones.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            indexVA = i;
            retorno = validarFechasRegistro(0);
            vigenciaValidaciones = auxiliar;
            if (retorno == true) {
                if (validarFechasRegistroTabla() == true) {
                    cambiarIndiceVA(i, c);
                    modificarVA(i);
                }
            } else {
                if (tipoListaVA == 0) {
                    listVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
                } else {
                    filtrarVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVAVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            } else {
                filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            context.execute("errorRegNew.show()");
        }
    }

    public void modificarFechasFinales(int i, int c) {
        VigenciasAfiliaciones auxiliar = null;
        if (tipoListaVA == 0) {
            auxiliar = listVigenciasAfiliaciones.get(i);
        } else {
            auxiliar = filtrarVigenciasAfiliaciones.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            indexVA = i;
            retorno = validarFechasRegistro(0);
            vigenciaValidaciones = auxiliar;
            if (retorno == true) {
                boolean modificacion = false;
                if (fechaFin == null && auxiliar.getFechafinal() == null) {
                    modificacion = false;
                } else {
                    if (fechaFin == null && auxiliar.getFechafinal() != null) {
                        modificacion = true;
                    } else {
                        if (fechaFin.equals(auxiliar.getFechafinal())) {
                            modificacion = false;
                        } else {
                            modificacion = true;
                        }
                    }
                }
                if (modificacion == false) {
                    cambiarIndiceVA(i, c);
                } else {
                    cambiarIndiceVA(i, c);
                    modificarVA(i);
                }
            } else {
                if (tipoListaVA == 0) {
                    listVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
                } else {
                    filtrarVigenciasAfiliaciones.get(i).setFechafinal(fechaFin);
                    filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVAVigencia");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            } else {
                filtrarVigenciasAfiliaciones.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            context.execute("errorRegNew.show()");
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciaProrrateo
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarVA(int indice, String confirmarCambio, String valorConfirmar) {
        indexVA = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOENTIDAD")) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
            } else {
                filtrarVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
            }
            for (int i = 0; i < listTiposEntidades.size(); i++) {
                if (listTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVA == 0) {
                    vigenciaValidaciones = new VigenciasAfiliaciones();
                    vigenciaValidaciones.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    //listVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    indexVA = indice;
                    boolean cambio = validarModificacionRegistroTabla();
                    if (cambio == false) {
                        cambioVigenciaA = false;
                        listVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
                    } else {
                        cambioVigenciaA = true;
                        listVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    }
                } else {
                    vigenciaValidaciones = new VigenciasAfiliaciones();
                    vigenciaValidaciones.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    indexVA = indice;
                    boolean cambio = validarIngresoNuevoRegistro(0);
                    indexVA = indice;
                    if (cambio == false) {
                        cambioVigenciaA = false;
                        filtrarVigenciasAfiliaciones.get(indice).getTipoentidad().setNombre(tiposEntidades);
                    } else {
                        cambioVigenciaA = true;
                        filtrarVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    }
                }
                listTiposEntidades = null;
                getListTiposEntidades();
            } else {
                permitirIndexVA = false;
                getInfoRegistroTipoEntidad();
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCEROS")) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNombre(terceros);
            } else {
                filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNombre(terceros);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaVA == 0) {
                    vigenciaValidaciones = new VigenciasAfiliaciones();
                    vigenciaValidaciones.setTercerosucursal(new TercerosSucursales());
                    vigenciaValidaciones.getTercerosucursal().setTercero(new Terceros());
                    vigenciaValidaciones.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    boolean cambio = validarModificacionRegistroTabla();
                    if (cambio == false) {
                        cambioVigenciaA = false;
                        listVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNombre(terceros);
                    } else {
                        cambioVigenciaA = true;
                        boolean banderaEncuentra = false;
                        int posicion = -1;
                        List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
                        for (int i = 0; i < listTercerosSucursales.size(); i++) {
                            if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                                banderaEncuentra = true;
                                posicion = i;
                            }
                        }
                        if ((banderaEncuentra == true) && (posicion != -1)) {
                            listVigenciasAfiliaciones.get(indice).setTercerosucursal(listTercerosSucursales.get(posicion));
                        }
                    }
                } else {
                    boolean cambio = validarModificacionRegistroTabla();
                    indexVA = indice;
                    vigenciaValidaciones = new VigenciasAfiliaciones();
                    vigenciaValidaciones.setTercerosucursal(new TercerosSucursales());
                    vigenciaValidaciones.getTercerosucursal().setTercero(new Terceros());
                    vigenciaValidaciones.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    if (cambio == false) {
                        cambioVigenciaA = true;
                        filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    } else {
                        cambioVigenciaA = true;
                        boolean banderaEncuentra = false;
                        int posicion = -1;
                        List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
                        for (int i = 0; i < listTercerosSucursales.size(); i++) {
                            if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                                banderaEncuentra = true;
                                posicion = i;
                            }
                        }
                        if ((banderaEncuentra == true) && (posicion != -1)) {
                            filtrarVigenciasAfiliaciones.get(indice).setTercerosucursal(listTercerosSucursales.get(posicion));
                        }
                    }
                }
                listTerceros = null;
                getListTerceros();
            } else {
                permitirIndexVA = false;
                getInfoRegistroTercero();
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTADOAFILIACION")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoListaVA == 0) {
                    listVigenciasAfiliaciones.get(indice).getEstadoafiliacion().setNombre(estadosAfiliacion);
                } else {
                    filtrarVigenciasAfiliaciones.get(indice).getEstadoafiliacion().setNombre(estadosAfiliacion);
                }
                for (int i = 0; i < listEstadosAfiliaciones.size(); i++) {
                    if (listEstadosAfiliaciones.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoListaVA == 0) {
                        cambioVigenciaA = true;
                        listVigenciasAfiliaciones.get(indice).setEstadoafiliacion(listEstadosAfiliaciones.get(indiceUnicoElemento));
                    } else {
                        cambioVigenciaA = true;
                        filtrarVigenciasAfiliaciones.get(indice).setEstadoafiliacion(listEstadosAfiliaciones.get(indiceUnicoElemento));
                    }
                    cambioVigenciaA = true;
                    listEstadosAfiliaciones = null;
                    getListEstadosAfiliaciones();
                } else {
                    permitirIndexVA = false;
                    getInfoRegistroEstado();
                    context.update("form:EstadoAfiliacionDialogo");
                    context.execute("EstadoAfiliacionDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                listEstadosAfiliaciones = null;
                getListEstadosAfiliaciones();
                if (tipoListaVA == 0) {
                    listVigenciasAfiliaciones.get(indice).setEstadoafiliacion(new EstadosAfiliaciones());
                } else {
                    filtrarVigenciasAfiliaciones.get(indice).setEstadoafiliacion(new EstadosAfiliaciones());
                }
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                    context.update("form:datosVAVigencia");
                }
            }
        }
        if (coincidencias == 1) {
            if (tipoListaVA == 0) {
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indice))) {

                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistro = null;
            } else {
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {

                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
                secRegistro = null;
            }
            cambioVigenciaA = true;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
                context.update("form:datosVAVigencia");
            }
        }
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos (VigenciaProrrateo)
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletarVA(int tipoNuevo, String Campo) {

        if (Campo.equals("TIPOENTIDAD")) {
            if (tipoNuevo == 1) {
                tiposEntidades = nuevaVigenciaA.getTipoentidad().getNombre();
            } else if (tipoNuevo == 2) {
                tiposEntidades = duplicarVA.getTipoentidad().getNombre();
            }
        } else if (Campo.equals("TERCERO")) {
            if (tipoNuevo == 1) {
                terceros = nuevaVigenciaA.getTercerosucursal().getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                terceros = duplicarVA.getTercerosucursal().getTercero().getNombre();
            }
        } else if (Campo.equals("ESTADOAFILIACION")) {
            if (tipoNuevo == 1) {
                estadosAfiliacion = nuevaVigenciaA.getEstadoafiliacion().getNombre();
            } else if (tipoNuevo == 2) {
                estadosAfiliacion = duplicarVA.getEstadoafiliacion().getNombre();
            }
        }
    }

    /**
     *
     * Metodo que genera el auto completar de un proceso nuevoRegistro o
     * duplicarRegistro de VigenciasProrrateos
     *
     * @param confirmarCambio Tipo de elemento a modificar: CENTROCOSTO -
     * PROYECTO
     * @param valorConfirmar Valor ingresado para confirmar
     * @param tipoNuevo Tipo de proceso: Nuevo - Duplicar
     */
    public void autocompletarNuevoyDuplicadoVA(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOENTIDAD")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaA.getTipoentidad().setNombre(tiposEntidades);
            } else if (tipoNuevo == 2) {
                duplicarVA.getTipoentidad().setNombre(tiposEntidades);
            }
            for (int i = 0; i < listTiposEntidades.size(); i++) {
                if (listTiposEntidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaA.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTipoEntidadVA");
                } else if (tipoNuevo == 2) {
                    duplicarVA.setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoEntidadVA");
                }
                listTiposEntidades = null;
                getListTiposEntidades();
            } else {
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTipoEntidadVA");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoEntidadVA");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaA.getTercerosucursal().getTercero().setNombre(terceros);
            } else if (tipoNuevo == 2) {
                duplicarVA.getTercerosucursal().getTercero().setNombre(terceros);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }

            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaA.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTerceroVA");
                } else if (tipoNuevo == 2) {
                    duplicarVA.getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTerceroVA");
                }
                listTerceros = null;
                getListTerceros();
            } else {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTerceroVA");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicadoTerceroVA");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTADOAFILIACION")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaA.getEstadoafiliacion().setNombre(terceros);
                } else if (tipoNuevo == 2) {
                    duplicarVA.getEstadoafiliacion().setNombre(terceros);
                }
                for (int i = 0; i < listEstadosAfiliaciones.size(); i++) {
                    if (listEstadosAfiliaciones.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigenciaA.setEstadoafiliacion(listEstadosAfiliaciones.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevaEstadoAfiliacionVA");
                    } else if (tipoNuevo == 2) {
                        duplicarVA.setEstadoafiliacion(listEstadosAfiliaciones.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicadoEstadoAfiliacionVA");
                    }
                    listEstadosAfiliaciones = null;
                    getListEstadosAfiliaciones();
                } else {
                    context.update("form:EstadoAfiliacionDialogo");
                    context.execute("EstadoAfiliacionDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevaEstadoAfiliacionVA");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicadoEstadoAfiliacionVA");
                    }
                }
            }
        } else {
            listEstadosAfiliaciones.clear();
            getListEstadosAfiliaciones();
            if (tipoNuevo == 1) {
                nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
                context.update("formularioDialogos:nuevaEstadoAfiliacionVA");
            } else if (tipoNuevo == 2) {
                duplicarVA.setEstadoafiliacion(new EstadosAfiliaciones());
                context.update("formularioDialogos:duplicadoEstadoAfiliacionVA");
            }
        }
    }

    public boolean validarInformacionRegistro(int tipo) {
        boolean retorno = true;

        if (tipo == 1) {
            if ((nuevaVigenciaA.getFechainicial() == null) || (nuevaVigenciaA.getTipoentidad().getSecuencia() == null) || (nuevaVigenciaA.getTercerosucursal().getSecuencia() == null)) {
                retorno = false;
            }
            if ((nuevaVigenciaA.getObservacion() != null) && (nuevaVigenciaA.getObservacion().length() > 100)) {
                retorno = false;
            }
        }
        if (tipo == 2) {
            if ((duplicarVA.getFechainicial() == null) || (duplicarVA.getTipoentidad().getNombre().isEmpty()) || (duplicarVA.getTercerosucursal().getTercero().getNombre().isEmpty())) {
                retorno = false;
            }
            if ((duplicarVA.getObservacion() != null) && (duplicarVA.getObservacion().length() > 100)) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarExistenciaTipoEntidad(int i) {
        boolean retorno = true;
        List<VigenciasAfiliaciones> listAuxiliar = new ArrayList<VigenciasAfiliaciones>();
        VigenciasAfiliaciones provisional = null;
        ///////////--------////////////
        /*
         if (i == 0) {
         provisional = vigenciaValidaciones;
         }
         */
        if (i == 1) {
            provisional = nuevaVigenciaA;
        }
        if (i == 2) {
            provisional = duplicarVA;
        }
        ///////////--------////////////
        for (int cont = 0; cont < listVigenciasAfiliaciones.size(); cont++) {
            if (listVigenciasAfiliaciones.get(cont).getTipoentidad().getSecuencia() == provisional.getTipoentidad().getSecuencia()) {
                listAuxiliar.add(listVigenciasAfiliaciones.get(cont));
            }
        }

        ///////////--------////////////
        int tam = listAuxiliar.size();
        ///////////--------////////////
        if (tam >= 2) {
            if (provisional.getFechafinal() == null) {
                int conteo = 0;
                for (int de = 0; de < listAuxiliar.size(); de++) {
                    if (listAuxiliar.get(de).getFechafinal() == null) {
                        conteo++;
                    }
                }
                ///////////--------////////////
                if (conteo > 0) {
                    retorno = false;
                } ///////////--------////////////
                else {
                    VigenciasAfiliaciones auxiliar = listAuxiliar.get(0);
                    if (provisional.getFechainicial().after(auxiliar.getFechafinal())) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
            } ///////////--------///////////////////////--------////////////
            else {
                boolean cambiosBefore = false;
                VigenciasAfiliaciones reg1 = listAuxiliar.get(0);
                VigenciasAfiliaciones regN = listAuxiliar.get(tam - 1);
                ///////////--------////////////
                if ((provisional.getFechafinal().before(reg1.getFechainicial())) && (provisional.getFechainicial().before(reg1.getFechainicial()))) {
                    cambiosBefore = true;
                }
                ///////////--------////////////
                if (regN.getFechafinal() != null) {
                    if ((provisional.getFechafinal().after(regN.getFechafinal())) && (provisional.getFechainicial().after(regN.getFechafinal()))) {
                        cambiosBefore = true;
                    }
                } else {
                    if (provisional.getFechainicial().after(regN.getFechafinal())) {
                        cambiosBefore = true;
                    }
                }
                ///////////--------////////////
                if (cambiosBefore == true) {
                    retorno = true;
                } else {
                    boolean ubicoReg = false;
                    int cambios = 0;
                    for (int o = 1; o < tam - 1; o++) {
                        if (listAuxiliar.get(o + 1).getFechafinal() != null) {
                            if ((provisional.getFechainicial().after(listAuxiliar.get(o - 1).getFechafinal())) && (provisional.getFechafinal().before(listAuxiliar.get(o + 1).getFechainicial()))) {
                                cambios++;
                                ubicoReg = true;
                            }
                        }
                    }
                    ///////////--------////////////
                    if (cambios == 1) {
                        if (ubicoReg == true) {
                            retorno = true;
                        }
                    } ///////////--------////////////
                    else {
                        retorno = false;
                    }
                }
            }
        }
        ///////////--------///////////////////////--------////////////
        if (tam == 1) {
            boolean ubico = false;
            if (provisional.getFechafinal() != null) {
                if ((provisional.getFechainicial().before(listAuxiliar.get(0).getFechainicial())) && (provisional.getFechafinal().before(listAuxiliar.get(0).getFechainicial()))) {
                    ubico = true;
                } else if ((provisional.getFechainicial().after(listAuxiliar.get(0).getFechafinal())) && (provisional.getFechafinal().after(listAuxiliar.get(0).getFechafinal()))) {
                    ubico = true;
                }
            } else {
                if (listAuxiliar.get(0).getFechafinal() != null) {
                    if ((provisional.getFechainicial().after(listAuxiliar.get(0).getFechafinal()))) {
                        ubico = true;
                    }
                } else {
                    ubico = false;
                }
            }
            if (ubico == false) {
                retorno = false;
            } else {
                retorno = true;
            }
        }
        if (tam == 0) {
            retorno = true;
        }
        return retorno;
    }

    public boolean validacionTercerorSucursalesNuevoRegistro(int i) {
        boolean retorno = true;

        if (i == 1) {
            Long r = administrarVigenciasAfiliaciones3.validacionTercerosSurcursalesNuevaVigencia(empleado.getSecuencia(), nuevaVigenciaA.getFechainicial(), nuevaVigenciaA.getEstadoafiliacion().getSecuencia(), nuevaVigenciaA.getTercerosucursal().getTercero().getSecuencia());
            if (r > 0) {
                retorno = false;
            }
        }
        if (i == 2) {
            Long r = administrarVigenciasAfiliaciones3.validacionTercerosSurcursalesNuevaVigencia(empleado.getSecuencia(), duplicarVA.getFechainicial(), duplicarVA.getEstadoafiliacion().getSecuencia(), duplicarVA.getTercerosucursal().getTercero().getSecuencia());
            if (r > 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validacionFechasNuevoRegistro(int i) {
        boolean retorno = true;
        if (i == 1) {
            if ((nuevaVigenciaA.getFechainicial() != null) && (nuevaVigenciaA.getFechafinal() != null)) {
                if (nuevaVigenciaA.getFechainicial().after(nuevaVigenciaA.getFechafinal())) {
                    retorno = false;
                }
            }
            if ((nuevaVigenciaA.getFechainicial() != null) && (nuevaVigenciaA.getFechafinal() == null)) {
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarVA.getFechainicial() != null) && (duplicarVA.getFechafinal() != null)) {
                if (duplicarVA.getFechainicial().after(duplicarVA.getFechafinal())) {
                    retorno = false;
                }
            }
            if ((duplicarVA.getFechainicial() != null) && (duplicarVA.getFechafinal() == null)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void dialogoCamposNulos() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorIngresoRegistro");
        context.execute("errorIngresoRegistro.show()");
    }

    public void dialogoFechasErroneas() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorFechasRegistro");
        context.execute("errorFechasRegistro.show()");
    }

    public void dialogoFechaContratacionError() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorFechaMenorContratacion");
        context.execute("errorFechaMenorContratacion.show()");
    }

    public void dialogoDiaInicioError() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorFechaInicialRegistro");
        context.execute("errorFechaInicialRegistro.show()");
    }

    public void dialogoTipoERepetida() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorExistenciaTipoEntidad");
        context.execute("errorExistenciaTipoEntidad.show()");
    }

    public void dialogoErrorTercero() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:errorTerceroSucursal");
        context.execute("errorTerceroSucursal.show()");
    }

    public boolean validarFechasRegistroTabla() {
        boolean retorno = true;
        List<VigenciasAfiliaciones> listAuxiliar = null;
        if (tipoListaVA == 0) {
            listAuxiliar = listVigenciasAfiliaciones;
        } else {
            listAuxiliar = filtrarVigenciasAfiliaciones;
        }
        boolean validacionCamposNulos;
        boolean validacionFechas;
        boolean validacionFechaMayorContratacion;
        boolean validacionDiaInicioFecha;
        validacionCamposNulos = camposModificacionRegistro(listAuxiliar);
        if (validacionCamposNulos == true) {
            //////----------////////////
            validacionFechas = fechasModificacionRegistro(listAuxiliar);
            if (validacionFechas == false) {
                retorno = false;
                dialogoFechasErroneas();
            }
            //////----------////////////
            validacionFechaMayorContratacion = fechaContratacionModificacionEmpleado(listAuxiliar);
            if (validacionFechaMayorContratacion == false) {
                dialogoFechaContratacionError();
            }
            //////----------////////////
            validacionDiaInicioFecha = diaInicialFechaModificacion();
            if (validacionDiaInicioFecha == false) {
                dialogoDiaInicioError();
            }
            //////----------////////////
            if (validacionFechas == false) {
                retorno = false;
            }
        } else {
            dialogoCamposNulos();
            retorno = false;
        }
        if (retorno == true) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones = listAuxiliar;
            } else {
                filtrarVigenciasAfiliaciones = listAuxiliar;
            }
        }
        return retorno;
    }

    public boolean validarModificacionRegistroTabla() {
        boolean retorno = true;
        List<VigenciasAfiliaciones> listAuxiliar = null;
        if (tipoListaVA == 0) {
            listAuxiliar = listVigenciasAfiliaciones;
        } else {
            listAuxiliar = filtrarVigenciasAfiliaciones;
        }
        boolean validacionCamposNulos;
        boolean validacionTiposEntidades;
        boolean validacionTerceroSucursal;
        validacionCamposNulos = camposModificacionRegistro(listAuxiliar);
        if (validacionCamposNulos == true) {
            //////----------////////////
            validacionTerceroSucursal = terceroModificacionRegistro(listAuxiliar);
            if (validacionTerceroSucursal == false) {
                retorno = false;
                dialogoErrorTercero();
            }
            //////----------////////////
            validacionTiposEntidades = tipoEntidadModificacionRegistro(listAuxiliar);
            if (validacionTiposEntidades == false) {
                retorno = false;
                dialogoTipoERepetida();
            }
            //////----------////////////
            if (validacionTiposEntidades == false || validacionTerceroSucursal == false) {
                retorno = false;
            }
        } else {
            dialogoCamposNulos();
            retorno = false;
        }
        if (retorno == true) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones = listAuxiliar;
            } else {
                filtrarVigenciasAfiliaciones = listAuxiliar;
            }
        }
        return retorno;
    }

    public boolean fechaContratacionModificacionEmpleado(List<VigenciasAfiliaciones> listaAuxuliar) {
        boolean retorna = true;
        if (listaAuxuliar.get(indexVA).getFechainicial().before(fechaContratacion)) {
            retorna = false;
        }
        return retorna;
    }

    public boolean diaInicialFechaModificacion() {
        boolean retorna = true;
        int dia = vigenciaValidaciones.getFechainicial().getDate();
        if (dia > 1) {
            retorna = false;
        }
        return retorna;
    }

    public boolean tipoEntidadModificacionRegistro(List<VigenciasAfiliaciones> listaAuxuliar) {
        boolean retorno = true;
        VigenciasAfiliaciones provisional = vigenciaValidaciones;
        List<VigenciasAfiliaciones> listAuxiliar = new ArrayList<VigenciasAfiliaciones>();
        ///////////--------////////////
        for (int cont = 0; cont < listaAuxuliar.size(); cont++) {
            if (listaAuxuliar.get(cont).getTipoentidad().getSecuencia() == provisional.getTipoentidad().getSecuencia()) {
                listAuxiliar.add(listaAuxuliar.get(cont));
            }
        }
        ///////////--------////////////
        int tam = listAuxiliar.size();
        ///////////--------////////////
        if (tam >= 2) {
            if (provisional.getFechafinal() == null) {
                int conteo = 0;
                for (int de = 0; de < listAuxiliar.size(); de++) {
                    if (listAuxiliar.get(de).getFechafinal() == null) {
                        conteo++;
                    }
                }
                ///////////--------////////////
                if (conteo > 0) {
                    conteo = 0;
                    for (int de = 0; de < listAuxiliar.size(); de++) {
                        if (listAuxiliar.get(de).getSecuencia() == provisional.getSecuencia()) {
                            conteo++;
                        }
                    }
                    if (conteo != 1) {
                        retorno = false;
                    }
                    if (conteo == 1) {
                        retorno = true;
                    }
                } ///////////--------////////////
                else {
                    VigenciasAfiliaciones auxiliar = listAuxiliar.get(0);
                    if (provisional.getFechainicial().after(auxiliar.getFechafinal())) {
                        retorno = true;
                    } else {
                        retorno = false;
                    }
                }
            } ///////////--------///////////////////////--------////////////
            else {
                boolean cambiosBefore = false;
                VigenciasAfiliaciones reg1 = listAuxiliar.get(0);
                VigenciasAfiliaciones regN = listAuxiliar.get(tam - 1);
                ///////////--------////////////
                if ((provisional.getFechafinal().before(reg1.getFechainicial())) && (provisional.getFechainicial().before(reg1.getFechainicial()))) {
                    cambiosBefore = true;
                }
                ///////////--------////////////
                if (regN.getFechafinal() != null) {
                    if ((provisional.getFechafinal().after(regN.getFechafinal())) && (provisional.getFechainicial().after(regN.getFechafinal()))) {
                        cambiosBefore = true;
                    }
                } else {
                    if (provisional.getFechainicial().after(regN.getFechafinal())) {
                        cambiosBefore = true;
                    }
                }
                ///////////--------////////////
                if (cambiosBefore == true) {
                    retorno = true;
                } else {
                    boolean ubicoReg = false;
                    int cambios = 0;
                    for (int o = 1; o < tam - 1; o++) {
                        if (listAuxiliar.get(o + 1).getFechafinal() != null) {
                            if ((provisional.getFechainicial().after(listAuxiliar.get(o - 1).getFechafinal())) && (provisional.getFechafinal().before(listAuxiliar.get(o + 1).getFechainicial()))) {
                                cambios++;
                                ubicoReg = true;
                            }
                        }
                    }
                    ///////////--------////////////
                    if (cambios == 1) {
                        if (ubicoReg == true) {
                            retorno = true;
                        }
                    } ///////////--------////////////
                    else {
                        retorno = false;
                    }
                }
            }
        }
        ///////////--------///////////////////////--------////////////
        if (tam == 1) {
            boolean ubico = false;
            if (provisional.getFechafinal() != null) {
                if ((provisional.getFechainicial().before(listAuxiliar.get(0).getFechainicial())) && (provisional.getFechafinal().before(listAuxiliar.get(0).getFechainicial()))) {
                    ubico = true;
                } else if ((provisional.getFechainicial().after(listAuxiliar.get(0).getFechafinal())) && (provisional.getFechafinal().after(listAuxiliar.get(0).getFechafinal()))) {
                    ubico = true;
                }
            } else {
                if (listAuxiliar.get(0).getFechafinal() != null) {
                    if ((provisional.getFechainicial().after(listAuxiliar.get(0).getFechafinal()))) {
                        ubico = true;
                    }
                } else {
                    ubico = false;
                }
            }
            if (ubico == false) {
                retorno = false;
            } else {
                retorno = true;
            }
        }
        if (tam == 0) {
            retorno = true;
        }
        return retorno;
    }

    public boolean camposModificacionRegistro(List<VigenciasAfiliaciones> listaAuxuliar) {
        boolean retorno = true;
        int posicion = indexVA;
        if ((listaAuxuliar.get(posicion).getFechainicial() == null) || (listaAuxuliar.get(posicion).getTipoentidad().getNombre().isEmpty()) || (listaAuxuliar.get(posicion).getTercerosucursal().getTercero().getNombre().isEmpty())) {
            retorno = false;
        }
        if ((listaAuxuliar.get(posicion).getObservacion() != null) && (listaAuxuliar.get(posicion).getObservacion().length() > 100)) {
            retorno = false;
        }

        return retorno;
    }

    public boolean fechasModificacionRegistro(List<VigenciasAfiliaciones> listaAuxuliar) {
        boolean retorno = true;
        VigenciasAfiliaciones auxiliar = null;
        auxiliar = listaAuxuliar.get(indexVA);
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            if (auxiliar.getFechainicial().after(auxiliar.getFechafinal())) {
                retorno = false;
            }
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() == null)) {
            retorno = true;
        }
        return retorno;
    }

    public boolean terceroModificacionRegistro(List<VigenciasAfiliaciones> listaAuxuliar) {
        boolean retorno = true;
        if (listaAuxuliar.get(indexVA).getEstadoafiliacion() == null) {
            listaAuxuliar.get(indexVA).setEstadoafiliacion(new EstadosAfiliaciones());
        }
        Long r = administrarVigenciasAfiliaciones3.validacionTercerosSurcursalesNuevaVigencia(empleado.getSecuencia(), listaAuxuliar.get(indexVA).getFechainicial(), listaAuxuliar.get(indexVA).getEstadoafiliacion().getSecuencia(), listaAuxuliar.get(indexVA).getTercerosucursal().getTercero().getSecuencia());
        if (r != null) {
            if (r > 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarFechaContratacionEmpleado(int i) {
        boolean retorna = true;
        if (i == 1) {
            if (nuevaVigenciaA.getFechainicial().before(fechaContratacion)) {
                retorna = false;
            }
        }
        if (i == 2) {
            if (duplicarVA.getFechainicial().before(fechaContratacion)) {
                retorna = false;
            }
        }
        return retorna;
    }

    public boolean validarDiaInicialRegistro(int i) {
        boolean retorna = true;
        if (i == 1) {
            int dia = nuevaVigenciaA.getFechainicial().getDate();
            if (dia >= 2) {
                retorna = false;
            }
        }
        if (i == 2) {
            int dia = duplicarVA.getFechainicial().getDate();
            if (dia >= 2) {
                retorna = false;
            }

        }
        return retorna;
    }

    public boolean validarIngresoNuevoRegistro(int i) {
        boolean mensaje = true;
        boolean validacionCamposNulos;
        boolean validacionTiposEntidades;
        boolean validacionFechas;
        boolean validacionTerceroSucursal;

        //////----------////////////
        validacionCamposNulos = validarInformacionRegistro(i);
        if (validacionCamposNulos == false) {
            dialogoCamposNulos();
        }

        //////----------////////////
        if (validacionCamposNulos == true) {
            validacionFechas = validacionFechasNuevoRegistro(i);
            if (validacionFechas == false) {
                dialogoFechasErroneas();
            }
            //////----------////////////
            validacionTerceroSucursal = validacionTercerorSucursalesNuevoRegistro(i);
            if (validacionTerceroSucursal == false) {
                dialogoErrorTercero();
            }
            //////----------////////////
            validacionTiposEntidades = validarExistenciaTipoEntidad(i);
            if (validacionTiposEntidades == false) {
                dialogoTipoERepetida();
            }
            if (validacionFechas == false || validacionTerceroSucursal == false || validacionTiposEntidades == false) {
                mensaje = false;
            }
        } //////----------////////////
        else {
            mensaje = false;
        }
        return mensaje;
    }

    public void posicionNit() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> map = context.getExternalContext().getRequestParameterMap();
        String name = map.get("n"); // name attribute of node
        String type = map.get("t"); // type attribute of node
        int indice = Integer.parseInt(type);
        int columna = Integer.parseInt(name);
        cambiarIndiceVA(indice, columna);
    }

    public void cambiarIndiceVA(int indice, int celda) {
        if (permitirIndexVA == true) {

            indexVA = indice;
            cualCeldaVA = celda;
            if (tipoListaVA == 0) {
                secRegistro = listVigenciasAfiliaciones.get(indexVA).getSecuencia();
                tiposEntidades = listVigenciasAfiliaciones.get(indexVA).getTipoentidad().getNombre();
                terceros = listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNombre();
                nit = listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNit();
                estadosAfiliacion = listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion().getNombre();
                fechaFin = listVigenciasAfiliaciones.get(indexVA).getFechafinal();
                fechaIni = listVigenciasAfiliaciones.get(indexVA).getFechainicial();
            } else {
                secRegistro = filtrarVigenciasAfiliaciones.get(indexVA).getSecuencia();
                tiposEntidades = filtrarVigenciasAfiliaciones.get(indexVA).getTipoentidad().getNombre();
                terceros = filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNombre();
                nit = filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNit();
                estadosAfiliacion = filtrarVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion().getNombre();
                fechaFin = filtrarVigenciasAfiliaciones.get(indexVA).getFechafinal();
                fechaIni = filtrarVigenciasAfiliaciones.get(indexVA).getFechainicial();
            }
        }
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosVA();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVA() {
        if (guardado == false) {
            if (!listVABorrar.isEmpty()) {
                for (int i = 0; i < listVABorrar.size(); i++) {
                    if (listVABorrar.get(i).getTipoentidad().getSecuencia() == null) {
                        listVABorrar.get(i).setTipoentidad(null);
                    }
                    if (listVABorrar.get(i).getTercerosucursal().getTercero().getSecuencia() == null) {
                        listVABorrar.get(i).getTercerosucursal().setTercero(null);
                    }
                    if (listVABorrar.get(i).getEstadoafiliacion().getSecuencia() == null) {
                        listVABorrar.get(i).setEstadoafiliacion(null);
                    }
                    administrarVigenciasAfiliaciones3.borrarVigenciaAfiliacion(listVABorrar.get(i));
                }
                listVABorrar.clear();
            }
            if (!listVACrear.isEmpty()) {
                for (int i = 0; i < listVACrear.size(); i++) {
                    if (listVACrear.get(i).getTipoentidad().getSecuencia() == null) {
                        listVACrear.get(i).setTipoentidad(null);
                    }
                    if (listVACrear.get(i).getTercerosucursal().getTercero().getSecuencia() == null) {
                        listVACrear.get(i).getTercerosucursal().setTercero(null);
                    }
                    if (listVACrear.get(i).getEstadoafiliacion().getSecuencia() == null) {
                        listVACrear.get(i).setEstadoafiliacion(null);
                    }
                    administrarVigenciasAfiliaciones3.crearVigenciaAfiliacion(listVACrear.get(i));
                }
                listVACrear.clear();
            }
            if (!listVAModificar.isEmpty()) {
                for (int i = 0; i < listVAModificar.size(); i++) {
                    if (listVAModificar.get(i).getTipoentidad().getSecuencia() == null) {
                        listVAModificar.get(i).setTipoentidad(null);
                    }
                    if (listVAModificar.get(i).getTercerosucursal().getTercero().getSecuencia() == null) {
                        listVAModificar.get(i).getTercerosucursal().setTercero(null);
                    }
                    if (listVAModificar.get(i).getEstadoafiliacion().getSecuencia() == null) {
                        listVAModificar.get(i).setEstadoafiliacion(null);
                    }
                    administrarVigenciasAfiliaciones3.editarVigenciaAfiliacion(listVAModificar.get(i));
                }

                listVAModificar.clear();
            }
            listVigenciasAfiliaciones = null;
            listVigenciasAfiliaciones = null;
            getListVigenciasAfiliaciones();
            int tam = 0;
            if (listVigenciasAfiliaciones != null) {
                tam = listVigenciasAfiliaciones.size();
            }
            if (tam > 0) {
                infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }
            guardado = true;
            permitirIndexVA = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosVAVigencia");
            context.update("form:informacionRegistro");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            k = 0;
        }
        cambioVigenciaA = false;
        indexVA = -1;
        secRegistro = null;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    /**
     * Cancela las modifaciones de la tabla vigencias prorrateos
     */
    public void cancelarModificacionVA() {
        if (banderaVA == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        listEstadosAfiliaciones = null;
        listTerceros = null;
        listTiposEntidades = null;
        listVABorrar.clear();
        listVACrear.clear();
        listVAModificar.clear();
        indexVA = -1;
        secRegistro = null;
        k = 0;
        fechaFin = null;
        fechaIni = null;
        listVigenciasAfiliaciones = null;
        getListVigenciasAfiliaciones();
        int tam = 0;
        if (listVigenciasAfiliaciones != null) {
            tam = listVigenciasAfiliaciones.size();
        }
        if (tam > 0) {
            infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVAVigencia");
        context.update("form:informacionRegistro");
        cambioVigenciaA = false;
        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (indexVA >= 0) {
            if (tipoListaVA == 0) {
                editarVA = listVigenciasAfiliaciones.get(indexVA);
            } else {
                editarVA = filtrarVigenciasAfiliaciones.get(indexVA);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaVA == 0) {
                context.update("formularioDialogos:editarFechaInicialVAD");
                context.execute("editarFechaInicialVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 1) {
                context.update("formularioDialogos:editarFechaFinalVAD");
                context.execute("editarFechaFinalVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 2) {
                context.update("formularioDialogos:editarTipoEntidadVAD");
                context.execute("editarTipoEntidadVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 3) {
                context.update("formularioDialogos:editarTerceroVAD");
                context.execute("editarTerceroVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 4) {
                context.update("formularioDialogos:editarNITTerceroVAD");
                context.execute("editarNITTerceroVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 5) {
                context.update("formularioDialogos:editarCodigoVAD");
                context.execute("editarCodigoVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 6) {
                context.update("formularioDialogos:editarEstadoAfiliacionVAD");
                context.execute("editarEstadoAfiliacionVAD.show()");
                cualCeldaVA = -1;
            } else if (cualCeldaVA == 7) {
                context.update("formularioDialogos:editarObservacionesVAD");
                context.execute("editarObservacionesVAD.show()");
                cualCeldaVA = -1;
            }
        }
        indexVA = -1;
        secRegistro = null;
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVA() {
        boolean msn = validarIngresoNuevoRegistro(1);
        if (msn == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (validarFechasRegistro(1) == true) {
                if (nuevaVigenciaA.getFechainicial().before(fechaContratacion)) {
                    dialogoFechaContratacionError();
                }
                int dia = nuevaVigenciaA.getFechainicial().getDate();
                if (dia > 1) {
                    dialogoDiaInicioError();
                }
                cambioVigenciaA = true;
                //CERRAR FILTRADO
                if (banderaVA == 1) {
                    FacesContext c = FacesContext.getCurrentInstance();
                    vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                    vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                    vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                    vATercero.setFilterStyle("display: none; visibility: hidden;");
                    vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                    vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                    vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
                    vANITTercero.setFilterStyle("display: none; visibility: hidden;");
                    vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
                    vACodigo.setFilterStyle("display: none; visibility: hidden;");
                    vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
                    vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
                    vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
                    vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "270";
                    RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                    banderaVA = 0;
                    filtrarVigenciasAfiliaciones = null;
                    tipoListaVA = 0;
                }
                //AGREGAR REGISTRO A LA LISTA VIGENCIAS
                k++;
                l = BigInteger.valueOf(k);

                nuevaVigenciaA.setSecuencia(l);
                nuevaVigenciaA.setEmpleado(empleado);
                listVigenciasAfiliaciones.add(nuevaVigenciaA);
                listVACrear.add(nuevaVigenciaA);
                //
                nuevaVigenciaA = new VigenciasAfiliaciones();
                nuevaVigenciaA.setTipoentidad(new TiposEntidades());
                nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
                nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
                limpiarNuevaVA();
                //
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                indexVA = -1;
                secRegistro = null;
                infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
                context.update("form:datosVAVigencia");
                context.update("form:informacionRegistro");
                context.execute("NuevoRegistroVA.hide()");
            } else {
                context.execute("errorFechas.show()");
            }
        }
    }

    /**
     * Limpia los elementos de una nueva vigencia prorrateo
     */
    public void limpiarNuevaVA() {
        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
        indexVA = -1;
        secRegistro = null;

    }

    public void cancelarNuevaVA() {
        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
        indexVA = -1;
        secRegistro = null;
        cambioVigenciaA = false;
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        if (indexVA >= 0) {
            if (cambioVigenciaA == false) {
                duplicarVigenciaA();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarGuardarSinSalir.show()");

            }
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarVigenciaA() {
        duplicarVA = new VigenciasAfiliaciones();
        k++;
        BigDecimal var = BigDecimal.valueOf(k);
        l = BigInteger.valueOf(k);

        if (tipoListaVA == 0) {
            duplicarVA.setSecuencia(l);
            duplicarVA.setFechafinal(listVigenciasAfiliaciones.get(indexVA).getFechafinal());
            duplicarVA.setFechainicial(listVigenciasAfiliaciones.get(indexVA).getFechainicial());
            duplicarVA.setTercerosucursal(listVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
            duplicarVA.setTipoentidad(listVigenciasAfiliaciones.get(indexVA).getTipoentidad());
            duplicarVA.setCodigo(listVigenciasAfiliaciones.get(indexVA).getCodigo());
            duplicarVA.setEstadoafiliacion(listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion());
            duplicarVA.setObservacion(listVigenciasAfiliaciones.get(indexVA).getObservacion());
        } else {
            duplicarVA.setSecuencia(l);
            duplicarVA.setFechafinal(filtrarVigenciasAfiliaciones.get(indexVA).getFechafinal());
            duplicarVA.setFechainicial(filtrarVigenciasAfiliaciones.get(indexVA).getFechainicial());
            duplicarVA.setTercerosucursal(filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
            duplicarVA.setTipoentidad(filtrarVigenciasAfiliaciones.get(indexVA).getTipoentidad());
            duplicarVA.setCodigo(filtrarVigenciasAfiliaciones.get(indexVA).getCodigo());
            duplicarVA.setEstadoafiliacion(filtrarVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion());
            duplicarVA.setObservacion(filtrarVigenciasAfiliaciones.get(indexVA).getObservacion());
        }
        cambioVigenciaA = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:duplicadoVA");
        context.execute("DuplicadoRegistroVA.show()");
        indexVA = -1;
        secRegistro = null;

    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVA() {
        boolean msn = validarIngresoNuevoRegistro(2);
        if (msn == true) {
            if (validarFechasRegistro(2) == true) {
                if (duplicarVA.getFechainicial().before(fechaContratacion)) {
                    dialogoFechaContratacionError();
                }
                int dia = duplicarVA.getFechainicial().getDate();
                if (dia > 1) {
                    dialogoDiaInicioError();
                }

                RequestContext context = RequestContext.getCurrentInstance();
                cambioVigenciaA = true;
                k++;
                l = BigInteger.valueOf(k);
                duplicarVA.setSecuencia(l);
                listVigenciasAfiliaciones.add(duplicarVA);
                listVACrear.add(duplicarVA);
                indexVA = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    context.update("form:ACEPTAR");
                }
                if (banderaVA == 1) {
                    //CERRAR FILTRADO
                    FacesContext c = FacesContext.getCurrentInstance();
                    vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                    vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                    vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                    vATercero.setFilterStyle("display: none; visibility: hidden;");
                    vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                    vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                    vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
                    vANITTercero.setFilterStyle("display: none; visibility: hidden;");
                    vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
                    vACodigo.setFilterStyle("display: none; visibility: hidden;");
                    vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
                    vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
                    vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
                    vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
                    altoTabla = "270";
                    RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                    banderaVA = 0;
                    filtrarVigenciasAfiliaciones = null;
                    tipoListaVA = 0;
                }
                duplicarVA = new VigenciasAfiliaciones();
                limpiarduplicarVA();
                infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
                context.update("form:datosVAVigencia");
                context.update("form:informacionRegistro");
                context.execute("DuplicadoRegistroVA.hide()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        }
    }

    /**
     * Limpia los elementos del duplicar registro Vigencia Prorrateo
     */
    public void limpiarduplicarVA() {
        duplicarVA = new VigenciasAfiliaciones();
        duplicarVA.setTipoentidad(new TiposEntidades());
        duplicarVA.setTercerosucursal(new TercerosSucursales());
        duplicarVA.setEstadoafiliacion(new EstadosAfiliaciones());

    }

    public void cancelarDuplicadoVA() {
        cambioVigenciaA = false;
        duplicarVA = new VigenciasAfiliaciones();
        duplicarVA.setTipoentidad(new TiposEntidades());
        duplicarVA.setTercerosucursal(new TercerosSucursales());
        duplicarVA.setEstadoafiliacion(new EstadosAfiliaciones());
        indexVA = -1;
        secRegistro = null;
    }

    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void validarBorradoVigencia() {
        if (indexVA >= 0) {
            borrarVA();
        }
    }

    /**
     * Metodo que borra una vigencia prorrateo
     */
    public void borrarVA() {
        cambioVigenciaA = true;
        if (tipoListaVA == 0) {
            if (!listVAModificar.isEmpty() && listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                int modIndex = listVAModificar.indexOf(listVigenciasAfiliaciones.get(indexVA));
                listVAModificar.remove(modIndex);
                listVABorrar.add(listVigenciasAfiliaciones.get(indexVA));
            } else if (!listVACrear.isEmpty() && listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                int crearIndex = listVACrear.indexOf(listVigenciasAfiliaciones.get(indexVA));
                listVACrear.remove(crearIndex);
            } else {
                listVABorrar.add(listVigenciasAfiliaciones.get(indexVA));
            }
            listVigenciasAfiliaciones.remove(indexVA);
            infoRegistro = "Cantidad de registros: " + listVigenciasAfiliaciones.size();
        } else {
            if (!listVAModificar.isEmpty() && listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                int modIndex = listVAModificar.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
                listVAModificar.remove(modIndex);
                listVABorrar.add(filtrarVigenciasAfiliaciones.get(indexVA));
            } else if (!listVACrear.isEmpty() && listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                int crearIndex = listVACrear.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
                listVACrear.remove(crearIndex);
            } else {
                listVABorrar.add(filtrarVigenciasAfiliaciones.get(indexVA));
            }
            int VPIndex = listVigenciasAfiliaciones.indexOf(filtrarVigenciasAfiliaciones.get(indexVA));
            listVigenciasAfiliaciones.remove(VPIndex);
            filtrarVigenciasAfiliaciones.remove(indexVA);
            infoRegistro = "Cantidad de registros: " + filtrarVigenciasAfiliaciones.size();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVAVigencia");
        context.update("form:informacionRegistro");
        indexVA = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }

    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        filtradoVigenciaAfiliacion();
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaAfiliacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (banderaVA == 0) {
            //Columnas Tabla VPP
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("width: 60px");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("width: 60px");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("width: 80px");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("width: 80px");
            vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("width: 80px");
            vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("width: 80px");
            vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("width: 80px");
            vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("width: 80px");
            altoTabla = "246";
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 1;
        } else if (banderaVA == 1) {
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (banderaVA == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            vAFechaInicial = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) c.getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "270";
            context.update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        listVABorrar.clear();
        listVACrear.clear();
        listVAModificar.clear();
        indexVA = -1;
        secRegistro = null;
        k = 0;
        fechaFin = null;
        fechaIni = null;
        listVigenciasAfiliaciones = null;
        guardado = true;
        context.update("form:ACEPTAR");
        vigenciaValidaciones = null;
        cambioVigenciaA = false;
        tipoActualizacion = -1;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = ESTRUCTURAS - MOTIVOSLOCALIZACIONES - PROYECTOS)

    /**
     * Metodo que ejecuta los dialogos de estructuras, motivos localizaciones,
     * proyectos
     *
     * @param indice Fila de la tabla
     * @param dlg Dialogo
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     * VigenciaProrrateoProyecto
     */
    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            indexVA = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            getInfoRegistroTipoEntidad();
            context.update("form:TipoEntidadDialogo");
            context.execute("TipoEntidadDialogo.show()");
        } else if (dlg == 1) {
            getInfoRegistroTercero();
            context.update("form:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        } else if (dlg == 2) {
            getInfoRegistroEstado();
            context.update("form:EstadoAfiliacionDialogo");
            context.execute("EstadoAfiliacionDialogo.show()");
        }

    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el motivo localizacion seleccionado (vigencia
     * localizacion)
     */
    public void actualizarTipoEntidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaVA == 0) {
                vigenciaValidaciones = new VigenciasAfiliaciones();
                vigenciaValidaciones.setTipoentidad(tipoEntidadSeleccionado);
                boolean cambio = validarModificacionRegistroTabla();
                if (cambio == true) {
                    listVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                    if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        if (listVAModificar.isEmpty()) {
                            listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                        } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                            listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                        }
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    cambioVigenciaA = true;
                    permitirIndexVA = true;
                }
            } else {
                vigenciaValidaciones = new VigenciasAfiliaciones();
                vigenciaValidaciones.setTipoentidad(tipoEntidadSeleccionado);
                boolean cambio = validarModificacionRegistroTabla();
                if (cambio == true) {
                    filtrarVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                    if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        if (listVAModificar.isEmpty()) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        }
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                    cambioVigenciaA = true;
                    permitirIndexVA = true;
                }
            }
            context.update(":form:editarTipoEntidadVA");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaA.setTipoentidad(tipoEntidadSeleccionado);
            context.update("formularioDialogos:nuevaTipoEntidadVA");
        } else if (tipoActualizacion == 2) {
            duplicarVA.setTipoentidad(tipoEntidadSeleccionado);
            context.update("formularioDialogos:duplicarTipoEntidadVA");
        }
        filtrarTiposEntidades = null;
        tipoEntidadSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:TipoEntidadDialogo");
        context.update("form:lovTipoEntidad");
        context.update("form:aceptarTE");
        context.reset("form:lovTipoEntidad:globalFilter");
        context.execute("TipoEntidadDialogo.hide()");
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioTipoEntidad() {
        filtrarTiposEntidades = null;
        tipoEntidadSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaVA == 0) {
                vigenciaValidaciones = new VigenciasAfiliaciones();
                vigenciaValidaciones.setTercerosucursal(new TercerosSucursales());
                vigenciaValidaciones.getTercerosucursal().setTercero(new Terceros());
                vigenciaValidaciones.getTercerosucursal().setTercero(terceroSeleccionado);
                boolean cambio = validarModificacionRegistroTabla();
                if (cambio == true) {
                    boolean banderaEncuentra = false;
                    int posicion = -1;
                    List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
                    for (int i = 0; i < listTercerosSucursales.size(); i++) {
                        if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                            banderaEncuentra = true;
                            posicion = i;
                        }
                    }
                    if ((banderaEncuentra == true) && (posicion != -1)) {
                        listVigenciasAfiliaciones.get(indexVA).setTercerosucursal(listTercerosSucursales.get(posicion));
                    }
                    if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        if (listVAModificar.isEmpty()) {
                            listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                        } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                            listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                        }
                    }
                }
            } else {
                vigenciaValidaciones = new VigenciasAfiliaciones();
                vigenciaValidaciones.setTercerosucursal(new TercerosSucursales());
                vigenciaValidaciones.getTercerosucursal().setTercero(new Terceros());
                vigenciaValidaciones.getTercerosucursal().setTercero(terceroSeleccionado);
                boolean cambio = validarModificacionRegistroTabla();
                if (cambio == true) {
                    boolean banderaEncuentra = false;
                    int posicion = -1;
                    List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
                    for (int i = 0; i < listTercerosSucursales.size(); i++) {
                        if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                            banderaEncuentra = true;
                            posicion = i;
                        }
                    }
                    if ((banderaEncuentra == true) && (posicion != -1)) {
                        filtrarVigenciasAfiliaciones.get(indexVA).setTercerosucursal(listTercerosSucursales.get(posicion));
                    }
                    if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        if (listVAModificar.isEmpty()) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        }
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            context.update(":form:editarTerceroVA");
        } else if (tipoActualizacion == 1) {
            boolean banderaEncuentra = false;
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if ((banderaEncuentra == true) && (posicion != -1)) {
                nuevaVigenciaA.setTercerosucursal(listTercerosSucursales.get(posicion));
            }
            context.update("formularioDialogos:nuevaTerceroVA");
            context.update("formularioDialogos:nuevaNITTerceroVA");
        } else if (tipoActualizacion == 2) {
            boolean banderaEncuentra = false;
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if ((banderaEncuentra == true) && (posicion != -1)) {
                duplicarVA.setTercerosucursal(listTercerosSucursales.get(posicion));
            }
            context.update("formularioDialogos:duplicarTerceroVA");
            context.update("formularioDialogos:duplicarNITTerceroVA");
        }
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:TerceroDialogo");
        context.update("form:lovTercero");
        context.update("form:aceptarT");
        context.reset("form:lovTercero:globalFilter");
        context.execute("TerceroDialogo.hide()");
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioTerceros() {
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    public void actualizarEstadoAfiliacion() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones.get(indexVA).setEstadoafiliacion(estadoSSeleccionado);
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    }
                }
            } else {
                filtrarVigenciasAfiliaciones.get(indexVA).setEstadoafiliacion(estadoSSeleccionado);
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            context.update(":form:editarEstadoAfiliacionVA");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaA.setEstadoafiliacion(estadoSSeleccionado);
            context.update("formularioDialogos:nuevaEstadoAfiliacionVA");
        } else if (tipoActualizacion == 2) {
            duplicarVA.setEstadoafiliacion(estadoSSeleccionado);
            context.update("formularioDialogos:duplicarEstadoAfiliacionVA");
        }
        filtrarEstadosAfiliaciones = null;
        estadoSSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        context.update("form:EstadoAfiliacionDialogo");
        context.update("form:lovEstadoAfiliacion");
        context.update("form:aceptarEA");
        context.reset("form:lovEstadoAfiliacion:globalFilter");
        context.execute("EstadoAfiliacionDialogo.hide()");
    }

    /**
     * Metodo que cancela la seleccion del motivo localizacion (vigencia
     * localizacion)
     */
    public void cancelarCambioEstadoAfiliacion() {
        filtrarEstadosAfiliaciones = null;
        estadoSSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    ///////////////////////////////////////////////////////////////////////
    /**
     *
     * //LISTA DE VALORES DINAMICA /** Metodo que activa la lista de valores de
     * todas las tablas con respecto al index activo y la columna activa
     */
    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (indexVA >= 0) {
            if (cualCeldaVA == 2) {
                getInfoRegistroTipoEntidad();
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVA == 3) {
                getInfoRegistroTercero();
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVA == 6) {
                getInfoRegistroEstado();
                context.update("form:EstadoAfiliacionDialogo");
                context.execute("EstadoAfiliacionDialogo.show()");
                tipoActualizacion = 0;
            }
        }

    }

    /**
     * Valida un proceso de nuevo registro dentro de la pagina con respecto a la
     * posicion en la pagina
     */
    public void validarNuevoRegistro() {
        if (cambioVigenciaA == false) {
            nuevaVigenciaA = new VigenciasAfiliaciones();
            nuevaVigenciaA.setTipoentidad(new TiposEntidades());
            nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
            nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:NuevoRegistroVA");
            context.execute("NuevoRegistroVA.show()");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardarSinSalir");
            context.execute("confirmarGuardarSinSalir.show()");
        }
    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDFVA();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Prorrateo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDFVA() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVA:datosVAVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasAfiliacionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVA = -1;
        secRegistro = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLSVA();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Afiliaciones
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLSVA() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVA:datosVAVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasAfiliacionesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        indexVA = -1;
        secRegistro = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoListaVA == 0) {
            tipoListaVA = 1;
        }
        infoRegistro = "Cantidad de registros: " + filtrarVigenciasAfiliaciones.size();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasAfiliaciones != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASAFILIACIONES");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASAFILIACIONES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        indexVA = -1;
    }

    //GET - SET 
    public List<VigenciasAfiliaciones> getListVigenciasAfiliaciones() {
        try {
            if (listVigenciasAfiliaciones == null) {
                listVigenciasAfiliaciones = new ArrayList<VigenciasAfiliaciones>();
                if (empleado != null) {
                    listVigenciasAfiliaciones = administrarVigenciasAfiliaciones3.listVigenciasAfiliacionesEmpleado(empleado.getSecuencia());
                    for (int i = 0; i < listVigenciasAfiliaciones.size(); i++) {
                        if (listVigenciasAfiliaciones.get(i).getEstadoafiliacion() == null) {
                            listVigenciasAfiliaciones.get(i).setEstadoafiliacion(new EstadosAfiliaciones());
                        }
                        if (listVigenciasAfiliaciones.get(i).getTercerosucursal().getTercero() == null) {
                            listVigenciasAfiliaciones.get(i).getTercerosucursal().setTercero(new Terceros());
                        }
                        if (listVigenciasAfiliaciones.get(i).getTipoentidad() == null) {
                            listVigenciasAfiliaciones.get(i).setTipoentidad(new TiposEntidades());
                        }
                    }
                }
            }
            return listVigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error en getListVigenciasAfiliaciones : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasAfiliaciones(List<VigenciasAfiliaciones> listVigenciasAfiliaciones) {
        this.listVigenciasAfiliaciones = listVigenciasAfiliaciones;
    }

    public List<VigenciasAfiliaciones> getFiltrarVigenciasAfiliaciones() {
        return filtrarVigenciasAfiliaciones;
    }

    public void setFiltrarVigenciasAfiliaciones(List<VigenciasAfiliaciones> filtrarVigenciasAfiliaciones) {
        this.filtrarVigenciasAfiliaciones = filtrarVigenciasAfiliaciones;
    }

    public List<TiposEntidades> getListTiposEntidades() {
        try {
            listTiposEntidades = new ArrayList<TiposEntidades>();
            listTiposEntidades = administrarVigenciasAfiliaciones3.listTiposEntidades();

            return listTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error getListTiposEntidades " + e.toString());
            return null;
        }
    }

    public void setListTiposEntidades(List<TiposEntidades> listTiposEntidades) {
        this.listTiposEntidades = listTiposEntidades;
    }

    public TiposEntidades getTipoEntidadSeleccionado() {
        return tipoEntidadSeleccionado;
    }

    public void setTipoEntidadSeleccionado(TiposEntidades tipoEntidadSeleccionado) {
        this.tipoEntidadSeleccionado = tipoEntidadSeleccionado;
    }

    public List<TiposEntidades> getFiltrarTiposEntidades() {
        return filtrarTiposEntidades;
    }

    public void setFiltrarTiposEntidades(List<TiposEntidades> filtrarTiposEntidades) {
        this.filtrarTiposEntidades = filtrarTiposEntidades;
    }

    public List<Terceros> getListTerceros() {
        try {
            listTerceros = new ArrayList<Terceros>();
            listTerceros = administrarVigenciasAfiliaciones3.listTerceros();

            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error getListTerceros " + e.toString());
            return null;
        }
    }

    public void setListTerceros(List<Terceros> listTerceros) {
        this.listTerceros = listTerceros;
    }

    public Terceros getTerceroSeleccionado() {
        return terceroSeleccionado;
    }

    public void setTerceroSeleccionado(Terceros terceroSeleccionado) {
        this.terceroSeleccionado = terceroSeleccionado;
    }

    public List<Terceros> getFiltrarTerceros() {
        return filtrarTerceros;
    }

    public void setFiltrarTerceros(List<Terceros> filtrarTerceros) {
        this.filtrarTerceros = filtrarTerceros;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<VigenciasAfiliaciones> getListVAModificar() {
        return listVAModificar;
    }

    public void setListVAModificar(List<VigenciasAfiliaciones> listVAModificar) {
        this.listVAModificar = listVAModificar;
    }

    public VigenciasAfiliaciones getNuevaVigenciaA() {
        return nuevaVigenciaA;
    }

    public void setNuevaVigenciaA(VigenciasAfiliaciones nuevaVigenciaA) {
        this.nuevaVigenciaA = nuevaVigenciaA;
    }

    public List<VigenciasAfiliaciones> getListVABorrar() {
        return listVABorrar;
    }

    public void setListVABorrar(List<VigenciasAfiliaciones> listVABorrar) {
        this.listVABorrar = listVABorrar;
    }

    public VigenciasAfiliaciones getEditarVA() {
        return editarVA;
    }

    public void setEditarVA(VigenciasAfiliaciones editarVA) {
        this.editarVA = editarVA;
    }

    public VigenciasAfiliaciones getDuplicarVA() {
        return duplicarVA;
    }

    public void setDuplicarVA(VigenciasAfiliaciones duplicarVA) {
        this.duplicarVA = duplicarVA;
    }

    public List<VigenciasAfiliaciones> getListVACrear() {
        return listVACrear;
    }

    public void setListVACrear(List<VigenciasAfiliaciones> listVACrear) {
        this.listVACrear = listVACrear;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public List<EstadosAfiliaciones> getListEstadosAfiliaciones() {
        try {
            listEstadosAfiliaciones = new ArrayList<EstadosAfiliaciones>();
            listEstadosAfiliaciones = administrarVigenciasAfiliaciones3.listEstadosAfiliaciones();

            return listEstadosAfiliaciones;
        } catch (Exception e) {
            return null;
        }

    }

    public void setListEstadosAfiliaciones(List<EstadosAfiliaciones> listEstadosAfiliaciones) {
        this.listEstadosAfiliaciones = listEstadosAfiliaciones;
    }

    public EstadosAfiliaciones getEstadoSSeleccionado() {
        return estadoSSeleccionado;
    }

    public void setEstadoSSeleccionado(EstadosAfiliaciones estadoSSeleccionado) {
        this.estadoSSeleccionado = estadoSSeleccionado;
    }

    public List<EstadosAfiliaciones> getFiltrarEstadosAfiliaciones() {
        return filtrarEstadosAfiliaciones;
    }

    public void setFiltrarEstadosAfiliaciones(List<EstadosAfiliaciones> filtrarEstadosAfiliaciones) {
        this.filtrarEstadosAfiliaciones = filtrarEstadosAfiliaciones;
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

    public VigenciasAfiliaciones getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasAfiliaciones vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getInfoRegistroTipoEntidad() {
        getListTiposEntidades();
        if (listTiposEntidades != null) {
            infoRegistroTipoEntidad = "Cantidad de registros : " + listTiposEntidades.size();
        } else {
            infoRegistroTipoEntidad = "Cantidad re registros : 0";
        }
        return infoRegistroTipoEntidad;
    }

    public void setInfoRegistroTipoEntidad(String infoRegistroTipoEntidad) {
        this.infoRegistroTipoEntidad = infoRegistroTipoEntidad;
    }

    public String getInfoRegistroTercero() {
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

    public String getInfoRegistroEstado() {
        getListEstadosAfiliaciones();
        if (listEstadosAfiliaciones != null) {
            infoRegistroEstado = "Cantidad de registros : " + listEstadosAfiliaciones.size();
        } else {
            infoRegistroEstado = "Cantidad de registros : 0";
        }
        return infoRegistroEstado;
    }

    public void setInfoRegistroEstado(String infoRegistroEstado) {
        this.infoRegistroEstado = infoRegistroEstado;
    }

}

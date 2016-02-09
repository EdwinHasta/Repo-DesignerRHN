/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

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
import Entidades.VigenciasAfiliaciones;
import Entidades.Empleados;
import Entidades.TiposEntidades;
import Entidades.Terceros;
import Entidades.EstadosAfiliaciones;
import Entidades.TercerosSucursales;

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
    //Autocompletar
    private boolean permitirIndexVA;
    //Activo/Desactivo VP Crtl + F11
    private int banderaVA;
    //Columnas Tabla VP
    private Column vAFechaInicial, vAFechaFinal, vATercero, vATipoEntidad, vANITTercero, vACodigo, vAEstadoAfiliacion, vAObservaciones;
    //Otros
    private boolean aceptar;
    //Indices VigenciaProrrateo / VigenciaProrrateoProyecto
    private int indexVA;
    //modificar
    private List<VigenciasAfiliaciones> listVAModificar;
    private boolean guardado, guardarOk;
    //crear VA
    public VigenciasAfiliaciones nuevaVigenciaA;
    private List<VigenciasAfiliaciones> listVACrear;
    private BigInteger l;
    private int k;
    //borrar VA
    private List<VigenciasAfiliaciones> listVABorrar;
    //editar celda
    private VigenciasAfiliaciones editarVA;
    private boolean cambioEditor, aceptarEditar;
    //Indice de celdas VigenciaProrrateo / VigenciaProrrateoProyecto
    private int cualCeldaVA, tipoListaVA;
    //duplicar
    private VigenciasAfiliaciones duplicarVA;
    //Variables Autompletar
    private String tiposEntidades, terceros, estadosAfiliacion;
    private long nit;
    //Duplicar Vigencia Prorrateo
    private boolean cambioVigenciaA;
    private VigenciasAfiliaciones vigenciaValidaciones;
    private Date fechaContratacion;
    private BigInteger backUpSecRegistro;
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    //ALTO TABLA
    private String altoTabla;
    private String infoRegistro;
    //RASTRO
    private BigInteger secRegistro;
    private String infoRegistroTipoEntidad, infoRegistroTercero, infoRegistroEstado;
    //Para validaciones de tipo entidades y terceros
    private int tipoValidacion;

    public ControlEmplVigenciaAfiliacion3() {
        tipoValidacion = 0;
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
     * @param empl Secuencia del Empleado
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
        System.out.println("ControlEmplVigenciaAfiliacion3.validarFechasRegistro");
        System.out.println("Valor i: " + i);
        if (i == 0) {
            VigenciasAfiliaciones auxiliar;
            if (tipoListaVA == 0) {
                auxiliar = listVigenciasAfiliaciones.get(indexVA);
                System.out.println("Auxiliar: " + auxiliar);
            } else {
                auxiliar = filtrarVigenciasAfiliaciones.get(indexVA);
                System.out.println("Auxiliar: " + auxiliar);
            }
            retorno = auxiliar.getFechainicial().after(fechaParametro);
            if (auxiliar.getFechafinal() != null && retorno) {
                retorno = auxiliar.getFechainicial().before(auxiliar.getFechafinal());
            }
        }
        if (i == 1) {
            retorno = nuevaVigenciaA.getFechainicial().after(fechaParametro);
            if (nuevaVigenciaA.getFechafinal() != null && retorno) {
                retorno = nuevaVigenciaA.getFechainicial().before(nuevaVigenciaA.getFechafinal());
            }
            if (retorno) {
                retorno = nuevaVigenciaA.getFechainicial().after(fechaParametro);
            }
        }
        if (i == 2) {
            retorno = duplicarVA.getFechainicial().after(fechaParametro);
            if (duplicarVA.getFechafinal() != null && retorno) {
                retorno = duplicarVA.getFechainicial().before(duplicarVA.getFechafinal());
            }
            if (duplicarVA.getFechafinal() == null && retorno) {
                retorno = duplicarVA.getFechainicial().after(fechaParametro);
            }
        }
        System.out.println("Valor retorno: " + retorno);
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        System.out.println("ControlEmplVigenciaAfiliacion.modificarFechas");
        VigenciasAfiliaciones auxiliar;
        if (tipoListaVA == 0) {
            auxiliar = listVigenciasAfiliaciones.get(i);
        } else {
            auxiliar = filtrarVigenciasAfiliaciones.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            indexVA = i;
            boolean retorno = validarFechasRegistro(0);
            vigenciaValidaciones = auxiliar;
            if (retorno) {
                if (validarFechasRegistroTabla()) {
                    System.out.println("FechasRegistroTabla true");
                    cambiarIndiceVA(i, c);
                    modificarVA(i);
                }
                System.out.println("retorno true");
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
        System.out.println("ControlEmplVigenciaAfiliacion.modificarFechas");
        VigenciasAfiliaciones auxiliar;
        if (tipoListaVA == 0) {
            auxiliar = listVigenciasAfiliaciones.get(i);
        } else {
            auxiliar = filtrarVigenciasAfiliaciones.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            indexVA = i;
            boolean retorno = validarFechasRegistro(0);
            vigenciaValidaciones = auxiliar;
            if (retorno) {
                boolean seModifica = false;
                if (fechaFin == null && auxiliar.getFechafinal() == null) {
                    seModifica = false;
                } else if (fechaFin == null && auxiliar.getFechafinal() != null) {
                    seModifica = true;
                } else {
                    if (fechaFin.equals(auxiliar.getFechafinal())) {
                        seModifica = false;
                    } else {
                        seModifica = true;
                    }
                }
                if (!seModifica) {
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
     * @param confirmarCambio Activa la validación de modificación
     * @param valorConfirmar Nombre del tercero o de la entidad a confirmar
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
        System.out.println("ControlEmplVigenciaAfiliacion3.validarExistenciaTipoEntidad");
        boolean retorno = true;
        List<VigenciasAfiliaciones> listAuxiliar = new ArrayList<VigenciasAfiliaciones>();
        VigenciasAfiliaciones provisional = null;
        ///////////--------////////////
        /*
         * if (i == 0) { provisional = vigenciaValidaciones; }
         */
        if (i == 1) {
            provisional = nuevaVigenciaA;
        }
        if (i == 2) {
            provisional = duplicarVA;
        }
        ///////////--------////////////
        for (int cont = 0; cont < listVigenciasAfiliaciones.size(); cont++) {
            try {
                if (listVigenciasAfiliaciones.get(cont).getTipoentidad().getSecuencia() == provisional.getTipoentidad().getSecuencia()) {
                    listAuxiliar.add(listVigenciasAfiliaciones.get(cont));
                }
            } catch (NullPointerException npe) {
                System.out.println("Dato nulo");
                System.out.println(provisional.getTipoentidad());
                System.out.println(npe.getMessage());
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
                    retorno = provisional.getFechainicial().after(auxiliar.getFechafinal());
                }
            } ///////////--------///////////////////////--------////////////
            else {
                boolean cambiosBefore = false;
                VigenciasAfiliaciones reg1 = listAuxiliar.get(0);
                VigenciasAfiliaciones regN = listAuxiliar.get(tam - 1);
                ///////////--------////////////
                cambiosBefore = provisional.getFechainicial().before(reg1.getFechainicial());
                if (provisional.getFechafinal() != null && cambiosBefore){
                    cambiosBefore = provisional.getFechafinal().before(reg1.getFechainicial());
                }
                ///////////--------////////////
                if (regN.getFechafinal() != null) {
                    cambiosBefore = provisional.getFechainicial().after(regN.getFechafinal());
                    if (provisional.getFechafinal() != null && cambiosBefore){
                        cambiosBefore = provisional.getFechafinal().after(regN.getFechafinal());
                    }
                } else {
                    cambiosBefore = provisional.getFechainicial().after(regN.getFechafinal());
                }
                ///////////--------////////////
                if (cambiosBefore) {
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
                    ubico = provisional.getFechainicial().after(listAuxiliar.get(0).getFechafinal());
                } else {
                    ubico = false;
                }
            }
            retorno=ubico;
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
        if (validacionCamposNulos) {
            //////----------////////////
            validacionFechas = fechasModificacionRegistro(listAuxiliar);
            if (!validacionFechas) {
                retorno = false;
                dialogoFechasErroneas();
            }
            //////----------////////////
            validacionFechaMayorContratacion = fechaContratacionModificacionEmpleado(listAuxiliar);
            if (!validacionFechaMayorContratacion) {
                dialogoFechaContratacionError();
            }
            //////----------////////////
            validacionDiaInicioFecha = diaInicialFechaModificacion();
            if (!validacionDiaInicioFecha) {
                dialogoDiaInicioError();
            }
            //////----------////////////
            if (!validacionFechas) {
                retorno = false;
            }
        } else {
            dialogoCamposNulos();
            retorno = false;
        }
        if (retorno) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones = listAuxiliar;
            } else {
                filtrarVigenciasAfiliaciones = listAuxiliar;
            }
        }
        return retorno;
    }

    public boolean validarModificacionRegistroTabla() {
        //booleano que determina si las validaciones fueron cumplidas
        boolean retorno = true;
        //lista con la que se haran las validaciones
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

        if (validacionCamposNulos) {//Si no hay campos nulos

            if (tipoValidacion == 2) {
                validacionTerceroSucursal = terceroModificacionRegistro(listAuxiliar);
                if (validacionTerceroSucursal == false) {
                    dialogoErrorTercero();
                    retorno = false;
                }
            } else if (tipoValidacion == 1) {
                validacionTiposEntidades = tipoEntidadModificacionRegistro(listAuxiliar);
                if (validacionTiposEntidades == false) {
                    dialogoTipoERepetida();
                    retorno = false;
                }
            }
        } else {
            //dialogoCamposNulos();
            retorno = false;
        }
        if (retorno) {
            if (tipoListaVA == 0) {
                listVigenciasAfiliaciones = listAuxiliar;
            } else {
                filtrarVigenciasAfiliaciones = listAuxiliar;
            }
        }
        tipoValidacion = 0;
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

    public boolean tipoEntidadModificacionRegistro(List<VigenciasAfiliaciones> listaAuxiliar) {
        boolean retorno = true;
        //Objeto con el tipo de entidad seleccionado
        VigenciasAfiliaciones provisional = vigenciaValidaciones;
        //Lista para llenar con los datos del tipo seleccionado
        List<VigenciasAfiliaciones> listAuxiliarTipoESeleccionado = new ArrayList<VigenciasAfiliaciones>();

        for (int cont = 0; cont < listaAuxiliar.size(); cont++) {
            if (listaAuxiliar.get(cont).getTipoentidad().getSecuencia() == provisional.getTipoentidad().getSecuencia()) {
                listAuxiliarTipoESeleccionado.add(listaAuxiliar.get(cont));
                }
        }
        int tam = listAuxiliarTipoESeleccionado.size();
        System.out.println("variable tam: " + tam);
        ///////////--------////////////
        if (tam >= 2) {
            if (provisional.getFechafinal() == null) {
                int conteo = 0;
                for (int de = 0; de < listAuxiliarTipoESeleccionado.size(); de++) {
                    if (listAuxiliarTipoESeleccionado.get(de).getFechafinal() == null) {
                        conteo++;
                    }
                }
                if (conteo > 0) {
                    conteo = 0;
                    for (int de = 0; de < listAuxiliarTipoESeleccionado.size(); de++) {
                        if (listAuxiliarTipoESeleccionado.get(de).getSecuencia() == provisional.getSecuencia()) {
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
                    VigenciasAfiliaciones auxiliar = listAuxiliarTipoESeleccionado.get(0);
                    retorno = provisional.getFechainicial().after(auxiliar.getFechafinal());
                }
            } ///////////--------///////////////////////--------////////////
            else {
                boolean cambiosBefore = false;
                VigenciasAfiliaciones reg1 = listAuxiliarTipoESeleccionado.get(0);
                VigenciasAfiliaciones regN = listAuxiliarTipoESeleccionado.get(tam - 1);
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
                        if (listAuxiliarTipoESeleccionado.get(o + 1).getFechafinal() != null) {
                            if ((provisional.getFechainicial().after(listAuxiliarTipoESeleccionado.get(o - 1).getFechafinal())) && (provisional.getFechafinal().before(listAuxiliarTipoESeleccionado.get(o + 1).getFechainicial()))) {
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
                if ((provisional.getFechainicial().before(listAuxiliarTipoESeleccionado.get(0).getFechainicial())) && (provisional.getFechafinal().before(listAuxiliarTipoESeleccionado.get(0).getFechainicial()))) {
                    ubico = true;
                } else if ((provisional.getFechainicial().after(listAuxiliarTipoESeleccionado.get(0).getFechafinal())) && (provisional.getFechafinal().after(listAuxiliarTipoESeleccionado.get(0).getFechafinal()))) {
                    ubico = true;
                }
            } else {
                if (listAuxiliarTipoESeleccionado.get(0).getFechafinal() != null) {
                    if ((provisional.getFechainicial().after(listAuxiliarTipoESeleccionado.get(0).getFechafinal()))) {
                        ubico = true;
                    }
                } else {
                    ubico = false;
                }
            }
            retorno=ubico;
        }
        if (tam == 0) {
            retorno = true;
        }
        return retorno;
    }

    public boolean camposModificacionRegistro(List<VigenciasAfiliaciones> listaAuxiliar) {
        boolean retorno = true;
        int posicion = indexVA;
        //Si alguno de los campos obligatorios es nulo 
        if ((listaAuxiliar.get(posicion).getFechainicial() == null) || (listaAuxiliar.get(posicion).getTipoentidad().getNombre().isEmpty()) || (listaAuxiliar.get(posicion).getTercerosucursal().getTercero().getNombre().isEmpty())) {
            dialogoCamposNulos();
            retorno = false;
        }
        //Si Observacion excede su limite de caracteres
        if ((listaAuxiliar.get(posicion).getObservacion() != null) && (listaAuxiliar.get(posicion).getObservacion().length() > 100)) {
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

    public boolean terceroModificacionRegistro(List<VigenciasAfiliaciones> listaAuxiliar) {
        boolean retorno = true;
        if (listaAuxiliar.get(indexVA).getEstadoafiliacion() == null) {
            listaAuxiliar.get(indexVA).setEstadoafiliacion(new EstadosAfiliaciones());
        }
        //Long r = administrarVigenciasAfiliaciones3.validacionTercerosSurcursalesNuevaVigencia(empleado.getSecuencia(), listaAuxiliar.get(indexVA).getFechainicial(), BigDecimal(decimal) , listaAuxiliar.get(indexVA).getTercerosucursal().getTercero().getSecuencia());
        Long r = administrarVigenciasAfiliaciones3.validacionTercerosSurcursalesNuevaVigencia(empleado.getSecuencia(), listaAuxiliar.get(indexVA).getFechainicial(), listaAuxiliar.get(indexVA).getEstadoafiliacion().getSecuencia(), listaAuxiliar.get(indexVA).getTercerosucursal().getTercero().getSecuencia());
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
        if (!validacionCamposNulos) {
            dialogoCamposNulos();
        }
        //////----------////////////
        if (!validacionCamposNulos) {
            validacionFechas = validacionFechasNuevoRegistro(i);
            if (!validacionFechas) {
                dialogoFechasErroneas();
            }
            //////----------////////////
            validacionTerceroSucursal = validacionTercerorSucursalesNuevoRegistro(i);
            if (!validacionTerceroSucursal) {
                dialogoErrorTercero();
            }
            //////----------////////////
            validacionTiposEntidades = validarExistenciaTipoEntidad(i);
            if (!validacionTiposEntidades) {
                dialogoTipoERepetida();
            }
            if (!validacionFechas || !validacionTerceroSucursal || !validacionTiposEntidades ) {
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
        if (permitirIndexVA) {
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
        System.out.println("ControlEmplVigenciaAfiliacion3.guardadoGeneral");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina VigenciasProrrateos
     */
    public void guardarCambiosVA() {
        System.out.println("ControlEmplVigenciaAfiliacion3.guardarCambiosVA");
        if (!guardado) {
            System.out.println("Realizando Operaciones.....");
            if (!listVABorrar.isEmpty()) {
                for (int i = 0; i < listVABorrar.size(); i++) {
                    System.out.println("Borrando...");
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
                    System.out.println("Creando...");
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
                    System.out.println("Modificando...");
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
            System.out.println("Se guardaron los datos con exito");
            //listVigenciasAfiliaciones = null;
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            guardado = true;
            context.update("form:ACEPTAR");
            context.update("form:informacionRegistro");
            k = 0;
            System.out.println("ControlEmplVigenciaAfiliacion3.guardarCambiosVA, Empleado: " + empleado);
            permitirIndexVA = true;
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
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
        System.out.println("ControlEmplVigenciaAfiliacion3.agregarNuevaVA");
        boolean msn = validarIngresoNuevoRegistro(1);
        System.out.println("mensaje: " + msn);
        if (msn) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (validarFechasRegistro(1) == true) {
                System.out.println("Nueva vigencia: " + nuevaVigenciaA);
                System.out.println("fechaContratacion: " + fechaContratacion);
                System.out.println("fechaInicial: " + nuevaVigenciaA.getFechainicial());
                if (nuevaVigenciaA.getFechainicial().before(fechaContratacion)) {
                    dialogoFechaContratacionError();
                }
                int dia = nuevaVigenciaA.getFechainicial().getDate();
                /*
                 * LocalDateTime calendarioAux =
                 * LocalDateTime.fromDateFields(nuevaVigenciaA.getFechainicial());
                 * int dia= calendarioAux.getDayOfMonth();
                 */
                if (dia > 1) {
                    dialogoDiaInicioError();
                }
                cambioVigenciaA = true;
                //CERRAR FILTRADO
                System.out.println("banderaVA: " + banderaVA);
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
                if (guardado) {
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
        System.out.println("fin ControlEmplVigenciaAfiliacion3.agregarNuevaVA");
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
        System.out.println("ControlEmplVigenciaAfiliacion3.verificarDuplicarVigencia");
        if (indexVA >= 0) {
            System.out.println("ControlEmplVigenciaAfiliacion3.guardarCambiosVA().Saliendo del for");
            System.out.println("Variable index: " + indexVA);
            if (!cambioVigenciaA) {
                System.out.println("Cambio de vigencia: " + cambioVigenciaA);
                duplicarVigenciaA();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                System.out.println("ControlEmplVigenciaAfiliacion.verificarDuplicarVigencia()");
                System.out.println("Valor de context: " + context);
                context.execute("confirmarGuardarSinSalir.show()");
            }
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de 
     */
    public void duplicarVigenciaA() {
        duplicarVA = new VigenciasAfiliaciones();
        k++;
//        BigDecimal var = BigDecimal.valueOf(k);
        BigInteger var = BigInteger.valueOf(k);
        l = BigInteger.valueOf(k);

        if (tipoListaVA == 0) {
            System.out.println("ControlEmplVigenciaAfiliacion.duplicarVigenciaA, if: ");
            System.out.println("Lista tipoListaVA: " + tipoListaVA);
            duplicarVA.setSecuencia(l);
            System.out.println("Valor de l: " + l);
            duplicarVA.setEmpleado(listVigenciasAfiliaciones.get(indexVA).getEmpleado());
            duplicarVA.setFechafinal(listVigenciasAfiliaciones.get(indexVA).getFechafinal());
            duplicarVA.setFechainicial(listVigenciasAfiliaciones.get(indexVA).getFechainicial());
            duplicarVA.setTercerosucursal(listVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
            duplicarVA.setTipoentidad(listVigenciasAfiliaciones.get(indexVA).getTipoentidad());
            duplicarVA.setCodigo(listVigenciasAfiliaciones.get(indexVA).getCodigo());
            duplicarVA.setEstadoafiliacion(listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion());
            duplicarVA.setObservacion(listVigenciasAfiliaciones.get(indexVA).getObservacion());
            System.out.println("Secuencia Empleado: " + listVigenciasAfiliaciones.get(indexVA).getEmpleado());
        } else {
            System.out.println("ControlEmplVigenciaAfiliacion.duplicarVigenciaA, else: ");
            duplicarVA.setSecuencia(l);
            System.out.println("Valor de l: " + l);
            duplicarVA.setEmpleado(filtrarVigenciasAfiliaciones.get(indexVA).getEmpleado());
            duplicarVA.setFechafinal(filtrarVigenciasAfiliaciones.get(indexVA).getFechafinal());
            duplicarVA.setFechainicial(filtrarVigenciasAfiliaciones.get(indexVA).getFechainicial());
            duplicarVA.setTercerosucursal(filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
            duplicarVA.setTipoentidad(filtrarVigenciasAfiliaciones.get(indexVA).getTipoentidad());
            duplicarVA.setCodigo(filtrarVigenciasAfiliaciones.get(indexVA).getCodigo());
            duplicarVA.setEstadoafiliacion(filtrarVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion());
            duplicarVA.setObservacion(filtrarVigenciasAfiliaciones.get(indexVA).getObservacion());
            System.out.println("Secuencia Empleado: " + filtrarVigenciasAfiliaciones.get(indexVA).getEmpleado());
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
        if (msn) {
            if (validarFechasRegistro(2)) {
                System.out.println("Estoy validando la fecha " + validarFechasRegistro(2));
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
                if (guardado) {
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
                    System.out.println("ControlEmplVigenciaAfilacion3.confirmarDuplicar 'empleado' " + empleado);
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
        System.out.println("ControlEmplVigenciaAfiliacion3.borrarVA");
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
        if (guardado) {
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
                tipoValidacion = 1;//Tipo Entidad
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
                    if (guardado) {
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
                if (cambio) {
                    filtrarVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                    if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                        if (listVAModificar.isEmpty()) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indexVA))) {
                            listVAModificar.add(filtrarVigenciasAfiliaciones.get(indexVA));
                        }
                    }
                    if (guardado) {
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
        tipoActualizacion = -1;/*
         * context.update("form:TipoEntidadDialogo");
         * context.update("form:lovTipoEntidad");
         * context.update("form:aceptarTE");
         */

        context.reset("form:lovTipoEntidad:globalFilter");
        context.execute("lovTipoEntidad.clearFilters()");
        context.execute("TipoEntidadDialogo.hide()");
        context.update("form:datosVAVigencia");

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
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTipoEntidad:globalFilter");
        context.execute("lovTipoEntidad.clearFilters()");
        context.execute("TipoEntidadDialogo.hide()");
    }

    public void actualizarTerceros() {
        RequestContext context = RequestContext.getCurrentInstance();
        boolean banderaEncuentra = false;//booleano que se activa 
        if (tipoActualizacion == 0) {//Si la actualizacion no es para nuevo registro o duplicar
            vigenciaValidaciones = new VigenciasAfiliaciones();
            vigenciaValidaciones.setTercerosucursal(new TercerosSucursales());
            vigenciaValidaciones.getTercerosucursal().setTercero(new Terceros());
            vigenciaValidaciones.getTercerosucursal().setTercero(terceroSeleccionado);
            //booleano cambio que determina si las validaciones se cumplieron
            tipoValidacion = 2;//Tipo Tercweros
            boolean cambio = validarModificacionRegistroTabla();
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if (cambio) {
                if (tipoListaVA == 0) {//si la lista NO tiene filtro
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
                } else {//Si la llista tiene filtro
                    if (banderaEncuentra && (posicion != -1)) {
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
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            context.update(":form:editarTerceroVA");
        } else if (tipoActualizacion == 1) {//Si es para un nuevo registro
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if (banderaEncuentra && (posicion != -1)) {
                nuevaVigenciaA.setTercerosucursal(listTercerosSucursales.get(posicion));
            }
            context.update("formularioDialogos:nuevaTerceroVA");
            context.update("formularioDialogos:nuevaNITTerceroVA");
        } else if (tipoActualizacion == 2) {// Si es para duplicar
            int posicion = -1;
            List<TercerosSucursales> listTercerosSucursales = administrarVigenciasAfiliaciones3.listTercerosSucursales();
            for (int i = 0; i < listTercerosSucursales.size(); i++) {
                if (listTercerosSucursales.get(i).getTercero().getNombre().equalsIgnoreCase(terceroSeleccionado.getNombre())) {
                    banderaEncuentra = true;
                    posicion = i;
                }
            }
            if (banderaEncuentra && (posicion != -1)) {
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
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");
        context.update("form:datosVAVigencia");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovTercero:globalFilter");
        context.execute("lovTercero.clearFilters()");
        context.execute("TerceroDialogo.hide()");
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
            if (guardado) {
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
        tipoActualizacion = -1;/*
         * context.update("form:EstadoAfiliacionDialogo");
         * context.update("form:lovEstadoAfiliacion");
         * context.update("form:aceptarEA");
         */

        context.reset("form:lovEstadoAfiliacion:globalFilter");
        context.execute("lovEstadoAfiliacion.clearFilters()");
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovEstadoAfiliacion:globalFilter");
        context.execute("lovEstadoAfiliacion.clearFilters()");
        context.execute("EstadoAfiliacionDialogo.hide()");
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
        System.out.println("ControlEmplVigenciaAfiliacion3.verificarRastro");
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVigenciasAfiliaciones.isEmpty()) {
            System.out.println("LA LISTA SI TIENE ALGO");
            //   if (listVigenciasAfiliaciones.size() > 0) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASAFILIACIONES");
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    System.out.println("ControlEmplVigenciaAfiliacion3.verificarRastro");
                    System.out.println("La secuencia para la tabla es: " + secRegistro);
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
            System.out.println("LA LISTA ESTA VACIA");
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
        if (listTiposEntidades == null) {
            listTiposEntidades = administrarVigenciasAfiliaciones3.listTiposEntidades();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listTiposEntidades == null || listTiposEntidades.isEmpty()) {
                infoRegistroTipoEntidad = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTipoEntidad = "Cantidad de registros: " + listTiposEntidades.size();
            }
            context.update("form:infoRegistroTipoEntidad");
        }
        return listTiposEntidades;
        /*
         * try { listTiposEntidades = new ArrayList<TiposEntidades>();
         * listTiposEntidades =
         * administrarVigenciasAfiliaciones3.listTiposEntidades();
         *
         * return listTiposEntidades; } catch (Exception e) {
         * System.out.println("Error getListTiposEntidades " + e.toString());
         * return null; }
         */
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

        if (listTerceros == null) {
            listTerceros = administrarVigenciasAfiliaciones3.listTerceros();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listTerceros == null || listTerceros.isEmpty()) {
                infoRegistroTercero = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTercero = "Cantidad de registros: " + listTerceros.size();
            }
            context.update("form:infoRegistroTercero");
        }
        return listTerceros;
        /*
         * try { listTerceros = new ArrayList<Terceros>(); listTerceros =
         * administrarVigenciasAfiliaciones3.listTerceros();
         *
         * return listTerceros; } catch (Exception e) {
         * System.out.println("Error getListTerceros " + e.toString()); return
         * null; }
         */
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
        if (listTiposEntidades == null) {
            listTiposEntidades = administrarVigenciasAfiliaciones3.listTiposEntidades();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listTiposEntidades == null || listTiposEntidades.isEmpty()) {
                infoRegistroTipoEntidad = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTipoEntidad = "Cantidad de registros: " + listTiposEntidades.size();
            }
            context.update("form:infoRegistroTipoEntidad");
        }
        return infoRegistroTipoEntidad;
        /*
         * getListTiposEntidades(); if (listTiposEntidades != null) {
         * infoRegistroTipoEntidad = "Cantidad de registros : " +
         * listTiposEntidades.size(); } else { infoRegistroTipoEntidad =
         * "Cantidad re registros : 0"; } return infoRegistroTipoEntidad;
         */
    }

    public String getInfoRegistroTercero() {
        if (listTerceros == null) {
            listTerceros = administrarVigenciasAfiliaciones3.listTerceros();
            RequestContext context = RequestContext.getCurrentInstance();
            if (listTerceros == null || listTerceros.isEmpty()) {
                infoRegistroTercero = "Cantidad de registros: 0 ";
            } else {
                infoRegistroTercero = "Cantidad de registros: " + listTerceros.size();
            }
            context.update("form:infoRegistroTercero");
        }
        return infoRegistroTercero;
        /*
         * getListTerceros(); if (listTerceros != null) { infoRegistroTercero =
         * "Cantidad de registros : " + listTerceros.size(); } else {
         * infoRegistroTercero = "Cantidad de registros : 0"; } return
         * infoRegistroTercero;
         */
    }

    public void setInfoRegistroTipoEntidad(String infoRegistroTipoEntidad) {
        this.infoRegistroTipoEntidad = infoRegistroTipoEntidad;
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

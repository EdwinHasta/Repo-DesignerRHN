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
import InterfaceAdministrar.AdministrarVigenciasAfiliaciones3Interface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class ControlEmplVigenciaAfiliacion3 implements Serializable {

    @EJB
    AdministrarVigenciasAfiliaciones3Interface administrarVigenciasAfiliaciones3;
    //Vigencias Afiliaciones
    private List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    private List<VigenciasAfiliaciones> filtrarVigenciasAfiliaciones;
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
    private int index;
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
    private int tipoLista;

    public ControlEmplVigenciaAfiliacion3() {
        tipoLista = 0;
        cambioVigenciaA = false;
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
        cualCeldaVA = -1;

        nuevaVigenciaA = new VigenciasAfiliaciones();
        nuevaVigenciaA.setTipoentidad(new TiposEntidades());
        nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
        nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
        listVACrear = new ArrayList<VigenciasAfiliaciones>();

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
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Modifica los elementos de la tabla VigenciaProrrateo que no usan
     * autocomplete
     *
     * @param indice Fila donde se efectu el cambio
     */
    public void modificarVA(int indice) {
        if (tipoListaVA == 0) {
            if (!listVACrear.contains(listVigenciasAfiliaciones.get(indice))) {
                if (listVAModificar.isEmpty()) {
                    listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indice))) {
                    listVAModificar.add(listVigenciasAfiliaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            listVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNit(nit);
            cambioVigenciaA = true;
            indexVA = -1;
        } else {
            if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {

                if (listVAModificar.isEmpty()) {
                    listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                    listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().getTercero().setNit(nit);
            cambioVigenciaA = true;
            indexVA = -1;
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
                    cambioVigenciaA = true;
                    listVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaA = true;
                    filtrarVigenciasAfiliaciones.get(indice).setTipoentidad(listTiposEntidades.get(indiceUnicoElemento));
                }
                listTiposEntidades.clear();
                getListTiposEntidades();
            } else {
                permitirIndexVA = false;
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
                    cambioVigenciaA = true;
                    listVigenciasAfiliaciones.get(indice).getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                } else {
                    cambioVigenciaA = true;
                    filtrarVigenciasAfiliaciones.get(indice).getTercerosucursal().setTercero(listTerceros.get(indiceUnicoElemento));
                }
                listTerceros.clear();
                getListTerceros();
            } else {
                permitirIndexVA = false;
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ESTADOAFILIACION")) {
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
                listEstadosAfiliaciones.clear();
                getListEstadosAfiliaciones();
            } else {
                permitirIndexVA = false;
                context.update("form:EstadoAfiliacionDialogo");
                context.execute("EstadoAfiliacionDialogo.show()");
                tipoActualizacion = 0;
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
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
            } else {
                if (!listVACrear.contains(filtrarVigenciasAfiliaciones.get(indice))) {

                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    } else if (!listVAModificar.contains(filtrarVigenciasAfiliaciones.get(indice))) {
                        listVAModificar.add(filtrarVigenciasAfiliaciones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                cambioVigenciaA = true;
                indexVA = -1;
            }
            cambioVigenciaA = true;
        }
        context.update("form:datosVAVigencia");
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
                listTiposEntidades.clear();
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
                listTerceros.clear();
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
                listEstadosAfiliaciones.clear();
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
    }

    public boolean validarInformacionRegistro(int tipo) {
        boolean retorno = true;
        if (tipo == 0) {
            int posicion = indexVA;
            if ((listVigenciasAfiliaciones.get(posicion).getFechainicial() == null) || (listVigenciasAfiliaciones.get(posicion).getTipoentidad().getNombre().isEmpty()) || (listVigenciasAfiliaciones.get(posicion).getTercerosucursal().getTercero().getNombre().isEmpty()) || (listVigenciasAfiliaciones.get(posicion).getObservacion().length() > 100)) {
                retorno = false;
            }
            if (listVigenciasAfiliaciones.get(posicion).getFechafinal().before(listVigenciasAfiliaciones.get(posicion).getFechainicial())) {
                retorno = false;
            }
        }
        if (tipo == 1) {
            if ((nuevaVigenciaA.getFechainicial() == null) || (nuevaVigenciaA.getTipoentidad().getNombre().isEmpty()) || (nuevaVigenciaA.getTercerosucursal().getTercero().getNombre().isEmpty()) || (nuevaVigenciaA.getObservacion().length() > 100)) {
                retorno = false;
            }
            if (nuevaVigenciaA.getFechainicial() != null) {
                if (nuevaVigenciaA.getFechafinal() != null) {
                    if (nuevaVigenciaA.getFechafinal().before(nuevaVigenciaA.getFechainicial())) {
                        retorno = false;
                    }
                }
            }
        }
            if (tipo == 2) {
                if ((duplicarVA.getFechainicial() == null) || (duplicarVA.getTipoentidad().getNombre().isEmpty()) || (duplicarVA.getTercerosucursal().getTercero().getNombre().isEmpty()) || (duplicarVA.getObservacion().length() > 100)) {
                    retorno = false;
                }
                if (duplicarVA.getFechafinal().before(duplicarVA.getFechainicial())) {
                    retorno = false;
                }
            }
            return retorno;
        }

    

    public boolean validarExistenciaTipoEntidad(int i) {
        boolean retorno = true;
        if (i == 0) {
            VigenciasAfiliaciones auxiliar = listVigenciasAfiliaciones.get(index);
            int rep = 0;
            for (int cont = 0; cont < listVigenciasAfiliaciones.size(); i++) {
                if (listVigenciasAfiliaciones.get(cont).getTipoentidad().getSecuencia() == auxiliar.getTipoentidad().getSecuencia()) {
                    rep++;
                }
            }
            if (rep > 0) {
                retorno = false;
            }
        }
        if (i == 1) {
            int rep = 0;
            for (int cont = 0; cont < listVigenciasAfiliaciones.size(); i++) {
                if (listVigenciasAfiliaciones.get(cont).getTipoentidad().getSecuencia() == nuevaVigenciaA.getTipoentidad().getSecuencia()) {
                    rep++;
                }
            }
            if (rep > 0) {
                retorno = false;
            }
        }
        if (i == 2) {
            int rep = 0;
            for (int cont = 0; cont < listVigenciasAfiliaciones.size(); i++) {
                if (listVigenciasAfiliaciones.get(cont).getTipoentidad().getSecuencia() == duplicarVA.getTipoentidad().getSecuencia()) {
                    rep++;
                }
            }
            if (rep > 0) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validacionTercerorSucursalesNuevoRegistro(int i) {
        if (i == 0) {
            administrarVigenciasAfiliaciones3.validacionTercerosSurcursales(empleado.getSecuencia(), listVigenciasAfiliaciones.get(indexVA).getFechainicial(), listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion().getSecuencia(), listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getSecuencia());
        }
        if (i == 1) {
            administrarVigenciasAfiliaciones3.validacionTercerosSurcursales(empleado.getSecuencia(), nuevaVigenciaA.getFechainicial(), nuevaVigenciaA.getEstadoafiliacion().getSecuencia(), nuevaVigenciaA.getTercerosucursal().getTercero().getSecuencia());
        }
        if (i == 2) {
            administrarVigenciasAfiliaciones3.validacionTercerosSurcursales(empleado.getSecuencia(), duplicarVA.getFechainicial(), duplicarVA.getEstadoafiliacion().getSecuencia(), duplicarVA.getTercerosucursal().getTercero().getSecuencia());
        }
        return false;
    }

    public String validarIngresoNuevoRegistro(int i) {
        String mensaje = "Error en ";
        boolean validacionCamposNulos;
        boolean validacionTiposEntidades;
        boolean validacionTerceroSucursal;
        validacionCamposNulos = validarInformacionRegistro(i);
        validacionTiposEntidades = validarExistenciaTipoEntidad(i);
        validacionTerceroSucursal = validacionTercerorSucursalesNuevoRegistro(i);
        if (validacionCamposNulos == false) {
            mensaje = mensaje + "validacionCamposNulos / ";
        }
        if (validacionTerceroSucursal == false) {
            mensaje = mensaje + "validacionTerceroSucursal";
        }
        return mensaje;
    }

    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasProrrateos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndiceVA(int indice, int celda) {
        if (permitirIndexVA == true) {
            indexVA = indice;
            cualCeldaVA = celda;
            if (cualCeldaVA == 2) {
                tiposEntidades = listVigenciasAfiliaciones.get(indexVA).getTipoentidad().getNombre();
            }
            if (cualCeldaVA == 3) {
                terceros = listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNombre();
            }
            if (cualCeldaVA == 4) {
                nit = listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().getTercero().getNit();
            }
            if (cualCeldaVA == 6) {
                estadosAfiliacion = listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion().getNombre();
            }
        }
        System.out.println("Indice : " + indice + " / Celda : " + celda);
    }

    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        guardarCambiosVA();
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            k = 0;
        }
        cambioVigenciaA = false;
        indexVA = -1;
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
            vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        listVABorrar.clear();
        listVACrear.clear();
        listVAModificar.clear();
        indexVA = -1;
        k = 0;
        listVigenciasAfiliaciones = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVAVigencia");
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
            }
            if (tipoListaVA == 1) {
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
    }

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Agrega una nueva Vigencia Prorrateo
     */
    public void agregarNuevaVA() {
        String msn = validarIngresoNuevoRegistro(1);
        if (!msn.isEmpty()) {
            cambioVigenciaA = true;
            //CERRAR FILTRADO
            if (banderaVA == 1) {
                vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
                vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
                vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
                vATercero.setFilterStyle("display: none; visibility: hidden;");
                vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
                vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
                vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
                vANITTercero.setFilterStyle("display: none; visibility: hidden;");
                vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
                vACodigo.setFilterStyle("display: none; visibility: hidden;");
                vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
                vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
                vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
                vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVAVigencia");
                banderaVA = 0;
                filtrarVigenciasAfiliaciones = null;
                tipoListaVA = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS
            k++;
            l = BigInteger.valueOf(k);
            BigDecimal var = BigDecimal.valueOf(k);
            nuevaVigenciaA.setSecuencia(var);
            nuevaVigenciaA.setEmpleado(empleado);
            System.out.println("Secuencia Nueva Vigencia : " + nuevaVigenciaA.getSecuencia());
            System.out.println("Tam Antes : " + listVigenciasAfiliaciones.size());
            listVigenciasAfiliaciones.add(nuevaVigenciaA);
            System.out.println("Tam Despues : " + listVigenciasAfiliaciones.size());
            listVACrear.add(nuevaVigenciaA);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVAVigencia");
            //
            nuevaVigenciaA = new VigenciasAfiliaciones();
            nuevaVigenciaA.setTipoentidad(new TiposEntidades());
            nuevaVigenciaA.setTercerosucursal(new TercerosSucursales());
            nuevaVigenciaA.setEstadoafiliacion(new EstadosAfiliaciones());
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            indexVA = -1;
        } else {
            System.out.println("Mensaje : " + msn);
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
    }

    //DUPLICAR VL
    /**
     * Metodo que verifica que proceso de duplicar se genera con respecto a la
     * posicion en la pagina que se tiene
     */
    public void verificarDuplicarVigencia() {
        if (indexVA >= 0) {
            duplicarVigenciaA();
        }
    }

    ///////////////////////////////////////////////////////////////
    /**
     * Duplica una registro de VigenciaProrrateos
     */
    public void duplicarVigenciaA() {
        if (indexVA >= 0) {
            duplicarVA = new VigenciasAfiliaciones();
            k++;
            BigDecimal var = BigDecimal.valueOf(k);
            l = BigInteger.valueOf(k);

            if (tipoListaVA == 0) {
                duplicarVA.setSecuencia(var);
                duplicarVA.setFechafinal(listVigenciasAfiliaciones.get(indexVA).getFechafinal());
                duplicarVA.setFechainicial(listVigenciasAfiliaciones.get(indexVA).getFechainicial());
                duplicarVA.setTercerosucursal(listVigenciasAfiliaciones.get(indexVA).getTercerosucursal());
                duplicarVA.setTipoentidad(listVigenciasAfiliaciones.get(indexVA).getTipoentidad());
                duplicarVA.setCodigo(listVigenciasAfiliaciones.get(indexVA).getCodigo());
                duplicarVA.setEstadoafiliacion(listVigenciasAfiliaciones.get(indexVA).getEstadoafiliacion());
                duplicarVA.setObservacion(listVigenciasAfiliaciones.get(indexVA).getObservacion());



            }
            if (tipoListaVA == 1) {
                duplicarVA.setSecuencia(var);
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
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciaProrrateo
     */
    public void confirmarDuplicarVA() {
        cambioVigenciaA = true;
        k++;
        l = BigInteger.valueOf(k);
        BigDecimal var = BigDecimal.valueOf(k);
        duplicarVA.setSecuencia(var);
        System.out.println("Antes : " + listVigenciasAfiliaciones.size());
        listVigenciasAfiliaciones.add(duplicarVA);
        System.out.println("Despues : " + listVigenciasAfiliaciones.size());
        listVACrear.add(duplicarVA);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVAVigencia");
        indexVA = -1;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (banderaVA == 1) {
            //CERRAR FILTRADO
            vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }
        duplicarVA = new VigenciasAfiliaciones();
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
        }
        if (tipoListaVA == 1) {
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
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVAVigencia");
        indexVA = -1;
        if (guardado == true) {
            guardado = false;
        }

    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        if (indexVA >= 0) {
            filtradoVigenciaAfiliacion();
        }
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia prorrateo
     */
    public void filtradoVigenciaAfiliacion() {
        if (banderaVA == 0) {
            //Columnas Tabla VPP
            vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("width: 60px");
            vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("width: 60px");
            vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("width: 80px");
            vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("width: 80px");
            vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("width: 80px");
            vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("width: 80px");
            vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("width: 80px");
            vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 1;
        } else if (banderaVA == 1) {
            vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
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

        if (banderaVA == 1) {
            vAFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaInicial");
            vAFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vAFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAFechaFinal");
            vAFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vATercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATercero");
            vATercero.setFilterStyle("display: none; visibility: hidden;");
            vATipoEntidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vATipoEntidad");
            vATipoEntidad.setFilterStyle("display: none; visibility: hidden;");
            vANITTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vANITTercero");
            vANITTercero.setFilterStyle("display: none; visibility: hidden;");
            vACodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vACodigo");
            vACodigo.setFilterStyle("display: none; visibility: hidden;");
            vAEstadoAfiliacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAEstadoAfiliacion");
            vAEstadoAfiliacion.setFilterStyle("display: none; visibility: hidden;");
            vAObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVAVigencia:vAObservaciones");
            vAObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVAVigencia");
            banderaVA = 0;
            filtrarVigenciasAfiliaciones = null;
            tipoListaVA = 0;
        }

        listVABorrar.clear();
        listVACrear.clear();
        listVAModificar.clear();
        indexVA = -1;
        k = 0;
        listVigenciasAfiliaciones = null;
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
            context.update("form:TipoEntidadDialogo");
            context.execute("TipoEntidadDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        } else if (dlg == 2) {
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
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    }
                }
            } else {
                filtrarVigenciasAfiliaciones.get(indexVA).setTipoentidad(tipoEntidadSeleccionado);
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
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTipoEntidadVA");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaA.setTipoentidad(tipoEntidadSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTipoEntidadVA");
        } else if (tipoActualizacion == 2) {
            duplicarVA.setTipoentidad(tipoEntidadSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTipoEntidadVA");
        }
        filtrarTiposEntidades = null;
        tipoEntidadSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        tipoActualizacion = -1;
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
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    //Motivo Localizacion
    /**
     * Metodo que actualiza el proyecto seleccionado (vigencia localizacion)
     */
    public void actualizarTerceros() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasAfiliaciones.get(indexVA).getTercerosucursal().setTercero(terceroSeleccionado);
                if (!listVACrear.contains(listVigenciasAfiliaciones.get(indexVA))) {
                    if (listVAModificar.isEmpty()) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    } else if (!listVAModificar.contains(listVigenciasAfiliaciones.get(indexVA))) {
                        listVAModificar.add(listVigenciasAfiliaciones.get(indexVA));
                    }
                }
            } else {
                filtrarVigenciasAfiliaciones.get(indexVA).getTercerosucursal().setTercero(terceroSeleccionado);
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
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            RequestContext context = RequestContext.getCurrentInstance();
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
            RequestContext context = RequestContext.getCurrentInstance();
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
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTerceroVA");
            context.update("formularioDialogos:duplicarNITTerceroVA");
        }
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del proyecto (vigencia localizacion)
     */
    public void cancelarCambioTerceros() {
        filtrarTerceros = null;
        terceroSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        tipoActualizacion = -1;
        permitirIndexVA = true;
    }

    public void actualizarEstadoAfiliacion() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
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
            }
            cambioVigenciaA = true;
            permitirIndexVA = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarEstadoAfiliacionVA");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaA.setEstadoafiliacion(estadoSSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaEstadoAfiliacionVA");
        } else if (tipoActualizacion == 2) {
            duplicarVA.setEstadoafiliacion(estadoSSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarEstadoAfiliacionVA");
        }
        filtrarEstadosAfiliaciones = null;
        estadoSSeleccionado = null;
        aceptar = true;
        indexVA = -1;
        tipoActualizacion = -1;
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
                context.update("form:TipoEntidadDialogo");
                context.execute("TipoEntidadDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVA == 3) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaVA == 6) {
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
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:NuevoRegistroVA");
        context.execute("NuevoRegistroVA.show()");
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
        index = -1;
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
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (indexVA >= 0) {
            if (tipoListaVA == 0) {
                tipoListaVA = 1;
            }
        }
    }

    //GET - SET 
    public List<VigenciasAfiliaciones> getListVigenciasAfiliaciones() {
        try {
            if (listVigenciasAfiliaciones == null) {
                listVigenciasAfiliaciones = new ArrayList<VigenciasAfiliaciones>();
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
            if (listTiposEntidades == null) {
                listTiposEntidades = new ArrayList<TiposEntidades>();
                listTiposEntidades = administrarVigenciasAfiliaciones3.listTiposEntidades();
            }
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
            if (listTerceros == null) {
                listTerceros = new ArrayList<Terceros>();
                listTerceros = administrarVigenciasAfiliaciones3.listTerceros();
            }
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
            if (listEstadosAfiliaciones == null) {
                listEstadosAfiliaciones = new ArrayList<EstadosAfiliaciones>();
                listEstadosAfiliaciones = administrarVigenciasAfiliaciones3.listEstadosAfiliaciones();

            }
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
}

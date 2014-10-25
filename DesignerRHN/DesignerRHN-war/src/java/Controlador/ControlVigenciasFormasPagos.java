/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.MetodosPagos;
import Entidades.Periodicidades;
import Entidades.Sucursales;
import Entidades.VigenciasFormasPagos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplVigenciasFormasPagosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlVigenciasFormasPagos implements Serializable {

    @EJB
    AdministrarEmplVigenciasFormasPagosInterface administrarEmplVigenciasFormasPagos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private BigInteger secuenciaEmpleado;
    private Empleados empleadoSeleccionado;
    private List<VigenciasFormasPagos> listVigenciasFormasPagosPorEmpleado;
    private List<VigenciasFormasPagos> filtrarVigenciasFormasPagosPorEmpleado;
    private List<VigenciasFormasPagos> borrarVigenciasFormasPagosPorEmpleado;
    private List<VigenciasFormasPagos> crearVigenciasFormasPagosPorEmpleado;
    private List<VigenciasFormasPagos> modificarVigenciasFormasPagosPorEmpleado;
    private VigenciasFormasPagos editarVigenciaFormasPagoPorEmpleado;
    private VigenciasFormasPagos nuevaVigenciaFormasPago;
    private VigenciasFormasPagos duplicarVigenciaFormasPago;
    private VigenciasFormasPagos vigenciaSeleccionada;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
//lista estados de valores
    private List<Sucursales> listaSucursales;
    private List<Sucursales> filtradoSucursales;
    private Sucursales sucursalSeleccionada;
    private List<Periodicidades> listaPeriodicidades;
    private List<Periodicidades> filtradoPeriodicidades;
    private Periodicidades PeriodicidadSeleccionada;
    private List<MetodosPagos> listaMetodosPagos;
    private List<MetodosPagos> filtradoMetodosPagos;
    private MetodosPagos metodoPagoSeleccionado;
    //Variables Autompletar
    private String backUpSucursales, periodicidad, metodosPagos, mensajeValidacion;
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
//COLUMNAS
    private Column fechaVigencia, cuenta, fechaCuenta, sucursal, formaPago, tc, metodoPago;
    public String altoTabla;
    public String infoRegistro;

    public ControlVigenciasFormasPagos() {
        empleadoSeleccionado = null;
        secuenciaEmpleado = BigInteger.valueOf(10664356);
        listVigenciasFormasPagosPorEmpleado = null;
        crearVigenciasFormasPagosPorEmpleado = new ArrayList();
        modificarVigenciasFormasPagosPorEmpleado = new ArrayList();
        borrarVigenciasFormasPagosPorEmpleado = new ArrayList();
        listaSucursales = null;
        listaPeriodicidades = null;
        listaMetodosPagos = null;
        //
        permitirIndex = true;
        secRegistro = null;
        //
        editarVigenciaFormasPagoPorEmpleado = new VigenciasFormasPagos();
        //
        nuevaVigenciaFormasPago = new VigenciasFormasPagos();
        nuevaVigenciaFormasPago.setFormapago(new Periodicidades());
        nuevaVigenciaFormasPago.setSucursal(new Sucursales());
        nuevaVigenciaFormasPago.setMetodopago(new MetodosPagos());
        guardado = true;
        altoTabla = "270";
        aceptar = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarEmplVigenciasFormasPagos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct ControlVigenciasCargos: " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(BigInteger sec) {
        RequestContext context = RequestContext.getCurrentInstance();
        empleadoSeleccionado = null;
        secuenciaEmpleado = sec;
        listVigenciasFormasPagosPorEmpleado = null;
        getListVigenciasFormasPagosPorEmpleado();

        if (listVigenciasFormasPagosPorEmpleado != null && !listVigenciasFormasPagosPorEmpleado.isEmpty()) {
            if (listVigenciasFormasPagosPorEmpleado.size() == 1) {
                //INFORMACION REGISTRO
                vigenciaSeleccionada = listVigenciasFormasPagosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (listVigenciasFormasPagosPorEmpleado.size() > 1) {
                //INFORMACION REGISTRO
                vigenciaSeleccionada = listVigenciasFormasPagosPorEmpleado.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
            }

        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        context.update("form:informacionRegistro");
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de Registros: " + filtrarVigenciasFormasPagosPorEmpleado.size();
        context.update("form:informacionRegistro");
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listVigenciasFormasPagosPorEmpleado.isEmpty()) {
            if (secRegistro != null) {
                int result = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASUBICACIONES");
                if (result == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (result == 2) {
                    context.execute("confirmarRastro.show()");
                } else if (result == 3) {
                    context.execute("errorRegistroRastro.show()");
                } else if (result == 4) {
                    context.execute("errorTablaConRastro.show()");
                } else if (result == 5) {
                    context.execute("errorTablaSinRastro.show()");
                }
            } else {
                context.execute("seleccionarRegistro.show()");
            }
        } else {
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASUBICACIONES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
        secRegistro = null;
    }

//Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla VigenciasLocalizaciones
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listVigenciasFormasPagosPorEmpleado.get(index).getSecuencia();

            if (cualCelda == 3) {
                if (tipoLista == 0) {
                    backUpSucursales = listVigenciasFormasPagosPorEmpleado.get(index).getSucursal().getNombre();
                } else {
                    backUpSucursales = filtrarVigenciasFormasPagosPorEmpleado.get(index).getSucursal().getNombre();
                }
            } else if (cualCelda == 4) {
                if (tipoLista == 0) {
                    periodicidad = listVigenciasFormasPagosPorEmpleado.get(index).getFormapago().getNombre();
                } else {
                    periodicidad = filtrarVigenciasFormasPagosPorEmpleado.get(index).getFormapago().getNombre();
                }
            } else if (cualCelda == 6) {
                if (tipoLista == 0) {
                    metodosPagos = listVigenciasFormasPagosPorEmpleado.get(index).getMetodopago().getDescripcion();
                } else {
                    metodosPagos = filtrarVigenciasFormasPagosPorEmpleado.get(index).getMetodopago().getDescripcion();
                }

            }
        }

    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 3) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 4) {
                context.update("form:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCelda == 6) {
                context.update("form:metodosPagosialogo");
                context.execute("metodosPagosialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    /**
     *
     * @param indice
     * @param LND
     * @param dig muestra el dialogo respectivo
     */
    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

            if (dig == 3) {
                context.update("form:sucursalesDialogo");
                context.execute("sucursalesDialogo.show()");
                dig = -1;
            }
            if (dig == 4) {
                context.update("form:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                dig = -1;
            }
            if (dig == 6) {
                context.update("form:metodosPagosialogo");
                context.execute("metodosPagosialogo.show()");
                dig = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlVigenciasAfiliaciones3.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     *
     */
    public void guardarCambiosVigenciasFormasPagos() {
        if (guardado == false) {
            if (!borrarVigenciasFormasPagosPorEmpleado.isEmpty()) {

                administrarEmplVigenciasFormasPagos.borrarVigenciasFormasPagos(borrarVigenciasFormasPagosPorEmpleado);
                borrarVigenciasFormasPagosPorEmpleado.clear();
            }
            if (!crearVigenciasFormasPagosPorEmpleado.isEmpty()) {
                for (int i = 0; i < crearVigenciasFormasPagosPorEmpleado.size(); i++) {
                    if (crearVigenciasFormasPagosPorEmpleado.get(i).getSucursal() != null) {
                        if (crearVigenciasFormasPagosPorEmpleado.get(i).getSucursal().getSecuencia() == null) {
                            crearVigenciasFormasPagosPorEmpleado.get(i).setSucursal(null);
                        }
                        administrarEmplVigenciasFormasPagos.crearVigencasFormasPagos(crearVigenciasFormasPagosPorEmpleado.get(i));
                    } else {
                        mensajeValidacion = "Fecha Inicial";
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:validacioNuevaVigencia");
                        context.execute("validacioNuevaVigencia.show()");
                    }
                }
                crearVigenciasFormasPagosPorEmpleado.clear();
            }
            if (!modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                administrarEmplVigenciasFormasPagos.modificarVigenciasFormasPagos(modificarVigenciasFormasPagosPorEmpleado);
                modificarVigenciasFormasPagosPorEmpleado.clear();
            }
            listVigenciasFormasPagosPorEmpleado = null;
            getListVigenciasFormasPagosPorEmpleado();
            if (listVigenciasFormasPagosPorEmpleado != null && !listVigenciasFormasPagosPorEmpleado.isEmpty()) {
                vigenciaSeleccionada = listVigenciasFormasPagosPorEmpleado.get(0);
                infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");

            context.update("form:datosVigenciasFormasPagos");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    /**
     *
     * @param indice donde se encuentra posicionado
     * @param confirmarCambio nombre de la columna
     * @param valorConfirmar valor ingresado
     */
    public void modificarVigenciasFormasPagos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                System.err.println("Fecha a modificar " + listVigenciasFormasPagosPorEmpleado.get(indice).getFechavigencia());
                for (int z = 0; z < listVigenciasFormasPagosPorEmpleado.size(); z++) {
                    System.err.println("Contador vigenciasformas pagos modificar " + z);
                    if (indice == z) {
                        System.err.println("z == indice " + indice);
                    } else {
                        if (listVigenciasFormasPagosPorEmpleado.get(indice).getFechavigencia().equals(listVigenciasFormasPagosPorEmpleado.get(z).getFechavigencia())) {
                            contador++;
                            z = listVigenciasFormasPagosPorEmpleado.size();
                        }
                    }
                }
                if (contador > 0) {
                    mensajeValidacion = "Fechas Repetidas";
                    context.update("form:validacioModificarVigenciaFechas");
                    context.execute("validacioModificarVigenciaFechas.show()");
                    banderita = false;
                } else {
                    banderita = true;
                }
                if (listVigenciasFormasPagosPorEmpleado.get(indice).getMetodopago().getDescripcion().equals("TRANSFERENCIA")) {
                    if (listVigenciasFormasPagosPorEmpleado.get(indice).getTipocuenta().equals("")) {
                        mensajeValidacion = "Tipo cuenta";
                        context.update("form:validacioModificarMetodoPago");
                        context.execute("validacioModificarMetodoPago.show()");
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (listVigenciasFormasPagosPorEmpleado.get(indice).getCuenta().equals("")) {
                        mensajeValidacion = "Cuenta";
                        context.update("form:validacioModificarMetodoPago");
                        context.execute("validacioModificarMetodoPago.show()");
                        banderita = false;
                    } else {
                        banderita = true;
                    }
                }
                if (banderita == true) {

                    if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {
                        if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                            modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {
                            modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");

                        }
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                System.err.println("Fecha a modificar " + listVigenciasFormasPagosPorEmpleado.get(indice).getFechavigencia());
                for (int z = 0; z < listVigenciasFormasPagosPorEmpleado.size(); z++) {
                    System.err.println("Contador vigenciasformas pagos modificar " + z);
                    if (indice == z) {
                        System.err.println("z == indice");
                    } else {
                        if (listVigenciasFormasPagosPorEmpleado.get(indice).getFechavigencia().equals(listVigenciasFormasPagosPorEmpleado.get(z).getFechavigencia())) {
                            contador++;
                            z = listVigenciasFormasPagosPorEmpleado.size();
                        }
                    }
                }
                if (contador > 0) {
                    mensajeValidacion = "Fechas Repetidas";
                    context.update("form:validacioModificarVigenciaFechas");
                    context.execute("validacioModificarVigenciaFechas.show()");
                    banderita = false;
                } else {
                    banderita = true;
                }
                if (listVigenciasFormasPagosPorEmpleado.get(indice).getMetodopago().getDescripcion().equals("TRANSFERENCIA")) {
                    if (listVigenciasFormasPagosPorEmpleado.get(indice).getTipocuenta().equals("")) {
                        mensajeValidacion = "Tipo cuenta";
                        context.update("form:validacioModificarMetodoPago");
                        context.execute("validacioModificarMetodoPago.show()");
                        banderita = false;
                    } else {
                        banderita = true;
                    }

                    if (listVigenciasFormasPagosPorEmpleado.get(indice).getCuenta().equals("")) {
                        mensajeValidacion = "Cuenta";
                        context.update("form:validacioModificarMetodoPago");
                        context.execute("validacioModificarMetodoPago.show()");
                        banderita = false;
                    } else {
                        banderita = true;
                    }
                }
                if (banderita == true) {
                    if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {

                        if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                            modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                        } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {
                            modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                            context.update("form:ACEPTAR");
                        }
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosVigenciasFormasPagos");
        } else if (confirmarCambio.equalsIgnoreCase("SUCURSAL")) {
            if (!valorConfirmar.isEmpty()) {
                if (!listVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().getNombre().equals("")) {
                    if (tipoLista == 0) {
                        listVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().setNombre(backUpSucursales);
                    } else {
                        listVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().setNombre(backUpSucursales);
                    }

                    for (int i = 0; i < listaSucursales.size(); i++) {
                        if (listaSucursales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                            indiceUnicoElemento = i;
                            coincidencias++;
                        }
                    }

                    if (coincidencias == 1) {
                        if (tipoLista == 0) {
                            listVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(listaSucursales.get(indiceUnicoElemento));
                        } else {
                            filtrarVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(listaSucursales.get(indiceUnicoElemento));
                        }
                        listaSucursales.clear();
                        getListaSucursales();
                        //getListaTiposFamiliares();

                    } else {
                        permitirIndex = false;
                        context.update("form:sucursalesDialogo");
                        context.execute("sucursalesDialogo.show()");
                        tipoActualizacion = 0;
                    }
                } else {

                    if (tipoLista == 0) {
                        listVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().setNombre(backUpSucursales);
                        listVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(new Sucursales());
                    } else {
                        filtrarVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().setNombre(backUpSucursales);
                        filtrarVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(new Sucursales());
                    }
                    coincidencias = 1;

                    tipoActualizacion = 0;

                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {

                            if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                                modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                            } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {
                                modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                            }
                            if (guardado == true) {
                                guardado = false;
                            }
                        }
                        index = -1;
                        secRegistro = null;
                    } else {
                        if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {

                            if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                                modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                            } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {
                                modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                            }
                            if (guardado == true) {
                                guardado = false;
                            }
                        }
                        index = -1;
                        secRegistro = null;
                    }
                }

                context.update("form:datosVigenciasFormasPagos");
                context.update("form:ACEPTAR");

            } else {
                listaSucursales.clear();
                getListaSucursales();
                if (tipoLista == 0) {
                    listVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(new Sucursales());
                } else {
                    listVigenciasFormasPagosPorEmpleado.get(indice).setSucursal(new Sucursales());
                }
                context.update("form:datosVigenciasFormasPagos");
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");

            }

        } else if (confirmarCambio.equalsIgnoreCase("FORMAPAGO")) {

            if (tipoLista == 0) {
                listVigenciasFormasPagosPorEmpleado.get(indice).getFormapago().setNombre(periodicidad);
            } else {
                filtrarVigenciasFormasPagosPorEmpleado.get(indice).getFormapago().setNombre(periodicidad);
            }
            for (int i = 0; i < listaPeriodicidades.size(); i++) {
                if (listaPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasFormasPagosPorEmpleado.get(indice).setFormapago(listaPeriodicidades.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasFormasPagosPorEmpleado.get(indice).setFormapago(listaPeriodicidades.get(indiceUnicoElemento));
                }
                listaPeriodicidades.clear();
                listaPeriodicidades = null;
                getListaPeriodicidades();
            } else {
                permitirIndex = false;
                context.update("form:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                context.update("form:datosVigenciasFormasPagos");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("METODOPAGO")) {
            if (tipoLista == 0) {
                listVigenciasFormasPagosPorEmpleado.get(indice).getMetodopago().setDescripcion(metodosPagos);
            } else {
                filtrarVigenciasFormasPagosPorEmpleado.get(indice).getMetodopago().setDescripcion(metodosPagos);
            }
            for (int i = 0; i < listaMetodosPagos.size(); i++) {
                if (listaMetodosPagos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasFormasPagosPorEmpleado.get(indice).setMetodopago(listaMetodosPagos.get(indiceUnicoElemento));
                } else {
                    filtrarVigenciasFormasPagosPorEmpleado.get(indice).setMetodopago(listaMetodosPagos.get(indiceUnicoElemento));
                }
                listaMetodosPagos.clear();
                listaMetodosPagos = null;
                getListaMetodosPagos();
            } else {
                permitirIndex = false;
                context.update("form:metodosPagosialogo");
                context.execute("metodosPagosialogo.show()");
                context.update("form:metodosPagosialogo");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {

                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(indice))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {

                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(indice))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        context.update("form:ACEPTAR");
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciasFormasPagos");
        mensajeValidacion = "";
        int val = 0;
        if (listVigenciasFormasPagosPorEmpleado.get(indice).getMetodopago().getDescripcion().equalsIgnoreCase("TRANSFERENCIA")) {
            if (listVigenciasFormasPagosPorEmpleado.get(indice).getCuenta() == null) {
                mensajeValidacion = mensajeValidacion + " Cuenta ";
                System.err.println("modificarVigenciasFormasPagos mensaje validacion : " + mensajeValidacion);
                val++;
            }
            if (listVigenciasFormasPagosPorEmpleado.get(indice).getSucursal().getNombre() == null) {
                mensajeValidacion = mensajeValidacion + " Sucursal ";
                val++;
                System.err.println("modificarVigenciasFormasPagos mensaje validacion : " + mensajeValidacion);

            }
            if (listVigenciasFormasPagosPorEmpleado.get(indice).getTipocuenta() == null) {
                mensajeValidacion = mensajeValidacion + " Tipo de cuenta ";
                val++;
                System.err.println("modificarVigenciasFormasPagos mensaje validacion : " + mensajeValidacion);

            }
            if (val > 0) {
                System.err.println("modificarVigenciasFormasPagos mensaje validacion : " + mensajeValidacion);

                context.update("form:validacioModificarMetodoPago");
                context.execute("validacioModificarMetodoPago.show()");
            }
        }
    }

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "270";
            //CERRAR FILTRADO
            fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
            fechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
            cuenta.setFilterStyle("display: none; visibility: hidden;");
            fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
            fechaCuenta.setFilterStyle("display: none; visibility: hidden;");
            sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
            sucursal.setFilterStyle("display: none; visibility: hidden;");
            formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
            formaPago.setFilterStyle("display: none; visibility: hidden;");
            tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
            tc.setFilterStyle("display: none; visibility: hidden;");
            metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
            metodoPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
            bandera = 0;
            filtrarVigenciasFormasPagosPorEmpleado = null;
            tipoLista = 0;
        }

        borrarVigenciasFormasPagosPorEmpleado.clear();
        crearVigenciasFormasPagosPorEmpleado.clear();
        modificarVigenciasFormasPagosPorEmpleado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasFormasPagosPorEmpleado = null;
        getListVigenciasFormasPagosPorEmpleado();
        if (listVigenciasFormasPagosPorEmpleado != null && !listVigenciasFormasPagosPorEmpleado.isEmpty()) {
            vigenciaSeleccionada = listVigenciasFormasPagosPorEmpleado.get(0);
            infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }

        guardado = true;

        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVigenciasFormasPagos");
        context.update("form:informacionRegistro");

    }

    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            altoTabla = "270";
            //CERRAR FILTRADO
            fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
            fechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
            cuenta.setFilterStyle("display: none; visibility: hidden;");
            fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
            fechaCuenta.setFilterStyle("display: none; visibility: hidden;");
            sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
            sucursal.setFilterStyle("display: none; visibility: hidden;");
            formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
            formaPago.setFilterStyle("display: none; visibility: hidden;");
            tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
            tc.setFilterStyle("display: none; visibility: hidden;");
            metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
            metodoPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
            bandera = 0;
            filtrarVigenciasFormasPagosPorEmpleado = null;
            tipoLista = 0;
        }

        borrarVigenciasFormasPagosPorEmpleado.clear();
        crearVigenciasFormasPagosPorEmpleado.clear();
        modificarVigenciasFormasPagosPorEmpleado.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasFormasPagosPorEmpleado = null;
        getListVigenciasFormasPagosPorEmpleado();
        if (listVigenciasFormasPagosPorEmpleado != null && !listVigenciasFormasPagosPorEmpleado.isEmpty()) {
            vigenciaSeleccionada = listVigenciasFormasPagosPorEmpleado.get(0);
            infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        guardado = true;

        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosVigenciasFormasPagos");
        context.update("form:informacionRegistro");

    }

    public void actualizarSucursal() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasFormasPagosPorEmpleado.get(index).setSucursal(sucursalSeleccionada);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarVigenciasFormasPagosPorEmpleado.get(index).setSucursal(sucursalSeleccionada);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormasPagos");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormasPago.setSucursal(sucursalSeleccionada);
            context.update("formularioDialogos:nuevaVigenciaFormasPagos");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormasPago.setSucursal(sucursalSeleccionada);
            context.update("formularioDialogos:duplicarVigenciaFormasPagos");
        }
        filtradoPeriodicidades = null;
        sucursalSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        /*
         context.update("form:sucursalesDialogo");
         context.update("form:lovSucursales");
         context.update("form:aceptarS");*/
        context.reset("form:lovSucursales:globalFilter");
        context.execute("lovSucursales.clearFilters()");
        context.execute("sucursalesDialogo.hide()");
    }

    public void cancelarCambioSucursal() {
        RequestContext context = RequestContext.getCurrentInstance();
        filtradoSucursales = null;
        sucursalSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;

        //guardado = true;
        //context.update("form:ACEPTAR");
        context.reset("form:lovSucursales:globalFilter");
        context.execute("lovSucursales.clearFilters()");
        context.execute("sucursalesDialogo.hide()");

    }

    public void actualizarPeriodicidad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasFormasPagosPorEmpleado.get(index).setFormapago(PeriodicidadSeleccionada);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarVigenciasFormasPagosPorEmpleado.get(index).setFormapago(PeriodicidadSeleccionada);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormasPagos");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormasPago.setFormapago(PeriodicidadSeleccionada);
            context.update("formularioDialogos:nuevaVigenciaFormasPagos");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormasPago.setFormapago(PeriodicidadSeleccionada);
            context.update("formularioDialogos:duplicarVigenciaFormasPagos");
        }
        filtradoPeriodicidades = null;
        PeriodicidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        /*
         context.update("form:periodicidadesDialogo");
         context.update("form:lovperiodicidades");
         context.update("form:aceptarP");*/
        context.reset("form:lovperiodicidades:globalFilter");
        context.execute("lovperiodicidades.clearFilters()");
        context.execute("periodicidadesDialogo.hide()");
    }

    public void cancelarCambioPeriodicidad() {
        filtradoPeriodicidades = null;
        PeriodicidadSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovperiodicidades:globalFilter");
        context.execute("lovperiodicidades.clearFilters()");
        context.execute("periodicidadesDialogo.hide()");
    }

    public void actualizarMetodoPago() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasFormasPagosPorEmpleado.get(index).setMetodopago(metodoPagoSeleccionado);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            } else {
                filtrarVigenciasFormasPagosPorEmpleado.get(index).setMetodopago(metodoPagoSeleccionado);

                if (!crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                    if (modificarVigenciasFormasPagosPorEmpleado.isEmpty()) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    } else if (!modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                        modificarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            permitirIndex = true;
            mensajeValidacion = "";
            int val = 0;
            //validacion cuando se cambia el valor a transferencia utilizando la lista de valores
            if (listVigenciasFormasPagosPorEmpleado.get(index).getMetodopago().getDescripcion().equalsIgnoreCase("TRANSFERENCIA")) {
                if (listVigenciasFormasPagosPorEmpleado.get(index).getCuenta() == null) {
                    mensajeValidacion = mensajeValidacion + " Cuenta ";
                    System.err.println("actualizarMetodoPago mensaje validacion : " + mensajeValidacion);
                    val++;
                }
                if (listVigenciasFormasPagosPorEmpleado.get(index).getSucursal().getNombre() == null) {
                    mensajeValidacion = mensajeValidacion + " Sucursal ";
                    val++;
                    System.err.println("actualizarMetodoPago mensaje validacion : " + mensajeValidacion);

                }
                if (listVigenciasFormasPagosPorEmpleado.get(index).getTipocuenta() == null) {
                    mensajeValidacion = mensajeValidacion + " Tipo de cuenta ";
                    val++;
                    System.err.println("actualizarMetodoPago mensaje validacion : " + mensajeValidacion);

                }
                if (val > 0) {
                    System.err.println("actualizarMetodoPago mensaje validacion : " + mensajeValidacion);

                    context.update("form:validacionModificarMetodoPago");
                    context.execute("validacionModificarMetodoPago.show()");
                }
            }
            context.update("form:datosVigenciasFormasPagos");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormasPago.setMetodopago(metodoPagoSeleccionado);
            context.update("formularioDialogos:nuevaVigenciaFormasPagos");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormasPago.setMetodopago(metodoPagoSeleccionado);
            context.update("formularioDialogos:duplicarVigenciaFormasPagos");
        }
        filtradoMetodosPagos = null;
        metodoPagoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        /*
         context.update("form:metodosPagosialogo");
         context.update("form:lovmetodospagos");
         context.update("form:aceptarMP");*/
        context.reset("form:lovmetodospagos:globalFilter");
        context.execute("lovmetodospagos.clearFilters()");
        context.execute("metodosPagosialogo.hide()");
    }

    public void cancelarCambioMetodoPago() {
        filtradoMetodosPagos = null;
        metodoPagoSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.reset("form:lovmetodospagos:globalFilter");
        context.execute("lovmetodospagos.clearFilters()");
        context.execute("metodosPagosialogo.hide()");
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    public void activarCtrlF11() {
        if (bandera == 0) {
            altoTabla = "246";
            FacesContext c = FacesContext.getCurrentInstance();

            fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
            fechaVigencia.setFilterStyle("width: 50px");
            cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
            cuenta.setFilterStyle("width: 60px");
            fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
            fechaCuenta.setFilterStyle("width: 70px");
            sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
            sucursal.setFilterStyle("width: 190px");
            formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
            formaPago.setFilterStyle("width: 190px");
            tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
            tc.setFilterStyle("width: 15px");
            metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
            metodoPago.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "270";
            FacesContext c = FacesContext.getCurrentInstance();
            fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
            fechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
            cuenta.setFilterStyle("display: none; visibility: hidden;");
            fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
            fechaCuenta.setFilterStyle("display: none; visibility: hidden;");
            sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
            sucursal.setFilterStyle("display: none; visibility: hidden;");
            formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
            formaPago.setFilterStyle("display: none; visibility: hidden;");
            tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
            tc.setFilterStyle("display: none; visibility: hidden;");
            metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
            metodoPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
            bandera = 0;
            filtrarVigenciasFormasPagosPorEmpleado = null;
            tipoLista = 0;
        }
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaFormasPagoPorEmpleado = listVigenciasFormasPagosPorEmpleado.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaFormasPagoPorEmpleado = filtrarVigenciasFormasPagosPorEmpleado.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaVigencia");
                context.execute("editarFechaVigencia.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editCuenta");
                context.execute("editCuenta.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:fechaCuentaEditar");
                context.execute("fechaCuentaEditar.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editSucursal");
                context.execute("editSucursal.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editFormaPago");
                context.execute("editFormaPago.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editTC");
                context.execute("editTC.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editMetodoPago");
                context.execute("editMetodoPago.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void validacionesNuevaVigenciaFormasPagos() {
        int pasa = 0;
        int contador = 0;
        int pasa2 = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevaVigenciaFormasPago.getFechavigencia() == null) {
            mensajeValidacion = " * Fecha Vigencia \n";
            pasa++;
        } else {
            for (int i = 0; i < listVigenciasFormasPagosPorEmpleado.size(); i++) {
                if (listVigenciasFormasPagosPorEmpleado.get(i).getFechavigencia().equals(nuevaVigenciaFormasPago.getFechavigencia())) {
                    contador++;
                }
            }
            if (contador > 0) {
                context.update("form:fechas");
                context.execute("fechas.show()");
                pasa2++;
            }

        }
        if (nuevaVigenciaFormasPago.getFormapago().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + " * Forma de pago \n";
            pasa++;

        }

        if (nuevaVigenciaFormasPago.getMetodopago().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Metodo de pago \n";
            pasa++;

        } else {
            if (nuevaVigenciaFormasPago.getMetodopago().getDescripcion().equals("TRANSFERENCIA")) {
                if (nuevaVigenciaFormasPago.getTipocuenta() == null || nuevaVigenciaFormasPago.getSucursal().getSecuencia() == null || nuevaVigenciaFormasPago.getCuenta() == null) {

                    if (nuevaVigenciaFormasPago.getTipocuenta() == null) {
                        mensajeValidacion = mensajeValidacion + " * Tipo de cuenta \n";
                        pasa++;
                    }
                    if (nuevaVigenciaFormasPago.getSucursal().getSecuencia() == null) {
                        mensajeValidacion = mensajeValidacion + " * Sucursal \n";
                        pasa++;
                    }
                    if (nuevaVigenciaFormasPago.getCuenta() == null) {
                        mensajeValidacion = mensajeValidacion + " * Cuenta \n";
                        pasa++;
                    }
                }
            }
        }
        if (pasa == 0 && pasa2 == 0) {
            agregarNuevaVigenciasFormasPagos();
        } else if (pasa == 0 && pasa2 > 0) {
            context.update("form:fechas");
            context.execute("fechas.show()");
        } else if (pasa > 0 && pasa2 == 0) {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        } else if (pasa > 0 && pasa2 > 0) {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
            context.update("form:fechas");
            context.execute("fechas.show()");
        }
    }

    public void agregarNuevaVigenciasFormasPagos() {

        RequestContext context = RequestContext.getCurrentInstance();
        //TENER PRESENTE ------------------------------------------------------
        /*if (nuevaVigenciaFormasPago.getFechavigencia() == null) {
         mensajeValidacion = " * Fecha Vigencia \n";
         System.out.println("Mensaje validacion : " + mensajeValidacion);
         pasa = false;
         } else {
         for (int i = 0; i < listVigenciasFormasPagosPorEmpleado.size(); i++) {
         if (listVigenciasFormasPagosPorEmpleado.get(i).getFechavigencia().equals(nuevaVigenciaFormasPago.getFechavigencia())) {
         System.out.println("Comparacion for !! if");
         contador++;
         }
         }
         if (contador == 0) {
         pasa = true;
         } else {
         System.out.println("Comparacion FechasRepetidad");
         mensajeValidacion = mensajeValidacion + " * Fechas repetidas \n";
         pasa = false;
         }
         }*/
        //----------------------------------------------------------------------
        //if (pasa == true) {
        if (bandera == 1) {
            altoTabla = "270";
            //CERRAR FILTRADO
            FacesContext c = FacesContext.getCurrentInstance();
            fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
            fechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
            cuenta.setFilterStyle("display: none; visibility: hidden;");
            fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
            fechaCuenta.setFilterStyle("display: none; visibility: hidden;");
            sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
            sucursal.setFilterStyle("display: none; visibility: hidden;");
            formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
            formaPago.setFilterStyle("display: none; visibility: hidden;");
            tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
            tc.setFilterStyle("display: none; visibility: hidden;");
            metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
            metodoPago.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
            bandera = 0;
            filtrarVigenciasFormasPagosPorEmpleado = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigenciaFormasPago.setSecuencia(l);
        nuevaVigenciaFormasPago.setEmpleado(empleadoSeleccionado);
        if (nuevaVigenciaFormasPago.getSucursal().getSecuencia() == null) {
            nuevaVigenciaFormasPago.setSucursal(null);
        }
        crearVigenciasFormasPagosPorEmpleado.add(nuevaVigenciaFormasPago);

        listVigenciasFormasPagosPorEmpleado.add(nuevaVigenciaFormasPago);
        infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
        context.update("form:informacionRegistro");
        nuevaVigenciaFormasPago = new VigenciasFormasPagos();
        nuevaVigenciaFormasPago.setSucursal(new Sucursales());
        // nuevaVigenciaFormasPago.getSucursal().setNombre(" ");
        nuevaVigenciaFormasPago.setFormapago(new Periodicidades());
        // nuevaVigenciaFormasPago.getFormapago().setNombre(" ");
        nuevaVigenciaFormasPago.setMetodopago(new MetodosPagos());
        context.update("form:datosVigenciasFormasPagos");
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        context.execute("NuevoRegistroVigenciasFormasPagos.hide()");
        index = -1;
        secRegistro = null;
        //} else {
        //context.update("form:validacioNuevaVigencia");
        //  context.execute("validacioNuevaVigencia.show()");
        //}
    }

    public void limpiarNuevaVigenciaFormpasPagos() {

        nuevaVigenciaFormasPago = new VigenciasFormasPagos();
        nuevaVigenciaFormasPago.setFormapago(new Periodicidades());
        nuevaVigenciaFormasPago.getFormapago().setNombre(" ");
        nuevaVigenciaFormasPago.setSucursal(new Sucursales());
        nuevaVigenciaFormasPago.getSucursal().setNombre(" ");
        nuevaVigenciaFormasPago.setMetodopago(new MetodosPagos());
        nuevaVigenciaFormasPago.getMetodopago().setDescripcion(" ");
        index = -1;
        secRegistro = null;
        RequestContext.getCurrentInstance().update("form:nuevoTipoEntidad");
    }
    private String nuevoNombreSucursal, nuevoNombrePeriodicidad, nuevoNombreMetodoPago;

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {

        if (Campo.equalsIgnoreCase("SUCURSAL")) {
            if (tipoNuevo == 1) {
                nuevoNombreSucursal = nuevaVigenciaFormasPago.getSucursal().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoNombreSucursal = duplicarVigenciaFormasPago.getSucursal().getNombre();
            }
        } else if (Campo.equalsIgnoreCase("FORMAPAGO")) {
            if (tipoNuevo == 1) {
                nuevoNombrePeriodicidad = nuevaVigenciaFormasPago.getFormapago().getNombre();
            } else if (tipoNuevo == 2) {
                nuevoNombrePeriodicidad = duplicarVigenciaFormasPago.getFormapago().getNombre();
            }
        } else if (Campo.equalsIgnoreCase("METODOPAGO")) {
            nuevoNombreMetodoPago = nuevaVigenciaFormasPago.getMetodopago().getDescripcion();
        } else if (tipoNuevo == 2) {
            nuevoNombreMetodoPago = duplicarVigenciaFormasPago.getMetodopago().getDescripcion();
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("SUCURSAL")) {
            if (!valorConfirmar.isEmpty()) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormasPago.getSucursal().setNombre(nuevoNombreSucursal);
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormasPago.getSucursal().setNombre(nuevoNombreSucursal);
                }
                for (int i = 0; i < listaSucursales.size(); i++) {
                    if (listaSucursales.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }
                if (coincidencias == 1) {
                    if (tipoNuevo == 1) {
                        nuevaVigenciaFormasPago.setSucursal(listaSucursales.get(indiceUnicoElemento));
                        context.update("formularioDialogos:nuevoNombreSucursal");
                    } else if (tipoNuevo == 2) {
                        duplicarVigenciaFormasPago.setSucursal(listaSucursales.get(indiceUnicoElemento));
                        context.update("formularioDialogos:duplicarNombreSucursal");
                    }
                    listaSucursales.clear();
                    getListaSucursales();
                } else {
                    context.update("form:sucursalesDialogo");
                    context.execute("sucursalesDialogo.show()");
                    tipoActualizacion = tipoNuevo;
                    if (tipoNuevo == 1) {
                        context.update("formularioDialogos:nuevoNombreSucursal");
                    } else if (tipoNuevo == 2) {
                        context.update("formularioDialogos:duplicarNombreSucursal");
                    }
                }
            } else {
                listaSucursales.clear();
                getListaSucursales();
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormasPago.setSucursal(new Sucursales());
                    context.update("formularioDialogos:nuevoNombreSucursal");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormasPago.setSucursal(new Sucursales());
                    context.update("formularioDialogos:duplicarNombreSucursal");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("FORMAPAGO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormasPago.getFormapago().setNombre(nuevoNombrePeriodicidad);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormasPago.getFormapago().setNombre(nuevoNombrePeriodicidad);
            }
            for (int i = 0; i < listaPeriodicidades.size(); i++) {
                if (listaPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormasPago.setFormapago(listaPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaFormaPago");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormasPago.setFormapago(listaPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarFormaPago");
                }
                listaSucursales.clear();
                listaSucursales = null;
                getListaSucursales();
            } else {
                context.update("form:periodicidadesDialogo");
                context.execute("periodicidadesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaFormaPago");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarFormaPago");
                }
            }
        }
        if (confirmarCambio.equalsIgnoreCase("METODOPAGO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormasPago.getMetodopago().setDescripcion(nuevoNombreMetodoPago);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormasPago.getMetodopago().setDescripcion(nuevoNombreMetodoPago);
            }
            for (int i = 0; i < listaMetodosPagos.size(); i++) {
                if (listaMetodosPagos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormasPago.setMetodopago(listaMetodosPagos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaMetodoPago");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormasPago.setMetodopago(listaMetodosPagos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarMetodoPago");
                }
                listaSucursales.clear();
                listaSucursales = null;
                getListaSucursales();
            } else {
                context.update("form:metodosPagosialogo");
                context.execute("metodosPagosialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaFormaPago");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarFormaPago");
                }
            }
        }
    }

    public void asignarVariableSucursalNueva(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:sucursalesDialogo");
        context.execute("sucursalesDialogo.show()");
    }

    public void cargarSucursalesNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:sucursalesDialogo");
            context.execute("sucursalesDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();

            context.update("form:sucursalesDialogo");
            context.execute("sucursalesDialogo.show()");
        }
    }

    public void asignarVariableMetodosPagosNueva(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:metodosPagosialogo");
        context.execute("metodosPagosialogo.show()");
    }

    public void cargarMetodosPagosNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:metodosPagosialogo");
            context.execute("metodosPagosialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();

            context.update("form:metodosPagosialogo");
            context.execute("metodosPagosialogo.show()");
        }
    }

    public void asignarVariablePeriodicidadNueva(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:periodicidadesDialogo");
        context.execute("periodicidadesDialogo.show()");
    }

    public void cargarPeriodicidadNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:periodicidadesDialogo");
            context.execute("periodicidadesDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:periodicidadesDialogo");
            context.execute("periodicidadesDialogo.show()");
        }

    }

    //BORRAR VC
    public void borrarVigenciasFormasPagos() {

        if (index >= 0) {

            if (tipoLista == 0) {
                if (!modificarVigenciasFormasPagosPorEmpleado.isEmpty() && modificarVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                    int modIndex = modificarVigenciasFormasPagosPorEmpleado.indexOf(listVigenciasFormasPagosPorEmpleado.get(index));
                    modificarVigenciasFormasPagosPorEmpleado.remove(modIndex);
                    borrarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                } else if (!crearVigenciasFormasPagosPorEmpleado.isEmpty() && crearVigenciasFormasPagosPorEmpleado.contains(listVigenciasFormasPagosPorEmpleado.get(index))) {
                    int crearIndex = crearVigenciasFormasPagosPorEmpleado.indexOf(listVigenciasFormasPagosPorEmpleado.get(index));
                    crearVigenciasFormasPagosPorEmpleado.remove(crearIndex);
                } else {
                    borrarVigenciasFormasPagosPorEmpleado.add(listVigenciasFormasPagosPorEmpleado.get(index));
                }
                listVigenciasFormasPagosPorEmpleado.remove(index);
                infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();

            }
            if (tipoLista == 1) {
                if (!modificarVigenciasFormasPagosPorEmpleado.isEmpty() && modificarVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                    int modIndex = modificarVigenciasFormasPagosPorEmpleado.indexOf(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    modificarVigenciasFormasPagosPorEmpleado.remove(modIndex);
                    borrarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                } else if (!crearVigenciasFormasPagosPorEmpleado.isEmpty() && crearVigenciasFormasPagosPorEmpleado.contains(filtrarVigenciasFormasPagosPorEmpleado.get(index))) {
                    int crearIndex = crearVigenciasFormasPagosPorEmpleado.indexOf(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                    crearVigenciasFormasPagosPorEmpleado.remove(crearIndex);
                } else {
                    borrarVigenciasFormasPagosPorEmpleado.add(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                }
                int VCIndex = listVigenciasFormasPagosPorEmpleado.indexOf(filtrarVigenciasFormasPagosPorEmpleado.get(index));
                listVigenciasFormasPagosPorEmpleado.remove(VCIndex);
                filtrarVigenciasFormasPagosPorEmpleado.remove(index);
                infoRegistro = "Cantidad de registros: " + filtrarVigenciasFormasPagosPorEmpleado.size();

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasFormasPagos");
            context.update("form:informacionRegistro");

            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }

    }

    //DUPLICAR VC
    public void duplicarVigenciaFormasPagos() {
        if (index >= 0) {
            duplicarVigenciaFormasPago = new VigenciasFormasPagos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVigenciaFormasPago.setSecuencia(l);
                duplicarVigenciaFormasPago.setFechavigencia(listVigenciasFormasPagosPorEmpleado.get(index).getFechavigencia());
                duplicarVigenciaFormasPago.setCuenta(listVigenciasFormasPagosPorEmpleado.get(index).getCuenta());
                duplicarVigenciaFormasPago.setEmpleado(listVigenciasFormasPagosPorEmpleado.get(index).getEmpleado());
                duplicarVigenciaFormasPago.setFechacuenta(listVigenciasFormasPagosPorEmpleado.get(index).getFechacuenta());
                duplicarVigenciaFormasPago.setFormapago(listVigenciasFormasPagosPorEmpleado.get(index).getFormapago());
                duplicarVigenciaFormasPago.setMetodopago(listVigenciasFormasPagosPorEmpleado.get(index).getMetodopago());
                duplicarVigenciaFormasPago.setSucursal(listVigenciasFormasPagosPorEmpleado.get(index).getSucursal());
                duplicarVigenciaFormasPago.setTipocuenta(listVigenciasFormasPagosPorEmpleado.get(index).getTipocuenta());
            }
            if (tipoLista == 1) {
                duplicarVigenciaFormasPago.setSecuencia(l);
                duplicarVigenciaFormasPago.setFechavigencia(filtrarVigenciasFormasPagosPorEmpleado.get(index).getFechavigencia());
                duplicarVigenciaFormasPago.setCuenta(filtrarVigenciasFormasPagosPorEmpleado.get(index).getCuenta());
                duplicarVigenciaFormasPago.setEmpleado(filtrarVigenciasFormasPagosPorEmpleado.get(index).getEmpleado());
                duplicarVigenciaFormasPago.setFechacuenta(filtrarVigenciasFormasPagosPorEmpleado.get(index).getFechacuenta());
                duplicarVigenciaFormasPago.setFormapago(filtrarVigenciasFormasPagosPorEmpleado.get(index).getFormapago());
                duplicarVigenciaFormasPago.setMetodopago(filtrarVigenciasFormasPagosPorEmpleado.get(index).getMetodopago());
                duplicarVigenciaFormasPago.setSucursal(filtrarVigenciasFormasPagosPorEmpleado.get(index).getSucursal());
                duplicarVigenciaFormasPago.setTipocuenta(filtrarVigenciasFormasPagosPorEmpleado.get(index).getTipocuenta());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigenciaFormasPagos");
            context.execute("duplicarRegistroVigenciasFormasPagos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        boolean banderaConfirmar = false;

        if (duplicarVigenciaFormasPago.getFormapago().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Forma de pago \n";
            banderaConfirmar = false;

        }
        if (duplicarVigenciaFormasPago.getMetodopago().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Metodo de pago \n";
            banderaConfirmar = false;

        } else {
            if (duplicarVigenciaFormasPago.getMetodopago().getDescripcion().equals("TRANSFERENCIA")) {
                if (duplicarVigenciaFormasPago.getTipocuenta() == null || duplicarVigenciaFormasPago.getSucursal().getSecuencia() == null || nuevaVigenciaFormasPago.getCuenta() == null) {

                    if (duplicarVigenciaFormasPago.getTipocuenta() == null) {
                        mensajeValidacion = mensajeValidacion + " * Tipo de cuenta \n";

                    } else {
                        banderaConfirmar = true;
                    }
                    if (duplicarVigenciaFormasPago.getSucursal().getSecuencia() == null) {
                        mensajeValidacion = mensajeValidacion + " * Sucursal \n";
                        banderaConfirmar = false;

                    }
                    if (duplicarVigenciaFormasPago.getCuenta() == null) {
                        mensajeValidacion = mensajeValidacion + " * Cuenta \n";
                        banderaConfirmar = false;

                    }
                }
            } else {
                duplicarVigenciaFormasPago.getSucursal().setNombre(" ");
                banderaConfirmar = true;

            }
        }

        for (int j = 0; j < listVigenciasFormasPagosPorEmpleado.size(); j++) {
            if (duplicarVigenciaFormasPago.getFechavigencia().equals(listVigenciasFormasPagosPorEmpleado.get(j).getFechavigencia())) {
                contador++;
            }
        }
        if (contador > 0) {
            mensajeValidacion = "Fechas Repetidas";
            banderaConfirmar = false;
        } else {
            banderaConfirmar = true;
        }

        if (banderaConfirmar == true) {
            listVigenciasFormasPagosPorEmpleado.add(duplicarVigenciaFormasPago);
            crearVigenciasFormasPagosPorEmpleado.add(duplicarVigenciaFormasPago);
            infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
            context.update("form:informacionRegistro");
            context.update("form:datosVigenciasFormasPagos");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                altoTabla = "270";
                FacesContext c = FacesContext.getCurrentInstance();
                fechaVigencia = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaVigencia");
                fechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                cuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:cuenta");
                cuenta.setFilterStyle("display: none; visibility: hidden;");
                fechaCuenta = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:fechaCuenta");
                fechaCuenta.setFilterStyle("display: none; visibility: hidden;");
                sucursal = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:sucursal");
                sucursal.setFilterStyle("display: none; visibility: hidden;");
                formaPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagosformaPago");
                formaPago.setFilterStyle("display: none; visibility: hidden;");
                tc = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:tc");
                tc.setFilterStyle("display: none; visibility: hidden;");
                metodoPago = (Column) c.getViewRoot().findComponent("form:datosVigenciasFormasPagos:metodoPago");
                metodoPago.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciasFormasPagos");
                bandera = 0;
                filtrarVigenciasFormasPagosPorEmpleado = null;
                tipoLista = 0;
            }
            duplicarVigenciaFormasPago = new VigenciasFormasPagos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroVigenciasFormasPagos.hide()");

        } else {
            context.update("form:validacioNuevaVigencia");
            context.execute("validacioNuevaVigencia.show()");
        }
    }

    public void limpiarduplicarVigenciasFormasPagos() {
        duplicarVigenciaFormasPago = new VigenciasFormasPagos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVFPEmpleadoExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasFormasPagosPDF", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVFPEmpleadoExportar");
        //DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficiones");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasFormasPagosXLS", false, false, "UTF-8", null, null);
        //exporter.export(context, tabla, "AficionesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

//GETTERS AND SETTERS
    /**
     *
     * @return
     */
    public Empleados getEmpleadoSeleccionado() {
        if (empleadoSeleccionado == null) {
            empleadoSeleccionado = administrarEmplVigenciasFormasPagos.consultarEmpleado(secuenciaEmpleado);
        }
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<VigenciasFormasPagos> getListVigenciasFormasPagosPorEmpleado() {
        try {
            if (listVigenciasFormasPagosPorEmpleado == null) {
                listVigenciasFormasPagosPorEmpleado = administrarEmplVigenciasFormasPagos.consultarVigenciasFormasPagosPorEmpleado(secuenciaEmpleado);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (listVigenciasFormasPagosPorEmpleado == null || listVigenciasFormasPagosPorEmpleado.isEmpty()) {
                infoRegistro = "Cantidad de registros: 0 ";
            } else {
                infoRegistro = "Cantidad de registros: " + listVigenciasFormasPagosPorEmpleado.size();
            }
            context.update("form:informacionRegistro");
            return listVigenciasFormasPagosPorEmpleado;
        } catch (Exception e) {
            return listVigenciasFormasPagosPorEmpleado = null;
        }
    }

    public void setListVigenciasFormasPagosPorEmpleado(List<VigenciasFormasPagos> listVigenciasFormasPagosPorEmpleado) {
        this.listVigenciasFormasPagosPorEmpleado = listVigenciasFormasPagosPorEmpleado;
    }

    public List<VigenciasFormasPagos> getFiltrarVigenciasFormasPagosPorEmpleado() {
        return filtrarVigenciasFormasPagosPorEmpleado;
    }

    public void setFiltrarVigenciasFormasPagosPorEmpleado(List<VigenciasFormasPagos> filtrarVigenciasFormasPagosPorEmpleado) {
        this.filtrarVigenciasFormasPagosPorEmpleado = filtrarVigenciasFormasPagosPorEmpleado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }
    private String infoRegistroSucursales;

    public List<Sucursales> getListaSucursales() {
        listaSucursales = administrarEmplVigenciasFormasPagos.consultarLOVSucursales();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaSucursales == null || listaSucursales.isEmpty()) {
            infoRegistroSucursales = "Cantidad de registros: 0 ";
        } else {
            infoRegistroSucursales = "Cantidad de registros: " + listaSucursales.size();
        }
        context.update("form:infoRegistroSucursales");
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursales> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public List<Sucursales> getFiltradoSucursales() {
        return filtradoSucursales;
    }

    public void setFiltradoSucursales(List<Sucursales> filtradoSucursales) {
        this.filtradoSucursales = filtradoSucursales;
    }

    public Sucursales getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    public void setSucursalSeleccionada(Sucursales sucursalSeleccionada) {
        this.sucursalSeleccionada = sucursalSeleccionada;
    }
    private String infoRegistroPeriodicidades;

    public List<Periodicidades> getListaPeriodicidades() {
        listaPeriodicidades = administrarEmplVigenciasFormasPagos.consultarLOVPerdiocidades();
        RequestContext context = RequestContext.getCurrentInstance();

        if (listaPeriodicidades == null || listaPeriodicidades.isEmpty()) {
            infoRegistroPeriodicidades = "Cantidad de registros: 0 ";
        } else {
            infoRegistroPeriodicidades = "Cantidad de registros: " + listaPeriodicidades.size();
        }
        context.update("form:infoRegistroPeriodicidades");
        return listaPeriodicidades;
    }

    public void setListaPeriodicidades(List<Periodicidades> listaPeriodicidades) {
        this.listaPeriodicidades = listaPeriodicidades;
    }

    public List<Periodicidades> getFiltradoPeriodicidades() {
        return filtradoPeriodicidades;
    }

    public void setFiltradoPeriodicidades(List<Periodicidades> filtradoPeriodicidades) {
        this.filtradoPeriodicidades = filtradoPeriodicidades;
    }

    public Periodicidades getPeriodicidadSeleccionada() {
        return PeriodicidadSeleccionada;
    }

    public void setPeriodicidadSeleccionada(Periodicidades PeriodicidadSeleccionada) {
        this.PeriodicidadSeleccionada = PeriodicidadSeleccionada;
    }
    private String infoRegistroMetodosPagos;

    public List<MetodosPagos> getListaMetodosPagos() {
        listaMetodosPagos = administrarEmplVigenciasFormasPagos.consultarLOVMetodosPagos();
        RequestContext context = RequestContext.getCurrentInstance();

        if (listaMetodosPagos == null || listaMetodosPagos.isEmpty()) {
            infoRegistroMetodosPagos = "Cantidad de registros: 0 ";
        } else {
            infoRegistroMetodosPagos = "Cantidad de registros: " + listaMetodosPagos.size();
        }
        context.update("form:infoRegistroMetodosPagos");
        return listaMetodosPagos;
    }

    public void setListaMetodosPagos(List<MetodosPagos> listaMetodosPagos) {
        this.listaMetodosPagos = listaMetodosPagos;
    }

    public List<MetodosPagos> getFiltradoMetodosPagos() {
        return filtradoMetodosPagos;
    }

    public void setFiltradoMetodosPagos(List<MetodosPagos> filtradoMetodosPagos) {
        this.filtradoMetodosPagos = filtradoMetodosPagos;
    }

    public MetodosPagos getMetodoPagoSeleccionado() {
        return metodoPagoSeleccionado;
    }

    public void setMetodoPagoSeleccionado(MetodosPagos metodoPagoSeleccionado) {
        this.metodoPagoSeleccionado = metodoPagoSeleccionado;
    }

    public VigenciasFormasPagos getEditarVigenciaFormasPagoPorEmpleado() {
        return editarVigenciaFormasPagoPorEmpleado;
    }

    public void setEditarVigenciaFormasPagoPorEmpleado(VigenciasFormasPagos editarVigenciaFormasPagoPorEmpleado) {
        this.editarVigenciaFormasPagoPorEmpleado = editarVigenciaFormasPagoPorEmpleado;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public VigenciasFormasPagos getNuevaVigenciaFormasPago() {
        return nuevaVigenciaFormasPago;
    }

    public void setNuevaVigenciaFormasPago(VigenciasFormasPagos nuevaVigenciaFormasPago) {
        this.nuevaVigenciaFormasPago = nuevaVigenciaFormasPago;
    }

    public VigenciasFormasPagos getDuplicarVigenciaFormasPago() {
        return duplicarVigenciaFormasPago;
    }

    public void setDuplicarVigenciaFormasPago(VigenciasFormasPagos duplicarVigenciaFormasPago) {
        this.duplicarVigenciaFormasPago = duplicarVigenciaFormasPago;
    }

    public VigenciasFormasPagos getVigenciaSeleccionada() {
        return vigenciaSeleccionada;
    }

    public void setVigenciaSeleccionada(VigenciasFormasPagos vigenciaSeleccionada) {
        this.vigenciaSeleccionada = vigenciaSeleccionada;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
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

    public String getInfoRegistroSucursales() {
        return infoRegistroSucursales;
    }

    public void setInfoRegistroSucursales(String infoRegistroSucursales) {
        this.infoRegistroSucursales = infoRegistroSucursales;
    }

    public String getInfoRegistroPeriodicidades() {
        return infoRegistroPeriodicidades;
    }

    public void setInfoRegistroPeriodicidades(String infoRegistroPeriodicidades) {
        this.infoRegistroPeriodicidades = infoRegistroPeriodicidades;
    }

    public String getInfoRegistroMetodosPagos() {
        return infoRegistroMetodosPagos;
    }

    public void setInfoRegistroMetodosPagos(String infoRegistroMetodosPagos) {
        this.infoRegistroMetodosPagos = infoRegistroMetodosPagos;
    }

}

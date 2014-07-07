/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Conceptos;
import Entidades.ClavesSap;
import Entidades.Empresas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarConceptosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlActualizarConceptos implements Serializable {

    @EJB
    AdministrarConceptosInterface administrarConceptos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Conceptos> listConceptos;
    private List<Conceptos> listLOVConceptos;
    private List<Conceptos> filtrarLOVConceptos;
    private List<Conceptos> filtrarConceptos;
    private List<Conceptos> crearConceptos;
    private List<Conceptos> modificarConceptos;
    private Conceptos nuevoConceptos;
    private Conceptos editarConceptos;
    private Conceptos conceptoSeleccionada;
    private Conceptos conceptoLOVSeleccionado;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, claveDebito, claveCredito, indiceDeudor, indiceAcredor, fechaCreacion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger secuenciaEmpresa;
//Empleado

    //autocompletar
    private String backUpClaveDebito;
    private String backUpClaveCredito;
    private List<ClavesSap> listaClavesSap;
    private List<ClavesSap> filtradoClavesSap;
    private ClavesSap claveSapDebito;
    private ClavesSap claveSapCredito;
    private String nuevoParentesco;
    private String infoRegistro;
    private String infoRegistroClaveSapDebito;
    private int tamano;
    private String backUpNombre;
    private BigInteger backUpCodigo;

    private Empresas empresaSeleccionada;
    private Empresas empresaSeleccionadaMostrar;
    private List<Empresas> listaEmpresas;
    private List<Empresas> filtradoEmpresas;
    private String infoRegistroEmpresas;

    public ControlActualizarConceptos() {
        listConceptos = null;
        crearConceptos = new ArrayList<Conceptos>();
        modificarConceptos = new ArrayList<Conceptos>();
        permitirIndex = true;
        guardado = true;
        editarConceptos = new Conceptos();
        nuevoConceptos = new Conceptos();
//        secuenciaEmpresa = new BigInteger("11197246");
        secuenciaEmpresa = null;
        listaClavesSap = null;
        filtradoClavesSap = null;
        tipoLista = 0;
        tamano = 270;
        aceptar = true;
        mostrartodos = true;
        buscarConceptos = false;

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarConceptos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpresa(BigInteger secEmpresa) {
        System.out.println("ControlActualizarConceptos recibirEmpresas secEmpresa : " + secEmpresa);
        if (secEmpresa.equals(new BigInteger("0"))) {
            secEmpresa = null;
        }
        this.secuenciaEmpresa = secEmpresa;
        getEmpresaSeleccionada();
        getListaEmpresas();
        System.out.println("Empresa seleccionada : " + empresaSeleccionada.getNombre());
        getListConceptos();
    }

    public void mostrarInfo(int indice, int celda) {
        int contador = 0;
        int fechas = 0;
        mensajeValidacion = " ";
        index = indice;
        cualCelda = celda;
        RequestContext context = RequestContext.getCurrentInstance();
        if (permitirIndex == true) {
            if (tipoLista == 0) {
                secRegistro = listConceptos.get(index).getSecuencia();

                if (listConceptos.get(indice).getFechacreacion() == null) {
                    listConceptos.get(indice).setFechacreacion(backUpFechaCreacion);
                } else if (!listConceptos.get(indice).getFechacreacion().equals(backUpFechaCreacion) && backUpFechaCreacion != null) {
                    listConceptos.get(indice).setFechacreacion(backUpFechaCreacion);
                }
                index = -1;
                secRegistro = null;

            } else {
                if (filtrarConceptos.get(indice).getFechacreacion() == null) {
                    filtrarConceptos.get(indice).setFechacreacion(backUpFechaCreacion);
                } else if (!filtrarConceptos.get(indice).getFechacreacion().equals(backUpFechaCreacion) && backUpFechaCreacion != null) {
                    filtrarConceptos.get(indice).setFechacreacion(backUpFechaCreacion);
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosActualizarConceptos");

        }

    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlConceptos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros: " + filtrarConceptos.size();
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlConceptos eventoFiltrar ERROR===" + e.getMessage());
        }
    }
    private Date backUpFechaCreacion;

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listConceptos.get(index).getSecuencia();

            if (cualCelda == 0) {
                if (tipoLista == 0) {
                    backUpCodigo = listConceptos.get(index).getCodigo();
                } else {
                    backUpCodigo = filtrarConceptos.get(index).getCodigo();
                }
            }
            if (cualCelda == 1) {
                if (tipoLista == 0) {
                    backUpNombre = listConceptos.get(index).getDescripcion();
                } else {
                    backUpNombre = filtrarConceptos.get(index).getDescripcion();
                }
            }

            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    backUpClaveDebito = listConceptos.get(index).getClavecontabledebito().getClave();
                } else {
                    backUpClaveDebito = filtrarConceptos.get(index).getClavecontabledebito().getClave();
                }
                System.out.println("Cambiar Indice Actualizar Conceptos backUpClaveDebito: " + backUpClaveDebito);
            }
            if (cualCelda == 3) {
                if (tipoLista == 0) {
                    backUpClaveCredito = listConceptos.get(index).getClavecontablecredito().getClave();
                } else {
                    backUpClaveCredito = filtrarConceptos.get(index).getClavecontablecredito().getClave();
                }
                System.out.println("Cambiar Indice Actualizar Conceptos backUpClaveCredito: " + backUpClaveCredito);
            }
            if (cualCelda == 6) {
                if (tipoLista == 0) {
                    backUpFechaCreacion = listConceptos.get(index).getFechacreacion();
                } else {
                    backUpFechaCreacion = filtrarConceptos.get(index).getFechacreacion();
                }
            }

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlConceptos.asignarIndex \n");
            RequestContext context = RequestContext.getCurrentInstance();

            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            if (dig == 2) {
                context.update("form:clavesSapDebitoDialgo");
                context.execute("clavesSapDebitoDialgo.show()");
                dig = -1;
            }
            if (dig == 3) {
                context.update("form:claveSapCreditoDialogo");
                context.execute("claveSapCreditoDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlConceptos.asignarIndex ERROR : ==" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();

            if (cualCelda == 4) {
                context.update("form:clavesSapDebitoDialgo");
                context.execute("clavesSapDebitoDialgo.show()");
                tipoActualizacion = 0;
            }

        }
    }

    public void cancelarModificacion() {
        FacesContext c = FacesContext.getCurrentInstance();
        System.out.println("ControlActualizarConceptos cancelarModificacion");
        if (bandera == 1) {
            //CERRAR FILTRADO
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("display: none; visibility: hidden;");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("display: none; visibility: hidden;");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("display: none; visibility: hidden;");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("display: none; visibility: hidden;");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            bandera = 0;
            filtrarConceptos = null;
            tipoLista = 0;
        }

        modificarConceptos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptos = null;
        guardado = true;
        permitirIndex = true;
        buscarConceptos = false;
        mostrartodos = true;
        getListConceptos();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptos == null || listConceptos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosActualizarConceptos");
        context.update("form:ACEPTAR");
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");
    }

    public void cancelarModificacionCambioEmpresa() {
        FacesContext c = FacesContext.getCurrentInstance();
        System.out.println("ControlActualizarConceptos cancelarModificacion");
        if (bandera == 1) {
            //CERRAR FILTRADO
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("display: none; visibility: hidden;");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("display: none; visibility: hidden;");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("display: none; visibility: hidden;");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("display: none; visibility: hidden;");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            bandera = 0;
            filtrarConceptos = null;
            tipoLista = 0;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        listConceptos = null;
        filtradoEmpresas = null;
        empresaSeleccionadaMostrar = new Empresas();
        aceptar = true;
        System.out.println("Realizando guardarConceptoCambioEmpresa empresaSeleccionada : " + empresaSeleccionada.getNombre());
        getListConceptos();
        context.update("formularioDialogos:lovEmpresas");
        context.reset("formularioDialogos:lovEmpresas:globalFilter");
        context.update("form:nombreEmpresa");
        context.update("form:nitEmpresa");
        context.execute("EmpresasDialogo.hide()");
        System.out.println("Se guardaron los datos con exito");
        context.update("form:datosActualizarConceptos");
        k = 0;

        index = -1;
        guardado = true;
        buscarConceptos = false;
        mostrartodos = true;

        context.update("form:informacionRegistro");
        context.update("form:datosActualizarConceptos");
        context.update("form:ACEPTAR");
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");
    }

    public void cancelarModificacionCambioConceptoEmpresa() {
        FacesContext c = FacesContext.getCurrentInstance();
        System.out.println("ControlActualizarConceptos cancelarModificacion");
        if (bandera == 1) {
            //CERRAR FILTRADO
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("display: none; visibility: hidden;");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("display: none; visibility: hidden;");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("display: none; visibility: hidden;");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("display: none; visibility: hidden;");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            bandera = 0;
            filtrarConceptos = null;
            tipoLista = 0;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        listConceptos = null;
        filtradoEmpresas = null;
        empresaSeleccionadaMostrar = new Empresas();
        aceptar = true;
        System.out.println("Realizando guardarConceptoCambioEmpresa empresaSeleccionada : " + empresaSeleccionada.getNombre());
        getListConceptos();
        context.update("formularioDialogos:lovEmpresas");
        context.reset("formularioDialogos:lovEmpresas:globalFilter");
        context.update("form:nombreEmpresa");
        context.update("form:nitEmpresa");
        context.execute("EmpresasDialogo.hide()");
        System.out.println("Se guardaron los datos con exito");
        context.update("form:datosActualizarConceptos");
        k = 0;
        listConceptos = null;
        filtradoEmpresas = null;
        aceptar = true;
        listLOVConceptos = null;
        getListLOVConceptos();
        context.update("formularioDialogos:lovConceptosPorEmpresa");
        context.execute("buscarConceptosPorEmpresaDialogo.show()");
        System.out.println("Se guardaron los datos con exito");
        context.update("form:datosActualizarConceptos");
        index = -1;
        guardado = true;
        buscarConceptos = false;
        mostrartodos = true;

        context.update("form:informacionRegistro");
        context.update("form:datosActualizarConceptos");
        context.update("form:ACEPTAR");
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");
    }

    public void salir() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 1) {
            //CERRAR FILTRADO
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("display: none; visibility: hidden;");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("display: none; visibility: hidden;");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("display: none; visibility: hidden;");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("display: none; visibility: hidden;");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            bandera = 0;
            filtrarConceptos = null;
            tipoLista = 0;
        }

        crearConceptos.clear();
        modificarConceptos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listConceptos = null;
        guardado = true;
        permitirIndex = true;
        getListConceptos();
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptos == null || listConceptos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            infoRegistro = "Cantidad de registros: " + listConceptos.size();
        }
        context.update("form:informacionRegistro");
        context.update("form:datosActualizarConceptos");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("width: 50px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("width: 145px");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("width: 10px");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("width: 10px");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("width: 10px");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("width: 10px");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            tamano = 270;
            System.out.println("Desactivar");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            codigo = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            claveDebito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveDebito");
            claveDebito.setFilterStyle("display: none; visibility: hidden;");
            claveCredito = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:claveCredito");
            claveCredito.setFilterStyle("display: none; visibility: hidden;");
            indiceDeudor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceDeudor");
            indiceDeudor.setFilterStyle("display: none; visibility: hidden;");
            indiceAcredor = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:indiceAcredor");
            indiceAcredor.setFilterStyle("display: none; visibility: hidden;");
            fechaCreacion = (Column) c.getViewRoot().findComponent("form:datosActualizarConceptos:fechaCreacion");
            fechaCreacion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosActualizarConceptos");
            bandera = 0;
            filtrarConceptos = null;
            tipoLista = 0;
        }
    }

    /**
     *
     * @param indice donde se encuentra posicionado
     * @param confirmarCambio nombre de la columna
     * @param valorConfirmar valor ingresado
     */
    public void modificarActualConcepto(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0, pass = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICARACTUALCONCEPTO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearConceptos.contains(listConceptos.get(indice))) {

                    if (listConceptos.get(indice).getCodigo() == null) {

                        listConceptos.get(indice).setCodigo(backUpCodigo);
                    } else if (!listConceptos.get(indice).getCodigo().equals(backUpCodigo) && backUpCodigo != null) {

                        listConceptos.get(indice).setCodigo(backUpCodigo);
                    }
                    if (listConceptos.get(indice).getDescripcion() == null) {

                        listConceptos.get(indice).setDescripcion(backUpNombre);
                    } else if (!listConceptos.get(indice).getDescripcion().equals(backUpNombre) && backUpNombre != null) {

                        listConceptos.get(indice).setDescripcion(backUpNombre);
                    }
                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(listConceptos.get(indice));
                    } else if (!modificarConceptos.contains(listConceptos.get(indice))) {
                        modificarConceptos.add(listConceptos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }

                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearConceptos.contains(filtrarConceptos.get(indice))) {

                    if (filtrarConceptos.get(indice).getCodigo() == null) {

                        filtrarConceptos.get(indice).setCodigo(backUpCodigo);
                    } else if (!filtrarConceptos.get(indice).getCodigo().equals(backUpCodigo) && backUpCodigo != null) {

                        filtrarConceptos.get(indice).setCodigo(backUpCodigo);
                    }
                    if (filtrarConceptos.get(indice).getDescripcion() == null) {

                        filtrarConceptos.get(indice).setDescripcion(backUpNombre);
                    } else if (!filtrarConceptos.get(indice).getDescripcion().equals(backUpNombre) && backUpNombre != null) {

                        filtrarConceptos.get(indice).setDescripcion(backUpNombre);
                    }

                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(filtrarConceptos.get(indice));
                    } else if (!modificarConceptos.contains(filtrarConceptos.get(indice))) {
                        modificarConceptos.add(filtrarConceptos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }

                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosActualizarConceptos");
        } else if (confirmarCambio.equalsIgnoreCase("CLAVESAPDEBITO")) {
            System.out.println("controlActualizarConceptos.modificarActualizarConcepto CLAVESAPDEBITO");

            if (!listConceptos.get(indice).getClavecontabledebito().getClave().equals("")) {
                if (tipoLista == 0) {
                    listConceptos.get(indice).getClavecontabledebito().setClave(backUpClaveDebito);

                } else {
                    filtrarConceptos.get(indice).getClavecontabledebito().setClave(backUpClaveDebito);
                }

                for (int i = 0; i < listaClavesSap.size(); i++) {
                    if (listaClavesSap.get(i).getClave().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listConceptos.get(indice).setClavecontabledebito(listaClavesSap.get(indiceUnicoElemento));
                    } else {
                        filtrarConceptos.get(indice).setClavecontabledebito(listaClavesSap.get(indiceUnicoElemento));
                    }
                    listaClavesSap.clear();
                    listaClavesSap = null;
                    getListaClavesSap();

                } else {
                    permitirIndex = false;
                    context.update("form:clavesSapDebitoDialgo");
                    context.execute("clavesSapDebitoDialgo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                System.out.println("PUSE UN VACIO");
                listConceptos.get(indice).getClavecontabledebito().setClave(backUpClaveDebito);
                listConceptos.get(indice).setClavecontabledebito(new ClavesSap());
                coincidencias = 1;
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearConceptos.contains(listConceptos.get(indice))) {

                        if (modificarConceptos.isEmpty()) {
                            modificarConceptos.add(listConceptos.get(indice));
                        } else if (!modificarConceptos.contains(listConceptos.get(indice))) {
                            modificarConceptos.add(listConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:datosActualizarConceptos");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearConceptos.contains(filtrarConceptos.get(indice))) {

                        if (modificarConceptos.isEmpty()) {
                            modificarConceptos.add(filtrarConceptos.get(indice));
                        } else if (!modificarConceptos.contains(filtrarConceptos.get(indice))) {
                            modificarConceptos.add(filtrarConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosActualizarConceptos");

        } else if (confirmarCambio.equalsIgnoreCase("CLAVESAPCREDITO")) {
            System.out.println("controlActualizarConceptos.modificarActualizarConcepto CLAVESSAPCREDITO");
            if (!listConceptos.get(indice).getClavecontablecredito().getClave().equals("")) {
                if (tipoLista == 0) {
                    listConceptos.get(indice).getClavecontablecredito().setClave(backUpClaveCredito);

                } else {
                    filtrarConceptos.get(indice).getClavecontablecredito().setClave(backUpClaveCredito);
                }

                for (int i = 0; i < listaClavesSap.size(); i++) {
                    if (listaClavesSap.get(i).getClave().startsWith(valorConfirmar.toUpperCase())) {
                        indiceUnicoElemento = i;
                        coincidencias++;
                    }
                }

                if (coincidencias == 1) {
                    if (tipoLista == 0) {
                        listConceptos.get(indice).setClavecontablecredito(listaClavesSap.get(indiceUnicoElemento));
                    } else {
                        filtrarConceptos.get(indice).setClavecontablecredito(listaClavesSap.get(indiceUnicoElemento));
                    }
                    listaClavesSap.clear();
                    listaClavesSap = null;
                    getListaClavesSap();

                } else {
                    permitirIndex = false;
                    context.update("form:claveSapCreditoDialogo");
                    context.execute("claveSapCreditoDialogo.show()");
                    tipoActualizacion = 0;
                }
            } else {
                System.out.println("PUSE UN VACIO");
                listConceptos.get(indice).getClavecontablecredito().setClave(backUpClaveCredito);
                listConceptos.get(indice).setClavecontablecredito(new ClavesSap());
                coincidencias = 1;
            }

            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    if (!crearConceptos.contains(listConceptos.get(indice))) {

                        if (modificarConceptos.isEmpty()) {
                            modificarConceptos.add(listConceptos.get(indice));
                        } else if (!modificarConceptos.contains(listConceptos.get(indice))) {
                            modificarConceptos.add(listConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:datosActualizarConceptos");
                    }
                    index = -1;
                    secRegistro = null;
                } else {
                    if (!crearConceptos.contains(filtrarConceptos.get(indice))) {

                        if (modificarConceptos.isEmpty()) {
                            modificarConceptos.add(filtrarConceptos.get(indice));
                        } else if (!modificarConceptos.contains(filtrarConceptos.get(indice))) {
                            modificarConceptos.add(filtrarConceptos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                    }
                    index = -1;
                    secRegistro = null;
                }
            }

            context.update("form:datosActualizarConceptos");

        }
        context.update("form:datosActualizarConceptos");
        context.update("form:ACEPTAR");
    }

    public void modificarActualConceptoCamposSinGuardar(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0, pass = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("CONTROLACTUALIZARCONCEPTOS modificarActualConceptoCamposSinGuardar");
            if (tipoLista == 0) {
                if (!crearConceptos.contains(listConceptos.get(indice))) {

                    if (listConceptos.get(indice).getCodigo() == null) {
                        listConceptos.get(indice).setCodigo(backUpCodigo);
                    } else if (!listConceptos.get(indice).getCodigo().equals(backUpCodigo) && backUpCodigo != null) {
                        listConceptos.get(indice).setCodigo(backUpCodigo);
                    }
                    if (listConceptos.get(indice).getDescripcion() == null) {
                        listConceptos.get(indice).setDescripcion(backUpNombre);
                    } else if (!listConceptos.get(indice).getDescripcion().equals(backUpNombre) && backUpNombre != null) {
                        listConceptos.get(indice).setDescripcion(backUpNombre);
                    }

                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearConceptos.contains(filtrarConceptos.get(indice))) {

                    if (filtrarConceptos.get(indice).getCodigo() == null) {
                        filtrarConceptos.get(indice).setCodigo(backUpCodigo);
                    } else if (!filtrarConceptos.get(indice).getCodigo().equals(backUpCodigo) && backUpCodigo != null) {
                        filtrarConceptos.get(indice).setCodigo(backUpCodigo);
                    }
                    if (filtrarConceptos.get(indice).getDescripcion() == null) {
                        filtrarConceptos.get(indice).setDescripcion(backUpNombre);
                    } else if (!filtrarConceptos.get(indice).getDescripcion().equals(backUpNombre) && backUpNombre != null) {
                        filtrarConceptos.get(indice).setDescripcion(backUpNombre);
                    }

                    index = -1;
                    secRegistro = null;
                }

            }
        }
        context.update("form:datosActualizarConceptos");
    }

    public void actualizarClaveDebito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listConceptos.get(index).setClavecontabledebito(claveSapDebito);

                if (!crearConceptos.contains(listConceptos.get(index))) {
                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(listConceptos.get(index));
                    } else if (!modificarConceptos.contains(listConceptos.get(index))) {
                        modificarConceptos.add(listConceptos.get(index));
                    }
                }
            } else {
                filtrarConceptos.get(index).setClavecontabledebito(claveSapDebito);

                if (!crearConceptos.contains(filtrarConceptos.get(index))) {
                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(filtrarConceptos.get(index));
                    } else if (!modificarConceptos.contains(filtrarConceptos.get(index))) {
                        modificarConceptos.add(filtrarConceptos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            // context.update("form:datosActualizarConceptos");
            context.update("form:ACEPTAR");
        }
        filtradoClavesSap = null;
        claveSapDebito = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("clavesSapDebitoDialgo.hide()");
        context.reset("form:lovClavesSap:globalFilter");
        context.update("form:lovClavesSap");
        context.update("form:datosActualizarConceptos");
    }

    public void cancelarCambioClavesSapDebito() {
        listConceptos.get(index).setClavecontabledebito(claveSapDebito);
        filtradoClavesSap = null;
        claveSapDebito = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void actualizarClaveCredito() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listConceptos.get(index).setClavecontablecredito(claveSapCredito);

                if (!crearConceptos.contains(listConceptos.get(index))) {
                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(listConceptos.get(index));
                    } else if (!modificarConceptos.contains(listConceptos.get(index))) {
                        modificarConceptos.add(listConceptos.get(index));
                    }
                }
            } else {
                filtrarConceptos.get(index).setClavecontablecredito(claveSapCredito);

                if (!crearConceptos.contains(filtrarConceptos.get(index))) {
                    if (modificarConceptos.isEmpty()) {
                        modificarConceptos.add(filtrarConceptos.get(index));
                    } else if (!modificarConceptos.contains(filtrarConceptos.get(index))) {
                        modificarConceptos.add(filtrarConceptos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            // context.update("form:datosActualizarConceptos");
            context.update("form:ACEPTAR");
        }
        filtradoClavesSap = null;
        claveSapCredito = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("claveSapCreditoDialogo.hide()");
        context.reset("form:lovClavesSapCredito:globalFilter");
        context.update("form:lovClavesSapCredito");
        context.update("form:datosActualizarConceptos");
    }

    public void cancelarCambioClavesSapCredito() {
        listConceptos.get(index).setClavecontablecredito(claveSapCredito);
        filtradoClavesSap = null;
        claveSapCredito = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    public void revisarDialogoGuardar() {

        if (!modificarConceptos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando guardarConcepto");
            if (!modificarConceptos.isEmpty()) {
                for (int i = 0; i < modificarConceptos.size(); i++) {
                    if (modificarConceptos.get(i).getClavecontabledebito().getSecuencia() == null) {
                        modificarConceptos.get(i).setClavecontabledebito(null);
                    }
                }
                administrarConceptos.modificarConceptos(modificarConceptos);
                modificarConceptos.clear();
            }
            getListConceptos();
            System.out.println("Se guardaron los datos con exito");
            listConceptos = null;
            context.update("form:datosActualizarConceptos");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");

            k = 0;
        }
        index = -1;
        guardado = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        buscarConceptos = false;
        mostrartodos = true;
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");

    }

    public void guardarConceptoCambioEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!modificarConceptos.isEmpty()) {
                for (int i = 0; i < modificarConceptos.size(); i++) {
                    if (modificarConceptos.get(i).getClavecontabledebito().getSecuencia() == null) {
                        modificarConceptos.get(i).setClavecontabledebito(null);
                    }
                }
                administrarConceptos.modificarConceptos(modificarConceptos);
                modificarConceptos.clear();
            }
            listConceptos = null;
            filtradoEmpresas = null;
            empresaSeleccionadaMostrar = new Empresas();
            aceptar = true;
            System.out.println("Realizando guardarConceptoCambioEmpresa empresaSeleccionada : " + empresaSeleccionada.getNombre());
            getListConceptos();
            context.update("formularioDialogos:lovEmpresas");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            context.execute("EmpresasDialogo.hide()");
            System.out.println("Se guardaron los datos con exito");
            context.update("form:datosActualizarConceptos");
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");

            k = 0;
        }
        index = -1;
        guardado = true;
        buscarConceptos = false;
        mostrartodos = true;
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void guardarConceptoCambioConceptoLOV() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (!modificarConceptos.isEmpty()) {
                for (int i = 0; i < modificarConceptos.size(); i++) {
                    if (modificarConceptos.get(i).getClavecontabledebito().getSecuencia() == null) {
                        modificarConceptos.get(i).setClavecontabledebito(null);
                    }
                }
                administrarConceptos.modificarConceptos(modificarConceptos);
                modificarConceptos.clear();
            }
            listConceptos = null;
            filtradoEmpresas = null;
            aceptar = true;
            listLOVConceptos = null;
            getListLOVConceptos();
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            index = -1;
            context.update("formularioDialogos:lovConceptosPorEmpresa");
            context.execute("buscarConceptosPorEmpresaDialogo.show()");
            System.out.println("Se guardaron los datos con exito");
            context.update("form:datosActualizarConceptos");

            k = 0;
        }
        index = -1;
        guardado = true;
        buscarConceptos = false;
        mostrartodos = true;
        context.update("form:BUSCARCONCEPTOS");
        context.update("form:MOSTRARTODOS");
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarConceptos = listConceptos.get(index);
            }
            if (tipoLista == 1) {
                editarConceptos = filtrarConceptos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 1) {
                context.update("formularioDialogos:editDescripcion");
                context.execute("editDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editClaveContableDebito");
                context.execute("editClaveContableDebito.show()");
                cualCelda = -1;

            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editClaveContableCredito");
                context.execute("editClaveContableCredito.show()");
                cualCelda = -1;

            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editIndiceDeudor");
                context.execute("editIndiceDeudor.show()");
                cualCelda = -1;

            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editIndiceAcredor");
                context.execute("editIndiceAcredor.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editFechaCreacion");
                context.execute("editFechaCreacion.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void limpiarNuevoConceptos() {
        System.out.println("limpiarNuevoHvEntrevistas");
        nuevoConceptos = new Conceptos();
        secRegistro = null;
        index = -1;

    }

    public void lovEmpresas() {
        index = -1;
        secRegistro = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void cambiarEmpresa() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("Cambiar empresa empresaSeleccionadaMostrar  GUARDADO = " + empresaSeleccionadaMostrar.getNombre());
        empresaSeleccionada = empresaSeleccionadaMostrar;
        System.err.println("1 Cambiar empresa empresaSeleccionada  GUARDADO = " + empresaSeleccionada.getNombre());
        if (!modificarConceptos.isEmpty()) {
            context.execute("confirmarGuardarCambioEmpresa.show()");
        } else {
            listConceptos = null;
            filtradoEmpresas = null;
            empresaSeleccionadaMostrar = new Empresas();
            aceptar = true;
            System.err.println("2 Cambiar empresa empresaSeleccionada  GUARDADO = " + empresaSeleccionada.getNombre());
            getListConceptos();
            context.update("formularioDialogos:lovEmpresas");
            context.reset("formularioDialogos:lovEmpresas:globalFilter");
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            context.execute("EmpresasDialogo.hide()");
        }
        context.update("form:datosActualizarConceptos");
        System.out.println("Terminó Cambiar Empresa");
    }

    public void cancelarCambioEmpresa() {
        filtradoEmpresas = null;
        listaEmpresas = null;
        index = -1;
    }

    public void llamadoDialogoBuscarConceptos() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (!modificarConceptos.isEmpty()) {
                context.execute("confirmarGuardarCambiosConceptoLOV.show()");
            } else {
                listLOVConceptos = null;
                getListLOVConceptos();
                index = -1;
                context.update("formularioDialogos:lovConceptosPorEmpresa");
                context.execute("buscarConceptosPorEmpresaDialogo.show()");
            }

        } catch (Exception e) {
            System.err.println("ERROR CONTROLACTUALIZARCONCEPTOS llamadoDialogoBuscarConceptos ERROR " + e);
        }

    }

    private boolean buscarConceptos;
    private boolean mostrartodos;

    public void seleccionConceptosPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();

            if (guardado == true) {
                listConceptos.clear();
                System.err.println("ControlActualizarConceptos seleccionConceptosPorEmpresa conceptoLOVSeleccionado : " + conceptoLOVSeleccionado.getDescripcion());
                listConceptos.add(conceptoLOVSeleccionado);
                System.err.println("listConceptos tamaño " + listConceptos.size());
                System.err.println("listConceptos nombre " + listConceptos.get(0).getDescripcion());
                conceptoLOVSeleccionado = null;
                filtrarLOVConceptos = null;
                aceptar = true;
                context.update("form:datosActualizarConceptos");
                context.execute("buscarConceptosPorEmpresaDialogo.hide()");
                context.reset("formularioDialogos:lovConceptosPorEmpresa:globalFilter");
                buscarConceptos = true;
                mostrartodos = false;
                listLOVConceptos = null;
                if (listConceptos == null || listConceptos.isEmpty()) {
                    infoRegistro = "Cantidad de registros: 0 ";
                } else {
                    infoRegistro = "Cantidad de registros: " + listConceptos.size();
                }
                context.update("form:informacionRegistro");
                context.update("form:BUSCARCONCEPTOS");
                context.update("form:MOSTRARTODOS");
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLACTUALIZARCONCEPTOS.seleccionaVigencia ERROR : " + e.getMessage());
        }
    }

    public void cancelarSeleccionConceptosPorEmpresa() {
        try {
            RequestContext context = RequestContext.getCurrentInstance();
            conceptoLOVSeleccionado = null;
            filtrarLOVConceptos = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
            context.update("form:aceptarNCC");

        } catch (Exception e) {
            System.out.println("ERROR CONTROLACTUALIZARCONCEPTOS.cancelarSeleccionConceptosPorEmpresa ERROR : " + e.getMessage());
        }
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosActualizarConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CONCEPTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosActualizarConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CONCEPTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listConceptos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "CONCEPTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
                System.out.println("resultado: " + resultado);
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
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //*/*/*/*/*/*/*/*/*/*-/-*//-*/-*/*/*-*/-*/-*/*/*/*/*/---/*/*/*/*/-*/-*/-*/-*/-*/
    public List<Conceptos> getListConceptos() {
        if (listConceptos == null) {
            listConceptos = administrarConceptos.consultarConceptosEmpresa(empresaSeleccionada.getSecuencia());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptos == null || listConceptos.isEmpty()) {
            infoRegistro = "Cantidad de registros: 0 ";
        } else {
            for (int z = 0; z < listConceptos.size(); z++) {
                if (listConceptos.get(z).getClavecontabledebito() == null) {
                    listConceptos.get(z).setClavecontabledebito(new ClavesSap());
                }
                if (listConceptos.get(z).getClavecontablecredito() == null) {
                    listConceptos.get(z).setClavecontablecredito(new ClavesSap());
                }
            }
            infoRegistro = "Cantidad de registros: " + listConceptos.size();
        }
        context.update("form:informacionRegistro");
        return listConceptos;
    }

    public void setListConceptos(List<Conceptos> listConceptos) {
        this.listConceptos = listConceptos;
    }
    private String infoRegistroLOVconceptos;

    public List<Conceptos> getListLOVConceptos() {
        if (listLOVConceptos == null) {
            listLOVConceptos = administrarConceptos.consultarConceptosEmpresa(empresaSeleccionada.getSecuencia());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        if (listLOVConceptos == null || listLOVConceptos.isEmpty()) {
            infoRegistroLOVconceptos = "Cantidad de registros: 0 ";
        } else {
            for (int z = 0; z < listLOVConceptos.size(); z++) {
                if (listLOVConceptos.get(z).getClavecontabledebito() == null) {
                    listLOVConceptos.get(z).setClavecontabledebito(new ClavesSap());
                }
                if (listLOVConceptos.get(z).getClavecontablecredito() == null) {
                    listLOVConceptos.get(z).setClavecontablecredito(new ClavesSap());
                }
            }
            infoRegistroLOVconceptos = "Cantidad de registros: " + listLOVConceptos.size();
        }
        context.update("form:infoRegistroLOVconceptos");
        return listLOVConceptos;
    }

    public void setListLOVConceptos(List<Conceptos> listLOVConceptos) {
        this.listLOVConceptos = listLOVConceptos;
    }

    public List<Conceptos> getFiltrarLOVConceptos() {
        return filtrarLOVConceptos;
    }

    public void setFiltrarLOVConceptos(List<Conceptos> filtrarLOVConceptos) {
        this.filtrarLOVConceptos = filtrarLOVConceptos;
    }

    public Conceptos getConceptoLOVSeleccionado() {
        return conceptoLOVSeleccionado;
    }

    public void setConceptoLOVSeleccionado(Conceptos conceptoLOVSeleccionado) {
        this.conceptoLOVSeleccionado = conceptoLOVSeleccionado;
    }

    public List<Conceptos> getFiltrarConceptos() {
        return filtrarConceptos;
    }

    public void setFiltrarConceptos(List<Conceptos> filtrarConceptos) {
        this.filtrarConceptos = filtrarConceptos;
    }

    public List<Conceptos> getCrearConceptos() {
        return crearConceptos;
    }

    public void setCrearConceptos(List<Conceptos> crearConceptos) {
        this.crearConceptos = crearConceptos;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }
    private String infoRegistroClaveSapCredito;

    public List<ClavesSap> getListaClavesSap() {
        if (listaClavesSap == null) {
            listaClavesSap = administrarConceptos.consultarLOVClavesSap();
        }
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaClavesSap == null || listaClavesSap.isEmpty()) {
            infoRegistroClaveSapDebito = "Cantidad de registros: 0 ";
            infoRegistroClaveSapCredito = "Cantidad de registros: 0 ";
        } else {
            infoRegistroClaveSapDebito = "Cantidad de registros: " + listaClavesSap.size();
            infoRegistroClaveSapCredito = "Cantidad de registros: " + listaClavesSap.size();
        }
        context.update("form:infoRegistroClaveSapDebito");
        context.update("form:infoRegistroClaveSapCredito");
        return listaClavesSap;
    }

    public void setListaClavesSap(List<ClavesSap> listaClavesSap) {
        this.listaClavesSap = listaClavesSap;
    }

    public List<ClavesSap> getFiltradoClavesSap() {
        return filtradoClavesSap;
    }

    public void setFiltradoClavesSap(List<ClavesSap> filtradoClavesSap) {
        this.filtradoClavesSap = filtradoClavesSap;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public Conceptos getEditarConceptos() {
        return editarConceptos;
    }

    public void setEditarConceptos(Conceptos editarConceptos) {
        this.editarConceptos = editarConceptos;
    }

    public ClavesSap getClaveSapDebito() {
        return claveSapDebito;
    }

    public void setClaveSapDebito(ClavesSap claveSapDebito) {
        this.claveSapDebito = claveSapDebito;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public Conceptos getConceptoSeleccionada() {
        return conceptoSeleccionada;
    }

    public void setConceptoSeleccionada(Conceptos conceptoSeleccionada) {
        this.conceptoSeleccionada = conceptoSeleccionada;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public String getInfoRegistroClaveSapDebito() {
        return infoRegistroClaveSapDebito;
    }

    public void setInfoRegistroClaveSapDebito(String infoRegistroClaveSapDebito) {
        this.infoRegistroClaveSapDebito = infoRegistroClaveSapDebito;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public ClavesSap getClaveSapCredito() {
        return claveSapCredito;
    }

    public void setClaveSapCredito(ClavesSap claveSapCredito) {
        this.claveSapCredito = claveSapCredito;
    }

    public String getInfoRegistroClaveSapCredito() {
        return infoRegistroClaveSapCredito;
    }

    public void setInfoRegistroClaveSapCredito(String infoRegistroClaveSapCredito) {
        this.infoRegistroClaveSapCredito = infoRegistroClaveSapCredito;
    }

    public Empresas getEmpresaSeleccionada() {
        getListaEmpresas();
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public List<Empresas> getListaEmpresas() {
        //if (secuenciaEmpresa == null) {
          /*  if (listaEmpresas == null) {
         listaEmpresas = administrarConceptos.consultarEmpresas();
         empresaSeleccionada = listaEmpresas.get(0);
         }*/
        //} else {
        if (listaEmpresas == null) {
            listaEmpresas = administrarConceptos.consultarEmpresaPorSecuencia(secuenciaEmpresa);
            System.out.println("ControlActualizarConceptos getListaEmpresas Tamaño listaEmpresas : " + listaEmpresas.size());
            empresaSeleccionada = listaEmpresas.get(0);
        }
        //}
        return listaEmpresas;
    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {
        this.listaEmpresas = listaEmpresas;
    }

    public List<Empresas> getFiltradoEmpresas() {
        return filtradoEmpresas;
    }

    public void setFiltradoEmpresas(List<Empresas> filtradoEmpresas) {
        this.filtradoEmpresas = filtradoEmpresas;
    }

    public String getInfoRegistroEmpresas() {
        return infoRegistroEmpresas;
    }

    public void setInfoRegistroEmpresas(String infoRegistroEmpresas) {
        this.infoRegistroEmpresas = infoRegistroEmpresas;
    }

    public Empresas getEmpresaSeleccionadaMostrar() {
        return empresaSeleccionadaMostrar;
    }

    public void setEmpresaSeleccionadaMostrar(Empresas empresaSeleccionadaMostrar) {
        this.empresaSeleccionadaMostrar = empresaSeleccionadaMostrar;
    }

    public boolean isBuscarConceptos() {
        return buscarConceptos;
    }

    public void setBuscarConceptos(boolean buscarConceptos) {
        this.buscarConceptos = buscarConceptos;
    }

    public boolean isMostrartodos() {
        return mostrartodos;
    }

    public void setMostrartodos(boolean mostrartodos) {
        this.mostrartodos = mostrartodos;
    }

}

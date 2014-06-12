/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.GruposTiposCC;
import Entidades.TiposCentrosCostos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposCentrosCostosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
public class ControlTiposCentrosCostos implements Serializable {

    @EJB
    AdministrarTiposCentrosCostosInterface administrarTiposCentrosCostos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    private List<TiposCentrosCostos> listTiposCentrosCostos;
    private List<TiposCentrosCostos> filtrarTiposCentrosCostos;
    private List<TiposCentrosCostos> crearTiposCentrosCostos;
    private List<TiposCentrosCostos> modificarTiposCentrosCostos;
    private List<TiposCentrosCostos> borrarTiposCentrosCostos;
    private TiposCentrosCostos nuevoTipoCentroCosto;
    private TiposCentrosCostos duplicarTipoCentroCosto;
    private TiposCentrosCostos editarTipoCentroCosto;
    //lov gruposTiposCC
    private GruposTiposCC grupoTipoCCSeleccionada;
    private List<GruposTiposCC> filtradoGruposTiposCC;
    private List<GruposTiposCC> listaGruposTiposCC;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    String grupoTipoCCAutoCompletar;
    String nuevoGrupoTipoCCAutoCompletar;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, nombre, grupoTipoCC;
    //borrado
    private BigInteger borradoCC;
    private BigInteger borradoVC;
    private BigInteger borradoRP;
    private int registrosBorrados;
    private String mensajeValidacion;

    /**
     * Creates a new instance of ControlTiposCentrosCostos
     */
    public ControlTiposCentrosCostos() {
        listTiposCentrosCostos = null;
        crearTiposCentrosCostos = new ArrayList<TiposCentrosCostos>();
        modificarTiposCentrosCostos = new ArrayList<TiposCentrosCostos>();
        borrarTiposCentrosCostos = new ArrayList<TiposCentrosCostos>();
        listaGruposTiposCC = null;
        permitirIndex = true;
        editarTipoCentroCosto = new TiposCentrosCostos();
        nuevoTipoCentroCosto = new TiposCentrosCostos();
        nuevoTipoCentroCosto.setGrupotipocc(new GruposTiposCC());
        duplicarTipoCentroCosto = new TiposCentrosCostos();
        duplicarTipoCentroCosto.setGrupotipocc(new GruposTiposCC());
        guardado = true;

    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposCentrosCostos.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlTiposCentrosCostos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlTiposCentrosCostos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposCentrosCostos.get(index).getSecuencia();

            if (cualCelda == 2) {
                if (tipoLista == 0) {
                    grupoTipoCCAutoCompletar = listTiposCentrosCostos.get(index).getGrupotipocc().getDescripcion();
                } else {
                    grupoTipoCCAutoCompletar = filtrarTiposCentrosCostos.get(index).getGrupotipocc().getDescripcion();
                }
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    /**
     *
     * @param indice
     * @param LND
     * @param dig muestra el dialogo respectivo
     */
    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlTiposCentrosCostos.asignarIndex \n");
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            System.out.println("dig: " + dig);

            if (dig == 2) {
                context.update("form:gruposTiposCentrosCostosDialogo");
                context.execute("gruposTiposCentrosCostosDialogo.show()");
                dig = -1;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlTiposCentrosCostos.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:gruposTiposCentrosCostosDialogo");
                context.execute("gruposTiposCentrosCostosDialogo.show()");
                tipoActualizacion = 0;
            }

        }
    }
// tipo Entidad

    public void actualizarGrupoTipoCC() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listTiposCentrosCostos.get(index).setGrupotipocc(grupoTipoCCSeleccionada);

                if (!crearTiposCentrosCostos.contains(listTiposCentrosCostos.get(index))) {
                    if (modificarTiposCentrosCostos.isEmpty()) {
                        modificarTiposCentrosCostos.add(listTiposCentrosCostos.get(index));
                    } else if (!modificarTiposCentrosCostos.contains(listTiposCentrosCostos.get(index))) {
                        modificarTiposCentrosCostos.add(listTiposCentrosCostos.get(index));
                    }
                }
            } else {
                filtrarTiposCentrosCostos.get(index).setGrupotipocc(grupoTipoCCSeleccionada);

                if (!crearTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(index))) {
                    if (modificarTiposCentrosCostos.isEmpty()) {
                        modificarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(index));
                    } else if (!modificarTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(index))) {
                        modificarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosTipoEntidad");
            context.update("form:ACEPTAR");
        } else if (tipoActualizacion == 1) {
            nuevoTipoCentroCosto.setGrupotipocc(grupoTipoCCSeleccionada);
            context.update("formularioDialogos:nuevoTipoCentroCosto");
        } else if (tipoActualizacion == 2) {
            duplicarTipoCentroCosto.setGrupotipocc(grupoTipoCCSeleccionada);
            context.update("formularioDialogos:duplicarTiposCentrosCostos");
        }
        filtradoGruposTiposCC = null;
        grupoTipoCCSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("gruposTiposCentrosCostosDialogo.hide()");
        context.reset("form:lovGruposTiposCC:globalFilter");
        context.update("form:lovGruposTiposCC");
    }

    public void cancelarCambioGrupoTipoCC() {
        filtradoGruposTiposCC = null;
        grupoTipoCCSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }
//------------------------------------------------------------------------------

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            grupoTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:grupoTipoCC");
            grupoTipoCC.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoCentroCosto");
            bandera = 0;
            filtrarTiposCentrosCostos = null;
            tipoLista = 0;
        }

        borrarTiposCentrosCostos.clear();
        crearTiposCentrosCostos.clear();
        modificarTiposCentrosCostos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposCentrosCostos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoCentroCosto");
        context.update("form:ACEPTAR");
    }
    //-----------------------------------------------------------------------

    public void modificarTipoCentroCosto(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPO CENTRO COSTO");
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        int contador = 0;
        boolean banderita = false;
        Short a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR TIPO CENTRO COSTO, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposCentrosCostos.contains(listTiposCentrosCostos.get(indice))) {
                    if (listTiposCentrosCostos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposCentrosCostos.size(); j++) {
                            if (j != indice) {
                                if (listTiposCentrosCostos.get(indice).getCodigo() == listTiposCentrosCostos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (banderita == true) {
                        if (modificarTiposCentrosCostos.isEmpty()) {
                            modificarTiposCentrosCostos.add(listTiposCentrosCostos.get(indice));
                        } else if (!modificarTiposCentrosCostos.contains(listTiposCentrosCostos.get(indice))) {
                            modificarTiposCentrosCostos.add(listTiposCentrosCostos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(indice))) {
                    if (filtrarTiposCentrosCostos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposCentrosCostos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCentrosCostos.get(indice).getCodigo() == listTiposCentrosCostos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarTiposCentrosCostos.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposCentrosCostos.get(indice).getCodigo() == filtrarTiposCentrosCostos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            banderita = true;
                        }

                    }

                    if (banderita == true) {
                        if (modificarTiposCentrosCostos.isEmpty()) {
                            modificarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(indice));
                        } else if (!modificarTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(indice))) {
                            modificarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }


                /*if (!crearTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {

                 if (modificarTiposEntidades.isEmpty()) {
                 modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                 } else if (!modificarTiposEntidades.contains(filtrarTiposEntidades.get(indice))) {
                 modificarTiposEntidades.add(filtrarTiposEntidades.get(indice));
                 }
                 if (guardado == true) {
                 guardado = false;
                 }
                 }
                 index = -1;
                 secRegistro = null;*/
            }
            context.update("form:datosTipoCentroCosto");
        } else if (confirmarCambio.equalsIgnoreCase("GRUPOSTIPOSCC")) {
            System.err.println("ENTRE A MODIFICAR TIPO CENTRO COSTO, CONFIRMAR CAMBIO ES GRUPOSTIPOSCC");
            if (tipoLista == 0) {

                listTiposCentrosCostos.get(indice).getGrupotipocc().setDescripcion(grupoTipoCCAutoCompletar);
            } else {

                filtrarTiposCentrosCostos.get(indice).getGrupotipocc().setDescripcion(grupoTipoCCAutoCompletar);
            }

            for (int i = 0; i < listaGruposTiposCC.size(); i++) {
                if (listaGruposTiposCC.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listTiposCentrosCostos.get(indice).setGrupotipocc(listaGruposTiposCC.get(indiceUnicoElemento));
                } else {
                    filtrarTiposCentrosCostos.get(indice).setGrupotipocc(listaGruposTiposCC.get(indiceUnicoElemento));
                }
                listaGruposTiposCC.clear();
                listaGruposTiposCC = null;
                getListaGruposTiposCC();
            } else {
                permitirIndex = false;
                context.update("form:gruposTiposCentrosCostosDialogo");
                context.execute("gruposTiposCentrosCostosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        context.update("form:datosTipoCentroCosto");
        context.update("form:ACEPTAR");

    }
    //------------------------------------------------------------------------- 

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:codigo");
            codigo.setFilterStyle("width: 200px");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:nombre");
            nombre.setFilterStyle("width: 270px");
            grupoTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:grupoTipoCC");
            grupoTipoCC.setFilterStyle("width: 270px");
            RequestContext.getCurrentInstance().update("form:datosTipoCentroCosto");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:nombre");
            nombre.setFilterStyle("display: none; visibility: hidden;");
            grupoTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:grupoTipoCC");
            grupoTipoCC.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoCentroCosto");
            bandera = 0;
            filtrarTiposCentrosCostos = null;
            tipoLista = 0;
        }
    }

//------------------------------------------------------------------------------
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("GRUPOSTIPOSCC")) {
            if (tipoNuevo == 1) {
                nuevoGrupoTipoCCAutoCompletar = nuevoTipoCentroCosto.getGrupotipocc().getDescripcion();
            } else if (tipoNuevo == 2) {
                nuevoGrupoTipoCCAutoCompletar = nuevoTipoCentroCosto.getGrupotipocc().getDescripcion();
            }

        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPOSTIPOSCC")) {
            if (tipoNuevo == 1) {
                nuevoTipoCentroCosto.getGrupotipocc().setDescripcion(nuevoGrupoTipoCCAutoCompletar);
            } else if (tipoNuevo == 2) {
                duplicarTipoCentroCosto.getGrupotipocc().setDescripcion(nuevoGrupoTipoCCAutoCompletar);
            }
            for (int i = 0; i < listaGruposTiposCC.size(); i++) {
                if (listaGruposTiposCC.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoTipoCentroCosto.setGrupotipocc(listaGruposTiposCC.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoGrupoTipoCC");
                } else if (tipoNuevo == 2) {
                    duplicarTipoCentroCosto.setGrupotipocc(listaGruposTiposCC.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarGrupoTipoCentroCosto");
                }
                listaGruposTiposCC.clear();
                listaGruposTiposCC = null;
                getListaGruposTiposCC();
            } else {
                context.update("form:gruposTiposCentrosCostosDialogo");
                context.execute("gruposTiposCentrosCostosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoGrupoTipoCC");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarGrupoTipoCentroCosto");
                }
            }
        }

    }

    public void asignarVariableGrupoTipoCC(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
        }
        if (tipoNuevo == 1) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:gruposTiposCentrosCostosDialogo");
        context.execute("gruposTiposCentrosCostosDialogo.show()");
    }

    public void agregarNuevoTipoCentroCosto() {
        System.out.println("Agregar nueva vigencia");
        int contador = 0;
        int duplicados = 0;

        Short a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoCentroCosto.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("tamaño lista de tipos de Centro Costo en agregar  : " + listTiposCentrosCostos.size());
            System.out.println("codigo en tipo CentroCosto: " + nuevoTipoCentroCosto.getCodigo());

            for (int x = 0; x < listTiposCentrosCostos.size(); x++) {
                if (listTiposCentrosCostos.get(x).getCodigo() == nuevoTipoCentroCosto.getCodigo()) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Hayan Codigos Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        if (nuevoTipoCentroCosto.getNombre() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener Un  Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoTipoCentroCosto.getGrupotipocc().getSecuencia() == null) {
            mensajeValidacion = mensajeValidacion + "   * Grupo Tipo CC \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;
            duplicados = 0;

        }//TENER PRESENTE ------------------------------------------------------
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
        if (contador == 3) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                grupoTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:grupoTipoCC");
                grupoTipoCC.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoCentroCosto");
                bandera = 0;
                filtrarTiposCentrosCostos = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoCentroCosto.setSecuencia(l);

            crearTiposCentrosCostos.add(nuevoTipoCentroCosto);

            listTiposCentrosCostos.add(nuevoTipoCentroCosto);
            nuevoTipoCentroCosto = new TiposCentrosCostos();
            nuevoTipoCentroCosto.setGrupotipocc(new GruposTiposCC());

            context.update("form:datosTipoCentroCosto");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.execute("nuevoRegistroTipoCentroCosto.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTipoCentroCosto() {
        System.out.println("LimpiarNuevoTipoCentroCostos");
        nuevoTipoCentroCosto = new TiposCentrosCostos();
        nuevoTipoCentroCosto.setGrupotipocc(new GruposTiposCC());
        secRegistro = null;
        index = -1;

    }

    public void cargarGruposTiposCentrosCostosNuevoRegistro(int tipoNuevo) {
        if (tipoNuevo == 0) {
            tipoActualizacion = 1;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:gruposTiposCentrosCostosDialogo");
            context.execute("gruposTiposCentrosCostosDialogo.show()");
        } else if (tipoNuevo == 1) {
            tipoActualizacion = 2;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:gruposTiposCentrosCostosDialogo");
            context.execute("gruposTiposCentrosCostosDialogo.show()");
        }
    }

    public void guardarCambiosTiposCentroCosto() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Operaciones Vigencias Localizacion");
            if (!borrarTiposCentrosCostos.isEmpty()) {
                administrarTiposCentrosCostos.borrarTiposCentrosCostos(borrarTiposCentrosCostos);

                //mostrarBorrados
                registrosBorrados = borrarTiposCentrosCostos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposCentrosCostos.clear();
            }
            if (!crearTiposCentrosCostos.isEmpty()) {
                administrarTiposCentrosCostos.crearTiposCentrosCostos(crearTiposCentrosCostos);

                crearTiposCentrosCostos.clear();
            }
            if (!modificarTiposCentrosCostos.isEmpty()) {
                for (int i = 0; i < modificarTiposCentrosCostos.size(); i++) {
                    if (modificarTiposCentrosCostos.get(i).getGrupotipocc().getSecuencia() == null) {
                        modificarTiposCentrosCostos.get(i).setGrupotipocc(null);
                    }
                }
                administrarTiposCentrosCostos.modificarTipoCentrosCostos(modificarTiposCentrosCostos);
                modificarTiposCentrosCostos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposCentrosCostos = null;
            context.update("form:datosTipoCentroCosto");
            k = 0;
            guardado = true;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    //------------------------------------------------------------------------------
    public void duplicarTiposCentrosCostos() {
        System.out.println("DuplicarVigenciasFormasPagos");
        if (index >= 0) {
            duplicarTipoCentroCosto = new TiposCentrosCostos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoCentroCosto.setSecuencia(l);
                duplicarTipoCentroCosto.setCodigo(listTiposCentrosCostos.get(index).getCodigo());
                duplicarTipoCentroCosto.setGrupotipocc(listTiposCentrosCostos.get(index).getGrupotipocc());
                duplicarTipoCentroCosto.setNombre(listTiposCentrosCostos.get(index).getNombre());
            }
            if (tipoLista == 1) {
                duplicarTipoCentroCosto.setCodigo(filtrarTiposCentrosCostos.get(index).getCodigo());
                duplicarTipoCentroCosto.setGrupotipocc(filtrarTiposCentrosCostos.get(index).getGrupotipocc());
                duplicarTipoCentroCosto.setNombre(filtrarTiposCentrosCostos.get(index).getNombre());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTiposCentrosCostos");
            context.execute("duplicarRegistroTiposCentrosCostos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLTIPOSCENTROSCOSTOS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Short a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarTipoCentroCosto.getCodigo());
        System.err.println("ConfirmarDuplicar grupo " + duplicarTipoCentroCosto.getGrupotipocc().getDescripcion());
        System.err.println("ConfirmarDuplicar nombre " + duplicarTipoCentroCosto.getNombre());

        if (duplicarTipoCentroCosto.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposCentrosCostos.size(); x++) {
                if (listTiposCentrosCostos.get(x).getCodigo() == duplicarTipoCentroCosto.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Existan Codigo Repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
                duplicados = 0;
            }
        }
        if (duplicarTipoCentroCosto.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarTipoCentroCosto.getGrupotipocc().getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Grupo Tipo Entidad \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            contador++;
            System.out.println("Bandera : ");
        }

        if (contador == 3) {

            System.out.println("Datos Duplicando: " + duplicarTipoCentroCosto.getSecuencia() + "  " + duplicarTipoCentroCosto.getCodigo());
            if (crearTiposCentrosCostos.contains(duplicarTipoCentroCosto)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposCentrosCostos.add(duplicarTipoCentroCosto);
            crearTiposCentrosCostos.add(duplicarTipoCentroCosto);
            context.update("form:datosTipoCentroCosto");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                nombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:nombre");
                nombre.setFilterStyle("display: none; visibility: hidden;");
                grupoTipoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoCentroCosto:grupoTipoCC");
                grupoTipoCC.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoCentroCosto");
                bandera = 0;
                filtrarTiposCentrosCostos = null;
                tipoLista = 0;
            }
            duplicarTipoCentroCosto = new TiposCentrosCostos();
            duplicarTipoCentroCosto.setGrupotipocc(new GruposTiposCC());
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposCentrosCostos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarduplicarTiposCentrosCostos() {
        duplicarTipoCentroCosto = new TiposCentrosCostos();
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoCentroCosto = listTiposCentrosCostos.get(index);
            }
            if (tipoLista == 1) {
                editarTipoCentroCosto = filtrarTiposCentrosCostos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editNombre");
                context.execute("editNombre.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editGrupoTipoCC");
                context.execute("editGrupoTipoCC.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoCentroCostoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposCentrosCostosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoCentroCostoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposCentrosCostosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            if (tipoLista == 0) {
                borradoCC = administrarTiposCentrosCostos.contarCentrosCostosTipoCentroCosto(listTiposCentrosCostos.get(index).getSecuencia());
                borradoVC = administrarTiposCentrosCostos.contarVigenciasCuentasTipoCentroCosto(listTiposCentrosCostos.get(index).getSecuencia());
                borradoRP = administrarTiposCentrosCostos.contarRiesgosProfesionalesTipoCentroCosto(listTiposCentrosCostos.get(index).getSecuencia());
            } else {
                borradoCC = administrarTiposCentrosCostos.contarCentrosCostosTipoCentroCosto(filtrarTiposCentrosCostos.get(index).getSecuencia());
                borradoVC = administrarTiposCentrosCostos.contarVigenciasCuentasTipoCentroCosto(filtrarTiposCentrosCostos.get(index).getSecuencia());
                borradoRP = administrarTiposCentrosCostos.contarRiesgosProfesionalesTipoCentroCosto(filtrarTiposCentrosCostos.get(index).getSecuencia());
            }
            if (borradoCC.equals(new BigInteger("0")) && borradoVC.equals(new BigInteger("0")) && borradoRP.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarTiposCentrosCostos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                borradoCC = new BigInteger("-1");
                borradoVC = new BigInteger("-1");
                borradoRP = new BigInteger("-1");
            }

        } catch (Exception e) {
            System.err.println("ERROR ControlTiposEntidades verificarBorrado ERROR " + e);
        }
    }

    public void borrarTiposCentrosCostos() {

        if (index >= 0) {

            if (tipoLista == 0) {
                System.out.println("Entro a borrarTiposCentrosCostos");
                if (!modificarTiposCentrosCostos.isEmpty() && modificarTiposCentrosCostos.contains(listTiposCentrosCostos.get(index))) {
                    int modIndex = modificarTiposCentrosCostos.indexOf(listTiposCentrosCostos.get(index));
                    modificarTiposCentrosCostos.remove(modIndex);
                    borrarTiposCentrosCostos.add(listTiposCentrosCostos.get(index));
                } else if (!crearTiposCentrosCostos.isEmpty() && crearTiposCentrosCostos.contains(listTiposCentrosCostos.get(index))) {
                    int crearIndex = crearTiposCentrosCostos.indexOf(listTiposCentrosCostos.get(index));
                    crearTiposCentrosCostos.remove(crearIndex);
                } else {
                    borrarTiposCentrosCostos.add(listTiposCentrosCostos.get(index));
                }
                listTiposCentrosCostos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("BorrarTiposCentrosCostos ");
                if (!modificarTiposCentrosCostos.isEmpty() && modificarTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(index))) {
                    int modIndex = modificarTiposCentrosCostos.indexOf(filtrarTiposCentrosCostos.get(index));
                    modificarTiposCentrosCostos.remove(modIndex);
                    borrarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(index));
                } else if (!crearTiposCentrosCostos.isEmpty() && crearTiposCentrosCostos.contains(filtrarTiposCentrosCostos.get(index))) {
                    int crearIndex = crearTiposCentrosCostos.indexOf(filtrarTiposCentrosCostos.get(index));
                    crearTiposCentrosCostos.remove(crearIndex);
                } else {
                    borrarTiposCentrosCostos.add(filtrarTiposCentrosCostos.get(index));
                }
                int VCIndex = listTiposCentrosCostos.indexOf(filtrarTiposCentrosCostos.get(index));
                listTiposCentrosCostos.remove(VCIndex);
                filtrarTiposCentrosCostos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoCentroCosto");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
        }

    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposCentrosCostos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCENTROSCOSTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCENTROSCOSTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

//------------------------------------------------------------------------------
    public List<TiposCentrosCostos> getListTiposCentrosCostos() {
        if (listTiposCentrosCostos == null) {
            listTiposCentrosCostos = administrarTiposCentrosCostos.consultarTiposCentrosCostos();
        }
        return listTiposCentrosCostos;
    }

    public void setListTiposCentrosCostos(List<TiposCentrosCostos> listTiposCentrosCostos) {
        this.listTiposCentrosCostos = listTiposCentrosCostos;
    }

    public List<TiposCentrosCostos> getFiltrarTiposCentrosCostos() {
        return filtrarTiposCentrosCostos;
    }

    public void setFiltrarTiposCentrosCostos(List<TiposCentrosCostos> filtrarTiposCentrosCostos) {
        this.filtrarTiposCentrosCostos = filtrarTiposCentrosCostos;
    }

    public GruposTiposCC getGrupoTipoCCSeleccionada() {
        return grupoTipoCCSeleccionada;
    }

    public void setGrupoTipoCCSeleccionada(GruposTiposCC grupoTipoCCSeleccionada) {
        this.grupoTipoCCSeleccionada = grupoTipoCCSeleccionada;
    }

    public List<GruposTiposCC> getListaGruposTiposCC() {
        if (listaGruposTiposCC == null) {
            listaGruposTiposCC = administrarTiposCentrosCostos.consultarLOVGruposTiposCentrosCostos();
        }
        return listaGruposTiposCC;
    }

    public void setListaGruposTiposCC(List<GruposTiposCC> listaGruposTiposCCD) {
        this.listaGruposTiposCC = listaGruposTiposCCD;
    }

    public List<GruposTiposCC> getFiltradoGruposTiposCC() {
        return filtradoGruposTiposCC;
    }

    public void setFiltradoGruposTiposCC(List<GruposTiposCC> filtradoGruposTiposCC) {
        this.filtradoGruposTiposCC = filtradoGruposTiposCC;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public TiposCentrosCostos getNuevoTipoCentroCosto() {
        return nuevoTipoCentroCosto;
    }

    public void setNuevoTipoCentroCosto(TiposCentrosCostos nuevoTipoCentroCosto) {
        this.nuevoTipoCentroCosto = nuevoTipoCentroCosto;
    }

    public TiposCentrosCostos getDuplicarTipoCentroCosto() {
        return duplicarTipoCentroCosto;
    }

    public void setDuplicarTipoCentroCosto(TiposCentrosCostos duplicarTipoCentroCosto) {
        this.duplicarTipoCentroCosto = duplicarTipoCentroCosto;
    }

    public TiposCentrosCostos getEditarTipoCentroCosto() {
        return editarTipoCentroCosto;
    }

    public void setEditarTipoCentroCosto(TiposCentrosCostos editarTipoCentroCosto) {
        this.editarTipoCentroCosto = editarTipoCentroCosto;
    }

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

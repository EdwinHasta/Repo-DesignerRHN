package Controlador;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Entidades.TiposDias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarTiposDiasInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlTiposDias implements Serializable {

    @EJB
    AdministrarTiposDiasInterface administrarTiposDias;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposDias> listTiposDias;
    private List<TiposDias> filtrarTiposDias;
    private List<TiposDias> crearTiposDias;
    private List<TiposDias> modificarTiposDias;
    private List<TiposDias> borrarTiposDias;
    private TiposDias nuevoTipoDia;
    private TiposDias duplicarTipoDia;
    private TiposDias editarTipoDia;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, tipo;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger verificarDiasLaborales;
    private BigInteger verificarExtrasRecargos;

    public ControlTiposDias() {
        listTiposDias = null;
        crearTiposDias = new ArrayList<TiposDias>();
        modificarTiposDias = new ArrayList<TiposDias>();
        borrarTiposDias = new ArrayList<TiposDias>();
        permitirIndex = true;
        editarTipoDia = new TiposDias();
        nuevoTipoDia = new TiposDias();
        duplicarTipoDia = new TiposDias();
        guardado = true;
    }
    
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposDias.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct "+ this.getClass().getName() +": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }
    
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSDIAS EVENTOFILTRAR \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR CONTROLTIPOSDIAS EVENTOFILTRAR ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listTiposDias.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A CONTROLTIPOSDIAS ASIGNAR INDEX \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("TIPO ACTUALIZACION : " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR CONTROLTIPOSDIAS ASIGNAR INDEX ERROR =" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            tipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:tipo");
            tipo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposDias = null;
            tipoLista = 0;
        }

        borrarTiposDias.clear();
        crearTiposDias.clear();
        modificarTiposDias.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposDias = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("width: 285px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("width: 365px");
            tipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:tipo");
            tipo.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            tipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:tipo");
            tipo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposDias = null;
            tipoLista = 0;
        }
    }

    public void mostrarInfo(int indice, int celda) {
        if (permitirIndex == true) {
            RequestContext context = RequestContext.getCurrentInstance();

            index = indice;
            cualCelda = celda;
            secRegistro = listTiposDias.get(index).getSecuencia();
            System.out.println("Tipo = " + listTiposDias.get(index).getTipo());
            if (!crearTiposDias.contains(listTiposDias.get(indice))) {

                if (modificarTiposDias.isEmpty()) {
                    modificarTiposDias.add(listTiposDias.get(indice));
                } else if (!modificarTiposDias.contains(listTiposDias.get(indice))) {
                    modificarTiposDias.add(listTiposDias.get(indice));
                }

            }
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);

    }

    public void modificandoTipoDia(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR TIPOSDIAS");
        index = indice;
        boolean banderaTamano = false;
        int contador = 0;
        int contadorGuardar = 0;
        boolean banderita = false;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICARTIPODIA, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearTiposDias.contains(listTiposDias.get(indice))) {
                    if (listTiposDias.get(indice).getCodigo() == a || listTiposDias.get(indice).getCodigo().equals(null)) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposDias.size(); j++) {
                            if (j != indice) {
                                if (listTiposDias.get(indice).getCodigo().equals(listTiposDias.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }
                    if (listTiposDias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (listTiposDias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }

                    if (listTiposDias.get(indice).getTipo().length() <= 3 || listTiposDias.get(indice).getTipo().equals(null) || listTiposDias.get(indice).getTipo().isEmpty()) {

                        contadorGuardar++;
                    } else {
                        banderaTamano = true;
                        context.execute("validacionTamano.show()");
                    }

                    if (contadorGuardar == 3) {
                        if (modificarTiposDias.isEmpty()) {
                            modificarTiposDias.add(listTiposDias.get(indice));
                        } else if (!modificarTiposDias.contains(listTiposDias.get(indice))) {
                            modificarTiposDias.add(listTiposDias.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }

                    } else {
                        if (banderaTamano == false) {
                            context.update("form:validacionModificar");
                            context.execute("validacionModificar.show()");
                        }
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearTiposDias.contains(filtrarTiposDias.get(indice))) {
                    if (filtrarTiposDias.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listTiposDias.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposDias.get(indice).getCodigo() == listTiposDias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarTiposDias.size(); j++) {
                            if (j != indice) {
                                if (filtrarTiposDias.get(indice).getCodigo() == filtrarTiposDias.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                            banderita = false;
                        } else {
                            contadorGuardar++;
                        }

                    }

                    if (filtrarTiposDias.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else if (filtrarTiposDias.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        contadorGuardar++;
                    }

                    if (contadorGuardar == 2) {
                        if (modificarTiposDias.isEmpty()) {
                            modificarTiposDias.add(filtrarTiposDias.get(indice));
                        } else if (!modificarTiposDias.contains(filtrarTiposDias.get(indice))) {
                            modificarTiposDias.add(filtrarTiposDias.get(indice));
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

            }
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
        }

    }

    public void borrandoTiposDias() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrandoTiposDias");
                if (!modificarTiposDias.isEmpty() && modificarTiposDias.contains(listTiposDias.get(index))) {
                    int modIndex = modificarTiposDias.indexOf(listTiposDias.get(index));
                    modificarTiposDias.remove(modIndex);
                    borrarTiposDias.add(listTiposDias.get(index));
                } else if (!crearTiposDias.isEmpty() && crearTiposDias.contains(listTiposDias.get(index))) {
                    int crearIndex = crearTiposDias.indexOf(listTiposDias.get(index));
                    crearTiposDias.remove(crearIndex);
                } else {
                    borrarTiposDias.add(listTiposDias.get(index));
                }
                listTiposDias.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrandoTiposDias");
                if (!modificarTiposDias.isEmpty() && modificarTiposDias.contains(filtrarTiposDias.get(index))) {
                    int modIndex = modificarTiposDias.indexOf(filtrarTiposDias.get(index));
                    modificarTiposDias.remove(modIndex);
                    borrarTiposDias.add(filtrarTiposDias.get(index));
                } else if (!crearTiposDias.isEmpty() && crearTiposDias.contains(filtrarTiposDias.get(index))) {
                    int crearIndex = crearTiposDias.indexOf(filtrarTiposDias.get(index));
                    crearTiposDias.remove(crearIndex);
                } else {
                    borrarTiposDias.add(filtrarTiposDias.get(index));
                }
                int VCIndex = listTiposDias.indexOf(filtrarTiposDias.get(index));
                listTiposDias.remove(VCIndex);
                filtrarTiposDias.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void verificarBorrado() {
        try {
            if (tipoLista == 0) {
                verificarDiasLaborales = administrarTiposDias.verificarDiasLaborales(listTiposDias.get(index).getSecuencia());
                verificarExtrasRecargos = administrarTiposDias.verificarExtrasRecargos(listTiposDias.get(index).getSecuencia());
            } else {
                verificarDiasLaborales = administrarTiposDias.verificarDiasLaborales(filtrarTiposDias.get(index).getSecuencia());
                verificarExtrasRecargos = administrarTiposDias.verificarExtrasRecargos(filtrarTiposDias.get(index).getSecuencia());
            }

            if (!verificarDiasLaborales.equals(new BigInteger("0")) || !verificarExtrasRecargos.equals(new BigInteger("0"))) {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                verificarDiasLaborales = new BigInteger("-1");
                verificarExtrasRecargos = new BigInteger("-1");

            } else {
                System.out.println("Borrado==0");
                borrandoTiposDias();
            }
        } catch (Exception e) {
            System.err.println("ERROR CONTROLTIPOSDIAS verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarTiposDias.isEmpty() || !crearTiposDias.isEmpty() || !modificarTiposDias.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardandoTiposDias() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando TiposDias");
            if (!borrarTiposDias.isEmpty()) {
                administrarTiposDias.borrarTiposDias(borrarTiposDias);

                //mostrarBorrados
                registrosBorrados = borrarTiposDias.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarTiposDias.clear();
            }
            if (!crearTiposDias.isEmpty()) {
                administrarTiposDias.crearTiposDias(crearTiposDias);
                crearTiposDias.clear();
            }
            if (!modificarTiposDias.isEmpty()) {
                administrarTiposDias.modificarTiposDias(modificarTiposDias);
                modificarTiposDias.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listTiposDias = null;
            guardado = true;
            context.update("form:datosTipoReemplazo");
            k = 0;
        }
        index = -1;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoDia = listTiposDias.get(index);
            }
            if (tipoLista == 1) {
                editarTipoDia = filtrarTiposDias.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editCodigo");
                context.execute("editCodigo.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editDescripcion");
                context.execute("editDescripcion.show()");
                cualCelda = -1;

            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoTiposDias() {
        System.out.println("agregarNuevoTiposDias");
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoDia.getCodigo() == a) {
            mensajeValidacion = " *Debe tener un codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoTipoDia.getCodigo());

            for (int x = 0; x < listTiposDias.size(); x++) {
                if (listTiposDias.get(x).getCodigo().equals(nuevoTipoDia.getCodigo())) {
                    duplicados++;
                }
            }
            System.out.println("Antes del if Duplicados eses igual  : " + duplicados);

            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
                System.out.println("Mensaje validacion : " + mensajeValidacion);
            } else {
                System.out.println("bandera");
                contador++;
            }
        }
        System.out.println("NUEVA DESCRIPCION : " + nuevoTipoDia.getDescripcion());
        if (nuevoTipoDia.getDescripcion().equals(" ") || nuevoTipoDia.getDescripcion().equals(null) || nuevoTipoDia.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        System.out.println("NUEVO TIPO : " + nuevoTipoDia.getTipo());
        if (nuevoTipoDia.getTipo() == null) {
            nuevoTipoDia.setTipo("ORD");
        }
        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposDias = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoDia.setSecuencia(l);

            crearTiposDias.add(nuevoTipoDia);

            listTiposDias.add(nuevoTipoDia);
            nuevoTipoDia = new TiposDias();
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroTiposReemplazos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposDias() {
        System.out.println("limpiarNuevoTiposDias");
        nuevoTipoDia = new TiposDias();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposDias() {
        System.out.println("duplicandoTiposDias");
        if (index >= 0) {
            duplicarTipoDia = new TiposDias();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarTipoDia.setSecuencia(l);
                duplicarTipoDia.setCodigo(listTiposDias.get(index).getCodigo());
                duplicarTipoDia.setDescripcion(listTiposDias.get(index).getDescripcion());
                duplicarTipoDia.setTipo(listTiposDias.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarTipoDia.setSecuencia(l);
                duplicarTipoDia.setCodigo(filtrarTiposDias.get(index).getCodigo());
                duplicarTipoDia.setDescripcion(filtrarTiposDias.get(index).getDescripcion());
                duplicarTipoDia.setTipo(filtrarTiposDias.get(index).getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICARTIPOSDIAS");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarTipoDia.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listTiposDias.size(); x++) {
                if (listTiposDias.get(x).getCodigo() == duplicarTipoDia.getCodigo()) {
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
        if (duplicarTipoDia.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTipoDia.getDescripcion().isEmpty()) {
            mensajeValidacion = mensajeValidacion + "   * Un descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTipoDia.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + "   * Un descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarTipoDia.getDescripcion().equals("")) {
            mensajeValidacion = mensajeValidacion + "   * Un descripción \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarTipoDia.getSecuencia() + "  " + duplicarTipoDia.getCodigo());
            if (crearTiposDias.contains(duplicarTipoDia)) {
                System.out.println("Ya lo contengo.");
            }
            listTiposDias.add(duplicarTipoDia);
            crearTiposDias.add(duplicarTipoDia);
            context.update("form:datosTipoReemplazo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposDias = null;
                tipoLista = 0;
            }
            duplicarTipoDia = new TiposDias();
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposDias() {
        duplicarTipoDia = new TiposDias();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSDIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSDIAS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listTiposDias.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSDIAS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSDIAS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposDias> getListTiposDias() {
        if (listTiposDias == null) {
            listTiposDias = administrarTiposDias.mostrarTiposDias();
        }
        return listTiposDias;
    }

    public void setListTiposDias(List<TiposDias> listTiposDias) {
        this.listTiposDias = listTiposDias;
    }

    public List<TiposDias> getFiltrarTiposDias() {
        return filtrarTiposDias;
    }

    public void setFiltrarTiposDias(List<TiposDias> filtrarTiposDias) {
        this.filtrarTiposDias = filtrarTiposDias;
    }

    public TiposDias getNuevoTipoDia() {
        return nuevoTipoDia;
    }

    public void setNuevoTipoDia(TiposDias nuevoTipoDia) {
        this.nuevoTipoDia = nuevoTipoDia;
    }

    public TiposDias getDuplicarTipoDia() {
        return duplicarTipoDia;
    }

    public void setDuplicarTipoDia(TiposDias duplicarTipoDia) {
        this.duplicarTipoDia = duplicarTipoDia;
    }

    public TiposDias getEditarTipoDia() {
        return editarTipoDia;
    }

    public void setEditarTipoDia(TiposDias editarTipoDia) {
        this.editarTipoDia = editarTipoDia;
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

    public int getRegistrosBorrados() {
        return registrosBorrados;
    }

    public void setRegistrosBorrados(int registrosBorrados) {
        this.registrosBorrados = registrosBorrados;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

}

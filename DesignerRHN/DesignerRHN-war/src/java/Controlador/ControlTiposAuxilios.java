package Controlador;

import Entidades.TiposAuxilios;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposAuxiliosInterface;
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
public class ControlTiposAuxilios implements Serializable {

    @EJB
    AdministrarTiposAuxiliosInterface administrarTiposAuxilios;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<TiposAuxilios> listTiposAuxilios;
    private List<TiposAuxilios> filtrarTiposAuxilios;
    private List<TiposAuxilios> crearTiposAuxilios;
    private List<TiposAuxilios> modificarTiposAuxilios;
    private List<TiposAuxilios> borrarTiposAuxilios;
    private TiposAuxilios nuevoTipoAuxilios;
    private TiposAuxilios duplicarTipoAuxilio;
    private TiposAuxilios editarTipoAuxilio;
    //otros
    private int cualCelda, tipoLista, index, k, bandera;
    private BigInteger l;
    private boolean guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    //
    private String infoRegistro;
    private TiposAuxilios tipoAuxilioTablaSeleccionado;
    private BigInteger verificarTablasAuxilios;
    //
    private String altoTabla;

    public ControlTiposAuxilios() {
        altoTabla = "330";
        listTiposAuxilios = null;
        crearTiposAuxilios = new ArrayList<TiposAuxilios>();
        modificarTiposAuxilios = new ArrayList<TiposAuxilios>();
        borrarTiposAuxilios = new ArrayList<TiposAuxilios>();
        permitirIndex = true;
        editarTipoAuxilio = new TiposAuxilios();
        nuevoTipoAuxilios = new TiposAuxilios();
        duplicarTipoAuxilio = new TiposAuxilios();
        guardado = true;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposAuxilios.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            getListTiposAuxilios();
            if (listTiposAuxilios != null) {
                infoRegistro = "Cantidad de registros : " + listTiposAuxilios.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void eventoFiltrar() {
        try {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            infoRegistro = "Cantidad de registros : " + filtrarTiposAuxilios.size();
            RequestContext.getCurrentInstance().update("form:informacionRegistro");
        } catch (Exception e) {
            System.err.println("ERROR CONTROLTIPOSAUXILIOS EVENTOFILTRAR  ERROR =" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listTiposAuxilios.get(index).getSecuencia();
            } else {
                secRegistro = filtrarTiposAuxilios.get(index).getSecuencia();
            }
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            altoTabla = "330";
            FacesContext c = FacesContext.getCurrentInstance();
            codigo = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposAuxilios = null;
            tipoLista = 0;
        }

        borrarTiposAuxilios.clear();
        crearTiposAuxilios.clear();
        modificarTiposAuxilios.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listTiposAuxilios = null;
        getListTiposAuxilios();
        if (listTiposAuxilios != null) {
            infoRegistro = "Cantidad de registros : " + listTiposAuxilios.size();
        } else {
            infoRegistro = "Cantidad de registros : 0";
        }
        RequestContext.getCurrentInstance().update("form:informacionRegistro");
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoReemplazo");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            altoTabla = "308";
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("width: 80px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("width: 150px");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "330";
            codigo = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
            bandera = 0;
            filtrarTiposAuxilios = null;
            tipoLista = 0;
        }
    }

    public void modificandoTipoAuxilio(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int contador = 0;
        int contadorGuardar = 0;
        Integer a;
        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!crearTiposAuxilios.contains(listTiposAuxilios.get(indice))) {
                    if (listTiposAuxilios.get(indice).getCodigo() == a || listTiposAuxilios.get(indice).getCodigo().equals(null)) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        for (int j = 0; j < listTiposAuxilios.size(); j++) {
                            if (j != indice) {
                                if (listTiposAuxilios.get(indice).getCodigo().equals(listTiposAuxilios.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                        } else {
                            contadorGuardar++;
                        }

                    }
                    if (listTiposAuxilios.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else if (listTiposAuxilios.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        contadorGuardar++;
                    }
                    if (listTiposAuxilios.get(indice).getDescripcion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else if (listTiposAuxilios.get(indice).getDescripcion().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposAuxilios.isEmpty()) {
                            modificarTiposAuxilios.add(listTiposAuxilios.get(indice));
                        } else if (!modificarTiposAuxilios.contains(listTiposAuxilios.get(indice))) {
                            modificarTiposAuxilios.add(listTiposAuxilios.get(indice));
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

                if (!crearTiposAuxilios.contains(filtrarTiposAuxilios.get(indice))) {
                    if (filtrarTiposAuxilios.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        for (int j = 0; j < listTiposAuxilios.size(); j++) {
                            if (filtrarTiposAuxilios.get(indice).getCodigo().equals(listTiposAuxilios.get(j).getCodigo())) {
                                contador++;
                            }
                        }

                        for (int j = 0; j < filtrarTiposAuxilios.size(); j++) {
                            if (j == indice) {
                                if (filtrarTiposAuxilios.get(indice).getCodigo().equals(filtrarTiposAuxilios.get(j).getCodigo())) {
                                    contador++;
                                }
                            }
                        }
                        if (contador > 0) {
                            mensajeValidacion = "CODIGOS REPETIDOS";
                        } else {
                            contadorGuardar++;
                        }

                    }

                    if (filtrarTiposAuxilios.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else if (filtrarTiposAuxilios.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        contadorGuardar++;
                    }
                    if (filtrarTiposAuxilios.get(indice).getDescripcion() == null) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else if (filtrarTiposAuxilios.get(indice).getDescripcion().equals("")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    } else {
                        contadorGuardar++;
                    }
                    if (contadorGuardar == 3) {
                        if (modificarTiposAuxilios.isEmpty()) {
                            modificarTiposAuxilios.add(filtrarTiposAuxilios.get(indice));
                        } else if (!modificarTiposAuxilios.contains(filtrarTiposAuxilios.get(indice))) {
                            modificarTiposAuxilios.add(filtrarTiposAuxilios.get(indice));
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

    public void verificarBorrado() {
        try {
            if (tipoLista == 0) {
                verificarTablasAuxilios = administrarTiposAuxilios.contarTablasAuxiliosTiposAuxilios(listTiposAuxilios.get(index).getSecuencia());
            } else {
                verificarTablasAuxilios = administrarTiposAuxilios.contarTablasAuxiliosTiposAuxilios(filtrarTiposAuxilios.get(index).getSecuencia());
            }
            if (!verificarTablasAuxilios.equals(new BigInteger("0"))) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;
                verificarTablasAuxilios = new BigInteger("-1");

            } else {
                borrandoTiposAuxilios();
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlTiposCertificados verificarBorrado ERROR " + e);
        }
    }

    public void borrandoTiposAuxilios() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!modificarTiposAuxilios.isEmpty() && modificarTiposAuxilios.contains(listTiposAuxilios.get(index))) {
                    int modIndex = modificarTiposAuxilios.indexOf(listTiposAuxilios.get(index));
                    modificarTiposAuxilios.remove(modIndex);
                    borrarTiposAuxilios.add(listTiposAuxilios.get(index));
                } else if (!crearTiposAuxilios.isEmpty() && crearTiposAuxilios.contains(listTiposAuxilios.get(index))) {
                    int crearIndex = crearTiposAuxilios.indexOf(listTiposAuxilios.get(index));
                    crearTiposAuxilios.remove(crearIndex);
                } else {
                    borrarTiposAuxilios.add(listTiposAuxilios.get(index));
                }
                listTiposAuxilios.remove(index);
            }
            if (tipoLista == 1) {
                if (!modificarTiposAuxilios.isEmpty() && modificarTiposAuxilios.contains(filtrarTiposAuxilios.get(index))) {
                    int modIndex = modificarTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                    modificarTiposAuxilios.remove(modIndex);
                    borrarTiposAuxilios.add(filtrarTiposAuxilios.get(index));
                } else if (!crearTiposAuxilios.isEmpty() && crearTiposAuxilios.contains(filtrarTiposAuxilios.get(index))) {
                    int crearIndex = crearTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                    crearTiposAuxilios.remove(crearIndex);
                } else {
                    borrarTiposAuxilios.add(filtrarTiposAuxilios.get(index));
                }
                int VCIndex = listTiposAuxilios.indexOf(filtrarTiposAuxilios.get(index));
                listTiposAuxilios.remove(VCIndex);
                filtrarTiposAuxilios.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            infoRegistro = "Cantidad de registros : " + listTiposAuxilios.size();
            RequestContext.getCurrentInstance().update("form:informacionRegistro");
            context.update("form:datosTipoReemplazo");
            context.update("form:ACEPTAR");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
        }

    }

    public void revisarDialogoGuardar() {
        if (!borrarTiposAuxilios.isEmpty() || !crearTiposAuxilios.isEmpty() || !modificarTiposAuxilios.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarTiposAuxilio() {
        RequestContext context = RequestContext.getCurrentInstance();
        try {
            if (guardado == false) {
                if (!borrarTiposAuxilios.isEmpty()) {
                    administrarTiposAuxilios.borrarTiposAuxilios(borrarTiposAuxilios);
                    //mostrarBorrados
                    registrosBorrados = borrarTiposAuxilios.size();
                    context.update("form:mostrarBorrados");
                    context.execute("mostrarBorrados.show()");
                    borrarTiposAuxilios.clear();
                }
                if (!crearTiposAuxilios.isEmpty()) {
                    administrarTiposAuxilios.crearTiposAuxilios(crearTiposAuxilios);
                    crearTiposAuxilios.clear();
                }
                if (!modificarTiposAuxilios.isEmpty()) {
                    administrarTiposAuxilios.modificarTiposAuxilios(modificarTiposAuxilios);
                    modificarTiposAuxilios.clear();
                }
                listTiposAuxilios = null;
                guardado = true;
                context.update("form:datosTipoReemplazo");
                k = 0;
                index = -1;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
                FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");
            }
        } catch (Exception e) {
            System.out.println("Error guardarTiposAuxilio : " + e.toString());
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
        }
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoAuxilio = listTiposAuxilios.get(index);
            }
            if (tipoLista == 1) {
                editarTipoAuxilio = filtrarTiposAuxilios.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
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

    public void agregarNuevoTiposAuxilios() {
        int contador = 0;
        int duplicados = 0;

        Integer a = 0;
        a = null;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoTipoAuxilios.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
        } else {
            for (int x = 0; x < listTiposAuxilios.size(); x++) {
                if (listTiposAuxilios.get(x).getCodigo() == nuevoTipoAuxilios.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO hayan codigos repetidos \n";
            } else {
                contador++;
            }
        }
        if (nuevoTipoAuxilios.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe tener una descripción \n";
        } else {
            contador++;
        }
        if (contador == 2) {
            if (bandera == 1) {
                altoTabla = "330";
                FacesContext c = FacesContext.getCurrentInstance();
                codigo = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposAuxilios = null;
                tipoLista = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoTipoAuxilios.setSecuencia(l);

            crearTiposAuxilios.add(nuevoTipoAuxilios);

            listTiposAuxilios.add(nuevoTipoAuxilios);
            nuevoTipoAuxilios = new TiposAuxilios();
            context.update("form:datosTipoReemplazo");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            infoRegistro = "Cantidad de registros : " + listTiposAuxilios.size();

            RequestContext.getCurrentInstance().update("form:informacionRegistro");
            context.execute("nuevoRegistroTiposReemplazos.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoTiposAuxilios() {
        nuevoTipoAuxilios = new TiposAuxilios();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicandoTiposAuxilios() {
        if (index >= 0) {
            duplicarTipoAuxilio = new TiposAuxilios();

            if (tipoLista == 0) {
                duplicarTipoAuxilio.setCodigo(listTiposAuxilios.get(index).getCodigo());
                duplicarTipoAuxilio.setDescripcion(listTiposAuxilios.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarTipoAuxilio.setCodigo(filtrarTiposAuxilios.get(index).getCodigo());
                duplicarTipoAuxilio.setDescripcion(filtrarTiposAuxilios.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTTR");
            context.execute("duplicarRegistroTiposReemplazos.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;

        if (duplicarTipoAuxilio.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
        } else {
            for (int x = 0; x < listTiposAuxilios.size(); x++) {
                if (listTiposAuxilios.get(x).getCodigo() == duplicarTipoAuxilio.getCodigo()) {
                    duplicados++;
                }
            }
            if (duplicados > 0) {
                mensajeValidacion = " *Que NO Existan Codigo Repetidos \n";
            } else {
                contador++;
                duplicados = 0;
            }
        }
        if (duplicarTipoAuxilio.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Una descripción \n";
        } else {
            contador++;
        }

        if (contador == 2) {
            k++;
            l = BigInteger.valueOf(k);
            duplicarTipoAuxilio.setSecuencia(l);
            listTiposAuxilios.add(duplicarTipoAuxilio);
            crearTiposAuxilios.add(duplicarTipoAuxilio);
            context.update("form:datosTipoReemplazo");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                altoTabla = "330";
                FacesContext c = FacesContext.getCurrentInstance();
                codigo = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) c.getViewRoot().findComponent("form:datosTipoReemplazo:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoReemplazo");
                bandera = 0;
                filtrarTiposAuxilios = null;
                tipoLista = 0;
            }
            duplicarTipoAuxilio = new TiposAuxilios();
            infoRegistro = "Cantidad de registros : " + listTiposAuxilios.size();
            RequestContext.getCurrentInstance().update("form:informacionRegistro");
            RequestContext.getCurrentInstance().execute("duplicarRegistroTiposReemplazos.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarTiposAuxilios() {
        duplicarTipoAuxilio = new TiposAuxilios();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TIPOSAUXILIOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosTipoReemplazoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TIPOSAUXILIOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listTiposAuxilios.isEmpty()) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSAUXILIOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSAUXILIOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //--------///////////////////////---------------------*****//*/*/*/*/*/-****----
    public List<TiposAuxilios> getListTiposAuxilios() {
        if (listTiposAuxilios == null) {
            listTiposAuxilios = administrarTiposAuxilios.consultarTiposAuxilios();
        }
        return listTiposAuxilios;
    }

    public void setListTiposAuxilios(List<TiposAuxilios> listTiposAuxilios) {
        this.listTiposAuxilios = listTiposAuxilios;
    }

    public List<TiposAuxilios> getFiltrarTiposAuxilios() {
        return filtrarTiposAuxilios;
    }

    public void setFiltrarTiposAuxilios(List<TiposAuxilios> filtrarTiposAuxilios) {
        this.filtrarTiposAuxilios = filtrarTiposAuxilios;
    }

    public TiposAuxilios getNuevoTipoAuxilios() {
        return nuevoTipoAuxilios;
    }

    public void setNuevoTipoAuxilios(TiposAuxilios nuevoTipoAuxilios) {
        this.nuevoTipoAuxilios = nuevoTipoAuxilios;
    }

    public TiposAuxilios getDuplicarTipoAuxilio() {
        return duplicarTipoAuxilio;
    }

    public void setDuplicarTipoAuxilio(TiposAuxilios duplicarTipoAuxilio) {
        this.duplicarTipoAuxilio = duplicarTipoAuxilio;
    }

    public TiposAuxilios getEditarTipoAuxilio() {
        return editarTipoAuxilio;
    }

    public void setEditarTipoAuxilio(TiposAuxilios editarTipoAuxilio) {
        this.editarTipoAuxilio = editarTipoAuxilio;
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

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public TiposAuxilios getTipoAuxilioTablaSeleccionado() {
        getListTiposAuxilios();
        if (listTiposAuxilios != null) {
            int tam = listTiposAuxilios.size();
            if (tam > 0) {
                tipoAuxilioTablaSeleccionado = listTiposAuxilios.get(0);
            }
        }
        return tipoAuxilioTablaSeleccionado;
    }

    public void setTipoAuxilioTablaSeleccionado(TiposAuxilios tipoAuxilioTablaSeleccionado) {
        this.tipoAuxilioTablaSeleccionado = tipoAuxilioTablaSeleccionado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

}

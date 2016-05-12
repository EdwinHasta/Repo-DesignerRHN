/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.NormasLaborales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarNormasLaboralesInterface;
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
 * @author John Pineda
 */
@ManagedBean
@SessionScoped
public class ControlNormasLaborales implements Serializable {

    @EJB
    AdministrarNormasLaboralesInterface administrarNormasLaborales;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<NormasLaborales> listNormasLaborales;
    private List<NormasLaborales> filtrarNormasLaborales;
    private List<NormasLaborales> crearNormaLaboral;
    private List<NormasLaborales> modificarNormaLaboral;
    private List<NormasLaborales> borrarNormaLaboral;
    private NormasLaborales nuevoNormaLaboral;
    private NormasLaborales duplicarNormaLaboral;
    private NormasLaborales editarNormaLaboral;
    private NormasLaborales normaLaboralSeleccionada;
//otros
    private int cualCelda, tipoLista, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private Column codigo, descripcion;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger borradoVC;
    private String backUpDescripcion;
    private Integer backUpCodigo;
    private int tamano;
    private String paginaAnterior;
    //    
    private String infoRegistro;
    //
    private DataTable tablaC;

    public ControlNormasLaborales() {
        listNormasLaborales = null;
        normaLaboralSeleccionada = null;
        crearNormaLaboral = new ArrayList<NormasLaborales>();
        modificarNormaLaboral = new ArrayList<NormasLaborales>();
        borrarNormaLaboral = new ArrayList<NormasLaborales>();
        permitirIndex = true;
        editarNormaLaboral = new NormasLaborales();
        nuevoNormaLaboral = new NormasLaborales();
        duplicarNormaLaboral = new NormasLaborales();
        guardado = true;
        tamano = 270;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarNormasLaborales.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
        getListNormasLaborales();
        if (listNormasLaborales != null) {
            if (!listNormasLaborales.isEmpty()) {
                normaLaboralSeleccionada = listNormasLaborales.get(0);
                modificarInfoRegistro(listNormasLaborales.size());
            }
        } else {
            modificarInfoRegistro(0);
        }
    }

    public String redirigir() {
        return paginaAnterior;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            modificarInfoRegistro(filtrarNormasLaborales.size());
            System.err.println("filtrarNormasLaborales.size(): " + filtrarNormasLaborales.size());
            context.update("form:informacionRegistro");
        } catch (Exception e) {
            System.out.println("ERROR ControlNormasLaborales eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(NormasLaborales normaL, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            normaLaboralSeleccionada = normaL;
            cualCelda = celda;
            if (cualCelda == 0) {
                backUpCodigo = normaLaboralSeleccionada.getCodigo();
                System.out.println(" backUpCodigo : " + backUpCodigo);
            } else if (cualCelda == 1) {
                backUpDescripcion = normaLaboralSeleccionada.getNombre();
                System.out.println(" backUpDescripcion : " + backUpDescripcion);
            }
//                secRegistro = normaLaboralSeleccionada.getSecuencia();
        }
        System.out.println("Indice: " + normaLaboralSeleccionada + " Celda: " + cualCelda);
    }

    public void asignarIndex(NormasLaborales normaL, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlNormasLaborales.asignarIndex \n");
            normaLaboralSeleccionada = normaL;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlNormasLaborales.asignarIndex ERROR======" + e.getMessage());
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void listaValoresBoton() {
    }

    public void cancelarModificacion() {
        cerrarFiltrado();
        borrarNormaLaboral.clear();
        crearNormaLaboral.clear();
        modificarNormaLaboral.clear();
        k = 0;
        listNormasLaborales = null;
        normaLaboralSeleccionada = null;
        guardado = true;
        permitirIndex = true;
        getListNormasLaborales();
        if (listNormasLaborales != null) {
            modificarInfoRegistro(listNormasLaborales.size());
        } else {
            modificarInfoRegistro(0);
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:informacionRegistro");
        context.update("form:datosNormaLaboral");
        context.update("form:ACEPTAR");
    }

    public void salir() {
        cerrarFiltrado();
        borrarNormaLaboral.clear();
        crearNormaLaboral.clear();
        modificarNormaLaboral.clear();
        normaLaboralSeleccionada = null;
        k = 0;
        listNormasLaborales = null;
        guardado = true;
    }

    public void cerrarFiltrado() {
        FacesContext c = FacesContext.getCurrentInstance();

        codigo = (Column) c.getViewRoot().findComponent("form:datosNormaLaboral:codigo");
        codigo.setFilterStyle("display: none; visibility: hidden;");
        descripcion = (Column) c.getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
        descripcion.setFilterStyle("display: none; visibility: hidden;");
        RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
        bandera = 0;
        filtrarNormasLaborales = null;
        tipoLista = 0;
    }

    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0) {
            tamano = 246;
            codigo = (Column) c.getViewRoot().findComponent("form:datosNormaLaboral:codigo");
            codigo.setFilterStyle("width: 200px");
            descripcion = (Column) c.getViewRoot().findComponent("form:datosNormaLaboral:descripcion");
            descripcion.setFilterStyle("width: 200px");
            RequestContext.getCurrentInstance().update("form:datosNormaLaboral");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            cerrarFiltrado();
        }
    }

    public void modificarNormaLaboral(NormasLaborales normaL, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR NORMA LABORAL");
        normaLaboralSeleccionada = normaL;

        int contador = 0;
        boolean banderita = false;
//        Integer a;
//        a = null;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR NORMA LABORAL, CONFIRMAR CAMBIO ES N");
            if (!crearNormaLaboral.contains(normaLaboralSeleccionada)) {
                if (normaLaboralSeleccionada.getCodigo() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                    normaLaboralSeleccionada.setCodigo(backUpCodigo);
                } else {
                    for (int j = 0; j < listNormasLaborales.size(); j++) {
                        if (j != listNormasLaborales.indexOf(normaLaboralSeleccionada)) {
                            if (normaLaboralSeleccionada.getCodigo().equals(listNormasLaborales.get(j).getCodigo())) {
                                contador++;
                            }
                        }
                    }
                    if (contador > 0) {
                        mensajeValidacion = "CODIGOS REPETIDOS";
                        banderita = false;
                        normaLaboralSeleccionada.setCodigo(backUpCodigo);
                    } else {
                        banderita = true;
                    }

                }
                if (normaLaboralSeleccionada.getNombre().isEmpty()) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                    normaLaboralSeleccionada.setNombre(backUpDescripcion);

                }
                if (normaLaboralSeleccionada.getNombre().equals(" ")) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    normaLaboralSeleccionada.setNombre(backUpDescripcion);
                    banderita = false;
                }

                if (banderita == true) {
                    if (modificarNormaLaboral.isEmpty()) {
                        modificarNormaLaboral.add(normaLaboralSeleccionada);
                    } else if (!modificarNormaLaboral.contains(normaLaboralSeleccionada)) {
                        modificarNormaLaboral.add(normaLaboralSeleccionada);
                    }
                    if (guardado) {
                        guardado = false;
                    }

                } else {
                    context.update("form:validacionModificar");
                    context.execute("validacionModificar.show()");
                }
            } else {
                if (normaLaboralSeleccionada.getCodigo() == null) {
                    mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                    banderita = false;
                    normaLaboralSeleccionada.setCodigo(backUpCodigo);
                }
            }
            context.update("form:datosNormaLaboral");
            context.update("form:ACEPTAR");
        }

    }

    public void borrarNormasLaborales() {

        if (normaLaboralSeleccionada != null) {
            System.out.println("Entro a borrarNormasLaborales");
            if (!modificarNormaLaboral.isEmpty() && modificarNormaLaboral.contains(normaLaboralSeleccionada)) {
                int modIndex = modificarNormaLaboral.indexOf(normaLaboralSeleccionada);
                modificarNormaLaboral.remove(modIndex);
                borrarNormaLaboral.add(normaLaboralSeleccionada);
            } else if (!crearNormaLaboral.isEmpty() && crearNormaLaboral.contains(normaLaboralSeleccionada)) {
                int crearIndex = crearNormaLaboral.indexOf(normaLaboralSeleccionada);
                crearNormaLaboral.remove(crearIndex);
            } else {
                borrarNormaLaboral.add(normaLaboralSeleccionada);
            }
            listNormasLaborales.remove(normaLaboralSeleccionada);
            if (tipoLista == 1) {
                filtrarNormasLaborales.remove(normaLaboralSeleccionada);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosNormaLaboral");
            //infoRegistro = "Cantidad de registros: " + listNormasLaborales.size();
            modificarInfoRegistro(listNormasLaborales.size());
            context.update("form:informacionRegistro");
            normaLaboralSeleccionada = null;
            if (guardado) {
                guardado = false;
            }
            context.update("form:ACEPTAR");
        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            borradoVC = administrarNormasLaborales.contarVigenciasNormasEmpleadoNormaLaboral(normaLaboralSeleccionada.getSecuencia());

            if (borradoVC.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarNormasLaborales();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                normaLaboralSeleccionada = null;
                borradoVC = new BigInteger("-1");
            }

        } catch (Exception e) {
            System.err.println("ERROR ControlNormasLaborales verificarBorrado ERROR " + e);
        }
    }

    public void guardarNormasLaborales() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            System.out.println("Realizando Normas Labolares");
            if (!borrarNormaLaboral.isEmpty()) {
                administrarNormasLaborales.borrarNormasLaborales(borrarNormaLaboral);
                //mostrarBorrados
                registrosBorrados = borrarNormaLaboral.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarNormaLaboral.clear();
            }
            if (!crearNormaLaboral.isEmpty()) {
                administrarNormasLaborales.crearNormasLaborales(crearNormaLaboral);
                crearNormaLaboral.clear();
            }
            if (!modificarNormaLaboral.isEmpty()) {
                administrarNormasLaborales.modificarNormasLaborales(modificarNormaLaboral);
                modificarNormaLaboral.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listNormasLaborales = null;
             getListNormasLaborales();
            if (listNormasLaborales != null) {
                normaLaboralSeleccionada = listNormasLaborales.get(0);
                modificarInfoRegistro(listNormasLaborales.size());
            } else {
                modificarInfoRegistro(0);
            }
            context.update("form:datosNormaLaboral");
            FacesMessage msg = new FacesMessage("Información", "Se guardarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            k = 0;
            guardado = true;
        }
        RequestContext.getCurrentInstance().update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (normaLaboralSeleccionada != null) {
            editarNormaLaboral = normaLaboralSeleccionada;
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
    }

    public void agregarNuevoNormaLaboral() {
        System.out.println("Agregar Norma Laboral");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoNormaLaboral.getCodigo() == null) {
            mensajeValidacion = " *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoNormaLaboral.getCodigo());

            for (int x = 0; x < listNormasLaborales.size(); x++) {
                if (listNormasLaborales.get(x).getCodigo().equals(nuevoNormaLaboral.getCodigo())) {
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
        if (nuevoNormaLaboral.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (nuevoNormaLaboral.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 2) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                cerrarFiltrado();
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoNormaLaboral.setSecuencia(l);

            crearNormaLaboral.add(nuevoNormaLaboral);
            listNormasLaborales.add(nuevoNormaLaboral);
            normaLaboralSeleccionada = listNormasLaborales.get(listNormasLaborales.indexOf(nuevoNormaLaboral));
            nuevoNormaLaboral = new NormasLaborales();

            context.update("form:datosNormaLaboral");
            modificarInfoRegistro(listNormasLaborales.size());
            context.update("form:informacionRegistro");
            if (guardado) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroNormaLaboral.hide()");

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoNormaLaboral() {
        System.out.println("limpiarNuevoNormaLaboral");
        nuevoNormaLaboral = new NormasLaborales();
        normaLaboralSeleccionada = null;

    }

    //------------------------------------------------------------------------------
    public void duplicarNormaLaborales() {
        System.out.println("duplicarNormaLaboral");
        if (normaLaboralSeleccionada != null) {
            duplicarNormaLaboral = new NormasLaborales();
            k++;
            l = BigInteger.valueOf(k);
            duplicarNormaLaboral.setSecuencia(l);
            duplicarNormaLaboral.setCodigo(normaLaboralSeleccionada.getCodigo());
            duplicarNormaLaboral.setNombre(normaLaboralSeleccionada.getNombre());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarNormaLaboral");
            context.execute("duplicarRegistroNormasLaborales.show()");
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR CONTROLNORMASLABORALES");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        Integer a = 0;
        a = null;
        System.err.println("ConfirmarDuplicar codigo " + duplicarNormaLaboral.getCodigo());
        System.err.println("ConfirmarDuplicar nombre " + duplicarNormaLaboral.getNombre());

        if (duplicarNormaLaboral.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   *Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listNormasLaborales.size(); x++) {
                if (listNormasLaborales.get(x).getCodigo().equals(duplicarNormaLaboral.getCodigo())) {
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
        if (duplicarNormaLaboral.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else if (duplicarNormaLaboral.getNombre().isEmpty()) {
            mensajeValidacion = mensajeValidacion + " *Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        if (contador == 2) {

            System.out.println("Datos Duplicando: " + duplicarNormaLaboral.getSecuencia() + "  " + duplicarNormaLaboral.getCodigo());
            if (crearNormaLaboral.contains(duplicarNormaLaboral)) {
                System.out.println("Ya lo contengo.");
            }
            listNormasLaborales.add(duplicarNormaLaboral);
            crearNormaLaboral.add(duplicarNormaLaboral);
            normaLaboralSeleccionada = listNormasLaborales.get(listNormasLaborales.indexOf(duplicarNormaLaboral));
            context.update("form:datosNormaLaboral");
            //infoRegistro = "Cantidad de registros: " + listNormasLaborales.size();
            modificarInfoRegistro(listNormasLaborales.size());
            context.update("form:informacionRegistro");
            if (guardado) {
                guardado = false;
                context.update("form:ACEPTAR");
            }
            if (bandera == 1) {
                cerrarFiltrado();
            }
            duplicarNormaLaboral = new NormasLaborales();
            RequestContext.getCurrentInstance().execute("duplicarRegistroNormasLaborales.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarNormasLaborales() {
        duplicarNormaLaboral = new NormasLaborales();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNormaLaboralExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "NormasLaborales", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosNormaLaboralExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "NormasLaborales", false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (normaLaboralSeleccionada != null) {
            int resultado = administrarRastros.obtenerTabla(normaLaboralSeleccionada.getSecuencia(), "NORMASLABORALES"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("NORMASLABORALES")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
    }

    private void modificarInfoRegistro(int valor) {
        infoRegistro = String.valueOf(valor);
    }
    
    
    public void recordarSeleccion() {
        if (normaLaboralSeleccionada != null) {
            FacesContext c = FacesContext.getCurrentInstance();
            tablaC = (DataTable) c.getViewRoot().findComponent("form:datosNormaLaboral");
            tablaC.setSelection(normaLaboralSeleccionada);
        }
    }

    //-------------------------------------------------------------------------- 
    public List<NormasLaborales> getListNormasLaborales() {
        if (listNormasLaborales == null) {
            listNormasLaborales = administrarNormasLaborales.consultarNormasLaborales();
        }
        return listNormasLaborales;
    }

    public void setListNormasLaborales(List<NormasLaborales> listNormasLaborales) {
        this.listNormasLaborales = listNormasLaborales;
    }

    public List<NormasLaborales> getFiltrarNormasLaborales() {
        return filtrarNormasLaborales;
    }

    public void setFiltrarNormasLaborales(List<NormasLaborales> filtrarNormasLaborales) {
        this.filtrarNormasLaborales = filtrarNormasLaborales;
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

    public NormasLaborales getNuevoNormaLaboral() {
        return nuevoNormaLaboral;
    }

    public void setNuevoNormaLaboral(NormasLaborales nuevoNormaLaboral) {
        this.nuevoNormaLaboral = nuevoNormaLaboral;
    }

    public NormasLaborales getDuplicarNormaLaboral() {
        return duplicarNormaLaboral;
    }

    public void setDuplicarNormaLaboral(NormasLaborales duplicarNormaLaboral) {
        this.duplicarNormaLaboral = duplicarNormaLaboral;
    }

    public NormasLaborales getEditarNormaLaboral() {
        return editarNormaLaboral;
    }

    public void setEditarNormaLaboral(NormasLaborales editarNormaLaboral) {
        this.editarNormaLaboral = editarNormaLaboral;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public NormasLaborales getNormaLaboralSeleccionada() {
        return normaLaboralSeleccionada;
    }

    public void setNormaLaboralSeleccionada(NormasLaborales normaLaboralSeleccionada) {
        this.normaLaboralSeleccionada = normaLaboralSeleccionada;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }
}

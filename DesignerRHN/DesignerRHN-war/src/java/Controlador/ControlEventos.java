/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Eventos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEventosInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
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
public class ControlEventos implements Serializable {

    @EJB
    AdministrarEventosInterface administrarEventos;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<Eventos> listEventos;
    private List<Eventos> filtrarEventos;
    private List<Eventos> crearEventos;
    private List<Eventos> modificarEventos;
    private List<Eventos> borrarEventos;
    private Eventos nuevoEvento;
    private Eventos duplicarEvento;
    private Eventos editarEvento;
    //otros
    private int cualCelda, tipoLista, index, tipoActualizacion, k, bandera;
    private BigInteger l;
    private boolean aceptar, guardado;
    //AutoCompletar
    private boolean permitirIndex;
    //RASTRO
    private BigInteger secRegistro;
    private Column codigo, descripcion, organizador, objetivo;
    //borrado
    private int registrosBorrados;
    private String mensajeValidacion;
    private BigInteger vigenciasEventos;
    private Integer a;

    public ControlEventos() {
        listEventos = null;
        crearEventos = new ArrayList<Eventos>();
        modificarEventos = new ArrayList<Eventos>();
        borrarEventos = new ArrayList<Eventos>();
        permitirIndex = true;
        editarEvento = new Eventos();
        nuevoEvento = new Eventos();
        duplicarEvento = new Eventos();
        a = null;
        guardado = true;
    }

    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlEventos.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlEventos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void cambiarIndice(int indice, int celda) {
        System.err.println("TIPO LISTA = " + tipoLista);

        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listEventos.get(index).getSecuencia();

        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    public void asignarIndex(Integer indice, int LND, int dig) {
        try {
            System.out.println("\n ENTRE A ControlEventos.asignarIndex \n");
            index = indice;
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlEventos.asignarIndex ERROR======" + e.getMessage());
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
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
            organizador.setFilterStyle("display: none; visibility: hidden;");
            objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
            objetivo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvento");
            bandera = 0;
            filtrarEventos = null;
            tipoLista = 0;
        }

        borrarEventos.clear();
        crearEventos.clear();
        modificarEventos.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listEventos = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosEvento");
        context.update("form:ACEPTAR");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {

            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
            codigo.setFilterStyle("width: 50px");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
            descripcion.setFilterStyle("width: 290px");
            organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
            organizador.setFilterStyle("width: 95px");
            objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
            objetivo.setFilterStyle("width: 280px");

            RequestContext.getCurrentInstance().update("form:datosEvento");
            System.out.println("Activar");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
            codigo.setFilterStyle("display: none; visibility: hidden;");
            descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
            descripcion.setFilterStyle("display: none; visibility: hidden;");
            organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
            organizador.setFilterStyle("display: none; visibility: hidden;");
            objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
            objetivo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosEvento");
            bandera = 0;
            filtrarEventos = null;
            tipoLista = 0;
        }
    }

    public void modificarEventos(int indice, String confirmarCambio, String valorConfirmar) {
        System.err.println("ENTRE A MODIFICAR Eventos");
        index = indice;

        int contador = 0;
        boolean banderita = false;

        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("TIPO LISTA = " + tipoLista);
        if (confirmarCambio.equalsIgnoreCase("N")) {
            System.err.println("ENTRE A MODIFICAR Eventos, CONFIRMAR CAMBIO ES N");
            if (tipoLista == 0) {
                if (!crearEventos.contains(listEventos.get(indice))) {
                    if (listEventos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEventos.size(); j++) {
                            if (j != indice) {
                                if (listEventos.get(indice).getCodigo() == listEventos.get(j).getCodigo()) {
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
                    if (listEventos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEventos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (listEventos.get(indice).getOrganizador().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEventos.get(indice).getOrganizador().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEventos.get(indice).getObjetivo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (listEventos.get(indice).getObjetivo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEventos.isEmpty()) {
                            modificarEventos.add(listEventos.get(indice));
                        } else if (!modificarEventos.contains(listEventos.get(indice))) {
                            modificarEventos.add(listEventos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:ACEPTAR");

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                    }
                    index = -1;
                    secRegistro = null;
                }
            } else {

                if (!crearEventos.contains(filtrarEventos.get(indice))) {
                    if (filtrarEventos.get(indice).getCodigo() == a) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    } else {
                        for (int j = 0; j < listEventos.size(); j++) {
                            if (j != indice) {
                                if (filtrarEventos.get(indice).getCodigo() == listEventos.get(j).getCodigo()) {
                                    contador++;
                                }
                            }
                        }
                        for (int j = 0; j < filtrarEventos.size(); j++) {
                            if (j != indice) {
                                if (filtrarEventos.get(indice).getCodigo() == filtrarEventos.get(j).getCodigo()) {
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

                    if (filtrarEventos.get(indice).getDescripcion().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEventos.get(indice).getDescripcion().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEventos.get(indice).getObjetivo().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEventos.get(indice).getObjetivo().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEventos.get(indice).getOrganizador().isEmpty()) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }
                    if (filtrarEventos.get(indice).getOrganizador().equals(" ")) {
                        mensajeValidacion = "NO PUEDEN HABER CAMPOS VACIOS";
                        banderita = false;
                    }

                    if (banderita == true) {
                        if (modificarEventos.isEmpty()) {
                            modificarEventos.add(filtrarEventos.get(indice));
                        } else if (!modificarEventos.contains(filtrarEventos.get(indice))) {
                            modificarEventos.add(filtrarEventos.get(indice));
                        }
                        if (guardado == true) {
                            guardado = false;
                        }
                        context.update("form:ACEPTAR");

                    } else {
                        context.update("form:validacionModificar");
                        context.execute("validacionModificar.show()");
                        cancelarModificacion();
                        contador = 0;
                    }
                    index = -1;
                    secRegistro = null;
                }

            }
            context.update("form:datosEvento");
        }

    }

    public void borrarEventos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                System.out.println("Entro a borrarEventos");
                if (!modificarEventos.isEmpty() && modificarEventos.contains(listEventos.get(index))) {
                    int modIndex = modificarEventos.indexOf(listEventos.get(index));
                    modificarEventos.remove(modIndex);
                    borrarEventos.add(listEventos.get(index));
                } else if (!crearEventos.isEmpty() && crearEventos.contains(listEventos.get(index))) {
                    int crearIndex = crearEventos.indexOf(listEventos.get(index));
                    crearEventos.remove(crearIndex);
                } else {
                    borrarEventos.add(listEventos.get(index));
                }
                listEventos.remove(index);
            }
            if (tipoLista == 1) {
                System.out.println("borrarGrupoInfAdicional ");
                if (!modificarEventos.isEmpty() && modificarEventos.contains(filtrarEventos.get(index))) {
                    int modIndex = modificarEventos.indexOf(filtrarEventos.get(index));
                    modificarEventos.remove(modIndex);
                    borrarEventos.add(filtrarEventos.get(index));
                } else if (!crearEventos.isEmpty() && crearEventos.contains(filtrarEventos.get(index))) {
                    int crearIndex = crearEventos.indexOf(filtrarEventos.get(index));
                    crearEventos.remove(crearIndex);
                } else {
                    borrarEventos.add(filtrarEventos.get(index));
                }
                int VCIndex = listEventos.indexOf(filtrarEventos.get(index));
                listEventos.remove(VCIndex);
                filtrarEventos.remove(index);

            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosEvento");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");

        }

    }

    public void verificarBorrado() {
        System.out.println("Estoy en verificarBorrado");
        try {
            System.err.println("Control Secuencia de verificarBorrado  a borrar");
            vigenciasEventos = administrarEventos.verificarVigenciasEventos(listEventos.get(index).getSecuencia());

            if (vigenciasEventos.equals(new BigInteger("0"))) {
                System.out.println("Borrado==0");
                borrarEventos();
            } else {
                System.out.println("Borrado>0");

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:validacionBorrar");
                context.execute("validacionBorrar.show()");
                index = -1;

                vigenciasEventos = new BigInteger("-1");

            }
        } catch (Exception e) {
            System.err.println("ERROR ControlEstadosCiviles verificarBorrado ERROR " + e);
        }
    }

    public void revisarDialogoGuardar() {

        if (!borrarEventos.isEmpty() || !crearEventos.isEmpty() || !modificarEventos.isEmpty()) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }

    }

    public void guardarEventos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (guardado == false) {
            if (bandera == 1) {
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
                organizador.setFilterStyle("display: none; visibility: hidden;");
                objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
                objetivo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvento");
            }
            System.out.println("Realizando GruposInfAdicionales");
            if (!borrarEventos.isEmpty()) {
                administrarEventos.borrarEventos(borrarEventos);
                //mostrarBorrados
                registrosBorrados = borrarEventos.size();
                context.update("form:mostrarBorrados");
                context.execute("mostrarBorrados.show()");
                borrarEventos.clear();
            }
            if (!crearEventos.isEmpty()) {
                administrarEventos.crearEventos(crearEventos);
                crearEventos.clear();
            }
            if (!modificarEventos.isEmpty()) {
                administrarEventos.modificarEventos(modificarEventos);
                modificarEventos.clear();
            }
            System.out.println("Se guardaron los datos con exito");
            listEventos = null;
            context.update("form:datosEvento");
            k = 0;
        }
        index = -1;
        guardado = true;
        context.update("form:ACEPTAR");

    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarEvento = listEventos.get(index);
            }
            if (tipoLista == 1) {
                editarEvento = filtrarEventos.get(index);
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
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editOrganizador");
                context.execute("editOrganizador.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editObjetivo");
                context.execute("editObjetivo.show()");
                cualCelda = -1;
            }

        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoEventos() {
        System.out.println("Agregar GruposInfAdicionales");
        int contador = 0;
        int duplicados = 0;

        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevoEvento.getCodigo() == a) {
            mensajeValidacion = " *Debe Tener Un Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            System.out.println("codigo en Motivo Cambio Cargo: " + nuevoEvento.getCodigo());

            for (int x = 0; x < listEventos.size(); x++) {
                if (listEventos.get(x).getCodigo() == nuevoEvento.getCodigo()) {
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
        if (nuevoEvento.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoEvento.getDescripcion() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener una Descripcion \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }
        if (nuevoEvento.getObjetivo() == (null)) {
            mensajeValidacion = mensajeValidacion + " *Debe Tener un Objetivo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("bandera");
            contador++;

        }

        System.out.println("contador " + contador);

        if (contador == 4) {
            if (bandera == 1) {
                //CERRAR FILTRADO
                System.out.println("Desactivar");
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
                organizador.setFilterStyle("display: none; visibility: hidden;");
                objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
                objetivo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvento");
                bandera = 0;
                filtrarEventos = null;
                tipoLista = 0;
            }
            System.out.println("Despues de la bandera");

            k++;
            l = BigInteger.valueOf(k);
            nuevoEvento.setSecuencia(l);

            crearEventos.add(nuevoEvento);

            listEventos.add(nuevoEvento);
            nuevoEvento = new Eventos();

            context.update("form:datosEvento");
            if (guardado == true) {
                guardado = false;
                context.update("form:ACEPTAR");
            }

            context.execute("nuevoRegistroEvento.hide()");
            index = -1;
            secRegistro = null;

        } else {
            context.update("form:validacionNuevaCentroCosto");
            context.execute("validacionNuevaCentroCosto.show()");
            contador = 0;
        }
    }

    public void limpiarNuevoEventos() {
        System.out.println("limpiarNuevoEstadosCiviles");
        nuevoEvento = new Eventos();
        secRegistro = null;
        index = -1;

    }

    //------------------------------------------------------------------------------
    public void duplicarEventos() {
        System.out.println("duplicarEstadosCiviles");
        if (index >= 0) {
            duplicarEvento = new Eventos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarEvento.setSecuencia(l);
                duplicarEvento.setCodigo(listEventos.get(index).getCodigo());
                duplicarEvento.setDescripcion(listEventos.get(index).getDescripcion());
                duplicarEvento.setOrganizador(listEventos.get(index).getOrganizador());
                duplicarEvento.setObjetivo(listEventos.get(index).getObjetivo());
            }
            if (tipoLista == 1) {
                duplicarEvento.setSecuencia(l);
                duplicarEvento.setCodigo(filtrarEventos.get(index).getCodigo());
                duplicarEvento.setDescripcion(filtrarEventos.get(index).getDescripcion());
                duplicarEvento.setOrganizador(listEventos.get(index).getOrganizador());
                duplicarEvento.setObjetivo(listEventos.get(index).getObjetivo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarE");
            context.execute("duplicarRegistroEvento.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void confirmarDuplicar() {
        System.err.println("ESTOY EN CONFIRMAR DUPLICAR GruposInfAdicionales");
        int contador = 0;
        mensajeValidacion = " ";
        int duplicados = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("ConfirmarDuplicar codigo " + duplicarEvento.getCodigo());
        System.err.println("ConfirmarDuplicar Descripcion " + duplicarEvento.getDescripcion());

        if (duplicarEvento.getCodigo() == a) {
            mensajeValidacion = mensajeValidacion + "   * Codigo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);
        } else {
            for (int x = 0; x < listEventos.size(); x++) {
                if (listEventos.get(x).getCodigo() == duplicarEvento.getCodigo()) {
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
        if (duplicarEvento.getDescripcion() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Nombre \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarEvento.getOrganizador() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Organizador \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }
        if (duplicarEvento.getObjetivo() == null) {
            mensajeValidacion = mensajeValidacion + "   * Un Objetivo \n";
            System.out.println("Mensaje validacion : " + mensajeValidacion);

        } else {
            System.out.println("Bandera : ");
            contador++;
        }

        if (contador == 4) {

            System.out.println("Datos Duplicando: " + duplicarEvento.getSecuencia() + "  " + duplicarEvento.getCodigo());
            if (crearEventos.contains(duplicarEvento)) {
                System.out.println("Ya lo contengo.");
            }
            listEventos.add(duplicarEvento);
            crearEventos.add(duplicarEvento);
            context.update("form:datosEvento");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
            }
            context.update("form:ACEPTAR");

            if (bandera == 1) {
                //CERRAR FILTRADO
                codigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:codigo");
                codigo.setFilterStyle("display: none; visibility: hidden;");
                descripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:descripcion");
                descripcion.setFilterStyle("display: none; visibility: hidden;");
                organizador = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:organizador");
                organizador.setFilterStyle("display: none; visibility: hidden;");
                objetivo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosEvento:objetivo");
                objetivo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosEvento");
                bandera = 0;
                filtrarEventos = null;
                tipoLista = 0;
            }
            duplicarEvento = new Eventos();
            RequestContext.getCurrentInstance().execute("duplicarRegistroEvento.hide()");

        } else {
            contador = 0;
            context.update("form:validacionDuplicarVigencia");
            context.execute("validacionDuplicarVigencia.show()");
        }
    }

    public void limpiarDuplicarEventos() {
        duplicarEvento = new Eventos();
    }

    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEventoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "EVENTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosEventoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "EVENTOS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listEventos.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int resultado = administrarRastros.obtenerTabla(secRegistro, "EVENTOS"); //En ENCARGATURAS lo cambia por el nombre de su tabla
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
            if (administrarRastros.verificarHistoricosTabla("EVENTOS")) { // igual acá
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    public List<Eventos> getListEventos() {
        if (listEventos == null) {
            listEventos = administrarEventos.consultarEventos();
        }
        return listEventos;
    }

    public void setListEventos(List<Eventos> listEventos) {
        this.listEventos = listEventos;
    }

    public List<Eventos> getFiltrarEventos() {
        return filtrarEventos;
    }

    public void setFiltrarEventos(List<Eventos> filtrarEventos) {
        this.filtrarEventos = filtrarEventos;
    }

    public Eventos getNuevoEvento() {
        return nuevoEvento;
    }

    public void setNuevoEvento(Eventos nuevoEvento) {
        this.nuevoEvento = nuevoEvento;
    }

    public Eventos getDuplicarEvento() {
        return duplicarEvento;
    }

    public void setDuplicarEvento(Eventos duplicarEvento) {
        this.duplicarEvento = duplicarEvento;
    }

    public Eventos getEditarEvento() {
        return editarEvento;
    }

    public void setEditarEvento(Eventos editarEvento) {
        this.editarEvento = editarEvento;
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

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

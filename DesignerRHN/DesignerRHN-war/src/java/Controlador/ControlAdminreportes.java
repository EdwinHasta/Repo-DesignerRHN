/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Inforeportes;
import Entidades.Modulos;
import Exportar.ExportarPDF;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarInforeportesInterface;
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
public class ControlAdminreportes implements Serializable {

    @EJB
    AdministrarInforeportesInterface administrarInforeportes;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //LISTA INFOREPORTES
    private List<Inforeportes> listaInforeportes;
    private List<Inforeportes> filtradosListaInforeportes;
    //L.O.V INFOREPORTES
    private List<Inforeportes> lovlistaInforeportes;
    private List<Inforeportes> lovfiltradoslistaInforeportes;
    private Inforeportes inforeportesSeleccionado;
    //editar celda
    private Inforeportes editarInforeportes;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //Crear Novedades
    private List<Inforeportes> listaInforeportesCrear;
    public Inforeportes nuevoInforeporte;
    public Inforeportes duplicarInforeporte;
    private int k;
    private BigInteger l;
    //Modificar Novedades
    private List<Inforeportes> listaInforeportesModificar;
    //Borrar Novedades
    private List<Inforeportes> listaInforeportesBorrar;
    //L.O.V MODULOS
    private List<Modulos> lovListaModulos;
    private List<Modulos> lovFiltradoslistaModulos;
    private Modulos moduloSeleccionado;
    //AUTOCOMPLETAR
    private String Modulo;
    //Columnas Tabla Ciudades
    private Column inforeportesCodigos, inforeportesNombres, inforeportesContadores, inforeportesNombresReportes, inforeportesTipos, inforeportesModulos;
    //ALTO SCROLL TABLA
    private String altoTabla;
    private boolean cambiosPagina;

    public ControlAdminreportes() {
        cambiosPagina = true;
        nuevoInforeporte = new Inforeportes();
        nuevoInforeporte.setModulo(new Modulos());
        lovListaModulos = null;
        permitirIndex = true;
        listaInforeportes = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaInforeportesBorrar = new ArrayList<Inforeportes>();
        listaInforeportesCrear = new ArrayList<Inforeportes>();
        listaInforeportesModificar = new ArrayList<Inforeportes>();
        altoTabla = "250";
        duplicarInforeporte = new Inforeportes();
    }

    //UBICACION CELDA
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaInforeportes.get(index).getSecuencia();
                if (cualCelda == 5) {
                    Modulo = listaInforeportes.get(index).getModulo().getNombre();
                }
            } else {
                secRegistro = filtradosListaInforeportes.get(index).getSecuencia();
                if (cualCelda == 5) {
                    Modulo = filtradosListaInforeportes.get(index).getModulo().getNombre();
                }
            }
        }
    }

    //AUTOCOMPLETAR
    public void modificarInforeportes(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaInforeportesCrear.contains(listaInforeportes.get(indice))) {

                    if (listaInforeportesModificar.isEmpty()) {
                        listaInforeportesModificar.add(listaInforeportes.get(indice));
                    } else if (!listaInforeportesModificar.contains(listaInforeportes.get(indice))) {
                        listaInforeportesModificar.add(listaInforeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }

                index = -1;
                secRegistro = null;

            } else {
                if (!listaInforeportesCrear.contains(filtradosListaInforeportes.get(indice))) {

                    if (listaInforeportesCrear.isEmpty()) {
                        listaInforeportesCrear.add(filtradosListaInforeportes.get(indice));
                    } else if (!listaInforeportesCrear.contains(filtradosListaInforeportes.get(indice))) {
                        listaInforeportesCrear.add(filtradosListaInforeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                    cambiosPagina = false;
                    context.update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosInforeportes");
        } else if (confirmarCambio.equalsIgnoreCase("MODULO")) {
            if (tipoLista == 0) {
                listaInforeportes.get(indice).getModulo().setNombre(Modulo);
            } else {
                filtradosListaInforeportes.get(indice).getModulo().setNombre(Modulo);
            }

            for (int i = 0; i < lovListaModulos.size(); i++) {
                if (lovListaModulos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaInforeportes.get(indice).setModulo(lovListaModulos.get(indiceUnicoElemento));
                } else {
                    filtradosListaInforeportes.get(indice).setModulo(lovListaModulos.get(indiceUnicoElemento));
                }
                lovListaModulos.clear();
                getLovListaModulos();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:modulosDialogo");
                context.execute("modulosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaInforeportesCrear.contains(listaInforeportes.get(indice))) {
                    if (listaInforeportesModificar.isEmpty()) {
                        listaInforeportesModificar.add(listaInforeportes.get(indice));
                    } else if (!listaInforeportesModificar.contains(listaInforeportes.get(indice))) {
                        listaInforeportesModificar.add(listaInforeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaInforeportesCrear.contains(filtradosListaInforeportes.get(indice))) {

                    if (listaInforeportesModificar.isEmpty()) {
                        listaInforeportesModificar.add(filtradosListaInforeportes.get(indice));
                    } else if (!listaInforeportesModificar.contains(filtradosListaInforeportes.get(indice))) {
                        listaInforeportesModificar.add(filtradosListaInforeportes.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosInforeportes");
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void seleccionarTipo(String estadoTipo, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoTipo.equals("PDF")) {
                listaInforeportes.get(indice).setTipo("PDF");
            } else if (estadoTipo.equals("XHTML")) {
                listaInforeportes.get(indice).setTipo("HTML");
            } else if (estadoTipo.equals("XLS")) {
                listaInforeportes.get(indice).setTipo("XLS");
            } else if (estadoTipo.equals("EXCEL")) {
                listaInforeportes.get(indice).setTipo("XLSX");
            } else if (estadoTipo.equals("TXT")) {
                listaInforeportes.get(indice).setTipo("TXT");
            } else if (estadoTipo.equals("CSV")) {
                listaInforeportes.get(indice).setTipo("CSV");
            }

            if (!listaInforeportesCrear.contains(listaInforeportes.get(indice))) {
                if (listaInforeportesModificar.isEmpty()) {
                    listaInforeportesModificar.add(listaInforeportes.get(indice));
                } else if (!listaInforeportesModificar.contains(listaInforeportes.get(indice))) {
                    listaInforeportesModificar.add(listaInforeportes.get(indice));
                }
            }
        } else {
            if (estadoTipo.equals("PDF")) {
                filtradosListaInforeportes.get(indice).setTipo("PDF");
            } else if (estadoTipo.equals("XHTML")) {
                filtradosListaInforeportes.get(indice).setTipo("HTML");
            } else if (estadoTipo.equals("XLS")) {
                filtradosListaInforeportes.get(indice).setTipo("XLS");
            } else if (estadoTipo.equals("EXCEL")) {
                filtradosListaInforeportes.get(indice).setTipo("XLSX");
            } else if (estadoTipo.equals("TXT")) {
                filtradosListaInforeportes.get(indice).setTipo("TXT");
            } else if (estadoTipo.equals("CSV")) {
                filtradosListaInforeportes.get(indice).setTipo("CSV");
            }

            if (!listaInforeportesCrear.contains(filtradosListaInforeportes.get(indice))) {
                if (listaInforeportesModificar.isEmpty()) {
                    listaInforeportesModificar.add(filtradosListaInforeportes.get(indice));
                } else if (!listaInforeportesModificar.contains(filtradosListaInforeportes.get(indice))) {
                    listaInforeportesModificar.add(filtradosListaInforeportes.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosInforeportes");
    }

    public void seleccionarTipoNuevoInforeporte(String estadoTipo, int tipoNuevo) {
        if (tipoNuevo == 1) {
            if (estadoTipo.equals("PDF")) {
                nuevoInforeporte.setTipo("PDF");
            } else if (estadoTipo.equals("XHTML")) {
                nuevoInforeporte.setTipo("HTML");
            } else if (estadoTipo.equals("XLS")) {
                nuevoInforeporte.setTipo("XLS");
            } else if (estadoTipo.equals("EXCEL")) {
                nuevoInforeporte.setTipo("XLSX");
            } else if (estadoTipo.equals("TXT")) {
                nuevoInforeporte.setTipo("TXT");
            } else if (estadoTipo.equals("CSV")) {
                nuevoInforeporte.setTipo("CSV");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoTipoInforeporte");
        } else {
            if (estadoTipo.equals("PDF")) {
                duplicarInforeporte.setTipo("PDF");
            } else if (estadoTipo.equals("XHTML")) {
                duplicarInforeporte.setTipo("HTML");
            } else if (estadoTipo.equals("XLS")) {
                duplicarInforeporte.setTipo("XLS");
            } else if (estadoTipo.equals("EXCEL")) {
                duplicarInforeporte.setTipo("XLSX");
            } else if (estadoTipo.equals("TXT")) {
                duplicarInforeporte.setTipo("TXT");
            } else if (estadoTipo.equals("CSV")) {
                duplicarInforeporte.setTipo("CSV");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarTipoInforeporte");
        }

    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
            index = -1;
            secRegistro = null;
            System.out.println("Tipo Actualizacion: " + tipoActualizacion);
        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:inforeportesDialogo");
            context.execute("inforeportesDialogo.show()");
        } else if (dlg == 1) {
            context.update("formularioDialogos:modulosDialogo");
            context.execute("modulosDialogo.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaInforeportes.isEmpty()) {
            listaInforeportes.clear();
            listaInforeportes = administrarInforeportes.inforeportes();
        } else {
            listaInforeportes = administrarInforeportes.inforeportes();
        }

        context.update("form:datosInforeportes");
        filtradosListaInforeportes = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void limpiarduplicarInforeportes() {
        duplicarInforeporte = new Inforeportes();
        duplicarInforeporte.setModulo(new Modulos());
    }

    public void confirmarDuplicar() {

        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        listaInforeportes.add(duplicarInforeporte);
        listaInforeportesCrear.add(duplicarInforeporte);

        index = -1;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            altoTabla = "250";
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("display: none; visibility: hidden;");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("display: none; visibility: hidden;");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 0;
            filtradosListaInforeportes = null;
            tipoLista = 0;
        }
        context.update("form:datosInforeportes");
        duplicarInforeporte = new Inforeportes();
        context.update("formularioDialogos:DuplicarInforeporte");
        context.execute("DuplicarInforeporte.hide()");
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarInforeportes() {
        RequestContext context = RequestContext.getCurrentInstance();
        Inforeportes i = inforeportesSeleccionado;

        if (!listaInforeportes.isEmpty()) {
            listaInforeportes.clear();
            listaInforeportes.add(i);

        } else {
            listaInforeportes.add(i);
        }
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        context.execute("inforeportesDialogo.hide()");
        context.reset("formularioDialogos:LOVInforeportes:globalFilter");
        context.update("formularioDialogos:LOVInforeportes");
        context.update("form:datosInforeportes");
        filtradosListaInforeportes = null;
        inforeportesSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void actualizarModulos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaInforeportes.get(index).setModulo(moduloSeleccionado);
                if (!listaInforeportesCrear.contains(listaInforeportes.get(index))) {
                    if (listaInforeportesModificar.isEmpty()) {
                        listaInforeportesModificar.add(listaInforeportes.get(index));
                    } else if (!listaInforeportesModificar.contains(listaInforeportes.get(index))) {
                        listaInforeportesModificar.add(listaInforeportes.get(index));
                    }
                }
            } else {
                filtradosListaInforeportes.get(index).setModulo(moduloSeleccionado);
                if (!listaInforeportesCrear.contains(filtradosListaInforeportes.get(index))) {
                    if (listaInforeportesModificar.isEmpty()) {
                        listaInforeportesModificar.add(filtradosListaInforeportes.get(index));
                    } else if (!listaInforeportesModificar.contains(filtradosListaInforeportes.get(index))) {
                        listaInforeportesModificar.add(filtradosListaInforeportes.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            permitirIndex = true;
            context.update("form:datosInforeportes");
        } else if (tipoActualizacion == 1) {
            nuevoInforeporte.setModulo(moduloSeleccionado);
            context.update("formularioDialogos:nuevoModuloInforeporte");
        } else if (tipoActualizacion == 2) {
            duplicarInforeporte.setModulo(moduloSeleccionado);
            context.update("formularioDialogos:duplicarModuloInforeporte");

        }
        filtradosListaInforeportes = null;
        moduloSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("modulosDialogo.hide()");
        context.reset("formularioDialogos:LOVModulos:globalFilter");
        context.update("formularioDialogos:LOVModulos");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("MODULO")) {
            if (tipoNuevo == 1) {
                Modulo = nuevoInforeporte.getModulo().getNombre();
            } else if (tipoNuevo == 2) {
                Modulo = duplicarInforeporte.getModulo().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("MODULO")) {
            if (tipoNuevo == 1) {
                nuevoInforeporte.getModulo().setNombre(Modulo);
            } else if (tipoNuevo == 2) {
                duplicarInforeporte.getModulo().setNombre(Modulo);
            }
            for (int i = 0; i < lovListaModulos.size(); i++) {
                if (lovListaModulos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoInforeporte.setModulo(lovListaModulos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoModuloInforeporte");
                } else if (tipoNuevo == 2) {
                    duplicarInforeporte.setModulo(lovListaModulos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarModuloInforeporte");
                }
                lovListaModulos.clear();
                getLovListaModulos();
            } else {
                context.update("form:modulosDialogo");
                context.execute("modulosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoModuloInforeporte");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarModuloInforeporte");
                }
            }
        }
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 5) {
                context.update("form:modulosDialogo");
                context.execute("modulosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0) {
            altoTabla = "228";
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("width: 60px");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("width: 60px");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("width: 60px");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            altoTabla = "250";
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("display: none; visibility: hidden;");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("display: none; visibility: hidden;");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 0;
            filtradosListaInforeportes = null;
            tipoLista = 0;
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            altoTabla = "250";
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("display: none; visibility: hidden;");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("display: none; visibility: hidden;");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 0;
            filtradosListaInforeportes = null;
            tipoLista = 0;

        }

        listaInforeportesBorrar.clear();
        listaInforeportesCrear.clear();
        listaInforeportesModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaInforeportes = null;
        guardado = true;
        permitirIndex = true;
        cambiosPagina = false;

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosInforeportes");
    }

    public void cancelarCambioInforeportes() {
        lovfiltradoslistaInforeportes = null;
        inforeportesSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioModulos() {
        lovFiltradoslistaModulos = null;
        moduloSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarInforeportes = listaInforeportes.get(index);
            }
            if (tipoLista == 1) {
                editarInforeportes = filtradosListaInforeportes.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigos");
                context.execute("editarCodigos.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarReportes");
                context.execute("editarReportes.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarContadores");
                context.execute("editarContadores.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarNombresReportes");
                context.execute("editarNombresReportes.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarModulos");
                context.execute("editarModulos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosInforeportesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "AdminreportesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosInforeportesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "AdminreportesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //LIMPIAR NUEVO REGISTRO CIUDAD

    public void limpiarNuevoInforeporte() {
        nuevoInforeporte = new Inforeportes();
        nuevoInforeporte.setModulo(new Modulos());
        nuevoInforeporte.getModulo().setNombre(" ");
        index = -1;
        secRegistro = null;
    }

    //CREAR NOVEDADES
    public void agregarNuevoInforeporte() {

        RequestContext context = RequestContext.getCurrentInstance();

        if (bandera == 1) {
            altoTabla = "250";
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("display: none; visibility: hidden;");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("display: none; visibility: hidden;");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 0;
            filtradosListaInforeportes = null;
            tipoLista = 0;

        }
        //AGREGAR REGISTRO A LA LISTA NOVEDADES .
        k++;
        l = BigInteger.valueOf(k);
        nuevoInforeporte.setSecuencia(l);
        if (nuevoInforeporte.isEstadoFechaDesde() == true) {
            nuevoInforeporte.setFecdesde("SI");

        } else if (nuevoInforeporte.isEstadoFechaDesde() == false) {
            nuevoInforeporte.setFecdesde("NO");
        }

        if (nuevoInforeporte.isEstadoFechaHasta() == true) {
            nuevoInforeporte.setFechasta("SI");
        } else if (nuevoInforeporte.isEstadoFechaHasta() == false) {
            nuevoInforeporte.setFechasta("NO");
        }

        if (nuevoInforeporte.isEstadoEmpleadoDesde() == true) {
            nuevoInforeporte.setEmdesde("SI");
        } else if (nuevoInforeporte.isEstadoEmpleadoDesde() == false) {
            nuevoInforeporte.setEmdesde("NO");
        }

        if (nuevoInforeporte.isEstadoEmpleadoHasta() == true) {
            nuevoInforeporte.setEmhasta("SI");
        } else if (nuevoInforeporte.isEstadoEmpleadoHasta() == false) {
            nuevoInforeporte.setEmhasta("NO");
        }

        if (nuevoInforeporte.isEstadoLocalizacion() == true) {
            nuevoInforeporte.setLocalizacion("SI");
        } else if (nuevoInforeporte.isEstadoLocalizacion() == false) {
            nuevoInforeporte.setLocalizacion("NO");
        }

        if (nuevoInforeporte.isEstadoEstado() == true) {

            nuevoInforeporte.setEstado("SI");
        } else if (nuevoInforeporte.isEstadoEstado() == false) {
            nuevoInforeporte.setEstado("NO");
        }

        if (nuevoInforeporte.isEstadoGrupo() == true) {

            nuevoInforeporte.setGrupo("SI");
        } else if (nuevoInforeporte.isEstadoGrupo() == false) {
            nuevoInforeporte.setGrupo("NO");
        }

        if (nuevoInforeporte.isEstadoTercero() == true) {

            nuevoInforeporte.setTercero("SI");
        } else if (nuevoInforeporte.isEstadoTercero() == false) {
            nuevoInforeporte.setTercero("NO");
        }

        if (nuevoInforeporte.isEstadoTrabajador() == true) {

            nuevoInforeporte.setTrabajador("SI");
        } else if (nuevoInforeporte.isEstadoTrabajador() == false) {
            nuevoInforeporte.setTrabajador("NO");
        }

        if (nuevoInforeporte.isEstadoTipoTrabajador() == true) {

            nuevoInforeporte.setTipotrabajador("SI");
        } else if (nuevoInforeporte.isEstadoTipoTrabajador() == false) {
            nuevoInforeporte.setTipotrabajador("NO");
        }

        if (nuevoInforeporte.isEstadoSolicitud() == true) {

            nuevoInforeporte.setSolicitud("SI");
        } else if (nuevoInforeporte.isEstadoSolicitud() == false) {
            nuevoInforeporte.setSolicitud("NO");
        }

        if (nuevoInforeporte.isEstadoCiudad() == true) {

            nuevoInforeporte.setCiudad("SI");
        } else if (nuevoInforeporte.isEstadoCiudad() == false) {
            nuevoInforeporte.setCiudad("NO");
        }

        if (nuevoInforeporte.isEstadoTipoTelefono() == true) {

            nuevoInforeporte.setTipotelefono("SI");
        } else if (nuevoInforeporte.isEstadoTipoTelefono() == false) {
            nuevoInforeporte.setTipotelefono("NO");
        }

        if (nuevoInforeporte.isEstadoEstadoCivil() == true) {

            nuevoInforeporte.setEstadocivil("SI");
        } else if (nuevoInforeporte.isEstadoEstadoCivil() == false) {
            nuevoInforeporte.setEstadocivil("NO");
        }

        if (nuevoInforeporte.isEstadoDeporte() == true) {

            nuevoInforeporte.setDeporte("SI");
        } else if (nuevoInforeporte.isEstadoDeporte() == false) {
            nuevoInforeporte.setDeporte("NO");
        }

        if (nuevoInforeporte.isEstadoIdioma() == true) {

            nuevoInforeporte.setIdioma("SI");
        } else if (nuevoInforeporte.isEstadoIdioma() == false) {
            nuevoInforeporte.setIdioma("NO");
        }

        if (nuevoInforeporte.isEstadoAficion() == true) {

            nuevoInforeporte.setAficion("SI");
        } else if (nuevoInforeporte.isEstadoAficion() == false) {
            nuevoInforeporte.setAficion("NO");
        }

        if (nuevoInforeporte.isEstadoJefeDivision() == true) {

            nuevoInforeporte.setJefedivision("SI");
        } else if (nuevoInforeporte.isEstadoJefeDivision() == false) {
            nuevoInforeporte.setJefedivision("NO");
        }

        if (nuevoInforeporte.isEstadoRodamiento() == true) {

            nuevoInforeporte.setRodamiento("SI");
        } else if (nuevoInforeporte.isEstadoRodamiento() == false) {
            nuevoInforeporte.setRodamiento("NO");
        }

        if (nuevoInforeporte.isEstadoEnvioMasivo() == true) {

            nuevoInforeporte.setEnviomasivo("S");
        } else if (nuevoInforeporte.isEstadoEnvioMasivo() == false) {
            nuevoInforeporte.setEnviomasivo("N");
        }

        System.out.println("Fecha Desde: " + nuevoInforeporte.getFecdesde());
        System.out.println("Fecha Hasta: " + nuevoInforeporte.getFechasta());
        System.out.println("Empleado Desde: " + nuevoInforeporte.getEmdesde());
        System.out.println("Empleado Hasta: " + nuevoInforeporte.getEmhasta());
        System.out.println("Localización: " + nuevoInforeporte.getLocalizacion());
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        listaInforeportesCrear.add(nuevoInforeporte);
        listaInforeportes.add(nuevoInforeporte);
        nuevoInforeporte = new Inforeportes();
        context.update("form:datosInforeportes");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        context.execute("NuevoInforeporte.hide()");
        index = -1;
        secRegistro = null;

    }

    //BORRAR CIUDADES
    public void borrarInforeportes() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaInforeportesModificar.isEmpty() && listaInforeportesModificar.contains(listaInforeportes.get(index))) {
                    int modIndex = listaInforeportesModificar.indexOf(listaInforeportes.get(index));
                    listaInforeportesModificar.remove(modIndex);
                    listaInforeportesBorrar.add(listaInforeportes.get(index));
                } else if (!listaInforeportesCrear.isEmpty() && listaInforeportesCrear.contains(listaInforeportes.get(index))) {
                    int crearIndex = listaInforeportesCrear.indexOf(listaInforeportes.get(index));
                    listaInforeportesCrear.remove(crearIndex);
                } else {
                    listaInforeportesBorrar.add(listaInforeportes.get(index));
                }
                listaInforeportes.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaInforeportesModificar.isEmpty() && listaInforeportesModificar.contains(filtradosListaInforeportes.get(index))) {
                    int modIndex = listaInforeportesModificar.indexOf(filtradosListaInforeportes.get(index));
                    listaInforeportesModificar.remove(modIndex);
                    listaInforeportesBorrar.add(filtradosListaInforeportes.get(index));
                } else if (!listaInforeportesCrear.isEmpty() && listaInforeportesCrear.contains(filtradosListaInforeportes.get(index))) {
                    int crearIndex = listaInforeportesCrear.indexOf(filtradosListaInforeportes.get(index));
                    listaInforeportesCrear.remove(crearIndex);
                } else {
                    listaInforeportesBorrar.add(filtradosListaInforeportes.get(index));
                }
                int CIndex = listaInforeportes.indexOf(filtradosListaInforeportes.get(index));
                listaInforeportes.remove(CIndex);
                filtradosListaInforeportes.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosInforeportes");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //GUARDAR
    public void guardarCambiosInforeportes() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Novedades");

            if (!listaInforeportesBorrar.isEmpty()) {
                for (int i = 0; i < listaInforeportesBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaInforeportesBorrar.size());

                    if (listaInforeportesBorrar.get(i).getAficion() == null) {
                        listaInforeportesBorrar.get(i).setAficion(null);
                    }
                    if (listaInforeportesBorrar.get(i).getCiudad() == null) {
                        listaInforeportesBorrar.get(i).setCiudad(null);
                    }
                    if (listaInforeportesBorrar.get(i).getCodigo() == null) {
                        listaInforeportesBorrar.get(i).setCodigo(null);
                    }
                    if (listaInforeportesBorrar.get(i).getContador() == null) {
                        listaInforeportesBorrar.get(i).setContador(null);
                    }
                    if (listaInforeportesBorrar.get(i).getDeporte() == null) {
                        listaInforeportesBorrar.get(i).setDeporte(null);
                    }
                    if (listaInforeportesBorrar.get(i).getEmdesde() == null) {
                        listaInforeportesBorrar.get(i).setEmdesde(null);
                    }
                    if (listaInforeportesBorrar.get(i).getEmhasta() == null) {
                        listaInforeportesBorrar.get(i).setEmhasta(null);
                    }
                    if (listaInforeportesBorrar.get(i).getEnviomasivo() == null) {
                        listaInforeportesBorrar.get(i).setEnviomasivo(null);
                    }
                    if (listaInforeportesBorrar.get(i).getEstado() == null) {
                        listaInforeportesBorrar.get(i).setEstado(null);
                    }
                    if (listaInforeportesBorrar.get(i).getEstadocivil() == null) {
                        listaInforeportesBorrar.get(i).setEstadocivil(null);
                    }
                    if (listaInforeportesBorrar.get(i).getFecdesde() == null) {
                        listaInforeportesBorrar.get(i).setFecdesde(null);
                    }
                    if (listaInforeportesBorrar.get(i).getFechasta() == null) {
                        listaInforeportesBorrar.get(i).setFechasta(null);
                    }
                    if (listaInforeportesBorrar.get(i).getGrupo() == null) {
                        listaInforeportesBorrar.get(i).setGrupo(null);
                    }
                    if (listaInforeportesBorrar.get(i).getIdioma() == null) {
                        listaInforeportesBorrar.get(i).setIdioma(null);
                    }
                    if (listaInforeportesBorrar.get(i).getJefedivision() == null) {
                        listaInforeportesBorrar.get(i).setJefedivision(null);
                    }
                    if (listaInforeportesBorrar.get(i).getLocalizacion() == null) {
                        listaInforeportesBorrar.get(i).setLocalizacion(null);
                    }
                    if (listaInforeportesBorrar.get(i).getModulo().getSecuencia() == null) {
                        listaInforeportesBorrar.get(i).setModulo(null);
                    }
                    if (listaInforeportesBorrar.get(i).getNombre() == null) {
                        listaInforeportesBorrar.get(i).setNombre(null);
                    }
                    if (listaInforeportesBorrar.get(i).getNombrereporte() == null) {
                        listaInforeportesBorrar.get(i).setNombrereporte(null);
                    }
                    if (listaInforeportesBorrar.get(i).getRodamiento() == null) {
                        listaInforeportesBorrar.get(i).setRodamiento(null);
                    }
                    if (listaInforeportesBorrar.get(i).getSolicitud() == null) {
                        listaInforeportesBorrar.get(i).setSolicitud(null);
                    }
                    if (listaInforeportesBorrar.get(i).getTercero() == null) {
                        listaInforeportesBorrar.get(i).setTercero(null);
                    }
                    if (listaInforeportesBorrar.get(i).getTipo() == null) {
                        listaInforeportesBorrar.get(i).setTipo(null);
                    }
                    if (listaInforeportesBorrar.get(i).getTipotelefono() == null) {
                        listaInforeportesBorrar.get(i).setTipotelefono(null);
                    }
                    if (listaInforeportesBorrar.get(i).getTipotrabajador() == null) {
                        listaInforeportesBorrar.get(i).setTipotrabajador(null);
                    }
                    if (listaInforeportesBorrar.get(i).getTrabajador() == null) {
                        listaInforeportesBorrar.get(i).setTrabajador(null);
                    }
                    administrarInforeportes.borrarInforeporte(listaInforeportesBorrar.get(i));
                }
                System.out.println("Entra");
                listaInforeportesBorrar.clear();
            }

            if (!listaInforeportesCrear.isEmpty()) {
                for (int i = 0; i < listaInforeportesCrear.size(); i++) {
                    System.out.println("Creando...");

                    if (listaInforeportesCrear.get(i).getAficion() == null) {
                        listaInforeportesCrear.get(i).setAficion(null);
                    }
                    if (listaInforeportesCrear.get(i).getCiudad() == null) {
                        listaInforeportesCrear.get(i).setCiudad(null);
                    }
                    if (listaInforeportesCrear.get(i).getCodigo() == null) {
                        listaInforeportesCrear.get(i).setCodigo(null);
                    }
                    if (listaInforeportesCrear.get(i).getContador() == null) {
                        listaInforeportesCrear.get(i).setContador(null);
                    }
                    if (listaInforeportesCrear.get(i).getDeporte() == null) {
                        listaInforeportesCrear.get(i).setDeporte(null);
                    }
                    if (listaInforeportesCrear.get(i).getEmdesde() == null) {
                        listaInforeportesCrear.get(i).setEmdesde(null);
                    }
                    if (listaInforeportesCrear.get(i).getEmhasta() == null) {
                        listaInforeportesCrear.get(i).setEmhasta(null);
                    }
                    if (listaInforeportesCrear.get(i).getEnviomasivo() == null) {
                        listaInforeportesCrear.get(i).setEnviomasivo(null);
                    }
                    if (listaInforeportesCrear.get(i).getEstado() == null) {
                        listaInforeportesCrear.get(i).setEstado(null);
                    }
                    if (listaInforeportesCrear.get(i).getEstadocivil() == null) {
                        listaInforeportesCrear.get(i).setEstadocivil(null);
                    }
                    if (listaInforeportesCrear.get(i).getFecdesde() == null) {
                        listaInforeportesCrear.get(i).setFecdesde(null);
                    }
                    if (listaInforeportesCrear.get(i).getFechasta() == null) {
                        listaInforeportesCrear.get(i).setFechasta(null);
                    }
                    if (listaInforeportesCrear.get(i).getGrupo() == null) {
                        listaInforeportesCrear.get(i).setGrupo(null);
                    }
                    if (listaInforeportesCrear.get(i).getIdioma() == null) {
                        listaInforeportesCrear.get(i).setIdioma(null);
                    }
                    if (listaInforeportesCrear.get(i).getJefedivision() == null) {
                        listaInforeportesCrear.get(i).setJefedivision(null);
                    }
                    if (listaInforeportesCrear.get(i).getLocalizacion() == null) {
                        listaInforeportesCrear.get(i).setLocalizacion(null);
                    }
                    if (listaInforeportesCrear.get(i).getModulo().getSecuencia() == null) {
                        listaInforeportesCrear.get(i).setModulo(null);
                    }
                    if (listaInforeportesCrear.get(i).getNombre() == null) {
                        listaInforeportesCrear.get(i).setNombre(null);
                    }
                    if (listaInforeportesCrear.get(i).getNombrereporte() == null) {
                        listaInforeportesCrear.get(i).setNombrereporte(null);
                    }
                    if (listaInforeportesCrear.get(i).getRodamiento() == null) {
                        listaInforeportesCrear.get(i).setRodamiento(null);
                    }
                    if (listaInforeportesCrear.get(i).getSolicitud() == null) {
                        listaInforeportesCrear.get(i).setSolicitud(null);
                    }
                    if (listaInforeportesCrear.get(i).getTercero() == null) {
                        listaInforeportesCrear.get(i).setTercero(null);
                    }
                    if (listaInforeportesCrear.get(i).getTipo() == null) {
                        listaInforeportesCrear.get(i).setTipo(null);
                    }
                    if (listaInforeportesCrear.get(i).getTipotelefono() == null) {
                        listaInforeportesCrear.get(i).setTipotelefono(null);
                    }
                    if (listaInforeportesCrear.get(i).getTipotrabajador() == null) {
                        listaInforeportesCrear.get(i).setTipotrabajador(null);
                    }
                    if (listaInforeportesCrear.get(i).getTrabajador() == null) {
                        listaInforeportesCrear.get(i).setTrabajador(null);
                    }

                    administrarInforeportes.crearInforeporte(listaInforeportesCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaInforeportesCrear.clear();
            }
            if (!listaInforeportesModificar.isEmpty()) {
                administrarInforeportes.modificarInforeporte(listaInforeportesModificar);
                listaInforeportesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaInforeportes = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = true;
            context.update("form:ACEPTAR");
            context.update("form:datosInforeportes");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        index = -1;
        secRegistro = null;
    }

    //DUPLICAR CIUDAD
    public void duplicarI() {
        if (index >= 0) {
            duplicarInforeporte = new Inforeportes();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarInforeporte.setSecuencia(l);
                duplicarInforeporte.setCodigo(listaInforeportes.get(index).getCodigo());
                duplicarInforeporte.setNombre(listaInforeportes.get(index).getNombre());
                duplicarInforeporte.setContador(listaInforeportes.get(index).getContador());
                duplicarInforeporte.setNombrereporte(listaInforeportes.get(index).getNombrereporte());
                duplicarInforeporte.setTipo(listaInforeportes.get(index).getTipo());
                duplicarInforeporte.setModulo(listaInforeportes.get(index).getModulo());
                duplicarInforeporte.setFecdesde(listaInforeportes.get(index).getFecdesde());
                duplicarInforeporte.setFechasta(listaInforeportes.get(index).getFechasta());
                duplicarInforeporte.setEmdesde(listaInforeportes.get(index).getEmdesde());
                duplicarInforeporte.setEmhasta(listaInforeportes.get(index).getEmhasta());
                duplicarInforeporte.setLocalizacion(listaInforeportes.get(index).getLocalizacion());
                duplicarInforeporte.setEstado(listaInforeportes.get(index).getEstado());
                duplicarInforeporte.setGrupo(listaInforeportes.get(index).getGrupo());
                duplicarInforeporte.setTercero(listaInforeportes.get(index).getTercero());
                duplicarInforeporte.setTrabajador(listaInforeportes.get(index).getTrabajador());
                duplicarInforeporte.setTipotrabajador(listaInforeportes.get(index).getTipotrabajador());
                duplicarInforeporte.setSolicitud(listaInforeportes.get(index).getSolicitud());
                duplicarInforeporte.setCiudad(listaInforeportes.get(index).getCiudad());
                duplicarInforeporte.setTipotelefono(listaInforeportes.get(index).getTipotelefono());
                duplicarInforeporte.setEstadocivil(listaInforeportes.get(index).getEstadocivil());
                duplicarInforeporte.setDeporte(listaInforeportes.get(index).getDeporte());
                duplicarInforeporte.setIdioma(listaInforeportes.get(index).getIdioma());
                duplicarInforeporte.setAficion(listaInforeportes.get(index).getAficion());
                duplicarInforeporte.setJefedivision(listaInforeportes.get(index).getJefedivision());
                duplicarInforeporte.setRodamiento(listaInforeportes.get(index).getRodamiento());
                duplicarInforeporte.setEnviomasivo(listaInforeportes.get(index).getEnviomasivo());

            }
            if (tipoLista == 1) {
                duplicarInforeporte.setSecuencia(l);
                duplicarInforeporte.setCodigo(filtradosListaInforeportes.get(index).getCodigo());
                duplicarInforeporte.setNombre(filtradosListaInforeportes.get(index).getNombre());
                duplicarInforeporte.setContador(filtradosListaInforeportes.get(index).getContador());
                duplicarInforeporte.setNombrereporte(filtradosListaInforeportes.get(index).getNombrereporte());
                duplicarInforeporte.setTipo(filtradosListaInforeportes.get(index).getTipo());
                duplicarInforeporte.setModulo(filtradosListaInforeportes.get(index).getModulo());
                duplicarInforeporte.setFecdesde(filtradosListaInforeportes.get(index).getFecdesde());
                duplicarInforeporte.setFechasta(filtradosListaInforeportes.get(index).getFechasta());
                duplicarInforeporte.setEmdesde(filtradosListaInforeportes.get(index).getEmdesde());
                duplicarInforeporte.setEmhasta(filtradosListaInforeportes.get(index).getEmhasta());
                duplicarInforeporte.setLocalizacion(filtradosListaInforeportes.get(index).getLocalizacion());
                duplicarInforeporte.setEstado(filtradosListaInforeportes.get(index).getEstado());
                duplicarInforeporte.setGrupo(filtradosListaInforeportes.get(index).getGrupo());
                duplicarInforeporte.setTercero(filtradosListaInforeportes.get(index).getTercero());
                duplicarInforeporte.setTrabajador(filtradosListaInforeportes.get(index).getTrabajador());
                duplicarInforeporte.setTipotrabajador(filtradosListaInforeportes.get(index).getTipotrabajador());
                duplicarInforeporte.setSolicitud(filtradosListaInforeportes.get(index).getSolicitud());
                duplicarInforeporte.setCiudad(filtradosListaInforeportes.get(index).getCiudad());
                duplicarInforeporte.setTipotelefono(filtradosListaInforeportes.get(index).getTipotelefono());
                duplicarInforeporte.setEstadocivil(filtradosListaInforeportes.get(index).getEstadocivil());
                duplicarInforeporte.setDeporte(filtradosListaInforeportes.get(index).getDeporte());
                duplicarInforeporte.setIdioma(filtradosListaInforeportes.get(index).getIdioma());
                duplicarInforeporte.setAficion(filtradosListaInforeportes.get(index).getAficion());
                duplicarInforeporte.setJefedivision(filtradosListaInforeportes.get(index).getJefedivision());
                duplicarInforeporte.setRodamiento(filtradosListaInforeportes.get(index).getRodamiento());
                duplicarInforeporte.setEnviomasivo(filtradosListaInforeportes.get(index).getEnviomasivo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarInforeporte");
            context.execute("DuplicarInforeporte.show()");
            index = -1;
            secRegistro = null;
        }
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("lol");
        if (!listaInforeportes.isEmpty()) {
            if (secRegistro != null) {
                System.out.println("lol 2");
                int result = administrarRastros.obtenerTabla(secRegistro, "INFOREPORTES");
                System.out.println("resultado: " + result);
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
            if (administrarRastros.verificarHistoricosTabla("INFOREPORTES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void salir() {
        if (bandera == 1) {
            altoTabla = "250";
            inforeportesCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesCodigos");
            inforeportesCodigos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombres");
            inforeportesNombres.setFilterStyle("display: none; visibility: hidden;");
            inforeportesContadores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesContadores");
            inforeportesContadores.setFilterStyle("display: none; visibility: hidden;");
            inforeportesNombresReportes = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesNombresReportes");
            inforeportesNombresReportes.setFilterStyle("display: none; visibility: hidden;");
            inforeportesTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesTipos");
            inforeportesTipos.setFilterStyle("display: none; visibility: hidden;");
            inforeportesModulos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInforeportes:inforeportesModulos");
            inforeportesModulos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInforeportes");
            bandera = 0;
            filtradosListaInforeportes = null;
            tipoLista = 0;

        }

        listaInforeportesBorrar.clear();
        listaInforeportesCrear.clear();
        listaInforeportesModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaInforeportes = null;
        guardado = true;
        permitirIndex = true;

    }

    //Getter & Setter
    public List<Inforeportes> getListaInforeportes() {
        if (listaInforeportes == null) {
            listaInforeportes = administrarInforeportes.inforeportes();
        }
        return listaInforeportes;
    }

    public void setListaInforeportes(List<Inforeportes> listaInforeportes) {
        this.listaInforeportes = listaInforeportes;
    }

    public List<Inforeportes> getFiltradosListaInforeportes() {
        return filtradosListaInforeportes;
    }

    public void setFiltradosListaInforeportes(List<Inforeportes> filtradosListaInforeportes) {
        this.filtradosListaInforeportes = filtradosListaInforeportes;
    }

    public List<Inforeportes> getLovlistaInforeportes() {
        return lovlistaInforeportes;
    }

    public void setLovlistaInforeportes(List<Inforeportes> lovlistaInforeportes) {
        this.lovlistaInforeportes = lovlistaInforeportes;
    }

    public List<Inforeportes> getLovfiltradoslistaInforeportes() {
        return lovfiltradoslistaInforeportes;
    }

    public void setLovfiltradoslistaInforeportes(List<Inforeportes> lovfiltradoslistaInforeportes) {
        this.lovfiltradoslistaInforeportes = lovfiltradoslistaInforeportes;
    }

    public Inforeportes getInforeportesSeleccionado() {
        return inforeportesSeleccionado;
    }

    public void setInforeportesSeleccionado(Inforeportes inforeportesSeleccionado) {
        this.inforeportesSeleccionado = inforeportesSeleccionado;
    }

    public List<Modulos> getLovListaModulos() {
        if (lovListaModulos == null) {
            lovListaModulos = administrarInforeportes.lovmodulos();
        }
        return lovListaModulos;
    }

    public void setLovListaModulos(List<Modulos> lovListaModulos) {
        this.lovListaModulos = lovListaModulos;
    }

    public List<Modulos> getLovFiltradoslistaModulos() {
        return lovFiltradoslistaModulos;
    }

    public void setLovFiltradoslistaModulos(List<Modulos> lovFiltradoslistaModulos) {
        this.lovFiltradoslistaModulos = lovFiltradoslistaModulos;
    }

    public Modulos getModuloSeleccionado() {
        return moduloSeleccionado;
    }

    public void setModuloSeleccionado(Modulos moduloSeleccionado) {
        this.moduloSeleccionado = moduloSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Inforeportes getEditarInforeportes() {
        return editarInforeportes;
    }

    public void setEditarInforeportes(Inforeportes editarInforeportes) {
        this.editarInforeportes = editarInforeportes;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public Inforeportes getNuevoInforeporte() {
        return nuevoInforeporte;
    }

    public void setNuevoInforeporte(Inforeportes nuevoInforeporte) {
        this.nuevoInforeporte = nuevoInforeporte;
    }

    public Inforeportes getDuplicarInforeporte() {
        return duplicarInforeporte;
    }

    public void setDuplicarInforeporte(Inforeportes duplicarInforeporte) {
        this.duplicarInforeporte = duplicarInforeporte;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

}

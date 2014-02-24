/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Operandos;
import Entidades.Modulos;
import Exportar.ExportarPDFTablasAnchas;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarOperandosInterface;
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
public class ControlOperando implements Serializable {

    @EJB
    AdministrarOperandosInterface administrarOperandos;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //LISTA INFOREPORTES
    private List<Operandos> listaOperandos;
    private List<Operandos> filtradosListaOperandos;
    //L.O.V INFOREPORTES
    private List<Operandos> lovlistaOperandos;
    private List<Operandos> lovfiltradoslistaOperandos;
    private Operandos operandosSeleccionado;
    //editar celda
    private Operandos editarOperandos;
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
    private List<Operandos> listaOperandosCrear;
    public Operandos nuevoOperando;
    public Operandos duplicarOperando;
    private int k;
    private BigInteger l;
    private String mensajeValidacion;
    //Modificar Novedades
    private List<Operandos> listaOperandosModificar;
    //Borrar Novedades
    private List<Operandos> listaOperandosBorrar;
    //L.O.V MODULOS
    private List<Modulos> lovListaModulos;
    private List<Modulos> lovFiltradoslistaModulos;
    private Modulos moduloSeleccionado;
    //AUTOCOMPLETAR
    private String Modulo;
    //Columnas Tabla Ciudades
    private Column operandosNombres, operandosTipos, operandosValores, operandosDescripciones, operandosCodigos;
    //ALTO SCROLL TABLA
    private String altoTabla;
    private boolean cambiosPagina;
    //Cambios de PAgina
    public String action;
    public BigInteger secuenciaOperando;
    public String tipoOperando;
    public Operandos operandoSeleccionado;

    public ControlOperando() {
        cambiosPagina = true;
        nuevoOperando = new Operandos();
        nuevoOperando.setCodigo(0);
        nuevoOperando.setDescripcion(" ");
        nuevoOperando.setNombre(" ");
        nuevoOperando.setTipo("CONSTANTE");
        lovListaModulos = null;
        permitirIndex = true;
        listaOperandos = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        listaOperandosBorrar = new ArrayList<Operandos>();
        listaOperandosCrear = new ArrayList<Operandos>();
        listaOperandosModificar = new ArrayList<Operandos>();
        altoTabla = "245";
        duplicarOperando = new Operandos();
    }

    //UBICACION CELDA
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                secRegistro = listaOperandos.get(index).getSecuencia();
            } else {
                secRegistro = filtradosListaOperandos.get(index).getSecuencia();
            }
        }
    }

    //AUTOCOMPLETAR
    public void modificarOperandos(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaOperandosCrear.contains(listaOperandos.get(indice))) {

                    if (listaOperandosModificar.isEmpty()) {
                        listaOperandosModificar.add(listaOperandos.get(indice));
                    } else if (!listaOperandosModificar.contains(listaOperandos.get(indice))) {
                        listaOperandosModificar.add(listaOperandos.get(indice));
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
                if (!listaOperandosCrear.contains(filtradosListaOperandos.get(indice))) {

                    if (listaOperandosCrear.isEmpty()) {
                        listaOperandosCrear.add(filtradosListaOperandos.get(indice));
                    } else if (!listaOperandosCrear.contains(filtradosListaOperandos.get(indice))) {
                        listaOperandosCrear.add(filtradosListaOperandos.get(indice));
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
            context.update("form:datosOperandos");
        }
    }

    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void seleccionarTipo(String estadoTipo, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoTipo.equals("FORMULA")) {
                listaOperandos.get(indice).setTipo("FORMULA");
            } else if (estadoTipo.equals("BLOQUE PL/SQL")) {
                listaOperandos.get(indice).setTipo("BLOQUE PL/SQL");
            } else if (estadoTipo.equals("CONSTANTE")) {
                listaOperandos.get(indice).setTipo("CONSTANTE");
            } else if (estadoTipo.equals("FUNCION")) {
                listaOperandos.get(indice).setTipo("FUNCION");
            } else if (estadoTipo.equals("RELACIONAL")) {
                listaOperandos.get(indice).setTipo("RELACIONAL");
            }

            if (!listaOperandosCrear.contains(listaOperandos.get(indice))) {
                if (listaOperandosModificar.isEmpty()) {
                    listaOperandosModificar.add(listaOperandos.get(indice));
                } else if (!listaOperandosModificar.contains(listaOperandos.get(indice))) {
                    listaOperandosModificar.add(listaOperandos.get(indice));
                }
            }
        } else {
            if (estadoTipo.equals("FORMULA")) {
                filtradosListaOperandos.get(indice).setTipo("FORMULA");
            } else if (estadoTipo.equals("BLOQUE PL/SQL")) {
                filtradosListaOperandos.get(indice).setTipo("BLOQUE PL/SQL");
            } else if (estadoTipo.equals("CONSTANTE")) {
                filtradosListaOperandos.get(indice).setTipo("CONSTANTE");
            } else if (estadoTipo.equals("FUNCION")) {
                filtradosListaOperandos.get(indice).setTipo("FUNCION");
            } else if (estadoTipo.equals("RELACIONAL")) {
                filtradosListaOperandos.get(indice).setTipo("RELACIONAL");
            }
            if (!listaOperandosCrear.contains(filtradosListaOperandos.get(indice))) {
                if (listaOperandosModificar.isEmpty()) {
                    listaOperandosModificar.add(filtradosListaOperandos.get(indice));
                } else if (!listaOperandosModificar.contains(filtradosListaOperandos.get(indice))) {
                    listaOperandosModificar.add(filtradosListaOperandos.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
        }
        RequestContext.getCurrentInstance().update("form:datosOperandos");
    }
    
    public void guardarVariables(int indice, BigInteger secuencia){
        index = indice;
        secuenciaOperando = listaOperandos.get(index).getSecuencia();
        operandoSeleccionado = listaOperandos.get(index);
        System.out.println("secuenciaOperando" + secuenciaOperando + "operandoSeleccionado" + operandoSeleccionado);
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("dirigirDetalle()");
       
    }

    public void verificarTipo(int indice, String tipo, BigInteger secuencia) {
        index = indice;
        
        if (listaOperandos.get(index).getTipo().equals("FUNCION")) {
            action = "funcion";
            secuenciaOperando = listaOperandos.get(index).getSecuencia();
            tipoOperando = listaOperandos.get(index).getTipo();
            operandoSeleccionado = listaOperandos.get(index);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dirigirTipoFuncion()");
        }
        if (listaOperandos.get(index).getTipo().equals("FORMULA")) {
            action = "formula";
            secuenciaOperando = listaOperandos.get(index).getSecuencia();
            tipoOperando = listaOperandos.get(index).getTipo();
            operandoSeleccionado = listaOperandos.get(index);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dirigirTipoFormula()");
        }
        if (listaOperandos.get(index).getTipo().equals("CONSTANTE")) {
            action = "constante";
            secuenciaOperando = listaOperandos.get(index).getSecuencia();
            tipoOperando = listaOperandos.get(index).getTipo();
            operandoSeleccionado = listaOperandos.get(index);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dirigirTipoConstante()");
        }
        if (listaOperandos.get(index).getTipo().equals("BLOQUE PL/SQL")) {
            action = "bloque";
            secuenciaOperando = listaOperandos.get(index).getSecuencia();
            tipoOperando = listaOperandos.get(index).getTipo();
            operandoSeleccionado = listaOperandos.get(index);
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("dirigirTipoBloque()");
        }
    }

    public void seleccionarTipoNuevoOperando(String estadoTipo, int tipoNuevo) {
        if (tipoNuevo == 1) {
            if (estadoTipo.equals("FORMULA")) {
                nuevoOperando.setTipo("FORMULA");
            } else if (estadoTipo.equals("BLOQUE PL/SQL")) {
                nuevoOperando.setTipo("BLOQUE PL/SQL");
            } else if (estadoTipo.equals("CONSTANTE")) {
                nuevoOperando.setTipo("CONSTANTE");
            } else if (estadoTipo.equals("FUNCION")) {
                nuevoOperando.setTipo("FUNCION");
            } else if (estadoTipo.equals("RELACIONAL")) {
                nuevoOperando.setTipo("RELACIONAL");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoTipoOperando");
        } else {
            if (estadoTipo.equals("FORMULA")) {
                duplicarOperando.setTipo("FORMULA");
            } else if (estadoTipo.equals("BLOQUE PL/SQL")) {
                duplicarOperando.setTipo("BLOQUE PL/SQL");
            } else if (estadoTipo.equals("CONSTANTE")) {
                duplicarOperando.setTipo("CONSTANTE");
            } else if (estadoTipo.equals("FUNCION")) {
                duplicarOperando.setTipo("FUNCION");
            } else if (estadoTipo.equals("RELACIONAL")) {
                duplicarOperando.setTipo("RELACIONAL");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarTipoOperando");
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

        } else if (LND == 2) {
            index = -1;
            secRegistro = null;
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("formularioDialogos:operandosDialogo");
            context.execute("operandosDialogo.show()");
        }
    }

    public void mostrarTodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaOperandos.isEmpty()) {
            listaOperandos.clear();
            listaOperandos = administrarOperandos.buscarOperandos();
        } else {
            listaOperandos = administrarOperandos.buscarOperandos();
        }

        context.update("form:datosOperandos");
        filtradosListaOperandos = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void confirmarDuplicar() {
        int pasa = 0;
        mensajeValidacion = new String();
        RequestContext context = RequestContext.getCurrentInstance();

        if (duplicarOperando.getDescripcion() == null || duplicarOperando.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " * Descripcion\n";
            pasa++;
        }

        if (duplicarOperando.getCodigo() == 0) {
            mensajeValidacion = mensajeValidacion + " * Codigo\n";
            pasa++;
        }

        if (duplicarOperando.getNombre().equals(" ") || duplicarOperando.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " * Nombre\n";
            pasa++;
        }
        if (duplicarOperando.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevoOperando");
            context.execute("validacionNuevoOperando.show()");
        }

        if (pasa == 0) {
            if (bandera == 1) {
                altoTabla = "245";
                operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
                operandosNombres.setFilterStyle("display: none; visibility: hidden;");
                operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
                operandosTipos.setFilterStyle("display: none; visibility: hidden;");
                operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
                operandosValores.setFilterStyle("display: none; visibility: hidden;");
                operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
                operandosDescripciones.setFilterStyle("display: none; visibility: hidden;");
                operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
                operandosCodigos.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosOperandos");
                bandera = 0;
                filtradosListaOperandos = null;
                tipoLista = 0;
            }
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            listaOperandos.add(duplicarOperando);
            listaOperandosCrear.add(duplicarOperando);

            index = -1;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.update("form:datosOperandos");
            duplicarOperando = new Operandos();
            context.update("formularioDialogos:DuplicarOperando");
            context.execute("DuplicarOperando.hide()");
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarOperandos() {
        RequestContext context = RequestContext.getCurrentInstance();

        if (!listaOperandos.isEmpty()) {
            listaOperandos.clear();
            listaOperandos.add(operandosSeleccionado);

        } else {
            listaOperandos.add(operandosSeleccionado);
        }
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        context.execute("operandosDialogo.hide()");
        context.reset("formularioDialogos:LOVOperandos:globalFilter");
        context.update("formularioDialogos:LOVOperandos");
        context.update("form:datosOperandos");
        filtradosListaOperandos = null;
        operandosSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
    }

    public void activarCtrlF11() {

        if (bandera == 0) {
            altoTabla = "223";
            operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
            operandosNombres.setFilterStyle("width: 60px");
            operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
            operandosTipos.setFilterStyle("width: 60px");
            operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
            operandosValores.setFilterStyle("width: 60px");
            operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
            operandosDescripciones.setFilterStyle("width: 60px");
            operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
            operandosCodigos.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosOperandos");
            bandera = 1;
            tipoLista = 1;
        } else if (bandera == 1) {
            altoTabla = "245";
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
            operandosNombres.setFilterStyle("display: none; visibility: hidden;");
            operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
            operandosTipos.setFilterStyle("display: none; visibility: hidden;");
            operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
            operandosValores.setFilterStyle("display: none; visibility: hidden;");
            operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
            operandosDescripciones.setFilterStyle("display: none; visibility: hidden;");
            operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
            operandosCodigos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOperandos");
            bandera = 0;
            filtradosListaOperandos = null;
            tipoLista = 0;
        }
    }

    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            altoTabla = "245";
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
            operandosNombres.setFilterStyle("display: none; visibility: hidden;");
            operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
            operandosTipos.setFilterStyle("display: none; visibility: hidden;");
            operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
            operandosValores.setFilterStyle("display: none; visibility: hidden;");
            operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
            operandosDescripciones.setFilterStyle("display: none; visibility: hidden;");
            operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
            operandosCodigos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOperandos");
            bandera = 0;
            filtradosListaOperandos = null;
            tipoLista = 0;
        }

        listaOperandosBorrar.clear();
        listaOperandosCrear.clear();
        listaOperandosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaOperandos = null;
        guardado = true;
        permitirIndex = true;
        cambiosPagina = false;

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosOperandos");
    }

    public void cancelarCambioOperandos() {
        lovfiltradoslistaOperandos = null;
        operandosSeleccionado = null;
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
                editarOperandos = listaOperandos.get(index);
            }
            if (tipoLista == 1) {
                editarOperandos = filtradosListaOperandos.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombres");
                context.execute("editarNombres.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarValores");
                context.execute("editarValores.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarDescripciones");
                context.execute("editarDescripciones.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarCodigos");
                context.execute("editarCodigos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosOperandosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDFTablasAnchas();
        exporter.export(context, tabla, "OperandosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosOperandosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "OperandosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //LIMPIAR NUEVO REGISTRO CIUDAD

    public void limpiarNuevoOperando() {
        nuevoOperando = new Operandos();
        nuevoOperando.setCodigo(0);
        nuevoOperando.setDescripcion(" ");
        nuevoOperando.setNombre(" ");
        nuevoOperando.setTipo("CONSTANTE");
        index = -1;
        secRegistro = null;
    }

    public void limpiarduplicarOperandos() {
        duplicarOperando = new Operandos();
        duplicarOperando.setCodigo(0);
        duplicarOperando.setDescripcion(" ");
        duplicarOperando.setNombre(" ");
        duplicarOperando.setTipo("CONSTANTE");
        index = -1;
        secRegistro = null;
    }

    //CREAR Operando
    public void agregarNuevoOperando() {
        int pasa = 0;
        mensajeValidacion = new String();

        RequestContext context = RequestContext.getCurrentInstance();

        if (nuevoOperando.getDescripcion() == null || nuevoOperando.getDescripcion().equals(" ")) {
            mensajeValidacion = mensajeValidacion + " * Descripcion\n";
            pasa++;
        }

        if (nuevoOperando.getNombre().equals(" ") || nuevoOperando.getNombre() == null) {
            mensajeValidacion = mensajeValidacion + " * Nombre\n";
            pasa++;
        }
        if (nuevoOperando.getTipo() == null) {
            mensajeValidacion = mensajeValidacion + " * Tipo\n";
            pasa++;
        }

        if (pasa != 0) {
            context.update("formularioDialogos:validacionNuevoOperando");
            context.execute("validacionNuevoOperando.show()");
        }

        if (pasa == 0) {
            if (bandera == 1) {
                altoTabla = "245";
                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
                operandosNombres.setFilterStyle("display: none; visibility: hidden;");
                operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
                operandosTipos.setFilterStyle("display: none; visibility: hidden;");
                operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
                operandosValores.setFilterStyle("display: none; visibility: hidden;");
                operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
                operandosDescripciones.setFilterStyle("display: none; visibility: hidden;");
                operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
                operandosCodigos.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosOperandos");
                bandera = 0;
                filtradosListaOperandos = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA NOVEDADES .
            k++;
            l = BigInteger.valueOf(k);
            nuevoOperando.setSecuencia(l);

            cambiosPagina = false;
            context.update("form:ACEPTAR");
            listaOperandosCrear.add(nuevoOperando);
            listaOperandos.add(nuevoOperando);
            nuevoOperando = new Operandos();
            context.update("form:datosOperandos");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoOperando.hide()");
            index = -1;
            secRegistro = null;
        }
    }

    //BORRAR CIUDADES
    public void borrarOperandos() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listaOperandosModificar.isEmpty() && listaOperandosModificar.contains(listaOperandos.get(index))) {
                    int modIndex = listaOperandosModificar.indexOf(listaOperandos.get(index));
                    listaOperandosModificar.remove(modIndex);
                    listaOperandosBorrar.add(listaOperandos.get(index));
                } else if (!listaOperandosCrear.isEmpty() && listaOperandosCrear.contains(listaOperandos.get(index))) {
                    int crearIndex = listaOperandosCrear.indexOf(listaOperandos.get(index));
                    listaOperandosCrear.remove(crearIndex);
                } else {
                    listaOperandosBorrar.add(listaOperandos.get(index));
                }
                listaOperandos.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaOperandosModificar.isEmpty() && listaOperandosModificar.contains(filtradosListaOperandos.get(index))) {
                    int modIndex = listaOperandosModificar.indexOf(filtradosListaOperandos.get(index));
                    listaOperandosModificar.remove(modIndex);
                    listaOperandosBorrar.add(filtradosListaOperandos.get(index));
                } else if (!listaOperandosCrear.isEmpty() && listaOperandosCrear.contains(filtradosListaOperandos.get(index))) {
                    int crearIndex = listaOperandosCrear.indexOf(filtradosListaOperandos.get(index));
                    listaOperandosCrear.remove(crearIndex);
                } else {
                    listaOperandosBorrar.add(filtradosListaOperandos.get(index));
                }
                int CIndex = listaOperandos.indexOf(filtradosListaOperandos.get(index));
                listaOperandos.remove(CIndex);
                filtradosListaOperandos.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosOperandos");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //GUARDAR
    public void guardarCambiosOperandos() {
        if (guardado == false) {
            System.out.println("Realizando Operaciones Novedades");

            if (!listaOperandosBorrar.isEmpty()) {
                for (int i = 0; i < listaOperandosBorrar.size(); i++) {
                    System.out.println("Borrando..." + listaOperandosBorrar.size());

                    administrarOperandos.borrarOperando(listaOperandosBorrar.get(i));
                }
                System.out.println("Entra");
                listaOperandosBorrar.clear();
            }

            if (!listaOperandosCrear.isEmpty()) {
                for (int i = 0; i < listaOperandosCrear.size(); i++) {
                    System.out.println("Creando...");

                    administrarOperandos.crearOperando(listaOperandosCrear.get(i));
                }
                System.out.println("LimpiaLista");
                listaOperandosCrear.clear();
            }
            if (!listaOperandosModificar.isEmpty()) {
                administrarOperandos.modificarOperando(listaOperandosModificar);
                listaOperandosModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaOperandos = null;

            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = true;
            context.update("form:ACEPTAR");
            context.update("form:datosOperandos");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            //  k = 0;
        }
        index = -1;
        secRegistro = null;
    }

    //DUPLICAR Operando
    public void duplicarO() {
        if (index >= 0) {
            duplicarOperando = new Operandos();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarOperando.setSecuencia(l);
                duplicarOperando.setNombre(listaOperandos.get(index).getNombre());
                duplicarOperando.setTipo(listaOperandos.get(index).getTipo());
                duplicarOperando.setDescripcion(listaOperandos.get(index).getDescripcion());
                duplicarOperando.setCambioanual(listaOperandos.get(index).getCambioanual());
                duplicarOperando.setActualizable(listaOperandos.get(index).getActualizable());
                duplicarOperando.setCodigo(listaOperandos.get(index).getCodigo());
            }
            if (tipoLista == 1) {
                duplicarOperando.setSecuencia(l);
                duplicarOperando.setNombre(filtradosListaOperandos.get(index).getNombre());
                duplicarOperando.setTipo(filtradosListaOperandos.get(index).getTipo());
                duplicarOperando.setDescripcion(filtradosListaOperandos.get(index).getDescripcion());
                duplicarOperando.setCambioanual(filtradosListaOperandos.get(index).getCambioanual());
                duplicarOperando.setActualizable(filtradosListaOperandos.get(index).getActualizable());
                duplicarOperando.setCodigo(filtradosListaOperandos.get(index).getCodigo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarOperando");
            context.execute("DuplicarOperando.show()");
            index = -1;
            secRegistro = null;
        }
    }

    //RASTROS 
    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (!listaOperandos.isEmpty()) {
            if (secRegistro != null) {
                int result = administrarRastros.obtenerTabla(secRegistro, "OPERANDOS");
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
            if (administrarRastros.verificarHistoricosTabla("OPERANDOS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void salir() {
        if (bandera == 1) {
            altoTabla = "245";
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            operandosNombres = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosNombres");
            operandosNombres.setFilterStyle("display: none; visibility: hidden;");
            operandosTipos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosTipos");
            operandosTipos.setFilterStyle("display: none; visibility: hidden;");
            operandosValores = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosValores");
            operandosValores.setFilterStyle("display: none; visibility: hidden;");
            operandosDescripciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosDescripciones");
            operandosDescripciones.setFilterStyle("display: none; visibility: hidden;");
            operandosCodigos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosOperandos:operandosCodigos");
            operandosCodigos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosOperandos");
            bandera = 0;
            filtradosListaOperandos = null;
            tipoLista = 0;
        }
        listaOperandosBorrar.clear();
        listaOperandosCrear.clear();
        listaOperandosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaOperandos = null;
        guardado = true;
        permitirIndex = true;

    }

    //Getter & Setter
    public List<Operandos> getListaOperandos() {
        if (listaOperandos == null) {
            listaOperandos = administrarOperandos.buscarOperandos();
            for (int i = 0; i < listaOperandos.size(); i++) {
                String valor;
                valor = administrarOperandos.buscarValores(listaOperandos.get(i).getSecuencia());
                listaOperandos.get(i).setValor(valor);
            }
        }
        return listaOperandos;
    }

    public void setListaOperandos(List<Operandos> listaOperandos) {
        this.listaOperandos = listaOperandos;
    }

    public List<Operandos> getFiltradosListaOperandos() {
        return filtradosListaOperandos;
    }

    public void setFiltradosListaOperandos(List<Operandos> filtradosListaOperandos) {
        this.filtradosListaOperandos = filtradosListaOperandos;
    }

    public List<Operandos> getLovlistaOperandos() {
        if (lovlistaOperandos == null) {
            lovlistaOperandos = administrarOperandos.buscarOperandos();
        }
        return lovlistaOperandos;
    }

    public void setLovlistaOperandos(List<Operandos> lovlistaOperandos) {
        this.lovlistaOperandos = lovlistaOperandos;
    }

    public List<Operandos> getLovfiltradoslistaOperandos() {
        return lovfiltradoslistaOperandos;
    }

    public void setLovfiltradoslistaOperandos(List<Operandos> lovfiltradoslistaOperandos) {
        this.lovfiltradoslistaOperandos = lovfiltradoslistaOperandos;
    }

    public Operandos getOperandosSeleccionado() {
        return operandosSeleccionado;
    }

    public void setOperandosSeleccionado(Operandos operandosSeleccionado) {
        this.operandosSeleccionado = operandosSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Operandos getEditarOperandos() {
        return editarOperandos;
    }

    public void setEditarOperandos(Operandos editarOperandos) {
        this.editarOperandos = editarOperandos;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public Operandos getNuevoOperando() {
        return nuevoOperando;
    }

    public void setNuevoOperando(Operandos nuevoOperando) {
        this.nuevoOperando = nuevoOperando;
    }

    public Operandos getDuplicarOperando() {
        return duplicarOperando;
    }

    public void setDuplicarOperando(Operandos duplicarOperando) {
        this.duplicarOperando = duplicarOperando;
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

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public BigInteger getSecuenciaOperando() {
        return secuenciaOperando;
    }

    public void setSecuenciaOperando(BigInteger secuenciaOperando) {
        this.secuenciaOperando = secuenciaOperando;
    }

    public String getTipoOperando() {
        return tipoOperando;
    }

    public void setTipoOperando(String tipoOperando) {
        this.tipoOperando = tipoOperando;
    }

    public Operandos getOperandoSeleccionado() {
        return operandoSeleccionado;
    }

    public void setOperandoSeleccionado(Operandos operandoSeleccionado) {
        this.operandoSeleccionado = operandoSeleccionado;
    }

}

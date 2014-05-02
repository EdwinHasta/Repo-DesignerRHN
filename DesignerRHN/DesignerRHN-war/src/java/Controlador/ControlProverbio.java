/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Recordatorios;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarRecordatoriosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
public class ControlProverbio implements Serializable {

    @EJB
    AdministrarRecordatoriosInterface administrarRecordatorios;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //LISTA PROVERBIOS
    private List<Recordatorios> listaProverbios;
    private List<Recordatorios> filtradosListaProverbios;
    private Recordatorios proverbioSeleccionado;
    //LISTA MENSAJES
    private List<Recordatorios> listaMensajesUsuario;
    private List<Recordatorios> filtradosListaMensajesUsuario;
    private Recordatorios mensajeUsuarioSeleccionado;
    //OTROS
    private boolean aceptar;
    private int index;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private boolean permitirIndex;
    //RASTROS
    private BigInteger secRegistro;
    private boolean guardado, guardarOk;
    //Editar Celda
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista, tipoListaNF;
    //ALTO SCROLL TABLA
    private String altoTabla, altoTablaNF;
    private boolean cambiosPagina;
    //Crear Proverbio
    private List<Recordatorios> listaProverbiosCrear;
    public Recordatorios nuevoProverbio;
    public Recordatorios duplicarProverbio;
    private int k;
    private BigInteger l;
    private int m;
    private BigInteger n;
    //Crear Mensaje Usuario
    private List<Recordatorios> listaMensajesUsuariosCrear;
    public Recordatorios nuevoRegistroMensajesUsuarios;
    public Recordatorios duplicarRegistroMensajesUsuarios;
    //OTROS
    private int indexNF;
    private int banderaNF;
    //Modificar Novedades
    private List<Recordatorios> listaProverbiosModificar;
    //Borrar Novedades
    private List<Recordatorios> listaProverbiosBorrar;
    //Cual Tabla
    private int CualTabla;
    //editar celda
    private Recordatorios editarProverbios;
    //Columnas Tabla Proverbio
    private Column pMensaje;
    //Columnas Tabla Detalles Tipos Cotizantes
    private Column mAno, mMes, mDia, mMensaje;
    //Tabla a Imprimir
    private String tablaImprimir, nombreArchivo;
    //Cual Insertar
    private String cualInsertar;
    //Cual Nuevo Update
    private String cualNuevo;
    //Modificar Detalles Tipos Cotizantes
    private List<Recordatorios> listaMensajesUsuariosModificar;
    private List<Recordatorios> listaMensajesUsuariosBorrar;

    public ControlProverbio() {
        cambiosPagina = true;
        permitirIndex = true;
        listaProverbios = null;
        listaMensajesUsuario = null;
        permitirIndex = true;
        aceptar = true;
        secRegistro = null;
        guardado = true;
        tipoLista = 0;
        altoTabla = "115";
        altoTablaNF = "115";
        tipoListaNF = 0;
        tablaImprimir = ":formExportar:datosProverbiosExportar";
        nombreArchivo = "ProverbiosXML";
        k = 0;
        cualInsertar = ":formularioDialogos:NuevoRegistroProverbio";
        cualNuevo = ":formularioDialogos:nuevoRegistroProverbio";
        //Crear Vigencia Formal
        nuevoProverbio = new Recordatorios();
        duplicarProverbio = new Recordatorios();
        nuevoProverbio.setTipo("PROVERBIO");
        nuevoRegistroMensajesUsuarios = new Recordatorios();
        nuevoRegistroMensajesUsuarios.setTipo("RECORDATORIO");
        duplicarRegistroMensajesUsuarios = new Recordatorios();
        //secuenciaPersona = BigInteger.valueOf(10668967);
        aceptar = true;
        listaMensajesUsuariosBorrar = new ArrayList<Recordatorios>();
        listaMensajesUsuariosCrear = new ArrayList<Recordatorios>();
        listaMensajesUsuariosModificar = new ArrayList<Recordatorios>();
        listaProverbiosBorrar = new ArrayList<Recordatorios>();
        listaProverbiosCrear = new ArrayList<Recordatorios>();
        listaProverbiosModificar = new ArrayList<Recordatorios>();
        //Inicializar LOVS
        secRegistro = null;
        m = 0;
    }

    //GUARDAR
    public void guardarCambiosProverbios() {

        if (guardado == false) {
            if (!listaProverbiosBorrar.isEmpty()) {
                for (int i = 0; i < listaProverbiosBorrar.size(); i++) {
                    administrarRecordatorios.borrar(listaProverbiosBorrar.get(i));
                }

                listaProverbiosBorrar.clear();
            }
        }
        if (!listaProverbiosCrear.isEmpty()) {
            for (int i = 0; i < listaProverbiosCrear.size(); i++) {
                administrarRecordatorios.crear(listaProverbiosCrear.get(i));
            }

        }

        listaProverbiosCrear.clear();

        if (!listaProverbiosModificar.isEmpty()) {
            administrarRecordatorios.modificar(listaProverbiosModificar);
            listaProverbiosModificar.clear();
        }
//  k = 0;

        if (guardado == false) {
            if (!listaMensajesUsuariosBorrar.isEmpty()) {
                for (int i = 0; i < listaMensajesUsuariosBorrar.size(); i++) {
                    administrarRecordatorios.borrarMU(listaMensajesUsuariosBorrar.get(i));
                    listaMensajesUsuariosBorrar.clear();
                }
            }
            if (!listaMensajesUsuariosCrear.isEmpty()) {
                for (int i = 0; i < listaMensajesUsuariosCrear.size(); i++) {
                    administrarRecordatorios.crearMU(listaMensajesUsuariosCrear.get(i));
                }
            }

            listaMensajesUsuariosCrear.clear();
        }
        if (!listaMensajesUsuariosModificar.isEmpty()) {
            administrarRecordatorios.modificarMU(listaMensajesUsuariosModificar);
            listaMensajesUsuariosModificar.clear();
        }

        listaMensajesUsuario = null;
        listaProverbios = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProverbios");
        index = -1;
        secRegistro = null;
        context.update("form:datosMensajesUsuarios");
        guardado = true;
        permitirIndex = true;
        RequestContext.getCurrentInstance().update("form:ACEPTAR");
        FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        context.update("form:growl");
        indexNF = -1;
        secRegistro = null;
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            CualTabla = 0;
            indexNF = -1;

            if (tipoLista == 0) {
                secRegistro = listaProverbios.get(index).getSecuencia();

            } else {
                secRegistro = filtradosListaProverbios.get(index).getSecuencia();

            }
        }
    }

    //Ubicacion Celda.
    public void cambiarIndiceNF(int indiceNF, int celdaNF) {

        if (permitirIndex == true) {
            indexNF = indiceNF;
            cualCelda = celdaNF;
            CualTabla = 1;
            tablaImprimir = ":formExportar:datosMensajesUsuariosExportar";
            cualNuevo = ":formularioDialogos:nuevoMensajeUsuarios";
            cualInsertar = "formularioDialogos:NuevoRegistroMensajeUsuario";
            nombreArchivo = "MensajeUsuarioXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:exportarXML");
            if (tipoListaNF == 0) {
                secRegistro = listaMensajesUsuario.get(indexNF).getSecuencia();
            } else {
                secRegistro = filtradosListaMensajesUsuario.get(indexNF).getSecuencia();

            }
        }
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevoProverbio() {
        nuevoProverbio = new Recordatorios();
        nuevoProverbio.setTipo("PROVERBIO");
        index = -1;
        secRegistro = null;

    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //FILTRADO
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0 && CualTabla == 0) {
            altoTabla = "91";
            pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
            pMensaje.setFilterStyle("width: 60px");

            RequestContext.getCurrentInstance().update("form:datosProverbios");
            bandera = 1;

        } else if (bandera == 1 && CualTabla == 0) {
            altoTabla = "115";
            pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
            pMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProverbios");
            bandera = 0;
            filtradosListaProverbios = null;
            tipoLista = 0;
        } else if (banderaNF == 0 && CualTabla == 1) {
            altoTablaNF = "91";
            mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
            mAno.setFilterStyle("width: 60px");
            mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
            mMes.setFilterStyle("width: 60px");
            mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
            mDia.setFilterStyle("width: 60px");
            mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
            mMensaje.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
            banderaNF = 1;

        } else if (banderaNF == 1 && CualTabla == 1) {
            altoTablaNF = "115";
            mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
            mAno.setFilterStyle("display: none; visibility: hidden;");
            mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
            mMes.setFilterStyle("display: none; visibility: hidden;");
            mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
            mDia.setFilterStyle("display: none; visibility: hidden;");
            mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
            mMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
            banderaNF = 0;
            filtradosListaMensajesUsuario = null;
            tipoListaNF = 0;
        }
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosProverbiosExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "ProverbiosPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMensajesUsuariosExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "MensajesUsuariosPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexNF = -1;
            secRegistro = null;
        }
    }

    public void exportXLS() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosProverbiosExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "ProverbiosXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosMensajesUsuariosExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "MensajesUsuariosXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexNF = -1;
            secRegistro = null;
        }
    }

    public void tablaNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();

        if ((listaProverbios.isEmpty() || listaMensajesUsuario.isEmpty())) {
            context.update("formularioDialogos:elegirTabla");
            context.execute("elegirTabla.show()");
        } else if (CualTabla == 0) {
            context.update("formularioDialogos:NuevoRegistroProverbio");
            context.execute("NuevoRegistroProverbio.show()");
        } else if (CualTabla == 1) {
            context.update("formularioDialogos:NuevoRegistroMensajeUsuario");
            context.execute("NuevoRegistroMensajeUsuario.show()");
        }
    }

    //EVENTO FILTRARNF
    public void eventoFiltrarNF() {
        if (tipoListaNF == 0) {
            tipoListaNF = 1;
        }
    }

    //AUTOCOMPLETAR
    public void modificarProverbio(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaProverbiosCrear.contains(listaProverbios.get(indice))) {

                    if (listaProverbiosModificar.isEmpty()) {
                        listaProverbiosModificar.add(listaProverbios.get(indice));
                    } else if (!listaProverbiosModificar.contains(listaProverbios.get(indice))) {
                        listaProverbiosModificar.add(listaProverbios.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaProverbiosCrear.contains(filtradosListaProverbios.get(indice))) {

                    if (listaProverbiosCrear.isEmpty()) {
                        listaProverbiosCrear.add(filtradosListaProverbios.get(indice));
                    } else if (!listaProverbiosCrear.contains(filtradosListaProverbios.get(indice))) {
                        listaProverbiosCrear.add(filtradosListaProverbios.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosProverbios");
        }
    }

    //AUTOCOMPLETAR
    public void modificarMensajeUsuario(int indiceNF, String confirmarCambio, String valorConfirmar) {
        indexNF = indiceNF;
        int coincidencias = 0;
        int indiceNFUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaMensajesUsuariosCrear.contains(listaMensajesUsuario.get(indiceNF))) {

                    if (listaMensajesUsuariosModificar.isEmpty()) {
                        listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indiceNF));
                    } else if (!listaMensajesUsuariosModificar.contains(listaMensajesUsuario.get(indiceNF))) {
                        listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                indexNF = -1;
                secRegistro = null;

            } else {
                if (!listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indiceNF))) {

                    if (listaMensajesUsuariosCrear.isEmpty()) {
                        listaMensajesUsuariosCrear.add(filtradosListaMensajesUsuario.get(indiceNF));
                    } else if (!listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indiceNF))) {
                        listaMensajesUsuariosCrear.add(filtradosListaMensajesUsuario.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                indexNF = -1;
                secRegistro = null;
            }
            context.update("form:datosMensajesUsuarios");
        }
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0 && CualTabla == 0) {
            if (tipoLista == 0) {
                editarProverbios = listaProverbios.get(index);
            }
            if (tipoLista == 1) {
                editarProverbios = filtradosListaProverbios.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarMensajes");
                context.execute("editarMensajes.show()");
                cualCelda = -1;
            }
            index = -1;
        } else if (indexNF >= 0 && CualTabla == 1) {
            if (tipoListaNF == 0) {
                editarProverbios = listaMensajesUsuario.get(indexNF);
            }
            if (tipoListaNF == 1) {
                editarProverbios = filtradosListaMensajesUsuario.get(indexNF);
            }

            RequestContext context = RequestContext.getCurrentInstance();

            if (cualCelda == 0) {
                context.update("formularioDialogos:editarAños");
                context.execute("editarAños.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarMeses");
                context.execute("editarMeses.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDias");
                context.execute("editarDias.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarMensajes2");
                context.execute("editarMensajes2.show()");
                cualCelda = -1;
            }
            indexNF = -1;
        }

        secRegistro = null;
    }

    public void dialogoProverbios() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosProverbios");
        context.execute("NuevoRegistroProverbio.show()");
    }

    public void dialogoMensajesUsuarios() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMensajesUsuarios");
        context.execute("NuevoRegistroMensajeUsuario.show()");

    }

    //CREAR NUEVO PROVERBIO
    public void agregarNuevoProverbio() {
        int pasa = 0;

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext c = FacesContext.getCurrentInstance();
        if (pasa == 0) {
            if (bandera == 1 && CualTabla == 0) {
                altoTabla = "115";
                pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
                pMensaje.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosProverbios");
                bandera = 0;
                filtradosListaProverbios = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS FORMALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevoProverbio.setSecuencia(l);
            nuevoProverbio.setTipo("PROVERBIO");

            listaProverbiosCrear.add(nuevoProverbio);
            listaProverbios.add(nuevoProverbio);

            nuevoProverbio = new Recordatorios();

            context.update("form:datosProverbios");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            context.execute("NuevoRegistroProverbio.hide()");
            indexNF = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevoProverbio");
            context.execute("validacionNuevoProverbio.show()");
        }
    }

    //CREAR NUEVO PROVERBIO
    public void agregarNuevoMensajeUsuario() {
        int pasa = 0;

        RequestContext context = RequestContext.getCurrentInstance();

        if (pasa == 0) {
            if (bandera == 1 && CualTabla == 0) {
                FacesContext c = FacesContext.getCurrentInstance();
                altoTablaNF = "115";
                mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
                mAno.setFilterStyle("display: none; visibility: hidden;");
                mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
                mMes.setFilterStyle("display: none; visibility: hidden;");
                mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
                mDia.setFilterStyle("display: none; visibility: hidden;");
                mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
                mMensaje.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
                banderaNF = 0;
                filtradosListaMensajesUsuario = null;
                tipoListaNF = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS FORMALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevoRegistroMensajesUsuarios.setSecuencia(l);
            nuevoRegistroMensajesUsuarios.setTipo("RECORDATORIO");

            listaMensajesUsuariosCrear.add(nuevoRegistroMensajesUsuarios);
            listaMensajesUsuario.add(nuevoRegistroMensajesUsuarios);

            nuevoRegistroMensajesUsuarios = new Recordatorios();

            context.update("form:datosMensajesUsuarios");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            context.execute("NuevoRegistroMensajeUsuario.hide()");
            indexNF = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevoMensajeUsuario");
            context.execute("validacionNuevoMensajeUsuario.show()");
        }
    }

    //DUPLICAR TIPO COTIZANTE
    public void duplicarP() {
        if (index >= 0 && CualTabla == 0) {
            duplicarProverbio = new Recordatorios();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarProverbio.setSecuencia(l);
                duplicarProverbio.setMensaje(listaProverbios.get(index).getMensaje());
                duplicarProverbio.setTipo(listaProverbios.get(index).getTipo());
            }
            if (tipoLista == 1) {
                duplicarProverbio.setSecuencia(l);
                duplicarProverbio.setMensaje(filtradosListaProverbios.get(index).getMensaje());
                duplicarProverbio.setTipo(filtradosListaProverbios.get(index).getTipo());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroProverbio");
            context.execute("DuplicarRegistroProverbio.show()");
            index = -1;
            secRegistro = null;
        } else if (indexNF >= 0 && CualTabla == 1) {

            duplicarRegistroMensajesUsuarios = new Recordatorios();
            m++;
            n = BigInteger.valueOf(m);

            if (tipoListaNF == 0) {
                duplicarRegistroMensajesUsuarios.setSecuencia(n);
                duplicarRegistroMensajesUsuarios.setAno(listaMensajesUsuario.get(indexNF).getAno());
                duplicarRegistroMensajesUsuarios.setMes(listaMensajesUsuario.get(indexNF).getMes());
                duplicarRegistroMensajesUsuarios.setDia(listaMensajesUsuario.get(indexNF).getDia());
                duplicarRegistroMensajesUsuarios.setTipo(listaMensajesUsuario.get(indexNF).getTipo());
                duplicarRegistroMensajesUsuarios.setMensaje(listaMensajesUsuario.get(indexNF).getMensaje());
            }
            if (tipoListaNF == 1) {
                duplicarRegistroMensajesUsuarios.setSecuencia(n);
                duplicarRegistroMensajesUsuarios.setAno(filtradosListaMensajesUsuario.get(indexNF).getAno());
                duplicarRegistroMensajesUsuarios.setMes(filtradosListaMensajesUsuario.get(indexNF).getMes());
                duplicarRegistroMensajesUsuarios.setDia(filtradosListaMensajesUsuario.get(indexNF).getDia());
                duplicarRegistroMensajesUsuarios.setTipo(filtradosListaMensajesUsuario.get(indexNF).getTipo());
                duplicarRegistroMensajesUsuarios.setMensaje(filtradosListaMensajesUsuario.get(indexNF).getMensaje());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroMensajeUsuario");
            context.execute("DuplicarRegistroMensajeUsuario.show()");
            indexNF = -1;
            secRegistro = null;

        }
    }

    public void seleccionarAno(String estadoAno, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoAno != null) {

                if (estadoAno.equals("2005")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2005"));
                } else if (estadoAno.equals("2006")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2006"));
                } else if (estadoAno.equals("2007")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2007"));
                } else if (estadoAno.equals("2008")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2008"));
                } else if (estadoAno.equals("2009")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2009"));
                } else if (estadoAno.equals("2010")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2010"));
                } else if (estadoAno.equals("2011")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2011"));
                } else if (estadoAno.equals("2012")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2012"));
                } else if (estadoAno.equals("2013")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2013"));
                } else if (estadoAno.equals("2014")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2014"));
                } else if (estadoAno.equals("2015")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2015"));
                } else if (estadoAno.equals("2016")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2016"));
                } else if (estadoAno.equals("2017")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2017"));
                } else if (estadoAno.equals("2018")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2018"));
                } else if (estadoAno.equals("2019")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2019"));
                } else if (estadoAno.equals("2020")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2020"));
                } else if (estadoAno.equals("2021")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2021"));
                } else if (estadoAno.equals("2022")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2022"));
                } else if (estadoAno.equals("2023")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2023"));
                } else if (estadoAno.equals("2024")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2024"));
                } else if (estadoAno.equals("2025")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("2025"));
                } else if (estadoAno.equalsIgnoreCase("TODOS LOS AÑOS")) {
                    listaMensajesUsuario.get(indice).setAno(new Short("0"));
                }
            } else {
                listaMensajesUsuario.get(indice).setAno(null);
            }
            if (!listaMensajesUsuariosCrear.contains(listaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(listaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                }
            }
        } else {
            if (estadoAno != null) {

                if (estadoAno.equals("2005")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2005"));
                } else if (estadoAno.equals("2006")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2006"));
                } else if (estadoAno.equals("2007")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2007"));
                } else if (estadoAno.equals("2008")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2008"));
                } else if (estadoAno.equals("2009")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2009"));
                } else if (estadoAno.equals("2010")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2010"));
                } else if (estadoAno.equals("2011")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2011"));
                } else if (estadoAno.equals("2012")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2012"));
                } else if (estadoAno.equals("2013")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2013"));
                } else if (estadoAno.equals("2014")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2014"));
                } else if (estadoAno.equals("2015")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2015"));
                } else if (estadoAno.equals("2016")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2016"));
                } else if (estadoAno.equals("2017")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2017"));
                } else if (estadoAno.equals("2018")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2018"));
                } else if (estadoAno.equals("2019")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2019"));
                } else if (estadoAno.equals("2020")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2020"));
                } else if (estadoAno.equals("2021")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2021"));
                } else if (estadoAno.equals("2022")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2022"));
                } else if (estadoAno.equals("2023")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2023"));
                } else if (estadoAno.equals("2024")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2024"));
                } else if (estadoAno.equals("2025")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("2025"));
                } else if (estadoAno.equalsIgnoreCase("TODOS LOS AÑOS")) {
                    filtradosListaMensajesUsuario.get(indice).setAno(new Short("0"));
                }
            } else {
                filtradosListaMensajesUsuario.get(indice).setAno(null);
            }
            if (!listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(filtradosListaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        }
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");

    }

    public void seleccionarMes(String estadoMes, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoMes != null) {

                if (estadoMes.equalsIgnoreCase("ENERO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("1"));
                } else if (estadoMes.equalsIgnoreCase("FEBRERO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("2"));
                } else if (estadoMes.equalsIgnoreCase("MARZO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("3"));
                } else if (estadoMes.equalsIgnoreCase("ABRIL")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("4"));
                } else if (estadoMes.equalsIgnoreCase("MAYO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("5"));
                } else if (estadoMes.equalsIgnoreCase("JUNIO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("6"));
                } else if (estadoMes.equalsIgnoreCase("JULIO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("7"));
                } else if (estadoMes.equalsIgnoreCase("AGOSTO")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("8"));
                } else if (estadoMes.equalsIgnoreCase("SEPTIEMBRE")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("9"));
                } else if (estadoMes.equalsIgnoreCase("OCTUBRE")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("10"));
                } else if (estadoMes.equalsIgnoreCase("NOVIEMBRE")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("11"));
                } else if (estadoMes.equalsIgnoreCase("DICIEMBRE")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("12"));
                } else if (estadoMes.equalsIgnoreCase("TODOS LOS MESES")) {
                    listaMensajesUsuario.get(indice).setMes(new Short("0"));
                }
            } else {
                listaMensajesUsuario.get(indice).setMes(null);
            }
            if (!listaMensajesUsuariosCrear.contains(listaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(listaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                }
            }
        } else {
            if (estadoMes != null) {

                if (estadoMes.equalsIgnoreCase("ENERO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("1"));
                } else if (estadoMes.equalsIgnoreCase("FEBRERO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("2"));
                } else if (estadoMes.equalsIgnoreCase("MARZO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("3"));
                } else if (estadoMes.equalsIgnoreCase("ABRIL")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("4"));
                } else if (estadoMes.equalsIgnoreCase("MAYO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("5"));
                } else if (estadoMes.equalsIgnoreCase("JUNIO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("6"));
                } else if (estadoMes.equalsIgnoreCase("JULIO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("7"));
                } else if (estadoMes.equalsIgnoreCase("AGOSTO")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("8"));
                } else if (estadoMes.equalsIgnoreCase("SEPTIEMBRE")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("9"));
                } else if (estadoMes.equalsIgnoreCase("OCTUBRE")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("10"));
                } else if (estadoMes.equalsIgnoreCase("NOVIEMBRE")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("11"));
                } else if (estadoMes.equalsIgnoreCase("DICIEMBRE")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("12"));
                } else if (estadoMes.equalsIgnoreCase("TODOS LOS MESES")) {
                    filtradosListaMensajesUsuario.get(indice).setMes(new Short("0"));
                }
            } else {
                filtradosListaMensajesUsuario.get(indice).setMes(null);
            }
            if (!listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(filtradosListaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        }
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");

    }

    public void seleccionarDia(String estadoDia, int indice, int celda) {
        if (tipoLista == 0) {
            if (estadoDia != null) {

                if (estadoDia.equalsIgnoreCase("01")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("1"));
                } else if (estadoDia.equalsIgnoreCase("02")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("2"));
                } else if (estadoDia.equalsIgnoreCase("03")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("3"));
                } else if (estadoDia.equalsIgnoreCase("04")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("4"));
                } else if (estadoDia.equalsIgnoreCase("05")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("5"));
                } else if (estadoDia.equalsIgnoreCase("06")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("6"));
                } else if (estadoDia.equalsIgnoreCase("07")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("7"));
                } else if (estadoDia.equalsIgnoreCase("08")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("8"));
                } else if (estadoDia.equalsIgnoreCase("09")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("9"));
                } else if (estadoDia.equalsIgnoreCase("10")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("10"));
                } else if (estadoDia.equalsIgnoreCase("11")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("11"));
                } else if (estadoDia.equalsIgnoreCase("12")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("12"));
                } else if (estadoDia.equalsIgnoreCase("13")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("13"));
                } else if (estadoDia.equalsIgnoreCase("14")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("14"));
                } else if (estadoDia.equalsIgnoreCase("15")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("15"));
                } else if (estadoDia.equalsIgnoreCase("16")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("16"));
                } else if (estadoDia.equalsIgnoreCase("17")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("17"));
                } else if (estadoDia.equalsIgnoreCase("18")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("18"));
                } else if (estadoDia.equalsIgnoreCase("19")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("19"));
                } else if (estadoDia.equalsIgnoreCase("20")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("20"));
                } else if (estadoDia.equalsIgnoreCase("21")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("21"));
                } else if (estadoDia.equalsIgnoreCase("22")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("22"));
                } else if (estadoDia.equalsIgnoreCase("23")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("23"));
                } else if (estadoDia.equalsIgnoreCase("24")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("24"));
                } else if (estadoDia.equalsIgnoreCase("25")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("25"));
                } else if (estadoDia.equalsIgnoreCase("26")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("26"));
                } else if (estadoDia.equalsIgnoreCase("27")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("27"));
                } else if (estadoDia.equalsIgnoreCase("28")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("28"));
                } else if (estadoDia.equalsIgnoreCase("29")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("29"));
                } else if (estadoDia.equalsIgnoreCase("30")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("30"));
                } else if (estadoDia.equalsIgnoreCase("31")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("31"));
                } else if (estadoDia.equalsIgnoreCase("TODOS LOS DIAS")) {
                    listaMensajesUsuario.get(indice).setDia(new Short("0"));
                }
            } else {
                listaMensajesUsuario.get(indice).setDia(null);
            }
            if (!listaMensajesUsuariosCrear.contains(listaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(listaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(listaMensajesUsuario.get(indice));
                }
            }
        } else {
            if (estadoDia != null) {

                if (estadoDia.equalsIgnoreCase("01")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("1"));
                } else if (estadoDia.equalsIgnoreCase("02")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("2"));
                } else if (estadoDia.equalsIgnoreCase("03")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("3"));
                } else if (estadoDia.equalsIgnoreCase("04")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("4"));
                } else if (estadoDia.equalsIgnoreCase("05")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("5"));
                } else if (estadoDia.equalsIgnoreCase("06")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("6"));
                } else if (estadoDia.equalsIgnoreCase("07")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("7"));
                } else if (estadoDia.equalsIgnoreCase("08")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("8"));
                } else if (estadoDia.equalsIgnoreCase("09")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("9"));
                } else if (estadoDia.equalsIgnoreCase("10")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("10"));
                } else if (estadoDia.equalsIgnoreCase("11")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("11"));
                } else if (estadoDia.equalsIgnoreCase("12")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("12"));
                } else if (estadoDia.equalsIgnoreCase("13")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("13"));
                } else if (estadoDia.equalsIgnoreCase("14")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("14"));
                } else if (estadoDia.equalsIgnoreCase("15")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("15"));
                } else if (estadoDia.equalsIgnoreCase("16")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("16"));
                } else if (estadoDia.equalsIgnoreCase("17")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("17"));
                } else if (estadoDia.equalsIgnoreCase("18")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("18"));
                } else if (estadoDia.equalsIgnoreCase("19")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("19"));
                } else if (estadoDia.equalsIgnoreCase("20")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("20"));
                } else if (estadoDia.equalsIgnoreCase("21")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("21"));
                } else if (estadoDia.equalsIgnoreCase("22")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("22"));
                } else if (estadoDia.equalsIgnoreCase("23")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("23"));
                } else if (estadoDia.equalsIgnoreCase("24")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("24"));
                } else if (estadoDia.equalsIgnoreCase("25")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("25"));
                } else if (estadoDia.equalsIgnoreCase("26")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("26"));
                } else if (estadoDia.equalsIgnoreCase("27")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("27"));
                } else if (estadoDia.equalsIgnoreCase("28")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("28"));
                } else if (estadoDia.equalsIgnoreCase("29")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("29"));
                } else if (estadoDia.equalsIgnoreCase("30")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("30"));
                } else if (estadoDia.equalsIgnoreCase("31")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("31"));
                } else if (estadoDia.equalsIgnoreCase("TODOS LOS DIAS")) {
                    filtradosListaMensajesUsuario.get(indice).setDia(new Short("0"));
                }
            } else {
                filtradosListaMensajesUsuario.get(indice).setMes(null);
            }
            if (!listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indice))) {
                if (listaMensajesUsuariosModificar.isEmpty()) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                } else if (!listaMensajesUsuariosModificar.contains(filtradosListaMensajesUsuario.get(indice))) {
                    listaMensajesUsuariosModificar.add(filtradosListaMensajesUsuario.get(indice));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        }
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");

    }

    public void seleccionarTipoNuevoAno(String estadoAno, int tipoNuevo) {

        if (tipoNuevo == 1) {
            if (estadoAno != null) {

                if (estadoAno.equals("2005")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2005"));
                } else if (estadoAno.equals("2006")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2006"));
                } else if (estadoAno.equals("2007")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2007"));
                } else if (estadoAno.equals("2008")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2008"));
                } else if (estadoAno.equals("2009")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2009"));
                } else if (estadoAno.equals("2010")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2010"));
                } else if (estadoAno.equals("2011")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2011"));
                } else if (estadoAno.equals("2012")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2012"));
                } else if (estadoAno.equals("2013")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2013"));
                } else if (estadoAno.equals("2014")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2014"));
                } else if (estadoAno.equals("2015")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2015"));
                } else if (estadoAno.equals("2016")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2016"));
                } else if (estadoAno.equals("2017")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2017"));
                } else if (estadoAno.equals("2018")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2018"));
                } else if (estadoAno.equals("2019")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2019"));
                } else if (estadoAno.equals("2020")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2020"));
                } else if (estadoAno.equals("2021")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2021"));
                } else if (estadoAno.equals("2022")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2022"));
                } else if (estadoAno.equals("2023")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2023"));
                } else if (estadoAno.equals("2024")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2024"));
                } else if (estadoAno.equals("2025")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("2025"));
                } else if (estadoAno.equalsIgnoreCase("TODOS LOS AÑOS")) {
                    nuevoRegistroMensajesUsuarios.setAno(new Short("0"));
                }
            } else {

                nuevoRegistroMensajesUsuarios.setAno(null);
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoAno");
        } else {
            if (estadoAno != null) {
                if (estadoAno.equals("2005")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2005"));
                } else if (estadoAno.equals("2006")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2006"));
                } else if (estadoAno.equals("2007")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2007"));
                } else if (estadoAno.equals("2008")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2008"));
                } else if (estadoAno.equals("2009")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2009"));
                } else if (estadoAno.equals("2010")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2010"));
                } else if (estadoAno.equals("2011")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2011"));
                } else if (estadoAno.equals("2012")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2012"));
                } else if (estadoAno.equals("2013")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2013"));
                } else if (estadoAno.equals("2014")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2014"));
                } else if (estadoAno.equals("2015")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2015"));
                } else if (estadoAno.equals("2016")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2016"));
                } else if (estadoAno.equals("2017")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2017"));
                } else if (estadoAno.equals("2018")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2018"));
                } else if (estadoAno.equals("2019")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2019"));
                } else if (estadoAno.equals("2020")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2020"));
                } else if (estadoAno.equals("2021")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2021"));
                } else if (estadoAno.equals("2022")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2022"));
                } else if (estadoAno.equals("2023")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2023"));
                } else if (estadoAno.equals("2024")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2024"));
                } else if (estadoAno.equals("2025")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("2025"));
                } else if (estadoAno.equalsIgnoreCase("TODOS LOS AÑOS")) {
                    duplicarRegistroMensajesUsuarios.setAno(new Short("0"));
                }
            } else {

                duplicarRegistroMensajesUsuarios.setAno(null);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarAno");
        }

    }

    public void seleccionarTipoNuevoMes(String estadoMes, int tipoNuevo) {

        if (tipoNuevo == 1) {
            if (estadoMes != null) {

                if (estadoMes.equalsIgnoreCase("ENERO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("1"));
                } else if (estadoMes.equalsIgnoreCase("FEBRERO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("2"));
                } else if (estadoMes.equalsIgnoreCase("MARZO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("3"));
                } else if (estadoMes.equalsIgnoreCase("ABRIL")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("4"));
                } else if (estadoMes.equalsIgnoreCase("MAYO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("5"));
                } else if (estadoMes.equalsIgnoreCase("JUNIO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("6"));
                } else if (estadoMes.equalsIgnoreCase("JULIO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("7"));
                } else if (estadoMes.equalsIgnoreCase("AGOSTO")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("8"));
                } else if (estadoMes.equalsIgnoreCase("SEPTIEMBRE")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("9"));
                } else if (estadoMes.equalsIgnoreCase("OCTUBRE")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("10"));
                } else if (estadoMes.equalsIgnoreCase("NOVIEMBRE")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("11"));
                } else if (estadoMes.equalsIgnoreCase("DICIEMBRE")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("12"));
                } else if (estadoMes.equalsIgnoreCase("TODOS LOS MESES")) {
                    nuevoRegistroMensajesUsuarios.setMes(new Short("0"));
                }
            } else {
                nuevoRegistroMensajesUsuarios.setMes(null);
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoMes");
        } else {
            if (estadoMes != null) {
                if (estadoMes.equalsIgnoreCase("ENERO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("1"));
                } else if (estadoMes.equalsIgnoreCase("FEBRERO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("2"));
                } else if (estadoMes.equalsIgnoreCase("MARZO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("3"));
                } else if (estadoMes.equalsIgnoreCase("ABRIL")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("4"));
                } else if (estadoMes.equalsIgnoreCase("MAYO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("5"));
                } else if (estadoMes.equalsIgnoreCase("JUNIO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("6"));
                } else if (estadoMes.equalsIgnoreCase("JULIO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("7"));
                } else if (estadoMes.equalsIgnoreCase("AGOSTO")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("8"));
                } else if (estadoMes.equalsIgnoreCase("SEPTIEMBRE")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("9"));
                } else if (estadoMes.equalsIgnoreCase("OCTUBRE")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("10"));
                } else if (estadoMes.equalsIgnoreCase("NOVIEMBRE")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("11"));
                } else if (estadoMes.equalsIgnoreCase("DICIEMBRE")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("12"));
                } else if (estadoMes.equalsIgnoreCase("TODOS LOS MESES")) {
                    duplicarRegistroMensajesUsuarios.setMes(new Short("0"));
                }
            } else {
                duplicarRegistroMensajesUsuarios.setMes(null);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarMes");
        }

    }

    public void seleccionarTipoNuevoDia(String estadoDia, int tipoNuevo) {

        if (tipoNuevo == 1) {
            if (estadoDia != null) {

                if (estadoDia.equalsIgnoreCase("01")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("1"));
                } else if (estadoDia.equalsIgnoreCase("02")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("2"));
                } else if (estadoDia.equalsIgnoreCase("03")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("3"));
                } else if (estadoDia.equalsIgnoreCase("04")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("4"));
                } else if (estadoDia.equalsIgnoreCase("05")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("5"));
                } else if (estadoDia.equalsIgnoreCase("06")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("6"));
                } else if (estadoDia.equalsIgnoreCase("07")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("7"));
                } else if (estadoDia.equalsIgnoreCase("08")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("8"));
                } else if (estadoDia.equalsIgnoreCase("09")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("9"));
                } else if (estadoDia.equalsIgnoreCase("10")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("10"));
                } else if (estadoDia.equalsIgnoreCase("11")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("11"));
                } else if (estadoDia.equalsIgnoreCase("12")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("12"));
                } else if (estadoDia.equalsIgnoreCase("13")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("13"));
                } else if (estadoDia.equalsIgnoreCase("14")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("14"));
                } else if (estadoDia.equalsIgnoreCase("15")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("15"));
                } else if (estadoDia.equalsIgnoreCase("16")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("16"));
                } else if (estadoDia.equalsIgnoreCase("17")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("17"));
                } else if (estadoDia.equalsIgnoreCase("18")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("18"));
                } else if (estadoDia.equalsIgnoreCase("19")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("19"));
                } else if (estadoDia.equalsIgnoreCase("20")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("20"));
                } else if (estadoDia.equalsIgnoreCase("21")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("21"));
                } else if (estadoDia.equalsIgnoreCase("22")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("22"));
                } else if (estadoDia.equalsIgnoreCase("23")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("23"));
                } else if (estadoDia.equalsIgnoreCase("24")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("24"));
                } else if (estadoDia.equalsIgnoreCase("25")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("25"));
                } else if (estadoDia.equalsIgnoreCase("26")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("26"));
                } else if (estadoDia.equalsIgnoreCase("27")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("27"));
                } else if (estadoDia.equalsIgnoreCase("28")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("28"));
                } else if (estadoDia.equalsIgnoreCase("29")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("29"));
                } else if (estadoDia.equalsIgnoreCase("30")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("30"));
                } else if (estadoDia.equalsIgnoreCase("31")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("31"));
                } else if (estadoDia.equalsIgnoreCase("TODOS LOS DIAS")) {
                    nuevoRegistroMensajesUsuarios.setDia(new Short("0"));
                }
            } else {
                nuevoRegistroMensajesUsuarios.setDia(null);
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoDia");
        } else {
            if (estadoDia != null) {
                if (estadoDia.equalsIgnoreCase("01")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("1"));
                } else if (estadoDia.equalsIgnoreCase("02")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("2"));
                } else if (estadoDia.equalsIgnoreCase("03")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("3"));
                } else if (estadoDia.equalsIgnoreCase("04")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("4"));
                } else if (estadoDia.equalsIgnoreCase("05")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("5"));
                } else if (estadoDia.equalsIgnoreCase("06")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("6"));
                } else if (estadoDia.equalsIgnoreCase("07")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("7"));
                } else if (estadoDia.equalsIgnoreCase("08")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("8"));
                } else if (estadoDia.equalsIgnoreCase("09")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("9"));
                } else if (estadoDia.equalsIgnoreCase("10")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("10"));
                } else if (estadoDia.equalsIgnoreCase("11")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("11"));
                } else if (estadoDia.equalsIgnoreCase("12")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("12"));
                } else if (estadoDia.equalsIgnoreCase("13")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("13"));
                } else if (estadoDia.equalsIgnoreCase("14")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("14"));
                } else if (estadoDia.equalsIgnoreCase("15")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("15"));
                } else if (estadoDia.equalsIgnoreCase("16")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("16"));
                } else if (estadoDia.equalsIgnoreCase("17")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("17"));
                } else if (estadoDia.equalsIgnoreCase("18")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("18"));
                } else if (estadoDia.equalsIgnoreCase("19")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("19"));
                } else if (estadoDia.equalsIgnoreCase("20")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("20"));
                } else if (estadoDia.equalsIgnoreCase("21")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("21"));
                } else if (estadoDia.equalsIgnoreCase("22")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("22"));
                } else if (estadoDia.equalsIgnoreCase("23")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("23"));
                } else if (estadoDia.equalsIgnoreCase("24")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("24"));
                } else if (estadoDia.equalsIgnoreCase("25")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("25"));
                } else if (estadoDia.equalsIgnoreCase("26")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("26"));
                } else if (estadoDia.equalsIgnoreCase("27")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("27"));
                } else if (estadoDia.equalsIgnoreCase("28")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("28"));
                } else if (estadoDia.equalsIgnoreCase("29")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("29"));
                } else if (estadoDia.equalsIgnoreCase("30")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("30"));
                } else if (estadoDia.equalsIgnoreCase("31")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("31"));
                } else if (estadoDia.equalsIgnoreCase("TODOS LOS DIAS")) {
                    duplicarRegistroMensajesUsuarios.setDia(new Short("0"));
                }
            } else {
                duplicarRegistroMensajesUsuarios.setDia(null);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarDia");
        }

    }

    //BORRAR VIGENCIA FORMAL
    public void borrarProverbio() {

        if (index >= 0 && CualTabla == 0) {
            if (tipoLista == 0) {
                if (!listaProverbiosModificar.isEmpty() && listaProverbiosModificar.contains(listaProverbios.get(index))) {
                    int modIndex = listaProverbiosModificar.indexOf(listaProverbios.get(index));
                    listaProverbiosModificar.remove(modIndex);
                    listaProverbiosBorrar.add(listaProverbios.get(index));
                } else if (!listaProverbiosCrear.isEmpty() && listaProverbiosCrear.contains(listaProverbios.get(index))) {
                    int crearIndex = listaProverbiosCrear.indexOf(listaProverbios.get(index));
                    listaProverbiosCrear.remove(crearIndex);
                } else {
                    listaProverbiosBorrar.add(listaProverbios.get(index));
                }
                listaProverbios.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaProverbiosModificar.isEmpty() && listaProverbiosModificar.contains(filtradosListaProverbios.get(index))) {
                    int modIndex = listaProverbiosModificar.indexOf(filtradosListaProverbios.get(index));
                    listaProverbiosModificar.remove(modIndex);
                    listaProverbiosBorrar.add(filtradosListaProverbios.get(index));
                } else if (!listaProverbiosCrear.isEmpty() && listaProverbiosCrear.contains(filtradosListaProverbios.get(index))) {
                    int crearIndex = listaProverbiosCrear.indexOf(filtradosListaProverbios.get(index));
                    listaProverbiosCrear.remove(crearIndex);
                } else {
                    listaProverbiosBorrar.add(filtradosListaProverbios.get(index));
                }
                int CIndex = listaProverbios.indexOf(filtradosListaProverbios.get(index));
                listaProverbios.remove(CIndex);
                filtradosListaProverbios.remove(index);

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosProverbios");
            index = -1;
            secRegistro = null;
            cambiosPagina = false;
            context.update("form:ACEPTAR");

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        } else if (indexNF >= 0 && CualTabla == 1) {

            if (tipoListaNF == 0) {
                if (!listaMensajesUsuariosModificar.isEmpty() && listaMensajesUsuariosModificar.contains(listaMensajesUsuariosModificar.get(indexNF))) {
                    int modIndex = listaMensajesUsuariosModificar.indexOf(listaMensajesUsuario.get(indexNF));
                    listaMensajesUsuariosModificar.remove(modIndex);
                    listaMensajesUsuariosBorrar.add(listaMensajesUsuario.get(indexNF));
                } else if (!listaMensajesUsuariosCrear.isEmpty() && listaMensajesUsuariosCrear.contains(listaMensajesUsuario.get(indexNF))) {
                    int crearIndex = listaMensajesUsuariosCrear.indexOf(listaMensajesUsuario.get(indexNF));
                    listaMensajesUsuariosCrear.remove(crearIndex);
                } else {
                    listaMensajesUsuariosBorrar.add(listaMensajesUsuario.get(indexNF));
                }
                listaMensajesUsuario.remove(indexNF);
            }

            if (tipoListaNF == 1) {
                if (!listaMensajesUsuariosModificar.isEmpty() && listaMensajesUsuariosModificar.contains(filtradosListaMensajesUsuario.get(indexNF))) {
                    int modIndex = listaMensajesUsuariosModificar.indexOf(filtradosListaMensajesUsuario.get(indexNF));
                    listaMensajesUsuariosModificar.remove(modIndex);
                    listaMensajesUsuariosBorrar.add(filtradosListaMensajesUsuario.get(indexNF));
                } else if (!listaMensajesUsuariosCrear.isEmpty() && listaMensajesUsuariosCrear.contains(filtradosListaMensajesUsuario.get(indexNF))) {
                    int crearIndex = listaMensajesUsuariosCrear.indexOf(filtradosListaMensajesUsuario.get(indexNF));
                    listaMensajesUsuariosCrear.remove(crearIndex);
                } else {
                    listaMensajesUsuariosBorrar.add(filtradosListaMensajesUsuario.get(indexNF));
                }
                int CIndex = listaMensajesUsuario.indexOf(filtradosListaMensajesUsuario.get(indexNF));
                listaMensajesUsuario.remove(CIndex);
                filtradosListaMensajesUsuario.remove(indexNF);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosMensajesUsuarios");
            cambiosPagina = false;
            context.update("form:ACEPTAR");
            indexNF = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }
    }

    public void verificarRastro() {
        if (CualTabla == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (!listaProverbios.isEmpty()) {
                if (secRegistro != null) {
                    int resultado = administrarRastros.obtenerTabla(secRegistro, "recordatorios");
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
                if (administrarRastros.verificarHistoricosTabla("RECORDATORIOS")) {
                    context.execute("confirmarRastroHistorico.show()");
                } else {
                    context.execute("errorRastroHistorico.show()");
                }

            }
            index = -1;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (!listaMensajesUsuario.isEmpty()) {
                if (secRegistro != null) {
                    int resultadoNF = administrarRastros.obtenerTabla(secRegistro, "RECORDATORIOS");
                    if (resultadoNF == 1) {
                        context.execute("errorObjetosDBNF.show()");
                    } else if (resultadoNF == 2) {
                        context.execute("confirmarRastroNF.show()");
                    } else if (resultadoNF == 3) {
                        context.execute("errorRegistroRastroNF.show()");
                    } else if (resultadoNF == 4) {
                        context.execute("errorTablaConRastroNF.show()");
                    } else if (resultadoNF == 5) {
                        context.execute("errorTablaSinRastroNF.show()");
                    }
                } else {
                    context.execute("seleccionarRegistroNF.show()");
                }
            } else {
                if (administrarRastros.verificarHistoricosTabla("RECORDATORIOS")) {
                    context.execute("confirmarRastroHistoricoNF.show()");
                } else {
                    context.execute("errorRastroHistoricoNF.show()");
                }

            }
            index = -1;
        }

    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevoMensajeUsuario() {
        nuevoRegistroMensajesUsuarios = new Recordatorios();
        nuevoRegistroMensajesUsuarios.setTipo("RECORDATORIO");

        index = -1;
        secRegistro = null;

    }

    //LIMPIAR DUPLICAR
    public void limpiarDuplicarProverbio() {
        duplicarProverbio = new Recordatorios();
    }
    //LIMPIAR DUPLICAR NO FORMAL

    public void limpiarDuplicarRegistroMensajeUsuario() {
        duplicarRegistroMensajesUsuarios = new Recordatorios();
    }

    public void confirmarDuplicar() {

        listaProverbios.add(duplicarProverbio);
        listaProverbiosCrear.add(duplicarProverbio);
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = false;
        context.update("form:ACEPTAR");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "115";
            pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
            pMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProverbios");
            bandera = 0;
            filtradosListaProverbios = null;
            tipoLista = 0;

        }
        context.update("form:datosProverbios");
        duplicarProverbio = new Recordatorios();
        context.update("formularioDialogos:DuplicarRegistroProverbio");
        context.execute("DuplicarRegistroProverbio.hide()");

    }

    public void confirmarDuplicarNF() {

        listaMensajesUsuario.add(duplicarRegistroMensajesUsuarios);
        listaMensajesUsuariosCrear.add(duplicarRegistroMensajesUsuarios);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosMensajesUsuarios");
        indexNF = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaNF = "115";
            mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
            mAno.setFilterStyle("display: none; visibility: hidden;");
            mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
            mMes.setFilterStyle("display: none; visibility: hidden;");
            mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
            mDia.setFilterStyle("display: none; visibility: hidden;");
            mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
            mMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
            banderaNF = 0;
            filtradosListaMensajesUsuario = null;
            tipoListaNF = 0;

        }
        context.update("form:DuplicarRegistroMensajeUsuario");
        duplicarRegistroMensajesUsuarios = new Recordatorios();
        context.update("formularioDialogos:duplicarRegistroMensajeUsuario");
        context.execute("DuplicarRegistroMensajeUsuario.hide()");

    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {

        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "115";
            pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
            pMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProverbios");
            bandera = 0;
            filtradosListaProverbios = null;
            tipoLista = 0;
        }

        if (banderaNF == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaNF = "115";
            mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
            mAno.setFilterStyle("display: none; visibility: hidden;");
            mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
            mMes.setFilterStyle("display: none; visibility: hidden;");
            mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
            mDia.setFilterStyle("display: none; visibility: hidden;");
            mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
            mMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
            banderaNF = 0;
            filtradosListaMensajesUsuario = null;
            tipoListaNF = 0;
        }
        listaProverbiosBorrar.clear();
        listaProverbiosCrear.clear();
        listaProverbiosModificar.clear();
        index = -1;
        secRegistro = null;

        listaProverbios = null;

        listaMensajesUsuariosBorrar.clear();
        listaMensajesUsuariosCrear.clear();
        listaMensajesUsuariosModificar.clear();
        indexNF = -1;

        listaMensajesUsuario = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        cambiosPagina = true;
        context.update("form:ACEPTAR");
        context.update("form:datosProverbios");
        context.update("form:datosMensajesUsuarios");
    }

    public void salir() {

        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "115";
            pMensaje = (Column) c.getViewRoot().findComponent("form:datosProverbios:pMensaje");
            pMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosProverbios");
            bandera = 0;
            filtradosListaProverbios = null;
            tipoLista = 0;
        }

        if (banderaNF == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            altoTablaNF = "115";
            mAno = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mAno");
            mAno.setFilterStyle("display: none; visibility: hidden;");
            mMes = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMes");
            mMes.setFilterStyle("display: none; visibility: hidden;");
            mDia = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mDia");
            mDia.setFilterStyle("display: none; visibility: hidden;");
            mMensaje = (Column) c.getViewRoot().findComponent("form:datosMensajesUsuarios:mMensaje");
            mMensaje.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosMensajesUsuarios");
            banderaNF = 0;
            filtradosListaMensajesUsuario = null;
            tipoListaNF = 0;
        }

        listaProverbiosBorrar.clear();
        listaProverbiosCrear.clear();
        listaProverbiosModificar.clear();
        index = -1;
        secRegistro = null;

        listaProverbios = null;

        listaMensajesUsuariosBorrar.clear();
        listaMensajesUsuariosCrear.clear();
        listaMensajesUsuariosModificar.clear();
        indexNF = -1;

        listaMensajesUsuario = null;
        guardado = true;
        permitirIndex = true;
        cambiosPagina = true;
    }

    //Getter & Setters
    public Recordatorios getEditarProverbios() {
        return editarProverbios;
    }

    public void setEditarProverbios(Recordatorios editarProverbios) {
        this.editarProverbios = editarProverbios;
    }

    public List<Recordatorios> getListaProverbios() {
        if (listaProverbios == null) {
            listaProverbios = administrarRecordatorios.recordatorios();
            if (!listaProverbios.isEmpty()) {
                proverbioSeleccionado = listaProverbios.get(0);
            }
        }
        return listaProverbios;
    }

    public void setListaProverbios(List<Recordatorios> listaProverbios) {
        this.listaProverbios = listaProverbios;
    }

    public List<Recordatorios> getFiltradosListaProverbios() {
        return filtradosListaProverbios;
    }

    public void setFiltradosListaProverbios(List<Recordatorios> filtradosListaProverbios) {
        this.filtradosListaProverbios = filtradosListaProverbios;
    }

    public List<Recordatorios> getListaMensajesUsuario() {
        if (listaMensajesUsuario == null) {
            listaMensajesUsuario = administrarRecordatorios.mensajesRecordatorios();
            if (!listaMensajesUsuario.isEmpty()) {
                mensajeUsuarioSeleccionado = listaMensajesUsuario.get(0);
            }
        }
        return listaMensajesUsuario;
    }

    public void setListaMensajesUsuario(List<Recordatorios> listaMensajesUsuario) {
        this.listaMensajesUsuario = listaMensajesUsuario;
    }

    public List<Recordatorios> getFiltradosListaMensajesUsuario() {
        return filtradosListaMensajesUsuario;
    }

    public void setFiltradosListaMensajesUsuario(List<Recordatorios> filtradosListaMensajesUsuario) {
        this.filtradosListaMensajesUsuario = filtradosListaMensajesUsuario;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getAltoTablaNF() {
        return altoTablaNF;
    }

    public void setAltoTablaNF(String altoTablaNF) {
        this.altoTablaNF = altoTablaNF;
    }

    public void setTablaImprimir(String tablaImprimir) {
        this.tablaImprimir = tablaImprimir;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public Recordatorios getNuevoProverbio() {
        return nuevoProverbio;
    }

    public void setNuevoProverbio(Recordatorios nuevoProverbio) {
        this.nuevoProverbio = nuevoProverbio;
    }

    public Recordatorios getDuplicarProverbio() {
        return duplicarProverbio;
    }

    public void setDuplicarProverbio(Recordatorios duplicarProverbio) {
        this.duplicarProverbio = duplicarProverbio;
    }

    public Recordatorios getNuevoRegistroMensajesUsuarios() {
        return nuevoRegistroMensajesUsuarios;
    }

    public void setNuevoRegistroMensajesUsuarios(Recordatorios nuevoRegistroMensajesUsuarios) {
        this.nuevoRegistroMensajesUsuarios = nuevoRegistroMensajesUsuarios;
    }

    public Recordatorios getDuplicarRegistroMensajesUsuarios() {
        return duplicarRegistroMensajesUsuarios;
    }

    public void setDuplicarRegistroMensajesUsuarios(Recordatorios duplicarRegistroMensajesUsuarios) {
        this.duplicarRegistroMensajesUsuarios = duplicarRegistroMensajesUsuarios;
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

    public Recordatorios getProverbioSeleccionado() {
        return proverbioSeleccionado;
    }

    public void setProverbioSeleccionado(Recordatorios proverbioSeleccionado) {
        this.proverbioSeleccionado = proverbioSeleccionado;
    }

    public Recordatorios getMensajeUsuarioSeleccionado() {
        return mensajeUsuarioSeleccionado;
    }

    public void setMensajeUsuarioSeleccionado(Recordatorios mensajeUsuarioSeleccionado) {
        this.mensajeUsuarioSeleccionado = mensajeUsuarioSeleccionado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

}

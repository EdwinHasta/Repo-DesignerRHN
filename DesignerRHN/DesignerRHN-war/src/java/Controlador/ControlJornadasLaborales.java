/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Jornadas;
import Entidades.JornadasLaborales;
import Entidades.JornadasSemanales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarJornadasLaboralesInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Administrador
 */
@ManagedBean
@SessionScoped
public class ControlJornadasLaborales implements Serializable {

    @EJB
    AdministrarJornadasLaboralesInterface administrarJornadasLaborales;
    @EJB
    AdministrarRastrosInterface administrarRastros;

    //SECUENCIA DE LA JORNADA
    private BigInteger secuenciaJornada;
    //LISTA DE JORNADA LABORAL
    private List<JornadasLaborales> listaJornadasLaborales;
    private List<JornadasLaborales> filtradoListaJornadasLaborales;
    private JornadasLaborales jornadaLaboralSeleccionada;

    //LISTA DE JORNADA SEMANAL
    private List<JornadasSemanales> listaJornadasSemanales;
    private List<JornadasSemanales> filtradoListaJornadasSemanales;
    private JornadasSemanales jornadaSemanalSeleccionada;

    //LISTA DE VALORES DE JORNADAS FALTA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private List<Jornadas> lovJornadas;
    private List<Jornadas> lovFiltradoJornadas;
    private Jornadas jornadaSeleccionada;
    //Otros
    private boolean aceptar;
    private int index;
    private int indexJS;
    private int tipoActualizacion;
    private boolean permitirIndex;
    private int tipoLista;
    private int cualCelda;
    private int tipoListaJS;
    private int CualTabla;

    //Tabla a Imprimir
    private String tablaImprimir, nombreArchivo;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    private int banderaJS;
    //Modificar Jornadas Laborales
    private List<JornadasLaborales> listaJornadasLaboralesModificar;
    private boolean guardado, guardarOk;
    //Crear Jornadas Laborales
    public JornadasLaborales nuevaJornadaLaboral;
    private List<JornadasLaborales> listaJornadasLaboralesCrear;
    private BigInteger l;
    private int k;
    private String mensajeValidacion;
    private String mensajeValidacionhoras;

    //Borrar Jornadas Laborales
    private List<JornadasLaborales> listaJornadasLaboralesBorrar;
    //Modificar Semana Laboral
    private List<JornadasSemanales> listaJornadasSemanalesModificar;
    //Crear Semana Laboral
    private JornadasSemanales nuevaSemanaLaboral;
    private List<JornadasSemanales> listaJornadasSemanalesCrear;
    private int m;
    private BigInteger n;
    private String mensajeValidacionJS;
    //Borrar Semana Laboral
    private List<JornadasSemanales> listaJornadasSemanalesBorrar;
    //AUTOCOMPLETAR
    private String jornada;
    //editar celda
    private JornadasLaborales editarJornadaLaboral;
    private JornadasSemanales editarJornadaSemanal;
    private boolean cambioEditor, aceptarEditar;
    //DUPLICAR
    private JornadasLaborales duplicarJornadaLaboral;
    private JornadasSemanales duplicarSemanaLaboral;
    //RASTRO
    private BigInteger secRegistro;
    //Cual Insertar
    private String cualInsertar;
    //Cual Nuevo Update
    private String cualNuevo;
    public String altoTabla;
    public String altoTabla2;
    public String infoRegistroJornadas;
    public String infoRegistroJL;
    public String infoRegistroSL;
    //Columnas Tabla Jornadas Laborales
    private Column jornadasLaboralesCodigos, jornadasLaboralesDescripcion, jornadasLaboralesHorasDiarias, jornadasLaboralesHorasMensuales, jornadasLaboralesRotativo, jornadasLaboralesTurnoRelativo, jornadasLaboralesCuadrillaHorasDiarias, jornadasLaboralesJornadas;
    //Columnas Tabla Semana Laboral
    private Column SemanaLaboralDia, SemanaLaboralHI, SemanaLaboralMI, SemanaLaboralHIA, SemanaLaboralMIA, SemanaLaboralHFA, SemanaLaboralMFA, SemanaLaboralHoraFinal, SemanaLaboralMinutoFinal;
    public String infoRegistro;
    ///////////////////////////////////////////////
    ///////////PRUEBAS UNITARIAS COMPONENTES///////
    ///////////////////////////////////////////////
    public String buscarNombre;
    public boolean buscador;
    public String paginaAnterior;

    public ControlJornadasLaborales() {
        permitirIndex = true;
        aceptar = true;
        tipoLista = 0;
        duplicarSemanaLaboral = new JornadasSemanales();
        listaJornadasLaboralesBorrar = new ArrayList<JornadasLaborales>();
        listaJornadasLaboralesCrear = new ArrayList<JornadasLaborales>();
        listaJornadasLaboralesModificar = new ArrayList<JornadasLaborales>();
        listaJornadasSemanalesBorrar = new ArrayList<JornadasSemanales>();
        listaJornadasSemanalesCrear = new ArrayList<JornadasSemanales>();
        listaJornadasSemanalesModificar = new ArrayList<JornadasSemanales>();
        lovJornadas = new ArrayList<Jornadas>();
        //editar
        editarJornadaLaboral = new JornadasLaborales();
        editarJornadaSemanal = new JornadasSemanales();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        tipoListaJS = 0;
        nuevaJornadaLaboral = new JornadasLaborales();
        nuevaJornadaLaboral.setJornada(new Jornadas());
        nuevaSemanaLaboral = new JornadasSemanales();
        //duplicar
        duplicarJornadaLaboral = new JornadasLaborales();
        duplicarJornadaLaboral.setJornada(new Jornadas());
        duplicarSemanaLaboral = new JornadasSemanales();

        secRegistro = null;
        k = 0;
        altoTabla = "115";
        altoTabla2 = "115";
        guardado = true;
        tablaImprimir = ":formExportar:datosJornadasLaboralesExportar";
        nombreArchivo = "JornadasLaboralesXML";
        buscador = false;

    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarJornadasLaborales.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
            getListaJornadasLaborales();
            if (listaJornadasLaborales != null) {
                infoRegistro = "Cantidad de registros : " + listaJornadasLaborales.size();
            } else {
                infoRegistro = "Cantidad de registros : 0";
            }
            if (listaJornadasSemanales == null || listaJornadasSemanales.isEmpty()) {
                infoRegistroSL = "Cantidad de registros: 0 ";
            } else {
                infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            }
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaEntrante(String pagina) {
        paginaAnterior = pagina;
    }

    public String redirigir() {
        return paginaAnterior;
    }

    //Ubicacion Celda. Tabla 1
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            CualTabla = 0;

            tablaImprimir = ":formExportar:datosJornadasLaboralesExportar";
            nombreArchivo = "JornadasLaboralesXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:exportarXML");
            if (tipoLista == 0) {
                secRegistro = listaJornadasLaborales.get(index).getSecuencia();
                if (cualCelda == 7) {
                    jornada = listaJornadasLaborales.get(index).getJornada().getDescripcion();
                }
            } else {
                secRegistro = filtradoListaJornadasLaborales.get(index).getSecuencia();
                if (cualCelda == 7) {
                    jornada = filtradoListaJornadasLaborales.get(index).getJornada().getDescripcion();
                }
            }
        }
    }

    //GUARDAR
    public void guardarTodo() {

        System.out.println("Guardado: " + guardado);
        System.out.println("Guardar en Primera Tabla: ");
        if (guardado == false) {
            System.out.println("Realizando Operaciones JornadasLaborales");
            if (!listaJornadasLaboralesBorrar.isEmpty()) {
                for (int i = 0; i < listaJornadasLaboralesBorrar.size(); i++) {
                    System.out.println("Borrando jornada semanal...");
                    if (listaJornadasLaboralesBorrar.get(i).getJornada().getSecuencia() == null) {
                        listaJornadasLaboralesBorrar.get(i).setJornada(null);
                        administrarJornadasLaborales.borrarJornadasLaborales(listaJornadasLaboralesBorrar);
                    } else {

                        administrarJornadasLaborales.borrarJornadasLaborales(listaJornadasLaboralesBorrar);
                    }
                    listaJornadasLaboralesBorrar.clear();
                }
            }
            if (!listaJornadasSemanalesBorrar.isEmpty()) {
                for (int i = 0; i < listaJornadasSemanalesBorrar.size(); i++) {
                    System.out.println("Borrando semana laboral...");

                    administrarJornadasLaborales.borrarJornadasSemanales(listaJornadasSemanalesBorrar);

                    System.out.println("Entra semana laboral");
                }
                listaJornadasSemanalesBorrar.clear();

            }
            if (!listaJornadasLaboralesCrear.isEmpty()) {
                for (int i = 0; i < listaJornadasLaboralesCrear.size(); i++) {
                    System.out.println("Creando jornada laboral...");
                    System.out.println(listaJornadasLaboralesCrear.size());

                    if (listaJornadasLaboralesCrear.get(i).getJornada().getSecuencia() == null) {
                        listaJornadasLaboralesCrear.get(i).setJornada(null);
                        administrarJornadasLaborales.crearJornadasLaborales(listaJornadasLaboralesCrear);
                    } else {
                        administrarJornadasLaborales.crearJornadasLaborales(listaJornadasLaboralesCrear);
                    }
                }
                System.out.println("LimpiaLista jornada laboral");
                listaJornadasLaboralesCrear.clear();
            }

            if (!listaJornadasSemanalesCrear.isEmpty()) {
                for (int i = 0; i < listaJornadasSemanalesCrear.size(); i++) {
                    System.out.println("Creando semana laboral...");
                    System.out.println(listaJornadasSemanalesCrear.size());

                    administrarJornadasLaborales.crearJornadasSemanales(listaJornadasSemanalesCrear);

                }

                System.out.println("LimpiaLista semana laboral");
                listaJornadasSemanalesCrear.clear();
            }

            if (!listaJornadasLaboralesModificar.isEmpty()) {
                administrarJornadasLaborales.modificarJornadasLaborales(listaJornadasLaboralesModificar);
                listaJornadasLaboralesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            RequestContext context = RequestContext.getCurrentInstance();
            getListaJornadasLaborales();
            if (listaJornadasLaborales != null && !listaJornadasLaborales.isEmpty()) {
                jornadaLaboralSeleccionada = listaJornadasLaborales.get(0);
                infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
            } else {
                infoRegistroJL = "Cantidad de registros: 0";
            }
            context.update("form:datosJornadasLaborales");
            context.update("form:infoRegistroJL");
            getListaJornadasSemanales();
            if (listaJornadasSemanales != null && !listaJornadasSemanales.isEmpty()) {
                jornadaSemanalSeleccionada = listaJornadasSemanales.get(0);
                infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            } else {
                infoRegistroSL = "Cantidad de registros: 0";
            }
            context.update("form:datosSemanasLaborales");
            context.update("form:infoRegistroSL");
            FacesMessage msg = new FacesMessage("Información", "Se guardaron los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");

        }

        indexJS = -1;
        secRegistro = null;
        index = -1;

    }

    //Ubicacion Celda Arriba 
    public void cambiarJornadaLaboral() {
        //Si ninguna de las 3 listas (crear,modificar,borrar) tiene algo, hace esto
        //{
        System.out.println("secuencia jornada seleccionada: " + jornadaLaboralSeleccionada.getSecuencia());
        if (listaJornadasSemanalesCrear.isEmpty() && listaJornadasSemanalesBorrar.isEmpty() && listaJornadasSemanalesModificar.isEmpty()) {
            secuenciaJornada = jornadaLaboralSeleccionada.getSecuencia();
            listaJornadasSemanales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            getListaJornadasSemanales();
            if (listaJornadasSemanales != null && !listaJornadasSemanales.isEmpty()) {
                jornadaSemanalSeleccionada = listaJornadasSemanales.get(0);
                infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            } else {
                infoRegistroSL = "Cantidad de registros: 0";
            }
            context.update("form:datosSemanasLaborales");
            context.update("form:infoRegistroSL");
        } else if (listaJornadasLaborales.get(index) != jornadaLaboralSeleccionada) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }
    }

    public void limpiarListas() {
        listaJornadasSemanalesCrear.clear();
        listaJornadasSemanalesBorrar.clear();
        listaJornadasSemanalesModificar.clear();
        secuenciaJornada = jornadaLaboralSeleccionada.getSecuencia();
        listaJornadasSemanales = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSemanasLaborales");
    }

    //Ubicacion Celda. Tabla 2
    public void cambiarIndiceJS(int indiceJS, int celdaJS) {
        if (permitirIndex == true) {
            indexJS = indiceJS;
            cualCelda = celdaJS;
            CualTabla = 1;
            tablaImprimir = ":formExportar:datosJornadasSemanalesExportar";
            cualNuevo = ":formularioDialogos:nuevaSemanaLaboral";
            cualInsertar = "formularioDialogos:NuevoRegistroJornadaSemanal";
            nombreArchivo = "JornadasSemanalesXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:exportarXML");

            if (tipoListaJS == 0) {
                secRegistro = listaJornadasSemanales.get(indexJS).getSecuencia();
            } else {
                secRegistro = filtradoListaJornadasSemanales.get(indexJS).getSecuencia();
            }
        }
    }

    //AUTOCOMPLETAR
    public void modificarJornadasLaborales(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaJornadasLaboralesCrear.contains(listaJornadasLaborales.get(indice))) {

                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(indice));
                    } else if (!listaJornadasLaboralesModificar.contains(listaJornadasLaborales.get(indice))) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaJornadasLaboralesCrear.contains(filtradoListaJornadasLaborales.get(indice))) {

                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(indice));
                    } else if (!listaJornadasLaboralesModificar.contains(filtradoListaJornadasLaborales.get(indice))) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosJornadasLaborales");
        } else if (confirmarCambio.equalsIgnoreCase("JORNADAS")) {
            if (tipoLista == 0) {
                listaJornadasLaborales.get(indice).getJornada().setDescripcion(jornada);
            } else {
                filtradoListaJornadasLaborales.get(indice).getJornada().setDescripcion(jornada);
            }
            for (int i = 0; i < lovJornadas.size(); i++) {
                if (lovJornadas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaJornadasLaborales.get(indice).setJornada(lovJornadas.get(indiceUnicoElemento));
                } else {
                    filtradoListaJornadasLaborales.get(indice).setJornada(lovJornadas.get(indiceUnicoElemento));
                }
                lovJornadas.clear();
                getLovJornadas();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:jornadasDialogo");
                context.execute("jornadasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaJornadasLaboralesCrear.contains(listaJornadasLaborales.get(indice))) {
                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(indice));
                    } else if (!listaJornadasLaboralesModificar.contains(listaJornadasLaborales.get(indice))) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaJornadasLaboralesCrear.contains(filtradoListaJornadasLaborales.get(indice))) {

                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(indice));
                    } else if (!listaJornadasLaboralesModificar.contains(filtradoListaJornadasLaborales.get(indice))) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosJornadasLaborales");
    }

    //AUTOCOMPLETAR SEGUNDA TABLA
    public void modificarJornadasSemanales(int indiceJS, String confirmarCambio, String valorConfirmar) {
        indexJS = indiceJS;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoListaJS == 0) {
                if (!listaJornadasSemanalesCrear.contains(listaJornadasSemanales.get(indiceJS))) {

                    if (listaJornadasSemanalesModificar.isEmpty()) {
                        listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                    } else if (!listaJornadasSemanalesModificar.contains(listaJornadasSemanales.get(indiceJS))) {
                        listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                indexJS = -1;
                secRegistro = null;

            } else {
                if (!listaJornadasSemanalesCrear.contains(filtradoListaJornadasLaborales.get(indiceJS))) {

                    if (listaJornadasSemanalesModificar.isEmpty()) {
                        listaJornadasSemanalesModificar.add(filtradoListaJornadasSemanales.get(indiceJS));
                    } else if (!listaJornadasSemanalesModificar.contains(filtradoListaJornadasSemanales.get(indiceJS))) {
                        listaJornadasSemanalesModificar.add(filtradoListaJornadasSemanales.get(indiceJS));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosSemanasLaborales");
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaJornadasSemanalesCrear.contains(listaJornadasSemanales.get(indiceJS))) {
                    if (listaJornadasSemanalesModificar.isEmpty()) {
                        listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                    } else if (!listaJornadasSemanalesModificar.contains(listaJornadasSemanales.get(indiceJS))) {
                        listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                indexJS = -1;
                secRegistro = null;
            } else {
                if (!listaJornadasSemanalesCrear.contains(filtradoListaJornadasSemanales.get(indiceJS))) {

                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(indiceJS));
                    } else if (!listaJornadasSemanalesModificar.contains(filtradoListaJornadasSemanales.get(indiceJS))) {
                        listaJornadasSemanalesModificar.add(filtradoListaJornadasSemanales.get(indiceJS));
                    }
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");

                    }
                }
                indexJS = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosSemanasLaborales");
    }

    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
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
            context.update("form:jornadasDialogo");
            context.execute("jornadasDialogo.show()");
        }
    }

    //MOSTRAR L.O.V JORNADAS   
    public void actualizarJornadas() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaJornadasLaborales.get(index).setJornada(jornadaSeleccionada);
                if (!listaJornadasLaboralesCrear.contains(listaJornadasLaborales.get(index))) {
                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(index));
                    } else if (!listaJornadasLaboralesModificar.contains(listaJornadasLaborales.get(index))) {
                        listaJornadasLaboralesModificar.add(listaJornadasLaborales.get(index));
                    }
                }
            } else {
                filtradoListaJornadasLaborales.get(index).setJornada(jornadaSeleccionada);
                if (!listaJornadasLaboralesCrear.contains(filtradoListaJornadasLaborales.get(index))) {
                    if (listaJornadasLaboralesModificar.isEmpty()) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(index));
                    } else if (!listaJornadasLaboralesModificar.contains(filtradoListaJornadasLaborales.get(index))) {
                        listaJornadasLaboralesModificar.add(filtradoListaJornadasLaborales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");

            }
            permitirIndex = true;
            context.update("form:datosJornadasLaborales");
        } else if (tipoActualizacion == 1) {
            nuevaJornadaLaboral.setJornada(jornadaSeleccionada);
            context.update("formularioDialogos:nuevaJornadaLaboral");
        } else if (tipoActualizacion == 2) {
            duplicarJornadaLaboral.setJornada(jornadaSeleccionada);
            context.update("formularioDialogos:duplicarJornadaLaboral");
        }
        lovFiltradoJornadas = null;
        jornadaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("jornadasDialogo.hide()");
        context.reset("formularioDialogos:LOVJornadas:globalFilter");
        context.update("formularioDialogos:LOVJornadas");
    }

    public void cancelarCambioJornadas() {
        lovFiltradoJornadas = null;
        jornadaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void seleccionarDia(String estadoDia, int indiceJS, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();

        if (tipoListaJS == 0) {
            if (estadoDia.equals("LUNES")) {
                listaJornadasSemanales.get(indiceJS).setDia("LUN");
            } else if (estadoDia.equals("MARTES")) {
                listaJornadasSemanales.get(indiceJS).setDia("MAR");
            } else if (estadoDia.equals("MIERCOLES")) {
                listaJornadasSemanales.get(indiceJS).setDia("MIE");
            } else if (estadoDia.equals("JUEVES")) {
                listaJornadasSemanales.get(indiceJS).setDia("JUE");
            } else if (estadoDia.equals("VIERNES")) {
                listaJornadasSemanales.get(indiceJS).setDia("VIE");
            } else if (estadoDia.equals("SABADO")) {
                listaJornadasSemanales.get(indiceJS).setDia("SAB");
            } else if (estadoDia.equals("DOMINGO")) {
                listaJornadasSemanales.get(indiceJS).setDia("DOM");
            } else if (estadoDia.equals("NADA")) {
                listaJornadasSemanales.get(indiceJS).setDia(" ");
            }

            if (!listaJornadasSemanalesCrear.contains(listaJornadasSemanales.get(indiceJS))) {
                if (listaJornadasSemanalesModificar.isEmpty()) {
                    listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                } else if (!listaJornadasSemanalesModificar.contains(listaJornadasSemanales.get(indiceJS))) {
                    listaJornadasSemanalesModificar.add(listaJornadasSemanales.get(indiceJS));
                }
            }
        } else {
            if (estadoDia.equals("LUNES")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("LUN");
            } else if (estadoDia.equals("MARTES")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("MAR");
            } else if (estadoDia.equals("MIERCOLES")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("MIE");
            } else if (estadoDia.equals("JUEVES")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("JUE");
            } else if (estadoDia.equals("VIERNES")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("VIE");
            } else if (estadoDia.equals("SABADO")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("SAB");
            } else if (estadoDia.equals("DOMINGO")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia("DOM");
            } else if (estadoDia.equals("NADA")) {
                filtradoListaJornadasSemanales.get(indiceJS).setDia(" ");
            }

            if (!listaJornadasSemanalesCrear.contains(filtradoListaJornadasSemanales.get(indiceJS))) {
                if (listaJornadasSemanalesModificar.isEmpty()) {
                    listaJornadasSemanalesModificar.add(filtradoListaJornadasSemanales.get(indiceJS));
                } else if (!listaJornadasSemanalesModificar.contains(filtradoListaJornadasSemanales.get(indiceJS))) {
                    listaJornadasSemanalesModificar.add(filtradoListaJornadasSemanales.get(indiceJS));
                }
            }
        }
        if (guardado == true) {
            guardado = false;
            context.update("form:ACEPTAR");
        }
        RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
    }

    public void seleccionarDiaNuevaSemana(String estadoDia, int tipoNuevo) {
        if (tipoNuevo == 1) {
            if (estadoDia.equals("LUNES")) {
                nuevaSemanaLaboral.setDia("LUN");
            } else if (estadoDia.equals("MARTES")) {
                nuevaSemanaLaboral.setDia("MAR");
            } else if (estadoDia.equals("MIERCOLES")) {
                nuevaSemanaLaboral.setDia("MIE");
            } else if (estadoDia.equals("JUEVES")) {
                nuevaSemanaLaboral.setDia("JUE");
            } else if (estadoDia.equals("VIERNES")) {
                nuevaSemanaLaboral.setDia("VIE");
            } else if (estadoDia.equals("SABADO")) {
                nuevaSemanaLaboral.setDia("SAB");
            } else if (estadoDia.equals("DOMINGO")) {
                nuevaSemanaLaboral.setDia("DOM");
            } else if (estadoDia.equals("NADA")) {
                nuevaSemanaLaboral.setDia(" ");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:nuevoDiaSemanaLaboral");
        } else {
            if (estadoDia.equals("LUNES")) {
                duplicarSemanaLaboral.setDia("LUN");
            } else if (estadoDia.equals("MARTES")) {
                duplicarSemanaLaboral.setDia("MAR");
            } else if (estadoDia.equals("MIERCOLES")) {
                duplicarSemanaLaboral.setDia("MIE");
            } else if (estadoDia.equals("JUEVES")) {
                duplicarSemanaLaboral.setDia("JUE");
            } else if (estadoDia.equals("VIERNES")) {
                duplicarSemanaLaboral.setDia("VIE");
            } else if (estadoDia.equals("SABADO")) {
                duplicarSemanaLaboral.setDia("SAB");
            } else if (estadoDia.equals("DOMINGO")) {
                duplicarSemanaLaboral.setDia("DOM");
            } else if (estadoDia.equals("NADA")) {
                duplicarSemanaLaboral.setDia(" ");
            }
            RequestContext.getCurrentInstance().update("formularioDialogos:duplicarDiaSemanaLaboral");
        }

    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistroJL = "Cantidad de registros: " + filtradoListaJornadasSemanales.size();
        context.update("form:infoRegistroJL");
    }

    //EVENTO FILTRARJS
    public void eventoFiltrarJS() {
        if (tipoListaJS == 0) {
            tipoListaJS = 1;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistroSL = "Cantidad de registros: " + filtradoListaJornadasSemanales.size();
        context.update("form:infoRegistroSL");
    }

    //MOSTRAR CELDA
    public void editarCelda() {
        if (index >= 0 && CualTabla == 0) {
            if (tipoLista == 0) {
                editarJornadaLaboral = listaJornadasLaborales.get(index);
            }
            if (tipoLista == 1) {
                editarJornadaLaboral = filtradoListaJornadasLaborales.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                System.out.println("Codigo: " + editarJornadaLaboral.getCodigo());
                context.update("formEditar:editarCodigo");
                context.execute("editarCodigo.show()");
                System.out.println("sh0w");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formEditar:editarDescripcion");
                context.execute("editarDescripcion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formEditar:editarHorasDiarias");
                context.execute("editarHorasDiarias.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formEditar:editarHorasMensuales");
                context.execute("editarHorasMensuales.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formEditar:editarJornada");
                context.execute("editarJornada.show()");
                cualCelda = -1;
            }
            index = -1;
        } else if (indexJS >= 0 && CualTabla == 1) {
            if (tipoListaJS == 0) {
                editarJornadaSemanal = listaJornadasSemanales.get(indexJS);
            }
            if (tipoListaJS == 1) {
                editarJornadaSemanal = filtradoListaJornadasSemanales.get(indexJS);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            System.out.println("Cual Tabla: " + CualTabla);
            if (cualCelda == 1) {
                context.update("formEditar:editarHoraInicialJS");
                context.execute("editarHoraInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formEditar:editarMinutoInicialJS");
                context.execute("editarMinutoInicialJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formEditar:editarHoraInicialAlimentacionJS");
                context.execute("editarHoraInicialAlimentacionJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formEditar:editarMinutoInicialAlimentacionJS");
                context.execute("editarMinutoInicialAlimentacionJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formEditar:editarHoraFinalAlimentacionJS");
                context.execute("editarHoraFinalAlimentacionJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formEditar:editarMinutoFinalAlimentacionJS");
                context.execute("editarMinutoFinalAlimentacionJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formEditar:editarHoraFinalJS");
                context.execute("editarHoraFinalJS.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formEditar:editarMinutoFinalJS");
                context.execute("editarMinutoFinalJS.show()");
                cualCelda = -1;
            }
            indexJS = -1;
        }
        secRegistro = null;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 7) {
                context.update("formularioDialogos:jornadasDialogo");
                context.execute("jornadasDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    //FILTRADO
    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        FacesContext c = FacesContext.getCurrentInstance();
        if (bandera == 0 && CualTabla == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
            jornadasLaboralesCodigos.setFilterStyle("width: 60px");
            jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
            jornadasLaboralesDescripcion.setFilterStyle("width: 90px");
            jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
            jornadasLaboralesHorasDiarias.setFilterStyle("width: 25px");
            jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
            jornadasLaboralesHorasMensuales.setFilterStyle("width: 25px");
            jornadasLaboralesRotativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesRotativo");
            jornadasLaboralesRotativo.setFilterStyle("width: 15px");
            jornadasLaboralesTurnoRelativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesTurnoRelativo");
            jornadasLaboralesTurnoRelativo.setFilterStyle("width: 15px");
            jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
            jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("width: 20" + "px");
            jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesJornadas");
            jornadasLaboralesJornadas.setFilterStyle("width: 60px");
            altoTabla = "91";
            RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
            bandera = 1;

        } else if (bandera == 1 && CualTabla == 0) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
            jornadasLaboralesCodigos.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
            jornadasLaboralesDescripcion.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
            jornadasLaboralesHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
            jornadasLaboralesHorasMensuales.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
            jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesJornadas");
            jornadasLaboralesJornadas.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "115";
            RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
            bandera = 0;
            filtradoListaJornadasLaborales = null;
            tipoLista = 0;
        } else if (banderaJS == 0 && CualTabla == 1) {

            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            SemanaLaboralDia = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralDia");
            SemanaLaboralDia.setFilterStyle("width: 40px");
            SemanaLaboralHI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHI");
            SemanaLaboralHI.setFilterStyle("width: 40px");
            SemanaLaboralMI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMI");
            SemanaLaboralMI.setFilterStyle("width: 40px");
            SemanaLaboralHIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHIA");
            SemanaLaboralHIA.setFilterStyle("width: 40px");
            SemanaLaboralMIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMIA");
            SemanaLaboralMIA.setFilterStyle("width: 40px");
            SemanaLaboralHFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHFA");
            SemanaLaboralHFA.setFilterStyle("width: 40px");
            SemanaLaboralMFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
            SemanaLaboralMFA.setFilterStyle("width: 40px");
            SemanaLaboralHoraFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHoraFinal");
            SemanaLaboralHoraFinal.setFilterStyle("width: 40px");
            SemanaLaboralMinutoFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMinutoFinal");
            SemanaLaboralMinutoFinal.setFilterStyle("width: 40px");
            altoTabla2 = "91";
            RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
            banderaJS = 1;

        } else if (banderaJS == 1 && CualTabla == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            SemanaLaboralDia = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralDia");
            SemanaLaboralDia.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHI");
            SemanaLaboralHI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMI");
            SemanaLaboralMI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHIA");
            SemanaLaboralHIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMIA");
            SemanaLaboralMIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHFA");
            SemanaLaboralHFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
            SemanaLaboralMFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHoraFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHoraFinal");
            SemanaLaboralHoraFinal.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMinutoFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMinutoFinal");
            SemanaLaboralMinutoFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
            banderaJS = 0;
            filtradoListaJornadasSemanales = null;
            tipoListaJS = 0;
        }
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //EXPORTAR PDF
    public void exportPDF() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasLaboralesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "JornadasLaboralesPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasSemanalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "SemanaLaboralPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexJS = -1;
            secRegistro = null;
        }
    }

    //EXPORTAR XLS
    public void exportXLS() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasLaboralesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "JornadasLaboralesXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosJornadasSemanalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "SemanaLaboralXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexJS = -1;
            secRegistro = null;
        }
    }

    // Agregar Nueva Jornada Laboral
    public void agregarNuevaJornadaLaboral() {

        RequestContext context = RequestContext.getCurrentInstance();
        int pasa = 0;
        mensajeValidacion = " ";
        mensajeValidacionhoras = " ";

        if (nuevaJornadaLaboral.getHorasdiarias() != null && (nuevaJornadaLaboral.getHorasdiarias().compareTo(BigDecimal.ONE) == 0 || nuevaJornadaLaboral.getHorasdiarias().compareTo(BigDecimal.ONE) == -1)) {
            mensajeValidacionhoras = mensajeValidacionhoras + " Horas Diarias. ";
            pasa++;
            context.update("formularioDialogos:validacionHorasJL");
            context.execute("validacionHorasJL.show()");
        }
        if (nuevaJornadaLaboral.getHorasmensuales() != null && (nuevaJornadaLaboral.getHorasmensuales().compareTo(Short.valueOf("0")) == 0 || nuevaJornadaLaboral.getHorasmensuales().compareTo(Short.valueOf("0")) < 0)) {
            mensajeValidacionhoras = mensajeValidacionhoras + " Horas Mensuale. ";
            pasa++;
            context.update("formularioDialogos:validacionHorasJL");
            context.execute("validacionHorasJL.show()");
        }
        if (nuevaJornadaLaboral.getCodigo() != null) {

            for (int i = 0; i < listaJornadasLaborales.size(); i++) {
                System.out.println("Lista Jornadas Laborales Posicion " + i + "Codigo: " + listaJornadasLaborales.get(i).getCodigo());
                System.out.println("Codigo NuevaJornadaLaboral:" + nuevaJornadaLaboral.getCodigo());
                if (nuevaJornadaLaboral.getCodigo() == listaJornadasLaborales.get(i).getCodigo()) {
                    pasa++;
                    context.update("formularioDialogos:validacionCodigo");
                    context.execute("validacionCodigo.show()");
                }
            }
        }
        if (nuevaJornadaLaboral.getDescripcion() == null || nuevaJornadaLaboral.getDescripcion().equals("")) {
            System.out.println("Entro a Descripción");
            mensajeValidacion = mensajeValidacion + " * Descripcion\n";
            pasa++;
        }

        if (pasa == 0) {
            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
                jornadasLaboralesCodigos.setFilterStyle("width: display: none; visibility: hidden;");
                jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
                jornadasLaboralesDescripcion.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
                jornadasLaboralesHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
                jornadasLaboralesHorasMensuales.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesRotativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesRotativo");
                jornadasLaboralesRotativo.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesTurnoRelativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesTurnoRelativo");
                jornadasLaboralesTurnoRelativo.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
                jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesJornadas");
                jornadasLaboralesJornadas.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "115";
                RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
                bandera = 0;
                filtradoListaJornadasLaborales = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA JORNADAS LABORALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaJornadaLaboral.setSecuencia(l);
            if (nuevaJornadaLaboral.isEstadoRotativo() == true) {
                nuevaJornadaLaboral.setRotativo("S");

            } else if (nuevaJornadaLaboral.isEstadoRotativo() == false) {
                nuevaJornadaLaboral.setRotativo("N");
            }

            if (nuevaJornadaLaboral.isEstadoTurnoRelativo() == true) {
                nuevaJornadaLaboral.setTurnorelativo("S");
            } else if (nuevaJornadaLaboral.isEstadoTurnoRelativo() == false) {
                nuevaJornadaLaboral.setTurnorelativo("N");
            }

            listaJornadasLaboralesCrear.add(nuevaJornadaLaboral);
            listaJornadasLaborales.add(nuevaJornadaLaboral);
            infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
            context.update("form:infoRegistroJL");
            nuevaJornadaLaboral = new JornadasLaborales();
            context.update("form:datosJornadasLaborales");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("formularioDialogos:NuevoRegistroJornadaLaboral");
            context.execute("NuevoRegistroJornadaLaboral.hide()");
            index = -1;
            secRegistro = null;
        } else if (nuevaJornadaLaboral.getDescripcion() == null || nuevaJornadaLaboral.getDescripcion().equals("")) {
            context.update("formularioDialogos:validacionNuevaJornadaLaboral");
            context.execute("validacionNuevaJornadaLaboral.show()");
        }
    }

    // Agregar Nueva Semana Laboral
    public void agregarNuevaSemanaLaboral() {

        RequestContext context = RequestContext.getCurrentInstance();
        int pasa = 0;
        mensajeValidacion = " ";
        if (nuevaSemanaLaboral.getDia() == null) {
            System.out.println("Entro a Dia");
            mensajeValidacion = mensajeValidacion + " * Dia\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getHorainicial() == null) {
            System.out.println("Entro a Hora Inicial");
            mensajeValidacion = mensajeValidacion + " * Hora Inicial\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getMinutoinicial() == null) {
            System.out.println("Entro a Minuto Inicial");
            mensajeValidacion = mensajeValidacion + " * Minuto Inicial\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getHorainicialalimentacion() == null) {
            System.out.println("Entro a Hora Inicial Alimentación");
            mensajeValidacion = mensajeValidacion + " * Hora Inicial Alimentacion\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getMinutoinicialalimentacion() == null) {
            System.out.println("Entro a Minuto Inicial Alimentación");
            mensajeValidacion = mensajeValidacion + " * Minuto Inicial Alimentacion\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getHorafinalalimentacion() == null) {
            System.out.println("Entro a Hora Final Alimentación");
            mensajeValidacion = mensajeValidacion + " * Hora Final Alimentacion\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getMinutofinalalimentacion() == null) {
            System.out.println("Entro a Minuto Final Alimentación");
            mensajeValidacion = mensajeValidacion + " * Minuto Final Alimentacion\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getHorafinal() == null) {
            System.out.println("Entro a Hora Final");
            mensajeValidacion = mensajeValidacion + " * Hora Final\n";
            pasa++;
        }
        if (nuevaSemanaLaboral.getMinutofinal() == null) {
            System.out.println("Entro a Minuto Final");
            mensajeValidacion = mensajeValidacion + " * Minuto Final\n";
            pasa++;
        }

        if (pasa == 0) {
            if (banderaJS == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoListaJS);
                SemanaLaboralDia = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralDia");
                SemanaLaboralDia.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralHI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHI");
                SemanaLaboralHI.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralMI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMI");
                SemanaLaboralMI.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralHIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHIA");
                SemanaLaboralHIA.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralMIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMIA");
                SemanaLaboralMIA.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralHFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHFA");
                SemanaLaboralHFA.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralMFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
                SemanaLaboralMFA.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralHoraFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHoraFinal");
                SemanaLaboralHoraFinal.setFilterStyle("display: none; visibility: hidden;");
                SemanaLaboralMinutoFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMinutoFinal");
                SemanaLaboralMinutoFinal.setFilterStyle("display: none; visibility: hidden;");
                altoTabla2 = "115";
                RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
                banderaJS = 0;
                filtradoListaJornadasSemanales = null;
                tipoListaJS = 0;

            }
            //AGREGAR REGISTRO A LA LISTA JORNADAS LABORALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaSemanaLaboral.setSecuencia(l);
            nuevaSemanaLaboral.setJornadalaboral(jornadaLaboralSeleccionada);

            listaJornadasSemanalesCrear.add(nuevaSemanaLaboral);

            listaJornadasSemanales.add(nuevaSemanaLaboral);
            nuevaSemanaLaboral = new JornadasSemanales();
            infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            context.update("form:infoRegistroSL");
            context.update("form:datosSemanasLaborales");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
            context.update("formularioDialogos:NuevoRegistroJornadaSemanal");
            context.execute("NuevoRegistroJornadaSemanal.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevaJornadaSemanal");
            context.execute("validacionNuevaJornadaSemanal.show()");
        }
    }

    public void chiste() {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Cual Tabla= " + CualTabla);
        System.out.println("listaJornadasLaborales: " + listaJornadasLaborales);
        System.out.println("listaSemanas: " + listaJornadasSemanales);
        if ((listaJornadasLaborales.isEmpty() || listaJornadasSemanales.isEmpty())) {
            context.update("formularioDialogos:elegirTabla");
            context.execute("elegirTabla.show()");
        } else if (CualTabla == 0) {
            context.update("formularioDialogos:NuevoRegistroJornadaLaboral");
            context.execute("NuevoRegistroJornadaLaboral.show()");
        } else if (CualTabla == 1) {
            context.update("formularioDialogos:NuevoRegistroJornadaSemanal");
            context.execute("NuevoRegistroJornadaSemanal.show()");
        }
    }

    //BORRAR JORNADA LABORAL
    public void borrarJornadaLaboral() {

        if (index >= 0 && CualTabla == 0) {
            if (listaJornadasSemanales == null || listaJornadasSemanales.isEmpty()) {
                if (tipoLista == 0) {
                    if (!listaJornadasLaboralesModificar.isEmpty() && listaJornadasLaboralesModificar.contains(listaJornadasLaborales.get(index))) {
                        int modIndex = listaJornadasLaboralesModificar.indexOf(listaJornadasLaborales.get(index));
                        listaJornadasLaboralesModificar.remove(modIndex);
                        listaJornadasLaboralesBorrar.add(listaJornadasLaborales.get(index));
                    } else if (!listaJornadasLaboralesCrear.isEmpty() && listaJornadasLaboralesCrear.contains(listaJornadasLaborales.get(index))) {
                        int crearIndex = listaJornadasLaboralesCrear.indexOf(listaJornadasLaborales.get(index));
                        listaJornadasLaboralesCrear.remove(crearIndex);
                    } else {
                        listaJornadasLaboralesBorrar.add(listaJornadasLaborales.get(index));
                    }
                    listaJornadasLaborales.remove(index);
                    infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
                }

                if (tipoLista == 1) {
                    if (!listaJornadasLaboralesModificar.isEmpty() && listaJornadasLaboralesModificar.contains(filtradoListaJornadasLaborales.get(index))) {
                        int modIndex = listaJornadasLaboralesModificar.indexOf(filtradoListaJornadasLaborales.get(index));
                        listaJornadasLaboralesModificar.remove(modIndex);
                        listaJornadasLaboralesBorrar.add(filtradoListaJornadasLaborales.get(index));
                    } else if (!listaJornadasLaboralesCrear.isEmpty() && listaJornadasLaboralesCrear.contains(filtradoListaJornadasLaborales.get(index))) {
                        int crearIndex = listaJornadasLaboralesCrear.indexOf(filtradoListaJornadasLaborales.get(index));
                        listaJornadasLaboralesCrear.remove(crearIndex);
                    } else {
                        listaJornadasLaboralesBorrar.add(filtradoListaJornadasLaborales.get(index));
                    }
                    int CIndex = listaJornadasLaborales.indexOf(filtradoListaJornadasLaborales.get(index));
                    listaJornadasLaborales.remove(CIndex);
                    filtradoListaJornadasLaborales.remove(index);
                    infoRegistroJL = "Cantidad de registros: " + filtradoListaJornadasLaborales.size();
                    System.out.println("Realizado");
                }

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosJornadasLaborales");
                context.update("form:infoRegistroJL");
                index = -1;
                secRegistro = null;

                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage("Advertencia", "No se puede realizar la operación porque tiene un valor asociado");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                context.update("form:growl");

            }

        } else if (indexJS >= 0 && CualTabla == 1) {

            if (tipoListaJS == 0) {
                if (!listaJornadasSemanalesModificar.isEmpty() && listaJornadasSemanalesModificar.contains(listaJornadasSemanales.get(indexJS))) {
                    int modIndex = listaJornadasSemanalesModificar.indexOf(listaJornadasSemanales.get(indexJS));
                    listaJornadasSemanalesModificar.remove(modIndex);
                    listaJornadasSemanalesBorrar.add(listaJornadasSemanales.get(indexJS));
                } else if (!listaJornadasSemanalesCrear.isEmpty() && listaJornadasSemanalesCrear.contains(listaJornadasSemanales.get(indexJS))) {
                    int crearIndex = listaJornadasSemanalesCrear.indexOf(listaJornadasSemanales.get(indexJS));
                    listaJornadasSemanalesCrear.remove(crearIndex);
                } else {
                    listaJornadasSemanalesBorrar.add(listaJornadasSemanales.get(indexJS));
                }
                listaJornadasSemanales.remove(indexJS);
                infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            }

            if (tipoListaJS == 1) {
                if (!listaJornadasSemanalesModificar.isEmpty() && listaJornadasSemanalesModificar.contains(filtradoListaJornadasSemanales.get(indexJS))) {
                    int modIndex = listaJornadasSemanalesModificar.indexOf(filtradoListaJornadasSemanales.get(indexJS));
                    listaJornadasSemanalesModificar.remove(modIndex);
                    listaJornadasSemanalesBorrar.add(filtradoListaJornadasSemanales.get(indexJS));
                } else if (!listaJornadasSemanalesCrear.isEmpty() && listaJornadasSemanalesCrear.contains(filtradoListaJornadasSemanales.get(indexJS))) {
                    int crearIndex = listaJornadasSemanalesCrear.indexOf(filtradoListaJornadasSemanales.get(indexJS));
                    listaJornadasSemanalesCrear.remove(crearIndex);
                } else {
                    listaJornadasSemanalesBorrar.add(filtradoListaJornadasSemanales.get(indexJS));
                }
                int CIndex = listaJornadasSemanales.indexOf(filtradoListaJornadasSemanales.get(indexJS));
                listaJornadasSemanales.remove(CIndex);
                filtradoListaJornadasSemanales.remove(indexJS);
                infoRegistroSL = "Cantidad de registros: " + filtradoListaJornadasSemanales.size();
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSemanasLaborales");
            context.update("form:infoRegistroSL");
            indexJS = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:ACEPTAR");
            }
        }

    }

    //LIMPIAR NUEVO REGISTRO JORNADA LABORAL
    public void limpiarNuevaJornadaLaboral() {
        nuevaJornadaLaboral = new JornadasLaborales();
        index = -1;
        secRegistro = null;
    }

    //LIMPIAR NUEVO REGISTRO SEMANA LABORAL
    public void limpiarNuevaSemanaLaboral() {
        nuevaSemanaLaboral = new JornadasSemanales();
        indexJS = -1;
        secRegistro = null;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("JORNADA")) {
            if (tipoNuevo == 1) {
                jornada = nuevaJornadaLaboral.getJornada().getDescripcion();
            } else if (tipoNuevo == 2) {
                jornada = duplicarJornadaLaboral.getJornada().getDescripcion();
            }

        }
    }

    //DUPLICAR JORNADAS LABORALES
    public void duplicarJL() {

        if (index >= 0 && CualTabla == 0) {

            duplicarJornadaLaboral = new JornadasLaborales();

            if (tipoLista == 0) {
                duplicarJornadaLaboral.setCodigo(listaJornadasLaborales.get(index).getCodigo());
                duplicarJornadaLaboral.setDescripcion(listaJornadasLaborales.get(index).getDescripcion());
                duplicarJornadaLaboral.setHorasdiarias(listaJornadasLaborales.get(index).getHorasdiarias());
                duplicarJornadaLaboral.setHorasmensuales(listaJornadasLaborales.get(index).getHorasmensuales());
                duplicarJornadaLaboral.setRotativo(listaJornadasLaborales.get(index).getRotativo());
                duplicarJornadaLaboral.setTurnorelativo(listaJornadasLaborales.get(index).getTurnorelativo());
                duplicarJornadaLaboral.setCuadrillahorasdiarias(listaJornadasLaborales.get(index).getCuadrillahorasdiarias());
                duplicarJornadaLaboral.setJornada(listaJornadasLaborales.get(index).getJornada());
            }
            if (tipoLista == 1) {
                duplicarJornadaLaboral.setCodigo(filtradoListaJornadasLaborales.get(index).getCodigo());
                duplicarJornadaLaboral.setDescripcion(filtradoListaJornadasLaborales.get(index).getDescripcion());
                duplicarJornadaLaboral.setHorasdiarias(filtradoListaJornadasLaborales.get(index).getHorasdiarias());
                duplicarJornadaLaboral.setHorasmensuales(filtradoListaJornadasLaborales.get(index).getHorasmensuales());
                duplicarJornadaLaboral.setRotativo(filtradoListaJornadasLaborales.get(index).getRotativo());
                duplicarJornadaLaboral.setTurnorelativo(filtradoListaJornadasLaborales.get(index).getTurnorelativo());
                duplicarJornadaLaboral.setCuadrillahorasdiarias(filtradoListaJornadasLaborales.get(index).getCuadrillahorasdiarias());
                duplicarJornadaLaboral.setJornada(filtradoListaJornadasLaborales.get(index).getJornada());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarJornadaLaboral");
            context.execute("DuplicarRegistroJornadaLaboral.show()");
            index = -1;
            secRegistro = null;

        } else if (indexJS >= 0 && CualTabla == 1) {
            System.out.println("Entra Duplicar JS");

            duplicarSemanaLaboral = new JornadasSemanales();
            m++;
            n = BigInteger.valueOf(m);

            if (tipoListaJS == 0) {
                duplicarSemanaLaboral.setJornadalaboral(jornadaLaboralSeleccionada);
                duplicarSemanaLaboral.setDia(listaJornadasSemanales.get(indexJS).getDia());
                duplicarSemanaLaboral.setHorainicial(listaJornadasSemanales.get(indexJS).getHorainicial());
                duplicarSemanaLaboral.setMinutoinicial(listaJornadasSemanales.get(indexJS).getMinutoinicial());
                duplicarSemanaLaboral.setHorainicialalimentacion(listaJornadasSemanales.get(indexJS).getHorainicialalimentacion());
                duplicarSemanaLaboral.setMinutoinicialalimentacion(listaJornadasSemanales.get(indexJS).getMinutoinicialalimentacion());
                duplicarSemanaLaboral.setHorafinalalimentacion(listaJornadasSemanales.get(indexJS).getHorafinalalimentacion());
                duplicarSemanaLaboral.setMinutofinalalimentacion(listaJornadasSemanales.get(indexJS).getMinutofinalalimentacion());
                duplicarSemanaLaboral.setHorafinal(listaJornadasSemanales.get(indexJS).getHorafinal());
                duplicarSemanaLaboral.setMinutofinal(listaJornadasSemanales.get(indexJS).getMinutofinal());
            }
            if (tipoListaJS == 1) {
                duplicarSemanaLaboral.setJornadalaboral(jornadaLaboralSeleccionada);
                duplicarSemanaLaboral.setDia(filtradoListaJornadasSemanales.get(indexJS).getDia());
                duplicarSemanaLaboral.setHorainicial(filtradoListaJornadasSemanales.get(indexJS).getHorainicial());
                duplicarSemanaLaboral.setMinutoinicial(filtradoListaJornadasSemanales.get(indexJS).getMinutoinicial());
                duplicarSemanaLaboral.setHorainicialalimentacion(filtradoListaJornadasSemanales.get(indexJS).getHorainicialalimentacion());
                duplicarSemanaLaboral.setMinutoinicialalimentacion(filtradoListaJornadasSemanales.get(indexJS).getMinutoinicialalimentacion());
                duplicarSemanaLaboral.setHorafinalalimentacion(filtradoListaJornadasSemanales.get(indexJS).getHorafinalalimentacion());
                duplicarSemanaLaboral.setMinutofinalalimentacion(filtradoListaJornadasSemanales.get(indexJS).getMinutofinalalimentacion());
                duplicarSemanaLaboral.setHorafinal(filtradoListaJornadasSemanales.get(indexJS).getHorafinal());
                duplicarSemanaLaboral.setMinutofinal(filtradoListaJornadasSemanales.get(indexJS).getMinutofinal());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarSemanaLaboral");
            context.execute("duplicarRegistroSemanaLaboral.show()");
            indexJS = -1;
            secRegistro = null;

        }
    }

    public void llamarLovJornadas(int tipoJ) {
        if (tipoJ == 1) {
            tipoActualizacion = 1;
        } else if (tipoJ == 2) {
            tipoActualizacion = 2;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:jornadasDialogo");
        context.execute("jornadasDialogo.show()");
    }

    //LIMPIAR DUPLICAR JORNADA LABORAL
    public void limpiarduplicarJornadaLaboral() {
        duplicarJornadaLaboral = new JornadasLaborales();
    }
    //LIMPIAR DUPLICAR SEMANA LABORAL

    public void limpiarduplicarSemanaLaboral() {
        duplicarSemanaLaboral = new JornadasSemanales();
    }

    //CONFIRMAR DUPLICAR JORNADA LABORAL
    public void confirmarDuplicarJL() {
        k++;
        l = BigInteger.valueOf(k);
        duplicarJornadaLaboral.setSecuencia(l);
        RequestContext context = RequestContext.getCurrentInstance();
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        int pasa = 0;
        mensajeValidacion = " ";
        mensajeValidacionhoras = " ";

        if (duplicarJornadaLaboral.getHorasdiarias() != null && (duplicarJornadaLaboral.getHorasdiarias().compareTo(BigDecimal.ONE) == 0 || nuevaJornadaLaboral.getHorasdiarias().compareTo(BigDecimal.ONE) == -1)) {
            mensajeValidacionhoras = mensajeValidacionhoras + " Horas Diarias. ";
            pasa++;
            context.update("formularioDialogos:validacionHorasJL");
            context.execute("validacionHorasJL.show()");
        }
        if (duplicarJornadaLaboral.getHorasmensuales() != null && (duplicarJornadaLaboral.getHorasmensuales().compareTo(Short.valueOf("0")) == 0 || nuevaJornadaLaboral.getHorasmensuales().compareTo(Short.valueOf("0")) < 0)) {
            mensajeValidacionhoras = mensajeValidacionhoras + " Horas Mensuale. ";
            pasa++;
            context.update("formularioDialogos:validacionHorasJL");
            context.execute("validacionHorasJL.show()");
        }
        if (duplicarJornadaLaboral.getCodigo() != null) {
            for (int i = 0; i < listaJornadasLaborales.size(); i++) {
                if (duplicarJornadaLaboral.getCodigo() == listaJornadasLaborales.get(i).getCodigo()) {
                    pasa++;
                }
                context.update("formularioDialogos:validacionCodigo");
                context.execute("validacionCodigo.show()");
            }
        }
        if (duplicarJornadaLaboral.getDescripcion() == null || duplicarJornadaLaboral.getDescripcion().equals("")) {
            System.out.println("Entro a Descripción");
            mensajeValidacion = mensajeValidacion + " * Descripcion\n";
            pasa++;
        }

        if (pasa == 0) {

            listaJornadasLaborales.add(duplicarJornadaLaboral);
            listaJornadasLaboralesCrear.add(duplicarJornadaLaboral);

            context.update("form:datosJornadasLaborales");

            if (bandera == 1) {
                FacesContext c = FacesContext.getCurrentInstance();

                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
                jornadasLaboralesCodigos.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
                jornadasLaboralesDescripcion.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
                jornadasLaboralesHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
                jornadasLaboralesHorasMensuales.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesRotativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesRotativo");
                jornadasLaboralesRotativo.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesTurnoRelativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesTurnoRelativo");
                jornadasLaboralesTurnoRelativo.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
                jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
                jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesJornadas");
                jornadasLaboralesJornadas.setFilterStyle("display: none; visibility: hidden;");
                altoTabla = "115";
                RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
                bandera = 0;
                filtradoListaJornadasLaborales = null;
                tipoLista = 0;

            }
            duplicarJornadaLaboral = new JornadasLaborales();
            infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
            context.update("form:infoRegistroJL");
        } else if (duplicarJornadaLaboral.getDescripcion() == null || duplicarJornadaLaboral.getDescripcion().equals("")) {
            context.update("formularioDialogos:validacionNuevaJornadaLaboral");
            context.execute("validacionNuevaJornadaLaboral.show()");
        }
    }

    //CONFIRMAR DUPLICAR JORNADA LABORAL
    public void confirmarDuplicarJS() {

        k++;
        l = BigInteger.valueOf(k);
        duplicarSemanaLaboral.setSecuencia(l);
        RequestContext context = RequestContext.getCurrentInstance();
        listaJornadasSemanales.add(duplicarSemanaLaboral);
        listaJornadasSemanalesCrear.add(duplicarSemanaLaboral);
        context.update("form:datosSemanasLaborales");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
        }
        if (banderaJS == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoListaJS);
            SemanaLaboralHI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHI");
            SemanaLaboralHI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMI");
            SemanaLaboralMI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHIA");
            SemanaLaboralHIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMIA");
            SemanaLaboralMIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHFA");
            SemanaLaboralHFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
            SemanaLaboralMFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHoraFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHoraFinal");
            SemanaLaboralHoraFinal.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMinutoFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMinutoFinal");
            SemanaLaboralMinutoFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
            banderaJS = 0;
            filtradoListaJornadasSemanales = null;
            tipoListaJS = 0;

        }
        duplicarSemanaLaboral = new JornadasSemanales();
        infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
        context.update("form:infoRegistroSL");
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("JORNADA")) {
            if (tipoNuevo == 1) {
                nuevaJornadaLaboral.getJornada().setDescripcion(jornada);
            } else if (tipoNuevo == 2) {
                duplicarJornadaLaboral.getJornada().setDescripcion(jornada);
            }
            for (int i = 0; i < lovJornadas.size(); i++) {
                if (lovJornadas.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaJornadaLaboral.setJornada(lovJornadas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaJornada");
                } else if (tipoNuevo == 2) {
                    duplicarJornadaLaboral.setJornada(lovJornadas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarJornada");
                }
                lovJornadas.clear();
                getLovJornadas();
            } else {
                context.update("form:jornadasDialogo");
                context.execute("jornadasDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaJornada");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarJornada");
                }
            }
        }
    }

    public void dialogoJornadaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:NuevoRegistroJornadaLaboral");
        context.execute("NuevoRegistroJornadaLaboral.show()");
    }

    public void dialogoSemanaLaboral() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:NuevoRegistroJornadaSemanal");
        context.execute("NuevoRegistroJornadaSemanal.show()");

    }

    //VERIFICAR RASTRO
    public void verificarRastro() {
        if (CualTabla == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("lol");
            if (!listaJornadasLaborales.isEmpty()) {
                if (secRegistro != null) {
                    System.out.println("lol2");
                    int resultado = administrarRastros.obtenerTabla(secRegistro, "JORNADASLABORALES");
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
                    } else if (resultado == 6) {
                        context.execute("errorTablaSinRastro.show()");
                    } else if (resultado == 7) {
                        context.execute("errorTablaSinRastro.show()");
                    }
                } else {
                    context.execute("seleccionarRegistro.show()");
                }
            } else {
                if (administrarRastros.verificarHistoricosTabla("JORNADASLABORALES")) {
                    context.execute("confirmarRastroHistorico.show()");
                } else {
                    context.execute("errorRastroHistorico.show()");
                }

            }
            index = -1;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("JS");
            if (!listaJornadasSemanales.isEmpty()) {
                if (secRegistro != null) {
                    System.out.println("JS2");
                    int resultadoJS = administrarRastros.obtenerTabla(secRegistro, "JORNADASSEMANALES");
                    System.out.println("resultado: " + resultadoJS);
                    if (resultadoJS == 1) {
                        context.execute("errorObjetosDBJS.show()");
                    } else if (resultadoJS == 2) {
                        context.execute("confirmarRastroJS.show()");
                    } else if (resultadoJS == 3) {
                        context.execute("errorRegistroRastroJS.show()");
                    } else if (resultadoJS == 4) {
                        context.execute("errorTablaConRastroJS.show()");
                    } else if (resultadoJS == 5) {
                        context.execute("errorTablaSinRastroJS.show()");
                    } else if (resultadoJS == 6) {
                        context.execute("errorTablaSinRastroJS.show()");
                    } else if (resultadoJS == 7) {
                        context.execute("errorTablaSinRastroJS.show()");
                    } else if (resultadoJS == 8) {
                        context.execute("errorTablaSinRastroJS.show()");
                    }
                } else {
                    context.execute("seleccionarRegistroJS.show()");
                }
            } else {
                if (administrarRastros.verificarHistoricosTabla("JORNADASSEMANALES")) {
                    context.execute("confirmarRastroHistoricoJS.show()");
                } else {
                    context.execute("errorRastroHistoricoJS.show()");
                }

            }
            indexJS = -1;
        }
    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {
        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
            jornadasLaboralesCodigos.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
            jornadasLaboralesDescripcion.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
            jornadasLaboralesHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
            jornadasLaboralesHorasMensuales.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesRotativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesRotativo");
            jornadasLaboralesRotativo.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesTurnoRelativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesTurnoRelativo");
            jornadasLaboralesTurnoRelativo.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
            jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:pECalificaciones");
            jornadasLaboralesJornadas.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "115";
            RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
            bandera = 0;
            filtradoListaJornadasLaborales = null;
            tipoLista = 0;
        }

        if (banderaJS == 1) {
            FacesContext c = FacesContext.getCurrentInstance();
            SemanaLaboralDia = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralDia");
            SemanaLaboralDia.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHI");
            SemanaLaboralHI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMI = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMI");
            SemanaLaboralMI.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHIA");
            SemanaLaboralHIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMIA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMIA");
            SemanaLaboralMIA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHFA");
            SemanaLaboralHFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMFA = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
            SemanaLaboralMFA.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralHoraFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralHoraFinal");
            SemanaLaboralHoraFinal.setFilterStyle("display: none; visibility: hidden;");
            SemanaLaboralMinutoFinal = (Column) c.getViewRoot().findComponent("form:datosSemanasLaborales:SemanaLaboralMFA");
            SemanaLaboralMinutoFinal.setFilterStyle("display: none; visibility: hidden;");
            altoTabla2 = "115";
            RequestContext.getCurrentInstance().update("form:datosSemanasLaborales");
            banderaJS = 0;
            filtradoListaJornadasSemanales = null;
            tipoListaJS = 0;
        }
        listaJornadasLaboralesBorrar.clear();
        listaJornadasLaboralesCrear.clear();
        listaJornadasLaboralesModificar.clear();
        index = -1;
        secRegistro = null;
        listaJornadasLaborales = null;
        getListaJornadasLaborales();
        if (listaJornadasLaborales != null && !listaJornadasLaborales.isEmpty()) {
            jornadaLaboralSeleccionada = listaJornadasLaborales.get(0);
            infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
        } else {
            infoRegistroJL = "Cantidad de registros: 0";
        }
        listaJornadasSemanalesBorrar.clear();
        listaJornadasSemanalesCrear.clear();
        listaJornadasSemanalesModificar.clear();
        indexJS = -1;
        listaJornadasSemanales = null;
        getListaJornadasSemanales();
        if (listaJornadasSemanales != null && !listaJornadasSemanales.isEmpty()) {
            jornadaSemanalSeleccionada = listaJornadasSemanales.get(0);
            infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
        } else {
            infoRegistroSL = "Cantidad de registros: 0";
        }
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosJornadasLaborales");
        context.update("form:infoRegistroJL");
        context.update("form:datosSemanasLaborales");
        context.update("form:infoRegistroSL");
    }

    //SALIR
    public void salir() {

        if (bandera == 1) {
            FacesContext c = FacesContext.getCurrentInstance();

            jornadasLaboralesCodigos = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCodigos");
            jornadasLaboralesCodigos.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesDescripcion = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesDescripcion");
            jornadasLaboralesDescripcion.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasDiarias");
            jornadasLaboralesHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesHorasMensuales = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesHorasMensuales");
            jornadasLaboralesHorasMensuales.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesRotativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesRotativo");
            jornadasLaboralesRotativo.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesTurnoRelativo = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesTurnoRelativo");
            jornadasLaboralesTurnoRelativo.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesCuadrillaHorasDiarias = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesCuadrillaHorasDiarias");
            jornadasLaboralesCuadrillaHorasDiarias.setFilterStyle("display: none; visibility: hidden;");
            jornadasLaboralesJornadas = (Column) c.getViewRoot().findComponent("form:datosJornadasLaborales:jornadasLaboralesJornadas");
            jornadasLaboralesJornadas.setFilterStyle("display: none; visibility: hidden;");
            altoTabla = "115";
            RequestContext.getCurrentInstance().update("form:datosJornadasLaborales");
            bandera = 0;
            filtradoListaJornadasLaborales = null;
            tipoLista = 0;
        }

        listaJornadasLaboralesBorrar.clear();
        listaJornadasLaboralesCrear.clear();
        listaJornadasLaboralesModificar.clear();
        index = -1;
        secRegistro = null;
        //  k = 0;
        listaJornadasLaborales = null;
        guardado = true;
        permitirIndex = true;

    }

    //Getter & Setters
    public BigInteger getSecuenciaJornada() {
        return secuenciaJornada;
    }

    public void setSecuenciaJornada(BigInteger secuenciaJornada) {
        this.secuenciaJornada = secuenciaJornada;
    }

    public List<JornadasLaborales> getListaJornadasLaborales() {
        if (listaJornadasLaborales == null) {
            listaJornadasLaborales = administrarJornadasLaborales.consultarJornadasLaborales();
            if (!listaJornadasLaborales.isEmpty()) {
                jornadaLaboralSeleccionada = listaJornadasLaborales.get(0);
                getListaJornadasSemanales();
                RequestContext context = RequestContext.getCurrentInstance();

                if (listaJornadasLaborales == null || listaJornadasLaborales.isEmpty()) {
                    infoRegistroJL = "Cantidad de registros: 0 ";
                } else {
                    infoRegistroJL = "Cantidad de registros: " + listaJornadasLaborales.size();
                }
                context.update("form:infoRegistroJL");
            } else {
                jornadaLaboralSeleccionada = new JornadasLaborales();
            }
        }
        return listaJornadasLaborales;
    }

    public void setListaJornadasLaborales(List<JornadasLaborales> listaJornadasLaborales) {
        this.listaJornadasLaborales = listaJornadasLaborales;
    }

    public List<JornadasLaborales> getFiltradoListaJornadasLaborales() {
        return filtradoListaJornadasLaborales;
    }

    public void setFiltradoListaJornadasLaborales(List<JornadasLaborales> filtradoListaJornadasLaborales) {
        this.filtradoListaJornadasLaborales = filtradoListaJornadasLaborales;
    }

    public JornadasLaborales getJornadaLaboralSeleccionada() {
        return jornadaLaboralSeleccionada;
    }

    public void setJornadaLaboralSeleccionada(JornadasLaborales jornadaLaboralSeleccionada) {
        this.jornadaLaboralSeleccionada = jornadaLaboralSeleccionada;
    }

    public List<JornadasSemanales> getListaJornadasSemanales() {
        if (listaJornadasSemanales == null && jornadaLaboralSeleccionada != null) {
            listaJornadasSemanales = administrarJornadasLaborales.consultarJornadasSemanales(jornadaLaboralSeleccionada.getSecuencia());
            RequestContext context = RequestContext.getCurrentInstance();

            if (listaJornadasSemanales == null || listaJornadasSemanales.isEmpty()) {
                infoRegistroSL = "Cantidad de registros: 0 ";
            } else {
                infoRegistroSL = "Cantidad de registros: " + listaJornadasSemanales.size();
            }
            context.update("form:infoRegistroSL");
        }
        return listaJornadasSemanales;
    }

    public void setListaJornadasSemanales(List<JornadasSemanales> listaJornadasSemanales) {
        this.listaJornadasSemanales = listaJornadasSemanales;
    }

    public List<JornadasSemanales> getFiltradoListaJornadasSemanales() {
        return filtradoListaJornadasSemanales;
    }

    public void setFiltradoListaJornadasSemanales(List<JornadasSemanales> filtradoListaJornadasSemanales) {
        this.filtradoListaJornadasSemanales = filtradoListaJornadasSemanales;
    }

    public JornadasSemanales getJornadaSemanalSeleccionada() {
        return jornadaSemanalSeleccionada;
    }

    public void setJornadaSemanalSeleccionada(JornadasSemanales jornadaSemanalSeleccionada) {
        this.jornadaSemanalSeleccionada = jornadaSemanalSeleccionada;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getAltoTabla2() {
        return altoTabla2;
    }

    public void setAltoTabla2(String altoTabla2) {
        this.altoTabla2 = altoTabla2;
    }

    public List<Jornadas> getLovJornadas() {
        lovJornadas = administrarJornadasLaborales.consultarJornadas();
        RequestContext context = RequestContext.getCurrentInstance();

        if (lovJornadas == null || lovJornadas.isEmpty()) {
            infoRegistroJornadas = "Cantidad de registros: 0 ";
        } else {
            infoRegistroJornadas = "Cantidad de registros: " + lovJornadas.size();
        }
        context.update("formularioDialogos:infoRegistroJornadas");
        return lovJornadas;
    }

    public void setLovJornadas(List<Jornadas> lovJornadas) {
        this.lovJornadas = lovJornadas;
    }

    public List<Jornadas> getLovFiltradoJornadas() {
        return lovFiltradoJornadas;
    }

    public void setLovFiltradoJornadas(List<Jornadas> lovFiltradoJornadas) {
        this.lovFiltradoJornadas = lovFiltradoJornadas;
    }

    public Jornadas getJornadaSeleccionada() {
        return jornadaSeleccionada;
    }

    public void setJornadaSeleccionada(Jornadas jornadaSeleccionada) {
        this.jornadaSeleccionada = jornadaSeleccionada;
    }

    public String getInfoRegistroJornadas() {
        return infoRegistroJornadas;
    }

    public void setInfoRegistroJornadas(String infoRegistroJornadas) {
        this.infoRegistroJornadas = infoRegistroJornadas;
    }

    public String getInfoRegistroJL() {
        return infoRegistroJL;
    }

    public void setInfoRegistroJL(String infoRegistroJL) {
        this.infoRegistroJL = infoRegistroJL;
    }

    public String getInfoRegistroSL() {
        return infoRegistroSL;
    }

    public void setInfoRegistroSL(String infoRegistroSL) {
        this.infoRegistroSL = infoRegistroSL;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public void setInfoRegistro(String infoRegistro) {
        this.infoRegistro = infoRegistro;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public boolean isBuscador() {
        return buscador;
    }

    public void setBuscador(boolean buscador) {
        this.buscador = buscador;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public JornadasLaborales getNuevaJornadaLaboral() {
        return nuevaJornadaLaboral;
    }

    public void setNuevaJornadaLaboral(JornadasLaborales nuevaJornadaLaboral) {
        this.nuevaJornadaLaboral = nuevaJornadaLaboral;
    }

    public JornadasSemanales getNuevaJornadaSemanal() {
        return nuevaSemanaLaboral;
    }

    public void setNuevaJornadaSemanal(JornadasSemanales nuevaSemanaLaboral) {
        this.nuevaSemanaLaboral = nuevaSemanaLaboral;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public String getMensajeValidacionhoras() {
        return mensajeValidacionhoras;
    }

    public void setMensajeValidacionhoras(String mensajeValidacionhoras) {
        this.mensajeValidacionhoras = mensajeValidacionhoras;
    }

    public JornadasLaborales getEditarJornadaLaboral() {
        return editarJornadaLaboral;
    }

    public void setEditarJornadaLaboral(JornadasLaborales editarJornadaLaboral) {
        this.editarJornadaLaboral = editarJornadaLaboral;
    }

    public JornadasSemanales getEditarJornadaSemanal() {
        return editarJornadaSemanal;
    }

    public void setEditarJornadaSemanal(JornadasSemanales editarJornadaSemanal) {
        this.editarJornadaSemanal = editarJornadaSemanal;
    }

    public JornadasLaborales getDuplicarJornadaLaboral() {
        return duplicarJornadaLaboral;
    }

    public void setDuplicarJornadaLaboral(JornadasLaborales duplicarJornadaLaboral) {
        this.duplicarJornadaLaboral = duplicarJornadaLaboral;
    }

    public JornadasSemanales getDuplicarSemanaLaboral() {
        return duplicarSemanaLaboral;
    }

    public void setDuplicarSemanaLaboral(JornadasSemanales duplicarSemanaLaboral) {
        this.duplicarSemanaLaboral = duplicarSemanaLaboral;
    }

    public String getCualInsertar() {
        return cualInsertar;
    }

    public void setCualInsertar(String cualInsertar) {
        this.cualInsertar = cualInsertar;
    }

    public String getCualNuevo() {
        return cualNuevo;
    }

    public void setCualNuevo(String cualNuevo) {
        this.cualNuevo = cualNuevo;
    }

    public String getTablaImprimir() {
        return tablaImprimir;
    }

    public void setTablaImprimir(String tablaImprimir) {
        this.tablaImprimir = tablaImprimir;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}

package Controlador;

import Entidades.AdiestramientosF;
import Entidades.AdiestramientosNF;
import Entidades.Cursos;
import Entidades.Instituciones;
import Entidades.Personas;
import Entidades.Profesiones;
import Entidades.TiposEducaciones;
import Entidades.VigenciasFormales;
import Entidades.VigenciasNoFormales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciasFormalesInterface;
import InterfaceAdministrar.AdministrarVigenciasNoFormalesInterface;
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

@ManagedBean
@SessionScoped
public class ControlPersonaEducacion implements Serializable {

    @EJB
    AdministrarVigenciasNoFormalesInterface administrarVigenciasNoFormales;
    @EJB
    AdministrarVigenciasFormalesInterface administrarVigenciasFormales;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //SECUENCIA DE LA PERSONA
    private BigInteger secuenciaPersona;
    private Personas persona;
    //LISTA VIGENCIAS FORMALES
    private List<VigenciasFormales> listaVigenciasFormales;
    private List<VigenciasFormales> filtradosListaVigenciasFormales;
    //LISTA VIGENCIAS NO FORMALES
    private List<VigenciasNoFormales> listaVigenciasNoFormales;
    private List<VigenciasNoFormales> filtradosListaVigenciasNoFormales;
    //L.O.V EDUCACION
    private List<TiposEducaciones> listaTiposEducaciones;
    private List<TiposEducaciones> filtradoslistaTiposEducaciones;
    private TiposEducaciones seleccionTiposEducaciones;
    //L.O.V CURSOS
    private List<Cursos> listaCursos;
    private List<Cursos> filtradoslistaCursos;
    private Cursos seleccionCursos;
    //L.O.V PROFESION
    private List<Profesiones> listaProfesiones;
    private List<Profesiones> filtradoslistaProfesiones;
    private Profesiones seleccionProfesiones;
    //L.O.V EDUCACION
    private List<Instituciones> listaInstituciones;
    private List<Instituciones> filtradoslistaInstituciones;
    private Instituciones seleccionInstituciones;
    //L.O.V ADIESTRAMIENTOS FORMALES
    private List<AdiestramientosF> listaAdiestramientosFormales;
    private List<AdiestramientosF> filtradoslistaAdiestramientosFormales;
    private AdiestramientosF seleccionAdiestramientosFormales;
    //L.O.V ADIESTRAMIENTOS No FORMALES
    private List<AdiestramientosNF> listaAdiestramientosNoFormales;
    private List<AdiestramientosNF> filtradoslistaAdiestramientosNoFormales;
    private AdiestramientosNF seleccionAdiestramientosNoFormales;
    //OTROS
    private boolean aceptar;
    private int index;
    private int indexNF;
    private int tipoActualizacion; //Activo/Desactivo Crtl + F11
    private int bandera;
    private int banderaNF;
    private boolean permitirIndex;
    //Crear Vigencias 
    public VigenciasFormales nuevaVigenciaFormal;
    public VigenciasNoFormales nuevaVigenciaNoFormal;
    private List<VigenciasFormales> listaVigenciasFormalesCrear;
    private int k;
    private BigInteger l;
    private int m;
    private BigInteger n;
    private String mensajeValidacion;
    private String mensajeValidacionNF;
    //Crear Vigencias No Formales
    private List<VigenciasNoFormales> listaVigenciasNoFormalesCrear;
    //Modificar Vigencias Formales
    private List<VigenciasFormales> listaVigenciasFormalesModificar;
    private boolean guardado, guardarOk;
    //Modificar Vigencias No Formales
    private List<VigenciasNoFormales> listaVigenciasNoFormalesModificar;
    //Borrar VigenciasFormales
    private List<VigenciasFormales> listaVigenciasFormalesBorrar;
    //Borrar Vigencias No Formales
    private List<VigenciasNoFormales> listaVigenciasNoFormalesBorrar;
    //editar celda
    private VigenciasFormales editarVigenciasFormales;
    private VigenciasNoFormales editarVigenciasNoFormales;
    private boolean cambioEditor, aceptarEditar;
    private int cualCelda, tipoLista, tipoListaNF;
    //AUTOCOMPLETAR
    private String Fecha, TipoEducacion, Profesion, Institucion, AdiestramientoF;
    private String Curso, AdiestramientoNF;
    //RASTRO
    private BigInteger secRegistro;
    //Columnas Tabla Vigencias Formales
    private Column pEFechas, pETiposEducaciones, pEProfesiones, pEInstituciones, pEAdiestramientosF, pECalificaciones;
    private Column pENumerosTarjetas, pEFechasExpediciones, pEFechasVencimientos, pEObservaciones;
    //Columnas Tabla Vigencias No Formales
    private Column pEFechasNF, pECursosNF, pETitulosNF, pEInstitucionesNF, pEAdiestramientosNF, pECalificacionesNF, pEObservacionesNF;
    //Duplicar
    private VigenciasFormales duplicarVigenciaFormal;
    private VigenciasNoFormales duplicarVigenciaNoFormal;
    //Cual Tabla
    private int CualTabla;
    //Tabla a Imprimir
    private String tablaImprimir, nombreArchivo;
    //Cual Insertar
    private String cualInsertar;
    //Cual Nuevo Update
    private String cualNuevo;

    public ControlPersonaEducacion() {
        permitirIndex = true;
        //secuenciaPersona = BigInteger.valueOf(10668967);
        aceptar = true;
        listaVigenciasNoFormalesBorrar = new ArrayList<VigenciasNoFormales>();
        listaVigenciasNoFormalesCrear = new ArrayList<VigenciasNoFormales>();
        listaVigenciasNoFormalesModificar = new ArrayList<VigenciasNoFormales>();
        listaVigenciasFormalesBorrar = new ArrayList<VigenciasFormales>();
        listaVigenciasFormalesCrear = new ArrayList<VigenciasFormales>();
        listaVigenciasFormalesModificar = new ArrayList<VigenciasFormales>();
        //INICIALIZAR LOVS
        listaCursos = new ArrayList<Cursos>();
        listaAdiestramientosNoFormales = new ArrayList<AdiestramientosNF>();
        listaTiposEducaciones = new ArrayList<TiposEducaciones>();
        listaProfesiones = new ArrayList<Profesiones>();
        listaInstituciones = new ArrayList<Instituciones>();
        listaAdiestramientosFormales = new ArrayList<AdiestramientosF>();
        secRegistro = null;
        //editar
        editarVigenciasFormales = new VigenciasFormales();
        editarVigenciasNoFormales = new VigenciasNoFormales();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        tipoListaNF = 0;
        //Crear Vigencia Formal
        nuevaVigenciaFormal = new VigenciasFormales();
        nuevaVigenciaFormal.setTipoeducacion(new TiposEducaciones());
        nuevaVigenciaFormal.setProfesion(new Profesiones());
        nuevaVigenciaFormal.setInstitucion(new Instituciones());
        nuevaVigenciaFormal.setAdiestramientof(new AdiestramientosF());
        nuevaVigenciaNoFormal = new VigenciasNoFormales();
        nuevaVigenciaNoFormal.setCurso(new Cursos());
        nuevaVigenciaNoFormal.setInstitucion(new Instituciones());
        nuevaVigenciaNoFormal.setAdiestramientonf(new AdiestramientosNF());
        guardado = true;
        tablaImprimir = ":formExportar:datosVigenciasFormalesExportar";
        nombreArchivo = "VigenciasFormalesXML";
        k = 0;
        cualInsertar = ":formularioDialogos:NuevoRegistroVigenciaFormal";
        cualNuevo = ":formularioDialogos:nuevaVigenciaFormal";
        m = 0;
    }

    public void recibirPersona(BigInteger secPersona) {
        secuenciaPersona = secPersona;
        persona = null;
        getPersona();
        listaVigenciasFormales = null;
        getListaVigenciasFormales();
        listaTiposEducaciones = null;
        getListaTiposEducaciones();
        listaProfesiones = null;
        getListaProfesiones();
        listaInstituciones = null;
        getListaInstituciones();
        listaAdiestramientosFormales = null;
        getListaAdiestramientosFormales();
        aceptar = true;
    }

    //EVENTO FILTRAR
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    //EVENTO FILTRARNF
    public void eventoFiltrarNF() {
        if (tipoListaNF == 0) {
            tipoListaNF = 1;
        }
    }

    //Ubicacion Celda.
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            CualTabla = 0;

            tablaImprimir = ":formExportar:datosVigenciasFormalesExportar";
            nombreArchivo = "VigenciasFormalesXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:exportarXML");
            if (tipoLista == 0) {
                secRegistro = listaVigenciasFormales.get(index).getSecuencia();
                if (cualCelda == 1) {
                    TipoEducacion = listaVigenciasFormales.get(index).getTipoeducacion().getNombre();
                } else if (cualCelda == 2) {
                    Profesion = listaVigenciasFormales.get(index).getProfesion().getDescripcion();
                } else if (cualCelda == 3) {
                    Institucion = listaVigenciasFormales.get(index).getInstitucion().getDescripcion();
                } else if (cualCelda == 4) {
                    AdiestramientoF = listaVigenciasFormales.get(index).getAdiestramientof().getDescripcion();
                }
            } else {
                secRegistro = filtradosListaVigenciasFormales.get(index).getSecuencia();
                if (cualCelda == 1) {
                    TipoEducacion = filtradosListaVigenciasFormales.get(index).getTipoeducacion().getNombre();
                } else if (cualCelda == 2) {
                    Profesion = filtradosListaVigenciasFormales.get(index).getProfesion().getDescripcion();
                } else if (cualCelda == 3) {
                    Institucion = filtradosListaVigenciasFormales.get(index).getInstitucion().getDescripcion();
                } else if (cualCelda == 4) {
                    AdiestramientoF = filtradosListaVigenciasFormales.get(index).getAdiestramientof().getDescripcion();
                }
            }
        }
    }

//AUTOCOMPLETAR
    public void modificarVigenciasFormales(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoLista == 0) {
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(indice))) {

                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(indice));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(indice))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;

            } else {
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(indice))) {

                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indice));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(indice))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
            context.update("form:datosVigenciasFormalesPersona");
        } else if (confirmarCambio.equalsIgnoreCase("TIPOEDUCACION")) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(indice).getTipoeducacion().setNombre(TipoEducacion);
            } else {
                filtradosListaVigenciasFormales.get(indice).getTipoeducacion().setNombre(TipoEducacion);
            }

            for (int i = 0; i < listaTiposEducaciones.size(); i++) {
                if (listaTiposEducaciones.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaVigenciasFormales.get(indice).setTipoeducacion(listaTiposEducaciones.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasFormales.get(indice).setTipoeducacion(listaTiposEducaciones.get(indiceUnicoElemento));
                }
                listaTiposEducaciones.clear();
                getListaTiposEducaciones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:tiposEducacionesDialogo");
                context.execute("tiposEducacionesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROFESION")) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(indice).getProfesion().setDescripcion(Profesion);
            } else {
                filtradosListaVigenciasFormales.get(indice).getProfesion().setDescripcion(Profesion);
            }
            for (int i = 0; i < listaProfesiones.size(); i++) {
                if (listaProfesiones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaVigenciasFormales.get(indice).setProfesion(listaProfesiones.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasFormales.get(indice).setProfesion(listaProfesiones.get(indiceUnicoElemento));
                }
                listaProfesiones.clear();
                getListaProfesiones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:profesionesDialogo");
                context.execute("profesionesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("INSTITUCION")) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(indice).getInstitucion().setDescripcion(Institucion);
            } else {
                filtradosListaVigenciasFormales.get(indice).getInstitucion().setDescripcion(Institucion);
            }
            for (int i = 0; i < listaInstituciones.size(); i++) {
                if (listaInstituciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaVigenciasFormales.get(indice).setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasFormales.get(indice).setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                }
                listaInstituciones.clear();
                getListaInstituciones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:institucionesDialogo");
                context.execute("institucionesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ADIESTRAMENTOF")) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(indice).getAdiestramientof().setDescripcion(AdiestramientoF);
            } else {
                filtradosListaVigenciasFormales.get(indice).getAdiestramientof().setDescripcion(AdiestramientoF);
            }
            for (int i = 0; i < listaAdiestramientosFormales.size(); i++) {
                if (listaAdiestramientosFormales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaVigenciasFormales.get(indice).setAdiestramientof(listaAdiestramientosFormales.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasFormales.get(indice).setAdiestramientof(listaAdiestramientosFormales.get(indiceUnicoElemento));
                }
                listaProfesiones.clear();
                getListaProfesiones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:profesionesDialogo");
                context.execute("profesionesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(indice))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(indice));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(indice))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(indice))) {

                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indice));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(indice))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciasFormalesPersona");
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
            context.update("form:tiposEducacionesDialogo");
            context.execute("tiposEducacionesDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:profesionesDialogo");
            context.execute("profesionesDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:institucionesDialogo");
            context.execute("institucionesDialogo.show()");
        } else if (dlg == 3) {
            context.update("form:adiestramientosFDialogo");
            context.execute("adiestramientosFDialogo.show()");
        }

    }

    public void actualizarTiposEducaciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(index).setTipoeducacion(seleccionTiposEducaciones);
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasFormales.get(index).setTipoeducacion(seleccionTiposEducaciones);
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormal.setTipoeducacion(seleccionTiposEducaciones);
            context.update("formularioDialogos:nuevaVigenciaFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormal.setTipoeducacion(seleccionTiposEducaciones);
            context.update("formularioDialogos:duplicarVigenciaFormal");
        }
        filtradoslistaTiposEducaciones = null;
        seleccionTiposEducaciones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("tiposEducacionesDialogo.hide()");
        context.reset("formularioDialogos:LOVTiposEducaciones:globalFilter");
        context.update("formularioDialogos:LOVTiposEducaciones");
    }

    public void actualizarProfesiones() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(index).setProfesion(seleccionProfesiones);
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasFormales.get(index).setProfesion(seleccionProfesiones);
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormal.setProfesion(seleccionProfesiones);
            context.update("formularioDialogos:nuevaVigenciaFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormal.setProfesion(seleccionProfesiones);
            context.update("formularioDialogos:duplicarVigenciaFormal");
        }
        filtradoslistaProfesiones = null;
        seleccionProfesiones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("profesionesDialogo.hide()");
        context.reset("formularioDialogos:LOVProfesiones:globalFilter");
        context.update("formularioDialogos:LOVProfesiones");
    }

    public void actualizarInstituciones() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(index).setInstitucion(seleccionInstituciones);
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasFormales.get(index).setInstitucion(seleccionInstituciones);
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormal.setInstitucion(seleccionInstituciones);
            context.update("formularioDialogos:nuevaVigenciaFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormal.setInstitucion(seleccionInstituciones);
            context.update("formularioDialogos:duplicarVigenciaFormal");
        }
        filtradoslistaInstituciones = null;
        seleccionInstituciones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("institucionesDialogo.hide()");
        context.reset("formularioDialogos:LOVInstituciones:globalFilter");
        context.update("formularioDialogos:LOVInstituciones");
    }

    public void actualizarAdiestramientoF() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listaVigenciasFormales.get(index).setAdiestramientof(seleccionAdiestramientosFormales);
                if (!listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(listaVigenciasFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasFormales.get(index).setAdiestramientof(seleccionAdiestramientosFormales);
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(index))) {
                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(index))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaFormal.setAdiestramientof(seleccionAdiestramientosFormales);
            context.update("formularioDialogos:nuevaVigenciaFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaFormal.setAdiestramientof(seleccionAdiestramientosFormales);
            context.update("formularioDialogos:duplicarVigenciaFormal");
        }
        filtradoslistaAdiestramientosFormales = null;
        seleccionAdiestramientosFormales = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("adiestramientosFDialogo.hide()");
        context.reset("formularioDialogos:LOVAdiestramientosF:globalFilter");
        context.update("formularioDialogos:LOVAdiestramientosF");
    }

    public void cancelarCambioTiposEducaciones() {
        filtradoslistaTiposEducaciones = null;
        seleccionTiposEducaciones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioProfesiones() {
        filtradoslistaProfesiones = null;
        seleccionProfesiones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioInstituciones() {
        filtradoslistaInstituciones = null;
        seleccionInstituciones = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioAdiestramientoF() {
        filtradoslistaAdiestramientosFormales = null;
        seleccionAdiestramientosFormales = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    //MOSTRAR DATOS CELDA
    public void editarCelda() {
        if (index >= 0 && CualTabla == 0) {
            if (tipoLista == 0) {
                editarVigenciasFormales = listaVigenciasFormales.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciasFormales = filtradosListaVigenciasFormales.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFecha");
                context.execute("editarFecha.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarTipoEducacion");
                context.execute("editarTipoEducacion.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarProfesion");
                context.execute("editarProfesion.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarInstitucion");
                context.execute("editarInstitucion.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarAdiestramientoF");
                context.execute("editarAdiestramientoF.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarValoracion");
                context.execute("editarValoracion.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarNumeroTarjeta");
                context.execute("editarNumeroTarjeta.show()");
                cualCelda = -1;
            } else if (cualCelda == 7) {
                context.update("formularioDialogos:editarFechaExpedicion");
                context.execute("editarFechaExpedicion.show()");
                cualCelda = -1;
            } else if (cualCelda == 8) {
                context.update("formularioDialogos:editarFechaVencimiento");
                context.execute("editarFechaVencimiento.show()");
                cualCelda = -1;
            } else if (cualCelda == 9) {
                context.update("formularioDialogos:editarObservacion");
                context.execute("editarObservacion.show()");
                cualCelda = -1;
            }
            index = -1;
        } else if (indexNF >= 0 && CualTabla == 1) {
            if (tipoListaNF == 0) {
                editarVigenciasNoFormales = listaVigenciasNoFormales.get(indexNF);
            }
            if (tipoListaNF == 1) {
                editarVigenciasNoFormales = filtradosListaVigenciasNoFormales.get(indexNF);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            System.out.println("Cual Tabla: " + CualTabla);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaNF");
                context.execute("editarFechaNF.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarCursoNF");
                context.execute("editarCursoNF.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarTitulo");
                context.execute("editarTitulo.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarInstitucionNF");
                context.execute("editarInstitucionNF.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarAdiestramientoNF");
                context.execute("editarAdiestramientoNF.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarCalificacionNF");
                context.execute("editarCalificacionNF.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarObservacion");
                context.execute("editarObservacion.show()");
                cualCelda = -1;
            }
            indexNF = -1;
        }


        secRegistro = null;
    }

    //LISTA DE VALORES DINAMICA
    public void listaValoresBoton() {
        if (index >= 0 && CualTabla == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("formularioDialogos:tiposEducacionesDialogo");
                context.execute("tiposEducacionesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:profesionesDialogo");
                context.execute("profesionesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:institucionesDialogo");
                context.execute("institucionesDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:adiestramientosFDialogo");
                context.execute("adiestramientosFDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (indexNF >= 0 && CualTabla == 1) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 1) {
                context.update("formularioDialogos:cursosDialogo");
                context.execute("cursosDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:institucionesNFDialogo");
                context.execute("institucionesNFDialogo.show()");
                tipoActualizacion = 0;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:adiestramientosNFDialogo");
                context.execute("adiestramientosNFDialogo.show()");
                tipoActualizacion = 0;
            }

        }
    }

    //FILTRADO
    public void activarCtrlF11() {
        System.out.println("TipoLista= " + tipoLista);
        if (bandera == 0 && CualTabla == 0) {
            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
            pEFechas.setFilterStyle("width: 60px");
            pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
            pETiposEducaciones.setFilterStyle("");
            pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
            pEProfesiones.setFilterStyle("");
            pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
            pEInstituciones.setFilterStyle("width: 60px");
            pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
            pEAdiestramientosF.setFilterStyle("width: 60px");
            pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
            pECalificaciones.setFilterStyle("width: 60px");
            pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
            pENumerosTarjetas.setFilterStyle("width: 60px");
            pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
            pEFechasExpediciones.setFilterStyle("width: 60px");
            pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
            pEFechasVencimientos.setFilterStyle("width: 60px");
            pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
            pEObservaciones.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
            bandera = 1;

        } else if (bandera == 1 && CualTabla == 0) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
            pEFechas.setFilterStyle("display: none; visibility: hidden;");
            pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
            pETiposEducaciones.setFilterStyle("display: none; visibility: hidden;");
            pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
            pEProfesiones.setFilterStyle("display: none; visibility: hidden;");
            pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
            pEInstituciones.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
            pEAdiestramientosF.setFilterStyle("display: none; visibility: hidden;");
            pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
            pECalificaciones.setFilterStyle("display: none; visibility: hidden;");
            pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
            pENumerosTarjetas.setFilterStyle("display: none; visibility: hidden;");
            pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
            pEFechasExpediciones.setFilterStyle("display: none; visibility: hidden;");
            pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
            pEFechasVencimientos.setFilterStyle("display: none; visibility: hidden;");
            pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
            pEObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
            bandera = 0;
            filtradosListaVigenciasFormales = null;
            tipoLista = 0;
        } else if (banderaNF == 0 && CualTabla == 1) {

            System.out.println("Activar");
            System.out.println("TipoLista= " + tipoLista);
            pEFechasNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEFechasNF");
            pEFechasNF.setFilterStyle("width: 60px");
            pECursosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECursosNF");
            pECursosNF.setFilterStyle("");
            pETitulosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pETitulosNF");
            pETitulosNF.setFilterStyle("");
            pEInstitucionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEInstitucionesNF");
            pEInstitucionesNF.setFilterStyle("width: 60px");
            pEAdiestramientosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEAdiestramientosNF");
            pEAdiestramientosNF.setFilterStyle("width: 60px");
            pECalificacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECalificacionesNF");
            pECalificacionesNF.setFilterStyle("width: 60px");
            pEObservacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEObservacionesNF");
            pEObservacionesNF.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosVigenciasNoFormalesPersona");
            banderaNF = 1;

        } else if (banderaNF == 1 && CualTabla == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            pEFechasNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEFechasNF");
            pEFechasNF.setFilterStyle("display: none; visibility: hidden;");
            pECursosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECursosNF");
            pECursosNF.setFilterStyle("display: none; visibility: hidden;");
            pETitulosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pETitulosNF");
            pETitulosNF.setFilterStyle("display: none; visibility: hidden;");
            pEInstitucionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEInstitucionesNF");
            pEInstitucionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEAdiestramientosNF");
            pEAdiestramientosNF.setFilterStyle("display: none; visibility: hidden;");
            pECalificacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECalificacionesNF");
            pECalificacionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEObservacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEObservacionesNF");
            pEObservacionesNF.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasNoFormalesPersona");
            banderaNF = 0;
            filtradosListaVigenciasNoFormales = null;
            tipoListaNF = 0;
        }
    }

    //EXPORTAR
    public void exportPDF() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciasFormalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "VigenciasFormalesPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciasNoFormalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "VigenciasNoFormalesPDF", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexNF = -1;
            secRegistro = null;
        }
    }

    public void exportXLS() throws IOException {
        if (CualTabla == 0) {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciasFormalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarXLS();
            exporter.export(context, tabla, "VigenciasFormalesXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            index = -1;
            secRegistro = null;
        } else {
            DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciasNoFormalesExportar");
            FacesContext context = FacesContext.getCurrentInstance();
            Exporter exporter = new ExportarPDF();
            exporter.export(context, tabla, "VigenciasNoFormalesXLS", false, false, "UTF-8", null, null);
            context.responseComplete();
            indexNF = -1;
            secRegistro = null;
        }
    }

    //LIMPIAR NUEVO REGISTRO
    public void limpiarNuevaVigenciaFormal() {
        nuevaVigenciaFormal = new VigenciasFormales();
        nuevaVigenciaFormal.setTipoeducacion(new TiposEducaciones());
        nuevaVigenciaFormal.setProfesion(new Profesiones());
        nuevaVigenciaFormal.setInstitucion(new Instituciones());
        nuevaVigenciaFormal.setAdiestramientof(new AdiestramientosF());
        index = -1;
        secRegistro = null;


    }

    public void limpiarNuevaVigenciaNoFormal() {

        nuevaVigenciaNoFormal = new VigenciasNoFormales();
        nuevaVigenciaNoFormal.setCurso(new Cursos());
        nuevaVigenciaNoFormal.setInstitucion(new Instituciones());
        nuevaVigenciaNoFormal.setAdiestramientonf(new AdiestramientosNF());
        indexNF = -1;
        secRegistro = null;
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("TIPOEDUCACION")) {
            if (tipoNuevo == 1) {
                TipoEducacion = nuevaVigenciaFormal.getTipoeducacion().getNombre();
            } else if (tipoNuevo == 2) {
                TipoEducacion = duplicarVigenciaFormal.getTipoeducacion().getNombre();
            } else if (Campo.equals("PROFESION")) {
                if (tipoNuevo == 1) {
                    Profesion = nuevaVigenciaFormal.getProfesion().getDescripcion();
                } else if (tipoNuevo == 2) {
                    Profesion = duplicarVigenciaFormal.getProfesion().getDescripcion();
                }
            } else if (Campo.equals("INSTITUCION")) {
                if (tipoNuevo == 1) {
                    Institucion = nuevaVigenciaFormal.getInstitucion().getDescripcion();
                } else if (tipoNuevo == 2) {
                    Institucion = duplicarVigenciaFormal.getInstitucion().getDescripcion();
                }
            } else if (Campo.equals("ADIESTRAMIENTOF")) {
                if (tipoNuevo == 1) {
                    AdiestramientoF = nuevaVigenciaFormal.getAdiestramientof().getDescripcion();
                } else if (tipoNuevo == 2) {
                    AdiestramientoF = duplicarVigenciaFormal.getAdiestramientof().getDescripcion();
                }
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("TIPOEDUCACION")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormal.getTipoeducacion().setNombre(TipoEducacion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormal.getTipoeducacion().setNombre(TipoEducacion);
            }
            for (int i = 0; i < listaTiposEducaciones.size(); i++) {
                if (listaTiposEducaciones.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormal.setTipoeducacion(listaTiposEducaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipoEducacion");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormal.setTipoeducacion(listaTiposEducaciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoEducacion");
                }
                listaTiposEducaciones.clear();
                getListaTiposEducaciones();
            } else {
                context.update("form:tiposEducacionesDialogo");
                context.execute("tiposEducacionesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipoEducacion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoEducacion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PROFESION")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormal.getProfesion().setDescripcion(Profesion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormal.getProfesion().setDescripcion(Profesion);
            }
            for (int i = 0; i < listaProfesiones.size(); i++) {
                if (listaProfesiones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormal.setProfesion(listaProfesiones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaProfesion");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormal.setProfesion(listaProfesiones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarProfesion");
                }
                listaProfesiones.clear();
                getListaProfesiones();
            } else {
                context.update("form:profesionesDialogo");
                context.execute("profesionesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaProfesion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarProfesion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("INSTITUCION")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormal.getInstitucion().setDescripcion(Institucion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormal.getInstitucion().setDescripcion(Institucion);
            }
            for (int i = 0; i < listaInstituciones.size(); i++) {
                if (listaInstituciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormal.setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaInstitucion");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormal.setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarInstitucion");
                }
                listaInstituciones.clear();
                getListaInstituciones();
            } else {
                context.update("form:institucionesDialogo");
                context.execute("institucionesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaInstitucion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarInstitucion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("ADIESTRAMIENTOF")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaFormal.getAdiestramientof().setDescripcion(AdiestramientoF);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaFormal.getAdiestramientof().setDescripcion(AdiestramientoF);
            }
            for (int i = 0; i < listaAdiestramientosFormales.size(); i++) {
                if (listaAdiestramientosFormales.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaFormal.setAdiestramientof(listaAdiestramientosFormales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoAdiestramientoF");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaFormal.setAdiestramientof(listaAdiestramientosFormales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarAdiestramientoF");
                }
                listaAdiestramientosFormales.clear();
                getListaAdiestramientosFormales();
            } else {
                context.update("form:adiestramientosFDialogo");
                context.execute("adiestramientosFDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoAdiestramientoF");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarAdiestramientoF");
                }
            }
        }
    }
// Agregar Nueva Vigencia Formal

    public void agregarNuevaVigenciaFormal() {
        int pasa = 0;
        mensajeValidacion = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        if (nuevaVigenciaFormal.getFechavigencia() == null) {
            System.out.println("Entro a FechaNF");
            mensajeValidacion = " * Fecha \n";
            pasa++;
        }
        if (nuevaVigenciaFormal.getTipoeducacion().getSecuencia() == null) {
            System.out.println("Entro a TipoEducacion");
            mensajeValidacion = mensajeValidacion + " * Tipo de Educacion\n";
            pasa++;
        }
        if (nuevaVigenciaFormal.getProfesion().getSecuencia() == null) {
            System.out.println("Entro a Profesion");
            mensajeValidacion = mensajeValidacion + " * Profesion\n";
            pasa++;
        }
        if (nuevaVigenciaFormal.getInstitucion().getSecuencia() == null) {
            System.out.println("Entro a Institucion");
            mensajeValidacion = mensajeValidacion + " * Institucion \n";
            pasa++;
        }
        if (nuevaVigenciaFormal.getAdiestramientof().getSecuencia() == null) {
            System.out.println("Entro a AdiestramientoF");
            mensajeValidacion = mensajeValidacion + " * Adiestramiento\n";
            pasa++;
        }
        
            



        if (pasa == 0) {
            if (bandera == 1) {

                System.out.println("Desactivar");
                System.out.println("TipoLista= " + tipoLista);
                pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
                pEFechas.setFilterStyle("display: none; visibility: hidden;");
                pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
                pETiposEducaciones.setFilterStyle("display: none; visibility: hidden;");
                pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
                pEProfesiones.setFilterStyle("display: none; visibility: hidden;");
                pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
                pEInstituciones.setFilterStyle("display: none; visibility: hidden;");
                pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
                pEAdiestramientosF.setFilterStyle("display: none; visibility: hidden;");
                pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
                pECalificaciones.setFilterStyle("display: none; visibility: hidden;");
                pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
                pENumerosTarjetas.setFilterStyle("display: none; visibility: hidden;");
                pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
                pEFechasExpediciones.setFilterStyle("display: none; visibility: hidden;");
                pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
                pEFechasVencimientos.setFilterStyle("display: none; visibility: hidden;");
                pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
                pEObservaciones.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
                bandera = 0;
                filtradosListaVigenciasFormales = null;
                tipoLista = 0;

            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS FORMALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaVigenciaFormal.setSecuencia(l);
            nuevaVigenciaFormal.setPersona(persona);
            if (nuevaVigenciaFormal.getTipoeducacion().getSecuencia() == null) {
                nuevaVigenciaFormal.setTipoeducacion(null);
            }
            if (nuevaVigenciaFormal.getProfesion().getSecuencia() == null) {
                nuevaVigenciaFormal.setProfesion(null);
            }
            if (nuevaVigenciaFormal.getInstitucion().getSecuencia() == null) {
                nuevaVigenciaFormal.setInstitucion(null);
            }
            if (nuevaVigenciaFormal.getAdiestramientof().getSecuencia() == null) {
                nuevaVigenciaFormal.setAdiestramientof(null);
            }

            listaVigenciasFormalesCrear.add(nuevaVigenciaFormal);

            listaVigenciasFormales.add(nuevaVigenciaFormal);
            nuevaVigenciaFormal = new VigenciasFormales();
            nuevaVigenciaFormal.setTipoeducacion(new TiposEducaciones());
            nuevaVigenciaFormal.setProfesion(new Profesiones());
            nuevaVigenciaFormal.setInstitucion(new Instituciones());
            nuevaVigenciaFormal.setAdiestramientof(new AdiestramientosF());
            context.update("form:datosVigenciasFormalesPersona");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroVigenciaFormal.hide()");
            index = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevaVigenciaFormal");
            context.execute("validacionNuevaVigenciaFormal.show()");
        }
    }

    //BORRAR VIGENCIA FORMAL
    public void borrarVigenciaFormal() {

        if (index >= 0 && CualTabla == 0) {
            if (tipoLista == 0) {
                if (!listaVigenciasFormalesModificar.isEmpty() && listaVigenciasFormalesModificar.contains(listaVigenciasFormales.get(index))) {
                    int modIndex = listaVigenciasFormalesModificar.indexOf(listaVigenciasFormales.get(index));
                    listaVigenciasFormalesModificar.remove(modIndex);
                    listaVigenciasFormalesBorrar.add(listaVigenciasFormales.get(index));
                } else if (!listaVigenciasFormalesCrear.isEmpty() && listaVigenciasFormalesCrear.contains(listaVigenciasFormales.get(index))) {
                    int crearIndex = listaVigenciasFormalesCrear.indexOf(listaVigenciasFormales.get(index));
                    listaVigenciasFormalesCrear.remove(crearIndex);
                } else {
                    listaVigenciasFormalesBorrar.add(listaVigenciasFormales.get(index));
                }
                listaVigenciasFormales.remove(index);
            }

            if (tipoLista == 1) {
                if (!listaVigenciasFormalesModificar.isEmpty() && listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(index))) {
                    int modIndex = listaVigenciasFormalesModificar.indexOf(filtradosListaVigenciasFormales.get(index));
                    listaVigenciasFormalesModificar.remove(modIndex);
                    listaVigenciasFormalesBorrar.add(filtradosListaVigenciasFormales.get(index));
                } else if (!listaVigenciasFormalesCrear.isEmpty() && listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(index))) {
                    int crearIndex = listaVigenciasFormalesCrear.indexOf(filtradosListaVigenciasFormales.get(index));
                    listaVigenciasFormalesCrear.remove(crearIndex);
                } else {
                    listaVigenciasFormalesBorrar.add(filtradosListaVigenciasFormales.get(index));
                }
                int CIndex = listaVigenciasFormales.indexOf(filtradosListaVigenciasFormales.get(index));
                listaVigenciasFormales.remove(CIndex);
                filtradosListaVigenciasFormales.remove(index);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasFormalesPersona");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        } else if (indexNF >= 0 && CualTabla == 1) {

            if (tipoListaNF == 0) {
                if (!listaVigenciasNoFormalesModificar.isEmpty() && listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormalesModificar.get(indexNF))) {
                    int modIndex = listaVigenciasNoFormalesModificar.indexOf(listaVigenciasNoFormales.get(indexNF));
                    listaVigenciasNoFormalesModificar.remove(modIndex);
                    listaVigenciasNoFormalesBorrar.add(listaVigenciasNoFormales.get(indexNF));
                } else if (!listaVigenciasNoFormalesCrear.isEmpty() && listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(indexNF))) {
                    int crearIndex = listaVigenciasNoFormalesCrear.indexOf(listaVigenciasNoFormales.get(indexNF));
                    listaVigenciasNoFormalesCrear.remove(crearIndex);
                } else {
                    listaVigenciasNoFormalesBorrar.add(listaVigenciasNoFormales.get(indexNF));
                }
                listaVigenciasNoFormales.remove(indexNF);
            }

            if (tipoListaNF == 1) {
                if (!listaVigenciasNoFormalesModificar.isEmpty() && listaVigenciasNoFormalesModificar.contains(filtradosListaVigenciasNoFormales.get(indexNF))) {
                    int modIndex = listaVigenciasNoFormalesModificar.indexOf(filtradosListaVigenciasNoFormales.get(indexNF));
                    listaVigenciasNoFormalesModificar.remove(modIndex);
                    listaVigenciasNoFormalesBorrar.add(filtradosListaVigenciasNoFormales.get(indexNF));
                } else if (!listaVigenciasNoFormalesCrear.isEmpty() && listaVigenciasNoFormalesCrear.contains(filtradosListaVigenciasNoFormales.get(indexNF))) {
                    int crearIndex = listaVigenciasNoFormalesCrear.indexOf(filtradosListaVigenciasNoFormales.get(indexNF));
                    listaVigenciasNoFormalesCrear.remove(crearIndex);
                } else {
                    listaVigenciasNoFormalesBorrar.add(filtradosListaVigenciasNoFormales.get(indexNF));
                }
                int CIndex = listaVigenciasNoFormales.indexOf(filtradosListaVigenciasNoFormales.get(indexNF));
                listaVigenciasNoFormales.remove(CIndex);
                filtradosListaVigenciasNoFormales.remove(indexNF);
                System.out.println("Realizado");
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasNoFormalesPersona");
            indexNF = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    //DUPLICAR VIGENCIAFORMAL
    public void duplicarVF() {
        if (index >= 0 && CualTabla == 0) {
            duplicarVigenciaFormal = new VigenciasFormales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarVigenciaFormal.setSecuencia(l);
                duplicarVigenciaFormal.setFechavigencia(listaVigenciasFormales.get(index).getFechavigencia());
                duplicarVigenciaFormal.setTipoeducacion(listaVigenciasFormales.get(index).getTipoeducacion());
                duplicarVigenciaFormal.setProfesion(listaVigenciasFormales.get(index).getProfesion());
                duplicarVigenciaFormal.setInstitucion(listaVigenciasFormales.get(index).getInstitucion());
                duplicarVigenciaFormal.setAdiestramientof(listaVigenciasFormales.get(index).getAdiestramientof());
                duplicarVigenciaFormal.setCalificacionobtenida(listaVigenciasFormales.get(index).getCalificacionobtenida());
                duplicarVigenciaFormal.setNumerotarjeta(listaVigenciasFormales.get(index).getNumerotarjeta());
                duplicarVigenciaFormal.setFechaexpediciontarjeta(listaVigenciasFormales.get(index).getFechaexpediciontarjeta());
                duplicarVigenciaFormal.setFechavencimientotarjeta(listaVigenciasFormales.get(index).getFechavencimientotarjeta());
                duplicarVigenciaFormal.setObservacion(listaVigenciasFormales.get(index).getObservacion());
            }
            if (tipoLista == 1) {
                duplicarVigenciaFormal.setSecuencia(l);
                duplicarVigenciaFormal.setFechavigencia(filtradosListaVigenciasFormales.get(index).getFechavigencia());
                duplicarVigenciaFormal.setTipoeducacion(filtradosListaVigenciasFormales.get(index).getTipoeducacion());
                duplicarVigenciaFormal.setProfesion(filtradosListaVigenciasFormales.get(index).getProfesion());
                duplicarVigenciaFormal.setInstitucion(filtradosListaVigenciasFormales.get(index).getInstitucion());
                duplicarVigenciaFormal.setAdiestramientof(filtradosListaVigenciasFormales.get(index).getAdiestramientof());
                duplicarVigenciaFormal.setCalificacionobtenida(filtradosListaVigenciasFormales.get(index).getCalificacionobtenida());
                duplicarVigenciaFormal.setNumerotarjeta(filtradosListaVigenciasFormales.get(index).getNumerotarjeta());
                duplicarVigenciaFormal.setFechaexpediciontarjeta(filtradosListaVigenciasFormales.get(index).getFechaexpediciontarjeta());
                duplicarVigenciaFormal.setFechavencimientotarjeta(filtradosListaVigenciasFormales.get(index).getFechavencimientotarjeta());
                duplicarVigenciaFormal.setObservacion(filtradosListaVigenciasFormales.get(index).getObservacion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigenciaFormal");
            context.execute("DuplicarRegistroVigenciaFormal.show()");
            index = -1;
            secRegistro = null;
        } else if (indexNF >= 0 && CualTabla == 1) {
            System.out.println("Entra Duplicar NF");

            duplicarVigenciaNoFormal = new VigenciasNoFormales();
            m++;
            n = BigInteger.valueOf(m);

            if (tipoListaNF == 0) {
                duplicarVigenciaNoFormal.setSecuencia(n);
                duplicarVigenciaNoFormal.setFechavigencia(listaVigenciasNoFormales.get(indexNF).getFechavigencia());
                duplicarVigenciaNoFormal.setCurso(listaVigenciasNoFormales.get(indexNF).getCurso());
                duplicarVigenciaNoFormal.setTitulo(listaVigenciasNoFormales.get(indexNF).getTitulo());
                duplicarVigenciaNoFormal.setInstitucion(listaVigenciasNoFormales.get(indexNF).getInstitucion());
                duplicarVigenciaNoFormal.setAdiestramientonf(listaVigenciasNoFormales.get(indexNF).getAdiestramientonf());
                duplicarVigenciaNoFormal.setCalificacionobtenida(listaVigenciasNoFormales.get(indexNF).getCalificacionobtenida());
                duplicarVigenciaNoFormal.setObservacion(listaVigenciasNoFormales.get(indexNF).getObservacion());
            }
            if (tipoListaNF == 1) {
                duplicarVigenciaNoFormal.setSecuencia(n);
                duplicarVigenciaNoFormal.setFechavigencia(filtradosListaVigenciasNoFormales.get(indexNF).getFechavigencia());
                duplicarVigenciaNoFormal.setCurso(filtradosListaVigenciasNoFormales.get(indexNF).getCurso());
                duplicarVigenciaNoFormal.setTitulo(filtradosListaVigenciasNoFormales.get(indexNF).getTitulo());
                duplicarVigenciaNoFormal.setInstitucion(filtradosListaVigenciasNoFormales.get(indexNF).getInstitucion());
                duplicarVigenciaNoFormal.setAdiestramientonf(filtradosListaVigenciasNoFormales.get(indexNF).getAdiestramientonf());
                duplicarVigenciaNoFormal.setCalificacionobtenida(filtradosListaVigenciasNoFormales.get(indexNF).getCalificacionobtenida());
                duplicarVigenciaNoFormal.setObservacion(filtradosListaVigenciasNoFormales.get(indexNF).getObservacion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigenciaNoFormal");
            context.execute("DuplicarRegistroVigenciaNoFormal.show()");
            indexNF = -1;
            secRegistro = null;

        }
    }

    public void confirmarDuplicar() {

        listaVigenciasFormales.add(duplicarVigenciaFormal);
        listaVigenciasFormalesCrear.add(duplicarVigenciaFormal);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasFormalesPersona");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            System.out.println("Desactivar");
            System.out.println("TipoLista= " + tipoLista);
            pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
            pEFechas.setFilterStyle("display: none; visibility: hidden;");
            pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
            pETiposEducaciones.setFilterStyle("display: none; visibility: hidden;");
            pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
            pEProfesiones.setFilterStyle("display: none; visibility: hidden;");
            pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
            pEInstituciones.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
            pEAdiestramientosF.setFilterStyle("display: none; visibility: hidden;");
            pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
            pECalificaciones.setFilterStyle("display: none; visibility: hidden;");
            pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
            pENumerosTarjetas.setFilterStyle("display: none; visibility: hidden;");
            pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
            pEFechasExpediciones.setFilterStyle("display: none; visibility: hidden;");
            pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
            pEFechasVencimientos.setFilterStyle("display: none; visibility: hidden;");
            pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
            pEObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
            bandera = 0;
            filtradosListaVigenciasFormales = null;
            tipoLista = 0;

        }
        duplicarVigenciaFormal = new VigenciasFormales();
    }
    //LIMPIAR DUPLICAR

    public void limpiarduplicarVigenciaFormal() {
        duplicarVigenciaFormal = new VigenciasFormales();
    }
    //LIMPIAR DUPLICAR NO FORMAL

    public void limpiarduplicarVigenciaNoFormal() {
        duplicarVigenciaNoFormal = new VigenciasNoFormales();
    }

    public void verificarRastro() {
        if (CualTabla == 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("lol");
            if (!listaVigenciasFormales.isEmpty()) {
                if (secRegistro != null) {
                    System.out.println("lol 2");
                    int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASFORMALES");
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
                if (administrarRastros.verificarHistoricosTabla("VIGENCIASFORMALES")) {
                    context.execute("confirmarRastroHistorico.show()");
                } else {
                    context.execute("errorRastroHistorico.show()");
                }

            }
            index = -1;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("NF");
            if (!listaVigenciasNoFormales.isEmpty()) {
                if (secRegistro != null) {
                    System.out.println("NF2");
                    int resultadoNF = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASNOFORMALES");
                    System.out.println("resultado: " + resultadoNF);
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
                if (administrarRastros.verificarHistoricosTabla("VIGENCIASNOFORMALES")) {
                    context.execute("confirmarRastroHistoricoNF.show()");
                } else {
                    context.execute("errorRastroHistoricoNF.show()");
                }

            }
            index = -1;
        }

    }

    public void salir() {

        if (bandera == 1) {
            pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
            pEFechas.setFilterStyle("display: none; visibility: hidden;");
            pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
            pETiposEducaciones.setFilterStyle("display: none; visibility: hidden;");
            pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
            pEProfesiones.setFilterStyle("display: none; visibility: hidden;");
            pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
            pEInstituciones.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
            pEAdiestramientosF.setFilterStyle("display: none; visibility: hidden;");
            pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
            pECalificaciones.setFilterStyle("display: none; visibility: hidden;");
            pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
            pENumerosTarjetas.setFilterStyle("display: none; visibility: hidden;");
            pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
            pEFechasExpediciones.setFilterStyle("display: none; visibility: hidden;");
            pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
            pEFechasVencimientos.setFilterStyle("display: none; visibility: hidden;");
            pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
            pEObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
            bandera = 0;
            filtradosListaVigenciasFormales = null;
            tipoLista = 0;
        }

        listaVigenciasFormalesBorrar.clear();
        listaVigenciasFormalesCrear.clear();
        listaVigenciasFormalesModificar.clear();
        index = -1;
        secRegistro = null;
        //  k = 0;
        listaVigenciasFormales = null;
        guardado = true;
        permitirIndex = true;

    }

    //CANCELAR MODIFICACIONES
    public void cancelarModificacion() {


        if (bandera == 1) {
            pEFechas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechas");
            pEFechas.setFilterStyle("display: none; visibility: hidden;");
            pETiposEducaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pETiposEducaciones");
            pETiposEducaciones.setFilterStyle("display: none; visibility: hidden;");
            pEProfesiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEProfesiones");
            pEProfesiones.setFilterStyle("display: none; visibility: hidden;");
            pEInstituciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEInstituciones");
            pEInstituciones.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEAdiestramientosF");
            pEAdiestramientosF.setFilterStyle("display: none; visibility: hidden;");
            pECalificaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pECalificaciones");
            pECalificaciones.setFilterStyle("display: none; visibility: hidden;");
            pENumerosTarjetas = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pENumerosTarjetas");
            pENumerosTarjetas.setFilterStyle("display: none; visibility: hidden;");
            pEFechasExpediciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasExpediciones");
            pEFechasExpediciones.setFilterStyle("display: none; visibility: hidden;");
            pEFechasVencimientos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEFechasVencimientos");
            pEFechasVencimientos.setFilterStyle("display: none; visibility: hidden;");
            pEObservaciones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasFormalesPersona:pEObservaciones");
            pEObservaciones.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasFormalesPersona");
            bandera = 0;
            filtradosListaVigenciasFormales = null;
            tipoLista = 0;
        }



        if (banderaNF == 1) {
            pEFechasNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEFechasNF");

            pEFechasNF.setFilterStyle("display: none; visibility: hidden;");
            pECursosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECursosNF");
            pECursosNF.setFilterStyle("display: none; visibility: hidden;");
            pETitulosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pETitulosNF");
            pETitulosNF.setFilterStyle("display: none; visibility: hidden;");
            pEInstitucionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEInstitucionesNF");
            pEInstitucionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEAdiestramientosNF");
            pEAdiestramientosNF.setFilterStyle("display: none; visibility: hidden;");
            pECalificacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECalificacionesNF");
            pECalificacionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEObservacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEObservacionesNF");
            pEObservacionesNF.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasNoFormalesPersona");
            banderaNF = 0;
            filtradosListaVigenciasNoFormales = null;
            tipoListaNF = 0;
        }
        listaVigenciasFormalesBorrar.clear();
        listaVigenciasFormalesCrear.clear();
        listaVigenciasFormalesModificar.clear();
        index = -1;
        secRegistro = null;

        listaVigenciasFormales = null;


        listaVigenciasNoFormalesBorrar.clear();
        listaVigenciasNoFormalesCrear.clear();
        listaVigenciasNoFormalesModificar.clear();
        indexNF = -1;

        listaVigenciasNoFormales = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasFormalesPersona");
        context.update("form:datosVigenciasNoFormalesPersona");
    }

    //GUARDAR
    public void guardarCambiosVigenciasFormales() {
        if (CualTabla == 0) {
            System.out.println("Guardado: " + guardado);
            if (guardado == false) {
                System.out.println("Realizando Operaciones VigenciasFormales");
                if (!listaVigenciasFormalesBorrar.isEmpty()) {
                    for (int i = 0; i < listaVigenciasFormalesBorrar.size(); i++) {
                        System.out.println("Borrando...");
                        if (listaVigenciasFormalesBorrar.get(i).getAdiestramientof().getSecuencia() == null) {
                            listaVigenciasFormalesBorrar.get(i).setAdiestramientof(null);
                            administrarVigenciasFormales.borrarVigenciaFormal(listaVigenciasFormalesBorrar.get(i));
                        } else {

                            administrarVigenciasFormales.borrarVigenciaFormal(listaVigenciasFormalesBorrar.get(i));
                        }

                        System.out.println("Entra");
                        listaVigenciasFormalesBorrar.clear();
                    }
                }
                if (!listaVigenciasFormalesCrear.isEmpty()) {
                    for (int i = 0; i < listaVigenciasFormalesCrear.size(); i++) {
                        System.out.println("Creando...");
                        System.out.println(listaVigenciasFormalesCrear.size());
                        if (listaVigenciasFormalesCrear.get(i).getNumerotarjeta() != null) {
                            listaVigenciasFormalesCrear.get(i).setTarjetaprofesional("S");
                        } else {
                            listaVigenciasFormalesCrear.get(i).setTarjetaprofesional("N");
                        }

                        if (listaVigenciasFormalesCrear.get(i).getAdiestramientof().getDescripcion() != null) {
                            listaVigenciasFormalesCrear.get(i).setAcargo("S");
                        } else {
                            listaVigenciasFormalesCrear.get(i).setAcargo("N");
                        }


                        if (listaVigenciasFormalesCrear.get(i).getAdiestramientof().getSecuencia() == null) {
                            listaVigenciasFormalesCrear.get(i).setAdiestramientof(null);
                            administrarVigenciasFormales.crearVigenciaFormal(listaVigenciasFormalesCrear.get(i));
                        } else {

                            administrarVigenciasFormales.crearVigenciaFormal(listaVigenciasFormalesCrear.get(i));
                        }

                    }


                    System.out.println("LimpiaLista");
                    listaVigenciasFormalesCrear.clear();
                }
                if (!listaVigenciasFormalesModificar.isEmpty()) {
                    administrarVigenciasFormales.modificarVigenciaFormal(listaVigenciasFormalesModificar);
                    listaVigenciasFormalesModificar.clear();
                }

                System.out.println("Se guardaron los datos con exito");
                listaVigenciasFormales = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciasFormalesPersona");
                guardado = true;
                permitirIndex = true;
                RequestContext.getCurrentInstance().update("form:aceptar");
                //  k = 0;
            }
            System.out.println("Tamaño lista: " + listaVigenciasFormalesCrear.size());
            System.out.println("Valor k: " + k);
            index = -1;
            secRegistro = null;

        } else {

            System.out.println("Está en la Tabla de Abajo");


            if (guardado == false) {
                System.out.println("Realizando Operaciones VigenciasNoFormales");
                if (!listaVigenciasNoFormalesBorrar.isEmpty()) {
                    for (int i = 0; i < listaVigenciasNoFormalesBorrar.size(); i++) {
                        System.out.println("Borrando...");
                        if (listaVigenciasNoFormalesBorrar.get(i).getAdiestramientonf().getSecuencia() == null) {
                            listaVigenciasNoFormalesBorrar.get(i).setAdiestramientonf(null);
                            administrarVigenciasNoFormales.borrarVigenciaNoFormal(listaVigenciasNoFormalesBorrar.get(i));
                        } else {

                            administrarVigenciasNoFormales.borrarVigenciaNoFormal(listaVigenciasNoFormalesBorrar.get(i));
                        }

                        System.out.println("Entra");
                        listaVigenciasNoFormalesBorrar.clear();
                    }
                }
                if (!listaVigenciasNoFormalesCrear.isEmpty()) {
                    for (int i = 0; i < listaVigenciasNoFormalesCrear.size(); i++) {
                        System.out.println("Creando...");
                        System.out.println(listaVigenciasNoFormalesCrear.size());
                        if (listaVigenciasNoFormalesCrear.get(i).getAdiestramientonf().getDesccripcion() != null) {
                            listaVigenciasNoFormalesCrear.get(i).setAcargo("S");
                        } else {
                            listaVigenciasNoFormalesCrear.get(i).setAcargo("N");
                        }


                        if (listaVigenciasNoFormalesCrear.get(i).getAdiestramientonf().getSecuencia() == null) {
                            listaVigenciasNoFormalesCrear.get(i).setAdiestramientonf(null);
                            administrarVigenciasNoFormales.crearVigenciaNoFormal(listaVigenciasNoFormalesCrear.get(i));
                        } else {

                            administrarVigenciasNoFormales.crearVigenciaNoFormal(listaVigenciasNoFormalesCrear.get(i));
                        }
                    }

                    System.out.println("LimpiaLista");
                    listaVigenciasNoFormalesCrear.clear();
                }
                if (!listaVigenciasNoFormalesModificar.isEmpty()) {
                    System.out.println("Lista Vigencias No Formales: " + listaVigenciasNoFormalesModificar.size());
                    administrarVigenciasNoFormales.modificarVigenciaNoFormal(listaVigenciasNoFormalesModificar);

                    listaVigenciasNoFormalesModificar.clear();
                }

                System.out.println("Se guardaron los datos con exito");
                listaVigenciasNoFormales = null;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosVigenciasNoFormalesPersona");
                guardado = true;
                permitirIndex = true;
                RequestContext.getCurrentInstance().update("form:aceptar");
                //  k = 0;
            }
            System.out.println("Valor k: " + k);
            indexNF = -1;
            secRegistro = null;
        }

    }

    //GUARDAR TODO
    public void guardarTodo() {

        System.out.println("Guardado: " + guardado);
        System.out.println("Guardadondo Primera Tabla: ");
        if (guardado == false) {
            System.out.println("Realizando Operaciones VigenciasFormales");
            if (!listaVigenciasFormalesBorrar.isEmpty()) {
                for (int i = 0; i < listaVigenciasFormalesBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listaVigenciasFormalesBorrar.get(i).getAdiestramientof().getSecuencia() == null) {
                        listaVigenciasFormalesBorrar.get(i).setAdiestramientof(null);
                        administrarVigenciasFormales.borrarVigenciaFormal(listaVigenciasFormalesBorrar.get(i));
                    } else {

                        administrarVigenciasFormales.borrarVigenciaFormal(listaVigenciasFormalesBorrar.get(i));
                    }

                    listaVigenciasFormalesBorrar.clear();
                }
            }
            if (!listaVigenciasNoFormalesBorrar.isEmpty()) {
                for (int i = 0; i < listaVigenciasNoFormalesBorrar.size(); i++) {
                    System.out.println("Borrando...");
                    if (listaVigenciasNoFormalesBorrar.get(i).getAdiestramientonf().getSecuencia() == null) {
                        listaVigenciasNoFormalesBorrar.get(i).setAdiestramientonf(null);
                        administrarVigenciasNoFormales.borrarVigenciaNoFormal(listaVigenciasNoFormalesBorrar.get(i));
                    } else {

                        administrarVigenciasNoFormales.borrarVigenciaNoFormal(listaVigenciasNoFormalesBorrar.get(i));
                    }

                    System.out.println("Entra");
                    listaVigenciasNoFormalesBorrar.clear();
                }
            }
            if (!listaVigenciasFormalesCrear.isEmpty()) {
                for (int i = 0; i < listaVigenciasFormalesCrear.size(); i++) {
                    System.out.println("Creando...");
                    System.out.println(listaVigenciasFormalesCrear.size());
                    if (listaVigenciasFormalesCrear.get(i).getNumerotarjeta() != null) {
                        listaVigenciasFormalesCrear.get(i).setTarjetaprofesional("S");
                    } else {
                        listaVigenciasFormalesCrear.get(i).setTarjetaprofesional("N");
                    }

                    if (listaVigenciasFormalesCrear.get(i).getAdiestramientof().getDescripcion() != null) {
                        listaVigenciasFormalesCrear.get(i).setAcargo("S");
                    } else {
                        listaVigenciasFormalesCrear.get(i).setAcargo("N");
                    }


                    if (listaVigenciasFormalesCrear.get(i).getAdiestramientof().getSecuencia() == null) {
                        listaVigenciasFormalesCrear.get(i).setAdiestramientof(null);
                        administrarVigenciasFormales.crearVigenciaFormal(listaVigenciasFormalesCrear.get(i));
                    } else {
                        administrarVigenciasFormales.crearVigenciaFormal(listaVigenciasFormalesCrear.get(i));
                    }

                }
                System.out.println("LimpiaLista");
                listaVigenciasFormalesCrear.clear();
            }

            if (!listaVigenciasNoFormalesCrear.isEmpty()) {
                for (int i = 0; i < listaVigenciasNoFormalesCrear.size(); i++) {
                    System.out.println("Creando...");
                    System.out.println(listaVigenciasNoFormalesCrear.size());
                    if (listaVigenciasNoFormalesCrear.get(i).getAdiestramientonf().getDesccripcion() != null) {
                        listaVigenciasNoFormalesCrear.get(i).setAcargo("S");
                    } else {
                        listaVigenciasNoFormalesCrear.get(i).setAcargo("N");
                    }


                    if (listaVigenciasNoFormalesCrear.get(i).getAdiestramientonf().getSecuencia() == null) {
                        listaVigenciasNoFormalesCrear.get(i).setAdiestramientonf(null);
                        administrarVigenciasNoFormales.crearVigenciaNoFormal(listaVigenciasNoFormalesCrear.get(i));
                    } else {

                        administrarVigenciasNoFormales.crearVigenciaNoFormal(listaVigenciasNoFormalesCrear.get(i));
                    }
                }

                System.out.println("LimpiaLista");
                listaVigenciasNoFormalesCrear.clear();
            }

            if (!listaVigenciasFormalesModificar.isEmpty()) {
                administrarVigenciasFormales.modificarVigenciaFormal(listaVigenciasFormalesModificar);
                listaVigenciasFormalesModificar.clear();
            }
            if (!listaVigenciasNoFormalesModificar.isEmpty()) {
                administrarVigenciasNoFormales.modificarVigenciaNoFormal(listaVigenciasNoFormalesModificar);
                listaVigenciasNoFormalesModificar.clear();
            }

            System.out.println("Se guardaron los datos con exito");
            listaVigenciasFormales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasFormalesPersona");
            guardado = true;
            permitirIndex = true;
            RequestContext.getCurrentInstance().update("form:aceptar");

        }

        indexNF = -1;
        secRegistro = null;
        index = -1;



    }

    //<--------------------------------------------METODOS VIGENCIAS NO FORMALES--------------------------------------------->
    //AUTOCOMPLETAR
    public void modificarVigenciasNoFormales(int indiceNF, String confirmarCambio, String valorConfirmar) {
        indexNF = indiceNF;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("N")) {
            if (tipoListaNF == 0) {
                if (!listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(indiceNF))) {

                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indiceNF));
                    } else if (!listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormales.get(indiceNF))) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexNF = -1;
                secRegistro = null;

            } else {
                if (!listaVigenciasNoFormalesCrear.contains(filtradosListaVigenciasNoFormales.get(indiceNF))) {

                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(indiceNF));
                    } else if (!listaVigenciasNoFormalesModificar.contains(filtradosListaVigenciasNoFormales.get(indiceNF))) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexNF = -1;
                secRegistro = null;
            }
            context.update("form:datosVigenciasNoFormalesPersona");
        } else if (confirmarCambio.equalsIgnoreCase("CURSO")) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(indiceNF).getCurso().setNombre(Curso);
            } else {
                filtradosListaVigenciasNoFormales.get(indiceNF).getCurso().setNombre(Curso);
            }

            for (int i = 0; i < listaCursos.size(); i++) {
                if (listaCursos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaNF == 0) {
                    listaVigenciasNoFormales.get(indiceNF).setCurso(listaCursos.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasNoFormales.get(indiceNF).setCurso(listaCursos.get(indiceUnicoElemento));
                }
                listaCursos.clear();
                getListaCursos();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:cursosDialogo");
                context.execute("cursosDialogo.show()");
                tipoActualizacion = 0;
            }

        } else if (confirmarCambio.equalsIgnoreCase("INSTITUCION")) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(indiceNF).getInstitucion().setDescripcion(Institucion);
            } else {
                filtradosListaVigenciasNoFormales.get(indiceNF).getInstitucion().setDescripcion(Institucion);
            }
            for (int i = 0; i < listaInstituciones.size(); i++) {
                if (listaInstituciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaNF == 0) {
                    listaVigenciasNoFormales.get(indiceNF).setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasNoFormales.get(indiceNF).setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                }
                listaInstituciones.clear();
                getListaInstituciones();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:institucionesDialogo");
                context.execute("institucionesDialogo.show()");
                tipoActualizacion = 0;
            }
        } else if (confirmarCambio.equalsIgnoreCase("ADIESTRAMENTOSNF")) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(indiceNF).getAdiestramientonf().setDesccripcion(AdiestramientoNF);
            } else {
                filtradosListaVigenciasNoFormales.get(indiceNF).getAdiestramientonf().setDesccripcion(AdiestramientoNF);
            }
            for (int i = 0; i < listaAdiestramientosNoFormales.size(); i++) {
                if (listaAdiestramientosNoFormales.get(i).getDesccripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaNF == 0) {
                    listaVigenciasNoFormales.get(indiceNF).setAdiestramientonf(listaAdiestramientosNoFormales.get(indiceUnicoElemento));
                } else {
                    filtradosListaVigenciasNoFormales.get(indiceNF).setAdiestramientonf(listaAdiestramientosNoFormales.get(indiceUnicoElemento));
                }
                listaAdiestramientosNoFormales.clear();
                getListaAdiestramientosNoFormales();
            } else {
                permitirIndex = false;
                context.update("formularioDialogos:adiestramientosNFDialogo");
                context.execute("adiestramientosNFDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaNF == 0) {
                if (!listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(indiceNF))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indiceNF));
                    } else if (!listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormales.get(indiceNF))) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexNF = -1;
                secRegistro = null;
            } else {
                if (!listaVigenciasFormalesCrear.contains(filtradosListaVigenciasFormales.get(indiceNF))) {

                    if (listaVigenciasFormalesModificar.isEmpty()) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indiceNF));
                    } else if (!listaVigenciasFormalesModificar.contains(filtradosListaVigenciasFormales.get(indiceNF))) {
                        listaVigenciasFormalesModificar.add(filtradosListaVigenciasFormales.get(indiceNF));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexNF = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciasNoFormalesPersona");
    }

    //Ubicacion Celda.
    public void cambiarIndiceNF(int indiceNF, int celdaNF) {

        if (permitirIndex == true) {
            indexNF = indiceNF;
            cualCelda = celdaNF;
            CualTabla = 1;
            tablaImprimir = ":formExportar:datosVigenciasNoFormalesExportar";
            cualNuevo = ":formularioDialogos:nuevaVigenciaFormal";
            cualInsertar = "formularioDialogos:NuevoRegistroVigenciaNoFormal";
            nombreArchivo = "VigenciasNoFormalesXML";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:exportarXML");

            if (tipoListaNF == 0) {
                secRegistro = listaVigenciasNoFormales.get(indexNF).getSecuencia();
                if (cualCelda == 1) {
                    Curso = listaVigenciasNoFormales.get(indexNF).getCurso().getNombre();
                } else if (cualCelda == 3) {
                    Institucion = listaVigenciasNoFormales.get(indexNF).getInstitucion().getDescripcion();
                } else if (cualCelda == 4) {
                    AdiestramientoNF = listaVigenciasNoFormales.get(indexNF).getAdiestramientonf().getDesccripcion();
                }
            } else {
                secRegistro = filtradosListaVigenciasNoFormales.get(indexNF).getSecuencia();
                if (cualCelda == 1) {
                    Curso = filtradosListaVigenciasNoFormales.get(indexNF).getCurso().getNombre();
                } else if (cualCelda == 3) {
                    Institucion = filtradosListaVigenciasNoFormales.get(indexNF).getInstitucion().getDescripcion();
                } else if (cualCelda == 4) {
                    AdiestramientoNF = filtradosListaVigenciasNoFormales.get(indexNF).getAdiestramientonf().getDesccripcion();
                }
            }
        }
    }

    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
    public void asignarIndexNF(Integer indiceNF, int dlg, int LND) {
        index = indiceNF;
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
            context.update("form:cursosDialogo");
            context.execute("cursosDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:institucionesNFDialogo");
            context.execute("institucionesNFDialogo.show()");
        } else if (dlg == 3) {
            context.update("form:adiestramientosNFDialogo");
            context.execute("adiestramientosNFDialogo.show()");
        }

    }

    public void actualizarCursos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(index).setCurso(seleccionCursos);
                if (!listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(index))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(index));
                    } else if (!listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormales.get(index))) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasNoFormales.get(index).setCurso(seleccionCursos);
                if (!listaVigenciasNoFormalesCrear.contains(filtradosListaVigenciasNoFormales.get(index))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(index));
                    } else if (!listaVigenciasNoFormalesModificar.contains(filtradosListaVigenciasNoFormales.get(index))) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasNoFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaNoFormal.setCurso(seleccionCursos);
            context.update("formularioDialogos:nuevaVigenciaNoFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaNoFormal.setCurso(seleccionCursos);
            context.update("formularioDialogos:duplicarVigenciaNoFormal");
        }
        filtradoslistaCursos = null;
        seleccionCursos = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("cursosDialogo.hide()");
        context.reset("formularioDialogos:LOVCursos:globalFilter");
        context.update("formularioDialogos:LOVCursos");
    }

    public void actualizarInstitucionesNF() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(indexNF).setInstitucion(seleccionInstituciones);
                if (!listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(indexNF))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indexNF));
                    } else if (!listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormales.get(indexNF))) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(indexNF));
                    }
                }
            } else {
                filtradosListaVigenciasNoFormales.get(indexNF).setInstitucion(seleccionInstituciones);
                if (!listaVigenciasNoFormalesCrear.contains(filtradosListaVigenciasNoFormales.get(indexNF))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(indexNF));
                    } else if (!listaVigenciasNoFormalesModificar.contains(filtradosListaVigenciasNoFormales.get(indexNF))) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(indexNF));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasNoFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaNoFormal.setInstitucion(seleccionInstituciones);
            context.update("formularioDialogos:nuevaVigenciaNoFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaNoFormal.setInstitucion(seleccionInstituciones);
            context.update("formularioDialogos:duplicarVigenciaNoFormal");
        }
        filtradoslistaInstituciones = null;
        seleccionInstituciones = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("institucionesNFDialogo.hide()");
        context.reset("formularioDialogos:LOVInstitucionesNF:globalFilter");
        context.update("formularioDialogos:LOVInstitucionesNF");
    }

    public void actualizarAdiestramientoNF() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoActualizacion == 0) {
            if (tipoListaNF == 0) {
                listaVigenciasNoFormales.get(index).setAdiestramientonf(seleccionAdiestramientosNoFormales);
                if (!listaVigenciasNoFormalesCrear.contains(listaVigenciasNoFormales.get(index))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(index));
                    } else if (!listaVigenciasNoFormalesModificar.contains(listaVigenciasNoFormales.get(index))) {
                        listaVigenciasNoFormalesModificar.add(listaVigenciasNoFormales.get(index));
                    }
                }
            } else {
                filtradosListaVigenciasNoFormales.get(index).setAdiestramientonf(seleccionAdiestramientosNoFormales);
                if (!listaVigenciasNoFormalesCrear.contains(filtradosListaVigenciasNoFormales.get(index))) {
                    if (listaVigenciasNoFormalesModificar.isEmpty()) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(index));
                    } else if (!listaVigenciasNoFormalesModificar.contains(filtradosListaVigenciasNoFormales.get(index))) {
                        listaVigenciasNoFormalesModificar.add(filtradosListaVigenciasNoFormales.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            context.update("form:datosVigenciasNoFormalesPersona");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaNoFormal.setAdiestramientonf(seleccionAdiestramientosNoFormales);
            context.update("formularioDialogos:nuevaVigenciaNoFormal");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaNoFormal.setAdiestramientonf(seleccionAdiestramientosNoFormales);
            context.update("formularioDialogos:duplicarVigenciaNoFormal");
        }
        filtradoslistaAdiestramientosNoFormales = null;
        seleccionAdiestramientosNoFormales = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        context.execute("adiestramientosNFDialogo.hide()");
        context.reset("formularioDialogos:LOVAdiestramientosNF:globalFilter");
        context.update("formularioDialogos:LOVAdiestramientosNF");
    }

    public void cancelarCambioInstitucionesNF() {
        filtradoslistaInstituciones = null;
        seleccionInstituciones = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioCursoNF() {
        filtradoslistaCursos = null;
        seleccionCursos = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void cancelarCambioAdiestramientoNF() {
        filtradoslistaAdiestramientosNoFormales = null;
        seleccionAdiestramientosNoFormales = null;
        aceptar = true;
        indexNF = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        cualCelda = -1;
        permitirIndex = true;
    }

    public void autocompletarNuevoyDuplicadoNF(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("CURSO")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaNoFormal.getCurso().setNombre(Curso);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaNoFormal.getCurso().setNombre(Curso);
            }
            for (int i = 0; i < listaCursos.size(); i++) {
                if (listaCursos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaNoFormal.setCurso(listaCursos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoCursoNF");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaNoFormal.setCurso(listaCursos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarCursoNF");
                }
                listaCursos.clear();
                getListaCursos();
            } else {
                context.update("form:cursosDialogo");
                context.execute("cursosDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoCursoNF");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarCursoNF");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("INSTITUCIONNF")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaNoFormal.getInstitucion().setDescripcion(Institucion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaNoFormal.getInstitucion().setDescripcion(Institucion);
            }
            for (int i = 0; i < listaInstituciones.size(); i++) {
                if (listaInstituciones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaNoFormal.setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaInstitucionNF");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaNoFormal.setInstitucion(listaInstituciones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarInstitucionNF");
                }
                listaInstituciones.clear();
                getListaInstituciones();
            } else {
                context.update("form:institucionesNFDialogo");
                context.execute("institucionesNFDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaInstitucionNF");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarInstitucionNF");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("ADIESTRAMIENTONF")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaNoFormal.getAdiestramientonf().setDesccripcion(AdiestramientoNF);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaNoFormal.getAdiestramientonf().setDesccripcion(AdiestramientoNF);
            }
            for (int i = 0; i < listaAdiestramientosNoFormales.size(); i++) {
                if (listaAdiestramientosNoFormales.get(i).getDesccripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaNoFormal.setAdiestramientonf(listaAdiestramientosNoFormales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoAdiestramientoNF");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaNoFormal.setAdiestramientonf(listaAdiestramientosNoFormales.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarAdiestramientoNF");
                }
                listaAdiestramientosNoFormales.clear();
                getListaAdiestramientosNoFormales();
            } else {
                context.update("form:adiestramientosNFDialogo");
                context.execute("adiestramientosNFDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoAdiestramientoNF");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarAdiestramientoNF");
                }
            }
        }
    }

    public void valoresBackupAutocompletarNF(int tipoNuevo, String Campo) {
        if (Campo.equals("CURSO")) {
            if (tipoNuevo == 1) {
                Curso = nuevaVigenciaNoFormal.getCurso().getNombre();
            } else if (tipoNuevo == 2) {
                Curso = duplicarVigenciaNoFormal.getCurso().getNombre();
            } else if (Campo.equals("iNSTITUCIONNF")) {
                if (tipoNuevo == 1) {
                    Institucion = nuevaVigenciaNoFormal.getInstitucion().getDescripcion();
                } else if (tipoNuevo == 2) {
                    Institucion = duplicarVigenciaNoFormal.getInstitucion().getDescripcion();
                }
            } else if (Campo.equals("ADIESTRAMIENTONF")) {
                if (tipoNuevo == 1) {
                    AdiestramientoNF = nuevaVigenciaNoFormal.getAdiestramientonf().getDesccripcion();
                } else if (tipoNuevo == 2) {
                    AdiestramientoNF = duplicarVigenciaNoFormal.getAdiestramientonf().getDesccripcion();
                }
            }
        }
    }

    //CREAR NUEVA VIGENCIA NO FORMAL
    public void agregarNuevaVigenciaNoFormal() {
        int pasa = 0;
        mensajeValidacionNF = " ";
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("Tamaño Lista Vigencias NF Modificar" + listaVigenciasNoFormalesModificar.size());

        if (nuevaVigenciaNoFormal.getFechavigencia() == null) {
            System.out.println("Entro a Fecha");
            mensajeValidacionNF = " * Fecha \n";
            pasa++;
        }
        if (nuevaVigenciaNoFormal.getCurso().getSecuencia() == null) {
            System.out.println("Entro a Curso");
            mensajeValidacionNF = mensajeValidacionNF + " * Curso \n";
            pasa++;
        }

        if (nuevaVigenciaNoFormal.getInstitucion().getSecuencia() == null) {
            System.out.println("Entro a Institucion");
            mensajeValidacionNF = mensajeValidacionNF + " * Institucion \n";
            pasa++;
        }

        if (nuevaVigenciaNoFormal.getAdiestramientonf().getSecuencia() == null) {
            System.out.println("Entro a AdiestramientoNF");
            mensajeValidacionNF = mensajeValidacionNF + " * Adiestramiento\n";
            pasa++;
        }


        if (pasa == 0) {
            if (bandera == 1 && CualTabla == 1) {

                pEFechasNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEFechasNF");
                pEFechasNF.setFilterStyle("display: none; visibility: hidden;");
                pECursosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECursosNF");
                pECursosNF.setFilterStyle("display: none; visibility: hidden;");
                pETitulosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pETitulosNF");
                pETitulosNF.setFilterStyle("display: none; visibility: hidden;");
                pEInstitucionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEInstitucionesNF");
                pEInstitucionesNF.setFilterStyle("display: none; visibility: hidden;");
                pEAdiestramientosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEAdiestramientosNF");
                pEAdiestramientosNF.setFilterStyle("display: none; visibility: hidden;");
                pECalificacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECalificacionesNF");
                pECalificacionesNF.setFilterStyle("display: none; visibility: hidden;");
                pEObservacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEObservacionesNF");
                pEObservacionesNF.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciasNoFormalesPersona");
                bandera = 0;
                filtradosListaVigenciasNoFormales = null;
                tipoListaNF = 0;
            }
            //AGREGAR REGISTRO A LA LISTA VIGENCIAS FORMALES.
            k++;
            l = BigInteger.valueOf(k);
            nuevaVigenciaNoFormal.setSecuencia(l);
            nuevaVigenciaNoFormal.setPersona(persona);
            if (nuevaVigenciaNoFormal.getCurso().getSecuencia() == null) {
                nuevaVigenciaNoFormal.setCurso(null);
            }
            if (nuevaVigenciaNoFormal.getInstitucion().getSecuencia() == null) {
                nuevaVigenciaNoFormal.setInstitucion(null);
            }
            if (nuevaVigenciaNoFormal.getAdiestramientonf().getSecuencia() == null) {
                nuevaVigenciaNoFormal.setAdiestramientonf(null);
            }

            listaVigenciasNoFormalesCrear.add(nuevaVigenciaNoFormal);

            listaVigenciasNoFormales.add(nuevaVigenciaNoFormal);
            nuevaVigenciaNoFormal = new VigenciasNoFormales();
            nuevaVigenciaNoFormal.setCurso(new Cursos());
            nuevaVigenciaNoFormal.setInstitucion(new Instituciones());
            nuevaVigenciaNoFormal.setAdiestramientonf(new AdiestramientosNF());

            context.update("form:datosVigenciasNoFormalesPersona");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            context.execute("NuevoRegistroVigenciaNoFormal.hide()");
            indexNF = -1;
            secRegistro = null;
        } else {
            context.update("formularioDialogos:validacionNuevaVigenciaNoFormal");
            context.execute("validacionNuevaVigenciaNoFormal.show()");
        }
    }

    public void chiste() {
        RequestContext context = RequestContext.getCurrentInstance();

        if ((listaVigenciasFormales.isEmpty() || listaVigenciasNoFormales.isEmpty())) {

            context.update("formularioDialogos:elegirTabla");
            context.execute("elegirTabla.show()");
        } else if (CualTabla == 0) {

            context.update("formularioDialogos:NuevoRegistroVigenciaFormal");
            context.execute("NuevoRegistroVigenciaFormal.show()");
        } else if (CualTabla == 1) {
            context.update("formularioDialogos:NuevoRegistroVigenciaNoFormal");
            context.execute("NuevoRegistroVigenciaNoFormal.show()");
        }
    }

    public void confirmarDuplicarNF() {

        listaVigenciasNoFormales.add(duplicarVigenciaNoFormal);
        listaVigenciasNoFormalesCrear.add(duplicarVigenciaNoFormal);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasNoFormalesPersona");
        indexNF = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {

            pEFechasNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEFechasNF");
            pEFechasNF.setFilterStyle("display: none; visibility: hidden;");
            pECursosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECursosNF");
            pECursosNF.setFilterStyle("display: none; visibility: hidden;");
            pETitulosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pETitulosNF");
            pETitulosNF.setFilterStyle("display: none; visibility: hidden;");
            pEInstitucionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEInstitucionesNF");
            pEInstitucionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEAdiestramientosNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEAdiestramientosNF");
            pEAdiestramientosNF.setFilterStyle("display: none; visibility: hidden;");
            pECalificacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pECalificacionesNF");
            pECalificacionesNF.setFilterStyle("display: none; visibility: hidden;");
            pEObservacionesNF = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasNoFormalesPersona:pEObservacionesNF");
            pEObservacionesNF.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasNoFormalesPersona");
            bandera = 0;
            filtradosListaVigenciasNoFormales = null;
            tipoListaNF = 0;

        }
        duplicarVigenciaNoFormal = new VigenciasNoFormales();
    }

    public void dialogoVigenciasFormales() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasFormalesPersona");
        context.execute("NuevoRegistroVigenciaFormal.show()");
    }

    public void dialogoVigenciasNoFormales() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasNoFormalesPersona");
        context.execute("NuevoRegistroVigenciaNoFormal.show()");

    }

    //<--------------------------------------------FIN METODOS VIGENCIAS NO FORMALES ----------------------------------------->
//GETTER & SETTER
    public Personas getPersona() {
        if (persona == null) {
            persona = administrarVigenciasFormales.encontrarPersona(secuenciaPersona);
        }
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

    public BigInteger getSecuenciaPersona() {
        return secuenciaPersona;
    }

    public void setSecuenciaPersona(BigInteger secuenciaPersona) {
        this.secuenciaPersona = secuenciaPersona;
    }

    public List<VigenciasFormales> getListaVigenciasFormales() {
        if (listaVigenciasFormales == null) {
            listaVigenciasFormales = administrarVigenciasFormales.vigenciasFormalesPersona(secuenciaPersona);
        }
        return listaVigenciasFormales;
    }

    public void setListaVigenciasFormales(List<VigenciasFormales> listaVigenciasFormales) {
        this.listaVigenciasFormales = listaVigenciasFormales;
    }

    public List<VigenciasFormales> getFiltradosListaVigenciasFormales() {
        return filtradosListaVigenciasFormales;
    }

    public void setFiltradosListaVigenciasFormales(List<VigenciasFormales> filtradosListaVigenciasFormales) {
        this.filtradosListaVigenciasFormales = filtradosListaVigenciasFormales;
    }

    public List<TiposEducaciones> getListaTiposEducaciones() {
        if (listaTiposEducaciones == null) {
            listaTiposEducaciones = administrarVigenciasFormales.lovTiposEducaciones();
        }
        return listaTiposEducaciones;
    }

    public void setListaEducaciones(List<TiposEducaciones> listaEducaciones) {
        this.listaTiposEducaciones = listaEducaciones;
    }

    public List<TiposEducaciones> getFiltradoslistaTiposEducaciones() {
        return filtradoslistaTiposEducaciones;
    }

    public void setFiltradoslistaTiposEducaciones(List<TiposEducaciones> filtradoslistaTiposEducaciones) {
        this.filtradoslistaTiposEducaciones = filtradoslistaTiposEducaciones;
    }

    public List<Profesiones> getListaProfesiones() {
        if (listaProfesiones == null) {
            listaProfesiones = administrarVigenciasFormales.lovProfesiones();
        }
        return listaProfesiones;
    }

    public void setListaProfesiones(List<Profesiones> listaProfesiones) {
        this.listaProfesiones = listaProfesiones;
    }

    public List<Profesiones> getFiltradoslistaProfesiones() {
        return filtradoslistaProfesiones;
    }

    public void setFiltradoslistaProfesiones(List<Profesiones> filtradoslistaProfesiones) {
        this.filtradoslistaProfesiones = filtradoslistaProfesiones;
    }

    public List<Instituciones> getListaInstituciones() {
        if (listaInstituciones == null) {
            listaInstituciones = administrarVigenciasFormales.lovInstituciones();
        }
        return listaInstituciones;
    }

    public void setListaInstituciones(List<Instituciones> listaInstituciones) {
        this.listaInstituciones = listaInstituciones;
    }

    public List<Instituciones> getFiltradoslistaInstituciones() {
        return filtradoslistaInstituciones;
    }

    public void setFiltradoslistaInstituciones(List<Instituciones> filtradoslistaInstituciones) {
        this.filtradoslistaInstituciones = filtradoslistaInstituciones;
    }

    public List<AdiestramientosF> getListaAdiestramientosFormales() {
        if (listaAdiestramientosFormales == null) {
            listaAdiestramientosFormales = administrarVigenciasFormales.lovAdiestramientosF();
        }
        return listaAdiestramientosFormales;
    }

    public void setListaAdiestramientosFormales(List<AdiestramientosF> listaAdiestramientosFormales) {
        this.listaAdiestramientosFormales = listaAdiestramientosFormales;
    }

    public List<AdiestramientosF> getFiltradoslistaAdiestramientosFormales() {
        return filtradoslistaAdiestramientosFormales;
    }

    public void setFiltradoslistaAdiestramientosFormales(List<AdiestramientosF> filtradoslistaAdiestramientosFormales) {
        this.filtradoslistaAdiestramientosFormales = filtradoslistaAdiestramientosFormales;
    }

    public TiposEducaciones getSeleccionTiposEducaciones() {
        return seleccionTiposEducaciones;
    }

    public void setSeleccionTiposEducaciones(TiposEducaciones seleccionTiposEducaciones) {
        this.seleccionTiposEducaciones = seleccionTiposEducaciones;
    }

    public Profesiones getSeleccionProfesiones() {
        return seleccionProfesiones;
    }

    public void setSeleccionProfesiones(Profesiones seleccionProfesiones) {
        this.seleccionProfesiones = seleccionProfesiones;
    }

    public Instituciones getSeleccionInstituciones() {
        return seleccionInstituciones;
    }

    public void setSeleccionInstituciones(Instituciones seleccionInstituciones) {
        this.seleccionInstituciones = seleccionInstituciones;
    }

    public AdiestramientosF getSeleccionAdiestramientosFormales() {
        return seleccionAdiestramientosFormales;
    }

    public void setSeleccionAdiestramientosFormales(AdiestramientosF seleccionAdiestramientosFormales) {
        this.seleccionAdiestramientosFormales = seleccionAdiestramientosFormales;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public VigenciasFormales getEditarVigenciasFormales() {
        return editarVigenciasFormales;
    }

    public void setEditarVigenciasFormales(VigenciasFormales editarVigenciasFormales) {
        this.editarVigenciasFormales = editarVigenciasFormales;
    }

    public VigenciasFormales getNuevaVigenciaFormal() {
        return nuevaVigenciaFormal;
    }

    public void setNuevaVigenciaFormal(VigenciasFormales nuevaVigenciaFormal) {
        this.nuevaVigenciaFormal = nuevaVigenciaFormal;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }

    public VigenciasFormales getDuplicarVigenciaFormal() {
        return duplicarVigenciaFormal;
    }

    public void setDuplicarVigenciaFormal(VigenciasFormales duplicarVigenciaFormal) {
        this.duplicarVigenciaFormal = duplicarVigenciaFormal;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    // Cosas de Vigencias No Formales
    public List<VigenciasNoFormales> getListaVigenciasNoFormales() {
        if (listaVigenciasNoFormales == null) {
            listaVigenciasNoFormales = administrarVigenciasNoFormales.vigenciasNoFormalesPersona(secuenciaPersona);
        }
        return listaVigenciasNoFormales;
    }

    public void setListaVigenciasNoFormales(List<VigenciasNoFormales> listaVigenciasNoFormales) {
        this.listaVigenciasNoFormales = listaVigenciasNoFormales;
    }

    public List<VigenciasNoFormales> getFiltradosListaVigenciasNoFormales() {
        return filtradosListaVigenciasNoFormales;
    }

    public void setFiltradosListaVigenciasNoFormales(List<VigenciasNoFormales> filtradosListaVigenciasNoFormales) {
        this.filtradosListaVigenciasNoFormales = filtradosListaVigenciasNoFormales;
    }

    public List<Cursos> getListaCursos() {
        if (listaCursos.isEmpty()) {
            listaCursos = administrarVigenciasNoFormales.lovCursos();
        }
        return listaCursos;
    }

    public void setListaCursos(List<Cursos> listaCursos) {
        this.listaCursos = listaCursos;
    }

    public List<Cursos> getFiltradoslistaCursos() {
        return filtradoslistaCursos;
    }

    public void setFiltradoslistaCursos(List<Cursos> filtradoslistaCursos) {
        this.filtradoslistaCursos = filtradoslistaCursos;
    }

    public Cursos getSeleccionCursos() {
        return seleccionCursos;
    }

    public void setSeleccionCursos(Cursos seleccionCursos) {
        this.seleccionCursos = seleccionCursos;
    }

    public List<AdiestramientosNF> getListaAdiestramientosNoFormales() {
        if (listaAdiestramientosNoFormales.isEmpty()) {
            listaAdiestramientosNoFormales = administrarVigenciasNoFormales.lovAdiestramientosNF();
        }
        return listaAdiestramientosNoFormales;
    }

    public void setListaAdiestramientosNoFormales(List<AdiestramientosNF> listaAdiestramientosNoFormales) {
        this.listaAdiestramientosNoFormales = listaAdiestramientosNoFormales;
    }

    public List<AdiestramientosNF> getFiltradoslistaAdiestramientosNoFormales() {
        return filtradoslistaAdiestramientosNoFormales;
    }

    public void setFiltradoslistaAdiestramientosNoFormales(List<AdiestramientosNF> filtradoslistaAdiestramientosNoFormales) {
        this.filtradoslistaAdiestramientosNoFormales = filtradoslistaAdiestramientosNoFormales;
    }

    public AdiestramientosNF getSeleccionAdiestramientosNoFormales() {
        return seleccionAdiestramientosNoFormales;
    }

    public void setSeleccionAdiestramientosNoFormales(AdiestramientosNF seleccionAdiestramientosNoFormales) {
        this.seleccionAdiestramientosNoFormales = seleccionAdiestramientosNoFormales;
    }

    public VigenciasNoFormales getEditarVigenciasNoFormales() {
        return editarVigenciasNoFormales;
    }

    public void setEditarVigenciasNoFormales(VigenciasNoFormales editarVigenciasNoFormales) {
        this.editarVigenciasNoFormales = editarVigenciasNoFormales;
    }

    public String getTablaImprimir() {
        return tablaImprimir;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public VigenciasNoFormales getNuevaVigenciaNoFormal() {
        return nuevaVigenciaNoFormal;
    }

    public void setNuevaVigenciaNoFormal(VigenciasNoFormales nuevaVigenciaNoFormal) {
        this.nuevaVigenciaNoFormal = nuevaVigenciaNoFormal;
    }

    public String getAdiestramientoNF() {
        return AdiestramientoNF;
    }

    public void setAdiestramientoNF(String AdiestramientoNF) {
        this.AdiestramientoNF = AdiestramientoNF;
    }

    public String getMensajeValidacionNF() {
        return mensajeValidacionNF;
    }

    public void setMensajeValidacionNF(String mensajeValidacionNF) {
        this.mensajeValidacionNF = mensajeValidacionNF;
    }

    public String getCualInsertar() {
        return cualInsertar;
    }

    public String getCualNuevo() {
        return cualNuevo;
    }

    public VigenciasNoFormales getDuplicarVigenciaNoFormal() {
        return duplicarVigenciaNoFormal;
    }

    public void setDuplicarVigenciaNoFormal(VigenciasNoFormales duplicarVigenciaNoFormal) {
        this.duplicarVigenciaNoFormal = duplicarVigenciaNoFormal;
    }
}

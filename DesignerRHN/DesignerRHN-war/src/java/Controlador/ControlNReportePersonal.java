/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Aficiones;
import Entidades.Ciudades;
import Entidades.Deportes;
import Entidades.Empleados;
import Entidades.Empresas;
import Entidades.EstadosCiviles;
import Entidades.Estructuras;
import Entidades.Idiomas;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.TiposTelefonos;
import Entidades.TiposTrabajadores;
import InterfaceAdministrar.AdministrarNReportePersonalInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.column.Column;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReportePersonal implements Serializable {

    @EJB
    AdministrarNReportePersonalInterface administrarNReportePersonal;
    //
    private ParametrosInformes parametroDeInforme;
    private ParametrosInformes nuevoParametroInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
    //
    private String reporteGenerar;
    private Inforeportes inforreporteSeleccionado;
    private int bandera;
    private boolean aceptar;
    private Column codigoIR, reporteIR, tipoIR;
    private int casilla;
    private ParametrosInformes parametroModificacion;
    //
    private int tipoLista;
    private int posicionReporte;
    private String requisitosReporte;
    //
    private Calendar fechaDesdeParametro, fechaHastaParametro;
    private InputText empleadoDesdeParametro, empleadoHastaParametro, estadoCivilParametro, tipoTelefonoParametro, estructuraParametro, ciudadParametro, deporteParametro, aficionParametro, idiomaParametro, tipoTrabajadorParametro;
    //
    private List<Empleados> listEmpleados;
    private List<Empresas> listEmpresas;
    private List<Estructuras> listEstructuras;
    private List<TiposTrabajadores> listTiposTrabajadores;
    private List<EstadosCiviles> listEstadosCiviles;
    private List<TiposTelefonos> listTiposTelefonos;
    private List<Ciudades> listCiudades;
    private List<Deportes> listDeportes;
    private List<Aficiones> listAficiones;
    private List<Idiomas> listIdiomas;
    //
    private Empleados empleadoSeleccionado;
    private Empresas empresaSeleccionada;
    private Estructuras estructuraSeleccionada;
    private TiposTrabajadores tipoTSeleccionado;
    private EstadosCiviles estadoCivilSeleccionado;
    private TiposTelefonos tipoTelefonoSeleccionado;
    private Ciudades ciudadSeleccionada;
    private Deportes deporteSeleccionado;
    private Aficiones aficionSeleccionada;
    private Idiomas idiomaSeleccionado;
    //
    private List<Empleados> filtrarListEmpleados;
    private List<Empresas> filtrarListEmpresas;
    private List<Estructuras> filtrarListEstructuras;
    private List<TiposTrabajadores> filtrarListTiposTrabajadores;
    private List<EstadosCiviles> filtrarListEstadosCiviles;
    private List<TiposTelefonos> filtrarListTiposTelefonos;
    private List<Ciudades> filtrarListCiudades;
    private List<Deportes> filtrarListDeportes;
    private List<Aficiones> filtrarListAficiones;
    private List<Idiomas> filtrarListIdiomas;
    //
    private String estadocivil, tipotelefono, jefediv, estructura, empresa, tipoTrabajador, ciudad, deporte, aficiones, idioma;
    private boolean permitirIndex;

    public ControlNReportePersonal() {

        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        filtrarListInforeportesUsuario = null;
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        tipoLista = 0;
        reporteGenerar = "";
        requisitosReporte = "";
        posicionReporte = -1;
        //
        listEmpleados = null;
        listEmpresas = null;
        listEstructuras = null;
        listTiposTrabajadores = null;
        listAficiones = null;
        listCiudades = null;
        listDeportes = null;
        listEstadosCiviles = null;;
        listIdiomas = null;;
        listTiposTelefonos = null;;
        //
        empleadoSeleccionado = new Empleados();
        empresaSeleccionada = new Empresas();
        estructuraSeleccionada = new Estructuras();
        tipoTSeleccionado = new TiposTrabajadores();
        aficionSeleccionada = new Aficiones();
        ciudadSeleccionada = new Ciudades();
        deporteSeleccionado = new Deportes();
        estadoCivilSeleccionado = new EstadosCiviles();
        idiomaSeleccionado = new Idiomas();
        tipoTelefonoSeleccionado = new TiposTelefonos();
        //
        permitirIndex = true;
    }

    public void guardarCambios() {
        try {
            if (parametroDeInforme.getEstadocivil().getSecuencia() == null) {
                parametroDeInforme.setEstadocivil(null);
            }
            if (parametroDeInforme.getTipotelefono().getSecuencia() == null) {
                parametroDeInforme.setTipotelefono(null);
            }
            if (parametroDeInforme.getLocalizacion().getSecuencia() == null) {
                parametroDeInforme.setLocalizacion(null);
            }
            if (parametroDeInforme.getTipotrabajador().getSecuencia() == null) {
                parametroDeInforme.setTipotrabajador(null);
            }
            if (parametroDeInforme.getCiudad().getSecuencia() == null) {
                parametroDeInforme.setCiudad(null);
            }
            if (parametroDeInforme.getDeporte().getSecuencia() == null) {
                parametroDeInforme.setDeporte(null);
            }
            if (parametroDeInforme.getAficion().getSecuencia() == null) {
                parametroDeInforme.setAficion(null);
            }
            if (parametroDeInforme.getIdioma().getSecuencia() == null) {
                parametroDeInforme.setIdioma(null);
            }
            if (parametroDeInforme.getEmpresa().getSecuencia() == null) {
                parametroDeInforme.setEmpresa(null);
            }
            administrarNReportePersonal.modificarParametrosInformes(parametroModificacion);
        } catch (Exception e) {
            System.out.println("Error en guardar Cambios Controlador : " + e.toString());
        }
    }

    public void modificarParametroInforme() {
        parametroModificacion = parametroDeInforme;
    }

    public void posicionCelda(int i) {
        casilla = i;
        if (permitirIndex == true) {
            if (casilla == 4) {
                estadocivil = parametroDeInforme.getEstadocivil().getDescripcion();
            }
            if (casilla == 5) {
                tipotelefono = parametroDeInforme.getTipotelefono().getNombre();
            }
            if (casilla == 6) {
                jefediv = parametroDeInforme.getNombregerente().getPersona().getNombreCompleto();
            }
            if (casilla == 10) {
                estructura = parametroDeInforme.getLocalizacion().getNombre();
            }
            if (casilla == 11) {
                tipoTrabajador = parametroDeInforme.getTipotrabajador().getNombre();
            }
            if (casilla == 12) {
                ciudad = parametroDeInforme.getCiudad().getNombre();
            }
            if (casilla == 13) {
                deporte = parametroDeInforme.getDeporte().getNombre();
            }
            if (casilla == 14) {
                aficiones = parametroDeInforme.getAficion().getDescripcion();
            }
            if (casilla == 15) {
                idioma = parametroDeInforme.getIdioma().getNombre();
            }
            if (casilla == 16) {
                empresa = parametroDeInforme.getEmpresa().getNombre();
            }
        }
    }

    public void autocompletarGeneral(String campoConfirmar, String valorConfirmar) {
        RequestContext context = RequestContext.getCurrentInstance();
        int indiceUnicoElemento = -1;
        int coincidencias = 0;
        if (campoConfirmar.equalsIgnoreCase("ESTADOCIVIL")) {
            parametroDeInforme.getEstadocivil().setDescripcion(estadocivil);
            for (int i = 0; i < listEstadosCiviles.size(); i++) {
                if (listEstadosCiviles.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setEstadocivil(listEstadosCiviles.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEstadosCiviles.clear();
                getListEstadosCiviles();
            } else {
                permitirIndex = false;
                context.update("form:EstadoCivilDialogo");
                context.execute("EstadoCivilDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOTELEFONO")) {
            parametroDeInforme.getTipotelefono().setNombre(tipotelefono);

            for (int i = 0; i < listTiposTelefonos.size(); i++) {
                if (listTiposTelefonos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setTipotelefono(listTiposTelefonos.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listTiposTelefonos.clear();
                getListTiposTelefonos();
            } else {
                permitirIndex = false;
                context.update("form:TipoTelefonoDialogo");
                context.execute("TipoTelefonoDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("JEFEDIV")) {
            parametroDeInforme.getNombregerente().getPersona().setNombreCompleto(jefediv);

            for (int i = 0; i < listEmpleados.size(); i++) {
                if (listEmpleados.get(i).getPersona().getNombreCompleto().startsWith(valorConfirmar)) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setNombregerente(listEmpleados.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEmpleados.clear();
                getListEmpleados();
            } else {
                permitirIndex = false;
                context.update("form:JefeDivisionDialogo");
                context.execute("JefeDivisionDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("ESTRUCTURA")) {
            parametroDeInforme.getLocalizacion().setNombre(estructura);
            for (int i = 0; i < listEstructuras.size(); i++) {
                if (listEstructuras.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setLocalizacion(listEstructuras.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEstructuras.clear();
                getListEstructuras();
            } else {
                permitirIndex = false;
                context.update("form:EstructuraDialogo");
                context.execute("EstructuraDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("TIPOTRABAJADOR")) {
            parametroDeInforme.getTipotrabajador().setNombre(tipoTrabajador);
            for (int i = 0; i < listTiposTrabajadores.size(); i++) {
                if (listTiposTrabajadores.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setTipotrabajador(listTiposTrabajadores.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listTiposTrabajadores.clear();
                getListTiposTrabajadores();
            } else {
                permitirIndex = false;
                context.update("form:TipoTrabajadorDialogo");
                context.execute("TipoTrabajadorDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("CIUDAD")) {
            System.out.println("Ciudad : "+parametroDeInforme.getCiudad().getNombre());
            parametroDeInforme.getCiudad().setNombre(ciudad);
            for (int i = 0; i < listCiudades.size(); i++) {
                if (listCiudades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setCiudad(listCiudades.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listCiudades.clear();
                getListCiudades();
            } else {
                permitirIndex = false;
                context.update("form:CiudadDialogo");
                context.execute("CiudadDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("DEPORTE")) {
            parametroDeInforme.getDeporte().setNombre(deporte);
            for (int i = 0; i < listDeportes.size(); i++) {
                if (listDeportes.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setDeporte(listDeportes.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listDeportes.clear();
                getListDeportes();
            } else {
                permitirIndex = false;
                context.update("form:DeportesDialogo");
                context.execute("DeportesDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("AFICION")) {
            parametroDeInforme.getAficion().setDescripcion(aficiones);
            for (int i = 0; i < listAficiones.size(); i++) {
                if (listAficiones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setAficion(listAficiones.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listAficiones.clear();
                getListAficiones();
            } else {
                permitirIndex = false;
                context.update("form:AficionesDialogo");
                context.execute("AficionesDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("IDIOMA")) {
            parametroDeInforme.getIdioma().setNombre(idioma);
            for (int i = 0; i < listIdiomas.size(); i++) {
                if (listIdiomas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setIdioma(listIdiomas.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listIdiomas.clear();
                getListIdiomas();
            } else {
                permitirIndex = false;
                context.update("form:IdiomasDialogo");
                context.execute("IdiomasDialogo.show()");
            }
        }
        if (campoConfirmar.equalsIgnoreCase("EMPRESA")) {
            parametroDeInforme.getEmpresa().setNombre(empresa);
            for (int i = 0; i < listEmpresas.size(); i++) {
                if (listEmpresas.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                parametroDeInforme.setEmpresa(listEmpresas.get(indiceUnicoElemento));
                parametroModificacion = parametroDeInforme;
                listEmpresas.clear();
                getListEmpresas();
            } else {
                permitirIndex = false;
                context.update("form:EmpresaDialogo");
                context.execute("EmpresaDialogo.show()");
            }
        }
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 2) {
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (casilla == 4) {
            context.update("form:EstadoCivilDialogo");
            context.execute("EstadoCivilDialogo.show()");
        }
        if (casilla == 5) {
            context.update("form:TipoTelefonoDialogo");
            context.execute("TipoTelefonoDialogo.show()");
        }
        if (casilla == 6) {
            context.update("form:JefeDivisionDialogo");
            context.execute("JefeDivisionDialogo.show()");
        }
        if (casilla == 9) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (casilla == 10) {
            context.update("form:EstructuraDialogo");
            context.execute("EstructuraDialogo.show()");
        }
        if (casilla == 11) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (casilla == 12) {
            context.update("form:CiudadDialogo");
            context.execute("CiudadDialogo.show()");
        }
        if (casilla == 13) {
            context.update("form:DeportesDialogo");
            context.execute("DeportesDialogo.show()");
        }
        if (casilla == 14) {
            context.update("form:AficionesDialogo");
            context.execute("AficionesDialogo.show()");
        }
        if (casilla == 15) {
            context.update("form:IdiomasDialogo");
            context.execute("IdiomasDialogo.show()");
        }
        if (casilla == 16) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }
    }

    public void listasValores(int pos) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (pos == 2) {
            context.update("form:EmpleadoDesdeDialogo");
            context.execute("EmpleadoDesdeDialogo.show()");
        }
        if (pos == 4) {
            context.update("form:EstadoCivilDialogo");
            context.execute("EstadoCivilDialogo.show()");
        }
        if (pos == 5) {
            context.update("form:TipoTelefonoDialogo");
            context.execute("TipoTelefonoDialogo.show()");
        }
        if (pos == 6) {
            context.update("form:JefeDivisionDialogo");
            context.execute("JefeDivisionDialogo.show()");
        }
        if (pos == 9) {
            context.update("form:EmpleadoHastaDialogo");
            context.execute("EmpleadoHastaDialogo.show()");
        }
        if (pos == 10) {
            context.update("form:EstructuraDialogo");
            context.execute("EstructuraDialogo.show()");
        }
        if (pos == 11) {
            context.update("form:TipoTrabajadorDialogo");
            context.execute("TipoTrabajadorDialogo.show()");
        }
        if (pos == 12) {
            context.update("form:CiudadDialogo");
            context.execute("CiudadDialogo.show()");
        }
        if (pos == 13) {
            context.update("form:DeportesDialogo");
            context.execute("DeportesDialogo.show()");
        }
        if (pos == 14) {
            context.update("form:AficionesDialogo");
            context.execute("AficionesDialogo.show()");
        }
        if (pos == 15) {
            context.update("form:IdiomasDialogo");
            context.execute("IdiomasDialogo.show()");
        }
        if (pos == 16) {
            context.update("form:EmpresaDialogo");
            context.execute("EmpresaDialogo.show()");
        }

    }

    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (casilla == 1) {
            context.update("formularioDialogos:editarFechaDesde");
            context.execute("editarFechaDesde.show()");
        }
        if (casilla == 2) {
            context.update("formularioDialogos:empleadoDesde");
            context.execute("empleadoDesde.show()");
        }
        if (casilla == 3) {
            context.update("formularioDialogos:solicitud");
            context.execute("solicitud.show()");
        }
        if (casilla == 4) {
            context.update("formularioDialogos:estadoCivil");
            context.execute("estadoCivil.show()");
        }
        if (casilla == 5) {
            context.update("formularioDialogos:tipoTelefono");
            context.execute("tipoTelefono.show()");
        }
        if (casilla == 6) {
            context.update("formularioDialogos:jefeDivision");
            context.execute("jefeDivision.show()");
        }
        if (casilla == 7) {
            context.update("formularioDialogos:rodamiento");
            context.execute("rodamiento.show()");
        }
        if (casilla == 8) {
            context.update("formularioDialogos:editarFechaHasta");
            context.execute("editarFechaHasta.show()");
        }
        if (casilla == 9) {
            context.update("formularioDialogos:empleadoHasta");
            context.execute("empleadoHasta.show()");
        }
        if (casilla == 10) {
            context.update("formularioDialogos:estructura");
            context.execute("estructura.show()");
        }
        if (casilla == 11) {
            context.update("formularioDialogos:tipoTrabajador");
            context.execute("tipoTrabajador.show()");
        }
        if (casilla == 12) {
            context.update("formularioDialogos:ciudad");
            context.execute("ciudad.show()");
        }
        if (casilla == 13) {
            context.update("formularioDialogos:deporte");
            context.execute("deporte.show()");
        }
        if (casilla == 14) {
            context.update("formularioDialogos:aficion");
            context.execute("aficion.show()");
        }
        if (casilla == 15) {
            context.update("formularioDialogos:idioma");
            context.execute("idioma.show()");
        }
        if (casilla == 16) {
            context.update("formularioDialogos:empresa");
            context.execute("empresa.show()");
        }
        casilla = -1;

    }

    public void refrescarParametros() {
        defaultPropiedadesParametrosReporte();
        parametroDeInforme = null;

        listEstructuras = null;
        listCiudades = null;
        listEmpleados = null;
        listEmpresas = null;
        listTiposTrabajadores = null;
        listAficiones = null;
        listDeportes = null;
        listEstadosCiviles = null;
        listIdiomas = null;
        listTiposTelefonos = null;
        parametroDeInforme = null;
        
        parametroDeInforme = administrarNReportePersonal.parametrosDeReporte();

        if (parametroDeInforme.getEstadocivil() == null) {
            parametroDeInforme.setEstadocivil(new EstadosCiviles());
        }
        if (parametroDeInforme.getTipotelefono() == null) {
            parametroDeInforme.setTipotelefono(new TiposTelefonos());
        }
        if (parametroDeInforme.getLocalizacion() == null) {
            parametroDeInforme.setLocalizacion(new Estructuras());
        }
        if (parametroDeInforme.getTipotrabajador() == null) {
            parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
        }
        if (parametroDeInforme.getCiudad() == null) {
            parametroDeInforme.setCiudad(new Ciudades());
        }
        if (parametroDeInforme.getDeporte() == null) {
            parametroDeInforme.setDeporte(new Deportes());
        }
        if (parametroDeInforme.getAficion() == null) {
            parametroDeInforme.setAficion(new Aficiones());
        }
        if (parametroDeInforme.getIdioma() == null) {
            parametroDeInforme.setIdioma(new Idiomas());
        }
        if (parametroDeInforme.getEmpresa() == null) {
            parametroDeInforme.setEmpresa(new Empresas());
        }

        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:fechaDesdeParametro");
        context.update("form:empleadoDesdeParametro");
        context.update("form:solicitudParametro");
        context.update("form:estadoCivilParametro");
        context.update("form:tipoTelefonoParametro");
        context.update("form:jefeDivisionParametro");
        context.update("form:rodamientoParametro");
        context.update("form:fondoCumpleParametro");
        context.update("form:fechaHastaParametro");
        context.update("form:empleadoHastaParametro");
        context.update("form:estructuraParametro");
        context.update("form:tipoTrabajadorParametro");

        context.update("form:ciudadParametro");
        context.update("form:deporteParametro");
        context.update("form:aficionParametro");
        context.update("form:idiomaParametro");

        context.update("form:personalParametro");
        context.update("form:empresaParametro");


    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void actualizarEmplDesde() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadodesde(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoDesdeParametro");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;

    }

    public void cancelarCambioEmplDesde() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void actualizarEmplHasta() {
        permitirIndex = true;
        parametroDeInforme.setCodigoempleadohasta(empleadoSeleccionado.getCodigoempleado());
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empleadoHastaParametro");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
    }

    public void cancelarCambioEmplHasta() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void actualizarEmpresa() {
        permitirIndex = true;
        parametroDeInforme.setEmpresa(empresaSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:empresaParametro");
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;

    }

    public void cancelarEmpresa() {
        empresaSeleccionada = null;
        aceptar = true;
        filtrarListEmpresas = null;
        permitirIndex = true;
    }

    public void actualizarEstructura() {
        parametroDeInforme.setLocalizacion(estructuraSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:estructuraParametro");
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;
    }

    public void cancelarEstructura() {
        estructuraSeleccionada = null;
        aceptar = true;
        filtrarListEstructuras = null;
        permitirIndex = true;
    }

    public void actualizarTipoTrabajador() {
        parametroDeInforme.setTipotrabajador(tipoTSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tipoTrabajadorParametro");
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;
    }

    public void cancelarTipoTrabajador() {
        tipoTSeleccionado = null;
        aceptar = true;
        filtrarListTiposTrabajadores = null;
        permitirIndex = true;
    }

    public void actualizarEstadoCivil() {
        permitirIndex = true;
        parametroDeInforme.setEstadocivil(estadoCivilSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:estadoCivilParametro");
        estadoCivilSeleccionado = null;
        aceptar = true;
        filtrarListEstadosCiviles = null;
    }

    public void cancelarEstadoCivil() {
        estadoCivilSeleccionado = null;
        aceptar = true;
        filtrarListEstadosCiviles = null;
        permitirIndex = true;
    }

    public void actualizarTipoTelefono() {
        permitirIndex = true;
        parametroDeInforme.setTipotelefono(tipoTelefonoSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:tipoTelefonoParametro");
        tipoTelefonoSeleccionado = null;
        aceptar = true;
        filtrarListTiposTelefonos = null;
    }

    public void cancelarTipoTelefono() {
        tipoTelefonoSeleccionado = null;
        aceptar = true;
        filtrarListTiposTelefonos = null;
        permitirIndex = true;
    }

    public void actualizarJefeDivision() {
        permitirIndex = true;
        parametroDeInforme.setNombregerente(empleadoSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:jefeDivisionParametro");
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
    }

    public void cancelarJefeDivision() {
        empleadoSeleccionado = null;
        aceptar = true;
        filtrarListEmpleados = null;
        permitirIndex = true;
    }

    public void actualizarCiudad() {
        permitirIndex = true;
        parametroDeInforme.setCiudad(ciudadSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ciudadParametro");
        ciudadSeleccionada = null;
        aceptar = true;
        filtrarListCiudades = null;
    }

    public void cancelarCiudad() {
        ciudadSeleccionada = null;
        aceptar = true;
        filtrarListCiudades = null;
        permitirIndex = true;
    }

    public void actualizarDeporte() {
        permitirIndex = true;
        parametroDeInforme.setDeporte(deporteSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:deporteParametro");
        deporteSeleccionado = null;
        aceptar = true;
        filtrarListDeportes = null;
    }

    public void cancelarDeporte() {
        deporteSeleccionado = null;
        aceptar = true;
        filtrarListDeportes = null;
        permitirIndex = true;
    }

    public void actualizarAficion() {
        permitirIndex = true;
        parametroDeInforme.setAficion(aficionSeleccionada);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:aficionParametro");
        aficionSeleccionada = null;
        aceptar = true;
        filtrarListAficiones = null;
    }

    public void cancelarAficion() {
        aficionSeleccionada = null;
        aceptar = true;
        filtrarListAficiones = null;
        permitirIndex = true;
    }

    public void actualizarIdioma() {
        permitirIndex = true;
        parametroDeInforme.setIdioma(idiomaSeleccionado);
        parametroModificacion = parametroDeInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:idiomaParametro");
        idiomaSeleccionado = null;
        aceptar = true;
        filtrarListIdiomas = null;
    }

    public void cancelarIdioma() {
        idiomaSeleccionado = null;
        aceptar = true;
        filtrarListIdiomas = null;
        permitirIndex = true;
    }

    public void generarReporte(int i) {
        defaultPropiedadesParametrosReporte();
        if (tipoLista == 0) {
            reporteGenerar = listaIR.get(i).getNombre();
            posicionReporte = i;
        }
        if (tipoLista == 1) {
            if (listaIR.contains(filtrarListInforeportesUsuario.get(i))) {
                int posicion = listaIR.indexOf(filtrarListInforeportesUsuario.get(i));
                reporteGenerar = listaIR.get(posicion).getNombre();
                posicionReporte = posicion;
            }
        }
        mostrarDialogoGenerarReporte();
    }

    public void mostrarDialogoGenerarReporte() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formDialogos:reporteAGenerar");
        context.execute("reporteAGenerar.show()");
    }

    public void cancelarGenerarReporte() {
        reporteGenerar = "";
        posicionReporte = -1;
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            listaIR = administrarNReportePersonal.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ReportesDialogo");
            context.execute("ReportesDialogo.show()");
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }

    public void salir() {
        if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesPersonal");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        listEmpleados = null;
        listEmpresas = null;
        listEstructuras = null;
        listTiposTrabajadores = null;
        listAficiones = null;
        listCiudades = null;
        listDeportes = null;
        listTiposTelefonos = null;
        listIdiomas = null;
        listEstadosCiviles = null;
        casilla = -1;
    }

    public void actualizarSeleccionInforeporte() {
        if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesPersonal");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        defaultPropiedadesParametrosReporte();
        listaIR = new ArrayList<Inforeportes>();
        listaIR.add(inforreporteSeleccionado);
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesPersonal");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
    }

    public void mostrarTodos() {
        defaultPropiedadesParametrosReporte();
        listaIR = null;
        getListaIR();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesPersonal");
    }

    public void activarCtrlF11() {
        if (bandera == 0) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:tipoIR");
            tipoIR.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:reportesPersonal");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesPersonal:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesPersonal");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
            defaultPropiedadesParametrosReporte();
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarNReportePersonal.listInforeportesUsuario();
            listaIR = listaIRRespaldo;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:reportesPersonal");
        } catch (Exception e) {
            System.out.println("Error en reporteModificado : " + e.toString());
        }
    }

    public void reporteSeleccionado(int i) {
        System.out.println("Posicion del reporte : " + i);
    }

    public void defaultPropiedadesParametrosReporte() {

        fechaDesdeParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametro");
        fechaDesdeParametro.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaDesdeParametro");

        fechaHastaParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
        fechaHastaParametro.setStyleClass("ui-datepicker, calendarioReportes");
        RequestContext.getCurrentInstance().update("form:fechaHastaParametro");

        empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametro");
        empleadoDesdeParametro.setStyle("position: absolute; top: 33px; left: 90px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");

        empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
        empleadoHastaParametro.setStyle("position: absolute; top: 33px; left: 270px;font-size: 11px;height: 10px;width: 90px;");
        RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");

        estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estructuraParametro");
        estructuraParametro.setStyle("position: absolute; top: 10px; left: 510px;font-size: 11px;height: 10px;width: 280px;");
        RequestContext.getCurrentInstance().update("form:estructuraParametro");

        tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
        tipoTrabajadorParametro.setStyle("position: absolute; top: 31px; left: 510px;font-size: 11px;height: 10px;width: 280px;");
        RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");

        estadoCivilParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estadoCivilParametro");
        estadoCivilParametro.setStyle("position: absolute; top: 75px; left: 90px;font-size: 11px;height: 10px;width: 120px;");
        RequestContext.getCurrentInstance().update("form:estadoCivilParametro");

        tipoTelefonoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTelefonoParametro");
        tipoTelefonoParametro.setStyle("position: absolute; top: 96px; left: 90px;font-size: 11px;height: 10px;width: 120px;");
        RequestContext.getCurrentInstance().update("form:tipoTelefonoParametro");

        ciudadParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:ciudadParametro");
        ciudadParametro.setStyle("position: absolute; top: 52px; left: 510px;font-size: 11px;height: 10px;width: 280px;");
        RequestContext.getCurrentInstance().update("form:ciudadParametro");

        deporteParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:deporteParametro");
        deporteParametro.setStyle("position: absolute; top: 73px; left: 510px;font-size: 11px;height: 10px;width: 280px;");
        RequestContext.getCurrentInstance().update("form:deporteParametro");

        aficionParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficionParametro");
        aficionParametro.setStyle("position: absolute; top: 94px; left: 510px;font-size: 11px;height: 10px;width: 280px");
        RequestContext.getCurrentInstance().update("form:aficionParametro");

        idiomaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:idiomaParametro");
        idiomaParametro.setStyle("position: absolute; top: 115px; left: 510px;font-size: 11px;height: 10px;width: 280px");
        RequestContext.getCurrentInstance().update("form:idiomaParametro");


    }

    public void resaltoParametrosParaReporte(int i) {
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        defaultPropiedadesParametrosReporte();
        if (reporteS.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
            fechaDesdeParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaDesdeParametro");
            fechaDesdeParametro.setStyleClass("ui-datepicker, myClass3");

        }
        if (reporteS.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
            fechaHastaParametro = (Calendar) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:fechaHastaParametro");
            fechaHastaParametro.setStyleClass("ui-datepicker, myClass3");

        }
        if (reporteS.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
            empleadoDesdeParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoDesdeParametro");
            empleadoDesdeParametro.setStyle("position: absolute; top: 33px; left: 90px;font-size: 11px;height: 10px;width: 90px;text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoDesdeParametro");
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
            empleadoHastaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:empleadoHastaParametro");
            empleadoHastaParametro.setStyle("position: absolute; top: 33px; left: 270px;font-size: 11px;height: 10px;width: 90px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:empleadoHastaParametro");
        }

        if (reporteS.getLocalizacion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estructura -";
            estructuraParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estructuraParametro");
            estructuraParametro.setStyle("position: absolute; top: 10px; left: 510px;font-size: 11px;height: 10px;width: 280px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:estructuraParametro");
        }
        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
            tipoTrabajadorParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTrabajadorParametro");
            tipoTrabajadorParametro.setStyle("position: absolute; top: 31px; left: 510px;font-size: 11px;height: 10px;width: 280px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:tipoTrabajadorParametro");
        }

        if (reporteS.getEstadocivil().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estado Civil -";
            estadoCivilParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:estadoCivilParametro");
            estadoCivilParametro.setStyle("position: absolute; top: 75px; left: 90px;font-size: 11px;height: 10px;width: 120px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:estadoCivilParametro");
        }

        if (reporteS.getTipotelefono().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Telefono -";
            tipoTelefonoParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:tipoTelefonoParametro");
            tipoTelefonoParametro.setStyle("position: absolute; top: 96px; left: 90px;font-size: 11px;height: 10px;width: 120px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:tipoTelefonoParametro");
        }

        if (reporteS.getCiudad().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Ciudad -";
            ciudadParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:ciudadParametro");
            ciudadParametro.setStyle("position: absolute; top: 52px; left: 510px;font-size: 11px;height: 10px;width: 280px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:ciudadParametro");
        }

        if (reporteS.getDeporte().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Deporte -";
            deporteParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:deporteParametro");
            deporteParametro.setStyle("position: absolute; top: 73px; left: 510px;font-size: 11px;height: 10px;width: 280px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:deporteParametro");
        }

        if (reporteS.getAficion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Aficion -";
            aficionParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:aficionParametro");
            aficionParametro.setStyle("position: absolute; top: 94px; left: 510px;font-size: 11px;height: 10px;width: 280px; text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:aficionParametro");
        }

        if (reporteS.getIdioma().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Idioma -";
            idiomaParametro = (InputText) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:idiomaParametro");
            idiomaParametro.setStyle("position: absolute; top: 115px; left: 510px;font-size: 11px;height: 10px;width: 280px text-decoration: underline; color: red;");
            RequestContext.getCurrentInstance().update("form:idiomaParametro");
        }


    }

    public void parametrosDeReporte(int i) {
        requisitosReporte = "";
        Inforeportes reporteS = new Inforeportes();
        reporteS = listaIR.get(i);
        RequestContext context = RequestContext.getCurrentInstance();

        if (reporteS.getFecdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Desde -";
        }
        if (reporteS.getFechasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Fecha Hasta -";
        }
        if (reporteS.getEmdesde().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Desde -";
        }
        if (reporteS.getEmhasta().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Empleado Hasta -";
        }
        if (reporteS.getLocalizacion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estructura -";
        }
        if (reporteS.getTipotrabajador().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Trabajador -";
        }
        if (reporteS.getEstadocivil().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Estado Civil -";
        }
        if (reporteS.getTipotelefono().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Tipo Telefono -";
        }
        if (reporteS.getCiudad().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Ciudad -";
        }
        if (reporteS.getDeporte().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Deporte -";
        }
        if (reporteS.getAficion().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Aficion -";
        }
        if (reporteS.getIdioma().equals("SI")) {
            requisitosReporte = requisitosReporte + "- Idioma -";
        }
        if (!requisitosReporte.isEmpty()) {
            context.update("formDialogos:requisitosReporte");
            context.execute("requisitosReporte.show()");
        }
    }

    public void cancelarRequisitosReporte() {
        requisitosReporte = "";
    }

    public ParametrosInformes getParametroDeInforme() {
        try {
            if (parametroDeInforme == null) {
                parametroDeInforme = new ParametrosInformes();
                parametroDeInforme = administrarNReportePersonal.parametrosDeReporte();
            }

            if (parametroDeInforme.getEstadocivil() == null) {
                parametroDeInforme.setEstadocivil(new EstadosCiviles());
            }
            if (parametroDeInforme.getTipotelefono() == null) {
                parametroDeInforme.setTipotelefono(new TiposTelefonos());
            }
            if (parametroDeInforme.getLocalizacion() == null) {
                parametroDeInforme.setLocalizacion(new Estructuras());
            }
            if (parametroDeInforme.getTipotrabajador() == null) {
                parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
            }
            if (parametroDeInforme.getCiudad() == null) {
                parametroDeInforme.setCiudad(new Ciudades());
            }
            if (parametroDeInforme.getDeporte() == null) {
                parametroDeInforme.setDeporte(new Deportes());
            }
            if (parametroDeInforme.getAficion() == null) {
                parametroDeInforme.setAficion(new Aficiones());
            }
            if (parametroDeInforme.getIdioma() == null) {
                parametroDeInforme.setIdioma(new Idiomas());
            }
            if (parametroDeInforme.getEmpresa() == null) {
                parametroDeInforme.setEmpresa(new Empresas());
            }

            return parametroDeInforme;
        } catch (Exception e) {
            System.out.println("Error getParametroDeInforme : " + e.toString());
            return null;
        }
    }

    public void setParametroDeInforme(ParametrosInformes parametroDeInforme) {
        this.parametroDeInforme = parametroDeInforme;
    }

    public List<Inforeportes> getListaIR() {
        try {
            if (listaIR == null) {
                listaIR = new ArrayList<Inforeportes>();
                listaIR = administrarNReportePersonal.listInforeportesUsuario();
            }
            listaIRRespaldo = listaIR;
            return listaIR;
        } catch (Exception e) {
            System.out.println("Error getListInforeportesUsuario : " + e);
            return null;
        }
    }

    public void setListaIR(List<Inforeportes> listaIR) {
        this.listaIR = listaIR;
    }

    public List<Inforeportes> getFiltrarListInforeportesUsuario() {
        return filtrarListInforeportesUsuario;
    }

    public void setFiltrarListInforeportesUsuario(List<Inforeportes> filtrarListInforeportesUsuario) {
        this.filtrarListInforeportesUsuario = filtrarListInforeportesUsuario;
    }

    public Inforeportes getInforreporteSeleccionado() {
        return inforreporteSeleccionado;
    }

    public void setInforreporteSeleccionado(Inforeportes inforreporteSeleccionado) {
        this.inforreporteSeleccionado = inforreporteSeleccionado;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public ParametrosInformes getNuevoParametroInforme() {
        return nuevoParametroInforme;
    }

    public void setNuevoParametroInforme(ParametrosInformes nuevoParametroInforme) {
        this.nuevoParametroInforme = nuevoParametroInforme;
    }

    public ParametrosInformes getParametroModificacion() {
        return parametroModificacion;
    }

    public void setParametroModificacion(ParametrosInformes parametroModificacion) {
        this.parametroModificacion = parametroModificacion;
    }

    public List<Inforeportes> getListaIRRespaldo() {
        return listaIRRespaldo;
    }

    public void setListaIRRespaldo(List<Inforeportes> listaIRRespaldo) {
        this.listaIRRespaldo = listaIRRespaldo;
    }

    public String getReporteGenerar() {

        if (posicionReporte != -1) {
            reporteGenerar = listaIR.get(posicionReporte).getNombre();
        }
        return reporteGenerar;
    }

    public void setReporteGenerar(String reporteGenerar) {
        this.reporteGenerar = reporteGenerar;
    }

    public String getRequisitosReporte() {
        return requisitosReporte;
    }

    public void setRequisitosReporte(String requisitosReporte) {
        this.requisitosReporte = requisitosReporte;
    }

    public List<Empleados> getListEmpleados() {
        try {
            if (listEmpleados == null) {
                listEmpleados = administrarNReportePersonal.listEmpleados();
            }
        } catch (Exception e) {
            listEmpleados = null;
            System.out.println("Error en getListEmpleados : " + e.toString());
        }
        return listEmpleados;
    }

    public void setListEmpleados(List<Empleados> listEmpleados) {
        this.listEmpleados = listEmpleados;
    }

    public List<Empresas> getListEmpresas() {
        try {
            if (listEmpresas == null) {
                listEmpresas = administrarNReportePersonal.listEmpresas();
            }
        } catch (Exception e) {
            listEmpresas = null;
            System.out.println("Error en getListEmpresas : " + e.toString());
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Estructuras> getListEstructuras() {
        try {
            if (listEstructuras == null) {
                listEstructuras = administrarNReportePersonal.listEstructuras();
            }
        } catch (Exception e) {
            listEstructuras = null;
            System.out.println("Error en getListEstructuras : " + e.toString());
        }
        return listEstructuras;
    }

    public void setListEstructuras(List<Estructuras> listEstructuras) {
        this.listEstructuras = listEstructuras;
    }

    public List<TiposTrabajadores> getListTiposTrabajadores() {
        try {
            if (listTiposTrabajadores == null) {
                listTiposTrabajadores = administrarNReportePersonal.listTiposTrabajadores();
            }
        } catch (Exception e) {
            listTiposTrabajadores = null;
            System.out.println("Error en getListTiposTrabajadores : " + e.toString());
        }
        return listTiposTrabajadores;
    }

    public void setListTiposTrabajadores(List<TiposTrabajadores> listTiposTrabajadores) {
        this.listTiposTrabajadores = listTiposTrabajadores;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public Empresas getEmpresaSeleccionada() {
        return empresaSeleccionada;
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    public Estructuras getEstructuraSeleccionada() {
        return estructuraSeleccionada;
    }

    public void setEstructuraSeleccionada(Estructuras estructuraSeleccionada) {
        this.estructuraSeleccionada = estructuraSeleccionada;
    }

    public TiposTrabajadores getTipoTSeleccionado() {
        return tipoTSeleccionado;
    }

    public void setTipoTSeleccionado(TiposTrabajadores tipoTSeleccionado) {
        this.tipoTSeleccionado = tipoTSeleccionado;
    }

    public List<Empleados> getFiltrarListEmpleados() {
        return filtrarListEmpleados;
    }

    public void setFiltrarListEmpleados(List<Empleados> filtrarListEmpleados) {
        this.filtrarListEmpleados = filtrarListEmpleados;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public List<Estructuras> getFiltrarListEstructuras() {
        return filtrarListEstructuras;
    }

    public void setFiltrarListEstructuras(List<Estructuras> filtrarListEstructuras) {
        this.filtrarListEstructuras = filtrarListEstructuras;
    }

    public List<TiposTrabajadores> getFiltrarListTiposTrabajadores() {
        return filtrarListTiposTrabajadores;
    }

    public void setFiltrarListTiposTrabajadores(List<TiposTrabajadores> filtrarListTiposTrabajadores) {
        this.filtrarListTiposTrabajadores = filtrarListTiposTrabajadores;
    }

    public List<EstadosCiviles> getListEstadosCiviles() {
        try {
            if (listEstadosCiviles == null) {
                listEstadosCiviles = new ArrayList<EstadosCiviles>();
                listEstadosCiviles = administrarNReportePersonal.listEstadosCiviles();
            }
            return listEstadosCiviles;
        } catch (Exception e) {
            System.out.println("Error getListEstadosCiviles : " + e.toString());
            return null;
        }
    }

    public void setListEstadosCiviles(List<EstadosCiviles> listEstadosCiviles) {
        this.listEstadosCiviles = listEstadosCiviles;
    }

    public List<TiposTelefonos> getListTiposTelefonos() {
        try {
            if (listTiposTelefonos == null) {
                listTiposTelefonos = new ArrayList<TiposTelefonos>();
                listTiposTelefonos = administrarNReportePersonal.listTiposTelefonos();
            }
            return listTiposTelefonos;
        } catch (Exception e) {
            System.out.println("Error getlistTiposTelefonos : " + e.toString());
            return null;
        }
    }

    public void setListTiposTelefonos(List<TiposTelefonos> listTiposTelefonos) {
        this.listTiposTelefonos = listTiposTelefonos;
    }

    public List<Ciudades> getListCiudades() {
        try {
            if (listCiudades == null) {
                listCiudades = new ArrayList<Ciudades>();
                listCiudades = administrarNReportePersonal.listCiudades();
            }
            return listCiudades;
        } catch (Exception e) {
            System.err.println("Error getListCiudades : " + e.toString());
            return null;
        }
    }

    public void setListCiudades(List<Ciudades> listCiudades) {
        this.listCiudades = listCiudades;
    }

    public List<Deportes> getListDeportes() {
        try {
            if (listDeportes == null) {
                listDeportes = new ArrayList<Deportes>();
                listDeportes = administrarNReportePersonal.listDeportes();
            }
            return listDeportes;
        } catch (Exception e) {
            System.out.println("Error en getListDeportes : " + e.toString());
            return null;
        }
    }

    public void setListDeportes(List<Deportes> listDeportes) {
        this.listDeportes = listDeportes;
    }

    public List<Aficiones> getListAficiones() {
        try {
            if (listAficiones == null) {
                listAficiones = new ArrayList<Aficiones>();
                listAficiones = administrarNReportePersonal.listAficiones();
            }
            return listAficiones;
        } catch (Exception e) {
            System.out.println("Error getListAficiones : " + e.toString());
            return null;
        }
    }

    public void setListAficiones(List<Aficiones> listAficiones) {
        this.listAficiones = listAficiones;
    }

    public List<Idiomas> getListIdiomas() {
        try {
            if (listIdiomas == null) {
                listIdiomas = new ArrayList<Idiomas>();
                listIdiomas = administrarNReportePersonal.listIdiomas();
            }
            return listIdiomas;
        } catch (Exception e) {
            System.out.println("Error getListIdiomas : " + e.toString());
            return null;
        }
    }

    public void setListIdiomas(List<Idiomas> listIdiomas) {
        this.listIdiomas = listIdiomas;
    }

    public EstadosCiviles getEstadoCivilSeleccionado() {
        return estadoCivilSeleccionado;
    }

    public void setEstadoCivilSeleccionado(EstadosCiviles estadoCivilSeleccionado) {
        this.estadoCivilSeleccionado = estadoCivilSeleccionado;
    }

    public TiposTelefonos getTipoTelefonoSeleccionado() {
        return tipoTelefonoSeleccionado;
    }

    public void setTipoTelefonoSeleccionado(TiposTelefonos tipoTelefonoSeleccionado) {
        this.tipoTelefonoSeleccionado = tipoTelefonoSeleccionado;
    }

    public Ciudades getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(Ciudades ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public Deportes getDeporteSeleccionado() {
        return deporteSeleccionado;
    }

    public void setDeporteSeleccionado(Deportes deporteSeleccionado) {
        this.deporteSeleccionado = deporteSeleccionado;
    }

    public Aficiones getAficionSeleccionada() {
        return aficionSeleccionada;
    }

    public void setAficionSeleccionada(Aficiones aficionSeleccionada) {
        this.aficionSeleccionada = aficionSeleccionada;
    }

    public List<EstadosCiviles> getFiltrarListEstadosCiviles() {
        return filtrarListEstadosCiviles;
    }

    public void setFiltrarListEstadosCiviles(List<EstadosCiviles> filtrarListEstadosCiviles) {
        this.filtrarListEstadosCiviles = filtrarListEstadosCiviles;
    }

    public List<TiposTelefonos> getFiltrarListTiposTelefonos() {
        return filtrarListTiposTelefonos;
    }

    public void setFiltrarListTiposTelefonos(List<TiposTelefonos> filtrarListTiposTelefonos) {
        this.filtrarListTiposTelefonos = filtrarListTiposTelefonos;
    }

    public List<Ciudades> getFiltrarListCiudades() {
        return filtrarListCiudades;
    }

    public void setFiltrarListCiudades(List<Ciudades> filtrarListCiudades) {
        this.filtrarListCiudades = filtrarListCiudades;
    }

    public List<Deportes> getFiltrarListDeportes() {
        return filtrarListDeportes;
    }

    public void setFiltrarListDeportes(List<Deportes> filtrarListDeportes) {
        this.filtrarListDeportes = filtrarListDeportes;
    }

    public List<Aficiones> getFiltrarListAficiones() {
        return filtrarListAficiones;
    }

    public void setFiltrarListAficiones(List<Aficiones> filtrarListAficiones) {
        this.filtrarListAficiones = filtrarListAficiones;
    }

    public List<Idiomas> getFiltrarListIdiomas() {
        return filtrarListIdiomas;
    }

    public void setFiltrarListIdiomas(List<Idiomas> filtrarListIdiomas) {
        this.filtrarListIdiomas = filtrarListIdiomas;
    }

    public Idiomas getIdiomaSeleccionado() {
        return idiomaSeleccionado;
    }

    public void setIdiomaSeleccionado(Idiomas idiomaSeleccionado) {
        this.idiomaSeleccionado = idiomaSeleccionado;
    }
}
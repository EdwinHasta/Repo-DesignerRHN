/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Asociaciones;
import Entidades.Estructuras;
import Entidades.GruposConceptos;
import Entidades.Inforeportes;
import Entidades.ParametrosInformes;
import Entidades.Procesos;
import Entidades.Terceros;
import Entidades.TiposTrabajadores;
import Entidades.Tiposasociaciones;
import Entidades.UbicacionesGeograficas;
import InterfaceAdministrar.AdministrarReportesInterface;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.component.column.Column;
import org.primefaces.context.RequestContext;

/**
 *
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlNReporteNomina implements Serializable {

    @EJB
    AdministrarReportesInterface administrarReportes;
    private ParametrosInformes parametroDeInforme;
    private ParametrosInformes nuevoParametroInforme;
    private List<Inforeportes> listaIR;
    private List<Inforeportes> filtrarListInforeportesUsuario;
    private List<Inforeportes> listaIRRespaldo;
    private Inforeportes reporteGenerar;
    private Inforeportes inforreporteSeleccionado;
    private int bandera;
    private boolean aceptar;
    private Column codigoIR, reporteIR, tipoIR;
    private int casilla;
    private ParametrosInformes parametroModificacion;
    private int tipoLista;

    public ControlNReporteNomina() {
        parametroDeInforme = null;
        listaIR = null;
        listaIRRespaldo = new ArrayList<Inforeportes>();
        filtrarListInforeportesUsuario = null;
        bandera = 0;
        aceptar = true;
        casilla = -1;
        parametroModificacion = new ParametrosInformes();
        tipoLista = 0;
        reporteGenerar = new Inforeportes();
        
    }

    public void nuevoParametroInforme() {
        System.out.println("Entro a nuevoParametroInforme");
        nuevoParametroInforme = new ParametrosInformes();
        parametroDeInforme = nuevoParametroInforme;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:fechaDesdeParametro");
        context.update("form:empleadoDesdeParametro");
        context.update("form:estadoParametro");
        context.update("form:grupoParametro");
        context.update("form:ubicacionGeograficaParametro");
        context.update("form:tipoAsociacionParametro");
        context.update("form:fechaHastaParametro");
        context.update("form:empleadoHastaParametro");
        context.update("form:tipoPersonalParametro");
        context.update("form:empresaParametro");
        context.update("form:estructuraParametro");
        context.update("form:tipoTrabajadorParametro");
        context.update("form:terceroParametro");
        context.update("form:procesoParametro");
        context.update("form:notasParametro");
        context.update("form:asociacionParametro");
    }

    
    public void guardarCambios() {
        if (parametroModificacion != null) {
            System.out.println("Estado : " + parametroModificacion.getEstadosolucionnodo());
            System.out.println("Personal : " + parametroModificacion.getTipopersonal());
            if (parametroModificacion.getGrupo() == null) {
                parametroModificacion.setGrupo(null);
            }
            if (parametroModificacion.getUbicaciongeografica() == null) {
                parametroModificacion.setUbicaciongeografica(null);
            }
            if (parametroModificacion.getTipoasociacion() == null) {
                parametroModificacion.setTipoasociacion(null);
            }
            if (parametroModificacion.getLocalizacion() == null) {
                parametroModificacion.setLocalizacion(null);
            }
            if (parametroModificacion.getTipotrabajador() == null) {
                parametroModificacion.setTipotrabajador(null);
            }
            if (parametroModificacion.getTercero() == null) {
                parametroModificacion.setTercero(null);
            }
            if (parametroModificacion.getProceso() == null) {
                parametroModificacion.setProceso(null);
            }
            if (parametroModificacion.getAsociacion() == null) {
                parametroModificacion.setAsociacion(null);
            }
            administrarReportes.modificarParametrosInformes(parametroModificacion);
        }
    }

    public void modificarParametroInforme() {
        System.out.println("Se modifico el parametro de informe");
        parametroModificacion = parametroDeInforme;
    }

    public void posicionCelda(int i) {
        casilla = i;
        System.out.println("Casilla : " + casilla);
    }

    public void editarCelda() {
        System.out.println("Casilla a editar : " + casilla);
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
            context.update("formularioDialogos:grupoDesde");
            context.execute("grupoDesde.show()");
        }
        if (casilla == 4) {
            context.update("formularioDialogos:ubicacionGeografica");
            context.execute("ubicacionGeografica.show()");
        }
        if (casilla == 5) {
            context.update("formularioDialogos:tipoAsociacion");
            context.execute("tipoAsociacion.show()");
        }
        if (casilla == 6) {
            context.update("formularioDialogos:editarFechaHasta");
            context.execute("editarFechaHasta.show()");
        }
        if (casilla == 7) {
            context.update("formularioDialogos:empleadoHasta");
            context.execute("empleadoHasta.show()");
        }
        if (casilla == 8) {
            context.update("formularioDialogos:empresa");
            context.execute("empresa.show()");
        }
        if (casilla == 9) {
            context.update("formularioDialogos:estructura");
            context.execute("estructura.show()");
        }
        if (casilla == 10) {
            context.update("formularioDialogos:tipoTrabajador");
            context.execute("tipoTrabajador.show()");
        }
        if (casilla == 11) {
            context.update("formularioDialogos:tercero");
            context.execute("tercero.show()");
        }
        if (casilla == 12) {
            context.update("formularioDialogos:proceso");
            context.execute("proceso.show()");
        }
        if (casilla == 13) {
            context.update("formularioDialogos:notas");
            context.execute("notas.show()");
        }
        if (casilla == 14) {
            context.update("formularioDialogos:asociacion");
            context.execute("asociacion.show()");
        }
        casilla = -1;

    }

    public void activarAceptar() {
        aceptar = false;
    }

    public void generarReporte(int i) {

        if (tipoLista == 0) {
            setReporteGenerar(listaIR.get(i));
            System.out.println("Genero reporte posicion : " + i);
            System.out.println("Nombre reporte : " + reporteGenerar.getNombre());
            mostrarDialogoReporteGenerar();
        }
        if (tipoLista == 1) {
            if (listaIR.contains(filtrarListInforeportesUsuario.get(i))) {
                int posicion = listaIR.indexOf(filtrarListInforeportesUsuario.get(i));
                setReporteGenerar(listaIR.get(i));
                System.out.println("Genero reporte posicion : " + posicion);
                System.out.println("Nombre reporte : " + reporteGenerar.getNombre());
                mostrarDialogoReporteGenerar();
            }
        }
    }

    public void mostrarDialogoReporteGenerar() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:reporteSeleccionado");
        context.execute("reporteSeleccionado.show()");
    }

    public void cancelarGenerarReporte() {
        reporteGenerar = new Inforeportes();
    }

    public void mostrarDialogoBuscarReporte() {
        try {
            listaIR = administrarReportes.listInforeportesUsuario();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ReportesDialogo");
            context.execute("ReportesDialogo.show()");
        } catch (Exception e) {
            System.out.println("Error mostrarDialogoBuscarReporte : " + e.toString());
        }
    }

    public void salir(){
        if (bandera == 1) {
            System.out.println("Desactivar");
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listaIR = null;
        parametroDeInforme = null;
        parametroModificacion = null;
        listaIRRespaldo = null;
        casilla = -1;
    }
    
    public void actualizarSeleccionInforeporte() {
        if (bandera == 1) {
            System.out.println("Desactivar");
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }
        listaIR = new ArrayList<Inforeportes>();
        listaIR.add(inforreporteSeleccionado);
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesNomina");
    }

    public void cancelarSeleccionInforeporte() {
        filtrarListInforeportesUsuario = null;
        inforreporteSeleccionado = new Inforeportes();
        aceptar = true;
    }

    public void mostrarTodos() {
        System.out.println("Muestra todos los reportes");
        listaIR = null;
        System.out.println("Lista IR " + listaIR);
        getListaIR();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update(":form:reportesNomina");
    }

    public void activarCtrlF11() {
        System.out.println("Filtrado Tabla VigenciaLocalizacion");
        if (bandera == 0) {
            System.out.println("Activar");
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("width: 25px");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("width: 200px");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("width: 80px");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            tipoLista = 1;
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            codigoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:codigoIR");
            codigoIR.setFilterStyle("display: none; visibility: hidden;");
            reporteIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:reporteIR");
            reporteIR.setFilterStyle("display: none; visibility: hidden;");
            tipoIR = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:reportesNomina:tipoIR");
            tipoIR.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:reportesNomina");
            bandera = 0;
            filtrarListInforeportesUsuario = null;
            tipoLista = 0;
        }

    }

    public void reporteModificado(int i) {
        try {
            listaIRRespaldo = administrarReportes.listInforeportesUsuario();
            listaIR = listaIRRespaldo;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:reportesNomina");
        } catch (Exception e) {
            System.out.println("Error en reporteModificado : " + e.toString());
        }
    }

    public void reporteSeleccionado(int i) {
        System.out.println("Posicion del reporte : " + i);
    }

    public void parametrosDeReporte(int i) {
        System.out.println("Parametro posicionado : " + i);
    }

    public ParametrosInformes getParametroDeInforme() {
        try {
            if (parametroDeInforme == null) {
                parametroDeInforme = new ParametrosInformes();
                parametroDeInforme = administrarReportes.parametrosDeReporte();
            }
            if (parametroDeInforme.getGrupo() == null) {
                parametroDeInforme.setGrupo(new GruposConceptos());
            }
            if (parametroDeInforme.getUbicaciongeografica() == null) {
                parametroDeInforme.setUbicaciongeografica(new UbicacionesGeograficas());
            }
            if (parametroDeInforme.getTipoasociacion() == null) {
                parametroDeInforme.setTipoasociacion(new Tiposasociaciones());
            }
            if (parametroDeInforme.getLocalizacion() == null) {
                parametroDeInforme.setLocalizacion(new Estructuras());
            }
            if (parametroDeInforme.getTipotrabajador() == null) {
                parametroDeInforme.setTipotrabajador(new TiposTrabajadores());
            }
            if (parametroDeInforme.getTercero() == null) {
                parametroDeInforme.setTercero(new Terceros());
            }
            if (parametroDeInforme.getProceso() == null) {
                parametroDeInforme.setProceso(new Procesos());
            }
            if (parametroDeInforme.getAsociacion() == null) {
                parametroDeInforme.setAsociacion(new Asociaciones());
            }
            return parametroDeInforme;
        } catch (Exception e) {
            System.out.println("Error getParametroDeInforme : " + e);
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
                listaIR = administrarReportes.listInforeportesUsuario();
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

    public Inforeportes getReporteGenerar() {
        return this.reporteGenerar;
    }

    public void setReporteGenerar(Inforeportes rG) {
        this.reporteGenerar = rG;
    }
}

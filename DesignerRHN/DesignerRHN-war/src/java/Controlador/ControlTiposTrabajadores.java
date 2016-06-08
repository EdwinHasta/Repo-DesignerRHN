/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.TiposCotizantes;
import Entidades.TiposTrabajadores;
import Entidades.VigenciasDiasTT;
import Entidades.VigenciasTiposTrabajadores;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposCotizantesInterface;
import InterfaceAdministrar.AdministrarTiposTrabajadoresInterface;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.Column;
import javax.servlet.http.HttpSession;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

/**
 *
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlTiposTrabajadores implements Serializable {

    @EJB
    AdministrarTiposCotizantesInterface administrarTiposCotizantes;
    @EJB
    AdministrarTiposTrabajadoresInterface administrarTiposTrabajadores;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Lista Tipos Trabajadores
    private List<TiposTrabajadores> listaTiposTrabajadores;
    private List<TiposTrabajadores> filtrarTiposTrabajadores;
    private TiposTrabajadores tipoTrabajadorSeleccionado;
    private List<VigenciasDiasTT> listaVigenciasDiasTT;
    private List<VigenciasDiasTT> filtrarVigenciasDiasTT;
    private VigenciasDiasTT vigenciaDiaSeleccionado;
    //Lista Tipos Cotizantes
    private List<TiposCotizantes> lovTiposCotizantes;
    private List<TiposCotizantes> filtrarLovTiposCotizantes;
    private TiposCotizantes tipoCotizanteLovSeleccionado;
    //Columnas Tabla Tipos Trabajadores
    private Column ttCodigo, ttDescripcion, ttFactorD, ttTipoCot, ttDiasVacNoOrd, ttDiasVac;
    //Variables de control
    private int tipoActualizacion;
    private int bandera;
    private boolean aceptar;
    private boolean guardado;
    private int cualCelda, tipoLista;
    private boolean permitirIndex;
    public String infoRegistroTT;
    public String infoRegistroTC;
    private String altoTabla;
    private DataTable tablatt;
    //modificar
    private List<TiposTrabajadores> listTTModificar;
    //crear
    private TiposTrabajadores nuevoTipoT;
    private List<TiposTrabajadores> listTTCrear;
    private BigInteger nuevaSecuencia;
    private int intNuevaSec;
    //borrar
    private List<TiposTrabajadores> listTTBorrar;
    //editar celda
    private TiposTrabajadores editarTT;
    //duplicar
    private TiposTrabajadores duplicarTT;
    //Backs
//    private String reformaLaboral;
    //pagina anterior
    private String paginaAnterior;

    /**
     * Creates a new instance of ControlTiposTrabajadores
     */
    public ControlTiposTrabajadores() {
        //Lista Tipos Trabajadores
        listaTiposTrabajadores = null;
        tipoTrabajadorSeleccionado = null;
        listaVigenciasDiasTT = null;
        //Lista Tipos Cotizantes
        lovTiposCotizantes = null;
        tipoCotizanteLovSeleccionado = null;
        //Variables de control
        tipoActualizacion = -1;
        aceptar = true;
        guardado = true;
        permitirIndex = true;
        cualCelda = -1;
        tipoLista = 0;
        infoRegistroTT = "";
        infoRegistroTC = "";
        altoTabla = "180";
        //modificar
        listTTModificar = new ArrayList<TiposTrabajadores>();
        //crear
        nuevoTipoT = new TiposTrabajadores();
        listTTCrear = new ArrayList<TiposTrabajadores>();
        intNuevaSec = 0;
        //borrar
        listTTBorrar = new ArrayList<TiposTrabajadores>();
        //editar celda
        editarTT = new TiposTrabajadores();
        //duplicar
        duplicarTT = new TiposTrabajadores();
        paginaAnterior = "";
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarTiposTrabajadores.obtenerConexion(ses.getId());
            administrarTiposCotizantes.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirPaginaAnterior(String pagina) {
        paginaAnterior = pagina;
        getListaTiposTrabajadores();
        if (listaTiposTrabajadores != null) {
            if (listaTiposTrabajadores != null) {
                tipoTrabajadorSeleccionado = listaTiposTrabajadores.get(0);
                listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
                System.out.println("cambiarIndice. listaVigenciasDiasTT : " + listaVigenciasDiasTT);
            }
        }
    }

    public String retornarPagina() {
        return paginaAnterior;
    }

    public void cambiarIndice(TiposTrabajadores tipoTrabajador, int celda) {
        RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("tipoContratoTablaSeleccionado antes : " + tipoTrabajadorSeleccionado);
        tipoTrabajadorSeleccionado = tipoTrabajador;
        cualCelda = celda;
        System.out.println("Secuencia : " + tipoTrabajadorSeleccionado.getSecuencia());
        listaVigenciasDiasTT = administrarTiposTrabajadores.consultarDiasPorTipoT(tipoTrabajadorSeleccionado.getSecuencia());
        System.out.println("cambiarIndice. listaVigenciasDiasTT : " + listaVigenciasDiasTT);
        context.update("form:datosVigenciasDTT");
    }

    //GET'S y SET'S
    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public TiposTrabajadores getDuplicarTT() {
        return duplicarTT;
    }

    public void setDuplicarTT(TiposTrabajadores duplicarTT) {
        this.duplicarTT = duplicarTT;
    }

    public TiposTrabajadores getEditarTT() {
        return editarTT;
    }

    public void setEditarTT(TiposTrabajadores editarTT) {
        this.editarTT = editarTT;
    }

    public List<TiposCotizantes> getFiltrarLovTiposCotizantes() {
        return filtrarLovTiposCotizantes;
    }

    public void setFiltrarLovTiposCotizantes(List<TiposCotizantes> filtrarLovTiposCotizantes) {
        this.filtrarLovTiposCotizantes = filtrarLovTiposCotizantes;
    }

    public List<TiposTrabajadores> getFiltrarTiposTrabajadores() {
        return filtrarTiposTrabajadores;
    }

    public void setFiltrarTiposTrabajadores(List<TiposTrabajadores> filtrarTiposTrabajadores) {
        this.filtrarTiposTrabajadores = filtrarTiposTrabajadores;
    }

    public String getInfoRegistroTC() {
        return infoRegistroTC;
    }

    public void setInfoRegistroTC(String infoRegistroTC) {
        this.infoRegistroTC = infoRegistroTC;
    }

    public String getInfoRegistroTT() {
        return infoRegistroTT;
    }

    public void setInfoRegistroTT(String infoRegistroTT) {
        this.infoRegistroTT = infoRegistroTT;
    }

    public List<TiposTrabajadores> getListaTiposTrabajadores() {
        try {
            if (listaTiposTrabajadores == null) {
                listaTiposTrabajadores = administrarTiposTrabajadores.buscarTiposTrabajadoresTT();
            }
        } catch (Exception e) {
            System.out.println("ERROR getListaTiposTrabajadores : " + e);
            listaTiposTrabajadores = null;
        }
        return listaTiposTrabajadores;
    }

    public void setListaTiposTrabajadores(List<TiposTrabajadores> listaTiposTrabajadores) {
        this.listaTiposTrabajadores = listaTiposTrabajadores;
    }

    public List<TiposCotizantes> getLovTiposCotizantes() {
        if (lovTiposCotizantes == null) {
            lovTiposCotizantes = administrarTiposCotizantes.tiposCotizantes();
        }
        return lovTiposCotizantes;
    }

    public void setLovTiposCotizantes(List<TiposCotizantes> lovTiposCotizantes) {
        this.lovTiposCotizantes = lovTiposCotizantes;
    }

    public TiposCotizantes getTipoCotizanteLovSeleccionado() {
        return tipoCotizanteLovSeleccionado;
    }

    public void setTipoCotizanteLovSeleccionado(TiposCotizantes tipoCotizanteLovSeleccionado) {
        this.tipoCotizanteLovSeleccionado = tipoCotizanteLovSeleccionado;
    }

    public TiposTrabajadores getTipoTrabajadorSeleccionado() {
        return tipoTrabajadorSeleccionado;
    }

    public void setTipoTrabajadorSeleccionado(TiposTrabajadores tipoTrabajadorSeleccionado) {
        this.tipoTrabajadorSeleccionado = tipoTrabajadorSeleccionado;
    }

    public List<VigenciasDiasTT> getListaVigenciasDiasTT() {
        System.out.println("Entro en getListaVigenciasDiasTT listaVigenciasDiasTT : " + listaVigenciasDiasTT);
        return listaVigenciasDiasTT;
    }

    public void setListaVigenciasDiasTT(List<VigenciasDiasTT> listaVigenciasDiasTT) {
        this.listaVigenciasDiasTT = listaVigenciasDiasTT;
    }

    public VigenciasDiasTT getVigenciaDiaSeleccionado() {
        return vigenciaDiaSeleccionado;
    }

    public void setVigenciaDiaSeleccionado(VigenciasDiasTT vigenciaDiaSeleccionado) {
        this.vigenciaDiaSeleccionado = vigenciaDiaSeleccionado;
    }

    public List<VigenciasDiasTT> getFiltrarVigenciasDiasTT() {
        return filtrarVigenciasDiasTT;
    }

    public void setFiltrarVigenciasDiasTT(List<VigenciasDiasTT> filtrarVigenciasDiasTT) {
        this.filtrarVigenciasDiasTT = filtrarVigenciasDiasTT;
    }
}

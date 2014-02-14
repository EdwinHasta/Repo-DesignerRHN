package Controlador;

import Entidades.DiasLaborables;
import Entidades.TiposContratos;
import Entidades.TiposDias;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarTiposContratosInterface;
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
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlTipoContrato implements Serializable {

    @EJB
    AdministrarTiposContratosInterface administrarTipoContrato;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<TiposContratos> listaTiposContratos;
    private List<TiposContratos> filtrarListaTiposContratos;
    ///////
    private List<DiasLaborables> listaDiasLaborables;
    private List<DiasLaborables> filtrarListaDiasLaborables;
    //Activo/Desactivo Crtl + F11
    private int bandera, banderaDiasLab;
    //Columnas Tabla VC
    private Column tipoCCodigo, tipoCNombre, tipoCPeriodo, tipoCVE, tipoCForza;
    private Column diasLabTipoDia, diasLabDia, diasLabHL;
    //Otros
    private boolean aceptar;
    private int index, indexDias, indexAux, indexAuxDias;
    //modificar
    private List<TiposContratos> listTiposContratosModificar;
    private List<DiasLaborables> listDiasLaborablesModificar;
    private boolean guardado, guardadoDias;
    //crear 
    private TiposContratos nuevoTipoContrato;
    private DiasLaborables nuevoDiaLaborable;
    private List<TiposContratos> listTiposContratosCrear;
    private List<DiasLaborables> listDiasLaborablesCrear;
    private BigInteger l;
    private int k;
    //borrar 
    private List<TiposContratos> listTiposContratosBorrar;
    private List<DiasLaborables> listDiasLaborablesBorrar;
    //editar celda
    private TiposContratos editarTipoContrato;
    private DiasLaborables editarDiaLaborable;
    private int cualCelda, tipoLista, cualCeldaDias, tipoListaDias;
    //duplicar
    private TiposContratos duplicarTipoContrato;
    private DiasLaborables duplicarDiaLaborable;
    private BigInteger secRegistro, secRegistroDias;
    private BigInteger backUpSecRegistro, backUpSecRegistroDias;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;
    private String tipoDia;

    //////////////////////
    private List<TiposDias> lovTiposDias;
    private List<TiposDias> filtrarLovTiposDias;
    private TiposDias tipoDiaSeleccionado;

    private boolean permitirIndexDias;
    private int tipoActualizacion;
    private short auxCodigoTipoContrato;
    private String auxNombreTipoContrato;
    //
    private boolean cambiosPagina;
    //
    private String altoTablaTiposC, altoTablaDiasLab;
    //
    private String nombreTipoCColnar;
    private short codigoTipoCClonar;
    //
    private List<TiposContratos> lovTiposContratos;
    private List<TiposContratos> filtrarLovTiposContratos;
    private TiposContratos tipoContratoSeleccionado;
    //
    private TiposContratos tipoContratoAClonar;
    //
    private String auxNombreClonar;
    private short auxCodigoClonar;

    public ControlTipoContrato() {
        tipoContratoAClonar = new TiposContratos();
        tipoContratoSeleccionado = new TiposContratos();
        lovTiposContratos = null;
        altoTablaTiposC = "137";
        altoTablaDiasLab = "120";
        cambiosPagina = true;
        permitirIndexDias = true;
        tipoActualizacion = -1;
        lovTiposDias = null;
        lovTiposDias = null;
        tipoDiaSeleccionado = new TiposDias();
        indexDias = -1;
        backUpSecRegistro = null;
        listaTiposContratos = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listTiposContratosBorrar = new ArrayList<TiposContratos>();
        listDiasLaborablesModificar = new ArrayList<DiasLaborables>();
        listDiasLaborablesBorrar = new ArrayList<DiasLaborables>();
        //crear aficiones
        listTiposContratosCrear = new ArrayList<TiposContratos>();
        listDiasLaborablesCrear = new ArrayList<DiasLaborables>();
        k = 0;
        //modificar aficiones
        listTiposContratosModificar = new ArrayList<TiposContratos>();
        //editar
        editarTipoContrato = new TiposContratos();
        editarDiaLaborable = new DiasLaborables();
        cualCelda = -1;
        cualCeldaDias = -1;
        tipoListaDias = 0;
        tipoLista = 0;
        //guardar
        guardado = true;
        guardadoDias = true;
        //Crear VC
        nuevoTipoContrato = new TiposContratos();
        nuevoDiaLaborable = new DiasLaborables();
        nuevoDiaLaborable.setTipodia(new TiposDias());
        duplicarDiaLaborable = new DiasLaborables();
        duplicarTipoContrato = new TiposContratos();
        secRegistro = null;
        secRegistroDias = null;
        backUpSecRegistroDias = null;
        bandera = 0;
        banderaDiasLab = 0;
    }

    public void inicializarPagina() {
        listaDiasLaborables = null;
        listaTiposContratos = null;
        getListaTiposContratos();
        int tam = listaTiposContratos.size();
        if (tam >= 1) {
            index = 0;
            getListaDiasLaborables();
        }
    }

    public boolean validarCamposNulosTipoContrato(int i) {
        boolean retorno = true;
        if (i == 0) {
            TiposContratos aux = new TiposContratos();
            if (tipoLista == 0) {
                aux = listaTiposContratos.get(index);
            }
            if (tipoLista == 1) {
                aux = filtrarListaTiposContratos.get(index);
            }
            if (aux.getCodigo() >= 0 || aux.getNombre().isEmpty()) {
                retorno = false;
            }
        }
        if (i == 1) {
            if (nuevoTipoContrato.getCodigo() >= 0 || nuevoTipoContrato.getNombre().isEmpty()) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarTipoContrato.getCodigo() >= 0 || duplicarTipoContrato.getNombre().isEmpty()) {
                retorno = false;
            }
        }
        return retorno;
    }

    public boolean validarCamposNulosDiaLaborable(int i) {
        boolean retorno = true;
        if (i == 1) {
            if (nuevoDiaLaborable.getDia() == null || nuevoDiaLaborable.getTipodia().getSecuencia() == null) {
                retorno = false;
            }
        }
        if (i == 2) {
            if (duplicarDiaLaborable.getDia() == null || duplicarDiaLaborable.getTipodia().getSecuencia() == null) {
                retorno = false;
            }
        }
        return retorno;
    }

    public void procesoModificacionTipoContrato(int i) {
        index = i;
        boolean respuesta = validarCamposNulosTipoContrato(0);
        if (respuesta == true) {
            modificarTipoContrato(i);
        } else {
            if (tipoLista == 0) {
                listaTiposContratos.get(index).setCodigo(auxCodigoTipoContrato);
                listaTiposContratos.get(index).setNombre(auxNombreTipoContrato);
            }
            if (tipoLista == 1) {
                filtrarListaTiposContratos.get(index).setCodigo(auxCodigoTipoContrato);
                filtrarListaTiposContratos.get(index).setNombre(auxNombreTipoContrato);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoContrato");
            context.execute("errorDatosNullTipoContrato.show()");
        }
    }

    public void modificarTipoContrato(int indice) {
        int tamDes = 0;
        if (tipoLista == 0) {
            tamDes = listaTiposContratos.get(indice).getNombre().length();
        }
        if (tipoLista == 1) {
            tamDes = filtrarListaTiposContratos.get(indice).getNombre().length();
        }
        if (tamDes >= 1 && tamDes <= 30) {
            if (tipoLista == 0) {
                String textM = listaTiposContratos.get(indice).getNombre().toUpperCase();
                listaTiposContratos.get(indice).setNombre(textM);
                if (!listTiposContratosCrear.contains(listaTiposContratos.get(indice))) {
                    if (listTiposContratosModificar.isEmpty()) {
                        listTiposContratosModificar.add(listaTiposContratos.get(indice));
                    } else if (!listTiposContratosModificar.contains(listaTiposContratos.get(indice))) {
                        listTiposContratosModificar.add(listaTiposContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                String textM = filtrarListaTiposContratos.get(indice).getNombre().toUpperCase();
                filtrarListaTiposContratos.get(indice).setNombre(textM);
                if (!listTiposContratosCrear.contains(filtrarListaTiposContratos.get(indice))) {
                    if (listTiposContratosModificar.isEmpty()) {
                        listTiposContratosModificar.add(filtrarListaTiposContratos.get(indice));
                    } else if (!listTiposContratosModificar.contains(filtrarListaTiposContratos.get(indice))) {
                        listTiposContratosModificar.add(filtrarListaTiposContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
            index = -1;
            secRegistro = null;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosTipoContrato");
        } else {
            if (tipoLista == 0) {
                listaTiposContratos.get(index).setNombre(auxNombreTipoContrato);
            }
            if (tipoLista == 1) {
                filtrarListaTiposContratos.get(index).setNombre(auxNombreTipoContrato);
            }
            index = -1;
            secRegistro = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoContrato");
            context.execute("errorNombreTipoContrato.show()");
        }

    }

    public void modificarDiaLaborable(int indice) {
        if (tipoListaDias == 0) {
            if (!listDiasLaborablesCrear.contains(listaDiasLaborables.get(indice))) {
                if (listDiasLaborablesModificar.isEmpty()) {
                    listDiasLaborablesModificar.add(listaDiasLaborables.get(indice));
                } else if (!listDiasLaborablesModificar.contains(listaDiasLaborables.get(indice))) {
                    listDiasLaborablesModificar.add(listaDiasLaborables.get(indice));
                }
                if (guardadoDias == true) {
                    guardadoDias = false;
                }
            }
        }
        if (tipoListaDias == 1) {
            if (!listDiasLaborablesCrear.contains(filtrarListaDiasLaborables.get(indice))) {
                if (listDiasLaborablesModificar.isEmpty()) {
                    listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indice));
                } else if (!listDiasLaborablesModificar.contains(filtrarListaDiasLaborables.get(indice))) {
                    listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indice));
                }
                if (guardadoDias == true) {
                    guardadoDias = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexDias = -1;
        secRegistroDias = null;
        cambiosPagina = false;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
        context.update("form:datosDiasLaborables");
    }

    public void modificarDiaLaborable(int indice, String confirmarCambio, String valorConfirmar) {
        indexDias = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("DIAS")) {
            if (tipoLista == 0) {
                listaDiasLaborables.get(indice).getTipodia().setDescripcion(tipoDia);
            } else {
                filtrarListaDiasLaborables.get(indice).getTipodia().setDescripcion(tipoDia);
            }
            for (int i = 0; i < lovTiposDias.size(); i++) {
                if (lovTiposDias.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listaDiasLaborables.get(indice).setTipodia(lovTiposDias.get(indiceUnicoElemento));
                } else {
                    filtrarListaDiasLaborables.get(indice).setTipodia(lovTiposDias.get(indiceUnicoElemento));
                }
                lovTiposDias.clear();
                getLovTiposDias();
                cambiosPagina = false;
                context.update("form:ACEPTAR");
            } else {
                permitirIndexDias = false;
                context.update("form:TipoDiaDialogo");
                context.execute("TipoDiaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoListaDias == 0) {
                if (!listDiasLaborablesCrear.contains(listaDiasLaborables.get(indice))) {
                    if (listDiasLaborablesModificar.isEmpty()) {
                        listDiasLaborablesModificar.add(listaDiasLaborables.get(indice));
                    } else if (!listDiasLaborablesModificar.contains(listaDiasLaborables.get(indice))) {
                        listDiasLaborablesModificar.add(listaDiasLaborables.get(indice));
                    }
                    if (guardadoDias == true) {
                        guardadoDias = false;
                    }
                }
            }
            if (tipoListaDias == 1) {
                if (!listDiasLaborablesCrear.contains(filtrarListaDiasLaborables.get(indice))) {
                    if (listDiasLaborablesModificar.isEmpty()) {
                        listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indice));
                    } else if (!listDiasLaborablesModificar.contains(filtrarListaDiasLaborables.get(indice))) {
                        listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indice));
                    }
                    if (guardadoDias == true) {
                        guardadoDias = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
        }
        context.update("form:datosDiasLaborables");
    }

    public void cambiarIndice(int indice, int celda) {
        if (guardadoDias == true) {
            index = indice;
            cualCelda = celda;
            indexAux = indice;
            indexDias = -1;
            if (tipoLista == 0) {
                auxCodigoTipoContrato = listaTiposContratos.get(index).getCodigo();
                secRegistro = listaTiposContratos.get(index).getSecuencia();
                auxNombreTipoContrato = listaTiposContratos.get(index).getNombre();
            }
            if (tipoLista == 1) {
                auxCodigoTipoContrato = filtrarListaTiposContratos.get(index).getCodigo();
                secRegistro = filtrarListaTiposContratos.get(index).getSecuencia();
                auxNombreTipoContrato = filtrarListaTiposContratos.get(index).getNombre();
            }
            listaDiasLaborables = null;
            getListaDiasLaborables();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDiasLaborables");
            if (banderaDiasLab == 1) {
                altoTablaDiasLab = "120";
                diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
                diasLabDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
                diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
                diasLabHL.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
                banderaDiasLab = 0;
                filtrarListaDiasLaborables = null;
                tipoListaDias = 0;
            }

        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceDia(int indice, int celda) {
        if (permitirIndexDias == true) {
            indexDias = indice;
            index = -1;
            indexAuxDias = indice;
            cualCeldaDias = celda;
            if (tipoListaDias == 0) {
                secRegistroDias = listaDiasLaborables.get(indexDias).getSecuencia();
                tipoDia = listaDiasLaborables.get(indexDias).getTipodia().getDescripcion();
            }
            if (tipoListaDias == 1) {
                secRegistroDias = filtrarListaDiasLaborables.get(indexDias).getSecuencia();
                tipoDia = filtrarListaDiasLaborables.get(indexDias).getTipodia().getDescripcion();
            }
            if (bandera == 1) {
                altoTablaTiposC = "137";
                tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
                tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
                tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
                tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
                tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
                tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
                tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
                tipoCVE.setFilterStyle("display: none; visibility: hidden;");
                tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
                tipoCForza.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoContrato");
                bandera = 0;
                filtrarListaTiposContratos = null;
                tipoLista = 0;
            }
        }
    }
    //GUARDAR

    public void guardarYSalir() {
        guardarGeneral();
        salir();
    }

    public void cancelarYSalir() {
        cancelarModificacionGeneral();
        salir();
    }

    public void guardarGeneral() {
        if (guardado == false || guardadoDias == false) {
            if (guardado == false) {
                guardarCambiosTipoContrato();
            }
            if (guardadoDias == false) {
                guardarCambiosDiasLaborables();
            }
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:growl");
            cambiosPagina = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
        }
    }

    public void guardarCambiosTipoContrato() {

        if (!listTiposContratosBorrar.isEmpty()) {
                administrarTipoContrato.borrarTiposContratos(listTiposContratosBorrar);
            
            listTiposContratosBorrar.clear();
        }
        if (!listTiposContratosCrear.isEmpty()) {
                administrarTipoContrato.crearTiposContratos(listTiposContratosCrear);
            
            listTiposContratosCrear.clear();
        }
        if (!listTiposContratosModificar.isEmpty()) {
            administrarTipoContrato.editarTiposContratos(listTiposContratosModificar);
            listTiposContratosModificar.clear();
        }
        listaTiposContratos = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoContrato");
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;

        index = -1;
        secRegistro = null;

    }

    public void guardarCambiosDiasLaborables() {
        if (!listDiasLaborablesBorrar.isEmpty()) {
                administrarTipoContrato.borrarDiasLaborables(listDiasLaborablesBorrar);
            
            listDiasLaborablesBorrar.clear();
        }
        if (!listDiasLaborablesCrear.isEmpty()) {
                administrarTipoContrato.crearDiasLaborables(listDiasLaborablesCrear);
            
            listDiasLaborablesCrear.clear();
        }
        if (!listDiasLaborablesModificar.isEmpty()) {
            administrarTipoContrato.editarDiasLaborables(listDiasLaborablesModificar);
            listDiasLaborablesModificar.clear();
        }
        listaDiasLaborables = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDiasLaborables");
        guardadoDias = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
        k = 0;

        indexDias = -1;
        secRegistroDias = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacionGeneral() {
        if (guardado == false) {
            cancelarModificacionTipoContrato();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosTipoContrato");
        }
        if (guardadoDias == false) {
            cancelarModificacionDiaLaborable();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosDiasLaborables");
        }
        cancelarProcesoClonado();
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void cancelarProcesoClonado() {
        codigoTipoCClonar = 0;
        nombreTipoCColnar = " ";
        tipoContratoAClonar = new TiposContratos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:codigoTipoCClonar");
        context.update("form:nombreTipoCClonar");
        context.update("form:codigoTipoCClonarBase");
        context.update("form:nombreTipoCClonarBase");
    }

    public void cancelarModificacionTipoContrato() {
        if (bandera == 1) {
            altoTablaTiposC = "137";
            tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
            tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
            tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
            tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
            tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
            tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
            tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
            tipoCVE.setFilterStyle("display: none; visibility: hidden;");
            tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
            tipoCForza.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoContrato");
            bandera = 0;
            filtrarListaTiposContratos = null;
            tipoLista = 0;
        }
        listTiposContratosBorrar.clear();
        listTiposContratosCrear.clear();
        listTiposContratosModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listaTiposContratos = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosTipoContrato");
    }

    public void cancelarModificacionDiaLaborable() {
        if (banderaDiasLab == 1) {
            altoTablaDiasLab = "120";
            diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
            diasLabDia.setFilterStyle("display: none; visibility: hidden;");
            diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
            diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
            diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
            diasLabHL.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
            banderaDiasLab = 0;
            filtrarListaDiasLaborables = null;
            tipoListaDias = 0;
        }
        listDiasLaborablesBorrar.clear();
        listDiasLaborablesCrear.clear();
        listDiasLaborablesModificar.clear();
        indexDias = -1;
        secRegistroDias = null;
        k = 0;
        listaDiasLaborables = null;
        guardadoDias = true;
        permitirIndexDias = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosDiasLaborables");
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarTipoContrato = listaTiposContratos.get(index);
            }
            if (tipoLista == 1) {
                editarTipoContrato = filtrarListaTiposContratos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoTipoContratoD");
                context.execute("editarCodigoTipoContratoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombreTipoContratoD");
                context.execute("editarNombreTipoContratoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDuracionPTipoContratoD");
                context.execute("editarDuracionPTipoContratoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarVinculacionTipoContratoD");
                context.execute("editarVinculacionTipoContratoD.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
        }
        if (indexDias >= 0) {
            if (tipoListaDias == 0) {
                editarDiaLaborable = listaDiasLaborables.get(indexDias);
            }
            if (tipoListaDias == 1) {
                editarDiaLaborable = listaDiasLaborables.get(indexDias);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaDias == 0) {
                context.update("formularioDialogos:editarTipoDiaDiaLabD");
                context.execute("editarTipoDiaDiaLabD.show()");
                cualCeldaDias = -1;
            } else if (cualCeldaDias == 1) {
                context.update("formularioDialogos:editarDiaDiaLabD");
                context.execute("editarDiaDiaLabD.show()");
                cualCeldaDias = -1;
            } else if (cualCeldaDias == 2) {
                context.update("formularioDialogos:editarHorasDiaLabD");
                context.execute("editarHorasDiaLabD.show()");
                cualCeldaDias = -1;
            }
            indexDias = -1;
            secRegistroDias = null;
        }

    }

    public void dialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        int tam1 = listaTiposContratos.size();
        int tam2 = listaDiasLaborables.size();
        if (tam1 == 0 || tam2 == 0) {
            context.update("formularioDialogos:verificarNuevoRegistro");
            context.execute("verificarNuevoRegistro.show()");
        } else {
            if (index >= 0) {
                context.update("formularioDialogos:NuevoRegistroTipoContrato");
                context.execute("NuevoRegistroTipoContrato.show()");
            }
            if (indexDias >= 0) {
                context.update("formularioDialogos:NuevoRegistroDiaLaboral");
                context.execute("NuevoRegistroDiaLaboral.show()");
            }
        }
    }

    //CREAR 
    public void agregarNuevoTipoContrato() {
        boolean respueta = validarCamposNulosTipoContrato(1);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoTipoContrato.getNombre().length();
            if (tamDes >= 1 && tamDes <= 30) {
                if (bandera == 1) {
                    altoTablaTiposC = "137";
                    tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
                    tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
                    tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
                    tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
                    tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
                    tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
                    tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
                    tipoCVE.setFilterStyle("display: none; visibility: hidden;");
                    tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
                    tipoCForza.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosTipoContrato");
                    bandera = 0;
                    filtrarListaTiposContratos = null;
                    tipoLista = 0;
                }

                k++;
                l = BigInteger.valueOf(k);
                String text = nuevoTipoContrato.getNombre().toUpperCase();
                nuevoTipoContrato.setNombre(text);
                nuevoTipoContrato.setSecuencia(l);
                listTiposContratosCrear.add(nuevoTipoContrato);
                listaTiposContratos.add(nuevoTipoContrato);
                nuevoTipoContrato = new TiposContratos();
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosTipoContrato");
                context.execute("NuevoRegistroTipoContrato.hide()");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                index = -1;
                secRegistro = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorNombreTipoContrato.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullTipoContrato.show()");
        }
    }

    public void agregarNuevoDiaLaborable() {
        boolean respueta = validarCamposNulosDiaLaborable(1);
        if (respueta == true) {
            if (banderaDiasLab == 1) {
                altoTablaDiasLab = "120";
                diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
                diasLabDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
                diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
                diasLabHL.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
                banderaDiasLab = 0;
                filtrarListaDiasLaborables = null;
                tipoListaDias = 0;
            }
            k++;
            l = BigInteger.valueOf(k);
            nuevoDiaLaborable.setSecuencia(l);
            if (tipoLista == 0) {
                nuevoDiaLaborable.setTipocontrato(listaTiposContratos.get(indexAux));
            }
            if (tipoLista == 1) {
                nuevoDiaLaborable.setTipocontrato(filtrarListaTiposContratos.get(indexAux));
            }
            if (listaDiasLaborables.size() == 0) {
                listaDiasLaborables = new ArrayList<DiasLaborables>();
            }
            listDiasLaborablesCrear.add(nuevoDiaLaborable);
            listaDiasLaborables.add(nuevoDiaLaborable);
            System.out.println("nuevoDiaLaborable : " + nuevoDiaLaborable.getTipodia().getDescripcion());
            System.out.println("listaDiasLaborables : " + listaDiasLaborables.size());
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            index = indexAux;
            context.update("form:datosDiasLaborables");
            context.execute("NuevoRegistroDiaLaboral.hide()");
            nuevoDiaLaborable = new DiasLaborables();
            nuevoDiaLaborable.setTipodia(new TiposDias());
            if (guardadoDias == true) {
                guardadoDias = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            indexDias = -1;
            secRegistroDias = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullDiasLab.show()");
        }
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     */
    public void limpiarNuevaTipoContrato() {
        nuevoTipoContrato = new TiposContratos();
        index = -1;
        secRegistro = null;
    }

    public void limpiarNuevaDiaLaborable() {
        nuevoDiaLaborable = new DiasLaborables();
        nuevoDiaLaborable.setTipodia(new TiposDias());
        indexDias = -1;
        secRegistroDias = null;
    }

    //DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
        if (index >= 0) {
            duplicarTipoContratoM();
        }
        if (indexDias >= 0) {
            duplicarDiaLaborableM();
        }
    }

    public void duplicarTipoContratoM() {
        if (index >= 0) {
            duplicarTipoContrato = new TiposContratos();
            k++;
            l = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarTipoContrato.setSecuencia(l);
                duplicarTipoContrato.setCodigo(listaTiposContratos.get(index).getCodigo());
                duplicarTipoContrato.setNombre(listaTiposContratos.get(index).getNombre());
                duplicarTipoContrato.setDuracionperiodoprueba(listaTiposContratos.get(index).getDuracionperiodoprueba());
                duplicarTipoContrato.setVinculacionempresa(listaTiposContratos.get(index).getVinculacionempresa());
                duplicarTipoContrato.setForzacotizacionpila30dias(listaTiposContratos.get(index).getForzacotizacionpila30dias());
            }
            if (tipoLista == 1) {
                duplicarTipoContrato.setSecuencia(l);
                duplicarTipoContrato.setCodigo(filtrarListaTiposContratos.get(index).getCodigo());
                duplicarTipoContrato.setNombre(filtrarListaTiposContratos.get(index).getNombre());
                duplicarTipoContrato.setDuracionperiodoprueba(filtrarListaTiposContratos.get(index).getDuracionperiodoprueba());
                duplicarTipoContrato.setVinculacionempresa(filtrarListaTiposContratos.get(index).getVinculacionempresa());
                duplicarTipoContrato.setForzacotizacionpila30dias(filtrarListaTiposContratos.get(index).getForzacotizacionpila30dias());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroTipoContrato");
            context.execute("DuplicarRegistroTipoContrato.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void duplicarDiaLaborableM() {
        if (indexDias >= 0) {
            duplicarDiaLaborable = new DiasLaborables();
            k++;
            l = BigInteger.valueOf(k);
            if (tipoListaDias == 0) {
                duplicarDiaLaborable.setSecuencia(l);
                duplicarDiaLaborable.setDia(listaDiasLaborables.get(indexDias).getDia());
                duplicarDiaLaborable.setHoraslaborables(listaDiasLaborables.get(indexDias).getHoraslaborables());
                duplicarDiaLaborable.setTipodia(listaDiasLaborables.get(indexDias).getTipodia());
            }
            if (tipoListaDias == 1) {
                duplicarDiaLaborable.setSecuencia(l);
                duplicarDiaLaborable.setDia(filtrarListaDiasLaborables.get(indexDias).getDia());
                duplicarDiaLaborable.setHoraslaborables(filtrarListaDiasLaborables.get(indexDias).getHoraslaborables());
                duplicarDiaLaborable.setTipodia(filtrarListaDiasLaborables.get(indexDias).getTipodia());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroDiaLaborable");
            context.execute("DuplicarRegistroDiaLaborable.show()");
            indexDias = -1;
            secRegistroDias = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicarTipoContrato() {
        boolean respueta = validarCamposNulosTipoContrato(2);
        if (respueta == true) {
            int tamDes = 0;
            tamDes = nuevoTipoContrato.getNombre().length();
            if (tamDes >= 1 && tamDes <= 30) {
                String text = duplicarTipoContrato.getNombre().toUpperCase();
                duplicarTipoContrato.setNombre(text);
                listaTiposContratos.add(duplicarTipoContrato);
                listTiposContratosCrear.add(duplicarTipoContrato);
                cambiosPagina = false;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:ACEPTAR");
                context.update("form:datosTipoContrato");
                context.execute("DuplicarRegistroTipoContrato.hide()");
                index = -1;
                secRegistro = null;
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
                if (bandera == 1) {
                    altoTablaTiposC = "137";
                    tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
                    tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
                    tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
                    tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
                    tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
                    tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
                    tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
                    tipoCVE.setFilterStyle("display: none; visibility: hidden;");
                    tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
                    tipoCForza.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosTipoContrato");
                    bandera = 0;
                    filtrarListaTiposContratos = null;
                    tipoLista = 0;
                }
                duplicarTipoContrato = new TiposContratos();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorNombreTipoContrato.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullTipoContrato.show()");
        }
    }

    public void confirmarDuplicarDiaLaborable() {
        boolean respueta = validarCamposNulosDiaLaborable(2);
        if (respueta == true) {
            if (tipoLista == 0) {
                duplicarDiaLaborable.setTipocontrato(listaTiposContratos.get(indexAux));
            }
            if (tipoLista == 1) {
                duplicarDiaLaborable.setTipocontrato(filtrarListaTiposContratos.get(indexAux));
            }
            listaDiasLaborables.add(duplicarDiaLaborable);
            listDiasLaborablesCrear.add(duplicarDiaLaborable);
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosDiasLaborables");
            context.execute("DuplicarRegistroDiaLaborable.hide()");
            indexDias = -1;
            secRegistroDias = null;
            if (guardadoDias == true) {
                guardadoDias = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (banderaDiasLab == 1) {
                altoTablaDiasLab = "120";
                diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
                diasLabDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
                diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
                diasLabHL.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
                banderaDiasLab = 0;
                filtrarListaDiasLaborables = null;
                tipoListaDias = 0;
            }
            duplicarDiaLaborable = new DiasLaborables();
            duplicarDiaLaborable.setTipodia(new TiposDias());
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDatosNullDiasLab.show()");
        }
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Set
     */
    public void limpiarDuplicarTipoContrato() {
        duplicarTipoContrato = new TiposContratos();
    }

    public void limpiarDuplicarDiaLaborable() {
        duplicarDiaLaborable = new DiasLaborables();
        duplicarDiaLaborable.setTipodia(new TiposDias());
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //BORRAR VC
    /**
     */
    public void verificarRegistroBorrar() {
        if (index >= 0) {
            if (listaDiasLaborables.isEmpty()) {
                borrarTipoContrato();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorBorrarRegistro.show()");
            }
        }
        if (indexDias >= 0) {
            borrarDiasLaborables();
        }
    }

    public void borrarTipoContrato() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listTiposContratosModificar.isEmpty() && listTiposContratosModificar.contains(listaTiposContratos.get(index))) {
                    int modIndex = listTiposContratosModificar.indexOf(listaTiposContratos.get(index));
                    listTiposContratosModificar.remove(modIndex);
                    listTiposContratosBorrar.add(listaTiposContratos.get(index));
                } else if (!listTiposContratosCrear.isEmpty() && listTiposContratosCrear.contains(listaTiposContratos.get(index))) {
                    int crearIndex = listTiposContratosCrear.indexOf(listaTiposContratos.get(index));
                    listTiposContratosCrear.remove(crearIndex);
                } else {
                    listTiposContratosBorrar.add(listaTiposContratos.get(index));
                }
                listaTiposContratos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listTiposContratosModificar.isEmpty() && listTiposContratosModificar.contains(filtrarListaTiposContratos.get(index))) {
                    int modIndex = listTiposContratosModificar.indexOf(filtrarListaTiposContratos.get(index));
                    listTiposContratosModificar.remove(modIndex);
                    listTiposContratosBorrar.add(filtrarListaTiposContratos.get(index));
                } else if (!listTiposContratosCrear.isEmpty() && listTiposContratosCrear.contains(filtrarListaTiposContratos.get(index))) {
                    int crearIndex = listTiposContratosCrear.indexOf(filtrarListaTiposContratos.get(index));
                    listTiposContratosCrear.remove(crearIndex);
                } else {
                    listTiposContratosBorrar.add(filtrarListaTiposContratos.get(index));
                }
                int VCIndex = listaTiposContratos.indexOf(filtrarListaTiposContratos.get(index));
                listaTiposContratos.remove(VCIndex);
                filtrarListaTiposContratos.remove(index);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosTipoContrato");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarDiasLaborables() {
        if (indexDias >= 0) {
            if (tipoListaDias == 0) {
                if (!listDiasLaborablesModificar.isEmpty() && listDiasLaborablesModificar.contains(listaDiasLaborables.get(indexDias))) {
                    int modIndex = listDiasLaborablesModificar.indexOf(listaDiasLaborables.get(indexDias));
                    listDiasLaborablesModificar.remove(modIndex);
                    listDiasLaborablesBorrar.add(listaDiasLaborables.get(indexDias));
                } else if (!listDiasLaborablesCrear.isEmpty() && listDiasLaborablesCrear.contains(listaDiasLaborables.get(indexDias))) {
                    int crearIndex = listDiasLaborablesCrear.indexOf(listaDiasLaborables.get(indexDias));
                    listDiasLaborablesCrear.remove(crearIndex);
                } else {
                    listDiasLaborablesBorrar.add(listaDiasLaborables.get(indexDias));
                }
                listaDiasLaborables.remove(indexDias);
            }
            if (tipoListaDias == 1) {
                if (!listDiasLaborablesModificar.isEmpty() && listDiasLaborablesModificar.contains(filtrarListaDiasLaborables.get(indexDias))) {
                    int modIndex = listDiasLaborablesModificar.indexOf(filtrarListaDiasLaborables.get(indexDias));
                    listDiasLaborablesModificar.remove(modIndex);
                    listDiasLaborablesBorrar.add(filtrarListaDiasLaborables.get(indexDias));
                } else if (!listDiasLaborablesCrear.isEmpty() && listDiasLaborablesCrear.contains(filtrarListaDiasLaborables.get(indexDias))) {
                    int crearIndex = listDiasLaborablesCrear.indexOf(filtrarListaDiasLaborables.get(indexDias));
                    listDiasLaborablesCrear.remove(crearIndex);
                } else {
                    listDiasLaborablesBorrar.add(filtrarListaDiasLaborables.get(indexDias));
                }
                int VCIndex = listaDiasLaborables.indexOf(filtrarListaDiasLaborables.get(indexDias));
                listaDiasLaborables.remove(VCIndex);
                filtrarListaDiasLaborables.remove(indexDias);
            }

            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosDiasLaborables");
            indexDias = -1;
            secRegistroDias = null;

            if (guardadoDias == true) {
                guardadoDias = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        System.out.println("Index : " + index);
        System.out.println("IndexDia : " + indexDias);
        if (index >= 0) {
            if (bandera == 0) {
                altoTablaTiposC = "115";
                tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
                tipoCCodigo.setFilterStyle("width: 65px");
                tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
                tipoCNombre.setFilterStyle("width: 65px");
                tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
                tipoCPeriodo.setFilterStyle("width: 65px");
                tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
                tipoCVE.setFilterStyle("width: 10px");
                tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
                tipoCForza.setFilterStyle("width: 10px");
                RequestContext.getCurrentInstance().update("form:datosTipoContrato");
                bandera = 1;
            } else if (bandera == 1) {
                altoTablaTiposC = "137";
                tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
                tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
                tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
                tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
                tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
                tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
                tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
                tipoCVE.setFilterStyle("display: none; visibility: hidden;");
                tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
                tipoCForza.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosTipoContrato");
                bandera = 0;
                filtrarListaTiposContratos = null;
                tipoLista = 0;
            }
        }
        if (indexDias >= 0) {
            if (banderaDiasLab == 0) {
                altoTablaDiasLab = "98";
                diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
                diasLabDia.setFilterStyle("width: 80px");
                diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
                diasLabTipoDia.setFilterStyle("width: 80px");
                diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
                diasLabHL.setFilterStyle("width: 80px");
                RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
                banderaDiasLab = 1;
            } else if (banderaDiasLab == 1) {
                altoTablaDiasLab = "120";
                diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
                diasLabDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
                diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
                diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
                diasLabHL.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
                banderaDiasLab = 0;
                filtrarListaDiasLaborables = null;
                tipoListaDias = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            altoTablaTiposC = "137";
            tipoCCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCCodigo");
            tipoCCodigo.setFilterStyle("display: none; visibility: hidden;");
            tipoCNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCNombre");
            tipoCNombre.setFilterStyle("display: none; visibility: hidden;");
            tipoCPeriodo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCPeriodo");
            tipoCPeriodo.setFilterStyle("display: none; visibility: hidden;");
            tipoCVE = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCVE");
            tipoCVE.setFilterStyle("display: none; visibility: hidden;");
            tipoCForza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosTipoContrato:tipoCForza");
            tipoCForza.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosTipoContrato");
            bandera = 0;
            filtrarListaTiposContratos = null;
            tipoLista = 0;
        }
        if (banderaDiasLab == 1) {
            altoTablaDiasLab = "120";
            diasLabDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabDia");
            diasLabDia.setFilterStyle("display: none; visibility: hidden;");
            diasLabTipoDia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabTipoDia");
            diasLabTipoDia.setFilterStyle("display: none; visibility: hidden;");
            diasLabHL = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosDiasLaborables:diasLabHL");
            diasLabHL.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosDiasLaborables");
            banderaDiasLab = 0;
            filtrarListaDiasLaborables = null;
            tipoListaDias = 0;
        }

        listTiposContratosBorrar.clear();
        listTiposContratosCrear.clear();
        listTiposContratosModificar.clear();
        listDiasLaborablesBorrar.clear();
        listDiasLaborablesCrear.clear();
        listDiasLaborablesModificar.clear();
        index = -1;
        indexAux = -1;
        indexAuxDias = -1;
        indexDias = -1;
        secRegistroDias = null;
        secRegistro = null;
        k = 0;
        listaTiposContratos = null;
        listaDiasLaborables = null;
        guardado = true;
        guardadoDias = true;
        cambiosPagina = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:ACEPTAR");
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexDias >= 0) {
            if (cualCeldaDias == 0) {
                context.update("form:TipoDiaDialogo");
                context.execute("TipoDiaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            indexDias = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:TipoDiaDialogo");
            context.execute("TipoDiaDialogo.show()");
        }
    }

    public void valoresBackupAutocompletarGeneral(int tipoNuevo, String Campo) {
        if (Campo.equals("DIAS")) {
            if (tipoNuevo == 1) {
                tipoDia = nuevoDiaLaborable.getTipodia().getDescripcion();
            } else if (tipoNuevo == 2) {
                tipoDia = duplicarDiaLaborable.getTipodia().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoDiaLaborable(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("DIAS")) {
            if (tipoNuevo == 1) {
                nuevoDiaLaborable.getTipodia().setDescripcion(tipoDia);
            } else if (tipoNuevo == 2) {
                duplicarDiaLaborable.getTipodia().setDescripcion(tipoDia);
            }
            for (int i = 0; i < lovTiposDias.size(); i++) {
                if (lovTiposDias.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoDiaLaborable.setTipodia(lovTiposDias.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoDiaLabTipoDia");
                } else if (tipoNuevo == 2) {
                    duplicarDiaLaborable.setTipodia(lovTiposDias.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarDiaLabTipoDia");
                }
                lovTiposDias.clear();
                getLovTiposDias();
            } else {
                context.update("form:TipoDiaDialogo");
                context.execute("TipoDiaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoDiaLabTipoDia");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarDiaLabTipoDia");
                }
            }
        }
    }

    public void actualizarTipoDia() {
        if (tipoActualizacion == 0) {
            if (tipoListaDias == 0) {
                listaDiasLaborables.get(indexDias).setTipodia(tipoDiaSeleccionado);
                if (!listDiasLaborablesCrear.contains(listaDiasLaborables.get(indexDias))) {
                    if (listDiasLaborablesModificar.isEmpty()) {
                        listDiasLaborablesModificar.add(listaDiasLaborables.get(indexDias));
                    } else if (!listDiasLaborablesModificar.contains(listaDiasLaborables.get(indexDias))) {
                        listDiasLaborablesModificar.add(listaDiasLaborables.get(indexDias));
                    }
                }
            } else {
                filtrarListaDiasLaborables.get(indexDias).setTipodia(tipoDiaSeleccionado);
                if (!listDiasLaborablesCrear.contains(filtrarListaDiasLaborables.get(indexDias))) {
                    if (listDiasLaborablesModificar.isEmpty()) {
                        listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indexDias));
                    } else if (!listDiasLaborablesModificar.contains(filtrarListaDiasLaborables.get(indexDias))) {
                        listDiasLaborablesModificar.add(filtrarListaDiasLaborables.get(indexDias));
                    }
                }
            }
            if (guardadoDias == true) {
                guardadoDias = false;
            }
            permitirIndexDias = true;
            cambiosPagina = false;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:ACEPTAR");
            context.update("form:datosDiasLaborables");
        } else if (tipoActualizacion == 1) {
            nuevoDiaLaborable.setTipodia(tipoDiaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoDiaLabTipoDia");
        } else if (tipoActualizacion == 2) {
            duplicarDiaLaborable.setTipodia(tipoDiaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarDiaLabTipoDia");
        }
        filtrarLovTiposDias = null;
        tipoDiaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TipoDiaDialogo");
        context.update("form:lovTipoDia");
        context.update("form:aceptarTD");
        context.execute("TipoDiaDialogo.hide()");
    }

    public void cancelarCambioTipoDia() {
        filtrarLovTiposDias = null;
        tipoDiaSeleccionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndexDias = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public String exportXML() {
        if (index >= 0) {
            nombreTabla = ":formExportarTC:datosTipoContratoExportar";
            nombreXML = "TiposContratos_XML";
        }
        if (indexDias >= 0) {
            nombreTabla = ":formExportarDL:datosDiasLaborablesExportar";
            nombreXML = "DiasLaborables_XML";
        }
        return nombreTabla;
    }

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (index >= 0) {
            exportPDF_TC();
        }
        if (indexDias >= 0) {
            exportPDF_DL();
        }
    }

    public void exportPDF_TC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTC:datosTipoContratoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "TiposContratos_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportPDF_DL() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarDL:datosDiasLaborablesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "DiasLaborables_PDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportXLS() throws IOException {
        if (index >= 0) {
            exportXLS_TC();
        }
        if (indexDias >= 0) {
            exportXLS_DL();
        }
    }

    public void exportXLS_TC() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarTC:datosTipoContratoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "TiposContratos_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS_DL() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarDL:datosDiasLaborablesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "DiasLaborables_XLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
        if (indexDias >= 0) {
            if (tipoListaDias == 0) {
                tipoListaDias = 1;
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        if (listaDiasLaborables == null || listaTiposContratos == null) {
        } else {
            if (index >= 0) {
                verificarRastroTipoContrato();
                index = -1;
            }
            if (indexDias >= 0) {
                verificarRastroDiaLaborable();
                indexDias = -1;
            }
        }
    }

    public void verificarRastroTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaTiposContratos != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "TIPOSCONTRATOS");
                backUpSecRegistro = secRegistro;
                backUp = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "TiposContratos";
                    msnConfirmarRastro = "La tabla TIPOSCONTRATOS tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("TIPOSCONTRATOS")) {
                nombreTablaRastro = "TiposContratos";
                msnConfirmarRastroHistorico = "La tabla TIPOSCONTRATOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroDiaLaborable() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listaDiasLaborables != null) {
            if (secRegistroDias != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroDias, "DIASLABORABLES");
                backUpSecRegistroDias = secRegistroDias;
                backUp = secRegistroDias;
                secRegistroDias = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "DiasLaborables";
                    msnConfirmarRastro = "La tabla DIASLABORABLES tiene rastros para el registro seleccionado, ¿desea continuar?";
                    context.update("form:msnConfirmarRastro");
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
            if (administrarRastros.verificarHistoricosTabla("DIASLABORABLES")) {
                nombreTablaRastro = "DiasLaborables";
                msnConfirmarRastroHistorico = "La tabla DIASLABORABLES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public boolean validarCodigoNuevoClonado() {
        boolean retorno = true;
        int conteo = 0;
        for (int i = 0; i < lovTiposContratos.size(); i++) {
            if (lovTiposContratos.get(i).getCodigo() == codigoTipoCClonar) {
                conteo++;
            }
        }
        if (conteo > 0) {
            retorno = false;
        }
        return retorno;
    }

    public void clonarTipoContrato() {
        if ((nombreTipoCColnar.isEmpty()) || (codigoTipoCClonar <= 0) || (tipoContratoAClonar.getSecuencia() == null)) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:errorClonadoTipoC");
            context.execute("errorClonadoTipoC.show()");
        } else {
            if (validarCodigoNuevoClonado() == true) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:continuarOperacionClonado");
                context.execute("continuarOperacionClonado.show()");
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:errorCodigoClonado");
                context.execute("errorCodigoClonado.show()");
            }
        }
    }

    public void dispararDialogoClonarTipoContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:TipoContratoDialogo");
        context.execute("TipoContratoDialogo.show()");
    }

    public void posicionTipoContratoClonar() {
        if (guardado == true) {
            auxCodigoClonar = tipoContratoAClonar.getCodigo();
            auxNombreClonar = tipoContratoAClonar.getNombre();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void seleccionarTipoContratoClonar() {
        tipoContratoAClonar = tipoContratoSeleccionado;
        lovTiposContratos.clear();
        getLovTiposContratos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:codigoTipoCClonarBase");
        context.update("form:nombreTipoCClonarBase");
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
        context.update("form:TipoContratoDialogo");
        context.update("form:lovTipoContrato");
        context.update("form:aceptarTC");
        context.execute("TipoContratoDialogo.hide()");
    }

    public void cancelarTipoContratoClonar() {
        tipoContratoSeleccionado = new TiposContratos();
        filtrarLovTiposContratos = null;
    }

    public void autoCompletarSeleccionarTipoContratoClonar(String valorConfirmar, int tipoAutoCompletar) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoAutoCompletar == 0) {
            short num = Short.parseShort(valorConfirmar);
            for (int i = 0; i < lovTiposContratos.size(); i++) {
                if (lovTiposContratos.get(i).getCodigo() == num) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoContratoAClonar = lovTiposContratos.get(indiceUnicoElemento);
                lovTiposContratos.clear();
                getLovTiposContratos();
            } else {
                tipoContratoAClonar.setCodigo(auxCodigoClonar);
                tipoContratoAClonar.setNombre(auxNombreClonar);
                context.update("form:codigoTipoCClonarBase");
                context.update("form:nombreTipoCClonarBase");
                context.update("form:TipoContratoDialogo");
                context.execute("TipoContratoDialogo.show()");
            }
        }
        if (tipoAutoCompletar == 1) {
            for (int i = 0; i < lovTiposContratos.size(); i++) {
                if (lovTiposContratos.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                tipoContratoAClonar = lovTiposContratos.get(indiceUnicoElemento);
                lovTiposContratos.clear();
                getLovTiposContratos();
            } else {
                tipoContratoAClonar.setCodigo(auxCodigoClonar);
                tipoContratoAClonar.setNombre(auxNombreClonar);
                context.update("form:nombreTipoCClonarBase");
                context.update("form:codigoTipoCClonarBase");
                context.update("form:TipoContratoDialogo");
                context.execute("TipoContratoDialogo.show()");
            }
        }
    }

    //GETTERS AND SETTERS
    public List<TiposContratos> getListaTiposContratos() {
        try {
            if (listaTiposContratos == null) {
                listaTiposContratos = new ArrayList<TiposContratos>();
                listaTiposContratos = administrarTipoContrato.listaTiposContratos();
                return listaTiposContratos;
            } else {
                return listaTiposContratos;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListaTiposContratos " + e.toString());
            return null;
        }
    }

    public void setListaTiposContratos(List<TiposContratos> setListaTiposContratos) {
        this.listaTiposContratos = setListaTiposContratos;
    }

    public List<TiposContratos> getFiltrarListaTiposContratos() {
        return filtrarListaTiposContratos;
    }

    public void setFiltrarListaTiposContratos(List<TiposContratos> setFiltrarListaTiposContratos) {
        this.filtrarListaTiposContratos = setFiltrarListaTiposContratos;
    }

    public TiposContratos getNuevoTipoContrato() {
        return nuevoTipoContrato;
    }

    public void setNuevoTipoContrato(TiposContratos setNuevoTipoContrato) {
        this.nuevoTipoContrato = setNuevoTipoContrato;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public TiposContratos getEditarTipoContrato() {
        return editarTipoContrato;
    }

    public void setEditarTipoContrato(TiposContratos setEditarTipoContrato) {
        this.editarTipoContrato = setEditarTipoContrato;
    }

    public TiposContratos getDuplicarTipoContrato() {
        return duplicarTipoContrato;
    }

    public void setDuplicarTipoContrato(TiposContratos setDuplicarTipoContrato) {
        this.duplicarTipoContrato = setDuplicarTipoContrato;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger BackUpSecRegistro) {
        this.backUpSecRegistro = BackUpSecRegistro;
    }

    public List<DiasLaborables> getListaDiasLaborables() {
        if (listaDiasLaborables == null) {
            listaDiasLaborables = new ArrayList<DiasLaborables>();
            if (index == -1) {
                index = indexAux;
            }
            if (tipoLista == 0) {
                listaDiasLaborables = administrarTipoContrato.listaDiasLaborablesParaTipoContrato(listaTiposContratos.get(index).getSecuencia());
            }
            if (tipoLista == 1) {
                listaDiasLaborables = administrarTipoContrato.listaDiasLaborablesParaTipoContrato(filtrarListaTiposContratos.get(index).getSecuencia());
            }
        }
        return listaDiasLaborables;
    }

    public void setListaDiasLaborables(List<DiasLaborables> setListaDiasLaborables) {
        this.listaDiasLaborables = setListaDiasLaborables;
    }

    public List<DiasLaborables> getFiltrarListaDiasLaborables() {
        return filtrarListaDiasLaborables;
    }

    public void setFiltrarListaDiasLaborables(List<DiasLaborables> setFiltrarListaDiasLaborables) {
        this.filtrarListaDiasLaborables = setFiltrarListaDiasLaborables;
    }

    public List<TiposContratos> getListTiposContratosModificar() {
        return listTiposContratosModificar;
    }

    public void setListTiposContratosModificar(List<TiposContratos> setListTiposContratosModificar) {
        this.listTiposContratosModificar = setListTiposContratosModificar;
    }

    public List<TiposContratos> getListTiposContratosCrear() {
        return listTiposContratosCrear;
    }

    public void setListTiposContratosCrear(List<TiposContratos> setListExtraRecargoCrear) {
        this.listTiposContratosCrear = setListExtraRecargoCrear;
    }

    public List<TiposContratos> getListTiposContratosBorrar() {
        return listTiposContratosBorrar;
    }

    public void setListTiposContratosBorrar(List<TiposContratos> setListTiposContratosBorrar) {
        this.listTiposContratosBorrar = setListTiposContratosBorrar;
    }

    public List<DiasLaborables> getListDiasLaborablesModificar() {
        return listDiasLaborablesModificar;
    }

    public void setListDiasLaborablesModificar(List<DiasLaborables> setListDiasLaborablesModificar) {
        this.listDiasLaborablesModificar = setListDiasLaborablesModificar;
    }

    public DiasLaborables getNuevoDiaLaborable() {
        return nuevoDiaLaborable;
    }

    public void setNuevoDiaLaborable(DiasLaborables setNuevoDiaLaborable) {
        this.nuevoDiaLaborable = setNuevoDiaLaborable;
    }

    public List<DiasLaborables> getListDiasLaborablesCrear() {
        return listDiasLaborablesCrear;
    }

    public void setListDiasLaborablesCrear(List<DiasLaborables> setListDiasLaborablesCrear) {
        this.listDiasLaborablesCrear = setListDiasLaborablesCrear;
    }

    public List<DiasLaborables> getListDiasLaborablesBorrar() {
        return listDiasLaborablesBorrar;
    }

    public void setListDiasLaborablesBorrar(List<DiasLaborables> setListDiasLaborablesBorrar) {
        this.listDiasLaborablesBorrar = setListDiasLaborablesBorrar;
    }

    public DiasLaborables getEditarDiaLaborable() {
        return editarDiaLaborable;
    }

    public void setEditarDiaLaborable(DiasLaborables setEditarDiaLaborable) {
        this.editarDiaLaborable = setEditarDiaLaborable;
    }

    public DiasLaborables getDuplicarDiaLaborable() {
        return duplicarDiaLaborable;
    }

    public void setDuplicarDiaLaborable(DiasLaborables setDuplicarDiaLaborable) {
        this.duplicarDiaLaborable = setDuplicarDiaLaborable;
    }

    public BigInteger getSecRegistroDias() {
        return secRegistroDias;
    }

    public void setSecRegistroDias(BigInteger setSecRegistroDER) {
        this.secRegistroDias = setSecRegistroDER;
    }

    public BigInteger getBackUpSecRegistroDias() {
        return backUpSecRegistroDias;
    }

    public void setBackUpSecRegistroDias(BigInteger setBackUpSecRegistroDER) {
        this.backUpSecRegistroDias = setBackUpSecRegistroDER;
    }

    public String getMsnConfirmarRastro() {
        return msnConfirmarRastro;
    }

    public void setMsnConfirmarRastro(String msnConfirmarRastro) {
        this.msnConfirmarRastro = msnConfirmarRastro;
    }

    public String getMsnConfirmarRastroHistorico() {
        return msnConfirmarRastroHistorico;
    }

    public void setMsnConfirmarRastroHistorico(String msnConfirmarRastroHistorico) {
        this.msnConfirmarRastroHistorico = msnConfirmarRastroHistorico;
    }

    public BigInteger getBackUp() {
        return backUp;
    }

    public void setBackUp(BigInteger backUp) {
        this.backUp = backUp;
    }

    public String getNombreTablaRastro() {
        return nombreTablaRastro;
    }

    public void setNombreTablaRastro(String nombreTablaRastro) {
        this.nombreTablaRastro = nombreTablaRastro;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }

    public List<TiposDias> getLovTiposDias() {
        if (lovTiposDias == null) {
            lovTiposDias = administrarTipoContrato.lovTiposDias();
        }
        return lovTiposDias;
    }

    public void setLovTiposDias(List<TiposDias> setLovTiposDias) {
        this.lovTiposDias = setLovTiposDias;
    }

    public List<TiposDias> getFiltrarLovTiposDias() {
        return filtrarLovTiposDias;
    }

    public void setFiltrarLovTiposDias(List<TiposDias> setFiltrarLovTiposDias) {
        this.filtrarLovTiposDias = setFiltrarLovTiposDias;
    }

    public TiposDias getTipoDiaSeleccionado() {
        return tipoDiaSeleccionado;
    }

    public void setTipoDiaSeleccionado(TiposDias setTipoDiaSeleccionado) {
        this.tipoDiaSeleccionado = setTipoDiaSeleccionado;
    }

    public boolean isCambiosPagina() {
        return cambiosPagina;
    }

    public void setCambiosPagina(boolean cambiosPagina) {
        this.cambiosPagina = cambiosPagina;
    }

    public String getAltoTablaTiposC() {
        return altoTablaTiposC;
    }

    public void setAltoTablaTiposC(String altoTablaTiposC) {
        this.altoTablaTiposC = altoTablaTiposC;
    }

    public String getAltoTablaDiasLab() {
        return altoTablaDiasLab;
    }

    public void setAltoTablaDiasLab(String altoTablaDiasLab) {
        this.altoTablaDiasLab = altoTablaDiasLab;
    }

    public short getCodigoTipoCClonar() {
        return codigoTipoCClonar;
    }

    public void setCodigoTipoCClonar(short codigoTipoCClonar) {
        this.codigoTipoCClonar = codigoTipoCClonar;
    }

    public String getNombreTipoCColnar() {
        return nombreTipoCColnar;
    }

    public void setNombreTipoCColnar(String nombreTipoCColnar) {
        this.nombreTipoCColnar = nombreTipoCColnar.toUpperCase();
    }

    public List<TiposContratos> getLovTiposContratos() {
        lovTiposContratos = administrarTipoContrato.listaTiposContratos();
        return lovTiposContratos;
    }

    public void setLovTiposContratos(List<TiposContratos> lovTiposContratos) {
        this.lovTiposContratos = lovTiposContratos;
    }

    public List<TiposContratos> getFiltrarLovTiposContratos() {
        return filtrarLovTiposContratos;
    }

    public void setFiltrarLovTiposContratos(List<TiposContratos> filtrarLovTiposContratos) {
        this.filtrarLovTiposContratos = filtrarLovTiposContratos;
    }

    public TiposContratos getTipoContratoSeleccionado() {
        return tipoContratoSeleccionado;
    }

    public void setTipoContratoSeleccionado(TiposContratos tipoContratoSeleccionado) {
        this.tipoContratoSeleccionado = tipoContratoSeleccionado;
    }

    public TiposContratos getTipoContratoAClonar() {
        return tipoContratoAClonar;
    }

    public void setTipoContratoAClonar(TiposContratos tipoContratoAClonar) {
        this.tipoContratoAClonar = tipoContratoAClonar;
    }

}

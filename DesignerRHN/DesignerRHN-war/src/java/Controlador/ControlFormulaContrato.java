package Controlador;

import Entidades.Contratos;
import Entidades.Formulas;
import Entidades.Formulascontratos;
import Entidades.Periodicidades;
import Entidades.Terceros;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarFormulaContratoInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
 * @author PROYECTO01
 */
@ManagedBean
@SessionScoped
public class ControlFormulaContrato implements Serializable {

    @EJB
    AdministrarFormulaContratoInterface administrarFormulaContrato;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    
    //////////////Formulas//////////////////
    private Formulas formulaActual;
    ///////////Formulascontratos////////////
    private List<Formulascontratos> listFormulasContratos;
    private List<Formulascontratos> filtrarListFormulasContratos;
    ///////////Formulascontratos/////////////
    private int banderaFormulaContrato;
    private int indexFormulaContrato, indexAuxFormulaContrato;
    private List<Formulascontratos> listFormulasContratosModificar;
    private Formulascontratos nuevaFormulaContrato;
    private List<Formulascontratos> listFormulasContratosCrear;
    private List<Formulascontratos> listFormulasContratosBorrar;
    private Formulascontratos editarFormulaContrato;
    private int cualCeldaFormulaContrato, tipoListaFormulaContrato;
    private Formulascontratos duplicarFormulaContrato;
    private boolean cambiosFormulaContrato;
    private BigInteger secRegistroFormulaContrato;
    private BigInteger backUpSecRegistroFormulaContrato;
    private String legislacion, periodicidad, tercero;
    private Date fechaIni;
    private Column formulaFechaInicial, formulaFechaFinal, formulaLegislacion, formulaPeriodicidad, formulaTercero;
    private boolean permitirIndexFormulaContrato;
    ////////////Listas Valores Formulascontratos/////////////
    private List<Contratos> listContratos;
    private List<Contratos> filtrarListContratos;
    private Contratos contratoSeleccionado;
    private List<Terceros> listTerceros;
    private List<Terceros> filtrarListTerceros;
    private Terceros terceroSeleccionada;
    private List<Periodicidades> listPeriodicidades;
    private List<Periodicidades> filtrarListPeriodicidades;
    private Periodicidades periodicidadSeleccionado;

    //////////////Otros////////////////Otros////////////////////
    private boolean aceptar;
    private boolean guardado;
    private BigInteger l;
    private int k;
    private String nombreXML, nombreExportar;
    private String nombreTabla;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int tipoActualizacion;
    private Date fechaParametro;

    public ControlFormulaContrato() {
        formulaActual = new Formulas();
        listFormulasContratos = null;
        fechaParametro = new Date(1, 1, 0);
        permitirIndexFormulaContrato = true;

        listContratos = null;
        listTerceros = null;
        listPeriodicidades = null;

        contratoSeleccionado = new Contratos();
        terceroSeleccionada = new Terceros();
        periodicidadSeleccionado = new Periodicidades();

        nombreExportar = "";
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";

        secRegistroFormulaContrato = null;
        backUpSecRegistroFormulaContrato = null;
        aceptar = true;
        k = 0;

        listFormulasContratosBorrar = new ArrayList<Formulascontratos>();
        listFormulasContratosCrear = new ArrayList<Formulascontratos>();
        listFormulasContratosModificar = new ArrayList<Formulascontratos>();
        editarFormulaContrato = new Formulascontratos();
        cualCeldaFormulaContrato = -1;
        tipoListaFormulaContrato = 0;
        guardado = true;
        nuevaFormulaContrato = new Formulascontratos();
        nuevaFormulaContrato.setPeriodicidad(new Periodicidades());
        nuevaFormulaContrato.setTercero(new Terceros());
        nuevaFormulaContrato.setContrato(new Contratos());
        indexFormulaContrato = -1;
        banderaFormulaContrato = 0;
        nombreTabla = ":formExportarFormula:datosFormulaContratosExportar";
        nombreXML = "FormulaContrato_XML";
        duplicarFormulaContrato = new Formulascontratos();
        cambiosFormulaContrato = false;
    }
    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarFormulaContrato.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirFormula(BigInteger secuencia) {
        formulaActual = administrarFormulaContrato.actualFormula(secuencia);
        listFormulasContratos = null;
    }

    public void modificarFormulaContrato(int indice) {
        if (tipoListaFormulaContrato == 0) {
            if (!listFormulasContratosCrear.contains(listFormulasContratos.get(indice))) {
                if (listFormulasContratosModificar.isEmpty()) {
                    listFormulasContratosModificar.add(listFormulasContratos.get(indice));
                } else if (!listFormulasContratosModificar.contains(listFormulasContratos.get(indice))) {
                    listFormulasContratosModificar.add(listFormulasContratos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexFormulaContrato = -1;
            secRegistroFormulaContrato = null;
        } else {
            if (!listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indice))) {
                if (listFormulasContratosModificar.isEmpty()) {
                    listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indice));
                } else if (!listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indice))) {
                    listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            indexFormulaContrato = -1;
            secRegistroFormulaContrato = null;
        }
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaContrato");
        cambiosFormulaContrato = true;
    }

    public void modificarFormulaContrato(int indice, String confirmarCambio, String valorConfirmar) {
        indexFormulaContrato = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("LEGISLACION")) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indice).getContrato().setDescripcion(legislacion);
            } else {
                filtrarListFormulasContratos.get(indice).getContrato().setDescripcion(legislacion);
            }
            for (int i = 0; i < listContratos.size(); i++) {
                if (listContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaFormulaContrato == 0) {
                    listFormulasContratos.get(indice).setContrato(listContratos.get(indiceUnicoElemento));
                } else {
                    filtrarListFormulasContratos.get(indice).setContrato(listContratos.get(indiceUnicoElemento));
                }
                listContratos.clear();
                getListContratos();
            } else {
                permitirIndexFormulaContrato = false;
                context.update("form:ContratoDialogo");
                context.execute("ContratoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("PERIODICIDAD")) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indice).getPeriodicidad().setNombre(periodicidad);
            } else {
                filtrarListFormulasContratos.get(indice).getPeriodicidad().setNombre(periodicidad);
            }
            for (int i = 0; i < listPeriodicidades.size(); i++) {
                if (listPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaFormulaContrato == 0) {
                    listFormulasContratos.get(indice).setPeriodicidad(listPeriodicidades.get(indiceUnicoElemento));
                } else {
                    filtrarListFormulasContratos.get(indice).setPeriodicidad(listPeriodicidades.get(indiceUnicoElemento));
                }
                listPeriodicidades.clear();
                getListPeriodicidades();
            } else {
                permitirIndexFormulaContrato = false;
                context.update("form:PeriodicidadDialogo");
                context.execute("PeriodicidadDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indice).getTercero().setNombre(tercero);
            } else {
                filtrarListFormulasContratos.get(indice).getTercero().setNombre(tercero);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoListaFormulaContrato == 0) {
                    listFormulasContratos.get(indice).setTercero(listTerceros.get(indiceUnicoElemento));
                } else {
                    filtrarListFormulasContratos.get(indice).setTercero(listTerceros.get(indiceUnicoElemento));
                }
                listTerceros.clear();
                getListTerceros();
            } else {
                permitirIndexFormulaContrato = false;
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }

        }
        if (coincidencias == 1) {
            if (tipoListaFormulaContrato == 0) {
                if (!listFormulasContratosCrear.contains(listFormulasContratos.get(indice))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indice));
                    } else if (!listFormulasContratosModificar.contains(listFormulasContratos.get(indice))) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexFormulaContrato = -1;
                secRegistroFormulaContrato = null;
            } else {
                if (!listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indice))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indice));
                    } else if (!listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indice))) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexFormulaContrato = -1;
                secRegistroFormulaContrato = null;
            }
            cambiosFormulaContrato = true;
        }
        context.update("form:datosFormulaContrato");
    }

    ///////////////////////////////////////////////////////////////////////////
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("LEGISLACION")) {
            if (tipoNuevo == 1) {
                legislacion = nuevaFormulaContrato.getContrato().getDescripcion();
            } else if (tipoNuevo == 2) {
                legislacion = duplicarFormulaContrato.getContrato().getDescripcion();
            }
        } else if (Campo.equals("PERIODICIDAD")) {
            if (tipoNuevo == 1) {
                periodicidad = nuevaFormulaContrato.getPeriodicidad().getNombre();
            } else if (tipoNuevo == 2) {
                periodicidad = duplicarFormulaContrato.getPeriodicidad().getNombre();
            }
        } else if (Campo.equals("TERCERO")) {
            if (tipoNuevo == 1) {
                tercero = nuevaFormulaContrato.getTercero().getNombre();
            } else if (tipoNuevo == 2) {
                tercero = duplicarFormulaContrato.getTercero().getNombre();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoFormulaContrato(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("LEGISLACION")) {
            if (tipoNuevo == 1) {
                nuevaFormulaContrato.getContrato().setDescripcion(legislacion);
            } else if (tipoNuevo == 2) {
                duplicarFormulaContrato.getContrato().setDescripcion(legislacion);
            }
            for (int i = 0; i < listContratos.size(); i++) {
                if (listContratos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulaContrato.setContrato(listContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaLegislacion");
                } else if (tipoNuevo == 2) {
                    duplicarFormulaContrato.setContrato(listContratos.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarLegislacion");
                }
                listContratos.clear();
                getListContratos();
            } else {
                context.update("form:ContratoDialogo");
                context.execute("ContratoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaLegislacion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarLegislacion");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("PERIODICIDAD")) {
            if (tipoNuevo == 1) {
                nuevaFormulaContrato.getPeriodicidad().setNombre(periodicidad);
            } else if (tipoNuevo == 2) {
                duplicarFormulaContrato.getPeriodicidad().setNombre(periodicidad);
            }
            for (int i = 0; i < listPeriodicidades.size(); i++) {
                if (listPeriodicidades.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulaContrato.setPeriodicidad(listPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaPeriodicidad");
                } else if (tipoNuevo == 2) {
                    duplicarFormulaContrato.setPeriodicidad(listPeriodicidades.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarPeriodicidad");
                }
                listPeriodicidades.clear();
                getListPeriodicidades();
            } else {
                context.update("form:PeriodicidadDialogo");
                context.execute("PeriodicidadDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaPeriodicidad");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarPeriodicidad");
                }
            }
        } else if (confirmarCambio.equalsIgnoreCase("TERCERO")) {
            if (tipoNuevo == 1) {
                nuevaFormulaContrato.getTercero().setNombre(tercero);
            } else if (tipoNuevo == 2) {
                duplicarFormulaContrato.getTercero().setNombre(tercero);
            }
            for (int i = 0; i < listTerceros.size(); i++) {
                if (listTerceros.get(i).getNombre().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaFormulaContrato.setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaTercero");
                } else if (tipoNuevo == 2) {
                    duplicarFormulaContrato.setTercero(listTerceros.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTercero");
                }
                listTerceros.clear();
                getListTerceros();
            } else {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaTercero");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTercero");
                }
            }
        }
    }

    public void cambiarIndiceFormulaContrato(int indice, int celda) {
        if (permitirIndexFormulaContrato == true) {
            cualCeldaFormulaContrato = celda;
            indexFormulaContrato = indice;
            secRegistroFormulaContrato = listFormulasContratos.get(indexFormulaContrato).getSecuencia();
            ///////// Captura Objetos Para Campos NotNull ///////////
            fechaIni = listFormulasContratos.get(indexFormulaContrato).getFechainicial();
            legislacion = listFormulasContratos.get(indexFormulaContrato).getContrato().getDescripcion();
            tercero = listFormulasContratos.get(indexFormulaContrato).getTercero().getNombre();
            periodicidad = listFormulasContratos.get(indexFormulaContrato).getPeriodicidad().getNombre();
        }
    }

    public boolean validarFechasRegistroFormulas(int i) {
        boolean retorno = false;
        if (i == 0) {
            Formulascontratos auxiliar = listFormulasContratos.get(i);
            if (auxiliar.getFechainicial() != null && auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                    retorno = true;
                }
            }
            if (auxiliar.getFechainicial() != null && auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                }
            }
        }
        if (i == 1) {
            if (nuevaFormulaContrato.getFechainicial() != null && nuevaFormulaContrato.getFechafinal() != null) {
                if (nuevaFormulaContrato.getFechainicial().after(fechaParametro) && (nuevaFormulaContrato.getFechainicial().before(nuevaFormulaContrato.getFechafinal()))) {
                    retorno = true;
                }
            }
            if (nuevaFormulaContrato.getFechainicial() != null && nuevaFormulaContrato.getFechafinal() == null) {
                if (nuevaFormulaContrato.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                }
            }
        }
        if (i == 2) {

            if (duplicarFormulaContrato.getFechainicial() != null && duplicarFormulaContrato.getFechafinal() != null) {
                if (duplicarFormulaContrato.getFechainicial().after(fechaParametro) && (duplicarFormulaContrato.getFechainicial().before(duplicarFormulaContrato.getFechafinal()))) {
                    retorno = true;
                }
            }
            if (duplicarFormulaContrato.getFechainicial() != null && duplicarFormulaContrato.getFechafinal() == null) {
                if (duplicarFormulaContrato.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public void modificacionesFechaFormula(int i, int c) {
        Formulascontratos auxiliar = null;
        if (tipoListaFormulaContrato == 0) {
            auxiliar = listFormulasContratos.get(i);
        }
        if (tipoListaFormulaContrato == 1) {
            auxiliar = filtrarListFormulasContratos.get(i);
        }
        if ((auxiliar.getFechainicial() != null)) {
            boolean validacion = validarFechasRegistroFormulas(0);
            if (validacion == true) {
                cambiarIndiceFormulaContrato(i, c);
                modificarFormulaContrato(i);
                indexAuxFormulaContrato = i;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosFormulaContrato");
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaFormulaContrato == 0) {
                    listFormulasContratos.get(indexFormulaContrato).setFechainicial(fechaIni);
                }
                if (tipoListaFormulaContrato == 1) {
                    filtrarListFormulasContratos.get(indexFormulaContrato).setFechainicial(fechaIni);
                }
                context.update("form:datosFormulaContrato");
                context.execute("errorFechasFC.show()");
                indexFormulaContrato = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indexFormulaContrato).setFechainicial(fechaIni);
            }
            if (tipoListaFormulaContrato == 1) {
                filtrarListFormulasContratos.get(indexFormulaContrato).setFechainicial(fechaIni);
            }
            context.update("form:datosFormulaContrato");
            context.execute("errorRegNuevo.show()");
            indexFormulaContrato = -1;
        }
    }
    //GUARDAR
    /**
     */
    public void guardadoGeneral() {
        if (cambiosFormulaContrato == true) {
            guardarCambiosFormula();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    public void guardarCambiosFormula() {

        if (!listFormulasContratosBorrar.isEmpty()) {
            administrarFormulaContrato.borrarFormulasContratos(listFormulasContratosBorrar);
            listFormulasContratosBorrar.clear();
        }
        if (!listFormulasContratosCrear.isEmpty()) {
            administrarFormulaContrato.crearFormulasContratos(listFormulasContratosCrear);
            listFormulasContratosCrear.clear();
        }
        if (!listFormulasContratosModificar.isEmpty()) {
            administrarFormulaContrato.editarFormulasContratos(listFormulasContratosModificar);
            listFormulasContratosModificar.clear();
        }
        listFormulasContratos = null;
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaContrato");
        context.update("form:growl");
        k = 0;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        cambiosFormulaContrato = false;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (banderaFormulaContrato == 1) {
            formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
            formulaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
            formulaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
            formulaLegislacion.setFilterStyle("display: none; visibility: hidden;");
            formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
            formulaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
            formulaTercero.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaContrato");
            banderaFormulaContrato = 0;
            filtrarListFormulasContratos = null;
            tipoListaFormulaContrato = 0;
        }

        listFormulasContratosBorrar.clear();
        listFormulasContratosCrear.clear();
        listFormulasContratosModificar.clear();
        indexFormulaContrato = -1;
        indexAuxFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        k = 0;
        listFormulasContratos = null;
        guardado = true;
        cambiosFormulaContrato = false;
        permitirIndexFormulaContrato = true;
        getListFormulasContratos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaContrato");
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (indexFormulaContrato >= 0) {
            if (cualCeldaFormulaContrato == 2) {
                context.update("form:ContratoDialogo");
                context.execute("ContratoDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaFormulaContrato == 3) {
                context.update("form:PeriodicidadDialogo");
                context.execute("PeriodicidadDialogo.show()");
                tipoActualizacion = 0;
            }
            if (cualCeldaFormulaContrato == 4) {
                context.update("form:TerceroDialogo");
                context.execute("TerceroDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:ContratoDialogo");
            context.execute("ContratoDialogo.show()");
        } else if (dlg == 1) {
            context.update("form:PeriodicidadDialogo");
            context.execute("PeriodicidadDialogo.show()");
        } else if (dlg == 2) {
            context.update("form:TerceroDialogo");
            context.execute("TerceroDialogo.show()");
        }
    }

    public void editarCelda() {
        if (indexFormulaContrato >= 0) {
            if (tipoListaFormulaContrato == 0) {
                editarFormulaContrato = listFormulasContratos.get(indexFormulaContrato);
            }
            if (tipoListaFormulaContrato == 1) {
                editarFormulaContrato = filtrarListFormulasContratos.get(indexFormulaContrato);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaFormulaContrato == 0) {
                context.update("formularioDialogos:editarFechaInicialFD");
                context.execute("editarFechaInicialFD.show()");
                cualCeldaFormulaContrato = -1;
            } else if (cualCeldaFormulaContrato == 1) {
                context.update("formularioDialogos:editarFechaFinalFD");
                context.execute("editarFechaFinalFD.show()");
                cualCeldaFormulaContrato = -1;
            } else if (cualCeldaFormulaContrato == 2) {
                context.update("formularioDialogos:editarContratoFD");
                context.execute("editarContratoFD.show()");
                cualCeldaFormulaContrato = -1;
            } else if (cualCeldaFormulaContrato == 3) {
                context.update("formularioDialogos:editarPerdiodicidadFD");
                context.execute("editarPerdiodicidadFD.show()");
                cualCeldaFormulaContrato = -1;
            } else if (cualCeldaFormulaContrato == 4) {
                context.update("formularioDialogos:editarTerceroFD");
                context.execute("editarTerceroFD.show()");
                cualCeldaFormulaContrato = -1;
            }
            indexFormulaContrato = -1;
            secRegistroFormulaContrato = null;
        }
    }

    public void ingresoNuevoRegistro() {
        validarIngresoNuevaFormula();
    }

    public void validarIngresoNuevaFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoFormula();
        context.update("form:nuevaF");
        context.update("formularioDialogos:NuevoRegistroFormula");
        context.execute("NuevoRegistroFormula.show()");

    }

    public void validarDuplicadoRegistro() {
        if (indexFormulaContrato >= 0) {
            duplicarFormulaM();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void validarBorradoRegistro() {
        if (indexFormulaContrato >= 0) {
            borrarFormula();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public boolean validarNuevosDatosFormula(int i) {
        boolean retorno = false;
        if (i == 1) {
            if ((nuevaFormulaContrato.getContrato().getSecuencia() != null)
                    && (nuevaFormulaContrato.getPeriodicidad().getSecuencia() != null)
                    && (nuevaFormulaContrato.getFechainicial() != null)) {
                return true;
            }
        }
        if (i == 2) {
            if ((duplicarFormulaContrato.getContrato().getSecuencia() != null)
                    && (duplicarFormulaContrato.getPeriodicidad().getSecuencia() != null)
                    && (duplicarFormulaContrato.getFechainicial() != null)) {
                return true;
            }
        }
        return retorno;
    }

    public void agregarNuevoFormula() {
        boolean resp = validarNuevosDatosFormula(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulas(1);
            if (validacion == true) {
                if (banderaFormulaContrato == 1) {
                    formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
                    formulaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
                    formulaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
                    formulaLegislacion.setFilterStyle("display: none; visibility: hidden;");
                    formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
                    formulaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
                    formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
                    formulaTercero.setFilterStyle("display: none; visibility: hidden;");

                    RequestContext.getCurrentInstance().update("form:datosFormulaContrato");
                    banderaFormulaContrato = 0;
                    filtrarListFormulasContratos = null;
                    tipoListaFormulaContrato = 0;
                }
                k++;

                BigInteger var = BigInteger.valueOf(k);
                nuevaFormulaContrato.setSecuencia(var);
                nuevaFormulaContrato.setFormula(formulaActual);
                listFormulasContratosCrear.add(nuevaFormulaContrato);
                listFormulasContratos.add(nuevaFormulaContrato);
                ////------////
                nuevaFormulaContrato = new Formulascontratos();
                nuevaFormulaContrato.setPeriodicidad(new Periodicidades());
                nuevaFormulaContrato.setTercero(new Terceros());
                nuevaFormulaContrato.setContrato(new Contratos());
                ////-----////
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("NuevoRegistroFormula.hide()");
                context.update("form:datosFormulaContrato");
                context.update("formularioDialogos:NuevoRegistroFormula");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                cambiosFormulaContrato = true;
                indexFormulaContrato = -1;
                secRegistroFormulaContrato = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasFC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoFormula() {
        nuevaFormulaContrato = new Formulascontratos();
        nuevaFormulaContrato.setPeriodicidad(new Periodicidades());
        nuevaFormulaContrato.setTercero(new Terceros());
        nuevaFormulaContrato.setContrato(new Contratos());
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
    }

    public void duplicarFormulaM() {

        if (indexFormulaContrato >= 0) {
            duplicarFormulaContrato = new Formulascontratos();
            if (tipoListaFormulaContrato == 0) {
                duplicarFormulaContrato.setContrato(listFormulasContratos.get(indexFormulaContrato).getContrato());
                duplicarFormulaContrato.setFechafinal(listFormulasContratos.get(indexFormulaContrato).getFechafinal());
                duplicarFormulaContrato.setFechainicial(listFormulasContratos.get(indexFormulaContrato).getFechainicial());
                duplicarFormulaContrato.setPeriodicidad(listFormulasContratos.get(indexFormulaContrato).getPeriodicidad());
                duplicarFormulaContrato.setTercero(listFormulasContratos.get(indexFormulaContrato).getTercero());
            }
            if (tipoListaFormulaContrato == 1) {
                duplicarFormulaContrato.setContrato(filtrarListFormulasContratos.get(indexFormulaContrato).getContrato());
                duplicarFormulaContrato.setFechafinal(filtrarListFormulasContratos.get(indexFormulaContrato).getFechafinal());
                duplicarFormulaContrato.setFechainicial(filtrarListFormulasContratos.get(indexFormulaContrato).getFechainicial());
                duplicarFormulaContrato.setPeriodicidad(filtrarListFormulasContratos.get(indexFormulaContrato).getPeriodicidad());
                duplicarFormulaContrato.setTercero(filtrarListFormulasContratos.get(indexFormulaContrato).getTercero());
            }
            if (duplicarFormulaContrato.getTercero() == null) {
                duplicarFormulaContrato.setTercero(new Terceros());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroFormula");
            context.execute("DuplicarRegistroFormula.show()");

        }
    }

    public void confirmarDuplicarFormula() {
        boolean resp = validarNuevosDatosFormula(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroFormulas(2);
            if (validacion == true) {

                if (banderaFormulaContrato == 1) {
                    formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
                    formulaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
                    formulaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
                    formulaLegislacion.setFilterStyle("display: none; visibility: hidden;");
                    formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
                    formulaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
                    formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
                    formulaTercero.setFilterStyle("display: none; visibility: hidden;");
                    banderaFormulaContrato = 0;
                    filtrarListFormulasContratos = null;
                    tipoListaFormulaContrato = 0;
                }
                k++;
                BigInteger var = BigInteger.valueOf(k);

                duplicarFormulaContrato.setSecuencia(var);
                duplicarFormulaContrato.setFormula(formulaActual);
                listFormulasContratosCrear.add(duplicarFormulaContrato);
                listFormulasContratos.add(duplicarFormulaContrato);
                duplicarFormulaContrato = new Formulascontratos();

                duplicarFormulaContrato = new Formulascontratos();
                duplicarFormulaContrato.setPeriodicidad(new Periodicidades());
                duplicarFormulaContrato.setTercero(new Terceros());
                duplicarFormulaContrato.setContrato(new Contratos());

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosFormulaContrato");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("DuplicarRegistroFormula.hide()");
                cambiosFormulaContrato = true;
                indexFormulaContrato = -1;
                secRegistroFormulaContrato = null;

            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasFC.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarFormula() {
        duplicarFormulaContrato = new Formulascontratos();
        duplicarFormulaContrato.setPeriodicidad(new Periodicidades());
        duplicarFormulaContrato.setTercero(new Terceros());
        duplicarFormulaContrato.setContrato(new Contratos());
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
    }

    ///////////////////////////////////////////////////////////////
    public void borrarFormula() {

        if (indexFormulaContrato >= 0) {
            if (tipoListaFormulaContrato == 0) {
                if (!listFormulasContratosModificar.isEmpty() && listFormulasContratosModificar.contains(listFormulasContratos.get(indexFormulaContrato))) {
                    int modIndex = listFormulasContratosModificar.indexOf(listFormulasContratos.get(indexFormulaContrato));
                    listFormulasContratosModificar.remove(modIndex);
                    listFormulasContratosBorrar.add(listFormulasContratos.get(indexFormulaContrato));
                } else if (!listFormulasContratosCrear.isEmpty() && listFormulasContratosCrear.contains(listFormulasContratos.get(indexFormulaContrato))) {
                    int crearIndex = listFormulasContratosCrear.indexOf(listFormulasContratos.get(indexFormulaContrato));
                    listFormulasContratosCrear.remove(crearIndex);
                } else {
                    listFormulasContratosBorrar.add(listFormulasContratos.get(indexFormulaContrato));
                }
                listFormulasContratos.remove(indexFormulaContrato);
            }
            if (tipoListaFormulaContrato == 1) {
                if (!listFormulasContratosModificar.isEmpty() && listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                    int modIndex = listFormulasContratosModificar.indexOf(filtrarListFormulasContratos.get(indexFormulaContrato));
                    listFormulasContratosModificar.remove(modIndex);
                    listFormulasContratosBorrar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                } else if (!listFormulasContratosCrear.isEmpty() && listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                    int crearIndex = listFormulasContratosCrear.indexOf(filtrarListFormulasContratos.get(indexFormulaContrato));
                    listFormulasContratosCrear.remove(crearIndex);
                } else {
                    listFormulasContratosBorrar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                }
                int VLIndex = listFormulasContratos.indexOf(filtrarListFormulasContratos.get(indexFormulaContrato));
                listFormulasContratos.remove(VLIndex);
                filtrarListFormulasContratos.remove(indexFormulaContrato);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaContrato");
            indexFormulaContrato = -1;
            secRegistroFormulaContrato = null;
            cambiosFormulaContrato = true;
            if (guardado == true) {
                guardado = false;
            }
        }
    }

    //CTRL + F11 ACTIVAR/DESACTIVAR
    /**
     * Metodo que activa el filtrado por medio de la opcion en el toolbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        filtradoFormula();
    }

    /**
     */
    public void filtradoFormula() {
        if (banderaFormulaContrato == 0) {
            formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
            formulaFechaInicial.setFilterStyle("width: 60px");
            formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
            formulaFechaFinal.setFilterStyle("width: 60px");
            formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
            formulaLegislacion.setFilterStyle("width: 80px");
            formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
            formulaPeriodicidad.setFilterStyle("width: 80px");
            formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
            formulaTercero.setFilterStyle("width: 80px");

            RequestContext.getCurrentInstance().update("form:datosFormulaContrato");
            banderaFormulaContrato = 1;
        } else if (banderaFormulaContrato == 1) {
            formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
            formulaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
            formulaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
            formulaLegislacion.setFilterStyle("display: none; visibility: hidden;");
            formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
            formulaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
            formulaTercero.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosFormulaContrato");
            banderaFormulaContrato = 0;
            filtrarListFormulasContratos = null;
            tipoListaFormulaContrato = 0;
        }
    }
    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaFormulaContrato == 1) {
            formulaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaInicial");
            formulaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            formulaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaFechaFinal");
            formulaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            formulaLegislacion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaLegislacion");
            formulaLegislacion.setFilterStyle("display: none; visibility: hidden;");
            formulaPeriodicidad = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaPeriodicidad");
            formulaPeriodicidad.setFilterStyle("display: none; visibility: hidden;");
            formulaTercero = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaContrato:formulaTercero");
            formulaTercero.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosFormulaContrato");
            banderaFormulaContrato = 0;
            filtrarListFormulasContratos = null;
            tipoListaFormulaContrato = 0;
        }

        listFormulasContratosBorrar.clear();
        listFormulasContratosCrear.clear();
        listFormulasContratosModificar.clear();
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        formulaActual = null;
        k = 0;
        indexAuxFormulaContrato = -1;
        listFormulasContratos = null;
        guardado = true;
        cambiosFormulaContrato = false;
        nuevaFormulaContrato = new Formulascontratos();
        duplicarFormulaContrato = new Formulascontratos();
        listPeriodicidades = null;
        listTerceros = null;
        listContratos = null;
    }

    public void actualizarContrato() {
        if (tipoActualizacion == 0) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indexFormulaContrato).setContrato(contratoSeleccionado);
                if (!listFormulasContratosCrear.contains(listFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(listFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    }
                }
            } else {
                filtrarListFormulasContratos.get(indexFormulaContrato).setContrato(contratoSeleccionado);
                if (!listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexFormulaContrato = true;
            cambiosFormulaContrato = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarLegislacionFC");
        } else if (tipoActualizacion == 1) {
            nuevaFormulaContrato.setContrato(contratoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaLegislacion");
        } else if (tipoActualizacion == 2) {
            duplicarFormulaContrato.setContrato(contratoSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarLegislacion");
        }
        filtrarListContratos = null;
        contratoSeleccionado = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioContrato() {
        filtrarListContratos = null;
        contratoSeleccionado = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
        permitirIndexFormulaContrato = true;
    }

    public void actualizarTercero() {
        if (tipoActualizacion == 0) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indexFormulaContrato).setTercero(terceroSeleccionada);
                if (!listFormulasContratosCrear.contains(listFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(listFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    }
                }
            } else {
                filtrarListFormulasContratos.get(indexFormulaContrato).setTercero(terceroSeleccionada);
                if (!listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexFormulaContrato = true;
            cambiosFormulaContrato = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarTercero");
        } else if (tipoActualizacion == 1) {
            nuevaFormulaContrato.setTercero(terceroSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaTercero");
        } else if (tipoActualizacion == 2) {
            duplicarFormulaContrato.setTercero(terceroSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarTercero");
        }
        filtrarListTerceros = null;
        terceroSeleccionada = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioTercero() {
        filtrarListTerceros = null;
        terceroSeleccionada = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
        permitirIndexFormulaContrato = true;
    }

    public void actualizarPeriodicidad() {
        if (tipoActualizacion == 0) {
            if (tipoListaFormulaContrato == 0) {
                listFormulasContratos.get(indexFormulaContrato).setPeriodicidad(periodicidadSeleccionado);
                if (!listFormulasContratosCrear.contains(listFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(listFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(listFormulasContratos.get(indexFormulaContrato));
                    }
                }
            } else {
                filtrarListFormulasContratos.get(indexFormulaContrato).setPeriodicidad(periodicidadSeleccionado);
                if (!listFormulasContratosCrear.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                    if (listFormulasContratosModificar.isEmpty()) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    } else if (!listFormulasContratosModificar.contains(filtrarListFormulasContratos.get(indexFormulaContrato))) {
                        listFormulasContratosModificar.add(filtrarListFormulasContratos.get(indexFormulaContrato));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndexFormulaContrato = true;
            cambiosFormulaContrato = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:editarPeriodicidad");
        } else if (tipoActualizacion == 1) {
            nuevaFormulaContrato.setPeriodicidad(periodicidadSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaPeriodicidad");
        } else if (tipoActualizacion == 2) {
            duplicarFormulaContrato.setPeriodicidad(periodicidadSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarPeriodicidad");
        }
        filtrarListPeriodicidades = null;
        periodicidadSeleccionado = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioPeriodicidad() {
        filtrarListPeriodicidades = null;
        periodicidadSeleccionado = null;
        aceptar = true;
        indexFormulaContrato = -1;
        secRegistroFormulaContrato = null;
        tipoActualizacion = -1;
        permitirIndexFormulaContrato = true;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        nombreTabla = ":formExportarFormula:datosFormulaContratosExportar";
        nombreXML = "FormulaContrato_XML";
        return nombreTabla;
    }

    /**
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        nombreTabla = ":formExportarFormula:datosFormulaContratosExportar";
        nombreExportar = "FormulaContrato_PDF";
        exportPDF_Tabla();
        indexFormulaContrato = -1;
        indexAuxFormulaContrato = -1;
        secRegistroFormulaContrato = null;

    }

    /**
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_Tabla() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(nombreTabla);
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, nombreExportar, false, false, "UTF-8", null, null);
        context.responseComplete();

    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        nombreTabla = ":formExportarFormula:datosFormulaContratosExportar";
        nombreExportar = "FormulaContrato_XLS";
        exportXLS_Tabla();
        indexFormulaContrato = -1;
        indexAuxFormulaContrato = -1;
        secRegistroFormulaContrato = null;
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_Tabla() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(nombreTabla);
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, nombreExportar, false, false, "UTF-8", null, null);
        context.responseComplete();
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoListaFormulaContrato == 0) {
            tipoListaFormulaContrato = 1;
        }

    }

    public void verificarRastroTabla() {
        verificarRastroFormulaContrato();
        indexFormulaContrato = -1;
        indexAuxFormulaContrato = -1;

    }

    public void verificarRastroFormulaContrato() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listFormulasContratos != null) {
            if (secRegistroFormulaContrato != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroFormulaContrato, "FORMULASCONTRATOS");
                backUpSecRegistroFormulaContrato = secRegistroFormulaContrato;
                backUp = secRegistroFormulaContrato;
                secRegistroFormulaContrato = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Formulascontratos";
                    msnConfirmarRastro = "La tabla FORMULASCONTRATOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("FORMULASCONTRATOS")) {
                nombreTablaRastro = "Formulascontratos";
                msnConfirmarRastroHistorico = "La tabla FORMULASCONTRATOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexFormulaContrato = -1;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //GET - SET 
    public List<Formulascontratos> getListFormulasContratos() {
        try {
            if (listFormulasContratos == null) {
                listFormulasContratos = new ArrayList<Formulascontratos>();
                listFormulasContratos = administrarFormulaContrato.listFormulasContratosParaFormula(formulaActual.getSecuencia());
            }
            int tam = listFormulasContratos.size();
            if (tam >= 1) {
                for (int i = 0; i < tam; i++) {
                    if (listFormulasContratos.get(i).getTercero() == null) {
                        listFormulasContratos.get(i).setTercero(new Terceros());
                    }
                }
            }
            return listFormulasContratos;
        } catch (Exception e) {
            System.out.println("Error getListFormulasContratos " + e.toString());
            return null;
        }
    }

    public void setListFormulasContratos(List<Formulascontratos> t) {
        this.listFormulasContratos = t;
    }

    public List<Formulascontratos> getFiltrarListFormulasContratos() {
        return filtrarListFormulasContratos;
    }

    public void setFiltrarListFormulasContratos(List<Formulascontratos> t) {
        this.filtrarListFormulasContratos = t;
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

    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    public void setSecRegistroConcepto(BigInteger secRegistro) {
        this.secRegistroFormulaContrato = secRegistro;
    }

    public BigInteger getBackUpSecRegistroConceptos() {
        return backUpSecRegistroFormulaContrato;
    }

    public void setBackUpSecRegistroConceptos(BigInteger b) {
        this.backUpSecRegistroFormulaContrato = b;
    }

    public List<Formulascontratos> getListFormulasContratosModificar() {
        return listFormulasContratosModificar;
    }

    public void setListFormulasContratosModificar(List<Formulascontratos> setListFormulasContratosModificar) {
        this.listFormulasContratosModificar = setListFormulasContratosModificar;
    }

    public Formulascontratos getNuevaFormulaContrato() {
        return nuevaFormulaContrato;
    }

    public void setNuevaFormulaContrato(Formulascontratos setNuevaFormulaContrato) {
        this.nuevaFormulaContrato = setNuevaFormulaContrato;
    }

    public List<Formulascontratos> getListFormulasContratosCrear() {
        return listFormulasContratosCrear;
    }

    public void setListFormulasContratosCrear(List<Formulascontratos> setListFormulasContratosCrear) {
        this.listFormulasContratosCrear = setListFormulasContratosCrear;
    }

    public List<Formulascontratos> getListFormulasContratosBorrar() {
        return listFormulasContratosBorrar;
    }

    public void setListFormulasContratosBorrar(List<Formulascontratos> setListFormulasContratosBorrar) {
        this.listFormulasContratosBorrar = setListFormulasContratosBorrar;
    }

    public Formulascontratos getEditarFormulaContrato() {
        return editarFormulaContrato;
    }

    public void setEditarFormulaContrato(Formulascontratos setEditarFormulaContrato) {
        this.editarFormulaContrato = setEditarFormulaContrato;
    }

    public Formulascontratos getDuplicarFormulaContrato() {
        return duplicarFormulaContrato;
    }

    public void setDuplicarFormulaContrato(Formulascontratos setDuplicarFormulaContrato) {
        this.duplicarFormulaContrato = setDuplicarFormulaContrato;
    }

    public BigInteger getSecRegistroFormulaContrato() {
        return secRegistroFormulaContrato;
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

    public Formulas getFormulaActual() {
        return formulaActual;
    }

    public void setFormulaActual(Formulas setFormulaActual) {
        this.formulaActual = setFormulaActual;
    }

    public List<Contratos> getListContratos() {
        if (listContratos == null) {
            listContratos = administrarFormulaContrato.listContratos();

        }
        return listContratos;
    }

    public void setListContratos(List<Contratos> setListContratos) {
        this.listContratos = setListContratos;
    }

    public List<Contratos> getFiltrarListContratos() {
        return filtrarListContratos;
    }

    public void setFiltrarListContratos(List<Contratos> setFiltrarListContratos) {
        this.filtrarListContratos = setFiltrarListContratos;
    }

    public Contratos getContratoSeleccionado() {
        return contratoSeleccionado;
    }

    public void setContratoSeleccionado(Contratos setContratoSeleccionado) {
        this.contratoSeleccionado = setContratoSeleccionado;
    }

    public List<Terceros> getListTerceros() {
        if (listTerceros == null) {
            listTerceros = administrarFormulaContrato.listTerceros();
        }
        return listTerceros;
    }

    public void setListTerceros(List<Terceros> setListTerceros) {
        this.listTerceros = setListTerceros;
    }

    public List<Terceros> getFiltrarListTerceros() {
        return filtrarListTerceros;
    }

    public void setFiltrarListTerceros(List<Terceros> setFiltrarListTerceros) {
        this.filtrarListTerceros = setFiltrarListTerceros;
    }

    public Terceros getTerceroSeleccionada() {
        return terceroSeleccionada;
    }

    public void setTerceroSeleccionada(Terceros setTerceroSeleccionada) {
        this.terceroSeleccionada = setTerceroSeleccionada;
    }

    public List<Periodicidades> getListPeriodicidades() {
        if (listPeriodicidades == null) {
            listPeriodicidades = administrarFormulaContrato.listPeriodicidades();
        }
        return listPeriodicidades;
    }

    public void setListPeriodicidades(List<Periodicidades> setListPeriodicidades) {
        this.listPeriodicidades = setListPeriodicidades;
    }

    public List<Periodicidades> getFiltrarListPeriodicidades() {
        return filtrarListPeriodicidades;
    }

    public void setFiltrarListPeriodicidades(List<Periodicidades> setFiltrarListPeriodicidades) {
        this.filtrarListPeriodicidades = setFiltrarListPeriodicidades;
    }

    public Periodicidades getPeriodicidadSeleccionada() {
        return periodicidadSeleccionado;
    }

    public void setPeriodicidadSeleccionada(Periodicidades setPeriodicidadSeleccionada) {
        this.periodicidadSeleccionado = setPeriodicidadSeleccionada;
    }

    public void setSecRegistroFormulaContrato(BigInteger secRegistroVigenciaCuenta) {
        this.secRegistroFormulaContrato = secRegistroVigenciaCuenta;
    }

    public BigInteger getBackUpSecRegistroFormulaContrato() {
        return backUpSecRegistroFormulaContrato;
    }

    public void setBackUpSecRegistroFormulaContrato(BigInteger backUpSecRegistroVigenciaCuenta) {
        this.backUpSecRegistroFormulaContrato = backUpSecRegistroVigenciaCuenta;
    }

}

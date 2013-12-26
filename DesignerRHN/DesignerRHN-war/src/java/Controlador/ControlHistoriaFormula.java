package Controlador;

import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
import Entidades.Operadores;
import Entidades.Operandos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarHistoriaFormulaInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
public class ControlHistoriaFormula implements Serializable {

    @EJB
    AdministrarHistoriaFormulaInterface administrarHistoriaFormula;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //////////////Formulas//////////////////
    private Formulas formulaActual;
    ///////////Formulascontratos////////////
    private List<Historiasformulas> listHistoriasFormulas;
    private List<Historiasformulas> filtrarListHistoriasFormulas;
    ///////////Formulascontratos/////////////
    private int banderaHistoriasFormulas;
    private int indexHistoriasFormulas, indexAuxHistoriasFormulas;
    private List<Historiasformulas> listHistoriasFormulasModificar;
    private Historiasformulas nuevaHistoriaFormula;
    private List<Historiasformulas> listHistoriasFormulasCrear;
    private List<Historiasformulas> listHistoriasFormulasBorrar;
    private Historiasformulas editarHistoriaFormula;
    private int cualCeldaHistoriasFormulas, tipoListaHistoriasFormulas;
    private Historiasformulas duplicarHistoriaFormula;
    private boolean cambiosHistoriaFormula;
    private BigInteger secRegistroHistoriaFormula;
    private BigInteger backUpSecRegistroHistoriaFormula;
    private String observacion;
    private Date fechaIni, fechaFin;
    private Column historiaFechaInicial, historiaFechaFinal, historiaNota;
    private boolean permitirIndexNodos;
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
    /////////////////////
    private List<Nodos> listNodosHistoriaFormula;
    private String formulaCompleta;
    private String nodo1, nodo2, nodo3, nodo4, nodo5, nodo6, nodo7, nodo8, nodo9, nodo10, nodo11, nodo12, nodo13, nodo14, nodo15, nodo16;
    private boolean nodo1RO, nodo2RO, nodo3RO, nodo4RO, nodo5RO, nodo6RO, nodo7RO, nodo8RO, nodo9RO, nodo10RO, nodo11RO, nodo12RO, nodo13RO, nodo14RO, nodo15RO, nodo16RO;
    //////Operadores//////////
    private Operadores operadorSeleccionado;
    private List<Operadores> listOperadores;
    private List<Operadores> filtrarListOperadores;
    //////Operandos//////////
    private Operandos operandoSeleccionado;
    private List<Operandos> listOperandos;
    private List<Operandos> filtrarListOperandos;
    /////////////////////
    private int indexNodoSeleecionado;
    private String auxNodoSeleccionado;
    private int actualizacionNodo;
    private List<Nodos> listNodosCrear;
    private List<Nodos> listNodosBorrar;
    private List<Nodos> listNodosModificar;
    private BigInteger secuenciaRegistroNodos, backUpSecuenciaRegistroNodo;
    private boolean cambiosNodos;
    private List<Nodos> listNodosParaExportar;
    private Nodos editarNodo;

    public ControlHistoriaFormula() {
        editarNodo = new Nodos();
        listNodosCrear = new ArrayList<Nodos>();
        listNodosBorrar = new ArrayList<Nodos>();
        listNodosModificar = new ArrayList<Nodos>();
        actualizacionNodo = -1;
        listOperadores = null;
        listOperandos = null;
        operandoSeleccionado = new Operandos();
        operadorSeleccionado = new Operadores();
        nodo1 = "";
        nodo2 = "";
        nodo3 = "";
        nodo4 = "";
        nodo5 = "";
        nodo6 = "";
        nodo7 = "";
        nodo8 = "";
        nodo9 = "";
        nodo10 = "";
        nodo11 = "";
        nodo12 = "";
        nodo13 = "";
        nodo14 = "";
        nodo15 = "";
        nodo16 = "";
        nodo1RO = true;
        nodo2RO = true;
        nodo3RO = true;
        nodo4RO = true;
        nodo5RO = true;
        nodo6RO = true;
        nodo7RO = true;
        nodo8RO = true;
        nodo9RO = true;
        nodo10RO = true;
        nodo11RO = true;
        nodo12RO = true;
        nodo13RO = true;
        nodo14RO = true;
        nodo15RO = true;
        nodo16RO = true;
        formulaCompleta = "";
        listNodosHistoriaFormula = null;
        formulaActual = new Formulas();
        listHistoriasFormulas = null;
        fechaParametro = new Date(1, 1, 0);
        permitirIndexNodos = true;

        nombreExportar = "";
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";

        secRegistroHistoriaFormula = null;
        backUpSecRegistroHistoriaFormula = null;
        aceptar = true;
        k = 0;

        listHistoriasFormulasBorrar = new ArrayList<Historiasformulas>();
        listHistoriasFormulasCrear = new ArrayList<Historiasformulas>();
        listHistoriasFormulasModificar = new ArrayList<Historiasformulas>();
        editarHistoriaFormula = new Historiasformulas();
        cualCeldaHistoriasFormulas = -1;
        tipoListaHistoriasFormulas = 0;
        guardado = true;
        nuevaHistoriaFormula = new Historiasformulas();
        indexHistoriasFormulas = -1;
        banderaHistoriasFormulas = 0;
        nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
        nombreXML = "HistoriaFormula_XML";
        duplicarHistoriaFormula = new Historiasformulas();
        cambiosHistoriaFormula = false;
        cambiosNodos = false;
    }

    public void recibirFormula(BigInteger secuencia) {
        formulaActual = administrarHistoriaFormula.actualFormula(secuencia);
        listHistoriasFormulas = null;
        indexHistoriasFormulas = 0;
        indexAuxHistoriasFormulas = 0;
        System.out.println("formulaActual : " + formulaActual.getSecuencia());
        getListHistoriasFormulas();
        getListNodosHistoriaFormula();
        cargarDatosParaNodos();
        listNodosParaExportar = null;
    }

    public void modificarHistoriaFormula(int indice) {
        int aux = 0;
        if (tipoListaHistoriasFormulas == 0) {
            String nota = listHistoriasFormulas.get(indice).getObservaciones();
            if (!nota.isEmpty()) {
                aux = nota.length();
            }
        }
        if (tipoListaHistoriasFormulas == 1) {
            String nota = filtrarListHistoriasFormulas.get(indice).getObservaciones();
            if (!nota.isEmpty()) {
                aux = nota.length();
            }
        }
        if (aux >= 0 && aux <= 200) {
            if (tipoListaHistoriasFormulas == 0) {
                if (!listHistoriasFormulasCrear.contains(listHistoriasFormulas.get(indice))) {
                    if (listHistoriasFormulasModificar.isEmpty()) {
                        listHistoriasFormulasModificar.add(listHistoriasFormulas.get(indice));
                    } else if (!listHistoriasFormulasModificar.contains(listHistoriasFormulas.get(indice))) {
                        listHistoriasFormulasModificar.add(listHistoriasFormulas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexHistoriasFormulas = -1;
                secRegistroHistoriaFormula = null;
            } else {
                if (!listHistoriasFormulasCrear.contains(filtrarListHistoriasFormulas.get(indice))) {
                    if (listHistoriasFormulasModificar.isEmpty()) {
                        listHistoriasFormulasModificar.add(filtrarListHistoriasFormulas.get(indice));
                    } else if (!listHistoriasFormulasModificar.contains(filtrarListHistoriasFormulas.get(indice))) {
                        listHistoriasFormulasModificar.add(filtrarListHistoriasFormulas.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                indexHistoriasFormulas = -1;
                secRegistroHistoriaFormula = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosHistoriaFormula");
            cambiosHistoriaFormula = true;
        } else {
            if (tipoListaHistoriasFormulas == 0) {
                listHistoriasFormulas.get(indice).setObservaciones(observacion);
            }
            if (tipoListaHistoriasFormulas == 1) {
                filtrarListHistoriasFormulas.get(indice).setObservaciones(observacion);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosHistoriaFormula");
            context.execute("errorNotaHF.show()");
        }
    }

    public void cambiarIndiceHistoriaFormula(int indice, int celda) {
        if (permitirIndexNodos == true) {
            cualCeldaHistoriasFormulas = celda;
            indexHistoriasFormulas = indice;
            indexAuxHistoriasFormulas = indice;
            secRegistroHistoriaFormula = listHistoriasFormulas.get(indexHistoriasFormulas).getSecuencia();
            fechaIni = listHistoriasFormulas.get(indexHistoriasFormulas).getFechainicial();
            fechaFin = listHistoriasFormulas.get(indexHistoriasFormulas).getFechafinal();
            observacion = listHistoriasFormulas.get(indexHistoriasFormulas).getObservaciones();
            listNodosParaExportar = null;
            cargarDatosParaNodos();
            indexNodoSeleecionado = -1;
        }
    }

    public boolean validarFechasRegistroHistoriaFormula(int i) {
        boolean retorno = false;
        if (i == 0) {
            Historiasformulas auxiliar = listHistoriasFormulas.get(i);
            if (auxiliar.getFechainicial().after(fechaParametro) && (auxiliar.getFechainicial().before(auxiliar.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 1) {
            if (nuevaHistoriaFormula.getFechainicial().after(fechaParametro) && (nuevaHistoriaFormula.getFechainicial().before(nuevaHistoriaFormula.getFechafinal()))) {
                retorno = true;
            }
        }
        if (i == 2) {
            if (duplicarHistoriaFormula.getFechainicial().after(fechaParametro) && (duplicarHistoriaFormula.getFechainicial().before(duplicarHistoriaFormula.getFechafinal()))) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void modificacionesFechaHistoriaFormula(int i, int c) {
        Historiasformulas auxiliar = null;
        if (tipoListaHistoriasFormulas == 0) {
            auxiliar = listHistoriasFormulas.get(i);
        }
        if (tipoListaHistoriasFormulas == 1) {
            auxiliar = filtrarListHistoriasFormulas.get(i);
        }
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            boolean validacion = validarFechasRegistroHistoriaFormula(0);
            if (validacion == true) {
                cambiarIndiceHistoriaFormula(i, c);
                modificarHistoriaFormula(i);
                indexAuxHistoriasFormulas = i;
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosHistoriaFormula");
            } else {
                System.out.println("Error de fechas de ingreso");
                RequestContext context = RequestContext.getCurrentInstance();
                if (tipoListaHistoriasFormulas == 0) {
                    listHistoriasFormulas.get(indexHistoriasFormulas).setFechainicial(fechaIni);
                    listHistoriasFormulas.get(indexHistoriasFormulas).setFechafinal(fechaFin);
                }
                if (tipoListaHistoriasFormulas == 1) {
                    filtrarListHistoriasFormulas.get(indexHistoriasFormulas).setFechainicial(fechaIni);
                    filtrarListHistoriasFormulas.get(indexHistoriasFormulas).setFechafinal(fechaFin);
                }
                context.update("form:datosHistoriaFormula");
                context.execute("errorFechasHF.show()");
                indexHistoriasFormulas = -1;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaHistoriasFormulas == 0) {
                listHistoriasFormulas.get(i).setFechainicial(fechaIni);
                listHistoriasFormulas.get(i).setFechafinal(fechaFin);
            }
            if (tipoListaHistoriasFormulas == 1) {
                filtrarListHistoriasFormulas.get(i).setFechainicial(fechaIni);
                filtrarListHistoriasFormulas.get(i).setFechafinal(fechaFin);
            }
            context.update("form:datosHistoriaFormula");
            context.execute("errorRegNuevo.show()");
            indexHistoriasFormulas = -1;
        }
    }
    //GUARDAR
    /**
     */
    public void guardadoGeneral() {
        if (cambiosHistoriaFormula == true) {
            guardarCambiosHistoriaFormula();
        }
        if (cambiosNodos == true) {
            guardarCambiosNodos();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    public void guardarCambiosHistoriaFormula() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if (!listHistoriasFormulasBorrar.isEmpty()) {
            administrarHistoriaFormula.borrarHistoriasFormulas(listHistoriasFormulasBorrar);
            listHistoriasFormulasBorrar.clear();
        }
        if (!listHistoriasFormulasCrear.isEmpty()) {
            administrarHistoriaFormula.crearHistoriasFormulas(listHistoriasFormulasCrear);
            listHistoriasFormulasCrear.clear();
        }
        if (!listHistoriasFormulasModificar.isEmpty()) {
            administrarHistoriaFormula.editarHistoriasFormulas(listHistoriasFormulasModificar);
            listHistoriasFormulasModificar.clear();
        }
        listHistoriasFormulas = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHistoriaFormula");
        context.update("form:growl");
        k = 0;
        indexHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
        cambiosHistoriaFormula = false;
    }

    public void guardarCambiosNodos() {
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        if (!listNodosBorrar.isEmpty()) {
            administrarHistoriaFormula.borrarNodos(listNodosBorrar);
            listNodosBorrar.clear();
        }
        if (!listNodosCrear.isEmpty()) {
            administrarHistoriaFormula.crearNodos(listNodosCrear);
            listNodosCrear.clear();
        }
        if (!listNodosModificar.isEmpty()) {
            administrarHistoriaFormula.editarNodos(listNodosModificar);
            listNodosModificar.clear();
        }
        listNodosHistoriaFormula = null;
        listNodosParaExportar = null;
        cargarDatosParaNodos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:growl");
        k = 0;
        indexNodoSeleecionado = -1;
        secuenciaRegistroNodos = null;
        cambiosNodos = false;
    }

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (indexHistoriasFormulas >= 0) {
            cancelarModificacionesHistoriaFormula();
        } else if (indexNodoSeleecionado >= 0) {
            cancelarModificacionNodos();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void cancelarModificacionesHistoriaFormula() {
        if (banderaHistoriasFormulas == 1) {
            historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
            historiaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
            historiaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
            historiaNota.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHistoriaFormula");
            banderaHistoriasFormulas = 0;
            filtrarListHistoriasFormulas = null;
            tipoListaHistoriasFormulas = 0;
        }
        listHistoriasFormulasBorrar.clear();
        listHistoriasFormulasCrear.clear();
        listHistoriasFormulasModificar.clear();
        indexHistoriasFormulas = -1;
        indexAuxHistoriasFormulas = 0;
        secRegistroHistoriaFormula = null;
        k = 0;
        listHistoriasFormulas = null;
        guardado = true;
        cambiosHistoriaFormula = false;
        getListHistoriasFormulas();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHistoriaFormula");
    }

    public void cancelarModificacionNodos() {
        listNodosBorrar.clear();
        listNodosCrear.clear();
        listNodosModificar.clear();
        indexNodoSeleecionado = -1;
        secuenciaRegistroNodos = null;
        k = 0;
        listNodosHistoriaFormula = null;
        guardado = true;
        cambiosNodos = false;
        permitirIndexNodos = true;
        listNodosParaExportar = null;
        cargarDatosParaNodos();
    }

    public void editarCelda() {
        if (indexHistoriasFormulas >= 0) {
            if (tipoListaHistoriasFormulas == 0) {
                editarHistoriaFormula = listHistoriasFormulas.get(indexHistoriasFormulas);
            }
            if (tipoListaHistoriasFormulas == 1) {
                editarHistoriaFormula = filtrarListHistoriasFormulas.get(indexHistoriasFormulas);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaHistoriasFormulas == 0) {
                context.update("formularioDialogos:editarFechaInicialHFD");
                context.execute("editarFechaInicialHFD.show()");
                cualCeldaHistoriasFormulas = -1;
            } else if (cualCeldaHistoriasFormulas == 1) {
                context.update("formularioDialogos:editarFechaFinalHFD");
                context.execute("editarFechaFinalHFD.show()");
                cualCeldaHistoriasFormulas = -1;
            } else if (cualCeldaHistoriasFormulas == 2) {
                context.update("formularioDialogos:editarObservacionHFD");
                context.execute("editarObservacionHFD.show()");
                cualCeldaHistoriasFormulas = -1;
            }
            indexHistoriasFormulas = -1;
            secRegistroHistoriaFormula = null;
        }
        if (indexNodoSeleecionado >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            editarNodo = listNodosHistoriaFormula.get(indexNodoSeleecionado);
            if (editarNodo.getOperador().getSecuencia() != null) {
                String aux = editarNodo.getOperador().getSigno();
                editarNodo.setDescripcionNodo(aux);
                context.update("formularioDialogos:editarOperadorNodoD");
                context.execute("editarOperadorNodoD.show()");
            }
            if (editarNodo.getOperando().getSecuencia() != null) {
                String aux = editarNodo.getOperando().getNombre();
                editarNodo.setDescripcionNodo(aux);
                context.update("formularioDialogos:editarOperandoNodoD");
                context.execute("editarOperandoNodoD.show()");
            }
            indexNodoSeleecionado = -1;
            secuenciaRegistroNodos = null;
        }
    }

    public void ingresoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("NuevoRegistroPagina.show()");
    }

    public void validarIngresoNuevaHistoriaFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoHistoriaFormula();
        context.update("form:nuevaHF");
        context.update("formularioDialogos:NuevoRegistroHistoria");
        context.execute("NuevoRegistroHistoria.show()");

    }

    public void validarDuplicadoRegistro() {
        if (indexHistoriasFormulas >= 0) {
            duplicarHistoriaFormulaM();
        } else if (indexNodoSeleecionado >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorDuplicarNodo.show()");
            indexNodoSeleecionado = -1;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void validarBorradoRegistro() {
        if (indexHistoriasFormulas >= 0) {
            borrarHistoriaFormula();
        } else if (indexNodoSeleecionado >= 0) {
            borrarNodo();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public boolean validarNuevosDatosHistoriaFormula(int i) {
        boolean retorno = false;
        if (i == 1) {
            if ((nuevaHistoriaFormula.getFechafinal() != null)
                    && (nuevaHistoriaFormula.getFechainicial() != null)) {
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarHistoriaFormula.getFechafinal() != null)
                    && (duplicarHistoriaFormula.getFechainicial() != null)) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void agregarNuevoHistoriaFormula() {
        boolean resp = validarNuevosDatosHistoriaFormula(1);
        if (resp == true) {
            boolean validacion = validarFechasRegistroHistoriaFormula(1);
            if (validacion == true) {
                int tam = 0;
                String aux = nuevaHistoriaFormula.getObservaciones();
                if (!aux.isEmpty()) {
                    tam = aux.length();
                }
                if (tam >= 0 && tam <= 200) {
                    if (banderaHistoriasFormulas == 1) {
                        historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
                        historiaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
                        historiaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
                        historiaNota.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosHistoriaFormula");
                        banderaHistoriasFormulas = 0;
                        filtrarListHistoriasFormulas = null;
                        tipoListaHistoriasFormulas = 0;
                    }
                    k++;

                    BigInteger var = BigInteger.valueOf(k);
                    nuevaHistoriaFormula.setSecuencia(var);
                    nuevaHistoriaFormula.setFormula(formulaActual);
                    listHistoriasFormulasCrear.add(nuevaHistoriaFormula);
                    listHistoriasFormulas.add(nuevaHistoriaFormula);
                    ////------////
                    nuevaHistoriaFormula = new Historiasformulas();
                    ////-----////
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("NuevoRegistroHistoria.hide()");
                    context.update("form:datosHistoriaFormula");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    cambiosHistoriaFormula = true;
                    indexHistoriasFormulas = -1;
                    secRegistroHistoriaFormula = null;
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorNotaHF.show()");
                }
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasHF.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoHistoriaFormula() {
        nuevaHistoriaFormula = new Historiasformulas();
        indexHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
    }

    public void duplicarHistoriaFormulaM() {

        if (indexHistoriasFormulas >= 0) {
            duplicarHistoriaFormula = new Historiasformulas();
            if (tipoListaHistoriasFormulas == 0) {
                duplicarHistoriaFormula.setObservaciones(listHistoriasFormulas.get(indexHistoriasFormulas).getObservaciones());
                duplicarHistoriaFormula.setFechafinal(listHistoriasFormulas.get(indexHistoriasFormulas).getFechafinal());
                duplicarHistoriaFormula.setFechainicial(listHistoriasFormulas.get(indexHistoriasFormulas).getFechainicial());

            }
            if (tipoListaHistoriasFormulas == 1) {
                duplicarHistoriaFormula.setObservaciones(filtrarListHistoriasFormulas.get(indexHistoriasFormulas).getObservaciones());
                duplicarHistoriaFormula.setFechafinal(filtrarListHistoriasFormulas.get(indexHistoriasFormulas).getFechafinal());
                duplicarHistoriaFormula.setFechainicial(filtrarListHistoriasFormulas.get(indexHistoriasFormulas).getFechainicial());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroHistoria");
            context.execute("DuplicarRegistroHistoria.show()");

        }
    }

    public void confirmarDuplicarHistoriaFormula() {
        boolean resp = validarNuevosDatosHistoriaFormula(2);
        if (resp == true) {
            boolean validacion = validarFechasRegistroHistoriaFormula(2);
            if (validacion == true) {
                int tam = 0;
                String aux = duplicarHistoriaFormula.getObservaciones();
                if (!aux.isEmpty()) {
                    tam = aux.length();
                }
                if (tam >= 0 && tam <= 200) {
                    if (banderaHistoriasFormulas == 1) {
                        historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
                        historiaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
                        historiaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
                        historiaNota.setFilterStyle("display: none; visibility: hidden;");
                        banderaHistoriasFormulas = 0;
                        filtrarListHistoriasFormulas = null;
                        tipoListaHistoriasFormulas = 0;
                    }
                    k++;
                    BigInteger var = BigInteger.valueOf(k);

                    duplicarHistoriaFormula.setSecuencia(var);
                    duplicarHistoriaFormula.setFormula(formulaActual);
                    listHistoriasFormulasCrear.add(duplicarHistoriaFormula);
                    listHistoriasFormulas.add(duplicarHistoriaFormula);
                    duplicarHistoriaFormula = new Historiasformulas();

                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosHistoriaFormula");
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                    context.execute("DuplicarRegistroFormula.hide()");
                    cambiosHistoriaFormula = true;
                    indexHistoriasFormulas = -1;
                    secRegistroHistoriaFormula = null;
                } else {
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.execute("errorNotaHF.show()");
                }

            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechasHF.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarHistoriaFormula() {
        duplicarHistoriaFormula = new Historiasformulas();
        indexHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
    }

    ///////////////////////////////////////////////////////////////
    public void borrarHistoriaFormula() {

        if (indexHistoriasFormulas >= 0) {
            if (tipoListaHistoriasFormulas == 0) {
                if (!listHistoriasFormulasModificar.isEmpty() && listHistoriasFormulasModificar.contains(listHistoriasFormulas.get(indexHistoriasFormulas))) {
                    int modIndex = listHistoriasFormulasModificar.indexOf(listHistoriasFormulas.get(indexHistoriasFormulas));
                    listHistoriasFormulasModificar.remove(modIndex);
                    listHistoriasFormulasBorrar.add(listHistoriasFormulas.get(indexHistoriasFormulas));
                } else if (!listHistoriasFormulasCrear.isEmpty() && listHistoriasFormulasCrear.contains(listHistoriasFormulas.get(indexHistoriasFormulas))) {
                    int crearIndex = listHistoriasFormulasCrear.indexOf(listHistoriasFormulas.get(indexHistoriasFormulas));
                    listHistoriasFormulasCrear.remove(crearIndex);
                } else {
                    listHistoriasFormulasBorrar.add(listHistoriasFormulas.get(indexHistoriasFormulas));
                }
                listHistoriasFormulas.remove(indexHistoriasFormulas);
            }
            if (tipoListaHistoriasFormulas == 1) {
                if (!listHistoriasFormulasModificar.isEmpty() && listHistoriasFormulasModificar.contains(filtrarListHistoriasFormulas.get(indexHistoriasFormulas))) {
                    int modIndex = listHistoriasFormulasModificar.indexOf(filtrarListHistoriasFormulas.get(indexHistoriasFormulas));
                    listHistoriasFormulasModificar.remove(modIndex);
                    listHistoriasFormulasBorrar.add(filtrarListHistoriasFormulas.get(indexHistoriasFormulas));
                } else if (!listHistoriasFormulasCrear.isEmpty() && listHistoriasFormulasCrear.contains(filtrarListHistoriasFormulas.get(indexHistoriasFormulas))) {
                    int crearIndex = listHistoriasFormulasCrear.indexOf(filtrarListHistoriasFormulas.get(indexHistoriasFormulas));
                    listHistoriasFormulasCrear.remove(crearIndex);
                } else {
                    listHistoriasFormulasBorrar.add(filtrarListHistoriasFormulas.get(indexHistoriasFormulas));
                }
                int VLIndex = listHistoriasFormulas.indexOf(filtrarListHistoriasFormulas.get(indexHistoriasFormulas));
                listHistoriasFormulas.remove(VLIndex);
                filtrarListHistoriasFormulas.remove(indexHistoriasFormulas);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosHistoriaFormula");
            indexHistoriasFormulas = -1;
            secRegistroHistoriaFormula = null;
            cambiosHistoriaFormula = true;
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
        if (banderaHistoriasFormulas == 0) {
            historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
            historiaFechaInicial.setFilterStyle("width: 60px");
            historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
            historiaFechaFinal.setFilterStyle("width: 60px");
            historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
            historiaNota.setFilterStyle("width: 80px");

            RequestContext.getCurrentInstance().update("form:datosHistoriaFormula");
            banderaHistoriasFormulas = 1;
        } else if (banderaHistoriasFormulas == 1) {
            historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
            historiaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
            historiaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
            historiaNota.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosHistoriaFormula");
            banderaHistoriasFormulas = 0;
            filtrarListHistoriasFormulas = null;
            tipoListaHistoriasFormulas = 0;
        }
    }
    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (banderaHistoriasFormulas == 1) {
            historiaFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaInicial");
            historiaFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            historiaFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaFechaFinal");
            historiaFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            historiaNota = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosHistoriaFormula:historiaNota");
            historiaNota.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosHistoriaFormula");
            banderaHistoriasFormulas = 0;
            filtrarListHistoriasFormulas = null;
            tipoListaHistoriasFormulas = 0;
        }

        listHistoriasFormulasBorrar.clear();
        listHistoriasFormulasCrear.clear();
        listHistoriasFormulasModificar.clear();
        indexHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
        formulaActual = null;
        k = 0;
        indexAuxHistoriasFormulas = -1;
        listHistoriasFormulas = null;
        guardado = true;
        cambiosHistoriaFormula = false;
        nuevaHistoriaFormula = new Historiasformulas();
        duplicarHistoriaFormula = new Historiasformulas();
        listNodosHistoriaFormula = null;
        listNodosCrear.clear();
        listNodosBorrar.clear();
        listNodosModificar.clear();
        formulaCompleta = null;
        indexNodoSeleecionado = -1;
        secuenciaRegistroNodos = null;
        aceptar = true;
        listNodosParaExportar = null;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (indexHistoriasFormulas >= 0) {
            nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
            nombreXML = "HistoriaFormula_XML";
        }
        if (indexNodoSeleecionado >= 0) {
            nombreTabla = ":formExportarNodos:datosNodosExportar";
            nombreXML = "GenerarFormulaXML";
        }
        return nombreTabla;
    }

    /**
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        if (indexHistoriasFormulas >= 0) {
            nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
            nombreExportar = "HistoriaFormula_PDF";
            exportPDF_Tabla();
            indexHistoriasFormulas = -1;
            secRegistroHistoriaFormula = null;
        }
        if (indexNodoSeleecionado >= 0) {
            nombreTabla = ":formExportarNodos:datosNodosExportar";
            nombreExportar = "GenerarFormula_PDF";
            exportPDF_Tabla();
            indexNodoSeleecionado = -1;
            secuenciaRegistroNodos = null;
        }
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
        if (indexHistoriasFormulas >= 0) {
            nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
            nombreExportar = "HistoriaFormula_XLS";
            exportXLS_Tabla();
            indexHistoriasFormulas = -1;
            indexAuxHistoriasFormulas = -1;
            secRegistroHistoriaFormula = null;
        }
        if (indexNodoSeleecionado >= 0) {
            nombreTabla = ":formExportarNodos:datosNodosExportar";
            nombreExportar = "GenerarFormula_XLS";
            exportXLS_Tabla();
            indexNodoSeleecionado = -1;
            secuenciaRegistroNodos = null;
        }
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
        if (tipoListaHistoriasFormulas == 0) {
            tipoListaHistoriasFormulas = 1;
        }
    }

    public void verificarRastroTabla() {
        if (indexHistoriasFormulas >= 0) {
            verificarRastroHistoriaFormula();
            indexHistoriasFormulas = -1;
            indexAuxHistoriasFormulas = -1;
        }
        if (indexNodoSeleecionado >= 0) {
            verificarRastroNodos();
            indexNodoSeleecionado = -1;
        }

    }

    public void verificarRastroHistoriaFormula() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listHistoriasFormulas != null) {
            if (secRegistroHistoriaFormula != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroHistoriaFormula, "HISTORIASFORMULAS");
                backUpSecRegistroHistoriaFormula = secRegistroHistoriaFormula;
                backUp = secRegistroHistoriaFormula;
                secRegistroHistoriaFormula = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Historiasformulas";
                    msnConfirmarRastro = "La tabla HISTORIASFORMULAS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("HISTORIASFORMULAS")) {
                nombreTablaRastro = "Historiasformulas";
                msnConfirmarRastroHistorico = "La tabla HISTORIASFORMULAS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexHistoriasFormulas = -1;
    }

    public void verificarRastroNodos() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listNodosHistoriaFormula != null) {
            if (secuenciaRegistroNodos != null) {
                int resultado = administrarRastros.obtenerTabla(secuenciaRegistroNodos, "NODOS");
                backUpSecuenciaRegistroNodo = secuenciaRegistroNodos;
                backUp = secuenciaRegistroNodos;
                secuenciaRegistroNodos = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "Nodos";
                    msnConfirmarRastro = "La tabla NODOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("NODOS")) {
                nombreTablaRastro = "Nodos";
                msnConfirmarRastroHistorico = "La tabla NODOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        indexNodoSeleecionado = -1;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    public void limpiarIformacionNodo() {
        nodo1 = " ";
        nodo1RO = true;
        nodo2 = " ";
        nodo2RO = true;
        nodo3 = " ";
        nodo3RO = true;
        nodo4 = " ";
        nodo5RO = true;
        nodo5 = " ";
        nodo5RO = true;
        nodo6 = " ";
        nodo6RO = true;
        nodo7 = " ";
        nodo7RO = true;
        nodo8 = " ";
        nodo8RO = true;
        nodo9 = " ";
        nodo9RO = true;
        nodo10 = " ";
        nodo10RO = true;
        nodo11 = " ";
        nodo11RO = true;
        nodo12 = " ";
        nodo12RO = true;
        nodo13 = " ";
        nodo13RO = true;
        nodo14 = " ";
        nodo14RO = true;
        nodo15 = " ";
        nodo15RO = true;
        nodo16 = " ";
        nodo16RO = true;
    }

    public void posicionNodo(int i) {
        indexNodoSeleecionado = i;
        secuenciaRegistroNodos = listNodosHistoriaFormula.get(indexNodoSeleecionado).getSecuencia();
        indexHistoriasFormulas = -1;
        if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperador().getSecuencia() != null) {
            auxNodoSeleccionado = listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperador().getSigno();
        }
        if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperando().getSecuencia() != null) {
            auxNodoSeleccionado = listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperando().getNombre();
        }
    }

    public void cargarDatosParaNodos() {
        getListNodosHistoriaFormula();
        limpiarIformacionNodo();
        RequestContext context = RequestContext.getCurrentInstance();
        int tam = listNodosHistoriaFormula.size();
        System.out.println("Valor tam : " + tam);
        int aux = 0;
        while (aux < tam) {
            if (aux == 0) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo1 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo1 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo1RO = false;
                context.update("form:nodo1");
            }
            if (aux == 1) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo2 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo2 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo2RO = false;
                context.update("form:nodo2");
            }
            if (aux == 2) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo3 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo3 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo3RO = false;
                context.update("form:nodo3");
            }
            if (aux == 3) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo4 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo4 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo4RO = false;
                context.update("form:nodo4");
            }
            if (aux == 4) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo5 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo5 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo5RO = false;
                context.update("form:nodo5");
            }
            if (aux == 5) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo6 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo6 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo6RO = false;
                context.update("form:nodo6");
            }
            if (aux == 6) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo7 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo7 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo7RO = false;
                context.update("form:nodo7");
            }
            if (aux == 7) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo8 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo8 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo8RO = false;
                context.update("form:nodo8");
            }
            if (aux == 8) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo9 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo9 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo9RO = false;
                context.update("form:nodo9");
            }
            if (aux == 9) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo10 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo10 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo10RO = false;
                context.update("form:nodo10");
            }
            if (aux == 10) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo11 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo11 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo11RO = false;
                context.update("form:nodo11");
            }
            if (aux == 11) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo12 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo12 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo12RO = false;
                context.update("form:nodo12");
            }
            if (aux == 12) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo13 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo13 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo13RO = false;
                context.update("form:nodo13");
            }
            if (aux == 13) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo14 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo14 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo14RO = false;
                context.update("form:nodo14");
            }
            if (aux == 14) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo15 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo15 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo15RO = false;
                context.update("form:nodo15");
            }
            if (aux == 15) {
                if (listNodosHistoriaFormula.get(aux).getOperador().getSecuencia() != null) {
                    nodo16 = listNodosHistoriaFormula.get(aux).getOperador().getSigno();
                } else if (listNodosHistoriaFormula.get(aux).getOperando().getSecuencia() != null) {
                    nodo16 = listNodosHistoriaFormula.get(aux).getOperando().getNombre();
                }
                nodo16RO = false;
                context.update("form:nodo16");
            }
            aux++;
        }
        getFormulaCompleta();
        context.update("form:formulaComplete");
        context.update("form:panelNodos");
    }

    public void dispararDialogoNodo() {
        if (indexNodoSeleecionado >= 0) {
            actualizacionNodo = 0;
            if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperando().getSecuencia() != null) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("OperandoDialogo.show()");
            }
            if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperador().getSecuencia() != null) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("OperadorDialogo.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistroNodo.show()");
        }
    }

    public void dialogoNuevoOperando() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("OperandoDialogo.show()");
        actualizacionNodo = 1;
    }

    public void dialogoNuevoOperandor() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("OperadorDialogo.show()");
        actualizacionNodo = 1;
    }

    public void actualizarOperando() {
        if (actualizacionNodo == 0) {
            modificacionesCambiosNodos(indexNodoSeleecionado, 0);
        }
        if (actualizacionNodo == 1) {
            k++;
            BigInteger var = BigInteger.valueOf(k);
            Nodos nuevo = new Nodos(var);
            nuevo.setOperador(new Operadores());
            nuevo.setOperando(new Operandos());
            nuevo.setHistoriaformula(listHistoriasFormulas.get(indexAuxHistoriasFormulas));
            nuevo.setOperando(operandoSeleccionado);
            listNodosHistoriaFormula.add(nuevo);
            listNodosCrear.add(nuevo);
        }
        listNodosParaExportar = null;
        cargarDatosParaNodos();
        filtrarListOperandos = null;
        operandoSeleccionado = new Operandos();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:OperandoDialogo");
    }

    public void cancelarCambioOperando() {
        filtrarListOperandos = null;
        operandoSeleccionado = new Operandos();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:OperandoDialogo");
        aceptar = true;
    }

    public void actualizarOperador() {
        if (actualizacionNodo == 0) {
            modificacionesCambiosNodos(indexNodoSeleecionado, 1);
        }
        if (actualizacionNodo == 1) {
            k++;
            BigInteger var = BigInteger.valueOf(k);
            Nodos nuevo = new Nodos(var);
            nuevo.setOperador(new Operadores());
            nuevo.setOperando(new Operandos());
            nuevo.setHistoriaformula(listHistoriasFormulas.get(indexAuxHistoriasFormulas));
            nuevo.setOperador(operadorSeleccionado);
            listNodosHistoriaFormula.add(nuevo);
            listNodosCrear.add(nuevo);
        }
        listNodosParaExportar = null;
        cargarDatosParaNodos();
        filtrarListOperadores = null;
        operadorSeleccionado = new Operadores();
        aceptar = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:OperadorDialogo");
    }

    public void cancelarCambioOperador() {
        filtrarListOperadores = null;
        operadorSeleccionado = new Operadores();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:OperadorDialogo");
        aceptar = true;
    }

    public void modificacionesCambiosNodos(int indice, int tipoCambio) {
        if (tipoCambio == 0) {
            listNodosHistoriaFormula.get(indice).setOperando(operandoSeleccionado);
        } else if (tipoCambio == 1) {
            listNodosHistoriaFormula.get(indice).setOperador(operadorSeleccionado);
        }
        if (!listNodosCrear.contains(listNodosHistoriaFormula.get(indice))) {
            if (listNodosModificar.isEmpty()) {
                listNodosModificar.add(listNodosHistoriaFormula.get(indice));
            } else if (!listNodosModificar.contains(listNodosHistoriaFormula.get(indice))) {
                listNodosModificar.add(listNodosHistoriaFormula.get(indice));
            }
            if (guardado == true) {
                guardado = false;
            }
        }
        cambiosNodos = true;
        indexNodoSeleecionado = -1;
        secuenciaRegistroNodos = null;
        listNodosParaExportar = null;
    }

    public void modificacionesAutoCompletarNodos(int indice, String valorConfirmar) {
        indexNodoSeleecionado = indice;
        int tipoCambio = -1;
        if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperando().getSecuencia() != null) {
            tipoCambio = 0;
        } else if (listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperador().getSecuencia() != null) {
            tipoCambio = 1;
        }
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (tipoCambio == 0) {
            listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperando().setDescripcion(auxNodoSeleccionado);
            for (int i = 0; i < listOperandos.size(); i++) {
                if (listOperandos.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                listNodosHistoriaFormula.get(indexNodoSeleecionado).setOperando(listOperandos.get(indiceUnicoElemento));
                listOperandos.clear();
                getListOperandos();
            } else {
                permitirIndexNodos = false;
                context.update("form:OperandoDialogo");
                context.execute("OperandoDialogo.show()");
                actualizacionNodo = 0;
            }
        }
        if (tipoCambio == 1) {
            listNodosHistoriaFormula.get(indexNodoSeleecionado).getOperador().setSigno(auxNodoSeleccionado);
            for (int i = 0; i < listOperadores.size(); i++) {
                if (listOperadores.get(i).getSigno().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                listNodosHistoriaFormula.get(indexNodoSeleecionado).setOperador(listOperadores.get(indiceUnicoElemento));
                listOperadores.clear();
                getListOperadores();
            } else {
                permitirIndexNodos = false;
                context.update("form:OperadorDialogo");
                context.execute("OperadorDialogo.show()");
                actualizacionNodo = 0;
            }
        }
        if (coincidencias == 1) {
            if (!listNodosCrear.contains(listNodosHistoriaFormula.get(indexNodoSeleecionado))) {
                if (listNodosModificar.isEmpty()) {
                    listNodosModificar.add(listNodosHistoriaFormula.get(indexNodoSeleecionado));
                } else if (!listNodosModificar.contains(listNodosHistoriaFormula.get(indexNodoSeleecionado))) {
                    listNodosModificar.add(listNodosHistoriaFormula.get(indexNodoSeleecionado));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            cambiosNodos = true;
            indexNodoSeleecionado = -1;
            secuenciaRegistroNodos = null;
        }
        listNodosParaExportar = null;
        cargarDatosParaNodos();
    }

    public void borrarNodo() {
        if (indexNodoSeleecionado >= 0) {
            if (!listNodosModificar.isEmpty() && listNodosModificar.contains(listNodosHistoriaFormula.get(indexNodoSeleecionado))) {
                int modIndex = listNodosModificar.indexOf(listNodosHistoriaFormula.get(indexNodoSeleecionado));
                listNodosModificar.remove(modIndex);
                listNodosBorrar.add(listNodosHistoriaFormula.get(indexNodoSeleecionado));
            } else if (!listNodosCrear.isEmpty() && listNodosCrear.contains(listNodosHistoriaFormula.get(indexNodoSeleecionado))) {
                int crearIndex = listNodosCrear.indexOf(listNodosHistoriaFormula.get(indexNodoSeleecionado));
                listNodosCrear.remove(crearIndex);
            } else {
                listNodosBorrar.add(listNodosHistoriaFormula.get(indexNodoSeleecionado));
            }
            listNodosHistoriaFormula.remove(indexNodoSeleecionado);
            indexNodoSeleecionado = -1;
            secuenciaRegistroNodos = null;
            cambiosNodos = true;
            if (guardado == true) {
                guardado = false;
            }
            listNodosParaExportar = null;
            cargarDatosParaNodos();
        }
    }

    //GET - SET 
    public List<Historiasformulas> getListHistoriasFormulas() {
        try {
            if (listHistoriasFormulas == null) {
                listHistoriasFormulas = new ArrayList<Historiasformulas>();
                listHistoriasFormulas = administrarHistoriaFormula.listHistoriasFormulasParaFormula(formulaActual.getSecuencia());
            }
            return listHistoriasFormulas;
        } catch (Exception e) {
            System.out.println("Error getListHistoriasFormulas " + e.toString());
            return null;
        }
    }

    public void setListHistoriasFormulas(List<Historiasformulas> t) {
        this.listHistoriasFormulas = t;
    }

    public List<Historiasformulas> getFiltrarListHistoriasFormulas() {
        return filtrarListHistoriasFormulas;
    }

    public void setFiltrarListHistoriasFormulas(List<Historiasformulas> filtrarListHistoriasFormulas) {
        this.filtrarListHistoriasFormulas = filtrarListHistoriasFormulas;
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

    public void setSecRegistroHistoriaFormula(BigInteger secRegistro) {
        this.secRegistroHistoriaFormula = secRegistro;
    }

    public BigInteger getBackUpSecRegistroHistoriaFormula() {
        return backUpSecRegistroHistoriaFormula;
    }

    public void setBackUpSecRegistroHistoriaFormula(BigInteger b) {
        this.backUpSecRegistroHistoriaFormula = b;
    }

    public List<Historiasformulas> getListHistoriasFormulasModificar() {
        return listHistoriasFormulasModificar;
    }

    public void setListHistoriasFormulasModificar(List<Historiasformulas> setListHistoriasFormulasModificar) {
        this.listHistoriasFormulasModificar = setListHistoriasFormulasModificar;
    }

    public Historiasformulas getNuevaHistoriaFormula() {
        return nuevaHistoriaFormula;
    }

    public void setNuevaHistoriaFormula(Historiasformulas setNuevaHistoriaFormula) {
        this.nuevaHistoriaFormula = setNuevaHistoriaFormula;
    }

    public List<Historiasformulas> getListHistoriasFormulasCrear() {
        return listHistoriasFormulasCrear;
    }

    public void setListHistoriasFormulasCrear(List<Historiasformulas> setListHistoriasFormulasCrear) {
        this.listHistoriasFormulasCrear = setListHistoriasFormulasCrear;
    }

    public List<Historiasformulas> getListHistoriasFormulasBorrar() {
        return listHistoriasFormulasBorrar;
    }

    public void setListHistoriasFormulasBorrar(List<Historiasformulas> setListHistoriasFormulasBorrar) {
        this.listHistoriasFormulasBorrar = setListHistoriasFormulasBorrar;
    }

    public Historiasformulas getEditarHistoriaFormula() {
        return editarHistoriaFormula;
    }

    public void setEditarHistoriaFormula(Historiasformulas setEditarHistoriaFormula) {
        this.editarHistoriaFormula = setEditarHistoriaFormula;
    }

    public Historiasformulas getDuplicarHistoriaFormula() {
        return duplicarHistoriaFormula;
    }

    public void setDuplicarHistoriaFormula(Historiasformulas setDuplicarHistoriaFormula) {
        this.duplicarHistoriaFormula = setDuplicarHistoriaFormula;
    }

    public BigInteger getSecRegistroHistoriaFormula() {
        return secRegistroHistoriaFormula;
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

    public List<Nodos> getListNodosHistoriaFormula() {
        if (listNodosHistoriaFormula == null) {
            listNodosHistoriaFormula = administrarHistoriaFormula.listNodosDeHistoriaFormula(listHistoriasFormulas.get(indexAuxHistoriasFormulas).getSecuencia());
            if (!listNodosHistoriaFormula.isEmpty()) {
                for (int i = 0; i < listNodosHistoriaFormula.size(); i++) {
                    if (listNodosHistoriaFormula.get(i).getOperador() == null) {
                        listNodosHistoriaFormula.get(i).setOperador(new Operadores());
                    }
                    if (listNodosHistoriaFormula.get(i).getOperando() == null) {
                        listNodosHistoriaFormula.get(i).setOperando(new Operandos());
                    }
                }
            }
        }
        return listNodosHistoriaFormula;
    }

    public void setListNodosHistoriaFormula(List<Nodos> listNodosHistoriaFormula) {
        this.listNodosHistoriaFormula = listNodosHistoriaFormula;
    }

    public String getFormulaCompleta() {
        if (listNodosHistoriaFormula != null) {
            formulaCompleta = "";
            for (int i = 0; i < listNodosHistoriaFormula.size(); i++) {
                if (listNodosHistoriaFormula.get(i).getOperador().getSecuencia() != null) {
                    formulaCompleta = formulaCompleta + listNodosHistoriaFormula.get(i).getOperador().getSigno() + " ";
                } else if (listNodosHistoriaFormula.get(i).getOperando().getSecuencia() != null) {
                    formulaCompleta = formulaCompleta + listNodosHistoriaFormula.get(i).getOperando().getNombre() + " ";
                }
            }
        }
        return formulaCompleta;
    }

    public void setFormulaCompleta(String formulaCompleta) {
        this.formulaCompleta = formulaCompleta;
    }

    public String getNodo1() {
        return nodo1;
    }

    public void setNodo1(String nodo1) {
        this.nodo1 = nodo1;
    }

    public String getNodo2() {
        return nodo2;
    }

    public void setNodo2(String nodo2) {
        this.nodo2 = nodo2;
    }

    public String getNodo3() {
        return nodo3;
    }

    public void setNodo3(String nodo3) {
        this.nodo3 = nodo3;
    }

    public String getNodo4() {
        return nodo4;
    }

    public void setNodo4(String nodo4) {
        this.nodo4 = nodo4;
    }

    public String getNodo5() {
        return nodo5;
    }

    public void setNodo5(String nodo5) {
        this.nodo5 = nodo5;
    }

    public String getNodo6() {
        return nodo6;
    }

    public void setNodo6(String nodo6) {
        this.nodo6 = nodo6;
    }

    public String getNodo7() {
        return nodo7;
    }

    public void setNodo7(String nodo7) {
        this.nodo7 = nodo7;
    }

    public String getNodo8() {
        return nodo8;
    }

    public void setNodo8(String nodo8) {
        this.nodo8 = nodo8;
    }

    public String getNodo9() {
        return nodo9;
    }

    public void setNodo9(String nodo9) {
        this.nodo9 = nodo9;
    }

    public String getNodo10() {
        return nodo10;
    }

    public void setNodo10(String nodo10) {
        this.nodo10 = nodo10;
    }

    public String getNodo11() {
        return nodo11;
    }

    public void setNodo11(String nodo11) {
        this.nodo11 = nodo11;
    }

    public String getNodo12() {
        return nodo12;
    }

    public void setNodo12(String nodo12) {
        this.nodo12 = nodo12;
    }

    public String getNodo13() {
        return nodo13;
    }

    public void setNodo13(String nodo13) {
        this.nodo13 = nodo13;
    }

    public String getNodo14() {
        return nodo14;
    }

    public void setNodo14(String nodo14) {
        this.nodo14 = nodo14;
    }

    public String getNodo15() {
        return nodo15;
    }

    public void setNodo15(String nodo15) {
        this.nodo15 = nodo15;
    }

    public String getNodo16() {
        return nodo16;
    }

    public void setNodo16(String nodo16) {
        this.nodo16 = nodo16;
    }

    public boolean isNodo1RO() {
        return nodo1RO;
    }

    public void setNodo1RO(boolean nodo1RO) {
        this.nodo1RO = nodo1RO;
    }

    public boolean isNodo2RO() {
        return nodo2RO;
    }

    public void setNodo2RO(boolean nodo2RO) {
        this.nodo2RO = nodo2RO;
    }

    public boolean isNodo3RO() {
        return nodo3RO;
    }

    public void setNodo3RO(boolean nodo3RO) {
        this.nodo3RO = nodo3RO;
    }

    public boolean isNodo4RO() {
        return nodo4RO;
    }

    public void setNodo4RO(boolean nodo4RO) {
        this.nodo4RO = nodo4RO;
    }

    public boolean isNodo5RO() {
        return nodo5RO;
    }

    public void setNodo5RO(boolean nodo5RO) {
        this.nodo5RO = nodo5RO;
    }

    public boolean isNodo6RO() {
        return nodo6RO;
    }

    public void setNodo6RO(boolean nodo6RO) {
        this.nodo6RO = nodo6RO;
    }

    public boolean isNodo7RO() {
        return nodo7RO;
    }

    public void setNodo7RO(boolean nodo7RO) {
        this.nodo7RO = nodo7RO;
    }

    public boolean isNodo8RO() {
        return nodo8RO;
    }

    public void setNodo8RO(boolean nodo8RO) {
        this.nodo8RO = nodo8RO;
    }

    public boolean isNodo9RO() {
        return nodo9RO;
    }

    public void setNodo9RO(boolean nodo9RO) {
        this.nodo9RO = nodo9RO;
    }

    public boolean isNodo10RO() {
        return nodo10RO;
    }

    public void setNodo10RO(boolean nodo10RO) {
        this.nodo10RO = nodo10RO;
    }

    public boolean isNodo11RO() {
        return nodo11RO;
    }

    public void setNodo11RO(boolean nodo11RO) {
        this.nodo11RO = nodo11RO;
    }

    public boolean isNodo12RO() {
        return nodo12RO;
    }

    public void setNodo12RO(boolean nodo12RO) {
        this.nodo12RO = nodo12RO;
    }

    public boolean isNodo13RO() {
        return nodo13RO;
    }

    public void setNodo13RO(boolean nodo13RO) {
        this.nodo13RO = nodo13RO;
    }

    public boolean isNodo14RO() {
        return nodo14RO;
    }

    public void setNodo14RO(boolean nodo14RO) {
        this.nodo14RO = nodo14RO;
    }

    public boolean isNodo15RO() {
        return nodo15RO;
    }

    public void setNodo15RO(boolean nodo15RO) {
        this.nodo15RO = nodo15RO;
    }

    public boolean isNodo16RO() {
        return nodo16RO;
    }

    public void setNodo16RO(boolean nodo16RO) {
        this.nodo16RO = nodo16RO;
    }

    public Operandos getOperandoSeleccionado() {
        return operandoSeleccionado;
    }

    public void setOperandoSeleccionado(Operandos operandoSeleccionado) {
        this.operandoSeleccionado = operandoSeleccionado;
    }

    public Operadores getOperadorSeleccionado() {
        return operadorSeleccionado;
    }

    public void setOperadorSeleccionado(Operadores operadorSeleccionado) {
        this.operadorSeleccionado = operadorSeleccionado;
    }

    public List<Operadores> getListOperadores() {
        if (listOperadores == null) {
            listOperadores = administrarHistoriaFormula.listOperadores();
        }
        return listOperadores;
    }

    public void setListOperadores(List<Operadores> listOperadores) {
        this.listOperadores = listOperadores;
    }

    public List<Operandos> getListOperandos() {
        if (listOperandos == null) {
            listOperandos = administrarHistoriaFormula.listOperandos();
        }
        return listOperandos;
    }

    public void setListOperandos(List<Operandos> listOperandos) {
        this.listOperandos = listOperandos;
    }

    public List<Operadores> getFiltrarListOperadores() {
        return filtrarListOperadores;
    }

    public void setFiltrarListOperadores(List<Operadores> filtrarListOperadores) {
        this.filtrarListOperadores = filtrarListOperadores;
    }

    public List<Operandos> getFiltrarListOperandos() {
        return filtrarListOperandos;
    }

    public void setFiltrarListOperandos(List<Operandos> filtrarListOperandos) {
        this.filtrarListOperandos = filtrarListOperandos;
    }

    public List<Nodos> getListNodosCrear() {
        return listNodosCrear;
    }

    public void setListNodosCrear(List<Nodos> listNodosCrear) {
        this.listNodosCrear = listNodosCrear;
    }

    public List<Nodos> getListNodosBorrar() {
        return listNodosBorrar;
    }

    public void setListNodosBorrar(List<Nodos> listNodosBorrar) {
        this.listNodosBorrar = listNodosBorrar;
    }

    public List<Nodos> getListNodosModificar() {
        return listNodosModificar;
    }

    public void setListNodosModificar(List<Nodos> listNodosModificar) {
        this.listNodosModificar = listNodosModificar;
    }

    public List<Nodos> getListNodosParaExportar() {
        if (listNodosParaExportar == null) {
            listNodosParaExportar = new ArrayList<Nodos>();
            if (!listNodosHistoriaFormula.isEmpty()) {
                for (int i = 0; i < listNodosHistoriaFormula.size(); i++) {
                    if (listNodosHistoriaFormula.get(i).getOperador().getSecuencia() != null) {
                        String aux = listNodosHistoriaFormula.get(i).getOperador().getSigno();
                        listNodosHistoriaFormula.get(i).setDescripcionNodo(aux);
                        listNodosParaExportar.add(listNodosHistoriaFormula.get(i));
                    }
                    if (listNodosHistoriaFormula.get(i).getOperando().getSecuencia() != null) {
                        String aux = listNodosHistoriaFormula.get(i).getOperando().getNombre();
                        listNodosHistoriaFormula.get(i).setDescripcionNodo(aux);
                        listNodosParaExportar.add(listNodosHistoriaFormula.get(i));
                    }
                }
            }
        }
        return listNodosParaExportar;
    }

    public void setListNodosParaExportar(List<Nodos> listNodosParaExportar) {
        this.listNodosParaExportar = listNodosParaExportar;
    }

    public Nodos getEditarNodo() {
        return editarNodo;
    }

    public void setEditarNodo(Nodos editarNodo) {
        this.editarNodo = editarNodo;
    }

    public BigInteger getSecuenciaRegistroNodos() {
        return secuenciaRegistroNodos;
    }

    public void setSecuenciaRegistroNodos(BigInteger secuenciaRegistroNodos) {
        this.secuenciaRegistroNodos = secuenciaRegistroNodos;
    }

    public BigInteger getBackUpSecuenciaRegistroNodo() {
        return backUpSecuenciaRegistroNodo;
    }

    public void setBackUpSecuenciaRegistroNodo(BigInteger backUpSecuenciaRegistroNodo) {
        this.backUpSecuenciaRegistroNodo = backUpSecuenciaRegistroNodo;
    }

}

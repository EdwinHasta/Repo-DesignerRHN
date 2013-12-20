package Controlador;

import Entidades.Formulas;
import Entidades.Historiasformulas;
import Entidades.Nodos;
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
    private boolean permitirIndexHistoriaFormula;
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

    public ControlHistoriaFormula() {
        formulaCompleta = "";
        listNodosHistoriaFormula = null;
        formulaActual = new Formulas();
        listHistoriasFormulas = null;
        fechaParametro = new Date(1, 1, 0);
        permitirIndexHistoriaFormula = true;

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
    }

    public void recibirFormula(BigInteger secuencia) {
        formulaActual = administrarHistoriaFormula.actualFormula(secuencia);
        listHistoriasFormulas = null;
        indexHistoriasFormulas = 0;
        System.out.println("formulaActual : " + formulaActual.getSecuencia());
        getListHistoriasFormulas();
        getListNodosHistoriaFormula();
        getFormulaCompleta();
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
        if (permitirIndexHistoriaFormula == true) {
            System.out.println("Do it !");
            cualCeldaHistoriasFormulas = celda;
            indexHistoriasFormulas = indice;
            secRegistroHistoriaFormula = listHistoriasFormulas.get(indexHistoriasFormulas).getSecuencia();
            fechaIni = listHistoriasFormulas.get(indexHistoriasFormulas).getFechainicial();
            fechaFin = listHistoriasFormulas.get(indexHistoriasFormulas).getFechafinal();
            observacion = listHistoriasFormulas.get(indexHistoriasFormulas).getObservaciones();
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
        System.out.println("Bot !");
        if ((auxiliar.getFechainicial() != null) && (auxiliar.getFechafinal() != null)) {
            System.out.println("Something");
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
            System.out.println("Fail !");
            RequestContext context = RequestContext.getCurrentInstance();
            if (tipoListaHistoriasFormulas == 0) {
                System.out.println("Number one");
                listHistoriasFormulas.get(i).setFechainicial(fechaIni);
                listHistoriasFormulas.get(i).setFechafinal(fechaFin);
            }
            if (tipoListaHistoriasFormulas == 1) {
                System.out.println("Number two");
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

    //CANCELAR MODIFICACIONES
    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
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
        indexAuxHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
        k = 0;
        listHistoriasFormulas = null;
        guardado = true;
        cambiosHistoriaFormula = false;
        permitirIndexHistoriaFormula = true;
        getListHistoriasFormulas();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosHistoriaFormula");
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
    }

    public void ingresoNuevoRegistro() {
        validarIngresoNuevaHistoriaFormula();
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
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void validarBorradoRegistro() {
        if (indexHistoriasFormulas >= 0) {
            borrarHistoriaFormula();
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
        formulaCompleta = null;
    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
        nombreXML = "HistoriaFormula_XML";
        return nombreTabla;
    }

    /**
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
        nombreExportar = "HistoriaFormula_PDF";
        exportPDF_Tabla();
        indexHistoriasFormulas = -1;
        indexAuxHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;

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
        nombreTabla = ":formExportarHistoria:datosHistoriaFormulasExportar";
        nombreExportar = "HistoriaFormula_XLS";
        exportXLS_Tabla();
        indexHistoriasFormulas = -1;
        indexAuxHistoriasFormulas = -1;
        secRegistroHistoriaFormula = null;
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
        verificarRastroHistoriaFormula();
        indexHistoriasFormulas = -1;
        indexAuxHistoriasFormulas = -1;

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

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
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
            listNodosHistoriaFormula = administrarHistoriaFormula.listNodosDeHistoriaFormula(listHistoriasFormulas.get(indexHistoriasFormulas).getSecuencia());
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
                if (listNodosHistoriaFormula.get(i).getOperador() != null) {
                    formulaCompleta = formulaCompleta + listNodosHistoriaFormula.get(i).getOperador().getSigno()+" ";
                    System.out.println(" --!!-- i : " + i);
                }
                if (listNodosHistoriaFormula.get(i).getOperando() != null) {
                    formulaCompleta = formulaCompleta + listNodosHistoriaFormula.get(i).getOperando().getNombre()+" ";
                    System.out.println(" !!--!! i : " + i);
                }
            }
        }
        System.out.println("formulaCompleta : " + formulaCompleta);
        return formulaCompleta;
    }

    public void setFormulaCompleta(String formulaCompleta) {
        this.formulaCompleta = formulaCompleta;
    }

}

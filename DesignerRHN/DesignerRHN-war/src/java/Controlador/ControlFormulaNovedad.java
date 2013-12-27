package Controlador;

import Entidades.Formulas;
import Entidades.FormulasNovedades;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarFormulaNovedadInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
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
public class ControlFormulaNovedad implements Serializable {

    @EJB
    AdministrarFormulaNovedadInterface administrarFormulaNovedad;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<FormulasNovedades> listFormulasNovedades;
    private List<FormulasNovedades> filtrarListFormulasNovedades;
    private Formulas formulaActual;
    ///////
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column formulaCorto, formulaNombre, formulaSugerida, formulaCargue, formulaUsa, formulaGarantiza;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<FormulasNovedades> listFormulasNovedadesModificar;
    private boolean guardado;
    //crear 
    private FormulasNovedades nuevoFormulaNovedad;
    private List<FormulasNovedades> listFormulasNovedadesCrear;
    private BigInteger l;
    private int k;
    //borrar 
    private List<FormulasNovedades> listFormulasNovedadesBorrar;
    //editar celda
    private FormulasNovedades editarFormulaNovedad;
    private int cualCelda, tipoLista;
    //duplicar
    private FormulasNovedades duplicarFormulaNovedad;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;
    private String formula;

    //////////////////////
    private List<Formulas> lovFormulas;
    private List<Formulas> filtrarLovFormulas;
    private Formulas formulaSeleccionada;

    private boolean permitirIndex;
    private int tipoActualizacion;
    private String auxFormulaDescripcion;

    public ControlFormulaNovedad() {
        auxFormulaDescripcion = "";
        permitirIndex = true;
        tipoActualizacion = -1;
        lovFormulas = null;
        formulaSeleccionada = new Formulas();
        backUpSecRegistro = null;
        listFormulasNovedades = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listFormulasNovedadesBorrar = new ArrayList<FormulasNovedades>();
        //crear aficiones
        listFormulasNovedadesCrear = new ArrayList<FormulasNovedades>();
        k = 0;
        //modificar aficiones
        listFormulasNovedadesModificar = new ArrayList<FormulasNovedades>();
        //editar
        editarFormulaNovedad = new FormulasNovedades();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoFormulaNovedad = new FormulasNovedades();
        nuevoFormulaNovedad.setFormula(new Formulas());
        duplicarFormulaNovedad = new FormulasNovedades();
        secRegistro = null;
        formulaActual = new Formulas();
    }

    public void recibirFormula(BigInteger secuencia) {
        formulaActual = administrarFormulaNovedad.formulaActual(secuencia);
        listFormulasNovedades = getListFormulasNovedades();
        System.out.println("formulaActual : " + formulaActual.getSecuencia());
    }

    public void modificacionesCamposFormula(int indice) {
        if (tipoLista == 0) {
            listFormulasNovedades.get(indice).getFormula().setNombrelargo(auxFormulaDescripcion);
        }
        if (tipoLista == 1) {
            listFormulasNovedades.get(indice).getFormula().setNombrelargo(auxFormulaDescripcion);
        }
        modificarFormulaNovedad(indice);
    }

    public void modificarFormulaNovedad(int indice) {
        if (tipoLista == 0) {
            if (!listFormulasNovedadesCrear.contains(listFormulasNovedades.get(indice))) {
                if (listFormulasNovedadesModificar.isEmpty()) {
                    listFormulasNovedadesModificar.add(listFormulasNovedades.get(indice));
                } else if (!listFormulasNovedadesModificar.contains(listFormulasNovedades.get(indice))) {
                    listFormulasNovedadesModificar.add(listFormulasNovedades.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
        }
        if (tipoLista == 1) {
            if (!listFormulasNovedadesCrear.contains(filtrarListFormulasNovedades.get(indice))) {
                if (listFormulasNovedadesModificar.isEmpty()) {
                    listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(indice));
                } else if (!listFormulasNovedadesModificar.contains(filtrarListFormulasNovedades.get(indice))) {
                    listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        index = -1;
        secRegistro = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaNovedad");

    }

    public void modificarFormulaNovedad(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoLista == 0) {
                listFormulasNovedades.get(indice).getFormula().setNombrecorto(formula);
            } else {
                filtrarListFormulasNovedades.get(indice).getFormula().setNombrecorto(formula);
            }
            for (int i = 0; i < lovFormulas.size(); i++) {
                if (lovFormulas.get(i).getNombrecorto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listFormulasNovedades.get(indice).setFormula(lovFormulas.get(indiceUnicoElemento));
                } else {
                    filtrarListFormulasNovedades.get(indice).setFormula(lovFormulas.get(indiceUnicoElemento));
                }
                lovFormulas.clear();
                getLovFormulas();
            } else {
                permitirIndex = false;
                context.update("form:FormulaDialogo");
                context.execute("FormulaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listFormulasNovedadesCrear.contains(listFormulasNovedades.get(indice))) {
                    if (listFormulasNovedadesModificar.isEmpty()) {
                        listFormulasNovedadesModificar.add(listFormulasNovedades.get(indice));
                    } else if (!listFormulasNovedadesModificar.contains(listFormulasNovedades.get(indice))) {
                        listFormulasNovedadesModificar.add(listFormulasNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
            }
            if (tipoLista == 1) {
                if (!listFormulasNovedadesCrear.contains(filtrarListFormulasNovedades.get(indice))) {
                    if (listFormulasNovedadesModificar.isEmpty()) {
                        listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(indice));
                    } else if (!listFormulasNovedadesModificar.contains(filtrarListFormulasNovedades.get(indice))) {
                        listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            }
            guardado = true;
        }
        context.update("form:datosFormulaNovedad");
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            if (tipoLista == 0) {
                auxFormulaDescripcion = listFormulasNovedades.get(index).getFormula().getNombrelargo();
                secRegistro = listFormulasNovedades.get(index).getSecuencia();
                formula = listFormulasNovedades.get(index).getFormula().getNombrecorto();
            }
            if (tipoLista == 1) {
                auxFormulaDescripcion = filtrarListFormulasNovedades.get(index).getFormula().getNombrelargo();
                secRegistro = filtrarListFormulasNovedades.get(index).getSecuencia();
                formula = filtrarListFormulasNovedades.get(index).getFormula().getNombrecorto();
            }
        }
    }

    //GUARDAR
    public void guardarGeneral() {
        guardarCambiosFormulaNovedad();
        FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarCambiosFormulaNovedad() {
        if (guardado == false) {
            if (!listFormulasNovedadesBorrar.isEmpty()) {
                for (int i = 0; i < listFormulasNovedadesBorrar.size(); i++) {
                    administrarFormulaNovedad.borrarFormulasNovedades(listFormulasNovedadesBorrar);
                }
                listFormulasNovedadesBorrar.clear();
            }
            if (!listFormulasNovedadesCrear.isEmpty()) {
                for (int i = 0; i < listFormulasNovedadesCrear.size(); i++) {
                    administrarFormulaNovedad.crearFormulasNovedades(listFormulasNovedadesCrear);
                }
                listFormulasNovedadesCrear.clear();
            }
            if (!listFormulasNovedadesModificar.isEmpty()) {
                administrarFormulaNovedad.editarFormulasNovedades(listFormulasNovedadesModificar);
                listFormulasNovedadesModificar.clear();
            }
            listFormulasNovedades = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaNovedad");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
        secRegistro = null;

    }

    public void cancelarModificacionGeneral() {
        if (guardado == false) {
            cancelarModificacionFormulaNovedad();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaNovedad");
        }
    }

    public void cancelarModificacionFormulaNovedad() {
        if (bandera == 1) {
            formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
            formulaCorto.setFilterStyle("display: none; visibility: hidden;");
            formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
            formulaNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
            formulaUsa.setFilterStyle("display: none; visibility: hidden;");
            formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
            formulaGarantiza.setFilterStyle("display: none; visibility: hidden;");
            formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
            formulaSugerida.setFilterStyle("display: none; visibility: hidden;");
            formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
            formulaCargue.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
            bandera = 0;
            filtrarListFormulasNovedades = null;
            tipoLista = 0;
        }
        listFormulasNovedadesBorrar.clear();
        listFormulasNovedadesCrear.clear();
        listFormulasNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listFormulasNovedades = null;
        guardado = true;
        permitirIndex = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosFormulaNovedad");
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarFormulaNovedad = listFormulasNovedades.get(index);
            }
            if (tipoLista == 1) {
                editarFormulaNovedad = filtrarListFormulasNovedades.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFormulaCortoD");
                context.execute("editarFormulaCortoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFormulaNombreD");
                context.execute("editarFormulaNombreD.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
        }
    }

    public void dialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formularioDialogos:NuevoRegistroFormula");
        context.execute("NuevoRegistroFormula.show()");
    }

    //CREAR 
    public void agregarNuevoFormulaNovedad() {
        if (nuevoFormulaNovedad.getFormula().getSecuencia() != null) {
            if (bandera == 1) {
                formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
                formulaCorto.setFilterStyle("display: none; visibility: hidden;");
                formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
                formulaNombre.setFilterStyle("display: none; visibility: hidden;");
                formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
                formulaUsa.setFilterStyle("display: none; visibility: hidden;");
                formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
                formulaGarantiza.setFilterStyle("display: none; visibility: hidden;");
                formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
                formulaSugerida.setFilterStyle("display: none; visibility: hidden;");
                formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
                formulaCargue.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
                bandera = 0;
                filtrarListFormulasNovedades = null;
                tipoLista = 0;
            }

            k++;
            l = BigInteger.valueOf(k);
            nuevoFormulaNovedad.setSecuencia(l);
            listFormulasNovedadesCrear.add(nuevoFormulaNovedad);
            listFormulasNovedades.add(nuevoFormulaNovedad);
            nuevoFormulaNovedad = new FormulasNovedades();
            nuevoFormulaNovedad.setFormula(new Formulas());
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaNovedad");
            context.execute("NuevoRegistroFormula.hide()");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            index = -1;
            secRegistro = null;
        } else {
            RequestContext.getCurrentInstance().execute("errorRegistroFN.show()");
        }
    }

    //LIMPIAR NUEVO REGISTRO
    /**
     */
    public void limpiarNuevaFormulaNovedad() {
        nuevoFormulaNovedad = new FormulasNovedades();
        nuevoFormulaNovedad.setFormula(new Formulas());
        index = -1;
        secRegistro = null;
    }

//DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
        if (index >= 0) {
            duplicarFormulaNovedadM();
        }
    }

    public void duplicarFormulaNovedadM() {
        if (index >= 0) {
            duplicarFormulaNovedad = new FormulasNovedades();
            k++;
            l = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarFormulaNovedad.setSecuencia(l);
                duplicarFormulaNovedad.setFormula(listFormulasNovedades.get(index).getFormula());
                duplicarFormulaNovedad.setGarantizada(listFormulasNovedades.get(index).getGarantizada());
                duplicarFormulaNovedad.setUsaordenformulaconcepto(listFormulasNovedades.get(index).getUsaordenformulaconcepto());
                duplicarFormulaNovedad.setSugerida(listFormulasNovedades.get(index).getSugerida());
                duplicarFormulaNovedad.setCargue(listFormulasNovedades.get(index).getCargue());
            }
            if (tipoLista == 1) {
                duplicarFormulaNovedad.setSecuencia(l);
                duplicarFormulaNovedad.setFormula(filtrarListFormulasNovedades.get(index).getFormula());
                duplicarFormulaNovedad.setGarantizada(filtrarListFormulasNovedades.get(index).getGarantizada());
                duplicarFormulaNovedad.setUsaordenformulaconcepto(filtrarListFormulasNovedades.get(index).getUsaordenformulaconcepto());
                duplicarFormulaNovedad.setSugerida(filtrarListFormulasNovedades.get(index).getSugerida());
                duplicarFormulaNovedad.setCargue(filtrarListFormulasNovedades.get(index).getCargue());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroFormulaNovedad");
            context.execute("DuplicarRegistroFormulaNovedad.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicarFormulaNovedad() {
        if (duplicarFormulaNovedad.getFormula().getSecuencia() != null) {
            listFormulasNovedades.add(duplicarFormulaNovedad);
            listFormulasNovedadesCrear.add(duplicarFormulaNovedad);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaNovedad");
            context.execute("DuplicarRegistroFormulaNovedad.hide()");
            index = -1;
            secRegistro = null;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
                formulaCorto.setFilterStyle("display: none; visibility: hidden;");
                formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
                formulaNombre.setFilterStyle("display: none; visibility: hidden;");
                formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
                formulaUsa.setFilterStyle("display: none; visibility: hidden;");
                formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
                formulaGarantiza.setFilterStyle("display: none; visibility: hidden;");
                formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
                formulaSugerida.setFilterStyle("display: none; visibility: hidden;");
                formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
                formulaCargue.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
                bandera = 0;
                filtrarListFormulasNovedades = null;
                tipoLista = 0;
            }
            duplicarFormulaNovedad = new FormulasNovedades();
        } else {
            RequestContext.getCurrentInstance().execute("errorRegistroFN.show()");
        }
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Set
     */
    public void limpiarDuplicarFormulaNovedad() {
        duplicarFormulaNovedad = new FormulasNovedades();
        duplicarFormulaNovedad.setFormula(new Formulas());
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
            borrarFormulaNovedad();
        }
    }

    public void borrarFormulaNovedad() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listFormulasNovedadesModificar.isEmpty() && listFormulasNovedadesModificar.contains(listFormulasNovedades.get(index))) {
                    int modIndex = listFormulasNovedadesModificar.indexOf(listFormulasNovedades.get(index));
                    listFormulasNovedadesModificar.remove(modIndex);
                    listFormulasNovedadesBorrar.add(listFormulasNovedades.get(index));
                } else if (!listFormulasNovedadesCrear.isEmpty() && listFormulasNovedadesCrear.contains(listFormulasNovedades.get(index))) {
                    int crearIndex = listFormulasNovedadesCrear.indexOf(listFormulasNovedades.get(index));
                    listFormulasNovedadesCrear.remove(crearIndex);
                } else {
                    listFormulasNovedadesBorrar.add(listFormulasNovedades.get(index));
                }
                listFormulasNovedades.remove(index);
            }
            if (tipoLista == 1) {
                if (!listFormulasNovedadesModificar.isEmpty() && listFormulasNovedadesModificar.contains(filtrarListFormulasNovedades.get(index))) {
                    int modIndex = listFormulasNovedadesModificar.indexOf(filtrarListFormulasNovedades.get(index));
                    listFormulasNovedadesModificar.remove(modIndex);
                    listFormulasNovedadesBorrar.add(filtrarListFormulasNovedades.get(index));
                } else if (!listFormulasNovedadesCrear.isEmpty() && listFormulasNovedadesCrear.contains(filtrarListFormulasNovedades.get(index))) {
                    int crearIndex = listFormulasNovedadesCrear.indexOf(filtrarListFormulasNovedades.get(index));
                    listFormulasNovedadesCrear.remove(crearIndex);
                } else {
                    listFormulasNovedadesBorrar.add(filtrarListFormulasNovedades.get(index));
                }
                int VCIndex = listFormulasNovedades.indexOf(filtrarListFormulasNovedades.get(index));
                listFormulasNovedades.remove(VCIndex);
                filtrarListFormulasNovedades.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosFormulaNovedad");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
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
        if (bandera == 0) {
            formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
            formulaCorto.setFilterStyle("width: 125px");
            formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
            formulaNombre.setFilterStyle("width: 125px");
            formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
            formulaUsa.setFilterStyle("width: 10px");
            formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
            formulaGarantiza.setFilterStyle("width: 10px");
            formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
            formulaSugerida.setFilterStyle("width: 10px");
            formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
            formulaCargue.setFilterStyle("width: 10px");
            RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
            bandera = 1;
        } else if (bandera == 1) {
            formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
            formulaCorto.setFilterStyle("display: none; visibility: hidden;");
            formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
            formulaNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
            formulaUsa.setFilterStyle("display: none; visibility: hidden;");
            formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
            formulaGarantiza.setFilterStyle("display: none; visibility: hidden;");
            formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
            formulaSugerida.setFilterStyle("display: none; visibility: hidden;");
            formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
            formulaCargue.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
            bandera = 0;
            filtrarListFormulasNovedades = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            formulaCorto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCorto");
            formulaCorto.setFilterStyle("display: none; visibility: hidden;");
            formulaNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaNombre");
            formulaNombre.setFilterStyle("display: none; visibility: hidden;");
            formulaUsa = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaUsa");
            formulaUsa.setFilterStyle("display: none; visibility: hidden;");
            formulaGarantiza = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaGarantiza");
            formulaGarantiza.setFilterStyle("display: none; visibility: hidden;");
            formulaSugerida = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaSugerida");
            formulaSugerida.setFilterStyle("display: none; visibility: hidden;");
            formulaCargue = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosFormulaNovedad:formulaCargue");
            formulaCargue.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosFormulaNovedad");
            bandera = 0;
            filtrarListFormulasNovedades = null;
            tipoLista = 0;
        }
        listFormulasNovedadesBorrar.clear();
        listFormulasNovedadesCrear.clear();
        listFormulasNovedadesModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listFormulasNovedades = null;
        guardado = true;
        formulaActual = null;
        lovFormulas = null;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
            if (cualCelda == 0) {
                context.update("form:FormulaDialogo");
                context.execute("FormulaDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    public void asignarIndex(Integer indice, int dlg, int LND) {
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            index = indice;
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        if (dlg == 0) {
            context.update("form:FormulaDialogo");
            context.execute("FormulaDialogo.show()");
        }

    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("FORMULA")) {
            if (tipoNuevo == 1) {
                formula = nuevoFormulaNovedad.getFormula().getNombrecorto();
            } else if (tipoNuevo == 2) {
                formula = duplicarFormulaNovedad.getFormula().getNombrecorto();
            }
        }
    }

    public void autocompletarNuevoyDuplicadoFormulaNovedad(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("FORMULA")) {
            if (tipoNuevo == 1) {
                nuevoFormulaNovedad.getFormula().setNombrecorto(formula);
            } else if (tipoNuevo == 2) {
                duplicarFormulaNovedad.getFormula().setNombrecorto(formula);
            }
            for (int i = 0; i < lovFormulas.size(); i++) {
                if (lovFormulas.get(i).getNombrecorto().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevoFormulaNovedad.setFormula(lovFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoFormulaCorto");
                    context.update("formularioDialogos:nuevoFormulaDescripcion");
                } else if (tipoNuevo == 2) {
                    duplicarFormulaNovedad.setFormula(lovFormulas.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarFormulaCorto");
                    context.update("formularioDialogos:duplicarFormulaDescripcion");
                }
                lovFormulas.clear();
                getLovFormulas();
            } else {
                context.update("form:FormulaDialogo");
                context.execute("FormulaDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoFormulaCorto");
                    context.update("formularioDialogos:nuevoFormulaDescripcion");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarFormulaCorto");
                    context.update("formularioDialogos:duplicarFormulaDescripcion");
                }
            }
        }
    }

    public void actualizarFormula() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listFormulasNovedades.get(index).setFormula(formulaSeleccionada);
                if (!listFormulasNovedadesCrear.contains(listFormulasNovedades.get(index))) {
                    if (listFormulasNovedadesModificar.isEmpty()) {
                        listFormulasNovedadesModificar.add(listFormulasNovedades.get(index));
                    } else if (!listFormulasNovedadesModificar.contains(listFormulasNovedades.get(index))) {
                        listFormulasNovedadesModificar.add(listFormulasNovedades.get(index));
                    }
                }
            } else {
                filtrarListFormulasNovedades.get(index).setFormula(formulaSeleccionada);
                if (!listFormulasNovedadesCrear.contains(filtrarListFormulasNovedades.get(index))) {
                    if (listFormulasNovedadesModificar.isEmpty()) {
                        listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(index));
                    } else if (!listFormulasNovedadesModificar.contains(filtrarListFormulasNovedades.get(index))) {
                        listFormulasNovedadesModificar.add(filtrarListFormulasNovedades.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update(":form:formulaCorto");
            context.update(":form:formulaNombre");
        } else if (tipoActualizacion == 1) {
            nuevoFormulaNovedad.setFormula(formulaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevoFormulaCorto");
            context.update("formularioDialogos:nuevoFormulaDescripcion");
        } else if (tipoActualizacion == 2) {
            duplicarFormulaNovedad.setFormula(formulaSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarFormulaCorto");
            context.update("formularioDialogos:duplicarFormulaDescripcion");
        }
        filtrarLovFormulas = null;
        formulaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    public void cancelarCambioFormula() {
        filtrarLovFormulas = null;
        formulaSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    public String exportXML() {
        nombreTabla = ":formExportarFormula:datosFormulaNovedadExportar";
        nombreXML = "FormulaNovedad_XML";
        return nombreTabla;
    }

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_NF();
    }

    public void exportPDF_NF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarFormula:datosFormulaNovedadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "FormulaNovedad_PDF", false, false, "UTF-8", null, null);
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
        exportXLS_NF();
    }

    public void exportXLS_NF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarFormula:datosFormulaNovedadExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "FormulaNovedad_XLS", false, false, "UTF-8", null, null);
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
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        verificarRastroFormulaNovedad();
        index = -1;
    }

    public void verificarRastroFormulaNovedad() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listFormulasNovedades != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "FORMULASNOVEDADES");
                backUpSecRegistro = secRegistro;
                backUp = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "FormulasNovedades";
                    msnConfirmarRastro = "La tabla FORMULASNOVEDADES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("FORMULASNOVEDADES")) {
                nombreTablaRastro = "FormulasNovedades";
                msnConfirmarRastroHistorico = "La tabla FORMULASNOVEDADES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    //GETTERS AND SETTERS
    public List<FormulasNovedades> getListFormulasNovedades() {
        try {
            if (listFormulasNovedades == null) {
                listFormulasNovedades = new ArrayList<FormulasNovedades>();
                listFormulasNovedades = administrarFormulaNovedad.listFormulasNovedadesParaFormula(formulaActual.getSecuencia());
                return listFormulasNovedades;
            } else {
                return listFormulasNovedades;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListFormulasNovedades " + e.toString());
            return null;
        }
    }

    public void setListFormulasNovedades(List<FormulasNovedades> setListFormulasNovedades) {
        this.listFormulasNovedades = setListFormulasNovedades;
    }

    public List<FormulasNovedades> getFiltrarListFormulasNovedades() {
        return filtrarListFormulasNovedades;
    }

    public void setFiltrarListFormulasNovedades(List<FormulasNovedades> setFiltrarListFormulasNovedades) {
        this.filtrarListFormulasNovedades = setFiltrarListFormulasNovedades;
    }

    public FormulasNovedades getNuevoFormulaNovedad() {
        return nuevoFormulaNovedad;
    }

    public void setNuevoFormulaNovedad(FormulasNovedades setNuevoFormulaNovedad) {
        this.nuevoFormulaNovedad = setNuevoFormulaNovedad;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public FormulasNovedades getEditarFormulaNovedad() {
        return editarFormulaNovedad;
    }

    public void setEditarFormulaNovedad(FormulasNovedades setEditarFormulaNovedad) {
        this.editarFormulaNovedad = setEditarFormulaNovedad;
    }

    public FormulasNovedades getDuplicarFormulaNovedad() {
        return duplicarFormulaNovedad;
    }

    public void setDuplicarFormulaNovedad(FormulasNovedades setDuplicarFormulaNovedad) {
        this.duplicarFormulaNovedad = setDuplicarFormulaNovedad;
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

    public List<FormulasNovedades> getListFormulasNovedadesModificar() {
        return listFormulasNovedadesModificar;
    }

    public void setListFormulasNovedadesModificar(List<FormulasNovedades> setListFormulasNovedadesModificar) {
        this.listFormulasNovedadesModificar = setListFormulasNovedadesModificar;
    }

    public List<FormulasNovedades> getListFormulasNovedadesCrear() {
        return listFormulasNovedadesCrear;
    }

    public void setListFormulasNovedadesCrear(List<FormulasNovedades> setListFormulasNovedadesCrear) {
        this.listFormulasNovedadesCrear = setListFormulasNovedadesCrear;
    }

    public List<FormulasNovedades> getListFormulasNovedadesBorrar() {
        return listFormulasNovedadesBorrar;
    }

    public void setListFormulasNovedadesBorrar(List<FormulasNovedades> setListFormulasNovedadesBorrar) {
        this.listFormulasNovedadesBorrar = setListFormulasNovedadesBorrar;
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

    public List<Formulas> getLovFormulas() {
        if (lovFormulas == null) {
            lovFormulas = administrarFormulaNovedad.listFormulas(formulaActual.getSecuencia());
        }
        return lovFormulas;
    }

    public void setLovFormulas(List<Formulas> setLovFormulas) {
        this.lovFormulas = setLovFormulas;
    }

    public List<Formulas> getFiltrarLovFormulas() {
        return filtrarLovFormulas;
    }

    public void setFiltrarLovFormulas(List<Formulas> setFiltrarLovFormulas) {
        this.filtrarLovFormulas = setFiltrarLovFormulas;
    }

    public Formulas getFormulaSeleccionada() {
        return formulaSeleccionada;
    }

    public void setFormulaSeleccionada(Formulas tipoDiaSeleccionado) {
        this.formulaSeleccionada = tipoDiaSeleccionado;
    }

    public Formulas getFormulaActual() {
        return formulaActual;
    }

    public void setFormulaActual(Formulas formulaActual) {
        this.formulaActual = formulaActual;
    }

}

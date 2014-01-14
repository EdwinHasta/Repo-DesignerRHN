/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.ConceptosJuridicos;
import Entidades.Empresas;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarConceptoJuridicoInterface;
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
 * @author user
 */
@ManagedBean
@SessionScoped
public class ControlConceptoJuridico implements Serializable {

    @EJB
    AdministrarConceptoJuridicoInterface administrarConceptoJuridico;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //
    private List<ConceptosJuridicos> listConceptosJuridicos;
    private List<ConceptosJuridicos> filtrarListConceptosJuridicos;
    //
    private List<Empresas> listEmpresas;
    private List<Empresas> filtrarListEmpresas;
    private Empresas empresaActual;
    private Empresas empresita;
    //

    private int bandera;
    private Empresas backUpEmpresaActual;
    private Column conceptoFecha, conceptoNombre, conceptoCargo;
    //Otros
    private boolean aceptar, readOnlyTexto;
    private int index;
    private List<ConceptosJuridicos> listConceptosJuridicosModificar;
    private boolean guardado;
    public ConceptosJuridicos nuevoConcepto;
    private List<ConceptosJuridicos> listConceptosJuridicosCrear;
    private BigInteger l;
    private int k;
    private List<ConceptosJuridicos> listConceptosJuridicosBorrar;
    private ConceptosJuridicos editarConcepto;
    private int cualCelda, tipoLista;
    private ConceptosJuridicos duplicarConcepto;
    private String nombreXML;
    private String nombreTabla;
    private boolean cambiosConceptos;
    private BigInteger secRegistroConcepto;
    private BigInteger backUpSecRegistroConceptos;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private int indexAux;
    private boolean cambioTextNormativo;
    private String textoNormativo, textoNormaAux;
    private String auxExpPor, auxQuien;
    private Date auxFecha;

    public ControlConceptoJuridico() {

        cambioTextNormativo = true;
        indexAux = 0;
        backUpEmpresaActual = new Empresas();
        empresaActual = new Empresas();
        listEmpresas = null;
        nombreTablaRastro = "";
        backUp = null;
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        secRegistroConcepto = null;
        backUpSecRegistroConceptos = null;
        listConceptosJuridicos = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listConceptosJuridicosBorrar = new ArrayList<ConceptosJuridicos>();
        //crear aficiones
        listConceptosJuridicosCrear = new ArrayList<ConceptosJuridicos>();
        k = 0;
        //modificar aficiones
        listConceptosJuridicosModificar = new ArrayList<ConceptosJuridicos>();
        //editar
        editarConcepto = new ConceptosJuridicos();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoConcepto = new ConceptosJuridicos();
        index = -1;
        bandera = 0;

        nombreTabla = ":formExportarConceptos:datosConceptosExportar";
        nombreXML = "ConceptosXML";

        duplicarConcepto = new ConceptosJuridicos();
        cambiosConceptos = false;
    }

    public void posicionTextoNormativo(int celda) {
        index = indexAux;
        if (index >= 0) {
            cualCelda = celda;
            secRegistroConcepto = listConceptosJuridicos.get(index).getSecuencia();
        }
    }

    public void modificarTextoNormativo() {
        if (!textoNormativo.isEmpty()) {
            if (index >= 0) {
                modificarConcepto(index);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formTexto:editarTexto");
                cambioTextNormativo = false;
            }
        } else {
            textoNormativo = textoNormaAux;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formTexto:editarTexto");
            context.execute("errorRegNuevo.show()");
            index = -1;
        }
    }

    public void modificarConcepto(int indice) {
        boolean retorno = validarNuevosDatosRegistro(0);
        if (retorno == true) {
            if (tipoLista == 0) {
                listConceptosJuridicos.get(indice).setTexto(textoNormativo);
                if (!listConceptosJuridicosCrear.contains(listConceptosJuridicos.get(indice))) {
                    if (listConceptosJuridicosModificar.isEmpty()) {
                        listConceptosJuridicosModificar.add(listConceptosJuridicos.get(indice));
                    } else if (!listConceptosJuridicosModificar.contains(listConceptosJuridicos.get(indice))) {
                        listConceptosJuridicosModificar.add(listConceptosJuridicos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroConcepto = null;
            } else {
                filtrarListConceptosJuridicos.get(indice).setTexto(textoNormativo);
                if (!listConceptosJuridicosCrear.contains(filtrarListConceptosJuridicos.get(indice))) {
                    if (listConceptosJuridicosModificar.isEmpty()) {
                        listConceptosJuridicosModificar.add(filtrarListConceptosJuridicos.get(indice));
                    } else if (!listConceptosJuridicosModificar.contains(filtrarListConceptosJuridicos.get(indice))) {
                        listConceptosJuridicosModificar.add(filtrarListConceptosJuridicos.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistroConcepto = null;
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConcepto");
            cambiosConceptos = true;
        } else {
            listConceptosJuridicos.get(index).setExpedidopor(auxExpPor);
            listConceptosJuridicos.get(index).setQuien(auxQuien);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConcepto");
            context.execute("errorRegNuevo.show()");
            index = -1;
        }
    }

    public void cambiarIndice(int indice, int celda) {
        if (cambioTextNormativo == true) {
            cualCelda = celda;
            index = indice;
            indexAux = indice;
            auxFecha = listConceptosJuridicos.get(index).getFecha();
            secRegistroConcepto = listConceptosJuridicos.get(index).getSecuencia();
            /////
            textoNormativo = listConceptosJuridicos.get(index).getTexto();
            textoNormaAux = listConceptosJuridicos.get(index).getTexto();
            /////
            auxExpPor = listConceptosJuridicos.get(index).getExpedidopor();
            auxQuien = listConceptosJuridicos.get(index).getQuien();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formTexto:editarTexto");
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void modificarFecha(int i, int c) {
        if (listConceptosJuridicos.get(i).getFecha() != null) {
            cambiarIndice(i, c);
            modificarConcepto(i);
            indexAux = i;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            listConceptosJuridicos.get(index).setFecha(auxFecha);
            context.update("form:datosConcepto");
            context.execute("errorRegNuevo.show()");
            index = -1;
        }
    }
    //GUARDAR
    /**
     * Metodo de guardado general para la pagina
     */
    public void guardadoGeneral() {
        if (cambiosConceptos == true) {
            guardarCambiosConcepto();
        }
        guardado = true;
        RequestContext.getCurrentInstance().update("form:aceptar");
    }

    /**
     * Metodo que guarda los cambios efectuados en la pagina
     * VigenciasLocalizaciones
     */
    public void guardarCambiosConcepto() {
        if (guardado == false) {
            FacesMessage msg = new FacesMessage("Información", "Los datos se guardaron con Éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            if (!listConceptosJuridicosBorrar.isEmpty()) {
                administrarConceptoJuridico.borrarConceptosJuridicos(listConceptosJuridicosBorrar);
                listConceptosJuridicosBorrar.clear();
            }
            if (!listConceptosJuridicosCrear.isEmpty()) {
                administrarConceptoJuridico.crearConceptosJuridicos(listConceptosJuridicosCrear);
                listConceptosJuridicosCrear.clear();
            }
            if (!listConceptosJuridicosModificar.isEmpty()) {
                administrarConceptoJuridico.editarConceptosJuridicos(listConceptosJuridicosModificar);
                listConceptosJuridicosModificar.clear();
            }
            listConceptosJuridicos = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConcepto");
            context.update("form:growl");
            k = 0;
        }
        cambioTextNormativo = true;
        index = -1;
        secRegistroConcepto = null;
        cambiosConceptos = false;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    
    public void refrescarTexto(){
        textoNormaAux = listConceptosJuridicos.get(indexAux).getTexto();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("formTexto:editarTexto");
    }
    
    public void cancelarModificacion() {
        if (bandera == 1) {
            conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
            conceptoFecha.setFilterStyle("display: none; visibility: hidden;");
            conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
            conceptoNombre.setFilterStyle("display: none; visibility: hidden;");
            conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
            conceptoCargo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosConcepto");
            bandera = 0;
            filtrarListConceptosJuridicos = null;
            tipoLista = 0;
        }
        listConceptosJuridicosBorrar.clear();
        listConceptosJuridicosCrear.clear();
        listConceptosJuridicosModificar.clear();
        index = -1;
        indexAux = -1;
        secRegistroConcepto = null;
        k = 0;
        listConceptosJuridicos = null;
        guardado = true;
        cambiosConceptos = false;
        getListConceptosJuridicos();
        textoNormativo = "";
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosConcepto");
        context.update("formTexto:editarTexto");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (cualCelda == 0 || cualCelda == 3) {
            index = indexAux;
        }
        if (index >= 0) {
            if (tipoLista == 0) {
                editarConcepto = listConceptosJuridicos.get(index);
            }
            if (tipoLista == 1) {
                editarConcepto = filtrarListConceptosJuridicos.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaCD");
                context.execute("editarFechaCD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarNombreCD");
                context.execute("editarNombreCD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarCargoCD");
                context.execute("editarCargoCD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editaTextoD");
                context.execute("editaTextoD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistroConcepto = null;
    }

    public void validarIngresoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        limpiarNuevoConcepto();
        context.update("form:nuevaC");
        context.update("formularioDialogos:NuevoRegistroConcepto");
        context.execute("NuevoRegistroConcepto.show()");

    }

    public void validarDuplicadoRegistro() {
        if (cualCelda == 0) {
            index = indexAux;
        }
        if (index >= 0) {
            duplicarConceptoM();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public void validarBorradoRegistro() {
        if (cualCelda == 0) {
            index = indexAux;
        }
        if (index >= 0) {
            borrarConcepto();
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("seleccionarRegistro.show()");
        }
    }

    public boolean validarBorrado() {
        String text = listConceptosJuridicos.get(index).getTexto();
        if (text.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean validarNuevosDatosRegistro(int i) {
        boolean retorno = false;
        if (i == 0) {
            ConceptosJuridicos aux = listConceptosJuridicos.get(index);
            if ((!aux.getExpedidopor().isEmpty()) && (aux.getFecha() != null) && (!aux.getQuien().isEmpty())) {
                return true;
            }
        }
        if (i == 1) {
            if ((nuevoConcepto.getFecha() != null) && (!nuevoConcepto.getExpedidopor().isEmpty()) && (!nuevoConcepto.getQuien().isEmpty()) && (!nuevoConcepto.getTexto().isEmpty())) {
                retorno = true;
            }
        }
        if (i == 2) {
            if ((duplicarConcepto.getFecha() != null) && (!duplicarConcepto.getExpedidopor().isEmpty()) && (!duplicarConcepto.getQuien().isEmpty()) && (!duplicarConcepto.getTexto().isEmpty())) {
                retorno = true;
            }
        }
        return retorno;
    }

    public void agregarNuevoConcepto() {
        boolean resp = validarNuevosDatosRegistro(1);
        if (resp == true) {
            if (bandera == 1) {
                conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
                conceptoFecha.setFilterStyle("display: none; visibility: hidden;");
                conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
                conceptoNombre.setFilterStyle("display: none; visibility: hidden;");
                conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
                conceptoCargo.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosConcepto");
                bandera = 0;
                filtrarListConceptosJuridicos = null;
                tipoLista = 0;
            }
            k++;
            String aux1, aux2, aux3;
            if (nuevoConcepto.getExpedidopor() != null) {
                aux1 = nuevoConcepto.getExpedidopor().toUpperCase();
                nuevoConcepto.setExpedidopor(aux1);
            }
            if (nuevoConcepto.getQuien() != null) {
                aux2 = nuevoConcepto.getQuien().toUpperCase();
                nuevoConcepto.setQuien(aux2);
            }
            if (nuevoConcepto.getTexto() != null) {
                aux3 = nuevoConcepto.getTexto().toUpperCase();
                nuevoConcepto.setTexto(aux3);
            }
            BigInteger var = BigInteger.valueOf(k);
            nuevoConcepto.setSecuencia(var);
            nuevoConcepto.setEmpresa(empresaActual);
            listConceptosJuridicosCrear.add(nuevoConcepto);
            listConceptosJuridicos.add(nuevoConcepto);
            ////------////
            nuevoConcepto = new ConceptosJuridicos();
            ////-----////
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("NuevoRegistroConcepto.hide()");
            context.update("form:datosConcepto");
            context.update("formularioDialogos:NuevoRegistroConcepto");
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");
            }
            cambiosConceptos = true;
            index = -1;
            secRegistroConcepto = null;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarNuevoConcepto() {
        nuevoConcepto = new ConceptosJuridicos();
        index = -1;
        secRegistroConcepto = null;
    }

    public void duplicarConceptoM() {
        if (cualCelda == 0) {
            index = indexAux;
        }
        if (index >= 0) {
            duplicarConcepto = new ConceptosJuridicos();
            if (tipoLista == 0) {
                duplicarConcepto.setEmpresa(listConceptosJuridicos.get(index).getEmpresa());
                duplicarConcepto.setExpedidopor(listConceptosJuridicos.get(index).getExpedidopor());
                duplicarConcepto.setFecha(listConceptosJuridicos.get(index).getFecha());
                duplicarConcepto.setQuien(listConceptosJuridicos.get(index).getQuien());
                duplicarConcepto.setTexto(listConceptosJuridicos.get(index).getTexto());
            }
            if (tipoLista == 1) {

                duplicarConcepto.setEmpresa(filtrarListConceptosJuridicos.get(index).getEmpresa());
                duplicarConcepto.setExpedidopor(filtrarListConceptosJuridicos.get(index).getExpedidopor());
                duplicarConcepto.setFecha(filtrarListConceptosJuridicos.get(index).getFecha());
                duplicarConcepto.setQuien(filtrarListConceptosJuridicos.get(index).getQuien());
                duplicarConcepto.setTexto(filtrarListConceptosJuridicos.get(index).getTexto());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroConcepto");
            context.execute("DuplicarRegistroConcepto.show()");

        }
    }

    public void confirmarDuplicar() {
        boolean resp = validarNuevosDatosRegistro(2);
        if (resp == true) {
            if (cualCelda == 0) {
                index = indexAux;
            }
            if (index >= 0) {
                if (bandera == 1) {
                    conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
                    conceptoFecha.setFilterStyle("display: none; visibility: hidden;");
                    conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
                    conceptoNombre.setFilterStyle("display: none; visibility: hidden;");
                    conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
                    conceptoCargo.setFilterStyle("display: none; visibility: hidden;");

                    RequestContext.getCurrentInstance().update("form:datosConcepto");
                    bandera = 0;
                    filtrarListConceptosJuridicos = null;
                    tipoLista = 0;
                }
                String aux1, aux2, aux3;
                if (duplicarConcepto.getExpedidopor() != null) {
                    aux1 = duplicarConcepto.getExpedidopor().toUpperCase();
                    duplicarConcepto.setExpedidopor(aux1);
                }
                if (duplicarConcepto.getQuien() != null) {
                    aux2 = duplicarConcepto.getQuien().toUpperCase();
                    duplicarConcepto.setQuien(aux2);
                }
                if (duplicarConcepto.getTexto() != null) {
                    aux3 = duplicarConcepto.getTexto().toUpperCase();
                    duplicarConcepto.setTexto(aux3);
                }
                k++;
                BigInteger var = BigInteger.valueOf(k);

                duplicarConcepto.setSecuencia(var);
                duplicarConcepto.setEmpresa(empresaActual);
                listConceptosJuridicosCrear.add(duplicarConcepto);
                listConceptosJuridicos.add(duplicarConcepto);
                duplicarConcepto = new ConceptosJuridicos();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosConcepto");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                }
                context.execute("DuplicarRegistroConcepto.hide()");
                cambiosConceptos = true;
                index = -1;
                secRegistroConcepto = null;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNuevo.show()");
        }
    }

    public void limpiarDuplicarConcepto() {
        duplicarConcepto = new ConceptosJuridicos();

    }

    ///////////////////////////////////////////////////////////////
    /**
     * Valida que registro se elimina de que tabla con respecto a la posicion en
     * la pagina
     */
    public void borrarConcepto() {
        if (cualCelda == 0) {
            index = indexAux;
        }
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listConceptosJuridicosModificar.isEmpty() && listConceptosJuridicosModificar.contains(listConceptosJuridicos.get(index))) {
                    int modIndex = listConceptosJuridicosModificar.indexOf(listConceptosJuridicos.get(index));
                    listConceptosJuridicosModificar.remove(modIndex);
                    listConceptosJuridicosBorrar.add(listConceptosJuridicos.get(index));
                } else if (!listConceptosJuridicosCrear.isEmpty() && listConceptosJuridicosCrear.contains(listConceptosJuridicos.get(index))) {
                    int crearIndex = listConceptosJuridicosCrear.indexOf(listConceptosJuridicos.get(index));
                    listConceptosJuridicosCrear.remove(crearIndex);
                } else {
                    listConceptosJuridicosBorrar.add(listConceptosJuridicos.get(index));
                }
                listConceptosJuridicos.remove(index);
            }
            if (tipoLista == 1) {
                if (!listConceptosJuridicosModificar.isEmpty() && listConceptosJuridicosModificar.contains(filtrarListConceptosJuridicos.get(index))) {
                    int modIndex = listConceptosJuridicosModificar.indexOf(filtrarListConceptosJuridicos.get(index));
                    listConceptosJuridicosModificar.remove(modIndex);
                    listConceptosJuridicosBorrar.add(filtrarListConceptosJuridicos.get(index));
                } else if (!listConceptosJuridicosCrear.isEmpty() && listConceptosJuridicosCrear.contains(filtrarListConceptosJuridicos.get(index))) {
                    int crearIndex = listConceptosJuridicosCrear.indexOf(filtrarListConceptosJuridicos.get(index));
                    listConceptosJuridicosCrear.remove(crearIndex);
                } else {
                    listConceptosJuridicosBorrar.add(filtrarListConceptosJuridicos.get(index));
                }
                int VLIndex = listConceptosJuridicos.indexOf(filtrarListConceptosJuridicos.get(index));
                listConceptosJuridicos.remove(VLIndex);
                filtrarListConceptosJuridicos.remove(index);
            }
            textoNormativo = "";
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosConcepto");
            context.update("formTexto:editarTexto");
            index = -1;
            secRegistroConcepto = null;
            cambiosConceptos = true;
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
        filtradoConcepto();
    }

    /**
     * Metodo que acciona el filtrado de la tabla vigencia localizacion
     */
    public void filtradoConcepto() {
        if (bandera == 0) {
            conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
            conceptoFecha.setFilterStyle("width: 60px");
            conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
            conceptoNombre.setFilterStyle("width: 250px");
            conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
            conceptoCargo.setFilterStyle("width: 220px");
            RequestContext.getCurrentInstance().update("form:datosConcepto");
            bandera = 1;
        } else if (bandera == 1) {
            conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
            conceptoFecha.setFilterStyle("display: none; visibility: hidden;");
            conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
            conceptoNombre.setFilterStyle("display: none; visibility: hidden;");
            conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
            conceptoCargo.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosConcepto");
            bandera = 0;
            filtrarListConceptosJuridicos = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            conceptoFecha = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoFecha");
            conceptoFecha.setFilterStyle("display: none; visibility: hidden;");
            conceptoNombre = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoNombre");
            conceptoNombre.setFilterStyle("display: none; visibility: hidden;");
            conceptoCargo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosConcepto:conceptoCargo");
            conceptoCargo.setFilterStyle("display: none; visibility: hidden;");

            RequestContext.getCurrentInstance().update("form:datosConcepto");
            bandera = 0;
            filtrarListConceptosJuridicos = null;
            tipoLista = 0;
        }

        listConceptosJuridicosBorrar.clear();
        listConceptosJuridicosCrear.clear();
        listConceptosJuridicosModificar.clear();
        index = -1;
        secRegistroConcepto = null;
        listConceptosJuridicos = null;
        listEmpresas = null;
        empresaActual = null;
        k = 0;
        empresita = null;
        textoNormativo = null;
        indexAux = -1;
        cambioTextNormativo = true;
        listConceptosJuridicos = null;
        guardado = true;
        cambiosConceptos = false;
        cambioTextNormativo = true;
        nuevoConcepto = new ConceptosJuridicos();
        duplicarConcepto = new ConceptosJuridicos();

    }

    public void activarAceptar() {
        aceptar = false;
    }

    /**
     * Selecciona la tabla a exportar XML con respecto al index activo
     *
     * @return Nombre del dialogo a exportar en XML
     */
    public String exportXML() {
        if (index >= 0) {
            nombreTabla = ":formExportarConceptos:datosConceptosExportar";
            nombreXML = "ConceptosXML";
        }
        return nombreTabla;
    }

    /**
     * Valida la tabla a exportar en PDF con respecto al index activo
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void validarExportPDF() throws IOException {
        exportPDF_C();
    }

    /**
     * Metodo que exporta datos a PDF Vigencia Localizacion
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarConceptos:datosConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "ConceptosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        indexAux = -1;
        secRegistroConcepto = null;
    }

    /**
     * Verifica que tabla exportar XLS con respecto al index activo
     *
     * @throws IOException
     */
    public void verificarExportXLS() throws IOException {
        exportXLS_C();
    }

    /**
     * Metodo que exporta datos a XLS Vigencia Sueldos
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS_C() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarConceptos:datosConceptosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "ConceptosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        indexAux = -1;
        secRegistroConcepto = null;
    }

    //EVENTO FILTRAR
    /**
     * Evento que cambia la lista real a la filtrada
     */
    public void eventoFiltrar() {
        if (index >= 0) {
            if (tipoLista == 0) {
                tipoLista = 1;
            }
        }
    }

    //METODO RASTROS PARA LAS TABLAS EN EMPLVIGENCIASUELDOS
    public void verificarRastroTabla() {
        verificarRastroConcepto();
        index = -1;
        indexAux = -1;
    }

    //Verificar Rastro Vigencia Sueldos
    public void verificarRastroConcepto() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listConceptosJuridicos != null) {
            if (secRegistroConcepto != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroConcepto, "CONCEPTOSJURIDICOS");
                backUpSecRegistroConceptos = secRegistroConcepto;
                backUp = secRegistroConcepto;
                secRegistroConcepto = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "ConceptosJuridicos";
                    msnConfirmarRastro = "La tabla CONCEPTOSJURIDICOS tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("CONCEPTOSJURIDICOS")) {
                nombreTablaRastro = "ConceptosJuridicos";
                msnConfirmarRastroHistorico = "La tabla CONCEPTOSJURIDICOS tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void lovEmpresas() {
        index = -1;
        indexAux = -1;
        secRegistroConcepto = null;
        cualCelda = -1;
        RequestContext.getCurrentInstance().execute("EmpresasDialogo.show()");
    }

    public void actualizarEmpresa() {
        if (cambiosConceptos == false && cambioTextNormativo == true) {
            RequestContext context = RequestContext.getCurrentInstance();
            empresaActual = empresita;
            listConceptosJuridicos = null;
            listConceptosJuridicos = getListConceptosJuridicos();
            if (!listConceptosJuridicos.isEmpty()) {
                textoNormativo = listConceptosJuridicos.get(0).getTexto();
            }
            textoNormativo = "";
            ////////////
            context.update("form:datosConcepto");
            context.update("form:nombreEmpresa");
            context.update("form:nitEmpresa");
            context.update("formTexto:editarTexto");
            filtrarListEmpresas = null;
            aceptar = true;
            context.execute("EmpresasDialogo.hide()");
            context.reset("form:lovEmpresas:globalFilter");
            context.update("form:lovEmpresas");
            backUpEmpresaActual = empresaActual;
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cancelarCambioEmpresa() {
        index = -1;
        indexAux = -1;
        filtrarListEmpresas = null;
    }

    public void limpiarMSNRastros() {
        msnConfirmarRastro = "";
        msnConfirmarRastroHistorico = "";
        nombreTablaRastro = "";
    }

    //GET - SET 
    public List<ConceptosJuridicos> getListConceptosJuridicos() {
        try {
            if (listConceptosJuridicos == null) {
                listConceptosJuridicos = new ArrayList<ConceptosJuridicos>();
                listConceptosJuridicos = administrarConceptoJuridico.listaConceptosJuridicosPorEmpresa(empresaActual.getSecuencia());
            }
            return listConceptosJuridicos;
        } catch (Exception e) {
            System.out.println("Error getListConceptosJuridicos " + e.toString());
            return null;
        }
    }

    public void setListConceptosJuridicos(List<ConceptosJuridicos> t) {
        this.listConceptosJuridicos = t;
    }

    public List<ConceptosJuridicos> getFiltrarListConceptosJuridicos() {
        return filtrarListConceptosJuridicos;
    }

    public void setFiltrarListConceptosJuridicos(List<ConceptosJuridicos> t) {
        this.filtrarListConceptosJuridicos = t;
    }

    public String getNombreXML() {
        return nombreXML;
    }

    public void setNombreXML(String nombreXML) {
        this.nombreXML = nombreXML;
    }

    public Empresas getEmpresita() {
        return empresita;
    }

    public void setEmpresita(Empresas empresita) {
        this.empresita = empresita;

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
        this.secRegistroConcepto = secRegistro;
    }

    public BigInteger getBackUpSecRegistroConceptos() {
        return backUpSecRegistroConceptos;
    }

    public void setBackUpSecRegistroConceptos(BigInteger b) {
        this.backUpSecRegistroConceptos = b;
    }

    public List<ConceptosJuridicos> getListConceptosJuridicosModificar() {
        return listConceptosJuridicosModificar;
    }

    public void setListConceptosJuridicosModificar(List<ConceptosJuridicos> setListConceptosJuridicosModificar) {
        this.listConceptosJuridicosModificar = setListConceptosJuridicosModificar;
    }

    public ConceptosJuridicos getNuevoConcepto() {
        return nuevoConcepto;
    }

    public void setNuevoConcepto(ConceptosJuridicos setNuevoConcepto) {
        this.nuevoConcepto = setNuevoConcepto;
    }

    public List<ConceptosJuridicos> getListConceptosJuridicosCrear() {
        return listConceptosJuridicosCrear;
    }

    public void setListConceptosJuridicosCrear(List<ConceptosJuridicos> setListConceptosJuridicosCrear) {
        this.listConceptosJuridicosCrear = setListConceptosJuridicosCrear;
    }

    public List<ConceptosJuridicos> getListConceptosJuridicosBorrar() {
        return listConceptosJuridicosBorrar;
    }

    public void setListConceptosJuridicosBorrar(List<ConceptosJuridicos> setListConceptosJuridicosBorrar) {
        this.listConceptosJuridicosBorrar = setListConceptosJuridicosBorrar;
    }

    public ConceptosJuridicos getEditarConcepto() {
        return editarConcepto;
    }

    public void setEditarConcepto(ConceptosJuridicos setEditarConcepto) {
        this.editarConcepto = setEditarConcepto;
    }

    public ConceptosJuridicos getDuplicarConcepto() {
        return duplicarConcepto;
    }

    public void setDuplicarConcepto(ConceptosJuridicos setDuplicarConcepto) {
        this.duplicarConcepto = setDuplicarConcepto;
    }

    public BigInteger getSecRegistroConcepto() {
        return secRegistroConcepto;
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

    public List<Empresas> getListEmpresas() {
        if (listEmpresas == null) {
            listEmpresas = administrarConceptoJuridico.listaEmpresas();
            if (!listEmpresas.isEmpty()) {
                empresaActual = listEmpresas.get(0);
                backUpEmpresaActual = empresaActual;
            }
        }
        return listEmpresas;
    }

    public void setListEmpresas(List<Empresas> listEmpresas) {
        this.listEmpresas = listEmpresas;
    }

    public List<Empresas> getFiltrarListEmpresas() {
        return filtrarListEmpresas;
    }

    public void setFiltrarListEmpresas(List<Empresas> filtrarListEmpresas) {
        this.filtrarListEmpresas = filtrarListEmpresas;
    }

    public Empresas getEmpresaActual() {
        getListEmpresas();
        empresita = empresaActual;
        return empresaActual;
    }

    public void setEmpresaActual(Empresas empresaActual) {
        this.empresaActual = empresaActual;
    }

    public Empresas getBackUpEmpresaActual() {
        return backUpEmpresaActual;
    }

    public void setBackUpEmpresaActual(Empresas backUpEmpresaActual) {
        this.backUpEmpresaActual = backUpEmpresaActual;
    }

    public String getTextoNormativo() {

        return textoNormativo;
    }

    public void setTextoNormativo(String textoNormativo) {
        this.textoNormativo = textoNormativo;
    }

    public boolean isReadOnlyTexto() {
        if ((!listConceptosJuridicos.isEmpty()) && (index >= 0)) {
            readOnlyTexto = false;
        } else {
            readOnlyTexto = true;
        }
        return readOnlyTexto;
    }

    public void setReadOnlyTexto(boolean readOnlyTexto) {
        this.readOnlyTexto = readOnlyTexto;
    }

}

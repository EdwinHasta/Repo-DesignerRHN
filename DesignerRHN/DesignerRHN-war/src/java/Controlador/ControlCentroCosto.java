package Controlador;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Administrar.AdministrarCentroCostos;
import Entidades.CentrosCostos;
import Entidades.Empresas;
import Entidades.TiposCentrosCostos;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarCentroCostosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.export.Exporter;
import org.primefaces.context.RequestContext;

/**
 *
 * @author John Pineda 
 */
@ManagedBean
@SessionScoped
public class ControlCentroCosto implements Serializable {

    //--------------------------------------------------------------------------
    //EJB
    //--------------------------------------------------------------------------
    @EJB
    AdministrarCentroCostosInterface administrarCentroCostos;
    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //                                  EMPRESAS
    //--------------------------------------------------------------------------
    /**
     * LISTA DE LAS EMPRESAS EXISTENTES
     */
    private List<Empresas> listaEmpresas;
    /**
     * ATRIBUTO QUE TENDRA LA EMPRESA SELECCIONADA
     */
    private Empresas empresaSeleccionada;
    //--------------------------------------------------------------------------
    //                              CENTROSCOSTOS
    //--------------------------------------------------------------------------
    /**
     * LISTA QUE TENDRA LOS CENTROS COSTOS DE LA EMPRESA
     */
    private List<CentrosCostos> listaCentrosCostosPorEmpresa;
    private List<CentrosCostos> listaCentrosCostosPorEmpresaLOV;
    /**
     * lista donde guardara el filtrado de el dialogo busca
     * buscarCentrosCostosDialogo
     */
    private List<CentrosCostos> filterCentrosCostosPorEmpresa;
    /**
     * guarda el centro costo seleccionado
     */
    private CentrosCostos CentrosCostosPorEmpresaSeleccionado;
    /**
     * Atributo que representa la Lista de los CentrosCostos que son creados por
     * el usuario
     */
    private List<CentrosCostos> listaCentrosCostosCrear;
    /**
     * Atributo que simboliza el nuevo CentroCostos que se va a crear
     */
    private CentrosCostos nuevoCentroCosto;
    /**
     * Atributo que representa la lista de los CentrosCostos que son modificados
     * por el usuario
     */
    private List<CentrosCostos> listaCentrosCostosModificar;
    /**
     * Atributo que representa la lista de los CentrosCostos que son borrados
     * por el usuario
     */
    private List<CentrosCostos> listaCentrosCostosBorrar;
    /**
     * Lista que cumple con el Sting que digite el usuario
     */
    private List<CentrosCostos> filtrarCentrosCostos;
    //--------------------------------------------------------------------------
    //                              TIPOSCENTROSCOSTOS
    //--------------------------------------------------------------------------
    //Unidades/(LOV)/////////////////////////////////////////////////////////////////
    /**
     * Atributo que guarda todas los TiposCentrosCostos existentes
     */
    private List<TiposCentrosCostos> listaTiposCentrosCostos;
    /**
     * Atributo que representa los TiposCentrosCostos que cumplen con el string
     * tecleado por el usuario (LOV)
     */
    private List<TiposCentrosCostos> filtradoTiposCentrosCostos;
    /**
     * Atributo que representa el TipoCentroCosto que se selecciona en la LOV en
     * el xhtml
     */
    private TiposCentrosCostos tipoCentrosCostosSeleccionado;
    //--------------------------------------------------------------------------
    //                                  OTROS
    //--------------------------------------------------------------------------
    /**
     * Atributo encargado de guardar el indice de la empresa que se esta
     * mostrando al usuario
     */
    private int indiceEmpresaMostrada;
    /**
     * Atributo que representa si (ctrl + F11) esta activado o desactivado
     */
    private int bandera;
    /**
     * Atributo encargado de habilitar o deshabilitar el boton de aceptar en el
     * xhtml (LOV)
     */
    private boolean aceptar;
    /**
     * Atributo que determina si hubo un cambio o no para permitir guardar los
     * cambios
     */
    private boolean guardado;
    /**
     * Atributo encargado de guardar el indice de la tabla en la que se
     * encuentra el valor seleccionado por el usuario
     */
    private int index;
    /**
     *
     */
    private boolean guardarOk;
    /**
     * bandera que cambia si se modifica crea duplica elimina... Hace un cambio
     * para que al momento de cambiar empresa sea obligatorio guardar
     */
    private int banderaModificacionEmpresa;
    /**
     * variable que se utiliza en el metodo guardar en la parte de borar para
     * revisar si la empresa no esta relacionada con otras tablas
     */
    private long contadorEmpresa;
    /**
     *
     */
    //--------------------------------------------------------------------------
    //                                  EDITAR
    //--------------------------------------------------------------------------
    /**
     * Atributo encargado de editar el registro en un dialogo
     */
    private CentrosCostos editarCentroCostos;
    /**
     * Atributoas que sirven para identificar en que celdo se esta trabajando y
     * el tipo de lista que se esta usando cualCelda: los posibles valores
     * dependen de la cantidad de columnas tipoLista: 0 si es la lista normal y
     * 1 si es la lista filtrada
     */
    private int cualCelda, tipoLista;
    /**
     * Atributo que determina si hubo algun cabio en el valor cuando se activa
     * los dialogos de editar
     */
    private boolean cambioEditor;
    /**
     * Atributo que sirve para habilitar o deshabilitar el boton de aceptar en
     * los dialogos de editar
     */
    private boolean aceptarEditar;
    //--------------------------------------------------------------------------
    //                                  DUPLICAR
    //--------------------------------------------------------------------------
    /**
     * Atributo donde se guarda la informacion del nuevo CentroCostos el cual es
     * la copia de otro CentroCostos
     */
    private CentrosCostos duplicarCentrosCostos;
    //--------------------------------------------------------------------------
    //                    CREACION SECUENCIA TEMPORAL
    //--------------------------------------------------------------------------
    /**
     * Atributo encargado de generar una secuencia temporal para crear un objeto
     */
    private BigInteger l;
    /**
     * Atributo encargado de darle el valor a la secuencia teporal
     */
    private int k;
    private int tipoActualizacion;
    private String[] filtradoManoObra = {"D", "I"};
    private SelectItem[] opcionesFiltradoManoObra;
    private SelectItem[] opciones;
    //--------------------------------------------------------------------------
    //                       DEPENDIENTES DE LA PANTALA
    //--------------------------------------------------------------------------
    /**
     * Atributos que representan las columnas de la tabla de CentroCostos en el
     * xhtml
     */
    private Column codigoCC, nombreCentroCosto, tipoCentroCosto, manoDeObra, codigoAT, obsoleto, codigoCTT, dimensiones;

    public ControlCentroCosto() {

        administrarCentroCostos = new AdministrarCentroCostos();
        //Empresas
        listaEmpresas = null;
        empresaSeleccionada = null;
        //CentrosCostos
        listaCentrosCostosPorEmpresa = null;
        listaCentrosCostosCrear = new ArrayList<CentrosCostos>();
        nuevoCentroCosto = new CentrosCostos();
        listaCentrosCostosModificar = new ArrayList<CentrosCostos>();
        listaCentrosCostosBorrar = new ArrayList<CentrosCostos>();
        //TiposCentrosCostos---
        listaTiposCentrosCostos = null;
        tipoCentrosCostosSeleccionado = new TiposCentrosCostos();
        //Otros
        aceptar = true;
        guardado = true;
        indiceEmpresaMostrada = 0;
        //editar
        editarCentroCostos = new CentrosCostos();
        cualCelda = -1;
        tipoLista = 0;
        cambioEditor = false;
        aceptarEditar = true;
        //Creacion Secuencia Teporal
        k = 0;
        listaCentrosCostosPorEmpresaLOV = null;
        crearOpcionesFiltradoManoObra(filtradoManoObra);
    }
    //--------------------------------------------------------------------------
    //                           METODOS
    //--------------------------------------------------------------------------

    /**
     * Metodo encargado de identificar el lugar donde se selecciono en el xhtml
     *
     * @param indice fila de ubicacion de un CentroCosto en la tabla
     * @param celda columna de ubicacion de un CentroCosto en la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        index = indice;
        cualCelda = celda;
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

//+++++++++++++++++++++++++++ modificar ++++++++++++++++++++++++++++++++++++++++
    public void modificarCentroCosto(int indice) {
        System.out.println("\n ENTRE A ControlCentroCosto.modificarCentroCosto \n");
        banderaModificacionEmpresa = 1;
        try {
            if (!listaCentrosCostosCrear.contains(listaCentrosCostosPorEmpresa.get(indice))) {
                System.out.println("\n  1 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getManoObra: " + listaCentrosCostosPorEmpresa.get(indice).getManoobra());
                System.out.println("\n  1 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getObsoleto===" + listaCentrosCostosPorEmpresa.get(indice).getObsoleto());
                System.out.println("\n  1 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getDimensiones===" + listaCentrosCostosPorEmpresa.get(indice).getDimensiones());
                if (listaCentrosCostosModificar.isEmpty()) {
                    System.out.println("\n  2 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getManoObra===" + listaCentrosCostosPorEmpresa.get(indice).getManoobra());
                    System.out.println("\n  2 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getObsoleto===" + listaCentrosCostosPorEmpresa.get(indice).getObsoleto());
                    System.out.println("\n  2 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getDimensiones===" + listaCentrosCostosPorEmpresa.get(indice).getDimensiones());
                    listaCentrosCostosModificar.add(listaCentrosCostosPorEmpresa.get(indice));
                } else if (!listaCentrosCostosModificar.contains(listaCentrosCostosPorEmpresa.get(indice))) {
                    System.out.println("\n  3 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getManoObra===" + listaCentrosCostosPorEmpresa.get(indice).getManoobra());
                    System.out.println("\n  3 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getObsoleto===" + listaCentrosCostosPorEmpresa.get(indice).getObsoleto());
                    System.out.println("\n  3 ControlCentroCostos.modificarCentroCosto.listaCentroCostosPorEmpresa.get(Indice).getDimensiones===" + listaCentrosCostosPorEmpresa.get(indice).getDimensiones());
                    listaCentrosCostosModificar.add(listaCentrosCostosPorEmpresa.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        } catch (Exception e) {
            System.out.println("\n ERROR ControlCentroCosto.modifcarCentroCosto ERROR=============" + e.getMessage());
        }
    }

    /**
     *
     */
    public void guardarCambiosCentroCostos() {
        System.out.println("\n ENTRE A controlCentroCosto.guardarCambiosCentroCostos \n");
        try {
            contadorEmpresa = 0;
            if (guardado == false) {
                //------------------------------------------------------------
                try {
                    System.out.println("ControlCentroCosto.guardarCambiosCentroCostos  Entre al segundo try");
                    if (!listaCentrosCostosBorrar.isEmpty()) {
                        try {
                            for (int i = 0; i < listaCentrosCostosBorrar.size(); i++) {
                                try {
                                    contadorEmpresa = administrarCentroCostos.contadorSecueniasEmpresas(listaCentrosCostosBorrar.get(i).getSecuencia());
                                    System.out.println("ControlCentrosCostos.guardarCambiosCentrosCosos.En el for de borrar... contarEmpresa=" + contadorEmpresa);
                                    if (contadorEmpresa == 0) {
                                        System.out.println("Borrando...");
                                        System.out.println("Borrando listaCentrosCostosBorrar.get(i).getSecuencia()" + listaCentrosCostosBorrar.get(i).getSecuencia());
                                        System.out.println("Borrando listaCentrosCostosBorrar.get(i).getSecuencia()" + listaCentrosCostosBorrar.get(i).getNombre());
                                        administrarCentroCostos.borrarCentroCostos(listaCentrosCostosBorrar.get(i));
                                    } else if (contadorEmpresa > 0) {
                                        RequestContext context = RequestContext.getCurrentInstance();
                                        context.execute("evitarBorrado.show()");
                                    }

                                } catch (Exception e) {
                                    System.out.println("ControlCentroCosto.guardarCambiosCentroCostos  Entre al segundo try DE BORRAR dentro del FOR");
                                }
                            }
                            listaCentrosCostosBorrar.clear();
                        } catch (Exception e) {
                            System.out.println("ERROR ControlCentroCosto CATCHBORRAR ERROR====" + e.getMessage());
                        }
                    }
                    if (!listaCentrosCostosCrear.isEmpty()) {
                        try {
                            for (int i = 0; i < listaCentrosCostosCrear.size(); i++) {
                                try {
                                    System.out.println("ControlCentroCosto.guardarCambiosCentroCostos  en Lista Crear");
                                    System.out.println("listaCentrosCostosCrear.get(i)====" + listaCentrosCostosCrear.get(i).getNombre());
                                    administrarCentroCostos.crearCentroCostos(listaCentrosCostosCrear.get(i));
                                } catch (Exception e) {
                                    System.out.println("ControlCentroCosto.guardarCambiosCentroCostos  Entre al segundo try DE CREAR dentro del FOR");
                                }
                            }
                            listaCentrosCostosCrear.clear();
                        } catch (Exception e) {
                            System.out.println("ERROR ControlCEntroCosto. CATHCREAR ERROR====" + e.getMessage());
                        }
                    }

                    if (!listaCentrosCostosModificar.isEmpty()) {
                        try {
                            System.out.println("ControlCentroCosto.guardarCambiosCentroCostos  en Lista modificar");
                            for (int i = 0; i < listaCentrosCostosModificar.size(); i++) {
                                administrarCentroCostos.modificarCentroCostos(listaCentrosCostosModificar.get(i));
                            }
                            listaCentrosCostosModificar.clear();
                        } catch (Exception e) {
                            System.out.println("ERROR ControlCentroCosto. CATCHMODIFICAR ERROR====" + e.getMessage());
                        }
                    }

                    System.out.println("ControlCentroCosto.GuardarCambiosCentrosCostos Se guardaron los datos con exito");
                    listaCentrosCostosPorEmpresa = null;
                    RequestContext context = RequestContext.getCurrentInstance();
                    context.update("form:datosCentrosCostos");
                    guardado = true;
                    RequestContext.getCurrentInstance().update("form:aceptar");
                    k = 0;
                } catch (Exception e) {
                    System.out.println("ERROR ControlCentroCostos.guardarCambiosCentrosCostos SEGUNDO CATCH ERROR=========" + e.getMessage());
                }

                contadorEmpresa = 0;
                //----------------------------------------------------------------------------
            }
            index = -1;
            banderaModificacionEmpresa = 0;
        } catch (Exception e) {
            System.out.println("\n ERROR controlCentroCostos.GuardarCambiosCentroCostos ERROR==============" + e.getMessage());
        }
    }

    /**
     *
     */
    public void cancelarModificacion() {
        try {
            System.out.println("entre a ControlCentroCostos.cancelarModificacion");
            if (bandera == 1) {
                //CERRAR FILTRADO
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }

            listaCentrosCostosBorrar.clear();
            listaCentrosCostosCrear.clear();
            listaCentrosCostosModificar.clear();
            index = -1;
            k = 0;
            listaCentrosCostosPorEmpresa = null;
            guardado = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");
        } catch (Exception E) {
            System.out.println("ERROR ControlCentroCosto.ModificarModificacion ERROR====================" + E.getMessage());
        }
    }

    /**
     * Metodo encargado de mostrar los dialogos dependiendo de la celda en la
     * que se este trabajando Indispensable para el editor
     */
    public void editarCelda() {
        System.out.println("\n ENTRE A ControlCentroCosto.editarCelda \n");

        try {
            System.out.println("\n ENTRE A ControlCentroCosto.editarCelda INDEX====  \n" + index);
            if (index >= 0) {
                System.out.println("\n ENTRE A ControlCentroCosto.editarCelda TIPOLISTA====  \n" + tipoLista);
                if (tipoLista == 0) {
                    editarCentroCostos = listaCentrosCostosPorEmpresa.get(index);
                }
                if (tipoLista == 1) {
                    editarCentroCostos = filtrarCentrosCostos.get(index);
                }
                RequestContext context = RequestContext.getCurrentInstance();
                System.out.println("ControlCentroCosto: Entro a editar... valor celda: " + cualCelda);
                if (cualCelda == 0) {
                    context.update("formularioDialogos:editarCCC");
                    context.execute("editarCCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 1) {
                    context.update("formularioDialogos:editarNCC");
                    context.execute("editarNCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 2) {
                    context.update("formularioDialogos:editarTCC");
                    context.execute("editarTCC.show()");
                    cualCelda = -1;
                } else if (cualCelda == 3) {
                    context.update("formularioDialogos:editarMO");
                    context.execute("editarMO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 4) {
                    context.update("formularioDialogos:editarCAT");
                    context.execute("editarCAT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 5) {
                    context.update("formularioDialogos:editarO");
                    context.execute("editarO.show()");
                    cualCelda = -1;
                } else if (cualCelda == 6) {
                    context.update("formularioDialogos:editarCCTT");
                    context.execute("editarCCTT.show()");
                    cualCelda = -1;
                } else if (cualCelda == 7) {
                    context.update("formularioDialogos:editarD");
                    context.execute("editarD.show()");
                    cualCelda = -1;
                }
            }
            index = -1;
        } catch (Exception E) {
            System.out.println("ERROR ControlCentroCosto.editarCelta ERROR=====================" + E.getMessage());
        }
    }

    /**
     *
     */
    public void agregarNuevoCentroCostos() {
        System.out.println("\n ENTRE A ControlCentroCosto.agregarNuevoCentroCostos \n");
        try {
            banderaModificacionEmpresa = 1;
            if (bandera == 1) {
                //CERRAR FILTRADO
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                codigoCTT.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            //AGREGAR REGISTRO A LA LISTA CENTROSCOSTOS POR EMPRESA.
            k++;
            l = BigInteger.valueOf(k);
            nuevoCentroCosto.setSecuencia(l);
            System.out.println("ControlCentroCosto.NuevoResgistroCentroCosto nuevocentrotroCosto.secuencia==" + nuevoCentroCosto.getSecuencia());
            System.out.println("Codigo: " + nuevoCentroCosto.getCodigo());
            System.out.println("Nombre: " + nuevoCentroCosto.getNombre());
            System.out.println("Nombre TCC: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
            System.out.println("Mano Obra: " + nuevoCentroCosto.getManoobra());
            System.out.println("Cod Alt: " + nuevoCentroCosto.getCodigoalternativo());
            System.out.println("obsoleto: " + nuevoCentroCosto.getObsoleto());
            System.out.println("Codigo ctt: " + nuevoCentroCosto.getCodigoctt());
            System.out.println("Dimensiones: " + nuevoCentroCosto.getDimensiones());
            nuevoCentroCosto.setComodin("N");
            nuevoCentroCosto.setEmpresa(empresaSeleccionada);
            listaCentrosCostosCrear.add(nuevoCentroCosto);
            listaCentrosCostosPorEmpresa.add(nuevoCentroCosto);
            nuevoCentroCosto = new CentrosCostos();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");//la ruta depende del xhtml
            if (guardado == true) {
                guardado = false;
                RequestContext.getCurrentInstance().update("form:aceptar");//la ruta depende del xhtml
            }
            index = -1;
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.agregarNuevoCentroCostos ERROR===========================" + e.getMessage());
        }
    }

    /**
     * Metodo encargado de limpiar los datos que estan en el atributo
     * nuevoCentroCosto
     */
    public void limpiarNuevoCentroCostos() {
        System.out.println("\n ENTRE A ControlCentroCosto.limpiarNuevoCentroCostos \n");
        try {
            nuevoCentroCosto = new CentrosCostos();
            index = -1;
        } catch (Exception e) {
            System.out.println("Error ControlCentroCosto.LimpiarNuevoCentroCostos ERROR=============================" + e.getMessage());
        }
    }

    /**
     * Metodo encargado de duplicar un CentroCosto
     */
    public void duplicarCentroCostos() {
        try {
            banderaModificacionEmpresa = 1;
            System.out.println("\n ENTRE A ControlCentroCosto.duplicarCentroCostos INDEX===" + index);

            if (index >= 0) {
                System.out.println("\n ENTRE A ControlCentroCosto.duplicarCentroCostos TIPOLISTA===" + tipoLista);

                duplicarCentrosCostos = new CentrosCostos();
                k++;
                l = BigInteger.valueOf(k);
                if (tipoLista == 0) {
                    duplicarCentrosCostos.setSecuencia(l);
                    duplicarCentrosCostos.setEmpresa(listaCentrosCostosPorEmpresa.get(index).getEmpresa());
                    duplicarCentrosCostos.setCodigo(listaCentrosCostosPorEmpresa.get(index).getCodigo());
                    duplicarCentrosCostos.setNombre(listaCentrosCostosPorEmpresa.get(index).getNombre());
                    duplicarCentrosCostos.setTipocentrocosto(listaCentrosCostosPorEmpresa.get(index).getTipocentrocosto());
                    duplicarCentrosCostos.setManoobra(listaCentrosCostosPorEmpresa.get(index).getManoobra());
                    System.out.println("duplicarCentrosCostos.setManoobra(listaCentrosCostosPorEmpresa.get(index).getManoobra()=" + duplicarCentrosCostos.getManoobra());
                    duplicarCentrosCostos.setCodigoalternativo(listaCentrosCostosPorEmpresa.get(index).getCodigoalternativo());
                    duplicarCentrosCostos.setObsoleto(listaCentrosCostosPorEmpresa.get(index).getObsoleto());
                    duplicarCentrosCostos.setCodigoctt(listaCentrosCostosPorEmpresa.get(index).getCodigoctt());
                    duplicarCentrosCostos.setDimensiones(listaCentrosCostosPorEmpresa.get(index).getDimensiones());
                    duplicarCentrosCostos.setComodin(listaCentrosCostosPorEmpresa.get(index).getComodin());

                }
                if (tipoLista == 1) {

                    duplicarCentrosCostos.setSecuencia(l);
                    duplicarCentrosCostos.setEmpresa(filtrarCentrosCostos.get(index).getEmpresa());
                    duplicarCentrosCostos.setCodigo(filtrarCentrosCostos.get(index).getCodigo());
                    duplicarCentrosCostos.setNombre(filtrarCentrosCostos.get(index).getNombre());
                    duplicarCentrosCostos.setTipocentrocosto(filtrarCentrosCostos.get(index).getTipocentrocosto());
                    duplicarCentrosCostos.setManoobra(filtrarCentrosCostos.get(index).getManoobra());
                    System.out.println("duplicarCentrosCostos.setManoobra(filtrarCentrosCostos.get(index).getManoobra())=" + duplicarCentrosCostos.getManoobra());
                    duplicarCentrosCostos.setCodigoalternativo(filtrarCentrosCostos.get(index).getCodigoalternativo());
                    duplicarCentrosCostos.setObsoleto(filtrarCentrosCostos.get(index).getObsoleto());
                    duplicarCentrosCostos.setCodigoctt(filtrarCentrosCostos.get(index).getCodigoctt());
                    duplicarCentrosCostos.setDimensiones(filtrarCentrosCostos.get(index).getDimensiones());

                }

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicarCentroCostos");
                context.execute("DuplicarRegistroCentroCostos.show()");
                index = -1;
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlCEntroCosto.DuplicarCentroCostos ERROR===============" + e.getMessage());
        }
    }

    public void confirmarDuplicar() {
        System.out.println("\n ENTRE A ControlCentroCosto.confirmarDuplicar \n");

        try {
            listaCentrosCostosPorEmpresa.add(duplicarCentrosCostos);
            listaCentrosCostosCrear.add(duplicarCentrosCostos);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");
            index = -1;
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            if (bandera == 1) {
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");

                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
            duplicarCentrosCostos = new CentrosCostos();
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCostos.confirmarDuplicar ERROR===========================" + e.getMessage());
        }
    }

    /**
     * Metodo encargado de limpiar el atributo duplicarCentroCostos
     */
    public void limpiarDuplicarCentroCostos() {
        System.out.println("\n ENTRE A ControlCentroCosto.limpiarDuplicarCentroCostos \n");
        try {
            duplicarCentrosCostos = new CentrosCostos();
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.limpiarDuplicarCentroCostos ERROR========" + e.getMessage());
        }

    }

    /**
     *
     */
    public void eventoFiltrar() {
        try {
            System.out.println("\n ENTRE A ControlCentroCosto.eventoFiltrar \n");
            if (tipoLista == 0) {
                tipoLista = 1;
                //filtrarManoObra();
            }

        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCostos eventoFiltrar ERROR===" + e.getMessage());
        }
    }

    public void filtrarManoObra() {
        try {
            crearOpcionesFiltradoManoObra(filtradoManoObra);
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.filtrarManoObra ERROR==" + e.getMessage());
        }
    }

    /**
     *
     * @throws IOException
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "CentroCostosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    /**
     *
     * @throws IOException
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosCentrosCostosExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "CentroCostosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
    }

    /**
     *
     */
    public void activarAceptar() {
        aceptar = false;
    }

    /**
     *
     */
    public void listaValoresBoton() {
        System.out.println("\n ENTRE A ControlCentroCosto.listaValoresBoton \n");

        try {
            System.out.println("\n ENTRE A ControlCentroCosto.listaValoresBoton INDEX====" + index);

            if (index >= 0) {
                RequestContext context = RequestContext.getCurrentInstance();
                System.out.println("\n ENTRE A ControlCentroCosto.listaValoresBoton CUALCELDA====" + cualCelda);

                if (cualCelda == 2) {
                    System.out.println("\n ListaValoresBoton \n");
                    context.update("formularioDialogos:tiposCentrosCostosDialogo");
                    context.execute("tiposCentrosCostosDialogo.show()");
                    tipoActualizacion = 0;
                }
            }
        } catch (Exception e) {
            System.out.println("\n ERROR ControlCentroCosto.listaValoresBoton ERROR====================" + e.getMessage());

        }
    }

    /**
     *
     */
    public void borrarCentroCosto() {
        try {
            banderaModificacionEmpresa = 1;
            System.out.println("\n 1 ENTRE A ControlCentroCosto.borrarCentroCosto INDEX= " + index);
            if (index >= 0) {
                System.out.println("\n 2 ENTRE A ControlCentroCosto.borrarCentroCosto tipoLista= " + tipoLista);
                if (tipoLista == 0) {
                    if (!listaCentrosCostosModificar.isEmpty() && listaCentrosCostosModificar.contains(listaCentrosCostosPorEmpresa.get(index))) {
                        System.out.println("\n 3 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==0 try if if if listaCentrosCostosPorEmpresa.get(index).getCodigo()" + listaCentrosCostosPorEmpresa.get(index).getCodigo());
                        int modIndex = listaCentrosCostosModificar.indexOf(listaCentrosCostosPorEmpresa.get(index));
                        listaCentrosCostosModificar.remove(modIndex);
                        listaCentrosCostosBorrar.add(listaCentrosCostosPorEmpresa.get(index));
                    } else if (!listaCentrosCostosCrear.isEmpty() && listaCentrosCostosCrear.contains(listaCentrosCostosPorEmpresa.get(index))) {
                        System.out.println("\n 4 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==0 try if if if else if listaCentrosCostosPorEmpresa.get(index).getCodigo()" + listaCentrosCostosPorEmpresa.get(index).getCodigo());
                        int crearIndex = listaCentrosCostosCrear.indexOf(listaCentrosCostosPorEmpresa.get(index));
                        listaCentrosCostosCrear.remove(crearIndex);
                    } else {
                        System.out.println("\n 5 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==0 try if if if else if else listaCentrosCostosPorEmpresa.get(index).getCodigo()" + listaCentrosCostosPorEmpresa.get(index).getCodigo());

                        listaCentrosCostosBorrar.add(listaCentrosCostosPorEmpresa.get(index));
                    }
                    listaCentrosCostosPorEmpresa.remove(index);
                }
                if (tipoLista == 1) {
                    if (!listaCentrosCostosModificar.isEmpty() && listaCentrosCostosModificar.contains(filtrarCentrosCostos.get(index))) {
                        System.out.println("\n 6 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());

                        int modIndex = listaCentrosCostosModificar.indexOf(filtrarCentrosCostos.get(index));
                        listaCentrosCostosModificar.remove(modIndex);
                        listaCentrosCostosBorrar.add(filtrarCentrosCostos.get(index));
                    } else if (!listaCentrosCostosCrear.isEmpty() && listaCentrosCostosCrear.contains(filtrarCentrosCostos.get(index))) {
                        System.out.println("\n 7 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());
                        int crearIndex = listaCentrosCostosCrear.indexOf(filtrarCentrosCostos.get(index));
                        listaCentrosCostosCrear.remove(crearIndex);
                    } else {
                        System.out.println("\n 8 ENTRE A ControlCentroCosto.borrarCentroCosto tipolista==1 try if if if filtrarCentrosCostos.get(index).getCodigo()" + filtrarCentrosCostos.get(index).getCodigo());
                        listaCentrosCostosBorrar.add(filtrarCentrosCostos.get(index));
                    }
                    int VCIndex = listaCentrosCostosPorEmpresa.indexOf(filtrarCentrosCostos.get(index));
                    listaCentrosCostosPorEmpresa.remove(VCIndex);
                    filtrarCentrosCostos.remove(index);
                }

                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosCentrosCostos");
                index = -1;

                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.BorrarCentroCosto ERROR=====================" + e.getMessage());
        }
    }

    /**
     *
     */
    public void activarCtrlF11() {
        System.out.println("\n ENTRE A ControlCentroCosto.activarCtrlF11 \n");

        try {

            if (bandera == 0) {
                System.out.println("Activar");
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("");
                //3 COMBO BOX
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("");
                //5 COMBO BOX
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("");
                //7 COMBO BOX
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("");
                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 1;
            } else if (bandera == 1) {
                System.out.println("Desactivar");
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 COMBO BOX
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 COMBO BOX
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 COMBO BOX
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }
        } catch (Exception e) {

            System.out.println("ERROR ControlCentroCostos.activarCtrlF11 ERROR====================" + e.getMessage());
        }
    }

    /**
     * Metodo encargado de cancelar todo lo no guardado. Deja todo segun la base
     * de datos
     */
    public void salir() {
        System.out.println("\n ENTRE A ControlCentroCosto.salir \n");

        try {
            if (bandera == 1) {
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 OneMenu
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 OneMenu
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 OneMenu
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                RequestContext.getCurrentInstance().update("form:datosCentrosCostos");
                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }

            listaCentrosCostosBorrar.clear();
            listaCentrosCostosCrear.clear();
            listaCentrosCostosModificar.clear();
            index = -1;
            k = 0;
            listaCentrosCostosPorEmpresa = null;
            guardado = true;

        } catch (Exception e) {
            System.out.println("ERROR controlCentroCosto.salir ERROR=============" + e.getMessage());
        }

    }

    /**
     * ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)
     *
     * @param indice
     * @param LND
     */
    public void asignarIndex(Integer indice, int LND) {
        System.out.println("\n ENTRE A ControlCentroCosto.asignarIndex \n");
        try {
            index = indice;
            RequestContext context = RequestContext.getCurrentInstance();
            if (LND == 0) {
                tipoActualizacion = 0;
            } else if (LND == 1) {
                tipoActualizacion = 1;
                System.out.println("Tipo Actualizacion: " + tipoActualizacion);
            } else if (LND == 2) {
                tipoActualizacion = 2;
            }
            context.update("form:tiposCentrosCostosDialogo");
            context.execute("tiposCentrosCostosDialogo.show()");
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.asignarIndex ERROR======" + e.getMessage());
        }


    }

    //LOVS
    //CIUDAD
    /**
     *
     */
    public void actualizarCentroCosto() {
        System.out.println("\n ENTRE A ControlCentroCosto.actualizarCentroCosto \n");
        banderaModificacionEmpresa = 1;
        try {
            System.out.println("\n ENTRE A ControlCentroCosto.actualizarCentroCosto TIPOACTUALIZACION====" + tipoActualizacion);
            if (tipoActualizacion == 0) {
                listaCentrosCostosPorEmpresa.get(index).setTipocentrocosto(tipoCentrosCostosSeleccionado);
                if (!listaCentrosCostosCrear.contains(listaCentrosCostosPorEmpresa.get(index))) {
                    if (listaCentrosCostosModificar.isEmpty()) {
                        listaCentrosCostosModificar.add(listaCentrosCostosPorEmpresa.get(index));
                    } else if (!listaCentrosCostosModificar.contains(listaCentrosCostosPorEmpresa.get(index))) {
                        listaCentrosCostosModificar.add(listaCentrosCostosPorEmpresa.get(index));
                    }
                    if (guardado == true) {
                        guardado = false;
                        //RequestContext.getCurrentInstance().update("form:aceptar");
                    }
                }
            } else if (tipoActualizacion == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                // context.reset("formularioDialogos:nuevaTipoCentroCostos");
                System.out.println("Entro actualizar centro costo nuevo rgistro");
                nuevoCentroCosto.setTipocentrocosto(tipoCentrosCostosSeleccionado);
                System.out.println("Centro Costo Seleccionado: " + nuevoCentroCosto.getTipocentrocosto().getNombre());
                context.update("formularioDialogos:nuevaTipoCentroCostos");
            } else if (tipoActualizacion == 2) {
                duplicarCentrosCostos.setTipocentrocosto(tipoCentrosCostosSeleccionado);
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("formularioDialogos:duplicarTipoCentroCostos");
            }
            filtradoTiposCentrosCostos = null;
            tipoCentrosCostosSeleccionado = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.actualizarCentroCosto ERROR============" + e.getMessage());
        }
    }

    //SELECCIONAR UNA VIGENCIA
    public void seleccionaVigencia() {
        try {
            listaCentrosCostosPorEmpresa.clear();
            listaCentrosCostosPorEmpresa.add(CentrosCostosPorEmpresaSeleccionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");
            CentrosCostosPorEmpresaSeleccionado = null;
            filterCentrosCostosPorEmpresa = null;
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCostos.seleccionaVigencia ERROR====" + e.getMessage());
        }
    }

    public void cancelarSeleccionVigencia() {
        try {
            CentrosCostosPorEmpresaSeleccionado = null;
            filterCentrosCostosPorEmpresa = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.cancelarSeleccionVigencia ERROR====" + e.getMessage());
        }
    }

    /**
     *
     */
    public void cancelarCambioTiposCentroCosto() {
        try {
            filtradoTiposCentrosCostos = null;
            tipoCentrosCostosSeleccionado = null;
            aceptar = true;
            index = -1;
            tipoActualizacion = -1;

        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.cancelarCambioCentroCosto ERROR=====" + e.getMessage());
        }
    }

    /**
     * al momento
     */
    public void cancelarCambioBanderaDialogoModificacionEmpresa() {

        try {
            System.out.println("entre a ControlCentroCostos.cancelarCambioBanderaDialogoModificacionEmpresa");
            if (banderaModificacionEmpresa == 1) {
                //0
                codigoCC = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCC");
                codigoCC.setFilterStyle("display: none; visibility: hidden;");
                //1
                nombreCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:nombreCentroCosto");
                nombreCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //2
                tipoCentroCosto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:tipoCentroCosto");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //3 
                manoDeObra = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:manoDeObra");
                manoDeObra.setFilterStyle("display: none; visibility: hidden;");
                //4
                codigoAT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoAT");
                codigoAT.setFilterStyle("display: none; visibility: hidden;");
                //5 
                obsoleto = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:obsoleto");
                obsoleto.setFilterStyle("display: none; visibility: hidden;");
                //6
                codigoCTT = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:codigoCTT");
                tipoCentroCosto.setFilterStyle("display: none; visibility: hidden;");
                //7 
                dimensiones = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosCentrosCostos:dimensiones");
                dimensiones.setFilterStyle("display: none; visibility: hidden;");

                bandera = 0;
                filtrarCentrosCostos = null;
                tipoLista = 0;
            }

            listaCentrosCostosBorrar.clear();
            listaCentrosCostosCrear.clear();
            listaCentrosCostosModificar.clear();
            index = -1;
            k = 0;
            listaCentrosCostosPorEmpresa = null;
            guardado = true;
            banderaModificacionEmpresa = 0;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosCentrosCostos");

        } catch (Exception E) {
            System.out.println("ERROR ControlCentroCosto.banderaModificacionEmpresa ERROR====================" + E.getMessage());
        }




    }
    //--------------------------------------------------------------------------
    //METODOS MANIPULAR EMPRESA MOSTRADA
    //--------------------------------------------------------------------------

    public void cambiarEmpresaSeleccionada(int updown) {
        try {
            if (banderaModificacionEmpresa == 1) {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("confirmarCambioEmpresa.show()");
            } else if (banderaModificacionEmpresa == 0) {
                getListaEmpresas();
                for (int i = 0; i < listaEmpresas.size(); i++) {
                    System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: empresa: " + i + " nombre: " + listaEmpresas.get(i).getNombre());
                }
                System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: Entra a cambiar la empresa seleccionada");
                int temp = indiceEmpresaMostrada;
                System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: temp = " + temp);
                if (updown == 1) {
                    temp--;
                    System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: Arriba_ temp = " + temp + " lista: " + listaEmpresas.size());
                    if (temp >= 0 && temp < listaEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getListaEmpresas().get(indiceEmpresaMostrada);
                        getListaCentrosCostosPorEmpresaLOV();
                        System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listaCentrosCostosPorEmpresa = administrarCentroCostos.buscarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("formularioDialogos:buscarCentrosCostosDialogo");
                    }
                } else {
                    temp++;
                    System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: Abajo_ temp = " + temp + " lista: " + listaEmpresas.size());
                    if (temp >= 0 && temp < listaEmpresas.size()) {
                        indiceEmpresaMostrada = temp;
                        empresaSeleccionada = getListaEmpresas().get(indiceEmpresaMostrada);
                        getListaCentrosCostosPorEmpresaLOV();
                        System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: empresaSeleccionada = " + empresaSeleccionada.getNombre());
                        listaCentrosCostosPorEmpresa = administrarCentroCostos.buscarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                        System.out.println("ControlCentroCostos.cambiarEmpresaSeleccionada: Empresa cambio a: " + empresaSeleccionada.getNombre());
                        RequestContext context = RequestContext.getCurrentInstance();
                        context.update("form:nombreEmpresa");
                        context.update("form:nitEmpresa");
                        context.update("form:datosCentrosCostos");
                        context.update("formularioDialogos:buscarCentrosCostosDialogo");
                    }

                }
            }


        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCosto.cambiarEmpresaSeleccionada ERROR======" + e.getMessage());
        }

    }

    /**
     * metodo encargado para filtrar en el onemenu de manoobra
     *
     * @param data
     * @return
     */
    private void crearOpcionesFiltradoManoObra(String[] datos) {
        try {
            //SelectItem[] opciones = new SelectItem[datos.length];
            for (int i = 0; i < datos.length; i++) {
                //opciones[i] = new SelectItem(datos[i], datos[i]);
                opcionesFiltradoManoObra[i] = new SelectItem(datos[i], datos[i]);
                System.out.println("crearOpcionesFiltradoManoObra");
                System.out.println("información en el arreglo recibido: " + datos[i]);
                //System.out.println("información en el arreglo SelectItem: " + opcionesFiltradoManoObra[i].getDescription());
            }
        } catch (Exception e) {
            System.err.println("ERROR ControlCentroCostos.crearOpcionesFiltradoManoObra ERROR = " + e.getMessage());
        }
    }

    public SelectItem[] getOpcionesFiltradoManoObra() {
        return opcionesFiltradoManoObra;
    }

    //--------------------------------------------------------------------------
    //                    GETTERS AND SETTERS
    //--------------------------------------------------------------------------
    //EMPRESAS
    public List<Empresas> getListaEmpresas() {
        try {
            if (listaEmpresas == null) {
                return listaEmpresas = administrarCentroCostos.buscarEmpresas();
            } else {
                return listaEmpresas;
            }
        } catch (Exception e) {
            System.out.println("ControlNovedad: Error al recibir las empresas /n" + e.getMessage());
            return null;
        }

    }

    public void setListaEmpresas(List<Empresas> listaEmpresas) {

        this.listaEmpresas = listaEmpresas;
    }
    //EMPRESAS SELECCIONADA

    public Empresas getEmpresaSeleccionada() {
        try {
            if (empresaSeleccionada == null) {
                System.out.println("ControlCentroCostos: Entro a empresa seleccionada");
                return empresaSeleccionada = administrarCentroCostos.buscarEmpresas().get(0);
            }
        } catch (Exception e) {
            System.out.println("ERROR ControlCentroCostos.getEmpresaSeleccionada ERROR=======" + e.getMessage());
        } finally {
            return empresaSeleccionada;
        }
    }

    public void setEmpresaSeleccionada(Empresas empresaSeleccionada) {
        this.empresaSeleccionada = empresaSeleccionada;
    }

    //              FIN EMPRESAS
    // CENTROSCOSTOS
    public List<CentrosCostos> getListaCentrosCostosPorEmpresa() {
        try {
            if (listaCentrosCostosPorEmpresa == null) {
                System.out.println("empresaSeleccionada.getSecuencia()== " + empresaSeleccionada.getSecuencia());
                return listaCentrosCostosPorEmpresa = administrarCentroCostos.buscarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
            } else {
                return listaCentrosCostosPorEmpresa;
            }
        } catch (Exception e) {
            System.out.println("ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }

    }

    public void setListaCentrosCostosPorEmpresa(List<CentrosCostos> listaCentrosCostosPorEmpresa) {
        this.listaCentrosCostosPorEmpresa = listaCentrosCostosPorEmpresa;
    }

    // FIN CENTROSCOSTOS
    //NUEVO CENTRO COSTO
    public CentrosCostos getNuevoCentroCosto() {
        return nuevoCentroCosto;
    }

    public void setNuevoCentroCosto(CentrosCostos nuevoCentroCosto) {
        this.nuevoCentroCosto = nuevoCentroCosto;
    }

    //FIN NUEVO CENTRO COSTO
    // FILTRAR CENTROCOSTO
    public List<CentrosCostos> getFiltrarCentrosCostos() {
        return filtrarCentrosCostos;
    }

    public void setFiltrarCentrosCostos(List<CentrosCostos> filtrarCentrosCostos) {
        this.filtrarCentrosCostos = filtrarCentrosCostos;
    }

    //FIN FILTRAR CENTRO COSTO
    // DUPLICAR CENTRO COSTO
    public CentrosCostos getDuplicarCentrosCostos() {
        return duplicarCentrosCostos;
    }

    public void setDuplicarCentrosCostos(CentrosCostos duplicarCentrosCostos) {
        this.duplicarCentrosCostos = duplicarCentrosCostos;
    }

    // FIN DUPLICAR CENTRO COSTO
    //EDITAR CENTRO COSTO
    public CentrosCostos getEditarCentroCostos() {
        return editarCentroCostos;
    }

    public void setEditarCentroCostos(CentrosCostos editarCentroCostos) {
        this.editarCentroCostos = editarCentroCostos;
    }

    //FIN EDITAR CENTRO COSTO
    // TIPOS CENTROS COSTOS
    public List<TiposCentrosCostos> getListaTiposCentrosCostos() {
        try {
            if (listaTiposCentrosCostos == null) {
                return listaTiposCentrosCostos = administrarCentroCostos.buscarTiposCentrosCostos();
            } else {
                return listaTiposCentrosCostos;
            }
        } catch (Exception e) {
            System.out.println("ControlCentrosCostos: Error al recibir las unidades /n" + e.getMessage());
            return null;
        }


    }

    public void setListaTiposCentrosCostos(List<TiposCentrosCostos> listaTiposCentrosCostos) {
        this.listaTiposCentrosCostos = listaTiposCentrosCostos;
    }

    //FIN TIPOS CENTROS COSTOS
    // TIPOCENTROCOSTO SELECCIONADO
    public TiposCentrosCostos getTipoCentrosCostosSeleccionado() {

        return tipoCentrosCostosSeleccionado;
    }

    public void setTipoCentrosCostosSeleccionado(TiposCentrosCostos tipoCentrosCostosSeleccionado) {
        this.tipoCentrosCostosSeleccionado = tipoCentrosCostosSeleccionado;
    }

    // FIN TIPOCENTROCOSTO SELECCIONADO
    //FILTRADOTIPOCENTROSCOSTOS
    public List<TiposCentrosCostos> getFiltradoTiposCentrosCostos() {
        return filtradoTiposCentrosCostos;
    }

    public void setFiltradoTiposCentrosCostos(List<TiposCentrosCostos> filtradoTiposCentrosCostos) {
        this.filtradoTiposCentrosCostos = filtradoTiposCentrosCostos;
    }

    //FIN FILTRADOTIPOCENTROSCOSTOS
    //ACEPTAR
    public boolean isAceptar() {
        return aceptar;
    }

    public void setAceptar(boolean aceptar) {
        this.aceptar = aceptar;
    }

    // FIN ACEPTAR
    public List<CentrosCostos> getFilterCentrosCostosPorEmpresa() {
        return filterCentrosCostosPorEmpresa;
    }

    public void setFilterCentrosCostosPorEmpresa(List<CentrosCostos> filterCentrosCostosPorEmpresa) {
        this.filterCentrosCostosPorEmpresa = filterCentrosCostosPorEmpresa;
    }

    public CentrosCostos getCentrosCostosPorEmpresaSeleccionado() {
        return CentrosCostosPorEmpresaSeleccionado;
    }

    public void setCentrosCostosPorEmpresaSeleccionado(CentrosCostos CentrosCostosPorEmpresaSeleccionado) {
        this.CentrosCostosPorEmpresaSeleccionado = CentrosCostosPorEmpresaSeleccionado;
    }

    public List<CentrosCostos> getListaCentrosCostosPorEmpresaLOV() {
        System.out.println("\n ENTRE A getListaCentrosCostosPorEmpresaLOV \n");
        try {
            System.out.println("\n ENTRE A getListaCentrosCostosPorEmpresaLOV EL TRY\n");
            if (listaCentrosCostosPorEmpresaLOV == null) {
                System.out.println("getListaCentrosCostosPorEmpresaLOV EN EL TRY empresaSeleccionada.getSecuencia()== " + empresaSeleccionada.getNombre());
                listaCentrosCostosPorEmpresaLOV = administrarCentroCostos.buscarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
                System.out.println("getListaCentrosCostosPorEmpresaLOV EN EL TRY Tamaño listaCentrosCostosPorEmpresaLOV" + listaCentrosCostosPorEmpresaLOV.size());
                return listaCentrosCostosPorEmpresaLOV;
            } else {
                System.out.println("getListaCentrosCostosPorEmpresaLOV EN EL TRY Tamaño en el else listaCentrosCostosPorEmpresaLOV" + listaCentrosCostosPorEmpresaLOV.size());
                return listaCentrosCostosPorEmpresaLOV = administrarCentroCostos.buscarCentrosCostosPorEmpresa(empresaSeleccionada.getSecuencia());
            }
        } catch (Exception e) {
            System.out.println("ControlCentrosCosto: Error al recibir los CentrosCostos de la empresa seleccionada /n" + e.getMessage());
            return null;
        }
    }
    //---------------------------
    //---------------------------
}
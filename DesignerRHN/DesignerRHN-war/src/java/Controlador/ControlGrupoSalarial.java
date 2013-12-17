/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.GruposSalariales;
import Entidades.VigenciasGruposSalariales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarGrupoSalarialInterface;
import InterfaceAdministrar.AdministrarRastrosInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
public class ControlGrupoSalarial implements Serializable {

    @EJB
    AdministrarGrupoSalarialInterface administrarGrupoSalarial;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    private List<GruposSalariales> listGruposSalariales;
    private List<GruposSalariales> filtrarListGruposSalariales;
    ///////
    private List<VigenciasGruposSalariales> listVigenciasGruposSalariales;
    private List<VigenciasGruposSalariales> filtrarListVigenciasGruposSalariales;
    //Activo/Desactivo Crtl + F11
    private int bandera, banderaVGS;
    //Columnas Tabla VC
    private Column gsCodigo, gsDescripcion, gsSalario, vgsFechaVigencia, vgsValor;
    //Otros
    private boolean aceptar;
    private int index, indexVGS, indexAux, indexAuxVGS;
    //modificar
    private List<GruposSalariales> listGrupoSalarialModificar;
    private List<VigenciasGruposSalariales> listVigenciaGrupoSalarialModificar;
    private boolean guardado, guardadoVGS;
    //crear VC
    private GruposSalariales nuevoGrupoSalarial;
    private VigenciasGruposSalariales nuevoVigenciaGrupoSalarial;
    private List<GruposSalariales> listGrupoSalarialCrear;
    private List<VigenciasGruposSalariales> listVigenciaGrupoSalarialCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<GruposSalariales> listGrupoSalarialBorrar;
    private List<VigenciasGruposSalariales> listVigenciaGrupoSalarialBorrar;
    //editar celda
    private GruposSalariales editarGrupoSalarial;
    private VigenciasGruposSalariales editarVigenciaGrupoSalarial;
    private int cualCelda, tipoLista, cualCeldaVigencia, tipoListaVigencia;
    //duplicar
    private GruposSalariales duplicarGrupoSalarial;
    private VigenciasGruposSalariales duplicarVigenciaGrupoSalarial;
    private BigInteger secRegistro, secRegistroVigencia;
    private BigInteger backUpSecRegistro, backUpSecRegistroVigencia;
    private String msnConfirmarRastro, msnConfirmarRastroHistorico;
    private BigInteger backUp;
    private String nombreTablaRastro;
    private String nombreXML, nombreTabla;

    public ControlGrupoSalarial() {
        indexVGS = -1;
        backUpSecRegistro = null;
        listGruposSalariales = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listGrupoSalarialBorrar = new ArrayList<GruposSalariales>();
        listVigenciaGrupoSalarialModificar = new ArrayList<VigenciasGruposSalariales>();
        listVigenciaGrupoSalarialBorrar = new ArrayList<VigenciasGruposSalariales>();
        //crear aficiones
        listGrupoSalarialCrear = new ArrayList<GruposSalariales>();
        listVigenciaGrupoSalarialCrear = new ArrayList<VigenciasGruposSalariales>();
        k = 0;
        //modificar aficiones
        listGrupoSalarialModificar = new ArrayList<GruposSalariales>();
        //editar
        editarGrupoSalarial = new GruposSalariales();
        editarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        editarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        cualCelda = -1;
        cualCeldaVigencia = -1;
        tipoListaVigencia = 0;
        tipoLista = 0;
        //guardar
        guardado = true;
        guardadoVGS = true;
        //Crear VC
        nuevoGrupoSalarial = new GruposSalariales();
        nuevoVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        duplicarGrupoSalarial = new GruposSalariales();
        duplicarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        secRegistro = null;
        secRegistroVigencia = null;
        backUpSecRegistroVigencia = null;
        nombreTabla = ":formExportarG:datosGruposSalarialesExportar";
        nombreXML = "GruposSalarialesXML";

    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla Sets de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarGrupoSalarial(int indice) {
        if (tipoLista == 0) {
            if (!listGrupoSalarialCrear.contains(listGruposSalariales.get(indice))) {
                if (listGrupoSalarialModificar.isEmpty()) {
                    listGrupoSalarialModificar.add(listGruposSalariales.get(indice));
                } else if (!listGrupoSalarialModificar.contains(listGruposSalariales.get(indice))) {
                    listGrupoSalarialModificar.add(listGruposSalariales.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
        }
        if (tipoLista == 1) {
            if (!listGrupoSalarialCrear.contains(filtrarListGruposSalariales.get(indice))) {
                if (listGrupoSalarialModificar.isEmpty()) {
                    listGrupoSalarialModificar.add(filtrarListGruposSalariales.get(indice));
                } else if (!listGrupoSalarialModificar.contains(filtrarListGruposSalariales.get(indice))) {
                    listGrupoSalarialModificar.add(filtrarListGruposSalariales.get(indice));
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
        context.update("form:datosGrupoSalarial");
    }

    public void modificarVigenciaGrupoSalarial(int indice) {
        if (tipoListaVigencia == 0) {
            if (!listVigenciaGrupoSalarialCrear.contains(listVigenciasGruposSalariales.get(indice))) {
                if (listVigenciaGrupoSalarialModificar.isEmpty()) {
                    listVigenciaGrupoSalarialModificar.add(listVigenciasGruposSalariales.get(indice));
                } else if (!listVigenciaGrupoSalarialModificar.contains(listVigenciasGruposSalariales.get(indice))) {
                    listVigenciaGrupoSalarialModificar.add(listVigenciasGruposSalariales.get(indice));
                }
                if (guardadoVGS == true) {
                    guardadoVGS = false;
                }
            }
        }
        if (tipoListaVigencia == 1) {
            if (!listVigenciaGrupoSalarialCrear.contains(filtrarListVigenciasGruposSalariales.get(indice))) {
                if (listVigenciaGrupoSalarialModificar.isEmpty()) {
                    listVigenciaGrupoSalarialModificar.add(filtrarListVigenciasGruposSalariales.get(indice));
                } else if (!listVigenciaGrupoSalarialModificar.contains(filtrarListVigenciasGruposSalariales.get(indice))) {
                    listVigenciaGrupoSalarialModificar.add(filtrarListVigenciasGruposSalariales.get(indice));
                }
                if (guardadoVGS == true) {
                    guardadoVGS = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        indexVGS = -1;
        secRegistroVigencia = null;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaGrupoSalarial");
    }

    public void cambiarIndice(int indice, int celda) {
        if (guardadoVGS == true) {
            index = indice;
            cualCelda = celda;
            indexAux = indice;
            indexAuxVGS = -1;
            secRegistro = listGruposSalariales.get(index).getSecuencia();
            listVigenciasGruposSalariales = null;
            getListVigenciasGruposSalariales();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoSalarial");
            if (banderaVGS == 1) {
                vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
                vgsValor.setFilterStyle("display: none; visibility: hidden;");
                vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
                vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
                banderaVGS = 0;
                filtrarListVigenciasGruposSalariales = null;
                tipoListaVigencia = 0;
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:confirmarGuardar");
            context.execute("confirmarGuardar.show()");
        }
    }

    public void cambiarIndiceVigencia(int indice, int celda) {
        indexVGS = indice;
        index = -1;
        indexAuxVGS = indice;
        cualCeldaVigencia = celda;
        secRegistroVigencia = listVigenciasGruposSalariales.get(indexVGS).getSecuencia();
        if (bandera == 1) {
            //CERRAR FILTRADO
            gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
            gsCodigo.setFilterStyle("display: none; visibility: hidden;");
            gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
            gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
            gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
            gsSalario.setFilterStyle("display: none; visibility: hidden;");
            ////
            RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }
    }
    //GUARDAR

    public void guardarGeneral() {
        guardarCambiosGrupoSalarial();
        guardarCambiosVigenciaGrupoSalarial();
    }

    public void guardarCambiosGrupoSalarial() {
        if (guardado == false) {
            if (!listGrupoSalarialBorrar.isEmpty()) {
                for (int i = 0; i < listGrupoSalarialBorrar.size(); i++) {
                    administrarGrupoSalarial.borrarGruposSalariales(listGrupoSalarialBorrar);
                }
                listGrupoSalarialBorrar.clear();
            }
            if (!listGrupoSalarialCrear.isEmpty()) {
                for (int i = 0; i < listGrupoSalarialCrear.size(); i++) {
                    administrarGrupoSalarial.crearGruposSalariales(listGrupoSalarialCrear);
                }
                listGrupoSalarialCrear.clear();
            }
            if (!listGrupoSalarialModificar.isEmpty()) {
                administrarGrupoSalarial.editarGruposSalariales(listGrupoSalarialModificar);
                listGrupoSalarialModificar.clear();
            }
            listGruposSalariales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosGrupoSalarial");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
        secRegistro = null;
    }

    public void guardarCambiosVigenciaGrupoSalarial() {
        if (guardadoVGS == false) {
            if (!listVigenciaGrupoSalarialBorrar.isEmpty()) {
                for (int i = 0; i < listVigenciaGrupoSalarialBorrar.size(); i++) {
                    administrarGrupoSalarial.borrarVigenciasGruposSalariales(listVigenciaGrupoSalarialBorrar);
                }
                listVigenciaGrupoSalarialBorrar.clear();
            }
            if (!listVigenciaGrupoSalarialCrear.isEmpty()) {
                for (int i = 0; i < listVigenciaGrupoSalarialCrear.size(); i++) {
                    administrarGrupoSalarial.crearVigenciasGruposSalariales(listVigenciaGrupoSalarialCrear);
                }
                listVigenciaGrupoSalarialCrear.clear();
            }
            if (!listVigenciaGrupoSalarialModificar.isEmpty()) {
                administrarGrupoSalarial.editarVigenciasGruposSalariales(listVigenciaGrupoSalarialModificar);
                listVigenciaGrupoSalarialModificar.clear();
            }
            listVigenciasGruposSalariales = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoSalarial");
            guardadoVGS = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        indexVGS = -1;
        secRegistroVigencia = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacionGeneral() {
        if (guardado == false) {
            cancelarModificacionGrupoSalarial();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosGrupoSalarial");
        }
        if (guardadoVGS == false) {
            cancelarModificacionVigenciaGrupoSalarial();
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoSalarial");
        }
    }

    public void cancelarModificacionGrupoSalarial() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
            gsCodigo.setFilterStyle("display: none; visibility: hidden;");
            gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
            gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
            gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
            gsSalario.setFilterStyle("display: none; visibility: hidden;");
            ////
            RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }
        listGrupoSalarialBorrar.clear();
        listGrupoSalarialCrear.clear();
        listGrupoSalarialModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listGruposSalariales = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosGrupoSalarial");
    }

    public void cancelarModificacionVigenciaGrupoSalarial() {
        if (banderaVGS == 1) {
            //CERRAR FILTRADO
            vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
            vgsValor.setFilterStyle("display: none; visibility: hidden;");
            vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
            vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
            banderaVGS = 0;
            filtrarListVigenciasGruposSalariales = null;
            tipoListaVigencia = 0;
        }
        listVigenciaGrupoSalarialBorrar.clear();
        listVigenciaGrupoSalarialCrear.clear();
        listVigenciaGrupoSalarialModificar.clear();
        indexVGS = -1;
        secRegistroVigencia = null;
        k = 0;
        listVigenciasGruposSalariales = null;
        guardadoVGS = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaGrupoSalarial");
    }

    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarGrupoSalarial = listGruposSalariales.get(index);
            }
            if (tipoLista == 1) {
                editarGrupoSalarial = filtrarListGruposSalariales.get(index);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarCodigoD");
                context.execute("editarCodigoD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarDescripcionD");
                context.execute("editarDescripcionD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarSalarioD");
                context.execute("editarSalarioD.show()");
                cualCelda = -1;
            }
            index = -1;
            secRegistro = null;
        }
        if (indexAuxVGS >= 0) {
            if (tipoListaVigencia == 0) {
                editarVigenciaGrupoSalarial = listVigenciasGruposSalariales.get(indexVGS);
            }
            if (tipoListaVigencia == 1) {
                editarVigenciaGrupoSalarial = listVigenciasGruposSalariales.get(indexVGS);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCeldaVigencia == 0) {
                context.update("formularioDialogos:editarFechaVigenciaD");
                context.execute("editarFechaVigenciaD.show()");
                cualCeldaVigencia = -1;
            } else if (cualCeldaVigencia == 1) {
                context.update("formularioDialogos:editarValorD");
                context.execute("editarValorD.show()");
                cualCeldaVigencia = -1;
            }
            indexVGS = -1;
            secRegistroVigencia = null;
        }

    }

    public void dialogoNuevoRegistro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listGruposSalariales.isEmpty() || listVigenciasGruposSalariales.isEmpty()) {
            context.update("formularioDialogos:verificarNuevoRegistro");
            context.execute("verificarNuevoRegistro.show()");
        } else {
            if (index >= 0) {
                context.update("formularioDialogos:NuevoRegistroGrupoSalarial");
                context.execute("NuevoRegistroGrupoSalarial.show()");
            }
            if (indexAuxVGS >= 0) {
                context.update("formularioDialogos:NuevoRegistroVigenciaGrupoSalarial");
                context.execute("NuevoRegistroVigenciaGrupoSalarial.show()");
            }
        }
    }

    //CREAR 
    public void agregarNuevoGrupoSalarial() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
            gsCodigo.setFilterStyle("display: none; visibility: hidden;");
            gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
            gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
            gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
            gsSalario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevoGrupoSalarial.setSecuencia(l);
        listGrupoSalarialCrear.add(nuevoGrupoSalarial);
        listGruposSalariales.add(nuevoGrupoSalarial);
        nuevoGrupoSalarial = new GruposSalariales();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosGrupoSalarial");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        index = -1;
        secRegistro = null;
    }

    public void agregarNuevoVigenciaGrupoSalarial() {
        if (banderaVGS == 1) {
            //CERRAR FILTRADO
            vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
            vgsValor.setFilterStyle("display: none; visibility: hidden;");
            vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
            vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
            banderaVGS = 0;
            filtrarListVigenciasGruposSalariales = null;
            tipoListaVigencia = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevoVigenciaGrupoSalarial.setSecuencia(l);
        nuevoVigenciaGrupoSalarial.setGruposalarial(listGruposSalariales.get(indexAux));
        listVigenciaGrupoSalarialCrear.add(nuevoVigenciaGrupoSalarial);
        listVigenciasGruposSalariales.add(nuevoVigenciaGrupoSalarial);
        nuevoVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaGrupoSalarial");
        if (guardadoVGS == true) {
            guardadoVGS = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        indexVGS = -1;
        secRegistroVigencia = null;
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     */
    public void limpiarNuevaGrupoSalarial() {
        nuevoGrupoSalarial = new GruposSalariales();
        index = -1;
        secRegistro = null;
    }

    public void limpiarNuevaVigenciaGrupoSalarial() {
        nuevoVigenciaGrupoSalarial = new VigenciasGruposSalariales();
        indexVGS = -1;
        secRegistroVigencia = null;
    }

    //DUPLICAR VC
    /**
     */
    public void verificarRegistroDuplicar() {
        if (index >= 0) {
            duplicarGrupoSalarialM();
        }
        if (indexAuxVGS >= 0) {
            duplicarVigenciaGrupoSalarialM();
        }
    }

    public void duplicarGrupoSalarialM() {
        if (index >= 0) {
            duplicarGrupoSalarial = new GruposSalariales();
            k++;
            l = BigInteger.valueOf(k);
            if (tipoLista == 0) {
                duplicarGrupoSalarial.setSecuencia(l);
                duplicarGrupoSalarial.setCodigo(listGruposSalariales.get(index).getCodigo());
                duplicarGrupoSalarial.setDescripcion(listGruposSalariales.get(index).getDescripcion());
                duplicarGrupoSalarial.setEscalafonsalarial(listGruposSalariales.get(index).getEscalafonsalarial());
                duplicarGrupoSalarial.setSalario(listGruposSalariales.get(index).getSalario());
            }
            if (tipoLista == 1) {
                duplicarGrupoSalarial.setSecuencia(l);
                duplicarGrupoSalarial.setCodigo(filtrarListGruposSalariales.get(index).getCodigo());
                duplicarGrupoSalarial.setDescripcion(filtrarListGruposSalariales.get(index).getDescripcion());
                duplicarGrupoSalarial.setEscalafonsalarial(filtrarListGruposSalariales.get(index).getEscalafonsalarial());
                duplicarGrupoSalarial.setSalario(filtrarListGruposSalariales.get(index).getSalario());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroGrupoSalarial");
            context.execute("DuplicarRegistroGrupoSalarial.show()");
            index = -1;
            secRegistro = null;
        }
    }

    public void duplicarVigenciaGrupoSalarialM() {
        if (indexAuxVGS >= 0) {
            duplicarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
            k++;
            indexVGS = indexAuxVGS;
            l = BigInteger.valueOf(k);
            if (tipoListaVigencia == 0) {
                duplicarVigenciaGrupoSalarial.setSecuencia(l);
                duplicarVigenciaGrupoSalarial.setFechavigencia(listVigenciasGruposSalariales.get(indexVGS).getFechavigencia());
                duplicarVigenciaGrupoSalarial.setGruposalarial(listVigenciasGruposSalariales.get(indexVGS).getGruposalarial());
                duplicarVigenciaGrupoSalarial.setValor(listVigenciasGruposSalariales.get(indexVGS).getValor());
            }
            if (tipoListaVigencia == 1) {
                duplicarVigenciaGrupoSalarial.setSecuencia(l);
                duplicarVigenciaGrupoSalarial.setFechavigencia(filtrarListVigenciasGruposSalariales.get(indexVGS).getFechavigencia());
                duplicarVigenciaGrupoSalarial.setGruposalarial(filtrarListVigenciasGruposSalariales.get(indexVGS).getGruposalarial());
                duplicarVigenciaGrupoSalarial.setValor(filtrarListVigenciasGruposSalariales.get(indexVGS).getValor());
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:DuplicarRegistroVigenciaGrupoSalarial");
            context.execute("DuplicarRegistroVigenciaGrupoSalarial.show()");
            indexVGS = -1;
            secRegistroVigencia = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicarGrupoSalarial() {

        listGruposSalariales.add(duplicarGrupoSalarial);
        listGrupoSalarialCrear.add(duplicarGrupoSalarial);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosGrupoSalarial");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
            gsCodigo.setFilterStyle("display: none; visibility: hidden;");
            gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
            gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
            gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
            gsSalario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }
        duplicarGrupoSalarial = new GruposSalariales();
    }

    public void confirmarDuplicarVigenciaGrupoSalarial() {

        duplicarVigenciaGrupoSalarial.setGruposalarial(listGruposSalariales.get(indexAux));
        listVigenciasGruposSalariales.add(duplicarVigenciaGrupoSalarial);
        listVigenciaGrupoSalarialCrear.add(duplicarVigenciaGrupoSalarial);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciaGrupoSalarial");
        indexVGS = -1;
        secRegistroVigencia = null;
        if (guardadoVGS == true) {
            guardadoVGS = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
            vgsValor.setFilterStyle("display: none; visibility: hidden;");
            vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
            vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }
        duplicarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Set
     */
    public void limpiarDuplicarGrupoSalarial() {
        duplicarGrupoSalarial = new GruposSalariales();
    }

    public void limpiarDuplicarVigenciaGrupoSalarial() {
        duplicarVigenciaGrupoSalarial = new VigenciasGruposSalariales();
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
            if (listVigenciasGruposSalariales.isEmpty()) {
                borrarGrupoSalarial();
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorBorrarRegistro.show()");
            }
        }
        if (indexAuxVGS >= 0) {
            borrarVigenciaGrupoSalarial();
        }
    }

    public void borrarGrupoSalarial() {
        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listGrupoSalarialModificar.isEmpty() && listGrupoSalarialModificar.contains(listGruposSalariales.get(index))) {
                    int modIndex = listGrupoSalarialModificar.indexOf(listGruposSalariales.get(index));
                    listGrupoSalarialModificar.remove(modIndex);
                    listGrupoSalarialBorrar.add(listGruposSalariales.get(index));
                } else if (!listGrupoSalarialCrear.isEmpty() && listGrupoSalarialCrear.contains(listGruposSalariales.get(index))) {
                    int crearIndex = listGrupoSalarialCrear.indexOf(listGruposSalariales.get(index));
                    listGrupoSalarialCrear.remove(crearIndex);
                } else {
                    listGrupoSalarialBorrar.add(listGruposSalariales.get(index));
                }
                listGruposSalariales.remove(index);
            }
            if (tipoLista == 1) {
                if (!listGrupoSalarialModificar.isEmpty() && listGrupoSalarialModificar.contains(filtrarListGruposSalariales.get(index))) {
                    int modIndex = listGrupoSalarialModificar.indexOf(filtrarListGruposSalariales.get(index));
                    listGrupoSalarialModificar.remove(modIndex);
                    listGrupoSalarialBorrar.add(filtrarListGruposSalariales.get(index));
                } else if (!listGrupoSalarialCrear.isEmpty() && listGrupoSalarialCrear.contains(filtrarListGruposSalariales.get(index))) {
                    int crearIndex = listGrupoSalarialCrear.indexOf(filtrarListGruposSalariales.get(index));
                    listGrupoSalarialCrear.remove(crearIndex);
                } else {
                    listGrupoSalarialBorrar.add(filtrarListGruposSalariales.get(index));
                }
                int VCIndex = listGruposSalariales.indexOf(filtrarListGruposSalariales.get(index));
                listGruposSalariales.remove(VCIndex);
                filtrarListGruposSalariales.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosGrupoSalarial");
            index = -1;
            secRegistro = null;

            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
        }
    }

    public void borrarVigenciaGrupoSalarial() {
        if (indexAuxVGS >= 0) {
            if (tipoListaVigencia == 0) {
                if (!listVigenciaGrupoSalarialModificar.isEmpty() && listVigenciaGrupoSalarialModificar.contains(listVigenciasGruposSalariales.get(indexVGS))) {
                    int modIndex = listVigenciaGrupoSalarialModificar.indexOf(listVigenciasGruposSalariales.get(indexVGS));
                    listVigenciaGrupoSalarialModificar.remove(modIndex);
                    listVigenciaGrupoSalarialBorrar.add(listVigenciasGruposSalariales.get(indexVGS));
                } else if (!listVigenciaGrupoSalarialCrear.isEmpty() && listVigenciaGrupoSalarialCrear.contains(listVigenciasGruposSalariales.get(indexVGS))) {
                    int crearIndex = listVigenciaGrupoSalarialCrear.indexOf(listVigenciasGruposSalariales.get(indexVGS));
                    listVigenciaGrupoSalarialCrear.remove(crearIndex);
                } else {
                    listVigenciaGrupoSalarialBorrar.add(listVigenciasGruposSalariales.get(indexVGS));
                }
                listVigenciasGruposSalariales.remove(indexVGS);
            }
            if (tipoListaVigencia == 1) {
                if (!listVigenciaGrupoSalarialModificar.isEmpty() && listVigenciaGrupoSalarialModificar.contains(filtrarListVigenciasGruposSalariales.get(indexVGS))) {
                    int modIndex = listVigenciaGrupoSalarialModificar.indexOf(filtrarListVigenciasGruposSalariales.get(indexVGS));
                    listVigenciaGrupoSalarialModificar.remove(modIndex);
                    listVigenciaGrupoSalarialBorrar.add(filtrarListVigenciasGruposSalariales.get(indexVGS));
                } else if (!listVigenciaGrupoSalarialCrear.isEmpty() && listVigenciaGrupoSalarialCrear.contains(filtrarListVigenciasGruposSalariales.get(indexVGS))) {
                    int crearIndex = listVigenciaGrupoSalarialCrear.indexOf(filtrarListVigenciasGruposSalariales.get(indexVGS));
                    listVigenciaGrupoSalarialCrear.remove(crearIndex);
                } else {
                    listVigenciaGrupoSalarialBorrar.add(filtrarListVigenciasGruposSalariales.get(indexVGS));
                }
                int VCIndex = listVigenciasGruposSalariales.indexOf(filtrarListVigenciasGruposSalariales.get(indexVGS));
                listVigenciasGruposSalariales.remove(VCIndex);
                filtrarListVigenciasGruposSalariales.remove(indexVGS);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciaGrupoSalarial");
            indexVGS = -1;
            secRegistroVigencia = null;

            if (guardadoVGS == true) {
                guardadoVGS = false;
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
        if (index >= 0) {
            if (bandera == 0) {
                gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
                gsCodigo.setFilterStyle("width: 120px");
                gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
                gsDescripcion.setFilterStyle("width: 120px");
                gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
                gsSalario.setFilterStyle("width: 120px");
                RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
                bandera = 1;
            } else if (bandera == 1) {
                gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
                gsCodigo.setFilterStyle("display: none; visibility: hidden;");
                gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
                gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
                gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
                gsSalario.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
                bandera = 0;
                filtrarListGruposSalariales = null;
                tipoLista = 0;
            }
        }
        if (indexAuxVGS >= 0) {
            if (banderaVGS == 0) {

                vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
                vgsValor.setFilterStyle("width: 180px");
                vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
                vgsFechaVigencia.setFilterStyle("width: 180px");
                RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
                banderaVGS = 1;
            } else if (banderaVGS == 1) {
                vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
                vgsValor.setFilterStyle("display: none; visibility: hidden;");
                vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
                vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
                RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
                banderaVGS = 0;
                filtrarListVigenciasGruposSalariales = null;
                tipoListaVigencia = 0;
            }
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            gsCodigo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsCodigo");
            gsCodigo.setFilterStyle("display: none; visibility: hidden;");
            gsDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsDescripcion");
            gsDescripcion.setFilterStyle("display: none; visibility: hidden;");
            gsSalario = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosGrupoSalarial:gsSalario");
            gsSalario.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosGrupoSalarial");
            bandera = 0;
            filtrarListGruposSalariales = null;
            tipoLista = 0;
        }

        if (banderaVGS == 1) {
            vgsValor = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsValor");
            vgsValor.setFilterStyle("display: none; visibility: hidden;");
            vgsFechaVigencia = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciaGrupoSalarial:vgsFechaVigencia");
            vgsFechaVigencia.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciaGrupoSalarial");
            banderaVGS = 0;
            filtrarListVigenciasGruposSalariales = null;
            tipoListaVigencia = 0;
        }

        listGrupoSalarialBorrar.clear();
        listGrupoSalarialCrear.clear();
        listGrupoSalarialModificar.clear();
        listVigenciaGrupoSalarialBorrar.clear();
        listVigenciaGrupoSalarialCrear.clear();
        listVigenciaGrupoSalarialModificar.clear();
        index = -1;
        indexAux = -1;
        indexVGS = -1;
        indexAuxVGS = -1;
        secRegistroVigencia = null;
        secRegistro = null;
        k = 0;
        listGruposSalariales = null;
        listVigenciasGruposSalariales = null;
        guardado = true;
        guardadoVGS = true;
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
            nombreTabla = ":formExportarG:datosGruposSalarialesExportar";
            nombreXML = "GruposSalarialesXML";
        }
        if (indexAuxVGS >= 0) {
            nombreTabla = ":formExportarVG:datosVigenciaGruposSalarialesExportar";
            nombreXML = "VigenciasGruposSalarialesXML";
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
            exportPDF_GS();
        }
        if (indexAuxVGS >= 0) {
            exportPDF_VGS();
        }
    }

    public void exportPDF_GS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarG:datosGruposSalarialesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "GruposSalarialesPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportPDF_VGS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVG:datosVigenciaGruposSalarialesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasGruposSalarialesPDF", false, false, "UTF-8", null, null);
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
            exportXLS_GS();
        }
        if (indexAuxVGS >= 0) {
            exportXLS_VGS();
        }
    }

    public void exportXLS_GS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportarVG:datosVigenciaGruposSalarialesExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "GruposSalarialesXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    public void exportXLS_VGS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSetsEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasGruposSalarialesXLS", false, false, "UTF-8", null, null);
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
        if (indexAuxVGS >= 0) {
            if (tipoListaVigencia == 0) {
                tipoListaVigencia = 1;
            }
        }
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        if (listVigenciasGruposSalariales == null || listGruposSalariales == null) {
        } else {
            if (index >= 0) {
                verificarRastroGrupoSalarial();
                index = -1;
            }
            if (indexAuxVGS >= 0) {
                verificarRastroVigenciaGrupoSalarial();
                indexVGS = -1;
            }
        }
    }

    public void verificarRastroGrupoSalarial() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listGruposSalariales != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "GRUPOSSALARIALES");
                backUpSecRegistro = secRegistro;
                backUp = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "GruposSalariales";
                    msnConfirmarRastro = "La tabla GRUPOSSALARIALES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("GRUPOSSALARIALES")) {
                nombreTablaRastro = "GruposSalariales";
                msnConfirmarRastroHistorico = "La tabla GRUPOSSALARIALES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    public void verificarRastroVigenciaGrupoSalarial() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasGruposSalariales != null) {
            if (secRegistroVigencia != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistroVigencia, "VIGENCIASGRUPOSSALARIALES");
                backUpSecRegistroVigencia = secRegistroVigencia;
                backUp = secRegistroVigencia;
                secRegistroVigencia = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
                    nombreTablaRastro = "VigenciasGruposSalariales";
                    msnConfirmarRastro = "La tabla VIGENCIASGRUPOSSALARIALES tiene rastros para el registro seleccionado, ¿desea continuar?";
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASGRUPOSSALARIALES")) {
                nombreTablaRastro = "VigenciasGruposSalariales";
                msnConfirmarRastroHistorico = "La tabla VIGENCIASGRUPOSSALARIALES tiene rastros historicos, ¿Desea continuar?";
                context.update("form:confirmarRastroHistorico");
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }
        }
        index = -1;
    }

    //GETTERS AND SETTERS
    public List<GruposSalariales> getListGruposSalariales() {
        try {
            if (listGruposSalariales == null) {
                listGruposSalariales = new ArrayList<GruposSalariales>();
                listGruposSalariales = administrarGrupoSalarial.listGruposSalariales();
                return listGruposSalariales;
            } else {
                return listGruposSalariales;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListGruposSalariales ");
            return null;
        }
    }

    public void setListGruposSalariales(List<GruposSalariales> ListGruposSalariales) {
        this.listGruposSalariales = ListGruposSalariales;
    }

    public List<GruposSalariales> getFiltrarListGruposSalariales() {
        return filtrarListGruposSalariales;
    }

    public void setFiltrarListGruposSalariales(List<GruposSalariales> ListGruposSalariales) {
        this.filtrarListGruposSalariales = ListGruposSalariales;
    }

    public GruposSalariales getNuevoGrupoSalarial() {
        return nuevoGrupoSalarial;
    }

    public void setNuevoGrupoSalarial(GruposSalariales GrupoSalarial) {
        this.nuevoGrupoSalarial = GrupoSalarial;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public GruposSalariales getEditarGrupoSalarial() {
        return editarGrupoSalarial;
    }

    public void setEditarGrupoSalarial(GruposSalariales GrupoSalarial) {
        this.editarGrupoSalarial = GrupoSalarial;
    }

    public GruposSalariales getDuplicarGrupoSalarial() {
        return duplicarGrupoSalarial;
    }

    public void setDuplicarGrupoSalarial(GruposSalariales duplicarGrupoSalarial) {
        this.duplicarGrupoSalarial = duplicarGrupoSalarial;
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

    public List<VigenciasGruposSalariales> getListVigenciasGruposSalariales() {
        if (listVigenciasGruposSalariales == null) {
            listVigenciasGruposSalariales = new ArrayList<VigenciasGruposSalariales>();
            index = indexAux;
            listVigenciasGruposSalariales = administrarGrupoSalarial.lisVigenciasGruposSalarialesSecuencia(listGruposSalariales.get(index).getSecuencia());
        }
        return listVigenciasGruposSalariales;
    }

    public void setListVigenciasGruposSalariales(List<VigenciasGruposSalariales> listVigenciasGruposSalariales) {
        this.listVigenciasGruposSalariales = listVigenciasGruposSalariales;
    }

    public List<VigenciasGruposSalariales> getFiltrarListVigenciasGruposSalariales() {
        return filtrarListVigenciasGruposSalariales;
    }

    public void setFiltrarListVigenciasGruposSalariales(List<VigenciasGruposSalariales> filtrarListVigenciasGruposSalariales) {
        this.filtrarListVigenciasGruposSalariales = filtrarListVigenciasGruposSalariales;
    }

    public List<GruposSalariales> getListGrupoSalarialModificar() {
        return listGrupoSalarialModificar;
    }

    public void setListGrupoSalarialModificar(List<GruposSalariales> listGrupoSalarialModificar) {
        this.listGrupoSalarialModificar = listGrupoSalarialModificar;
    }

    public List<GruposSalariales> getListGrupoSalarialCrear() {
        return listGrupoSalarialCrear;
    }

    public void setListGrupoSalarialCrear(List<GruposSalariales> listGrupoSalarialCrear) {
        this.listGrupoSalarialCrear = listGrupoSalarialCrear;
    }

    public List<GruposSalariales> getListGrupoSalarialBorrar() {
        return listGrupoSalarialBorrar;
    }

    public void setListGrupoSalarialBorrar(List<GruposSalariales> listGrupoSalarialBorrar) {
        this.listGrupoSalarialBorrar = listGrupoSalarialBorrar;
    }

    public List<VigenciasGruposSalariales> getListVigenciaGrupoSalarialModificar() {
        return listVigenciaGrupoSalarialModificar;
    }

    public void setListVigenciaGrupoSalarialModificar(List<VigenciasGruposSalariales> listVigenciaGrupoSalariaModificar) {
        this.listVigenciaGrupoSalarialModificar = listVigenciaGrupoSalariaModificar;
    }

    public VigenciasGruposSalariales getNuevoVigenciaGrupoSalarial() {
        return nuevoVigenciaGrupoSalarial;
    }

    public void setNuevoVigenciaGrupoSalarial(VigenciasGruposSalariales nuevoVigenciaGrupoSalarial) {
        this.nuevoVigenciaGrupoSalarial = nuevoVigenciaGrupoSalarial;
    }

    public List<VigenciasGruposSalariales> getListVigenciaGrupoSalarialCrear() {
        return listVigenciaGrupoSalarialCrear;
    }

    public void setListVigenciaGrupoSalarialCrear(List<VigenciasGruposSalariales> listVigenciaGrupoSalarialCrear) {
        this.listVigenciaGrupoSalarialCrear = listVigenciaGrupoSalarialCrear;
    }

    public List<VigenciasGruposSalariales> getListVigenciaGrupoSalarialBorrar() {
        return listVigenciaGrupoSalarialBorrar;
    }

    public void setListVigenciaGrupoSalarialBorrar(List<VigenciasGruposSalariales> listVigenciaGrupoSalarialBorrar) {
        this.listVigenciaGrupoSalarialBorrar = listVigenciaGrupoSalarialBorrar;
    }

    public VigenciasGruposSalariales getEditarVigenciaGrupoSalarial() {
        return editarVigenciaGrupoSalarial;
    }

    public void setEditarVigenciaGrupoSalarial(VigenciasGruposSalariales editarVigenciaGrupoSalarial) {
        this.editarVigenciaGrupoSalarial = editarVigenciaGrupoSalarial;
    }

    public VigenciasGruposSalariales getDuplicarVigenciaGrupoSalarial() {
        return duplicarVigenciaGrupoSalarial;
    }

    public void setDuplicarVigenciaGrupoSalarial(VigenciasGruposSalariales duplicarVigenciaGrupoSalarial) {
        this.duplicarVigenciaGrupoSalarial = duplicarVigenciaGrupoSalarial;
    }

    public BigInteger getSecRegistroVigencia() {
        return secRegistroVigencia;
    }

    public void setSecRegistroVigencia(BigInteger secRegistroVigencia) {
        this.secRegistroVigencia = secRegistroVigencia;
    }

    public BigInteger getBackUpSecRegistroVigencia() {
        return backUpSecRegistroVigencia;
    }

    public void setBackUpSecRegistroVigencia(BigInteger backUpSecRegistroVigencia) {
        this.backUpSecRegistroVigencia = backUpSecRegistroVigencia;
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
}

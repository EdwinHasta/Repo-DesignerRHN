/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Empleados;
import Entidades.GruposInfAdicionales;
import Entidades.InformacionesAdicionales;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarEmplInformacionAdicionalInterface;
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
public class ControlEmplInformacionAdicional implements Serializable {

    @EJB
    AdministrarEmplInformacionAdicionalInterface administrarEmplInformacionAdicional;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Contratos
    private List<InformacionesAdicionales> listInformacionAdicional;
    private List<InformacionesAdicionales> filtrarListInformacionAdicional;
    private List<GruposInfAdicionales> listGruposInfAdicional;
    private GruposInfAdicionales grupoSelecionado;
    private List<GruposInfAdicionales> filtrarListGruposInfAdicional;
    private Empleados empleado;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column vcFechaInicial, vcFechaFinal, vcContrato, vcTipoContrato;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<InformacionesAdicionales> listInfoAdicionalModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public InformacionesAdicionales nuevaInfoAdicional;
    private List<InformacionesAdicionales> listInfoAdicionalCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<InformacionesAdicionales> listInfoAdicionalBorrar;
    //editar celda
    private InformacionesAdicionales editarInfoAdicional;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private InformacionesAdicionales duplicarInfoAdicional;
    //String Variables AutoCompletar
    private String grupo;
    //Boolean AutoCompletar
    private boolean permitirIndex;
    private BigInteger backUpSecRegistro;
    private BigInteger secRegistro;

    public ControlEmplInformacionAdicional() {
        secRegistro = null;
        backUpSecRegistro = null;
        listInformacionAdicional = null;
        listGruposInfAdicional = new ArrayList<GruposInfAdicionales>();
        empleado = new Empleados();
        //Otros
        aceptar = true;
        //borrar aficiones
        listInfoAdicionalBorrar = new ArrayList<InformacionesAdicionales>();
        //crear aficiones
        listInfoAdicionalCrear = new ArrayList<InformacionesAdicionales>();
        k = 0;
        //modificar aficiones
        listInfoAdicionalModificar = new ArrayList<InformacionesAdicionales>();
        //editar
        editarInfoAdicional = new InformacionesAdicionales();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar 
        guardado = true;
        //Crear VC
        nuevaInfoAdicional = new InformacionesAdicionales();
        nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
        permitirIndex = true;
    }
    //EMPLEADO DE LA VIGENCIA

    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(BigInteger empl) {
        listInformacionAdicional = null;
        empleado = administrarEmplInformacionAdicional.empleadoActual(empl);
    }

    public void modificarInfoAd(int indice) {
        if (tipoLista == 0) {
            if (!listInfoAdicionalCrear.contains(listInformacionAdicional.get(indice))) {

                if (listInfoAdicionalModificar.isEmpty()) {
                    listInfoAdicionalModificar.add(listInformacionAdicional.get(indice));
                } else if (!listInfoAdicionalModificar.contains(listInformacionAdicional.get(indice))) {
                    listInfoAdicionalModificar.add(listInformacionAdicional.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        }
        if (tipoLista == 1) {
            if (!listInfoAdicionalCrear.contains(filtrarListInformacionAdicional.get(indice))) {

                if (listInfoAdicionalModificar.isEmpty()) {
                    listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(indice));
                } else if (!listInfoAdicionalModificar.contains(filtrarListInformacionAdicional.get(indice))) {
                    listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla VigenciasContratos
     * de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarInfoAd(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPO")) {
            if (tipoLista == 0) {
                listInformacionAdicional.get(indice).getGrupo().setDescripcion(grupo);
            } else {
                filtrarListInformacionAdicional.get(indice).getGrupo().setDescripcion(grupo);
            }
            for (int i = 0; i < listGruposInfAdicional.size(); i++) {
                if (listGruposInfAdicional.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listInformacionAdicional.get(indice).setGrupo(listGruposInfAdicional.get(indiceUnicoElemento));
                } else {
                    filtrarListInformacionAdicional.get(indice).setGrupo(listGruposInfAdicional.get(indiceUnicoElemento));
                }
                listGruposInfAdicional.clear();
                getListGruposInfAdicional();
            } else {
                permitirIndex = false;
                context.update("form:GrupoDialogo");
                context.execute("GrupoDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listInfoAdicionalCrear.contains(listInformacionAdicional.get(indice))) {

                    if (listInfoAdicionalModificar.isEmpty()) {
                        listInfoAdicionalModificar.add(listInformacionAdicional.get(indice));
                    } else if (!listInfoAdicionalModificar.contains(listInformacionAdicional.get(indice))) {
                        listInfoAdicionalModificar.add(listInformacionAdicional.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listInfoAdicionalCrear.contains(filtrarListInformacionAdicional.get(indice))) {

                    if (listInfoAdicionalModificar.isEmpty()) {
                        listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(indice));
                    } else if (!listInfoAdicionalModificar.contains(filtrarListInformacionAdicional.get(indice))) {
                        listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosInfoAdEmpleado");
    }

    /**
     * Metodo que obtiene los valores de los dialogos para realizar los
     * autocomplete de los campos
     *
     * @param tipoNuevo Tipo de operacion: Nuevo Registro - Duplicar Registro
     * @param Campo Campo que toma el cambio de autocomplete
     */
    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("GRUPO")) {
            if (tipoNuevo == 1) {
                grupo = nuevaInfoAdicional.getGrupo().getDescripcion();
            } else if (tipoNuevo == 2) {
                grupo = duplicarInfoAdicional.getGrupo().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("GRUPO")) {
            if (tipoNuevo == 1) {
                nuevaInfoAdicional.getGrupo().setDescripcion(grupo);
            } else if (tipoNuevo == 2) {
                duplicarInfoAdicional.getGrupo().setDescripcion(grupo);
            }
            for (int i = 0; i < listGruposInfAdicional.size(); i++) {
                if (listGruposInfAdicional.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaInfoAdicional.setGrupo(listGruposInfAdicional.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevoTipoContrato");
                } else if (tipoNuevo == 2) {
                    duplicarInfoAdicional.setGrupo(listGruposInfAdicional.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarTipoContrato");
                }
                listGruposInfAdicional.clear();
                getListGruposInfAdicional();
            } else {
                context.update("form:TiposContratoDialogo");
                context.execute("TiposContratoDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevoTipoContrato");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarTipoContrato");
                }
            }
        }
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Vigencias Contratos
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listInformacionAdicional.get(index).getSecuencia();
            if (cualCelda == 2) {
                grupo = listInformacionAdicional.get(index).getGrupo().getDescripcion();
            }
        }
        System.out.println("Indice: " + index + " Celda: " + cualCelda);
    }

    //GUARDAR
    /**
     * Metodo que guarda los cambios efectuados en la pagina Vigencias Contratos
     */
    public void guardarCambiosInfoAd() {
        if (guardado == false) {
            if (!listInfoAdicionalBorrar.isEmpty()) {
                administrarEmplInformacionAdicional.borrarInformacionAdicional(listInfoAdicionalBorrar);
                listInfoAdicionalBorrar.clear();
            }
            if (!listInfoAdicionalCrear.isEmpty()) {
                administrarEmplInformacionAdicional.crearInformacionAdicional(listInfoAdicionalCrear);
                listInfoAdicionalCrear.clear();
            }
            if (!listInfoAdicionalModificar.isEmpty()) {
                administrarEmplInformacionAdicional.modificarInformacionAdicional(listInfoAdicionalModificar);
                listInfoAdicionalModificar.clear();
            }
            listInformacionAdicional = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosInfoAdEmpleado");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:aceptar");
            k = 0;
        }
        index = -1;
        secRegistro = null;
    }
    //CANCELAR MODIFICACIONES

    /**
     * Cancela las modificaciones realizas en la pagina
     */
    public void cancelarModificacion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }

        listInfoAdicionalBorrar.clear();
        listInfoAdicionalCrear.clear();
        listInfoAdicionalModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listInformacionAdicional = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosInfoAdEmpleado");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarInfoAdicional = listInformacionAdicional.get(index);
            }
            if (tipoLista == 1) {
                editarInfoAdicional = filtrarListInformacionAdicional.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            System.out.println("Entro a editar... valor celda: " + cualCelda);
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicial");
                context.execute("editarFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarContrato");
                context.execute("editarContrato.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarTipoContrato");
                context.execute("editarTipoContrato.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciasContratos
     */
    public void agregarNuevaInfoAd() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevaInfoAdicional.setSecuencia(l);
        nuevaInfoAdicional.setEmpleado(empleado);
        listInfoAdicionalCrear.add(nuevaInfoAdicional);

        listInformacionAdicional.add(nuevaInfoAdicional);
        nuevaInfoAdicional = new InformacionesAdicionales();
        nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosInfoAdEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        index = -1;
        secRegistro = null;
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas de la nueva vigencia
     */
    public void limpiarNuevaInfoAd() {
        nuevaInfoAdicional = new InformacionesAdicionales();
        nuevaInfoAdicional.setGrupo(new GruposInfAdicionales());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarInfoAd() {
        if (index >= 0) {
            duplicarInfoAdicional = new InformacionesAdicionales();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarInfoAdicional.setSecuencia(l);
                duplicarInfoAdicional.setEmpleado(listInformacionAdicional.get(index).getEmpleado());
                duplicarInfoAdicional.setFechainicial(listInformacionAdicional.get(index).getFechainicial());
                duplicarInfoAdicional.setFechafinal(listInformacionAdicional.get(index).getFechafinal());
                duplicarInfoAdicional.setGrupo(listInformacionAdicional.get(index).getGrupo());
                duplicarInfoAdicional.setTipodato(listInformacionAdicional.get(index).getTipodato());
                duplicarInfoAdicional.setResultadocaracter(listInformacionAdicional.get(index).getResultadocaracter());
                duplicarInfoAdicional.setResultadonumerico(listInformacionAdicional.get(index).getResultadonumerico());
                duplicarInfoAdicional.setResultadofecha(listInformacionAdicional.get(index).getResultadofecha());
                duplicarInfoAdicional.setDescripcion(listInformacionAdicional.get(index).getDescripcion());
            }
            if (tipoLista == 1) {
                duplicarInfoAdicional.setSecuencia(l);
                duplicarInfoAdicional.setEmpleado(filtrarListInformacionAdicional.get(index).getEmpleado());
                duplicarInfoAdicional.setFechainicial(filtrarListInformacionAdicional.get(index).getFechainicial());
                duplicarInfoAdicional.setFechafinal(filtrarListInformacionAdicional.get(index).getFechafinal());
                duplicarInfoAdicional.setGrupo(filtrarListInformacionAdicional.get(index).getGrupo());
                duplicarInfoAdicional.setTipodato(filtrarListInformacionAdicional.get(index).getTipodato());
                duplicarInfoAdicional.setResultadocaracter(filtrarListInformacionAdicional.get(index).getResultadocaracter());
                duplicarInfoAdicional.setResultadonumerico(filtrarListInformacionAdicional.get(index).getResultadonumerico());
                duplicarInfoAdicional.setResultadofecha(filtrarListInformacionAdicional.get(index).getResultadofecha());
                duplicarInfoAdicional.setDescripcion(filtrarListInformacionAdicional.get(index).getDescripcion());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVC");
            context.execute("DuplicarRegistroVC.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasContratos
     */
    public void confirmarDuplicar() {

        listInformacionAdicional.add(duplicarInfoAdicional);
        listInfoAdicionalCrear.add(duplicarInfoAdicional);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosInfoAdEmpleado");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVRLEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }
        duplicarInfoAdicional = new InformacionesAdicionales();
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarduplicarInfoAd() {
        duplicarInfoAdicional = new InformacionesAdicionales();
        duplicarInfoAdicional.setGrupo(new GruposInfAdicionales());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarInfoAd() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listInfoAdicionalModificar.isEmpty() && listInfoAdicionalModificar.contains(listInformacionAdicional.get(index))) {
                    int modIndex = listInfoAdicionalModificar.indexOf(listInformacionAdicional.get(index));
                    listInfoAdicionalModificar.remove(modIndex);
                    listInfoAdicionalBorrar.add(listInformacionAdicional.get(index));
                } else if (!listInfoAdicionalCrear.isEmpty() && listInfoAdicionalCrear.contains(listInformacionAdicional.get(index))) {
                    int crearIndex = listInfoAdicionalCrear.indexOf(listInformacionAdicional.get(index));
                    listInfoAdicionalCrear.remove(crearIndex);
                } else {
                    listInfoAdicionalBorrar.add(listInformacionAdicional.get(index));
                }
                listInformacionAdicional.remove(index);
            }
            if (tipoLista == 1) {
                if (!listInfoAdicionalModificar.isEmpty() && listInfoAdicionalModificar.contains(filtrarListInformacionAdicional.get(index))) {
                    int modIndex = listInfoAdicionalModificar.indexOf(filtrarListInformacionAdicional.get(index));
                    listInfoAdicionalModificar.remove(modIndex);
                    listInfoAdicionalBorrar.add(filtrarListInformacionAdicional.get(index));
                } else if (!listInfoAdicionalCrear.isEmpty() && listInfoAdicionalCrear.contains(filtrarListInformacionAdicional.get(index))) {
                    int crearIndex = listInfoAdicionalCrear.indexOf(filtrarListInformacionAdicional.get(index));
                    listInfoAdicionalCrear.remove(crearIndex);
                } else {
                    listInfoAdicionalBorrar.add(filtrarListInformacionAdicional.get(index));
                }
                int VCIndex = listInformacionAdicional.indexOf(filtrarListInformacionAdicional.get(index));
                listInformacionAdicional.remove(VCIndex);
                filtrarListInformacionAdicional.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosInfoAdEmpleado");
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
            System.out.println("Activar");
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("width: 60px");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("width: 60px");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("width: 60px");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            System.out.println("Desactivar");
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            vcFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaInicial");
            vcFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            vcFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcFechaFinal");
            vcFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            vcContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcContrato");
            vcContrato.setFilterStyle("display: none; visibility: hidden;");
            vcTipoContrato = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosInfoAdEmpleado:vcTipoContrato");
            vcTipoContrato.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosInfoAdEmpleado");
            bandera = 0;
            filtrarListInformacionAdicional = null;
            tipoLista = 0;
        }

        listInfoAdicionalBorrar.clear();
        listInfoAdicionalCrear.clear();
        listInfoAdicionalModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listInformacionAdicional = null;
        guardado = true;
    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO) (list = CONTRATOS - TIPOSCONTRATOS)

    /**
     * Metodo que ejecuta los dialogos de contratos y tipos contratos
     *
     * @param indice Fila de la tabla
     * @param list Lista filtrada - Lista real
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
    public void asignarIndex(Integer indice, int list, int LND) {
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

        if (list == 0) {
            context.update("form:ContratosDialogo");
            context.execute("ContratosDialogo.show()");
        } else if (list == 1) {
            context.update("form:TiposContratoDialogo");
            context.execute("TiposContratoDialogo.show()");
        }
    }

    public void actualizarGrupo() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listInformacionAdicional.get(index).setGrupo(grupoSelecionado);
                if (!listInfoAdicionalCrear.contains(listInformacionAdicional.get(index))) {
                    if (listInfoAdicionalModificar.isEmpty()) {
                        listInfoAdicionalModificar.add(listInformacionAdicional.get(index));
                    } else if (!listInfoAdicionalModificar.contains(listInformacionAdicional.get(index))) {
                        listInfoAdicionalModificar.add(listInformacionAdicional.get(index));
                    }
                }
            } else {
                filtrarListInformacionAdicional.get(index).setGrupo(grupoSelecionado);
                if (!listInfoAdicionalCrear.contains(filtrarListInformacionAdicional.get(index))) {
                    if (listInfoAdicionalModificar.isEmpty()) {
                        listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(index));
                    } else if (!listInfoAdicionalModificar.contains(filtrarListInformacionAdicional.get(index))) {
                        listInfoAdicionalModificar.add(filtrarListInformacionAdicional.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
            }
            permitirIndex = true;
        } else if (tipoActualizacion == 1) {
            nuevaInfoAdicional.setGrupo(grupoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVC");
        } else if (tipoActualizacion == 2) {
            duplicarInfoAdicional.setGrupo(grupoSelecionado);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVC");
        }
        filtrarListGruposInfAdicional = null;
        grupoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela la seleccion del tipo contrato seleccionado
     */
    public void cancelarCambioGrupo() {
        filtrarListGruposInfAdicional = null;
        grupoSelecionado = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a la
     * columna tipos contratos o contratos
     */
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:ContratosDialogo");
                context.execute("ContratosDialogo.show()");
                tipoActualizacion = 0;
            }
        }
    }

    /**
     * Metodo que activa el boton aceptar de la pagina y los dialogos
     */
    public void activarAceptar() {
        aceptar = false;
    }
    //EXPORTAR

    /**
     * Metodo que exporta datos a PDF
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportPDF() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasContratosPDF", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }

    /**
     * Metodo que exporta datos a XLS
     *
     * @throws IOException Excepcion de In-Out de datos
     */
    public void exportXLS() throws IOException {
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVCEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasContratosXLS", false, false, "UTF-8", null, null);
        context.responseComplete();
        index = -1;
        secRegistro = null;
    }
    //EVENTO FILTRAR

    /**
     * Evento que cambia la lista reala a la filtrada
     */
    public void eventoFiltrar() {
        if (tipoLista == 0) {
            tipoLista = 1;
        }
    }

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listInformacionAdicional != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "INFORMACIONESADICIONALES");
                backUpSecRegistro = secRegistro;
                secRegistro = null;
                if (resultado == 1) {
                    context.execute("errorObjetosDB.show()");
                } else if (resultado == 2) {
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
            if (administrarRastros.verificarHistoricosTabla("INFORMACIONESADICIONALES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //GETTERS AND SETTERS

    /**
     * Metodo que obtiene las VigenciasContratos de un empleado, en caso de ser
     * null por medio del administrar hace el llamado para almacenarlo
     *
     * @return listVC Lista Vigencias Contratos
     */
    public List<InformacionesAdicionales> getListInformacionAdicional() {
        try {
            if (listInformacionAdicional == null) {
                return listInformacionAdicional = administrarEmplInformacionAdicional.listInformacionesAdicionalesEmpleado(empleado.getSecuencia());
            } else {
                return listInformacionAdicional;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListInformacionAdicional ");
            return null;
        }
    }

    public void setListInformacionAdicional(List<InformacionesAdicionales> list) {
        this.listInformacionAdicional = list;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public List<InformacionesAdicionales> getFiltrarListInformacionAdicional() {
        return filtrarListInformacionAdicional;
    }

    public void setFiltrarListInformacionAdicional(List<InformacionesAdicionales> filtrar) {
        this.filtrarListInformacionAdicional = filtrar;
    }

    public InformacionesAdicionales getNuevaInfoAdicional() {
        return nuevaInfoAdicional;
    }

    public void setNuevaInfoAdicional(InformacionesAdicionales nuevaVigencia) {
        this.nuevaInfoAdicional = nuevaVigencia;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<GruposInfAdicionales> getListGruposInfAdicional() {
        if (listGruposInfAdicional == null) {
            listGruposInfAdicional = administrarEmplInformacionAdicional.listGruposInfAdicionales();
        }
        return listGruposInfAdicional;
    }

    public void setListGruposInfAdicional(List<GruposInfAdicionales> listGruposInfAdicional) {
        this.listGruposInfAdicional = listGruposInfAdicional;
    }

    public List<GruposInfAdicionales> getFiltrarListGruposInfAdicional() {
        return filtrarListGruposInfAdicional;
    }

    public void setFiltrarListGruposInfAdicional(List<GruposInfAdicionales> filtrado) {
        this.filtrarListGruposInfAdicional = filtrado;
    }

    public InformacionesAdicionales getEditarInfoAdicional() {
        return editarInfoAdicional;
    }

    public void setEditarInfoAdicional(InformacionesAdicionales editar) {
        this.editarInfoAdicional = editar;
    }

    public InformacionesAdicionales getDuplicarInfoAdicional() {
        return duplicarInfoAdicional;
    }

    public void setDuplicarInfoAdicional(InformacionesAdicionales duplicar) {
        this.duplicarInfoAdicional = duplicar;
    }

    public GruposInfAdicionales getGrupoSelecionado() {
        return grupoSelecionado;
    }

    public void setGrupoSelecionad(GruposInfAdicionales selecionado) {
        this.grupoSelecionado = selecionado;
    }

    public BigInteger getBackUpSecRegistro() {
        return backUpSecRegistro;
    }

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public BigInteger getSecRegistro() {
        return secRegistro;
    }

    public void setSecRegistro(BigInteger secRegistro) {
        this.secRegistro = secRegistro;
    }
}

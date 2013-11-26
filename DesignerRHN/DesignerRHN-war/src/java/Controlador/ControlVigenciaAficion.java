/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Entidades.Aficiones;
import Entidades.Empleados;
import Entidades.VigenciasAficiones;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarVigenciaAficionInterface;
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
public class ControlVigenciaAficion implements Serializable{

    @EJB
    AdministrarVigenciaAficionInterface administrarVigenciaAficion;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    ////
    private List<VigenciasAficiones> listVigenciasAficiones;
    private List<VigenciasAficiones> filtrarListVigenciasAficiones;
    private List<Aficiones> listAficiones;
    private Aficiones aficionSeleccionada;
    private List<Aficiones> filtrarListAficiones;
    private int tipoActualizacion;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column veFechaInicial, veFechaFinal, veDescripcion, veIndividual, veCIndividual, veGrupal, veCGrupal;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<VigenciasAficiones> listVigenciaAficionModificar;
    private boolean guardado;
    //crear VC
    public VigenciasAficiones nuevaVigenciaAficion;
    private List<VigenciasAficiones> listVigenciaAficionCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<VigenciasAficiones> listVigenciaAficionBorrar;
    //editar celda
    private VigenciasAficiones editarVigenciaAficion;
    private int cualCelda, tipoLista;
    //duplicar
    private VigenciasAficiones duplicarVigenciaAficion;
    private String aficion;
    private boolean permitirIndex;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;
    private Empleados empleado;

    public ControlVigenciaAficion() {
        listVigenciasAficiones = null;
        listAficiones = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listVigenciaAficionBorrar = new ArrayList<VigenciasAficiones>();
        //crear aficiones
        listVigenciaAficionCrear = new ArrayList<VigenciasAficiones>();
        k = 0;
        //modificar aficiones
        listVigenciaAficionModificar = new ArrayList<VigenciasAficiones>();
        //editar
        editarVigenciaAficion = new VigenciasAficiones();
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevaVigenciaAficion = new VigenciasAficiones();
        nuevaVigenciaAficion.setAficion(new Aficiones());
        secRegistro = null;
        permitirIndex = true;
        backUpSecRegistro = null;
        empleado = new Empleados();
    }

    public void recibirEmpleado(BigInteger secuencia) {
        listVigenciasAficiones = null;
        listAficiones = null;
        empleado = administrarVigenciaAficion.empleadoActual(secuencia);
    }

    public void modificarVigenciaAficion(int indice) {
        if (tipoLista == 0) {
            if (!listVigenciaAficionCrear.contains(listVigenciasAficiones.get(indice))) {

                if (listVigenciaAficionModificar.isEmpty()) {
                    listVigenciaAficionModificar.add(listVigenciasAficiones.get(indice));
                } else if (!listVigenciaAficionModificar.contains(listVigenciasAficiones.get(indice))) {
                    listVigenciaAficionModificar.add(listVigenciasAficiones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        } else {
            if (!listVigenciaAficionCrear.contains(filtrarListVigenciasAficiones.get(indice))) {

                if (listVigenciaAficionModificar.isEmpty()) {
                    listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(indice));
                } else if (!listVigenciaAficionModificar.contains(filtrarListVigenciasAficiones.get(indice))) {
                    listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
            index = -1;
            secRegistro = null;
        }
    }

    public void modificarVigenciaAficion(int indice, String confirmarCambio, String valorConfirmar) {
        index = indice;
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("AFICIONES")) {
            if (tipoLista == 0) {
                listVigenciasAficiones.get(indice).getAficion().setDescripcion(aficion);
            } else {
                filtrarListVigenciasAficiones.get(indice).getAficion().setDescripcion(aficion);
            }
            for (int i = 0; i < listAficiones.size(); i++) {
                if (listAficiones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoLista == 0) {
                    listVigenciasAficiones.get(indice).setAficion(listAficiones.get(indiceUnicoElemento));
                } else {
                    filtrarListVigenciasAficiones.get(indice).setAficion(listAficiones.get(indiceUnicoElemento));
                }
                listAficiones.clear();
                getListAficiones();
            } else {
                permitirIndex = false;
                context.update("form:AficionesDialogo");
                context.execute("AficionesDialogo.show()");
                tipoActualizacion = 0;
            }
        }
        if (coincidencias == 1) {
            if (tipoLista == 0) {
                if (!listVigenciaAficionCrear.contains(listVigenciasAficiones.get(indice))) {

                    if (listVigenciaAficionModificar.isEmpty()) {
                        listVigenciaAficionModificar.add(listVigenciasAficiones.get(indice));
                    } else if (!listVigenciaAficionModificar.contains(listVigenciasAficiones.get(indice))) {
                        listVigenciaAficionModificar.add(listVigenciasAficiones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            } else {
                if (!listVigenciaAficionCrear.contains(filtrarListVigenciasAficiones.get(indice))) {

                    if (listVigenciaAficionModificar.isEmpty()) {
                        listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(indice));
                    } else if (!listVigenciaAficionModificar.contains(filtrarListVigenciasAficiones.get(indice))) {
                        listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(indice));
                    }
                    if (guardado == true) {
                        guardado = false;
                    }
                }
                index = -1;
                secRegistro = null;
            }
        }
        context.update("form:datosVigenciasAficiones");
    }

    public void valoresBackupAutocompletar(int tipoNuevo, String Campo) {
        if (Campo.equals("AFICIONES")) {
            if (tipoNuevo == 1) {
                aficion = nuevaVigenciaAficion.getAficion().getDescripcion();
            } else if (tipoNuevo == 2) {
                aficion = duplicarVigenciaAficion.getAficion().getDescripcion();
            }
        }
    }

    public void autocompletarNuevoyDuplicado(String confirmarCambio, String valorConfirmar, int tipoNuevo) {
        int coincidencias = 0;
        int indiceUnicoElemento = 0;
        RequestContext context = RequestContext.getCurrentInstance();
        if (confirmarCambio.equalsIgnoreCase("AFICIONES")) {
            if (tipoNuevo == 1) {
                nuevaVigenciaAficion.getAficion().setDescripcion(aficion);
            } else if (tipoNuevo == 2) {
                duplicarVigenciaAficion.getAficion().setDescripcion(aficion);
            }
            for (int i = 0; i < listAficiones.size(); i++) {
                if (listAficiones.get(i).getDescripcion().startsWith(valorConfirmar.toUpperCase())) {
                    indiceUnicoElemento = i;
                    coincidencias++;
                }
            }
            if (coincidencias == 1) {
                if (tipoNuevo == 1) {
                    nuevaVigenciaAficion.setAficion(listAficiones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:nuevaVigencias");
                } else if (tipoNuevo == 2) {
                    duplicarVigenciaAficion.setAficion(listAficiones.get(indiceUnicoElemento));
                    context.update("formularioDialogos:duplicarVigencias");
                }
                listAficiones.clear();
                getListAficiones();
            } else {
                context.update("form:AficionesDialogo");
                context.execute("AficionesDialogo.show()");
                tipoActualizacion = tipoNuevo;
                if (tipoNuevo == 1) {
                    context.update("formularioDialogos:nuevaVigencias");
                } else if (tipoNuevo == 2) {
                    context.update("formularioDialogos:duplicarVigencias");
                }
            }
        }
    }

    public void cambiarIndice(int indice, int celda) {
        if (permitirIndex == true) {
            index = indice;
            cualCelda = celda;
            secRegistro = listVigenciasAficiones.get(index).getSecuencia();
            if (cualCelda == 2) {
                aficion = listVigenciasAficiones.get(index).getAficion().getDescripcion();
            }
        }
    }

    public void guardarCambios() {
        if (guardado == false) {
            if (!listVigenciaAficionBorrar.isEmpty()) {
                administrarVigenciaAficion.borrarVigenciasAficiones(listVigenciaAficionBorrar);
                listVigenciaAficionBorrar.clear();
            }
            if (!listVigenciaAficionCrear.isEmpty()) {
                administrarVigenciaAficion.crearVigenciasAficiones(listVigenciaAficionCrear);
                listVigenciaAficionCrear.clear();
            }
            if (!listVigenciaAficionModificar.isEmpty()) {
                administrarVigenciaAficion.editarVigenciasAficiones(listVigenciaAficionModificar);
                listVigenciaAficionModificar.clear();
            }
            listVigenciasAficiones = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasAficiones");
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
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 0;
            filtrarListVigenciasAficiones = null;
            tipoLista = 0;
        }

        listVigenciaAficionBorrar.clear();
        listVigenciaAficionCrear.clear();
        listVigenciaAficionModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasAficiones = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasAficiones");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarVigenciaAficion = listVigenciasAficiones.get(index);
            }
            if (tipoLista == 1) {
                editarVigenciaAficion = filtrarListVigenciasAficiones.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicialD");
                context.execute("editarFechaInicialD.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinalD");
                context.execute("editarFechaFinalD.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarDescripcionD");
                context.execute("editarDescripcionD.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarIndividualD");
                context.execute("editarIndividualD.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarCIndividualD");
                context.execute("editarCIndividualD.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarGrupalD");
                context.execute("editarGrupalD.show()");
                cualCelda = -1;
            } else if (cualCelda == 6) {
                context.update("formularioDialogos:editarCGrupalD");
                context.execute("editarCGrupalD.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nueva VigenciaReformaLaboral
     */
    public void agregarNuevaVigenciaAficion() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 0;
            filtrarListVigenciasAficiones = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevaVigenciaAficion.setSecuencia(l);
        nuevaVigenciaAficion.setPersona(empleado.getPersona());
        listVigenciaAficionCrear.add(nuevaVigenciaAficion);

        listVigenciasAficiones.add(nuevaVigenciaAficion);
        nuevaVigenciaAficion = new VigenciasAficiones();
        nuevaVigenciaAficion.setAficion(new Aficiones());
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasAficiones");
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
    public void limpiarNuevaVigenciaAficion() {
        nuevaVigenciaAficion = new VigenciasAficiones();
        nuevaVigenciaAficion.setAficion(new Aficiones());
        index = -1;
        secRegistro = null;
    }
    //DUPLICAR VC

    /**
     * Metodo que duplica una vigencia especifica dado por la posicion de la
     * fila
     */
    public void duplicarVigenciaAficionM() {
        if (index >= 0) {
            duplicarVigenciaAficion = new VigenciasAficiones();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {

                duplicarVigenciaAficion.setSecuencia(l);
                duplicarVigenciaAficion.setAficion(listVigenciasAficiones.get(index).getAficion());
                duplicarVigenciaAficion.setFechafinal(listVigenciasAficiones.get(index).getFechafinal());
                duplicarVigenciaAficion.setFechainicial(listVigenciasAficiones.get(index).getFechainicial());
                duplicarVigenciaAficion.setPersona(listVigenciasAficiones.get(index).getPersona());
                duplicarVigenciaAficion.setValorcualitativo(listVigenciasAficiones.get(index).getValorcualitativo());
                duplicarVigenciaAficion.setValorcualitativogrupo(listVigenciasAficiones.get(index).getValorcualitativogrupo());
                duplicarVigenciaAficion.setValorcuantitativo(listVigenciasAficiones.get(index).getValorcuantitativo());
                duplicarVigenciaAficion.setValorcuantitativogrupo(listVigenciasAficiones.get(index).getValorcuantitativogrupo());


            }
            if (tipoLista == 1) {

                duplicarVigenciaAficion.setSecuencia(l);
                duplicarVigenciaAficion.setAficion(filtrarListVigenciasAficiones.get(index).getAficion());
                duplicarVigenciaAficion.setFechafinal(filtrarListVigenciasAficiones.get(index).getFechafinal());
                duplicarVigenciaAficion.setFechainicial(filtrarListVigenciasAficiones.get(index).getFechainicial());
                duplicarVigenciaAficion.setPersona(filtrarListVigenciasAficiones.get(index).getPersona());
                duplicarVigenciaAficion.setValorcualitativo(filtrarListVigenciasAficiones.get(index).getValorcualitativo());
                duplicarVigenciaAficion.setValorcualitativogrupo(filtrarListVigenciasAficiones.get(index).getValorcualitativogrupo());
                duplicarVigenciaAficion.setValorcuantitativo(filtrarListVigenciasAficiones.get(index).getValorcuantitativo());
                duplicarVigenciaAficion.setValorcuantitativogrupo(filtrarListVigenciasAficiones.get(index).getValorcuantitativogrupo());

            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigencias");
            context.execute("DuplicarRegistroVigencias.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla
     * VigenciasReformasLaborales
     */
    public void confirmarDuplicar() {
        duplicarVigenciaAficion.setPersona(empleado.getPersona());
        listVigenciasAficiones.add(duplicarVigenciaAficion);
        listVigenciaAficionCrear.add(duplicarVigenciaAficion);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosVigenciasAficiones");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 0;
            filtrarListVigenciasAficiones = null;
            tipoLista = 0;
        }
        duplicarVigenciaAficion = new VigenciasAficiones();
    }
    //LIMPIAR DUPLICAR

    /**
     * Metodo que limpia los datos de un duplicar Vigencia
     */
    public void limpiarDuplicar() {
        duplicarVigenciaAficion = new VigenciasAficiones();
        duplicarVigenciaAficion.setAficion(new Aficiones());
    }

    //BORRAR VC
    /**
     * Metodo que borra las vigencias seleccionadas
     */
    public void borrarVigenciaAficion() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listVigenciaAficionModificar.isEmpty() && listVigenciaAficionModificar.contains(listVigenciasAficiones.get(index))) {
                    int modIndex = listVigenciaAficionModificar.indexOf(listVigenciasAficiones.get(index));
                    listVigenciaAficionModificar.remove(modIndex);
                    listVigenciaAficionBorrar.add(listVigenciasAficiones.get(index));
                } else if (!listVigenciaAficionCrear.isEmpty() && listVigenciaAficionCrear.contains(listVigenciasAficiones.get(index))) {
                    int crearIndex = listVigenciaAficionCrear.indexOf(listVigenciasAficiones.get(index));
                    listVigenciaAficionCrear.remove(crearIndex);
                } else {
                    listVigenciaAficionBorrar.add(listVigenciasAficiones.get(index));
                }
                listVigenciasAficiones.remove(index);
            }
            if (tipoLista == 1) {
                if (!listVigenciaAficionModificar.isEmpty() && listVigenciaAficionModificar.contains(filtrarListVigenciasAficiones.get(index))) {
                    int modIndex = listVigenciaAficionModificar.indexOf(filtrarListVigenciasAficiones.get(index));
                    listVigenciaAficionModificar.remove(modIndex);
                    listVigenciaAficionBorrar.add(filtrarListVigenciasAficiones.get(index));
                } else if (!listVigenciaAficionCrear.isEmpty() && listVigenciaAficionCrear.contains(filtrarListVigenciasAficiones.get(index))) {
                    int crearIndex = listVigenciaAficionCrear.indexOf(filtrarListVigenciasAficiones.get(index));
                    listVigenciaAficionCrear.remove(crearIndex);
                } else {
                    listVigenciaAficionBorrar.add(filtrarListVigenciasAficiones.get(index));
                }
                int VCIndex = listVigenciasAficiones.indexOf(filtrarListVigenciasAficiones.get(index));
                listVigenciasAficiones.remove(VCIndex);
                filtrarListVigenciasAficiones.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasAficiones");
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

            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("width: 50px");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("width: 50px");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("width: 100px");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("width: 100px");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("width: 100px");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("width: 100px");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("width: 100px");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 1;
        } else if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 0;
            filtrarListVigenciasAficiones = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            veFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaInicial");
            veFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            veFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veFechaFinal");
            veFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            veDescripcion = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veDescripcion");
            veDescripcion.setFilterStyle("display: none; visibility: hidden;");
            veIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veIndividual");
            veIndividual.setFilterStyle("display: none; visibility: hidden;");
            veCIndividual = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCIndividual");
            veCIndividual.setFilterStyle("display: none; visibility: hidden;");
            veGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veGrupal");
            veGrupal.setFilterStyle("display: none; visibility: hidden;");
            veCGrupal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosVigenciasAficiones:veCGrupal");
            veCGrupal.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosVigenciasAficiones");
            bandera = 0;
            filtrarListVigenciasAficiones = null;
            tipoLista = 0;
        }

        listVigenciaAficionBorrar.clear();
        listVigenciaAficionCrear.clear();
        listVigenciaAficionModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listVigenciasAficiones = null;
        guardado = true;

    }
    //ASIGNAR INDEX PARA DIALOGOS COMUNES (LDN = LISTA - NUEVO - DUPLICADO)

    /**
     * Metodo que ejecuta el dialogo de reforma laboral
     *
     * @param indice Fila de la tabla
     * @param list Lista filtrada - Lista real
     * @param LND Tipo actualizacion = LISTA - NUEVO - DUPLICADO
     */
    public void asignarIndex(Integer indice, int LND) {
        index = indice;
        RequestContext context = RequestContext.getCurrentInstance();
        if (LND == 0) {
            tipoActualizacion = 0;
        } else if (LND == 1) {
            tipoActualizacion = 1;
        } else if (LND == 2) {
            tipoActualizacion = 2;
        }
        context.update("form:AficionesDialogo");
        context.execute("AficionesDialogo.show()");
    }

    //LOVS
    //CIUDAD
    /**
     * Metodo que actualiza la reforma laboral seleccionada
     */
    public void actualizarAficion() {
        if (tipoActualizacion == 0) {
            if (tipoLista == 0) {
                listVigenciasAficiones.get(index).setAficion(aficionSeleccionada);
                if (!listVigenciaAficionCrear.contains(listVigenciasAficiones.get(index))) {
                    if (listVigenciaAficionModificar.isEmpty()) {
                        listVigenciaAficionModificar.add(listVigenciasAficiones.get(index));
                    } else if (!listVigenciaAficionModificar.contains(listVigenciasAficiones.get(index))) {
                        listVigenciaAficionModificar.add(listVigenciasAficiones.get(index));
                    }
                }
            } else {
                filtrarListVigenciasAficiones.get(index).setAficion(aficionSeleccionada);
                if (!listVigenciaAficionCrear.contains(filtrarListVigenciasAficiones.get(index))) {
                    if (listVigenciaAficionModificar.isEmpty()) {
                        listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(index));
                    } else if (!listVigenciaAficionModificar.contains(filtrarListVigenciasAficiones.get(index))) {
                        listVigenciaAficionModificar.add(filtrarListVigenciasAficiones.get(index));
                    }
                }
            }
            if (guardado == true) {
                guardado = false;
                //RequestContext.getCurrentInstance().update("form:aceptar");
            }
            permitirIndex = true;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosVigenciasAficiones");
        } else if (tipoActualizacion == 1) {
            nuevaVigenciaAficion.setAficion(aficionSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:nuevaVigencias");
        } else if (tipoActualizacion == 2) {
            duplicarVigenciaAficion.setAficion(aficionSeleccionada);
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarVigencias");
        }
        filtrarListAficiones = null;
        aficionSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
    }

    /**
     * Metodo que cancela los cambios sobre reforma laboral
     */
    public void cancelarCambioAficion() {
        filtrarListAficiones = null;
        aficionSeleccionada = null;
        aceptar = true;
        index = -1;
        secRegistro = null;
        tipoActualizacion = -1;
        permitirIndex = true;
    }

    //LISTA DE VALORES DINAMICA
    /**
     * Metodo que activa la lista de valores de la tabla con respecto a las
     * reformas laborales
     */
    public void listaValoresBoton() {
        if (index >= 0) {
            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 2) {
                context.update("form:AficionesDialogo");
                context.execute("AficionesDialogo.show()");
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "VigenciasAficionesDF", false, false, "UTF-8", null, null);
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosVigenciaExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "VigenciasAficionesXLS", false, false, "UTF-8", null, null);
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
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (listVigenciasAficiones != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "VIGENCIASAFICIONES");
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
            if (administrarRastros.verificarHistoricosTabla("VIGENCIASAFICIONES")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }
    //GETTERS AND SETTERS

    public List<VigenciasAficiones> getListVigenciasAficiones() {
        try {
            if (listVigenciasAficiones == null) {
                listVigenciasAficiones = new ArrayList<VigenciasAficiones>();
                listVigenciasAficiones = administrarVigenciaAficion.listVigenciasAficionesPersona(empleado.getPersona().getSecuencia());
                return listVigenciasAficiones;
            } else {
                return listVigenciasAficiones;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getListVigenciasAficiones : " + e.toString());
            return null;
        }
    }

    public void setListVigenciasAficiones(List<VigenciasAficiones> setListVigenciasAficiones) {
        this.listVigenciasAficiones = setListVigenciasAficiones;
    }

    public List<VigenciasAficiones> getFiltrarListVigenciasAficiones() {
        return filtrarListVigenciasAficiones;
    }

    public void setFiltrarListVigenciasAficiones(List<VigenciasAficiones> setFiltrarListVigenciasAficiones) {
        this.filtrarListVigenciasAficiones = setFiltrarListVigenciasAficiones;
    }

    public VigenciasAficiones getNuevaVigenciaAficion() {
        return nuevaVigenciaAficion;
    }

    public void setNuevaVigenciaAficion(VigenciasAficiones setNuevaVigenciaAficion) {
        this.nuevaVigenciaAficion = setNuevaVigenciaAficion;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public List<Aficiones> getListAficiones() {
        if (listAficiones == null) {
            listAficiones = new ArrayList<Aficiones>();
            listAficiones = administrarVigenciaAficion.listAficiones();
        }
        return listAficiones;
    }

    public void setListAficiones(List<Aficiones> setListAficiones) {
        this.listAficiones = setListAficiones;
    }

    public List<Aficiones> getFiltrarListAficiones() {
        return filtrarListAficiones;
    }

    public void setFiltrarListAficiones(List<Aficiones> setFiltrarListAficiones) {
        this.filtrarListAficiones = setFiltrarListAficiones;
    }

    public VigenciasAficiones getEditarVigenciaAficion() {
        return editarVigenciaAficion;
    }

    public void setEditarVigenciaAficion(VigenciasAficiones setEditarVigenciaAficion) {
        this.editarVigenciaAficion = setEditarVigenciaAficion;
    }

    public VigenciasAficiones getDuplicarVigenciaAficion() {
        return duplicarVigenciaAficion;
    }

    public void setDuplicarVigenciaAficion(VigenciasAficiones setDuplicarVigenciaAficion) {
        this.duplicarVigenciaAficion = setDuplicarVigenciaAficion;
    }

    public Aficiones getAficionSeleccionada() {
        return aficionSeleccionada;
    }

    public void setAficionSeleccionada(Aficiones setAficionSeleccionada) {
        this.aficionSeleccionada = setAficionSeleccionada;
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

    public void setBackUpSecRegistro(BigInteger backUpSecRegistro) {
        this.backUpSecRegistro = backUpSecRegistro;
    }

    public Empleados getEmpleado() {
        return empleado;
    }
}

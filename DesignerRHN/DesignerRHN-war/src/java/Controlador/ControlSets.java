package Controlador;

import Entidades.Empleados;
import Entidades.Sets;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSetsInterface;
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
 * @author AndresPineda
 */
@ManagedBean
@SessionScoped
public class ControlSets implements Serializable {

    @EJB
    AdministrarSetsInterface administrarSets;
    @EJB
    AdministrarRastrosInterface administrarRastros;
    //Vigencias Cargos
    private List<Sets> listSets;
    private List<Sets> filtrarSets;
    private Empleados empleado;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column setsFechaInicial, setsFechaFinal, setsPromedio, setsTipo, setsPorcentaje, setsTotalIngresos;
    //Otros
    private boolean aceptar;
    private int index;
    //modificar
    private List<Sets> listSetsModificar;
    private boolean guardado, guardarOk;
    //crear VC
    public Sets nuevoSet;
    private List<Sets> listSetsCrear;
    private BigInteger l;
    private int k;
    //borrar VC
    private List<Sets> listSetsBorrar;
    //editar celda
    private Sets editarSets;
    private int cualCelda, tipoLista;
    private boolean cambioEditor, aceptarEditar;
    //duplicar
    private Sets duplicarSet;
    private BigInteger secRegistro;
    private BigInteger backUpSecRegistro;

    /**
     * Constructor de ControlSet
     */
    public ControlSets() {

        backUpSecRegistro = null;
        listSets = null;
        //Otros
        aceptar = true;
        //borrar aficiones
        listSetsBorrar = new ArrayList<Sets>();
        //crear aficiones
        listSetsCrear = new ArrayList<Sets>();
        k = 0;
        //modificar aficiones
        listSetsModificar = new ArrayList<Sets>();
        //editar
        editarSets = new Sets();
        cambioEditor = false;
        aceptarEditar = true;
        cualCelda = -1;
        tipoLista = 0;
        //guardar
        guardado = true;
        //Crear VC
        nuevoSet = new Sets();
        duplicarSet = new Sets();
        secRegistro = null;
    }

    //EMPLEADO DE LA VIGENCIA
    /**
     * Metodo que recibe la secuencia empleado desde la pagina anterior y
     * obtiene el empleado referenciado
     *
     * @param sec Secuencia del Empleado
     */
    public void recibirEmpleado(Empleados empl) {
        listSets = null;
        empleado = empl;
    }

    /**
     * Metodo que modifica los cambios efectuados en la tabla Sets de la pagina
     *
     * @param indice Fila en la cual se realizo el cambio
     */
    public void modificarSets(int indice) {
        if (tipoLista == 0) {
            if (!listSetsCrear.contains(listSets.get(indice))) {
                if (listSetsModificar.isEmpty()) {
                    listSetsModificar.add(listSets.get(indice));
                } else if (!listSetsModificar.contains(listSets.get(indice))) {
                    listSetsModificar.add(listSets.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                }
            }
        }
        if (tipoLista == 1) {
            if (!listSetsCrear.contains(filtrarSets.get(indice))) {
                if (listSetsModificar.isEmpty()) {
                    listSetsModificar.add(filtrarSets.get(indice));
                } else if (!listSetsModificar.contains(filtrarSets.get(indice))) {
                    listSetsModificar.add(filtrarSets.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    //RequestContext.getCurrentInstance().update("form:aceptar");
                }
            }
        }
        index = -1;
        secRegistro = null;
    }

    //Ubicacion Celda.
    /**
     * Metodo que obtiene la posicion dentro de la tabla Sets
     *
     * @param indice Fila de la tabla
     * @param celda Columna de la tabla
     */
    public void cambiarIndice(int indice, int celda) {
        index = indice;
        cualCelda = celda;
        secRegistro = listSets.get(index).getSecuencia();
    }
    //GUARDAR

    /**
     * Metodo que guarda los cambios efectuados en la pagina Sets
     */
    public void guardarCambiosSets() {
        if (guardado == false) {
            if (!listSetsBorrar.isEmpty()) {
                for (int i = 0; i < listSetsBorrar.size(); i++) {
                    administrarSets.borrarSets(listSetsBorrar.get(i));
                }
                listSetsBorrar.clear();
            }
            if (!listSetsCrear.isEmpty()) {
                for (int i = 0; i < listSetsCrear.size(); i++) {
                    administrarSets.crearSets(listSetsCrear.get(i));
                }
                listSetsCrear.clear();
            }
            if (!listSetsModificar.isEmpty()) {
                administrarSets.modificarSets(listSetsModificar);
                listSetsModificar.clear();
            }
            listSets = null;
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSetsEmpleado");
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
            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 0;
            filtrarSets = null;
            tipoLista = 0;
        }

        listSetsBorrar.clear();
        listSetsCrear.clear();
        listSetsModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listSets = null;
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSetsEmpleado");
    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        if (index >= 0) {
            if (tipoLista == 0) {
                editarSets = listSets.get(index);
            }
            if (tipoLista == 1) {
                editarSets = filtrarSets.get(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            if (cualCelda == 0) {
                context.update("formularioDialogos:editarFechaInicial");
                context.execute("editarFechaInicial.show()");
                cualCelda = -1;
            } else if (cualCelda == 1) {
                context.update("formularioDialogos:editarFechaFinal");
                context.execute("editarFechaFinal.show()");
                cualCelda = -1;
            } else if (cualCelda == 2) {
                context.update("formularioDialogos:editarPorcentaje");
                context.execute("editarPorcentaje.show()");
                cualCelda = -1;
            } else if (cualCelda == 3) {
                context.update("formularioDialogos:editarPromedio");
                context.execute("editarPromedio.show()");
                cualCelda = -1;
            } else if (cualCelda == 4) {
                context.update("formularioDialogos:editarTipoSet");
                context.execute("editarTipoSet.show()");
                cualCelda = -1;
            } else if (cualCelda == 5) {
                context.update("formularioDialogos:editarTotalIngresos");
                context.execute("editarTotalIngresos.show()");
                cualCelda = -1;
            }
        }
        index = -1;
        secRegistro = null;
    }

    //CREAR VU
    /**
     * Metodo que se encarga de agregar un nuevo Set
     */
    public void agregarNuevoSet() {
        if (bandera == 1) {
            //CERRAR FILTRADO
            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 0;
            filtrarSets = null;
            tipoLista = 0;
        }
        //AGREGAR REGISTRO A LA LISTA VIGENCIAS CARGOS EMPLEADO.
        k++;
        l = BigInteger.valueOf(k);
        nuevoSet.setSecuencia(l);
        nuevoSet.setEmpleado(empleado);
        listSetsCrear.add(nuevoSet);

        listSets.add(nuevoSet);
        nuevoSet = new Sets();
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSetsEmpleado");
        if (guardado == true) {
            guardado = false;
            RequestContext.getCurrentInstance().update("form:aceptar");
        }
        index = -1;
        secRegistro = null;
    }
    //LIMPIAR NUEVO REGISTRO

    /**
     * Metodo que limpia las casillas del nuevo Set
     */
    public void limpiarNuevaSets() {
        nuevoSet = new Sets();
        index = -1;
        secRegistro = null;
    }

    //DUPLICAR VC
    /**
     * Metodo que duplica un Set especifico dado por la posicion de la fila
     */
    public void duplicarSets() {
        if (index >= 0) {
            duplicarSet = new Sets();
            k++;
            l = BigInteger.valueOf(k);

            if (tipoLista == 0) {
                duplicarSet.setSecuencia(l);
                duplicarSet.setEmpleado(listSets.get(index).getEmpleado());
                duplicarSet.setFechainicial(listSets.get(index).getFechainicial());
                duplicarSet.setFechafinal(listSets.get(index).getFechafinal());
                duplicarSet.setPorcentaje(listSets.get(index).getPorcentaje());
                duplicarSet.setPromedio(listSets.get(index).getPromedio());
                duplicarSet.setTiposet(listSets.get(index).getTiposet());
                duplicarSet.setTotalingresos(listSets.get(index).getTotalingresos());
            }
            if (tipoLista == 1) {

                duplicarSet.setSecuencia(l);
                duplicarSet.setEmpleado(filtrarSets.get(index).getEmpleado());
                duplicarSet.setFechainicial(filtrarSets.get(index).getFechainicial());
                duplicarSet.setFechafinal(filtrarSets.get(index).getFechafinal());
                duplicarSet.setPorcentaje(filtrarSets.get(index).getPorcentaje());
                duplicarSet.setPromedio(filtrarSets.get(index).getPromedio());
                duplicarSet.setTiposet(filtrarSets.get(index).getTiposet());
                duplicarSet.setTotalingresos(filtrarSets.get(index).getTotalingresos());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("formularioDialogos:duplicarSet");
            context.execute("DuplicarRegistroSet.show()");
            index = -1;
            secRegistro = null;
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicar() {

        listSets.add(duplicarSet);
        listSetsCrear.add(duplicarSet);
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSetsEmpleado");
        index = -1;
        secRegistro = null;
        if (guardado == true) {
            guardado = false;
            //RequestContext.getCurrentInstance().update("form:aceptar");
        }
        if (bandera == 1) {
            //CERRAR FILTRADO
            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 0;
            filtrarSets = null;
            tipoLista = 0;
        }
        duplicarSet = new Sets();
    }

    //LIMPIAR DUPLICAR
    /**
     * Metodo que limpia los datos de un duplicar Set
     */
    public void limpiarDuplicarSets() {
        duplicarSet = new Sets();
    }

    //BORRAR VC
    /**
     * Metodo que borra los Sets seleccionados
     */
    public void borrarSets() {

        if (index >= 0) {
            if (tipoLista == 0) {
                if (!listSetsModificar.isEmpty() && listSetsModificar.contains(listSets.get(index))) {
                    int modIndex = listSetsModificar.indexOf(listSets.get(index));
                    listSetsModificar.remove(modIndex);
                    listSetsBorrar.add(listSets.get(index));
                } else if (!listSetsCrear.isEmpty() && listSetsCrear.contains(listSets.get(index))) {
                    int crearIndex = listSetsCrear.indexOf(listSets.get(index));
                    listSetsCrear.remove(crearIndex);
                } else {
                    listSetsBorrar.add(listSets.get(index));
                }
                listSets.remove(index);
            }
            if (tipoLista == 1) {
                if (!listSetsModificar.isEmpty() && listSetsModificar.contains(filtrarSets.get(index))) {
                    int modIndex = listSetsModificar.indexOf(filtrarSets.get(index));
                    listSetsModificar.remove(modIndex);
                    listSetsBorrar.add(filtrarSets.get(index));
                } else if (!listSetsCrear.isEmpty() && listSetsCrear.contains(filtrarSets.get(index))) {
                    int crearIndex = listSetsCrear.indexOf(filtrarSets.get(index));
                    listSetsCrear.remove(crearIndex);
                } else {
                    listSetsBorrar.add(filtrarSets.get(index));
                }
                int VCIndex = listSets.indexOf(filtrarSets.get(index));
                listSets.remove(VCIndex);
                filtrarSets.remove(index);
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSetsEmpleado");
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

            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("width: 60px");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("width: 60px");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("width: 60px");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("width: 60px");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("width: 60px");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 0;
            filtrarSets = null;
            tipoLista = 0;
        }
    }

    //SALIR
    /**
     * Metodo que cierra la sesion y limpia los datos en la pagina
     */
    public void salir() {
        if (bandera == 1) {
            setsFechaInicial = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) FacesContext.getCurrentInstance().getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 0;
            filtrarSets = null;
            tipoLista = 0;
        }

        listSetsBorrar.clear();
        listSetsCrear.clear();
        listSetsModificar.clear();
        index = -1;
        secRegistro = null;
        k = 0;
        listSets = null;
        guardado = true;
    }

    /**
     * Metodo que activa el boton aceptar de la pantalla y dialogos
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSetsEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarPDF();
        exporter.export(context, tabla, "SetsPDF", false, false, "UTF-8", null, null);
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
        DataTable tabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("formExportar:datosSetsEmpleadoExportar");
        FacesContext context = FacesContext.getCurrentInstance();
        Exporter exporter = new ExportarXLS();
        exporter.export(context, tabla, "SetsXLS", false, false, "UTF-8", null, null);
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
        if (listSets != null) {
            if (secRegistro != null) {
                int resultado = administrarRastros.obtenerTabla(secRegistro, "SETS");
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
            if (administrarRastros.verificarHistoricosTabla("SETS")) {
                context.execute("confirmarRastroHistorico.show()");
            } else {
                context.execute("errorRastroHistorico.show()");
            }

        }
        index = -1;
    }

    //GETTERS AND SETTERS
    /**
     * Metodo que obtiene la lista de Sets de un Empleado, en caso de que sea
     * null hace el llamado al metodo de obtener Sets del empleado, en caso
     * contrario no genera operaciones
     *
     * @return listS Lista de Sets de un Empleado
     */
    public List<Sets> getSetsEmpleado() {
        try {
            if (listSets == null) {
                return listSets = administrarSets.SetsEmpleado(empleado.getSecuencia());
            } else {
                return listSets;
            }
        } catch (Exception e) {
            System.out.println("Error...!! getSetsEmpleado ");
            return null;
        }
    }

    public void setSetsEmpleado(List<Sets> sets) {
        this.listSets = sets;
    }

    /**
     * Get del empleado, en caso de existir lo retorna en caso contrario lo
     * obtiene y retorna
     *
     * @return empleado Empleado que esta usado en el momento
     */
    public Empleados getEmpleado() {
        return empleado;
    }

    public List<Sets> getFiltrarSets() {
        return filtrarSets;
    }

    public void setFiltrarSets(List<Sets> filtrarSet) {
        this.filtrarSets = filtrarSet;
    }

    public Sets getNuevoSet() {
        return nuevoSet;
    }

    public void setNuevoSet(Sets nuevoSet) {
        this.nuevoSet = nuevoSet;
    }

    public boolean isAceptar() {
        return aceptar;
    }

    public Sets getEditarSets() {
        return editarSets;
    }

    public void setEditarSets(Sets editarSet) {
        this.editarSets = editarSet;
    }

    public Sets getDuplicarSet() {
        return duplicarSet;
    }

    public void setDuplicarSet(Sets duplicarSet) {
        this.duplicarSet = duplicarSet;
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
    
    
}

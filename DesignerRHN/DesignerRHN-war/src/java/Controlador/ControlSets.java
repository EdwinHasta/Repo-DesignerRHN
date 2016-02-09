package Controlador;

import Entidades.Empleados;
import Entidades.Personas;
import Entidades.Sets;
import Exportar.ExportarPDF;
import Exportar.ExportarXLS;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfaceAdministrar.AdministrarSetsInterface;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
    private Sets setSeleccionado;
    private Empleados empleado;
    private Personas per;
    //Activo/Desactivo Crtl + F11
    private int bandera;
    //Columnas Tabla VC
    private Column setsFechaInicial, setsFechaFinal, setsPromedio, setsTipo, setsPorcentaje, setsTotalIngresos;
    public String altoTabla;
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
    private Date fechaParametro;
    private Date fechaIni, fechaFin;
    private String auxTipoSet;
    private BigDecimal auxPromedio;
    public String infoRegistro;
    private int tipoActualizacion;
    private String mensajeValidacion;

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
        altoTabla = "270";
        tipoActualizacion = 0;
        mensajeValidacion = " ";
        index = -1;
    }

    @PostConstruct
    public void inicializarAdministrador() {
        try {
            FacesContext x = FacesContext.getCurrentInstance();
            HttpSession ses = (HttpSession) x.getExternalContext().getSession(false);
            administrarSets.obtenerConexion(ses.getId());
            administrarRastros.obtenerConexion(ses.getId());
        } catch (Exception e) {
            System.out.println("Error postconstruct " + this.getClass().getName() + ": " + e);
            System.out.println("Causa: " + e.getCause());
        }
    }

    public void recibirEmpleado(Empleados empl) {
        RequestContext context = RequestContext.getCurrentInstance();
        empleado = empl;
        listSets = null;
        per = empleado.getPersona();
        getSetsEmpleado();

        if (listSets != null && !listSets.isEmpty()) {
            System.out.println("Entra al primer IF");
            if (listSets.size() == 1) {
                System.out.println("Segundo IF");
                //INFORMACION REGISTRO
                setSeleccionado = listSets.get(0);
                //infoRegistro = "Registro 1 de 1";
                infoRegistro = "Cantidad de registros: 1";
            } else if (listSets.size() > 1) {
                System.out.println("Else If");
                //INFORMACION REGISTRO
                setSeleccionado = listSets.get(0);
                //infoRegistro = "Registro 1 de " + vigenciasCargosEmpleado.size();
                infoRegistro = "Cantidad de registros: " + listSets.size();
            }

        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        context.update("form:informacionRegistro");
    }

    public boolean validarDatosRegistro(int i) {
        boolean retorno = true;
        if (i == 0) {
            Sets auxiliar = new Sets();
            if (tipoLista == 0) {
                auxiliar = listSets.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarSets.get(index);
            }
            int tam = 0;
            if (auxiliar.getTiposet() == null || auxiliar.getTiposet().isEmpty()) {
                retorno = false;
            } else {
                tam = auxiliar.getTiposet().length();
                if (tam == 0 || tam >= 2) {
                    retorno = false;
                }
            }
            if (tam == 1) {
                String tipo = auxiliar.getTiposet();
                System.out.println("Tipo : " + tipo);
                if (tipo.equalsIgnoreCase("1") || tipo.equalsIgnoreCase("2")) {
                    System.out.println("OK");
                } else {
                    retorno = false;
                    System.out.println("Entr :S");
                }
            }
            if (auxiliar.getPromedio() != null) {
                int signo = auxiliar.getPromedio().signum();
                if (signo <= 0) {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }
        if (i == 1) {
            int tam = 0;
            if (nuevoSet.getTiposet() == null || nuevoSet.getTiposet().isEmpty()) {
                retorno = false;
            } else {
                tam = nuevoSet.getTiposet().length();
                if (tam == 0 || tam >= 2) {
                    retorno = false;
                }
            }
            if (tam == 1) {
                String tipo = nuevoSet.getTiposet();
                System.out.println("Tipo : " + tipo);
                if (tipo.equalsIgnoreCase("1") || tipo.equalsIgnoreCase("2")) {
                    System.out.println("OK");
                } else {
                    retorno = false;
                    System.out.println("Entr :S");
                }
            }
            if (nuevoSet.getPromedio() != null) {
                int signo = nuevoSet.getPromedio().signum();
                if (signo <= 0) {
                    retorno = false;
                }
            } else {
                retorno = false;
            }
        }
        if (i == 2) {
            int tam = 0;
            if (duplicarSet.getTiposet() == null || duplicarSet.getTiposet().isEmpty()) {
                retorno = false;
            } else {
                tam = duplicarSet.getTiposet().length();
                if (tam == 0 || tam >= 2) {
                    System.out.println("Duplicar error");
                    retorno = false;
                }
            }
            if (tam == 1) {
                String tipo = duplicarSet.getTiposet();
                System.out.println("Tipo : " + tipo);
                if (tipo.equalsIgnoreCase("1") || tipo.equalsIgnoreCase("2")) {
                    System.out.println("OK");
                } else {
                    retorno = false;
                    System.out.println("Entr :S");
                }
            }
            if (duplicarSet.getPromedio() != null) {
                System.out.println("Diferente");
                int signo = duplicarSet.getPromedio().signum();
                System.out.println("Signo = " + signo);
                if (signo <= 0) {
                    System.out.println("Signp fail");
                    retorno = false;
                }
            } else {
                System.out.println("Error fuck ");
                retorno = false;
            }
        }
        return retorno;
    }

    public void listaValoresBoton() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (cualCelda == 1) {
                    context.update("");
                    context.execute("");
                    tipoActualizacion = 0;
                }
            }
        }
    }

    public void modificarSets(int indice) {
        if (tipoLista == 0) {
            if (validarDatosRegistro(0) == false) {
                listSets.get(indice).setPromedio(auxPromedio);
                listSets.get(indice).setTiposet(auxTipoSet);
            }
            if (!listSetsCrear.contains(listSets.get(indice))) {
                if (listSetsModificar.isEmpty()) {
                    listSetsModificar.add(listSets.get(indice));
                } else if (!listSetsModificar.contains(listSets.get(indice))) {
                    listSetsModificar.add(listSets.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");

                }
            }
        }
        if (tipoLista == 1) {
            if (validarDatosRegistro(0) == false) {
                filtrarSets.get(indice).setPromedio(auxPromedio);
                filtrarSets.get(indice).setTiposet(auxTipoSet);
            }
            if (!listSetsCrear.contains(filtrarSets.get(indice))) {
                if (listSetsModificar.isEmpty()) {
                    listSetsModificar.add(filtrarSets.get(indice));
                } else if (!listSetsModificar.contains(filtrarSets.get(indice))) {
                    listSetsModificar.add(filtrarSets.get(indice));
                }
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
        }
        index = -1;
        secRegistro = null;
    }

    public boolean validarFechasRegistro(int i) {
        fechaParametro = new Date();
        fechaParametro.setYear(0);
        fechaParametro.setMonth(1);
        fechaParametro.setDate(1);
        System.err.println("fechaparametro : " + fechaParametro);
        boolean retorno = true;
        if (i == 0) {
            Sets auxiliar = null;
            if (tipoLista == 0) {
                auxiliar = listSets.get(index);
            }
            if (tipoLista == 1) {
                auxiliar = filtrarSets.get(index);
            }
            if (auxiliar.getFechafinal() != null) {
                if (auxiliar.getFechainicial().after(fechaParametro) && auxiliar.getFechainicial().before(auxiliar.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (auxiliar.getFechafinal() == null) {
                if (auxiliar.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 1) {
            if (nuevoSet.getFechafinal() != null) {
                if (nuevoSet.getFechainicial().after(fechaParametro) && nuevoSet.getFechainicial().before(nuevoSet.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (nuevoSet.getFechafinal() == null) {
                if (nuevoSet.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        if (i == 2) {
            if (duplicarSet.getFechafinal() != null) {
                if (duplicarSet.getFechainicial().after(fechaParametro) && duplicarSet.getFechainicial().before(duplicarSet.getFechafinal())) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
            if (duplicarSet.getFechafinal() == null) {
                if (duplicarSet.getFechainicial().after(fechaParametro)) {
                    retorno = true;
                } else {
                    retorno = false;
                }
            }
        }
        return retorno;
    }

    public void modificarFechas(int i, int c) {
        Sets auxiliar = null;
        if (tipoLista == 0) {
            auxiliar = listSets.get(i);
        }
        if (tipoLista == 1) {
            auxiliar = filtrarSets.get(i);
        }
        if (auxiliar.getFechainicial() != null) {
            boolean retorno = false;
            index = i;
            retorno = validarFechasRegistro(0);
            if (retorno == true) {
                cambiarIndice(i, c);
                modificarSets(i);
            } else {
                if (tipoLista == 0) {
                    listSets.get(i).setFechafinal(fechaFin);
                    listSets.get(i).setFechainicial(fechaIni);
                }
                if (tipoLista == 1) {
                    filtrarSets.get(i).setFechafinal(fechaFin);
                    filtrarSets.get(i).setFechainicial(fechaIni);

                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:datosSetsEmpleado");
                context.execute("errorFechas.show()");
            }
        } else {
            if (tipoLista == 0) {
                listSets.get(i).setFechainicial(fechaIni);
            }
            if (tipoLista == 1) {
                filtrarSets.get(i).setFechainicial(fechaIni);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:datosSetsEmpleado");
            context.execute("errorRegNew.show()");
        }
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
        if (tipoLista == 0) {
            auxPromedio = listSets.get(index).getPromedio();
            auxTipoSet = listSets.get(index).getTiposet();
            fechaFin = listSets.get(index).getFechafinal();
            fechaIni = listSets.get(index).getFechainicial();
            secRegistro = listSets.get(index).getSecuencia();
        }
        if (tipoLista == 1) {
            auxPromedio = filtrarSets.get(index).getPromedio();
            auxTipoSet = filtrarSets.get(index).getTiposet();
            fechaFin = filtrarSets.get(index).getFechafinal();
            fechaIni = filtrarSets.get(index).getFechainicial();
            secRegistro = filtrarSets.get(index).getSecuencia();
        }
        System.out.println("Index : " + index + "//Celda: " + cualCelda);
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
            getSetsEmpleado();
            if (listSets != null && !listSets.isEmpty()) {
                setSeleccionado = listSets.get(0);
                infoRegistro = "Cantidad de registros: " + listSets.size();
            } else {
                infoRegistro = "Cantidad de registros: 0";
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.update("form:informacionRegistro");
            context.update("form:datosSetsEmpleado");
            guardado = true;
            RequestContext.getCurrentInstance().update("form:ACEPTAR");
            k = 0;
            FacesMessage msg = new FacesMessage("Información", "Se gurdarón los datos con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            context.update("form:growl");
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
            FacesContext c = FacesContext.getCurrentInstance();
            altoTabla = "270";
            //CERRAR FILTRADO
            setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
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
        getSetsEmpleado();
        if (listSets != null && !listSets.isEmpty()) {
            setSeleccionado = listSets.get(0);
            infoRegistro = "Cantidad de registros: " + listSets.size();
        } else {
            infoRegistro = "Cantidad de registros: 0";
        }
        guardado = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.update("form:datosSetsEmpleado");
        context.update("form:ACEPTAR");
        context.update("form:informacionRegistro");

    }

    //MOSTRAR DATOS CELDA
    /**
     * Metodo que muestra los dialogos de editar con respecto a la lista real o
     * la lista filtrada y a la columna
     */
    public void editarCelda() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                if (tipoLista == 0) {
                    editarSets = listSets.get(index);
                }
                if (tipoLista == 1) {
                    editarSets = filtrarSets.get(index);
                }

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
    }
    //CREAR VU

    /**
     * Metodo que se encarga de agregar un nuevo Set
     */
    public void agregarNuevoSet() {
        boolean resp = validarDatosRegistro(1);
        if (nuevoSet.getFechainicial() != null && resp == true) {
            if (validarFechasRegistro(1) == true) {
                if (bandera == 1) {
                    FacesContext c = FacesContext.getCurrentInstance();

                    altoTabla = "270";
                    //CERRAR FILTRADO
                    setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
                    setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                    setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
                    setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                    setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
                    setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                    setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
                    setsPromedio.setFilterStyle("display: none; visibility: hidden;");
                    setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
                    setsTipo.setFilterStyle("display: none; visibility: hidden;");
                    setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
                    setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
                    RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
                    bandera = 0;
                    filtrarSets = null;
                    tipoLista = 0;
                }
                k++;
                l = BigInteger.valueOf(k);
                nuevoSet.setSecuencia(l);
                nuevoSet.setEmpleado(empleado);
                listSetsCrear.add(nuevoSet);

                listSets.add(nuevoSet);
                infoRegistro = "Cantidad de registros: " + listSets.size();
                nuevoSet = new Sets();
                RequestContext context = RequestContext.getCurrentInstance();
                context.update("form:informacionRegistro");
                context.update("form:datosSetsEmpleado");
                context.execute("NuevoRegistroSet.hide()");
                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
                index = -1;
                secRegistro = null;
            } else {
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("errorFechas.show()");
            }
        } else {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("errorRegNew.show()");
        }
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
            if (index >= 0) {
                duplicarSet = new Sets();

                if (tipoLista == 0) {
                    duplicarSet.setEmpleado(listSets.get(index).getEmpleado());
                    duplicarSet.setFechainicial(listSets.get(index).getFechainicial());
                    duplicarSet.setFechafinal(listSets.get(index).getFechafinal());
                    duplicarSet.setPorcentaje(listSets.get(index).getPorcentaje());
                    duplicarSet.setPromedio(listSets.get(index).getPromedio());
                    duplicarSet.setTiposet(listSets.get(index).getTiposet());
                    duplicarSet.setTotalingresos(listSets.get(index).getTotalingresos());
                }
                if (tipoLista == 1) {

                    duplicarSet.setEmpleado(filtrarSets.get(index).getEmpleado());
                    duplicarSet.setFechainicial(filtrarSets.get(index).getFechainicial());
                    duplicarSet.setFechafinal(filtrarSets.get(index).getFechafinal());
                    duplicarSet.setPorcentaje(filtrarSets.get(index).getPorcentaje());
                    duplicarSet.setPromedio(filtrarSets.get(index).getPromedio());
                    duplicarSet.setTiposet(filtrarSets.get(index).getTiposet());
                    duplicarSet.setTotalingresos(filtrarSets.get(index).getTotalingresos());
                }

                context.update("formularioDialogos:duplicarSet");
                context.execute("DuplicarRegistroSet.show()");
                index = -1;
                secRegistro = null;
            }
        }
    }

    /**
     * Metodo que confirma el duplicado y actualiza los datos de la tabla Sets
     */
    public void confirmarDuplicar() {
        RequestContext context = RequestContext.getCurrentInstance();
        int contador = 0;
        mensajeValidacion = " ";

        for (int j = 0; j < listSets.size(); j++) {
            if (duplicarSet.getFechainicial().equals(listSets.get(j).getFechainicial())) {
                contador++;
            }
        }
        if (contador > 0) {
            mensajeValidacion = "Fechas NO Repetidas";

            context.update("form:validacionFechas");
            context.execute("validacionFechas.show()");

        } else {
            boolean resp = validarDatosRegistro(2);
            if (duplicarSet.getFechainicial() != null && resp == true) {
                if (validarFechasRegistro(2) == true) {
                    k++;
                    l = BigInteger.valueOf(k);
                    duplicarSet.setSecuencia(l);
                    listSets.add(duplicarSet);
                    listSetsCrear.add(duplicarSet);
                    infoRegistro = "Cantidad de registros: " + listSets.size();
                    context.update("form:datosSetsEmpleado");
                    context.update("form:informacionRegistro");
                    context.execute("DuplicarRegistroSet.hide()");
                    index = -1;
                    secRegistro = null;
                    if (guardado == true) {
                        guardado = false;
                        RequestContext.getCurrentInstance().update("form:ACEPTAR");
                    }
                    if (bandera == 1) {
                        FacesContext c = FacesContext.getCurrentInstance();

                        altoTabla = "270";
                        //CERRAR FILTRADO
                        setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
                        setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
                        setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
                        setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
                        setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
                        setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
                        setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
                        setsPromedio.setFilterStyle("display: none; visibility: hidden;");
                        setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
                        setsTipo.setFilterStyle("display: none; visibility: hidden;");
                        setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
                        setsTotalIngresos.setFilterStyle("display: none; visibility: hidden;");
                        RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
                        bandera = 0;
                        filtrarSets = null;
                        tipoLista = 0;
                    }
                    duplicarSet = new Sets();
                } else {
                    context.execute("errorFechas.show()");
                }
            } else {
                context.execute("errorRegNew.show()");
            }
        }
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
        RequestContext context = RequestContext.getCurrentInstance();
        if (index < 0) {
            context.execute("seleccionarRegistro.show()");
        } else {
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
                    infoRegistro = "Cantidad de registros: " + listSets.size();

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
                    infoRegistro = "Cantidad de registros: " + filtrarSets.size();

                }

                context.update("form:datosSetsEmpleado");
                context.update("form:informacionRegistro");

                index = -1;
                secRegistro = null;

                if (guardado == true) {
                    guardado = false;
                    RequestContext.getCurrentInstance().update("form:ACEPTAR");
                }
            }
        }
    }
    //CTRL + F11 ACTIVAR/DESACTIVAR

    /**
     * Metodo que activa el filtrado por medio de la opcion en el tollbar o por
     * medio de la tecla Crtl+F11
     */
    public void activarCtrlF11() {
        FacesContext c = FacesContext.getCurrentInstance();

        if (bandera == 0) {
            altoTabla = "246";
            setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("width: 60px");
            setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("width: 60px");
            setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("width: 60px");
            setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("width: 60px");
            setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("width: 60px");
            setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
            setsTotalIngresos.setFilterStyle("width: 60px");
            RequestContext.getCurrentInstance().update("form:datosSetsEmpleado");
            bandera = 1;
        } else if (bandera == 1) {
            altoTabla = "270";
            setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
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
            FacesContext c = FacesContext.getCurrentInstance();

            altoTabla = "270";
            setsFechaInicial = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaInicial");
            setsFechaInicial.setFilterStyle("display: none; visibility: hidden;");
            setsFechaFinal = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsFechaFinal");
            setsFechaFinal.setFilterStyle("display: none; visibility: hidden;");
            setsPorcentaje = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPorcentaje");
            setsPorcentaje.setFilterStyle("display: none; visibility: hidden;");
            setsPromedio = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsPromedio");
            setsPromedio.setFilterStyle("display: none; visibility: hidden;");
            setsTipo = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTipo");
            setsTipo.setFilterStyle("display: none; visibility: hidden;");
            setsTotalIngresos = (Column) c.getViewRoot().findComponent("form:datosSetsEmpleado:setsTotalIngresos");
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
        RequestContext context = RequestContext.getCurrentInstance();
        infoRegistro = "Cantidad de Registros: " + filtrarSets.size();
        context.update("form:informacionRegistro");
    }
    //RASTRO - COMPROBAR SI LA TABLA TIENE RASTRO ACTIVO

    public void verificarRastro() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (index >= 0) {
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
        if (listSets == null && empleado != null) {
            listSets = administrarSets.SetsEmpleado(empleado.getSecuencia());
        }
        return listSets;

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

    public Personas getPer() {
        return per;
    }

    public void setPer(Personas per) {
        this.per = per;
    }

    public Sets getSetSeleccionado() {
        return setSeleccionado;
    }

    public void setSetSeleccionado(Sets setSeleccionado) {
        this.setSeleccionado = setSeleccionado;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public String getAltoTabla() {
        return altoTabla;
    }

    public void setAltoTabla(String altoTabla) {
        this.altoTabla = altoTabla;
    }

    public String getInfoRegistro() {
        return infoRegistro;
    }

    public String getMensajeValidacion() {
        return mensajeValidacion;
    }

    public void setMensajeValidacion(String mensajeValidacion) {
        this.mensajeValidacion = mensajeValidacion;
    }
}
